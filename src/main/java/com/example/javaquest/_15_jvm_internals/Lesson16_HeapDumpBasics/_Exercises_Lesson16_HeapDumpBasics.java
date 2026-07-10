package com.example.javaquest._15_jvm_internals.Lesson16_HeapDumpBasics;

public class _Exercises_Lesson16_HeapDumpBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PrintHotSpotDiagnosticMXBeanClassName {
        /*
         * 🧪 Zadanie 1:
         * Pobierz HotSpotDiagnosticMXBean przez
         * ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class)
         * i wypisz nazwę jego klasy (getClass().getName()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_CreateTempDirectoryForDumps {
        /*
         * 🧪 Zadanie 2:
         * Utwórz tymczasowy katalog przez Files.createTempDirectory("cwiczenie02")
         * i wypisz jego pełną ścieżkę (toAbsolutePath()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_GenerateBasicLiveHeapDump {
        /*
         * 🧪 Zadanie 3:
         * W tymczasowym katalogu wygeneruj plik "cwiczenie03.hprof" przez
         * dumpHeap(sciezka, true) (liveOnly). Wypisz, czy plik istnieje i jaki
         * ma rozmiar w bajtach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_GenerateFullHeapDump {
        /*
         * 🧪 Zadanie 4:
         * Wygeneruj heap dump z liveOnly=false ("cwiczenie04.hprof") i wypisz
         * jego rozmiar. Porównaj (println) z rozmiarem z Zadania 3 – który jest
         * większy i dlaczego (dump pełny może zawierać jeszcze nieposprzątane
         * śmieci).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_DumpToExistingFileThrowsIOException {
        /*
         * 🧪 Zadanie 5:
         * Spróbuj wygenerować heap dump DWA razy do TEJ SAMEJ ścieżki pliku.
         * Złap IOException przy drugiej próbie i wypisz jego komunikat –
         * dumpHeap wymaga, by plik docelowy jeszcze NIE istniał.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_PrintHeapUsedBeforeDump {
        /*
         * 🧪 Zadanie 6:
         * Przez MemoryMXBean.getHeapMemoryUsage().getUsed() wypisz, ile bajtów
         * sterty jest używane BEZPOŚREDNIO PRZED wygenerowaniem heap dumpu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_AllocateListThenDump {
        /*
         * 🧪 Zadanie 7:
         * Zaalokuj listę 100 000 obiektów String (np. "element-" + i), a potem
         * wygeneruj heap dump (liveOnly=true). Zweryfikuj, że plik powstał i ma
         * rozmiar > 0.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_UniqueFileNameWithTimestamp {
        /*
         * 🧪 Zadanie 8:
         * Wygeneruj nazwę pliku dumpu zawierającą aktualny znacznik czasu (np.
         * "heap-" + System.currentTimeMillis() + ".hprof"), żeby uniknąć
         * kolizji z Zadania 5. Wykonaj dump do tej ścieżki i potwierdź sukces.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ListToolsForOpeningHprof {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu wypisz (jako listę) 3 narzędzia GUI do
         * otwierania plików .hprof wymienione w lekcji oraz jednym zdaniem,
         * do czego służy każde z nich.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_CompareJmapAndJcmdCommands {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu zapisz dokładną komendę `jmap` oraz
         * dokładną komendę `jcmd` do wygenerowania heap dumpu procesu o PID
         * 12345 do pliku "app.hprof", i wyjaśnij jedną różnicę między nimi.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DumpSizeGrowsWithMoreObjects {
        /*
         * 🧪 Zadanie 11:
         * Wykonaj heap dump przy 10 000 zaalokowanych obiektach, a potem drugi
         * dump (inny plik) przy 200 000 obiektach. Wypisz oba rozmiary i
         * zweryfikuj (asercja/println), że drugi dump jest wyraźnie większy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_ForceGcBeforeLiveDump {
        /*
         * 🧪 Zadanie 12:
         * Zaalokuj dużo tymczasowych obiektów, usuń do nich referencje
         * (ustaw na null), wywołaj System.gc(), a potem zrób heap dump
         * (liveOnly=true). Wypisz rozmiar heap PRZED i PO System.gc() przez
         * MemoryMXBean, żeby zobaczyć wpływ GC.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_SimulatedLeakGrowingList {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj statyczną listę, która w pętli 5 rund dodaje po 20 000
         * elementów (symulacja wycieku), i po KAŻDEJ rundzie wypisz aktualny
         * rozmiar listy oraz used heap (MemoryMXBean) – bez zwalniania
         * referencji między rundami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_DumpAtEachLeakStage {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz Zadanie 13: po każdej z 5 rund wykonaj OSOBNY heap dump
         * (dump-runda-1.hprof ... dump-runda-5.hprof) i wypisz rozmiar pliku
         * dla każdej rundy – tak, jakbyś budował "serię zrzutów" do
         * porównania w Eclipse MAT.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_HelperMethodDumpHeapTo {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę pomocniczą `Path dumpHeapTo(String fileName, boolean
         * liveOnly)`, która tworzy katalog tymczasowy (jeśli trzeba), robi
         * dump i zwraca ścieżkę do pliku. Użyj jej co najmniej 3 razy z
         * różnymi nazwami plików.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_CatchIOExceptionGracefully {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę `boolean tryDumpHeap(Path path, boolean liveOnly)`,
         * która zwraca true przy sukcesie i false (łapiąc IOException) przy
         * porażce – bez rzucania wyjątku dalej. Przetestuj ją raz z poprawną
         * ścieżką i raz z już istniejącym plikiem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CompareLiveVsFullOnSameState {
        /*
         * 🧪 Zadanie 17:
         * Dla DOKŁADNIE tego samego stanu sterty (bez alokacji między
         * wywołaniami) zrób jeden dump z liveOnly=true i jeden z
         * liveOnly=false do dwóch różnych plików. Wypisz oba rozmiary i
         * skomentuj (println) obserwację.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_MemoryPoolSnapshotAlongsideDump {
        /*
         * 🧪 Zadanie 18:
         * Przed wykonaniem heap dumpu wypisz (przez
         * ManagementFactory.getMemoryPoolMXBeans(), nawiązanie do Lesson06)
         * użycie WSZYSTKICH pul pamięci, a potem wykonaj heap dump i wypisz
         * jego rozmiar – połącz oba źródła informacji w jednym raporcie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_DumpFileNamingConvention {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj i zaimplementuj spójną konwencję nazewnictwa plików
         * dumpów: "<nazwaAplikacji>-<znacznikCzasu>-<liveOnly|full>.hprof".
         * Wygeneruj 2 przykładowe pliki zgodnie z tą konwencją i wypisz ich
         * nazwy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ExplainDominatorTreeOnPaper {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zamodeluj (w komentarzu, jako prosty tekstowy graf)
         * 5 obiektów A-B-C-D-E, gdzie A trzyma referencje do B i C, a B trzyma
         * referencję do D. Wskaż, który obiekt jest "dominatorem" dla D i
         * uzasadnij, korzystając z definicji z lekcji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RetainedSizeCalculatorSimpleGraph {
        /*
         * 🧪 Zadanie 21:
         * Zamodeluj mini-graf obiektów jako Map<String, List<String>> (kto
         * trzyma referencję do kogo). Napisz metodę obliczającą "retained
         * set" danego węzła – zbiór węzłów osiągalnych WYŁĄCZNIE przez ten
         * węzeł (a nie przez inną ścieżkę z korzenia). Przetestuj na grafie z
         * co najmniej 6 węzłami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_LeakSuspectHeuristic {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj prostą heurystykę "podejrzenia wycieku": monitoruj
         * used heap (MemoryMXBean) w 5 pomiarach co 200ms podczas ciągłej
         * alokacji bez zwalniania referencji; jeśli used ROŚNIE w KAŻDYM z 5
         * pomiarów z rzędu, wypisz "podejrzenie wycieku – rozważ heap dump" i
         * automatycznie wykonaj dump.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_ThresholdTriggeredAutoDump {
        /*
         * 🧪 Zadanie 23:
         * Napisz mechanizm, który co 100ms sprawdza used heap i AUTOMATYCZNIE
         * wykonuje heap dump w momencie, gdy used przekroczy zadany próg
         * (podany jako parametr, np. aktualny used + 5 MB). Ogranicz
         * monitorowanie do maksymalnie 3 sekund (bounded pętla).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_MultipleDumpsTimeSeriesReport {
        /*
         * 🧪 Zadanie 24:
         * Wykonaj serię 4 heap dumpów w regularnych odstępach (co ok. 300ms),
         * z narastającą alokacją pomiędzy nimi. Zbierz rozmiary wszystkich 4
         * plików do listy i wypisz raport w formie tabeli tekstowej: numer
         * dumpu, rozmiar w MB, przyrost względem poprzedniego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_CombineHeapDumpWithThreadSnapshot {
        /*
         * 🧪 Zadanie 25:
         * Przed wykonaniem heap dumpu wypisz też liczbę aktywnych wątków
         * (Thread.activeCount() lub ThreadMXBean.getThreadCount(), nawiązanie
         * do Lesson17) – zbuduj "mini raport diagnostyczny" łączący oba
         * źródła w jednym wywołaniu metody.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_SimulateOomTriggeredDumpFlag {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: w komentarzu napisz PEŁNĄ komendę uruchomienia JVM z
         * flagami -Xmx64m -XX:+HeapDumpOnOutOfMemoryError
         * -XX:HeapDumpPath=... dla przykładowej aplikacji "app.jar", i
         * wyjaśnij, kiedy dokładnie JVM sama wygeneruje plik .hprof.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_CompareDumpSizeAcrossGcRuns {
        /*
         * 🧪 Zadanie 27:
         * Zaalokuj i porzuć (bez referencji) 500 000 obiektów, wykonaj dump
         * (liveOnly=true) BEZ wywołania System.gc(), następnie wywołaj
         * System.gc() i wykonaj drugi dump (liveOnly=true). Porównaj rozmiary
         * i wyjaśnij różnicę (println) – dumpHeap z liveOnly=true SAM
         * wykonuje pełny GC przed zrzutem, więc oczekiwana różnica może być
         * mniejsza niż się wydaje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_DumpAnalysisReportGenerator {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę `String buildDumpReport(Path dumpFile)`, która zwraca
         * sformatowany tekstowy raport: ścieżka, rozmiar w MB, znacznik czasu
         * utworzenia pliku (Files.getLastModifiedTime). Wygeneruj 2 dumpy i
         * wypisz raporty dla obu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_CleanupOldDumpsKeepLastN {
        /*
         * 🧪 Zadanie 29:
         * Wygeneruj 6 plików dumpów w jednym katalogu tymczasowym, a
         * następnie napisz metodę, która zostawia tylko 3 NAJNOWSZE pliki
         * (wg czasu modyfikacji) i usuwa resztę. Wypisz listę plików przed i
         * po czyszczeniu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullDiagnosticSnapshotCapstone {
        /*
         * 🧪 Zadanie 30:
         * Połącz wszystko z tej lekcji w jedną metodę `runFullSnapshot()`:
         * (1) wypisz used heap PRZED, (2) zaalokuj 200 000 obiektów, (3)
         * wypisz użycie wszystkich pul pamięci (Lesson06), (4) wykonaj heap
         * dump (liveOnly=true) i zweryfikuj plik, (5) wypisz used heap PO, (6)
         * wypisz jednozdaniowe podsumowanie co się zmieniło. Uruchom tę
         * metodę i zweryfikuj kompletność raportu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
