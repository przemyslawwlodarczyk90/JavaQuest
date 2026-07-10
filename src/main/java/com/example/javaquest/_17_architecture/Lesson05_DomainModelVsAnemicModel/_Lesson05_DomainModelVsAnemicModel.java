package com.example.javaquest._17_architecture.Lesson05_DomainModelVsAnemicModel;

public class _Lesson05_DomainModelVsAnemicModel {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: MODEL ANEMICZNY VS BOGATY MODEL DOMENOWY ===");

        /*
         * ============================================================
         * 📦 ANEMICZNY MODEL DOMENOWY (ANEMIC DOMAIN MODEL)
         * ============================================================
         * - Martin Fowler w artykule "AnemicDomainModel" (2003) opisal i
         *   SKRYTYKOWAL WZORZEC, w ktorym encje (np. `Order`, `Account`)
         *   sa TYLKO workami na dane - maja pola + gettery + settery,
         *   ale ZERO wlasnego zachowania.
         * - CALA logika biznesowa ("czy zamowienie mozna wyslac?", "czy
         *   konto ma wystarczajace srodki?") zyje W OSOBNYCH klasach
         *   Service, ktore "z zewnatrz" czytaja i ustawiaja pola encji.
         * - Fowler: to "fundamentalne naruszenie podstawowej idei OOP -
         *   POLACZENIA danych i zachowania w 1 miejscu" - encja staje sie
         *   praktycznie strukturą danych (jak w programowaniu proceduralnym
         *   sprzed OOP), mimo ze technicznie jest "klasa".
         */
        demonstrateAnemicModel();

        /*
         * ============================================================
         * 🔍 DLACZEGO TO PROBLEM: NIC NIE CHRONI WEWNETRZNEJ SPOJNOSCI
         * ============================================================
         * - Skoro `OrderAnemic` ma publiczny setter `setStatus(...)`, TO
         *   NIC nie stoi na przeszkodzie, zeby JAKIS kod (byc moze zupelnie
         *   inny niz `OrderService`) ustawil status z "DOSTARCZONE" z
         *   powrotem na "OCZEKUJACE" - encja SAMA nie ma jak tego
         *   zabronic, bo NIE MA zadnej logiki, tylko pole.
         * - Regula "zamowienie moze przejsc z OCZEKUJACE do WYSLANE, ale
         *   NIE z WYSLANE z powrotem do OCZEKUJACE" musi byc wtedy
         *   POWTORZONA w KAZDYM miejscu, ktore zmienia status - dokladnie
         *   "Shotgun Surgery" (`_16_clean_code/Lesson14`).
         */
        demonstrateAnemicModelAllowsInvalidStateFromOutside();

        /*
         * ============================================================
         * 🔹 BOGATY MODEL DOMENOWY (RICH DOMAIN MODEL)
         * ============================================================
         * - W bogatym modelu encja SAMA chroni swoja spojnosc - pola sa
         *   `private` (bez publicznego settera), a zmiana stanu odbywa
         *   sie WYLACZNIE przez metody o NAZWACH z jezyka biznesu
         *   (`ship()`, `cancel()`), ktore SAME sprawdzaja, czy przejscie
         *   jest DOZWOLONE.
         * - To DOKLADNIE zasada "Tell, Don't Ask" z `_16_clean_code/
         *   Lesson12` zastosowana do calej encji: kod z zewnatrz nie PYTA
         *   "jaki jest status" zeby samemu zdecydowac - MOWI encji, CO ma
         *   zrobic, a ONA decyduje, czy to jest dozwolone.
         */
        demonstrateRichDomainModel();

        /*
         * ============================================================
         * 🔍 REGULA BIZNESOWA W 1 MIEJSCU, NIE ROZPROSZONA
         * ============================================================
         * - Poniewaz regula przejscia statusu zyje TERAZ wewnatrz `Order`
         *   (bogaty model), KAZDE miejsce w calym systemie, ktore probuje
         *   wyslac juz wyslane zamowienie, dostanie TEN SAM, spojny blad -
         *   nie trzeba pamietac o powtorzeniu walidacji w kazdym nowym
         *   miejscu.
         */
        demonstrateRichModelPreventsInvalidTransitionEverywhere();

        /*
         * ============================================================
         * 🔹 KIEDY ANEMICZNY MODEL JEST OK (NIE ZAWSZE TO BLAD)
         * ============================================================
         * - Dla PROSTYCH aplikacji CRUD (np. panel administracyjny bez
         *   realnych regul biznesowych, potok transformacji danych) model
         *   anemiczny NIE jest bledem - dodanie "bogatego" zachowania
         *   byloby niepotrzebna ceremonia (patrz YAGNI, `_16_clean_code/
         *   Lesson13`) tam, gdzie i tak nie ma czego chronic.
         * - Bogaty model domenowy oplaca sie TAM, gdzie SA realne reguly
         *   biznesowe do ochrony (niezmienniki, dozwolone przejscia stanu) -
         *   im wiecej takich regul, tym wieksza korzysc z ich
         *   "zamkniecia" wewnatrz encji.
         */
        System.out.println("\nModel anemiczny jest OK dla prostego CRUD bez regul - bogaty oplaca sie TAM, gdzie SA realne niezmienniki.");

