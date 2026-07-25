package com.example.javaquest._31_spring_cloud_microservices.Lesson17_ContainerizingSpringBootApps;

public class _Exercises_Lesson17_ContainerizingSpringBootApps {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteMultiStageDockerfileForSpringBootApp {
        /* 🧪 Zadanie 1: Napisz multi-stage Dockerfile DLA aplikacji Spring Boot (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhyMultiStageBuildsProduceSmallerImages {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, DLACZEGO multi-stage build DAJE MNIEJSZE obrazy. */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteDockerignoreExcludingTargetAndGit {
        /* 🧪 Zadanie 3: Napisz `.dockerignore` WYKLUCZAJACY `target/` I `.git/` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise04_CheckIfDockerCliIsAvailableProgrammatically {
        /* 🧪 Zadanie 4: Sprawdz PROGRAMOWO dostepnosc Docker CLI (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyCopyPomXmlBeforeCopySrc {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO `COPY pom.xml` idzie PRZED `COPY src`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainDifferenceBetweenJdkAndJreBaseImage {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij ROZNICE MIEDZY obrazem bazowym JDK A JRE. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExposePortInDockerfileAndExplainItsPurpose {
        /* 🧪 Zadanie 7: Dodaj `EXPOSE` W Dockerfile I wyjasnij JEGO cel (dokumentacyjny, NIE otwiera portu SAM W SOBIE). */
        public static void main(String[] args) { }
    }

    static class Exercise08_BuildDockerImageIfDockerAvailable {
        /* 🧪 Zadanie 8: Jesli MASZ dzialajacy Docker - zbuduj obraz I zweryfikuj JEGO rozmiar (`docker images`). */
        public static void main(String[] args) { }
    }

    static class Exercise09_RunContainerAndVerifyApplicationResponds {
        /* 🧪 Zadanie 9: Uruchom kontener (`docker run`) I zweryfikuj, ze aplikacja ODPOWIADA. */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareImageSizeWithAndWithoutMultiStageBuild {
        /* 🧪 Zadanie 10: Porownaj rozmiar obrazu Z multi-stage build A BEZ niego (JEDEN etap Z JDK). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseNonRootUserInDockerfileForSecurity {
        /* 🧪 Zadanie 11: Uzyj NIE-root uzytkownika W Dockerfile (bezpieczenstwo, powiazanie Z `_19_security_basics`). */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddHealthcheckInstructionToDockerfile {
        /* 🧪 Zadanie 12: Dodaj instrukcje `HEALTHCHECK` DO Dockerfile (wywolujaca `/actuator/health`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureJvmMemoryLimitsForContainerAwareness {
        /* 🧪 Zadanie 13: Skonfiguruj limity pamieci JVM SWIADOME kontenera (`-XX:MaxRAMPercentage`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseSpringBootBuildImageMavenGoalInsteadOfDockerfile {
        /* 🧪 Zadanie 14: Zbadaj I uzyj `mvn spring-boot:build-image` (Cloud Native Buildpacks) ZAMIAST recznego Dockerfile. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExtractLayeredJarUsingSpringBootLayertools {
        /* 🧪 Zadanie 15: Wyodrebnij warstwy JAR-a (`java -Djarmode=layertools -jar app.jar extract`) DLA lepszego cache'owania. */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteDockerComposeFileForMultipleServices {
        /* 🧪 Zadanie 16: Napisz `docker-compose.yml` DLA WIELU serwisow naraz (orders+payments+eureka). */
        public static void main(String[] args) { }
    }

    static class Exercise17_ConfigureEnvironmentVariablesForContainerizedApp {
        /* 🧪 Zadanie 17: Skonfiguruj zmienne srodowiskowe DLA skonteneryzowanej aplikacji (powiazanie Z `_10_dao/Lesson13`). */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementGracefulShutdownHandlingForSigtermInContainer {
        /* 🧪 Zadanie 18: Obsluz SIGTERM (graceful shutdown) W skonteneryzowanej aplikacji. */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareAlpineWithDistrolessBaseImages {
        /* 🧪 Zadanie 19: Zbadaj I porownaj obraz bazowy Alpine Z "distroless" (jeszcze mniejszy, bez powloki). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ScanDockerImageForVulnerabilities {
        /* 🧪 Zadanie 20: Zbadaj narzedzie DO skanowania podatnosci obrazu (np. Trivy, powiazanie Z `_19_security_basics/Lesson20`). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildMultiArchitectureImageForAmd64AndArm64 {
        /* 🧪 Zadanie 21: Zbuduj obraz WIELOARCHITEKTURALNY (amd64+arm64, `docker buildx`). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCiCdPipelineStageBuildingAndPushingImage {
        /* 🧪 Zadanie 22: Zaprojektuj etap CI/CD budujacy I WYSYLAJACY obraz DO rejestru (powiazanie Z `_11_buildtools`). */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignImageTaggingStrategyForContinuousDeployment {
        /* 🧪 Zadanie 23: Zaprojektuj strategie tagowania obrazow DLA ciaglego wdrazania (semver/commit-sha/latest). */
        public static void main(String[] args) { }
    }

    static class Exercise24_OptimizeDockerBuildCacheUsingBuildKitMountCache {
        /* 🧪 Zadanie 24: Zoptymalizuj cache buildu Dockera uzywajac BuildKit `--mount=type=cache`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignSecretsInjectionStrategyForContainerizedServices {
        /* 🧪 Zadanie 25: Zaprojektuj strategie wstrzykiwania sekretow DO skonteneryzowanych serwisow (BEZ wpisywania W obraz). */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareDockerWithPodmanAndContainerd {
        /* 🧪 Zadanie 26: Zbadaj I porownaj Docker Z Podman I containerd (alternatywne silniki kontenerow). */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignImmutableInfrastructureDeploymentStrategy {
        /* 🧪 Zadanie 27: Zaprojektuj strategie "immutable infrastructure" WYKORZYSTUJACA obrazy kontenerow. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementReproducibleBuildsWithPinnedBaseImageDigests {
        /* 🧪 Zadanie 28: Zaimplementuj powtarzalne buildy Z PRZYPIETYMI digestami obrazu bazowego (NIE tagami). */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignImageSizeReductionStrategyForLargeMonorepo {
        /* 🧪 Zadanie 29: Zaprojektuj strategie zmniejszenia rozmiaru obrazow DLA duzego monorepo (WSPOLNE warstwy). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionContainerizationChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" konteneryzacji (bezpieczenstwo/rozmiar/healthcheck/graceful shutdown). */
        public static void main(String[] args) { }
    }
}
