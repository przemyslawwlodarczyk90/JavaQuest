package com.example.javaquest._19_security_basics.Lesson09_Cors;

public class _Exercises_Lesson09_Cors {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSameOriginPolicyInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami Same-Origin Policy -
         * podaj przyklad 2 adresow URL, ktore sa TYM SAMYM originem i 2,
         * ktore sa INNYM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddAllowOriginHeaderForSpecificOrigin {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/api/data`, ktory dodaje
         * `Access-Control-Allow-Origin` TYLKO dla 1 skonfigurowanego
         * originu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ShowMissingHeaderForOtherOrigins {
        /*
         * 🧪 Zadanie 3:
         * Wyslij zadanie z INNYM originem niz dozwolony - zweryfikuj, ze
         * naglowek CORS NIE jest dodany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementWildcardOriginForPublicApi {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj wariant z `Access-Control-Allow-Origin: *` (dla
         * publicznego API bez danych uwierzytelniajacych) - wyjasnij w
         * komentarzu, kiedy to jest OK.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HandleOptionsPreflightRequest {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj obsluge zadania OPTIONS zwracajaca
         * `Access-Control-Allow-Methods`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AllowSpecificHeadersInPreflight {
        /*
         * 🧪 Zadanie 6:
         * Dodaj `Access-Control-Allow-Headers` do odpowiedzi na preflight
         * dla naglowka `Authorization`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RejectPreflightForDisallowedMethod {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj, ze preflight z metoda DELETE (jesli niedozwolona)
         * zwraca odpowiedni status/BRAK naglowkow CORS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyCorsIsNotServerSideSecurity {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, dlaczego CORS NIE zastepuje
         * autoryzacji po stronie serwera (Lesson01/Lesson07) - co MOZE, a
         * czego NIE MOZE ochronic.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListMultipleAllowedOrigins {
        /*
         * 🧪 Zadanie 9:
         * Rozszerz konfiguracje o Set kilku dozwolonych originow -
         * zweryfikuj kazdy z osobna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWildcardVsCredentialsConflict {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego `Access-Control-Allow-Origin:
         * *` NIGDY nie moze byc uzyte razem z
         * `Access-Control-Allow-Credentials: true`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementAllowCredentialsWithSpecificOrigin {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `Access-Control-Allow-Credentials: true` razem z
         * KONKRETNYM (nie wildcard) originem - dla endpointu wymagajacego
         * ciastka sesyjnego z Lesson04.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CacheProflightWithMaxAge {
        /*
         * 🧪 Zadanie 12:
         * Dodaj `Access-Control-Max-Age` do odpowiedzi preflight -
         * wyjasnij w komentarzu, jak to redukuje liczbe zadan OPTIONS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementPerRouteOriginPolicy {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj rozne polityki CORS dla roznych endpointow (np.
         * `/api/public` = wildcard, `/api/admin` = tylko 1 zaufany
         * origin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ValidateOriginAgainstRegexPattern {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj walidacje originu wzgledem WZORCA (np. wszystkie
         * subdomeny "*.przyklad.pl") zamiast sztywnej listy - UWAZAJ na
         * pulapki (np. "zprzyklad.pl" nie powinno pasowac).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainExposedHeadersForClientJs {
        /*
         * 🧪 Zadanie 15:
         * Dodaj `Access-Control-Expose-Headers` dla wlasnego naglowka
         * odpowiedzi (np. `X-Total-Count`) - wyjasnij, dlaczego BEZ tego
         * JS PO STRONIE KLIENTA nie widzi wlasnych naglowkow odpowiedzi
         * mimo poprawnego CORS na dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LogRejectedCorsRequestsForMonitoring {
        /*
         * 🧪 Zadanie 16:
         * Dodaj logowanie (na konsole) kazdego zadania z NIEDOZWOLONEGO
         * originu - przydatne do wykrywania prob nieautoryzowanego
         * dostepu z zewnetrznych stron.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareCorsWithJsonpHistorically {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: zbadaj i opisz (kilka zdan), jak przed CORS
         * radzono sobie z zadaniami cross-origin (JSONP) i dlaczego CORS
         * jest bezpieczniejszym, nowoczesnym rozwiazaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementDynamicOriginReflectionSafely {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj "odbicie" originu (`Access-Control-Allow-Origin:
         * <ten sam Origin co w zadaniu>`) TYLKO gdy origin jest na
         * bialej liscie - wyjasnij, dlaczego NAIWNE odbijanie
         * KAZDEGO originu (bez sprawdzenia listy) to POWAZNA luka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestPreflightWithCustomHeaderCombination {
        /*
         * 🧪 Zadanie 19:
         * Wyslij preflight z KOMBINACJA `Access-Control-Request-Headers`
         * (np. "Content-Type, X-Custom-Header") - zweryfikuj poprawna
         * odpowiedz `Access-Control-Allow-Headers`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementVaryOriginHeaderForCaching {
        /*
         * 🧪 Zadanie 20:
         * Dodaj naglowek `Vary: Origin` do odpowiedzi z CORS - wyjasnij w
         * komentarzu, dlaczego jest WAZNY, gdy odpowiedz moze byc
         * cache'owana (np. przez CDN) dla roznych originow.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildConfigurableCorsFilterMiddleware {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj (i zaimplementuj) reużywalny "filtr" CORS
         * (metoda/klasa dekorujaca `HttpHandler`), ktory mozna nalozyc na
         * DOWOLNY endpoint z konfigurowalna lista dozwolonych originow/
         * metod/naglowkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullCorsPolicyMatrix {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj macierz polityk: rozne originy z ROZNYMI dozwolonymi
         * metodami/naglowkami (np. origin A tylko GET, origin B GET+POST)
         * - zweryfikuj kazda kombinacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimulateCredentialedCrossOriginAttackWithoutCors {
        /*
         * 🧪 Zadanie 23:
         * Zademonstruj (koncepcyjnie, komentarz + kod), co MOGLOBY sie
         * stac, gdyby serwer zwracal `Access-Control-Allow-Origin: *`
         * RAZEM z `Access-Control-Allow-Credentials: true` dla endpointu
         * zwracajacego dane wrazliwe uzytkownika (dlaczego biblioteki
         * WYMUSZAJA blad przy takiej kombinacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCorsForWebSocketUpgradeConceptually {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: zbadaj i wyjasnij, dlaczego WebSocket (Upgrade)
         * NIE podlega standardowemu CORS w taki sam sposob jak zwykle
         * HTTP - jakie INNE mechanizmy (np. sprawdzanie naglowka
         * `Origin` recznie) trzeba zastosowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AuditProductionCorsConfigForOverPermissiveness {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode "audytujaca" konfiguracje CORS (liste
         * dozwolonych originow/metod) pod katem NADMIAROWYCH uprawnien
         * (np. wildcard razem z credentials, zbyt szeroki wzorzec
         * originu) - wypisz ostrzezenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCorsForMultiTenantApiPerClient {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj system wieloklientowy (multi-tenant), gdzie
         * kazdy "klient API" (identyfikowany np. po sciezce
         * `/api/tenant/{id}/`) ma WLASNA, oddzielna liste dozwolonych
         * originow zapisana w konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BenchmarkPreflightOverheadVsSimpleRequests {
        /*
         * 🧪 Zadanie 27:
         * Zmierz narzut czasowy preflight (2 zadania: OPTIONS + wlasciwe)
         * wobec "prostego" zadania (1 zadanie) dla 1000 wywolan -
         * skomentuj, dlaczego `Access-Control-Max-Age` (Zadanie 12) ma
         * znaczenie w praktyce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementStrictContentTypeValidationAlongsideCors {
        /*
         * 🧪 Zadanie 28:
         * Polacz CORS z jawna walidacja `Content-Type` (odrzucenie
         * nieoczekiwanych typow tresci) - wyjasnij, dlaczego CORS SAM w
         * sobie NIE chroni przed nieprawidlowa/zlosliwa tresc zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareCorsPolicyAcrossFrameworksConceptually {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zbadaj (dokumentacje), jak konfiguruje sie CORS w
         * Spring Boot (`@CrossOrigin`/`CorsConfigurationSource`) -
         * porownaj z recznym podejsciem z tej lekcji - zapowiedz ostatni
         * rozdzial kursu (Spring Boot).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureCorsConfiguredApi {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne API z: bialą listą originow (Zadanie 9),
         * preflight z cache (Zadanie 12), credentials dla konkretnego
         * originu (Zadanie 11) i audytem konfiguracji (Zadanie 25) -
         * zweryfikuj co najmniej 4 scenariusze z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
