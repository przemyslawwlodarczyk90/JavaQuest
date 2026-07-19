package com.example.javaquest._27_spring_test.Lesson12_TestRestTemplateAndWebTestClient;

public class _Exercises_Lesson12_TestRestTemplateAndWebTestClient {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_UseTestRestTemplateGetForObject {
        /* 🧪 Zadanie 1: Uzyj `TestRestTemplate.getForObject(...)` DLA WLASNEGO endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseTestRestTemplateGetForEntity {
        /* 🧪 Zadanie 2: Uzyj `getForEntity(...)` I zweryfikuj status+naglowki. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseWebTestClientForSameEndpoint {
        /* 🧪 Zadanie 3: Przetestuj TEN SAM endpoint `WebTestClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseTestRestTemplatePostForObject {
        /* 🧪 Zadanie 4: Uzyj `postForObject(...)` DO wyslania danych. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseWebTestClientPostWithBody {
        /* 🧪 Zadanie 5: Uzyj `WebTestClient.post().bodyValue(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhenToChooseEachClient {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, KIEDY wybrac TestRestTemplate, a KIEDY WebTestClient. */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestErrorResponseWithTestRestTemplate {
        /* 🧪 Zadanie 7: Przetestuj odpowiedz bledu (404) `TestRestTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestErrorResponseWithWebTestClient {
        /* 🧪 Zadanie 8: Przetestuj TEN SAM blad `WebTestClient` - porownaj skladnie. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseWebTestClientExpectBodyList {
        /* 🧪 Zadanie 9: Uzyj `.expectBodyList(Klasa.class)` DLA odpowiedzi-listy. */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentRestTemplateDeprecationHistory {
        /* 🧪 Zadanie 10: Bez terminala - powiaz z `_22_spring_web/Lesson17` - przypomnij historie RestTemplate->WebClient->RestClient. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseTestRestTemplateWithBasicAuth {
        /* 🧪 Zadanie 11: Powiaz z `_24_spring_security` - uzyj `TestRestTemplate.withBasicAuth(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseWebTestClientWithCustomHeader {
        /* 🧪 Zadanie 12: Uzyj `WebTestClient` Z WLASNYM naglowkiem (`.header(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestRestTemplateExchangeMethodForFullControl {
        /* 🧪 Zadanie 13: Uzyj `exchange(...)` DLA PELNEJ kontroli NAD zadaniem/odpowiedzia. */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestWebTestClientResponseTimeout {
        /* 🧪 Zadanie 14: Powiaz z `_26_integration_testing/Lesson02` - przetestuj TIMEOUT `WebTestClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CombineTestRestTemplateWithMockitoBeanDependency {
        /* 🧪 Zadanie 15: Powiaz z Lesson08 - polacz `TestRestTemplate` Z `@MockitoBean` warstwy serwisowej. */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestFileDownloadWithTestRestTemplate {
        /* 🧪 Zadanie 16: Przetestuj POBRANIE pliku (binarna odpowiedz) `TestRestTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseWebTestClientToVerifyResponseHeaders {
        /* 🧪 Zadanie 17: Zweryfikuj naglowki odpowiedzi `.expectHeader()...`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestConcurrentRequestsWithTestRestTemplate {
        /* 🧪 Zadanie 18: Powiaz z `_05_multithreading` - wyslij WIELE ROWNOLEGLYCH zadan `TestRestTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareResponseTimeBetweenBothClients {
        /* 🧪 Zadanie 19: Zmierz CZAS odpowiedzi OBU klientow DLA TEGO SAMEGO endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullEndToEndTestSuiteUsingBothClients {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet end-to-end LACZACY OBA klienty W ROZNYCH testach. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomTestRestTemplateBuilder {
        /* 🧪 Zadanie 21: Zbuduj WLASNY `RestTemplateBuilder` Z NIESTANDARDOWA konfiguracja. */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestStreamingResponseWithWebTestClient {
        /* 🧪 Zadanie 22: Powiaz z `_29_spring_reactive` - przetestuj STREAMOWANA odpowiedz `WebTestClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementLoadTestUsingTestRestTemplateAcrossManyRequests {
        /* 🧪 Zadanie 23: Powiaz z `_05_multithreading` - wyslij 1000 zadan I zmierz przepustowosc. */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestWebSocketUpgradeWithWebTestClient {
        /* 🧪 Zadanie 24: Zbadaj (koncepcyjnie) WebSocket testing W kontekscie WebTestClient. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementContractTestUsingTestRestTemplateAgainstRealApi {
        /* 🧪 Zadanie 25: Powiaz z `_26_integration_testing/Lesson13` - zbuduj test kontraktowy `TestRestTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestSecuredEndpointWithWebTestClientAndJwt {
        /* 🧪 Zadanie 26: Powiaz z Lesson13 - przetestuj zabezpieczony endpoint `WebTestClient` Z JWT. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildCustomAssertionHelperWrappingWebTestClient {
        /* 🧪 Zadanie 27: Zbuduj WLASNY helper OPAKOWUJACY typowe asercje `WebTestClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareErrorHandlingStrategiesAcrossBothClients {
        /* 🧪 Zadanie 28: Porownaj strategie obslugi bledow (wyjatki vs statusy) OBU klientow. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementRetryLogicWrapperAroundTestRestTemplate {
        /* 🧪 Zadanie 29: Powiaz z `_26_integration_testing/Lesson12` - dodaj RETRY WOKOL `TestRestTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullHttpClientTestingStandardForApiTeam {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard testowania klientow HTTP DLA zespolu API. */
        public static void main(String[] args) { }
    }
}
