package com.example.javaquest._02_oop.Lesson05_Inheritance;

public class _Lesson05_Inheritance {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🧬 DZIEDZICZENIE – CZYM JEST?
         * ============================================================
         * Dziedziczenie (ang. inheritance) pozwala jednej klasie przejąć
         * pola i metody innej klasy. Tworzymy hierarchię klas.
         *
         * Słowo kluczowe: extends
         *
         * Klasa nadrzędna (parent/superclass): definiuje wspólne zachowanie
         * Klasa podrzędna (child/subclass):    rozszerza lub specjalizuje
         *
         * Relacja: IS-A ("jest")
         * Pies IS-A Zwierzę → class Dog extends Animal
         *
         * Java wspiera tylko POJEDYNCZE dziedziczenie klas
         * (klasa może mieć tylko jedną klasę nadrzędną).
         */

        Dog dog = new Dog("Rex", 3, "Labrador");
        dog.eat();       // metoda odziedziczona z Animal
        dog.bark();      // metoda własna Dog
        dog.displayInfo(); // nadpisana (overridden) metoda z Animal

        Cat cat = new Cat("Whiskers", 5);
        cat.eat();
        cat.displayInfo();

        /*
         * ============================================================
         * 🔑 SŁOWO KLUCZOWE super
         * ============================================================
         * `super` odnosi się do klasy nadrzędnej.
         *
         * Dwa zastosowania:
         * 1. super() – wywołanie konstruktora klasy nadrzędnej
         *    (musi być PIERWSZĄ instrukcją w konstruktorze!)
         * 2. super.metoda() – wywołanie metody z klasy nadrzędnej
         *    (gdy podklasa ją nadpisała, ale chce też wykonać oryginał)
         */

        ElectricCar tesla = new ElectricCar("Tesla", "Model 3", 2024, 500);
        tesla.displayInfo(); // wywołuje super.displayInfo() + dodaje własne info

        /*
         * ============================================================
         * 🔄 PRZESŁANIANIE METOD (@Override)
         * ============================================================
         * Podklasa może zdefiniować metodę o tej samej sygnaturze co nadklasa.
         * Wtedy wersja z podklasy zastępuje wersję z nadklasy.
         *
         * @Override – adnotacja (opcjonalna, ale zalecana):
         * - kompilator sprawdza, czy rzeczywiście nadpisujemy metodę
         * - informuje czytelnika o intencji
         *
         * Przesłanianie ≠ przeciążanie (overloading):
         * - overriding: ta sama sygnatura, inna klasa w hierarchii
         * - overloading: inna sygnatura (inne parametry), ta sama klasa
         */

        AnimalBase a1 = new AnimalBase("Zwierzę generyczne");
        AnimalBase a2 = new DogAnimal("Burek");
        AnimalBase a3 = new CatAnimal("Luna");

        a1.speak(); // "Zwierzę mówi..."
        a2.speak(); // "Burek: Hau!"  – dynamiczne wiązanie (polimorfizm)
        a3.speak(); // "Luna: Miau!"

        /*
         * ============================================================
         * 🔒 CO SIĘ DZIEDZICZY, A CO NIE?
         * ============================================================
         * DZIEDZICZY SIĘ:
         * ✅ pola publiczne i chronione (public, protected)
         * ✅ metody publiczne i chronione
         * ✅ pola i metody package-private (jeśli w tym samym pakiecie)
         *
         * NIE DZIEDZICZY SIĘ:
         * ❌ konstruktorów (ale można je wywołać przez super())
         * ❌ pól i metod prywatnych (private)
         * ❌ metod statycznych (są ukrywane, nie nadpisywane)
         */

        /*
         * ============================================================
         * 🌳 HIERARCHIA DZIEDZICZENIA
         * ============================================================
         * Każda klasa w Javie niejawnie dziedziczy z klasy Object.
         * Object jest korzeniem każdej hierarchii klas.
         *
         * Object → Animal → Dog
         *
         * Metody odziedziczone z Object:
         * toString(), equals(), hashCode(), getClass() itd.
         */

        System.out.println(dog.getClass().getName()); // pełna nazwa klasy
        System.out.println(dog instanceof Dog);        // true
        System.out.println(dog instanceof Animal);     // true (przez dziedziczenie)

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - extends: klasa dziedziczy pola i metody rodzica
         * - super(): wywołanie konstruktora nadklasy
         * - super.metoda(): wywołanie metody nadklasy z podklasy
         * - @Override: przesłanianie metody z nadklasy
         * - IS-A: pies IS-A zwierzę (relacja dziedziczenia)
         * - private pola nie są dziedziczone (tylko dostępne przez gettery)
         * - każda klasa niejawnie extends Object
         */
    }
}

class AnimalBase {
    String name;

    AnimalBase(String name) {
        this.name = name;
    }

    void speak() {
        System.out.println(name + ": ...");
    }
}

class DogAnimal extends AnimalBase {
    DogAnimal(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + ": Hau!");
    }
}

class CatAnimal extends AnimalBase {
    CatAnimal(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + ": Miau!");
    }
}

// Hierarchia: Animal → Dog, Cat
class Animal {
    protected String name;
    protected int age;

    Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void eat() {
        System.out.println(name + " je jedzenie.");
    }

    void displayInfo() {
        System.out.println("Zwierzę: " + name + ", wiek: " + age);
    }
}

class Dog extends Animal {
    private String breed;

    Dog(String name, int age, String breed) {
        super(name, age); // wywołanie konstruktora Animal
        this.breed = breed;
    }

    void bark() {
        System.out.println(name + " szczeka: Hau hau!");
    }

    @Override
    void displayInfo() {
        super.displayInfo(); // najpierw info z Animal
        System.out.println("  Rasa: " + breed);
    }
}

class Cat extends Animal {
    Cat(String name, int age) {
        super(name, age);
    }

    @Override
    void displayInfo() {
        System.out.println("Kot: " + name + ", wiek: " + age + " lat");
    }
}

// Hierarchia: Animal → Vehicle → Car → ElectricCar (głębsze dziedziczenie)
class CarBase {
    protected String brand;
    protected String model;
    protected int year;

    CarBase(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    void displayInfo() {
        System.out.println(year + " " + brand + " " + model);
    }
}

class ElectricCar extends CarBase {
    private int rangeKm;

    ElectricCar(String brand, String model, int year, int rangeKm) {
        super(brand, model, year); // wywołanie konstruktora CarBase
        this.rangeKm = rangeKm;
    }

    @Override
    void displayInfo() {
        super.displayInfo(); // wypisuje bazowe info
        System.out.println("  Zasięg elektryczny: " + rangeKm + " km");
    }
}
