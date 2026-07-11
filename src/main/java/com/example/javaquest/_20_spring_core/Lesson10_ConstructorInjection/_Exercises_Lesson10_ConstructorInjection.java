package com.example.javaquest._20_spring_core.Lesson10_ConstructorInjection;

public class _Exercises_Lesson10_ConstructorInjection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListFourReasonsConstructorInjectionIsRecommended {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 4 powody, dla ktorych constructor
         * injection jest REKOMENDOWANY styl w Springu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementSingleConstructorWithoutAutowired {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA klase `@Component` z JEDNYM konstruktorem,
         * BEZ `@Autowired` - zweryfikuj dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddSecondConstructorAndObserveError {
        /*
         * 🧪 Zadanie 3:
         * Dodaj DRUGI konstruktor do klasy z Zadania 2 (bez zadnego
         * @Autowired) - zaobserwuj i zapisz DOKLADNY blad przy starcie
         * kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixMultiConstructorErrorWithAutowired {
        /*
         * 🧪 Zadanie 4:
         * Napraw blad z Zadania 3, dodajac `@Autowired` na WLASCIWYM
         * konstruktorze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MakeFieldFinalAndVerifyImmutability {
        /*
         * 🧪 Zadanie 5:
         * Upewnij sie, ze pole wstrzykiwane przez konstruktor jest
         * `final` - sprobuj (i pokaz w komentarzu) przypisac je gdzie
         * indziej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateObjectManuallyWithFakeDependency {
        /*
         * 🧪 Zadanie 6:
         * Utworz obiekt z Zadania 2 RECZNIE (przez `new`), przekazujac
         * "fake" implementacje zaleznosci - BEZ zadnego ApplicationContext.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyBeanCreationExceptionOccursWithoutHint {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, dlaczego Spring RZUCA wyjatek (zamiast
         * "zgadywac") gdy widzi wiele konstruktorow bez wskazowki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementConstructorWithThreeDependencies {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj klase z 3 zaleznosciami wstrzykiwanymi przez
         * konstruktor naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareReadabilityLongConstructorVsSetters {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: gdy konstruktor ma 6+ parametrow, czy to ZAWSZE
         * oznacza "wroc do setterow"? Jaka INNA opcja istnieje
         * (podpowiedz: czy klasa robi za DUZO)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainDifferenceBetweenUsedAndUnusedConstructor {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, co dzieje sie z konstruktorem BEZ
         * `@Autowired` (przy wielu konstruktorach) - czy Spring go w
         * ogole "widzi"?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementUnitTestStyleVerificationWithoutSpringBootTest {
        /*
         * 🧪 Zadanie 11:
         * Napisz "test" (zwykla metoda `main`) weryfikujacy logike klasy
         * z Zadania 2 na PROSTYCH asercjach (`if`+wyjatek), BEZ ZADNEGO
         * frameworka testowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementConstructorInjectionWithGenericDependency {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj klase wstrzykujaca zaleznosc GENERYCZNA (np.
         * `List<Validator>` - wszystkie beany danego typu naraz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementOptionalConstructorDependency {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj konstruktor z zaleznoscia OPCJONALNA -
         * `@Autowired(required = false)` lub `Optional<T>` jako typ
         * parametru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementValueAnnotationInConstructorParameter {
        /*
         * 🧪 Zadanie 14:
         * Polacz constructor injection z `@Value("${klucz}")` NA
         * PARAMETRZE konstruktora (nie tylko na polu) - zapowiedz
         * `Lesson16_PropertiesAndConfiguration`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareTestSetupComplexityAcrossInjectionStyles {
        /*
         * 🧪 Zadanie 15:
         * Napisz TEN SAM "test" (bez frameworka) dla klasy z constructor
         * injection I dla klasy z field injection (z Lesson04) - porownaj
         * ILOSC kodu potrzebnego do przygotowania testu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementConstructorValidatingArgumentsEagerly {
        /*
         * 🧪 Zadanie 16:
         * Dodaj `Objects.requireNonNull(...)` w konstruktorze dla KAZDEJ
         * zaleznosci - zweryfikuj, ze `null` przekazany recznie od razu
         * rzuca czytelny wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementRecordStyleImmutableServiceClass {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj serwis jako `record` (nie zwykla klase) z
         * zaleznosciami jako komponentami rekordu - zweryfikuj, ze
         * Spring MOZE go tez zarzadzac jako bean (JEDEN "konstruktor"
         * kanoniczny rekordu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DemonstrateEarlyFailureVsLateFailure {
        /*
         * 🧪 Zadanie 18:
         * Porownaj MOMENT wykrycia braku zaleznosci - constructor
         * injection (od razu przy starcie kontekstu) vs field injection
         * (dopiero przy UZYCIU niezainicjalizowanego pola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementBuilderPatternAlternativeToLongConstructor {
        /*
         * 🧪 Zadanie 19:
         * Dla klasy z wieloma zaleznosciami, zaimplementuj wzorzec
         * Builder JAKO ALTERNATYWE - omow w komentarzu, kiedy Builder ma
         * sens ZAMIAST DI (podpowiedz: DTO/wartosci, nie beany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildComparisonOfConstructorPatternsAcrossCodebase {
        /*
         * 🧪 Zadanie 20:
         * Przejrzyj (recznie, bez terminala) 3 istniejace klasy z
         * WCZESNIEJSZYCH rozdzialow kursu (np. `_10_dao`) - czy uzywaja
         * wzorca constructor injection, mimo ze NIE byly pisane pod
         * Springa?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCircularConstructorDependencyAndObserveFailure {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj 2 klasy zalezne CYKLICZNIE od siebie przez
         * konstruktor - zaobserwuj `BeanCurrentlyInCreationException`
         * (zapowiedz `Lesson13_CircularDependencies`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementConstructorInjectionWithQualifierDisambiguation {
        /*
         * 🧪 Zadanie 22:
         * Majac 2 implementacje TEGO SAMEGO interfejsu, uzyj
         * `@Qualifier` NA PARAMETRZE konstruktora, zeby wskazac
         * WLASCIWA (zapowiedz `Lesson12_QualifierAndPrimary`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureReflectionOverheadOfConstructorInjection {
        /*
         * 🧪 Zadanie 23:
         * Zmierz roznice czasowa tworzenia 10000 obiektow przez `new`
         * (recznie) vs przez kontener Springa (refleksja) - jaki wniosek
         * dla wydajnosci startu duzej aplikacji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomConstructorResolutionLogic {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj WLASNY, prosty "resolver" (metoda) wybierajacy,
         * KTORY konstruktor klasy (z wielu dostepnych przez refleksje)
         * ma NAJWIECEJ parametrow mozliwych do zaspokojenia z danej Mapy
         * dostepnych "beanow".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDeepConstructorInjectionChainFiveLevels {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj lancuch 5 klas, kazda zalezna od NASTEPNEJ przez
         * konstruktor - zweryfikuj poprawne zbudowanie CALEGO lancucha
         * przez Springa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareConstructorInjectionWithFactoryMethodPattern {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj TA SAMA klase przez statyczna metode fabrykujaca
         * (`static of(...)`) ZAMIAST publicznego konstruktora - jak to
         * wspolgra z Springiem (`@Bean` woajace metode fabrykujaca)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementConstructorInjectionForAbstractBaseClass {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj klase ABSTRAKCYJNA z konstruktorem przyjmujacym
         * WSPOLNA zaleznosc, i 2 konkretne podklasy wolajace `super(...)` -
         * zweryfikuj dzialanie z kontenerem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnalyzeConstructorParameterNamesAtRuntime {
        /*
         * 🧪 Zadanie 28:
         * Uzyj refleksji (`Constructor.getParameters()`), zeby wypisac
         * NAZWY i TYPY parametrow konstruktora WLASNEJ klasy w runtime -
         * sprawdz, czy nazwy sa zachowane (flaga kompilatora `-parameters`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementSelfValidatingConstructorWithBusinessRules {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj konstruktor sprawdzajacy NIE TYLKO `null`, ale
         * REGULY BIZNESOWE (np. "zaleznosc A i B nie moga byc rownoczesnie
         * aktywne") - powiaz z `_17_architecture/Lesson15_
         * ValidationArchitecture`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteTestableServiceLayerCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna warstwe serwisowa (3+ klasy) WYLACZNIE z
         * constructor injection - napisz "testy" (bez frameworka) dla
         * WSZYSTKICH klas z podstawionymi ("fake") zaleznosciami, bez
         * uruchamiania ani jednego ApplicationContext.
         */
        public static void main(String[] args) { }
    }
}
