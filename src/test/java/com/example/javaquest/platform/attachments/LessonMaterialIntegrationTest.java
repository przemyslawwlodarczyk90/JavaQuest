package com.example.javaquest.platform.attachments;

import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import com.example.javaquest.web.JavaQuestApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes=JavaQuestApplication.class, properties={
        "spring.datasource.url=jdbc:h2:mem:lesson_materials;DB_CLOSE_DELAY=-1",
        "spring.flyway.enabled=false",
        "platform.attachments.max-bytes=32",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,"
                +"org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,"
                +"org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration"
})
@ActiveProfiles("h2")
@AutoConfigureMockMvc(print=MockMvcPrint.NONE)
class LessonMaterialIntegrationTest {
    static final String BASE="/api/chapters/_01_fundamentals/lessons/00_JavaPlatformBasics";
    static final String OTHER="/api/chapters/_01_fundamentals/lessons/01_Variables";
    @TempDir static Path directory;
    @DynamicPropertySource static void storage(DynamicPropertyRegistry properties) {
        properties.add("platform.attachments.directory",()->directory.toString());
    }
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder passwords;
    @Autowired LessonMaterialRepository materials;
    @Autowired LessonMaterialService service;
    @Autowired PlatformTransactionManager transactions;
    @Autowired org.springframework.context.ApplicationContext context;
    @Autowired com.example.javaquest.platform.chapter.LessonRepository lessons;
    @Autowired org.springframework.jdbc.core.JdbcTemplate jdbc;

