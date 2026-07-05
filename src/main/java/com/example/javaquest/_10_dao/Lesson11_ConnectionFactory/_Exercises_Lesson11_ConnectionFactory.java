package com.example.javaquest._10_dao.Lesson11_ConnectionFactory;

public class _Exercises_Lesson11_ConnectionFactory {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DuplicatedUrlInTwoDaos {
        /*
         * 🧪 Zadanie 1:
         * Napisz dwie klasy OrderDaoBefore i CustomerDaoBefore, KAŻDA z własną,
         * powieloną stałą DB_URL = "jdbc:h2:mem:l11ex01_before;DB_CLOSE_DELAY=-1".
         * Każda ma metodę setUp() tworzącą swoją tabelę. Wywołaj setUp() na obu i
         * wypisz obie stałe DB_URL, żeby zobaczyć powielenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BasicConnectionFactory {
        /*
         * 🧪 Zadanie 2:
         * Napisz klasę ConnectionFactory z prywatną stałą URL =
         * "jdbc:h2:mem:l11ex02;DB_CLOSE_DELAY=-1" i statyczną metodą getConnection().
         * Użyj jej do utworzenia połączenia i wypisania testowego zapytania
         * "SELECT 1".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TwoDaosUsingSameFactory {
        /*
         * 🧪 Zadanie 3:
         * Napisz OrderDaoAfter i CustomerDaoAfter, ŻADNA z nich nie zna URL-a -
         * obie korzystają z ConnectionFactory.getConnection() z Zadania 2. Każda
         * tworzy swoją tabelę w metodzie setUp() i ma metodę insert(). Wywołaj
         * obie setUp() i insert() i wypisz wynik odczytu z obu tabel.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrivateConstructorPreventsInstantiation {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj ConnectionFactory z PRYWATNYM konstruktorem (klasa
         * narzędziowa). W komentarzu wyjaśnij, czemu nie powinno się tworzyć
         * instancji tej klasy (new ConnectionFactory()) - wszystkie metody są
         * statyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FactoryWithUsernameAndPassword {
        /*
         * 🧪 Zadanie 5:
         * Rozbuduj ConnectionFactory o prywatne stałe USER="sa" i PASSWORD="".
         * Wywołaj getConnection() dwa razy i sprawdź, że OBA połączenia działają
         * (proste zapytanie na każdym), bez podawania danych logowania z
         * zewnątrz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleGetConnectionCallsAreIndependent {
        /*
         * 🧪 Zadanie 6:
         * Wywołaj ConnectionFactory.getConnection() DWA razy i sprawdź (przez
         * porównanie ==), że są to DWA różne obiekty Connection (fabryka bez puli
         * daje nowe połączenie za każdym razem). Zamknij oba połączenia w
         * try-with-resources.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DaoDoesNotKnowUrlAtAll {
        /*
         * 🧪 Zadanie 7:
         * Napisz ProductDaoAfter BEZ żadnej stałej URL/USER/PASSWORD - jedyna
         * referencja do bazy to wywołanie ConnectionFactory.getConnection()
         * wewnątrz metod. Zademonstruj insert i findAll bez ujawniania adresu
         * bazy w klasie DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ChangeUrlInOnePlaceAffectsAllDaos {
        /*
         * 🧪 Zadanie 8:
         * Zademonstruj "efekt jednego miejsca zmiany": zmień URL w
         * ConnectionFactory (np. na inną nazwę bazy H2 in-memory) i pokaż, że
         * WSZYSTKIE DAO korzystające z fabryki (min. 2 klasy) automatycznie
         * łączą się z nową bazą, bez modyfikacji samych DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FactoryCreatesTableIfNotExists {
        /*
         * 🧪 Zadanie 9:
         * Napisz UserDaoAfter.setUp() używający "CREATE TABLE IF NOT EXISTS" (jak
         * w lekcji), wywołaj setUp() DWA razy pod rząd na tej samej bazie przez
         * ConnectionFactory i sprawdź, że NIE rzuca wyjątku przy drugim wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareBeforeAndAfterLineCount {
        /*
         * 🧪 Zadanie 10:
         * Napisz OBIE wersje (Before z powieloną stałą URL w 2 klasach, After z
         * ConnectionFactory) dla tabeli reviews (id, content). W komentarzu
         * policz i porównaj, w ilu MIEJSCACH trzeba by zmienić kod, gdyby URL
         * bazy się zmienił, dla każdej z wersji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FactoryWithConfigurableUrlParameter {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj ConnectionFactory, by przyjmowała URL jako parametr metody
         * statycznej getConnection(String url) ZAMIAST stałej wewnętrznej (mniej
         * "sztywne" podejście - przygotowanie do dalszych lekcji o konfiguracji).
         * Wywołaj ją dla dwóch różnych URL-i i sprawdź, że łączy się z dwiema
         * różnymi bazami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FactoryUsedInsideServiceLayer {
        /*
         * 🧪 Zadanie 12:
         * Napisz UserService korzystający Z DAO (nie z fabryki bezpośrednio),
         * gdzie DAO korzysta z ConnectionFactory. Zademonstruj rejestrację 3
         * użytkowników przez Service, pokazując, że Service NIE WIE nic o
         * fabryce ani URL-u - zna tylko DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultipleDaosSharedSingleConnectionViaFactory {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę runInSingleConnection(Runnable-podobny kod) otwierającą
         * JEDNO Connection z ConnectionFactory i przekazującą je do DWÓCH DAO
         * (OrderDao, CustomerDao skonstruowanych z tym samym Connection) - w
         * odróżnieniu od wersji z lekcji, gdzie każdy DAO sam woła fabrykę przy
         * KAŻDEJ metodzie. Porównaj oba podejścia w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FactoryThrowsClearErrorOnBadUrl {
        /*
         * 🧪 Zadanie 14:
         * Zmodyfikuj (na potrzeby testu) ConnectionFactory, by URL wskazywał na
         * niepoprawny protokół (np. "jdbc:nieistniejacy:xyz"). Złap SQLException
         * rzucany przez getConnection() i wypisz czytelny komunikat - pokaż, że
         * błąd konfiguracji jest wykrywany w JEDNYM miejscu (fabryce), a nie w
         * każdym DAO z osobna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FactoryWrappedInTryWithResourcesHelper {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę pomocniczą <T> T withConnection(ConnectionCallback<T>
         * action) w klasie DAO, otwierającą Connection z ConnectionFactory w
         * try-with-resources i wykonującą przekazaną lambdę - eliminuje
         * powtarzanie "try (Connection c = ConnectionFactory.getConnection())" w
         * każdej metodzie DAO. Użyj jej w insert() i findAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FactoryUsedByThreeDifferentDaos {
        /*
         * 🧪 Zadanie 16:
         * Napisz TRZY różne DAO (UserDaoAfter, ProductDaoAfter, OrderDaoAfter)
         * korzystające z JEDNEJ, WSPÓLNEJ ConnectionFactory. Wykonaj po jednej
         * operacji insert na każdym i sprawdź (przez odczyt z ODDZIELNYCH
         * połączeń pobranych z tej samej fabryki), że wszystkie dane są widoczne
         * (bo to ta sama baza H2 z DB_CLOSE_DELAY=-1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureConnectionCreationCost {
        /*
         * 🧪 Zadanie 17:
         * Zmierz (System.nanoTime()) czas 100 wywołań ConnectionFactory.getConnection()
         * (każde od razu zamykane w try-with-resources). Wypisz łączny i średni
         * czas jednego wywołania - to przygotowanie do tematu puli połączeń w
         * Lesson14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FactorySingletonVsStaticMethodsDiscussion {
        /*
         * 🧪 Zadanie 18:
         * Napisz DRUGĄ wersję fabryki jako singleton (prywatny konstruktor +
         * statyczna metoda getInstance() zwracająca jedyną instancję + metoda
         * instancyjna getConnection()) ZAMIAST statycznych metod klasowych z
         * lekcji. Zademonstruj jej użycie i w komentarzu porównaj wady/zalety
         * obu podejść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ReplaceFactoryImplementationWithoutTouchingDaos {
        /*
         * 🧪 Zadanie 19:
         * Napisz DWIE różne implementacje fabryki o TEJ SAMEJ sygnaturze metody
         * getConnection() (np. jedna łącząca się z bazą "l11ex19a", druga z
         * "l11ex19b") w OSOBNYCH klasach ConnectionFactoryA i ConnectionFactoryB.
         * Napisz DAO przyjmujący w konstruktorze referencję do metody (Supplier
         * <Connection>) - podmień "fabrykę" bez zmiany kodu DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FactoryUsedAcrossServiceAndControllerLayers {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj mini-łańcuch Controller->Service->Dao->ConnectionFactory dla
         * rejestracji produktu. TYLKO DAO wie o istnieniu ConnectionFactory -
         * Service i Controller nie mają importu ani referencji do niej.
         * Zademonstruj pełny przepływ dla 2 produktów.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FactoryWithLazyInitialization {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj ConnectionFactory, która loguje ("Inicjalizuje polaczenie
         * z baza...") TYLKO przy PIERWSZYM wywołaniu getConnection() w całym
         * życiu programu (statyczna flaga boolean initialized). Wywołaj
         * getConnection() 5 razy i sprawdź, że log "inicjalizacji" wypisuje się
         * TYLKO raz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FactoryUsageAcrossMultipleThreads {
        /*
         * 🧪 Zadanie 22:
         * Uruchom 3 wątki, każdy wołający ConnectionFactory.getConnection() 20
         * razy i wykonujący prosty insert do wspólnej tabeli (każde wywołanie w
         * NOWYM połączeniu z fabryki). Poczekaj na zakończenie wszystkich wątków
         * (join z limitem czasu) i sprawdź finalną liczbę wierszy (powinno być
         * 60, bez utraconych wpisów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FactoryDecoratedWithConnectionCounter {
        /*
         * 🧪 Zadanie 23:
         * Rozbuduj ConnectionFactory o statyczny licznik AtomicLong zliczający,
         * ile RAZY w całym życiu programu wywołano getConnection(). Wywołaj ją z
         * 3 różnych DAO w różnych momentach i wypisz finalną wartość licznika -
         * to metryka przydatna do monitorowania obciążenia bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MigratingBeforeToAfterStepByStep {
        /*
         * 🧪 Zadanie 24:
         * Napisz 3 DAO w wersji "Before" (każdy z powieloną stałą URL). Przepisz
         * je JEDEN PO DRUGIM na wersję "After" (korzystającą z fabryki),
         * wypisując po każdym kroku migracji, ile klas WCIĄŻ ma powieloną stałą
         * URL (symulacja realnej, stopniowej refaktoryzacji dużego projektu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FactoryChoosingUrlBasedOnEnvironmentFlag {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj ConnectionFactory o statyczne pole "environment" (np. "TEST"
         * albo "DEV") i logikę wybierającą JEDEN z dwóch różnych URL-i H2 w
         * zależności od tej flagi (przygotowanie do tematu konfiguracji z
         * Lesson12/13). Zademonstruj połączenie z obiema bazami przez zmianę
         * flagi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FactoryWithRetryOnTransientFailure {
        /*
         * 🧪 Zadanie 26:
         * Napisz getConnectionWithRetry(int maxAttempts) próbującą
         * ConnectionFactory.getConnection() do maxAttempts razy, jeśli pierwsza
         * próba zawiedzie (zasymuluj celowo błędny URL na pierwsze 2 "próby"
         * przez zmienną licznikową w teście). Wypisz numer próby, przy której
         * połączenie się powiodło.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FactoryMetricsIntegratedWithServiceLayer {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj UserService korzystający z UserDao (który korzysta z
         * ConnectionFactory z licznikiem z Zadania 23). Po zarejestrowaniu 10
         * użytkowników przez Service wypisz, ile fizycznych połączeń zostało
         * utworzonych przez fabrykę (powinno odpowiadać liczbie wywołań DAO,
         * przy założeniu jednego getConnection() per metoda DAO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FactoryVsManualConnectionPassingHybrid {
        /*
         * 🧪 Zadanie 28:
         * Napisz DAO wspierające OBA style: konstruktor bez argumentów (pobiera
         * Connection z fabryki PRZY KAŻDYM wywołaniu metody) oraz konstruktor z
         * jawnym Connection (używa TEGO SAMEGO połączenia dla wszystkich metod -
         * przydatne w transakcjach). Zademonstruj obie ścieżki na tej samej
         * tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FactoryClosingBehaviorVerification {
        /*
         * 🧪 Zadanie 29:
         * Sprawdź, że Connection zwrócone przez ConnectionFactory.getConnection()
         * jest w PEŁNI zamykane po try-with-resources (connection.isClosed() ==
         * true po wyjściu z bloku) - w odróżnieniu od Connection z puli
         * (Lesson14), gdzie close() tylko "oddaje" połączenie. Zademonstruj
         * różnicę w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullApplicationWithSingleConnectionFactoryForAllDaos {
        /*
         * 🧪 Zadanie 30:
         * Złóż mini-aplikację z CZTEREMA DAO (User, Product, Order, Review)
         * korzystającymi z JEDNEJ, wspólnej ConnectionFactory. Zasymuluj pełny
         * scenariusz biznesowy (rejestracja użytkownika, dodanie produktu,
         * złożenie zamówienia, dodanie recenzji) przechodzący przez wszystkie
         * cztery DAO, i wypisz podsumowanie z licznikiem wywołań fabryki (z
         * Zadania 23) na końcu.
         */
        public static void main(String[] args) { }
    }
}
