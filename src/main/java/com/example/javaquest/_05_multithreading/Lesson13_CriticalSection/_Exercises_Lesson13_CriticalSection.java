package com.example.javaquest._05_multithreading.Lesson13_CriticalSection;

public class _Exercises_Lesson13_CriticalSection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_NarrowVsWideTiming {
        /*
         * 🧪 Zadanie 1:
         * Zaimplementuj dwie wersje pętli 4 wątków x 20_000 iteracji: (a) synchronized
         * obejmuje TYLKO `counter++`, (b) synchronized obejmuje DODATKOWO sztuczne,
         * niekrytyczne obliczenie (np. pętla licząca sumę pierwiastków 100 liczb).
         * Zmierz i wypisz oba czasy (join(10_000)) oraz oba wyniki liczników (powinny
         * być identyczne i poprawne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_IdentifyCriticalSectionInSnippet {
        /*
         * 🧪 Zadanie 2:
         * Mając opisany (w komentarzu) fragment kodu wątku: (a) odczyt danych wejściowych,
         * (b) lokalne obliczenie niezależne od stanu dzielonego, (c) aktualizacja
         * współdzielonego `total`, zaimplementuj go tak, by synchronized obejmował
         * WYŁĄCZNIE krok (c). Uruchom 4 wątki (join(5_000)) i zweryfikuj poprawność total.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SharedTotalIndependentComputationFirst {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj klasę SharedTotal z synchronized metodą add(double). Uruchom 4 wątki,
         * z których każdy NAJPIERW oblicza (lokalnie, kosztowna pętla) wartość do dodania,
         * a DOPIERO POTEM woła add() (join(10_000)). Zweryfikuj total wobec oczekiwanej sumy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RewriteLesson11WideDemoNarrow {
        /*
         * 🧪 Zadanie 4:
         * Weź demo "wide synchronized" z Lesson11 (synchronized wokół increment() oraz
         * niezwiązanego Thread.sleep/obliczenia). Przepisz je na wersję wąską (synchronized
         * tylko wokół increment()) i zmierz różnicę czasu dla identycznego obciążenia
         * (4 wątki, join(10_000)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NarrowVsWideStaticMethodsSameWorkload {
        /*
         * 🧪 Zadanie 5:
         * Napisz dwie statyczne metody narrowCriticalSection() i wideCriticalSection()
         * wykonujące IDENTYCZNE obciążenie (N wątków, M iteracji, sztuczny koszt na
         * iterację), różniące się TYLKO zakresem synchronized. Wypisz oba czasy
         * i poprawność obu wyników (join(10_000)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SharedLogAppendOnlyCriticalSection {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj klasę SharedLog z metodą addEntry(String) – budowanie treści wpisu
         * (String.format z lokalnymi danymi wątku) odbywa się POZA synchronized, a tylko
         * dopisanie do współdzielonej List<String> jest w synchronized bloku. Uruchom
         * 4 wątki dodające po 10_000 wpisów (join(10_000)) i zweryfikuj finalny rozmiar listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SleepOutsideVsInsideSynchronized {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj metodę z synchronized obejmującym CAŁĄ zawartość razem
         * z Thread.sleep(20) (symulacja "I/O") oraz drugą wersję, gdzie sleep jest POZA
         * synchronized. Uruchom obie z 4 wątkami x 20 iteracji (join(10_000)) i porównaj
         * zmierzone czasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RefactorBadSnippetToNarrow {
        /*
         * 🧪 Zadanie 8:
         * Mając "zły" fragment (opisany w komentarzu) synchronizujący CAŁĄ pętlę wątku
         * łącznie z lokalnym obliczeniem, przepisz go tak, by synchronized obejmował
         * TYLKO aktualizację stanu dzielonego. Uruchom 4 wątki (join(10_000)) i potwierdź,
         * że wynik obu wersji (przed i po refaktoryzacji) jest identyczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SharedMaxTrackerNarrowCompareAndSet {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj SharedMaxTracker z synchronized metodą updateIfGreater(double candidate)
         * zawierającą TYLKO porównanie i przypisanie (bez kosztownego wyliczania candidate
         * wewnątrz synchronized). Podaj ustaloną (seed) tablicę wartości rozdzieloną
         * między 4 wątki (join(10_000)) i zweryfikuj finalne max wobec wartości
         * obliczonej sekwencyjnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MicroBenchmarkWideVsNarrowConclusion {
        /*
         * 🧪 Zadanie 10:
         * Zmierz czasy dla identycznego obciążenia licznika (4 wątki x 20_000 iteracji,
         * sztuczny koszt niekrytyczny 100 operacji per iteracja) w wersji szerokiej
         * i wąskiej synchronized. Wypisz oba czasy i krótki wniosek (print) – wersja
         * wąska powinna być szybsza lub co najmniej nie wolniejsza, a wynik zawsze poprawny.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WordCounterLocalSplitSharedMerge {
        /*
         * 🧪 Zadanie 11:
         * Podziel ustalony, dłuższy tekst na 4 fragmenty (po jednym per wątek). Każdy
         * wątek dzieli SWÓJ fragment na słowa (praca lokalna, poza synchronized)
         * i dopiero zliczone lokalnie wystąpienia scala do współdzielonej
         * Map<String,Integer> wewnątrz MINIMALNEGO synchronized bloku (join(10_000)).
         * Zweryfikuj wynik wobec zliczenia sekwencyjnego całego tekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LeaderboardMinimalSynchronizedUpdate {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj współdzieloną Map<String,Integer> scores. Obliczenie nowego wyniku
         * gracza (ustalony wzór, np. bonus = losowa wartość z seeda) odbywa się POZA
         * synchronized, a tylko get+put do mapy jest w minimalnym synchronized bloku.
         * Uruchom kilka wątków aktualizujących ustalony zestaw graczy (join(10_000))
         * i zweryfikuj finalne wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_BatchProcessingWideVsNarrowStats {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj dwie wersje symulacji przetwarzania wsadowego (4 wątki, ustalona liczba
         * "rekordów" każdy, sztuczny koszt per rekord) aktualizujące wspólny obiekt
         * statystyk (count/sum): wersja A z synchronized wokół CAŁEJ iteracji pętli,
         * wersja B z synchronized tylko wokół aktualizacji statystyk. Zmierz czasy obu
         * i zweryfikuj identyczną poprawność wyników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SharedCacheExpensiveMissOutsideLock {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj SharedCache<String,Integer> z synchronized metodami get/put. Symuluj
         * "kosztowne" obliczenie brakującej wartości (Thread.sleep(10)) POZA synchronized,
         * a samo zapisanie do mapy w minimalnym bloku. Uruchom kilka wątków żądających
         * ustalonego zestawu kluczy (część powtarzających się, join(10_000)) i zweryfikuj
         * poprawność zawartości cache po zakończeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TransactionLogMinimalAppendSection {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj scenariusz logowania transakcji: budowa treści wpisu (String.format
         * z kilkoma polami – niekrytyczne) POZA synchronized, dopisanie do współdzielonej
         * List<String> log w minimalnym synchronized bloku. Uruchom 4 wątki logujące
         * ustaloną liczbę transakcji każdy (join(10_000)) i zweryfikuj finalny rozmiar
         * logu oraz brak utraconych wpisów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_HashComputationOutsideCounterInside {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę obliczającą sztuczny "hash" danych wejściowych (kosztowna,
         * niezależna od stanu dzielonego pętla) POZA synchronized, a tylko inkrementację
         * współdzielonego `processedCount` wewnątrz synchronized. Zmierz czas dla 4 wątków
         * i porównaj z wersją (dla kontrastu), gdzie całe obliczenie hash też jest
         * wewnątrz synchronized (join(10_000)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SharedHistogramIndexComputedOutside {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj SharedHistogram (int[] buckets chronione jednym lockiem). Obliczenie
         * indeksu bucketu na podstawie surowej wartości (jakiś wzór) odbywa się POZA
         * synchronized, a tylko `buckets[index]++` wewnątrz. Podaj ustaloną (seed)
         * tablicę losowych wartości rozdzieloną między 4 wątki (join(10_000)) i zweryfikuj
         * histogram wobec obliczenia sekwencyjnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_VaryingNonCriticalCostTable {
        /*
         * 🧪 Zadanie 18:
         * Dla sztucznego kosztu niekrytycznego o rozmiarach 50, 200, 800 (iteracji
         * wewnętrznej pętli), zmierz czas wersji szerokiej i wąskiej synchronized dla
         * tego samego licznika (4 wątki x 5_000 iteracji, join(10_000) każda kombinacja).
         * Wypisz tabelę: koszt | czas szeroki | czas wąski, pokazując rosnącą różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SharedAccumulatorCallerPreprocesses {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj SharedAccumulator z synchronized metodą accumulate(double) zawierającą
         * TYLKO `total += value;`. Wywołujący (4 wątki) SAMODZIELNIE przetwarza (ustalonym
         * wzorem) swoją listę surowych wartości PRZED wywołaniem accumulate (join(10_000)).
         * Zweryfikuj finalny total wobec oczekiwanej sumy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_LocalPartialCounterFinalMergeVariant {
        /*
         * 🧪 Zadanie 20:
         * Zainspirowany metodami z lekcji (runWideSynchronizedDemo/runNarrowSynchronizedDemo),
         * zbuduj TRZECI wariant: każdy z 4 wątków liczy WŁASNY lokalny licznik częściowy
         * (bez żadnej synchronizacji podczas pętli) i dopiero PO zakończeniu pętli scala
         * wynik do wspólnego total jednym synchronized wywołaniem. Zmierz czas i porównaj
         * z dwoma wariantami z lekcji (join(10_000) dla wszystkich).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BenchmarkHarnessThreeStrategiesTable {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj record BenchmarkResult(String name, long millis, long counterValue).
         * Uruchom dla IDENTYCZNYCH parametrów (4 wątki, 20_000 iteracji, ustalony koszt
         * niekrytyczny) 3 strategie: szeroki synchronized, wąski synchronized, lokalny
         * licznik + scalenie na końcu. Zbierz wyniki do List<BenchmarkResult>, wypisz
         * tabelę posortowaną wg czasu i zweryfikuj poprawność wszystkich trzech wyników
         * (join(15_000) łącznie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_OrderProcessingPairedAggregatesAtomic {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj przetwarzanie zamówień: każdy wątek lokalnie (poza synchronized)
         * oblicza cenę zamówienia z ustalonej tablicy danych, a następnie w JEDNYM
         * synchronized bloku aktualizuje RAZEM dwa powiązane pola (totalRevenue,
         * orderCount) – nigdy osobno. Uruchom 4 wątki (join(10_000)) i zweryfikuj,
         * że totalRevenue/orderCount daje poprawną średnią cenę zamówienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FalseSharingSingleLockTwoCountersRefactor {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj wersję z JEDNYM wspólnym lockiem chroniącym DWA logicznie niezależne
         * liczniki (counterA, counterB) aktualizowane przez 2 grupy wątków. Zmierz czas
         * (join(10_000)), a następnie przerób na DWA osobne locki (po jednym na licznik)
         * i zmierz ponownie – porównaj czasy i skomentuj związek z granularnością blokad
         * z Lesson12.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_StreamingStatsLocalPartialsSingleMerge {
        /*
         * 🧪 Zadanie 24:
         * Wygeneruj ustalony (seed), duży zbiór danych losowych podzielony na 4 fragmenty
         * (po jednym per wątek). Każdy wątek liczy LOKALNIE (bez żadnego locka) częściowe
         * min/max/sum/count dla swojego fragmentu, po czym wykonuje JEDNO synchronized
         * scalenie do wspólnych agregatów (join(10_000)). Zweryfikuj wynik wobec
         * jednowątkowego obliczenia referencyjnego na tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RateLimiterCheckAndDecrementTogether {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj SharedRateBudget(int totalPermits) z JEDNĄ synchronized metodą
         * tryAcquire() sprawdzającą i dekrementującą dostępny budżet RAZEM (jako
         * pojedyncza sekcja krytyczna). Uruchom wiele wątków rywalizujących o ustalony
         * budżet (np. 1000, join(10_000)) i zweryfikuj, że suma przyznanych zezwoleń
         * nigdy nie przekracza budżetu i wyczerpuje się dokładnie do zera przy
         * wystarczającym popycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SplitCheckAndDecrementReintroducesRace {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj "zepsutą" wersję z Zadania 25, gdzie checkBudget() i decrementBudget()
         * to DWIE OSOBNE synchronized metody wywoływane po kolei (nie jedna atomowa
         * operacja). Uruchom ten sam test obciążeniowy (join(10_000)) i wykaż (licząc
         * przyznane zezwolenia), że budżet może zostać PRZEKROCZONY mimo synchronizacji
         * każdej metody z osobna. Napraw, łącząc je w JEDNĄ metodę, i zweryfikuj ponownie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CriticalSectionAuditorProgrammatic {
        /*
         * 🧪 Zadanie 27:
         * Reprezentuj kroki pracy wątku jako listę oznaczonych etykietami Runnable/
         * Supplier (część dotyka stanu dzielonego, część nie – oznacz to w etykiecie).
         * Napisz metodę budującą poprawnie zawężoną wersję synchronized (opakowując
         * TYLKO kroki dotykające stanu dzielonego) i wykonaj ją wieloma wątkami
         * (join(10_000)), weryfikując poprawność wyniku końcowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CpuBoundLocalComputationSingleFinalMerge {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj obliczenie CPU-bound (np. sumę kwadratów) dla 4 rozłącznych
         * podzakresów, każdy liczony lokalnie przez osobny wątek (bez synchronizacji
         * podczas obliczeń), z JEDNYM synchronized scaleniem wyniku skalarnego do total
         * na końcu. Zmierz czas i porównaj z wersją (dla kontrastu) synchronizującą CAŁĄ
         * pętlę obliczeniową każdego wątku (join(15_000) dla obu wariantów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MinimalCriticalSectionForCoupledFields {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj klasę utrzymującą `count` i `avg` (średnia krocząca), gdzie aktualizacja
         * wymaga wzoru `avg = (avg*count + value)/(count+1)` a POTEM `count++` – oba pola
         * MUSZĄ zaktualizować się razem, atomowo. Zaimplementuj to z NAJMNIEJSZYM możliwym
         * synchronized blokiem zachowującym poprawność, uruchom 4 wątki z ustaloną listą
         * wartości (join(10_000)) i zweryfikuj wynik wobec obliczenia sekwencyjnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_LogAnalyzerLocalCountThenMergeCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj "analizator logów": 4 ustalone fragmenty tablicy Stringów (poziomy
         * ważności logów, np. "INFO"/"WARN"/"ERROR") przetwarzane równolegle przez 4 wątki.
         * Każdy wątek zlicza LOKALNIE (własna Map<String,Integer>, bez locka) wystąpienia
         * w swoim fragmencie, po czym wykonuje JEDNO synchronized scalenie do wspólnej
         * mapy totals (join(10_000)). Zmierz czas i zweryfikuj totals wobec referencyjnego
         * obliczenia sekwencyjnego na połączonych danych.
         */
        public static void main(String[] args) { }
    }
}
