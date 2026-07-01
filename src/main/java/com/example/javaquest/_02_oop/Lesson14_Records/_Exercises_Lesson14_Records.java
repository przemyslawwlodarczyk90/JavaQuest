package com.example.javaquest._02_oop.Lesson14_Records;

public class _Exercises_Lesson14_Records {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicRecord {
        /*
         * 🧪 Zadanie 1:
         * Stwórz record Point(int x, int y).
         * Utwórz 3 punkty i wypisz je (auto toString).
         * Sprawdź auto-wygenerowane gettery: punkt.x() i punkt.y().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_RecordEquals {
        /*
         * 🧪 Zadanie 2:
         * Record Color(int r, int g, int b).
         * Utwórz dwa identyczne kolory Color(255, 0, 0).
         * Sprawdź: equals() (powinno być true), hashCode() (powinny być równe), ==.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_RecordGetters {
        /*
         * 🧪 Zadanie 3:
         * Record Person(String firstName, String lastName, int age).
         * Wypisz dane przez: person.firstName(), person.lastName(), person.age().
         * Sprawdź że nie ma tradycyjnych "getXxx()" metod.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_CompactConstructor {
        /*
         * 🧪 Zadanie 4:
         * Record Email(String value) z compact constructorem:
         * Email { value = value.trim().toLowerCase(); }
         * Przetestuj: new Email("  HELLO@EXAMPLE.COM  ") → "hello@example.com".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_RecordValidation {
        /*
         * 🧪 Zadanie 5:
         * Record Age(int value) z compact constructorem walidującym: 0 <= value <= 150.
         * Rzuć IllegalArgumentException dla niepoprawnych wartości.
         * Przetestuj: 25 (OK), -1 (błąd), 200 (błąd).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_RecordMethod {
        /*
         * 🧪 Zadanie 6:
         * Record Circle(double radius) z metodami:
         * double area() i double perimeter().
         * Przetestuj dla promienia 5.0.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_RecordImmutability {
        /*
         * 🧪 Zadanie 7:
         * Record Temperature(double celsius).
         * Pokaż że record jest immutable: nie można zmienić celsius po stworzeniu.
         * Dodaj metodę Temperature toColder(double degrees) zwracającą NOWY record.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_NestedRecord {
        /*
         * 🧪 Zadanie 8:
         * Records: Address(String street, String city, String country) i
         * Person(String name, Address address).
         * Utwórz osobę z adresem i wypisz pełne dane.
         * Sprawdź person.address().city().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_RecordArray {
        /*
         * 🧪 Zadanie 9:
         * Record Product(String name, double price, int stock).
         * Stwórz tablicę 5 produktów.
         * Znajdź: najdroższy, najtańszy, łączną wartość magazynu (price * stock).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_RecordStaticMethod {
        /*
         * 🧪 Zadanie 10:
         * Record Point(double x, double y) ze statyczną metodą:
         * static Point origin() → new Point(0, 0)
         * static double distance(Point a, Point b) → odległość euklidesowa.
         * Instancyjna metoda: double distanceTo(Point other).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RecordInterface {
        /*
         * 🧪 Zadanie 11:
         * Interfejs Shape z metodami: double area(), String shapeName().
         * Records: CircleShape(double radius) i RectShape(double w, double h) implements Shape.
         * Wypisz obszary przez interfejs (polimorfizm z recordami).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_RecordWithList {
        /*
         * 🧪 Zadanie 12:
         * Record Classroom(String name, String[] students).
         * Compact constructor robi defensive copy tablicy students.
         * Metody: int size(), boolean hasStudent(String name), String roster().
         * Udowodnij że zewnętrzna modyfikacja tablicy nie wpływa na record.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_RecordDTO {
        /*
         * 🧪 Zadanie 13:
         * Symuluj DTO pattern:
         * Record UserRequest(String username, String email, String password).
         * Record UserResponse(int id, String username, String email, String createdAt).
         * Metoda static UserResponse register(UserRequest request) – tworzy odpowiedź.
         * Wypisz żądanie i odpowiedź.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_RecordComparable {
        /*
         * 🧪 Zadanie 14:
         * Record Version(int major, int minor, int patch) implements Comparable<Version>.
         * compareTo: najpierw major, potem minor, potem patch.
         * Utwórz tablicę wersji i posortuj przez prosty algorytm.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_RecordMoney {
        /*
         * 🧪 Zadanie 15:
         * Record Money(long cents, String currency) z metodami:
         * - Money add(Money other) – sprawdź zgodność waluty
         * - Money subtract(Money other) – sprawdź zgodność waluty
         * - Money multiply(int factor)
         * - String format() → "12.50 PLN"
         * Przetestuj operacje na kwotach PLN i EUR.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_RecordRange {
        /*
         * 🧪 Zadanie 16:
         * Record Range(int from, int to) z compact constructorem: from <= to.
         * Metody: int length(), boolean contains(int value), boolean overlaps(Range other).
         * Record Intersection(Range r1, Range r2) → oblicza część wspólną lub null.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_RecordJson {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj records do reprezentacji JSON:
         * JsonString(String value), JsonNumber(double value), JsonBoolean(boolean value).
         * Interfejs JsonValue z metodą String toJson().
         * Każdy record implements JsonValue.
         * Wypisz każdy typ jako JSON.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_RecordResult {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj Result<T> pattern przez dwa recordy:
         * record Success<T>(T value)
         * record Failure(String errorMessage, String errorCode)
         * Interfejs Result z: boolean isSuccess(), T getValueOrNull(), String getError().
         * Metoda static Result<Integer> divide(int a, int b) → Failure jeśli b==0.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_RecordPattern {
        /*
         * 🧪 Zadanie 19:
         * Records: Circle(double r), Rect(double w, double h), Triangle(double b, double h).
         * Metoda static String describe(Object shape) używa switch + pattern matching:
         * switch (shape) {
         *   case Circle c -> "Koło r=" + c.r()
         *   case Rect r -> "Prostokąt " + r.w() + "x" + r.h()
         *   case Triangle t -> "Trójkąt"
         *   default -> "Nieznany"
         * }
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_RecordHierarchy {
        /*
         * 🧪 Zadanie 20:
         * Records mogą implements interface – symuluj hierarchię:
         * Interfejs Event z: String type(), long timestamp().
         * Records: LoginEvent(String userId, long timestamp),
         *          PurchaseEvent(String userId, double amount, long timestamp),
         *          LogoutEvent(String userId, long timestamp).
         * Przeiteruj Event[] i wypisz polimorficznie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImmutableLinkedList {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj immutable linked list przez record:
         * record Node<T>(T head, Node<T> tail)
         * Metody statyczne: empty() → null, prepend(T, Node<T>) → new Node<>.
         * Metody: int size(), boolean contains(T), String toList().
         * Przetestuj z listą intów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_RecordTree {
        /*
         * 🧪 Zadanie 22:
         * Immutable binary tree przez records:
         * record Tree(int value, Tree left, Tree right)
         * Metoda statyczna: Tree leaf(int v) → new Tree(v, null, null).
         * Metody: int size(), int sum(), int height(), boolean contains(int).
         * Zbuduj i przetestuj drzewo BST.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_RecordSealedSim {
        /*
         * 🧪 Zadanie 23:
         * Symulacja sealed interface przez records:
         * Interfejs Shape (sealed-like przez private konstruktor w interface).
         * Records: Circle(double r), Square(double s), Triangle(double b, double h).
         * Metoda static double totalArea(Object[] shapes) – switch z pattern matching.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_RecordGraph {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj immutable graf przez records:
         * record Edge(String from, String to, int weight)
         * record Graph(String[] vertices, Edge[] edges)
         * Metody: Edge[] edgesFrom(String vertex), int degree(String vertex).
         * Graph addEdge(Edge e) → nowy Graf z dodaną krawędzią (immutable).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_RecordValueObject {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj zestaw Value Objects w domenie e-commerce:
         * record SKU(String code) – walidacja formatu "XXXX-9999"
         * record Price(long cents, String currency) – z operacjami arytmetycznymi
         * record Quantity(int value) – value >= 0
         * record OrderLine(SKU sku, Price unitPrice, Quantity qty) – total()
         * Przetestuj tworzenie zamówienia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_RecordParser {
        /*
         * 🧪 Zadanie 26:
         * Record CSV reprezentujący wiersz CSV: record CsvRow(String[] fields).
         * Compact constructor trimmuje wszystkie pola.
         * Metoda get(int index), int count(), String toJson().
         * Metoda statyczna CsvRow parse(String line) → split po ",".
         * Sparsuj 5 wierszy i wypisz jako JSON.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_RecordStateMachine {
        /*
         * 🧪 Zadanie 27:
         * Immutable state machine przez records:
         * record State(String name, String[] allowedTransitions)
         * record Transition(State from, String event, State to)
         * record Machine(State current, Transition[] transitions)
         * Metoda Machine transition(String event) → nowa Machine ze zmienionym stanem.
         * Symuluj maszynę do zamawiania kawy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_RecordConfig {
        /*
         * 🧪 Zadanie 28:
         * Hierarchia recordów konfiguracyjnych:
         * record LogConfig(String level, String format, boolean colorized)
         * record DbConfig(String host, int port, String schema, int poolSize)
         * record AppConfig(String name, String version, LogConfig logging, DbConfig database)
         * Metoda AppConfig withLogLevel(String level) → nowy AppConfig z nowym LogConfig.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_RecordEvent {
        /*
         * 🧪 Zadanie 29:
         * Event sourcing przez records:
         * record Event(long id, String type, String payload, long timestamp)
         * record EventLog(Event[] events)
         * EventLog append(Event) → nowy EventLog z dodanym zdarzeniem.
         * EventLog filter(String type) → filtruje po typie.
         * Symuluj aktualizacje konta bankowego przez zdarzenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_RecordSystem {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj system zarządzania biblioteką z records:
         * record Book(String isbn, String title, String author, int year)
         * record Borrower(String id, String name)
         * record Loan(Book book, Borrower borrower, long borrowedAt, long dueAt)
         * record Library(Book[] catalog, Loan[] loans)
         * Metody: Library addBook, Library borrow, Library returnBook, Library overdueLoans.
         * Wszystko immutable – każda operacja zwraca nowy Library.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
