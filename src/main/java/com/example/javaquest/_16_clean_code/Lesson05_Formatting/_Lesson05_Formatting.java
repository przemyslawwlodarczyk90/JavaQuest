package com.example.javaquest._16_clean_code.Lesson05_Formatting;

public class _Lesson05_Formatting {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: FORMATOWANIE KODU ===");

        /*
         * ============================================================
         * 📦 DLACZEGO FORMATOWANIE MA ZNACZENIE?
         * ============================================================
         * - Robert C. Martin: kod, ktory piszesz DZISIAJ, bedzie CZYTANY
         *   dziesiatki razy przez innych (i przez Ciebie samego za pol roku) -
         *   formatowanie to inwestycja w KOMUNIKACJE, nie kosmetyka.
         * - "Metafora gazety" (newspaper metaphor): dobrze napisany artykul
         *   ma NAGLOWEK (mowi, o czym jest caly tekst), pierwszy akapit
         *   (streszcza najwazniejsze fakty), a kolejne akapity schodza w
         *   coraz wieksze szczegoly. Klasa powinna dzialac tak samo: nazwa
         *   klasy + pola na gorze mowia "co to jest", metody publiczne wysokiego
         *   poziomu na poczatku, prywatne szczegoly implementacyjne nizej.
         * - Formatowanie dzieli sie na 2 kategorie: PIONOWE (vertical - jak
         *   kod jest ulozony od gory do dolu w pliku) i POZIOME (horizontal -
         *   jak dlugie sa linie, jak uzyta jest spacja/wciecia).
         */
        System.out.println("Formatowanie = komunikacja z przyszlym czytelnikiem kodu, nie kosmetyka.");

        /*
         * ============================================================
         * 🔹 FORMATOWANIE PIONOWE: ROZMIAR PLIKU/KLASY
         * ============================================================
         * - Mniejsze pliki sa LATWIEJSZE do zrozumienia niz wieksze - Martin
         *   podaje (dla Javy) orientacyjnie 200-500 linii jako rozsadny
         *   rozmiar wiekszosci klas (nie sztywna regula, ale sygnal
         *   ostrzegawczy przy znacznym przekroczeniu).
         * - Klasa/plik, ktory rosnie bez konca, to czesto sygnal naruszenia
         *   Single Responsibility Principle (Lesson07) - zbyt wiele
         *   odpowiedzialnosci w jednym miejscu.
         */
        System.out.println("Orientacyjny rozmiar klasy: 200-500 linii - znaczne przekroczenie to sygnal ostrzegawczy.");

        /*
         * ============================================================
         * 🔍 PIONOWA "OTWARTOSC" (VERTICAL OPENNESS) - PUSTE LINIE
         * ============================================================
         * - Pusta linia miedzy dwoma blokami kodu to SYGNAL dla czytelnika:
         *   "to sa dwie oddzielne mysli/koncepcje".
         * - Brak pustych linii w ogole zlepia rozne koncepcje w jedna
         *   nieczytelna masse - nadmiar pustych linii (np. po kazdej
         *   instrukcji) rozbija logiczna calosc na fragmenty.
         */
        demonstrateVerticalOpennessGoodVsBad();

        /*
         * ============================================================
         * 🔹 PIONOWA GĘSTOŚĆ (VERTICAL DENSITY) - CO NALEZY DO SIEBIE
         * ============================================================
         * - Linie kodu, ktore sa ze soba SCISLE powiazane, powinny stac
         *   BLISKO siebie (bez pustych linii/komentarzy rozdzielajacych) -
         *   to sygnalizuje "to jest jedna, spojna mysl".
         * - Komentarz wstawiony W SRODKU scisle powiazanego bloku kodu
         *   rozbija te gestosc i sugeruje (czesto mylnie) podzial, ktorego
         *   tam nie ma.
         */
        demonstrateVerticalDensityGoodVsBad();

