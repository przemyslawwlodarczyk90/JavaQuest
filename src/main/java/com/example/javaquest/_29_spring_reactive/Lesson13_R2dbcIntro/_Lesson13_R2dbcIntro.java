package com.example.javaquest._29_spring_reactive.Lesson13_R2dbcIntro;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Row;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson13_R2dbcIntro {

    record Autor(long id, String imie, String nazwisko) {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: R2DBC - reaktywny dostep do bazy danych (kontrast z blokujacym JDBC/JPA) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - R2DBC
         * ============================================================
         * `_09_jdbc`/`_12_hibernate`/`_23_spring_data_jpa` uczyly
         * dostepu DO bazy PRZEZ JDBC - API Z NATURY BLOKUJACE
         * (`ResultSet.next()` CZEKA NA odpowiedz Z bazy, BLOKUJAC
         * watek wywolujacy). R2DBC (Reactive Relational Database
         * Connectivity, 2018) TO SPECYFIKACJA (analogiczna DO Reactive
         * Streams Z Lesson02) DLA REAKTYWNEGO dostepu DO baz
         * relacyjnych - `ConnectionFactory`, `Connection`, `Statement`,
         * `Result` ZWRACAJA `Publisher<T>` ZAMIAST blokowac.
         *
         * WAZNE OGRANICZENIE: R2DBC NIE JEST "szybszym JDBC" - baza
         * SQL SAMA W SOBIE MOZE nadal byc waskim gardlem. Zaleta jest
         * INNA: watek APLIKACJI NIE JEST zablokowany PODCZAS
         * oczekiwania NA baze - MOZE OBSLUZYC INNE zadania W tym
         * czasie (Lesson01: C10K problem).
         */
        System.out.println("R2DBC (2018) - specyfikacja reaktywnego dostepu do bazy. Kontrast z blokujacym JDBC (_09_jdbc): Connection/Statement/Result zwracaja Publisher<T>.");

        ConnectionFactory connectionFactory = ConnectionFactories.get(
                "r2dbc:h2:mem:///lesson13db;DB_CLOSE_DELAY=-1");

        demonstrateCreateTableAndInsertReactively(connectionFactory);
        demonstrateQueryingReactively(connectionFactory);
        demonstrateContrastWithBlockingJdbc();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `ConnectionFactories.get(url)` - tworzy `ConnectionFactory`
         *   (odpowiednik `DataSource` Z JDBC, ale REAKTYWNY).
         * - `Mono.from(connectionFactory.create())` - uzyskanie
         *   `Connection` JAKO `Mono` (NIE blokujace!).
         * - `connection.createStatement(sql).execute()` zwraca
         *   `Publisher<Result>` - WYNIKI SA strumieniowane, NIE
         *   czekamy NA CALY `ResultSet` naraz.
         * - Spring Data R2DBC (`spring-boot-starter-data-r2dbc`) BUDUJE
         *   NAD tym `DatabaseClient`/`R2dbcRepository` - WYZSZY poziom
         *   abstrakcji, ANALOGICZNY DO Spring Data JPA
         *   (`_23_spring_data_jpa`), ale REAKTYWNY.
         * - R2DBC NIE zastepuje JDBC WSZEDZIE - TYLKO tam, GDZIE cala
         *   aplikacja (kontroler+serwis+baza) jest reaktywna END-TO-END.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static void demonstrateCreateTableAndInsertReactively(ConnectionFactory connectionFactory) {
        System.out.println("\n--- Tworzenie tabeli i wstawianie danych - REAKTYWNIE ---");

        Mono<Void> operacja = Mono.usingWhen(
                connectionFactory.create(),
                connection -> Mono.from(connection.createStatement(
                                "CREATE TABLE autorzy (id BIGINT AUTO_INCREMENT PRIMARY KEY, imie VARCHAR(100), nazwisko VARCHAR(100))")
                        .execute())
                        .then(Mono.from(connection.createStatement(
                                        "INSERT INTO autorzy (imie, nazwisko) VALUES ('Andrzej', 'Sapkowski')")
                                .execute()))
                        .then(Mono.from(connection.createStatement(
                                        "INSERT INTO autorzy (imie, nazwisko) VALUES ('Stanislaw', 'Lem')")
                                .execute()))
                        .then(),
                Connection::close
        );

        operacja.block(); // .block() TYLKO tu, do weryfikacji W main()
        System.out.println("Tabela 'autorzy' utworzona I 2 wiersze wstawione - CALA operacja BYLA reaktywna (Publisher<Result> na kazdym kroku).");
    }

    private static void demonstrateQueryingReactively(ConnectionFactory connectionFactory) {
        System.out.println("\n--- Zapytanie SELECT - wynik jako Flux<Autor> ---");

        Flux<Autor> autorzy = Flux.usingWhen(
                connectionFactory.create(),
                connection -> Flux.from(connection.createStatement("SELECT id, imie, nazwisko FROM autorzy ORDER BY id").execute())
                        .flatMap(result -> result.map((Row row, io.r2dbc.spi.RowMetadata metadata) ->
                                new Autor(row.get("id", Long.class), row.get("imie", String.class), row.get("nazwisko", String.class)))),
                Connection::close
        );

        List<Autor> wynik = autorzy.collectList().block();
        System.out.println("SELECT * FROM autorzy -> " + wynik);

        assertThat(wynik).hasSize(2);
        assertThat(wynik).extracting(Autor::nazwisko).containsExactly("Sapkowski", "Lem");
    }

    private static void demonstrateContrastWithBlockingJdbc() {
        System.out.println("\n--- Kontrast Z blokujacym JDBC (_09_jdbc) ---");
        System.out.println("JDBC: 'ResultSet rs = statement.executeQuery(sql);' - watek BLOKUJE SIE, DOPOKI baza NIE ODPOWIE.");
        System.out.println("R2DBC: 'Flux.from(connection.createStatement(sql).execute())' - watek WOLNY natychmiast, wyniki PRZYCHODZA ASYNCHRONICZNIE.");
        System.out.println("Spring Data R2DBC (`R2dbcRepository<Autor, Long>`) DAJE TEN SAM poziom wygody, CO Spring Data JPA (`_23_spring_data_jpa`), ale metody zwracaja `Mono<T>`/`Flux<T>` ZAMIAST `T`/`List<T>`.");
    }
}
