package com.example.javaquest._14_advancedjava.Lesson12_Annotations;

public class _Exercises_Lesson12_Annotations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_OverrideToString {
        /*
         * 🧪 Zadanie 1:
         * Utwórz klasę Book z polami String title i String author. Nadpisz
         * metodę toString() (z jawnym @Override), zwracającą
         * "title by author". Utwórz instancję i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OverrideEqualsAndHashCode {
        /*
         * 🧪 Zadanie 2:
         * Utwórz klasę Point z polami int x, y. Nadpisz (z @Override)
         * equals(Object o) i hashCode() zgodnie z kontraktem (te same x, y
         * -> równe obiekty, ten sam hashCode). Sprawdź
         * new Point(1,2).equals(new Point(1,2)) (spodziewane true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MissingOverrideSilentBug {
        /*
         * 🧪 Zadanie 3:
         * Utwórz klasę Vehicle z metodą String describe() zwracającą "pojazd
         * ogólny". Utwórz podklasę Car z metodą describe() zawierającą
         * CELOWĄ literówkę w nazwie (np. "descirbe") i BEZ @Override.
         * Wywołaj describe() na zmiennej typu Vehicle wskazującej na obiekt
         * Car i w komentarzu wyjaśnij, dlaczego wypisze się "pojazd ogólny",
         * a nie tekst z Car.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixWithOverride {
        /*
         * 🧪 Zadanie 4:
         * Popraw kod z Zadania 3: dodaj @Override i napraw literówkę w
         * nazwie metody describe() w klasie Car. Wywołaj describe() na
         * zmiennej typu Vehicle wskazującej na obiekt Car i sprawdź, że
         * teraz zwraca tekst specyficzny dla Car.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DeprecatedMarkerOnly {
        /*
         * 🧪 Zadanie 5:
         * Utwórz klasę StringUtils z metodą statyczną oznaczoną SAMYM
         * @Deprecated (bez elementów) - String reverseOld(String s)
         * zwracającą odwrócony napis (np. przez new
         * StringBuilder(s).reverse()). Wywołaj ją dla "Java" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DeprecatedWithSinceAndForRemoval {
        /*
         * 🧪 Zadanie 6:
         * Rozbuduj StringUtils z Zadania 5 o drugą metodę
         * @Deprecated(since = "3.0", forRemoval = true)
         * String reverseVeryOld(String s) - taka sama logika. Dodaj też
         * komentarz Javadoc @deprecated wskazujący nowy zamiennik
         * reverseNew(String s) (String reverseNew(String s) - zaimplementuj
         * ją TEŻ, bez adnotacji). Wywołaj wszystkie trzy warianty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SuppressUncheckedCast {
        /*
         * 🧪 Zadanie 7:
         * Napisz metodę statyczną generyczną <T> T firstElement(java.util.List<?>
         * list), która rzutuje list.get(0) na (T) i zwraca - oznacz ją (albo
         * zmienną lokalną z rzutowaniem wewnątrz) @SuppressWarnings("unchecked").
         * Wywołaj dla java.util.List.of("a", "b", "c") jako List<String> i
         * wypisz pierwszy element.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SuppressDeprecationOnCall {
        /*
         * 🧪 Zadanie 8:
         * Wywołaj metodę reverseOld(String) ze StringUtils z Zadania 5
         * wewnątrz LOKALNEJ zmiennej oznaczonej @SuppressWarnings("deprecation"),
         * żeby wyciszyć ostrzeżenie kompilatora. Wypisz wynik dla "Kotlin".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MarkerVsSingleValueVsFull {
        /*
         * 🧪 Zadanie 9:
         * W komentarzu wypisz TRZY przykłady adnotacji poznanych w tej
         * lekcji - po jednym przykładzie dla każdej postaci: MARKER (bez
         * elementów), SINGLE-VALUE (jeden element "value" podany bez
         * nazwy) i FULL (kilka nazwanych elementów). Krótko uzasadnij
         * klasyfikację każdego przykładu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FunctionalInterfaceRecap {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj interfejs @FunctionalInterface Calculator z jedną
         * metodą int calculate(int a, int b). Zaimplementuj lambdą
         * (dodawanie) i wywołaj dla (5, 7). W komentarzu przypomnij (z
         * Lekcji 08), co by się stało, gdybyś dodał drugą metodę
         * abstrakcyjną do tego interfejsu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultipleSuppressWarnings {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę statyczną, która WEWNĄTRZ jednocześnie: używa
         * przestarzałej metody z Zadania 5 ORAZ wykonuje niebezpieczne
         * rzutowanie generyczne (jak w Zadaniu 7). Oznacz metodę
         * @SuppressWarnings({"unchecked", "deprecation"}) (dwie kategorie
         * naraz w tablicy) i sprawdź, że kompiluje się bez ostrzeżeń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_OverrideOnInterfaceDefaultMethod {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj interfejs Greetable z metodą default String greet()
         * zwracającą "Cześć!". Utwórz klasę PolishGreeter implements
         * Greetable, NADPISUJĄCĄ greet() (z @Override) na "Cześć, Polsko!".
         * Utwórz DRUGĄ klasę, która NIE nadpisuje greet() - porównaj wynik
         * obu wywołań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DeprecatedClassLevel {
        /*
         * 🧪 Zadanie 13:
         * Oznacz CAŁĄ klasę (nie pojedynczą metodę)
         * @Deprecated(since = "1.5", forRemoval = false) OldConfig z polem
         * String value i konstruktorem. Utwórz jej instancję gdzieś w
         * main() (spodziewaj się ostrzeżenia kompilatora dla samego użycia
         * konstruktora) i wypisz pole value.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_OverrideEqualsWithoutHashCodeExplain {
        /*
         * 🧪 Zadanie 14:
         * Utwórz klasę Coordinate z polami x, y - nadpisz TYLKO equals()
         * (z @Override), CELOWO pomijając hashCode(). Dodaj dwie równe wg
         * equals() instancje Coordinate do java.util.HashSet i wypisz
         * rozmiar zbioru. W komentarzu wyjaśnij niespodziewany wynik i
         * dlaczego kontrakt equals/hashCode jest ważny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SafeVarargsRecap {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę statyczną generyczną (final lub static, zgodnie z
         * regułami @SafeVarargs) @SafeVarargs <T> java.util.List<T>
         * listOf(T... elements) zwracającą java.util.Arrays.asList(elements).
         * Wywołaj dla trzech Stringów i wypisz wynik. W komentarzu wyjaśnij,
         * na jakie metody WOLNO nałożyć @SafeVarargs.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CustomExceptionWithOverrideGetMessage {
        /*
         * 🧪 Zadanie 16:
         * Utwórz własny wyjątek InsufficientFundsException extends
         * RuntimeException, NADPISUJĄCY (z @Override) getMessage() tak,
         * żeby zwracał sformatowany komunikat z polem double missingAmount
         * przekazanym w konstruktorze. Rzuć i złap ten wyjątek, wypisując
         * getMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SuppressRawtypes {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę statyczną, która tworzy SUROWĄ (bez parametru typu)
         * java.util.List, dodaje do niej element i oznacz ją
         * @SuppressWarnings("rawtypes") żeby wyciszyć ostrzeżenie o
         * surowym typie. Wypisz zawartość listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OverrideCompareTo {
        /*
         * 🧪 Zadanie 18:
         * Utwórz klasę Money implements Comparable<Money> z polem
         * BigDecimal amount, NADPISUJĄCĄ (z @Override) compareTo(Money
         * other). Utwórz listę trzech obiektów Money w losowej kolejności,
         * posortuj przez Collections.sort() i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DeprecatedInterfaceMethod {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj interfejs LegacyApi z metodą
         * @Deprecated(since = "2.0", forRemoval = true) void oldOperation()
         * ORAZ nową metodą void newOperation(). Zaimplementuj interfejs
         * klasą (implementującą OBIE metody - lambda by nie zadziałała, bo
         * są dwie metody abstrakcyjne) i wywołaj oldOperation() (oczekuj
         * ostrzeżenia kompilatora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AnnotationsOnParametersAndLocalVars {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę statyczną process(@SuppressWarnings("unused") String
         * unusedParam, int value), która ignoruje pierwszy parametr, a
         * zwraca value * 2. W komentarzu wyjaśnij, że @SuppressWarnings
         * może być nałożony na PARAMETR metody, nie tylko na całą metodę
         * czy zmienną lokalną - i podaj jeszcze jeden przykład takiego
         * "wąskiego" zastosowania z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DeprecationMigrationPath {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj klasę PaymentProcessor z DWIEMA metodami: starą
         * @Deprecated(since = "4.0", forRemoval = true)
         * void pay(double amount) oraz nową
         * void pay(java.math.BigDecimal amount, String currency). Napisz
         * Javadoc @deprecated przy starej metodzie wskazujący nową. W
         * main() wywołaj OBIE i w komentarzu opisz typową "ścieżkę
         * migracji" API (deprecation -> okres przejściowy -> usunięcie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_OverrideAcrossThreeLevelHierarchy {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj hierarchię trzech klas: Shape -> Polygon -> Triangle,
         * gdzie KAŻDA warstwa nadpisuje (z @Override) metodę double area()
         * i wywołuje super.area() TYLKO w komentarzu logującym (nie w
         * obliczeniach) żeby pokazać kolejność. Utwórz Triangle i wypisz
         * wynik area() (dowolna sensowna logika obliczeń, np. przez wzór
         * Herona albo stałe boki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SuppressWarningsScopeDemo {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę z DWIEMA zmiennymi lokalnymi obok siebie - JEDNĄ z
         * niebezpiecznym rzutowaniem generycznym OZNACZONĄ
         * @SuppressWarnings("unchecked"), DRUGĄ (bez adnotacji) z takim
         * samym rzutowaniem. W komentarzu wyjaśnij, dlaczego druga wciąż
         * generuje ostrzeżenie kompilatora mimo obecności pierwszej -
         * zasięg (scope) adnotacji jest ograniczony do elementu, na który
         * została nałożona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_EqualsHashCodeToStringFullTriad {
        /*
         * 🧪 Zadanie 24:
         * Utwórz klasę Employee (id, name, salary) nadpisującą (z
         * @Override) WSZYSTKIE TRZY metody z Object: equals(), hashCode()
         * i toString() - zgodnie z kontraktem (te same id -> równe,
         * spójny hashCode). Umieść kilka obiektów Employee w
         * java.util.HashSet, wypisz zbiór i policz elementy po dodaniu
         * duplikatu wg id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DeprecatedForRemovalCompilationWarningLevels {
        /*
         * 🧪 Zadanie 25:
         * W komentarzu porównaj różnicę między
         * @Deprecated(forRemoval = false) (domyślne - "przestarzałe, ale
         * zostanie") a @Deprecated(forRemoval = true) ("zostanie USUNIĘTE
         * w przyszłej wersji") pod kątem powagi ostrzeżenia kompilatora i
         * strategii biblioteki. Zaimplementuj DWIE metody z każdym
         * wariantem i wywołaj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_OverrideCloneWithDeepCopy {
        /*
         * 🧪 Zadanie 26:
         * Utwórz klasę MutablePoint implements Cloneable z polami int x, y,
         * NADPISUJĄCĄ (z @Override) protected Object clone() (rzutując
         * wynik super.clone() na MutablePoint, obsługując
         * CloneNotSupportedException). Sklonuj obiekt, zmodyfikuj klon i
         * wypisz OBA obiekty (oryginał nie powinien się zmienić).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SuppressWarningsOnClassVsMethod {
        /*
         * 🧪 Zadanie 27:
         * Utwórz klasę z DWIEMA metodami wykonującymi niebezpieczne
         * rzutowanie generyczne. Wypróbuj DWA podejścia: (a) osobno
         * oznacz KAŻDĄ metodę @SuppressWarnings("unchecked"), (b) oznacz
         * CAŁĄ KLASĘ @SuppressWarnings("unchecked") (adnotacja na klasę
         * wycisza dla WSZYSTKICH jej członków). W komentarzu przedyskutuj,
         * które podejście jest bezpieczniejsze/lepsze praktycznie i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FunctionalInterfaceWithGenericsAndSafeVarargs {
        /*
         * 🧪 Zadanie 28:
         * Połącz wiedzę z Lekcji 08 (funkcyjne interfejsy) i tej lekcji:
         * zdefiniuj @FunctionalInterface Aggregator<T> z metodą T
         * aggregate(java.util.List<T> values). Napisz OSOBNĄ metodę
         * statyczną @SafeVarargs <T> java.util.List<T> toList(T... values),
         * która buduje listę z varargów i przekazuje ją do Aggregator.
         * Zaimplementuj sumowanie Integerów i przetestuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DeprecatedApiWrapperAdapter {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj interfejs ModernLogger z metodą void log(String
         * message). Utwórz klasę @Deprecated(since = "1.0", forRemoval = true)
         * LegacyLogger z metodą void write(String msg) (System.out).
         * Napisz klasę-adapter LegacyLoggerAdapter implements ModernLogger,
         * OPAKOWUJĄCĄ instancję LegacyLogger (z @SuppressWarnings("deprecation")
         * TAM, gdzie wywołujesz write()) - w komentarzu wyjaśnij, dlaczego
         * to typowy wzorzec przy stopniowej migracji z przestarzałego API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_AuditAnnotationUsageAcrossSmallProject {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj MINI-hierarchię 3 klas (dowolna dziedzina, np.
         * system zamówień: Order, DiscountedOrder, ExpressOrder) z co
         * najmniej: DWOMA @Override (nadpisania metod), JEDNYM
         * @Deprecated(since=..., forRemoval=...) (stara metoda z Javadoc
         * @deprecated), JEDNYM @SuppressWarnings (dowolna kategoria) - i
         * zademonstruj działanie WSZYSTKICH klas w main(). Podsumuj w
         * komentarzu, gdzie w tej mini-hierarchii każda adnotacja pomogła
         * (kompilacja/czytelność/bezpieczeństwo).
         */
        public static void main(String[] args) { }
    }
}
