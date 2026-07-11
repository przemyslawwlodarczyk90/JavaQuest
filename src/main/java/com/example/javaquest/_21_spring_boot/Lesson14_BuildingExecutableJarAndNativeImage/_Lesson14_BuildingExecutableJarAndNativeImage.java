package com.example.javaquest._21_spring_boot.Lesson14_BuildingExecutableJarAndNativeImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson14_BuildingExecutableJarAndNativeImage {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: BUDOWANIE WYKONYWALNEGO JAR-A I NATYWNY OBRAZ (GraalVM) ===");

        /*
         * ============================================================
         * 📦 "FAT JAR" - CALA APLIKACJA + WSZYSTKIE ZALEZNOSCI W 1 PLIKU
         * ============================================================
         * `mvnw.cmd package` (cel `repackage` z `spring-boot-maven-plugin`,
         * JUZ obecnego w `pom.xml` tego projektu - sprawdz sekcje
         * `<build><plugins>`) tworzy "wykonywalny jar" - NIE zwykly JAR
         * (ktory wymaga recznego budowania classpath, jak w
         * `_11_buildtools/Lesson01-02`), tylko samowystarczalny plik z
         * WLASNYM, ZAGNIEZDZONYM layoutem: `BOOT-INF/classes/` (Twoj
         * skompilowany kod) + `BOOT-INF/lib/` (WSZYSTKIE zaleznosci,
         * jako ZAGNIEZDZONE pliki .jar) + WLASNY `Main-Class`
         * (`JarLauncher`) potrafiacy URUCHOMIC kod z tej NIETYPOWEJ
         * struktury.
         */
        System.out.println("'mvnw.cmd package' (spring-boot-maven-plugin) tworzy WYKONYWALNY jar z BOOT-INF/classes + BOOT-INF/lib (zagniezdzone .jar) - samowystarczalny.");

        demonstrateSpringBootMavenPluginIsAlreadyConfigured();
        demonstrateNativeImageToolAvailability();
        explainNativeImageTradeoffs();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `mvnw.cmd package` (dzieki `spring-boot-maven-plugin`) daje
         *   JEDEN, wykonywalny plik `.jar` - `java -jar app.jar` i
         *   GOTOWE, BEZ recznego ustawiania classpath.
         * - `BOOT-INF/classes` + `BOOT-INF/lib` (zagniezdzone jary) to
         *   struktura ROZNA od zwyklego "uber jara" (splaszczajacego
         *   WSZYSTKO do jednego poziomu) - unika konfliktow nazw plikow
         *   miedzy bibliotekami.
         * - Natywny obraz (GraalVM `native-image`, NOWOSC Boot 3) -
         *   znaczaco SZYBSZY start/MNIEJSZE zuzycie pamieci, kosztem
         *   DLUGIEGO czasu kompilacji i koniecznosci jawnych "hints"
         *   dla refleksji/proxy (Spring AOT processing generuje je
         *   AUTOMATYCZNIE dla WIEKSZOSCI typowych przypadkow).
         * - DevTools (Lesson09) jest AUTOMATYCZNIE wykluczany z jara -
         *   ZAWSZE, bez dodatkowej konfiguracji.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private static void demonstrateSpringBootMavenPluginIsAlreadyConfigured() {
        System.out.println("\n=== spring-boot-maven-plugin JEST JUZ SKONFIGUROWANY W TYM PROJEKCIE ===");

        Path pomPath = Path.of("pom.xml");
        if (!Files.exists(pomPath)) {
            System.out.println("Nie znaleziono pom.xml w biezacym katalogu - pomijam ten fragment demo.");
            return;
        }
        try {
            String pomContent = Files.readString(pomPath);
            boolean hasPlugin = pomContent.contains("spring-boot-maven-plugin");
            System.out.println("pom.xml zawiera wpis 'spring-boot-maven-plugin': " + hasPlugin);
            System.out.println("-> ZWYKLY `mvnw.cmd package` (bez zadnej dodatkowej konfiguracji) juz DZIALA - Spring Initializr (Lesson02) dodaje ten plugin AUTOMATYCZNIE.");
        } catch (IOException e) {
            System.out.println("Nie udalo sie odczytac pom.xml: " + e.getMessage());
        }
    }

    private static void demonstrateNativeImageToolAvailability() {
        System.out.println("\n=== SPRAWDZENIE DOSTEPNOSCI native-image (GraalVM) NA TEJ MASZYNIE ===");

        try {
            ProcessBuilder builder = new ProcessBuilder("native-image", "--version");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                System.out.println("Sprawdzenie przekroczylo limit czasu - pomijam.");
                return;
            }
            if (process.exitValue() == 0) {
                System.out.println("Narzedzie 'native-image' ZNALEZIONE na tej maszynie - kompilacja natywna JEST mozliwa lokalnie.");
            } else {
                System.out.println("Narzedzie 'native-image' obecne, ale zwrocilo blad - sprawdz instalacje GraalVM.");
            }
        } catch (IOException e) {
            System.out.println("Narzedzie 'native-image' NIE ZNALEZIONE na tej maszynie (GraalVM prawdopodobnie nie jest zainstalowany) - to NORMALNE,");
            System.out.println("kompilacja natywna wymaga OSOBNEJ dystrybucji JDK (GraalVM) - standardowy OpenJDK (uzywany w tym kursie) jej NIE MA.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void explainNativeImageTradeoffs() {
        System.out.println("\n=== KOMPROMISY NATYWNEGO OBRAZU (GraalVM native-image) ===");

        System.out.println("ZALETY: start w MILISEKUNDACH (zamiast sekund JVM), NIZSZE zuzycie pamieci - idealne dla funkcji serverless/kontenerow o krotkim cyklu zycia.");
        System.out.println("WADY: DLUGA kompilacja (minuty, nie sekundy jak zwykly 'javac'), WIEKSZY plik wynikowy, BRAK dynamicznego ladowania klas w runtime.");
        System.out.println("Spring AOT (Ahead-Of-Time) processing (Boot 3) GENERUJE automatycznie 'hints' dla refleksji/proxy dla WIEKSZOSCI typowych przypadkow Springa -");
        System.out.println("ale WLASNA, niestandardowa refleksja (np. `_14_advancedjava/Lesson15-17`) MOZE wymagac RECZNYCH hints (`@RegisterReflectionForBinding` i podobne).");
    }
}
