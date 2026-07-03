package com.example.javaquest._04_io.Lesson13_TryWithResources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson13_TryWithResources {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 TRY-WITH-RESOURCES – CZYM JEST?
         * ============================================================
         * Try-with-resources to konstrukcja (Java 7+) która AUTOMATYCZNIE
         * zamyka zasoby (pliki, strumienie, połączenia) po zakończeniu bloku try
         * – niezależnie od tego, czy kod zakończył się normalnie, czy wyjątkiem.
         *
         * Warunek: zasób musi implementować interfejs AutoCloseable
         * (Closeable, którego implementują wszystkie strumienie I/O, jest
         * podinterfejsem AutoCloseable).
         *
         * interface AutoCloseable {
         *     void close() throws Exception;
         * }
         *
         * Składnia:
         * try (Zasob z = new Zasob()) {
         *     // użycie z
         * } catch (Exception e) {
         *     // obsługa
         * }
         * // z.close() wywoła się automatycznie, nawet przy wyjątku!
         */

        /*
         * ============================================================
         * 🔹 WŁASNA KLASA IMPLEMENTUJĄCA AutoCloseable
         * ============================================================
         */

        System.out.println("=== Własny zasób ===");
        try (MyResource resource = new MyResource("Zasob-A")) {
            resource.use();
        }
        // Kolejność: konstruktor -> use() -> close() (automatycznie!)

        System.out.println();

        /*
         * ============================================================
         * 🔍 WIELE ZASOBÓW W JEDNYM TRY – KOLEJNOŚĆ ZAMYKANIA
         * ============================================================
         * Zasoby zamykane są w ODWROTNEJ kolejności do deklaracji
         * (jak stos – ostatni otwarty, pierwszy zamknięty).
         */

        System.out.println("=== Wiele zasobów – kolejność zamykania ===");
        try (MyResource a = new MyResource("Pierwszy");
             MyResource b = new MyResource("Drugi");
             MyResource c = new MyResource("Trzeci")) {
            a.use();
            b.use();
            c.use();
        }
        // Zamykanie: Trzeci, Drugi, Pierwszy (odwrotna kolejność!)

        System.out.println();

        /*
         * ============================================================
         * 🔹 TRY-WITH-RESOURCES NA STRUMIENIACH PLIKOWYCH
         * ============================================================
         * To najczęstsze praktyczne zastosowanie – pliki, czytniki, pisarze.
         */

        Path tempFile = Files.createTempFile("lesson13", ".txt");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
                writer.write("Linia 1");
                writer.newLine();
                writer.write("Linia 2");
            } // writer.close() automatycznie – dane trafiają na dysk

            try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Odczytano: " + line);
                }
            } // reader.close() automatycznie

            System.out.println();

            /*
             * ============================================================
             * ⚠️ SUPPRESSED EXCEPTIONS – GDY I TRY, I close() RZUCAJĄ WYJĄTEK
             * ============================================================
             * Co się dzieje, gdy kod w bloku try rzuca wyjątek, a close()
             * też rzuca wyjątek podczas sprzątania?
             *
             * Java NIE gubi żadnego z nich:
             * - wyjątek z bloku try staje się głównym wyjątkiem (propagowany dalej)
             * - wyjątek z close() zostaje "stłumiony" (suppressed) i DOŁĄCZONY
             *   do głównego wyjątku – dostępny przez Throwable.getSuppressed()
             */

            System.out.println("=== Suppressed exceptions ===");
            try {
                try (FailingResource fr = new FailingResource()) {
                    throw new RuntimeException("Błąd w bloku try!");
                }
            } catch (RuntimeException e) {
                System.out.println("Złapano główny wyjątek: " + e.getMessage());
                for (Throwable suppressed : e.getSuppressed()) {
                    System.out.println("  Stłumiony (suppressed): " + suppressed);
                }
            }
            // Wypisze:
            // Złapano główny wyjątek: Błąd w bloku try!
            //   Stłumiony (suppressed): java.lang.RuntimeException: Błąd w close()!

        } finally {
            Files.deleteIfExists(tempFile);
        }

        System.out.println();

        /*
         * ============================================================
         * 🔍 PORÓWNANIE Z RĘCZNYM finally
         * ============================================================
         * Przed Javą 7 zasoby zamykało się ręcznie w finally – i to
         * miało SWOJE WŁASNE problemy z wyjątkami:
         *
         * MyResource r = null;
         * try {
         *     r = new MyResource("Reczny");
         *     r.use();
         *     throw new RuntimeException("Błąd główny");
         * } finally {
         *     if (r != null) {
         *         r.close(); // ❌ jeśli close() rzuci wyjątek, ZASTĄPI on
         *                     //    (przykryje) oryginalny wyjątek z try!
         *                     //    Oryginalny wyjątek GINIE bezpowrotnie.
         *     }
         * }
         *
         * Try-with-resources rozwiązuje ten problem – oryginalny wyjątek
         * jest zawsze zachowany jako główny, a wyjątek z close() trafia
         * do getSuppressed() zamiast go przykrywać.
         *
         * ✅ Try-with-resources: czytelniejszy kod, brak ryzyka pominięcia
         *    close(), poprawna obsługa podwójnych wyjątków.
         * ❌ Ręczny finally: więcej kodu, łatwo zapomnieć o null-checku,
         *    łatwo "zgubić" wyjątek.
         */

        System.out.println("=== Ręczny finally (demonstracja utraty wyjątku) ===");
        MyResource manual = null;
        try {
            manual = new MyResource("Reczny");
            manual.use();
            throw new RuntimeException("Błąd główny (ręczny finally)");
        } catch (RuntimeException e) {
            System.out.println("Złapano: " + e.getMessage());
        } finally {
            if (manual != null) {
                manual.close();
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zasób w try-with-resources musi implementować AutoCloseable
         *   (Closeable to podinterfejs AutoCloseable, close() bez IOException-only)
         * - Wiele zasobów: zamykane w ODWROTNEJ kolejności deklaracji
         * - close() wywołuje się ZAWSZE – nawet gdy blok try rzuci wyjątek
         * - Gdy try i close() oba rzucają wyjątek:
         *   -> wyjątek z try jest głównym, wyjątek z close() trafia do
         *      getSuppressed() (nic nie ginie!)
         * - Ręczny finally nie chroni przed przykryciem wyjątku –
         *   try-with-resources to rozwiązuje automatycznie
         * ✅ Zawsze używaj try-with-resources dla plików, strumieni, połączeń
         */
    }

    /**
     * Prosty zasób demonstracyjny implementujący AutoCloseable.
     */
    static class MyResource implements AutoCloseable {
        private final String name;

        MyResource(String name) {
            this.name = name;
            System.out.println("Otwieram zasób: " + name);
        }

        void use() {
            System.out.println("Uzywam zasobu: " + name);
        }

        @Override
        public void close() {
            System.out.println("Zamykam zasob: " + name);
        }
    }

    /**
     * Zasob, ktorego close() zawsze rzuca wyjatek - do demonstracji
     * suppressed exceptions.
     */
    static class FailingResource implements AutoCloseable {
        @Override
        public void close() {
            throw new RuntimeException("Blad w close()!");
        }
    }
}
