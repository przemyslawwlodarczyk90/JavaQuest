package com.example.javaquest._16_clean_code.Lesson21_LegacyCodeAndTechnicalDebt;

public class _Exercises_Lesson21_LegacyCodeAndTechnicalDebt {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainFeathersDefinitionOfLegacyCodeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) definicje kodu legacy wg Michaela Feathersa i dlaczego jest
         * ona przydatniejsza niz potoczne "stary kod".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ClassifyThreeScenariosIntoDebtQuadrant {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: dla 3 podanych scenariuszy (a) "kopiujemy kod, bo
         * nie znamy jeszcze lepszego sposobu", (b) "swiadomie piszemy
         * uproszczona wersje z planem rozbudowy w tickecie", (c) "piszemy bez
         * testow, bo 'nie ma na to czasu', bez zadnego planu naprawy" -
         * przypisz KAZDY do jednej z 4 cwiartek kwadrantu Fowlera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteClassWithoutSeamCreatingDependencyInside {
        /*
         * 🧪 Zadanie 3:
         * Napisz klase `LegacyPriceCalculator` tworzaca w sobie `new
         * TaxRateProvider()` (bez szwu) - w komentarzu wyjasnij, dlaczego NIE
         * da sie podmienic stawki podatku w tescie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IntroduceSeamByExtractingInterface {
        /*
         * 🧪 Zadanie 4:
         * Wprowadz SZEW do klasy z Zadania 3: interfejs `TaxRateProvider` +
         * `SeamPriceCalculator` przyjmujacy go przez konstruktor. Podaj
         * WLASNA, przewidywalna implementacje do "testu" (zwykle wywolanie w
         * `main()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteCharacterizationTestForUndocumentedMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode `String classifyShippingSpeed(int days)` z
         * nieoczywista logika progow. Napisz test charakteryzujacy (metoda
         * wywolujaca ja dla min. 5 wartosci i wypisujaca kazdy wynik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyMissingTestsAsLegacySignal {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wybierz 1 klase z dowolnej wczesniejszej lekcji
         * kursu - w komentarzu oceń (wg definicji Feathersa), czy jest to
         * "kod legacy" (czy sam kurs ma gdziekolwiek prawdziwe testy
         * jednostkowe dla tej klasy, czy tylko demo w `main()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DesignSimpleStranglerRouterForTwoImplementations {
        /*
         * 🧪 Zadanie 7:
         * Zaprojektuj interfejs `Greeter` (greet(String name)) z 2
         * implementacjami (`LegacyGreeter`, `ModernGreeter`) i prosty
         * `GreeterRouter` z polem `boolean useModern` decydujacym, ktorej
         * uzyc. Przetestuj obie wartosci flagi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyDeliberateDebtNeedsDocumentation {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * SWIADOMY dlug techniczny powinien byc UDOKUMENTOWANY (np. komentarz
         * w kodzie + ticket), a nie tylko "w glowie" autora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ApplyBoyScoutRuleToLegacyMethod {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode ze zla nazwa zmiennej i 1 magiczna liczba. Napraw
         * TYLKO te 2 drobne rzeczy (zasada skauta z Lesson15) - NIE zmieniaj
         * nic wiecej. Zweryfikuj identyczny wynik przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListStepsOfLegacyCodeChangeAlgorithmFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) 5
         * krokow "Legacy Code Change Algorithm" Feathersa w poprawnej
         * kolejnosci.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorLegacyClassStepByStepUsingSeamAndCharacterizationTest {
        /*
         * 🧪 Zadanie 11:
         * Napisz "legacy" klase `LegacyDiscountCalculator` z metoda tworzaca
         * `new` zaleznosc W SRODKU oraz nieoczywista logika rabatu. Zastosuj
         * PELNY algorytm Feathersa: (1) napisz test charakteryzujacy, (2)
         * wprowadz szew, (3) zrefaktoryzuj, weryfikujac test po kazdym kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DesignStranglerFigMigrationForThreeStagePercentages {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz wzorzec StranglerRouter z teorii (lub napisz podobny dla
         * innej funkcjonalnosci) o 3 ETAPY migracji: 0%, 50%, 100% ruchu na
         * nowej implementacji - zademonstruj kazdy etap z min. 4 "klientami"
         * (roznymi ID) i policz, ilu trafilo na KTORA implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentifyAllSeamsMissingInGivenLegacyClass {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase `LegacyReportService` z 3 zaleznosciami tworzonymi
         * przez `new` w srodku (np. DataSource, Formatter, Logger). W
         * komentarzu wskaz WSZYSTKIE 3 miejsca, gdzie brakuje szwu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IntroduceSeamsForAllThreeDependenciesFromExercise13 {
        /*
         * 🧪 Zadanie 14:
         * Wprowadz szwy dla WSZYSTKICH 3 zaleznosci z Zadania 13 (interfejsy +
         * constructor injection, Lesson11) - zaimplementuj klase
         * `TestableReportService` przyjmujaca wszystkie 3 przez konstruktor i
         * zademonstruj uzycie z prostymi, wlasnymi implementacjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteComprehensiveCharacterizationTestSuiteForComplexMethod {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode `String calculateLegacyShippingCost(double weight,
         * String country, boolean express)` z min. 4 kombinacjami warunkow.
         * Napisz test charakteryzujacy pokrywajacy WSZYSTKIE sensowne
         * kombinacje (min. 6 wywolan) - wypisz wszystkie wyniki jako
         * dokumentacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UseCharacterizationTestAsSafetyNetWhileRefactoring {
        /*
         * 🧪 Zadanie 16:
         * Zrefaktoryzuj metode z Zadania 15 (np. rozbij na male metody, patrz
         * Lesson16) - uruchom test charakteryzujacy PO KAZDYM kroku i
         * potwierdz identyczne wyniki dla wszystkich kombinacji z Zadania 15.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignDeliberateDebtWithDocumentedPlanToPayItOff {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode z CELOWO uproszczona logika (np. tylko 1 waluta
         * zamiast wielu) - dodaj KOMENTARZ w stylu "// DLUG TECHNICZNY:
         * ..." opisujacy, CO zostalo uproszczone i JAKI jest plan rozbudowy.
         * To jest przyklad ostroznego+celowego dlugu (najlepsza cwiartka).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareCostOfChangeWithAndWithoutSeam {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj TA SAMA funkcjonalnosc na 2 sposoby: (a) BEZ szwu
         * (zaleznosc tworzona przez `new`), (b) Z szwem (constructor
         * injection). W komentarzu opisz, o ile TRUDNIEJ byloby dodac test z
         * atrapa danych w wersji (a) niz w wersji (b).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignStranglerRouterWithGradualPercentageIncrease {
        /*
         * 🧪 Zadanie 19:
         * Napisz petle symulujaca STOPNIOWE zwiekszanie ruchu na nowej
         * implementacji co 10 punktow procentowych (0, 10, 20, ..., 100) -
         * dla kazdego etapu wypisz, ile z 20 "klientow" (ID 1-20) trafiloby
         * na nowa implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCodeFromEarlierChapterForDebtQuadrant {
        /*
         * 🧪 Zadanie 20:
         * Wybierz 1 fragment kodu z dowolnej wczesniejszej lekcji kursu,
         * ktory zawiera komentarz typu "uproszczone" / "DODANE" / "celowo
         * NIE" (patrz CLAUDE.md rozdzialow tego kursu jako inspiracja) - w
         * komentarzu oceń, do ktorej cwiartki kwadrantu Fowlera by pasowal.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullLegacyRefactoringWorkflowForRealisticClass {
        /*
         * 🧪 Zadanie 21:
         * Napisz realistyczna "legacy" klase `LegacyInvoiceService` (min. 2
         * zaleznosci tworzone przez `new`, min. 1 metoda z nieoczywista
         * logika) - przeprowadz PELNY workflow: test charakteryzujacy, szwy
         * dla obu zaleznosci, refaktoryzacja na male metody (Lesson16), z
         * weryfikacja PO KAZDYM kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementStranglerFigForThreeMethodLegacyApi {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj interfejs z 3 metodami (np. `PaymentGateway` z
         * charge/refund/getStatus) - zaimplementuj Legacy i Modern wersje.
         * Zbuduj Router pozwalajacy przelaczac KAZDA z 3 metod NIEZALEZNIE
         * (np. `charge` juz na nowej implementacji, `refund`/`getStatus`
         * nadal na starej) - zademonstruj taki "mieszany" stan migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureAndReduceTechnicalDebtUsingStaticAnalysisFromLesson20 {
        /*
         * 🧪 Zadanie 23:
         * Napisz "legacy" kod z min. 3 smellami z Lesson14 - jesli masz
         * dostep do wzorca z Lesson20 (PMD), uruchom analize i potraktuj
         * LICZBE naruszen jako przyblizona "miare dlugu technicznego". Napraw
         * kod i pokaz SPADEK tej liczby (lub, jesli wolisz podejscie bez PMD,
         * policz smelle RECZNIE przed/po).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignPrioritizationMatrixForMultipleDebtItems {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: wymysl 5 roznych "pozycji dlugu technicznego" (np.
         * "brak testow dla modulu platnosci", "przestarzala biblioteka",
         * "duplikacja logiki walidacji") - w komentarzu oceń kazda wg 2 osi
         * (wplyw na biznes: wysoki/niski, koszt naprawy: wysoki/niski) i
         * ustal KOLEJNOSC, w jakiej warto je splacac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementIncrementalSeamIntroductionAcrossMultipleClasses {
        /*
         * 🧪 Zadanie 25:
         * Napisz 3 POWIAZANE "legacy" klasy (np. Order -> PaymentProcessor ->
         * ReceiptPrinter), gdzie KAZDA tworzy nastepna przez `new`. Wprowadz
         * szwy STOPNIOWO, klasa po klasie (3 osobne kroki refaktoryzacji),
         * weryfikujac dzialanie calego lancucha po KAZDYM kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildFeatureToggleAsAlternativeToStranglerRouter {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj mechanizm "feature toggle" (prostszy kuzyn Strangler
         * Fig) - klasa `FeatureToggles` z metoda `isEnabled(String
         * featureName)` (na podstawie Map<String,Boolean>) - uzyj go do
         * przelaczania miedzy stara a nowa logika w 1 metodzie. Porownaj w
         * komentarzu z pelnym Strangler Router (kiedy wystarczy prostszy
         * toggle, a kiedy potrzebny caly router).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignRollbackSafeMigrationUsingStranglerPattern {
        /*
         * 🧪 Zadanie 27:
         * Rozszerz StranglerRouter (z teorii lub Zadania 12) o mozliwosc
         * NATYCHMIASTOWEGO cofniecia (rollback) do 0% w razie wykrycia
         * problemu - zademonstruj: 100% ruchu na nowej implementacji, "wykrycie
         * problemu" (symulowany warunek), natychmiastowy powrot do starej
         * implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildDebtTrackingLogAsSimpleInMemoryRegistry {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj `record TechnicalDebtItem(String description, String
         * quadrant, String plannedFix)` oraz `DebtRegistry` (lista takich
         * wpisow) z metoda `addDebt(...)` i `printAllDebt()`. Dodaj min. 3
         * wpisy reprezentujace rozne cwiartki kwadrantu Fowlera i wypisz
         * cala liste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveLegacyCodeHandlingChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do bezpiecznej pracy z kodem legacy - laczac WSZYSTKIE
         * pojecia z tej lekcji (definicja Feathersa, kwadrant dlugu, szwy,
         * testy charakteryzujace, Strangler Fig, stopniowa splata).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullLegacyModernizationProject {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj KOMPLETNY,
         * realistyczny projekt modernizacji "legacy" systemu zarzadzania
         * zamowieniami laczacy WSZYSTKIE techniki z tej lekcji: (1)
         * zidentyfikuj i udokumentuj (komentarzem) dlug techniczny wg
         * kwadrantu Fowlera, (2) napisz testy charakteryzujace dla
         * kluczowej, nieoczywistej logiki, (3) wprowadz szwy dla WSZYSTKICH
         * zaleznosci tworzonych przez `new`, (4) zaimplementuj Strangler
         * Fig Router migrujacy stopniowo (0% -> 50% -> 100%) do nowej
         * implementacji, z mozliwoscia rollbacku. Zademonstruj caly proces
         * krok po kroku.
         */
        public static void main(String[] args) { }
    }
}
