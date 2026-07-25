package com.example.javaquest._31_spring_cloud_microservices.Lesson02_ServiceDiscoveryConcepts;

public class _Exercises_Lesson02_ServiceDiscoveryConcepts {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyHardcodedAddressesFailInMicroservices {
        /* 🧪 Zadanie 1: Bez terminala - wyjasnij, DLACZEGO zahardkodowane adresy zawodza W mikroserwisach. */
        public static void main(String[] args) { }
    }

    static class Exercise02_BuildSimpleInMemoryServiceRegistryUsingMap {
        /* 🧪 Zadanie 2: Zbuduj PROSTY rejestr uslug W PAMIECI uzywajac `Map<String, List<String>>` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise03_RegisterMultipleInstancesOfSameService {
        /* 🧪 Zadanie 3: Zarejestruj WIELE instancji TEGO SAMEGO serwisu W swoim rejestrze. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ThrowExceptionWhenDiscoveringUnknownService {
        /* 🧪 Zadanie 4: Rzuc wyjatek, GDY klient probuje odnalezc NIEZAREJESTROWANY serwis. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainHeartbeatMechanismPurpose {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij CEL mechanizmu heartbeat. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementSimpleHeartbeatTimeoutEvictionInMemory {
        /* 🧪 Zadanie 6: Zaimplementuj PROSTA ewikcje instancji PO przekroczeniu timeoutu heartbeatu (znacznik czasu W mapie). */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareClientSideAndServerSideDiscoveryInOwnWords {
        /* 🧪 Zadanie 7: Bez terminala - porownaj WLASNYMI slowami client-side I server-side discovery. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListRealWorldExamplesOfServerSideDiscovery {
        /* 🧪 Zadanie 8: Wymien PRZYKLADY server-side discovery Z prawdziwego swiata (np. Kubernetes Service). */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyDnsCachingIsProblematicForDiscovery {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, DLACZEGO cache'owanie DNS (TTL) jest PROBLEMEM DLA service discovery. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListMetadataServiceRegistryCanStorePerInstance {
        /* 🧪 Zadanie 10: Wymien METADANE, JAKIE rejestr moze przechowywac DLA kazdej instancji (wersja, strefa, wagi). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementRoundRobinDiscoveryOverInMemoryRegistry {
        /* 🧪 Zadanie 11: Zaimplementuj wybor instancji metoda round-robin NAD SWOIM rejestrem W pamieci. */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddHealthStatusFieldToRegisteredInstance {
        /* 🧪 Zadanie 12: Dodaj pole statusu zdrowia (UP/DOWN) DO zarejestrowanej instancji. */
        public static void main(String[] args) { }
    }

    static class Exercise13_FilterOutUnhealthyInstancesDuringDiscovery {
        /* 🧪 Zadanie 13: Odfiltruj NIEZDROWE instancje PODCZAS wyszukiwania (discovery). */
        public static void main(String[] args) { }
    }

    static class Exercise14_SimulateInstanceCrashAndObserveEviction {
        /* 🧪 Zadanie 14: Zasymuluj awarie instancji (brak heartbeatu) I ZAOBSERWUJ jej ewikcje. */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignRegistryApiWithRegisterDiscoverDeregisterMethods {
        /* 🧪 Zadanie 15: Zaprojektuj API rejestru Z metodami `register`/`discover`/`deregister`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainWhatHappensWhenRegistryItselfGoesDown {
        /* 🧪 Zadanie 16: Bez terminala - wyjasnij, CO SIE DZIEJE, gdy SAM rejestr (Eureka Server) przestaje dzialac. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainSelfPreservationModeConceptually {
        /* 🧪 Zadanie 17: Zbadaj I opisz koncepcyjnie tryb "self-preservation" Eureki (zapowiedz Lesson03). */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareRegistryWithConfigServerResponsibilities {
        /* 🧪 Zadanie 18: Powiaz Z Lesson04 - porownaj odpowiedzialnosc rejestru uslug Z Config Serverem (RÓZNE problemy!). */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementRegistrySnapshotPrintout {
        /* 🧪 Zadanie 19: Zaimplementuj wypisanie PELNEGO zrzutu (snapshot) stanu rejestru NA konsole. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyCachingClientSideDiscoveryResultsMatters {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, DLACZEGO klient CACHE'UJE lokalnie wynik discovery (Lesson03: Eureka client cache). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignMultiZoneAwareDiscoveryStrategy {
        /* 🧪 Zadanie 21: Zaprojektuj (na papierze) strategie discovery SWIADOMA stref dostepnosci (availability zones). */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareEurekaConsulAndKubernetesNativeDiscovery {
        /* 🧪 Zadanie 22: Zbadaj I porownaj Eureke, Consul I natywny discovery Kubernetesa. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ExplainCapTheoremImplicationsForServiceRegistry {
        /* 🧪 Zadanie 23: Bez terminala - omow implikacje twierdzenia CAP DLA rejestru uslug (Eureka wybiera AP, NIE CP). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignFailoverStrategyWhenRegistryUnavailable {
        /* 🧪 Zadanie 24: Zaprojektuj strategie failover KLIENTA, gdy rejestr jest CHWILOWO niedostepny (uzyj cache'u). */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementThreadSafeRegistryWithConcurrentAccess {
        /* 🧪 Zadanie 25: Zaimplementuj rejestr BEZPIECZNY watkowo (powiazanie Z `_05_multithreading`) Z rownoczesnym dostepem WIELU watkow. */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureDiscoveryLatencyOverhead {
        /* 🧪 Zadanie 26: Zmierz (symulowany) narzut czasowy discovery WZGLEDEM zahardkodowanego adresu. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExplainServiceMeshAsAlternativeToClientSideDiscovery {
        /* 🧪 Zadanie 27: Zbadaj I opisz service mesh (np. Istio) jako ALTERNATYWE DLA client-side discovery. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignGracefulShutdownDeregistrationFlow {
        /* 🧪 Zadanie 28: Zaprojektuj przeplyw "graceful shutdown" - instancja WYREJESTROWUJE SIE SAMA PRZED zamknieciem. */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareEventualConsistencyOfRegistryWithStrongConsistency {
        /* 🧪 Zadanie 29: Bez terminala - porownaj eventual consistency rejestru (Eureka) Z silna spojnoscia (np. ZooKeeper). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignDiscoveryStrategyForHundredsOfServiceInstances {
        /* 🧪 Zadanie 30: Zaprojektuj (na papierze) strategie discovery DLA systemu Z SETKAMI instancji (skalowalnosc rejestru). */
        public static void main(String[] args) { }
    }
}
