package com.example.javaquest._03_collections.Lesson14_Optional;

import java.util.List;
import java.util.Optional;

public class _Lesson14_Optional {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🎁 OPTIONAL – CZYM JEST?
         * ============================================================
         * Optional<T> (java.util.Optional, Java 8+) to kontener,
         * który może zawierać wartość lub być pusty (brak NullPointerException).
         *
         * Problem bez Optional:
         * - metoda zwraca null gdy brak wartości
         * - klient musi pamiętać o sprawdzaniu null
         * - NullPointerException to najczęstszy błąd w Javie
         *
         * Rozwiązanie z Optional:
         * - metoda jawnie sygnalizuje "może nie być wartości"
         * - bogaty API do obsługi braku wartości
         *
         * ⚠️ Kiedy NIE używać Optional:
         * - jako pole klasy (tylko w return types metod)
         * - w kolekcjach (Optional w liście to anty-wzorzec)
         * - dla prymitywów (użyj OptionalInt/Long/Double)
         */

        /*
         * ============================================================
         * 🔹 TWORZENIE OPTIONAL
         * ============================================================
         */

        Optional<String> present = Optional.of("Hello");     // wartość nie-null
        Optional<String> empty = Optional.empty();            // pusty
        Optional<String> nullable = Optional.ofNullable(null); // może być null

        // Optional.of(null) → NullPointerException!
        // Optional.ofNullable(null) → Optional.empty()

        System.out.println("present: " + present);
        System.out.println("empty: " + empty);
        System.out.println("nullable: " + nullable);

        /*
         * ============================================================
         * 🔹 SPRAWDZANIE I WYCIĄGANIE WARTOŚCI
         * ============================================================
         * isPresent()   → boolean – czy jest wartość
         * isEmpty()     → boolean – czy pusty (Java 11+)
         * get()         → T – wartość (NoSuchElementException gdy pusty!)
         * orElse(def)   → T – wartość lub domyślna
         * orElseGet(s)  → T – wartość lub wynik Supplier (lazy)
         * orElseThrow() → T lub NoSuchElementException
         * orElseThrow(Supplier) → T lub podany wyjątek
         */

        System.out.println("\nisPresent: " + present.isPresent());
        System.out.println("isEmpty: " + empty.isEmpty());
        System.out.println("get: " + present.get());
        System.out.println("orElse: " + empty.orElse("Domyślna"));
        System.out.println("orElseGet: " + empty.orElseGet(() -> "Z Supplier"));

