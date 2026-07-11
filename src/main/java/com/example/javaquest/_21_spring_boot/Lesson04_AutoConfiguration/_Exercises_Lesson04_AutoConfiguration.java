package com.example.javaquest._21_spring_boot.Lesson04_AutoConfiguration;

public class _Exercises_Lesson04_AutoConfiguration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAutoConfigurationInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami mechanizm
         * auto-konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnConditionalOnClassBean {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY bean `@ConditionalOnClass` sprawdzajacy
         * obecnosc INNEJ, znanej klasy z tego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementConditionalOnMissingBeanFallback {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNA pare "domyslny bean" + "bean uzytkownika"
         * jak w teorii - zweryfikuj OBA scenariusze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementFeatureFlagWithConditionalOnProperty {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj WLASNA feature flage `@ConditionalOnProperty` dla
         * WYMYSLONEJ funkcjonalnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseHavingValueMatchIfMissingAttribute {
        /*
         * 🧪 Zadanie 5:
         * Uzyj `matchIfMissing = true` w `@ConditionalOnProperty` -
         * zweryfikuj, ze bean powstaje TAKZE gdy wlasciwosc W OGOLE nie
         * jest ustawiona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReadAutoConfigurationImportsFileAndCountEntries {
        /*
         * 🧪 Zadanie 6:
         * Odtworz odczyt pliku `AutoConfiguration.imports` z teorii -
         * wypisz WSZYSTKIE (nie tylko 5 pierwszych) nazwy klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FindSpecificAutoConfigurationClassByKeyword {
        /*
         * 🧪 Zadanie 7:
         * Przeszukaj liste z Zadania 6 pod katem klas zawierajacych
         * "Jackson" lub "Web" w nazwie - wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyConditionalOnMissingBeanOrderMatters {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, dlaczego KOLEJNOSC przetwarzania
         * konfiguracji ma znaczenie dla `@ConditionalOnMissingBean` -
         * co, gdyby auto-konfiguracja byla przetworzona PRZED
         * konfiguracja uzytkownika?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_VerifyRealDataSourceAutoConfigurationAbsence {
        /*
         * 🧪 Zadanie 9:
         * Sprawdz, czy w PROSTEJ aplikacji Boot (bez `spring-boot-
         * starter-data-jpa`) istnieje jakikolwiek bean `DataSource` -
         * wyjasnij WYNIK.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListThreeOtherConditionalAnnotationsFromDocs {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (dokumentacja): wymien 3 INNE adnotacje
         * `@Conditional*` spoza tej lekcji (np. `@ConditionalOnBean`,
         * `@ConditionalOnResource`, `@ConditionalOnExpression`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementConditionalOnBeanDependentConfiguration {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@ConditionalOnBean` - zarejestruj bean TYLKO jesli INNY,
         * KONKRETNY bean juz istnieje w kontekscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementConditionalOnExpressionWithSpel {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@ConditionalOnExpression` z wyrazeniem SpEL (powiaz z
         * `_20_spring_core/Lesson17`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCustomConditionClass {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj WLASNA klase `Condition` (interfejs Springa,
         * powiaz z `_20_spring_core/Lesson15` gdzie `@Profile` uzywal
         * podobnego mechanizmu) - uzyj jej z `@Conditional(WlasnaKlasa.class)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementAutoConfigurationOrderingWithAutoConfigureAfter {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala (dokumentacja): wyjasnij `@AutoConfigureAfter`/
         * `@AutoConfigureBefore` - po co kontrolowac KOLEJNOSC klas
         * auto-konfiguracji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareConditionalOnClassWithConditionalOnMissingClass {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj przyklad `@ConditionalOnMissingClass` - bean
         * powstajacy TYLKO gdy KONKRETNA klasa NIE jest na classpath
         * (przeciwienstwo `@ConditionalOnClass`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementMultiplePropertyConditionsOnSameBean {
        /*
         * 🧪 Zadanie 16:
         * Polacz WIELE `@ConditionalOnProperty` NA TEJ SAMEJ metodzie
         * `@Bean` (2+ adnotacje) - zweryfikuj, ze WSZYSTKIE warunki
         * musza byc spelnione naraz (AND).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ParseAutoConfigurationImportsAndGroupByPackage {
        /*
         * 🧪 Zadanie 17:
         * Rozbuduj odczyt pliku z Zadania 6 - pogrupuj klasy WEDLUG
         * pakietu (np. `org.springframework.boot.autoconfigure.web`) -
         * policz ile klas jest w KAZDEJ grupie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementAutoConfigurationForCustomLibraryStyle {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj (zaimplementuj) auto-konfiguracje dla WYMYSLONEJ,
         * WLASNEJ "biblioteki" (klasa+interfejs) - z `@ConditionalOnClass`
         * i `@ConditionalOnMissingBean` razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainDifferenceBetweenAutoConfigurationAndComponentScan {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij, DLACZEGO auto-konfiguracja NIE jest
         * po prostu component-scanningiem (`_20_spring_core/Lesson05`) -
         * jaka jest KLUCZOWA roznica w PODEJSCIU?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildConditionalAnnotationDecisionTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele decyzyjna - dla danego
         * scenariusza (np. "chce bean TYLKO jesli biblioteka jest
         * obecna I uzytkownik nic nie zdefiniowal") wskaz WLASCIWA
         * kombinacje adnotacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFullCustomAutoConfigurationClass {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PELNA klase `@AutoConfiguration` (nie tylko
         * `@Configuration`) - z `@ConditionalOnClass`+
         * `@ConditionalOnMissingBean` - zapowiedz `Lesson15_
         * CustomAutoConfigurationAndStarters`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RegisterCustomAutoConfigurationViaImportsFile {
        /*
         * 🧪 Zadanie 22:
         * Zarejestruj klase z Zadania 21 przez WLASNY plik `META-INF/
         * spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
         * (na dysku, w `src/main/resources`) - zweryfikuj, ze Boot ja
         * ZNAJDUJE automatycznie, BEZ jawnego `.sources(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareOldSpringFactoriesWithNewMechanismPractically {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj TE SAMA auto-konfiguracje ZAREJESTROWANA przez
         * STARY mechanizm (`META-INF/spring.factories`, wciaz honorowany
         * dla wstecznej kompatybilnosci) - zweryfikuj, ze TEZ dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementConditionEvaluationReportInspection {
        /*
         * 🧪 Zadanie 24:
         * Uzyj `--debug` (lub programowego odpowiednika) zeby wygenerowac
         * "Condition Evaluation Report" - PRAWDZIWY raport Boota
         * pokazujacy, KTORE auto-konfiguracje sie WLACZYLY, a KTORE NIE
         * i DLACZEGO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureStartupTimeImpactOfManyAutoConfigurations {
        /*
         * 🧪 Zadanie 25:
         * Zmierz roznice czasu startu miedzy aplikacja z 1 starterem a
         * aplikacja z WSZYSTKIMI starterami tego projektu naraz -
         * ILE kosztuje ewaluacja WSZYSTKICH warunkow?
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCustomConditionCheckingSystemProperty {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj WLASNA `Condition` sprawdzajaca WLASCIWOSC
         * systemowa JVM (nie Springa) - np. dostepna pamiec, system
         * operacyjny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExplainWhyAutoConfigurationClassesAreLoadedLazily {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala (dokumentacja): wyjasnij, DLACZEGO Boot 2.2+
         * LENIWIE laduje (nie tworzy od razu wszystkich obiektow klas)
         * setki klas auto-konfiguracji - jaki to ma zwiazek z wydajnoscia
         * startu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAutoConfigurationExclusionMechanism {
        /*
         * 🧪 Zadanie 28:
         * Uzyj `@SpringBootApplication(exclude = {Klasa.class})` -
         * WYLACZ KONKRETNA auto-konfiguracje (np. wlasna z Zadania 21) -
         * zweryfikuj, ze bean juz NIE powstaje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildAutoConfigurationDependencyGraphAnalyzer {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj analizator zaleznosci MIEDZY klasami
         * auto-konfiguracji (na podstawie `@AutoConfigureAfter`/`@AutoConfigureBefore`,
         * symulowane dane) - wypisz KOLEJNOSC ich przetwarzania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteAutoConfigurationSimulatorCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, WLASNY "mini-silnik auto-konfiguracji" -
         * skanuje liste klas z WLASNYMI adnotacjami `@Conditional*`,
         * ewaluuje warunki, rejestruje pasujace beany - dzialajaca,
         * uproszczona replika mechanizmu Boota.
         */
        public static void main(String[] args) { }
    }
}
