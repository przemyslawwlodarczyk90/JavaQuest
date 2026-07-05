package com.example.javaquest._12_hibernate.Lesson06_CrudOperations;

public class _Exercises_Lesson06_CrudOperations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PersistAndFind {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Article (id, title, content) na H2
         * ("jdbc:h2:mem:l06ex01;DB_CLOSE_DELAY=-1"). Zapisz jeden artykul (persist) i
         * odczytaj go w NOWEJ Session (find).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MergeOnDetachedEntity {
        /*
         * 🧪 Zadanie 2:
         * Zapisz artykul, zamknij Session (obiekt staje sie detached), zmien pole
         * "title" i zapisz zmiane przez merge() w NOWEJ Session. Zweryfikuj zmiane w
         * KOLEJNEJ Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RemoveManagedEntity {
        /*
         * 🧪 Zadanie 3:
         * Zapisz artykul, znajdz go w NOWEJ Session i usun przez remove(). Zweryfikuj
         * w KOLEJNEJ Session, ze find() zwraca null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_NativeSaveMethodComparison {
        /*
         * 🧪 Zadanie 4:
         * Uzyj natywnej metody session.save(artykul) (zamiast persist) i zapisz w
         * komentarzu zwrocona wartosc (Serializable - klucz glowny) - porownaj z
         * persist(), ktore zwraca void.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NativeGetVsFind {
        /*
         * 🧪 Zadanie 5:
         * Uzyj session.get(Article.class, id) (natywne) zamiast find() dla
         * NIEISTNIEJACEGO id. Zapisz w komentarzu, ze zachowuje sie tak samo jak
         * find() (zwraca null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RemoveNonExistentEntity {
        /*
         * 🧪 Zadanie 6:
         * Sprobuj wywolac remove() na obiekcie zwroconym przez find() dla
         * nieistniejacego id (czyli remove(null)). Zapisz w komentarzu, co sie
         * dzieje (spodziewaj sie wyjatku NullPointerException lub podobnego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MultiplePersistsInOneTransaction {
        /*
         * 🧪 Zadanie 7:
         * W JEDNEJ transakcji zapisz 5 roznych artykulow (persist x5), potem
         * commit(). Zweryfikuj w NOWEJ Session, ze wszystkie 5 istnieje w bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UpdateWithoutMergeOnManagedEntity {
        /*
         * 🧪 Zadanie 8:
         * Znajdz artykul (find - obiekt jest managed), zmien pole BEZPOSREDNIO na tym
         * obiekcie (bez merge) i wywolaj commit - zweryfikuj, ze zmiana zostala
         * zapisana (dirty checking, wiecej w Lesson17).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ForgottenCommitPitfall {
        /*
         * 🧪 Zadanie 9:
         * Zapisz artykul (persist), ale CELOWO NIE wywoluj commit() (zamknij Session
         * bez commitu). Sprawdz w KOLEJNEJ Session, czy artykul zostal zapisany -
         * zapisz obserwacje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FullCrudSequence {
        /*
         * 🧪 Zadanie 10:
         * Wykonaj PELNA sekwencje CRUD na jednym artykule: create (persist), read
         * (find), update (zmiana pola + commit), delete (remove) - z println po
         * kazdym kroku.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MergeReturnsNewInstance {
        /*
         * 🧪 Zadanie 11:
         * Wywolaj merge() na obiekcie detached i zapisz w komentarzu (przez
         * porownanie == ) ze zwrocony obiekt jest INNA instancja niz oryginalny
         * argument - a modyfikacja oryginalnego argumentu PO merge() nie ma wplywu
         * na baze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NonUniqueObjectExceptionDemo {
        /*
         * 🧪 Zadanie 12:
         * W JEDNEJ Session: znajdz artykul (find), potem sprobuj wywolac
         * session.update(nowyObiektZTymSamymId) (natywne update, NIE merge). Zapisz
         * w komentarzu wyjatek NonUniqueObjectException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CascadingDeleteWithoutRelation {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj DWIE niepowiazane encje (Article i Comment, bez @ManyToOne/
         * @OneToMany). Usun Article i sprawdz, ze Comment (osobny wiersz w innej
         * tabeli) POZOSTAJE nienaruszony - brak zwiazku, brak kaskady.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConditionalUpdateBusinessLogic {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode "publishArticle(Session, Long id)" ktora wywoluje find(),
         * sprawdza WARUNEK biznesowy (np. content.length() > 100) i TYLKO wtedy
         * zmienia pole "published" na true - w przeciwnym razie rzuca wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BatchDeleteInLoop {
        /*
         * 🧪 Zadanie 15:
         * Zapisz 10 artykulow. Znajdz i usun (find + remove) TYLKO te, ktorych tytul
         * zaczyna sie na literke "A" (recznie sprawdzajac w petli po odczytaniu
         * wszystkich - HQL bulk delete poznamy w Lesson19).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExceptionHandlingWithRollback {
        /*
         * 🧪 Zadanie 16:
         * Opakuj sekwencje persist+persist+commit w try-catch, gdzie DRUGI persist
         * celowo narusza ograniczenie (np. nullable=false na title=null) - w catch
         * wywolaj rollback() i zweryfikuj, ze PIERWSZY obiekt TEZ nie zostal zapisany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ReadModifyWriteRaceConditionDemo {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj wzorzec "read-modify-write" (find, zmiana pola, commit) i
         * zapisz w komentarzu, dlaczego bez @Version (Lesson25) dwa rownolegle watki
         * wykonujace ten wzorzec moga "zgubic" jedna ze zmian (lost update).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DetachedEntityReattachViaMergeChain {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj LANCUCH: zapisz -> zamknij Session -> zmien pole -> merge (nowa
         * Session) -> zamknij -> zmien INNE pole na ZWROCONYM obiekcie -> merge
         * (kolejna nowa Session). Zweryfikuj koncowy stan w bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareGetAndLoadBehavior {
        /*
         * 🧪 Zadanie 19:
         * Porownaj session.get(Article.class, nieistniejaceId) (zwraca null) z
         * session.load(Article.class, nieistniejaceId) (zwraca proxy, rzuca wyjatek
         * DOPIERO przy pierwszym dostepie do pola) - zapisz obserwacje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CrudWrapperServiceClass {
        /*
         * 🧪 Zadanie 20:
         * Napisz klase "ArticleService" z metodami create/read/update/delete
         * opakowujacymi Session+Transaction (kazda metoda otwiera i zamyka WLASNA
         * Session) - uzyj jej z main() zamiast bezposredniego wolania API Hibernate.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_OptimizedBulkCreateWithBatching {
        /*
         * 🧪 Zadanie 21:
         * Zapisz 1000 artykulow z hibernate.jdbc.batch_size=50 ORAZ flush+clear co
         * 50 (Lesson23) - zmierz czas i porownaj z zapisem BEZ zadnej optymalizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TransactionalServiceWithRetryOnFailure {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode wykonujaca operacje CRUD z automatycznym RETRY (do 3 prob)
         * przy dowolnym wyjatku bazodanowym - z krotkim opoznieniem (Thread.sleep)
         * miedzy probami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SoftDeleteInsteadOfRemove {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj "soft delete" - zamiast session.remove(), dodaj pole
         * "deleted" (boolean) i metode markAsDeleted(Session, Long id) ustawiajaca
         * je na true. Napisz metode findAllActive() pomijajaca usuniete "miekko".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AuditFieldsManualImplementation {
        /*
         * 🧪 Zadanie 24:
         * Dodaj reczie (bez Lesson16 callbacks) pola createdAt/updatedAt, ustawiane
         * jawnie w kodzie serwisu PRZED kazdym persist/merge - porownaj w komentarzu
         * z automatycznym podejsciem przez @PrePersist/@PreUpdate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CrudOnEntityWithEmbeddedValueObject {
        /*
         * 🧪 Zadanie 25:
         * Rozszerz Article o wbudowany komponent @Embeddable "Metadata" (author,
         * publishDate) - wykonaj pelny CRUD na artykule z takim komponentem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConcurrentReadsNoConflict {
        /*
         * 🧪 Zadanie 26:
         * Uruchom 5 watkow (jak w _05_multithreading) rownolegle ODCZYTUJACYCH (BEZ
         * modyfikacji) ten sam artykul przez find() - zweryfikuj, ze wszystkie
         * konczy sie sukcesem bez zadnych wyjatkow (czyste odczyty sie nie konfliktuja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_IdempotentCreateOrUpdate {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode "upsert(Session, String naturalKey, String content)", ktora
         * NAJPIERW probuje znalezc artykul po unikalnym polu (np. slug), a jesli
         * istnieje - aktualizuje go, jesli nie - tworzy nowy (wzorzec "upsert").
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransactionalBoundaryAcrossMultipleEntities {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj DWIE niezalezne encje (Article, Notification) i w JEDNEJ transakcji
         * zapisz Article ORAZ Notification "artykul opublikowany" - zapisz w
         * komentarzu, dlaczego obie operacje powinny byc atomowe (albo obie sie
         * udaja, albo zadna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MigrateJdbcDaoCrudToHibernateFully {
        /*
         * 🧪 Zadanie 29:
         * Wez kompletne DAO CRUD z _10_dao/Lesson05_CrudInDao i PRZEPISZ WSZYSTKIE
         * jego metody na Hibernate, zachowujac identyczne sygnatury publiczne -
         * uruchom oba i porownaj wyniki na tych samych danych testowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCrudServiceCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY "ArticleService" z: create, read
         * (find + lista wszystkich), update (przez merge na detached), delete,
         * soft-delete (Zadanie 23), upsert (Zadanie 27) i audit fields (Zadanie 24) -
         * zademonstruj kazda operacje w main().
         */
        public static void main(String[] args) { }
    }
}
