package com.example.javaquest._31_spring_cloud_microservices.Lesson03_SpringCloudNetflixEureka;

public class _Exercises_Lesson03_SpringCloudNetflixEureka {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StartEmbeddedEurekaServerOnRandomPort {
        /* 🧪 Zadanie 1: Uruchom embedded Eureka Server NA losowym porcie (`server.port=0`), jak W lekcji. */
        public static void main(String[] args) { }
    }

    static class Exercise02_StartClientServiceAndObserveRegistration {
        /* 🧪 Zadanie 2: Uruchom KLIENTA (druga aplikacje Spring Boot) I ZAOBSERWUJ jego rejestracje W Eurece. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainRoleOfEnableEurekaServerAnnotation {
        /* 🧪 Zadanie 3: Bez terminala - wyjasnij role adnotacji `@EnableEurekaServer`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhySpringApplicationNamePropertyMatters {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij, DLACZEGO `spring.application.name` decyduje O nazwie W Eurece. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyEurekaClientEnabledIsGloballyFalseInThisProject {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO `eureka.client.enabled=false` jest ustawione GLOBALNIE W `application.properties` TEGO projektu. */
        public static void main(String[] args) { }
    }

    static class Exercise06_RegisterTwoInstancesOfSameServiceName {
        /* 🧪 Zadanie 6: Zarejestruj DWIE instancje TEGO SAMEGO `spring.application.name` (rozne porty). */
        public static void main(String[] args) { }
    }

    static class Exercise07_QueryEurekaRegistryDirectlyOverHttp {
        /* 🧪 Zadanie 7: Odpytaj rejestr Eureki BEZPOSREDNIO PRZEZ HTTP (`GET /eureka/apps`, naglowek `Accept: application/json`). */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhatPeerAwareInstanceRegistryIs {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, CZYM jest `PeerAwareInstanceRegistry` uzyty W lekcji. */
        public static void main(String[] args) { }
    }

    static class Exercise09_MeasureTimeUntilInstanceAppearsInRegistry {
        /* 🧪 Zadanie 9: Zmierz czas MIEDZY startem klienta A JEGO POJAWIENIEM SIE W rejestrze (jak W lekcji, bounded polling). */
        public static void main(String[] args) { }
    }

    static class Exercise10_GracefullyShutdownClientAndObserveDeregistration {
        /* 🧪 Zadanie 10: Zamknij kontekst klienta (`context.close()`) I ZAOBSERWUJ wyrejestrowanie Z Eureki. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TuneLeaseRenewalIntervalAndObserveFasterHeartbeat {
        /* 🧪 Zadanie 11: Dostrój `eureka.instance.lease-renewal-interval-in-seconds` I ZAOBSERWUJ czestsze heartbeaty. */
        public static void main(String[] args) { }
    }

    static class Exercise12_DisableSelfPreservationAndExplainWhy {
        /* 🧪 Zadanie 12: Wylacz self-preservation (jak W lekcji) I wyjasnij, DLACZEGO W demo TO ma sens (W PRODUKCJI nie zawsze). */
        public static void main(String[] args) { }
    }

    static class Exercise13_SimulateInstanceCrashByForceKillingContextWithoutGracefulShutdown {
        /* 🧪 Zadanie 13: Zasymuluj AWARIE instancji (BEZ graceful shutdown) I ZAOBSERWUJ, ze rejestr TRZYMA ja do timeoutu. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConfigureCustomEurekaInstanceId {
        /* 🧪 Zadanie 14: Skonfiguruj WLASNE `eureka.instance.instance-id` (przydatne DLA WIELU instancji NA localhost). */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddCustomMetadataToEurekaInstance {
        /* 🧪 Zadanie 15: Dodaj WLASNE metadane (`eureka.instance.metadata-map.*`) DO instancji I ODCZYTAJ je Z rejestru. */
        public static void main(String[] args) { }
    }

    static class Exercise16_UsePreferIpAddressAndExplainWhenUseful {
        /* 🧪 Zadanie 16: Uzyj `eureka.instance.prefer-ip-address=true` I wyjasnij, KIEDY jest przydatne (kontenery). */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainDifferenceBetweenReadOnlyAndReadWriteRegistryCache {
        /* 🧪 Zadanie 17: Bez terminala - wyjasnij ROZNICE MIEDZY read-only A read-write cache rejestru Eureki. */
        public static void main(String[] args) { }
    }

    static class Exercise18_RegisterThreeDifferentServicesAndListAllApplications {
        /* 🧪 Zadanie 18: Zarejestruj 3 rozne serwisy I wypisz WSZYSTKIE aplikacje ZAREJESTROWANE W Eurece. */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareEurekaDashboardWithProgrammaticQuery {
        /* 🧪 Zadanie 19: Porownaj wbudowany dashboard Eureki (`/`) Z programowym zapytaniem `PeerAwareInstanceRegistry`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyServerPort0IsUsedInAllContextsInThisChapter {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, DLACZEGO WSZYSTKIE konteksty W tym rozdziale uzywaja `server.port=0`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildTwoPeerAwareEurekaServersReplicatingToEachOther {
        /* 🧪 Zadanie 21: Zbuduj DWA WZAJEMNIE swiadome serwery Eureka (peer replication) - zaawansowane, opcjonalne. */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureImpactOfRegistryFetchIntervalOnClientCacheStaleness {
        /* 🧪 Zadanie 22: Zmierz wplyw `eureka.client.registry-fetch-interval-seconds` NA "zastarzalosc" cache'u klienta. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomHealthCheckHandlerForEurekaInstance {
        /* 🧪 Zadanie 23: Zaimplementuj WLASNY `HealthCheckHandler` (Eureka), zamiast domyslnego statusu UP. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExplainZoneAwareRoutingConcept {
        /* 🧪 Zadanie 24: Bez terminala - wyjasnij koncepcje "zone-aware routing" W Eurece (preferowanie tej samej strefy). */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareEurekaStandaloneVsPeerToPeerModeTradeoffs {
        /* 🧪 Zadanie 25: Bez terminala - porownaj kompromisy trybu standalone (jak W lekcji) A peer-to-peer Eureki. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignMigrationFromEurekaToKubernetesNativeDiscovery {
        /* 🧪 Zadanie 26: Zaprojektuj (na papierze) migracje Z Eureki NA natywny discovery Kubernetesa. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomEurekaEventListenerLoggingRegistrations {
        /* 🧪 Zadanie 27: Zaimplementuj WLASNY listener zdarzen Eureki (`EurekaInstanceRegisteredEvent` itp.), logujacy rejestracje. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExplainWhyEurekaIsInMaintenanceModeAndAlternatives {
        /* 🧪 Zadanie 28: Zbadaj I wyjasnij, DLACZEGO Netflix Eureka jest W trybie utrzymaniowym I JAKIE sa dzisiejsze alternatywy. */
        public static void main(String[] args) { }
    }

    static class Exercise29_LoadTestRegistryWithManyRapidRegistrationsAndDeregistrations {
        /* 🧪 Zadanie 29: Obciaz rejestr WIELOMA szybkimi rejestracjami/wyrejestrowaniami I zmierz stabilnosc. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionReadyEurekaDeploymentChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" DLA wdrozenia Eureki (self-preservation, peer replication, monitoring). */
        public static void main(String[] args) { }
    }
}
