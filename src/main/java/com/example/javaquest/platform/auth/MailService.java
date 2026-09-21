package com.example.javaquest.platform.auth;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

/**
 * Wysyla mail powitalny z linkiem potwierdzajacym konto. Szablon to zwykly plik HTML z
 * placeholderami {@code {{nazwa}}} (bez Thymeleafa - ten starter dodany do wspolnego classpath
 * rejestrowalby ViewResolver w kontekstach WSZYSTKICH lekcji kursu, a tu potrzebujemy jednego maila).
 */
@Service
class MailService {

    private static final String TEMPLATE_PATH = "mail/registration-email.html";

    private final JavaMailSender mailSender;
    private final String fromAddress;
    private final String fromName;
    private final String template;

    MailService(JavaMailSender mailSender,
                @Value("${spring.mail.username:}") String fromAddress,
                @Value("${app.mail.from-name}") String fromName) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
        this.fromName = fromName;
        try {
            this.template = new ClassPathResource(TEMPLATE_PATH).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Nie mozna wczytac szablonu maila " + TEMPLATE_PATH, e);
        }
    }

    void sendWelcomeEmail(String to, String firstName, String confirmationLink) {
        String html = template
                .replace("{{firstName}}", HtmlUtils.htmlEscape(firstName))
                .replace("{{confirmationLink}}", HtmlUtils.htmlEscape(confirmationLink));
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            if (!fromAddress.isBlank()) {
                helper.setFrom(fromAddress, fromName);
            }
            helper.setTo(to);
            helper.setSubject("Witaj w JavaQuest - potwierdź swoje konto");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException | MailException | IOException e) {
            throw AuthException.mailDeliveryFailed(e);
        }
    }
}
