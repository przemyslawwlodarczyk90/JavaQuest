package com.example.javaquest._12_hibernate.Lesson29_HibernateEnvers;

public class _Lesson29_HibernateEnvers {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 29: AUDYTOWANIE ZMIAN: Hibernate Envers ===");

        /*
         * ============================================================
         * 📌 ZARYS LEKCJI (TODO - do napisania pełnej treści)
         * ============================================================
         * 1. Po co audytowanie - historia WSZYSTKICH zmian encji (kto/kiedy/co), wymóg compliance w wielu systemach.
         * 2. @Audited na encji (lub całej klasie) - Envers automatycznie tworzy tabelę _AUD z historią rewizji.
         * 3. AuditReader - odczyt stanu encji z KONKRETNEJ rewizji, lista wszystkich rewizji dla danego Id.
         * 4. RevisionEntity/@RevisionEntity - własne metadane rewizji (np. kto wykonał zmianę - powiązanie z użytkownikiem systemu).
         * 5. Pełny przykład: encja Account z historią zmian salda, odczyt stanu "sprzed 3 zmian".
         *
         * TODO: rozwinąć każdy punkt w pełną teorię (bloki komentarzy z
         * nagłówkami 📦/🔹/🔍/📌, jak w pozostałych rozdziałach kursu) +
         * w pełni wykonywalny kod na H2 in-memory (SessionFactory/
         * EntityManagerFactory), zgodnie z decyzją z CLAUDE.md
         * (_12_hibernate w pełni wykonywalny, jak _08_sql/_09_jdbc/_10_dao).
         */

        System.out.println("TODO: treść lekcji 29 nie jest jeszcze napisana.");

        System.out.println("\n=== KONIEC LEKCJI 29 (SZKIELET) ===");
    }
}
