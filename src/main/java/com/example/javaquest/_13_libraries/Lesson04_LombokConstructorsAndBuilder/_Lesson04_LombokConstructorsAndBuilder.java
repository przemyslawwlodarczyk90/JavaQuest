package com.example.javaquest._13_libraries.Lesson04_LombokConstructorsAndBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

public class _Lesson04_LombokConstructorsAndBuilder {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: LOMBOK - KONSTRUKTORY I BUILDER ===");

        /*
         * ============================================================
         * 📦 @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor
         * ============================================================
         * - @NoArgsConstructor - generuje konstruktor BEZ argumentow (przydaje
         *   sie np. dla frameworkow wymagajacych "pustego" konstruktora, jak
         *   Hibernate/Jackson - patrz _12_hibernate). Jesli klasa ma pola
         *   "final" bez wartosci domyslnej, wygenerowanie takiego
         *   konstruktora bez wartosci dla nich jest BLEDEM KOMPILACJI - trzeba
         *   wtedy uzyc @NoArgsConstructor(force = true), co ustawia takie pola
         *   na wartosc domyslna (0 / false / null).
         * - @AllArgsConstructor - generuje konstruktor z JEDNYM parametrem dla
         *   KAZDEGO pola (w kolejnosci deklaracji).
         * - @RequiredArgsConstructor - generuje konstruktor TYLKO dla pol
         *   "wymaganych": final BEZ wartosci domyslnej, oraz oznaczonych
         *   @NonNull, ktore rowniez nie maja wartosci domyslnej. To WLASNIE
         *   ten konstruktor jest domyslnie dokladany przez adnotacje @Data.
         */
        System.out.println("\n--- @NoArgsConstructor + @AllArgsConstructor (Point) ---");
        Point defaultPoint = new Point();
        Point exactPoint = new Point(5, 10);
        System.out.println("Point domyslny: " + defaultPoint);
        System.out.println("Point z (5, 10): " + exactPoint);

        System.out.println("\n--- @RequiredArgsConstructor (User: wymagane tylko 'id') ---");
        User user = new User("U-42");
        user.setEmail("user42@example.com");
        System.out.println("User: id=" + user.getId() + ", email=" + user.getEmail());

        /*
         * ============================================================
         * 🔹 @NonNull
         * ============================================================
         * - Umieszczone na polu (lub parametrze metody) powoduje, ze Lombok
         *   dokleja NA POCZATKU wygenerowanego konstruktora/settera sprawdzenie
         *   "if (param == null) throw new NullPointerException(...)".
         * - To NIE jest adnotacja walidacyjna typu Bean Validation (np.
         *   @NotNull z jakarta.validation) - ta druga wymaga OSOBNEGO
         *   "walidatora" wywolywanego recznie lub przez framework (patrz
         *   _12_hibernate/Lesson28). @NonNull Lomboka generuje PROSTY,
         *   NATYCHMIASTOWY check w bytecode, bez zadnej dodatkowej
         *   infrastruktury.
         */
        System.out.println("\n--- @NonNull: null-check generowany przez Lomboka ---");
        try {
            new Account(null, 100.0);
        } catch (NullPointerException e) {
            System.out.println("Zlapano NullPointerException przy proba utworzenia Account z owner=null: " + e.getMessage());
        }
        Account validAccount = new Account("Jan Kowalski", 100.0);
        System.out.println("Poprawnie utworzone konto: " + validAccount);

        /*
         * ============================================================
         * 🔹 @Builder
         * ============================================================
         * - Generuje wzorzec Buildera BEZ pisania go recznie: klase
         *   wewnetrzna XxxBuilder ze statyczna metoda builder(), metodami
         *   "fluent" (zwracajacymi "this") dla kazdego pola, oraz metoda
         *   build() tworzaca finalny obiekt.
         * - Szczegolnie wygodne dla klas z WIELOMA polami, gdzie wywolanie
         *   konstruktora z 6-7 parametrami pozycyjnymi byloby nieczytelne
         *   (latwo pomylic kolejnosc argumentow tego samego typu) - Builder
         *   nazywa kazda wartosc po imieniu pola.
         */
        System.out.println("\n--- @Builder (Product) ---");
        Product product = Product.builder()
                .name("Monitor 27\"")
                .price(1299.99)
                .category("Elektronika")
                .build();
        System.out.println("Product zbudowany przez Buildera: " + product);

        /*
         * ============================================================
         * 🔍 @Builder.Default
         * ============================================================
         * - BEZ tej adnotacji, Builder IGNORUJE inicjalizatory pol przy
         *   deklaracji (np. "private int quantity = 1;") - pole, ktorego nie
         *   ustawimy przez buildera, dostanie WARTOSC DOMYSLNA JAVY (0, null,
         *   false), a NIE wartosc z inicjalizatora! To CZESTA PULAPKA.
         * - @Builder.Default NAKAZUJE Lombokowi UZYC wartosci z
         *   inicjalizatora jako domyslnej, jesli pole nie zostanie jawnie
         *   ustawione przez metode buildera.
         */
        System.out.println("\n--- @Builder.Default: quantity domyslnie = 1, jesli nie ustawiona ---");
        Product productWithDefault = Product.builder()
                .name("Klawiatura mechaniczna")
                .price(349.0)
                .category("Elektronika")
                .build();
        System.out.println("Bez jawnego .quantity(...): " + productWithDefault
                + " (quantity=" + productWithDefault.getQuantity() + " - wartosc z @Builder.Default)");

