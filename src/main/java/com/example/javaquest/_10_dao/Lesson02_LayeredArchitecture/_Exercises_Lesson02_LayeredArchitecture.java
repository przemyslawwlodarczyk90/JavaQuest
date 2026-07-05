package com.example.javaquest._10_dao.Lesson02_LayeredArchitecture;

public class _Exercises_Lesson02_LayeredArchitecture {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ThreeLayersForProducts {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l02ex01;DB_CLOSE_DELAY=-1" utwórz tabelę products
         * (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(150)). Napisz klasę
         * ProductDao (zna tylko SQL: insert(String name), boolean existsByName(String))
         * oraz ProductService (zna regułę biznesową: nazwa nie może być pusta,
         * deleguje zapis do ProductDao). ProductDao nie może zawierać ani jednej
         * linijki walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ControllerCallsServiceOnly {
        /*
         * 🧪 Zadanie 2:
         * Rozbuduj Zadanie 1 o metodę handleAddProductRequest(ProductService, String name)
         * symulującą Controller - przyjmuje surowe dane i wywołuje TYLKO ProductService
         * (nie ma dostępu do Connection ani DAO). Wywołaj ją dla nazwy poprawnej
         * i dla pustego stringa, wypisując odpowiedź "201 Created" albo "400 Bad Request".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DaoHasNoBusinessLogic {
        /*
         * 🧪 Zadanie 3:
         * Stwórz tabelę books (id, title) i BookDao z metodami insert(String title)
         * oraz existsByTitle(String title) - SAMYM SQL, bez żadnej walidacji formatu.
         * W main pokaż, że wywołanie BookDao.insert("") DZIAŁA (bo DAO nie ocenia
         * poprawności danych) - to warstwa Service powinna to zablokować, nie DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ServiceDelegatesNotDuplicatesSql {
        /*
         * 🧪 Zadanie 4:
         * Napisz UserDao (tabela users: id, email) z metodą existsByEmail(String)
         * i insert(String email) oraz UserService.registerUser(email) korzystającą
         * WYŁĄCZNIE z tych dwóch metod DAO (bez pisania żadnego SQL w Service).
         * Zarejestruj 2 różne e-maile i wypisz zawartość tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyLayerViolation {
        /*
         * 🧪 Zadanie 5:
         * Dany jest fragment kodu (przepisz go do main jako komentarz + kod):
         * metoda "controller" bezpośrednio otwiera Connection i wykonuje
         * "INSERT INTO orders...". Zrefaktoryzuj to na 3 warstwy: OrderDao
         * (insert), OrderService (bez logiki, na razie tylko delegacja),
         * handleCreateOrderRequest (Controller wołający TYLKO Service).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TwoBusinessRulesInService {
        /*
         * 🧪 Zadanie 6:
         * Tabela accounts (id, username, balance). AccountDao z insert(username,
         * balance) i existsByUsername(username). AccountService.openAccount(username,
         * balance) z DWIEMA regułami: username min. 3 znaki, balance >= 0. Przetestuj
         * na 4 przypadkach naruszających różne kombinacje reguł, wypisz komunikaty
         * odrzucenia z warstwy Service (nie z DAO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PrintAllThroughDaoOnly {
        /*
         * 🧪 Zadanie 7:
         * Tabela movies (id, title, year). MovieDao z insert(title, year) i
         * printAllViaSelect(Connection) (osobna metoda pomocnicza main, NIE w DAO,
         * czytająca bezpośrednio przez Statement - żeby zobaczyć różnicę między
         * "czytaniem przez DAO" a "czytaniem z zewnątrz"). Dodaj 3 filmy przez DAO,
         * wypisz je oboma sposobami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ControllerReturnsHttpLikeStrings {
        /*
         * 🧪 Zadanie 8:
         * Rozbuduj handleRegisterUserRequest z lekcji (email + firstName) o
         * przypadek z pustym firstName - Service ma zwrócić failure z komunikatem
         * "imie nie moze byc puste", a Controller ma to przetłumaczyć na
         * "400 Bad Request - imie nie moze byc puste". Przetestuj 3 przypadki
         * (sukces, zły e-mail, puste imię).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SwapControllerKeepServiceAndDao {
        /*
         * 🧪 Zadanie 9:
         * Używając UserDao/UserService z Zadania 4, napisz DWIE różne metody
         * "controllerowe": handleRegisterFromWebForm(UserService, email) i
         * handleRegisterFromCliCommand(UserService, email) - każda z innym
         * prefiksem logów, ale obie wywołujące TEN SAM UserService.registerUser.
         * Pokaż, że Service i DAO nie zmieniają się wcale.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CountLayersInvolved {
        /*
         * 🧪 Zadanie 10:
         * Dla operacji "usunięcie użytkownika po id" napisz pełny łańcuch trzech
         * warstw: UserDao.deleteById(id), UserService.deactivateUser(id) (reguła:
         * nie można usunąć użytkownika o id <= 0), handleDeleteUserRequest(controller).
         * W komentarzu w main wypisz, przez ile warstw przechodzi jedno żądanie
         * i co robi każda z nich.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ServiceCoordinatesTwoDaos {
        /*
         * 🧪 Zadanie 11:
         * Tabele customers (id, name) i orders (id, customer_id, total). CustomerDao
         * (insert, existsById) i OrderDao (insert przyjmujący customer_id). Napisz
         * OrderService.placeOrder(customerId, total) sprawdzającą PRZEZ CustomerDao,
         * że klient istnieje, PRZED wywołaniem OrderDao.insert. Przetestuj dla
         * istniejącego i nieistniejącego klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RefactorMixedMethodIntoLayers {
        /*
         * 🧪 Zadanie 12:
         * Napisz najpierw "złą" metodę registerProductBadWay mieszającą walidację
         * ceny (>0) z SQL insertu do tabeli products w JEDNEJ metodzie. Następnie
         * zrefaktoryzuj na ProductDao.insert(name, price) (tylko SQL) i
         * ProductService.registerProduct(name, price) (tylko walidacja + delegacja).
         * Uruchom obie wersje na tych samych 3 produktach i porównaj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ServiceReturnsResultRecord {
        /*
         * 🧪 Zadanie 13:
         * Analogicznie do RegisterUserResult z lekcji, zdefiniuj własny record
         * PlaceOrderResult(boolean success, long orderId, String errorMessage) z
         * fabrykami ok(id)/failure(msg). OrderService.placeOrder ma go zwracać.
         * Controller (handlePlaceOrderRequest) tłumaczy go na String odpowiedzi
         * "201 Created" / "400 Bad Request - <komunikat>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DaoNeverThrowsBusinessException {
        /*
         * 🧪 Zadanie 14:
         * StudentDao (tabela students: id, index_number UNIQUE) z insert i
         * existsByIndexNumber - DAO rzuca WYŁĄCZNIE RuntimeException opakowujący
         * SQLException, NIGDY wyjątku biznesowego. StudentService.enroll(indexNumber)
         * rzuca własny IllegalArgumentException, gdy numer już istnieje - zademonstruj
         * różnicę między tymi dwoma typami błędów w main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleControllersSameService {
        /*
         * 🧪 Zadanie 15:
         * Mając jeden ProductService.registerProduct(name, price) (z Zadania 12),
         * napisz TRZY różne "controllery": handleFromRestApi, handleFromAdminPanel,
         * handleFromBatchImport - każdy przyjmuje inne dane wejściowe (np. String[]
         * dla importu wsadowego z kilku produktów) i tłumaczy je na wywołania Service.
         * Zademonstruj wszystkie trzy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ServiceValidatesBeforeCallingTwoDaos {
        /*
         * 🧪 Zadanie 16:
         * Tabele authors (id, name) i books (id, title, author_id). AuthorDao.insert
         * (zwraca id), BookDao.insert(title, authorId). BookService.publishBook(authorName,
         * title) - reguła: title nie może być krótszy niż 2 znaki - najpierw
         * zapisuje autora przez AuthorDao, potem książkę przez BookDao. Przetestuj
         * dla poprawnego i za krótkiego tytułu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ControllerHandlesExceptionFromService {
        /*
         * 🧪 Zadanie 17:
         * Rozbuduj handleRegisterUserRequest tak, by Controller łapał RuntimeException
         * rzucany przez Service (symulujący błąd SQL, np. przez zamknięcie connection
         * przed wywołaniem DAO) i tłumaczył go na "500 Internal Server Error" -
         * zamiast pozwolić wyjątkowi "wylecieć" z main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ServiceWithoutDaoForReadOnlyRule {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę statyczną (bez DAO, czysta logika biznesowa)
         * calculateDiscount(BigDecimal amount) - 10% rabatu dla kwot > 500, w
         * przeciwnym razie 0. Pokaż, że TA reguła w ogóle NIE wymaga bazy danych -
         * dopiero OrderService.placeOrderWithDiscount(dao, amount) łączy tę regułę
         * z zapisem do bazy przez DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LayerDependencyDirectionCheck {
        /*
         * 🧪 Zadanie 19:
         * Napisz UserDao, UserService i handleRegisterUserRequest z lekcji (lub
         * podobne), a następnie w komentarzu w main wypisz "mapę zależności":
         * które klasy mają POLE/PARAMETR wskazujący na inną klasę z tej trójki
         * (np. UserService ma pole UserDao). Zwróć uwagę, że UserDao NIE MA pola
         * typu UserService ani Controller - zależność jest jednostronna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReplaceDaoImplementationWithoutTouchingService {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj UserService przyjmujący w konstruktorze DOWOLNY obiekt z
         * metodami existsByEmail/insert (dwie różne klasy: UserJdbcDao na bazie H2
         * i UserInMemoryDao na HashMap, bez wspólnego interfejsu - użyj przeciążonych
         * konstruktorów UserService albo dwóch metod fabrykujących). Zademonstruj,
         * że registerUser daje IDENTYCZNY efekt niezależnie od implementacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullPipelineWithReport {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny pipeline 3-warstwowy (Dao/Service/Controller) dla
         * rejestracji użytkownika z regułami: format e-maila, min. 2 znaki imienia,
         * brak duplikatu. Uruchom dla listy 10 żądań (mieszanka poprawnych i
         * błędnych na różne sposoby) przez warstwę Controller i wypisz podsumowanie:
         * liczba 201/400 odpowiedzi z podziałem na powód błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ServiceOrchestratesThreeDaos {
        /*
         * 🧪 Zadanie 22:
         * Tabele customers (id, name), orders (id, customer_id, total), order_items
         * (id, order_id, product_name). Trzy osobne DAO. OrderService.placeFullOrder
         * (customerName, total, List<String> productNames) - sprawdza/tworzy klienta,
         * wstawia zamówienie, wstawia pozycje - WSZYSTKO delegowane do trzech DAO,
         * Service zawiera TYLKO kolejność wywołań i walidację (total > 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestServiceWithFakeDao {
        /*
         * 🧪 Zadanie 23:
         * Napisz UserService przyjmujący w konstruktorze obiekt "podobny do DAO"
         * (klasa FakeUserDao z HashMap w pamięci, bez bazy danych, ta sama sygnatura
         * metod co prawdziwy UserDao, ale bez wspólnego interfejsu - po prostu
         * napisz identyczne metody). Przetestuj CAŁĄ logikę biznesową UserService
         * BEZ uruchamiania żadnej bazy H2, korzystając z FakeUserDao.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MultiStepBusinessTransactionAcrossLayers {
        /*
         * 🧪 Zadanie 24:
         * Tabele accounts (id, owner, balance). AccountDao z findBalance(id) i
         * updateBalance(id, newBalance). AccountService.transfer(fromId, toId, amount)
         * - reguła: amount > 0 oraz saldo źródłowe musi wystarczyć - obie sprawdzane
         * w Service PRZED wywołaniem DAO. Controller (handleTransferRequest) tłumaczy
         * wynik na komunikat. Przetestuj przelew udany i nieudany (za mało środków).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LayeredArchitectureWithLoggingAtEachLayer {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj UserDao/UserService/Controller z lekcji tak, by KAŻDA warstwa
         * dodawała swój prefiks logu ("[Controller]", "[Service]", "[DAO]") przy
         * każdym wywołaniu - dokładnie jak w przykładzie z lekcji. Uruchom pełny
         * przepływ dla 3 żądań i wypisz kompletny log, żeby zobaczyć KOLEJNOŚĆ
         * przechodzenia żądania przez warstwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BatchImportThroughAllLayers {
        /*
         * 🧪 Zadanie 26:
         * Zaimportuj 20 użytkowników (część z duplikatem e-maila, część z błędnym
         * formatem) przez CAŁY łańcuch Controller->Service->Dao (handleImportBatch
         * przyjmujący List<String[]> z parami email/imię). Zbierz raport (3 liczniki:
         * sukcesy, duplikaty, błędny format) i wypisz go na końcu z procentowym
         * udziałem każdej kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ReplaceJdbcDaoWithHashMapDaoAtRuntime {
        /*
         * 🧪 Zadanie 27:
         * Napisz ProductService przyjmujący w konstruktorze DAO z metodami insert/
         * existsByName - zaimplementuj DWIE klasy: ProductJdbcDao (H2) i
         * ProductInMemoryDao (HashMap), bez wspólnego interfejsu Java (użyj dwóch
         * przeciążonych konstruktorów Service, każdy przyjmujący inny konkretny typ).
         * Uruchom TĘ SAMĄ metodę biznesową registerProduct na obu wariantach i
         * porównaj identyczność zachowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ControllerServiceDaoWithCustomExceptionHierarchy {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj hierarchię wyjątków: ServiceException (bazowy, niesprawdzany),
         * DuplicateEmailException i InvalidEmailFormatException (obie dziedziczą po
         * ServiceException). UserService.registerUser rzuca odpowiedni podtyp
         * zamiast zwracać record wyniku. Controller łapie ServiceException i
         * mapuje KAŻDY podtyp na inny komunikat "400 Bad Request - ...".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PerformanceComparisonAcrossLayerBoundaries {
        /*
         * 🧪 Zadanie 29:
         * Zmierz (System.nanoTime()) czas wykonania 500 wywołań rejestracji
         * użytkownika DWOMA sposobami: (a) bezpośrednio przez UserDao (bez Service,
         * bez walidacji), (b) przez cały łańcuch Controller->Service->Dao z pełną
         * walidacją. Wypisz oba czasy i skomentuj, jaki jest praktyczny koszt
         * dodania warstw (powinien być minimalny w porównaniu do kosztu SQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMiniApplicationThreeLayers {
        /*
         * 🧪 Zadanie 30:
         * Złóż mini-aplikację "rejestr klientów": tabela customers (id, email UNIQUE,
         * name). CustomerDao (insert, existsByEmail, findAll, count). CustomerService
         * (walidacja e-maila + imienia, sprawdzenie duplikatu). Trzy "controllery"
         * symulujące web/cli/import. Zasymuluj 8 żądań o różnych wynikach przez
         * WSZYSTKIE trzy controllery na raz i wypisz raport końcowy: liczba sukcesów,
         * odrzuceń z podziałem na powód, oraz finalny count() z CustomerDao.
         */
        public static void main(String[] args) { }
    }
}
