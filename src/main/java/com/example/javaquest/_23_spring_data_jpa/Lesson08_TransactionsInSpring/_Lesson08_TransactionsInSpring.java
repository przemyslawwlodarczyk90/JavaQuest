package com.example.javaquest._23_spring_data_jpa.Lesson08_TransactionsInSpring;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

public class _Lesson08_TransactionsInSpring {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 8: @Transactional W Spring Data ===");

        /*
         * ============================================================
         * 📦 GDZIE ZYJE GRANICA TRANSAKCJI - POGLEBIENIE _17_architecture/Lesson13
         * ============================================================
         * `_17_architecture/Lesson13_TransactionBoundaries` uczyl
         * KONCEPCJI (granica transakcji POWINNA zyc W WARSTWIE serwisu,
         * NIE W kontrolerze ani W repozytorium). Tu pokazujemy
         * KONKRETNY mechanizm: `@Transactional` NA metodzie SERWISU -
         * WSZYSTKIE operacje repozytorium wewnatrz TEJ metody DZIALAJA
         * W JEDNEJ transakcji - BLAD W SRODKU cofa WSZYSTKO.
         *
         * WAZNA PULAPKA (powiazanie Z `_20_spring_core/Lesson22` -
         * self-invocation): `@Transactional` DZIALA PRZEZ PROXY (jak
         * KAZDY mechanizm Spring AOP) - wywolanie metody
         * `@Transactional` Z TEJ SAMEJ klasy (`this.metoda()`) OMIJA
         * proxy I TRANSAKCJA NIE DZIALA.
         */
        System.out.println("@Transactional na metodzie serwisu = WSZYSTKIE operacje repozytorium W JEDNEJ transakcji - blad cofa WSZYSTKO.");

