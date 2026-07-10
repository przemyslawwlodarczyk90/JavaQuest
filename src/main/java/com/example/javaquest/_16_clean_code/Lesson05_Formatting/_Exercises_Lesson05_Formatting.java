package com.example.javaquest._16_clean_code.Lesson05_Formatting;

public class _Exercises_Lesson05_Formatting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainNewspaperMetaphorInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) "metafore gazety" z teorii lekcji i jak przekłada sie na
         * ukladanie metod w klasie Javy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteMethodWithoutBlankLinesBetweenSteps {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode liczaca cene koncowa (podstawa + VAT, potem rabat) BEZ
         * zadnej pustej linii miedzy krokami - wypisz wynik i w komentarzu opisz,
         * dlaczego trudno tu zobaczyc, gdzie konczy sie 1 "mysl", a zaczyna
         * kolejna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddBlankLinesToSeparateLogicalSteps {
        /*
         * 🧪 Zadanie 3:
         * Popraw metode z Zadania 2, dodajac puste linie miedzy logicznie
         * oddzielnymi krokami (np. miedzy "liczeniem VAT" a "liczeniem
         * rabatu") - zweryfikuj identyczny wynik, porownaj czytelnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteCodeWithCommentSplittingCoherentBlock {
        /*
         * 🧪 Zadanie 4:
         * Napisz 3 linie SCISLE powiazanego kodu (np. deklaracja+walidacja+
         * uzycie jednej zmiennej) z niepotrzebnym komentarzem WSTAWIONYM w
         * srodku, sugerujacym falszywy podzial. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RemoveSplittingCommentToRestoreDensity {
        /*
         * 🧪 Zadanie 5:
         * Popraw kod z Zadania 4, usuwajac komentarz rozbijajacy spojny blok -
         * zweryfikuj identyczny wynik i w komentarzu wyjasnij, dlaczego 3 linie
         * "razem" czytaja sie teraz jako 1 spojna mysl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DeclareVariableFarFromItsUsage {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode, w ktorej zmienna `String reportTitle` jest
         * zadeklarowana na SAMEJ GORZE metody, a uzyta dopiero 8+ linii nizej
         * (po innym, niepowiazanym kodzie). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MoveVariableDeclarationCloserToUsage {
        /*
         * 🧪 Zadanie 7:
         * Popraw metode z Zadania 6, przenoszac deklaracje `reportTitle` TUZ
         * przed pierwsze uzycie - zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteOneLineTooLongToRead {
        /*
         * 🧪 Zadanie 8:
         * Napisz JEDNA linie kodu liczaca zlozony warunek boolean (min. 4
         * skladniki polaczone && i ||) w jednej, bardzo dlugiej linii (bez
         * zawijania). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BreakLongLineIntoReadableMultilineExpression {
        /*
         * 🧪 Zadanie 9:
         * Popraw warunek z Zadania 8, rozbijajac go na kilka linii z
         * operatorami logicznymi na POCZATKU kazdej nowej linii i spojnym
         * wcieciem - zweryfikuj identyczny wynik logiczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListVerticalAndHorizontalFormattingRulesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu min. 3 zasady formatowania
         * PIONOWEGO i min. 2 zasady formatowania POZIOMEGO z tej lekcji, bez
         * podgladania teorii.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteUnorderedMethodsIgnoringStepDownRule {
        /*
         * 🧪 Zadanie 11:
         * Napisz klase z 3 metodami w LOSOWEJ kolejnosci: najpierw metoda
         * najnizszego poziomu (szczegol implementacyjny), potem orkiestrator
         * (main-like), potem metoda posredniego poziomu. Wywolaj orkiestrator.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReorderMethodsToFollowStepDownRule {
        /*
         * 🧪 Zadanie 12:
         * Popraw klase z Zadania 11, ukladajac metody wg zasady step-down
         * (orkiestrator na gorze, potem coraz nizszy poziom szczegolu) -
         * zweryfikuj identyczny wynik, porownaj czytelnosc pliku "od gory do
         * dolu".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteClassWithFieldsScatteredAmongMethods {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase z 3 polami instancyjnymi zadeklarowanymi w ROZNYCH
         * miejscach (jedno na gorze, jedno w srodku miedzy metodami, jedno na
         * samym dole) - skomentuj, dlaczego trudno je "znalezc" bez czytania
         * calej klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_GroupAllFieldsAtTopOfClass {
        /*
         * 🧪 Zadanie 14:
         * Popraw klase z Zadania 13, przenoszac WSZYSTKIE pola instancyjne na
         * SAM POCZATEK klasy - zweryfikuj identyczne dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteRelatedHelperMethodsFarApartInFile {
        /*
         * 🧪 Zadanie 15:
         * Napisz 2 SCISLE powiazane metody prywatne (jedna wywoluje druga) -
         * ROZDZIEL je celowo 3-4 NIEPOWIAZANYMI metodami w tym samym pliku, by
         * pokazac, jak trudno je znalezc razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MoveRelatedMethodsCloseTogether {
        /*
         * 🧪 Zadanie 16:
         * Popraw uklad z Zadania 15, przenoszac obie powiazane metody TUZ
         * obok siebie (zasada "conceptual affinity") - zweryfikuj identyczne
         * dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ManuallyAlignVariableAssignmentsInColumns {
        /*
         * 🧪 Zadanie 17:
         * Napisz 4 deklaracje zmiennych z RECZNIE wyrownanymi znakami `=`
         * (dodatkowe spacje, tak by wszystkie `=` byly w tej samej kolumnie).
         * Dodaj PIATA zmienna z dluzsza nazwa i zaobserwuj, ze psuje ona
         * wyrownanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RemoveManualAlignmentUseSingleSpace {
        /*
         * 🧪 Zadanie 18:
         * Popraw kod z Zadania 17, usuwajac reczne wyrownanie (pojedyncza
         * spacja przed/po `=` w kazdej linii) - dodaj kolejna zmienna z
         * dowolna dlugoscia nazwy BEZ potrzeby przerownowywania czegokolwiek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteDeeplyNestedCodeWithFlatIndentation {
        /*
         * 🧪 Zadanie 19:
         * Napisz kod z 3 zagniezdzonymi blokami if (warunek w warunku w
         * warunku), ale ZE SPLASZCZONYMI wcieciami (wszystkie linie na tym
         * samym poziomie wciecia, niezaleznie od zagniezdzenia) - w komentarzu
         * opisz, dlaczego trudno zrozumiec strukture.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FixIndentationToReflectNestingStructure {
        /*
         * 🧪 Zadanie 20:
         * Popraw kod z Zadania 19, dodajac WCIECIA odzwierciedlajace faktyczne
         * zagniezdzenie (kazdy poziom `if` o 4 spacje glebiej) - zweryfikuj
         * identyczne dzialanie logiki.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WriteRealisticClassViolatingAllFormattingRules {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase OrderProcessor swiadomie LAMIACA WSZYSTKIE zasady
         * pionowe z tej lekcji naraz: metody w losowej kolejnosci (bez
         * step-down), pola rozrzucone, brak pustych linii miedzy krokami,
         * niepotrzebne komentarze rozbijajace spojne bloki. Powinna sie
         * kompilowac i dzialac poprawnie mimo zlego formatowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullyReformatOrderProcessorFollowingAllRules {
        /*
         * 🧪 Zadanie 22:
         * Przepisz klase z Zadania 21 od zera, stosujac WSZYSTKIE zasady
         * pionowe z tej lekcji: pola na gorze, step-down w kolejnosci metod,
         * puste linie miedzy krokami, powiazane metody blisko siebie.
         * Zweryfikuj identyczny wynik dzialania programu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureAndReportClassSizeAgainstGuideline {
        /*
         * 🧪 Zadanie 23:
         * Wybierz 1 realna klase z dowolnej wczesniejszej lekcji kursu (np.
         * `_10_dao`) i w komentarzu policz przyblizona liczbe linii - porownaj
         * z orientacyjna wskazowka 200-500 linii z teorii i oceń, czy klasa
         * jest w rozsadnym zakresie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteLongLineWithChainedMethodCalls {
        /*
         * 🧪 Zadanie 24:
         * Napisz JEDNA bardzo dluga linie z lancuchem 5+ wywolan metod na
         * Stringu (np. `.trim().toLowerCase().replace(...).substring(...)...`)
         * bez zadnego zawijania. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BreakMethodChainIntoReadableMultilineFluentStyle {
        /*
         * 🧪 Zadanie 25:
         * Popraw lancuch z Zadania 24, rozbijajac go na wiele linii - KAZDE
         * wywolanie metody (`.metoda(...)`) w osobnej linii, z kropka na
         * POCZATKU nowej linii i spojnym wcieciem. Zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignClassFollowingNewspaperMetaphorFully {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj klase ReportGenerator w PELNI wg metafory gazety: pola
         * na gorze, JEDNA publiczna metoda generate() jako "naglowek" (wysoki
         * poziom - tylko wywolania), min. 4 prywatne metody coraz nizszego
         * poziomu szczegolu ulozone w kolejnosci step-down. Wywolaj generate().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AuditRealClassFromEarlierChapterForStepDownViolations {
        /*
         * 🧪 Zadanie 27:
         * Wybierz 1 klase z dowolnej wczesniejszej lekcji kursu z min. 4
         * metodami. W komentarzu sprawdz, czy metody sa ulozone zgodnie z
         * zasada step-down (caller nad callee) - jesli NIE, zaproponuj nowa
         * kolejnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildFormattingChecklistAsExecutableValidator {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode `List<String> checkLineLengths(List<String> lines, int
         * maxLength)` zwracajaca liste numerow linii (1-indexed) przekraczajacych
         * `maxLength` znakow - przetestuj ja na liscie zawierajacej min. 1
         * zbyt dluga linie i min. 2 poprawne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveFormattingGuidelineDocument {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu WLASNY, kompletny "przewodnik
         * formatowania" dla zespolu (min. 6 punktow: dlugosc linii, wciecia,
         * puste linie, kolejnosc metod, kolejnosc pol, wyrownanie) -
         * inspirowany, ale NIE kopiujac 1:1, tresci teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneBeforeAfterFormattingShowcase {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz kompletna, realistyczna klase (min.
         * 5 metod, min. 3 pola, jakas logika biznesowa np. system rezerwacji)
         * w DWOCH wersjach w tym samym pliku: "PRZED" (swiadomie lamiac
         * WSZYSTKIE zasady formatowania z lekcji) i "PO" (w pelni zgodna z
         * kazda zasada: step-down, gestosc, odleglosc, dlugosc linii,
         * wciecia). Wywolaj obie wersje i pokaz identyczny wynik dzialania.
         */
        public static void main(String[] args) { }
    }
}
