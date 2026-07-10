package com.example.javaquest._16_clean_code.Lesson16_RefactoringCatalog;

public class _Lesson16_RefactoringCatalog {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 16: KATALOG TECHNIK REFAKTORYZACJI ===");

        /*
         * ============================================================
         * 📦 CZYM JEST "KATALOG REFAKTORYZACJI"?
         * ============================================================
         * - Lesson15 nauczyl Cie PROCESU (male kroki, siatka bezpieczenstwa,
         *   regula trzech razy) - ta lekcja to "skrzynka z narzedziami":
         *   KONKRETNE, NAZWANE techniki, ktorymi wykonuje sie te male kroki.
         * - Martin Fowler skatalogowal dziesiatki takich technik w
         *   "Refactoring" - kazda ma NAZWE (zeby zespol mogl o niej mowic
         *   krotko: "zrob tu Extract Method" zamiast dlugiego opisu),
         *   KROKI i sytuacje, w ktorych pasuje.
         * - W tej lekcji: 8 najczesciej uzywanych technik, kazda powiazana
         *   z KONKRETNYM smellem z Lesson14, ktory naprawia.
         */
        System.out.println("8 nazwanych technik refaktoringu - kazda naprawia KONKRETNY smell z Lesson14.");

        /*
         * ============================================================
         * 🔹 TECHNIKA 1: EXTRACT METHOD (WYDZIEL METODE)
         * ============================================================
         * - Naprawia: Long Method (Lesson14).
         * - Fragment kodu, ktory mozna opisac WLASNA nazwa, staje sie
         *   osobna, dobrze nazwana metoda - metoda oryginalna WOLA teraz
         *   te nowa metode zamiast zawierac jej kod bezposrednio.
         */
        demonstrateExtractMethod();

        /*
         * ============================================================
         * 🔍 TECHNIKA 2: EXTRACT CLASS (WYDZIEL KLASE)
         * ============================================================
         * - Naprawia: Large Class (Lesson14).
         * - Podzbior pol i metod, ktore logicznie TWORZA WLASNA
         *   odpowiedzialnosc, przenosi sie do NOWEJ, osobnej klasy -
         *   oryginalna klasa deleguje do niej.
         */
        demonstrateExtractClass();

        /*
         * ============================================================
         * 🔹 TECHNIKA 3: INTRODUCE PARAMETER OBJECT
         * ============================================================
         * - Naprawia: Long Parameter List / Data Clumps (Lesson14).
         * - Grupa parametrow, ktore ZAWSZE wystepuja razem, staje sie
         *   JEDNYM obiektem (najlepiej `record`) - sygnatura metody
         *   przyjmuje ten 1 obiekt zamiast wielu pojedynczych parametrow.
         */
        demonstrateIntroduceParameterObject();

        /*
         * ============================================================
         * 🔍 TECHNIKA 4: REPLACE CONDITIONAL WITH POLYMORPHISM
         * ============================================================
         * - Naprawia: Shotgun Surgery (Lesson14) powstajacy z powtorzonych
         *   `if/else`/`switch` po TYM SAMYM "typie" w wielu miejscach.
         * - Kazda "galaz" warunku staje sie osobna klasa implementujaca
         *   wspolny interfejs - dodanie NOWEGO przypadku to dodanie NOWEJ
         *   klasy, BEZ modyfikacji istniejacego kodu (patrz tez OCP,
         *   Lesson08).
         */
        demonstrateReplaceConditionalWithPolymorphism();

        /*
         * ============================================================
         * 🔹 TECHNIKA 5: RENAME METHOD / VARIABLE
         * ============================================================
         * - Naprawia: nieczytelne, mylace nazwy (por. Lesson02: Naming).
         * - Najprostsza, najczesciej wykonywana technika - zmiana samej
         *   NAZWY (metody, zmiennej, klasy) na taka, ktora SZCZERZE
         *   opisuje swoje zachowanie/zawartosc, bez zmiany logiki.
         */
        demonstrateRenameMethod();

        /*
         * ============================================================
         * 🔍 TECHNIKA 6: REPLACE MAGIC NUMBER WITH SYMBOLIC CONSTANT
         * ============================================================
         * - Naprawia: "magiczne liczby" (niewyjasnione literaly liczbowe w
         *   srodku logiki) - liczba dostaje NAZWE (`static final`), ktora
         *   TLUMACZY, co ona reprezentuje.
         */
        demonstrateReplaceMagicNumberWithConstant();

