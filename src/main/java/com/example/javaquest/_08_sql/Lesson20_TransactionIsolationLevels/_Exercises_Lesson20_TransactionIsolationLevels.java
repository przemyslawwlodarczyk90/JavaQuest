package com.example.javaquest._08_sql.Lesson20_TransactionIsolationLevels;

public class _Exercises_Lesson20_TransactionIsolationLevels {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SetAndReadTransactionIsolation {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex01_iso;DB_CLOSE_DELAY=-1). Wykonaj
         * conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED) i
         * odczytaj z powrotem poziom przez conn.getTransactionIsolation() - wypisz
         * wartość liczbową i porównaj ze stałą Connection.TRANSACTION_READ_COMMITTED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DefaultIsolationLevelInH2 {
        /*
         * 🧪 Zadanie 2:
         * Połącz się z bazą H2. BEZ jawnego ustawiania poziomu izolacji, odczytaj
         * conn.getTransactionIsolation() i wypisz jego wartość - zweryfikuj (w
         * println), że to READ_COMMITTED (domyślny poziom w H2 zgodnie z lekcją).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FourIsolationLevelsVocabularyPrint {
        /*
         * 🧪 Zadanie 3:
         * Bez łączenia się z bazą: wypisz println 4 standardowe poziomy izolacji z
         * lekcji od najsłabszego do najsilniejszego (READ UNCOMMITTED, READ
         * COMMITTED, REPEATABLE READ, SERIALIZABLE) z jednozdaniowym opisem każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ThreeAnomaliesVocabularyPrint {
        /*
         * 🧪 Zadanie 4:
         * Bez łączenia się z bazą: wypisz println 3 klasyczne anomalie z lekcji
         * (DIRTY READ, NON-REPEATABLE READ, PHANTOM READ) z jednozdaniowym
         * wyjaśnieniem każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TwoConnectionsToSameInMemoryDb {
        /*
         * 🧪 Zadanie 5:
         * Połącz się DWA razy do TEJ SAMEJ bazy H2 (jdbc:h2:mem:ex05_iso;
         * DB_CLOSE_DELAY=-1) - "connA" i "connB". Utwórz przez connA tabelę
         * "accounts" (id INT PRIMARY KEY, balance INT) i wstaw 1 wiersz. Odczytaj
         * ten wiersz przez connB - zweryfikuj, że oba połączenia widzą tę samą,
         * WSPÓLNĄ bazę w pamięci (technika z lekcji, dwa połączenia symulujące dwie
         * transakcje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DirtyReadObservedUnderReadUncommitted {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz scenariusz z lekcji: 2 połączenia do wspólnej bazy z tabelą
         * "accounts" (balance=1000). Wyłącz autocommit na obu. Transakcja A
         * (connA) wykonuje UPDATE balance=1 BEZ commit. Transakcja B (connB, z
         * setTransactionIsolation(TRANSACTION_READ_UNCOMMITTED)) odczytuje balance -
         * zweryfikuj, że WIDZI wartość 1 (dirty read). Wykonaj rollback() na connA i
         * skomentuj w println, że B "widziało" dane, które nigdy naprawdę nie
         * powstały.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReadCommittedBlocksDirtyRead {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz scenariusz z Zadania 6, ale z connB na DOMYŚLNYM poziomie
         * READ_COMMITTED (bez ustawiania READ_UNCOMMITTED). Transakcja A zmienia
         * balance BEZ commit. Transakcja B odczytuje balance - zweryfikuj, że B
         * WCIĄŻ widzi STARĄ wartość (1000), bo READ_COMMITTED nie pozwala na dirty
         * read. Wykonaj rollback na A.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NonRepeatableReadUnderReadCommitted {
        /*
         * 🧪 Zadanie 8:
         * Odtwórz scenariusz z lekcji: transakcja A (READ_COMMITTED) odczytuje
         * balance (1. odczyt), transakcja B zmienia balance i wykonuje COMMIT,
         * transakcja A odczytuje balance PONOWNIE (2. odczyt, w TEJ SAMEJ
         * transakcji) - zweryfikuj, że 1. i 2. odczyt dają RÓŻNE wartości
         * (non-repeatable read).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TransactionIsolationConstantsOverview {
        /*
         * 🧪 Zadanie 9:
         * Bez łączenia się z bazą: wypisz println WARTOŚCI LICZBOWE 4 stałych
         * Connection.TRANSACTION_* (READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ,
         * SERIALIZABLE) - odwołaj się do nich w Javie (Connection.TRANSACTION_...) i
         * wypisz każdą z opisem, w jakiej kolejności rosną (od najsłabszej do
         * najsilniejszej izolacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ResetHelperMethodPattern {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) z 1 kontem.
         * Napisz metodę resetBalance(String url) (analogiczną do tej z lekcji), która
         * w NOWYM, osobnym połączeniu resetuje balance do wartości początkowej.
         * Zmień balance przez UPDATE, wywołaj resetBalance i zweryfikuj (nowym
         * SELECT), że wartość wróciła do stanu początkowego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RepeatableReadEliminatesNonRepeatableRead {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz scenariusz z lekcji: transakcja A z jawnie ustawionym
         * REPEATABLE_READ odczytuje balance (1. odczyt), transakcja B zmienia
         * balance i commituje, transakcja A odczytuje PONOWNIE (2. odczyt) -
         * zweryfikuj, że OBA odczyty w A dają TĘ SAMĄ wartość (REPEATABLE_READ
         * eliminuje non-repeatable read, w przeciwieństwie do READ_COMMITTED z
         * Zadania 8).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SideBySideComparisonReadCommittedVsRepeatableRead {
        /*
         * 🧪 Zadanie 12:
         * Wykonaj DWA analogiczne scenariusze (jak w Zadaniach 8 i 11) jeden po
         * drugim na TYCH SAMYCH danych (resetując balance między nimi metodą z
         * Zadania 10): raz z READ_COMMITTED, raz z REPEATABLE_READ. Zbierz wyniki
         * (1. odczyt, 2. odczyt) z OBU scenariuszy i wypisz porównanie w formie
         * tabeli tekstowej, jasno pokazując różnicę w zachowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DirtyReadPreventedButVisibleAfterCommit {
        /*
         * 🧪 Zadanie 13:
         * Odtwórz scenariusz z READ_COMMITTED (jak Zadanie 7, bez dirty read).
         * Transakcja A zmienia balance BEZ commit - transakcja B (READ_COMMITTED)
         * NIE widzi zmiany. Transakcja A wykonuje commit(). Transakcja B odczytuje
         * balance PONOWNIE - teraz POWINNA zobaczyć nową wartość (bo została
         * zatwierdzona) - zweryfikuj to i wypisz obie próby odczytu transakcji B.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SettingIsolationMustHappenBeforeTransactionStarts {
        /*
         * 🧪 Zadanie 14:
         * Utwórz połączenie, wyłącz autocommit i wykonaj JAKIŚ SELECT (rozpoczynając
         * transakcję). Spróbuj TERAZ zmienić poziom izolacji
         * (setTransactionIsolation) W TRAKCIE aktywnej transakcji - sprawdź
         * empirycznie w H2, czy to się powiedzie, czy zgłosi błąd/zostanie
         * zignorowane, i wypisz zaobserwowane zachowanie w println (H2 może się
         * różnić od innych baz w tym zachowaniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleTransactionsReadingDifferentIsolationLevels {
        /*
         * 🧪 Zadanie 15:
         * Otwórz TRZY połączenia do wspólnej bazy z tabelą "accounts" (balance=1000).
         * Ustaw connA na READ_UNCOMMITTED, connB na READ_COMMITTED, connC na
         * REPEATABLE_READ. Wykonaj UPDATE balance=1 na CZWARTYM połączeniu BEZ
         * commit, i sprawdź, co widzi KAŻDE z 3 połączeń (A powinno zobaczyć dirty
         * read, B i C - starą wartość) - wypisz porównanie wszystkich 3 odczytów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_IsolationLevelImpactOnConcurrentUpdatesCounter {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "counters" (id INT PRIMARY KEY, value INT) z 1 wierszem
         * value=0. Napisz metodę incrementNaively(Connection conn), która odczytuje
         * value, dodaje 1 w Javie, i wykonuje UPDATE z nową wartością (klasyczny
         * wzorzec "read-modify-write", podatny na problemy współbieżności). Wywołaj
         * ją NA PRZEMIAN z 2 połączeń bez żadnej synchronizacji (10 razy każde) i
         * wypisz finalną wartość value - skomentuj w println, że może być NIŻSZA niż
         * 20 z powodu "zgubionych aktualizacji" (lost updates).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SerializableIsolationSettingAndBehaviorNote {
        /*
         * 🧪 Zadanie 17:
         * Utwórz połączenie i ustaw conn.setTransactionIsolation(Connection.
         * TRANSACTION_SERIALIZABLE). Wykonaj ten sam scenariusz non-repeatable-read
         * co w Zadaniu 11 (2 odczyty w transakcji A z modyfikacją przez B pomiędzy
         * nimi), ale z poziomem SERIALIZABLE - zweryfikuj, że oba odczyty A dają TĘ
         * SAMĄ wartość (podobnie jak REPEATABLE_READ w H2) i wypisz komentarz z
         * lekcji, że w H2 różnica między REPEATABLE_READ i SERIALIZABLE nie jest
         * łatwo obserwowalna prostym przykładem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_IsolationLevelDoesNotAffectSingleConnectionLogic {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT). Wykonaj TEN
         * SAM ciąg operacji (2 UPDATE + commit) w JEDNYM połączeniu 3 razy, każdy raz
         * z INNYM poziomem izolacji (READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE)
         * ustawionym PRZED transakcją - zweryfikuj, że finalny wynik (balance) jest
         * IDENTYCZNY we wszystkich 3 przypadkach, bo poziom izolacji wpływa na
         * WIDOCZNOŚĆ zmian INNYCH transakcji, a nie na logikę własnej transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DetectDirtyReadProgrammatically {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę detectDirtyRead(String url), która odtwarza scenariusz z
         * Zadania 6 (READ_UNCOMMITTED) i PROGRAMOWO (nie tylko przez println)
         * zwraca boolean - true, jeśli connB zobaczyło niezatwierdzoną zmianę connA
         * (dirty read wystąpił), false w przeciwnym razie. Wywołaj tę metodę i
         * wypisz jej wynik jako "Dirty read wystapil: <true/false>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_IsolationLevelDecisionMatrixPrint {
        /*
         * 🧪 Zadanie 20:
         * Bez łączenia się z bazą: wypisz na konsoli tabelę decyzyjną (println) - dla
         * każdego z 4 poziomów izolacji z lekcji podaj, KTÓRE z 3 anomalii (dirty
         * read, non-repeatable read, phantom read) są dozwolone, a które
         * eliminowane, w formacie "<poziom>: dirty_read=<T/F>,
         * non_repeatable_read=<T/F>, phantom_read=<T/F>" (na podstawie standardu SQL
         * z lekcji).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullAnomalyDemonstrationSuite {
        /*
         * 🧪 Zadanie 21:
         * Napisz KOMPLETNY zestaw demonstracyjny (metoda runFullSuite(String url)),
         * który PO KOLEI (resetując dane między krokami, jak w lekcji) demonstruje:
         * (1) dirty read pod READ_UNCOMMITTED, (2) brak dirty read pod
         * READ_COMMITTED, (3) non-repeatable read pod READ_COMMITTED, (4) brak
         * non-repeatable read pod REPEATABLE_READ. Zbierz wynik KAŻDEGO kroku jako
         * boolean (anomalia wystąpiła / nie wystąpiła) i wypisz zbiorczy raport na
         * końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ConcurrentTransactionSimulationWithInterleavedSteps {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "inventory" (id INT PRIMARY KEY, quantity INT). Zasymuluj
         * (na jednym wątku, przeplatając wywołania na 2 connection, jak w lekcji)
         * scenariusz "race condition": obie transakcje (READ_COMMITTED) odczytują
         * quantity=100, obie w Javie liczą quantity-10, obie wykonują UPDATE i
         * commit - wypisz finalną wartość quantity i skomentuj w println, że
         * powinna być 90 (zgubiona jedna aktualizacja), a nie 80, mimo że logicznie
         * "powinny się odjąć dwa razy 10".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PessimisticLockingWithSelectForUpdate {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT). Sprawdź
         * empirycznie wsparcie H2 dla "SELECT ... FOR UPDATE" (blokada pesymistyczna
         * - poza główną treścią lekcji, ale wspominana jako technika radzenia sobie
         * z problemami współbieżności). Wykonaj SELECT FOR UPDATE w transakcji A
         * (bez commit), a potem spróbuj wykonać UPDATE tego samego wiersza z
         * transakcji B - zaobserwuj i wypisz zachowanie (zablokowanie/oczekiwanie
         * albo błąd, w zależności od konfiguracji timeoutu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_OptimisticLockingWithVersionColumn {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2), version
         * INT DEFAULT 0) - technika "blokady optymistycznej" jako ALTERNATYWA dla
         * wysokich poziomów izolacji. Napisz metodę updatePriceOptimistic(Connection
         * conn, int id, BigDecimal newPrice, int expectedVersion), która wykonuje
         * "UPDATE products SET price = ?, version = version + 1 WHERE id = ? AND
         * version = ?" - jeśli executeUpdate() zwróci 0 (ktoś inny zmienił dane
         * wcześniej), zgłasza konflikt. Zademonstruj udaną aktualizację i konflikt
         * (stary expectedVersion po tym, jak "ktoś inny" już zmienił wersję).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasurePerformanceCostOfHigherIsolation {
        /*
         * 🧪 Zadanie 25:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, balance INT) z 100 wierszami.
         * Zmierz czas wykonania 200 niezależnych transakcji (odczyt+UPDATE+commit)
         * pod READ_COMMITTED, a potem TE SAME 200 transakcji pod SERIALIZABLE -
         * wypisz porównanie czasów i skomentuj w println ogólną zasadę z lekcji, że
         * wyższa izolacja kosztuje wydajność (nawet jeśli w H2 różnica bywa mniej
         * widoczna niż w innych silnikach).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildIsolationAwareTransferWithChosenLevel {
        /*
         * 🧪 Zadanie 26:
         * Utwórz "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance >= 0)).
         * Napisz metodę transferWithIsolation(Connection conn, int fromId, int
         * toId, int amount, int isolationLevel), która ustawia PODANY poziom
         * izolacji PRZED transakcją, wykonuje przelew z pełną obsługą
         * commit/rollback (jak w Lesson19), i wypisuje, jaki poziom był użyty.
         * Wywołaj ją 3 razy z różnymi poziomami (READ_COMMITTED, REPEATABLE_READ,
         * SERIALIZABLE) dla 3 niezależnych przelewów i zweryfikuj, że WSZYSTKIE dają
         * ten sam poprawny wynik biznesowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DetectAndReportAnomalyTypeAutomatically {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę classifyAnomaly(String url, int isolationLevel), która
         * odtwarza scenariusz non-repeatable-read (2 odczyty w transakcji A,
         * modyfikacja przez B pomiędzy nimi) pod PODANYM poziomem izolacji i zwraca
         * String opisujący wynik ("NON_REPEATABLE_READ_WYSTAPIL" albo "BRAK_ANOMALII").
         * Wywołaj ją dla WSZYSTKICH 4 poziomów izolacji (resetując dane między
         * wywołaniami) i wypisz zbiorczy raport klasyfikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransactionIsolationWithMultiStepBusinessProcess {
        /*
         * 🧪 Zadanie 28:
         * Utwórz "seats" (id INT PRIMARY KEY, reserved BOOLEAN DEFAULT FALSE) z 10
         * miejscami (symulacja rezerwacji biletów - klasyczny przypadek problemów
         * współbieżności). Zasymuluj 2 "klientów" próbujących zarezerwować TO SAMO
         * miejsce jednocześnie (2 połączenia, READ_COMMITTED, przeplot: oba
         * odczytują reserved=false, oba próbują UPDATE) - zaobserwuj i wypisz, czy
         * OBA zdążyły "zarezerwować" (typowy bug bez odpowiedniej blokady) - a potem
         * napraw to przez dodanie warunku "WHERE reserved = FALSE" w UPDATE i
         * sprawdzenie affected rows (0 = ktoś już zarezerwował) - zademonstruj
         * poprawioną wersję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ComprehensiveIsolationLevelBenchmarkReport {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj KOMPLETNY benchmark: dla WSZYSTKICH 4 poziomów izolacji wykonaj
         * IDENTYCZNY scenariusz (10 "klientów" symulowanych sekwencyjnie na
         * przeplatanych połączeniach, każdy odczytuje i modyfikuje wspólny licznik -
         * jak w Zadaniu 16/22) i dla każdego poziomu zmierz: (a) czas wykonania, (b)
         * finalną wartość licznika (czy doszło do lost update), (c) czy wystąpił
         * dirty read w wariancie kontrolnym. Wypisz KOMPLETNĄ tabelę porównawczą
         * wszystkich 4 poziomów wg 3 kryteriów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneConcurrencySafeBankingSystem {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj mini-system bankowy odporny na problemy współbieżności:
         * "accounts" (id INT PRIMARY KEY, balance INT CHECK (balance >= 0), version
         * INT DEFAULT 0). Napisz KOMPLETNE API: safeWithdraw(Connection conn, int
         * id, int amount) używające blokady optymistycznej (jak w Zadaniu 24, z
         * PĘTLĄ ponawiającą próbę przy konflikcie wersji, do 3 razy) ORAZ
         * safeTransfer(Connection conn, int fromId, int toId, int amount, int
         * isolationLevel) z jawnie wybranym poziomem izolacji. Zademonstruj
         * WSZYSTKIE nauczone w tym rozdziale techniki naraz: (1) symulację race
         * condition bez ochrony (jak w Zadaniu 22, żeby pokazać problem), (2) tę samą
         * operację Z blokadą optymistyczną (poprawny wynik), (3) transfer z
         * REPEATABLE_READ i pełną obsługą rollback. Wypisz końcowy raport
         * podsumowujący wszystkie 3 demonstracje z wnioskami.
         */
        public static void main(String[] args) { }
    }
}
