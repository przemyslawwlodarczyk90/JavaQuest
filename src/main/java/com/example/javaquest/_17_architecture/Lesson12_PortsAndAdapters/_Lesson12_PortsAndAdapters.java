package com.example.javaquest._17_architecture.Lesson12_PortsAndAdapters;

import java.util.List;
import java.util.function.Supplier;

public class _Lesson12_PortsAndAdapters {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: PORTY I ADAPTERY W PRAKTYCE ===");

        /*
         * ============================================================
         * 📦 LESSON11 = KONCEPCJA, TA LEKCJA = JAK TO REALNIE ZORGANIZOWAC
         * ============================================================
         * - Lesson11 wprowadzil porty (driving/driven) i adaptery -
         *   ta lekcja pokazuje PRAKTYCZNE detale: jak ULOZYC to w
         *   pakietach (Lesson09), jak TESTOWAC zgodnosc adapterow z
         *   portem (contract testing), i GDZIE w kodzie faktycznie
         *   "spina sie" wybor konkretnego adaptera (composition root).
         */
        System.out.println("Lesson11 = koncepcja portow/adapterow. Ta lekcja = jak to realnie zorganizowac w kodzie.");

        /*
         * ============================================================
         * 🔹 TYPOWA STRUKTURA PAKIETOW (LACZY SIE Z LESSON09)
         * ============================================================
         * - Powszechna konwencja nazewnicza pakietow (rozszerzenie
         *   package-by-feature z Lesson09): `in` dla adapterow driving,
         *   `out` dla adapterow driven.
         */
        System.out.println(PACKAGE_STRUCTURE);

        /*
         * ============================================================
         * 🔍 CONTRACT TESTING: TA SAMA "BATERIA TESTOW" DLA WSZYSTKICH ADAPTEROW
         * ============================================================
         * - Skoro WIELE adapterow implementuje TEN SAM port, powinny
         *   WSZYSTKIE zachowywac sie ZGODNIE Z TYM SAMYM kontraktem (np.
         *   "zapisany element MUSI byc odnajdywalny po ID zaraz po
         *   zapisie").
         * - "Contract test" (test kontraktowy) to 1 ZESTAW asercji
         *   URUCHAMIANY PONOWNIE dla KAZDEGO adaptera portu - gwarantuje,
         *   ze podmiana adaptera (in-memory na "zewnetrzny") NIE ZMIENIA
         *   OBSERWOWALNEGO zachowania.
         */
        demonstrateContractTestAcrossMultipleAdapters();

        /*
         * ============================================================
         * 🔹 COMPOSITION ROOT: GDZIE SPINA SIE WYBOR ADAPTEROW
         * ============================================================
         * - Cala reszta kodu (aplikacja, porty) NIGDY nie decyduje, KTORY
         *   konkretny adapter uzyc - to NARUSZALOBY DIP
         *   (`_16_clean_code/Lesson11`).
         * - Decyzja "ktory adapter z ktorym portem" zapada w JEDNYM
         *   miejscu - "composition root" (najczesciej: metoda `main()`
         *   albo konfiguracja kontenera DI, np. Guice z `_13_libraries/
         *   Lesson19-20`) - WSZYSTKIE `new KonkretnyAdapter()` zyja TAM,
         *   nigdzie indziej.
         */
        demonstrateCompositionRoot();

        /*
         * ============================================================
         * 🔍 ANTY-WZORZEC: "PRZECIEKAJACY ADAPTER" (LEAKY ADAPTER)
         * ============================================================
         * - Adapter powinien TYLKO TLUMACZYC miedzy swiatem zewnetrznym a
         *   portem - jesli adapter zaczyna zawierac WLASNA logike
         *   biznesowa (np. "jesli kwota > 1000, zastosuj specjalna
         *   regule"), to regula ta "ucieka" z aplikacji do adaptera -
         *   dokladnie ten sam blad co "gruby kontroler" (`_17_architecture/
         *   Lesson04`), teraz nazwany w jezyku portow/adapterow.
         */
        demonstrateLeakyAdapterAntiPattern();

