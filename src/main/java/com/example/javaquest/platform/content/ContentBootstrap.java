package com.example.javaquest.platform.content;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Uruchamia {@link LessonContentLoader#load()} podczas startu aplikacji, PRZED otwarciem portu
 * HTTP.
 *
 * <p>Dlaczego osobna klasa, a nie {@code @PostConstruct} bezposrednio na {@code LessonContentLoader}:
 * {@code load()} jest {@code @Transactional}, a adnotacja ta dziala tylko przy wywolaniu PRZEZ
 * proxy AOP beana - samo-wywolanie {@code @PostConstruct} na tym samym obiekcie omija proxy
 * (Spring tworzy proxy transakcyjne dopiero PO zakonczeniu wlasnej inicjalizacji beana). Ten
 * bootstrap dostaje w konstruktorze JUZ GOTOWY, opakowany proxy {@code LessonContentLoader} (Spring
 * zawsze zwraca finalny, w pelni zainicjalizowany bean przy wstrzykiwaniu zaleznosci) i wywoluje
 * {@code load()} z zewnatrz, wiec transakcja faktycznie sie zaczyna.
 *
 * <p>{@code @DependsOn("contentSeeder")} gwarantuje, ze
 * {@link com.example.javaquest.platform.chapter.ContentSeeder} (ktory zasila baze rozdzialami i
 * lekcjami) juz zakonczyl swoj {@code @PostConstruct}, zanim {@code load()} zacznie czytac
 * {@code lessonRepository.findAll()}.
 *
 * <p>Celowo {@code @PostConstruct}, NIE {@code ApplicationRunner} - wbudowany serwer (Tomcat)
 * zaczyna przyjmowac polaczenia w {@code finishRefresh()}, ktore nastepuje PO instancjonowaniu
 * wszystkich singletonow (a wiec i po {@code @PostConstruct}), ale PRZED wywolaniem jakiegokolwiek
 * {@code ApplicationRunner}/{@code CommandLineRunner}. Uzycie {@code ApplicationRunner} dawalo
 * realne, widoczne dla uzytkownika opoznienie ("lekcja w przygotowaniu" mimo ze backend juz
 * "wystartowal") - przy duzej liczbie lekcji ladowanie tresci trwalo kilkanascie-kilkadziesiat
 * sekund PO otwarciu portu. {@code @PostConstruct} gwarantuje pelne zaladowanie tresci PRZED
 * przyjeciem pierwszego zadania.
 */
@Component
@DependsOn("contentSeeder")
class ContentBootstrap {

    private final LessonContentLoader lessonContentLoader;

    ContentBootstrap(LessonContentLoader lessonContentLoader) {
        this.lessonContentLoader = lessonContentLoader;
    }

    @PostConstruct
    void init() throws IOException {
        lessonContentLoader.load();
    }
}
