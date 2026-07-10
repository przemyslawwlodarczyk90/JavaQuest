package com.example.javaquest._16_clean_code.Lesson22_CodeReviewBestPracticesAndCapstone;

import java.util.Optional;

public class _Lesson22_CodeReviewBestPracticesAndCapstone {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 22: CODE REVIEW I PROJEKT KAPSZTONOWY ===");

        /*
         * ============================================================
         * 📦 PO CO CODE REVIEW, SKORO MAMY TESTY I STATYCZNA ANALIZE?
         * ============================================================
         * - Testy (poza kursem, JUnit) sprawdzaja, czy kod DZIALA
         *   POPRAWNIE. Static analysis (Lesson20) automatycznie wylapuje
         *   MECHANICZNE wzorce (nieuzywane pola, zle porownania).
         * - ANI JEDNO, ani drugie nie oceni, czy kod jest ZROZUMIALY dla
         *   INNEGO czlowieka, czy nazwa metody KLAMIE o jej zachowaniu
         *   (Lesson02/04), czy architektura ma SENS BIZNESOWY, czy
         *   ktos przypadkiem NIE POWTORZYL logiki, ktora juz istnieje
         *   gdzie indziej w systemie (Lesson13: DRY).
         * - Code review to jedyny etap, w ktorym DRUGI CZLOWIEK czyta
         *   Twoj kod z pelnym zrozumieniem KONTEKSTU biznesowego - i
         *   to jest jego GLOWNA, niezastapiona wartosc.
         */
        System.out.println("Code review uzupelnia testy i static analysis o PERSPEKTYWE CZLOWIEKA - kontekst, sens, czytelnosc.");

        /*
         * ============================================================
         * 🔹 CO SPRAWDZAC W REVIEW - CHECKLISTA CALEGO ROZDZIALU
         * ============================================================
         * - Dobry recenzent (reviewer) przechodzi mentalnie przez zasady
         *   z CALEGO tego rozdzialu: nazwy (Lesson02), komentarze
         *   (Lesson03), rozmiar metod (Lesson04), formatowanie (Lesson05),
         *   odpowiedzialnosci klas (Lesson06-07), SOLID (Lesson08-11),
         *   sprzezenie/Prawo Demeter (Lesson12), DRY/KISS/YAGNI
         *   (Lesson13), code smelle (Lesson14), projekt wyjatkow
         *   (Lesson17), obsluga null (Lesson18), niezmiennosc (Lesson19).
         * - To NIE znaczy, ze KAZDY komentarz w review musi cytowac
         *   nazwe zasady - ale DOSWIADCZONY recenzent ROZPOZNAJE te
         *   wzorce "odruchowo", tak jak Ty teraz, po ukonczeniu tego
         *   rozdzialu.
         */
        System.out.println("Checklist reviewera = wszystkie zasady tego rozdzialu, rozpoznawane 'odruchowo'.");

        /*
         * ============================================================
         * 🔍 JAK DAWAC FEEDBACK: KONKRETNIE, UPRZEJMIE, Z ROZROZNIENIEM WAGI
         * ============================================================
         * - "To jest zle" NIE mowi autorowi, CO konkretnie poprawic ANI
         *   DLACZEGO - "Ta metoda ma 4 odpowiedzialnosci (waliduje,
         *   liczy, loguje, zapisuje) - czy da sie rozbic wg Extract
         *   Method (Lesson16)?" jest KONKRETNE i EDUKACYJNE.
         * - Rozroznij WAGE uwagi: "MUSI byc poprawione przed mergem"
         *   (np. blad logiczny, luka bezpieczenstwa) vs "sugestia/nit"
         *   (np. drobna preferencja stylistyczna, mozna zignorowac).
         *   Mieszanie tych 2 kategorii bez oznaczenia frustruje autora.
         * - Zadawaj PYTANIA zamiast wydawac rozkazy, gdy nie jestes
         *   pewien konteksu ("Czy byl powod, dla ktorego X zostalo
         *   zrobione w ten sposob?") - czasem autor MA dobry powod,
         *   ktorego recenzent nie widzi.
         * - CHWAL dobre rozwiazania, nie tylko wytykaj problemy - review
         *   to tez okazja do UCZENIA SIE OD SIEBIE NAWZAJEM.
         */
        demonstrateFeedbackQualityComparison();

