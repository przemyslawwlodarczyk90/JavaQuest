package com.example.javaquest._16_clean_code.Lesson18_NullHandling;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class _Lesson18_NullHandling {

    public static void main(String[] args) {
        System.out.println("=== LEKCJA 18: OBSLUGA NULL ===");

        /*
         * ============================================================
         * 🔹 "MILIARDOWY BŁĄD" TONY'EGO HOARE'A
         * ============================================================
         * Tony Hoare, twórca referencji null (w języku ALGOL W, 1965),
         * nazwał ją publicznie w 2009 roku "swoim miliardowym błędem"
         * ("my billion-dollar mistake") - bo koszt błędów spowodowanych
         * przez null (awarie, godziny debugowania, incydenty produkcyjne)
         * na przestrzeni dekad oszacował właśnie na ten rząd wielkości.
         *
         * Dlaczego null jest tak niebezpieczny?
         * - Referencja typu `String imie` SUGERUJE "tu jest String", ale
         *   kompilator NIE odróżnia "tu jest String" od "tu może nie być
         *   nic" - obie sytuacje mają dokładnie ten sam typ.
         * - Błąd ujawnia się dopiero w RUNTIME (NullPointerException),
         *   często daleko od miejsca, gdzie null powstał - a nie w czasie
         *   kompilacji, gdzie kosztowałby ułamek czasu na naprawę.
         * - Java (w odróżnieniu np. od Kotlina z jego `String?` vs `String`)
         *   nie ma wbudowanego w system typów rozróżnienia "może być null"
         *   vs "na pewno nie jest null" - musimy to dyscyplinować sami.
         */
        demonstrateNpeProblem();

        /*
         * ============================================================
         * 🔹 STRATEGIA 1: FAIL-FAST - WALIDACJA NA WEJŚCIU
         * ============================================================
         * Zamiast pozwolić, żeby null "przeciekł" głęboko w kod i wybuchł
         * NullPointerException w losowym miejscu (z bezużytecznym stack
         * trace'em), sprawdzamy argumenty NA WEJŚCIU metody i rzucamy
         * czytelny wyjątek NATYCHMIAST - to jest "fail fast".
         *
         * `Objects.requireNonNull(arg)` - rzuca NullPointerException,
         * ale w MIEJSCU wywołania, z jasnym komunikatem.
         * `Objects.requireNonNull(arg, "opis")` - własny komunikat,
         * dużo łatwiejszy do zdiagnozowania niż goły NPE bez opisu.
         *
         * Efekt: błąd ujawnia się w konstruktorze/na wejściu metody,
         * a nie 5 wywołań metod dalej, gdzie ślad prowadzący do
         * przyczyny już dawno się urwał.
         */
        demonstrateFailFastValidation();

        /*
         * ============================================================
         * 🔹 STRATEGIA 2: OPTIONAL<T> JAKO TYP ZWRACANY
         * ============================================================
         * API klasy Optional<T> (isPresent/map/orElse/orElseThrow itd.)
         * już dokładnie poznałeś w _03_collections/Lesson14_Optional -
         * tutaj NIE powtarzamy tamtej mechaniki. Nacisk jest inny:
         * KIEDY i DLACZEGO w ogóle sięgać po Optional jako element
         * projektowania API, a nie tylko jako "kolejny kontener".
         *
         * Reguła: Optional<T> jako typ zwracany metody jest właściwy
         * WTEDY i TYLKO WTEDY, gdy "brak wyniku" jest NORMALNYM,
         * oczekiwanym przypadkiem biznesowym - np. "znajdź użytkownika
         * po e-mailu" (może go po prostu nie być) - a NIE gdy brak
         * wyniku oznacza błąd programisty (wtedy lepszy jest wyjątek)
         * albo gdy zwracamy kolekcję (wtedy - patrz niżej - pusta
         * kolekcja, NIGDY Optional<List<T>>).
         *
         * Optional sygnalizuje w SYGNATURZE metody (a nie tylko
         * w komentarzu) "ta metoda może nie mieć czego zwrócić" -
         * kompilator wymusza obsłużenie tego przypadku u wywołującego.
         */
        demonstrateOptionalAsReturnType();

        /*
         * ============================================================
         * 🔹 STRATEGIA 3: WZORZEC NULL OBJECT
         * ============================================================
         * Zamiast zwracać null, gdy "nie ma czego zwrócić", zwracamy
         * SPECJALNY obiekt reprezentujący "brak" - który implementuje
         * ten sam interfejs/typ i ma BEZPIECZNE, NO-OP zachowanie.
         *
         * Zaleta: wywołujący kod NIE musi w ogóle sprawdzać `if (x !=
         * null)` przed każdym użyciem - wywołanie metody na Null Object
         * po prostu nic nie robi (albo zwraca neutralną wartość), zamiast
         * wybuchać NullPointerException.
         *
         * Kiedy stosować: gdy "brak" ma jedno, dobrze zdefiniowane,
         * neutralne zachowanie (np. "brak rabatu" = rabat 0%, "gość bez
         * zalogowania" = użytkownik o pustych uprawnieniach). Gdy
         * "brak" ma być traktowany inaczej w różnych miejscach kodu -
         * Optional pasuje lepiej, bo wymusza jawną decyzję u wywołującego.
         */
        demonstrateNullObjectPattern();

        /*
         * ============================================================
         * 🔹 STRATEGIA 4: NIGDY NIE ZWRACAJ NULL Z METOD KOLEKCJOWYCH
         * ============================================================
         * Jeśli metoda ma typ zwracany List<T>/Set<T>/Map<K,V> itp.,
         * a "wynik" jest pusty - zwróć PUSTĄ kolekcję (`List.of()`,
         * `Collections.emptyList()`), NIGDY null.
         *
         * Dlaczego to takie ważne: pusta kolekcja i null wyglądają
         * niewinnie identycznie w kodzie wywołującym ("chyba dostałem
         * listę") - dopóki ktoś nie napisze `for (String s : wynik)`
         * albo `wynik.size()` bez sprawdzenia null. Pusta kolekcja
         * obsługuje się w pętli/streamie TAK SAMO jak niepusta (zero
         * iteracji) - null wymaga osobnego, łatwego do zapomnienia
         * sprawdzenia w KAŻDYM miejscu użycia.
         */
        demonstrateEmptyCollectionInsteadOfNull();

        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    // ============================================================
    // DEMO 1: Problem - metoda zwracająca null powoduje NPE
    // ============================================================
    private static void demonstrateNpeProblem() {
        System.out.println("\n--- DEMO: null jako 'cichy' problem ---");

        UserRepositoryBad repo = new UserRepositoryBad();
        String email = repo.findEmailById("nieznane-id");
        System.out.println("Znaleziony e-mail (bez sprawdzenia null): " + email);

        try {
            // Wygląda niewinnie - dopóki nie wybuchnie, i to daleko
            // od PRAWDZIWEJ przyczyny (metody findEmailById).
            int dlugosc = email.length();
            System.out.println("Dlugosc e-maila: " + dlugosc);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException zlapany tutaj - ale PRZYCZYNA");
            System.out.println("(zwrocenie null z findEmailById) jest gdzies indziej -");
            System.out.println("dokladnie to jest sedno problemu: blad ujawnia sie");
            System.out.println("daleko od miejsca, gdzie powstal.");
        }
    }

    static class UserRepositoryBad {
        String findEmailById(String id) {
            // Symulacja "nie znaleziono" przez zwrocenie null - zly pomysl.
            return null;
        }
    }

    // ============================================================
    // DEMO 2: Objects.requireNonNull - fail-fast na wejsciu
    // ============================================================
    private static void demonstrateFailFastValidation() {
        System.out.println("\n--- DEMO: fail-fast z Objects.requireNonNull ---");

        try {
            registerUser("konto@example.com", null);
        } catch (NullPointerException e) {
            System.out.println("Zlapany NPE OD RAZU, w miejscu wywolania: " + e.getMessage());
            System.out.println("(zamiast 10 metod dalej, gdzie 'imie' zostaloby uzyte");
            System.out.println("bez zwiazku przyczynowego widocznego w stack trace)");
        }

        registerUser("konto@example.com", "Jan Kowalski");
    }

    private static void registerUser(String email, String imieINazwisko) {
        Objects.requireNonNull(email, "email nie moze byc null");
        Objects.requireNonNull(imieINazwisko, "imieINazwisko nie moze byc null");
        System.out.println("Zarejestrowano uzytkownika: " + imieINazwisko + " <" + email + ">");
    }

    // ============================================================
    // DEMO 3: Optional jako typ zwracany - KIEDY, nie API
    // ============================================================
    private static void demonstrateOptionalAsReturnType() {
        System.out.println("\n--- DEMO: Optional<T> jako sygnal 'moze nie byc wyniku' ---");

        UserRepositoryGood repo = new UserRepositoryGood();

        // "Brak uzytkownika o danym e-mailu" to NORMALNY przypadek
        // biznesowy - Optional wymusza jego obsluzenie w miejscu uzycia.
        Optional<String> znaleziony = repo.findEmailById("nieznane-id");
        String opis = znaleziony
                .map(e -> "znaleziono: " + e)
                .orElse("uzytkownik nie istnieje - obsluzone jawnie, bez NPE");
        System.out.println(opis);

        Optional<String> istniejacy = repo.findEmailById("user-1");
        System.out.println(istniejacy.map(e -> "znaleziono: " + e).orElse("brak"));

        System.out.println("Szczegoly API Optional (map/filter/orElseGet/orElseThrow...) -");
        System.out.println("patrz _03_collections/Lesson14_Optional. Tutaj liczy sie");
        System.out.println("DECYZJA: 'brak wyniku' = normalny przypadek => Optional w return type.");
    }

    static class UserRepositoryGood {
        Optional<String> findEmailById(String id) {
            if ("user-1".equals(id)) {
                return Optional.of("jan@example.com");
            }
            return Optional.empty();
        }
    }

    // ============================================================
    // DEMO 4: Null Object - bezpieczne zachowanie zamiast null
    // ============================================================
    private static void demonstrateNullObjectPattern() {
        System.out.println("\n--- DEMO: wzorzec Null Object ---");

        Discount rabatVIP = new PercentageDiscount(20);
        Discount brakRabatu = NoDiscount.INSTANCE; // zamiast null!

        System.out.println("Cena z rabatem VIP: " + applyDiscount(100.0, rabatVIP));
        System.out.println("Cena bez rabatu (Null Object, BEZ sprawdzania null): "
                + applyDiscount(100.0, brakRabatu));
    }

    private static double applyDiscount(double cena, Discount rabat) {
        // Brak `if (rabat != null)` - kazdy Discount, wlacznie z "brakiem
        // rabatu", ma bezpieczne zachowanie. To jest cala korzysc wzorca.
        return rabat.applyTo(cena);
    }

    interface Discount {
        double applyTo(double price);
    }

    static class PercentageDiscount implements Discount {
        private final double percent;

        PercentageDiscount(double percent) {
            this.percent = percent;
        }

        @Override
        public double applyTo(double price) {
            return price - (price * percent / 100.0);
        }
    }

    /** Null Object: reprezentuje "brak rabatu" - zachowanie no-op zamiast null. */
    enum NoDiscount implements Discount {
        INSTANCE;

        @Override
        public double applyTo(double price) {
            return price;
        }
    }

    // ============================================================
    // DEMO 5: pusta kolekcja zamiast null
    // ============================================================
    private static void demonstrateEmptyCollectionInsteadOfNull() {
        System.out.println("\n--- DEMO: pusta kolekcja zamiast null ---");

        OrderHistoryBad zla = new OrderHistoryBad();
        try {
            for (String zamowienie : zla.findOrders("brak-klienta")) {
                System.out.println(zamowienie);
            }
        } catch (NullPointerException e) {
            System.out.println("BadOrderHistory.findOrders() zwrocilo null -> petla for-each wybucha NPE");
        }

        OrderHistoryGood dobra = new OrderHistoryGood();
        // Petla dziala IDENTYCZNIE dla pustego i niepustego wyniku - zero
        // specjalnych przypadkow, zero sprawdzania null.
        List<String> zamowienia = dobra.findOrders("brak-klienta");
        System.out.println("Liczba zamowien (pusta kolekcja, bez NPE): " + zamowienia.size());
        for (String zamowienie : zamowienia) {
            System.out.println(zamowienie);
        }
        System.out.println("Petla for-each zakonczyla sie normalnie, bez wyjatku.");
    }

    static class OrderHistoryBad {
        List<String> findOrders(String clientId) {
            return null; // zly pomysl - zobacz NPE w for-each powyzej
        }
    }

    static class OrderHistoryGood {
        List<String> findOrders(String clientId) {
            return Collections.emptyList(); // albo List.of()
        }
    }
}
