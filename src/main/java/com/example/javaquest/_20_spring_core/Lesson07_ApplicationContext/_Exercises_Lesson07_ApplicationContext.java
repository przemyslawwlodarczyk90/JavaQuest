package com.example.javaquest._20_spring_core.Lesson07_ApplicationContext;

public class _Exercises_Lesson07_ApplicationContext {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainBeanFactoryVsApplicationContextRelationship {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij relacje miedzy `BeanFactory` a
         * `ApplicationContext` - ktory "dziedziczy" po ktorym?
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReproduceLazyVsEagerDemo {
        /*
         * 🧪 Zadanie 2:
         * Odtworz demo "leniwy vs chetny" z teorii dla WLASNEJ klasy z
         * printem w konstruktorze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListThreeExtraFeaturesOfApplicationContext {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wymien 3 mozliwosci, ktore ma ApplicationContext,
         * a NIE ma goly BeanFactory.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PublishCustomEventAndListenForIt {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj WLASNY rekord-zdarzenie i `ApplicationListener` -
         * opublikuj zdarzenie przez `context.publishEvent(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddMessagesInThreeLanguages {
        /*
         * 🧪 Zadanie 5:
         * Rozbuduj `StaticMessageSource` o WIADOMOSC w 3 jezykach (np.
         * PL/EN/DE) - pobierz wszystkie 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LoadResourceThatDoesNotExist {
        /*
         * 🧪 Zadanie 6:
         * Zaladuj (przez `context.getResource(...)`) plik, KTORY NIE
         * ISTNIEJE - zweryfikuj `resource.exists()` zwraca `false`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhatRefreshMethodDoes {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij (na podstawie demo z teorii), co robi
         * metoda `refresh()` w ApplicationContext.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareGetBeanBehaviorAcrossBothContainers {
        /*
         * 🧪 Zadanie 8:
         * Wywolaj `getBean()` DWUKROTNIE na tym samym typie - zarowno na
         * `DefaultListableBeanFactory`, jak i na `ApplicationContext` -
         * porownaj, KIEDY pojawia sie print z konstruktora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyEventPojoDoesNotNeedApplicationEventBase {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego zdarzenie w tej lekcji jest
         * ZWYKLYM rekordem, a NIE dziedziczy po `ApplicationEvent` - od
         * ktorej wersji Springa to mozliwe?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRealWorldUsagesOfEachExtraFeature {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: dla kazdej z 3 dodatkowych mozliwosci
         * ApplicationContext (eventy/i18n/zasoby) podaj REALNY scenariusz
         * uzycia w aplikacji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementMultipleListenersForSameEvent {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj DWA rozne `ApplicationListener` dla TEGO SAMEGO
         * typu zdarzenia - zweryfikuj, ze OBA sa wywolywane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementListenerForMultipleEventTypes {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `ApplicationListener<ApplicationEvent>` (lub
         * `Object`), ktory reaguje na WIELE typow zdarzen - rozroznij je
         * przez `instanceof`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementMessageSourceWithArgumentsAndDefaultLocale {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `getMessage(String, Object[], Locale)` z BRAKUJACYM
         * tlumaczeniem dla danego locale - zweryfikuj zachowanie
         * (wyjatek czy fallback?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementResourceLoaderForClasspathAndFileSystem {
        /*
         * 🧪 Zadanie 14:
         * Zaladuj zasob przez `classpath:` ORAZ przez `file:` (rozny
         * prefiks) - porownaj zachowanie `ResourceLoader`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureTimeDifferenceLazyVsEagerCreation {
        /*
         * 🧪 Zadanie 15:
         * Zarejestruj 20 beanow z printem w konstruktorze - zmierz, KIEDY
         * (w jakim momencie wzgledem calego programu) powstaja w
         * BeanFactory vs ApplicationContext.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCustomApplicationEventWithExtraData {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj zdarzenie z WIELOMA polami (np. `UserRegisteredEvent`
         * z email/data/zrodlem rejestracji) - odczytaj WSZYSTKIE pola w
         * listenerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareStaticMessageSourceWithResourceBundleMessageSource {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj `StaticMessageSource` (w pamieci, jak w
         * teorii) z `ResourceBundleMessageSource` (pliki `.properties`) -
         * kiedy uzyc ktorego?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConditionalEventPublishingBasedOnBusinessLogic {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj metode biznesowa, ktora publikuje zdarzenie TYLKO
         * gdy spelniony jest warunek (np. kwota zamowienia > 1000) -
         * zweryfikuj OBA przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExploreApplicationContextIdAndDisplayName {
        /*
         * 🧪 Zadanie 19:
         * Wypisz `context.getId()` i `context.getDisplayName()` dla
         * kilku roznych sposobow utworzenia kontekstu - co sie rozni?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildComparisonTableBeanFactoryVsApplicationContext {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza BeanFactory vs
         * ApplicationContext - kolumny: mozliwosc, obecna w BeanFactory?,
         * obecna w ApplicationContext?.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementApplicationContextAwareBeanInspectingItself {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj bean implementujacy `ApplicationContextAware` -
         * wypisz w `setApplicationContext(...)` liczbe WSZYSTKICH beanow
         * widocznych z jego perspektywy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementHierarchicalApplicationContexts {
        /*
         * 🧪 Zadanie 22:
         * Utworz DWA konteksty, gdzie DRUGI ma PIERWSZY jako "parenta"
         * (`setParent(...)`) - zweryfikuj, ze bean z rodzica jest
         * WIDOCZNY w dziecku, ale NIE odwrotnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomEventWithVetoableListener {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj mechanizm "zdarzenia z mozliwoscia zawetowania" -
         * pierwszy listener ustawia flage w mutowalnym obiekcie zdarzenia,
         * drugi listener sprawdza ja PRZED wykonaniem akcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureMemoryImpactOfEagerInitializationAtScale {
        /*
         * 🧪 Zadanie 24:
         * Zarejestruj 500 "ciezkich" beanow (alokujacych spora tablice w
         * konstruktorze) - zmierz zuzycie pamieci PO starcie
         * ApplicationContext - jaki to ma wplyw na czas startu duzej
         * aplikacji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementLazyBeanInsideEagerApplicationContext {
        /*
         * 🧪 Zadanie 25:
         * Oznacz JEDEN bean `@Lazy` wewnatrz normalnie "chetnego"
         * ApplicationContext - zweryfikuj (printem w konstruktorze), ze
         * TYLKO on powstaje pozniej niz reszta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementAsyncEventListenerWithExecutor {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj listener zdarzen dzialajacy NA OSOBNYM watku
         * (`@Async`-podobna symulacja przez `ExecutorService`) - zweryfikuj,
         * ze glowny watek NIE czeka na jego zakonczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildCustomMessageSourceWithFallbackChain {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj `MessageSource` z LANCUCHEM fallbackow (najpierw
         * sprawdz jezyk uzytkownika, potem jezyk domyslny aplikacji,
         * potem angielski) - uzyj `setParentMessageSource(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementEventDrivenAuditLogUsingApplicationEvents {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj prosty dziennik audytu (powiaz z
         * `_19_security_basics/Lesson19`) subskrybujacy WSZYSTKIE
         * zdarzenia biznesowe publikowane w aplikacji - zapisz je do listy
         * w pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareEventDrivenWithDirectMethodCallCoupling {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj TA SAMA logike (np. wyslij powiadomienie po
         * zamowieniu) DWOMA sposobami - bezposrednie wywolanie metody vs
         * zdarzenie - porownaj sprzezenie (coupling) miedzy klasami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteApplicationContextShowcaseCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo laczace WSZYSTKIE mozliwosci
         * ApplicationContext z tej lekcji (eager singletons, eventy,
         * i18n, zasoby) w JEDNYM, spojnym scenariuszu biznesowym
         * (np. "system rejestracji zamowien z powiadomieniami").
         */
        public static void main(String[] args) { }
    }
}
