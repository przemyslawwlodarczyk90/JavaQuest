package com.example.javaquest._12_hibernate.Lesson09_EmbeddableTypes;

public class _Exercises_Lesson09_EmbeddableTypes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicEmbeddableAddress {
        /*
         * 🧪 Zadanie 1:
         * Utworz @Embeddable Address (street, city, zipCode) i encje Supplier
         * (id, name, @Embedded address) na H2
         * ("jdbc:h2:mem:l09ex01;DB_CLOSE_DELAY=-1"). Zapisz dostawce z adresem i
         * odczytaj go w NOWEJ Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifySingleTableNoJoin {
        /*
         * 🧪 Zadanie 2:
         * Zapisz Supplier z Zadania 1 i sprawdz w show_sql, ze wygenerowany INSERT
         * dotyczy JEDNEJ tabeli (supplier) - BEZ osobnej tabeli "address".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_EmbeddableWithBusinessMethod {
        /*
         * 🧪 Zadanie 3:
         * Dodaj do Address metode formatOneLine() (jak w lekcji). Zapisz obiekt,
         * odczytaj go i wypisz wynik tej metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReuseEmbeddableInSecondEntity {
        /*
         * 🧪 Zadanie 4:
         * Uzyj TEJ SAMEJ klasy Address w DRUGIEJ, niepowiazanej encji (np.
         * Warehouse). Zapisz po jednym obiekcie kazdej i zweryfikuj, ze kazda ma
         * WLASNE kolumny adresu w swojej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_EmbeddableWithMoneyValueObject {
        /*
         * 🧪 Zadanie 5:
         * Utworz @Embeddable Money (amount, currency) i uzyj go w encji Product
         * (@Embedded price). Zapisz produkt z cena 100 PLN i odczytaj ja poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NullEmbeddableField {
        /*
         * 🧪 Zadanie 6:
         * Zapisz Supplier z polem address = null (caly komponent embeddable
         * niewypelniony). Zapisz w komentarzu zaobserwowane zachowanie (wszystkie
         * kolumny adresu jako NULL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EmbeddableEqualsAndHashCode {
        /*
         * 🧪 Zadanie 7:
         * Dodaj do Address wlasna equals()/hashCode() (porownujace WSZYSTKIE pola -
         * value object). Utworz DWA obiekty Address o tych samych wartosciach i
         * porownaj przez equals() (powinno byc true, mimo ze to rozne instancje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleEmbeddedFieldsSameEntity {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do encji Product DWA rozne embeddable (Money price ORAZ inny prosty
         * komponent Dimensions - width, height, depth). Zapisz i odczytaj kompletny
         * obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_QueryByEmbeddableFieldHql {
        /*
         * 🧪 Zadanie 9:
         * Zapisz 3 dostawcow z roznymi miastami w Address. Uzyj HQL (podglad przed
         * Lesson18: "from Supplier s where s.address.city = :city") zeby znalezc
         * dostawcow z konkretnego miasta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_UpdateEmbeddableFieldOnly {
        /*
         * 🧪 Zadanie 10:
         * Znajdz Supplier, zmien TYLKO pole city w jego Address (bez tworzenia
         * nowego obiektu Address) i zapisz zmiane (dirty checking) - zweryfikuj w
         * NOWEJ Session.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AttributeOverrideForDuplicateComponent {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do encji Customer DWA pola typu Address: homeAddress i
         * billingAddress. Uzyj @AttributeOverrides na KAZDYM z nich, zeby uniknac
         * konfliktu nazw kolumn. Zapisz obiekt z ROZNYMI adresami w kazdym polu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VerifyColumnNamesAfterOverride {
        /*
         * 🧪 Zadanie 12:
         * Dla encji z Zadania 11: sprawdz recznym SQL (INFORMATION_SCHEMA.COLUMNS)
         * DOKLADNE nazwy kolumn wygenerowanych dla homeAddress i billingAddress -
         * potwierdz brak konfliktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NestedEmbeddableInsideEmbeddable {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj @Embeddable ContactInfo zawierajacy WEWNATRZ SIEBIE inny @Embeddable
         * (np. Address) - zagniezdzone komponenty. Zapisz i odczytaj pelny obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_EmbeddableWithEnumField {
        /*
         * 🧪 Zadanie 14:
         * Dodaj do Money pole "currency" typu enum (PLN, EUR, USD) z @Enumerated(STRING)
         * (podglad przed Lesson10). Zapisz produkt z cena w EUR i odczytaj ja poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MoneyArithmeticMethod {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do Money metode add(Money other) zwracajaca NOWY obiekt Money (suma
         * kwot, ta sama waluta - rzuc wyjatek jesli waluty sie roznia). Przetestuj
         * na dwoch obiektach Money.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_EmbeddableInCollectionElementCollection {
        /*
         * 🧪 Zadanie 16:
         * Dodaj do encji Order pole List<Address> deliveryAddresses oznaczone
         * @ElementCollection (kolekcja embeddable, NIE encji) - zapisz zamowienie z
         * 2 adresami dostawy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareEmbeddableVsSeparateEntity {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj TA SAMA koncepcje (Address) RAZ jako @Embeddable, RAZ jako pelna,
         * osobna @Entity z wlasna tabela i relacja @OneToOne - porownaj w komentarzu
         * wygenerowany schemat i zlozonosc kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ValidationInsideEmbeddable {
        /*
         * 🧪 Zadanie 18:
         * Dodaj do konstruktora/settera Address WLASNA walidacje (np. zipCode musi
         * miec format "00-000") rzucajaca IllegalArgumentException dla
         * nieprawidlowego formatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImmutableEmbeddableWithFinalFields {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj Money jako NIEMUTOWALNY (final pola, ustawiane tylko w
         * konstruktorze, add() z Zadania 15 zwraca zawsze NOWY obiekt). Zweryfikuj,
         * ze Hibernate wciaz potrafi go zapisac/odczytac (dostep przez pola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_EmbeddableToStringFormatting {
        /*
         * 🧪 Zadanie 20:
         * Dodaj do Money i Address wlasne toString() zwracajace czytelny,
         * jednoliniowy format. Zapisz obiekt Product i wypisz go bezposrednio
         * (wykorzystujac toString() encji, ktore deleguje do komponentow).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCustomerWithTwoAddressesAndOrders {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj Customer z homeAddress/billingAddress (Zadanie 11) ORAZ relacja
         * @OneToMany do Order (kazdy Order z wlasnym Money price i List<Address>
         * deliveryAddresses z Zadania 16) - zapisz pelny, powiazany graf obiektow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_QueryAcrossEmbeddableAndAssociation {
        /*
         * 🧪 Zadanie 22:
         * Napisz zapytanie HQL laczace pole embeddable (customer.homeAddress.city) Z
         * polem relacji (order.price.amount) - znajdz wszystkie zamowienia klientow
         * z konkretnego miasta o cenie powyzej progu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MigrateEmbeddableToSeparateEntityRefactor {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj RAZWOJ wymagan: zacznij od Address jako @Embeddable, potem
         * "zrefaktoryzuj" na osobna @Entity Address z wlasnym Id (bo okazalo sie, ze
         * potrzebny jest do niego dostep niezalezny od Customer) - napisz w
         * komentarzu, co dokladnie trzeba zmienic w kodzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CustomAttributeConverterInsideEmbeddable {
        /*
         * 🧪 Zadanie 24:
         * Dodaj do Money (lub Address) pole korzystajace z WLASNEGO
         * AttributeConverter (podglad przed Lesson10, np. konwersja formatu kodu
         * pocztowego). Zweryfikuj poprawna konwersje przy zapisie/odczycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PerformanceOfManyEmbeddableFields {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj encje z 5 roznymi polami @Embedded (razem ~15 dodatkowych kolumn).
         * Zmierz czas zapisu 100 takich obiektow i zapisz w komentarzu, czy embeddable
         * wprowadza zauwazalny narzut wzgledem prostszej, "plaskiej" encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_EmbeddableWithOptionalNestedFields {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj encje z @Embedded polem, ktore MOZE byc czesciowo null (np. Address
         * z wypelnionym city, ale bez street) - zapisz w komentarzu, czy Hibernate
         * pozwala na taki "czesciowy" stan (brak walidacji NOT NULL na poziomie
         * komponentu, chyba ze jawnie dodana).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ReportGroupedByEmbeddableField {
        /*
         * 🧪 Zadanie 27:
         * Zapisz 10 dostawcow w 3 roznych miastach (Address.city). Napisz raport HQL
         * "select s.address.city, count(s) from Supplier s group by s.address.city"
         * (podglad przed Lesson19) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_EmbeddableVersusJsonColumnComparison {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz porownanie (min. 3 punkty) @Embeddable (osobne
         * kolumny) vs zapisanie CALEGO obiektu jako JSON w jednej kolumnie tekstowej
         * (@Converter, Lesson10) - kiedy ktore podejscie ma sens.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RefactorFlatEntityToEmbeddables {
        /*
         * 🧪 Zadanie 29:
         * Wez "plaska" encje z 8 polami (np. street/city/zip/country + firstName/
         * lastName/email/phone) i PRZEBUDUJ ja na dwie @Embeddable (Address,
         * ContactInfo) - porownaj czytelnosc PRZED i PO w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullEmbeddableModelCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY model domenowy sklepu z: Customer
         * (2 adresy, Zadanie 11), Order (Money price, List<Address> dostaw, Zadanie
         * 16), oraz WLASNA logika biznesowa na komponentach (Money.add, Address.
         * formatOneLine) - zademonstruj pelny scenariusz zlozenia zamowienia.
         */
        public static void main(String[] args) { }
    }
}
