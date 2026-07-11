package com.example.javaquest._22_spring_web.Lesson11_ContentNegotiationAndMessageConverters;

public class _Exercises_Lesson11_ContentNegotiationAndMessageConverters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainHttpMessageConverterRole {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, do czego sluzy `HttpMessageConverter`
         * i GDZIE dziala W CYKLU zycia zadania HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnEndpointWithProducesJson {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY endpoint Z `produces =
         * MediaType.APPLICATION_JSON_VALUE`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TriggerNotAcceptableWithMismatchedAccept {
        /*
         * 🧪 Zadanie 3:
         * Wyslij naglowek `Accept` NIEPASUJACY do `produces` - zapisz
         * DOKLADNY status (406).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementEndpointWithConsumesRestriction {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj WLASNY endpoint POST Z `consumes =
         * "application/json"`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TriggerUnsupportedMediaTypeWithMismatchedContentType {
        /*
         * 🧪 Zadanie 5:
         * Wyslij `Content-Type` NIEPASUJACY do `consumes` - zapisz
         * DOKLADNY status (415).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementMultipleProducesOnSameMethod {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `produces = {"application/json", "text/plain"}` - JEDNA
         * metoda OBSLUGUJACA WIELE formatow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementTwoEndpointsSamePathDifferentProduces {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj DWIE metody NA TEJ SAMEJ sciezce Z RÓZNYM
         * `produces` - zweryfikuj, ze Spring WYBIERA odpowiednia PO
         * naglowku `Accept` (NIE 'Ambiguous mapping').
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareDefaultJacksonConverterBehavior {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala (powiaz z `_21_spring_boot/Lesson04`):
         * wyjasnij, KIEDY I JAK Spring Boot AUTOMATYCZNIE rejestruje
         * `MappingJackson2HttpMessageConverter`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementStringHttpMessageConverterDemo {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj endpoint zwracajacy `String` Z `produces =
         * "text/plain"` - zweryfikuj, ze `StringHttpMessageConverter`
         * (WBUDOWANY) go obsluguje BEZ Jacksona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyExplicitProducesImprovesApiClarity {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (powiaz z `_18_rest_api/Lesson07`): wyjasnij,
         * dlaczego JAWNE `produces`/`consumes` POPRAWIA CZYTELNOSC I
         * BEZPIECZENSTWO kontraktu API.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCustomMessageConverterForOwnFormat {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNY `HttpMessageConverter` DLA WYMYSLONEGO
         * formatu (np. `text/vcard`-podobny) - zarejestruj przez
         * `WebMvcConfigurer`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementReadInternalForCustomConverter {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz `ProductCsvMessageConverter` Z teorii - dodaj
         * endpoint POST PRZYJMUJACY CSV I zweryfikuj `readInternal(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementConverterPriorityOrdering {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj DWA konwertery DLA TEGO SAMEGO typu MediaType -
         * zweryfikuj, KTORY Spring WYBIERA (kolejnosc rejestracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementJacksonCustomSerializerIntegration {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj WLASNY `JsonSerializer<T>` (Jackson) - powiaz Z
         * `_13_libraries` - zarejestruj GLOBALNIE PRZEZ `ObjectMapper`
         * bean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementContentNegotiationViaUrlSuffix {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala (dokumentacja): sprawdz HISTORYCZNY mechanizm
         * negocjacji PRZEZ SUFIKS URL (`/products.json`) - DLACZEGO
         * WSPOLCZESNE Spring GO ODRADZA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementContentNegotiationViaQueryParam {
        /*
         * 🧪 Zadanie 16:
         * Skonfiguruj negocjacje PRZEZ PARAMETR zapytania
         * (`?format=json`) ZAMIAST naglowka `Accept`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementByteArrayResponseWithCustomMediaType {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj endpoint zwracajacy `byte[]` Z WLASNYM
         * `MediaType` (np. `image/png`) - zweryfikuj naglowek
         * `Content-Type`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementResourceHttpMessageConverterDemo {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj endpoint zwracajacy `Resource`
         * (`ByteArrayResource`) - zweryfikuj automatyczna obsluge PRZEZ
         * `ResourceHttpMessageConverter`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureConverterSelectionOverhead {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas WYBORU konwertera PRZY 10+ ZAREJESTROWANYCH
         * konwerterach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildContentNegotiationCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" mechanizmow negocjacji tresci
         * (naglowek Accept/parametr/wbudowane konwertery/wlasny
         * konwerter).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementProtobufOrMessagePackConverterConceptually {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala (dokumentacja): sprawdz, JAK dodac obsluge
         * Protocol Buffers/MessagePack W Spring MVC PRZEZ WLASNY
         * `HttpMessageConverter`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementConditionalConverterBasedOnFeatureFlag {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_21_spring_boot/Lesson04` - zarejestruj WLASNY
         * konwerter WARUNKOWO (`@ConditionalOnProperty`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementStreamingCsvExportForLargeDataset {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz konwerter CSV O STRUMIENIOWY eksport DUZEGO zbioru
         * danych (bez buforowania calosci w pamieci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementContentNegotiationSecurityConsiderations {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics` - wyjasnij (i zademonstruj)
         * RYZYKO akceptowania "zbyt wielu" formatow wejsciowych
         * (powierzchnia ataku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCustomAcceptHeaderResolver {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WLASNY `ContentNegotiationStrategy` -
         * NIESTANDARDOWA logika wyboru formatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementBackwardCompatibleFormatMigration {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_18_rest_api/Lesson14` - zaimplementuj MIGRACJE Z
         * XML NA JSON (OBSLUGA obu formatow rownolegle, PRZEZ okres
         * przejsciowy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementConverterErrorHandlingForMalformedCsv {
        /*
         * 🧪 Zadanie 27:
         * Rozszerz `readInternal(...)` WLASNEGO konwertera O OBSLUGE
         * BLEDOW (zle sformatowany CSV) - zwroc CZYTELNY 400.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementPerEndpointConverterCustomization {
        /*
         * 🧪 Zadanie 28:
         * Skonfiguruj RÓZNE `ObjectMapper` (np. RÓZNE strategie
         * nazewnictwa pol) DLA RÓZNYCH GRUP endpointow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareMessageConverterApproachAcrossFrameworks {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj mechanizm `HttpMessageConverter`
         * (Spring) Z PODOBNYMI mechanizmami W innych frameworkach
         * (Jakarta REST `MessageBodyWriter`, ASP.NET formatters).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMultiFormatApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNE mini-API OBSLUGUJACE 3+ formaty
         * (JSON/CSV/text) DLA TEGO SAMEGO zasobu - jeden spojny,
         * dzialajacy system negocjacji tresci.
         */
        public static void main(String[] args) { }
    }
}
