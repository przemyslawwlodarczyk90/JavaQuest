package com.example.javaquest._16_clean_code.Lesson15_RefactoringBasics;

import java.util.List;

public class _Lesson15_RefactoringBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: PODSTAWY REFAKTORYZACJI ===");

        /*
         * ============================================================
         * 📦 DEFINICJA REFAKTORYZACJI (MARTIN FOWLER)
         * ============================================================
         * - Martin Fowler ("Refactoring: Improving the Design of Existing
         *   Code", 1999/2018): refaktoryzacja to "proces zmiany
         *   WEWNETRZNEJ STRUKTURY oprogramowania w sposob, ktory NIE
         *   ZMIENIA jego ZEWNETRZNEGO zachowania".
         * - Kluczowe: refaktoryzacja to NIE "naprawianie bledow" i NIE
         *   "dodawanie nowej funkcjonalnosci" - to WYLACZNIE poprawa
         *   CZYTELNOSCI/STRUKTURY kodu, ktory JUZ dziala poprawnie.
         * - Jesli po Twojej zmianie program robi COS INNEGO niz przedtem -
         *   to NIE byla refaktoryzacja, tylko zmiana funkcjonalna (moze
         *   dobra, ale to inna kategoria pracy, z innym ryzykiem).
         */
        System.out.println("Refaktoryzacja = zmiana STRUKTURY kodu bez zmiany jego ZACHOWANIA (Fowler, 1999).");

        /*
         * ============================================================
         * 🔹 PO CO W OGOLE REFAKTORYZOWAC?
         * ============================================================
         * - Kod ze smellami z Lesson14 (Long Method, Large Class, itd.)
         *   DZIALA - ale kazda kolejna zmiana w nim jest WOLNIEJSZA i
         *   BARDZIEJ RYZYKOWNA niz musialaby byc.
         * - Fowler: "refaktoryzuj PRZED dodaniem nowej funkcji, jesli
         *   obecna struktura kodu utrudnia jej dodanie" - refaktoryzacja
         *   to inwestycja, ktora zwraca sie przy NASTEPNEJ zmianie, nie
         *   cel sam w sobie.
         * - "Zasada skauta" (Boy Scout Rule, spopularyzowana przez Roberta
         *   C. Martina): zostaw kod CZYSTSZY, niz go zastales - nawet
         *   drobna poprawa (lepsza nazwa zmiennej) przy okazji innej
         *   pracy powoli poprawia caly kod bazy.
         */
        System.out.println("Zasada skauta: zostaw kod czystszy, niz go zastales - male poprawki przy kazdej okazji.");

        /*
         * ============================================================
         * 🔍 SIATKA BEZPIECZENSTWA: TESTY PRZED REFAKTORYZACJA
         * ============================================================
         * - Bez testow refaktoryzacja jest "szukaniem po omacku" - nie
         *   masz jak POTWIERDZIC, ze zachowanie programu sie NIE zmienilo.
         * - Jesli kod NIE MA testow, pierwszym krokiem jest napisanie
         *   TESTOW CHARAKTERYZUJACYCH (characterization tests) -
         *   testow opisujacych FAKTYCZNE, obecne zachowanie kodu (nawet
         *   jesli to zachowanie samo w sobie jest niedoskonale) - dopiero
         *   POTEM refaktoryzujesz, majac siatke bezpieczenstwa.
         * - Ten kurs (jak wiekszosc lekcji) demonstruje refaktoryzacje
         *   przez PROSTE porownanie wywolan "przed"/"po" w `main()` -
         *   w realnym projekcie ta rola nalezy do prawdziwych testow
         *   jednostkowych (JUnit), nie do `System.out.println`.
         */
        demonstrateSafetyNetWithSimpleAssertions();

        /*
         * ============================================================
         * 🔹 MALE, BEZPIECZNE KROKI (NIE "WIELKI SKOK")
         * ============================================================
         * - Refaktoryzacja robi sie MALYMI, POJEDYNCZYMI krokami (np.
         *   "Extract Method" na JEDNEJ metodzie - poznasz konkretne
         *   techniki w Lesson16), z WERYFIKACJA (uruchomieniem testow) PO
         *   KAZDYM kroku.
         * - Przeciwienstwo: "wielki skok" - jednoczesna zmiana 10 rzeczy
         *   naraz. Jesli cos sie zepsuje, TRUDNO wskazac, KTORA z 10 zmian
         *   byla przyczyna - a male kroki dokladnie lokalizuja problem.
         * - Kazdy MALY krok powinien zostawiac kod w stanie
         *   KOMPILUJACYM SIE i DZIALAJACYM (`main()` nadal daje ten sam
         *   wynik) - to jest test, czy krok byl naprawde "bezpieczny".
         */
        demonstrateSmallStepsRefactoring();

        /*
         * ============================================================
         * 🔍 REGULA TRZECH RAZY ("RULE OF THREE")
         * ============================================================
         * - Don Roberts (cytowany przez Fowlera): "za pierwszym razem po
         *   prostu to zrob. Za drugim razem, gdy zauwazysz podobienstwo,
         *   skrzyw sie - ale i tak to zrob (zduplikuj). Za TRZECIM razem -
         *   zrefaktoryzuj."
         * - To praktyczna wskazowka PRZECIWKO przedwczesnej abstrakcji
         *   (patrz tez Lesson13: YAGNI) - 2 podobne fragmenty kodu moga
         *   byc PRZYPADKOWYM podobienstwem; dopiero 3. wystapienie daje
         *   pewnosc, ze to NAPRAWDE powtarzajacy sie wzorzec, wart
         *   wydzielenia.
         */
        System.out.println("\nRegula 3 razy: 1. raz - napisz, 2. raz - zduplikuj (mimo grymasu), 3. raz - refaktoryzuj.");

        /*
         * ============================================================
         * 🔹 KIEDY NIE REFAKTORYZOWAC
         * ============================================================
         * - Kod, ktory ZOSTANIE WKROTCE USUNIETY/ZASTAPIONY (np. legacy
         *   system planowany do wygaszenia w tym miesiacu) - inwestycja
         *   sie nie zwroci.
         * - Bezposrednio PRZED terminem/release'em, bez czasu na pelne
         *   przetestowanie zmiany - ryzyko wprowadzenia regresji tuz przed
         *   wdrozeniem jest zbyt kosztowne.
         * - Kod BEZ zadnej siatki bezpieczenstwa (testow) I bez czasu na
         *   jej dodanie - najpierw czas na testy charakteryzujace, potem
         *   refaktoryzacja (patrz wyzej).
         * - "Jesli to nie zepsute, nie napraw tego" - kod dzialajacy
         *   poprawnie i RZADKO zmieniany moze nie byc wart refaktoryzacji
         *   TYLKO dla estetyki, jesli nie planujesz go wkrotce rozwijac.
         */
        System.out.println("\nNIE refaktoryzuj: kod do usuniecia, tuz przed terminem, bez siatki bezpieczenstwa i czasu na nia.");

        /*
         * ============================================================
         * 🔍 REFAKTORYZACJA VS PRZEPISANIE OD ZERA (REWRITE)
         * ============================================================
         * - Refaktoryzacja: STOPNIOWA, male kroki, program DZIALA (i daje
         *   te same wyniki) przez CALY czas trwania procesu.
         * - Przepisanie od zera (rewrite): stary kod odklada sie na bok,
         *   nowy pisze sie "z czystej kartki" - ryzykowniejsze (utrata
         *   ukrytej, "zaszytej" w starym kodzie logiki biznesowej,
         *   dlugi okres, gdy NIC nie dziala w pelni), ale czasem
         *   uzasadnione (gdy struktura jest TAK zla, ze male kroki nie sa
         *   w stanie jej naprawic w rozsadnym czasie).
         * - Domyslny wybor w praktyce: refaktoryzacja - rewrite to
         *   ostatecznosc, nie pierwsza opcja.
         */
        System.out.println("\nRefaktoryzacja (male kroki, kod caly czas dziala) domyslnie lepsza niz rewrite (ryzykowne).");

        /*
         * ============================================================
         * 📌 PRZYKLAD: BEZPIECZNA REFAKTORYZACJA KROK PO KROKU
         * ============================================================
         * - Ponizej: metoda ze smellem Long Method (Lesson14),
         *   refaktoryzowana w 3 MALYCH krokach - po KAZDYM kroku wynik
         *   `main()` jest IDENTYCZNY jak na poczatku (weryfikacja
         *   "siatki bezpieczenstwa").
         */
        demonstrateStepByStepRefactoringExample();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Refaktoryzacja = zmiana STRUKTURY bez zmiany ZACHOWANIA
         *   (Fowler) - nie mieszaj jej z naprawianiem bledow/dodawaniem
         *   funkcji.
         * - Zasada skauta: zostaw kod czystszy niz go zastales.
         * - Siatka bezpieczenstwa: testy PRZED refaktoryzacja (lub testy
         *   charakteryzujace, jesli ich brak).
         * - Male kroki, weryfikowane po kazdym - nie "wielki skok".
         * - Regula 3 razy: refaktoryzuj dopiero przy 3. powtorzeniu wzorca.
         * - Rewrite to ostatecznosc, nie pierwszy wybor.
         * - W kolejnej lekcji (Lesson16): KATALOG konkretnych technik
         *   refaktoringu (Extract Method, Extract Class, Introduce
         *   Parameter Object, Replace Conditional with Polymorphism i
         *   inne) - "narzedzia", ktorymi wykonuje sie male kroki opisane
         *   w tej lekcji.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void demonstrateSafetyNetWithSimpleAssertions() {
        System.out.println("\n=== SIATKA BEZPIECZENSTWA: PROSTE 'ASERCJE' PRZED/PO ===");

        double before = calculateOrderTotalOriginal(3, 49.99);
        System.out.println("Wynik PRZED jakakolwiek zmiana (zapamietany jako punkt odniesienia): " + before);

        double after = calculateOrderTotalOriginal(3, 49.99);
        boolean stillIdentical = Double.compare(before, after) == 0;
        System.out.println("Wynik PO (ta sama metoda, wywolana ponownie): " + after);
        System.out.println("Zachowanie niezmienione? " + (stillIdentical ? "TAK (bezpieczne)" : "NIE (regresja!)"));
        System.out.println("W realnym projekcie ta rola nalezy do testow JUnit, nie recznego porownania jak tutaj.");
    }

    private static double calculateOrderTotalOriginal(int quantity, double unitPrice) {
        double subtotal = quantity * unitPrice;
        return quantity >= 10 ? subtotal * 0.9 : subtotal;
    }

    private static void demonstrateSmallStepsRefactoring() {
        System.out.println("\n=== MALE KROKI: KAZDY WERYFIKOWANY OSOBNO ===");

        List<Integer> quantities = List.of(1, 5, 10, 15);
        for (int qty : quantities) {
            double viaOriginal = calculateOrderTotalOriginal(qty, 20.0);
            double viaStep1 = calculateOrderTotalStep1ExtractedDiscountCheck(qty, 20.0);
            double viaStep2 = calculateOrderTotalStep2NamedConstant(qty, 20.0);

            boolean allMatch = Double.compare(viaOriginal, viaStep1) == 0 && Double.compare(viaStep1, viaStep2) == 0;
            System.out.println("qty=" + qty + " -> oryginal=" + viaOriginal + ", po kroku1=" + viaStep1
                    + ", po kroku2=" + viaStep2 + " | zgodne=" + allMatch);
        }
    }

    // Krok 1: wydzielenie warunku rabatu do osobnej metody (bez zmiany logiki).
    private static double calculateOrderTotalStep1ExtractedDiscountCheck(int quantity, double unitPrice) {
        double subtotal = quantity * unitPrice;
        return isBulkOrder(quantity) ? subtotal * 0.9 : subtotal;
    }

    private static boolean isBulkOrder(int quantity) {
        return quantity >= 10;
    }

    // Krok 2: zamiana "magicznej liczby" 10 na nazwana stala (kolejny, oddzielny, mały krok).
    private static final int BULK_ORDER_THRESHOLD = 10;

    private static double calculateOrderTotalStep2NamedConstant(int quantity, double unitPrice) {
        double subtotal = quantity * unitPrice;
        return quantity >= BULK_ORDER_THRESHOLD ? subtotal * 0.9 : subtotal;
    }

    private static void demonstrateStepByStepRefactoringExample() {
        System.out.println("\n=== PRZYKLAD: REFAKTORYZACJA METODY ZE SMELLEM LONG METHOD ===");

        String resultBefore = describeOrderOriginal("Ala Kowalska", 12, 15.0);
        System.out.println("[PRZED] " + resultBefore);

        String resultAfter = describeOrderRefactored("Ala Kowalska", 12, 15.0);
        System.out.println("[PO 3 KROKACH REFAKTORYZACJI] " + resultAfter);
        System.out.println("Zachowanie identyczne? " + resultBefore.equals(resultAfter));
    }

    /** PRZED: 1 metoda robiaca walidacje + obliczenia + formatowanie naraz (Long Method, Lesson14). */
    private static String describeOrderOriginal(String customerName, int quantity, double unitPrice) {
        if (customerName == null || customerName.isBlank()) {
            return "BLAD: brak klienta";
        }
        double subtotal = quantity * unitPrice;
        double total = quantity >= BULK_ORDER_THRESHOLD ? subtotal * 0.9 : subtotal;
        return "Zamowienie: " + customerName + ", " + quantity + " szt., do zaplaty " + total + " zl";
    }

    /**
     * PO: TA SAMA logika, po 3 malych, niezaleznie weryfikowalnych krokach
     * refaktoryzacji (walidacja / obliczenia / formatowanie rozdzielone).
     * Konkretne NAZWY technik (Extract Method itd.) poznasz w Lesson16.
     */
    private static String describeOrderRefactored(String customerName, int quantity, double unitPrice) {
        String validationError = validateCustomerName(customerName);
        if (validationError != null) {
            return validationError;
        }
        double total = calculateOrderTotal(quantity, unitPrice);
        return formatOrderDescription(customerName, quantity, total);
    }

    private static String validateCustomerName(String customerName) {
        return (customerName == null || customerName.isBlank()) ? "BLAD: brak klienta" : null;
    }

    private static double calculateOrderTotal(int quantity, double unitPrice) {
        double subtotal = quantity * unitPrice;
        return quantity >= BULK_ORDER_THRESHOLD ? subtotal * 0.9 : subtotal;
    }

    private static String formatOrderDescription(String customerName, int quantity, double total) {
        return "Zamowienie: " + customerName + ", " + quantity + " szt., do zaplaty " + total + " zl";
    }
}
