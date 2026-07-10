package com.example.javaquest._15_jvm_internals.Lesson03_ClassLoadingMechanics;

public class _Lesson03_ClassLoadingMechanics {

    // Flaga statyczna + blok inicjalizujacy - uzywane ponizej do demonstracji
    // LENIWEJ inicjalizacji klas (klasa jest ladowana, ale NIE inicjalizowana,
    // dopoki nie zostanie realnie uzyta).
    static class LazyDemo {
        static {
            System.out.println("  >>> Blok statyczny LazyDemo WLASNIE SIE WYKONAL (klasa zostala zainicjalizowana)!");
        }

        static final String MESSAGE = "Wiadomosc z LazyDemo";

        static void sayHello() {
            System.out.println("  LazyDemo.sayHello() -> " + MESSAGE);
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 3: MECHANIKA LADOWANIA KLAS ===\n");

        /*
         * ============================================================
         * 🔹 HIERARCHIA CLASS LOADEROW
         * ============================================================
         * JVM (HotSpot) laduje klasy przy pomocy hierarchii TRZECH
         * wbudowanych class loaderow, ulozonych "rodzic-dziecko":
         *
         *  1) BOOTSTRAP CLASS LOADER - napisany w kodzie NATYWNYM (nie w
         *    Javie!), czesc samej JVM. Laduje rdzen biblioteki standardowej
         *    (java.lang.*, java.util.* itd. - moduly zaczynajace sie od
         *    "java."). Z poziomu kodu Javy widac go jako "null" (bo nie
         *    jest reprezentowany zadnym obiektem Java) - zobaczymy to zaraz
         *    w demo.
         *  2) PLATFORM CLASS LOADER (dawniej "Extension ClassLoader" przed
         *     JPMS) - laduje niektore moduly platformowe spoza absolutnego
         *     rdzenia (np. czesci XML, kryptografii).
         *  3) APPLICATION CLASS LOADER (tez "System ClassLoader") - laduje
         *    klasy Z TWOJEGO classpath/module path - czyli CALY kod tego
         *    kursu jest ladowany WLASNIE przez ten loader.
         *
         * Ponad tym mozesz dolozyc WLASNE class loadery (dziecko
         * Application Class Loadera) - zobaczymy to w Lekcji 4.
         */
        explainClassLoaderHierarchy();

        /*
         * ============================================================
         * 🔹 MODEL DELEGACJI RODZIC-NAJPIERW (PARENT-FIRST DELEGATION)
         * ============================================================
         * Gdy dowolny class loader dostaje prosbe "zaladuj mi klase X",
         * DOMYSLNIE (tak dziala standardowa implementacja ClassLoader)
         * NIE probuje ladowac jej sam od razu - najpierw PYTA SWOJEGO
         * RODZICA, czy on juz tego nie zrobil / nie potrafi tego zrobic.
         * Dopiero gdy CALY lancuch rodzicow zglosi "nie mam takiej klasy",
         * loader probuje zaladowac ja SAM.
         *
         * Praktyczna konsekwencja: jesli Twoj wlasny kod zdefiniuje klase
         * "java.lang.String" (teoretycznie mozliwe - taki sam pakiet i
         * nazwa), Application Class Loader I TAK najpierw zapyta Bootstrap
         * Class Loadera, ktory ZAWSZE znajdzie SWOJA, prawdziwa klase
         * java.lang.String pierwszy - Twoja wersja NIGDY nie zostanie
         * uzyta. To fundamentalny mechanizm BEZPIECZENSTWA - chroni rdzen
         * jezyka przed podmiana ("class spoofing").
         */
        explainParentFirstDelegation();

        /*
         * ============================================================
         * 🔹 Class.forName(...) vs ClassLoader.loadClass(...)
         * ============================================================
         * Obie metody "znajduja" klase po nazwie, ale ROZNIE traktuja
         * INICJALIZACJE (patrz nizej: Loading vs Initialization):
         *
         *  - Class.forName(String name) - domyslnie (jednoargumentowa
         *    wersja) LADUJE klase I OD RAZU JA INICJALIZUJE (wykonuje bloki
         *    statyczne, ustawia wartosci pol statycznych).
         *  - Class.forName(name, initialize, loader) - trojargumentowa
         *    wersja pozwala JAWNIE wylaczyc inicjalizacje (initialize=false).
         *  - ClassLoader.loadClass(String name) - TYLKO LADUJE klase
         *    (Loading + Linking), ale domyslnie NIE inicjalizuje jej -
         *    inicjalizacja nastapi dopiero przy pierwszym REALNYM uzyciu
         *    (np. dostep do statycznego pola, wywolanie statycznej metody,
         *    utworzenie instancji).
         *
         * To rozroznienie ma realne znaczenie - zobaczymy ponizej dowod na
         * to, ze inicjalizacja jest LENIWA (lazy) i nastepuje dokladnie w
         * momencie pierwszego uzycia, NIE w momencie zaladowania.
         */
        explainForNameVsLoadClass();

        /*
         * ============================================================
         * 🔹 FAZY: LOADING -> LINKING -> INITIALIZATION
         * ============================================================
         * JVMS definiuje scisly cykl zycia klasy w pamieci JVM, w 3 duzych
         * krokach (Linking dzieli sie na 3 podfazy):
         *
         *  1) LOADING (ladowanie) - class loader znajduje bajty .class
         *    (na dysku, w JAR-ze, wygenerowane w locie...) i tworzy w
         *    pamieci JVM odpowiadajacy im obiekt java.lang.Class.
         *  2) LINKING (lacznie) - dzieli sie na:
         *     a) VERIFICATION (weryfikacja) - JVM SPRAWDZA, czy bajtkod
         *        jest poprawny i bezpieczny (np. czy stos operandow nigdy
         *        nie przepelnia sie, czy typy sie zgadzaja) - to wazna
         *        warstwa bezpieczenstwa, dziala NIEZALEZNIE od tego, czy
         *        bajtkod pochodzi z zaufanego kompilatora, czy zostal
         *        wygenerowany/zmodyfikowany recznie.
         *     b) PREPARATION (przygotowanie) - JVM alokuje pamiec na pola
         *        statyczne i ustawia je na wartosci DOMYSLNE (0, null,
         *        false) - jeszcze NIE te podane w kodzie.
         *     c) RESOLUTION (rozwiazywanie) - symboliczne odwolania w
         *        constant poolu (nazwy innych klas/metod/pol) sa
         *        zamieniane na konkretne referencje - moze to zachodzic
         *        leniwie (przy pierwszym uzyciu danego odwolania).
         *  3) INITIALIZATION (inicjalizacja) - JVM wykonuje bloki statyczne
         *    (static { ... }) oraz przypisuje REALNE wartosci polom
         *    statycznym (np. "static final String X = "abc";" - dopiero
         *    tutaj pole dostaje "abc", wczesniej mialo null).
         *
         * Inicjalizacja jest LENIWA - JVM odklada ja az do momentu, gdy
         * klasa zostanie faktycznie "aktywnie uzyta" (m.in.: utworzenie
         * instancji, wywolanie statycznej metody, dostep/przypisanie do
         * statycznego pola nie bedacego compile-time constant, uzycie
         * refleksji z jawna inicjalizacja).
         */
        explainLoadingLinkingInitialization();

        /*
         * ============================================================
         * 🔍 DEMO 1: LANCUCH CLASS LOADEROW DLA ROZNYCH KLAS
         * ============================================================
         */
        System.out.println("\n--- DEMO: getClassLoader() dla roznych klas ---");
        printClassLoaderChain("Wlasna klasa tej lekcji", _Lesson03_ClassLoadingMechanics.class);
        printClassLoaderChain("java.lang.String (rdzen JDK)", String.class);
        printClassLoaderChain("java.util.ArrayList (rdzen JDK)", java.util.ArrayList.class);

        /*
         * ============================================================
         * 🔍 DEMO 2: LENIWA INICJALIZACJA - DOWOD NA ZYWO
         * ============================================================
         * Ponizej NAJPIERW tylko "wspominamy" o klasie LazyDemo przez
         * Class.forName(..., initialize = false, ...) - klasa zostaje
         * ZALADOWANA, ale jej blok statyczny NIE wykonuje sie jeszcze.
         * Dopiero gdy realnie wywolamy jej metode statyczna, zobaczymy
         * komunikat z bloku statycznego - DOKLADNIE w tym momencie.
         */
        System.out.println("\n--- DEMO: leniwa inicjalizacja klasy LazyDemo ---");
        String lazyClassName = LazyDemo.class.getName();

        System.out.println("Krok 1: Class.forName(nazwa, initialize=false, ...) - TYLKO ladowanie.");
        Class<?> loadedNotInitialized = Class.forName(
                lazyClassName, false, _Lesson03_ClassLoadingMechanics.class.getClassLoader());
        System.out.println("Klasa zaladowana: " + loadedNotInitialized.getName()
                + " (uwaga: blok statyczny NIE powinien byl jeszcze wypisac komunikatu powyzej)");

        System.out.println("\nKrok 2: dopiero TERAZ realnie uzywamy klasy (wywolanie metody statycznej):");
        LazyDemo.sayHello();
        System.out.println("^ Widac powyzej, ze komunikat z bloku statycznego pojawil sie DOPIERO TU.");

        System.out.println("\n=== KONIEC LEKCJI 3 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Hierarchia loaderow: Bootstrap (natywny, rdzen JDK, widoczny
         *   jako null) -> Platform -> Application (Twoj kod/classpath).
         * - Model PARENT-FIRST: kazdy loader najpierw pyta rodzica, zanim
         *   sam sprobuje zaladowac klase - chroni rdzen jezyka przed
         *   podmiana klas ("class spoofing").
         * - Class.forName(name) domyslnie LADUJE i INICJALIZUJE; wersja
         *   3-argumentowa (name, initialize, loader) pozwala to rozdzielic;
         *   ClassLoader.loadClass(name) domyslnie TYLKO laduje.
         * - Cykl zycia klasy: LOADING -> LINKING (Verification ->
         *   Preparation -> Resolution) -> INITIALIZATION. Inicjalizacja
         *   jest LENIWA - nastepuje dopiero przy pierwszym realnym uzyciu
         *   klasy, co udowodnilismy na zywo powyzej.
         * - Nastepna lekcja (4): piszemy WLASNY ClassLoader i pokazujemy
         *   klasyczna "zagadke" - dwie roznie instancje custom loadera
         *   ladujace ta sama klase daja DWA ROZNE obiekty Class.
         */
    }

