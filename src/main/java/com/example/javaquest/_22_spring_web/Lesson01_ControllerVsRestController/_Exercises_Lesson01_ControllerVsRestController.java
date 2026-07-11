package com.example.javaquest._22_spring_web.Lesson01_ControllerVsRestController;

public class _Exercises_Lesson01_ControllerVsRestController {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainControllerVsRestControllerInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami roznice miedzy
         * `@Controller` a `@RestController`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnRestControllerReturningString {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY `@RestController` zwracajacy prosty
         * String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnRestControllerReturningRecord {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNY `@RestController` zwracajacy REKORD -
         * zweryfikuj serializacje do JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddResponseBodyToControllerMethod {
        /*
         * 🧪 Zadanie 4:
         * Uzyj `@Controller` + `@ResponseBody` NA POJEDYNCZEJ metodzie
         * (nie calej klasie) - dodaj DRUGA metode BEZ `@ResponseBody` w
         * TEJ SAMEJ klasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyJsonContentTypeHeader {
        /*
         * 🧪 Zadanie 5:
         * Sprawdz naglowek `Content-Type` odpowiedzi z `@RestController`
         * zwracajacego obiekt - jaka wartosc ma DOMYSLNIE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReturnListFromRestController {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj `@RestController` zwracajacy `List<T>` -
         * zweryfikuj serializacje jako TABLICA JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyRestControllerDoesNotNeedResponseBody {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij (reflekcja jak w
         * `_20_spring_core/Lesson08`), ze `@RestController` jest
         * META-OZNACZONE `@ResponseBody`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareControllerStereotypeWithServiceRepository {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: powiaz z `_20_spring_core/Lesson08` - jak
         * `@Controller`/`@RestController` MAJA sie do
         * `@Service`/`@Repository`?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementMultipleEndpointsInOneController {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj `@RestController` z 3+ endpointami (rozne
         * sciezki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenPlainControllerIsStillUseful {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: podaj scenariusz, w ktorym `@Controller` (BEZ
         * `@ResponseBody`, server-side rendering) MA SENS w REALNYM
         * projekcie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementControllerReturningResponseEntity {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj metode zwracajaca `ResponseEntity<T>` ZAMIAST
         * "gologo" obiektu - zapowiedz `Lesson06_ResponseEntity`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareRestControllerWithManualHttpServerFromChapter18 {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_18_rest_api` - zaimplementuj TEN SAM endpoint DWOMA
         * sposobami (surowy `HttpServer` vs `@RestController`) - porownaj
         * ILOSC kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementNestedObjectSerialization {
        /*
         * 🧪 Zadanie 13:
         * Zwroc obiekt z ZAGNIEZDZONYM INNYM obiektem/rekordem -
         * zweryfikuj poprawna, zagniezdzona serializacje JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExploreJacksonAutoConfigurationCustomization {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala (dokumentacja): sprawdz, jak dostosowac
         * auto-konfigurowany `ObjectMapper` (np. format daty) PRZEZ
         * `application.properties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementControllerHandlingMultipleHttpMethods {
        /*
         * 🧪 Zadanie 15:
         * Dodaj TA SAMA sciezke z RÓZNYMI metodami HTTP (GET/POST) W
         * TEJ SAMEJ klasie (zapowiedz `Lesson02_
         * RequestMappingGetPostPutDelete`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareControllerClassLevelRequestMapping {
        /*
         * 🧪 Zadanie 16:
         * Dodaj `@RequestMapping("/api")` NA POZIOMIE KLASY - zweryfikuj,
         * ze sciezki METOD sa WZGLEDNE wobec tego prefiksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementVoidReturningEndpoint {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj metode `void` (bez zwracanej wartosci) - jaki
         * status/cialo odpowiedzi otrzymujesz DOMYSLNIE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementPrimitiveReturnTypeSerialization {
        /*
         * 🧪 Zadanie 18:
         * Zwroc PROSTY typ (np. `int`/`boolean`) z `@RestController` -
         * zweryfikuj FORMAT odpowiedzi (JSON czy zwykly tekst?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementOptionalReturnTypeHandling {
        /*
         * 🧪 Zadanie 19:
         * Zwroc `Optional<T>` z metody kontrolera - zweryfikuj
         * zachowanie dla `Optional.empty()` vs wypelnionego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildControllerStyleComparisonTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza `@Controller`/
         * `@Controller+@ResponseBody`/`@RestController` - kolumny:
         * domyslne zachowanie zwracanej wartosci, typowe zastosowanie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomHttpMessageConverter {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `HttpMessageConverter` obslugujacy
         * NIESTANDARDOWY typ zwracany (np. CSV zamiast JSON) - zapowiedz
         * `Lesson11_ContentNegotiationAndMessageConverters`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementMixedControllerWithBothViewAndApiEndpoints {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj `@Controller` (NIE `@RestController`) Z
         * MIESZANYMI metodami - niektore zwracaja widoki, niektore MAJA
         * `@ResponseBody` - jeden kontroler, DWIE role.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AnalyzeRequestMappingHandlerMappingInternals {
        /*
         * 🧪 Zadanie 23:
         * Zbadaj (przez `context.getBean(RequestMappingHandlerMapping.class)`)
         * WSZYSTKIE zarejestrowane mapowania URL -> metoda - wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomReturnValueHandler {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala (zaawansowana dokumentacja): sprawdz
         * `HandlerMethodReturnValueHandler` - jak Spring MVC DECYDUJE,
         * jak obslugowac RÓZNE typy zwracanych wartosci?
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareJacksonWithGsonForSerialization {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_13_libraries/Lesson21` (Gson) - PODMIEN
         * auto-konfigurowanego Jacksona na Gson dla serializacji -
         * porownaj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementConditionalResponseFormatBasedOnRole {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj endpoint zwracajacy RÓZNY ksztalt odpowiedzi w
         * zaleznosci od "roli" (symulowanej naglowkiem) - zapowiedz
         * `_24_spring_security`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasureSerializationPerformanceForLargeObjects {
        /*
         * 🧪 Zadanie 27:
         * Zmierz czas serializacji DUZEGO obiektu (1000+ elementow
         * listy) - jaki jest narzut Jacksona?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementStreamingResponseForLargeDataset {
        /*
         * 🧪 Zadanie 28:
         * Zbadaj (dokumentacja) `StreamingResponseBody` - jak
         * STRUMIENIOWO wysylac DUZE odpowiedzi BEZ ladowania calosci do
         * pamieci?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareControllerTestingApproaches {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (zapowiedz przyszly rozdzial o testowaniu):
         * porownaj testowanie kontrolera przez `HttpClient` (jak w tej
         * lekcji) z `MockMvc` (dedykowane narzedzie Springa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteControllerStyleShowcaseCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo pokazujace WSZYSTKIE style kontrolerow z
         * tej lekcji DLA TEJ SAMEJ logiki biznesowej (5+ endpointow) -
         * jeden spojny raport porownawczy.
         */
        public static void main(String[] args) { }
    }
}
