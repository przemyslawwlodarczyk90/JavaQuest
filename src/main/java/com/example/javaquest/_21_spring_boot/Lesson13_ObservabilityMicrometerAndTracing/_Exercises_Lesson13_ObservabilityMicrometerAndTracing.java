package com.example.javaquest._21_spring_boot.Lesson13_ObservabilityMicrometerAndTracing;

public class _Exercises_Lesson13_ObservabilityMicrometerAndTracing {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainMicrometerAsFacade {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij analogie "Micrometer = SLF4J dla
         * metryk".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateOwnCounterAndIncrement {
        /*
         * 🧪 Zadanie 2:
         * Utworz WLASNY `Counter` przez `MeterRegistry` - zwieksz go
         * kilkukrotnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateOwnTimerRecordingWork {
        /*
         * 🧪 Zadanie 3:
         * Utworz WLASNY `Timer` mierzacy czas WYMYSLONEJ operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainDifferenceBetweenCounterAndGauge {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala (dokumentacja): wyjasnij roznice miedzy
         * `Counter` (tylko rosnie) a `Gauge` (moze rosnac i maleic).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementOwnGauge {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj WLASNY `Gauge` (np. "aktualna liczba elementow w
         * kolejce").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseObservationApiForOwnOperation {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `Observation.observe(...)` dla WLASNEJ, symulowanej
         * operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainBoot2VsBoot3TracingDifference {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, jak Boot 3 zmienil podejscie do
         * distributed tracing wzgledem Boot 2 (Spring Cloud Sleuth).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListMetricTagsAndExplainPurpose {
        /*
         * 🧪 Zadanie 8:
         * Dodaj TAGI (np. `Tags.of("status", "success")`) do WLASNEGO
         * licznika - wyjasnij, po co sluza tagi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareMetricNamingConventions {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala (dokumentacja Micrometer): sprawdz KONWENCJE
         * nazewnicze metryk (kropkowane, np. `http.server.requests`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhatBravAndOpenTelemetryAre {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (dokumentacja): sprawdz, czym sa Brave i
         * OpenTelemetry w kontekscie Micrometer Tracing.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementDistributionSummary {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `DistributionSummary` (rozklad WARTOSCI, nie CZASU) - np.
         * rozmiar przesylanych plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPercentileConfiguration {
        /*
         * 🧪 Zadanie 12:
         * Skonfiguruj percentyle (`publishPercentiles(0.5, 0.95, 0.99)`)
         * dla `Timer` - wyjasnij, PO CO percentyle sa lepsze niz
         * srednia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementMeterFilterExcludingSensitiveTags {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj `MeterFilter` USUWAJACY/MASKUJACY WRAZLIWE tagi
         * (powiaz z `_19_security_basics/Lesson19`) PRZED publikacja
         * metryki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomObservationHandler {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj WLASNY `ObservationHandler` REAGUJACY na
         * start/koniec KAZDEJ obserwacji (np. dodatkowe logowanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementHighCardinalityVsLowCardinalityTags {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij roznice `lowCardinalityKeyValue` vs
         * `highCardinalityKeyValue` w `Observation` - DLACZEGO ma to
         * znaczenie dla systemow metryk (np. Prometheus)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureMultipleOperationsAndCompareTimers {
        /*
         * 🧪 Zadanie 16:
         * Zmierz 3 RÓZNE, symulowane operacje (roznym czasem trwania) -
         * porownaj ich statystyki Timer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementConditionalMetricBasedOnFeatureFlag {
        /*
         * 🧪 Zadanie 17:
         * Polacz z `Lesson04_AutoConfiguration` - zbieraj METRYKE TYLKO
         * gdy okreslona feature flaga jest wlaczona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExposeCustomMetricViaActuator {
        /*
         * 🧪 Zadanie 18:
         * Uruchom PELNA aplikacje webowa (jak Lesson12) z WLASNA
         * metryka - sprawdz ja PRZEZ `/actuator/metrics/nazwa`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementErrorRateMetricForFailingOperations {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj 2 liczniki ("sukces"/"blad") dla symulowanej
         * operacji - oblicz "wskaznik bledow" (error rate) na ich
         * podstawie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildMicrometerCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" typow metryk Micrometer (Counter/
         * Timer/Gauge/DistributionSummary) z KROTKIM opisem
         * zastosowania KAZDEGO.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomMeterRegistryForTesting {
        /*
         * 🧪 Zadanie 21:
         * Uzyj `SimpleMeterRegistry` (implementacja w-pamieci,
         * przydatna do TESTOW) ZAMIAST domyslnej - zweryfikuj TE SAME
         * metryki BEZ pelnego kontekstu Springa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCompositeRegistryForMultipleBackends {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala (dokumentacja): sprawdz `CompositeMeterRegistry` -
         * jak PUBLIKOWAC metryki do WIELU systemow naraz (Prometheus +
         * CloudWatch)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementFullObservationWithParentChildRelationship {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj ZAGNIEZDZONE obserwacje (rodzic-dziecko,
         * `Observation.start()` wewnatrz INNEJ obserwacji) - symulacja
         * "tracingu" bez prawdziwego tracera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementPrometheusFormatExportSimulation {
        /*
         * 🧪 Zadanie 24:
         * Zbadaj (dokumentacja `micrometer-registry-prometheus`, NIE
         * dodawaj do pom.xml) jak wygladalby format eksportu metryk
         * DLA Prometheusa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAlertingRuleBasedOnMetricThreshold {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj prosta regule alertujaca - jesli WARTOSC
         * licznika bledow PRZEKROCZY prog W OKNIE czasowym, wypisz
         * ostrzezenie (powiaz z `_19_security_basics/Lesson19`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareObservabilityPillarsMetricsLogsTraces {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wyjasnij "3 filary observability" (metryki,
         * logi, slady/traces) - jak Boot 3 Observation API LACZY
         * WSZYSTKIE trzy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomTimedAnnotationViaAop {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_20_spring_core/Lesson21` - zaimplementuj WLASNA
         * adnotacje `@Timed` (nie wbudowana Micrometer) + `@Aspect`
         * automatycznie mierzacy CZAS oznaczonych metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementResourceUtilizationDashboardSimulation {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj (konsolowy) "dashboard" pokazujacy NA ZYWO WSZYSTKIE
         * metryki JVM (pamiec, watki, GC) dostepne PRZEZ auto-konfigurowane
         * `Gauge`-y Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementBusinessMetricsForDomainEvents {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_20_spring_core/Lesson20` - zbieraj METRYKI
         * BIZNESOWE (nie techniczne) na podstawie publikowanych zdarzen
         * domenowych (np. "liczba zamowien na minute").
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteObservabilityStackCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "stos observability" - metryki biznesowe +
         * techniczne + wlasny `ObservationHandler` + symulowany alerting -
         * jeden spojny system monitorujacy.
         */
        public static void main(String[] args) { }
    }
}
