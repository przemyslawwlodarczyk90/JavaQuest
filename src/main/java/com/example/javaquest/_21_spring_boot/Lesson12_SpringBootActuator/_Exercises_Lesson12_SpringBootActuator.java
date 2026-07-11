package com.example.javaquest._21_spring_boot.Lesson12_SpringBootActuator;

public class _Exercises_Lesson12_SpringBootActuator {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatActuatorProvides {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, co daje `spring-boot-starter-actuator`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CallHealthEndpointAndInspectResponse {
        /*
         * 🧪 Zadanie 2:
         * Odtworz wywolanie `/actuator/health` z teorii - wypisz PELNA
         * odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExposeAllActuatorEndpoints {
        /*
         * 🧪 Zadanie 3:
         * Ustaw `management.endpoints.web.exposure.include=*` - wypisz
         * WSZYSTKIE dostepne endpointy (`/actuator`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CallInfoEndpoint {
        /*
         * 🧪 Zadanie 4:
         * Wywolaj `/actuator/info` - dodaj WLASNE metadane przez
         * `info.app.*` w konfiguracji i zweryfikuj ich obecnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CallSpecificMetricByName {
        /*
         * 🧪 Zadanie 5:
         * Wywolaj `/actuator/metrics/jvm.memory.used` (KONKRETNA
         * metryke) - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyMostEndpointsAreHiddenByDefault {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, DLACZEGO wiekszosc endpointow
         * Actuatora NIE jest wystawiona domyslnie (powiaz z
         * `_19_security_basics`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementOwnHealthIndicator {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj WLASNY `HealthIndicator` dla WYMYSLONEGO
         * zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SimulateDownStatusInHealthIndicator {
        /*
         * 🧪 Zadanie 8:
         * Zmien logike z Zadania 7, zeby zwracala `Health.down()` -
         * zweryfikuj, ze OGOLNY status `/actuator/health` TEZ zmienia sie
         * na DOWN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListSecurityRisksOfExposingActuatorPublicly {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wymien RYZYKA wystawienia Actuatora BEZ
         * zabezpieczenia (np. `/actuator/env` ujawnia zmienne
         * srodowiskowe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareLivenessAndReadinessProbes {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (dokumentacja): sprawdz `/actuator/health/liveness`
         * i `/actuator/health/readiness` - jak sie ROZNIA (Kubernetes)?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCustomMetricWithMicrometer {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj WLASNY licznik (`Counter`) przez Micrometer
         * (zapowiedz `Lesson13`) - zwieksz go i sprawdz przez
         * `/actuator/metrics`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomInfoContributor {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj WLASNY `InfoContributor` (interfejs Actuatora)
         * DODAJACY dane do `/actuator/info` PROGRAMOWO (nie tylko przez
         * wlasciwosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SecureActuatorEndpointsConceptually {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala (zapowiedz `_24_spring_security`): zaprojektuj,
         * jak ZABEZPIECZYC endpointy Actuatora (np. `/actuator/**`
         * dostepne TYLKO dla roli ADMIN).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomEndpointWithReadOperation {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj WLASNY, niestandardowy endpoint Actuatora
         * (`@Endpoint`+`@ReadOperation`) - np. `/actuator/features`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCustomEndpointWithWriteOperation {
        /*
         * 🧪 Zadanie 15:
         * Rozbuduj Zadanie 14 o `@WriteOperation` - endpoint POZWALAJACY
         * ZMIENIC stan aplikacji przez POST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ChangeHealthDetailVisibilityByRole {
        /*
         * 🧪 Zadanie 16:
         * Skonfiguruj `management.endpoint.health.show-details=when-authorized` -
         * bez pelnego Security (zapowiedz `_24_spring_security`) wyjasnij
         * KONCEPCJE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDiskSpaceHealthIndicatorIntegration {
        /*
         * 🧪 Zadanie 17:
         * Zbadaj (dokumentacja) WBUDOWANY `DiskSpaceHealthIndicator` -
         * jak skonfigurowac PROGI ostrzegawcze?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCompositeHealthCheckWithMultipleIndicators {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj 3 WLASNE `HealthIndicator` naraz - zweryfikuj, ze
         * WSZYSTKIE pojawiaja sie w JEDNEJ odpowiedzi `/actuator/health`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureActuatorEndpointResponseTime {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas odpowiedzi `/actuator/health` z 10 WLASNYMI
         * `HealthIndicator` - czy AGREGACJA wprowadza zauwazalne
         * opoznienie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildActuatorEndpointReferenceTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele NAJWAZNIEJSZYCH
         * endpointow Actuatora - kolumny: sciezka, cel, domyslnie
         * WYSTAWIONY?.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomHealthIndicatorCheckingExternalDependency {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj `HealthIndicator` FAKTYCZNIE sprawdzajacy
         * dostepnosc zewnetrznego zasobu (np. lokalny `HttpServer`,
         * jak w `_06_networking`) - z TIMEOUTEM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementGracefulShutdownWithActuator {
        /*
         * 🧪 Zadanie 22:
         * Skonfiguruj `server.shutdown=graceful` - zweryfikuj, ze
         * aplikacja KONCZY w toku zadania PRZED faktycznym zamknieciem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementActuatorBasedDeploymentReadinessGate {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (opisz) krok CI/CD sprawdzajacy
         * `/actuator/health/readiness` PRZED przekierowaniem ruchu na
         * NOWA wersje aplikacji (blue-green deployment).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomSecurityFilterForActuatorPortSeparation {
        /*
         * 🧪 Zadanie 24:
         * Skonfiguruj Actuator na INNYM porcie niz glowna aplikacja
         * (`management.server.port`) - zweryfikuj IZOLACJE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCustomMetricsExportToExternalSystem {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala (dokumentacja): sprawdz, jak Micrometer
         * EKSPORTUJE metryki do zewnetrznych systemow (Prometheus,
         * Datadog) - zapowiedz `Lesson13`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementAuditEventListenerViaActuator {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson19` - zaimplementuj
         * listener `AuditApplicationEvent` (mechanizm Actuatora) i
         * powiaz z WLASNYM dziennikiem audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementFullObservabilityDashboardSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj (recznie, jako konsolowy "dashboard") podsumowanie
         * WSZYSTKICH wskaznikow zdrowia + WYBRANYCH metryk aplikacji NA
         * ZYWO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareActuatorWithCustomHealthCheckEndpointFromScratch {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj WLASNY, PROSTY endpoint `/health` OD ZERA (bez
         * Actuatora, czysty `@RestController`) - porownaj ILOSC kodu
         * wzgledem gotowego Actuatora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementActuatorEndpointRateLimiting {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_18_rest_api/Lesson16` - zaimplementuj rate limiting
         * SPECYFICZNIE dla endpointow `/actuator/**` (zabezpieczenie
         * przed nadmiarowym odpytywaniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteProductionMonitoringSetupCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny setup monitoringu produkcyjnego - WLASNE
         * health indicatory + wystawione metryki + izolacja portu +
         * zabezpieczenie dostepu - jeden, gotowy do wdrozenia system.
         */
        public static void main(String[] args) { }
    }
}
