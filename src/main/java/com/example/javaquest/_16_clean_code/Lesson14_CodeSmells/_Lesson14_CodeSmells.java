package com.example.javaquest._16_clean_code.Lesson14_CodeSmells;

import java.util.List;

public class _Lesson14_CodeSmells {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: CODE SMELLS (KATALOG FOWLERA) ===");

        /*
         * ============================================================
         * 📦 CZYM SA "CODE SMELLS"?
         * ============================================================
         * - "Code smell" (zapaszek kodu) to NIE blad - kod ze smellem
         *   dziala poprawnie i przechodzi testy. To SYGNAL OSTRZEGAWCZY,
         *   ze cos w strukturze kodu utrudni przyszla zmiane.
         * - Termin i katalog spopularyzowal Martin Fowler w ksiazce
         *   "Refactoring: Improving the Design of Existing Code" (1999,
         *   wznowienie 2018) - kazdy smell ma tam przypisane konkretne
         *   techniki refaktoringu, ktore go usuwaja.
         * - W tej lekcji: 8 klasycznych smelli, kazdy z minimalnym
         *   przykladem kodu. Same TECHNIKI naprawy (Extract Method,
         *   Introduce Parameter Object, itd.) poznasz w Lesson16 -
         *   proces bezpiecznego refaktoringu w Lesson15.
         */
        System.out.println("8 klasycznych 'zapaszkow' kodu wg Martina Fowlera - kazdy to sygnal, nie blad.");

        /*
         * ============================================================
         * 🔹 SMELL 1: LONG METHOD (DLUGA METODA)
         * ============================================================
         * - Metoda robi zbyt wiele rzeczy naraz - czytelnik musi trzymac
         *   w glowie caly jej kontekst, zeby zrozumiec choc jeden fragment.
         * - Sygnal: metoda ma sekcje oddzielone pustymi liniami/komentarzami
         *   typu "// walidacja", "// obliczenia", "// zapis" - to zwykle
         *   znaczy, ze w srodku sa 3 (lub wiecej) oddzielne odpowiedzialnosci.
         * - Naprawa: Extract Method (Lesson16) - wydziel kazda sekcje do
         *   osobnej, dobrze nazwanej metody.
         */
        demonstrateLongMethod();

        /*
         * ============================================================
         * 🔹 SMELL 2: LARGE CLASS (ZBYT DUZA KLASA)
         * ============================================================
         * - Klasa ma dziesiatki pol i metod, obslugujac wiele niepowiazanych
         *   ze soba odpowiedzialnosci (np. jedna klasa Employee liczy
         *   pensje, generuje raporty PDF I wysyla e-maile).
         * - Sygnal: nazwa klasy jest tak ogolna ("Manager", "Helper",
         *   "Util", "Employee" ktore-robi-wszystko), ze trudno jednym
         *   zdaniem opisac, ZA CO ta klasa odpowiada.
         * - Naprawa: Extract Class (Lesson16) - podziel na kilka mniejszych
         *   klas, kazda z jedna, jasna odpowiedzialnoscia (patrz tez
         *   Lesson07: Single Responsibility Principle).
         */
        demonstrateLargeClass();

        /*
         * ============================================================
         * 🔹 SMELL 3: LONG PARAMETER LIST (DLUGA LISTA PARAMETROW)
         * ============================================================
         * - Metoda przyjmuje 5, 6, 7+ parametrow - wywolanie staje sie
         *   nieczytelne (latwo pomylic kolejnosc dwoch String-ow albo
         *   dwoch double), a testowanie wymaga podawania wszystkich za
         *   kazdym razem.
         * - Naprawa: Introduce Parameter Object (Lesson16) - grupa
         *   powiazanych parametrow staje sie jednym obiektem.
         */
        demonstrateLongParameterList();

        /*
         * ============================================================
         * 🔹 SMELL 4: DUPLICATED CODE (POWIELONY KOD)
         * ============================================================
         * - Ten sam fragment logiki (lub bardzo podobny) wystepuje w kilku
         *   miejscach - patrz tez Lesson13 (DRY).
         * - Sygnal: kopiuj-wklej z drobnymi modyfikacjami, ta sama seria
         *   warunkow w 2+ metodach.
         * - Naprawa: Extract Method / Extract Class (Lesson16), zeby
         *   wspolna logika istniala w jednym miejscu.
         */
        demonstrateDuplicatedCode();

