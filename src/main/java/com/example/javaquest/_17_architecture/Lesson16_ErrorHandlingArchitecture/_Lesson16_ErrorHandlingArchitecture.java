package com.example.javaquest._17_architecture.Lesson16_ErrorHandlingArchitecture;

public class _Lesson16_ErrorHandlingArchitecture {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 16: ARCHITEKTURA OBSLUGI BLEDOW ===");

        /*
         * ============================================================
         * 📦 PROJEKT 1 WYJATKU (LESSON17, _16_clean_code) VS SYSTEM JAKO CALOSC
         * ============================================================
         * - `_16_clean_code/Lesson17` nauczyl Cie projektowac POJEDYNCZY
         *   wyjatek dobrze (nazwa z perspektywy wolujacego, kontekst w
         *   komunikacie, tlumaczenie miedzy warstwami).
         * - Ta lekcja pyta o COS WIEKSZEGO: gdy wyjatek WYLECI z gleboko
         *   ukrytej logiki biznesowej, JAK dotrze w SPOJNY sposob do
         *   KLIENTA na SAMEJ granicy systemu (Lesson11: adapter driving) -
         *   niezaleznie OD KTOREGO miejsca w calej aplikacji pochodzi?
         */
        System.out.println("Lesson17 (_16_clean_code) = projekt 1 wyjatku. Ta lekcja = jak to dociera SPOJNIE do klienta na granicy systemu.");

        /*
         * ============================================================
         * 🔹 CENTRALNY "TLUMACZ BLEDOW" NA GRANICY (BOUNDARY ERROR HANDLER)
         * ============================================================
         * - Zamiast KAZDEJ metodzie adaptera driving (kazdemu
         *   kontrolerowi) recznie lapac i formatowac bledy OSOBNO, JEDEN
         *   centralny mechanizm (na SAMEJ granicy, tam gdzie adapter
         *   driving konczy sie i zwraca odpowiedz) lapie WSZYSTKIE
         *   wyjatki i tlumaczy je na SPOJNY format zewnetrzny.
         * - To DOKLADNIE ten sam mechanizm co DTO/Mapper (Lesson07), ale
         *   zastosowany do SCIEZKI BLEDU zamiast sciezki sukcesu.
         */
        demonstrateCentralizedBoundaryErrorHandler();

        /*
         * ============================================================
         * 🔍 MAPOWANIE TYPOW WYJATKOW NA SPOJNA REPREZENTACJE ZEWNETRZNA
         * ============================================================
         * - Wyjatek domenowy (np. `InsufficientFundsException`) mapuje
         *   sie na KONKRETNY, przewidywalny kod bledu (np. "INSUFFICIENT_
         *   FUNDS") - klient API MOZE zaprogramowac swoja reakcje na TEN
         *   KONKRETNY kod, niezaleznie od tego, jak zmieni sie tresc
         *   komunikatu w przyszlosci.
         * - Wyjatek walidacji (Lesson15: poziom 1/2) mapuje sie na inny
         *   kod (np. "VALIDATION_ERROR").
         * - NIEOCZEKIWANY wyjatek (blad programisty, `_16_clean_code/
         *   Lesson17`: nie powinien byc "po cichu" polykany, ale TEZ nie
         *   powinien ujawniac wewnetrznych szczegolow klientowi) mapuje
         *   sie na OGoLNY kod (np. "INTERNAL_ERROR") - BEZ ujawniania
         *   nazwy klasy wyjatku czy stack trace'u.
         */
        demonstrateExceptionTypeToErrorCodeMapping();

        /*
         * ============================================================
         * 🔹 BEZPIECZENSTWO: NIE UJAWNIAJ WEWNETRZNYCH SZCZEGOLOW
         * ============================================================
         * - Jesli centralny "tlumacz bledow" ZAPOMNI o generycznym
         *   fallbacku i przepusci SUROWY komunikat nieoczekiwanego
         *   wyjatku (np. z nazwa tabeli SQL czy sciezka pliku), klient
         *   zewnetrzny dostaje WYCIEK informacji o wewnetrznej
         *   implementacji systemu - to REALNE ryzyko bezpieczenstwa, nie
         *   tylko brzydki komunikat.
         * - Pelny, techniczny komunikat (ze stack trace'em) powinien
         *   trafiac do LOGoW (dla zespolu), a klient dostaje TYLKO
         *   bezpieczny, ogolny komunikat + identyfikator do
         *   skorelowania z logami (np. "ID incydentu: abc-123").
         */
        demonstrateHidingInternalDetailsFromClient();

        /*
         * ============================================================
         * 🔍 ANTY-WZORZEC: ROZPROSZONA, NIESPOJNA OBSLUGA BLEDOW
         * ============================================================
         * - Bez centralnego tlumacza, KAZDY kontroler formatuje bledy PO
         *   SWOJEMU - 1 endpoint zwraca `{"error": "..."}`, inny
         *   `{"message": "...", "code": 5}`, jeszcze inny goly tekst.
         * - Klient API MUSI wtedy znac SPECYFICZNY format bledu dla
         *   KAZDEGO endpointu z osobna - dokladnie "Shotgun Surgery"
         *   (`_16_clean_code/Lesson14`) na poziomie calego API.
         */
        demonstrateInconsistentErrorHandlingAntiPattern();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Lesson17 (_16_clean_code) = projekt 1 wyjatku. Ta lekcja =
         *   spojna obsluga bledow NA GRANICY calego systemu.
         * - Centralny "tlumacz bledow" na granicy (adapter driving) -
         *   dokladnie jak DTO/Mapper (Lesson07), ale dla sciezki bledu.
         * - Mapowanie typu wyjatku -> spojny, przewidywalny kod bledu dla
         *   klienta.
         * - Bezpieczenstwo: nigdy nie ujawniaj surowych, wewnetrznych
         *   szczegolow (stack trace, nazwy klas/tabel) klientowi.
         * - Anty-wzorzec: rozproszona, niespojna obsluga bledow w kazdym
         *   kontrolerze osobno.
         * - W kolejnej lekcji (Lesson17): modularny monolit - jak
         *   ZORGANIZOWAC caly system w moduly z wymuszonymi granicami,
         *   opierajac sie na bounded context (Lesson06).
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    // Hierarchia wyjatkow domenowych - por. _16_clean_code/Lesson17 (projekt pojedynczego wyjatku).
    static class InsufficientFundsException extends RuntimeException {
        InsufficientFundsException(String message) {
            super(message);
        }
    }

