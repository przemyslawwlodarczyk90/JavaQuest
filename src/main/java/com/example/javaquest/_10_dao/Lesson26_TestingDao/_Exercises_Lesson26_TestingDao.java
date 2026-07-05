package com.example.javaquest._10_dao.Lesson26_TestingDao;

public class _Exercises_Lesson26_TestingDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MiniTestRunnerSkeleton {
        /*
         * 🧪 Zadanie 1:
         * Napisz mini test-runner (jak w lekcji: funkcyjny interfejs TestCase z
         * "void run() throws Exception", statyczne liczniki passed/failed, metoda
         * runTest(name, testCase) lapiaca AssertionError i inne wyjatki). Zarejestruj
         * i wywolaj JEDEN, zawsze przechodzacy test (assertTrue(true, "...")) i
         * wypisz koncowy wynik "X passed, Y failed".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AssertTrueAndAssertEqualsHelpers {
        /*
         * 🧪 Zadanie 2:
         * Napisz metody pomocnicze assertTrue(boolean, String) i assertEquals(Object,
         * Object, String) (jak w lekcji - rzucaja AssertionError przy niepowodzeniu).
         * Napisz 4 male testy (2 uzywajace assertTrue, 2 assertEquals) - 3
         * przechodzace, 1 celowo failujacy - i sprawdz koncowy raport runnera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FreshConnectionHelperWithSetUp {
        /*
         * 🧪 Zadanie 3:
         * Napisz metody freshConnection() (otwiera polaczenie do
         * "jdbc:h2:mem:lesson26ex03;DB_CLOSE_DELAY=-1" i wywoluje setUp) oraz
         * setUp(connection) (DROP TABLE IF EXISTS + CREATE TABLE dla prostej tabeli
         * items(id, name)) - jak w lekcji. Napisz test sprawdzajacy, ze PO setUp
         * tabela jest PUSTA (SELECT COUNT(*) = 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestSaveAssignsGeneratedId {
        /*
         * 🧪 Zadanie 4:
         * Napisz ItemDao z metoda long save(String name) (INSERT z
         * RETURN_GENERATED_KEYS). Napisz test testSaveAssignsGeneratedId
         * weryfikujacy assertTrue(id > 0, ...) - analogicznie do
         * testSaveAssignsGeneratedId z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TestFindByIdReturnsSavedEntity {
        /*
         * 🧪 Zadanie 5:
         * Rozbuduj ItemDao o Optional<String> findById(long id). Napisz test
         * testFindByIdReturnsSavedItem zapisujacy element, odczytujacy go przez
         * findById i weryfikujacy assertTrue(found.isPresent()) oraz
         * assertEquals(oczekiwana nazwa, found.get(), "nazwa").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestFindByIdReturnsEmptyForMissingRow {
        /*
         * 🧪 Zadanie 6:
         * Napisz test testFindByIdReturnsEmptyForMissingItem wywolujacy
         * findById(999L) na PUSTEJ (dopiero co odtworzonej) tabeli i sprawdzajacy
         * assertTrue(found.isEmpty(), ...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestDeleteRemovesRow {
        /*
         * 🧪 Zadanie 7:
         * Rozbuduj ItemDao o metode deleteById(long id). Napisz test
         * testDeleteRemovesItem: zapisz element, usun go, sprawdz assertTrue
         * (findById(id).isEmpty()) po usunieciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestFindAllReturnsAllInsertedRows {
        /*
         * 🧪 Zadanie 8:
         * Rozbuduj ItemDao o metode List<String> findAllNames(). Napisz test
         * testFindAllReturnsAllInsertedItems: zapisz 4 elementy, wywolaj
         * findAllNames() i sprawdz assertEquals(4, wynik.size(), ...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IntentionallyFailingTestProvesRunnerWorks {
        /*
         * 🧪 Zadanie 9:
         * Napisz test test_CELOWO_FAILING (jak w lekcji) z BLEDNYM zalozeniem
         * (assertEquals(10, faktyczna_liczba_elementow, ...) gdy naprawde jest ich
         * mniej). Uruchom runner z TYM testem WSRÓD innych (poprawnych) i sprawdz,
         * ze raport koncowy poprawnie liczy 1 failed - dowod, ze runner NAPRAWDE
         * wykrywa niepowodzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RunFiveTestsFullReport {
        /*
         * 🧪 Zadanie 10:
         * Zarejestruj i uruchom WSZYSTKIE testy z Zadan 4-9 (6 testow lacznie) przez
         * jedno wywolanie runnera w main(). Wypisz koncowy raport "X passed, Y
         * failed" i sprawdz, ze liczby sa zgodne z oczekiwanym wynikiem (5 passed, 1
         * failed z celowego testu).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TestIsolationBetweenTwoTestsUsingSameTable {
        /*
         * 🧪 Zadanie 11:
         * Napisz DWA testy, KAZDY wstawiajacy inna liczbe elementow i sprawdzajacy
         * COUNT(*) rowna WLASNEJ liczbie wstawionych elementow. Uruchom je w OBU
         * mozliwych kolejnosciach (A potem B, oraz B potem A) i sprawdz, ze WYNIK
         * KAZDEGO testu jest identyczny niezaleznie od kolejnosci (dzieki setUp
         * odtwarzajacemu tabele PRZED kazdym testem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestUniqueConstraintViolationBehavior {
        /*
         * 🧪 Zadanie 12:
         * Dodaj do schematu testowego ograniczenie UNIQUE na kolumnie name. Napisz
         * test testDuplicateNameThrowsSqlException wywolujacy save() dwukrotnie z
         * TA SAMA nazwa i weryfikujacy (przez try/catch wewnatrz testu +
         * assertTrue), ze DRUGI insert rzuca SQLException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestUpdateMethodChangesValue {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj ItemDao o metode void updateName(long id, String newName). Napisz
         * test testUpdateNameChangesValue: zapisz element, zaktualizuj nazwe,
         * odczytaj przez findById i sprawdz assertEquals(nowa nazwa, odczytana
         * wartosc, ...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestCountMethodAfterMultipleOperations {
        /*
         * 🧪 Zadanie 14:
         * Rozbuduj ItemDao o metode long count(). Napisz test
         * testCountReflectsInsertsAndDeletes wykonujacy sekwencje: 3x save, 1x
         * delete, sprawdzajacy assertEquals(2, count(), ...) po tej sekwencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BeforeEachEquivalentAppliedConsistently {
        /*
         * 🧪 Zadanie 15:
         * Napisz PIEC testow korzystajacych z freshConnection() (kazdy odtwarza
         * czysta tabele PRZED swoim wykonaniem). Dodaj do setUp() statyczny licznik
         * setUpCallsCount inkrementowany przy KAZDYM wywolaniu i sprawdz PO
         * uruchomieniu wszystkich piecu testow, ze licznik wynosi DOKLADNIE 5 -
         * dowod, ze setUp() faktycznie wykonuje sie przed KAZDYM testem, nie tylko
         * raz na starcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestingDaoMethodThatThrowsOnInvalidInput {
        /*
         * 🧪 Zadanie 16:
         * Rozbuduj ItemDao.save(String name), by rzucal IllegalArgumentException dla
         * name==null lub pustego (walidacja w DAO, przed SQL). Napisz test
         * testSaveRejectsBlankName sprawdzajacy (przez try/catch wewnatrz testu),
         * ze wywolanie save("") rzuca IllegalArgumentException, a NIE dociera do
         * bazy (assertTrue ze count() pozostaje 0 po tej probie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RunnerCatchesUnexpectedExceptionSeparatelyFromAssertionError {
        /*
         * 🧪 Zadanie 17:
         * Napisz test rzucajacy NIEOCZEKIWANY wyjatek (nie AssertionError, np. NIE
         * zlapany SQLException z bledna skladnia SQL) i sprawdz, ze runner (jak w
         * lekcji: catch (AssertionError) vs catch (Exception)) poprawnie rozpoznaje
         * i raportuje ta ROZNICE w komunikacie ("nieoczekiwany wyjatek: ..." vs zwykly
         * komunikat AssertionError).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestSuiteForTwoDifferentDaoClassesInOneRunner {
        /*
         * 🧪 Zadanie 18:
         * Dodaj druga tabele i DRUGI DAO (np. CategoryDao z podobnymi metodami do
         * ItemDao). Uruchom W JEDNYM runnerze testy dla OBU klas DAO (co najmniej 3
         * testy na kazda) i sprawdz, ze raport koncowy poprawnie sumuje wyniki
         * WSZYSTKICH testow niezaleznie od tego, ktorego DAO dotycza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TimingEachTestExecution {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj runTest(name, testCase), by mierzyl czas wykonania KAZDEGO testu
         * (System.nanoTime()) i dopisywal go do komunikatu PASSED/FAILED (np.
         * "[PASSED] testX (3ms)"). Uruchom co najmniej 5 testow i sprawdz, ze
         * czasy sa wypisywane dla wszystkich, niezaleznie od wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_H2VsTestcontainersTradeoffDocumented {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode compareTestStrategies() wypisujaca (jako komentarz w
         * kodzie oraz jako log na konsole) porownanie H2 in-memory vs Testcontainers
         * z lekcji: szybkosc startu, zaleznosc od Dockera, wiernosc wobec
         * produkcyjnej bazy. Zademonstruj (praktycznie) tylko czesc H2 - zmierz
         * (System.nanoTime()) czas otwarcia polaczenia H2 in-memory i wypisz go jako
         * argument za jego szybkoscia.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TestingTransactionalDaoMethodWithRollback {
        /*
         * 🧪 Zadanie 21:
         * Rozbuduj ItemDao o metode transferQuantity(fromId, toId, amount)
         * (analogiczna do transferMoney z Lesson15_JdbcTransactions - operujaca na
         * kolumnie quantity). Napisz test testTransferRollsBackOnInsufficientQuantity
         * weryfikujacy, ze PO nieudanym transferze (za duza kwota) OBIE wartosci
         * quantity sa NIEZMIENIONE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ParameterizedTestPatternWithoutJUnit {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNY odpowiednik "testu parametryzowanego" - metoda
         * runParameterizedTest(String testName, List<Object[]> cases,
         * BiConsumer<Object[], ItemDao> testLogic) wywolujaca testLogic() dla
         * KAZDEGO zestawu danych z listy, raportujac PASSED/FAILED OSOBNO dla
         * kazdego przypadku (np. "testName[0]", "testName[1]"...). Zademonstruj na
         * 4 zestawach danych testujacych save() z roznymi nazwami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestingConcurrentAccessToSameDao {
        /*
         * 🧪 Zadanie 23:
         * Napisz test testConcurrentSavesProduceCorrectCount uruchamiajacy DWA WATKI
         * (kazdy z WLASNYM Connection do TEJ SAMEJ bazy testowej), kazdy zapisujacy
         * 10 elementow. Poczekaj na oba (join z limitem) i sprawdz assertEquals(20,
         * count(), ...) - test integracyjny weryfikujacy zachowanie DAO pod
         * wspolbieznym obciazeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestSuiteWithSetupAndTeardownHooks {
        /*
         * 🧪 Zadanie 24:
         * Rozbuduj runner o mechanizm "tearDown" - metoda wywolywana PO KAZDYM
         * tescie (niezaleznie od wyniku), np. zamykajaca dodatkowe zasoby albo
         * logujaca "test zakonczony". Zademonstruj na 3 testach, ze tearDown
         * wywoluje sie ZAWSZE, w tym po tescie, ktory zakonczyl sie FAILED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TestCoverageReportByCategory {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj runner, by KAZDY test byl rejestrowany z "kategoria" (np. "CRUD",
         * "WALIDACJA", "TRANSAKCJE" - dodatkowy parametr przy rejestracji). Po
         * uruchomieniu WSZYSTKICH testow (co najmniej 9, po 3 na kategorie) wypisz
         * RAPORT z podzialem na kategorie: liczba passed/failed W KAZDEJ kategorii
         * osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MutationTestingStyleVerification {
        /*
         * 🧪 Zadanie 26:
         * Zademonstruj koncepcje "mutation testing" recznie: skopiuj metode
         * findById, WPROWADZ do niej celowy blad (np. zla nazwa kolumny w WHERE), i
         * uruchom PELNY zestaw testow z Zadania 10 na TEJ zmutowanej wersji. Sprawdz
         * (i wypisz), KTORE testy "wylapaly" mutacje (zmienily wynik z PASSED na
         * FAILED) - to dowod jakosci zestawu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TestingDaoAgainstMultipleDatabaseConfigurations {
        /*
         * 🧪 Zadanie 27:
         * Uruchom CALY zestaw testow z Zadania 10 TRZYKROTNIE, na TRZECH ROZNYCH,
         * niezaleznych bazach H2 (rozne nazwy w URL). Sprawdz, ze wyniki (liczba
         * passed/failed) sa IDENTYCZNE dla wszystkich trzech przebiegow - dowod
         * pelnej izolacji miedzy "srodowiskami testowymi".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AssertThrowsHelperMethod {
        /*
         * 🧪 Zadanie 28:
         * Napisz generyczna metode pomocnicza <T extends Throwable> void
         * assertThrows(Class<T> expectedType, ThrowingRunnable action, String message)
         * (analogicznie do JUnit) - lapie wyjatek z action.run(), sprawdza jego typ,
         * rzuca AssertionError jesli NIE zostal rzucony wyjatek OCZEKIWANEGO typu.
         * Uzyj jej w co najmniej 3 testach z poprzednich zadan, ZASTEPUJAC reczny
         * try/catch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestSuiteRegressionAfterRefactoringDao {
        /*
         * 🧪 Zadanie 29:
         * Uruchom PELNY zestaw testow z Zadania 10 PRZED refaktoryzacja ItemDao
         * (zapisz wynik). Zrefaktoryzuj ItemDao (np. zmien implementacje findById z
         * PreparedStatement na inny, rownowazny sposob budowania zapytania - bez
         * zmiany SEMANTYKI). Uruchom TEN SAM zestaw testow PONOWNIE i sprawdz, ze
         * wyniki sa IDENTYCZNE - testy jako "siec bezpieczenstwa" przy
         * refaktoryzacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullDaoTestSuiteWithReportAndCategoriesAndTiming {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletny "framework testowy" DAO: runner z kategoriami (Zadanie 25),
         * pomiarem czasu (Zadanie 19), assertThrows (Zadanie 28), test
         * wspolbiezny (Zadanie 23) i test transakcyjny (Zadanie 21). Uruchom co
         * najmniej 12 testow (mieszanka wszystkich typow) i wypisz KOMPLETNY raport
         * koncowy: laczna liczba passed/failed, rozklad po kategoriach, laczny czas
         * wykonania calego zestawu.
         */
        public static void main(String[] args) { }
    }
}
