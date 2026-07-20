package com.example.javaquest._29_spring_reactive.Lesson09_WebFluxVsSpringMvc;

public class _Exercises_Lesson09_WebFluxVsSpringMvc {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ForceWebApplicationTypeReactiveAndStartApp {
        /* 🧪 Zadanie 1: Wymus `WebApplicationType.REACTIVE` I uruchom PROSTA aplikacje. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateRestControllerReturningMonoOfString {
        /* 🧪 Zadanie 2: Stworz `@RestController` ZWRACAJACY `Mono<String>`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_SendGetRequestUsingWebClientToReactiveEndpoint {
        /* 🧪 Zadanie 3: Wyslij zadanie GET uzywajac `WebClient` DO reaktywnego endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareLocalServerPortPropertyForReactiveVsServletApp {
        /* 🧪 Zadanie 4: Porownaj property `local.server.port` DLA aplikacji REACTIVE A SERVLET. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhySpringBootChoosesServletByDefaultWhenBothStartersPresent {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO Spring Boot WYBIERA SERVLET domyslnie, GDY OBA startery SA obecne. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainDifferenceBetweenTomcatAndNettyThreadModel {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij ROZNICE modelu watkow Tomcat A Netty. */
        public static void main(String[] args) { }
    }

    static class Exercise07_PrintThreadNameInsideReactiveControllerMethod {
        /* 🧪 Zadanie 7: Wypisz nazwe watku wewnatrz REAKTYWNEJ metody kontrolera. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareResponseEntityBasedControllerWithMonoBasedController {
        /* 🧪 Zadanie 8: Powiaz z `_22_spring_web` - porownaj kontroler ZWRACAJACY `ResponseEntity` Z ZWRACAJACYM `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhenMixingWebAndWebfluxStartersMakesSense {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, KIEDY MIESZANIE `web` I `webflux` MA SENS (np. `WebClient` W aplikacji MVC). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListScenariosWhereWebFluxIsClearWinnerOverSpringMvc {
        /* 🧪 Zadanie 10: Bez terminala - wypisz scenariusze, GDZIE WebFlux jest WYRAZNIE LEPSZY OD Spring MVC. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildReactiveControllerReturningFluxOfMultipleItems {
        /* 🧪 Zadanie 11: Zbuduj REAKTYWNY kontroler ZWRACAJACY `Flux` Z WIELOMA elementami. */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureStartupTimeDifferenceBetweenTomcatAndNettyContext {
        /* 🧪 Zadanie 12: Zmierz ROZNICE czasu startu MIEDZY kontekstem Tomcat A Netty. */
        public static void main(String[] args) { }
    }

    static class Exercise13_SimulateHighConcurrentLoadOnBothServletAndReactiveEndpoints {
        /* 🧪 Zadanie 13: Symuluj WYSOKIE obciazenie ROWNOLEGLE NA endpoincie SERVLET I REACTIVE. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementServerSentEventsEndpointUsingFluxWithDelayElements {
        /* 🧪 Zadanie 14: Zaimplementuj endpoint Server-Sent Events uzywajac `Flux` + `delayElements`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareMemoryFootprintOfTomcatVsNettyContextAtIdle {
        /* 🧪 Zadanie 15: Porownaj zuzycie pamieci kontekstu Tomcat A Netty W STANIE BEZCZYNNYM. */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildMixedApplicationWithMvcControllerAndWebClientCallingExternalReactiveService {
        /* 🧪 Zadanie 16: Zbuduj MIESZANA aplikacje Z kontrolerem MVC UZYWAJACYM `WebClient` DO zewnetrznego serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainWhyBlockingCallInReactiveControllerDefeatsThePurpose {
        /* 🧪 Zadanie 17: Bez terminala - wyjasnij, DLACZEGO blokujace wywolanie W REAKTYWNYM kontrolerze NISZCZY SENS WebFlux. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareErrorHandlingApproachBetweenControllerAdviceAndReactiveExceptionHandler {
        /* 🧪 Zadanie 18: Porownaj `@ControllerAdvice` (Spring MVC) Z obsluga bledow W WebFlux. */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildLoadTestComparingResponseTimeUnderThousandConcurrentRequests {
        /* 🧪 Zadanie 19: Zbuduj TEST OBCIAZENIOWY porownujacy CZAS odpowiedzi PRZY TYSIACU rownoleglych zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignDecisionMatrixForChoosingMvcOrWebFluxForNewProject {
        /* 🧪 Zadanie 20: Zaprojektuj MACIERZ decyzyjna DLA wyboru MVC LUB WebFlux DLA NOWEGO projektu. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildBenchmarkSuiteComparingThroughputOfMvcVsWebFluxUnderIoHeavyLoad {
        /* 🧪 Zadanie 21: Zbuduj PAKIET benchmarkow porownujacy PRZEPUSTOWOSC MVC A WebFlux POD obciazeniem I/O-heavy. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementGradualMigrationStrategyFromMvcControllerToWebFluxController {
        /* 🧪 Zadanie 22: Zaimplementuj (koncepcyjnie) STOPNIOWA migracje kontrolera MVC NA WebFlux. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildHybridArchitectureWithSelectedEndpointsOnWebFluxAndRestOnMvc {
        /* 🧪 Zadanie 23: Zbuduj HYBRYDOWA architekture Z WYBRANYMI endpointami NA WebFlux, RESZTA NA MVC. */
        public static void main(String[] args) { }
    }

    static class Exercise24_AnalyzeRealWorldCaseStudyOfMigratingLegacyMvcServiceToWebFlux {
        /* 🧪 Zadanie 24: (WYMAGA dostepu DO internetu) Zbadaj REALNE studium przypadku migracji serwisu MVC NA WebFlux. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignArchitectureDecisionRecordForAdoptingWebFluxInExistingSystem {
        /* 🧪 Zadanie 25: Powiaz z `_17_architecture/Lesson02` - zaprojektuj ADR DLA adopcji WebFlux W ISTNIEJACYM systemie. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCustomNettyServerCustomizerForConnectionTuning {
        /* 🧪 Zadanie 26: Zaimplementuj WLASNY `NettyServerCustomizer` DO tuningu polaczen. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildComprehensivePerformanceReportComparingBothStacksAcrossScenarios {
        /* 🧪 Zadanie 27: Zbuduj KOMPLEKSOWY raport wydajnosci porownujacy OBA stosy W ROZNYCH scenariuszach. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignTeamTrainingPlanForAdoptingReactiveProgrammingParadigm {
        /* 🧪 Zadanie 28: Zaprojektuj PLAN szkolenia zespolu DO adopcji paradygmatu reaktywnego. */
        public static void main(String[] args) { }
    }

    static class Exercise29_AnalyzeOperationalComplexityDifferencesBetweenDebuggingMvcAndWebFluxApps {
        /* 🧪 Zadanie 29: Przeanalizuj ROZNICE ZLOZONOSCI operacyjnej PRZY debugowaniu aplikacji MVC A WebFlux. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMigrationRoadmapFromMonolithicMvcToReactiveMicroservices {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA mape drogowa migracji Z monolitu MVC NA reaktywne mikroserwisy (powiazanie Z `_31_spring_cloud_microservices`). */
        public static void main(String[] args) { }
    }
}
