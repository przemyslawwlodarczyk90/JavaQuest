package com.example.javaquest._18_rest_api.Lesson12_ErrorResponseDesign;

public class _Exercises_Lesson12_ErrorResponseDesign {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListRfc7807Fields {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 5 pol standardu RFC 7807 (type, title,
         * status, detail, instance) - po 1 zdaniu przeznaczenia kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementBasicProblemJsonResponse {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/items/{id}` zwracajacym RFC 7807 dla
         * nieistniejacego ID - Content-Type "application/problem+json".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementConsistentShapeForTwoDifferentErrors {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz serwer z Zadania 2 o 2. blad (np. 400 dla zlego
         * requestu) - zweryfikuj, ze OBA maja identyczny zestaw kluczy JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GenerateUuidCorrelationId {
        /*
         * 🧪 Zadanie 4:
         * Wygeneruj `UUID.randomUUID()` jako correlationId dla KAZDEGO
         * bledu - zweryfikuj, ze 2 kolejne requesty daja RÓZNE ID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyLeakedInternalDetails {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: w podanym (przykladowym) tekscie odpowiedzi
         * bledu znajdz WSZYSTKIE fragmenty, ktore NIE powinny trafic do
         * klienta (sciezki plikow, nazwy klas, zapytania SQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementGenericExceptionCatchWithSafeMessage {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj endpoint, ktory celowo rzuca wyjatek wewnetrznie -
         * przechwyc go i zwroc BEZPIECZNY, ogolny komunikat 500, zamiast
         * pozwolic wyjatkowi "wyciec" do klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_LogFullDetailsServerSideOnly {
        /*
         * 🧪 Zadanie 7:
         * Rozszerz Zadanie 6 - wypisz PELNE szczegoly bledu (stack trace)
         * do konsoli serwera (symulacja logow), ale klient dostaje TYLKO
         * ogolny komunikat + correlationId.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareErrorShapeWithAndWithoutStandard {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: napisz przyklad "chaotycznej" odpowiedzi bledu
         * (rozne pola dla roznych endpointow) i jej odpowiednik zgodny z
         * RFC 7807 - porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementTypeFieldAsDocumentationLink {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj pole "type" jako URL wskazujacy na (symulowana)
         * strone dokumentacji konkretnego typu bledu - kazdy TYP bledu ma
         * WLASNY, stabilny URL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyClientNeedsPredictableErrorShape {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego klient API POTRZEBUJE
         * przewidywalnego ksztaltu bledu, zeby zaimplementowac
         * PROGRAMISTYCZNA (nie tylko wizualna) obsluge bledow.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCentralizedErrorHandlerMiddleware {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj "middleware" opakowujacy WSZYSTKIE handlery -
         * przechwytuje KAZDY wyjatek rzucony przez wlasciwa logike i
         * konwertuje go na spojna odpowiedz RFC 7807, zamiast robic to w
         * kazdym handlerze osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MapExceptionTypesToStatusCodes {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj mapowanie roznych typow wyjatkow Java (np.
         * `IllegalArgumentException` -> 400, `NoSuchElementException` ->
         * 404, inne -> 500) na odpowiednie kody statusu w centralnym
         * handlerze bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementErrorCatalogWithStableCodes {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj "katalog" bledow (Mapa kod->szablon RFC 7807) - kazdy
         * blad w API odwoluje sie do WPISU z katalogu po STABILNYM kodzie
         * (np. "ORDER_NOT_FOUND"), zamiast budowac tresc bledu recznie
         * za kazdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementLocalizedErrorMessages {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz Zadanie 13 o obsluge naglowka "Accept-Language" -
         * zwroc "title"/"detail" w jezyku zadanym przez klienta (min. PL i EN).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCorrelationIdPropagationFromClient {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj obsluge naglowka "X-Correlation-Id" WYSYLANEGO
         * przez klienta - jesli obecny, UZYJ go zamiast generowac nowy
         * (przydatne przy sledzeniu requestu przez wiele serwisow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementExtensionFieldsInProblemJson {
        /*
         * 🧪 Zadanie 16:
         * Rozszerz format RFC 7807 o WLASNE, DODATKOWE pola (np. "errors"
         * z lista pol walidacji) - zweryfikuj, ze podstawowe 5 pol
         * standardu WCIAZ jest obecnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDifferentDetailForDevVsProdMode {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj przelacznik "tryb deweloperski" (flaga w
         * konfiguracji) - w trybie DEV zwroc pelne szczegoly bledu
         * (stack trace), w trybie PROD tylko bezpieczny komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_LogErrorWithFullContextForDebugging {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj logowanie (do konsoli) PELNEGO kontekstu bledu
         * po stronie serwera: correlationId, metoda, sciezka, naglowki,
         * czas, stack trace - wszystko, czego potrzeba do debugowania
         * BEZ ujawniania tego klientowi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementRetryableVsNonRetryableErrorDistinction {
        /*
         * 🧪 Zadanie 19:
         * Dodaj do formatu bledu pole "retryable": true/false - klient
         * moze na tej podstawie AUTOMATYCZNIE zdecydowac, czy ponowic
         * request (np. 503 = retryable, 400 = nie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementErrorResponseVersioning {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj format bledu respektujacy wersjonowanie API
         * (Lesson14) - v1 API zwraca stary, prostszy format bledu, v2
         * zwraca pelny RFC 7807 - oba dzialajace niezaleznie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullExceptionToProblemJsonFramework {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny "framework" mapowania wyjatkow na RFC 7807 -
         * rejestracja mapowan przez adnotacje/konfiguracje, automatyczne
         * generowanie "instance" z aktualnej sciezki, automatyczne
         * generowanie correlationId - przetestuj na min. 5 typach bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDistributedTracingHeaderPropagation {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj 2 "serwisy" (2 osobne `HttpServer`) - Serwis A wywoluje
         * Serwis B, PRZEKAZUJAC dalej naglowek "X-Correlation-Id" - jesli
         * Serwis B zawiedzie, blad zwrocony do klienta ma TEN SAM ID, co
         * pozwala powiazac logi obu serwisow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementRateLimitedErrorLoggingToPreventFlooding {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj ograniczenie CZESTOTLIWOSCI logowania TEGO SAMEGO
         * bledu (np. max 1 pelny log na sekunde per typ bledu) - zapobiega
         * "zalaniu" logow przy powtarzajacym sie problemie, zachowujac
         * jednak licznik wystapien.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCircuitBreakerAwareErrorResponses {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prosty "circuit breaker" - po N kolejnych bledach
         * 500 z rzedu, KOLEJNE requesty dostaja od razu 503 z detail
         * "usluga tymczasowo niedostepna" BEZ probowania wykonac operacje -
         * zweryfikuj przejscia stanow (zamkniety -> otwarty -> polprzepustowy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementSecurityAwareErrorMessagesPreventingEnumeration {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj endpoint logowania, ktory zwraca IDENTYCZNY
         * komunikat bledu dla "uzytkownik nie istnieje" i "zle haslo" -
         * w komentarzu wyjasnij, dlaczego rozne komunikaty umozliwiaja
         * atak "enumeracji uzytkownikow" (pogłębienie w
         * `_19_security_basics`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildErrorAnalyticsDashboardData {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj zbieranie statystyk bledow (liczba wystapien per
         * typ, per endpoint, per godzina) w pamieci - po serii testowych
         * bledow wypisz podsumowanie jak dashboard monitoringu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementGracefulDegradationWithPartialErrors {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj endpoint agregujacy dane z 3 "zroDEL" (symulowane
         * wywolania) - jesli 1 zawiedzie, zwroc CZESCIOWY wynik (200) z
         * dodatkowym polem "warnings" opisujacym, KTORE zrodlo zawiodlo,
         * zamiast calkowicie odrzucac cala odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementIdempotentErrorRetryWithBackoff {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj klienta respektujacego pole "retryable" (Zadanie 19)
         * z automatycznym retry i rosnacym opoznieniem - zatrzymaj sie po
         * ustalonej liczbie prob i zglos ostateczny blad z PELNA historia prob.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementSchemaValidationForErrorResponsesThemselves {
        /*
         * 🧪 Zadanie 29:
         * Napisz walidator sprawdzajacy, czy KAZDA odpowiedz bledu z
         * Twojego API faktycznie zawiera WSZYSTKIE wymagane pola RFC 7807 -
         * uruchom go na kilku endpointach jako "test kontraktu".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteErrorHandlingSystemForMultiResourceApi {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system obslugi bledow dla mini-API (min. 3
         * zasoby) - centralny handler, katalog bledow, correlationId,
         * logowanie po stronie serwera, lokalizacja komunikatow - napisz
         * "test suite" weryfikujacy spojnosc formatu dla min. 8 roznych bledow.
         */
        public static void main(String[] args) { }
    }
}
