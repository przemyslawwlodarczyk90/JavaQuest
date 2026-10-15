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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Pelny import i zabezpieczone API; tylko odrebna H2 w pamieci, bez Dockera i poczty. */
@SpringBootTest(classes = JavaQuestApplication.class, properties = {
        "spring.datasource.url=jdbc:h2:mem:linux_course_api;DB_CLOSE_DELAY=-1",
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude="
                + "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,"
                + "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration"
})
@ActiveProfiles("h2")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class LinuxCourseApiRegressionTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder passwords;

    @Test
    void allLinuxLessonsMatchSourcesAndOtherTracksStillWork() throws Exception {
        String email = "linux-api-" + UUID.randomUUID() + "@example.invalid";
        String password = "Validation-Local-2026";
        User user = new User("Linux", "Validation", email, passwords.encode(password));
        user.setEnabled(true);
        users.save(user);
        String login = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(java.util.Map.of("email", email, "password", password))))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String token = mapper.readTree(login).path("token").asText();
        assertThat(token).isNotBlank();
        JsonNode chapters = read("/api/chapters?track=LINUX", token);
        assertThat(chapters.size()).isEqualTo(46);
        int checked = 0;
        for (JsonNode chapter : chapters) {
            String slug = chapter.path("slug").asText();
            JsonNode lessons = read("/api/chapters/" + slug + "/lessons", token);
            try (var files = Files.list(Path.of("src/main/resources/content", slug))) {
                assertThat(lessons.size()).isEqualTo((int) files.filter(p -> p.toString().endsWith(".json")).count());
            }
            assertThat(chapter.path("lessonCount").asInt()).isEqualTo(lessons.size());
            for (JsonNode lesson : lessons) {
                compare(slug, lesson.path("slug").asText(), token);
                checked++;
            }
        }
        assertThat(checked).isEqualTo(437);
        for (String track : new String[]{"JAVA", "JAVASCRIPT"}) {
            JsonNode chapter = read("/api/chapters?track=" + track, token).get(0);
            String slug = chapter.path("slug").asText();
            JsonNode lesson = read("/api/chapters/" + slug + "/lessons", token).get(0);
            compare(slug, lesson.path("slug").asText(), token);
        }
    }

    private void compare(String chapter, String lesson, String token) throws Exception {
        String resource = "content/" + chapter + "/" + lesson + ".json";
        JsonNode expected;
        try (var input = new ClassPathResource(resource).getInputStream()) {
            expected = mapper.readTree(input);
        }
        String route = "/api/chapters/" + chapter + "/lessons/" + lesson;
        for (String section : new String[]{"theory", "exercises"}) {
            JsonNode actual = read(route + "/" + section, token);
            JsonNode source = expected.path(section);
            assertThat(actual.size()).as(resource + " " + section).isEqualTo(source.size());
            String[] fields = section.equals("theory")
                    ? new String[]{"type", "heading", "body", "code"}
                    : new String[]{"prompt", "hint", "solution"};
            for (int i = 0; i < source.size(); i++) {
                for (String field : fields) {
                    assertThat(actual.get(i).path(field).asText(""))
                            .as(resource + " " + section + " " + i + " " + field)
                            .isEqualTo(source.get(i).path(field).asText(""));
                }
            }
        }
        JsonNode quiz = read(route + "/quiz", token);
        int count = expected.path("quiz").size();
        assertThat(quiz.path("questionCount").asInt()).as(resource).isEqualTo(count);
        assertThat(quiz.path("drawSize").asInt()).isEqualTo(Math.min(20, count));
        assertThat(quiz.path("passPercent").asInt()).isEqualTo(80);
    }

    private JsonNode read(String route, String token) throws Exception {
        String body = mvc.perform(get(route).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return mapper.readTree(body);
    }
}

