package com.example.javaquest._03_collections.Lesson21_LegacyCollections;

import java.util.*;

public class _Lesson21_LegacyCollections {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 LEGACY COLLECTIONS – STARE KOLEKCJE JAVY (JAVA 1.0/1.1)
         * ============================================================
         * Przed Java 2 (JDK 1.2) istniały pierwsze kolekcje Javy.
         * Zostały one zastąpione przez Collections Framework (java.util).
         *
         * Legacy kolekcje:
         * - Vector         → stary ArrayList (synchronized)
         * - Stack           → stary stos oparty na Vector
         * - Hashtable      → stara HashMap (synchronized)
         * - Properties     → Hashtable dla String→String (pliki .properties)
         * - Enumeration    → stary Iterator
         *
         * ⚠️ DLACZEGO ICH UNIKAĆ?
         * - Wszystkie metody są synchronized → wolniejsze
         * - Mają zbędne metody (addElement, elementAt, firstElement...)
         * - Stack ma problem: push() działa z dołu, ale get(0) z góry
         * - Hashtable nie pozwala null kluczy ani wartości
         * - Użyj ArrayList/ArrayDeque/HashMap zamiast nich!
         *
         * KIEDY JE ZOBACZYSZ?
         * - Stary kod (legacy codebases)
         * - Niektóre Android API
         * - Properties – nadal używane do plików konfiguracyjnych
         */

        /*
         * ============================================================
         * 🔹 VECTOR – STARY ARRAYLIST
         * ============================================================
         * Vector jest Thread-Safe (synchronized) → wolniejszy niż ArrayList
         * Zdwaja rozmiar przy każdym przepełnieniu (ArrayList rośnie o 50%)
         */

        System.out.println("=== VECTOR ===");
        Vector<String> vector = new Vector<>();
        vector.add("Java");
        vector.add("Python");
        vector.addElement("C++");    // stara metoda (= add())
        vector.add(1, "Kotlin");

        System.out.println("Vector: " + vector);
        System.out.println("Rozmiar: " + vector.size());
        System.out.println("Pojemność: " + vector.capacity());  // unikalny dla Vector
        System.out.println("Element [0]: " + vector.elementAt(0)); // stara metoda (= get())
        System.out.println("Pierwszy: " + vector.firstElement());
        System.out.println("Ostatni: " + vector.lastElement());

        vector.removeElement("Python"); // stara metoda (= remove())
        System.out.println("Po removeElement: " + vector);

        // Enumeration – stary Iterator
        Enumeration<String> enumeration = vector.elements();
        System.out.print("Enumeration: ");
        while (enumeration.hasMoreElements()) {
            System.out.print(enumeration.nextElement() + " ");
        }
        System.out.println();

        // Nowoczesna alternatywa:
        // ArrayList<String> modern = new ArrayList<>();  // szybsza, nie-sync
        // Collections.synchronizedList(modern);          // jeśli potrzeba sync

        /*
         * ============================================================
         * 🔹 STACK – STARY STOS (LIFO)
         * ============================================================
         * Stack extends Vector – problematyczna hierarchia!
         * Problem: Stack jest Vector, więc można wstawiać do środka (złe!)
         *
         * ✅ Używaj ArrayDeque.push/pop zamiast Stack!
         */

        System.out.println("\n=== STACK ===");
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Stack: " + stack);
        System.out.println("Peek: " + stack.peek()); // 30 – bez usuwania
        System.out.println("Pop: " + stack.pop());   // 30
        System.out.println("Pop: " + stack.pop());   // 20
        System.out.println("Stack po: " + stack);
        System.out.println("Empty? " + stack.empty());

        // Szukanie (od góry) – specyficzne dla Stack
        Stack<String> stackStr = new Stack<>();
        stackStr.push("A"); stackStr.push("B"); stackStr.push("C"); stackStr.push("B");
        System.out.println("Search B: " + stackStr.search("B")); // 1 (od góry)
        System.out.println("Search A: " + stackStr.search("A")); // 4

        // Nowoczesna alternatywa:
        // Deque<Integer> modernStack = new ArrayDeque<>();
        // modernStack.push(10); modernStack.pop();

        /*
         * ============================================================
         * 🔹 HASHTABLE – STARA HASHMAP
         * ============================================================
         * Hashtable jest synchronized → wolniejsza niż HashMap
         * ❌ Nie pozwala null kluczy ani wartości → NullPointerException
         */

        System.out.println("\n=== HASHTABLE ===");
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("one", 1);
        hashtable.put("two", 2);
        hashtable.put("three", 3);

        System.out.println("Hashtable: " + hashtable);
        System.out.println("get(two): " + hashtable.get("two"));
        System.out.println("containsKey: " + hashtable.containsKey("one"));
        System.out.println("contains(wartość): " + hashtable.contains(2)); // stara metoda!

        // Enumeration kluczy
        Enumeration<String> keys = hashtable.keys();
        System.out.print("Klucze: ");
        while (keys.hasMoreElements()) System.out.print(keys.nextElement() + " ");
        System.out.println();

        try {
            hashtable.put(null, 0); // ❌ rzuci NPE
        } catch (NullPointerException e) {
            System.out.println("Hashtable nie pozwala null: " + e);
        }

        // Nowoczesna alternatywa: HashMap lub ConcurrentHashMap

        /*
         * ============================================================
         * 🔹 PROPERTIES – DO DZIŚ UŻYWANE!
         * ============================================================
         * Properties extends Hashtable<Object, Object>
         * Przeznaczone dla par String → String
         * Używane do plików konfiguracyjnych (.properties)
         */

        System.out.println("\n=== PROPERTIES ===");
        Properties props = new Properties();
        props.setProperty("db.host", "localhost");
        props.setProperty("db.port", "5432");
        props.setProperty("db.name", "mydb");

        System.out.println("Host: " + props.getProperty("db.host"));
        System.out.println("Port: " + props.getProperty("db.port"));
        System.out.println("User: " + props.getProperty("db.user", "admin")); // default value

        // Wypisz wszystkie
        props.forEach((k, v) -> System.out.println("  " + k + "=" + v));

        // Konwersja typów (Properties to tylko Strings)
        int port = Integer.parseInt(props.getProperty("db.port"));
        System.out.println("Port jako int: " + port);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE – CO UŻYWAĆ ZAMIAST LEGACY
         * ============================================================
         * ❌ Vector           → ✅ ArrayList (lub synchronizedList)
         * ❌ Stack            → ✅ ArrayDeque (push/pop)
         * ❌ Hashtable        → ✅ HashMap (lub ConcurrentHashMap)
         * ❌ Enumeration      → ✅ Iterator lub for-each
         * ✅ Properties       → nadal OK dla plików .properties
         *
         * Jedyna uzasadniona sytuacja dla legacy:
         * - Praca ze starym kodem który używa tych typów
         * - Properties dla konfiguracji w plikach .properties
         */
    }
}
