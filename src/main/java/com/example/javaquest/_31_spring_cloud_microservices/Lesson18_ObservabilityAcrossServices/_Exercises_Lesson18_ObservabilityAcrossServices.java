package com.example.javaquest._31_spring_cloud_microservices.Lesson18_ObservabilityAcrossServices;

public class _Exercises_Lesson18_ObservabilityAcrossServices {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListThreePillarsOfObservability {
        /* 🧪 Zadanie 1: Wymien "trzy filary obserwowalnosci" I krotko opisz kazdy. */
        public static void main(String[] args) { }
    }

    static class Exercise02_LogTraceIdAlongsideBusinessEvent {
        /* 🧪 Zadanie 2: Zaloguj `traceId` OBOK zdarzenia biznesowego (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise03_IncrementCustomCounterUsingMeterRegistry {
        /* 🧪 Zadanie 3: Zwieksz WLASNY licznik uzywajac `MeterRegistry.counter(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_RecordCustomTimerForMethodDuration {
        /* 🧪 Zadanie 4: Zarejestruj WLASNY timer DLA czasu trwania metody. */
        public static void main(String[] args) { }
    }

    static class Exercise05_QueryMetricViaActuatorHttpEndpoint {
        /* 🧪 Zadanie 5: Odpytaj metryke PRZEZ endpoint HTTP `/actuator/metrics/{nazwa}` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyAllThreePillarsShouldShareCorrelationId {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, DLACZEGO wszystkie trzy filary POWINNY dzielic WSPOLNY identyfikator korelacji. */
        public static void main(String[] args) { }
    }

    static class Exercise07_AddTagToCounterForDimensionalMetrics {
        /* 🧪 Zadanie 7: Dodaj tag DO licznika DLA metryk WIELOWYMIAROWYCH (np. "status=success"/"status=error"). */
        public static void main(String[] args) { }
    }

    static class Exercise08_QueryHealthEndpointAndInterpretStatus {
        /* 🧪 Zadanie 8: Odpytaj `/actuator/health` I zinterpretuj status. */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareLogLevelUsageForDifferentEventSeverities {
        /* 🧪 Zadanie 9: Bez terminala - porownaj uzycie poziomow logowania DLA roznej wagi zdarzen (INFO/WARN/ERROR). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyMetricsAreCheaperThanLogsAtScale {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO metryki SA TANSZE (storage) NIZ logi PRZY duzej skali. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCustomHealthIndicatorReflectingBusinessState {
        /* 🧪 Zadanie 11: Zaimplementuj WLASNY `HealthIndicator` odzwierciedlajacy stan biznesowy (powiazanie Z `_21_spring_boot/Lesson12`). */
        public static void main(String[] args) { }
    }

    static class Exercise12_CreateGaugeMetricReflectingCurrentQueueSize {
        /* 🧪 Zadanie 12: Stworz metryke typu gauge odzwierciedlajaca AKTUALNY rozmiar kolejki/cache'u. */
        public static void main(String[] args) { }
    }

    static class Exercise13_CorrelateErrorLogWithMetricSpikeAndTraceId {
        /* 🧪 Zadanie 13: Skoreluj log bledu Z WZROSTEM metryki I `traceId` - zbuduj PELNY lancuch debugowania. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementStructuredJsonLoggingWithTraceContext {
        /* 🧪 Zadanie 14: Zaimplementuj STRUKTURALNE logowanie JSON Z kontekstem tracingu (powiazanie Z `_13_libraries/Lesson16`). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExposeCustomMetricsToPrometheusFormat {
        /* 🧪 Zadanie 15: Wystaw WLASNE metryki W formacie Prometheus (`/actuator/prometheus`, WYMAGA `micrometer-registry-prometheus`). */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementAlertingRuleBasedOnErrorRateThreshold {
        /* 🧪 Zadanie 16: Zaprojektuj regule alertowania OPARTA NA progu wskaznika bledow. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareRedMethodWithUseMethodForMetrics {
        /* 🧪 Zadanie 17: Zbadaj I porownaj metode RED (Rate/Errors/Duration) Z metoda USE (Utilization/Saturation/Errors). */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementDistributedContextPropagationAcrossAsyncBoundary {
        /* 🧪 Zadanie 18: Zaimplementuj propagacje kontekstu (traceId) PRZEZ granice ASYNCHRONICZNA (`@Async`, `_30_spring_messaging_and_async`). */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildDashboardConceptCombiningLogsMetricsTraces {
        /* 🧪 Zadanie 19: Zaprojektuj koncepcje dashboardu LACZACEGO logi+metryki+slady W JEDNYM widoku. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainObservabilityVsMonitoringDifference {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij ROZNICE MIEDZY "observability" (odpowiadanie NA NOWE pytania) A "monitoring" (znane pytania). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementOpenTelemetryCollectorPipelineConcept {
        /* 🧪 Zadanie 21: Zaprojektuj (koncepcyjnie) potok OpenTelemetry Collector LACZACY WSZYSTKIE trzy filary. */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignSliSloErrorBudgetForCriticalService {
        /* 🧪 Zadanie 22: Zbadaj I zaprojektuj SLI/SLO/error budget DLA KRYTYCZNEGO serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomMeterFilterForCardinalityControl {
        /* 🧪 Zadanie 23: Zaimplementuj WLASNY `MeterFilter` KONTROLUJACY kardynalnosc tagow (unikaj eksplozji metryk). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignCostAwareObservabilityStrategyAtScale {
        /* 🧪 Zadanie 24: Zaprojektuj strategie obserwowalnosci SWIADOMA kosztow PRZY duzej skali (probkowanie/agregacja/retencja). */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementSyntheticMonitoringForCriticalUserJourney {
        /* 🧪 Zadanie 25: Zaprojektuj monitoring syntetyczny (regularne, sztuczne zadania) DLA KRYTYCZNEJ sciezki uzytkownika. */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareVendorSpecificApmWithOpenStandardsObservability {
        /* 🧪 Zadanie 26: Zbadaj I porownaj rozwiazania APM (Datadog/New Relic) Z otwartymi standardami (OpenTelemetry). */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAnomalyDetectionStrategyForMetricTimeSeries {
        /* 🧪 Zadanie 27: Zaprojektuj strategie wykrywania anomalii W szeregach czasowych metryk. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCrossServiceDashboardForBusinessKpis {
        /* 🧪 Zadanie 28: Zaprojektuj dashboard MIEDZYSERWISOWY DLA wskaznikow biznesowych (NIE TYLKO technicznych). */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignPostIncidentReviewProcessUsingObservabilityData {
        /* 🧪 Zadanie 29: Zaprojektuj proces analizy POINCYDENTALNEJ WYKORZYSTUJACY dane obserwowalnosci. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionObservabilityStackChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" stosu obserwowalnosci (logi/metryki/slady/alerty/koszt). */
        public static void main(String[] args) { }
    }
}
