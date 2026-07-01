package com.example.javaquest._02_oop.Lesson06_Polymorphism;

public class _Lesson06_Polymorphism {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🎭 POLIMORFIZM – CZYM JEST?
         * ============================================================
         * Polimorfizm (gr. „wiele form") = jedna zmienna może wskazywać
         * na obiekty różnych typów, a wywoływana metoda zależy
         * od rzeczywistego typu obiektu w czasie wykonania.
         *
         * Dwa rodzaje w Javie:
         * 1. Polimorfizm dynamiczny (runtime) – przesłanianie metod (@Override)
         *    Decyzja o tym, która metoda jest wywołana, zapada w CZASIE DZIAŁANIA.
         *
         * 2. Polimorfizm statyczny (compile-time) – przeciążanie metod (overloading)
         *    Decyzja o tym, która metoda jest wywołana, zapada w CZASIE KOMPILACJI.
         */

        /*
         * ============================================================
         * 🔄 POLIMORFIZM DYNAMICZNY – DYNAMICZNE WIĄZANIE
         * ============================================================
         * Zmienna typu Shape wskazuje na różne obiekty:
         * - Circle, Rectangle, Triangle
         *
         * Wywołanie shape.area() uruchamia metodę właściwą
         * dla rzeczywistego typu obiektu – nie dla typu zmiennej.
         */

        Shape[] shapes = {
                new Circle(5),
                new Rectangle(4, 6),
                new Triangle(3, 8)
        };

        for (Shape shape : shapes) {
            System.out.printf("%-15s: pole=%.2f, obwód=%.2f%n",
                    shape.getClass().getSimpleName(),
                    shape.area(),
                    shape.perimeter());
        }

        /*
         * ============================================================
         * 📊 POLIMORFIZM STATYCZNY – PRZECIĄŻANIE METOD (overloading)
         * ============================================================
         * Kilka metod o tej samej nazwie, ale różnych listach parametrów.
         * Kompilator wybiera właściwą na etapie kompilacji.
         *
         * Przeciążanie ≠ przesłanianie:
         * - overloading: różne sygnatury, ta sama klasa
         * - overriding:  ta sama sygnatura, różne klasy w hierarchii
         */

        Printer printer = new Printer();
        printer.print("Witaj");       // String
        printer.print(42);            // int
        printer.print(3.14);          // double
        printer.print("Pi: ", 3.14);  // String + double

        /*
         * ============================================================
         * 🔍 instanceof – SPRAWDZANIE TYPU
         * ============================================================
         * Operator instanceof sprawdza, czy obiekt jest instancją danej klasy.
         * Po sprawdzeniu można bezpiecznie downcastować.
         *
         * Od Java 16: pattern matching (instanceof z jednoczesnym przypisaniem)
         */

        Shape someShape = new Circle(3);

        if (someShape instanceof Circle c) { // pattern matching (Java 16+)
            System.out.println("To koło o promieniu: " + c.radius);
        }

        // Tradycyjne sprawdzenie (Java < 16):
        if (someShape instanceof Circle) {
            Circle circle = (Circle) someShape;
            System.out.println("Tradycyjnie: promień = " + circle.radius);
        }

        /*
         * ============================================================
         * 📋 POLIMORFIZM W PRAKTYCE – KOLEKCJA RÓŻNYCH TYPÓW
         * ============================================================
         * Przechowywanie różnych obiektów w jednej tablicy/liście
         * możliwe dzięki wspólnemu nadtypowi.
         */

        Animal[] animals = {
                new AnimalDog("Azor"),
                new AnimalCat("Mruczek"),
                new AnimalDog("Reksio"),
                new AnimalCat("Kicia")
        };

        System.out.println("\n--- Dźwięki zwierząt ---");
        for (Animal animal : animals) {
            animal.makeSound(); // każdy typ woła swoje makeSound()
        }

        double totalArea = calculateTotalArea(shapes);
        System.out.printf("%nŁączne pole wszystkich figur: %.2f%n", totalArea);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - polimorfizm dynamiczny: @Override + zmienna nadtypu
         * - metoda wywoływana zależy od rzeczywistego typu (dynamiczne wiązanie)
         * - polimorfizm statyczny: overloading – różne sygnatury tej samej metody
         * - instanceof: bezpieczne sprawdzenie typu przed downcastem
         * - kluczowa korzyść: kod niezależny od konkretnego podtypu
         */
    }

    // Metoda przyjmuje tablicę Shape – nie wie nic o konkretnych typach
    static double calculateTotalArea(Shape[] shapes) {
        double total = 0;
        for (Shape s : shapes) {
            total += s.area();
        }
        return total;
    }
}

abstract class Shape {
    abstract double area();
    abstract double perimeter();
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends Shape {
    double width, height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }

    @Override
    double perimeter() {
        return 2 * (width + height);
    }
}

class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }

    @Override
    double perimeter() {
        return 3 * base; // uproszczenie dla trójkąta równobocznego
    }
}

class Printer {
    void print(String text) {
        System.out.println("[String] " + text);
    }

    void print(int number) {
        System.out.println("[int] " + number);
    }

    void print(double number) {
        System.out.println("[double] " + number);
    }

    void print(String label, double number) {
        System.out.println("[String+double] " + label + number);
    }
}

abstract class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    abstract void makeSound();
}

class AnimalDog extends Animal {
    AnimalDog(String name) {
        super(name);
    }

    @Override
    void makeSound() {
        System.out.println(name + ": Hau hau!");
    }
}

class AnimalCat extends Animal {
    AnimalCat(String name) {
        super(name);
    }

    @Override
    void makeSound() {
        System.out.println(name + ": Miau miau!");
    }
}
