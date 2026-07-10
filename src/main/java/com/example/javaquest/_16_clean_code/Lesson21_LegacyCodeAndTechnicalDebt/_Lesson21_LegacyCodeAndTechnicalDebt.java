package com.example.javaquest._16_clean_code.Lesson21_LegacyCodeAndTechnicalDebt;

import java.util.ArrayList;
import java.util.List;

public class _Lesson21_LegacyCodeAndTechnicalDebt {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 21: KOD LEGACY I DLUG TECHNICZNY ===");

        /*
         * ============================================================
         * 📦 DEFINICJA "KODU LEGACY" (MICHAEL FEATHERS)
         * ============================================================
         * - Potoczne rozumienie "legacy" to po prostu "stary kod" - Michael
         *   Feathers ("Working Effectively with Legacy Code", 2004)
         *   zaproponowal DEFINICJE precyzyjniejsza i bardziej uzyteczna:
         *   "kod legacy to kod BEZ TESTOW".
         * - Dlaczego akurat testy? Bo bez nich KAZDA zmiana jest skokiem w
         *   ciemno - nie masz jak SZYBKO zweryfikowac, czy Twoja zmiana
         *   zepsula cos innego (dokladnie "siatka bezpieczenstwa" z
         *   Lesson15). Kod napisany WCZORAJ, ale bez testow, jest wg tej
         *   definicji juz "legacy".
         * - Ta definicja jest UZYTECZNA, bo wskazuje KONKRETNE dzialanie
         *   naprawcze: dodaj testy, a kod przestaje byc "legacy" (nawet
         *   jesli nadal wymaga refaktoryzacji).
         */
        System.out.println("Legacy code (Feathers) = kod BEZ TESTOW, niezaleznie od tego, jak 'stary' jest kalendarzowo.");

        /*
         * ============================================================
         * 🔹 DLUG TECHNICZNY (TECHNICAL DEBT) - METAFORA WARDA CUNNINGHAMA
         * ============================================================
         * - Ward Cunningham (1992) ukul metafore: pojscie na "skrot" w
         *   projekcie (np. skopiowanie kodu zamiast wydzielenia wspolnej
         *   metody, zeby zdazyc na termin) jest jak zaciagniecie POZYCZKI -
         *   przyspiesza dostarczenie TERAZ, ale generuje "odsetki"
         *   (dodatkowy koszt KAZDEJ przyszlej zmiany w tym miejscu), az do
         *   momentu "splaty" (refaktoryzacji).
         * - Kluczowe: dlug NIE JEST z definicji zly - czasem to SWIADOMA,
         *   ROZSADNA decyzja biznesowa (np. "wydajmy MVP teraz, uporzadkujemy
         *   pozniej") - problem pojawia sie, gdy dlug jest NIESWIADOMY
         *   (nikt nie wie, ze go zaciagnieto) LUB nigdy nie zostaje splacony.
         */
        System.out.println("Dlug techniczny = SWIADOMY skrot z 'odsetkami' (kosztem kazdej przyszlej zmiany) - nie zawsze zly.");

        /*
         * ============================================================
         * 🔍 KWADRANT DLUGU TECHNICZNEGO (MARTIN FOWLER)
         * ============================================================
         * - Fowler podzielil dlug na 4 cwiartki wg 2 osi: ROZWAZNY
         *   (reckless) vs OSTROZNY (prudent), oraz CELOWY (deliberate) vs
         *   PRZYPADKOWY (inadvertent):
         *   1) OSTROZNY + CELOWY: "wiemy, ze idziemy na skrot, i wiemy
         *      dlaczego" (np. presja terminu, swiadoma decyzja) - NAJLEPSZY
         *      rodzaj dlugu, bo jest udokumentowany i zaplanowany do splaty.
         *   2) ROZWAZNY + CELOWY: "nie mamy czasu na porzadny projekt" (bez
         *      planu splaty) - ryzykowne, ale przynajmniej SWIADOME.
         *   3) OSTROZNY + PRZYPADKOWY: "teraz dopiero widzimy, jak
         *      powinnismy to byli zaprojektowac" - naturalny efekt UCZENIA
         *      SIE w trakcie projektu, nie do uniknięcia.
         *   4) ROZWAZNY + PRZYPADKOWY: "co to sa wzorce projektowe?" - dlug
         *      powstajacy z BRAKU WIEDZY, najgorszy, bo niewidoczny az do
         *      momentu, gdy zaczyna boleć.
         */
        demonstrateDebtQuadrantExample();

