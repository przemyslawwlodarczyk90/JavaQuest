package com.example.javaquest._16_clean_code.Lesson12_CouplingCohesionAndLawOfDemeter;

public class _Lesson12_CouplingCohesionAndLawOfDemeter {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: SPRZEZENIE, SPOJNOSC I PRAWO DEMETER ===");

        /*
         * ============================================================
         * 📦 SPRZEZENIE (COUPLING) - ILE JEDNA KLASA "WIE" O DRUGIEJ
         * ============================================================
         * - Sprzezenie (coupling) mierzy, jak SILNIE jedna klasa zalezy od
         *   WEWNETRZNYCH szczegolow drugiej. WYSOKIE sprzezenie = zmiana w
         *   klasie A wymusza zmiane w klasie B (i odwrotnie) - klasy sa
         *   "sklejone".
         * - Cel: LUZNE sprzezenie (loose coupling) - klasy wspoldzialaja
         *   przez WASKIE, stabilne API (najlepiej interfejsy - patrz
         *   Lesson11: DIP), NIE przez znajomosc wzajemnych szczegolow
         *   implementacji.
         * - Sprzezenie i spojnosc (Lesson06) to DWIE STRONY tego samego
         *   celu projektowego: chcemy WYSOKIEJ spojnosci WEWNATRZ klasy
         *   (metody uzywaja wlasnych pol) i LUZNEGO sprzezenia MIEDZY
         *   klasami (klasy nie grzebia we wzajemnych szczegolach).
         */
        demonstrateTightVsLooseCoupling();

        /*
         * ============================================================
         * 🔍 PRAWO DEMETER ("ZASADA NAJMNIEJSZEJ WIEDZY")
         * ============================================================
         * - Sformulowane na Northeastern University (projekt "Demeter",
         *   1987): obiekt powinien rozmawiac TYLKO ze swoimi
         *   "bezposrednimi znajomymi" - NIE z "znajomymi znajomych".
         * - Metoda obiektu X moze bezpiecznie wywolywac metody: (1) samego
         *   X, (2) obiektow przekazanych jako PARAMETRY tej metody, (3)
         *   obiektow utworzonych WEWNATRZ tej metody, (4) pol WLASNYCH
         *   obiektu X.
         * - Potocznie: "kropka, kropka, kropka" (`a.getB().getC().getD()`)
         *   to sygnal naruszenia - kazda kolejna kropka to kolejny
         *   "znajomy znajomego", o ktorym kod NIE powinien wiedziec.
         */
        System.out.println("\nPrawo Demeter: 'rozmawiaj tylko z bezposrednimi znajomymi', nie z 'znajomymi znajomych'.");

        /*
         * ============================================================
         * 🔹 "TRAIN WRECK" (POCIAG WRAKOW) - LANCUCH WYWOLAN GETTEROW
         * ============================================================
         * - Klasyczny przyklad naruszenia: `order.getCustomer().getAddress()
         *   .getCity()` - kod, ktory to wywoluje, MUSI znac WEWNETRZNA
         *   strukture az 3 roznych klas (Order, Customer, Address).
         * - Problem: jesli struktura Customer/Address kiedykolwiek sie
         *   zmieni (np. Address przestanie byc bezposrednim polem
         *   Customer), TRZEBA zmienic KAZDE miejsce w kodzie, ktore
         *   zawiera taki lancuch - to jest dokladnie "Shotgun Surgery" z
         *   Lesson14 (Code Smells).
         */
        demonstrateTrainWreckViolation();

        /*
         * ============================================================
         * 📌 NAPRAWA: METODY DELEGUJACE ("TELL, DON'T ASK")
         * ============================================================
         * - Zasada "Tell, Don't Ask" (Alec Sharp): zamiast PYTAC obiekt o
         *   jego wewnetrzne dane, zeby SAMEMU cos z nimi policzyc/zrobic
         *   ("ask") - POWIEDZ obiektowi, CO ma zrobic, i niech SAM
         *   uzyje swoich danych ("tell").
         * - W praktyce: kazda klasa w lancuchu dostaje WLASNA metode
         *   delegujaca do nastepnego poziomu (`order.getCustomerCity()`
         *   wewnatrz woluje `customer.getCity()`, ktora wewnatrz woluje
         *   `address.getCity()`) - kod ZEWNETRZNY zna TYLKO `order`, NIE
         *   zna istnienia Customer/Address.
         */
        demonstrateTellDontAskFix();

