package com.example.javaquest.platform.quiz;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class QuizRulesTest {

    @Test
    void requiredCorrectNeverDropsBelowPassPercent() {
        for (int total = 1; total <= 100; total++) {
            int required = QuizRules.requiredCorrect(total, 80);
            assertThat(required * 100.0 / total).as("total=%d", total).isGreaterThanOrEqualTo(80.0);
            assertThat((required - 1) * 100.0 / total).as("total=%d minus one", total).isLessThan(80.0);
        }
    }

    @Test
    void requiredCorrectForTypicalSizes() {
        assertThat(QuizRules.requiredCorrect(20, 80)).isEqualTo(16);
        assertThat(QuizRules.requiredCorrect(10, 80)).isEqualTo(8);
        assertThat(QuizRules.requiredCorrect(6, 80)).isEqualTo(5);
        assertThat(QuizRules.requiredCorrect(5, 80)).isEqualTo(4);
        assertThat(QuizRules.requiredCorrect(1, 80)).isEqualTo(1);
    }

    @Test
    void drawsExactlyDrawSizeDistinctQuestionsFromLargePool() {
        List<Integer> pool = IntStream.range(0, 100).boxed().toList();
        List<Integer> drawn = QuizRules.draw(pool, Set.of(), 20, new Random(1));
        assertThat(drawn).hasSize(20).doesNotHaveDuplicates().isSubsetOf(pool);
    }

    @Test
    void smallPoolReturnsAllQuestionsButReshufflesEveryTime() {
        List<Integer> pool = IntStream.range(0, 8).boxed().toList();
        Random random = new Random(42);
        Set<List<Integer>> orders = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            List<Integer> drawn = QuizRules.draw(pool, Set.of(), 20, random);
            assertThat(drawn).containsExactlyInAnyOrderElementsOf(pool);
            orders.add(drawn);
        }
        assertThat(orders.size()).as("rozne kolejnosci w 20 losowaniach").isGreaterThan(1);
    }

    @Test
    void retakePrefersQuestionsNotSeenBefore() {
        List<Integer> pool = IntStream.range(0, 100).boxed().toList();
        Set<Integer> seen = new HashSet<>(IntStream.range(0, 60).boxed().toList());
        List<Integer> drawn = QuizRules.draw(pool, seen, 20, new Random(7));
        assertThat(drawn).hasSize(20).allMatch(no -> no >= 60);
    }

    @Test
    void fillsFromSeenWhenNotEnoughUnseen() {
        List<Integer> pool = IntStream.range(0, 100).boxed().toList();
        Set<Integer> seen = new HashSet<>(IntStream.range(0, 95).boxed().toList());
        List<Integer> drawn = QuizRules.draw(pool, seen, 20, new Random(3));
        assertThat(drawn).hasSize(20).doesNotHaveDuplicates().contains(95, 96, 97, 98, 99);
    }

    @Test
    void previouslyWrongQuestionsComeBackUpToTheLimit() {
        List<Integer> pool = IntStream.range(0, 100).boxed().toList();
        Set<Integer> seen = new HashSet<>(IntStream.range(0, 20).boxed().toList());
        Set<Integer> wrong = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> drawn = QuizRules.draw(pool, seen, wrong, 20, 5, new Random(11));
        assertThat(drawn).hasSize(20).doesNotHaveDuplicates();
        assertThat(drawn.stream().filter(wrong::contains)).hasSize(5);
        // reszta to pytania jeszcze niewidziane
        assertThat(drawn.stream().filter(no -> !wrong.contains(no))).allMatch(no -> no >= 20);
    }

    @Test
    void wrongQuestionsMissingFromPoolAreIgnored() {
        List<Integer> pool = IntStream.range(0, 30).boxed().toList();
        List<Integer> drawn = QuizRules.draw(pool, Set.of(), Set.of(500, 501), 20, 5, new Random(5));
        assertThat(drawn).hasSize(20).doesNotHaveDuplicates().isSubsetOf(pool);
    }

    @Test
    void wrongQuestionsOverTheLimitStayInPoolAsSeen() {
        List<Integer> pool = IntStream.range(0, 10).boxed().toList();
        Set<Integer> wrong = new HashSet<>(pool);
        List<Integer> drawn = QuizRules.draw(pool, wrong, wrong, 20, 5, new Random(9));
        assertThat(drawn).containsExactlyInAnyOrderElementsOf(pool);
    }
}
