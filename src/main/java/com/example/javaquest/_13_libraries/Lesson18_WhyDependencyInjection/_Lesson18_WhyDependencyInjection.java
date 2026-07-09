package com.example.javaquest._13_libraries.Lesson18_WhyDependencyInjection;

public class _Lesson18_WhyDependencyInjection {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: PO CO DEPENDENCY INJECTION? ===");

        /*
         * ============================================================
         * 📦 PROBLEM: "TIGHT COUPLING" - KLASA TWORZACA WLASNE ZALEZNOSCI
         * ============================================================
         * - Ponizej "OrderServiceTightlyCoupled" SAMA, wewnatrz siebie,
         *   tworzy obiekt "new CreditCardGateway()" - jest wiec NA SZTYWNO
         *   ("tightly coupled") zwiazana z JEDNA, KONKRETNA implementacja
         *   platnosci.
         * - To wyglada niewinnie, ale rodzi dwa powazne problemy, ktore
         *   zobaczymy zaraz w akcji:
         *     1) Nie da sie PODMIENIC CreditCardGateway na inna
         *        implementacje (np. PayPalGateway) bez zmiany kodu
         *        OrderServiceTightlyCoupled.
         *     2) Nie da sie PRZETESTOWAC OrderServiceTightlyCoupled w
         *        izolacji - kazdy test i tak uderzy w prawdziwa (lub
         *        udawana-ale-zaszyta-na-sztywno) bramke platnicza, bo
         *        nie mamy jak podstawic "fałszywej" wersji na potrzeby
         *        testu.
         */
        System.out.println("\n=== PRZYKLAD 1: TIGHT COUPLING (ZLA PRAKTYKA) ===");
        OrderServiceTightlyCoupled tightService = new OrderServiceTightlyCoupled();
        tightService.placeOrder(150.00);
        System.out.println("Problem: OrderServiceTightlyCoupled ZAWSZE uzyje CreditCardGateway.");
        System.out.println("Zeby zmienic na PayPal, trzeba by zmienic KOD KLASY OrderServiceTightlyCoupled.");

        /*
         * ============================================================
         * 🔹 INVERSION OF CONTROL (IoC): ZASADA
         * ============================================================
         * - Inversion of Control (odwrocenie sterowania) to OGOLNA ZASADA
         *   projektowa: "nie twórz swoich zaleznosci samodzielnie - dostan
         *   je Z ZEWNATRZ".
         * - Normalnie (bez IoC) to KLASA decyduje, JAKIEJ konkretnej
         *   implementacji uzyc (jak wyzej: OrderServiceTightlyCoupled sam
         *   zdecydowal "bede uzywac CreditCardGateway").
         * - Z IoC to KTOS INNY (kod wywolujacy, framework, "kompozytor"
         *   aplikacji) decyduje, ktora implementacje dostarczyc - klasa
         *   jedynie DEKLARUJE "potrzebuje jakiegos PaymentGateway", nie
         *   wiedzac ani nie obchodzac ja, KTORA to bedzie implementacja.
         * - To jest "odwrocenie" w stosunku do naturalnego, "recznego"
         *   sposobu pisania kodu - stad nazwa.
         */
        System.out.println("\n=== INVERSION OF CONTROL (IoC) - ZASADA ===");
        System.out.println("Bez IoC: klasa SAMA decyduje, jakiej implementacji uzyc (tworzy ja przez 'new').");
        System.out.println("Z IoC:   klasa DOSTAJE gotowa implementacje z zewnatrz - nie wie, KTORA to jest.");

        /*
         * ============================================================
         * 🔹 DEPENDENCY INJECTION (DI): KONKRETNA TECHNIKA REALIZACJI IoC
         * ============================================================
         * - Dependency Injection to NAJPOPULARNIEJSZA, konkretna technika
         *   realizujaca zasade IoC: zaleznosci ("dependencies", czyli inne
         *   obiekty, ktorych klasa potrzebuje do dzialania) sa "wstrzykiwane"
         *   ("injected") do klasy Z ZEWNATRZ, zamiast byc tworzone przez
         *   "new" wewnatrz niej.
         * - UWAGA na czesty blad pojeciowy: IoC to ZASADA (cel), DI to
         *   TECHNIKA (jeden ze sposobow osiagniecia tego celu). Istnieja
         *   tez inne techniki IoC (np. Service Locator), ale DI jest
         *   zdecydowanie najczesciej stosowana we wspolczesnej Javie.
         * - Ponizej "PaymentGateway" to INTERFEJS (abstrakcja) - kontrakt,
         *   ktorego oczekuje OrderService, NIE konkretna implementacja.
         *   Dzieki temu OrderService moze wspolpracowac z DOWOLNA klasa
         *   implementujaca ten interfejs, bez zadnej wiedzy o szczegolach.
         */
        System.out.println("\n=== DEPENDENCY INJECTION (DI) - TECHNIKA ===");
        System.out.println("DI = konkretny SPOSOB realizacji IoC: zaleznosci wstrzykiwane z zewnatrz (np. przez konstruktor).");