        /*
         * ============================================================
         * 🔹 "SZEW" (SEAM) - MIEJSCE, GDZIE MOZNA ZMIENIC ZACHOWANIE BEZ EDYCJI
         * ============================================================
         * - Feathers zdefiniowal "seam" (szew) jako miejsce w kodzie, gdzie
         *   MOZESZ zmienic zachowanie programu BEZ edytowania kodu W TYM
         *   MIEJSCU - najczestszy "szew" w Javie to PUNKT WSTRZYKIWANIA
         *   ZALEZNOSCI (interfejs + konstruktor, patrz Lesson11: DIP).
         * - Legacy kod CZESTO nie ma zadnych szwow - klasy tworza swoje
         *   zaleznosci przez `new` w srodku (dokladnie anty-wzorzec z
         *   Lesson11) - NIE MOZNA podmienic np. polaczenia z baza danych
         *   na atrape do testu, bo nie ma "gdzie" tego zrobic.
         * - Pierwszym krokiem pracy z legacy kodem jest czesto
         *   WPROWADZENIE szwu (np. wydzielenie interfejsu z konkretnej
         *   klasy) - MINIMALNA zmiana, ktora umozliwia PozNIEJSZE
         *   testowanie i refaktoryzacje.
         */
        demonstrateIntroducingSeamForTestability();

        /*
         * ============================================================
         * 🔍 TESTY CHARAKTERYZUJACE JAKO PIERWSZY KROK (RECAP Z LESSON15)
         * ============================================================
         * - Lesson15 wprowadzil testy charakteryzujace (characterization
         *   tests) jako ogolna technike - w kontekscie LEGACY KODU staja
         *   sie one PIERWSZYM, obowiazkowym krokiem: zanim cokolwiek
         *   zmienisz, udokumentuj (przez testy) OBECNE zachowanie, jakie
         *   by ono nie bylo (nawet jesli samo w sobie jest watpliwe).
         * - "Legacy Code Change Algorithm" (Feathers): (1) zidentyfikuj
         *   miejsca zmiany, (2) znajdz "punkty testowe" (gdzie mozesz
         *   obserwowac zachowanie), (3) przerwij zaleznosci utrudniajace
         *   testowanie (wprowadz szew), (4) napisz testy, (5) wprowadz
         *   zmiane/refaktoryzacje.
         */
        demonstrateCharacterizationTestOnLegacyMethod();

        /*
         * ============================================================
         * 🔹 WZORZEC "STRANGLER FIG" - STOPNIOWA WYMIANA SYSTEMU
         * ============================================================
         * - Nazwa od "figowca-dusiciela" (Martin Fowler, popularyzujac
         *   koncept od 2004) - roslina, ktora stopniowo OTACZA i ZASTĘPUJE
         *   drzewo-gospodarza, az w koncu oryginal moze zniknac, a nowa
         *   struktura stoi samodzielnie.
         * - W praktyce: NOWA implementacja jest budowana OBOK starej, a
         *   ROUTER/fasada stopniowo przekierowuje coraz WIECEJ ruchu z
         *   legacy na nowa implementacje - CALY system NIGDY nie jest
         *   "wylaczony na przebudowe" (w odroznieniu od ryzykownego
         *   "wielkiego przepisania od zera", odradzanego juz w Lesson15).
         */
        demonstrateStranglerFigPattern();

