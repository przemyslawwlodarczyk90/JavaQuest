package com.example.javaquest._16_clean_code.Lesson04_MethodsAndFunctions;

import java.util.ArrayList;
import java.util.List;

public class _Lesson04_MethodsAndFunctions {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: METODY I FUNKCJE ===");

        /*
         * ============================================================
         * 🔹 ZASADA: MALE FUNKCJE, ROBIACE JEDNA RZECZ
         * ============================================================
         * - Robert C. Martin: "pierwsza zasada funkcji - powinny byc MALE.
         *   Druga zasada - powinny byc JESZCZE MNIEJSZE".
         * - "Jedna rzecz" (do one thing) oznacza: jesli potrafisz wydzielic
         *   z metody INNA metode o nazwie, ktora NIE jest po prostu
         *   przeformulowaniem oryginalnej implementacji - metoda robila
         *   wiecej niz jedna rzecz.
         * - Male funkcje sa latwiejsze do: przeczytania za jednym rzutem
         *   oka, przetestowania w izolacji, ponownego uzycia, bezpiecznej
         *   zmiany (male ryzyko efektow ubocznych w innych miejscach).
         */
        demonstrateGiantMethodDoingFiveThings();
        demonstrateDecomposedIntoSmallMethods();

        /*
         * ============================================================
         * 🔍 ZASADA: MALA LICZBA ARGUMENTOW (IDEALNIE 0-2)
         * ============================================================
         * - Im wiecej argumentow, tym trudniej zapamietac ich KOLEJNOSC i
         *   ZNACZENIE przy kazdym wywolaniu - a kompilator NIE ostrzeze,
         *   jesli przypadkowo zamienisz miejscami 2 parametry tego samego typu
         *   (np. `createUser(String imie, String nazwisko)` wywolane jako
         *   `createUser(nazwisko, imie)` - kompiluje sie, dziala zle).
         * - Funkcje z 0 argumentow (niladic) i 1 argumentem (monadic) sa
         *   najlatwiejsze do zrozumienia. 2 argumenty (dyadic) sa jeszcze OK.
         *   3+ argumentow (polyadic) powinno budzic podejrzenie - czy te
         *   dane nie powinny byc jednym obiektem (patrz Zadanie z Lekcji 2
         *   o grupowaniu parametrow)?
         */
        demonstrateTooManyParametersVsParameterObject();

        /*
         * ============================================================
         * 🔹 ZASADA: UNIKAJ FLAG BOOLEAN JAKO ARGUMENTOW STERUJACYCH
         * ============================================================
         * - Argument `boolean` sterujacy zachowaniem metody (tzw. "flag
         *   argument") jest sygnalem, ze metoda robi DWIE rozne rzeczy w
         *   zaleznosci od wartosci flagi - czyli lamie zasade "jedna rzecz".
         * - Dodatkowy problem czytelnosci: w miejscu wywolania
         *   `render(true)` nie wiadomo, co znaczy `true` bez zajrzenia do
         *   deklaracji metody - `render(RenderMode.PDF)` (enum) albo dwie
         *   osobne metody `renderAsHtml()`/`renderAsPdf()` sa czytelniejsze.
         */
        demonstrateBooleanFlagArgumentVsSeparateMethods();

        /*
         * ============================================================
         * 🔍 ZASADA: BRAK UKRYTYCH EFEKTOW UBOCZNYCH (SIDE EFFECTS)
         * ============================================================
         * - Efekt uboczny (side effect) to zmiana stanu (pola obiektu,
         *   zmiennej globalnej, pliku, bazy danych) wykonana przez metode,
         *   ktora z NAZWY tego nie sugeruje.
         * - Klasyczny przyklad: `boolean checkPassword(String password)`,
         *   ktora - oprocz sprawdzenia hasla - PRZY OKAZJI inicjalizuje
         *   sesje uzytkownika. Nazwa sugeruje tylko "sprawdz", wiec caller
         *   nie ma powodu spodziewac sie efektu ubocznego - a jednak on
         *   zachodzi. Prowadzi to do trudnych do wytropienia bugow.
         * - Rozwiazanie: albo NIE rob efektu ubocznego w takiej metodzie,
         *   albo zmien nazwe tak, by efekt uboczny byl w niej WIDOCZNY (np.
         *   `checkPasswordAndInitializeSession(...)`).
         */
        demonstrateHiddenSideEffectVsExplicitName();

