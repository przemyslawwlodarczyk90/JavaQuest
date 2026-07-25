package com.example.javaquest._31_spring_cloud_microservices.Lesson05_SpringCloudConfigClient;

public class _Exercises_Lesson05_SpringCloudConfigClient {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateNativeConfigServerAndClientReadingRemoteValue {
        /* 🧪 Zadanie 1: Stworz Config Server (backend native) + klienta odczytujacego JEDNA zdalna wartosc (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainSpringConfigImportMechanism {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij mechanizm `spring.config.import=configserver:...`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhySpringCloudConfigEnabledIsGloballyFalseInThisProject {
        /* 🧪 Zadanie 3: Bez terminala - wyjasnij, DLACZEGO `spring.cloud.config.enabled=false` jest GLOBALNIE W `application.properties` TEGO projektu. */
        public static void main(String[] args) { }
    }

    static class Exercise04_QueryConfigServerRestApiForDifferentApplicationName {
        /* 🧪 Zadanie 4: Odpytaj Config Server DLA INNEJ nazwy aplikacji (BEZ wlasnego pliku) I ZAOBSERWUJ fallback. */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddSecondPropertyToYamlFileAndVerifyClientSeesIt {
        /* 🧪 Zadanie 5: Dodaj DRUGA wlasciwosc DO pliku YAML I zweryfikuj, ze klient JA WIDZI. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainDifferenceBetweenOldSpringCloudConfigUriAndNewSpringConfigImport {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij ROZNICE MIEDZY starym `spring.cloud.config.uri` A nowym `spring.config.import=configserver:`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseConfigurationPropertiesInsteadOfValueForRemoteConfig {
        /* 🧪 Zadanie 7: Uzyj `@ConfigurationProperties` (zamiast `@Value`) DO odczytu ZDALNEJ konfiguracji. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhatOptionalPrefixInConfigserverImportMeans {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, CO OZNACZA prefiks `optional:` W `spring.config.import=optional:configserver:...`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_SimulateConfigServerUnavailableWithoutOptionalPrefix {
        /* 🧪 Zadanie 9: Zasymuluj niedostepny Config Server BEZ prefiksu `optional:` I ZAOBSERWUJ blad startu aplikacji. */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareRawRestResponseWithBoundJavaValue {
        /* 🧪 Zadanie 10: Porownaj SUROWA odpowiedz REST API (JSON) Z odczytana wartoscia W Javie (`@Value`). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddProfileSpecificYamlAndActivateProfileOnClient {
        /* 🧪 Zadanie 11: Dodaj plik `orders-service-prod.yml` I aktywuj profil 'prod' PO STRONIE klienta - zweryfikuj NADPISANIE. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ShareCommonPropertyViaApplicationYmlAcrossTwoServices {
        /* 🧪 Zadanie 12: Udostepnij WSPOLNA wlasciwosc PRZEZ `application.yml` (backend native) DLA DWOCH klientow. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementRestControllerExposingCurrentConfigValue {
        /* 🧪 Zadanie 13: Zaimplementuj `@RestController` wystawiajacy AKTUALNA wartosc konfiguracji PRZEZ HTTP. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainWhyConfigIsFetchedOnceAtStartupNotContinuously {
        /* 🧪 Zadanie 14: Bez terminala - wyjasnij, DLACZEGO konfiguracja JEST pobierana RAZ PRZY starcie (bez `@RefreshScope`). */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseRefreshScopeToAllowRuntimeConfigUpdate {
        /* 🧪 Zadanie 15: Uzyj `@RefreshScope` NA beanie, zeby UMOZLIWIC odswiezenie konfiguracji W runtime. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareFileBackendVsGitBackendResultForSameClient {
        /* 🧪 Zadanie 16: Porownaj wynik DLA klienta PRZY backendzie 'native' A (koncepcyjnie) 'git'. */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureStartupTimeDifferenceWithAndWithoutRemoteConfig {
        /* 🧪 Zadanie 17: Zmierz roznice czasu startu KLIENTA Z ZDALNA konfiguracja A BEZ niej. */
        public static void main(String[] args) { }
    }

    static class Exercise18_HandleMissingPropertyGracefullyWithDefaultValue {
        /* 🧪 Zadanie 18: Obsluz BRAKUJACA wlasciwosc Z WARTOSCIA DOMYSLNA (`@Value("${klucz:domyslna}")`). */
        public static void main(String[] args) { }
    }

    static class Exercise19_CombineConfigServerWithEurekaForServiceDiscoveryOfConfigServer {
        /* 🧪 Zadanie 19: Powiaz Z Lesson03 - opisz (koncepcyjnie), jak Eureka MOZE posluzyc DO odnalezienia Config Servera. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhatHappensIfTwoServicesShareSameYamlFileByMistake {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, CO SIE STANIE, GDY dwa RÓZNE serwisy przypadkiem DZIELA TEN SAM plik YAML. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementActuatorRefreshEndpointToReloadConfigWithoutRestart {
        /* 🧪 Zadanie 21: Zaimplementuj endpoint `/actuator/refresh`, zeby PRZELADOWAC konfiguracje BEZ restartu. */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignConfigServerFailoverWithMultipleUris {
        /* 🧪 Zadanie 22: Zaprojektuj failover Config Servera Z WIELOMA URI (`spring.cloud.config.uri` jako lista). */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareStartupFetchWithSpringCloudBusBroadcastRefresh {
        /* 🧪 Zadanie 23: Zbadaj I porownaj recznyy `/actuator/refresh` Z automatycznym Spring Cloud Bus (broadcast odswiezenia). */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementEncryptedPropertyDecryptedAutomaticallyByClient {
        /* 🧪 Zadanie 24: Zaimplementuj zaszyfrowana wlasciwosc (`{cipher}...`), automatycznie odszyfrowywana PO STRONIE klienta. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignConfigServerCachingStrategyOnClientSide {
        /* 🧪 Zadanie 25: Zaprojektuj strategie cache'owania konfiguracji PO STRONIE klienta (odpornosc NA chwilowa niedostepnosc). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainRiskOfStoringSecretsInPlainYamlOnConfigServer {
        /* 🧪 Zadanie 26: Bez terminala - omow ryzyko przechowywania sekretow W CZYSTYM YAML NA Config Serverze. */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareConfigClientBootstrapContextWithModernConfigImport {
        /* 🧪 Zadanie 27: Zbadaj I opisz historyczny "bootstrap context" (Spring Cloud Config PRZED 2.4) WZGLEDEM dzisiejszego `spring.config.import`. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignMultiClusterConfigServerSetupForDifferentRegions {
        /* 🧪 Zadanie 28: Zaprojektuj (na papierze) Config Server DLA WIELU regionow/klastrow. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCustomHealthIndicatorCheckingConfigServerReachability {
        /* 🧪 Zadanie 29: Zaimplementuj WLASNY health indicator sprawdzajacy OSIAGALNOSC Config Servera. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionConfigClientResiliencyChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste odpornosci klienta NA niedostepnosc Config Servera W PRODUKCJI. */
        public static void main(String[] args) { }
    }
}
