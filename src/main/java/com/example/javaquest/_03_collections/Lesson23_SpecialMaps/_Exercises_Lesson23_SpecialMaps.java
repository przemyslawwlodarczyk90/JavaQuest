package com.example.javaquest._03_collections.Lesson23_SpecialMaps;

public class _Exercises_Lesson23_SpecialMaps {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicEnumMap {
        /*
         * 🧪 Zadanie 1:
         * EnumMap<Day,String>: dodaj plan na MONDAY="Java", WEDNESDAY="Kolekcje",
         * FRIDAY="Streams". Wypisz całą mapę przez forEach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_EnumMapDefault {
        /*
         * 🧪 Zadanie 2:
         * Ten sam EnumMap<Day,String> co wyżej.
         * Wypisz getOrDefault(Day.TUESDAY, "Wolne") dla dnia bez wpisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_EnumMapIterationOrder {
        /*
         * 🧪 Zadanie 3:
         * EnumMap<Day,String>: wstaw wpisy w kolejności FRIDAY, MONDAY, WEDNESDAY.
         * Wypisz keySet() – sprawdź że kolejność iteracji odpowiada deklaracji enuma
         * (MONDAY, WEDNESDAY, FRIDAY), a nie kolejności wstawiania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BasicIdentityHashMap {
        /*
         * 🧪 Zadanie 4:
         * String s1 = new String("klucz"), s2 = new String("klucz") (dwie różne referencje).
         * IdentityHashMap<String,Integer>: put(s1,1), put(s2,2).
         * Wypisz rozmiar mapy (powinien być 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentityVsEquals {
        /*
         * 🧪 Zadanie 5:
         * Te same s1, s2 co w zadaniu 4.
         * Wstaw je do zwykłej HashMap<String,Integer> i porównaj rozmiar
         * (powinien być 1, bo equals() traktuje je jako ten sam klucz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BasicWeakHashMap {
        /*
         * 🧪 Zadanie 6:
         * WeakHashMap<Object,String>: dodaj 2 klucze (Object) z wartościami.
         * Wypisz rozmiar. Ustaw jedną referencję na null, wywołaj System.gc(),
         * wypisz rozmiar ponownie (może, ale nie musi, spaść do 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EnumMapValues {
        /*
         * 🧪 Zadanie 7:
         * EnumMap<Season,Integer> z liczbą dni wolnych w każdej porze roku
         * (SPRING=3, SUMMER=10, AUTUMN=2, WINTER=5).
         * Zsumuj wszystkie wartości (values()) i wypisz sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdentityHashMapSameRef {
        /*
         * 🧪 Zadanie 8:
         * IdentityHashMap<String,Integer>: String s = "abc";
         * put(s, 1), potem put(s, 2) (ta sama referencja).
         * Sprawdź że rozmiar wynosi 1, a wartość została nadpisana na 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WeakHashMapNullKey {
        /*
         * 🧪 Zadanie 9:
         * WeakHashMap<Object,String>: wstaw wpis z kluczem null i wartością "wartość dla null".
         * Sprawdź containsKey(null) i get(null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MapComparisonPrint {
        /*
         * 🧪 Zadanie 10:
         * Wypisz (println) krótkie porównanie: HashMap, EnumMap, IdentityHashMap,
         * WeakHashMap – po jednej linii z główną cechą każdej z nich.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EnumMapStateMachine {
        /*
         * 🧪 Zadanie 11:
         * Enum State {NEW, IN_PROGRESS, DONE, CANCELLED} (opisz w komentarzu jak dodać do projektu).
         * EnumMap<State, List<State>> transitions określająca dozwolone przejścia
         * (np. NEW→IN_PROGRESS, IN_PROGRESS→DONE/CANCELLED).
         * Napisz metodę canTransition(from, to) sprawdzającą dozwolone przejście.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_EnumMapGrouping {
        /*
         * 🧪 Zadanie 12:
         * Klasa Person(String name, Day workDay).
         * Lista 6 osób z różnymi dniami pracy.
         * Pogrupuj je w EnumMap<Day, List<Person>> – kto pracuje którego dnia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentityHashMapVisitedNodes {
        /*
         * 🧪 Zadanie 13:
         * Prosta klasa Node(int val, Node next) tworząca listę z cyklem
         * (ostatni next wskazuje z powrotem na wcześniejszy węzeł).
         * Użyj IdentityHashMap<Node,Boolean> do wykrycia cyklu przy przechodzeniu
         * (odwiedzony węzeł po referencji, nie po equals()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WeakHashMapCache {
        /*
         * 🧪 Zadanie 14:
         * Klasa SimpleCache opakowująca WeakHashMap<Object,String>.
         * Metody put(key,value), get(key).
         * Dodaj 3 wpisy, usuń silną referencję do jednego klucza,
         * wywołaj System.gc() i sprawdź czy wpis zniknął z cache.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EnumMapVsHashMapPerf {
        /*
         * 🧪 Zadanie 15:
         * Wykonaj 1 000 000 operacji get/put z kluczami typu Day
         * raz na EnumMap<Day,Integer>, raz na HashMap<Day,Integer>.
         * Zmierz czas (System.nanoTime()) i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_IdentityHashMapDeepCopyTracker {
        /*
         * 🧪 Zadanie 16:
         * Prosta klasa Box(int value) z referencją do innego Box (shared).
         * Napisz deepCopy(Box) używający IdentityHashMap<Box,Box> jako
         * mapy oryginał→kopia, żeby uniknąć duplikowania współdzielonych obiektów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_EnumMapPriorityQueues {
        /*
         * 🧪 Zadanie 17:
         * Enum Priority {LOW, MEDIUM, HIGH}.
         * EnumMap<Priority, Queue<String>> z osobną kolejką zadań dla każdego priorytetu.
         * Dodaj kilka zadań tekstowych do różnych priorytetów, następnie
         * obsłuż wszystkie zaczynając od HIGH do LOW.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WeakHashMapListenerRegistry {
        /*
         * 🧪 Zadanie 18:
         * Symulacja rejestru obserwatorów: WeakHashMap<Object,String> listeners,
         * gdzie kluczem jest obiekt-obserwator, a wartością jego nazwa.
         * Pokaż że po utracie silnej referencji do obserwatora i System.gc()
         * wpis może zniknąć – w przeciwieństwie do zwykłej HashMap, gdzie zostałby na zawsze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_EnumMapPermissionSystem {
        /*
         * 🧪 Zadanie 19:
         * Enum Role {ADMIN, EDITOR, VIEWER}.
         * EnumMap<Role, EnumSet<Action>> gdzie Action to enum {READ, WRITE, DELETE}.
         * Zdefiniuj uprawnienia dla każdej roli i napisz hasPermission(role, action).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_IdentityVsEqualsCachingPitfall {
        /*
         * 🧪 Zadanie 20:
         * Napisz prostą funkcję memoizującą kwadrat liczby, cache jako
         * IdentityHashMap<Integer,Integer>.
         * Pokaż pułapkę: dwa obiekty Integer o tej samej wartości ale
         * utworzone przez new Integer(...) (lub inny sposób tworzenia nowych instancji)
         * nie trafiają w ten sam wpis cache – w przeciwieństwie do HashMap.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TrafficLightStateMachine {
        /*
         * 🧪 Zadanie 21:
         * Enum Light {RED, YELLOW, GREEN}.
         * EnumMap<Light,Light> określająca następny stan (RED→GREEN→YELLOW→RED).
         * Zasymuluj 6 cykli zmiany świateł, wypisując aktualny stan co "sekundę"
         * (bez realnego czekania – po prostu w pętli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GraphCycleDetectionIdentity {
        /*
         * 🧪 Zadanie 22:
         * Graf obiektowy z węzłami-referencjami (klasa Node z listą sąsiadów Node).
         * Zbuduj graf z cyklem. Wykryj cykl DFS-em używając
         * IdentityHashMap<Node,Boolean> jako zbioru odwiedzonych i "w trakcie przetwarzania".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WeakReferenceCacheEviction {
        /*
         * 🧪 Zadanie 23:
         * Klasa cache oparta na WeakHashMap<Object,byte[]> symulująca duże dane.
         * Dodaj 5 kluczy trzymanych w silnej referencji (np. w List) oraz
         * 5 kluczy tworzonych "w locie" (bez zachowania referencji).
         * Po System.gc() sprawdź, które wpisy przetrwały (tylko te z silną referencją).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_EnumMapFSMParser {
        /*
         * 🧪 Zadanie 24:
         * Prosty automat stanów do walidacji formatu "ab*c" (litera a, potem
         * dowolna liczba b, potem c) zbudowany jako
         * EnumMap<State, Map<Character,State>> tablica przejść.
         * Przetestuj na wejściach "abc", "abbbc", "ac", "ba" (ostatnie dwa błędne/poprawne wg reguł).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CategorizedInventorySystem {
        /*
         * 🧪 Zadanie 25:
         * Enum Category {ELECTRONICS, FOOD, CLOTHING, BOOKS}.
         * EnumMap<Category, Set<String>> inventory – zestaw unikalnych nazw produktów
         * per kategoria (Set jako HashSet z Lesson05).
         * Dodaj po kilka produktów do każdej kategorii, wypisz raport z liczbą
         * unikalnych produktów per kategoria.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MemoizationComparisonIdentityVsHash {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj memoizację silni (factorial) dwoma cache'ami:
         * jeden HashMap<Integer,Long>, drugi IdentityHashMap<Integer,Long>.
         * Wywołaj obie wersje z argumentami tworzonymi jako "new Integer(n)"
         * (różne instancje o tej samej wartości) i porównaj skuteczność cache
         * (liczbę trafień vs nietrafień).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_WeakHashMapClassMetadataCache {
        /*
         * 🧪 Zadanie 27:
         * Symulacja cache metadanych klas: WeakHashMap<Class<?>, String>
         * przechowująca np. opis klasy dla kluczy typu Class (String.class, Integer.class, itd.).
         * Wypisz zawartość cache i wyjaśnij w komentarzu dlaczego WeakHashMap
         * pasuje tu lepiej niż HashMap (klucze Class mogą być zwalniane przy unload classloadera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_EnumMapHttpRouting {
        /*
         * 🧪 Zadanie 28:
         * Enum HttpMethod {GET, POST, PUT, DELETE}.
         * EnumMap<HttpMethod, List<String>> routing – lista ścieżek obsługiwanych
         * dla każdej metody (np. GET → "/users","/posts").
         * Napisz dispatch(HttpMethod, String path) sprawdzający czy ścieżka
         * jest zarejestrowana i wypisujący wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SpecialMapsBenchmarkSuite {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj pełny benchmark: te same operacje put/get (np. 500 000) wykonaj na
         * HashMap, EnumMap (klucze enum), IdentityHashMap oraz WeakHashMap.
         * Zmierz czas każdej i wypisz tabelę porównawczą (nazwa mapy – czas w ms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_TwoTierCacheSystem {
        /*
         * 🧪 Zadanie 30:
         * Enum CacheLevel {L1, L2}.
         * Zbuduj dwupoziomowy cache: EnumMap<CacheLevel, WeakHashMap<Object,String>>.
         * L1 (WeakHashMap) trzyma "gorące" dane z krótkim czasem życia,
         * L2 (WeakHashMap) dane zapasowe.
         * Napisz get(key) sprawdzający najpierw L1, potem L2 (i promujący trafienie z L2 do L1),
         * oraz put(key,value) zapisujący do L1.
         */
        public static void main(String[] args) { }
    }
}