        /*
         * ============================================================
         * 🔍 3 SPOSOBY WSTRZYKIWANIA ZALEZNOSCI
         * ============================================================
         * 1) PRZEZ KONSTRUKTOR (constructor injection) - REKOMENDOWANY:
         *    - Zaleznosc przekazywana jako argument konstruktora,
         *      przypisywana do pola "final".
         *    - Obiekt jest ZAWSZE w pelni zainicjalizowany od razu po
         *      utworzeniu (nie da sie stworzyc "polowicznego" obiektu bez
         *      zaleznosci) - niemozliwy "NullPointerException" z powodu
         *      zapomnianej zaleznosci.
         *    - Pole "final" - zaleznosc NIE MOZE zostac podmieniona po
         *      utworzeniu obiektu (immutability, bezpieczenstwo watkowe).
         *    - Ulatwia testowanie - w tescie po prostu podajesz inna
         *      implementacje w konstruktorze.
         * 2) PRZEZ SETTER (setter injection) - dla zaleznosci OPCJONALNYCH:
         *    - Zaleznosc ustawiana metoda "setXxx(...)" PO utworzeniu
         *      obiektu (konstruktorem bezargumentowym lub z innymi
         *      wymaganymi polami).
         *    - Dobre, gdy zaleznosc NIE JEST wymagana do dzialania obiektu
         *      (ma sensowna wartosc domyslna) albo moze byc zmieniana w
         *      trakcie zycia obiektu.
         *    - Wada: obiekt MOZE istniec przez chwile BEZ ustawionej
         *      zaleznosci (ryzyko zapomnienia wywolania settera).
         * 3) PRZEZ POLE (field injection) - NAJMNIEJ ZALECANE:
         *    - Zaleznosc przypisywana BEZPOSREDNIO do pola (bez
         *      konstruktora ani settera) - w praktyce w realnych
         *      projektach robi to framework przez REFLEKSJE (np. adnotacja
         *      wstrzykujaca wartosc wprost do pola prywatnego).
         *    - Bez frameworka (jak w tej lekcji) oznacza to pole
         *      PAKIETOWE/PUBLICZNE ustawiane recznie z zewnatrz - LAMIE
         *      hermetyzacje (encapsulation) i utrudnia testowanie bez
         *      dodatkowych narzedzi (nie da sie latwo wstrzyknac "z reki"
         *      przez czysta Jave bez refleksji, jesli pole jest prywatne).
         *    - Nie da sie uzyc "final" - zaleznosc teoretycznie MOZE zostac
         *      zmieniona pozniej, co utrudnia rozumowanie o kodzie.
         */
        printInjectionTypesComparison();

        /*
         * ============================================================
         * 📦 PRZYKLAD: RECZNE DI (BEZ FRAMEWORKA) - KONSTRUKTOR
         * ============================================================
         * - "OrderService" zalezy od INTERFEJSU "PaymentGateway", NIE od
         *   konkretnej implementacji.
         * - W "main()" to MY (kod wywolujacy, "kompozytor" aplikacji)
         *   decydujemy, KTORA implementacje wstrzyknac - to jest wlasnie
         *   Inversion of Control w praktyce, zrealizowane RECZNIE, bez
         *   zadnego frameworka DI.
         * - W kolejnej lekcji (Lesson19) zobaczymy, jak Guice automatyzuje
         *   dokladnie to "reczne" wiazanie ponizej.
         */
        System.out.println("\n=== PRZYKLAD 2: RECZNE DI PRZEZ KONSTRUKTOR (DOBRA PRAKTYKA) ===");

        PaymentGateway creditCardGateway = new CreditCardGateway();
        OrderService orderServiceWithCard = new OrderService(creditCardGateway);
        orderServiceWithCard.placeOrder(200.00);

        PaymentGateway payPalGateway = new PayPalGateway();
        OrderService orderServiceWithPayPal = new OrderService(payPalGateway);
        orderServiceWithPayPal.placeOrder(75.50);

