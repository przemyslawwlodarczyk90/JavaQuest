package com.example.javaquest._22_spring_web.Lesson17_HttpClientsRestTemplateWebClientRestClient;

public class _Exercises_Lesson17_HttpClientsRestTemplateWebClientRestClient {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreeClientsEvolution {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij KOLEJNOSC I MOTYWACJE powstania
         * RestTemplate -> WebClient -> RestClient.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementGetRequestWithRestTemplate {
        /*
         * 🧪 Zadanie 2:
         * Wykonaj WLASNE zadanie GET przez `RestTemplate`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementGetRequestWithWebClient {
        /*
         * 🧪 Zadanie 3:
         * Wykonaj TO SAMO zadanie przez `WebClient` (Z `.block()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementGetRequestWithRestClient {
        /*
         * 🧪 Zadanie 4:
         * Wykonaj TO SAMO zadanie przez `RestClient`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementPostRequestWithAllThreeClients {
        /*
         * 🧪 Zadanie 5:
         * Wykonaj zadanie POST (Z cialem JSON) WSZYSTKIMI 3 klientami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementErrorHandlingWithRestTemplate {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj obsluge bledu 4xx/5xx przez `RestTemplate`
         * (`HttpClientErrorException`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementErrorHandlingWithRestClient {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj TO SAMO przez `RestClient`
         * (`onStatus(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementCustomHeadersWithEachClient {
        /*
         * 🧪 Zadanie 8:
         * Dodaj WLASNE naglowki (np. `Authorization`) WSZYSTKIMI 3
         * klientami - porownaj SKLADNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareApiVerbosity {
        /*
         * 🧪 Zadanie 9:
         * Zestaw obok siebie IDENTYCZNE zadanie (URL+naglowki+body)
         * wszystkimi klientami - KTORY wymaga NAJMNIEJ kodu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyRestClientIsRecommendedDefault {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, DLACZEGO `RestClient` jest ZALECANYM
         * wyborem DLA nowego kodu W zwyklej (nie-reaktywnej) aplikacji
         * Spring MVC.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementRestTemplateWithConnectionTimeout {
        /*
         * 🧪 Zadanie 11:
         * Skonfiguruj TIMEOUT polaczenia/odczytu DLA `RestTemplate`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementRestClientWithConnectionTimeout {
        /*
         * 🧪 Zadanie 12:
         * Skonfiguruj TO SAMO DLA `RestClient`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementWebClientReactiveChaining {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj LANCUCH 2 zadan `WebClient` (WYNIK pierwszego
         * uzyty W drugim) BEZ `.block()` posrodku (`flatMap`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRestClientAsBeanInjection {
        /*
         * 🧪 Zadanie 14:
         * Zarejestruj `RestClient.Builder` jako bean (auto-konfigurowany
         * przez Spring Boot) - wstrzyknij DO WLASNEGO serwisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRestTemplateWithInterceptor {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj `ClientHttpRequestInterceptor` DLA
         * `RestTemplate` (np. logowanie kazdego wychodzacego zadania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRestClientWithRequestInterceptor {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj TO SAMO DLA `RestClient`
         * (`requestInterceptor(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementExchangeMethodForFullControl {
        /*
         * 🧪 Zadanie 17:
         * Uzyj `.exchange(...)` (WSZYSTKIE 3 klienty MAJA
         * PODOBNY mechanizm) DLA PELNEJ kontroli NAD odpowiedzia
         * (status+naglowki+body NARAZ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementRetryLogicForFailedRequests {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj PROSTA logike PONAWIANIA (retry) NIEUDANEGO
         * zadania (max 3 proby).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureLatencyDifferenceAcrossClients {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas WYKONANIA 100 zadan KAZDYM klientem - CZY SA
         * ZAUWAZALNE ROZNICE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildHttpClientMigrationCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" MIGRACJI kodu Z `RestTemplate` NA
         * `RestClient` (odpowiedniki metod).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementServiceLayerAbstractionOverHttpClient {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_17_architecture/Lesson12_PortsAndAdapters` -
         * ukryj WYBRANY klient HTTP ZA WLASNYM portem/interfejsem
         * (LATWA podmiana implementacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCircuitBreakerAroundHttpClient {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_22_spring_web/Lesson16` (interceptory) -
         * zaimplementuj PROSTY "wylacznik" WOKOL wywolan HTTP DO INNEGO
         * serwisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementMockServerForClientTesting {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_13_libraries/Lesson14` (MockWebServer) -
         * przetestuj WLASNY kod uzywajacy `RestClient` PRZECIWKO
         * mockowanemu serwerowi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementWebClientTrueReactivePipeline {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj PRAWDZIWY reaktywny potok `WebClient`
         * (WIELE rownoleglych zadan, `Flux`, BEZ `.block()`) - powiaz
         * z KONCEPCJA reaktywnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementHttpClientMetricsWithMicrometer {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_21_spring_boot/Lesson13` - zbierz metryki
         * (czas/liczba/kody statusu) DLA WYCHODZACYCH zadan HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSecureHttpClientWithCustomTrustStore {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson08_HttpsTlsBasics` -
         * skonfiguruj `RestClient` Z WLASNYM `SSLContext`/truststore.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementDistributedTracePropagation {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_21_spring_boot/Lesson13` (tracing) - propaguj
         * "trace ID" W naglowku WYCHODZACEGO zadania HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRateLimitedOutgoingClient {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_18_rest_api/Lesson16` - ogranicz CZESTOTLIWOSC
         * WYCHODZACYCH zadan DO ZEWNETRZNEGO API (unikanie 429 PO
         * DRUGIEJ stronie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareVirtualThreadsWithReactiveForIoBoundCalls {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_05_multithreading/Lesson33_VirtualThreads` -
         * porownaj SYNCHRONICZNY `RestClient` NA WATKACH WIRTUALNYCH Z
         * REAKTYWNYM `WebClient` DLA operacji I/O-bound - CZY WATKI
         * WIRTUALNE ZMNIEJSZAJA potrzebe reaktywnosci?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteResilientHttpClientCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY, odporny klient HTTP - timeout (Zadanie 12)
         * + retry (Zadanie 18) + circuit breaker (Zadanie 22) + metryki
         * (Zadanie 25) - jeden spojny, dzialajacy komponent.
         */
        public static void main(String[] args) { }
    }
}
