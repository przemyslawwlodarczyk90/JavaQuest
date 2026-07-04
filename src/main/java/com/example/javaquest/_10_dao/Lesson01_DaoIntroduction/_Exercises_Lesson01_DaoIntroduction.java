package com.example.javaquest._10_dao.Lesson01_DaoIntroduction;

public class _Exercises_Lesson01_DaoIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateProductsTable {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z bazą H2 w pamięci (np. "jdbc:h2:mem:ex01dao;DB_CLOSE_DELAY=-1").
         * Utwórz tabelę products (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(150),
         * price DECIMAL(10,2)). Wstaw jeden wiersz zwykłym Statement.executeUpdate(),
         * a następnie odczytaj go przez SELECT i wypisz wszystkie kolumny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SimpleBookDaoInsert {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę SimpleBookDao (pole Connection w konstruktorze) z jedną metodą
         * void insert(String title, String author) wykonującą INSERT INTO books (title, author)
         * VALUES (?, ?) przez PreparedStatement. Użyj jej do dodania dwóch książek,
         * a potem wypisz zawartość tabeli books zwykłym zapytaniem SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExistsByTitleMethod {
        /*
         * 🧪 Zadanie 3:
         * Rozbuduj SimpleBookDao z Zadania 2 o metodę boolean existsByTitle(String title)
         * (SELECT COUNT(*) FROM books WHERE title = ?). W main sprawdź istnienie tytułu
         * PRZED wywołaniem insert - jeśli tytuł już istnieje, wypisz komunikat i pomiń insert.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MixedBadPractice {
        /*
         * 🧪 Zadanie 4:
         * Napisz metodę registerProductBadWay(Connection connection, String name, BigDecimal price)
         * mieszającą w JEDNEJ metodzie: walidację (price musi być > 0), SQL sprawdzający
         * duplikat nazwy (SELECT COUNT(*)) i SQL wstawiający nowy wiersz (INSERT) do tabeli
         * products. Wywołaj ją dla 3 produktów: poprawnego, z ujemną ceną i z powtórzoną nazwą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RefactorToDao {
        /*
         * 🧪 Zadanie 5:
         * Zrefaktoryzuj kod z Zadania 4: wydziel klasę SimpleProductDao z metodami
         * existsByName(String) i insert(String, BigDecimal) - SAM SQL, bez walidacji.
         * Napisz osobną metodę registerProductGoodWay(SimpleProductDao dao, String name,
         * BigDecimal price) zawierającą TYLKO logikę walidacji i wywołania DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SimpleMovieDaoCount {
        /*
         * 🧪 Zadanie 6:
         * Stwórz tabelę movies (id, title, year) i klasę SimpleMovieDao z metodami
         * insert(String title, int year) oraz long count() (SELECT COUNT(*) FROM movies).
         * Dodaj 4 filmy i wypisz wynik count() przed i po dodaniu piątego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SimpleEmployeeDaoFindAllPrint {
        /*
         * 🧪 Zadanie 7:
         * Stwórz tabelę employees (id, first_name, department) i klasę SimpleEmployeeDao
         * z metodami insert(String firstName, String department) oraz void printAll()
         * wypisującą KAŻDEGO pracownika bezpośrednio przez System.out.println wewnątrz
         * metody (bez zwracania listy). Dodaj 3 pracowników z różnych działów i wywołaj printAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ValidateBeforeInsert {
        /*
         * 🧪 Zadanie 8:
         * Napisz osobną metodę boolean isValidPostalCode(String code) sprawdzającą, czy
         * kod ma dokładnie 6 znaków w formacie "NN-NNN" (dwie cyfry, myślnik, trzy cyfry).
         * Stwórz prosty CustomerDao z metodą insert(String name, String postalCode) do
         * tabeli customers i wywołaj insert TYLKO dla kodów, które przejdą walidację
         * (przetestuj na liście 5 kodów, część poprawnych, część niepoprawnych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DuplicateCheckDao {
        /*
         * 🧪 Zadanie 9:
         * Stwórz SimpleCustomerDao z tabelą customers (id, email UNIQUE) i metodami
         * existsByEmail(String) oraz insert(String email). W main zarejestruj 4 e-maile,
         * z czego jeden powtórzony - wypisz komunikat "odrzucono duplikat" dla powtórzonego
         * i zweryfikuj przez SELECT COUNT(*), że w tabeli są dokładnie 3 unikalne rekordy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareBadVsGoodLineCount {
        /*
         * 🧪 Zadanie 10:
         * Dla rejestracji studenta (imię + unikalny numer indeksu, np. "123456") napisz
         * DWIE wersje: registerStudentBadWay (walidacja formatu numeru indeksu - dokładnie
         * 6 cyfr - wymieszana z SQL w jednej metodzie) oraz registerStudentGoodWay
         * (walidacja osobno + StudentDao osobno). Uruchom obie dla tych samych 3 danych
         * wejściowych i porównaj w komentarzu czytelność obu podejść.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoTablesTwoDaos {
        /*
         * 🧪 Zadanie 11:
         * Stwórz dwie tabele: authors (id, name) i books (id, title, author_id) oraz
         * dwie osobne klasy: SimpleAuthorDao (insert zwracający wygenerowane id) i
         * SimpleBookDao (insert przyjmujący author_id). Wstaw jednego autora i dwie
         * jego książki, a następnie wypisz zawartość obu tabel osobnymi zapytaniami SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BusinessRuleUsesDao {
        /*
         * 🧪 Zadanie 12:
         * Stwórz tabelę loans (id, user_id) i SimpleLoanDao z metodą
         * long countByUser(long userId). Napisz metodę biznesową
         * boolean canBorrowBook(SimpleLoanDao dao, long userId) zwracającą true tylko
         * gdy countByUser(userId) < 3. Przetestuj dla użytkownika z 2 wypożyczeniami
         * (powinien móc) i z 3 wypożyczeniami (nie powinien).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RejectAndLogInvalid {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj registerUserGoodWay z dwiema regułami biznesowymi: e-mail musi
         * zawierać "@", a imię nie może być puste. Metoda ma zwracać boolean sukcesu
         * i wypisywać osobny komunikat odrzucenia dla KAŻDEJ z dwóch reguł. Przetestuj
         * na 4 przypadkach: poprawny, zły e-mail, puste imię, oba błędy naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DaoWithMultipleQueries {
        /*
         * 🧪 Zadanie 14:
         * Stwórz SimpleOrderDao z metodami insert(String status), boolean existsById(long id)
         * i long count(). W main wstaw 3 zamówienia, sprawdź existsById dla poprawnego
         * i dla nieistniejącego id (np. 999), a na końcu wypisz count().
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SeparateValidationClass {
        /*
         * 🧪 Zadanie 15:
         * Wydziel osobną klasę EmailValidator ze statyczną metodą boolean isValid(String email)
         * (sprawdza obecność "@" oraz kropki po "@"). Napisz metodę biznesową rejestracji
         * korzystającą WYŁĄCZNIE z EmailValidator.isValid i prostego DAO - DAO nie może
         * znać ani jednej linijki reguły walidacji e-maila.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BadWayRefactorChallenge {
        /*
         * 🧪 Zadanie 16:
         * Napisz najpierw "złą" metodę placeOrderBadWay mieszającą regułę biznesową
         * (rabat 10% gdy kwota zamówienia > 500) z INSERT do tabeli orders (kolumny
         * amount, final_amount). Następnie zrefaktoryzuj: wydziel OrderDao z metodą
         * insert(BigDecimal amount, BigDecimal finalAmount) i osobną metodę
         * calculateFinalAmount(BigDecimal amount) zawierającą tylko regułę rabatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultipleBusinessRulesOneDao {
        /*
         * 🧪 Zadanie 17:
         * Dla jednego SimpleProductDao (insert do tabeli products) zaimplementuj DWIE
         * reguły biznesowe sprawdzane PRZED wywołaniem insert: cena musi być > 0 oraz
         * nazwa nie może być pusta ani dłuższa niż 100 znaków. Przetestuj na 4 produktach
         * naruszających różne kombinacje reguł.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CountBeforeAndAfter {
        /*
         * 🧪 Zadanie 18:
         * Stwórz SimpleTaskDao z metodami insert(String title) i long count(). Wypisz
         * count() na starcie (powinno być 0), wstaw 5 zadań przez pętlę wywołań insert(),
         * wypisz count() po każdym wstawieniu, żeby prześledzić narastanie liczby rekordów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExistsByEmailAndInsertSequence {
        /*
         * 🧪 Zadanie 19:
         * Dla listy 5 adresów e-mail (2 z nich to duplikaty już istniejących w bazie)
         * wykonaj dla każdego: sprawdź existsByEmail, jeśli nie istnieje - wstaw i wypisz
         * "Zarejestrowano: <email>", jeśli istnieje - wypisz "Pominięto duplikat: <email>".
         * Na końcu wypisz łączną liczbę zarejestrowanych i pominiętych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DaoUsedByTwoDifferentCallers {
        /*
         * 🧪 Zadanie 20:
         * Stwórz jedno SimpleUserDao (insert, existsByEmail) i DWIE różne metody
         * "wywołujące" symulujące dwa różne miejsca w aplikacji: registerFromWebForm(dao, ...)
         * i registerFromImportFile(dao, ...) - obie korzystają z tego samego DAO, ale
         * każda wypisuje inny prefiks logów (np. "[WEB]" / "[IMPORT]"). Wywołaj obie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullRegistrationPipeline {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj pełny pipeline rejestracji użytkownika: walidacja formatu e-maila,
         * walidacja długości imienia (min. 2 znaki), sprawdzenie duplikatu przez DAO,
         * insert. Uruchom dla listy 10 rejestracji (mieszanka poprawnych i błędnych
         * na różne sposoby) i wypisz podsumowanie: liczba sukcesów i liczba odrzuceń
         * z podziałem na powód odrzucenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DaoIndependentFromValidationTest {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę testującą (bez żadnej bazy danych, bez DAO) samą walidację
         * e-maila na liście 8 przykładowych Stringów, wypisując wynik dla każdego.
         * W komentarzu wyjaśnij, dlaczego ta metoda testowa NIE wymaga uruchomienia
         * bazy H2 - to właśnie zaleta oddzielenia logiki biznesowej od DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SwapDaoImplementationMentally {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj dwie wersje SimpleCounterDao z metodą increment() i int value():
         * jedną opartą o tabelę H2 (kolumna counter_value w jednowierszowej tabeli),
         * drugą opartą o zwykłe pole int w pamięci. Napisz metodę incrementFiveTimes(dao)
         * przyjmującą "dowolną" z tych dwóch klas (bez wspólnego interfejsu - użyj
         * przeciążenia albo dwóch wywołań) i pokaż identyczny efekt końcowy (value()==5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BatchRegistrationWithReport {
        /*
         * 🧪 Zadanie 24:
         * Zarejestruj 20 użytkowników (część z duplikowanym e-mailem, część z niepoprawnym
         * formatem e-maila) przez DAO + logikę biznesową z tej lekcji. Zbierz do trzech
         * osobnych liczników: sukcesy, odrzucone duplikaty, odrzucone złym formatem.
         * Na końcu wypisz raport z trzema liczbami i procentowym udziałem każdej kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TransactionalFeel {
        /*
         * 🧪 Zadanie 25:
         * Stwórz dwie tabele orders (id, total) i order_items (id, order_id, product_name).
         * Zaimplementuj dwie osobne klasy DAO: OrderDao (insert zwracający wygenerowane id)
         * i OrderItemDao (insert przyjmujący order_id). Napisz metodę biznesową placeOrder
         * łączącą obie klasy - najpierw wstawia zamówienie, potem jego pozycje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiRuleValidationChain {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj 3 osobne metody walidujące hasło: boolean hasMinLength(String, int),
         * boolean hasDigit(String), boolean hasUpperCase(String). Połącz je w jedną metodę
         * registerAccount(dao, username, password) wywołującą DAO.insert TYLKO gdy WSZYSTKIE
         * trzy reguły są spełnione, w przeciwnym razie wypisz, która reguła zawiodła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DaoErrorSimulation {
        /*
         * 🧪 Zadanie 27:
         * Stwórz tabelę accounts z kolumną username VARCHAR UNIQUE. Zaimplementuj
         * SimpleAccountDao.insert(username) BEZ wcześniejszego sprawdzania duplikatu
         * (celowo). Wywołaj insert dwukrotnie z tym samym username i przechwyć
         * SQLException wynikający z naruszenia UNIQUE constraint w H2 - w metodzie
         * biznesowej opakuj tę sytuację i zwróć czytelny komunikat błędu zamiast
         * pozwolić wyjątkowi "wylecieć" z main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ReadOnlyReportDao {
        /*
         * 🧪 Zadanie 28:
         * Stwórz tabelę sales (id, amount) z kilkoma wierszami. Zaimplementuj
         * SimpleSalesDao z metodami BigDecimal sumRevenue() (SELECT SUM(amount))
         * i long countOrders() (SELECT COUNT(*)). Napisz osobną metodę biznesową
         * generateReport(dao) łączącą oba wywołania w jeden sformatowany String
         * (np. "Liczba zamówień: 5, suma przychodu: 1234.50").
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareTwoApproachesPerformanceNote {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj dwie metody sprawdzające istnienie e-maila: existsByEmailCount
         * (SELECT COUNT(*) ... WHERE email = ?) oraz existsByEmailExists
         * (SELECT 1 FROM users WHERE email = ? LIMIT 1, sprawdzenie rs.next()).
         * Zmierz czas wykonania 1000 wywołań każdej z metod (System.nanoTime()) dla
         * tego samego e-maila i wypisz porównanie czasów wraz z komentarzem, która
         * wersja teoretycznie skaluje się lepiej na dużych tabelach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniAppSimulation {
        /*
         * 🧪 Zadanie 30:
         * Złóż całość: tabela accounts (id, username UNIQUE, password). SimpleAccountDao
         * z metodami existsByUsername, insert, count. Logika biznesowa registerAccount
         * z dwiema regułami (login min. 3 znaki, hasło min. 6 znaków) plus sprawdzeniem
         * duplikatu przez DAO. W main zasymuluj 5 prób rejestracji o różnych wynikach
         * (sukces, duplikat, zły login, złe hasło, oba błędy), wypisz raport końcowy
         * z liczbą sukcesów/odrzuceń oraz finalny wynik count().
         */
        public static void main(String[] args) { }
    }
}
