package com.example.javaquest._18_rest_api.Lesson18_OpenApiSwaggerIntro;

public class _Exercises_Lesson18_OpenApiSwaggerIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainOpenApiVsSwaggerDifference {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij roznice miedzy OpenAPI (standard) a
         * Swagger (ekosystem narzedzi) - dlaczego czesto uzywane sa
         * zamiennie mimo tej roznicy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteMinimalOpenApiSpecForOneEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Napisz (jako text block) minimalna specyfikacje OpenAPI 3.0
         * (info+servers+paths) dla 1 endpointu GET /health zwracajacego
         * 200 - wypisz ja na konsole.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddSecondEndpointToSpec {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz specyfikacje z Zadania 2 o 2. endpoint (np. POST
         * /events) - zweryfikuj poprawnosc wciec YAML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DefineSchemaComponentForDto {
        /*
         * 🧪 Zadanie 4:
         * Dodaj sekcje "components/schemas" definiujaca ksztalt prostego
         * DTO (np. Event: id, name, date) - uzyj `$ref` w odpowiedzi endpointu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ListFourMainSectionsOfOpenApiDoc {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wymien 4 glowne sekcje dokumentu OpenAPI (info,
         * servers, paths, components) - po 1 zdaniu przeznaczenia kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_GenerateSchemaFromSimpleRecordViaReflection {
        /*
         * 🧪 Zadanie 6:
         * Zdefiniuj wlasny rekord (min. 3 pola roznych typow) i uzyj
         * metody `generateOpenApiSchema` (wzorem teorii) do
         * wygenerowania mapy pol->typ OpenAPI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MapAllJavaPrimitiveTypesToOpenApiTypes {
        /*
         * 🧪 Zadanie 7:
         * Rozszerz mapowanie typow o wszystkie prymitywy Java (byte, short,
         * char) - zweryfikuj poprawne mapowanie na typy OpenAPI
         * (integer/number/string).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddPathParameterToSpec {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do specyfikacji endpoint z path variable (np. GET
         * /events/{id}) - zdefiniuj parametr w sekcji "parameters" z
         * poprawnym typem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddErrorResponseToSpec {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do endpointu z Zadania 8 mozliwa odpowiedz '404' obok
         * '200' - nawiazanie do Lesson05/12 (kody statusu, ksztalt bledu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhySwaggerUiIsUseful {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego interaktywna dokumentacja
         * (Swagger UI) jest wartosciowa dla zespolu frontendowego/
         * zewnetrznych integratorow API w porownaniu do statycznego pliku PDF.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenerateFullSpecForLesson04Endpoints {
        /*
         * 🧪 Zadanie 11:
         * Napisz PELNA specyfikacje OpenAPI dla WSZYSTKICH endpointow z
         * `Lesson04_HttpMethods` (GET/POST/PUT/PATCH/DELETE na /books) -
         * wlacznie z schema komponentu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementReusableParameterDefinition {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj sekcje "components/parameters" z WIELOKROTNEGO
         * uzytku definicja parametru "id" - uzyj jej (`$ref`) w min. 2
         * roznych endpointach zamiast powielac definicje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DocumentQueryParametersFromLesson09 {
        /*
         * 🧪 Zadanie 13:
         * Udokumentuj w OpenAPI parametry query z `Lesson09` (np.
         * "category", "offset", "limit") - wlacznie z typami i wartosciami
         * domyslnymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_GenerateSchemaForNestedRecord {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz generator schematu (Zadanie 6) o obsluge ZAGNIEZDZONYCH
         * rekordow (np. Order zawiera Customer) - wygeneruj
         * ZAGNIEZDZONY schemat OpenAPI (obiekt w obiekcie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DocumentAuthenticationRequirement {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do specyfikacji sekcje "components/securitySchemes"
         * opisujaca uwierzytelnianie Bearer token - oznacz 1 endpoint
         * jako wymagajacy autoryzacji (foreshadowing `_19_security_basics`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ValidateGeneratedYamlStructureManually {
        /*
         * 🧪 Zadanie 16:
         * Napisz prosty walidator sprawdzajacy, czy wygenerowany string
         * YAML zawiera WSZYSTKIE wymagane sekcje najwyzszego poziomu
         * (openapi, info, paths) - zwroc liste brakujacych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DocumentMultipleServersForDifferentEnvironments {
        /*
         * 🧪 Zadanie 17:
         * Dodaj do sekcji "servers" WIELE wpisow (dev/staging/prod, z
         * opisami) - nawiazanie do srodowisk Postmana (Lesson17).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GenerateSpecFromMultipleRecordsAutomatically {
        /*
         * 🧪 Zadanie 18:
         * Rozszerz generator (Zadanie 6) tak, zeby przyjmowal LISTE klas
         * rekordow i generowal PELNA sekcje "components/schemas" dla
         * WSZYSTKICH naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DocumentPaginationMetadataSchema {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj w OpenAPI schemat "koperty" ze stronicowaniem
         * (Lesson10: data+meta) jako WIELOKROTNEGO uzytku komponent -
         * uzyj go w odpowiedzi endpointu kolekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareManualSpecWithReflectionGeneratedSpec {
        /*
         * 🧪 Zadanie 20:
         * Porownaj recznie napisana specyfikacje (Zadanie 11) z
         * WYGENEROWANA automatycznie przez reflection (Zadanie 6/18) -
         * w komentarzu opisz zalety/wady kazdego podejscia.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullSpecGeneratorFromAnnotatedMethods {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj WLASNE adnotacje (np. `@ApiEndpoint(path=...,
         * method=...)`) i uzyj refleksji do zbudowania AUTOMATYCZNIE
         * pelnej specyfikacji OpenAPI z oznaczonych metod klasy - to
         * uproszczona wersja tego, co robi springdoc w Spring Boot.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementYamlToJsonConversion {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj (recznie lub uzywajac Gson/istniejacych
         * narzedzi kursu) konwersje wygenerowanej specyfikacji z formatu
         * YAML na JSON - OpenAPI wspiera OBA formaty zamiennie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ValidateSpecAgainstRealOpenApiSchema {
        /*
         * 🧪 Zadanie 23:
         * Zapisz wygenerowana specyfikacje do pliku i (recznie, poza
         * kodem) zweryfikuj ja w publicznym walidatorze Swagger Editor
         * (edytor online) - opisz w komentarzu napotkane bledy/ostrzezenia,
         * jesli byly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_GenerateClientCodeFromSpecConceptually {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: opisz (na podstawie dokumentacji openapi-generator),
         * jak z 1 pliku specyfikacji YAML mozna wygenerowac GOTOWY kod
         * klienta HTTP w wielu jezykach - jaka to daje przewage nad
         * recznym pisaniem `HttpClient` dla kazdego jezyka osobno?
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementSpecDiffToolForBreakingChangeDetection {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj narzedzie porownujace 2 wersje specyfikacji
         * OpenAPI (np. sparsowane Mapy pol) i wykrywajace BREAKING
         * changes (Lesson14) - usuniete endpointy/pola, zmiana typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementContractTestingAgainstSpec {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj "test kontraktu" - dla dzialajacego lokalnego
         * serwera (np. z Lesson04) zweryfikuj, ze RZECZYWISTE odpowiedzi
         * (status/Content-Type) sa ZGODNE z tym, co deklaruje specyfikacja
         * OpenAPI - zglos rozbieznosc, jesli wystapi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DocumentComplexOneOfSchemaForPolymorphicResponse {
        /*
         * 🧪 Zadanie 27:
         * Udokumentuj w OpenAPI odpowiedz POLIMORFICZNA (np.
         * powiadomienie email/sms z Lesson08 Zadanie 23) uzywajac
         * slowa kluczowego "oneOf" i "discriminator".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementSpecVersioningAlongsideApiVersioning {
        /*
         * 🧪 Zadanie 28:
         * Wygeneruj OSOBNE specyfikacje OpenAPI dla v1 i v2 API z
         * Lesson14 - zweryfikuj, ze KAZDA poprawnie opisuje SWOJ ksztalt
         * odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementSpecGenerationPipelineAsPartOfBuild {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj (opisz kroki, nawiazanie do `_11_buildtools`)
         * automatyczne generowanie i publikowanie specyfikacji OpenAPI
         * jako czesc procesu budowania - kiedy w cyklu zycia builda
         * powinno to sie dziac?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSpecForAllChapter18Endpoints {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNA specyfikacje OpenAPI (recznie LUB przez
         * generator reflection) obejmujaca WSZYSTKIE reprezentatywne
         * endpointy z tego rozdzialu (min. 5 zasobow z roznych lekcji) -
         * wlacznie z schematami bledow (Lesson12), wersjonowaniem
         * (Lesson14) i stronicowaniem (Lesson10).
         */
        public static void main(String[] args) { }
    }
}
