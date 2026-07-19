package com.example.javaquest._27_spring_test.Lesson07_JsonTestForSerialization;

public class _Exercises_Lesson07_JsonTestForSerialization {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteJsonTestForSimpleRecord {
        /* 🧪 Zadanie 1: Napisz `@JsonTest` DLA WLASNEGO, prostego rekordu. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AssertJsonPathStringValue {
        /* 🧪 Zadanie 2: Zweryfikuj `extractingJsonPathStringValue`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ParseJsonBackToObject {
        /* 🧪 Zadanie 3: Uzyj `json.parseObject(...)` DO deserializacji. */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestRecordWithNestedObject {
        /* 🧪 Zadanie 4: Przetestuj rekord Z ZAGNIEZDZONYM obiektem. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyJsonTestIsFastestSlice {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO `@JsonTest` jest NAJSZYBSZYM wycinkiem. */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestListSerialization {
        /* 🧪 Zadanie 6: Przetestuj serializacje `List<Product>`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestJsonIgnoreAnnotationBehavior {
        /* 🧪 Zadanie 7: Powiaz z `_04_io/Lesson21` - przetestuj `@JsonIgnore` NA polu. */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestJsonPropertyRenaming {
        /* 🧪 Zadanie 8: Przetestuj `@JsonProperty("inna_nazwa")`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_AssertFullJsonEquality {
        /* 🧪 Zadanie 9: Uzyj `.isEqualToJson("...")` DO PELNEJ weryfikacji struktury. */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareJsonTestWithLesson21Gson {
        /* 🧪 Zadanie 10: Bez terminala - porownaj `@JsonTest` (Jackson) Z `_13_libraries/Lesson21` (MapStruct/Gson koncepcyjnie). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TestCustomSerializerForDateTime {
        /* 🧪 Zadanie 11: Powiaz z `_04_io/Lesson21` - przetestuj WLASNY serializer DLA `LocalDateTime`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestPolymorphicSerializationWithJsonTypeInfo {
        /* 🧪 Zadanie 12: Zbadaj `@JsonTypeInfo`/`@JsonSubTypes` DLA hierarchii klas. */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestErrorResponseDtoSerialization {
        /* 🧪 Zadanie 13: Powiaz z `_18_rest_api/Lesson12` - przetestuj serializacje DTO bledu RFC 7807. */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestJsonSchemaValidationOfOutput {
        /* 🧪 Zadanie 14: Powiaz z `_26_integration_testing/Lesson13` - zweryfikuj wyjscie WZGLEDEM kontraktu JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestOptionalFieldSerialization {
        /* 🧪 Zadanie 15: Przetestuj serializacje `Optional<String>` (`@JsonInclude`). */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestEnumSerializationAsString {
        /* 🧪 Zadanie 16: Powiaz z `_12_hibernate/Lesson10` - przetestuj serializacje enum jako String. */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestBigDecimalPrecisionInJson {
        /* 🧪 Zadanie 17: Przetestuj precyzje `BigDecimal` W serializacji JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CombineJsonTestWithWebMvcTestForFullCoverage {
        /* 🧪 Zadanie 18: Powiaz z Lesson05 - opisz, KIEDY warto MIEC OBA testy (JsonTest + WebMvcTest). */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestMalformedJsonHandling {
        /* 🧪 Zadanie 19: Przetestuj zachowanie PRZY ZLE SFORMATOWANYM JSON (wyjatek parsowania). */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullJsonContractTestSuiteForApiDtos {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet `@JsonTest` DLA WSZYSTKICH DTO publicznego API. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomJsonComponentAndTestIt {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `@JsonComponent` I przetestuj go `@JsonTest`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestBackwardCompatibleJsonSchemaEvolution {
        /* 🧪 Zadanie 22: Powiaz z `_18_rest_api/Lesson14` - przetestuj KOMPATYBILNOSC WSTECZNA formatu JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSnapshotTestingForJsonOutput {
        /* 🧪 Zadanie 23: Zaimplementuj "snapshot testing" (porownanie Z ZAPISANYM plikiem `.json`). */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestCircularReferenceHandlingInJson {
        /* 🧪 Zadanie 24: Przetestuj obsluge CYKLICZNYCH referencji (`@JsonManagedReference`/`@JsonBackReference`). */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildJsonFuzzingTestForRobustDeserialization {
        /* 🧪 Zadanie 25: Powiaz z `_25_unit_testing/Lesson17` - zbuduj "fuzzer" GENERUJACY ZEPSUTY JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestJsonViewsForDifferentApiConsumers {
        /* 🧪 Zadanie 26: Zbadaj `@JsonView` DO ROZNYCH "widokow" TEJ SAMEJ encji DLA ROZNYCH konsumentow. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementPerformanceBenchmarkForSerializationOfLargeObjectGraph {
        /* 🧪 Zadanie 27: Zmierz WYDAJNOSC serializacji DUZEGO grafu obiektow. */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestSecuritySensitiveFieldMaskingInJson {
        /* 🧪 Zadanie 28: Powiaz z `_19_security_basics/Lesson18` - przetestuj MASKOWANIE wrazliwych pol W JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementContractDrivenJsonTestFromOpenApiSpec {
        /* 🧪 Zadanie 29: Powiaz z `_18_rest_api/Lesson18` - wygeneruj test JSON NA PODSTAWIE specyfikacji OpenAPI. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullJsonSerializationTestingStandardForApiTeam {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard testowania serializacji JSON DLA zespolu API. */
        public static void main(String[] args) { }
    }
}
