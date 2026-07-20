package com.example.javaquest._28_java_evolution.Lesson14_Java16RecordsAndPatternMatchingInstanceof;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson14_Java16RecordsAndPatternMatchingInstanceof {

    record Punkt(int x, int y) {
    }

    sealed interface Ksztalt permits Kolo, Prostokat {
    }

    record Kolo(double promien) implements Ksztalt {
    }

    record Prostokat(double szerokosc, double wysokosc) implements Ksztalt {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: Java 16 (marzec 2021) - rekordy FINALNE, pattern matching instanceof FINALNY ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_02_oop/Lesson14` I
         * `_14_advancedjava/Lesson20_PatternMatchingInstanceof`
         * ============================================================
         * Java 16 USTABILIZOWALA 2 funkcje, ktore w tym kursie
         * dzialaly JUZ OD DAWNA (bo projekt celuje W Java 21):
         *
         * 1. JEP 395 - REKORDY (byly preview W 14-15, Lesson12/13 z
         *    tego rozdzialu) - OD Javy 16 STANDARD, BEZ flagi.
         * 2. JEP 394 - pattern matching DLA `instanceof` (byl preview
         *    W Javie 14-15) - OD Javy 16 STANDARD. Pelna, glebsza
         *    teoria: `_14_advancedjava/Lesson20`.
         *
         * TA lekcja NIE POWTARZA mechaniki OD ZERA - POKAZUJE, JAK
         * OBA mechanizmy WSPOLPRACUJA razem (rekordy JAKO cel
         * pattern matching instanceof) - CO byla NATURALNA zapowiedz
         * record patterns W Javie 21 (Lesson18).
         */
        System.out.println("Java 16 (marzec 2021): rekordy (JEP 395) i pattern matching instanceof (JEP 394) OSTATECZNIE STABILNE.");

        demonstrateRecordsNowFullyStandard();
        demonstratePatternMatchingInstanceofFinal();
        demonstrateCombiningRecordsWithPatternMatching();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Rekordy: STANDARD OD Javy 16 (droga: 14 preview -> 15
         *   preview (druga runda) -> 16 STABILNY).
         * - Pattern matching `instanceof`: STANDARD OD Javy 16
         *   (droga: 14 preview -> 15 preview -> 16 STABILNY).
         * - RAZEM: `instanceof Kolo k` WYCIAGA OD RAZU zmienna `k`
         *   TYPU `Kolo` - BEZ jawnego rzutowania - ZAPOWIEDZ record
         *   patterns Z Javy 21 (`instanceof Kolo(var promien)`),
         *   gdzie MOZNA WPROST rozbic rekord NA komponenty
         *   (Lesson18 tego rozdzialu, pelna teoria:
         *   `_14_advancedjava/Lesson21`).
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private static void demonstrateRecordsNowFullyStandard() {
        System.out.println("\n--- Rekordy: STANDARD od Javy 16 (bez --enable-preview) ---");
        Punkt p1 = new Punkt(1, 2);
        Punkt p2 = new Punkt(1, 2);
        System.out.println("record Punkt(int x, int y) - kompiluje sie normalnie, BEZ flag.");
        System.out.println("p1=" + p1 + ", p2=" + p2 + ", p1.equals(p2)=" + p1.equals(p2));
        assertThat(p1).isEqualTo(p2);
    }

    private static void demonstratePatternMatchingInstanceofFinal() {
        System.out.println("\n--- Pattern matching instanceof: STANDARD od Javy 16 ---");
        Object obiekt = "Tekst o dlugosci wiekszej niz 5";

        System.out.println("--- STARY styl (rzutowanie RECZNE) ---");
        if (obiekt instanceof String) {
            String tekst = (String) obiekt;
            System.out.println("Dlugosc (stary styl): " + tekst.length());
        }

        System.out.println("--- NOWY styl (pattern variable od razu W warunku) ---");
        if (obiekt instanceof String tekst && tekst.length() > 5) {
            System.out.println("Dlugosc (nowy styl): " + tekst.length() + " (zmienna 'tekst' WYCIAGNIETA BEZPOSREDNIO Z warunku)");
        }

        System.out.println("Pelna teoria (negacja, zasieg zmiennej pattern): _14_advancedjava/Lesson20_PatternMatchingInstanceof.");
        assertThat(obiekt instanceof String s && s.length() > 5).isTrue();
    }

    private static void demonstrateCombiningRecordsWithPatternMatching() {
        System.out.println("\n--- Polaczenie: instanceof + rekordy = zapowiedz record patterns (Java 21, Lesson18) ---");
        Ksztalt[] ksztalty = { new Kolo(3.0), new Prostokat(4.0, 5.0) };

        for (Ksztalt ksztalt : ksztalty) {
            double pole;
            if (ksztalt instanceof Kolo kolo) {
                pole = Math.PI * kolo.promien() * kolo.promien();
                System.out.println("Kolo(promien=" + kolo.promien() + ") -> pole = " + String.format("%.2f", pole));
            } else if (ksztalt instanceof Prostokat prostokat) {
                pole = prostokat.szerokosc() * prostokat.wysokosc();
                System.out.println("Prostokat(" + prostokat.szerokosc() + "x" + prostokat.wysokosc() + ") -> pole = " + pole);
            }
        }

        System.out.println("W Javie 21 (Lesson18) TEN SAM kod da sie zapisac ZWIEZLEJ: 'instanceof Kolo(var promien)' - rozbicie NA komponenty W JEDNYM kroku.");
        System.out.println("sealed interface Ksztalt permits Kolo, Prostokat - pelna teoria sealed classes: _14_advancedjava/Lesson19, Lesson15 tego rozdzialu.");
    }
}