        /*
         * ============================================================
         * 🔍 PIONOWA ODLEGLOSC (VERTICAL DISTANCE) - 3 ZASADY
         * ============================================================
         * 1) ZMIENNE LOKALNE powinny byc zadeklarowane possible NAJBLIZEJ
         *    miejsca pierwszego uzycia - NIE wszystkie na gorze metody
         *    (styl typowy dla starszych jezykow jak C, gdzie deklaracje
         *    musialy byc na poczatku bloku).
         * 2) FUNKCJE POWIAZANE (jedna wywoluje druga, albo robia podobna
         *    rzecz) powinny stac BLISKO siebie w pliku - pionowa bliskosc
         *    funkcji odzwierciedla ich logiczna bliskosc (tzw. "conceptual
         *    affinity").
         * 3) ZMIENNE INSTANCYJNE deklarujemy w JEDNYM, przewidywalnym
         *    miejscu (Martin rekomenduje na GORZE klasy) - czytelnik zawsze
         *    wie, gdzie ich szukac, bez przeszukiwania calego pliku.
         */
        demonstrateVerticalDistanceGoodVsBad();

        /*
         * ============================================================
         * 🔹 PIONOWE UPORZADKOWANIE (VERTICAL ORDERING) - CALLER NAD CALLEE
         * ============================================================
         * - Zasada "step-down rule": funkcja WYWOLUJACA powinna stac NAD
         *   funkcja WYWOLYWANA - czytajac plik od gory do dolu, schodzimy w
         *   coraz WIEKSZE detale (dokladnie jak w metaforze gazety).
         * - Efekt: kazdy plik da sie przeczytac "jak artykul" - najpierw
         *   ogolny sens (metoda main/orkiestrator), potem kolejne poziomy
         *   szczegolu.
         */
        System.out.println("\nZasada step-down: caller nad callee - czytasz plik od ogolu do szczegolu, jak artykul.");
        demonstrateStepDownOrdering();

        /*
         * ============================================================
         * 📌 FORMATOWANIE POZIOME: DLUGOSC LINII I WCIECIA
         * ============================================================
         * - Zbyt dluga linia (np. 200+ znakow) zmusza czytelnika do
         *   przewijania w poziomie - traci sie mozliwosc "objecia calej
         *   linii jednym rzutem oka". Typowa rekomendacja to ok. 100-120
         *   znakow (Martin w Clean Code sugerowal nawet mniej - w praktyce
         *   dzisiejszych szerokich ekranow 100-120 to rozsadny kompromis,
         *   czesto wymuszany przez formatter/linter w konfiguracji zespolu).
         * - WCIECIA (indentation) odzwierciedlaja HIERARCHIE kodu (blok w
         *   bloku w bloku) - to WIZUALNA mapa struktury; nigdy nie "spłaszczaj"
         *   wciec, zeby zaoszczedzic linie, bo to niszczy te mape.
         */
        demonstrateLineLengthAndIndentation();

        /*
         * ============================================================
         * 🔍 UNIKAJ SZTUCZNEGO WYRÓWNANIA W PIONIE (HORIZONTAL ALIGNMENT)
         * ============================================================
         * - Reczne wyrownywanie znakow `=` czy typow zmiennych w kolumnach
         *   (przez wielokrotne spacje) WYGLADA schludnie w momencie pisania,
         *   ale przy KAZDEJ kolejnej zmianie (np. dluzsza nazwa zmiennej)
         *   trzeba przerownowac WSZYSTKIE sasiednie linie - to zbedny koszt
         *   utrzymania, ktory nowoczesne formattery (i tak) ignoruja.
         * - Java (w odroznieniu np. od jezykow z gofmt) nie ma jednego
         *   "kanonicznego" formattera narzucanego przez sam jezyk - dlatego
         *   TYM WAZNIEJSZE jest ustalenie 1 stylu w zespole (patrz nizej).
         */
        demonstrateHorizontalAlignmentAntiPattern();

