package com.example.javaquest._02_oop.Lesson03_Constructors;

public class _Lesson03_Constructors {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🏗️ KONSTRUKTORY – CZYM SĄ?
         * ============================================================
         * Konstruktor = specjalna metoda inicjalizująca obiekt podczas `new`.
         *
         * Cechy konstruktora:
         * - Ta sama nazwa co klasa
         * - Brak typu zwracanego (nawet void)
         * - Wywoływany automatycznie przy tworzeniu obiektu
         * - Może być przeciążony (kilka wersji z różnymi parametrami)
         *
         * Jeśli nie zdefiniujesz żadnego konstruktora,
         * Java automatycznie dodaje domyślny bezargumentowy.
         * Jednak gdy zdefiniujesz choć jeden – domyślny znika!
         */

        /*
         * ============================================================
         * 🔹 KONSTRUKTOR DOMYŚLNY (bezargumentowy)
         * ============================================================
         */

        Point p1 = new Point(); // konstruktor bezargumentowy
        System.out.println("Punkt domyślny: " + p1);

        Point p2 = new Point(3, 7); // konstruktor z parametrami
        System.out.println("Punkt (3,7): " + p2);

        /*
         * ============================================================
         * 🔗 ŁAŃCUCHOWANIE KONSTRUKTORÓW: this()
         * ============================================================
         * this() wywołuje inny konstruktor tej samej klasy.
         * Musi być PIERWSZĄ instrukcją w konstruktorze.
         *
         * Korzyść: eliminacja duplikacji kodu – logika inicjalizacji w jednym miejscu.
         */

        Product p = new Product("Laptop");
        System.out.println(p);

        Product p3 = new Product("Klawiatura", 199.99);
        System.out.println(p3);

        Product p4 = new Product("Monitor", 899.99, "Electronics");
        System.out.println(p4);

        /*
         * ============================================================
         * 🔹 KOLEJNOŚĆ INICJALIZACJI W JAVIE
         * ============================================================
         * 1. Inicjalizatory pól (przypisania przy deklaracji)
         * 2. Bloki inicjalizacyjne { }
         * 3. Treść konstruktora
         *
         * Wszystkie trzy wykonują się w powyższej kolejności.
         */

        System.out.println("--- Tworzenie obiektu Counter ---");
        Counter c = new Counter();
        System.out.println("Wartość po inicjalizacji: " + c.getValue());

        /*
         * ============================================================
         * 🔹 KOPIUJĄCY KONSTRUKTOR (copy constructor)
         * ============================================================
         * Tworzy nowy obiekt jako kopię istniejącego.
         * Przydatny gdy chcemy mieć niezależne kopie obiektów.
         */

        Point original = new Point(5, 10);
        Point copy = new Point(original); // konstruktor kopiujący
        copy.x = 99;

        System.out.println("Oryginał: " + original);
        System.out.println("Kopia (zmodyfikowana): " + copy);

        /*
         * ============================================================
         * ⚠️ PUŁAPKA: this() vs super()
         * ============================================================
         * - this()  – wywołuje inny konstruktor TEJ SAMEJ klasy
         * - super() – wywołuje konstruktor KLASY NADRZĘDNEJ (dziedziczenie)
         * - Oba muszą być PIERWSZĄ instrukcją – nie można ich używać razem
         *
         * (super() pokazane w Lesson05_Inheritance)
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - konstruktor bezargumentowy: domyślny stan obiektu
         * - konstruktor z parametrami: pełna inicjalizacja przy tworzeniu
         * - this() w konstruktorze: delegacja do innego konstruktora
         * - konstruktor kopiujący: niezależna kopia obiektu
         * - kolejność: inicjalizatory pól → bloki { } → konstruktor
         */
    }
}

class Point {
    int x;
    int y;

    // Konstruktor bezargumentowy
    Point() {
        this(0, 0); // deleguje do konstruktora z dwoma parametrami
    }

    // Konstruktor główny
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Konstruktor kopiujący
    Point(Point other) {
        this(other.x, other.y);
    }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
}

class Product {
    String name;
    double price;
    String category;

    // Konstruktor z jednym parametrem – deleguje do kolejnych
    Product(String name) {
        this(name, 0.0); // → wywołuje Product(String, double)
    }

    Product(String name, double price) {
        this(name, price, "General"); // → wywołuje Product(String, double, String)
    }

    // Konstruktor pełny – jedyny z rzeczywistą logiką
    Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return name + " [" + category + "] – " + price + " PLN";
    }
}

class Counter {
    // 1. Inicjalizator pola (wykonuje się pierwszy)
    int value = 10;

    // 2. Blok inicjalizacyjny (wykonuje się po inicjalizatorach pól)
    {
        System.out.println("Blok inicjalizacyjny – aktualna wartość: " + value);
        value += 5;
    }

    // 3. Konstruktor (wykonuje się po bloku inicjalizacyjnym)
    Counter() {
        System.out.println("Konstruktor – aktualna wartość: " + value);
        value += 5;
    }

    int getValue() {
        return value;
    }
}
