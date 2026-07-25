package com.example.javaquest._31_spring_cloud_microservices.Lesson04_ConfigServerIntro;

public class _Exercises_Lesson04_ConfigServerIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainProblemConfigServerSolves {
        /* 🧪 Zadanie 1: Bez terminala - wyjasnij PROBLEM, KTORY rozwiazuje Config Server. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainRoleOfEnableConfigServerAnnotation {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij role adnotacji `@EnableConfigServer`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_DescribeConfigServerRestApiUrlPattern {
        /* 🧪 Zadanie 3: Opisz wzorzec URL REST API Config Servera (`/{application}/{profile}[/{label}]`). */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareNativeAndGitBackends {
        /* 🧪 Zadanie 4: Bez terminala - porownaj backend 'native' Z backendem 'git'. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyGitBackendGivesVersionHistoryForFree {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO backend 'git' DAJE historie zmian konfiguracji "za darmo". */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListFourFileNamingLevelsInResolutionOrder {
        /* 🧪 Zadanie 6: Wymien 4 poziomy nazewnictwa plikow (application.yml -> orders-service-prod.yml) W kolejnosci priorytetu. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyConfigServerMayContainSecrets {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO Config Server CZESTO zawiera sekrety. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ConnectConfigServerToSecretsManagementLesson {
        /* 🧪 Zadanie 8: Powiaz Config Server Z `_19_security_basics/Lesson18_SecretsManagement`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhatLabelParameterMeansInGitContext {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, CZYM jest parametr `{label}` W kontekscie backendu git (branch/tag/commit). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyCentralizedConfigIsCrossCuttingConcern {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO centralna konfiguracja jest "cross-cutting concern" (powiazanie Z `_17_architecture`). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CreateNativeConfigServerServingLocalYamlFiles {
        /* 🧪 Zadanie 11: Stworz Config Server Z backendem 'native', serwujacy pliki YAML Z LOKALNEGO katalogu. */
        public static void main(String[] args) { }
    }

    static class Exercise12_QueryConfigServerRestApiDirectlyOverHttp {
        /* 🧪 Zadanie 12: Odpytaj REST API Config Servera BEZPOSREDNIO PRZEZ HTTP (`java.net.http.HttpClient`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_CreateApplicationSpecificAndProfileSpecificYamlFiles {
        /* 🧪 Zadanie 13: Stworz pliki `application.yml`, `moj-serwis.yml` I `moj-serwis-prod.yml` I ZAOBSERWUJ scalenie. */
        public static void main(String[] args) { }
    }

    static class Exercise14_VerifyProfileSpecificValueOverridesCommonValue {
        /* 🧪 Zadanie 14: Zweryfikuj, ze wartosc Z `moj-serwis-prod.yml` NADPISUJE wartosc Z `application.yml`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhatHappensWhenRequestedApplicationHasNoOwnFile {
        /* 🧪 Zadanie 15: Bez terminala - wyjasnij, CO SIE DZIEJE, gdy zadany serwis NIE MA WLASNEGO pliku (fallback DO application.yml). */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareConfigServerWithEnvironmentVariablesApproach {
        /* 🧪 Zadanie 16: Powiaz Z `_10_dao/Lesson13_EnvironmentVariables` - porownaj Config Server Z podejsciem "zmienne srodowiskowe". */
        public static void main(String[] args) { }
    }

    static class Exercise17_ListProsAndConsOfCentralizingAllConfigInOnePlace {
        /* 🧪 Zadanie 17: Wypisz zalety I WADY centralizacji CALEJ konfiguracji W jednym miejscu (single point of failure!). */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainWhyConfigServerItselfNeedsHighAvailability {
        /* 🧪 Zadanie 18: Bez terminala - wyjasnij, DLACZEGO SAM Config Server WYMAGA wysokiej dostepnosci W PRODUKCJI. */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignYamlStructureForMultiEnvironmentDeployment {
        /* 🧪 Zadanie 19: Zaprojektuj strukture plikow YAML DLA wdrozenia W 3 srodowiskach (dev/staging/prod). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainDifferenceBetweenConfigServerAndFeatureFlagService {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij ROZNICE MIEDZY Config Serverem A serwisem feature flag (rozne cele!). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ConfigureConfigServerWithGitBackendPointingToRealRepo {
        /* 🧪 Zadanie 21: Skonfiguruj Config Server Z backendem 'git', wskazujacym NA prawdziwe (publiczne) repozytorium. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementEncryptedPropertyUsingConfigServerEncryptDecrypt {
        /* 🧪 Zadanie 22: Zbadaj I zaimplementuj szyfrowana wlasciwosc PRZEZ `/encrypt`/`/decrypt` endpoint Config Servera. */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignConfigServerHighAvailabilityDeployment {
        /* 🧪 Zadanie 23: Zaprojektuj wdrozenie Config Servera W trybie wysokiej dostepnosci (WIELE instancji + Eureka). */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareConfigServerWithHashicorpVaultAndConsulKv {
        /* 🧪 Zadanie 24: Zbadaj I porownaj Config Server Z HashiCorp Vault I Consul KV jako alternatywami. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCustomEnvironmentRepositoryBackend {
        /* 🧪 Zadanie 25: Zaimplementuj WLASNY `EnvironmentRepository` (niestandardowe zrodlo konfiguracji). */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignAuditTrailForConfigurationChanges {
        /* 🧪 Zadanie 26: Zaprojektuj dziennik audytu zmian konfiguracji (powiazanie Z `_19_security_basics/Lesson19`). */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExplainSecurityRisksOfExposingConfigServerPublicly {
        /* 🧪 Zadanie 27: Bez terminala - omow ryzyka bezpieczenstwa WYSTAWIENIA Config Servera publicznie BEZ zabezpieczen. */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasureLatencyImpactOfFetchingConfigOnEveryServiceStartup {
        /* 🧪 Zadanie 28: Zmierz narzut czasowy pobierania konfiguracji PRZY KAZDYM starcie serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignConfigServerCachingStrategyForResilience {
        /* 🧪 Zadanie 29: Zaprojektuj strategie cache'owania konfiguracji PO STRONIE klienta (odpornosc NA chwilowa niedostepnosc serwera). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionConfigServerArchitectureChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" DLA wdrozenia Config Servera (bezpieczenstwo, HA, szyfrowanie, audyt). */
        public static void main(String[] args) { }
    }
}
