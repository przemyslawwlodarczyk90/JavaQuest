package com.example.javaquest._13_libraries.Lesson11_GuavaPreconditionsAndCache;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class _Lesson11_GuavaPreconditionsAndCache {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: GUAVA - PRECONDITIONS I CACHE ===");

        /*
         * ============================================================
         * 📦 DWA NIEZALEŻNE TEMATY W JEDNEJ LEKCJI
         * ============================================================
         * - Lekcje 9-10 pokazywały kolekcje Guavy (niemutowalne,
         *   Multimap/Multiset/BiMap/Table). Ta lekcja przechodzi do
         *   DWÓCH innych, zupełnie niezwiązanych ze sobą narzędzi z tej
         *   samej biblioteki - łączy je tylko to, że obydwa są bardzo
         *   często spotykane w praktyce:
         *     1. com.google.common.base.Preconditions - walidacja
         *        argumentów/stanu metod (checkNotNull, checkArgument,
         *        checkState, checkElementIndex, checkPositionIndex),
         *     2. com.google.common.cache - "starszy", ale wciąż szeroko
         *        używany cache in-memory (CacheBuilder/LoadingCache/
         *        CacheLoader), zanim ten sam autor napisał jego
         *        następcę - Caffeine (poznasz go w Lesson27 tego
         *        rozdziału).
         */
        System.out.println("\nTa lekcja: Preconditions (walidacja argumentow) + Guava Cache (cache in-memory).");

        /*
         * ============================================================
         * 🔹 PRECONDITIONS.CHECKNOTNULL - WALIDACJA "NIE NULL"
         * ============================================================
         * - Preconditions.checkNotNull(referencja) rzuca
         *   NullPointerException, jeśli argument jest null - w
         *   przeciwnym razie ZWRACA tę samą referencję, co pozwala
         *   użyć go "w locie" (np. bezpośrednio w przypisaniu do pola).
         * - To dokładnie ten sam efekt, co ręczne
         *   `if (x == null) throw new NullPointerException(...)`, ale w
         *   JEDNEJ linijce, czytelniej i spójnie w całym projekcie.
         * - Od Javy 7 istnieje odpowiednik w JDK:
         *   java.util.Objects.requireNonNull(...) - w NOWYM kodzie
         *   często wystarczy sam JDK, bez Guavy. Preconditions ma sens
         *   głównie tam, gdzie projekt i tak już zależy od Guavy (np.
         *   żeby zachować spójny styl walidacji z checkArgument/
         *   checkState poniżej, których JDK nie ma wcale).
         */
        demonstrateCheckNotNull();

        /*
         * ============================================================
         * 🔹 CHECKARGUMENT I CHECKSTATE - WALIDACJA WARUNKÓW
         * ============================================================
         * - Preconditions.checkArgument(warunek) rzuca
         *   IllegalArgumentException, gdy warunek jest FALSE - służy do
         *   walidacji ARGUMENTÓW przekazanych do metody (np. "wiek nie
         *   może być ujemny").
         * - Preconditions.checkState(warunek) rzuca IllegalStateException,
         *   gdy warunek jest FALSE - służy do walidacji STANU OBIEKTU
         *   (np. "nie można wypłacić środków z zamkniętego konta").
         *   Różnica jest semantyczna: checkArgument mówi "TY (wywołujący)
         *   podałeś złe dane", checkState mówi "OBIEKT jest w złym
         *   stanie, żeby wykonać tę operację teraz" - dobór właściwego
         *   wyjątku ułatwia diagnozę, KTO popełnił błąd.
         * - Żadna z tych dwóch metod NIE MA odpowiednika w czystym JDK -
         *   to jeden z głównych powodów, dla których Preconditions
         *   wciąż jest chętnie używane, mimo że checkNotNull już ma
         *   swój odpowiednik (Objects.requireNonNull).
         */
        demonstrateCheckArgumentAndCheckState();

        /*
         * ============================================================
         * 🔍 KOMUNIKATY BŁĘDÓW Z PLACEHOLDERAMI %s
         * ============================================================
         * - Każda z metod check*(...) przyjmuje OPCJONALNY komunikat
         *   błędu jako drugi (i kolejne) argument, z placeholderami %s,
         *   które są podstawiane WARTOŚCIAMI przekazanymi po komunikacie
         *   - podobnie do String.format, ale prościej (tylko %s, żadnych
         *   %d/%f - Preconditions samo wywoła .toString() na każdym
         *   argumencie).
         * - KLUCZOWA zaleta wydajnościowa: komunikat i jego argumenty są
         *   przekazywane jako OSOBNE parametry (nie String już
         *   POSKLEJANY przez +), więc konkatenacja stringów wykonuje się
         *   TYLKO wtedy, gdy warunek faktycznie zawiedzie. Gdyby napisać
         *   `checkArgument(warunek, "Zly wiek: " + wiek)`, konkatenacja
         *   `"Zly wiek: " + wiek` wykonałaby się PRZY KAŻDYM wywołaniu
         *   metody, nawet gdy warunek jest spełniony (bo Java buduje
         *   argumenty PRZED wywołaniem metody) - drobny, ale realny
         *   koszt przy metodach wywoływanych bardzo często.
         */
        demonstrateErrorMessagesWithPlaceholders();

        /*
         * ============================================================
         * 🔹 CHECKELEMENTINDEX I CHECKPOSITIONINDEX - INDEKSY
         * ============================================================
         * - checkElementIndex(index, size) waliduje indeks użyty do
         *   DOSTĘPU DO ELEMENTU (np. list.get(index)) - poprawny zakres
         *   to [0, size) (size WYŁĄCZNIE, bo to już poza tablicą).
         * - checkPositionIndex(index, size) waliduje indeks użyty jako
         *   POZYCJA (np. do wstawiania - list.add(index, x) albo
         *   list.subList(0, index)) - poprawny zakres to [0, size]
         *   (size WŁĄCZNIE - wstawienie "na końcu" jest poprawne).
         * - Obie rzucają IndexOutOfBoundsException z gotowym, czytelnym
         *   komunikatem (np. "index (5) must be less than size (3)"),
         *   bez potrzeby ręcznego pisania takiego komunikatu za każdym
         *   razem.
         */
        demonstrateIndexChecks();

        /*
         * ============================================================
         * 📌 KIEDY W OGÓLE UŻYWAĆ PRECONDITIONS
         * ============================================================
         * - Najczęściej na SAMYM POCZĄTKU metody publicznej (konstruktora,
         *   settera, metody biznesowej) - "fail fast": jeśli dane
         *   wejściowe są złe, lepiej rzucić czytelny wyjątek OD RAZU,
         *   niż pozwolić metodzie kontynuować z niepoprawnym stanem i
         *   dostać niezrozumiały błąd gdzieś głębiej w kodzie.
         * - To osobny temat od Bean Validation (@NotNull, @Min itp. -
         *   patrz _12_hibernate/Lesson28_BeanValidationIntegration),
         *   które działa deklaratywnie na polach encji/DTO przez
         *   adnotacje. Preconditions to walidacja IMPERATYWNA, wprost w
         *   kodzie metody - dobra tam, gdzie nie ma frameworka Bean
         *   Validation albo walidacja zależy od logiki, której nie da
         *   się łatwo wyrazić adnotacją.
         */
        System.out.println("\nPreconditions = 'fail fast' na poczatku metody - czytelny wyjatek zamiast bledu gdzies glebiej.");

        /*
         * ============================================================
         * 📦 GUAVA CACHE - CACHEBUILDER I LOADINGCACHE
         * ============================================================
         * - Guava Cache (pakiet com.google.common.cache) to cache
         *   in-memory ogólnego przeznaczenia - zapamiętuje wyniki
         *   KOSZTOWNYCH operacji (obliczenia, zapytania do bazy/API), by
         *   nie powtarzać ich przy kolejnych, identycznych zapytaniach.
         *   To DOKŁADNIE ten sam pomysł, co Caffeine z Lesson27 tego
         *   rozdziału - i to NIE przypadek: Caffeine napisał TEN SAM
         *   AUTOR (Ben Manes), jako NOWOCZEŚNIEJSZEGO, szybszego
         *   następcę Guava Cache, z celowo bardzo podobnym API.
         * - Guava Cache jest STARSZE (część Guavy od dawna) i wciąż
         *   szeroko spotykane w istniejących projektach - dlatego warto
         *   je znać, nawet jeśli w NOWYM kodzie Caffeine jest zwykle
         *   lepszym wyborem (szybszy algorytm eviction W-TinyLFU,
         *   aktywny rozwój, API bez zależności od reszty Guavy).
         * - CacheBuilder.newBuilder() to punkt startowy - builder, na
         *   którym konfigurujesz limity/politykę, a .build(...) tworzy
         *   gotowy cache.
         */
        System.out.println("\n=== Guava Cache - starszy krewny Caffeine (Lesson27), ten sam autor ===");

        /*
         * ============================================================
         * 🔹 LOADINGCACHE I CACHELOADER - CACHE, KTÓRY SAM SIĘ UZUPEŁNIA
         * ============================================================
         * - Zwykły Cache<K,V> (jak w Caffeine z Lesson27) wymaga, żeby
         *   TY ręcznie sprawdzał getIfPresent(...) i w razie braku sam
         *   wywoływał kosztowną operację oraz .put(...) wyniku.
         * - LoadingCache<K,V> idzie o krok dalej: dostaje CacheLoader,
         *   czyli "przepis" JAK obliczyć wartość dla klucza, którego
         *   jeszcze nie ma w cache. Wtedy wystarczy WOŁAĆ .get(klucz) -
         *   jeśli wartość jest w cache, dostajesz ją natychmiast (hit);
         *   jeśli jej brak (miss), LoadingCache SAM wywołuje
         *   CacheLoader.load(klucz), zapamiętuje wynik i go zwraca -
         *   cała logika "sprawdź, a jak brak to policz i zapamiętaj"
         *   jest w JEDNYM miejscu (CacheLoader), zamiast rozsiana po
         *   każdym miejscu użycia cache.
         * - CacheLoader.load(key) może rzucić wyjątek (np. przy błędzie
         *   zewnętrznego zasobu) - wtedy .get(key) rzuca
         *   ExecutionException (checked) opakowujące oryginalną
         *   przyczynę - trzeba go obsłużyć/zadeklarować.
         */
        LoadingCache<Integer, Integer> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .build(new CacheLoader<Integer, Integer>() {
                    @Override
                    public Integer load(Integer key) {
                        System.out.println("  [CacheLoader.load] liczenie kosztownej wartosci dla klucza " + key);
                        return expensiveComputation(key);
                    }
                });

        System.out.println("\n=== LoadingCache.get(...) - pierwsze wywolanie liczy, kolejne czyta z cache ===");
        System.out.println("get(5) #1 (miss, CacheLoader.load wywolany): " + loadingCache.get(5));
        System.out.println("get(5) #2 (hit, z cache, BEZ ponownego liczenia): " + loadingCache.get(5));
        System.out.println("get(7) #1 (nowy klucz, znowu miss): " + loadingCache.get(7));

        /*
         * ============================================================
         * 🔍 GETUNCHECKED - GDY CACHELOADER NIE RZUCA CHECKED WYJĄTKU
         * ============================================================
         * - .get(key) wymusza obsługę checked ExecutionException, nawet
         *   jeśli CacheLoader.load(...) w ogóle nie deklaruje żadnego
         *   checked wyjątku (jak w naszym przykładzie wyżej) - bywa to
         *   uciążliwe.
         * - .getUnchecked(key) robi to samo, ale opakowuje ewentualny
         *   błąd w UNCHECKED UncheckedExecutionException - wygodne, gdy
         *   wiadomo, że CacheLoader nie rzuca checked wyjątków.
         */
        System.out.println("getUnchecked(9) (bez checked ExecutionException): " + loadingCache.getUnchecked(9));

        /*
         * ============================================================
         * 🔍 EVICTION: MAXIMUMSIZE, EXPIREAFTERWRITE, EXPIREAFTERACCESS
         * ============================================================
         * - Identyczne pojęcia jak w Caffeine (Lesson27) - Guava Cache
         *   był PIERWOWZOREM tego API:
         *     * maximumSize(long) - limit liczby wpisów, po przekroczeniu
         *       najmniej przydatne wpisy są usuwane (Guava Cache używa
         *       algorytmu zbliżonego do LRU, bez wyrafinowania W-TinyLFU
         *       z Caffeine),
         *     * expireAfterWrite(czas, jednostka) - wpis wygasa okreslony
         *       czas PO ZAPISIE, niezależnie od odczytów,
         *     * expireAfterAccess(czas, jednostka) - wpis wygasa okreslony
         *       czas PO OSTATNIM ODCZYCIE, każdy odczyt przedłuża życie.
         * - Różnica techniczna względem Caffeine: starsze API Guava
         *   Cache przyjmuje (długość, TimeUnit) zamiast java.time.Duration
         *   (Duration pojawił się w Javie 8, już PO zaprojektowaniu tego
         *   API).
         */
        System.out.println("\n=== ROZNICA: expireAfterWrite vs expireAfterAccess (Guava Cache) ===");

        CacheLoader<String, String> stringEchoLoader = CacheLoader.from(key -> "wartosc-dla-" + key);
        LoadingCache<String, String> writeExpiringCache = CacheBuilder.newBuilder()
                .expireAfterWrite(300, TimeUnit.MILLISECONDS)
                .build(stringEchoLoader);
        writeExpiringCache.get("klucz");

        for (int i = 0; i < 3; i++) {
            Thread.sleep(120);
            System.out.println("expireAfterWrite, odczyt #" + (i + 1) + " po ~"
                    + (120 * (i + 1)) + "ms: " + writeExpiringCache.getIfPresent("klucz"));
        }

        LoadingCache<String, String> accessExpiringCache = CacheBuilder.newBuilder()
                .expireAfterAccess(300, TimeUnit.MILLISECONDS)
                .build(stringEchoLoader);
        accessExpiringCache.get("klucz");

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
         * 🔹 REMOVALLISTENER - REAKCJA NA USUNIĘCIE WPISU
         * ============================================================
         * - RemovalListener<K,V> pozwala zarejestrować "callback", który
         *   zostanie wywołany za KAŻDYM razem, gdy wpis zniknie z cache -
         *   niezależnie od PRZYCZYNY (RemovalCause): SIZE (wypchnięty z
         *   powodu limitu rozmiaru), EXPIRED (wygasł czasowo),
         *   EXPLICIT (ręczne .invalidate(...)), REPLACED (nadpisany
         *   nową wartością dla tego samego klucza) i inne.
         * - Typowe zastosowanie: logowanie/audyt, zwolnienie zasobów
         *   powiązanych z wartością (np. zamknięcie połączenia
         *   sieciowego przechowywanego jako wartość w cache), albo
         *   metryki (ile wpisów wygasło vs zostało wypchniętych przez
         *   limit rozmiaru).
         */
        CacheLoader<Integer, String> entryLabelLoader = CacheLoader.from(key -> "wpis-" + key);
        RemovalListener<Integer, String> removalListener = notification ->
                System.out.println("  [RemovalListener] usunieto klucz=" + notification.getKey()
                        + " przyczyna=" + notification.getCause());
        LoadingCache<Integer, String> cacheWithListener = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(removalListener)
                .build(entryLabelLoader);

        System.out.println("\n=== RemovalListener - reakcja na kazde usuniecie wpisu (maximumSize=3) ===");
        for (int key = 1; key <= 5; key++) {
            cacheWithListener.get(key); // wstawienie 5 wpisow do cache o limicie 3 -> wymusza eviction
        }
        cacheWithListener.invalidate(5); // jawne, reczne usuniecie -> przyczyna EXPLICIT

        /*
         * ============================================================
         * 📌 STATYSTYKI CACHE: RECORDSTATS() + CACHE.STATS()
         * ============================================================
         * - Tak samo jak w Caffeine, Guava Cache domyślnie NIE zbiera
         *   statystyk (minimalny narzut) - trzeba je włączyć jawnie przez
         *   .recordStats() na builderze.
         * - cache.stats() zwraca com.google.common.cache.CacheStats z
         *   hitCount(), missCount(), hitRate(), evictionCount() -
         *   identyczny zestaw metryk co w Caffeine (Caffeine odziedziczył
         *   ten kształt API po Guava Cache).
         */
        CacheLoader<Integer, Integer> expensiveLoader =
                CacheLoader.from(_Lesson11_GuavaPreconditionsAndCache::expensiveComputation);
        LoadingCache<Integer, Integer> statsCache = CacheBuilder.newBuilder()
                .maximumSize(50)
                .recordStats()
                .build(expensiveLoader);

        for (int round = 0; round < 5; round++) {
            for (int key = 0; key < 10; key++) {
                statsCache.get(key);
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
         * - Preconditions (com.google.common.base): checkNotNull (NPE),
         *   checkArgument (IllegalArgumentException - zly argument),
         *   checkState (IllegalStateException - zly stan obiektu),
         *   checkElementIndex/checkPositionIndex (IndexOutOfBoundsException).
         *   Komunikaty z %s liczą się TYLKO gdy warunek zawiedzie -
         *   brak kosztu konkatenacji przy poprawnych wywołaniach.
         * - Guava Cache (com.google.common.cache): CacheBuilder buduje
         *   Cache/LoadingCache. LoadingCache + CacheLoader = cache, ktory
         *   SAM oblicza brakujace wartosci przy .get(...). Eviction:
         *   maximumSize, expireAfterWrite, expireAfterAccess (jednostki
         *   jako long+TimeUnit, nie Duration). RemovalListener reaguje
         *   na kazde usuniecie wpisu (SIZE/EXPIRED/EXPLICIT/REPLACED).
         *   recordStats()+stats() mierzy hitRate/evictionCount.
         * - Guava Cache i Caffeine (Lesson27) maja PRAWIE identyczne API
         *   nieprzypadkowo - ten sam autor napisal Caffeine jako
         *   nowszego, szybszego nastepce (W-TinyLFU zamiast LRU,
         *   aktywny rozwoj) - w NOWYM kodzie zwykle wybieraj Caffeine,
         *   ale Guava Cache wciaz warto rozpoznawac w istniejacych
         *   projektach.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void demonstrateCheckNotNull() {
        System.out.println("\n=== Preconditions.checkNotNull(...) ===");

        String imie = "Ala";
        String zweryfikowaneImie = Preconditions.checkNotNull(imie);
        System.out.println("checkNotNull na poprawnej wartosci -> zwraca ta sama referencje: " + zweryfikowaneImie);

        try {
            String brak = null;
            Preconditions.checkNotNull(brak);
        } catch (NullPointerException e) {
            System.out.println("checkNotNull(null) -> NullPointerException (jak spodziewano).");
        }
    }

    private static void demonstrateCheckArgumentAndCheckState() {
        System.out.println("\n=== Preconditions.checkArgument(...) i checkState(...) ===");

        try {
            int wiek = -5;
            Preconditions.checkArgument(wiek >= 0);
        } catch (IllegalArgumentException e) {
            System.out.println("checkArgument(wiek >= 0) dla wieku=-5 -> IllegalArgumentException.");
        }

        KontoBankowe konto = new KontoBankowe();
        konto.zamknij();
        try {
            konto.wyplac(100);
        } catch (IllegalStateException e) {
            System.out.println("checkState(!zamkniete) na zamknietym koncie -> IllegalStateException.");
        }
    }

    private static void demonstrateErrorMessagesWithPlaceholders() {
        System.out.println("\n=== Komunikaty bledow z placeholderami %s ===");

        int wiek = -3;
        try {
            Preconditions.checkArgument(wiek >= 0, "Wiek nie moze byc ujemny, otrzymano: %s", wiek);
        } catch (IllegalArgumentException e) {
            System.out.println("Komunikat z %s: " + e.getMessage());
        }

        String login = "ab";
        int minDlugosc = 3;
        try {
            Preconditions.checkArgument(login.length() >= minDlugosc,
                    "Login '%s' jest za krotki (min. %s znakow, otrzymano %s)",
                    login, minDlugosc, login.length());
        } catch (IllegalArgumentException e) {
            System.out.println("Komunikat z wieloma %s: " + e.getMessage());
        }
    }

    private static void demonstrateIndexChecks() {
        System.out.println("\n=== checkElementIndex vs checkPositionIndex ===");

        List<String> lista = List.of("a", "b", "c"); // size = 3

        int poprawnyIndeksElementu = Preconditions.checkElementIndex(2, lista.size());
        System.out.println("checkElementIndex(2, 3) - poprawny (ostatni element) -> " + poprawnyIndeksElementu);

        try {
            Preconditions.checkElementIndex(3, lista.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("checkElementIndex(3, 3) -> IndexOutOfBoundsException: " + e.getMessage());
        }

        int poprawnaPozycja = Preconditions.checkPositionIndex(3, lista.size());
        System.out.println("checkPositionIndex(3, 3) - poprawna (wstawienie NA KONCU) -> " + poprawnaPozycja);

        try {
            Preconditions.checkPositionIndex(4, lista.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("checkPositionIndex(4, 3) -> IndexOutOfBoundsException: " + e.getMessage());
        }
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

    /**
     * Prosta klasa domenowa demonstrująca checkState - operacja
     * wyplac(...) powinna być dozwolona TYLKO gdy konto NIE jest zamknięte.
     */
    private static class KontoBankowe {
        private boolean zamkniete = false;

        void zamknij() {
            zamkniete = true;
        }

        void wyplac(double kwota) {
            Preconditions.checkState(!zamkniete, "Nie mozna wyplacic srodkow z zamknietego konta");
        }
    }
}
