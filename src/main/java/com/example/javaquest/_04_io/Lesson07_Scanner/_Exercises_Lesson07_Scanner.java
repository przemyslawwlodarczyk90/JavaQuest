package com.example.javaquest._04_io.Lesson07_Scanner;

public class _Exercises_Lesson07_Scanner {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ScannerFromString {
        /*
         * 🧪 Zadanie 1:
         * Utwórz Scanner na Stringu "Ala ma 10 lat" i odczytaj kolejno
         * 2 słowa metodą next() oraz jedną liczbę metodą nextInt().
         * Wypisz odczytane wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NextLineBasic {
        /*
         * 🧪 Zadanie 2:
         * Utwórz Scanner na wieloliniowym Stringu (3 linie tekstu
         * oddzielone znakiem '\n') i odczytaj każdą linię metodą
         * nextLine() w pętli while (hasNextLine()), wypisując ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NextIntNextDouble {
        /*
         * 🧪 Zadanie 3:
         * Odczytaj ze Scannera na Stringu "5 3.14 true" kolejno: int
         * (nextInt), double (nextDouble), boolean (nextBoolean). Wypisz
         * każdą wartość razem z jej typem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_HasNextCheck {
        /*
         * 🧪 Zadanie 4:
         * Utwórz Scanner na Stringu z 4 liczbami oddzielonymi spacją
         * (np. "3 7 2 9"). Użyj hasNextInt() w pętli while, by wczytać
         * i zsumować wszystkie liczby, wypisz sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ScannerFromFile {
        /*
         * 🧪 Zadanie 5:
         * Utwórz plik tekstowy "cwiczenie05.txt" (w katalogu tymczasowym)
         * z 3 liniami "imię wiek" (np. "Jan 25"). Wczytaj plik przez
         * Scanner(File) i wypisz każdą parę imię-wiek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NextVsNextLineDifference {
        /*
         * 🧪 Zadanie 6:
         * Zademonstruj różnicę next() (tylko jedno słowo) vs nextLine()
         * (cała reszta linii) na Stringu "Jan Kowalski ma psa" – wypisz
         * co zwraca next() a co zwraca nextLine() wywołane zaraz po nim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CountWordsInText {
        /*
         * 🧪 Zadanie 7:
         * Użyj Scannera na Stringu z kilkoma zdaniami (np. "To jest test
         * Scannera w Javie") i pętli while (hasNext()), by policzyć
         * liczbę słów w tekście. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NextBooleanDemo {
        /*
         * 🧪 Zadanie 8:
         * Odczytaj ze Scannera serię wartości boolean z tekstu
         * "true false true true false" i policz ile wśród nich było true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ScannerDelimiter {
        /*
         * 🧪 Zadanie 9:
         * Ustaw scanner.useDelimiter(",") i wczytaj dane rozdzielone
         * przecinkami zamiast spacjami, np. Stringu "jablko,banan,gruszka".
         * Wypisz wszystkie odczytane tokeny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CloseFileScanner {
        /*
         * 🧪 Zadanie 10:
         * Utwórz plik tekstowy z kilkoma liniami, otwórz go Scannerem
         * w try-with-resources, wczytaj i wypisz zawartość, a następnie
         * potwierdź, że blok zamknął Scanner poprawnie (bez błędów).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_NextIntNextLineTrap {
        /*
         * 🧪 Zadanie 11:
         * Zademonstruj pułapkę Scannera: na Stringu "20\nJan Nowak\n"
         * wywołaj nextInt(), a zaraz potem nextLine(). Wypisz otrzymany
         * (pusty) String, żeby zobaczyć problem opisany w teorii lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FixNextIntNextLineTrap {
        /*
         * 🧪 Zadanie 12:
         * Napraw problem z zadania 11: na tym samym wejściu
         * "20\nJan Nowak\n" dodaj dodatkowe "pochłaniające" nextLine()
         * zaraz po nextInt(), tak by KOLEJNE nextLine() poprawnie zwróciło
         * "Jan Nowak". Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_InputMismatchHandling {
        /*
         * 🧪 Zadanie 13:
         * Utwórz Scanner na Stringu "abc" i spróbuj wywołać nextInt()
         * w bloku try-catch, łapiąc InputMismatchException. Wypisz
         * komunikat błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RecoverAfterMismatch {
        /*
         * 🧪 Zadanie 14:
         * Na Stringu "abc 42" spróbuj nextInt() (rzuci
         * InputMismatchException, bo "abc" nie jest liczbą). W bloku
         * catch użyj scanner.next(), by pominąć błędny token, a następnie
         * scanner.nextInt(), by poprawnie odczytać kolejną liczbę (42).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidateUserAgeLoop {
        /*
         * 🧪 Zadanie 15:
         * Scanner na Stringu z kilkoma tokenami, gdzie część to poprawne
         * liczby (wiek), a część to błędny tekst (np. "25 abc 30 xyz 18").
         * W pętli while (hasNext()) sprawdzaj hasNextInt(): jeśli tak,
         * wczytaj i wypisz wiek; jeśli nie, pomiń token (next()) i wypisz
         * komunikat o błędnym wpisie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ReadCsvLineByLine {
        /*
         * 🧪 Zadanie 16:
         * Utwórz plik "cwiczenie16.csv" z 3-4 liniami w formacie
         * "imie,wiek,miasto" (np. "Anna,30,Kraków"). Wczytaj plik linia po
         * linii przez Scanner.nextLine(), rozbij każdą linię po przecinku
         * (String.split(",")) i wypisz sparsowane pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ScannerVsBufferedReaderSpeed {
        /*
         * 🧪 Zadanie 17:
         * Zapisz do pliku 10 000 losowych liczb int (po jednej w linii
         * albo oddzielonych spacją). Porównaj czas (System.nanoTime())
         * wczytania ich raz przez Scanner.nextInt() w pętli, a raz przez
         * BufferedReader + Integer.parseInt(). Wypisz, który sposób był
         * szybszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MultiTypeRecordParsing {
        /*
         * 🧪 Zadanie 18:
         * Wczytaj z pliku listę rekordów w formacie "id;nazwa;cena"
         * (średnik jako separator – użyj useDelimiter(";|\\n") albo
         * podobnego wzorca), sparsuj każdą linię na int, String, double
         * i wypisz jako sformatowaną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SumUntilSentinel {
        /*
         * 🧪 Zadanie 19:
         * Odczytuj liczby int ze Scannera na Stringu "5 8 12 -1 20" aż
         * napotkasz wartość -1 (sentinel), która kończy wczytywanie.
         * Zsumuj wszystkie liczby wczytane PRZED napotkaniem -1 i wypisz
         * sumę (powinno być 25).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NestedScannerPerLine {
        /*
         * 🧪 Zadanie 20:
         * Wczytaj plik, w którym każda linia zawiera po kilka liczb
         * oddzielonych spacją (np. wyniki jednego ucznia z kilku
         * przedmiotów w jednej linii, "80 90 75"). Dla KAŻDEJ linii
         * utwórz osobny Scanner(String), rozbij ją na liczby i oblicz
         * średnią dla tej linii, wypisując wynik per linia.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullFormValidator {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj walidację "formularza" z danymi w wieloliniowym
         * Stringu (imię wiek email, oddzielone spacją, po jednym rekordzie
         * na linię, np. "Jan 25 jan@test.pl" oraz błędne wpisy typu
         * "Anna abc annatest.pl"). Dla każdej linii sprawdź czy wiek
         * parsuje się jako liczba (hasNextInt) i czy email zawiera znak
         * '@'. Zbierz i wypisz osobno listę błędów oraz listę poprawnych
         * rekordów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_InteractiveMenuSimulation {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj menu konsolowe: Scanner na Stringu reprezentującym
         * sekwencję "poleceń" użytkownika (np. "1\n2\n5\n3\n"). W pętli
         * do-while odczytuj kolejne opcje (nextInt + konsumujący
         * nextLine) i wypisuj odpowiadający im komunikat (switch po
         * numerze opcji), kończąc pętlę, gdy napotkasz opcję 5
         * ("wyjście").
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RobustNumberParser {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę parseNumbersRobustly(String input), która używa
         * Scannera do wyciągnięcia WSZYSTKICH liczb (int lub double)
         * z dowolnego tekstu zawierającego też słowa (np. "Cena: 19.99
         * zł, ilość: 3 szt, rabat 10%"), łapiąc InputMismatchException dla
         * tokenów nie będących liczbami i pomijając je (next()). Zwróć
         * listę znalezionych liczb i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TransactionParserFromFile {
        /*
         * 🧪 Zadanie 24:
         * Wczytaj plik z historią transakcji w formacie tekstowym
         * "typ kwota" po jednej transakcji na linię (np. "WPLATA 500.0",
         * "WYPLATA 120.5"). Użyj Scannera z hasNextLine() i wewnętrznego
         * Scannera per-linia, by odczytać typ i kwotę, i oblicz końcowe
         * saldo startując od 0.0 (WPLATA dodaje, WYPLATA odejmuje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_QuizGradingSimulation {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj sprawdzanie quizu: Scanner odczytuje z pliku pytania
         * i odpowiedzi ucznia w formacie "pytanieId odpowiedź" (int char,
         * np. "1 A", "2 C"). Porównaj odpowiedzi z tablicą poprawnych
         * odpowiedzi zdefiniowaną w kodzie i policz wynik procentowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_WordFrequencyCounter {
        /*
         * 🧪 Zadanie 26:
         * Wczytaj dłuższy tekst (kilka zdań) Scannerem słowo po słowie
         * (next(), po usunięciu znaków interpunkcyjnych przez
         * replaceAll("[^a-zA-Ząęółśżźćń]", "")). Policz częstość
         * występowania każdego słowa (Map<String,Integer>) i wypisz
         * słowa posortowane malejąco według liczby wystąpień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiFileAggregator {
        /*
         * 🧪 Zadanie 27:
         * Utwórz 3 osobne pliki tekstowe, każdy zawierający listę liczb
         * (wyniki sprzedaży dnia, oddzielone spacją lub w osobnych
         * liniach). Scannerem zsumuj wszystkie liczby ze wszystkich
         * plików łącznie i wypisz sumę całkowitą oraz sumę per plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_StateMachineParser {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj prosty parser komend tekstowych (Scanner na
         * wieloliniowym Stringu z liniami typu "ADD 5", "SUB 3", "MUL 2",
         * "RESET") sterujący "akumulatorem" (int, start 0). Dla każdej
         * linii odczytuj komendę (next()) i argument (nextInt() – gdy
         * dotyczy), w switch wykonaj odpowiednią operację i wypisz wartość
         * akumulatora po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CsvToObjectListWithValidation {
        /*
         * 🧪 Zadanie 29:
         * Wczytaj plik CSV z danymi produktów (nazwa,cena,ilosc) Scannerem
         * z useDelimiter(",|\\n"). Dla każdej linii zwaliduj, czy cena
         * i ilość parsują się jako liczby (obsłuż InputMismatchException
         * per pole, np. próbując Double.parseDouble/Integer.parseInt
         * w try-catch). Zbuduj listę poprawnych rekordów i osobno listę
         * linii odrzuconych z powodu błędu, wypisz obie listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_InteractiveCalculatorFromScript {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj prosty "kalkulator" sterowany skryptem tekstowym
         * (Scanner na wieloliniowym Stringu z komendami typu "PUSH 5",
         * "PUSH 3", "ADD", "PUSH 2", "MUL", "PRINT"), operujący na stosie
         * liczb (np. Deque<Double>). Dla komend PUSH odczytaj argument
         * (nextDouble), dla ADD/SUB/MUL/DIV zdejmij dwa elementy ze stosu,
         * wykonaj działanie i odłóż wynik z powrotem, a po komendzie PRINT
         * wypisz aktualny wynik na szczycie stosu.
         */
        public static void main(String[] args) { }
    }
}
