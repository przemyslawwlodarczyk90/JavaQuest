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

    private static final String REGISTRATION_TEMPLATE_PATH = "mail/registration-email.html";
    private static final String RESET_PASSWORD_TEMPLATE_PATH = "mail/reset-password-email.html";

    private final JavaMailSender mailSender;
    private final String fromAddress;
    private final String fromName;
    private final String registrationTemplate;
    private final String resetPasswordTemplate;

    MailService(JavaMailSender mailSender,
                @Value("${spring.mail.username:}") String fromAddress,
                @Value("${app.mail.from-name}") String fromName) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
        this.fromName = fromName;
        this.registrationTemplate = readTemplate(REGISTRATION_TEMPLATE_PATH);
        this.resetPasswordTemplate = readTemplate(RESET_PASSWORD_TEMPLATE_PATH);
    }

    private static String readTemplate(String path) {
        try {
            return new ClassPathResource(path).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Nie mozna wczytac szablonu maila " + path, e);
        }
    }

    void sendWelcomeEmail(String to, String firstName, String confirmationLink) {
        String html = registrationTemplate
                .replace("{{firstName}}", HtmlUtils.htmlEscape(firstName, "UTF-8"))
                .replace("{{confirmationLink}}", HtmlUtils.htmlEscape(confirmationLink, "UTF-8"));
        send(to, "Witaj w JavaQuest - potwierdź swoje konto", html);
    }

    void sendPasswordResetEmail(String to, String firstName, String resetLink) {
        String html = resetPasswordTemplate
                .replace("{{firstName}}", HtmlUtils.htmlEscape(firstName, "UTF-8"))
                .replace("{{resetLink}}", HtmlUtils.htmlEscape(resetLink, "UTF-8"));
        send(to, "JavaQuest - reset hasła", html);
    }

    private void send(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            if (!fromAddress.isBlank()) {
                helper.setFrom(fromAddress, fromName);
            }
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException | MailException | IOException e) {
            throw AuthException.mailDeliveryFailed(e);
        }
    }
}
