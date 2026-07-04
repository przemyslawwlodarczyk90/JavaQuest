package com.example.javaquest._10_dao.Lesson10_RepositoryVsDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class _Lesson10_RepositoryVsDao {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 DAO vs REPOSITORY - CZY TO TO SAMO?
         * ============================================================
         * W praktyce te dwa słowa są używane momentami zamiennie, ale
         * historycznie i koncepcyjnie różnią się PUNKTEM WIDZENIA:
         *
         * - DAO (Data Access Object) - podejście TECHNICZNE. Metody
         *   nazywają się blisko operacji SQL/tabeli: findById, findAll,
         *   insert, update, delete. DAO "wie", że pod spodem jest baza
         *   danych, tabela, wiersze.
         *
         * - Repository - podejście bardziej DOMENOWE (z Domain-Driven
         *   Design). Repository UDAJE, że jest zwykłą kolekcją obiektów
         *   biznesowych w pamięci ("kolekcja użytkowników"), a metody
         *   nazywają się językiem BIZNESU, nie językiem bazy danych:
         *   findActiveUsers(), findByEmailDomain(), reactivateUser().
         *   Osoba czytająca kod Repository nie musi wiedzieć, że pod
         *   spodem w ogóle jest SQL.
         *
         * 🔹 JEDNO ZDANIE DO ZAPAMIĘTANIA
         * DAO odpowiada na pytanie "JAK dostać się do danych?".
         * Repository odpowiada na pytanie "CZEGO potrzebuje BIZNES?".
         */

        String url = "jdbc:h2:mem:daolesson10;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {
            setUpSchema(connection);
            seedData(connection);

            /*
             * ============================================================
             * 🔍 TA SAMA FUNKCJONALNOŚĆ, DWIE NAZWY
             * ============================================================
             * Poniżej dwa obiekty operujące na DOKŁADNIE tej samej tabeli
             * users - jeden nazwany i napisany w stylu DAO (techniczne
             * metody CRUD), drugi w stylu Repository (metody nazwane
             * językiem biznesowym). Zwróć uwagę na RÓŻNICĘ w czytelności
             * wywołania, mimo że pod spodem leży ten sam SQL.
             */

            System.out.println("=== STYL DAO: metody techniczne ===");
            UserDao userDao = new UserDao(connection);
            Optional<String> byId = userDao.findById(1L);
            System.out.println("userDao.findById(1L) -> " + byId);
            List<String> all = userDao.findAll();
            System.out.println("userDao.findAll() -> " + all);

            System.out.println("\n=== STYL REPOSITORY: metody domenowe ===");
            UserRepository userRepository = new UserRepository(connection);
            List<String> activeUsers = userRepository.findActiveUsers();
            System.out.println("userRepository.findActiveUsers() -> " + activeUsers);
            List<String> companyUsers = userRepository.findByEmailDomain("firma.pl");
            System.out.println("userRepository.findByEmailDomain(\"firma.pl\") -> " + companyUsers);

            /*
             * ============================================================
             * 🔍 CZYTAJ NA GŁOS OBA WYWOŁANIA
             * ============================================================
             * "userDao.findById(1L)" - trzeba wiedzieć, że w tabeli
             * istnieje kolumna "id" i że tak identyfikujemy wiersz.
             *
             * "userRepository.findActiveUsers()" - nie trzeba wiedzieć
             * NIC o strukturze tabeli. Wiadomo tylko, że chcemy
             * "aktywnych użytkowników" - to zdanie zrozumiałe dla
             * analityka biznesowego, nie tylko dla programisty.
             */

            System.out.println("\n=== ZAPOWIEDŹ: Spring Data ===");
            System.out.println("""
                    W ekosystemie Spring (poznasz to w kolejnym rozdziale kursu)
                    interfejsy dostępu do danych nazywają się WŁAŚNIE Repository
                    (np. JpaRepository, CrudRepository) - to nie przypadek.
                    Spring promuje myślenie "domenowe": Repository ma udawać
                    kolekcję obiektów biznesowych, a nie cienką nakładkę na SQL.""");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DAO i Repository często realizują TĘ SAMĄ funkcję (dostęp
         *   do danych) - różnią się głównie NAZEWNICTWEM i PERSPEKTYWĄ
         * - DAO = techniczne, blisko SQL/tabeli (findById, insert, delete)
         * - Repository = domenowe, blisko języka biznesu (findActiveUsers,
         *   reactivateUser) - udaje kolekcję obiektów w pamięci
         * - Repository jest popularny w Domain-Driven Design i w Spring
         *   Data (JpaRepository, CrudRepository) - to zapowiedź kolejnego
         *   rozdziału kursu
         * - Wybór nazwy to kwestia konwencji zespołu/frameworka - nie ma
         *   tu "jedynej słusznej" odpowiedzi, ważna jest SPÓJNOŚĆ w projekcie
         */
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        email VARCHAR(150) NOT NULL,
                        active BOOLEAN NOT NULL
                    )
                    """);
        }
    }

    private static void seedData(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    INSERT INTO users (first_name, email, active) VALUES
                    ('Ania', 'ania@firma.pl', TRUE),
                    ('Bartek', 'bartek@firma.pl', TRUE),
                    ('Zenek', 'zenek@prywatny.com', FALSE)
                    """);
        }
    }

    /**
     * 🔹 STYL DAO: metody nazwane TECHNICZNIE, blisko operacji na tabeli.
     * Osoba czytająca ten kod musi znać strukturę danych (id, kolumny).
     */
    static class UserDao {

        private final Connection connection;

        UserDao(Connection connection) {
            this.connection = connection;
        }

        Optional<String> findById(Long id) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT first_name FROM users WHERE id = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() ? Optional.of(rs.getString("first_name")) : Optional.empty();
                }
            }
        }

        List<String> findAll() throws SQLException {
            List<String> result = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT first_name FROM users ORDER BY id")) {
                while (rs.next()) {
                    result.add(rs.getString("first_name"));
                }
            }
            return result;
        }
    }

    /**
     * 🔹 STYL REPOSITORY: te same dane co UserDao, ale metody nazwane
     * JĘZYKIEM BIZNESU. "findActiveUsers" i "findByEmailDomain" mówią
     * WPROST, po co ich używamy - bez zaglądania do implementacji.
     */
    static class UserRepository {

        private final Connection connection;

        UserRepository(Connection connection) {
            this.connection = connection;
        }

        List<String> findActiveUsers() throws SQLException {
            List<String> result = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT first_name FROM users WHERE active = TRUE ORDER BY first_name")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        result.add(rs.getString("first_name"));
                    }
                }
            }
            return result;
        }

        List<String> findByEmailDomain(String domain) throws SQLException {
            List<String> result = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT first_name FROM users WHERE email LIKE ? ORDER BY first_name")) {
                stmt.setString(1, "%@" + domain);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        result.add(rs.getString("first_name"));
                    }
                }
            }
            return result;
        }
    }
}
