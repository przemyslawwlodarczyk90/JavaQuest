-- Migracja IZOLOWANA dla kapsztonu Lesson15 (rozdzial _23_spring_data_jpa) - POZA
-- dzielonym 'db/migration' (patrz pulapka udokumentowana w Lesson14 - podfolder
-- WEWNATRZ 'db/migration' bylby skanowany REKURENCYJNIE i kolidowalby wersja 'V1'
-- z dzielonymi migracjami). Konfigurowane przez
-- 'spring.flyway.locations=classpath:db/migration-lesson15'.

CREATE TABLE authors (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(150) NOT NULL,
    country VARCHAR(100) NOT NULL
);

CREATE TABLE books (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    title              VARCHAR(200) NOT NULL,
    pages              INT NOT NULL,
    price              DECIMAL(10, 2) NOT NULL,
    author_id          BIGINT NOT NULL,
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    created_by         VARCHAR(100),
    last_modified_by   VARCHAR(100),
    CONSTRAINT fk_books_author FOREIGN KEY (author_id) REFERENCES authors (id)
);
