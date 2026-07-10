package com.example.javaquest._16_clean_code.Lesson18_NullHandling;

public class _Exercises_Lesson18_NullHandling {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainBillionDollarMistakeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), dlaczego Tony Hoare nazwal referencje null "swoim
         * miliardowym bledem" i dlaczego kompilator Javy nie odroznia "tu jest
         * wartosc" od "tu moze nic nie byc".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReproduceNpeFromMethodReturningNull {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode `String findCityByZipCode(String zipCode)` zwracajaca
         * null dla nieznanego kodu. Wywolaj ja z nieznanym kodem, a nastepnie
         * (BEZ sprawdzenia null) wywolaj `.toUpperCase()` na wyniku - zlap
         * NullPointerException w try-catch i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddFailFastValidationWithRequireNonNull {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode `createUser(String username, String email)` uzywajaca
         * `Objects.requireNonNull(arg, "opis")` dla obu parametrow. Wywolaj ja
         * z email=null i zlap NullPointerException, wypisujac wlasny komunikat
         * z requireNonNull.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareFailFastVsLateFailureStackTrace {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania) sytuacje, gdy
         * null jest sprawdzony NATYCHMIAST na wejsciu metody (fail-fast) z
         * sytuacja, gdy null "przecieka" 5 wywolan metod dalej, zanim wybuchnie
         * NullPointerException - ktora latwiej zdebugowac i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteMethodReturningOptionalForMissingResult {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode `Optional<String> findDepartmentByEmployeeId(String
         * id)` zwracajaca Optional.empty() dla nieznanego id. Wywolaj ja dla
         * znanego i nieznanego id, uzywajac `.map(...).orElse(...)` do
         * zbudowania opisu bez ryzyka NPE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhenOptionalIsAppropriateReturnType {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), kiedy Optional
         * jako typ zwracany JEST wlasciwy (brak wyniku = normalny przypadek
         * biznesowy), a kiedy NIE jest wlasciwy (blad programisty, kolekcje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DesignNullObjectForMissingLogger {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj interfejs Logger z metoda log(String message).
         * Zaimplementuj ConsoleLogger (prawdziwe println) oraz NoOpLogger jako
         * Null Object (metoda log() nic nie robi). Napisz metode
         * `processWithLogging(Logger logger)` uzywana z OBIEMA
         * implementacjami, bez sprawdzania null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReturnEmptyCollectionInsteadOfNull {
        /*
         * 🧪 Zadanie 8:
         * Napisz metode `List<String> findTagsForArticle(String articleId)`
         * zwracajaca `List.of()` (nie null!) dla artykulu bez tagow. Wywolaj ja
         * i przejdz wynik petla for-each - pokaz, ze petla dziala bez wyjatku
         * nawet dla pustego wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DemonstrateNpeFromCollectionReturningNull {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode `List<String> findTagsBad(String articleId)` zwracajaca
         * null (zamiast pustej listy). Wywolaj petle for-each na jej wyniku BEZ
         * sprawdzenia null i zlap NullPointerException - porownaj z Zadaniem 8.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAllFourStrategiesWithOneExampleEach {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu 4 strategie obslugi null z tej
         * lekcji (fail-fast, Optional jako return type, Null Object, pusta
         * kolekcja) - dla kazdej podaj JEDNO wlasne (inne niz w teorii)
         * zastosowanie z zycia.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ValidateConstructorArgumentsWithRequireNonNull {
        /*
         * 🧪 Zadanie 11:
         * Napisz klase Product z konstruktorem walidujacym WSZYSTKIE 3
         * parametry (String name, String category, Double price) przez
         * Objects.requireNonNull - stworz obiekt poprawny oraz sprobuj stworzyc
         * z category=null, lapiac wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ChainOptionalMapCallsForNestedLookup {
        /*
         * 🧪 Zadanie 12:
         * Napisz metody `Optional<String> findUserEmail(String userId)` i
         * `Optional<String> findDomainFromEmail(String email)` - polacz je
         * przez `.flatMap(...)`, by z userId dostac Optional<String> z domena
         * e-maila, bez zagniezdzonych if-ow sprawdzajacych null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignNullObjectForShoppingCartDiscount {
        /*
         * 🧪 Zadanie 13:
         * Odtworz interfejs Discount i klasy PercentageDiscount/NoDiscount z
         * teorii (lub napisz podobne dla "kodu rabatowego") - napisz metode
         * `findDiscountForCode(String code)` zwracajaca ZAWSZE nie-null
         * Discount (NoDiscount.INSTANCE dla nieznanego kodu). Wywolaj dla
         * znanego i nieznanego kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReturnEmptyMapInsteadOfNullForStatistics {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode `Map<String, Integer> countWordsByCategory(String
         * text)` zwracajaca `Collections.emptyMap()` dla pustego tekstu -
         * wywolaj dla pustego stringa i dla niepustego, iterujac po wyniku bez
         * sprawdzania null w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorNpeProneMethodToOptionalReturnType {
        /*
         * 🧪 Zadanie 15:
         * Dana jest metoda `String findManagerName(String employeeId)`
         * zwracajaca null dla pracownika bez managera (np. CEO). Przepisz ja na
         * `Optional<String> findManagerName(String employeeId)` i pokaz
         * wywolanie z `.orElse("Brak przelozonego")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignNullObjectForUnauthenticatedUser {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj interfejs User z metoda `List<String> getPermissions()`.
         * Zaimplementuj RegularUser (prawdziwe uprawnienia) oraz
         * GuestUser/AnonymousUser jako Null Object (pusta lista uprawnien,
         * NIGDY null). Napisz metode `hasPermission(User user, String perm)`
         * dzialajaca identycznie dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseOrElseThrowForRequiredButMissingValue {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode `Optional<String> findRequiredConfigValue(String key)`
         * - wywolaj ja z `.orElseThrow(() -> new IllegalStateException("brak
         * wymaganej konfiguracji: " + key))` dla brakujacego klucza i zlap
         * wyjatek, wypisujac komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_IdentifyWhichOfThreeMethodsShouldUseOptional {
        /*
         * 🧪 Zadanie 18:
         * Dane sa 3 opisy metod: (a) "znajdz uzytkownika po e-mailu - moze go
         * nie byc", (b) "policz sume zamowien - zawsze zwraca liczbe (0 dla
         * braku)", (c) "znajdz WSZYSTKICH uzytkownikow w miescie - moze byc
         * pusta lista". W komentarzu wskaz, KTORA metoda powinna zwracac
         * Optional, a ktore NIE (i dlaczego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CombineFailFastAndOptionalInOneWorkflow {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode `processOrder(String customerId, Optional<String>
         * couponCode)` - waliduj customerId przez requireNonNull (fail-fast),
         * a couponCode obsluz przez Optional (rabat 10% jesli present, 0% jesli
         * empty). Wywolaj dla obu przypadkow couponCode.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildNullSafeChainWithoutIfStatements {
        /*
         * 🧪 Zadanie 20:
         * Napisz klasy Company{Optional<Address> address} i
         * Address{String city} - napisz metode `findCompanyCity(Company c)`
         * zwracajaca Optional<String> UZYWAJAC WYLACZNIE `.flatMap`/`.map` (bez
         * ANI JEDNEGO `if (x != null)`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignCompleteNullSafeRepository {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj klase InMemoryUserRepository z metodami:
         * `Optional<User> findById(String id)` (moze brakowac), `List<User>
         * findAll()` (nigdy null, moze byc pusta), `void save(User user)`
         * (waliduje user przez requireNonNull). Zaimplementuj i przetestuj
         * wszystkie 3 metody dla istniejacych i brakujacych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorLegacyNullReturningApiToModernApi {
        /*
         * 🧪 Zadanie 22:
         * Napisz "legacy" klase LegacyProductLookup z metoda `Product
         * findById(String id)` zwracajaca null dla braku - a obok NOWA klase
         * ModernProductLookup opakowujaca legacy i zwracajaca
         * `Optional<Product>` (adapter konwertujacy null na Optional.empty()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignNullObjectHierarchyWithMultipleImplementations {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj interfejs PaymentProcessor z metoda `boolean
         * processPayment(double amount)`. Zaimplementuj CreditCardProcessor,
         * PaypalProcessor, oraz DisabledPaymentProcessor (Null Object - zawsze
         * zwraca false + loguje "platnosci wylaczone"). Napisz metode
         * `resolveProcessor(String type)` zwracajaca ODPOWIEDNI processor,
         * NIGDY null (domyslnie DisabledPaymentProcessor).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildValidationChainThrowingOnFirstNullField {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `validateRegistrationForm(String username, String
         * email, String password, String phone)` sprawdzajaca WSZYSTKIE 4 pola
         * przez requireNonNull Z WLASNYM komunikatem dla kazdego - wywolaj z
         * roznymi kombinacjami null i zlap/wypisz KTORE pole zawiodlo jako
         * pierwsze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareNullCheckVsOptionalVsNullObjectForSameProblem {
        /*
         * 🧪 Zadanie 25:
         * Wez 1 problem (np. "znajdz opiekuna klienta") i zaimplementuj GO
         * TRZEMA sposobami: (a) zwracajac null + recznym `if != null`, (b)
         * zwracajac Optional, (c) uzywajac Null Object. W komentarzu porownaj
         * czytelnosc kodu WYWOLUJACEGO dla wszystkich trzech wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignNullSafeBuilderWithRequiredAndOptionalFields {
        /*
         * 🧪 Zadanie 26:
         * Napisz builder EventBuilder z polami wymaganymi (name, date -
         * walidowane przez requireNonNull w build()) i opcjonalnymi
         * (description jako Optional<String>, domyslnie Optional.empty()).
         * Zbuduj event z i bez opisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RefactorFeatureEnvyStyleNullCheckChainWithOptional {
        /*
         * 🧪 Zadanie 27:
         * Dana jest metoda z zagniezdzonymi sprawdzeniami null: `if (order !=
         * null) { if (order.getCustomer() != null) { if
         * (order.getCustomer().getAddress() != null) {...}}}`. Napisz taka
         * metode (z klasami Order/Customer/Address majacymi pola typu
         * Optional), a nastepnie przepisz ja na lancuch `.flatMap` bez zadnego
         * jawnego `if (x != null)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignApiMixingAllFourStrategiesCorrectly {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj klase OrderService z 4 metodami, kazda uzywajaca INNEJ
         * strategii z tej lekcji tam, gdzie jest ona wlasciwa: placeOrder(...)
         * (fail-fast na wejsciu), findOrderById(...) (Optional), resolveShipper
         * (...) (Null Object - DefaultShipper), listOrderItems(...) (pusta
         * lista zamiast null). Zademonstruj wszystkie 4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveNullHandlingDecisionChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste/drzewo
         * decyzyjne (min. 5 punktow) pomagajace wybrac wlasciwa strategie
         * obslugi "braku wartosci" w zaleznosci od kontekstu (parametr wejsciowy
         * vs wartosc zwracana, kolekcja vs pojedynczy obiekt, normalny
         * przypadek biznesowy vs blad programisty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneNullSafeCustomerOnboardingSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny system
         * "onboardingu klienta" laczacy WSZYSTKIE 4 strategie naraz: fail-fast
         * walidacja danych wejsciowych, Optional jako wynik wyszukiwania
         * istniejacego klienta, Null Object dla "domyslnego planu
         * subskrypcji" gdy klient nie wybral zadnego, oraz pusta lista (nie
         * null) dla "historii zamowien nowego klienta". Wywolaj cale demo i
         * wypisz podsumowanie.
         */
        public static void main(String[] args) { }
    }
}
