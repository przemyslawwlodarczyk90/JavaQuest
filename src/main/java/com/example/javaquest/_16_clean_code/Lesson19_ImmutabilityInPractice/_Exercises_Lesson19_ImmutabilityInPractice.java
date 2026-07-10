package com.example.javaquest._16_clean_code.Lesson19_ImmutabilityInPractice;

public class _Exercises_Lesson19_ImmutabilityInPractice {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAliasingBugInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), czym jest "blad aliasingu" i dlaczego niezmiennosc go
         * CALKOWICIE eliminuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReproduceAliasingBugWithMutableClass {
        /*
         * 🧪 Zadanie 2:
         * Napisz mutowalna klase `MutableCounter` z polem `int value` i metoda
         * `increment()`. Stworz 2 zmienne wskazujace na TEN SAM obiekt,
         * zwieksz przez jedna z nich i wypisz wartosc widoczna przez druga -
         * zaobserwuj "niespodziewana" zmiane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FixAliasingBugWithImmutableClass {
        /*
         * 🧪 Zadanie 3:
         * Napisz niezmienny odpowiednik `ImmutableCounter` (record lub klasa z
         * `final` polami, metoda `withIncrementedValue()` zwracajaca NOWY
         * obiekt) - pokaz, ze oryginalny obiekt NIGDY nie zmienia stanu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DesignImmutableValueObjectForPhoneNumber {
        /*
         * 🧪 Zadanie 4:
         * Zaprojektuj `record PhoneNumber(String value)` z walidacja w
         * konstruktorze kompaktowym (9 cyfr) - stworz poprawny numer i
         * sprobuj stworzyc niepoprawny, lapiac wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteWitherMethodForSimpleImmutableRecord {
        /*
         * 🧪 Zadanie 5:
         * Zaprojektuj `record Point(int x, int y)` z metoda `withX(int newX)`
         * zwracajaca NOWY Point z ta sama wartoscia y. Zademonstruj, ze
         * oryginalny obiekt sie nie zmienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteSecondWitherMethodForSameRecord {
        /*
         * 🧪 Zadanie 6:
         * Dodaj do Point z Zadania 5 metode `withY(int newY)` - polacz obie
         * metody w lancuch (`point.withX(5).withY(10)`) i wypisz finalny wynik
         * ORAZ potwierdz, ze oryginalny `point` jest nienaruszony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DesignSimpleBuilderForTwoFieldObject {
        /*
         * 🧪 Zadanie 7:
         * Zaprojektuj `record Address(String city, String zipCode)` oraz
         * prosty `AddressBuilder` z metodami `withCity`/`withZipCode`/`build`.
         * Zbuduj 1 obiekt Address przez builder.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReturnMutableListFromGetterAndObserveLeak {
        /*
         * 🧪 Zadanie 8:
         * Napisz klase `BadTagHolder` z polem `List<String> tags` i getterem
         * ZWRACAJACYM referencje BEZPOSREDNIO (bez kopii). Zmodyfikuj liste
         * zwrocona z gettera i pokaz, ze WEWNETRZNY stan obiektu TEZ sie
         * zmienil (wyciek reprezentacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FixLeakWithImmutableCollectionInGetter {
        /*
         * 🧪 Zadanie 9:
         * Popraw klase z Zadania 8: konstruktor kopiuje liste przez
         * `List.copyOf(...)` - sprobuj zmodyfikowac liste zwrocona z gettera i
         * zlap `UnsupportedOperationException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListBenefitsOfImmutabilityFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 4
         * korzysci z niezmiennosci omowione w tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignImmutableMoneyValueObjectWithArithmetic {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj `record Money(double amount, String currency)` z
         * metodami `add(Money other)` i `subtract(Money other)` (obie
         * ZWRACAJA NOWY obiekt, waliduja ZGODNOSC waluty rzucajac wyjatek przy
         * niezgodnosci). Przetestuj dodawanie 2 kwot w tej samej walucie i
         * probe dodania w roznych walutach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RefactorPrimitiveObsessionExampleToImmutableType {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode `sendSms(String phoneNumber)` z walidacja numeru
         * POWTORZONA wewnatrz (Primitive Obsession, por. Lesson14).
         * Zrefaktoryzuj: wprowadz niezmienny `record PhoneNumber` z walidacja
         * w konstruktorze, sendSms przyjmuje teraz PhoneNumber.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignBuilderWithRequiredAndOptionalFieldsValidation {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj `record Event(String name, String date, String
         * description)` oraz `EventBuilder`, gdzie `build()` RZUCA wyjatek,
         * jesli `name` lub `date` (pola WYMAGANE) nie zostaly ustawione -
         * `description` jest OPCJONALNE (domyslnie pusty String). Przetestuj
         * budowanie z i bez opisu, oraz probe budowania bez `name`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareMutableVsImmutableVersionForThreadSafetyReasoning {
        /*
         * 🧪 Zadanie 14:
         * Napisz mutowalna klase `MutableConfig` (pole `String value` +
         * setter) i niezmienna `ImmutableConfig` (pole `final`). W komentarzu
         * wyjasnij (min. 3 zdania), dlaczego `ImmutableConfig` mozna bezpiecznie
         * czytac z wielu watkow BEZ synchronizacji, a `MutableConfig` NIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignImmutableCollectionWrapperForDomainObject {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj klase `ShoppingCart` z prywatnym polem `List<String>
         * items` (kopiowanym przez `List.copyOf` w konstruktorze) i metoda
         * `addItem(String item)` zwracajaca NOWY `ShoppingCart` (wzorzec
         * wither) zamiast mutowac istniejacy. Zademonstruj lancuch
         * `cart.addItem("A").addItem("B")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureObjectAllocationCostOfWitherPatternInLoop {
        /*
         * 🧪 Zadanie 16:
         * Uzywajac klasy Money z Zadania 11 (lub podobnej), napisz petle
         * wykonujaca 1000 wywolan `withAmount(...)` w rzedzie - w komentarzu
         * napisz, ile OBIEKTOW zostalo utworzonych w tej petli (odpowiedz:
         * 1000, bo kazde wywolanie tworzy NOWY obiekt) i kiedy taki koszt
         * moze zaczac miec znaczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignImmutableRecordWithDefensiveCopyOfArrayField {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj klase `ImmutableByteBuffer` z polem `byte[] data`
         * (KOPIOWANYM w konstruktorze przez `Arrays.copyOf`, bo tablice sa
         * ZAWSZE mutowalne, nawet z `final`) i getterem TEZ zwracajacym KOPIE
         * (nie oryginalnej referencji). Zademonstruj, ze modyfikacja tablicy
         * przekazanej do konstruktora PO jego wywolaniu NIE wplywa na
         * wewnetrzny stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefactorMutableDtoToImmutableRecord {
        /*
         * 🧪 Zadanie 18:
         * Napisz mutowalna klase `UserDtoMutable` z polami `String name` i
         * setterami. Zrefaktoryzuj na `record UserDto(String name, String
         * email)` niezmienny - w komentarzu wyjasnij, dlaczego DTO (obiekty
         * przenoszace dane) SZCZEGOLNIE dobrze nadaja sie do bycia niezmiennymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignBuilderProducingDifferentImmutableVariants {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj `PizzaBuilder` z metodami `withSize`, `withTopping`
         * (wielokrotnie wolane, zbiera do wewnetrznej mutowalnej listy) i
         * `build()` zwracajacym niezmienny `record Pizza(String size,
         * List<String> toppings)` (z `List.copyOf` wewnatrz konstruktora
         * recordu). Zbuduj 2 rozne pizze tym samym builderem (2 osobne
         * instancje buildera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealClassFromEarlierChapterForImmutabilityOpportunities {
        /*
         * 🧪 Zadanie 20:
         * Wybierz 1 klase z dowolnej wczesniejszej lekcji kursu (np.
         * `_09_jdbc/Lesson19_Dto`), ktora JEST juz niezmienna, LUB znajdz
         * mutowalna klase-kandydata do zmiany. W komentarzu oceń, czy
         * niezmiennosc bylaby tam korzystna i dlaczego.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullyImmutableOrderDomainModel {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj CALKOWICIE niezmienny model domenowy zamowienia: `record
         * OrderItem(String product, int quantity, Money unitPrice)`, `record
         * Order(String customerName, List<OrderItem> items)` (z `List.copyOf`
         * w konstruktorze) - dodaj metode `Order.calculateTotal()` liczaca
         * sume WSZYSTKICH pozycji. Zademonstruj tworzenie zamowienia z 3
         * pozycjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AddItemToImmutableOrderUsingWitherPattern {
        /*
         * 🧪 Zadanie 22:
         * Dodaj do `Order` z Zadania 21 metode `withAddedItem(OrderItem
         * newItem)` zwracajaca NOWY Order z rozszerzona lista pozycji (stary
         * Order pozostaje NIENARUSZONY). Zademonstruj dodanie pozycji i
         * porownanie sumy PRZED i PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignThreadSafeSharedConfigurationUsingImmutability {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj niezmienna klase `AppConfig` (kilka pol `final`) i
         * zademonstruj (uzywajac `Thread` z `_05_multithreading`) odczyt TEGO
         * SAMEGO obiektu `AppConfig` z 3 roznych watkow RÓWNOCZESNIE, BEZ
         * zadnej synchronizacji - potwierdz brak race condition dzieki
         * niezmiennosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareImmutableVsMutableConfigUnderConcurrentModification {
        /*
         * 🧪 Zadanie 24:
         * Napisz mutowalny odpowiednik `MutableAppConfig` z Zadania 23 (pole
         * bez `final`, z setterem) - uruchom 1 watek MODYFIKUJACY konfiguracje
         * w petli i 1 watek CZYTAJACY ja rownolegle (bez synchronizacji). W
         * komentarzu opisz (bez konieczności deterministycznego odtworzenia)
         * ryzyko, jakiego NIE ma wersja niezmienna z Zadania 23.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignComplexBuilderWithFluentValidationChain {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj `record FlightBooking(String passenger, String from,
         * String to, String seatClass)` oraz `FlightBookingBuilder`, gdzie
         * `build()` WALIDUJE wszystkie 4 pola naraz i - jesli cokolwiek brakuje
         * - rzuca WYJATEK Z LISTA wszystkich brakujacych pol naraz (nie tylko
         * pierwszego, por. Lesson17: collect-all errors).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RefactorGodClassMutableStateIntoImmutablePipeline {
        /*
         * 🧪 Zadanie 26:
         * Napisz mutowalna klase `ReportBuilderMutable` z polami
         * modyfikowanymi przez kolejne wywolania metod (`addSection`,
         * `setTitle`) i metoda `generate()` zwracajaca String NA PODSTAWIE
         * BIEZACEGO (mutowalnego) stanu. Zrefaktoryzuj na PIPELINE
         * niezmiennych krokow - kazdy krok zwraca NOWY, niezmienny obiekt
         * "raportu w budowie", ostatni krok generuje finalny String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasureAndDiscussPerformanceTradeoffOfImmutability {
        /*
         * 🧪 Zadanie 27:
         * Napisz 2 wersje sumowania 100 000 wartosci: (a) mutowalny
         * `int[]`/akumulator zmieniany "w miejscu" w petli, (b) niezmienny
         * `record RunningTotal(int value)` z metoda `withAdded(int x)`
         * tworzaca NOWY obiekt w KAZDEJ iteracji petli. Zmierz przyblizony
         * czas obu (np. `System.nanoTime()`) i w komentarzu skomentuj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignImmutableEventLogWithAppendReturningNewInstance {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj niezmienny `record EventLog(List<String> events)` (z
         * `List.copyOf`) z metoda `append(String event)` zwracajaca NOWY
         * EventLog z dodanym zdarzeniem. Zademonstruj sekwencje 5 wywolan
         * `append` w lancuchu i potwierdz, ze KAZDY posredni EventLog w
         * lancuchu pozostaje nienaruszony (np. zapisz 1 posredni wynik do
         * zmiennej i porownaj go z finalnym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveImmutabilityAdoptionChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) pomagajaca zdecydowac, KIEDY warto uczynic klase
         * niezmienna w realnym projekcie - laczac korzysci (aliasing,
         * watkowosc, Primitive Obsession) i kompromisy (koszt kopii) z tej
         * lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullyImmutableBookingDomainWithBuilder {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny,
         * CALKOWICIE niezmienny model domenowy systemu rezerwacji sali
         * konferencyjnej laczacy WSZYSTKIE techniki z tej lekcji: niezmienne
         * typy wartosciowe (np. TimeSlot, RoomId) naprawiajace Primitive
         * Obsession, wzorzec wither do "modyfikacji" rezerwacji, Builder do
         * stopniowego tworzenia zlozonej rezerwacji z walidacja WSZYSTKICH
         * wymaganych pol naraz, oraz niezmienne kolekcje w publicznym API.
         * Zademonstruj pelny przeplyw: budowa, "modyfikacja" przez wither,
         * proba naruszenia niezmiennosci z zewnatrz (zlapana).
         */
        public static void main(String[] args) { }
    }
}
