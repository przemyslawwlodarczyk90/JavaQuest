package com.example.javaquest._09_jdbc.Lesson18_DomainModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class _Lesson18_DomainModel {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 CZYM JEST MODEL DOMENOWY?
         * ============================================================
         * MODEL DOMENOWY (domain model) to zestaw klas reprezentujących
         * POJĘCIA Z DZIEDZINY BIZNESOWEJ, którą modeluje aplikacja - a
         * NIE strukturę tabel w bazie danych. "Domena" to po prostu
         * "dziedzina, w której działa firma/aplikacja": sklep internetowy
         * ma domenę złożoną z pojęć takich jak Użytkownik (User), Produkt
         * (Product), Zamówienie (Order), Kategoria (Category), Adres
         * (Address).
         *
         * To bardzo ważne rozróżnienie:
         * - TABELA w bazie danych = jak dane są ZAPISANE (kolumny, klucze
         *   obce, indeksy, ograniczenia)
         * - ENCJA DOMENOWA = jak dane są ROZUMIANE przez biznes (Zamówienie
         *   ma pozycje i sumę wartości, Użytkownik ma adres e-mail i może
         *   złożyć zamówienie)
         *
         * Te dwie perspektywy CZĘSTO się pokrywają (tabela "users" i klasa
         * User wyglądają podobnie), ale nie MUSZĄ - i z czasem mogą się
         * rozjechać (np. jedna klasa domenowa Order może być złożona z
         * danych pochodzących z kilku tabel).
         *
         * 🔹 NA TYM ETAPIE KURSU (czysty JDBC)
         * Klasy domenowe to ZWYKŁE klasy Javy - żadnych adnotacji, żadnej
         * "magii". Sami piszemy kod, który tworzy obiekty User/Order i sami
         * decydujemy, jak je zapisać w bazie (SQL, który już znasz).
         * W kolejnym rozdziale poznasz Hibernate (ORM) - tam te same klasy
         * domenowe zostaną OZNACZONE adnotacją @Entity i framework SAM
         * zajmie się przekładaniem ich na SQL. Już teraz warto pisać klasy
         * domenowe tak, jakby miały "przetrwać" tę zmianę - czyli skupione
         * na logice biznesowej, a nie na szczegółach zapytań SQL.
         */

        System.out.println("=== BUDOWA MODELU DOMENOWEGO: User, Product, Order ===");

        User buyer = new User(1, "ania@example.com", "Ania");

        Product keyboard = new Product(1, "Klawiatura mechaniczna", new BigDecimal("299.00"));
        Product mouse = new Product(2, "Mysz bezprzewodowa", new BigDecimal("129.50"));

        Order order = new Order(1, buyer);
        order.addItem(keyboard, 1);
        order.addItem(mouse, 2);

        System.out.println("Zamowienie #" + order.getId() + " zlozone przez: " + order.getBuyer().getDisplayName());
        order.getItems().forEach(item ->
                System.out.println(" - " + item.quantity() + "x " + item.product().name()
                        + " = " + item.getSubtotal() + " zl"));

        /*
         * ============================================================
         * 🔍 ENCJA DOMENOWA MA ZACHOWANIE, NIE TYLKO DANE
         * ============================================================
         * Kluczowa cecha DOBREGO modelu domenowego: klasy nie są tylko
         * "workami na dane" (gettery/settery i nic więcej). Zawierają
         * METODY BIZNESOWE, które wyrażają reguły dziedziny - tu np.
         * Order.getTotalValue() (suma wartości pozycji) i
         * Order.addItem() (dodanie pozycji z walidacją ilości).
         * To odróżnia model domenowy od "głupich" struktur danych.
         */

        System.out.println("\n=== METODY BIZNESOWE ===");
        System.out.println("Wartosc calkowita zamowienia: " + order.getTotalValue() + " zl");
        System.out.println("Czy zamowienie kwalifikuje sie do darmowej dostawy (>= 300 zl)? "
                + order.qualifiesForFreeShipping());

        try {
            order.addItem(mouse, 0); // reguła biznesowa: ilość musi być dodatnia
        } catch (IllegalArgumentException e) {
            System.out.println("\nProba dodania pozycji z iloscia 0 odrzucona: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔹 MODEL DOMENOWY JEST NIEZALEŻNY OD BAZY DANYCH
         * ============================================================
         * Zwróć uwagę: klasy User, Product, Order NIE WIEDZĄ nic o SQL,
         * ResultSet ani Connection. Można by je stworzyć ręcznie w
         * pamięci (tak jak wyżej), wczytać z pliku JSON, otrzymać z API
         * sieciowego albo złożyć z wyniku zapytania SQL (Lesson17) -
         * klasa domenowa wygląda IDENTYCZNIE niezależnie od źródła danych.
         * To właśnie oddzielenie "czym coś JEST" (domena) od "skąd to
         * WZIĘLIŚMY" (persystencja) jest sednem dobrego projektowania.
         */

        System.out.println("\n=== KONIEC LEKCJI ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Model domenowy = klasy reprezentujące pojęcia BIZNESOWE
         *   (User, Product, Order, Category, Address), nie strukturę tabel
         * - W czystym JDBC to zwykłe klasy Javy, BEZ adnotacji (@Entity
         *   pojawi się dopiero z Hibernate w kolejnym rozdziale)
         * - Dobra klasa domenowa ma METODY BIZNESOWE (np.
         *   Order.getTotalValue(), Order.qualifiesForFreeShipping()),
         *   a nie tylko gettery/settery
         * - Model domenowy jest niezależny od źródła danych - te same
         *   klasy działają niezależnie od tego, czy dane pochodzą z SQL,
         *   JSON, API czy zostały utworzone ręcznie w kodzie
         */
    }

    /** Encja domenowa reprezentująca użytkownika sklepu. */
    private static class User {
        private final long id;
        private final String email;
        private final String firstName;

        User(long id, String email, String firstName) {
            this.id = id;
            this.email = email;
            this.firstName = firstName;
        }

        String getDisplayName() {
            return firstName + " (" + email + ")";
        }
    }

    /** Encja domenowa reprezentująca produkt w ofercie sklepu. */
    private record Product(long id, String name, BigDecimal price) {
    }

    /** Pojedyncza pozycja zamówienia - produkt + zamówiona ilość. */
    private record OrderItem(Product product, int quantity) {
        BigDecimal getSubtotal() {
            return product.price().multiply(BigDecimal.valueOf(quantity));
        }
    }

    /**
     * Encja domenowa reprezentująca zamówienie - zawiera METODY BIZNESOWE
     * (getTotalValue, qualifiesForFreeShipping), a nie tylko dane.
     */
    private static class Order {
        private final long id;
        private final User buyer;
        private final List<OrderItem> items = new ArrayList<>();

        Order(long id, User buyer) {
            this.id = id;
            this.buyer = buyer;
        }

        void addItem(Product product, int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Ilosc musi byc dodatnia, otrzymano: " + quantity);
            }
            items.add(new OrderItem(product, quantity));
        }

        BigDecimal getTotalValue() {
            return items.stream()
                    .map(OrderItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        boolean qualifiesForFreeShipping() {
            return getTotalValue().compareTo(new BigDecimal("300.00")) >= 0;
        }

        long getId() {
            return id;
        }

        User getBuyer() {
            return buyer;
        }

        List<OrderItem> getItems() {
            return items;
        }
    }
}
