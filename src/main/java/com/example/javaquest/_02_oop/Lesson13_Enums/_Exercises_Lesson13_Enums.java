package com.example.javaquest._02_oop.Lesson13_Enums;

import java.util.EnumSet;

public class _Exercises_Lesson13_Enums {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicEnum {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj enum Day { MONDAY, ..., SUNDAY }.
         * Wypisz wszystkie dni przez values().
         * Wypisz ordinal() i name() dla WEDNESDAY.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_SwitchEnum {
        /*
         * 🧪 Zadanie 2:
         * Użyj enum Season { SPRING, SUMMER, AUTUMN, WINTER }.
         * Switch expression: wypisz polskie nazwy i typowe aktywności dla każdej pory.
         * Przetestuj dla SUMMER i WINTER.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_EnumValueOf {
        /*
         * 🧪 Zadanie 3:
         * Użyj enum Direction { NORTH, SOUTH, EAST, WEST }.
         * Konwertuj String "NORTH" na enum przez valueOf().
         * Przetestuj z prawidłową i nieprawidłową nazwą (catch IllegalArgumentException).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_EnumFields {
        /*
         * 🧪 Zadanie 4:
         * Stwórz enum Planet { MERCURY, VENUS, EARTH, MARS } z polami:
         * String polishName i double distanceFromSunAU.
         * Wypisz każdą planetę z polską nazwą i odległością od Słońca.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_EnumMethod {
        /*
         * 🧪 Zadanie 5:
         * Stwórz enum Coin { PENNY(1), NICKEL(5), DIME(10), QUARTER(25) } z polem int cents.
         * Dodaj metodę double toDollars().
         * Wypisz każdą monetę z wartością w centach i dolarach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_EnumComparison {
        /*
         * 🧪 Zadanie 6:
         * Enum Priority { LOW, MEDIUM, HIGH, CRITICAL }.
         * Sprawdź: Priority.HIGH.ordinal() > Priority.LOW.ordinal() → true.
         * Sprawdź: Priority.CRITICAL.compareTo(Priority.LOW) > 0 → true.
         * Znajdź enum o największym ordinal().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_EnumInArray {
        /*
         * 🧪 Zadanie 7:
         * Stwórz enum Color { RED, GREEN, BLUE, YELLOW, ORANGE }.
         * Utwórz tablicę Color[] z 3 losowymi kolorami (użyj Color.values()[i]).
         * Policz ile razy każdy kolor wystąpił.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_TrafficLight {
        /*
         * 🧪 Zadanie 8:
         * Enum TrafficLight { RED, YELLOW, GREEN } z metodą TrafficLight next().
         * RED → GREEN → YELLOW → RED.
         * Symuluj 6 przejść sygnalizacji i wypisz każdy stan.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_EnumAbstract {
        /*
         * 🧪 Zadanie 9:
         * Enum Operation { PLUS, MINUS, TIMES, DIVIDE }.
         * Każda stała ma abstract double apply(double a, double b).
         * Przetestuj wszystkie operacje dla (10, 3).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_EnumSingleton {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj Singleton przez enum (najlepsza praktyka):
         * enum AppConfig { INSTANCE; String dbUrl = "jdbc:..."; int maxConn = 10; }
         * Sprawdź że AppConfig.INSTANCE zawsze ten sam.
         * Dodaj metodę getConnectionString().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EnumSet {
        /*
         * 🧪 Zadanie 11:
         * Enum Permission { READ, WRITE, EXECUTE, DELETE, ADMIN }.
         * Stwórz: adminPermissions = EnumSet.allOf(Permission.class).
         * readOnlyPermissions = EnumSet.of(READ).
         * Sprawdź czy admin ma wszystkie uprawnienia.
         * Sprawdź complement (brakujące uprawnienia read-only użytkownika).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_EnumMap {
        /*
         * 🧪 Zadanie 12:
         * Enum Weekday { MON, TUE, WED, THU, FRI }.
         * Stwórz EnumMap<Weekday, String> z planem zajęć.
         * Wypisz plan dla każdego dnia.
         * Znajdź dzień z najdłuższą nazwą zajęć.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_StatePattern {
        /*
         * 🧪 Zadanie 13:
         * Enum OrderStatus { PLACED, PROCESSING, SHIPPED, DELIVERED, CANCELLED }.
         * Metoda boolean canTransitionTo(OrderStatus next) sprawdza dozwolone przejścia:
         * PLACED → PROCESSING lub CANCELLED
         * PROCESSING → SHIPPED lub CANCELLED
         * SHIPPED → DELIVERED
         * DELIVERED, CANCELLED → nic
         * Symuluj cykl życia zamówienia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_EnumParser {
        /*
         * 🧪 Zadanie 14:
         * Enum HttpMethod { GET, POST, PUT, DELETE, PATCH }.
         * Metoda statyczna fromString(String s) → case-insensitive valueOf.
         * Przetestuj: "get", "POST", "Delete", "UNKNOWN" (zwraca null lub opcjonalnie wyjątek).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_EnumCalculator {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj kalkulator oparty na enum:
         * enum Op { ADD, SUB, MUL, DIV, MOD, POW } z abstract double calc(double a, double b).
         * Klasa Calculator przyjmuje Op i przetwarza wyrażenia.
         * Oblicz: 2 POW 10, 100 MOD 7, 15 DIV 4.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_EnumFlags {
        /*
         * 🧪 Zadanie 16:
         * Użyj EnumSet jako flag systemu uprawnień:
         * enum Role { VIEWER, EDITOR, MODERATOR, ADMIN }.
         * Metody: boolean hasPermission(EnumSet<Role> userRoles, Role required).
         * Zasada: ADMIN ma wszystkie uprawnienia, MODERATOR ma VIEWER+EDITOR.
         * Przetestuj różne kombinacje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_PlanetGravity {
        /*
         * 🧪 Zadanie 17:
         * Rozbuduj enum Planet z lekcji o metodę double weightOnPlanet(double earthWeight):
         * Zwraca wagę obiektu (w kg) na danej planecie.
         * Dane: grawitacja na Ziemi = 9.81 m/s², każda planeta ma swój współczynnik.
         * Oblicz wagę 70kg człowieka na każdej planecie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_EnumChain {
        /*
         * 🧪 Zadanie 18:
         * Enum Month { JANUARY(31), FEBRUARY(28), ..., DECEMBER(31) } z polem int days.
         * Metody: Month next(), Month previous(), int totalDaysBefore().
         * totalDaysBefore() → suma dni od stycznia do poprzedniego miesiąca.
         * Wypisz datę "15 marca" jako dzień roku (15 + totalDaysBefore(MARCH)).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_EnumComparator {
        /*
         * 🧪 Zadanie 19:
         * Enum Severity { DEBUG(0), INFO(1), WARN(2), ERROR(3), FATAL(4) } z polem int level.
         * Stwórz tablicę zdarzeń logowania: {level, message}.
         * Przefiltruj zdarzenia z level >= WARN.
         * Posortuj malejąco po Severity.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_EnumInterface {
        /*
         * 🧪 Zadanie 20:
         * Interfejs Describable z metodą String describe().
         * Enum Animal { DOG, CAT, BIRD, FISH } implements Describable.
         * Każda stała implementuje describe() z własnym opisem.
         * Dodaj metodę String getSound() jako abstract w enum.
         * Wypisz wszystkie zwierzęta z opisem i dźwiękiem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_EnumBitmask {
        /*
         * 🧪 Zadanie 21:
         * Enum Permission z polami int bit (1, 2, 4, 8, 16...).
         * Symuluj bitowe flagi: combine(Permission...) → int mask.
         * hasPermission(int mask, Permission p) → (mask & p.bit) != 0.
         * Porównaj z EnumSet – kiedy który podejście jest lepsze?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_EnumStrategy {
        /*
         * 🧪 Zadanie 22:
         * Enum SortAlgorithm { BUBBLE, SELECTION, INSERTION, MERGE_SIMPLE } z abstract void sort(int[]).
         * Każda stała implementuje inny algorytm.
         * Metoda statyczna benchmark(int[] arr) mierzy czas każdego algorytmu.
         * Porównaj wyniki dla tablicy 100 elementów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_EnumFSM {
        /*
         * 🧪 Zadanie 23:
         * Finite State Machine przez enum:
         * enum VendingState { IDLE, HAS_MONEY, DISPENSING, OUT_OF_STOCK }.
         * Metody: insertCoin() → przejście, selectProduct() → przejście, dispense() → przejście.
         * Klasa VendingMachine przechowuje VendingState.
         * Symuluj pełny cykl zakupu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_EnumRegistry {
        /*
         * 🧪 Zadanie 24:
         * Enum Command { HELP, LIST, ADD, REMOVE, QUIT } z polem String description.
         * Statyczna metoda Command.parse(String input) – case-insensitive.
         * Klasa CLI z metodą void process(String userInput) używającą switch na Command.
         * Symuluj sesję CLI z kilkoma komendami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_EnumBuilder {
        /*
         * 🧪 Zadanie 25:
         * Enum HtmlTag { DIV, SPAN, P, H1, H2, BUTTON } z polem String tag.
         * Każda stała ma metodę String render(String content).
         * Zbuduj prostą stronę HTML przez łańcuch wywołań.
         * Wypisz wygenerowany HTML.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_EnumProduct {
        /*
         * 🧪 Zadanie 26:
         * Enum ProductCategory { ELECTRONICS, CLOTHING, FOOD, BOOKS, SPORTS }
         * z polami: double taxRate i double discountThreshold.
         * Metoda double calculateFinalPrice(double basePrice):
         * - jeśli basePrice > discountThreshold → 10% zniżki
         * - potem dodaj podatek (taxRate)
         * Oblicz ceny dla kilku produktów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_EnumSet2 {
        /*
         * 🧪 Zadanie 27:
         * Stwórz system uprawnień użytkownika:
         * enum Feature { DASHBOARD, REPORTS, ADMIN_PANEL, USER_MANAGEMENT, BILLING }.
         * Role: ADMIN = allOf, MANAGER = {DASHBOARD, REPORTS, USER_MANAGEMENT},
         *       USER = {DASHBOARD}, GUEST = EnumSet.noneOf.
         * Metoda List<Feature> getAvailableFeatures(EnumSet<Feature> userPerms).
         * Przetestuj dostęp dla każdej roli.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_EnumSerialization {
        /*
         * 🧪 Zadanie 28:
         * Enum Status { ACTIVE("A"), INACTIVE("I"), PENDING("P"), DELETED("D") } z polem code.
         * Statyczna metoda fromCode(String code) → szukaj po code, nie po name.
         * Symuluj serializację/deserializację: wypisz code, parsuj z code.
         * Wyjaśnij dlaczego używamy code zamiast name() przy serializacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_EnumVisitor {
        /*
         * 🧪 Zadanie 29:
         * Interfejs ShapeVisitor z: visitCircle(r), visitRect(w,h), visitTriangle(b,h).
         * Enum Shape { CIRCLE, RECTANGLE, TRIANGLE } z metodą abstract double accept(ShapeVisitor v, double... params).
         * Implementacje Visitora: AreaVisitor, PerimeterVisitor.
         * Odwiedź kształty przez oba wizytory.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_EnumDSL {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj DSL do konfiguracji bazy danych przez enuma i builder:
         * enum DbType { MYSQL, POSTGRESQL, SQLITE, H2 } z polem int defaultPort.
         * Klasa DbConfig.Builder przyjmuje DbType i ustawia host, port, credentials.
         * Metoda Builder.validate() sprawdza czy konfiguracja jest spójna.
         * Przetestuj konfigurację każdego typu bazy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