        /*
         * ============================================================
         * 🔹 JAK PRZYJMOWAC FEEDBACK
         * ============================================================
         * - Zalozenie DOBREJ WOLI (assume good intent) - recenzent
         *   komentuje KOD, nie ocenia Ciebie jako programisty.
         * - Pytaj o UZASADNIENIE, jesli sugestia jest niejasna, zamiast
         *   od razu sie bronic - czasem sugestia odkrywa realny problem,
         *   ktorego nie widziales, pisząc kod "od srodka".
         */
        System.out.println("\nOdbior feedbacku: zalozenie dobrej woli, pytaj o uzasadnienie zamiast bronic sie odruchowo.");

        /*
         * ============================================================
         * 🔍 MALE PULL REQUESTY REVIEWUJA SIE LEPIEJ
         * ============================================================
         * - Powiazane z Lesson15 (male kroki refaktoryzacji): duzy PR
         *   (1000+ linii zmian) jest PRAKTYCZNIE niemozliwy do
         *   dokladnego przejrzenia - recenzent albo spedza na nim
         *   godziny, albo (czesciej) "przeklika" go pobieznie.
         * - Male, skupione PR-y (1 zmiana = 1 logiczna calosc) reviewuja
         *   sie SZYBCIEJ i DOKLADNIEJ - to samo rozumowanie co za malymi
         *   krokami refaktoryzacji.
         */
        System.out.println("\nMale PR-y (1 logiczna zmiana) reviewuja sie dokladniej niz gigantyczne - jak male kroki w Lesson15.");

