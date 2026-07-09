package com.example.javaquest._13_libraries.Lesson05_LombokAdvancedAndPitfalls;

public class _Exercises_Lesson05_LombokAdvancedAndPitfalls {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_Slf4jBasicLogging {
        /*
         * 🧪 Zadanie 1:
         * Utworz klase zagniezdzona OrderProcessor oznaczona adnotacja @Slf4j.
         * W metodzie process(String orderId) wywolaj log.info("Przetwarzam
         * zamowienie {}", orderId). Utworz instancje i wywolaj process("ORD-100").
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_Slf4jMultipleLevels {
        /*
         * 🧪 Zadanie 2:
         * Rozszerz klase z @Slf4j (np. PaymentService) o metode pay(double kwota),
         * ktora loguje: log.debug("Start platnosci: {}", kwota), log.info("Platnosc
         * zaakceptowana"), oraz - jesli kwota < 0 - log.error("Nieprawidlowa kwota:
         * {}", kwota). Przetestuj wywolaniami pay(150.0) i pay(-50.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ToBuilderCreateAndModify {
        /*
         * 🧪 Zadanie 3:
         * Utworz klase EmailMessage z @Getter, @ToString, @Builder(toBuilder = true)
         * i polami String to, String subject, String body. Zbuduj oryginalny obiekt
         * (to = "jan@example.com", subject = "Witaj", body = "Tresc"), potem przez
         * toBuilder().subject("Nowy temat").build() utworz zmodyfikowana kopie.
         * Wypisz oba obiekty i pokaz, ze oryginal jest nietkniety.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ToBuilderMultipleVariants {
        /*
         * 🧪 Zadanie 4:
         * Dla klasy z Zadania 3 (albo nowej, analogicznej) zbuduj JEDEN obiekt
         * bazowy EmailMessage ("bok@sklep.pl", "Potwierdzenie", "Dziekujemy"), a
         * nastepnie za pomoca toBuilder() utworz TRZY warianty rozny tylko polem
         * subject: "Wysylka", "Dostawa", "Anulowanie". Wypisz wszystkie 4 obiekty
         * (oryginal + 3 warianty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WithSingleFieldChange {
        /*
         * 🧪 Zadanie 5:
         * Utworz niemutowalna klase @Value @With Point3D z polami double x, y, z.
         * Utworz punkt (1.0, 2.0, 3.0), potem uzyj withZ(10.0) - wypisz oryginal i
         * nowy obiekt, potwierdz w komentarzu, ze oryginal ma nadal z = 3.0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WithChainedCalls {
        /*
         * 🧪 Zadanie 6:
         * Dla klasy Point3D z Zadania 5 wykonaj LANCUCH wywolan:
         * punkt.withX(100.0).withY(200.0).withZ(300.0) - wypisz wynik posredni
         * (kazdy krok jest NOWYM obiektem) oraz finalny punkt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EqualsHashCodeDefaultNoInheritance {
        /*
         * 🧪 Zadanie 7:
         * Utworz klase @Getter @Setter @EqualsAndHashCode Book (BEZ dziedziczenia)
         * z polami String isbn, String title. Utworz dwa obiekty o tym samym isbn
         * ale roznym title - sprawdz book1.equals(book2) (powinno byc false, bo
         * WSZYSTKIE pola licza sie domyslnie) oraz z tym samym isbn i title
         * (powinno byc true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_EqualsHashCodeCallSuperFalseExplicit {
        /*
         * 🧪 Zadanie 8:
         * Utworz klase bazowa Animal (@Getter @Setter, pole String species) oraz
         * podklase @EqualsAndHashCode(callSuper = false) Dog extends Animal (pole
         * String name). Utworz dwa psy o ROZNYM species, ale tym samym name -
         * wypisz wynik equals() i wyjasnij w komentarzu, dlaczego species jest
         * ignorowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EqualsHashCodeCallSuperTrue {
        /*
         * 🧪 Zadanie 9:
         * Zmien podejscie z Zadania 8: podklasa @EqualsAndHashCode(callSuper = true)
         * Cat extends Animal. Utworz dwa koty o ROZNYM species i tym samym name -
         * wypisz wynik equals() (powinno byc false) i porownaj w komentarzu z
         * wynikiem Zadania 8.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SimpleUtilityClass {
        /*
         * 🧪 Zadanie 10:
         * Utworz klase @UtilityClass StringHelper z metoda
         * String reverse(String tekst) oraz stala int MAX_LENGTH = 100 (bez slowa
         * "static" w kodzie zrodlowym). Wywolaj StringHelper.reverse("lombok") i
         * wypisz StringHelper.MAX_LENGTH.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DataOnMutableEntityHashCodeChanges {
        /*
         * 🧪 Zadanie 11:
         * Utworz klase @Data Customer z polami Long id, String email. Utworz
         * obiekt (id=1L, email="stary@test.pl") i wypisz jego hashCode(). Zmien
         * email przez setter na "nowy@test.pl" i wypisz hashCode() ponownie -
         * pokaz w komentarzu, ze wartosc SIE ZMIENILA (bo email jest w
         * hashCode()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReproduceHashSetLostObjectBug {
        /*
         * 🧪 Zadanie 12:
         * Uzywajac klasy Customer z Zadania 11, dodaj obiekt (id=1L,
         * email="a@test.pl") do java.util.HashSet<Customer>. Sprawdz
         * set.contains(customer) (powinno byc true), zmien email PRZEZ SETTER na
         * "b@test.pl", sprawdz set.contains(customer) ponownie - wykaz "zgubiony"
         * obiekt (contains = false, ale set.size() sie nie zmienil).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FixWithEqualsAndHashCodeOfId {
        /*
         * 🧪 Zadanie 13:
         * Napraw problem z Zadania 12: utworz klase @Getter @Setter @ToString
         * @EqualsAndHashCode(of = "id") CustomerFixed z tymi samymi polami. Powtorz
         * scenariusz (dodaj do HashSet, zmien email, sprawdz contains) i pokaz, ze
         * TYM RAZEM obiekt NIE ginie z zestawu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BidirectionalRecursionStackOverflow {
        /*
         * 🧪 Zadanie 14:
         * Utworz klasy @Getter @Setter @ToString @EqualsAndHashCode Department
         * (pole String name, List<Employee> employees) oraz Employee (pole String
         * name, Department department) - obie strony BEZ wykluczen. Powiaz
         * department z jednym employee (obustronnie) i wywolaj
         * department.toString() w bloku try-catch (StackOverflowError) - zlap i
         * wypisz komunikat potwierdzajacy nieskonczona rekurencje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FixBidirectionalWithExclude {
        /*
         * 🧪 Zadanie 15:
         * Napraw klasy z Zadania 14: w klasie Employee dodaj
         * @ToString.Exclude @EqualsAndHashCode.Exclude na polu department. Powtorz
         * scenariusz (department z jednym employee) i pokaz, ze
         * department.toString() oraz employee.toString() dzialaja BEZ
         * StackOverflowError.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuilderDefaultField {
        /*
         * 🧪 Zadanie 16:
         * Utworz klase @Getter @ToString @Builder(toBuilder = true)
         * ConnectionSettings z polami String host, int port oraz
         * @Builder.Default int retryCount = 3. Zbuduj obiekt PODAJAC tylko host i
         * port ("db.local", 5432) - wypisz retryCount (powinno byc 3, wartosc
         * domyslna). Potem zbuduj drugi obiekt z retryCount(10) jawnie ustawionym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareWithVersusToBuilder {
        /*
         * 🧪 Zadanie 17:
         * Miedzy klasa @Value @With Point3D (Zadanie 5) a klasa
         * @Builder(toBuilder = true) EmailMessage (Zadanie 3) - dla ZMIANY
         * JEDNEGO pola wypisz oba warianty kodu obok siebie jako String w
         * komentarzu (withX(...) vs toBuilder().pole(...).build()) i w komentarzu
         * podsumuj, kiedy ktory jest wygodniejszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_Slf4jParameterizedLogging {
        /*
         * 🧪 Zadanie 18:
         * Utworz klase @Slf4j InventoryService z metoda
         * updateStock(String sku, int oldQty, int newQty), ktora loguje JEDNYM
         * wywolaniem log.info z DWOMA placeholderami "{}":
         * "Zmiana stanu {}: {} -> {}" (sku, oldQty, newQty). Wywolaj metode dla
         * sku = "SKU-42", oldQty = 10, newQty = 25.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_UtilityClassMultipleMembers {
        /*
         * 🧪 Zadanie 19:
         * Utworz klase @UtilityClass MathHelper z polem double EPSILON = 0.0001
         * oraz DWOMA metodami: boolean almostEqual(double a, double b) (uzywa
         * EPSILON) i double average(double... liczby). Wywolaj obie metody z
         * przykladowymi danymi (np. almostEqual(1.0001, 1.0), average(2.0, 4.0,
         * 6.0)) i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainDelombokInComment {
        /*
         * 🧪 Zadanie 20:
         * Bez uruchamiania delombok: dla klasy
         * "@Getter @Setter @EqualsAndHashCode(of = "id") class Product { private
         * Long id; private String name; }" napisz w komentarzu (min. 15 linii)
         * PELNY, jawny kod Javy, jaki wygenerowalby Lombok (konstruktor, gettery,
         * settery, equals, hashCode) - dokladnie tak jak w przykladzie z teorii
         * lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ThreeLevelInheritanceCallSuperChain {
        /*
         * 🧪 Zadanie 21:
         * Utworz trzypoziomowa hierarchie: @Getter @Setter Vehicle (pole String
         * vin), @Getter @Setter @EqualsAndHashCode(callSuper = true) Car extends
         * Vehicle (pole String model), @Getter @Setter
         * @EqualsAndHashCode(callSuper = true) SportsCar extends Car (pole int
         * topSpeed). Utworz dwa obiekty SportsCar o ROZNYM vin, ale tym samym
         * model i topSpeed - sprawdz equals() (powinno byc false, bo lancuch
         * callSuper=true dociera do vin) i wyjasnij dlaczego w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImmutableDtoFullRoundTrip {
        /*
         * 🧪 Zadanie 22:
         * Utworz @Value @With ProductPrice (double netAmount, double vatRate).
         * Dodaj (recznie, bez Lomboka) metode obliczGross() zwracajaca netAmount *
         * (1 + vatRate). Utworz cene (100.0, 0.23), policz kwote brutto, potem
         * uzyj withVatRate(0.08) i policz brutto DLA NOWEGO obiektu - pokaz, ze
         * oryginal ma nadal stara stawke VAT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MutableEntityCacheBugAndFix {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj prosty "cache": java.util.Set<Customer> cache (klasa Customer
         * z Zadania 11, @Data). Dodaj do cache 3 klientow (id 1, 2, 3), zmien
         * email klienta o id=2 PRZEZ SETTER, a nastepnie sprobuj usunac go z cache
         * (cache.remove(ten_klient)) - pokaz, ze remove() ZAWODZI (zwraca false,
         * obiekt "zostaje uwieziony" w cache mimo referencji). Powtorz cwiczenie z
         * CustomerFixed (Zadanie 13) i pokaz, ze remove() dziala poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BidirectionalModelSafeToStringAndEquals {
        /*
         * 🧪 Zadanie 24:
         * Rozbuduj naprawiony model Department/Employee (Zadanie 15) o DRUGIEGO
         * employee w tym samym dziale. Wypisz department.toString() (lista obu
         * pracownikow, bez rekurencji), sprawdz department.equals(department)
         * (musi byc true, bez StackOverflowError) i policz department.hashCode()
         * dwukrotnie, potwierdzajac stabilnosc wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_Slf4jPlusBuilderServiceClass {
        /*
         * 🧪 Zadanie 25:
         * Polacz @Slf4j i @Builder(toBuilder = true) w JEDNEJ klasie
         * ReportRequest (pola String reportType, java.time.LocalDate fromDate,
         * java.time.LocalDate toDate). Napisz metode generate(ReportRequest req),
         * ktora loguje log.info("Generuje raport {} za okres {} - {}", ...) - a
         * nastepnie przez toBuilder() zmien tylko toDate na inna date i wywolaj
         * generate() dla obu wariantow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_UtilityClassReflectionInstantiationBlocked {
        /*
         * 🧪 Zadanie 26:
         * Dla klasy @UtilityClass MathHelper (Zadanie 19) sprobuj utworzyc
         * instancje przez refleksje: pobierz prywatny konstruktor
         * (getDeclaredConstructor()), ustaw setAccessible(true) i wywolaj
         * newInstance() w bloku try-catch - zlap wyjatek (Lombok generuje
         * konstruktor rzucajacy UnsupportedOperationException) i wypisz jego
         * komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiLevelEqualsHashCodeMixedCallSuper {
        /*
         * 🧪 Zadanie 27:
         * Zmodyfikuj hierarchie z Zadania 21 tak, aby SportsCar mial
         * @EqualsAndHashCode(callSuper = false) (a Car nadal callSuper = true).
         * Utworz dwa obiekty SportsCar o ROZNYM vin i model, ale tym samym
         * topSpeed - sprawdz equals() i w komentarzu wyjasnij, dlaczego wynik
         * jest INNY niz w Zadaniu 21 (lancuch callSuper zostal "przerwany" na
         * poziomie SportsCar).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GroupCustomersByFixedEquals {
        /*
         * 🧪 Zadanie 28:
         * Utworz liste 5 obiektow CustomerFixed (Zadanie 13) z ROZNYMI id, ale
         * DWA z nich majace TEN SAM id (duplikat logiczny mimo roznych obiektow w
         * pamieci). Wloz je wszystkie do java.util.HashSet<CustomerFixed> i pokaz,
         * ze zbior ma 4 elementy (duplikat po id zostal odrzucony) - wypisz
         * finalny rozmiar zbioru i jego zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullDelombokComparisonForValueWith {
        /*
         * 🧪 Zadanie 29:
         * Dla klasy "@Value @With class Money { long amountInCents; String
         * currency; }" napisz w komentarzu (min. 20 linii) PELNY wygenerowany kod
         * Javy: pola jako "private final", konstruktor, gettery BEZ prefiksu "get"
         * (zgodnie z konwencja @Value dla rekordopodobnych klas - amountInCents()
         * i currency()), equals/hashCode/toString ORAZ metody
         * withAmountInCents(long)/withCurrency(String).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneOrderDomainWithAllPitfallsFixed {
        /*
         * 🧪 Zadanie 30:
         * Zamodeluj domene zamowien z UWZGLEDNIENIEM wszystkich pulapek z tej
         * lekcji: klasa @Slf4j @Getter @Setter @ToString @EqualsAndHashCode(of =
         * "orderId") @Builder(toBuilder = true) Order (String orderId,
         * String status, List<OrderItem> items) oraz @Getter @Setter @ToString
         * @EqualsAndHashCode(of = "sku") OrderItem (String sku, int quantity,
         * @ToString.Exclude @EqualsAndHashCode.Exclude Order order - back-
         * reference). Utworz zamowienie "ORD-500" z 2 pozycjami (obustronnie
         * powiazanymi), zaloguj jego utworzenie (log.info), wypisz toString() BEZ
         * rekurencji, dodaj je do java.util.HashSet<Order>, zmien status przez
         * toBuilder().status("WYSLANE").build() (NOWY obiekt) i potwierdz, ze
         * oryginal w zbiorze nadal jest znajdywalny przez set.contains(...) mimo
         * ze status sie zmienil (bo status NIE wchodzi do equals/hashCode).
         */
        public static void main(String[] args) { }
    }
}
