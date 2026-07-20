package com.example.javaquest._28_java_evolution.Lesson09_Java11LtsStringAndFilesMethods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson09_Java11LtsStringAndFilesMethods {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 9: Java 11 (wrzesien 2018) - LTS, nowe metody String/Files, var w lambdach ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - Java 11 TO DRUGA wersja LTS (PO Javie 8)
         * ============================================================
         * Rytm 6-miesieczny Z Lesson01 oznacza, ze MIEDZY Java 8 A 11
         * bylo AZ 5 wydan (9, 10, 11) - ale WIEKSZOSC projektow
         * "przeskoczyla" PROSTO Z 8 NA 11, bo TO PIERWSZA kolejna
         * wersja LTS. Java 11 TO TEZ OSTATNIA wersja Z DARMOWYMI
         * dlugoterminowymi aktualizacjami Oracle JDK BEZ subskrypcji
         * (OD Javy 17 zasady sponsoringu sie ZMIENILY) - stad JEJ
         * WYJATKOWA popularnosc W produkcji DO DZIS.
         *
         * TA lekcja skupia sie NA 4 KONKRETNYCH dodatkach: nowe metody
         * `String` (`isBlank`/`strip`/`repeat`/`lines`), nowe metody
         * `Files` (`readString`/`writeString`), `var` W parametrach
         * lambdy, ORAZ uruchamianie POJEDYNCZEGO pliku `.java` BEZ
         * jawnej kompilacji (`java Plik.java`).
         */
        System.out.println("Java 11 (wrzesien 2018) - druga wersja LTS. Ostatnia z darmowym dlugoterminowym wsparciem Oracle JDK bez subskrypcji.");

        demonstrateNewStringMethods();
        demonstrateNewFilesMethods();
        demonstrateVarInLambdaParameters();
        explainSingleFileSourceLaunch();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Java 11 = 2. LTS, ostatnia Z darmowym dlugoterminowym
         *   wsparciem Oracle JDK bez subskrypcji.
         * - `String`: `isBlank()` (biale znaki TEZ sie licza, W
         *   ODROZNIENIU OD `isEmpty()`), `strip()`/`stripLeading()`/
         *   `stripTrailing()` (Unicode-aware, LEPSZE OD starego
         *   `trim()`), `repeat(n)`, `lines()` (Stream<String>).
         * - `Files.readString(Path)`/`writeString(Path, CharSequence)`
         *   - JEDNA linijka ZAMIAST rezcnego strumienia/czytnika
         *   (pelna teoria strumieni: `_04_io`).
         * - `var` W parametrach lambdy - GLOWNY powod: MOZNA dodac
         *   ADNOTACJE DO parametru lambdy (co bylo NIEMOZLIWE Z
         *   niejawnym typem).
         * - `java PlikZrodlowy.java` - URUCHOMIENIE pojedynczego
         *   pliku BEZ jawnego `javac` (wygodne DO SKRYPTOW/nauki).
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateNewStringMethods() {
        System.out.println("\n--- String.isBlank() - biale znaki TEZ traktowane jako 'puste' ---");
        String tylkoSpacje = "   ";
        System.out.println("\"   \".isEmpty() = " + tylkoSpacje.isEmpty() + " (FALSE - sa znaki!)");
        System.out.println("\"   \".isBlank() = " + tylkoSpacje.isBlank() + " (TRUE - Java 11 sprawdza TEZ BIALE znaki)");
        assertThat(tylkoSpacje.isEmpty()).isFalse();
        assertThat(tylkoSpacje.isBlank()).isTrue();

        System.out.println("\n--- String.strip() - Unicode-aware odpowiednik trim() ---");
        String zOtoczka = "  Witaj Javo!  ";
        System.out.println("strip(): \"" + zOtoczka.strip() + "\"");
        System.out.println("stripLeading(): \"" + zOtoczka.stripLeading() + "\"");
        System.out.println("stripTrailing(): \"" + zOtoczka.stripTrailing() + "\"");
        assertThat(zOtoczka.strip()).isEqualTo("Witaj Javo!");

        System.out.println("\n--- String.repeat(n) - powtorzenie ciagu znakow ---");
        String separator = "-".repeat(20);
        System.out.println("\"-\".repeat(20) = " + separator);
        assertThat(separator).hasSize(20);

        System.out.println("\n--- String.lines() - Stream<String> po liniach (bez recznego split) ---");
        String tekstWieloliniowy = "linia1\nlinia2\nlinia3";
        List<String> linie = tekstWieloliniowy.lines().toList();
        System.out.println("lines() -> " + linie);
        assertThat(linie).containsExactly("linia1", "linia2", "linia3");
    }

    private static void demonstrateNewFilesMethods() throws IOException {
        System.out.println("\n--- Files.writeString() / Files.readString() - jedna linijka zamiast strumieni ---");
        Path plikTymczasowy = Files.createTempFile("lesson09-java11", ".txt");
        try {
            String tresc = "Java 11 upraszcza operacje na plikach tekstowych.";

            Files.writeString(plikTymczasowy, tresc);
            System.out.println("Files.writeString(sciezka, \"" + tresc + "\");");

            String odczytana = Files.readString(plikTymczasowy);
            System.out.println("Files.readString(sciezka) -> \"" + odczytana + "\"");

            assertThat(odczytana).isEqualTo(tresc);

            System.out.println("\nPelna teoria strumieni/czytnikow plikowych (BufferedReader/Writer, NIO): _04_io/Lesson02-04.");
        } finally {
            Files.deleteIfExists(plikTymczasowy);
        }
    }

    private static void demonstrateVarInLambdaParameters() {
        System.out.println("\n--- var w parametrach lambdy (Java 11) - glownie po to, by MOC dodac adnotacje ---");

        BiFunction<Integer, Integer, Integer> sumaBezVar = (a, b) -> a + b;
        System.out.println("(a, b) -> a + b  -- bez var, standardowa skladnia");

        BiFunction<Integer, Integer, Integer> sumaZVar = (var a, var b) -> a + b;
        System.out.println("(var a, var b) -> a + b  -- Z var, typ nadal WYWNIOSKOWANY przez kompilator");

        assertThat(sumaBezVar.apply(3, 4)).isEqualTo(sumaZVar.apply(3, 4));

        BiFunction<String, String, String> konkatenacjaZAdnotacja =
                (@SuppressWarnings("unused") var a, var b) -> a + b;
        System.out.println("(@SuppressWarnings(\"unused\") var a, var b) -> a + b  -- adnotacja na parametrze lambdy WYMAGA var (nie da sie jej dodac do niejawnego parametru)");
        assertThat(konkatenacjaZAdnotacja.apply("Java", "11")).isEqualTo("Java11");

        System.out.println("\nUWAGA: w lambdzie MUSZA byc UZYTE var na WSZYSTKICH parametrach naraz (mieszanie var z typem jawnym lub brakiem typu - blad kompilacji).");
    }

    private static void explainSingleFileSourceLaunch() {
        System.out.println("\n--- java PlikZrodlowy.java - uruchomienie BEZ jawnego javac (Java 11, JEP 330) ---");
        System.out.println("PRZED Java 11: javac Hello.java && java Hello (2 kroki, dodatkowo plik .class na dysku)");
        System.out.println("OD Java 11:    java Hello.java (1 krok - kompilacja W PAMIECI, .class NIGDY nie trafia na dysk)");
        System.out.println("Ograniczenie: dziala TYLKO dla POJEDYNCZEGO pliku zrodlowego BEZ zaleznosci zewnetrznych - wygodne do SKRYPTOW/nauki/szybkich testow, NIE zastepuje Mavena/Gradle (_11_buildtools) w prawdziwym projekcie.");
        System.out.println("Powiazanie: `_11_buildtools/Lesson01-02` uczyl recznego javac+java - to jest SKROT dla najprostszego przypadku.");
    }
}
