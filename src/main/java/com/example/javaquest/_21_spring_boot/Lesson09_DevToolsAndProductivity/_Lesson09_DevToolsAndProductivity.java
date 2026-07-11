package com.example.javaquest._21_spring_boot.Lesson09_DevToolsAndProductivity;

public class _Lesson09_DevToolsAndProductivity {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: spring-boot-devtools I PRODUKTYWNOSC DEWELOPERSKA ===");

        /*
         * ============================================================
         * 📦 CZYM JEST spring-boot-devtools
         * ============================================================
         * `spring-boot-devtools` to zestaw narzedzi DEV-ONLY,
         * przyspieszajacych PETLE "zmien kod -> zobacz efekt": (1)
         * AUTOMATYCZNY RESTART aplikacji po zapisaniu zmiany w kodzie
         * (SZYBSZY niz recznie zatrzymywac i uruchamiac JVM od nowa -
         * uzywa DWOCH classloaderow: "base" dla bibliotek zewnetrznych,
         * KTORE sie NIE zmieniaja, i "restart" dla TWOJEGO kodu, KTORY
         * jest przeladowywany), (2) LiveReload (wbudowany serwer
         * powiadamiajacy przegladarke o potrzebie odswiezenia), (3)
         * WYLACZENIE cache'owania szablonow/zasobow statycznych W TRYBIE
         * DEV.
         */
        System.out.println("spring-boot-devtools = auto-restart (2 classloadery) + LiveReload + wylaczenie cache'owania w dev - przyspiesza petle 'zmien->zobacz'.");

        demonstrateDevToolsNotOnClasspathInThisProject();
        explainRestartClassLoaderMechanism();
        explainAutomaticExclusionFromProductionJar();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DevTools SPECJALNIE NIE jest dodany do TEGO projektu -
         *   ten kurs uczy Cie MECHANIZMU, nie kaze go WLACZAC na sztywno.
         * - Dodaje sie go z `<optional>true</optional>` (lub scope
         *   `runtime`) - dzieki temu NIE trafia TRANZYTYWNIE do
         *   projektow, ktore ZALEZA od Twojego (nikt nie chce cudzego
         *   auto-restartu w SWOJEJ aplikacji).
         * - `spring-boot-maven-plugin` (`repackage`) AUTOMATYCZNIE
         *   WYKLUCZA DevTools z finalnego, produkcyjnego "fat jara"
         *   (Lesson14) - bez zadnej dodatkowej konfiguracji.
         * - DevTools to WYGODA, NIE mechanizm produkcyjny - w
         *   przeciwienstwie do auto-konfiguracji (Lesson04), ktora
         *   dziala WSZEDZIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateDevToolsNotOnClasspathInThisProject() {
        System.out.println("\n=== DevTools NIE JEST DODANY DO TEGO PROJEKTU (SWIADOMA DECYZJA DYDAKTYCZNA) ===");

        try {
            Class.forName("org.springframework.boot.devtools.restart.Restarter");
            System.out.println("Klasa DevTools ZNALEZIONA na classpath - nieoczekiwane dla tego projektu!");
        } catch (ClassNotFoundException e) {
            System.out.println("Klasa 'org.springframework.boot.devtools.restart.Restarter' NIE ZNALEZIONA na classpath.");
            System.out.println("-> TEN kurs celowo NIE dodaje `spring-boot-devtools` jako stalej zaleznosci projektu -");
            System.out.println("   ta lekcja uczy MECHANIZMU, zebys wiedzial CO dodac (`spring-boot-starter` + `spring-boot-devtools`, scope 'runtime', optional=true), gdy WLASNY projekt tego potrzebuje.");
        }
    }

    private static void explainRestartClassLoaderMechanism() {
        System.out.println("\n=== MECHANIZM: DWA CLASSLOADERY (base + restart) ===");

        System.out.println("BEZ DevTools: zmiana 1 linii kodu = zatrzymaj CALA JVM, uruchom OD ZERA (wolno - Tomcat/Spring/wszystkie biblioteki ladowane od nowa).");
        System.out.println("Z DevTools: 2 classloadery -");
        System.out.println("  'base classloader' - laduje biblioteki ZEWNETRZNE (Spring, Tomcat, Jackson...) - RAZ, NIE zmienia sie miedzy restartami.");
        System.out.println("  'restart classloader' - laduje TWOJ kod (`src/main/java`) - TEN jest ODRZUCANY i TWORZONY OD NOWA przy KAZDEJ zmianie.");
        System.out.println("-> restart jest SZYBSZY, bo NIE trzeba ladowac PONOWNIE bibliotek zewnetrznych - TYLKO Twoj (zazwyczaj MALY) kod.");
    }

    private static void explainAutomaticExclusionFromProductionJar() {
        System.out.println("\n=== DevTools JEST AUTOMATYCZNIE WYKLUCZANY Z PRODUKCYJNEGO JAR-A ===");

        System.out.println("Gdyby DevTools byl dodany do pom.xml, `spring-boot-maven-plugin` (cel 'repackage', Lesson14) i tak");
        System.out.println("WYKLUCZYLBY go z finalnego, wykonywalnego jara - DevTools NIGDY nie trafia na produkcje, BEZ dodatkowej konfiguracji.");
        System.out.println("-> to swiadomy projekt Boota: narzedzie deweloperskie NIE MOZE przypadkiem 'wyciec' do srodowiska produkcyjnego.");
    }
}
