package com.example.javaquest._28_java_evolution.Lesson01_JavaReleaseCadenceAndLtsExplained;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson01_JavaReleaseCadenceAndLtsExplained {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: Cykl wydawniczy Javy i LTS ===");

        /*
         * ============================================================
         * 📦 OD "Javy co kilka lat" DO "Javy co 6 miesiecy" - ZMIANA Z 2017 ROKU
         * ============================================================
         * PRZED Java 9 (2017): NOWA wersja Javy wychodzila
         * NIEREGULARNIE, CZASEM PO 2-3 LATACH (Java 7->8 TO AZ
         * 2 lata, Java 8->9 TO PONAD 3 LATA) - funkcje "CZEKALY" NA
         * siebie NAWZAJEM, OPOZNIAJAC CALY release.
         *
         * OD Java 9 (wrzesien 2017): SCISLY, 6-MIESIECZNY cykl -
         * NOWA wersja KAZDEGO marca I wrzesnia, NIEZALEZNIE OD tego,
         * CZY DANA funkcja jest GOTOWA (NIEGOTOWA funkcja PO PROSTU
         * CZEKA NA KOLEJNY release, NIE BLOKUJE calosci) - powiazanie
         * Z `_01_fundamentals/Lesson00_JavaPlatformBasics`
         * (JDK/JRE/JVM), TEN rozdzial ROZWIJA temat W OSI CZASU.
         *
         * 🔍 LTS (Long-Term Support) - CO KILKA wersji (NIE KAZDA!)
         * jest oznaczona jako LTS - DOSTAJE POPRAWKI bezpieczenstwa
         * PRZEZ LATA (typowo minimum 8 lat OD dostawcow takich jak
         * Oracle) - WIEKSZOSC PRODUKCYJNYCH projektow (WLACZNIE Z
         * TYM kursem - `pom.xml`: `--release 21`) TRZYMA sie WYLACZNIE
         * wersji LTS.
         */
        System.out.println("Od Javy 9 (2017): SCISLY 6-miesieczny cykl (marzec+wrzesien). LTS = wersja Z DLUGOTERMINOWYM wsparciem, NIE KAZDA wersja.");

        demonstrateReleaseTimeline();
        demonstrateLtsVersions();
        demonstrateProjectBaseline();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Java 8 (marzec 2014) - OSTATNIA "duza", NIEREGULARNIE
         *   wydana wersja PRZED nowym cyklem - LTS, WCIAZ SZEROKO
         *   uzywana W 2026 (STARE projekty).
         * - Java 9-16 - wersje NIE-LTS (feature releases, WSPIERANE
         *   TYLKO DO kolejnej wersji, ~6 miesiecy).
         * - Java 11 (wrzesien 2018) - PIERWSZA LTS PO NOWYM cyklu.
         * - Java 17 (wrzesien 2021) - LTS, PIERWSZA WYMAGAJACA
         *   `jakarta.*` DLA Spring Boot 3 (`_20_spring_core/Lesson02`).
         * - Java 21 (wrzesien 2023) - LTS, CEL TEGO projektu
         *   (`pom.xml`), pierwsza Z FINALNYMI wirtualnymi watkami
         *   (Lesson19) I record patterns (Lesson18).
         * - Java 25 (wrzesien 2025) - NAJNOWSZA LTS (na dzien pisania
         *   tego kursu) - maszyna deweloperska UZYWA OpenJDK 25.0.2
         *   DO URUCHAMIANIA (`--release 21` TO CEL KOMPILACJI, NIE
         *   wersja URUCHOMIENIOWA - powiazanie Z
         *   `_25_unit_testing/Lesson11` Zadanie: `@EnabledOnJre`).
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    record JavaRelease(int version, String releaseDate, boolean isLts) {
    }

    private static void demonstrateReleaseTimeline() {
        System.out.println("\n--- Wybrane wydania Javy - OS czasu ---");
        List<JavaRelease> releases = List.of(
                new JavaRelease(8, "marzec 2014", true),
                new JavaRelease(9, "wrzesien 2017", false),
                new JavaRelease(10, "marzec 2018", false),
                new JavaRelease(11, "wrzesien 2018", true),
                new JavaRelease(17, "wrzesien 2021", true),
                new JavaRelease(21, "wrzesien 2023", true),
                new JavaRelease(25, "wrzesien 2025", true)
        );

        for (JavaRelease release : releases) {
            System.out.printf("Java %-3d | %-15s | %s%n", release.version(), release.releaseDate(),
                    release.isLts() ? "LTS" : "non-LTS (krotkie wsparcie)");
        }

        assertThat(releases).filteredOn(JavaRelease::isLts).hasSizeGreaterThanOrEqualTo(5);
    }

    private static void demonstrateLtsVersions() {
        System.out.println("\n--- Dlaczego LTS jest wazne DLA projektow produkcyjnych ---");
        Map<Integer, String> ltsSupportReason = new TreeMap<>(Map.of(
                8, "WCIAZ szeroko uzywana - OGROMNA baza istniejacego kodu (legacy)",
                11, "PIERWSZA LTS bez darmowych, komercyjnych aktualizacji Oracle po pewnym czasie (=> OpenJDK/inni dostawcy)",
                17, "WYMAGANA DLA Spring Boot 3 / Jakarta EE 9+ (namespace jakarta.*)",
                21, "CEL tego kursu - virtual threads, pattern matching w switch, sequenced collections",
                25, "NAJNOWSZA LTS - maszyna deweloperska tego kursu URUCHAMIA na niej testy"
        ));

        ltsSupportReason.forEach((version, reason) ->
                System.out.println("Java " + version + " (LTS): " + reason));
    }

    private static void demonstrateProjectBaseline() {
        System.out.println("\n--- Baseline TEGO projektu (javaQuest) ---");
        String javaVersionRuntime = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");

        System.out.println("Wersja URUCHOMIENIOWA (java.version): " + javaVersionRuntime + " (dostawca: " + javaVendor + ")");
        System.out.println("Wersja DOCELOWA kompilacji (pom.xml --release): 21 (LTS)");
        System.out.println("TO SA 2 ROZNE rzeczy: mozna KOMPILOWAC pod starsza wersje (--release 21), URUCHAMIAJAC na NOWSZEJ JVM (25) - "
                + "powiazanie Z `_25_unit_testing/Lesson11`, gdzie @EnabledOnJre(JRE.JAVA_21) byl 'skipped' na tej WLASNIE maszynie.");

        assertThat(javaVersionRuntime).isNotBlank();
    }
}
