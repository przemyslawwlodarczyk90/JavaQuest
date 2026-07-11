package com.example.javaquest._19_security_basics.Lesson10_Csrf;

public class _Exercises_Lesson10_Csrf {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCsrfInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, na czym polega atak
         * CSRF - dlaczego ofiara MUSI byc wczesniej zalogowana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementStateChangingEndpointWithoutProtection {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/change-email` zmieniajacym email
         * zalogowanego uzytkownika (na podstawie ciastka sesyjnego) BEZ
         * zadnej ochrony CSRF.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SimulateForgedRequestWithCookieManager {
        /*
         * 🧪 Zadanie 3:
         * Zaloguj "ofiare" (CookieManager), a nastepnie wyslij zadanie z
         * innym naglowkiem `Origin` do `/change-email` - zweryfikuj, ze
         * atak sie POWIODL (bo brak ochrony).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GenerateCsrfTokenAtLogin {
        /*
         * 🧪 Zadanie 4:
         * Dodaj generowanie losowego CSRF tokenu przy `/login`,
         * powiazanego z sesja - zwroc go w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RequireCsrfTokenForStateChangingRequest {
        /*
         * 🧪 Zadanie 5:
         * Wymagaj naglowka `X-CSRF-Token` w `/change-email` - odrzuc
         * (403) zadanie bez niego lub z nieprawidlowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ShowForgedRequestFailsWithCsrfProtection {
        /*
         * 🧪 Zadanie 6:
         * Powtorz atak z Zadania 3 na endpoint z ochrona z Zadania 5 -
         * zweryfikuj, ze atak zostaje ODRZUCONY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ShowLegitimateRequestSucceedsWithToken {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj, ze PRAWDZIWE zadanie (z poprawnym CSRF tokenem)
         * nadal dziala poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyGetRequestsShouldNotChangeState {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, dlaczego operacje ZMIENIAJACE stan
         * (usuniecie konta, przelew) NIGDY nie powinny byc dostepne pod
         * metoda GET (podpowiedz: linki/obrazki moga byc "klikniete"
         * automatycznie przez przegladarke bez wiedzy uzytkownika).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddSameSiteAttributeToSessionCookie {
        /*
         * 🧪 Zadanie 9:
         * Dodaj atrybut `SameSite=Lax` do ciastka sesyjnego przy
         * `/login` - wyjasnij w komentarzu jego dzialanie (dokladnie
         * jak w teorii, bez mozliwosci pelnej demonstracji przez
         * `HttpClient`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareCookieAuthWithBearerTokenCsrfExposure {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego uwierzytelnianie przez
         * `Authorization: Bearer <token>` (Lesson05) jest z NATURY mniej
         * podatne na klasyczny CSRF niz uwierzytelnianie ciastkiem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementDoubleSubmitCookiePattern {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj wzorzec "double submit cookie" - CSRF token
         * zapisany ROWNIEZ jako (osobne) ciastko, JS strony odczytuje go
         * i wysyla jako naglowek - serwer porownuje wartosc z ciastka z
         * wartoscia z naglowka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RotateCsrfTokenAfterEachUse {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj rotacje CSRF tokenu po kazdym udanym uzyciu -
         * zweryfikuj, ze STARY token przestaje dzialac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ValidateOriginAndRefererHeadersAsSecondDefense {
        /*
         * 🧪 Zadanie 13:
         * Dodaj DODATKOWA (obok CSRF tokenu) walidacje naglowka `Origin`/
         * `Referer` wobec oczekiwanego hosta - odrzuc zadanie z
         * nieznanego originu nawet gdyby (hipotetycznie) mial poprawny
         * token.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCsrfProtectionForMultipleForms {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz ochrone na 3 rozne endpointy modyfikujace stan
         * (`/change-email`, `/change-password`, `/delete-account`) -
         * kazdy wymaga TEGO SAMEGO tokenu z sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExemptSafeReadOnlyEndpointsFromCsrfCheck {
        /*
         * 🧪 Zadanie 15:
         * Zweryfikuj, ze endpointy TYLKO-DO-ODCZYTU (np. `/profile` GET)
         * NIE wymagaja CSRF tokenu (bo nie zmieniaja stanu) - wyjasnij
         * dlaczego w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LogCsrfViolationAttemptsForSecurityMonitoring {
        /*
         * 🧪 Zadanie 16:
         * Dodaj logowanie (na konsole) kazdej odrzuconej proby (brak/zly
         * CSRF token) z informacja o adresie IP i czasie - zapowiedz
         * Lesson19 (Secure Logging).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementPerFormUniqueTokenVsPerSessionToken {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj WARIANT z tokenem UNIKALNYM per formularz/zadanie
         * (nie tylko per sesja) - porownaj poziom ochrony i zlozonosc
         * wobec podejscia per-sesja z reszty lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestCsrfProtectionAgainstMultipleAttackerOrigins {
        /*
         * 🧪 Zadanie 18:
         * Zweryfikuj ochrone CSRF wobec KILKU roznych symulowanych
         * zlosliwych originow - upewnij sie, ze ZADEN nie przechodzi bez
         * poprawnego tokenu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementCsrfExemptionForJsonApiWithCustomHeader {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj (i wyjasnij w komentarzu) alternatywna ochrone -
         * wymaganie DOWOLNEGO, prostego niestandardowego naglowka (np.
         * `X-Requested-With: XMLHttpRequest`) jako "taniej" ochrony przed
         * prostymi atakami CSRF z formularzy HTML (ktore NIE MOGA ustawic
         * dowolnych naglowkow bez preflight z Lesson09).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareCsrfDefenseInDepthStrategies {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: porownaj (tabela w komentarzu) 3 warstwy ochrony
         * z tej lekcji (CSRF token, SameSite, walidacja Origin/Referer) -
         * ktora jest NAJSILNIEJSZA sama w sobie, a ktora najlepiej dziala
         * jako DODATEK.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildReusableCsrfProtectionMiddleware {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj reużywalny "filtr" CSRF (analogicznie do filtra CORS
         * z Lesson09, Zadanie 21) mozliwy do nalozenia na DOWOLNY
         * endpoint modyfikujacy stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateFullAttackChainCorsAndCsrfTogether {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj demo laczace CORS (Lesson09) i CSRF - pokaz, ze
         * POPRAWNA konfiguracja obu NIEZALEZNIE chroni przed roznymi
         * aspektami tego samego scenariusza ataku (odczyt odpowiedzi
         * przez JS INNEJ strony vs wykonanie akcji w Twoim imieniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCsrfTokenBoundToSpecificAction {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj token CSRF powiazany z KONKRETNA akcja (np.
         * "usun konto X") - token wygenerowany dla 1 akcji NIE dziala dla
         * innej, nawet w tej samej sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HandleCsrfInMultiTabBrowserScenario {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj uzytkownika z 2 "kartami przegladarki" (2 sekwencyjne
         * zadania z TA SAMA sesja) - zweryfikuj, ze rotacja tokenu
         * (Zadanie 12) NIE psuje dzialania drugiej "karty" w rozsadny
         * sposob (lub udokumentuj kompromis, jesli psuje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BenchmarkCsrfTokenValidationOverhead {
        /*
         * 🧪 Zadanie 25:
         * Zmierz narzut czasowy walidacji CSRF tokenu dla 100 000 zadan -
         * skomentuj, czy to realistyczne obciazenie dla produkcyjnego API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCsrfProtectionWithHmacSignedTokenNoServerState {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj BEZSTANOWY wariant CSRF tokenu - token to HMAC
         * (podpisany kluczem serwera) z identyfikatora sesji + znacznika
         * czasu, WERYFIKOWANY bez przechowywania go w Mapie (podobienstwo
         * do JWT z Lesson05).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TestCsrfResistanceOfJsonOnlyApiWithoutCookies {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj wariant API uzywajacy WYLACZNIE `Authorization: Bearer`
         * (bez ciastek) - zademonstruj, ze "podrobiony" formularz z
         * zlosliwej strony NIE MOZE dolaczyc tokenu (bo nie jest on
         * wysylany automatycznie jak ciastko) - potwierdz teze z Zadania
         * 10 kodem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AuditExistingEndpointsForMissingCsrfProtection {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode "audytujaca" liste endpointow (nazwa + metoda
         * HTTP + czy modyfikuje stan) i wskazujaca, ktore POWINNY, ale
         * NIE MAJA ochrony CSRF.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCsrfProtectionCompatibleWithMobileApp {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zbadaj i wyjasnij, dlaczego aplikacje mobilne
         * (bez przegladarki/ciastek) zazwyczaj NIE potrzebuja ochrony
         * CSRF w taki sam sposob jak aplikacje webowe - jak wtedy
         * powinno wygladac uwierzytelnianie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureFormSubmissionSystem {
        /*
         * 🧪 Zadanie 30:
         * Polacz CSRF token z rotacja (Zadanie 12), walidacje Origin
         * (Zadanie 13), logowanie naruszen (Zadanie 16) i SameSite
         * (Zadanie 9) w 1 spojne, wielowarstwowe demo - zweryfikuj co
         * najmniej 4 scenariusze z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
