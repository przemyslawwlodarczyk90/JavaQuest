package com.example.javaquest._13_libraries.Lesson03_LombokBasics;

public class _Exercises_Lesson03_LombokBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_GetterOnlyBook {
        /*
         * 🧪 Zadanie 1:
         * Utworz klase Book z polami: String title, String author, int pages.
         * Dodaj adnotacje @Getter NA KLASIE (Lombok) - bez setterow. W main()
         * utworz obiekt (recznym konstruktorem) i wypisz wszystkie 3 wartosci
         * przez wygenerowane gettery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetterSetterOnClass {
        /*
         * 🧪 Zadanie 2:
         * Utworz klase Movie z polami: String title, int releaseYear, double
         * rating. Dodaj @Getter i @Setter NA KLASIE. W main() utworz obiekt,
         * zmien rating setterem na 9.5 i wypisz go przez getter.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FieldLevelGetterOnly {
        /*
         * 🧪 Zadanie 3:
         * Utworz klase Invoice z polami: String number (@Getter na polu,
         * bez settera), double amount (@Getter i @Setter na polu). W main()
         * zweryfikuj, ze setAmount() dziala, a setNumber() NIE ISTNIEJE
         * (kod ktory probowalby go wywolac nie skompiluje sie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ToStringDefault {
        /*
         * 🧪 Zadanie 4:
         * Utworz klase Country z polami: String name, long population, String
         * capital. Dodaj @Getter i @ToString (bez parametrow, domyslne
         * zachowanie). W main() wypisz obiekt przez System.out.println -
         * powinny pojawic sie WSZYSTKIE 3 pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ToStringExcludeSensitiveField {
        /*
         * 🧪 Zadanie 5:
         * Utworz klase BankCard z polami: String owner, String cardNumber,
         * String pin. Dodaj @ToString(exclude = "pin") tak, aby PIN NIGDY nie
         * pojawil sie w wypisanym napisie. Zweryfikuj to w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ToStringOfSelectedFields {
        /*
         * 🧪 Zadanie 6:
         * Utworz klase Employee z polami: String firstName, String lastName,
         * double salary, String pesel. Dodaj @ToString(of = {"firstName",
         * "lastName"}) tak, aby tylko imie i nazwisko trafialy do napisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EqualsAndHashCodeBasic {
        /*
         * 🧪 Zadanie 7:
         * Utworz klase Point z polami int x, int y. Dodaj @EqualsAndHashCode
         * (i @Getter). W main() utworz dwa obiekty Point(2, 3) i Point(2, 3) -
         * zweryfikuj, ze point1.equals(point2) zwraca true, mimo ze to dwa
         * ROZNE obiekty w pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_EqualsAndHashCodeExclude {
        /*
         * 🧪 Zadanie 8:
         * Utworz klase Session z polami: String userId, long createdAtMillis
         * (znacznik czasu, TECHNICZNY, nie powinien wplywac na rownosc).
         * Dodaj @EqualsAndHashCode(exclude = "createdAtMillis"). Zweryfikuj w
         * main(), ze dwie Session z tym samym userId, ale ROZNYM
         * createdAtMillis, sa uznawane za rowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DataAnnotationBasic {
        /*
         * 🧪 Zadanie 9:
         * Utworz klase Laptop z polami: String brand, String model, double
         * price (wszystkie NIE-final, mutowalne). Dodaj JEDNA adnotacje
         * @Data. W main() utworz obiekt (przez recznie napisany konstruktor
         * lub przez settery), zmien price, wypisz go przez toString.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareManualVsLombokLineCount {
        /*
         * 🧪 Zadanie 10:
         * Napisz klase Address (String street, String city, String zipCode)
         * DWA RAZY: raz RECZNIE (gettery, settery, toString, equals,
         * hashCode napisane samodzielnie), raz z @Data. W komentarzu w main()
         * zapisz, ile linii kodu zaoszczedziles dzieki Lombokowi.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MixedFieldAndClassLevelAnnotations {
        /*
         * 🧪 Zadanie 11:
         * Utworz klase Account z polami: String id (final, TYLKO getter),
         * String owner (getter i setter), double balance (getter i setter).
         * Zastosuj @Getter NA KLASIE oraz @Setter TYLKO na polach owner i
         * balance (na poziomie pola). Zweryfikuj w main(), ze setId() nie
         * istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ToStringCombinedExcludeAndCustomFields {
        /*
         * 🧪 Zadanie 12:
         * Utworz klase Order z polami: String orderId, java.util.List&lt;String&gt;
         * items, String internalDebugNotes. Dodaj @ToString(exclude =
         * "internalDebugNotes"). Wypelnij liste 3 elementami i zweryfikuj, ze
         * toString() poprawnie wypisuje liste, ale NIE notatki debugowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_EqualsAndHashCodeWithInheritancePitfall {
        /*
         * 🧪 Zadanie 13:
         * Utworz klase Vehicle (pole String vin, @Getter @Setter) oraz klase
         * Car extends Vehicle (dodatkowe pole String model,
         * @EqualsAndHashCode BEZ callSuper). Utworz dwa obiekty Car o ROZNYM
         * vin, ale TYM SAMYM model - zweryfikuj (i zapisz w komentarzu), ze
         * blednie sa uznawane za rowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FixInheritancePitfallWithCallSuper {
        /*
         * 🧪 Zadanie 14:
         * Napraw problem z Zadania 13 dodajac @EqualsAndHashCode(callSuper =
         * true) do Car. Zweryfikuj w main(), ze te same dwa obiekty co w
         * Zadaniu 13 sa teraz POPRAWNIE uznane za NIErowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DataOnClassWithCollectionField {
        /*
         * 🧪 Zadanie 15:
         * Utworz klase Playlist z polami: String name, java.util.List&lt;String&gt;
         * songs. Dodaj @Data. Dodaj 3 utwory do listy, wypisz obiekt przez
         * toString() i zweryfikuj poprawnosc equals() dla dwoch Playlist o
         * tej samej nazwie i tych samych utworach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UsingLombokObjectInHashSet {
        /*
         * 🧪 Zadanie 16:
         * Utworz klase City z polami String name, String country. Dodaj
         * @Data. Utworz java.util.HashSet&lt;City&gt; i dodaj do niego DWA
         * obiekty City o IDENTYCZNYCH wartosciach pol - zweryfikuj, ze zbior
         * ma rozmiar 1 (dzieki wygenerowanemu equals/hashCode).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ToStringForNestedLombokObject {
        /*
         * 🧪 Zadanie 17:
         * Utworz klase Address (@Data: street, city) oraz klase Person (@Data:
         * name, Address address - pole typu Address). W main() utworz Person z
         * zagniezdzonym Address i wypisz obiekt Person - zweryfikuj, ze
         * toString() Person poprawnie zagniezdza toString() Address.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareTwoDataObjectsWithDifferentOneField {
        /*
         * 🧪 Zadanie 18:
         * Utworz klase Ticket z polami: String eventName, int seatNumber,
         * double price. Dodaj @Data. Utworz dwa bilety o tych samych
         * wartosciach WSZYSTKICH pol oprocz seatNumber - zweryfikuj (i
         * wypisz komunikat), ze NIE sa rowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_EqualsAndHashCodeExcludeMultipleFields {
        /*
         * 🧪 Zadanie 19:
         * Utworz klase LogEntry z polami: String message, String level, long
         * timestamp, String threadName. Dodaj @EqualsAndHashCode(exclude =
         * {"timestamp", "threadName"}) tak, aby tylko message i level
         * decydowaly o rownosci. Zweryfikuj to w main() na 2 przykladach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorLegacyClassToData {
        /*
         * 🧪 Zadanie 20:
         * Wez klase ProductManual z teorii lekcji (skopiuj jej pola: name,
         * price, quantity) i napisz jej odpowiednik z @Data od zera, we
         * WLASNEJ klasie o nazwie ProductRefactored. Porownaj zachowanie
         * (toString, equals) obu wersji na tych samych danych wejsciowych.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_HierarchyOfThreeClassesWithCallSuper {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj hierarchie 3 poziomow: LivingBeing (String id, @Getter
         * @Setter) -&gt; Animal extends LivingBeing (String species,
         * @EqualsAndHashCode(callSuper = true)) -&gt; Dog extends Animal
         * (String breed, @EqualsAndHashCode(callSuper = true)). Zweryfikuj,
         * ze equals() na poziomie Dog uwzglednia POLA ZE WSZYSTKICH 3 KLAS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CustomEqualsCoexistingWithLombok {
        /*
         * 🧪 Zadanie 22:
         * Utworz klase Money z polami: java.math.BigDecimal amount, String
         * currency. Dodaj @Getter, ale NAPISZ RECZNIE equals()/hashCode() tak,
         * aby porownanie amount ignorowalo koncowe zera (uzyj
         * BigDecimal.compareTo zamiast equals wewnatrz recznej metody).
         * Wyjasnij w komentarzu, dlaczego w tym przypadku NIE uzyto
         * @EqualsAndHashCode.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DataClassAsMapKeyMutationPitfallPreview {
        /*
         * 🧪 Zadanie 23:
         * Utworz klase Coordinate (@Data: int x, int y). Wstaw obiekt jako
         * klucz do java.util.HashMap&lt;Coordinate, String&gt;, POTEM zmien pole x
         * przez setter i sprobuj odczytac wartosc po tym samym obiekcie -
         * zapisz w komentarzu, co sie stalo (pelne wyjasnienie mechanizmu w
         * Lekcji 5 - tu tylko zaobserwuj efekt).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ToStringOfWithInheritedFields {
        /*
         * 🧪 Zadanie 24:
         * Utworz klase Shape (String color, @Getter @Setter) oraz Circle
         * extends Shape (double radius, @ToString(callSuper = true) -
         * sprawdz w dokumentacji Lomboka, ze @ToString TEZ ma parametr
         * callSuper). Zweryfikuj, ze toString() obiektu Circle zawiera
         * ZAROWNO color, jak i radius.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImmutableStyleFieldsWithDataAndFinal {
        /*
         * 🧪 Zadanie 25:
         * Utworz klase Temperature z polami: private final double celsius,
         * private final String scale. Dodaj @Data. Zweryfikuj, ze setterow
         * NIE MA dla zadnego z pol (bo oba sa final), a mimo to obiekt da
         * sie utworzyc konstruktorem wygenerowanym przez Lomboka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LargeClassTenFieldsWithData {
        /*
         * 🧪 Zadanie 26:
         * Utworz klase Customer z DOKLADNIE 10 polami roznych typow (String,
         * int, double, boolean, java.time.LocalDate, itd.). Dodaj @Data.
         * Wypisz w main() PELNY toString() i zweryfikuj rownosc dwoch
         * identycznych obiektow - w komentarzu oszacuj, ile linii kodu
         * musialbys napisac RECZNIE dla tej samej funkcjonalnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EqualsContractVerification {
        /*
         * 🧪 Zadanie 27:
         * Dla dowolnej klasy z @Data zweryfikuj W KODZIE (nie tylko w
         * komentarzu) kontrakt equals(): refleksywnosc (a.equals(a)),
         * symetrycznosc (a.equals(b) == b.equals(a)), spojnosc z hashCode
         * (a.equals(b) implikuje a.hashCode() == b.hashCode()) - wypisz
         * wynik kazdego sprawdzenia jako true/false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ComparingLombokGeneratedVsRecordEquality {
        /*
         * 🧪 Zadanie 28:
         * Utworz klase Vector2D (@Data: double x, double y) ORAZ rekord
         * Java (record Vector2DRecord(double x, double y)) o tych samych
         * polach. Porownaj w main() zachowanie toString()/equals() OBU
         * wariantow dla tych samych wartosci - zapisz w komentarzu
         * roznice/podobienstwa (pelne omowienie w Lekcji 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullEntityStyleClassWithAllBasicAnnotations {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj klase Reservation symulujaca rekord z bazy danych:
         * String id (@Getter, bez settera), String guestName (@Getter
         * @Setter), java.time.LocalDate checkIn, checkOut (@Getter @Setter),
         * boolean paid (@Getter @Setter), String internalAuditLog (WYKLUCZONE
         * z @ToString I z @EqualsAndHashCode). Uzasadnij w komentarzu kazda
         * decyzje adnotacyjna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniLibraryOfDataClassesWithTests {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj MALA "biblioteke" 4 klas modelowych (np. Book, Author,
         * Library, Loan) z rozsadnym doborem @Getter/@Setter/@ToString/
         * @EqualsAndHashCode/@Data na kazdej z nich (NIE wszedzie musi byc
         * @Data - uzasadnij wybor kazdej klasy w komentarzu). Napisz w
         * main() scenariusz uzycia laczacy wszystkie 4 klasy i wypisujacy
         * czytelny raport stanu "biblioteki".
         */
        public static void main(String[] args) { }
    }
}
