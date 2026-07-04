package com.example.javaquest._05_multithreading.Lesson07_RaceCondition;

public class _Exercises_Lesson07_RaceCondition {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicRaceReplication {
        /*
         * 🧪 Zadanie 1:
         * Powtórz eksperyment z lekcji: 4 wątki, każdy inkrementujący wspólny,
         * niezsynchronizowany licznik (jak Counter z lekcji) 100_000 razy.
         * Wypisz oczekiwany wynik (400_000) i rzeczywisty wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TwoThreadsSimpleCounter {
        /*
         * 🧪 Zadanie 2:
         * 2 wątki, każdy inkrementujący wspólny licznik (klasa Counter z lekcji)
         * 200_000 razy. Wypisz oczekiwany (400_000) i rzeczywisty wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SingleThreadNoRace {
        /*
         * 🧪 Zadanie 3:
         * Dla porównania: TEN SAM licznik inkrementowany przez JEDEN wątek
         * 500_000 razy. Potwierdź (println), że wynik ZAWSZE się zgadza, bo
         * brak jest współbieżnego dostępu do danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IncreasingThreadCountObservation {
        /*
         * 🧪 Zadanie 4:
         * Uruchom eksperyment z rosnącą liczbą wątków (2, 4, 8), każdy
         * inkrementujący ten sam licznik 100_000 razy. Wypisz oczekiwany i
         * rzeczywisty wynik dla każdej konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RepeatExperimentMultipleRuns {
        /*
         * 🧪 Zadanie 5:
         * Powtórz DOKŁADNIE ten sam eksperyment (4 wątki x 100_000 inkrementacji)
         * 5 razy w pętli, za każdym razem tworząc NOWY obiekt Counter. Wypisz
         * wyniki wszystkich 5 powtórzeń i pokaż, że mogą się różnić między sobą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DecrementRaceCondition {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj klasę z metodą decrement() (value--). Uruchom 4 wątki
         * dekrementujące wspólną wartość startową (2_000_000) o 500_000 każdy.
         * Sprawdź, czy wynik końcowy to dokładnie 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MixedIncrementDecrementRace {
        /*
         * 🧪 Zadanie 7:
         * 2 wątki inkrementujące i 2 wątki dekrementujące (każdy 200_000 razy)
         * wspólny licznik startujący od 0. Oczekiwany wynik to 0 (bo +i -i się
         * równoważą) – sprawdź rzeczywisty wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_LostUpdateCount {
        /*
         * 🧪 Zadanie 8:
         * Dla konfiguracji 4 wątki x 250_000 inkrementacji zmierz DOKŁADNIE,
         * ile inkrementacji zostało "zgubionych" (expected - actual). Wypisz
         * liczbę i procent zgubionych aktualizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SharedListSizeRace {
        /*
         * 🧪 Zadanie 9:
         * Zamiast licznika int, użyj zwykłej, niezsynchronizowanej ArrayList<Integer>.
         * 4 wątki dodają po 10_000 elementów każdy (add()). Sprawdź, czy finalny
         * size() zgadza się z oczekiwanym 40_000 (ArrayList też nie jest
         * bezpieczny wątkowo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TwoCountersOneSharedOneNot {
        /*
         * 🧪 Zadanie 10:
         * Uruchom eksperyment z DWOMA licznikami jednocześnie: jednym
         * współdzielonym (4 wątki na tym samym obiekcie Counter) i jednym
         * "prywatnym" per-wątek (każdy wątek ma WŁASNY Counter, sumowany na
         * końcu przez main po joinach). Porównaj poprawność obu podejść.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PerThreadCounterThenSum {
        /*
         * 🧪 Zadanie 11:
         * Rozwiąż race condition BEZ synchronized/Atomic (te poznamy w kolejnych
         * lekcjach): daj każdemu z 4 wątków WŁASNY, lokalny licznik zapisywany
         * TYLKO do swojego indeksu współdzielonej tablicy int[4] (bez współdzielenia
         * zapisu). Po join wszystkich zsumuj wyniki cząstkowe i potwierdź, że
         * wynik ZAWSZE się zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RaceConditionSeverityVsIncrementCount {
        /*
         * 🧪 Zadanie 12:
         * Zbadaj, jak liczba inkrementacji na wątek (1000, 10_000, 100_000,
         * 1_000_000) wpływa na "widoczność" race condition dla stałej liczby
         * 4 wątków. Wypisz tabelę (liczba inkrementacji, oczekiwany, rzeczywisty,
         * czy się zgadza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RaceConditionSeverityVsThreadCount {
        /*
         * 🧪 Zadanie 13:
         * Zbadaj wpływ liczby wątków (1, 2, 4, 8, 16) na wynik przy stałej
         * liczbie inkrementacji (100_000 na wątek). Wypisz tabelę wyników
         * (liczba wątków, oczekiwany, rzeczywisty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SharedStringBuilderRace {
        /*
         * 🧪 Zadanie 14:
         * 4 wątki dopisują (append) po 1000 znaków (jedną, ustaloną literę na
         * wątek: 'A', 'B', 'C', 'D') do wspólnego, niezsynchronizowanego
         * StringBuildera. Sprawdź, czy finalna długość (length()) zgadza się z
         * oczekiwaną (4000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_HashMapPutRace {
        /*
         * 🧪 Zadanie 15:
         * 4 wątki wstawiają (put) różne klucze (każdy wątek swój zakres kluczy,
         * np. wątek i wstawia klucze i*1000..i*1000+999) do wspólnej,
         * niezsynchronizowanej HashMap<Integer,Integer>. Sprawdź finalny size()
         * i skomentuj (println), jakie dodatkowe ryzyko istniałoby, gdyby wątki
         * wstawiały te SAME klucze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BankAccountRaceSimulation {
        /*
         * 🧪 Zadanie 16:
         * Zamodeluj "konto bankowe" (klasa z polem saldo, metodą wplac(int) BEZ
         * synchronizacji). 4 wątki wykonują na tym samym koncie 100_000 wywołań
         * wplac(1) każdy. Sprawdź, czy finalne saldo zgadza się z oczekiwanym
         * (400_000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RaceConditionWithSleepAmplification {
        /*
         * 🧪 Zadanie 17:
         * Dodaj sztucznie krótkie opóźnienie MIĘDZY odczytem a zapisem w metodzie
         * increment() (odczytaj wartość do zmiennej lokalnej, Thread.sleep(1),
         * potem zapisz) – zastosuj to dla NIEWIELKIEJ liczby iteracji (20 na
         * wątek), by "wymusić" i uwidocznić interleaving przy zaledwie 2 wątkach.
         * Porównaj wynik z wersją bez sztucznego opóźnienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_VisibilityVsRaceDistinctionNote {
        /*
         * 🧪 Zadanie 18:
         * Powtórz podstawowy eksperyment licznika (2 wątki x 200_000 inkrementacji)
         * z jasnym komentarzem w kodzie wyjaśniającym, że demonstrujemy TU race
         * condition (utratę aktualizacji przy odczyt-modyfikuj-zapisz), a NIE
         * problem widoczności danych między wątkami (visibility – osobny temat
         * kolejnej lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RaceConditionReportBuilder {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę runRaceExperiment(int threadCount, int incrementsPerThread)
         * zwracającą String z raportem (oczekiwany, rzeczywisty, liczba zgubionych
         * aktualizacji, czy wykryto race condition). Wywołaj ją dla 3 różnych
         * konfiguracji i wypisz wszystkie raporty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareArrayVsSingleCounterRace {
        /*
         * 🧪 Zadanie 20:
         * Porównaj dla 8 wątków x 50_000 inkrementacji dwa podejścia: (a) wszystkie
         * piszą do JEDNEGO wspólnego licznika (race), (b) każdy pisze do WŁASNEGO
         * pola w tablicy long[8], a na końcu main sumuje (brak race). Zmierz też
         * czas obu podejść i wypisz porównanie poprawności ORAZ czasu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RaceConditionStatisticalAnalysis {
        /*
         * 🧪 Zadanie 21:
         * Uruchom eksperyment (4 wątki x 100_000 inkrementacji) 20 razy pod rząd
         * (nowy Counter za każdym razem). Zbierz wszystkie 20 wyników do listy,
         * policz ile razy wynik był poprawny, a ile błędny, oraz średnie
         * odchylenie od oczekiwanej wartości – wypisz statystyczny raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CombiningRunnableAndThreadClassInRace {
        /*
         * 🧪 Zadanie 22:
         * Powtórz eksperyment z race condition, ale połowę "atakujących" wątków
         * zaimplementuj jako klasy implements Runnable (Lesson03), a drugą
         * połowę jako klasy extends Thread (Lesson02) – obie grupy inkrementują
         * TEN SAM wspólny licznik. Potwierdź, że mechanizm tworzenia wątku nie
         * ma znaczenia dla wystąpienia race condition.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RaceConditionWithNamedThreadsAndPriorities {
        /*
         * 🧪 Zadanie 23:
         * Uruchom 6 wątków o różnych nazwach i priorytetach (Lesson05)
         * inkrementujących wspólny licznik (100_000 razy każdy). Sprawdź, czy
         * różne priorytety mają jakikolwiek wpływ na to, czy race condition
         * wystąpi, i skomentuj wynik (println) w świetle wiedzy, że priorytet
         * to tylko sugestia dla schedulera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MultiFieldSharedObjectRace {
        /*
         * 🧪 Zadanie 24:
         * Zamodeluj obiekt z dwoma powiązanymi polami (sumaX i licznik, gdzie
         * sumaX += wartosc i licznik++ powinny być spójne razem), modyfikowany
         * przez 4 wątki (każdy dodaje ustaloną wartość, np. swój numer+1,
         * 100_000 razy) BEZ synchronizacji. Sprawdź, czy na końcu invariant
         * (sumaX == licznik * wartosc, dla stałej wartości) jest zachowany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RaceFreeAlternativeUsingArrayPartitioning {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj w pełni race-free rozwiązanie: policz, ile spośród
         * 10_000_000 losowych liczb w tablicy int[] jest parzystych, dzieląc
         * tablicę na N = availableProcessors() fragmentów, każdy liczony w
         * osobnym wątku do WŁASNEGO pola wyniku. Zsumuj wyniki po joinach i
         * porównaj z wersją liczoną sekwencyjnie (muszą być identyczne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RaceConditionInSharedMaxFinder {
        /*
         * 🧪 Zadanie 26:
         * 4 wątki próbują zaktualizować wspólne pole "aktualne maksimum" (metoda
         * updateIfGreater(int) sprawdzająca if (value > currentMax) currentMax =
         * value, BEZ synchronizacji) na podstawie różnych zakresów losowych liczb.
         * Zademonstruj, że mimo prostoty logiki, także tu może dojść do utraty
         * aktualizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RaceConditionThroughputVsCorrectnessTradeoff {
        /*
         * 🧪 Zadanie 27:
         * Zmierz czas wykonania race-prone (współdzielony licznik) oraz
         * race-free (per-wątek + suma) wersji tego samego zadania (4 wątki x
         * 1_000_000 inkrementacji). Wypisz oba czasy i skomentuj, że wersja
         * "bezpieczna" (bez współdzielenia zapisu) może być też szybsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DetectRaceViaRepeatedRunsAutomatically {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę detectRaceCondition(int threadCount, int incrementsPerThread,
         * int repetitions) uruchamiającą eksperyment WIELOKROTNIE i zwracającą
         * boolean (czy choć raz wynik był niepoprawny). Użyj jej dla kilku
         * konfiguracji i wypisz, dla których udało się "złapać" race condition
         * w podanej liczbie powtórzeń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RaceConditionOnObjectReferenceSwap {
        /*
         * 🧪 Zadanie 29:
         * Zamodeluj scenariusz z niezsynchronizowanym polem-referencją (String
         * aktualnyStatus), kilka wątków ustawiających różne wartości statusu
         * w pętli (50_000 razy każdy). Na końcu sprawdź, jaka wartość "wygrała"
         * (ostatnia widoczna) i skomentuj, że dla prostego przypisania referencji
         * (bez odczyt-modyfikuj-zapis) nie ma klasycznego "lost update", ale
         * kolejność jest wciąż niedeterministyczna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullRaceConditionExperimentSuite {
        /*
         * 🧪 Zadanie 30:
         * Połącz wszystkie techniki z tej lekcji w jeden "suite" eksperymentów:
         * (a) podstawowy race na liczniku, (b) wersja race-free przez
         * partycjonowanie, (c) statystyka wielu powtórzeń, (d) porównanie czasu
         * obu podejść. Zbuduj i wypisz kompletny raport końcowy podsumowujący
         * wszystkie 4 podeksperymenty jednym czytelnym zestawieniem.
         */
        public static void main(String[] args) { }
    }
}
