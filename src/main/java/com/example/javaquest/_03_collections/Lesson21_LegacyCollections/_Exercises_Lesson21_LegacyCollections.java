package com.example.javaquest._03_collections.Lesson21_LegacyCollections;

public class _Exercises_Lesson21_LegacyCollections {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicVector {
        /*
         * 🧪 Zadanie 1:
         * Vector<String>: dodaj "A","B","C" metodą add().
         * Wypisz zawartość, size() i capacity().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VectorOldMethods {
        /*
         * 🧪 Zadanie 2:
         * Vector<String> z elementami "Java","Kotlin","Scala".
         * Użyj starych metod: addElement("Groovy"), elementAt(0),
         * firstElement(), lastElement().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VectorRemove {
        /*
         * 🧪 Zadanie 3:
         * Vector<Integer> z {10,20,30,40}.
         * Użyj removeElement(20) oraz removeElementAt(0).
         * Wypisz wynik po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BasicStack {
        /*
         * 🧪 Zadanie 4:
         * Stack<Integer>: push(10), push(20), push(30).
         * Wypisz peek(), potem pop() dwa razy, sprawdź empty().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_StackSearch {
        /*
         * 🧪 Zadanie 5:
         * Stack<String>: push "A","B","C","B".
         * Użyj search("B") i search("A") – wypisz pozycje (liczone od góry, 1-indexed).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BasicHashtable {
        /*
         * 🧪 Zadanie 6:
         * Hashtable<String,Integer>: put("one",1), put("two",2), put("three",3).
         * Wypisz get("two") i containsKey("one").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_HashtableNull {
        /*
         * 🧪 Zadanie 7:
         * Spróbuj hashtable.put(null, 1) oraz hashtable.put("klucz", null).
         * Złap NullPointerException w try/catch i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BasicProperties {
        /*
         * 🧪 Zadanie 8:
         * Properties: setProperty("app.name","JavaQuest"), setProperty("app.version","1.0").
         * Wypisz getProperty("app.name") oraz getProperty("app.author","nieznany") (wartość domyślna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EnumerationVector {
        /*
         * 🧪 Zadanie 9:
         * Vector<String> z {"raz","dwa","trzy"}.
         * Pobierz Enumeration przez elements() i wypisz elementy pętlą
         * hasMoreElements()/nextElement().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_EnumerationHashtable {
        /*
         * 🧪 Zadanie 10:
         * Hashtable<String,Integer> z 4 wpisami.
         * Pobierz Enumeration kluczy przez keys() i wypisz je po kolei.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_VectorVsArrayList {
        /*
         * 🧪 Zadanie 11:
         * Dodaj 100 000 elementów do Vector i do ArrayList.
         * Zmierz czas dodawania (System.nanoTime()) dla obu.
         * Wypisz też capacity Vectora po dodaniu (obserwuj podwajanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_StackVsArrayDeque {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj tę samą sekwencję operacji (push 1,2,3, pop, pop)
         * raz na Stack<Integer>, raz na Deque<Integer> (jako stos: push/pop).
         * Porównaj wyniki – powinny być identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_HashtableVsHashMap {
        /*
         * 🧪 Zadanie 13:
         * Wstaw te same dane do Hashtable i HashMap.
         * Spróbuj wstawić null klucz/wartość do obu – porównaj zachowanie
         * (Hashtable rzuca NPE, HashMap pozwala jeden null klucz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PropertiesFromReader {
        /*
         * 🧪 Zadanie 14:
         * Stwórz String z zawartością:
         * "db.host=localhost\ndb.port=5432\ndb.name=quest"
         * Wczytaj go do Properties przez load(new StringReader(tekst)).
         * Wypisz wszystkie właściwości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_PropertiesDefaults {
        /*
         * 🧪 Zadanie 15:
         * Stwórz Properties "defaults" z wpisem "timeout"="30".
         * Stwórz Properties główne z konstruktorem Properties(defaults),
         * nadpisz tylko "retries"="3".
         * Wypisz getProperty("timeout") i getProperty("retries").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_VectorSynchronized {
        /*
         * 🧪 Zadanie 16:
         * Porównaj Vector (domyślnie synchronized) z
         * Collections.synchronizedList(new ArrayList<>()).
         * Wykonaj te same operacje add/remove na obu i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_StackBadDesign {
        /*
         * 🧪 Zadanie 17:
         * Stack<Integer>: push(1), push(2), push(3).
         * Ponieważ Stack extends Vector, użyj insertElementAt(99, 0)
         * żeby wstawić element na dole – pokaż że łamie to zasadę LIFO
         * (peek() nie zwraca 99, ale get(0) tak).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_EnumerationToIterator {
        /*
         * 🧪 Zadanie 18:
         * Vector<String> z {"x","y","z"}.
         * Zamień jego Enumeration na Iterator przez Collections.list(enumeration).iterator().
         * Iteruj przez zwrócony Iterator.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HashtableIterateEntries {
        /*
         * 🧪 Zadanie 19:
         * Hashtable<String,Integer> z 5 wpisami.
         * Wypisz wszystkie wpisy dwoma sposobami: przez Enumeration (keys())
         * z ręcznym get(), oraz przez entrySet() z for-each.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PropertiesToMap {
        /*
         * 🧪 Zadanie 20:
         * Properties z 4 wpisami konfiguracyjnymi.
         * Skonwertuj je na Map<String,String> (stringPropertyNames() + getProperty()).
         * Wypisz mapę posortowaną po kluczu (np. przez TreeMap).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LegacyConfigLoader {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj klasę konfiguracji opartą na Properties z typowanymi getterami:
         * getInt(key, default), getBoolean(key, default), getString(key, default).
         * Przetestuj na wpisach "port"="8080", "debug"="true".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_VectorThreadSafetyDemo {
        /*
         * 🧪 Zadanie 22:
         * Uruchom 4 wątki dodające po 1000 elementów do wspólnego Vector
         * oraz do wspólnej zwykłej ArrayList (bez synchronizacji).
         * Porównaj końcowe rozmiary (ArrayList może rzucić wyjątek lub zgubić dane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_StackBasedCalculator {
        /*
         * 🧪 Zadanie 23:
         * Kalkulator RPN (odwrotna notacja polska) używając Stack<Integer>.
         * Wejście: "5 3 + 8 2 - *" → wynik (5+3)*(8-2)=48.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BalancedParentheses {
        /*
         * 🧪 Zadanie 24:
         * Sprawdź poprawność nawiasów w Stringu używając Stack<Character>.
         * Przetestuj: "({[]})" → true, "([)]" → false, "(()" → false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_HashtableCache {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj prosty cache licznika odwiedzin stron oparty na
         * Hashtable<String,Integer> (thread-safe z założenia).
         * Metody: visit(url) inkrementuje licznik, getCount(url).
         * Zasymuluj wiele wątków wołających visit() na tych samych URL-ach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MigrateLegacyToModern {
        /*
         * 🧪 Zadanie 26:
         * Napisz metody generyczne:
         * toArrayList(Vector<T>) → ArrayList<T>
         * toHashMap(Hashtable<K,V>) → HashMap<K,V>
         * Przetestuj konwersję i porównaj zawartość przed/po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PropertiesMerge {
        /*
         * 🧪 Zadanie 27:
         * Properties "systemDefaults" (np. host, port, timeout)
         * i Properties "userOverrides" (np. tylko port).
         * Scal je tak, by userOverrides miały priorytet, wynik w nowym Properties.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_EnumerationIteratorAdapter {
        /*
         * 🧪 Zadanie 28:
         * Napisz własną klasę EnumerationAdapter<T> implementującą Iterator<T>,
         * która opakowuje Enumeration<T> (konstruktor przyjmuje Enumeration<T>).
         * Zaimplementuj hasNext() i next() delegując do hasMoreElements()/nextElement().
         * Przetestuj na Enumeration z Vectora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_StackVectorHybrid {
        /*
         * 🧪 Zadanie 29:
         * Pokaż problem hierarchii Stack extends Vector: napisz metodę
         * przyjmującą Vector<Integer> i wywołaj ją ze Stackiem po kilku push().
         * Metoda robi insertElementAt psując LIFO – wypisz stan przed i po,
         * a potem napraw problem przepisując logikę na Deque<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_LegacyCollectionsBenchmark {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj benchmark porównujący dla 500 000 operacji:
         * Vector.add vs ArrayList.add, Hashtable.put vs HashMap.put,
         * Stack.push/pop vs ArrayDeque.push/pop.
         * Zmierz czasy (System.nanoTime()) i wypisz raport z różnicami procentowymi.
         */
        public static void main(String[] args) { }
    }
}