    static class ProductNotFoundException extends RuntimeException {
        ProductNotFoundException(String message) {
            super(message);
        }
    }

    static class ValidationException extends RuntimeException {
        ValidationException(String message) {
            super(message);
        }
    }

    /** Spojny ksztalt bledu zwracany klientowi - NIEZALEZNIE od tego, ktora czesc systemu rzucila wyjatek. */
    record ErrorResponse(int httpLikeStatus, String code, String message) {
        @Override
        public String toString() {
            return "[" + httpLikeStatus + " " + code + "] " + message;
        }
    }

    /** Centralny 'tlumacz bledow' na granicy - JEDYNE miejsce mapujace wyjatki na odpowiedz. */
    static class BoundaryErrorHandler {
        ErrorResponse translate(RuntimeException exception) {
            if (exception instanceof InsufficientFundsException) {
                return new ErrorResponse(422, "INSUFFICIENT_FUNDS", exception.getMessage());
            }
            if (exception instanceof ProductNotFoundException) {
                return new ErrorResponse(404, "PRODUCT_NOT_FOUND", exception.getMessage());
            }
            if (exception instanceof ValidationException) {
                return new ErrorResponse(400, "VALIDATION_ERROR", exception.getMessage());
            }
            // Fallback dla NIEOCZEKIWANYCH wyjatkow - BEZ ujawniania szczegolow technicznych.
            logFullDetailsInternally(exception); // pelny szczegol trafia do 'logow', NIE do klienta
            return new ErrorResponse(500, "INTERNAL_ERROR", "Wystapil nieoczekiwany blad. ID incydentu: INC-0042");
        }

        private void logFullDetailsInternally(RuntimeException exception) {
            System.out.println("  [LOG WEWNETRZNY, NIE dla klienta] " + exception.getClass().getName() + ": " + exception.getMessage());
        }
    }

    private static void demonstrateCentralizedBoundaryErrorHandler() {
        System.out.println("\n=== CENTRALNY TLUMACZ BLEDOW NA GRANICY ===");

        BoundaryErrorHandler handler = new BoundaryErrorHandler();

        ErrorResponse response1 = handler.translate(new InsufficientFundsException("saldo=50.0, wymagane=100.0"));
        System.out.println("Blad z modulu Platnosci: " + response1);

        ErrorResponse response2 = handler.translate(new ProductNotFoundException("brak produktu SKU-999"));
        System.out.println("Blad z modulu Katalogu: " + response2);

        System.out.println("-> OBA bledy, mimo ze pochodza z ROZNYCH modulow, maja TEN SAM, spojny ksztalt ErrorResponse.");
    }

    private static void demonstrateExceptionTypeToErrorCodeMapping() {
        System.out.println("\n=== MAPOWANIE TYPU WYJATKU NA KOD BLEDU ===");

        BoundaryErrorHandler handler = new BoundaryErrorHandler();

        System.out.println(handler.translate(new ValidationException("pole 'email' jest wymagane")));
        System.out.println("-> Klient API MOZE zaprogramowac logike na podstawie kodu 'VALIDATION_ERROR',");
        System.out.println("   niezaleznie od tego, JAKI dokladnie tekst komunikatu bledu sie pojawi.");
    }

    private static void demonstrateHidingInternalDetailsFromClient() {
        System.out.println("\n=== BEZPIECZENSTWO: UKRYWANIE WEWNETRZNYCH SZCZEGOLOW ===");

        BoundaryErrorHandler handler = new BoundaryErrorHandler();

        RuntimeException unexpectedBug = new NullPointerException("Cannot invoke \"Connection.prepareStatement\" because \"conn\" is null");
        ErrorResponse response = handler.translate(unexpectedBug);

        System.out.println("Klient WIDZI TYLKO: " + response);
        System.out.println("-> Pelny szczegol (nazwa klasy, komunikat z detalami JDBC) trafil TYLKO do logu wewnetrznego,");
        System.out.println("   NIGDY do klienta - zero wycieku informacji o wewnetrznej implementacji.");
    }

    private static void demonstrateInconsistentErrorHandlingAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: KAZDY KONTROLER FORMATUJE BLEDY PO SWOJEMU ===");

        System.out.println("[Kontroler A] zwraca: {\"error\": \"Brak srodkow\"}");
        System.out.println("[Kontroler B] zwraca: {\"message\": \"Not found\", \"errorCode\": 5}");
        System.out.println("[Kontroler C] zwraca: goly tekst \"BLAD: cos poszlo nie tak\"");
        System.out.println("-> 3 ROZNE ksztalty bledu dla 3 endpointow TEGO SAMEGO systemu - klient API musi");
        System.out.println("   znac WSZYSTKIE 3 formaty osobno, zamiast 1 spojnego ErrorResponse.");
    }
}