        /*
         * ============================================================
         * 📌 CZESC 2: PROJEKT KAPSZTONOWY - "PRZED" I "PO"
         * ============================================================
         * - Ponizej: 1 realistyczna klasa `OrderProcessingEverything`
         *   naruszajaca NARAZ 8 roznych zasad z tego rozdzialu (por.
         *   liste w komentarzu przy klasie) - a zaraz obok jej W PELNI
         *   poprawiona wersja, zaprojektowana od nowa z uzyciem
         *   WSZYSTKICH tych zasad.
         * - Traktuj to jak WYNIK code review: "PRZED" to kod ZGLOSZONY
         *   do review, komentarze w nim to UWAGI recenzenta, "PO" to
         *   wersja PO uwzglednieniu WSZYSTKICH uwag.
         */
        demonstrateCapstoneBeforeAndAfter();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE ROZDZIALU
         * ============================================================
         * - Code review uzupelnia testy/static analysis o perspektywe
         *   czlowieka - kontekst, sens biznesowy, czytelnosc.
         * - Dobry feedback: konkretny, z rozroznieniem wagi, w formie
         *   pytania gdy niepewny, z pochwalami dobrych rozwiazan.
         * - Male PR-y reviewuja sie lepiej niz duze (jak male kroki w
         *   refaktoryzacji, Lesson15).
         * - Caly rozdzial `_16_clean_code` (22 lekcje) to JEDEN spojny
         *   zestaw narzedzi: nazewnictwo, komentarze, metody, formatowanie,
         *   klasy, SOLID, sprzezenie/spojnosc, DRY/KISS/YAGNI, code
         *   smelle, refaktoryzacja (proces + katalog technik), wyjatki,
         *   null, niezmiennosc, static analysis, legacy code/dlug
         *   techniczny, i code review jako klamra spinajaca wszystko.
         * - GRATULACJE - to byla OSTATNIA lekcja rozdzialu "Clean Code i
         *   refactoring"!
         */
        System.out.println("\n=== KONIEC LEKCJI 22 (KONIEC ROZDZIALU _16_clean_code) ===");
    }

    private static void demonstrateFeedbackQualityComparison() {
        System.out.println("\n=== JAKOSC FEEDBACKU: OGOLNIKOWY VS KONKRETNY ===");

        System.out.println("[ZLY FEEDBACK] 'Ta metoda jest zla, popraw.'");
        System.out.println("  -> Nie mowi CO ani DLACZEGO - autor nie wie, od czego zaczac.");

        System.out.println("[DOBRY FEEDBACK] 'Ta metoda ma 4 odpowiedzialnosci (waliduje/liczy/loguje/zapisuje) -");
        System.out.println("  to Long Method (Lesson14). Czy da sie rozbic przez Extract Method (Lesson16)?'");
        System.out.println("  -> Konkretny problem, odwolanie do zasady, PYTANIE zamiast rozkazu.");

        System.out.println("[MUST FIX] 'Ta metoda nie waliduje wejscia - null spowoduje NPE dla klienta.' (blokuje merge)");
        System.out.println("[NIT/SUGESTIA] 'Mozna by nazwac ta zmienna `orderTotal` zamiast `t`, ale to tylko sugestia.' (nie blokuje)");
    }

    private static void demonstrateCapstoneBeforeAndAfter() {
        System.out.println("\n=== KAPSZTON: 'PRZED' (ZGLOSZONE DO REVIEW) ===");

        OrderProcessingEverything before = new OrderProcessingEverything();
        System.out.println(before.process("Ala Kowalska", 3, 49.99, "PL", null));

        System.out.println("\n=== KAPSZTON: 'PO' (PO UWZGLEDNIENIU CALEGO REVIEW) ===");

        OrderProcessor after = new OrderProcessor(new StandardTaxCalculator(), new ConsoleReceiptPrinter());
        Order order = new Order("Ala Kowalska", 3, 49.99, "PL");
        String receipt = after.process(order, Optional.empty());
        System.out.println(receipt);
    }

    /*
     * ============================================================
     * 🔴 "PRZED" - ZGLOSZONE DO REVIEW - NARUSZA NARAZ:
     * ============================================================
     * 1) Naming (Lesson02): `t`, `x`, `process` - nazwy nic nie mowia.
     * 2) Methods (Lesson04): 1 metoda robi walidacje+obliczenia+
     *    formatowanie+"logowanie" naraz (Long Method, Lesson14).
     * 3) SRP (Lesson07): klasa "Everything" robi WSZYSTKO naraz.
     * 4) OCP (Lesson08): dodanie nowego kraju = zmiana tej samej metody.
     * 5) DIP (Lesson11): `new` (podatek) tworzony W SRODKU, brak szwu.
     * 6) NullHandling (Lesson18): `couponCode` moze byc null, brak
     *    fail-fast, brak Optional.
     * 7) Immutability (Lesson19): pola mutowalne bez potrzeby.
     * 8) Exception design (Lesson17): brak walidacji wejscia w ogole.
     */
    static class OrderProcessingEverything {
        String x; // "customerName" pod zla nazwa (Naming, Lesson02)

        String process(String n, int q, double p, String c, String cpn) {
            // walidacja (a raczej jej BRAK - Exception design, Lesson17)
            this.x = n;

            // obliczenia + podatek TWORZONY w srodku (DIP, Lesson11) + warunek
            // po kraju (OCP, Lesson08) + magiczne liczby (Refactoring, Lesson16)
            double t = q * p;
            double taxRate = "PL".equals(c) ? 0.23 : "DE".equals(c) ? 0.19 : 0.20;
            t = t * (1 + taxRate);

            // obsluga couponCode - MOZE byc null, brak jakiejkolwiek ochrony (Lesson18)
            if (cpn != null && cpn.equals("PROMO10")) {
                t = t * 0.9;
            }

            // "logowanie" wymieszane z formatowaniem w tej samej metodzie
            System.out.println("[LOG] " + x + " - przetwarzanie");
            return "Zamowienie: " + x + ", " + q + " szt., do zaplaty " + t + " " + c;
        }
    }

    /*
     * ============================================================
     * 🟢 "PO" - PO UWZGLEDNIENIU CALEGO REVIEW:
     * ============================================================
     * - Order: niezmienny record (Lesson19), dane pogrupowane
     *   (Introduce Parameter Object, Lesson16), fail-fast walidacja
     *   w konstruktorze kompaktowym (Lesson17/18).
     * - TaxCalculator: abstrakcja (DIP, Lesson11) zamiast `new` w
     *   srodku, hierarchia zamiast if/else po kraju (Replace
     *   Conditional with Polymorphism, Lesson16 - naprawia OCP,
     *   Lesson08).
     * - couponCode jako Optional<String> - jawny sygnal "moze nie byc"
     *   w sygnaturze (Lesson18), zero ryzyka NPE.
     * - OrderProcessor: 1 odpowiedzialnosc (orkiestracja), male
     *   metody (Lesson04), dobre nazwy (Lesson02), zaleznosci
     *   przyjmowane przez konstruktor (Lesson11).
     */
    record Order(String customerName, int quantity, double unitPrice, String countryCode) {
        Order {
            if (customerName == null || customerName.isBlank()) {
                throw new IllegalArgumentException("customerName nie moze byc puste");
            }
            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity musi byc > 0, otrzymano: " + quantity);
            }
        }
    }

    interface TaxCalculator {
        double calculateTaxRate(String countryCode);
    }

    static class StandardTaxCalculator implements TaxCalculator {
        private static final double DEFAULT_TAX_RATE = 0.20;
        private static final double POLAND_TAX_RATE = 0.23;
        private static final double GERMANY_TAX_RATE = 0.19;

        @Override
        public double calculateTaxRate(String countryCode) {
            return switch (countryCode) {
                case "PL" -> POLAND_TAX_RATE;
                case "DE" -> GERMANY_TAX_RATE;
                default -> DEFAULT_TAX_RATE;
            };
        }
    }

    interface ReceiptPrinter {
        void printReceipt(String receipt);
    }

    static class ConsoleReceiptPrinter implements ReceiptPrinter {
        @Override
        public void printReceipt(String receipt) {
            System.out.println("[PARAGON] " + receipt);
        }
    }

    static class OrderProcessor {
        private static final double COUPON_DISCOUNT_RATE = 0.1;
        private static final String VALID_COUPON_CODE = "PROMO10";

        private final TaxCalculator taxCalculator;
        private final ReceiptPrinter receiptPrinter;

        OrderProcessor(TaxCalculator taxCalculator, ReceiptPrinter receiptPrinter) {
            this.taxCalculator = taxCalculator;
            this.receiptPrinter = receiptPrinter;
        }

        String process(Order order, Optional<String> couponCode) {
            double total = calculateTotalWithTax(order);
            total = applyCouponIfPresent(total, couponCode);
            String summary = buildOrderSummary(order, total);
            receiptPrinter.printReceipt(summary);
            return summary;
        }

        private double calculateTotalWithTax(Order order) {
            double subtotal = order.quantity() * order.unitPrice();
            double taxRate = taxCalculator.calculateTaxRate(order.countryCode());
            return subtotal * (1 + taxRate);
        }

        private double applyCouponIfPresent(double total, Optional<String> couponCode) {
            boolean isValidCoupon = couponCode.map(VALID_COUPON_CODE::equals).orElse(false);
            return isValidCoupon ? total * (1 - COUPON_DISCOUNT_RATE) : total;
        }

        private String buildOrderSummary(Order order, double total) {
            return "Zamowienie: " + order.customerName() + ", " + order.quantity()
                    + " szt., do zaplaty " + total + " " + order.countryCode();
        }
    }
}
