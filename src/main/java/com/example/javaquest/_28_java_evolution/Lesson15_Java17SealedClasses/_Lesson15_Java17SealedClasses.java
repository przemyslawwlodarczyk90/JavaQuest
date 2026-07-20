package com.example.javaquest._28_java_evolution.Lesson15_Java17SealedClasses;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson15_Java17SealedClasses {

    sealed interface Platnosc permits Karta, Gotowka, Przelew {
    }

    record Karta(String numerZamaskowany) implements Platnosc {
    }

    record Gotowka(double kwota) implements Platnosc {
    }

    record Przelew(String iban) implements Platnosc {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: Java 17 (wrzesien 2021) - sealed classes/interfaces (JEP 409), 3. LTS ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_14_advancedjava/Lesson19_SealedClasses`
         * ============================================================
         * Java 17 TO 3. LTS (PO 8 I 11) - byla preview W Javie 15
         * (JEP 360) I 16 (JEP 397), USTABILIZOWANA W 17 (JEP 409).
         * `sealed` OGRANICZA, KTORE klasy/interfejsy MOGA
         * IMPLEMENTOWAC/ROZSZERZAC dany typ (`permits Lista, ...`) -
         * DAJE kompilatorowi PELNA WIEDZE O HIERARCHII, CO WLASNIE
         * WLACZYLO WYCZERPUJACE pattern matching W `switch` BEZ
         * `default` (Lesson18, Java 21).
         *
         * 🔍 TA lekcja NIE POWTARZA szczegolow skladni (`_14_advancedjava/
         * Lesson19` JUZ to zrobil, WLACZNIE Z `non-sealed`) - TU
         * pokazujemy PRAKTYCZNY przyklad (hierarchia platnosci) I
         * POLACZENIE Z `instanceof` pattern Z Lesson14.
         */
        System.out.println("Java 17 (wrzesien 2021, 3. LTS): sealed classes/interfaces (JEP 409) - STABILNE po preview w 15-16. Pelna teoria: _14_advancedjava/Lesson19.");

        demonstrateSealedRestrictsImplementations();
        demonstrateExhaustiveHandlingWithSealedHierarchy();
        explainWhyJava17IsSignificantLts();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `sealed interface X permits A, B, C` - TYLKO `A`, `B`, `C`
         *   MOGA implementowac `X` (ZAMKNIETA, KONTROLOWANA hierarchia).
         * - KAZDA klasa Z listy `permits` MUSI byc `final`, `sealed`
         *   LUB `non-sealed` (jawna deklaracja, CO sie Z nia dzieje
         *   DALEJ).
         * - Kompilator ZNA WSZYSTKIE mozliwe podtypy - TO fundament
         *   WYCZERPUJACEGO pattern matching W `switch` (Lesson18,
         *   Java 21) - BEZ potrzeby `default`.
         * - Java 17 = 3. LTS - PIERWSZA wersja, W ktorej Oracle
         *   ZMIENIL model licencjonowania (NDA Free Terms) - SZCZEGOLY
         *   licencyjne POZA zakresem kursu, ale WARTO wiedziec, ze
         *   TO PRZELOMOWA wersja adopcji W branzy.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void demonstrateSealedRestrictsImplementations() {
        System.out.println("\n--- sealed interface - TYLKO wymienione typy MOGA go implementowac ---");
        System.out.println("sealed interface Platnosc permits Karta, Gotowka, Przelew {}");
        System.out.println("PROBA dodania 4. implementacji (np. 'Kryptowaluta') W INNYM pliku/pakiecie DA blad kompilacji: 'class is not allowed to extend sealed interface'.");

        Platnosc platnosc = new Karta("**** **** **** 1234");
        System.out.println("Przyklad instancji: " + platnosc);
        assertThat(platnosc).isInstanceOf(Karta.class);
    }

    private static void demonstrateExhaustiveHandlingWithSealedHierarchy() {
        System.out.println("\n--- Kompilator ZNA WSZYSTKIE podtypy - obsluga WYCZERPUJACA (bez default) mozliwa dzieki sealed ---");
        Platnosc[] platnosci = {
                new Karta("**** 5678"),
                new Gotowka(150.00),
                new Przelew("PL61109010140000071219812874")
        };

        for (Platnosc platnosc : platnosci) {
            String opis;
            if (platnosc instanceof Karta karta) {
                opis = "Platnosc karta: " + karta.numerZamaskowany();
            } else if (platnosc instanceof Gotowka gotowka) {
                opis = "Platnosc gotowka: " + gotowka.kwota() + " PLN";
            } else if (platnosc instanceof Przelew przelew) {
                opis = "Przelew na IBAN: " + przelew.iban();
            } else {
                // NIEOSIAGALNE - kompilator (I my) WIEMY, ze permits ma TYLKO 3 typy.
                throw new IllegalStateException("Nieznany typ platnosci: " + platnosc);
            }
            System.out.println(opis);
        }

        System.out.println("W Javie 21 (Lesson18) TEN SAM lancuch da sie zapisac JAKO `switch` BEZ galezi 'else'/'default' - kompilator SAM SPRAWDZI wyczerpujacosc dzieki sealed.");
        assertThat(platnosci).hasSize(3);
    }

    private static void explainWhyJava17IsSignificantLts() {
        System.out.println("\n--- Java 17 - 3. LTS, przelomowa dla adopcji W branzy ---");
        System.out.println("Java 17 laczy WSZYSTKIE funkcje Z Java 9-17: rekordy, sealed classes, pattern matching instanceof, text blocks, HttpClient, moduly.");
        System.out.println("Wiele frameworkow (WLACZNIE ze Spring Framework 6/Spring Boot 3 uzywanym W tym kursie OD _20_spring_core) WYMAGA Java 17 JAKO MINIMALNEGO baseline.");
        System.out.println("Powiazanie: `_20_spring_core/Lesson01_WhatIsSpring` - Spring 6/Boot 3 wymagaja Java 17+ (namespace jakarta.*, NIE javax.*).");
    }
}
