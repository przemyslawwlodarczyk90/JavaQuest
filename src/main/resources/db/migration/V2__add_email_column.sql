-- V2: druga migracja - modyfikuje SCHEMA istniejacej tabeli 'users'
-- (dodaje kolumne email) zamiast tworzyc ja od nowa. To typowy przyklad
-- EWOLUCJI schematu w czasie: V1 stworzyla tabele w wersji "1.0", V2
-- dokladana jest PO fakcie, gdy pojawia sie nowa potrzeba biznesowa
-- (np. wysylka powiadomien mailowych do uzytkownikow).

ALTER TABLE users ADD COLUMN email VARCHAR(150);

-- Uzupelniamy kolumne dla istniejacych (juz wstawionych w V1) wierszy -
-- w prawdziwym projekcie takie "backfille" danych historycznych to
-- czesty element migracji dodajacej NOT NULL kolumne.
UPDATE users SET email = LOWER(first_name) || '@example.com';
