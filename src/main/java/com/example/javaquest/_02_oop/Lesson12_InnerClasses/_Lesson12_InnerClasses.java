package com.example.javaquest._02_oop.Lesson12_InnerClasses;

public class _Lesson12_InnerClasses {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🪆 KLASY WEWNĘTRZNE – PRZEGLĄD
         * ============================================================
         * Java pozwala definiować klasy wewnątrz innych klas.
         * 4 rodzaje klas wewnętrznych:
         *
         * 1. Non-static inner class (klasa wewnętrzna)
         *    – powiązana z instancją klasy zewnętrznej
         *
         * 2. Static nested class (statyczna klasa zagnieżdżona)
         *    – niezależna od instancji zewnętrznej
         *
         * 3. Local class (klasa lokalna)
         *    – zdefiniowana wewnątrz metody
         *
         * 4. Anonymous class (klasa anonimowa)
         *    – klasa bez nazwy, zdefiniowana i użyta w jednym miejscu
         */

        /*
         * ============================================================
         * 🔹 1. NON-STATIC INNER CLASS
         * ============================================================
         * - Ma dostęp do WSZYSTKICH składowych klasy zewnętrznej (również private)
         * - Do stworzenia potrzebna jest instancja klasy zewnętrznej
         * - Przydatna gdy klasa wewnętrzna jest ściśle powiązana z zewnętrzną
         */

        Outer outer = new Outer("dane zewnętrzne");
        Outer.Inner inner = outer.new Inner("dane wewnętrzne");
        inner.display();
        inner.accessOuter();

        /*
         * ============================================================
         * 🔹 2. STATIC NESTED CLASS
         * ============================================================
         * - NIE ma dostępu do niestatycznych składowych zewnętrznej klasy
         * - Można stworzyć bez instancji klasy zewnętrznej
         * - Logicznie powiązana z zewnętrzną, ale niezależna
         * - Najczęściej używana (np. wzorzec Builder)
         */

        Outer.StaticNested nested = new Outer.StaticNested("statyczna zagnieżdżona");
        nested.display();

        // Wzorzec Builder z static nested class
        Person person = new Person.Builder()
                .name("Jan Kowalski")
                .age(30)
                .email("jan@example.com")
                .build();
        System.out.println(person);

        /*
         * ============================================================
         * 🔹 3. LOCAL CLASS
         * ============================================================
         * - Zdefiniowana wewnątrz metody
         * - Widoczna tylko w tej metodzie
         * - Ma dostęp do effectively final zmiennych lokalnych
         */

        createLocalClassDemo();

        /*
         * ============================================================
         * 🔹 4. ANONYMOUS CLASS
         * ============================================================
         * - Klasa bez nazwy, tworzona inline
         * - Jednorazowa implementacja interfejsu lub nadklasy
         * - Często zastępowana przez lambdy (Java 8+)
         * - Użyteczna gdy potrzebna jest bardziej złożona logika niż w lambdzie
         */

        Greeting formalGreeting = new Greeting() {
            @Override
            public void greet(String name) {
                System.out.println("Szanowny Panie/Pani " + name + ", dzień dobry!");
            }
        };

        Greeting casualGreeting = new Greeting() {
            private int count = 0; // anonimowa klasa może mieć pola

            @Override
            public void greet(String name) {
                count++;
                System.out.println("Hej " + name + "! (powitanie #" + count + ")");
            }
        };

        formalGreeting.greet("Kowalski");
        casualGreeting.greet("Arek");
        casualGreeting.greet("Basia");

        // Anonimowa nadklasa (nie tylko interfejsu)
        Animal silentAnimal = new Animal() {
            @Override
            void makeSound() {
                System.out.println("...(milczenie)...");
            }
        };
        silentAnimal.makeSound();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * inner class    → powiązana z instancją, outer.new Inner()
         * static nested  → niezależna od instancji, Outer.Nested()
         * local class    → wewnątrz metody, effectively final z zasięgu
         * anonymous      → inline jednorazowa implementacja/nadklasa
         *
         * Najczęściej w praktyce: static nested (Builder pattern, Entry klasy)
         * i anonymous class (listenery, Runnable, Comparator – teraz lambdy)
         */
    }

    static void createLocalClassDemo() {
        String prefix = "LOCAL";

        class LocalFormatter {
            void format(String message) {
                System.out.println("[" + prefix + "] " + message); // effectively final
            }
        }

        LocalFormatter fmt = new LocalFormatter();
        fmt.format("Klasa lokalna w działaniu");
        fmt.format("Dostęp do zmiennych z metody");
    }
}

class Outer {
    private String outerData;

    Outer(String outerData) {
        this.outerData = outerData;
    }

    class Inner {
        private String innerData;

        Inner(String innerData) {
            this.innerData = innerData;
        }

        void display() {
            System.out.println("Inner: " + innerData);
        }

        void accessOuter() {
            // Dostęp do private pola zewnętrznej klasy
            System.out.println("Outer (z Inner): " + outerData);
        }
    }

    static class StaticNested {
        private String data;

        StaticNested(String data) {
            this.data = data;
        }

        void display() {
            System.out.println("StaticNested: " + data);
            // outerData; // ❌ brak dostępu do niestatycznych pól Outer
        }
    }
}

class Person {
    private String name;
    private int age;
    private String email;

    private Person() {}

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }

    // Builder jako static nested class
    static class Builder {
        private String name;
        private int age;
        private String email;

        Builder name(String name)   { this.name = name; return this; }
        Builder age(int age)        { this.age = age; return this; }
        Builder email(String email) { this.email = email; return this; }

        Person build() {
            Person p = new Person();
            p.name = name;
            p.age = age;
            p.email = email;
            return p;
        }
    }
}

interface Greeting {
    void greet(String name);
}

abstract class Animal {
    abstract void makeSound();
}