        try {
            empty.orElseThrow(() -> new RuntimeException("Brak wartości!"));
        } catch (RuntimeException e) {
            System.out.println("orElseThrow: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔹 ifPresent / ifPresentOrElse
         * ============================================================
         */

        present.ifPresent(v -> System.out.println("\nifPresent: " + v));
        empty.ifPresent(v -> System.out.println("To się nie wypisze"));

        // Java 9+: ifPresentOrElse
        empty.ifPresentOrElse(
                v -> System.out.println("Wartość: " + v),
                () -> System.out.println("ifPresentOrElse: brak wartości")
        );

        /*
         * ============================================================
         * 🔹 map() – transformacja wartości
         * ============================================================
         * Jeśli Optional ma wartość → aplikuje funkcję i zwraca nowy Optional.
         * Jeśli pusty → zwraca pusty Optional.
         */

        System.out.println("\n=== map() ===");
        Optional<String> name = Optional.of("  anna  ");

        Optional<String> upperName = name
                .map(String::trim)
                .map(String::toUpperCase);

        System.out.println("Po map: " + upperName.orElse("brak"));

        // Łańcuch map nie rzuca NPE nawet jeśli gdzieś po drodze null
        Optional<String> result = Optional.<String>empty()
                .map(String::toUpperCase)
                .map(s -> s + "!");
        System.out.println("Empty po map: " + result.orElse("brak"));

        // Praktyczny przykład – bezpieczne wyciąganie zagnieżdżonych pól
        Optional<User> user = Optional.of(new User("Jan", new Address("Warszawa")));
        Optional<User> noUser = Optional.empty();

        String city = user.map(User::getAddress).map(Address::getCity).orElse("Nieznane");
        String noCity = noUser.map(User::getAddress).map(Address::getCity).orElse("Nieznane");
        System.out.println("Miasto: " + city);
        System.out.println("Brak użytkownika → miasto: " + noCity);

        /*
         * ============================================================
         * 🔹 filter() – filtrowanie wartości
         * ============================================================
         * Jeśli wartość spełnia predykat → pozostaje.
         * Jeśli nie spełnia lub pusty → zwraca pusty Optional.
         */

        System.out.println("\n=== filter() ===");
        Optional<Integer> num = Optional.of(42);

        num.filter(n -> n > 10).ifPresent(n -> System.out.println("Liczba > 10: " + n));
        num.filter(n -> n > 100).ifPresentOrElse(
                n -> System.out.println(n),
                () -> System.out.println("Liczba nie spełnia warunku > 100")
        );

        // Walidacja emaila
        Optional<String> email = Optional.of("jan@example.com");
        email.filter(e -> e.contains("@"))
             .ifPresentOrElse(
                     e -> System.out.println("Poprawny email: " + e),
                     () -> System.out.println("Niepoprawny email")
             );

        /*
         * ============================================================
         * 🔹 or() – alternatywny Optional (Java 9+)
         * ============================================================
         * Jeśli pusty → zwraca Optional dostarczony przez Supplier.
         * Różnica od orElse: zwraca Optional<T>, nie T.
         */

        System.out.println("\n=== or() ===");
        Optional<String> primary = Optional.empty();
        Optional<String> fallback = Optional.of("Wartość zapasowa");

        Optional<String> finalValue = primary.or(() -> fallback);
        System.out.println("or(): " + finalValue.orElse("brak"));

        // Łańcuch fallbacków
        Optional<String> value = Optional.<String>empty()
                .or(() -> Optional.empty())
                .or(() -> Optional.of("Trzecia opcja"));
        System.out.println("Łańcuch or: " + value.orElse("brak"));

        /*
         * ============================================================
         * 🔹 Optional w Stream API
         * ============================================================
         */

        List<String> words = List.of("java", "python", "c++", "kotlin");
        Optional<String> firstLong = words.stream()
                .filter(w -> w.length() > 4)
                .findFirst();

        String lang = firstLong.orElse("brak");
        System.out.println("\nPierwszy długi język: " + lang);

        // stream() na Optional (Java 9+) – 0 lub 1 elementów
        long count = firstLong.stream().count();
        System.out.println("Strumień z Optional: " + count + " element(y)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Tworzenie:
         * Optional.of(val)         → nie-null lub NPE
         * Optional.ofNullable(val) → może być null
         * Optional.empty()         → pusty
         *
         * Sprawdzanie:
         * isPresent() / isEmpty()  → boolean
         * get()                    → wartość (uwaga: może rzucić!)
         * orElse(def)              → wartość lub domyślna
         * orElseGet(supplier)      → wartość lub lazy default
         * orElseThrow(supplier)    → wartość lub wyjątek
         *
         * Transformacje:
         * ifPresent(consumer)      → wykona tylko gdy wartość
         * ifPresentOrElse(c, r)    → z fallback akcją
         * map(function)            → transformuj wartość
         * filter(predicate)        → warunkowe zachowanie
         * or(supplier)             → alternatywny Optional (Java 9+)
         */
    }
}

class User {
    private String name;
    private Address address;
    User(String name, Address address) { this.name = name; this.address = address; }
    String getName() { return name; }
    Address getAddress() { return address; }
}

class Address {
    private String city;
    Address(String city) { this.city = city; }
    String getCity() { return city; }
}
