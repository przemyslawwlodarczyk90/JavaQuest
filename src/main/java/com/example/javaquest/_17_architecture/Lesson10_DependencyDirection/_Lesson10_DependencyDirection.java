package com.example.javaquest._17_architecture.Lesson10_DependencyDirection;

public class _Lesson10_DependencyDirection {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: KIERUNEK ZALEZNOSCI (DEPENDENCY RULE) ===");

        /*
         * ============================================================
         * 📦 RECAP: DIP NA POZIOMIE KLASY (`_16_clean_code/Lesson11`)
         * ============================================================
         * - DIP (Dependency Inversion Principle) mowil: modul
         *   wysokopoziomowy i niskopoziomowy zalezna OBA od abstrakcji -
         *   nie logika biznesowa wprost od szczegolu technicznego.
         * - Ta lekcja BIERZE dokladnie ta sama idee i skaluje ja na
         *   CALA ARCHITEKTURE - to jest "Dependency Rule" z "Clean
         *   Architecture" Roberta C. Martina (2017), analogiczna do
         *   "Onion Architecture" Jeffreya Palermo (2008).
         */
        System.out.println("Dependency Rule = DIP (_16_clean_code/Lesson11) zastosowany do CALEJ architektury, nie 1 klasy.");

        /*
         * ============================================================
         * 🔹 KONCENTRYCZNE KREGI: OD DOMENY DO SZCZEGOLOW TECHNICZNYCH
         * ============================================================
         * - Wyobraz sobie architekture jako kregi koncentryczne:
         *   1) DOMENA (Entities) - w SAMYM SRODKU, najbardziej stabilna,
         *      czysta logika biznesowa (Lesson05: bogaty model).
         *   2) PRZYPADKI UZYCIA (Use Cases) - orkiestracja logiki
         *      biznesowej (odpowiednik Service, Lesson04).
         *   3) ADAPTERY (Controllers/Prezentery/Gatewaye) - tlumaczenie
         *      miedzy przypadkami uzycia a swiatem zewnetrznym.
         *   4) FRAMEWORKI I NARZEDZIA (bazy danych, UI, biblioteki) -
         *      NAJBARDZIEJ zewnetrzny, najbardziej zmienny krag.
         * - GLOWNA ZASADA (Dependency Rule): zaleznosci w KODZIE ZRODLOWYM
         *   moga wskazywac TYLKO DO SRODKA. Nic w kregu WEWNETRZNYM nie
         *   moze wiedziec NIC o kregu ZEWNETRZNYM.
         */
        System.out.println(CONCENTRIC_CIRCLES_DIAGRAM);

        /*
         * ============================================================
         * 🔍 DLACZEGO AKURAT TAK: STABILNE WEWNATRZ, ZMIENNE NA ZEWNATRZ
         * ============================================================
         * - Krag ZEWNETRZNY (baza danych, framework webowy, UI) to
         *   dokladnie te "kosztowne do odwrocenia" decyzje z Lesson01 -
         *   ale ROWNIEZ te NAJBARDZIEJ podatne na zmiane technologiczna
         *   w czasie (nowa baza danych, nowa wersja frameworka).
         * - Krag WEWNETRZNY (logika biznesowa) to WARTOSC, dla ktorej
         *   system w ogole istnieje - regula "przechowaj zamowienie" nie
         *   zmienia sie, nawet gdy zmienia sie baza danych POD SPODEM.
         * - Kierujac zaleznosci DO SRODKA, chronimy NAJWARTOSCIOWSZY i
         *   NAJSTABILNIEJSZY kod przed koniecznoscia zmiany, gdy zmienia
         *   sie NAJMNIEJ stabilny, zewnetrzny szczegol.
         */
        System.out.println("\nStabilne (domena) w srodku, zmienne (framework/baza) na zewnatrz - zaleznosci ZAWSZE do srodka.");

