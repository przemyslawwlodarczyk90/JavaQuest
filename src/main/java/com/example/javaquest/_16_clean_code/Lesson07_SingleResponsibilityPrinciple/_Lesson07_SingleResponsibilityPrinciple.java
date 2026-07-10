package com.example.javaquest._16_clean_code.Lesson07_SingleResponsibilityPrinciple;

import java.util.ArrayList;
import java.util.List;

public class _Lesson07_SingleResponsibilityPrinciple {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: SINGLE RESPONSIBILITY PRINCIPLE (SRP) ===");

        /*
         * ============================================================
         * 📦 SOLID - 5 ZASAD PROJEKTOWANIA OBIEKTOWEGO (LEKCJE 7-11)
         * ============================================================
         * - SOLID to akronim 5 klasycznych zasad projektowania OOP, ktore
         *   pomagaja pisac kod latwy w utrzymaniu, testowaniu i rozszerzaniu:
         *     S - Single Responsibility Principle   (ta lekcja)
         *     O - Open/Closed Principle              (Lesson08)
         *     L - Liskov Substitution Principle       (Lesson09)
         *     I - Interface Segregation Principle     (Lesson10)
         *     D - Dependency Inversion Principle      (Lesson11)
         * - Lekcje _02_oop/Lesson07_AbstractClasses i Lesson08_Interfaces
         *   nauczyly Cie MECHANIKI (jak napisac klase abstrakcyjna, interfejs).
         *   SOLID to zasady mowiace, JAK MADRZE uzyc tych mechanizmow, zeby
         *   kod byl latwiejszy w utrzymaniu.
         * - Po SOLID (Lesson12) omowimy coupling/cohesion/Prawo Demeter -
         *   "meta-cel", ktoremu w praktyce sluzy caly SOLID.
         */
        System.out.println("SOLID: S-Single Responsibility, O-Open/Closed, L-Liskov, I-Interface Segregation, D-Dependency Inversion");

        /*
         * ============================================================
         * 🔹 DEFINICJA SRP
         * ============================================================
         * - Sformulowanie Roberta C. Martina ("Uncle Bob"): "Klasa powinna
         *   miec TYLKO JEDEN powod do zmiany" (ang. "a class should have only
         *   one reason to change").
         * - UWAGA na czeste nieporozumienie: SRP NIE mowi "klasa ma robic
         *   tylko jedna rzecz" w sensie "jedna metoda". Chodzi o to, ze
         *   odpowiedzialnosc klasy powinna byc zwiazana z JEDNYM aktorem/
         *   interesariuszem (np. "dzial ksiegowosci", "zespol UI", "zespol
         *   infrastruktury") - jesli 2 rozne osoby/zespoly z 2 roznych
         *   powodow chcialyby zmienic te sama klase, to znak, ze klasa robi
         *   za duzo.
         * - "Powod do zmiany" = zrodlo zmiany wymagan, nie liczba linii kodu.
         */
        System.out.println("\nSRP: klasa powinna miec TYLKO JEDEN powod do zmiany (jednego 'wlasciciela' odpowiedzialnosci).");

        /*
         * ============================================================
         * 🔹 PRZYKLAD NARUSZENIA SRP: Invoice "ROBI WSZYSTKO"
         * ============================================================
         * - BadInvoice ponizej trzyma dane faktury, ALE dodatkowo:
         *     1. Liczy sume (logika biznesowa).
         *     2. "Zapisuje" fakture do magazynu danych (odpowiedzialnosc
         *        infrastruktury/persystencji).
         *     3. Formatuje fakture do wydruku (odpowiedzialnosc prezentacji).
         * - To SA 3 ROZNE POWODY DO ZMIANY:
         *     - zmiana sposobu liczenia podatku/rabatu -> zmiana w BadInvoice,
         *     - zmiana formatu zapisu (np. z "pliku" na baze danych) -> zmiana
         *       w TEJ SAMEJ klasie BadInvoice,
         *     - zmiana ukladu wydruku (np. marketing chce inny naglowek) ->
         *       znowu zmiana w TEJ SAMEJ klasie BadInvoice.
         * - Efekt: klasa staje sie "magnesem na zmiany" z 3 roznych stron,
         *   rosnie w nieskonczonosc, trudno ja testowac (test logiki liczenia
         *   sumy "przy okazji" dotyka kodu zapisu i wydruku).
         */
        demonstrateBadExample();

