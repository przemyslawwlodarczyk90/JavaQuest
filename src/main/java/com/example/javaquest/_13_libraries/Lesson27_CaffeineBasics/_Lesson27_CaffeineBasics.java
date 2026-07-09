package com.example.javaquest._13_libraries.Lesson27_CaffeineBasics;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import java.time.Duration;

public class _Lesson27_CaffeineBasics {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 27: CAFFEINE - PODSTAWY CACHE IN-MEMORY ===");

        /*
         * ============================================================
         * 📦 CZYM JEST CAFFEINE I PO CO CACHE
         * ============================================================
         * - Caffeine to nowoczesna biblioteka cache in-memory dla Javy
         *   (com.github.ben-manes.caffeine:caffeine). Jest DUCHOWYM
         *   NASTĘPCĄ Guava Cache (Lesson11 tego rozdziału) - ten sam
         *   autor (Ben Manes), ale nowocześniejszy algorytm eviction
         *   (W-TinyLFU zamiast prostego LRU) i wyraźnie lepsza
         *   wydajność/przepustowość. API jest CELOWO bardzo podobne do
         *   Guava Cache, więc przejście z jednego na drugie jest łatwe.
         * - Caffeine jest używany "pod spodem" m.in. przez Spring Cache
         *   (jako jedna z domyślnie wspieranych implementacji) oraz
         *   przez Hibernate L2 cache (Lesson24 w _12_hibernate używał
         *   Ehcache, ale Ehcache 3.x sam w sobie może korzystać z
         *   Caffeine jako silnika pod spodem).
         * - PO CO w ogóle cache? Wyobraź sobie operację, która jest
         *   KOSZTOWNA - wolne obliczenie matematyczne, zapytanie do
         *   zewnętrznego API przez sieć, albo zapytanie do bazy danych.
         *   Jeśli tej samej odpowiedzi potrzebujesz wielokrotnie w
         *   krótkim czasie, zamiast powtarzać kosztowną operację za
         *   każdym razem, wynik ZAPAMIĘTUJESZ w pamięci (cache) i przy
         *   kolejnych zapytaniach zwracasz go od razu.
         * - To jest KOMPROMIS pamięć-za-szybkość ("space-time
         *   tradeoff") - oddajesz trochę pamięci RAM (na przechowanie
         *   wyników), a w zamian zyskujesz ogromne przyspieszenie przy
         *   powtarzających się zapytaniach o te same dane.
         * - UWAGA: to jest cache OGÓLNEGO PRZEZNACZENIA, niezależny od
         *   Hibernate/JPA - może cache'ować DOWOLNĄ wartość w DOWOLNEJ
         *   aplikacji Javy (wynik obliczenia, odpowiedź HTTP, wynik
         *   parsowania pliku itd.), nie tylko encje bazodanowe.
         */
        long slowStart = System.currentTimeMillis();
        int slowResult = expensiveComputation(7);
        long slowDuration = System.currentTimeMillis() - slowStart;
        System.out.println("Wynik kosztownego obliczenia(7) = " + slowResult
                + " (czas: " + slowDuration + " ms, BEZ cache)");

        /*
         * ============================================================
         * 🔹 BUDOWANIE PROSTEGO CACHE: Caffeine.newBuilder().build()
         * ============================================================
         * - Cache<K, V> to podstawowy interfejs Caffeine - "ręczny"
         *   cache, w którym TY decydujesz kiedy coś wstawić (put) i
         *   kiedy sprawdzić czy coś już jest (getIfPresent). W Lesson28
         *   poznasz LoadingCache, który sam wie jak obliczyć brakującą
         *   wartość.
         * - Caffeine.newBuilder() zwraca budowniczego (builder), na
         *   którym konfigurujesz limity/politykę, a .build() tworzy
         *   gotowy obiekt Cache.
         * - maximumSize(100) ogranicza cache do maksymalnie 100 wpisów
         *   - to jedna z najważniejszych konfiguracji, bo bez limitu
         *   cache mógłby rosnąć w nieskończoność i zająć całą dostępną
         *   pamięć (wyciek pamięci "przez cache").
         */
        Cache<Integer, Integer> simpleCache = Caffeine.newBuilder()
                .maximumSize(100)
                .build();

        // put - ręczne wstawienie wartości do cache
        simpleCache.put(7, slowResult);
        System.out.println("\n=== Cache.put(7, " + slowResult + ") - wartosc zapisana recznie ===");

        // getIfPresent - zwraca wartość, jeśli jest w cache, lub null, jeśli jej brak
        Integer cachedValue = simpleCache.getIfPresent(7);
        System.out.println("Cache.getIfPresent(7) = " + cachedValue);

