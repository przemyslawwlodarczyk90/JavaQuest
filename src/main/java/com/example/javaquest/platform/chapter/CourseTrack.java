package com.example.javaquest.platform.chapter;

/**
 * Ktory kurs platformy obejmuje dany rozdzial - JavaQuest od Etapu "drugi blok" (2026-09)
 * hostuje rownolegle kursy (Java, JavaScript, od 2026-09-26 Linux, a od 2026-10-20 przygotowanie
 * do certyfikatu OCP Java SE 21) pod tym samym
 * backendem/frontendem.
 * Domyslny, uruchamiany na starcie panelu glownego jest {@link #JAVA}.
 */
public enum CourseTrack {
    JAVA,
    JAVASCRIPT,
    LINUX,
    OCP
}
