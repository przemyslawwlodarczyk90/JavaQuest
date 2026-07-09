package com.example.javaquest._14_advancedjava.Lesson12_Annotations;

import java.util.ArrayList;
import java.util.List;

public class _Lesson12_Annotations {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: ADNOTACJE (ANNOTATIONS) - WSTĘP ===\n");

        /*
         * ============================================================
         * 📦 CZYM SĄ ADNOTACJE? (I CZYM NIE SĄ)
         * ============================================================
         * Adnotacja (annotation) to METADANE dołączone do elementu kodu -
         * klasy, metody, pola, parametru, zmiennej lokalnej, a nawet typu
         * (od Javy 8). Składniowo wygląda jak "@Nazwa", np.:
         *
         *   @Override
         *   public String toString() { ... }
         *
         * WAŻNE: adnotacja to NIE komentarz. Komentarz (// albo /* ... *\/)
         * jest wyłącznie dla człowieka - kompilator go całkowicie ignoruje
         * i znika bez śladu po kompilacji. Adnotacja jest PEŁNOPRAWNYM
         * elementem języka - kompilator ją WIDZI i może na jej podstawie:
         *  - sprawdzić coś w czasie kompilacji (np. @Override każe
         *    sprawdzić, czy metoda faktycznie nadpisuje metodę z nadklasy/
         *    interfejsu - błąd kompilacji, jeśli nie),
         *  - wygenerować ostrzeżenie (np. @Deprecated),
         *  - zostać zapisana w pliku .class i odczytana w RUNTIME przez
         *    refleksję (poznamy dokładnie w Lekcji 14 i 15) - na tym
         *    opierają się frameworki takie jak Lombok, MapStruct
         *    (poznane w rozdziale _13_libraries), Hibernate (@Entity,
         *    @Column w rozdziale _12_hibernate) czy JUnit (@Test).
         *
         * Krótko: adnotacja to informacja DLA NARZĘDZI (kompilatora,
         * frameworków, IDE) zapisana WEWNĄTRZ kodu, w formalnej,
         * przetwarzalnej maszynowo postaci - w odróżnieniu od komentarza,
         * który jest informacją WYŁĄCZNIE dla człowieka.
         */

        /*
         * ============================================================
         * 🔹 TRZY POSTACIE ADNOTACJI
         * ============================================================
         * Adnotacje występują w trzech "kształtach", w zależności od
         * tego, ile elementów (atrybutów) deklarują i ile z nich
         * podajemy przy użyciu:
         *
         * 1) MARKER ANNOTATION (adnotacja-znacznik) - BEZ żadnych
         *    elementów, sama jej OBECNOŚĆ jest informacją:
         *      @Override
         *      @Deprecated
         *
         * 2) SINGLE-VALUE ANNOTATION - dokładnie JEDEN element o nazwie
         *    "value" - można go podać BEZ nazwy, samą wartością:
         *      @SuppressWarnings("unchecked")
         *      // to skrót od: @SuppressWarnings(value = "unchecked")
         *
         * 3) FULL ANNOTATION (pełna postać) - WIELE elementów, każdy
         *    podawany z nazwą "nazwa = wartość", oddzielone przecinkami:
         *      @Deprecated(since = "2.0", forRemoval = true)
         *
         * Do własnych adnotacji (Lekcja 13) wrócimy do tego rozróżnienia
         * przy definiowaniu, jakie elementy adnotacja ma mieć.
         */

        /*
         * ============================================================
         * 🔹 @Override - "ta metoda MA nadpisywać coś z rodzica"
         * ============================================================
         * @Override to MARKER ANNOTATION mówiąca kompilatorowi: "ta
         * metoda powinna nadpisywać metodę z nadklasy albo implementować
         * metodę interfejsu - jeśli tak NIE jest, zgłoś błąd kompilacji".
         *
         * Bez @Override literówka w nazwie metody (albo zła sygnatura)
         * to CICHY BŁĄD - kompilator myśli, że dopisujesz zupełnie NOWĄ
         * metodę, program się kompiluje, ale w runtime wywoła się
         * oryginalna metoda z Object/nadklasy zamiast Twojej.
         */
        Animal brokenDog = new BrokenDog();
        System.out.println("BrokenDog.toString() (literówka 'toStrign', BEZ @Override):");
        System.out.println("  " + brokenDog);
        // ^ To wypisze coś w stylu "BrokenDog@1b6d3586" (domyślny
        //   Object.toString()), a NIE "Hau hau!" - bo BrokenDog NIE
        //   nadpisał toString(), tylko dodał zupełnie nową metodę
        //   toStrign() (literówka). Kompilator się NIE poskarżył, bo
        //   bez @Override nie miał podstaw sądzić, że to miało być
        //   nadpisanie - zobacz klasę BrokenDog niżej w pliku.

