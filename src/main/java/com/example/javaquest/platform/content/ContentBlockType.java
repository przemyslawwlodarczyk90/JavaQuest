package com.example.javaquest.platform.content;

/**
 * Typ bloku teorii lekcji - kazdy typ renderowany jest innym "kaflem" po stronie frontendu
 * (patrz {@code TheoryView.jsx}), zeby notatka byla wizualna, nie jedna sciana tekstu.
 *
 * <p><b>Etap 2 platformy (2026-09):</b> enum rozszerzony o typy odpowiadajace obowiazkowej
 * strukturze 11 sekcji lekcji (patrz {@code STAGE2_LESSON_REDESIGN_PROMPT.md}). Stare typy
 * ({@link #CONCEPT}, {@link #CODE_EXAMPLE}, {@link #DIAGRAM}) sa ZACHOWANE jako alias/legacy -
 * uzywaja ich WSZYSTKIE lekcje jeszcze niezmigrowane do nowego standardu (migracja przebiega
 * rozdzialami, patrz WORK_PROGRESS.md) - nie usuwac, dopoki cala tresc nie zostanie przepisana.
 */
public enum ContentBlockType {
    // --- Legacy (Faza 1) - zachowane dla lekcji jeszcze niezmigrowanych ---

    /** LEGACY: krotkie, konkretne wyjasnienie pojecia (Faza 1). Nowe lekcje: {@link #DEFINITION}. */
    CONCEPT,
    /** Fragment kodu Java z komentarzem (Faza 1, bez rozroznienia podstawowy/praktyczny). */
    CODE_EXAMPLE,
    /** LEGACY: prosty schemat/diagram (Faza 1). Nowe lekcje: {@link #VISUAL_EXAMPLE}. */
    DIAGRAM,

    // --- Standard Etapu 2 (11 obowiazkowych sekcji lekcji) ---

    /** 1. Wprowadzenie - czego dotyczy lekcja, dlaczego jest wazna, z czym sie laczy. */
    INTRO,
    /** 2. Prosta definicja - czym cos JEST, a czym NIE JEST (jesli czeste nieporozumienie). */
    DEFINITION,
    /** 3. Analogia - latwy do wyobrazenia obraz pomagajacy zrozumiec, bez zastepowania wyjasnienia technicznego. */
    ANALOGY,
    /** 4. Przyklad wizualny - relacje/hierarchia/przeplyw pokazane jako schemat (np. "Vehicle -> Car -> ElectricCar"). */
    VISUAL_EXAMPLE,
    /** 5. Pierwsza wersja kodu - najprostszy przyklad pokazujacy sam mechanizm. */
    CODE_BASIC,
    /** 6. Druga wersja kodu - praktyczny przypadek przypominajacy prawdziwy projekt. */
    CODE_PRACTICAL,
    /** 7. Wyjasnienie dzialania krok po kroku (co sie dzieje, w jakiej kolejnosci, co zwraca). */
    STEP_BY_STEP,
    /** 8. Praktyczne zastosowanie - po co to programiscie, gdzie spotka to w prawdziwym projekcie. */
    USAGE,
    /** Wazna informacja/uwaga (nie blad) - wyroznione zastrzezenie techniczne warte podkreslenia. */
    NOTE,
    /** 9. Typowe bledy i pulapki - opis problemu (czesto razem z parą {@link #CODE_WRONG}/{@link #CODE_RIGHT}). */
    PITFALL,
    /** Blednie napisany kod (czesc pulapki) - WYRAZNIE oznaczony, zeby nie pomylic z zalecanym rozwiazaniem. */
    CODE_WRONG,
    /** Poprawiona wersja kodu z pulapki (czesc pulapki, nastepuje po {@link #CODE_WRONG}). */
    CODE_RIGHT,
    /** 10. Kiedy uzywac danego rozwiazania, a kiedy nie - zalety/ograniczenia/alternatywy. */
    WHEN_TO_USE,
    /** 11. Podsumowanie - najwazniejsze informacje, bez powtarzania calej lekcji. */
    SUMMARY
}
