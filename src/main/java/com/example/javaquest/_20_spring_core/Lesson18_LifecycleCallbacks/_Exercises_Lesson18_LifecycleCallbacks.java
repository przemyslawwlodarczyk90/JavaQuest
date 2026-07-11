package com.example.javaquest._20_spring_core.Lesson18_LifecycleCallbacks;

public class _Exercises_Lesson18_LifecycleCallbacks {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreeLifecycleMechanisms {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien i krotko opisz 3 mechanizmy callbackow
         * cyklu zycia z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnPostConstructPreDestroy {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA klase z `@PostConstruct`/`@PreDestroy`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyPostConstructRunsAfterConstructorAndInjection {
        /*
         * 🧪 Zadanie 3:
         * Dodaj konstruktor Z zaleznoscia DO klasy z Zadania 2 -
         * zweryfikuj, ze `@PostConstruct` widzi JUZ wstrzykniete pole.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementInitializingBeanInterface {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj WLASNA klase przez `InitializingBean` (starszy
         * styl).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementInitMethodForExternalStyleClass {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj WLASNA "zewnetrzna" klase z `initMethod`/
         * `destroyMethod` na `@Bean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyJakartaAnnotationsAreRecommended {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, dlaczego `@PostConstruct`/`@PreDestroy`
         * sa REKOMENDOWANE ponad `InitializingBean`/`DisposableBean`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyPreDestroyDoesNotRunOnPrototypeBeans {
        /*
         * 🧪 Zadanie 7:
         * Oznacz bean z `@PreDestroy` jako `prototype` (Lesson14) -
         * zweryfikuj, ze `@PreDestroy` NIE jest wywolywane przy
         * zamknieciu kontekstu (kontener NIE zarzadza destrukcja
         * prototype'ow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementMultiplePostConstructMethodsError {
        /*
         * 🧪 Zadanie 8:
         * Sprobuj dodac `@PostConstruct` do DWOCH metod TEJ SAMEJ klasy -
         * zaobserwuj zachowanie (czy OBIE sie wywoluja, czy blad?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareCallbackTimingWithConstructorLogging {
        /*
         * 🧪 Zadanie 9:
         * Dodaj `System.out.println` w KONSTRUKTORZE i w
         * `@PostConstruct` TEJ SAMEJ klasy - zweryfikuj KOLEJNOSC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyPrototypeBeansAreNotDestroyedByContainer {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij (powiazujac z `Lesson14_BeanScopes`),
         * dlaczego kontener NIE MOZE zarzadzac zniszczeniem beanow
         * `prototype`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ReproduceOrderingDemoWithAllThreeMechanisms {
        /*
         * 🧪 Zadanie 11:
         * Odtworz demo kolejnosci z teorii (WSZYSTKIE 3 mechanizmy naraz)
         * dla WLASNEJ klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementResourceCleanupPatternWithTryFinally {
        /*
         * 🧪 Zadanie 12:
         * Porownaj `@PreDestroy` z recznym `try-finally`/try-with-resources
         * (poza Springiem) - kiedy `@PreDestroy` NIE zostanie wywolane
         * (np. `kill -9`, awaria JVM)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementGracefulShutdownHookUsingPreDestroy {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj bean symulujacy "graceful shutdown" (np.
         * dokonczenie w toku operacji) w `@PreDestroy`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareContextCloseWithJvmShutdownHook {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: wyjasnij roznice miedzy `context.close()`
         * (jawne) a `context.registerShutdownHook()` (automatyczne przy
         * zamknieciu JVM) - kiedy uzyc ktorego?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementLazyInitializedBeanLifecycleTiming {
        /*
         * 🧪 Zadanie 15:
         * Oznacz bean `@Lazy` (Lesson06/09 zapowiadaly) - zweryfikuj, ze
         * `@PostConstruct` uruchamia sie DOPIERO przy pierwszym uzyciu,
         * NIE przy starcie kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementExceptionInPostConstructAndObserveContextFailure {
        /*
         * 🧪 Zadanie 16:
         * Rzuc wyjatek WEWNATRZ `@PostConstruct` - zaobserwuj, czy caly
         * kontekst PRZESTAJE sie uruchamiac (fail-fast).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDependentResourceInitializationOrder {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj 2 beany zalezne od siebie (A wstrzykniety do B) -
         * zweryfikuj, ze `@PostConstruct` A wykonuje sie PRZED
         * `@PostConstruct` B (zaleznosci sa inicjalizowane NAJPIERW).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConnectionPoolWarmupPattern {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj realistyczny wzorzec "rozgrzania" puli zasobow w
         * `@PostConstruct` (np. utworz 5 "polaczen" z wyprzedzeniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareDestroyMethodInferenceForCloseableClasses {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: sprawdz, czy Spring potrafi AUTOMATYCZNIE
         * wykryc metode `close()`/`shutdown()` jako `destroyMethod` (bez
         * jawnego podania) - dla klas implementujacych `AutoCloseable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildLifecycleEventTimelineReport {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (przez wspoldzielona liste + timestampy) pelna oS czasu
         * zdarzen cyklu zycia dla 3 wspolzaleznych beanow.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomSmartLifecycleBean {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj interfejs `SmartLifecycle` (zaawansowany
         * mechanizm start/stop, POZA @PostConstruct) - wyjasnij w
         * komentarzu, czym rozni sie od `@PostConstruct`/`@PreDestroy`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementPhaseOrderingWithSmartLifecycle {
        /*
         * 🧪 Zadanie 22:
         * Uzyj `getPhase()` z `SmartLifecycle`, zeby kontrolowac
         * KOLEJNOSC uruchamiania WIELU komponentow (nizsza faza startuje
         * pierwsza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCircularInitializationDependencyDetection {
        /*
         * 🧪 Zadanie 23:
         * Zbadaj (eksperymentalnie), co sie dzieje, gdy `@PostConstruct`
         * JEDNEGO beana probuje uzyc INNEGO beana, ktorego `@PostConstruct`
         * JESZCZE sie nie wykonal (kolejnosc inicjalizacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementAsyncPostConstructWithExecutorService {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj `@PostConstruct` uruchamiajacy DLUGOTRWALA
         * operacje NA OSOBNYM watku (`_05_multithreading`) - zeby NIE
         * blokowac startu CALEGO kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureStartupTimeImpactOfHeavyPostConstruct {
        /*
         * 🧪 Zadanie 25:
         * Zmierz wplyw "ciezkiego" `@PostConstruct` (np. sleep 500ms) NA
         * calkowity czas startu kontekstu z 10 takimi beanami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementHealthCheckBasedOnLifecycleState {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj prosty "health check" - flaga `ready` ustawiana
         * NA KONIEC `@PostConstruct`, sprawdzana PRZED uzyciem beana -
         * zapowiedz `_21_spring_boot/Lesson12_SpringBootActuator`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareDestroyOrderWithCreationOrderReversal {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj 3 zalezne beany (A<-B<-C) - zweryfikuj, ze
         * kolejnosc NISZCZENIA jest ODWROTNA do kolejnosci TWORZENIA
         * (C, potem B, potem A).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRetryLogicInPostConstructForFlakyResource {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj `@PostConstruct` z LOGIKA ponawiania (retry) dla
         * "niestabilnego" zasobu (symulowanego losowym niepowodzeniem) -
         * powiaz z `_16_clean_code`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementContextRefreshedEventVsPostConstructComparison {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (lub eksperymentalnie): porownaj `@PostConstruct`
         * (per-bean) z nasluchiwaniem `ContextRefreshedEvent` (zapowiedz
         * `Lesson20_ApplicationEvents`) - kiedy CALY kontekst jest JUZ
         * gotowy, w odroznieniu od POJEDYNCZEGO beana?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteResourceManagementCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system zarzadzania zasobami (pula polaczen,
         * cache, logger) z PELNYM cyklem zycia (inicjalizacja z retry,
         * health check, graceful shutdown w odwrotnej kolejnosci) -
         * zweryfikuj CALY przebieg od startu do zamkniecia.
         */
        public static void main(String[] args) { }
    }
}