        /*
         * ============================================================
         * 🔍 NAPIECIE Z FRAMEWORKAMI ORM (KROTKO, POGLEBIONE W _12_hibernate)
         * ============================================================
         * - Frameworki ORM (np. Hibernate, `_12_hibernate`) HISTORYCZNIE
         *   wymagaly bezargumentowego konstruktora i (czesto) publicznych
         *   getterow/setterow, co NATURALNIE ciagnie w strone modelu
         *   anemicznego.
         * - To NIE oznacza, ze encja JPA MUSI byc anemiczna - mozna miec
         *   PRYWATNY setter (albo brak settera w ogole, z polami
         *   ustawianymi TYLKO przez konstruktor/metody biznesowe) i DALEJ
         *   dodac metody takie jak `ship()` obok adnotacji `@Entity` -
         *   framework i bogaty model NIE WYKLUCZAJA sie nawzajem.
         */
        System.out.println("\nORM (Hibernate, _12_hibernate) NATURALNIE ciagnie w strone anemii, ale NIE WYKLUCZA bogatego modelu.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Model anemiczny (Fowler): encje = worki na dane, CALA logika w
         *   Service - narusza polaczenie danych i zachowania z OOP.
         * - Problem: nic nie chroni wewnetrznej spojnosci encji - regula
         *   musi byc powtorzona wszedzie, gdzie ktos modyfikuje dane.
         * - Bogaty model: encja SAMA chroni swoj stan przez metody
         *   biznesowe (Tell, Don't Ask, `_16_clean_code/Lesson12`).
         * - Model anemiczny jest OK dla prostego CRUD bez regul - bogaty
         *   oplaca sie tam, gdzie SA realne niezmienniki do ochrony.
         * - W kolejnej lekcji (Lesson06): Bounded Contexts - jak TEN SAM
         *   termin biznesowy (np. "Produkt") moze mieć INNY model (i inny
         *   zestaw regul) w roznych czesciach systemu.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void demonstrateAnemicModel() {
        System.out.println("\n=== MODEL ANEMICZNY: OrderAnemic = TYLKO DANE ===");

        OrderAnemic order = new OrderAnemic();
        order.setStatus("PENDING");

        System.out.println("Status: " + order.getStatus());
        System.out.println("Cala logika 'czy mozna wyslac' zyje W OrderService, NIE w OrderAnemic:");
        System.out.println(OrderServiceForAnemicModel.ship(order));
        System.out.println("Status po wyslaniu: " + order.getStatus());
    }

    /** ANEMICZNA encja - TYLKO pole + getter/setter, ZERO wlasnego zachowania. */
    static class OrderAnemic {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    /** Cala logika biznesowa zyje TUTAJ, "z zewnatrz" operujac na anemicznej encji. */
    static class OrderServiceForAnemicModel {
        static String ship(OrderAnemic order) {
            if (!"PENDING".equals(order.getStatus())) {
                return "BLAD: mozna wyslac TYLKO zamowienie w statusie PENDING";
            }
            order.setStatus("SHIPPED"); // Service SAM manipuluje polem encji
            return "Zamowienie wyslane";
        }
    }

    private static void demonstrateAnemicModelAllowsInvalidStateFromOutside() {
        System.out.println("\n=== PROBLEM: NIC NIE CHRONI SPOJNOSCI ANEMICZNEJ ENCJI ===");

        OrderAnemic order = new OrderAnemic();
        order.setStatus("SHIPPED");

        // ZADEN kod nie stoi na przeszkodzie - encja SAMA nie ma jak tego zabronic:
        order.setStatus("PENDING"); // cofniecie z 'wyslane' na 'oczekujace' - NIEDOZWOLONE biznesowo, ale mozliwe technicznie!
        System.out.println("Status PO 'cofnieciu' przez dowolny kod: " + order.getStatus());
        System.out.println("-> OrderAnemic NIE MA jak zabronic tej niedozwolonej zmiany - to TYLKO pole.");
    }

    private static void demonstrateRichDomainModel() {
        System.out.println("\n=== BOGATY MODEL: OrderRich CHRONI WLASNY STAN ===");

        OrderRich order = new OrderRich();
        System.out.println("Status: " + order.getStatus());

        order.ship(); // "Tell, Don't Ask" - mowimy encji CO zrobic, ona decyduje CZY to dozwolone
        System.out.println("Status po ship(): " + order.getStatus());

        try {
            order.ship(); // proba wyslania JUZ wyslanego zamowienia
        } catch (IllegalStateException e) {
            System.out.println("Proba ponownego ship() zlapana: " + e.getMessage());
        }
    }

    /** BOGATA encja - status `private`, BRAK publicznego settera, zmiana TYLKO przez metody biznesowe. */
    static class OrderRich {
        private String status = "PENDING";

        String getStatus() {
            return status;
        }

        void ship() {
            if (!"PENDING".equals(status)) {
                throw new IllegalStateException("Nie mozna wyslac zamowienia w statusie " + status + " - wymagany PENDING");
            }
            status = "SHIPPED";
        }

        void cancel() {
            if ("SHIPPED".equals(status)) {
                throw new IllegalStateException("Nie mozna anulowac juz wyslanego zamowienia");
            }
            status = "CANCELLED";
        }
    }

    private static void demonstrateRichModelPreventsInvalidTransitionEverywhere() {
        System.out.println("\n=== REGULA W 1 MIEJSCU - SPOJNA WSZEDZIE ===");

        OrderRich orderA = new OrderRich();
        orderA.ship();

        OrderRich orderB = new OrderRich();
        orderB.ship();

        // 2 NIEZALEZNE miejsca w kodzie probujace anulowac juz wyslane zamowienie -
        // OBA dostaja TEN SAM, spojny blad, bo regula zyje w 1 miejscu (OrderRich.cancel()):
        for (OrderRich order : java.util.List.of(orderA, orderB)) {
            try {
                order.cancel();
            } catch (IllegalStateException e) {
                System.out.println("  Proba anulowania wyslanego zamowienia: " + e.getMessage());
            }
        }
        System.out.println("-> Ta sama regula, 2 niezalezne wywolania, ZERO powielonego kodu walidujacego.");
    }
}