        /*
         * ============================================================
         * 🔹 TECHNIKA 7: DECOMPOSE CONDITIONAL / REPLACE NESTED
         * CONDITIONAL WITH GUARD CLAUSES
         * ============================================================
         * - Naprawia: zagniezdzone, trudne do przeczytania warunki
         *   (`if wewnatrz if wewnatrz if`).
         * - "Guard clauses" (klauzule straznicze): warunki BRZEGOWE
         *   sprawdzane NAJPIERW, z natychmiastowym `return`/`continue` -
         *   "glowna sciezka" logiki zostaje NIEZAGNIEZDZONA, na jednym
         *   poziomie wciecia.
         */
        demonstrateGuardClauses();

        /*
         * ============================================================
         * 🔍 TECHNIKA 8: MOVE METHOD
         * ============================================================
         * - Naprawia: Feature Envy (Lesson14) / niska spojnosc (Lesson06).
         * - Metoda, ktora bardziej "interesuje sie" danymi INNEJ klasy niz
         *   wlasnej, PRZENOSI SIE do tej innej klasy - powiazane tez z
         *   "Tell, Don't Ask" (Lesson12).
         */
        demonstrateMoveMethod();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Extract Method: rozbij dluga metode na male, nazwane kawalki.
         * - Extract Class: rozbij duza klase na male, spojne klasy.
         * - Introduce Parameter Object: zgrupuj powiazane parametry w 1
         *   obiekt.
         * - Replace Conditional with Polymorphism: zamien powtarzane
         *   warunki na hierarchie klas.
         * - Rename Method/Variable: napraw nazwe, ktora klamie o swoim
         *   zachowaniu.
         * - Replace Magic Number with Symbolic Constant: nazwij liczby.
         * - Guard Clauses: splaszcz zagniezdzone warunki.
         * - Move Method: przenies metode tam, gdzie "naprawde nalezy".
         * - W kolejnej lekcji (Lesson17): projektowanie WYJATKOW - jak
         *   uzywac ich CZYSTO (checked vs unchecked, hierarchie, kontekst
         *   bledu) w POLACZENIU z technikami refaktoringu z tej lekcji.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    // ------------------------------------------------------------------
    // 1. Extract Method
    // ------------------------------------------------------------------
    private static void demonstrateExtractMethod() {
        System.out.println("\n=== TECHNIKA: EXTRACT METHOD ===");

        System.out.println("[PRZED] " + describeOrderLongMethod("Ala Kowalska", 12, 15.0));
        System.out.println("[PO] " + describeOrderExtracted("Ala Kowalska", 12, 15.0));
    }

    private static String describeOrderLongMethod(String customerName, int quantity, double unitPrice) {
        double subtotal = quantity * unitPrice;
        double total = quantity >= 10 ? subtotal * 0.9 : subtotal;
        return "Zamowienie: " + customerName + ", " + quantity + " szt., " + total + " zl";
    }

    private static String describeOrderExtracted(String customerName, int quantity, double unitPrice) {
        double total = calculateOrderTotal(quantity, unitPrice);
        return "Zamowienie: " + customerName + ", " + quantity + " szt., " + total + " zl";
    }

    private static double calculateOrderTotal(int quantity, double unitPrice) {
        double subtotal = quantity * unitPrice;
        return quantity >= 10 ? subtotal * 0.9 : subtotal;
    }

    // ------------------------------------------------------------------
    // 2. Extract Class
    // ------------------------------------------------------------------
    private static void demonstrateExtractClass() {
        System.out.println("\n=== TECHNIKA: EXTRACT CLASS ===");

        EmployeeDoingEverything before = new EmployeeDoingEverything("Jan Nowak", 96000);
        System.out.println("[PRZED] " + before.generateYearlyReport());

        Employee afterEmployee = new Employee("Jan Nowak", 96000);
        EmployeeReportGenerator afterReportGenerator = new EmployeeReportGenerator();
        System.out.println("[PO] " + afterReportGenerator.generateYearlyReport(afterEmployee));
    }

    /** PRZED: 1 klasa laczy dane pracownika I generowanie raportu (Large Class). */
    static class EmployeeDoingEverything {
        String name;
        double baseSalary;

        EmployeeDoingEverything(String name, double baseSalary) {
            this.name = name;
            this.baseSalary = baseSalary;
        }

