package com.example.javaquest._28_java_evolution.Lesson11_Java12To13SwitchExpressionsPreview;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson11_Java12To13SwitchExpressionsPreview {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: Java 12-13 - wyrazenia switch jako PREVIEW (droga do 14) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - jak dziala mechanizm PREVIEW FEATURES
         * ============================================================
         * Java 12 (marzec 2019, JEP 325) WPROWADZILA nowa skladnie
         * strzalkowa `switch` JAKO **PREVIEW** - czyli funkcje
         * GOTOWA, ale JESZCZE NIE ZAGWARANTOWANA na stale (MOZE sie
         * zmienic LUB zniknac W kolejnej wersji). Java 13 (wrzesien
         * 2019, JEP 354) DOPRECYZOWALA skladnie, dodajac slowo
         * kluczowe `yield` (ZAMIAST wczesniejszej, PORZUCONEJ propozycji
         * `break value;`). DOPIERO Java 14 (2020, JEP 361)
         * USTABILIZOWALA cala funkcje - OD TAD kod DZIALA BEZ flagi
         * `--enable-preview`.
         *
         * PRAKTYCZNA konsekwencja: kod, ktory PISZESZ dzis (skladnia
         * strzalkowa + `yield`) TO DOKLADNIE ta sama skladnia, ktora
         * byla eksperymentowana W 12/13 - "droga" do stabilizacji jest
         * WIDOCZNA W samej skladni (`yield` istnieje TYLKO dlatego, ze
         * `break value` okazalo sie mylace przy zagniezdzonych petlach).
         *
         * 🔍 Pelne poznanie NOWOCZESNYCH wyrazen switch (Java 14+,
         * WLACZNIE Z pattern matching): `Lesson12` (Java 14) I
         * `_14_advancedjava/Lesson22_SwitchExpressions`.
         */
        System.out.println("Java 12 (2019, JEP 325): switch-expression jako PREVIEW. Java 13 (2019, JEP 354): dodano 'yield'. Java 14 (2020, JEP 361): STABILNE.");

        demonstrateOldStatementSwitchProblems();
        demonstrateNewExpressionSwitchSyntax();
        explainWhyYieldWasIntroduced();
        explainPreviewFeatureMechanism();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - STARY `switch` (STATEMENT) - podatny NA "fall-through"
         *   (brakujacy `break`), NIE zwraca wartosci BEZPOSREDNIO.
         * - NOWY `switch` (EXPRESSION, skladnia `->`) - ZWRACA
         *   wartosc, BRAK fall-through, WYMUSZONA WYCZERPUJACOSC
         *   (compiler SPRAWDZA WSZYSTKIE przypadki).
         * - `yield value;` - "zwroc `value` Z bloku `{ }` wewnatrz
         *   wyrazenia switch" (ODROZNIENIE OD `return`, ktory
         *   opuszcza CALA metode).
         * - PREVIEW FEATURE = funkcja GOTOWA DO WYPROBOWANIA, ale
         *   BEZ GWARANCJI stabilnosci - wymaga `--enable-preview`
         *   PRZY kompilacji I uruchomieniu, MOZE sie zmienic/zniknac.
         * - Pelna, WSPOLCZESNA skladnia switch (WLACZNIE Z pattern
         *   matching W Javie 21): `Lesson12`, `Lesson18`,
         *   `_14_advancedjava/Lesson21-22`.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void demonstrateOldStatementSwitchProblems() {
        System.out.println("\n--- STARY switch (statement, SPRZED Javy 12) - podatny NA fall-through ---");
        int dzienTygodnia = 3;
        String rodzajDnia;
        switch (dzienTygodnia) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                rodzajDnia = "roboczy";
                break;
            case 6:
            case 7:
                rodzajDnia = "weekend";
                break;
            default:
                rodzajDnia = "nieznany";
        }
        System.out.println("Stary switch: dzien " + dzienTygodnia + " -> " + rodzajDnia);
        System.out.println("Problem: KAZDY `case` WYMAGA jawnego `break` (brak = FALL-THROUGH do kolejnego case - czesty blad!).");

        assertThat(rodzajDnia).isEqualTo("roboczy");
    }

    private static void demonstrateNewExpressionSwitchSyntax() {
        System.out.println("\n--- NOWY switch (expression, skladnia '->', PREVIEW od Javy 12) ---");
        int dzienTygodnia = 3;
        String rodzajDnia = switch (dzienTygodnia) {
            case 1, 2, 3, 4, 5 -> "roboczy";
            case 6, 7 -> "weekend";
            default -> "nieznany";
        };
        System.out.println("Nowy switch: dzien " + dzienTygodnia + " -> " + rodzajDnia);
        System.out.println("Zalety: BRAK fall-through (kazda galaz TO OSOBNY przypadek), MOZE zwrocic wartosc BEZPOSREDNIO (jest 'expression', nie 'statement').");

        assertThat(rodzajDnia).isEqualTo("roboczy");
    }

    private static void explainWhyYieldWasIntroduced() {
        System.out.println("\n--- yield (Java 13, JEP 354) - 'zwroc wartosc Z BLOKU wewnatrz switch-expression' ---");
        int miesiac = 2;
        int liczbaDni = switch (miesiac) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> {
                boolean przestepny = true; // uproszczenie demonstracyjne
                yield przestepny ? 29 : 28;
            }
            default -> throw new IllegalArgumentException("Nieprawidlowy miesiac: " + miesiac);
        };
        System.out.println("switch z blokiem {} dla miesiaca 2 (luty) -> yield " + liczbaDni + " dni");
        System.out.println("DLACZEGO 'yield', a NIE 'break value' (pierwotna propozycja Java 12)? 'break value' bylo MYLACE PRZY zagniezdzonych petlach wewnatrz bloku switch - 'yield' jest JEDNOZNACZNE.");

        assertThat(liczbaDni).isEqualTo(29);
    }

    private static void explainPreviewFeatureMechanism() {
        System.out.println("\n--- Mechanizm PREVIEW FEATURES w JDK ---");
        System.out.println("1. Funkcja jest ZAIMPLEMENTOWANA i DOSTEPNA, ale WYMAGA jawnej flagi `--enable-preview` PRZY `javac` I `java`.");
        System.out.println("2. `.class` skompilowany Z preview feature jest OZNACZONY specjalna flaga W bajtkodzie - NIE URUCHOMI SIE na standardowym JVM bez `--enable-preview`.");
        System.out.println("3. Funkcja MOZE zostac ZMIENIONA (jak switch: 'break value' -> 'yield') LUB WYCOFANA miedzy wersjami - to jest CENA wczesnego dostepu.");
        System.out.println("4. Gdy funkcja JEST STABILNA (jak switch-expression W Javie 14), flaga PRZESTAJE byc potrzebna - dziala 'z pudelka'.");
        System.out.println("Przyklady innych funkcji, ktore PRZESZLY te sama droge: text blocks (13-15, Lesson13), pattern matching w switch (17-21, Lesson18), wirtualne watki (19-21, Lesson17/19).");
    }
}