        /*
         * ============================================================
         * 🔍 DLACZEGO TO PROBLEM W PRAKTYCE
         * ============================================================
         * - Trudne testowanie jednostkowe: chcac przetestowac TYLKO logike
         *   liczenia sumy, i tak "pociagamy za soba" kod zapisu/wydruku
         *   (sa w tej samej klasie, wspoldziela pola/stan).
         * - Krucha klasa: zmiana w jednym miejscu (np. w formacie zapisu)
         *   moze przypadkowo zepsuc cos w liczeniu sumy - bo wszystko jest
         *   w jednym pliku, latwo o przypadkowa zaleznosc.
         * - Trudna wspolpraca zespolowa: 2 osoby pracujace nad roznymi
         *   "powodami zmiany" (jedna nad logika rabatow, druga nad formatem
         *   zapisu) edytuja TEN SAM plik -> konflikty w systemie kontroli
         *   wersji (Git) czesciej niz to konieczne.
         * - Brak reuzywalnosci: nie da sie uzyc "tylko logiki liczenia" gdzie
         *   indziej bez zaciagniecia tez kodu zapisu/wydruku.
         */
        System.out.println("\nProblemy BadInvoice: trudne testy, krucha klasa, konflikty w Git, brak reuzywalnosci.");

        /*
         * ============================================================
         * 📌 REFAKTORYZACJA: ROZBICIE NA 3 KLASY WEDLUG ODPOWIEDZIALNOSCI
         * ============================================================
         * - Invoice        - TYLKO dane faktury + logika biznesowa (liczenie
         *                    sumy). Powod do zmiany: zmiana zasad liczenia
         *                    (np. nowy sposob liczenia VAT).
         * - InvoiceRepository - TYLKO zapis/odczyt faktury. Powod do zmiany:
         *                    zmiana sposobu przechowywania (plik -> baza
         *                    danych -> API zewnetrzne).
         * - InvoicePrinter    - TYLKO formatowanie do wydruku/wyswietlenia.
         *                    Powod do zmiany: zmiana wygladu wydruku.
         * - Kazda klasa ma teraz DOKLADNIE JEDEN powod do zmiany - i moze byc
         *   testowana, zmieniana i reuzywana NIEZALEZNIE od pozostalych.
         */
        demonstrateGoodExample();

        /*
         * ============================================================
         * 🔍 KORZYSCI Z ROZBICIA WEDLUG SRP
         * ============================================================
         * - Niezalezne testy jednostkowe: Invoice.calculateTotal() testujemy
         *   BEZ potrzeby dotykania zapisu czy wydruku.
         * - Niezalezne zmiany: zmiana formatu wydruku NIE dotyka logiki
         *   liczenia sumy ani kodu zapisu.
         * - Reuzywalnosc: ten sam InvoicePrinter moze formatowac faktury z
         *   roznych zrodel; ten sam InvoiceRepository moze zapisywac rozne
         *   typy dokumentow (po drobnej generalizacji).
         * - Mniej konfliktow w Git: rozne zespoly edytuja rozne, mniejsze
         *   pliki zamiast jednego "boskiego obiektu" (God Object).
         */
        System.out.println("\nPo rozbiciu: Invoice/InvoiceRepository/InvoicePrinter - kazda klasa ma 1 powod do zmiany.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SRP: jedna klasa = jeden powod do zmiany = jeden "wlasciciel"
         *   odpowiedzialnosci.
         * - Sygnal ostrzegawczy ("code smell"), ze SRP jest zlamane: nazwa
         *   klasy zawiera "And"/"Manager"/"Helper" albo opis klasy w 1 zdaniu
         *   wymaga uzycia slowa "oraz"/"i" wiecej niz raz.
         * - W kolejnej lekcji (Lesson08): Open/Closed Principle - jak
         *   dodawac NOWE zachowania bez modyfikowania istniejacego,
         *   dzialajacego kodu.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void demonstrateBadExample() {
        System.out.println("\n=== ZLY PRZYKLAD: BadInvoice (3 odpowiedzialnosci w 1 klasie) ===");

        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("Kurs Java Online", 199.99, 1));
        items.add(new LineItem("Konsultacja mentorska", 150.00, 2));

        BadInvoice invoice = new BadInvoice("Jan Kowalski", items);

        double total = invoice.calculateTotal();
        System.out.println("BadInvoice.calculateTotal() = " + total + " zl");

        invoice.saveToFile("faktura_001.txt");
        invoice.printFormatted();

        System.out.println("Zauwaz: JEDNA klasa liczy, zapisuje I formatuje wydruk - 3 powody do zmiany w 1 miejscu.");
    }

