package com.example.javaquest._31_spring_cloud_microservices.Lesson12_DistributedTracingWithZipkin;

public class _Exercises_Lesson12_DistributedTracingWithZipkin {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ConfigureZipkinTracingEndpointProperty {
        /* 🧪 Zadanie 1: Skonfiguruj `management.zipkin.tracing.endpoint` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhatZipkinVisualizesThatRawIdsDoNot {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, CO Zipkin wizualizuje, CZEGO surowe ID (Lesson11) NIE POKAZUJA. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CheckIfZipkinIsReachableBeforeSendingSpans {
        /* 🧪 Zadanie 3: Sprawdz OSIAGALNOSC Zipkina (socket connect) PRZED wyslaniem spanow (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise04_InstallAndRunZipkinLocallyIfDockerAvailable {
        /* 🧪 Zadanie 4: Jesli MASZ Dockera - uruchom Zipkin lokalnie (`docker run -d -p 9411:9411 openzipkin/zipkin`) I zweryfikuj demo Z PRAWDZIWYMI danymi. */
        public static void main(String[] args) { }
    }

    static class Exercise05_QueryZipkinApiForServicesList {
        /* 🧪 Zadanie 5: Odpytaj `GET /api/v2/services` Zipkin API (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise06_QueryZipkinApiForTraceById {
        /* 🧪 Zadanie 6: Odpytaj `GET /api/v2/trace/{traceId}` DLA konkretnego sladu. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhySpansAreSentAsynchronously {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO spany sa wysylane ASYNCHRONICZNIE (BEZ blokowania glownego zadania). */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddCustomTagToSpanVisibleInZipkinUi {
        /* 🧪 Zadanie 8: Dodaj WLASNY tag DO spanu, widoczny W Zipkin UI. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhatHappensWhenZipkinIsUnreachable {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, CO SIE DZIEJE Z aplikacja, GDY Zipkin jest NIEOSIAGALNY (spany "dropped", aplikacja DZIALA DALEJ). */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareZipkinUiTraceViewWithLesson11ConsoleOutput {
        /* 🧪 Zadanie 10: Porownaj widok sladu W Zipkin UI Z surowym outputem konsoli Z Lesson11. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TraceMultiServiceCallChainAndViewInZipkin {
        /* 🧪 Zadanie 11: Sledz LANCUCH WIELU serwisow I OBEJRZYJ pelne drzewo W Zipkinie (WYMAGA Dockera). */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureSpanDurationDiscrepancyBetweenServices {
        /* 🧪 Zadanie 12: Zmierz roznice czasu trwania MIEDZY spanami (ktory krok jest NAJWOLNIEJSZY). */
        public static void main(String[] args) { }
    }

    static class Exercise13_FilterTracesInZipkinUiByServiceName {
        /* 🧪 Zadanie 13: Przefiltruj slady W Zipkin UI PO nazwie serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise14_FilterTracesInZipkinUiByTagValue {
        /* 🧪 Zadanie 14: Przefiltruj slady W Zipkin UI PO wartosci tagu (np. "klient=Kasia"). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConfigureLowerSamplingProbabilityAndObserveFewerTraces {
        /* 🧪 Zadanie 15: Skonfiguruj NIZSZE prawdopodobienstwo probkowania I ZAOBSERWUJ MNIEJ sladow W Zipkinie. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementErrorSpanAndObserveItMarkedInZipkinUi {
        /* 🧪 Zadanie 16: Zaimplementuj span BLEDU I ZAOBSERWUJ, ze Zipkin UI OZNACZA go WIZUALNIE (czerwony). */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareZipkinStorageBackendsInMemoryVsElasticsearch {
        /* 🧪 Zadanie 17: Zbadaj I porownaj backend przechowywania Zipkina "in-memory" Z Elasticsearch (PRODUKCYJNY wybor). */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignFallbackBehaviorWhenTracingBackendUnavailable {
        /* 🧪 Zadanie 18: Zaprojektuj zachowanie fallback, GDY backend tracingu jest NIEDOSTEPNY (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareZipkinWithJaegerConceptually {
        /* 🧪 Zadanie 19: Zbadaj I porownaj koncepcyjnie Zipkin Z Jaegerem (alternatywa OD Uber/CNCF). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyZipkinIsTypicallyItsOwnCentralService {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, DLACZEGO Zipkin JEST ZWYKLE WSPOLNYM, centralnym serwisem (jak Config Server, Lesson04). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DeployZipkinWithPersistentStorageUsingDocker {
        /* 🧪 Zadanie 21: Wdroz Zipkin Z TRWALYM przechowywaniem (docker-compose Z Elasticsearch/Cassandra). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomSpanExporterForAlternativeBackend {
        /* 🧪 Zadanie 22: Zaimplementuj WLASNY eksporter spanow DLA innego backendu (nie Zipkin). */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignSamplingStrategyBalancingCostAndVisibility {
        /* 🧪 Zadanie 23: Zaprojektuj strategie probkowania BALANSUJACA koszt (storage) Z widocznoscia (debugging). */
        public static void main(String[] args) { }
    }

    static class Exercise24_IntegrateZipkinTraceIdIntoLogAggregationSystem {
        /* 🧪 Zadanie 24: Zintegruj `traceId` Z systemem agregacji logow (powiazanie Z `_19_security_basics/Lesson19`). */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareOpenTelemetryCollectorArchitectureWithDirectZipkinExport {
        /* 🧪 Zadanie 25: Zbadaj I porownaj architekture OpenTelemetry Collector Z BEZPOSREDNIM eksportem DO Zipkina. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignMultiRegionTracingArchitecture {
        /* 🧪 Zadanie 26: Zaprojektuj architekture tracingu DLA systemu rozproszonego NA WIELE regionow. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementTraceBasedAlertingForSlowRequests {
        /* 🧪 Zadanie 27: Zaprojektuj alertowanie OPARTE NA sladach DLA WOLNYCH zadan (powyzej progu). */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignPrivacyStrategyForSensitiveDataInSpanTags {
        /* 🧪 Zadanie 28: Zaprojektuj strategie prywatnosci DLA WRAZLIWYCH danych W tagach spanow (NIE loguj PESEL/haslo). */
        public static void main(String[] args) { }
    }

    static class Exercise29_MeasureStorageGrowthRateForHighTrafficService {
        /* 🧪 Zadanie 29: Oszacuj tempo wzrostu przechowywania DLA serwisu Z DUZYM ruchem (probkowanie 100% vs 1%). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionTracingInfrastructureChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" infrastruktury tracingu (HA Zipkina, retencja, koszt, alerting). */
        public static void main(String[] args) { }
    }
}
