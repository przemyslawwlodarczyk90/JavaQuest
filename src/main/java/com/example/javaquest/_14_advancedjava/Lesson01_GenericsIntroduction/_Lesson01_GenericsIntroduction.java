package com.example.javaquest._14_advancedjava.Lesson01_GenericsIntroduction;

import java.util.ArrayList;
import java.util.List;

public class _Lesson01_GenericsIntroduction {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: WPROWADZENIE DO GENERYKOW ===");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAL: _14_advancedjava - ZAAWANSOWANE MECHANIZMY JEZYKA JAVA
         * ============================================================
         * - Do tej pory poznales podstawy Javy, OOP, kolekcje, I/O, wielowatkowosc,
         *   siec, SQL/JDBC/DAO, Hibernate, narzedzia budowania i biblioteki.
         *   Ten rozdzial wraca do SAMEGO JEZYKA - jego bardziej zaawansowanych
         *   mechanizmow, ktore w praktyce widzisz codziennie w kodzie, ale
         *   dotad nie mialy osobnej, dogłebnej lekcji.
         * - Mapa drogowa calego rozdzialu _14_advancedjava (30 lekcji):
         *     1. Generyki (Lekcje 1-7) - typy parametryzowane, ograniczenia,
         *        wildcardy, wariancja (PECS), type erasure, dobre praktyki.
         *     2. Programowanie funkcyjne (Lekcje 8-11) - interfejsy funkcyjne,
         *        wyrazenia lambda, referencje do metod, wbudowane interfejsy
         *        z java.util.function.
         *     3. Adnotacje i refleksja (Lekcje 12-18) - wlasne adnotacje,
         *        retencja/przetwarzanie, podstawy Reflection API, dynamiczne
         *        proxy, MethodHandles.
         *     4. Nowoczesna Java (Lekcje 19-23) - klasy sealed, pattern
         *        matching (instanceof, switch, rekordy), wyrazenia switch,
         *        var i wnioskowanie typow.
         *     5. Niezmiennosc i moduly (Lekcje 24-28) - immutability, kopiowanie
         *        obronne, ServiceLoader/SPI, podstawy i zaawansowane JPMS.
         *     6. Podsumowanie (Lekcje 29-30) - dobre praktyki i projekt
         *        kapstonowy laczacy wszystkie poznane mechanizmy.
         * - Zaczynamy od GENERYKOW (generics) - mechanizmu, ktory poznajesz
         *   uzywajac List<String> czy Map<K, V> od pierwszej lekcji o
         *   kolekcjach, ale nigdy nie napisales WLASNEJ klasy generycznej.
         *   Ta lekcja to nadrabia od podstaw.
         */
        System.out.println("\nRozdzial _14_advancedjava: generyki -> programowanie funkcyjne -> adnotacje/refleksja");
        System.out.println("-> nowoczesna Java (sealed/pattern matching) -> niezmiennosc/moduly -> capstone.");

        /*
         * ============================================================
         * 🔹 PROBLEM: SWIAT PRZED GENERYKAMI (JAVA < 5, "RAW TYPES")
         * ============================================================
         * - Przed Java 5 (2004) kolekcje (np. ArrayList) przechowywaly
         *   WSZYSTKO jako Object - nie bylo sposobu, by powiedziec "ta lista
         *   trzyma tylko Stringi".
         * - Skutek: mozna byla przypadkowo wlozyc do listy element ZLEGO typu.
         *   Kompilator tego NIE wylapie - bo dla niego wszystko to Object.
         * - Blad ujawnia sie dopiero w RUNTIME, przy odczycie - jako
         *   ClassCastException, czesto daleko od miejsca, gdzie zly element
         *   zostal wlozony (co utrudnia debugowanie).
         */
        demonstrateRawTypeProblem();

