package com.example.javaquest._27_spring_test.Lesson05_WebMvcTestAndMockMvc;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.PrintWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class _Lesson05_WebMvcTestAndMockMvc {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: @WebMvcTest i MockMvc ===");

        /*
         * ============================================================
         * 📦 @WebMvcTest = TYLKO warstwa Spring MVC - BEZ prawdziwego serwera (jak MOCK z Lesson03)
         * ============================================================
         * `@WebMvcTest(KlasaKontrolera.class)` LADUJE TYLKO
         * infrastrukture Spring MVC (`DispatcherServlet`,
         * `@ControllerAdvice`, konwertery JSON) DLA WSKAZANEGO
         * kontrolera - BEZ warstwy serwisowej/repozytorium/bazy
         * (Lesson04). Zaleznosci kontrolera (np. serwis) MUSZA byc
         * DOSTARCZONE PRZEZ `@MockitoBean` (Lesson08 pogłębi TEN
         * temat).
         *
         * 🔍 `MockMvc` SYMULUJE zadania HTTP BEZ prawdziwego portu
         * TCP (podobnie jak `webEnvironment=MOCK` Z Lesson03) - PRZEZ
         * DispatcherServlet W PAMIECI - SZYBSZE NIZ prawdziwy serwer
         * (`RANDOM_PORT`), ALE WCIAZ testuje PELNY potok Spring MVC
         * (routing/walidacja/serializacja).
         */
        System.out.println("@WebMvcTest = TYLKO Spring MVC (BEZ bazy/serwisow realnych). MockMvc = SYMULOWANE zadania HTTP, BEZ prawdziwego portu.");

        runAndReport(UserControllerWebMvcTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@WebMvcTest(UserController.class)` - WSKAZUJE
         *   KONKRETNY kontroler (OPCJONALNE - BEZ argumentu LADUJE
         *   WSZYSTKIE `@Controller`).
         * - `MockMvc.perform(get("/sciezka"))` - SYMULUJE zadanie GET.
         * - `.andExpect(status().isOk())` - asercja NA kod statusu.
         * - `.andExpect(jsonPath("$.pole").value(...))` - asercja NA
         *   POLE W odpowiedzi JSON (biblioteka JsonPath - CZESC
         *   `spring-boot-starter-test`, Lesson01).
         * - `@MockitoBean` (Lesson08) DOSTARCZA zamockowana zaleznosc
         *   (serwis) - kontroler DZIALA NAPRAWDE, serwis jest
         *   ZASTAPIONY.
         * - `MockMvcTester` (Spring Framework 6.2+) - NOWOCZESNA,
         *   AssertJ-owa alternatywa DLA `MockMvc` - pokazana JAKO
         *   DRUGI styl W tej samej lekcji.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    // @WebMvcTest (jak @SpringBootTest) SZUKA klasy @SpringBootConfiguration W PAKIECIE testu
    // (i pakietach NADRZEDNYCH) - w tym kursie kazda lekcja zyje we WLASNYM pakiecie, wiec
    // potrzebuje WLASNEGO "kotwicznego" punktu konfiguracji. MUSI to byc @SpringBootApplication
    // (NIE samo @SpringBootConfiguration) - bare @SpringBootConfiguration NIE niesie ze soba
    // @ComponentScan, wiec DispatcherServlet W OGOLE nie znajdowal UserController (404 zamiast
    // 200 - zweryfikowane empirycznie). Ten plik ma TYLKO 1 scenariusz/1 kontroler, wiec
    // implicit component-scan (znana pulapka z _20_spring_core przy WIELU scenariuszach W 1 pliku)
    // tu NIE WYSTEPUJE - bezpiecznie uzywamy @SpringBootApplication.
    @SpringBootApplication
    static class TestApp {
    }

    interface UserService {
        Map<String, Object> findUserById(String id);
    }

    @RestController
    static class UserController {
        private final UserService userService;

        UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/users/{id}")
        Map<String, Object> getUser(@PathVariable String id) {
            return userService.findUserById(id);
        }
    }

    @WebMvcTest(UserController.class)
    static class UserControllerWebMvcTest {
        @Autowired
        MockMvc mockMvc;

        @Autowired
        org.springframework.web.context.WebApplicationContext webApplicationContext;

        @MockitoBean
        UserService userService;

        @Test
        void getUserReturnsJsonFromMockedServiceThroughRealMvcPipeline() throws Exception {
            when(userService.findUserById("U1")).thenReturn(Map.of("id", "U1", "name", "Kursant"));

            mockMvc.perform(get("/users/U1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value("U1"))
                    .andExpect(jsonPath("$.name").value("Kursant"));

            System.out.println("MockMvc: GET /users/U1 -> 200, JSON zawiera id/name z ZAMOCKOWANEGO serwisu (kontroler+MVC pipeline SA prawdziwe).");
        }

        @Test
        void mockMvcTesterProvidesFluentAssertJStyleAssertions() {
            when(userService.findUserById("U2")).thenReturn(Map.of("id", "U2", "name", "Druga Osoba"));
            MockMvcTester mvcTester = MockMvcTester.from(webApplicationContext);

            assertThat(mvcTester.get().uri("/users/U2"))
                    .hasStatusOk()
                    .bodyJson().extractingPath("$.name").isEqualTo("Druga Osoba");

            System.out.println("MockMvcTester (styl AssertJ): GET /users/U2 -> status OK, pole 'name' zweryfikowane fluent API.");
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
