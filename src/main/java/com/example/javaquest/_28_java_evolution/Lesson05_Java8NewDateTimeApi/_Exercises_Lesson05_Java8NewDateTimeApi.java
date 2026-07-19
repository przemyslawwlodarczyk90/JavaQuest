package com.example.javaquest._28_java_evolution.Lesson05_Java8NewDateTimeApi;

public class _Exercises_Lesson05_Java8NewDateTimeApi {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateLocalDateWithOf {
        /* 🧪 Zadanie 1: Utworz `LocalDate` PRZEZ `.of(rok, miesiac, dzien)`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddDaysToLocalDate {
        /* 🧪 Zadanie 2: Dodaj DNI DO `LocalDate` (`.plusDays(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareTwoLocalDatesForOrder {
        /* 🧪 Zadanie 3: Porownaj 2 `LocalDate` (`.isBefore()`/`.isAfter()`). */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateOldStyleDateAndObserveMonthOffByOne {
        /* 🧪 Zadanie 4: Utworz STARY `Date` I zaobserwuj PULAPKE miesiaca OD zera. */
        public static void main(String[] args) { }
    }

    static class Exercise05_DemonstrateDateMutabilityBug {
        /* 🧪 Zadanie 5: Zademonstruj BLAD spowodowany MUTOWALNOSCIA `Date`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseDurationBetweenTwoInstants {
        /* 🧪 Zadanie 6: Uzyj `Duration.between(...)` DLA 2 `Instant`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ConvertOldDateToNewLocalDateTime {
        /* 🧪 Zadanie 7: PRZEKONWERTUJ STARY `Date` NA `LocalDateTime` (`.toInstant()`). */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyDateWasDeprecated {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, DLACZEGO WIEKSZOSC konstruktorow `Date` jest DEPRECATED. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UsePeriodForCalendarBasedDifference {
        /* 🧪 Zadanie 9: Uzyj `Period.between(...)` DLA roznicy KALENDARZOWEJ (lata/miesiace/dni). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFiveClassesFromJavaTimePackage {
        /* 🧪 Zadanie 10: Wymien 5 klas Z pakietu `java.time`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseZonedDateTimeForTimeZoneAwareLogic {
        /* 🧪 Zadanie 11: Powiaz z `_01_fundamentals/Lesson07` - uzyj `ZonedDateTime` DLA logiki UWZGLEDNIAJACEJ strefy czasowe. */
        public static void main(String[] args) { }
    }

    static class Exercise12_FormatLocalDateWithDateTimeFormatter {
        /* 🧪 Zadanie 12: Sformatuj `LocalDate` PRZEZ `DateTimeFormatter`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ParseStringToLocalDateTime {
        /* 🧪 Zadanie 13: Sparsuj String DO `LocalDateTime`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementThreadSafetyComparisonOldVsNewDateApi {
        /* 🧪 Zadanie 14: Powiaz z `_05_multithreading` - porownaj BEZPIECZENSTWO watkowe STAREGO I NOWEGO API. */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseLocalDateWithMethodForImmutableModification {
        /* 🧪 Zadanie 15: Uzyj `.withYear(...)`/`.withMonth(...)` (NIEZMIENNA modyfikacja). */
        public static void main(String[] args) { }
    }

    static class Exercise16_CalculateAgeUsingPeriod {
        /* 🧪 Zadanie 16: Oblicz WIEK osoby PRZEZ `Period.between(dataUrodzenia, dzisiaj)`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareInstantWithLocalDateTimeUseCases {
        /* 🧪 Zadanie 17: Bez terminala - porownaj przypadki uzycia `Instant` (bez strefy) Z `LocalDateTime`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_MigrateLegacyDateBasedMethodSignatureToLocalDate {
        /* 🧪 Zadanie 18: PRZEPISZ (HIPOTETYCZNA) sygnatura metody Z `Date` NA `LocalDate`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_UseChronoUnitForPreciseDurationCalculation {
        /* 🧪 Zadanie 19: Uzyj `ChronoUnit.DAYS.between(...)` DLA PRECYZYJNEGO obliczenia. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignMigrationChecklistFromDateToJavaTime {
        /* 🧪 Zadanie 20: Zaprojektuj LISTE kontrolna migracji STAREGO kodu `Date` NA `java.time`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomTemporalAdjuster {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `TemporalAdjuster` (np. "nastepny poniedzialek"). */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildDateRangeIteratorUsingLocalDate {
        /* 🧪 Zadanie 22: Zbuduj ITERATOR PO zakresie dat (`Stream.iterate` + `LocalDate`). */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementBusinessDayCalculatorSkippingWeekends {
        /* 🧪 Zadanie 23: Zaimplementuj KALKULATOR dni ROBOCZYCH (POMIJAJACY weekendy). */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareDaylightSavingTimeHandlingOldVsNewApi {
        /* 🧪 Zadanie 24: Bez terminala - porownaj obsluge czasu LETNIEGO/ZIMOWEGO W STARYM I NOWYM API. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRecurringEventSchedulerUsingJavaTime {
        /* 🧪 Zadanie 25: Powiaz z `_27_spring_test/Lesson16` - zaimplementuj harmonogram CYKLICZNYCH zdarzen. */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildDateRangeOverlapDetectionAlgorithm {
        /* 🧪 Zadanie 26: Zbuduj algorytm WYKRYWAJACY NAKLADANIE sie zakresow dat. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomChronologyForNonGregorianCalendar {
        /* 🧪 Zadanie 27: Zbadaj `Chronology` DLA kalendarza INNEGO NIZ gregorianski (KONCEPCYJNIE). */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasurePerformanceDifferenceBetweenDateAndLocalDate {
        /* 🧪 Zadanie 28: Zmierz (przyblizona) roznice WYDAJNOSCI operacji NA `Date` WZGLEDEM `LocalDate`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFullAuditTimestampSystemUsingInstant {
        /* 🧪 Zadanie 29: Powiaz z `_19_security_basics/Lesson19` - zaimplementuj system znacznikow czasu audytu OPARTY NA `Instant`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullDateTimeHandlingStandardForInternationalApplication {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard obslugi daty/czasu DLA aplikacji MIEDZYNARODOWEJ (WIELE stref czasowych). */
        public static void main(String[] args) { }
    }
}
