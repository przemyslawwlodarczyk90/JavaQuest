package com.example.javaquest._01_fundamentals.Lesson06_StringsAndBuilder;

public class _Exercises_Lesson06_StringsAndBuilder {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_StringLength {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj String text = "Java Programming".
         * Wypisz liczbę znaków tego tekstu używając .length().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_CharAtIndex {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj String text = "Java Programming".
         * Wypisz znak na indeksie 0, 5 i ostatni znak.
         * Użyj .charAt() i .length()-1.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_CompareStrings {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj String a = "hello" i String b = "HELLO".
         * Porównaj je przez .equals() – co zwraca?
         * Następnie przez .equalsIgnoreCase() – co teraz?
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_ContainsCheck {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj String sentence = "Java jest super!".
         * Sprawdź i wypisz wyniki:
         * - czy zawiera "super"
         * - czy zaczyna się od "Java"
         * - czy kończy się na "!"
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_IsEmptyAndBlank {
        /*
         * 🧪 Zadanie 5:
         * Sprawdź i wypisz wyniki dla:
         * - "".isEmpty() i "".isBlank()
         * - "   ".isEmpty() i "   ".isBlank()
         * - "tekst".isEmpty() i "tekst".isBlank()
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_IndexOf {
        /*
         * 🧪 Zadanie 6:
         * Zadeklaruj String text = "banana".
         * Wypisz:
         * - indexOf('a') – pierwszy indeks litery 'a'
         * - lastIndexOf('a') – ostatni indeks litery 'a'
         * - indexOf("nan") – indeks podciągu
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_Substring {
        /*
         * 🧪 Zadanie 7:
         * Zadeklaruj String text = "Hello, World!".
         * Wypisz:
         * - substring(7) – od indeksu 7 do końca
         * - substring(0, 5) – od początku do indeksu 4 (włącznie)
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_TrimAndStrip {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj String messy = "   Java   ".
         * Wypisz wynik .trim() i .strip() – powinny usunąć spacje z obu stron.
         * Wypisz też .stripLeading() i .stripTrailing().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_CaseChange {
        /*
         * 🧪 Zadanie 9:
         * Zadeklaruj String text = "Java Programming".
         * Wypisz:
         * - .toUpperCase() – wszystkie litery wielkie
         * - .toLowerCase() – wszystkie litery małe
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_Replace {
        /*
         * 🧪 Zadanie 10:
         * Zadeklaruj String text = "Ala ma kota. Ala lubi kota.".
         * Użyj .replace("kota", "psa") i wypisz wynik.
         * Następnie użyj .replaceFirst("Ala", "Ela") i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_Split {
        /*
         * 🧪 Zadanie 11:
         * Zadeklaruj String csv = "Jan,Kowalski,30,Programista".
         * Podziel po przecinku używając .split(",").
         * Wypisz każdą część w osobnej linii.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_Join {
        /*
         * 🧪 Zadanie 12:
         * Stwórz tablicę String[] words = {"Java", "jest", "super"}.
         * Połącz elementy spacją używając String.join(" ", words).
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_Repeat {
        /*
         * 🧪 Zadanie 13:
         * Zadeklaruj String star = "* ".
         * Użyj .repeat(5) i wypisz wynik.
         * Następnie wypisz linię 20 znaków "-" jako separator.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_ToCharArray {
        /*
         * 🧪 Zadanie 14:
         * Zadeklaruj String word = "Programowanie".
         * Użyj .toCharArray() i w pętli for-each wypisz każdy znak w osobnej linii.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_StringFormat {
        /*
         * 🧪 Zadanie 15:
         * Zadeklaruj: String name = "Anna", int age = 25, double gpa = 4.75.
         * Użyj String.format() aby wypisać: "Student: Anna, wiek: 25, średnia: 4.75".
         * Pamiętaj o formacie %.2f dla double.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_StringPool {
        /*
         * 🧪 Zadanie 16:
         * Zadeklaruj: String a = "test", b = "test", c = new String("test").
         * Porównaj: a==b, a==c, a.equals(c). Wypisz każdy wynik.
         * Wyjaśnij w komentarzu działanie String Pool.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_StringBuilderBasics {
        /*
         * 🧪 Zadanie 17:
         * Stwórz StringBuilder sb = new StringBuilder("Java").
         * Wywołaj:
         * - sb.append(" is amazing")
         * - sb.insert(0, ">> ")
         * Wypisz wynik po każdej operacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_StringBuilderReverse {
        /*
         * 🧪 Zadanie 18:
         * Zadeklaruj String word = "palindrom".
         * Użyj StringBuilder aby odwrócić ten tekst i wypisz wynik.
         * Sprawdź też słowo "level" – czy jest palindromem (czy oryginał == odwrócony)?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_StringBuilderDelete {
        /*
         * 🧪 Zadanie 19:
         * Stwórz StringBuilder sb = new StringBuilder("Hello World!").
         * Usuń fragment " World" używając .delete(5, 11).
         * Wypisz wynik. Następnie zresetuj sb używając .setLength(0) i wypisz jego długość.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_StringBuilderReplace {
        /*
         * 🧪 Zadanie 20:
         * Stwórz StringBuilder sb = new StringBuilder("Kocham Javę").
         * Użyj .replace(7, 11, "Pythona") i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_CountVowels {
        /*
         * 🧪 Zadanie 21:
         * Zadeklaruj String text = "Java Programming is fun".
         * Policz i wypisz liczbę samogłosek (a, e, i, o, u) w tym tekście (ignoruj wielkość liter).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ReverseWords {
        /*
         * 🧪 Zadanie 22:
         * Zadeklaruj String sentence = "Uczę się Javy".
         * Odwróć kolejność słów (wynik: "Javy się Uczę").
         * Użyj split() i StringBuilder lub String.join().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_ReplaceAllRegex {
        /*
         * 🧪 Zadanie 23:
         * Zadeklaruj String text = "Mój numer: 123-456-789 i email: test@test.com".
         * Użyj .replaceAll("[0-9]", "*") aby zastąpić cyfry gwiazdkami.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_InternEffect {
        /*
         * 🧪 Zadanie 24:
         * Stwórz: String a = new String("hello"), b = new String("hello").
         * Wypisz a == b (false). Następnie wywołaj a.intern() i b.intern() i porównaj wyniki.
         * Wyjaśnij w komentarzu, co robi metoda intern().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_ExtractNumbers {
        /*
         * 🧪 Zadanie 25:
         * Zadeklaruj String text = "Cena: 42 zł, rabat: 5 zł, suma: 37 zł".
         * Użyj .replaceAll("[^0-9 ]", "") i .trim() aby wyekstrahować same liczby.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_BuildTableRow {
        /*
         * 🧪 Zadanie 26:
         * Używając StringBuilder zbuduj wiersz tabeli dla:
         * String[] columns = {"Jan", "Kowalski", "30", "Programista"}.
         * Wynik: "| Jan | Kowalski | 30 | Programista |"
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_ParseCsvToTable {
        /*
         * 🧪 Zadanie 27:
         * Zadeklaruj String csv = "Imię,Nazwisko,Wiek\nAnna,Nowak,25\nJan,Kowalski,30".
         * Podziel na wiersze (po \n), każdy wiersz podziel po przecinku.
         * Wypisz w czytelnym formacie tabeli.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_CountWordOccurrences {
        /*
         * 🧪 Zadanie 28:
         * Zadeklaruj String text = "java jest super java jest popularny java".
         * Policz ile razy słowo "java" pojawia się w tekście (używając split i liczenia).
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_CamelCaseToSnakeCase {
        /*
         * 🧪 Zadanie 29:
         * Zadeklaruj String camel = "myVariableName".
         * Konwertuj do snake_case: "my_variable_name".
         * Wskazówka: użyj .toCharArray() i sprawdzaj Character.isUpperCase().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_BuildHtmlTag {
        /*
         * 🧪 Zadanie 30:
         * Napisz program, który przy użyciu StringBuilder tworzy prosty HTML:
         * String tag = "p", String content = "Witaj, świecie!".
         * Wynik: "<p>Witaj, świecie!</p>".
         * Następnie zbuduj cały dokument HTML z tytułem i jednym paragrafem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
