package com.example.javaquest.platform.mastery;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * JEDYNE miejsce z parametrami mastery (patrz MASTERY_V1_PLAN.md, sekcja 8). Wartosci domyslne sa
 * tutaj, a nadpisuje sie je w {@code javaquest-platform.properties} ({@code platform.mastery.*}).
 *
 * <ul>
 *   <li>{@code spacingLevelN} - minimalny odstep od ostatniego POLICZONEGO potwierdzenia, po ktorym
 *       zaliczony quiz na poziomie N podnosi poziom (N=3: odswieza zegar starzenia, zostaje 3).</li>
 *   <li>{@code retentionLevelN} - jak dlugo poziom N utrzymuje sie bez potwierdzenia, zanim spadnie
 *       o jeden. Poziom 1 nie ma retencji - jest podloga (raz zaliczona lekcja nie wraca do zera).</li>
 *   <li>{@code minQuestions} - podejscie z mniejsza liczba pytan nie jest zdarzeniem mastery
 *       (za latwo zgadnac/zapamietac).</li>
 * </ul>
 *
 * Bledna konfiguracja (np. retencja krotsza niz spacing - uczen tracilby gwiazdke, zanim w ogole
 * moglby ja potwierdzic) zatrzymuje start aplikacji zamiast po cichu psuc model.
 */
@ConfigurationProperties("platform.mastery")
public record MasteryProperties(
        @DefaultValue("10") int minQuestions,
        @DefaultValue("20h") Duration spacingLevel1,
        @DefaultValue("6d") Duration spacingLevel2,
        @DefaultValue("30d") Duration spacingLevel3,
        @DefaultValue("30d") Duration retentionLevel2,
        @DefaultValue("60d") Duration retentionLevel3) {

    public MasteryProperties {
        requirePositive("spacing-level1", spacingLevel1);
        requirePositive("spacing-level2", spacingLevel2);
        requirePositive("spacing-level3", spacingLevel3);
        requirePositive("retention-level2", retentionLevel2);
        requirePositive("retention-level3", retentionLevel3);
        if (minQuestions < 1) {
            throw new IllegalArgumentException("platform.mastery.min-questions musi byc >= 1");
        }
        if (retentionLevel2.compareTo(spacingLevel2) <= 0 || retentionLevel3.compareTo(spacingLevel3) <= 0) {
            throw new IllegalArgumentException(
                    "platform.mastery: retencja poziomu musi byc dluzsza niz odstep potrzebny do jego potwierdzenia");
        }
    }

    /** Odstep potrzebny, zeby zaliczenie na poziomie {@code level} (1..3) zostalo policzone. */
    public Duration spacing(int level) {
        return switch (level) {
            case 1 -> spacingLevel1;
            case 2 -> spacingLevel2;
            case 3 -> spacingLevel3;
            default -> throw new IllegalArgumentException("Brak spacingu dla poziomu " + level);
        };
    }

    /** Ile utrzymuje sie poziom {@code level} (2..3) bez potwierdzenia. */
    public Duration retention(int level) {
        return switch (level) {
            case 2 -> retentionLevel2;
            case 3 -> retentionLevel3;
            default -> throw new IllegalArgumentException("Brak retencji dla poziomu " + level);
        };
    }

    private static void requirePositive(String name, Duration value) {
        if (value == null || value.isZero() || value.isNegative()) {
            throw new IllegalArgumentException("platform.mastery." + name + " musi byc dodatnie");
        }
    }
}
