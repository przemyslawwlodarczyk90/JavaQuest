package com.example.javaquest._20_spring_core.Lesson16_PropertiesAndConfiguration;

public class _Exercises_Lesson16_PropertiesAndConfiguration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyExternalConfigurationMatters {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, dlaczego konfiguracja POZA kodem
         * (pliki/zmienne srodowiskowe) jest wazna dla aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateOwnPropertiesFileAndLoadIt {
        /*
         * 🧪 Zadanie 2:
         * Utworz WLASNY plik `.properties` w `src/main/resources` i
         * zaladuj go przez `@PropertySource`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_InjectValueWithoutDefault {
        /*
         * 🧪 Zadanie 3:
         * Wstrzyknij `@Value("${klucz}")` BEZ wartosci domyslnej dla
         * klucza Z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InjectValueWithDefaultForMissingKey {
        /*
         * 🧪 Zadanie 4:
         * Wstrzyknij `@Value("${brakujacyKlucz:wartoscDomyslna}")` dla
         * klucza, KTOREGO NIE MA w pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TriggerMissingRequiredPropertyError {
        /*
         * 🧪 Zadanie 5:
         * Usun wartosc domyslna z Zadania 4 i USUN klucz z pliku -
         * zaobserwuj blad przy starcie kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseEnvironmentGetPropertyDirectly {
        /*
         * 🧪 Zadanie 6:
         * Odczytaj TA SAMA wartosc przez `Environment.getProperty(...)`
         * ZAMIAST `@Value`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ConvertPropertyToIntegerType {
        /*
         * 🧪 Zadanie 7:
         * Wstrzyknij wartosc liczbowa z pliku `.properties` jako `int`
         * (NIE `String`) przez `@Value`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainDifferenceBetweenPropertiesAndYaml {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij glowna roznice miedzy `.properties` a
         * `.yml` (zapowiedz `_21_spring_boot/Lesson05`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListMultiplePropertySourcesInOneConfig {
        /*
         * 🧪 Zadanie 9:
         * Zaladuj DWA PLIKI `.properties` naraz przez `@PropertySource`
         * (wielokrotna adnotacja lub `@PropertySources`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhatHappensWhenTwoFilesHaveSameKey {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj TEN SAM klucz w OBU plikach z Zadania 9 z RÓZNA
         * wartoscia - sprawdz, KTORA wygrywa.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementProgrammaticPropertySourceRegistration {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj `MapPropertySource` PROGRAMOWO (jak w teorii),
         * BEZ pliku `.properties` w ogole.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VerifyAddFirstVsAddLastPrecedence {
        /*
         * 🧪 Zadanie 12:
         * Zarejestruj 3 `PropertySource` z TYM SAMYM kluczem, roznymi
         * metodami (`addFirst`/`addLast`/`addBefore`) - zweryfikuj
         * KOLEJNOSC wygranej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementListOfValuesFromProperties {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj w pliku `.properties` liste (rozdzielona przecinkami)
         * i wstrzyknij ja jako `List<String>` przez `@Value("${klucz}")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UsePlaceholderNestedInsideAnotherPlaceholder {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj wlasciwosc odwolujaca sie do INNEJ wlasciwosci (np.
         * `app.url=http://${app.host}:${app.port}`) - zweryfikuj poprawne
         * rozwiazanie zagniezdzonych placeholderow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementProfileSpecificPropertiesFile {
        /*
         * 🧪 Zadanie 15:
         * Utworz 2 pliki `.properties` (dla "dev"/"prod") i zaladuj
         * WLASCIWY na podstawie aktywnego profilu (powiaz z `Lesson15_
         * Profiles`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCustomPropertySourceReadingFromCustomFormat {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj WLASNY `PropertySource<T>` czytajacy dane z
         * NIESTANDARDOWEGO formatu (np. Mapy zakodowanej w Stringu
         * "klucz1=wartosc1;klucz2=wartosc2").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureEnvironmentLookupPerformance {
        /*
         * 🧪 Zadanie 17:
         * Zmierz czas 10 000 wywolan `Environment.getProperty(...)` -
         * czy Environment cache'uje wyniki, czy przeszukuje zrodla za
         * KAZDYM razem?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementRequiredPropertyValidationAtStartup {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj metode sprawdzajaca PRZY STARCIE, czy WSZYSTKIE
         * "wymagane" klucze (z listy) sa obecne w `Environment` - rzuc
         * czytelny blad, jesli brakuje ktoregokolwiek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareValueInjectionWithConfigurationPropertiesPreview {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: zapowiedz `_21_spring_boot/Lesson08_
         * ConfigurationProperties` - dlaczego wiele POJEDYNCZYCH
         * `@Value` moze byc niewygodne przy DUZEJ, zagniezdzonej
         * konfiguracji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildConfigurationSourceReport {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj raport wypisujacy WSZYSTKIE zarejestrowane
         * `PropertySource` w `Environment` (nazwa + liczba kluczy, jesli
         * to `EnumerablePropertySource`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementEncryptedPropertyValueDecryption {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PROSTY mechanizm "szyfrowanych" wartosci w pliku
         * `.properties` (np. prefiks `ENC(...)`) - WLASNY `PropertySource`
         * odszyfrowujacy je PRZY odczycie (powiaz z
         * `_19_security_basics/Lesson18_SecretsManagement`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementConfigurationChangeDetectionAtRuntime {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj mechanizm WYKRYWAJACY zmiane wartosci w
         * `MapPropertySource` W TRAKCIE dzialania aplikacji (bez restartu) -
         * jakie SA ograniczenia takiego podejscia w Springu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementTypeSafeConfigurationWrapperWithoutConfigurationProperties {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj WLASNA klase "opakowujaca" `Environment` i
         * udostepniajaca TYPOWANE gettery (np. `getMaxRetries(): int`) -
         * to jest RECZNA wersja tego, co `@ConfigurationProperties` robi
         * automatycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementConfigurationValidationWithConstraintsFromLesson17 {
        /*
         * 🧪 Zadanie 24:
         * Polacz odczyt konfiguracji z Bean Validation
         * (`_19_security_basics/Lesson17_InputValidation`) - zwaliduj
         * wczytana konfiguracje (np. "port musi byc 1-65535") PRZED
         * uzyciem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFallbackChainAcrossMultiplePropertySources {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj lancuch 4 `PropertySource` (symulacja: CLI, zmienna
         * srodowiskowa, plik uzytkownika, plik domyslny) - zweryfikuj
         * poprawna PRECEDENCJE dla RÓZNYCH kombinacji obecnosci klucza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareYamlNestedStructureParsingManually {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj PROSTY, WLASNY parser zagniezdzonej struktury
         * YAML-podobnej (bez zewnetrznej biblioteki) na plaska Mape
         * kluczy (jak robi to Spring z `application.yml`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementAuditLogForPropertyAccess {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj WLASNY `PropertySource` opakowujacy INNY,
         * LOGUJACY kazdy odczyt klucza (audyt dostepu do konfiguracji) -
         * powiaz z `_19_security_basics/Lesson19_SecureLoggingAndAuditing`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementConfigurationDiffToolBetweenEnvironments {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj narzedzie porownujace 2 zestawy `PropertySource` (np.
         * "dev" i "prod") i wypisujace RÓZNICE (klucze OBECNE tylko w
         * jednym, klucze z RÓZNA wartoscia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementLazyPropertyResolutionWithPlaceholderCycles {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj CYKLICZNE odwolanie miedzy placeholderami (`a=${b}`,
         * `b=${a}`) - zaobserwuj i wyjasnij zachowanie Springa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteExternalizedConfigurationSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system konfiguracji - wiele `PropertySource`
         * (programowe + plikowe), walidacja wymaganych kluczy, typowane
         * opakowanie (Zadanie 23), raport zrodel (Zadanie 20) - jeden
         * spojny system gotowy do uzycia w realnej aplikacji.
         */
        public static void main(String[] args) { }
    }
}
