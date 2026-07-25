package com.example.javaquest._31_spring_cloud_microservices.Lesson13_DeclarativeHttpClientsWithFeign;

public class _Exercises_Lesson13_DeclarativeHttpClientsWithFeign {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineFeignClientInterfaceWithGetMapping {
        /* 🧪 Zadanie 1: Zdefiniuj interfejs `@FeignClient` Z metoda `@GetMapping` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhyFeignNeedsNoMethodBody {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, DLACZEGO metody interfejsu Feign NIE MAJA ciala. */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddEnableFeignClientsToConfigurationClass {
        /* 🧪 Zadanie 3: Dodaj `@EnableFeignClients` DO klasy konfiguracji. */
        public static void main(String[] args) { }
    }

    static class Exercise04_CallFeignClientAndPrintResult {
        /* 🧪 Zadanie 4: Wywolaj klienta Feign I wypisz wynik. */
        public static void main(String[] args) { }
    }

    static class Exercise05_DefinePostMappingMethodOnFeignInterface {
        /* 🧪 Zadanie 5: Zdefiniuj metode `@PostMapping` NA interfejsie Feign (Z cialem zadania). */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainRelationshipBetweenFeignAndDynamicProxies {
        /* 🧪 Zadanie 6: Powiaz Z `_14_advancedjava/Lesson17` - wyjasnij RELACJE MIEDZY Feign A dynamic proxy. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ConfigureExplicitUrlViaProperty {
        /* 🧪 Zadanie 7: Skonfiguruj jawny `url` PRZEZ wlasciwosc (`${...}`), jak W lekcji. */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddPathVariableAndRequestParamToFeignMethod {
        /* 🧪 Zadanie 8: Dodaj `@PathVariable` I `@RequestParam` DO metody Feign. */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareFeignCallWithEquivalentRestTemplateCall {
        /* 🧪 Zadanie 9: Porownaj wywolanie Feign Z ROWNOWAZNYM wywolaniem `RestTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhatNameAttributeMeansInFeignClient {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, CO OZNACZA atrybut `name` W `@FeignClient`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DefineFeignClientReturningDtoInsteadOfString {
        /* 🧪 Zadanie 11: Zdefiniuj klienta Feign zwracajacego DTO (rekord), NIE `String`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_HandleFeignExceptionWhenBackendReturnsError {
        /* 🧪 Zadanie 12: Obsluz wyjatek Feign (`FeignException`), GDY backend zwraca blad (4xx/5xx). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureFeignClientTimeout {
        /* 🧪 Zadanie 13: Skonfiguruj timeout klienta Feign (`connect-timeout`/`read-timeout`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomErrorDecoderForFeignClient {
        /* 🧪 Zadanie 14: Zaimplementuj WLASNY `ErrorDecoder` DLA klienta Feign. */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddRequestInterceptorAddingCustomHeaderToAllFeignCalls {
        /* 🧪 Zadanie 15: Dodaj `RequestInterceptor` dopisujacy WLASNY naglowek DO WSZYSTKICH wywolan Feign. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CombineFeignWithCircuitBreakerFromLesson10 {
        /* 🧪 Zadanie 16: Polacz Feign Z circuit breakerem (`@CircuitBreaker` NA metodzie WOLAJACEJ klienta Feign, Lesson10). */
        public static void main(String[] args) { }
    }

    static class Exercise17_DefineFeignClientPointingToServiceNameInsteadOfExplicitUrl {
        /* 🧪 Zadanie 17: Zdefiniuj klienta Feign wskazujacego NA nazwe serwisu (BEZ jawnego `url`) - powiazanie Z Eureka+LoadBalancer. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConfigureFeignLoggingLevel {
        /* 🧪 Zadanie 18: Skonfiguruj poziom logowania Feign (`feign.client.config.*.logger-level`). */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementFallbackFactoryForFeignClient {
        /* 🧪 Zadanie 19: Zaimplementuj `FallbackFactory` DLA klienta Feign (fallback Z dostepem DO wyjatku). */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareFeignWithSpring6HttpInterfaceClients {
        /* 🧪 Zadanie 20: Zbadaj I porownaj Feign Z natywnymi `@HttpExchange` interfejsami Spring 6 (nowsza alternatywa). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomFeignEncoderForNonJsonBody {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `Encoder` Feign DLA cial INNYCH NIZ JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ConfigureFeignWithApacheHttpClientInsteadOfDefault {
        /* 🧪 Zadanie 22: Skonfiguruj Feign Z Apache HttpClient ZAMIAST domyslnego (`feign-httpclient`). */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementContractCustomizationForNonSpringAnnotations {
        /* 🧪 Zadanie 23: Zaimplementuj WLASNY `Contract` Feign (adnotacje INNE NIZ Springowe, np. JAX-RS). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignFeignClientVersioningStrategy {
        /* 🧪 Zadanie 24: Zaprojektuj strategie wersjonowania klientow Feign PRZY zmianie kontraktu API. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFeignClientWithMultipartFileUpload {
        /* 🧪 Zadanie 25: Zaimplementuj klienta Feign Z uploadem pliku (`multipart/form-data`). */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareFeignPerformanceWithWebClientUnderLoad {
        /* 🧪 Zadanie 26: Porownaj wydajnosc Feign Z `WebClient` POD OBCIAZENIEM. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCachingLayerOverFeignClientCalls {
        /* 🧪 Zadanie 27: Zaimplementuj warstwe cache'owania (Caffeine, `_13_libraries/Lesson27-28`) NAD wywolaniami Feign. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignContractTestingStrategyForFeignConsumers {
        /* 🧪 Zadanie 28: Zaprojektuj strategie contract testing DLA konsumentow Feign (powiazanie Z `_26_integration_testing/Lesson13`). */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFeignClientWithRetryAndBackoffPolicy {
        /* 🧪 Zadanie 29: Zaimplementuj polityke retry+backoff DLA klienta Feign (`Retryer`). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionFeignClientConfigurationChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" konfiguracji klientow Feign (timeouty/retry/circuit breaker/logowanie). */
        public static void main(String[] args) { }
    }
}