        /*
         * ============================================================
         * 🔍 SPLACANIE DLUGU: STOPNIOWO, PRZY OKAZJI (BOY SCOUT RULE)
         * ============================================================
         * - Rzadko oplaca sie "zatrzymac wszystko" i splacic caly dlug
         *   techniczny naraz (patrz Lesson15: kiedy NIE refaktoryzowac) -
         *   praktyczna strategia to zasada skauta (Lesson15): przy KAZDEJ
         *   okazji pracy w danym miejscu kodu, zostaw je odrobine
         *   czystsze - male, systematyczne splaty zamiast jednorazowej,
         *   ryzykownej "wielkiej spaty".
         */
        System.out.println("\nSplacaj dlug STOPNIOWO (zasada skauta, Lesson15) - rzadko oplaca sie 'wielka jednorazowa splata'.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Legacy code (Feathers) = kod BEZ testow, niezaleznie od wieku.
         * - Dlug techniczny (Cunningham) = swiadomy lub nieswiadomy skrot z
         *   "odsetkami" - kwadrant Fowlera rozroznia 4 rodzaje.
         * - Szew (seam) = miejsce, gdzie mozna zmienic zachowanie bez
         *   edycji - czesto trzeba go SWIADOMIE wprowadzic do legacy kodu.
         * - Testy charakteryzujace jako pierwszy, obowiazkowy krok przed
         *   zmiana legacy kodu (Legacy Code Change Algorithm).
         * - Strangler Fig: stopniowa wymiana systemu OBOK dzialajacego
         *   starego, bez "wielkiego przepisania".
         * - Splacaj dlug stopniowo, zasada skauta, nie jednorazowo.
         * - W kolejnej, OSTATNIEJ lekcji rozdzialu (Lesson22): najlepsze
         *   praktyki code review + kapsztonowy projekt laczacy CALY
         *   rozdzial `_16_clean_code`.
         */
        System.out.println("\n=== KONIEC LEKCJI 21 ===");
    }

    private static void demonstrateDebtQuadrantExample() {
        System.out.println("\n=== KWADRANT DLUGU TECHNICZNEGO - PRZYKLADY ===");

        System.out.println("[Ostrozny+Celowy] 'Wiemy, ze ten walidator jest uproszczony - w tickecie #123 zapisalismy plan rozbudowy po MVP.'");
        System.out.println("[Rozwazny+Celowy] 'Nie mamy czasu na testy, wdrazamy jak jest.' (bez planu naprawy)");
        System.out.println("[Ostrozny+Przypadkowy] 'Teraz, po 3 miesiacach, widzimy, ze ten interfejs powinien byc mniejszy (patrz ISP, Lesson10).'");
        System.out.println("[Rozwazny+Przypadkowy] Kod napisany bez znajomosci podstawowych zasad z tego rozdzialu - dlug niewidoczny do czasu, az zaboli.");
    }

    private static void demonstrateIntroducingSeamForTestability() {
        System.out.println("\n=== WPROWADZENIE SZWU (SEAM) DO LEGACY KODU ===");

        LegacyOrderValidator legacy = new LegacyOrderValidator();
        System.out.println("[LEGACY] Brak szwu - LegacyOrderValidator tworzy DateProvider przez 'new' w srodku,");
        System.out.println("         NIE da sie podmienic 'biezacej daty' w tescie: " + legacy.isOrderStillValid("2024-01-01"));

        SeamOrderValidator withSeam = new SeamOrderValidator(new FixedDateProvider("2024-06-15"));
        System.out.println("[Z SZWEM] SeamOrderValidator przyjmuje DateProvider przez konstruktor (Lesson11: DIP) -");
        System.out.println("          mozemy podac DOWOLNA, przewidywalna date do testu: "
                + withSeam.isOrderStillValid("2024-01-01"));
    }

    /** LEGACY: sama tworzy swoja zaleznosc - BRAK szwu, nie da sie podmienic w tescie. */
    static class LegacyOrderValidator {
        boolean isOrderStillValid(String orderDate) {
            String today = new RealDateProvider().getTodayAsString(); // 'new' w srodku - brak szwu!
            return orderDate.compareTo(today) <= 0;
        }
    }

    static class RealDateProvider {
        String getTodayAsString() {
            return java.time.LocalDate.now().toString();
        }
    }

    /** Z SZWEM: zaleznosc PRZYJMOWANA przez konstruktor - mozna podmienic w tescie (Lesson11: constructor injection). */
    interface DateProvider {
        String getTodayAsString();
    }

    static class FixedDateProvider implements DateProvider {
        private final String fixedDate;

        FixedDateProvider(String fixedDate) {
            this.fixedDate = fixedDate;
        }

        @Override
        public String getTodayAsString() {
            return fixedDate;
        }
    }

    static class SeamOrderValidator {
        private final DateProvider dateProvider;

        SeamOrderValidator(DateProvider dateProvider) {
            this.dateProvider = dateProvider;
        }

