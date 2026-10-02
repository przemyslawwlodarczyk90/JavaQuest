package com.example.javaquest.platform.mastery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.javaquest.platform.mastery.MasteryPolicy.LessonMastery;
import com.example.javaquest.platform.mastery.MasteryPolicy.State;
import org.junit.jupiter.api.Test;

/**
 * Czysta logika mastery - wszystkie chwile podane jawnie, zadnego realnego zegara.
 * Interwaly jak w domyslnej konfiguracji: spacing 20 h / 6 d / 30 d, retencja ★★ 30 d, ★★★ 60 d.
 */
class MasteryPolicyTest {

    private static final LocalDateTime T0 = LocalDateTime.of(2026, 1, 5, 20, 0);

    private final MasteryPolicy policy = new MasteryPolicy(new MasteryProperties(10,
            Duration.ofHours(20), Duration.ofDays(6), Duration.ofDays(30),
            Duration.ofDays(30), Duration.ofDays(60)));

    private static LocalDateTime hours(long h) {
        return T0.plusHours(h);
    }

    private static LocalDateTime days(long d) {
        return T0.plusDays(d);
    }

    private LessonMastery at(LocalDateTime now, LocalDateTime... passes) {
        return policy.evaluate(List.of(passes), now);
    }

    /** Sciezka do ★★★: dzien 0, nastepnego dnia, tydzien pozniej. Ostatnie potwierdzenie = dzien 8. */
    private static final LocalDateTime[] THREE_STARS = {T0, days(1), days(8)};

    @Test
    void noPassedAttemptsMeansZeroStars() {
        assertThat(at(T0)).isEqualTo(LessonMastery.NONE);
    }

    @Test
    void firstPassGivesOneStar() {
        LessonMastery m = at(hours(1), T0);
        assertThat(m.stars()).isEqualTo(1);
        assertThat(m.reviewSuggested()).isFalse();
    }

    @Test
    void passingAgainImmediatelyDoesNotGiveSecondStar() {
        assertThat(at(hours(2), T0, hours(1)).stars()).isEqualTo(1);
    }

    @Test
    void threePassesInOneDayStillGiveOneStar() {
        assertThat(at(hours(12), T0, hours(1), hours(2), hours(10)).stars()).isEqualTo(1);
    }

    @Test
    void secondStarAfterRequiredSpacing() {
        assertThat(at(hours(20), T0, hours(20)).stars()).isEqualTo(2);
        assertThat(at(hours(20), T0, hours(19)).stars()).isEqualTo(1);
    }

    @Test
    void thirdStarAfterLongerSpacing() {
        assertThat(at(days(8), THREE_STARS).stars()).isEqualTo(3);
        // ★★ w dniu 1, kolejne zaliczenie po 5 dniach - za wczesnie na ★★★
        assertThat(at(days(6), T0, days(1), days(6)).stars()).isEqualTo(2);
    }

    @Test
    void tooEarlyPassDoesNotMoveTheConfirmationClock() {
        // ★ w T0; zaliczenia co kilka godzin nie odsuwaja momentu, od ktorego liczy sie 20 h
        LocalDateTime[] passes = {T0, hours(5), hours(10), hours(15), hours(21)};
        assertThat(at(hours(21), passes).stars()).isEqualTo(2);
    }

    @Test
    void failedAttemptsAreNotEventsSoTheyChangeNothing() {
        // Niezaliczone podejscia w ogole nie trafiaja do polityki (filtruje je zapytanie) - stan zalezy
        // tylko od zaliczen, wiec "porazka" niczego nie odbiera.
        assertThat(at(days(3), T0, days(1)).stars()).isEqualTo(2);
    }

    @Test
    void threeStarsDecayToTwoAfterRetention() {
        assertThat(at(days(8 + 60), THREE_STARS).stars()).isEqualTo(3);
        assertThat(policy.evaluate(List.of(THREE_STARS), days(8 + 60).plusSeconds(1)).stars()).isEqualTo(2);
    }

