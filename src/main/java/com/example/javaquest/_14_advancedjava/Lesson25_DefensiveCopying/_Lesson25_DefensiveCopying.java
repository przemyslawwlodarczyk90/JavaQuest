package com.example.javaquest._14_advancedjava.Lesson25_DefensiveCopying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class _Lesson25_DefensiveCopying {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 25: KOPIOWANIE OBRONNE (DEFENSIVE COPYING) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE PROBLEMU Z Lesson24_Immutability
         * ============================================================
         * W poprzedniej lekcji `BrokenImmutablePerson` "wyglądała"
         * niemutowalnie (final pola, brak setterów), ale dało się zepsuć jej
         * stan na dwa sposoby:
         *   a) mutując oryginalną listę PRZED przekazaniem jej "na stałe" -
         *      a właściwie PO przekazaniu jej referencji do konstruktora,
         *   b) mutując listę zwróconą przez getter.
         * Ta lekcja pokazuje DOKŁADNĄ naprawę obu dziur: KOPIOWANIE OBRONNE
         * (defensive copying) - zarówno na WEJŚCIU (konstruktor), jak i na
         * WYJŚCIU (getter).
         */

        /*
         * ============================================================
         * 🔹 KOPIOWANIE OBRONNE NA WEJŚCIU (konstruktor)
         * ============================================================
         * Zamiast zapisywać CUDZĄ referencję wprost, konstruktor tworzy
         * WŁASNĄ, niezależną kopię przekazanych danych. Od tej chwili nic,
         * co wywołujący zrobi z ORYGINALNĄ kolekcją/tablicą, nie ma wpływu
         * na stan obiektu.
         */
        System.out.println("\n=== Naprawa (a): kopia na WEJSCIU, w konstruktorze ===");

        List<String> hobbyAli = new ArrayList<>(List.of("bieganie", "gotowanie"));
        FixedImmutablePerson ala = new FixedImmutablePerson("Ala", 31, hobbyAli);
        System.out.println("Hobby Ali (na starcie): " + ala.getHobbies());

        hobbyAli.add("wlamania do sieci"); // mutujemy oryginalna liste
        System.out.println("Po mutacji ORYGINALNEJ listy z zewnatrz: " + ala.getHobbies());
        System.out.println("-> zadnej zmiany! Konstruktor zrobil wlasna kopie (List.copyOf).");

        /*
         * ============================================================
         * 🔹 KOPIOWANIE OBRONNE NA WYJŚCIU (getter)
         * ============================================================
         * Nawet jeśli konstruktor jest bezpieczny, getter MUSI też zwracać
         * kopię (albo niemodyfikowalny widok) - w przeciwnym razie
         * wywołujący dostaje żywą referencję do wnętrza obiektu.
         */
        System.out.println("\n=== Naprawa (b): kopia/widok na WYJSCIU, w getterze ===");

        try {
            ala.getHobbies().add("cos nowego"); // proba mutacji przez getter
        } catch (UnsupportedOperationException e) {
            System.out.println("getHobbies().add(...) rzucil: " + e.getClass().getSimpleName());
            System.out.println("-> List.copyOf() zwraca NIEMODYFIKOWALNA liste - nawet przez getter nie da sie jej zepsuc.");
        }

        /*
         * ============================================================
         * 🔍 List.copyOf / Map.copyOf / Set.copyOf (JDK 10+) vs
         *     Collections.unmodifiableList (JDK 1.2)
         * ============================================================
         * To NIE są równoważne narzędzia, mimo pozornego podobieństwa:
         *
         * - `List.copyOf(source)` (i analogicznie Map.copyOf/Set.copyOf):
         *     tworzy PRAWDZIWĄ, NIEZALEŻNĄ kopię zawartości w nowej,
         *     wewnętrznie niemodyfikowalnej strukturze. Rzuca
         *     NullPointerException, jeśli `source` jest null LUB zawiera
         *     null. Zmiany w oryginale PO wywołaniu copyOf() NIE mają
         *     żadnego wpływu na kopię.
         *
         * - `Collections.unmodifiableList(source)`:
         *     zwraca tylko CIENKI WRAPPER (widok, "view") NAD oryginalną
         *     listą. Sam wrapper rzuca UnsupportedOperationException przy
         *     próbie add()/remove() NA NIM - ale to fałszywe poczucie
         *     bezpieczeństwa: jeśli ktokolwiek inny wciąż ma referencję do
         *     ORYGINALNEJ listy, może ją mutować, a te zmiany PRZEBIJAJĄ
         *     SIĘ przez widok, bo widok i oryginał to wciąż TA SAMA
         *     struktura danych w pamięci.
         */
        System.out.println("\n=== List.copyOf (kopia) vs Collections.unmodifiableList (widok) ===");

        List<String> mutowalnaOryginalna = new ArrayList<>(List.of("A", "B"));
        List<String> prawdziwaKopia = List.copyOf(mutowalnaOryginalna);
        List<String> tylkoWidok = Collections.unmodifiableList(mutowalnaOryginalna);

        System.out.println("Przed mutacja oryginalu:");
        System.out.println("  prawdziwaKopia = " + prawdziwaKopia);
        System.out.println("  tylkoWidok     = " + tylkoWidok);

        mutowalnaOryginalna.add("C"); // mutujemy TYLKO oryginal

        System.out.println("Po mutowalnaOryginalna.add(\"C\"):");
        System.out.println("  prawdziwaKopia = " + prawdziwaKopia + "  <- bez zmian, to niezalezna kopia");
        System.out.println("  tylkoWidok     = " + tylkoWidok + "  <- WIDAC \"C\"! to tylko widok na oryginal");

        try {
            tylkoWidok.add("D"); // sam widok wciaz nie da sie modyfikowac BEZPOSREDNIO
        } catch (UnsupportedOperationException e) {
            System.out.println("tylkoWidok.add(\"D\") rzucil " + e.getClass().getSimpleName()
                    + " - ale to NIE chroni przed mutacja przez oryginalna referencje (patrz wyzej).");
        }

        /*
         * ============================================================
         * 📌 WNIOSEK: WIDOK TO NIE TO SAMO CO NIEZALEŻNA KOPIA
         * ============================================================
         * `Collections.unmodifiableXxx` blokuje mutację TYLKO przez samą
         * referencję zwróconą przez to wywołanie. Jeśli chcesz PRAWDZIWEJ
         * gwarancji ("nikt, w żaden sposób, już tego nie zmieni") - potrzebujesz
         * kopii (List.copyOf/Map.copyOf/Set.copyOf albo ręcznego `new
         * ArrayList<>(source)` gdy z jakiegoś powodu potrzebujesz kolekcji
         * wciąż mutowalnej WEWNĄTRZ klasy, a niemodyfikowalnej na zewnątrz).
         */
        System.out.println("\nWidok (unmodifiableList) != kopia (copyOf). Widok NIE izoluje od zmian oryginalu.");

        /*
         * ============================================================
         * 🔍 PŁYTKA (SHALLOW) VS GŁĘBOKA (DEEP) KOPIA
         * ============================================================
         * `List.copyOf(lista)` kopiuje samą STRUKTURĘ listy (nową tablicę
         * referencji) - ale NIE klonuje poszczególnych ELEMENTÓW. Jeśli
         * elementy same są typu mutowalnego, wciąż można je zmienić przez
         * oryginalną referencję do elementu, mimo że lista "wokół" nich jest
         * już bezpieczna.
         */
        System.out.println("\n=== Plytka kopia NIE chroni mutowalnych ELEMENTOW listy ===");

        MutableGracz gracz1 = new MutableGracz("Kowalski");
        List<MutableGracz> druzynaOryginalna = new ArrayList<>(List.of(gracz1));
        List<MutableGracz> druzynaPlytkaKopia = List.copyOf(druzynaOryginalna); // plytka kopia!

        System.out.println("Przed mutacja gracza: " + druzynaPlytkaKopia.get(0).getNazwisko());
        gracz1.setNazwisko("Nowak"); // mutujemy OBIEKT gracza, nie liste
        System.out.println("Po gracz1.setNazwisko(\"Nowak\"): " + druzynaPlytkaKopia.get(0).getNazwisko());
        System.out.println("-> lista jest 'zamknieta' (nie da sie dodac/usunac gracza),");
        System.out.println("   ale ELEMENT wewnatrz wciaz jest tym samym, mutowalnym obiektem!");

        // Naprawa: GŁĘBOKA kopia - kopiujemy nie tylko liste, ale KAZDY element:
        List<MutableGracz> druzynaGlebokaKopia = druzynaOryginalna.stream()
                .map(MutableGracz::kopia) // kazdy gracz dostaje WLASNA, niezalezna kopie
                .toList();

        gracz1.setNazwisko("Wisniewski");
        System.out.println("\nPo kolejnej mutacji gracz1, GLEBOKA kopia: " + druzynaGlebokaKopia.get(0).getNazwisko());
        System.out.println("-> bez zmian! Glebokia kopia sklonowala tez sam obiekt Gracz.");

        System.out.println("\nPraktyczna wskazowka: jesli elementy Twojej kolekcji SAME sa niemutowalne");
        System.out.println("(String, Integer, rekordy z niemutowalnymi polami...) - plytka kopia (List.copyOf)");
        System.out.println("w zupelnosci wystarcza i glebokie kopiowanie jest niepotrzebne.");

        /*
         * ============================================================
         * 🔹 NAPRAWA REKORDU: DEFENSYWNA KOPIA W KOMPAKTOWYM KONSTRUKTORZE
         * ============================================================
         * Rekord z Lesson24 (`ZespolRecord`) miał dokładnie ten sam problem
         * co BrokenImmutablePerson. Naprawa jest jeszcze krótsza dzięki
         * kompaktowemu konstruktorowi (poznanemu w Lesson14_Records) -
         * wystarczy JEDNA linijka.
         */
        System.out.println("\n=== Naprawiony rekord: kompaktowy konstruktor + List.copyOf ===");

        List<String> czlonkowieFrontend = new ArrayList<>(List.of("Ola", "Tomek"));
        ZespolRecordFixed frontend = new ZespolRecordFixed("Frontend", czlonkowieFrontend);
        System.out.println("Zespol (na starcie): " + frontend);

        czlonkowieFrontend.add("Intruz"); // mutacja oryginalnej listy - juz bez efektu
        System.out.println("Po mutacji oryginalnej listy z zewnatrz: " + frontend);
        System.out.println("-> 'Intruz' NIE przecieka - kompaktowy konstruktor zrobil List.copyOf().");

        /*
         * ============================================================
         * 📌 PRAKTYCZNY WZORZEC: TABLICE (BRAK GOTOWEGO Xxx.copyOf DLA ARRAY)
         * ============================================================
         * Kolekcje mają wygodne `List.copyOf`/`Map.copyOf`/`Set.copyOf`.
         * Tablice takiego gotowca nie mają - trzeba użyć `clone()` albo
         * `Arrays.copyOf(tablica, tablica.length)`, i to W OBU miejscach:
         * konstruktorze ORAZ getterze.
         */
        System.out.println("\n=== Poprawnie skopiowana tablica (clone w konstruktorze i getterze) ===");

        int[] ocenyOryginalne = {5, 4, 5};
        FixedImmutableGrades oceny = new FixedImmutableGrades(ocenyOryginalne);

        ocenyOryginalne[0] = 1; // mutacja oryginalu - bez wplywu
        oceny.getScores()[1] = 1; // mutacja wyniku gettera - tez bez wplywu (getter zwraca kopie)

        System.out.println("Oceny wewnatrz obiektu: " + Arrays.toString(oceny.getScores()));
        System.out.println("-> obie proby mutacji (oryginal + getter) NIE zmienily stanu wewnetrznego.");

        /*
         * ============================================================
         * 📌 ZAMKNIĘCIE KLASTRA "TYPE INFERENCE & IMMUTABILITY" (Lekcje 23-25)
         * ============================================================
         * - Lekcja 23 (var): `var` to wygoda składniowa, wciąż statyczne
         *   typowanie - typ ustalony raz, w kompilacji.
         * - Lekcja 24 (Immutability): niemutowalność = cały stan obiektu
         *   identyczny przez całe życie obiektu; final chroni TYLKO
         *   referencję, nie zawartość obiektu mutowalnego typu.
         * - Lekcja 25 (Defensive Copying): naprawa - kopiuj na WEJŚCIU
         *   (konstruktor) i na WYJŚCIU (getter); używaj List.copyOf/
         *   Map.copyOf/Set.copyOf (prawdziwa kopia) zamiast
         *   Collections.unmodifiableXxx (tylko widok) tam, gdzie potrzebujesz
         *   pełnej izolacji; pamiętaj, że kopiowanie kolekcji jest domyślnie
         *   PŁYTKIE - jeśli elementy są mutowalne, potrzebna jest głęboka
         *   kopia (mapowanie każdego elementu).
         *
         * Następna lekcja: Lesson26_ServiceLoaderAndSpi - jak Java potrafi w
         * runtime odnaleźć IMPLEMENTACJE interfejsu bez twardego,
         * skompilowanego powiązania (mechanizm Service Provider Interface,
         * używany m.in. przez JDBC do ładowania sterowników baz danych).
         */
        System.out.println("\n=== KONIEC LEKCJI 25 (i klastra 23-25). Dalej: Lesson26_ServiceLoaderAndSpi ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Kopiowanie obronne = naprawa pułapki z Lesson24: kopiuj dane
         *   WCHODZĄCE do konstruktora ORAZ dane WYCHODZĄCE z gettera.
         * - `List.copyOf`/`Map.copyOf`/`Set.copyOf` (JDK 10+): prawdziwa,
         *   niezależna, niemodyfikowalna kopia; rzuca NPE na null (liście
         *   lub jej elementach).
         * - `Collections.unmodifiableList` i podobne (starsze API): tylko
         *   WIDOK na oryginał - mutacje oryginału PRZEBIJAJĄ SIĘ przez widok.
         * - Kopiowanie kolekcji jest domyślnie PŁYTKIE - potrzeba głębokiej
         *   kopii (np. `.stream().map(Element::kopia).toList()`), jeśli
         *   elementy same są mutowalne.
         * - Tablice nie mają gotowego `copyOf` na poziomie kolekcji - użyj
         *   `clone()`/`Arrays.copyOf(...)` w konstruktorze I w getterze.
         * - Rekordy naprawia się najprościej: jedna linijka w kompaktowym
         *   konstruktorze (np. `pole = List.copyOf(pole);`).
         */
    }
}

