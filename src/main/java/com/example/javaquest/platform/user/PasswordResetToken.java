package com.example.javaquest.platform.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Jednorazowy token z linku w mailu resetu hasla - jego uzycie (z nowym haslem) zmienia haslo
 * uzytkownika. Osobna encja/tabela od {@link ConfirmationToken} (nie ten sam token na dwa cele) -
 * krotszy czas zycia (1h, nie 24h - reset hasla jest bardziej wrazliwy niz aktywacja konta) i wlasny
 * cykl zycia (usuwany przy kazdej nowej prosbie o reset, patrz AuthService.forgotPassword()).
 */
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected PasswordResetToken() {
        // wymagane przez JPA
    }

    public PasswordResetToken(String token, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public User getUser() {
        return user;
    }

    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }
}