        /*
         * ============================================================
         * 🔍 WYJATEK: OBIEKTY DTO/DANE STRUKTURALNE (BEZ ZACHOWANIA)
         * ============================================================
         * - Prawo Demeter dotyczy przede wszystkim obiektow z ZACHOWANIEM
         *   (logika biznesowa) - proste struktury danych bez logiki (DTO,
         *   `record`y, np. `_09_jdbc/Lesson19_Dto`) sa CZESTO wyjatkiem,
         *   bo ich JEDYNYM celem jest przenoszenie danych, nie ukrywanie
         *   szczegolow implementacji.
         * - `zamowienie.getAdres().getMiasto()` na prostych rekordach DTO
         *   jest wiec CZESTO akceptowalne - ale jesli te dane sa
         *   uzywane przez logike BIZNESOWA (nie tylko np. do
         *   serializacji JSON), warto rozwazyc metode delegujaca.
         */
        System.out.println("\nWyjatek: proste DTO/rekordy bez logiki - lancuch getterow tam jest mniej problematyczny.");

        /*
         * ============================================================
         * 📌 SPOJNOSC (COHESION) - PRZYPOMNIENIE I POGLEBIENIE
         * ============================================================
         * - Lesson06 wprowadzil spojnosc na poziomie POJEDYNCZEJ klasy
         *   (metody uzywaja wiekszosci pol). Tutaj laczymy to z
         *   SPRZEZENIEM: klasa o WYSOKIEJ spojnosci WEWNETRZNEJ naturalnie
         *   potrzebuje MNIEJ danych z ZEWNATRZ - a wiec generuje mniejsze,
         *   luzniejsze sprzezenie z innymi klasami.
         * - Odwrotnie: NISKA spojnosc (np. Feature Envy z Lesson14) czesto
         *   IDZIE W PARZE z wysokim sprzezeniem - metoda "zazdroszczaca"
         *   danym innej klasy siega po nie glebokim lancuchem wywolan.
         */
        demonstrateCohesionAndCouplingRelationship();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Sprzezenie: ile klasa A "wie" o wewnetrznych szczegolach klasy
         *   B - celem jest LUZNE sprzezenie (przez interfejsy - Lesson11).
         * - Prawo Demeter: rozmawiaj tylko z bezposrednimi "znajomymi" -
         *   unikaj lancuchow `a.getB().getC().getD()` ("train wreck").
         * - Naprawa: Tell, Don't Ask - metody delegujace zamiast pytania o
         *   wewnetrzne dane obcych obiektow.
         * - Wysoka spojnosc wewnatrz klasy (Lesson06) i luzne sprzezenie
         *   miedzy klasami to 2 strony tego samego celu projektowego.
         * - W kolejnej lekcji (Lesson13): DRY, KISS, YAGNI - kolejny zestaw
         *   fundamentalnych zasad projektowych, tym razem skupiony na
         *   UNIKANIU zbednej zlozonosci i powielania.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static void demonstrateTightVsLooseCoupling() {
        System.out.println("\n=== WYSOKIE SPRZEZENIE VS LUZNE SPRZEZENIE ===");

        TightlyCoupledOrderProcessor tight = new TightlyCoupledOrderProcessor();
        System.out.println("[WYSOKIE SPRZEZENIE] " + tight.processOrder(100.0));
        System.out.println("-> OrderProcessor zna WEWNETRZNE pola MySqlTaxTable (bezposredni dostep, brak abstrakcji).");

        LooselyCoupledOrderProcessor loose = new LooselyCoupledOrderProcessor(new StandardTaxCalculator());
        System.out.println("[LUZNE SPRZEZENIE] " + loose.processOrder(100.0));
        System.out.println("-> OrderProcessor zna TYLKO interfejs TaxCalculator - szczegoly liczenia podatku sa ukryte.");
    }

    /** ZLA: OrderProcessor siega BEZPOSREDNIO do WEWNETRZNEGO pola innej klasy - wysokie sprzezenie. */
    static class MySqlTaxTable {
        double vatRatePercent = 23.0; // publiczne pole - inne klasy moga siegac wprost
    }

    static class TightlyCoupledOrderProcessor {
        private final MySqlTaxTable taxTable = new MySqlTaxTable();

        String processOrder(double basePrice) {
            // Bezposredni dostep do pola CUDZEJ klasy - kazda zmiana struktury
            // MySqlTaxTable (np. przemianowanie pola) zepsuje TEN kod.
            double total = basePrice * (1 + taxTable.vatRatePercent / 100);
            return String.format("Suma z podatkiem (wysokie sprzezenie): %.2f", total);
        }
    }

    /** DOBRA: OrderProcessor zalezy TYLKO od waskiego interfejsu - luzne sprzezenie (por. DIP, Lesson11). */
    interface TaxCalculator {
        double calculateTax(double basePrice);
    }