        /*
         * ============================================================
         * 🔹 ZASADA: COMMAND-QUERY SEPARATION (CQS)
         * ============================================================
         * - Zasada Bertranda Meyera: metoda powinna byc ALBO "command"
         *   (rozkazem - zmienia stan, zwraca void), ALBO "query" (zapytaniem -
         *   zwraca wartosc, NIE zmienia stanu) - NIGDY obiema naraz.
         * - Przyklad zlamania CQS: metoda `boolean removeIfPresent(String
         *   item)`, ktora zarowno USUWA element (command), jak i ZWRACA
         *   informacje, czy element istnial (query) - `if
         *   (list.removeIfPresent(x)) { ... }` jest MYLACE, bo czytajac
         *   `if (...)` naturalnie myslimy o zapytaniu, nie o rozkazie ze
         *   skutkiem ubocznym.
         * - W praktyce Javy sa uzasadnione wyjatki (np. `Map.remove(key)`
         *   zwraca usuwana wartosc) - ale w WLASNYM kodzie warto trzymac sie
         *   CQS tam, gdzie to mozliwe, dla jasnosci.
         */
        demonstrateCommandQuerySeparation();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Male funkcje robiace jedna rzecz sa latwiejsze do czytania,
         *   testowania i zmiany.
         * - Mala liczba argumentow (0-2 idealnie) - grupuj powiazane dane w
         *   obiekty zamiast mnozyc parametry.
         * - Unikaj flag boolean sterujacych zachowaniem - wydziel osobne
         *   metody albo uzyj enum.
         * - Unikaj ukrytych efektow ubocznych - nazwa metody musi obiecywac
         *   dokladnie to, co metoda robi.
         * - Command-Query Separation: metoda albo zmienia stan (command),
         *   albo zwraca wartosc (query) - nie oba naraz.
         * - W kolejnej lekcji (Lesson05): formatowanie kodu - jak
         *   ROZMIESCIC male, dobrze nazwane metody w klasie.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    /*
     * ZLY PRZYKLAD: jedna gigantyczna metoda robiaca 5 rzeczy naraz -
     * walidacje, obliczenia, logowanie, "zapis" (symulowany) i budowanie
     * podsumowania. Kazda zmiana w jednej z tych 5 odpowiedzialnosci
     * wymaga dotkniecia tej samej, ogromnej metody.
     */
    private static void demonstrateGiantMethodDoingFiveThings() {
        System.out.println("\n=== ZLY PRZYKLAD: METODA ROBIACA 5 RZECZY NARAZ ===");

        String result = processOrderEverything("Jan Kowalski", 3, 49.99, "PL");
        System.out.println(result);
    }

    private static String processOrderEverything(String customerName, int itemCount, double unitPrice, String countryCode) {
        // 1. WALIDACJA
        if (customerName == null || customerName.isEmpty()) {
            return "BLAD: brak nazwy klienta";
        }
        if (itemCount <= 0) {
            return "BLAD: nieprawidlowa liczba sztuk";
        }
        if (unitPrice <= 0) {
            return "BLAD: nieprawidlowa cena";
        }

        // 2. OBLICZENIA
        double subtotal = itemCount * unitPrice;
        double discount = itemCount >= 10 ? 0.1 : 0.0;
        double taxRate = countryCode.equals("PL") ? 0.23 : 0.19;
        double afterDiscount = subtotal * (1 - discount);
        double total = afterDiscount * (1 + taxRate);

        // 3. "LOGOWANIE" (symulowane printlnem zamiast prawdziwego loggera)
        System.out.println(" [LOG] Przetwarzanie zamowienia dla: " + customerName);

        // 4. "ZAPIS" (symulowany - w realnym kodzie byloby to zapisanie do bazy)
        System.out.println(" [ZAPIS] Zamowienie zapisane w 'bazie' dla " + customerName);

        // 5. BUDOWANIE PODSUMOWANIA
        return String.format(" Podsumowanie: klient=%s, suma=%.2f PLN (rabat=%.0f%%, VAT=%.0f%%)",
                customerName, total, discount * 100, taxRate * 100);
    }