    @Test void materialsSurviveTheStartupContentRefresh() throws Exception {
        String alice = login(), bob = login();
        var lesson = lessons.findByChapterSlugAndSlug("_01_fundamentals", "00_JavaPlatformBasics").orElseThrow();
        Long lessonId = lesson.getId();
        long lessonCount = lessons.count();
        byte[] bytes = "notatki po restarcie".getBytes(StandardCharsets.UTF_8);
        JsonNode link = read(mvc.perform(post(BASE + "/links").header("Authorization", auth(alice))
                .contentType(MediaType.APPLICATION_JSON).content(body("Trwały link", "https://example.org/restart")))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8));
        JsonNode attachment = read(mvc.perform(multipart(BASE + "/attachments")
                .file(new MockMultipartFile("file", "notatki.txt", "text/plain", bytes))
                .header("Authorization", auth(alice))).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8));

        // The same initialization sequence as on a subsequent application startup.
        org.springframework.test.util.ReflectionTestUtils.invokeMethod(context.getBean("contentReset"), "reset");
        jdbc.update("update lessons set title = ? where id = ?", "Stary tytuł", lessonId);
        org.springframework.test.util.ReflectionTestUtils.invokeMethod(context.getBean("contentSeeder"), "seed");
        org.springframework.test.util.ReflectionTestUtils.invokeMethod(context.getBean("contentBootstrap"), "init");

        var restored = lessons.findByChapterSlugAndSlug("_01_fundamentals", "00_JavaPlatformBasics").orElseThrow();
        assertThat(restored.getId()).isEqualTo(lessonId);
        assertThat(restored.getTitle()).isEqualTo(lesson.getTitle());
        assertThat(lessons.count()).isEqualTo(lessonCount);
        mvc.perform(get(BASE + "/materials").header("Authorization", auth(alice)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
        mvc.perform(get(attachment.path("url").asText()).header("Authorization", auth(alice)))
                .andExpect(status().isOk()).andExpect(content().bytes(bytes));
        mvc.perform(get(attachment.path("url").asText()).header("Authorization", auth(bob)))
                .andExpect(status().isNotFound());
        mvc.perform(get(BASE + "/theory").header("Authorization", auth(alice)))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].type").exists());
        mvc.perform(put(BASE + "/links/" + link.path("id").asText()).header("Authorization", auth(alice))
                .contentType(MediaType.APPLICATION_JSON).content(body("Po restarcie", "https://example.org/updated")))
                .andExpect(status().isOk());
        mvc.perform(delete(BASE + "/attachments/" + attachment.path("id").asText())
                .header("Authorization", auth(alice))).andExpect(status().isNoContent());
        mvc.perform(delete(BASE + "/links/" + link.path("id").asText())
                .header("Authorization", auth(alice))).andExpect(status().isNoContent());
    }

    String login() throws Exception {
        String email="materials-"+UUID.randomUUID()+"@example.invalid";
        User user=new User("Material","Test",email,passwords.encode("Validation-Local-2026"));
        user.setEnabled(true);
        users.save(user);
        return read(mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(Map.of("email",email,"password","Validation-Local-2026"))))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8))
                .path("token").asText();
    }
    JsonNode read(String value) throws Exception { return json.readTree(value); }
    String body(String title,String url) throws Exception { return json.writeValueAsString(Map.of("title",title,"url",url)); }
    String auth(String token) { return "Bearer "+token; }

    @Test void linksArePrivateAndHaveFullCrud() throws Exception {
        String alice=login(),bob=login();
        JsonNode item=read(mvc.perform(post(BASE+"/links").header("Authorization",auth(alice))
                .contentType(MediaType.APPLICATION_JSON).content(body("Artykuł","https://example.org/article")))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8));
        String route=BASE+"/links/"+item.path("id").asText();
        mvc.perform(get(BASE+"/materials").header("Authorization",auth(alice)))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].title").value("Artykuł"));
        mvc.perform(get(BASE+"/materials").header("Authorization",auth(bob)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
        mvc.perform(put(route).header("Authorization",auth(bob)).contentType(MediaType.APPLICATION_JSON)
                .content(body("Cudzy","https://example.org"))).andExpect(status().isNotFound());
        mvc.perform(delete(route).header("Authorization",auth(bob))).andExpect(status().isNotFound());
        mvc.perform(delete(OTHER+"/links/"+item.path("id").asText()).header("Authorization",auth(alice)))
                .andExpect(status().isNotFound());
        mvc.perform(put(route).header("Authorization",auth(alice)).contentType(MediaType.APPLICATION_JSON)
                .content(body("Film","https://example.org/video"))).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Film")).andExpect(jsonPath("$.url").value("https://example.org/video"));
        mvc.perform(delete(route).header("Authorization",auth(alice))).andExpect(status().isNoContent());
        mvc.perform(get(BASE+"/materials").header("Authorization",auth(alice)))
                .andExpect(jsonPath("$.length()").value(0));
    }
    @Test void attachmentsAreOnDiskAndOnlyOwnerCanDownloadOrDelete() throws Exception {
        String alice=login(),bob=login();
        byte[] bytes="private bytes".getBytes(StandardCharsets.UTF_8);
        JsonNode item=read(mvc.perform(multipart(BASE+"/attachments")
                .file(new MockMultipartFile("file","../../notatki.html","text/html",bytes))
                .header("Authorization",auth(alice))).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8));
        UUID id=UUID.fromString(item.path("id").asText());
        String url=item.path("url").asText(),route=BASE+"/attachments/"+id;
        assertThat(item.path("title").asText()).isEqualTo("notatki.html");
        assertThat(Files.readAllBytes(directory.resolve(id.toString()))).isEqualTo(bytes);
        assertThat(materials.findById(id).orElseThrow().url).isEqualTo(url);
        mvc.perform(get(url)).andExpect(status().isUnauthorized());
        mvc.perform(get(url).header("Authorization",auth(bob))).andExpect(status().isNotFound());
        mvc.perform(delete(route).header("Authorization",auth(bob))).andExpect(status().isNotFound());
        mvc.perform(get(url).header("Authorization",auth(alice))).andExpect(status().isOk())
                .andExpect(header().string("Content-Type","application/octet-stream"))
                .andExpect(header().string("X-Content-Type-Options","nosniff"))
                .andExpect(header().string("Cache-Control","private, no-store"))
                .andExpect(content().bytes(bytes));
        mvc.perform(delete(OTHER+"/attachments/"+id).header("Authorization",auth(alice))).andExpect(status().isNotFound());
        mvc.perform(delete(route).header("Authorization",auth(alice))).andExpect(status().isNoContent());
        assertThat(Files.exists(directory.resolve(id.toString()))).isFalse();
        assertThat(materials.findById(id)).isEmpty();
        mvc.perform(get(url).header("Authorization",auth(alice))).andExpect(status().isNotFound());
    }
    @Test void invalidLinksAndUploadsDoNotCreateFilesOrRecords() throws Exception {
        String token=login();
        for(String url:new String[]{"javascript:alert(1)","file:///etc/passwd","https://user:secret@example.org","relative"}) {
            mvc.perform(post(BASE+"/links").header("Authorization",auth(token)).contentType(MediaType.APPLICATION_JSON)
                    .content(body("Tytuł",url))).andExpect(status().isBadRequest());
        }
        mvc.perform(post(BASE+"/links").header("Authorization",auth(token)).contentType(MediaType.APPLICATION_JSON)
                .content(body(" ","https://example.org"))).andExpect(status().isBadRequest());
        mvc.perform(multipart(BASE+"/attachments").file(new MockMultipartFile("file","big.txt","text/plain",new byte[33]))
                .header("Authorization",auth(token))).andExpect(status().isPayloadTooLarge());
        mvc.perform(multipart(BASE+"/attachments").file(new MockMultipartFile("file","empty.txt","text/plain",new byte[0]))
                .header("Authorization",auth(token))).andExpect(status().isBadRequest());
        mvc.perform(multipart("/api/chapters/missing/lessons/missing/attachments")
                .file(new MockMultipartFile("file","small.txt","text/plain",new byte[1]))
                .header("Authorization",auth(token))).andExpect(status().isNotFound());
        mvc.perform(get(BASE+"/materials").header("Authorization",auth(token))).andExpect(jsonPath("$.length()").value(0));
        try(var files=Files.list(directory)) { assertThat(files.count()).isZero(); }
        mvc.perform(get(BASE+"/materials")).andExpect(status().isUnauthorized());
    }

    @Test void rollbackRemovesNewFilesAndPreservesFilesPendingDeletion() throws Exception {
        String email="rollback-"+UUID.randomUUID()+"@example.invalid";
        User owner=new User("Rollback","Test",email,passwords.encode("Validation-Local-2026"));
        owner.setEnabled(true);
        users.save(owner);
        TransactionTemplate tx=new TransactionTemplate(transactions);
        LessonMaterialService.View rolledBack=tx.execute(status -> {
            try {
                var item=service.upload(email,"_01_fundamentals","00_JavaPlatformBasics",
                        new MockMultipartFile("file","rollback.txt","text/plain",new byte[]{1,2}));
                assertThat(Files.exists(directory.resolve(item.id().toString()))).isTrue();
                status.setRollbackOnly();
                return item;
            } catch(java.io.IOException e) { throw new java.io.UncheckedIOException(e); }
        });
        assertThat(rolledBack).isNotNull();
        assertThat(materials.findById(rolledBack.id())).isEmpty();
        assertThat(Files.exists(directory.resolve(rolledBack.id().toString()))).isFalse();
        var saved=service.upload(email,"_01_fundamentals","00_JavaPlatformBasics",
                new MockMultipartFile("file","keep.txt","text/plain",new byte[]{3}));
        tx.executeWithoutResult(status -> {
            service.delete(email,"_01_fundamentals","00_JavaPlatformBasics",saved.id(),LessonMaterial.Kind.ATTACHMENT);
            status.setRollbackOnly();
        });
        assertThat(materials.findById(saved.id())).isPresent();
        assertThat(Files.exists(directory.resolve(saved.id().toString()))).isTrue();
        service.delete(email,"_01_fundamentals","00_JavaPlatformBasics",saved.id(),LessonMaterial.Kind.ATTACHMENT);
        assertThat(Files.exists(directory.resolve(saved.id().toString()))).isFalse();
    }
}

