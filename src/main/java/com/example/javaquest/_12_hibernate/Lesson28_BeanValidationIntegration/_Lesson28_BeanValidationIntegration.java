package com.example.javaquest._12_hibernate.Lesson28_BeanValidationIntegration;

public class _Lesson28_BeanValidationIntegration {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 28: INTEGRACJA Z BEAN VALIDATION: Hibernate Validator ===");

        /*
         * ============================================================
         * 📌 ZARYS LEKCJI (TODO - do napisania pełnej treści)
         * ============================================================
         * 1. Jakarta Bean Validation (JSR 380) - standard adnotacji walidacyjnych, Hibernate Validator jako referencyjna implementacja (już w pom.xml przez spring-boot-starter-validation).
         * 2. @NotNull/@Size/@Email/@Min/@Max na polach encji - deklaratywne reguły poprawności danych.
         * 3. Automatyczna walidacja przy persist/update przez Hibernate (hibernate.validator integration) - ConstraintViolationException.
         * 4. Walidacja RĘCZNA przez Validator/ValidatorFactory (poza cyklem życia encji) - np. przy odbiorze danych z formularza.
         * 5. Przykład: encja Customer z regułami walidacji, demo udanego zapisu i przechwyconego ConstraintViolationException.
         *
         * TODO: rozwinąć każdy punkt w pełną teorię (bloki komentarzy z
         * nagłówkami 📦/🔹/🔍/📌, jak w pozostałych rozdziałach kursu) +
         * w pełni wykonywalny kod na H2 in-memory (SessionFactory/
         * EntityManagerFactory), zgodnie z decyzją z CLAUDE.md
         * (_12_hibernate w pełni wykonywalny, jak _08_sql/_09_jdbc/_10_dao).
         */

        System.out.println("TODO: treść lekcji 28 nie jest jeszcze napisana.");

        System.out.println("\n=== KONIEC LEKCJI 28 (SZKIELET) ===");
    }
}
