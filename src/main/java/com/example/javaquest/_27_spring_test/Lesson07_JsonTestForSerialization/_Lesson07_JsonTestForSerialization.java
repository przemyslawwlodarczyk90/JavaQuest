package com.example.javaquest._27_spring_test.Lesson07_JsonTestForSerialization;

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
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson07_JsonTestForSerialization {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: @JsonTest - testowanie serializacji JSON ===");

        /*
         * ============================================================
         * 📦 @JsonTest = NAJWEZSZY wycinek - TYLKO Jackson (BEZ kontrolerow/bazy/Web)
         * ============================================================
         * `@JsonTest` konfiguruje TYLKO `ObjectMapper`+ewentualne
         * WLASNE `JsonComponent`/`Serializer`/`Deserializer`
         * (powiazanie Z `_04_io/Lesson21_Jackson`) - NIE URUCHAMIA
         * ANI Web, ANI bazy - NAJSZYBSZY ZE WSZYSTKICH wycinkow.
         * `JacksonTester<T>` DAJE fluentne API DO serializacji/
         * deserializacji + asercji NA WYNIKU (`isEqualToJson`,
         * `hasJsonPathValue`).
         *
         * 🔍 UZYTECZNE, GDY masz WLASNE, niestandardowe
         * `@JsonComponent`/adnotacje Jacksona (`@JsonProperty`/
         * `@JsonIgnore`) I chcesz przetestowac SAM ksztalt JSON, BEZ
         * angazowania calej reszty aplikacji.
         */
        System.out.println("@JsonTest = NAJWEZSZY wycinek - TYLKO Jackson, BEZ Web/bazy. JacksonTester = fluentne API serializacji/deserializacji.");

        runAndReport(ProductJsonTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Autowired JacksonTester<Product> json;` - Spring Boot
         *   WSTRZYKUJE gotowy "tester" DLA WSKAZANEGO typu.
         * - `json.write(obj)` - serializuje DO `JsonContent<T>`
         *   (`.isEqualToJson("...")`/`.hasJsonPathValue("$.pole")`).
         * - `json.parse(tekst)` / `json.parseObject(tekst)` -
         *   deserializuje Z JSON DO obiektu Javy.
         * - PRZYDATNE DO testowania WLASNYCH `@JsonSerialize`/
         *   `@JsonDeserialize` (powiazanie Z `_04_io/Lesson21`) BEZ
         *   uruchamiania calego kontrolera (Lesson05).
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    record Product(String id, String name, double price) {
    }

    @SpringBootApplication
    static class TestApp {
    }

    @JsonTest
    static class ProductJsonTest {
        @Autowired
        JacksonTester<Product> json;

        @Test
        void serializesProductToExpectedJsonShape() throws Exception {
            Product product = new Product("P1", "Klawiatura", 199.99);

            var content = json.write(product);

            assertThat(content).extractingJsonPathStringValue("$.id").isEqualTo("P1");
            assertThat(content).extractingJsonPathStringValue("$.name").isEqualTo("Klawiatura");
            assertThat(content).extractingJsonPathNumberValue("$.price").isEqualTo(199.99);
            System.out.println("Serializacja Product -> JSON: " + content.getJson());
        }

        @Test
        void deserializesJsonBackToProductRecord() throws Exception {
            String rawJson = "{\"id\":\"P2\",\"name\":\"Mysz\",\"price\":49.99}";

            Product parsed = json.parseObject(rawJson);

            assertThat(parsed.id()).isEqualTo("P2");
            assertThat(parsed.name()).isEqualTo("Mysz");
            assertThat(parsed.price()).isEqualTo(49.99);
            System.out.println("Deserializacja JSON -> Product: " + parsed);
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
