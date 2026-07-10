package com.example.javaquest._17_architecture.Lesson10_DependencyDirection;

public class _Exercises_Lesson10_DependencyDirection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDependencyRuleInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) Dependency Rule i jak sie ma do DIP z
         * `_16_clean_code/Lesson11`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DrawConcentricCirclesForGivenSystem {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: narysuj (jako tekst w komentarzu) 4 kregi
         * koncentryczne dla prostego systemu bibliotecznego - wskaz, co
         * konkretnie trafia do KAZDEGO kregu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DesignInterfaceDefinedInInnerCircle {
        /*
         * 🧪 Zadanie 3:
         * Zaprojektuj interfejs `NotificationSender` (krag wewnetrzny) z
         * metoda `send(String message)` - to fundament pod Zadanie 4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementAdapterInOuterCircle {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `EmailAdapter implements NotificationSender` (krag
         * zewnetrzny) - zademonstruj wywolanie przez przypadek uzycia
         * znajacy TYLKO interfejs.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyDependencyRuleViolationInGivenSnippet {
        /*
         * 🧪 Zadanie 5:
         * Napisz klase domenowa z polem nazwanym/skomentowanym tak, by
         * zdradzalo zaleznosc od szczegolu bazy danych (jak w teorii). W
         * komentarzu wskaz naruszenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FixDependencyRuleViolationFromExercise05 {
        /*
         * 🧪 Zadanie 6:
         * Popraw klase z Zadania 5, zastepujac szczegol techniczny WLASNYM
         * pojeciem domenowym (np. enum) - zweryfikuj identyczne dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyOuterCircleIsMoreVolatile {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * krag zewnetrzny (framework/baza danych) jest BARDZIEJ podatny na
         * zmiane niz krag domeny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareCleanArchitectureVsOnionArchitectureTerminology {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: napisz w komentarzu tabele porownujaca nazwy
         * warstw w Clean Architecture (Martin) i Onion Architecture
         * (Palermo) - potwierdz, ze GLOWNA zasada jest identyczna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DesignUseCaseDependingOnlyOnDomainInterface {
        /*
         * 🧪 Zadanie 9:
         * Zaprojektuj `ShippingUseCase` zalezny WYLACZNIE od interfejsu
         * `ShippingCostCalculator` (bez zadnej wiedzy o konkretnej
         * implementacji) - zademonstruj wywolanie z 1 implementacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListDependencyRuleSignalsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * sygnaly naruszenia Dependency Rule.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignFullThreeCircleSystemForInventoryManagement {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj system zarzadzania magazynem z 3 kregami: Domena
         * (`StockItem` z regula "nie mozna zejsc ponizej zera"), Przypadek
         * uzycia (`ReserveStockUseCase`), Adapter (implementacja
         * `StockRepository`). Zademonstruj pelny przeplyw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SwapOuterAdapterWithoutTouchingInnerCircles {
        /*
         * 🧪 Zadanie 12:
         * Dla systemu z Zadania 11, dodaj DRUGA implementacje adaptera
         * (np. inna "technologia" magazynowa) - zademonstruj, ze Domena i
         * Przypadek uzycia NIE WYMAGAJA zadnej zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentifyViolationWhereDomainImportsFrameworkAnnotation {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase domenowa z komentarzem symulujacym adnotacje typu
         * "@Entity"/"@Column" BEZPOSREDNIO na polach biznesowych (bez
         * prawdziwej zaleznosci Hibernate, tylko jako symulacja przez
         * komentarz) - w komentarzu wyjasnij, dlaczego to narusza
         * Dependency Rule.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignSeparationBetweenDomainEntityAndPersistenceModel {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj CZYSTA encje domenowa `Product` (bez ZADNYCH adnotacji/
         * zaleznosci) ORAZ osobny "model trwalosci" `ProductRecord`
         * (symulujacy encje JPA) - napisz mapper miedzy nimi (por. Lesson07).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignAdrJustifyingDependencyRuleAdoption {
        /*
         * 🧪 Zadanie 15:
         * Napisz PELNY ADR (`Lesson02`) uzasadniajacy przyjecie Dependency
         * Rule dla nowego, duzego projektu - z realistycznym kontekstem
         * (np. "spodziewamy sie zmiany dostawcy platnosci w ciagu roku") i
         * konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorViolatingSystemToRespectDependencyRule {
        /*
         * 🧪 Zadanie 16:
         * Napisz system, gdzie klasa "przypadku uzycia" BEZPOSREDNIO tworzy
         * `new` konkretna implementacje (np. `new
         * PostgresPaymentAdapter()`) zamiast zalezec od interfejsu.
         * Zrefaktoryzuj zgodnie z Dependency Rule (constructor injection,
         * `_16_clean_code/Lesson11`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignMultipleAdaptersImplementingSameInnerInterface {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj interfejs `AuditLogger` (krag wewnetrzny) z 3 ROZNYMI
         * implementacjami w kregu zewnetrznym (Console, InMemory, "Legacy
         * File") - zademonstruj podmiane wszystkich trzech bez zmiany
         * przypadku uzycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareTestabilityWithAndWithoutDependencyRule {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj TA SAMA funkcjonalnosc (np. wysylka powiadomien) w 2
         * wersjach: (a) zgodna z Dependency Rule (interfejs + adapter), (b)
         * z bezposrednia zaleznoscia od konkretnej implementacji. W
         * komentarzu porownaj latwosc testowania obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignDomainEventFreeOfInfrastructureDetails {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj `record OrderPlaced(String orderId, double amount)`
         * (zdarzenie domenowe, krag wewnetrzny) - upewnij sie, ze NIE
         * zawiera zadnych typow z "infrastruktury" (np. formatow JSON,
         * typow specyficznych dla kolejki wiadomosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCodeFromHibernateChapterForDependencyRuleCompliance {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) 1 encje z `_12_hibernate` - czy jej
         * logika biznesowa (jesli istnieje) jest "zanieczyszczona"
         * adnotacjami/zaleznosciami Hibernate, czy da sie ja oddzielic?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullFourCircleArchitectureForRealisticBookingSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny system rezerwacji (podobny do wczesniejszych
         * lekcji) z WSZYSTKIMI 4 kregami jawnie nazwanymi (Domena, Przypadki
         * uzycia, Adaptery, Frameworki/Narzedzia) - min. 2 klasy na krag.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateFrameworkMigrationRespectingDependencyRule {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21, zasymuluj "migracje frameworka" (np.
         * zmiana implementacji adaptera bazy danych na CALKOWICIE inna) -
         * zademonstruj, ze kregi Domena i Przypadki uzycia NIE WYMAGAJA
         * ZADNEJ zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignCleanDomainModelIndependentOfAnyPersistenceTechnology {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj bogaty model domenowy (`_17_architecture/Lesson05`) z
         * min. 3 metodami biznesowymi, kompletnie NIEZALEZNY od JAKIEJKOLWIEK
         * technologii przechowywania - napisz osobno "adapter trwalosci"
         * konwertujacy go do/z prostego "rekordu" symulujacego wiersz bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildDependencyDirectionValidatorHeuristic {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> detectDependencyRuleViolations(
         * List<String> domainClassImports)` sprawdzajaca (na liscie
         * symulowanych nazw importow), czy KTORYKOLWIEK z nich zawiera
         * "typowo zewnetrzne" slowa kluczowe (np. "jdbc", "servlet",
         * "hibernate") - przetestuj dla listy czystej i naruszajacej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignLayeredSystemWhereEachCircleHasOwnTestSuite {
        /*
         * 🧪 Zadanie 25:
         * Dla systemu z Zadania 21, zaprojektuj "zestawy testowe" (metody
         * wywolujace logike) dla KAZDEGO z 4 kregow OSOBNO - zademonstruj,
         * ze test Domeny dziala BEZ potrzeby angazowania adapterow/frameworkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RefactorRealisticViolatingSystemAcrossAllCircles {
        /*
         * 🧪 Zadanie 26:
         * Napisz REALISTYCZNY system NARUSZAJACY Dependency Rule na
         * WSZYSTKICH poziomach (domena zna szczegoly bazy, przypadek uzycia
         * tworzy adaptery przez `new`) - w PELNI zrefaktoryzuj do zgodnosci
         * z regula, krok po kroku (`_16_clean_code/Lesson15`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForResolvingDependencyRuleViolationFoundInAudit {
        /*
         * 🧪 Zadanie 27:
         * Napisz ADR dokumentujacy DECYZJE naprawy istniejacego naruszenia
         * Dependency Rule wykrytego podczas audytu - z kontekstem (jak
         * wykryto problem), decyzja (plan naprawy) i konsekwencjami (koszt
         * migracji vs korzysc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareDependencyRuleWithHexagonalArchitecturePreview {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz w komentarzu (min. 3 zdania) hipoteze, jak
         * "porty" i "adaptery" (nazwy, ktore poznasz w Lesson11-12) mogłyby
         * odpowiadac interfejsom i implementacjom z tej lekcji - sprawdzisz
         * to w kolejnej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveDependencyRuleAuditChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do audytu zgodnosci realnego systemu z Dependency Rule -
         * laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullDependencyRuleCompliantSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny
         * system (np. system wypozyczalni rowerow) w PELNI zgodny z
         * Dependency Rule - 4 wyrazne kregi, WSZYSTKIE interfejsy
         * zdefiniowane wewnatrz i implementowane na zewnatrz, ZERO importow
         * "do gory" (z domeny w strone frameworkow). Zademonstruj pelny
         * przeplyw ORAZ podmiane 1 adaptera bez zadnej zmiany w
         * pozostalych kregach.
         */
        public static void main(String[] args) { }
    }
}
