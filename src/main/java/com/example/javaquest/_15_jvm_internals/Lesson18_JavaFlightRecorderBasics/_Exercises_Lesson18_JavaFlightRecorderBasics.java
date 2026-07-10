package com.example.javaquest._15_jvm_internals.Lesson18_JavaFlightRecorderBasics;

public class _Exercises_Lesson18_JavaFlightRecorderBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateAndCloseEmptyRecording {
        /*
         * 🧪 Zadanie 1:
         * Utwórz obiekt jdk.jfr.Recording w try-with-resources, wypisz jego
         * nazwę domyślną (getName()), i zamknij go bez włączania nagrywania.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_StartAndStopRecordingImmediately {
        /*
         * 🧪 Zadanie 2:
         * Uruchom Recording (start()), od razu je zatrzymaj (stop()), i
         * wypisz getState() nagrania w obu momentach (przed start, po start,
         * po stop).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_EnableSingleBuiltInEvent {
        /*
         * 🧪 Zadanie 3:
         * Włącz TYLKO zdarzenie "jdk.CPULoad" z okresem 100ms
         * (withPeriod(Duration.ofMillis(100))), nagrywaj przez 500ms
         * (Thread.sleep), zatrzymaj i zrzuć nagranie do pliku
         * "cwiczenie03.jfr" w katalogu tymczasowym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_VerifyDumpFileExistsAndSize {
        /*
         * 🧪 Zadanie 4:
         * Powtórz nagranie z Zadania 3, a następnie zweryfikuj przez
         * Files.exists i Files.size, że plik .jfr powstał i ma rozmiar > 0
         * bajtów. Wypisz obie wartości.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_DefineMinimalCustomEvent {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj własną klasę zdarzenia `SimpleEvent extends
         * jdk.jfr.Event` z jednym polem String message, oznaczoną
         * @Label("Proste zdarzenie"). Nie musisz jej jeszcze commitować -
         * tylko zdefiniuj klasę i wypisz jej nazwę przez getClass().getName().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_CommitCustomEventOnce {
        /*
         * 🧪 Zadanie 6:
         * Włącz zdarzenie SimpleEvent z Zadania 5 w nagraniu, utwórz jego
         * instancję, ustaw pole message="pierwsze zdarzenie" i wywołaj
         * commit(). Zrzuć nagranie do pliku i zweryfikuj jego rozmiar.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_CommitMultipleCustomEventsInLoop {
        /*
         * 🧪 Zadanie 7:
         * W pętli 20 iteracji utwórz i zacommituj 20 instancji SimpleEvent, z
         * message="zdarzenie-" + i. Zrzuć nagranie i wypisz jego rozmiar.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_UseEventBeginAndEnd {
        /*
         * 🧪 Zadanie 8:
         * Zamiast samego commit(), użyj event.begin() PRZED wykonaniem jakiejś
         * krótkiej pracy (np. sleep(200)) i event.commit() PO niej - zbadaj w
         * komentarzu różnicę między begin()/commit() a samym commit().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ExplainJfrVsHeapDumpOnPaper {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu (min. 3 zdania) wyjaśnij różnicę
         * między plikiem .jfr (Lesson18) a plikiem .hprof (Lesson16) -
         * "zdarzenia w czasie" vs "statyczny zrzut w jednym momencie".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_ListEnabledEventNames {
        /*
         * 🧪 Zadanie 10:
         * Włącz 2 wbudowane zdarzenia ("jdk.CPULoad", "jdk.GarbageCollection")
         * i wypisz ich nazwy przez pętlę po recording.getEventSettings()
         * (lub po prostu wypisz literalnie nazwy, które właśnie włączyłeś, z
         * potwierdzeniem że enable() nie rzuciło wyjątku).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EventWithMultipleFieldsAndLabels {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj zdarzenie `OrderEvent` z polami: String orderId, int
         * itemCount, double totalPrice - każde z osobną adnotacją @Label.
         * Wyemituj 5 instancji z różnymi danymi i zrzuć nagranie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_EventWithStackTraceEnabled {
        /*
         * 🧪 Zadanie 12:
         * Włącz OrderEvent z Zadania 11 przez
         * recording.enable(OrderEvent.class).withStackTrace(). Wyemituj
         * zdarzenie z wnętrza zagnieżdżonego wywołania metody (min. 2 poziomy
         * głębokości) i zrzuć nagranie - wyjaśnij (println), po co włącza się
         * stack trace dla zdarzeń.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_CategoryAndDescriptionAnnotations {
        /*
         * 🧪 Zadanie 13:
         * Dodaj do OrderEvent adnotacje @Category({"JavaQuest", "Sklep"}) i
         * @Description z krótkim opisem. Wypisz (przez refleksję lub po
         * prostu println) że adnotacje zostały zastosowane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_WorkloadDuringRecordingOneSecond {
        /*
         * 🧪 Zadanie 14:
         * Uruchom nagranie z włączonym "jdk.GarbageCollection", wykonaj
         * obciążenie alokujące śmieci przez dokładnie 1 sekundę (bounded
         * pętla z System.currentTimeMillis()), zatrzymaj nagranie i zrzuć do
         * pliku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_RunJfrSummaryViaProcessBuilder {
        /*
         * 🧪 Zadanie 15:
         * Po wygenerowaniu pliku .jfr uruchom bundled CLI `jfr summary
         * <plik>` przez ProcessBuilder z timeoutem 10 sekund
         * (process.waitFor(10, TimeUnit.SECONDS)), i wypisz pierwsze 10 linii
         * realnego outputu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_HandleMissingJfrToolGracefully {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj ścieżkę do narzędzia `jfr` przez java.home, ale celowo
         * podaj BŁĘDNĄ nazwę pliku (np. "jfr-nieistniejacy"), złap
         * IOException i wypisz przyjazny komunikat błędu zamiast pozwolić
         * programowi się wysypać.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_MultipleRecordingsSequentially {
        /*
         * 🧪 Zadanie 17:
         * Uruchom 3 KOLEJNE, oddzielne nagrania (każde start-workload-stop-
         * dump) do 3 różnych plików, każde trwające ok. 300ms. Wypisz
         * rozmiary wszystkich 3 plików.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_CompareRecordingSizeWithAndWithoutStackTrace {
        /*
         * 🧪 Zadanie 18:
         * Nagraj to samo obciążenie (emitujące 100 instancji własnego
         * zdarzenia) dwa razy - raz BEZ withStackTrace(), raz Z
         * withStackTrace(). Porównaj (println) rozmiary obu plików .jfr i
         * skomentuj różnicę narzutu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_ThresholdFilteredEvent {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj zdarzenie z polem `long durationMillis` i użyj
         * recording.enable(TwojeZdarzenie.class).withThreshold(Duration.
         * ofMillis(50)) - wyemituj 10 zdarzeń, z których tylko część trwa
         * dłużej niż 50ms (użyj begin()/end()/commit() z realnym sleep).
         * Wyjaśnij (println), po co służy withThreshold.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ExplainJmcWorkflowOnPaper {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: w komentarzu opisz krok po kroku (min. 4 kroki),
         * jak analityk otworzyłby wygenerowany w tej lekcji plik .jfr w JDK
         * Mission Control i czego by tam szukał (np. "Automated Analysis
         * Results", timeline GC, hot methods).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ContinuousRecordingWithPeriodicDump {
        /*
         * 🧪 Zadanie 21:
         * Uruchom JEDNO długie nagranie (2 sekundy) i w trakcie jego
         * trwania (bez zatrzymywania recording) zrób 2 pośrednie zrzuty
         * przez recording.dump(...) do różnych plików (dump "w locie" jest
         * dozwolony bez stop()). Zweryfikuj oba pliki i porównaj ich
         * rozmiary.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_CorrelateCustomEventsWithGcEvents {
        /*
         * 🧪 Zadanie 22:
         * Nagraj jednocześnie własne zdarzenie (np. "PrzetworzonoPartie" z
         * polem batchSize) oraz "jdk.GarbageCollection", podczas obciążenia
         * alokującego dużo śmieci między partiami. Po zrzucie uruchom `jfr
         * summary` i wypisz fragment outputu pokazujący oba typy zdarzeń.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_RecordingWithMaxSizeOrMaxAge {
        /*
         * 🧪 Zadanie 23:
         * Skonfiguruj Recording z setMaxSize(1024L * 1024L) (limit rozmiaru
         * nagrania w pamięci, mechanizm circular buffer), wygeneruj dużo
         * zdarzeń w pętli przez 1 sekundę i zrzuć nagranie - wyjaśnij
         * (println), co się dzieje, gdy limit zostanie przekroczony.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_ParseJfrSummaryOutputForEventCounts {
        /*
         * 🧪 Zadanie 24:
         * Uruchom `jfr summary <plik>` przez ProcessBuilder, przechwyć CAŁY
         * output (bounded liczbą linii, np. 200) i napisz prosty parser,
         * który wyciąga z tekstu liczbę wystąpień zdarzenia
         * "jdk.GarbageCollection" (przeszukaj linie zawierające tę nazwę).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_CompareOverheadWithAndWithoutRecording {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas wykonania tego samego obciążenia (np. 2 000 000
         * iteracji obliczeniowych) DWA razy: raz bez żadnego nagrania JFR,
         * raz z aktywnym nagraniem (kilka włączonych zdarzeń). Wypisz oba
         * czasy w milisekundach i procentową różnicę - zilustruj empirycznie
         * "niski narzut" JFR z lekcji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_StartRecordingFromJvmFlagExplanation {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: w komentarzu zapisz PEŁNĄ flagę uruchomieniową JVM
         * -XX:StartFlightRecording=... z parametrami: duration=60s,
         * filename=start.jfr, settings=profile. Wyjaśnij różnicę między
         * settings=profile a settings=default.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_EventFilteringByCustomAttribute {
        /*
         * 🧪 Zadanie 27:
         * Wyemituj 50 instancji własnego zdarzenia z polem `String
         * category` przyjmującym losowo jedną z 3 wartości ("A", "B", "C").
         * Po zrzucie nagrania, uruchom `jfr print --events
         * TwojaNazwaZdarzenia <plik>` przez ProcessBuilder (bounded output)
         * i wypisz fragment pokazujący wartości pola category.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_RecordingLifecycleStateMachine {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę, która wypisuje recording.getState() PO KAŻDYM z
         * etapów: utworzenie, enable(), start(), stop(), close() - zbuduj
         * pełną "maszynę stanów" cyklu życia Recording jako tekstowy log.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_CombineJfrWithThreadDumpOnSlowdown {
        /*
         * 🧪 Zadanie 29:
         * Uruchom nagranie JFR obejmujące okres, w którym uruchamiasz też
         * bezpieczny (daemon+bounded) deadlock z Lesson17. Po zatrzymaniu
         * nagrania i zrzuceniu pliku .jfr, DODATKOWO wykonaj
         * findDeadlockedThreads() i wypisz oba źródła diagnostyki obok
         * siebie w jednym raporcie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullJfrCapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny raport capstone: (1) heap used PRZED
         * (MemoryMXBean), (2) nagranie JFR z co najmniej 2 wbudowanymi i 1
         * własnym zdarzeniem, (3) obciążenie 1.5 sekundy, (4) zrzut do
         * pliku + weryfikacja rozmiaru, (5) uruchomienie `jfr summary` i
         * wypisanie pierwszych 15 linii, (6) heap used PO. Wypisz wszystko
         * w jednym spójnym, ponumerowanym raporcie tekstowym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
