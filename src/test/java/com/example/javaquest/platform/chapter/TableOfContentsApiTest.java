package com.example.javaquest.platform.chapter;

import com.example.javaquest.web.JavaQuestApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Spis tresci toru: publiczny, jednym zapytaniem, z polskimi tytulami lekcji w kolejnosci kursu. */
@SpringBootTest(classes = JavaQuestApplication.class, properties = {
        "spring.datasource.url=jdbc:h2:mem:toc_api;DB_CLOSE_DELAY=-1",
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude="
                + "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,"
                + "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration"
})
@ActiveProfiles("h2")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
class TableOfContentsApiTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @Test
    void returnsWholeTrackWithPolishLessonTitlesWithoutLogin() throws Exception {
        JsonNode toc = read("/api/chapters?track=OCP&withLessons=true");
        assertThat(toc.size()).isEqualTo(15);
        assertThat(toc.get(0).path("slug").asText()).isEqualTo("_ocp_00_wprowadzenie");
        assertThat(toc.get(0).path("lessons").get(0).path("title").asText()).isEqualTo("Przegląd egzaminu");
        int lessons = 0;
        for (JsonNode chapter : toc) lessons += chapter.path("lessons").size();
        assertThat(lessons).isEqualTo(39);

        JsonNode java = read("/api/chapters?track=JAVA&withLessons=true");
        assertThat(java.get(0).path("lessons").get(1).path("title").asText()).isEqualTo("Zmienne");
    }

    @Test
    void plainChapterListKeepsItsShape() throws Exception {
        JsonNode chapters = read("/api/chapters?track=OCP");
        assertThat(chapters.get(0).has("lessonCount")).isTrue();
        assertThat(chapters.get(0).has("lessons")).isFalse();
    }

    private JsonNode read(String url) throws Exception {
        String body = mvc.perform(get(url)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return mapper.readTree(body);
    }
}
