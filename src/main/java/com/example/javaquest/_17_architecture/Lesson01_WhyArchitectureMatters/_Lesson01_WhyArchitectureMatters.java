package com.example.javaquest._17_architecture.Lesson01_WhyArchitectureMatters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _Lesson01_WhyArchitectureMatters {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: PO CO W OGOLE ARCHITEKTURA? ===");

        /*
         * ============================================================
         * 📦 CZYM RÓŻNI SIĘ "ARCHITEKTURA" OD "CZYSTEGO KODU"?
         * ============================================================
         * - Rozdział `_16_clean_code` uczył zasad na poziomie POJEDYNCZEJ
         *   metody/klasy - jak nazwać zmienną, jak rozbić metodę, jak
         *   zaprojektować 1 hierarchię klas (SOLID).
         * - Architektura to te SAME idee (odpowiedzialność, sprzężenie,
         *   kierunek zależności), ale zastosowane na poziomie CAŁEJ
         *   APLIKACJI - jak zorganizować SETKI klas w spójną całość, jak
         *   podzielić system na warstwy/moduły, gdzie mają przebiegać
         *   GRANICE między częściami systemu.
         * - Prosta analogia: czysty kod to jakość POJEDYNCZEJ cegły,
         *   architektura to PROJEKT całego budynku - można mieć idealne
         *   cegły i fatalny projekt budynku (i odwrotnie).
         */
        System.out.println("Clean Code = jakosc POJEDYNCZEJ klasy/metody. Architektura = organizacja CALEJ aplikacji.");

        /*
         * ============================================================
         * 🔹 KOSZT ZMIANY ROSNIE Z CZASEM BEZ ARCHITEKTURY
         * ============================================================
         * - Bez swiadomej architektury system naturalnie dryfuje w strone
         *   tzw. "Big Ball of Mud" (termin z artykulu Fostera i Hylanda,
         *   1997) - kod, w ktorym WSZYSTKO zalezy od WSZYSTKIEGO, bez
         *   wyraznych granic.
         * - W takim systemie KAZDA, nawet drobna zmiana (np. "zmienmy
         *   sposob liczenia rabatu") wymaga zrozumienia i dotkniecia
         *   fragmentow rozsianych po calym kodzie - dokladnie "Shotgun
         *   Surgery" z `_16_clean_code/Lesson14`, ale na skale calej
         *   aplikacji, nie 1 metody.
         */
        demonstrateBigBallOfMud();

        /*
         * ============================================================
         * 🔍 DOBRA ARCHITEKTURA: ZMIANA IZOLOWANA DO 1 MIEJSCA
         * ============================================================
         * - W systemie z wyraznymi granicami (choc TA SAMA funkcjonalnosc
         *   co wyzej) zmiana logiki rabatu dotyka TYLKO 1 klasy - reszta
         *   systemu (wyswietlanie, "zapis do bazy") nie musi wiedziec, ze
         *   cokolwiek sie zmienilo.
         */
        demonstrateWellSeparatedSystem();

        /*
         * ============================================================
         * 🔹 ARCHITEKTURA = DECYZJE TRUDNE DO ODWROCENIA
         * ============================================================
         * - Nie kazda decyzja projektowa jest "architektoniczna" - zmiana
         *   nazwy zmiennej albo algorytmu sortowania wewnatrz 1 metody
         *   jest TANIA do cofniecia (Extract Method/Rename, `_16_clean_code
         *   /Lesson16`).
         * - Decyzje ARCHITEKTONICZNE to te, ktorych zmiana kosztuje
         *   NIEPROPORCJONALNIE wiecej: wybor bazy danych, sposob podzialu
         *   na moduly, format komunikacji miedzy czesciami systemu, GDZIE
         *   przebiega granica miedzy "logika biznesowa" a "szczegoly
         *   techniczne". Robert C. Martin ("Clean Architecture", 2017)
         *   podsumowuje to jako: architektura to sztuka ODKLADANIA decyzji
         *   (framework, baza danych, UI) na PozNIEJ, jak najdluzej, przez
         *   izolowanie ich za granicami/abstrakcjami.
         */
        System.out.println("\nArchitektura = decyzje TRUDNE i KOSZTOWNE do odwrocenia (baza danych, podzial na moduly, granice).");

        /*
         * ============================================================
         * 🔍 CEL: NIEZALEZNOSC OD FRAMEWORKA / UI / BAZY DANYCH
         * ============================================================
         * - Jedna z centralnych idei "Clean Architecture": LOGIKA
         *   BIZNESOWA (to, co czyni Twoja aplikacje WARTOSCIOWA - reguly,
         *   obliczenia, decyzje) powinna dac sie PRZETESTOWAC i ZROZUMIEC
         *   BEZ URUCHAMIANIA bazy danych, serwera WWW, czy konkretnego
         *   frameworka.
         * - To NIE znaczy "nigdy nie uzywaj frameworka" - znaczy: logika
         *   biznesowa nie powinna byc NIEROZLACZNIE WMIESZANA w kod
         *   frameworka, tak by nie dalo sie jej przetestowac/zrozumiec w
         *   izolacji. W kolejnych lekcjach (Lesson11-12: Hexagonal/Ports
         *   and Adapters) poznasz KONKRETNY wzorzec realizujacy ten cel.
         */
        demonstrateBusinessLogicIndependentOfInfrastructure();

        /*
         * ============================================================
         * 🔹 ARCHITEKTURA TO NIE "WIELKI PROJEKT Z GORY" (BIG DESIGN UP FRONT)
         * ============================================================
         * - Swiadoma architektura NIE oznacza projektowania WSZYSTKIEGO
         *   przed napisaniem 1 linijki kodu (tzw. "Big Design Up Front",
         *   krytykowane w metodykach zwinnych) - to tez ryzykowne, bo
         *   wczesne zalozenia czesto okazuja sie bledne.
         * - Oznacza raczej SWIADOME rozpoznawanie, KTORE decyzje sa
         *   drogie do zmiany, i celowe ZOSTAWIANIE SOBIE MOZLIWOSCI
         *   zmiany tam, gdzie niepewnosc jest najwieksza - reszta lekcji
         *   tego rozdzialu pokazuje KONKRETNE techniki temu sluzace.
         */
        System.out.println("\nArchitektura != projektowanie wszystkiego z gory - to swiadome zostawianie sobie mozliwosci zmiany.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Clean Code (`_16_clean_code`) = jakosc na poziomie klasy/metody.
         *   Architektura = organizacja CALEJ aplikacji.
         * - Bez architektury systemy dryfuja w strone "Big Ball of Mud" -
         *   koszt zmiany rosnie z czasem.
         * - Architektura = decyzje TRUDNE do odwrocenia (baza danych,
         *   podzial na moduly, granice miedzy warstwami).
         * - Cel: logika biznesowa niezalezna od frameworka/UI/bazy danych -
         *   testowalna i zrozumiala w izolacji.
         * - To NIE "Big Design Up Front" - to swiadome odkladanie
         *   kosztownych decyzji i izolowanie niepewnosci.
         * - W kolejnej lekcji (Lesson02): Architecture Decision Records -
         *   jak DOKUMENTOWAC wazne decyzje architektoniczne, zeby "dlaczego"
         *   nie zginelo razem z autorem decyzji.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void demonstrateBigBallOfMud() {
        System.out.println("\n=== 'BIG BALL OF MUD' - WSZYSTKO ZALEZY OD WSZYSTKIEGO ===");

        BigBallOfMudShop shop = new BigBallOfMudShop();
        System.out.println(shop.handleOrder("Ala Kowalska", "Laptop", 3000.0, 2));

        System.out.println("-> Logika rabatu, 'zapis do bazy' i formatowanie sa SPLECIONE w 1 metodzie.");
        System.out.println("   Zmiana ZASADY rabatu wymaga grzebania w TEJ SAMEJ metodzie, co zapis i formatowanie.");
    }

    /**
     * ZLY PRZYKLAD - "Big Ball of Mud" w miniaturze: 1 metoda miesza logike
     * rabatu, "zapis do bazy" (symulowana Map) i budowanie odpowiedzi.
     * W realnym systemie taki wzorzec powtarza sie w SETKACH metod naraz.
     */
    static class BigBallOfMudShop {
        private final Map<String, Double> fakeDatabase = new HashMap<>();

        String handleOrder(String customer, String product, double unitPrice, int quantity) {
            double total = unitPrice * quantity;
            if (quantity >= 5) {
                total = total * 0.9; // logika rabatu ZAKOPANA w tej samej metodzie
            } else if (quantity >= 2) {
                total = total * 0.95;
            }
            fakeDatabase.put(customer + "-" + product, total); // "dostep do bazy" tez tutaj
            return "Zamowienie " + customer + " (" + product + " x" + quantity + "): " + total + " zl";
        }
    }

    private static void demonstrateWellSeparatedSystem() {
        System.out.println("\n=== DOBRA ARCHITEKTURA - ZMIANA IZOLOWANA DO 1 MIEJSCA ===");

        OrderRepository repository = new InMemoryOrderRepository();
        DiscountPolicy discountPolicy = new QuantityDiscountPolicy();
        OrderService service = new OrderService(discountPolicy, repository);

        System.out.println(service.placeOrder("Ala Kowalska", "Laptop", 3000.0, 2));

        System.out.println("-> Zmiana ZASADY rabatu = zmiana TYLKO w QuantityDiscountPolicy.");
        System.out.println("   OrderService/OrderRepository NIE MUSZA wiedziec, ze cokolwiek sie zmienilo.");
    }

    interface DiscountPolicy {
        double applyDiscount(double total, int quantity);
    }

    static class QuantityDiscountPolicy implements DiscountPolicy {
        @Override
        public double applyDiscount(double total, int quantity) {
            if (quantity >= 5) {
                return total * 0.9;
            }
            if (quantity >= 2) {
                return total * 0.95;
            }
            return total;
        }
    }

    interface OrderRepository {
        void save(String key, double total);
    }

    static class InMemoryOrderRepository implements OrderRepository {
        private final Map<String, Double> storage = new HashMap<>();

        @Override
        public void save(String key, double total) {
            storage.put(key, total);
        }
    }

    static class OrderService {
        private final DiscountPolicy discountPolicy;
        private final OrderRepository repository;

        OrderService(DiscountPolicy discountPolicy, OrderRepository repository) {
            this.discountPolicy = discountPolicy;
            this.repository = repository;
        }

        String placeOrder(String customer, String product, double unitPrice, int quantity) {
            double total = discountPolicy.applyDiscount(unitPrice * quantity, quantity);
            repository.save(customer + "-" + product, total);
            return "Zamowienie " + customer + " (" + product + " x" + quantity + "): " + total + " zl";
        }
    }

    private static void demonstrateBusinessLogicIndependentOfInfrastructure() {
        System.out.println("\n=== LOGIKA BIZNESOWA TESTOWALNA BEZ 'PRAWDZIWEJ' INFRASTRUKTURY ===");

        // Testujemy REGULE rabatu (logika biznesowa) BEZ zadnej bazy danych,
        // sieci, czy frameworka - tylko czysta Java, sama decyzja biznesowa.
        DiscountPolicy policy = new QuantityDiscountPolicy();
        List<String> results = new ArrayList<>();
        for (int quantity : List.of(1, 2, 5)) {
            double total = policy.applyDiscount(100.0, quantity);
            results.add("quantity=" + quantity + " -> total=" + total);
        }
        results.forEach(r -> System.out.println("  " + r));
        System.out.println("Cala logika rabatu przetestowana BEZ InMemoryOrderRepository - to jest 'niezaleznosc od infrastruktury'.");
    }
}