        Animal correctDog = new CorrectDog();
        System.out.println("CorrectDog.toString() (z @Override, literówka wykryta by kompilator):");
        System.out.println("  " + correctDog);
        /*
         * Gdyby w CorrectDog była literówka "toStrign()" OZNACZONA
         * @Override, kompilacja zakończyłaby się błędem:
         *
         *   error: method does not override or implement a method from
         *   a supertype
         *
         * Właśnie dlatego DOBRA PRAKTYKA to: ZAWSZE oznaczaj @Override
         * każdą metodę, która ma nadpisywać/implementować coś z rodzica
         * - literówka wychodzi na etapie kompilacji, a nie dopiero gdy
         * ktoś zauważy dziwne zachowanie w runtime.
         */

        /*
         * ============================================================
         * 🔹 @Deprecated - "nie używaj tego, jest zamiennik"
         * ============================================================
         * @Deprecated oznacza element (klasę, metodę, pole) jako
         * PRZESTARZAŁY - kompilator wypisze OSTRZEŻENIE (nie błąd) przy
         * każdym jego użyciu. Ma dwa opcjonalne elementy (FULL ANNOTATION):
         *  - since (String)      - od której wersji element jest przestarzały,
         *  - forRemoval (boolean) - czy planowane jest USUNIĘCIE go w
         *                            przyszłości (domyślnie false).
         *
         *   @Deprecated(since = "2.1", forRemoval = true)
         *   public void oldMethod() { ... }
         *
         * Dobra praktyka: adnotację @Deprecated ZAWSZE łącz z komentarzem
         * Javadoc @deprecated (z małej litery, w /** ... *\/) wyjaśniającym
         * CZYM zastąpić przestarzały element - adnotacja mówi "nie używaj",
         * Javadoc mówi "użyj zamiast tego X".
         */
        LegacyCalculator calc = new LegacyCalculator();
        int sum = calc.add(3, 4); // wywołanie przestarzałej metody -
        // kompilator wypisze ostrzeżenie w rodzaju "add(int,int) in
        // LegacyCalculator has been deprecated and marked for removal"
        System.out.println("\nWynik z przestarzałej metody add(): " + sum
                + " (kompilator ostrzegł - zobacz output kompilacji / IDE)");

        /*
         * ============================================================
         * 🔹 @SuppressWarnings - "wiem co robię, ucisz to ostrzeżenie"
         * ============================================================
         * @SuppressWarnings to SINGLE-VALUE ANNOTATION (element "value"
         * typu String[]) - wycisza konkretne KATEGORIE ostrzeżeń
         * kompilatora dla oznaczonego elementu. Najczęstsze wartości:
         *  - "unchecked"  - niebezpieczne rzutowanie generyczne (patrz
         *                    Lekcja 06_TypeErasure - type erasure sprawia,
         *                    że część rzutowań generycznych kompilator
         *                    może tylko OSTRZEC, nie zweryfikować),
         *  - "deprecation" - użycie elementu oznaczonego @Deprecated,
         *  - "rawtypes"    - użycie surowego typu generycznego (np. List
         *                    zamiast List<String>).
         *
         * WAŻNE: @SuppressWarnings NIE naprawia problemu - tylko UKRYWA
         * ostrzeżenie. Używaj świadomie, tylko gdy NAPRAWDĘ wiesz, że
         * dany przypadek jest bezpieczny (np. rzutowanie generyczne
         * poprzedzone ręczną weryfikacją typu w runtime) - nadużywanie
         * @SuppressWarnings("unchecked") "na wszelki wypadek" chowa
         * PRAWDZIWE błędy.
         */
        @SuppressWarnings("unchecked")
        List<String> rawToTyped = (List<String>) (List<?>) new ArrayList<Object>();
        rawToTyped.add("bezpieczne, bo lista jest pusta i lokalna");
        System.out.println("\n@SuppressWarnings(\"unchecked\") wyciszył ostrzeżenie o rzutowaniu: "
                + rawToTyped);

        @SuppressWarnings("deprecation")
        int suppressedSum = calc.add(1, 1); // to samo wywołanie co wyżej,
        // ale TERAZ bez ostrzeżenia kompilatora - wyciszone jawnie
        System.out.println("Wynik z wyciszonym ostrzeżeniem @Deprecated: " + suppressedSum);

        // Można podać WIĘCEJ NIŻ JEDNĄ kategorię naraz - value to String[],
        // więc w nawiasach klamrowych:
        // @SuppressWarnings({"unchecked", "deprecation"})

