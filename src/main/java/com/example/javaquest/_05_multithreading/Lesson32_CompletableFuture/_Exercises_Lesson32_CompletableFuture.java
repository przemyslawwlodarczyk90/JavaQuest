package com.example.javaquest._05_multithreading.Lesson32_CompletableFuture;

public class _Exercises_Lesson32_CompletableFuture {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SupplyAsyncBasics {
        /*
         * 🧪 Zadanie 1:
         * Użyj CompletableFuture.supplyAsync(), aby asynchronicznie obliczyć kwadrat
         * liczby 7. Wewnątrz supplera wypisz nazwę bieżącego wątku, a wynik odbierz
         * przez get() i wypisz w wątku main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ThenApplyChain {
        /*
         * 🧪 Zadanie 2:
         * Utwórz CompletableFuture.supplyAsync() zwracający liczbę 10, a następnie
         * użyj thenApply() aby ją podwoić. Wypisz wynik końcowy przez get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ThenAcceptPrints {
        /*
         * 🧪 Zadanie 3:
         * Utwórz CompletableFuture.supplyAsync() zwracający String "hello", a następnie
         * thenAccept() wypisujący ten napis wielkimi literami. Poczekaj na zakończenie
         * całego łańcucha metodą join() na Void wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExceptionallyFallback {
        /*
         * 🧪 Zadanie 4:
         * Utwórz supplyAsync(), które próbuje sparsować String "abc" na int (Integer.parseInt),
         * co rzuci NumberFormatException. Dodaj exceptionally() zwracające wartość zastępczą -1.
         * Wypisz wynik przez get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ThenCombineRectangle {
        /*
         * 🧪 Zadanie 5:
         * Dla prostokąta o bokach 3 i 4 policz asynchronicznie pole (jeden supplyAsync)
         * i obwód (drugi supplyAsync), a następnie połącz oba wyniki metodą thenCombine()
         * w jeden String "pole=..., obwód=...". Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ThenComposeUserOrders {
        /*
         * 🧪 Zadanie 6:
         * Utwórz supplyAsync() zwracający userId=42, a następnie thenCompose() wywołujące
         * kolejny supplyAsync(), który "pobiera liczbę zamówień" dla tego userId (np. userId * 3).
         * Wypisz końcowy wynik jednym get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RunAsyncSideEffect {
        /*
         * 🧪 Zadanie 7:
         * Użyj CompletableFuture.runAsync() do wykonania zadania bez wyniku, które wypisuje
         * "Zadanie wykonane w tle". Poczekaj na jego zakończenie metodą join().
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GetWithTimeoutOk {
        /*
         * 🧪 Zadanie 8:
         * Utwórz supplyAsync() usypiające wątek na 100 ms i zwracające napis "gotowe".
         * Odbierz wynik przez get(1, TimeUnit.SECONDS) i wypisz go, pokazując że mieści
         * się w limicie czasu bez wyjątku TimeoutException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DoubleThenApplyChain {
        /*
         * 🧪 Zadanie 9:
         * Zaczynając od supplyAsync() zwracającego liczbę 5, zbuduj łańcuch dwóch thenApply():
         * pierwszy dodaje 10, drugi mnoży przez 2. Wypisz wynik końcowy (powinno być 30).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ApplyVsComposeNesting {
        /*
         * 🧪 Zadanie 10:
         * Analogicznie do przykładu z lekcji: mając supplyAsync() zwracający 5 oraz metodę
         * asyncTriple(int) zwracającą CompletableFuture<Integer> (wartość * 3), porównaj
         * użycie thenApply (zagnieżdżony CompletableFuture<CompletableFuture<Integer>>,
         * wymaga 2x get()) z thenCompose (spłaszczony wynik, 1x get()). Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TemperaturePipelineWithFallback {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj łańcuch: supplyAsync() "pobiera" surowy napis temperatury (np. "25C"),
         * thenApply() parsuje liczbę i konwertuje na Fahrenheity, thenApply() formatuje
         * wynik, exceptionally() łapie błąd dla niepoprawnego wejścia (np. "abcC") i zwraca
         * komunikat zastępczy. Uruchom łańcuch dla poprawnego i niepoprawnego wejścia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ThenCombineThreePrices {
        /*
         * 🧪 Zadanie 12:
         * Utwórz 3 niezależne supplyAsync() zwracające ceny trzech produktów (10.0, 25.5, 7.25).
         * Połącz je (np. dwoma zagnieżdżonymi thenCombine()) w jedną sumę końcową i wypisz
         * łączną kwotę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ComposeChainWithFakeDb {
        /*
         * 🧪 Zadanie 13:
         * Mając Map<Integer,String> symulującą "bazę użytkowników" oraz Map<String,Double>
         * symulującą "sumę koszyka" po nazwie użytkownika, zbuduj thenCompose() łączący dwa
         * zależne od siebie asynchroniczne zapytania: najpierw pobierz nazwę po id, potem
         * sumę koszyka po nazwie. Wypisz wynik końcowy dla wybranego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RecoveryForMultipleInputs {
        /*
         * 🧪 Zadanie 14:
         * Dla listy napisów {"10", "20", "abc", "30"} zbuduj dla każdego łańcuch
         * supplyAsync -> thenApply (parseInt) -> exceptionally (fallback 0), a na końcu
         * thenAccept wypisujący wynik. Uruchom dla wszystkich czterech wejść i sprawdź,
         * że "abc" nie wysadza programu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AllOfCollectResults {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tablicę 5 CompletableFuture<Integer>, z których każdy liczy kwadrat liczb
         * 1-5. Użyj CompletableFuture.allOf() do poczekania na wszystkie, a następnie zbierz
         * wyniki z każdego futures[i].join() do listy i wypisz ich sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AnyOfFirstFinished {
        /*
         * 🧪 Zadanie 16:
         * Utwórz dwa supplyAsync() symulujące "dwa źródła danych" o różnym czasie odpowiedzi
         * (np. sleep 50 ms i sleep 200 ms), zwracające różne napisy. Użyj CompletableFuture.anyOf()
         * i wypisz, które źródło odpowiedziało jako pierwsze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ParallelVsSequentialTiming {
        /*
         * 🧪 Zadanie 17:
         * Zmierz czas wykonania dwóch supplyAsync() (każdy śpi 100 ms) połączonych
         * thenCombine() (powinno trwać ~100 ms, bo liczą się równolegle) oraz czas
         * sekwencyjnego wywołania dwóch takich samych operacji jedna po drugiej (~200 ms).
         * Wypisz oba czasy obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FetchAndFormatUtility {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę fetchAndFormat(int id) zwracającą CompletableFuture<String>, łączącą
         * supplyAsync (symulacja pobrania danych po id), thenApply (formatowanie) oraz
         * exceptionally (fallback dla id ujemnego, traktowanego jako błąd). Wywołaj ją dla
         * kilku id, w tym jednego ujemnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TimeoutException {
        /*
         * 🧪 Zadanie 19:
         * Utwórz supplyAsync() usypiające wątek na 3 sekundy. Wywołaj get(500, TimeUnit.MILLISECONDS)
         * w bloku try/catch, złap TimeoutException i wypisz jego komunikat zamiast czekać
         * na zakończenie zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BatchTemperatureConversion {
        /*
         * 🧪 Zadanie 20:
         * Dla listy temperatur w Celsjuszach {0, 10, 20, 30, -40} utwórz dla każdej osobny
         * supplyAsync -> thenApply konwertujący na Fahrenheity. Zbierz wszystkie
         * CompletableFuture do listy, poczekaj na wszystkie (join) i wypisz sformatowane wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CheckoutPipeline {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj pipeline "checkout" dla 3 klientów: thenCombine łączące cenę i rabat,
         * thenCompose pobierające asynchronicznie kurs waluty zależny od kraju klienta,
         * oraz exceptionally obsługujące symulowany błąd dla jednego z klientów
         * (np. nieznany kraj). Wypisz finalne kwoty dla wszystkich trzech.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RetryWithBackoff {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj zadanie, które sztucznie zawodzi za pierwszymi 2 razami (licznik prób)
         * i udaje się za 3. razem. Zbuduj ręczną logikę ponawiania przez exceptionally()
         * + thenCompose() wywołujące ponownie tę samą operację, maksymalnie 3 próby.
         * Wypisz finalny wynik albo komunikat o porażce po wyczerpaniu prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FanOutFanIn {
        /*
         * 🧪 Zadanie 23:
         * Dla listy 5 symulowanych "adresów URL" utwórz dla każdego supplyAsync zwracający
         * losową "długość odpowiedzi" (po krótkim sleep, np. 20 ms). Użyj allOf() do
         * poczekania na wszystkie, zsumuj długości i wypisz sumę oraz całkowity czas wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WordCountPipeline {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj wieloetapowy pipeline: supplyAsync wczytuje stały tekst (kilka zdań),
         * thenApply dzieli go na słowa, thenApply liczy częstość słów do Map<String,Integer>,
         * thenAccept wypisuje 3 najczęstsze słowa. Dodaj exceptionally jako zabezpieczenie
         * na wypadek pustego tekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ParallelSumMeasured {
        /*
         * 🧪 Zadanie 25:
         * Utwórz 5 niezależnych supplyAsync (każdy śpi ok. 50 ms i zwraca liczbę), połącz je
         * łańcuchem thenCombine (parami, redukując do jednej sumy końcowej). Zmierz całkowity
         * czas wykonania i wypisz go razem z sumą, potwierdzając że zadania działały równolegle
         * (czas bliski 50 ms, a nie sumie wszystkich opóźnień).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TimeoutWithFallbackPerService {
        /*
         * 🧪 Zadanie 26:
         * Dla 4 symulowanych "usług" o różnych czasach odpowiedzi (50, 100, 300, 150 ms)
         * użyj orTimeout(Duration) z progiem 200 ms oraz exceptionally() zwracającego wartość
         * zastępczą dla usług, które przekroczyły limit. Wypisz tabelę wyników dla wszystkich
         * czterech usług.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_OrderProcessingScenario {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj scenariusz "przetwarzania zamówienia": supplyAsync pobiera zamówienie,
         * thenCompose asynchronicznie waliduje je (odrzuca zamówienia z ujemną ilością),
         * thenCombine łączy wynik z asynchronicznie pobranym kosztem wysyłki, thenApply
         * formatuje podsumowanie, exceptionally obsługuje błąd walidacji. Uruchom dla
         * 3 zamówień: poprawnego, z ujemną ilością i z nieznanym produktem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GenericMapAsyncUtility {
        /*
         * 🧪 Zadanie 28:
         * Napisz generyczną metodę <T,R> CompletableFuture<List<R>> mapAsync(List<T> items,
         * Function<T,R> mapper), tworzącą po jednym CompletableFuture na element i łączącą
         * je przez allOf() oraz zbierającą wyniki do listy. Przetestuj ją na liście liczb
         * 1-5, obliczając ich kwadraty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SequentialVsAsyncBenchmark {
        /*
         * 🧪 Zadanie 29:
         * Policz kwadraty liczb 1-20 na dwa sposoby: sekwencyjnie w zwykłej pętli oraz
         * asynchronicznie przez supplyAsync per liczba połączone allOf(). Zmierz i wypisz
         * oba czasy wykonania, porównując je w komentarzu/println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ResilientBatchParsing {
        /*
         * 🧪 Zadanie 30:
         * Dla listy 6 napisów (część poprawnych liczb jak "10","20", część niepoprawnych
         * jak "abc") zbuduj dla każdego łańcuch: thenApply parsujący liczbę, exceptionally
         * podstawiający 0 przy błędzie. Połącz wszystkie przez allOf(), zsumuj wyniki
         * przez thenApply po zakończeniu wszystkich, i wypisz sumę końcową oraz raport,
         * które wejścia się nie powiodły.
         */
        public static void main(String[] args) { }
    }
}
