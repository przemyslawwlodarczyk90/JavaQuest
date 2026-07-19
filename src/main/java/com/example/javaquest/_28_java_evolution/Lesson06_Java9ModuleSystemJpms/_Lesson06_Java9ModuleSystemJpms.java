package com.example.javaquest._28_java_evolution.Lesson06_Java9ModuleSystemJpms;

import java.lang.module.ModuleDescriptor;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson06_Java9ModuleSystemJpms {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: Java 9 (2017) - system modulow (JPMS) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE SZCZEGOLOWO W `_14_advancedjava/Lesson27-28`
         * ============================================================
         * Java 9 (WRZESIEN 2017) TO PIERWSZA wersja NOWEGO,
         * 6-MIESIECZNEGO cyklu (Lesson01) - I ZARAZEM NAJWIEKSZA
         * zmiana W SAMYM JDK OD Javy 1.0: SAM JDK ZOSTAL PODZIELONY
         * NA MODULY (`java.base`, `java.sql`, `java.xml`...) ZAMIAST
         * JEDNEGO monolitycznego `rt.jar`. `_14_advancedjava/
         * Lesson27-28` JUZ nauczyl CIE PISANIA WLASNYCH modulow
         * (`module-info.java`, `exports`/`requires`/`opens`) PRZEZ
         * PRAWDZIWY subprocess `javac --module-source-path` +
         * `java --module-path` - TA lekcja NIE POWTARZA tego (WCIAZ
         * NAJDOKLADNIEJSZE zrodlo).
         *
         * 🔍 TU demo INSPEKCJI modulow JUZ obecnych W URUCHOMIONEJ
         * JVM (`java.lang.Module` API) - BEZ subprocess, PROSTA
         * refleksja NAD modulem `java.base`.
         */
        System.out.println("Java 9 (2017): JPMS - SAM JDK podzielony NA moduly. Pelna teoria PISANIA WLASNYCH modulow: _14_advancedjava/Lesson27-28.");

        inspectJavaBaseModule();
        demonstrateThisProjectStaysOnClasspath();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `java.lang.Module` - reprezentuje modul W runtime -
         *   KAZDA klasa MA `getModule()` (NAWET NA classpath -
         *   "unnamed module", Lesson27 `_14_advancedjava`).
         * - `module-info.java`: `module nazwa { requires ...;
         *   exports ...; }` - JAWNA deklaracja zaleznosci/API.
         * - GLOWNE KORZYSCI: SILNA enkapsulacja (`exports` KONTROLUJE,
         *   CO jest publiczne NA ZEWNATRZ modulu - MOCNIEJSZE NIZ
         *   `public` NA klasie), MNIEJSZY rozmiar runtime
         *   (`jlink` - WLASNY, ZMINIMALIZOWANY runtime).
         * - TEN projekt (javaQuest) CELOWO POZOSTAJE NA classpath
         *   (BEZ `module-info.java` W `src/main/java`) - JPMS
         *   demonstrowany WYLACZNIE W OSOBNYCH katalogach tymczasowych
         *   (`_14_advancedjava/Lesson27-28`), zeby NIE ZLAMAC
         *   kompilacji POZOSTALYCH 27+ rozdzialow.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void inspectJavaBaseModule() {
        System.out.println("\n--- Inspekcja modulu java.base (reflection, java.lang.Module) ---");
        Module javaBaseModule = String.class.getModule();

        System.out.println("Modul klasy String: " + javaBaseModule.getName());
        assertThat(javaBaseModule.getName()).isEqualTo("java.base");

        ModuleDescriptor descriptor = javaBaseModule.getDescriptor();
        List<String> sampleExports = descriptor.exports().stream()
                .map(ModuleDescriptor.Exports::source)
                .filter(pkg -> pkg.equals("java.lang") || pkg.equals("java.util") || pkg.equals("java.io"))
                .sorted()
                .toList();

        System.out.println("Wybrane PUBLICZNE (exports) pakiety modulu java.base: " + sampleExports);
        assertThat(sampleExports).contains("java.lang", "java.util", "java.io");

        System.out.println("Modul java.base jest OTWARTY DLA WSZYSTKICH? " + javaBaseModule.isNamed() + " (TAK - NAZWANY modul, W ODROZNIENIU OD 'unnamed module' ponizej).");
    }

    private static void demonstrateThisProjectStaysOnClasspath() {
        System.out.println("\n--- Ten projekt (javaQuest) dziala W 'unnamed module' (classpath, BEZ module-info.java) ---");
        Module thisClassModule = _Lesson06_Java9ModuleSystemJpms.class.getModule();

        System.out.println("Modul TEJ klasy lekcji: " + (thisClassModule.isNamed() ? thisClassModule.getName() : "unnamed module (classpath)"));
        assertThat(thisClassModule.isNamed()).isFalse();

        System.out.println("Potwierdzone: kod TEGO kursu (BEZ module-info.java) ZAWSZE dziala W 'unnamed module' - "
                + "PRAWDZIWY JPMS (requires/exports MIEDZY WLASNYMI modulami) demonstrowany TYLKO W `_14_advancedjava/Lesson27-28` "
                + "W OSOBNYCH katalogach tymczasowych PRZEZ subprocess javac/java.");
    }
}
