package com.example.javaquest._02_oop.Lesson01_ClassesAndObjects;

public class _Lesson01_ClassesAndObjects {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🏗️ CZYM JEST KLASA I OBIEKT?
         * ============================================================
         * Klasa to szablon (blueprint) opisujący:
         * - POLA (fields) – dane, które obiekt przechowuje
         * - METODY (methods) – zachowania, które obiekt może wykonać
         *
         * Obiekt to konkretna instancja klasy, tworzona przez `new`.
         * Każdy obiekt ma własne pola, ale współdzieli metody z klasą.
         */

        Car car1 = new Car("Toyota", "Corolla", 2022);
        Car car2 = new Car("BMW", "M3", 2023);

        car1.displayInfo();
        car2.displayInfo();

        System.out.println("Marka car1: " + car1.brand);
        System.out.println("Rok car2: " + car2.year);

        /*
         * ============================================================
         * 🔧 POLA (FIELDS)
         * ============================================================
         * - Pola instancyjne: każdy obiekt ma własną kopię
         * - Pola statyczne: wspólne dla wszystkich instancji (klasy)
         * - Inicjalizacja domyślna: int→0, double→0.0, boolean→false, Object→null
         */

        Car car3 = new Car();
        System.out.println("Domyślna marka: " + car3.brand); // null

        System.out.println("Liczba samochodów: " + Car.count); // pole statyczne

        /*
         * ============================================================
         * ⚙️ METODY (METHODS)
         * ============================================================
         * Metoda to nazwany blok kodu, który:
         * - może przyjmować parametry
         * - może zwracać wartość (lub void – brak wartości)
         * - definiuje zachowanie obiektu
         *
         * Sygnatura: [modyfikatory] typ_zwracany nazwa(parametry)
         */

        int speed = car1.accelerate(30);
        System.out.println("Prędkość po przyspieszeniu: " + speed);

        boolean fast = car1.isFasterThan(100);
        System.out.println("Czy jedzie szybciej niż 100? " + fast);

        /*
         * ============================================================
         * 🔑 SŁOWO KLUCZOWE this
         * ============================================================
         * `this` odnosi się do bieżącego obiektu.
         * Używane gdy:
         * - parametr metody ma taką samą nazwę jak pole klasy
         * - chcemy zwrócić bieżący obiekt (method chaining)
         */

        car1.rename("Toyota", "Supra");
        car1.displayInfo();

        /*
         * ============================================================
         * 📦 KONSTRUKTORY
         * ============================================================
         * Konstruktor = specjalna metoda wywoływana przy tworzeniu obiektu (new).
         * - Ta sama nazwa co klasa
         * - Brak typu zwracanego (nawet void)
         * - Może być przeciążony (kilka wersji z różnymi parametrami)
         * - Jeśli nie zdefiniujesz, Java doda domyślny bezargumentowy
         */

        Car luxuryCar = new Car("Mercedes", "S-Class", 2024, "silver");
        luxuryCar.displayInfo();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - klasa = szablon / typ
         * - obiekt = konkretna instancja klasy (new)
         * - pola = dane obiektu
         * - metody = zachowania obiektu
         * - this = referencja do bieżącego obiektu
         * - konstruktor = specjalna metoda inicjalizująca obiekt
         */
    }
}

class Car {
    // Pola instancyjne
    String brand;
    String model;
    int year;
    String color;
    int currentSpeed;

    // Pole statyczne – wspólne dla wszystkich instancji
    static int count = 0;

    // Konstruktor bezargumentowy
    Car() {
        count++;
    }

    // Konstruktor z parametrami
    Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = "white";
        count++;
    }

    // Konstruktor z 4 parametrami (przeciążenie)
    Car(String brand, String model, int year, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        count++;
    }

    // Metoda void – wypisuje dane, nic nie zwraca
    void displayInfo() {
        System.out.println(year + " " + brand + " " + model + " [" + color + "], prędkość: " + currentSpeed);
    }

    // Metoda zwracająca wartość
    int accelerate(int amount) {
        currentSpeed += amount;
        return currentSpeed;
    }

    // Metoda z parametrem i wartością zwrotną boolean
    boolean isFasterThan(int threshold) {
        return currentSpeed > threshold;
    }

    // Metoda używająca this do odróżnienia pól od parametrów
    void rename(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }
}
