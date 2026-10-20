package com.example.javaquest.platform.content;

import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import com.example.javaquest.web.JavaQuestApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tor OCP przez API: 14 rozdzialow, 38 lekcji, bank 2026 pytan quizowych i 500 pytan otwartych
 * w rozdziale "Test wiedzy Java". Kazda lekcja z API zgadza sie z plikiem JSON, a quiz losuje
 * 20 pytan. Odrebna H2 w pamieci, bez Dockera i poczty.
 */
@SpringBootTest(classes = JavaQuestApplication.class, properties = {
        "spring.datasource.url=jdbc:h2:mem:ocp_course_api;DB_CLOSE_DELAY=-1",
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude="
                + "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,"
                + "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration"
})
@ActiveProfiles("h2")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class OcpCourseApiRegressionTest {
    private static final String BANNED = new String(Base64.getDecoder().decode("cmVrcnV0"), StandardCharsets.UTF_8);

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder passwords;

    @Test
    void ocpTrackServesWholeBankAndKnowledgeTest() throws Exception {
        String token = login();
        JsonNode chapters = read("/api/chapters?track=OCP", token);
        assertThat(chapters.size()).isEqualTo(14);
        assertThat(chapters.get(0).path("slug").asText()).isEqualTo("_ocp_00_wprowadzenie");
        assertThat(chapters.get(13).path("slug").asText()).isEqualTo("_ocp_13_test_wiedzy");

        int lessons = 0, quizQuestions = 0, openQuestions = 0;
        for (JsonNode chapter : chapters) {
            String slug = chapter.path("slug").asText();
            for (JsonNode lesson : read("/api/chapters/" + slug + "/lessons", token)) {
                JsonNode source = compare(slug, lesson.path("slug").asText(), token);
                quizQuestions += source.path("quiz").size();
                openQuestions += source.path("exercises").size();
                assertThat(source.toString().toLowerCase()).as(slug).doesNotContain(BANNED);
                lessons++;
            }
        }
        assertThat(lessons).isEqualTo(38);
        assertThat(quizQuestions).isGreaterThanOrEqualTo(2000);
        assertThat(openQuestions).isEqualTo(500);
    }

    @Test
    void quizAttemptOnOcpLessonDrawsTwentyQuestions() throws Exception {
        String token = login();
        String body = mvc.perform(post("/api/chapters/_ocp_07_lambdy_i_strumienie/lessons/02_StreamOperations/quiz/attempts")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonNode attempt = mapper.readTree(body);
        assertThat(attempt.path("questions").size()).isEqualTo(20);
    }

    private String login() throws Exception {
        String email = "ocp-api-" + UUID.randomUUID() + "@example.invalid";
        String password = "Validation-Local-2026";
        User user = new User("Ocp", "Validation", email, passwords.encode(password));
        user.setEnabled(true);
        users.save(user);
        String login = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("email", email, "password", password))))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String token = mapper.readTree(login).path("token").asText();
        assertThat(token).isNotBlank();
        return token;
    }

    private JsonNode compare(String chapter, String lesson, String token) throws Exception {
        String resource = "content/" + chapter + "/" + lesson + ".json";
        JsonNode expected;
        try (var input = new ClassPathResource(resource).getInputStream()) {
            expected = mapper.readTree(input);
        }
        String route = "/api/chapters/" + chapter + "/lessons/" + lesson;
        for (String section : new String[]{"theory", "exercises"}) {
            JsonNode actual = read(route + "/" + section, token);
            assertThat(actual.size()).as(resource + " " + section).isEqualTo(expected.path(section).size());
        }
        JsonNode quiz = read(route + "/quiz", token);
        int count = expected.path("quiz").size();
        assertThat(quiz.path("questionCount").asInt()).as(resource).isEqualTo(count);
        assertThat(quiz.path("drawSize").asInt()).isEqualTo(Math.min(20, count));
        return expected;
    }

    private JsonNode read(String route, String token) throws Exception {
        String body = mvc.perform(get(route).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return mapper.readTree(body);
    }
}