    private static void demonstrateGoodExample() {
        System.out.println("\n=== DOBRY PRZYKLAD: Invoice + InvoiceRepository + InvoicePrinter ===");

        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("Kurs Java Online", 199.99, 1));
        items.add(new LineItem("Konsultacja mentorska", 150.00, 2));

        Invoice invoice = new Invoice("Jan Kowalski", items);
        double total = invoice.calculateTotal();
        System.out.println("Invoice.calculateTotal() = " + total + " zl (TYLKO logika biznesowa)");

        InvoiceRepository repository = new InvoiceRepository();
        repository.save(invoice, "faktura_001.txt");

        InvoicePrinter printer = new InvoicePrinter();
        printer.print(invoice);

        System.out.println("Kazda klasa (Invoice/Repository/Printer) moze byc zmieniana i testowana NIEZALEZNIE.");
    }

    /**
     * Prosty rekord reprezentujacy pozycje na fakturze: nazwa, cena
     * jednostkowa, ilosc.
     */
    record LineItem(String name, double unitPrice, int quantity) {
        double lineTotal() {
            return unitPrice * quantity;
        }
    }

    /**
     * ZLY PRZYKLAD - klasa laczy 3 rozne odpowiedzialnosci (3 powody do
     * zmiany): logike biznesowa, zapis (persystencje) i formatowanie
     * wydruku. Narusza SRP.
     */
    static class BadInvoice {
        private final String customerName;
        private final List<LineItem> items;

        BadInvoice(String customerName, List<LineItem> items) {
            this.customerName = customerName;
            this.items = items;
        }

        // Powod do zmiany #1: logika biznesowa (np. nowy sposob liczenia rabatu/VAT)
        double calculateTotal() {
            double sum = 0;
            for (LineItem item : items) {
                sum += item.lineTotal();
            }
            return sum;
        }

        // Powod do zmiany #2: sposob zapisu (np. plik -> baza danych)
        void saveToFile(String fileName) {
            System.out.println(" [SYMULACJA ZAPISU] Zapisano fakture dla '" + customerName
                    + "' do pliku: " + fileName);
        }

        // Powod do zmiany #3: format wydruku (np. inny uklad, inny jezyk)
        void printFormatted() {
            System.out.println(" [WYDRUK] Faktura dla: " + customerName);
            for (LineItem item : items) {
                System.out.printf("  - %-25s %6.2f zl x %d%n", item.name(), item.unitPrice(), item.quantity());
            }
            System.out.println("  SUMA: " + calculateTotal() + " zl");
        }
    }

    /**
     * DOBRY PRZYKLAD - TYLKO dane faktury i logika biznesowa. Jedyny powod
     * do zmiany: zmiana zasad liczenia sumy/podatku/rabatu.
     */
    static class Invoice {
        private final String customerName;
        private final List<LineItem> items;

        Invoice(String customerName, List<LineItem> items) {
            this.customerName = customerName;
            this.items = items;
        }

        String getCustomerName() {
            return customerName;
        }

        List<LineItem> getItems() {
            return items;
        }

        double calculateTotal() {
            double sum = 0;
            for (LineItem item : items) {
                sum += item.lineTotal();
            }
            return sum;
        }
    }

    /**
     * DOBRY PRZYKLAD - TYLKO zapis/odczyt faktury. Jedyny powod do zmiany:
     * zmiana sposobu przechowywania danych.
     */
    static class InvoiceRepository {
        void save(Invoice invoice, String fileName) {
            System.out.println(" [SYMULACJA ZAPISU] Zapisano fakture dla '" + invoice.getCustomerName()
                    + "' do pliku: " + fileName);
        }
    }

    /**
     * DOBRY PRZYKLAD - TYLKO formatowanie/prezentacja faktury. Jedyny powod
     * do zmiany: zmiana wygladu wydruku.
     */
    static class InvoicePrinter {
        void print(Invoice invoice) {
            System.out.println(" [WYDRUK] Faktura dla: " + invoice.getCustomerName());
            for (LineItem item : invoice.getItems()) {
                System.out.printf("  - %-25s %6.2f zl x %d%n", item.name(), item.unitPrice(), item.quantity());
            }
            System.out.println("  SUMA: " + invoice.calculateTotal() + " zl");
        }
    }
}
