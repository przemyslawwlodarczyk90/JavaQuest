package com.example.javaquest._14_advancedjava.Lesson06_TypeErasure;

public class _Exercises_Lesson06_TypeErasure {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_GetClassEqualityTwoLists {
        /*
         * 🧪 Zadanie 1:
         * Utworz List<String> oraz List<Double> (obie puste ArrayList).
         * Wypisz getClass() obu list oraz wynik porownania
         * (listaString.getClass() == listaDouble.getClass()) - powinno byc
         * true, mimo roznych parametrow typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetClassEqualityMapsAndSets {
        /*
         * 🧪 Zadanie 2:
         * Powtorz eksperyment z Zadania 1, ale dla Map<String, Integer> i
         * Map<Integer, String> (HashMap) oraz dla Set<String> i Set<Long>
         * (HashSet). Wypisz wyniki porownan getClass() dla obu par.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RawTypeVsParameterizedRuntimeSame {
        /*
         * 🧪 Zadanie 3:
         * Utworz surowa (raw) liste `List surowa = new ArrayList();` oraz
         * parametryzowana `List<String> parametryzowana = new ArrayList<>();`.
         * Porownaj ich getClass() i wypisz wniosek - w runtime nie ma miedzy
         * nimi zadnej roznicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_NoNewTDirectCompileError {
        /*
         * 🧪 Zadanie 4:
         * W komentarzu zapisz metode generyczna `<T> T stworz() { return new
         * T(); }` i wypisz (jako println) dokladny komunikat bledu
         * kompilacji, jaki by sie pojawil, gdyby probowac to naprawde
         * skompilowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SupplierFactoryWorkaround {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode generyczna `<T> T stworz(Supplier<T> fabryka)`
         * zwracajaca `fabryka.get()`. Uzyj jej do utworzenia nowego
         * StringBuilder oraz nowej ArrayList<Integer> (przez referencje do
         * konstruktora) i wypisz oba utworzone obiekty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NoGenericArrayDirectCompileError {
        /*
         * 🧪 Zadanie 6:
         * W komentarzu zapisz fragment metody generycznej
         * `<T> T[] stworzTablice(int rozmiar) { return new T[rozmiar]; }` i
         * wypisz (println) dokladny komunikat bledu kompilacji "generic
         * array creation".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UncheckedCastArrayWorkaround {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode generyczna z adnotacja @SuppressWarnings("unchecked")
         * `<T> T[] stworzTabliceUnsafe(int rozmiar)`, ktora zwraca
         * `(T[]) new Object[rozmiar]`. Wywolaj ja dla String i wypisz
         * getClass().getSimpleName() otrzymanej tablicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ArrayNewInstanceWorkaround {
        /*
         * 🧪 Zadanie 8:
         * Napisz metode generyczna `<T> T[] stworzTablice(Class<T> typ, int
         * rozmiar)` korzystajaca z `java.lang.reflect.Array.newInstance`.
         * Wywolaj ja dla Integer.class i wypisz getClass().getSimpleName()
         * otrzymanej tablicy - powinna to byc "Integer[]", a nie "Object[]".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_InstanceofRawTypeOnly {
        /*
         * 🧪 Zadanie 9:
         * Majac Object zawierajacy List<String>, sprawdz go przez
         * `instanceof List<?> lista` (pattern matching) i wypisz jego
         * rozmiar. W komentarzu zapisz, dlaczego `instanceof List<String>`
         * NIE skompilowalby sie ("illegal generic type for instanceof").
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ErasureBoundedTypeParameter {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj metode generyczna `<T extends Number> void wypisz(T
         * wartosc)`. W komentarzu wyjasnij, na jaki typ zostanie wymazany
         * parametr T w bajtkodzie (podpowiedz: na ograniczenie z extends, a
         * nie na Object). Wywolaj metode dla Integer i Double.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SafeVarargsBasicUsage {
        /*
         * 🧪 Zadanie 11:
         * Napisz prywatna, statyczna metode generyczna oznaczona
         * @SafeVarargs `<T> List<T> listaZ(T... elementy)`, ktora zwraca
         * nowa ArrayList wypelniona przekazanymi elementami (bez modyfikacji
         * ani udostepniania tablicy varargs na zewnatrz). Przetestuj dla 5
         * Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_HeapPollutionConceptualExample {
        /*
         * 🧪 Zadanie 12:
         * W komentarzu (NIE w realnym, uruchamianym kodzie) zapisz metode
         * generyczna z varargs, ktora wewnatrz przypisuje tablice varargs do
         * zmiennej Object[] i wstawia do niej element niewlasciwego typu
         * (heap pollution). Wypisz (println), w ktorym miejscu wyleci
         * ClassCastException - u wywolujacego, nie w tej metodzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WhySafeVarargsOnlyStaticFinalPrivate {
        /*
         * 🧪 Zadanie 13:
         * Napisz w komentarzu przyklad klasy z metoda NIE-static, NIE-final,
         * publiczna, generyczna z varargs, ktora probuje uzyc @SafeVarargs -
         * wypisz (println) dokladny komunikat bledu kompilacji ("Invalid
         * SafeVarargs annotation..."). Wyjasnij, dlaczego regula ta chroni
         * przed nadpisaniem metody w niebezpieczny sposob w podklasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BridgeMethodDetectionViaReflection {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj klase `MojaLiczba implements Comparable<MojaLiczba>`
         * (pole int wartosc, implementacja compareTo(MojaLiczba)). Przez
         * refleksje (getClass().getMethods()) znajdz i wypisz WSZYSTKIE
         * metody compareTo (w tym bridge method compareTo(Object)) razem z
         * wynikiem ich isBridge().
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EraseToFirstBoundInterface {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj interfejs `Nazwany { String nazwa(); }` i metode
         * generyczna `<T extends Nazwany> void pokaz(T obiekt)`. W
         * komentarzu wyjasnij, na jaki typ wymazuje sie T (Nazwany, bo to
         * jego jedyne ograniczenie). Zaimplementuj prosta klase realizujaca
         * Nazwany i wywolaj pokaz(...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GenericStackWithArrayWorkaround {
        /*
         * 🧪 Zadanie 16:
         * Napisz prosta klase generyczna `GenericStack<T>` z wewnetrzna
         * tablica `Object[]` (NIE `T[]` - uzasadnij w komentarzu dlaczego),
         * metodami push(T) i T pop() (z rzutowaniem @SuppressWarnings przy
         * odczycie). Przetestuj push/pop na kilku Stringach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ClassObjectAsRuntimeTypeToken {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode generyczna `<T> boolean jestTypu(Object obiekt,
         * Class<T> typ)` uzywajaca `typ.isInstance(obiekt)` jako sposobu na
         * sprawdzenie typu w runtime (obejscie ograniczenia instanceof z
         * Zadania 9). Przetestuj dla Stringa i Integera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_VarargsArrayIsVisibleObjectArray {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode generyczna `<T> void pokazTypTablicy(T... elementy)`,
         * ktora wypisuje `elementy.getClass().getSimpleName()` - pokaz, ze
         * dla wywolania z Stringami wynik to "Object[]", a NIE "String[]"
         * (bo w runtime typ generyczny varargs jest wymazany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareRawTypeWarningsVsGeneric {
        /*
         * 🧪 Zadanie 19:
         * Napisz DWIE wersje tej samej operacji: dodanie elementu do listy
         * przez surowy typ `List surowa = new ArrayList();
         * surowa.add("tekst")` (generuje ostrzezenie kompilatora "unchecked
         * call") oraz przez `List<String> bezpieczna = new ArrayList<>();`.
         * Wypisz obie listy i skomentuj roznice w bezpieczenstwie typow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ArrayOfGenericTypeFieldWorkaround {
        /*
         * 🧪 Zadanie 20:
         * Napisz klase `Bufor<T>` z polem `private final Object[] dane` (nie
         * T[]) i metoda `T get(int index)` rzutujaca element na T (z
         * @SuppressWarnings("unchecked") w jednej, wask zawezonej linii).
         * Przetestuj na buforze Integerow o rozmiarze 5.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericArrayCreationHelperClass {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase pomocnicza `ArrayFactory` z jedyna metoda statyczna
         * `<T> T[] create(Class<T> typ, int rozmiar)` uzywajaca
         * Array.newInstance. Uzyj jej do utworzenia tablicy Double[10] i
         * String[3], wypisujac dla obu getClass().getSimpleName() oraz
         * dlugosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SafeVsUnsafeVarargsSideBySide {
        /*
         * 🧪 Zadanie 22:
         * Napisz DWIE metody generyczne z varargs: bezpieczna (oznaczona
         * @SafeVarargs, tylko odczytuje elementy) oraz "podejrzana"
         * (BEZ @SafeVarargs, bo zwraca surowa tablice varargs na zewnatrz
         * jako Object[] - opisz w komentarzu dlaczego to niebezpieczne).
         * Przetestuj obie na tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TypeTokenPatternForGenericClassLiteral {
        /*
         * 🧪 Zadanie 23:
         * W komentarzu wyjasnij, dlaczego `List<String>.class` NIE
         * kompiluje sie (erasure - nie istnieje osobny obiekt Class dla
         * kazdej parametryzacji). Zademonstruj (dzialajacy kod) uzycie
         * `List.class` (surowy typ) jako jedynej dostepnej alternatywy i
         * wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BridgeMethodWithGenericInheritanceHierarchy {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj generyczna klase bazowa `Kontener<T> { T pobierz() { ...
         * } }` oraz podklase `KontenerTekstu extends Kontener<String>`
         * nadpisujaca pobierz() zwracajac String. Przez refleksje wypisz
         * WSZYSTKIE metody pobierz() widoczne na KontenerTekstu.class (w
         * tym bridge method) wraz z ich typami zwracanymi i isBridge().
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_HeapPollutionRealDemoWithSuppressedWarning {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode generyczna z varargs BEZ @SafeVarargs, ktora
         * celowo (dla celow dydaktycznych, w bloku otoczonym komentarzem
         * ostrzegawczym) wstawia do tablicy varargs element niewlasciwego
         * typu przez rzutowanie na Object[]. Wywolaj ja i zlap
         * ClassCastException w try-catch u WYWOLUJACEGO, wypisujac jego
         * komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_GenericMethodOverloadErasureCollision {
        /*
         * 🧪 Zadanie 26:
         * W komentarzu zapisz dwie metody generyczne o tej samej nazwie w
         * jednej klasie: `void proces(List<String> lista)` i `void
         * proces(List<Integer> lista)` - wypisz (println) dokladny
         * komunikat bledu kompilacji "erasure of method ... is the same as
         * ..." i wyjasnij dlaczego erasure na to nie pozwala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SafeVarargsOnConstructor {
        /*
         * 🧪 Zadanie 27:
         * Napisz klase generyczna `Zestaw<T>` z konstruktorem varargs
         * `Zestaw(T... elementy)` oznaczonym @SafeVarargs (konstruktory sa
         * jednym z dozwolonych miejsc), kopiujaca elementy do wewnetrznej
         * listy. Przetestuj tworzac Zestaw<String> z 4 elementami i
         * wypisujac jego zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ArrayNewInstanceMultiDimensional {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode generyczna `<T> T[][] stworzTablice2D(Class<T> typ,
         * int wiersze, int kolumny)` uzywajaca
         * `Array.newInstance(typ, wiersze, kolumny)`. Utworz nia tablice
         * Integer[3][4], wypisz jej getClass().getSimpleName() oraz wymiary
         * (length obu poziomow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RuntimeTypeCheckerUtilityClass {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj klase narzedziowa `TypeChecker` z metoda generyczna
         * `<T> List<T> filterByType(List<?> lista, Class<T> typ)`, ktora
         * zwraca TYLKO elementy zgodne z typem (przez typ.isInstance(...) i
         * typ.cast(...)) - obejscie braku `instanceof List<T>` z poziomu
         * calej listy. Przetestuj na liscie mieszajacej String i Integer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullErasureCaseStudySummary {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne studium przypadku "bezpieczny generyczny
         * bufor kolowy": klasa `RingBuffer<T>` z wewnetrzna tablica
         * `Object[]` (erasure workaround), metodami add(T) i T get(int),
         * konstruktorem varargs oznaczonym @SafeVarargs oraz metoda
         * statyczna fabrykujaca `<T> RingBuffer<T> of(Class<T> typ, int
         * pojemnosc)` korzystajaca z Array.newInstance TYLKO do
         * zadeklarowania rozmiaru (wewnetrznie i tak Object[]). Przetestuj
         * pelny cykl dodawania i odczytu na buforze Stringow.
         */
        public static void main(String[] args) { }
    }
}
