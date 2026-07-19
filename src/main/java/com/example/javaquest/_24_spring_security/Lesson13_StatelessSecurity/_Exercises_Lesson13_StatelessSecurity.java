package com.example.javaquest._24_spring_security.Lesson13_StatelessSecurity;

public class _Exercises_Lesson13_StatelessSecurity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSessionCreationPolicyOptions {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz WSZYSTKIE 4 wartosci
         * `SessionCreationPolicy` (ALWAYS/IF_REQUIRED/NEVER/STATELESS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainDifferenceNeverVsStateless {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij subtelna ROZNICE miedzy `NEVER` A
         * `STATELESS` (NEVER NIE TWORZY, ale UZYJE ISTNIEJACEJ; STATELESS
         * NIGDY NIE UZYJE ZADNEJ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestSecondRequestWithSameTokenStateless {
        /*
         * 🧪 Zadanie 3:
         * Wyslij DRUGIE zadanie Z TYM SAMYM tokenem - zweryfikuj, ze
         * DALEJ dziala (BEZ sesji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareCookieHeadersSideBySide {
        /*
         * 🧪 Zadanie 4:
         * Wypisz WSZYSTKIE naglowki odpowiedzi Z OBU demo OBOK siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyIfRequiredIsDefaultNotStateless {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, DLACZEGO Spring Security wybral
         * `IF_REQUIRED` jako DOMYSLNY (NIE `STATELESS`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestAlwaysPolicyBehavior {
        /*
         * 🧪 Zadanie 6:
         * Ustaw `SessionCreationPolicy.ALWAYS` I zweryfikuj sesje
         * TWORZONA NAWET PRZED uwierzytelnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareWithLesson04SessionBehavior {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: powiaz z Lesson04 - CZY tamten formLogin
         * TWORZYL sesje (sprawdz WSTECZ ODPOWIEDZ Z Lesson04).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestStatelessWithFormLoginCombination {
        /*
         * 🧪 Zadanie 8:
         * SPROBUJ POLACZYC `STATELESS` Z `.formLogin(...)` - zaobserwuj
         * KONFLIKT (formLogin POTRZEBUJE sesji DO przechowania
         * kontekstu bledu/przekierowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MeasureMemoryUsageDifference {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: oszacuj ROZNICE W zuzyciu pamieci serwera
         * PRZY 10000 aktywnych uzytkownikach: sesje vs STATELESS+JWT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListProsAndConsOfStatelessArchitecture {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz PO 3 zalety I wady architektury
         * bezstanowej.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementLoadBalancingSimulation {
        /*
         * 🧪 Zadanie 11:
         * Zasymuluj 2 "instancje" serwera (2 osobne konteksty) -
         * zweryfikuj, ze TEN SAM token JWT DZIALA NA OBU (bez
         * dzielonego magazynu sesji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareStickySessionsWithStatelessApproach {
        /*
         * 🧪 Zadanie 12:
         * Bez terminala: powiaz z `_17_architecture/Lesson19` -
         * porownaj "sticky sessions" (load balancer PAMIETA serwer) Z
         * podejsciem STATELESS (DOWOLNY serwer moze obsluzyc zadanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDistributedSessionStoreAlternative {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: opisz ALTERNATYWE DLA STATELESS - dzielony
         * magazyn sesji (Redis) - kompromisy WZGLEDEM JWT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestConcurrentRequestsWithStatelessApi {
        /*
         * 🧪 Zadanie 14:
         * Wyslij 20 ROWNOLEGLYCH zadan Z ROZNYMI tokenami - zweryfikuj
         * BRAK jakiejkolwiek "przeciekajacej" tozsamosci miedzy nimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureStatelessVsStatefulLatency {
        /*
         * 🧪 Zadanie 15:
         * Porownaj latencje 100 zadan STATELESS (weryfikacja JWT) Z
         * 100 zadan Z sesja (odczyt Z magazynu sesji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementStatelessCsrfConsiderations {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: powiaz z Lesson14 - wyjasnij, DLACZEGO CSRF
         * jest MNIEJ istotny PRZY podejsciu STATELESS+JWT (BRAK
         * ciasteczka sesji DO wykorzystania PRZEZ atak CSRF).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestSecurityContextNotPersistedBetweenRequests {
        /*
         * 🧪 Zadanie 17:
         * Zweryfikuj (PRZEZ debugger/logi), ze
         * `SecurityContextHolder.getContext()` jest PUSTY NA POCZATKU
         * KAZDEGO nowego zadania W trybie STATELESS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignHybridApproachForWebAndApi {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj aplikacje Z 2 `SecurityFilterChain` - STATELESS
         * DLA `/api/**`, stanowa (sesje) DLA reszty (panel admina Z
         * formLogin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainImpactOnHorizontalAutoscaling {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij, DLACZEGO STATELESS UPRASZCZA
         * auto-skalowanie (Kubernetes HPA) W POROWNANIU Z sesjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareTokenSizeWithSessionIdSize {
        /*
         * 🧪 Zadanie 20:
         * Porownaj ROZMIAR (bajty) tokenu JWT Z rozmiarem ID sesji
         * (`JSESSIONID`) - wplyw NA rozmiar KAZDEGO zadania HTTP.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementStatelessRateLimitingPerToken {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_18_rest_api/Lesson16` - zaimplementuj rate
         * limiting NA PODSTAWIE `subject` tokenu (BEZ sesji DO
         * przechowania licznika - uzyj zewnetrznego magazynu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignMultiRegionStatelessDeployment {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: zaprojektuj WDROZENIE WIELOREGIONOWE (USA+EU)
         * korzystajace Z podejscia STATELESS (bez replikacji sesji
         * miedzy regionami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BenchmarkStatelessThroughputUnderLoad {
        /*
         * 🧪 Zadanie 23:
         * Zmierz maksymalny throughput (zadania/sekunde) STATELESS API
         * PRZY rosnacej liczbie ROWNOLEGLYCH klientow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementGracefulDegradationWhenKeyRotates {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj, CO SIE DZIEJE Z AKTYWNYMI tokenami PRZY rotacji
         * klucza podpisujacego W architekturze STATELESS
         * (WIELOSERWEROWEJ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareStatelessWithEventSourcingPatterns {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: powiaz z `_17_architecture/Lesson18` -
         * porownaj bezstanowosc API Z wzorcem event sourcing (stan
         * ODTWARZANY, NIE przechowywany bezposrednio).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementStatelessAuditTrailWithoutSessionContext {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson19` - zaprojektuj audyt
         * DZIALAN uzytkownika BEZ polegania NA kontekscie sesji
         * (wszystko Z tokenu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignZeroDowntimeMigrationFromSessionsToJwt {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: zaprojektuj MIGRACJE (BEZ przestoju) aplikacji
         * Z sesji NA JWT - JAK obsluzyc UZYTKOWNIKOW ZALOGOWANYCH W
         * TRAKCIE migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestStatelessResilienceToServerRestart {
        /*
         * 🧪 Zadanie 28:
         * Zamknij I ponownie uruchom kontekst (symulacja restartu
         * serwera) - zweryfikuj, ze token WYDANY PRZED restartem WCIAZ
         * DZIALA (ZAKLADAJAC TEN SAM klucz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareCostOfStatelessVsStatefulInfrastructure {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: oszacuj ROZNICE kosztow infrastruktury (BRAK
         * potrzeby Redis/dzielonego magazynu) STATELESS vs sesyjnej
         * architektury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullStatelessMicroserviceDemoWithTwoInstances {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj DEMO Z 2 NIEZALEZNYMI "instancjami" serwisu (osobne
         * konteksty, TEN SAM klucz JWT) I zweryfikuj, ze token Z
         * INSTANCJI A DZIALA NA INSTANCJI B - PRAWDZIWA bezstanowosc.
         */
        public static void main(String[] args) { }
    }
}
