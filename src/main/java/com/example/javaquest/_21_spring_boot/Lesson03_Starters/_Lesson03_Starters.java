package com.example.javaquest._21_spring_boot.Lesson03_Starters;

import java.util.LinkedHashMap;
import java.util.Map;

public class _Lesson03_Starters {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: STARTERY (spring-boot-starter-*) ===");

        /*
         * ============================================================
         * 📦 STARTER = "KURATOR ZALEZNOSCI"
         * ============================================================
         * Lesson02 pokazal REALNE startery w `pom.xml` tego projektu.
         * Dzisiaj: CO dokladnie robi JEDEN starter? `spring-boot-starter-
         * web` to sam w sobie PRAWIE PUSTY plik POM - jego JEDYNA praca
         * to WYCIAGNIECIE (transytywnie) grupy SPRAWDZONYCH, WZAJEMNIE
         * ZGODNYCH bibliotek (Tomcat, Jackson, Spring MVC, Bean
         * Validation) w KONKRETNYCH, PRZETESTOWANYCH RAZEM wersjach -
         * ZAMIAST Ciebie dobierajacego KAZDA z osobna i martwiacego sie,
         * czy wersje sie ZGADZAJA.
         */
        System.out.println("Starter = 'kurator zaleznosci' - 1 wpis w pom.xml SCIAGA grupe SPRAWDZONYCH, wzajemnie ZGODNYCH bibliotek naraz.");

        demonstrateStarterWebBringsManyLibrariesTransitively();
        demonstrateWhatYouWouldNeedWithoutStarters();
        demonstrateStartersAlreadyPresentInThisProject();

        /*
         * ============================================================
         * 🔹 BOM (BILL OF MATERIALS) - SKAD BIORA SIE WERSJE
         * ============================================================
         * `spring-boot-starter-parent` (widoczny w `pom.xml` tego
         * projektu, Lesson02) to "Bill of Materials" - CENTRALNA lista
         * "ta wersja Jacksona idzie z TA wersja Tomcata idzie z TA
         * wersja Spring Frameworka..." - PRZETESTOWANA jako CALOSC przez
         * zespol Springa PRZED KAZDYM wydaniem Boota. Dlatego zaleznosci
         * w `pom.xml` (poza kilkoma wyjatkami jak jbcrypt/jjwt z
         * `_19_security_basics`) NIE MAJA jawnej wersji - dostaja ja Z
         * BOM-u.
         */
        System.out.println("\nspring-boot-starter-parent = BOM (Bill of Materials) - CENTRALNA, PRZETESTOWANA lista 'ktora wersja z ktora wersja pasuje'.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Starter = PRAWIE PUSTY plik POM, ktorego JEDYNA praca to
         *   sciagniecie GRUPY sprawdzonych bibliotek naraz.
         * - BEZ starterow musialbys RECZNIE dobierac KAZDA biblioteke I
         *   jej WERSJE, ryzykujac niezgodnosci.
         * - `spring-boot-starter-parent` (BOM) zarzadza WERSJAMI
         *   WSZYSTKICH bibliotek zarzadzanych przez Boota - dlatego
         *   wiekszosc zaleznosci w `pom.xml` NIE MA jawnej wersji.
         * - Nastepna lekcja (`Lesson04_AutoConfiguration`) pokaze DRUGA
         *   polowe "magii" - jak Boot WIE, ze skoro Tomcat jest na
         *   classpath (dzieki starterowi), to ma go AUTOMATYCZNIE
         *   uruchomic.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void checkClassPresent(String label, String className) {
        try {
            Class.forName(className);
            System.out.println("  " + label + " (" + className + ") -> OBECNA na classpath");
        } catch (ClassNotFoundException e) {
            System.out.println("  " + label + " (" + className + ") -> BRAK na classpath");
        }
    }

    private static void demonstrateStarterWebBringsManyLibrariesTransitively() {
        System.out.println("\n=== JEDEN WPIS 'spring-boot-starter-web' W pom.xml = WIELE BIBLIOTEK NA CLASSPATH ===");

        checkClassPresent("Tomcat (wbudowany serwer, znany z _07_servlets)", "org.apache.catalina.startup.Tomcat");
        checkClassPresent("Jackson (JSON, znany z _04_io/Lesson21)", "com.fasterxml.jackson.databind.ObjectMapper");
        checkClassPresent("Spring MVC (DispatcherServlet)", "org.springframework.web.servlet.DispatcherServlet");
        checkClassPresent("Bean Validation - Hibernate Validator (znany z _19_security_basics/Lesson17)", "org.hibernate.validator.HibernateValidator");

        System.out.println("-> Ty dodales JEDEN wpis w pom.xml (`spring-boot-starter-web`) - Maven SCIAGNAL WSZYSTKIE powyzsze biblioteki transytywnie, W ZGODNYCH wersjach.");
    }

    private static void demonstrateWhatYouWouldNeedWithoutStarters() {
        System.out.println("\n=== BEZ STARTEROW: MUSIALBYS DOBRAC KAZDA BIBLIOTEKE (I WERSJE) RECZNIE ===");

        Map<String, String> manualDependencies = new LinkedHashMap<>();
        manualDependencies.put("org.apache.tomcat.embed:tomcat-embed-core", "10.1.39 (musi PASOWAC do wersji Spring MVC!)");
        manualDependencies.put("com.fasterxml.jackson.core:jackson-databind", "2.18.x (musi PASOWAC do wersji Spring MVC!)");
        manualDependencies.put("org.springframework:spring-webmvc", "6.2.x");
        manualDependencies.put("org.hibernate.validator:hibernate-validator", "8.0.x (musi PASOWAC do wersji Jakarta Validation!)");

        System.out.println("Zamiast 1 wpisu, musialbys jawnie wypisac (i UTRZYMYWAC W ZGODZIE przy KAZDEJ aktualizacji):");
        manualDependencies.forEach((artifact, version) -> System.out.println("  - " + artifact + " -> " + version));
        System.out.println("-> ZGADYWANIE 'ktora wersja z ktora wersja dziala' to DOKLADNIE praca, ktora starter (+ BOM) robi ZA CIEBIE.");
    }

    private static void demonstrateStartersAlreadyPresentInThisProject() {
        System.out.println("\n=== STARTERY JUZ OBECNE W TYM PROJEKCIE - CO KAZDY Z NICH DAJE ===");

        Map<String, String> starterPurpose = new LinkedHashMap<>();
        starterPurpose.put("spring-boot-starter", "rdzen: auto-konfiguracja, logowanie (Logback), YAML - podstawa KAZDEJ aplikacji Boot");
        starterPurpose.put("spring-boot-starter-web", "Spring MVC + wbudowany Tomcat + Jackson - REST API/aplikacje webowe (_18_rest_api, _22_spring_web)");
        starterPurpose.put("spring-boot-starter-validation", "Bean Validation (Hibernate Validator) - _19_security_basics/Lesson17");
        starterPurpose.put("spring-boot-starter-aop", "Spring AOP + AspectJ - _20_spring_core/Lesson21-22");
        starterPurpose.put("spring-boot-starter-test", "JUnit 5 + Mockito + AssertJ - PRZYSZLY, osobny rozdzial o testowaniu");

        starterPurpose.forEach((starter, purpose) -> System.out.println("  " + starter + "\n    -> " + purpose));
        System.out.println("-> pelna, PROGRAMOWA lista (sparsowana z REALNEGO pom.xml) byla juz w Lesson02 - tu skupiamy sie na PRZEZNACZENIU kazdego.");
    }
}
