package com.example.javaquest._28_java_evolution.Lesson23_ChoosingJavaVersionForNewProjects;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson23_ChoosingJavaVersionForNewProjects {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 23: Wybor wersji Javy dla NOWEGO projektu - LTS vs najnowsza, strategie migracji ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - PRAKTYCZNA decyzja, NIE POJEDYNCZA funkcja jezyka
         * ============================================================
         * PO 22 lekcjach retrospektywy (Java 8 -> 25) NALEZY zadac
         * pytanie PRAKTYCZNE: "KTOREJ wersji uzyc DLA NOWEGO projektu
         * DZISIAJ (2026)?". Ta lekcja LACZY fakty Z Lesson01 (cykl
         * wydan, LTS) Z DOSWIADCZENIEM zebranym W tym rozdziale.
         */
        System.out.println("Po 22 lekcjach retrospektywy: JAKA wersja Javy dla NOWEGO projektu w 2026 roku?");

        explainLtsVsNonLts();
        demonstrateVersionSelectionDecisionMatrix();
        explainMigrationStrategy();
        explainWhyThisCourseTargetsJava21();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - LTS (Long-Term Support): 8, 11, 17, 21, 25 - WSPARCIE
         *   PRZEZ LATA (produkcyjny wybor domyslny DLA WIEKSZOSCI
         *   firm).
         * - NIE-LTS (9,10,12,13,...): WSPARCIE TYLKO DO nastepnego
         *   wydania (6 miesiecy) - dobre DO eksperymentow/nauki
         *   nowosci PRZED ich stabilizacja, ZLE DO produkcji
         *   dlugoterminowej.
         * - "Najnowsza LTS mniejsza NIZ najnowsza wersja" - typowa
         *   REKOMENDACJA DLA NOWYCH projektow produkcyjnych (STABILNOSC
         *   + WSPARCIE + EKOSYSTEM/frameworki NADAZAJACE za wersja).
         * - Strategia migracji: MALE, CZESTE kroki (LTS->LTS, NIE
         *   przeskakiwanie WIELU wersji naraz), zawsze Z testami
         *   regresji (powiazanie Z `_25_unit_testing`/`_26_integration_testing`).
         */
        System.out.println("\n=== KONIEC LEKCJI 23 ===");
    }

    private static void explainLtsVsNonLts() {
        System.out.println("\n--- LTS vs NIE-LTS - przypomnienie z Lesson01 ---");
        System.out.println("LTS (Long-Term Support): Java 8, 11, 17, 21, 25 - wsparcie dostawcow (Oracle/Eclipse Temurin/Amazon Corretto) PRZEZ WIELE LAT.");
        System.out.println("NIE-LTS (9,10,12,13,14,15,16,18,19,20,22,23,24): wsparcie TYLKO DO WYDANIA kolejnej wersji (6 miesiecy) - potem BRAK aktualizacji bezpieczenstwa.");
        System.out.println("KONSEKWENCJA: uzycie Javy NIE-LTS na produkcji WYMAGA aktualizacji CO 6 MIESIECY, bez wyjatku - dla WIEKSZOSCI firm NIEPRAKTYCZNE.");
    }

    private static void demonstrateVersionSelectionDecisionMatrix() {
        System.out.println("\n--- Macierz decyzyjna: jaka wersje wybrac? ---");
        Map<String, String> macierz = new LinkedHashMap<>();
        macierz.put("Nowy projekt produkcyjny, dlugi horyzont", "Najnowsza LTS (dzis: Java 25) - najdluzsze wsparcie OD startu.");
        macierz.put("Istniejacy projekt na starszej LTS, dziala dobrze", "ZOSTAN na obecnej LTS, DOPOKI wsparcie dostawcy trwa - migracja TYLKO gdy jest KONKRETNY powod (koniec wsparcia, potrzebna funkcja).");
        macierz.put("Nauka/eksperymenty Z NAJNOWSZYMI funkcjami preview", "Najnowsza NIE-LTS (dzis: Java 24) - dostep DO funkcji preview PRZED stabilizacja.");
        macierz.put("Framework/biblioteka Z OGRANICZONYM wsparciem nowych wersji", "SPRAWDZ najpierw wymagania frameworka (np. Spring Boot 3.x wymaga Java 17+) - TO CZESTO ogranicza wybor.");
        macierz.put("Aplikacja embedded/o OGRANICZONYCH zasobach", "Rozwaz NAJNOWSZA LTS DLA korzysci Z ZUZYCIA pamieci/GC (Generational ZGC, compact object headers z Java 24-25).");

        for (Map.Entry<String, String> wpis : macierz.entrySet()) {
            System.out.println("Scenariusz: " + wpis.getKey());
            System.out.println("  -> Rekomendacja: " + wpis.getValue());
        }

        assertThat(macierz).hasSize(5);
    }

    private static void explainMigrationStrategy() {
        System.out.println("\n--- Strategia migracji miedzy wersjami ---");
        System.out.println("1. Migruj LTS->LTS (np. 8->11->17->21), NIE przeskakuj WIELU wersji naraz - kazdy krok MA znaczaco MNIEJSZY zakres zmian.");
        System.out.println("2. PRZED migracja: sprawdz `--release <wersja>` lokalnie (jak Lesson21/22 tego rozdzialu) - wykryj BLEDY kompilacji WCZESNIE.");
        System.out.println("3. Zaktualizuj ZALEZNOSCI (Maven/Gradle - powiazanie Z `_11_buildtools`) - stare wersje bibliotek MOGA nie wspierac nowego JDK.");
        System.out.println("4. URUCHOM PELNY pakiet testow (powiazanie Z `_25_unit_testing`/`_26_integration_testing`) PO KAZDYM kroku migracji - regresje CZESTO ujawniaja sie W runtime, NIE W kompilacji (przypomnienie Z `_22_spring_web` - springdoc/Security auto-konfiguracja).");
        System.out.println("5. Migracja `javax.*` -> `jakarta.*` (Spring Boot 2->3, Lesson-wspomnienie Z `_20_spring_core/Lesson02`) TO NAJWIEKSZA, NAJBARDZIEJ PRACOCHLONNA zmiana W historii ekosystemu Javy EE/Jakarta EE - PLANUJ na nia OSOBNY czas.");
    }

    private static void explainWhyThisCourseTargetsJava21() {
        System.out.println("\n--- Dlaczego TEN kurs (javaQuest) celuje w Java 21 ---");
        System.out.println("Java 21 (LTS, wrzesien 2023) byla NAJNOWSZA LTS W MOMENCIE rozpoczecia tego projektu - RODZAJ 'sweet spot': STABILNA (LTS), Z rekordami/sealed/pattern matching/watkami wirtualnymi JUZ finalnymi.");
        System.out.println("Java 25 (LTS, wrzesien 2025) jest TERAZ najnowsza LTS - kurs SWIADOMIE NIE zostal przepisany NA 25, bo: (a) Java 21 pozostaje w PELNI wspierana, (b) wiekszosc projektow enterprise WCIAZ MIGRUJE z wolniejszym tempem, (c) funkcje Z Javy 22-25 SA udokumentowane W tym WLASNIE rozdziale (Lesson21-22) - kursant POZNAJE je SWIADOMIE, jako 'co dalej', zamiast W milczacym baseline.");
        System.out.println("Praktyczna lekcja: baseline projektu TO DECYZJA, nie przypadek - i CZASEM 'nie najnowsza wersja' jest wlasnie SLUSZNYM wyborem.");
    }
}