        /*
         * ============================================================
         * 🔹 ILE PORTOW? UNIKAJ "PORTU NA KAZDA METODE"
         * ============================================================
         * - Port powinien grupowac SPOJNE operacje (podobnie jak
         *   projektowanie interfejsow w ogole - ISP, `_16_clean_code/
         *   Lesson10`) - NIE twórz osobnego portu dla KAZDEJ pojedynczej
         *   metody, ale TEZ nie wrzucaj WSZYSTKIEGO do 1 "boskiego" portu.
         * - Praktyczna heurystyka: 1 port driven na 1 ZEWNETRZNY SYSTEM/
         *   ZASOB (np. 1 port na baze danych zamowien, OSOBNY port na
         *   system platnosci) - 1 port driving na 1 SPOJNY przypadek
         *   uzycia (lub blisko powiazana ich grupe).
         */
        System.out.println("\nHeurystyka: 1 port driven na 1 zewnetrzny system/zasob, 1 port driving na 1 spojny przypadek uzycia.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Struktura pakietow: `application` (rdzen+porty), `adapter.in.*`
         *   (driving), `adapter.out.*` (driven) - rozszerzenie
         *   package-by-feature (Lesson09).
         * - Contract testing: 1 zestaw testow uruchamiany dla WSZYSTKICH
         *   adapterow tego samego portu - gwarancja spojnego zachowania.
         * - Composition root: JEDYNE miejsce, gdzie decyduje sie, ktory
         *   adapter z ktorym portem - `new` zyje TYLKO tam.
         * - Anty-wzorzec: przeciekajacy adapter - logika biznesowa w
         *   adapterze zamiast w aplikacji.
         * - Unikaj "portu na kazda metode" - grupuj spojne operacje (ISP).
         * - W kolejnej lekcji (Lesson13): granice transakcji - GDZIE w
         *   architekturze (heksagonalnej/warstwowej) powinna zyc granica
         *   "wszystko albo nic".
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static final String PACKAGE_STRUCTURE = """

            com.example.orders
              application/
                PlaceOrderUseCase.java        <- port DRIVING (interfejs)
                OrderRepositoryPort.java      <- port DRIVEN (interfejs)
                PlaceOrderService.java        <- APLIKACJA (implementuje port driving)
              adapter.in.web/
                OrderRestController.java      <- adapter DRIVING (uzywa portu driving)
              adapter.in.cli/
                OrderCliCommand.java          <- INNY adapter DRIVING (ten sam port)
              adapter.out.persistence/
                JdbcOrderRepositoryAdapter.java  <- adapter DRIVEN (implementuje port driven)
              adapter.out.persistence.inmemory/
                InMemoryOrderRepositoryAdapter.java  <- INNY adapter DRIVEN (ten sam port, do testow)
            """;

    interface OrderRepositoryPort {
        void save(String orderId);

        boolean existsById(String orderId);
    }

    private static void demonstrateContractTestAcrossMultipleAdapters() {
        System.out.println("\n=== CONTRACT TEST: TA SAMA 'BATERIA' DLA 2 ROZNYCH ADAPTEROW ===");

        OrderRepositoryPort inMemoryAdapter = new InMemoryOrderAdapter();
        OrderRepositoryPort simulatedExternalAdapter = new SimulatedExternalOrderAdapter();

        System.out.println("Adapter 'in-memory': " + (runRepositoryContractTest(inMemoryAdapter) ? "PASS" : "FAIL"));
        System.out.println("Adapter 'symulowany zewnetrzny': " + (runRepositoryContractTest(simulatedExternalAdapter) ? "PASS" : "FAIL"));
        System.out.println("-> TEN SAM test kontraktowy potwierdza, ze OBA adaptery zachowuja sie SPOJNIE z portem.");
    }

    /** Test kontraktowy - dziala dla DOWOLNEJ implementacji OrderRepositoryPort, bez wiedzy, ktora to konkretnie. */
    private static boolean runRepositoryContractTest(OrderRepositoryPort adapter) {
        String testOrderId = "ORD-TEST-1";
        if (adapter.existsById(testOrderId)) {
            return false; // kontrakt: swiezy adapter NIE powinien miec tego ID przed zapisem
        }
        adapter.save(testOrderId);
        return adapter.existsById(testOrderId); // kontrakt: PO zapisie, ID MUSI byc odnajdywalne
    }

    static class InMemoryOrderAdapter implements OrderRepositoryPort {
        private final List<String> savedIds = new java.util.ArrayList<>();

        @Override
        public void save(String orderId) {
            savedIds.add(orderId);
        }

        @Override
        public boolean existsById(String orderId) {
            return savedIds.contains(orderId);
        }
    }

    static class SimulatedExternalOrderAdapter implements OrderRepositoryPort {
        private final java.util.Set<String> remoteIds = new java.util.HashSet<>();

        @Override
        public void save(String orderId) {
            remoteIds.add(orderId);
        }

        @Override
        public boolean existsById(String orderId) {
            return remoteIds.contains(orderId);
        }
    }

    private static void demonstrateCompositionRoot() {
        System.out.println("\n=== COMPOSITION ROOT: 1 MIEJSCE DECYDUJE, KTORY ADAPTER UZYC ===");

        // Ten fragment TO WLASNIE composition root - JEDYNE miejsce z 'new'
        // dla konkretnych adapterow. Reszta aplikacji (Service) o tym nie wie.
        OrderRepositoryPort chosenAdapter = resolveAdapterForEnvironment(() -> "production");
        System.out.println("Wybrany adapter (na podstawie 'srodowiska'): " + chosenAdapter.getClass().getSimpleName());
    }

    private static OrderRepositoryPort resolveAdapterForEnvironment(Supplier<String> environmentProvider) {
        String environment = environmentProvider.get();
        return "production".equals(environment) ? new SimulatedExternalOrderAdapter() : new InMemoryOrderAdapter();
    }

    private static void demonstrateLeakyAdapterAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: PRZECIEKAJACY ADAPTER Z WLASNA LOGIKA BIZNESOWA ===");

        System.out.println("[ZLA] Adapter SAM decyduje o rabacie (logika biznesowa w adapterze):");
        LeakyPricingAdapter leaky = new LeakyPricingAdapter();
        System.out.println("  " + leaky.fetchPrice("SKU-1", 15));

        System.out.println("[DOBRA] Adapter TYLKO pobiera surowa cene - rabat liczy aplikacja (Service):");
        CleanPricingAdapter clean = new CleanPricingAdapter();
        double rawPrice = clean.fetchRawPrice("SKU-1");
        double finalPrice = applyBulkDiscountInApplication(rawPrice, 15);
        System.out.println("  Surowa cena z adaptera: " + rawPrice + ", cena PO logice biznesowej aplikacji: " + finalPrice);
    }

    /** ZLA: adapter zawiera WLASNA regule biznesowa (rabat ilosciowy) - "przecieka" logike z aplikacji. */
    static class LeakyPricingAdapter {
        double fetchPrice(String sku, int quantity) {
            double basePrice = 100.0;
            return quantity >= 10 ? basePrice * 0.9 : basePrice; // regula biznesowa NIE POWINNA tu byc!
        }
    }

    /** DOBRA: adapter TYLKO tlumaczy (pobiera surowa cene) - logika biznesowa zostaje w aplikacji. */
    static class CleanPricingAdapter {
        double fetchRawPrice(String sku) {
            return 100.0;
        }
    }

    private static double applyBulkDiscountInApplication(double basePrice, int quantity) {
        return quantity >= 10 ? basePrice * 0.9 : basePrice;
    }
}
