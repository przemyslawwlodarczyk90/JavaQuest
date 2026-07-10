package com.example.javaquest._17_architecture.Lesson11_HexagonalArchitectureIntro;

public class _Lesson11_HexagonalArchitectureIntro {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: ARCHITEKTURA HEKSAGONALNA (PORTS AND ADAPTERS) ===");

        /*
         * ============================================================
         * 📦 ALISTAIR COCKBURN (2005) - "HEKSAGON" TO TYLKO METAFORA
         * ============================================================
         * - Alistair Cockburn opisal (2005) wzorzec "Ports and Adapters",
         *   spopularyzowany jako "Hexagonal Architecture".
         * - "Heksagon" (szesciokat) NIE MA znaczenia geometrycznego -
         *   Cockburn wybral go SWIADOMIE, zeby ZERWAC z intuicja
         *   tradycyjnych diagramow "warstwa na gorze, warstwa na dole"
         *   (jak w Lesson03). Szesc bokow sugeruje: "moze byc DOWOLNA
         *   liczba roznych sposobow wejscia/wyjscia z aplikacji, nie
         *   tylko 2 (gora/dol)".
         * - W SRODKU heksagonu: APLIKACJA (logika biznesowa, dokladnie
         *   ten sam "wewnetrzny krag" co Domena+Przypadki uzycia z
         *   Lesson10).
         */
        System.out.println("Heksagon = metafora 'wielu roznych stron', nie geometria - w srodku: aplikacja/logika biznesowa.");

        /*
         * ============================================================
         * 🔹 PORTY: DRIVING (PIERWOTNE) I DRIVEN (WTORNE)
         * ============================================================
         * - PORT to interfejs na GRANICY aplikacji. Cockburn rozroznia 2
         *   RODZAJE (symetrycznie, w OBIE strony):
         *   - PORT PIERWOTNY / "DRIVING" (napedzajacy) - sposob, w JAKI
         *     SWIAT ZEWNETRZNY URUCHAMIA aplikacje (np. interfejs
         *     `PlaceOrderUseCase`, wywolywany przez kontroler REST albo
         *     komende CLI).
         *   - PORT WTORNY / "DRIVEN" (napedzany) - sposob, w JAKI
         *     APLIKACJA korzysta Z CZEGOS na zewnatrz (np. interfejs
         *     `OrderRepository`, implementowany przez adapter bazy
         *     danych).
         * - To WLASNIE ta SYMETRIA (oba kierunki nazwane i traktowane
         *   tak samo jako "porty") jest GLOWNYM wkladem Cockburna ponad
         *   ogolna Dependency Rule z Lesson10.
         */
        System.out.println("\n2 rodzaje portow: DRIVING (jak swiat URUCHAMIA aplikacje) i DRIVEN (jak aplikacja UZYWA czegos na zewnatrz).");

        /*
         * ============================================================
         * 🔍 ADAPTERY: RozNE "WTYCZKI" DO TEGO SAMEGO PORTU
         * ============================================================
         * - Adapter to KONKRETNA implementacja/uzytkownik portu.
         * - Dla portu DRIVING (`PlaceOrderUseCase`) mozliwe adaptery:
         *   kontroler REST, obsluga komendy CLI, konsument kolejki
         *   wiadomosci - WSZYSTKIE "napedzaja" TA SAMA aplikacje przez
         *   TEN SAM port, roznymi "wtyczkami".
         * - Dla portu DRIVEN (`OrderRepository`) mozliwe adaptery: baza
         *   PostgreSQL, baza in-memory (do testow), plik CSV - aplikacja
         *   NIE WIE i NIE OBCHODZI jej, ktora "wtyczka" jest akurat
         *   podlaczona.
         */
        demonstrateMultipleDrivingAdaptersForSamePort();

        /*
         * ============================================================
         * 🔹 PODMIANA ADAPTERA WTORNEGO (DRIVEN) - BEZ DOTYKANIA APLIKACJI
         * ============================================================
         */
        demonstrateSwappingDrivenAdapter();