        demonstrateRollbackOnRuntimeException();
        demonstrateCheckedExceptionDoesNotRollbackByDefault();
        demonstrateSelfInvocationBypassesTransactionalProxy();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Transactional` NA metodzie serwisu - WSZYSTKO wewnatrz to
         *   JEDNA transakcja - `RuntimeException` (NIEWYLAPANY) COFA
         *   WSZYSTKIE zmiany.
         * - DOMYSLNIE `@Transactional` COFA TYLKO PRZY `RuntimeException`/
         *   `Error` - CHECKED wyjatek (`Exception`) NIE COFA transakcji,
         *   CHYBA ZE JAWNIE dodasz `rollbackFor = Exception.class`.
         * - Self-invocation (`this.metodaTransactional()` Z TEJ SAMEJ
         *   klasy) OMIJA proxy - `@Transactional` PO CICHU PRZESTAJE
         *   DZIALAC - TA SAMA pulapka co przy AOP (`_20_spring_core/
         *   Lesson22`).
         * - `@Transactional(readOnly = true)` - OPTYMALIZACJA DLA
         *   operacji TYLKO-ODCZYTU (Hibernate MOZE pominac dirty
         *   checking).
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    @Entity(name = "Account")
    static class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String owner;
        double balance;

        Account() {
        }

        Account(String owner, double balance) {
            this.owner = owner;
            this.balance = balance;
        }
    }

    interface AccountRepository extends JpaRepository<Account, Long> {
    }

    @Service
    static class TransferService {
        private final AccountRepository accountRepository;

        @Autowired
        TransferService(AccountRepository accountRepository) {
            this.accountRepository = accountRepository;
        }

        @Transactional
        void transferWithFailure(Long fromId, Long toId, double amount) {
            Account from = accountRepository.findById(fromId).orElseThrow();
            from.balance -= amount;
            accountRepository.save(from);

            // Symulacja bledu W POLOWIE transakcji - druga operacja NIGDY sie NIE wykona.
            throw new RuntimeException("Symulowany blad systemu podczas transferu");
        }

        @Transactional
        void transferSuccessfully(Long fromId, Long toId, double amount) {
            Account from = accountRepository.findById(fromId).orElseThrow();
            Account to = accountRepository.findById(toId).orElseThrow();
            from.balance -= amount;
            to.balance += amount;
            accountRepository.save(from);
            accountRepository.save(to);
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class RollbackApp {
        @Bean
        TransferService transferService(AccountRepository repository) {
            return new TransferService(repository);
        }
    }

    private static void demonstrateRollbackOnRuntimeException() throws Exception {
        System.out.println("\n=== RuntimeException W @Transactional - COFA WSZYSTKIE zmiany ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson08rollback;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RollbackApp.class)
                .properties(props)
                .run();
        try {
            AccountRepository repository = context.getBean(AccountRepository.class);
            TransferService service = context.getBean(TransferService.class);

            Account a = repository.save(new Account("Jan", 1000.0));
            Account b = repository.save(new Account("Anna", 500.0));

            try {
                service.transferWithFailure(a.id, b.id, 200.0);
            } catch (RuntimeException ex) {
                System.out.println("transferWithFailure(...) rzucil: " + ex.getMessage());
            }

            Account reloaded = repository.findById(a.id).orElseThrow();
            System.out.println("Saldo konta Jana PO nieudanym transferze: " + reloaded.balance + " (oczekiwane: 1000.0 - transakcja ZOSTALA COFNIETA, mimo ze 'save(from)' JUZ SIE WYKONALO)");
        } finally {
            context.close();
        }
    }

    @Service
    static class CheckedExceptionService {
        private final AccountRepository accountRepository;

        @Autowired
        CheckedExceptionService(AccountRepository accountRepository) {
            this.accountRepository = accountRepository;
        }

        @Transactional
        void updateBalanceThenThrowChecked(Long accountId, double newBalance) throws Exception {
            Account account = accountRepository.findById(accountId).orElseThrow();
            account.balance = newBalance;
            accountRepository.save(account);
            throw new Exception("CHECKED wyjatek - domyslnie NIE cofa transakcji");
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class CheckedExceptionApp {
        @Bean
        CheckedExceptionService checkedExceptionService(AccountRepository repository) {
            return new CheckedExceptionService(repository);
        }
    }

    private static void demonstrateCheckedExceptionDoesNotRollbackByDefault() throws Exception {
        System.out.println("\n=== CHECKED wyjatek W @Transactional - DOMYSLNIE NIE COFA transakcji! ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson08checked;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CheckedExceptionApp.class)
                .properties(props)
                .run();
        try {
            AccountRepository repository = context.getBean(AccountRepository.class);
            CheckedExceptionService service = context.getBean(CheckedExceptionService.class);

            Account account = repository.save(new Account("Piotr", 100.0));

            try {
                service.updateBalanceThenThrowChecked(account.id, 999.0);
            } catch (Exception ex) {
                System.out.println("updateBalanceThenThrowChecked(...) rzucil CHECKED wyjatek: " + ex.getMessage());
            }

            Account reloaded = repository.findById(account.id).orElseThrow();
            System.out.println("Saldo PO checked wyjatku: " + reloaded.balance + " (oczekiwane: 999.0 - transakcja ZOSTALA ZATWIERDZONA mimo wyjatku, bo to NIE RuntimeException!)");
            System.out.println("-> Naprawa: '@Transactional(rollbackFor = Exception.class)' WYMUSZA cofniecie TEZ DLA checked wyjatkow.");
        } finally {
            context.close();
        }
    }

    @Service
    static class SelfInvocationService {
        private final AccountRepository accountRepository;

        @Autowired
        SelfInvocationService(AccountRepository accountRepository) {
            this.accountRepository = accountRepository;
        }

        void publicEntryPoint(Long accountId) {
            // Wywolanie 'this.transactionalMethod(...)' - OMIJA proxy, @Transactional NIE DZIALA.
            this.transactionalMethod(accountId);
        }

        @Transactional
        void transactionalMethod(Long accountId) {
            Account account = accountRepository.findById(accountId).orElseThrow();
            account.balance = 1_000_000.0;
            accountRepository.save(account);
            throw new RuntimeException("Powinno cofnac zmiany, ALE self-invocation OMIJA proxy!");
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class SelfInvocationApp {
        @Bean
        SelfInvocationService selfInvocationService(AccountRepository repository) {
            return new SelfInvocationService(repository);
        }
    }

    private static void demonstrateSelfInvocationBypassesTransactionalProxy() throws Exception {
        System.out.println("\n=== SELF-INVOCATION - this.metoda() OMIJA proxy, @Transactional PRZESTAJE DZIALAC ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson08selfinvocation;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SelfInvocationApp.class)
                .properties(props)
                .run();
        try {
            AccountRepository repository = context.getBean(AccountRepository.class);
            SelfInvocationService service = context.getBean(SelfInvocationService.class);

            Account account = repository.save(new Account("Ewa", 50.0));

            try {
                service.publicEntryPoint(account.id);
            } catch (RuntimeException ex) {
                System.out.println("publicEntryPoint(...) rzucil: " + ex.getMessage());
            }

            Account reloaded = repository.findById(account.id).orElseThrow();
            System.out.println("Saldo PO self-invocation: " + reloaded.balance + " (oczekiwane: 1000000.0 - transakcja MIALA cofnac, ale self-invocation OMINELA proxy!)");
        } finally {
            context.close();
        }
    }
}
