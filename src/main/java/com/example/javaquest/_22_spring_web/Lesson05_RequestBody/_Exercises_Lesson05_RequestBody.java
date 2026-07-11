package com.example.javaquest._22_spring_web.Lesson05_RequestBody;

public class _Exercises_Lesson05_RequestBody {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRequestBodyPurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, do czego sluzy `@RequestBody` i CZYM
         * rozni sie od `@RequestParam`/`@PathVariable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnRequestBodyEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY endpoint POST z `@RequestBody` (rekord
         * z 2+ polami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementNestedObjectInRequestBody {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj `@RequestBody` z ZAGNIEZDZONYM obiektem
         * (rekord w rekordzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementListOfObjectsRequestBody {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `@RequestBody List<T>` gdzie `T` to WLASNY
         * rekord (nie `String`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TriggerMalformedJsonError {
        /*
         * 🧪 Zadanie 5:
         * Wyslij ZLAMANY JSON - zapisz DOKLADNY status i komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementUnknownFieldHandling {
        /*
         * 🧪 Zadanie 6:
         * Wyslij JSON z DODATKOWYM polem, KTOREGO NIE MA w rekordzie -
         * zweryfikuj DOMYSLNE zachowanie Jacksona (ignorowanie czy blad?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementMissingFieldHandling {
        /*
         * 🧪 Zadanie 7:
         * Wyslij JSON BEZ jednego pola rekordu - zweryfikuj, JAKA wartosc
         * dostaje pole (null/0/false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementEmptyBodyHandling {
        /*
         * 🧪 Zadanie 8:
         * Wyslij PUSTE cialo zadania (bez JSON) - zapisz status i
         * komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareRequestBodyWithFormUrlEncoded {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: porownaj `@RequestBody` (JSON) z tradycyjnymi
         * danymi formularza (`application/x-www-form-urlencoded`,
         * `@RequestParam`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyRequestBodyNeedsPostPutPatch {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego `@RequestBody` typowo idzie
         * w parze z POST/PUT/PATCH, NIE z GET.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementListOfNestedObjectsRequestBody {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `@RequestBody` z LISTA ZAGNIEZDZONYCH obiektow
         * (np. zamowienie z lista pozycji, kazda pozycja to rekord).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomJsonDeserializer {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj WLASNY `JsonDeserializer<T>` (Jackson) dla
         * NIETYPOWEGO formatu pola (np. data w formacie "DD.MM.RRRR").
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementRequestBodyWithEnumField {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj rekord z polem typu `enum` w `@RequestBody` -
         * zweryfikuj zachowanie dla NIEPRAWIDLOWEJ wartosci enum w JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRequestBodyWithLocalDateField {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj rekord z polem `LocalDate`/`LocalDateTime` w
         * `@RequestBody` - zweryfikuj DOMYSLNY format ISO obslugiwany
         * przez Jacksona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareRequestBodyWithHttpEntity {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala (dokumentacja): sprawdz roznice miedzy
         * `@RequestBody T` a `HttpEntity<T>` (parametr metody) - co
         * daje DODATKOWO `HttpEntity`?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRawStringRequestBody {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj endpoint przyjmujacy `@RequestBody String` (SUROWY
         * tekst, BEZ deserializacji JSON) - kiedy to przydatne?
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementByteArrayRequestBody {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj endpoint przyjmujacy `@RequestBody byte[]`
         * (surowe dane binarne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementJsonNodeGenericRequestBody {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj `@RequestBody JsonNode` (Jackson) - przetwarzaj
         * JSON o NIEZNANEJ Z GORY strukturze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureLargeRequestBodyParsingTime {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas deserializacji DUZEGO JSON (1000+ elementow listy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildRequestBodyPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" wzorcow `@RequestBody` z tej lekcji
         * (rekord/zagniezdzony/lista/String/byte[]/JsonNode).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomHttpMessageConverter {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `HttpMessageConverter` (np. dla formatu
         * CSV zamiast JSON) - zarejestruj go dla `@RequestBody`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDeepValidationOfNestedRequestBody {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z Lesson08 (`@Valid`) - zaimplementuj walidacje
         * ZAGNIEZDZONEGO obiektu W `@RequestBody` (`@Valid` na polu
         * zagniezdzonym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementRequestBodySizeLimit {
        /*
         * 🧪 Zadanie 23:
         * Skonfiguruj LIMIT rozmiaru ciala zadania - wyslij CIALO
         * PRZEKRACZAJACE limit i zweryfikuj odrzucenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRequestBodySanitizationBeforeProcessing {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_19_security_basics/Lesson11_Xss` - zaimplementuj
         * SANITYZACJE pol tekstowych z `@RequestBody` PRZED zapisem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementPolymorphicRequestBodyWithJacksonTypeInfo {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj POLIMORFICZNY `@RequestBody` (`@JsonTypeInfo` +
         * `@JsonSubTypes`) - JEDEN endpoint przyjmujacy RÓZNE podtypy
         * zdarzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementIdempotentRequestBodyProcessing {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_18_rest_api/Lesson15_Idempotency` - zaimplementuj
         * OBSLUGE `@RequestBody` Z kluczem idempotencji (odrzuc
         * DUPLIKAT tego samego ciala).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRequestBodyAuditLoggingWithoutSensitiveData {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj (z
         * SANITYZACJA - bez hasel/danych wrazliwych) TRESC KAZDEGO
         * `@RequestBody` do dziennika audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementStreamingLargeRequestBody {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj STRUMIENIOWE przetwarzanie DUZEGO `@RequestBody`
         * (np. `InputStream` zamiast pelnej deserializacji do pamieci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareRequestBodyDeserializationAcrossFormats {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj deserializacje `@RequestBody` dla
         * JSON vs XML (`_06_networking/Lesson13`) vs Protocol Buffers
         * (koncepcyjnie) - JAKI format i KIEDY?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteRequestBodyProcessingPipelineCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "pipeline" przetwarzania `@RequestBody` -
         * walidacja (Zadanie 22) + sanityzacja (Zadanie 24) + audyt
         * (Zadanie 27) + limit rozmiaru (Zadanie 23) - jeden spojny,
         * dzialajacy endpoint.
         */
        public static void main(String[] args) { }
    }
}