        System.out.println("Ta sama klasa OrderService - DWIE rozne implementacje PaymentGateway,");
        System.out.println("wstrzykniete Z ZEWNATRZ, BEZ zadnej zmiany w kodzie OrderService.");

        /*
         * ============================================================
         * 🔍 KORZYSC W PRAKTYCE: LATWE TESTOWANIE DZIEKI DI
         * ============================================================
         * - Ponizej "FakePaymentGateway" to prosta, "udawana" (fake)
         *   implementacja PaymentGateway, ktora NIE laczy sie z zadna
         *   prawdziwa bramka platnicza - tylko ZAPAMIETUJE, ile razy i na
         *   jaka kwote zostala wywolana.
         * - Dzieki constructor injection mozemy ja wstrzyknac do
         *   OrderService DOKLADNIE tak samo, jak prawdziwa implementacje -
         *   OrderService W OGOLE nie wie, ze dostal "falszywke".
         * - To jest SEDNO korzysci z DI dla testow: klasa pod testem
         *   (OrderService) jest testowana W IZOLACJI, bez efektow
         *   ubocznych (prawdziwych platnosci, wywolan sieciowych).
         */
        System.out.println("\n=== PRZYKLAD 3: DI UMOZLIWIA LATWE TESTOWANIE ===");
        FakePaymentGateway fakeGateway = new FakePaymentGateway();
        OrderService testableOrderService = new OrderService(fakeGateway);
        testableOrderService.placeOrder(999.99);
        System.out.println("FakePaymentGateway zarejestrowal " + fakeGateway.getCallCount()
                + " wywolanie/wywolania na laczna kwote " + fakeGateway.getTotalCharged()
                + " - bez ANI JEDNEGO polaczenia z prawdziwa bramka platnicza.");

        /*
         * ============================================================
         * 🔹 SETTER INJECTION I FIELD INJECTION - PRZYKLADY
         * ============================================================
         */
        System.out.println("\n=== PRZYKLAD 4: SETTER INJECTION (ZALEZNOSC OPCJONALNA) ===");
        OrderServiceSetterInjection setterService = new OrderServiceSetterInjection();
        System.out.println("Utworzony BEZ bramki platniczej - uzywa domyslnej implementacji:");
        setterService.placeOrder(50.00);
        setterService.setPaymentGateway(new PayPalGateway());
        System.out.println("Po wywolaniu setPaymentGateway(...) - podmieniona w LOCIE:");
        setterService.placeOrder(50.00);

        System.out.println("\n=== PRZYKLAD 5: FIELD INJECTION (NAJMNIEJ ZALECANE) ===");
        OrderServiceFieldInjection fieldService = new OrderServiceFieldInjection();
        fieldService.paymentGateway = new CreditCardGateway(); // wstrzykniecie recznie, wprost do pola
        fieldService.placeOrder(30.00);
        System.out.println("Dziala, ale: pole NIE MOZE byc 'final', latwo zapomniec je ustawic");
        System.out.println("(NullPointerException przy uzyciu), a w realnym kodzie takie pole zwykle jest");
        System.out.println("prywatne i wymaga REFLEKSJI frameworka, by je wstrzyknac (patrz Lesson19: @Inject).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Tight coupling (tworzenie zaleznosci przez "new" wewnatrz
         *   klasy) utrudnia TESTOWANIE i ZMIANE IMPLEMENTACJI.
         * - Inversion of Control (IoC) - zasada: "nie twórz swoich
         *   zaleznosci, dostan je z zewnatrz".
         * - Dependency Injection (DI) - konkretna TECHNIKA realizujaca IoC.
         * - 3 sposoby wstrzykiwania: KONSTRUKTOR (rekomendowany, "final",
         *   zawsze kompletny obiekt), SETTER (zaleznosci opcjonalne),
         *   POLE (najmniej zalecane, lamie hermetyzacje).
         * - Powyzszy przyklad to RECZNE DI - dzialajace bez zadnego
         *   frameworka, tylko dzieki dobrze zaprojektowanym interfejsom i
         *   przekazywaniu obiektow przez konstruktor.
         * - Problem recznego DI: przy DUZYM grafie zaleznosci (dziesiatki
         *   klas zaleznych od siebie nawzajem) reczne "wiring" w main()
         *   staje sie nieporzadne i trudne w utrzymaniu - w kolejnej
         *   lekcji poznasz Guice, framework, ktory robi to AUTOMATYCZNIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    private static void printInjectionTypesComparison() {
        System.out.println("\n=== 3 SPOSOBY WSTRZYKIWANIA ZALEZNOSCI ===");
        String format = "%-20s | %-25s | %-35s%n";
        System.out.printf(format, "Sposob", "Kiedy uzywac", "Wada / uwaga");
        System.out.println("-".repeat(85));
        System.out.printf(format, "Konstruktor", "REKOMENDOWANY, domyslny", "Wiecej argumentow przy duzej liczbie zaleznosci");
        System.out.printf(format, "Setter", "Zaleznosci opcjonalne", "Obiekt moze chwilowo byc bez zaleznosci");
        System.out.printf(format, "Pole", "Najmniej zalecane", "Lamie hermetyzacje, brak 'final', trudne testy bez frameworka");
    }