/**
 * Naprawiona wersja BrokenImmutablePerson z Lesson24_Immutability - kopia na
 * WEJŚCIU (konstruktor, List.copyOf) i "kopia" na WYJŚCIU (getter po prostu
 * zwraca tę samą, ale JUŻ niemodyfikowalną referencję - nie trzeba kopiować
 * drugi raz, bo List.copyOf() dała trwale niemodyfikowalny obiekt).
 */
class FixedImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    FixedImmutablePerson(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = List.copyOf(hobbies); // ✅ prawdziwa, niezalezna, niemodyfikowalna kopia
    }

    String getName() { return name; }
    int getAge() { return age; }

    List<String> getHobbies() {
        return hobbies; // ✅ bezpieczne - hobbies jest juz trwale niemodyfikowalne
    }
}

/**
 * Poprawnie skopiowana tablica - `clone()` (rownowazny Arrays.copyOf) i w
 * konstruktorze, i w getterze, bo tablice nie maja wbudowanego mechanizmu
 * "trwale niemodyfikowalnej" wersji samej siebie (w odroznieniu od List.copyOf).
 */
class FixedImmutableGrades {
    private final int[] scores;

    FixedImmutableGrades(int[] scores) {
        this.scores = scores.clone(); // ✅ kopia na WEJSCIU
    }

    int[] getScores() {
        return scores.clone(); // ✅ kopia na WYJSCIU
    }
}

/**
 * Naprawiony ZespolRecord z Lesson24_Immutability - kompaktowy konstruktor
 * (poznany w Lesson14_Records) robi defensywna kopie w JEDNEJ linijce.
 */
record ZespolRecordFixed(String nazwa, List<String> czlonkowie) {
    ZespolRecordFixed { // kompaktowy konstruktor
        czlonkowie = List.copyOf(czlonkowie); // ✅ naprawa - niezalezna, niemodyfikowalna kopia
    }
}

/**
 * Mutowalny gracz uzywany do demonstracji plytkiej vs glebokiej kopii kolekcji.
 */
class MutableGracz {
    private String nazwisko;

    MutableGracz(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    String getNazwisko() { return nazwisko; }
    void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }

    // "Konstruktor kopiujacy" jako metoda fabrykujaca - potrzebny do GLEBOKIEJ kopii.
    MutableGracz kopia() {
        return new MutableGracz(this.nazwisko);
    }
}
