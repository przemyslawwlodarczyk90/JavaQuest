package com.example.javaquest._20_spring_core.Lesson17_SpelBasics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class _Lesson17_SpelBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: SPRING EXPRESSION LANGUAGE (SpEL) ===");

        /*
         * ============================================================
         * 📦 SpEL = MALY JEZYK WYRAZEN WEWNATRZ ADNOTACJI
         * ============================================================
         * Widziales `${klucz}` w `@Value` (Lesson16) - to PLACEHOLDER
         * wlasciwosci, NIE SpEL. SpEL to ODDZIELNY, POTEZNIEJSZY
         * mechanizm - wyrazenia w skladni `#{...}` moga wywolywac
         * METODY, ODWOLYWAC SIE do INNYCH beanow, robic ARYTMETYKE,
         * warunki (`? :`) - TO JEST prawdziwy, mini-jezyk programowania,
         * NIE tylko podstawienie tekstu. Poznajesz go TERAZ (raz), zeby
         * pozniej rozpoznac IDENTYCZNA skladnie w wyrazeniach
         * `@PreAuthorize` w `_24_spring_security/Lesson10`.
         */
        System.out.println("${klucz} (Lesson16) = podstawienie WARTOSCI. #{wyrazenie} (SpEL) = MALY JEZYK - wywolania metod, warunki, odwolania do beanow.");

        demonstrateSpelArithmeticAndLiterals();
        demonstrateSpelReferencingAnotherBean();
        demonstrateSpelInValueAnnotation();
        demonstrateSpelTernaryAndSafeNavigation();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SpEL (`#{...}`) to ODDZIELNY mechanizm od `${...}` (Lesson16) -
         *   prawdziwy jezyk wyrazen, NIE tylko podstawienie wartosci.
         * - Mozna go uzywac STANDALONE (przez `ExpressionParser`, jak w
         *   demo) LUB wewnatrz adnotacji (`@Value("#{...}")`).
         * - SpEL POZWALA odwolywac sie do INNYCH beanow (`@nazwaBeana`),
         *   wywolywac ich METODY, robic operator trojkowy (`?:`) i
         *   "bezpieczna nawigacje" (`?.`) chroniaca przed NPE.
         * - TA SAMA skladnia wraca w `@PreAuthorize`/`@PostAuthorize`
         *   (`_24_spring_security/Lesson10`) - inwestycja w zrozumienie
         *   SpEL TERAZ oplaci sie tam.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static void demonstrateSpelArithmeticAndLiterals() {
        System.out.println("\n=== SpEL STANDALONE: LITERALY, ARYTMETYKA, LANCUCHY METOD ===");

        ExpressionParser parser = new SpelExpressionParser();

        Expression mathExpression = parser.parseExpression("10 * (2 + 3)");
        System.out.println("'10 * (2 + 3)' = " + mathExpression.getValue());

        Expression stringExpression = parser.parseExpression("'Ala ma kota'.toUpperCase()");
        System.out.println("''Ala ma kota'.toUpperCase()' = " + stringExpression.getValue());

        Expression lengthExpression = parser.parseExpression("'JavaQuest'.length()");
        System.out.println("''JavaQuest'.length()' = " + lengthExpression.getValue());
        System.out.println("-> SpEL wywoluje PRAWDZIWE metody Javy (String.toUpperCase()/.length()) - to NIE jest zwykle podstawienie tekstu.");
    }

    // PUBLIC klasa I metoda - SpEL (ReflectiveMethodResolver) przeszukuje TYLKO PUBLICZNE metody
    // (Class.getMethods()), wiec pakietowa widocznosc (domyslna w reszcie tego kursu) NIE
    // WYSTARCZY tutaj - to kolejna, warta zapamietania pulapka SpEL.
    public static class DiscountPolicy {
        public double discountFor(double amount) {
            return amount > 100 ? amount * 0.1 : 0.0;
        }
    }

    private static void demonstrateSpelReferencingAnotherBean() {
        System.out.println("\n=== SpEL ODWOLUJACY SIE DO METODY WLASNEGO OBIEKTU (StandardEvaluationContext) ===");

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable("policy", new DiscountPolicy());

        Expression discountExpression = parser.parseExpression("#policy.discountFor(250.0)");
        Object result = discountExpression.getValue(evaluationContext);
        System.out.println("'#policy.discountFor(250.0)' = " + result);
        System.out.println("-> SpEL wywolal METODE na obiekcie Javy przekazanym przez zmienna '#policy' - dokladnie TAK dziala '@nazwaBeana' w kontekscie Springa.");
    }

    static class PricingConfig {
        @Value("#{10 * 5}")
        private int computedAtStartup;

        @Value("#{systemProperties['os.name']}")
        private String operatingSystem;

        int getComputedAtStartup() {
            return computedAtStartup;
        }

        String getOperatingSystem() {
            return operatingSystem;
        }
    }

    @Configuration
    static class SpelValueConfig {
        @Bean
        PricingConfig pricingConfig() {
            return new PricingConfig();
        }
    }

    private static void demonstrateSpelInValueAnnotation() {
        System.out.println("\n=== SpEL WEWNATRZ @Value (#{...}) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpelValueConfig.class)) {
            PricingConfig config = context.getBean(PricingConfig.class);
            System.out.println("computedAtStartup (z '#{10 * 5}'): " + config.getComputedAtStartup());
            System.out.println("operatingSystem (z '#{systemProperties['os.name']}'): " + config.getOperatingSystem());
            System.out.println("-> '#{...}' w @Value jest WYLICZANE PRZY STARCIE kontekstu, NIE 'na sztywno' wpisane w kodzie.");
        }
    }

    private static void demonstrateSpelTernaryAndSafeNavigation() {
        System.out.println("\n=== OPERATOR TROJKOWY I 'BEZPIECZNA NAWIGACJA' (?.) W SpEL ===");

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

        Expression ternaryExpression = parser.parseExpression("15 > 10 ? 'DUZO' : 'MALO'");
        System.out.println("'15 > 10 ? \\'DUZO\\' : \\'MALO\\'' = " + ternaryExpression.getValue());

        evaluationContext.setVariable("maybeNullPolicy", null);
        Expression safeNavigationExpression = parser.parseExpression("#maybeNullPolicy?.discountFor(100.0)");
        Object safeResult = safeNavigationExpression.getValue(evaluationContext);
        System.out.println("'#maybeNullPolicy?.discountFor(100.0)' (obiekt jest null) = " + safeResult + " (BEZ NullPointerException!)");
        System.out.println("-> '?.' to 'bezpieczna nawigacja' - jesli obiekt PRZED nia jest null, CALE wyrazenie zwraca null, ZAMIAST rzucac wyjatek.");
    }
}