        String generateYearlyReport() {
            return "Raport roczny dla " + name + ": " + baseSalary + " zl";
        }
    }

    /** PO: Employee - TYLKO dane pracownika (wydzielona odpowiedzialnosc). */
    static class Employee {
        private final String name;
        private final double baseSalary;

        Employee(String name, double baseSalary) {
            this.name = name;
            this.baseSalary = baseSalary;
        }

        String getName() {
            return name;
        }

        double getBaseSalary() {
            return baseSalary;
        }
    }

    /** PO: EmployeeReportGenerator - TYLKO generowanie raportu (Extract Class). */
    static class EmployeeReportGenerator {
        String generateYearlyReport(Employee employee) {
            return "Raport roczny dla " + employee.getName() + ": " + employee.getBaseSalary() + " zl";
        }
    }

    // ------------------------------------------------------------------
    // 3. Introduce Parameter Object
    // ------------------------------------------------------------------
    private static void demonstrateIntroduceParameterObject() {
        System.out.println("\n=== TECHNIKA: INTRODUCE PARAMETER OBJECT ===");

        System.out.println("[PRZED] " + formatAddressLongParams("Kwiatowa 5", "Krakow", "30-001"));

        Address address = new Address("Kwiatowa 5", "Krakow", "30-001");
        System.out.println("[PO] " + formatAddress(address));
    }

    private static String formatAddressLongParams(String street, String city, String zipCode) {
        return street + ", " + zipCode + " " + city;
    }

    private record Address(String street, String city, String zipCode) {
    }

    private static String formatAddress(Address address) {
        return address.street() + ", " + address.zipCode() + " " + address.city();
    }

    // ------------------------------------------------------------------
    // 4. Replace Conditional with Polymorphism
    // ------------------------------------------------------------------
    private static void demonstrateReplaceConditionalWithPolymorphism() {
        System.out.println("\n=== TECHNIKA: REPLACE CONDITIONAL WITH POLYMORPHISM ===");

        System.out.println("[PRZED] rabat VIP: " + calculateDiscountWithConditional("VIP", 100));
        System.out.println("[PRZED] rabat REGULAR: " + calculateDiscountWithConditional("REGULAR", 100));

        DiscountPolicy vip = new VipDiscountPolicy();
        DiscountPolicy regular = new RegularDiscountPolicy();
        System.out.println("[PO] rabat VIP: " + vip.calculateDiscount(100));
        System.out.println("[PO] rabat REGULAR: " + regular.calculateDiscount(100));
        System.out.println("-> Dodanie NOWEJ kategorii (np. GOLD) to nowa klasa, BEZ modyfikacji istniejacych (OCP, Lesson08).");
    }

    /** PRZED: powtarzany warunek po "typie" - kazde nowe miejsce uzycia MUSI go powielic (Shotgun Surgery). */
    private static double calculateDiscountWithConditional(String customerCategory, double amount) {
        double discount = "VIP".equals(customerCategory) ? 0.15 : "REGULAR".equals(customerCategory) ? 0.05 : 0.0;
        return amount * (1 - discount);
    }

    /** PO: hierarchia klas zamiast powtarzanego warunku. */
    interface DiscountPolicy {
        double calculateDiscount(double amount);
    }

    static class VipDiscountPolicy implements DiscountPolicy {
        @Override
        public double calculateDiscount(double amount) {
            return amount * 0.85;
        }
    }

    static class RegularDiscountPolicy implements DiscountPolicy {
        @Override
        public double calculateDiscount(double amount) {
            return amount * 0.95;
        }
    }

    // ------------------------------------------------------------------
    // 5. Rename Method / Variable
    // ------------------------------------------------------------------
    private static void demonstrateRenameMethod() {
        System.out.println("\n=== TECHNIKA: RENAME METHOD ===");

        System.out.println("[PRZED] proc(5) = " + proc(5));
        System.out.println("[PO] calculateSquare(5) = " + calculateSquare(5));
        System.out.println("-> Identyczna logika - jedyna zmiana to nazwa, ktora TERAZ mowi prawde o dzialaniu.");
    }

    /** PRZED: nazwa "proc" NIC nie mowi o dzialaniu metody. */
    private static int proc(int x) {
        return x * x;
    }

    /** PO: nazwa jasno opisuje, co metoda robi. */
    private static int calculateSquare(int x) {
        return x * x;
    }

