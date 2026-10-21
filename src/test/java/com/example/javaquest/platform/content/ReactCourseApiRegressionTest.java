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
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Tor React: rozdzialy i lekcje z ChapterSeedData zgadzaja sie z plikami tresci. */
@SpringBootTest(classes = JavaQuestApplication.class, properties = {
        "spring.datasource.url=jdbc:h2:mem:react_course_api;DB_CLOSE_DELAY=-1",
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude="
                + "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,"
                + "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration"
})
@ActiveProfiles("h2")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class ReactCourseApiRegressionTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder passwords;

    @Test
    void reactTrackServesAllChaptersAndLessons() throws Exception {
        String email = "react-api-" + UUID.randomUUID() + "@example.invalid";
        String password = "Validation-Local-2026";
        User user = new User("React", "Validation", email, passwords.encode(password));
        user.setEnabled(true);
        users.save(user);
        String login = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(java.util.Map.of("email", email, "password", password))))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String token = mapper.readTree(login).path("token").asText();

        JsonNode chapters = read("/api/chapters?track=REACT", token);
        assertThat(chapters.size()).isEqualTo(24);
        assertThat(chapters.get(0).path("slug").asText()).isEqualTo("_react_00_wprowadzenie");
        int lessons = 0;
        for (JsonNode chapter : chapters) {
            String slug = chapter.path("slug").asText();
            assertThat(slug).startsWith("_react_");
            JsonNode list = read("/api/chapters/" + slug + "/lessons", token);
            try (var files = Files.list(Path.of("src/main/resources/content", slug))) {
                assertThat(list.size()).as(slug).isEqualTo((int) files.filter(p -> p.toString().endsWith(".json")).count());
            }
            assertThat(chapter.path("lessonCount").asInt()).isEqualTo(list.size());
            lessons += list.size();
        }
        assertThat(lessons).isEqualTo(174);
    }

    private JsonNode read(String url, String token) throws Exception {
        String body = mvc.perform(get(url).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return mapper.readTree(body);
    }
}
