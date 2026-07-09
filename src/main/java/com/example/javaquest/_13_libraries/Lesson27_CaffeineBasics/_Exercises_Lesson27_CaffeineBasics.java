package com.example.javaquest._13_libraries.Lesson27_CaffeineBasics;

public class _Exercises_Lesson27_CaffeineBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BuildSimpleCacheAndPut {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj Cache<String, Integer> przez Caffeine.newBuilder()
         * .maximumSize(10).build(). Wstaw parę put("jablko", 5) i wypisz
         * cache.getIfPresent("jablko").
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetIfPresentMissingKey {
        /*
         * 🧪 Zadanie 2:
         * Na pustym Cache<String, Integer> (bez zadnego put) wywolaj
         * getIfPresent("brak") i wypisz wynik - zweryfikuj w komentarzu,
         * ze zwraca null, a nie wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PutMultipleAndCountEntries {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj Cache<Integer, String> i wstaw 5 par (1, "jeden") ...
         * (5, "piec"). Wypisz cache.estimatedSize() (przyblizona liczba
         * wpisow w cache).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InvalidateSingleKey {
        /*
         * 🧪 Zadanie 4:
         * Wstaw do cache pary (1, "A"), (2, "B"), (3, "C"). Wywolaj
         * cache.invalidate(2) i wypisz getIfPresent dla wszystkich trzech
         * kluczy, zeby pokazac, ze tylko klucz 2 zniknal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_InvalidateAllEntries {
        /*
         * 🧪 Zadanie 5:
         * Wstaw 4 pary do cache, wypisz estimatedSize() PRZED i PO
         * wywolaniu cache.invalidateAll() - zweryfikuj, ze cache jest
         * calkowicie pusty po invalidateAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MaximumSizeLimitsCacheGrowth {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj Cache<Integer, Integer> z maximumSize(20). Wstaw 100 par
         * (klucz -> klucz*klucz) w petli. Wypisz cache.estimatedSize() i
         * zweryfikuj w komentarzu, ze jest znacznie mniejsze niz 100 (cache
         * evictuje nadmiarowe wpisy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExpireAfterWriteBasicDemo {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj Cache<String, String> z expireAfterWrite(Duration.ofMillis(200)).
         * Wstaw put("klucz", "wartosc"), odczytaj od razu getIfPresent("klucz")
         * (powinno byc obecne), potem Thread.sleep(300) i odczytaj ponownie
         * (powinno byc null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExpireAfterAccessBasicDemo {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj Cache<String, String> z expireAfterAccess(Duration.ofMillis(200)).
         * Wstaw put("sesja", "aktywna"), w petli 3 razy odczytuj co 100ms
         * (kazdy odczyt powinien zwrocic wartosc), a potem odczekaj 300ms bez
         * odczytu i sprawdz, ze wpis wygasl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AsMapViewOfCache {
        /*
         * 🧪 Zadanie 9:
         * Wstaw 4 pary (String -> Integer) do cache. Uzyj cache.asMap()
         * zeby uzyskac widok ConcurrentMap i wypisz wszystkie wpisy w
         * petli for-each po entrySet().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RecordStatsHitAndMissCount {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj Cache<Integer, Integer> z recordStats(). Wykonaj
         * getIfPresent(1) (miss, bo pusty cache), potem put(1, 100) i
         * getIfPresent(1) dwa razy (2 hity). Wypisz stats().hitCount() i
         * stats().missCount().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CacheComputationResultsPattern {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode int fibonacciSlow(int n) symulujaca kosztowne
         * obliczenie (Thread.sleep(10) + zwrot n*n). Uzywajac
         * Cache<Integer, Integer> z recznym wzorcem "sprawdz getIfPresent,
         * jesli null - policz i put", policz wartosci dla n = 1..5 DWA RAZY
         * i zmierz System.currentTimeMillis() dla obu przebiegow - drugi
         * powinien byc znaczaco szybszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareExpireAfterWriteVsAccessUnderRepeatedReads {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj DWA cache z tym samym kluczem "dane": jeden z
         * expireAfterWrite(300ms), drugi z expireAfterAccess(300ms). W obu
         * odczytuj co 150ms przez 600ms (4 odczyty) - wypisz wynik kazdego
         * odczytu dla obu cache i zaobserwuj, ze expireAfterWrite wygasa
         * mimo odczytow, a expireAfterAccess nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_HitRatePercentageCalculation {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj Cache<Integer, Integer> z maximumSize(50) i recordStats().
         * Zasymuluj 200 losowych odczytow kluczy z zakresu 0-9 (Random),
         * przy kazdym missie policz wartosc i wstaw put. Wypisz
         * stats().hitRate() jako procent z dwoma miejscami po przecinku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_EvictionCountUnderSmallMaximumSize {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj Cache<Integer, String> z maximumSize(5) i recordStats().
         * Wstaw 50 unikalnych kluczy (0-49) w petli. Wypisz
         * stats().evictionCount() i zweryfikuj w komentarzu, ze jest bliskie
         * 45 (50 wstawien - 5 zmieszczonych w limicie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CacheOfExpensiveStringComputation {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode String reverseSlow(String s) symulujaca kosztowna
         * operacje (Thread.sleep(15) + odwrocenie napisu). Uzyj Cache<String,
         * String> z maximumSize(20) do zapamietania wynikow dla 5 slow, z
         * ktorych 2 sa powtorzone - wypisz, ile RAZYSZYBCIEJ zwrocil sie
         * wynik z cache (porownaj czas pierwszego i drugiego wywolania dla
         * tego samego slowa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CombineMaximumSizeAndExpireAfterWrite {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj Cache<String, Integer> LACZAC maximumSize(10) ORAZ
         * expireAfterWrite(Duration.ofMillis(250)) na jednym builderze.
         * Wstaw 3 wpisy, odczytaj od razu (obecne), odczekaj 300ms i
         * odczytaj ponownie (wygasle) - wypisz oba zestawy wynikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ManualCacheAsideForUserLookup {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj record User(int id, String name) oraz Map<Integer, User>
         * "baze danych" (co najmniej 5 uzytkownikow). Napisz metode
         * findUserById(int id) uzywajaca wzorca cache-aside (Cache<Integer,
         * User>: sprawdz cache, jesli brak - pobierz z mapy-bazy i wstaw do
         * cache) - wywolaj dla tych samych 3 id dwa razy i policz ile razy
         * "baza" zostala faktycznie odpytana (licznik w kodzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_InvalidateByPredicateUsingAsMap {
        /*
         * 🧪 Zadanie 18:
         * Wstaw do Cache<String, Integer> 10 par (klucz "item-0".."item-9",
         * wartosc = indeks). Uzywajac cache.asMap().keySet().removeIf(...)
         * usun WSZYSTKIE klucze, ktorych wartosc jest nieparzysta - wypisz
         * zawartosc cache przed i po operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareCacheVsNoCachePerformance {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj metode int slowSquare(int n) (Thread.sleep(10) + n*n).
         * Zmierz laczny czas wywolania jej 20 razy dla tych samych 4
         * powtarzajacych sie argumentow BEZ cache, a potem ten sam scenariusz
         * Z cache (Cache<Integer, Integer>) - wypisz oba czasy i procentowe
         * przyspieszenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CacheStatsSummaryReport {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj Cache<Integer, Integer> z maximumSize(30) i recordStats().
         * Zasymuluj mieszany ruch (czesc kluczy odczytywana wielokrotnie,
         * czesc raz) przez min. 300 operacji. Wypisz PELNY raport:
         * hitCount, missCount, hitRate (%), evictionCount, requestCount.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SimulateApiCallCacheWithTimeout {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode String fetchWeather(String miasto) symulujaca wolne
         * wywolanie zewnetrznego API (Thread.sleep(50)). Uzyj Cache<String,
         * String> z expireAfterWrite(Duration.ofSeconds(1)) - odpytaj o
         * pogode dla 3 miast, potem ponownie od razu (z cache), potem
         * odczekaj ponad sekunde i odpytaj jeszcze raz (ponowne "wywolanie
         * API") - wypisz, kiedy dane pochodzily z cache, a kiedy z "API"
         * (na podstawie zmierzonego czasu wywolania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LruLikeBehaviorObservationUnderPressure {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj Cache<Integer, String> z maximumSize(10) i recordStats().
         * Wstaw klucze 0-9 (pelny cache), potem WIELOKROTNIE (50 razy)
         * odczytuj TYLKO klucze 0-2 (czynisz je "goracymi"), a nastepnie
         * wstaw 10 nowych kluczy 100-109. Sprawdz getIfPresent dla kluczy
         * 0-2 i 3-9 - opisz w komentarzu obserwacje zwiazane z W-TinyLFU
         * (czeste klucze maja wieksza szanse przetrwac).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MultiLevelCacheKeyWithRecord {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj record CacheKey(String kategoria, int id) uzywany jako
         * klucz Cache<CacheKey, Double> (np. ceny produktow w roznych
         * walutach: kategoria="PLN"/"EUR", id=numer produktu). Wstaw kilka
         * wartosci i zweryfikuj, ze rowne rekordy (ta sama kategoria+id) traktowane
         * sa jako TEN SAM klucz (dzieki automatycznemu equals/hashCode
         * recordu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThreadSafetyOfConcurrentPuts {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj Cache<Integer, Integer> wspoldzielony przez 4 watki
         * (ExecutorService, _05_multithreading). Kazdy watek wstawia 1000
         * unikalnych kluczy (rozlaczne zakresy). Po zakonczeniu wszystkich
         * watkow (awaitTermination) wypisz cache.estimatedSize() i
         * zweryfikuj w komentarzu brak utraconych zapisow (Caffeine jest
         * thread-safe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConfigurableCacheFactoryMethod {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode statyczna <K, V> Cache<K, V> buildCache(long
         * maxSize, Duration ttl, boolean stats), ktora warunkowo dodaje
         * maximumSize/expireAfterWrite/recordStats do buildera w zaleznosci
         * od argumentow (np. ttl == null pomija expireAfterWrite). Sprawdz
         * ja na 2 roznych konfiguracjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CacheBackedMemoizedRecursiveFunction {
        /*
         * 🧪 Zadanie 26:
         * Uzyj Cache<Integer, Long> do memoizacji rekurencyjnej funkcji
         * Fibonacciego (bez tego cache byloby to wykladniczo wolne dla n=35+).
         * Napisz metode fib(int n), ktora przy KAZDYM wywolaniu najpierw
         * sprawdza cache, a jesli brak - liczy rekurencyjnie (wywolujac
         * ponownie fib) i zapisuje wynik. Zmierz czas fib(40).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TrackEvictionCauseWithSizeLimit {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj Cache<Integer, String> z maximumSize(15) i recordStats().
         * Wstaw 200 kluczy w petli. Co 50 wstawien wypisuj biezace
         * stats().evictionCount() i estimatedSize(), zeby zaobserwowac, jak
         * rosnie liczba evictowanych wpisow w czasie, podczas gdy rozmiar
         * cache pozostaje ograniczony do 15.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareTwoEvictionPoliciesSideBySide {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj DWA cache z maximumSize(5) i recordStats(): jeden ODCZYTYWANY
         * losowo (Random po 20 unikalnych kluczach), drugi odczytywany wg
         * wzorca "80% zapytan do tych samych 3 kluczy, 20% losowych" (typowy
         * rozklad w realnych aplikacjach). Porownaj hitRate() obu cache i
         * opisz w komentarzu, dlaczego drugi wzorzec daje wyzszy hit rate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CacheWarmupBeforeTraffic {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj "rozgrzewanie" (warm-up) cache: PRZED symulacja ruchu
         * wstaw z gory (put w petli) wyniki dla 10 najczestszych kluczy
         * (znanych z gory), a dopiero potem zasymuluj 100 losowych odczytow
         * kluczy z szerszego zakresu 0-19. Porownaj stats().hitRate() z
         * wariantem BEZ warm-up (ten sam ruch, ale pusty cache na starcie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCacheAsideServiceSimulation {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna symulacje: record Product(int id, String nazwa,
         * double cena), "baza danych" jako Map<Integer, Product> (20
         * produktow), Cache<Integer, Product> z maximumSize(10),
         * expireAfterWrite(Duration.ofSeconds(2)) i recordStats(). Napisz
         * serwis getProduct(id) w konwencji cache-aside. Zasymuluj 500
         * losowych zadan (Random, zakres id 0-19) z krotkimi opoznieniami,
         * a na koniec wypisz PELNY raport: hitRate, evictionCount, liczbe
         * faktycznych odczytow "bazy danych" (osobny licznik) oraz laczny
         * czas symulacji.
         */
        public static void main(String[] args) { }
    }
}
