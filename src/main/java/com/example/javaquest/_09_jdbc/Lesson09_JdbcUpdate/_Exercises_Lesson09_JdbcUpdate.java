package com.example.javaquest._09_jdbc.Lesson09_JdbcUpdate;

public class _Exercises_Lesson09_JdbcUpdate {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicUpdateExistingRow {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l09ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "users" (id, name, email) z 2 wierszami. Wykonaj UPDATE
         * zmieniający email jednego z nich przez PreparedStatement.
         * Wypisz liczbę zaktualizowanych wierszy (powinna być 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UpdateNonexistentIdReturnsZero {
        /*
         * 🧪 Zadanie 2:
         * Na tej samej tabeli "users" wykonaj UPDATE z WHERE id = 999
         * (nieistniejące). Wypisz liczbę zmienionych wierszy (powinna
         * być 0) - zapytanie wykonało się bez błędu, ale nic nie
         * zmieniło.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UpdateEmailReusableMethod {
        /*
         * 🧪 Zadanie 3:
         * Napisz metodę int updateEmail(Connection, int id, String
         * newEmail) (jak w lekcji). Wywołaj ją dla istniejącego id
         * (wypisz wynik i zweryfikuj SELECT-em) i dla nieistniejącego id
         * (wypisz wynik 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UpdateMultipleColumnsAtOnce {
        /*
         * 🧪 Zadanie 4:
         * Na tabeli "users" (id, name, email) wykonaj JEDEN UPDATE
         * zmieniający ORAZ name, ORAZ email w tym samym zapytaniu
         * ("SET name = ?, email = ? WHERE id = ?"). Zweryfikuj SELECT-em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BulkUpdateMultipleRows {
        /*
         * 🧪 Zadanie 5:
         * Na tabeli "users" (id, name, email, active BOOLEAN) z 5
         * wierszami wykonaj JEDEN UPDATE bez warunku po id (np. "SET
         * active = FALSE WHERE active = TRUE"), dotykający WIELU
         * wierszy naraz. Wypisz liczbę zmienionych wierszy (powinna być
         * >1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VerifyDataChangedAfterUpdate {
        /*
         * 🧪 Zadanie 6:
         * Odczytaj wiersz PRZED update, wykonaj UPDATE zmieniający jedną
         * kolumnę, odczytaj wiersz PO update i wypisz obie wersje
         * (przed/po), żeby zobaczyć różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UpdateWithSameValueStillCountsAsMatch {
        /*
         * 🧪 Zadanie 7:
         * Wykonaj UPDATE ustawiający kolumnę na TĘ SAMĄ wartość, którą
         * już ma (bez faktycznej zmiany danych). Wypisz liczbę zmienionych
         * wierszy - powinna wynosić 1, bo WHERE dopasowało wiersz,
         * niezależnie od tego, czy wartość faktycznie się zmieniła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UpdateViolatingUniqueConstraint {
        /*
         * 🧪 Zadanie 8:
         * Na tabeli "users" (email UNIQUE) z 2 wierszami spróbuj
         * zaktualizować email jednego wiersza na email JUŻ istniejący w
         * drugim wierszu. Złap SQLException i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UpdateNumericColumnWithArithmetic {
        /*
         * 🧪 Zadanie 9:
         * Na tabeli "products" (id, name, price DECIMAL) wykonaj UPDATE
         * "SET price = price * 1.10 WHERE id = ?" (podwyżka 10%). Wypisz
         * cenę przed i po update.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SequentialUpdatesOnSameRow {
        /*
         * 🧪 Zadanie 10:
         * Na jednym wierszu wykonaj TRZY kolejne UPDATE-y zmieniające tę
         * samą kolumnę (np. licznik odwiedzin: 0 -> 1 -> 2 -> 3). Po
         * każdym update odczytaj i wypisz aktualną wartość.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UpdateUserOrThrowException {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj własny niesprawdzany wyjątek
         * UserNotFoundException(int id). Napisz metodę
         * updateEmailOrThrow(Connection, int id, String newEmail), która
         * rzuca ten wyjątek, jeśli executeUpdate() zwróci 0. Przetestuj
         * dla istniejącego (sukces) i nieistniejącego id (wyjątek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BulkUpdateByDepartment {
        /*
         * 🧪 Zadanie 12:
         * Na tabeli "employees" (id, name, department, salary) z co
         * najmniej 6 wierszami wykonaj UPDATE zwiększający salary o
         * konkretną wartość dla WSZYSTKICH pracowników z danego
         * department (WHERE department = ?). Wypisz liczbę zmienionych
         * wierszy i zweryfikuj SELECT-em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConditionalUpdateWithExtraWhereClause {
        /*
         * 🧪 Zadanie 13:
         * Na tabeli "users" (id, active BOOLEAN) wykonaj UPDATE z
         * warunkiem "WHERE id = ? AND active = TRUE" - przetestuj dla
         * użytkownika aktywnego (sukces, 1) i dla użytkownika już
         * nieaktywnego (0, mimo że id istnieje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AtomicIncrementInSql {
        /*
         * 🧪 Zadanie 14:
         * Na tabeli "posts" (id, title, views INT) wykonaj UPDATE "SET
         * views = views + 1 WHERE id = ?" DZIESIĘĆ razy w pętli (symulacja
         * 10 odsłon). Odczytaj finalną wartość views i porównaj z
         * oczekiwaną (10) - skomentuj, że inkrementacja w SQL jest
         * bezpieczniejsza niż "odczytaj w Javie, dodaj 1, zapisz".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UpdateFullUserObject {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj record User(int id, String name, String email).
         * Napisz metodę updateUser(Connection, User user) wykonującą
         * jeden UPDATE ustawiający name i email na podstawie pól obiektu
         * User (WHERE id = user.id()). Przetestuj i zweryfikuj SELECT-em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BugPatternIgnoringZeroResult {
        /*
         * 🧪 Zadanie 16:
         * Zademonstruj typowy błąd: wywołaj UPDATE dla nieistniejącego
         * id, ZUPEŁNIE IGNORUJĄC wynik executeUpdate() (bez sprawdzania).
         * Program "nie wybuchnie", ale nic się nie zmieni - wypisz na
         * końcu komunikat wyjaśniający, dlaczego brak sprawdzenia wyniku
         * to cichy błąd logiczny, a nie techniczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_UpdateStatusWithValidation {
        /*
         * 🧪 Zadanie 17:
         * Na tabeli "orders" (id, status VARCHAR(20)) napisz metodę
         * updateStatus(Connection, int id, String newStatus), która
         * NAJPIERW (w Javie, przed dotknięciem bazy) waliduje, że
         * newStatus jest jedną z dozwolonych wartości ("NEW", "PAID",
         * "SHIPPED", "CANCELLED") - jeśli nie, rzuca
         * IllegalArgumentException BEZ wykonania żadnego SQL. Przetestuj
         * dla poprawnego i niepoprawnego statusu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OptimisticLockingBasicDemo {
        /*
         * 🧪 Zadanie 18:
         * Na tabeli "accounts" (id, balance, version INT DEFAULT 0)
         * napisz metodę updateWithVersion(Connection, id, newBalance,
         * expectedVersion) wykonującą "UPDATE accounts SET balance = ?,
         * version = version + 1 WHERE id = ? AND version = ?". Przetestuj
         * z PRAWIDŁOWĄ wersją (sukces, 1 wiersz) i ze STARĄ (nieaktualną)
         * wersją (0 wierszy - konflikt wersji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LoopOfIndividualUpdatesSumRows {
        /*
         * 🧪 Zadanie 19:
         * Mając listę par (id, nowaWartość) dla 5 różnych wierszy tabeli
         * "products" (price), wykonaj 5 osobnych UPDATE-ów w pętli,
         * sumując liczbę zmienionych wierszy z każdego wywołania. Wypisz
         * sumę na końcu (powinna być 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareBeforeAfterUpdateDiff {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę printUpdateDiff(Connection, int id, String
         * newEmail), która odczytuje stary email, wykonuje UPDATE,
         * odczytuje nowy email, i wypisuje sformatowaną różnicę w
         * formacie "stary -> nowy".
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_OptimisticLockingConflictScenario {
        /*
         * 🧪 Zadanie 21:
         * Rozszerz Zadanie 18: zasymuluj sytuację, w której DWIE "sesje"
         * odczytują ten sam wiersz (balance=1000, version=0). Pierwsza
         * sesja wykonuje updateWithVersion() z version=0 - sukces
         * (version staje się 1). DRUGA sesja próbuje updateWithVersion()
         * z TĄ SAMĄ, już nieaktualną version=0 - powinna dostać 0
         * (konflikt). Wypisz obie próby i finalny stan wiersza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RetryOnOptimisticLockConflict {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę updateWithRetry(Connection, int id, BigDecimal
         * delta, int maxAttempts), która przy KAŻDEJ próbie NAJPIERW
         * odczytuje aktualny balance i version, licząc nowy balance =
         * stary + delta, a potem próbuje updateWithVersion(). Jeśli
         * wynik to 0 (konflikt), próbuje ponownie (do maxAttempts razy).
         * Przetestuj normalny przypadek (sukces przy 1. próbie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_UpdateWithSubqueryCondition {
        /*
         * 🧪 Zadanie 23:
         * Na tabeli "employees" (id, name, salary) z 6 wierszami wykonaj
         * UPDATE zwiększający salary o 10% TYLKO dla pracowników
         * zarabiających MNIEJ niż średnia (WHERE salary < (SELECT
         * AVG(salary) FROM employees)). Wypisz liczbę zmienionych
         * wierszy i porównaj wynagrodzenia przed/po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AuditLogBeforeUpdate {
        /*
         * 🧪 Zadanie 24:
         * Dodaj tabelę "audit_log" (id, message). Napisz metodę
         * updateWithAudit(Connection, id, newEmail), która NAJPIERW
         * odczytuje stary email, wstawia wpis do audit_log
         * ("Zmiana email dla id=X: stary -> nowy"), a POTEM wykonuje
         * UPDATE. Zweryfikuj, że audit_log ma odpowiedni wpis po
         * wykonaniu operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TimingIndividualVsBulkUpdate {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas: (a) 100 osobnych UPDATE-ów (WHERE id = ?) w pętli
         * dla 100 różnych wierszy, (b) JEDEN UPDATE obejmujący
         * WSZYSTKIE te wiersze naraz przez "WHERE id IN (...)" (dynamicznie
         * zbudowana lista placeholderów). Wypisz oba czasy i różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DenormalizedTotalUpdateAfterInsert {
        /*
         * 🧪 Zadanie 26:
         * Na tabelach "orders" (id, total DECIMAL) i "order_items" (id,
         * order_id, price) wstaw pozycję do order_items, a potem
         * wykonaj UPDATE "orders SET total = total + ? WHERE id = ?"
         * (manualna denormalizacja - suma trzymana też w orders dla
         * wydajności). Zweryfikuj, że total zgadza się z SUM(price) z
         * order_items dla danego zamówienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExecuteUpdateAndValidateHelper {
        /*
         * 🧪 Zadanie 27:
         * Napisz generyczną metodę executeUpdateAndValidate(PreparedStatement
         * ps, int expectedMinRows), wykonującą executeUpdate() i rzucającą
         * IllegalStateException, jeśli wynik jest MNIEJSZY niż
         * expectedMinRows. Użyj jej dla UPDATE, który powinien zmienić co
         * najmniej 3 wiersze - przetestuj przypadek zgodny (sukces) i
         * niezgodny (wyjątek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PartialUpdateFromFieldMap {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę updatePartial(Connection, int id, Map<String,
         * Object> fieldsToChange), która DYNAMICZNIE buduje klauzulę SET
         * tylko dla podanych w mapie kolumn (np. tylko "name", albo
         * "name" i "email"), wiążąc odpowiadające im wartości jako
         * parametry. Przetestuj dla mapy z 1 polem i dla mapy z 2 polami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConcurrentUpdatesWithoutLockingRaceCondition {
        /*
         * 🧪 Zadanie 29:
         * Na tabeli "counters" (id, value INT, BEZ kolumny version) z
         * jednym wierszem (value=0) uruchom 2 wątki, każdy z WŁASNYM
         * Connection, wykonujące po 20 razy: odczytaj value, dodaj 1 w
         * Javie, zapisz nowy value (UPDATE SET value = ? WHERE id = ?) -
         * BEZ żadnej ochrony przed race condition. Po zakończeniu obu
         * wątków (join z limitem czasu) wypisz finalną wartość value i
         * skomentuj, że najpewniej jest ONA MNIEJSZA niż oczekiwane 40 -
         * klasyczny przykład "utraconej aktualizacji" (lost update),
         * kontrastujący z bezpiecznym podejściem z Zadania 14/21.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneUserProfileService {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klasę UserProfileService łączącą: sprawdzony update
         * (rzuca wyjątek przy 0 wierszy), aktualizację częściową (tylko
         * podane pola), optimistic locking (kolumna version) oraz
         * zapis do audit_log przed każdą zmianą. Zademonstruj pełny
         * scenariusz: udana aktualizacja, konflikt wersji (odrzucona
         * aktualizacja), oraz aktualizacja nieistniejącego użytkownika
         * (wyjątek) - z podsumowującym raportem na końcu.
         */
        public static void main(String[] args) { }
    }
}
