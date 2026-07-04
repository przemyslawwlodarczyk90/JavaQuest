package com.example.javaquest._09_jdbc.Lesson20_Mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class _Lesson20_Mapper {

    public static void main(String[] args) throws SQLException {

        /*
         * ============================================================
         * 📦 CZYM JEST MAPPER?
         * ============================================================
         * MAPPER to klasa (albo zestaw metod), której jedynym zadaniem
         * jest PRZEKŁADANIE danych z jednej postaci na drugą. W kontekście
         * JDBC + DTO + model domenowy, które poznałeś w Lesson17-19,
         * mapper spina to wszystko w jedną, spójną całość. Są DOKŁADNIE
         * 4 kierunki mapowania, z którymi będziesz się spotykać w każdej
         * warstwie dostępu do danych:
         *
         *   1) ResultSet -> Object   (encja z wiersza bazy danych - już
         *      poznane w Lesson17 jako mapRow())
         *   2) Object -> parametry SQL  (odwrotny kierunek - wartości
         *      obiektu domenowego trzeba "zbindować" do PreparedStatement
         *      przed INSERT/UPDATE)
         *   3) DTO -> Entity   (dane wejściowe z Lesson19, np.
         *      CreateUserRequest, trzeba zamienić na encję domenową)
         *   4) Entity -> DTO   (encja domenowa trzeba zamienić na dane
         *      wyjściowe z Lesson19, np. UserResponse, z pominięciem
         *      pól wrażliwych)
         *
         * Poniższa klasa UserMapper grupuje wszystkie 4 kierunki w jednym
         * miejscu - to typowy wzorzec w realnych aplikacjach (czasem
         * nazywany też "Converter" albo, w kontekście Hibernate/JPA,
         * pojawi się w innej formie jako część warstwy DAO/Repository).
         */

        String url = "jdbc:h2:mem:jdbclesson20;DB_CLOSE_DELAY=-1";

        try (Connection connection = DriverManager.getConnection(url)) {

            setUpSchema(connection);

            System.out.println("=== KROK 1: DTO wejsciowe -> Encja (UserMapper.fromCreateRequest) ===");
            CreateUserRequest request = new CreateUserRequest("darek@example.com", "Darek", "SuperTajneHaslo!");
            User newUser = UserMapper.fromCreateRequest(request, hash(request.plainPassword()));
            System.out.println("Encja przygotowana do zapisu: " + newUser);

            System.out.println("\n=== KROK 2: Encja -> parametry SQL (UserMapper.bindParameters) ===");
            long generatedId;
            try (PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO users (email, first_name, password_hash) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                UserMapper.bindParameters(insert, newUser);
                insert.executeUpdate();
                try (ResultSet keys = insert.getGeneratedKeys()) {
                    keys.next();
                    generatedId = keys.getLong(1);
                }
            }
            System.out.println("Zapisano uzytkownika, wygenerowane id=" + generatedId);

            System.out.println("\n=== KROK 3: ResultSet -> Encja (UserMapper.toEntity) ===");
            User loadedUser;
            try (PreparedStatement select = connection.prepareStatement(
                    "SELECT id, email, first_name, password_hash FROM users WHERE id = ?")) {
                select.setLong(1, generatedId);
                try (ResultSet rs = select.executeQuery()) {
                    rs.next();
                    loadedUser = UserMapper.toEntity(rs);
                }
            }
            System.out.println("Odczytana encja: " + loadedUser);

            System.out.println("\n=== KROK 4: Encja -> DTO wyjsciowe (UserMapper.toDto) ===");
            UserResponse response = UserMapper.toDto(loadedUser);
            System.out.println("Odpowiedz API (bez password_hash): " + response);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Mapper = klasa grupująca logikę przekładania danych między
         *   reprezentacjami (ResultSet, encja domenowa, DTO, parametry SQL)
         * - 4 kierunki mapowania w typowej aplikacji JDBC:
         *   1) ResultSet -> Object (odczyt z bazy)
         *   2) Object -> parametry SQL (zapis do bazy)
         *   3) DTO -> Entity (dane wejściowe -> obiekt domenowy)
         *   4) Entity -> DTO (obiekt domenowy -> dane wyjściowe, z
         *      pominięciem pól wrażliwych, patrz Lesson19)
         * - Scalenie tych 4 kierunków w jednej klasie (np. UserMapper)
         *   trzyma logikę konwersji w jednym, łatwym do znalezienia
         *   i przetestowania miejscu, zamiast rozrzuconą po całej
         *   aplikacji
         */
    }

    /** DTO WEJŚCIOWE - dane z formularza rejestracji. */
    private record CreateUserRequest(String email, String firstName, String plainPassword) {
    }

    /** DTO WYJŚCIOWE - dane bezpieczne do pokazania klientowi. */
    private record UserResponse(long id, String email, String firstName) {
    }

    /** Encja domenowa - zawiera dane wewnętrzne aplikacji (password_hash). */
    private record User(long id, String email, String firstName, String passwordHash) {
    }

    /**
     * Mapper skupiający WSZYSTKIE 4 kierunki konwersji między ResultSet,
     * encją domenową (User) i DTO (CreateUserRequest, UserResponse).
     */
    private static class UserMapper {

        /** Kierunek 1: ResultSet -> Object (odczyt wiersza z bazy). */
        static User toEntity(ResultSet resultSet) throws SQLException {
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("password_hash")
            );
        }

        /** Kierunek 2: Object -> parametry SQL (przygotowanie do zapisu). */
        static void bindParameters(PreparedStatement statement, User user) throws SQLException {
            statement.setString(1, user.email());
            statement.setString(2, user.firstName());
            statement.setString(3, user.passwordHash());
        }

        /** Kierunek 3: DTO -> Entity (dane wejściowe -> obiekt domenowy). */
        static User fromCreateRequest(CreateUserRequest request, String passwordHash) {
            long tymczasoweId = 0L; // prawdziwe id nadaje baza danych dopiero przy zapisie
            return new User(tymczasoweId, request.email(), request.firstName(), passwordHash);
        }

        /** Kierunek 4: Entity -> DTO (obiekt domenowy -> dane wyjściowe, bez hasha). */
        static UserResponse toDto(User user) {
            return new UserResponse(user.id(), user.email(), user.firstName());
        }
    }

    private static String hash(String plainPassword) {
        return "hash(" + plainPassword.length() + "znakow)#" + Math.abs(plainPassword.hashCode());
    }

    private static void setUpSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL,
                        first_name VARCHAR(100) NOT NULL,
                        password_hash VARCHAR(255) NOT NULL
                    )
                    """);
        }
    }
}
