package com.example.javaquest._02_oop.Lesson10_FinalKeyword;

public class _Exercises_Lesson10_FinalKeyword {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FinalVariable {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj final int MAX = 100 i final String GREETING = "Witaj".
         * Wypisz obie wartości. Spróbuj zmienić (w komentarzu) – wyjaśnij błąd.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_FinalStaticConstant {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę AppConstants z stałymi public static final:
         * VERSION (String), MAX_USERS (int), TAX_RATE (double).
         * Użyj UPPER_SNAKE_CASE. Wypisz każdą stałą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_FinalField {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę Invoice z final int id i final double amount.
         * Oba pola ustawiane TYLKO w konstruktorze.
         * Spróbuj zmienić po inicjalizacji (w komentarzu).
         * Utwórz 3 faktury i wypisz dane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_FinalMethod {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę BaseAuth z final boolean authenticate(String pwd).
         * Podklasa StrongAuth dziedziczy (nie może nadpisać authenticate).
         * StrongAuth może dodać inne metody.
         * W komentarzu pokaż próbę nadpisania i jej błąd.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_FinalClass {
        /*
         * 🧪 Zadanie 5:
         * Stwórz final class Money z polami long cents i String currency.
         * Nie można jej rozszerzyć (w komentarzu pokaż próbę).
         * Metody: add(Money other) – zwraca nową Money, toString().
         * Przetestuj.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_EffectivelyFinal {
        /*
         * 🧪 Zadanie 6:
         * Stwórz zmienną String prefix = ">> " (nie final, ale effectively final).
         * Użyj jej w lambdzie: Runnable r = () -> System.out.println(prefix + "start");
         * Uruchom lambdę. Następnie spróbuj zmienić prefix po lambdzie (w komentarzu).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_BlankFinal {
        /*
         * 🧪 Zadanie 7:
         * Stwórz klasę Config z blank final polami:
         * final String host i final int port.
         * Dwa konstruktory: Config(String host) → port=80
         * i Config(String host, int port).
         * Przetestuj oba konstruktory.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_FinalParam {
        /*
         * 🧪 Zadanie 8:
         * Stwórz metodę void process(final String input, final int count).
         * Wewnątrz nie można zmienić input ani count (sprawdź w komentarzu).
         * Metoda wypisuje input powtórzony count razy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_JdkFinal {
        /*
         * 🧪 Zadanie 9:
         * Sprawdź klasy final w JDK:
         * - String (pokaż próbę extends String w komentarzu)
         * - Integer, Double, Boolean
         * Wypisz: String.class.isFinal(), Integer.class.isFinal() przez reflection.
         * (java.lang.reflect: Modifier.isFinal(klasa.getModifiers()))
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_FinalLoop {
        /*
         * 🧪 Zadanie 10:
         * W pętli for użyj final zmiennej iteracyjnej:
         * for (final int n : new int[]{1,2,3,4,5}) { ... }
         * Wypisz kwadraty. Spróbuj n++ wewnątrz (w komentarzu).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_Immutable {
        /*
         * 🧪 Zadanie 11:
         * Stwórz w pełni immutable klasę Point3D z final polami x, y, z (double).
         * Metody: translate(dx, dy, dz) → nowy Point3D, scale(factor) → nowy Point3D.
         * distance(Point3D other) → odległość euklidesowa.
         * Przetestuj łańcuch: nowy punkt → translate → scale.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_FinalTemplate {
        /*
         * 🧪 Zadanie 12:
         * Abstrakcyjna klasa DataProcessor z final void run():
         * validate() → parse() → process() → output()
         * validate() i parse() są final (nie można nadpisać).
         * process() i output() są abstract.
         * Podklasy XmlProcessor i CsvProcessor.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_StringImmutable {
        /*
         * 🧪 Zadanie 13:
         * Demonstruj immutability Stringa:
         * String s = "hello"; s.toUpperCase(); // s nadal "hello"!
         * String upper = s.toUpperCase(); // musisz przypisać wynik
         * Sprawdź czy s == s.toUpperCase() (nie, bo nowy obiekt).
         * Porównaj przez equals().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_FinalArray {
        /*
         * 🧪 Zadanie 14:
         * final int[] arr = {1, 2, 3};
         * Sprawdź: czy można zmienić arr[0]? TAK (final to referencja, nie zawartość).
         * Czy można arr = new int[]{4,5,6}? NIE.
         * Jak zrobić naprawdę immutable tablicę? (kopiuj i ukryj za getterem)
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_FinalVirtualDispatch {
        /*
         * 🧪 Zadanie 15:
         * Stwórz Parent z metodą void display() (nie-final).
         * Child extends Parent nadpisuje display().
         * Parent z metodą final void run() wywołującą display().
         * Wywołaj run() na obiekcie Child – jaka display() zostanie użyta?
         * Wyjaśnij w komentarzu (polimorfizm działa nawet z final run!).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_ImmutableRecord {
        /*
         * 🧪 Zadanie 16:
         * Porównaj final class z record:
         * final class ClassVersion { private final int major, minor; ... }
         * record RecordVersion(int major, int minor) {}
         * Obie są immutable. Sprawdź equals/hashCode/toString dla obu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_CriticalSection {
        /*
         * 🧪 Zadanie 17:
         * Stwórz klasę SafeCounter z final int maxValue.
         * Metoda increment() nie przekracza maxValue.
         * Metoda final boolean isAtMax() nie może być nadpisana.
         * Podklasa ExtendedCounter może dodać metody, ale nie zmienić logiki limitu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Singleton {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj singleton przez enum (thread-safe i serializowalny):
         * enum DatabaseConnection {
         *   INSTANCE;
         *   public void query(String sql) { ... }
         * }
         * Sprawdź że DatabaseConnection.INSTANCE zawsze jest tym samym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_FinalInInterface {
        /*
         * 🧪 Zadanie 19:
         * Wszystkie pola w interfejsie są implicitly public static final.
         * Stwórz interfejs HttpStatus z polami:
         * int OK = 200, NOT_FOUND = 404, SERVER_ERROR = 500.
         * Sprawdź przez reflection: Modifier.isFinal(HttpStatus.class.getField("OK").getModifiers()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Compound {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę Transaction z:
         * - final long id (generowany przez static counter)
         * - final double amount
         * - final String type ("CREDIT" lub "DEBIT")
         * - final long timestamp = System.currentTimeMillis()
         * Brak setterów. Metoda boolean isCredit().
         * Utwórz 5 transakcji i wypisz podsumowanie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImmutableGraph {
        /*
         * 🧪 Zadanie 21:
         * Stwórz immutable klasę ImmutableGraph z:
         * - final int[][] adjacencyMatrix
         * - final int nodeCount
         * Konstruktor robi defensive copy macierzy.
         * Metoda addEdge(int from, int to) zwraca NOWY ImmutableGraph z dodaną krawędzią.
         * Metoda boolean hasPath(int from, int to) (BFS).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_FinalHierarchy {
        /*
         * 🧪 Zadanie 22:
         * Stwórz hierarchię:
         * abstract Animal → abstract Pet extends Animal (final greet()) → final Cat, final Dog.
         * Cat i Dog nie mogą być rozszerzone.
         * Animal.makeSound() – nadpisywalne.
         * Pet.greet() – final, wywołuje makeSound().
         * Przetestuj polimorfizm.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_BuilderImmutable {
        /*
         * 🧪 Zadanie 23:
         * Stwórz immutable klasę FlightTicket z final polami:
         * flightNumber, from, to (String), departureTime (long), price (double), seat (String).
         * Prywatny konstruktor.
         * Statyczna klasa Builder (mutable) z metodą build() tworzącą immutable FlightTicket.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_FinalEfficiency {
        /*
         * 🧪 Zadanie 24:
         * Porównaj wydajność final vs non-final metod:
         * Interfejs MathOp z methodami: int square(int n), int cube(int n).
         * Klasa FastMath z final metodami.
         * Klasa SlowMath (zwykłe metody).
         * Zmierz 10M wywołań każdej wersji.
         * W komentarzu wyjaśnij czy JIT eliminuje różnicę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_SealedSimulation {
        /*
         * 🧪 Zadanie 25:
         * Symuluj sealed class pattern bez Java 17:
         * final class Result {
         *   private constructor
         *   public static final class Success extends Result
         *   public static final class Failure extends Result
         *   static Result success(T value), static Result failure(String error)
         * }
         * Przetestuj wynik operacji, switch po typie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_FinalProtocol {
        /*
         * 🧪 Zadanie 26:
         * Abstrakcyjna klasa NetworkRequest z final metodą:
         * final Response send() → authenticate() → buildPayload() → transmit() → parseResponse()
         * Metody authenticate i transmit są final (security-critical).
         * buildPayload i parseResponse są abstract.
         * Podklasy: JsonRequest i XmlRequest.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_ValueObject {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj Value Object (Domain Driven Design):
         * final class Email z final String value.
         * Konstruktor waliduje format przez regex.
         * equals() i hashCode() na podstawie value.
         * final class PhoneNumber z final String digits (tylko cyfry).
         * Użyj obu w klasie Contact.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Inheritance {
        /*
         * 🧪 Zadanie 28:
         * Pokaż problem z dziedziczeniem i jak final go rozwiązuje:
         * Klasa BadCounter { int count = 0; void increment() { count++; } int getCount() {...} }
         * BadSafeCounter extends BadCounter, nadpisuje increment() – może psuć invariant!
         * GoodCounter { final void increment() { count++; } } – bezpieczna.
         * Wyjaśnij w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_LambdaCapture {
        /*
         * 🧪 Zadanie 29:
         * Stwórz tablicę Runnable[] z lambdami w pętli:
         * for (int i = 0; i < 5; i++) { final int idx = i; runnables[i] = () -> print(idx); }
         * Porównaj z wersją bez final (effectively final też działa).
         * Wyjaśnij problem "variable capture" i dlaczego effectively final jest wymagane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_ImmutableSystem {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj immutable system konfiguracji gry:
         * final class GameConfig z:
         * - final String mapName, final int width, final int height
         * - final int[] spawnPoints (defensively copied)
         * - final String[] allowedItems (defensively copied)
         * - Builder z walidacją
         * - withMapName(String) → nowy GameConfig
         * Stwórz konfigurację i "zaktualizuj" mapę bez mutowania oryginału.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
