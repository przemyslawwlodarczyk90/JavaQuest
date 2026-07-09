package com.example.javaquest._13_libraries.Lesson28_CaffeineLoadingAndAsyncCache;

public class _Exercises_Lesson28_CaffeineLoadingAndAsyncCache {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BuildLoadingCacheWithLambda {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj LoadingCache<Integer, Integer> przez Caffeine.newBuilder()
         * .build(key -> key * key). Wywolaj get(6) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetNeverReturnsNull {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj LoadingCache<String, Integer> z funkcja ladujaca
         * key -> key.length(). Wywolaj get("brakujacy") mimo ze nigdy nic
         * nie wstawiales przez put - wypisz wynik i zweryfikuj w komentarzu,
         * ze get() NIGDY nie zwraca null (w przeciwienstwie do getIfPresent).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SecondGetDoesNotRecompute {
        /*
         * 🧪 Zadanie 3:
         * Uzyj AtomicInteger jako licznika wywolan funkcji ladujacej.
         * Zbuduj LoadingCache<Integer, Integer>, wywolaj get(3) trzy razy
         * pod rzad i wypisz wartosc licznika - powinien wynosic 1 (tylko
         * pierwsze wywolanie faktycznie liczylo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetAllForMultipleKeys {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj LoadingCache<Integer, String> z funkcja ladujaca
         * key -> "wartosc-" + key. Uzyj getAll(List.of(1, 2, 3, 4, 5)) i
         * wypisz zwrocona Map<Integer, String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_InvalidateOnLoadingCache {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj LoadingCache<String, Integer>. Wywolaj get("a") (zaladuje),
         * potem invalidate("a"), a nastepnie ponownie get("a") - z pomoca
         * licznika (AtomicInteger) w funkcji ladujacej zweryfikuj, ze
         * wartosc zostala PONOWNIE obliczona po invalidate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ManualCacheVsLoadingCacheLineCount {
        /*
         * 🧪 Zadanie 6:
         * Napisz DWIE wersje tej samej funkcji "pobierz dlugosc slowa z
         * cache": jedna uzywajac zwyklego Cache (getIfPresent+put recznie),
         * druga uzywajac LoadingCache (get()). Wywolaj obie dla tego samego
         * slowa i porownaj liczbe linii kodu potrzebnych do obslugi cache
         * w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RefreshAfterWriteBasicSetup {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj LoadingCache<String, String> z
         * refreshAfterWrite(Duration.ofMillis(200)) i funkcja ladujaca
         * zwracajaca aktualny czas (String.valueOf(System.currentTimeMillis())).
         * Wywolaj get("czas") dwa razy - zaraz po sobie - i wypisz obie
         * wartosci (powinny byc takie same, bo wpis jest jeszcze swiezy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BuildAsyncLoadingCache {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj AsyncLoadingCache<Integer, Integer> przez
         * Caffeine.newBuilder().buildAsync(key -> key + 100). Wywolaj
         * get(5), wypisz typ zwroconego obiektu (getClass().getSimpleName())
         * a potem .join() zeby uzyskac i wypisac wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AsyncGetDoesNotBlockCaller {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj AsyncLoadingCache<Integer, String> z funkcja ladujaca
         * symulujaca Thread.sleep(200). Zmierz System.currentTimeMillis()
         * PRZED i ZARAZ PO wywolaniu asyncCache.get(1) (bez .join()) -
         * wypisz zmierzony czas i zweryfikuj w komentarzu, ze jest BLISKI
         * zeru (get() nie blokuje), a nie ~200ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RegisterSimpleRemovalListener {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj Cache<String, Integer> z removalListener((key, value,
         * cause) -> System.out.println(...)). Wstaw jeden wpis i wywolaj
         * invalidate() na nim - wypisz zawartosc callbacku (klucz, wartosc,
         * RemovalCause).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LoadingCacheForUserRepository {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj record User(int id, String name) oraz Map<Integer,
         * User> jako "baze danych" (min. 5 uzytkownikow). Zbuduj
         * LoadingCache<Integer, User> z funkcja ladujaca pobierajaca z tej
         * mapy (rzucajaca wyjatek jesli brak id). Pobierz 3 uzytkownikow
         * DWA razy kazdego i policz (licznikiem) ile razy "baza" byla
         * faktycznie odpytana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LoadingCacheWithMaximumSizeEviction {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj LoadingCache<Integer, Integer> z maximumSize(5) i funkcja
         * ladujaca key -> key * 2. Wywolaj get() dla kluczy 1-20 w petli.
         * Wypisz estimatedSize() na koniec i zweryfikuj w komentarzu, ze
         * jest ograniczone do okolo 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RefreshAfterWriteServesStaleValueOnce {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj LoadingCache<String, Integer> z licznikiem wersji
         * (AtomicInteger) jako wartoscia oraz refreshAfterWrite(Duration.
         * ofMillis(150)). Wywolaj get("v") (wersja 1), odczekaj 250ms, a
         * potem wywolaj get("v") ponownie NATYCHMIAST - wypisz zwrocona
         * wartosc i zmierzony czas trwania tego wywolania (powinno byc
         * SZYBKIE, bo zwraca stara wartosc, mimo ze jest przeterminowana).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RefreshAfterWriteEventuallyUpdates {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz Zadanie 13: po odczycie "stale" (przeterminowanej)
         * wartosci odczekaj JESZCZE 200ms (na dokonanie sie odswiezenia w
         * tle) i wywolaj get("v") ponownie - wypisz nowa wartosc i
         * zweryfikuj w komentarzu, ze roznie sie od pierwszej (odswiezenie
         * w tle sie dokonalo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AsyncLoadingCacheChainedTransformation {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj AsyncLoadingCache<Integer, String> z funkcja ladujaca
         * "produkt-" + key. Uzyj get(3).thenApply(String::toUpperCase)
         * .thenApply(s -> s + "!") i na koncu .join() - wypisz wynik
         * koncowy pipeline'u transformacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AsyncLoadingCacheMultipleParallelRequests {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj AsyncLoadingCache<Integer, Integer> z funkcja ladujaca
         * symulujaca Thread.sleep(100) + key*key. Wywolaj get() dla 5
         * roznych kluczy BEZ .join() od razu (zbierz futures do listy),
         * a dopiero POTEM zrob CompletableFuture.allOf(...).join() na
         * wszystkich naraz - zmierz czas i zweryfikuj w komentarzu, ze
         * jest BLISKI 100ms (rownolegle), a nie 500ms (sekwencyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RemovalListenerDistinguishesCauses {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj Cache<Integer, String> z maximumSize(3) i removalListener
         * zapisujacym kazde zdarzenie (klucz, RemovalCause) do listy.
         * Wywolaj sekwencje operacji powodujacych ROZNE przyczyny:
         * invalidate() (EXPLICIT), nadpisanie put() tym samym kluczem
         * (REPLACED), oraz wstawienie wielu kluczy ponad limit (SIZE).
         * Wypisz liste zdarzen pogrupowana wg RemovalCause.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RemovalListenerWithExpiredCause {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj Cache<String, String> z expireAfterWrite(Duration.
         * ofMillis(150)) i removalListener wypisujacym przyczyne usuniecia.
         * Wstaw wpis, odczekaj 250ms, wywolaj getIfPresent (zeby "dotknac"
         * cache) i cleanUp() - zweryfikuj, ze listener zostal wywolany z
         * RemovalCause.EXPIRED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WasEvictedHelperMethod {
        /*
         * 🧪 Zadanie 19:
         * Uzywajac cache z removalListener (jak w Zadaniu 17), dla kazdego
         * zarejestrowanego zdarzenia wypisz takze cause.wasEvicted() -
         * zweryfikuj w komentarzu ktore przyczyny zwracaja true (SIZE,
         * EXPIRED, COLLECTED), a ktore false (EXPLICIT, REPLACED).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_LoadingCacheThrowingExceptionOnMissingKey {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj LoadingCache<Integer, String> z funkcja ladujaca, ktora
         * RZUCA NoSuchElementException dla kluczy spoza zakresu 1-10.
         * Wywolaj get() dla klucza w zakresie (sukces) oraz poza zakresem
         * (w try-catch) - wypisz oba wyniki, w tym przechwycony wyjatek.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MemoizedRecursiveFibonacciWithLoadingCache {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj rekurencyjna funkcje Fibonacciego z pamiecia podreczna
         * przez LoadingCache<Integer, Long>, gdzie funkcja ladujaca dla n
         * wywoluje SAMA SIEBIE (przez referencje do cache) dla n-1 i n-2.
         * Zmierz czas policzenia fib(50) i zweryfikuj w komentarzu, ze
         * konczy sie natychmiast (dzieki cache), mimo ze naiwna rekurencja
         * bez cache bylaby niewykonalna w rozsadnym czasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CombineRefreshAfterWriteAndExpireAfterWrite {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj LoadingCache<String, Integer> LACZAC refreshAfterWrite
         * (Duration.ofMillis(100)) ORAZ expireAfterWrite(Duration.ofMillis(500))
         * na tym samym builderze (refreshAfterWrite krotszy niz
         * expireAfterWrite - typowy wzorzec: czesto odswiezaj w tle, ale
         * po dluzszym czasie calkowicie wygas). Zademonstruj odczyty w
         * roznych momentach czasu i opisz w komentarzu obserwowane
         * zachowanie (stale przez refresh, potem pelne wygasniecie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AsyncLoadingCacheWithCustomExecutor {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj wlasny Executor (np. Executors.newFixedThreadPool(2),
         * _05_multithreading) i przekaz go do Caffeine.newBuilder()
         * .executor(executor).buildAsync(...). Wywolaj kilka get() i po
         * .join() wszystkich zamknij executor (shutdown+awaitTermination).
         * Zweryfikuj w komentarzu po co moglibysmy chciec wlasny executor
         * zamiast domyslnego ForkJoinPool.commonPool().
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RemovalListenerTriggersResourceCleanup {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj klase "Zasob" (np. symulowane polaczenie) z metoda
         * close() wypisujaca komunikat. Zbuduj Cache<String, Zasob> z
         * maximumSize(2) i removalListener, ktory wywoluje close() na
         * KAZDYM usuwanym zasobie (niezaleznie od przyczyny). Wstaw 5
         * zasobow pod rzad i zweryfikuj, ze wszystkie evictowane zasoby
         * zostaly zamkniete.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AsyncLoadingCacheFallbackOnException {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj AsyncLoadingCache<Integer, String>, ktorego funkcja
         * ladujaca RZUCA wyjatek dla wybranych kluczy (np. parzystych).
         * Uzyj get(key).exceptionally(ex -> "wartosc-domyslna") zeby
         * obsluzyc blad bez wysypania calego programu - wywolaj dla
         * klucza dzialajacego i rzucajacego wyjatek, wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareLoadingCacheVsAsyncLoadingCacheThroughput {
        /*
         * 🧪 Zadanie 26:
         * Majac funkcje symulujaca Thread.sleep(50) na klucz, porownaj
         * calkowity czas pobrania 5 roznych kluczy: (a) sekwencyjnie przez
         * LoadingCache.get() w petli, (b) rownolegle przez
         * AsyncLoadingCache.get() (zbierz futures, potem allOf().join()).
         * Wypisz oba czasy calkowite i procentowe przyspieszenie wariantu
         * asynchronicznego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RemovalListenerStatisticsByCause {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj Cache<Integer, String> z maximumSize(5), expireAfterWrite
         * (Duration.ofMillis(150)) i removalListener zliczajacym zdarzenia
         * osobno dla kazdej RemovalCause (Map<RemovalCause, Integer> jako
         * licznik). Wygeneruj mieszanke operacji (invalidate, nadpisania,
         * wstawienia ponad limit, oczekiwanie na wygasniecie + cleanUp())
         * i wypisz koncowe zliczenia per przyczyna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullProductServiceWithLoadingCacheAndRefresh {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj record Product(int id, String nazwa, double cena) oraz
         * "baze danych" (Map<Integer, Product>, 15 produktow, z osobnym
         * licznikiem odczytow bazy). Zbuduj LoadingCache<Integer, Product>
         * z refreshAfterWrite(Duration.ofMillis(200)) i removalListener
         * logujacym kazde usuniecie. Zasymuluj 30 zadan o rozne id
         * (czesc powtorzonych, z opoznieniami przekraczajacymi czas
         * odswiezania) - na koniec wypisz liczbe faktycznych odczytow bazy
         * oraz log usuniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AsyncLoadingCacheWebLikeSimulation {
        /*
         * 🧪 Zadanie 29:
         * Zasymuluj "serwis webowy" - AsyncLoadingCache<String, String>
         * z funkcja ladujaca symulujaca wolne API (Thread.sleep(80)) dla
         * kluczy "endpoint-1".."endpoint-5". Wyslij 20 losowych "zadan"
         * (Random) BEZ czekania na kazde po kolei (zbierz futures), a na
         * koncu poczekaj na wszystkie (allOf().join()) i wypisz laczny
         * czas oraz liczbe unikalnych faktycznie wykonanych obliczen
         * (licznik w funkcji ladujacej) - powinno byc znaczaco mniej niz 20.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCachingLayerCombiningAllFeatures {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna warstwe cache dla "serwisu kursow walut":
         * LoadingCache<String, Double> (klucze "USD", "EUR", "GBP", ...)
         * LACZACY maximumSize(10), refreshAfterWrite(Duration.ofMillis(200)),
         * expireAfterWrite(Duration.ofSeconds(2)) oraz removalListener
         * logujacy kazde usuniecie z przyczyna. Funkcja ladujaca symuluje
         * wywolanie zewnetrznego API kursow (Thread.sleep + losowa
         * fluktuacja kursu). Zasymuluj min. 50 odczytow rozlozonych w
         * czasie (mieszanka czestych i rzadkich kluczy) i wypisz PELNY
         * raport na koniec: liczbe faktycznych wywolan "API", liczbe
         * odswiezen w tle, log usuniec pogrupowany wg przyczyny oraz
         * laczny czas symulacji.
         */
        public static void main(String[] args) { }
    }
}
