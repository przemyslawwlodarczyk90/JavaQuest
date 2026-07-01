package com.example.javaquest._01_fundamentals.Lesson07_DateAndTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class _Exercises_Lesson07_DateAndTime {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_PrintTodayDate {
        /*
         * 🧪 Zadanie 1:
         * Pobierz i wypisz dzisiejszą datę używając LocalDate.now().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_PrintCurrentTime {
        /*
         * 🧪 Zadanie 2:
         * Pobierz i wypisz aktualny czas używając LocalTime.now().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PrintDateComponents {
        /*
         * 🧪 Zadanie 3:
         * Pobierz dzisiejszą datę i wypisz osobno:
         * - rok (.getYear())
         * - miesiąc (.getMonth())
         * - dzień miesiąca (.getDayOfMonth())
         * - dzień tygodnia (.getDayOfWeek())
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_CreateSpecificDate {
        /*
         * 🧪 Zadanie 4:
         * Stwórz datę: 25 grudnia 2024 roku używając LocalDate.of(2024, Month.DECEMBER, 25).
         * Wypisz ją.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_AddDaysToDate {
        /*
         * 🧪 Zadanie 5:
         * Pobierz dzisiejszą datę.
         * Wypisz datę za 7 dni (.plusDays(7)) i datę 30 dni temu (.minusDays(30)).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_CompareLocalDates {
        /*
         * 🧪 Zadanie 6:
         * Stwórz daty: date1 = LocalDate.of(2020, 1, 1) i date2 = LocalDate.now().
         * Sprawdź i wypisz:
         * - date1.isBefore(date2)
         * - date1.isAfter(date2)
         * - date1.isEqual(LocalDate.of(2020, 1, 1))
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_LocalDateTimeNow {
        /*
         * 🧪 Zadanie 7:
         * Pobierz bieżącą datę i czas używając LocalDateTime.now().
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_CreateLocalDateTime {
        /*
         * 🧪 Zadanie 8:
         * Stwórz LocalDateTime dla: 2025-05-20 o godzinie 10:30.
         * Wypisz ją.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_TimeAddition {
        /*
         * 🧪 Zadanie 9:
         * Pobierz aktualny czas (LocalTime.now()).
         * Wypisz czas za 2 godziny i 30 minut używając .plusHours(2).plusMinutes(30).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_IsLeapYear {
        /*
         * 🧪 Zadanie 10:
         * Sprawdź, czy rok 2024 jest przestępny używając LocalDate.of(2024, 1, 1).isLeapYear().
         * Sprawdź też 2023 i 2000. Wypisz wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_FormatDate {
        /*
         * 🧪 Zadanie 11:
         * Pobierz LocalDate.now() i sformatuj ją do postaci "dd.MM.yyyy" używając DateTimeFormatter.
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_FormatDateTime {
        /*
         * 🧪 Zadanie 12:
         * Pobierz LocalDateTime.now() i sformatuj do postaci "dd.MM.yyyy HH:mm:ss".
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_ParseDate {
        /*
         * 🧪 Zadanie 13:
         * Sparsuj String "25.12.2024" do LocalDate używając DateTimeFormatter.ofPattern("dd.MM.yyyy").
         * Wypisz rok, miesiąc i dzień sparsowanej daty.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_ParseDateTime {
        /*
         * 🧪 Zadanie 14:
         * Sparsuj String "20.05.2025 10:30" do LocalDateTime używając odpowiedniego formattera.
         * Wypisz sparsowaną datę i czas.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_DaysBetween {
        /*
         * 🧪 Zadanie 15:
         * Oblicz liczbę dni między: LocalDate.of(1990, 6, 15) a LocalDate.now().
         * Użyj ChronoUnit.DAYS.between().
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_ZonedDateTime {
        /*
         * 🧪 Zadanie 16:
         * Pobierz aktualny czas w strefach: UTC, Europe/Warsaw, America/New_York.
         * Wypisz każdy wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_FirstAndLastDayOfMonth {
        /*
         * 🧪 Zadanie 17:
         * Pobierz LocalDate.now().
         * Wypisz pierwszy dzień tego miesiąca (.withDayOfMonth(1))
         * oraz ostatni dzień (.withDayOfMonth(today.lengthOfMonth())).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_InstantNow {
        /*
         * 🧪 Zadanie 18:
         * Pobierz Instant.now() i wypisz go.
         * Wyjaśnij w komentarzu czym jest Instant (znacznik czasu w UTC).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_MonthsBetween {
        /*
         * 🧪 Zadanie 19:
         * Oblicz liczbę miesięcy między Twoimi urodzinami (np. LocalDate.of(1990, 6, 15)) a dziś.
         * Użyj ChronoUnit.MONTHS.between().
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_AddWeeksAndMonths {
        /*
         * 🧪 Zadanie 20:
         * Pobierz LocalDate.now().
         * Wypisz datę: za 2 tygodnie (.plusWeeks(2)), za 3 miesiące (.plusMonths(3)), rok temu (.minusYears(1)).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_BirthdayCountdown {
        /*
         * 🧪 Zadanie 21:
         * Zadeklaruj datę urodzin: LocalDate birthday = LocalDate.of(1990, 5, 15).
         * Oblicz, ile dni pozostało do następnych urodzin.
         * Wskazówka: zmień rok urodzin na bieżący (lub następny jeśli już minął).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_FormatWithLocale {
        /*
         * 🧪 Zadanie 22:
         * Pobierz LocalDate.now() i sformatuj używając DateTimeFormatter.ofPattern z Locale("pl").
         * Spróbuj wzorca "EEEE, d MMMM yyyy" (np. "wtorek, 1 lipca 2025").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_TimeZoneConversion {
        /*
         * 🧪 Zadanie 23:
         * Pobierz ZonedDateTime.now(ZoneId.of("UTC")).
         * Przelicz go na strefę "Asia/Tokyo" i wypisz oba czasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_WeekdayCheck {
        /*
         * 🧪 Zadanie 24:
         * Pobierz LocalDate.now().
         * Sprawdź, czy dzisiejszy dzień tygodnia to weekend (SATURDAY lub SUNDAY).
         * Wypisz odpowiedni komunikat.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_HoursBetweenTimes {
        /*
         * 🧪 Zadanie 25:
         * Zadeklaruj LocalTime start = LocalTime.of(9, 0) i LocalTime end = LocalTime.of(17, 30).
         * Oblicz i wypisz liczbę minut między tymi czasami używając ChronoUnit.MINUTES.between().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ParseMultipleDateFormats {
        /*
         * 🧪 Zadanie 26:
         * Spróbuj sparsować daty w formatach: "2024-12-25", "25/12/2024", "25.12.2024".
         * Dla każdego użyj odpowiedniego DateTimeFormatter.
         * Wypisz sparsowane daty.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_DateComparison {
        /*
         * 🧪 Zadanie 27:
         * Stwórz dwie daty: deadline = LocalDate.of(2025, 12, 31) i today = LocalDate.now().
         * Sprawdź, czy deadline jeszcze nie minął.
         * Wypisz ile dni pozostało lub ile dni temu minął.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_WorkingDaysCalculator {
        /*
         * 🧪 Zadanie 28:
         * Pobierz LocalDate.now().
         * Wypisz wszystkie daty od dziś do 14 dni w przód, które są dniami roboczymi
         * (Poniedziałek – Piątek).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_AgeCalculator {
        /*
         * 🧪 Zadanie 29:
         * Zadeklaruj datę urodzin: LocalDate birthDate = LocalDate.of(1990, 3, 15).
         * Oblicz pełny wiek w latach (używając ChronoUnit.YEARS.between()).
         * Wypisz: "Masz X lat".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_MeetingScheduler {
        /*
         * 🧪 Zadanie 30:
         * Masz spotkanie co 2 tygodnie, zaczynając od LocalDate.of(2025, 1, 6).
         * Wypisz daty 8 kolejnych spotkań używając pętli i .plusWeeks(2).
         * Sformatuj każdą datę jako "dd.MM.yyyy (EEEE)".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
