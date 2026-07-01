package com.example.javaquest._02_oop.Lesson13_Enums;

import java.util.EnumMap;
import java.util.EnumSet;

public class _Lesson13_Enums {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🃏 ENUM – TYPY WYLICZENIOWE
         * ============================================================
         * enum to specjalny typ klasy, który definiuje zbiór STAŁYCH wartości.
         *
         * Kiedy używać?
         * - gdy zmienna może przyjmować tylko kilka określonych wartości
         * - zamiast stałych int/String (int NORTH=1, SOUTH=2...)
         *
         * Zalety enumów nad stałymi int/String:
         * ✅ type-safe (kompilator sprawdza poprawność wartości)
         * ✅ czytelniejszy kod
         * ✅ wbudowane metody: name(), ordinal(), values(), valueOf()
         * ✅ można dodawać pola, konstruktory, metody
         */

        /*
         * ============================================================
         * 🔹 PODSTAWOWY ENUM
         * ============================================================
         */

        Direction dir = Direction.NORTH;
        System.out.println("Kierunek: " + dir);            // NORTH
        System.out.println("Nazwa: " + dir.name());        // NORTH
        System.out.println("Porządkowy: " + dir.ordinal()); // 0

        Direction[] allDirections = Direction.values();
        System.out.print("Wszystkie kierunki: ");
        for (Direction d : allDirections) {
            System.out.print(d + " ");
        }
        System.out.println();

        // Konwersja String → enum
        Direction fromString = Direction.valueOf("SOUTH");
        System.out.println("Z String: " + fromString);

        /*
         * ============================================================
         * 🔹 SWITCH Z ENUMEM
         * ============================================================
         */

        Season season = Season.SUMMER;

        String description = switch (season) {
            case SPRING -> "Wiosna – czas kwitnienia";
            case SUMMER -> "Lato – czas urlopów";
            case AUTUMN -> "Jesień – czas opadających liści";
            case WINTER -> "Zima – czas śniegu";
        };
        System.out.println(description);

        /*
         * ============================================================
         * 🔹 ENUM Z POLAMI I KONSTRUKTOREM
         * ============================================================
         * Enum może mieć pola, konstruktor i metody.
         * Konstruktor jest ZAWSZE prywatny (z definicji).
         * Pola inicjalizowane przy deklaracji każdej stałej.
         */

        Planet earth = Planet.EARTH;
        System.out.println("Planeta: " + earth.getName());
        System.out.println("Masa: " + earth.getMassKg() + " kg");
        System.out.println("Promień: " + earth.getRadiusKm() + " km");

        for (Planet p : Planet.values()) {
            System.out.printf("%-10s grawitacja: %.2f m/s²%n",
                    p.getName(), p.surfaceGravity());
        }

        /*
         * ============================================================
         * 🔹 ENUM Z METODĄ ABSTRAKCYJNĄ
         * ============================================================
         * Każda stała enuma może mieć własną implementację metody.
         */

        System.out.println("Dostawa EXPRESS: " + DeliveryType.EXPRESS.getEstimate());
        System.out.println("Dostawa STANDARD: " + DeliveryType.STANDARD.getEstimate());
        System.out.println("Dostawa ECONOMY: " + DeliveryType.ECONOMY.getEstimate());

        double price = 100.0;
        for (DeliveryType type : DeliveryType.values()) {
            System.out.printf("%s: %.2f PLN%n", type, type.calculateCost(price));
        }

        /*
         * ============================================================
         * 🔹 EnumSet I EnumMap
         * ============================================================
         * EnumSet: zestaw wartości enum – bardzo wydajny (bitmask wewnętrznie)
         * EnumMap: mapa z kluczem enum – wydajniejsza niż HashMap<Enum, V>
         */

        EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        EnumSet<Day> weekdays = EnumSet.complementOf(weekend);

        System.out.println("Weekend: " + weekend);
        System.out.println("Dni robocze: " + weekdays);
        System.out.println("Czy sobota to weekend? " + weekend.contains(Day.SATURDAY));

        EnumMap<Season, String> activities = new EnumMap<>(Season.class);
        activities.put(Season.SPRING, "Jazda na rowerze");
        activities.put(Season.SUMMER, "Pływanie");
        activities.put(Season.AUTUMN, "Grzybobranie");
        activities.put(Season.WINTER, "Narty");

        for (var entry : activities.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - enum: zbiór nazwanych stałych, type-safe
         * - name(), ordinal(), values(), valueOf() – wbudowane metody
         * - enum może mieć pola, konstruktory (private), metody
         * - abstract method w enum: każda stała ma swoją implementację
         * - switch z enum: od Java 14+ wyrażenie switch (bez default jeśli wyczerpany)
         * - EnumSet / EnumMap: wydajne kolekcje dedykowane dla enumów
         */
    }
}

enum Direction {
    NORTH, SOUTH, EAST, WEST
}

enum Season {
    SPRING, SUMMER, AUTUMN, WINTER
}

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

enum Planet {
    MERCURY("Merkury", 3.303e+23, 2.4397e6),
    VENUS("Wenus", 4.869e+24, 6.0518e6),
    EARTH("Ziemia", 5.976e+24, 6.37814e6),
    MARS("Mars", 6.421e+23, 3.3972e6);

    private final String name;
    private final double massKg;
    private final double radiusKm;

    Planet(String name, double massKg, double radiusKm) {
        this.name = name;
        this.massKg = massKg;
        this.radiusKm = radiusKm;
    }

    String getName() { return name; }
    double getMassKg() { return massKg; }
    double getRadiusKm() { return radiusKm; }

    double surfaceGravity() {
        final double G = 6.67300E-11;
        return G * massKg / (radiusKm * radiusKm);
    }
}

enum DeliveryType {
    EXPRESS {
        @Override
        public String getEstimate() { return "1 dzień roboczy"; }

        @Override
        public double calculateCost(double orderValue) { return orderValue * 0.10 + 20; }
    },
    STANDARD {
        @Override
        public String getEstimate() { return "3-5 dni roboczych"; }

        @Override
        public double calculateCost(double orderValue) { return orderValue * 0.05 + 10; }
    },
    ECONOMY {
        @Override
        public String getEstimate() { return "7-14 dni roboczych"; }

        @Override
        public double calculateCost(double orderValue) { return 5.0; }
    };

    public abstract String getEstimate();
    public abstract double calculateCost(double orderValue);
}
