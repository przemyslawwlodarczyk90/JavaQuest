package com.example.javaquest._12_hibernate.Lesson17_DirtyCheckingAndFlush;

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

public class _Lesson17_DirtyCheckingAndFlush {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: DIRTY CHECKING I FLUSH: automatyczne wykrywanie zmian ===");

        /*
         * ============================================================
         * 📦 AUTOMATIC DIRTY CHECKING
         * ============================================================
         * Gdy Hibernate ładuje encję (find/HQL), robi w pamięci
         * WEWNĘTRZNY "snapshot" jej pierwotnego stanu (wartości
         * WSZYSTKICH pól w momencie załadowania). Przy każdym flush
         * (patrz niżej) Hibernate PORÓWNUJE aktualny stan obiektu z tym
         * snapshotem - dla KAŻDEGO zarządzanego obiektu w Session.
         *
         * Jeśli wykryje różnicę - SAM generuje UPDATE, TYLKO dla pól,
         * które FAKTYCZNIE się zmieniły (nie dla wszystkich kolumn). Ty
         * NIE wołasz żadnego "save"/"update" po modyfikacji - wystarczy
         * zwykły setter na zarządzanym obiekcie.
         */
        System.out.println("\n=== AUTOMATIC DIRTY CHECKING ===");
        System.out.println("Hibernate porownuje aktualny stan obiektu ze SNAPSHOTEM z momentu zaladowania.");
        System.out.println("Roznica -> automatyczny UPDATE, TYLKO dla zmienionych pol - bez wywolywania update() recznie!");

        /*
         * ============================================================
         * 🔹 flush() - MOMENT WYSŁANIA SQL (NIEKONIECZNIE COMMIT!)
         * ============================================================
         * flush() to moment, w którym Hibernate FAKTYCZNIE wysyła
         * zgromadzone operacje (INSERT/UPDATE/DELETE) do bazy jako SQL.
         * To NIE JEST to samo co commit() transakcji! flush() może
         * wystąpić WIELOKROTNIE w obrębie JEDNEJ transakcji (np.
         * automatycznie PRZED wykonaniem zapytania HQL, żeby zapytanie
         * "widziało" wcześniejsze, jeszcze niewysłane zmiany) - a
         * dopiero commit() FAKTYCZNIE zatwierdza transakcję na poziomie
         * bazy danych (co pozwala też na ewentualny rollback PRZED
         * commitem, nawet po flush()).
         */
        System.out.println("\n=== flush() vs commit() ===");
        System.out.println("flush() - wysyla SQL do bazy (moze wystapic WIELOKROTNIE w jednej transakcji).");
        System.out.println("commit() - FAKTYCZNIE zatwierdza transakcje na poziomie bazy (flush + commit bazodanowy).");

        /*
         * ============================================================
         * 🔍 FlushMode: AUTO vs COMMIT vs MANUAL
         * ============================================================
         * - AUTO (domyślny) - Hibernate robi flush automatycznie PRZED
         *   wykonaniem zapytania (HQL/Criteria), jeśli wykryje, że
         *   zapytanie MOGŁOBY dotyczyć jeszcze niewysłanych zmian -
         *   oraz zawsze PRZED commit().
         * - COMMIT - flush TYLKO przy jawnym commit() transakcji -
         *   zapytania HQL w międzyczasie mogą NIE widzieć jeszcze
         *   niewysłanych zmian (potencjalna pułapka).
         * - MANUAL - flush TYLKO gdy jawnie wywołasz session.flush() -
         *   pełna kontrola, ale pełna odpowiedzialność programisty.
         */
        printFlushModeTable();

        /*
         * ============================================================
         * 🔹 KOLEJNOŚĆ OPERACJI W BUFORZE SESSION (ACTION QUEUE)
         * ============================================================
         * Session NIE wysyła SQL natychmiast przy każdym persist()/
         * setterze - GROMADZI operacje w wewnętrznej kolejce ("action
         * queue"), a dopiero PRZY FLUSH wykonuje je w OKREŚLONEJ
         * kolejności wg TYPU operacji (typowo: najpierw wszystkie
         * INSERT-y, potem wszystkie UPDATE-y, na końcu wszystkie
         * DELETE-y) - NIEKONIECZNIE w kolejności, w jakiej wywołałeś je
         * w kodzie Javy! To ważne przy debugowaniu "dlaczego SQL
         * wykonał się w innej kolejności niż mój kod".
         */
        System.out.println("\n=== KOLEJNOSC W ACTION QUEUE ===");
        System.out.println("Flush wykonuje operacje wg TYPU (najpierw INSERT-y, potem UPDATE-y, na koncu DELETE-y),");
        System.out.println("NIEKONIECZNIE w kolejnosci wywolan w kodzie Javy!");

        /*
         * ============================================================
         * 🔹 DEMONSTRACJA: UPDATE TYLKO DLA JEDNEGO ZMIENIONEGO POLA
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session seedSession = sessionFactory.openSession();
            Transaction seedTx = seedSession.beginTransaction();
            Product product = new Product();
            product.setName("Mysz bezprzewodowa");
            product.setPrice(89.99);
            product.setStock(50);
            seedSession.persist(product);
            seedTx.commit();
            seedSession.close();

            System.out.println("\n=== DIRTY CHECKING NA ZYWO (patrz na SQL UPDATE ponizej - tylko kolumna 'price') ===");
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Product managed = session.find(Product.class, product.getId());
            managed.setPrice(79.99); // ZMIENIAMY TYLKO cene - name i stock zostaja bez zmian

            // BRAK jawnego session.update()! Dirty checking wykryje zmiane SAM przy flush/commit.
            transaction.commit();
            session.close();

            System.out.println("Cena po aktualizacji: " + managed.getPrice() + " (UPDATE dotyczyl TYLKO kolumny price).");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Automatic dirty checking - Hibernate porównuje stan
         *   obiektu ze snapshotem, generuje UPDATE tylko dla zmienionych
         *   pól, BEZ jawnego wywołania update().
         * - flush() wysyła SQL, commit() zatwierdza transakcję na
         *   poziomie bazy - to DWIE różne rzeczy.
         * - FlushMode: AUTO (domyślny, bezpieczny), COMMIT (flush tylko
         *   przy commit), MANUAL (pełna kontrola ręczna).
         * - Operacje w Session wykonują się przy flush wg TYPU (INSERT/
         *   UPDATE/DELETE), niekoniecznie w kolejności kodu Javy.
         */

        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson17flush;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Product.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printFlushModeTable() {
        System.out.println("\n=== FlushMode ===");
        String format = "%-10s | %-50s%n";
        System.out.printf(format, "Tryb", "Kiedy nastepuje flush");
        System.out.println("-".repeat(65));
        System.out.printf(format, "AUTO", "przed zapytaniem (jesli moze dotyczyc zmian) + przed commit (domyslny)");
        System.out.printf(format, "COMMIT", "tylko przy jawnym commit() transakcji");
        System.out.printf(format, "MANUAL", "tylko przy jawnym session.flush() - pelna kontrola");
    }

    @Entity(name = "Product")
    @Table(name = "product")
    public static class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private double price;
        private int stock;

        public Long getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
