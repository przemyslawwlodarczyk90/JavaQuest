package com.example.javaquest._13_libraries.Lesson18_WhyDependencyInjection;

public class _Exercises_Lesson18_WhyDependencyInjection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateNotifierInterfaceWithTwoImpls {
        /*
         * 🧪 Zadanie 1:
         * Utworz interfejs "Notifier" z metoda "void send(String message)" oraz
         * dwie implementacje: "EmailNotifier" (wypisuje "Email: " + message) i
         * "SmsNotifier" (wypisuje "SMS: " + message).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TightlyCoupledOrderProcessor {
        /*
         * 🧪 Zadanie 2:
         * Napisz klase "OrderProcessor" ktora WEWNATRZ SIEBIE tworzy pole
         * "private final Notifier notifier = new EmailNotifier();" (tight
         * coupling, uzyj interfejsu/klas z Zadania 1). Zawola "notifier.send(...)"
         * w metodzie "process(String order)". W komentarzu opisz DWA problemy
         * takiego podejscia (z teorii lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RefactorToConstructorInjection {
        /*
         * 🧪 Zadanie 3:
         * Zrefaktoryzuj "OrderProcessor" z Zadania 2 tak, by "Notifier" byl
         * przekazywany przez KONSTRUKTOR (final pole) zamiast tworzony przez
         * "new" wewnatrz klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SwapImplementationsWithoutModifyingClass {
        /*
         * 🧪 Zadanie 4:
         * Uzywajac "OrderProcessor" z Zadania 3: utworz DWIE instancje - jedna z
         * "EmailNotifier", druga z "SmsNotifier" - i wywolaj "process(...)" na
         * obu, BEZ zadnej zmiany w kodzie klasy OrderProcessor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SetterInjectionWithDefault {
        /*
         * 🧪 Zadanie 5:
         * Napisz klase "Logger" (interfejs) z implementacja "ConsoleLogger"
         * (domyslna). Napisz klase "TaskRunner" ktora ma pole "Logger logger"
         * zainicjalizowane domyslnie na "new ConsoleLogger()" ORAZ metode
         * "setLogger(Logger logger)" do podmiany (setter injection, jak w lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FieldInjectionExample {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase "ReportPrinter" z polem PAKIETOWYM (bez modyfikatora)
         * "Notifier notifier" (bez konstruktora ani settera, field injection jak
         * w lekcji) - ustaw je recznie z main() i wywolaj metode drukujaca raport
         * przez notifier. W komentarzu wypisz WADE tego podejscia (brak "final").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FakeNotifierForTesting {
        /*
         * 🧪 Zadanie 7:
         * Napisz "FakeNotifier implements Notifier" ktory ZAPAMIETUJE liczbe
         * wywolan "send(...)" (pole int callCount) zamiast cokolwiek wypisywac.
         * Wstrzyknij go do "OrderProcessor" (Zadanie 3) i po wywolaniu
         * "process(...)" 3 razy zweryfikuj (println PASS/FAIL), ze callCount == 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareInjectionTypesForScenario {
        /*
         * 🧪 Zadanie 8:
         * Dla klasy "PaymentService" ktora WYMAGA "PaymentGateway" do dzialania
         * (bez niego nie ma sensu istniec) - w komentarzu uzasadnij, KTORY z 3
         * sposobow wstrzykiwania (konstruktor/setter/pole) jest najlepszy i
         * dlaczego (odwolaj sie do teorii lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainIocVsDiForSnippet {
        /*
         * 🧪 Zadanie 9:
         * Dla ponizszego (opisanego slownie w komentarzu) kodu: "klasa Foo w
         * konstruktorze przyjmuje interfejs Bar" - w komentarzu wyjasnij OSOBNO,
         * co tu jest realizacja zasady IoC, a co jest technika DI (z teorii lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RewriteTightlyCoupledSnippet {
        /*
         * 🧪 Zadanie 10:
         * Dany (do napisania samemu w tym zadaniu) tightly-coupled kod: klasa
         * "InvoiceService" tworzaca "new PdfExporter()" wewnatrz siebie -
         * przepisz go na constructor injection z interfejsem "Exporter" (metoda
         * "export(String content)") i implementacjami PdfExporter/CsvExporter.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultipleDependenciesViaConstructor {
        /*
         * 🧪 Zadanie 11:
         * Napisz "OrderService" zalezny JEDNOCZESNIE od "PaymentGateway" ORAZ
         * "Notifier" (obie zaleznosci final, wstrzykniete przez konstruktor).
         * Metoda "placeOrder(double amount)" ma wywolac obciazenie platnosci I
         * wyslanie powiadomienia o zlozeniu zamowienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExplainImmutabilityBenefit {
        /*
         * 🧪 Zadanie 12:
         * Dla klasy z Zadania 11 (pola "final"): w komentarzu wyjasnij, dlaczego
         * niemutowalnosc (final) zaleznosci jest korzystna w kontekscie
         * wielowatkowosci (nawiaz do rozdzialu _05_multithreading) - min. 3 zdania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ManualDiSelectionByConfigString {
        /*
         * 🧪 Zadanie 13:
         * Napisz interfejs "ReportGenerator" (metoda "generate()") z dwiema
         * implementacjami "PdfReportGenerator" i "CsvReportGenerator". Napisz
         * metode "createGenerator(String format)" ktora na podstawie napisu
         * "pdf"/"csv" tworzy WLASCIWA implementacje i wstrzykuje ja do klasy
         * "ReportService" przez konstruktor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_StubPaymentGatewayWithFailureSimulation {
        /*
         * 🧪 Zadanie 14:
         * Napisz "StubPaymentGateway implements PaymentGateway" z polem boolean
         * "shouldFail" - jesli true, metoda "charge(...)" rzuca RuntimeException
         * ("Platnosc odrzucona"), jesli false - wypisuje sukces. Zademonstruj
         * OBA scenariusze wstrzykujac stub do OrderService (Lesson18) w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorGodClassToConstructorInjection {
        /*
         * 🧪 Zadanie 15:
         * Napisz "GodClass" ktora WEWNATRZ SIEBIE tworzy TRZY zaleznosci
         * (np. "new EmailNotifier()", "new CreditCardGateway()", "new
         * ConsoleLogger()"). Zrefaktoryzuj ja na "final" pola wstrzykiwane
         * WSZYSTKIE przez JEDEN konstruktor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DemonstrateFieldInjectionTestingProblem {
        /*
         * 🧪 Zadanie 16:
         * Dla klasy z field injection (Zadanie 6, pole prywatne tym razem) -
         * sprobuj napisac test, ktory podmienia zaleznosc BEZ dostepu do pola
         * (bez settera/konstruktora). W komentarzu opisz, dlaczego to niemozliwe
         * bez refleksji lub frameworka DI (zapowiedz Lesson19: @Inject).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RuntimeReconfigurationViaSetter {
        /*
         * 🧪 Zadanie 17:
         * Napisz "AlertService" z setter injection dla "Notifier" (domyslnie
         * "EmailNotifier"). W trakcie dzialania main() wywolaj alert(...), potem
         * podmien na "SmsNotifier" przez setter, wywolaj alert(...) ponownie -
         * pokaz, ze zaleznosc zmienila sie W LOCIE (runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompositionRootPattern {
        /*
         * 🧪 Zadanie 18:
         * Napisz JEDNA metode "wireApplication()" (tzw. "composition root") w
         * ktorej tworzysz WSZYSTKIE obiekty aplikacji (Notifier, PaymentGateway,
         * OrderService) i laczysz je przez konstruktory - w komentarzu wyjasnij,
         * dlaczego korzystnie jest miec TYLKO JEDNO miejsce w kodzie z "new".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SimpleRegistryOfImplementations {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj "Map<String, PaymentGateway>" z wpisami "creditCard" ->
         * CreditCardGateway, "paypal" -> PayPalGateway. Napisz metode
         * "resolve(String key)" zwracajaca odpowiednia implementacje i wstrzyknij
         * WYNIK do OrderService przez konstruktor. W komentarzu napisz, ze to
         * zapowiedz tego, co Lesson19 automatyzuje frameworkiem Guice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ManualUnitTestOfOrderService {
        /*
         * 🧪 Zadanie 20:
         * Napisz w main() prosty "test" (bez frameworka testowego) - wstrzyknij
         * FakePaymentGateway (Lesson18) do OrderService, wywolaj placeOrder(100),
         * i przez if/else wypisz "PASS"/"FAIL" sprawdzajac ze
         * fakeGateway.getCallCount() == 1 oraz getTotalCharged() == 100.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FourClassDependencyGraph {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj graf 4 klas polaczonych constructor injection: "OrderService"
         * (zalezy od PaymentGateway), "NotificationService" (zalezy od Notifier),
         * "OrderController" (zalezy od OBU powyzszych) - zwencuj CALY graf recznie
         * w main() i wywolaj pelny scenariusz przez OrderController.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DiscussWiringComplexityGrowth {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz graf z Zadania 21 o KOLEJNE 3 klasy (np. InventoryService,
         * ShippingService, AuditService), kazda z wlasnymi zaleznosciami. W
         * komentarzu opisz, jak rosnie zlozonosc recznego "wiring" w main() wraz
         * z liczba klas - i w ktorym momencie warto siegnac po framework (Lesson19).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DecoratorPaymentGatewayWithLogging {
        /*
         * 🧪 Zadanie 23:
         * Napisz "LoggingPaymentGateway implements PaymentGateway" ktory w
         * KONSTRUKTORZE przyjmuje INNY "PaymentGateway" (opakowywany) - w metodzie
         * "charge(...)" wypisuje log PRZED i PO delegacji do opakowanego obiektu
         * (wzorzec Decorator zrealizowany przez DI).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompositeNotifierWithListInjection {
        /*
         * 🧪 Zadanie 24:
         * Napisz "CompositeNotifier implements Notifier" ktory w konstruktorze
         * przyjmuje "List<Notifier> notifiers" i w metodzie "send(...)" wysyla
         * wiadomosc przez WSZYSTKIE z listy. Wstrzyknij liste zawierajaca
         * EmailNotifier i SmsNotifier jednoczesnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CircuitBreakerPaymentGatewayDecorator {
        /*
         * 🧪 Zadanie 25:
         * Napisz "CircuitBreakerPaymentGateway implements PaymentGateway"
         * opakowujacy inny PaymentGateway (przez konstruktor) - licz KOLEJNE
         * niepowodzenia (np. rzucone wyjatki opakowanego gateway'u) i po 3 pod
         * rzad wypisz "OBWOD OTWARTY - platnosci wstrzymane" zamiast probowac dalej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HandwrittenMiniDiContainer {
        /*
         * 🧪 Zadanie 26:
         * Napisz PROSTY "kontener DI" - klase z "Map<Class<?>, Object> registry",
         * metoda "register(Class<T> type, T instance)" i "resolve(Class<T> type)"
         * zwracajaca zarejestrowana instancje. Zarejestruj Notifier i PaymentGateway,
         * odczytaj je z powrotem przez resolve() i wstrzyknij do OrderService.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExtendMiniContainerForNamedBindings {
        /*
         * 🧪 Zadanie 27:
         * Rozszerz kontener z Zadania 26 o obsluge WIELU implementacji tego
         * samego interfejsu pod ROZNYMI nazwami (np. "Map<String, PaymentGateway>"
         * z kluczami "creditCard"/"paypal") - dodaj metody "registerNamed(String
         * name, PaymentGateway gw)" i "resolveNamed(String name)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DiscussOwnContainerVsGuiceVsSpring {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: w komentarzu (min. 6 zdan) omow kompromisy miedzy
         * WLASNYM kontenerem DI (Zadanie 26-27), biblioteka Guice (Lesson19) a
         * pelnym frameworkiem typu Spring (jeszcze niepoznanym w kursie) - kiedy
         * ktore rozwiazanie ma sens.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullCheckoutFlowWithConstructorInjectionOnly {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj PELNY przeplyw checkout: "CartService" -> "PricingService" ->
         * "OrderService" -> "PaymentGateway" + "Notifier" (min. 5 klas), WSZYSTKO
         * polaczone WYLACZNIE przez constructor injection, z jednym
         * "composition root" w main() (jak Zadanie 18) skladajacym caly graf.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_RefactorMonolithicOrderProcessingService {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz (najpierw jako "zle" rozwiazanie)
         * monolityczna klase "OrderProcessingService" tworzaca WEWNATRZ SIEBIE
         * (przez "new") logike platnosci, powiadomien I logowania - a nastepnie
         * ZREFAKTORYZUJ ja do co najmniej 3 interfejsow wstrzykiwanych przez
         * KONSTRUKTOR, z pelnym dzialajacym scenariuszem demo w main() i
         * krotkim podsumowaniem w komentarzu, co konkretnie zyskales.
         */
        public static void main(String[] args) { }
    }
}
