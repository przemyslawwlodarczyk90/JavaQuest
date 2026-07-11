package com.example.javaquest._20_spring_core.Lesson22_SpringAopAdvancedPointcutsAndProxies;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class _Lesson22_SpringAopAdvancedPointcutsAndProxies {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 22: ZAAWANSOWANE POINTCUTY I PULAPKI PROXY ===");

        /*
         * ============================================================
         * 📦 execution() - DOPASOWANIE PO SYGNATURZE, NIE ADNOTACJI
         * ============================================================
         * Lesson21 uzywal `@annotation(" + LOG_EXECUTION + ")` - dopasowanie
         * PO ADNOTACJI (musisz oznaczyc KAZDA metode). Wyrazenie
         * `execution(...)` dopasowuje PO SYGNATURZE (pakiet, klasa,
         * nazwa metody, parametry) - MOZESZ objac CALA warstwe
         * (np. "wszystkie metody publiczne w pakiecie *.service") BEZ
         * dotykania ani jednej linii kodu docelowych klas.
         */
        System.out.println("execution(...) dopasowuje PO SYGNATURZE metody (pakiet/klasa/nazwa) - obejmuje CALA warstwe BEZ adnotowania kazdej metody.");

        demonstrateExecutionPointcutMatchesWholeInterface();
        demonstrateSelfInvocationBypassesProxy();
        demonstrateMultipleAspectsOrderedExecution();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `execution(...)` dopasowuje PO SYGNATURZE - obejmuje CALE
         *   warstwy naraz, `@annotation(...)` dopasowuje PO ADNOTACJI -
         *   precyzyjniejsze, ale wymaga oznaczenia kazdej metody.
         * - PULAPKA SELF-INVOCATION: wywolanie metody `@Transactional`/
         *   `@Cacheable`/dowolnej "AOP-owanej" metody Z WNETRZA TEJ
         *   SAMEJ klasy (`this.metoda()`) OMIJA proxy - aspekt SIE NIE
         *   URUCHOMI. To JEDEN Z NAJCZESTSZYCH bledow w praktyce
         *   ("dlaczego moj @Transactional nie dziala?").
         * - `@Order` na wielu aspektach kontroluje KOLEJNOSC ich
         *   "owijania" wokol metody - nizszy numer = "bliej" metody
         *   docelowej w kolejnosci @Before, dalej w kolejnosci @After.
         */
        System.out.println("\n=== KONIEC LEKCJI 22 ===");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface LogExecution {
    }

    // Pelna nazwa WYMAGANA w wyrazeniach pointcut ponizej - patrz notatka w Lesson21 (sam parser
    // AspectJ nie rozwiazuje zagniezdzonej adnotacji po prostej nazwie).
    private static final String LOG_EXECUTION = "com.example.javaquest._20_spring_core.Lesson22_SpringAopAdvancedPointcutsAndProxies._Lesson22_SpringAopAdvancedPointcutsAndProxies.LogExecution";

    interface PricingService {
        double calculate(double amount);

        double calculateWithTax(double amount);
    }

    static class PricingServiceImpl implements PricingService {
        @Override
        public double calculate(double amount) {
            System.out.println("    [PricingServiceImpl] calculate(" + amount + ")");
            return amount;
        }

        @Override
        public double calculateWithTax(double amount) {
            System.out.println("    [PricingServiceImpl] calculateWithTax(" + amount + ")");
            return amount * 1.23;
        }
    }

    @Aspect
    static class WholeInterfaceLoggingAspect {
        // execution(<zwracany typ> <pelna nazwa klasy>.<nazwa metody>(<parametry>))
        // '*' jako typ zwracany/nazwa metody = 'dowolny'. '..' jako parametry = 'dowolna liczba'.
        @Before("execution(* com.example.javaquest._20_spring_core.Lesson22_SpringAopAdvancedPointcutsAndProxies._Lesson22_SpringAopAdvancedPointcutsAndProxies.PricingServiceImpl.*(..))")
        void logAnyMethod(JoinPoint joinPoint) {
            System.out.println("  [Aspect execution()] PRZED: " + joinPoint.getSignature().getName() + " - ZERO adnotacji na metodzie docelowej!");
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    static class ExecutionPointcutConfig {
        @Bean
        PricingService pricingService() {
            return new PricingServiceImpl();
        }

        @Bean
        WholeInterfaceLoggingAspect wholeInterfaceLoggingAspect() {
            return new WholeInterfaceLoggingAspect();
        }
    }

    private static void demonstrateExecutionPointcutMatchesWholeInterface() {
        System.out.println("\n=== execution(): DOPASOWANIE CALEJ KLASY BEZ ADNOTOWANIA METOD ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExecutionPointcutConfig.class)) {
            PricingService service = context.getBean(PricingService.class);
            service.calculate(100.0);
            service.calculateWithTax(100.0);
            System.out.println("-> OBIE metody zostaly 'zlapane' - PricingServiceImpl NIE MA ani jednej adnotacji @LogExecution.");
        }
    }

    interface ReportService {
        String generateSummary();

        String generateDetailed();
    }

    static class ReportServiceImpl implements ReportService {
        @LogExecution
        @Override
        public String generateSummary() {
            System.out.println("    [ReportServiceImpl] generateSummary()");
            return "PODSUMOWANIE";
        }

        @LogExecution
        @Override
        public String generateDetailed() {
            System.out.println("    [ReportServiceImpl] generateDetailed() - woluje generateSummary() PRZEZ 'this'...");
            // UWAGA: to wywolanie idzie na SUROWY obiekt ('this'), NIE na proxy - aspekt NIE
            // ZOBACZY tego wywolania, mimo ze generateSummary() jest oznaczone @LogExecution.
            String summary = this.generateSummary();
            return summary + " + SZCZEGOLY";
        }
    }

    @Aspect
    static class CountingAspect {
        static int invocationCount = 0;

        @Before("@annotation(" + LOG_EXECUTION + ")")
        void count(JoinPoint joinPoint) {
            invocationCount++;
            System.out.println("  [Aspect @Before] #" + invocationCount + ": " + joinPoint.getSignature().getName());
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    static class SelfInvocationConfig {
        @Bean
        ReportService reportService() {
            return new ReportServiceImpl();
        }

        @Bean
        CountingAspect countingAspect() {
            return new CountingAspect();
        }
    }

    private static void demonstrateSelfInvocationBypassesProxy() {
        System.out.println("\n=== PULAPKA: SELF-INVOCATION OMIJA PROXY (NAJCZESTSZY BLAD W PRAKTYCE) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SelfInvocationConfig.class)) {
            CountingAspect.invocationCount = 0;
            ReportService service = context.getBean(ReportService.class);

            System.out.println("Wywoluje generateDetailed() PRZEZ PROXY (z zewnatrz, jak normalne wywolanie beana):");
            service.generateDetailed();

            System.out.println("\nLiczba przechwycen aspektu: " + CountingAspect.invocationCount + " (oczekiwane: 1, NIE 2!)");
            System.out.println("-> generateDetailed() ZOSTALO zlapane (wywolanie PRZEZ proxy z zewnatrz),");
            System.out.println("   ale WEWNETRZNE wywolanie generateSummary() (przez 'this') NIE - to TA SAMA pulapka co przy @Transactional w realnych projektach.");
        }
    }

    static class RecordingAspectBase {
        static final List<String> executionLog = new ArrayList<>();
    }

    @Aspect
    @Order(1)
    static class FirstAspect extends RecordingAspectBase {
        @Before("@annotation(" + LOG_EXECUTION + ")")
        void before(JoinPoint joinPoint) {
            executionLog.add("FirstAspect (@Order(1)) - PRZED");
        }
    }

    @Aspect
    @Order(2)
    static class SecondAspect extends RecordingAspectBase {
        @Before("@annotation(" + LOG_EXECUTION + ")")
        void before(JoinPoint joinPoint) {
            executionLog.add("SecondAspect (@Order(2)) - PRZED");
        }
    }

    static class OrderedTargetService {
        @LogExecution
        void doWork() {
            RecordingAspectBase.executionLog.add("OrderedTargetService.doWork() - SAMA METODA");
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    static class OrderingConfig {
        @Bean
        OrderedTargetService orderedTargetService() {
            return new OrderedTargetService();
        }

        @Bean
        FirstAspect firstAspect() {
            return new FirstAspect();
        }

        @Bean
        SecondAspect secondAspect() {
            return new SecondAspect();
        }
    }

    private static void demonstrateMultipleAspectsOrderedExecution() {
        System.out.println("\n=== @Order NA WIELU ASPEKTACH: KONTROLA KOLEJNOSCI 'OWIJANIA' ===");

        RecordingAspectBase.executionLog.clear();
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OrderingConfig.class)) {
            OrderedTargetService service = context.getBean(OrderedTargetService.class);
            service.doWork();

            System.out.println("Kolejnosc wykonania:");
            RecordingAspectBase.executionLog.forEach(entry -> System.out.println("  " + entry));
            System.out.println("-> nizszy numer @Order (1) 'owija' METODE JAKO PIERWSZY (jest 'blizej' rdzenia) - FirstAspect wykonal sie PRZED SecondAspect.");
        }
    }
}
