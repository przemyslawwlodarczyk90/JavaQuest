package com.example.javaquest._28_java_evolution.Lesson09_Java11LtsStringAndFilesMethods;

public class _Exercises_Lesson09_Java11LtsStringAndFilesMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CompareIsEmptyWithIsBlank {
        /* 🧪 Zadanie 1: Porownaj `isEmpty()` I `isBlank()` NA STRINGU zlozonym Z SAMYCH spacji. */
        public static void main(String[] args) { }
    }

    static class Exercise02_StripWhitespaceFromString {
        /* 🧪 Zadanie 2: Usun BIALE znaki Z OBU stron stringa uzywajac `strip()`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseStripLeadingAndStripTrailing {
        /* 🧪 Zadanie 3: Uzyj OSOBNO `stripLeading()` I `stripTrailing()` NA TYM SAMYM stringu. */
        public static void main(String[] args) { }
    }

    static class Exercise04_RepeatStringNTimes {
        /* 🧪 Zadanie 4: Powtorz DANY string 5 razy uzywajac `repeat(5)`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_SplitMultilineTextIntoLines {
        /* 🧪 Zadanie 5: Podziel wieloliniowy tekst NA linie uzywajac `lines()`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteStringToFile {
        /* 🧪 Zadanie 6: Zapisz string DO pliku tymczasowego uzywajac `Files.writeString()`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReadStringFromFile {
        /* 🧪 Zadanie 7: Odczytaj CALA zawartosc pliku uzywajac `Files.readString()`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_DeclareLambdaWithVarParameters {
        /* 🧪 Zadanie 8: Zadeklaruj lambde Z `var` NA WSZYSTKICH parametrach. */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareTrimWithStripOnUnicodeWhitespace {
        /* 🧪 Zadanie 9: Porownaj STARY `trim()` Z NOWYM `strip()` NA stringu Z Unicode'owym bialym znakiem. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhatLtsMeansForJava11 {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, CO oznacza "LTS" I DLACZEGO Java 11 byla WYJATKOWO popularna. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CountBlankLinesInMultilineText {
        /* 🧪 Zadanie 11: Policz PUSTE (blank) linie W wieloliniowym tekscie laczac `lines()` I `isBlank()`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildSeparatorLineUsingRepeat {
        /* 🧪 Zadanie 12: Zbuduj LINIE separatora (np. "=".repeat(40)) DO formatowania wyjscia konsolowego. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ReadFileAndCountLinesWithStreamApi {
        /* 🧪 Zadanie 13: Odczytaj plik `Files.readString()` I POLICZ linie przez `lines()` + Stream API. */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteAndAppendToFileUsingFilesWriteString {
        /* 🧪 Zadanie 14: Zapisz, A POTEM DOPISZ DO pliku uzywajac opcji `StandardOpenOption.APPEND`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseVarInLambdaWithAnnotation {
        /* 🧪 Zadanie 15: Uzyj `var` W parametrze lambdy Z ADNOTACJA (np. `@Deprecated` - demonstracyjnie). */
        public static void main(String[] args) { }
    }

    static class Exercise16_ObserveCompileErrorMixingVarWithExplicitType {
        /* 🧪 Zadanie 16: Zaobserwuj BLAD kompilacji PRZY MIESZANIU `var` Z JAWNYM typem W TEJ SAMEJ lambdzie. */
        public static void main(String[] args) { }
    }

    static class Exercise17_RunSingleJavaFileWithoutExplicitCompilation {
        /* 🧪 Zadanie 17: Uruchom (W terminalu) POJEDYNCZY plik `.java` PRZEZ `java Plik.java` BEZ `javac`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareSingleFileLaunchWithTraditionalJavacJavaFlow {
        /* 🧪 Zadanie 18: Porownaj CZAS/kroki `java Plik.java` Z tradycyjnym `javac`+`java` (_11_buildtools/Lesson02). */
        public static void main(String[] args) { }
    }

    static class Exercise19_ProcessCsvLikeTextUsingLinesAndStrip {
        /* 🧪 Zadanie 19: Przetworz PROSTY tekst CSV laczac `lines()`, `strip()` I `split(",")`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareOldFilesReadAllBytesWithNewReadString {
        /* 🧪 Zadanie 20: Porownaj STARE `Files.readAllBytes()`+`new String(...)` Z NOWYM `Files.readString()`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildTextTemplateEngineUsingRepeatAndStrip {
        /* 🧪 Zadanie 21: Zbuduj PROSTY "silnik szablonow" tekstowych laczac `repeat()`, `strip()` I `lines()`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFileBasedConfigLoaderUsingReadString {
        /* 🧪 Zadanie 22: Zaimplementuj PROSTY loader konfiguracji (klucz=wartosc) Z pliku uzywajac `Files.readString()` + `lines()`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasurePerformanceDifferenceBetweenOldAndNewFileApi {
        /* 🧪 Zadanie 23: Zmierz (przyblizeniowo) ROZNICE wydajnosci STAREGO I NOWEGO API plikowego NA WIEKSZYM pliku. */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildLogFileAnalyzerCountingBlankAndNonBlankLines {
        /* 🧪 Zadanie 24: Zbuduj analizator pliku logow LICZACY puste I niepuste linie uzywajac `lines()`+`isBlank()`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDecoratorPatternWithLambdaUsingVarParameters {
        /* 🧪 Zadanie 25: Zaimplementuj PROSTY wzorzec dekoratora Z lambdami UZYWAJACYMI `var` W parametrach Z adnotacjami walidujacymi (koncepcyjnie). */
        public static void main(String[] args) { }
    }

    static class Exercise26_WriteScriptStyleJavaFileRunnableWithoutBuildTool {
        /* 🧪 Zadanie 26: Napisz plik `.java` W STYLU SKRYPTU (jedna klasa, main) URUCHAMIALNY BEZ Maven/Gradle. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildTextDiffToolUsingLinesAndStreamComparison {
        /* 🧪 Zadanie 27: Zbuduj PROSTE narzedzie "diff" PORÓWNUJACE 2 wieloliniowe teksty linia PO linii. */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeWhenSingleFileLaunchIsAppropriateVsFullBuildTool {
        /* 🧪 Zadanie 28: Bez terminala - przeanalizuj, KIEDY `java Plik.java` WYSTARCZY, A KIEDY POTRZEBNY PELNY build tool (_11_buildtools). */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildCsvToObjectParserUsingNewStringAndFilesApi {
        /* 🧪 Zadanie 29: Zbuduj parser CSV->obiekty laczac WSZYSTKIE nowe API Z tej lekcji (`readString`, `lines`, `strip`, `repeat` DO formatowania raportu). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignMigrationChecklistFromJava8ToJava11ForLegacyProject {
        /* 🧪 Zadanie 30: Zaprojektuj LISTE kontrolna MIGRACJI istniejacego projektu Z Javy 8 NA Java 11 (uwzglednij `javax`->`jakarta` OSTRZEZENIE - to nastapi DOPIERO PRZY Spring Boot 3, ale wypisz INNE punkty: nowe API, usuniete moduly Java EE Z JDK). */
        public static void main(String[] args) { }
    }
}
