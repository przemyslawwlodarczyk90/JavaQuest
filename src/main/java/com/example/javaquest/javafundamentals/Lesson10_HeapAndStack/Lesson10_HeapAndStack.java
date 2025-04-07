package com.example.javaquest.javafundamentals.Lesson10_HeapAndStack;

public class Lesson10_HeapAndStack {

    /**
     * ========================================================================
     * 🧠 PAMIĘĆ WIRTUALNA JVM – HEAP vs STACK
     * ========================================================================
     * JVM (Java Virtual Machine) zarządza pamięcią dzieląc ją na segmenty:
     *
     * ┌────────────┐
     * │   STACK    │  ← każda metoda ma własny stos wywołań
     * └────────────┘
     *    |  zmienne lokalne
     *    |  parametry metod
     *    |  referencje do obiektów
     *    ↓
     * ┌────────────┐
     * │    HEAP    │  ← współdzielona pula obiektów
     * └────────────┘
     *    |  obiekty utworzone przez `new`
     *    |  np. String, Dog, ArrayList
     *
     * ✳️ STACK (Stos):
     * - Każde wywołanie metody dostaje nową ramkę (stack frame).
     * - Zmienne prymitywne przechowywane są bezpośrednio.
     * - Referencje do obiektów także mieszkają na stosie.
     * - Szybki dostęp, mała pamięć, zwalniana automatycznie po wyjściu z metody.
     *
     * ✳️ HEAP (Sterta):
     * - Obiekty i ich pola (np. `new Dog()`, `new String()`).
     * - Obiekty żyją tak długo, jak istnieje do nich referencja.
     * - Zarządzany przez Garbage Collector.
     *
     * 📌 RÓŻNICA:
     * - stack: szybki, mały, krótkotrwałe dane
     * - heap: wolniejszy, większy, dane długoterminowe
     */

    public static void main(String[] args) {
        int localPrimitive = 10; // typ prymitywny – przechowywany bezpośrednio na stosie

        Dog dog1 = new Dog("Azor"); // referencja dog1 → na stosie
        // obiekt Dog("Azor") → na stercie

        Dog dog2 = dog1; // kopiujemy referencję – wskazuje na ten sam obiekt w heapie

        System.out.println("Imię psa przed modyfikacją: " + dog2.name);

        modifyPrimitive(localPrimitive); // przekazanie kopii prymitywu – oryginał się nie zmienia
        modifyObject(dog1);              // przekazanie referencji – obiekt może być zmodyfikowany

        System.out.println("Imię psa po modyfikacji: " + dog1.name); // imię zostało zmienione na heapie

        // Odcięcie wszystkich referencji do obiektu
        dog1 = null;
        dog2 = null;

        // Sugerujemy wykonanie garbage collectora
        System.gc();
        System.out.println("Program zakończony – możliwa czyszczenie obiektu z heapu");
    }

    static void modifyPrimitive(int value) {
        value = 99; // Modyfikujemy tylko lokalną kopię, oryginał się nie zmienia
        System.out.println("Wewnątrz modifyPrimitive: " + value);
    }

    static void modifyObject(Dog dog) {
        dog.name = "Reksio"; // Modyfikujemy obiekt na heapie przez referencję
    }
}

class Dog {
    String name; // pole obiektu (na heapie)

    Dog(String name) {
        this.name = name;
    }
}

/*
 * ============================
 * DODATKOWE PRZYKŁADY KOMENTARZY
 * ============================
 *
 * // STACK:
 * int number = 5;         // na stosie
 * String name = "Asia";   // referencja na stosie, ale obiekt String na heapie
 *
 * // HEAP:
 * Dog d = new Dog("Burek");  // obiekt Dog na heapie, d – referencja na stosie
 *
 * // METODY:
 * - każda metoda ma własny stos z parametrami i zmiennymi lokalnymi
 * - zakończenie metody = usunięcie ramki ze stosu
 *
 * // GC:
 * - gdy obiekt traci wszystkie referencje → kwalifikuje się do usunięcia
 * - GC działa automatycznie, nie musimy nic robić
 * - można wywołać: System.gc(); ale to tylko sugestia
 */