    /*
     * DOBRY PRZYKLAD: identyczna logika rozbita na male, dobrze nazwane
     * metody - kazda robi DOKLADNIE jedna rzecz. Metoda glowna
     * (orkiestrator) czyta sie jak spis tresci calego procesu.
     */
    private static void demonstrateDecomposedIntoSmallMethods() {
        System.out.println("\n=== DOBRY PRZYKLAD: ROZBICIE NA MALE METODY ===");

        String summary = processOrder("Jan Kowalski", 3, 49.99, "PL");
        System.out.println(summary);
    }

    private static String processOrder(String customerName, int itemCount, double unitPrice, String countryCode) {
        String validationError = validateOrderInput(customerName, itemCount, unitPrice);
        if (validationError != null) {
            return validationError;
        }

        double total = calculateOrderTotal(itemCount, unitPrice, countryCode);
        logOrderProcessing(customerName);
        saveOrder(customerName);

        return buildOrderSummary(customerName, total, itemCount, countryCode);
    }

    private static String validateOrderInput(String customerName, int itemCount, double unitPrice) {
        if (customerName == null || customerName.isEmpty()) {
            return "BLAD: brak nazwy klienta";
        }
        if (itemCount <= 0) {
            return "BLAD: nieprawidlowa liczba sztuk";
        }
        if (unitPrice <= 0) {
            return "BLAD: nieprawidlowa cena";
        }
        return null;
    }

    private static double calculateOrderTotal(int itemCount, double unitPrice, String countryCode) {
        double subtotal = itemCount * unitPrice;
        double afterDiscount = subtotal * (1 - resolveBulkDiscountRate(itemCount));
        return afterDiscount * (1 + resolveTaxRate(countryCode));
    }

    private static double resolveBulkDiscountRate(int itemCount) {
        return itemCount >= 10 ? 0.1 : 0.0;
    }

    private static double resolveTaxRate(String countryCode) {
        return countryCode.equals("PL") ? 0.23 : 0.19;
    }

    private static void logOrderProcessing(String customerName) {
        System.out.println(" [LOG] Przetwarzanie zamowienia dla: " + customerName);
    }

    private static void saveOrder(String customerName) {
        System.out.println(" [ZAPIS] Zamowienie zapisane w 'bazie' dla " + customerName);
    }

    private static String buildOrderSummary(String customerName, double total, int itemCount, String countryCode) {
        return String.format(" Podsumowanie: klient=%s, suma=%.2f PLN (rabat=%.0f%%, VAT=%.0f%%)",
                customerName, total, resolveBulkDiscountRate(itemCount) * 100, resolveTaxRate(countryCode) * 100);
    }

    private static void demonstrateTooManyParametersVsParameterObject() {
        System.out.println("\n=== ZLA: ZBYT WIELE PARAMETROW VS DOBRA: OBIEKT PARAMETROW ===");

        // ZLA: 6 parametrow tego samego typu (String/double) - latwo pomylic kolejnosc
        String receiptBad = createShippingLabelTooManyParams(
                "Jan Kowalski", "ul. Dluga 5", "Warszawa", "00-001", "PL", "+48123456789");
        System.out.println(" [ZLA] " + receiptBad);

        // DOBRA: dane pogrupowane w jeden obiekt ShippingAddress
        ShippingAddress address = new ShippingAddress("Jan Kowalski", "ul. Dluga 5", "Warszawa", "00-001", "PL", "+48123456789");
        String receiptGood = createShippingLabel(address);
        System.out.println(" [DOBRA] " + receiptGood);
    }

    private static String createShippingLabelTooManyParams(String recipient, String street, String city,
                                                             String postalCode, String countryCode, String phone) {
        return "Etykieta dla: " + recipient + ", " + street + ", " + postalCode + " " + city
                + ", " + countryCode + ", tel: " + phone;
    }

    private record ShippingAddress(String recipient, String street, String city,
                                    String postalCode, String countryCode, String phone) {
    }

    private static String createShippingLabel(ShippingAddress address) {
        return "Etykieta dla: " + address.recipient() + ", " + address.street() + ", "
                + address.postalCode() + " " + address.city() + ", " + address.countryCode()
                + ", tel: " + address.phone();
    }

