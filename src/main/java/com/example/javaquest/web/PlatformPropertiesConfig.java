package com.example.javaquest.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Laduje konfiguracje platformy (baza, Hibernate, SMTP, JWT) z {@code javaquest-platform.properties}
 * oraz - jesli istnieje - z lokalnego, nieversjonowanego {@code javaquest-platform-secrets.properties}
 * (haslo SMTP, sekret JWT; drugi plik nadpisuje pierwszy).
 *
 * <p>Dlaczego {@code @PropertySource}, a nie globalny application.properties: ten ostatni jest
 * wspoldzielony z 31 rozdzialami kursu (patrz komentarz w application.properties), a klasa jest w
 * pakiecie "web", ktory skanuje WYLACZNIE {@link JavaQuestApplication} - lekcje jej nie zobacza.
 * {@code @PropertySource} ma nizszy priorytet niz application.properties/System properties/argumenty
 * CLI, wiec kazda wartosc mozna nadal nadpisac z linii polecen (np. {@code --spring.datasource.url=...}).
 */
@Configuration
@PropertySource(value = {
        "classpath:javaquest-platform.properties",
        "classpath:javaquest-platform-secrets.properties"
}, ignoreResourceNotFound = true)
class PlatformPropertiesConfig {
}
