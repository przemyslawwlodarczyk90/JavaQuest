package com.example.javaquest._20_spring_core.Lesson21_SpringAopFundamentals;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class _Lesson21_SpringAopFundamentals {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 21: SPRING AOP - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 PROBLEM: KOD "PRZECINAJACY" WIELE KLAS NARAZ
         * ============================================================
         * Logowanie, pomiar czasu, bezpieczenstwo, TRANSAKCJE
         * (`@Transactional` z `_23_spring_data_jpa/Lesson08` — TO
         * WLASNIE ten sam mechanizm!) - to "cross-cutting concerns":
         * logika POTRZEBNA w DZIESIATKACH miejsc naraz, NIEZWIAZANA z
         * logika biznesowa KONKRETNEJ klasy. AOP (Aspect-Oriented
         * Programming) pozwala NAPISAC tu logike RAZ, w JEDNYM miejscu
         * ("aspekt"), i "podpiac" ja automatycznie do WIELU metod -
         * kontener TWORZY PROXY, ktore PRZECHWYTUJE wywolania.
         */
        System.out.println("AOP = logika 'przecinajaca' wiele klas (logowanie, bezpieczenstwo, TRANSAKCJE) napisana RAZ, podpieta automatycznie przez proxy.");

        demonstrateProblemWithoutAop();
        demonstrateBeforeAfterReturningAfterThrowingAdvice();
        demonstrateAroundAdviceForTiming();
        demonstrateJdkProxyForInterfaceImplementingBean();
        demonstrateCglibProxyForClassWithoutInterface();

        /*
         * ============================================================
         * 🔹 JDK DYNAMIC PROXY vs CGLIB - JAK SPRING TWORZY PROXY
         * ============================================================
         * - JDK dynamic proxy - dziala TYLKO dla klas implementujacych
         *   INTERFEJS - proxy implementuje TEN SAM interfejs.
         * - CGLIB - tworzy PODKLASE oryginalnej klasy (dziala BEZ
         *   interfejsu) - JEDYNA opcja dla klas konkretnych.
         * Spring Boot ustawia `proxyTargetClass=true` DOMYSLNIE (zawsze
         * CGLIB) - w "golym" Spring Frameworku domyslnie jest JDK proxy
         * DLA klas z interfejsem. Zobaczyles OBA warianty wyzej.
         */
        System.out.println("\nJDK proxy = implementuje interfejs (goly Spring, domyslnie). CGLIB = podklasa oryginalnej klasy (Spring Boot, domyslnie zawsze).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - AOP rozwiazuje problem logiki POWTARZAJACEJ SIE w wielu
         *   miejscach (logowanie/bezpieczenstwo/TRANSAKCJE).
         * - `@Aspect` + `@Before`/`@AfterReturning`/`@AfterThrowing`/
         *   `@After`/`@Around` - RODZAJE "rad" (advice), KAZDY w innym
         *   momencie wywolania metody.
         * - Mechanizm oparty jest o PROXY (JDK dynamic proxy lub CGLIB) -
         *   DOKLADNIE ten sam pomysl co reczny proxy z `Lesson08`.
         * - `@Transactional`, `@Cacheable`, `@Async` - WSZYSTKIE dzialaja
         *   dzieki TEMU SAMEMU mechanizmowi AOP.
         */
        System.out.println("\n=== KONIEC LEKCJI 21 ===");
    }

    static class ManualLoggingOrderService {
        String placeOrder(String product) {
            long start = System.nanoTime();
            System.out.println("  [RECZNY LOG] PRZED: placeOrder(" + product + ")");
            String result = "OK-" + product;
            System.out.println("  [RECZNY LOG] PO: wynik=" + result + ", czas=" + (System.nanoTime() - start) + "ns");
            return result;
        }

        String cancelOrder(String orderId) {
            long start = System.nanoTime();
            System.out.println("  [RECZNY LOG] PRZED: cancelOrder(" + orderId + ")");
            String result = "ANULOWANO-" + orderId;
            System.out.println("  [RECZNY LOG] PO: wynik=" + result + ", czas=" + (System.nanoTime() - start) + "ns");
            return result;
        }
    }

    private static void demonstrateProblemWithoutAop() {
        System.out.println("\n=== PROBLEM: LOGOWANIE RECZNIE POWTORZONE W KAZDEJ METODZIE ===");

        ManualLoggingOrderService service = new ManualLoggingOrderService();
        service.placeOrder("Laptop");
        service.cancelOrder("ORD-1");
        System.out.println("-> IDENTYCZNY 'szkielet' logowania skopiowany w KAZDEJ metodzie - przy 20 metodach to 20 kopii TEGO SAMEGO kodu.");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface LogExecution {
    }

    // Nazwa PELNA (nie sam 'LogExecution') MUSI byc uzywana w STRINGACH wyrazen pointcut ponizej -
    // parser AspectJ nie zna zasiegu (scope) Javy, wiec sama nazwa ZAGNIEZDZONEJ adnotacji nie
    // rozwiazuje sie poprawnie i pointcut po cichu NIE DOPASOWUJE NICZEGO (zero metod = zero
    // proxy!) - kolejna pulapka zagniezdzania klas typowa dla stylu tego kursu.
    private static final String LOG_EXECUTION = "com.example.javaquest._20_spring_core.Lesson21_SpringAopFundamentals._Lesson21_SpringAopFundamentals.LogExecution";

    interface OrderService {
        String placeOrder(String product);

        String cancelOrder(String orderId);
    }

    static class OrderServiceImpl implements OrderService {
        @LogExecution
        @Override
        public String placeOrder(String product) {
            System.out.println("    [OrderServiceImpl] przetwarzam zamowienie: " + product);
            if (product.isBlank()) {
                throw new IllegalArgumentException("Produkt nie moze byc pusty");
            }
            return "OK-" + product;
        }

        @LogExecution
        @Override
        public String cancelOrder(String orderId) {
            System.out.println("    [OrderServiceImpl] anuluje zamowienie: " + orderId);
            return "ANULOWANO-" + orderId;
        }
    }

    @Aspect
    static class LoggingAspect {
        @Before("@annotation(" + LOG_EXECUTION + ")")
        void logBefore(JoinPoint joinPoint) {
            System.out.println("  [Aspect @Before] PRZED: " + joinPoint.getSignature().getName());
        }

        @AfterReturning(pointcut = "@annotation(" + LOG_EXECUTION + ")", returning = "result")
        void logAfterReturning(JoinPoint joinPoint, Object result) {
            System.out.println("  [Aspect @AfterReturning] " + joinPoint.getSignature().getName() + " zwrocilo: " + result);
        }

        @AfterThrowing(pointcut = "@annotation(" + LOG_EXECUTION + ")", throwing = "ex")
        void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
            System.out.println("  [Aspect @AfterThrowing] " + joinPoint.getSignature().getName() + " RZUCILO: " + ex.getClass().getSimpleName());
        }

        @After("@annotation(" + LOG_EXECUTION + ")")
        void logAfter(JoinPoint joinPoint) {
            System.out.println("  [Aspect @After] ZAWSZE (sukces LUB wyjatek): " + joinPoint.getSignature().getName());
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    static class AdviceTypesConfig {
        @Bean
        OrderService orderService() {
            return new OrderServiceImpl();
        }

        @Bean
        LoggingAspect loggingAspect() {
            return new LoggingAspect();
        }
    }

    private static void demonstrateBeforeAfterReturningAfterThrowingAdvice() {
        System.out.println("\n=== @Before / @AfterReturning / @AfterThrowing / @After (WSZYSTKIE RODZAJE 'RAD') ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdviceTypesConfig.class)) {
            OrderService service = context.getBean(OrderService.class);

            System.out.println("--- Wywolanie ZAKONCZONE SUKCESEM ---");
            service.placeOrder("Klawiatura");

            System.out.println("\n--- Wywolanie KONCZACE SIE WYJATKIEM ---");
            try {
                service.placeOrder("");
            } catch (IllegalArgumentException e) {
                System.out.println("  (wyjatek zlapany w main - aspekt i tak zdazyl zareagowac PRZED nim)");
            }
            System.out.println("-> Ty NIGDY nie napisales logiki logowania W SAMEJ klasie OrderServiceImpl - CALA zyje w LoggingAspect.");
        }
    }

    @Aspect
    static class TimingAspect {
        @Around("@annotation(" + LOG_EXECUTION + ")")
        Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
            long start = System.nanoTime();
            System.out.println("  [Aspect @Around] START: " + joinPoint.getSignature().getName());
            Object result = joinPoint.proceed(); // TU faktycznie wywoluje sie ORYGINALNA metoda
            long elapsedNanos = System.nanoTime() - start;
            System.out.println("  [Aspect @Around] KONIEC: " + joinPoint.getSignature().getName() + " (" + elapsedNanos + " ns)");
            return result;
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    static class AroundConfig {
        @Bean
        OrderService orderService() {
            return new OrderServiceImpl();
        }

        @Bean
        TimingAspect timingAspect() {
            return new TimingAspect();
        }
    }

    private static void demonstrateAroundAdviceForTiming() {
        System.out.println("\n=== @Around: PELNA KONTROLA NAD WYWOLANIEM (NAJPOTEZNIEJSZY RODZAJ 'RADY') ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AroundConfig.class)) {
            OrderService service = context.getBean(OrderService.class);
            service.placeOrder("Monitor");
            System.out.println("-> @Around MUSI jawnie wywolac joinPoint.proceed() - inaczej ORYGINALNA metoda W OGOLE by sie NIE wykonala!");
        }
    }

    private static void demonstrateJdkProxyForInterfaceImplementingBean() {
        System.out.println("\n=== JDK DYNAMIC PROXY (BEAN IMPLEMENTUJE INTERFEJS, proxyTargetClass=false) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdviceTypesConfig.class)) {
            OrderService service = context.getBean(OrderService.class);
            System.out.println("Rzeczywista klasa beana: " + service.getClass().getName());
            System.out.println("Czy to WCIAZ 'OrderServiceImpl'? " + (service.getClass() == OrderServiceImpl.class));
            System.out.println("-> to NIE jest 'OrderServiceImpl' - to WYGENEROWANA klasa proxy implementujaca interfejs OrderService.");
        }
    }

    static class NoInterfaceService {
        @LogExecution
        String process(String input) {
            System.out.println("    [NoInterfaceService] przetwarzam: " + input);
            return "PRZETWORZONE-" + input;
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    static class CglibConfig {
        @Bean
        NoInterfaceService noInterfaceService() {
            return new NoInterfaceService();
        }

        @Bean
        LoggingAspect loggingAspect() {
            return new LoggingAspect();
        }
    }

    private static void demonstrateCglibProxyForClassWithoutInterface() {
        System.out.println("\n=== CGLIB PROXY (BEAN BEZ INTERFEJSU - JEDYNA MOZLIWA OPCJA) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CglibConfig.class)) {
            NoInterfaceService service = context.getBean(NoInterfaceService.class);
            service.process("dane");
            System.out.println("Rzeczywista klasa beana: " + service.getClass().getName());
            System.out.println("Czy to PODKLASA 'NoInterfaceService'? " + (NoInterfaceService.class.isAssignableFrom(service.getClass())) + ", ale NIE ta sama klasa: " + (service.getClass() != NoInterfaceService.class));
            System.out.println("-> CGLIB wygenerowal PODKLASE NoInterfaceService (nazwa zawiera '$$SpringCGLIB$$') - BEZ interfejsu to JEDYNY mozliwy sposob na proxy.");
        }
    }
}
