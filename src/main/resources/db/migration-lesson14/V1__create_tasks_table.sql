-- Migracja IZOLOWANA dla Lesson14 (rozdzial _23_spring_data_jpa) - celowo w PODFOLDERZE
-- 'db/migration/lesson14', NIE w dzielonym 'db/migration' (tam zyja V1/V2 wspolne dla
-- CALEGO rozdzialu, patrz teoria Lesson14 - demonstrateDefaultFlywayLocationAndSharedMigrations).
-- Konfigurowane przez 'spring.flyway.locations=classpath:db/migration/lesson14'.

CREATE TABLE tasks (
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    done  BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO tasks (title, done) VALUES ('Napisac lekcje o Flyway', FALSE);
INSERT INTO tasks (title, done) VALUES ('Zweryfikowac migracje', TRUE);
