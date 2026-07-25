package com.example.javaquest._31_spring_cloud_microservices.Lesson11_DistributedTracingIntro;

public class _Exercises_Lesson11_DistributedTracingIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainProblemDistributedTracingSolves {
        /* 🧪 Zadanie 1: Bez terminala - wyjasnij PROBLEM, KTORY rozwiazuje distributed tracing. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainDifferenceBetweenTraceIdAndSpanId {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij ROZNICE MIEDZY `traceId` A `spanId`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateSpanUsingTracerBean {
        /* 🧪 Zadanie 3: Utworz span uzywajac beana `Tracer` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateNestedChildSpanAndCompareTraceIds {
        /* 🧪 Zadanie 4: Utworz ZAGNIEZDZONY span-dziecko I porownaj `traceId` obu. */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateTwoIndependentTracesAndCompareTraceIds {
        /* 🧪 Zadanie 5: Utworz DWA NIEZALEZNE slady (traces) I porownaj ich `traceId` (POWINNY sie ROZNIC). */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhatSamplingProbabilityMeans {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, CO OZNACZA `management.tracing.sampling.probability`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyNotSample100PercentInProduction {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO W PRODUKCJI ZWYKLE NIE probkuje sie 100% zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddSpanNameAndReadItBack {
        /* 🧪 Zadanie 8: Dodaj nazwe spanu (`.name(...)`) I odczytaj ja PONOWNIE. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainMicrometerTracingAsAbstraction {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, DLACZEGO Micrometer Tracing jest "abstrakcja jak SLF4J". */
        public static void main(String[] args) { }
    }

    static class Exercise10_UseTryWithResourcesForSpanScope {
        /* 🧪 Zadanie 10: Uzyj try-with-resources DLA `Tracer.SpanInScope` (jak W lekcji) I wyjasnij PO CO. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddTagsToSpanForExtraContext {
        /* 🧪 Zadanie 11: Dodaj tagi (`.tag(klucz, wartosc)`) DO spanu Z DODATKOWYM kontekstem. */
        public static void main(String[] args) { }
    }

    static class Exercise12_RecordExceptionOnSpanWhenErrorOccurs {
        /* 🧪 Zadanie 12: Zarejestruj wyjatek NA spanie (`span.error(...)`), GDY wystapi blad. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ChainThreeServicesAndVerifySameTraceIdThroughout {
        /* 🧪 Zadanie 13: Polacz LANCUCHOWO 3 "serwisy" I zweryfikuj TEN SAM `traceId` PRZEZ CALY lancuch. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainWhatHappensWhenSpanIsNotEnded {
        /* 🧪 Zadanie 14: Bez terminala - wyjasnij, CO SIE DZIEJE, GDY span NIE ZOSTANIE zakonczony (`span.end()` pominiete). */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseCurrentSpanToAccessActiveSpanWithoutPassingItExplicitly {
        /* 🧪 Zadanie 15: Uzyj `tracer.currentSpan()` DO dostepu DO aktywnego spanu BEZ jawnego przekazywania. */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureSpanDurationUsingStartAndEndTimestamps {
        /* 🧪 Zadanie 16: Zmierz czas trwania spanu (roznica MIEDZY startem A zakonczeniem). */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainHowTraceContextPropagatesOverHttpHeaders {
        /* 🧪 Zadanie 17: Bez terminala - wyjasnij, JAK kontekst sladu PROPAGUJE SIE PRZEZ naglowki HTTP (`traceparent`/B3). */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareB3PropagationWithW3CTraceContext {
        /* 🧪 Zadanie 18: Zbadaj I porownaj propagacje B3 (Zipkin/Brave) Z W3C Trace Context. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementCustomSpanNamingStrategy {
        /* 🧪 Zadanie 19: Zaimplementuj WLASNA strategie nazywania spanow (np. "warstwa.operacja"). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainRelationshipBetweenTracingAndLoggingMdc {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij RELACJE MIEDZY tracingiem A MDC logowania (powiazanie Z `_19_security_basics/Lesson19`). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomObservationHandlerForBusinessMetrics {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `ObservationHandler` DLA metryk biznesowych. */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareSamplingStrategiesHeadBasedVsTailBased {
        /* 🧪 Zadanie 22: Zbadaj I porownaj probkowanie "head-based" (decyzja NA starcie) Z "tail-based" (decyzja NA koniec). */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignTracingStrategyForAsyncMessageDrivenFlow {
        /* 🧪 Zadanie 23: Zaprojektuj strategie tracingu DLA przeplywu ASYNCHRONICZNEGO (komunikaty, powiazanie Z `_30_spring_messaging_and_async`). */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementBaggagePropagationForCrossCuttingContext {
        /* 🧪 Zadanie 24: Zaimplementuj "baggage" (dodatkowy kontekst PROPAGOWANY WRAZ ze sladem, NIE TYLKO ID). */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasurePerformanceOverheadOf100PercentSampling {
        /* 🧪 Zadanie 25: Zmierz narzut wydajnosciowy probkowania 100% W PORONANIU Z 10%. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignTraceIdInjectionIntoErrorResponseForSupportTeam {
        /* 🧪 Zadanie 26: Zaprojektuj wstrzykiwanie `traceId` DO odpowiedzi bledu (powiazanie Z `_18_rest_api/Lesson12`) DLA zespolu wsparcia. */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareOpenTelemetryWithMicrometerTracingConceptually {
        /* 🧪 Zadanie 27: Zbadaj I porownaj koncepcyjnie OpenTelemetry Z Micrometer Tracing. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCustomPropagatorForNonHttpTransport {
        /* 🧪 Zadanie 28: Zaprojektuj WLASNY propagator kontekstu DLA transportu INNEGO NIZ HTTP (np. komunikaty). */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignRetentionPolicyForTraceDataAtScale {
        /* 🧪 Zadanie 29: Zaprojektuj polityke retencji danych sladow PRZY duzej skali (koszt przechowywania). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionTracingStrategyChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" strategii tracingu (probkowanie/retencja/alerting/koszt). */
        public static void main(String[] args) { }
    }
}
