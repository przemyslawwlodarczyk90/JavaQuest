package com.example.javaquest._17_architecture.Lesson03_LayeredArchitecture;

public class _Exercises_Lesson03_LayeredArchitecture {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainLayersVsTiersInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy "warstwami" (layers, podzial logiczny) a
         * "tiers" (podzial fizyczny/wdrozeniowy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DesignThreeLayerCliApplication {
        /*
         * 🧪 Zadanie 2:
         * Zaprojektuj prosta aplikacje CLI (np. kalkulator podatku) z 3
         * warstwami: "Prezentacja" (metoda symulujaca input z linii komend),
         * "Biznes" (obliczenia), "Dane" (interfejs + 1 implementacja
         * in-memory). Zademonstruj przeplyw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyWhichLayerViolatesStrictLayering {
        /*
         * 🧪 Zadanie 3:
         * Dany jest opis: metoda w warstwie prezentacji woluje BEZPOSREDNIO
         * metode warstwy danych, pomijajac warstwe biznesowa. Napisz taki
         * kod (3 klasy) i w komentarzu wskaz, KTORA zasada scislego
         * warstwowania zostala naruszona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixStrictLayeringViolationFromExercise03 {
        /*
         * 🧪 Zadanie 4:
         * Popraw kod z Zadania 3 tak, by prezentacja wolala WYLACZNIE
         * warstwe biznesowa, a ta deleguje do warstwy danych. Zweryfikuj
         * identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DesignRepositoryInterfaceWithTwoImplementations {
        /*
         * 🧪 Zadanie 5:
         * Zaprojektuj interfejs `NotesRepository` (save/findAll) z 2
         * implementacjami (InMemory i "LegacyFile", symulowana) - napisz
         * warstwe biznesowa przyjmujaca dowolna implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SwapDataLayerImplementationWithoutChangingBusinessLayer {
        /*
         * 🧪 Zadanie 6:
         * Uzywajac rozwiazania z Zadania 5, wywolaj warstwe biznesowa NAJPIERW
         * z InMemory, POTEM z "LegacyFile" implementacja - zweryfikuj, ze KOD
         * warstwy biznesowej sie NIE zmienil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyBusinessLogicLeakingIntoPresentation {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode "prezentacji" SAMA liczaca regule biznesowa (np.
         * rabat) zamiast delegowac do warstwy biznesowej. W komentarzu wskaz
         * ten anty-wzorzec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FixBusinessLogicLeakFromExercise07 {
        /*
         * 🧪 Zadanie 8:
         * Popraw kod z Zadania 7, przenoszac logike rabatu do WLASCIWEJ
         * warstwy biznesowej - prezentacja ma teraz TYLKO wolac te warstwe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyDataLayerTypeLeakingUpward {
        /*
         * 🧪 Zadanie 9:
         * Napisz warstwe biznesowa zwracajaca typ specyficzny dla "bazy
         * danych" (np. wlasna klase `RawDbRow` symulujaca `ResultSet`) zamiast
         * wlasnego DTO/rekordu. W komentarzu wyjasnij, dlaczego to zmusza
         * prezentacje do znajomosci szczegolow technicznych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListStrictLayeringRulesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) glowna
         * zasade scislego warstwowania i min. 2 przyklady jej naruszenia z
         * tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignFourLayerBatchProcessingApplication {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj aplikacje WSADOWA (batch) przetwarzajaca liste
         * zamowien - z warstwami: "Wejscie" (parsowanie danych wejsciowych),
         * "Biznes" (walidacja + obliczenia), "Wyjscie" (formatowanie
         * raportu). Zademonstruj pelny przeplyw dla min. 3 zamowien.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareStrictVsRelaxedLayeringForReadOnlyOperation {
        /*
         * 🧪 Zadanie 12:
         * Zaprojektuj 2 wersje odczytu "slownika kategorii" (bez logiki
         * biznesowej): (a) scisle - przez warstwe biznesowa (nawet jesli to
         * "pass-through"), (b) luzno - prezentacja czyta bezposrednio z
         * warstwy danych. W komentarzu oceń kompromisy obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignLayeredSystemForInventoryManagement {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj system zarzadzania magazynem z 3 warstwami - warstwa
         * biznesowa musi obslugiwac regule "nie mozna sprzedac wiecej niz jest
         * na stanie". Zademonstruj przypadek udany i nieudany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RefactorTwoLayerSystemIntoThreeProperLayers {
        /*
         * 🧪 Zadanie 14:
         * Napisz system z TYLKO 2 "warstwami" (prezentacja bezposrednio
         * mieszajaca logike biznesowa z dostepem do danych) - zrefaktoryzuj na
         * WLASCIWE 3 warstwy, wydzielajac osobno logike biznesowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DemonstratePassThroughLayerCost {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj warstwe biznesowa, ktora dla 1 KONKRETNEJ metody TYLKO
         * przekazuje wywolanie do warstwy danych (pass-through, bez zadnej
         * wlasnej logiki) - w komentarzu oceń, czy ten "koszt" scislego
         * warstwowania jest tu uzasadniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignLayeredSystemWithSwappableNotificationChannel {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj system powiadomien z warstwa biznesowa NIE znajaca
         * konkretnego kanalu wysylki (interfejs `NotificationChannel`) - podmien
         * implementacje (np. Email na SMS) BEZ zmiany warstwy biznesowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_AuditRealCodeFromDaoChapterForLayeringViolations {
        /*
         * 🧪 Zadanie 17:
         * Przeanalizuj (w komentarzu) 1 klase z `_10_dao` inna niz
         * Lesson02_LayeredArchitecture - oceń, czy respektuje scisle
         * warstwowanie (czy Service zna SQL, czy DAO podejmuje decyzje
         * biznesowe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignLayeredArchitectureDocumentedWithAdr {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj system z 3 warstwami DLA dowolnej funkcjonalnosci - napisz
         * do niej TOWARZYSZACY ADR (`_17_architecture/Lesson02`) uzasadniajacy
         * wybor scislego (a nie luznego) warstwowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureImpactOfChangingDataLayerAcrossTwoDesigns {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj TA SAMA funkcjonalnosc (np. rejestr zdarzen) w 2
         * wersjach: (a) scisle warstwowanie, (b) prezentacja bezposrednio
         * uzywajaca warstwy danych. Zmien implementacje warstwy danych w OBU
         * wersjach - policz, ile linii/klas trzeba zmienic w kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignLayeredSystemForDesktopGuiSimulation {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj system "symulujacy" aplikacje desktopowa (np. edytor
         * notatek) z 3 warstwami - w komentarzu potwierdz, ze wzorzec dziala
         * IDENTYCZNIE jak dla aplikacji webowej z `_10_dao/Lesson02`.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignRealisticFourLayerHotelBookingSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj system rezerwacji hotelu z 4 warstwami: Prezentacja,
         * Biznes (dostepnosc pokoi, ceny), Dane (interfejs repozytorium),
         * oraz DODATKOWA warstwa "Integracje" (symulowany zewnetrzny system
         * platnosci) - Biznes zna TYLKO interfejsy obu niższych warstw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SwapBothDataAndIntegrationLayersIndependently {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21, podmien NIEZALEZNIE: (a) implementacje
         * warstwy Danych, (b) implementacje warstwy Integracji - zademonstruj,
         * ze KAZDA podmiana jest niezalezna od drugiej i nie rusza warstwy
         * Biznes.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignHybridStrictAndRelaxedLayeringWithJustification {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj system, gdzie WIEKSZOSC operacji (zapis) uzywa scislego
         * warstwowania, ale 1 KONKRETNA operacja odczytu (np. "pobierz liste
         * krajow") swiadomie uzywa luznego warstwowania - napisz komentarz
         * uzasadniajacy TEN WYJATEK (jak w ADR, Lesson02).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildLayerViolationDetectorAsSimpleHeuristic {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> detectLayeringSmells(boolean
         * presentationCallsDataDirectly, boolean businessReturnsDbSpecificType)`
         * zwracajaca liste wykrytych naruszen na podstawie prostych flag -
         * przetestuj dla min. 3 kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorRealisticGodLayerIntoProperThreeLayers {
        /*
         * 🧪 Zadanie 25:
         * Napisz 1 "god" klase laczaca NARAZ prezentacje+logike+dane dla
         * systemu zarzadzania zadaniami (todo list) - w PELNI zrefaktoryzuj na
         * 3 wlasciwe warstwy, zachowujac identyczne zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignTestStrategyExercisingOnlyBusinessLayer {
        /*
         * 🧪 Zadanie 26:
         * Dla systemu z Zadania 25 (po refaktoryzacji), napisz "zestaw
         * testowy" wywolujacy WYLACZNIE warstwe biznesowa (z prosta atrapa
         * warstwy danych) - zademonstruj, ze logika biznesowa jest w PELNI
         * testowalna bez prezentacji/prawdziwych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareLayeredArchitectureAcrossThreeApplicationTypes {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj TEN SAM wzorzec 3 warstw dla 3 ROZNYCH typow aplikacji
         * (web-podobna, CLI-podobna, batch-podobna) dla tej samej logiki
         * biznesowej (np. walidacja zamowienia) - w komentarzu potwierdz, ze
         * warstwa BIZNESOWA jest identyczna we wszystkich trzech, zmienia sie
         * TYLKO warstwa prezentacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAdrJustifyingChoiceBetweenStrictAndRelaxedLayering {
        /*
         * 🧪 Zadanie 28:
         * Napisz PELNY ADR (`Lesson02`) dla decyzji "scisle czy luzne
         * warstwowanie w nowym module raportowania" - z realistycznym
         * kontekstem (np. potrzeba szybkich odczytow) i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveLayeredArchitectureAuditChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do audytu architektury warstwowej realnego systemu -
         * laczac WSZYSTKIE zasady z tej lekcji (layers vs tiers, scisle vs
         * luzne, przeciekanie w obie strony, izolacja zmiany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneRealisticThreeLayerApplicationWithSwapDemo {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny,
         * realistyczny system (np. system wypozyczania samochodow) z 3
         * WLASCIWIE oddzielonymi warstwami, ZERO przeciekania w zadna strone,
         * i zademonstruj (jak w Zadaniu 22) niezalezna podmiane warstwy
         * danych NA implementacje 2 roznych "technologii" (in-memory i
         * symulowana zewnetrzna), bez zadnej zmiany w warstwie biznesowej.
         */
        public static void main(String[] args) { }
    }
}
