package com.example.javaquest._28_java_evolution.Lesson04_Java8DefaultAndStaticInterfaceMethods;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson04_Java8DefaultAndStaticInterfaceMethods {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: Java 8 (2014) - metody default i static w interfejsach ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_02_oop/Lesson08_Interfaces`
         * ============================================================
         * PRZED Java 8: interfejs MOGL miec WYLACZNIE metody
         * ABSTRAKCYJNE (BEZ ciala) - DODANIE NOWEJ metody DO
         * ISTNIEJACEGO interfejsu ZAWSZE LAMALO WSZYSTKIE klasy JUZ
         * go implementujace (musialy DOPISAC implementacje). TO BYL
         * GLOWNY POWOD wprowadzenia metod `default` W Javie 8 -
         * UMOZLIWIC EWOLUCJE API bez lamania wstecznej kompatybilnosci
         * (KLASYCZNY przyklad: dodanie `default void forEach(...)` DO
         * `Iterable` W SAMEJ Javie 8 - MILIONY ISTNIEJACYCH klas
         * implementujacych `Iterable` "ZA DARMO" DOSTALY NOWA metode).
         *
         * 🔍 Metody `static` W interfejsie - "FUNKCJE NARZEDZIOWE"
         * POWIAZANE Z interfejsem (np. `Comparator.naturalOrder()`),
         * BEZ POTRZEBY osobnej klasy "Utils".
         */
        System.out.println("Metody default (Java 8) = EWOLUCJA API BEZ lamania wstecznej kompatybilnosci. Metody static = narzedziowe funkcje NA interfejsie.");

        demonstrateBeforeAfterDefaultMethod();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `default void metoda() { ... }` W interfejsie - MA CIALO,
         *   klasy implementujace MOGA (ale NIE MUSZA) NADPISAC.
         * - `static void metoda() { ... }` W interfejsie - WYWOLYWANA
         *   NA SAMYM interfejsie (`Interfejs.metoda()`), NIE NA
         *   instancji.
         * - KLASYCZNY przyklad Z JDK: `Comparator.reversed()`
         *   (default) + `Comparator.naturalOrder()` (static).
         * - POGLEBIONA teoria (diamond problem, kolejnosc
         *   pierwszenstwa KLASA > interfejs): `_02_oop/Lesson08`.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    // PRZED Java 8: interfejs TYLKO Z metodami abstrakcyjnymi.
    interface Vehicle {
        String getName();

        int getMaxSpeed();
    }

    // PO Java 8: DODAJEMY nowa metode `default` - WSZYSTKIE ISTNIEJACE implementacje
    // (jak GDYBY 'Vehicle' bylo interfejsem Z BIBLIOTEKI, KTOREJ NIE MOZEMY zmienic ZBIOROWO)
    // DOSTAJA JA "ZA DARMO", BEZ konieczności zmiany.
    interface VehicleWithDescription {
        String getName();

        int getMaxSpeed();

        default String describe() {
            return getName() + " (max " + getMaxSpeed() + " km/h)";
        }

        static VehicleWithDescription of(String name, int maxSpeed) {
            return new VehicleWithDescription() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public int getMaxSpeed() {
                    return maxSpeed;
                }
            };
        }
    }

    static class Car implements VehicleWithDescription {
        @Override
        public String getName() {
            return "Samochod";
        }

        @Override
        public int getMaxSpeed() {
            return 180;
        }
        // Car NIE MUSI implementowac describe() - DOSTAJE JA "ZA DARMO" Z interfejsu.
    }

    static class SportsCar implements VehicleWithDescription {
        @Override
        public String getName() {
            return "Samochod sportowy";
        }

        @Override
        public int getMaxSpeed() {
            return 320;
        }

        @Override
        public String describe() {
            // SportsCar MOZE (opcjonalnie) NADPISAC domyslna implementacje.
            return "🏎 " + VehicleWithDescription.super.describe();
        }
    }

    private static void demonstrateBeforeAfterDefaultMethod() {
        System.out.println("\n--- Car - UZYWA domyslnej implementacji describe() 'za darmo' ---");
        Car car = new Car();
        System.out.println(car.describe());
        assertThat(car.describe()).isEqualTo("Samochod (max 180 km/h)");

        System.out.println("\n--- SportsCar - NADPISUJE domyslna implementacje (super.describe()) ---");
        SportsCar sportsCar = new SportsCar();
        System.out.println(sportsCar.describe());
        assertThat(sportsCar.describe()).contains("Samochod sportowy (max 320 km/h)");

        System.out.println("\n--- Metoda static NA interfejsie - fabryka BEZ osobnej klasy Utils ---");
        VehicleWithDescription bike = VehicleWithDescription.of("Rower", 30);
        System.out.println(bike.describe());
        assertThat(bike.describe()).isEqualTo("Rower (max 30 km/h)");
    }
}
