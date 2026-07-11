package com.example.javaquest._20_spring_core.Lesson12_QualifierAndPrimary;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

public class _Lesson12_QualifierAndPrimary {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: @Qualifier I @Primary ===");

        /*
         * ============================================================
         * 📦 PROBLEM: WIECEJ NIZ JEDEN BEAN TEGO SAMEGO TYPU
         * ============================================================
         * Do tej pory kazdy interfejs mial DOKLADNIE JEDNA implementacje
         * w kontekscie. W realnych aplikacjach czesto masz WIELE
         * implementacji TEGO SAMEGO interfejsu (np. `PaymentGateway`:
         * Stripe/PayPal/Przelewy24) - Spring NIE WIE, ktorej uzyc przy
         * wstrzykiwaniu PO TYPIE. Dzisiaj zobaczysz problem NA ZYWO i 2
         * sposoby jego rozwiazania.
         */
        System.out.println("Wiecej niz 1 bean tego samego typu = Spring NIE WIE, ktory wstrzyknac - dzis 2 rozwiazania.");

        demonstrateAmbiguousBeanFailsWithoutHint();
        demonstratePrimaryResolvesAmbiguity();
        demonstrateQualifierPicksSpecificBean();

        /*
         * ============================================================
         * 🔹 @Primary vs @Qualifier - KIEDY KTOREGO UZYC
         * ============================================================
         * - `@Primary` — na POZIOMIE DEFINICJI beana - "to jest
         *   DOMYSLNY wybor, gdy ktos nie powie inaczej". Jest TYLKO
         *   JEDEN `@Primary` na typ (2+ `@Primary` tego samego typu to
         *   TAK SAMO niejednoznaczne).
         * - `@Qualifier("nazwa")` — na POZIOMIE MIEJSCA WSTRZYKIWANIA -
         *   "chce WLASNIE TEN konkretny bean, NIEZALEZNIE od tego, ktory
         *   jest @Primary". Precyzyjniejszy, ale WYMAGA jawnego podania
         *   za KAZDYM razem.
         * `@Qualifier` w miejscu wstrzykiwania ZAWSZE WYGRYWA z
         * `@Primary`, gdy oba sa obecne.
         */
        System.out.println("\n@Primary = 'domyslny wybor calego typu'. @Qualifier = 'ten KONKRETNY, w TYM miejscu' - Qualifier WYGRYWA z Primary.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wiecej niz 1 bean danego typu BEZ podpowiedzi =
         *   `NoUniqueBeanDefinitionException` przy starcie kontekstu.
         * - `@Primary` na definicji beana ustawia DOMYSLNY wybor.
         * - `@Qualifier("nazwa")` w miejscu wstrzykiwania wybiera
         *   KONKRETNY bean, niezaleznie od `@Primary`.
         * - Oba mechanizmy DZIALAJA razem - `@Primary` jako "fallback",
         *   `@Qualifier` jako "wyjatek od reguly" w konkretnym miejscu.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    interface PaymentGateway {
        String pay(double amount);
    }

    static class StripeGateway implements PaymentGateway {
        @Override
        public String pay(double amount) {
            return "Stripe: zaplacono " + amount;
        }
    }

    static class PayPalGateway implements PaymentGateway {
        @Override
        public String pay(double amount) {
            return "PayPal: zaplacono " + amount;
        }
    }

    static class CheckoutService {
        private final PaymentGateway gateway;

        CheckoutService(PaymentGateway gateway) {
            this.gateway = gateway;
        }

        String checkout(double amount) {
            return gateway.pay(amount);
        }
    }

    @Configuration
    static class AmbiguousConfig {
        @Bean
        StripeGateway stripeGateway() {
            return new StripeGateway();
        }

        @Bean
        PayPalGateway payPalGateway() {
            return new PayPalGateway();
        }

        @Bean
        CheckoutService checkoutService(PaymentGateway gateway) {
            return new CheckoutService(gateway);
        }
    }

    private static void demonstrateAmbiguousBeanFailsWithoutHint() {
        System.out.println("\n=== BEZ PODPOWIEDZI: NoUniqueBeanDefinitionException ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AmbiguousConfig.class)) {
            context.getBean(CheckoutService.class);
            System.out.println("BLAD: kontekst wystartowal, mimo ze POWINIEN byl sie nie udac!");
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            System.out.println("Kontekst NIE wystartowal - przyczyna: " + root.getClass().getSimpleName());
            System.out.println("Komunikat: " + root.getMessage());
            System.out.println("-> " + (root instanceof NoUniqueBeanDefinitionException ? "DOKLADNIE ten wyjatek, ktorego sie spodziewalismy." : "inny wyjatek."));
        }
    }

    @Configuration
    static class PrimaryConfig {
        @Bean
        @Primary
        StripeGateway stripeGateway() {
            return new StripeGateway();
        }

        @Bean
        PayPalGateway payPalGateway() {
            return new PayPalGateway();
        }

        @Bean
        CheckoutService checkoutService(PaymentGateway gateway) {
            return new CheckoutService(gateway);
        }
    }

    private static void demonstratePrimaryResolvesAmbiguity() {
        System.out.println("\n=== @Primary: DOMYSLNY WYBOR SPOSROD WIELU BEANOW ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrimaryConfig.class)) {
            CheckoutService service = context.getBean(CheckoutService.class);
            System.out.println(service.checkout(150.0));
            System.out.println("-> StripeGateway (@Primary) zostal wybrany AUTOMATYCZNIE, mimo ze PayPalGateway TEZ pasuje do typu PaymentGateway.");
        }
    }

    static class QualifierAwareCheckoutService {
        private final PaymentGateway gateway;

        QualifierAwareCheckoutService(@Qualifier("payPalGateway") PaymentGateway gateway) {
            this.gateway = gateway;
        }

        String checkout(double amount) {
            return gateway.pay(amount);
        }
    }

    @Configuration
    static class QualifierConfig {
        @Bean
        @Primary
        StripeGateway stripeGateway() {
            return new StripeGateway();
        }

        @Bean
        PayPalGateway payPalGateway() {
            return new PayPalGateway();
        }

        @Bean
        QualifierAwareCheckoutService qualifierAwareCheckoutService(@Qualifier("payPalGateway") PaymentGateway gateway) {
            return new QualifierAwareCheckoutService(gateway);
        }
    }

    private static void demonstrateQualifierPicksSpecificBean() {
        System.out.println("\n=== @Qualifier: WYBIERA KONKRETNY BEAN, WYGRYWA Z @Primary ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(QualifierConfig.class)) {
            QualifierAwareCheckoutService service = context.getBean(QualifierAwareCheckoutService.class);
            System.out.println(service.checkout(75.0));
            System.out.println("-> mimo ze StripeGateway jest @Primary, @Qualifier(\"payPalGateway\") w KONSTRUKTORZE jawnie zazadal INNEGO beana - i go dostal.");
        }
    }
}
