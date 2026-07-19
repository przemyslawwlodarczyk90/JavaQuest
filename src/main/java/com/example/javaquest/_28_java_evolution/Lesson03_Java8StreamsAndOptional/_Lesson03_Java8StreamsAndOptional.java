package com.example.javaquest._28_java_evolution.Lesson03_Java8StreamsAndOptional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson03_Java8StreamsAndOptional {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: Java 8 (2014) - Streamy i Optional ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE SZCZEGOLOWO W `_03_collections`
         * ============================================================
         * Streamy (`_03_collections/Lesson16-19`, 4 CALE lekcje) I
         * `Optional` (`_03_collections/Lesson21`) TO DRUGA (obok
         * lambd, Lesson02) WIELKA nowosc Javy 8 - PRZETWARZANIE
         * kolekcji BEZ recznych petli, I EKSPLICYTNE modelowanie
         * "BRAKU wartosci" BEZ `null`.
         *
         * 🔍 TA lekcja NIE POWTARZA API (znasz JUZ `.filter()`/
         * `.map()`/`.collect()`/`.orElseGet()` SZCZEGOLOWO) - TU
         * DEMO POKAZUJE "PRZED/PO": JAK wygladalby TEN SAM kod BEZ
         * Streamow (petla+`if`) I BEZ `Optional` (`null`+`if`).
         */
        System.out.println("Streamy + Optional (Java 8) = DRUGA rewolucja OBOK lambd. Pelna teoria: _03_collections/Lesson16-21.");

        demonstrateStreamsBeforeAfter();
        demonstrateOptionalBeforeAfter();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Stream API: DEKLARATYWNE przetwarzanie danych
         *   (`.filter().map().collect()`) ZAMIAST IMPERATYWNYCH petli.
         * - `Optional<T>`: JAWNY typ "MOZE nie byc wartosci" W
         *   SYGNATURZE metody - kompilator WYMUSZA obsluge (W
         *   ODROZNIENIU OD `null`, KTORY MOZNA po prostu ZAPOMNIEC
         *   sprawdzic - `NullPointerException`).
         * - Pelna, POGLEBIONA teoria (`.reduce()`, kolektory,
         *   `Optional.map()`/`flatMap()`, Streamy rownolegle): CALY
         *   `_03_collections/Lesson16-21`.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateStreamsBeforeAfter() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("\n--- PRZED Java 8: reczna petla + warunek ---");
        int sumOfSquaresOfEvenOld = 0;
        for (int n : numbers) {
            if (n % 2 == 0) {
                sumOfSquaresOfEvenOld += n * n;
            }
        }
        System.out.println("Suma kwadratow liczb parzystych (petla): " + sumOfSquaresOfEvenOld);

        System.out.println("\n--- PO Java 8: Stream (deklaratywnie) ---");
        int sumOfSquaresOfEvenNew = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n)
                .sum();
        System.out.println("Suma kwadratow liczb parzystych (Stream): " + sumOfSquaresOfEvenNew);

        assertThat(sumOfSquaresOfEvenOld).isEqualTo(sumOfSquaresOfEvenNew);
    }

    record User(String name, String email) {
    }

    private static User findUserByNameOldStyle(List<User> users, String name) {
        for (User u : users) {
            if (u.name().equals(name)) {
                return u;
            }
        }
        return null; // PRZED Java 8 - BRAK wartosci = null, LATWO ZAPOMNIEC sprawdzic.
    }

    private static Optional<User> findUserByNameNewStyle(List<User> users, String name) {
        return users.stream().filter(u -> u.name().equals(name)).findFirst();
    }

    private static void demonstrateOptionalBeforeAfter() {
        List<User> users = List.of(new User("Anna", "anna@example.com"), new User("Piotr", "piotr@example.com"));

        System.out.println("\n--- PRZED Java 8: null jako 'brak wartosci' (RYZYKOWNE) ---");
        User foundOld = findUserByNameOldStyle(users, "Anna");
        String emailOld = foundOld != null ? foundOld.email() : "BRAK";
        System.out.println("Znaleziono (null-style): " + emailOld);

        User notFoundOld = findUserByNameOldStyle(users, "Zenon");
        System.out.println("Kod BEZ sprawdzenia null RZUCILBY NullPointerException: " + (notFoundOld == null));

        System.out.println("\n--- PO Java 8: Optional (JAWNY typ 'moze nie byc wartosci') ---");
        Optional<User> foundNew = findUserByNameNewStyle(users, "Anna");
        String emailNew = foundNew.map(User::email).orElse("BRAK");
        System.out.println("Znaleziono (Optional): " + emailNew);

        Optional<User> notFoundNew = findUserByNameNewStyle(users, "Zenon");
        assertThat(notFoundNew).isEmpty();
        assertThatThrownBy(notFoundNew::get).isInstanceOf(java.util.NoSuchElementException.class);
        System.out.println("Optional.get() NA pustym Optional RZUCA CZYTELNY NoSuchElementException (NIE cichy null).");

        assertThat(emailOld).isEqualTo(emailNew);
    }
}
