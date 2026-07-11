package com.example.javaquest._20_spring_core.Lesson05_ConfigurationStylesXmlVsJavaVsAnnotations;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

public class _Lesson05_ConfigurationStylesXmlVsJavaVsAnnotations {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: STYLE KONFIGURACJI - XML vs ADNOTACJE vs JAVA CONFIG ===");

        /*
         * ============================================================
         * 📦 TA SAMA IDEA, TRZY EPOKI SPRINGA
         * ============================================================
         * Lesson02 zapowiedział tabelę mechanizmów, które ZMIENIŁY SIĘ
         * między wersjami — TU jest pierwszy, najbardziej fundamentalny
         * z nich: JAK w ogóle POWIEDZIEĆ kontenerowi "stwórz mi ten
         * bean". Historia Springa to 3 kolejne style, KAŻDY wciąż
         * TECHNICZNIE działający dziś, ale tylko JEDEN jest aktualnym
         * standardem. Dzisiaj zobaczysz WSZYSTKIE TRZY, tworzące
         * KONCEPCYJNIE ten sam bean, obok siebie.
         */
        System.out.println("3 style konfiguracji Springa: XML (najstarszy) -> adnotacje+component-scan -> Java Config (obecny standard w Spring Boot).");

        demonstrateXmlConfigurationStyle();
        demonstrateAnnotationComponentScanStyle();
        demonstrateJavaConfigStyle();

