package com.example.javaquest._14_advancedjava.Lesson01_GenericsIntroduction;

public class _Exercises_Lesson01_GenericsIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyGenericsExist {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania),
         * jaki problem rozwiazuja generyki i dlaczego blad typu wykryty w
         * COMPILE TIME jest lepszy niz ClassCastException w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateRawListAndTriggerClassCastException {
        /*
         * 🧪 Zadanie 2:
         * Stworz surowa (raw, bez <>) java.util.List, dodaj do niej Stringa
         * "Ala" i Integera 5, a nastepnie w petli sprobuj rzutowac kazdy
         * element na String - zlap i wypisz zlapany ClassCastException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateGenericListOfStrings {
        /*
         * 🧪 Zadanie 3:
         * Stworz List<String>, dodaj 3 dowolne imiona, wypisz kazde imie oraz
         * jego dlugosc (name.length()) BEZ zadnego rzutowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_NameTypeParameterConventions {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: w komentarzu wypisz 6 konwencjonalnych nazw parametrow
         * typu z lekcji (T, E, K, V, R, N) wraz z ich znaczeniem (co reprezentuja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseBoxClassWithString {
        /*
         * 🧪 Zadanie 5:
         * Uzyj klasy Box<T> z teorii lekcji (skopiuj ja lub odtworz podobna) -
         * stworz Box<String> z wartoscia "witaj", wypisz ja przez get(), potem
         * zmien przez set("zegnaj") i wypisz ponownie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseBoxClassWithInteger {
        /*
         * 🧪 Zadanie 6:
         * Uzywajac tej samej klasy Box<T> co w zadaniu 5, stworz Box<Integer>
         * z wartoscia 100, wypisz ja, a nastepnie wypisz wynik dodania 1 do
         * wartosci pobranej przez get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteGenericBoxDoubleClass {
        /*
         * 🧪 Zadanie 7:
         * Napisz WLASNA, prosta klase generyczna BoxDouble<T> z polem
         * prywatnym `value`, konstruktorem, metodami get()/set(T) - analogiczna
         * do Box<T> z teorii, ale z innym parametrem konstruktora (bezargumentowy
         * + osobne set()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareRawAndGenericDeclaration {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: w komentarzu napisz deklaracje zmiennej typu "surowa
         * lista" (raw List) obok deklaracji "lista generyczna List<String>" i
         * opisz jednym zdaniem, czym roznia sie na poziomie kompilatora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainDiamondOperator {
        /*
         * 🧪 Zadanie 9:
         * Stworz zmienna `List<String> list = new ArrayList<>();` (diamond
         * operator) - w komentarzu wyjasnij, skad kompilator "wie", jaki typ
         * ma String w new ArrayList<>() mimo braku jawnego <String> po prawej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TryToAddWrongTypeCommentedOut {
        /*
         * 🧪 Zadanie 10:
         * Stworz List<Integer>, dodaj 3 liczby. W komentarzu (zakomentowana
         * linia kodu) napisz linie, ktora probowalaby dodac String do tej
         * listy - i wyjasnij, dlaczego po odkomentowaniu kod NIE skompiluje sie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteGenericPairPlaceholderClass {
        /*
         * 🧪 Zadanie 11:
         * Napisz prosta klase generyczna NamedBox<T> z DWOMA polami: String
         * name oraz T value, konstruktorem ustawiajacym oba, i metoda
         * describe() zwracajaca String w formacie "name = value".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_StoreBoxInstancesInList {
        /*
         * 🧪 Zadanie 12:
         * Stworz List<Box<String>> (lista pojemnikow Box trzymajacych Stringi),
         * dodaj 3 rozne Box<String> z roznymi wartosciami, wypisz zawartosc
         * kazdego przez petle for-each.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DemonstrateClassCastExceptionStackTraceLocation {
        /*
         * 🧪 Zadanie 13:
         * Odtworz sytuacje z Zadania 2 (raw List z mieszanymi typami), ale
         * tym razem NIE lap wyjatku w tym samym miejscu, gdzie zla wartosc
         * zostala dodana - zamiast tego wypisz w komentarzu obserwacje: w
         * ktorej linii (dodania czy odczytu) pojawia sie realny blad i dlaczego
         * to utrudnia debugowanie w duzych projektach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareTwoBoxInstancesDifferentTypes {
        /*
         * 🧪 Zadanie 14:
         * Stworz Box<String> i Box<Integer> - w komentarzu wyjasnij, czy mozna
         * przypisac jedno do drugiego (Box<String> b = box_integer;) i dlaczego
         * kompilator by na to nie pozwolil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteGenericClassForCustomType {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj prosty rekord/klase Product (np. z polami String name,
         * double price), a nastepnie stworz Box<Product> z jednym produktem i
         * wypisz jego zawartosc przez get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainWhyPrimitivesNotAllowedAsTypeArguments {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: w komentarzu wyjasnij, dlaczego nie mozna napisac
         * Box<int> (typ prymitywny jako argument generyczny) i jakiego typu
         * (opakowujacego, wrapper) trzeba uzyc zamiast tego (np. Box<Integer>).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SwapValuesInTwoBoxes {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode statyczna `static <T> void swap(Box<T> a, Box<T> b)`,
         * ktora zamienia miejscami wartosci dwoch obiektow Box<T> tego samego
         * typu, i przetestuj ja na dwoch Box<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CountElementsOfSpecificTypeInRawList {
        /*
         * 🧪 Zadanie 18:
         * W surowej (raw) liscie zawierajacej mieszane typy (String, Integer,
         * Double) policz przy pomocy `instanceof`, ile jest elementow typu
         * String - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignBoxWithValidation {
        /*
         * 🧪 Zadanie 19:
         * Rozszerz klase Box<T> o walidacje w konstruktorze/set() - jesli
         * przekazana wartosc jest null, rzuc IllegalArgumentException z
         * czytelnym komunikatem. Przetestuj oba przypadki (poprawna wartosc
         * i null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainTypeSafetyInOwnWords {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz krotki akapit (min. 5 zdan) tlumaczacy koledze,
         * ktory nie zna generyków, dlaczego List<String> jest "bezpieczniejsza"
         * niz surowa List, z odwolaniem do konkretnego przykladu z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WriteGenericPairOfDifferentTypes {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase generyczna TwoBox<A, B> trzymajaca DWIE wartosci roznych
         * typow (pole first typu A, pole second typu B), z konstruktorem i
         * gettery/settery dla obu - przetestuj z TwoBox<String, Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulatePreJava5CollectionApiPain {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode `static void processRawList(java.util.List list)`,
         * ktora przyjmuje SUROWA liste i probuje zsumowac wszystkie elementy
         * jako Integer - wywolaj ja z lista zawierajaca 1 zly element (np.
         * String) i zlap wyjatek, wypisujac przyjazny komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildGenericStackFromScratch {
        /*
         * 🧪 Zadanie 23:
         * Napisz WLASNA, prosta klase generyczna SimpleStack<T> oparta na
         * java.util.ArrayList<T> (pole wewnetrzne), z metodami push(T),
         * pop() (zwraca T, rzuca wyjatek gdy pusty) i isEmpty() - przetestuj
         * dla SimpleStack<String>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareGenericAndOverloadedMethodsApproach {
        /*
         * 🧪 Zadanie 24:
         * Napisz 2 przeciazone (overloaded) metody printValue(String) i
         * printValue(Integer) ORAZ jedna metode generyczna
         * `static <T> void printGeneric(T value)` - w komentarzu porownaj
         * oba podejscia (ile kodu trzeba napisac dla N typow) i wyjasnij,
         * dlaczego generyki skaluja sie lepiej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignImmutableGenericBox {
        /*
         * 🧪 Zadanie 25:
         * Napisz klase ImmutableBox<T> - BEZ metody set(), wartosc ustawiana
         * TYLKO raz w konstruktorze i pole `final`. W komentarzu wyjasnij,
         * dlaczego brak mozliwosci zmiany wartosci moze byc zaleta (odwolaj
         * sie do bezpieczenstwa watkowego, poznanego w rozdziale _05_multithreading).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AnalyzeRealJdkGenericTypeUsage {
        /*
         * 🧪 Zadanie 26:
         * Znajdz (przegladajac wczesniejsze lekcje kursu, np. _03_collections)
         * 3 przyklady generycznych typow z JDK (np. List<T>, Optional<T>,
         * Map<K,V>) - dla kazdego w komentarzu napisz, jakiego parametru typu
         * uzywa i co reprezentuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_WriteGenericMethodCountMatching {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode generyczna
         * `static <T> int countEqual(List<T> list, T target)`, ktora liczy,
         * ile razy target wystepuje w liscie (uzyj equals()) - przetestuj dla
         * List<String> i List<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignTypeSafeEventBoxHierarchy {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj interfejs Event oraz 2 klasy implementujace go (np.
         * OrderCreatedEvent, UserRegisteredEvent). Stworz Box<Event> i pokaz,
         * ze mozna do niego wlozyc DOWOLNA implementacje Event (polimorfizm +
         * generyki dzialaja razem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MeasureCompileTimeVsRuntimeErrorCost {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz krotki "raport" (min. 6 zdan) porownujacy
         * koszt naprawy bledu wykrytego w compile time vs bledu wykrytego
         * dopiero na produkcji (ClassCastException po miesiacach dzialania
         * aplikacji) - uzasadnij liczbowo/scenariuszowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildMiniTypeSafeRegistry {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj klase Registry<T> przechowujaca
         * wewnetrznie java.util.Map<String, T> (nazwa -> wartosc), z metodami
         * register(String key, T value), get(String key) i metoda
         * printAll() wypisujaca wszystkie wpisy. Przetestuj z Registry<String>
         * (np. rejestr konfiguracji) i wyjasnij w komentarzu, dlaczego ta
         * klasa jest bezpieczniejsza niz Registry oparta na Map<String, Object>.
         */
        public static void main(String[] args) { }
    }
}
