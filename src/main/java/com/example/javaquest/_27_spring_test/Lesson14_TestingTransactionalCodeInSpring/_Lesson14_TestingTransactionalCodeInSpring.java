package com.example.javaquest._27_spring_test.Lesson14_TestingTransactionalCodeInSpring;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson14_TestingTransactionalCodeInSpring {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: @Transactional w testach - auto-rollback i TestTransaction ===");

        /*
         * ============================================================
         * 📦 @Transactional NA klasie/metodzie testowej = auto-rollback "z automatu" (jak @DataJpaTest)
         * ============================================================
         * Lesson06 pokazal, ze `@DataJpaTest` MA auto-rollback WBUDOWANY.
         * TA lekcja pokazuje, ze TEN SAM mechanizm MOZNA WLACZYC W
         * DOWOLNYM `@SpringBootTest` PRZEZ RECZNE dodanie
         * `@Transactional` NA klasie/metodzie testowej (Spring Test
         * TRAKTUJE `@Transactional` NA TESCIE SPECJALNIE - domyslnie
         * ROLLBACK, W ODROZNIENIU OD `@Transactional` NA PRAWDZIWYM
         * kodzie produkcyjnym, KTORY domyslnie COMMITUJE -
         * powiazanie Z `_23_spring_data_jpa/Lesson08`).
         *
         * 🔍 `TestTransaction` - PROGRAMOWA kontrola TRANSAKCJI
         * testowej W TRAKCIE metody (`flagForCommit()`/`end()`/
         * `start()`) - PRZYDATNE, GDY test MUSI zweryfikowac stan PO
         * FAKTYCZNYM commicie (np. SPRAWDZIC zachowanie triggera SQL).
         */
        System.out.println("@Transactional NA tescie = auto-rollback (odwrotnie niz NA kodzie produkcyjnym). TestTransaction = programowa kontrola.");

        runAndReport(TaskRepositoryTransactionalTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Transactional` NA klasie testowej `@SpringBootTest` -
         *   KAZDA metoda `@Test` dziala W WLASNEJ transakcji,
         *   COFNIETEJ PO zakonczeniu (DOMYSLNE zachowanie).
         * - `@Rollback(false)` (LUB `@Commit`) NA metodzie - JAWNIE
         *   WYLACZA rollback DLA TEJ JEDNEJ metody (RZADKO
         *   potrzebne, GLOWNIE DO debugowania - "chce zobaczyc dane W
         *   bazie PO tescie").
         * - `TestTransaction.flagForCommit()` +
         *   `TestTransaction.end()` - PROGRAMOWO KONCZY biezaca
         *   transakcje Z commitem W TRAKCIE metody testowej
         *   (`TestTransaction.start()` MOZE potem zaczac NOWA).
         * - TA SAMA mechanika co `@DataJpaTest` (Lesson06), ALE
         *   DOSTEPNA W `@SpringBootTest`/dowolnym tescie - nie TYLKO
         *   W wycinku JPA.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    @Entity(name = "TransactionalTask")
    static class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String title;

        Task() {
        }

        Task(String title) {
            this.title = title;
        }
    }

    interface TaskRepository extends JpaRepository<Task, Long> {
    }

    @SpringBootApplication
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class TestApp {
    }

    @SpringBootTest(classes = TestApp.class, properties = {
            "spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
    @Transactional
    static class TaskRepositoryTransactionalTest {
        @Autowired
        TaskRepository repository;

        @Test
        void firstTestSavesTaskInsideAutoRollbackTransaction() {
            repository.save(new Task("Zadanie z pierwszego testu"));
            assertThat(repository.count()).isEqualTo(1);
            System.out.println("Test 1: zapisano zadanie, count()=" + repository.count() + " (transakcja NIE JEST JESZCZE zatwierdzona).");
        }

        @Test
        void secondTestSeesCleanDatabaseDueToRollbackOfFirstTest() {
            // Analogicznie DO Lesson06 - baza jest PUSTA, bo transakcja Z POPRZEDNIEGO testu
            // zostala COFNIETA PO jego zakonczeniu (@Transactional NA klasie testowej).
            assertThat(repository.count()).isZero();
            System.out.println("Test 2: baza jest PUSTA - transakcja z Testu 1 zostala COFNIETA (auto-rollback).");
        }

        @Test
        void testTransactionApiAllowsProgrammaticCommitMidTest() {
            repository.save(new Task("Zadanie z programowym commitem"));

            assertThat(TestTransaction.isActive()).isTrue();
            TestTransaction.flagForCommit();
            TestTransaction.end();
            System.out.println("TestTransaction.end() ZAKONCZYL transakcje Z commitem (dane BYLYBY trwale, GDYBY NIE nowa transakcja ponizej).");

            TestTransaction.start();
            assertThat(repository.count()).isEqualTo(1);
            System.out.println("Nowa transakcja (TestTransaction.start()) WIDZI zacommitowane dane: count()=" + repository.count());

            // WAZNE: flagForCommit()+end() TRWALE zapisalo dane (NIE zostana cofniete jak
            // reszta testu) - MUSIMY je RECZNIE posprzatac I TEZ zacommitowac to sprzatanie
            // (inaczej domyslny rollback NA KONCU testu cofnalby SAMO deleteAll(), zostawiajac
            // "brudny" wiersz DLA INNYCH testow - dokladnie ta sama zasada izolacji co
            // _26_integration_testing/Lesson11).
            repository.deleteAll();
            TestTransaction.flagForCommit();
        }
    }

    private static void runAndReport(Class<?> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println(testClass.getSimpleName() + " -> udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount());
    }
}
