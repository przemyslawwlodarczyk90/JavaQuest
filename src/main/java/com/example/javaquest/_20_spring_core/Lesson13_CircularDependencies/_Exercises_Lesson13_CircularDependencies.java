package com.example.javaquest._20_spring_core.Lesson13_CircularDependencies;

public class _Exercises_Lesson13_CircularDependencies {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyCircularDependencyIsUsuallyDesignFlaw {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, dlaczego cykl A<->B jest ZWYKLE
         * sygnalem zlego podzialu odpowiedzialnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReproduceConstructorCycleFailure {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNE 2 klasy zalezne cyklicznie przez
         * konstruktor - zaobserwuj `BeanCurrentlyInCreationException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainChickenEggProblemInOwnWords {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij "problem jajka i kury" w kontekscie
         * cyklu przez constructor injection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReadFullStackTraceOfCircularException {
        /*
         * 🧪 Zadanie 4:
         * Wypisz PELNY stack trace wyjatku z Zadania 2 - znajdz w nim
         * fragment WSKAZUJACY dokladnie na cykl (nazwy obu beanow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareFieldVsConstructorCycleBehaviorSideBySide {
        /*
         * 🧪 Zadanie 5:
         * Uruchom TA SAMA "logiczna" pare klas RAZ z field injection
         * (dziala), RAZ z constructor injection (nie dziala) - porownaj
         * w JEDNYM wydruku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ManuallySetAllowCircularReferencesFalse {
        /*
         * 🧪 Zadanie 6:
         * Odtworz demo z teorii (`setAllowCircularReferences(false)`) dla
         * WLASNEJ pary klas z field injection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainSpringBoot26DefaultChange {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, co DOKLADNIE zmienil Spring Boot 2.6 w
         * domyslnym zachowaniu wobec cykli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListThreeWaysToBreakACycle {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wymien 3 sposoby PRAWDZIWEGO rozwiazania cyklu
         * (nie "obejscia" go).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExtractSharedDependencyToBreakCycle {
        /*
         * 🧪 Zadanie 9:
         * Napraw cykl z Zadania 2, wydzielajac WSPOLNA logike do 3.
         * klasy, od ktorej zalezy KAZDA z pierwotnych 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhySetterInjectionAlsoAllowsCircularity {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego setter injection (jak field
         * injection) TEZ pozwala na cykl - co je LACZY, a co ROZNI od
         * konstruktora?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementThreeClassCycleWithConstructorInjection {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj cykl 3 klas (A->B->C->A) przez constructor injection -
         * zweryfikuj, ze TEZ zawodzi (cykl NIE MUSI byc miedzy tylko 2
         * klasami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ResolveCycleWithSetterInjectionAsPartialFix {
        /*
         * 🧪 Zadanie 12:
         * Zmien JEDNA z 2 klas w cyklu na setter injection (druga zostaje
         * constructor) - zweryfikuj, czy TO rozwiazuje problem (i
         * DLACZEGO/DLACZEGO NIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ResolveCycleWithApplicationEventsPreview {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj rozwiazanie cyklu PRZEZ zdarzenia (zamiast
         * bezposredniej zaleznosci) - zapowiedz `Lesson20_
         * ApplicationEvents`, powiaz z `_17_architecture/Lesson18`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementLazyResolutionToBreakCycleWithoutRedesign {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `@Lazy` na JEDNYM z pol/parametrow w cyklu - zweryfikuj,
         * ze kontekst WYSTARTOWAL (ale wyjasnij w komentarzu, DLACZEGO to
         * "lekarstwo na objaw", nie na PRZYCZYNE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareLazyFixWithProperRedesign {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: porownaj rozwiazanie przez `@Lazy` (Zadanie 14)
         * z prawdziwym przeprojektowaniem (Zadanie 9) - jakie DLUGOTERMINOWE
         * ryzyko niesie `@Lazy`?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementObjectProviderToDeferResolution {
        /*
         * 🧪 Zadanie 16:
         * Uzyj `ObjectProvider<T>` (zamiast bezposredniej zaleznosci) w
         * JEDNYM z beanow cyklu - sprawdz, czy to rozwiazuje problem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DetectCycleBeforeContextStartupWithStaticAnalysis {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj METODE (bez uruchamiania Springa) analizujaca
         * Mape "kto-zalezy-od-kogo" i WYKRYWAJACA cykl PRZED probą
         * startu (DFS/BFS na grafie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareBeanCurrentlyInCreationExceptionAcrossScenarios {
        /*
         * 🧪 Zadanie 18:
         * Wywolaj `BeanCurrentlyInCreationException` w 2 ROZNYCH
         * scenariuszach (cykl konstruktorowy, recznie wywolany `getBean()`
         * w trakcie tworzenia INNEGO beana) - porownaj komunikaty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementDependencyGraphVisualizerHighlightingCycles {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj wizualizator grafu zaleznosci (podobny do zadan z
         * `Lesson06`) o PODSWIETLANIE (w tekscie) wykrytych cykli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildComparisonTableOfCycleResolutionStrategies {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele strategii rozwiazywania
         * cyklu - kolumny: strategia, czy naprawia PRZYCZYNE?, ryzyko.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomCircularDependencyDetectorBeanPostProcessor {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `BeanPostProcessor`/`BeanFactoryPostProcessor`
         * wykrywajacy cykle PRZED faktycznym tworzeniem beanow (analiza
         * metadanych, nie prob-i-bledow runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateThreeLevelCacheMechanismManually {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNY, uproszczony "3-poziomowy cache" (jak
         * wewnetrzny mechanizm Springa dla field injection) - Mapa
         * singletonow gotowych, Mapa "wczesnych" referencji, Mapa
         * fabryk - zademonstruj rozwiazanie cyklu field-injection-podobnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareSpringBehaviorWithAndWithoutSpringApplicationWrapper {
        /*
         * 🧪 Zadanie 23:
         * Zweryfikuj (na podstawie dokumentacji, bez pelnego Spring
         * Boota - ktorego ten rozdzial jeszcze nie ma) RÓZNICE zachowania
         * `AnnotationConfigApplicationContext` vs `SpringApplication.run()`
         * wobec `allowCircularReferences`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementGracefulCycleErrorReportingForDevelopers {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj "opakowanie" (try-catch wokol tworzenia kontekstu)
         * generujace CZYTELNY, przyjazny komunikat bledu (z nazwami
         * KONKRETNYCH klas w cyklu) zamiast surowego stack trace'u Springa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementLargeScaleDependencyGraphWithOneHiddenCycle {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj graf 15+ klas (WIEKSZOSC bez cykli) z JEDNYM ukrytym,
         * DLUGIM cyklem (5+ klas) - zweryfikuj, ze algorytm z Zadania 17
         * go ZNAJDUJE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareCostOfLazyResolutionAtRuntime {
        /*
         * 🧪 Zadanie 26:
         * Zmierz narzut wydajnosciowy `@Lazy`/`ObjectProvider` (proxy,
         * odroczone rozwiazanie) w porownaniu do zwyklego constructor
         * injection - ile to "kosztuje" przy KAZDYM wywolaniu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRefactoringToolSuggestingCycleBreakPoints {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj metode analizujaca graf zaleznosci i SUGERUJACA
         * (heurystycznie), KTORA krawedz cyklu najlepiej "przeciac"
         * (np. ta z NAJMNIEJSZA liczba wywolan metod miedzy klasami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementEventBasedDecouplingForRealCycleScenario {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj PELNY, realistyczny scenariusz (np. `OrderService` i
         * `InventoryService` "potrzebujace siebie nawzajem") i NAPRAW go
         * przez zdarzenia (Zadanie 13), z dzialajacym demo PRZED i PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DocumentArchitecturalRuleAgainstCycles {
        /*
         * 🧪 Zadanie 29:
         * Napisz (jako String/tekst) fragment "wytycznych architektonicznych"
         * zespolu - zasada zabraniajaca cykli miedzy modulami, powiazana
         * z `_17_architecture/Lesson10_DependencyDirection`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteCycleDetectionAndResolutionCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne narzedzie: wykrywanie cyklu w grafie
         * (statycznie, Zadanie 17) + sugestie naprawy (Zadanie 27) +
         * demo NAPRAWIONEGO systemu (zdarzenia, Zadanie 28) - jeden
         * spojny raport "od problemu do rozwiazania".
         */
        public static void main(String[] args) { }
    }
}