        /*
         * ============================================================
         * 🔍 ROZWIAZANIE: GENERYKI (JAVA 5+) - BLAD WYLAPANY W COMPILE TIME
         * ============================================================
         * - Generyki (generics) pozwalaja PARAMETRYZOWAC typ klasy/interfejsu/
         *   metody - np. List<String> to "lista, ktora trzyma TYLKO Stringi".
         * - Teraz proba dodania elementu zlego typu to BLAD KOMPILACJI, nie
         *   runtime - najlepszy mozliwy moment na zlapanie bledu (im wczesniej
         *   w cyklu zycia kodu, tym taniej naprawic).
         * - Dodatkowa korzysc: NIE trzeba juz rzutowac (cast) przy odczycie -
         *   kompilator "wie", ze element listy to String, wiec
         *   list.get(0).length() dziala bez (String) list.get(0).
         */
        demonstrateGenericTypeSafety();

        /*
         * ============================================================
         * 🔹 SKLADNIA: PARAMETR TYPU <T> I KONWENCJE NAZEWNICTWA
         * ============================================================
         * - Parametr typu (type parameter) to "zmienna", ktora reprezentuje
         *   TYP zamiast wartosci - deklarowana w ostrych nawiasach <...>
         *   zaraz po nazwie klasy/interfejsu/metody.
         * - Konwencja nazewnicza w Javie (jedna wielka litera, nie jest
         *   wymogiem jezyka, ale powszechnie przestrzegana):
         *     * T - Type (typ ogolny, gdy nie pasuje nic bardziej konkretnego)
         *     * E - Element (element kolekcji, np. List<E>)
         *     * K - Key (klucz, np. Map<K, V>)
         *     * V - Value (wartosc, np. Map<K, V>)
         *     * R - Result (typ wyniku, np. w funkcjach transformujacych)
         *     * N - Number (typ liczbowy)
         *     * S, U - kolejne typy pomocnicze, gdy T/E/K/V/R juz zajete
         * - Nazwy sa TYLKO konwencja - kompilator zaakceptuje dowolna nazwe
         *   (np. <Foo>), ale trzymanie sie konwencji ulatwia czytanie kodu
         *   innym programistom (i Tobie za pol roku).
         */
        System.out.println("\nKonwencje nazw: T=Type, E=Element, K=Key, V=Value, R=Result, N=Number");

        /*
         * ============================================================
         * 📌 PIERWSZA WLASNA KLASA GENERYCZNA: Box<T>
         * ============================================================
         * - Box<T> to najprostszy mozliwy "pojemnik" - trzyma JEDNA wartosc
         *   dowolnego typu T, ustalanego w momencie UZYCIA klasy (nie
         *   definicji).
         * - Zauwaz: pole `value` ma typ T - kompilator zna dokladny typ
         *   dopiero, gdy ktos napisze np. `new Box<String>()` albo (od Javy 7)
         *   `new Box<>()` z operatorem diamentowym (diamond operator) -
         *   kompilator WYWNIOSKUJE typ z kontekstu (np. z deklaracji zmiennej).
         * - Ta sama klasa Box<T> moze byc uzyta dla String, Integer, wlasnej
         *   klasy - bez pisania osobnej wersji dla kazdego typu (to jest
         *   sedno generyków: PISZESZ RAZ, UZYWASZ dla wielu typow BEZPIECZNIE).
         */
        demonstrateBoxClass();

