package com.example.javaquest._23_spring_data_jpa.Lesson03_CrudRepositoryJpaRepository;

public class _Exercises_Lesson03_CrudRepositoryJpaRepository {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSaveInsertVsUpdateDecision {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, JAK Spring Data JPA DECYDUJE, czy
         * `save(...)` wykona INSERT czy UPDATE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnEntityAndVerifyInsert {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA encje - zapisz NOWY obiekt, zweryfikuj
         * INSERT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementModifyAndSaveVerifyUpdate {
        /*
         * 🧪 Zadanie 3:
         * ZMODYFIKUJ zapisana encje I zapisz PONOWNIE - zweryfikuj
         * UPDATE (brak nowego wiersza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TriggerDeleteByIdOnMissingId {
        /*
         * 🧪 Zadanie 4:
         * Wywolaj `deleteById(...)` NA NIEISTNIEJACYM ID - zapisz
         * DOKLADNE zachowanie (wyjatek CZY cichy sukces).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementDeleteEntityOnDetachedObject {
        /*
         * 🧪 Zadanie 5:
         * Wywolaj `delete(entity)` NA obiekcie, KTORY NIE zostal
         * pobrany Z bazy (recznie utworzony Z ISTNIEJACYM ID) -
         * zweryfikuj zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementSaveAllAndFlush {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `saveAllAndFlush(...)` DO zapisania 5 encji NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementDeleteAllInBatch {
        /*
         * 🧪 Zadanie 7:
         * Uzyj `deleteAllInBatch()` PO zapisaniu WIELU encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareCountBeforeAndAfterOperations {
        /*
         * 🧪 Zadanie 8:
         * Sledz `count()` PRZED I PO KAZDEJ operacji CRUD.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementExistsByIdCheck {
        /*
         * 🧪 Zadanie 9:
         * Uzyj `existsById(...)` DO sprawdzenia, CZY encja ISTNIEJE
         * PRZED probą aktualizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyMergeQueriesDatabaseFirst {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (powiaz z `_12_hibernate/Lesson06`): wyjasnij,
         * dlaczego `merge()` (UPDATE) MUSI NAJPIERW ZAPYTAC baze O
         * ISTNIEJACY stan encji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCustomIsNewLogic {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z `_12_hibernate/Lesson05` - zaimplementuj encje Z
         * RECZNIE PRZYPISYWANYM ID (`assigned`) - zweryfikuj, JAK
         * WPLYWA to NA decyzje `save()` INSERT vs UPDATE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPersistableInterface {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj interfejs `Persistable<ID>` NA WLASNEJ encji -
         * WYMUS WLASNA logike "czy encja jest nowa".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDeleteAllByIdInBatch {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `deleteAllByIdInBatch(...)` (Spring Data 3+) - usun
         * KONKRETNE ID W JEDNYM zapytaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementFindAllVersusStream {
        /*
         * 🧪 Zadanie 14:
         * Powiaz z `_03_collections` (Streams) - uzyj
         * `findAll().stream()...` DO przetworzenia WYNIKOW.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementOptimisticLockingWithSave {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_12_hibernate/Lesson25` - dodaj `@Version` DO
         * encji - zweryfikuj `OptimisticLockingFailureException` PRZY
         * KONFLIKCIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementSaveInLoopVsSaveAllPerformance {
        /*
         * 🧪 Zadanie 16:
         * Zmierz czas zapisu 1000 encji PRZEZ `save()` W PETLI wzgledem
         * `saveAll(...)` JEDNORAZOWO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementTransientEntityWithPrePopulatedId {
        /*
         * 🧪 Zadanie 17:
         * Utworz NOWY obiekt encji Z RECZNIE ustawionym ID (BEZ
         * pobrania Z bazy) I zapisz - CZY Spring Data ROZPOZNA to jako
         * INSERT czy UPDATE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareDeleteWithDeleteById {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala (dokumentacja): porownaj `delete(T entity)` Z
         * `deleteById(ID id)` - JAKIE zapytania SQL GENERUJE KAZDA Z
         * nich?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureBatchDeleteVsIndividualDeletes {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas `deleteAllInBatch()` wzgledem `deleteAll()` (petla
         * `delete()`) DLA 500 encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildCrudOperationsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" operacji CRUD Z ZACHOWANIEM
         * (INSERT/UPDATE/wyjatek/cichy sukces).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementUpsertPatternManually {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WZORZEC "upsert" (znajdz-lub-utworz) RECZNIE
         * PRZEZ `findById` + warunkowe `save`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementConcurrentSaveRaceCondition {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_05_multithreading` - wyslij 2 rownolegle `save()`
         * NA TEJ SAMEJ encji - zweryfikuj WYNIK (ostatni WYGRYWA?
         * wyjatek?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSoftDeleteAsAlternativeToRealDelete {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj "miekkie usuwanie" (flaga `deleted=true`
         * ZAMIAST prawdziwego DELETE) - zweryfikuj, ze `findAll()`
         * WCIAZ JE zwraca (chyba ze DODASZ WLASNY filtr).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementBulkUpdateWithoutLoadingEntities {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z Lesson05 (`@Modifying`) - zaktualizuj WIELE
         * rekordow JEDNYM zapytaniem UPDATE, BEZ ladowania encji DO
         * pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAuditedDeleteWithReasonLogging {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj POWOD
         * KAZDEGO usuniecia (kto/kiedy/dlaczego) DO dziennika audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCascadingDeleteVerification {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_12_hibernate/Lesson14_CascadeTypes` - zweryfikuj
         * KASKADOWE usuwanie POWIAZANYCH encji PRZEZ Spring Data.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRetryOnOptimisticLockFailure {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z Zadaniem 15 - zaimplementuj RETRY (3 proby) PRZY
         * `OptimisticLockingFailureException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementBatchInsertWithHibernateBatchSize {
        /*
         * 🧪 Zadanie 28:
         * Skonfiguruj `spring.jpa.properties.hibernate.jdbc.batch_size`
         * - zmierz WPLYW NA wydajnosc `saveAll(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareJpaRepositorySaveWithRawJdbcInsert {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_09_jdbc` - porownaj `save()` (Spring Data) Z
         * RECZNYM `PreparedStatement` INSERT - RÓZNICA wydajnosci PRZY
         * DUZEJ liczbie wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteInventoryManagementCrudCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY system zarzadzania magazynem - CRUD +
         * miekkie usuwanie (Zadanie 23) + optymistyczne blokowanie
         * (Zadanie 15) + audyt (Zadanie 25) - jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