        /*
         * ============================================================
         * 🔍 HEXAGONAL VS CLEAN/ONION (LESSON10) - TA SAMA RODZINA IDEI
         * ============================================================
         * - Hexagonal (Cockburn, 2005), Onion (Palermo, 2008) i Clean
         *   Architecture (Martin, 2012/2017) to WARIANTY tej samej,
         *   fundamentalnej idei: logika biznesowa w SRODKU, izolowana od
         *   szczegolow technicznych przez interfejsy zdefiniowane PRZEZ
         *   NIA, implementowane NA ZEWNATRZ.
         * - Roznice sa GLOWNIE w TERMINOLOGII i NACISKU: Hexagonal
         *   podkresla SYMETRIE portow (driving/driven) i uzywa metafory
         *   "wtyczek", Clean/Onion podkreslaja KONCENTRYCZNE KREGI ze
         *   scisla hierarchia warstw.
         * - W PRAKTYCE: mozesz uzywac ktorejkolwiek terminologii (albo
         *   zadnej formalnie) - liczy sie PRZESTRZEGANIE tej samej
         *   zasady kierunku zaleznosci (Lesson10).
         */
        System.out.println("\nHexagonal/Onion/Clean = TA SAMA rodzina idei, rozna terminologia - liczy sie zasada, nie nazwa.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Heksagon to metafora "wielu stron", nie geometria - w
         *   srodku: aplikacja/logika biznesowa.
         * - Port DRIVING: jak swiat URUCHAMIA aplikacje. Port DRIVEN:
         *   jak aplikacja UZYWA czegos na zewnatrz - oba traktowane
         *   SYMETRYCZNIE.
         * - Adapter: konkretna "wtyczka" do portu - mozna miec WIELE
         *   adapterow dla 1 portu (REST i CLI dla tego samego use case;
         *   Postgres i in-memory dla tego samego repozytorium).
         * - Hexagonal/Onion/Clean to WARIANTY tej samej idei z Lesson10.
         * - W kolejnej lekcji (Lesson12): konkretna implementacja portow
         *   i adapterow w Javie - jak faktycznie ZORGANIZOWAC kod wg
         *   tego wzorca.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void demonstrateMultipleDrivingAdaptersForSamePort() {
        System.out.println("\n=== 2 ADAPTERY DRIVING (REST-podobny, CLI-podobny) DLA 1 PORTU ===");

        PlaceOrderUseCase useCase = new PlaceOrderService(new InMemoryOrderRepositoryAdapter());

        String restLikeResult = handleRestLikeRequest(useCase, "Ala Kowalska", 199.99);
        String cliLikeResult = handleCliLikeCommand(useCase, "Bartek Nowak", 49.99);

        System.out.println("Adapter REST-podobny: " + restLikeResult);
        System.out.println("Adapter CLI-podobny: " + cliLikeResult);
        System.out.println("-> OBA adaptery 'napedzaja' TA SAMA aplikacje przez TEN SAM port PlaceOrderUseCase.");
    }

    /** PORT DRIVING - jak swiat ZEWNETRZNY moze uruchomic aplikacje. Zdefiniowany WEWNATRZ (Lesson10: Dependency Rule). */
    interface PlaceOrderUseCase {
        String placeOrder(String customer, double amount);
    }

    /** PORT DRIVEN - jak aplikacja korzysta z czegos na zewnatrz (przechowywanie). */
    interface OrderRepositoryPort {
        void save(String customer, double amount);
    }

    /** APLIKACJA (srodek heksagonu) - implementuje port driving, zalezy TYLKO od portu driven. */
    static class PlaceOrderService implements PlaceOrderUseCase {
        private final OrderRepositoryPort repository;

        PlaceOrderService(OrderRepositoryPort repository) {
            this.repository = repository;
        }

        @Override
        public String placeOrder(String customer, double amount) {
            repository.save(customer, amount);
            return "Zamowienie zlozone dla " + customer + " na kwote " + amount;
        }
    }

    /** ADAPTER DRIVEN - konkretna "wtyczka" do portu OrderRepositoryPort (in-memory). */
    static class InMemoryOrderRepositoryAdapter implements OrderRepositoryPort {
        @Override
        public void save(String customer, double amount) {
            System.out.println("  [Adapter driven: in-memory] zapisano " + customer + " -> " + amount);
        }
    }

    /** ADAPTER DRIVING nr 1 - symulacja kontrolera REST, "napedza" aplikacje przez port PlaceOrderUseCase. */
    private static String handleRestLikeRequest(PlaceOrderUseCase useCase, String customer, double amount) {
        System.out.println("[Adapter driving: REST-podobny] odebrano zadanie POST /orders");
        return useCase.placeOrder(customer, amount);
    }

    /** ADAPTER DRIVING nr 2 - symulacja komendy CLI, TEN SAM port co adapter REST-podobny. */
    private static String handleCliLikeCommand(PlaceOrderUseCase useCase, String customer, double amount) {
        System.out.println("[Adapter driving: CLI-podobny] odebrano komende 'place-order'");
        return useCase.placeOrder(customer, amount);
    }

    private static void demonstrateSwappingDrivenAdapter() {
        System.out.println("\n=== PODMIANA ADAPTERA DRIVEN (BAZA IN-MEMORY -> 'ZEWNETRZNA') BEZ ZMIANY APLIKACJI ===");

        PlaceOrderUseCase useCaseWithExternalAdapter = new PlaceOrderService(new ExternalLikeOrderRepositoryAdapter());
        String result = handleRestLikeRequest(useCaseWithExternalAdapter, "Celina Wojcik", 89.50);

        System.out.println(result);
        System.out.println("-> PlaceOrderService (aplikacja) to DOKLADNIE ta sama klasa - nie wie, ze 'pod spodem'");
        System.out.println("   podmienilismy adapter in-memory na inny (symulowany 'zewnetrzny system').");
    }

    /** DRUGI adapter driven dla TEGO SAMEGO portu - aplikacja o tym nie wie. */
    static class ExternalLikeOrderRepositoryAdapter implements OrderRepositoryPort {
        @Override
        public void save(String customer, double amount) {
            System.out.println("  [Adapter driven: symulowany zewnetrzny system] wyslano " + customer + " -> " + amount);
        }
    }
}
