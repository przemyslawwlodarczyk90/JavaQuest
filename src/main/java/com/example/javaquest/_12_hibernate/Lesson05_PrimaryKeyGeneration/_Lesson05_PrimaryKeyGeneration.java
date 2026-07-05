package com.example.javaquest._12_hibernate.Lesson05_PrimaryKeyGeneration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson05_PrimaryKeyGeneration {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: GENEROWANIE KLUCZY GŁÓWNYCH: @GeneratedValue ===");

        /*
         * ============================================================
         * 📦 GenerationType.IDENTITY - AUTO-INCREMENT BAZY DANYCH
         * ============================================================
         * Najprostsza strategia - klucz generuje SAMA BAZA (kolumna
         * "auto-increment"/"identity"), Hibernate po prostu odczytuje
         * wygenerowaną wartość PO wykonaniu INSERT.
         *
         * Wada: Hibernate MUSI wykonać INSERT natychmiast (nie może
         * odłożyć go na później), żeby poznać wygenerowane id - to
         * UNIEMOŻLIWIA tzw. batch inserts (grupowanie wielu wstawień w
         * jedno zapytanie sieciowe dla wydajności). Mimo to IDENTITY
         * jest bardzo popularne (proste, wspierane przez H2/MySQL/
         * PostgreSQL) - wybór domyślny, gdy nie masz szczególnych
         * wymagań wydajnościowych przy masowych insertach.
         */
        System.out.println("\n=== GenerationType.IDENTITY ===");
        System.out.println("Auto-increment bazy - proste, ale BEZ mozliwosci batch insertow (INSERT musi byc od razu).");

        /*
         * ============================================================
         * 🔹 GenerationType.SEQUENCE - SEKWENCJA BAZODANOWA
         * ============================================================
         * Sekwencja to OSOBNY obiekt bazodanowy generujący kolejne
         * liczby (niezależnie od tabeli) - H2/PostgreSQL/Oracle ją
         * wspierają (MySQL historycznie nie miał sekwencji - stąd
         * popularność IDENTITY w projektach MySQL-owych).
         *
         * Zaleta względem IDENTITY: Hibernate może POBRAĆ wartość z
         * sekwencji PRZED wykonaniem INSERT (a nawet pobrać całą PULĘ
         * wartości naraz - "allocationSize") - to WŁAŚNIE umożliwia
         * batch inserts (grupowanie wielu INSERT-ów w jedno zapytanie
         * sieciowe), co jest znaczącą przewagą wydajnościową przy
         * masowym zapisie.
         */
        System.out.println("\n=== GenerationType.SEQUENCE ===");
        System.out.println("Sekwencja bazodanowa - Hibernate zna id PRZED insertem -> mozliwe batch inserts.");

        /*
         * ============================================================
         * 🔍 GenerationType.TABLE - TABELA SYMULUJĄCA SEKWENCJĘ
         * ============================================================
         * Dla baz BEZ natywnych sekwencji (historycznie np. MySQL)
         * Hibernate może symulować sekwencję zwykłą TABELĄ (jeden wiersz
         * trzymający "następną wartość", aktualizowany przy każdym
         * pobraniu klucza). Działa WSZĘDZIE (maksymalna przenośność
         * między różnymi bazami), ale jest WOLNIEJSZE niż natywna
         * SEQUENCE - każde pobranie klucza to dodatkowa transakcja na
         * tej tabeli pomocniczej. W praktyce: używaj TABLE tylko, gdy
         * REALNIE potrzebujesz przenośności między bazami bez sekwencji
         * - w pozostałych przypadkach SEQUENCE/IDENTITY wystarczą.
         */
        System.out.println("\n=== GenerationType.TABLE ===");
        System.out.println("Tabela symulujaca sekwencje - dziala wszedzie, ale WOLNIEJSZE - rzadko wybierane bez powodu.");

        /*
         * ============================================================
         * 🔹 KLUCZE UUID - @UuidGenerator (Hibernate)
         * ============================================================
         * Zamiast liczb, kluczem może być UUID (128-bitowy identyfikator
         * losowy, praktycznie bez ryzyka kolizji). Przydatne w systemach
         * ROZPROSZONYCH (wiele niezależnych instancji generujących id
         * BEZ koordynacji z centralną bazą) - np. mikroserwisy,
         * synchronizacja offline.
         *
         * Wada: UUID jest znacznie WIĘKSZY niż liczba (16 bajtów vs 8),
         * co powiększa indeksy i może pogorszyć wydajność JOIN-ów przy
         * bardzo dużych tabelach - świadomy kompromis, nie "zawsze
         * lepszy wybór".
         */
        System.out.println("\n=== UUID jako klucz glowny (@UuidGenerator) ===");
        System.out.println("Losowy, bez kolizji, dobry dla systemow rozproszonych - ale wiekszy niz liczba (indeksy).");

        /*
         * ============================================================
         * 🔍 KLUCZ PRZYPISYWANY RĘCZNIE (BEZ @GeneratedValue)
         * ============================================================
         * Czasem klucz główny to NATURALNY identyfikator, który i tak
         * musisz podać sam - np. kod kraju ISO ("PL", "DE"), numer ISBN
         * książki. W takich przypadkach pomijamy @GeneratedValue
         * całkowicie i ustawiamy pole @Id RĘCZNIE przed session.persist().
         */
        System.out.println("\n=== KLUCZ RECZNY (bez @GeneratedValue) ===");
        System.out.println("Sensowne dla kluczy NATURALNYCH (np. kod kraju ISO, ISBN) - Ty podajesz wartosc.");

        /*
         * ============================================================
         * 🔹 PEŁNY, DZIAŁAJĄCY PRZYKŁAD WSZYSTKICH STRATEGII
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            IdentityKeyEntity identityEntity = new IdentityKeyEntity();
            identityEntity.setName("IDENTITY demo");
            session.persist(identityEntity);

            SequenceKeyEntity sequenceEntity = new SequenceKeyEntity();
            sequenceEntity.setName("SEQUENCE demo");
            session.persist(sequenceEntity);

            UuidKeyEntity uuidEntity = new UuidKeyEntity();
            uuidEntity.setName("UUID demo");
            session.persist(uuidEntity);

            CountryEntity poland = new CountryEntity();
            poland.setIsoCode("PL");
            poland.setName("Polska");
            session.persist(poland);

            transaction.commit();
            session.close();

            System.out.println("\n=== WYGENEROWANE KLUCZE ===");
            System.out.println("IDENTITY -> " + identityEntity.getId());
            System.out.println("SEQUENCE -> " + sequenceEntity.getId());
            System.out.println("UUID     -> " + uuidEntity.getId());
            System.out.println("Reczny (kod ISO) -> " + poland.getIsoCode());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - IDENTITY - auto-increment bazy, proste, ale bez batch inserts.
         * - SEQUENCE - sekwencja bazodanowa, umożliwia batchowanie
         *   (Hibernate zna klucz PRZED insertem).
         * - TABLE - maksymalnie przenośne, ale najwolniejsze - rzadki
         *   wybór bez konkretnego powodu.
         * - UUID (@UuidGenerator) - losowy, bez kolizji, dobry dla
         *   systemów rozproszonych, ale większy niż liczba.
         * - Klucz ręczny (bez @GeneratedValue) - dla identyfikatorów
         *   NATURALNYCH (kod ISO, ISBN), które i tak musisz podać.
         */

        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson05keys;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(IdentityKeyEntity.class);
        configuration.addAnnotatedClass(SequenceKeyEntity.class);
        configuration.addAnnotatedClass(UuidKeyEntity.class);
        configuration.addAnnotatedClass(CountryEntity.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Entity(name = "IdentityKeyEntity")
    @Table(name = "identity_key_entity")
    public static class IdentityKeyEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity(name = "SequenceKeyEntity")
    @Table(name = "sequence_key_entity")
    public static class SequenceKeyEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_key_seq")
        @SequenceGenerator(name = "sequence_key_seq", sequenceName = "sequence_key_seq", allocationSize = 1)
        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity(name = "UuidKeyEntity")
    @Table(name = "uuid_key_entity")
    public static class UuidKeyEntity {

        @Id
        @GeneratedValue
        @UuidGenerator
        private UUID id;

        private String name;

        public UUID getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity(name = "CountryEntity")
    @Table(name = "country_entity")
    public static class CountryEntity {

        @Id
        private String isoCode;

        private String name;

        public String getIsoCode() {
            return isoCode;
        }

        public void setIsoCode(String isoCode) {
            this.isoCode = isoCode;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
