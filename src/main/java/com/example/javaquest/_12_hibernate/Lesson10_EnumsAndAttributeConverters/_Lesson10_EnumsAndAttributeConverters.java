package com.example.javaquest._12_hibernate.Lesson10_EnumsAndAttributeConverters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson10_EnumsAndAttributeConverters {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: MAPOWANIE ENUM I KONWERTERY: @Enumerated, @Converter ===");

        /*
         * ============================================================
         * 📦 @Enumerated(EnumType.ORDINAL) - ZAPIS JAKO LICZBA
         * ============================================================
         * ORDINAL zapisuje POZYCJĘ wartości enum w jego deklaracji
         * (licząc od 0) jako zwykłą liczbę w bazie. Kompaktowe (1 bajt/
         * mała liczba całkowita), ale BARDZO KRUCHE - jeśli ktoś kiedyś
         * ZMIENI KOLEJNOŚĆ stałych enum (albo wstawi nową POŚRODKU),
         * WSZYSTKIE już zapisane dane w bazie nagle zaczną oznaczać
         * COŚ INNEGO niż wcześniej - cichy, bardzo groźny błąd danych,
         * niewykrywalny przez kompilator.
         */
        System.out.println("\n=== EnumType.ORDINAL ===");
        System.out.println("Zapisuje POZYCJE (0,1,2...) w deklaracji enum - KRUCHE przy zmianie kolejnosci stalych!");

        /*
         * ============================================================
         * 🔹 @Enumerated(EnumType.STRING) - ZAPIS JAKO TEKST
         * ============================================================
         * STRING zapisuje NAZWĘ stałej enum (np. "PENDING", "SHIPPED")
         * jako tekst w bazie. Nieco więcej miejsca w bazie, ALE:
         * - odporne na zmianę KOLEJNOŚCI stałych (nazwa się nie zmienia),
         * - czytelne WPROST w bazie danych (SELECT status FROM orders
         *   pokazuje "SHIPPED", a nie tajemnicze "2"),
         * - jedyne ryzyko: zmiana NAZWY stałej enum wymaga migracji
         *   danych (ale to rzadsza i bardziej świadoma operacja niż
         *   przypadkowa zmiana kolejności).
         *
         * 📌 W PRAKTYCE: prawie ZAWSZE wybieraj STRING. ORDINAL bywa
         * używany tylko tam, gdzie rozmiar danych jest krytyczny i
         * kolejność stałych jest 100% gwarantowana niezmienna.
         */
        System.out.println("\n=== EnumType.STRING (ZALECANE) ===");
        System.out.println("Zapisuje NAZWE stalej (\"SHIPPED\") - czytelne w bazie, odporne na zmiane kolejnosci.");
        System.out.println("W PRAKTYCE: prawie zawsze wybieraj STRING zamiast ORDINAL.");

        /*
         * ============================================================
         * 🔍 @Temporal - MAPOWANIE STARSZYCH TYPÓW java.util.Date
         * ============================================================
         * Przed Javą 8 (java.time) jedynym typem daty/czasu był
         * java.util.Date/Calendar - niejednoznaczny (mógł reprezentować
         * samą datę, sam czas, albo oba naraz). @Temporal(TemporalType.
         * DATE/TIME/TIMESTAMP) informowało Hibernate, JAK dokładnie
         * zmapować taki niejednoznaczny typ na kolumnę SQL.
         *
         * DZIŚ, z java.time (LocalDate/LocalDateTime/Instant), @Temporal
         * jest w OGROMNEJ większości przypadków NIEPOTRZEBNE - Hibernate
         * automatycznie i jednoznacznie mapuje te nowsze typy (LocalDate
         * -> DATE, LocalDateTime -> TIMESTAMP) bez żadnej dodatkowej
         * adnotacji. Wzmianka o @Temporal to głównie kontekst historyczny,
         * przydatny przy pracy ze STARSZYM kodem.
         */
        System.out.println("\n=== @Temporal (kontekst historyczny) ===");
        System.out.println("Potrzebne TYLKO dla java.util.Date/Calendar - z java.time (LocalDate...) NIEPOTRZEBNE.");

        /*
         * ============================================================
         * 🔹 @Converter + AttributeConverter<X,Y> - WŁASNA KONWERSJA
         * ============================================================
         * Czasem chcesz zmapować typ, którego Hibernate NIE ROZUMIE
         * wprost (np. List<String> jako JEDNA kolumna tekstowa CSV,
         * zamiast osobnej tabeli - patrz Lesson12/13 dla "prawdziwych"
         * kolekcji encji). AttributeConverter<X, Y> pozwala napisać
         * WŁASNĄ, dwukierunkową konwersję:
         * - convertToDatabaseColumn(X) -> Y  (Java -> SQL, przy zapisie)
         * - convertToEntityAttribute(Y) -> X (SQL -> Java, przy odczycie)
         *
         * @Converter(autoApply = true) sprawia, że Hibernate automatycznie
         * użyje tego konwertera dla WSZYSTKICH pól typu X w KAŻDEJ
         * encji - bez potrzeby jawnego @Convert na każdym polu.
         */
        System.out.println("\n=== AttributeConverter<X,Y> ===");
        System.out.println("Wlasna konwersja: convertToDatabaseColumn (Java->SQL) i convertToEntityAttribute (SQL->Java).");
        System.out.println("@Converter(autoApply=true) -> stosowany AUTOMATYCZNIE do wszystkich pol tego typu.");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD: Order ZE STATUSEM ENUM (STRING) I TAGAMI (CSV)
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Order order = new Order();
            order.setStatus(OrderStatus.SHIPPED);
            order.setTags(Arrays.asList("pilne", "vip", "wysylka-kurier"));
            session.persist(order);

            transaction.commit();
            session.close();

            Session readSession = sessionFactory.openSession();
            Order loaded = readSession.find(Order.class, order.getId());
            readSession.close();

            System.out.println("\n=== ODCZYT Order ===");
            System.out.println("Status: " + loaded.getStatus() + " (zapisany jako tekst 'SHIPPED' w bazie).");
            System.out.println("Tagi (z powrotem jako List): " + loaded.getTags());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - EnumType.ORDINAL - liczba, kompaktowe, ale KRUCHE przy
         *   zmianie kolejności stałych enum.
         * - EnumType.STRING - tekst, czytelniejsze i bezpieczniejsze -
         *   ZALECANY wybór w praktyce.
         * - @Temporal - potrzebne tylko dla starszego java.util.Date,
         *   niepotrzebne z java.time.
         * - AttributeConverter<X,Y> + @Converter(autoApply=true) -
         *   własna, dwukierunkowa konwersja typu (np. List jako CSV).
         */

        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson10enums;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Order.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public enum OrderStatus {
        PENDING, PAID, SHIPPED, DELIVERED, CANCELLED
    }

    @Converter(autoApply = true)
    public static class TagListConverter implements AttributeConverter<List<String>, String> {

        @Override
        public String convertToDatabaseColumn(List<String> attribute) {
            return attribute == null ? "" : String.join(",", attribute);
        }

        @Override
        public List<String> convertToEntityAttribute(String dbData) {
            return dbData == null || dbData.isBlank() ? List.of() : Arrays.asList(dbData.split(","));
        }
    }

    @Entity(name = "Order")
    @Table(name = "orders")
    public static class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private OrderStatus status;

        @Convert(converter = TagListConverter.class)
        private List<String> tags;

        public Long getId() {
            return id;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public void setStatus(OrderStatus status) {
            this.status = status;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
