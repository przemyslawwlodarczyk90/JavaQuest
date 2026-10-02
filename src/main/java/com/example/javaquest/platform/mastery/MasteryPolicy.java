package com.example.javaquest.platform.mastery;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Czysta logika mastery (bez Springa, bazy i zegara) - patrz MASTERY_V1_PLAN.md, sekcja 7.
 *
 * <p>Mastery NIE jest nigdzie zapisywane: to funkcja historii zaliczonych podejsc do quizu
 * ({@link #evaluate}). Kazde zaliczone podejscie jest "zdarzeniem"; odtwarzamy je chronologicznie
 * ({@link #replay}), a starzenie liczymy przy odczycie ({@link #effectiveLevel}). Dzieki temu nie da
 * sie przyznac gwiazdki dwa razy, zmiana interwalow nie wymaga migracji danych i nie potrzebujemy
 * schedulera.
 */
public final class MasteryPolicy {

    public static final int MAX_LEVEL = 3;

    /** Stan po odtworzeniu zdarzen: poziom "zapisany" i czas ostatniego POLICZONEGO potwierdzenia. */
    public record State(int level, LocalDateTime lastConfirmedAt) {
        public static final State NONE = new State(0, null);
    }

    /**
     * Wynik dla uzytkownika w chwili {@code now}: liczba gwiazdek i czy powtorka cos teraz da.
     * {@code decayed} - poziom spadl przez uplyw czasu (uzywane tylko do kolejnosci powtorek).
     */
    public record LessonMastery(int stars, boolean reviewSuggested, boolean decayed) {
        public static final LessonMastery NONE = new LessonMastery(0, false, false);
    }

    private final MasteryProperties properties;

    public MasteryPolicy(MasteryProperties properties) {
        this.properties = properties;
    }

    /** Mastery z czasow zaliczonych podejsc (dowolna kolejnosc) w chwili {@code now}. */
    public LessonMastery evaluate(List<LocalDateTime> passedAt, LocalDateTime now) {
        State state = replay(passedAt);
        if (state.level() == 0) {
            return LessonMastery.NONE;
        }
        int effective = effectiveLevel(state, now);
        return new LessonMastery(effective, reviewSuggested(state, now), effective < state.level());
    }

    /** Odtwarza zdarzenia w kolejnosci chronologicznej. */
    public State replay(List<LocalDateTime> passedAt) {
        State state = State.NONE;
        for (LocalDateTime at : passedAt.stream().sorted().toList()) {
            state = apply(state, at);
        }
        return state;
    }

    /**
     * Jedno zaliczone podejscie w chwili {@code at}. Zaliczenie "za wczesnie" niczego nie zmienia -
     * TAKZE nie przesuwa {@code lastConfirmedAt}, inaczej powtarzanie quizu co godzine odsuwaloby
     * w nieskonczonosc moment zdobycia kolejnej gwiazdki.
     */
    public State apply(State state, LocalDateTime at) {
        int effective = effectiveLevel(state, at);
        if (effective == 0) {
            return new State(1, at);
        }
        if (Duration.between(state.lastConfirmedAt(), at).compareTo(properties.spacing(effective)) >= 0) {
            return new State(Math.min(MAX_LEVEL, effective + 1), at);
        }
        return state;
    }

    /**
     * Poziom po starzeniu: poziom L utrzymuje sie przez {@code retention(L)} od ostatniego
     * potwierdzenia, potem spada o jeden i kolejny poziom "zyje" od chwili spadku. Poziom 1 to podloga.
     */
    public int effectiveLevel(State state, LocalDateTime now) {
        if (state.level() == 0) {
            return 0;
        }
        Duration age = Duration.between(state.lastConfirmedAt(), now);
        int level = state.level();
        while (level >= 2 && age.compareTo(properties.retention(level)) > 0) {
            age = age.minus(properties.retention(level));
            level--;
        }
        return level;
    }

    /** Czy zaliczenie quizu TERAZ zmieniloby stan (nowa gwiazdka, odzyskanie albo odswiezenie 3 gwiazdek). */
    public boolean reviewSuggested(State state, LocalDateTime now) {
        int effective = effectiveLevel(state, now);
        return effective >= 1
                && Duration.between(state.lastConfirmedAt(), now).compareTo(properties.spacing(effective)) >= 0;
    }
}