        Integer missingValue = simpleCache.getIfPresent(999);
        System.out.println("Cache.getIfPresent(999) = " + missingValue + " (brak wpisu -> null)");

        // invalidate - ręczne usunięcie konkretnego wpisu z cache
        simpleCache.invalidate(7);
        System.out.println("Po Cache.invalidate(7): getIfPresent(7) = " + simpleCache.getIfPresent(7));

        /*
         * ============================================================
         * 🔍 EVICTION (WYPIERANIE WPISÓW): SIZE-BASED vs TIME-BASED
         * ============================================================
         * - Cache NIE MOŻE rosnąć w nieskończoność - w pewnym momencie
         *   musi USUWAĆ (evict) stare wpisy, żeby zrobić miejsce na
         *   nowe lub żeby dane się nie "zestarzały". Caffeine oferuje
         *   kilka niezależnych, łączonych ze sobą polityk eviction.
         *
         * 1) SIZE-BASED - maximumSize(long): gdy cache przekroczy
         *    zadany rozmiar, NAJMNIEJ przydatne wpisy (wg algorytmu
         *    W-TinyLFU, patrz niżej) są usuwane, żeby zrobić miejsce
         *    dla nowych.
         *
         * 2) TIME-BASED - dwie RÓŻNE polityki czasowe:
         *    - expireAfterWrite(Duration) - wpis wygasa okreslony
         *      czas PO ZAPISIE (put/obliczeniu), niezależnie od tego,
         *      ile razy w międzyczasie był odczytywany. Dobre dla
         *      danych, które powinny być odświeżane regularnie
         *      (np. kurs waluty co minutę).
         *    - expireAfterAccess(Duration) - wpis wygasa okreslony
         *      czas PO OSTATNIM ODCZYCIE (getIfPresent/get), więc
         *      częste odczyty PRZEDŁUŻAJĄ jego życie w cache. Dobre
         *      dla danych "sesyjnych" - dopóki ktoś ich używa, mają
         *      zostać w pamięci, ale po okresie bezczynności można je
         *      usunąć.
         */
        System.out.println("\n=== ROZNICA: expireAfterWrite vs expireAfterAccess ===");

