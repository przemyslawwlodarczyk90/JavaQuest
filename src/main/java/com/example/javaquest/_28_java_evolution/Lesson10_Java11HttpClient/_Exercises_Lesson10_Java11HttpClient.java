package com.example.javaquest._28_java_evolution.Lesson10_Java11HttpClient;

public class _Exercises_Lesson10_Java11HttpClient {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SendSimpleGetRequestWithHttpClient {
        /* 🧪 Zadanie 1: Wyslij PROSTE zadanie GET uzywajac `HttpClient.newHttpClient()`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_SendSameRequestWithOldHttpUrlConnection {
        /* 🧪 Zadanie 2: Wyslij TO SAMO zadanie uzywajac STAREGO `HttpURLConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareLinesOfCodeBetweenOldAndNewApi {
        /* 🧪 Zadanie 3: Porownaj ILOSC linii kodu POTRZEBNYCH obiema metodami DLA tego samego zadania. */
        public static void main(String[] args) { }
    }

    static class Exercise04_BuildRequestWithCustomHeaders {
        /* 🧪 Zadanie 4: Zbuduj zadanie Z WLASNYMI naglowkami uzywajac `HttpRequest.newBuilder()`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_SetConnectTimeoutOnHttpClient {
        /* 🧪 Zadanie 5: Ustaw timeout POLACZENIA NA `HttpClient.newBuilder()`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReadResponseStatusCodeAndHeaders {
        /* 🧪 Zadanie 6: Odczytaj kod statusu I naglowki Z `HttpResponse`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_SendPostRequestWithJsonBody {
        /* 🧪 Zadanie 7: Wyslij zadanie POST Z cialem JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseBodyHandlerOfStringVsOfByteArray {
        /* 🧪 Zadanie 8: Porownaj `BodyHandlers.ofString()` Z `BodyHandlers.ofByteArray()`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyHttpClientWasIncubatorInJava9 {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, CZYM jest "incubator module" I DLACZEGO `HttpClient` PRZEZ TO PRZESZEDL. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAdvantagesOfNewApiOverOld {
        /* 🧪 Zadanie 10: Bez terminala - wypisz co NAJMNIEJ 3 ZALETY nowego `HttpClient` NAD `HttpURLConnection`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RequestHttp2AndInspectActualVersionUsed {
        /* 🧪 Zadanie 11: Zazadaj HTTP/2 I sprawdz FAKTYCZNIE uzyta wersje W odpowiedzi. */
        public static void main(String[] args) { }
    }

    static class Exercise12_SendAsyncRequestAndContinueOtherWorkMeanwhile {
        /* 🧪 Zadanie 12: Wyslij `sendAsync()` I wykonaj INNA prace W watku glownym, ZANIM sprawdzisz wynik. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ChainMultipleAsyncRequestsWithThenCompose {
        /* 🧪 Zadanie 13: Powiaz 2 asynchroniczne zadania (wynik 1. UZYTY W 2.) uzywajac `thenCompose`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_HandleConnectionRefusedExceptionGracefully {
        /* 🧪 Zadanie 14: Obsluz BEZPIECZNIE `ConnectException` PRZY POLACZENIU DO nieistniejacego portu. */
        public static void main(String[] args) { }
    }

    static class Exercise15_SendMultipleRequestsInParallelWithSendAsync {
        /* 🧪 Zadanie 15: Wyslij WIELE zadan ROWNOLEGLE (`sendAsync`) I ZBIERZ WSZYSTKIE wyniki. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConfigureRedirectPolicyOnHttpClient {
        /* 🧪 Zadanie 16: Skonfiguruj polityke przekierowan (`HttpClient.Redirect`) NA builderze. */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildRequestWithQueryParametersManually {
        /* 🧪 Zadanie 17: Zbuduj URI Z parametrami zapytania (RECZNIE, `HttpClient` NIE MA wbudowanego buildera query). */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureResponseTimeDifferenceBetweenHttp1And2 {
        /* 🧪 Zadanie 18: Zmierz (PRZYBLIZENIOWO) roznice czasu odpowiedzi MIEDZY wymuszonym HTTP/1.1 A HTTP/2. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainWhyHttpUrlConnectionStillExistsInJdk {
        /* 🧪 Zadanie 19: Bez terminala - wyjasnij, DLACZEGO `HttpURLConnection` NADAL istnieje W JDK MIMO nowego API. */
        public static void main(String[] args) { }
    }

    static class Exercise20_UsePublisherOfBytesForRequestBody {
        /* 🧪 Zadanie 20: Uzyj `BodyPublishers.ofBytes()` DO wyslania SUROWYCH bajtow W ciele zadania. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildRetryMechanismAroundSendAsyncWithExponentialBackoff {
        /* 🧪 Zadanie 21: Zbuduj MECHANIZM retry Z EXPONENTIAL BACKOFF WOKOL `sendAsync()`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementConnectionPoolReuseComparisonBetweenClients {
        /* 🧪 Zadanie 22: Porownaj PONOWNE uzycie polaczen PRZY WIELU zadaniach Z JEDNYM `HttpClient` A NOWYM ZA KAZDYM razem. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildSimpleRestClientWrapperAroundHttpClient {
        /* 🧪 Zadanie 23: Zbuduj PROSTY wrapper REST (get/post/put/delete) WOKOL `HttpClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_StreamLargeResponseBodyWithBodyHandlerOfInputStream {
        /* 🧪 Zadanie 24: Przetworz DUZE cialo odpowiedzi STRUMIENIOWO uzywajac `BodyHandlers.ofInputStream()`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCircuitBreakerPatternAroundHttpClientCalls {
        /* 🧪 Zadanie 25: Zaimplementuj PROSTY wzorzec "circuit breaker" WOKOL wywolan `HttpClient` (powiazanie Z `_31_spring_cloud_microservices`, koncepcyjnie). */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareThreadUsageBetweenSynchronousAndAsynchronousCalls {
        /* 🧪 Zadanie 26: Porownaj UZYCIE watkow PRZY 100 zadaniach SYNCHRONICZNYCH A 100 ASYNCHRONICZNYCH. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildRateLimitedHttpClientWrapper {
        /* 🧪 Zadanie 27: Zbuduj wrapper OGRANICZAJACY liczbe zadan NA sekunde (powiazanie Z `_18_rest_api/Lesson16`). */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCustomAuthenticatorForHttpClient {
        /* 🧪 Zadanie 28: Zaimplementuj WLASNY `java.net.Authenticator` UZYWANY PRZEZ `HttpClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_MigrateExistingHttpUrlConnectionCodeToHttpClient {
        /* 🧪 Zadanie 29: ZMIGRUJ istniejacy fragment kodu OPARTY NA `HttpURLConnection` NA `HttpClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignDecisionMatrixForChoosingHttpClientConfiguration {
        /* 🧪 Zadanie 30: Zaprojektuj MACIERZ decyzyjna (timeout/redirect/wersja HTTP) DLA ROZNYCH scenariuszy produkcyjnych. */
        public static void main(String[] args) { }
    }
}
