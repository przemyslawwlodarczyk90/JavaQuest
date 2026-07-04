package com.example.javaquest._06_networking.Lesson05_SocketHttpDownload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class _Lesson05_SocketHttpDownload {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 HTTP TO "TYLKO TEKST" NAD TCP
         * ============================================================
         * HTTP (HyperText Transfer Protocol) to protokół, na którym
         * opiera się cały web - a mimo to, "pod spodem", to zwykły
         * protokół TEKSTOWY nad TCP, bardzo podobny w duchu do WHOIS
         * z Lesson04 (linie tekstu zakończone "\r\n").
         *
         * W tej lekcji NIE używamy żadnej biblioteki HTTP (ani
         * java.net.http.HttpClient, ani żadnej zewnętrznej) - budujemy
         * zapytanie HTTP RĘCZNIE, jako zwykły String, i wysyłamy je
         * przez surowy Socket. To pokazuje, że HTTP to "po prostu tekst
         * wysłany przez gniazdo sieciowe" - żadnej magii.
         *
         * Minimalne zapytanie HTTP/1.1 GET wygląda tak:
         *
         *   GET / HTTP/1.1
         *   Host: example.com
         *   Connection: close
         *   (pusta linia)
         *
         * - linia startowa: METODA ŚCIEŻKA WERSJA
         * - nagłówek Host - wymagany w HTTP/1.1 (jeden serwer/adres IP
         *   może obsługiwać wiele domen - Host mówi, o którą chodzi)
         * - nagłówek Connection: close - mówimy serwerowi "zamknij
         *   połączenie po odpowiedzi" (ułatwia nam to odczyt - patrz niżej)
         * - PUSTA LINIA na końcu (\r\n\r\n) - oddziela nagłówki od
         *   (ewentualnego) ciała zapytania i sygnalizuje "koniec nagłówków"
         *
         * ⚠️ WAŻNE (bezpieczeństwo demo): łączymy się z PRAWDZIWĄ
         * stroną w internecie (example.com - domena zarezerwowana przez
         * IANA specjalnie do celów dokumentacyjnych/testowych, więc jest
         * "bezpiecznym" celem takich demonstracji). Jak w Lesson04:
         * setSoTimeout(5000) i try-catch z czytelnym fallbackiem, żeby
         * program zawsze zakończył się w kilka sekund, nawet bez internetu.
         */

        System.out.println("=== java.net.Socket - reczne zapytanie HTTP GET (example.com:80) ===");

        String host = "example.com";
        int port = 80;

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(5000); // max 5s oczekiwania na dane

            System.out.println("Polaczono z " + host + ":" + port);

            /*
             * ============================================================
             * 🔹 KROK 1: reczne zbudowanie i wyslanie zapytania HTTP
             * ============================================================
             * Kazda linia MUSI konczyc sie "\r\n" (CRLF) - tak wymaga
             * specyfikacja HTTP, nie samo "\n". Ostatnia pusta linia
             * ("\r\n" na koncu calego builderu) oznacza koniec naglowkow.
             */

            String request = "GET / HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n";

            System.out.println("\n=== WYSYLANE ZAPYTANIE ===");
            System.out.print(request);

            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();

            /*
             * ============================================================
             * 🔍 KROK 2: odczyt surowej odpowiedzi (status + naglowki + cialo)
             * ============================================================
             * Dzieki "Connection: close" serwer sam zamknie polaczenie po
             * wyslaniu calej odpowiedzi - readLine() zwroci null i petla
             * zakonczy sie naturalnie (analogicznie do Lesson04).
             *
             * Odpowiedz HTTP ma budowe:
             *   HTTP/1.1 200 OK              <- linia statusu
             *   Content-Type: text/html      <- naglowki...
             *   Content-Length: 1256
             *   (pusta linia)
             *   <html>...</html>             <- cialo (body) odpowiedzi
             */

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            System.out.println("\n=== SUROWA ODPOWIEDZ SERWERA ===");
            String linia;
            while ((linia = reader.readLine()) != null) {
                System.out.println(linia);
            }
            System.out.println("=== KONIEC ODPOWIEDZI (serwer zamknal polaczenie) ===");

        } catch (SocketTimeoutException e) {
            System.out.println("\n[BLAD] Przekroczono czas oczekiwania (5s) - " +
                    "brak polaczenia z internetem lub serwer niedostepny.");
        } catch (IOException e) {
            System.out.println("\n[BLAD] Nie udalo sie polaczyc z serwerem HTTP - " +
                    "brak polaczenia z internetem lub serwer niedostepny. Szczegoly: " + e.getMessage());
        }

        System.out.println("\n=== KONIEC DEMONSTRACJI ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HTTP "pod spodem" to zwykly protokol TEKSTOWY nad TCP -
         *   dokladnie taki sam Socket jak przy WHOIS (Lesson04) czy
         *   echo (Lesson03), tylko z inna "gramatyka" tekstu.
         * - Minimalne zapytanie GET: linia startowa + naglowek Host +
         *   "Connection: close" + pusta linia konczaca naglowki (CRLF!).
         * - Odpowiedz HTTP = linia statusu + naglowki + pusta linia +
         *   cialo (body) - wszystko jako tekst (dla stron HTML) plyna
         *   przez ten sam strumien bajtow.
         * - W praktyce PRAWIE NIGDY nie pisze sie HTTP "recznie" tak jak
         *   tutaj - uzywa sie gotowych klientow (np. java.net.http.HttpClient,
         *   OkHttp, RestTemplate/WebClient w Springu), ktore ukrywaja te
         *   szczegoly (parsowanie naglowkow, obsluga przekierowan,
         *   kodowanie, chunked transfer encoding itd.). Ta lekcja miala
         *   pokazac, CO tak naprawde dzieje sie "pod spodem".
         * - setSoTimeout(...) i try-catch z fallbackiem sa tak samo
         *   niezbedne jak przy WHOIS - kazde polaczenie z prawdziwym
         *   serwerem w internecie moze zawiesc.
         */
    }
}
