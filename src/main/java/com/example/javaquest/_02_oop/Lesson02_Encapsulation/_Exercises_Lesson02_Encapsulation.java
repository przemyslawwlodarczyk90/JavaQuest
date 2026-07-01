package com.example.javaquest._02_oop.Lesson02_Encapsulation;

public class _Exercises_Lesson02_Encapsulation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PrivateFields {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Person z prywatnymi polami name (String) i age (int).
         * Dodaj gettery: getName(), getAge().
         * Wypisz dane obiektu używając getterów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_Setter {
        /*
         * 🧪 Zadanie 2:
         * Rozbuduj klasę Person z zadania 1 o setter setAge(int).
         * Warunek: wiek musi być >= 0. Jeśli nie – ignoruj zmianę.
         * Przetestuj ustawienie: 25 (OK), -5 (odrzucone), 0 (OK).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_BooleanGetter {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę LightBulb z prywatnym polem boolean isOn.
         * Dodaj metody: turnOn(), turnOff(), boolean isOn() (getter dla boolean).
         * Wypisz stan żarówki po każdej operacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_ReadOnly {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę ImmutableId z prywatnym polem final int id.
         * Pole jest ustawiane TYLKO w konstruktorze.
         * Dodaj getter getId(). Brak settera.
         * Wypisz id kilku obiektów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_Validation {
        /*
         * 🧪 Zadanie 5:
         * Stwórz klasę Product z polami name (String) i price (double).
         * Setter setPrice waliduje: cena >= 0. Jeśli nie – ustaw 0.
         * Setter setName waliduje: nie może być null ani pusty.
         * Przetestuj przypadki brzegowe.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_BankAccount {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę BankAccount z prywatnym polem balance (double).
         * Metody: deposit(double) – tylko > 0, withdraw(double) – tylko jeśli balance >= amount.
         * Getter getBalance().
         * Przetestuj: wpłać 100, wypłać 30, wypłać 200 (nieudane).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_ComputedGetter {
        /*
         * 🧪 Zadanie 7:
         * Stwórz klasę Circle z prywatnym polem radius (double).
         * Gettery: getRadius(), getArea(), getPerimeter() – obliczają i zwracają.
         * Brak osobnych pól area i perimeter – tylko obliczenia w getterach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_NameValidation {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę Username z polem username (String).
         * Setter setUsername: musi mieć 3-20 znaków, tylko litery i cyfry.
         * Użyj String.matches("[a-zA-Z0-9]{3,20}").
         * Przetestuj: "jan123" (OK), "ab" (za krótki), "jan kowalski" (spacja – błąd).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_Temperature {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę Thermometer z prywatnym polem celsius (double).
         * Getter getCelsius().
         * Getter getFahrenheit() – przelicza i zwraca.
         * Setter setCelsius waliduje: >= -273.15.
         * Przetestuj dla: 0, 100, -300 (odrzucone).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_Encapsulated {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę Rectangle z prywatnymi polami width i height (double).
         * Settery walidują: > 0.
         * Gettery: getWidth(), getHeight(), getArea(), getPerimeter(), isSquare().
         * Przetestuj prostokąt 5x5 i 4x6.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_Invariant {
        /*
         * 🧪 Zadanie 11:
         * Stwórz klasę DateRange z polami startDay i endDay (int, 1-365).
         * Warunek: startDay <= endDay. Setter powinien odrzucić niepoprawny zakres.
         * Getter getDuration() zwraca endDay - startDay.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_CopyGetter {
        /*
         * 🧪 Zadanie 12:
         * Stwórz klasę SafeData z prywatnym polem int[] values.
         * Getter getValues() zwraca KOPIĘ tablicy (nie oryginał).
         * Udowodnij, że modyfikacja kopi nie zmienia oryginału.
         * (Ochrona przed wyciekiem referencji.)
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_Password {
        /*
         * 🧪 Zadanie 13:
         * Stwórz klasę SecurePassword z prywatnym polem hash (int).
         * Konstruktor przyjmuje plaintext i oblicza hash (String.hashCode()).
         * Metoda verify(String input) sprawdza czy input.hashCode() == hash.
         * BRAK gettera dla hash ani plaintext!
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_Bounded {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę ProgressBar z polem value (int, 0-100).
         * Setter setValue(int): jeśli < 0, ustaw 0; jeśli > 100, ustaw 100.
         * Metoda increment(int amount) dodaje do value (z ograniczeniem do 100).
         * Wypisz pasek postępu jako: [###-------] (# = wypełnione, - = puste).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_Lazy {
        /*
         * 🧪 Zadanie 15:
         * Stwórz klasę ExpensiveData z polem private String cachedResult.
         * Getter getResult() oblicza wynik TYLKO przy pierwszym wywołaniu
         * (lazy initialization – jeśli cachedResult == null, oblicz i zapamiętaj).
         * Symuluj "obliczenie" przez wypisanie "Obliczam...".
         * Drugie wywołanie getResult() nie powinno ponownie drukować "Obliczam...".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_MultipleSetters {
        /*
         * 🧪 Zadanie 16:
         * Stwórz klasę Address z polami: street, city, zipCode, country (String).
         * Każdy setter waliduje czy wartość nie jest null/pusta.
         * Dodaj metodę toString() zwracającą pełny adres.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_ObservableField {
        /*
         * 🧪 Zadanie 17:
         * Stwórz klasę ObservableInt z polem value (int).
         * Setter setValue() wypisuje "Zmiana: [stara] → [nowa]" gdy wartość się zmienia.
         * Jeśli nowa == stara, wypisz "Brak zmiany".
         * Przetestuj kilka ustawień.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Audit {
        /*
         * 🧪 Zadanie 18:
         * Stwórz klasę AuditedValue z polami: value (double), changeCount (int).
         * Każda zmiana przez setter zwiększa changeCount.
         * Gettery: getValue(), getChangeCount().
         * Po 5 zmianach wypisz: "Zmieniono X razy. Obecna wartość: Y".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_Stack {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasę SafeStack z prywatnym polem int[] data i int top.
         * Metody push(int) i pop() z walidacją (nie push gdy pełny, nie pop gdy pusty).
         * Getter peek() bez usuwania elementu.
         * Przetestuj pełny cykl push/pop.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Versioned {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę VersionedDocument z polami: content (String) i version (int).
         * Setter setContent() zwiększa version przy każdej zmianie.
         * Konstruktor ustawia version=1.
         * Wypisz historię zmian: "Wersja X: [fragment contentu]".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImmutableValue {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj w pełni niezmienną (immutable) klasę Money:
         * - private final long cents
         * - private final String currency
         * - brak setterów
         * - metody add/subtract zwracają nowy obiekt Money
         * - metoda toString: "12.50 PLN"
         * Przetestuj łańcuch: new Money(100, "PLN").add(Money(50, "PLN")).subtract(Money(30, "PLN")).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_DefensiveCopy {
        /*
         * 🧪 Zadanie 22:
         * Stwórz klasę DateContainer przechowującą datę jako int[] {year, month, day}.
         * Konstruktor powinien zapisać KOPIĘ tablicy (defensive copy).
         * Getter powinien zwrócić KOPIĘ (nie oryginał).
         * Udowodnij że zewnętrzna modyfikacja tablicy nie wpływa na DateContainer.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Builder {
        /*
         * 🧪 Zadanie 23:
         * Stwórz klasę ServerConfig z prywatnymi polami:
         * host (String), port (int), maxConnections (int), timeout (int).
         * Prywatny konstruktor.
         * Dodaj statyczną klasę wewnętrzną Builder z metodami fluent.
         * Zbuduj konfigurację: host=localhost, port=8080, maxConnections=100, timeout=5000.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_Cache {
        /*
         * 🧪 Zadanie 24:
         * Stwórz klasę SimpleCache przechowującą pary klucz-wartość (String[]).
         * Metody: put(String key, String value), get(String key) → String lub null.
         * Pojemność maks: 5 wpisów. Przy przepełnieniu odrzuć nowe.
         * Wszystkie pola prywatne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_StateMachine {
        /*
         * 🧪 Zadanie 25:
         * Stwórz klasę TrafficLight z prywatnym polem state (String).
         * Stany: "RED" → "GREEN" → "YELLOW" → "RED" (cykl).
         * Metoda next() przesuwa do kolejnego stanu.
         * Getter getState().
         * Przetestuj 6 przejść.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_BoundedList {
        /*
         * 🧪 Zadanie 26:
         * Stwórz klasę BoundedList z prywatnym polem String[] items i int count.
         * Konstruktor przyjmuje capacity (rozmiar maksymalny).
         * Metody: add(String) → boolean (false jeśli pełna), remove(int index), get(int index).
         * Przetestuj: dodaj 3 elementy, usuń środkowy, dodaj nowy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_Encapsulated2D {
        /*
         * 🧪 Zadanie 27:
         * Stwórz klasę Grid z prywatnym polem int[][] cells.
         * Konstruktor: Grid(int rows, int cols) inicjuje siatkę zerami.
         * Metody: set(int row, int col, int value) z walidacją indeksów,
         *         get(int row, int col), void print().
         * Przetestuj siatką 3x3.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Fluent {
        /*
         * 🧪 Zadanie 28:
         * Stwórz klasę QueryBuilder z prywatną StringBuilder query.
         * Metody fluent: from(String table), where(String condition),
         * orderBy(String column), limit(int n), String build().
         * Zbuduj zapytanie: from("users").where("age > 18").orderBy("name").limit(10).build().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_Validator {
        /*
         * 🧪 Zadanie 29:
         * Stwórz klasę UserRegistration z polami: username, email, password (String).
         * Gettery bez setterów – dane tylko przez konstruktor.
         * Konstruktor waliduje:
         * - username: 3-20 znaków alfanumerycznych
         * - email: zawiera "@" i "."
         * - password: min 8 znaków, ma cyfrę i wielką literę
         * Rzuć IllegalArgumentException z opisem błędu przy naruszeniu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_EncapsulatedHistory {
        /*
         * 🧪 Zadanie 30:
         * Stwórz klasę PriceHistory z prywatną tablicą double[] prices (max 10 wpisów).
         * Metody: addPrice(double), double average(), double max(), double min(),
         *         double[] getHistory() – kopia tablicy, void printTrend() (↑ lub ↓ między kolejnymi).
         * Dodaj 7 cen i wypisz analizę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
