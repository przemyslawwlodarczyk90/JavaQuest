package com.example.javaquest._04_io.Lesson23_CSV;

public class _Exercises_Lesson23_CSV {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteBasicCsv {
        /*
         * 🧪 Zadanie 1:
         * Stwórz plik tymczasowy CSV z nagłówkiem "name,age,city" i 3 wierszami danych
         * (np. Jan,30,Warszawa) używając BufferedWriter i writer.newLine().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadRawLines {
        /*
         * 🧪 Zadanie 2:
         * Wczytaj plik CSV z zadania 1 przy pomocy BufferedReader.readLine() w pętli
         * i wypisz każdą linię tak jak jest (surowy tekst), łącznie z nagłówkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SkipHeaderAndSplit {
        /*
         * 🧪 Zadanie 3:
         * Wczytaj nagłówek osobno (pierwsza linia), pomiń go, a każdą kolejną linię
         * podziel metodą split(",") na tablicę String[] i wypisz zawartość tablic.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MapRowsToRecord {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj record Person(String name, int age, String city). Zmapuj sparsowane
         * wiersze String[] z zadania 3 na listę List<Person> i wypisz każdy obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CountRows {
        /*
         * 🧪 Zadanie 5:
         * Wczytaj plik CSV i policz liczbę wierszy z danymi (bez nagłówka, bez pustych linii).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SkipBlankLines {
        /*
         * 🧪 Zadanie 6:
         * Do pliku CSV z zadania 1 dodaj ręcznie kilka pustych linii pomiędzy wierszami.
         * Wczytaj plik pomijając puste linie (line.isBlank()) i sprawdź, że liczba
         * poprawnych wierszy się zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteListOfObjects {
        /*
         * 🧪 Zadanie 7:
         * Mając List<Person> (3-5 obiektów), zapisz je do nowego pliku CSV z nagłówkiem
         * "name,age,city" – jedna linia na osobę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FilterRows {
        /*
         * 🧪 Zadanie 8:
         * Wczytaj CSV z osobami i wypisz tylko te, których wiek (age) jest większy niż 30.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SemicolonSeparator {
        /*
         * 🧪 Zadanie 9:
         * Zapisz plik CSV używając średnika ";" jako separatora zamiast przecinka
         * (np. "name;age;city"), a potem odczytaj go dzieląc linie przez split(";").
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AverageAge {
        /*
         * 🧪 Zadanie 10:
         * Wczytaj CSV z osobami i oblicz średni wiek (age) na podstawie sparsowanych wierszy.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CommaPitfallDemo {
        /*
         * 🧪 Zadanie 11:
         * Zapisz w pliku CSV wiersz z nazwiskiem zawierającym przecinek bez cudzysłowów,
         * np. Kowalski, Jan,30,Warszawa. Wczytaj naiwnym split(",") i pokaż, że wiersz
         * rozpada się na złą liczbę pól (błąd).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ParseQuotedLine {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę parseQuotedLine(String line) -> String[], która poprawnie obsłuży
         * wiersz typu "Kowalski, Jan",30,Warszawa (wartość w cudzysłowach z przecinkiem
         * w środku traktowana jako jedno pole).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_EscapeCommasOnWrite {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę escapeField(String value), która owija wartość w cudzysłowy,
         * jeśli zawiera przecinek. Użyj jej przy zapisie listy osób, gdzie jedno nazwisko
         * zawiera przecinek, i sprawdź poprawność zapisanego pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MergeTwoCsvFiles {
        /*
         * 🧪 Zadanie 14:
         * Mając dwa pliki CSV z tym samym nagłówkiem "name,age,city", scal je w jeden
         * plik wynikowy z pojedynczym nagłówkiem i wszystkimi wierszami danych z obu plików.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SortAndRewrite {
        /*
         * 🧪 Zadanie 15:
         * Wczytaj CSV z osobami, posortuj sparsowane obiekty Person po wieku rosnąco,
         * a następnie zapisz posortowaną listę do nowego pliku CSV.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RowsAsMaps {
        /*
         * 🧪 Zadanie 16:
         * Wczytaj CSV i zamiast mapować na klasę Person, zbuduj List<Map<String,String>>,
         * gdzie kluczami map są nazwy kolumn wzięte z nagłówka. Wypisz przykładowy wiersz jako Map.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ValidateRowLength {
        /*
         * 🧪 Zadanie 17:
         * Wczytaj CSV, w którym jeden wiersz ma za mało pól (np. brakuje city).
         * Podczas parsowania wykryj taki wiersz (row.length != oczekiwana liczba kolumn)
         * i wypisz komunikat o błędnym wierszu zamiast rzucić wyjątek indeksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddComputedColumn {
        /*
         * 🧪 Zadanie 18:
         * Wczytaj CSV z osobami i dopisz do każdego wiersza nową kolumnę "isAdult"
         * (true/false na podstawie age >= 18), zapisując wynik do nowego pliku
         * z rozszerzonym nagłówkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GroupByColumn {
        /*
         * 🧪 Zadanie 19:
         * Wczytaj CSV z osobami i zgrupuj je po kolumnie city (Map<String, List<Person>>),
         * a następnie wypisz liczbę osób w każdym mieście.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CsvToJsonBridge {
        /*
         * 🧪 Zadanie 20:
         * Wczytaj CSV z osobami, zmapuj na List<Person>, a następnie (korzystając z Jacksona
         * poznanego w poprzedniej lekcji) zserializuj tę listę do JSON-a i zapisz do pliku .json.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericCsvMapper {
        /*
         * 🧪 Zadanie 21:
         * Napisz generyczną metodę <T> List<T> readCsv(Path file, Function<String[], T> rowMapper),
         * która wczytuje plik, pomija nagłówek i mapuje każdy wiersz na obiekt typu T
         * przy pomocy przekazanej funkcji. Użyj jej do wczytania listy Person.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullCsvLineParser {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj parseCsvLine(String line) -> String[] zgodny w uproszczeniu
         * z RFC 4180: obsłuż wartości w cudzysłowach zawierające przecinki ORAZ
         * escapowane cudzysłowy "" wewnątrz takich wartości (np. "Jan ""Wielki"" Kowalski").
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_StreamingAggregate {
        /*
         * 🧪 Zadanie 23:
         * Wygeneruj duży plik CSV (np. 50 000 wierszy) z kolumnami amount. Wczytaj go
         * linia po linii (bez wczytywania całości do pamięci) i policz sumę kolumny amount
         * w locie, wypisując wynik na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CsvDiffTool {
        /*
         * 🧪 Zadanie 24:
         * Dwa pliki CSV o tej samej strukturze (np. name,age,city) z kolumną "name" jako
         * kluczem. Napisz narzędzie, które porówna wiersze o tym samym kluczu między
         * plikiem A i B, i wypisze te, które się różnią (inny age lub city).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CsvCrudDatabase {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj prostą "bazę danych" opartą na pliku CSV z operacjami:
         * insert(Person), update(name, nowyPerson), delete(name) – każda operacja
         * wczytuje cały plik, modyfikuje dane w pamięci i zapisuje go od nowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HandleMissingValues {
        /*
         * 🧪 Zadanie 26:
         * Wczytaj CSV, w którym niektóre wiersze mają puste pole city (np. "Anna,25,").
         * Podczas mapowania na Person podstaw wartość domyślną "Unknown" dla pustych pól.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiFormatExportCompare {
        /*
         * 🧪 Zadanie 27:
         * Zapisz tę samą listę List<Person> do pliku CSV i do pliku JSON (Jackson).
         * Porównaj rozmiary obu plików i wypisz wniosek, który format jest bardziej
         * kompaktowy, a który bardziej czytelny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_EmbeddedNewlinesChallenge {
        /*
         * 🧪 Zadanie 28:
         * Zapisz ręcznie plik CSV z wartością zawierającą znak nowej linii wewnątrz
         * cudzysłowów (wielolinijkowa komórka). Spróbuj wczytać go zwykłym readLine()
         * w pętli i pokaż, dlaczego się psuje. Zaproponuj (opisz w komentarzu / naszkicuj)
         * alternatywne podejście czytające cały plik i tokenizujące go inaczej niż liniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PerformanceTest {
        /*
         * 🧪 Zadanie 29:
         * Wygeneruj 100 000 wierszy losowych danych CSV i zapisz je do pliku, mierząc
         * czas zapisu (System.nanoTime()). Następnie wczytaj i sparsuj cały plik,
         * mierząc czas odczytu. Wypisz oba czasy w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ExpenseTrackerProject {
        /*
         * 🧪 Zadanie 30:
         * Wczytaj plik CSV z wydatkami (kolumny: date,category,amount). Oblicz sumę
         * wydatków w każdej kategorii oraz sumę całkowitą, a wynik zapisz jako nowy
         * plik CSV-podsumowanie z kolumnami category,total (plus osobno wypisz sumę całkowitą).
         */
        public static void main(String[] args) { }
    }
}