        /*
         * ============================================================
         * 🔹 SMELL 5: FEATURE ENVY (ZAZDROSC O CUDZE DANE)
         * ============================================================
         * - Metoda jednej klasy jest bardziej zainteresowana POLAMI/
         *   METODAMI innej klasy niz wlasnymi - ciagle "siega" po dane
         *   obcego obiektu (invoice.getCustomer().getAddress().getCity()...),
         *   zamiast poprosic ten obiekt, zeby sam cos policzyl.
         * - Sygnal: metoda niemal nie uzywa `this`, za to intensywnie
         *   uzywa gettery innego obiektu przekazanego jako parametr.
         * - Naprawa: przenies metode (Move Method) blizej danych, ktorych
         *   naprawde dotyczy - albo popros obiekt, by sam wykonal
         *   obliczenie (Tell, Don't Ask - patrz Lesson12: Prawo Demeter).
         */
        demonstrateFeatureEnvy();

        /*
         * ============================================================
         * 🔹 SMELL 6: DATA CLUMPS (GRUDKI DANYCH)
         * ============================================================
         * - Te same 3-4 parametry pojawiaja sie RAZEM, w tej samej
         *   kolejnosci, w wielu miejscach kodu (np. street, city,
         *   zipCode wedruja razem po calym systemie) - to sygnal, ze
         *   brakuje osobnego typu/obiektu, ktory powinien je reprezentowac.
         * - Naprawa: Introduce Parameter Object / wydzielenie osobnej
         *   klasy (np. Address) - patrz tez Lesson09 (Embeddable Types w
         *   `_12_hibernate` to ten sam pomysl na poziomie ORM).
         */
        demonstrateDataClumps();

        /*
         * ============================================================
         * 🔹 SMELL 7: PRIMITIVE OBSESSION (OBSESJA NA PUNKCIE PRYMITYWOW)
         * ============================================================
         * - Uzywanie String/int tam, gdzie logicznie chodzi o odrebne,
         *   wyspecjalizowane pojecie (np. String email zamiast klasy
         *   Email, ktora sama pilnuje swojej poprawnosci).
         * - Sygnal: walidacja tego "String-a udajacego typ" (np. sprawdzanie
         *   `@` w adresie e-mail) jest powielona w wielu miejscach, bo
         *   kazdy, kto dostaje "goly" String, musi sam sprawdzic, czy jest
         *   poprawny.
         * - Naprawa: wprowadz maly, dedykowany typ (value object), ktory
         *   PILNUJE swojej poprawnosci w konstruktorze - raz, na zawsze.
         */
        demonstratePrimitiveObsession();

        /*
         * ============================================================
         * 🔹 SMELL 8: SHOTGUN SURGERY (CHIRURGIA ZA POMOCA SRUTOWKI)
         * ============================================================
         * - Jedna, pozornie prosta zmiana wymaga edycji WIELU roznych
         *   klas naraz - bo ta sama decyzja jest "rozstrzelona" po
         *   calym systemie zamiast zyc w jednym miejscu.
         * - Sygnal: opis zadania brzmi "dodaj nowy typ rabatu" i ODRAZU
         *   wiesz, ze musisz zmienic 4 rozne, niepowiazane ze soba pliki.
         * - Naprawa: scal rozproszona logike w jedno miejsce (czesto:
         *   Extract Class + polimorfizm zamiast serii `if/else`
         *   powtorzonej wszedzie - patrz Lesson16: Replace Conditional
         *   with Polymorphism).
         */
        demonstrateShotgunSurgery();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Code smell != blad. To wskazowka: "tu refaktoring prawdopodobnie
         *   sie oplaci", nie wyrok.
         * - 8 smelli z tej lekcji: Long Method, Large Class, Long Parameter
         *   List, Duplicated Code, Feature Envy, Data Clumps, Primitive
         *   Obsession, Shotgun Surgery.
         * - W kolejnej lekcji (Lesson15): CZYM w ogole jest refaktoryzacja i
         *   jak bezpiecznie, malymi krokami, przejsc od "kodu ze smellem" do
         *   "kodu bez smellu" NIE zmieniajac zachowania programu.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    // ------------------------------------------------------------------
    // 1. Long Method
    // ------------------------------------------------------------------

    /** Jedna metoda robi: walidacje, obliczenia RABATU i formatowanie - 3 odpowiedzialnosci naraz. */
    private static String processOrderLongMethod(String customerName, double price, int quantity, boolean vip) {
        // walidacja
        if (customerName == null || customerName.isBlank()) {
            return "BLAD: brak nazwy klienta";
        }
        if (price <= 0 || quantity <= 0) {
            return "BLAD: niepoprawna cena lub ilosc";
        }
        // obliczenia
        double total = price * quantity;
        if (quantity >= 10) {
            total *= 0.9;
        }
        if (vip) {
            total *= 0.95;
        }
        // formatowanie wyniku
        return "Zamowienie dla " + customerName + ": " + quantity + " szt., do zaplaty "
                + String.format("%.2f", total) + " zl";
    }

    private static void demonstrateLongMethod() {
        System.out.println("\n=== SMELL: LONG METHOD ===");
        System.out.println(processOrderLongMethod("Ala Kowalska", 20.0, 12, true));
        System.out.println("-> SMELL: 1 metoda = walidacja + obliczenia + formatowanie. Naprawa: Extract Method (Lesson16).");
    }

