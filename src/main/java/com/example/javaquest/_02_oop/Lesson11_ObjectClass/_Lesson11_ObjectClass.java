package com.example.javaquest._02_oop.Lesson11_ObjectClass;

import java.util.Objects;

public class _Lesson11_ObjectClass {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🌍 KLASA Object – KORZEŃ WSZYSTKIEGO
         * ============================================================
         * Każda klasa w Javie niejawnie extends Object.
         * Object definiuje metody, które dziedziczy każdy obiekt.
         *
         * Kluczowe metody Object:
         * - toString()   → tekstowa reprezentacja obiektu
         * - equals()     → porównanie zawartości
         * - hashCode()   → skrót obiektu (liczba całkowita)
         * - getClass()   → informacje o typie w czasie działania
         * - clone()      → kopia obiektu (rzadko używane)
         */

        /*
         * ============================================================
         * 🖨️ toString() – TEKSTOWA REPREZENTACJA
         * ============================================================
         * Domyślna implementacja: NazwaKlasy@hashCodeHex (mało użyteczna)
         * Warto zawsze nadpisać, by widzieć sensowne dane.
         *
         * Wywołanie: System.out.println(obiekt) → automatycznie wywołuje toString()
         */

        PersonObj p1 = new PersonObj("Jan", "Kowalski", 30);
        System.out.println(p1); // wywołuje p1.toString()

        PersonObj p2 = new PersonObj("Anna", "Nowak", 25);
        System.out.println("Osoba: " + p2); // konkatenacja też wywołuje toString()

        /*
         * ============================================================
         * ⚖️ equals() – PORÓWNANIE ZAWARTOŚCI
         * ============================================================
         * Domyślna implementacja w Object: sprawdza == (ten sam obiekt).
         * Zazwyczaj chcemy porównywać ZAWARTOŚĆ, nie adres.
         *
         * Kontrakt equals():
         * 1. Zwrotność: x.equals(x) → true
         * 2. Symetryczność: x.equals(y) → y.equals(x)
         * 3. Przechodniość: x.equals(y) && y.equals(z) → x.equals(z)
         * 4. Spójność: wiele wywołań → ten sam wynik (jeśli stan bez zmian)
         * 5. x.equals(null) → false (nigdy NullPointerException)
         */

        PersonObj a = new PersonObj("Jan", "Kowalski", 30);
        PersonObj b = new PersonObj("Jan", "Kowalski", 30);
        PersonObj c = a; // ta sama referencja

        System.out.println("a == b (referencja): " + (a == b));       // false
        System.out.println("a.equals(b) (zawartość): " + a.equals(b)); // true
        System.out.println("a == c (referencja): " + (a == c));        // true
        System.out.println("a.equals(null): " + a.equals(null));       // false

        /*
         * ============================================================
         * #️⃣ hashCode() – SKRÓT OBIEKTU
         * ============================================================
         * Zwraca liczbę int reprezentującą obiekt.
         * Używany przez HashMap, HashSet i inne kolekcje haszujące.
         *
         * KONTRAKT equals/hashCode (MUST!):
         * Jeśli a.equals(b) → true, to a.hashCode() MUSI == b.hashCode()
         * (Odwrotnie nie musi: równe hashCode != równe obiekty – kolizja)
         *
         * Naruszenie kontraktu = błędne zachowanie HashMap/HashSet!
         */

        System.out.println("hashCode a: " + a.hashCode());
        System.out.println("hashCode b: " + b.hashCode());
        System.out.println("hashCode są równe? " + (a.hashCode() == b.hashCode())); // true

        /*
         * ============================================================
         * 🔍 getClass() – INFORMACJE O TYPIE
         * ============================================================
         * Zwraca obiekt Class opisujący rzeczywisty typ obiektu.
         * Używany do refleksji, porównywania typów.
         */

        Object obj = new PersonObj("x", "y", 1);
        System.out.println("Klasa: " + obj.getClass().getName());
        System.out.println("Prosta nazwa: " + obj.getClass().getSimpleName());
        System.out.println("Czy PersonObj? " + (obj.getClass() == PersonObj.class));

        /*
         * ============================================================
         * 🛡️ Objects – KLASA POMOCNICZA (java.util.Objects)
         * ============================================================
         * Utility class z bezpiecznymi metodami do pracy z obiektami:
         * - Objects.equals(a, b)   → null-safe equals
         * - Objects.hash(a, b, c)  → hashCode wielu pól
         * - Objects.requireNonNull → walidacja null
         * - Objects.toString(obj, "domyślny") → null-safe toString
         */

        PersonObj nullPerson = null;
        System.out.println("Objects.toString null: " + Objects.toString(nullPerson, "brak danych"));

        String name = Objects.requireNonNull("Jan", "imię nie może być null");
        System.out.println("Wymagane: " + name);

        boolean safeEquals = Objects.equals(null, null); // nie rzuca NPE
        System.out.println("null equals null: " + safeEquals);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - toString(): zawsze nadpisuj – ułatwia debugowanie
         * - equals(): nadpisuj gdy porównujesz zawartość, nie adres
         * - hashCode(): ZAWSZE nadpisuj razem z equals() – kontrakt!
         * - getClass(): typ w czasie działania (runtime type)
         * - Objects: utility class z null-safe wersjami equals/hashCode
         */
    }
}

class PersonObj {
    private String firstName;
    private String lastName;
    private int age;

    PersonObj(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonObj{" + firstName + " " + lastName + ", wiek=" + age + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;          // ten sam obiekt w pamięci
        if (obj == null) return false;          // null zawsze false
        if (getClass() != obj.getClass()) return false; // różne typy

        PersonObj other = (PersonObj) obj;
        return age == other.age
                && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age); // delegate to Objects.hash
    }
}
