package com.example.javaquest._02_oop.Lesson14_Records;

import java.util.List;
import java.util.Objects;

public class _Lesson14_Records {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 RECORDS – NIEZMIENNE KLASY DANYCH (Java 16+)
         * ============================================================
         * Record to specjalny rodzaj klasy do przechowywania niezmiennych danych.
         *
         * Jedna linijka zastępuje klasę z:
         * - private final polami
         * - konstruktorem
         * - getterami (nazwa pola bez "get")
         * - toString()
         * - equals()
         * - hashCode()
         *
         * Ograniczenia recordów:
         * - pola są zawsze final (immutable)
         * - nie można extends (implicit extends Record)
         * - mogą implements interface
         * - mogą mieć metody instancyjne i statyczne
         */

        /*
         * ============================================================
         * 🔹 PODSTAWOWY RECORD
         * ============================================================
         */

        Point point = new Point(3, 7);
        System.out.println("Point: " + point);      // toString() auto
        System.out.println("x: " + point.x());      // getter = nazwa pola (bez "get")
        System.out.println("y: " + point.y());

        Point p2 = new Point(3, 7);
        System.out.println("equals: " + point.equals(p2)); // true – porównuje wartości
        System.out.println("hashCode: " + (point.hashCode() == p2.hashCode())); // true

        /*
         * ============================================================
         * 🔹 RECORD Z METODAMI I WALIDACJĄ
         * ============================================================
         * Record może zawierać:
         * - metody instancyjne
         * - metody statyczne
         * - kompaktowy konstruktor (compact constructor) – walidacja
         */

        try {
            Temperature t1 = new Temperature(100, "C");
            System.out.println("Temperatura: " + t1);
            System.out.println("W Fahrenheit: " + t1.toFahrenheit());

            Temperature t2 = new Temperature(-300, "C"); // ❌ poniżej zera absolutnego
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔹 KOMPAKTOWY KONSTRUKTOR (compact constructor)
         * ============================================================
         * Specjalna forma konstruktora w rekordzie.
         * Nie wymaga podawania parametrów – są automatycznie dostępne.
         * Przypisanie do pól następuje AUTOMATYCZNIE po treści konstruktora.
         * Używany do WALIDACJI lub NORMALIZACJI danych wejściowych.
         *
         * record MyRecord(String name) {
         *     MyRecord { // compact – bez (String name)
         *         name = name.trim(); // normalizacja
         *     }
         * }
         */

        Person person = new Person("  Jan  ", "  Kowalski  ", 30);
        System.out.println("Osoba: " + person); // imię/nazwisko będzie trimowane

        /*
         * ============================================================
         * 🔹 RECORD IMPLEMENTUJE INTERFEJS
         * ============================================================
         */

        Shape circle = new CircleShape(5.0);
        Shape rect = new RectShape(4.0, 6.0);

        List<Shape> shapes = List.of(circle, rect);
        for (Shape s : shapes) {
            System.out.printf("%s → pole=%.2f%n", s, s.area());
        }

        /*
         * ============================================================
         * 🔹 ZAGNIEŻDŻONE REKORDY
         * ============================================================
         */

        Address address = new Address("Warszawska 10", "Kraków", "30-000");
        Employee emp = new Employee("Anna Nowak", "anna@firma.pl", address);
        System.out.println("Pracownik: " + emp);
        System.out.println("Miasto: " + emp.address().city());

        /*
         * ============================================================
         * ⚖️ RECORD vs CLASS vs LOMBOK @Data
         * ============================================================
         *
         * Record (Java 16+):
         * ✅ wbudowany w język
         * ✅ automatyczny toString/equals/hashCode
         * ❌ nie można dziedziczyć
         * ❌ pola zawsze final (immutable)
         *
         * Zwykła klasa:
         * ✅ pełna elastyczność (mutable, inheritance)
         * ❌ dużo boilerplate kodu
         *
         * Kiedy używać recordu?
         * - Data Transfer Objects (DTO)
         * - Value Objects (adres, pieniądze, punkt)
         * - klucze do map (dobry hashCode/equals)
         * - odpowiedzi z API
         *
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - record = immutable class z auto-wygenerowanymi metodami
         * - gettery = nazwy pól bez "get" (x(), name())
         * - compact constructor: walidacja/normalizacja bez super()
         * - record może implements interface, mieć metody, static factory
         * - record nie może extends (jest final)
         * - idealne dla DTO, value objects, klucze Map
         */
    }
}

record Point(int x, int y) {}

record Temperature(double value, String unit) {
    // Kompaktowy konstruktor – walidacja
    Temperature {
        if (unit.equals("C") && value < -273.15) {
            throw new IllegalArgumentException("Temperatura poniżej zera absolutnego: " + value);
        }
        if (unit.equals("K") && value < 0) {
            throw new IllegalArgumentException("Kelwin nie może być ujemny");
        }
    }

    // Metoda instancyjna
    double toFahrenheit() {
        return unit.equals("C") ? value * 9 / 5 + 32 : value;
    }

    // Statyczna metoda fabryczna
    static Temperature fromFahrenheit(double f) {
        return new Temperature((f - 32) * 5 / 9, "C");
    }
}

record Person(String firstName, String lastName, int age) {
    // Compact constructor – normalizacja danych
    Person {
        firstName = firstName.trim();
        lastName = lastName.trim();
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Niepoprawny wiek: " + age);
        }
    }

    // Metoda instancyjna
    String fullName() {
        return firstName + " " + lastName;
    }
}

interface Shape {
    double area();
}

record CircleShape(double radius) implements Shape {
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

record RectShape(double width, double height) implements Shape {
    @Override
    public double area() {
        return width * height;
    }
}

record Address(String street, String city, String zipCode) {}

record Employee(String name, String email, Address address) {}