        /*
         * ============================================================
         * 🔹 JAK TO TECHNICZNIE DZIALA: INTERFEJS ZDEFINIOWANY WEWNATRZ
         * ============================================================
         * - Sztuczka (dokladnie DIP): interfejs (np. `PaymentGateway`)
         *   jest ZDEFINIOWANY w kregu WEWNETRZNYM (przypadki uzycia), a
         *   IMPLEMENTOWANY w kregu ZEWNETRZNYM (adapter dla konkretnego
         *   dostawcy platnosci).
         * - Zaleznosc W KODZIE ZRODLOWYM (`implements PaymentGateway`)
         *   "wskazuje" z zewnatrz DO SRODKA (adapter zna interfejs
         *   domeny) - mimo ze w RUNTIME wywolanie "leci" Z domeny NA
         *   ZEWNATRZ (przez wstrzykniety obiekt). To WLASNIE nazywa sie
         *   "odwroceniem zaleznosci" - kierunek KOMPILACJI/IMPORTU jest
         *   PRZECIWNY do kierunku WYWOLANIA w czasie dzialania.
         */
        demonstrateDependencyRuleWithPaymentGateway();

        /*
         * ============================================================
         * 🔍 ANTY-WZORZEC: DOMENA IMPORTUJACA SZCZEGOL TECHNICZNY
         * ============================================================
         * - Naruszenie Dependency Rule: klasa domenowa (np. `Order`)
         *   BEZPOSREDNIO odwoluje sie do typu specyficznego dla
         *   frameworka/bazy danych (np. adnotacji JPA, `java.sql.
         *   ResultSet`, typu z biblioteki HTTP).
         * - Skutek: zeby SKOMPILOWAC "czysta" logike biznesowa, MUSISZ
         *   miec na classpath CALY framework/baze danych - logika
         *   biznesowa PRZESTAJE byc niezalezna (Lesson01), mimo ze
         *   "wyglada" jak zwykla klasa Javy.
         */
        demonstrateDependencyRuleViolation();

