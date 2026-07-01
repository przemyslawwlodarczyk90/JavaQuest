package com.example.javaquest._02_oop.Lesson09_AccessModifiers;

public class _Exercises_Lesson09_AccessModifiers {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PrivateField {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Wallet z private polem double balance.
         * Dodaj metodę deposit(double) i getter getBalance().
         * Spróbuj dostępu bezpośredniego do pola z zewnątrz (w komentarzu) – wyjaśnij błąd.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_PublicMethod {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Calculator z metodami public add, subtract (dostępnymi z zewnątrz)
         * i private validate(int n) sprawdzającą czy n != 0.
         * Pokaż że validate() nie jest dostępna z zewnątrz klasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_ProtectedField {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę Animal z protected polem int energy.
         * Podklasa Dog może bezpośrednio modyfikować energy.
         * Sprawdź że z "zewnątrz" (w main) nie można dostać się do energy przez obiekt Animal.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_PackagePrivate {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę InternalHelper bez modyfikatora dostępu (package-private).
         * Metoda void help() też bez modyfikatora.
         * Wywołaj help() z tej samej klasy testowej (ten sam pakiet) – działa.
         * W komentarzu wyjaśnij co by się stało gdyby klasa była w innym pakiecie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_Encapsulation {
        /*
         * 🧪 Zadanie 5:
         * Stwórz klasę Employee z private polami: name (String), salary (double), ssn (String).
         * Dodaj gettery dla name i salary, ale NIE dla ssn (całkowicie ukryte).
         * Metoda public boolean verifySSN(String input) sprawdza czy input == ssn.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_AccessLevels {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę AccessDemo z:
         * - public String publicField = "public"
         * - protected String protectedField = "protected"
         * - String packageField = "package"
         * - private String privateField = "private"
         * Stwórz podklasę SubAccessDemo i sprawdź dostęp do każdego pola.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_PrivateConstructor {
        /*
         * 🧪 Zadanie 7:
         * Stwórz klasę Singleton z private konstruktorem.
         * Metoda statyczna getInstance() zwraca jedyną instancję.
         * Sprawdź że nie można wywołać new Singleton() z zewnątrz (w komentarzu).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ProtectedMethod {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę Game z protected metodą loadLevel() wypisującą "Ładowanie poziomu".
         * Podklasa RPG wywołuje loadLevel() z własnej metody startGame().
         * Z main wywołaj startGame() przez obiekt RPG.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_PublicAPI {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę TextProcessor z:
         * - public String process(String text) – główne API
         * - private String trim(String text) – helper
         * - private String normalize(String text) – helper
         * - private String encode(String text) – helper
         * process() wywołuje wszystkie trzy helperów w kolejności.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_MinimumAccess {
        /*
         * 🧪 Zadanie 10:
         * Refaktoruj klasę Message, która ma wszystkie pola public:
         * public String content, public String sender, public long timestamp.
         * Zmień na private, dodaj gettery (bez setterów – immutable przez konstruktor).
         * W komentarzu wyjaśnij dlaczego to lepsze podejście.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ProtectedTemplate {
        /*
         * 🧪 Zadanie 11:
         * Stwórz abstrakcyjną klasę Algorithm z:
         * - public final void execute() (nie do nadpisania)
         * - protected abstract void prepare()
         * - protected abstract void run()
         * - protected void cleanup() { System.out.println("Czyszczenie"); }
         * Podklasy mogą nadpisać prepare/run, opcjonalnie cleanup.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_InheritanceAccess {
        /*
         * 🧪 Zadanie 12:
         * Hierarchia: Vehicle → Car → ElectricCar.
         * Vehicle: protected int batteryLevel, private String vin.
         * Car: używa batteryLevel, nie może używać vin.
         * ElectricCar: używa batteryLevel, dodaje charge().
         * Wypisz dostępne pola na każdym poziomie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_FriendMethod {
        /*
         * 🧪 Zadanie 13:
         * Java nie ma "friend" z C++, ale package-private daje podobny efekt.
         * Stwórz dwie klasy w tym samym "pakiecie":
         * Matrix i MatrixHelper (bez modyfikatora – package-private).
         * MatrixHelper ma dostęp do package-private metod Matrix.
         * Wypisz wynik mnożenia macierzy przez helper.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_NestedAccess {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę Outer z private polem String secret = "ABC".
         * Wewnętrzna klasa Inner może odczytać secret.
         * Stwórz obiekt Inner z zewnątrz, wywołaj metodę odczytującą secret.
         * (Klasy wewnętrzne mają dostęp do private składowych klasy zewnętrznej)
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_ApiDesign {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj klasa Stack<T> z minimalnym publicznym API:
         * - public void push(T item)
         * - public T pop()
         * - public T peek()
         * - public boolean isEmpty()
         * - public int size()
         * Wszystkie pomocnicze metody i pola powinny być private.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_ProtectedConstructor {
        /*
         * 🧪 Zadanie 16:
         * Stwórz abstrakcyjną klasę AbstractConnection z protected konstruktorem.
         * Podklasy MySQLConnection, PostgreSQLConnection mogą go wywołać przez super().
         * Nie można stworzyć AbstractConnection bezpośrednio (abstract + protected).
         * Przetestuj tworzenie przez podklasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_Visibility {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę widoczności w komentarzach i przetestuj każdy przypadek:
         *
         * | Modyfikator | Ta klasa | Podklasa | Pakiet | Świat |
         * |-------------|----------|----------|--------|-------|
         * | private     |    ✅    |    ❌    |   ❌   |  ❌   |
         * | (brak)      |    ✅    |    ❌*   |   ✅   |  ❌   |
         * | protected   |    ✅    |    ✅    |   ✅   |  ❌   |
         * | public      |    ✅    |    ✅    |   ✅   |  ✅   |
         *
         * Napisz kod potwierdzający każdy wiersz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Library {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj bibliotekę do obsługi dat (uproszczoną):
         * - public class SimpleDate (klasa publiczna)
         * - package-private class DateParser (helper wewnętrzny)
         * - package-private class DateFormatter (helper wewnętrzny)
         * Użytkownik widzi tylko SimpleDate, parsowanie i formatowanie są ukryte.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_Sealed {
        /*
         * 🧪 Zadanie 19:
         * Symuluj sealed class (Java 17) przez private konstruktor:
         * Klasa Result z private konstruktorem.
         * Dwie klasy wewnętrzne: Success i Failure.
         * Statyczne fabryki: Result.success(String value), Result.failure(String error).
         * Metoda isSuccess() i getValue()/getError().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Builder {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę Pizza z:
         * - private pola: size, crust, cheese (String), extraToppings (String[])
         * - prywatny konstruktor
         * - Publiczna klasa statyczna Builder
         * - Builder ma publiczne metody fluent
         * - Builder.build() wywołuje prywatny konstruktor Pizza
         * Wypisz zamówioną pizzę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AccessAudit {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj system audytu dostępu:
         * Klasa SecureData z private danymi.
         * Każdy dostęp przez getter jest logowany (kiedy, kto – symulowany przez thread name).
         * Utwórz 3 "wątki" (symulowane przez zwykłe wywołania) i sprawdź logi.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ModularDesign {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj moduł autoryzacji:
         * - public interface AuthService (login, logout, isLoggedIn)
         * - package-private class UserCredentials (dane uwierzytelniające)
         * - package-private class TokenManager (zarządza tokenami)
         * - public class AuthServiceImpl implements AuthService
         * Zewnętrzny kod widzi tylko AuthService i AuthServiceImpl.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_PrivateOverride {
        /*
         * 🧪 Zadanie 23:
         * Prywatna metoda NIE może być nadpisana (jest hidden, nie overridden).
         * Stwórz klasę Parent z private void test() → "parent".
         * Stwórz Child extends Parent z void test() → "child" (to NOWA metoda, nie override!).
         * Przypisz new Child() do zmiennej Parent i wywołaj test().
         * Wyjaśnij wynik w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_ReflectionBypass {
        /*
         * 🧪 Zadanie 24:
         * Pokaż że reflection może ominąć private (edukacyjnie):
         * Klasa Secret z private String value = "sekret".
         * Użyj java.lang.reflect.Field: field.setAccessible(true); field.get(obj).
         * Wypisz wartość private pola przez reflection.
         * W komentarzu wyjaśnij dlaczego to jest niebezpieczne i jak chronić (Security Manager, sealed).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_PackageScope {
        /*
         * 🧪 Zadanie 25:
         * Stwórz dwie package-private klasy: Node i LinkedList.
         * Node ma package-private pola value i next.
         * LinkedList ma metody publiczne: add, remove, toArray.
         * LinkedList używa bezpośrednio pól Node (bez getterów – same package).
         * Demonstracja "package as module" bez getterów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_Immutable {
        /*
         * 🧪 Zadanie 26:
         * Stwórz w pełni immutable klasę ImmutableUser:
         * - private final String username
         * - private final String email
         * - private final int age
         * - brak setterów
         * - wszystkie gettery
         * - metoda withEmail(String) zwraca nowy ImmutableUser z nowym emailem
         * Udowodnij immutability przez próbę modyfikacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_Decorator {
        /*
         * 🧪 Zadanie 27:
         * Wzorzec Decorator z modyfikatorami dostępu:
         * public interface TextService (process(String))
         * protected abstract class AbstractDecorator implements TextService
         * public class TrimDecorator extends AbstractDecorator
         * public class UpperDecorator extends AbstractDecorator
         * Konstruktory dekoratorów są package-private (można tylko wewnętrznie tworzyć).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_SecurityLayer {
        /*
         * 🧪 Zadanie 28:
         * Stwórz wielowarstwowy system bezpieczeństwa:
         * - public interface SecurityContext (getRole(), hasPermission(String))
         * - package-private class JwtSecurityContext implements SecurityContext
         * - public class SecurityFactory (tworzy SecurityContext)
         * - public class SecureService (wymaga SecurityContext z odpowiednią rolą)
         * Przetestuj dostęp z rolą "ADMIN" i "USER".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_ModulePattern {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj "Revealing Module Pattern" przez statyczną klasę wewnętrzną:
         * Outer: prywatne dane i metody pomocnicze.
         * public static class Module { ... } (tylko publiczne metody wynikowe).
         * Module deleguje do prywatnych metod Outer przez instancję.
         * Symuluj moduł kalkulatora statystyk.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_SOLID {
        /*
         * 🧪 Zadanie 30:
         * Pokaż wszystkie zasady SOLID przez dobór modyfikatorów:
         * S – Single Responsibility: klasa ma jedno zadanie
         * O – Open/Closed: open for extension (protected/abstract), closed for modification (final/private)
         * L – Liskov: podklasy nie zawężają widoczności (override nie może być bardziej restrictive)
         * I – Interface Segregation: małe interfejsy zamiast jednego dużego
         * D – Dependency Inversion: zależności przez interfejsy (public), nie klasy
         * Zaimplementuj mały system demonstrujący wszystkie 5 zasad.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