        Product productCustomQuantity = Product.builder()
                .name("Klawiatura mechaniczna")
                .price(349.0)
                .category("Elektronika")
                .quantity(5)
                .build();
        System.out.println("Z jawnym .quantity(5): " + productCustomQuantity);

        /*
         * ============================================================
         * 📦 @Value - odpowiednik @Data DLA KLAS NIEMUTOWALNYCH
         * ============================================================
         * - @Value to "przeciwienstwo" @Data: generuje @Getter dla
         *   wszystkich pol (ale NIGDY @Setter), automatycznie oznacza
         *   WSZYSTKIE pola jako "private final", samą klase jako "final"
         *   (nie mozna jej dziedziczyc), oraz dokada @ToString,
         *   @EqualsAndHashCode i @AllArgsConstructor (bo skoro wszystkie
         *   pola sa final, potrzebny jest konstruktor ustawiajacy WSZYSTKIE
         *   od razu).
         * - Efekt: obiekt raz utworzony NIE MOZE zostac zmodyfikowany -
         *   klasyczny "value object"/DTO niemutowalny, bezpieczny do
         *   dzielenia miedzy watkami bez synchronizacji (patrz
         *   _05_multithreading).
         */
        System.out.println("\n--- @Value (ImmutablePoint - w pelni niemutowalny) ---");
        ImmutablePoint immutablePoint = new ImmutablePoint(3, 4);
        System.out.println("ImmutablePoint: " + immutablePoint
                + " (BRAK setterow - kod wywolujacy setX() w ogole by sie NIE SKOMPILOWAL)");

        /*
         * ============================================================
         * 🔍 KIEDY @Data, A KIEDY @Value?
         * ============================================================
         * - @Data: obiekt bedzie MODYFIKOWANY po utworzeniu (typowe encje,
         *   formularze, obiekty budowane krok po kroku przez settery).
         * - @Value: obiekt reprezentuje STALA wartosc/fakt (np. wspolrzedne,
         *   pieniadze, zakres dat, DTO tylko do odczytu) - RAZ utworzony,
         *   NIGDY sie nie zmienia. Bezpieczniejszy wybor domyslny, gdy
         *   mutowalnosc NIE jest faktycznie potrzebna (mniej bledow
         *   zwiazanych ze wspoldzielonym, zmiennym stanem).
         */
        printDataVsValueTable();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - @NoArgsConstructor/@AllArgsConstructor/@RequiredArgsConstructor -
         *   trzy warianty generowanych konstruktorow, mozna je laczyc.
         * - @NonNull - prosty, natychmiastowy null-check w bytecode.
         * - @Builder - wzorzec budowniczego bez recznego kodu, czytelne
         *   tworzenie obiektow z wieloma polami.
         * - @Builder.Default - PAMIETAJ o niej, gdy pole ma wartosc
         *   domyslna przy deklaracji, inaczej Builder ja zignoruje.
         * - @Value - "@Data dla niemutowalnosci": wszystkie pola final,
         *   klasa final, brak setterow.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static void printDataVsValueTable() {
        System.out.println("\n=== @Data vs @Value ===");
        String format = "%-25s | %-20s | %-20s%n";
        System.out.printf(format, "Cecha", "@Data", "@Value");
        System.out.println("-".repeat(70));
        System.out.printf(format, "Pola", "mutowalne", "final (niemutowalne)");
        System.out.printf(format, "Settery", "generowane", "BRAK");
        System.out.printf(format, "Klasa", "moze byc rozszerzana", "final (bez dziedziczenia)");
        System.out.printf(format, "Konstruktor domyslny", "@RequiredArgsConstructor", "@AllArgsConstructor");
        System.out.printf(format, "Typowe zastosowanie", "encje, formularze", "value objects, DTO");
    }

    // ------------------------------------------------------------------
    // @NoArgsConstructor + @AllArgsConstructor - brak pol final, wiec oba
    // konstruktory moga bezpiecznie wspolistniec.
    // ------------------------------------------------------------------
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class Point {
        private int x;
        private int y;
    }

    // ------------------------------------------------------------------
    // @RequiredArgsConstructor - konstruktor TYLKO dla pola final "id".
    // ------------------------------------------------------------------
    @Getter
    @Setter
    @RequiredArgsConstructor
    static class User {
        private final String id;
        private String email;
    }

    // ------------------------------------------------------------------
    // @NonNull na polu nie-final - @RequiredArgsConstructor traktuje je
    // jako "wymagane" i generuje null-check. @AllArgsConstructor dokladany
    // OBOK niego (owner, balance) - Lombok wstawia null-check na @NonNull
    // do KAZDEGO generowanego konstruktora, w ktorym pole to wystepuje.
    // ------------------------------------------------------------------
    @Getter
    @ToString
    @RequiredArgsConstructor
    @AllArgsConstructor
    static class Account {
        @NonNull
        private String owner;
        private double balance;
    }

    // ------------------------------------------------------------------
    // @Builder + @Builder.Default (quantity domyslnie = 1).
    // ------------------------------------------------------------------
    @Getter
    @ToString
    @Builder
    static class Product {
        private String name;
        private double price;
        private String category;
        @Builder.Default
        private int quantity = 1;
    }

    // ------------------------------------------------------------------
    // @Value - w pelni niemutowalna klasa (pola final, klasa final, brak
    // setterow, konstruktor all-args generowany automatycznie).
    // ------------------------------------------------------------------
    @Value
    static class ImmutablePoint {
        int x;
        int y;
    }
}