        /*
         * ============================================================
         * 📌 SPOJNOSC ZESPOLOWA (TEAM RULES) I NARZEDZIA
         * ============================================================
         * - Formatowanie to NIE kwestia osobistego gustu w projekcie
         *   zespolowym - zespol powinien uzgodnic JEDEN zestaw regul (styl
         *   nawiasow klamrowych, dlugosc linii, kolejnosc modyfikatorow
         *   itd.) i stosowac go KONSEKWENTNIE we wszystkich plikach.
         * - W praktyce: automatyczne formattery (np. wbudowany formatter
         *   IntelliJ/Eclipse, google-java-format, Spotless w Mavenie/Gradle)
         *   eliminuja spory o styl - formatowanie stosuje sie automatycznie
         *   przy zapisie/buildzie, zamiast recznie i niekonsekwentnie.
         * - Static analysis tools (Lesson20: PMD/SpotBugs/Checkstyle) czesto
         *   TEZ pilnuja regul formatowania (dlugosc linii, wciecia) jako
         *   czesc szerszej analizy jakosci kodu.
         */
        System.out.println("\nFormatowanie = zespolowa umowa + automatyczny formatter, NIE indywidualny gust.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Metafora gazety: klasa czytana od gory (ogol) do dolu (szczegol).
         * - Pionowe: rozmiar pliku, otwartosc (puste linie miedzy mysli),
         *   gestosc (powiazany kod razem), odleglosc (zmienne blisko
         *   uzycia, powiazane funkcje blisko siebie), step-down (caller nad
         *   callee).
         * - Poziome: rozsadna dlugosc linii, wciecia jako mapa struktury,
         *   unikanie recznego wyrownywania w kolumnach.
         * - Formatowanie = zespolowa umowa + automatyczny formatter.
         * - W kolejnej lekcji (Lesson06): jak projektowac SAME KLASY -
         *   rozmiar, odpowiedzialnosci, kolejnosc skladowych (co jest
         *   naturalnym rozszerzeniem "pionowego uporzadkowania" z tej lekcji
         *   na poziom calej klasy).
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void demonstrateVerticalOpennessGoodVsBad() {
        System.out.println("\n=== ZLA: BRAK PUSTYCH LINII VS DOBRA: PUSTA LINIA MIEDZY MYSLAMI ===");
        System.out.println("[ZLA] (zlepione linie, brak sygnalu 'to sa 2 rozne kroki'):");
        System.out.println("  int total = calculateTotal();");
        System.out.println("  applyDiscount(total);");
        System.out.println("  printReceipt(total);");
        System.out.println("  saveOrderToDatabase(total);");
        System.out.println("[DOBRA] (pusta linia oddziela 'liczenie' od 'efektow ubocznych'):");
        System.out.println("  int total = calculateTotal();");
        System.out.println("  applyDiscount(total);");
        System.out.println();
        System.out.println("  printReceipt(total);");
        System.out.println("  saveOrderToDatabase(total);");
    }

    private static void demonstrateVerticalDensityGoodVsBad() {
        System.out.println("\n=== ZLA: KOMENTARZ ROZBIJAJACY SPOJNY BLOK VS DOBRA: BLOK RAZEM ===");

        // ZLA: komentarz wstawiony w SRODKU jednej, spojnej mysli sugeruje
        // podzial, ktorego tu logicznie nie ma:
        int price = 100;
        // sprawdzamy czy cena jest dodatnia
        boolean isValid = price > 0;
        // liczymy podatek
        double tax = isValid ? price * 0.23 : 0;
        System.out.println("[ZLA] price=" + price + ", isValid=" + isValid + ", tax=" + tax);

        // DOBRA: ta sama logika, bez sztucznego rozbicia - jest to JEDNA
        // spojna mysl ("policz podatek dla poprawnej ceny"), wiec stoi razem:
        int priceGood = 100;
        boolean isValidGood = priceGood > 0;
        double taxGood = isValidGood ? priceGood * 0.23 : 0;
        System.out.println("[DOBRA] price=" + priceGood + ", isValid=" + isValidGood + ", tax=" + taxGood);
    }

    private static void demonstrateVerticalDistanceGoodVsBad() {
        System.out.println("\n=== ZLA: ZMIENNE DEKLAROWANE Z DALA OD UZYCIA VS DOBRA: BLISKO UZYCIA ===");

        // ZLA: 'unusedForNow' zadeklarowana dawno przed faktycznym uzyciem -
        // czytelnik musi "pamietac" o niej przez wiele linii kodu:
        String unusedForNow = "raport-2024";
        int a = 5;
        int b = 10;
        int sum = a + b;
        System.out.println("[ZLA] suma=" + sum + " (zmienna 'unusedForNow' zadeklarowana daleko od uzycia)");
        System.out.println("      uzycie 'unusedForNow' dopiero tutaj: " + unusedForNow);

        // DOBRA: zmienna zadeklarowana TUZ przed pierwszym (i jedynym) uzyciem:
        int aGood = 5;
        int bGood = 10;
        int sumGood = aGood + bGood;
        System.out.println("[DOBRA] suma=" + sumGood);
        String reportName = "raport-2024";
        System.out.println("[DOBRA] uzycie 'reportName' zaraz po deklaracji: " + reportName);
    }

    private static void demonstrateStepDownOrdering() {
        // Ta metoda demonstruje SAMA SIEBIE - jest orkiestratorem (wysoki
        // poziom abstrakcji), a wywolywane metody stoja NIZEJ w pliku, coraz
        // bardziej szczegolowe - dokladnie zasada step-down.
        String summary = buildDailySummary();
        System.out.println(summary);
    }

    // Krok 1 (wysoki poziom, tuz pod callerem)
    private static String buildDailySummary() {
        int visitors = countVisitors();
        double revenue = calculateRevenue();
        return formatSummaryLine(visitors, revenue);
    }

    // Krok 2 (nizszy poziom, wywolywany przez buildDailySummary)
    private static int countVisitors() {
        return 128;
    }

    private static double calculateRevenue() {
        return 4599.50;
    }

    // Krok 3 (najnizszy poziom szczegolu - formatowanie tekstu)
    private static String formatSummaryLine(int visitors, double revenue) {
        return String.format("Odwiedzajacy: %d, przychod: %.2f PLN", visitors, revenue);
    }

    private static void demonstrateLineLengthAndIndentation() {
        System.out.println("\n=== ZLA: JEDNA DLUGA LINIA VS DOBRA: ROZBITA Z WCIECIAMI ===");

        // ZLA: wszystko w 1 linii - trudno objac wzrokiem, trudno zrozumiec
        // hierarchie warunkow bez czytania calej linii od poczatku do konca.
        int age = 25; boolean hasLicense = true; boolean hasInsurance = true; String result = age >= 18 && hasLicense && hasInsurance ? "MOZE PROWADZIC" : "NIE MOZE PROWADZIC";
        System.out.println("[ZLA, 1 linia] " + result);

        // DOBRA: rozbite na czytelne linie, wciecia pokazuja strukture warunku.
        int ageGood = 25;
        boolean hasLicenseGood = true;
        boolean hasInsuranceGood = true;
        boolean canDrive = ageGood >= 18
                && hasLicenseGood
                && hasInsuranceGood;
        System.out.println("[DOBRA, rozbite] " + (canDrive ? "MOZE PROWADZIC" : "NIE MOZE PROWADZIC"));
    }

    private static void demonstrateHorizontalAlignmentAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: RECZNE WYROWNANIE W KOLUMNACH ===");

        // ZLA: reczne wyrownanie znakow '=' spacjami - wyglada schludnie
        // DOPOKI ktos nie doda dluzszej nazwy zmiennej (wtedy trzeba
        // przerownowac WSZYSTKIE linie ponizej):
        System.out.println("[ZLA - reczne wyrownanie]");
        System.out.println("  int    id          = 1;");
        System.out.println("  String name        = \"Ala\";");
        System.out.println("  double accountBalance = 250.0; // ta linia juz 'psuje' wyrownanie!");

        // DOBRA: bez recznego wyrownania - pojedyncza spacja, formatter
        // zespolowy i tak zadba o spojnosc, bez recznej pracy:
        System.out.println("[DOBRA - bez recznego wyrownania, spojnosc przez formatter]");
        System.out.println("  int id = 1;");
        System.out.println("  String name = \"Ala\";");
        System.out.println("  double accountBalance = 250.0;");
    }
}
