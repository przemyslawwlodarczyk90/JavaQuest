package com.example.javaquest._02_oop.Lesson04_StaticKeyword;

public class _Exercises_Lesson04_StaticKeyword {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StaticField {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Dog z polem statycznym int count i polem instancyjnym String name.
         * Konstruktor inkrementuje count.
         * Utwórz 4 psy i wypisz Dog.count.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_StaticMethod {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Converter z metodami statycznymi:
         * - static double kgToLbs(double kg)   → kg * 2.20462
         * - static double lbsToKg(double lbs)  → lbs / 2.20462
         * Wywołaj bez tworzenia obiektu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_Constant {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę Physics z stałymi statycznymi:
         * - SPEED_OF_LIGHT = 299_792_458 (m/s)
         * - GRAVITY = 9.81 (m/s²)
         * - PLANCK = 6.626e-34
         * Wypisz każdą stałą z opisem jednostki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_StaticBlock {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę Config z statycznym polem String[] allowedCountries.
         * Blok statyczny wypełnia tablicę: {"PL", "DE", "FR", "US"}.
         * Metoda statyczna isAllowed(String country) sprawdza czy kraj jest na liście.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_Singleton {
        /*
         * 🧪 Zadanie 5:
         * Stwórz prostego Singletona – klasę Database z prywatnym konstruktorem,
         * statyczną instancją i metodą getInstance().
         * Metodą query(String sql) wypisuje "Executing: [sql]".
         * Sprawdź że dwa wywołania getInstance() dają ten sam obiekt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_UtilityClass {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę StringUtils z prywatnymkonstruktorem i metodami statycznymi:
         * - static boolean isEmpty(String s) → null lub ""
         * - static String capitalize(String s) → pierwsza litera wielka
         * - static int countVowels(String s) → liczba samogłosek (aeiouAEIOU)
         * Przetestuj każdą metodę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_StaticCounter {
        /*
         * 🧪 Zadanie 7:
         * Stwórz klasę IdGenerator z statycznym polem int lastId = 0.
         * Metoda statyczna nextId() zwraca ++lastId.
         * Wywołaj nextId() 5 razy i wypisz kolejne wartości.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_StaticVsInstance {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę Account z:
         * - statycznym polem double interestRate = 0.05
         * - polem instancyjnym double balance
         * - metodą instancyjną addInterest() → balance *= (1 + interestRate)
         * Stwórz 3 konta z różnymi saldami, zmień interestRate na 0.03,
         * dodaj odsetki do wszystkich. Wypisz salda.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_FactoryMethod {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę Currency z prywatnym konstruktorem i polem code (String), symbol (String).
         * Statyczne fabryki: Currency.PLN(), Currency.EUR(), Currency.USD().
         * Metoda format(double amount) → "100.00 zł" / "100.00 €" / "100.00 $".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_StaticImport {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę ArrayUtils z metodami statycznymi:
         * - static int sum(int[] arr)
         * - static double average(int[] arr)
         * - static int max(int[] arr)
         * - static int min(int[] arr)
         * Przetestuj dla tablicy {3, 7, 1, 9, 4, 6, 2}.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_StaticRegistry {
        /*
         * 🧪 Zadanie 11:
         * Stwórz klasę Plugin z polem name i statyczną tablicą Plugin[] registry.
         * Każdy konstruktor Plugin(String name) rejestruje się w tablicy.
         * Metoda statyczna Plugin.list() wypisuje wszystkie zarejestrowane pluginy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_StaticCache {
        /*
         * 🧪 Zadanie 12:
         * Stwórz klasę FibCache z statyczną tablicą long[] memo (50 elementów).
         * Statyczna metoda fib(int n) oblicza Fibonacciego z memoizacją.
         * Statyczny blok inicjalizacyjny ustawia memo[0]=0, memo[1]=1.
         * Sprawdź fib(40) i zmierz szybkość vs naiwna rekurencja.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_ConfigSingleton {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj Singleton AppConfig z polami:
         * - debug (boolean)
         * - maxConnections (int)
         * - appName (String)
         * Metoda statyczna getInstance().
         * Ustaw wartości przez settery i odczytaj z różnych "miejsc" (różne zmienne wskazujące na singleton).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_StaticNestedClass {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę outer Order z statyczną klasą wewnętrzną LineItem
         * (pola: productName, quantity, price).
         * Order ma tablicę LineItem[] items.
         * Metoda totalPrice() sumuje quantity * price wszystkich pozycji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_MultitonPattern {
        /*
         * 🧪 Zadanie 15:
         * Stwórz klasę Logger z statyczną tablicą Logger[] instances (3 elementy: INFO, WARN, ERROR).
         * Metoda statyczna Logger.getLogger(String level) zwraca odpowiedni logger.
         * Metoda log(String message) wypisuje "[LEVEL] message".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_StaticFactory {
        /*
         * 🧪 Zadanie 16:
         * Stwórz klasę Fraction z polami final int numerator, denominator.
         * Prywatny konstruktor.
         * Statyczna metoda of(int n, int d) – redukuje ułamek przez NWD i zwraca Fraction.
         * Metoda toString() → "3/4".
         * Metoda add(Fraction other) → nowa Fraction z sumą.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_StaticInitOrder {
        /*
         * 🧪 Zadanie 17:
         * Stwórz klasy A, B, C gdzie:
         * - A ma statyczne pole int x = B.value
         * - B ma statyczne pole int value = 10
         * - C ładuje A i wypisuje A.x
         * Przeanalizuj kolejność ładowania klas i przewidź wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ObjectCount {
        /*
         * 🧪 Zadanie 18:
         * Stwórz klasę Tracked z polem statycznym int created i int active.
         * Konstruktor inkrementuje created i active.
         * Metoda destroy() dekrementuje active.
         * Stwórz 5 obiektów, "zniszcz" 2, wypisz statystyki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_StaticBuilder {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasę Email z prywatnym konstruktorem i Builder jako statyczną klasą wewnętrzną.
         * Pola: from, to, subject, body (String).
         * Zbuduj email metodą fluent, wypisz go.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ThreadLocal {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę RequestContext z statycznym polem requestCount (int)
         * i polem instancyjnym userId (String).
         * Metoda statyczna getCount() → requestCount.
         * Symuluj 5 requestów (każdy new RequestContext("user_X")), wypisz liczbę requestów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImmutableSingleton {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj thread-safe Singleton przy użyciu "Initialization-on-demand holder":
         * class Singleton {
         *   private static class Holder { static final Singleton INSTANCE = new Singleton(); }
         *   static Singleton getInstance() { return Holder.INSTANCE; }
         * }
         * Przetestuj poprawność wzorca.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ServiceLocator {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj uproszczony Service Locator:
         * - statyczna tablica par {String name, Object service}
         * - metoda register(String name, Object service)
         * - metoda get(String name) → Object
         * Zarejestruj "database" i "logger", pobierz i użyj.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_FlyweightPattern {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj wzorzec Flyweight dla klasy FontStyle (bold, italic, size).
         * Statyczna tablica/cache przechowuje już stworzone instancje.
         * Metoda statyczna getStyle(boolean b, boolean i, int size) zwraca istniejącą lub nową.
         * Sprawdź że te same parametry zwracają TEN SAME obiekt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_EventBus {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prosty statyczny EventBus:
         * - statyczna tablica listenerów String[]
         * - statyczna metoda subscribe(String listener)
         * - statyczna metoda publish(String event) → powiadamia wszystkich
         * Dodaj 3 listenery, opublikuj 2 zdarzenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_StaticProxy {
        /*
         * 🧪 Zadanie 25:
         * Stwórz interfejs Service z metodą execute(String task).
         * Klasa RealService implementuje Service.
         * Klasa ServiceProxy implementuje Service, deleguje do RealService,
         * ale statycznie liczy wywołania i wypisuje "[PROXY] wywołanie #N".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_EnumLikeSingleton {
        /*
         * 🧪 Zadanie 26:
         * Stwórz klasę Season z 4 statycznymi finalnymi instancjami (SPRING, SUMMER, AUTUMN, WINTER).
         * Każda instancja ma pole name i nextSeason().
         * Metoda statyczna fromName(String name) zwraca odpowiednią porę roku.
         * Symuluj przejście przez wszystkie 4 pory roku.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_StaticMethodOverriding {
        /*
         * 🧪 Zadanie 27:
         * Stwórz klasy ParentStatic i ChildStatic.
         * Obie mają static void describe() z różnymi treściami.
         * Sprawdź przez zmienną typu ParentStatic wskazującą na ChildStatic
         * co się wywołuje (static methods are hidden, not overridden!).
         * Wyjaśnij różnicę między hiding a overriding w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_SingletonEnum {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj Singleton przez enum (najlepsza praktyka w Javie):
         * enum DatabaseEnum { INSTANCE; ... }
         * Dodaj metody query(String sql) i getConnectionCount().
         * Porównaj z klasycznym Singleton.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_LazyInit {
        /*
         * 🧪 Zadanie 29:
         * Stwórz klasę ExpensiveResource z statycznym polem instance.
         * getInstance() używa double-checked locking:
         * if (instance == null) { synchronized(...) { if (instance == null) { ... } } }
         * (użyj volatile dla instance).
         * Wypisz "Inicjalizacja..." tylko raz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_StaticDSL {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj prosty DSL (Domain Specific Language) do budowania SQL:
         * Klasa SQL z metodami statycznymi zwracającymi nową instancję SQL:
         * SQL.select("name, age").from("users").where("age > 18").orderBy("name").limit(10).build()
         * Każda metoda zwraca ten sam obiekt (this) – fluent API.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
