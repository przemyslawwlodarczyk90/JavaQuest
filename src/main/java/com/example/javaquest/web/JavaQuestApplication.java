package com.example.javaquest.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Prawdziwy, dzialajacy punkt wejscia aplikacji JavaQuest - serwuje frontend React
 * zbudowany w katalogu "frontend/" (ktorego "npm run build" laduje wynik do
 * src/main/resources/static) oraz wlasne REST API (patrz {@link HelloController}).
 *
 * <p>UMIESZCZONA CELOWO w podpakiecie "com.example.javaquest.web", NIE w pakiecie
 * najwyzszego poziomu "com.example.javaquest" - domyslny component-scan/auto-
 * konfiguracja {@code @SpringBootApplication} obejmuje TYLKO pakiet klasy, w ktorej
 * jest zadeklarowana, i jego podpakiety. Gdyby ta klasa zyla w "com.example.javaquest"
 * (jak w oryginalnym szablonie Spring Initializr), skanowalaby WSZYSTKIE 31 rozdzialow
 * kursu naraz - a te sa PELNE wlasnych, izolowanych klas @Configuration/@Component/
 * @Entity zaprojektowanych do dzialania KAZDA W WLASNYM, ODDZIELNYM kontekscie Springa
 * (uruchamianym z main() danej lekcji), nie w jednym wspolnym kontekscie. Zweryfikowane
 * empirycznie: proba uruchomienia z pakietu najwyzszego poziomu konczyla sie
 * natychmiastowym BeanDefinitionOverrideException (dwie rozne lekcje _20_spring_core
 * definiuja bean o nazwie "engine") - a to byl dopiero PIERWSZY z wielu podobnych
 * konfliktow, ktore napotkalby dalszy skan (m.in. kolidujace nazwy encji JPA
 * @Entity(name = "Book")/@Entity(name = "Task") powtarzane celowo w wielu lekcjach
 * _12_hibernate/_23_spring_data_jpa).
 */
@SpringBootApplication
public class JavaQuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaQuestApplication.class, args);
    }

}
