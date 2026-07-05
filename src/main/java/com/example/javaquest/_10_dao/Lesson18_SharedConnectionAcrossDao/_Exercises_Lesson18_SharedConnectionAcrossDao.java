package com.example.javaquest._10_dao.Lesson18_SharedConnectionAcrossDao;

public class _Exercises_Lesson18_SharedConnectionAcrossDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DaoOpeningOwnConnection {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson18ex01;DB_CLOSE_DELAY=-1" utworz tabele orders
         * (id, user_id). Napisz BadOrderDao z metoda insert(String url, long userId)
         * SAMA OTWIERAJACA polaczenie przez DriverManager.getConnection(url) (jak w
         * lekcji). Wywolaj ja 2 razy i sprawdz SELECT COUNT(*) - dziala poprawnie,
         * gdy wywolywana W IZOLACJI (bez transakcji z innym DAO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DaoAcceptingConnectionParameter {
        /*
         * 🧪 Zadanie 2:
         * Napisz GoodOrderDao z metoda insert(Connection connection, long userId)
         * PRZYJMUJACA polaczenie jako parametr (jak w lekcji). Otworz JEDNO
         * polaczenie recznie, wywolaj insert dwukrotnie na nim, zamknij polaczenie -
         * sprawdz nowym polaczeniem, ze sa 2 wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RollbackOnOwnConnectionWorks {
        /*
         * 🧪 Zadanie 3:
         * Na bazie "jdbc:h2:mem:lesson18ex03;DB_CLOSE_DELAY=-1", otworz JEDNO
         * polaczenie, wylacz autocommit, wstaw wiersz do orders przez GoodOrderDao,
         * wywolaj rollback() NA TYM SAMYM polaczeniu. Sprawdz SELECT COUNT(*) - 0
         * wierszy, rollback zadzialal, bo operacja i rollback byly na tym samym
         * Connection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TwoConnectionsAreIndependent {
        /*
         * 🧪 Zadanie 4:
         * Na bazie "jdbc:h2:mem:lesson18ex04;DB_CLOSE_DELAY=-1" otworz DWA ROZNE,
         * niezalezne polaczenia (C1, C2) do TEJ SAMEJ bazy w pamieci. Wstaw wiersz
         * przez C1 (autocommit domyslny=true, natychmiastowy commit), wywolaj
         * rollback() na C2 (ktory NIC nie wstawil) i pokaz, ze wiersz z C1 mimo
         * wszystko WCIAZ ISTNIEJE - rollback na C2 nie ma wplywu na dane z C1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DefaultAutoCommitCommitsImmediately {
        /*
         * 🧪 Zadanie 5:
         * Otworz polaczenie z DOMYSLNYM autocommit (bez zmiany), wstaw wiersz do
         * orders, ZAMKNIJ polaczenie BEZ wywolywania commit() recznie. Otworz NOWE
         * polaczenie do tej samej bazy i sprawdz SELECT COUNT(*) - wiersz jest
         * widoczny, bo autocommit=true zatwierdzil go natychmiast po executeUpdate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TwoDaosSameConnectionSimpleCase {
        /*
         * 🧪 Zadanie 6:
         * Na bazie "jdbc:h2:mem:lesson18ex06;DB_CLOSE_DELAY=-1" z tabelami orders i
         * order_items (jak w lekcji), otworz JEDNO polaczenie, wylacz autocommit,
         * wywolaj GoodOrderDao.insert i GoodOrderItemDao.insert NA TYM SAMYM
         * polaczeniu, zacommituj RAZ na koniec. Sprawdz, ze obie tabele maja
         * dokladnie 1 wiersz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BadScenarioReproduction {
        /*
         * 🧪 Zadanie 7:
         * Odtworz DOKLADNIE scenariusz runBadScenario() z lekcji na bazie
         * "jdbc:h2:mem:lesson18ex07;DB_CLOSE_DELAY=-1": BadOrderDao otwiera
         * polaczenie C1 (autocommit=false, insert do orders, BEZ commit),
         * BadOrderItemDao otwiera WLASNE polaczenie C2 (autocommit=true, insert do
         * order_items - zatwierdzony natychmiast), potem symulowany blad + rollback
         * TYLKO na C1. Wypisz stan obu tabel po tej probie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GoodScenarioReproduction {
        /*
         * 🧪 Zadanie 8:
         * Odtworz runGoodScenario() z lekcji na TEJ SAMEJ bazie po wyczyszczeniu
         * danych (DELETE FROM order_items; DELETE FROM orders) - JEDNO polaczenie
         * przekazywane do obu DAO, symulowany blad, JEDEN rollback. Porownaj stan
         * tabel z wynikiem Zadania 7 - w dobrej wersji obie tabele powinny byc puste,
         * w zlej - order_items ma "osierocony" wiersz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CountDiscrepancyBetweenOrdersAndItems {
        /*
         * 🧪 Zadanie 9:
         * Po odtworzeniu ZLEGO scenariusza (Zadanie 7), napisz metode
         * detectOrphanedOrderItems(connection) wykonujaca zapytanie LEFT JOIN
         * pomiedzy order_items i orders, wyszukujace wiersze order_items, dla
         * ktorych order_id NIE ISTNIEJE w orders (albo odwrotnie - w zaleznosci od
         * tego, co "przetrwalo"). Wypisz znalezione niespojnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CleanUpDataHelper {
        /*
         * 🧪 Zadanie 10:
         * Napisz metode cleanUpData(url) (jak w lekcji: DELETE FROM order_items;
         * DELETE FROM orders) i uzyj jej MIEDZY dwoma niezaleznymi przebiegami testu
         * (zla wersja, potem dobra wersja) na TEJ SAMEJ bazie, tak aby wyniki obu
         * przebiegow nie "zanieczyszczaly" sie nawzajem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeDaosMixedConnectionOwnership {
        /*
         * 🧪 Zadanie 11:
         * Na bazie "jdbc:h2:mem:lesson18ex11;DB_CLOSE_DELAY=-1" dodaj trzeci DAO
         * (ProductDao z decreaseStock przyjmujacym Connection). Zbuduj scenariusz, w
         * ktorym DWA z trzech DAO (order, order_item) uzywaja WSPOLNEGO polaczenia, a
         * TRZECI (product) OTWIERA WLASNE - pokaz, ze rollback wywolany na wspolnym
         * polaczeniu NIE cofa zmiany stanu magazynowego zrobionej przez trzeci,
         * "odizolowany" DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FixMixedOwnershipBug {
        /*
         * 🧪 Zadanie 12:
         * Napraw scenariusz z Zadania 11 - przebuduj ProductDao, by przyjmowal
         * Connection jako parametr, i przekaz do niego TO SAMO polaczenie co do
         * pozostalych dwoch DAO. Sprawdz, ze teraz rollback cofa WSZYSTKIE trzy
         * operacje (order, order_item, stock) razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DetectBugByCountingConnections {
        /*
         * 🧪 Zadanie 13:
         * Dodaj do BadOrderDao i BadOrderItemDao statyczny licznik
         * "openConnectionsCount", inkrementowany PRZY KAZDYM DriverManager.getConnection.
         * Wywolaj zly scenariusz i wypisz koncowa wartosc licznika (powinna byc >= 2 -
         * dowod, ze powstaly DWA NIEZALEZNE polaczenia, mimo ze logicznie to JEDNA
         * operacja biznesowa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SameFixVerifiedWithConnectionCounter {
        /*
         * 🧪 Zadanie 14:
         * Powtorz licznik z Zadania 13 dla DOBREJ wersji (GoodOrderDao/
         * GoodOrderItemDao, ktore NIE otwierajca wlasnego polaczenia). Sprawdz, ze
         * licznik otwartych polaczen wynosi dokladnie 1 dla calej operacji biznesowej
         * (jedno polaczenie otwarte w Service, przekazane do obu DAO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AutoCommitStateLeaksBetweenDaoCalls {
        /*
         * 🧪 Zadanie 15:
         * Na bazie "jdbc:h2:mem:lesson18ex15;DB_CLOSE_DELAY=-1" otworz WSPOLNE
         * polaczenie, wylacz autocommit, wywolaj GoodOrderDao.insert, ale ZAPOMNIJ
         * wywolac commit()/rollback() i po prostu zamknij polaczenie (close() bez
         * commitu). Otworz NOWE polaczenie i sprawdz SELECT COUNT(*) - wypisz, czy
         * wiersz przetrwal (H2 domyslnie robi rollback niezatwierdzonych zmian przy
         * close()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleGoodDaosFourStepsOneRollback {
        /*
         * 🧪 Zadanie 16:
         * Rozbuduj dobra wersje o CZWARTY krok (np. insert do tabeli order_log) na
         * TYM SAMYM, wspolnym polaczeniu. Zasymuluj blad w trzecim kroku (product) i
         * sprawdz, ze rollback cofa WSZYSTKIE CZTERY operacje - wlacznie z pierwszymi
         * dwoma, ktore "juz sie powiodly".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ConnectionPassedThroughThreeMethodLayers {
        /*
         * 🧪 Zadanie 17:
         * Napisz metody: placeOrder(connection, ...) -> insertOrderAndItem(connection,
         * ...) -> GoodOrderDao.insert(connection, ...) - Connection PRZEPLYWA przez
         * TRZY warstwy metod jako parametr, bez otwierania nowego w zadnej z nich.
         * Zweryfikuj (np. przez System.identityHashCode(connection)), ze WSZYSTKIE
         * trzy warstwy operuja na TYM SAMYM obiekcie Connection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SimulateTwoSeparateBusinessOperationsCorrectly {
        /*
         * 🧪 Zadanie 18:
         * Na bazie "jdbc:h2:mem:lesson18ex18;DB_CLOSE_DELAY=-1" wykonaj DWIE ROZNE
         * operacje biznesowe (dwa wywolania "placeOrder") - KAZDA z WLASNYM, osobno
         * otwieranym polaczeniem (poprawnie, bo to DWIE ROZNE, niezalezne
         * transakcje). Sprawdz, ze obie sie powiodly i ze uzycie DWOCH polaczen w
         * TYM przypadku jest wlasciwe (to nie jest ten sam blad co w lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteAssertionHelperForConsistency {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode assertConsistentState(connection) sprawdzajaca, ze KAZDY
         * order_id w order_items ISTNIEJE w orders (SELECT z NOT EXISTS albo LEFT
         * JOIN) - rzuca AssertionError, jesli znajdzie niespojnosc. Wywolaj ja PO
         * zlym scenariuszu (oczekuj AssertionError) i PO dobrym scenariuszu
         * (oczekuj braku wyjatku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorBadDaoStepByStep {
        /*
         * 🧪 Zadanie 20:
         * Zacznij od DZIALAJACEGO zlego scenariusza (Zadanie 7). Refaktoryzuj go
         * KROK PO KROKU: najpierw napraw TYLKO BadOrderItemDao (przyjmuje Connection),
         * uruchom i sprawdz stan (czesciowa poprawa), potem napraw TEZ BadOrderDao -
         * uruchom ponownie i pokaz, ze TERAZ dane sa w pelni spojne. Skomentuj, co
         * sie zmienilo miedzy kazdym krokiem.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ConcurrentGoodAndBadDaoRace {
        /*
         * 🧪 Zadanie 21:
         * Na bazie "jdbc:h2:mem:lesson18ex21;DB_CLOSE_DELAY=-1", uruchom DWA WATKI
         * rownolegle: jeden wykonuje 5 "dobrych" operacji biznesowych (wspolne
         * polaczenie na operacje), drugi wykonuje 5 "zlych" (kazdy DAO wlasne
         * polaczenie). Po zakonczeniu obu (join z limitem czasu) porownaj liczbe
         * "osieroconych" order_items - powinna wynikac WYLACZNIE z watka zlego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ConnectionLeakDetectionViaOpenCount {
        /*
         * 🧪 Zadanie 22:
         * Opakuj DriverManager.getConnection w statyczna metode pomocnicza
         * trackedGetConnection(url) zliczajaca WYWOLANIA i wykonaj pelny "zly"
         * scenariusz biznesowy skladajacy sie z 3 operacji (order+item+product, kazdy
         * z osobnym polaczeniem). Wypisz koncowa liczbe otwartych polaczen i
         * skomentuj, dlaczego w prawdziwej aplikacji (z limitowanym connection
         * poolem) taki wzorzec moglby wyczerpac cala pule.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RepairArchitectureWithServiceOwningConnection {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (i zaimplementuj) OrderService, ktory JEST JEDYNYM miejscem w
         * calym kodzie otwierajacym Connection - WSZYSTKIE DAO przyjmuja je jako
         * parametr metody, NIGDY jako pole obiektu ani przez wlasny
         * DriverManager.getConnection. Udokumentuj to w komentarzu jako "zasade
         * architektoniczna" i zweryfikuj dzialaniem na scenariuszu z 3 krokami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureImpactOfConnectionCountOnPerformance {
        /*
         * 🧪 Zadanie 24:
         * Zmierz (System.nanoTime()) czas wykonania 20 operacji biznesowych (kazda: 2
         * inserty) DWOMA sposobami: (a) zla wersja - kazdy insert osobne polaczenie
         * (40 polaczen), (b) dobra wersja - 1 polaczenie na operacje (20 polaczen).
         * Wypisz oba czasy i skomentuj, dlaczego wersja (a) jest wolniejsza (koszt
         * nawiazywania polaczenia TCP przy kazdym insercie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SimulatePartialCommitDisasterAndRecovery {
        /*
         * 🧪 Zadanie 25:
         * Odtworz zly scenariusz tak, by order_items mial "osierocony" wiersz (bez
         * odpowiadajacego orders). Napisz metode repairOrphanedOrderItems(connection)
         * usuwajaca WSZYSTKIE osierocone wiersze z order_items (DELETE ... WHERE
         * order_id NOT IN (SELECT id FROM orders)) i uzyj jej jako "naprawy po
         * fakcie" - sprawdz stan przed i po naprawie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestBothScenariosWithSharedAssertionRunner {
        /*
         * 🧪 Zadanie 26:
         * Napisz mini test-runner (jak w Lesson26_TestingDao, ale uproszczony -
         * assertTrue/assertEquals + try/catch AssertionError) sprawdzajacy DWA
         * przypadki: "zla wersja PRODUKUJE niespojnosc" (assertTrue ze
         * detectOrphanedOrderItems znajduje > 0) i "dobra wersja NIE PRODUKUJE
         * niespojnosci" (assertTrue ze detectOrphanedOrderItems zwraca 0). Wypisz
         * PASSED/FAILED dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ThreeLayerCallChainWithConnectionAudit {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj lancuch Controller -> Service -> DAO (3 warstwy metod), w ktorym KAZDA
         * warstwa loguje System.identityHashCode(connection) otrzymanego jako
         * parametr. Wywolaj cala operacje i sprawdz w logu, ze WSZYSTKIE trzy
         * warstwy zaraportowaly IDENTYCZNY hash - dowod przeplywu tego samego
         * obiektu przez cala aplikacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultipleBusinessOperationsEachOwnTransactionCorrectIsolation {
        /*
         * 🧪 Zadanie 28:
         * Wykonaj 10 NIEZALEZNYCH operacji biznesowych (kazda z WLASNYM, poprawnie
         * zarzadzanym polaczeniem/transakcja - wzorowanym na dobrej wersji), z czego
         * 3 celowo nieudane (rollback). Sprawdz, ze KAZDA nieudana operacja cofnela
         * WYLACZNIE swoje wlasne zmiany, nie wplywajac na 7 udanych - policz koncowa
         * liczbe wierszy w orders (powinna wynosic 7).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DocumentAntiPatternWithCodeReviewComment {
        /*
         * 🧪 Zadanie 29:
         * Napisz klase "CodeReviewExample" z DWOMA metodami: reviewedBadCode()
         * zawierajaca (jako kod, faktycznie wykonywany) wzorzec z osobnymi
         * polaczeniami oraz DOKLADNY komentarz code-review wytykajacy problem (jak
         * przy prawdziwym PR), i reviewedGoodCode() z poprawiona wersja + komentarzem
         * "APPROVED". Wykonaj obie i porownaj wynik stanu bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullRegressionSuiteForConnectionSharingBug {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "pakiet regresyjny" (mini test-runner) z co najmniej 5
         * testami weryfikujacymi: (1) zla wersja produkuje niespojnosc, (2) dobra
         * wersja nie produkuje niespojnosci, (3) rollback na wspolnym polaczeniu cofa
         * wszystkie kroki, (4) autocommit domyslny zatwierdza natychmiast, (5)
         * naprawa osieroconych wierszy dziala poprawnie. Wypisz koncowy raport
         * PASSED/FAILED dla wszystkich testow.
         */
        public static void main(String[] args) { }
    }
}
