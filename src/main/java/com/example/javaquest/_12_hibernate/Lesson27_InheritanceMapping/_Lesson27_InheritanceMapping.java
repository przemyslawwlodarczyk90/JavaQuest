package com.example.javaquest._12_hibernate.Lesson27_InheritanceMapping;

public class _Lesson27_InheritanceMapping {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 27: MAPOWANIE DZIEDZICZENIA: SINGLE_TABLE, JOINED, TABLE_PER_CLASS ===");

        /*
         * ============================================================
         * 📌 ZARYS LEKCJI (TODO - do napisania pełnej treści)
         * ============================================================
         * 1. InheritanceType.SINGLE_TABLE - jedna tabela dla całej hierarchii + kolumna dyskryminatora (@DiscriminatorColumn) - najszybsze zapytania, dużo NULL-i.
         * 2. InheritanceType.JOINED - osobna tabela na każdą klasę, połączone kluczem obcym - normalizacja, ale JOIN-y przy odczycie.
         * 3. InheritanceType.TABLE_PER_CLASS - osobna, pełna tabela na każdą konkretną klasę - brak JOIN-ów, ale trudniejsze zapytania po całej hierarchii.
         * 4. @MappedSuperclass - współdzielenie pól (np. id, createdAt) BEZ że nadklasa jest encją/tabelą.
         * 5. Przykład: hierarchia Payment -> CardPayment/BankTransferPayment w każdej z 3 strategii, z komentarzem o kompromisach.
         *
         * TODO: rozwinąć każdy punkt w pełną teorię (bloki komentarzy z
         * nagłówkami 📦/🔹/🔍/📌, jak w pozostałych rozdziałach kursu) +
         * w pełni wykonywalny kod na H2 in-memory (SessionFactory/
         * EntityManagerFactory), zgodnie z decyzją z CLAUDE.md
         * (_12_hibernate w pełni wykonywalny, jak _08_sql/_09_jdbc/_10_dao).
         */

        System.out.println("TODO: treść lekcji 27 nie jest jeszcze napisana.");

        System.out.println("\n=== KONIEC LEKCJI 27 (SZKIELET) ===");
    }
}
