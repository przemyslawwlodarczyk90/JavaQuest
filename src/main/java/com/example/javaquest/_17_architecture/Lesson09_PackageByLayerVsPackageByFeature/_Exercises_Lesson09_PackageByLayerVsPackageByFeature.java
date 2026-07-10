package com.example.javaquest._17_architecture.Lesson09_PackageByLayerVsPackageByFeature;

public class _Exercises_Lesson09_PackageByLayerVsPackageByFeature {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPackageByLayerVsFeatureInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy package-by-layer a package-by-feature.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DrawPackageByLayerTreeForGivenFeatures {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: narysuj (jako tekst w komentarzu, jak drzewo
         * katalogow w teorii) strukture package-by-layer dla systemu z 2
         * funkcjami: "Rezerwacje" i "Platnosci".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DrawPackageByFeatureTreeForSameFeatures {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: narysuj (jako tekst w komentarzu) strukture
         * package-by-feature dla TYCH SAMYCH 2 funkcji z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SimulateLayerGroupedClassesForThreeFeatures {
        /*
         * 🧪 Zadanie 4:
         * Zaprojektuj (jako zagniezdzone rekordy/klasy w tym pliku,
         * symulujac pakiety) grupe "wszystkie serwisy" laczaca serwisy 3
         * niepowiazanych funkcji (np. Rezerwacje, Platnosci, Powiadomienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SimulateFeatureGroupedClassesForOneFeature {
        /*
         * 🧪 Zadanie 5:
         * Zaprojektuj grupe "Rezerwacje" laczaca Controller+Service+
         * Repository DLA TEJ JEDNEJ funkcji - porownaj w komentarzu spojnosc
         * z Zadaniem 4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainPackagePrivateEnforcementBenefit {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * `package-private` (brak `public`) w package-by-feature MOZE
         * technicznie wymusic, ze inne funkcje nie omina warstwy Service.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyWhichStructureMakesFindingAllControllersEasier {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: dla zadania "znajdz WSZYSTKIE kontrolery aplikacji w
         * 5 sekund" - oceń, ktora struktura (package-by-layer czy
         * package-by-feature) czyni to latwiejszym, i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentifyWhichStructureMakesUnderstandingOneFeatureEasier {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: dla zadania "zrozum WSZYSTKO o funkcji Platnosci w 5
         * minut" - oceń, ktora struktura czyni to latwiejszym, i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DesignHybridStructureForTwoFeaturesWithInternalLayers {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: narysuj (jako tekst) strukture hybrydowa (feature na
         * gorze, warstwy wewnatrz) dla 2 funkcji z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListTradeoffsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 2
         * zalety i 2 wady KAZDEGO z 2 podejsc z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorPackageByLayerSimulationToPackageByFeature {
        /*
         * 🧪 Zadanie 11:
         * Zasymuluj (zagniezdzonymi klasami) system 3 funkcji w
         * package-by-layer (osobne grupy controller/service/repository
         * laczace WSZYSTKIE funkcje) - zrefaktoryzuj na package-by-feature (3
         * grupy, kazda z 1 funkcja w calosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureNumberOfFilesToOpenForOneFeatureInEachStructure {
        /*
         * 🧪 Zadanie 12:
         * Bez terminala: dla systemu z 4 funkcjami x 3 warstwy (12 klas), w
         * komentarzu policz, ile ROZNYCH "folderow" trzeba otworzyc, zeby
         * zobaczyc WSZYSTKO o 1 funkcji w KAZDYM z 2 podejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignFeatureAlignedWithBoundedContextFromLesson06 {
        /*
         * 🧪 Zadanie 13:
         * Korzystajac z bounded context zaprojektowanych w
         * `_17_architecture/Lesson06`, zaproponuj (jako drzewo tekstowe)
         * strukture package-by-feature, gdzie KAZDY pakiet odpowiada 1
         * bounded context.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignAdrJustifyingChoiceForNewProject {
        /*
         * 🧪 Zadanie 14:
         * Napisz PELNY ADR (`Lesson02`) dla decyzji "package by layer czy
         * package by feature dla nowego projektu" - z realistycznym
         * kontekstem (np. rozmiar zespolu, liczba funkcji biznesowych) i
         * konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SimulateAccidentalCrossFeatureRepositoryAccessInLayerStructure {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj (komentarzem + kodem) sytuacje w package-by-layer, gdzie
         * `PaymentService` PRZYPADKOWO uzywa `OrderRepository` bezposrednio
         * (bo oba sa "publiczne i w tym samym pakiecie repository") - w
         * komentarzu wyjasnij, dlaczego to byloby TRUDNIEJSZE do zrobienia w
         * package-by-feature.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignMigrationPlanFromLayerToFeatureStructure {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: rozpisz (min. 4 kroki) PLAN stopniowej migracji
         * istniejacego, duzego systemu z package-by-layer na
         * package-by-feature - dlaczego warto to robic MALYMI krokami
         * (`_16_clean_code/Lesson15`: male kroki refaktoryzacji), nie
         * jednorazowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignHybridStructureWithSharedUtilityPackage {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj strukture hybrydowa z 3 pakietami "by feature" ORAZ 1
         * DODATKOWYM pakietem `shared`/`common` na typy wartosciowe uzywane
         * przez WSZYSTKIE funkcje (np. `Money` z `_16_clean_code/Lesson19`) -
         * w komentarzu wyjasnij, kiedy taki wspolny pakiet jest uzasadniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareTestOrganizationAcrossBothStructures {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz (min. 3 zdania), jak wygladalaby organizacja
         * TESTOW jednostkowych w package-by-layer vs package-by-feature -
         * ktora struktura testow lepiej odzwierciedla strukture produkcyjna?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignFeaturePackageWithPackagePrivateRepository {
        /*
         * 🧪 Zadanie 19:
         * W komentarzu (jako opis, bo prawdziwa wielopakietowa struktura
         * wymaga osobnych plikow) zaprojektuj `OrderRepository` jako
         * `package-private` wewnatrz pakietu `orders` - opisz, co KONKRETNIE
         * przestaje kompilowac sie, gdyby `PaymentService` (inny pakiet)
         * probowal go uzyc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCourseChapterForPackagingStyle {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) strukture pakietow rozdzialu
         * `_10_dao` w tym kursie - czy jest bliżej package-by-layer, czy
         * package-by-feature (czy moze inny podzial - np. wg lekcji)?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullPackageByFeatureStructureForRealisticSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj (jako drzewo tekstowe + symulacja zagniezdzonymi
         * klasami) kompletna strukture package-by-feature dla systemu z 5
         * funkcjami (np. Katalog, Koszyk, Zamowienia, Platnosci, Konto
         * Uzytkownika) - kazda z Controller+Service+Repository.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorFullSystemFromLayerToFeatureStepByStep {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj TEN SAM system z Zadania 21 najpierw w
         * package-by-layer (symulacja zagniezdzonymi klasami), a nastepnie
         * zrefaktoryzuj GO na package-by-feature W 5 MALYCH, NIEZALEZNYCH
         * krokach (1 funkcja na krok) - zweryfikuj identyczne dzialanie po
         * kazdym kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignHybridArchitectureForLargeAndSmallFeaturesDifferently {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj system, gdzie DUZE funkcje (np. Zamowienia - 10+ klas)
         * maja WEWNETRZNY podzial na warstwy, a MALE funkcje (np.
         * "Kontakt" - 2 klasy) NIE maja (wszystko w 1 "plaskim" pakiecie) - w
         * komentarzu uzasadnij te ROZNA strategie w zaleznosci od rozmiaru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildPackagingConsistencyCheckerHeuristic {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> detectPackagingSmells(int
         * featuresSpreadAcrossLayerPackages, int crossFeatureDirectAccessCount)`
         * zwracajaca liste ostrzezen na podstawie prostych metryk -
         * przetestuj dla min. 3 kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignPackageByFeatureAlignedWithFutureModularMonolith {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj package-by-feature strukture dla systemu, ktory W
         * PRZYSZLOSCI (Lesson17) ma stac sie modularnym monolitem - dla
         * KAZDEJ funkcji zaprojektuj JAWNY "publiczny interfejs modulu" (1-2
         * klasy `public`) i "szczegoly wewnetrzne" (`package-private`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareRealWorldRefactoringStoryForBothStrategies {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: napisz krotka "historyjke" (min. 4 zdania) zespolu,
         * ktory zaczal od package-by-layer, urosl do 50+ klas na warstwe, i
         * zdecydowal sie zmigrowac na package-by-feature - opisz KONKRETNY
         * moment/ból, ktory sklonil ich do tej zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForHybridStrategyWithSharedKernel {
        /*
         * 🧪 Zadanie 27:
         * Napisz PELNY ADR uzasadniajacy strategie hybrydowa: package-by-feature
         * dla wiekszosci systemu + 1 wspolny pakiet `shared` na typy
         * wartosciowe (por. Zadanie 17 i "shared kernel" z Lesson06).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignTestForVerifyingNoCrossFeatureLeakage {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode `boolean verifyNoDirectCrossFeatureAccess(List<String>
         * featureADependencies, String featureBRepositoryClassName)`
         * sprawdzajaca (na uproszczonych danych), czy 1 funkcja NIE odwoluje
         * sie bezposrednio do repozytorium innej funkcji - przetestuj dla
         * przypadku poprawnego i naruszajacego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensivePackagingStrategyChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do wyboru miedzy package-by-layer, package-by-feature i
         * hybryda dla nowego projektu - laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullFeatureOrganizedSystemWithEnforcedBoundaries {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj (jako pelna symulacja
         * zagniezdzonymi klasami + drzewo tekstowe w komentarzu) kompletny
         * system (np. platforma edukacyjna: Kursy, Platnosci, Certyfikaty)
         * w package-by-feature z: WYRAZNYM publicznym API kazdej funkcji,
         * wewnetrznymi szczegolami (symulowane jako oznaczone komentarzem
         * "package-private"), i JAWNYM tlumaczeniem (Lesson06/07) na
         * granicach miedzy funkcjami. Zademonstruj pelny przeplyw miedzy
         * min. 2 funkcjami.
         */
        public static void main(String[] args) { }
    }
}
