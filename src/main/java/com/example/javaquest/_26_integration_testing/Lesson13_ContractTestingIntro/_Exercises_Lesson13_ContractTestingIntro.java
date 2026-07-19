package com.example.javaquest._26_integration_testing.Lesson13_ContractTestingIntro;

public class _Exercises_Lesson13_ContractTestingIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineSimpleContractForOwnEndpoint {
        /* 🧪 Zadanie 1: Zdefiniuj WLASNY `ApiContract` DLA prostego endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifyContractAgainstValidResponse {
        /* 🧪 Zadanie 2: Zweryfikuj kontrakt PRZECIW POPRAWNEJ odpowiedzi. */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyContractAgainstBrokenResponse {
        /* 🧪 Zadanie 3: Zweryfikuj kontrakt PRZECIW ZLAMANEJ odpowiedzi (brak pola). */
        public static void main(String[] args) { }
    }

    static class Exercise04_DefineContractWithOptionalField {
        /* 🧪 Zadanie 4: Zdefiniuj kontrakt Z POLEM OPCJONALNYM (moze byc `null`). */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyContractTestingSolvesDifferentProblemThanWireMock {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, JAKI problem ROZWIAZUJE contract testing, KTOREGO NIE ROZWIAZUJE WireMock. */
        public static void main(String[] args) { }
    }

    static class Exercise06_RunSameContractAgainstTwoDifferentProviders {
        /* 🧪 Zadanie 6: Uruchom TEN SAM kontrakt PRZECIW 2 ROZNYM (symulowanym) dostawcom. */
        public static void main(String[] args) { }
    }

    static class Exercise07_DefineContractForListEndpoint {
        /* 🧪 Zadanie 7: Zdefiniuj kontrakt DLA endpointu zwracajacego LISTE elementow. */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestContractCatchesTypeChange {
        /* 🧪 Zadanie 8: Zademonstruj, ze kontrakt WYKRYWA zmiane TYPU pola (String zamiast Number). */
        public static void main(String[] args) { }
    }

    static class Exercise09_DocumentConsumerVsProviderRoles {
        /* 🧪 Zadanie 9: Bez terminala - opisz ROLE konsumenta I dostawcy W CDC. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRealWorldToolsForContractTesting {
        /* 🧪 Zadanie 10: Wypisz narzedzia DO contract testingu (Pact, Spring Cloud Contract). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DefineContractForErrorResponseShape {
        /* 🧪 Zadanie 11: Powiaz z `_18_rest_api/Lesson12` - zdefiniuj kontrakt DLA ksztaltu odpowiedzi bledu (RFC 7807). */
        public static void main(String[] args) { }
    }

    static class Exercise12_VerifyProviderRealServerAgainstMultipleContracts {
        /* 🧪 Zadanie 12: Zweryfikuj PRAWDZIWY serwer PRZECIW 3 ROZNYM kontraktom (rozne endpointy). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementContractVersioningStrategy {
        /* 🧪 Zadanie 13: Powiaz z `_18_rest_api/Lesson14_Versioning` - zaprojektuj WERSJONOWANIE kontraktow. */
        public static void main(String[] args) { }
    }

    static class Exercise14_CombineContractTestingWithWireMock {
        /* 🧪 Zadanie 14: Polacz WireMock (Lesson07) Z kontraktem - stub GENEROWANY Z kontraktu. */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignContractForPaginatedEndpoint {
        /* 🧪 Zadanie 15: Powiaz z `_18_rest_api/Lesson10` - zaprojektuj kontrakt DLA stronicowanej odpowiedzi. */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestBackwardCompatibleContractChange {
        /* 🧪 Zadanie 16: Zademonstruj ZMIANE kontraktu KOMPATYBILNA WSTECZ (DODANE pole). */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestBreakingContractChange {
        /* 🧪 Zadanie 17: Zademonstruj ZMIANE LAMIACA kontrakt (USUNIETE/PRZEMIANOWANE pole). */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementContractRepositoryStoringMultipleContracts {
        /* 🧪 Zadanie 18: Zbuduj PROSTE "repozytorium" WIELU kontraktow (Map<nazwa, ApiContract>). */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplorePactFrameworkConceptually {
        /* 🧪 Zadanie 19: Bez terminala - opisz (koncepcyjnie) JAK dziala biblioteka Pact I "Pact Broker". */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignContractTestSuiteForMicroservicesBoundary {
        /* 🧪 Zadanie 20: Powiaz z `_31_spring_cloud_microservices` - zaprojektuj kontrakty NA GRANICY 2 mikroserwisow. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementJsonSchemaBasedContractValidator {
        /* 🧪 Zadanie 21: Zaimplementuj WALIDACJE kontraktu OPARTA NA prawdziwym JSON Schema. */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildContractTestingCiPipelineWithProducerConsumerStages {
        /* 🧪 Zadanie 22: Powiaz z `_11_buildtools` - zaprojektuj pipeline CI Z ETAPAMI konsument/dostawca. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCanIDeployStyleCompatibilityCheck {
        /* 🧪 Zadanie 23: Zaimplementuj mechanizm SPRAWDZAJACY (jak Pact "can-i-deploy"), CZY WERSJA jest BEZPIECZNA do wdrozenia. */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignContractForAsyncEventBasedCommunication {
        /* 🧪 Zadanie 24: Powiaz z `_30_spring_messaging_and_async` - zaprojektuj kontrakt DLA zdarzenia (message contract). */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementContractDiffToolShowingBreakingChanges {
        /* 🧪 Zadanie 25: Zbuduj narzedzie PORTOWNUJACE 2 WERSJE kontraktu I WYPISUJACE ZMIANY LAMIACE. */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestContractAgainstMultipleConsumerVersionsSimultaneously {
        /* 🧪 Zadanie 26: Zweryfikuj dostawce PRZECIW WIELU WERSJOM kontraktu NARAZ (rozni konsumenci). */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSpringCloudContractStyleGeneratedStub {
        /* 🧪 Zadanie 27: Zbadaj (koncepcyjnie) Spring Cloud Contract - GENEROWANIE stuba Z kontraktu. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignContractGovernanceProcessForLargeOrganization {
        /* 🧪 Zadanie 28: Zaprojektuj PROCES zarzadzania kontraktami DLA organizacji Z DZIESIATKAMI mikroserwisow. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementBackwardCompatibilityGateInCiPipeline {
        /* 🧪 Zadanie 29: Zaimplementuj "bramke" CI BLOKUJACA merge PRZY LAMIACEJ zmianie kontraktu. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullContractTestingStrategyForModularMonolithToMicroservicesMigration {
        /* 🧪 Zadanie 30: Powiaz z `_17_architecture/Lesson17`+`_31` - zaprojektuj strategie kontraktow PRZY migracji monolit->mikroserwisy. */
        public static void main(String[] args) { }
    }
}