    // ------------------------------------------------------------------
    // 2. Large Class
    // ------------------------------------------------------------------

    /** Jedna klasa liczy pensje, generuje raport I wysyla powiadomienia - 3 rozne odpowiedzialnosci. */
    static class EmployeeDoingEverything {
        String name;
        double baseSalary;

        EmployeeDoingEverything(String name, double baseSalary) {
            this.name = name;
            this.baseSalary = baseSalary;
        }

        double calculateMonthlySalary() {
            return baseSalary / 12;
        }

        String generateYearlyReport() {
            return "Raport roczny dla " + name + ": " + baseSalary + " zl (rocznie)";
        }

        String prepareEmailNotification() {
            return "Do: " + name + ", Temat: Twoje wynagrodzenie zostalo naliczone.";
        }
    }

    private static void demonstrateLargeClass() {
        System.out.println("\n=== SMELL: LARGE CLASS ===");
        EmployeeDoingEverything employee = new EmployeeDoingEverything("Jan Nowak", 96000);
        System.out.println("Pensja miesieczna: " + employee.calculateMonthlySalary());
        System.out.println(employee.generateYearlyReport());
        System.out.println(employee.prepareEmailNotification());
        System.out.println("-> SMELL: 1 klasa = place + raportowanie + powiadomienia. Naprawa: Extract Class (Lesson16).");
    }

    // ------------------------------------------------------------------
    // 3. Long Parameter List
    // ------------------------------------------------------------------

    /** 7 parametrow - latwo pomylic kolejnosc, trudno czytac wywolanie. */
    private static String createInvoiceLongParams(String customerName, String street, String city,
                                                    String zipCode, double amount, String currency, boolean paid) {
        return "Faktura dla " + customerName + " (" + street + ", " + zipCode + " " + city + "): "
                + amount + " " + currency + (paid ? " [oplacona]" : " [nieoplacona]");
    }

    private static void demonstrateLongParameterList() {
        System.out.println("\n=== SMELL: LONG PARAMETER LIST ===");
        System.out.println(createInvoiceLongParams("Ala Kowalska", "Kwiatowa 5", "Krakow", "30-001",
                199.99, "PLN", false));
        System.out.println("-> SMELL: 7 parametrow w 1 wywolaniu. Naprawa: Introduce Parameter Object (Lesson16).");
    }

    // ------------------------------------------------------------------
    // 4. Duplicated Code
    // ------------------------------------------------------------------

    private static String describeTemperatureCelsius(double celsius) {
        if (celsius < 0) return "zimno";
        if (celsius < 20) return "chlodno";
        return "cieplo";
    }

    private static String describeTemperatureFahrenheitDuplicated(double fahrenheit) {
        double celsius = (fahrenheit - 32) / 1.8;
        // Ten sam schemat progow co wyzej, przepisany od nowa zamiast wywolany:
        if (celsius < 0) return "zimno";
        if (celsius < 20) return "chlodno";
        return "cieplo";
    }

    private static void demonstrateDuplicatedCode() {
        System.out.println("\n=== SMELL: DUPLICATED CODE ===");
        System.out.println("15*C -> " + describeTemperatureCelsius(15));
        System.out.println("68F -> " + describeTemperatureFahrenheitDuplicated(68));
        System.out.println("-> SMELL: te same progi 'zimno/chlodno/cieplo' przepisane w 2 metodach.");
        System.out.println("   Naprawa: Extract Method - describeTemperatureFahrenheit powinna WOLAC describeTemperatureCelsius.");
    }

    // ------------------------------------------------------------------
    // 5. Feature Envy
    // ------------------------------------------------------------------

    static class Address {
        String city;
        String zipCode;

        Address(String city, String zipCode) {
            this.city = city;
            this.zipCode = zipCode;
        }
    }

    static class Customer {
        String name;
        Address address;

        Customer(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }

    static class Invoice {
        Customer customer;

        Invoice(Customer customer) {
            this.customer = customer;
        }
    }

    /** Ta metoda niemal wcale nie uzywa 'invoice' - ciagle siega po dane Customer/Address. */
    private static String formatShippingLabelFeatureEnvy(Invoice invoice) {
        return invoice.customer.name + "\n"
                + invoice.customer.address.zipCode + " " + invoice.customer.address.city;
    }

    private static void demonstrateFeatureEnvy() {
        System.out.println("\n=== SMELL: FEATURE ENVY ===");
        Invoice invoice = new Invoice(new Customer("Ala Kowalska", new Address("Krakow", "30-001")));
        System.out.println(formatShippingLabelFeatureEnvy(invoice));
        System.out.println("-> SMELL: metoda 'zazdrosci' o dane Customer/Address zamiast wlasnych danych Invoice.");
        System.out.println("   Naprawa: przenies formatowanie do Customer/Address (Tell, Don't Ask - patrz Lesson12).");
    }

