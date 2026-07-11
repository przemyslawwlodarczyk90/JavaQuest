package com.example.javaquest._21_spring_boot.Lesson15_CustomAutoConfigurationAndStarters;

public class _Exercises_Lesson15_CustomAutoConfigurationAndStarters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAutoConfigurationVsConfigurationDifference {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij roznice miedzy `@AutoConfiguration` a
         * zwyklym `@Configuration`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnAutoConfigurationClass {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA klase `@AutoConfiguration` z
         * `@ConditionalOnMissingBean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyItWorksViaExplicitSources {
        /*
         * 🧪 Zadanie 3:
         * Zweryfikuj dzialanie klasy z Zadania 2 przez `.sources(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteAutoConfigurationImportsFileContent {
        /*
         * 🧪 Zadanie 4:
         * Napisz (jako String) DOKLADNA zawartosc pliku
         * `AutoConfiguration.imports` dla klasy z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateRealResourceFileInThisLesson {
        /*
         * 🧪 Zadanie 5:
         * Utworz REALNY plik `META-INF/spring/org.springframework.boot.
         * autoconfigure.AutoConfiguration.imports` w
         * `src/main/resources` TEGO KURSU (TYMCZASOWO, dla WLASNEGO
         * testu) - zweryfikuj automatyczne wykrycie, POTEM USUN plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyBothModulesAreSeparate {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, DLACZEGO prawdziwy starter to 2
         * OSOBNE moduly (autoconfigure + starter), a NIE 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListRealStarterAutoconfigurePairsFromThisProject {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala (dokumentacja): sprawdz nazwy modulow PAR
         * starter+autoconfigure dla `spring-boot-starter-web`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementConditionalOnPropertyInCustomAutoConfig {
        /*
         * 🧪 Zadanie 8:
         * Dodaj `@ConditionalOnProperty` do WLASNEJ auto-konfiguracji -
         * feature flaga wlaczajaca/wylaczajaca JA CALA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainOrderingWithAutoConfigureBefore {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala (dokumentacja): uzyj `@AutoConfigureBefore`/
         * `@AutoConfigureAfter` na WLASNEJ klasie - wyjasnij PO CO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareSpringFactoriesWithImportsFileForCustomAutoConfig {
        /*
         * 🧪 Zadanie 10:
         * Zarejestruj TA SAMA klase PRZEZ STARY mechanizm
         * (`META-INF/spring.factories`) - zweryfikuj, ze TEZ dziala
         * (wsteczna kompatybilnosc).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementAutoConfigurationWithConfigurationPropertiesIntegration {
        /*
         * 🧪 Zadanie 11:
         * Polacz WLASNA auto-konfiguracje z `@ConfigurationProperties`
         * (Lesson08) - konfigurowalne zachowanie beana AUTO-konfigurowanego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementAutoConfigurationRegisteringMultipleBeans {
        /*
         * 🧪 Zadanie 12:
         * Rozbuduj WLASNA auto-konfiguracje o WIELE (3+) powiazanych
         * beanow, KAZDY z WLASNYMI warunkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementAutoConfigurationDisabledByExclude {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `@SpringBootApplication(exclude = {WlasnaAutoConfig.class})` -
         * zweryfikuj WYLACZENIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementAutoConfigurationWithConditionalOnWebApplication {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `@ConditionalOnWebApplication` - zweryfikuj RÓZNE
         * zachowanie miedzy `WebApplicationType.NONE` a `SERVLET`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BuildRealTwoModuleMavenProject {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj (opisz STRUKTURE plikow, opcjonalnie zaimplementuj)
         * PRAWDZIWY, 2-modulowy projekt Maven (autoconfigure + starter)
         * POZA tym kursem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PublishCustomStarterToLocalRepository {
        /*
         * 🧪 Zadanie 16:
         * (Zaawansowane, opcjonalne) Zainstaluj WLASNY starter do
         * lokalnego repozytorium Maven (`mvn install`, powiaz z
         * `_11_buildtools/Lesson17`) - uzyj go w INNYM projekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementAutoConfigurationTestWithApplicationContextRunner {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala (dokumentacja, zapowiedz przyszly rozdzial o
         * testowaniu): sprawdz `ApplicationContextRunner` - dedykowane
         * narzedzie do TESTOWANIA auto-konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementDocumentedAutoConfigurationWithJavadoc {
        /*
         * 🧪 Zadanie 18:
         * Udokumentuj WLASNA klase auto-konfiguracji PELNYM Javadoc -
         * jakie WARUNKI musza byc spelnione, jakie beany DOSTARCZA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementAutoConfigurationMetadataForIde {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala (dokumentacja): sprawdz, jak
         * `spring-boot-configuration-processor` generuje METADANE
         * UZYWANE przez IDE do podpowiedzi WLASNYCH `@ConfigurationProperties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildCustomAutoConfigurationChecklistDocument {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) checkliste "co sprawdzic" PRZED
         * publikacja WLASNEGO startera (warunki/dokumentacja/testy/
         * wersjonowanie).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFullyDynamicAutoConfigurationViaClassLoader {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PELNY, dynamiczny scenariusz (wzorem
         * `_14_advancedjava/Lesson26`) - skompiluj WLASNA klase
         * `@AutoConfiguration` DO KATALOGU TYMCZASOWEGO, zapisz plik
         * `.imports` TAM, zaladuj przez `URLClassLoader` i URUCHOM
         * `SpringApplication` z TYM classloaderem jako kontekstowym -
         * zweryfikuj AUTOMATYCZNE wykrycie BEZ `.sources(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementAutoConfigurationOrderingConflictResolution {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj 3 WLASNE auto-konfiguracje z ZALEZNYMI od siebie
         * warunkami (`@ConditionalOnBean` miedzy nimi) - zweryfikuj
         * poprawna KOLEJNOSC rozwiazania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomStarterWithTransitiveDependencyManagement {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (opisz `pom.xml`) WLASNY starter Z WLASNYM BOM
         * (jak `spring-boot-starter-parent`) zarzadzajacym wersjami
         * WLASNYCH, DODATKOWYCH zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementAutoConfigurationCompatibilityAcrossBootVersions {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: zaprojektuj (opisz) strategie utrzymania
         * WLASNEGO startera KOMPATYBILNEGO z WIELOMA wersjami Spring
         * Boota naraz (2.7+ i 3.x) - powiaz z `_20_spring_core/Lesson02`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAutoConfigurationHealthCheckIntegration {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj WLASNA auto-konfiguracje o AUTOMATYCZNIE
         * rejestrowany `HealthIndicator` (powiaz z Lesson12) - WARUNKOWO,
         * TYLKO gdy Actuator jest na classpath.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSelfContainedAutoConfigurationTestSuite {
        /*
         * 🧪 Zadanie 26:
         * Napisz "test" (bez frameworka testowego) URUCHAMIAJACY WLASNA
         * auto-konfiguracje w 3+ RÓZNYCH scenariuszach (obecnosc/brak
         * warunkow) - zweryfikuj KAZDY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareCustomAutoConfigurationWithManualBeanDefinition {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: przedyskutuj, KIEDY pisanie WLASNEJ
         * auto-konfiguracji jest PRZESADA (wystarczy zwykly
         * `@Configuration` w tym SAMYM projekcie) - kiedy JEST
         * uzasadnione (WSPOLDZIELONA biblioteka miedzy projektami)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAutoConfigurationVersioningStrategy {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (opisz) strategie WERSJONOWANIA WLASNEGO startera -
         * jak wprowadzac ZMIANY LAMIACE (breaking changes) BEZ
         * zaskakiwania uzytkownikow?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementAutoConfigurationSecurityReview {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_19_security_basics/Lesson20` - PRZEANALIZUJ WLASNA
         * auto-konfiguracje pod katem bezpieczenstwa (czy WLASCIWOSCI sa
         * WALIDOWANE? czy MOZE zostac naduzyta przez zlosliwa
         * konfiguracje?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompletePublishableStarterCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, GOTOWY DO PUBLIKACJI starter (2 moduly,
         * dokumentacja, testy, health check, walidacja) DLA WYMYSLONEJ
         * funkcjonalnosci - jeden, spojny, profesjonalny produkt.
         */
        public static void main(String[] args) { }
    }
}