    /**
     * Interfejs - abstrakcja bramki platniczej. OrderService bedzie zalezal
     * TYLKO od tego kontraktu, nigdy od konkretnej implementacji.
     */
    interface PaymentGateway {
        void charge(double amount);
    }

    static class CreditCardGateway implements PaymentGateway {
        @Override
        public void charge(double amount) {
            System.out.println(" [CreditCardGateway] Obciazono karte kredytowa kwota: " + amount + " PLN");
        }
    }

    static class PayPalGateway implements PaymentGateway {
        @Override
        public void charge(double amount) {
            System.out.println(" [PayPalGateway] Obciazono konto PayPal kwota: " + amount + " PLN");
        }
    }

    /**
     * "Fake" implementacja uzywana WYLACZNIE do demonstracji testowalnosci -
     * nie laczy sie z zadna prawdziwa bramka, tylko zapamietuje wywolania.
     */
    static class FakePaymentGateway implements PaymentGateway {
        private int callCount = 0;
        private double totalCharged = 0.0;

        @Override
        public void charge(double amount) {
            callCount++;
            totalCharged += amount;
            System.out.println(" [FakePaymentGateway] (TEST) Zarejestrowano platnosc: " + amount + " PLN");
        }

        int getCallCount() {
            return callCount;
        }

        double getTotalCharged() {
            return totalCharged;
        }
    }

    /**
     * ZLA PRAKTYKA: klasa SAMA tworzy swoja zaleznosc przez "new" - tight
     * coupling z konkretna implementacja CreditCardGateway.
     */
    static class OrderServiceTightlyCoupled {
        private final PaymentGateway paymentGateway = new CreditCardGateway(); // "new" WEWNATRZ klasy - problem!

        void placeOrder(double amount) {
            System.out.println("Skladanie zamowienia na kwote: " + amount + " PLN");
            paymentGateway.charge(amount);
        }
    }

    /**
     * DOBRA PRAKTYKA: constructor injection. Zaleznosc przekazana z
     * zewnatrz, przypisana do pola "final" - obiekt zawsze kompletny.
     */
    static class OrderService {
        private final PaymentGateway paymentGateway;

        OrderService(PaymentGateway paymentGateway) {
            this.paymentGateway = paymentGateway;
        }

        void placeOrder(double amount) {
            System.out.println("Skladanie zamowienia na kwote: " + amount + " PLN");
            paymentGateway.charge(amount);
        }
    }

    /**
     * Setter injection - zaleznosc opcjonalna, z sensowna wartoscia
     * domyslna, mozliwa do podmiany w trakcie zycia obiektu.
     */
    static class OrderServiceSetterInjection {
        private PaymentGateway paymentGateway = new CreditCardGateway(); // wartosc domyslna

        void setPaymentGateway(PaymentGateway paymentGateway) {
            this.paymentGateway = paymentGateway;
        }

        void placeOrder(double amount) {
            System.out.println("Skladanie zamowienia na kwote: " + amount + " PLN");
            paymentGateway.charge(amount);
        }
    }

    /**
     * Field injection - NAJMNIEJ zalecane. Pole pakietowe (nie prywatne),
     * zeby dalo sie je ustawic recznie z zewnatrz BEZ frameworka/refleksji -
     * w realnym kodzie z frameworkiem byloby to pole prywatne + adnotacja.
     */
    static class OrderServiceFieldInjection {
        PaymentGateway paymentGateway; // brak "final", brak konstruktora/settera

        void placeOrder(double amount) {
            System.out.println("Skladanie zamowienia na kwote: " + amount + " PLN");
            paymentGateway.charge(amount);
        }
    }
}
