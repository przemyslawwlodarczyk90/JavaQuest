package com.example.javaquest._20_spring_core.Lesson14_BeanScopes;

public class _Exercises_Lesson14_BeanScopes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSingletonVsPrototypeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami roznice miedzy
         * `singleton` a `prototype`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnPrototypeScopedBean {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY bean `@Scope("prototype")` z licznikiem
         * instancji jak w teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyPrototypeGivesDifferentInstances {
        /*
         * 🧪 Zadanie 3:
         * Pobierz bean z Zadania 2 TRZYKROTNIE - zweryfikuj operatorem
         * `==`, ze to 3 RÓZNE instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReproduceThePrototypeInSingletonTrap {
        /*
         * 🧪 Zadanie 4:
         * Odtworz pulapke z teorii (prototype wstrzykniety do
         * singletona) dla WLASNEJ pary klas - zweryfikuj, ze ID sie NIE
         * zmienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixTrapWithObjectProvider {
        /*
         * 🧪 Zadanie 5:
         * Napraw pulapke z Zadania 4 uzywajac `ObjectProvider<T>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyDefaultScopeIsSingleton {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, dlaczego `singleton` jest DOMYSLNYM
         * zakresem w Springu (podpowiedz: koszt tworzenia obiektow,
         * typowe zastosowania - serwisy/repozytoria).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListWebScopesAndTheirLifetime {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wymien 3 zakresy web-owe i opisz "czas zycia"
         * KAZDEGO (1 zadanie HTTP? 1 sesja uzytkownika?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhenPrototypeMakesSense {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: podaj REALNY przyklad, gdzie zakres `prototype`
         * ma SENS (np. obiekt ze stanem MUTOWALNYM, uzywanym przez
         * wiele niezaleznych operacji naraz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_VerifySingletonSharesStateAcrossInjectionPoints {
        /*
         * 🧪 Zadanie 9:
         * Wstrzyknij TEN SAM singleton bean do DWOCH RÓZNYCH serwisow -
         * zweryfikuj, ze zmiana stanu w JEDNYM jest WIDOCZNA w drugim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareScopeAnnotationSyntaxOldAndNew {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: sprawdz (dokumentacja), czy istnieja SKROTOWE
         * adnotacje dla popularnych zakresow (np. `@RequestScope`,
         * `@SessionScope`) - jak sie MAJA do `@Scope("...")`?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementLookupMethodInjectionAlternative {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj rozwiazanie pulapki prototype PRZEZ `@Lookup`
         * (metoda abstrakcyjna) ZAMIAST `ObjectProvider` - porownaj OBA
         * podejscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureMemoryImpactOfPrototypeVsSingleton {
        /*
         * 🧪 Zadanie 12:
         * Utworz 10 000 beanow `prototype` w petli (przez `getBean()`) -
         * porownaj zuzycie pamieci/czas z 10 000 wywolaniami `getBean()`
         * na singletonie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExplainWhoDestroysPrototypeBeans {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: wyjasnij, DLACZEGO kontener Springa NIE
         * zarzadza NISZCZENIEM beanow `prototype` (w odroznieniu od
         * singletonow) - kto jest za to ODPOWIEDZIALNY?
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomScopeUsingThreadLocal {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj WLASNY, prosty zakres "per-watek" (przez
         * `ThreadLocal` + `org.springframework.beans.factory.config.Scope`)
         * - zarejestruj przez `ConfigurableBeanFactory.registerScope(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareObjectProviderWithProviderFromJakartaInject {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: porownaj `ObjectProvider<T>` (Springowe) z
         * `jakarta.inject.Provider<T>` (standard JSR-330) - czy Spring
         * wspiera OBA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementSingletonWrappingMultiplePrototypesInList {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj singleton, ktory zbiera WIELE swiezych instancji
         * prototype (przez `ObjectProvider`) do `List<T>` w PETLI - kazdy
         * element ma INNE ID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementScopedProxyModeConceptually {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala (lub eksperymentalnie): sprawdz, czym jest
         * `proxyMode` w `@Scope` - jak rozwiazuje problem wstrzykiwania
         * "waskiego" zakresu (np. request) do SZERSZEGO (singleton)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareScopeMismatchErrorsAtDifferentTimes {
        /*
         * 🧪 Zadanie 18:
         * Sprobuj wstrzyknac bean `request`-scoped do singletona BEZ
         * `proxyMode` w kontekscie BEZ Spring MVC (poza `_22_spring_web`) -
         * zaobserwuj i zapisz DOKLADNY blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementStatefulPrototypeUsedInParallel {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj bean prototype uzywany rownolegle przez 5 watkow
         * (`_05_multithreading`) - zweryfikuj, ze KAZDY watek dostaje
         * WLASNA instancje, BEZ konfliktow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildScopeComparisonTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza WSZYSTKICH
         * zakresow z tej lekcji - kolumny: nazwa, czas zycia, typowe
         * zastosowanie, wymaga Spring MVC?.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomScopeWithExpirationPolicy {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY zakres z WYGASANIEM (np. "per 5 sekund" -
         * nowa instancja co 5 sekund, ta sama w tym oknie czasowym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AnalyzeBeanDefinitionScopeMetadataProgrammatically {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj metode skanujaca WSZYSTKIE beany w kontekscie i
         * grupujaca je WEDLUG zakresu (`BeanDefinition.getScope()`) -
         * wypisz raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementProxyBasedLazyPrototypeWrapper {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj WLASNY, uproszczony "scoped proxy" (przez
         * `java.lang.reflect.Proxy`) - kazde wywolanie metody na
         * "proxy" pobiera SWIEZY prototype spod spodu (jak
         * `proxyMode = TARGET_CLASS` w Springu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareMemoryLeakRiskOfLongLivedSingletonHoldingPrototypes {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: przedyskutuj ryzyko WYCIEKU pamieci, gdyby
         * dlugozyjacy singleton zbieral (bez limitu) referencje do
         * KOLEJNYCH prototype'ow przez `ObjectProvider` w kolekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRequestScopeSimulationWithoutSpringMvc {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj zakres "request" (BEZ Spring MVC) - `ThreadLocal`
         * przechowujacy "biezace zadanie", bean tworzony/czyszczony NA
         * POCZATKU/KONCU symulowanego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BenchmarkObjectProviderOverheadVsDirectInjection {
        /*
         * 🧪 Zadanie 26:
         * Zmierz narzut wydajnosciowy `ObjectProvider.getObject()` (100
         * 000 wywolan) w porownaniu do bezposredniego wstrzykniecia
         * singletona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementHybridScopeSingletonWithPrototypeFactory {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj singleton udostepniajacy METODE fabrykujaca
         * (`createNew()`) zwracajaca NOWA instancje prototype - jako
         * ALTERNATYWA dla `ObjectProvider`/`@Lookup` (czysta Java,
         * BEZ magii Springa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeRealWorldScopeMisuseInLegacyCode {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj (liste "klas" z metadanymi) audyt istniejacego
         * projektu pod katem NIEWLASCIWEGO uzycia zakresow (np. bean
         * `prototype` NIGDY nie pobierany przez `ObjectProvider`, wiec
         * FAKTYCZNIE dziala jak singleton).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementScopeAwareCachingDecorator {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj dekorator cache'ujacy (wzorem `_13_libraries/
         * Lesson27` Caffeine) DLA prototype beanow - "cachuj" na
         * podstawie klucza, zeby UNIKNAC nadmiarowego tworzenia
         * kosztownych obiektow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteScopeShowcaseCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo laczace: singleton, prototype, pulapke +
         * jej naprawe, WLASNY zakres (Zadanie 14/21) - jeden spojny
         * raport pokazujacy WSZYSTKIE mozliwosci zakresow beanow.
         */
        public static void main(String[] args) { }
    }
}
