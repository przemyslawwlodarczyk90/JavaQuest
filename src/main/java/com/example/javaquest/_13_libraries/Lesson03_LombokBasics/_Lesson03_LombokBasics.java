package com.example.javaquest._13_libraries.Lesson03_LombokBasics;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class _Lesson03_LombokBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: LOMBOK - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 CZYM JEST LOMBOK
         * ============================================================
         * - Lombok to biblioteka, ktora generuje "boilerplate" (powtarzalny,
         *   szablonowy kod: gettery, settery, toString, equals/hashCode,
         *   konstruktory) automatycznie, na podstawie ADNOTACJI umieszczonych
         *   na klasie lub polach.
         * - KLUCZOWE: dzieje sie to w czasie KOMPILACJI, przez tzw. annotation
         *   processing (APT - Annotation Processing Tool) - Lombok podpina
         *   sie pod proces kompilacji (javac) i modyfikuje drzewo skladniowe
         *   (AST) klasy, DOKLADAJAC do niej metody/konstruktory, ZANIM
         *   powstanie plik .class.
         * - To NIE jest refleksja w czasie dzialania (runtime) - w
         *   przeciwienstwie do np. adnotacji Springa (@Autowired) czy
         *   Jacksona, ktore sa odczytywane przez refleksje PO uruchomieniu
         *   programu. Adnotacje Lomboka maja RetentionPolicy.SOURCE - po
         *   kompilacji w ogole ich nie widac w bytecode, zostaje juz tylko
         *   WYGENEROWANY kod (patrz Lekcja 5 - jak to dziala pod spodem).
         * - Problem, ktory rozwiazuje: klasa z 10 polami "danych" (typowe
         *   DTO/model) to w czystej Javie DZIESIATKI linii identycznego,
         *   mechanicznego kodu - gettery, settery, toString, equals,
         *   hashCode, konstruktory. Ten kod trzeba pisac, czytac,
         *   utrzymywac (i latwo o nim ZAPOMNIEC przy dodaniu nowego pola).
         */
        System.out.println("\n--- PROBLEM: boilerplate w czystej Javie ---");
        ProductManual manualProduct = new ProductManual("Klawiatura", 199.99, 5);
        System.out.println("ProductManual: " + manualProduct);
        System.out.println("Liczba linii recznego kodu dla 3 pol: gettery+settery+toString+equals+hashCode = kilkadziesiat linii (patrz kod klasy ProductManual ponizej w pliku).");

        System.out.println("\n--- ROZWIAZANIE: ta sama klasa z @Data (Lombok) ---");
        ProductLombok lombokProduct = new ProductLombok("Klawiatura", 199.99);
        lombokProduct.setQuantity(5);
        System.out.println("ProductLombok: " + lombokProduct);
        System.out.println("Ta sama funkcjonalnosc - JEDNA adnotacja @Data zamiast dziesiatek linii kodu.");

        /*
         * ============================================================
         * 🔹 @Getter i @Setter - NA POLU vs NA KLASIE
         * ============================================================
         * - Umieszczone NA POLU: generuja getter/setter TYLKO dla tego
         *   jednego pola.
         * - Umieszczone NA KLASIE: generuja getter/setter dla WSZYSTKICH
         *   pol niestatycznych (chyba ze pole jest "final" - wtedy setter
         *   dla niego jest pomijany, bo nie da sie go wygenerowac).
         * - Mozna je mieszac: adnotacja na polu NADPISUJE/uzupelnia to,
         *   co wygenerowalaby adnotacja na klasie dla tego konkretnego pola.
         */
        System.out.println("\n--- @Getter/@Setter: pole 'id' bez settera (final), reszta z getterem i setterem ---");
        Employee employee = new Employee("E-100", "Anna Nowak", 8500.0);
        System.out.println("Przed zmiana: " + employee.getName() + ", pensja=" + employee.getSalary());
        employee.setName("Anna Kowalska");
        employee.setSalary(9200.0);
        System.out.println("Po zmianie: " + employee.getName() + ", pensja=" + employee.getSalary());
        System.out.println("Pole 'id' NIE ma settera (bylo final + @Getter na polu) - id=" + employee.getId());

        /*
         * ============================================================
         * 🔹 @ToString - exclude / of
         * ============================================================
         * - Domyslnie @ToString wypisuje WSZYSTKIE pola klasy.
         * - exclude = {"pole1", "pole2"} - wyklucza wybrane pola (np. hasla,
         *   dane wrazliwe, pola cykliczne odwolujace sie do samej klasy).
         * - of = {"pole1", "pole2"} - odwrotnie: wypisuje TYLKO wskazane
         *   pola (biala lista zamiast czarnej).
         */
        System.out.println("\n--- @ToString(exclude = \"password\") ---");
        UserAccount account = new UserAccount("jkowalski", "tajneHaslo123", "jan@example.com");
        System.out.println("UserAccount: " + account + " (haslo NIE jest widoczne w toString)");

        System.out.println("\n--- @ToString(of = {\"username\", \"email\"}) ---");
        UserAccountSummary summary = new UserAccountSummary("jkowalski", "tajneHaslo123", "jan@example.com", "internal-id-9981");
        System.out.println("UserAccountSummary: " + summary + " (tylko username i email - reszta pol ukryta)");

        /*
         * ============================================================
         * 🔍 @EqualsAndHashCode - exclude, oraz PULAPKA z dziedziczeniem
         * ============================================================
         * - Domyslnie @EqualsAndHashCode uzywa WSZYSTKICH pol klasy do
         *   wyliczenia equals/hashCode. exclude = {"pole"} pozwala pominac
         *   wybrane pola (np. pole techniczne "version" niezwiazane z
         *   tozsamoscia biznesowa obiektu).
         * - PULAPKA przy dziedziczeniu: domyslnie @EqualsAndHashCode NIE
         *   wywoluje super.equals()/super.hashCode() - porownuje TYLKO pola
         *   WLASNE danej klasy, IGNORUJAC pola odziedziczone po klasie
         *   nadrzednej! Lombok wypisuje wtedy OSTRZEZENIE kompilacji
         *   ("Generating equals/hashCode implementation but without a call
         *   to superclass"), ale kod i tak sie skompiluje - blad jest czysto
         *   LOGICZNY i latwo go przeoczyc.
         * - Naprawa: jawne @EqualsAndHashCode(callSuper = true) - wtedy
         *   wygenerowany equals/hashCode UWZGLEDNIA rowniez pola z klasy
         *   nadrzednej (wywoluje super.equals()/super.hashCode()).
         */
        System.out.println("\n--- PULAPKA: @EqualsAndHashCode bez callSuper=true ignoruje pola z nadklasy ---");
        PersonBuggy person1 = new PersonBuggy("Jan Kowalski");
        person1.setDepartment("IT");
        PersonBuggy person2 = new PersonBuggy("Piotr Nowak");
        person2.setDepartment("IT");
        System.out.println("person1=" + person1 + ", person2=" + person2);
        System.out.println("person1.equals(person2) = " + person1.equals(person2)
                + " (BLAD LOGICZNY: rozne imiona z nadklasy Person, ale uznane za rowne, bo 'name' zostalo zignorowane!)");

        System.out.println("\n--- NAPRAWA: @EqualsAndHashCode(callSuper = true) ---");
        PersonFixed fixed1 = new PersonFixed("Jan Kowalski");
        fixed1.setDepartment("IT");
        PersonFixed fixed2 = new PersonFixed("Piotr Nowak");
        fixed2.setDepartment("IT");
        System.out.println("fixed1.equals(fixed2) = " + fixed1.equals(fixed2) + " (poprawnie: rozne imiona -> nierowne)");

        /*
         * ============================================================
         * 📦 @Data - KOMBINACJA WSZYSTKIEGO POWYZEJ
         * ============================================================
         * - @Data to "skrot" laczacy w jednej adnotacji:
         *   @Getter + @Setter (dla wszystkich pol) + @ToString +
         *   @EqualsAndHashCode + @RequiredArgsConstructor (konstruktor
         *   dla pol "final" lub oznaczonych @NonNull - patrz Lekcja 4).
         * - Najczestszy wybor dla klas typu DTO/model, ktore sa MUTOWALNE
         *   (maja settery). Dla klas NIEMUTOWALNYCH lepszym wyborem jest
         *   @Value (Lekcja 4).
         */
        System.out.println("\n--- @Data w akcji (Product z lekcji) ---");
        ProductLombok p1 = new ProductLombok("Mysz", 79.99);
        p1.setQuantity(10);
        ProductLombok p2 = new ProductLombok("Mysz", 79.99);
        p2.setQuantity(10);
        System.out.println("p1=" + p1);
        System.out.println("p1.equals(p2) = " + p1.equals(p2) + " (te same wartosci pol -> rowne, dzieki @Data)");
        p1.setQuantity(3);
        System.out.println("Po p1.setQuantity(3): " + p1);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Lombok wymaga WTYCZKI w IDE (np. "Lombok" w IntelliJ/Eclipse),
         *   zeby edytor "widzial" wygenerowane metody (podpowiedzi, brak
         *   czerwonych podkreslen przy getX()/setX() w kodzie). BEZ
         *   wtyczki kod i tak sie SKOMPILUJE poprawnie przez Mavena/Gradle
         *   (bo to czysty annotation processor dolaczony jako zaleznosc
         *   "provided") - wtyczka poprawia tylko komfort pracy w edytorze,
         *   nie jest wymagana do dzialania programu.
         * - @Getter/@Setter - kontrola na poziomie pola LUB calej klasy.
         * - @ToString(exclude=.../of=...) - kontrola co trafia do napisu.
         * - @EqualsAndHashCode - UWAZAJ na dziedziczenie: callSuper=true,
         *   gdy klasa dziedziczy pola, ktore MAJA wplywac na tozsamosc.
         * - @Data - wygodny "wszystko w jednym" dla mutowalnych klas.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    // ------------------------------------------------------------------
    // Klasa napisana RECZNIE (bez Lomboka) - ilustracja problemu boilerplate.
    // ------------------------------------------------------------------
    static class ProductManual {
        private String name;
        private double price;
        private int quantity;

        public ProductManual(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "ProductManual{name='" + name + "', price=" + price + ", quantity=" + quantity + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProductManual)) return false;
            ProductManual that = (ProductManual) o;
            return Double.compare(price, that.price) == 0
                    && quantity == that.quantity
                    && java.util.Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name, price, quantity);
        }
    }

    // ------------------------------------------------------------------
    // Ta sama klasa co ProductManual, ale przez @Data (Lombok).
    // ------------------------------------------------------------------
    @Data
    static class ProductLombok {
        private final String name;
        private final double price;
        private int quantity;
    }

    // ------------------------------------------------------------------
    // @Getter NA KLASIE (wszystkie pola) + @Setter NA POLU (tylko mutowalne).
    // ------------------------------------------------------------------
    @Getter
    static class Employee {
        private final String id;
        @Setter
        private String name;
        @Setter
        private double salary;

        public Employee(String id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }
    }

    // ------------------------------------------------------------------
    // @ToString(exclude = ...) - haslo NIE trafia do napisu.
    // ------------------------------------------------------------------
    @Getter
    @ToString(exclude = "password")
    static class UserAccount {
        private final String username;
        private final String password;
        private final String email;

        UserAccount(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
    }

    // ------------------------------------------------------------------
    // @ToString(of = {...}) - TYLKO wskazane pola trafiaja do napisu.
    // ------------------------------------------------------------------
    @Getter
    @ToString(of = {"username", "email"})
    static class UserAccountSummary {
        private final String username;
        private final String password;
        private final String email;
        private final String internalId;

        UserAccountSummary(String username, String password, String email, String internalId) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.internalId = internalId;
        }
    }

    // ------------------------------------------------------------------
    // PULAPKA: @EqualsAndHashCode BEZ callSuper - ignoruje pola z Person.
    // ------------------------------------------------------------------
    @Getter
    @Setter
    static class PersonBase {
        private String name;

        PersonBase(String name) {
            this.name = name;
        }
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(callSuper = false)
    static class PersonBuggy extends PersonBase {
        private String department;

        PersonBuggy(String name) {
            super(name);
        }
    }

    // ------------------------------------------------------------------
    // NAPRAWA: @EqualsAndHashCode(callSuper = true).
    // ------------------------------------------------------------------
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    static class PersonFixed extends PersonBase {
        private String department;

        PersonFixed(String name) {
            super(name);
        }
    }
}
