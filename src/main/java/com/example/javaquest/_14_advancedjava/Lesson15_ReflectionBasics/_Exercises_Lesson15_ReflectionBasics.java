package com.example.javaquest._14_advancedjava.Lesson15_ReflectionBasics;

public class _Exercises_Lesson15_ReflectionBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_GetClassBySimpleLiteral {
        /*
         * 🧪 Zadanie 1:
         * Utworz klase "Product" z polami "String name" i "double price".
         * Pobierz jej obiekt "Class<Product>" sposobem "Product.class" i
         * wypisz "getName()" oraz "getSimpleName()".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetClassFromInstance {
        /*
         * 🧪 Zadanie 2:
         * Utworz instancje "Product" (Zadanie 1) i pobierz jej "Class" przez
         * "instance.getClass()". Porownaj (operatorem "==") z
         * "Product.class" i wypisz, czy to TEN SAM obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_GetClassByForName {
        /*
         * 🧪 Zadanie 3:
         * Uzyj "Class.forName(String)" z PELNA, kwalifikowana nazwa klasy
         * "Product" (Zadanie 1), zlap "ClassNotFoundException" (checked) i
         * wypisz "getName()" zwroconego obiektu Class.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DeclaredFieldsVsFields {
        /*
         * 🧪 Zadanie 4:
         * Utworz klase "Account" z polami: "public String owner",
         * "private double balance", "protected String currency". Wypisz
         * NAZWY pol zwroconych przez "getFields()" oraz osobno przez
         * "getDeclaredFields()" - porownaj dlugosc obu tablic w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DeclaredMethodsVsMethods {
        /*
         * 🧪 Zadanie 5:
         * Dodaj do "Account" (Zadanie 4) metody: "public void deposit(double)"
         * i "private void logTransaction(String)". Wypisz nazwy metod z
         * "getMethods()" i osobno z "getDeclaredMethods()" - zwroc uwage, ze
         * "getMethods()" zawiera tez metody odziedziczone po "Object".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_GetFieldByNameAndReadValue {
        /*
         * 🧪 Zadanie 6:
         * Na instancji "Account" (Zadanie 4) uzyj "getDeclaredField("owner")",
         * wywolaj "setAccessible(true)" i odczytaj wartosc przez "Field.get(obiekt)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InvokePublicMethodReflectively {
        /*
         * 🧪 Zadanie 7:
         * Uzyj "getMethod("deposit", double.class)" na klasie "Account"
         * (Zadanie 4/5) i wywolaj ja reflekcyjnie przez "Method.invoke(obiekt, 100.0)".
         * Zweryfikuj efekt, odczytujac pole "balance" reflekcyjnie (jak w Zadaniu 6).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NewInstanceViaPublicNoArgConstructor {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do "Product" (Zadanie 1) publiczny konstruktor bezargumentowy.
         * Uzyj "Product.class.getConstructor().newInstance()" i wypisz
         * utworzony obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListConstructorsOfAClass {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do "Product" (Zadanie 1/8) DRUGI konstruktor przyjmujacy
         * "(String name, double price)". Wypisz WSZYSTKIE konstruktory
         * zwrocone przez "getDeclaredConstructors()" wraz z liczba ich
         * parametrow ("getParameterCount()").
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ModifiersOfFieldsAndMethods {
        /*
         * 🧪 Zadanie 10:
         * Dla klasy "Account" (Zadanie 4/5) wypisz dla KAZDEGO pola i KAZDEJ
         * zadeklarowanej metody jej modyfikatory dostepu przez
         * "Modifier.toString(field.getModifiers())" /
         * "Modifier.toString(method.getModifiers())".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PrivateFieldWithoutSetAccessibleThrows {
        /*
         * 🧪 Zadanie 11:
         * Na polu "balance" klasy "Account" (Zadanie 4) sprobuj wywolac
         * "Field.get(obiekt)" BEZ uprzedniego "setAccessible(true)" - zlap
         * "IllegalAccessException" i wypisz jej komunikat, a NASTEPNIE
         * powtorz odczyt PO wywolaniu "setAccessible(true)" i porownaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SetPrivateFieldBypassingSetter {
        /*
         * 🧪 Zadanie 12:
         * Zaloz, ze "Account.deposit(double)" (Zadanie 5) NIGDY nie pozwala
         * na ujemny balans (rzuca wyjatek dla ujemnej kwoty). Uzyj
         * "Field.set(obiekt, -500.0)" na polu "balance" (po "setAccessible(true)"),
         * by USTAWIC ujemny balans OMIJAJAC ta walidacje - wypisz wynik i
         * skomentuj (min. 2 zdania), dlaczego to niebezpieczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_InvokePrivateMethodReflectively {
        /*
         * 🧪 Zadanie 13:
         * Uzyj "getDeclaredMethod("logTransaction", String.class)" na klasie
         * "Account" (Zadanie 5), wywolaj "setAccessible(true)" i wywolaj
         * PRYWATNA metode "logTransaction" reflekcyjnie z dowolnym tekstem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NewInstanceViaPrivateConstructor {
        /*
         * 🧪 Zadanie 14:
         * Dodaj do "Product" (Zadanie 1) PRYWATNY konstruktor bezargumentowy
         * (usun/zostaw publiczny z Zadania 8 - masz miec OBA warianty do
         * porownania). Uzyj "getDeclaredConstructor()" + "setAccessible(true)"
         * + "newInstance()", by utworzyc obiekt mimo prywatnosci konstruktora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_GenericReflectiveObjectPrinter {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode "static void printAllFields(Object obj)", ktora
         * przez "obj.getClass().getDeclaredFields()" (z "setAccessible(true)"
         * na kazdym polu) wypisuje "nazwaPola = wartosc" dla WSZYSTKICH pol,
         * niezaleznie od widocznosci. Przetestuj ja na instancjach "Account" i "Product".
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CopyFieldsBetweenTwoObjectsReflectively {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode "static void copyFields(Object source, Object target)"
         * (oba tego samego typu), ktora dla kazdego pola zadeklarowanego w
         * klasie odczytuje wartosc ze "source" i ustawia ja na "target"
         * (uzywajac "setAccessible(true)" na kazdym polu). Przetestuj na
         * dwoch obiektach "Product".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InstantiateByClassNameFromString {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode "static Object createByName(String fullyQualifiedName)",
         * ktora uzywa "Class.forName(name)" i bezargumentowego konstruktora
         * (przez "getDeclaredConstructor().newInstance()", z "setAccessible(true)"
         * gdyby byl prywatny) do stworzenia obiektu. Wywolaj ja z nazwa klasy
         * "Product" jako zwykly String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_HandleNoSuchFieldException {
        /*
         * 🧪 Zadanie 18:
         * Sprobuj pobrac przez "getDeclaredField(...)" pole o NIEISTNIEJACEJ
         * nazwie (np. "doesNotExist") z klasy "Account" - zlap
         * "NoSuchFieldException" i wypisz przyjazny komunikat bledu z nazwa
         * brakujacego pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HandleInvocationTargetException {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode "divide(int a, int b)" w klasie "Account" (lub nowej
         * klasie pomocniczej), ktora rzuca "ArithmeticException" przy dzieleniu
         * przez zero. Wywolaj ja reflekcyjnie przez "Method.invoke(obj, 10, 0)"
         * - zlap "InvocationTargetException", odczytaj "getCause()" i wypisz
         * ORYGINALNY wyjatek (ArithmeticException), ktory sie w niej ukryl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ListAllMembersGroupedByDeclaringClass {
        /*
         * 🧪 Zadanie 20:
         * Utworz klase "SavingsAccount extends Account" (Zadanie 4) dodajaca
         * wlasne pole "double interestRate". Dla instancji "SavingsAccount"
         * wypisz WSZYSTKIE pola zwrocone przez "getFields()" razem z tym, KTORA
         * klasa je zadeklarowala (uzyj "Field.getDeclaringClass()") - pokaz,
         * ze wsrod nich sa pola z OBU klas (rodzica i dziecka).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SimpleReflectiveEqualsUsingDeclaredFields {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode "static boolean reflectiveEquals(Object a, Object b)",
         * ktora (dla obiektow tej samej klasy) porownuje WARTOSCI wszystkich
         * pol zadeklarowanych w klasie (przez "getDeclaredFields()" +
         * "setAccessible(true)" + "Field.get") i zwraca "true" tylko gdy
         * WSZYSTKIE sa rowne. Przetestuj na dwoch rownych i dwoch roznych
         * obiektach "Product".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReflectiveToStringBuilder {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode "static String reflectiveToString(Object obj)", ktora
         * buduje napis w formacie "KlasaProstaNazwa{pole1=wartosc1, pole2=wartosc2, ...}"
         * na podstawie WSZYSTKICH pol zadeklarowanych w klasie (reflekcyjnie,
         * bez wywolywania wlasnego "toString()" obiektu). Przetestuj na "Account".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MiniDependencyInjectorByFieldType {
        /*
         * 🧪 Zadanie 23:
         * Napisz interfejs "Repository" i implementacje "InMemoryRepository".
         * Napisz klase "Service" z PRYWATNYM polem "Repository repository"
         * (bez settera/konstruktora go ustawiajacego). Napisz metode
         * "static void injectByType(Object target, Object dependency)", ktora
         * przez refleksje znajduje POLE w "target" o typie zgodnym z klasa
         * "dependency" i ustawia je (setAccessible + Field.set) - mini,
         * uproszczona wersja tego, co robi Guice z "@Inject" (Lesson16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ReflectiveBuilderFromMap {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode "static <T> T buildFromMap(Class<T> type, Map<String, Object> values)",
         * ktora tworzy obiekt przez bezargumentowy konstruktor (reflekcyjnie)
         * i dla kazdego wpisu w mapie ustawia pole o tej nazwie (reflekcyjnie,
         * setAccessible). Przetestuj z klasa "Product" i mapa
         * {"name" -> "Laptop", "price" -> 4500.0}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CountInstancesOfAllPrimitiveVsReferenceFields {
        /*
         * 🧪 Zadanie 25:
         * Napisz klase z mieszanka pol prymitywnych (int, double, boolean) i
         * referencyjnych (String, List). Metoda "static void categorizeFields(Class<?> type)"
         * ma przez "Field.getType().isPrimitive()" policzyc i wypisac, ile pol
         * jest prymitywnych, a ile referencyjnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ReflectiveMethodChainInvoker {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode "static Object invokeChain(Object start, String... methodNames)",
         * ktora WYWOLUJE KOLEJNO (reflekcyjnie, bezargumentowo) metody o
         * podanych nazwach, przekazujac WYNIK poprzedniej jako obiekt, na
         * ktorym wywolujemy nastepna (np. "invokeChain(account, "toString", "trim", "toUpperCase")").
         * Zlap i wypisz przyjazny blad, jesli ktoras metoda nie istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DeepCopyViaReflection {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode "static <T> T shallowCopy(T source)", ktora tworzy NOWY
         * obiekt tej samej klasy (przez bezargumentowy konstruktor, reflekcyjnie)
         * i kopiuje do niego wartosci WSZYSTKICH pol zrodla (jak Zadanie 16, ale
         * generycznie, dla DOWOLNEJ klasy). Zweryfikuj, ze kopia i oryginal to
         * DWA ROZNE obiekty ("!=") o TAKICH SAMYCH wartosciach pol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnnotationDrivenFieldValidator {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj wlasna adnotacje "@NotBlank" (RUNTIME, TYPE_USE/FIELD) -
         * jak w Lesson13/14. Oznacz nia pole "String name" w klasie "Product".
         * Napisz metode "static List<String> validate(Object obj)", ktora
         * reflekcyjnie przeglada pola obiektu, sprawdza obecnosc adnotacji
         * "@NotBlank" i - jesli wartosc pola jest null/pusta - dodaje komunikat
         * bledu do listy. Przetestuj na poprawnym i niepoprawnym obiekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PerformanceComparisonDirectVsReflectiveCall {
        /*
         * 🧪 Zadanie 29:
         * Zmierz (System.nanoTime()) czas 1 000 000 wywolan metody "getSalary()"
         * (lub podobnej) NA WPROST (obiekt.getSalary()) oraz TE SAMA liczbe
         * wywolan PRZEZ "Method.invoke(obiekt)" (Method pobrany RAZ, przed
         * petla). Wypisz oba czasy i stosunek miedzy nimi - w komentarzu
         * skomentuj wynik (przygotowanie do tematu wydajnosci w Lesson16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneGenericObjectInspector {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz metode "static void inspect(Object obj)",
         * ktora dla DOWOLNEGO obiektu wypisuje: pelna nazwe klasy, liste
         * WSZYSTKICH pol (nazwa, typ, modyfikatory, wartosc - z setAccessible),
         * liste WSZYSTKICH zadeklarowanych metod (nazwa, typy parametrow, typ
         * zwracany) oraz liste WSZYSTKICH konstruktorow (liczba parametrow,
         * modyfikatory). Przetestuj ja na "Account" i "SavingsAccount"
         * (Zadanie 20) - to ma dzialac jak mini "debugger" napisany w calosci
         * przez refleksje.
         */
        public static void main(String[] args) { }
    }
}