    static class StandardTaxCalculator implements TaxCalculator {
        @Override
        public double calculateTax(double basePrice) {
            return basePrice * 0.23;
        }
    }

    static class LooselyCoupledOrderProcessor {
        private final TaxCalculator taxCalculator;

        LooselyCoupledOrderProcessor(TaxCalculator taxCalculator) {
            this.taxCalculator = taxCalculator;
        }

        String processOrder(double basePrice) {
            double total = basePrice + taxCalculator.calculateTax(basePrice);
            return String.format("Suma z podatkiem (luzne sprzezenie): %.2f", total);
        }
    }

    private static void demonstrateTrainWreckViolation() {
        System.out.println("\n=== NARUSZENIE: 'TRAIN WRECK' - LANCUCH order.getCustomer().getAddress().getCity() ===");

        Address address = new Address("Krakow");
        Customer customer = new Customer("Ala Kowalska", address);
        Order order = new Order(customer);

        // Kod ZEWNETRZNY musi znac WEWNETRZNA strukture Order -> Customer -> Address:
        String city = order.getCustomer().getAddress().getCity();
        System.out.println("[ZLA] Miasto klienta (przez lancuch 3 getterow): " + city);
        System.out.println("-> Ten kod 'wie' o istnieniu Customer I Address - narusza Prawo Demeter.");
    }

    static class Address {
        private final String city;

        Address(String city) {
            this.city = city;
        }

        String getCity() {
            return city;
        }
    }

    static class Customer {
        private final String name;
        private final Address address;

        Customer(String name, Address address) {
            this.name = name;
            this.address = address;
        }

        Address getAddress() {
            return address;
        }
    }

    static class Order {
        private final Customer customer;

        Order(Customer customer) {
            this.customer = customer;
        }

        Customer getCustomer() {
            return customer;
        }
    }

    private static void demonstrateTellDontAskFix() {
        System.out.println("\n=== NAPRAWA: METODY DELEGUJACE (TELL, DON'T ASK) ===");

        AddressGood address = new AddressGood("Krakow");
        CustomerGood customer = new CustomerGood("Ala Kowalska", address);
        OrderGood order = new OrderGood(customer);

        // Kod zewnetrzny zna TYLKO 'order' - NIE wie NIC o Customer/Address:
        String city = order.getCustomerCity();
        System.out.println("[DOBRA] Miasto klienta (przez 1 metode delegujaca): " + city);
        System.out.println("-> order.getCustomerCity() 'mowi' Order co zrobic - Order sam pyta Customer, Customer sam pyta Address.");
    }

    static class AddressGood {
        private final String city;

        AddressGood(String city) {
            this.city = city;
        }

        String getCity() {
            return city;
        }
    }

    static class CustomerGood {
        private final String name;
        private final AddressGood address;

        CustomerGood(String name, AddressGood address) {
            this.name = name;
            this.address = address;
        }

        // Metoda delegujaca - Order pyta TYLKO Customer, nie zna istnienia Address.
        String getCity() {
            return address.getCity();
        }
    }

    static class OrderGood {
        private final CustomerGood customer;

        OrderGood(CustomerGood customer) {
            this.customer = customer;
        }

        // Kod zewnetrzny pyta TYLKO Order - jedyny "bezposredni znajomy".
        String getCustomerCity() {
            return customer.getCity();
        }
    }

    private static void demonstrateCohesionAndCouplingRelationship() {
        System.out.println("\n=== ZWIAZEK: WYSOKA SPOJNOSC WEWNATRZ -> LUZNE SPRZEZENIE NA ZEWNATRZ ===");

        InvoiceLine line = new InvoiceLine("Laptop", 3000.0, 0.23);
        System.out.println("InvoiceLine (wysoka spojnosc - obie metody uzywaja WSZYSTKICH pol): " + line.describe());
        System.out.println("Kod zewnetrzny potrzebuje TYLKO describe()/getTotalWithTax() - nie musi znac");
        System.out.println("wewnetrznych pol (name/basePrice/taxRate) wcale - to jest LUZNE sprzezenie z zewnatrz.");
    }

    static class InvoiceLine {
        private final String name;
        private final double basePrice;
        private final double taxRate;

        InvoiceLine(String name, double basePrice, double taxRate) {
            this.name = name;
            this.basePrice = basePrice;
            this.taxRate = taxRate;
        }

        double getTotalWithTax() {
            return basePrice * (1 + taxRate);
        }

        String describe() {
            return name + ": " + String.format("%.2f", getTotalWithTax());
        }
    }
}
