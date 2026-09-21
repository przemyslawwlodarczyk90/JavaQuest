package com.example.javaquest.platform.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** Czyste reguly quizu (bez Springa/bazy) - losowanie pytan i prog zaliczenia. */
final class QuizRules {

    private QuizRules() {
    }

    /**
     * Ile poprawnych odpowiedzi trzeba miec, zeby zdac: {@code ceil(passPercent% * total)}, czyli ZAWSZE
     * co najmniej {@code passPercent}% (nigdy mniej przez zaokraglenie w dol). Przy 80%: 20 pytan -> 16,
     * 10 -> 8, 6 -> 5 (83%, a nie 4 = 67%), 5 -> 4, 3 -> 3, 1 -> 1.
     */
    static int requiredCorrect(int total, int passPercent) {
        return (total * passPercent + 99) / 100;
    }

    /**
     * Losuje pytania do podejscia i zwraca ich numery w losowej kolejnosci.
     * <ul>
     *   <li>Pytan nie wiecej niz {@code drawSize} - bierzemy WSZYSTKIE, ale kazde podejscie ma nowa,
     *       przetasowana kolejnosc (zeby nie uczyc sie odpowiedzi "z pozycji").</li>
     *   <li>Wiecej pytan - losujemy {@code drawSize}, najpierw sposrod jeszcze NIEWIDZIANYCH przez
     *       uzytkownika ({@code seen}); gdy ich zabraknie, uzupelniamy losowo z juz widzianych.
     *       Powtorka po niezdanym quizie dostaje wiec w pierwszej kolejnosc nowe pytania.</li>
     * </ul>
     */
    static List<Integer> draw(List<Integer> allQuestionNos, Set<Integer> seen, int drawSize, Random random) {
        List<Integer> unseen = new ArrayList<>();
        List<Integer> alreadySeen = new ArrayList<>();
        for (Integer no : allQuestionNos) {
            (seen.contains(no) ? alreadySeen : unseen).add(no);
        }
        Collections.shuffle(unseen, random);
        Collections.shuffle(alreadySeen, random);

        List<Integer> picked = new ArrayList<>(unseen);
        picked.addAll(alreadySeen);
        picked = new ArrayList<>(picked.subList(0, Math.min(drawSize, picked.size())));
        // Kolejnosc wyswietlania jest osobna od kolejnosci "kogo wzielismy": niewidziane nie moga isc
        // zawsze na poczatku.
        Collections.shuffle(picked, random);
        return picked;
    }
}
