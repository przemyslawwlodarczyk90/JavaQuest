package com.example.javaquest._02_oop.Lesson03_Constructors;

public class _Exercises_Lesson03_Constructors {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicConstructor {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Cat z polami name (String) i age (int).
         * Dodaj konstruktor Cat(String name, int age).
         * Utwórz dwa koty i wypisz ich dane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_DefaultConstructor {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Laptop z polami brand (String="Unknown") i ram (int=8).
         * Dodaj konstruktor bezargumentowy ustawiający wartości domyślne.
         * Wypisz dane laptopa po stworzeniu bez argumentów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_Overloaded {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę Pizza z polami size (String), crust (String).
         * Konstruktory:
         * - Pizza() → size="Medium", crust="thin"
         * - Pizza(String size) → crust="thin"
         * - Pizza(String size, String crust)
         * Przetestuj wszystkie 3 wersje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_ThisChain {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę Car z polami brand, model, color (String), year (int).
         * Użyj łańcuchowania konstruktorów przez this().
         * Konstruktor z 1 parametrem (brand) ustawia resztę na wartości domyślne.
         * Wypisz auto stworzone przez Car("Toyota").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_CopyConstructor {
        /*
         * 🧪 Zadanie 5:
         * Stwórz klasę Point z polami x, y (double).
         * Dodaj konstruktor kopiujący Point(Point other).
         * Stwórz punkt, skopiuj go, zmień kopię i sprawdź, że oryginał bez zmian.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_InitOrder {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę InitDemo z polem int value = 10.
         * Dodaj blok inicjalizacyjny { value += 5; System.out.println("Blok: " + value); }
         * Dodaj konstruktor { value += 5; System.out.println("Konstruktor: " + value); }
         * Stwórz obiekt i obserwuj kolejność inicjalizacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_StaticInit {
        /*
         * 🧪 Zadanie 7:
         * Stwórz klasę AppSettings z polem statycznym String DEFAULT_LANG.
         * Blok statyczny { DEFAULT_LANG = "pl"; System.out.println("Załadowano ustawienia"); }
         * Stwórz 3 obiekty i sprawdź, że blok statyczny wykonał się tylko raz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_BlankFinal {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę Invoice z polami: final int id i final String currency.
         * Pole id ustawiane w konstruktorze z generatora: static int nextId = 1000.
         * Każdy nowy Invoice otrzymuje kolejne id (1001, 1002...).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaja
        }
    }

    static class Exercise09_Validation {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę Age z polem int value.
         * Konstruktor rzuca IllegalArgumentException gdy value < 0 lub > 150.
         * Przetestuj prawidłową wartość i błędną (sprawdź wyjątek przez try-catch).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_MultiField {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę Config z polami: host (String), port (int), secure (boolean).
         * Konstruktory:
         * - Config() → "localhost", 8080, false
         * - Config(String host) → port=8080, secure=false
         * - Config(String host, int port, boolean secure)
         * Wypisz konfigurację każdego obiektu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConstructorCounter {
        /*
         * 🧪 Zadanie 11:
         * Stwórz klasę Ticket z polem final int number.
         * Statyczny licznik nextNumber zaczyna od 1.
         * Każdy Ticket dostaje kolejny numer (przez konstruktor).
         * Stwórz 5 biletów i wypisz ich numery.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_DeepCopy {
        /*
         * 🧪 Zadanie 12:
         * Stwórz klasę Data z polem int[] values.
         * Konstruktor kopiujący Data(Data other) robi głęboką kopię tablicy.
         * Udowodnij, że zmiana skopiowanej tablicy nie wpływa na oryginał.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_MultipleBlocks {
        /*
         * 🧪 Zadanie 13:
         * Stwórz klasę MultiInit z:
         * - static int staticValue = 1
         * - statycznym blokiem: staticValue *= 10
         * - polem int instanceValue = 1
         * - blokiem instancyjnym: instanceValue += 100
         * - konstruktorem: instanceValue *= 2
         * Stwórz 2 obiekty i wypisz obie wartości. Wyjaśnij w komentarzu oczekiwany wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_FactoryMethod {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę Shape z prywatnym konstruktorem i polem type (String).
         * Statyczne metody fabryczne:
         * - Shape circle() → type="CIRCLE"
         * - Shape square() → type="SQUARE"
         * - Shape triangle() → type="TRIANGLE"
         * Przetestuj tworzenie każdego kształtu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_ChainedInit {
        /*
         * 🧪 Zadanie 15:
         * Stwórz klasę HttpRequest z polami: url (String), method="GET",
         * timeout=3000, followRedirects=true.
         * Konstruktor HttpRequest(String url) – ustawia URL, reszta domyślna.
         * Konstruktor HttpRequest(String url, String method) – this(url).
         * Pełny konstruktor z wszystkimi 4 polami.
         * Wypisz każde zapytanie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_Named {
        /*
         * 🧪 Zadanie 16:
         * Stwórz klasę Color z polami r, g, b (int).
         * Prywatny konstruktor Color(int r, int g, int b).
         * Statyczne metody: red(), green(), blue(), white(), black(), fromHex(String hex).
         * Metoda toString: "#RRGGBB" (formatowanej jako hex).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_Singleton {
        /*
         * 🧪 Zadanie 17:
         * Stwórz singleton Logger z prywatnym konstruktorem.
         * Statyczna metoda getInstance().
         * Metoda log(String message) wypisuje "[LOG] message".
         * Sprawdź, że getInstance() zawsze zwraca ten sam obiekt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Prototype {
        /*
         * 🧪 Zadanie 18:
         * Stwórz klasę GameCharacter z polami: name, race (String), health, mana (int).
         * Konstruktor kopiujący tworzy duplikat postaci.
         * Stwórz postać "bazową" (template), skopiuj ją 3 razy z różnymi imionami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_LazyStatic {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasę HeavyResource z statycznym blokiem symulującym
         * "ciężką" inicjalizację (wypisz "Inicjalizacja...").
         * Metody statyczne: compute(int n) zwraca n * n.
         * Sprawdź kiedy dokładnie blok statyczny się uruchamia (przy pierwszym użyciu klasy).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ComplexInit {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę Matrix z polem int[][] data i konstruktorem Matrix(int rows, int cols).
         * Blok instancyjny wypełnia tablicę zerami.
         * Konstruktor Matrix(int[][] source) – kopiuje dane z innej tablicy (deep copy).
         * Metoda print() wypisuje tablicę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuilderPattern {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj wzorzec Builder dla klasy DatabaseConfig:
         * - host (String, wymagane)
         * - port (int, domyślnie 5432)
         * - database (String, wymagane)
         * - username (String)
         * - password (String)
         * - maxPoolSize (int, domyślnie 10)
         * Wypisz konfigurację po zbudowaniu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_RegistryPattern {
        /*
         * 🧪 Zadanie 22:
         * Stwórz klasę Animal z polami: name i species.
         * Statyczna tablica animalRegistry[] przechowuje wszystkie stworzone zwierzęta.
         * Każdy konstruktor rejestruje obiekt w tablicy.
         * Metoda statyczna printAll() wypisuje wszystkie zwierzęta.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Telescoping {
        /*
         * 🧪 Zadanie 23:
         * Zrefaktoruj "telescoping constructor" do wzorca Builder.
         * Klasa Report ma pola: title, author, content, footer, pageNumbers (boolean).
         * Wyjściowa wersja ma 5 konstruktorów. Zastąp ją Builderem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_PoolPattern {
        /*
         * 🧪 Zadanie 24:
         * Stwórz klasę Connection z prywatnym konstruktorem i statyczną pulą 3 połączeń.
         * Metoda statyczna acquire() zwraca wolne połączenie lub null.
         * Metoda release() oddaje połączenie do puli.
         * Przetestuj akwizycję, wyczerpanie puli i zwolnienie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_VersionedCopy {
        /*
         * 🧪 Zadanie 25:
         * Stwórz klasę Document z polami: content (String), version (int).
         * Konstruktor Document(String content) ustawia version=1.
         * Konstruktor Document(Document original, String newContent) tworzy
         * nową wersję z version = original.version + 1.
         * Stwórz 3 wersje dokumentu i wypisz historię.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_Abstract {
        /*
         * 🧪 Zadanie 26:
         * Stwórz abstrakcyjną klasę Shape z polem color (String).
         * Konstruktor Shape(String color).
         * Abstrakcyjna metoda double area().
         * Podklasy Circle(double radius) i Square(double side) dziedziczą przez super(color).
         * Wypisz kolory i pola kształtów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_ImmutableList {
        /*
         * 🧪 Zadanie 27:
         * Stwórz klasę ImmutableList przechowującą String[] items.
         * Konstruktor przyjmuje tablicę i zapisuje KOPIĘ (defensive copy).
         * Brak metod mutujących.
         * Metoda with(String newItem) zwraca NOWĄ ImmutableList z dodanym elementem.
         * Metoda get(int index) i int size().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_ConversionConstructors {
        /*
         * 🧪 Zadanie 28:
         * Stwórz klasę Temperature z prywatnym polem double kelvin.
         * Prywatny konstruktor Temperature(double kelvin).
         * Statyczne fabryki:
         * - fromCelsius(double c) → kelvin = c + 273.15
         * - fromFahrenheit(double f) → kelvin = (f - 32) * 5/9 + 273.15
         * Metody getCelsius(), getFahrenheit(), getKelvin().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_Recursive {
        /*
         * 🧪 Zadanie 29:
         * Stwórz klasę TreeNode z polami: int value, TreeNode left, TreeNode right.
         * Konstruktor TreeNode(int value).
         * Metody setLeft(TreeNode), setRight(TreeNode).
         * Zbuduj proste drzewo (root=10, left=5, right=15).
         * Metoda int sum() zwraca sumę wszystkich węzłów rekurencyjnie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_MultiStep {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj wzorzec Step Builder dla klasy Order:
         * Pola: customerId (String), items (String[]), deliveryAddress (String), paymentMethod (String).
         * Kroki: builder.customer("ID").addItem("X").addItem("Y").deliver("addr").pay("card").build()
         * Każdy krok zwraca następny builder (inny typ lub ten sam z walidacją).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