    // ------------------------------------------------------------------
    // 6. Data Clumps
    // ------------------------------------------------------------------

    /** street/city/zipCode wedruja RAZEM w kilku sygnaturach - to sygnal brakujacego typu Address. */
    private static String formatPackageLabelDataClump(String street, String city, String zipCode) {
        return street + ", " + zipCode + " " + city;
    }

    private static double calculateShippingFeeDataClump(String street, String city, String zipCode, double weightKg) {
        // street/city/zipCode nie sa tu nawet uzywane do obliczenia - a mimo to musza byc przekazane,
        // bo "wedruja razem" z weightKg po calym systemie.
        return weightKg * 2.0 + (city.equalsIgnoreCase("Krakow") ? 0.0 : 5.0);
    }

    private static void demonstrateDataClumps() {
        System.out.println("\n=== SMELL: DATA CLUMPS ===");
        String street = "Kwiatowa 5";
        String city = "Krakow";
        String zipCode = "30-001";
        System.out.println(formatPackageLabelDataClump(street, city, zipCode));
        System.out.println("Koszt wysylki: " + calculateShippingFeeDataClump(street, city, zipCode, 3.0));
        System.out.println("-> SMELL: (street, city, zipCode) powtarzaja sie razem w kilku sygnaturach.");
        System.out.println("   Naprawa: wydziel klase Address i przekazuj JEDEN obiekt (Introduce Parameter Object, Lesson16).");
    }

    // ------------------------------------------------------------------
    // 7. Primitive Obsession
    // ------------------------------------------------------------------

    /** email jako 'goly' String - kazdy wywolujacy musi SAM pamietac o walidacji. */
    private static String sendWelcomeEmailPrimitiveObsession(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return "BLAD: niepoprawny adres e-mail: " + email;
        }
        return "Wyslano powitalny e-mail na: " + email;
    }

    private static void demonstratePrimitiveObsession() {
        System.out.println("\n=== SMELL: PRIMITIVE OBSESSION ===");
        System.out.println(sendWelcomeEmailPrimitiveObsession("ala@example.com"));
        System.out.println(sendWelcomeEmailPrimitiveObsession("zly-email"));
        System.out.println("-> SMELL: 'String email' zamiast dedykowanego typu Email, ktory sam pilnuje poprawnosci.");
        System.out.println("   Naprawa: wprowadz maly typ Email(String value) z walidacja w konstruktorze.");
    }

    // ------------------------------------------------------------------
    // 8. Shotgun Surgery
    // ------------------------------------------------------------------

    /** Ta sama decyzja "jaki jest rabat dla kategorii klienta" rozstrzelona w 3 niepowiazanych miejscach. */
    static class PricingCalculator {
        double calculatePrice(double base, String customerCategory) {
            double discount = "VIP".equals(customerCategory) ? 0.15 : "REGULAR".equals(customerCategory) ? 0.05 : 0.0;
            return base * (1 - discount);
        }
    }

    static class InvoiceFooterGenerator {
        String describeDiscount(String customerCategory) {
            // Ta sama logika co w PricingCalculator - powtorzona przy opisie faktury!
            double discount = "VIP".equals(customerCategory) ? 0.15 : "REGULAR".equals(customerCategory) ? 0.05 : 0.0;
            return "Zastosowany rabat: " + (discount * 100) + "%";
        }
    }

    static class LoyaltyPointsCalculator {
        int calculatePoints(double base, String customerCategory) {
            // I znowu ta sama logika kategorii - trzecie miejsce do zmiany, gdyby dodano nowa kategorie!
            double multiplier = "VIP".equals(customerCategory) ? 2.0 : "REGULAR".equals(customerCategory) ? 1.0 : 0.5;
            return (int) (base * multiplier / 10);
        }
    }

    private static void demonstrateShotgunSurgery() {
        System.out.println("\n=== SMELL: SHOTGUN SURGERY ===");
        String category = "VIP";
        double base = 200.0;
        System.out.println("Cena: " + new PricingCalculator().calculatePrice(base, category));
        System.out.println(new InvoiceFooterGenerator().describeDiscount(category));
        System.out.println("Punkty lojalnosciowe: " + new LoyaltyPointsCalculator().calculatePoints(base, category));

        List<String> affected = List.of("PricingCalculator", "InvoiceFooterGenerator", "LoyaltyPointsCalculator");
        System.out.println("-> SMELL: dodanie NOWEJ kategorii klienta wymaga zmiany az w " + affected.size()
                + " klasach: " + affected);
        System.out.println("   Naprawa: scal logike kategorii w 1 miejscu (np. enum CustomerCategory z metodami) - Lesson16.");
    }
}
