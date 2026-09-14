package com.example.javaquest.platform.topics;

/**
 * Jeden temat z zakladki "Krytyczne" - lista tematow uznanych za krytyczne/wazne dla
 * rekrutacji na Java Developera, opracowana na podstawie raportu rynkowego (patrz
 * "kytyyczne tematy.md" w katalogu glownym repo).
 *
 * <p>{@code chapterSlug}/{@code lessonSlug} wskazuja lekcje kursu, ktora juz pokrywa dany
 * temat - {@code null}, jesli temat nie jest jeszcze opracowany w kursie (kandydat do
 * nowego rozdzialu). Gdy {@code lessonSlug} jest {@code null}, ale {@code chapterSlug}
 * nie jest, temat odpowiada calemu rozdzialowi (frontend linkuje wtedy do listy lekcji,
 * nie do konkretnej lekcji). {@code note} to opcjonalny komentarz wyjasniajacy czesciowe
 * pokrycie albo powod braku (widoczny w UI dla nieopracowanych/czesciowo opracowanych
 * tematow).
 */
public record CriticalTopic(
        String id,
        String title,
        String category,
        String priority,
        String chapterSlug,
        String lessonSlug,
        String note) {
}
