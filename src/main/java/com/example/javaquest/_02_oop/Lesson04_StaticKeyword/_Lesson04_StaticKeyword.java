package com.example.javaquest._02_oop.Lesson04_StaticKeyword;

public class _Lesson04_StaticKeyword {

    public static void main(String[] args) {

        /*
         * ============================================================
         * ⚡ SŁOWO KLUCZOWE static
         * ============================================================
         * `static` oznacza, że dana składowa należy do KLASY, a nie do instancji.
         *
         * Składowe statyczne:
         * - pola statyczne  → wspólne dla wszystkich obiektów
         * - metody statyczne → nie mają dostępu do this, działają na klasie
         * - bloki statyczne → jednorazowa inicjalizacja przy ładowaniu klasy
         * - klasy statyczne  → klasy zagnieżdżone (static nested class)
         *
         * Dostęp: NazwaKlasy.pole / NazwaKlasy.metoda()
         * Choć można przez referencję obiektu, jest to mylące i niezalecane.
         */

        /*
         * ============================================================
         * 🔢 POLA STATYCZNE (class-level state)
         * ============================================================
         * Pole statyczne istnieje JEDNO dla całej klasy.
         * Wszystkie obiekty widzą i modyfikują tę samą wartość.
         */

        System.out.println("Liczba pojazdów: " + Vehicle.count); // 0

        Vehicle v1 = new Vehicle("Toyota");
        Vehicle v2 = new Vehicle("BMW");
        Vehicle v3 = new Vehicle("Ford");

        System.out.println("Liczba pojazdów: " + Vehicle.count); // 3

        /*
         * ============================================================
         * ⚙️ METODY STATYCZNE
         * ============================================================
         * - Nie mają dostępu do `this` (bo nie należą do instancji)
         * - Nie mogą odwoływać się do pól instancyjnych (niestatycznych)
         * - Mogą odwoływać się do pól i metod statycznych
         * - Wywoływane na klasie: Klasa.metoda()
         * - Często używane jako metody pomocnicze (utility)
         */

        double area = MathUtils.circleArea(5.0);
        System.out.println("Pole koła r=5: " + MathUtils.round(area, 2));

        int max = MathUtils.max(42, 17);
        System.out.println("Max(42, 17): " + max);

        boolean even = MathUtils.isEven(8);
        System.out.println("Czy 8 jest parzyste? " + even);

        /*
         * ============================================================
         * 🏭 FABRYKOWE METODY STATYCZNE (factory methods)
         * ============================================================
         * Zamiast konstruktora – statyczna metoda tworzy i zwraca obiekt.
         * Korzyść: własna nazwa, logika warunkowa przy tworzeniu.
         */

        Color red = Color.of(255, 0, 0);
        Color fromHex = Color.fromHex("#00FF00");
        System.out.println("Kolor: " + red);
        System.out.println("Z hex: " + fromHex);

        /*
         * ============================================================
         * 🚀 BLOKI STATYCZNE (static initializer blocks)
         * ============================================================
         * Wykonują się RAZ przy pierwszym załadowaniu klasy przez JVM.
         * Używane do inicjalizacji złożonych pól statycznych.
         */

        System.out.println("Konfiguracja: " + Config.DATABASE_URL);
        System.out.println("Max połączeń: " + Config.MAX_CONNECTIONS);

        /*
         * ============================================================
         * 🔒 KLASA SINGLETON (wzorzec z static)
         * ============================================================
         * Gwarantuje istnienie dokładnie JEDNEJ instancji klasy.
         * Prywatny konstruktor + statyczna metoda dostępowa.
         */

        AppLogger logger1 = AppLogger.getInstance();
        AppLogger logger2 = AppLogger.getInstance();

        logger1.log("Start aplikacji");
        System.out.println("Ten sam obiekt? " + (logger1 == logger2)); // true

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - static field  → wspólne dla wszystkich instancji (liczniki, stałe)
         * - static method → bez `this`, wywoływany przez klasę
         * - static block  → jednorazowa inicjalizacja przy ładowaniu klasy
         * - factory method → statyczna metoda zamiast konstruktora
         * - singleton     → prywatny konstruktor + static getInstance()
         */
    }
}

class Vehicle {
    static int count = 0; // pole statyczne – jedno dla całej klasy
    String brand;

    Vehicle(String brand) {
        this.brand = brand;
        count++; // inkrementowany przy każdym new Vehicle()
    }
}

class MathUtils {
    // Klasa utility – sam prywatny konstruktor, same metody statyczne
    private MathUtils() {}

    static double circleArea(double radius) {
        return Math.PI * radius * radius;
    }

    static double round(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }

    static int max(int a, int b) {
        return a >= b ? a : b;
    }

    static boolean isEven(int n) {
        return n % 2 == 0;
    }
}

class Color {
    private int r, g, b;

    private Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Factory method – tworzenie przez nazwę
    static Color of(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("Wartości muszą być 0-255");
        }
        return new Color(r, g, b);
    }

    // Factory method – z hex string
    static Color fromHex(String hex) {
        hex = hex.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new Color(r, g, b);
    }

    @Override
    public String toString() {
        return String.format("Color(%d, %d, %d)", r, g, b);
    }
}

class Config {
    static final String DATABASE_URL;
    static final int MAX_CONNECTIONS;

    // Blok statyczny – wykonuje się raz przy załadowaniu klasy
    static {
        System.out.println("[Config] Ładowanie konfiguracji...");
        DATABASE_URL = "jdbc:postgresql://localhost:5432/app";
        MAX_CONNECTIONS = 10;
    }
}

class AppLogger {
    private static AppLogger instance; // jedyna instancja
    private int logCount = 0;

    private AppLogger() {} // prywatny konstruktor

    static AppLogger getInstance() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    void log(String message) {
        logCount++;
        System.out.println("[LOG #" + logCount + "] " + message);
    }
}
