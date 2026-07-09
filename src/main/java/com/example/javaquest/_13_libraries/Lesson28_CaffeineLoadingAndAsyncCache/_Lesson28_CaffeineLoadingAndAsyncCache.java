package com.example.javaquest._13_libraries.Lesson28_CaffeineLoadingAndAsyncCache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson28_CaffeineLoadingAndAsyncCache {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 28: CAFFEINE - LoadingCache, refreshAfterWrite, AsyncLoadingCache, RemovalListener ===");

        /*
         * ============================================================
         * 📦 KONTYNUACJA LEKCJI 27
         * ============================================================
         * Lekcja 27 pokazala "reczny" Cache<K,V>: TY decydowales, kiedy
         * sprawdzic getIfPresent(key) i kiedy recznie wstawic put(key,
         * value) po policzeniu brakujacej wartosci. Ten wzorzec (sprawdz
         * -> policz jesli brak -> wstaw) nazywa sie "cache-aside" i jest
         * bardzo czesty - ale wymaga POWTARZANIA tej samej logiki w
         * kazdym miejscu, gdzie korzystasz z cache.
         * Ta lekcja pokazuje LoadingCache - cache, ktory SAM WIE, jak
         * obliczyc brakujaca wartosc, wiec caly ten wzorzec jest ukryty
         * wewnatrz jednej metody .get(key).
         */

        demonstrateLoadingCacheBasics();
        demonstrateManualCacheVsLoadingCacheContrast();
        demonstrateRefreshAfterWrite();
        demonstrateAsyncLoadingCache();
        demonstrateRemovalListener();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - LoadingCache<K,V> (Caffeine.newBuilder()...build(CacheLoader))
         *   to Cache<K,V> rozszerzony o funkcje ladujaca (key -> value).
         *   Wywolanie .get(key) automatycznie OBLICZA i ZAPAMIETUJE
         *   brakujaca wartosc przy pierwszym uzyciu (miss), a przy
         *   kolejnych wywolaniach zwraca ja z cache (hit) - bez
         *   recznego getIfPresent+put w kazdym miejscu uzycia.
         * - refreshAfterWrite(Duration) NIE usuwa wpisu jak
         *   expireAfterWrite - zamiast tego, gdy wpis "sie zestarzeje",
         *   NASTEPNE wywolanie .get() na tym kluczu ZWRACA STARA (stale)
         *   wartosc OD RAZU (bez czekania), a jednoczesnie w tle
         *   URUCHAMIA ponowne przeliczenie. Dopiero kolejne wywolania
         *   dostana swiezy wynik. To kompromis: uzytkownik nigdy nie
         *   czeka na przeliczenie, kosztem czasami lekko nieaktualnych
         *   danych.
         * - AsyncLoadingCache<K,V> (buildAsync(CacheLoader)) to wersja
         *   asynchroniczna - .get(key) zwraca od razu CompletableFuture<V>,
         *   nie blokujac watku wywolujacego, a obliczenie dzieje sie w
         *   tle (domyslnie na ForkJoinPool.commonPool()). Idealne, gdy
         *   ladowanie wartosci jest kosztowne (np. wywolanie sieciowe) i
         *   nie chcesz blokowac watku, ktory prosi o dane.
         * - RemovalListener<K,V> to callback wywolywany za KAZDYM razem,
         *   gdy wpis znika z cache - niezaleznie od PRZYCZYNY. RemovalCause
         *   mowi dokladnie dlaczego: EXPLICIT (recznie invalidate()),
         *   REPLACED (nadpisany nowa wartoscia), SIZE (evictowany z
         *   powodu maximumSize), EXPIRED (wygasl czasowo), COLLECTED
         *   (zebrany przez GC, przy slabych referencjach). Przydatne do
         *   logowania, zwalniania zasobow albo debugowania eviction.
         */
        System.out.println("\n=== KONIEC LEKCJI 28 ===");
    }

    /*
     * ============================================================
     * 🔹 LoadingCache: build(CacheLoader) + automatyczne .get(key)
     * ============================================================
     * - Caffeine.newBuilder()...build(key -> obliczWartosc(key)) tworzy
     *   LoadingCache<K,V> zamiast zwyklego Cache<K,V> - roznica polega
     *   na tym, ze podajesz FUNKCJE LADUJACA (CacheLoader<K,V>, w Javie
     *   najczesciej jako lambda key -> value).
     * - .get(key) na LoadingCache NIGDY nie zwraca null (w przeciwienstwie
     *   do Cache.getIfPresent) - jesli wartosci nie ma w cache, funkcja
     *   ladujaca jest wywolywana AUTOMATYCZNIE, wynik zapisywany do
     *   cache i zwracany. Kolejne .get(key) dla tego samego klucza
     *   zwracaja juz zapamietana wartosc BEZ ponownego liczenia.
     */
    private static void demonstrateLoadingCacheBasics() {
        System.out.println("\n=== LoadingCache: build(CacheLoader) + get(key) auto-compute ===");

        AtomicInteger computationCount = new AtomicInteger(0);

        LoadingCache<Integer, Integer> squaresCache = Caffeine.newBuilder()
                .maximumSize(100)
                .build(key -> {
                    computationCount.incrementAndGet();
                    return expensiveSquare(key);
                });

        long start1 = System.currentTimeMillis();
        int result1 = squaresCache.get(7);
        long duration1 = System.currentTimeMillis() - start1;
        System.out.println("Pierwsze get(7) = " + result1 + " (czas: " + duration1
                + " ms, obliczenia wykonane: " + computationCount.get() + ")");

        long start2 = System.currentTimeMillis();
        int result2 = squaresCache.get(7);
        long duration2 = System.currentTimeMillis() - start2;
        System.out.println("Drugie get(7)  = " + result2 + " (czas: " + duration2
                + " ms, obliczenia wykonane: " + computationCount.get() + " - BEZ ponownego liczenia)");

        // get(key) dla wielu kluczy naraz - getAll(Iterable) laduje brakujace i zwraca Map
        var multiple = squaresCache.getAll(java.util.List.of(1, 2, 3, 7));
        System.out.println("getAll([1,2,3,7]) -> " + multiple
                + " (obliczenia wykonane laczne: " + computationCount.get() + ")");
    }

    /*
     * ============================================================
     * 🔍 KONTRAST: reczny Cache (Lesson27) vs LoadingCache (ta lekcja)
     * ============================================================
     * Ten sam problem - "pobierz uzytkownika po id, cache'ujac wynik" -
     * rozwiazany DWOMA sposobami obok siebie, zeby roznica byla jasna.
     */
    private static void demonstrateManualCacheVsLoadingCacheContrast() {
        System.out.println("\n=== Kontrast: reczny Cache (cache-aside) vs LoadingCache ===");

        // Wariant A (Lesson27): reczny Cache, TY piszesz logike "sprawdz-policz-wstaw"
        com.github.benmanes.caffeine.cache.Cache<String, String> manualCache =
                Caffeine.newBuilder().maximumSize(50).build();

        String userId = "user-42";
        String manualResult = manualCache.getIfPresent(userId);
        if (manualResult == null) {
            manualResult = fetchUserNameFromDatabase(userId);
            manualCache.put(userId, manualResult);
        }
        System.out.println("Wariant A (reczny Cache): " + manualResult
                + " (musiales sam napisac getIfPresent+put)");

        // Wariant B (ta lekcja): LoadingCache, logika "sprawdz-policz-wstaw" UKRYTA w get()
        LoadingCache<String, String> loadingCache = Caffeine.newBuilder()
                .maximumSize(50)
                .build(id -> fetchUserNameFromDatabase(id));

        String loadingResult = loadingCache.get(userId);
        System.out.println("Wariant B (LoadingCache): " + loadingResult
                + " (jedna linijka - get() sam wie, jak policzyc brakujaca wartosc)");
    }

    /*
     * ============================================================
     * 🔹 refreshAfterWrite(Duration) - ODSWIEZANIE W TLE
     * ============================================================
     * WAZNA ROZNICA wzgledem expireAfterWrite (Lesson27):
     * - expireAfterWrite: po uplywie czasu wpis jest USUWANY. Kolejny
     *   get() MUSI poczekac na pelne przeliczenie (synchronicznie),
     *   zanim cokolwiek zwroci.
     * - refreshAfterWrite: po uplywie czasu wpis NIE jest usuwany -
     *   zostaje oznaczony jako "stale" (nieaktualny). Kolejny get() na
     *   tym kluczu OD RAZU zwraca STARA wartosc (bez czekania!), a w tle
     *   (asynchronicznie) startuje przeliczenie nowej wartosci. Dopiero
     *   PO zakonczeniu tego przeliczenia kolejne get() zwroca swiezy wynik.
     * Ten mechanizm jest idealny tam, gdzie wolisz na chwile dostac lekko
     * nieaktualne dane niz kazac uzytkownikowi CZEKAC na przeliczenie.
     */
    private static void demonstrateRefreshAfterWrite() throws InterruptedException {
        System.out.println("\n=== refreshAfterWrite(Duration) - stara wartosc od razu, przeliczenie w tle ===");

        AtomicInteger version = new AtomicInteger(0);

        LoadingCache<String, String> refreshingCache = Caffeine.newBuilder()
                .refreshAfterWrite(Duration.ofMillis(150))
                .build(key -> {
                    int v = version.incrementAndGet();
                    // Symulacja kosztownego przeliczenia - zeby bylo widac, ze
                    // odswiezenie dzieje sie w tle, a nie blokuje wywolujacego.
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return "wersja-" + v;
                });

        String first = refreshingCache.get("dane");
        System.out.println("Pierwsze get(\"dane\") -> " + first + " (pierwsze zaladowanie, synchroniczne)");

        // Odczyt PRZED uplywem refreshAfterWrite - wartosc jeszcze "swieza", bez odswiezania.
        String stillFresh = refreshingCache.get("dane");
        System.out.println("get(\"dane\") od razu potem -> " + stillFresh + " (wciaz swieze, brak odswiezania)");

        // Czekamy, az wpis "sie zestarzeje" wg refreshAfterWrite.
        Thread.sleep(200);

        // Ten get() zwraca STARA wartosc NATYCHMIAST, a w tle startuje przeliczenie.
        long staleReadStart = System.currentTimeMillis();
        String staleButInstant = refreshingCache.get("dane");
        long staleReadDuration = System.currentTimeMillis() - staleReadStart;
        System.out.println("get(\"dane\") po uplywie refreshAfterWrite -> " + staleButInstant
                + " (czas: " + staleReadDuration + " ms - STARA wartosc, ale BEZ czekania na przeliczenie)");

        // Dajemy chwile, zeby asynchroniczne odswiezenie w tle zdazylo sie zakonczyc.
        Thread.sleep(150);

        String refreshed = refreshingCache.get("dane");
        System.out.println("get(\"dane\") po odczekaniu na odswiezenie w tle -> " + refreshed
                + " (juz NOWA wersja - odswiezenie w tle zakonczone)");
    }

    /*
     * ============================================================
     * 🔍 AsyncLoadingCache: buildAsync(CacheLoader) + CompletableFuture<V>
     * ============================================================
     * - Caffeine.newBuilder()...buildAsync(key -> obliczWartosc(key))
     *   tworzy AsyncLoadingCache<K,V>.
     * - .get(key) NIE blokuje watku wywolujacego - zwraca OD RAZU
     *   CompletableFuture<V>, ktory "dokona sie" (complete), gdy
     *   obliczenie (domyslnie wykonywane na ForkJoinPool.commonPool())
     *   sie zakonczy.
     * - Idealne, gdy ladowanie wartosci jest kosztowne w czasie (np.
     *   zapytanie do zewnetrznego API/bazy) i chcesz, zeby watek
     *   wywolujacy mogl w miedzyczasie robic cos innego, zamiast czekac
     *   zablokowany.
     * - W demo UZYWAMY .join(), zeby main() zakonczyl sie samoistnie -
     *   w realnej aplikacji async czesto lancuchuje sie .thenApply(...)
     *   zamiast blokujaco czekac.
     */
    private static void demonstrateAsyncLoadingCache() {
        System.out.println("\n=== AsyncLoadingCache: buildAsync(CacheLoader) -> CompletableFuture<V> ===");

        AsyncLoadingCache<Integer, String> asyncCache = Caffeine.newBuilder()
                .maximumSize(100)
                .buildAsync(key -> {
                    // Symulacja wolnej operacji (np. wywolanie sieciowe) - wykonywana
                    // na osobnym watku (ForkJoinPool.commonPool()), NIE blokuje watku main.
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return "produkt-o-id-" + key;
                });

        CompletableFuture<String> futureResult = asyncCache.get(5);
        System.out.println("asyncCache.get(5) zwrocil OD RAZU obiekt: " + futureResult.getClass().getSimpleName()
                + " (watek main NIE jest zablokowany, obliczenie trwa w tle)");

        // .join() blokuje TERAZ (celowo, zeby demo bylo deterministyczne i main()
        // zakonczyl sie samoistnie), ale w realnym kodzie czesciej uzylibysmy
        // thenApply/thenAccept, zeby nie blokowac watku wywolujacego wcale.
        String resolvedValue = futureResult.join();
        System.out.println("futureResult.join() -> " + resolvedValue + " (wartosc gotowa)");

        // Drugie get() dla tego samego klucza - future jest JUZ ukonczony (dokonany),
        // wiec zwracany jest natychmiast, bez ponownego liczenia.
        CompletableFuture<String> cachedFuture = asyncCache.get(5);
        System.out.println("Drugie get(5) -> isDone() = " + cachedFuture.isDone()
                + ", wartosc = " + cachedFuture.join() + " (z cache, bez ponownego obliczenia)");

        // thenApply pozwala przeksztalcic wynik BEZ blokowania - lancuch dokona
        // sie, gdy future bazowy sie dokona; .join() na koncu tylko po to, zeby
        // main() poczekal na wynik przed zakonczeniem demo.
        String upperCased = asyncCache.get(6)
                .thenApply(String::toUpperCase)
                .join();
        System.out.println("get(6).thenApply(toUpperCase).join() -> " + upperCased);
    }

    /*
     * ============================================================
     * 🔹 RemovalListener + RemovalCause - CO SIE STALO Z WPISEM?
     * ============================================================
     * - removalListener((key, value, cause) -> ...) rejestruje callback
     *   wywolywany za KAZDYM razem, gdy wpis znika z cache - niezaleznie
     *   od tego, CZY zniknal recznie, przez wygasniecie, czy przez
     *   eviction z powodu limitu rozmiaru.
     * - RemovalCause (enum) mowi DOKLADNIE dlaczego:
     *   - EXPLICIT  - kod jawnie wywolal invalidate()/invalidateAll()
     *   - REPLACED  - wpis zostal NADPISANY nowa wartoscia (put/get z
     *     nowym wynikiem dla tego samego klucza)
     *   - SIZE      - wpis zostal evictowany, bo cache przekroczyl
     *     maximumSize/maximumWeight
     *   - EXPIRED   - wpis wygasl wg polityki czasowej (expireAfterWrite/
     *     expireAfterAccess)
     *   - COLLECTED - wpis zostal zebrany przez Garbage Collector (przy
     *     uzyciu slabych/miekkich referencji - weakKeys()/weakValues())
     * - cause.wasEvicted() to wygodny skrot: true dla SIZE/EXPIRED/
     *   COLLECTED (cache "wymusil" usuniecie), false dla EXPLICIT/REPLACED
     *   (usuniecie bylo swiadomym dzialaniem kodu).
     */
    private static void demonstrateRemovalListener() throws InterruptedException {
        System.out.println("\n=== RemovalListener + RemovalCause - dlaczego wpis zniknal z cache ===");

        var removalLog = new java.util.concurrent.ConcurrentLinkedQueue<String>();
        com.github.benmanes.caffeine.cache.RemovalListener<Integer, String> loggingListener =
                (Integer key, String value, RemovalCause cause) ->
                        removalLog.add("klucz=" + key + ", wartosc=" + value
                                + ", przyczyna=" + cause + ", wymuszone=" + cause.wasEvicted());

        // Kazdy scenariusz dostaje WLASNY, izolowany cache, zeby przyczyny
        // usuniecia nie mieszaly sie ze soba (latwiej zobaczyc "1 operacja = 1 przyczyna").

        // 1) EXPLICIT - reczne invalidate()
        var explicitCache = Caffeine.newBuilder().removalListener(loggingListener).<Integer, String>build();
        explicitCache.put(1, "jeden");
        explicitCache.invalidate(1);

        // 2) REPLACED - nadpisanie tego samego klucza nowa wartoscia
        var replacedCache = Caffeine.newBuilder().removalListener(loggingListener).<Integer, String>build();
        replacedCache.put(2, "dwa-stare");
        replacedCache.put(2, "dwa-nowe");

        // 3) SIZE - przekroczenie maximumSize(3) przez wstawienie wielu kluczy
        var sizeCache = Caffeine.newBuilder().maximumSize(3).removalListener(loggingListener).<Integer, String>build();
        for (int key = 10; key <= 14; key++) {
            sizeCache.put(key, "wartosc-" + key);
        }
        sizeCache.cleanUp(); // wymusza natychmiastowe przetworzenie zalegajacych evictów

        // 4) EXPIRED - wpis wygasajacy wg expireAfterWrite
        var expiredCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(150))
                .removalListener(loggingListener)
                .<Integer, String>build();
        expiredCache.put(99, "wygasnie");
        Thread.sleep(250);
        expiredCache.getIfPresent(99); // odczyt "dotyka" cache i pozwala zauwazyc wygasniecie
        expiredCache.cleanUp(); // Caffeine normalnie sprzata "leniwie", przy okazji innych operacji

        System.out.println("Zarejestrowane zdarzenia usuniecia (RemovalListener), po jednym scenariuszu na przyczyne:");
        for (String entry : removalLog) {
            System.out.println("  - " + entry);
        }
    }

    /**
     * Symuluje kosztowne obliczenie kwadratu (np. skomplikowana operacja
     * matematyczna albo wywolanie zewnetrznego serwisu).
     */
    private static int expensiveSquare(int input) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return input * input;
    }

    /**
     * Symuluje odczyt nazwy uzytkownika z bazy danych (kosztowna operacja I/O).
     */
    private static String fetchUserNameFromDatabase(String userId) {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Uzytkownik-" + userId;
    }
}
