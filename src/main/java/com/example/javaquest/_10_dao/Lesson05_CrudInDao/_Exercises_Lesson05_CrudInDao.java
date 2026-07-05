package com.example.javaquest._10_dao.Lesson05_CrudInDao;

public class _Exercises_Lesson05_CrudInDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateThreeUsers {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l05ex01;DB_CLOSE_DELAY=-1" z tabelą users (id, email,
         * first_name), zaimplementuj UserJdbcDao.save(User) (CREATE). Zapisz 3
         * użytkowników i wypisz każdego z wygenerowanym id - to jedyna operacja CRUD
         * użyta w tym zadaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadFindByIdAndFindAll {
        /*
         * 🧪 Zadanie 2:
         * Rozbuduj UserJdbcDao o findById(long) i findAll() (READ). Zapisz 3
         * użytkowników, wywołaj findById dla drugiego z nich oraz findAll(), i
         * wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UpdateChangesEmail {
        /*
         * 🧪 Zadanie 3:
         * Rozbuduj UserJdbcDao o update(User) (UPDATE). Zapisz użytkownika, zmień
         * jego e-mail i wywołaj update, a następnie zweryfikuj zmianę przez
         * findById - wypisz stan PRZED i PO update.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DeleteRemovesRow {
        /*
         * 🧪 Zadanie 4:
         * Rozbuduj UserJdbcDao o deleteById(long) (DELETE). Zapisz 3 użytkowników,
         * usuń środkowego i wypisz findAll() przed i po usunięciu - lista powinna
         * mieć 2 elementy po operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExistsByIdTrueAndFalse {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj existsById(long id) (SELECT COUNT(*) ... WHERE id = ?).
         * Sprawdź wynik dla id istniejącego użytkownika oraz dla id=999
         * (nieistniejące) - wypisz oba wyniki boolean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CountBeforeAndAfterInsert {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj count() (SELECT COUNT(*) FROM users). Wypisz count() na
         * starcie (0), wstaw 4 użytkowników i wypisz count() po wstawieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FullCrudCycleOneUser {
        /*
         * 🧪 Zadanie 7:
         * Dla JEDNEGO użytkownika przejdź przez CAŁY cykl CRUD po kolei: save,
         * findById, update, findById (weryfikacja), deleteById, existsById
         * (weryfikacja usunięcia) - z komentarzem przed każdym krokiem, jaka
         * operacja CRUD jest wykonywana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ProductCrudFromScratch {
        /*
         * 🧪 Zadanie 8:
         * Analogicznie do UserJdbcDao z lekcji, napisz OD ZERA ProductJdbcDao
         * (tabela products: id, name, price) z pełnym kompletem: save, findById,
         * findAll, update, deleteById, existsById, count. Przetestuj wszystkie 7
         * metod na 3 produktach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExistsByIdBeforeUpdate {
        /*
         * 🧪 Zadanie 9:
         * Napisz logikę w main (nie w DAO): przed wywołaniem update(user), sprawdź
         * existsById(user.id()) - jeśli false, wypisz komunikat "Nie mozna
         * zaktualizowac - uzytkownik nie istnieje" i NIE wywołuj update. Przetestuj
         * dla istniejącego i sfabrykowanego (nieistniejącego) id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CountAfterEachCrudOperation {
        /*
         * 🧪 Zadanie 10:
         * Wypisuj count() PO KAŻDEJ operacji CRUD w sekwencji: 3x save, 1x
         * deleteById, 1x save - żeby zobaczyć, jak zmienia się liczba rekordów
         * krok po kroku (3, potem 2, potem 3).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ExistsByIdVsFindByIdPerformance {
        /*
         * 🧪 Zadanie 11:
         * Zmierz (System.nanoTime()) czas wykonania 1000 wywołań existsById(id)
         * oraz 1000 wywołań findById(id).isPresent() dla tego samego, istniejącego
         * id. Wypisz porównanie czasów i skomentuj, czy existsById jest szybsze
         * (mniej danych do przesłania z bazy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UpdateNonExistentIdNoEffect {
        /*
         * 🧪 Zadanie 12:
         * Wywołaj update(User) z id, które NIE istnieje w tabeli (np. 999) i
         * sprawdź, ile wierszy faktycznie zmieniła operacja (executeUpdate()
         * zwraca liczbę zmienionych wierszy - zwróć ją z update() jako int
         * zamiast void i wypisz wynik: powinno być 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DeleteNonExistentIdNoError {
        /*
         * 🧪 Zadanie 13:
         * Wywołaj deleteById(999) (nieistniejące id) i sprawdź, że metoda NIE
         * rzuca wyjątku - wypisz count() przed i po tej operacji (powinien być
         * niezmieniony, bo nic nie zostało usunięte).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BatchDeleteMultipleIds {
        /*
         * 🧪 Zadanie 14:
         * Zapisz 5 użytkowników, zbierz ich id do List<Long>, a następnie napisz
         * metodę deleteByIds(List<Long> ids) wywołującą deleteById dla każdego
         * elementu listy. Usuń 3 z 5 użytkowników i sprawdź count() na końcu
         * (powinno być 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CountUsedForPagination {
        /*
         * 🧪 Zadanie 15:
         * Zapisz 25 użytkowników. Użyj count() do obliczenia liczby "stron" po 10
         * elementów (np. Math.ceil(count / 10.0)) i wypisz wynik - to wstęp do
         * tematu paginacji z dalszych lekcji rozdziału.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UpdatePartialFieldsOnly {
        /*
         * 🧪 Zadanie 16:
         * Dodaj do UserJdbcDao metodę updateEmailOnly(long id, String newEmail) -
         * UPDATE users SET email = ? WHERE id = ? (bez zmiany first_name, w
         * odróżnieniu od update(User) aktualizującego wszystkie kolumny). Porównaj
         * SQL obu metod w komentarzu i przetestuj updateEmailOnly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExistsByIdUsedAsGuardClause {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę safeDeleteById(UserJdbcDao dao, long id) w main, która
         * WYPISUJE komunikat i NIE wywołuje deleteById, jeśli existsById(id) ==
         * false - w przeciwnym razie usuwa i wypisuje "Usunieto". Przetestuj dla
         * istniejącego i nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FindAllAfterFullCrudSequence {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj sekwencję: save 4 userów -> update jednego -> deleteById innego
         * -> save jeszcze jednego. Po CAŻDYM kroku wywołaj findAll() i wypisz
         * pełną zawartość tabeli, żeby zobaczyć, jak stan bazy zmienia się
         * krok po kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CrudForProductWithExistsByName {
        /*
         * 🧪 Zadanie 19:
         * Do ProductJdbcDao (z Zadania 8) dodaj existsByName(String name) - SELECT
         * COUNT(*) WHERE name = ?. Napisz logikę insertIfNotExists(name, price) w
         * main, która wywołuje save TYLKO, gdy existsByName zwraca false.
         * Przetestuj na 4 nazwach, z czego 2 powtórzone.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareCountVsFindAllSize {
        /*
         * 🧪 Zadanie 20:
         * Zapisz 10 użytkowników. Porównaj wynik count() z findAll().size() -
         * powinny być identyczne. Zmierz (System.nanoTime()) różnicę w czasie
         * wykonania obu podejść dla 500 powtórzeń każdego i skomentuj, które jest
         * bardziej efektywne, gdy interesuje nas TYLKO liczba, nie same dane.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCrudWithValidationLayer {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę registerUser(UserJdbcDao dao, email, firstName) w main
         * łączącą walidację e-maila (musi zawierać "@") z existsById-podobną
         * regułą (sprawdź duplikat po e-mailu przez własne zapytanie SQL) PRZED
         * wywołaniem save. Przetestuj na 5 przypadkach o różnych wynikach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CrudAuditTrail {
        /*
         * 🧪 Zadanie 22:
         * Dodaj tabelę audit_log (id, action, user_id). Za każdym razem, gdy w
         * main wywołujesz save/update/deleteById na UserJdbcDao, wstaw osobny
         * wpis do audit_log (np. "CREATE", "UPDATE", "DELETE" + id użytkownika).
         * Na końcu wypisz cały audit_log i pokaż, że odpowiada dokładnie
         * wykonanym operacjom CRUD.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConcurrentCrudOnSameRow {
        /*
         * 🧪 Zadanie 23:
         * Zapisz jednego użytkownika. Uruchom DWA wątki z WŁASNYMI Connection do
         * tej samej bazy - jeden wykonuje 20x update() zmieniając first_name, drugi
         * 20x findById() i wypisuje aktualną wartość. Poczekaj na zakończenie obu
         * (join z limitem czasu) i sprawdź, że aplikacja nie się nie zawiesza i
         * kończy się poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SoftDeleteInsteadOfHardDelete {
        /*
         * 🧪 Zadanie 24:
         * Dodaj do tabeli users kolumnę active BOOLEAN DEFAULT TRUE. Zamiast
         * prawdziwego deleteById, napisz deactivateById(long id) (UPDATE ... SET
         * active = FALSE). Zaimplementuj findAllActive() (WHERE active = TRUE) i
         * pokaż różnicę między "usuniętym" (soft delete) a naprawdę usuniętym
         * wierszem - dane wciąż istnieją w tabeli, ale nie są zwracane przez
         * findAllActive().
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CrudWithOptimisticLockingVersionColumn {
        /*
         * 🧪 Zadanie 25:
         * Dodaj do tabeli users kolumnę version INT DEFAULT 0. Zaimplementuj
         * update(User user, int expectedVersion) - UPDATE ... SET ..., version =
         * version + 1 WHERE id = ? AND version = ?. Jeśli executeUpdate() zwróci
         * 0 (bo version się nie zgadza - ktoś inny zaktualizował wiersz w
         * międzyczasie), rzuć własny wyjątek OptimisticLockException. Zademonstruj
         * udaną aktualizację i próbę ze złą wersją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BulkCountByConditionSql {
        /*
         * 🧪 Zadanie 26:
         * Dodaj do tabeli users kolumnę department VARCHAR(50). Napisz
         * countByDepartment(String dept) (SELECT COUNT(*) WHERE department = ?)
         * jako rozszerzenie idei count(). Zapisz 10 użytkowników w 3 różnych
         * działach i wypisz liczbę dla każdego działu, sumując je i porównując
         * z ogólnym count().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CrudTransactionalRollbackOnPartialFailure {
        /*
         * 🧪 Zadanie 27:
         * Wykonaj w JEDNEJ transakcji (setAutoCommit(false)) sekwencję: update
         * jednego użytkownika (poprawny) + deleteById nieistniejącego (bez błędu,
         * bo deleteById jest "bezpieczne") + insert z NULL w NOT NULL kolumnie
         * (rzuci SQLException). Złap wyjątek, wywołaj rollback() i sprawdź, że
         * PIERWSZY (poprawny) update też został cofnięty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GenericCrudInterfaceExtraction {
        /*
         * 🧪 Zadanie 28:
         * Wydziel wspólny interfejs SimpleCrud<T> z metodami T save(T), Optional<T>
         * findById(long), List<T> findAll(), void update(T), void deleteById(long),
         * boolean existsById(long), long count(). Zaimplementuj go DWA razy:
         * UserJdbcDao i ProductJdbcDao. Napisz jedną metodę generyczną
         * printSummary(SimpleCrud<?> dao) wypisującą count() dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CrudPerformanceUnderLoad {
        /*
         * 🧪 Zadanie 29:
         * Zmierz łączny czas (System.nanoTime()) wykonania 200 pełnych cykli CRUD
         * (save + findById + update + deleteById dla jednego użytkownika na raz,
         * powtórzone 200 razy) i wypisz średni czas jednego cyklu w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCrudMiniInventorySystem {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj mini-system inwentarza: tabela items (id, name, quantity).
         * ItemJdbcDao z pełnym kompletem CRUD + existsById + count. Zasymuluj
         * scenariusz: dodaj 5 produktów, zmień stan magazynowy 3 z nich (update),
         * usuń 1 (deleteById), sprawdź istnienie usuniętego (existsById -> false),
         * wypisz finalny stan (findAll()) i count(). Podsumuj wynik w jednym
         * sformatowanym raporcie tekstowym.
         */
        public static void main(String[] args) { }
    }
}
