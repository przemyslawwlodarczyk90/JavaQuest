package com.example.javaquest._12_hibernate.Lesson01_OrmIntroduction;

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

public class _Lesson01_OrmIntroduction {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: CZYM JEST ORM I PO CO NAM HIBERNATE ===");

        /*
         * ============================================================
         * 📦 PROBLEM NIEDOPASOWANIA OBIEKTOWO-RELACYJNEGO
         * ============================================================
         * "Impedance mismatch" (niedopasowanie impedancji) to nazwa
         * fundamentalnego napięcia między dwoma światami:
         *
         * - Świat OBIEKTOWY (Java): klasy, dziedziczenie, referencje
         *   między obiektami, kolekcje (List/Set/Map), grafy powiązań.
         * - Świat RELACYJNY (SQL, rozdział _08_sql): płaskie TABELE
         *   (wiersze x kolumny), powiązania przez klucze obce, brak
         *   dziedziczenia, brak "referencji" w sensie obiektowym.
         *
         * Przykład: klasa Book ma pole "Author author" (referencja do
         * obiektu). W SQL nie ma czegoś takiego - jest kolumna
         * "author_id" w tabeli "book", wskazująca wiersz w tabeli
         * "author". Żeby z tej kolumny odtworzyć PRAWDZIWY obiekt Author
         * w Javie, ktoś musi wykonać JOIN/dodatkowe zapytanie i RĘCZNIE
         * złożyć obiekty z powrotem - to właśnie robiliście ręcznie w
         * rozdziałach _09_jdbc i _10_dao.
         */
        System.out.println("\n=== IMPEDANCE MISMATCH ===");
        System.out.println("Java: obiekty, referencje, dziedziczenie, kolekcje.");
        System.out.println("SQL:  plaskie tabele, klucze obce, brak dziedziczenia.");
        System.out.println("ORM ma za zadanie automatycznie tlumaczyc miedzy tymi dwoma swiatami.");

        /*
         * ============================================================
         * 🔹 RĘCZNY JDBC/DAO vs AUTOMATYCZNY ORM
         * ============================================================
         * W _09_jdbc i _10_dao pisaliście RĘCZNIE:
         * - SQL do tworzenia/odczytu/aktualizacji/usuwania wierszy,
         * - kod mapujący ResultSet -> obiekt Java (rs.getInt/getString...),
         * - kod mapujący obiekt Java -> parametry PreparedStatement,
         * - ręczne JOIN-y i składanie obiektów z wielu tabel (np.
         *   _10_dao/Lesson08_OneToManyDao - Order + lista OrderItem).
         *
         * ORM (Object-Relational Mapping) AUTOMATYZUJE dokładnie to -
         * na podstawie ADNOTACJI na klasie (np. @Entity, @OneToMany)
         * ORM sam generuje SQL, sam mapuje ResultSet na obiekty, sam
         * składa powiązane obiekty w grafy. Ty piszesz kod obiektowy
         * (obj.setName(...), author.getBooks()), a ORM "pod spodem"
         * tłumaczy to na SQL.
         */
        printManualVsOrmTable();

        /*
         * ============================================================
         * 🔍 HIBERNATE JAKO IMPLEMENTACJA JPA
         * ============================================================
         * JPA (Jakarta Persistence API, dawniej Java Persistence API) to
         * STANDARD (zbiór interfejsów i adnotacji: @Entity, @Id,
         * EntityManager...) - sam w sobie nie robi NIC, to tylko
         * "umowa" (kontrakt).
         *
         * Hibernate to KONKRETNA, najpopularniejsza IMPLEMENTACJA tego
         * standardu (obok innych, np. EclipseLink). Historycznie
         * Hibernate powstał PRZED JPA i "zainspirował" jego twórców -
         * dlatego Hibernate ma też WŁASNE, natywne API (Session,
         * SessionFactory), bogatsze niż sam standard JPA (Lesson07
         * pokaże obie strony).
         *
         * Analogia: JPA to jak interfejs List w Javie, a Hibernate to
         * jak konkretna implementacja ArrayList - używasz interfejsu
         * (przenośność), ale "pod spodem" działa konkretna klasa.
         */
        System.out.println("\n=== JPA (STANDARD) vs HIBERNATE (IMPLEMENTACJA) ===");
        System.out.println("JPA       = zbior interfejsow/adnotacji (@Entity, EntityManager) - sam kontrakt.");
        System.out.println("Hibernate = konkretna implementacja JPA + WLASNE, bogatsze API (Session/SessionFactory).");
        System.out.println("Analogia: JPA ~ interfejs List, Hibernate ~ konkretna implementacja ArrayList.");

        /*
         * ============================================================
         * 🔹 ALTERNATYWY DLA HIBERNATE
         * ============================================================
         * Hibernate to NIE jedyny sposób pracy z bazą danych z poziomu
         * Javy. Krótki przegląd konkurencyjnych podejść:
         *
         * - MyBatis - "SQL mapper": TY piszesz SQL ręcznie (w plikach
         *   XML albo adnotacjach), a MyBatis automatyzuje TYLKO
         *   mapowanie wyników na obiekty. Mniej "magii" niż Hibernate,
         *   pełna kontrola nad SQL.
         * - jOOQ - typowane zapytania SQL budowane W KODZIE Javy
         *   (generowane klasy odpowiadające tabelom) - kompilator
         *   wyłapuje błędy w nazwach kolumn/tabel.
         * - Spring Data JPA - NIE jest alternatywą dla Hibernate, tylko
         *   WARSTWĄ NAD JPA/Hibernate (automatyczne generowanie
         *   implementacji repozytoriów z samych nazw metod) - poza
         *   zakresem tego rozdziału, temat na przyszły, ostatni rozdział
         *   kursu o Spring Boot.
         */
        printAlternativesTable();

