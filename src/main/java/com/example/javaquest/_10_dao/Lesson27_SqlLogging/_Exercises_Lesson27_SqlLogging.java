package com.example.javaquest._10_dao.Lesson27_SqlLogging;

public class _Exercises_Lesson27_SqlLogging {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExecuteLoggedForSimpleInsert {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson27ex01;DB_CLOSE_DELAY=-1" utworz tabele
         * products (id, name, price) jak w lekcji. Zaimplementuj executeLogged(
         * connection, sql, params...) DOKLADNIE jak w lekcji (log w formacie "[SQL]
         * <sql> | params=[...] | time=Xms | rows=Y"). Wywolaj dla jednego INSERT i
         * sprawdz format wypisanego logu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExecuteLoggedForSelect {
        /*
         * 🧪 Zadanie 2:
         * Uzywajac executeLogged z Zadania 1, wykonaj SELECT * FROM products WHERE
         * price < ? (po wstawieniu kilku produktow). Sprawdz, ze log podaje w
         * "rows=" LICZBE ZWROCONYCH WIERSZY (nie liczbe zmienionych, jak przy
         * INSERT/UPDATE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExecuteLoggedForUpdate {
        /*
         * 🧪 Zadanie 3:
         * Wykonaj przez executeLogged zapytanie UPDATE products SET price = ? WHERE
         * name = ?. Sprawdz, ze "rows=" pokazuje LICZBE ZAKTUALIZOWANYCH wierszy
         * (getUpdateCount z executeUpdate).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParamsArrayLoggedCorrectly {
        /*
         * 🧪 Zadanie 4:
         * Wywolaj executeLogged z WIELOMA parametrami (np. INSERT z 2 kolumnami).
         * Sprawdz, ze log wypisuje WSZYSTKIE parametry w kolejnosci, w formacie
         * Arrays.toString (np. "[Klawiatura, 199.99]").
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SlowQueryThresholdConstant {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj SLOW_QUERY_THRESHOLD_MS = 100 (jak w lekcji). Wykonaj JEDNO
         * "szybkie" zapytanie (bez sztucznego opoznienia) i sprawdz, ze log NIE
         * zawiera ostrzezenia "WOLNE ZAPYTANIE".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExecuteSlowLoggedTriggersWarning {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj executeSlowLogged (jak w lekcji, z Thread.sleep(150)
         * wliczonym w pomiar czasu). Wywolaj ja i sprawdz, ze log ZAWIERA ostrzezenie
         * "<-- ⚠️ WOLNE ZAPYTANIE (powyzej 100ms)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NanoTimeVsCurrentTimeMillisComment {
        /*
         * 🧪 Zadanie 7:
         * Zmierz czas wykonania TEGO SAMEGO zapytania DWOMA sposobami:
         * System.nanoTime() (jak w lekcji) i System.currentTimeMillis(). Wypisz oba
         * wyniki i skomentuj (jak w podsumowaniu lekcji), czym rozni sie nanoTime -
         * jest odporne na zmiany zegara systemowego przy mierzeniu UPLYWU czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleLoggedQueriesInSequence {
        /*
         * 🧪 Zadanie 8:
         * Wykonaj SEKWENCJE 5 roznych zapytan (INSERT, INSERT, SELECT, UPDATE,
         * SELECT) przez executeLogged - sprawdz, ze KAZDE z nich generuje WLASNA,
         * niezalezna linie logu w jednolitym formacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IsSelectDetectionLogic {
        /*
         * 🧪 Zadanie 9:
         * Sprawdz (jak w lekcji: sql.trim().toUpperCase().startsWith("SELECT"))
         * dzialanie rozpoznawania typu zapytania na 3 przykladach: "select * from
         * x" (male litery), "  SELECT ..." (biale znaki na poczatku), "INSERT
         * INTO...". Wypisz, czy KAZDY z nich zostal poprawnie sklasyfikowany jako
         * SELECT albo nie-SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NoParamsQueryLogging {
        /*
         * 🧪 Zadanie 10:
         * Wywolaj executeLogged dla zapytania BEZ zadnych parametrow (np. "SELECT
         * COUNT(*) FROM products", bez varargs params). Sprawdz, ze log wypisuje
         * "params=[]" (pusta tablica), a nie blad/null.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConfigurableThresholdPerCallSite {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj executeTimed, by przyjmowal WLASNY prog SLOW_QUERY_THRESHOLD jako
         * PARAMETR (zamiast globalnej stalej) - dwa rozne wywolania moga miec dwa
         * rozne progi (np. 50ms dla "krytycznych" zapytan, 500ms dla "raportowych").
         * Zademonstruj oba progi na tym samym, sztucznie opoznionym zapytaniu (150ms)
         * - jedno wywolanie oznacza je jako wolne, drugie nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LogToInMemoryListInsteadOfConsole {
        /*
         * 🧪 Zadanie 12:
         * Zamiast System.out.println, zapisuj kazda linie logu do statycznej listy
         * List<String> queryLog. Wykonaj 5 zapytan i na koniec wypisz CALA
         * zawartosc queryLog naraz - zademonstruj, ze log mozna latwo "przechwycic"
         * do dalszej analizy (np. do testu weryfikujacego liczbe wolnych
         * zapytan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CountSlowQueriesFromLog {
        /*
         * 🧪 Zadanie 13:
         * Uzywajac listy z Zadania 12, napisz metode long countSlowQueries()
         * zliczajaca linie logu ZAWIERAJACE oznaczenie "WOLNE ZAPYTANIE". Wykonaj 4
         * zapytania (2 szybkie, 2 sztucznie spowolnione) i sprawdz, ze
         * countSlowQueries() zwraca 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LoggingWrapperAppliedToDaoClass {
        /*
         * 🧪 Zadanie 14:
         * Napisz ProductDao, ktorego WSZYSTKIE metody (insert, findByName, updatePrice)
         * WEWNETRZNIE korzystaja z executeLogged/executeTimed (zamiast bezposrednio
         * z PreparedStatement) - kazda operacja DAO jest automatycznie logowana bez
         * dopisywania logow recznie w kazdej metodzie. Zademonstruj na 3 wywolaniach
         * roznych metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MaskingSensitiveParametersInLog {
        /*
         * 🧪 Zadanie 15:
         * Rozbuduj logLine, by parametry na okreslonych POZYCJACH (np. przekazanych
         * jako Set<Integer> "wskaznikow do zamaskowania") byly wypisywane jako
         * "****" zamiast rzeczywistej wartosci (np. hasla). Zademonstruj na
         * zapytaniu INSERT INTO users (email, password) VALUES (?, ?) - w logu
         * email jest widoczny, a haslo zamaskowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AggregateStatisticsPerQueryType {
        /*
         * 🧪 Zadanie 16:
         * Rozbuduj executeTimed, by aktualizowal MAPE statystyk Map<String, Long>
         * (klucz: "SELECT"/"INSERT"/"UPDATE"/"DELETE", wartosc: suma czasu w ms).
         * Wykonaj po kilka zapytan kazdego typu i wypisz na koniec RAPORT: laczny
         * czas spedzony na kazdym typie operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TopNSlowestQueriesReport {
        /*
         * 🧪 Zadanie 17:
         * Wykonaj 10 zapytan o ROZNYCH, celowo zroznicowanych czasach trwania (uzyj
         * roznych wartosci artificialDelayMs). Zbierz kazdy wynik (sql + czas) do
         * listy i wypisz TOP 3 NAJWOLNIEJSZE zapytania (posortowane po czasie,
         * malejaco).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PercentageOfSlowQueriesAlert {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj 20 zapytan (mieszanka szybkich i wolnych, np. co 4. wolne). Napisz
         * metode checkSlowQueryRate(queryLog) obliczajaca PROCENT wolnych zapytan i
         * wypisujaca ostrzezenie "WYSOKI ODSETEK WOLNYCH ZAPYTAN", jesli przekracza
         * 20%. Zademonstruj na danych, ktore PRZEKRACZAJA ten prog.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LoggingWithCorrelationIdAcrossMultipleQueries {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj log, by KAZDA "operacja biznesowa" (grupa kilku zapytan
         * wykonanych w ramach jednego wywolania metody) miala WSPOLNY,
         * wygenerowany "correlation id" (np. UUID) dopisywany na POCZATKU kazdej
         * linii logu z tej operacji. Zademonstruj na operacji skladajacej sie z 3
         * zapytan - wszystkie 3 linie logu powinny miec TEN SAM correlation id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestingLoggingBehaviorWithMiniRunner {
        /*
         * 🧪 Zadanie 20:
         * Napisz mini test-runner (jak w Lesson26_TestingDao) z co najmniej 4
         * testami: "szybkie zapytanie NIE generuje ostrzezenia", "wolne zapytanie
         * GENERUJE ostrzezenie", "rows dla SELECT to liczba zwroconych wierszy",
         * "rows dla UPDATE to liczba zmienionych wierszy". Wypisz PASSED/FAILED dla
         * kazdego (test moze sprawdzac zawartosc listy queryLog z Zadania 12).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LoggingWrapperImplementedAsDecorator {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj interfejs SqlExecutor z metoda int execute(String sql, Object...
         * params) i DWIE implementacje: PlainSqlExecutor (bez logowania, bezposrednio
         * PreparedStatement) i LoggingSqlExecutor (DEKORATOR - przyjmuje w
         * konstruktorze inny SqlExecutor, dodaje logowanie WOKOL wywolania
         * "opakowanego" executora, zgodnie z wzorcem Decorator). Zademonstruj oba na
         * tym samym zapytaniu i pokaz roznice w wypisanym logu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NPlusOneQueryDetectionViaLogCounting {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj klasyczny problem "N+1 zapytan": napisz metode ladujaca liste 5
         * zamowien, a POTEM dla KAZDEGO z nich wykonujaca OSOBNE zapytanie SELECT po
         * dane produktu (5 dodatkowych zapytan, w petli) - wszystko przez
         * executeLogged. Napisz metode detectNPlusOne(queryLog) wykrywajaca (na
         * podstawie liczby PODOBNYCH zapytan SELECT w krotkim czasie) potencjalny
         * problem N+1 i wypisujaca ostrzezenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_QueryLogExportToCsvForExternalAnalysis {
        /*
         * 🧪 Zadanie 23:
         * Rozbuduj logowanie, by KAZDY wpis byl reprezentowany jako obiekt (record
         * QueryLogEntry(String sql, long timeMs, int rows, boolean slow)) zamiast
         * samego Stringa. Napisz metode String toCsv(List<QueryLogEntry> entries)
         * eksportujaca cala historie zapytan do formatu CSV (naglowek + wiersze).
         * Zademonstruj na logu z 8 zapytan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AdaptiveThresholdBasedOnMovingAverage {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj "adaptacyjny prog": zamiast STALEJ wartosci
         * SLOW_QUERY_THRESHOLD_MS, oblicz prog jako sredni czas WSZYSTKICH
         * dotychczasowych zapytan tego samego typu * 3 (zapytanie 3x wolniejsze niz
         * przecietne dla swojego typu = "wolne"). Zademonstruj na sekwencji 10
         * zapytan tego samego typu z jednym WYRAZNIE odstajacym czasem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ThreadSafeQueryLogUnderConcurrentAccess {
        /*
         * 🧪 Zadanie 25:
         * Zmien statyczna liste queryLog (z Zadania 12) na THREAD-SAFE strukture
         * (np. java.util.concurrent.CopyOnWriteArrayList albo synchronizowana
         * lista). Uruchom 4 WATKI, kazdy wykonujacy 25 zapytan przez executeLogged
         * (kazdy z WLASNYM Connection). Poczekaj na zakonczenie (join z limitem) i
         * sprawdz, ze queryLog ma DOKLADNIE 100 wpisow (bez utraty wpisow z powodu
         * wspolbieznego dostepu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LoggingIntegratedWithErrorHandling {
        /*
         * 🧪 Zadanie 26:
         * Rozbuduj executeTimed, by w przypadku SQLException RÓWNIEZ logowal linie
         * (np. "[SQL-ERROR] <sql> | params=[...] | time=Xms | error=<komunikat>") -
         * PRZED ponownym rzuceniem wyjatku dalej. Zademonstruj na celowo zlym
         * zapytaniu (nieistniejaca tabela) - sprawdz, ze log zawiera informacje o
         * BLEDZIE, a nie tylko o sukcesach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_QueryLogRetentionWithMaxSizeEviction {
        /*
         * 🧪 Zadanie 27:
         * Rozbuduj queryLog, by przechowywal TYLKO ostatnie 20 wpisow (przy
         * przekroczeniu limitu, usuwaj NAJSTARSZY - jak prosty bufor cykliczny).
         * Wykonaj 35 zapytan i sprawdz, ze queryLog.size() wynosi dokladnie 20, a
         * zawiera NAJNOWSZE 20 wpisow (nie najstarsze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ComparingLoggingOverheadWithAndWithoutWrapper {
        /*
         * 🧪 Zadanie 28:
         * Zmierz (System.nanoTime() OKALAJACY caly test, nie pojedyncze zapytanie)
         * laczny czas wykonania 500 zapytan DWOMA sposobami: przez executeLogged
         * (z pomiarem+logowaniem) i BEZ (czysty PreparedStatement bez logowania).
         * Wypisz oba czasy i skomentuj, jak duzy jest narzut samego mechanizmu
         * logowania w porownaniu do czasu samego SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestSuiteForLoggingInfrastructureWithMiniRunner {
        /*
         * 🧪 Zadanie 29:
         * Napisz mini test-runner z co najmniej 6 testami pokrywajacymi: adaptacyjny
         * prog (Zadanie 24), wykrywanie N+1 (Zadanie 22), maskowanie danych
         * wrazliwych (Zadanie 15), bufor cykliczny (Zadanie 27), logowanie bledow
         * (Zadanie 26), i thread-safety (Zadanie 25 - test prostszy, bez pelnej
         * wspolbieznosci, ale sprawdzajacy poprawnosc licznika). Wypisz raport
         * PASSED/FAILED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullSqlLoggingFrameworkMiniApp {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletny "framework logowania SQL": LoggingSqlExecutor (dekorator,
         * Zadanie 21) z adaptacyjnym progiem (Zadanie 24), maskowaniem danych
         * wrazliwych (Zadanie 15), wykrywaniem N+1 (Zadanie 22), bufor cykliczny
         * (Zadanie 27) i eksport CSV (Zadanie 23). Uzyj go do "przegonienia" 30
         * roznorodnych zapytan (w tym operacja z problemem N+1 i zapytanie z
         * hasłem) i wypisz KOMPLETNY raport koncowy (statystyki typow zapytan, top
         * najwolniejsze, wykryte problemy, eksport CSV pierwszych 10 wpisow).
         */
        public static void main(String[] args) { }
    }
}
