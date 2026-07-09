package com.example.javaquest._13_libraries.Lesson19_GuiceBasics;

public class _Exercises_Lesson19_GuiceBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InterfaceWithInjectConstructorImpl {
        /*
         * 🧪 Zadanie 1:
         * Utworz interfejs "Notifier" z metoda "void send(String message)" oraz
         * implementacje "EmailNotifier" (wypisuje "Email: " + message). Napisz
         * klase "AlertService" ktora w KONSTRUKTORZE oznaczonym adnotacja
         * "@Inject" przyjmuje "Notifier" i przypisuje go do pola "final".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FirstAbstractModule {
        /*
         * 🧪 Zadanie 2:
         * Napisz klase "NotifierModule extends AbstractModule" ktora w metodzie
         * "configure()" wiaze "bind(Notifier.class).to(EmailNotifier.class)"
         * (uzyj typow z Zadania 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateInjectorAndGetInstance {
        /*
         * 🧪 Zadanie 3:
         * W main() wywolaj "Guice.createInjector(new NotifierModule())"
         * (Zadanie 2), pobierz "AlertService" (Zadanie 1) przez
         * "injector.getInstance(AlertService.class)" i wywolaj na nim metode
         * wysylajaca alert.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SecondImplementationAndModule {
        /*
         * 🧪 Zadanie 4:
         * Dodaj druga implementacje "Notifier" - "SmsNotifier" (wypisuje
         * "SMS: " + message) - oraz drugi modul "SmsNotifierModule" wiazacy
         * "Notifier" na "SmsNotifier". Zbuduj DWA osobne Injectory (jeden na
         * kazdy modul) i pokaz, ze "AlertService" dziala z OBOMA, bez zmiany
         * swojego kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TwoDependenciesInOneConstructor {
        /*
         * 🧪 Zadanie 5:
         * Napisz interfejs "Logger" z metoda "void log(String text)" i
         * implementacje "ConsoleLogger". Napisz klase "TaskService" ktora w
         * konstruktorze z "@Inject" przyjmuje JEDNOCZESNIE "Notifier" ORAZ
         * "Logger". Napisz modul wiazacy OBIE zaleznosci i zbuduj TaskService
         * przez Injector.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FieldInjectionWithPrivateField {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase "ReportService" z PRYWATNYM polem "Logger logger"
         * oznaczonym "@Inject" (field injection, BEZ konstruktora ani settera).
         * Zbuduj ja przez Injector i wywolaj metode logujaca raport - udowodnij,
         * ze Guice wstrzyknal zaleznosc mimo prywatnosci pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SwapModuleAtRuntime {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode "buildAlertService(Module module)" przyjmujaca DOWOLNY
         * modul i zwracajaca "AlertService" zbudowany przez
         * "Guice.createInjector(module).getInstance(AlertService.class)".
         * Wywolaj ja RAZ z EmailModule, RAZ z SmsModule (Zadania 1-4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainAtInjectVsManualNew {
        /*
         * 🧪 Zadanie 8:
         * W komentarzu (min. 4 zdania) porownaj: "PaymentGateway gateway = new
         * CreditCardGateway(); OrderService s = new OrderService(gateway);"
         * (Lesson18) vs "injector.getInstance(OrderService.class)" (Lesson19) -
         * co dokladnie robi Guice "za kulisami" zamiast Ciebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ThreeLevelDependencyChain {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj lancuch 3 klas: "RepositoryService" (bez zaleznosci),
         * "BusinessService" (@Inject konstruktor zalezny od RepositoryService),
         * "ApiService" (@Inject konstruktor zalezny od BusinessService). Bez
         * ZADNEGO modulu (wszystkie klasy konkretne, nie interfejsy) pobierz
         * "ApiService" z Injectora - Guice zbuduje CALY lancuch automatycznie
         * (Just-In-Time bindings).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PrintInjectorBindingsExplanation {
        /*
         * 🧪 Zadanie 10:
         * Utworz Injector z modulem z Zadania 2. W komentarzu (nie w kodzie)
         * wypisz krok po kroku, co dzieje sie WEWNATRZ Guice, gdy wywolujesz
         * "injector.getInstance(AlertService.class)" - od odczytania @Inject
         * po zwrocenie gotowego obiektu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FourClassDependencyGraphViaGuice {
        /*
         * 🧪 Zadanie 11:
         * Przepisz na Guice graf z "Lesson18.Exercise21" (OrderService zalezny
         * od PaymentGateway, NotificationService zalezny od Notifier,
         * OrderController zalezny od OBU) - uzyj @Inject konstruktorow i
         * DWOCH modulow polaczonych w JEDNYM "Guice.createInjector(moduleA,
         * moduleB)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareManualDiVsGuiceLinesOfCode {
        /*
         * 🧪 Zadanie 12:
         * Dla grafu z Zadania 11: policz (w komentarzu) ile linii kodu
         * wymagaloby reczne polaczenie WSZYSTKICH obiektow w main() (jak w
         * Lesson18) vs ile linii wymaga to samo przez
         * "injector.getInstance(OrderController.class)". Wyciagnij wniosek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DecoratorPatternWithInjectConstructor {
        /*
         * 🧪 Zadanie 13:
         * Napisz "LoggingNotifier implements Notifier" ktory w KONSTRUKTORZE z
         * "@Inject" przyjmuje INNY "Notifier" (opakowywany, wzorzec Decorator z
         * Lesson18.Exercise23) i loguje PRZED/PO delegacji. Zwiaz w module
         * "Notifier" na "LoggingNotifier", a wewnatrz LoggingNotifier recznie
         * (przez inny modul lub @Named z Lesson20 - tu wystarczy prosty
         * przyklad z jedna implementacja "opakowywana" wpisana na sztywno w
         * osobnej klasie) pokaz, ze delegacja dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleGetInstanceCallsProduceDifferentObjects {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj Injector z modulem wiazacym "Notifier" na "EmailNotifier".
         * Wywolaj "injector.getInstance(Notifier.class)" DWA RAZY do dwoch
         * zmiennych i porownaj je operatorem "==" - wypisz PASS/FAIL sprawdzajac,
         * ze (bez zadnego scope'u) sa to DWIE ROZNE instancje (przygotowanie
         * do tematu scope'ow w Lesson20).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestableServiceWithFakeNotifierViaGuice {
        /*
         * 🧪 Zadanie 15:
         * Napisz "FakeNotifier implements Notifier" zapamietujacy liczbe wywolan
         * (jak w Lesson18.Exercise07). Napisz modul testowy wiazacy "Notifier"
         * na "FakeNotifier", zbuduj przez niego "AlertService" i zweryfikuj
         * (PASS/FAIL) liczbe wywolan po 3 alertach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorLesson18RegistryToGuiceModule {
        /*
         * 🧪 Zadanie 16:
         * Wroc do "Lesson18.Exercise19" (recznie zbudowana Map<String,
         * PaymentGateway>). Przepisz to samo wiazanie jako Guice "Module" z
         * "bind(PaymentGateway.class).to(CreditCardGateway.class)" - w
         * komentarzu opisz, co Guice zautomatyzowal w porownaniu do recznej mapy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ModuleThatBindsTwoInterfaces {
        /*
         * 🧪 Zadanie 17:
         * Napisz JEDEN modul "AppModule extends AbstractModule" ktorego metoda
         * "configure()" zawiera DWA wiazania (np. "PaymentGateway" ORAZ
         * "Notifier" jednoczesnie) - zbuduj przez niego klase zalezna od OBU
         * interfejsow naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_InjectorGetInstanceForConcreteClassNoModule {
        /*
         * 🧪 Zadanie 18:
         * Utworz Injector BEZ zadnego modulu: "Guice.createInjector()" (pusty).
         * Sprobuj pobrac przez niego klase KONKRETNA (nie interfejs) z
         * bezargumentowym konstruktorem - wypisz, ze to dziala (Just-In-Time
         * binding), a nastepnie sprobuj pobrac INTERFEJS bez wiazania - zlap
         * wyjatek "com.google.inject.ConfigurationException" i wypisz jego
         * komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ChainOfThreeModulesCombined {
        /*
         * 🧪 Zadanie 19:
         * Napisz TRZY osobne, malutkie moduly (kazdy wiaze inny interfejs -
         * np. Notifier, Logger, PaymentGateway) i polacz je WSZYSTKIE w
         * JEDNYM wywolaniu "Guice.createInjector(moduleA, moduleB, moduleC)".
         * Pobierz klase zalezna od WSZYSTKICH TRZECH interfejsow naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DiscussWhenNotToUseGuice {
        /*
         * 🧪 Zadanie 20:
         * W komentarzu (min. 5 zdan) opisz, w jakich sytuacjach uzycie frameworka
         * DI (jak Guice) MOZE byc przesada (np. bardzo mala aplikacja, skrypt
         * jednorazowy) - i dlaczego reczne DI z Lesson18 bywa wtedy prostsze i
         * czytelniejsze.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FiveClassGraphWithTwoModules {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj graf 5 klas: "PaymentGateway"+impl, "Notifier"+impl,
         * "OrderService" (zalezny od PaymentGateway), "NotificationService"
         * (zalezny od Notifier), "CheckoutController" (zalezny od OBU
         * powyzszych serwisow). Podziel wiazania na DWA moduly tematyczne
         * (PaymentModule, NotificationModule) i pobierz "CheckoutController"
         * jednym "getInstance(...)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CircuitBreakerDecoratorWithGuiceInject {
        /*
         * 🧪 Zadanie 22:
         * Przepisz "CircuitBreakerPaymentGateway" z Lesson18.Exercise25 tak, by
         * jego konstruktor mial adnotacje "@Inject" i przyjmowal "PaymentGateway"
         * (opakowywany) - zwiaz w module "PaymentGateway" na
         * "CircuitBreakerPaymentGateway" i zademonstruj otwarcie obwodu po 3
         * niepowodzeniach (jak w oryginalnym zadaniu), ale tym razem caly
         * lancuch budowany przez Injector.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompositeNotifierListInjectionAttempt {
        /*
         * 🧪 Zadanie 23:
         * Sprobuj przepisac "CompositeNotifier" z Lesson18.Exercise24 (konstruktor
         * z "List<Notifier> notifiers") na Guice, uzywajac zwyklego
         * "bind(Notifier.class).to(...)" - w komentarzu wyjasnij, dlaczego
         * zwykle "bind" NIE wystarcza, by wstrzyknac LISTE WIELU implementacji
         * tego samego interfejsu (zapowiedz multibindings z Lesson20).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_InjectorChildScenario {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj DWA niezalezne Injectory z RZNYMI modulami (np. jeden wiaze
         * CreditCardGateway, drugi PayPalGateway) - pobierz z KAZDEGO osobno
         * "OrderService" i wykonaj po jednym zamowieniu w kazdym. W komentarzu
         * potwierdz, ze oba Injectory dzialaja NIEZALEZNIE (osobne "grafy" wiazan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ManualContainerVsGuiceRefactor {
        /*
         * 🧪 Zadanie 25:
         * Wez "kontener DI" z Lesson18.Exercise26-27 (Map<Class<?>, Object>) i
         * przepisz DOKLADNIE TE SAMA funkcjonalnosc (rejestracja + odczyt
         * implementacji dla interfejsow Notifier i PaymentGateway) uzywajac
         * Guice Module + Injector. Porownaj (w komentarzu) ilosc kodu potrzebna
         * do tego samego efektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ErrorHandlingMissingBinding {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj interfejs "AuditLogger" (metoda "void audit(String event)")
         * BEZ zadnej implementacji wiazanej w zadnym module. Sprobuj pobrac go
         * przez Injector zbudowany z pustym/niepowiazanym modulem - zlap
         * wyjatek Guice i wypisz PRZYJAZNY komunikat bledu (np. "Brak wiazania
         * dla AuditLogger - dodaj bind(...) w module").
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RefactorFullCheckoutFlowToGuice {
        /*
         * 🧪 Zadanie 27:
         * Przepisz PELNY przeplyw checkout z Lesson18.Exercise29 (CartService ->
         * PricingService -> OrderService -> PaymentGateway + Notifier, min. 5
         * klas) tak, by KAZDA klasa miala konstruktor z "@Inject", a CALY graf
         * byl budowany przez "injector.getInstance(CartService.class)" -
         * ZERO recznego "new" w main() (poza samym tworzeniem Injectora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasureInjectorCreationOnce {
        /*
         * 🧪 Zadanie 28:
         * Utworz Injector JEDEN RAZ na poczatku main() i uzyj go do pobrania
         * TRZECH ROZNYCH klas (np. OrderService, NotificationService,
         * OrderController) - w komentarzu wyjasnij, dlaczego w realnej
         * aplikacji tworzy sie Injector TYLKO RAZ (przy starcie), a nie za
         * kazdym razem, gdy potrzebny jest jakis obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareIocPrincipleAcrossLesson18And19 {
        /*
         * 🧪 Zadanie 29:
         * W komentarzu (min. 6 zdan) zestaw OBA podejscia z tego i poprzedniego
         * rozdzialu: reczne DI (Lesson18: "kto tworzy obiekty" = MY, w main())
         * vs Guice (Lesson19: "kto tworzy obiekty" = FRAMEWORK, przez
         * refleksje na podstawie @Inject/Module) - podkresl, ze ZASADA IoC
         * (kto NIE decyduje, jakiej implementacji uzyc - klasa) jest identyczna
         * w OBU przypadkach, zmienia sie tylko MECHANIZM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneRefactorMonolithToGuice {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: wez "OrderProcessingService" z
         * Lesson18.Exercise30 (juz zrefaktoryzowana do 3 interfejsow
         * wstrzykiwanych przez konstruktor) i przepisz ja NA GUICE - dodaj
         * "@Inject" na konstruktorze, napisz "AppModule" wiazacy WSZYSTKIE 3
         * interfejsy na konkretne implementacje, pobierz gotowy
         * "OrderProcessingService" JEDNYM "getInstance(...)" i wykonaj pelny
         * scenariusz demo. W komentarzu podsumuj, co DOKLADNIE Guice zdjal z
         * Twoich barkow w porownaniu do wersji z Lesson18.
         */
        public static void main(String[] args) { }
    }
}
