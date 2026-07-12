-- V2 - ewolucja schematu 'tasks' PO fakcie, dokladnie jak V2 w dzielonym
-- 'db/migration' (add_email_column) - dodajemy kolumne Z wartoscia domyslna,
-- zeby istniejace (juz wstawione W V1) wiersze DALEJ byly poprawne.

ALTER TABLE tasks ADD COLUMN priority INT NOT NULL DEFAULT 3;
