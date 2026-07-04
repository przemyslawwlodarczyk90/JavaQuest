package com.example.javaquest._05_multithreading.Lesson10_ThreadSafety;

public class _Exercises_Lesson10_ThreadSafety {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MutableCounterNotThreadSafe {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj klasę MutableCounter z polem `int value` i niesynchronizowaną metodą
         * increment(). Uruchom 2 wątki, każdy wywołujący increment() 50_000 razy
         * (join(10_000)). Wypisz oczekiwany i rzeczywisty wynik – potwierdź brak
         * thread-safety.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_LocalVariableNoSharing {
        /*
         * 🧪 Zadanie 2:
         * Napisz metodę, w której KAŻDY wątek liczy własną, LOKALNĄ zmienną `sum`
         * dodając do niej liczby 1..1000 w pętli (bez żadnego pola współdzielonego).
         * Uruchom to na 4 wątkach (join(5_000)), zbierz wyniki każdego wątku (np. do
         * tablicy wyników per-indeks) i potwierdź, że każdy wątek dostał identyczny,
         * poprawny wynik (500500).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImmutableRecordConcurrentReads {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj record Point(int x, int y) z metodą move(dx,dy) zwracającą NOWY Point.
         * Utwórz jeden współdzielony `Point p = new Point(1,2)`. Uruchom 4 wątki, każdy
         * czytający p.x()+p.y() 10_000 razy i sprawdzający, czy suma zawsze wynosi 3
         * (join(5_000)). Wypisz, czy wszystkie odczyty były spójne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImmutableMoneyLocalAccumulation {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj record Money(long cents) z metodą add(Money other) zwracającą NOWY
         * Money. Uruchom 3 wątki, każdy niezależnie sumujący (lokalnie, bez współdzielenia)
         * 1000 obiektów Money(100) poprzez add() i wypisujący swój finalny wynik
         * (join(5_000)) – potwierdź, że każdy wątek dostał 100_000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ClassifySnippetsThreadSafety {
        /*
         * 🧪 Zadanie 5:
         * Mając opisy 5 fragmentów kodu jako Stringi (np. "lokalna zmienna int w metodzie",
         * "statyczne pole int modyfikowane przez wiele wątków bez synchronizacji",
         * "record z polami final", "statyczna final lista List.of(...)",
         * "instancyjne pole ArrayList modyfikowane przez wiele wątków"), napisz metodę
         * klasyfikującą każdy jako "THREAD-SAFE" lub "NIEBEZPIECZNY" na podstawie reguły
         * stan+współdzielony+mutowalny i wypisz klasyfikację z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UnsafeArrayListWrapper {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj klasę UnsafeListWrapper opakowującą zwykłą ArrayList<Integer> metodą
         * add(int). Uruchom 4 wątki, każdy dodający 10_000 własnych, unikalnych liczb
         * (join(10_000)). Porównaj oczekiwany rozmiar (40_000) z size() – wypisz wynik
         * (może być niezgodny albo rzucić wyjątek – ArrayList nie jest thread-safe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImmutableConfigConsistentReads {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj record Config(String name, int maxRetries) z metodą withName(String)
         * zwracającą NOWĄ instancję. Uruchom 4 wątki czytające WSPÓLNĄ instancję Config
         * 10_000 razy każdy i sprawdzające, że oba pola są zawsze spójne (nigdy null,
         * nigdy "połowicznie" zbudowane). Wypisz wynik weryfikacji (join(5_000)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_LargerDiscrepancyDemo {
        /*
         * 🧪 Zadanie 8:
         * Powtórz demo MutableCounter z 8 wątkami x 50_000 increment() (join(10_000)).
         * Wypisz oczekiwany, rzeczywisty wynik i różnicę – skomentuj, jak rozbieżność
         * ma się do wyniku z Zadania 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_StatelessPureFunction {
        /*
         * 🧪 Zadanie 9:
         * Napisz statyczną, bezstanową metodę `int square(int n)`. Uruchom 4 wątki,
         * każdy wywołujący ją dla RÓŻNYCH zakresów liczb (np. 0-999, 1000-1999, itd.)
         * i weryfikujący poprawność każdego wyniku (join(5_000)). Wypisz, że wszystkie
         * wywołania dały matematycznie poprawne wyniki – kod bezstanowy jest trywialnie
         * thread-safe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MutableVsLocalSideBySide {
        /*
         * 🧪 Zadanie 10:
         * Uruchom obok siebie: (a) demo MutableCounter (4 wątki x 50_000 increment(),
         * błędne) oraz (b) 4 wątki liczące WŁASNE lokalne sumy scalane po join() przez
         * proste dodanie w main (poprawne). Wypisz oba wyniki i wskaż, który podejście
         * dało poprawny rezultat.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImmutableSnapshotDefensiveCopy {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj record ImmutableSnapshot(List<String> items) ze statyczną fabryką
         * `of(List<String> source)` używającą List.copyOf(source) wewnątrz. Zbuduj
         * zwykłą mutowalną ArrayList, przekaż ją do fabryki, a NASTĘPNIE zmodyfikuj
         * oryginalną listę (dodaj element). Wypisz zawartość snapshotu i potwierdź,
         * że pozostała niezmieniona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImmutablePathConcurrentReaders {
        /*
         * 🧪 Zadanie 12:
         * Używając record Point z Zadania 3, zdefiniuj record ImmutablePath(List<Point>
         * points) z metodą addPoint(Point) zwracającą NOWY ImmutablePath (List.copyOf +
         * dodany element). Jeden wątek co jakiś czas tworzy nowe wersje ścieżki (lokalnie),
         * a 3 wątki czytające WSPÓLNĄ, niezmienną instancję iterują po niej równolegle
         * bez błędów (join(5_000)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SharedMutableBooleanRiskCategory {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj klasę z polem `boolean flag` (bez volatile/synchronized) modyfikowanym
         * przez 2 wątki na przemian (500 razy każdy, join(5_000)). Sklasyfikuj (w
         * komentarzu/print) tę sytuację wg reguły stan+współdzielony+mutowalny i wypisz
         * finalną wartość flag jako ilustrację nieprzewidywalności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PerThreadLocalListMerge {
        /*
         * 🧪 Zadanie 14:
         * Przerób UnsafeListWrapper z Zadania 6 tak, by każdy z 4 wątków budował WŁASNĄ,
         * lokalną ArrayList<Integer> (10_000 elementów), a dopiero po join() main scala
         * wszystkie 4 listy w jedną (np. przez addAll). Wypisz finalny rozmiar i potwierdź
         * poprawność (40_000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImmutableScoreBoardDefensiveMap {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj record ScoreBoard(Map<String,Integer> scores) z konstruktorem
         * kompaktowym kopiującym mapę przez Map.copyOf. Uruchom 4 wątki odczytujące
         * WSPÓLNĄ instancję ScoreBoard (get dla ustalonych kluczy) 10_000 razy każdy
         * (join(5_000)) i potwierdź spójność wszystkich odczytów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SharedStringBuilderVsLocalBuilders {
        /*
         * 🧪 Zadanie 16:
         * Porównaj: (a) 4 wątki appendujące do JEDNEGO współdzielonego StringBuildera
         * (bez synchronizacji, po 5_000 razy krótki napis, join(5_000)) – sprawdź
         * finalną długość vs oczekiwaną, (b) 4 wątki budujące WŁASNE StringBuildery,
         * łączone (concat) po join() – sprawdź poprawność. Wypisz porównanie obu wyników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImmutableTemperatureConversion {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj record Temperature(double celsius) z metodami toFahrenheit()
         * i statyczną fabryką fromFahrenheit(double). Uruchom 4 wątki konwertujące
         * WSPÓLNĄ instancję Temperature tam i z powrotem 10_000 razy każdy, weryfikując
         * poprawność matematyczną każdej konwersji (join(5_000)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ThreadSafetyQuizSixSnippets {
        /*
         * 🧪 Zadanie 18:
         * Mając 6 opisanych jako Stringi scenariuszy kodu (analogicznie do Zadania 5,
         * ale inny zestaw – np. "final referencja do mutowalnej mapy", "record z polem
         * List bez defensywnej kopii", "lokalny StringBuilder w metodzie"), napisz metodę
         * klasyfikującą każdy jako thread-safe/nie i wypisz klasyfikację + krótkie
         * uzasadnienie dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AlmostImmutableArrayPitfall {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj record AlmostImmutable(int[] data) BEZ kopiowania tablicy
         * w konstruktorze. Utwórz instancję z tablicą {1,2,3}, a NASTĘPNIE zmodyfikuj
         * oryginalną tablicę z zewnątrz (data[0] = 999). Wypisz zawartość
         * AlmostImmutable.data() i pokaż, że "niemutowalność" została złamana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FixArrayWithDefensiveCopy {
        /*
         * 🧪 Zadanie 20:
         * Popraw record z Zadania 19, dodając kompaktowy konstruktor kopiujący tablicę
         * (Arrays.copyOf). Powtórz ten sam test (modyfikacja oryginalnej tablicy po
         * utworzeniu rekordu) i potwierdź, że tym razem wewnętrzny stan pozostaje
         * niezmieniony.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_UnsafeVsImmutableBankAccount {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj UnsafeBankAccount (mutowalne int balance, deposit() bez synchronizacji)
         * oraz alternatywne podejście z niemutowalnym record BankAccountSnapshot(int
         * balance) z metodą deposit() zwracającą NOWY snapshot (używane sekwencyjnie,
         * pojedynczo na wątek, wyniki scalane po join()). Uruchom 4 wątki x 20_000 wpłat
         * po 1 na obu podejściach i porównaj poprawność finalnego salda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImmutableAppendOnlyEventLog {
        /*
         * 🧪 Zadanie 22:
         * Zdefiniuj metodę withEvent(List<String> events, String event) zwracającą
         * `List.copyOf` z dodanym elementem (nowa lista, oryginał bez zmian). Uruchom
         * 4 wątki, z których każdy lokalnie (własny łańcuch referencji, bez współdzielenia)
         * dodaje 1000 zdarzeń i na końcu raportuje rozmiar swojej finalnej listy
         * (join(5_000)). Wyjaśnij (print), dlaczego ten wzorzec unika stanu współdzielonego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SharedReferenceWithoutVolatileRisk {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj record Price(java.math.BigDecimal value, java.time.Instant timestamp).
         * Zadeklaruj (BEZ volatile) `static Price current`. Jeden wątek co 50ms (5 razy)
         * podmienia current na nowy Price. Kilka wątków czytelników odczytuje current
         * co 10ms (join(3_000)) i loguje odczytane wartości. Skomentuj (print), że mimo
         * iż sam obiekt Price jest bezpieczny (niemutowalny), brak volatile na referencji
         * current teoretycznie może opóźnić widoczność zmian (nawiązanie do Lesson08).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThreadSafetyAuditorUtility {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę `boolean looksThreadSafe(boolean hasSharedState, boolean isMutable)`
         * implementującą regułę z lekcji (bezpieczny, jeśli brak stanu WSPÓŁDZIELONEGO
         * albo brak MUTOWALNOŚCI). Zbuduj tabelę 8 hipotetycznych scenariuszy (kombinacje
         * true/false plus opis) i wypisz werdykt dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImmutableCounterSequentialAccumulation {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj record ImmutableCounter(int value) z metodą increment() zwracającą
         * NOWĄ instancję (value+1). W JEDNYM wątku wykonaj sekwencyjną redukcję – zacznij
         * od ImmutableCounter(0) i wywołaj increment() 100_000 razy, za każdym razem
         * podmieniając zmienną lokalną na nowy wynik. Wypisz finalną wartość (100_000)
         * i skomentuj (print), dlaczego ten wzorzec nie zrównolegla się trywialnie
         * (kolejne wywołania muszą być sekwencyjne, bo każde zależy od poprzedniego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ProducerOnlyLocalListsMerged {
        /*
         * 🧪 Zadanie 26:
         * Uruchom 4 wątki, każdy budujący WŁASNĄ (nie współdzieloną) List<Integer>
         * z wynikami czystej funkcji (np. i*i dla i z ustalonego zakresu). Po join(5_000)
         * scal wszystkie listy sekwencyjnie w main i policz sumę wszystkich elementów.
         * Porównaj z sumą obliczoną bezpośrednio (bez wątków) dla tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FinalReferenceMutableContentsPitfall {
        /*
         * 🧪 Zadanie 27:
         * Zadeklaruj `static final List<Integer> sharedList = new ArrayList<>();` (final
         * NIE oznacza niemutowalności zawartości!). Uruchom 4 wątki wywołujące
         * sharedList.add() 10_000 razy każdy (join(10_000), bez synchronizacji). Sprawdź
         * finalny size() vs oczekiwane 40_000 i wypisz komentarz wyjaśniający, że final
         * chroni tylko referencję, nie zawartość obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ThreeImplementationsComparisonTable {
        /*
         * 🧪 Zadanie 28:
         * Uruchom pod tymi samymi parametrami (4 wątki x 50_000 operacji): (a)
         * UnsafeMutableCounter, (b) wariant per-thread-local-then-merged, (c) wariant
         * z pojedynczym wątkiem-zapisującym (tylko 1 wątek modyfikuje pole, pozostałe
         * tylko czytają na końcu). Wypisz tabelę wyników z poprawnością każdego wariantu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImmutableCacheDerivedNeverMutatesOriginal {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj record Cache(Map<String,String> data) z metodą withEntry(String k,
         * String v) zwracającą NOWY Cache (Map.copyOf + dodany wpis). Utwórz jeden
         * początkowy, współdzielony Cache. Uruchom 4 wątki, każdy niezależnie tworzący
         * własne pochodne wersje (withEntry) na bazie tego samego oryginału (join(5_000)).
         * Po zakończeniu potwierdź, że oryginalny współdzielony Cache pozostał niezmieniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MutableCounterTwoFixesCapstone {
        /*
         * 🧪 Zadanie 30:
         * Weź MutableCounter z Zadania 1. Zaimplementuj DWA rozwiązania BEZ użycia
         * `synchronized` (poznamy je w następnej lekcji): (A) eliminacja współdzielenia
         * – każdy z 4 wątków ma WŁASNY licznik, wyniki scalane (sumowane) po join();
         * (B) eliminacja mutowalności – niemutowalny record-based licznik, gdzie increment()
         * tworzy NOWY obiekt (używany sekwencyjnie w jednym wątku dla identycznego
         * obciążenia całkowitego). Uruchom oba warianty z tym samym obciążeniem
         * (4 x 50_000) i wypisz porównanie poprawności oraz krótki komentarz o kosztach
         * (alokacje obiektów w wariancie B).
         */
        public static void main(String[] args) { }
    }
}
