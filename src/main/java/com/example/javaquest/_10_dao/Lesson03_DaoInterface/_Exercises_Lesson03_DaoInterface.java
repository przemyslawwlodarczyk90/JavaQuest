package com.example.javaquest._10_dao.Lesson03_DaoInterface;

public class _Exercises_Lesson03_DaoInterface {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineProductDaoInterface {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj interfejs ProductDao z metodami: Optional<Product> findById(Long id),
         * List<Product> findAll(), Product save(Product product), void deleteById(Long id).
         * Product to record(Long id, String name, BigDecimal price). Wypisz sam
         * interfejs jako String w main (System.out.println z blokiem tekstowym),
         * bez żadnej implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_JdbcImplementationOfProductDao {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj interfejs ProductDao z Zadania 1 klasą ProductJdbcDao łączącą
         * się z bazą "jdbc:h2:mem:l03ex02;DB_CLOSE_DELAY=-1" i tabelą products (id, name,
         * price). Zapisz 2 produkty, wypisz findAll() i findById dla istniejącego
         * i nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_InMemoryImplementationOfProductDao {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj TEN SAM interfejs ProductDao klasą ProductInMemoryDao opartą
         * na HashMap<Long, Product> i AtomicLong jako generator id - BEZ jakiejkolwiek
         * bazy danych. Wykonaj identyczne operacje (save, findById, findAll) jak w
         * Zadaniu 2 i porównaj wyniki (powinny być analogiczne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MethodAcceptsInterfaceNotClass {
        /*
         * 🧪 Zadanie 4:
         * Napisz metodę printAllProducts(ProductDao dao) przyjmującą parametr TYPU
         * INTERFEJSU (nie ProductJdbcDao ani ProductInMemoryDao). Wywołaj ją DWA razy -
         * raz z ProductJdbcDao, raz z ProductInMemoryDao - i wypisz identyczny format
         * wyniku dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DefineSimpleTaskDaoInterface {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj interfejs TaskDao z metodami: Task save(Task task), List<Task>
         * findAll(), void markDone(Long id). Task to record(Long id, String title,
         * boolean done). Zaimplementuj JEDNĄ prostą wersję opartą na HashMap i
         * przetestuj wszystkie trzy metody na 3 zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_OptionalReturnTypeInInterface {
        /*
         * 🧪 Zadanie 6:
         * Do interfejsu UserDao (jak w lekcji) dodaj metodę Optional<User>
         * findByEmail(String email) i zaimplementuj ją w UserJdbcDao (tabela users:
         * id, email, first_name na bazie "jdbc:h2:mem:l03ex06;DB_CLOSE_DELAY=-1").
         * Sprawdź wynik dla istniejącego i nieistniejącego e-maila.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareTwoImplementationsSideBySide {
        /*
         * 🧪 Zadanie 7:
         * Uruchom demoUseUserDao (analogiczna do tej z lekcji, przyjmująca UserDao)
         * DWA razy pod rząd - raz z UserJdbcDao, raz z UserInMemoryDao - i wypisz
         * przed każdym uruchomieniem, jaka implementacja jest używana
         * (dao.getClass().getSimpleName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_InterfaceWithFiveCrudMethods {
        /*
         * 🧪 Zadanie 8:
         * Zdefiniuj interfejs BookDao z pięcioma metodami CRUD (jak UserDao w
         * lekcji: findById, findAll, save, update, deleteById) dla encji Book
         * (record Long id, String title, String author). Zaimplementuj JEDNĄ
         * wersję JDBC i przetestuj każdą z pięciu metod po kolei.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementationSpecificDetailHidden {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj UserDao dwiema klasami z RÓŻNYM sposobem generowania id:
         * UserJdbcDao (AUTO_INCREMENT bazy) i UserInMemoryDao (AtomicLong w Javie).
         * Wywołaj save() na obu i pokaż, że kod WOŁAJĄCY (przyjmujący UserDao) nie
         * musi wiedzieć, JAK dokładnie id zostało wygenerowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListMethodReturnsEmptyNotNull {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj UserDao.findAll() w UserInMemoryDao dla PUSTEJ mapy (bez
         * żadnego zapisanego użytkownika) i sprawdź, że wynik to pusta lista
         * (isEmpty() == true), NIE null. Zrób to samo dla UserJdbcDao na pustej
         * tabeli - porównaj, że obie implementacje zachowują się identycznie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DaoInterfaceForOneToManyRelation {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj interfejs OrderDao z metodą Order findByIdWithItems(Long id),
         * gdzie Order to record(Long id, String customerName, List<OrderItem> items).
         * Zaimplementuj JEDNĄ wersję JDBC (tabele orders, order_items) używając
         * dwóch osobnych zapytań (wzorzec ze wcześniejszych lekcji, tu jako
         * przygotowanie do Lesson08). Przetestuj na jednym zamówieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SwapImplementationInServiceConstructor {
        /*
         * 🧪 Zadanie 12:
         * Napisz UserService przyjmujący w konstruktorze UserDao (interfejs, nie
         * konkretna klasa). Utwórz DWIE instancje Service - jedną z UserJdbcDao,
         * jedną z UserInMemoryDao - i wywołaj IDENTYCZNĄ metodę biznesową
         * registerUser(email, name) na obu. Pokaż, że Service nie wie, z którą
         * implementacją rozmawia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestDoubleForUnitTestSimulation {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę testStyle testRegisterDuplicateEmail(UserDao dao) sprawdzającą,
         * że próba zapisania dwóch userów z tym samym e-mailem daje dwa różne id
         * (bo DAO na tym poziomie NIE sprawdza unikalności - to zadanie Service).
         * Uruchom tę metodę z UserInMemoryDao jako "test jednostkowy" BEZ
         * uruchamiania żadnej bazy danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_InterfaceSegregationTwoSmallInterfaces {
        /*
         * 🧪 Zadanie 14:
         * Zamiast jednego dużego UserDao, zdefiniuj DWA małe interfejsy:
         * UserReader (findById, findAll) i UserWriter (save, update, deleteById).
         * Zaimplementuj JEDNĄ klasę UserJdbcDao implementującą OBA interfejsy
         * naraz. Napisz metodę printAllUsers(UserReader reader) przyjmującą TYLKO
         * węższy interfejs.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_GenericDaoInterface {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj generyczny interfejs CrudDao<T, ID> z metodami Optional<T>
         * findById(ID id), List<T> findAll(), T save(T entity), void deleteById(ID id).
         * Zaimplementuj go DWA razy: UserJdbcDao implements CrudDao<User, Long> i
         * ProductJdbcDao implements CrudDao<Product, Long>. Przetestuj obie na
         * odpowiednich tabelach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MethodAcceptingGenericDaoInterface {
        /*
         * 🧪 Zadanie 16:
         * Używając CrudDao<T, ID> z Zadania 15, napisz generyczną metodę
         * <T, ID> void printCount(CrudDao<T, ID> dao) wypisującą findAll().size().
         * Wywołaj ją dla UserJdbcDao i ProductJdbcDao - jedna metoda obsługująca
         * DWA różne typy encji dzięki generyczności interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InMemoryDaoForFastTesting {
        /*
         * 🧪 Zadanie 17:
         * Napisz UserService.findActiveUsersCount(UserDao) zliczającą użytkowników,
         * których e-mail zawiera "@firma.pl" (logika w Service, nie w DAO - DAO ma
         * tylko findAll()). Przetestuj tę logikę wyłącznie na UserInMemoryDao
         * wypełnionym 5 przykładowymi użytkownikami, bez uruchamiania bazy H2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UpdateMethodBehaviorAcrossImplementations {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj UserDao.update(User) w OBU wariantach (Jdbc i InMemory).
         * W main zapisz użytkownika, zaktualizuj jego dane w KAŻDEJ implementacji
         * i sprawdź przez findById, że zmiana faktycznie zaszła - dla obu
         * implementacji w identyczny sposób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DeleteByIdIdempotency {
        /*
         * 🧪 Zadanie 19:
         * Sprawdź zachowanie deleteById(id) wywołanego DWA razy z rzędu dla tego
         * samego id (drugie wywołanie na już usuniętym wierszu) w OBU
         * implementacjach UserDao. Zweryfikuj, że druga próba usunięcia NIE rzuca
         * wyjątku w żadnej z implementacji (usuwanie nieistniejącego jest "bezpieczne").
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentContractWithJavadocStyleComments {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj interfejs ReviewDao (Review: record Long id, String productName,
         * int rating) z metodami save, findByProductName(String) -> List<Review>,
         * averageRating(String productName) -> double. Zaimplementuj JDBC wersję i
         * przetestuj wszystkie trzy metody na 4 recenzjach dla 2 różnych produktów.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FactoryChoosesImplementationAtRuntime {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę statyczną UserDao createUserDao(boolean useDatabase,
         * Connection connectionOrNull) zwracającą UserJdbcDao gdy useDatabase==true,
         * a UserInMemoryDao w przeciwnym razie. Zademonstruj wybór implementacji
         * "w locie" na podstawie flagi, bez zmiany kodu korzystającego z UserDao.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullCrudComparisonAcrossImplementations {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę runFullCrudScenario(UserDao dao) wykonującą PEŁNY scenariusz
         * (save 3 userów, findById, findAll, update jednego, deleteById innego,
         * findAll po usunięciu) i wypisującą wynik każdego kroku. Uruchom ją DWA
         * razy - z UserJdbcDao i UserInMemoryDao - i zweryfikuj identyczność
         * wyników (poza samym id, jeśli generowanie się różni).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_InterfaceWithDefaultMethod {
        /*
         * 🧪 Zadanie 23:
         * Do interfejsu UserDao dodaj DEFAULT metodę
         * default boolean existsById(Long id) { return findById(id).isPresent(); }
         * - zaimplementowaną RAZ w interfejsie, bez potrzeby powtarzania jej w
         * UserJdbcDao ani UserInMemoryDao. Zademonstruj jej działanie na obu
         * implementacjach (dziedziczą ją "za darmo").
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThrowingConsistentExceptionsAcrossImplementations {
        /*
         * 🧪 Zadanie 24:
         * Upewnij się, że OBIE implementacje UserDao (Jdbc i InMemory) rzucają TEN
         * SAM typ wyjątku (np. NoSuchElementException) przy update() na
         * nieistniejącym id - dla InMemory trzeba to dopisać jawnie (rzuć wyjątek,
         * jeśli storage.containsKey(id) == false), dla Jdbc sprawdź liczbę
         * zmienionych wierszy (executeUpdate() zwraca 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompositeDaoUsingTwoInterfaces {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj interfejsy UserDao i OrderDao (osobno). Napisz klasę
         * ReportService przyjmującą OBA interfejsy w konstruktorze i metodę
         * generateUserOrderReport(userId) łączącą wynik obu DAO w jeden String
         * raportu (np. imię użytkownika + liczba jego zamówień). Podaj JDBC
         * implementacje obu i przetestuj raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PerformanceComparisonJdbcVsInMemory {
        /*
         * 🧪 Zadanie 26:
         * Zmierz (System.nanoTime()) czas wykonania 1000 wywołań findById dla
         * istniejącego id na UserJdbcDao oraz na UserInMemoryDao (po wcześniejszym
         * wypełnieniu obu tymi samymi danymi). Wypisz porównanie czasów i skomentuj
         * w kodzie, dlaczego wersja w pamięci jest szybsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigratingDataBetweenImplementations {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę migrateData(UserDao source, UserDao target) kopiującą
         * WSZYSTKICH użytkowników z source.findAll() do target.save(...). Wykonaj
         * migrację z UserInMemoryDao (5 userów) do UserJdbcDao (pusta baza) i
         * zweryfikuj przez target.findAll(), że dane trafiły do bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ContractTestReusedForBothImplementations {
        /*
         * 🧪 Zadanie 28:
         * Napisz JEDNĄ metodę verifyDaoContract(UserDao dao) sprawdzającą (przez
         * proste asercje/porównania wypisywane w konsoli) podstawowe reguły
         * kontraktu: findById dla nieistniejącego id daje Optional.empty(),
         * findAll() na starcie nie jest null, save() zwraca obiekt z niepustym id.
         * Uruchom ją dla OBU implementacji i wypisz "kontrakt OK" dla każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DecoratingDaoWithLoggingWrapper {
        /*
         * 🧪 Zadanie 29:
         * Napisz klasę LoggingUserDao implements UserDao, która w konstruktorze
         * przyjmuje INNY UserDao (dowolną implementację) i DELEGUJE do niego każde
         * wywołanie, dodatkowo wypisując log przed i po każdej operacji (np.
         * "[LOG] wywolanie findById(3)"). Owiń nią UserInMemoryDao i zademonstruj
         * logowanie przy pełnym scenariuszu CRUD.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullApplicationSwappableStorage {
        /*
         * 🧪 Zadanie 30:
         * Złóż mini-aplikację zarządzania zadaniami: interfejs TaskDao (save,
         * findAll, markDone, deleteById) z DWIEMA implementacjami (Jdbc na tabeli
         * tasks, InMemory na HashMap). Napisz TaskService korzystający WYŁĄCZNIE
         * z interfejsu, obsługujący scenariusz: dodaj 5 zadań, oznacz 2 jako
         * wykonane, usuń 1, wypisz listę pozostałych. Uruchom CAŁY scenariusz
         * DWA razy - raz na każdej implementacji - i porównaj wyniki końcowe.
         */
        public static void main(String[] args) { }
    }
}
