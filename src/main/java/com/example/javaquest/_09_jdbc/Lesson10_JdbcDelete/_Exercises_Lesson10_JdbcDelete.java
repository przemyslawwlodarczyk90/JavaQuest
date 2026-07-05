package com.example.javaquest._09_jdbc.Lesson10_JdbcDelete;

public class _Exercises_Lesson10_JdbcDelete {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_HardDeleteExistingRow {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l10ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "users" (id, name, email) z 3 wierszami. Wykonaj DELETE po id
         * istniejącego wiersza przez PreparedStatement. Wypisz liczbę
         * usuniętych wierszy (powinna być 1) i zawartość tabeli po usunięciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HardDeleteNonexistentIdReturnsZero {
        /*
         * 🧪 Zadanie 2:
         * Na tej samej tabeli "users" wykonaj DELETE WHERE id = 999
         * (nieistniejące). Wypisz liczbę usuniętych wierszy (powinna być 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SoftDeleteSetsActiveFalse {
        /*
         * 🧪 Zadanie 3:
         * Na tabeli "users" (id, name, active BOOLEAN DEFAULT TRUE)
         * wykonaj UPDATE ustawiający active = FALSE dla jednego wiersza
         * (soft delete). Wypisz liczbę zdezaktywowanych wierszy i
         * potwierdź SELECT-em, że wiersz WCIĄŻ fizycznie istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrintAllVsPrintActiveUsers {
        /*
         * 🧪 Zadanie 4:
         * Na tabeli "users" (id, name, active) z 4 wierszami, z których 1
         * jest zdezaktywowany, napisz DWIE metody: printAllUsers (SELECT
         * bez warunku) i printActiveUsers (SELECT WHERE active = TRUE).
         * Wywołaj obie i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HardDeleteDecreasesCount {
        /*
         * 🧪 Zadanie 5:
         * Na tabeli "users" z 5 wierszami wykonaj SELECT COUNT(*) PRZED
         * usunięciem jednego wiersza (hard delete) i PO usunięciu -
         * wypisz obie wartości i potwierdź różnicę o 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SoftDeleteKeepsRowInFullSelect {
        /*
         * 🧪 Zadanie 6:
         * Na tabeli "users" z active BOOLEAN wykonaj soft delete jednego
         * wiersza, a potem wykonaj "SELECT * FROM users" (bez warunku
         * active) - wypisz WSZYSTKIE wiersze, w tym ten "usunięty"
         * (active=FALSE), pokazując że fizycznie nadal istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BulkDeleteMatchingCondition {
        /*
         * 🧪 Zadanie 7:
         * Na tabeli "users" (id, name, active) z 6 wierszami, gdzie 3
         * mają active = FALSE, wykonaj JEDEN DELETE "WHERE active =
         * FALSE" usuwający je wszystkie naraz. Wypisz liczbę usuniętych
         * wierszy (powinna być 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RestoreSoftDeletedRow {
        /*
         * 🧪 Zadanie 8:
         * Na tabeli "users" (active BOOLEAN) wykonaj soft delete
         * jednego wiersza, a następnie "przywróć" go UPDATE-em ustawiającym
         * active = TRUE. Zweryfikuj przez printActiveUsers (z Zadania 4),
         * że wiersz znowu jest widoczny jako aktywny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DeleteAllRowsWithoutWhere {
        /*
         * 🧪 Zadanie 9:
         * Na tabeli "users" z 4 wierszami wykonaj DELETE FROM users (bez
         * WHERE) usuwający WSZYSTKIE wiersze naraz. Wypisz liczbę
         * usuniętych wierszy i potwierdź SELECT COUNT(*) = 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_HardDeleteBlockedByForeignKey {
        /*
         * 🧪 Zadanie 10:
         * Utwórz tabele "users" (id, name) i "orders" (id, user_id
         * REFERENCES users(id)). Wstaw użytkownika i jedno zamówienie
         * powiązane z nim. Spróbuj wykonać HARD DELETE użytkownika -
         * złap SQLException (naruszenie klucza obcego) i wypisz jego
         * komunikat.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SoftDeleteOrThrow {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj własny niesprawdzany wyjątek
         * UserNotFoundException(int id). Napisz metodę
         * softDeleteOrThrow(Connection, int id), która rzuca ten wyjątek,
         * jeśli UPDATE ustawiający active=FALSE zwróci 0. Przetestuj dla
         * istniejącego (sukces) i nieistniejącego id (wyjątek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_HardDeleteOrThrow {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę hardDeleteOrThrow(Connection, int id) analogiczną
         * do Zadania 11, ale wykonującą fizyczny DELETE. Przetestuj dla
         * istniejącego i nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_BulkSoftDeleteByDepartment {
        /*
         * 🧪 Zadanie 13:
         * Na tabeli "employees" (id, name, department, active) z co
         * najmniej 6 wierszami wykonaj JEDEN UPDATE dezaktywujący
         * WSZYSTKICH pracowników z danego departamentu (WHERE department
         * = ?). Wypisz liczbę zdezaktywowanych wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SoftDeleteWithAuditEntry {
        /*
         * 🧪 Zadanie 14:
         * Dodaj tabelę "audit_log" (id, message). Napisz metodę
         * softDeleteWithAudit(Connection, int id), która wykonuje soft
         * delete ORAZ wstawia wpis do audit_log ("Uzytkownik id=X
         * zdezaktywowany"). Zweryfikuj obie tabele po wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RestoreByIdReusableMethod {
        /*
         * 🧪 Zadanie 15:
         * Napisz reużywalną metodę restoreById(Connection, int id)
         * (UPDATE active=TRUE WHERE id=?). Zdezaktywuj 3 użytkowników,
         * przywróć 1 z nich, i wypisz finalną listę aktywnych/nieaktywnych
         * (printAllUsers z Zadania 4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ActiveOnlyQueryHelper {
        /*
         * 🧪 Zadanie 16:
         * Napisz generyczną metodę findActiveOnly(Connection, String
         * baseSelectSql), która dokleja " WHERE active = TRUE" do
         * podanego zapytania SELECT i wykonuje je, wypisując wynik.
         * Przetestuj na 2 różnych zapytaniach bazowych (np. inna
         * kolejność kolumn/ORDER BY).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CascadeSoftDeleteParentAndChild {
        /*
         * 🧪 Zadanie 17:
         * Na tabelach "users" (id, active) i "profiles" (id, user_id,
         * active) wykonaj DWA UPDATE-y w metodzie
         * deactivateUserAndProfile(Connection, int userId): jeden
         * dezaktywujący users, drugi profiles WHERE user_id = ?. Wypisz
         * liczby zmienionych wierszy w obu tabelach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_HardDeleteFreesUniqueEmailSoftDeleteDoesNot {
        /*
         * 🧪 Zadanie 18:
         * Na tabeli "users" (id, email UNIQUE, active) wstaw użytkownika
         * z konkretnym email, a potem: (a) SOFT delete go i spróbuj
         * wstawić NOWEGO użytkownika z TYM SAMYM email - złap
         * SQLException (naruszenie UNIQUE, bo stary wiersz WCIĄŻ
         * istnieje); (b) w innej bazie/tabeli HARD delete go i spróbuj
         * wstawić nowego z tym samym email - powinno się udać (miejsce
         * "zwolnione"). Wypisz oba wyniki i skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PurgeOldSoftDeletedRows {
        /*
         * 🧪 Zadanie 19:
         * Na tabeli "users" (id, name, active) z 3 wierszami active=FALSE
         * i 2 active=TRUE, napisz metodę purgeInactive(Connection) trwale
         * usuwającą (HARD DELETE) WSZYSTKIE wiersze active=FALSE. Wypisz
         * liczbę usuniętych wierszy i finalny stan tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_IdentityGapAfterHardDeleteAndReinsert {
        /*
         * 🧪 Zadanie 20:
         * Na tabeli "users" (id INT GENERATED ALWAYS AS IDENTITY, name)
         * wstaw 3 wiersze, HARD DELETE środkowy, wstaw NOWY wiersz. Wypisz
         * wszystkie id (SELECT ORDER BY id) - zaobserwuj "dziurę" w
         * numeracji (usunięte id nie jest reużywane).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SoftDeleteUserRepository {
        /*
         * 🧪 Zadanie 21:
         * Napisz klasę UserRepository z metodami deactivate(id),
         * restore(id), findAllActive() i findAll() (włącznie z
         * nieaktywnymi). Zademonstruj pełny scenariusz: 5 użytkowników,
         * dezaktywacja 2, findAllActive() (3 wyniki), restore 1,
         * findAllActive() ponownie (4 wyniki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareForeignKeyBehaviorHardVsSoft {
        /*
         * 🧪 Zadanie 22:
         * Na tabelach "users" i "orders" (order_id REFERENCES users(id))
         * z użytkownikiem MAJĄCYM zamówienie, porównaj: (a) próba HARD
         * DELETE użytkownika - SQLException (naruszenie FK); (b) SOFT
         * DELETE tego samego użytkownika - działa bez problemu, bo nie
         * dotyka integralności referencyjnej. Wypisz oba wyniki jako
         * jasne porównanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TrashPatternWithPurge {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj wzorzec "kosza": metoda moveToTrash(id) (soft
         * delete), metoda listTrash() (SELECT WHERE active = FALSE) oraz
         * metoda emptyTrash() (HARD DELETE WHERE active = FALSE).
         * Zademonstruj pełny cykl: 4 użytkowników, 2 przeniesione do
         * kosza, listTrash() (2 wyniki), emptyTrash(), listTrash()
         * ponownie (0 wyników), findAll() (tylko 2 pozostałe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TimingDeleteReinsertVsSoftToggle {
        /*
         * 🧪 Zadanie 24:
         * Zmierz czas 50 cykli "hard delete + reinsert tego samego
         * użytkownika" oraz 50 cykli "soft delete + restore (toggle
         * active)" na osobnej bazie każdy. Wypisz oba czasy i porównaj,
         * które podejście jest szybsze i dlaczego (mniej operacji I/O
         * przy samym UPDATE flagi niż przy DELETE+INSERT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConcurrentHardDeleteSameRowIdempotent {
        /*
         * 🧪 Zadanie 25:
         * Na tabeli "users" z jednym wierszem uruchom 2 wątki, każdy z
         * WŁASNYM Connection, próbujące HARD DELETE tego samego id w
         * tym samym momencie. Poczekaj na zakończenie obu (join z
         * limitem czasu) i wypisz sumę liczby usuniętych wierszy z obu
         * wątków (powinna być 1 - drugi wątek dostanie 0, bez wyjątku),
         * potwierdzając, że DELETE jest idempotentny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SoftDeleteWithTimestampAudit {
        /*
         * 🧪 Zadanie 26:
         * Na tabeli "users" (id, name, active, deactivated_at TIMESTAMP)
         * napisz metodę deactivateWithTimestamp(Connection, int id),
         * ustawiającą active=FALSE ORAZ deactivated_at = CURRENT_TIMESTAMP
         * w JEDNYM UPDATE. Zweryfikuj, że deactivated_at zostało
         * wypełnione (niepuste) po wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FullLifecycleCreateReadDeleteRestoreHardDelete {
        /*
         * 🧪 Zadanie 27:
         * Przeprowadź pełny cykl życia jednego użytkownika: CREATE
         * (insert), READ (select, potwierdź obecność), SOFT DELETE
         * (potwierdź active=FALSE), RESTORE (potwierdź active=TRUE
         * ponownie), HARD DELETE (potwierdź brak wiersza w SELECT).
         * Wypisz stan po każdym z 5 kroków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ManualCascadeHardDelete {
        /*
         * 🧪 Zadanie 28:
         * Na tabelach "users" (id) i "orders" (id, user_id REFERENCES
         * users(id)) napisz metodę deleteUserCascade(Connection, int
         * userId), która NAJPIERW usuwa wszystkie powiązane zamówienia
         * (DELETE FROM orders WHERE user_id = ?), a POTEM usuwa samego
         * użytkownika - unikając naruszenia klucza obcego. Przetestuj na
         * użytkowniku z 3 zamówieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SmartDeleteDecideSoftOrHard {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę smartDelete(Connection, int userId), która
         * SPRAWDZA (SELECT COUNT(*) FROM orders WHERE user_id = ?), czy
         * użytkownik ma powiązane zamówienia: jeśli TAK - wykonuje SOFT
         * DELETE (zachowanie integralności historii), jeśli NIE -
         * wykonuje HARD DELETE (bezpieczne, bo nic się nie odwołuje).
         * Przetestuj dla użytkownika z zamówieniami i bez.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneUserLifecycleManager {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klasę UserLifecycleManager łączącą: smartDelete
         * (Zadanie 29), restore, audit log przy każdej zmianie statusu,
         * oraz metodę generateReport() wypisującą liczbę użytkowników
         * aktywnych, nieaktywnych (soft-deleted) i całkowitą liczbę
         * wpisów w audit_log. Zademonstruj na 5 użytkownikach (część z
         * zamówieniami, część bez) pełny scenariusz zarządzania cyklem
         * życia z finalnym raportem.
         */
        public static void main(String[] args) { }
    }
}
