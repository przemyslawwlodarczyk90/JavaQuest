package com.example.javaquest._14_advancedjava.Lesson23_VarAndTypeInference;

public class _Exercises_Lesson23_VarAndTypeInference {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DeclareVarStringAndPrintType {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj zmienną `var imie = "Nowak";`, wypisz jej wartość oraz
         * wywnioskowany typ przez `imie.getClass().getSimpleName()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DeclareMultipleVarPrimitives {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj cztery zmienne przez `var`: int (np. 42), double (np.
         * 3.14), boolean (np. true) i char (np. 'A') - wypisz każdą z nich
         * wraz z jej wywnioskowanym typem (dla typów prostych wpisz typ
         * ręcznie w komentarzu obok println, bo prymitywy nie mają getClass()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VarInForEachOverIntArray {
        /*
         * 🧪 Zadanie 3:
         * Stwórz tablicę `int[] liczby = {2, 4, 6, 8, 10};`, użyj `var` w
         * pętli for-each do zsumowania wszystkich elementów, wypisz sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_VarAsLoopCounter {
        /*
         * 🧪 Zadanie 4:
         * Użyj `var` jako licznika w zwykłej pętli for (`for (var i = 0; ...)`)
         * do wypisania liczb od 1 do 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VarInTryWithResources {
        /*
         * 🧪 Zadanie 5:
         * Analogicznie do przykładu z teorii, użyj `var` do zadeklarowania
         * zasobu `BufferedReader` (opartego o `StringReader` z tekstem
         * "wiersz1\nwiersz2\nwiersz3") w try-with-resources i wypisz każdą
         * wczytaną linię.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VarArrayListSum {
        /*
         * 🧪 Zadanie 6:
         * Zadeklaruj `var lista = new ArrayList<Integer>();`, dodaj do niej
         * wartości 10, 20, 30, zsumuj je w pętli for-each i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListForbiddenVarPlacesInComments {
        /*
         * 🧪 Zadanie 7:
         * Bez kompilowania: w komentarzu napisz 3 przykłady kodu, w których
         * `var` jest ZABRONIONY (pole klasy, parametr metody, typ zwracany
         * metody) - dla każdego dopisz jednym zdaniem, dlaczego kompilator
         * tego zabrania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_VarHashMapEntrySetForEach {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj `var mapa = new HashMap<String, Integer>();`, dodaj 3
         * wpisy (np. owoc -> cena), a następnie iteruj po `mapa.entrySet()`
         * używając `var` w pętli for-each, wypisując każdy klucz i wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainNoInitializerError {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu wyjaśnij, dlaczego linia `var x;` (bez
         * inicjalizatora) nie kompiluje się - z czego dokładnie kompilator
         * miałby wywnioskować typ zmiennej x?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NullInitializerCastWorkaround {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu wyjaśnij, dlaczego `var y = null;` nie
         * kompiluje się, ale `var y = (String) null;` już tak. Następnie w
         * działającym kodzie zadeklaruj `var tekst = (String) null;` i
         * wypisz `tekst == null`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_VarTypeFixedOnceCommentedError {
        /*
         * 🧪 Zadanie 11:
         * Zadeklaruj `var tekst = "abc";`. W zakomentowanej linii kodu
         * napisz próbę `tekst = 42;` i w komentarzu wyjaśnij dokładny błąd
         * kompilacji, jaki by się pojawił - odwołaj się do faktu, że typ
         * `var` jest ustalany RAZ, w czasie kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VarDiamondWithoutExplicitTypeArgument {
        /*
         * 🧪 Zadanie 12:
         * Zadeklaruj `var listaObiektow = new ArrayList<>();` (diamond BEZ
         * jawnego typu). W komentarzu wyjaśnij, dlaczego wywnioskowany typ
         * elementów to `Object`, a następnie pokaż w kodzie, że można do tej
         * listy dodać zarówno String, jak i Integer bez błędu kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareInterfaceTypeVsVarConcreteType {
        /*
         * 🧪 Zadanie 13:
         * Napisz obie deklaracje: `List<String> l1 = new ArrayList<>();`
         * oraz `var l2 = new ArrayList<String>();`. W komentarzu wyjaśnij
         * różnicę na poziomie STATYCZNEGO typu zmiennej (interfejs List vs
         * konkretna klasa ArrayList) i kiedy ta różnica mogłaby mieć
         * znaczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_VarWithComplexReturnType {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę `static Map<String, List<Integer>> pogrupuj()`
         * zwracającą przykładową mapę grup liczb, przypisz jej wynik do
         * `var`, wypisz wynik. W komentarzu oceń, czy `var` poprawia tu
         * czytelność względem jawnego typu `Map<String, List<Integer>>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_VarInferredFromArithmeticExpression {
        /*
         * 🧪 Zadanie 15:
         * Zadeklaruj `var wynik = 5 / 2.0;` - wypisz wynik oraz w komentarzu
         * napisz, jaki typ został wywnioskowany i dlaczego (reguły promocji
         * typów w wyrażeniach arytmetycznych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_VarWithRecordInstance {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj prosty rekord `Osoba(String imie, int wiek)`, przypisz
         * nową instancję do `var osoba = new Osoba("Kasia", 25);`, wypisz ją.
         * W komentarzu potwierdź, że `var` wywnioskował konkretny typ
         * rekordu `Osoba`, a nie jakiś ogólny typ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_VarWithArrayLiteral {
        /*
         * 🧪 Zadanie 17:
         * Zadeklaruj `var tablica = new int[]{1, 2, 3, 4, 5};`, wypisz jej
         * długość (`tablica.length`) oraz wszystkie elementy w pętli for.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_VarHidingImportantTypeInformation {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę `static Object policzRabat(double cena, double
         * procent)` (celowo niejasny typ zwracany), przypisz jej wynik do
         * `var wynik = policzRabat(100, 10);`. W komentarzu zaproponuj
         * lepszą wersję - z jawnym, konkretnym typem zwracanym zamiast
         * Object oraz bez var przy wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_VarTwoResourcesInOneTryBlock {
        /*
         * 🧪 Zadanie 19:
         * W jednym bloku try-with-resources zadeklaruj DWA zasoby przez
         * `var` (dwa niezależne `BufferedReader` oparte o różne teksty) i
         * wypisz zawartość obu, każdy w osobnej pętli while.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_WriteUseAvoidVarChecklist {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: w komentarzu napisz listę 5 sytuacji, w których
         * WARTO użyć `var` oraz 5 sytuacji, w których NALEŻY go unikać -
         * oprzyj się na zasadzie czytelności z teorii lekcji ("czy typ
         * widać na pierwszy rzut oka").
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ExplainIdenticalBytecodeForVarAndExplicitType {
        /*
         * 🧪 Zadanie 21:
         * Napisz dwie metody statyczne zwracające `void` - jedną z jawnym
         * typem `ArrayList<String> lista = new ArrayList<>();`, drugą z
         * `var lista = new ArrayList<String>();`, obie robiące dokładnie to
         * samo (dodanie 2 elementów i wypisanie). W komentarzu wyjaśnij,
         * dlaczego kompilator generuje dla obu IDENTYCZNY bajtkod (var to
         * cukier składniowy kompilatora, a nie mechanizm działający w
         * runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_VarWithBoundedGenericMethodResult {
        /*
         * 🧪 Zadanie 22:
         * Napisz generyczną metodę statyczną `static <T extends Number> double
         * podwojna(T liczba)` zwracającą `liczba.doubleValue() * 2`, wywołaj
         * ją dla Integer i przypisz wynik do `var`. W komentarzu zapisz,
         * jaki typ zostanie wywnioskowany dla zmiennej var.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_GenericMethodReturningListAssignedToVar {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę generyczną `static <T> List<T> wrapInList(T value)`
         * zwracającą jednoelementową listę. Przypisz jej wynik (dla wartości
         * typu Integer) do `var`, wypisz wynik oraz typ pierwszego elementu
         * przez getClass().
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_VarWithStreamMethodChain {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj łańcuch metod Stream API:
         * `List.of(1, 2, 3, 4, 5).stream().filter(x -> x % 2 == 0).map(x ->
         * x * 10).toList()` i przypisz wynik do `var`. Wypisz wynik. W
         * komentarzu oceń, czy `var` jest tu uzasadniony (jak długi byłby
         * jawny typ pośredni w tym łańcuchu bez var).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_VarLambdaParametersObservation {
        /*
         * 🧪 Zadanie 25:
         * Spróbuj napisać wyrażenie lambda z jawnym `var` przy parametrach,
         * np. `java.util.function.BinaryOperator<Integer> dodaj = (var a,
         * var b) -> a + b;` - jeśli kompiluje się w tym projekcie (Java 21),
         * przetestuj je i wypisz wynik; jeśli nie, w komentarzu zanotuj
         * dokładny błąd kompilacji jako obserwację.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_VarInferresDeclaredInterfaceTypeFromFactoryMethod {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę statyczną fabrykującą `static List<String> stworz()
         * { return new ArrayList<>(); }` (typ zwracany to interfejs List).
         * Przypisz jej wynik do `var wynik = stworz();`. W komentarzu
         * wyjaśnij, że `var` wywnioskuje typ DEKLAROWANY przez sygnaturę
         * metody (List<String>), a nie faktyczną klasę implementującą
         * (ArrayList) widoczną dopiero w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareReadabilityWithNestedGenericType {
        /*
         * 🧪 Zadanie 27:
         * Napisz (jako tekst w komentarzu, bez konieczności pełnej
         * inicjalizacji) obie wersje deklaracji zmiennej dla typu
         * `Map<String, List<Map<String, Integer>>>` - jedną z jawnym typem
         * po obu stronach, drugą z `var`. Skomentuj, która jest bardziej
         * czytelna i dlaczego (odwołaj się do zasady "typ widoczny z prawej
         * strony").
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareJavaVarWithOtherLanguagesTypeInference {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: w komentarzu porównaj krótko `var` z Javy z `auto`
         * z C++ oraz `val`/`var` z Kotlina (czy któryś z nich pozwala
         * zmienić typ zmiennej w trakcie działania programu, tak jak
         * dynamiczne typowanie w Pythonie/JavaScripcie?). Zakończ jednym
         * zdaniem potwierdzającym, że Java `var` NIGDY na to nie pozwala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ProveVarBehavesIdenticallyAtRuntime {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę pomocniczą `static void opisz(Object o) { ... }`
         * wypisującą `o.getClass().getName()`. Wywołaj ją raz przekazując
         * zmienną zadeklarowaną przez `var`, a raz zmienną zadeklarowaną
         * jawnym typem (ta sama wartość) - pokaż, że wynik jest identyczny,
         * potwierdzając że `var` nie zmienia niczego w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildProductRegistryMixingVarAndExplicitTypes {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj krótki fragment programu -
         * rejestr produktów (nazwa + cena) jako `var rejestr = new
         * HashMap<String, Double>();`. Dodaj 4 produkty. Napisz metodę
         * `static double sredniaCena(Map<String, Double> rejestr)` (jawny
         * typ zwracany, bo wynik NIE jest oczywisty z nazwy metody bez
         * zajrzenia do jej ciała) i wywołaj ją, przypisując wynik do zmiennej
         * z JAWNYM typem `double` (celowo bez var). W komentarzu uzasadnij
         * każdy wybór - gdzie var poprawia czytelność, a gdzie jawny typ był
         * lepszy.
         */
        public static void main(String[] args) { }
    }
}
