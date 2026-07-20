package com.example.javaquest._28_java_evolution.Lesson13_Java15TextBlocks;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson13_Java15TextBlocks {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: Java 15 (wrzesien 2020) - Text Blocks (JEP 378) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - text blocks, ale JUZ SZEROKO uzywane W kursie
         * ============================================================
         * Text blocks (`"""..."""`) BYLY preview W Javie 13 (JEP 355)
         * I 14 (JEP 368), USTABILIZOWANE W Javie 15 (JEP 378) - TA
         * SAMA droga PREVIEW->STABILNY co switch-expression (Lesson11
         * -12). TA lekcja jest RETROSPEKTYWNA - text blocks byly JUZ
         * uzywane WIELOKROTNIE W tym kursie (`_11_buildtools`
         * generujace `build.xml`/`pom.xml`, `_18_rest_api` generujace
         * OpenAPI YAML) - TU pokazujemy SZCZEGOLY MECHANIKI (incydentalne
         * bialе znaki, `\` NA KONCU linii, `\s`), ktorych tamte
         * lekcje NIE tlumaczyly wprost.
         */
        System.out.println("Java 15 (wrzesien 2020): Text Blocks (JEP 378) - USTABILIZOWANE po preview w 13/14. Uzywane juz w _11_buildtools/_18_rest_api.");

        demonstrateOldVsNewMultilineString();
        demonstrateIncidentalWhitespaceStripping();
        demonstrateBackslashLineContinuation();
        demonstrateExplicitTrailingSpaceWithS();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PRZED Java 15: wieloliniowy tekst = `+ "\n"` LACZENIE
         *   stringow, TRUDNE do czytania I edycji.
         * - `"""` (text block) - tekst PISANY DOKLADNIE tak, JAK
         *   MA WYGLADAC (bez `\n` na koncu kazdej linii).
         * - "Incydentalne biale znaki" - wspolny WCIEC prefix
         *   WSZYSTKICH linii JEST AUTOMATYCZNIE USUWANY (bazujac NA
         *   POZYCJI zamykajacego `"""`).
         * - `\` NA KONCU linii - LACZY 2 linie W JEDNA (bez `\n`).
         * - `\s` - JAWNA SPACJA NA KONCU linii (INACZEJ usuwana JAKO
         *   "trailing whitespace").
         * - Praktyczne zastosowania W tym kursie: `_11_buildtools`
         *   (XML/Groovy/Kotlin DSL), `_18_rest_api/Lesson18` (YAML),
         *   `_16_clean_code` (przyklady kodu W komentarzach).
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static void demonstrateOldVsNewMultilineString() {
        System.out.println("\n--- PRZED Java 15: laczenie stringow + \\n RECZNIE ---");
        String staryJson = "{\n" +
                "  \"imie\": \"Anna\",\n" +
                "  \"wiek\": 30\n" +
                "}";
        System.out.println("Stary sposob (fragment kodu): \"{\\n\" + \"  \\\"imie\\\": \\\"Anna\\\",\\n\" + ...");
        System.out.println("Wynik:\n" + staryJson);

        System.out.println("\n--- OD Java 15: text block - piszesz DOKLADNIE tak, JAK MA WYGLADAC ---");
        String nowyJson = """
                {
                  "imie": "Anna",
                  "wiek": 30
                }""";
        System.out.println("Wynik:\n" + nowyJson);

        assertThat(nowyJson).isEqualTo(staryJson);
    }

    private static void demonstrateIncidentalWhitespaceStripping() {
        System.out.println("\n--- Incydentalne biale znaki - WSPOLNY prefiks wciecia USUWANY AUTOMATYCZNIE ---");
        String tekst = """
                Linia pierwsza
                Linia druga
                Linia trzecia""";
        System.out.println("Kod zrodlowy MA wciecie (zgodne Z reszta metody), ale WYNIK go NIE ZAWIERA:");
        System.out.println("[" + tekst + "]");
        System.out.println("Zasada: kompilator PATRZY NA POZYCJE zamykajacego \"\"\" I linie Z NAJMNIEJSZYM wcieciem - TO wciecie jest WSPOLNYM prefiksem, ODCIETYM ze WSZYSTKICH linii.");

        assertThat(tekst).doesNotContain("    Linia");
        assertThat(tekst.lines().toList()).containsExactly("Linia pierwsza", "Linia druga", "Linia trzecia");
    }

    private static void demonstrateBackslashLineContinuation() {
        System.out.println("\n--- \\ na koncu linii - LACZY dlugi tekst W JEDNA linie BEZ znaku nowej linii ---");
        String dlugiTekstBezLamania = """
                To jest bardzo dlugie zdanie, ktore W kodzie zrodlowym \
                jest zlamane NA 2 linie DLA czytelnosci, ale W WYNIKU \
                stanowi JEDNA, ciagla linie tekstu.""";
        System.out.println("[" + dlugiTekstBezLamania + "]");
        System.out.println("Liczba znakow nowej linii W wyniku: " + dlugiTekstBezLamania.lines().count() + " (1 linia, mimo 3 linii W KODZIE ZRODLOWYM)");

        assertThat(dlugiTekstBezLamania.lines().count()).isEqualTo(1);
    }

    private static void demonstrateExplicitTrailingSpaceWithS() {
        System.out.println("\n--- \\s - JAWNA spacja na koncu linii (inaczej USUWANA jako 'trailing whitespace') ---");
        String tabelaBezS = """
                Kolumna1
                Kolumna2""";
        String tabelaZS = """
                Kolumna1\s
                Kolumna2""";
        System.out.println("Bez \\s, dlugosc pierwszej linii: " + tabelaBezS.lines().findFirst().orElseThrow().length());
        System.out.println("Z \\s, dlugosc pierwszej linii:    " + tabelaZS.lines().findFirst().orElseThrow().length() + " (o 1 znak wiecej - spacja ZACHOWANA)");

        assertThat(tabelaZS.lines().findFirst().orElseThrow()).hasSize(tabelaBezS.lines().findFirst().orElseThrow().length() + 1);
    }
}
