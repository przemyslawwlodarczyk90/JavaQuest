package com.example.javaquest._04_io.Lesson06_PrintWriterAndStream;

public class _Exercises_Lesson06_PrintWriterAndStream {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicPrintln {
        /*
         * 🧪 Zadanie 1:
         * Utwórz PrintWriter na pliku "cwiczenie01.txt" (w katalogu
         * tymczasowym System.getProperty("java.io.tmpdir")) i zapisz nim
         * 3 linie tekstu za pomocą println(). Odczytaj plik i wypisz jego
         * zawartość na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PrintVsPrintln {
        /*
         * 🧪 Zadanie 2:
         * Użyj print() i println() naprzemiennie, by zapisać do pliku
         * tekst "Imię: Jan Wiek: 25" w jednej linii (samym print()),
         * a następnie osobno w dwóch liniach (println()). Porównaj wynik
         * po odczytaniu pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_Printf {
        /*
         * 🧪 Zadanie 3:
         * Użyj printf() by zapisać do pliku sformatowaną linię z imieniem
         * (String), wiekiem (int) i wzrostem (double, 2 miejsca po
         * przecinku), np. "Jan ma 25 lat i 180.50 cm wzrostu.".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FormatMethod {
        /*
         * 🧪 Zadanie 4:
         * Użyj metody format() (alias printf) by zapisać tabelkę 3
         * produktów (nazwa, cena) z wyrównaniem kolumn, np. wzorzec
         * "%-10s %8.2f%n".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IsSystemOutPrintStream {
        /*
         * 🧪 Zadanie 5:
         * Sprawdź i wypisz, czy System.out oraz System.err są instancjami
         * PrintStream (instanceof).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PrintWriterFromWriter {
        /*
         * 🧪 Zadanie 6:
         * Utwórz PrintWriter opakowujący FileWriter (PrintWriter(Writer))
         * i zapisz nim 2 linie zawierające polskie znaki (ą, ę, ż) do
         * pliku "cwiczenie06.txt", z jawnym kodowaniem UTF-8.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PrintWriterFromOutputStream {
        /*
         * 🧪 Zadanie 7:
         * Utwórz PrintWriter opakowujący bezpośrednio FileOutputStream
         * (PrintWriter(OutputStream)) i zapisz nim 2 linie tekstu do pliku
         * "cwiczenie07.txt".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AutoFlushDemo {
        /*
         * 🧪 Zadanie 8:
         * Utwórz PrintWriter z autoFlush=true na pliku "cwiczenie08.txt",
         * zapisz kilka println() i po KAŻDEJ linii sprawdź rozmiar pliku
         * (file.length()), bez zamykania strumienia – wypisz rozmiar po
         * każdym println().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CheckErrorMethod {
        /*
         * 🧪 Zadanie 9:
         * Zapisz PrintWriterem kilka linii do pliku "cwiczenie09.txt"
         * i wywołaj checkError() po zapisie (przed zamknięciem strumienia),
         * wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NewLineFormatting {
        /*
         * 🧪 Zadanie 10:
         * Użyj %n w printf() (zamiast \n) do zapisania 3 linii do pliku
         * "cwiczenie10.txt" i sprawdź (odczytując linie), czy plik zawiera
         * poprawne separatory linii.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AutoFlushFalseVsTrue {
        /*
         * 🧪 Zadanie 11:
         * Utwórz dwa PrintWritery na dwóch różnych plikach – jeden
         * z autoFlush=false, drugi z autoFlush=true. W obu wywołaj
         * println(), ale NIE zamykaj strumieni od razu (bez
         * try-with-resources) – sprawdź rozmiar obu plików zaraz po
         * println() i zaobserwuj różnicę. Na końcu jawnie wywołaj
         * flush()/close() na obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RedirectSystemOutToFile {
        /*
         * 🧪 Zadanie 12:
         * Przekieruj System.out do pliku "cwiczenie12.txt" za pomocą
         * System.setOut(new PrintStream(file)). Wypisz kilka linii przez
         * System.out.println(), a następnie przywróć oryginalny System.out
         * i wypisz na konsoli potwierdzenie zakończenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RedirectAndRestore {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę captureConsoleOutput(Runnable action), która
         * przekierowuje System.out do pliku tymczasowego, wykonuje
         * action.run(), przywraca oryginalny System.out i zwraca
         * zawartość pliku jako String. Przetestuj ją, przekazując
         * Runnable wypisujący kilka linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LogToFileHelper {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj metodę logToFile(String wiadomosc), która za
         * każdym wywołaniem DOPISUJE linię z timestampem (np.
         * System.currentTimeMillis()) do pliku "log.txt", używając
         * PrintWriter(new FileWriter(file, true), true) (autoFlush=true).
         * Wywołaj ją kilka razy i odczytaj zawartość pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FileWriterVsPrintWriter {
        /*
         * 🧪 Zadanie 15:
         * Zapisz te same dane (3 liczby i ich sumę) raz przy pomocy
         * samego FileWriter.write(String) (ręczne sklejanie tekstu przez
         * konkatenację), a raz przy pomocy PrintWriter.printf(). Porównaj
         * zawartość obu plików wynikowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultiFormatReport {
        /*
         * 🧪 Zadanie 16:
         * Wygeneruj PrintWriterem raport sprzedaży do pliku
         * "raport16.txt": nagłówek, 5 wierszy produktów (nazwa, ilość:int,
         * cena:double, wartość=ilość*cena) sformatowanych w kolumnach
         * (printf), oraz linię podsumowania z sumą wartości wszystkich
         * produktów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PrintStreamToConsoleAndFile {
        /*
         * 🧪 Zadanie 17:
         * Utwórz osobny PrintStream zapisujący do pliku "cwiczenie17.txt"
         * (bez przekierowywania System.out). Wypisz te same dane raz przez
         * zwykłe System.out.println(), a raz przez ten własny PrintStream,
         * następnie odczytaj plik i porównaj zawartość z tym, co widać
         * na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ErrorStreamRedirect {
        /*
         * 🧪 Zadanie 18:
         * Przekieruj System.err (System.setErr) do osobnego pliku
         * "errors18.txt", wypisz przez System.err.println() 2 komunikaty
         * błędów, przywróć oryginalny System.err, odczytaj i wypisz
         * zawartość pliku errors18.txt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FormattedNumberLocale {
        /*
         * 🧪 Zadanie 19:
         * Użyj printf z wzorcem "%,.2f" żeby zapisać dużą liczbę
         * (1234567.891) z separatorem tysięcy, i porównaj z wersją bez
         * separatora ("%.2f") – zapisz obie wersje do pliku i wypisz
         * różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ConditionalLoggingLevels {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj metodę log(String poziom, String wiadomosc), która
         * zapisuje PrintWriterem do pliku "log20.txt" linię w formacie
         * "[POZIOM] wiadomość" TYLKO jeśli poziom to "ERROR" lub "WARN"
         * (pomija "INFO" i "DEBUG"). Przetestuj wywołaniami z różnymi
         * poziomami i sprawdź, co trafiło do pliku.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CsvReportGenerator {
        /*
         * 🧪 Zadanie 21:
         * Mając listę obiektów Pracownik(String imie, String dzial, double
         * pensja), wygeneruj plik CSV "pracownicy21.csv" (rozdzielany
         * przecinkami) używając PrintWriter.printf(), z nagłówkiem kolumn
         * w pierwszej linii. Następnie odczytaj plik z powrotem i wypisz
         * sformatowaną tabelkę na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DualOutputLogger {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj klasę/metodę TeeLogger.log(String msg), która
         * przy każdym wywołaniu zapisuje wiadomość JEDNOCZEŚNIE do
         * System.out ORAZ do pliku "log22.txt" (dwa niezależne
         * PrintWritery/PrintStreamy użyte w jednej metodzie). Przetestuj
         * kilkoma wiadomościami i zweryfikuj plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RedirectedTestHarness {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę testFunction(Runnable functionUnderTest, String
         * expectedOutput), która przekierowuje System.out do bufora
         * (pliku tymczasowego), uruchamia functionUnderTest, przywraca
         * System.out, porównuje przechwyconą zawartość z expectedOutput
         * i wypisuje "PASS" albo "FAIL". Przetestuj z poprawnym
         * i niepoprawnym expectedOutput.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RotatingLogFiles {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prosty mechanizm rotacji logów: jeśli plik
         * "app24.log" przekroczy określony rozmiar (np. 200 bajtów),
         * przenieś go (renameTo) do "app24.log.old" i zacznij zapis do
         * nowego, pustego "app24.log". Przetestuj, dopisując wiele linii
         * w pętli, aż nastąpi rotacja – wypisz moment, w którym do niej
         * doszło.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ProgressBarSimulation {
        /*
         * 🧪 Zadanie 25:
         * Używając print() (bez println) i znaku powrotu karetki '\r'
         * na System.out, zasymuluj prosty pasek postępu 0%-100% w pętli,
         * aktualizowany w tej samej linii konsoli (np. co 10%, z krótkim
         * opóźnieniem Thread.sleep()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_StructuredReportWithSections {
        /*
         * 🧪 Zadanie 26:
         * Wygeneruj PrintWriterem wieloczęściowy raport do pliku
         * "raport26.txt": sekcja nagłówka, sekcja danych (tabela
         * produktów: nazwa, cena), sekcja podsumowania statystycznego
         * (min/max/średnia cen) – wszystko w jednym pliku, z czytelnym
         * formatowaniem printf i separatorami linii między sekcjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExceptionSafeLogging {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj metodę safeLog(String msg), która używa
         * PrintWriter z checkError() zamiast wyjątków – po każdym zapisie
         * sprawdza checkError() i jeśli true, przełącza się na
         * zapisywanie kolejnych wiadomości do konsoli (System.out)
         * zamiast pliku. Zasymuluj błąd zapisu (np. przez zamknięcie
         * strumienia przed kolejnym zapisem) i sprawdź, czy przełączenie
         * działa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareBufferedVsAutoFlushPerformance {
        /*
         * 🧪 Zadanie 28:
         * Zmierz czas zapisu 100 000 linii do pliku PrintWriterem
         * z autoFlush=true vs autoFlush=false (używając
         * System.nanoTime()). Wypisz różnicę czasu i krótkie wyjaśnienie
         * w komentarzu, dlaczego autoFlush=true jest wolniejsze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiStreamBroadcast {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj metodę broadcast(String message, PrintStream...
         * streams), która wypisuje tę samą wiadomość do dowolnej liczby
         * przekazanych PrintStreamów (np. System.out i strumień zapisujący
         * do pliku "broadcast29.txt") w jednej pętli. Przetestuj
         * wywołaniem z dwoma strumieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullConsoleCaptureAndRestore {
        /*
         * 🧪 Zadanie 30:
         * Napisz kompletny mechanizm przechwytywania CAŁEGO wyjścia
         * konsoli (System.out i System.err jednocześnie) na czas
         * wykonania bloku kodu, zapisujący oba strumienie do osobnych
         * plików ("out30.txt", "err30.txt"), z gwarantowanym przywróceniem
         * oryginalnych strumieni nawet w przypadku wyjątku (try/finally).
         * Przetestuj, celowo rzucając wyjątek w środku przechwytywanego
         * bloku i sprawdzając, że System.out/System.err zostały mimo to
         * przywrócone.
         */
        public static void main(String[] args) { }
    }
}
