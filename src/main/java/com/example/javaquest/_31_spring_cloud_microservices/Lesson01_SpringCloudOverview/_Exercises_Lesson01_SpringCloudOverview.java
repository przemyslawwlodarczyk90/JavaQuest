package com.example.javaquest._31_spring_cloud_microservices.Lesson01_SpringCloudOverview;

public class _Exercises_Lesson01_SpringCloudOverview {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllSpringCloudModulesFromThisChapter {
        /* 🧪 Zadanie 1: Wypisz WSZYSTKIE moduly Spring Cloud omawiane W TYM rozdziale wraz Z JEDNYM zdaniem opisu. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainDifferenceBetweenSpringBootAndSpringCloud {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij ROZNICE MIEDZY Spring Bootem A Spring Cloud. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReadSpringCloudCommonsVersionFromClasspath {
        /* 🧪 Zadanie 3: Odczytaj wersje `spring-cloud-commons` Z manifestu NA classpath (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhySpringCloudNeedsOwnBom {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij, DLACZEGO Spring Cloud WYMAGA WLASNEGO importu BOM. */
        public static void main(String[] args) { }
    }

    static class Exercise05_FindSpringCloudVersionInPomXml {
        /* 🧪 Zadanie 5: Znajdz W `pom.xml` TEGO projektu wpis `spring-cloud-dependencies` I ZAPISZ jego wersje. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhatReleaseTrainMeans {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, CZYM jest "release train" (np. "2024.0.1"/"Moorgate"). */
        public static void main(String[] args) { }
    }

    static class Exercise07_MapEachModuleToProblemItSolves {
        /* 🧪 Zadanie 7: Dla KAZDEGO modulu Z Lesson01 zapisz JEDNO zdanie: JAKI problem systemu rozproszonego rozwiazuje. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyNoModuleRequiresRealCloud {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, DLACZEGO ZADNA lekcja tego rozdzialu NIE wymaga prawdziwej chmury AWS/GCP. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListWhichModulesRequireDocker {
        /* 🧪 Zadanie 9: Wskaz, KTORE lekcje tego rozdzialu MOGA wymagac dzialajacego Dockera (I DLACZEGO). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConnectChapterToLesson19Architecture {
        /* 🧪 Zadanie 10: Powiaz TEN rozdzial Z `_17_architecture/Lesson19_WhenMicroservicesMakeSense`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteDownStarterArtifactIdForEachModule {
        /* 🧪 Zadanie 11: Sprawdz W `pom.xml` I zapisz artifactId starter'a DLA KAZDEGO modulu (Eureka/Gateway/Feign/itd.). */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExplainAutoConfigurationMechanismReuse {
        /* 🧪 Zadanie 12: Bez terminala - wyjasnij, jak Spring Cloud REUZYWA mechanizm auto-konfiguracji Z `_21_spring_boot`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareEurekaAndConsulConceptually {
        /* 🧪 Zadanie 13: Bez terminala - porownaj koncepcyjnie Eureke Z Consulem (obydwa TO service registry). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainRelationshipBetweenGatewayAndLoadBalancer {
        /* 🧪 Zadanie 14: Bez terminala - wyjasnij RELACJE MIEDZY Gateway A LoadBalancer (Lesson06-08). */
        public static void main(String[] args) { }
    }

    static class Exercise15_DescribeWhatCircuitBreakerProtectsAgainst {
        /* 🧪 Zadanie 15: Bez terminala - opisz, PRZED CZYM chroni circuit breaker (kaskadowa awaria). */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainDistributedTracingPurpose {
        /* 🧪 Zadanie 16: Bez terminala - wyjasnij, PO CO potrzebny jest distributed tracing W mikroserwisach. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareFeignWithRestClientFromLesson17SpringWeb {
        /* 🧪 Zadanie 17: Powiaz Z `_22_spring_web/Lesson17` - porownaj Feign Z `RestClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainSpringCloudStreamAbstractionOverKafkaRabbit {
        /* 🧪 Zadanie 18: Bez terminala - wyjasnij, CO Spring Cloud Stream DODAJE PONAD `_30_spring_messaging_and_async` (Kafka/RabbitMQ). */
        public static void main(String[] args) { }
    }

    static class Exercise19_DrawTextDiagramOfTypicalMicroservicesRequestFlow {
        /* 🧪 Zadanie 19: Narysuj TEKSTOWY diagram typowego przeplywu zadania: klient -> Gateway -> Eureka -> serwis A -> serwis B. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ListWhichModulesAreNetflixOssOrigin {
        /* 🧪 Zadanie 20: Wskaz, KTORE moduly Spring Cloud maja RODOWOD Netflix OSS (historycznie). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ResearchWhySpringCloudReleaseTrainsUseCityNames {
        /* 🧪 Zadanie 21: Zbadaj (dokumentacja Spring Cloud), DLACZEGO release trainy maja nazwy KODOWE (np. "Moorgate"). */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareSpringCloudGatewayWithNginxConceptually {
        /* 🧪 Zadanie 22: Bez terminala - porownaj koncepcyjnie Spring Cloud Gateway Z Nginx/Traefik jako reverse proxy. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ExplainWhyServiceDiscoveryIsHarderThanDnsAlone {
        /* 🧪 Zadanie 23: Bez terminala - wyjasnij, DLACZEGO samo DNS NIE WYSTARCZA jako service discovery W dynamicznym srodowisku (autoskalowanie, kontenery). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignModuleChoiceForHypotheticalECommerceSystem {
        /* 🧪 Zadanie 24: Zaprojektuj (na papierze), KTORYCH modulow Spring Cloud UZYLBYS DLA hipotetycznego systemu e-commerce Z 5 mikroserwisami. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ExplainTradeoffsOfCentralizedGatewayAsSinglePointOfFailure {
        /* 🧪 Zadanie 25: Bez terminala - omow ryzyko Gateway jako "single point of failure" I jak sie PRZED TYM bronic. */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareOrchestrationVsChoreographyPreview {
        /* 🧪 Zadanie 26: Zapowiedz Lesson14 - wyjasnij ROZNICE MIEDZY orkiestracja A choreografia W kontekscie mikroserwisow. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ResearchSpringCloudVsSpringCloudAlibabaVsSpringCloudAws {
        /* 🧪 Zadanie 27: Zbadaj, CZYM ROZNIA sie `spring-cloud-netflix`/`spring-cloud-alibaba`/`spring-cloud-aws` (rozne implementacje TYCH SAMYCH abstrakcji). */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExplainWhyThisCourseUsesLocalEmbeddedContextsInsteadOfSeparateProcesses {
        /* 🧪 Zadanie 28: Bez terminala - wyjasnij, DLACZEGO ten kurs demonstruje mikroserwisy jako WIELE kontekstow W JEDNYM JVM, zamiast osobnych procesow. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ListProductionDifferencesFromThisCourseApproach {
        /* 🧪 Zadanie 29: Wypisz, CO BYLOBY inaczej W prawdziwej produkcji (osobne procesy/kontenery/orkiestracja K8s) WZGLEDEM demo Z tego kursu. */
        public static void main(String[] args) { }
    }

    static class Exercise30_WriteYourOwnOneParagraphSummaryOfSpringCloudPurpose {
        /* 🧪 Zadanie 30: Napisz WLASNYMI slowami JEDEN akapit podsumowujacy, PO CO istnieje Spring Cloud. */
        public static void main(String[] args) { }
    }
}
