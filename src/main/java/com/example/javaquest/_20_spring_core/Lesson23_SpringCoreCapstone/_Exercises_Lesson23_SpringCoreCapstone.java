package com.example.javaquest._20_spring_core.Lesson23_SpringCoreCapstone;

public class _Exercises_Lesson23_SpringCoreCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllMechanismsUsedInCapstone {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz WSZYSTKIE mechanizmy Springa uzyte w
         * kapsztonie (min. 8) - dla kazdego podaj, w KTOREJ lekcji byl
         * wprowadzony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddThirdRepositoryImplementationForTestProfile {
        /*
         * 🧪 Zadanie 2:
         * Dodaj 3. implementacje `OrderRepository` dla profilu "test"
         * (np. rzucajaca wyjatek przy kazdym `save()`, symulujaca awarie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ChangeDiscountThresholdViaProperty {
        /*
         * 🧪 Zadanie 3:
         * Zmien prog rabatu przez REALNA wlasciwosc (`@PropertySource`,
         * powiaz z `Lesson16`) zamiast wartosci domyslnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SwitchQualifierToEmailNotification {
        /*
         * 🧪 Zadanie 4:
         * Zmien `@Qualifier` w `OrderProcessingService` na
         * "emailNotificationSender" - zweryfikuj zmiane zachowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddSecondEventListenerForAuditTrail {
        /*
         * 🧪 Zadanie 5:
         * Dodaj DRUGI listener `OrderProcessedEvent` - prosty "dziennik
         * audytu" zbierajacy WSZYSTKIE przetworzone zamowienia do listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VerifyPostConstructRunsBeforeFirstOrder {
        /*
         * 🧪 Zadanie 6:
         * Zweryfikuj (przez logi), ze `@PostConstruct` repozytorium
         * wykonuje sie PRZED PIERWSZYM wywolaniem `processOrder()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyPreDestroyRunsOnContextClose {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj (przez logi), ze `@PreDestroy` repozytorium
         * wykonuje sie DOKLADNIE PRZY `context.close()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddThirdScenarioForNoActiveProfile {
        /*
         * 🧪 Zadanie 8:
         * Dodaj 3. scenariusz BEZ aktywnego profilu - zaobserwuj i
         * wyjasnij wynikajacy blad (brak `@Profile("default")` beana).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyThisCapstoneAvoidsSpringBoot {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego ten kapszton CELOWO NIE uzywa
         * Spring Boota - co konkretnie musiales zrobic RECZNIE, co Boot
         * zrobilby automatycznie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TraceFullRequestFlowThroughAllLayers {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: narysuj (tekst/ASCII) PELNY przeplyw
         * `processOrder()` - AOP -> serwis -> repozytorium -> zdarzenie ->
         * listener - z zaznaczeniem, GDZIE dziala KTORY mechanizm.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_AddValidationLayerBeforeProcessing {
        /*
         * 🧪 Zadanie 11:
         * Dodaj warstwe walidacji (powiaz z
         * `_19_security_basics/Lesson17`) - odrzuc zamowienie z
         * ujemna/zerowa kwota PRZED zapisem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddSecondAspectForPerformanceMonitoring {
        /*
         * 🧪 Zadanie 12:
         * Dodaj DRUGI aspekt (`@Order` wzgledem `AuditAspect`) mierzacy
         * czas wykonania `processOrder()` - zweryfikuj kolejnosc "owijania".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RefactorToExposeQueryMethod {
        /*
         * 🧪 Zadanie 13:
         * Dodaj do `OrderRepository` metode `findAll()` - zwroc liste
         * WSZYSTKICH zapisanych zamowien, zademonstruj jej uzycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementGracefulErrorHandlingForRepositoryFailure {
        /*
         * 🧪 Zadanie 14:
         * Zasymuluj awarie repozytorium (rzucany wyjatek w `save()`) -
         * zaimplementuj OBSLUGE bledu w serwisie (np. fallback/retry) BEZ
         * przerywania calego programu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddSecondServiceDependingOnFirst {
        /*
         * 🧪 Zadanie 15:
         * Dodaj `OrderReportingService` ZALEZNY od `OrderProcessingService`
         * (kolejna warstwa) - zweryfikuj poprawne polaczenie CALEGO grafu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureContextStartupTimeForBothProfiles {
        /*
         * 🧪 Zadanie 16:
         * Zmierz czas startu kontekstu DLA OBU profili (dev/prod) -
         * porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementBeanPostProcessorLoggingCapstoneBeans {
        /*
         * 🧪 Zadanie 17:
         * Dodaj `BeanPostProcessor` (Lesson19) logujacy tworzenie KAZDEGO
         * beana kapsztonu - zweryfikuj PELNA liste utworzonych beanow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementSpelBasedDiscountRule {
        /*
         * 🧪 Zadanie 18:
         * Zastap prosta regule rabatu (if/else) wyrazeniem SpEL (Lesson17)
         * wczytanym z konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AddPrototypeScopedOrderContext {
        /*
         * 🧪 Zadanie 19:
         * Dodaj bean `@Scope("prototype")` reprezentujacy "kontekst
         * przetwarzania pojedynczego zamowienia" (powiaz z `Lesson14`) -
         * zweryfikuj SWIEZA instancje przy KAZDYM `processOrder()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildFullSystemHealthReport {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj raport "stanu systemu" wypisywany na koniec KAZDEGO
         * scenariusza - liczba przetworzonych zamowien, aktywny profil,
         * liczba zdarzen wyemitowanych.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorCapstoneToUseCustomScope {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY zakres (Lesson14, Zadanie 14/21) i
         * zastosuj go do JEDNEGO z beanow kapsztonu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCircuitBreakerAspectForRepository {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj aspekt "circuit breaker" (po N kolejnych awariach
         * repozytorium, kolejne wywolania sa NATYCHMIAST odrzucane BEZ
         * probowania) - powiaz z `_18_rest_api/Lesson16`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementFullEventSourcingForOrderHistory {
        /*
         * 🧪 Zadanie 23:
         * Rozbuduj system o pelny "event sourcing" - stan zamowienia
         * ODTWARZANY WYLACZNIE z historii zdarzen (Lesson20, Zadanie 23),
         * ZAMIAST bezposredniego zapisu w repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementMultiTenantProfileSelection {
        /*
         * 🧪 Zadanie 24:
         * Rozbuduj system o "wielodzierzawczosc" (multi-tenancy) - RÓZNA
         * implementacja repozytorium na podstawie ID klienta (nie tylko
         * profilu srodowiskowego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildFullIntegrationTestWithoutSpringBootTest {
        /*
         * 🧪 Zadanie 25:
         * Napisz "test integracyjny" (zwykla metoda `main`, BEZ
         * frameworka testowego) uruchamiajacy PELNY kontekst i
         * WERYFIKUJACY co najmniej 5 asercji na WYNIKACH `processOrder()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareCapstoneArchitectureWithHexagonal {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: przeanalizuj kapszton pod katem architektury
         * heksagonalnej (`_17_architecture/Lesson11-12`) - ktore
         * interfejsy to "porty", ktore implementacje to "adaptery"?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementGracefulShutdownOrderingAcrossBeans {
        /*
         * 🧪 Zadanie 27:
         * Zweryfikuj i UDOKUMENTUJ (przez logi) DOKLADNA kolejnosc
         * niszczenia WSZYSTKICH beanow kapsztonu przy `context.close()` -
         * czy jest zgodna z oczekiwaniami (Lesson18, Zadanie 27)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MigrateCapstoneToXmlConfigForComparison {
        /*
         * 🧪 Zadanie 28:
         * Przepisz CZESC konfiguracji kapsztonu (Lesson05) na XML - policz,
         * ILE WIECEJ kodu to wymaga wzgledem Java Config.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildPerformanceBenchmarkForFullScenario {
        /*
         * 🧪 Zadanie 29:
         * Zmierz PELNY czas przetworzenia 10 000 zamowien (obejmujacy
         * AOP + zdarzenia + repozytorium) - zidentyfikuj (przez proste
         * pomiary per-etap) NAJWOLNIEJSZY fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildYourOwnCapstoneVariantFromScratch {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj i zaimplementuj WLASNY kapszton (INNA dziedzina
         * biznesowa, np. biblioteka/hotel/kurier) LACZACY WSZYSTKIE
         * mechanizmy z tego rozdzialu, tak jak zrobiono to dla zamowien -
         * z co najmniej 2 scenariuszami (rozne profile) w `main()`.
         */
        public static void main(String[] args) { }
    }
}
