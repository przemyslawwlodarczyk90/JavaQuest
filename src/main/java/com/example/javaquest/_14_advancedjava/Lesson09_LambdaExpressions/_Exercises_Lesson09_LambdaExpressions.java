package com.example.javaquest._14_advancedjava.Lesson09_LambdaExpressions;

public class _Exercises_Lesson09_LambdaExpressions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SingleParamNoParens {
        /*
         * 🧪 Zadanie 1:
         * Utwórz zmienną typu java.util.function.Function<Integer, Integer>
         * o nazwie square, przypisując lambdę BEZ nawiasów wokół
         * pojedynczego parametru (x -> x * x). Wypisz square.apply(9)
         * (spodziewane 81).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MultiParamWithParens {
        /*
         * 🧪 Zadanie 2:
         * Utwórz zmienną typu java.util.function.BiFunction<Integer,
         * Integer, Integer> o nazwie sum, przypisując lambdę (a, b) -> a +
         * b. Wypisz sum.apply(15, 27) (spodziewane 42).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ZeroParamLambda {
        /*
         * 🧪 Zadanie 3:
         * Utwórz zmienną typu java.util.function.Supplier<String> o
         * nazwie greeting, przypisując lambdę () -> "Witaj w Javie!".
         * Wypisz wynik greeting.get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplicitTypeSingleParam {
        /*
         * 🧪 Zadanie 4:
         * Utwórz zmienną typu java.util.function.IntUnaryOperator o
         * nazwie triple, przypisując lambdę z JAWNYM typem parametru:
         * (int x) -> x * 3. Wypisz triple.applyAsInt(14) (spodziewane 42).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExpressionBodyParity {
        /*
         * 🧪 Zadanie 5:
         * Utwórz Function<Integer, String> o nazwie parity, ciałem
         * WYRAŻENIOWYM (operator trójargumentowy ?:), zwracającą
         * "parzysta" lub "nieparzysta". Sprawdź dla liczb 10 i 7.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BlockBodyMultiStatement {
        /*
         * 🧪 Zadanie 6:
         * Utwórz Function<Integer, String> o nazwie describe, z ciałem
         * BLOKOWYM ({}), które oblicza DWIE lokalne zmienne (czy liczba
         * jest dodatnia i czy jest parzysta) i zwraca (przez jawny
         * "return") połączony opis, np. "dodatnia, parzysta". Sprawdź dla
         * -8 i 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CaptureEffectivelyFinalLocal {
        /*
         * 🧪 Zadanie 7:
         * Zadeklaruj lokalną zmienną int limit = 100 (nigdy więcej nie
         * przypisywaną - effectively final). Utwórz Runnable, który w
         * lambdzie wypisuje "Limit wynosi: " + limit. Wywołaj run().
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IllegalMixedParamTypesInComment {
        /*
         * 🧪 Zadanie 8:
         * W KOMENTARZU (kod NIE ma się skompilować, więc nie pisz go jako
         * realny kod) zapisz próbę utworzenia lambdy BiFunction<Integer,
         * Integer, Integer>, w której JEDEN parametr ma jawny typ (int a),
         * a drugi nie (b) - czyli (int a, b) -> a + b. Zapisz w komentarzu,
         * dlaczego to się nie skompiluje (reguła "wszystko albo nic" dla
         * typów parametrów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EffectivelyFinalViolationInComment {
        /*
         * 🧪 Zadanie 9:
         * W KOMENTARZU zapisz kod: lokalną zmienną int x = 5, lambdę
         * Runnable wypisującą x, a NASTĘPNIE (po utworzeniu lambdy)
         * ponowne przypisanie x = 10. Zapisz dokładny komunikat błędu
         * kompilacji, jaki by się pojawił, i wyjaśnij POWÓD tej reguły
         * (lambda może "przeżyć" metodę, w której powstała).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConcatenationWithSeparator {
        /*
         * 🧪 Zadanie 10:
         * Utwórz BiFunction<String, String, String> o nazwie joinWithDash,
         * łączącą dwa Stringi myślnikiem (np. "Ala" i "Ola" -> "Ala-Ola").
         * Sprawdź dla trzech różnych par argumentów.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SnapshotVariableInClassicForLoop {
        /*
         * 🧪 Zadanie 11:
         * W klasycznej pętli for (int i = 0; i < 5; i++) utwórz WEWNĄTRZ
         * pętli lokalną zmienną "final int snapshot = i" i lambdę
         * Runnable wypisującą snapshot * snapshot. Zbierz wszystkie 5
         * Runnable-i do List<Runnable>, a PO zakończeniu pętli wykonaj je
         * wszystkie (spodziewane 0, 1, 4, 9, 16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MutableArrayCaptureTrick {
        /*
         * 🧪 Zadanie 12:
         * Utwórz int[] counter = {0}. Utwórz Runnable increment
         * zwiększający counter[0] o 1. Wywołaj increment.run() pięć razy w
         * pętli i wypisz finalną wartość counter[0] (spodziewane 5).
         * Wyjaśnij w komentarzu, dlaczego samo "int counter = 0" by nie
         * zadziałało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ComparatorLambdaSortByLength {
        /*
         * 🧪 Zadanie 13:
         * Utwórz java.util.List<String> z elementami "banan", "kiwi",
         * "gruszka", "fig". Posortuj listę metodą list.sort(...) z lambdą
         * Comparator<String> porównującą po długości (a.length() -
         * b.length()). Wypisz posortowaną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SupplierCreatesFreshInstanceEachCall {
        /*
         * 🧪 Zadanie 14:
         * Utwórz Supplier<StringBuilder> o nazwie freshBuilder, zwracający
         * ZA KAŻDYM RAZEM nowy obiekt new StringBuilder(). Wywołaj get()
         * dwukrotnie, dopisz różny tekst do każdego wyniku i pokaż (np.
         * przez ==), że to DWA różne obiekty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CurriedAddFunction {
        /*
         * 🧪 Zadanie 15:
         * Utwórz Function<Integer, Function<Integer, Integer>> o nazwie
         * curriedAdd, gdzie zewnętrzna lambda przyjmuje "a" i zwraca
         * WEWNĘTRZNĄ lambdę przyjmującą "b" i zwracającą a + b. Wywołaj
         * curriedAdd.apply(10).apply(5) (spodziewane 15).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ThisInsideLambdaRefersToEnclosing {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj klasę Box z polem String label ustawianym w
         * konstruktorze oraz metodą Runnable asLambda(), zwracającą
         * lambdę wypisującą "this.getClass() = " +
         * this.getClass().getSimpleName() + ", label = " + this.label.
         * Utwórz instancję Box i wywołaj asLambda().run() - sprawdź, że
         * this.getClass() to Box (NIE żadna wewnętrzna klasa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ThisInsideAnonymousClassRefersToItself {
        /*
         * 🧪 Zadanie 17:
         * Do klasy Box z Zadania 16 dodaj metodę Runnable
         * asAnonymousClass(), zwracającą klasę anonimową implementującą
         * Runnable, której run() wypisuje this.getClass().getSimpleName()
         * (będzie zawierać "$1") oraz Box.this.label (bo zwykłe this.label
         * wskazywałoby na pole klasy anonimowej, którego tu nie ma).
         * Porównaj wynik z Zadaniem 16 w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ForEachLoopSafeCapture {
        /*
         * 🧪 Zadanie 18:
         * Utwórz List<String> imion (min. 4 elementy). W pętli for-each
         * (BEZ dodatkowej zmiennej-kopii) utwórz dla każdego imienia
         * lambdę Runnable wypisującą to imię, zbierz wszystkie do
         * List<Runnable>, a na końcu wykonaj wszystkie. Wyjaśnij w
         * komentarzu, dlaczego for-each NIE wymaga tu ręcznego "snapshotu"
         * (w przeciwieństwie do klasycznej pętli for z Zadania 11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ClassicForLoopBugInCommentPlusFix {
        /*
         * 🧪 Zadanie 19:
         * W KOMENTARZU zapisz "zepsutą" wersję: klasyczna pętla for (int i
         * = 0; ...), w której lambda BEZPOŚREDNIO używa "i" bez kopiowania
         * go do nowej zmiennej - zapisz dokładny komunikat błędu
         * kompilacji. NASTĘPNIE, jako REALNY, działający kod, napisz
         * POPRAWIONĄ wersję (z lokalną kopią) realizującą to samo zadanie
         * (np. wypisanie kwadratów 0-4 z opóźnionym wykonaniem przez
         * listę Runnable).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ManualTwoStepCompositionInBlockBody {
        /*
         * 🧪 Zadanie 20:
         * Utwórz Function<Integer, Integer> o nazwie addThenSquare, z
         * ciałem BLOKOWYM, które NAJPIERW oblicza pośredni wynik (x + 3),
         * zapisuje go w lokalnej zmiennej, a POTEM zwraca jego kwadrat -
         * bez używania żadnej gotowej metody kompozycji (poznamy je w
         * Lesson11). Sprawdź dla wejścia 2 (spodziewane 25).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CustomThreeParamInterfaceExplicitTypes {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj interfejs funkcyjny Trio<A, B, C, R> z metodą R
         * combine(A a, B b, C c). Zaimplementuj lambdą z JAWNYMI typami
         * WSZYSTKICH trzech parametrów (nawiasy i tak są wymagane przy
         * wielu parametrach, ale tu dodatkowo wypisz jawne typy), łączącą
         * String, Integer i Boolean w jeden opisowy String. Przetestuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LambdaOutlivesCreatingMethod {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę statyczną Runnable createDeferredTask(String
         * message), która TWORZY lokalną zmienną kopiującą "message" i
         * ZWRACA lambdę Runnable wypisującą tę wiadomość - lambda jest
         * WYKONYWANA dopiero PÓŹNIEJ, poza metodą, w której powstała.
         * Zbierz 3 takie zadania (z różnymi wiadomościami) do
         * List<Runnable> i wykonaj je dopiero na końcu main(), pokazując,
         * że przechwycone wartości "przeżyły" swoje metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_InstanceFieldMutatedAcrossManyLambdaCalls {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj klasę Accumulator z polem int total = 0 oraz metodą
         * java.util.function.IntUnaryOperator adder(), zwracającą lambdę,
         * która za każdym wywołaniem DODAJE argument do this.total i
         * zwraca nowe total (pole instancji - NIE lokalna zmienna, więc
         * brak ograniczeń effectively final). Wywołaj zwróconą lambdę
         * trzykrotnie z różnymi argumentami i wypisuj total po każdym
         * wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThreeWaysToImplementRunnableCompareThis {
        /*
         * 🧪 Zadanie 24:
         * Rozbuduj ideę z Zadań 16-17: w klasie Outer (z polem String tag)
         * napisz TRZY metody zwracające Runnable realizujące to samo
         * zadanie (wypisanie this.getClass().getSimpleName()): asLambda()
         * (lambda), asAnonymousClass() (klasa anonimowa) oraz
         * asStaticNestedHelperInstance() (osobna, ZWYKŁA klasa
         * zaimplementowana jako static nested class implementująca
         * Runnable, tworzona przez konstruktor). Wywołaj wszystkie trzy i
         * porównaj w komentarzu wypisane nazwy klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WhyLambdaCannotHaveOwnFields {
        /*
         * 🧪 Zadanie 25:
         * W KOMENTARZU wyjaśnij, dlaczego lambda NIE MOŻE mieć własnych
         * pól instancji (nie ma własnego "this" ani deklaracji ciała
         * klasy - tylko lista parametrów i ciało metody). Następnie, jako
         * REALNY kod, zaimplementuj "obejście" - java.util.function.
         * Supplier<Integer> licznikStanow, który przy KAŻDYM wywołaniu
         * get() zwraca kolejną wartość (0, 1, 2, ...), korzystając z
         * przechwyconej tablicy int[] jednoelementowej (workaround z
         * Zadania 12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ManualPipelineOverListOfFunctions {
        /*
         * 🧪 Zadanie 26:
         * Utwórz List<Function<Integer, Integer>> zawierającą TRZY
         * transformacje (dodaj 5, pomnóż razy 2, odejmij 3) jako lambdy z
         * ciałem wyrażeniowym. Napisz pętlę, która RĘCZNIE (bez andThen z
         * Lesson11) stosuje wszystkie transformacje po kolei do wejścia 4
         * i wypisuje wynik po KAŻDYM kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SupplierOfRunnableFactory {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę statyczną Supplier<Runnable> taskFactory(String
         * label, int repeatCount), zwracającą lambdę (Supplier), która z
         * kolei ZWRACA NOWY Runnable wypisujący label repeatCount razy.
         * Wywołaj taskFactory(...).get().run() dla DWÓCH różnych
         * konfiguracji (różne label i repeatCount) i pokaż, że to
         * niezależne domknięcia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_NestedThisComparisonLambdaVsOuterThisAnonymous {
        /*
         * 🧪 Zadanie 28:
         * W klasie Reporter (pole String reportName) napisz metodę
         * buildBoth(), która tworzy DWA Runnable robiące to samo (wypisują
         * "Raport: " + reportName) - JEDEN jako lambda (this.reportName
         * albo po prostu reportName), DRUGI jako klasa anonimowa
         * (Reporter.this.reportName, bo zwykłe "this" w klasie anonimowej
         * wskazuje na nią samą). Wykonaj oba i zwróć w komentarzu, który
         * zapis jest krótszy i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ReverseOrderExecutionOfCapturedLoopLambdas {
        /*
         * 🧪 Zadanie 29:
         * W klasycznej pętli for (int i = 0; i < 6; i++) utwórz - z
         * poprawną techniką "snapshot" z Zadania 11 - lambdy Runnable
         * wypisujące "krok " + snapshot, zbierz je do List<Runnable>, a
         * następnie wykonaj CAŁĄ listę w ODWRÓCONEJ kolejności (od
         * ostatniego do pierwszego elementu), pokazując, że każda lambda
         * "pamięta" WŁAŚCIWĄ, przypisaną jej wartość niezależnie od
         * kolejności wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CommandHistoryWithSharedMutableBoxState {
        /*
         * 🧪 Zadanie 30:
         * Zdefiniuj interfejs funkcyjny Command { void execute(); }.
         * Zdefiniuj prostą klasę pomocniczą Box<T> z jednym polem
         * publicznym T value. Utwórz Box<Integer> balance = new
         * Box<>(0) i zbuduj listę List<Command> z TRZEMA operacjami
         * (depozyt +100, wypłata -30, depozyt +50), każda jako lambda
         * modyfikująca balance.value. Wykonaj wszystkie polecenia po kolei,
         * wypisując balance.value po KAŻDYM z nich. W komentarzu wyjaśnij,
         * dlaczego zwykłe "int balance" (lokalna zmienna) NIE dałoby się
         * tu użyć bez opakowania w Box (reguła effectively final).
         */
        public static void main(String[] args) { }
    }
}
