package com.example.javaquest._16_clean_code.Lesson03_Comments;

public class _Lesson03_Comments {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: KOMENTARZE ===");

        /*
         * ============================================================
         * 🔹 KOMENTARZ TO ZAWSZE "PORAZKA" WYRAZENIA CZEGOS W KODZIE
         * ============================================================
         * - Robert C. Martin: "kazdy komentarz to przyznanie sie do porazki
         *   w wyrazeniu intencji przez sam kod". To NIE znaczy "nigdy nie
         *   pisz komentarzy" - znaczy "najpierw sprobuj, czy da sie to samo
         *   wyrazic lepsza nazwa/struktura, ZANIM dopiszesz komentarz".
         * - Powod: komentarz i kod moga sie ROZJECHAC. Kod jest wykonywany
         *   przez kompilator/JVM - MUSI byc aktualny, zeby program dzialal.
         *   Komentarz jest tylko tekstem - nikt (i nic) nie wymusza jego
         *   aktualizacji, gdy kod sie zmienia.
         * - Wniosek: komentarze sa NARZEDZIEM, nie celem samym w sobie -
         *   uzywaj ich tam, gdzie kod SAM w sobie nie moze wyrazic czegos
         *   waznego (patrz sekcje ponizej).
         */
        System.out.println("\nZasada: najpierw probuj wyrazic intencje w kodzie (nazwa/struktura), potem w komentarzu.");

        /*
         * ============================================================
         * 🔍 DOBRE KOMENTARZE (1): WYJASNIENIE "DLACZEGO", NIE "CO"
         * ============================================================
         * - Kod sam mowi CO robi (kompilator to wykonuje). Kod NIE mowi
         *   DLACZEGO ten konkretny sposob zostal wybrany - to jedyna
         *   informacja, ktorej sam kod nigdy nie przekaze.
         * - Dobry komentarz "dlaczego" ratuje przyszlego programiste przed
         *   przypadkowym "uproszczeniem" kodu, ktore zlamie cos nieoczywistego
         *   (np. obejscie buga w bibliotece, wymog zgodnosci prawnej).
         */
        demonstrateWhyCommentIsValuable();

        /*
         * ============================================================
         * 🔹 DOBRE KOMENTARZE (2): OSTRZEZENIA, TODO, JAVADOC PUBLICZNEGO API
         * ============================================================
         * - Ostrzezenie (warning comment) - informuje o NIEOCZYWISTYM ryzyku,
         *   np. "// UWAGA: ta metoda NIE jest bezpieczna watkowo".
         * - TODO - jawnie oznacza wiadomy, tymczasowy brak/uproszczenie,
         *   najlepiej z odwolaniem do zadania/ticketu (np. "// TODO(JIRA-123):
         *   dodac obsluge waluty EUR").
         * - Javadoc publicznego API - jedyny KONIECZNY typ komentarza w
         *   dobrym kodzie: opisuje KONTRAKT metody/klasy (co przyjmuje, co
         *   zwraca, jakie wyjatki rzuca) dla kogos, kto NIE widzi implementacji
         *   (np. uzytkownik biblioteki) - to nie "co robi kod", tylko "jak
         *   z tego bezpiecznie korzystac".
         */
        demonstrateGoodCommentTypes();

        /*
         * ============================================================
         * 🔍 ZLE KOMENTARZE (1): REDUNDANTNE, POWTARZAJACE KOD
         * ============================================================
         * - Komentarz, ktory tylko "tlumaczy kod na polski slowo w slowo",
         *   NIE dodaje zadnej wartosci - zajmuje miejsce, wymaga czytania,
         *   a informacyjnie jest zerowy (kod juz to mowi).
         * - Przyklad: `i++; // zwieksz i o jeden` - kazdy programista Javy
         *   wie, co robi `i++` - komentarz jest czystym szumem.
         */
        demonstrateRedundantComments();

        /*
         * ============================================================
         * 🔹 ZLE KOMENTARZE (2): KOMENTARZ KLAMIACY (KOD SIE ZMIENIL, KOMENTARZ NIE)
         * ============================================================
         * - Najbardziej NIEBEZPIECZNY typ zlego komentarza - gorszy niz brak
         *   komentarza, bo aktywnie WPROWADZA W BLAD.
         * - Powstaje, gdy ktos zmienia logike, ale zapomina zaktualizowac
         *   opisujacy ja komentarz - czytelnik UFA komentarzowi (bo po co
         *   ktos mialby klamac?) i zaklada zle zalozenia o dzialaniu kodu.
         */
        demonstrateLyingComment();

        /*
         * ============================================================
         * 🔍 ZLE KOMENTARZE (3): KOMENTARZ ZAMIAST REFAKTORYZACJI
         * ============================================================
         * - Zamiast rozbic dlugi, skomplikowany blok kodu na dobrze nazwane
         *   metody, programista czasem dodaje komentarze - sekcje "// ---
         *   krok 1: walidacja ---", "// --- krok 2: obliczenia ---" - jako
         *   PROTEZE struktury.
         * - Problem: te "sekcje" to dokladnie granice, w ktorych powinny
         *   powstac osobne, nazwane metody. Komentarz "// krok 1: walidacja"
         *   to najlepszy mozliwy PODPOWIEDZ na nazwe metody `validateInput()`.
         */
        demonstrateCommentInsteadOfRefactoring();

