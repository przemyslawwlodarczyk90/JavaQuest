package com.example.javaquest._21_spring_boot.Lesson01_WhatIsSpringBoot;

public class _Exercises_Lesson01_WhatIsSpringBoot {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRelationshipSpringBootSpringFramework {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij relacje miedzy Spring Boot a Spring
         * Framework (powiaz z `_20_spring_core/Lesson01`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListThreeAnnotationsInsideSpringBootApplication {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wymien 3 adnotacje, ktore laczy w sobie
         * `@SpringBootApplication`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyMetaAnnotationsWithReflection {
        /*
         * 🧪 Zadanie 3:
         * Uzyj reflekcji (jak w teorii), zeby zweryfikowac WLASNA
         * hipoteze o adnotacjach na `@SpringBootApplication`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateMinimalBootAppWithOwnComponent {
        /*
         * 🧪 Zadanie 4:
         * Utworz WLASNA minimalna aplikacje Boot (jak w teorii) z
         * WLASNYM `@Component`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareContextTypeReturnedByBothApproaches {
        /*
         * 🧪 Zadanie 5:
         * Porownaj typ kontekstu zwracany przez `SpringApplication.run()`
         * z typem z `AnnotationConfigApplicationContext`
         * (`_20_spring_core/Lesson01`) - CO je LACZY?
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWebApplicationTypeNoneOption {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, po co uzyto
         * `WebApplicationType.NONE` w tej lekcji - co by sie stalo BEZ
         * tego ustawienia?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareCodeLengthBootVsRawSpringCore {
        /*
         * 🧪 Zadanie 7:
         * Policz linie kodu potrzebne do uzyskania DZIALAJACEGO
         * kontekstu z 1 beanem - Boot (ta lekcja) vs goly Spring
         * (`_20_spring_core/Lesson01`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListSpringEcosystemModulesFromMemory {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: odtworz z pamieci mape ekosystemu Springa z
         * `_20_spring_core/Lesson01`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CheckSpringBootVersionOfThisProject {
        /*
         * 🧪 Zadanie 9:
         * Uzyj `SpringBootVersion.getVersion()` (jak w
         * `_20_spring_core/Lesson02`) - potwierdz, ze ten kurs UCZY
         * Spring Boot 3.4.x.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhySpringBootWasCreated {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, JAKI problem rozwiazal Spring Boot,
         * ktorego "goly" Spring Framework NIE rozwiazywal.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_CreateBootAppWithMultipleComponents {
        /*
         * 🧪 Zadanie 11:
         * Utworz minimalna aplikacje Boot z 3+ WSPOLZALEZNYMI
         * komponentami (constructor injection, jak `_20_spring_core/
         * Lesson10`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VerifyComponentScanBasePackageBehavior {
        /*
         * 🧪 Zadanie 12:
         * Zweryfikuj (przez `getBeanDefinitionNames()`), KTORE beany
         * zostaly znalezione przez domyslny `@ComponentScan` w Twojej
         * minimalnej aplikacji Boot.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseSpringApplicationBuilderWithProfile {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `SpringApplicationBuilder.profiles(...)`, zeby aktywowac
         * profil (powiaz z `_20_spring_core/Lesson15`) - porownaj z
         * `setActiveProfiles` uzywanym tam.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExploreConfigurableApplicationContextApi {
        /*
         * 🧪 Zadanie 14:
         * Zbadaj metody `ConfigurableApplicationContext` (interfejs
         * zwracany przez `SpringApplication.run()`) - ktore SA nowe
         * wzgledem zwyklego `ApplicationContext`?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureStartupTimeBootVsRawContext {
        /*
         * 🧪 Zadanie 15:
         * Zmierz czas startu 10 identycznych beanow - Boot vs goly
         * `AnnotationConfigApplicationContext` - czy jest ROZNICA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainSpringBootConfigurationAnnotation {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala (lub dokumentacja): wyjasnij, czym rozni sie
         * `@SpringBootConfiguration` od zwyklego `@Configuration`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementCustomMainMethodArgumentsHandling {
        /*
         * 🧪 Zadanie 17:
         * Przekaz WLASNE argumenty do `SpringApplication.run(Klasa.class,
         * args)` - odczytaj je w `main()` (zapowiedz
         * `Lesson07_CommandLineRunner`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareApplicationContextIdAcrossBothApproaches {
        /*
         * 🧪 Zadanie 18:
         * Porownaj `context.getId()` dla kontekstu Boot i gologo
         * `AnnotationConfigApplicationContext` - co widac w nazwie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementBannerCustomization {
        /*
         * 🧪 Zadanie 19:
         * Uzyj `SpringApplicationBuilder.bannerMode(Banner.Mode.OFF)` -
         * porownaj wyjscie konsoli z WLACZONYM/WYLACZONYM bannerem Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildComparisonTableBootVsRawSpring {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza Spring Boot vs
         * goly Spring Framework - kolumny: aspekt, kto to robi automatycznie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomSpringApplicationRunListener {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `SpringApplicationRunListener` (interfejs
         * Boota) reagujacy na etapy startu aplikacji (starting/started/
         * running).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementApplicationContextInitializer {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNY `ApplicationContextInitializer` -
         * dodawany PRZED `refresh()`, podobnie do BeanFactoryPostProcessor
         * z `_20_spring_core/Lesson19`, ale na WCZESNIEJSZYM etapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AnalyzeSpringApplicationSourceStartupSequence {
        /*
         * 🧪 Zadanie 23:
         * Zbadaj (dokumentacja/kod zrodlowy) DOKLADNA sekwencje
         * wywolan wewnatrz `SpringApplication.run()` - opisz WLASNYMI
         * slowami co najmniej 5 krokow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareBootWithManuallyBuiltEquivalentContainer {
        /*
         * 🧪 Zadanie 24:
         * Odtworz RECZNIE (BEZ `SpringApplication`) TAKI SAM efekt jak
         * Boot dla PROSTEJ aplikacji - component scan + auto-rejestracja
         * kilku BeanPostProcessor - ile kodu to wymaga?
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFailFastValidationBeforeContextStart {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj `ApplicationContextInitializer` walidujacy
         * WYMAGANE zmienne srodowiskowe PRZED startem calego kontekstu -
         * rzuc czytelny blad, jesli brakuje ktorejs.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureMemoryFootprintOfBootstrapMachinery {
        /*
         * 🧪 Zadanie 26:
         * Zmierz przyblizone zuzycie pamieci "maszynerii" Boota
         * (dodatkowe beany infrastrukturalne) wzgledem gologo kontenera -
         * ile to WLASCIWIE kosztuje?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomStartupLoggerReplacingBanner {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj WLASNY `Banner` (interfejs Boota) zamiast
         * domyslnego ASCII-artu - wypisz WLASNE informacje o starcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareSpringBootWithOtherOpinionatedFrameworks {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj filozofie "opinionated" (majaca zdanie)
         * Spring Boota z INNYM "opinionated" frameworkiem/narzedziem, ktory
         * znasz z tego kursu (podpowiedz: Maven Standard Directory Layout
         * z `_11_buildtools`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementGracefulShutdownHookForBootApp {
        /*
         * 🧪 Zadanie 29:
         * Zarejestruj shutdown hook (`context.registerShutdownHook()`) dla
         * aplikacji Boot - zweryfikuj poprawne zamkniecie przy sygnale
         * przerwania (powiaz z `_20_spring_core/Lesson18`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteFromScratchBootstrapExplanationCapstone {
        /*
         * 🧪 Zadanie 30:
         * Napisz (jako komentarz/String) KOMPLETNE, KROK PO KROKU
         * wyjasnienie, co DOKLADNIE dzieje sie od wywolania `main()` do
         * momentu, gdy `GreetingService` jest gotowy do uzycia w tej
         * lekcji - polacz WIEDZE z `_20_spring_core` (co robi kontener) z
         * NOWA wiedza (co Boot automatyzuje).
         */
        public static void main(String[] args) { }
    }
}
