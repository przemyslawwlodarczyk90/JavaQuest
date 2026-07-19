package com.example.javaquest._27_spring_test.Lesson13_TestingSecuredEndpoints;

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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class _Lesson13_TestingSecuredEndpoints {

    public static void main(String[] args) {

        // CALY projekt domyslnie WYLACZA auto-konfiguracje Spring Security w application.properties
        // (patrz CLAUDE.md, sekcja _24_spring_security) - musimy ja JAWNIE przywrocic PRZED
        // uruchomieniem testow tej lekcji, inaczej @WebMvcTest w ogole nie zobaczy SecurityFilterChain.
        System.setProperty("spring.autoconfigure.exclude", "");

        System.out.println("=== LEKCJA 13: Testowanie zabezpieczonych endpointow (spring-security-test) ===");

        /*
         * ============================================================
         * 📦 @WithMockUser - SYMULUJE zalogowanego uzytkownika BEZ prawdziwego logowania
         * ============================================================
         * Kursant ZNA JUZ Spring Security Z `_24_spring_security`
         * (`SecurityFilterChain`/role/`@PreAuthorize`). Testowanie
         * zabezpieczonych endpointow WYMAGALOBY normalnie PRAWDZIWEGO
         * logowania (formularz/Basic Auth/JWT) W KAZDYM tescie -
         * `@WithMockUser("nazwa")` (Z `spring-security-test`, CZESC
         * `spring-boot-starter-test` - Lesson01) SYMULUJE
         * UWIERZYTELNIONEGO uzytkownika BEZPOSREDNIO W kontekscie
         * bezpieczenstwa testu, BEZ zadnego PRAWDZIWEGO zadania
         * logowania.
         *
         * 🔍 `@WithMockUser(username = "...", roles = {"ADMIN"})` -
         * DEFINIUJE TEZ role - MockMvc "widzi" TAKIEGO uzytkownika
         * jako JUZ zalogowanego OD PIERWSZEGO zadania.
         */
        System.out.println("@WithMockUser = SYMULUJE zalogowanego uzytkownika W tescie MockMvc, BEZ prawdziwego logowania.");

        runAndReport(SecuredEndpointTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@WithMockUser` (BEZ argumentow) - domyslnie `username=
         *   "user"`, `roles={"USER"}`.
         * - `@WithMockUser(username = "admin", roles = {"ADMIN"})` -
         *   JAWNA nazwa I role.
         * - BRAK `@WithMockUser` NA metodzie testowej = ANONIMOWE
         *   zadanie (jak niezalogowany uzytkownik) - PRZYDATNE DO
         *   testowania 401/403.
         * - `@WithAnonymousUser` - JAWNE wymuszenie anonimowosci
         *   (NAWET jesli klasa MA `@WithMockUser` domyslnie).
         * - RÓZNICA WZGLEDEM `_24_spring_security/Lesson17`
         *   (kapszton): TAM logowanie bylo PRAWDZIWE (BCrypt+JWT
         *   PRZEZ prawdziwe HTTP), TU jest SYMULOWANE (SZYBSZE, DLA
         *   testow WARSTWY autoryzacji, NIE samego mechanizmu
         *   logowania).
         */
        System.clearProperty("spring.autoconfigure.exclude");
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    @RestController
    static class AdminController {
        @GetMapping("/public/info")
        String publicInfo() {
            return "informacja publiczna";
        }

        @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/admin/report")
        String adminReport() {
            return "raport poufny";
        }
    }

    @SpringBootApplication
    @EnableWebSecurity
    @org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
    static class TestApp {
        @org.springframework.context.annotation.Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/public/**").permitAll()
                            .anyRequest().authenticated())
                    .httpBasic(withDefaults());
            return http.build();
        }
    }

    @WebMvcTest(AdminController.class)
    @org.springframework.context.annotation.Import(TestApp.class)
    static class SecuredEndpointTest {
        @Autowired
        MockMvc mockMvc;

        @Test
        void publicEndpointIsAccessibleWithoutAuthentication() throws Exception {
            mockMvc.perform(get("/public/info")).andExpect(status().isOk());
            System.out.println("GET /public/info BEZ logowania -> 200 (permitAll).");
        }

        @Test
        void adminEndpointWithoutAuthenticationReturns401() throws Exception {
            mockMvc.perform(get("/admin/report")).andExpect(status().isUnauthorized());
            System.out.println("GET /admin/report BEZ logowania -> 401 (wymaga uwierzytelnienia).");
        }

        @Test
        @WithMockUser(username = "kasia", roles = "USER")
        void adminEndpointWithUserRoleReturns403() throws Exception {
            mockMvc.perform(get("/admin/report")).andExpect(status().isForbidden());
            System.out.println("GET /admin/report jako USER (@WithMockUser) -> 403 (brak roli ADMIN).");
        }

        @Test
        @WithMockUser(username = "dyrektor", roles = "ADMIN")
        void adminEndpointWithAdminRoleReturns200() throws Exception {
            mockMvc.perform(get("/admin/report")).andExpect(status().isOk());
            System.out.println("GET /admin/report jako ADMIN (@WithMockUser) -> 200.");
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
