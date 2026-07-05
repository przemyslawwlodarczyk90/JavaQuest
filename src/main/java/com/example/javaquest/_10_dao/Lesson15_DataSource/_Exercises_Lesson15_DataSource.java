package com.example.javaquest._10_dao.Lesson15_DataSource;

public class _Exercises_Lesson15_DataSource {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DriverManagerAlwaysNewConnection {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l15ex01;DB_CLOSE_DELAY=-1", wywołaj
         * DriverManager.getConnection(url) DWA razy i porównaj obiekty przez ==
         * - wypisz wynik (powinno być false, to dwa różne obiekty Connection).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MethodAcceptingDataSourceParameter {
        /*
         * 🧪 Zadanie 2:
         * Napisz metodę countRows(DataSource dataSource, String tableName)
         * (parametr typu INTERFEJSU DataSource, nie konkretnej klasy) - pożycza
         * Connection przez dataSource.getConnection(), wykonuje SELECT COUNT(*)
         * i zwraca wynik. Wywołaj ją z HikariDataSource.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SimpleDriverManagerDataSourceUsage {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj (albo skopiuj wzorem z lekcji) SimpleDriverManagerDataSource
         * implements DataSource. Użyj jej z metodą countRows z Zadania 2 na
         * tabeli utworzonej wcześniej - pokaż, że metoda działa identycznie jak
         * z HikariDataSource.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_HikariDataSourceImplementsDataSourceInterface {
        /*
         * 🧪 Zadanie 4:
         * Utwórz HikariDataSource i przypisz je do zmiennej typu DataSource
         * (nie HikariDataSource) - zademonstruj, że kompiluje się bez castowania,
         * bo HikariDataSource implementuje javax.sql.DataSource. Wypisz
         * dataSource.getClass().getSimpleName().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DescribeDataSourceImplementationType {
        /*
         * 🧪 Zadanie 5:
         * Napisz metodę describe(DataSource dataSource) (jak w lekcji) zwracającą
         * "typ: " + dataSource.getClass().getSimpleName(). Wywołaj ją z DWOMA
         * różnymi implementacjami (Hikari i Simple) i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SameMethodTwoImplementations {
        /*
         * 🧪 Zadanie 6:
         * Napisz runQuery(DataSource dataSource) (jak w lekcji) wykonującą
         * proste zapytanie SELECT COUNT(*). Wywołaj ją DWA razy - raz z
         * HikariDataSource, raz z SimpleDriverManagerDataSource - na tej samej
         * tabeli i porównaj wyniki (powinny być identyczne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DataSourceGetConnectionWithCredentials {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj (albo użyj) DataSource.getConnection(String username,
         * String password) - drugą wersję metody z jawnymi danymi logowania,
         * inną niż bezargumentowa getConnection(). Wywołaj obie wersje na
         * SimpleDriverManagerDataSource i sprawdź, że OBIE działają.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SetUpSchemaViaDataSourceParameter {
        /*
         * 🧪 Zadanie 8:
         * Napisz setUpSchemaIfNeeded(DataSource dataSource) (jak w lekcji, z
         * "CREATE TABLE IF NOT EXISTS"). Wywołaj ją DWA razy pod rząd z tym samym
         * DataSource i sprawdź, że NIE rzuca wyjątku przy drugim wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DaoConstructedWithDataSourceInterface {
        /*
         * 🧪 Zadanie 9:
         * Napisz UserDao przyjmujący w konstruktorze DataSource (typ
         * interfejsu, nie HikariDataSource ani żadna konkretna klasa). Utwórz
         * DWIE instancje DAO - jedną z HikariDataSource, jedną z
         * SimpleDriverManagerDataSource - i wywołaj insert/findAll na obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareDriverManagerVsDataSourceMentalModel {
        /*
         * 🧪 Zadanie 10:
         * W main napisz komentarz porównujący DriverManager.getConnection(url)
         * (statyczna metoda) z dataSource.getConnection() (metoda instancyjna
         * interfejsu) - i zademonstruj OBA wywołania na tej samej bazie,
         * wypisując wynik prostego zapytania z każdego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ServiceLayerDependsOnDataSourceInterface {
        /*
         * 🧪 Zadanie 11:
         * Napisz UserService przyjmujący UserDao (z Zadania 9). Utwórz DWIE
         * instancje Service z DWOMA różnymi implementacjami DataSource
         * przekazanymi do DAO i zademonstruj IDENTYCZNE zachowanie metody
         * biznesowej registerUser niezależnie od implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CustomDataSourceWithLogging {
        /*
         * 🧪 Zadanie 12:
         * Napisz LoggingDataSource implements DataSource - w konstruktorze
         * przyjmuje INNY DataSource (delegat) i w getConnection() wypisuje log
         * "[LOG] pozyczanie polaczenia..." PRZED delegowaniem do
         * delegate.getConnection(). Owiń nią HikariDataSource i zademonstruj log
         * przy 3 wywołaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UnsupportedMethodsOfSimpleDataSource {
        /*
         * 🧪 Zadanie 13:
         * Sprawdź zachowanie SimpleDriverManagerDataSource.getParentLogger() -
         * złap SQLFeatureNotSupportedException i wypisz komunikat. Sprawdź też
         * unwrap(Class) - złap SQLException. Wyjaśnij w komentarzu, czemu prosta
         * implementacja edukacyjna nie musi w pełni wspierać całego interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CountingDataSourceDecorator {
        /*
         * 🧪 Zadanie 14:
         * Napisz CountingDataSource implements DataSource - dekorator zliczający
         * (AtomicLong) liczbę wywołań getConnection() na delegowanym DataSource.
         * Owiń nią HikariDataSource, wywołaj getConnection() 7 razy i wypisz
         * licznik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SwapDataSourceAtRuntimeBasedOnFlag {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę DataSource createDataSource(boolean usePool) zwracającą
         * HikariDataSource, gdy usePool==true, a SimpleDriverManagerDataSource,
         * gdy false. Wywołaj metodę biznesową korzystającą z DataSource dla OBU
         * wartości flagi i porównaj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DataSourceWithReadOnlyWrapper {
        /*
         * 🧪 Zadanie 16:
         * Napisz ReadOnlyDataSource implements DataSource, która w
         * getConnection() ustawia connection.setReadOnly(true) na połączeniu
         * PRZED jego zwróceniem (delegacja do wewnętrznego DataSource). Spróbuj
         * wykonać INSERT przez takie połączenie i sprawdź zachowanie (H2 może
         * rzucić SQLException albo zignorować - zweryfikuj empirycznie i opisz
         * wynik w komentarzu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultipleDaosSharingOneDataSourceInterfaceReference {
        /*
         * 🧪 Zadanie 17:
         * Utwórz JEDNĄ zmienną typu DataSource (przypisaną z HikariDataSource) i
         * przekaż JĄ (nie konkretny typ) do DWÓCH różnych DAO (UserDao,
         * ProductDao). Wykonaj operacje na obu i sprawdź, że korzystają z tego
         * samego, współdzielonego źródła połączeń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestingWithFakeDataSourceThrowingOnPurpose {
        /*
         * 🧪 Zadanie 18:
         * Napisz FailingDataSource implements DataSource, której
         * getConnection() ZAWSZE rzuca SQLException("Baza niedostepna") - reszta
         * metod może rzucać UnsupportedOperationException. Użyj jej do
         * przetestowania, jak Twoje DAO/Service reaguje na CAŁKOWITĄ
         * niedostępność bazy (bez uruchamiania prawdziwej, zepsutej bazy danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DataSourceGetLogWriterBehavior {
        /*
         * 🧪 Zadanie 19:
         * Sprawdź getLogWriter()/setLogWriter(PrintWriter) na
         * SimpleDriverManagerDataSource (zwraca null, metoda ustawiająca nic nie
         * robi - zgodnie z lekcją) - wypisz oba wywołania i porównaj z
         * HikariDataSource (które może mieć inne zachowanie - zweryfikuj i
         * opisz różnicę).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DataSourceInterfaceUsedInGenericRepository {
        /*
         * 🧪 Zadanie 20:
         * Napisz generyczną klasę SimpleRepository<T> przyjmującą DataSource i
         * RowMapper<T> (funkcyjny interfejs z Lesson04) w konstruktorze, z
         * metodą List<T> findAllFrom(String tableName). Użyj jej dla tabeli
         * users i tabeli products (dwa różne RowMapper), pokazując, że jedna
         * klasa obsługuje różne encje dzięki parametryzacji DataSource i
         * RowMapper.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullDaoLayerAcceptingAnyDataSourceImplementation {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny UserDao (CRUD) przyjmujący DataSource w konstruktorze.
         * Przetestuj TEN SAM zestaw operacji (save, findById, findAll, update,
         * deleteById) na TRZECH różnych implementacjach: HikariDataSource,
         * SimpleDriverManagerDataSource, oraz CountingDataSource (dekorator z
         * Zadania 14) owijającym jedną z powyższych. Porównaj wyniki i wypisz
         * finalny licznik z CountingDataSource.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DecoratorChainOfDataSources {
        /*
         * 🧪 Zadanie 22:
         * Złóż ŁAŃCUCH dekoratorów: LoggingDataSource owijające CountingDataSource
         * owijające HikariDataSource. Wykonaj 5 operacji przez ten łańcuch i
         * sprawdź, że KAŻDY poziom dekoratora poprawnie deleguje (logi się
         * wypisują, licznik się zwiększa, a bazowe HikariDataSource realnie
         * obsługuje połączenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DataSourceHealthCheckAcrossImplementations {
        /*
         * 🧪 Zadanie 23:
         * Napisz isHealthy(DataSource dataSource) próbującą pożyczyć i zwrócić
         * połączenie, zwracającą false przy wyjątku. Przetestuj ją na
         * HikariDataSource (zdrowej), SimpleDriverManagerDataSource (zdrowej) i
         * FailingDataSource z Zadania 18 (niezdrowej) - wypisz wynik dla
         * wszystkich trzech, pokazując, że JEDNA metoda obsługuje różne
         * implementacje interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PerformanceComparisonThreeDataSourceImplementations {
        /*
         * 🧪 Zadanie 24:
         * Zmierz (System.nanoTime()) czas wykonania 200 wywołań getConnection()+
         * proste zapytanie+close() dla TRZECH implementacji: HikariDataSource
         * (z pulą), SimpleDriverManagerDataSource (bez puli), oraz surowego
         * DriverManager.getConnection(url) (bez DataSource wcale). Wypisz
         * porównanie wszystkich trzech czasów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DataSourceSwitchingBasedOnEnvironmentSimulated {
        /*
         * 🧪 Zadanie 25:
         * Napisz DataSourceFactory.create(String environment) zwracającą
         * HikariDataSource skonfigurowane z większym poolem dla "PROD", a
         * SimpleDriverManagerDataSource (bez puli) dla "TEST" - zademonstruj
         * budowę OBU wariantów i wypisanie describe() dla każdego (analogiczne
         * do decyzji konfiguracyjnych z Lesson12/13, ale na poziomie wyboru
         * implementacji DataSource).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TransactionalRepositoryAcceptingDataSource {
        /*
         * 🧪 Zadanie 26:
         * Napisz AccountRepository przyjmujący DataSource, z metodą
         * transferMoney(fromId, toId, amount) pożyczającą JEDNO połączenie na
         * całą transakcję (setAutoCommit(false), 2 UPDATE, commit/rollback).
         * Przetestuj na HikariDataSource i SimpleDriverManagerDataSource -
         * zachowanie transakcyjne musi być identyczne w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MockableDataSourceForUnitTestsWithoutRealDb {
        /*
         * 🧪 Zadanie 27:
         * Napisz InMemoryFakeDataSource implements DataSource, której
         * getConnection() zwraca połączenie do H2 in-memory utworzonej "w
         * locie" (unikalny URL generowany przy KAŻDYM wywołaniu konstruktora
         * testu, np. z UUID) - symulacja "czystej bazy na każdy test". Użyj jej
         * do przetestowania UserDao BEZ współdzielenia stanu między dwoma
         * niezależnymi "testami" (metodami w main).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DataSourceInterfaceContractVerification {
        /*
         * 🧪 Zadanie 28:
         * Napisz verifyDataSourceContract(DataSource dataSource) sprawdzającą (z
         * wypisywanymi w konsoli asercjami): getConnection() nie zwraca null,
         * zwrócone Connection nie jest zamknięte (isClosed()==false) od razu po
         * otrzymaniu, a po close() jest zamknięte. Uruchom ją dla HikariDataSource
         * i SimpleDriverManagerDataSource i wypisz "kontrakt OK" dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CircuitBreakerStyleDataSourceWrapper {
        /*
         * 🧪 Zadanie 29:
         * Napisz CircuitBreakerDataSource implements DataSource - owija inny
         * DataSource i po 3 KOLEJNYCH nieudanych getConnection() (symulowanych
         * przez owinięcie FailingDataSource) "otwiera obwód" - kolejne
         * wywołania od razu rzucają SQLException("Circuit open") BEZ próby
         * delegowania, aż do "resetu" (metoda reset() zerująca licznik).
         * Zademonstruj otwarcie obwodu i reset.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullApplicationPolymorphicOverDataSource {
        /*
         * 🧪 Zadanie 30:
         * Złóż mini-aplikację (User/Product/Order DAO + odpowiednie Service)
         * przyjmującą DataSource jako JEDYNĄ zależność do bazy w CAŁEJ
         * aplikacji (żadna klasa poza samą konfiguracją startową nie odwołuje
         * się do konkretnej implementacji). Uruchom PEŁNY scenariusz biznesowy
         * (rejestracja, dodanie produktu, złożenie zamówienia) DWA razy - raz z
         * HikariDataSource, raz z SimpleDriverManagerDataSource owinięte
         * CountingDataSource - i porównaj wyniki końcowe oraz wypisz licznik
         * wywołań połączeń dla drugiego przebiegu.
         */
        public static void main(String[] args) { }
    }
}
