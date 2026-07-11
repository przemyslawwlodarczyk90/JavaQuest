package com.example.javaquest._20_spring_core.Lesson09_ConfigurationAndBeanAnnotation;

public class _Exercises_Lesson09_ConfigurationAndBeanAnnotation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhenComponentIsNotEnough {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, kiedy `@Component` NIE wystarczy i
         * potrzebne jest `@Configuration`+`@Bean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RegisterExternalStyleClassViaBean {
        /*
         * 🧪 Zadanie 2:
         * Zarejestruj WLASNA, "zewnetrzna" klase (BEZ zadnej adnotacji
         * Springa) przez `@Configuration`+`@Bean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_InjectDependencyAsBeanMethodParameter {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj 2 metody `@Bean`, gdzie DRUGA przyjmuje PIERWSZA jako
         * PARAMETR - zweryfikuj poprawne polaczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_VerifySameInstanceViaParameterInjection {
        /*
         * 🧪 Zadanie 4:
         * Zweryfikuj (operatorem `==`), ze zaleznosc wstrzyknieta jako
         * PARAMETR metody `@Bean` to TA SAMA instancja co samodzielny
         * bean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReproduceFullModeDemo {
        /*
         * 🧪 Zadanie 5:
         * Odtworz demo "full mode" z teorii dla WLASNYCH 2 klas - policz
         * ile razy metoda `@Bean` faktycznie sie wykonala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReproduceLiteModeDemo {
        /*
         * 🧪 Zadanie 6:
         * Odtworz TO SAMO demo z `@Configuration(proxyBeanMethods = false)` -
         * porownaj liczbe wywolan i tozsamosc obiektow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainCglibProxyingInOwnWords {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij wlasnymi slowami, CO ROBI CGLIB w
         * kontekscie klasy `@Configuration` w trybie "full".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListWhenLiteModeIsSafe {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, KIEDY bezpiecznie mozna uzyc
         * `proxyBeanMethods = false` - co MUSI byc prawda o klasie
         * konfiguracyjnej?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CreateBeanMethodWithMultipleParameters {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj metode `@Bean` przyjmujaca DWIE rozne zaleznosci
         * jako parametry naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareComponentAndBeanForOwnedClass {
        /*
         * 🧪 Zadanie 10:
         * Zarejestruj TA SAMA, WLASNA klase RAZ przez `@Component`, RAZ
         * przez `@Bean` - zweryfikuj, ze OBIE dzialaja identycznie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementBeanMethodWithInitAndDestroyAttributes {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@Bean(initMethod = "...", destroyMethod = "...")` -
         * zweryfikuj wywolanie OBU metod w odpowiednim momencie
         * (zapowiedz `Lesson18_LifecycleCallbacks`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementBeanMethodWithCustomName {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `@Bean("wlasnaNazwa")` - zweryfikuj przez
         * `context.containsBean(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementThreeLevelDependencyChainViaParameters {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj 3 metody `@Bean` tworzace lancuch zaleznosci (A<-B<-C)
         * WYLACZNIE przez parametry metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasurePerformanceDifferenceFullVsLiteMode {
        /*
         * 🧪 Zadanie 14:
         * Zmierz czas tworzenia kontekstu z 20+ beanami DLA obu trybow
         * (full/lite) - czy roznica jest ZAUWAZALNA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementConditionalBeanCreationInsideMethod {
        /*
         * 🧪 Zadanie 15:
         * W metodzie `@Bean` uzyj zwyklego `if`, zeby zwrocic RÓZNA
         * implementacje interfejsu w zaleznosci od warunku (np. zmiennej
         * srodowiskowej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CombineComponentScanWithMultipleBeanMethods {
        /*
         * 🧪 Zadanie 16:
         * W JEDNEJ klasie `@Configuration` polacz `@ComponentScan` z
         * WIELOMA metodami `@Bean` (symulujacymi rozne "zewnetrzne"
         * klasy) - zweryfikuj wspolistnienie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementBeanMethodThrowingCheckedException {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj metode `@Bean` rzucajaca CHECKED wyjatek (np.
         * `IOException`) - sprawdz, jak Spring to obsluguje przy starcie
         * kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DemonstrateFullModeWithThreeInternalCalls {
        /*
         * 🧪 Zadanie 18:
         * Rozbuduj demo full mode o 3. metode `@Bean`, ktora WEWNETRZNIE
         * woła TE SAMA zaleznosc CO INNA metoda - zweryfikuj, ze WSZYSTKIE
         * dostaja TEN SAM singleton.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainWhySpringBootUsesLiteModeForAutoConfig {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij, dlaczego Spring Boot CZESTO uzywa
         * `proxyBeanMethods = false` we WLASNYCH klasach
         * auto-konfiguracyjnych (zapowiedz `_21_spring_boot/Lesson04`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildComparisonTableFullVsLiteMode {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza full vs lite
         * mode - kolumny: mechanizm, koszt startu, bezpieczenstwo
         * "recznych" wywolan.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InspectCglibGeneratedClassAtRuntime {
        /*
         * 🧪 Zadanie 21:
         * Wypisz `getClass().getName()` beana `@Configuration` w trybie
         * full - zaobserwuj, ze to NIE jest Twoja oryginalna klasa (CGLIB
         * wygenerowal PODKLASE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareCglibClassNameBetweenFullAndLiteMode {
        /*
         * 🧪 Zadanie 22:
         * Porownaj `getClass().getName()` DLA TEJ SAMEJ logicznej klasy w
         * trybie full vs lite - w KTORYM przypadku NIE ma podklasy CGLIB?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementBeanMethodReturningFactoryBean {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj metode `@Bean` zwracajaca `FactoryBean<T>` -
         * zweryfikuj, ze `getBean()` zwraca WYNIK `getObject()`, NIE sam
         * `FactoryBean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementStaticBeanMethodForBeanFactoryPostProcessor {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj `static` metode `@Bean` zwracajaca
         * `BeanFactoryPostProcessor` - wyjasnij w komentarzu, DLACZEGO
         * TAKIE metody MUSZA byc `static` (podpowiedz: kolejnosc
         * inicjalizacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildDeepDependencyGraphWithMixedModes {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj graf zaleznosci LACZACY 2 klasy `@Configuration` (jedna
         * full, jedna lite) - zweryfikuj poprawne dzialanie CALOSCI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCustomScopeAwareBeanMethod {
        /*
         * 🧪 Zadanie 26:
         * Polacz `@Bean` z `@Scope("prototype")` (zapowiedz `Lesson14_
         * BeanScopes`) - zweryfikuj, ze KAZDE wywolanie `getBean()` daje
         * NOWA instancje, mimo full mode.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasureMemoryOverheadOfCglibProxying {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj 100 klas `@Configuration` (w petli/generowanych
         * programowo) w trybie full - zmierz przyblizony narzut pamieciowy
         * proxy CGLIB wzgledem lite mode.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRealisticExternalLibraryConfigurationScenario {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj realistyczna konfiguracje 3 "zewnetrznych" klas z
         * zaleznosciami MIEDZY soba (np. klient HTTP -> parser odpowiedzi
         * -> cache) - WYLACZNIE przez `@Configuration`+`@Bean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ExplainTradeoffsOfChoosingLiteModeGlobally {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: przedyskutuj, czy ustawienie `proxyBeanMethods =
         * false` GLOBALNIE (dla wszystkich klas `@Configuration` we
         * WLASNEJ aplikacji) to dobry pomysl - jakie ryzyko?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteConfigurationStyleShowcaseCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo laczace: rejestracje "zewnetrznej" klasy,
         * wstrzykiwanie przez parametry, porownanie full/lite mode,
         * `initMethod`/`destroyMethod` - jeden spojny raport pokazujacy
         * WSZYSTKIE mozliwosci `@Configuration`+`@Bean`.
         */
        public static void main(String[] args) { }
    }
}
