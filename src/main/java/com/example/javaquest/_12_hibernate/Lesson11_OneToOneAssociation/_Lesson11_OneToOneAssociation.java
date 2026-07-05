package com.example.javaquest._12_hibernate.Lesson11_OneToOneAssociation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson11_OneToOneAssociation {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: RELACJA JEDEN-DO-JEDNEGO: @OneToOne ===");

        /*
         * ============================================================
         * 📦 UNIDIRECTIONAL vs BIDIRECTIONAL
         * ============================================================
         * UNIDIRECTIONAL (jednokierunkowa) - tylko JEDNA strona relacji
         * "zna" drugą (np. UserProfile ma pole "user", ale User NIE MA
         * pola "profile") - prostsze, wystarcza, gdy nawigujesz TYLKO w
         * jedną stronę w kodzie.
         *
         * BIDIRECTIONAL (dwukierunkowa) - OBIE strony znają się nawzajem
         * (User ma pole "profile" I UserProfile ma pole "user") -
         * wygodniejsze w użyciu (nawigacja w obie strony), ale wymaga
         * WIĘCEJ dyscypliny (Lesson12 pokaże metody pomocnicze
         * utrzymujące spójność obu stron w pamięci).
         *
         * W relacji dwukierunkowej JEDNA strona jest "posiadająca"
         * (owning side - to ONA ma kolumnę @JoinColumn w swojej tabeli),
         * a druga jest "odwrotna" (inverse side - używa mappedBy,
         * wskazując na pole we WŁAŚCICIELU relacji).
         */
        System.out.println("\n=== UNIDIRECTIONAL vs BIDIRECTIONAL ===");
        System.out.println("Unidirectional: tylko JEDNA strona zna druga.");
        System.out.println("Bidirectional: OBIE strony sie znaja - jedna 'posiadajaca' (@JoinColumn), druga 'odwrotna' (mappedBy).");

        /*
         * ============================================================
         * 🔹 WSPÓŁDZIELONY KLUCZ (@MapsId) vs OSOBNA KOLUMNA FK
         * ============================================================
         * Są DWA sposoby zamodelowania @OneToOne w SQL:
         *
         * 1. Osobna kolumna klucza obcego (@JoinColumn) - tabela
         *    "user_profile" ma WŁASNY klucz główny ORAZ dodatkową
         *    kolumnę "user_id" (FK, z ograniczeniem UNIQUE, żeby
         *    wymusić "jeden-do-jednego" a nie "jeden-do-wielu").
         *
         * 2. Współdzielony klucz główny (@MapsId) - tabela
         *    "user_profile" W OGÓLE nie ma własnego, niezależnego
         *    klucza - jej klucz główny to JEDNOCZEŚNIE klucz obcy do
         *    "user" (profile.id == user.id). Eleganckie i gwarantuje
         *    "jeden-do-jednego" na poziomie STRUKTURY (bez potrzeby
         *    dodatkowego UNIQUE) - ale nieco trudniejsze koncepcyjnie.
         *
         * W tej lekcji używamy prostszego, częściej spotykanego
         * podejścia: osobna kolumna @JoinColumn z UNIQUE.
         */
        System.out.println("\n=== @JoinColumn (osobna kolumna FK) vs @MapsId (wspoldzielony klucz) ===");
        System.out.println("@JoinColumn - prostsze, wlasny PK + kolumna FK z UNIQUE.");
        System.out.println("@MapsId     - PK dziecka JEST jednoczesnie FK do rodzica - elegantsze, ale trudniejsze.");

        /*
         * ============================================================
         * 🔍 LAZY LOADING DLA @OneToOne - PUŁAPKA
         * ============================================================
         * Domyślnie @OneToOne jest EAGER (Lesson15 wyjaśni fetch types
         * szczegółowo) - powiązany obiekt ładuje się OD RAZU razem z
         * rodzicem. Ustawienie fetch = FetchType.LAZY na stronie BEZ
         * @JoinColumn (stronie "odwrotnej") w praktyce CZĘSTO i tak
         * ładuje się EAGERLY, bo Hibernate bez dodatkowej "bajtkodowej
         * instrumentacji" (bytecode enhancement) NIE WIE z góry, czy
         * powiązany wiersz w ogóle ISTNIEJE (a musiałby zwrócić null
         * bez zapytania) - to jedna z mniej intuicyjnych pułapek
         * @OneToOne, warta świadomości, nawet jeśli w tym kursie nie
         * konfigurujemy bytecode enhancement.
         */
        System.out.println("\n=== PULAPKA: LAZY dla @OneToOne ===");
        System.out.println("LAZY na stronie BEZ @JoinColumn czesto i tak laduje sie EAGERLY (bez bytecode enhancement).");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD: User I UserProfile
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            User user = new User();
            user.setUsername("jkowalski");
            session.persist(user);

            UserProfile profile = new UserProfile();
            profile.setBio("Programista Java, entuzjasta ORM.");
            profile.setUser(user);
            session.persist(profile);

            transaction.commit();
            session.close();

            Session readSession = sessionFactory.openSession();
            UserProfile loadedProfile = readSession.find(UserProfile.class, profile.getId());
            readSession.close();

            System.out.println("\n=== ODCZYT UserProfile -> User (strona posiadajaca @JoinColumn) ===");
            System.out.println("Profil uzytkownika '" + loadedProfile.getUser().getUsername() + "': " + loadedProfile.getBio());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Unidirectional - jedna strona zna drugą; bidirectional -
         *   obie strony (właściciel z @JoinColumn + odwrotna mappedBy).
         * - @JoinColumn (osobna kolumna FK z UNIQUE) vs @MapsId
         *   (współdzielony klucz główny) - dwa sposoby modelowania.
         * - LAZY dla @OneToOne bywa zawodne bez bytecode enhancement -
         *   w praktyce często ładuje się EAGERLY mimo deklaracji LAZY.
         */

        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson11onetoone;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(UserProfile.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Entity(name = "User")
    @Table(name = "app_user")
    public static class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String username;

        public Long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    @Entity(name = "UserProfile")
    @Table(name = "user_profile")
    public static class UserProfile {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String bio;

        // Strona POSIADAJACA relacje - ma kolumne FK (unique = "jeden-do-jednego").
        @OneToOne
        @JoinColumn(name = "user_id", unique = true)
        private User user;

        public Long getId() {
            return id;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
