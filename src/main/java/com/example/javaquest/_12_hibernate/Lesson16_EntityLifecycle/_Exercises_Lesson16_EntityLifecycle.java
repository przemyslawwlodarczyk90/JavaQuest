package com.example.javaquest._12_hibernate.Lesson16_EntityLifecycle;

public class _Exercises_Lesson16_EntityLifecycle {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_TransientStateBasic {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Invoice (id, number, amount) na H2
         * ("jdbc:h2:mem:l16ex01;DB_CLOSE_DELAY=-1"). Stworz obiekt przez "new" i
         * wypisz jego pole id (spodziewaj sie null - stan TRANSIENT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PersistentStateBasic {
        /*
         * 🧪 Zadanie 2:
         * Zapisz Invoice (persist) i wypisz jego id ZARAZ po persist (przed
         * commit) - potwierdz w komentarzu, ze id JUZ jest przypisane (stan PERSISTENT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DetachedAfterSessionClose {
        /*
         * 🧪 Zadanie 3:
         * Zapisz Invoice, zamknij Session. Zmien pole "amount" na obiekcie (teraz
         * detached) i sprawdz w NOWEJ Session, ze zmiana NIE zostala zapisana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MergeReattachesDetached {
        /*
         * 🧪 Zadanie 4:
         * Kontynuuj Zadanie 3: wywolaj session.merge(invoice) w NOWEJ Session i
         * zweryfikuj, ze TERAZ zmiana zostala zapisana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RemovedStateBasic {
        /*
         * 🧪 Zadanie 5:
         * Znajdz Invoice, wywolaj remove(), a PRZED commit sprawdz (jesli mozliwe)
         * jego stan - zapisz w komentarzu, ze obiekt jest oznaczony REMOVED do
         * momentu flush/commit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_EvictDetachesFromSession {
        /*
         * 🧪 Zadanie 6:
         * Znajdz Invoice, wywolaj session.evict(invoice) (BEZ zamykania Session).
         * Zmien pole i wywolaj commit - zapisz w komentarzu, ze zmiana NIE zostala
         * zapisana (obiekt "wyewiktowany" jest juz detached).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ClearDetachesAllInSession {
        /*
         * 🧪 Zadanie 7:
         * Znajdz 3 rozne Invoice w JEDNEJ Session, wywolaj session.clear() (BEZ
         * zamykania Session). Zmien pole na JEDNYM z nich i wywolaj commit - zapisz
         * w komentarzu, ze zmiana NIE zostala zapisana (wszystkie stały się detached).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PrePersistCallbackBasic {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do Invoice metode @PrePersist ustawiajaca pole createdAt. Zapisz
         * obiekt i zweryfikuj, ze createdAt zostalo automatycznie wypelnione.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PreUpdateCallbackBasic {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do Invoice metode @PreUpdate ustawiajaca pole updatedAt. Zmien pole
         * "amount" na zarzadzanym obiekcie i zweryfikuj po commit, ze updatedAt
         * zostalo ustawione (a createdAt z Zadania 8 pozostalo bez zmian).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AllFourStatesInOneRun {
        /*
         * 🧪 Zadanie 10:
         * Napisz PELNY scenariusz przechodzacy przez wszystkie 4 stany (transient ->
         * persistent -> detached -> removed) z printlnami przy KAZDYM przejsciu,
         * jak w lekcji, ale dla WLASNEJ encji Invoice.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PostLoadCallbackVerification {
        /*
         * 🧪 Zadanie 11:
         * Dodaj @PostLoad do Invoice wypisujace println przy KAZDYM zaladowaniu
         * obiektu z bazy. Wywolaj find() DWA razy w DWOCH roznych Session i
         * zweryfikuj, ze callback wywolal sie za KAZDYM razem (bo to nowe Session,
         * osobny first-level cache).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_PostPersistVsPrePersistOrdering {
        /*
         * 🧪 Zadanie 12:
         * Dodaj do Invoice OBA callbacki @PrePersist i @PostPersist z printlnami
         * ("PRE" i "POST"). Zapisz obiekt i zweryfikuj KOLEJNOSC wypisania (PRE
         * przed faktycznym INSERT, POST po nim).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ModifyingDetachedThenDiscardingChanges {
        /*
         * 🧪 Zadanie 13:
         * Zapisz Invoice, zamknij Session (detached), zmien 3 rozne pola. ZAMIAST
         * merge(), po prostu ZAROB nowy obiekt (find() w nowej Session) - zapisz w
         * komentarzu, ze zmiany na detached obiekcie zostaly CALKOWICIE zignorowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReattachViaSessionUpdateNative {
        /*
         * 🧪 Zadanie 14:
         * Zamiast merge(), uzyj natywnego session.update(detachedInvoice) - zapisz w
         * komentarzu roznice zachowania (update rzuca wyjatek, jesli w Session jest
         * juz INNY zarzadzany obiekt o tym samym Id, merge nie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_PreRemoveValidationBlocksDeletion {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do Invoice @PreRemove sprawdzajace warunek biznesowy (np. amount ==
         * 0, "faktura musi byc rozliczona") i rzucajace wyjatek, jesli warunek nie
         * jest spelniony - zademonstruj probe usuniecia nierozliczonej faktury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleCallbackMethodsSameAnnotation {
        /*
         * 🧪 Zadanie 16:
         * Sprobuj dodac DWIE metody oznaczone @PrePersist w JEDNEJ klasie encji.
         * Zapisz w komentarzu, czy Hibernate na to pozwala i jak (czy obie sie
         * wykonuja, w jakiej kolejnosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LifecycleCallbacksInMappedSuperclass {
        /*
         * 🧪 Zadanie 17:
         * Przenies @PrePersist/@PreUpdate (createdAt/updatedAt) do WSPOLNEJ klasy
         * @MappedSuperclass "AuditableEntity" i rozszerz o nia Invoice. Zapisz w
         * komentarzu, ze callbacki DZIALAJA mimo ze sa zdefiniowane w nadklasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DetachedEntityIdentityVsEquality {
        /*
         * 🧪 Zadanie 18:
         * Zapisz Invoice, zamknij Session (detached). W NOWEJ Session znajdz TEN SAM
         * wiersz (nowy obiekt, PERSISTENT). Porownaj obiekt detached i nowy obiekt
         * PRZEZ equals() (oparte na Id) - powinny byc rowne, mimo ze to inne instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefreshDiscardsInMemoryChanges {
        /*
         * 🧪 Zadanie 19:
         * Znajdz Invoice (managed), zmien pole W PAMIECI (bez commit), a POTEM
         * wywolaj session.refresh(invoice) - zweryfikuj, ze zmiana ZOSTALA
         * NADPISANA danymi z bazy (odrzucona).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_LifecycleStateMachineDiagramCode {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode "printState(String etykieta)" wypisujaca aktualny,
         * DOMNIEMANY stan obiektu (na podstawie tego, jakie operacje wykonales
         * wczesniej) - uzyj jej po KAZDYM kroku w scenariuszu z Zadania 10.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLifecycleWithAllCallbacksAndAssociations {
        /*
         * 🧪 Zadanie 21:
         * Rozszerz Invoice o powiazanie @OneToMany InvoiceLine. Zbuduj PELNY
         * scenariusz przechodzacy przez wszystkie stany z callbackami na OBU
         * poziomach (Invoice i InvoiceLine) - zapisz kolejnosc wywolan callbackow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CallbackBasedSoftDeleteImplementation {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj "soft delete" (podglad przed Lesson06/23) uzywajac @PreRemove
         * do PRZECHWYCENIA proby usuniecia (rzuc UnsupportedOperationException) i
         * zamiast tego ustaw pole "deleted=true" recznie w kodzie serwisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DetachedEntityCacheAcrossRequests {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj typowy scenariusz webowy: "kontroler A" laduje Invoice
         * (detached po zamknieciu Session), "kontroler B" (osobne wywolanie main(),
         * symulujace osobne zadanie HTTP) modyfikuje go i zapisuje przez merge() -
         * zweryfikuj poprawnosc koncowego stanu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CallbackOrderWithCascadingOperations {
        /*
         * 🧪 Zadanie 24:
         * Z cascade=ALL (Lesson14) miedzy Invoice i InvoiceLine: dodaj callbacki do
         * OBU encji i zapisz KOLEJNOSC ich wywolania przy JEDNYM session.persist(invoice)
         * z 2 nowymi liniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PreventDetachedModificationAtCompileTime {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj WLASNY wzorzec (np. "immutable snapshot" - klasa DTO
         * zwracana zamiast bezposrednio encji poza Session) uniemozliwiajacy
         * PRZYPADKOWA modyfikacje detached entity w kodzie wolajacym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LifecycleTestingHarness {
        /*
         * 🧪 Zadanie 26:
         * Napisz "test harness" (metoda z asercjami if+throw) weryfikujaca
         * automatycznie WSZYSTKIE przejscia stanow dla DOWOLNEJ encji przekazanej
         * jako parametr generyczny - uzyj jej dla Invoice i dla innej, prostszej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EntityListenerExternalClass {
        /*
         * 🧪 Zadanie 27:
         * Zamiast metod @PrePersist BEZPOSREDNIO w encji, uzyj @EntityListeners z
         * OSOBNA klasa listenera (metody statyczne/instancyjne z @PrePersist
         * przyjmujace encje jako parametr) - zademonstruj TA SAMA funkcjonalnosc co
         * Zadanie 8, ale w oddzielonej klasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultipleEntityListenersOrdering {
        /*
         * 🧪 Zadanie 28:
         * Dodaj DWIE rozne klasy @EntityListeners do jednej encji (przez
         * @EntityListeners({A.class, B.class})) - zapisz w komentarzu KOLEJNOSC
         * wywolania ich metod @PrePersist.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportLifecycleBestPractices {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, jak PRAWIDLOWO zarzadzac cyklem zycia
         * encji w typowej aplikacji webowej (kiedy Session powinna byc otwarta,
         * kiedy uzywac merge, jak unikac LazyInitializationException).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullLifecycleCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system fakturowania (Invoice +
         * InvoiceLine) laczacy: audit fields przez @MappedSuperclass (Zadanie 17),
         * walidacje @PreRemove (Zadanie 15), zewnetrzny EntityListener (Zadanie 27),
         * i pelny scenariusz przez wszystkie 4 stany na CALYM grafie obiektow.
         */
        public static void main(String[] args) { }
    }
}
