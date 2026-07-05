package com.example.javaquest._12_hibernate.Lesson26_PessimisticLocking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson26_PessimisticLocking {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 26: BLOKOWANIE PESYMISTYCZNE: LockModeType ===");

        /*
         * ============================================================
         * 📦 ZAŁOŻENIE PESYMISTYCZNE
         * ============================================================
         * W przeciwieństwie do blokowania optymistycznego (Lesson25,
         * zakładającego RZADKIE konflikty), blokowanie pesymistyczne
         * zakłada, że konflikty są CZĘSTE albo bardzo KOSZTOWNE (np.
         * krytyczne operacje finansowe) - więc BLOKUJEMY wiersz JUŻ PRZY
         * ODCZYCIE, na CAŁY CZAS trwania transakcji, żeby ŻADNA inna
         * transakcja nie mogła go w międzyczasie zmienić.
         */
        System.out.println("\n=== ZALOZENIE PESYMISTYCZNE ===");
        System.out.println("Konflikty CZESTE/kosztowne - blokujemy wiersz JUZ przy odczycie, na CALY czas transakcji.");

        /*
         * ============================================================
         * 🔹 LockModeType.PESSIMISTIC_READ vs PESSIMISTIC_WRITE
         * ============================================================
         * - PESSIMISTIC_READ - odpowiednik SQL "SELECT ... FOR SHARE" -
         *   blokuje przed MODYFIKACJĄ przez inne transakcje, ale INNE
         *   transakcje wciąż mogą go ODCZYTAĆ (też przez PESSIMISTIC_READ).
         * - PESSIMISTIC_WRITE - odpowiednik SQL "SELECT ... FOR UPDATE" -
         *   blokuje wiersz WYŁĄCZNIE dla siebie - żadna inna transakcja
         *   nie może go ani zmienić, ani nawet odczytać z blokadą, dopóki
         *   pierwsza transakcja się nie zakończy (commit/rollback).
         */
        printLockModesTable();

        /*
         * ============================================================
         * 🔍 JAK ZASTOSOWAĆ BLOKADĘ
         * ============================================================
         * - session.find(Klasa, id, LockModeType.PESSIMISTIC_WRITE) -
         *   odczyt Z JEDNOCZESNYM zablokowaniem wiersza.
         * - query.setLockMode(LockModeType...) - blokada na wyniku
         *   zapytania HQL/JPQL.
         * - Opcjonalny hint "jakarta.persistence.lock.timeout" (w
         *   milisekundach) - jak DŁUGO czekać na zwolnienie blokady,
         *   zanim rzucony zostanie LockTimeoutException (zamiast
         *   czekać w NIESKOŃCZONOŚĆ).
         */
        System.out.println("\n=== JAK ZASTOSOWAC BLOKADE ===");
        System.out.println("session.find(Klasa, id, LockModeType.PESSIMISTIC_WRITE) + opcjonalny hint 'lock.timeout' (ms).");

        /*
         * ============================================================
         * 🔹 DEMO: BLOKADA MIĘDZY DWOMA WĄTKAMI (Z BEZPIECZNYM TIMEOUTEM)
         * ============================================================
         * Zgodnie z zasadą bezpieczeństwa demo z rozdziału
         * _05_multithreading tego kursu: main() musi zakończyć się
         * samoistnie w kilka sekund - żadnych realnych zawieszeń. Poniżej
         * DWA prawdziwe wątki (nie symulacja) - wątek A trzyma blokadę
         * (i niezacommitowany UPDATE) przez 800ms, wątek B próbuje
         * zdobyć TĘ SAMĄ blokadę z limitem czasu 300ms.
         *
         * ⚠️ WAŻNA UWAGA O H2: uruchomienie tego przykładu pokazuje, że
         * H2 NAPRAWDĘ blokuje wątek B (czeka ok. 800ms, dokładnie tyle,
         * ile wątek A trzyma blokadę) - pesymistyczne blokowanie
         * FAKTYCZNIE działa. ALE H2 NIE HONORUJE w pełni hinta
         * "jakarta.persistence.lock.timeout" (300ms) - zamiast rzucić
         * LockTimeoutException po 300ms, H2 czeka swoim WŁASNYM,
         * dłuższym, wewnętrznym limitem (domyślnie ok. 1-2 sekund) i
         * dopiero wtedy zwraca sukces (gdy blokada zdąży się zwolnić) -
         * albo wyjątek, gdyby blokada trwała dłużej niż ten wewnętrzny
         * limit. To WAŻNA, PRAKTYCZNA lekcja: obsługa hintów JPA (w tym
         * lock.timeout) bywa NIEJEDNOLITA między dostawcami baz danych -
         * PostgreSQL/MySQL honorują ten hint znacznie precyzyjniej niż
         * H2. W REALNYM projekcie zawsze warto zweryfikować zachowanie
         * na docelowej bazie produkcyjnej, a nie tylko na H2.
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Long accountId = seedData(sessionFactory);

            CountDownLatch lockAcquiredLatch = new CountDownLatch(1);
            Map<String, Object> results = new HashMap<>();

            Thread threadA = new Thread(() -> {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                Account account = session.find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE);
                account.setBalance(account.getBalance() + 1.0);
                session.flush(); // wymusza UPDATE od razu, mimo ze transakcja jeszcze trwa
                System.out.println("[Watek A] Zdobyl blokade PESSIMISTIC_WRITE (+ wykonal UPDATE) na koncie id=" + account.getId());
                lockAcquiredLatch.countDown();
                sleepQuietly(800);
                transaction.commit();
                session.close();
                System.out.println("[Watek A] Zwolnil blokade (commit).");
            }, "watek-A");
            threadA.setDaemon(true);

            Thread threadB = new Thread(() -> {
                try {
                    lockAcquiredLatch.await(2, TimeUnit.SECONDS);
                    Session session = sessionFactory.openSession();
                    Transaction transaction = session.beginTransaction();
                    Map<String, Object> hints = new HashMap<>();
                    hints.put("jakarta.persistence.lock.timeout", 300);
                    long start = System.currentTimeMillis();
                    try {
                        session.find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE, hints);
                        results.put("outcome", "success-po-" + (System.currentTimeMillis() - start) + "ms");
                    } catch (LockTimeoutException e) {
                        results.put("outcome", "LockTimeoutException-po-" + (System.currentTimeMillis() - start) + "ms");
                    } finally {
                        transaction.rollback();
                        session.close();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "watek-B");
            threadB.setDaemon(true);

            threadA.start();
            threadB.start();
            threadA.join(3000);
            threadB.join(3000);

            System.out.println("\n=== WYNIK DEMO ===");
            System.out.println("Watek B: " + results.getOrDefault("outcome", "brak-wyniku-w-limicie-czasu"));
            System.out.println("Czas oczekiwania ok. 800ms dowodzi REALNEGO blokowania (tyle, ile Watek A trzymal blokade).");
            System.out.println("H2 NIE honoruje w pelni hinta lock.timeout=300ms - patrz wyjasnienie w komentarzu powyzej.");
        }

        /*
         * ============================================================
         * 🔍 RYZYKO: DEADLOCKI I SPADEK PRZEPUSTOWOŚCI
         * ============================================================
         * Blokady pesymistyczne trzymane przez DŁUGIE transakcje
         * poważnie ograniczają PRZEPUSTOWOŚĆ systemu (inne transakcje
         * czekają) - a gdy DWIE transakcje próbują zablokować DWA różne
         * wiersze w PRZECIWNEJ kolejności, może dojść do DEADLOCKU (patrz
         * _05_multithreading/Lesson25_Deadlock tego kursu - te same
         * zasady dotyczą też blokad na poziomie bazy danych, nie tylko
         * blokad w pamięci JVM). Bazy danych zwykle wykrywają taki
         * deadlock automatycznie i przerywają JEDNĄ z transakcji
         * wyjątkiem, żeby druga mogła kontynuować.
         */
        System.out.println("\n=== RYZYKO: DEADLOCKI I SPADEK PRZEPUSTOWOSCI ===");
        System.out.println("Dlugie transakcje z blokadami ograniczaja przepustowosc - i moga prowadzic do deadlockow");
        System.out.println("(te same zasady co w _05_multithreading/Lesson25_Deadlock, na poziomie bazy danych).");

        /*
         * ============================================================
         * 📌 KIEDY PESYMISTYCZNE, A KIEDY OPTYMISTYCZNE (Lesson25)
         * ============================================================
         */
        printOptimisticVsPessimisticTable();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Blokowanie pesymistyczne - blokuje wiersz JUŻ przy odczycie,
         *   na cały czas transakcji (zakłada częste/kosztowne konflikty).
         * - PESSIMISTIC_READ (FOR SHARE) vs PESSIMISTIC_WRITE (FOR
         *   UPDATE) - różny poziom wyłączności blokady.
         * - find(..., LockModeType, hints) z opcjonalnym
         *   "jakarta.persistence.lock.timeout" - kontrola czasu
         *   oczekiwania na blokadę.
         * - Ryzyko: spadek przepustowości i deadlocki przy długich
         *   transakcjach trzymających blokady.
         * - Wybór: pesymistyczne dla wysokiego ryzyka konfliktu/operacji
         *   krytycznych, optymistyczne (Lesson25) dla reszty przypadków.
         */

        System.out.println("\n=== KONIEC LEKCJI 26 ===");
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static Long seedData(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Account account = new Account();
        account.setOwner("Anna Nowak");
        account.setBalance(5000.0);
        session.persist(account);
        transaction.commit();
        session.close();
        return account.getId();
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson26pessimistic;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Account.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void printLockModesTable() {
        System.out.println("\n=== PESSIMISTIC_READ vs PESSIMISTIC_WRITE ===");
        String format = "%-20s | %-40s%n";
        System.out.printf(format, "Tryb", "Odpowiednik SQL / zachowanie");
        System.out.println("-".repeat(65));
        System.out.printf(format, "PESSIMISTIC_READ", "SELECT ... FOR SHARE - blokuje zapis, pozwala na odczyt");
        System.out.printf(format, "PESSIMISTIC_WRITE", "SELECT ... FOR UPDATE - blokuje WYLACZNIE dla siebie");
    }

    private static void printOptimisticVsPessimisticTable() {
        System.out.println("\n=== OPTYMISTYCZNE (Lesson25) vs PESYMISTYCZNE (ta lekcja) ===");
        String format = "%-25s | %-40s%n";
        System.out.printf(format, "Kryterium", "Wybor");
        System.out.println("-".repeat(70));
        System.out.printf(format, "Rzadkie konflikty", "Optymistyczne (@Version)");
        System.out.printf(format, "Czeste konflikty", "Pesymistyczne (LockModeType)");
        System.out.printf(format, "Operacje finansowe/krytyczne", "Pesymistyczne");
        System.out.printf(format, "Wysoka przepustowosc odczytow", "Optymistyczne");
    }

    @Entity(name = "Account")
    @Table(name = "account")
    public static class Account {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String owner;
        private double balance;

        public Long getId() {
            return id;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