        /*
         * ============================================================
         * 🔹 XML — SPRING 1.x-2.x (2004-2007), DZIŚ PRAKTYCZNIE WYMARŁE
         * ============================================================
         * `<beans><bean id="..." class="...">` — CAŁA konfiguracja w
         * osobnym pliku XML, oddzielona od kodu Javy. Zalety: zmiana
         * konfiguracji BEZ rekompilacji. Wady: BRAK bezpieczeństwa
         * typów (literówka w nazwie klasy = błąd dopiero w RUNTIME),
         * rozwlekłość, 2 miejsca do utrzymania (kod + XML). MOŻESZ to
         * spotkać w BARDZO starym, legacy kodzie korporacyjnym — kod z
         * demo wyżej pokazuje, że kontener WCIĄŻ to obsługuje (`XmlBean
         * DefinitionReader` nie został usunięty), ale Spring Boot NIGDY
         * nie generuje ani nie oczekuje takiej konfiguracji.
         *
         * 🔹 ADNOTACJE + COMPONENT-SCAN — MAINSTREAM OD SPRING 2.5/3.0
         * ============================================================
         * `@Component`+`@ComponentScan` — Ty OZNACZASZ klasę adnotacją
         * WEWNĄTRZ niej samej, kontener SAM ją znajduje, przeszukując
         * pakiety. Zaleta: konfiguracja "przy" kodzie, mniej rozwlekłości
         * niż XML. Wada: mniejsza kontrola (nie widzisz w JEDNYM miejscu
         * WSZYSTKICH beanów naraz) — dobre dla WŁASNYCH klas.
         *
         * 🔹 JAVA CONFIG — DOMYŚLNY I JEDYNY STYL W SPRING BOOT
         * ============================================================
         * `@Configuration`+`@Bean` na METODZIE — pełne bezpieczeństwo
         * typów (to zwykły kod Javy, kompilator złapie literówkę),
         * pełna kontrola (jawnie widzisz WSZYSTKIE beany zdefiniowane w
         * klasie konfiguracyjnej). NIEZBĘDNE dla klas, których NIE
         * jesteś właścicielem kodu (biblioteki zewnętrzne — nie możesz
         * dodać `@Component` do cudzej klasy) — pogłębienie w Lesson09.
         */
        System.out.println("\nXML (Spring 1.x-2.x, dziś wymarłe) -> adnotacje (mainstream od 2.5/3.0, dla WŁASNYCH klas) -> Java Config (JEDYNY styl w Boot, też dla klas zewnętrznych).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wszystkie 3 style TWORZĄ beany w TYM SAMYM kontenerze —
         *   kontener NIE ROZRÓŻNIA, skąd bean pochodzi.
         * - XML — dziś praktycznie martwe, ale WCIĄŻ TECHNICZNIE
         *   działa (zobaczysz je tylko w bardzo starym kodzie).
         * - Adnotacje (`@Component`) — dla TWOICH WŁASNYCH klas.
         * - Java Config (`@Configuration`+`@Bean`) — DOMYŚLNY styl w
         *   Spring Boot, JEDYNA opcja dla klas z zewnętrznych bibliotek.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    static class XmlStyleGreeter {
        String greet() {
            return "Witaj ze STYLU XML (Spring 1.x-2.x)";
        }
    }

    private static void demonstrateXmlConfigurationStyle() {
        System.out.println("\n=== STYL 1: XML (<bean id=\"...\" class=\"...\"/>) ===");

        String xmlConfig = """
                <?xml version="1.0" encoding="UTF-8"?>
                <beans xmlns="http://www.springframework.org/schema/beans"
                       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">
                    <bean id="xmlGreeter" class="com.example.javaquest._20_spring_core.Lesson05_ConfigurationStylesXmlVsJavaVsAnnotations._Lesson05_ConfigurationStylesXmlVsJavaVsAnnotations$XmlStyleGreeter"/>
                </beans>
                """;

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(factory);
        xmlReader.loadBeanDefinitions(new ByteArrayResource(xmlConfig.getBytes(StandardCharsets.UTF_8)));

        XmlStyleGreeter greeter = factory.getBean("xmlGreeter", XmlStyleGreeter.class);
        System.out.println(greeter.greet());
        System.out.println("-> nazwa klasy jest STRINGIEM w pliku XML - literowka zlapalbys DOPIERO przy uruchomieniu, nie w kompilacji.");
    }

    @Component
    static class AnnotationStyleGreeter {
        String greet() {
            return "Witaj ze STYLU ADNOTACJI (@Component, mainstream od Spring 2.5/3.0)";
        }
    }

    @Configuration
    @ComponentScan
    static class AnnotationStyleConfig {
    }

    private static void demonstrateAnnotationComponentScanStyle() {
        System.out.println("\n=== STYL 2: ADNOTACJE + COMPONENT-SCAN (@Component) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationStyleConfig.class)) {
            AnnotationStyleGreeter greeter = context.getBean(AnnotationStyleGreeter.class);
            System.out.println(greeter.greet());
            System.out.println("-> adnotacja jest WEWNATRZ klasy - kontener SAM ja znajduje, przeszukujac caly pakiet.");
        }
    }

    // CELOWO bez @Component - ta klasa jest zarejestrowana JAWNIE w AppConfig ponizej,
    // symulujac klase z biblioteki ZEWNETRZNEJ (ktorej kodu NIE jestes wlascicielem).
    static class JavaConfigStyleGreeter {
        String greet() {
            return "Witaj ze STYLU JAVA CONFIG (@Configuration+@Bean, jedyny styl w Spring Boot)";
        }
    }

    @Configuration
    static class JavaConfigStyleAppConfig {
        @Bean
        JavaConfigStyleGreeter javaConfigStyleGreeter() {
            return new JavaConfigStyleGreeter();
        }
    }

    private static void demonstrateJavaConfigStyle() {
        System.out.println("\n=== STYL 3: JAVA CONFIG (@Configuration + @Bean NA METODZIE) ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfigStyleAppConfig.class)) {
            JavaConfigStyleGreeter greeter = context.getBean(JavaConfigStyleGreeter.class);
            System.out.println(greeter.greet());
            System.out.println("-> JavaConfigStyleGreeter NIE MA @Component (jak klasa z cudzej biblioteki) - Ty JAWNIE tworzysz jego instancje w metodzie @Bean.");
            System.out.println("   Pelne bezpieczenstwo typow (to zwykly kod Javy) - literowke zlapalby KOMPILATOR, nie runtime.");
        }
    }
}
