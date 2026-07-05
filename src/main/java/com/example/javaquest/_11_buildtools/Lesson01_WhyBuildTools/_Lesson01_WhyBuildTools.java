package com.example.javaquest._11_buildtools.Lesson01_WhyBuildTools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class _Lesson01_WhyBuildTools {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LESSON 1: Po co sa build toole? ===\n");

        /*
         * ============================================================
         * 📦 CO TO JEST "BUILD"?
         * ============================================================
         * "Build" (proces budowania) to wszystko, co trzeba zrobic, aby
         * z kodu zrodlowego (plikow .java, zasobow, konfiguracji) powstal
         * dzialajacy, gotowy do uruchomienia/wdrozenia program. W skladzie
         * typowego builda znajdziemy m.in.:
         *
         *  - KOMPILACJA - przeksztalcenie plikow .java na bajtkod .class
         *    (robi to javac - poznamy go dokladnie w Lekcji 2).
         *  - KOPIOWANIE ZASOBOW - pliki .properties, .xml, obrazki, itp.
         *    musza wylądowac w odpowiednim miejscu razem z klasami.
         *  - TESTY - automatyczne uruchomienie testow jednostkowych i
         *    integracyjnych, zanim uznamy build za "zielony".
         *  - PAKOWANIE - spakowanie skompilowanych klas i zasobow do
         *    jednego pliku JAR (aplikacja/biblioteka) albo WAR (aplikacja
         *    webowa) - poznamy to w Lekcji 2.
         *  - RAPORTY - np. raport z pokrycia testami, raport z analizy
         *    statycznej kodu, raport z testow (kto/co nie przeszlo).
         *  - DEPLOYMENT (WDROZENIE) - skopiowanie/wgranie gotowej paczki
         *    na serwer testowy, produkcyjny, do repozytorium artefaktow.
         *  - AUTOMATYZACJA CALOSCI - polaczenie wszystkich powyzszych
         *    krokow w jedna, powtarzalna sekwencje, ktora mozna odpalic
         *    jedna komenda (lub jednym kliknieciem w CI/CD).
         *
         * Kazdy z tych krokow da sie wykonac RECZNIE. Problem nie jest w
         * tym, ze pojedynczy krok jest trudny - problem jest w skali i w
         * powtarzalnosci. To zaraz pokazemy prawdziwym demo nizej.
         */

        /*
         * ============================================================
         * 🔹 PROBLEM WIEKSZYCH PROJEKTOW
         * ============================================================
         * W male, jednoplikowym programie (jak wiele przykladow z
         * poprzednich rozdzialow tego kursu) reczny build jest trywialny:
         * jeden javac, jeden java. Ale prawdziwe projekty komercyjne to:
         *
         *  - WIELE KLAS W WIELU FOLDERACH - setki albo tysiace plikow
         *    .java rozlozonych w drzewie pakietow (np. com.firma.projekt.*).
         *  - BIBLIOTEKI ZEWNETRZNE - dziesiatki JAR-ow (frameworki,
         *    narzedzia, sterowniki baz danych), kazdy w konkretnej wersji,
         *    czesto z wlasnymi zaleznosciami przechodnimi (transitive).
         *  - TESTY - osobny zestaw klas testowych, osobny classpath,
         *    osobna faza budowania (nie chcemy pakowac testow do
         *    produkcyjnego JAR-a).
         *  - ROZNE SRODOWISKA - dev/test/prod maja rozne pliki
         *    konfiguracyjne, rozne adresy baz danych, czasem rozny kod
         *    (np. flagi debugowania wylaczone na produkcji).
         *  - ROZNE JDK - jeden projekt moze byc budowany na JDK 17 przez
         *    jednego programiste i JDK 21 przez drugiego - build tool
         *    pozwala to jednoznacznie wymusic i sprawdzic.
         *
         * Reczne "klikanie" (albo reczne wpisywanie komend javac/java dla
         * kazdego pliku z osobna) przy takiej skali jest: WOLNE, NUDNE
         * i - co najgorsze - PODATNE NA BLEDY LUDZKIE (zapomniany plik,
         * zla kolejnosc kompilacji, zla wersja biblioteki na classpath).
         */

        /*
         * ============================================================
         * 🔍 DEMO: RECZNY BUILD "NA WLASNEJ SKORZE"
         * ============================================================
         * Zamiast tylko OPOWIADAC o bolu recznego builda, ponizej
         * NAPRAWDE go przezyjemy - na malej, 3-plikowej "aplikacji".
         * Wygenerujemy 3 pliki .java na dysku, a potem skompilujemy i
         * uruchomimy je KROK PO KROKU, tak jak zrobilby to programista
         * bez zadnego build toola - przez osobne wywolania procesow
         * javac i java (ProcessBuilder), liczac kazdy reczny krok.
         */

        Path projectDir = Files.createTempDirectory("lesson01-manual-build");
        Path classesDir = projectDir.resolve("classes");
        Files.createDirectories(classesDir);
        System.out.println("Katalog projektu (tymczasowy): " + projectDir);
        System.out.println("Katalog wynikowy klas:          " + classesDir + "\n");

        // KROK A: generujemy 3 male, powiazane ze soba pliki zrodlowe -
        // w prawdziwym projekcie to byloby np. 500 plikow w 40 pakietach.
        // Tu dla przejrzystosci demo uzywamy domyslnego pakietu (bez
        // "package ...;") - to uproszczenie tylko na potrzeby tej lekcji.
        Path helper1 = projectDir.resolve("MathHelper.java");
        Path helper2 = projectDir.resolve("TextHelper.java");
        Path mainSrc = projectDir.resolve("Main.java");

        Files.writeString(helper1, """
                public class MathHelper {
                    public static int square(int x) {
                        return x * x;
                    }
                }
                """);

        Files.writeString(helper2, """
                public class TextHelper {
                    public static String shout(String text) {
                        return text.toUpperCase() + "!";
                    }
                }
                """);

        Files.writeString(mainSrc, """
                public class Main {
                    public static void main(String[] args) {
                        System.out.println(TextHelper.shout("build dziala"));
                        System.out.println("3 do kwadratu = " + MathHelper.square(3));
                    }
                }
                """);

        System.out.println("Wygenerowano 3 pliki zrodlowe: MathHelper.java, TextHelper.java, Main.java\n");

        String javaHome = System.getProperty("java.home");
        String javacExe = javaHome + java.io.File.separator + "bin" + java.io.File.separator + "javac";
        String javaExe = javaHome + java.io.File.separator + "bin" + java.io.File.separator + "java";

        List<String> manualSteps = new ArrayList<>();

        // KROK 1: kompilujemy PIERWSZA klase pomocnicza - osobna komenda,
        // osobne wywolanie javac. Programista musi PAMIETAC, ze ten plik
        // trzeba skompilowac jako pierwszy (bo nic od niego nie zalezy).
        runManualStep(manualSteps, javacExe, "-d", classesDir.toString(), helper1.toString());

        // KROK 2: kompilujemy DRUGA klase pomocnicza - kolejna, osobna
        // komenda. Znowu trzeba pamietac o poprawnym katalogu -d.
        runManualStep(manualSteps, javacExe, "-d", classesDir.toString(), helper2.toString());

        // KROK 3: kompilujemy Main.java - TA klasa zalezy od dwoch
        // poprzednich, wiec trzeba dodac -cp (classpath) wskazujacy na
        // katalog z juz skompilowanymi MathHelper.class i TextHelper.class.
        // Gdyby ktos pomylil kolejnosc (skompilowal Main PRZED helperami),
        // dostalby blad kompilacji "cannot find symbol".
        runManualStep(manualSteps, javacExe, "-d", classesDir.toString(),
                "-cp", classesDir.toString(), mainSrc.toString());

        // KROK 4: uruchamiamy skompilowany program - kolejna, osobna
        // komenda, znowu z reczne wskazanym classpath.
        runManualStep(manualSteps, javaExe, "-cp", classesDir.toString(), "Main");

        System.out.println("\n📌 Podsumowanie recznego builda:");
        System.out.println("   Liczba recznie wpisanych/wywolanych komend: " + manualSteps.size());
        for (int i = 0; i < manualSteps.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + manualSteps.get(i));
        }

        /*
         * ============================================================
         * 🔍 A TERAZ WYOBRAZ SOBIE...
         * ============================================================
         * Dla TRZECH plikow potrzebowalismy 4 recznych komend, w
         * konkretnej kolejnosci, z reczne wpisywanym classpathem. A teraz
         * pomnoz to przez:
         *  - setki/tysiace klas zamiast 3,
         *  - dziesiatki bibliotek zewnetrznych na classpath,
         *  - osobna kompilacje i uruchomienie testow,
         *  - spakowanie wyniku do JAR-a z poprawnym manifestem,
         *  - powtorzenie WSZYSTKIEGO od zera po kazdej zmianie w kodzie,
         *  - i zrobienie tego identycznie u KAZDEGO programisty w zespole
         *    oraz na serwerze CI/CD.
         *
         * Reczne robienie tego nie jest "trudne intelektualnie" - jest
         * NIEWYKONALNE w praktyce (zbyt wolne, zbyt podatne na bledy,
         * niemozliwe do wiernego powtorzenia przez kazdego czlonka
         * zespolu w ten sam sposob).
         */

        /*
         * ============================================================
         * 🔹 BUILD TOOL = AUTOMATYZACJA
         * ============================================================
         * Build tool (Ant, Maven, Gradle - poznamy wszystkie trzy w tym
         * rozdziale) to program, ktory:
         *
         *  - Zna KOLEJNOSC krokow (kompilacja -> testy -> pakowanie ->
         *    ...) i wykonuje je za nas, zawsze w tej samej kolejnosci.
         *  - Pozwala opisac CALY build JEDNYM plikiem konfiguracyjnym
         *    (build.xml w Ancie, pom.xml w Mavenie, build.gradle w
         *    Gradle) - "przepis", ktory dziala identycznie u kazdego.
         *  - Odpala sie JEDNA komenda (np. "mvn package") zamiast
         *    dziesiatek recznych komend javac/java.
         *  - Jest POWTARZALNY - ten sam przepis, ten sam wynik, u kazdego
         *    programisty i na kazdym serwerze CI/CD (Jenkins, GitHub
         *    Actions, GitLab CI...).
         *  - Redukuje liczbe bledow ludzkich - nikt nie zapomni
         *    skompilowac jakiegos pliku, bo build tool wie o WSZYSTKICH.
         *
         * W kolejnych lekcjach poznamy dokladnie mechanizm, na ktorym
         * budowane sa wszystkie build toole (javac/java/jar/classpath -
         * Lekcja 2), a potem trzy konkretne narzedzia: Apache Ant
         * (Lekcje 3-10), Maven (Lekcje 11-15) i Gradle (Lekcje 16-17).
         */

        System.out.println("\n=== KONIEC LEKCJI 1 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Build = kompilacja + zasoby + testy + pakowanie + raporty +
         *   deployment + automatyzacja calosci.
         * - Wieksze projekty (wiele klas, biblioteki, testy, wiele
         *   srodowisk/JDK) czynia reczny build niewykonalnym w praktyce.
         * - Powyzsze demo NAPRAWDE wykonalo reczny build 3 plikow przez
         *   4 osobne komendy javac/java (ProcessBuilder) - w realnym
         *   projekcie ta liczba rosnie do setek/tysiecy krokow.
         * - Build tool = automatyzacja calego tego procesu jedna komenda,
         *   powtarzalnie, u kazdego czlonka zespolu i na CI/CD.
         * - Lekcja 2 pokaze dokladnie mechanizm javac/java/jar/classpath,
         *   na ktorym oparte sa wszystkie build toole.
         */
    }

    /**
     * Wykonuje JEDNA "reczna" komende (javac albo java) jako osobny
     * proces systemowy, wypisuje jej dokladna postac (tak jak
     * wpisalby ja programista w terminalu), przechwytuje i wypisuje
     * jej output, a na koniec dolicza ja do listy wykonanych krokow.
     */
    private static void runManualStep(List<String> stepsLog, String... command) throws IOException, InterruptedException {
        String commandLine = String.join(" ", command);
        System.out.println(">>> " + commandLine);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (InputStream in = process.getInputStream()) {
            byte[] output = in.readAllBytes();
            if (output.length > 0) {
                System.out.print(new String(output));
            }
        }

        int exitCode = process.waitFor();
        System.out.println("(kod wyjscia: " + exitCode + ")\n");

        stepsLog.add(commandLine);
    }
}
