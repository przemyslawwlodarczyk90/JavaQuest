package com.example.javaquest._20_spring_core.Lesson03_InversionOfControl;

public class _Exercises_Lesson03_InversionOfControl {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainHollywoodPrincipleInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami "zasade Hollywood"
         * ("don't call us, we'll call you") w kontekscie IoC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DistinguishIocFromDi {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij, dlaczego IoC i DI to NIE synonimy -
         * jaka jest relacja miedzy nimi?
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteTraditionalControlFlowExample {
        /*
         * 🧪 Zadanie 3:
         * Napisz WLASNY przyklad tradycyjnego przeplywu sterowania (BEZ
         * IoC) - klasa, ktora SAMA decyduje o calej sekwencji dzialan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementTemplateMethodForDifferentDomain {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj Template Method dla INNEJ dziedziny niz raporty
         * (np. "przetwarzanie zamowienia": walidacja->obliczenie->
         * zapis, z 2 punktami rozszerzenia dla podklas).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddSecondSubclassToTemplateMethod {
        /*
         * 🧪 Zadanie 5:
         * Dodaj DRUGA podklase `ReportTemplate` (np. `JsonReportTemplate`)
         * z INNA implementacja `readData()`/`formatReport()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyGenerateMethodIsFinal {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, dlaczego metoda `generate()` w
         * `ReportTemplate` jest oznaczona `final` - co by sie stalo, gdyby
         * nie byla?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementServiceLocatorForDifferentService {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj WLASNY `ServiceLocator` dla INNEJ uslugi (np.
         * `NotificationSender`) - zarejestruj i pobierz implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TriggerServiceLocatorMissingRegistrationError {
        /*
         * 🧪 Zadanie 8:
         * Sprobuj pobrac z `ServiceLocator` usluge, ktora NIE zostala
         * zarejestrowana - zaobserwuj i zapisz wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListRealWorldFrameworksUsingIoc {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wymien co najmniej 2 mechanizmy z WCZESNIEJSZYCH
         * rozdzialow tego kursu, ktore TEZ sa przykladem IoC (podpowiedz:
         * `_07_servlets` - kto wywoluje `doGet()`?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareControlFlowDiagramsBeforeAfter {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: narysuj (jako tekst/ASCII) diagram strzalek
         * wywolan DLA obu wersji z teorii - tradycyjnej i przez Template
         * Method - zaznacz, GDZIE odwraca sie kontrola.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementThreeLevelTemplateMethodHierarchy {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj Template Method o POSREDNIA klase abstrakcyjna (3
         * poziomy dziedziczenia) - zachowaj, ze TYLKO najwyzszy poziom
         * ma `final generate()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareServiceLocatorWithStaticSingleton {
        /*
         * 🧪 Zadanie 12:
         * Bez terminala: porownaj `ServiceLocator` z klasycznym wzorcem
         * Singleton - czym sie roznia, mimo ze OBA uzywaja statycznego
         * pola?
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCallbackBasedInversionOfControl {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj IoC przez CALLBACK (interfejs funkcyjny
         * przekazywany jako parametr metody) - metoda "frameworkowa"
         * wywoluje Twoj callback w OKRESLONYM momencie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementObserverPatternAsIocExample {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj prosty wzorzec Observer (lista listenerow +
         * powiadamianie) - wyjasnij w komentarzu, DLACZEGO to TEZ
         * przyklad IoC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorServiceLocatorToConstructorInjection {
        /*
         * 🧪 Zadanie 15:
         * Przepisz `CheckoutService` z demo teorii, zeby NIE uzywal
         * `ServiceLocator`, tylko dostawal `PaymentGateway` przez
         * KONSTRUKTOR (zapowiedz DI z Lesson04) - porownaj czytelnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainHiddenDependencyProblemInServiceLocator {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wyjasnij, dlaczego `ServiceLocator.get(...)`
         * wewnatrz metody to "ukryta zaleznosc" - czym to gorsze od
         * zaleznosci widocznej w konstruktorze?
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementPluginStyleIocWithMultipleImplementations {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj system "pluginow" - interfejs + 3 implementacje +
         * "framework" iterujacy po WSZYSTKICH i wywolujacy je po kolei
         * (bez wiedzy CO konkretnie robia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureCouplingBeforeAfterIoc {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: policz "twarde" zaleznosci (importy konkretnych
         * klas) w wersji tradycyjnej vs Template Method vs Service
         * Locator - ktora ma ich NAJMNIEJ?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementTestableVersionUsingTemplateMethod {
        /*
         * 🧪 Zadanie 19:
         * Wykorzystaj Template Method, zeby stworzyc WERSJE TESTOWA
         * (podklasa z "podstawionymi" `readData()`/`formatReport()`
         * zwracajacymi ustalone dane) - wyjasnij korzysc dla testowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareIocTechniquesInTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza 3 technik IoC
         * z teorii - kolumny: nazwa, stopien inwersji (1-5), typowe
         * zastosowanie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementEventDrivenIocMiniFramework {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj mini "framework" zdarzeniowy (rejestracja listenerow +
         * `publish(event)`) - kod UZYTKOWNIKA rejestruje sie, ale to
         * FRAMEWORK decyduje, KIEDY go wywolac (zapowiedz `Lesson20_
         * ApplicationEvents`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildMiniDependencyContainerFromScratch {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj WLASNY, minimalny "kontener" (Mapa `Class -> Supplier`)
         * ktory PRZY starcie tworzy WSZYSTKIE zarejestrowane obiekty i
         * WIAZE je automatycznie na podstawie typu parametrow konstruktora
         * (uzyj refleksji) - to jest jadro tego, co robi Spring.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareIocWithAndWithoutFrameworkOverhead {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: przedyskutuj koszt (zlozonosc, "magia", trudnosc
         * debugowania) wprowadzenia frameworka realizujacego IoC (jak
         * Spring) vs reczne stosowanie tych samych wzorcow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementChainOfResponsibilityAsIocVariant {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj wzorzec Chain of Responsibility (lancuch
         * handlerow) - wyjasnij w komentarzu ZWIAZEK z IoC i podobienstwo
         * do Filter Chain z `_07_servlets/Lesson14`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ExtendMiniContainerWithLifecycleHooks {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj mini-kontener z Zadania 22 o wywolywanie metody
         * `init()` (jesli obiekt ja ma) PO utworzeniu wszystkich obiektow -
         * zapowiedz `Lesson18_LifecycleCallbacks`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareHollywoodPrincipleAcrossDifferentFrameworks {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wskaz "zasade Hollywood" w DWOCH innych miejscach
         * tego kursu (np. JUnit wywolujace metody testowe, Tomcat
         * wywolujacy `doGet()`) - opisz KTO tam jest "frameworkiem".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementFactoryBasedPartialIocAlternative {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj wzorzec Factory Method jako JESZCZE INNY, czesciowy
         * przyklad IoC - porownaj stopien inwersji z Service Locatorem i
         * Template Method.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildDecouplingScoreCalculator {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj metode oceniajaca "stopien odsprzezenia" (decoupling
         * score) danej klasy na podstawie prostych heurystyk (liczba
         * bezposrednich `new`, liczba zaleznosci w konstruktorze) -
         * zastosuj do przykladow z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignHybridIocSystemCombiningTechniques {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj (i zaimplementuj) system LACZACY Template Method
         * (stala kolejnosc krokow) z DI (zaleznosci przekazane przez
         * konstruktor, NIE Service Locator) - pokaz, ze techniki IoC MOGA
         * wspolistniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteIocPatternsShowcase {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo pokazujace WSZYSTKIE 4 techniki z tej
         * lekcji (tradycyjny kod, Template Method, Service Locator,
         * callback/Observer) rozwiazujace TEN SAM problem biznesowy -
         * z podsumowaniem roznic w komentarzu.
         */
        public static void main(String[] args) { }
    }
}