    @Test
    void decayCascadesButNeverBelowOneStar() {
        assertThat(at(days(8 + 60 + 30), THREE_STARS).stars()).isEqualTo(2);
        assertThat(policy.evaluate(List.of(THREE_STARS), days(8 + 60 + 30).plusSeconds(1)).stars()).isEqualTo(1);
        assertThat(at(days(8 + 3650), THREE_STARS).stars()).isEqualTo(1);
    }

    @Test
    void decayIsFlaggedOnlyAfterLosingALevel() {
        assertThat(at(days(30), THREE_STARS).decayed()).isFalse();
        assertThat(at(days(70), THREE_STARS).decayed()).isTrue();
    }

    @Test
    void reviewAfterDecayRecoversTheLostStar() {
        List<LocalDateTime> passes = new ArrayList<>(List.of(THREE_STARS));
        assertThat(policy.evaluate(passes, days(70)).stars()).isEqualTo(2);
        passes.add(days(70));
        LessonMastery recovered = policy.evaluate(passes, days(70));
        assertThat(recovered.stars()).isEqualTo(3);
        assertThat(recovered.decayed()).isFalse();
        // i zegar starzenia liczy sie od powtorki
        assertThat(policy.evaluate(passes, days(70 + 60)).stars()).isEqualTo(3);
    }

    @Test
    void recoveryFromOneStarTakesTwoSpacedReviews() {
        List<LocalDateTime> passes = new ArrayList<>(List.of(THREE_STARS));
        passes.add(days(200)); // po spadku do ★ -> ★★
        assertThat(policy.evaluate(passes, days(200)).stars()).isEqualTo(2);
        passes.add(days(201)); // dzien pozniej: na ★★ potrzeba 6 dni
        assertThat(policy.evaluate(passes, days(201)).stars()).isEqualTo(2);
        passes.add(days(206));
        assertThat(policy.evaluate(passes, days(206)).stars()).isEqualTo(3);
    }

    @Test
    void refreshingThreeStarsResetsDecayOnlyAfterSpacing() {
        // odswiezenie po 20 dniach (< 30 d) nie liczy sie - spadek nadal po 60 dniach od dnia 8
        State early = policy.replay(List.of(T0, days(1), days(8), days(28)));
        assertThat(early.lastConfirmedAt()).isEqualTo(days(8));
        // odswiezenie po 30 dniach liczy sie - zegar od dnia 38
        State refreshed = policy.replay(List.of(T0, days(1), days(8), days(38)));
        assertThat(refreshed.level()).isEqualTo(3);
        assertThat(refreshed.lastConfirmedAt()).isEqualTo(days(38));
        assertThat(policy.effectiveLevel(refreshed, days(38 + 60))).isEqualTo(3);
    }

    @Test
    void reviewSuggestedOnlyWhenAPassWouldChangeSomething() {
        assertThat(at(hours(10), T0).reviewSuggested()).isFalse();
        assertThat(at(hours(20), T0).reviewSuggested()).isTrue();
        assertThat(at(days(10), THREE_STARS).reviewSuggested()).isFalse();
        assertThat(at(days(8 + 30), THREE_STARS).reviewSuggested()).isTrue();
        assertThat(at(days(80), THREE_STARS).reviewSuggested()).isTrue();
    }

    @Test
    void passOrderInInputDoesNotMatter() {
        assertThat(at(days(9), days(8), T0, days(1)).stars()).isEqualTo(3);
    }

    @Test
    void invalidConfigurationIsRejected() {
        assertThatThrownBy(() -> new MasteryProperties(10, Duration.ofHours(20), Duration.ofDays(6),
                Duration.ofDays(30), Duration.ofDays(5), Duration.ofDays(60)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new MasteryProperties(0, Duration.ofHours(20), Duration.ofDays(6),
                Duration.ofDays(30), Duration.ofDays(30), Duration.ofDays(60)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
