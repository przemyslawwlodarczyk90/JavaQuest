package com.example.javaquest._12_hibernate.Lesson03_ProjectSetupAndConfiguration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson03_ProjectSetupAndConfiguration {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: KONFIGURACJA PROJEKTU: zależności, hibernate.cfg.xml vs persistence.xml ===");

        /*
         * ============================================================
         * 📦 ZALEŻNOŚCI MAVEN
         * ============================================================
         * Ten rozdział wymaga w pom.xml (już dodane do tego projektu):
         * - org.hibernate.orm:hibernate-core - silnik ORM, dociąga
         *   transytywnie jakarta.persistence-api (adnotacje @Entity itp.).
         * - com.h2database:h2 - baza in-memory (już obecna od _08_sql).
         * - com.zaxxer:HikariCP - opcjonalna, wydajniejsza pula połączeń
         *   (już obecna od _10_dao) - Lesson-y w tym rozdziale używają
         *   prostszej, wbudowanej puli Hibernate dla czytelności, ale w
         *   REALNYM projekcie warto skonfigurować Hibernate z HikariCP.
         * - org.hibernate.orm:hibernate-envers, hibernate-jcache,
         *   org.ehcache:ehcache - potrzebne dopiero w Lesson24/Lesson29.
         */
        System.out.println("\n=== ZALEZNOSCI MAVEN (juz w pom.xml tego kursu) ===");
        System.out.println("hibernate-core (+ jakarta.persistence-api transytywnie), h2, HikariCP,");
        System.out.println("hibernate-envers, hibernate-jcache, ehcache (do pozniejszych lekcji).");

        /*
         * ============================================================
         * 🔹 DWA STYLE KONFIGURACJI: hibernate.cfg.xml vs persistence.xml
         * ============================================================
         * Hibernate historycznie oferuje DWA równoległe sposoby
         * konfiguracji:
         *
         * 1. hibernate.cfg.xml - NATYWNY, Hibernate-specific plik XML
         *    (zwykle w src/main/resources), wczytywany przez
         *    new Configuration().configure(). Daje dostęp do WSZYSTKICH
         *    właściwości Hibernate, ale wiąże Cię z Hibernate (mniej
         *    przenośne na inną implementację JPA).
         *
         * 2. META-INF/persistence.xml - STANDARD JPA, wczytywany przez
         *    Persistence.createEntityManagerFactory("nazwa-jednostki").
         *    Bardziej przenośny (ten sam plik zadziała z inną
         *    implementacją JPA), ale odrobinę bardziej "sztywny" format.
         *
         * W TYM KURSIE świadomie używamy TRZECIEGO podejścia (patrz
         * niżej) - programowej konfiguracji przez Configuration#setProperty
         * - żeby uniknąć dodatkowych plików XML w każdej lekcji i mieć
         * WSZYSTKO widoczne w jednym miejscu, w main().
         */
        printConfigStylesTable();

        /*
         * ============================================================
         * 🔍 KONFIGURACJA PROGRAMOWA (BEZ XML) - WYBÓR TEGO KURSU
         * ============================================================
         * Configuration#setProperty(klucz, wartość) pozwala ustawić
         * DOKŁADNIE te same właściwości, które normalnie trafiłyby do
         * hibernate.cfg.xml - ale wprost w kodzie Javy. Zaleta dla
         * nauki: cała konfiguracja lekcji widoczna w jednym main(), bez
         * przełączania się między plikami.
         *
         * W REALNYM projekcie (zwłaszcza ze Spring Boot - przyszły,
         * ostatni rozdział kursu) konfiguracja zwykle idzie przez
         * application.properties/application.yml, a Spring Boot SAM
         * buduje SessionFactory/EntityManagerFactory "pod spodem".
         */
        System.out.println("\n=== KONFIGURACJA PROGRAMOWA (Configuration#setProperty) ===");
        System.out.println("Wybor tego kursu - cala konfiguracja widoczna wprost w main(), bez plikow XML.");

        /*
         * ============================================================
         * 🔹 KLUCZOWE WŁAŚCIWOŚCI KONFIGURACYJNE
         * ============================================================
         */
        printKeyPropertiesTable();

        /*
         * ============================================================
         * 🔍 hibernate.hbm2ddl.auto - AUTOMATYCZNE ZARZĄDZANIE SCHEMATEM
         * ============================================================
         * Ta właściwość mówi Hibernate, co zrobić ze SCHEMATEM bazy przy
         * starcie SessionFactory - w tym kursie używamy jej zamiast
         * ręcznych CREATE TABLE (jak w _08_sql) czy migracji Flyway
         * (jak w _10_dao/Lesson25), bo Hibernate generuje schemat
         * WPROST z adnotacji @Entity.
         *
         * Wartości: "none" (nic nie rób - zakładamy istniejący schemat,
         * jak w REALNEJ produkcji z Flyway/Liquibase), "validate"
         * (sprawdź zgodność, nie zmieniaj), "update" (dodaj brakujące
         * tabele/kolumny, NIGDY nie usuwaj - ryzykowne przy złożonych
         * zmianach), "create" (usuń i stwórz od nowa przy starcie),
         * "create-drop" (jak create, ale DODATKOWO usuwa tabele przy
         * zamknięciu SessionFactory - używane w TYM kursie, bo każda
         * lekcja ma świeżą, izolowaną bazę in-memory).
         *
         * ⚠️ W REALNYM projekcie produkcyjnym "create"/"create-drop"/
         * "update" są ODRADZANE - schemat powinien być zarządzany przez
         * narzędzie migracji (Flyway/Liquibase, patrz _10_dao/Lesson25),
         * a Hibernate powinien dostać "validate" albo "none".
         */
        System.out.println("\n=== hibernate.hbm2ddl.auto ===");
        System.out.println("none/validate/update/create/create-drop - w tym kursie: create-drop (swieza baza za kazdym razem).");
        System.out.println("UWAGA: na produkcji zwykle 'validate'/'none' + Flyway/Liquibase do migracji (patrz _10_dao/Lesson25)!");

        /*
         * ============================================================
         * 🔹 PEŁNY, DZIAŁAJĄCY PRZYKŁAD KONFIGURACJI NA H2
         * ============================================================
         * Poniższy wzorzec (metoda buildSessionFactory) będzie
         * powtarzany - z drobnymi wariacjami - w KAŻDEJ kolejnej lekcji
         * tego rozdziału. URL "jdbc:h2:mem:...;DB_CLOSE_DELAY=-1" to ten
         * sam wzorzec, co w _08_sql/Lesson01 - unikalna nazwa bazy per
         * lekcja, żeby demonstracje się nie mieszały.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Setting setting = new Setting();
            setting.setKey("theme");
            setting.setValue("dark");
            session.persist(setting);

            transaction.commit();
            session.close();

            System.out.println("\n=== KONFIGURACJA DZIALA - zapisano Setting id=" + setting.getId() + " ===");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zależności: hibernate-core (+ jakarta.persistence-api
         *   transytywnie), h2, opcjonalnie HikariCP.
         * - Dwa "klasyczne" style konfiguracji: hibernate.cfg.xml
         *   (natywny) vs META-INF/persistence.xml (standard JPA) - w
         *   tym kursie używamy TRZECIEGO stylu: Configuration#setProperty
         *   programowo, dla przejrzystości w main().
         * - Kluczowe właściwości: connection.url/driver_class,
         *   hbm2ddl.auto, show_sql, format_sql.
         * - hbm2ddl.auto=create-drop - wygodne do nauki/testów, ale NIE
         *   na produkcji (tam: validate/none + Flyway/Liquibase).
         */

        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson03setup;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.addAnnotatedClass(Setting.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printConfigStylesTable() {
        System.out.println("\n=== STYLE KONFIGURACJI HIBERNATE ===");
        String format = "%-22s | %-45s%n";
        System.out.printf(format, "Styl", "Charakterystyka");
        System.out.println("-".repeat(75));
        System.out.printf(format, "hibernate.cfg.xml", "Natywny XML Hibernate, pelny dostep do wlasciwosci");
        System.out.printf(format, "META-INF/persistence.xml", "Standard JPA, przenosny miedzy implementacjami");
        System.out.printf(format, "Configuration#setProperty", "Programowo w kodzie - wybor tego kursu");
    }

    private static void printKeyPropertiesTable() {
        System.out.println("\n=== KLUCZOWE WLASCIWOSCI KONFIGURACYJNE ===");
        String format = "%-38s | %-35s%n";
        System.out.printf(format, "Wlasciwosc", "Znaczenie");
        System.out.println("-".repeat(78));
        System.out.printf(format, "hibernate.connection.url", "adres JDBC bazy danych");
        System.out.printf(format, "hibernate.connection.driver_class", "klasa sterownika JDBC");
        System.out.printf(format, "hibernate.hbm2ddl.auto", "zarzadzanie schematem (patrz nizej)");
        System.out.printf(format, "hibernate.show_sql", "wypisuje generowany SQL na konsole");
        System.out.printf(format, "hibernate.format_sql", "formatuje wypisywany SQL czytelnie");
    }

    @Entity(name = "Setting")
    @Table(name = "setting")
    public static class Setting {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String key;
        private String value;

        public Long getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