        /*
         * ============================================================
         * 🔹 ZLE KOMENTARZE (4): ZAKOMENTOWANY MARTWY KOD
         * ============================================================
         * - Zakomentowany kod (np. stara implementacja "na wszelki wypadek")
         *   to SMIEC - nikt nie wie, czy jest wazny, czy zapomniany, i nikt
         *   go nie odwazy sie usunac ("a nuz sie przyda").
         * - Wspolczesne repozytoria uzywaja systemow kontroli wersji (Git) -
         *   HISTORIA kodu jest tam, nie w komentarzach. Jesli potrzebujesz
         *   starej wersji, uzyj `git log`/`git blame`, a nie zakomentowanego
         *   bloku w pliku.
         */
        demonstrateDeadCodeComment();

        /*
         * ============================================================
         * 📌 DEMO KONCOWE: METODA Z NADMIAREM ZLYCH KOMENTARZY VS DOBRE NAZEWNICTWO
         * ============================================================
         * - Ponizej ta sama logika (obliczanie ceny koncowej zamowienia z
         *   rabatem) - najpierw z gestwiny zlych komentarzy proboujacych
         *   ratowac zle nazwane zmienne, potem wersja, w ktorej DOBRE
         *   NAZEWNICTWO czyni wiekszosc komentarzy zbednymi - zostaje TYLKO
         *   jeden, faktycznie wartosciowy komentarz "dlaczego".
         */
        demonstrateOverCommentedMessyVersion();
        demonstrateSelfDocumentingVersionWithOneGoodComment();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Komentarz to "plan B" - najpierw probuj lepszej nazwy/struktury.
         * - Dobre komentarze: wyjasniaja DLACZEGO, ostrzegaja, TODO z kontekstem,
         *   Javadoc publicznego API.
         * - Zle komentarze: redundantne, klamiace (nieaktualne), zastepujace
         *   refaktoryzacje, zakomentowany martwy kod.
         * - W kolejnej lekcji (Lesson04): male funkcje robiace jedna rzecz -
         *   naturalne rozwiniecie tematu "komentarz zamiast refaktoryzacji".
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateWhyCommentIsValuable() {
        System.out.println("\n=== DOBRY KOMENTARZ: WYJASNIA 'DLACZEGO' ===");

        // UWAGA: Uzywamy Math.round zamiast (int) rzutowania, bo (int) obcina
        // czesc ulamkowa (floor), a wymagania biznesowe (dzial ksiegowosci)
        // wymagaja zaokraglenia matematycznego (np. 2.5 -> 3, nie 2).
        double rawPrice = 2.5;
        long roundedPrice = Math.round(rawPrice);

        System.out.println(" rawPrice=" + rawPrice + " -> roundedPrice=" + roundedPrice
                + " (komentarz wyjasnia WYBOR Math.round zamiast (int), nie sam fakt zaokraglenia)");
    }

    private static void demonstrateGoodCommentTypes() {
        System.out.println("\n=== DOBRE TYPY KOMENTARZY ===");

        // TODO(CLEAN-42): dodac obsluge waluty EUR, na razie zakladamy tylko PLN
        String currency = "PLN";
        System.out.println(" waluta=" + currency + " (przyklad dobrego TODO z odwolaniem do zadania)");

        System.out.println(" " + describeAsPercentage(0.755));
    }

    /**
     * Formatuje wartosc ulamkowa (0.0-1.0) jako procent z 1 miejscem po przecinku.
     *
     * @param fraction wartosc ulamkowa w zakresie [0.0, 1.0]
     * @return sformatowany procent, np. "75.5%"
     * @throws IllegalArgumentException gdy fraction jest poza zakresem [0.0, 1.0]
     */
    private static String describeAsPercentage(double fraction) {
        if (fraction < 0.0 || fraction > 1.0) {
            throw new IllegalArgumentException("fraction musi byc w zakresie [0.0, 1.0], otrzymano: " + fraction);
        }
        return String.format("%.1f%%", fraction * 100);
    }

    private static void demonstrateRedundantComments() {
        System.out.println("\n=== ZLY PRZYKLAD: REDUNDANTNE KOMENTARZE ===");

        int i = 0;
        i++; // zwieksz i o jeden
        int total = 0;
        total = total + i; // dodaj i do total

        System.out.println(" i=" + i + ", total=" + total);
        System.out.println(" ^ oba komentarze tylko powtarzaja to, co kod juz mowi - czysty szum");
    }

