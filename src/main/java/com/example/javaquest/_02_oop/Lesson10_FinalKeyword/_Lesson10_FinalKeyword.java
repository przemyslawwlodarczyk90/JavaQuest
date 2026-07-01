package com.example.javaquest._02_oop.Lesson10_FinalKeyword;

public class _Lesson10_FinalKeyword {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔒 SŁOWO KLUCZOWE final
         * ============================================================
         * `final` stosowany na trzech poziomach:
         *
         * 1. final ZMIENNA  → wartość nie może być zmieniona po inicjalizacji
         * 2. final METODA   → metody nie można nadpisać (@Override) w podklasie
         * 3. final KLASA    → klasy nie można rozszerzać (extends)
         */

        /*
         * ============================================================
         * 🔹 1. FINAL ZMIENNA / POLE
         * ============================================================
         * final lokalna zmienna: musi być przypisana dokładnie raz.
         * final pole instancyjne: musi być przypisane w konstruktorze lub inicjalizatorze.
         * final pole statyczne + static = stała klasy (UPPER_SNAKE_CASE).
         */

        final int maxRetries = 3;
        // maxRetries = 5; // ❌ błąd kompilacji – nie można zmieniać final

        System.out.println("Max retries: " + maxRetries);

        // Stałe statyczne
        System.out.println("PI: " + MathConst.PI);
        System.out.println("E: " + MathConst.E);
        System.out.println("Max długość nazwy: " + AppConfig.MAX_NAME_LENGTH);

        // final pole instancyjne
        ImmutablePoint point = new ImmutablePoint(10, 20);
        System.out.println("Punkt: " + point.getX() + ", " + point.getY());
        // point.x = 99; // ❌ private + final – niedostępne i niezmienne

        /*
         * ============================================================
         * 🔹 2. FINAL METODA – zakaz nadpisania
         * ============================================================
         * Metody final nie można nadpisać w podklasie.
         * Używane gdy implementacja musi być zawsze taka sama.
         */

        SecureAuth auth = new SecureAuth("admin", "pass123");
        System.out.println("Zalogowany? " + auth.login()); // final – nie można zmienić logiki
        auth.showUserInfo(); // przesłonięta

        /*
         * ============================================================
         * 🔹 3. FINAL KLASA – zakaz dziedziczenia
         * ============================================================
         * Klasy final nie można rozszerzać (extends).
         *
         * Przykłady z JDK:
         * - String      → final (stąd immutability)
         * - Integer     → final
         * - Math        → final
         * - System      → final
         *
         * Powody:
         * - bezpieczeństwo (nikt nie podmieni zachowania)
         * - niezmienność (immutability)
         * - wydajność (JVM może optymalizować)
         */

        FinalString fs = new FinalString("Witaj");
        System.out.println("Wartość: " + fs.getValue());
        System.out.println("Długość: " + fs.length());
        // class SubFinalString extends FinalString {} // ❌ błąd kompilacji

        /*
         * ============================================================
         * 🔹 EFFECTIVELY FINAL (od Java 8)
         * ============================================================
         * Zmienna lokalna, która NIE jest oznaczona final,
         * ale nigdy nie jest modyfikowana po przypisaniu.
         *
         * Lambdy i klasy anonimowe mogą używać "effectively final" zmiennych
         * bez konieczności pisania słowa `final`.
         */

        String prefix = "Wynik: "; // effectively final – nigdy nie zmieniana
        // prefix = "coś innego"; // gdybyśmy odkomentowali → lambda by nie zadziałała

        Runnable r = () -> System.out.println(prefix + 42); // OK – prefix effectively final
        r.run();

        /*
         * ============================================================
         * 🔹 BLANK FINAL (puste final)
         * ============================================================
         * Pole final bez wartości przy deklaracji – przypisane w konstruktorze.
         * Każdy konstruktor MUSI je zainicjalizować!
         */

        Config config1 = new Config("development");
        Config config2 = new Config("production", 8080);

        System.out.println("Config1 env: " + config1.environment + ", port: " + config1.port);
        System.out.println("Config2 env: " + config2.environment + ", port: " + config2.port);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - final zmienna: przypisana raz, nie do zmiany
         * - static final: stała klasowa (UPPER_SNAKE_CASE)
         * - final metoda: nie można nadpisać w podklasie
         * - final klasa: nie można rozszerzać
         * - blank final: final pole bez wartości – zainicjalizowane w konstruktorze
         * - effectively final: nieoznaczona, ale niezmienna – OK dla lambdy
         */
    }
}

class MathConst {
    static final double PI = 3.14159265358979;
    static final double E  = 2.71828182845904;
}

class AppConfig {
    static final int MAX_NAME_LENGTH = 50;
    static final int MAX_RETRY_COUNT = 3;
    static final String DEFAULT_LANGUAGE = "pl";
}

class ImmutablePoint {
    private final int x;
    private final int y;

    ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() { return x; }
    int getY() { return y; }
}

class BaseAuth {
    protected String username;
    protected String password;

    BaseAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // final method – nie można nadpisać logiki uwierzytelnienia
    final boolean login() {
        return username != null && password != null && password.length() >= 6;
    }

    void showUserInfo() {
        System.out.println("Użytkownik: " + username);
    }
}

class SecureAuth extends BaseAuth {
    SecureAuth(String username, String password) {
        super(username, password);
    }

    // final method login() – nie można nadpisać (próba = błąd kompilacji)

    @Override
    void showUserInfo() { // tę można nadpisać
        System.out.println("[SECURE] Użytkownik: " + username + " [hasło ukryte]");
    }
}

final class FinalString {
    private final String value;

    FinalString(String value) {
        this.value = value;
    }

    String getValue() { return value; }

    int length() { return value.length(); }
}

class Config {
    final String environment; // blank final – przypisywane w konstruktorze
    final int port;

    Config(String environment) {
        this.environment = environment;
        this.port = 3000; // domyślny port
    }

    Config(String environment, int port) {
        this.environment = environment;
        this.port = port;
    }
}
