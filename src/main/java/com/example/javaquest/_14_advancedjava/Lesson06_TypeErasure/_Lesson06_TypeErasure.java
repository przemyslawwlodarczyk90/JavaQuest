package com.example.javaquest._14_advancedjava.Lesson06_TypeErasure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class _Lesson06_TypeErasure {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: TYPE ERASURE (WYMAZYWANIE TYPOW) ===");

        /*
         * ============================================================
         * 📦 CZYM JEST TYPE ERASURE
         * ============================================================
         * - Generyki w Javie sa zaimplementowane przez mechanizm zwany
         *   TYPE ERASURE (wymazywanie typow): informacja o parametrach
         *   typu (`<T>`, `<String>`, `<Integer>`...) istnieje TYLKO na
         *   etapie KOMPILACJI - kompilator uzywa jej do sprawdzania
         *   poprawnosci typow i wstawiania niewidocznych rzutowan.
         * - Po kompilacji, w wygenerowanym pliku `.class` (a wiec i w
         *   trakcie dzialania programu, w RUNTIME), CALA ta informacja
         *   znika ("jest wymazywana") - parametr typu `T` zostaje
         *   zastapiony jego OGRANICZENIEM (`Object`, jesli `T` jest
         *   nieograniczone, albo pierwszym typem z `extends`, jesli
         *   jest ograniczone), a np. `List<String>` staje sie po prostu
         *   `List`.
         * - Powod historyczny: generyki dodano w Javie 5 (2004), a JVM
         *   i cala biblioteka standardowa musialy zostac WSTECZNIE
         *   KOMPATYBILNE ze starym kodem sprzed generykow (surowe typy,
         *   `List` bez `<>`). Erasure pozwolil to osiagnac bez zmiany
         *   formatu pliku `.class` ani samej maszyny wirtualnej.
         */
        System.out.println("\nType erasure: typy generyczne istnieja tylko przy kompilacji, znikaja w runtime.");

        /*
         * ============================================================
         * 🔹 DOWOD: list1.getClass() == list2.getClass()
         * ============================================================
         * - Najprostszy, namacalny dowod na erasure: dwie listy o
         *   ROZNYCH parametrach typu (`List<String>` i `List<Integer>`)
         *   maja w runtime DOKLADNIE TEN SAM obiekt `Class` - bo JVM w
         *   ogole nie wie, ze kiedykolwiek istnialy jakies `String` czy
         *   `Integer` w ich sygnaturze.
         */
        demonstrateGetClassEquality();

        /*
         * ============================================================
         * 🔍 KONSEKWENCJA 1: NIE MOZNA ZROBIC `new T()`
         * ============================================================
         * - Skoro w runtime `T` nie istnieje jako konkretny typ (zostal
         *   wymazany), kompilator NIE POZWALA napisac `new T()` wewnatrz
         *   metody/klasy generycznej - fizycznie nie ma jak wygenerowac
         *   bajtkodu tworzacego instancje typu, ktory zniknie przed
         *   uruchomieniem.
         * - Blad kompilacji: "Type parameter 'T' cannot be instantiated
         *   directly".
         * - Obejscie: przekazac "fabryke" (np. `Supplier<T>` lub obiekt
         *   `Class<T>`) i to NIA utworzyc instancje - wtedy to
         *   WYWOLUJACY dostarcza konkretny typ w runtime.
         */
        demonstrateNoNewTWorkaround();

        /*
         * ============================================================
         * 🔍 KONSEKWENCJA 2: NIE MOZNA ZROBIC `new T[10]`
         * ============================================================
         * - Analogicznie nie da sie utworzyc tablicy generycznego typu:
         *
         *     T[] tablica = new T[10]; // BLAD KOMPILACJI:
         *     // generic array creation
         *
         * - Powod jest ten sam co przy `new T()`, ale jest tu dodatkowy
         *   niuans zwiazany z tablicami: tablice w Javie SA
         *   KOWARIANTNE i PAMIETAJA swoj typ w runtime (partz Lekcja 5)
         *   - gdyby JVM pozwolil utworzyc `T[]` bez znajomosci `T`, caly
         *   mechanizm sprawdzania `ArrayStoreException` przy zapisie
         *   przestalby dzialac, bo tablica "nie wiedzialaby", czego
         *   pilnowac.
         * - Obejscie 1 (klasyczne, uzywane m.in. w kodzie JDK): utworzyc
         *   `Object[]` i zrzutowac je na `T[]` z jawnym
         *   `@SuppressWarnings("unchecked")` - dziala, bo w runtime i tak
         *   `T[]` to bedzie po prostu `Object[]`.
         * - Obejscie 2 (bezpieczniejsze, gdy naprawde potrzebujesz
         *   WLASCIWEGO typu tablicy w runtime): `Array.newInstance(Class<T>
         *   typ, int rozmiar)` z pakietu `java.lang.reflect` - wymaga
         *   przekazania obiektu `Class<T>` (bo tylko on niesie
         *   informacje o typie w runtime), ale tworzy tablice
         *   WLASCIWEGO typu, a nie zamaskowany `Object[]`.
         */
        demonstrateGenericArrayWorkarounds();

        /*
         * ============================================================
         * 🔍 KONSEKWENCJA 3: NIE MOZNA ZROBIC `instanceof List<String>`
         * ============================================================
         * - `instanceof` sprawdza typ w RUNTIME - a w runtime, po
         *   wymazaniu, `List<String>` i `List<Integer>` to ten sam typ
         *   `List`. Kompilator wie o tym i od razu blokuje taki zapis:
         *
         *     if (obiekt instanceof List<String>) { } // BLAD KOMPILACJI:
         *     // illegal generic type for instanceof
         *
         * - Mozna sprawdzic TYLKO surowy (raw) typ:
         *   `obiekt instanceof List` - co odpowiada na pytanie "czy to
         *   jest jakas Lista", ale NIE "czy to jest Lista Stringow".
         * - Java 16+ wprowadzila pattern matching dla instanceof
         *   (`obiekt instanceof List<?> lista`), ale to wciaz sprawdza
         *   tylko surowy typ `List` - `<?>` (wildcard nieograniczony)
         *   jest jedynym parametrem typu, jaki wolno tu napisac, bo nie
         *   wymaga sprawdzenia niczego w runtime.
         */
        demonstrateInstanceofLimitation();

        /*
         * ============================================================
         * 🔍 BRIDGE METHODS (METODY MOSTKUJACE) - KROTKO
         * ============================================================
         * - Gdy klasa generyczna jest implementowana/rozszerzana z
         *   BARDZIEJ SZCZEGOLOWYM typem (np. `Comparable<T>` implementowane
         *   jako `Comparable<String>`), kompilator generuje DODATKOWA,
         *   NIEWIDOCZNA w kodzie zrodlowym metode "mostkujaca" (bridge
         *   method), ktora deleguje wywolanie do wlasciwej implementacji.
         * - Powod: po erasure, `Comparable<T>.compareTo(T)` staje sie
         *   `compareTo(Object)` na poziomie bajtkodu (bo `T` bez
         *   ograniczen wymazuje sie do `Object`) - ale klasa
         *   implementujaca `Comparable<String>` chce miec (i ma w
         *   zrodle) metode `compareTo(String)`. Zeby JVM (ktora
         *   dziedziczy/nadpisuje metody na podstawie SYGNATURY bajtkodu,
         *   a nie kodu zrodlowego) poprawnie widziala nadpisanie,
         *   kompilator dokleja dodatkowa metode `compareTo(Object)`,
         *   ktora tylko rzutuje argument na `String` i deleguje do
         *   `compareTo(String)`.
         * - Na co dzien programista NIGDY nie pisze bridge methods
         *   recznie ani nawet ich nie widzi w zrodle - generuje je
         *   WYLACZNIE kompilator. Wystarczy wiedziec, ze istnieja i ze
         *   to jedna z "cichych" konsekwencji erasure (mozna je
         *   zobaczyc np. przez `getClass().getMethods()` - beda tam
         *   metody oznaczone flaga `isBridge() == true`).
         */
        System.out.println("\nBridge methods: kompilator dokleja ukryte metody, zeby polimorfizm dzialal mimo erasure (szczegoly w komentarzu).");

        /*
         * ============================================================
         * 🔍 HEAP POLLUTION Z VARARGS TYPU GENERYCZNEGO
         * ============================================================
         * - Varargs generycznego typu (np. `List<String>... listy`) sa
         *   pod spodem zwykla tablica (`List<String>[]`) - a jak juz
         *   wiemy, tablicy generycznego typu NIE MOZNA utworzyc wprost.
         *   Kompilator i tak to robi za kulisami (bo varargs tego
         *   wymaga), ale oznacza to niebezpieczna tablice, ktora w
         *   runtime jest po prostu `Object[]` udajaca `List[]`.
         * - "Heap pollution" (zanieczyszczenie sterty) to sytuacja, gdy
         *   zmienna zadeklarowana z konkretnym typem generycznym
         *   wskazuje NA OBIEKT NIEZGODNEGO typu - moze sie to zdarzyc
         *   WLASNIE przez taka tablice varargs, jesli metoda w swoim
         *   ciele wstawi do niej element NIEWLASCIWEGO typu (co wolno
         *   zrobic, bo w runtime to tylko `Object[]`).
         */
        demonstrateHeapPollution();

        /*
         * ============================================================
         * 🔍 @SafeVarargs - JAK SIE PRZED TYM CHRONIC
         * ============================================================
         * - Adnotacja `@SafeVarargs` na metodzie z varargs generycznego
         *   typu to OBIETNICA programisty dana kompilatorowi: "sprawdzilem
         *   recznie, ze ta metoda NIE robi nic niebezpiecznego z tablica
         *   varargs (nie zapisuje do niej niewlasciwych typow, nie
         *   udostepnia jej na zewnatrz)" - w zamian kompilator przestaje
         *   generowac ostrzezenie "unchecked generic array creation" u
         *   KAZDEGO WYWOLUJACEGO te metode.
         * - `@SafeVarargs` MOZNA umiescic tylko na metodach, ktore i tak
         *   nie moga byc nadpisane w niebezpieczny sposob: `static`,
         *   `final`, `private` (od Javy 9) oraz na konstruktorach -
         *   szczegoly reguł uzycia w Lekcji 7.
         */
        System.out.println("\n@SafeVarargs: obietnica programisty, ze varargs generycznego typu sa uzywane bezpiecznie (szczegoly w Lekcji 7).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Type erasure: parametry typu generycznego istnieja TYLKO
         *   przy kompilacji, w runtime znikaja (zastapione ograniczeniem
         *   lub Object) - dlatego `List<String>` i `List<Integer>` maja
         *   ten sam `Class` w runtime.
         * - Konsekwencje: nie da sie `new T()` ani `new T[10]` wprost
         *   (obejscia: fabryka/`Supplier<T>` dla obiektow, rzutowany
         *   `Object[]` z `@SuppressWarnings` lub `Array.newInstance` dla
         *   tablic), nie da sie `instanceof List<String>` (tylko raw
         *   `instanceof List`).
         * - Bridge methods to niewidoczne metody generowane przez
         *   kompilator, zeby polimorfizm dzialal mimo erasure - nigdy
         *   nie pisze sie ich recznie.
         * - Varargs generycznego typu moga prowadzic do heap pollution -
         *   `@SafeVarargs` to obietnica bezpieczenstwa, ktora wycisza
         *   ostrzezenia u wywolujacych (pelne zasady w Lekcji 7).
         * - Kolejna lekcja: dobre praktyki i pulapki generykow - raw
         *   types, kiedy `@SuppressWarnings("unchecked")` jest naprawde
         *   bezpieczny, oraz zamkniecie calego bloku Generics.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void demonstrateGetClassEquality() {
        System.out.println("\n=== list1.getClass() == list2.getClass() ===");

        List<String> listaStringow = new ArrayList<>();
        List<Integer> listaLiczb = new ArrayList<>();

        System.out.println("listaStringow.getClass(): " + listaStringow.getClass());
        System.out.println("listaLiczb.getClass():    " + listaLiczb.getClass());
        System.out.println("Czy to ten sam Class? -> " + (listaStringow.getClass() == listaLiczb.getClass()));
    }

    // Obejscie braku "new T()" - fabryka dostarczona przez wywolujacego.
    private static <T> T createInstance(java.util.function.Supplier<T> fabryka) {
        // return new T(); // BLAD KOMPILACJI - T nie istnieje w runtime
        return fabryka.get();
    }

    private static void demonstrateNoNewTWorkaround() {
        System.out.println("\n=== new T() jest niemozliwe - obejscie przez Supplier<T> ===");

        String nowyString = createInstance(String::new);
        ArrayList<Integer> nowaLista = createInstance(ArrayList::new);

        System.out.println("Utworzony przez fabryke String: '" + nowyString + "'");
        System.out.println("Utworzona przez fabryke lista: " + nowaLista);
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] createArrayUnsafe(int rozmiar) {
        // T[] tablica = new T[rozmiar]; // BLAD KOMPILACJI - generic array creation
        return (T[]) new Object[rozmiar]; // dziala, bo w runtime T[] jest i tak Object[]
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] createArraySafe(Class<T> typ, int rozmiar) {
        return (T[]) Array.newInstance(typ, rozmiar); // tworzy tablice WLASCIWEGO typu w runtime
    }

    private static void demonstrateGenericArrayWorkarounds() {
        System.out.println("\n=== new T[10] jest niemozliwe - dwa obejscia ===");

        String[] rzutowanaZObjectArray = createArrayUnsafe(3);
        System.out.println("Obejscie 1 (rzutowany Object[]) - klasa tablicy w runtime: "
                + rzutowanaZObjectArray.getClass().getSimpleName());

        String[] wlasciwaTablica = createArraySafe(String.class, 3);
        System.out.println("Obejscie 2 (Array.newInstance) - klasa tablicy w runtime: "
                + wlasciwaTablica.getClass().getSimpleName());
    }

    private static void demonstrateInstanceofLimitation() {
        System.out.println("\n=== instanceof List<String> jest niemozliwe ===");

        List<String> listaStringow = List.of("a", "b", "c");
        Object obiekt = listaStringow;

        // if (obiekt instanceof List<String>) { } // BLAD KOMPILACJI: illegal generic type for instanceof

        if (obiekt instanceof List<?> lista) {
            System.out.println("Sprawdzenie raw typu 'instanceof List<?>' dziala - rozmiar: " + lista.size());
        }
        System.out.println("Nie da sie sprawdzic w runtime, CZY konkretnie List<String> - erasure wymazal ta informacje.");
    }

    // Metoda niebezpieczna - CELOWO pokazuje heap pollution (bez @SafeVarargs, na wlasny uzytek dydaktyczny).
    @SafeVarargs
    private static <T> List<T> listOfSafe(T... elementy) {
        // Bezpieczna implementacja: tablica varargs nie jest ani modyfikowana, ani udostepniana na zewnatrz.
        List<T> wynik = new ArrayList<>();
        for (T element : elementy) {
            wynik.add(element);
        }
        return wynik;
    }

    private static void unsafeVarargsExampleExplanation() {
        // PRZYKLAD KONCEPCYJNY (nieuruchamiany), zeby nie psuc bezpieczenstwa demo:
        //
        // static <T> T[] niebezpieczna(T... elementy) {
        //     Object[] tablica = elementy;   // varargs to pod spodem Object[]
        //     tablica[0] = "zepsute";        // wstawiamy String do "T[]" - HEAP POLLUTION
        //     return (T[]) tablica;          // ClassCastException wyleci u WYWOLUJACEGO, nie tutaj!
        // }
        //
        // static void demo() {
        //     Integer[] liczby = niebezpieczna(1, 2, 3); // rzuci ClassCastException w tej linii
        // }
        //
        // Blad ujawnia sie DALEKO od miejsca, gdzie faktycznie powstal - dlatego heap pollution
        // jest szczegolnie podstepny. @SafeVarargs na "niebezpieczna" byloby KLAMSTWEM.
    }

    private static void demonstrateHeapPollution() {
        System.out.println("\n=== Heap pollution z varargs generycznego typu ===");

        List<String> lista1 = List.of("a", "b");
        List<String> lista2 = List.of("c");

        List<List<String>> polaczone = listOfSafe(lista1, lista2);
        System.out.println("Bezpieczne uzycie varargs generycznych (@SafeVarargs): " + polaczone);

        unsafeVarargsExampleExplanation();
        System.out.println("Niebezpieczny przyklad (heap pollution) opisany jako komentarz - patrz kod metody.");
    }
}
