-- V1: pierwsza migracja - tworzy tabele 'users' od zera.
-- Konwencja nazewnictwa Flyway: V<wersja>__<opis>.sql (DWA podkreslenia
-- miedzy wersja a opisem). Wersja musi rosnac (V1, V2, V3...), a plik,
-- gdy juz zostanie zastosowany w jakimkolwiek srodowisku, NIE powinien
-- byc pozniej edytowany - Flyway liczy sume kontrolna kazdego pliku i
-- wykryje zmiane jako blad przy nastepnym uruchomieniu migracji.

CREATE TABLE users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL
);

INSERT INTO users (first_name) VALUES ('Jan');
INSERT INTO users (first_name) VALUES ('Anna');
