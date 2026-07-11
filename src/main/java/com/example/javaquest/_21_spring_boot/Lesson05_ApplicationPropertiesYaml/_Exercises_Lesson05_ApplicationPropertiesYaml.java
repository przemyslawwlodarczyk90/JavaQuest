package com.example.javaquest._21_spring_boot.Lesson05_ApplicationPropertiesYaml;

public class _Exercises_Lesson05_ApplicationPropertiesYaml {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAutomaticLoadingOfApplicationProperties {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, dlaczego `application.properties` NIE
         * wymaga `@PropertySource` (w odroznieniu od `_20_spring_core/Lesson16`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddNewKeyToApplicationPropertiesAndReadIt {
        /*
         * 🧪 Zadanie 2:
         * Dodaj WLASNY klucz do `application.properties` tego projektu i
         * odczytaj go przez `@Value`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainPropertiesVsYamlSyntaxDifference {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: napisz TA SAMA wartosc konfiguracyjna w OBU
         * formatach (.properties i .yml) obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainYamlIndentationPitfall {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij, dlaczego MIESZANIE spacji i
         * tabulatorow w YAML jest bledem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReadNestedYamlLikeKeyViaEnvironment {
        /*
         * 🧪 Zadanie 5:
         * Odczytaj WLASNY, zagniezdzony (3+ poziomy) klucz przez
         * `Environment.getProperty(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareDefaultValueSyntaxAcrossFormats {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: czy skladnia `${klucz:domyslna}` (z `_20_spring_core/
         * Lesson16`) dziala TAK SAMO niezaleznie od formatu pliku?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyResourceFileExistsProgrammatically {
        /*
         * 🧪 Zadanie 7:
         * Sprawdz (jak w teorii) czy w Twoim projekcie istnieje TEZ plik
         * `application.yml` OBOK `application.properties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhatHappensWithBothFilesPresent {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: co by sie stalo, gdyby ISTNIALY OBA pliki
         * (`application.properties` I `application.yml`) z RÓZNA
         * wartoscia TEGO SAMEGO klucza?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListLocationsWhereBootLooksForApplicationProperties {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala (dokumentacja): wymien MIEJSCA (poza `src/main/
         * resources`), gdzie Boot SZUKA `application.properties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainKebabCaseVsCamelCaseKeyConvention {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij konwencje "kebab-case" (`max-size`) w
         * plikach konfiguracyjnych vs "camelCase" (`maxSize`) w Javie -
         * jak Boot je LACZY?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementListBindingFromProperties {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj liste w `application.properties` (indeksowana
         * `[0]`, `[1]`...) - zwiaz ja z `List<String>` przez `@Value`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementMapBindingFromProperties {
        /*
         * 🧪 Zadanie 12:
         * Zwiaz grupe kluczy z `Map<String, String>` (powiaz z
         * `_20_spring_core/Lesson16`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementProfileSpecificOverrideDemo {
        /*
         * 🧪 Zadanie 13:
         * Zasymuluj (2 zestawy `Properties` programowo) "override"
         * profilowy - zapowiedz `Lesson06_ProfilesInSpringBoot`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRandomValuePropertySource {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `${random.uuid}`/`${random.int}` (wbudowany
         * `RandomValuePropertySource` Boota) w `@Value`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementPropertyPlaceholderReferencingAnother {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj wlasciwosc odwolujaca sie do INNEJ (zagniezdzony
         * placeholder, jak w `_20_spring_core/Lesson16`, Zadanie 14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareYamlListSyntaxWithPropertiesIndexedSyntax {
        /*
         * 🧪 Zadanie 16:
         * Napisz TA SAMA liste w YAML (`- element`) i w .properties
         * (`[0]=element`) - porownaj czytelnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementCustomPropertySourceLoaderConceptually {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: sprawdz (dokumentacja) czy Boot wspiera INNE
         * formaty konfiguracji (np. `.json`) przez wlasny
         * `PropertySourceLoader`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementEnvironmentPostProcessorConceptually {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala (dokumentacja): sprawdz `EnvironmentPostProcessor` -
         * jak MOZNA programowo dodac WLASNE zrodlo konfiguracji PRZED
         * startem calego kontekstu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainRelaxedBindingRules {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij "relaxed binding" Boota - czy
         * `MAX_SIZE`, `max-size`, `maxSize` wszystkie wiaza sie z tym
         * samym polem?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildComparisonReportPropertiesVsYaml {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza .properties vs
         * .yml - kolumny: czytelnosc przy zagniezdzeniu, ryzyko bledu,
         * wsparcie list/map.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomPropertySourceLoaderForNewFormat {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `PropertySourceLoader` obslugujacy
         * WYMYSLONY format pliku (np. `.ini`) - zarejestruj przez
         * `spring.factories`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementConfigDataLocationResolverConceptually {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala (dokumentacja, zaawansowane): sprawdz mechanizm
         * `ConfigDataLocationResolver` - jak Boot 2.4+ PRZEBUDOWAL
         * ladowanie konfiguracji (`spring.config.import`)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementEncryptedPropertyValuesIntegration {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_19_security_basics/Lesson18_SecretsManagement` -
         * zaimplementuj `EnvironmentPostProcessor` odszyfrowujacy
         * wartosci z prefiksem `ENC(...)` PRZED udostepnieniem ich reszcie
         * aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasurePropertyResolutionPerformanceAtScale {
        /*
         * 🧪 Zadanie 24:
         * Zmierz czas odczytu 10 000 roznych kluczy przez
         * `Environment.getProperty(...)` - czy Boot cache'uje wyniki?
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementConfigurationDriftDetector {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj narzedzie porownujace 2 pliki konfiguracyjne
         * (np. "dev" i "prod", symulowane Mapami) - wykryj klucze OBECNE
         * tylko w jednym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementConfigurationDocumentationGenerator {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj generator dokumentacji (Markdown/tekst) ze WSZYSTKICH
         * uzywanych w projekcie kluczy `@Value` (przez skanowanie zrodel,
         * uproszczone) - z opisem KAZDEGO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementFullConfigTreeSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj (recznie, jako zagniezdzona Mapa) PELNE "drzewo
         * konfiguracji" YAML z 4+ poziomami zagniezdzenia - spłaszcz je
         * do kropkowanych kluczy (jak robi to Boot wewnetrznie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareSpringConfigImportWithLegacyLocations {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj NOWY mechanizm `spring.config.import`
         * (Boot 2.4+) ze STARSZYM `spring.config.location` - jaka jest
         * roznica filozofii?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementConfigValidationPipelineAtStartup {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj `ApplicationRunner` (zapowiedz `Lesson07`) walidujacy
         * SPOJNOSC calej konfiguracji PRZY STARCIE (np. "port musi byc w
         * zakresie") - PRZERWIJ start przy bledzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMultiFormatConfigurationSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system LACZACY: .properties + .yml + zmienne
         * srodowiskowe + wartosci domyslne w kodzie - jeden spojny raport
         * pokazujacy, SKAD pochodzi KAZDA finalna wartosc konfiguracji.
         */
        public static void main(String[] args) { }
    }
}