    private static void demonstrateBooleanFlagArgumentVsSeparateMethods() {
        System.out.println("\n=== ZLA: FLAGA BOOLEAN VS DOBRA: OSOBNE METODY ===");

        List<String> data = List.of("Ala", "Bartek", "Celina");

        // ZLA: co znaczy 'true'? trzeba zajrzec do deklaracji metody, by sie dowiedziec
        System.out.println(" [ZLA] renderReport(data, true) -> " + renderReport(data, true));

        // DOBRA: nazwa metody mowi wszystko, bez potrzeby czytania implementacji
        System.out.println(" [DOBRA] renderAsHtmlReport(data) -> " + renderAsHtmlReport(data));
    }

    private static String renderReport(List<String> items, boolean asHtml) {
        return asHtml ? renderAsHtmlReport(items) : renderAsPlainTextReport(items);
    }

    private static String renderAsHtmlReport(List<String> items) {
        return "<ul>" + String.join("", items.stream().map(i -> "<li>" + i + "</li>").toList()) + "</ul>";
    }

    private static String renderAsPlainTextReport(List<String> items) {
        return String.join(", ", items);
    }

    private static void demonstrateHiddenSideEffectVsExplicitName() {
        System.out.println("\n=== ZLA: UKRYTY EFEKT UBOCZNY VS DOBRA: NAZWA MOWIACA PRAWDE ===");

        List<String> activeSessions = new ArrayList<>();

        // ZLA: nazwa sugeruje tylko "sprawdzenie", ale metoda PRZY OKAZJI modyfikuje stan
        boolean loginOkBad = checkPassword("sekret123", activeSessions, "jan.kowalski");
        System.out.println(" [ZLA] checkPassword(...) = " + loginOkBad + ", activeSessions=" + activeSessions);

        activeSessions.clear();

        // DOBRA: nazwa jawnie mowi, ze metoda i sprawdza haslo, I tworzy sesje
        boolean loginOkGood = checkPasswordAndCreateSession("sekret123", activeSessions, "jan.kowalski");
        System.out.println(" [DOBRA] checkPasswordAndCreateSession(...) = " + loginOkGood + ", activeSessions=" + activeSessions);
    }

    private static boolean checkPassword(String password, List<String> activeSessions, String username) {
        boolean isCorrect = password.equals("sekret123");
        if (isCorrect) {
            activeSessions.add(username); // UKRYTY efekt uboczny - nazwa metody o tym nie mowi!
        }
        return isCorrect;
    }

    private static boolean checkPasswordAndCreateSession(String password, List<String> activeSessions, String username) {
        boolean isCorrect = password.equals("sekret123");
        if (isCorrect) {
            activeSessions.add(username); // efekt uboczny JEST w nazwie - brak zaskoczenia dla czytelnika
        }
        return isCorrect;
    }

    private static void demonstrateCommandQuerySeparation() {
        System.out.println("\n=== COMMAND-QUERY SEPARATION (CQS) ===");

        List<String> shoppingCart = new ArrayList<>(List.of("Chleb", "Mleko", "Maslo"));

        // ZLA (zlamane CQS): jedna metoda usuwa (command) I zwraca informacje (query)
        boolean wasRemovedBad = removeItemIfPresentBad(shoppingCart, "Mleko");
        System.out.println(" [ZLA] removeItemIfPresentBad zwrocilo=" + wasRemovedBad + ", koszyk=" + shoppingCart);

        shoppingCart.add("Mleko");

        // DOBRA: rozdzielone na query (containsItem) i command (removeItem)
        boolean containsItem = containsItem(shoppingCart, "Mleko");
        if (containsItem) {
            removeItem(shoppingCart, "Mleko");
        }
        System.out.println(" [DOBRA] containsItem=" + containsItem + ", koszyk po removeItem=" + shoppingCart);
    }

    private static boolean removeItemIfPresentBad(List<String> cart, String item) {
        return cart.remove(item); // command (usuwa) + query (zwraca czy sie udalo) naraz
    }

    private static boolean containsItem(List<String> cart, String item) {
        return cart.contains(item); // TYLKO query - nie zmienia stanu
    }

    private static void removeItem(List<String> cart, String item) {
        cart.remove(item); // TYLKO command - void, nie zwraca informacji
    }
}
