package com.example.javaquest._28_java_evolution.Lesson05_Java8NewDateTimeApi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson05_Java8NewDateTimeApi {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: Java 8 (2014) - nowe API daty i czasu (java.time) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_01_fundamentals/Lesson07_DatesAndTimes`
         * ============================================================
         * PRZED Java 8: `java.util.Date`+`java.util.Calendar` (OD
         * Javy 1.0/1.1!) BYLY OSLAWIONE ZLYM designem - `Date` byl
         * MUTOWALNY (NIEBEZPIECZNY W WATKACH), miesiace LICZONE OD
         * ZERA (STYCZEN=0!), lata OD 1900 (`new Date(126, 0, 1)` =
         * 2026!). Java 8 wprowadzila `java.time` (JSR-310, autorstwa
         * TWORCY biblioteki Joda-Time) - NIEZMIENNE (immutable, ta
         * SAMA idea CO `_14_advancedjava/Lesson24`), CZYTELNE,
         * WATKOBEZPIECZNE klasy.
         *
         * 🔍 TA lekcja NIE POWTARZA API (`_01_fundamentals/Lesson07`
         * JUZ uczyl `LocalDate`/`LocalDateTime`/`Duration`
         * SZCZEGOLOWO) - TU demo POKAZUJE DOKLADNIE, JAK ZLY byl
         * STARY `Date`.
         */
        System.out.println("java.time (JSR-310, Java 8) ZASTAPIL fatalny design java.util.Date/Calendar. Pelna teoria: _01_fundamentals/Lesson07.");

        demonstrateOldDateProblems();
        demonstrateNewApiSolvesThem();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `java.util.Date` - miesiace OD ZERA, lata OD 1900,
         *   MUTOWALNY, BEZ stref czasowych W SAMEJ klasie - ZRODLO
         *   niezliczonych bledow.
         * - `java.time.LocalDate`/`LocalDateTime`/`Instant`/`Duration`/
         *   `Period` - NIEZMIENNE, CZYTELNE (`LocalDate.of(2026, 1, 1)`,
         *   STYCZEN=1, NIE 0!), THREAD-SAFE.
         * - KAZDA operacja (`plusDays(...)`) ZWRACA NOWY obiekt
         *   (NIGDY NIE modyfikuje ISTNIEJACEGO).
         * - POGLEBIONA teoria: `_01_fundamentals/Lesson07`.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    @SuppressWarnings("deprecation")
    private static void demonstrateOldDateProblems() {
        System.out.println("\n--- PRZED Java 8: java.util.Date - OSLAWIONY zly design ---");

        // Konstruktor (rok, MIESIAC, dzien) - rok LICZONY OD 1900, MIESIAC LICZONY OD ZERA!
        Date oldStyleDate = new Date(126, 0, 1); // "126" = 2026, "0" = STYCZEN (nie luty!).
        System.out.println("new Date(126, 0, 1) OZNACZA: " + oldStyleDate + " (rok=126+1900=2026, miesiac=0=STYCZEN - MYLACE!).");

        // Date jest MUTOWALNY - "dzielenie sie" obiektem Date MIEDZY metodami jest NIEBEZPIECZNE.
        Date mutableDate = new Date();
        long beforeMutation = mutableDate.getTime();
        mutableDate.setTime(0L); // KTOKOLWIEK MA referencje DO tego obiektu, WIDZI TA zmiane.
        System.out.println("Date jest MUTOWALNY - setTime(0) ZMIENIL obiekt 'w miejscu' (was: " + beforeMutation + ", now: " + mutableDate.getTime() + ").");
    }

    private static void demonstrateNewApiSolvesThem() {
        System.out.println("\n--- PO Java 8: java.time - CZYTELNE, NIEZMIENNE ---");

        // STYCZEN = 1 (NIE 0!), rok PODAJEMY WPROST (NIE +1900).
        LocalDate newStyleDate = LocalDate.of(2026, 1, 1);
        System.out.println("LocalDate.of(2026, 1, 1) OZNACZA: " + newStyleDate + " (CZYTELNE, BEZ przeliczen).");
        assertThat(newStyleDate.getMonthValue()).isEqualTo(1);

        // NIEZMIENNosc - plusDays(...) ZWRACA NOWY obiekt, ORYGINAL POZOSTAJE NIETKNIETY.
        LocalDateTime original = LocalDateTime.of(2026, 1, 1, 12, 0);
        LocalDateTime later = original.plusDays(10);

        assertThat(original.getDayOfMonth()).isEqualTo(1);
        assertThat(later.getDayOfMonth()).isEqualTo(11);
        System.out.println("original: " + original + " (NIEZMIENIONY), later = original.plusDays(10): " + later + " (NOWY obiekt).");

        Duration duration = Duration.between(original, later);
        System.out.println("Duration.between(...): " + duration.toDays() + " dni - CZYTELNE obliczenie roznicy.");
        assertThat(duration.toDays()).isEqualTo(10);
    }
}
