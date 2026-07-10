package com.example.javaquest._18_rest_api.Lesson05_StatusCodes;

public class _Exercises_Lesson05_StatusCodes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListFiveStatusCodeClasses {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 5 klas kodow statusu (1xx-5xx) - dla
         * kazdej 1 zdanie wyjasnienia + 1 przykladowy kod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementEndpointReturning200And404 {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/items/{id}` zwracajacym 200+JSON dla
         * istniejacego ID i 404 dla nieistniejacego (dane w Mapie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementPostReturning201 {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj POST /items zwracajacy 201 Created z nowo
         * utworzonym zasobem (id nadane przez serwer) w ciele odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementDeleteReturning204 {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj DELETE /items/{id} zwracajacy 204 No Content BEZ
         * ciala odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainDifferenceBetween401And403 {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij roznice miedzy 401 Unauthorized a 403
         * Forbidden - podaj po 1 konkretnym scenariuszu dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementBadRequestForInvalidInput {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj POST /items zwracajacy 400 Bad Request, jesli
         * cialo requestu jest puste lub nie zawiera wymaganego pola "name".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementMethodNotAllowedWithAllowHeader {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj zwracanie 405 Method Not Allowed dla nieobslugiwanej
         * metody na `/items/{id}` - dodaj naglowek "Allow" z lista
         * dozwolonych metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementInternalServerErrorSimulation {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj endpoint, ktory dla konkretnego, "specjalnego" ID
         * (np. 999) symuluje blad wewnetrzny i zwraca 500 - zweryfikuj
         * inne ID dzialaja normalnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyStatusCodeForFiveScenarios {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: dla 5 scenariuszy (udany odczyt, udane utworzenie,
         * usuniecie zasobu, brak uprawnien, zasob nie istnieje) podaj
         * WLASCIWY kod statusu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyLying200IsAntiPattern {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego zwracanie ZAWSZE 200 OK z
         * polem "success": false/true w ciele jest antywzorcem - kto
         * (jaki element infrastruktury) na tym traci?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementConflictForDuplicateResource {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj POST /items zwracajacy 409 Conflict, jesli
         * uzytkownik probuje utworzyc zasob o nazwie, ktora juz istnieje
         * (np. unikalny "email" lub "username").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementUnsupportedMediaTypeCheck {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj sprawdzanie naglowka "Content-Type" w POST/PUT -
         * zwroc 415 Unsupported Media Type dla innych wartosci niz
         * "application/json".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementUnprocessableEntityForValidData {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj 422 Unprocessable Entity dla requestu, ktory jest
         * POPRAWNYM JSON-em (w odroznieniu od 400), ale narusza regule
         * biznesowa (np. ujemna cena produktu) - zademonstruj roznice
         * 400 vs 422 na 2 przykladach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementGoneForPermanentlyRemovedResource {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj 410 Gone dla zasobu, ktory ISTNIAL, ale zostal
         * TRWALE usuniety (w odroznieniu od 404, gdzie zasob nigdy nie
         * istnial LUB moze pojawic sie ponownie) - w komentarzu wyjasnij
         * roznice semantyczna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRedirectChainWith301And302 {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj 2 przekierowania - 301 (trwale, stary URL na
         * zawsze) i 302 (tymczasowe, np. maintenance page) - zweryfikuj
         * poprawny naglowek Location dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildStatusCodeDecisionHelper {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode `int decideStatusCode(String method, boolean
         * resourceExists, boolean requestValid)` zwracajaca odpowiedni
         * kod statusu na podstawie tych 3 czynnikow - przetestuj dla
         * min. 6 kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementClientThatBranchesOnStatusCodeClass {
        /*
         * 🧪 Zadanie 17:
         * Napisz klienta, ktory na podstawie KLASY kodu statusu (2xx/3xx/
         * 4xx/5xx, przez `statusCode() / 100`) wybiera rozna sciezke
         * obslugi (np. loguj sukces / podazaj za przekierowaniem / pokaz
         * blad uzytkownikowi / retry) - przetestuj dla min. 4 endpointow
         * zwracajacych rozne klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConsistentErrorBodyAcrossAll4xx {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj SPOJNY format ciala bledu (np. {"error": {"code":
         * ..., "message": ...}}) dla WSZYSTKICH endpointow 4xx w Twoim
         * API - zweryfikuj identyczna strukture dla min. 3 roznych
         * bledow (foreshadowing Lesson10: Error Response Design).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DistinguishNotFoundResourceVsNotFoundRoute {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj rozroznienie miedzy "404 bo zly URL/endpoint nie
         * istnieje" a "404 bo zasob o danym ID nie istnieje" - zwroc
         * INNY komunikat bledu dla kazdego przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementTooManyRequestsPlaceholder {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj prosty licznik requestow per "klient" (np. po
         * naglowku X-Client-Id) - po przekroczeniu 5 requestow w krotkim
         * czasie zwroc 429 Too Many Requests (pelna mechanika w Lesson16).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildComprehensiveStatusCodeTestSuite {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj API z min. 5 endpointami pokrywajacymi min. 10 roznych
         * kodow statusu (z roznych klas) - napisz "test suite" w `main()`
         * wywolujacy kazdy scenariusz i WERYFIKUJACY oczekiwany kod
         * (assert lub rzucenie wyjatku przy niezgodnosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRetryWithBackoffFor503 {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj endpoint, ktory dla pierwszych 2 wywolan zwraca
         * 503 Service Unavailable, a 3. konczy sie sukcesem (symulacja
         * przejsciowej awarii) - po stronie klienta zaimplementuj retry
         * z rosnacym opoznieniem (exponential backoff) az do sukcesu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementGatewayTimeoutSimulation {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj endpoint symulujacy 504 Gateway Timeout (np.
         * celowe opoznienie dluzsze niz timeout klienta) - zaobserwuj i
         * obsluz `HttpTimeoutException` po stronie klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildMiddlewareEnforcingStatusCodeConsistency {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj "middleware" (opakowujacy handler), ktory
         * WERYFIKUJE, ze KAZDA odpowiedz z Content-Type application/json
         * i statusem 2xx ma NIEPUSTE cialo, a KAZDA odpowiedz 204 ma cialo
         * PUSTE - loguj naruszenia tej zasady.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementProblemDetailsRfc7807Format {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj format bledow zgodny z RFC 7807 ("Problem
         * Details for HTTP APIs": pola "type", "title", "status",
         * "detail", "instance") dla WSZYSTKICH bledow 4xx/5xx w Twoim API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareStatusCodeUsageAcrossRealWorldApis {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: porownaj (na podstawie wiedzy/dokumentacji) jak
         * 2 rozne, znane publiczne API uzywaja kodow statusu dla podobnych
         * scenariuszy (np. walidacja wejscia) - opisz roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementHealthCheckEndpointWithMeaningfulCodes {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj `/health` zwracajacy 200 gdy wszystkie "zaleznosci"
         * (symulowane flagi) sa OK, i 503 gdy chociaz 1 nie dziala -
         * zwroc szczegoly w ciele, KTORA zaleznosc zawiodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementStatusCodeAuditLogger {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj "middleware" zliczajacy WYSTAPIENIA kazdej klasy
         * kodu statusu (2xx/3xx/4xx/5xx) w czasie dzialania serwera -
         * po serii testowych requestow wypisz podsumowanie (ile 2xx, ile
         * 4xx itd.) - to podstawa monitoringu API w praktyce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementContentNegotiationAffectingStatusCode {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj endpoint zwracajacy 406 Not Acceptable, jesli
         * klient w naglowku "Accept" zada formatu, ktorego serwer NIE
         * potrafi wygenerowac (np. "application/xml", gdy serwer umie
         * tylko JSON) - foreshadowing Lesson07.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullStateMachineWithStatusCodeTransitions {
        /*
         * 🧪 Zadanie 30:
         * Zamodeluj zasob "zamowienie" z jawnym cyklem zycia (NOWE ->
         * OPLACONE -> WYSLANE -> DOSTARCZONE / ANULOWANE) - zaimplementuj
         * endpointy zmieniajace stan, zwracajace 409 Conflict przy
         * probie NIEDOZWOLONEGO przejscia (np. anulowanie juz dostarczonego) -
         * zweryfikuj min. 2 dozwolone i 2 niedozwolone przejscia.
         */
        public static void main(String[] args) { }
    }
}