        /*
         * ============================================================
         * 🔍 KIEDY ORM POMAGA, A KIEDY PRZESZKADZA
         * ============================================================
         * ORM świetnie sprawdza się w typowych operacjach CRUD na
         * grafach obiektów domenowych - to większość kodu w typowej
         * aplikacji biznesowej.
         *
         * ORM może PRZESZKADZAĆ w:
         * - bardzo złożonych raportach analitycznych (wielopoziomowe
         *   agregacje, window functions) - często prościej i wydajniej
         *   napisać czyste SQL (Lesson21_NativeSqlQueries pokaże, że
         *   Hibernate na to pozwala),
         * - masowych operacjach wsadowych (miliony wierszy) - ładowanie
         *   ich jako pełnoprawnych obiektów Java jest kosztowne
         *   pamięciowo; czasem lepszy jest bulk update/delete (Lesson19)
         *   albo wręcz czyste SQL/JDBC.
         *
         * 📌 Wniosek: Hibernate to POTĘŻNE narzędzie do 80-90% typowego
         * kodu CRUD, ale świadomy programista wie, KIEDY zejść "pod"
         * ORM po czyste SQL (Lesson21) - a nie walczyć z narzędziem.
         */
        System.out.println("\n=== KIEDY ORM POMAGA, A KIEDY PRZESZKADZA ===");
        System.out.println("Pomaga: typowy CRUD na obiektach domenowych (wiekszosc kodu aplikacji biznesowej).");
        System.out.println("Przeszkadza: bardzo zlozone raporty, masowe operacje wsadowe (miliony wierszy).");

        /*
         * ============================================================
         * 🔹 PIERWSZY, MINIMALNY, DZIAŁAJĄCY PRZYKŁAD
         * ============================================================
         * Poniżej najprostszy możliwy przykład Hibernate "w akcji" -
         * pełne szczegóły (co to SessionFactory, co znaczy każda
         * właściwość konfiguracji) poznamy w Lesson02/Lesson03. Tu
         * chodzi tylko o to, żeby zobaczyć EFEKT: klasa Java z adnotacją
         * @Entity automatycznie zamienia się w tabelę SQL, a metoda
         * session.persist(...) sama generuje INSERT.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Note note = new Note();
            note.setContent("Pierwsza notatka zapisana przez Hibernate!");
            session.persist(note);

            transaction.commit();
            session.close();

            System.out.println("\n=== PIERWSZY ZAPIS PRZEZ HIBERNATE (tabela wygenerowana z adnotacji @Entity) ===");
            System.out.println("Zapisano encje Note o id=" + note.getId() + " - BEZ jednej linii recznego SQL INSERT.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Impedance mismatch - napięcie między światem obiektowym
         *   (Java) a relacyjnym (SQL) - ORM automatyzuje tłumaczenie.
         * - Ręczny JDBC/DAO (_09_jdbc/_10_dao) wymaga ręcznego SQL i
         *   ręcznego mapowania - ORM generuje to automatycznie na
         *   podstawie adnotacji.
         * - JPA to standard (interfejsy/adnotacje), Hibernate to jego
         *   najpopularniejsza implementacja + własne, bogatsze API.
         * - Alternatywy: MyBatis (SQL mapper, mniej magii), jOOQ
         *   (typowane SQL w Javie), Spring Data JPA (warstwa NAD JPA).
         * - ORM świetny do typowego CRUD, słabszy do bardzo złożonych
         *   raportów i masowych operacji wsadowych - wtedy sięgamy po
         *   native SQL (Lesson21) albo czysty JDBC.
         */

        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson01hibernate;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Note.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printManualVsOrmTable() {
        System.out.println("\n=== RECZNY JDBC/DAO vs AUTOMATYCZNY ORM ===");
        String format = "%-30s | %-35s%n";
        System.out.printf(format, "Reczny JDBC/DAO (_09/_10)", "ORM (Hibernate, ten rozdzial)");
        System.out.println("-".repeat(70));
        System.out.printf(format, "Piszesz SQL recznie", "SQL generowany automatycznie");
        System.out.printf(format, "rs.getInt/getString...", "Mapowanie z adnotacji @Column");
        System.out.printf(format, "Reczne JOIN-y + skladanie", "@OneToMany/@ManyToOne automatycznie");
        System.out.printf(format, "Pelna kontrola nad SQL", "Mniej kontroli, wiecej wygody");
    }

    private static void printAlternativesTable() {
        System.out.println("\n=== ALTERNATYWY DLA HIBERNATE ===");
        String format = "%-20s | %-45s%n";
        System.out.printf(format, "Narzedzie", "Podejscie");
        System.out.println("-".repeat(70));
        System.out.printf(format, "MyBatis", "SQL mapper - Ty piszesz SQL, ono mapuje wynik na obiekty");
        System.out.printf(format, "jOOQ", "Typowane zapytania SQL budowane w kodzie Javy");
        System.out.printf(format, "Spring Data JPA", "Warstwa NAD JPA/Hibernate, nie alternatywa");
    }

    @Entity(name = "Note")
    @Table(name = "note")
    public static class Note {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String content;

        public Long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
