package com.example.javaquest._21_spring_boot.Lesson08_ConfigurationProperties;

public class _Exercises_Lesson08_ConfigurationProperties {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhenToPreferConfigurationProperties {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, KIEDY warto uzyc
         * `@ConfigurationProperties` zamiast `@Value`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnConfigurationPropertiesClass {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA klase `@ConfigurationProperties` z 3+
         * polami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RegisterViaEnableConfigurationProperties {
        /*
         * 🧪 Zadanie 3:
         * Zarejestruj klase z Zadania 2 przez
         * `@EnableConfigurationProperties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_VerifyKebabCaseToCamelCaseBinding {
        /*
         * 🧪 Zadanie 4:
         * Zweryfikuj wiazanie klucza `moje-pole` (kebab-case) do pola
         * `mojePole` (camelCase).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddValidationConstraintToOwnClass {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `@Validated` + Bean Validation do klasy z Zadania 2 -
         * zweryfikuj fail-fast dla NIEPRAWIDLOWEJ wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyPublicGetterSetterAreRequired {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: powiaz z `_20_spring_core/Lesson19` - wyjasnij,
         * dlaczego getter/setter MUSZA byc PUBLICZNE dla wiazania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TryPrivateClassAndObserveError {
        /*
         * 🧪 Zadanie 7:
         * Sprobuj uzyc klasy PAKIETOWEJ (nie `public`) - zaobserwuj
         * BLAD wiazania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareDefaultValuesInFieldVsValueAnnotation {
        /*
         * 🧪 Zadanie 8:
         * Ustaw wartosc DOMYSLNA jako inicjalizacje pola (jak w teorii) -
         * porownaj z `${klucz:domyslna}` z `@Value`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementNestedConfigurationPropertiesClass {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj ZAGNIEZDZONA klase konfiguracji (pole typu
         * innej klasy `@ConfigurationProperties`-podobnej, wewnetrznej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainConfigurationPropertiesScanAlternative {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: sprawdz (dokumentacja) `@ConfigurationPropertiesScan` -
         * jak rozni sie od `@EnableConfigurationProperties`?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementConfigurationPropertiesWithRecord {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `@ConfigurationProperties` jako `record`
         * (Boot wspiera wiazanie konstruktorowe od 2.x+) ZAMIAST
         * zwyklej klasy z setterami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementDeeplyNestedRecordProperties {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj rekord z ZAGNIEZDZONYM INNYM rekordem (2+
         * poziomy) - zwiaz z zagniezdzonymi kluczami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementListAndMapFieldsInConfigurationProperties {
        /*
         * 🧪 Zadanie 13:
         * Dodaj pole `List<String>` I `Map<String, String>` do
         * WLASNEJ klasy konfiguracji - zwiaz z listowanymi/mapowanymi
         * kluczami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCrossFieldValidationOnConfigProperties {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj walidacje "krzyzowa" (powiaz z
         * `_19_security_basics/Lesson17`, Zadanie 12) NA CALEJ klasie
         * konfiguracji (np. "min <= max").
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareConfigurationPropertiesWithEnvironmentDirectly {
        /*
         * 🧪 Zadanie 15:
         * Odczytaj TE SAME wartosci PRZEZ `Environment.getProperty(...)`
         * BEZPOSREDNIO - porownaj bezpieczenstwo typow z
         * `@ConfigurationProperties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementConfigurationPropertiesWithCustomConverter {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj pole WLASNEGO typu (np. `Duration`, `DataSize` -
         * wbudowane konwertery Boota) - zweryfikuj poprawne parsowanie
         * (np. "10s", "5MB").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementProfileSpecificConfigurationPropertiesValues {
        /*
         * 🧪 Zadanie 17:
         * Polacz `@ConfigurationProperties` z profilami (Lesson06) -
         * RÓZNE wartosci CALEJ grupy per profil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConstructorBindingExplicitly {
        /*
         * 🧪 Zadanie 18:
         * Uzyj jawnego `@ConstructorBinding` (starsza skladnia, PRZED
         * automatycznym wykrywaniem dla rekordow) - porownaj z Zadaniem 11.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainMetadataJsonForIdeAutocomplete {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala (dokumentacja): sprawdz
         * `spring-boot-configuration-processor` - jak generuje
         * podpowiedzi w IDE dla WLASNYCH kluczy konfiguracji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildConfigurationPropertiesVsValueDecisionTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele decyzyjna - dla danego
         * scenariusza (1 wartosc/grupa 10 wartosci/potrzeba walidacji/
         * itd.) wskaz WLASCIWY mechanizm.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomPropertyEditorForComplexType {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY konwerter (`Converter<String, T>`) dla
         * WYMYSLONEGO typu (np. "IP:PORT" -> obiekt `Endpoint`) -
         * zarejestruj go dla wiazania `@ConfigurationProperties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementConfigurationPropertiesWithSelfValidatingConstructor {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj rekord konfiguracji z WALIDACJA W KONSTRUKTORZE
         * (jak "compact constructor" rekordow) ZAMIAST adnotacji Bean
         * Validation - porownaj OBA podejscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_GenerateConfigurationMetadataJsonManually {
        /*
         * 🧪 Zadanie 23:
         * Recznie napisz (jako String) plik
         * `META-INF/spring-configuration-metadata.json` dla WLASNEJ
         * klasy konfiguracji - jak zrobilby to
         * `spring-boot-configuration-processor`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRelaxedBindingEdgeCaseAnalysis {
        /*
         * 🧪 Zadanie 24:
         * Przetestuj WSZYSTKIE warianty zapisu TEGO SAMEGO klucza
         * (kebab-case/camelCase/UPPER_SNAKE_CASE) - zweryfikuj, ktore
         * SIE wiaza, a ktore NIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDynamicRefreshOfConfigurationProperties {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala (dokumentacja Spring Cloud): sprawdz mechanizm
         * `@RefreshScope` - jak POZWALA odswiezyc `@ConfigurationProperties`
         * BEZ restartu aplikacji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementConfigurationPropertiesAuditLogging {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj `ApplicationRunner` (Lesson07) LOGUJACY (z
         * MASKOWANIEM sekretow, powiaz z `_19_security_basics/Lesson19`)
         * PELNY, wiazany stan WLASNEJ klasy konfiguracji przy starcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareConfigurationPropertiesPerformanceAtScale {
        /*
         * 🧪 Zadanie 27:
         * Zmierz czas wiazania 50 POL w JEDNEJ klasie konfiguracji vs 50
         * OSOBNYCH `@Value` - czy jest ROZNICA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementLayeredConfigurationPropertiesArchitecture {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (zaimplementuj) HIERARCHIE klas konfiguracji dla
         * DUZEJ aplikacji (5+ modulow, KAZDY z WLASNYM prefiksem) -
         * jeden spojny system.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementConfigurationPropertiesTestWithoutSpringContext {
        /*
         * 🧪 Zadanie 29:
         * Napisz "test" (zwykla metoda `main`, BEZ frameworka) dla
         * WLASNEJ klasy konfiguracji - RECZNIE ustaw pola przez settery i
         * zweryfikuj logike WALIDACJI/wartosci domyslnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteTypeSafeConfigurationSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, TYPOWANY system konfiguracji DUZEJ aplikacji -
         * WIELE klas `@ConfigurationProperties` (rekordy + walidacja +
         * zagniezdzenie) + raport WSZYSTKICH wiazanych wartosci przy
         * starcie.
         */
        public static void main(String[] args) { }
    }
}
