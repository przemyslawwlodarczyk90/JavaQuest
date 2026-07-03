package com.example.javaquest._03_collections.Lesson20_ConcurrentCollections;

public class _Exercises_Lesson20_ConcurrentCollections {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicConcurrentHashMap {
        /*
         * 🧪 Zadanie 1:
         * ConcurrentHashMap<String, Integer>: put("a",1), put("b",2), put("c",3).
         * Wypisz mapę, sprawdź get("b") i containsKey("d").
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NullNotAllowed {
        /*
         * 🧪 Zadanie 2:
         * Spróbuj wykonać put(null, 1) oraz put("key", null) na ConcurrentHashMap.
         * Złap NullPointerException i wypisz komunikat dla obu przypadków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PutIfAbsentBasic {
        /*
         * 🧪 Zadanie 3:
         * ConcurrentHashMap<String,Integer>: putIfAbsent("x", 10).
         * Wywołaj ponownie putIfAbsent("x", 99) – sprawdź że wartość się nie zmieniła.
         * Wypisz mapę po obu wywołaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BasicCopyOnWriteArrayList {
        /*
         * 🧪 Zadanie 4:
         * CopyOnWriteArrayList<String>: add("Java"), add("Kotlin"), add("Scala").
         * Wypisz listę, usuń "Kotlin" metodą remove(Object), wypisz ponownie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ArrayBlockingQueueBasics {
        /*
         * 🧪 Zadanie 5:
         * ArrayBlockingQueue<Integer> o pojemności 3.
         * Dodaj offer(1), offer(2), offer(3), offer(4) – sprawdź że ostatni offer
         * zwraca false (kolejka pełna). Wypisz kolejkę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PollVsTake {
        /*
         * 🧪 Zadanie 6:
         * Pusta ArrayBlockingQueue<String> o pojemności 5.
         * Wywołaj poll() – sprawdź że zwraca null natychmiast (nie blokuje).
         * Skomentuj (bez wywoływania) dlaczego take() by zablokowało wątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ConcurrentLinkedQueueBasics {
        /*
         * 🧪 Zadanie 7:
         * ConcurrentLinkedQueue<Integer>: offer(1), offer(2), offer(3).
         * Wypisz kolejkę, wykonaj poll() i peek(), wypisz stan po obu operacjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SynchronizedListWrapper {
        /*
         * 🧪 Zadanie 8:
         * Stwórz List<String> przez Collections.synchronizedList(new ArrayList<>()).
         * Dodaj kilka elementów. Zademonstruj poprawną iterację
         * wewnątrz bloku synchronized(list) { ... }.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MergeCounter {
        /*
         * 🧪 Zadanie 9:
         * ConcurrentHashMap<String,Integer> counter.
         * Użyj merge("visits", 1, Integer::sum) pięć razy pod rząd.
         * Wypisz finalną wartość dla klucza "visits" (powinna wynosić 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PriorityBlockingQueueBasics {
        /*
         * 🧪 Zadanie 10:
         * PriorityBlockingQueue<Integer>: offer(50), offer(10), offer(30), offer(20).
         * Zdejmuj poll() w pętli aż pusta i wypisz kolejność (powinna być rosnąca).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WordFrequencyCounter {
        /*
         * 🧪 Zadanie 11:
         * Tekst: "ala ma kota kot ma alę ala kota lubi".
         * Policz częstość każdego słowa używając ConcurrentHashMap<String,AtomicInteger>
         * i computeIfAbsent + incrementAndGet. Wypisz wynikową mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SnapshotIterationCOWAL {
        /*
         * 🧪 Zadanie 12:
         * CopyOnWriteArrayList<Integer>: {1,2,3}.
         * Podczas iteracji for-each dodaj nowy element do listy (add(99)) –
         * pokaż że iterator NIE rzuca ConcurrentModificationException
         * (bo działa na migawce) i że nowy element nie pojawia się w tej iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ArrayListConcurrentModificationException {
        /*
         * 🧪 Zadanie 13:
         * Zwykły ArrayList<Integer>: {1,2,3,4,5}.
         * Podczas iteracji for-each usuń element (list.remove(...)) –
         * złap i wypisz ConcurrentModificationException.
         * Napraw problem zamieniając ArrayList na CopyOnWriteArrayList.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ProducerConsumerSingleThread {
        /*
         * 🧪 Zadanie 14:
         * BlockingQueue<String> capacity=3. Jeden wątek-producent dodaje
         * put() 5 elementów ("item1".."item5"), jeden wątek-konsument
         * pobiera take() 5 razy. Uruchom oba wątki i poczekaj (join())
         * na zakończenie, wypisując log produkcji/konsumpcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ComputeIfAbsentGrouping {
        /*
         * 🧪 Zadanie 15:
         * Lista słów: {"kot","kura","kajak","pies","piec","auto"}.
         * Pogrupuj słowa po pierwszej literze używając
         * ConcurrentHashMap<Character, List<String>> i computeIfAbsent.
         * Wypisz grupy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AtomicIntegerVsPlainInt {
        /*
         * 🧪 Zadanie 16:
         * Dwa wątki, każdy 100 000 razy inkrementuje: raz zwykłe pole int
         * (bez synchronizacji), raz AtomicInteger.incrementAndGet().
         * Porównaj finalne wartości – pokaż że plain int daje błędny wynik,
         * a AtomicInteger poprawny (200 000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_HashMapRaceCondition {
        /*
         * 🧪 Zadanie 17:
         * Dwa wątki jednocześnie wykonują put() na współdzielonym zwykłym HashMap
         * (po 1000 unikalnych kluczy każdy). Uruchom kilka razy i pokaż że
         * może wystąpić utrata danych lub wyjątek. Zamień na ConcurrentHashMap i pokaż poprawność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BoundedQueueBackpressure {
        /*
         * 🧪 Zadanie 18:
         * ArrayBlockingQueue<Integer> o pojemności 2.
         * Producent próbuje offer() z timeoutem 500ms gdy kolejka pełna –
         * pokaż że offer(e, timeout, unit) zwraca false po przekroczeniu czasu
         * zamiast blokować w nieskończoność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConcurrentSkipListMapOrdering {
        /*
         * 🧪 Zadanie 19:
         * ConcurrentSkipListMap<Integer,String>: dodaj klucze w losowej kolejności
         * (5, 1, 3, 2, 4). Wypisz mapę – sprawdź że klucze są automatycznie
         * posortowane (jak w TreeMap, ale thread-safe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MultipleProducersOneConsumer {
        /*
         * 🧪 Zadanie 20:
         * LinkedBlockingQueue<String>. Dwa wątki-producenci dodają po 5 zadań
         * każdy (put), jeden wątek-konsument pobiera 10 razy (take).
         * Użyj join() na wszystkich wątkach i wypisz podsumowanie ile zadań przetworzono.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ThreadSafeCacheWithComputeIfAbsent {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj prosty thread-safe cache wyników funkcji kosztownej
         * (np. symulowane obliczenie fibonacci z Thread.sleep) używając
         * ConcurrentHashMap<Integer,Long>.computeIfAbsent. Uruchom kilka wątków
         * żądających tych samych kluczy i pokaż że obliczenie wykonuje się raz na klucz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ProducerConsumerMultiplePairs {
        /*
         * 🧪 Zadanie 22:
         * System z 3 producentami i 2 konsumentami dzielącymi jedną
         * BlockingQueue<Integer>. Każdy producent generuje 20 liczb,
         * każdy konsument przetwarza aż do otrzymania specjalnego znacznika
         * "koniec" (poison pill). Zsumuj wszystkie przetworzone liczby
         * (użyj AtomicLong jako współdzielonego sumatora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RateLimiterWithBlockingQueue {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj prosty rate limiter (token bucket) używając
         * ArrayBlockingQueue<Object> jako zbiornika tokenów.
         * Osobny wątek co 100ms dodaje token (jeśli jest miejsce),
         * a metoda tryAcquire() próbuje poll() token z timeoutem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ParallelWordCountAggregation {
        /*
         * 🧪 Zadanie 24:
         * Podziel duży tekst (kilka zdań) na 4 fragmenty, przetwórz każdy
         * w osobnym wątku licząc wystąpienia słów lokalnie, następnie
         * scal wyniki do współdzielonej ConcurrentHashMap<String,Integer>
         * używając merge(). Wypisz finalne zliczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DiningPhilosophersWithConcurrentStructures {
        /*
         * 🧪 Zadanie 25:
         * Uproszczony problem pięciu filozofów: użyj ConcurrentHashMap
         * do śledzenia stanu każdego "widelca" (wolny/zajęty) i zasymuluj
         * kilka rund jedzenia bez zakleszczenia (np. ustalona kolejność
         * podnoszenia widelców). Wypisz log kto je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_EventBusWithCopyOnWriteArrayList {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj prosty EventBus: CopyOnWriteArrayList<Consumer<String>>
         * subscribers. subscribe(listener), publish(event) wywołuje wszystkich
         * subskrybentów. Pokaż że subskrybent może się wypisać (unsubscribe)
         * podczas trwania publish() bez wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConcurrentHashMapVsSynchronizedMapBenchmark {
        /*
         * 🧪 Zadanie 27:
         * Porównaj przepustowość ConcurrentHashMap vs
         * Collections.synchronizedMap(new HashMap<>()) przy 4 wątkach
         * wykonujących po 100 000 operacji put/get. Zmierz czas
         * (System.nanoTime()) i wypisz który wariant był szybszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GracefulShutdownWithPoisonPill {
        /*
         * 🧪 Zadanie 28:
         * BlockingQueue<Task> gdzie Task to obiekt lub null jako "poison pill".
         * Producent generuje 10 zadań, potem wysyła poison pill.
         * Konsument pracuje w pętli take() aż otrzyma poison pill,
         * wtedy kończy działanie – zaimplementuj i zweryfikuj czyste zamknięcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConcurrentInventorySystem {
        /*
         * 🧪 Zadanie 29:
         * System magazynowy: ConcurrentHashMap<String,AtomicInteger> stock.
         * Wiele wątków jednocześnie wykonuje reserve(String product, int qty)
         * (zmniejsza stan, ale nie może zejść poniżej zera – użyj pętli CAS
         * z compareAndSet lub updateAndGet). Przetestuj równoległe rezerwacje
         * gdy zapas jest ograniczony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CustomBlockingQueueImplementation {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj własną prostą blokującą kolejkę SimpleBlockingQueue<T>
         * używając zwykłej LinkedList jako bufora oraz synchronized/wait/notifyAll
         * (bez używania java.util.concurrent.BlockingQueue) z metodami put(T) i take().
         * Przetestuj z jednym producentem i jednym konsumentem w osobnych wątkach.
         */
        public static void main(String[] args) { }
    }
}
