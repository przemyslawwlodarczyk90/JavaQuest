package com.example.javaquest._25_unit_testing.Lesson03_FirstTestAndBuiltInAssertions;

public class _Exercises_Lesson03_FirstTestAndBuiltInAssertions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteTestWithAssertEquals {
        /*
         * 🧪 Zadanie 1:
         * Napisz test uzywajacy `assertEquals` DO sprawdzenia wyniku
         * dzialania (np. konkatenacji Stringow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainEqualsVsSame {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij ROZNICE miedzy `assertEquals`
         * (`.equals()`) A `assertSame` (`==`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteTestDemonstratingEqualsVsSame {
        /*
         * 🧪 Zadanie 3:
         * Napisz test Z 2 obiektami `new String("a")` - zweryfikuj, ze
         * `assertEquals` PRZECHODZI, ale `assertSame` NIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteTestWithAssertThrows {
        /*
         * 🧪 Zadanie 4:
         * Napisz test sprawdzajacy, ze dzielenie PRZEZ zero RZUCA
         * `ArithmeticException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReadExceptionMessageAfterAssertThrows {
        /*
         * 🧪 Zadanie 5:
         * Rozszerz Exercise4 - odczytaj I zweryfikuj TRESC komunikatu
         * wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteTestWithAssertArrayEquals {
        /*
         * 🧪 Zadanie 6:
         * Napisz test sortujacy tablice I sprawdzajacy wynik
         * `assertArrayEquals`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteTestWithAssertNullAndNotNull {
        /*
         * 🧪 Zadanie 7:
         * Napisz test sprawdzajacy metode zwracajaca `null` DLA
         * NIEZNALEZIONEGO elementu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteFailingAssertEqualsAndReadMessage {
        /*
         * 🧪 Zadanie 8:
         * Napisz CELOWO nieudany `assertEquals` I odczytaj DOKLADNY
         * format komunikatu bledu (expected/actual).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteTestWithCustomFailureMessage {
        /*
         * 🧪 Zadanie 9:
         * Dodaj WLASNY komunikat (3. argument) DO `assertTrue`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyCustomMessagesHelp {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, DLACZEGO WLASNY komunikat W asercji
         * PRZYSPIESZA diagnoze bledu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteTestWithAssertAllGrouping {
        /*
         * 🧪 Zadanie 11:
         * Napisz test Z `assertAll` GRUPUJACYM 4 NIEZALEZNE sprawdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ObserveAssertAllReportsMultipleFailures {
        /*
         * 🧪 Zadanie 12:
         * CELOWO POPSUJ 2 Z 4 asercji W `assertAll` - zweryfikuj, ze
         * RAPORT pokazuje OBIE porazki NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareAssertAllWithSequentialAssertions {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: porownaj `assertAll` Z ZWYKLYM ciagiem
         * osobnych asercji - KIEDY pierwsza porazka PRZERYWA test.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteTestWithAssertTimeoutPreemptively {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `assertTimeoutPreemptively` (ZAMIAST `assertTimeout`) I
         * wyjasnij ROZNICE (przerywa WATEK PO timeoucie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteTestThatExceedsTimeout {
        /*
         * 🧪 Zadanie 15:
         * Napisz test Z `assertTimeout` przekraczajacy limit -
         * zweryfikuj PORAZKE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementLambdaBasedAssertThrowsWithMultipleLines {
        /*
         * 🧪 Zadanie 16:
         * Napisz `assertThrows` Z LAMBDA zawierajaca WIELE linii kodu
         * (NIE tylko `throw`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteTestForCustomExceptionHierarchy {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj WLASNA hierarchie wyjatkow (2 klasy) I napisz
         * testy sprawdzajace `assertThrows` DLA OBU.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareBuiltInAssertionsVerbosity {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: policz ILE znakow zajmuje sprawdzenie
         * "lista zawiera element X I MA rozmiar 3" WBUDOWANYMI
         * asercjami - zapowiedz AssertJ (krocej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteParameterizedLikeTestManually {
        /*
         * 🧪 Zadanie 19:
         * NAPISZ RECZNIE 5 osobnych metod `@Test` sprawdzajacych TA
         * SAMA logike Z ROZNYMI danymi - zapowiedz `@ParameterizedTest`
         * (Lesson08, znaczne skrocenie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignAssertionHelperMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz WLASNA metode pomocnicza `assertPositiveEven(int)`
         * OPAKOWUJACA 2 wbudowane asercje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomAssertionsClass {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj WLASNA klase `MyAssertions` Z 5 statycznymi metodami
         * asercji DOMENOWYCH (np. `assertValidEmail(String)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureAssertAllVsSequentialPerformance {
        /*
         * 🧪 Zadanie 22:
         * Zmierz narzut `assertAll` (lambda PER asercja) WZGLEDEM
         * bezposredniego wywolania NA 10000 iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementRecursiveArrayComparison {
        /*
         * 🧪 Zadanie 23:
         * Napisz test porownujacy ZAGNIEZDZONE tablice (`int[][]`) -
         * `assertArrayEquals` REKURENCYJNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignAssertionForFloatingPointComparison {
        /*
         * 🧪 Zadanie 24:
         * Uzyj `assertEquals(double, double, double delta)` DO
         * porownania liczb zmiennoprzecinkowych - wyjasnij, DLACZEGO
         * DELTA jest KONIECZNA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAssertThrowsWithExactMessageMatching {
        /*
         * 🧪 Zadanie 25:
         * Napisz test sprawdzajacy DOKLADNY wzorzec (regex) komunikatu
         * wyjatku, NIE tylko rownosc Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignAssertionLibraryComparisonMatrix {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: zbuduj MACIERZ porownawcza (Assertions.* vs
         * AssertJ vs Hamcrest) - CZYTELNOSC/MOZLIWOSCI/populacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSoftAssertionsManually {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj WLASNY, PROSTY mechanizm "soft assertions"
         * (zbieranie WIELU bledow, RZUCENIE JEDNEGO zbiorczego wyjatku
         * NA KONIEC) - zapowiedz AssertJ `SoftAssertions` (Lesson05-07).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestConcurrentAssertionExecution {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_05_multithreading` - zweryfikuj, CZY `assertAll`
         * MOZE bezpiecznie wykonywac asercje ROWNOLEGLE (odpowiedz:
         * NIE domyslnie - sprawdz Javadoc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementTimeoutWithCleanupOnFailure {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj test Z `assertTimeoutPreemptively`, KTORY
         * POPRAWNIE SPRZATA zasoby (try-finally) NAWET GDY timeout
         * przerywa watek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullAssertionShowcaseWithAllTypes {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj JEDNA klase testowa UZYWAJACA WSZYSTKICH 12 typow
         * asercji Z tej lekcji W JEDNYM spojnym scenariuszu domenowym
         * (np. "koszyk zakupowy").
         */
        public static void main(String[] args) { }
    }
}