        /*
         * ============================================================
         * 🔍 CO GENERYKI DAJA W PRAKTYCE - PODSUMOWANIE KORZYSCI
         * ============================================================
         * - Bezpieczenstwo typow w compile time (type safety) - bledy
         *   zlapane przez kompilator, nie na produkcji.
         * - Brak jawnego rzutowania (cast) przy odczycie z kolekcji/klas
         *   generycznych - krotszy, czytelniejszy kod.
         * - Reuzywalnosc kodu - jedna klasa/metoda generyczna zastepuje wiele
         *   wersji "po typie" (np. IntBox, StringBox, ...).
         * - Samodokumentujacy sie kod - sygnatura List<Employee> mowi wiecej
         *   niz surowa List.
         * - W kolejnych lekcjach zobaczysz, ze generyki to nie tylko
         *   kolekcje - to jeden z fundamentow tego, jak zaprojektowane jest
         *   nowoczesne API Javy (Optional<T>, Stream<T>, Comparable<T>,
         *   CompletableFuture<T> i wiele innych).
         */
        printGenericsBenefitsSummary();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Generyki (od Javy 5) pozwalaja parametryzowac typy - bledy
         *   niezgodnosci typow sa wylapywane w COMPILE TIME zamiast RUNTIME
         *   (ClassCastException).
         * - Skladnia: <T> po nazwie klasy/interfejsu/metody, konwencje
         *   T/E/K/V/R/N.
         * - Napisales pierwsza wlasna klase generyczna: Box<T> z get/set.
         * - W kolejnej lekcji (Lesson02): pelne klasy generyczne z WIELOMA
         *   parametrami typu (Pair<K, V>), metody generyczne, wnioskowanie
         *   typow (diamond operator) i interfejsy generyczne.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void demonstrateRawTypeProblem() {
        System.out.println("\n=== PROBLEM: RAW TYPE (SWIAT SPRZED GENERYKOW) ===");

        List rawList = new ArrayList(); // raw type - brak informacji o typie elementu
        rawList.add("Ala");
        rawList.add("Kot");
        rawList.add(42); // KOMPILATOR TO PRZEPUSCI - rawList "nie wie", ze ma trzymac Stringi

        System.out.println("Dodano do surowej (raw) listy: \"Ala\", \"Kot\", 42 (Integer)");

        try {
            for (Object element : rawList) {
                String text = (String) element; // jawny cast wymagany dla raw type
                System.out.println(" element jako String: " + text);
            }
        } catch (ClassCastException e) {
            System.out.println(" BLAD W RUNTIME: " + e + " (kompilator nie ostrzegl!)");
        }
    }

    private static void demonstrateGenericTypeSafety() {
        System.out.println("\n=== ROZWIAZANIE: LISTA GENERYCZNA List<String> ===");

        List<String> names = new ArrayList<>();
        names.add("Ala");
        names.add("Kot");
        // names.add(42); // <-- TO NIE SKOMPILUJE SIE! Integer nie pasuje do String.

        System.out.println("List<String> pozwala dodac TYLKO String - proba names.add(42) to blad kompilacji.");
        for (String name : names) {
            // BRAK rzutowania - kompilator wie, ze name to String
            System.out.println(" dlugosc \"" + name + "\" = " + name.length());
        }
    }

    private static void demonstrateBoxClass() {
        System.out.println("\n=== WLASNA KLASA GENERYCZNA: Box<T> ===");

        Box<String> stringBox = new Box<>("Zawartosc tekstowa");
        Box<Integer> intBox = new Box<>(123);

        System.out.println("stringBox.get() = " + stringBox.get());
        System.out.println("intBox.get() = " + intBox.get());

        stringBox.set("Nowa zawartosc");
        System.out.println("po stringBox.set(...): " + stringBox.get());

        System.out.println("Ta sama klasa Box<T> obsluzyla String i Integer - bez pisania 2 osobnych klas.");
    }

    private static void printGenericsBenefitsSummary() {
        System.out.println("\n=== KORZYSCI Z GENERYKOW ===");
        List<String> benefits = List.of(
                "Bledy typow wylapane w compile time, nie w runtime",
                "Brak jawnego rzutowania (cast) przy odczycie",
                "Reuzywalnosc - jedna klasa/metoda dla wielu typow",
                "Samodokumentujacy sie kod (List<Employee> mowi wiecej niz List)"
        );
        benefits.forEach(b -> System.out.println(" + " + b));
    }

    /**
     * Najprostsza mozliwa klasa generyczna - "pojemnik" na jedna wartosc
     * dowolnego typu T, ustalanego przy tworzeniu obiektu.
     */
    static class Box<T> {
        private T value;

        Box(T value) {
            this.value = value;
        }

        T get() {
            return value;
        }

        void set(T value) {
            this.value = value;
        }
    }
}
