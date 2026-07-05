package com.example.javaquest._12_hibernate.Lesson30_BestPracticesAndCapstone;

public class _Lesson30_BestPracticesAndCapstone {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 30: DOBRE PRAKTYKI I PROJEKT PODSUMOWUJĄCY ===");

        /*
         * ============================================================
         * 📌 ZARYS LEKCJI (TODO - do napisania pełnej treści)
         * ============================================================
         * 1. Zestawienie typowych pułapek z całego rozdziału: N+1 (Lekcja 15), detached entity bez merge (Lekcja 16/6), brak @Version przy współbieżności (Lekcja 25), niewłaściwy fetch EAGER wszędzie.
         * 2. Dobre praktyki: DTO do odczytu zamiast całych encji tam gdzie można, jawne fetch join zamiast polegania na EAGER, transakcje możliwie krótkie.
         * 3. Wydajność: batch inserts/updates (hibernate.jdbc.batch_size), unikanie L2 cache dla często zmienianych danych.
         * 4. Porównanie całościowe: czysty JDBC (_09_jdbc) vs DAO ręczny (_10_dao) vs Hibernate/JPA (ten rozdział) - kiedy który wybrać.
         * 5. Projekt podsumowujący (capstone): mini-biblioteka (Author/Book/Loan) łącząca asocjacje, cache, HQL i walidację z całego rozdziału w jednym, uruchamialnym programie.
         *
         * TODO: rozwinąć każdy punkt w pełną teorię (bloki komentarzy z
         * nagłówkami 📦/🔹/🔍/📌, jak w pozostałych rozdziałach kursu) +
         * w pełni wykonywalny kod na H2 in-memory (SessionFactory/
         * EntityManagerFactory), zgodnie z decyzją z CLAUDE.md
         * (_12_hibernate w pełni wykonywalny, jak _08_sql/_09_jdbc/_10_dao).
         */

        System.out.println("TODO: treść lekcji 30 nie jest jeszcze napisana.");

        System.out.println("\n=== KONIEC LEKCJI 30 (SZKIELET) ===");
    }
}
