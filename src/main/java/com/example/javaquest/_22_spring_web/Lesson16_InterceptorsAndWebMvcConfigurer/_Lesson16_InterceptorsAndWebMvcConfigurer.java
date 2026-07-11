package com.example.javaquest._22_spring_web.Lesson16_InterceptorsAndWebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class _Lesson16_InterceptorsAndWebMvcConfigurer {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: Interceptory i WebMvcConfigurer ===");

        /*
         * ============================================================
         * 📦 HandlerInterceptor - PRZEKROJOWA LOGIKA NA POZIOMIE Spring MVC
         * ============================================================
         * `HandlerInterceptor` DZIALA na WYZSZYM poziomie niz Servlet
         * `Filter` (`_07_servlets/Lesson14_Filters`) - Filter widzi
         * TYLKO surowe `HttpServletRequest`/`Response`, PODCZAS gdy
         * Interceptor ma DOSTEP do `HandlerMethod` (KTORA konkretna
         * metoda kontrolera OBSLUZY zadanie) - przydatne DO logowania,
         * mierzenia czasu, autoryzacji NA POZIOMIE metody.
         *
         * 3 metody cyklu zycia:
         * - `preHandle(...)` - PRZED wywolaniem metody kontrolera
         *   (zwrot `false` = PRZERWANIE - kontroler NIGDY NIE zostanie
         *   wywolany).
         * - `postHandle(...)` - PO metodzie kontrolera, PRZED
         *   renderowaniem widoku (rzadko uzywane W REST API).
         * - `afterCompletion(...)` - PO CALKOWITYM zakonczeniu (WLACZNIE
         *   Z ewentualnym wyjatkiem) - idealne DO SPRZATANIA/logowania
         *   CZASU calkowitego.
         */
        System.out.println("HandlerInterceptor = przekrojowa logika NA POZIOMIE Spring MVC, Z DOSTEPEM do HandlerMethod (w odroznieniu od surowego Filter).");

        demonstrateInterceptorLifecycleOrder();
        demonstrateInterceptorShortCircuitsRequest();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `preHandle` zwracajace `false` = PRZERYWA lancuch - metoda
         *   kontrolera NIGDY nie zostaje wywolana (np. brak wymaganego
         *   naglowka autoryzacji).
         * - `WebMvcConfigurer.addInterceptors(...)` - rejestracja
         *   Z opcjonalnym `addPathPatterns(...)`/`excludePathPatterns(...)`
         *   (OGRANICZENIE zasiegu interceptora DO WYBRANYCH sciezek).
         * - Interceptor vs Filter: Interceptor jest CZESCIA Spring MVC
         *   (DOSTEP do `HandlerMethod`, kontekstu Springa), Filter jest
         *   CZESCIA specyfikacji Servlet (dziala NAWET BEZ Springa MVC,
         *   NIZEJ W lancuchu).
         * - `afterCompletion` dostaje EWENTUALNY wyjatek jako parametr -
         *   IDEALNE miejsce DO logowania CALKOWITEGO czasu zadania
         *   (sukces LUB blad).
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    static final List<String> EXECUTION_LOG = new ArrayList<>();

    static class TimingLoggingInterceptor implements HandlerInterceptor {
        private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            START_TIME.set(System.nanoTime());
            EXECUTION_LOG.add("preHandle: " + request.getRequestURI());
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) {
            EXECUTION_LOG.add("postHandle: " + request.getRequestURI());
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            long elapsedNanos = System.nanoTime() - START_TIME.get();
            EXECUTION_LOG.add("afterCompletion: " + request.getRequestURI() + " (" + (elapsedNanos / 1_000_000) + " ms)");
            START_TIME.remove();
        }
    }

    @RestController
    static class LifecycleController {
        @GetMapping("/ping")
        String ping() {
            EXECUTION_LOG.add("kontroler: obsluga /ping");
            return "pong";
        }
    }

    static class LifecycleWebConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new TimingLoggingInterceptor()).addPathPatterns("/**");
        }
    }

    @org.springframework.context.annotation.Configuration
    static class LifecycleConfig {
        @org.springframework.context.annotation.Bean
        WebMvcConfigurer lifecycleWebMvcConfigurer() {
            return new LifecycleWebConfig();
        }
    }

    @SpringBootApplication
    static class LifecycleApp {
    }

    private static void demonstrateInterceptorLifecycleOrder() throws Exception {
        System.out.println("\n=== KOLEJNOSC WYWOLAN: preHandle -> kontroler -> postHandle -> afterCompletion ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(LifecycleApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /ping -> " + httpGet(port, "/ping").body());
            EXECUTION_LOG.forEach(entry -> System.out.println("  " + entry));
        } finally {
            context.close();
            EXECUTION_LOG.clear();
        }
    }

    static class AuthGateInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws java.io.IOException {
            String apiKey = request.getHeader("X-Api-Key");
            if (apiKey == null || !apiKey.equals("tajny-klucz")) {
                // 'false' = PRZERWANIE - metoda kontrolera NIGDY nie zostanie wywolana.
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Brak lub nieprawidlowy X-Api-Key");
                return false;
            }
            return true;
        }
    }

    @RestController
    static class ProtectedController {
        @GetMapping("/admin/stats")
        String getStats() {
            return "Statystyki administracyjne";
        }
    }

    static class AuthGateWebConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new AuthGateInterceptor()).addPathPatterns("/admin/**");
        }
    }

    @org.springframework.context.annotation.Configuration
    static class AuthGateConfig {
        @org.springframework.context.annotation.Bean
        WebMvcConfigurer authGateWebMvcConfigurer() {
            return new AuthGateWebConfig();
        }
    }

    @SpringBootApplication
    static class AuthGateApp {
    }

    private static void demonstrateInterceptorShortCircuitsRequest() throws Exception {
        System.out.println("\n=== preHandle ZWRACAJACY false - PRZERWANIE PRZED wywolaniem kontrolera ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(AuthGateApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> unauthorized = httpGetWithApiKey(port, "/admin/stats", null);
            System.out.println("GET /admin/stats (BEZ X-Api-Key) -> status: " + unauthorized.statusCode() + " (oczekiwane: 401), body: " + unauthorized.body());

            HttpResponse<String> authorized = httpGetWithApiKey(port, "/admin/stats", "tajny-klucz");
            System.out.println("GET /admin/stats (X-Api-Key: tajny-klucz) -> status: " + authorized.statusCode() + " (oczekiwane: 200), body: " + authorized.body());
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> httpGetWithApiKey(int port, String path, String apiKey) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET();
        if (apiKey != null) {
            builder.header("X-Api-Key", apiKey);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
