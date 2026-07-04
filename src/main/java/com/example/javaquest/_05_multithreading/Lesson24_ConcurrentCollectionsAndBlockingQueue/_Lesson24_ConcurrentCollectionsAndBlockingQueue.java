package com.example.javaquest._05_multithreading.Lesson24_ConcurrentCollectionsAndBlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class _Lesson24_ConcurrentCollectionsAndBlockingQueue {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 KOLEKCJE WSPÓŁBIEŻNE – PERSPEKTYWA POPRAWNOŚCI WĄTKOWEJ
         * ============================================================
         * W rozdziale o kolekcjach (Lesson20_ConcurrentCollections)
         * poznaliśmy już PODSTAWOWE API: ConcurrentHashMap.put()/get(),
         * CopyOnWriteArrayList, ArrayBlockingQueue.put()/take() itd.
         * Nie będziemy tego powtarzać. Tutaj, w rozdziale o wątkach,
         * interesuje nas INNE pytanie: DLACZEGO zwykłe kolekcje w ogóle
         * są problemem przy wielu wątkach, i jak BlockingQueue pomaga
         * ZBUDOWAĆ poprawny wzorzec producent-konsument.
         *
         * ❌ PROBLEM: HashMap / ArrayList używane współbieżnie
         * Zwykłe kolekcje (HashMap, ArrayList, HashSet...) NIE mają
         * żadnej ochrony przed jednoczesnym dostępem z wielu wątków:
         *
         * 1. ConcurrentModificationException – jeśli wątek A iteruje
         *    po liście (for-each), a wątek B w tym czasie ją modyfikuje
         *    (add/remove), iterator wykrywa zmianę licznika modCount
         *    i rzuca wyjątek przy kolejnym next().
         *
         * 2. Cichsze i GROŹNIEJSZE: uszkodzenie wewnętrznej struktury.
         *    HashMap przy rehashingu (powiększaniu tablicy) przepina
         *    wewnętrzne referencje bucketów. Jeśli dwa wątki robią to
         *    RÓWNOCZEŚNIE, może powstać np. cykliczna lista w buckecie –
         *    kolejne get() na tym kluczu wchodzi w NIESKOŃCZONĄ pętlę
         *    (realny, znany problem z produkcji, szczególnie sprzed
         *    Javy 8). Brak wyjątku, po prostu wątek "wisi" albo dane
         *    znikają/duplikują się bez ostrzeżenia.
         *
         * ✅ ConcurrentHashMap – blokowanie SEGMENTOWE (koncepcyjnie)
         * Zamiast jednej blokady na całą mapę (jak w starym Hashtable
         * czy Collections.synchronizedMap()), ConcurrentHashMap dzieli
         * dane na wiele "segmentów"/binów i blokuje TYLKO ten fragment,
         * do którego aktualnie pisze wątek. Efekt: wielu wątków może
         * pisać RÓWNOLEGLE do RÓŻNYCH kluczy bez wzajemnego blokowania,
         * a odczyty w większości w ogóle nie wymagają blokady. To daje
         * dużo wyższą przepustowość niż jedna globalna blokada.
         *
         * ✅ BlockingQueue – narzędzie do koordynacji, nie tylko "kolekcja"
         * To nie jest zwykła kolekcja z ochroną przed wyjątkiem – to
         * mechanizm KOORDYNACJI wątków: producent i konsument nie muszą
         * ręcznie pilnować synchronized/wait/notify. Kolejka SAMA:
         * - usypia producenta, gdy jest pełna (put() czeka na miejsce)
         * - usypia konsumenta, gdy jest pusta (take() czeka na element)
         */

        System.out.println("=== 1) Dlaczego zwykła kolekcja pęka przy wielu wątkach ===");
        demoConcurrentModificationProblem();

        System.out.println("\n=== 2) ConcurrentHashMap – bezpieczne współbieżne zliczanie ===");
        demoConcurrentHashMapCoordination();

        System.out.println("\n=== 3) Producent-konsument z ArrayBlockingQueue + poison pill ===");
        demoProducerConsumerWithPoisonPill();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zwykłe HashMap/ArrayList NIE są thread-safe: równoczesna
         *   modyfikacja to co najmniej ConcurrentModificationException,
         *   a w gorszym razie cicho uszkodzona struktura wewnętrzna.
         * - ConcurrentHashMap → bezpieczna współbieżnie, blokowanie
         *   segmentowe (fragment danych, nie cała mapa) = wysoka
         *   przepustowość przy równoległych zapisach do różnych kluczy.
         * - BlockingQueue to przede wszystkim NARZĘDZIE KOORDYNACJI:
         *   put()/take() same usypiają/budzą wątki producenta i
         *   konsumenta – nie trzeba ręcznego wait()/notify().
         * - Wzorzec "poison pill": producent na koniec wysyła specjalny
         *   znacznik (np. null-object albo dedykowaną stałą), konsument
         *   po jego otrzymaniu kończy pętlę – to DETERMINISTYCZNY sposób
         *   zakończenia, bez zgadywania "czy kolejka już na pewno pusta".
         * - Zawsze zamykaj ExecutorService przez shutdown()+awaitTermination().
         */
    }

    /**
     * Pokazuje ConcurrentModificationException przy iteracji po zwykłej
     * ArrayList, gdy w międzyczasie ktoś ją modyfikuje – tu wprost
     * (bez wątków, dla czytelności), by uzasadnić PO CO nam w ogóle
     * kolekcje współbieżne.
     */
    private static void demoConcurrentModificationProblem() {
        List<String> plainList = new ArrayList<>(List.of("A", "B", "C"));
        try {
            for (String item : plainList) {
                System.out.println("Iteruję: " + item);
                if (item.equals("B")) {
                    plainList.add("D"); // modyfikacja w trakcie iteracji!
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Złapano ConcurrentModificationException: " + e);
            System.out.println("To dzieje się nawet w JEDNYM wątku – z wieloma wątkami");
            System.out.println("ryzyko jest jeszcze większe i mniej przewidywalne.");
        }
    }

    /**
     * Wiele wątków bezpiecznie inkrementuje liczniki w ConcurrentHashMap.
     * Dzięki blokowaniu segmentowemu wątki piszące do RÓŻNYCH kluczy
     * nie blokują się nawzajem.
     */
    private static void demoConcurrentHashMapCoordination() throws InterruptedException {
        Map<String, Integer> hitCounters = new ConcurrentHashMap<>();
        String[] endpoints = {"/login", "/home", "/login", "/profile", "/home", "/login"};

        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (String endpoint : endpoints) {
            pool.submit(() -> hitCounters.merge(endpoint, 1, Integer::sum));
        }
        shutdownAndWait(pool);

        System.out.println("Liczniki odwiedzin (bez utraconych aktualizacji): " + hitCounters);
    }

    /**
     * Klasyczny producent-konsument na ArrayBlockingQueue.
     * Producent generuje N "zamówień", a na koniec wrzuca specjalny
     * obiekt POISON_PILL. Konsument pracuje w pętli i kończy działanie
     * dokładnie w momencie, gdy odbierze poison pill – deterministyczne
     * zakończenie, bez zgadywania czy kolejka "na pewno" jest pusta.
     */
    private static void demoProducerConsumerWithPoisonPill() throws InterruptedException {
        final String POISON_PILL = "__KONIEC__";
        BlockingQueue<String> orders = new ArrayBlockingQueue<>(3); // mała pojemność - wymusi czekanie

        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String order = "Zamówienie-" + i;
                    orders.put(order); // blokuje, gdy kolejka pełna
                    System.out.println("[Producent] dodał: " + order + " (rozmiar kolejki=" + orders.size() + ")");
                }
                orders.put(POISON_PILL); // sygnał końca dla konsumenta
                System.out.println("[Producent] wysłał poison pill – koniec produkcji");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        pool.submit(() -> {
            try {
                while (true) {
                    String order = orders.take(); // blokuje, gdy kolejka pusta
                    if (order.equals(POISON_PILL)) {
                        System.out.println("[Konsument] odebrał poison pill – kończę pracę");
                        break;
                    }
                    System.out.println("[Konsument] przetwarza: " + order);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        shutdownAndWait(pool);
        System.out.println("Producent i konsument zakończyli deterministycznie, kolejka pusta: " + orders.isEmpty());
    }

    /**
     * Wzorcowe, bezpieczne zamykanie ExecutorService – zawsze z timeoutem.
     */
    private static void shutdownAndWait(ExecutorService pool) throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }
}
