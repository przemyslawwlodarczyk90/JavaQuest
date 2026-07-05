package com.example.javaquest._11_buildtools.Lesson26_AntMavenGradleComparison;

public class _Lesson26_AntMavenGradleComparison {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 26: ANT vs MAVEN vs GRADLE - POROWNANIE ===");

        /*
         * ============================================================
         * 📦 TRZY FILOZOFIE, TRZY NARZĘDZIA
         * ============================================================
         * Przez cały ten rozdział poznaliśmy po kolei Ant (lekcje 03-10),
         * Maven (lekcje 11-15) i Gradle (lekcje 16-17). Czas zestawić je
         * razem i uporządkować, KIEDY sięgnąć po które.
         *
         * - ANT      -> IMPERATYWNY. Ty piszesz KAŻDY krok (target po
         *   targecie) w XML. Zero wbudowanej konwencji - Ant nie ma
         *   pojęcia, gdzie jest Twój kod źródłowy, dopóki mu nie powiesz.
         *   Pełna kontrola, ale dużo "boilerplate'u" i ręcznej pracy
         *   (patrz zarządzanie classpath w Lekcji 05, zależnościami przez
         *   Ivy w Lekcji 09).
         *
         * - MAVEN    -> DEKLARATYWNY, oparty na KONWENCJI. Ty opisujesz
         *   CO projekt jest i CZEGO potrzebuje (pom.xml), a Maven sam
         *   wie JAK go zbudować (standardowy lifecycle: validate->
         *   compile->test->package->...). Przewidywalność i porządek za
         *   cenę mniejszej elastyczności "poza schematem".
         *
         * - GRADLE   -> HYBRYDA. Konwencje jak Maven (domyślna struktura
         *   katalogów, pluginy), ale konfiguracja to prawdziwy PROGRAM
         *   (DSL na Groovy/Kotlinie) - można dopisać dowolną logikę
         *   (custom tasks, Lekcja 17). Elastyczność Anta + wygoda Mavena,
         *   kosztem stromszej krzywej uczenia (trzeba znać/rozumieć DSL).
         */

        System.out.println("\n=== TRZY FILOZOFIE ===");
        System.out.println("Ant    - IMPERATYWNY, zero konwencji, pelna kontrola (XML, krok po kroku).");
        System.out.println("Maven  - DEKLARATYWNY, konwencja + sztywny lifecycle, duza przewidywalnosc.");
        System.out.println("Gradle - HYBRYDA: konwencje Mavena + programowalny DSL (Groovy/Kotlin).");

        /*
         * ============================================================
         * 🔹 TABELA PORÓWNAWCZA
         * ============================================================
         * Zestawmy trzy narzędzia wg kilku konkretnych kryteriów.
         */

        System.out.println("\n=== TABELA PORÓWNAWCZA ===");
        printComparisonRow("KRYTERIUM", "ANT", "MAVEN", "GRADLE");
        printSeparator();
        printComparisonRow("Paradygmat", "imperatywny", "deklaratywny", "deklarat.+programowalny");
        printComparisonRow("Format konfiguracji", "XML (build.xml)", "XML (pom.xml)", "DSL Groovy/Kotlin");
        printComparisonRow("Konwencja struktury", "brak (Ty decydujesz)", "sztywna (src/main/java...)", "taka jak Maven, elastyczna");
        printComparisonRow("Zarzadzanie zaleznosci.", "brak natywnego (Ivy - dodatek)", "wbudowane (repo, transytywne)", "wbudowane (jak Maven, szybsze)");
        printComparisonRow("Szybkosc builda", "zalezy od Ciebie", "umiarkowana", "szybka (cache, incremental)");
        printComparisonRow("Krzywa uczenia", "niska skladniowo, wysoka utrzymaniowo", "niska (konwencja)", "srednia/wysoka (DSL, custom logic)");
        printComparisonRow("Typowe zastosowanie", "legacy, niestandardowe procesy", "standardowe backendy Java", "Android, Spring Boot, nowe projekty");

        /*
         * ============================================================
         * 🔍 "TA SAMA ZALEŻNOŚĆ" W TRZECH NARZĘDZIACH
         * ============================================================
         * Najbardziej namacalny przykład różnicy filozofii: dodanie tej
         * samej zależności (Gson) do projektu w każdym z trzech narzędzi.
         *
         * ANT nie ma NATYWNEGO mechanizmu zależności - trzeba (a) ręcznie
         * pobrać JAR-a i wrzucić go do lib/, dodając do <path> (Lekcja 05),
         * albo (b) użyć Ivy (Lekcja 09) - osobnego narzędzia dokładanego
         * do Anta. Poniżej najpierw wariant "ręczny" (najbardziej "goły"
         * Ant), potem krótkie porównanie z Maven/Gradle:
         */