    // ------------------------------------------------------------------
    // 6. Replace Magic Number with Symbolic Constant
    // ------------------------------------------------------------------
    private static final double STANDARD_VAT_RATE = 0.23;

    private static void demonstrateReplaceMagicNumberWithConstant() {
        System.out.println("\n=== TECHNIKA: REPLACE MAGIC NUMBER WITH SYMBOLIC CONSTANT ===");

        System.out.println("[PRZED] " + calculatePriceWithMagicNumber(100));
        System.out.println("[PO] " + calculatePriceWithNamedConstant(100));
    }

    private static double calculatePriceWithMagicNumber(double basePrice) {
        return basePrice * 1.23; // 1.23 - co dokladnie oznacza ta liczba?
    }

    private static double calculatePriceWithNamedConstant(double basePrice) {
        return basePrice * (1 + STANDARD_VAT_RATE); // nazwa STANDARD_VAT_RATE mowi, co to jest
    }

    // ------------------------------------------------------------------
    // 7. Guard Clauses (Decompose Conditional)
    // ------------------------------------------------------------------
    private static void demonstrateGuardClauses() {
        System.out.println("\n=== TECHNIKA: GUARD CLAUSES (SPLASZCZENIE ZAGNIEZDZONYCH WARUNKOW) ===");

        System.out.println("[PRZED] " + describeEligibilityNested(25, true, true));
        System.out.println("[PO] " + describeEligibilityGuardClauses(25, true, true));
        System.out.println("[PRZED] " + describeEligibilityNested(15, true, true));
        System.out.println("[PO] " + describeEligibilityGuardClauses(15, true, true));
    }

    /** PRZED: 3 poziomy zagniezdzenia - trudno objac wzrokiem "glowna sciezke". */
    private static String describeEligibilityNested(int age, boolean hasLicense, boolean hasInsurance) {
        if (age >= 18) {
            if (hasLicense) {
                if (hasInsurance) {
                    return "MOZE PROWADZIC";
                } else {
                    return "BRAK UBEZPIECZENIA";
                }
            } else {
                return "BRAK PRAWA JAZDY";
            }
        } else {
            return "NIEPELNOLETNI";
        }
    }

    /** PO: guard clauses - przypadki brzegowe odrzucone NAJPIERW, bez zagniezdzenia. */
    private static String describeEligibilityGuardClauses(int age, boolean hasLicense, boolean hasInsurance) {
        if (age < 18) {
            return "NIEPELNOLETNI";
        }
        if (!hasLicense) {
            return "BRAK PRAWA JAZDY";
        }
        if (!hasInsurance) {
            return "BRAK UBEZPIECZENIA";
        }
        return "MOZE PROWADZIC";
    }

    // ------------------------------------------------------------------
    // 8. Move Method
    // ------------------------------------------------------------------
    private static void demonstrateMoveMethod() {
        System.out.println("\n=== TECHNIKA: MOVE METHOD ===");

        InvoiceWithFeatureEnvy invoiceBad = new InvoiceWithFeatureEnvy(new CustomerData("Ala Kowalska", "Krakow", "30-001"));
        System.out.println("[PRZED] " + formatShippingLabelFeatureEnvy(invoiceBad));

        InvoiceGood invoiceGood = new InvoiceGood(new CustomerData("Ala Kowalska", "Krakow", "30-001"));
        System.out.println("[PO] " + invoiceGood.formatShippingLabel());
    }

    record CustomerData(String name, String city, String zipCode) {
    }

    /** PRZED: metoda zewnetrzna "zazdrosci" o dane CustomerData - Feature Envy (Lesson14). */
    static class InvoiceWithFeatureEnvy {
        CustomerData customer;

        InvoiceWithFeatureEnvy(CustomerData customer) {
            this.customer = customer;
        }
    }

    private static String formatShippingLabelFeatureEnvy(InvoiceWithFeatureEnvy invoice) {
        return invoice.customer.name() + ", " + invoice.customer.zipCode() + " " + invoice.customer.city();
    }

    /** PO: logika PRZENIESIONA (Move Method) do klasy, ktorej dane naprawde dotyczy. */
    static class InvoiceGood {
        private final CustomerData customer;

        InvoiceGood(CustomerData customer) {
            this.customer = customer;
        }

        String formatShippingLabel() {
            return customer.name() + ", " + customer.zipCode() + " " + customer.city();
        }
    }
}