    private static void explainClassLoaderHierarchy() {
        System.out.println("[Hierarchia class loaderow]");
        System.out.println("Bootstrap (natywny, rdzen JDK) -> Platform -> Application (Twoj kod)\n");
    }

    private static void explainParentFirstDelegation() {
        System.out.println("[Model delegacji parent-first]");
        System.out.println("Kazdy loader pyta NAJPIERW rodzica - chroni rdzen jezyka przed podmiana klas.\n");
    }

    private static void explainForNameVsLoadClass() {
        System.out.println("[Class.forName vs ClassLoader.loadClass]");
        System.out.println("Class.forName(name)              -> laduje I inicjalizuje.");
        System.out.println("Class.forName(name, false, cl)   -> laduje BEZ inicjalizacji.");
        System.out.println("classLoader.loadClass(name)      -> domyslnie TYLKO laduje.\n");
    }

    private static void explainLoadingLinkingInitialization() {
        System.out.println("[Cykl zycia klasy]");
        System.out.println("LOADING -> LINKING (Verification -> Preparation -> Resolution) -> INITIALIZATION\n");
    }

    private static void printClassLoaderChain(String label, Class<?> clazz) {
        System.out.println(label + " (" + clazz.getName() + "):");
        ClassLoader loader = clazz.getClassLoader();
        if (loader == null) {
            System.out.println("  -> null (oznacza: zaladowana przez BOOTSTRAP class loader, napisany natywnie)");
            return;
        }
        int depth = 0;
        while (loader != null) {
            System.out.println("  " + "  ".repeat(depth) + "-> " + loader.getClass().getName());
            loader = loader.getParent();
            depth++;
        }
        System.out.println("  " + "  ".repeat(depth) + "-> null (Bootstrap - koniec lancucha rodzicow)");
    }
}
