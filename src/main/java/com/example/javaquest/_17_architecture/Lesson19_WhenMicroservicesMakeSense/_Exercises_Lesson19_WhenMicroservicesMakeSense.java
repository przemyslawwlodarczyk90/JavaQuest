package com.example.javaquest._17_architecture.Lesson19_WhenMicroservicesMakeSense;

public class _Exercises_Lesson19_WhenMicroservicesMakeSense {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainMicroserviceVsModuleInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy modulem w modularnym monolicie (Lesson17)
         * a mikroserwisem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainConwaysLawWithOwnExample {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: opisz WLASNY (inny niz w teorii) przyklad ilustrujacy
         * Prawo Conwaya - jak struktura zespolu wplynelaby na strukture
         * systemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SimulateInProcessCallFailureMode {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode symulujaca wywolanie modulu W PAMIECI (jak w
         * teorii) - zademonstruj JEDYNY mozliwy tryb awarii (blad logiki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SimulateNetworkCallTimeoutFailureMode {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode symulujaca wywolanie PRZEZ SIEC z DODATKOWYM trybem
         * awarii (timeout) - zademonstruj, ze timeout NIE MOWI, czy operacja
         * sie powiodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ListDistributedSystemCostsFromMemory {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * realne koszty systemu rozproszonego z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyWhenIndependentScalingJustifiesMicroservice {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: opisz scenariusz (min. 3 zdania), w ktorym RoZNICA w
         * charakterystyce obciazenia 2 czesci systemu uzasadnia ich
         * rozdzielenie na osobne serwisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyPrematureDistributionAntiPattern {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: opisz scenariusz (2-osobowy startup rozbijajacy
         * system na 8 mikroserwisow od pierwszego dnia) - w komentarzu
         * wyjasnij, dlaczego to przedwczesna optymalizacja (YAGNI,
         * `_16_clean_code/Lesson13`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareTransactionAvailabilityMonolithVsMicroservices {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania) dostepnosc
         * "prawdziwej" transakcji bazodanowej (Lesson13) w modularnym
         * monolicie vs w architekturze mikroserwisow (osobne bazy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyModularMonolithIsRecommendedStartingPoint {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego modularny
         * monolit (Lesson17) jest zalecanym PUNKTEM STARTU, a mikroserwisy
         * czyms, do czego migrujesz PoZNIEJ, gdy potrzeba sie pojawi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListSignalsThatJustifyMicroservicesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) 4
         * sygnaly z tej lekcji, ktore REALNIE uzasadniaja mikroserwisy.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignRetryStrategyForNetworkTimeoutScenario {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz symulacje z Zadania 4 o PROSTA strategie ponawiania (max 3
         * proby) - zademonstruj sukces po 2. probie i porazke po
         * wyczerpaniu wszystkich prob.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DesignIdempotentOperationSafeForRetries {
        /*
         * 🧪 Zadanie 12:
         * Zaprojektuj operacje "zarezerwuj miejsce" idempotentna wzgledem
         * ponawiania (2. wywolanie z TYM SAMYM identyfikatorem zadania NIE
         * powoduje podwojnej rezerwacji) - zademonstruj podwojne wywolanie z
         * identycznym wynikiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignAdrForStayingWithModularMonolith {
        /*
         * 🧪 Zadanie 13:
         * Napisz PELNY ADR (`Lesson02`) uzasadniajacy DECYZJE o POZOSTANIU
         * przy modularnym monolicie (Lesson17) zamiast migracji na
         * mikroserwisy, mimo sugestii ze strony - z realistycznym
         * kontekstem (maly zespol, brak potrzeby niezaleznego skalowania) i
         * konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignAdrForExtractingOneModuleToMicroservice {
        /*
         * 🧪 Zadanie 14:
         * Napisz PELNY ADR uzasadniajacy WYDZIELENIE 1 KONKRETNEGO modulu
         * (np. przetwarzanie obrazow) do osobnego mikroserwisu - z
         * kontekstem (KONKRETNA, zmierzona roznica w obciazeniu) i
         * konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareDeploymentIndependenceAcrossTeamStructures {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: opisz (min. 4 zdania) 2 scenariusze zespolowe -
         * (a) 1 zespol na caly system, (b) 3 niezalezne zespoly - i
         * zaproponuj, ktora architektura (modularny monolit vs
         * mikroserwisy) lepiej pasuje do KAZDEGO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignSagaLikeCompensationAcrossSimulatedServices {
        /*
         * 🧪 Zadanie 16:
         * Zasymuluj (2 "serwisy" jako osobne klasy) operacje wymagajaca
         * spojnosci miedzy nimi (np. rezerwacja + platnosc) BEZ wspolnej
         * transakcji - zaimplementuj kompensacje (cofniecie rezerwacji), gdy
         * platnosc zawiedzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureLatencyDifferenceConceptuallyBetweenInProcessAndNetwork {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: opisz (z przyblizonymi rzedami wielkosci: np.
         * "wywolanie metody: nanosekundy, wywolanie sieciowe: milisekundy")
         * roznice w typowym opoznieniu miedzy wywolaniem w pamieci a przez
         * siec, i jak to wplywa na projektowanie systemu z WIELOMA
         * kaskadowymi wywolaniami miedzyserwisowymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignCircuitBreakerConceptForUnreliableRemoteService {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj (koncepcyjnie + prosty kod) mechanizm "wylacznika
         * obwodu" (circuit breaker) - po N kolejnych awariach wywolania
         * "zdalnego serwisu", KOLEJNE wywolania sa natychmiast odrzucane
         * (bez czekania na timeout), dopoki serwis "nie wroci do zdrowia".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareTestingComplexityMonolithVsMicroservices {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania) trudnosc
         * napisania testu integracyjnego obejmujacego 3 moduly w modularnym
         * monolicie (1 proces) vs 3 mikroserwisy (3 osobne procesy do
         * uruchomienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCourseArchitectureForMicroservicesReadiness {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: przeanalizuj caly kurs javaQuest jako "system" -
         * gdyby zespol tworzacy ten kurs mial 10 niezaleznych zespolow
         * (po rozdziale), czy mikroserwisy (osobne "aplikacje" per rozdzial)
         * mialyby sens? A gdyby to byl 1 autor (jak w rzeczywistosci)?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullMigrationPlanFromModularMonolithToMicroservices {
        /*
         * 🧪 Zadanie 21:
         * Wez modularny monolit z `_17_architecture/Lesson17` (Zadanie 21,
         * 5 modulow SaaS) - rozpisz KROK PO KROKU (min. 5 krokow) plan
         * migracji 1 WYBRANEGO modulu do osobnego mikroserwisu, z jasnym
         * uzasadnieniem KAZDEGO kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementNetworkAdapterReplacingInProcessCallForExtractedModule {
        /*
         * 🧪 Zadanie 22:
         * Dla modulu wydzielanego w Zadaniu 21, zaimplementuj (symulowany)
         * adapter sieciowy ZASTEPUJACY dotychczasowe bezposrednie wywolanie
         * w pamieci - z obsluga timeoutu i retry (Zadanie 11) - zachowujac
         * TEN SAM interfejs (port) co przedtem (Lesson11-12), zeby reszta
         * systemu nie musiala sie zmieniac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignFullSagaForMultiServiceBusinessTransaction {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj PELNA Sage (min. 3 kroki, KAZDY z WLASNA kompensacja)
         * dla operacji biznesowej rozciagnietej na 3 symulowane serwisy
         * (np. Rezerwacja -> Platnosc -> Powiadomienie) - zademonstruj
         * sukces oraz PELNE wycofanie (wszystkie kompensacje) przy awarii
         * 3. kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildMicroservicesReadinessAssessmentHeuristic {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> assessMicroservicesReadiness(int
         * teamCount, boolean hasDistinctScalingNeeds, boolean
         * hasProvenTeamCoordinationFriction)` zwracajaca REKOMENDACJE
         * (zostac przy monolicie / rozwazyc migracje) na podstawie prostych
         * kryteriow - przetestuj dla min. 3 kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignHybridArchitectureWithMostlyMonolithAndOneExtractedService {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj REALISTYCZNY system - WIEKSZOSC modulow w modularnym
         * monolicie, ale 1 modul (np. generowanie raportow PDF, ciezki
         * obliczeniowo) WYDZIELONY jako osobny serwis - zademonstruj
         * komunikacje monolitu Z tym 1 wydzielonym serwisem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareOperationalOverheadForNServicesConceptually {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: dla systemu z 1 modularnym monolitem vs TYM SAMYM
         * systemem jako 5 mikroserwisow, w komentarzu wypisz (min. 5
         * pozycji) DODATKOWA prace operacyjna wymagana w wersji z 5
         * serwisami (monitoring, logi, wdrozenia, wersjonowanie API miedzy
         * nimi - Lesson08, itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForRejectingMicroservicesDespiteTeamPressure {
        /*
         * 🧪 Zadanie 27:
         * Napisz PELNY ADR dokumentujacy SWIADOMA decyzje ODRZUCENIA
         * propozycji migracji na mikroserwisy (mimo ze "wszyscy inni to
         * robia") - z kontekstem opartym na REALNEJ ocenie z tej lekcji
         * (brak niezaleznego skalowania, 1 maly zespol) i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignDistributedTracingConceptForCrossServiceDebugging {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (koncepcyjnie + prosty kod) mechanizm "correlation
         * ID" - identyfikator PRZEKAZYWANY przez WSZYSTKIE wywolania
         * miedzyserwisowe w 1 przeplywie biznesowym, umozliwiajacy
         * POLACZENIE logow z roznych serwisow w 1 spojna historie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveMicroservicesDecisionChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 7
         * punktow) do podejmowania decyzji "monolit modularny czy
         * mikroserwisy" - laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullDecisionAnalysisForRealisticGrowingStartup {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: opisz (min. 8 zdan, jako "studium
         * przypadku") realistyczny, rozwijajacy sie startup w 3 fazach (5
         * osob/1 modul, 20 osob/kilka zespolow, 100 osob/dziesiatki
         * zespolow) - dla KAZDEJ fazy zaproponuj WLASCIWA architekture
         * (modularny monolit vs mikroserwisy vs hybryda) z pelnym
         * uzasadnieniem opartym na kryteriach z tej lekcji, oraz
         * zaimplementuj PROSTY, symboliczny kod demonstrujacy przejscie
         * miedzy fazami (np. adapter portu podmieniany z in-process na
         * sieciowy, jak w Zadaniu 22).
         */
        public static void main(String[] args) { }
    }
}
