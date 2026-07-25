package com.example.javaquest._31_spring_cloud_microservices.Lesson17_ContainerizingSpringBootApps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson17_ContainerizingSpringBootApps {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 17: Konteneryzacja aplikacji Spring Boot (Docker) ===");

        /*
         * ============================================================
         * 📦 DLACZEGO kontenery DLA mikroserwisow
         * ============================================================
         * Mikroserwisy CZESTO wdraza sie NA Kubernetesie/podobnej
         * platformie - WYMAGA to, zeby KAZDY serwis byl PAKOWANY jako
         * OBRAZ kontenera (Docker image). Powiazanie Z
         * `_11_buildtools` - TAM uczyles sie budowac JAR (fat jar,
         * Lesson14 W `_21_spring_boot`); TU pakujemy TEN JAR W OBRAZ,
         * KTORY MOZNA uruchomic NA DOWOLNEJ maszynie Z Dockerem, BEZ
         * instalowania Javy osobno.
         *
         * "Multi-stage build" - DWA etapy W JEDNYM Dockerfile: (1)
         * budowanie (potrzebuje Mavena+JDK, DUZY obraz), (2)
         * uruchamianie (potrzebuje TYLKO JRE, MALY obraz) - finalny
         * obraz NIE ZAWIERA narzedzi budowania, TYLKO gotowy JAR.
         */
        System.out.println("Multi-stage build: etap 1 (Maven+JDK, budowanie) -> etap 2 (TYLKO JRE, uruchamianie) - finalny obraz MALY.");

        Path projectDir = Files.createTempDirectory("lesson17-docker-demo");
        Path dockerfile = writeDockerfile(projectDir);
        Path dockerignore = writeDockerignore(projectDir);
        System.out.println("\nWygenerowano Dockerfile W: " + dockerfile);
        System.out.println("Wygenerowano .dockerignore W: " + dockerignore);

        demonstrateLayerCachingConcept();

        boolean dockerCliDostepny = checkDockerCli();
        if (dockerCliDostepny) {
            attemptDockerBuild(projectDir);
        } else {
            System.out.println("\nDocker CLI NIEDOSTEPNY NA tej maszynie - pomijam probe `docker build`.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Multi-stage build - MALY finalny obraz (TYLKO JRE + JAR).
         * - `.dockerignore` - WYKLUCZA `target/`/`.git/` Z KONTEKSTU
         *   budowania (szybsze, MNIEJSZE przesylanie DO demona Dockera).
         * - Warstwy (layers) Dockera - COACHE'OWANE NIEZALEZNIE -
         *   kopiowanie `pom.xml` PRZED kodem zrodlowym pozwala
         *   Dockerowi ZACHOWAC warstwe zaleznosci Maven MIEDZY
         *   buildami (kod zmienia sie CZESCIEJ NIZ zaleznosci).
         * - Powiazanie Z Lesson19 (kapszton) - TAM WSZYSTKIE serwisy
         *   demo dzialaja W JEDNYM JVM; W PRAWDZIWYM wdrozeniu KAZDY
         *   bylby WLASNYM obrazem/kontenerem.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static Path writeDockerfile(Path dir) throws IOException {
        String dockerfileContent = """
                # Etap 1: budowanie (potrzebuje Mavena + JDK - DUZY obraz, NIE trafia DO finalnego)
                FROM maven:3.9-eclipse-temurin-21 AS build
                WORKDIR /app
                COPY pom.xml .
                RUN mvn dependency:go-offline -B
                COPY src ./src
                RUN mvn package -DskipTests -B

                # Etap 2: uruchamianie (TYLKO JRE - MALY finalny obraz)
                FROM eclipse-temurin:21-jre-alpine
                WORKDIR /app
                COPY --from=build /app/target/*.jar app.jar
                EXPOSE 8080
                ENTRYPOINT ["java", "-jar", "app.jar"]
                """;
        Path dockerfile = dir.resolve("Dockerfile");
        Files.writeString(dockerfile, dockerfileContent);
        return dockerfile;
    }

    private static Path writeDockerignore(Path dir) throws IOException {
        String content = """
                target/
                .git/
                .idea/
                *.iml
                Dockerfile
                .dockerignore
                """;
        Path dockerignore = dir.resolve(".dockerignore");
        Files.writeString(dockerignore, content);
        return dockerignore;
    }

    private static void demonstrateLayerCachingConcept() {
        System.out.println("\n--- Dlaczego 'COPY pom.xml' PRZED 'COPY src' ---");
        System.out.println("Docker CACHE'UJE KAZDA linie (warstwe) Dockerfile NIEZALEZNIE.");
        System.out.println("Jesli `pom.xml` SIE NIE ZMIENIL, `RUN mvn dependency:go-offline` UZYWA CACHE'U (POMIJA ponowne pobieranie zaleznosci).");
        System.out.println("Kod zrodlowy (`src/`) zmienia sie CZESCIEJ NIZ zaleznosci - KOLEJNOSC W Dockerfile MA ZNACZENIE DLA szybkosci buildow.");
    }

    private static boolean checkDockerCli() {
        try {
            Process process = new ProcessBuilder("docker", "--version").redirectErrorStream(true).start();
            boolean zakonczony = process.waitFor(5, TimeUnit.SECONDS);
            if (!zakonczony) {
                process.destroyForcibly();
                return false;
            }
            String output = new String(process.getInputStream().readAllBytes()).trim();
            System.out.println("\nDocker CLI wykryty: " + output);
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    private static void attemptDockerBuild(Path projectDir) {
        System.out.println("\n--- Proba 'docker build' (WYMAGA dzialajacego demona Dockera) ---");
        try {
            Process process = new ProcessBuilder("docker", "build", "-t", "javaquest-demo:lesson17", ".")
                    .directory(projectDir.toFile())
                    .redirectErrorStream(true)
                    .start();
            boolean zakonczony = process.waitFor(10, TimeUnit.SECONDS);
            if (!zakonczony) {
                process.destroyForcibly();
                System.out.println("Build NIE ZAKONCZYL SIE W 10s - przerwano (prawdopodobnie demon Dockera NIE ODPOWIADA).");
                return;
            }
            String output = new String(process.getInputStream().readAllBytes());
            if (process.exitValue() == 0) {
                System.out.println("Build ZAKONCZONY SUKCESEM:\n" + output);
            } else {
                System.out.println("Build NIEUDANY (demon Dockera prawdopodobnie NIE DZIALA NA tej maszynie):");
                System.out.println(output.lines().reduce("", (a, b) -> a + "\n" + b).trim());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Nie udalo sie uruchomic 'docker build': " + e.getMessage());
        }
    }
}
