package com.example.javaquest._03_collections.Lesson14_Optional;

public class _Exercises_Lesson14_Optional {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_OptionalOf {
        /*
         * 🧪 Zadanie 1:
         * Stwórz Optional<String> przez Optional.of("Java").
         * Wypisz isPresent() oraz wartość przez get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OptionalEmpty {
        /*
         * 🧪 Zadanie 2:
         * Stwórz pusty Optional<String> przez Optional.empty().
         * Wypisz isPresent() i isEmpty().
         * Spróbuj wywołać get() w try/catch i wypisz komunikat
         * NoSuchElementException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OptionalOfNullable {
        /*
         * 🧪 Zadanie 3:
         * Zmienna String a = "wartość", String b = null.
         * Owiń obie w Optional.ofNullable(...) i wypisz oba obiekty (toString).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OrElseDefault {
        /*
         * 🧪 Zadanie 4:
         * Optional<String> empty = Optional.empty().
         * Użyj orElse("Wartość domyślna") i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_OrElseGetSupplier {
        /*
         * 🧪 Zadanie 5:
         * Optional<Integer> empty = Optional.empty().
         * Użyj orElseGet(() -> 100) i wypisz wynik.
         * Porównaj (komentarzem) z orElse(100) – kiedy Supplier faktycznie się wykonuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_OrElseThrow {
        /*
         * 🧪 Zadanie 6:
         * Optional<String> empty = Optional.empty().
         * Użyj orElseThrow(() -> new IllegalStateException("Brak danych")).
         * Złap wyjątek w try/catch i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IfPresentBasic {
        /*
         * 🧪 Zadanie 7:
         * Optional<String> present = Optional.of("kot").
         * Użyj ifPresent(...) żeby wypisać wartość zamienioną na wielkie litery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IfPresentOrElse {
        /*
         * 🧪 Zadanie 8:
         * Dla Optional<Integer> pełnego (np. of(5)) i pustego (empty())
         * użyj ifPresentOrElse żeby wypisać wartość albo "Brak wartości".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MapTransform {
        /*
         * 🧪 Zadanie 9:
         * Optional<String> name = Optional.of("  ala  ").
         * Zastosuj map(String::trim).map(String::toUpperCase).
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FilterBasic {
        /*
         * 🧪 Zadanie 10:
         * Optional<Integer> num = Optional.of(15).
         * Zastosuj filter(n -> n > 10) i wypisz przez ifPresent.
         * Zastosuj filter(n -> n > 100) i wypisz (powinno być puste –
         * użyj orElse(-1) do sprawdzenia).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SafeDivision {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę safeDivide(int a, int b) zwracającą Optional<Integer>
         * (Optional.empty() gdy b == 0, inaczej Optional.of(a / b)).
         * Przetestuj z (10, 2) i (10, 0), wypisz wyniki przez orElse(-1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FindInListOptional {
        /*
         * 🧪 Zadanie 12:
         * List<String> names = {"Ala","Bartłomiej","Cez","Dawid"}.
         * Znajdź pierwsze imię dłuższe niż 6 znaków przez
         * stream().filter(...).findFirst() (zwraca Optional<String>).
         * Wypisz wynik przez orElse("Brak").
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ChainedMapNested {
        /*
         * 🧪 Zadanie 13:
         * Klasa User(name, Address address), klasa Address(city).
         * Dla Optional<User> z pełnym adresem i dla User z address = null,
         * użyj map(User::getAddress).map(Address::getCity).orElse("Nieznane")
         * i wypisz oba wyniki bez rzucenia NPE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_OrFallbackChain {
        /*
         * 🧪 Zadanie 14:
         * Optional<String> primary = empty(), secondary = empty(),
         * tertiary = Optional.of("Backup").
         * Użyj łańcucha or(...).or(...) żeby uzyskać finalną wartość.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_OptionalStreamCount {
        /*
         * 🧪 Zadanie 15:
         * Optional<String> present = Optional.of("x"), empty = Optional.empty().
         * Użyj present.stream().count() i empty.stream().count() (Java 9+).
         * Wypisz obie liczby (powinno być 1 i 0) oraz ich sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ValidateEmail {
        /*
         * 🧪 Zadanie 16:
         * Optional<String> email = Optional.of("jan@example.com").
         * Użyj filter(e -> e.contains("@") && e.contains(".")).
         * ifPresentOrElse wypisz "Poprawny email" albo "Niepoprawny email".
         * Przetestuj też z "janexample" (bez @).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MapToDifferentType {
        /*
         * 🧪 Zadanie 17:
         * Optional<String> numberStr = Optional.of("42").
         * Użyj map(Integer::parseInt) żeby uzyskać Optional<Integer>,
         * a potem map(n -> n * 2). Wypisz wynik przez orElse(0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OrElseGetVsOrElse {
        /*
         * 🧪 Zadanie 18:
         * Optional<String> present = Optional.of("gotowe").
         * Napisz metodę obliczDomyslna() która wypisuje "Obliczam domyślną..."
         * i zwraca "domyślna".
         * Wywołaj present.orElse(obliczDomyslna()) oraz
         * present.orElseGet(() -> obliczDomyslna()) i zaobserwuj (w konsoli)
         * różnicę – orElse zawsze wywołuje argument, orElseGet tylko gdy trzeba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_OptionalWithCollectionLookup {
        /*
         * 🧪 Zadanie 19:
         * Map<String,Integer> inventory = {"jabłko":10,"banan":5,"gruszka":0}.
         * Owiń inventory.get("kiwi") w Optional.ofNullable i użyj orElse(0),
         * żeby bezpiecznie obsłużyć brakujący klucz. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ChainFilterMapOrElseThrow {
        /*
         * 🧪 Zadanie 20:
         * Optional<Integer> age = Optional.of(15).
         * Użyj filter(a -> a >= 18).orElseThrow(() ->
         * new IllegalArgumentException("Niepełnoletni")).
         * Złap wyjątek i wypisz komunikat. Przetestuj też z age = 25 (bez wyjątku).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FindUserById {
        /*
         * 🧪 Zadanie 21:
         * Map<Integer,String> users = {1:"Anna", 2:"Bartek", 3:"Celina"}.
         * Napisz findUserById(int id) zwracające Optional<String>.
         * Dla id z {1, 5, 3} wypisz przez ifPresentOrElse "Znaleziono: X"
         * albo "Użytkownik o id X nie istnieje".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NestedOptionalCompanyCeo {
        /*
         * 🧪 Zadanie 22:
         * Klasy Company(name, Optional<Ceo> ceo), Ceo(name).
         * Dla spółki z CEO i spółki bez CEO (Optional.empty()), bezpiecznie
         * wyciągnij imię CEO łańcuchem flatMap/map, z domyślną wartością
         * "Brak CEO" gdy nie istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_OptionalInStreamPipeline {
        /*
         * 🧪 Zadanie 23:
         * List<String> inputs = {"10","abc","20","xyz","5"}.
         * Napisz parseIntSafe(String s) zwracające Optional<Integer>
         * (empty gdy nie da się sparsować).
         * Użyj stream().map(...).flatMap(Optional::stream) żeby zebrać
         * tylko poprawne liczby i policzyć ich sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FirstValidConfig {
        /*
         * 🧪 Zadanie 24:
         * 3 źródła konfiguracji jako Supplier<Optional<String>>: z "env" (empty),
         * z "pliku" (empty), z "domyślnej" (Optional.of("default-value")).
         * Połącz je łańcuchem or(...) żeby uzyskać pierwszą dostępną wartość.
         * Wypisz finalną konfigurację.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_OptionalEqualsAndHashcode {
        /*
         * 🧪 Zadanie 25:
         * Stwórz dwa niezależne obiekty Optional.of("test").
         * Porównaj je przez equals() (powinno być true).
         * Dodaj oba do HashSet<Optional<String>> i sprawdź rozmiar zbioru
         * (powinien wynosić 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BankAccountWithdraw {
        /*
         * 🧪 Zadanie 26:
         * Klasa Account(balance) z metodą withdraw(double amount) zwracającą
         * Optional<Account> (nowe konto z pomniejszonym saldem, albo
         * Optional.empty() gdy saldo byłoby ujemne).
         * Wykonaj łańcuch 3 wypłat po kolei przez flatMap, zaczynając od
         * konta z saldem 100 (np. -30, -50, -40) i wypisz wynik końcowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_OptionalVsNullPerformance {
        /*
         * 🧪 Zadanie 27:
         * Zamodeluj strukturę Firma -> Optional<Dzial> -> Optional<Kierownik>
         * -> Optional<String> imie, 3 poziomy zagnieżdżenia.
         * Przetestuj 3 warianty danych: pełne dane, brak Dzial, brak Kierownik.
         * Napisz bezpieczne wyciąganie imienia łańcuchem flatMap/map/orElse
         * bez żadnego jawnego "if (x != null)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GroupedLookupWithOptional {
        /*
         * 🧪 Zadanie 28:
         * Map<String, List<Integer>> groupScores = {"A":{80,90,70},"B":{},
         * "C":{60}} (grupa "D" nie istnieje w mapie).
         * Napisz getAverageScore(String group) zwracającą Optional<Double>
         * (empty gdy grupa nie istnieje LUB lista jest pusta).
         * Przetestuj dla "A", "B" i "D".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RetryWithOptional {
        /*
         * 🧪 Zadanie 29:
         * Tablica 3 z góry ustawionych wyników operacji: Optional.empty(),
         * Optional.empty(), Optional.of("Sukces za 3 razem").
         * Zaimplementuj mechanizm retry, który próbuje kolejnych elementów
         * (or(...) w pętli lub łańcuchu) i na końcu orElseThrow gdy wszystkie
         * zawiodą. Wypisz finalny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_OptionalReduceCombination {
        /*
         * 🧪 Zadanie 30:
         * List<Optional<Integer>> optionalNumbers = {of(5), empty(), of(10),
         * empty(), of(3)}.
         * Użyj stream().flatMap(Optional::stream) żeby wyciągnąć tylko
         * obecne wartości, policz ich sumę przez reduce.
         * Osobno policz ile elementów listy było puste (empty()).
         */
        public static void main(String[] args) { }
    }
}
