package com.example.javaquest.liveCodeVersion._01_fundamentals.Lesson07_DateAndTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class _Lesson07_DateAndTime {

    public static void main(String[] args) {

        /*
         * ===================================================================
         * 🕰️ WSTĘP: API CZASU W JAVIE
         * ===================================================================
         * Java 8 wprowadziła nowoczesne API daty i czasu w pakiecie java.time
         * - LocalDate → tylko data (rok, miesiąc, dzień)
         * - LocalTime → tylko czas (godzina, minuta, sekunda)
         * - LocalDateTime → data i czas razem
         * - ZonedDateTime → data i czas z informacją o strefie czasowej
         * - Instant → znacznik czasu (timestamp w UTC)
         */

        // ========================
        // 📅 LocalDate – Tylko data
        // ========================
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1990, Month.JUNE, 15);

        System.out.println("Dzisiejsza data: " + today);
        System.out.println("Urodziny: " + birthday);

        System.out.println("Rok: " + today.getYear());
        System.out.println("Miesiąc: " + today.getMonth());
        System.out.println("Dzień miesiąca: " + today.getDayOfMonth());
        System.out.println("Dzień tygodnia: " + today.getDayOfWeek());

        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate lastMonth = today.minusMonths(1);
        System.out.println("Za tydzień: " + nextWeek);
        System.out.println("Miesiąc temu: " + lastMonth);

        boolean isBefore = birthday.isBefore(today);
        boolean isAfter = birthday.isAfter(today);
        System.out.println("Urodziny przed dziś? " + isBefore);

        // ========================
        // 🕒 LocalTime – Tylko czas
        // ========================
        LocalTime now = LocalTime.now();
        LocalTime meeting = LocalTime.of(14, 30);

        System.out.println("Aktualna godzina: " + now);
        System.out.println("Spotkanie: " + meeting);

        LocalTime later = now.plusHours(2).plusMinutes(15);
        System.out.println("Za 2 godziny 15 minut: " + later);

        // =============================
        // 🧭 LocalDateTime – Data + Czas
        // =============================
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime appointment = LocalDateTime.of(2025, 5, 20, 10, 0);

        System.out.println("Teraz (data + czas): " + currentDateTime);
        System.out.println("Wizyta: " + appointment);

        LocalDateTime inFiveDays = currentDateTime.plusDays(5);
        System.out.println("Za 5 dni: " + inFiveDays);

        // ==========================
        // 🌍 ZonedDateTime – Strefy
        // ==========================
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime polandTime = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
        ZonedDateTime nyTime = ZonedDateTime.now(ZoneId.of("America/New_York"));

        System.out.println("UTC: " + utcNow);
        System.out.println("Polska: " + polandTime);
        System.out.println("Nowy Jork: " + nyTime);

        // ==========================
        // ⏳ Instant – Znacznik czasu
        // ==========================
        Instant instantNow = Instant.now();
        System.out.println("Instant teraz (UTC): " + instantNow);

        // ============================
        // 🛠️ Formatowanie daty i czasu
        // ============================
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.forLanguageTag("pl"));
        String formatted = currentDateTime.format(formatter);
        System.out.println("Sformatowana data: " + formatted);

        // Parsowanie tekstu na datę
        String dateText = "21.12.2024 18:00";
        LocalDateTime parsedDate = LocalDateTime.parse(dateText, formatter);
        System.out.println("Sparsowana data: " + parsedDate);

        // ============================
        // 🔢 Obliczenia między datami
        // ============================
        long daysBetween = ChronoUnit.DAYS.between(birthday, today);
        long monthsBetween = ChronoUnit.MONTHS.between(birthday, today);

        System.out.println("Dni od urodzin: " + daysBetween);
        System.out.println("Miesięcy od urodzin: " + monthsBetween);

        // ============================
        // 🧪 Dodatkowe metody i właściwości
        // ============================
        System.out.println("Czy przestępny rok? " + today.isLeapYear());
        System.out.println("Pierwszy dzień miesiąca: " + today.withDayOfMonth(1));
        System.out.println("Ostatni dzień miesiąca: " + today.withDayOfMonth(today.lengthOfMonth()));

    }
}