        /*
         * ============================================================
         * 🔍 @FunctionalInterface - przypomnienie z Lekcji 08
         * ============================================================
         * @FunctionalInterface to MARKER ANNOTATION, którą poznaliśmy
         * już dokładnie w Lekcji 08_FunctionalInterfaces. Oznacza
         * interfejs jako mający DOKŁADNIE JEDNĄ metodę abstrakcyjną
         * (SAM - Single Abstract Method), czyli nadający się do
         * implementacji lambdą. Nie jest WYMAGANA (interfejs z jedną
         * metodą abstrakcyjną jest funkcyjny nawet bez niej), ale jeśli
         * jest obecna, kompilator PILNUJE tej reguły - dodanie drugiej
         * metody abstrakcyjnej do oznaczonego interfejsu to błąd
         * kompilacji, nie dopiero problem przy próbie użycia lambdy.
         */
        System.out.println("\n@FunctionalInterface - przypomnienie z Lekcji 08 (SAM = Single Abstract Method).");

        /*
         * ============================================================
         * 🔍 @SafeVarargs - przypomnienie z rozdziału o generykach
         * ============================================================
         * @SafeVarargs to kolejna MARKER ANNOTATION, poznana przy okazji
         * generyków (varargi generyczne, np. Lesson06_TypeErasure /
         * Lesson07_GenericsBestPracticesAndPitfalls) - można ją nałożyć
         * TYLKO na metody statyczne, final albo prywatne (Java 9+) z
         * parametrem varargs typu generycznego. Mówi kompilatorowi:
         * "obiecuję, że ta metoda NIE robi nic niebezpiecznego z tablicą
         * varargs (np. nie zapisuje do niej złego typu)" - dzięki temu
         * wywołujący NIE dostają ostrzeżenia "unchecked generic array
         * creation" przy każdym wywołaniu.
         */
        System.out.println("@SafeVarargs - przypomnienie z rozdziału o generykach (bezpieczne varargi generyczne).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Adnotacja to METADANE dla narzędzi (kompilator, IDE, biblioteki)
         *   zapisane wewnątrz kodu, formalnie przetwarzalne - w odróżnieniu
         *   od komentarza, który jest wyłącznie dla człowieka i znika po
         *   kompilacji.
         * - Trzy postaci: MARKER (@Override, bez elementów), SINGLE-VALUE
         *   (@SuppressWarnings("unchecked"), jeden element "value"), FULL
         *   (@Deprecated(since = "...", forRemoval = true), wiele nazwanych
         *   elementów).
         * - @Override - kompilator SPRAWDZA, że metoda faktycznie nadpisuje
         *   coś z rodzica; bez niej literówka w nazwie to cichy błąd
         *   (nowa metoda zamiast nadpisania).
         * - @Deprecated(since, forRemoval) - ostrzeżenie kompilatora przy
         *   użyciu; łącz z komentarzem Javadoc @deprecated wyjaśniającym
         *   zamiennik.
         * - @SuppressWarnings("unchecked"/"deprecation"/...) - wycisza
         *   KONKRETNE kategorie ostrzeżeń; używaj świadomie, nie "na
         *   wszelki wypadek".
         * - @FunctionalInterface i @SafeVarargs - poznane wcześniej
         *   (Lekcja 08 i rozdział o generykach) - też są zwykłymi
         *   marker-adnotacjami wbudowanymi w bibliotekę standardową.
         * - W następnej lekcji (13) nauczymy się definiować WŁASNE
         *   adnotacje (@interface) razem z meta-adnotacjami @Target,
         *   @Retention, @Documented, @Inherited i @Repeatable.
         */

        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    /** Bazowa klasa - deklaruje toString() do nadpisania przez podklasy. */
    static class Animal {
        @Override
        public String toString() {
            return "Jakieś zwierzę";
        }
    }

    /**
     * Podklasa z LITERÓWKĄ w nazwie metody ("toStrign" zamiast "toString")
     * i BEZ @Override - kompilator NIE ma jak wykryć pomyłki, bo z jego
     * punktu widzenia to po prostu nowa, dodatkowa metoda tej klasy.
     */
    static class BrokenDog extends Animal {
        // Celowa literówka - brak @Override sprawia, że to się KOMPILUJE,
        // mimo że autor najwyraźniej chciał nadpisać toString().
        public String toStrign() {
            return "Hau hau!";
        }
    }

    /** Ta sama logika, ale POPRAWNIE - z @Override i bez literówki. */
    static class CorrectDog extends Animal {
        @Override
        public String toString() {
            return "Hau hau!";
        }
    }

    /** Klasa z przestarzałą metodą - demonstracja @Deprecated. */
    static class LegacyCalculator {
        /**
         * Dodaje dwie liczby.
         *
         * @deprecated od wersji 2.1, zamiast tego użyj {@link #addSafely(int, int)}.
         */
        @Deprecated(since = "2.1", forRemoval = true)
        public int add(int a, int b) {
            return a + b;
        }

        public int addSafely(int a, int b) {
            return Math.addExact(a, b); // rzuca ArithmeticException przy przepełnieniu
        }
    }
}
