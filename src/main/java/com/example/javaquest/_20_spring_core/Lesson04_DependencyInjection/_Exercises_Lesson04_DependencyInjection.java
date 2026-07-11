package com.example.javaquest._20_spring_core.Lesson04_DependencyInjection;

public class _Exercises_Lesson04_DependencyInjection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDependencyInjectionInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, czym jest Dependency
         * Injection - podaj analogie spoza swiata programowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CompareDiWithServiceLocatorFromPreviousLesson {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij, czym DI rozni sie od Service Locatora
         * z `Lesson03` - ktora zaleznosc jest "ukryta", a ktora "widoczna"?
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementConstructorInjectionForNewDomain {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj wstrzykiwanie przez konstruktor dla WLASNEGO
         * przykladu (np. `InvoiceService` zalezny od `TaxCalculator`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementSetterInjectionForOptionalDependency {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj wstrzykiwanie przez setter dla ZALEZNOSCI
         * OPCJONALNEJ (np. opcjonalny `Logger` - dziala tez bez niego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementFieldInjectionManuallyWithReflection {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj RECZNE wstrzykiwanie do pola przez refleksje
         * (jak w teorii) dla WLASNEGO przykladu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TryToUseObjectBeforeSetterInjectionCompletes {
        /*
         * 🧪 Zadanie 6:
         * Sprobuj uzyc obiektu z Zadania 4 PRZED wywolaniem settera -
         * zaobserwuj i wyjasnij zachowanie (np. `NullPointerException`
         * lub logike domyslna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MakeConstructorInjectedFieldFinal {
        /*
         * 🧪 Zadanie 7:
         * Upewnij sie, ze pole wstrzykiwane przez konstruktor w Zadaniu 3
         * jest `final` - sprobuj (i pokaz blad kompilacji w komentarzu)
         * przypisac je gdziekolwiek indziej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareCodeLengthAcrossThreeStyles {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj TA SAMA klase (2 zaleznosci) WSZYSTKIMI 3
         * stylami DI - policz linie kodu potrzebne w kazdym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyFieldInjectionNeedsReflection {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, DLACZEGO wstrzykiwanie do pola WYMAGA
         * refleksji (`setAccessible(true)`), a konstruktor/setter - NIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWhichInjectionTypeSpringPrefers {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: na podstawie tej lekcji, zgadnij (zanim
         * przeczytasz Lesson10/11), ktory styl DI Spring REKOMENDUJE -
         * uzasadnij swoja odpowiedz.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementClassWithTwoConstructorDependencies {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj klase z DWOMA zaleznosciami wstrzykiwanymi przez
         * konstruktor - zweryfikuj, ze OBIE sa wymagane do utworzenia
         * obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementMixedInjectionRequiredAndOptional {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj klase MIESZAJACA style - WYMAGANA zaleznosc przez
         * konstruktor, OPCJONALNA przez setter - uzasadnij ten wybor w
         * komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementFieldInjectionWithoutReflectionAccessException {
        /*
         * 🧪 Zadanie 13:
         * Usun `setAccessible(true)` z Zadania 5 - zaobserwuj i zapisz
         * DOKLADNY wyjatek (`IllegalAccessException`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildManualDiContainerForConstructorInjection {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj PROSTY "kontener" (metoda generyczna), ktora tworzy
         * obiekt przez refleksje, ROZPOZNAJAC automatycznie zaleznosci z
         * PARAMETROW konstruktora (podstawa tego, co robi Spring).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareTestabilityOfThreeInjectionStyles {
        /*
         * 🧪 Zadanie 15:
         * Napisz "test" (zwykla metoda `main`) dla KLAS z Zadania 8 -
         * ktory styl jest NAJLATWIEJSZY do przetestowania z "fałszywą"
         * (mock) zaleznoscia, bez uzycia frameworka testowego?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementImmutableClassWithConstructorInjection {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj W PELNI niezmienna klase (WSZYSTKIE pola `final`)
         * z 3 zaleznosciami wstrzykiwanymi przez konstruktor - powiaz z
         * `_14_advancedjava/Lesson24_Immutability`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DemonstrateSetterInjectionAllowsReconfiguration {
        /*
         * 🧪 Zadanie 17:
         * Pokaz, ze wstrzykiwanie przez setter pozwala PODMIENIC
         * zaleznosc PO utworzeniu obiektu (wywolaj setter DWUKROTNIE z
         * roznymi implementacjami) - czy konstruktor na to pozwala?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementFieldInjectionForMultipleFields {
        /*
         * 🧪 Zadanie 18:
         * Rozbuduj RECZNA symulacje wstrzykiwania do pola (Zadanie 5) o
         * OBSLUGE wielu pol NARAZ (iteracja po `getDeclaredFields()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainNullPointerRiskInEachInjectionStyle {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: dla KAZDEGO z 3 stylow DI, opisz SCENARIUSZ, w
         * ktorym mozna dostac `NullPointerException` z powodu
         * niekompletnej inicjalizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildComparisonTableOfInjectionStyles {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza 3 stylow DI -
         * kolumny: mozliwosc 'final', widocznosc zaleznosci, ryzyko NPE,
         * latwosc testowania.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildGenericReflectionBasedAutoWiringContainer {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj GENERYCZNY "auto-wiring" kontener (Mapa `Class<?> ->
         * Object`) ktory dla DOWOLNEJ zarejestrowanej klasy automatycznie
         * ROZWIAZUJE i wstrzykuje jej zaleznosci konstruktorowe (rekurencyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_HandleCircularDependencyInManualContainer {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj (w kontenerze z Zadania 21) probe polaczenia 2 klas
         * zaleznych CYKLICZNIE od siebie przez konstruktor - zaobserwuj
         * (np. `StackOverflowError`) i wyjasnij, dlaczego constructor
         * injection NIE MOZE tego "naprawic" (zapowiedz `Lesson13_
         * CircularDependencies`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementDependencyInjectionWithFactoryFallback {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj klase, ktora przyjmuje zaleznosc przez konstruktor,
         * ale ma TEZ DRUGI konstruktor bezargumentowy z sensowna wartoscia
         * DOMYSLNA - kiedy to dobra praktyka, a kiedy zła?
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasurePerformanceOfReflectionBasedInjection {
        /*
         * 🧪 Zadanie 24:
         * Zmierz roznice czasowa miedzy 10000 utworzeniami obiektu przez
         * `new` (konstruktor) a przez refleksje (`Constructor.newInstance`) -
         * jaki wniosek dla kontenerow takich jak Spring?
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementOptionalDependencyWithJavaOptional {
        /*
         * 🧪 Zadanie 25:
         * Przeprojektuj wstrzykiwanie OPCJONALNEJ zaleznosci (Zadanie 4)
         * przy uzyciu `Optional<T>` zamiast dopuszczania `null` - powiaz z
         * `_03_collections/Lesson` o Optionalu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementConstructorInjectionWithValidationInConstructor {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj konstruktor, ktory WALIDUJE wstrzykiwana zaleznosc
         * (`Objects.requireNonNull`) - wyjasnij, dlaczego to MOZLIWE
         * TYLKO przy constructor injection (nie setter/pole).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareDiWithPlainJavaBuilderPattern {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj TEN SAM obiekt (3 zaleznosci) przez wzorzec
         * Builder - porownaj z constructor injection - kiedy Builder ma
         * sens ZAMIAST DI?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDependencyGraphVisualizer {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj metode analizujaca (przez refleksje) graf
         * zaleznosci MIEDZY zarejestrowanymi klasami w kontenerze z
         * Zadania 21 - wypisz go jako tekstowe drzewo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementLazyDependencyInjectionWithSupplier {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj "leniwe" wstrzykiwanie - zaleznosc dostarczona
         * jako `Supplier<T>`, tworzona DOPIERO przy pierwszym uzyciu -
         * wyjasnij mozliwe zastosowanie (kosztowne zaleznosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMiniDiFrameworkWithAllThreeStyles {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, mini-framework DI obslugujacy WSZYSTKIE 3
         * style (rozpoznawane przez adnotacje: `@Inject` na konstruktorze/
         * setterze/polu, wlasnej roboty) - zademonstruj na przykladzie
         * z co najmniej 4 klasami.
         */
        public static void main(String[] args) { }
    }
}