    private static void demonstrateLyingComment() {
        System.out.println("\n=== ZLY PRZYKLAD: KOMENTARZ KLAMIACY ===");

        // Zwraca liste TYLKO aktywnych uzytkownikow
        // (KOMENTARZ NIEAKTUALNY - logika ponizej zwraca WSZYSTKICH, bez filtra!)
        java.util.List<String> allUsers = java.util.List.of("Ala (aktywna)", "Tomek (nieaktywny)", "Ewa (aktywna)");
        java.util.List<String> result = allUsers; // brak filtrowania - kod "urosl", komentarz zostal stary

        System.out.println(" wynik (rzekomo 'tylko aktywni'): " + result);
        System.out.println(" ^ komentarz klamie - kod zwraca WSZYSTKICH, nie tylko aktywnych. Niebezpieczne!");
    }

    private static void demonstrateCommentInsteadOfRefactoring() {
        System.out.println("\n=== ZLY PRZYKLAD: KOMENTARZ ZAMIAST REFAKTORYZACJI ===");

        double amount = 500.0;
        int itemCount = 12;

        // --- krok 1: walidacja ---
        boolean isValid = amount > 0 && itemCount > 0;

        // --- krok 2: obliczenie rabatu ---
        double discount = itemCount > 10 ? 0.1 : 0.0;
        double discountedAmount = amount * (1 - discount);

        System.out.println(" isValid=" + isValid + ", discountedAmount=" + discountedAmount);
        System.out.println(" ^ te komentarze-sekcje to gotowe nazwy metod: validateOrder(), applyBulkDiscount()");
    }

    private static void demonstrateDeadCodeComment() {
        System.out.println("\n=== ZLY PRZYKLAD: ZAKOMENTOWANY MARTWY KOD ===");

        int price = 100;
        // int oldPriceWithTax = price + (price / 100 * 23); // stara wersja sprzed zmiany stawki VAT
        // double legacyDiscount = price * 0.05; // uzywane przed refaktoryzacja w 2023 - juz nieaktualne
        int finalPrice = price;

        System.out.println(" finalPrice=" + finalPrice);
        System.out.println(" ^ 2 linie zakomentowanego kodu wyzej to smiec - historia jest w Git, nie tutaj");
    }

    /*
     * ZLY PRZYKLAD: metoda z fatalnymi nazwami "ratowanymi" przez gestwine
     * komentarzy - komentarze probuja zrekompensowac zla strukture kodu.
     */
    private static void demonstrateOverCommentedMessyVersion() {
        System.out.println("\n=== ZLY PRZYKLAD: NADMIAR KOMENTARZY RATUJACYCH ZLY KOD ===");

        double a = 1000.0; // a to kwota zamowienia
        int q = 15; // q to liczba sztuk

        // sprawdz, czy q jest wieksze niz 10 - jesli tak, dajemy rabat
        boolean r = q > 10;
        double d;
        if (r) {
            // rabat wynosi 10%, wiec mnozymy przez 0.9
            d = a * 0.9;
        } else {
            // bez rabatu, kwota bez zmian
            d = a;
        }

        System.out.println(" a=" + a + ", q=" + q + ", d=" + d);
        System.out.println(" ^ 5 komentarzy tlumaczacych KAZDA linie - bo zmienne/metoda nic same nie mowia");
    }

    /*
     * DOBRY PRZYKLAD: ta sama logika, ale dobre nazewnictwo (Lekcja 2) czyni
     * wiekszosc komentarzy zbednymi. Zostaje TYLKO jeden komentarz - i to
     * dlatego, ze wyjasnia DLACZEGO prog rabatu wynosi akurat 10 sztuk
     * (informacja, ktorej sam kod nie jest w stanie przekazac).
     */
    private static void demonstrateSelfDocumentingVersionWithOneGoodComment() {
        System.out.println("\n=== DOBRY PRZYKLAD: CZYTELNE NAZWY ZAMIAST KOMENTARZY ===");

        double orderAmount = 1000.0;
        int itemCount = 15;

        double discountedAmount = applyBulkOrderDiscount(orderAmount, itemCount);

        System.out.println(" orderAmount=" + orderAmount + ", itemCount=" + itemCount
                + ", discountedAmount=" + discountedAmount);
        System.out.println(" ^ ZERO komentarzy tlumaczacych 'co' - nazwy mowia wszystko same");
    }

    // Prog 10 sztuk pochodzi z umowy z dostawca logistycznym (nizsza stawka
    // transportu przy wiekszych paczkach) - NIE jest to dowolna, arbitralna
    // wartosc, wiec nie wolno jej zmieniac bez konsultacji z dzialem zakupow.
    private static final int BULK_ORDER_ITEM_THRESHOLD = 10;
    private static final double BULK_ORDER_DISCOUNT_RATE = 0.1;

    private static double applyBulkOrderDiscount(double orderAmount, int itemCount) {
        boolean qualifiesForBulkDiscount = itemCount > BULK_ORDER_ITEM_THRESHOLD;
        return qualifiesForBulkDiscount ? orderAmount * (1 - BULK_ORDER_DISCOUNT_RATE) : orderAmount;
    }
}
