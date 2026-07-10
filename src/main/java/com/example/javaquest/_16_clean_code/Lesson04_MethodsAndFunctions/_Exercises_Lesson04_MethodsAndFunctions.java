package com.example.javaquest._16_clean_code.Lesson04_MethodsAndFunctions;

public class _Exercises_Lesson04_MethodsAndFunctions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDoOneThingInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania),
         * co oznacza zasada "funkcja powinna robic jedna rzecz" i po czym mozna
         * rozpoznac, ze metoda robi WIECEJ niz jedna rzecz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteGiantMethodValidatingAndFormatting {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `describeStudent(String name, int age, double avgGrade)`,
         * ktora W JEDNEJ METODZIE: waliduje dane (name niepuste, age 1-120,
         * avgGrade 1.0-6.0 - zwraca "BLAD: ..." dla pierwszego naruszonego
         * warunku), oblicza status ("wyrozniony" jesli avgGrade>=4.75, w
         * przeciwnym razie "standardowy") i buduje opis tekstowy. Wywolaj ja
         * dla ("Ala", 20, 4.9) i dla (" ", 20, 4.9).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DecomposeStudentMethodIntoSmallMethods {
        /*
         * 🧪 Zadanie 3:
         * Rozbij metode z Zadania 2 na 3 male metody: validateStudentInput(...),
         * resolveStudentStatus(double avgGrade), buildStudentDescription(...) -
         * metoda glowna describeStudent(...) ma tylko je WOLAC (orkiestrator).
         * Sprawdz, ze wynik jest identyczny jak w Zadaniu 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CountArgumentsOfExistingMethods {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wypisz w komentarzu 3 metody z dowolnej wczesniejszej
         * lekcji kursu (podaj klase.metoda) i policz, ile maja argumentow -
         * oznacz kazda jako niladic (0), monadic (1), dyadic (2) lub polyadic
         * (3+).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteMethodWithFiveSameTypeParams {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode `createInvoiceLine(String product, String currency,
         * String description, String category, String note)` (5 parametrow
         * typu String) i wywolaj ja z argumentami w ZLEJ kolejnosci (podmien
         * miejscami product i currency) - zaobserwuj, ze kod SIE KOMPILUJE mimo
         * bledu logicznego. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IntroduceParameterObjectForInvoiceLine {
        /*
         * 🧪 Zadanie 6:
         * Zdefiniuj record InvoiceLineData(String product, String currency,
         * String description, String category, String note) i przepisz metode
         * z Zadania 5 na createInvoiceLine(InvoiceLineData data) - wywolaj ja i
         * porownaj czytelnosc wywolania z Zadaniem 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteMethodWithBooleanFlagArgument {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode `formatPrice(double amount, boolean withCurrencySymbol)`,
         * ktora zwraca "99.99 PLN" gdy flaga=true, a "99.99" gdy flaga=false.
         * Wywolaj `formatPrice(99.99, true)` i w komentarzu napisz, dlaczego w
         * miejscu wywolania nie widac, co znaczy `true`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SplitFormatPriceIntoTwoNamedMethods {
        /*
         * 🧪 Zadanie 8:
         * Zastap metode z Zadania 7 dwiema osobnymi metodami:
         * formatPriceWithCurrency(double) i formatPriceWithoutCurrency(double) -
         * wywolaj obie i w komentarzu porownaj czytelnosc z wersja z flaga.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyHiddenSideEffectInGivenMethod {
        /*
         * 🧪 Zadanie 9:
         * Dana jest metoda `boolean isDiscountEligible(Customer c, List<String>
         * log)`, ktora - oprocz sprawdzenia rabatu - PRZY OKAZJI dopisuje wpis
         * do listy `log`. Napisz taka metode, wywolaj ja i w komentarzu
         * wyjasnij, dlaczego nazwa metody wprowadza w blad co do jej
         * faktycznego zachowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RenameMethodToRevealSideEffect {
        /*
         * 🧪 Zadanie 10:
         * Zmien nazwe metody z Zadania 9 na taka, ktora JAWNIE opisuje OBA
         * efekty (sprawdzenie ORAZ logowanie) - wywolaj ja pod nowa nazwa i w
         * komentarzu potwierdz, ze teraz nazwa nie wprowadza w blad.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DecomposeLargeReportGeneratingMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode `generateReport(List<Double> sales)` robaca NARAZ:
         * walidacje (lista niepusta, wartosci >0), sume, srednia, oraz
         * sformatowany raport tekstowy - a nastepnie rozbij ja na min. 4 male
         * metody (validateSales, calculateTotal, calculateAverage,
         * buildReportText), z metoda glowna jako orkiestratorem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_IdentifyTooManyParametersInGivenSignature {
        /*
         * 🧪 Zadanie 12:
         * Dana jest sygnatura `createEmployee(String firstName, String
         * lastName, String department, String position, double salary, String
         * email, String phone)` (7 parametrow). W komentarzu wskaz, ktore
         * parametry logicznie "naleza do siebie" i zaproponuj podzial na 2-3
         * obiekty parametrow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementParameterObjectsFromExercise12 {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj podzial zaproponowany w Zadaniu 12 jako 2-3 recordy
         * (np. PersonalInfo, JobInfo, ContactInfo) i przepisz
         * createEmployee(...) tak, by przyjmowala te obiekty zamiast 7
         * pojedynczych parametrow. Wywolaj i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteMethodViolatingCqsAndFixIt {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode `int incrementAndGet(int[] counterHolder)`, ktora
         * ZWIEKSZA wartosc pod indeksem 0 (command) I ZWRACA nowa wartosc
         * (query) naraz. Nastepnie rozbij ja na `void increment(int[]
         * counterHolder)` i `int getValue(int[] counterHolder)` - pokaz oba
         * uzycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhyMapRemoveIsAcceptableCqsException {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: `java.util.Map.remove(key)` zwraca usuwana wartosc
         * (command + query naraz) - w komentarzu wyjasnij (min. 3 zdania),
         * dlaczego to jest AKCEPTOWALNY wyjatek od CQS w API standardowym, a w
         * WLASNYM kodzie i tak warto trzymac sie CQS tam, gdzie to mozliwe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteMethodWithTwoBooleanFlags {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode `exportData(List<String> data, boolean asCsv, boolean
         * includeHeader)` (2 flagi boolean = 4 kombinacje zachowania w jednej
         * metodzie). Wywolaj ja dla wszystkich 4 kombinacji i w komentarzu
         * napisz, dlaczego 2 flagi sa gorsze niz 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RefactorTwoFlagsIntoEnumAndSeparateMethods {
        /*
         * 🧪 Zadanie 17:
         * Zastap 2 flagi z Zadania 16 enumem `ExportFormat { CSV_WITH_HEADER,
         * CSV_NO_HEADER, PLAIN_WITH_HEADER, PLAIN_NO_HEADER }` i JEDNA metoda
         * `exportData(List<String> data, ExportFormat format)` z switch
         * expression - wywolaj dla wszystkich 4 wartosci enuma.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureMethodLengthOfGivenSnippet {
        /*
         * 🧪 Zadanie 18:
         * Odtworz metode processOrderEverything(...) z teorii lekcji (5
         * odpowiedzialnosci naraz) i policz (recznie, w komentarzu) ile
         * ODPOWIEDZIALNOSCI mozna wyodrebnic - wypisz ich liste z nazwami
         * proponowanych metod docelowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteMethodWithSideEffectOnStaticField {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode `boolean validateAge(int age)`, ktora - oprocz
         * walidacji (age 0-150) - PRZY OKAZJI zwieksza statyczny licznik
         * `validationCallCount`. Wywolaj ja 3 razy i wypisz koncowa wartosc
         * licznika - w komentarzu wyjasnij, dlaczego ten efekt uboczny jest
         * szczegolnie niebezpieczny (globalny, ukryty stan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildOrchestratorMethodReadingLikeTableOfContents {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode `checkoutCart(List<Double> prices, String customerName)`
         * jako CZYSTY orkiestrator (max 5-6 linii, tylko wywolania innych
         * metod: validateCart, calculateTotal, applyLoyaltyDiscount,
         * printReceipt) - zaimplementuj kazda z wywolywanych metod osobno.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorRealisticGodMethodIntoSmallMethods {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode `handleUserRegistration(String email, String password,
         * int age)` robaca NARAZ: walidacje (email zawiera "@", password
         * min. 8 znakow, age>=18), "haszowanie" hasla (symulowane -
         * password.hashCode()), "zapis do bazy" (println), wyslanie "e-maila
         * powitalnego" (println) i zwrocenie podsumowania. Rozbij na min. 5
         * male, dobrze nazwane metody prywatne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignParameterObjectHierarchyForOrderApi {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj API `placeOrder(...)` przyjmujace 3 obiekty parametrow:
         * record Customer(String name, String email), record ShippingInfo(
         * String address, String city, String zipCode), record OrderItems(
         * List<String> productNames, List<Double> prices) - metoda placeOrder
         * przyjmuje te 3 obiekty (max 3 parametry) zamiast 8+ pojedynczych
         * pol. Wywolaj i wypisz podsumowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_IdentifyAllCqsViolationsInGivenClass {
        /*
         * 🧪 Zadanie 23:
         * Napisz klase Inventory z 3 metodami: `boolean removeIfAvailable(String
         * item)` (usuwa I zwraca czy sie udalo), `int reserveAndCount(String
         * item)` (rezerwuje I zwraca liczbe pozostalych), `void addItem(String
         * item)` (tylko command). Wypisz, ktore metody LAMIA CQS i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RefactorInventoryToRespectCqs {
        /*
         * 🧪 Zadanie 24:
         * Zrefaktoryzuj klase Inventory z Zadania 23 tak, by KAZDA metoda byla
         * albo command (void), albo query (zwraca wartosc, nie zmienia stanu) -
         * napisz nowe metody `isAvailable`, `remove`, `reserve`, `countRemaining`,
         * `addItem` i pokaz przyklad uzycia laczacego query+command bez
         * lamania CQS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WriteMethodDoingThreeThingsAndNameTheSmell {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode `processPayment(double amount, String cardNumber)`
         * ktora: waliduje numer karty (dlugosc 16 cyfr), symuluje autoryzacje
         * (amount<=5000 -> sukces), i loguje wynik do konsoli. W komentarzu
         * nazwij KAZDA z 3 odpowiedzialnosci osobno, jakby to byl osobny
         * "smell" (por. Lesson14: Long Method).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DecomposeProcessPaymentWithClearNames {
        /*
         * 🧪 Zadanie 26:
         * Rozbij metode z Zadania 25 na 3 metody o nazwach jednoznacznie
         * opisujacych KAZDA odpowiedzialnosc (np. isValidCardNumber,
         * authorizePayment, logPaymentResult) - metoda glowna processPayment
         * ma tylko je orkiestrowac. Zweryfikuj identyczny wynik dla kwoty 100
         * i 6000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignFluentBuilderAvoidingLongParameterList {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj klase PizzaOrderBuilder z metodami fluent (zwracajacymi
         * `this`): withSize(String), withTopping(String) (mozna wolac kilka
         * razy), withExtraCheese(boolean), oraz metoda build() zwracajaca
         * gotowy opis String. Uzyj jej, by zbudowac zamowienie BEZ konstruktora
         * z 5+ parametrami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareGodMethodVsDecomposedForTestability {
        /*
         * 🧪 Zadanie 28:
         * Wez metode processOrderEverything(...) z teorii (albo z Zadania 21) -
         * w komentarzu napisz, ile ODDZIELNYCH scenariuszy testowych
         * potrzebowalbys, zeby przetestowac ja W CALOSCI za jednym razem, a
         * ile - gdyby kazda odpowiedzialnosc byla osobna, mala metoda
         * testowana NIEZALEZNIE. Wyjasnij zwiazek z latwoscia pisania testow
         * jednostkowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AuditRealMethodFromEarlierChapterAgainstAllRules {
        /*
         * 🧪 Zadanie 29:
         * Wybierz 1 metode z dowolnej wczesniejszej lekcji kursu (np.
         * `_03_collections` lub `_09_jdbc`) i w komentarzu oceń ja wg WSZYSTKICH
         * zasad z tej lekcji: rozmiar, liczba argumentow, obecnosc flag
         * boolean, ukryte efekty uboczne, zgodnosc z CQS - dla kazdej zasady
         * napisz PASS/FAIL z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneCleanRegistrationApi {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletne API
         * `registerCustomer(...)` stosujace WSZYSTKIE zasady z tej lekcji
         * naraz: parametry pogrupowane w 1 obiekt (record), zero flag boolean
         * sterujacych zachowaniem (uzyj enum, jesli potrzebne rozgalezienie),
         * brak ukrytych efektow ubocznych (kazdy efekt uboczny w nazwie
         * metody), pelne rozbicie na male metody z zachowaniem CQS. Wywolaj
         * cale API i wypisz podsumowanie rejestracji.
         */
        public static void main(String[] args) { }
    }
}