        boolean isOrderStillValid(String orderDate) {
            return orderDate.compareTo(dateProvider.getTodayAsString()) <= 0;
        }
    }

    private static void demonstrateCharacterizationTestOnLegacyMethod() {
        System.out.println("\n=== TEST CHARAKTERYZUJACY JAKO PIERWSZY KROK PRZED ZMIANA ===");

        // Metoda "legacy" z nieoczywista logika progow - NIE wiemy, czy to
        // "poprawne" zachowanie biznesowe, ale MUSIMY je zachowac przy zmianie.
        List<String> characterizationResults = new ArrayList<>();
        int[] testScores = {0, 49, 50, 74, 75, 89, 90, 100};
        for (int score : testScores) {
            characterizationResults.add(score + " -> " + classifyLegacyScore(score));
        }

        System.out.println("Udokumentowane (charakteryzujace) zachowanie PRZED jakakolwiek zmiana:");
        characterizationResults.forEach(line -> System.out.println("  " + line));
        System.out.println("Ta lista to teraz SIATKA BEZPIECZENSTWA (Lesson15) - kazda przyszla zmiana");
        System.out.println("musi dawac IDENTYCZNE wyniki dla tych samych 8 wartosci wejsciowych.");
    }

    /** "Legacy" metoda - dziala, ale nikt juz nie pamieta, CZY progi 50/75/90 sa celowe. */
    private static String classifyLegacyScore(int score) {
        if (score >= 90) return "A";
        if (score >= 75) return "B";
        if (score >= 50) return "C";
        return "F";
    }

    private static void demonstrateStranglerFigPattern() {
        System.out.println("\n=== WZORZEC STRANGLER FIG - STOPNIOWA WYMIANA ===");

        StranglerRouter router = new StranglerRouter();

        System.out.println("[0% ruchu na nowej implementacji] " + router.calculateShipping("PL", 2.5));

        router.setNewImplementationTrafficPercent(50);
        System.out.println("[50% ruchu na nowej implementacji] Klienci 1 i 2:");
        System.out.println("  klient 1: " + router.calculateShippingForCustomer(1, "PL", 2.5));
        System.out.println("  klient 2: " + router.calculateShippingForCustomer(2, "PL", 2.5));

        router.setNewImplementationTrafficPercent(100);
        System.out.println("[100% ruchu na nowej implementacji] " + router.calculateShipping("PL", 2.5));
        System.out.println("-> Legacy implementacja moze teraz zostac bezpiecznie USUNIETA - caly ruch juz jej nie uzywa.");
    }

    interface ShippingCalculator {
        double calculate(String country, double weightKg);
    }

    /** Stara, dzialajaca implementacja - zostaje NIETKNIETA przez caly proces migracji. */
    static class LegacyShippingCalculator implements ShippingCalculator {
        @Override
        public double calculate(String country, double weightKg) {
            return weightKg * 2.0 + ("PL".equals(country) ? 0 : 5.0);
        }
    }

    /** Nowa implementacja - budowana OBOK starej, stopniowo przejmuje ruch. */
    static class ModernShippingCalculator implements ShippingCalculator {
        @Override
        public double calculate(String country, double weightKg) {
            return weightKg * 1.8 + ("PL".equals(country) ? 0 : 4.5); // np. wynegocjowane nowe stawki
        }
    }

    /** Router/fasada - decyduje, KTORA implementacja obsluzy dane wywolanie (procent ruchu). */
    static class StranglerRouter {
        private final ShippingCalculator legacy = new LegacyShippingCalculator();
        private final ShippingCalculator modern = new ModernShippingCalculator();
        private int newImplementationTrafficPercent = 0;

        void setNewImplementationTrafficPercent(int percent) {
            this.newImplementationTrafficPercent = percent;
        }

        double calculateShipping(String country, double weightKg) {
            return newImplementationTrafficPercent >= 100 ? modern.calculate(country, weightKg) : legacy.calculate(country, weightKg);
        }

        double calculateShippingForCustomer(int customerId, String country, double weightKg) {
            boolean routeToModern = (customerId % 100) < newImplementationTrafficPercent;
            return routeToModern ? modern.calculate(country, weightKg) : legacy.calculate(country, weightKg);
        }
    }
}
