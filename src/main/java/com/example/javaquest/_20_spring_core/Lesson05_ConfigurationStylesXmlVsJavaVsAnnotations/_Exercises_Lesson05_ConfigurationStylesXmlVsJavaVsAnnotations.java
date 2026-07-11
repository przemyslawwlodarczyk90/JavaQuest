package com.example.javaquest._20_spring_core.Lesson05_ConfigurationStylesXmlVsJavaVsAnnotations;

public class _Exercises_Lesson05_ConfigurationStylesXmlVsJavaVsAnnotations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreeConfigurationStylesInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami 3 style konfiguracji
         * Springa i w jakiej byly (chronologicznie) kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteXmlBeanDefinitionForNewClass {
        /*
         * 🧪 Zadanie 2:
         * Napisz WLASNY XML z definicja beana dla nowej, prostej klasy
         * (np. `Calculator`) - zaladuj go przez `XmlBeanDefinitionReader`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AnnotateOwnClassWithComponent {
        /*
         * 🧪 Zadanie 3:
         * Oznacz WLASNA klase `@Component`, pobierz ja z
         * `AnnotationConfigApplicationContext` przez component-scan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RegisterExternalStyleClassViaJavaConfig {
        /*
         * 🧪 Zadanie 4:
         * Zarejestruj WLASNA klase (BEZ `@Component`, jak
         * `JavaConfigStyleGreeter`) przez `@Configuration`+`@Bean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TriggerXmlClassNameTypoError {
        /*
         * 🧪 Zadanie 5:
         * Celowo wprowadz literowke w nazwie klasy w XML (Zadanie 2) -
         * zaobserwuj DOKLADNY blad w RUNTIME.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareCompileTimeVsRuntimeSafety {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, dlaczego Java Config zapewnia
         * bezpieczenstwo typow W KOMPILACJI, a XML - dopiero w RUNTIME.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListWhenToUseJavaConfigOverComponent {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wymien co najmniej 2 sytuacje, w ktorych MUSISZ
         * uzyc `@Configuration`+`@Bean` zamiast `@Component`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountBeansRegisteredByEachStyle {
        /*
         * 🧪 Zadanie 8:
         * Dla kazdego z 3 stylow demo z teorii, uzyj
         * `getBeanDefinitionNames()` zeby policzyc WLASNE (nie
         * infrastrukturalne) beany zarejestrowane w kontekscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyXmlIsRarelySeenToday {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego Spring Boot NIGDY nie
         * generuje/oczekuje konfiguracji XML - co ja zastapilo?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CombineAllThreeStylesInSingleContext {
        /*
         * 🧪 Zadanie 10:
         * Sprobuj polaczyc WSZYSTKIE 3 style W JEDNYM kontekscie (import
         * XML + component-scan + @Bean) - czy kontener na to pozwala?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementXmlConfigWithConstructorArgs {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj definicje XML o `<constructor-arg>` przekazujacy
         * WARTOSC (np. String) do konstruktora beana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementXmlConfigWithBeanReference {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj W XML DWA beany, gdzie JEDEN odwoluje sie do
         * DRUGIEGO przez `<ref bean="..."/>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementJavaConfigWithBeanDependingOnAnotherBean {
        /*
         * 🧪 Zadanie 13:
         * W `@Configuration` zdefiniuj metode `@Bean`, ktora jako
         * PARAMETR przyjmuje INNY bean z TEJ SAMEJ klasy konfiguracyjnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CombineComponentScanWithExplicitBeanMethod {
        /*
         * 🧪 Zadanie 14:
         * W JEDNEJ klasie `@Configuration` polacz `@ComponentScan`
         * (Twoje klasy) z jedna metoda `@Bean` (symulujaca zewnetrzna
         * biblioteke) - zweryfikuj, ze OBA typy beanow sa dostepne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementImportResourceForXmlPlusJavaConfig {
        /*
         * 🧪 Zadanie 15:
         * Uzyj adnotacji `@ImportResource` na klasie `@Configuration`,
         * zeby zaladowac beany Z XML OBOK beanow z Java Config -
         * zweryfikuj, ze OBA sa dostepne w JEDNYM kontekscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainBeanNamingConventionAcrossStyles {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wyjasnij, SKAD bierze sie domyslna NAZWA beana
         * w kazdym z 3 stylow (atrybut `id` w XML, nazwa klasy z mala
         * litera w `@Component`, nazwa metody w `@Bean`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MigrateXmlConfigToJavaConfig {
        /*
         * 🧪 Zadanie 17:
         * Wez definicje XML z Zadania 11/12 i PRZEPISZ ja RECZNIE na Java
         * Config (`@Configuration`+`@Bean`) - zachowaj IDENTYCZNE
         * zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MigrateComponentScanToJavaConfig {
        /*
         * 🧪 Zadanie 18:
         * Wez klase `@Component` z Zadania 3 i PRZEPISZ jej rejestracje
         * na Java Config (usun `@Component`, dodaj metode `@Bean`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainProsAndConsTableForEachStyle {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj (jako liste rekordow) tabele zalet/wad kazdego z 3
         * stylow - kolumny: styl, zaleta, wada, typowe zastosowanie DZIS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementProfileSpecificXmlVsJavaConfig {
        /*
         * 🧪 Zadanie 20:
         * Zaladuj RÓZNA konfiguracje XML w zaleznosci od "profilu"
         * (symulowanego Stringiem) - porownaj z tym, jak zrobiloby to
         * `@Profile` w Java Config (zapowiedz `Lesson15_Profiles`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildXmlConfigGeneratorFromJavaClasses {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj metode, ktora dla listy klas GENERUJE tekst XML
         * z definicjami beanow (odwrotnosc tego, co robi kontener) -
         * pomoze zrozumiec STRUKTURE formatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomBeanFactoryPostProcessorForXmlStyle {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj prosty `BeanFactoryPostProcessor`, ktory MODYFIKUJE
         * definicje beana zaladowana z XML PRZED jej utworzeniem
         * (zapowiedz `Lesson19_BeanPostProcessorsAndContainerExtensionPoints`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareStartupPerformanceAcrossThreeStyles {
        /*
         * 🧪 Zadanie 23:
         * Zmierz czas tworzenia kontekstu DLA KAZDEGO z 3 stylow (10+
         * beanow kazdy) - czy styl konfiguracji WPLYWA na czas startu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementHybridLegacyMigrationScenario {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj REALISTYCZNY scenariusz migracji - "stary" modul z
         * konfiguracja XML wspoldziala z "nowym" modulem w Java Config W
         * JEDNYM kontekscie - typowe dla stopniowej migracji duzych
         * projektow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementConditionalBeanRegistrationInJavaConfig {
        /*
         * 🧪 Zadanie 25:
         * W `@Configuration` warunkowo zarejestruj RÓZNE implementacje
         * (przez zwykly `if` w metodzie `@Bean` zwracajacej interfejs) -
         * czego XML/component-scan NIE potrafia zrobic tak latwo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainWhyJavaConfigEnablesRefactoringSafety {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wyjasnij, dlaczego zmiana nazwy klasy (refaktoryzacja
         * w IDE) jest BEZPIECZNA przy Java Config, a RYZYKOWNA przy XML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementProgrammaticBeanRegistrationWithoutAnnotations {
        /*
         * 🧪 Zadanie 27:
         * Uzyj `GenericApplicationContext.registerBean(...)` (4. sposob,
         * spoza teorii) - zarejestruj beana BEZ zadnej z 3 pokazanych
         * technik - porownaj z pozostalymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildDecisionMatrixForChoosingConfigStyle {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj "macierz decyzyjna" (tekst/ASCII) - dla danego
         * scenariusza (wlasna klasa/cudza biblioteka/warunkowa logika/
         * legacy XML) wskaz REKOMENDOWANY styl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AnalyzeRealWorldLegacyXmlConfigSnippet {
        /*
         * 🧪 Zadanie 29:
         * Znajdz (lub napisz WZOREM prawdziwego) fragment XML z
         * `<property name="..." ref="..."/>` (setter injection w XML) -
         * przepisz go na Java Config z setter injection (Lesson04).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteThreeStyleComparisonCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNA aplikacje (min. 4 beany, z zaleznosciami
         * miedzy soba) zaimplementowana WSZYSTKIMI 3 stylami NARAZ (3
         * OSOBNE konteksty) - zweryfikuj, ze WSZYSTKIE daja IDENTYCZNY
         * wynik dzialania.
         */
        public static void main(String[] args) { }
    }
}
