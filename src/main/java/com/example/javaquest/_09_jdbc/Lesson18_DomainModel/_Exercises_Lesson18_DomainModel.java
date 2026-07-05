package com.example.javaquest._09_jdbc.Lesson18_DomainModel;

public class _Exercises_Lesson18_DomainModel {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimpleImmutableRecordEntity {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj record Book(long id, String title, String author,
         * BigDecimal price) - prosta encja domenowa (jak Product w
         * lekcji). Utwórz 2 instancje i wypisz ich dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ClassWithBusinessMethod {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj klasę Customer (pola: id, name, totalPurchases -
         * BigDecimal) z konstruktorem i getterami, ORAZ metodą biznesową
         * boolean isEligibleForDiscount() (zwraca true, jeśli
         * totalPurchases >= 500). Przetestuj dla 2 klientów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ProductWithDerivedValue {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj record Product(String name, BigDecimal unitPrice, int
         * quantityInStock) z metodą getTotalStockValue() (unitPrice *
         * quantityInStock). Przetestuj dla 3 produktów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ValidationInConstructor {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj klasę Product z konstruktorem WALIDUJĄCYM, że price
         * musi być większe od zera (IllegalArgumentException w
         * przeciwnym razie). Zademonstruj utworzenie poprawnego produktu
         * i próbę utworzenia z ceną -10 (złap wyjątek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AggregateWithAddItemValidation {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj klasę ShoppingCart z listą CartItem (produkt +
         * ilość) i metodą addItem(Product, int quantity) rzucającą
         * IllegalArgumentException dla quantity <= 0 (jak Order.addItem
         * w lekcji). Zademonstruj poprawne dodanie i odrzuconą próbę
         * (quantity=0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TotalValueMethodWithBigDecimal {
        /*
         * 🧪 Zadanie 6:
         * Rozszerz ShoppingCart z Zadania 5 o metodę getTotalValue()
         * sumującą wartość WSZYSTKICH pozycji (Stream + reduce, jak
         * Order.getTotalValue() w lekcji). Przetestuj na koszyku z 3
         * pozycjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ThresholdBasedBusinessRule {
        /*
         * 🧪 Zadanie 7:
         * Dodaj do ShoppingCart metodę boolean qualifiesForFreeShipping()
         * (analogiczną do Order w lekcji, ale z INNYM progiem, np. 200
         * zl). Przetestuj dla koszyka powyżej i poniżej progu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SameDomainObjectFromDifferentConstructionPaths {
        /*
         * 🧪 Zadanie 8:
         * Utwórz DWA identyczne obiekty Product - jeden przez wywołanie
         * konstruktora z literałami, drugi przez metodę fromRawValues(String
         * name, String priceAsText, String quantityAsText) parsującą
         * Stringi (symulacja "innego źródła danych"). Porównaj (println),
         * że oba zachowują się identycznie w metodach biznesowych, mimo
         * różnego sposobu powstania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ValidationMethodOutsideConstructor {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj klasę Reservation z metodą setNights(int nights)
         * rzucającą IllegalArgumentException dla nights <= 0. Zademonstruj
         * poprawne wywołanie i odrzuconą próbę (nights=0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RecordVsMutableClassComparison {
        /*
         * 🧪 Zadanie 10:
         * Zdefiniuj TĘ SAMĄ koncepcję (np. Address) DWOMA sposobami: jako
         * record AddressRecord(String street, String city) (niezmienny) i
         * jako klasę AddressMutable z setterami (zmienna). Wypisz
         * komentarz (println), kiedy warto wybrać który wariant.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultiClassDomainModelLibrary {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj mini model domenowy biblioteki: klasy Book, Member,
         * Loan (Loan zawiera Book, Member, borrowedDate, dueDate). Dodaj
         * metodę biznesową Loan.isOverdue(LocalDate today). Przetestuj
         * dla wypożyczenia przeterminowanego i nieprzeterminowanego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_EncapsulatedInternalRepresentation {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj klasę Price, która WEWNĘTRZNIE przechowuje cenę jako
         * long (grosze), ale udostępnia PUBLICZNY getter
         * getAmountAsBigDecimal() zwracający BigDecimal w złotówkach
         * (dzielenie przez 100). Zademonstruj utworzenie i odczyt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_InvariantEnforcedThroughoutLifetime {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj klasę BankAccount (balance) z metodą withdraw(BigDecimal
         * amount) rzucającą IllegalStateException("Niewystarczajace
         * srodki"), jeśli amount > balance. Zademonstruj udane i nieudane
         * wypłacenie (balance NIE zmienia się po nieudanej próbie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_StateMachineWithinDomainClass {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj klasę Order ze statusem (enum OrderStatus {NEW, PAID,
         * SHIPPED}) i metodami markAsPaid() (dozwolone tylko z NEW) oraz
         * markAsShipped() (dozwolone tylko z PAID) - rzucającymi
         * IllegalStateException dla niedozwolonych przejść. Zademonstruj
         * poprawną sekwencję i próbę markAsShipped() na zamówieniu NEW
         * (błąd).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MoneyValueObjectIntroduced {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj record Money(BigDecimal amount, String currency) z
         * metodą add(Money other) rzucającą IllegalArgumentException,
         * jeśli waluty się różnią. Użyj Money jako typu pola price w
         * klasie Product z poprzednich zadań (zamiast gołego BigDecimal).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BidirectionalDomainReferences {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj klasy Team (nazwa, List<Player>) i Player (imię, wiek,
         * referencja do swojego Team). Dodaj metodę Team.averageAge()
         * liczącą średni wiek WSZYSTKICH graczy zespołu. Przetestuj dla
         * zespołu z 4 graczami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_EqualitySemanticsBasedOnId {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj klasę Product z polem id, przesłoń equals()/hashCode()
         * tak, aby DWA obiekty Product z TYM SAMYM id były uznawane za
         * równe (niezależnie od innych pól, np. name może się różnić - np.
         * po aktualizacji). Zademonstruj porównanie dwóch instancji o tym
         * samym id, ale różnej nazwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DerivedCollectionWithStreams {
        /*
         * 🧪 Zadanie 18:
         * Rozszerz klasę Order (z listą OrderItem) o metodę
         * getItemsAbovePrice(BigDecimal threshold) zwracającą listę
         * pozycji, których wartość jednostkowa przekracza próg (przez
         * Stream API). Przetestuj na zamówieniu z 5 pozycjami o różnych
         * cenach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DefensiveCopyOfInternalCollection {
        /*
         * 🧪 Zadanie 19:
         * W klasie Order upewnij się, że getItems() zwraca NIEMODYFIKOWALNĄ
         * kopię wewnętrznej listy (Collections.unmodifiableList albo
         * List.copyOf), nie referencję do oryginału. Zademonstruj, że
         * próba order.getItems().add(...) rzuca
         * UnsupportedOperationException, chroniąc integralność zamówienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_InjectedDiscountPolicy {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj funkcyjny interfejs DiscountPolicy { BigDecimal
         * apply(BigDecimal total); }. Napisz metodę
         * Order.getTotalValueWithDiscount(DiscountPolicy policy),
         * przyjmującą różne polityki jako lambdy (np. "10% zniżki", "brak
         * zniżki"). Przetestuj z DWOMA różnymi politykami na tym samym
         * zamówieniu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AggregateWithMultipleInvariants {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj ShoppingCart wymuszający JEDNOCZEŚNIE: maksymalnie 10
         * pozycji, brak duplikatów produktu (ten sam produkt nie może być
         * dodany dwa razy - zamiast tego zwiększana ilość), oraz
         * minimalną wartość zamówienia 20 zl przy checkout(). Zademonstruj
         * naruszenie KAŻDEJ z tych 3 reguł osobno (3 różne wyjątki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullMoneyValueObjectWithArithmetic {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz Money z Zadania 15 o subtract(Money) i multiply(int
         * factor), wszystkie zwracające NOWY obiekt Money (niezmienność).
         * Użyj Money konsekwentnie w CAŁYM agregacie Order (ceny pozycji,
         * suma zamówienia) i zademonstruj obliczenie sumy zamówienia
         * WYŁĄCZNIE przez operacje na Money.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DomainEventsCollectedInAggregate {
        /*
         * 🧪 Zadanie 23:
         * Dodaj do Order listę domainEvents (List<Object>) oraz metodę
         * ship(), która zmienia status i DODAJE do tej listy obiekt
         * OrderShippedEvent(long orderId, LocalDateTime timestamp).
         * Zademonstruj wywołanie ship() i odczytanie zapisanego zdarzenia
         * z listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AnemicVsRichDomainModelComparison {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj TĘ SAMĄ logikę biznesową (np. "czy faktura jest
         * przeterminowana i jaka jest kara za zwłokę") DWA razy: (a)
         * "anemiczny" model - klasa Invoice ma TYLKO gettery/settery, a
         * cała logika żyje w statycznej klasie InvoiceUtils; (b) "bogaty"
         * model - klasa Invoice ma metody isOverdue()/calculatePenalty()
         * jako metody instancyjne. Porównaj (println) czytelność kodu
         * wywołującego w obu wariantach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PrivateConstructorWithStaticFactory {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj klasę Order z PRYWATNYM konstruktorem i publiczną
         * statyczną metodą fabrykującą Order.createEmpty(Customer buyer),
         * jedynym sposobem utworzenia zamówienia (gwarantuje poprawny
         * stan początkowy - status NEW, pusta lista pozycji).
         * Zademonstruj utworzenie zamówienia WYŁĄCZNIE przez tę fabrykę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SpecificationPatternForFiltering {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj funkcyjny interfejs Specification<T> { boolean
         * isSatisfiedBy(T candidate); }. Zbuduj 2 konkretne specyfikacje
         * dla Order: isLargeOrder (total > 500) i isFromVipCustomer
         * (customer.isVip()). Napisz metodę filterOrders(List<Order>,
         * Specification<Order>...) łączącą WIELE specyfikacji (AND) i
         * przefiltruj listę 6 zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultipleViewsFromSameDomainObject {
        /*
         * 🧪 Zadanie 27:
         * Dodaj do Order TRZY różne metody "widoku": toSummaryLine()
         * (jedna linia tekstu), toDetailedReport() (wieloliniowy opis ze
         * WSZYSTKIMI pozycjami), toInvoiceText() (sformatowany tekst
         * "faktury"). Wywołaj wszystkie trzy na TYM SAMYM obiekcie Order
         * i wypisz wynik każdej - pokazując, że jeden model domenowy może
         * "obsłużyć" wiele różnych sposobów prezentacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FreshDomainModelDifferentIndustry {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj mini model domenowy dla ZUPEŁNIE innej branży niż sklep -
         * np. szpital: klasy Patient, Doctor, Appointment (z metodą
         * biznesową isConflicting(Appointment other) sprawdzającą
         * nakładanie się dwóch wizyt w czasie). Zademonstruj wykrycie
         * konfliktu i braku konfliktu dla 2 par wizyt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestableBusinessRulesInIsolation {
        /*
         * 🧪 Zadanie 29:
         * Napisz w main() serię "testów" (bez frameworka, tylko println
         * "PASS"/"FAIL") weryfikujących co najmniej 5 reguł biznesowych z
         * poprzednich zadań (np. BankAccount.withdraw, Order.markAsShipped
         * z niedozwolonego stanu, Money.add z różnymi walutami) -
         * zademonstruj, że logika CZYSTO domenowa (bez bazy danych, bez
         * I/O) jest łatwa do przetestowania w izolacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneCompleteDomainModel {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj i zaimplementuj kompletny (ale mały) model domenowy
         * dla WYBRANEJ dziedziny (np. "siłownia i karnety" albo
         * "wypożyczalnia rowerów") - co najmniej 4-5 klas, JEDEN agregat
         * główny, JEDEN obiekt wartości (value object, jak Money/Address),
         * co najmniej 3 metody biznesowe wymuszające realne reguły. W
         * main() zademonstruj: utworzenie obiektów, co najmniej 2 udane
         * operacje biznesowe i co najmniej 2 odrzucone (wyjątki z powodu
         * naruszenia reguł biznesowych).
         */
        public static void main(String[] args) { }
    }
}
