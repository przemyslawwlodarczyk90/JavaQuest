package com.example.javaquest._28_java_evolution.Lesson12_Java14SwitchExpressionsAndRecordsPreview;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson12_Java14SwitchExpressionsAndRecordsPreview {

    record Wspolrzedne(Integer x, Integer y) {
    }

    static class Adres {
        String miasto;
    }

    static class Osoba {
        Adres adres;
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: Java 14 (marzec 2020) - switch STABILNY, rekordy jako preview, pomocne NPE ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - 3 kluczowe zmiany Javy 14
         * ============================================================
         * Java 14 TO wersja NIE-LTS, ale ZAWIERA 3 WAZNE kroki:
         *
         * 1. JEP 361 - wyrazenia `switch` Z Lesson11 STAJA SIE
         *    STANDARDEM (BEZ `--enable-preview`) - droga OD Javy 12
         *    JEST ZAKONCZONA.
         * 2. JEP 359 - REKORDY WCHODZA JAKO PREVIEW (PIERWSZY raz W
         *    JDK) - ZWIEZLA skladnia DLA niezmiennych nosnikow danych.
         *    Rekordy ZOSTANA USTABILIZOWANE dopiero W Javie 16
         *    (Lesson14) - TU pokazujemy JE JUZ DZIALAJACE (bo projekt
         *    celuje W Java 21, gdzie sa juz stabilne OD DAWNA), ale Z
         *    ZAZNACZENIEM, ze W Javie 14 wymagalyby `--enable-preview`.
         * 3. JEP 358 - POMOCNE komunikaty `NullPointerException` -
         *    JVM DOKLADNIE wskazuje, KTORA zmienna byla `null` W
         *    LANCUCHU wywolan (np. `a.b.c.metoda()`).
         *
         * 🔍 Pelna teoria rekordow: `_02_oop/Lesson14`,
         * `_14_advancedjava` (record patterns W Lesson21).
         */
        System.out.println("Java 14 (marzec 2020): switch-expression STABILNY (JEP 361), rekordy PREVIEW (JEP 359), pomocne NPE (JEP 358).");

        demonstrateSwitchExpressionNowStandard();
        demonstrateRecordsAsPreviewFeature();
        demonstrateHelpfulNullPointerExceptions();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wyrazenia `switch` (Lesson11) SA OD Javy 14 STANDARDEM -
         *   BEZ zadnej dodatkowej flagi.
         * - Rekordy (JEP 359) WESZLY jako PREVIEW W Javie 14 -
         *   PELNA stabilizacja: Java 16 (Lesson14).
         * - Pomocne NPE (JEP 358) - JVM POKAZUJE DOKLADNIE, KTORY
         *   element LANCUCHA `a.b.c` byl `null`, ZAMIAST samego
         *   "NullPointerException" bez kontekstu. WLACZONE DOMYSLNIE
         *   OD Javy 15 (W 14 byl to preview, wymagajacy
         *   `-XX:+ShowCodeDetailsInExceptionMessages`).
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static void demonstrateSwitchExpressionNowStandard() {
        System.out.println("\n--- switch-expression: OD Javy 14 STANDARD, BEZ --enable-preview ---");
        int ocena = 4;
        String opis = switch (ocena) {
            case 5 -> "celujacy";
            case 4 -> "bardzo dobry";
            case 3 -> "dobry";
            default -> "do poprawy";
        };
        System.out.println("Ocena " + ocena + " -> " + opis + " (kompiluje sie BEZ zadnej flagi, w odroznieniu OD Lesson11!)");
        assertThat(opis).isEqualTo("bardzo dobry");
    }

    private static void demonstrateRecordsAsPreviewFeature() {
        System.out.println("\n--- Rekordy (JEP 359) - W Javie 14 PREVIEW, tu (Java 21) JUZ STABILNE ---");
        Wspolrzedne punkt = new Wspolrzedne(3, 7);
        System.out.println("record Wspolrzedne(Integer x, Integer y) - JEDNA linijka daje: konstruktor, akcesory (x()/y()), equals/hashCode/toString.");
        System.out.println("punkt = " + punkt + ", x=" + punkt.x() + ", y=" + punkt.y());
        System.out.println("W Javie 14 ten kod WYMAGALBY `javac --release 14 --enable-preview` - PELNA stabilizacja: Java 16 (Lesson14 pogliebi temat).");

        assertThat(punkt.x()).isEqualTo(3);
        assertThat(punkt).isEqualTo(new Wspolrzedne(3, 7));
    }

    private static void demonstrateHelpfulNullPointerExceptions() {
        System.out.println("\n--- Pomocne NullPointerException (JEP 358) - dokladnie WSKAZUJE, CO bylo null ---");
        Osoba osoba = new Osoba();
        // osoba.adres jest celowo NIEZAINICJALIZOWANE (null).

        System.out.println("Probujemy wywolac osoba.adres.miasto.length() - 'adres' jest null.");
        assertThatThrownBy(() -> {
            int dlugosc = osoba.adres.miasto.length();
            System.out.println("Nigdy nie wypisze sie: " + dlugosc);
        }).isInstanceOf(NullPointerException.class)
          .satisfies(wyjatek -> {
              String komunikat = wyjatek.getMessage();
              System.out.println("Komunikat NPE: " + komunikat);
              assertThat(komunikat).contains("adres");
          });

        System.out.println("PRZED Java 14/15: komunikat bylby PUSTY LUB tylko \"NullPointerException\" - bez wskazania, KTORA zmienna W LANCUCHU byla null.");
        System.out.println("OD Java 15 (domyslnie WLACZONE): komunikat WPROST mowi 'because \"osoba.adres\" is null' - OGROMNA oszczednosc czasu debugowania.");
    }
}
