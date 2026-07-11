package com.example.javaquest._19_security_basics.Lesson13_SqlInjectionDeepDive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class _Lesson13_SqlInjectionDeepDive {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: SQL INJECTION - POGLEBIENIE ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z _09_jdbc/Lesson14_SqlInjection
         * ============================================================
         * Podstawowa lekcja JDBC pokazala GENEZE problemu (konkatenacja
         * inputu uzytkownika do zapytania SQL) i PODSTAWOWA obrone
         * (PreparedStatement z parametrami). Ta lekcja ZAKLADA, ze to
         * juz wiesz, i idzie GLEBIEJ - pokazuje 3 KONKRETNE TECHNIKI,
         * ktorymi atakujacy REALNIE wykorzystuje podatnosc: UNION-based,
         * blind boolean-based, i second-order injection.
         */
        System.out.println("Ta lekcja poglebia _09_jdbc/Lesson14 - zaklada znajomosc podstaw (konkatenacja = zle, PreparedStatement = dobrze).");

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:lesson13;DB_CLOSE_DELAY=-1", "sa", "")) {
            setupSchema(connection);

            demonstrateUnionBasedInjection(connection);
            demonstrateBlindBooleanBasedInjection(connection);
            demonstrateSecondOrderInjection(connection);
        }

        /*
         * ============================================================
         * 🔹 UNION-BASED INJECTION
         * ============================================================
         * Atakujacy dolacza WLASNE zapytanie `UNION SELECT ...` do
         * istniejacego, PODATNEGO zapytania - jesli liczba i typy kolumn
         * sie zgadzaja, wynik DRUGIEGO zapytania (np. z INNEJ tabeli,
         * takiej jak hasla uzytkownikow) pojawia sie WSROD normalnych
         * wynikow - atakujacy "wyciaga" dane, ktorych NIGDY nie powinien
         * zobaczyc.
         *
         * ============================================================
         * 🔹 BLIND BOOLEAN-BASED INJECTION
         * ============================================================
         * Gdy aplikacja NIE pokazuje bezposrednio wynikow zapytania
         * (np. tylko "znaleziono"/"nie znaleziono"), atakujacy WCIAZ
         * moze wydobyc dane - zadajac serie pytan TAK/NIE (np. "czy
         * pierwsza litera hasla admina to 'a'? 'b'? ...") i obserwujac
         * ROZNICE w odpowiedzi. Wolniejsze niz UNION-based, ale
         * DZIALA nawet gdy aplikacja "nic nie pokazuje".
         *
         * ============================================================
         * 🔹 SECOND-ORDER INJECTION
         * ============================================================
         * NAJBARDZIEJ podstepny wariant - dane sa BEZPIECZNIE zapisane
         * (przez PreparedStatement!) PRZY PIERWSZYM uzyciu, ale POZNIEJ,
         * w INNYM miejscu kodu, sa odczytane z bazy i (przez pomylke
         * programisty) skonkatenowane do NOWEGO zapytania SQL. Sama
         * obecnosc PreparedStatement przy ZAPISIE NIE WYSTARCZY - KAZDE
         * uzycie danych w zapytaniu SQL musi byc bezpieczne, niezaleznie
         * od tego, skad te dane pochodza (nawet z WLASNEJ bazy!).
         */
        System.out.println("\nUNION-based (dolaczenie wlasnego SELECT), blind boolean-based (pytania TAK/NIE), second-order (podatnosc przy DRUGIM uzyciu danych).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - UNION-based - atakujacy dopasowuje liczbe/typy kolumn i
         *   "dolacza" dane z innej tabeli do wynikow.
         * - Blind boolean-based - wydobywanie danych PYTANIE PO PYTANIU,
         *   nawet bez bezposredniego wyswietlania wynikow.
         * - Second-order - podatnosc ujawnia sie przy PONOWNYM uzyciu
         *   danych z bazy w NOWYM, niezabezpieczonym zapytaniu.
         * - JEDYNA niezawodna obrona we WSZYSTKICH przypadkach:
         *   PreparedStatement z parametrami ZA KAZDYM RAZEM, gdy dane
         *   (jakiekolwiek, z JAKIEGOKOLWIEK zrodla) trafiaja do
         *   zapytania SQL - NIE TYLKO przy pierwszym zapisie.
         * - Dodatkowa warstwa: zasada najmniejszych uprawnien (Lesson07)
         *   na koncie bazodanowym aplikacji - nawet udana injekcja ma
         *   OGRANICZONY zasieg, jesli konto NIE MA uprawnien np. do
         *   `DROP TABLE`.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static void setupSchema(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE users (id INT PRIMARY KEY, username VARCHAR(50), password_hash VARCHAR(100), is_admin BOOLEAN)");
            statement.execute("INSERT INTO users VALUES (1, 'jan.kowalski', 'a1b2c3hash', FALSE)");
            statement.execute("INSERT INTO users VALUES (2, 'admin', 'SEKRETNY_HASH_ADMINA_9f8e7d', TRUE)");

            statement.execute("CREATE TABLE products (id INT PRIMARY KEY, name VARCHAR(50), price DECIMAL(10,2))");
            statement.execute("INSERT INTO products VALUES (1, 'Laptop', 3500.00)");
            statement.execute("INSERT INTO products VALUES (2, 'Mysz', 80.00)");

            statement.execute("CREATE TABLE comments (id INT PRIMARY KEY, author VARCHAR(50), text VARCHAR(200))");
        }
    }

    private static void demonstrateUnionBasedInjection(Connection connection) throws Exception {
        System.out.println("\n=== UNION-BASED INJECTION ===");

        String userSearchTerm = "Laptop' UNION SELECT id, password_hash, 0 FROM users --";
        String vulnerableQuery = "SELECT id, name, price FROM products WHERE name = '" + userSearchTerm + "'";
        // UWAGA: kolumny products (id:INT, name:VARCHAR, price:DECIMAL) i wstrzykniete MUSZA pasowac
        // LICZBA i TYPEM - dlatego password_hash (VARCHAR) trafia w miejsce "name", a "0" (liczba) w
        // miejsce "price" - atakujacy w praktyce dobiera to metoda prob i bledow (patrz Zadanie 18).
        System.out.println("Podatne zapytanie (konkatenacja): " + vulnerableQuery);

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(vulnerableQuery)) {
            System.out.println("Wyniki 'wyszukiwania produktow' (zawieraja WYCIEKNIETE hasla!):");
            while (resultSet.next()) {
                System.out.println("  id=" + resultSet.getInt(1) + ", nazwa/username=" + resultSet.getString(2) + ", cena/hash=" + resultSet.getString(3));
            }
        }
        System.out.println("-> atakujacy WYDOBYL zawartosc TABELI users (username + password_hash) przez okno 'wyszukiwarki produktow'!");
    }

    private static void demonstrateBlindBooleanBasedInjection(Connection connection) throws Exception {
        System.out.println("\n=== BLIND BOOLEAN-BASED INJECTION ===");

        String secretPrefix = "SEKRETNY_HASH_ADMINA_9f8e7d".substring(0, 1); // atakujacy tego NIE ZNA - wydobywa PO ZNAKU
        System.out.println("Aplikacja pokazuje TYLKO 'ZNALEZIONO'/'NIE ZNALEZIONO' - atakujacy zgaduje 1. znak hasha admina litera po literze.");

        StringBuilder discoveredCharacters = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";

        for (char candidate : alphabet.toCharArray()) {
            String probeQuery = "SELECT 1 FROM users WHERE username = 'admin' AND SUBSTRING(password_hash, 1, 1) = '" + candidate + "'";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(probeQuery)) {
                if (resultSet.next()) {
                    discoveredCharacters.append(candidate);
                    System.out.println("Pytanie 'czy 1. znak to \"" + candidate + "\"?' -> ZNALEZIONO (prawda) - odgadniety znak: " + candidate);
                    break;
                }
            }
        }
        System.out.println("Po " + alphabet.length() + " (w najgorszym razie) pytaniach TAK/NIE, atakujacy zna 1. znak hasha: '"
                + discoveredCharacters + "' - powtarzajac dla kolejnych pozycji, odtworzy CALY hash bez WIDZENIA go bezposrednio.");
    }

    private static void demonstrateSecondOrderInjection(Connection connection) throws Exception {
        System.out.println("\n=== SECOND-ORDER INJECTION ===");

        String maliciousUsername = "admin'; DROP TABLE products; --";

        // KROK 1: zapis BEZPIECZNY (PreparedStatement) - programista zrobil to "dobrze" na pierwszy rzut oka.
        try (var preparedStatement = connection.prepareStatement("INSERT INTO comments (id, author, text) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, maliciousUsername);
            preparedStatement.setString(3, "Swietny sklep!");
            preparedStatement.executeUpdate();
        }
        System.out.println("Krok 1 (BEZPIECZNY zapis przez PreparedStatement): zapisano autora komentarza: " + maliciousUsername);

        // KROK 2: PROBLEM - inny fragment kodu (np. "wyslij powiadomienie do autora") ODCZYTUJE
        // te sama wartosc z bazy i KONKATENUJE ja do NOWEGO zapytania, ZAPOMINAJAC, ze pochodzi z bazy,
        // a NIE bezposrednio od uzytkownika - "przeciez to juz jest w naszej bazie, wiec jest bezpieczne"
        // to BLEDNE zalozenie.
        String authorFromDatabase;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT author FROM comments WHERE id = 1")) {
            resultSet.next();
            authorFromDatabase = resultSet.getString("author");
        }

        String vulnerableSecondQuery = "SELECT COUNT(*) FROM products WHERE name = '" + authorFromDatabase + "'"; // "niewinne" uzycie gdzie indziej
        System.out.println("Krok 2 (PODATNE uzycie TYCH SAMYCH danych GDZIE INDZIEJ w kodzie): " + vulnerableSecondQuery);

        try (Statement statement = connection.createStatement()) {
            statement.execute(vulnerableSecondQuery); // wykona SIE TEZ 'DROP TABLE products' doklejone przez ';'!
        } catch (Exception e) {
            System.out.println("Wynik proby (H2 moze odrzucic wielokrotne polecenia w 1 Statement, zalezy od sterownika): " + e.getMessage());
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM products")) {
            resultSet.next();
            System.out.println("Liczba wierszy w 'products' PO ataku: " + resultSet.getInt(1)
                    + " (jesli 0 lub blad - tabela zostala USUNIETA/uszkodzona przez wstrzykniete 'DROP TABLE').");
        } catch (Exception e) {
            System.out.println("Tabela 'products' zostala USUNIETA przez second-order injection: " + e.getMessage());
        }
        System.out.println("-> DANE POCHODZACE Z WLASNEJ BAZY sa RÓWNIE niebezpieczne jak bezposredni input uzytkownika,");
        System.out.println("   jesli trafily tam KIEDYKOLWIEK od uzytkownika - KAZDE uzycie w SQL wymaga PreparedStatement.");
    }
}