        Cache<String, String> writeExpiringCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(300))
                .build();
        writeExpiringCache.put("klucz", "wartosc-zapisana-raz");

        // Nawet mimo wielokrotnego odczytu, wpis wygasnie DOKLADNIE 300ms po PUT.
        for (int i = 0; i < 3; i++) {
            Thread.sleep(120);
            System.out.println("expireAfterWrite, odczyt #" + (i + 1) + " po ~"
                    + (120 * (i + 1)) + "ms: " + writeExpiringCache.getIfPresent("klucz"));
        }

        Cache<String, String> accessExpiringCache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMillis(300))
                .build();
        accessExpiringCache.put("klucz", "wartosc-odswiezana-przy-odczycie");

        // Każdy odczyt w odstępie < 300ms PRZEDŁUŻA życie wpisu.
        for (int i = 0; i < 3; i++) {
            Thread.sleep(200);
            System.out.println("expireAfterAccess, odczyt #" + (i + 1) + " po 200ms od poprzedniego: "
                    + accessExpiringCache.getIfPresent("klucz") + " (kazdy odczyt resetuje licznik)");
        }
        Thread.sleep(350);
        System.out.println("expireAfterAccess, po 350ms BEZ odczytu: "
                + accessExpiringCache.getIfPresent("klucz") + " (wygaslo z braku aktywnosci)");

        /*
         * ============================================================
         * 🔍 POLITYKA EVICTION: W-TinyLFU (koncepcyjnie)
         * ============================================================
         * - Gdy cache jest pełny (maximumSize) i trzeba zrobić miejsce
         *   na nowy wpis, TRZEBA wybrać, KTÓRY istniejący wpis usunąć.
         *   Prosty algorytm LRU (Least Recently Used) usuwa wpis, który
         *   był NAJDAWNIEJ odczytany - ale to naiwne: pojedynczy,
         *   przypadkowy "skok" odczytów rzadko używanego klucza może
         *   wypchnąć z cache wpis, który normalnie jest odczytywany
         *   BARDZO często.
         * - Caffeine domyślnie używa W-TinyLFU (Windowed Tiny Least
         *   Frequently Used) - "sprytniejszej" polityki, która bierze
         *   pod uwagę DWIE rzeczy jednocześnie:
         *     1. CZĘSTOTLIWOŚĆ - jak często dany klucz był używany
         *        HISTORYCZNIE (przybliżona licznikami częstotliwości,
         *        nie pełną historią - stąd "Tiny"),
         *     2. ŚWIEŻOŚĆ - niedawno dodane wpisy dostają "okno"
         *        (Windowed) szansy, żeby udowodnić swoją przydatność,
         *        zanim zostaną ocenione na równi ze starszymi.
         * - Efekt: W-TinyLFU radzi sobie DUŻO lepiej niż czyste LRU z
         *   typowymi wzorcami dostępu w aplikacjach (np. gdy część
         *   danych jest "gorąca" - odczytywana stale - a część tylko
         *   sporadycznie). Nie musisz implementować tego algorytmu
         *   samodzielnie - Caffeine robi to automatycznie pod maską,
         *   wystarczy ustawić maximumSize.
         */
        System.out.println("\n=== W-TinyLFU (koncepcyjnie) ===");
        System.out.println("Caffeine domyslnie uwzglednia CZESTOTLIWOSC + SWIEZOSC wpisu przy evictowaniu,");
        System.out.println("zamiast prostego 'najdawniej uzyty' (LRU) - stad lepsza trafnosc cache w praktyce.");

        /*
         * ============================================================
         * 📌 STATYSTYKI CACHE: recordStats() + cache.stats()
         * ============================================================
         * - Domyślnie Caffeine NIE zbiera statystyk (minimalny narzut
         *   wydajnościowy) - trzeba je jawnie włączyć przez
         *   .recordStats() na builderze.
         * - cache.stats() zwraca obiekt CacheStats z m.in.:
         *   hitCount(), missCount(), hitRate() (odsetek trafień),
         *   evictionCount() (ile wpisów zostało wypartych).
         * - Statystyki są NIEOCENIONE w praktyce - pozwalają
         *   zweryfikować, czy cache w ogóle POMAGA (wysoki hit rate)
         *   czy jest źle skonfigurowany (niski hit rate = zbyt małe
         *   maximumSize albo zbyt krótkie expireAfter*).
         */
        Cache<Integer, Integer> statsCache = Caffeine.newBuilder()
                .maximumSize(50)
                .recordStats()
                .build();

        // Symulacja ruchu: część kluczy odczytywana wielokrotnie (hit), część raz (miss).
        for (int round = 0; round < 5; round++) {
            for (int key = 0; key < 10; key++) {
                Integer value = statsCache.getIfPresent(key);
                if (value == null) {
                    value = expensiveComputation(key);
                    statsCache.put(key, value);
                }
            }
        }

        CacheStats stats = statsCache.stats();
        System.out.println("\n=== STATYSTYKI CACHE (recordStats) ===");
        System.out.println("Trafienia (hitCount): " + stats.hitCount());
        System.out.println("Chybienia (missCount): " + stats.missCount());
        System.out.printf("Wspolczynnik trafien (hitRate): %.2f%%%n", stats.hitRate() * 100);
        System.out.println("Liczba wypartych wpisow (evictionCount): " + stats.evictionCount());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Caffeine to nowoczesny, wydajny cache in-memory - duchowy
         *   następca Guava Cache (Lesson11), z bardzo podobnym API.
         * - Cache<K,V> to "ręczny" cache: put/getIfPresent/invalidate -
         *   TY decydujesz, kiedy wstawić i sprawdzić wartość.
         * - Eviction: maximumSize (rozmiar) i dwie RÓŻNE polityki
         *   czasowe - expireAfterWrite (od zapisu) vs expireAfterAccess
         *   (od ostatniego odczytu, odczyty przedłużają życie wpisu).
         * - W-TinyLFU: domyślna, "sprytniejsza" niż LRU polityka
         *   eviction, uwzględniająca częstotliwość ORAZ świeżość.
         * - recordStats() + cache.stats() pozwalają zmierzyć, czy
         *   cache faktycznie POMAGA (hit rate) w praktyce.
         * - W Lesson28 poznasz LoadingCache (cache, który SAM oblicza
         *   brakujące wartości), refreshAfterWrite, AsyncLoadingCache
         *   oraz RemovalListener.
         */
        System.out.println("\n=== KONIEC LEKCJI 27 ===");
    }

    /**
     * Symuluje KOSZTOWNĄ operację (np. skomplikowane obliczenie, wolne
     * wywołanie zewnętrznego API albo zapytanie do bazy danych) przez
     * sztuczne opóźnienie. W realnym kodzie byłoby to np. wywołanie
     * REST API albo złożone zapytanie SQL.
     */
    private static int expensiveComputation(int input) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return input * input;
    }
}
