package com.example.javaquest._17_architecture.Lesson18_EventDrivenCommunicationBetweenModules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class _Lesson18_EventDrivenCommunicationBetweenModules {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: KOMUNIKACJA ZDARZENIOWA MIEDZY MODULAMI ===");

        /*
         * ============================================================
         * 📦 PROBLEM: BEZPOSREDNIE WYWOLANIA TWORZA SZTYWNE SPRZEZENIE
         * ============================================================
         * - W Lesson17 moduly komunikowaly sie przez BEZPOSREDNIE
         *   wywolania publicznego API (Orders wolal InventoryModuleApi
         *   wprost) - to dziala DOBRZE, gdy Orders POTRZEBUJE natychmiastowej
         *   ODPOWIEDZI od Inventory (np. "czy jest na stanie?"), zeby
         *   zdecydowac, co robic dalej.
         * - Ale co, gdy modul A po prostu chce POINFORMOWAC inne moduly,
         *   ze "cos sie stalo" (np. "zamowienie zlozone"), bez CZEKANIA na
         *   ich odpowiedz i bez WIEDZY, ILU (i KTORYCH) modulow to w ogole
         *   interesuje?
         */
        System.out.println("Bezposrednie wywolanie (Lesson17) = gdy POTRZEBUJESZ odpowiedzi. Zdarzenie = gdy TYLKO informujesz.");

        /*
         * ============================================================
         * 🔹 ZDARZENIA DOMENOWE (DOMAIN EVENTS) + SZYNA ZDARZEN (EVENT BUS)
         * ============================================================
         * - Modul A PUBLIKUJE zdarzenie domenowe (prosty, niezmienny
         *   `record`, np. `OrderPlaced`) do WSPOLNEJ "szyny zdarzen"
         *   (event bus) - i NIC WIECEJ nie robi.
         * - Moduly B, C, D (dowolna ich liczba) MOGA (ale nie musza)
         *   ZASUBSKRYBOWAC ten typ zdarzenia i zareagowac NIEZALEZNIE od
         *   siebie nawzajem.
         * - Modul A NIE WIE (i NIE POWINIEN wiedziec) ILE modulow
         *   subskrybuje jego zdarzenia - to jest SEDNO odwrocenia
         *   sprzezenia (analogicznie do DIP, `_16_clean_code/Lesson11`,
         *   ale zastosowane do KOMUNIKACJI, nie do wstrzykiwania
         *   zaleznosci).
         */
        demonstrateEventDrivenDecoupling();

        /*
         * ============================================================
         * 🔍 DODANIE NOWEGO SLUCHACZA = ZERO ZMIAN W MODULE PUBLIKUJACYM
         * ============================================================
         * - To jest DOKLADNIE Open/Closed Principle (`_16_clean_code/
         *   Lesson08`) zastosowany na poziomie CALEJ architektury: dodanie
         *   NOWEGO modulu reagujacego na istniejace zdarzenie NIE WYMAGA
         *   zadnej zmiany w module PUBLIKUJACYM to zdarzenie.
         */
        demonstrateAddingNewSubscriberWithoutChangingPublisher();

        /*
         * ============================================================
         * 🔹 KIEDY BEZPOSREDNIE WYWOLANIE, A KIEDY ZDARZENIE?
         * ============================================================
         * - BEZPOSREDNIE WYWOLANIE (Lesson17): gdy operacja modulu A NIE
         *   MOZE sie zakonczyc bez odpowiedzi modulu B (np. Orders MUSI
         *   znac wynik "czy jest na stanie", zeby zdecydowac, czy w ogole
         *   przyjac zamowienie).
         * - ZDARZENIE: gdy operacja modulu A jest JUZ ZAKONCZONA, a inne
         *   moduly MOGA (opcjonalnie) na nia zareagowac - "zamowienie
         *   zostalo zlozone" to FAKT, ktory sie juz wydarzyl; to, czy
         *   Powiadomienia wysla e-mail, NIE POWINNO wplywac na to, czy
         *   zamowienie zostalo poprawnie zlozone.
         */
        System.out.println("\nBezposrednie wywolanie: gdy POTRZEBUJESZ odpowiedzi PRZED zakonczeniem. Zdarzenie: gdy operacja JUZ sie zakonczyla.");

        /*
         * ============================================================
         * 🔍 KOMPROMIS: SPOJNOSC OSTATECZNA (EVENTUAL CONSISTENCY)
         * ============================================================
         * - Sluchacze zdarzen CZESTO dzialaja POZA granica transakcji
         *   (Lesson13) modulu publikujacego - miedzy momentem publikacji
         *   zdarzenia a momentem reakcji sluchacza MOZE minac chwila (albo
         *   sluchacz moze chwilowo zawiesc).
         * - To oznacza SPOJNOSC OSTATECZNA (eventual consistency), NIE
         *   natychmiastowa - inne moduly "dowiaduja sie" o zmianie z
         *   OPOZNIENIEM, nie w tej samej mikrosekundzie. To swiadomy
         *   kompromis (warty ADR, Lesson02), nie darmowa korzysc.
         */
        System.out.println("\nKompromis: sluchacze reaguja z OPOZNIENIEM (spojnosc ostateczna) - to NIE jest darmowe, warto to udokumentowac (ADR, Lesson02).");

        /*
         * ============================================================
         * 🔹 W PROCESIE DZIS, MIEDZY PROCESAMI JUTRO (ZAPOWIEDZ LESSON19)
         * ============================================================
         * - Szyna zdarzen w tej lekcji dziala CALKOWICIE WEWNATRZ 1
         *   procesu (modularny monolit, Lesson17) - prosta lista
         *   subskrybentow w pamieci.
         * - Ta SAMA idea (publikuj zdarzenie, subskrybenci reaguja
         *   niezaleznie) skaluje sie do komunikacji MIEDZY procesami/
         *   mikroserwisami (przez prawdziwy broker wiadomosci, np. Kafka/
         *   RabbitMQ) - mechanizm transportu sie zmienia, ale ARCHITEKTURA
         *   (odwrocone sprzezenie przez zdarzenia) zostaje TA SAMA.
         *   Wiecej o tym kompromisie w Lesson19.
         */
        System.out.println("\nTa sama idea (publikuj+subskrybuj) skaluje sie do komunikacji MIEDZY mikroserwisami (Lesson19) - transport inny, architektura ta sama.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Bezposrednie wywolanie (Lesson17): gdy POTRZEBUJESZ odpowiedzi.
         * - Zdarzenie domenowe + szyna zdarzen: gdy TYLKO informujesz o
         *   fakcie, ktory sie juz wydarzyl.
         * - Dodanie nowego sluchacza = OCP na poziomie architektury - ZERO
         *   zmian w module publikujacym.
         * - Kompromis: spojnosc ostateczna, nie natychmiastowa - sluchacze
         *   dzialaja POZA granica transakcji (Lesson13).
         * - Ta sama idea skaluje sie do komunikacji miedzy mikroserwisami
         *   (Lesson19), tylko transport sie zmienia.
         * - W kolejnej lekcji (Lesson19): kiedy w ogole WARTO przejsc z
         *   modularnego monolitu na prawdziwe, rozproszone mikroserwisy - i
         *   kiedy zdecydowanie NIE warto.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    /** Zdarzenie domenowe - prosty, niezmienny fakt: "to sie JUZ wydarzylo" (nie prosba o wykonanie czegos). */
    record OrderPlaced(String orderId, String customerEmail, double amount) {
    }

    /** Prosta szyna zdarzen w pamieci - modul publikujacy NIE WIE, ilu (i ktorych) ma subskrybentow. */
    static class InMemoryEventBus {
        private final Map<Class<?>, List<Consumer<Object>>> subscribers = new HashMap<>();

        @SuppressWarnings("unchecked")
        <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
            subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add((Consumer<Object>) handler);
        }

        void publish(Object event) {
            List<Consumer<Object>> handlers = subscribers.getOrDefault(event.getClass(), List.of());
            for (Consumer<Object> handler : handlers) {
                handler.accept(event);
            }
        }
    }

    /** MODUL "Orders" - publikuje zdarzenie PO zakonczeniu WLASNEJ operacji, nie zna zadnych subskrybentow. */
    static class OrdersModule {
        private final InMemoryEventBus eventBus;

        OrdersModule(InMemoryEventBus eventBus) {
            this.eventBus = eventBus;
        }

        void placeOrder(String orderId, String customerEmail, double amount) {
            System.out.println("[Orders] Zamowienie " + orderId + " zapisane (operacja WLASNA modulu Orders JUZ zakonczona).");
            eventBus.publish(new OrderPlaced(orderId, customerEmail, amount)); // publikacja - bez oczekiwania na odpowiedz
        }
    }

    private static void demonstrateEventDrivenDecoupling() {
        System.out.println("\n=== PUBLIKACJA ZDARZENIA - MODUL 'ORDERS' NIE ZNA SUBSKRYBENTOW ===");

        InMemoryEventBus eventBus = new InMemoryEventBus();

        // MODUL "Notifications" subskrybuje NIEZALEZNIE, bez wiedzy Orders o jego istnieniu.
        eventBus.subscribe(OrderPlaced.class, event ->
                System.out.println("  [Notifications] Wysylam e-mail potwierdzajacy do " + event.customerEmail()));

        OrdersModule orders = new OrdersModule(eventBus);
        orders.placeOrder("ORD-1", "ala@example.com", 199.99);

        System.out.println("-> OrdersModule.placeOrder() NIE zawiera ANI JEDNEJ linii kodu odnoszacej sie do Notifications.");
    }

    private static void demonstrateAddingNewSubscriberWithoutChangingPublisher() {
        System.out.println("\n=== DODANIE DRUGIEGO SLUCHACZA - ZERO ZMIAN W 'ORDERS' ===");

        InMemoryEventBus eventBus = new InMemoryEventBus();

        eventBus.subscribe(OrderPlaced.class, event ->
                System.out.println("  [Notifications] Wysylam e-mail potwierdzajacy do " + event.customerEmail()));

        // NOWY subskrybent - dodany PO fakcie, bez zadnej zmiany w OrdersModule:
        eventBus.subscribe(OrderPlaced.class, event ->
                System.out.println("  [Analityka] Odnotowuje sprzedaz: " + event.amount() + " PLN dla " + event.orderId()));

        OrdersModule orders = new OrdersModule(eventBus);
        orders.placeOrder("ORD-2", "bartek@example.com", 89.50);

        System.out.println("-> TA SAMA klasa OrdersModule.java (nie zmieniona) - teraz obsluzyla 2 subskrybentow zamiast 1.");
    }
}
