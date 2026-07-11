package com.example.javaquest._18_rest_api.Lesson15_Idempotency;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson15_Idempotency {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: IDEMPOTENCJA W GLAB ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LESSON04 + DLACZEGO TO PROBLEM PRAKTYCZNY
         * ============================================================
         * Lesson04 nauczyla definicji: GET/PUT/DELETE sa idempotentne
         * "z natury", POST nie jest. Ale to NIE jest ciekawostka
         * teoretyczna - to REALNY problem niezawodnosci:
         *
         *   Klient wysyla POST /payments -> siec zrywa polaczenie
         *   PRZED odebraniem odpowiedzi -> klient NIE WIE, czy platnosc
         *   PRZESZLA, czy NIE -> jesli po prostu PONOWI request "na
         *   wszelki wypadek", a pierwszy request FAKTYCZNIE dotarl do
         *   serwera -> UZYTKOWNIK ZOSTAJE OBCIAZONY DWUKROTNIE.
         *
         * To NIE jest hipotetyczne - to KLASYCZNY, dobrze znany problem
         * w systemach platnosci (Stripe, PayPal) i wszedzie tam, gdzie
         * POST ma REALNE, kosztowne skutki uboczne.
         */
        System.out.println("Problem: klient NIE WIE, czy POST faktycznie dotarl, gdy siec zawodzi - retry moze podwoic skutek.");

        Map<String, String> idempotencyStore = new ConcurrentHashMap<>();
        AtomicInteger actualChargesProcessed = new AtomicInteger(0);

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/payments", exchange -> handlePayment(exchange, idempotencyStore, actualChargesProcessed));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateProblemWithoutIdempotencyKey();
            demonstrateIdempotencyKeySolution(client, port, actualChargesProcessed);
            demonstrateDifferentKeysCreateDifferentPayments(client, port, actualChargesProcessed);
            demonstrateWhichMethodsNeedThisPattern();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PUT/DELETE sa idempotentne "za darmo" - z natury operacji
         *   (zastap/usun po ID) - retry jest BEZPIECZNY bez dodatkowej pracy.
         * - POST NIE JEST idempotentny "za darmo" - wymaga JAWNEGO wzorca
         *   "Idempotency Key": klient generuje unikalny klucz (np. UUID)
         *   PRZED wyslaniem, wysyla go w naglowku (np. "Idempotency-Key"),
         *   serwer PAMIETA wynik dla kazdego klucza i przy POWTORZONYM
         *   kluczu zwraca ZAPAMIETANY wynik zamiast wykonywac operacje ponownie.
         * - Klucz idempotencji identyfikuje POJEDYNCZA, LOGICZNA operacje -
         *   klient generuje NOWY klucz dla KAZDEJ nowej intencji (nowa
         *   platnosc = nowy klucz), ale UZYWA TEGO SAMEGO klucza przy
         *   RETRY tej samej intencji.
         * - Realne API (Stripe) dokladnie tak to implementuja - to nie
         *   jest teoretyczny wzorzec, to STANDARD branzowy.
         * - Kolejna lekcja (Lesson16): Rate Limiting - inny mechanizm
         *   niezawodnosci, chroniacy serwer PRZED nadmiarem requestow.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void handlePayment(com.sun.net.httpserver.HttpExchange exchange, Map<String, String> idempotencyStore, AtomicInteger counter) throws java.io.IOException {
        String idempotencyKey = exchange.getRequestHeaders().getFirst("Idempotency-Key");
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        if (idempotencyKey != null && idempotencyStore.containsKey(idempotencyKey)) {
            // TEN SAM klucz juz widziany - zwroc ZAPAMIETANY wynik, NIE wykonuj platnosci ponownie
            respond(exchange, 200, idempotencyStore.get(idempotencyKey));
            return;
        }

        // FAKTYCZNE przetworzenie platnosci - dzieje sie TYLKO RAZ per klucz
        int chargeId = counter.incrementAndGet();
        String result = "{\"chargeId\":" + chargeId + ",\"status\":\"processed\"}";

        if (idempotencyKey != null) {
            idempotencyStore.put(idempotencyKey, result);
        }
        respond(exchange, 201, result);
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, int status, String json) throws java.io.IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void demonstrateProblemWithoutIdempotencyKey() {
        System.out.println("\n=== PROBLEM: 'NAIWNY' RETRY BEZ KLUCZA IDEMPOTENCJI ===");
        System.out.println("Klient: POST /payments {\"amount\":100} -> siec zrywa polaczenie PRZED odpowiedzia.");
        System.out.println("Klient (nie wiedzac, czy sie udalo): POST /payments {\"amount\":100} PONOWNIE.");
        System.out.println("Bez klucza idempotencji: serwer NIE MA jak odroznic 'to ten sam zamiar' od 'to NOWA platnosc' -> DWIE oplaty.");
    }

    private static void demonstrateIdempotencyKeySolution(HttpClient client, int port, AtomicInteger counter) throws Exception {
        System.out.println("\n=== ROZWIAZANIE: KLUCZ IDEMPOTENCJI ===");

        String idempotencyKey = UUID.randomUUID().toString();
        System.out.println("Klient generuje klucz PRZED pierwsza proba: " + idempotencyKey);

        var first = sendPayment(client, port, idempotencyKey, "{\"amount\":100}");
        System.out.println("1. proba (siec 'zrywa sie' PO faktycznym dotarciu do serwera) -> " + first.statusCode() + " " + first.body());

        // symulacja RETRY - klient NIE WIE, czy 1. proba sie udala, wiec ponawia z TYM SAMYM kluczem
        var retry = sendPayment(client, port, idempotencyKey, "{\"amount\":100}");
        System.out.println("2. proba (retry, TEN SAM klucz)  -> " + retry.statusCode() + " " + retry.body());

        System.out.println("Faktyczna liczba PRZETWORZONYCH platnosci po stronie serwera: " + counter.get()
                + " (mimo 2 requestow HTTP - dzieki kluczowi idempotencji)");
    }

    private static void demonstrateDifferentKeysCreateDifferentPayments(HttpClient client, int port, AtomicInteger counter) throws Exception {
        System.out.println("\n=== ROZNE KLUCZE = ROZNE, NIEZALEZNE PLATNOSCI ===");

        int before = counter.get();
        var payment1 = sendPayment(client, port, UUID.randomUUID().toString(), "{\"amount\":50}");
        var payment2 = sendPayment(client, port, UUID.randomUUID().toString(), "{\"amount\":50}");
        System.out.println("Platnosc A (klucz X): " + payment1.body());
        System.out.println("Platnosc B (klucz Y, ta sama kwota): " + payment2.body());
        System.out.println("Liczba NOWYCH platnosci: " + (counter.get() - before) + " (2 - bo to 2 ROZNE zamiary, kazdy z WLASNYM kluczem)");
    }

    private static void demonstrateWhichMethodsNeedThisPattern() {
        System.out.println("\n=== KTORE METODY POTRZEBUJA TEGO WZORCA? ===");
        System.out.println("POST     - TAK, zawsze warto rozwazyc dla operacji z REALNYMI skutkami (platnosci, wysylka email, tworzenie zamowien).");
        System.out.println("PUT      - NIE - juz idempotentny z natury (Lesson04) - retry jest bezpieczny bez dodatkowej pracy.");
        System.out.println("DELETE   - NIE - retry usuniecia juz usunietego zasobu daje po prostu 404, efekt koncowy taki sam.");
        System.out.println("PATCH    - ZALEZY - jesli PATCH jest 'ustaw pole na X' to idempotentny; jesli 'zwieksz licznik o 1' to NIE i tez potrzebuje klucza.");
    }

    private static HttpResponse<String> sendPayment(HttpClient client, int port, String idempotencyKey, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/payments"))
                .header("Idempotency-Key", idempotencyKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
