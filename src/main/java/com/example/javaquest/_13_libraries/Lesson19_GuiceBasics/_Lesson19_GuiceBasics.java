package com.example.javaquest._13_libraries.Lesson19_GuiceBasics;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public class _Lesson19_GuiceBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: GUICE - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 18: RECZNE DI
         * ============================================================
         * W Lesson18 nauczylismy sie, ze DOBRA praktyka to constructor
         * injection - klasa "OrderService" zalezala od INTERFEJSU
         * "PaymentGateway", a MY, w main(), reczne decydowalismy, ktora
         * implementacje wstrzyknac:
         *
         *   PaymentGateway gateway = new CreditCardGateway();
         *   OrderService service = new OrderService(gateway);
         *
         * Dla JEDNEJ klasy to zaden problem. Ale co, gdy "OrderService"
         * zalezy od "Notifier", ktory z kolei zalezy od "Logger", ktory
         * zalezy od "Clock"...? Reczne skladanie takiego grafu w main()
         * (tzw. "composition root") szybko robi sie dlugie, powtarzalne
         * i podatne na bledy (latwo zapomniec przekazac ktoras zaleznosc
         * albo utworzyc obiekt w zlej kolejnosci).
         *
         * 🔍 CZYM JEST GUICE?
         * Guice (wymowa: "juice", od Google + "juice") to lekki framework
         * Dependency Injection od Google. Zamiast REKODOWANIA recznego
         * wiazania w main(), MY tylko DEKLARUJEMY (adnotacjami i/lub
         * konfiguracja w Module), "co jest czym" - a Guice, w czasie
         * dzialania programu, PRZEZ REFLEKSJE, sam buduje caly graf
         * obiektow i wstrzykuje je tam, gdzie trzeba.
         * To DOKLADNIE ta sama idea IoC/DI co w Lesson18 - Guice tylko
         * AUTOMATYZUJE reczne "new" + przekazywanie przez konstruktor.
         */

        /*
         * ============================================================
         * 🔹 TEN SAM PRZYKLAD CO W LESSON18 - TERAZ Z ADNOTACJA @Inject
         * ============================================================
         * "OrderService" ponizej wyglada PRAWIE identycznie jak w Lesson18 -
         * jedyna roznica to adnotacja "@Inject" nad konstruktorem. Ta
         * adnotacja to SYGNAL dla Guice: "to jest konstruktor, ktorego
         * masz uzyc, by zbudowac ten obiekt - wypelnij jego argumenty
         * SAMODZIELNIE, na podstawie ich typow".
         * "PaymentGateway" to nadal zwykly interfejs - OrderService nadal
         * NIC nie wie, KTORA implementacja dostanie.
         */
        System.out.println("\n=== PRZYKLAD 1: KLASA Z @Inject NA KONSTRUKTORZE ===");
        System.out.println("OrderService(@Inject PaymentGateway paymentGateway) - identycznie jak Lesson18,");
        System.out.println("tylko z adnotacja @Inject informujaca Guice, ktorego konstruktora uzyc.");

        /*
         * ============================================================
         * 🔹 Module / AbstractModule: GDZIE MOWIMY GUICE "CO JEST CZYM"
         * ============================================================
         * Sam "@Inject" na konstruktorze OrderService NIE wystarczy - Guice
         * wciaz musi wiedziec, JAKA konkretna implementacje podstawic pod
         * interfejs "PaymentGateway". To wlasnie robi MODUL:
         *
         *   class PaymentModule extends AbstractModule {
         *       @Override
         *       protected void configure() {
         *           bind(PaymentGateway.class).to(CreditCardGateway.class);
         *       }
         *   }
         *
         * - "Module" to interfejs, "AbstractModule" to wygodna klasa
         *   bazowa z metoda "configure()", ktora nadpisujemy.
         * - "bind(PaymentGateway.class).to(CreditCardGateway.class)" to
         *   deklaracja: "kiedykolwiek ktos potrzebuje PaymentGateway,
         *   podstaw instancje CreditCardGateway". To DOKLADNY odpowiednik
         *   linijki "PaymentGateway gateway = new CreditCardGateway();"
         *   z Lesson18 - tylko zapisany DEKLARATYWNIE, RAZ, w jednym
         *   miejscu (module), zamiast w kazdym miejscu uzycia z osobna.
         */
        System.out.println("\n=== PRZYKLAD 2: MODULE - DEKLARACJA WIAZAN ===");
        System.out.println("bind(PaymentGateway.class).to(CreditCardGateway.class) w metodzie configure()");
        System.out.println("= deklaratywny odpowiednik 'new CreditCardGateway()' z Lesson18.");

        /*
         * ============================================================
         * 🔹 Guice.createInjector(...) I injector.getInstance(...)
         * ============================================================
         * - "Guice.createInjector(new PaymentModule())" tworzy "Injector" -
         *   glowny obiekt frameworka, ktory ZNA wszystkie wiazania z
         *   przekazanych modulow i UMIE budowac obiekty na ich podstawie.
         * - "injector.getInstance(OrderService.class)" prosi Injector:
         *   "zbuduj mi kompletny OrderService". Guice widzi adnotacje
         *   @Inject na konstruktorze OrderService, widzi, ze potrzebuje
         *   PaymentGateway, zagląda do wiazan z modulu, znajduje
         *   "PaymentGateway -> CreditCardGateway", tworzy CreditCardGateway,
         *   i na koncu tworzy OrderService(creditCardGateway) - CALY ten
         *   lancuch dzieje sie AUTOMATYCZNIE, bez ANI JEDNEGO "new" z naszej
         *   strony.
         */
        System.out.println("\n=== PRZYKLAD 3: Guice.createInjector + injector.getInstance ===");
        Injector creditCardInjector = Guice.createInjector(new PaymentModule());
        OrderService orderServiceWithCard = creditCardInjector.getInstance(OrderService.class);
        orderServiceWithCard.placeOrder(200.00);
        System.out.println("OrderService zostal zbudowany W CALOSCI przez Guice - bez jednego recznego 'new'.");

        /*
         * ============================================================
         * 🔍 PODMIANA IMPLEMENTACJI: TYLKO ZMIANA MODULU
         * ============================================================
         * Zeby uzyc PayPala zamiast karty kredytowej, w Lesson18 trzeba
         * bylo w main() napisac "new PayPalGateway()" zamiast
         * "new CreditCardGateway()". Tutaj wystarczy podac INNY modul -
         * kod klasy "OrderService" ANI RAZU nie jest dotykany.
         */
        System.out.println("\n=== PRZYKLAD 4: PODMIANA IMPLEMENTACJI PRZEZ INNY MODULE ===");
        Injector payPalInjector = Guice.createInjector(new PayPalPaymentModule());
        OrderService orderServiceWithPayPal = payPalInjector.getInstance(OrderService.class);
        orderServiceWithPayPal.placeOrder(75.50);
        System.out.println("Ta sama klasa OrderService - inny Module przekazany do createInjector -");
        System.out.println("inna implementacja PaymentGateway, BEZ zmiany kodu OrderService.");

        /*
         * ============================================================
         * 🔹 FIELD INJECTION Z @Inject (PRZYPOMNIENIE OSTRZEZENIA Z LESSON18)
         * ============================================================
         * W Lesson18 padlo zdanie: "w realnym kodzie takie pole zwykle
         * jest prywatne i wymaga REFLEKSJI frameworka, by je wstrzyknac".
         * Guice wlasnie TAK dziala - adnotacja "@Inject" na PRYWATNYM
         * POLU wystarczy, Guice przez refleksje ustawi je SAMODZIELNIE
         * (nie trzeba settera ani konstruktora):
         *
         *   static class NotificationService {
         *       @Inject
         *       private Notifier notifier; // prywatne, a jednak wstrzykniete
         *       ...
         *   }
         *
         * Guice wspiera obie formy: constructor injection (REKOMENDOWANA,
         * jak OrderService wyzej - pozwala na pole "final", obiekt zawsze
         * kompletny) oraz field injection (wygodna, ale - jak w Lesson18 -
         * mniej zalecana: pole nie moze byc "final", a obiekt teoretycznie
         * moze na chwile istniec bez wstrzykniętej zaleznosci przed tym,
         * jak Guice zdazy ja ustawic).
         */
        System.out.println("\n=== PRZYKLAD 5: FIELD INJECTION PRZEZ @Inject (PRYWATNE POLE!) ===");
        Injector notificationInjector = Guice.createInjector(new NotificationModule());
        NotificationService notificationService = notificationInjector.getInstance(NotificationService.class);
        notificationService.notifyUser("Twoje zamowienie zostalo przyjete.");
        System.out.println("Pole 'notifier' w NotificationService jest PRYWATNE - Guice ustawil je");
        System.out.println("przez refleksje, dzieki samej adnotacji @Inject. Zadnego konstruktora/settera.");

        /*
         * ============================================================
         * 🔍 WIELE ZALEZNOSCI W JEDNYM KONSTRUKTORZE
         * ============================================================
         * Guice rozwiazuje graf zaleznosci REKURENCYJNIE - "OrderController"
         * ponizej zalezy JEDNOCZESNIE od "OrderService" ORAZ "Notifier".
         * Wystarczy JEDNO wywolanie "getInstance(OrderController.class)",
         * a Guice sam ustali kolejnosc budowy WSZYSTKICH posrednich
         * obiektow (PaymentGateway -> OrderService, Notifier -> ...
         * -> OrderController).
         */
        System.out.println("\n=== PRZYKLAD 6: WIELOPOZIOMOWY GRAF ZALEZNOSCI ===");
        Injector controllerInjector = Guice.createInjector(new PaymentModule(), new NotificationModule());
        OrderController orderController = controllerInjector.getInstance(OrderController.class);
        orderController.handleNewOrder(120.00);
        System.out.println("Jeden getInstance(OrderController.class) - Guice sam zbudowal CALY graf:");
        System.out.println("OrderController -> OrderService -> PaymentGateway, OrderController -> Notifier.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Guice to framework Dependency Injection - AUTOMATYZUJE reczne
         *   wiazanie zaleznosci znane z Lesson18, przez REFLEKSJE.
         * - "@Inject" na konstruktorze (rekomendowane) lub na polu (dziala,
         *   nawet gdy pole jest prywatne, ale mniej zalecane - dokladnie
         *   jak w Lesson18) mowi Guice, GDZIE wstrzykiwac.
         * - "Module"/"AbstractModule" + metoda "configure()" to miejsce,
         *   gdzie DEKLARUJEMY wiazania: "bind(Interfejs.class).to(Impl.class)" -
         *   deklaratywny odpowiednik recznego "new Impl()" z Lesson18.
         * - "Guice.createInjector(new MojModul())" tworzy "Injector" -
         *   obiekt znajacy wszystkie wiazania i umiejacy budowac grafy
         *   obiektow.
         * - "injector.getInstance(Klasa.class)" buduje kompletny obiekt
         *   (i CALY graf jego zaleznosci) w JEDNYM wywolaniu.
         * - Podmiana implementacji (np. CreditCard -> PayPal) to teraz
         *   zmiana JEDNEGO modulu, ZERO zmian w klasach korzystajacych
         *   z interfejsu.
         * - W kolejnej lekcji (Lesson20) poznasz zaawansowane mozliwosci
         *   Guice: scope'y (@Singleton), metody @Provides, Provider<T>,
         *   wstrzykiwanie nazwane (@Named) i laczenie wielu modulow.
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    /**
     * Interfejs - ta sama abstrakcja bramki platniczej co w Lesson18.
     * OrderService zalezy TYLKO od tego kontraktu.
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

    interface Notifier {
        void send(String message);
    }

    static class EmailNotifier implements Notifier {
        @Override
        public void send(String message) {
            System.out.println(" [EmailNotifier] Wysylam e-mail: " + message);
        }
    }

    /**
     * DOBRA PRAKTYKA (rekomendowana przez Guice): constructor injection.
     * "@Inject" na konstruktorze mowi Guice "tego uzyj do budowy obiektu".
     * Pole nadal "final" - dokladnie jak reczne DI w Lesson18.
     */
    static class OrderService {
        private final PaymentGateway paymentGateway;

        @Inject
        OrderService(PaymentGateway paymentGateway) {
            this.paymentGateway = paymentGateway;
        }

        void placeOrder(double amount) {
            System.out.println("Skladanie zamowienia na kwote: " + amount + " PLN");
            paymentGateway.charge(amount);
        }
    }

    /**
     * Field injection przez @Inject - Guice wstrzyknie zaleznosc przez
     * refleksje, mimo ze pole jest prywatne (patrz ostrzezenie w Lesson18).
     */
    static class NotificationService {
        @Inject
        private Notifier notifier;

        void notifyUser(String message) {
            notifier.send(message);
        }
    }

    /**
     * Zalezy JEDNOCZESNIE od OrderService i Notifier - Guice sam zbuduje
     * cala CHAIN zaleznosci przy JEDNYM wywolaniu getInstance(...).
     */
    static class OrderController {
        private final OrderService orderService;
        private final Notifier notifier;

        @Inject
        OrderController(OrderService orderService, Notifier notifier) {
            this.orderService = orderService;
            this.notifier = notifier;
        }

        void handleNewOrder(double amount) {
            orderService.placeOrder(amount);
            notifier.send("Nowe zamowienie na kwote " + amount + " PLN zostalo obsluzone.");
        }
    }

    /**
     * Module - deklaruje wiazanie PaymentGateway -> CreditCardGateway.
     * Odpowiednik "new CreditCardGateway()" z Lesson18, tylko deklaratywnie.
     */
    static class PaymentModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(PaymentGateway.class).to(CreditCardGateway.class);
        }
    }

    /**
     * Inny Module - to samo wiazanie, ale na inna implementacje. Pokazuje,
     * jak LATWO podmienic implementacje bez dotykania klas korzystajacych.
     */
    static class PayPalPaymentModule implements Module {
        @Override
        public void configure(com.google.inject.Binder binder) {
            binder.bind(PaymentGateway.class).to(PayPalGateway.class);
        }
    }

    static class NotificationModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Notifier.class).to(EmailNotifier.class);
        }
    }
}
