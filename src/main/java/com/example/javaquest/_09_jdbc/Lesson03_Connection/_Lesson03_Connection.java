package com.example.javaquest._09_jdbc.Lesson03_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class _Lesson03_Connection {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST Connection?
         * ============================================================
         * `java.sql.Connection` reprezentuje AKTYWNE POŁĄCZENIE między
         * naszą aplikacją a konkretną bazą danych. To przez ten obiekt
         * tworzymy Statement/PreparedStatement (kolejne lekcje),
         * sterujemy transakcjami (commit/rollback - Lesson15) i w ogóle
         * "rozmawiamy" z bazą.
         *
         * Connection tworzymy przez DriverManager, podając URL (i
         * ewentualnie login/hasło):
         *
         *     Connection conn = DriverManager.getConnection(url, user, password);
         */

        /*
         * ============================================================
         * 🔹 BUDOWA URL-A POŁĄCZENIA (na przykładzie H2)
         * ============================================================
         * jdbc:h2:mem:jdbclesson03;DB_CLOSE_DELAY=-1
         * │    │  │   │            │
         * │    │  │   │            └─ PARAMETRY (opcjonalne, oddzielone ;)
         * │    │  │   │               DB_CLOSE_DELAY=-1 = nie zamykaj bazy
         * │    │  │   │               w pamięci, gdy ostatnie połączenie
         * │    │  │   │               się zamknie (inaczej baza in-memory
         * │    │  │   │               znikałaby natychmiast po pierwszym
         * │    │  │   │               close())
         * │    │  │   └─ NAZWA/LOKALIZACJA bazy (tu: "jdbclesson03" -
         * │    │  │      identyfikator bazy w pamięci)
         * │    │  └─ PODTYP (subprotocol) - "mem" = baza W PAMIĘCI
         * │    │     (istnieje inne: "jdbc:h2:file:./sciezka/do/pliku"
         * │    │     - baza zapisywana na dysku)
         * │    └─ nazwa bazy danych/produktu ("h2")
         * └─ STAŁY PROTOKÓŁ JDBC - zawsze zaczyna się od "jdbc:"
         *
         * Dla porównania, URL-e innych baz mają PODOBNĄ strukturę
         * (protokół:podtyp:lokalizacja), ale lokalizacja to zwykle
         * host+port+nazwa bazy na serwerze:
         *
         *   jdbc:postgresql://localhost:5432/moja_baza
         *   jdbc:mysql://localhost:3306/moja_baza?useSSL=false
         *
         * 🔍 LOGIN I HASŁO
         * H2 w trybie in-memory domyślnie NIE wymaga loginu/hasła (można
         * pominąć albo podać puste dane) - to wygodne do nauki i testów.
         * PRAWDZIWE bazy danych (PostgreSQL, MySQL na serwerze) WYMAGAJĄ
         * poprawnych danych logowania - próba połączenia z błędnym
         * loginem/hasłem zakończy się wyjątkiem SQLException.
         */

        System.out.println("=== WARIANTY URL POLACZENIA H2 ===");

        // Wariant 1: najprostszy - baza w pamięci, bez dodatkowych parametrów
        // UWAGA: bez DB_CLOSE_DELAY=-1 baza zniknęłaby natychmiast po
        // zamknięciu JEDYNEGO połączenia - tu robimy to celowo w bloku
        // try-with-resources, żeby zobaczyć samo połączenie i rozłączenie.
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:jdbclesson03_a")) {
            System.out.println("Wariant 1 OK - polaczono z: " + conn.getMetaData().getURL());
        }

        // Wariant 2: z parametrem DB_CLOSE_DELAY=-1 (baza "przetrwa" zamknięcie
        // pojedynczego połączenia - przydatne gdy otwieramy wiele połączeń
        // do tej samej bazy w trakcie działania programu)
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:jdbclesson03_b;DB_CLOSE_DELAY=-1")) {
            System.out.println("Wariant 2 OK - polaczono z: " + conn.getMetaData().getURL());
        }

        // Wariant 3: jawne podanie loginu i hasła (dla H2 in-memory
        // domyślny użytkownik to "sa" z pustym hasłem)
        try (Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:jdbclesson03_c;DB_CLOSE_DELAY=-1", "sa", "")) {
            System.out.println("Wariant 3 OK - polaczono jako uzytkownik: " + conn.getMetaData().getUserName());
        }

        /*
         * ============================================================
         * 🔍 KOSZT TWORZENIA POŁĄCZENIA
         * ============================================================
         * Nawiązanie połączenia z PRAWDZIWĄ bazą danych (na osobnym
         * serwerze) to operacja stosunkowo DROGA:
         * - nawiązanie połączenia sieciowego (TCP) z serwerem bazy danych,
         * - uwierzytelnienie (przesłanie i weryfikacja loginu/hasła),
         * - negocjacja parametrów sesji (kodowanie znaków, wersja
         *   protokołu itd.).
         *
         * To wszystko trwa - typowo pojedyncze do kilkudziesięciu
         * milisekund, ale przy setkach/tysiącach zapytań na sekundę
         * (typowa aplikacja webowa) tworzenie NOWEGO połączenia PRZY
         * KAŻDYM zapytaniu byłoby katastrofalne dla wydajności.
         *
         * H2 w trybie in-memory jest wyjątkowo szybkie (nie ma sieci -
         * wszystko dzieje się w tym samym procesie JVM), więc na tym
         * kursie ten koszt jest praktycznie niewidoczny. Ale w prawdziwym
         * projekcie z PostgreSQL/MySQL na osobnym serwerze - jest bardzo
         * odczuwalny.
         *
         * 📌 DLATEGO w praktyce NIE tworzy się nowego Connection dla
         * każdego zapytania - zamiast tego używa się PULI POŁĄCZEŃ
         * (connection pool, np. HikariCP), która utrzymuje pewną liczbę
         * GOTOWYCH, już nawiązanych połączeń i "wypożycza" je aplikacji
         * na czas jednego zapytania, zamiast tworzyć je od zera za
         * każdym razem. Poznamy to dokładnie w kolejnym rozdziale
         * poświęconym wzorcowi DAO.
         */

        System.out.println("\n=== POMIAR CZASU NAWIAZANIA POLACZENIA (H2 in-memory) ===");
        long start = System.nanoTime();
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:jdbclesson03_d;DB_CLOSE_DELAY=-1")) {
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Polaczenie nawiazane w ok. " + elapsedMs + " ms (H2 in-memory - bardzo szybko).");
            System.out.println("Dla porownania: polaczenie TCP do prawdziwego serwera bazy danych");
            System.out.println("(PostgreSQL/MySQL na innej maszynie) typowo trwa dluzej - dlatego");
            System.out.println("w praktyce uzywa sie puli polaczen (connection pool).");
        }

        /*
         * ============================================================
         * 🔹 ZAMYKANIE POŁĄCZENIA
         * ============================================================
         * Connection to zasób, który MUSI zostać zamknięty (conn.close())
         * po zakończeniu pracy - inaczej "wyciekają" zasoby (uchwyty
         * sieciowe/pamięć po stronie sterownika i bazy danych). Najlepszy
         * sposób to try-with-resources (Connection implementuje
         * AutoCloseable) - zamknięcie następuje automatycznie, nawet gdy
         * wystąpi wyjątek. Dokładnie ten wzorzec poznamy w Lesson12.
         */

        System.out.println("\n=== KONIEC LEKCJI 3 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Connection reprezentuje aktywne połączenie z bazą danych,
         *   tworzone przez DriverManager.getConnection(url, user, pass)
         * - URL JDBC ma strukturę: jdbc:<baza>:<podtyp>:<lokalizacja>;
         *   <parametry> (dla H2: jdbc:h2:mem:nazwa;DB_CLOSE_DELAY=-1)
         * - H2 in-memory domyślnie nie wymaga loginu/hasła, ale
         *   PRAWDZIWE bazy danych tego wymagają
         * - Tworzenie połączenia z prawdziwą, sieciową bazą danych jest
         *   relatywnie KOSZTOWNE (TCP, uwierzytelnienie) - dlatego w
         *   praktyce używa się puli połączeń (connection pool), zamiast
         *   tworzyć nowe połączenie na każde zapytanie
         * - Connection trzeba zawsze zamknąć (najlepiej przez
         *   try-with-resources)
         */
    }
}
