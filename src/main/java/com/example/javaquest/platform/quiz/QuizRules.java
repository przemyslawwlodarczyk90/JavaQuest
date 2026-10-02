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

    /** Losowanie bez pytan "do poprawy" - patrz {@link #draw(List, Set, Set, int, int, Random)}. */
    static List<Integer> draw(List<Integer> allQuestionNos, Set<Integer> seen, int drawSize, Random random) {
        return draw(allQuestionNos, seen, Set.of(), drawSize, 0, random);
    }

    /**
     * Losuje pytania do podejscia i zwraca ich numery w losowej kolejnosci.
     * <ul>
     *   <li>Najpierw do {@code retryWrongMax} pytan "do poprawy" ({@code wrong} - ostatnia odpowiedz
     *       uzytkownika byla bledna). Limit pilnuje, zeby quiz nie zamienil sie w "same moje bledy".</li>
     *   <li>Reszta: najpierw sposrod jeszcze NIEWIDZIANYCH przez uzytkownika ({@code seen}); gdy ich
     *       zabraknie, uzupelniamy losowo z juz widzianych. Powtorka dostaje wiec glownie nowe pytania.</li>
     *   <li>Pytan nie wiecej niz {@code drawSize} - bierzemy WSZYSTKIE, ale kazde podejscie ma nowa,
     *       przetasowana kolejnosc (zeby nie uczyc sie odpowiedzi "z pozycji").</li>
     * </ul>
     */
    static List<Integer> draw(List<Integer> allQuestionNos, Set<Integer> seen, Set<Integer> wrong, int drawSize,
                              int retryWrongMax, Random random) {
        List<Integer> retry = new ArrayList<>();
        List<Integer> unseen = new ArrayList<>();
        List<Integer> alreadySeen = new ArrayList<>();
        for (Integer no : allQuestionNos) {
            if (wrong.contains(no)) {
                retry.add(no);
            } else {
                (seen.contains(no) ? alreadySeen : unseen).add(no);
            }
        }
        Collections.shuffle(retry, random);
        Collections.shuffle(unseen, random);
        Collections.shuffle(alreadySeen, random);

        int retryCount = Math.min(Math.min(retryWrongMax, drawSize), retry.size());
        List<Integer> picked = new ArrayList<>(retry.subList(0, retryCount));
        // Bledne, ktore nie zmiescily sie w limicie, wracaja do puli jako zwykle "widziane".
        alreadySeen.addAll(retry.subList(retryCount, retry.size()));
        Collections.shuffle(alreadySeen, random);
        picked.addAll(unseen);
        picked.addAll(alreadySeen);
        picked = new ArrayList<>(picked.subList(0, Math.min(drawSize, picked.size())));
        // Kolejnosc wyswietlania jest osobna od kolejnosci "kogo wzielismy": pytania do poprawy
        // i niewidziane nie moga isc zawsze na poczatku.
        Collections.shuffle(picked, random);
        return picked;
    }
}
