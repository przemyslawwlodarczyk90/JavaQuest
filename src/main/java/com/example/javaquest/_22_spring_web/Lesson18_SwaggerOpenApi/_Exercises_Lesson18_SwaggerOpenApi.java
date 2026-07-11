package com.example.javaquest._22_spring_web.Lesson18_SwaggerOpenApi;

public class _Exercises_Lesson18_SwaggerOpenApi {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSpringdocAutomationBenefit {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_18_rest_api/Lesson18`): wyjasnij,
         * CO springdoc AUTOMATYZUJE wzgledem RECZNEGO pisania YAML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnControllerAndInspectApiDocs {
        /*
         * 🧪 Zadanie 2:
         * Dodaj WLASNY kontroler - sprawdz, CZY pojawil sie
         * automatycznie W `/v3/api-docs`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CustomizeOpenApiInfoBean {
        /*
         * 🧪 Zadanie 3:
         * Dostosuj bean `OpenAPI` - WLASNY tytul/wersja/opis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InspectSwaggerUiPageStatus {
        /*
         * 🧪 Zadanie 4:
         * Zweryfikuj status `/swagger-ui/index.html` PO dodaniu
         * springdoc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddOperationSummaryAnnotation {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `@Operation(summary = "...")` (adnotacja springdoc) NA
         * WLASNA metode kontrolera - zweryfikuj JEJ obecnosc W JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddParameterDescriptionAnnotation {
        /*
         * 🧪 Zadanie 6:
         * Dodaj `@Parameter(description = "...")` NA `@PathVariable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AddApiResponsesAnnotation {
        /*
         * 🧪 Zadanie 7:
         * Dodaj `@ApiResponses` DOKUMENTUJACA MOZLIWE kody statusu
         * (200/404) DLA WLASNEGO endpointu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareManualYamlWithGeneratedJson {
        /*
         * 🧪 Zadanie 8:
         * Powiaz z `_18_rest_api/Lesson18` - zestaw obok siebie RECZNY
         * YAML I WYGENEROWANY JSON DLA TEGO SAMEGO endpointu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementSchemaAnnotationOnDto {
        /*
         * 🧪 Zadanie 9:
         * Dodaj `@Schema(description = "...")` NA polach WLASNEGO
         * rekordu DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhySpringdocNeedsRunningApplication {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, DLACZEGO springdoc WYMAGA
         * URUCHOMIONEJ aplikacji (W ODROZNIENIU od statycznej generacji
         * kodu jak `openapi-generator`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementTagGroupingForControllers {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@Tag(name = "...")` DO GRUPOWANIA endpointow W Swagger
         * UI (np. "Zadania" vs "Uzytkownicy").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementHiddenEndpointFromDocs {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@Hidden` - UKRYJ WYBRANY endpoint PRZED dokumentacja
         * (np. wewnetrzny endpoint diagnostyczny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementSecuritySchemeDocumentation {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_24_spring_security` (zapowiedz) - udokumentuj
         * WYMAGANY naglowek `Authorization: Bearer <token>` PRZEZ
         * `@SecurityScheme`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementGroupedApiForPublicVsAdmin {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj `GroupedOpenApi` - ROZDZIEL dokumentacje NA
         * "publiczna" I "administracyjna" GRUPE endpointow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementExampleValuesInDocs {
        /*
         * 🧪 Zadanie 15:
         * Dodaj PRZYKLADOWE wartosci (`@Schema(example = "...")`) DO
         * pol WLASNEGO DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCustomServersListInDocs {
        /*
         * 🧪 Zadanie 16:
         * Skonfiguruj liste `servers` (np. "dev"/"prod") W beanie
         * `OpenAPI`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementValidationConstraintsReflectedInSchema {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z Lesson08 - zweryfikuj, CZY adnotacje Bean Validation
         * (`@NotBlank`/`@Size`) SA AUTOMATYCZNIE ODZWIERCIEDLONE W
         * wygenerowanym schemacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareSpringdocWithManualReflectiveGenerator {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_18_rest_api/Lesson18` (RECZNY generator schematu
         * przez refleksje) - porownaj Z springdoc - jakie SA
         * OGRANICZENIA WLASNEGO rozwiazania?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureStartupOverheadWithSpringdoc {
        /*
         * 🧪 Zadanie 19:
         * Zmierz DODATKOWY czas STARTU aplikacji Z springdoc wzgledem
         * BEZ niego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildSpringdocAnnotationsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" adnotacji springdoc
         * (`@Operation`/`@Parameter`/`@ApiResponses`/`@Schema`/`@Tag`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomOpenApiCustomizerBean {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY bean `OpenApiCustomizer` - PROGRAMOWA
         * modyfikacja WYGENEROWANEJ specyfikacji PRZED jej zwroceniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementOperationCustomizerForConditionalDocs {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNY `OperationCustomizer` - DODAJ metadane
         * WARUNKOWO (np. NA PODSTAWIE adnotacji WLASNEGO typu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementApiDocsExportToFile {
        /*
         * 🧪 Zadanie 23:
         * Pobierz `/v3/api-docs` I zapisz DO PLIKU - powiaz Z Lesson17
         * `_18_rest_api` (Postman) - zaimportuj DO Postmana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementContractTestingAgainstGeneratedSpec {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj (opisowo) test SPRAWDZAJACY, ze WYGENEROWANA
         * specyfikacja NIE ZMIENILA SIE W SPOSOB LAMIACY kompatybilnosc
         * (powiaz z `_18_rest_api/Lesson14_Versioning`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementMultiModuleApiAggregation {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala (dokumentacja): sprawdz, JAK springdoc
         * AGREGUJE dokumentacje Z WIELU MODULOW/mikroserwisow (SpringDoc
         * + gateway).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementApiVersioningInSwaggerDocs {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z Lesson02/`_18_rest_api/Lesson14` - udokumentuj 2
         * WERSJE TEGO SAMEGO endpointu (`/api/v1/...`,
         * `/api/v2/...`) OSOBNO W Swagger UI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCodeGenerationFromApiDocs {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala (dokumentacja): sprawdz, JAK WYGENEROWAC KLIENTA
         * (np. TypeScript/Java) Z `/v3/api-docs` PRZEZ OpenAPI
         * Generator.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementApiDocsAccessRestriction {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_24_spring_security` (zapowiedz) - zabezpiecz
         * DOSTEP DO `/swagger-ui/**`/`/v3/api-docs` TYLKO DLA
         * zalogowanych administratorow W SRODOWISKU produkcyjnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareSpringdocWithAlternativeDocsTools {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj springdoc Z alternatywami (Redoc,
         * Stoplight) - jakie SA RÓZNICE prezentacji/funkcji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteDocumentedApiCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNE, W PELNI udokumentowane mini-API - tagi
         * (Zadanie 11) + przyklady (Zadanie 15) + walidacja
         * odzwierciedlona (Zadanie 17) + security scheme (Zadanie 13) -
         * jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
