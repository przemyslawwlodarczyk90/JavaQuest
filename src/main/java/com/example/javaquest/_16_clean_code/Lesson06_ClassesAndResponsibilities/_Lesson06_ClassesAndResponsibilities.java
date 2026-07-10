package com.example.javaquest._16_clean_code.Lesson06_ClassesAndResponsibilities;

public class _Lesson06_ClassesAndResponsibilities {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: KLASY I ODPOWIEDZIALNOSCI ===");

        /*
         * ============================================================
         * 📦 ROZMIAR KLASY: LICZONY W ODPOWIEDZIALNOSCIACH, NIE W LINIACH
         * ============================================================
         * - Lesson05 mowil o ORIENTACYJNYM rozmiarze pliku (200-500 linii) -
         *   to jednak tylko SYGNAL, nie sedno sprawy. Prawdziwa miara
         *   "wielkosci" klasy to LICZBA JEJ ODPOWIEDZIALNOSCI, nie liczba
         *   linii kodu.
         * - Test szybkiej oceny: sprobuj opisac klase JEDNYM zdaniem, BEZ
         *   uzycia spojnikow "i"/"oraz"/"a takze". Jesli sie nie da (np.
         *   "ta klasa liczy pensje I generuje raporty I wysyla e-maile") -
         *   klasa robi za duzo. Pelna, formalna wersja tej zasady to Single
         *   Responsibility Principle (Lesson07) - tutaj poznajesz jej
         *   intuicje "od strony organizacji klasy".
         */
        demonstrateOneSentenceTest();

        /*
         * ============================================================
         * 🔹 ENKAPSULACJA: MINIMALNY PUBLICZNY INTERFEJS
         * ============================================================
         * - Pola powinny byc `private` (znasz to z `_02_oop`) - ale ten sam
         *   nacisk na "pokazuj tylko to, co konieczne" dotyczy TEZ METOD.
         * - Metoda pomocnicza, ktora istnieje WYLACZNIE do wsparcia innej
         *   metody TEJ SAMEJ klasy, powinna byc `private` - publiczne API
         *   klasy to jej "widok od zewnatrz", i powinno byc MALE oraz
         *   celowo zaprojektowane, a nie "wszystko, co akurat napisalem".
         * - Male publiczne API = mniej dla klienta klasy do zrozumienia, i
         *   mniej rzeczy, ktore MUSISZ utrzymywac w kompatybilnosci wstecz,
         *   gdy zmieniasz implementacje.
         */
        demonstrateMinimalPublicInterface();

        /*
         * ============================================================
         * 🔍 SPOJNOSC (COHESION): METODY POWINNY UZYWAC POL KLASY
         * ============================================================
         * - Klasa jest SPOJNA (cohesive), gdy WIEKSZOSC jej metod uzywa
         *   WIEKSZOSCI jej pol instancyjnych. Im mniej pol dana metoda
         *   uzywa, tym mniej "naprawde nalezy" do tej klasy.
         * - Niska spojnosc to sygnal, ze klasa PRAWDOPODOBNIE da sie
         *   rozbic na 2+ mniejszych, bardziej spojnych klas - kazda
         *   grupujaca metody wokol INNEGO podzbioru pol.
         * - Uwaga: to jest DOKLADNIE ten sam sygnal co "Feature Envy" z
         *   Lesson14 (Code Smells) - patrzysz na TA SAMA klase z dwoch
         *   stron: "czy metoda uzywa WLASNYCH pol" (spojnosc) vs "czy
         *   metoda uzywa CUDZYCH pol wiecej niz wlasnych" (feature envy).
         */
        demonstrateLowVsHighCohesion();

        /*
         * ============================================================
         * 🔹 ROZBIJANIE NISKO SPOJNEJ KLASY NA MNIEJSZE
         * ============================================================
         * - Gdy klasa ma 2 (lub wiecej) WYRAZNE grupy metod+pol, ktore ze
         *   soba nie wspoldzialaja, to sygnal do podzialu - kazda grupa
         *   staje sie osobna, mniejsza, bardziej spojna klasa.
         * - Korzysc: kazda z mniejszych klas jest LATWIEJSZA do
         *   zrozumienia, przetestowania i zmiany W IZOLACJI - zmiana
         *   logiki jednej grupy nie ryzykuje zepsucia drugiej.
         */
        demonstrateSplittingLowCohesionClass();

        /*
         * ============================================================
         * 🔍 KOLEJNOSC SKLADOWYCH KLASY (CONVENTION)
         * ============================================================
         * - Typowa, czytelna kolejnosc w klasie Javy: 1) stale (`static
         *   final`), 2) pola instancyjne, 3) konstruktory, 4) metody
         *   publiczne (w kolejnosci step-down z Lesson05), 5) metody
         *   prywatne (szczegoly implementacyjne, uzywane przez metody
         *   publiczne powyzej).
         * - To NIE jest "prawo fizyki" - rozne zespoly/style-guide'y (np.
         *   Google Java Style) moga miec drobne wariacje - kluczem jest
         *   KONSEKWENCJA w calym projekcie (patrz Lesson05: team rules).
         */
        demonstrateConventionalMemberOrder();

