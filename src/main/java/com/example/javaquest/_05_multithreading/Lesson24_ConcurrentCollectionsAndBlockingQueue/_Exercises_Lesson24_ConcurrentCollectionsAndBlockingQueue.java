package com.example.javaquest._05_multithreading.Lesson24_ConcurrentCollectionsAndBlockingQueue;

public class _Exercises_Lesson24_ConcurrentCollectionsAndBlockingQueue {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ConcurrentModificationDemo {
        /*
         * 🧪 Zadanie 1:
         * Utwórz ArrayList<Integer> z elementami {10, 20, 30, 40, 50}. Iteruj po
         * niej pętlą for-each i przy napotkaniu wartości 30 wywołaj list.add(60).
         * Złap ConcurrentModificationException i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ConcurrentHashMapMergeFromThreads {
        /*
         * 🧪 Zadanie 2:
         * Utwórz ConcurrentHashMap<String, Integer>. Za pomocą 4 wątków (pula
         * fixed) inkrementuj liczniki dla 3 stałych kluczy ("kot", "pies", "ptak")
         * przy pomocy merge(klucz, 1, Integer::sum). Po zamknięciu puli wypisz
         * finalną mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ArrayBlockingQueueBasicPutTake {
        /*
         * 🧪 Zadanie 3:
         * Utwórz ArrayBlockingQueue<Integer> o pojemności 5. Wywołaj put() 5 razy
         * (wartości 1-5), wypisz queue.size(), a następnie wywołaj take() 5 razy w
         * pętli, wypisując każdy odebrany element w kolejności FIFO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PutBlocksWhenFull {
        /*
         * 🧪 Zadanie 4:
         * Utwórz ArrayBlockingQueue<String> o pojemności 2. Wątek producent
         * wywołuje put() dla 4 elementów (co spowoduje blokowanie 2 razy). Wątek
         * konsument odbiera je z małymi opóźnieniami (sleep 100ms między take()).
         * Wypisz kolejność produkcji i konsumpcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TakeBlocksWhenEmpty {
        /*
         * 🧪 Zadanie 5:
         * Uruchom wątek konsumenta jako PIERWSZY - wywołuje take() na pustej
         * ArrayBlockingQueue i czeka. Po 200ms wątek producenta wykonuje pojedynczy
         * put() jednego elementu. Wypisz, że konsument otrzymał element dopiero po
         * jego dodaniu (potwierdzenie, że take() czekało, a nie rzuciło błędu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ProducerConsumerPoisonPillBasic {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj producenta-konsumenta jak w lekcji, ale z 5 elementami
         * "Zadanie-1".."Zadanie-5" i ArrayBlockingQueue(3). Producent na końcu
         * wysyła poison pill "__KONIEC__". Użyj puli z dokładnie 2 wątkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ComputeIfAbsentGrouping {
        /*
         * 🧪 Zadanie 7:
         * Utwórz ConcurrentHashMap<Character, List<String>>. Dla 5 stałych słów
         * ("kot", "kran", "pies", "portal", "auto") użyj computeIfAbsent(), by
         * pogrupować je wg pierwszej litery. Wypisz finalną mapę grup.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PutIfAbsentSeenBeforeCache {
        /*
         * 🧪 Zadanie 8:
         * Utwórz ConcurrentHashMap<String, String> jako cache "już widziane". 4
         * wątki sprawdzają tę samą tablicę Stringów z powtórzeniami (np.
         * {"a","b","a","c","b"}) i dla każdego elementu wywołują
         * putIfAbsent(element, Thread.currentThread().getName()). Wypisz, który
         * wątek "wygrał" wpis dla każdego unikalnego elementu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_OfferWithTimeoutOnFullQueue {
        /*
         * 🧪 Zadanie 9:
         * Utwórz ArrayBlockingQueue<Integer>(1) i wypełnij ją jednym elementem.
         * Wywołaj offer(2, 200, TimeUnit.MILLISECONDS) (bez konsumenta) i wypisz,
         * że zwróciło false po upływie czasu, zamiast blokować się bezterminowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PollWithTimeoutOnEmptyQueue {
        /*
         * 🧪 Zadanie 10:
         * Utwórz pustą ArrayBlockingQueue<Integer>(5). Wywołaj poll(200,
         * TimeUnit.MILLISECONDS) i wypisz, że zwróciło null po upływie czasu,
         * zamiast blokować się bezterminowo.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultiProducerSingleConsumer {
        /*
         * 🧪 Zadanie 11:
         * Uruchom 3 wątki producentów, z których każdy dodaje 3 elementy (łącznie
         * 9) oznaczone swoim id producenta do wspólnej ArrayBlockingQueue(4). Użyj
         * CountDownLatch(3) do wykrycia, kiedy wszyscy producenci skończyli, a
         * dopiero wtedy wyślij poison pill do 1 konsumenta. Wypisz łączną liczbę
         * odebranych elementów (powinno być 9).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SingleProducerMultiConsumer {
        /*
         * 🧪 Zadanie 12:
         * 1 producent dodaje 10 elementów do wspólnej kolejki. 3 wątki konsumentów
         * odbierają z niej elementy, każdy zliczając ile SAM przetworzył. Zakończ
         * każdego konsumenta osobną poison pill (3 pigułki łącznie). Wypisz
         * podsumowanie przetworzonych elementów przez każdego konsumenta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ThreadSafeWordFrequencyCounter {
        /*
         * 🧪 Zadanie 13:
         * Podziel tablicę 20 słów na 4 równe fragmenty przetwarzane przez 4
         * wątki. Każdy wątek liczy częstość słów swojego fragmentu do wspólnej
         * ConcurrentHashMap<String,Integer> (merge()). Po zakończeniu zweryfikuj,
         * że suma wszystkich wartości w mapie równa się 20.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConcurrentHashMapNewKeySet {
        /*
         * 🧪 Zadanie 14:
         * Utwórz zbiór ConcurrentHashMap.<String>newKeySet(). 4 wątki dodają do
         * niego nakładające się wartości z tablicy {"a","b","c","a","b","d"}.
         * Zweryfikuj, że finalny zbiór zawiera dokładnie unikalne wartości
         * {"a","b","c","d"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BoundedRunnableQueue {
        /*
         * 🧪 Zadanie 15:
         * Utwórz BlockingQueue<Runnable> o pojemności 3. Producent dodaje 6
         * konkretnych zadań Runnable (każde wypisuje swój numer). Konsument w
         * pętli wywołuje task.run() dla każdego odebranego zadania, aż do
         * odebrania specjalnego "pustego" Runnable-znacznika końca. Zweryfikuj, że
         * każde z 6 zadań wykonało się dokładnie raz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConcurrentVsPlainHashMapMerge {
        /*
         * 🧪 Zadanie 16:
         * Uruchom ten sam eksperyment (4 wątki, każdy inkrementujący wspólny
         * licznik "total" 250 razy przez merge()) raz na ConcurrentHashMap, a raz
         * na zwykłej HashMap (bez synchronizacji). Wypisz finalną wartość "total"
         * dla obu przypadków i skomentuj (println) że ConcurrentHashMap
         * gwarantuje poprawność (1000), a zwykła HashMap tego nie gwarantuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ChainedTwoQueuePipeline {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj potok z dwiema BlockingQueue<Integer>: producent wrzuca liczby
         * 1-10 do stage1Queue. Wątek "worker" pobiera z stage1Queue, liczy kwadrat
         * i wrzuca do stage2Queue. Wątek konsumenta finalnego odbiera ze
         * stage2Queue i wypisuje wyniki. Zakończ oba etapy poison pillami
         * przepuszczanymi przez obie kolejki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_LogAggregatorFixedCount {
        /*
         * 🧪 Zadanie 18:
         * 3 wątki, każdy wysyła 4 oznaczone swoim id komunikaty logu (łącznie 12)
         * do wspólnej ArrayBlockingQueue(5). Jeden konsument odbiera dokładnie 12
         * elementów (znana z góry liczba, bez poison pill) do listy, a na końcu
         * wypisuje logi pogrupowane wg nadawcy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LinkedBlockingQueueComparison {
        /*
         * 🧪 Zadanie 19:
         * Powtórz scenariusz producenta-konsumenta z poison pill (jak w Zadaniu 6),
         * ale użyj LinkedBlockingQueue zamiast ArrayBlockingQueue. Wypisz nazwę
         * klasy użytej kolejki (queue.getClass().getSimpleName()) i porównaj (w
         * komentarzu) różnicę w domyślnej pojemności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_EvenOddRouter {
        /*
         * 🧪 Zadanie 20:
         * Producent wrzuca liczby 1-10 do wejściowej BlockingQueue. Wątek "router"
         * pobiera każdą liczbę i kieruje ją do jednej z dwóch wyjściowych
         * BlockingQueue (parzyste/nieparzyste) w zależności od parzystości. Dwa
         * wątki konsumenckie odbierają swoje kolejki (poison pill wysłana przez
         * router po rozdystrybuowaniu wszystkich liczb) i wypisują zebrane listy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiProducerMultiConsumer {
        /*
         * 🧪 Zadanie 21:
         * 2 producentów, każdy dodający 5 oznaczonych "zamówień" (łącznie 10) do
         * wspólnej ArrayBlockingQueue(4), oraz 2 konsumentów przetwarzających
         * zamówienia. Użyj CountDownLatch(2) do wykrycia zakończenia obu
         * producentów, po czym wyślij dokładnie 2 poison pille (po jednej na
         * konsumenta). Zweryfikuj, że suma zamówień przetworzonych przez obu
         * konsumentów wynosi 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RateLimitedCounterMap {
        /*
         * 🧪 Zadanie 22:
         * Utwórz ConcurrentHashMap<String, AtomicInteger> z 3 kategoriami. 5
         * wątków inkrementuje każdą kategorię 200 razy (łącznie 1000 na
         * kategorię). Po zakończeniu zweryfikuj, że każda kategoria ma wartość
         * dokładnie 1000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DeadLetterQueuePattern {
        /*
         * 🧪 Zadanie 23:
         * Producent wrzuca 10 elementów (2 z nich zawierają marker "BŁĄD") do
         * BlockingQueue. Konsument przy przetwarzaniu elementu z markerem
         * "przekazuje" go (put()) do OSOBNEJ kolejki dead-letter zamiast go
         * przetwarzać normalnie. Po zakończeniu (poison pill) wypisz osobno listę
         * poprawnie przetworzonych elementów i zawartość dead-letter queue.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BackpressureDemonstration {
        /*
         * 🧪 Zadanie 24:
         * Szybki producent (bez opóźnień) dodaje 8 elementów do
         * ArrayBlockingQueue(2), a wolny konsument śpi 100ms przed każdym take().
         * Wypisz sygnatury czasowe put() i take(), pokazując, że producent jest
         * spowalniany (blokowany na put()) przez tempo konsumenta. Zweryfikuj, że
         * mimo to wszystkie 8 elementów dotarło (poison pill na koniec).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FanOutSumWithFourConsumers {
        /*
         * 🧪 Zadanie 25:
         * Wypełnij BlockingQueue<Integer> liczbami 1-20. 4 wątki konsumenckie
         * pobierają elementy (aż otrzymają swoją poison pill - użyj 4 pigułek),
         * każdy sumując SWOJĄ część do osobnego wpisu w ConcurrentHashMap<String,Long>
         * kluczowanego nazwą wątku. Zsumuj wszystkie częściowe sumy z mapy i
         * zweryfikuj wynik wzorem n*(n+1)/2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ChatRoomSimulation {
        /*
         * 🧪 Zadanie 26:
         * 3 wątki "użytkowników", z których każdy wysyła (put()) 4 oznaczone
         * swoim imieniem wiadomości (łącznie 12) do wspólnej kolejki
         * reprezentującej pokój czatu. Jeden wątek "logger" odbiera dokładnie 12
         * wiadomości (znana z góry liczba) do zsynchronizowanej listy w kolejności
         * odbioru i na końcu wypisuje pełny transkrypt rozmowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SemaphoreLimitedActiveConsumers {
        /*
         * 🧪 Zadanie 27:
         * Uruchom 4 wątki konsumenckie odbierające łącznie 12 elementów z
         * BlockingQueue, ale ogranicz do 2 liczbę RÓWNOCZEŚNIE "przetwarzających"
         * (nie samego take()) konsumentów za pomocą Semaphore(2) opasującego krok
         * przetwarzania. Zweryfikuj, że wszystkie 12 elementów zostało
         * przetworzonych dokładnie raz łącznie przez wszystkich konsumentów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PriorityBatchDrainTo {
        /*
         * 🧪 Zadanie 28:
         * Producent wrzuca do jednej BlockingQueue naprzemiennie oznaczone
         * elementy "zwykły" i "pilny" (łącznie 10, w ustalonej kolejności
         * przyjścia). Konsument zamiast pobierać pojedynczo, okresowo odciąga
         * (drainTo()) wszystkie aktualnie dostępne elementy do tymczasowej listy,
         * sortuje ją tak by "pilne" szły przed "zwykłymi", i dopiero wtedy je
         * przetwarza. Powtarzaj aż do poison pill i wypisz finalną kolejność przetwarzania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RetryQueueWithDeadLetterLimit {
        /*
         * 🧪 Zadanie 29:
         * Przetwórz 8 elementów z BlockingQueue, z których 2 (ustalone z góry id)
         * ZAWSZE symulują porażkę. Nieudane elementy wracają (put()) na koniec tej
         * samej kolejki, a licznik prób dla każdego id trzymany jest w
         * ConcurrentHashMap<String,Integer>. Po 2 nieudanych próbach dany element
         * trafia do osobnej listy dead-letter zamiast być ponawiany dalej. Wypisz
         * finalny raport sukcesów i dead-letter.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_OrderProcessingSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletną symulację: 3 producentów (łącznie 10 zamówień) wrzuca
         * zamówienia do ArrayBlockingQueue(5), a status każdego zamówienia
         * ("RECEIVED"/"PROCESSING"/"DONE") śledzony jest we wspólnej
         * ConcurrentHashMap<String,String>. Pula 3 konsumentów przetwarza
         * zamówienia (koordynacja zakończenia producentów przez CountDownLatch(3)
         * i wysłanie 3 poison pilli). Na końcu wypisz mapę statusów oraz
         * podsumowanie liczby zamówień przetworzonych przez każdego konsumenta, i
         * poprawnie zamknij ExecutorService.
         */
        public static void main(String[] args) { }
    }
}
