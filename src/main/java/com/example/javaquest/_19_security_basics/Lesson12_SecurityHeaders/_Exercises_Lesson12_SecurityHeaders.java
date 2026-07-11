package com.example.javaquest._19_security_basics.Lesson12_SecurityHeaders;

public class _Exercises_Lesson12_SecurityHeaders {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListFiveSecurityHeadersFromMemory {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz z pamieci 5 naglowkow bezpieczenstwa
         * omawianych w tej lekcji i jednym zdaniem opisz kazdy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddContentSecurityPolicyHeader {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` dodajacy `Content-Security-Policy:
         * default-src 'self'` do kazdej odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddXFrameOptionsHeader {
        /*
         * 🧪 Zadanie 3:
         * Dodaj `X-Frame-Options: DENY` - zweryfikuj obecnosc naglowka w
         * odpowiedzi klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddHstsHeader {
        /*
         * 🧪 Zadanie 4:
         * Dodaj `Strict-Transport-Security: max-age=31536000` -
         * zweryfikuj obecnosc w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddNosniffHeader {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `X-Content-Type-Options: nosniff` - zweryfikuj obecnosc w
         * odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareResponsesWithAndWithoutHeaders {
        /*
         * 🧪 Zadanie 6:
         * Wykonaj to samo zadanie do serwera Z i BEZ naglowkow - wypisz
         * roznice obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainClickjackingInOwnWords {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij wlasnymi slowami, czym jest
         * "clickjacking" i jak `X-Frame-Options` przed nim chroni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainHstsProtectionAgainstSslStripping {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, jak HSTS chroni przed atakiem "SSL
         * stripping" (atakujacy przechwytuje pierwsze, jawne zadanie
         * HTTP przed przekierowaniem na HTTPS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddReferrerPolicyHeader {
        /*
         * 🧪 Zadanie 9:
         * Dodaj `Referrer-Policy: no-referrer` - wyjasnij w komentarzu,
         * jakie dane normalnie wyciekaja bez tego naglowka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CentralizeHeadersInOneHelperMethod {
        /*
         * 🧪 Zadanie 10:
         * Zrefaktoryzuj dodawanie WSZYSTKICH naglowkow z tej lekcji do 1
         * metody `addSecurityHeaders(HttpExchange)` wywolywanej na
         * poczatku kazdego handlera.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_BuildStrictCspWithMultipleDirectives {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj bardziej rozbudowana CSP z kilkoma dyrektywami (`script-src`,
         * `style-src`, `img-src`, `connect-src`) - wyjasnij w komentarzu
         * kazda z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCspReportOnlyMode {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj wariant `Content-Security-Policy-Report-Only` -
         * wyjasnij w komentarzu, jak pozwala TESTOWAC polityke bez
         * BLOKOWANIA niczego (tylko raportowanie naruszen).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementFrameAncestorsInsteadOfXFrameOptions {
        /*
         * 🧪 Zadanie 13:
         * Zastap `X-Frame-Options` nowoczesniejsza dyrektywa CSP
         * `frame-ancestors 'none'` - wyjasnij w komentarzu, dlaczego CSP
         * jest bardziej elastyczne (np. `frame-ancestors 'self'
         * https://zaufany.pl` pozwala na WIELE konkretnych domen, czego
         * `X-Frame-Options` nie potrafi).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementPermissionsPolicyHeader {
        /*
         * 🧪 Zadanie 14:
         * Dodaj naglowek `Permissions-Policy` ograniczajacy dostep do API
         * przegladarki (np. `camera=(), microphone=(), geolocation=()`) -
         * wyjasnij jego cel.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestHeadersAgainstDifferentEndpointTypes {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj rozne polityki dla roznych typow endpointow (np.
         * strona HTML dostaje pelny CSP, endpoint API zwracajacy JSON
         * dostaje inny, prostszy zestaw naglowkow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementNonceBasedCspForInlineScripts {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj CSP z `nonce` (losowa wartosc per zadanie,
         * dopasowana do atrybutu `nonce` na tagu `<script>`) - pozwala na
         * KONKRETNY inline script bez rozluzniania calej polityki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_AuditMissingSecurityHeadersAcrossEndpoints {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode wysylajaca zadania do listy endpointow i
         * raportujaca, KTORYCH naglowkow bezpieczenstwa brakuje w
         * odpowiedzi kazdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCoopAndCoepHeaders {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: zbadaj i opisz `Cross-Origin-Opener-Policy` i
         * `Cross-Origin-Embedder-Policy` - jaki dodatkowy poziom
         * izolacji originow wprowadzaja (potrzebne np. dla
         * `SharedArrayBuffer`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareHeaderBasedDefenseWithCodeLevelDefense {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: porownaj (tabela w komentarzu) ochrone przez
         * naglowki (ta lekcja) z ochrona na poziomie kodu (escapowanie -
         * Lesson11, CSRF token - Lesson10) - dlaczego OBIE warstwy sa
         * potrzebne razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementSecurityHeadersMiddlewareForAllRoutes {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj "filtr" (`com.sun.net.httpserver.Filter`)
         * dodajacy WSZYSTKIE naglowki bezpieczenstwa automatycznie do
         * KAZDEGO endpointu zarejestrowanego na serwerze, bez
         * modyfikowania handlerow.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildCspViolationReportingEndpoint {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj endpoint `/csp-report` przyjmujacy raporty
         * naruszen CSP (`report-uri`/`report-to`) - wypisz i "zaloguj"
         * kazdy odebrany raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestClickjackingScenarioConceptually {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj (koncepcyjnie w komentarzu + prostym kodzie HTML jako
         * text block) przyklad zlosliwej strony z niewidocznym `<iframe>`
         * nad przyciskiem - wyjasnij, jak `frame-ancestors 'none'` by to
         * zablokowal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementProgressiveCspRolloutStrategy {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj strategie WDROZENIA restrykcyjnej CSP na istniejacej
         * aplikacji: najpierw `Report-Only` (Zadanie 12), analiza
         * raportow naruszen, dopiero potem WLACZENIE trybu blokujacego -
         * wyjasnij, dlaczego "od razu na twardo" jest ryzykowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkHeaderProcessingOverhead {
        /*
         * 🧪 Zadanie 24:
         * Zmierz narzut czasowy dodawania 6-7 naglowkow bezpieczenstwa do
         * 100 000 odpowiedzi - skomentuj, czy to ma ZNACZACY wplyw na
         * wydajnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDifferentPoliciesForDifferentEnvironments {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj rozne (luzniejsze na dev, scisle na produkcji)
         * polityki CSP zalezne od "srodowiska" (symulowana zmienna
         * `Environment.DEV`/`Environment.PROD`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AuditThirdPartyScriptSourcesInCsp {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj CSP dopuszczajaca konkretne, ZAUFANE domeny
         * trzecie (np. CDN dla bibliotek JS) w `script-src` - wyjasnij,
         * dlaczego KAZDA dodana domena ROZSZERZA "powierzchnie ataku".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSubresourceIntegrityAlongsideCsp {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: zbadaj i opisz Subresource Integrity (SRI,
         * atrybut `integrity` na `<script>`/`<link>`) - jak WSPOLPRACUJE
         * z CSP, chroniac przed podmieniona zawartoscia zaufanego CDN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildAutomatedHeaderComplianceCheck {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj automatyczny "test zgodnosci" sprawdzajacy wszystkie
         * endpointy aplikacji z tej lekcji pod katem WYMAGANEGO minimum
         * naglowkow bezpieczenstwa - wypisz PASS/FAIL per endpoint.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareRealWorldCspExamplesFromMajorSites {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zbadaj (z pamieci/wiedzy ogolnej), jak wygladaja
         * uproszczone przyklady CSP duzych serwisow (np. GitHuba) -
         * jakie kompromisy (np. `unsafe-inline` dla starszego kodu) czasem
         * musza zaakceptowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteHardenedServerWithAllHeaders {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, "utwardzony" serwer laczacy WSZYSTKIE
         * naglowki z tej lekcji (CSP z nonce, frame-ancestors, HSTS,
         * nosniff, referrer-policy, permissions-policy) + filtr
         * centralny (Zadanie 20) + endpoint raportowania (Zadanie 21) -
         * zweryfikuj kompletnosc automatycznym testem zgodnosci
         * (Zadanie 28).
         */
        public static void main(String[] args) { }
    }
}