        /*
         * ============================================================
         * 📌 KLASY POWINNY BYC LATWE DO ROZSZERZANIA, TRUDNE DO ZEPSUCIA
         * ============================================================
         * - Dobrze zaprojektowana klasa pozwala DODAWAC nowe zachowanie BEZ
         *   MODYFIKOWANIA istniejacego, sprawdzonego kodu - to jest
         *   zapowiedz Open/Closed Principle (Lesson08), ktory poznasz
         *   szczegolowo w nastepnej-nastepnej lekcji.
         * - Male, spojne klasy z jasno okreslonymi odpowiedzialnosciami
         *   (efekt zasad z tej lekcji) sa FUNDAMENTEM, na ktorym w ogole
         *   mozna budowac takie rozszerzalne projekty - stąd ta lekcja jest
         *   "bramka wejsciowa" do calego bloku SOLID (Lesson07-11).
         */
        System.out.println("\nMale, spojne klasy = fundament pod zasady SOLID z kolejnych lekcji (07-11).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Rozmiar klasy mierzymy ODPOWIEDZIALNOSCIAMI ("test 1 zdania"
         *   bez spojnikow "i"), nie liczba linii.
         * - Enkapsulacja: minimalne publiczne API, szczegoly `private`.
         * - Spojnosc: metody powinny uzywac WIEKSZOSCI pol klasy - niska
         *   spojnosc = sygnal do podzialu na mniejsze klasy.
         * - Konwencjonalna kolejnosc skladowych: stale, pola, konstruktory,
         *   metody publiczne, metody prywatne.
         * - W kolejnej lekcji (Lesson07): Single Responsibility Principle -
         *   formalizacja "1 zdanie bez spojnika i" jako pierwsza z 5 zasad
         *   SOLID.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void demonstrateOneSentenceTest() {
        System.out.println("\n=== TEST '1 ZDANIA BEZ SPOJNIKA I' ===");

        System.out.println("[ZLA] 'InvoicePrinter liczy faktury I generuje PDF I wysyla e-mail I archiwizuje w chmurze.'");
        System.out.println("      -> 4 rozne odpowiedzialnosci w 1 zdaniu = klasa robi za duzo.");

        System.out.println("[DOBRA] 'InvoiceCalculator liczy sume faktury.' (1 czasownik, 0 spojnikow 'i')");
        System.out.println("        Osobno: PdfGenerator, EmailSender, CloudArchiver - kazda z 1 zdaniem.");
    }

    private static void demonstrateMinimalPublicInterface() {
        System.out.println("\n=== ZLA: NADMIAROWE PUBLICZNE API VS DOBRA: MINIMALNE API ===");

        BadPriceCalculator badCalc = new BadPriceCalculator();
        System.out.println("[ZLA] BadPriceCalculator eksponuje 4 metody publiczne, z ktorych");
        System.out.println("      3 sa tylko wewnetrznymi krokami obliczen: " + badCalc.calculateFinalPrice(100));

        GoodPriceCalculator goodCalc = new GoodPriceCalculator();
        System.out.println("[DOBRA] GoodPriceCalculator eksponuje TYLKO 1 metode publiczna: "
                + goodCalc.calculateFinalPrice(100));
    }

    /** ZLA: kroki posrednie (applyVat/applyDiscount/round) sa publiczne, mimo ze nikt z zewnatrz ich nie potrzebuje. */
    static class BadPriceCalculator {
        public double applyVat(double price) {
            return price * 1.23;
        }

        public double applyDiscount(double price) {
            return price * 0.9;
        }

        public double round(double price) {
            return Math.round(price * 100) / 100.0;
        }

        public double calculateFinalPrice(double basePrice) {
            return round(applyDiscount(applyVat(basePrice)));
        }
    }

    /** DOBRA: kroki posrednie sa `private` - publiczne API to TYLKO to, czego klient potrzebuje. */
    static class GoodPriceCalculator {
        public double calculateFinalPrice(double basePrice) {
            return round(applyDiscount(applyVat(basePrice)));
        }

        private double applyVat(double price) {
            return price * 1.23;
        }

        private double applyDiscount(double price) {
            return price * 0.9;
        }

        private double round(double price) {
            return Math.round(price * 100) / 100.0;
        }
    }