        String antManualComment =
                "<!-- Ant: 'zaleznosc' to prosty JAR w lib/, reczne dodanie do classpath -->\n" +
                "<path id=\"classpath\">\n" +
                "    <fileset dir=\"lib\">\n" +
                "        <include name=\"gson-2.11.0.jar\"/>\n" +
                "    </fileset>\n" +
                "</path>\n" +
                "<!-- ...i gson-2.11.0.jar MUSI juz fizycznie istniec w katalogu lib/ -->\n" +
                "<!-- (pobrany recznie albo przez Ivy - patrz Lesson09_AntIvy) -->";

        String mavenDependency =
                "<dependency>\n" +
                "    <groupId>com.google.code.gson</groupId>\n" +
                "    <artifactId>gson</artifactId>\n" +
                "    <version>2.11.0</version>\n" +
                "</dependency>";

        String gradleDependency = "implementation 'com.google.code.gson:gson:2.11.0'";

        System.out.println("\n=== TA SAMA ZALEZNOSC (Gson) W TRZECH NARZEDZIACH ===");
        System.out.println("--- Ant (bez Ivy - reczny JAR w lib/) ---");
        System.out.println(antManualComment);
        System.out.println("\n--- Maven (pom.xml, 5 linii XML) ---");
        System.out.println(mavenDependency);
        System.out.println("\n--- Gradle (build.gradle, 1 linia) ---");
        System.out.println(gradleDependency);
        System.out.println();
        System.out.println("Ten sam cel (uzyc Gson 2.11.0), TRZY bardzo rozne ilosci ceremonii:");
        System.out.println("Ant wymaga fizycznego JAR-a + reczny classpath, Maven deklaruje zaleznosc");
        System.out.println("w XML (Maven sam sciaga JAR z repozytorium), Gradle robi to samo w 1 linii DSL.");

        /*
         * ============================================================
         * 🔍 KIEDY KTÓRE NARZĘDZIE?
         * ============================================================
         * - ANT    -> projekty LEGACY (istniejące od lat, migracja
         *   ryzykowna/nieopłacalna), NIESTANDARDOWE procesy budowania
         *   (np. generowanie plików w nietypowy sposób, integracja
         *   z zewnętrznymi, "dziwnymi" narzędziami), stare środowiska
         *   enterprise, gdzie zmiana narzędzia jest politycznie/
         *   technicznie kosztowna.
         *
         * - MAVEN  -> STANDARDOWE backendy Java (REST API, aplikacje
         *   webowe, mikroserwisy) - gdy struktura projektu jest "zwykła"
         *   i chcesz przewidywalności + ogromnego ekosystemu pluginów +
         *   dobrze znanej konwencji, którą rozumie każdy Javowiec.
         *
         * - GRADLE -> NOWOCZESNE projekty, zwłaszcza gdy potrzebujesz
         *   customowej logiki budowania (custom tasks), wieloprojektowe
         *   monorepo z częstymi rebuildami (build cache = ogromna
         *   różnica w czasie), świat Android (Gradle jest tam
         *   standardem "z definicji"), oraz projekty Spring Boot (coraz
         *   częściej Gradle jako alternatywa dla Mavena).
         *
         * 📌 W praktyce: to NIE jest wybór "na całe życie" - wiele firm
         * ma miks (stare moduły na Ancie, nowe na Mavenie/Gradle) -
         * stąd sens Lekcji 19 (migracje) i Lekcji 20 (build tools w
         * praktyce codziennej pracy).
         */

        System.out.println("\n=== KIEDY KTORE NARZEDZIE? ===");
        System.out.println("Ant    -> legacy, niestandardowe procesy budowania, stare enterprise.");
        System.out.println("Maven  -> standardowe backendy Java, gdy liczy sie przewidywalnosc.");
        System.out.println("Gradle -> nowoczesne projekty, custom buildy, Spring Boot, Android.");
        System.out.println();
        System.out.println("W praktyce to nie decyzja 'raz na zawsze' - firmy czesto miksuja narzedzia");
        System.out.println("(stare moduly na Ancie, nowe na Mavenie/Gradle) - patrz Lekcje 19-20.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ant: imperatywny, zero konwencji, pelna kontrola, brak
         *   natywnego zarzadzania zaleznosciami (Ivy jako dodatek).
         * - Maven: deklaratywny, sztywna konwencja + lifecycle, ogromny
         *   ekosystem pluginow, przewidywalny.
         * - Gradle: hybryda - konwencje jak Maven + programowalny DSL,
         *   szybszy dzieki build cache/incremental build.
         * - Wybor zalezy od kontekstu: legacy -> Ant, standardowy backend
         *   -> Maven, nowoczesny/elastyczny/Android/Spring Boot -> Gradle.
         * - Ta sama zaleznosc (Gson) w Ant/Maven/Gradle = ten sam cel,
         *   3 bardzo rozne ilosci ceremonii - to najlepiej widac roznice
         *   filozofii "na zywo".
         */

        System.out.println("\n=== KONIEC LEKCJI 26 ===");
    }

    private static void printComparisonRow(String criterion, String ant, String maven, String gradle) {
        System.out.printf("%-24s | %-32s | %-28s | %-30s%n", criterion, ant, maven, gradle);
    }

    private static void printSeparator() {
        System.out.println("-".repeat(120));
    }
}