        /*
         * ============================================================
         * 🔹 ONION ARCHITECTURE - TA SAMA IDEA, INNA NAZWA
         * ============================================================
         * - Jeffrey Palermo (2008) opisal BARDZO podobny model pod nazwa
         *   "Onion Architecture" (architektura cebulowa) - rdzen
         *   domenowy w srodku, kolejne warstwy (Domain Services,
         *   Application Services) wokol niego, INFRASTRUKTURA/UI na
         *   samym zewnatrz.
         * - Roznice miedzy Clean Architecture a Onion Architecture sa
         *   GLOWNIE terminologiczne - GLOWNA zasada (zaleznosci do
         *   srodka) jest IDENTYCZNA w obu. W kolejnych lekcjach
         *   (Lesson11-12: Hexagonal/Ports and Adapters) poznasz JESZCZE
         *   1 wariant tej samej rodziny idei.
         */
        System.out.println("\nOnion Architecture (Palermo) = TA SAMA zasada co Clean Architecture (Martin), inna terminologia.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Dependency Rule = DIP (`_16_clean_code/Lesson11`) zastosowany
         *   do CALEJ architektury: zaleznosci ZAWSZE do srodka (domena).
         * - Kregi: Domena -> Przypadki uzycia -> Adaptery -> Frameworki/
         *   Narzedzia - stabilne w srodku, zmienne na zewnatrz.
         * - Technicznie: interfejs zdefiniowany WEWNATRZ, implementowany
         *   NA ZEWNATRZ - kierunek importu przeciwny do kierunku wywolania.
         * - Anty-wzorzec: domena importujaca szczegol techniczny (JPA/
         *   JDBC/HTTP) - lamie niezaleznosc logiki biznesowej.
         * - Onion Architecture (Palermo) = ta sama zasada, inna nazwa.
         * - W kolejnej lekcji (Lesson11): Hexagonal Architecture (Ports
         *   and Adapters) - KONKRETNY, praktyczny wzorzec realizujacy
         *   Dependency Rule.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static final String CONCENTRIC_CIRCLES_DIAGRAM = """

              [ Frameworki / Narzedzia (baza danych, web, UI) ]   <- najbardziej ZEWNETRZNY, najbardziej ZMIENNY
                 [ Adaptery (Controller / Prezenter / Gateway) ]
                    [ Przypadki uzycia (Use Cases / Service) ]
                       [ DOMENA (Entities) ]                      <- najbardziej WEWNETRZNY, najbardziej STABILNY

              Zaleznosci w kodzie zrodlowym: ZAWSZE ze ZEWNATRZ DO SRODKA, NIGDY odwrotnie.
            """;

    private static void demonstrateDependencyRuleWithPaymentGateway() {
        System.out.println("\n=== INTERFEJS ZDEFINIOWANY WEWNATRZ, IMPLEMENTOWANY NA ZEWNATRZ ===");

        // Adapter (krag ZEWNETRZNY) IMPLEMENTUJE interfejs zdefiniowany
        // w kregu WEWNETRZNYM - import "wskazuje" z zewnatrz do srodka.
        PaymentGateway gateway = new StripeLikeAdapter();
        CheckoutUseCase useCase = new CheckoutUseCase(gateway);

        String result = useCase.checkout(199.99);
        System.out.println(result);
        System.out.println("-> CheckoutUseCase (krag wewnetrzny) zna TYLKO interfejs PaymentGateway (tez wewnetrzny).");
        System.out.println("   StripeLikeAdapter (krag zewnetrzny) IMPLEMENTUJE ten interfejs - import wskazuje DO SRODKA.");
    }

    /** DOMENA/PRZYPADEK UZYCIA (krag wewnetrzny) - definiuje WLASNA abstrakcje, nie zna ZADNEGO konkretnego dostawcy. */
    interface PaymentGateway {
        boolean charge(double amount);
    }

    static class CheckoutUseCase {
        private final PaymentGateway paymentGateway;

        CheckoutUseCase(PaymentGateway paymentGateway) {
            this.paymentGateway = paymentGateway;
        }

        String checkout(double amount) {
            boolean success = paymentGateway.charge(amount);
            return success ? "Platnosc " + amount + " zaakceptowana" : "Platnosc odrzucona";
        }
    }

    /** ADAPTER (krag zewnetrzny) - IMPLEMENTUJE interfejs zdefiniowany przez wewnetrzny przypadek uzycia. */
    static class StripeLikeAdapter implements PaymentGateway {
        @Override
        public boolean charge(double amount) {
            System.out.println("  [Adapter zewnetrzny] Wywolanie 'API dostawcy platnosci' dla kwoty " + amount);
            return amount > 0;
        }
    }

    private static void demonstrateDependencyRuleViolation() {
        System.out.println("\n=== ANTY-WZORZEC: DOMENA ZALEZNA OD SZCZEGOLU TECHNICZNEGO ===");

        OrderViolatingDependencyRule badOrder = new OrderViolatingDependencyRule("ORD-1", "PENDING_STATUS_CODE_1");
        System.out.println("[ZLA] " + badOrder.describe());
        System.out.println("-> Pole 'statusFromDatabaseColumn' NAZWANE i UDOKUMENTOWANE jak szczegol bazy danych -");
        System.out.println("   domena 'wie' zbyt duzo o tym, JAK dane sa przechowywane (nawet bez importu klasy JDBC).");

        OrderRespectingDependencyRule goodOrder = new OrderRespectingDependencyRule("ORD-1", OrderStatus.PENDING);
        System.out.println("[DOBRA] " + goodOrder.describe());
        System.out.println("-> OrderStatus to WLASNY enum domeny - ZERO wiedzy o tym, jak status jest zapisany w bazie.");
    }

    /** ZLA: nazwa pola i komentarz zdradzaja, ze domena "mysli" w kategoriach kolumny bazy danych. */
    static class OrderViolatingDependencyRule {
        private final String orderId;
        private final String statusFromDatabaseColumn; // nazwa ZDRADZA zaleznosc od szczegolu przechowywania

        OrderViolatingDependencyRule(String orderId, String statusFromDatabaseColumn) {
            this.orderId = orderId;
            this.statusFromDatabaseColumn = statusFromDatabaseColumn;
        }

        String describe() {
            return orderId + " -> " + statusFromDatabaseColumn;
        }
    }

    enum OrderStatus {
        PENDING, SHIPPED, CANCELLED
    }

    /** DOBRA: domena uzywa WLASNEGO, czystego pojecia (enum) - zero wiedzy o warstwie przechowywania. */
    static class OrderRespectingDependencyRule {
        private final String orderId;
        private final OrderStatus status;

        OrderRespectingDependencyRule(String orderId, OrderStatus status) {
            this.orderId = orderId;
            this.status = status;
        }

        String describe() {
            return orderId + " -> " + status;
        }
    }
}
