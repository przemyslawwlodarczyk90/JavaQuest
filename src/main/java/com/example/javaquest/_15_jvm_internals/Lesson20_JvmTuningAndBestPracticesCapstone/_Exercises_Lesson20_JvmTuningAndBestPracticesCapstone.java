package com.example.javaquest._15_jvm_internals.Lesson20_JvmTuningAndBestPracticesCapstone;

public class _Exercises_Lesson20_JvmTuningAndBestPracticesCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllFlagsFromLesson {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wypisz wszystkie 9 flag JVM
         * wymienionych w tej lekcji razem z jednozdaniowym opisem każdej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_ExplainMeasureFirstPrinciple {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: w komentarzu (min. 3 zdania) wyjaśnij, dlaczego
         * "najpierw zmierz, potem zmieniaj" jest ważniejsze niż intuicja
         * przy strojeniu JVM - podaj przykład błędnej intuicyjnej zmiany.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PrintHeapUsageSnapshot {
        /*
         * 🧪 Zadanie 3:
         * Przez MemoryMXBean (nawiązanie do Lesson06) wypisz jeden snapshot
         * used/committed/max heap.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_StartAndDumpBasicJfrRecording {
        /*
         * 🧪 Zadanie 4:
         * Uruchom proste nagranie JFR (nawiązanie do Lesson18) z jednym
         * wbudowanym zdarzeniem przez 500ms i zrzuć je do pliku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_DumpHeapToFile {
        /*
         * 🧪 Zadanie 5:
         * Wygeneruj pojedynczy heap dump (nawiązanie do Lesson16) do pliku
         * w katalogu tymczasowym i zweryfikuj jego rozmiar.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_RunSafeDeadlockAndDetect {
        /*
         * 🧪 Zadanie 6:
         * Uruchom bezpieczny (daemon + bounded join) deadlock z dwoma
         * blokadami (nawiązanie do Lesson17) i wykryj go przez
         * findDeadlockedThreads().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_CollectOneSamplingProfile {
        /*
         * 🧪 Zadanie 7:
         * Uruchom prosty mini sampling profiler (nawiązanie do Lesson19) na
         * wątku roboczym wykonującym jedną metodę CPU przez 1 sekundę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ExplainOneFlagChangeAtATime {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: opisz (w komentarzu) przykładowy eksperyment
         * strojenia: startowa konfiguracja, JEDNA zmieniana flaga (np.
         * -Xmx), i jak zmierzyłbyś efekt (jakie metryki/narzędzie z lekcji
         * 16-19 by użyć).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_CompareG1AndZgcFlagsOnPaper {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu porównaj (min. 2 zdania na kolektor)
         * -XX:+UseG1GC i -XX:+UseZGC pod kątem długości pauz i typowych
         * zastosowań (nawiązanie do lekcji 8-12 o GC).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_PrintChapterSummaryOneLinePerCluster {
        /*
         * 🧪 Zadanie 10:
         * Wypisz (przez println, jedna linia na klaster) własne
         * podsumowanie 6 klastrów tematycznych rozdziału: classloading,
         * obszary pamięci, GC, JIT, wycieki, diagnostyka - jednym zdaniem
         * każdy, własnymi słowami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BeforeAfterHeapComparisonWithWorkload {
        /*
         * 🧪 Zadanie 11:
         * Zmierz used heap PRZED i PO wykonaniu obciążenia alokującego 200
         * 000 obiektów (bez zwalniania referencji). Wypisz różnicę w MB.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_CombineJfrRecordingWithThreadDump {
        /*
         * 🧪 Zadanie 12:
         * Uruchom nagranie JFR obejmujące okres, w którym wykonujesz też
         * pełny thread dump (ThreadMXBean.dumpAllThreads) - zrzuć oba
         * artefakty (.jfr + wypisany raport tekstowy) i wypisz ich lokalizacje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_HeapDumpBeforeAndAfterGc {
        /*
         * 🧪 Zadanie 13:
         * Wykonaj heap dump PRZED System.gc() i drugi PO System.gc()
         * (oba liveOnly=true), po uprzednim porzuceniu referencji do dużej
         * listy obiektów. Porównaj rozmiary obu plików.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_DetectDeadlockDuringActiveJfrRecording {
        /*
         * 🧪 Zadanie 14:
         * Uruchom nagranie JFR, a W TRAKCIE jego trwania wywołaj bezpieczny
         * deadlock (daemon + bounded join) i wykryj go przez
         * findDeadlockedThreads() ZANIM zatrzymasz nagranie. Zrzuć nagranie
         * na koniec.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_ProfileWorkloadThatAlsoAllocates {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj obciążenie, które JEDNOCZEŚNIE dużo liczy (obciąża CPU) I
         * dużo alokuje (obciąża heap). Sprofiluj je mini sampling
         * profilerem ORAZ zmierz used heap przed/po - połącz oba raporty w
         * jeden.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_SimulateTuningIterationLog {
        /*
         * 🧪 Zadanie 16:
         * Zasymuluj 3 "iteracje strojenia": za każdym razem zmień JEDEN
         * parametr obciążenia (np. rozmiar bufora alokacji: 100, 1000,
         * 10000 elementów) i zmierz used heap PRZED/PO każdej iteracji.
         * Wypisz log iteracji w formacie tabeli tekstowej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CorrelateThreadCountWithHeapUsage {
        /*
         * 🧪 Zadanie 17:
         * Uruchom rosnącą liczbę wątków roboczych (2, 4, 8), każdy
         * alokujący dane, i dla każdej konfiguracji wypisz liczbę wątków
         * (ThreadMXBean) razem z used heap (MemoryMXBean) - zbuduj tabelę
         * korelacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_FullDiagnosticBundleToDirectory {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj JEDEN katalog tymczasowy zawierający: plik .jfr, plik
         * .hprof, oraz plik tekstowy z raportem thread dump - wszystkie z
         * tej samej "sesji diagnostycznej". Wypisz listę wszystkich plików
         * w tym katalogu na koniec.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_ExplainWhichToolForWhichSymptom {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: w komentarzu zbuduj tabelę "objaw -> narzędzie" dla
         * min. 4 objawów (np. "aplikacja się zawiesiła" -> thread dump,
         * "rosnące zużycie pamięci w czasie" -> heap dump + JFR, "wysokie
         * CPU bez wyjaśnienia" -> sampling profiler/JFR, "sporadyczne długie
         * pauzy" -> logi GC/JFR).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_MeasureImpactOfMetaspaceGrowth {
        /*
         * 🧪 Zadanie 20:
         * Wygeneruj i załaduj w runtime kilkanaście dodatkowych klas
         * (nawiązanie do Lesson04_CustomClassLoaders z tego rozdziału) i
         * zmierz non-heap (Metaspace) memory usage PRZED/PO przez
         * MemoryMXBean.getNonHeapMemoryUsage().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCapstoneVariantWithThreeWorkers {
        /*
         * 🧪 Zadanie 21:
         * Rozszerz capstone z lekcji (main demo): zamiast jednego
         * deadlocka, uruchom RÓWNOLEGLE bezpieczny deadlock ORAZ 3 wątki
         * robocze profilowane sampling profilerem, wszystko pod jednym
         * aktywnym nagraniem JFR. Zbuduj jeden spójny raport końcowy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_AutomatedTuningExperimentRunner {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę `runExperiment(String name, Runnable workload)`,
         * która automatycznie: mierzy heap przed, uruchamia workload,
         * mierzy heap po, i zwraca obiekt-raport (rekord) z wynikami.
         * Uruchom ją dla 3 różnych obciążeń i wypisz porównawczą tabelę
         * wyników.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_DiagnosticDecisionTreeSimulator {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj prosty "system decyzyjny": w zależności od
         * zmierzonych wartości (used heap > próg ORAZ liczba wątków >
         * próg ORAZ wykryto deadlock) automatycznie decyduje, KTÓRE z
         * trzech artefaktów (.hprof / .jfr / thread dump) wygenerować, i
         * faktycznie je generuje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_RegressionSuiteAcrossAllDiagnostics {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj "zestaw testów regresyjnych" złożony z 4 sprawdzeń: (1)
         * heap dump da się utworzyć i ma rozmiar > 0, (2) JFR recording da
         * się utworzyć i zrzucić, (3) deadlock jest wykrywany poprawnie,
         * (4) sampling profiler zbiera co najmniej 10 próbek w 1 sekundzie.
         * Wypisz wynik każdego sprawdzenia jako PASS/FAIL.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_CompareTwoWorkloadsFullDiagnosticProfile {
        /*
         * 🧪 Zadanie 25:
         * Uruchom PEŁNY zestaw diagnostyczny (heap przed/po, JFR, sampling
         * profiling) dla DWÓCH różnych obciążeń (jedno "CPU-bound", jedno
         * "memory-bound") i wypisz tabelę porównawczą pokazującą, że
         * diagnostyka faktycznie odzwierciedla charakter każdego z nich.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_SimulateProductionIncidentTimeline {
        /*
         * 🧪 Zadanie 26:
         * Zasymuluj "incydent produkcyjny" jako sekwencję zdarzeń w czasie:
         * normalna praca (500ms) -> nagły wzrost alokacji (500ms) ->
         * deadlock (bezpieczny, daemon) -> powrót do normy. Zbieraj
         * ciągle (co 100ms) used heap i status deadlocka, i na końcu wypisz
         * chronologiczny "dziennik incydentu".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_GenerateHumanReadableIncidentReport {
        /*
         * 🧪 Zadanie 27:
         * Na bazie dziennika z Zadania 26 wygeneruj czytelny dla człowieka
         * raport tekstowy (nie surowe dane) z sekcjami: "Co się stało",
         * "Kiedy", "Jakie artefakty diagnostyczne zebrano i gdzie
         * (ścieżki plików)", "Rekomendacja". Zapisz raport do pliku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_TuningFlagRecommendationEngine {
        /*
         * 🧪 Zadanie 28:
         * Na podstawie zmierzonych metryk (np. used heap blisko max, duża
         * liczba wątków BLOCKED) napisz prostą metodę zwracającą LISTĘ
         * rekomendowanych flag JVM do rozważenia (jako String[], z krótkim
         * uzasadnieniem każdej) - czysto na podstawie reguł if/else, bez
         * uczenia maszynowego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_EndToEndCapstoneWithCleanupPolicy {
        /*
         * 🧪 Zadanie 29:
         * Uruchom pełne demo capstone z lekcji, a na końcu (po wypisaniu
         * wszystkich ścieżek) zaimplementuj POLITYKĘ przechowywania: jeśli
         * w katalogu tymczasowym zbierze się więcej niż 5 plików
         * diagnostycznych z poprzednich uruchomień, usuń najstarsze,
         * zostawiając 5 najnowszych (nawiązanie do Lesson16, Zadanie 29).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_WholeChapterCapstoneProject {
        /*
         * 🧪 Zadanie 30 (projekt podsumowujący CAŁY rozdział):
         * Zbuduj pojedynczą klasę `JvmHealthCheck` z metodą `runFullCheck()`
         * łączącą WSZYSTKIE techniki z lekcji 16-19: (1) heap dump, (2) JFR
         * recording z co najmniej 2 zdarzeniami, (3) wykrycie deadlocka
         * (jeśli wystąpi), (4) sampling profiling wątku roboczego. Metoda ma
         * zwrócić jeden obiekt-raport (rekord lub klasa) zawierający ścieżki
         * do wszystkich wygenerowanych plików oraz krótkie podsumowanie
         * tekstowe stanu JVM. Wywołaj ją i wypisz kompletny raport -
         * to ma być Twoje własne, minimalne narzędzie diagnostyczne
         * spinające cały rozdział _15_jvm_internals.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