    private static void demonstrateLowVsHighCohesion() {
        System.out.println("\n=== NISKA SPOJNOSC VS WYSOKA SPOJNOSC ===");

        LowCohesionReportBox lowCohesion = new LowCohesionReportBox("Raport Q1", "jan@example.com", 42);
        System.out.println("[NISKA SPOJNOSC] formatTitle() uzywa TYLKO 'title': " + lowCohesion.formatTitle());
        System.out.println("[NISKA SPOJNOSC] formatRecipient() uzywa TYLKO 'recipientEmail': "
                + lowCohesion.formatRecipient());
        System.out.println("[NISKA SPOJNOSC] formatVisitorCount() uzywa TYLKO 'visitorCount': "
                + lowCohesion.formatVisitorCount());
        System.out.println("-> 3 metody, KAZDA uzywa TYLKO 1 z 3 pol - to sygnal, ze te 3 pola/metody");
        System.out.println("   NIE naleza logicznie do tej samej klasy (por. Data Clumps, Lesson14).");

        HighCohesionInvoiceSummary highCohesion = new HighCohesionInvoiceSummary(100.0, 0.1, 0.23);
        System.out.println("[WYSOKA SPOJNOSC] calculateFinalAmount() uzywa WSZYSTKICH 3 pol razem: "
                + highCohesion.calculateFinalAmount());
        System.out.println("[WYSOKA SPOJNOSC] describe() TEZ uzywa wszystkich 3 pol: " + highCohesion.describe());
    }

    /** NISKA SPOJNOSC: kazda metoda uzywa TYLKO JEDNEGO z 3 pol - pola/metody nie sa ze soba realnie powiazane. */
    static class LowCohesionReportBox {
        private final String title;
        private final String recipientEmail;
        private final int visitorCount;

        LowCohesionReportBox(String title, String recipientEmail, int visitorCount) {
            this.title = title;
            this.recipientEmail = recipientEmail;
            this.visitorCount = visitorCount;
        }

        String formatTitle() {
            return "Tytul: " + title;
        }

        String formatRecipient() {
            return "Do: " + recipientEmail;
        }

        String formatVisitorCount() {
            return "Odwiedzajacy: " + visitorCount;
        }
    }

    /** WYSOKA SPOJNOSC: obie metody uzywaja WSZYSTKICH pol razem - realnie ze soba powiazane. */
    static class HighCohesionInvoiceSummary {
        private final double baseAmount;
        private final double discountRate;
        private final double vatRate;

        HighCohesionInvoiceSummary(double baseAmount, double discountRate, double vatRate) {
            this.baseAmount = baseAmount;
            this.discountRate = discountRate;
            this.vatRate = vatRate;
        }

        double calculateFinalAmount() {
            double afterDiscount = baseAmount * (1 - discountRate);
            return afterDiscount * (1 + vatRate);
        }

        String describe() {
            return String.format("Kwota bazowa=%.2f, rabat=%.0f%%, VAT=%.0f%%, do zaplaty=%.2f",
                    baseAmount, discountRate * 100, vatRate * 100, calculateFinalAmount());
        }
    }

    private static void demonstrateSplittingLowCohesionClass() {
        System.out.println("\n=== PODZIAL NISKO SPOJNEJ KLASY NA 2 MNIEJSZE ===");

        // Zamiast LowCohesionReportBox (3 niepowiazane grupy) - 2 male, spojne klasy:
        ReportTitle reportTitle = new ReportTitle("Raport Q1");
        VisitorStats visitorStats = new VisitorStats(42);

        System.out.println("ReportTitle (spojna, 1 pole + 1 metoda uzywajaca go): " + reportTitle.format());
        System.out.println("VisitorStats (spojna, 1 pole + 1 metoda uzywajaca go): " + visitorStats.format());
        System.out.println("-> Kazda z tych klas jest teraz TESTOWALNA i ZMIENIALNA niezaleznie od drugiej.");
    }

    static class ReportTitle {
        private final String title;

        ReportTitle(String title) {
            this.title = title;
        }

        String format() {
            return "Tytul: " + title;
        }
    }

    static class VisitorStats {
        private final int visitorCount;

        VisitorStats(int visitorCount) {
            this.visitorCount = visitorCount;
        }

        String format() {
            return "Odwiedzajacy: " + visitorCount;
        }
    }

    private static void demonstrateConventionalMemberOrder() {
        System.out.println("\n=== KONWENCJONALNA KOLEJNOSC SKLADOWYCH KLASY ===");
        OrderedExampleClass example = new OrderedExampleClass("Laptop", 3000.0);
        System.out.println(example.describe());
        System.out.println("Kolejnosc w OrderedExampleClass: stala DEFAULT_TAX_RATE -> pola -> konstruktor");
        System.out.println("-> metoda publiczna describe() -> metoda prywatna calculatePriceWithTax().");
    }

    /**
     * Przyklad KONWENCJONALNEJ kolejnosci skladowych klasy: stale, pola,
     * konstruktor, metody publiczne (step-down), metody prywatne na koncu.
     */
    static class OrderedExampleClass {
        private static final double DEFAULT_TAX_RATE = 0.23;

        private final String productName;
        private final double basePrice;

        OrderedExampleClass(String productName, double basePrice) {
            this.productName = productName;
            this.basePrice = basePrice;
        }

        public String describe() {
            return productName + ": " + calculatePriceWithTax();
        }

        private double calculatePriceWithTax() {
            return basePrice * (1 + DEFAULT_TAX_RATE);
        }
    }
}
