package com.example.javaquest._28_java_evolution.Lesson18_Java21PatternMatchingSwitchAndRecordPatterns;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson18_Java21PatternMatchingSwitchAndRecordPatterns {

    sealed interface Ksztalt permits Kolo, Prostokat, Trojkat {
    }

    record Kolo(double promien) implements Ksztalt {
    }

    record Prostokat(double szerokosc, double wysokosc) implements Ksztalt {
    }

    record Trojkat(double podstawa, double wysokosc) implements Ksztalt {
    }

    record Punkt(int x, int y) {
    }

    record Odcinek(Punkt start, Punkt koniec) {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: Java 21 (wrzesien 2023, 4. LTS) - pattern matching w switch + record patterns (JEP 441, 440) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_14_advancedjava/Lesson21`
         * ============================================================
         * Java 21 TO 4. LTS - FINALIZUJE 2 funkcje BUDOWANE PRZEZ
         * WCZESNIEJSZE lekcje tego rozdzialu (Lesson11-12: switch-
         * expression; Lesson14-15: instanceof pattern I sealed):
         *
         * - JEP 441 - pattern matching W `switch` (BYL preview W 17-
         *   20!, 4 rundy preview - NAJDLUZSZA droga stabilizacji Z
         *   CALEJ tej retrospektywy).
         * - JEP 440 - record patterns (BYL preview W 19-20).
         *
         * RAZEM DAJA MOZLIWOSC "rozbicia" obiektu NA komponenty
         * BEZPOSREDNIO W `case` - TO, CO Lesson14 zapowiadal JAKO
         * "naturalne rozwiniecie" instanceof+rekordow.
         *
         * 🔍 Pelna, GLEBOKA teoria (WLACZNIE Z zagniezdzonymi record
         * patterns, `when` guards): `_14_advancedjava/Lesson21_
         * PatternMatchingSwitchAndRecordPatterns`.
         */
        System.out.println("Java 21 (wrzesien 2023, 4. LTS): pattern matching w switch (JEP 441, preview OD Javy 17!) + record patterns (JEP 440, preview OD Javy 19).");

        demonstrateSwitchPatternMatchingReplacesInstanceofChain();
        demonstrateRecordPatternsDestructureDirectlyInCase();
        demonstrateNestedRecordPatterns();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `switch` Z pattern matching ZASTEPUJE DLUGI lancuch
         *   `instanceof` (Lesson14/15) - KAZDY `case` MOZE testowac
         *   INNY typ, kompilator SPRAWDZA WYCZERPUJACOSC dzieki
         *   `sealed` (Lesson15).
         * - Record patterns (`case Kolo(var promien) ->`) ROZBIJAJA
         *   rekord NA komponenty W JEDNYM kroku - BEZ jawnego
         *   wywolania akcesorow.
         * - Wsparcie DLA ZAGNIEZDZONYCH record patterns
         *   (`Odcinek(Punkt(var x1, var y1), Punkt(var x2, var y2))`).
         * - Pelna teoria (`when` guards, `null` W `switch`):
         *   `_14_advancedjava/Lesson21`.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    private static void demonstrateSwitchPatternMatchingReplacesInstanceofChain() {
        System.out.println("\n--- switch z pattern matching ZASTEPUJE lancuch instanceof (Lesson14/15) ---");
        Ksztalt[] ksztalty = { new Kolo(2.0), new Prostokat(3.0, 4.0), new Trojkat(5.0, 6.0) };

        for (Ksztalt ksztalt : ksztalty) {
            double pole = switch (ksztalt) {
                case Kolo k -> Math.PI * k.promien() * k.promien();
                case Prostokat p -> p.szerokosc() * p.wysokosc();
                case Trojkat t -> 0.5 * t.podstawa() * t.wysokosc();
                // BRAK 'default' - kompilator WIE (dzieki sealed), ze to WSZYSTKIE mozliwosci!
            };
            System.out.println(ksztalt.getClass().getSimpleName() + " -> pole = " + String.format("%.2f", pole));
        }

        System.out.println("Porownaj Z Lesson15 (lancuch if-instanceof-else): TU BRAK 'else'/'default' - kompilator WYMUSZA WYCZERPUJACOSC dzieki sealed interface.");
    }

    private static void demonstrateRecordPatternsDestructureDirectlyInCase() {
        System.out.println("\n--- Record patterns - rozbicie rekordu NA komponenty BEZPOSREDNIO w case ---");
        Ksztalt[] ksztalty = { new Kolo(3.0), new Prostokat(4.0, 5.0) };

        for (Ksztalt ksztalt : ksztalty) {
            String opis = switch (ksztalt) {
                case Kolo(var promien) -> "Kolo o promieniu " + promien + " (skladowa WYCIAGNIETA BEZPOSREDNIO, bez k.promien())";
                case Prostokat(var szerokosc, var wysokosc) -> "Prostokat " + szerokosc + "x" + wysokosc;
                case Trojkat t -> "Trojkat (nie rozbity NA skladowe - zwykly pattern typu)";
            };
            System.out.println(opis);
        }

        System.out.println("Porownaj Z Lesson14: TAM 'instanceof Kolo kolo' + RECZNE 'kolo.promien()'. TU: 'case Kolo(var promien)' - ROZBICIE w JEDNYM kroku.");
        assertThat(ksztalty[0]).isInstanceOf(Kolo.class);
    }

    private static void demonstrateNestedRecordPatterns() {
        System.out.println("\n--- Zagniezdzone record patterns - rozbicie WIELU poziomow naraz ---");
        Odcinek odcinek = new Odcinek(new Punkt(0, 0), new Punkt(3, 4));

        double dlugosc = switch (odcinek) {
            case Odcinek(Punkt(var x1, var y1), Punkt(var x2, var y2)) -> {
                double dx = x2 - x1;
                double dy = y2 - y1;
                yield Math.sqrt(dx * dx + dy * dy);
            }
        };

        System.out.println("Odcinek((0,0) -> (3,4)) -> dlugosc = " + dlugosc + " (rozbity NA 4 zmienne x1,y1,x2,y2 W JEDNYM wzorcu!)");
        System.out.println("Bez record patterns: 'odcinek.start().x()', 'odcinek.start().y()', itd. - WIELE wywolan akcesorow.");

        assertThat(dlugosc).isEqualTo(5.0);
    }
}
