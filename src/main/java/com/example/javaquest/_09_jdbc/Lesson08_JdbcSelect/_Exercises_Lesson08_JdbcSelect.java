package com.example.javaquest._09_jdbc.Lesson08_JdbcSelect;

public class _Exercises_Lesson08_JdbcSelect {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FindByIdExisting {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l08ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "users" (id, name, email) z 3 wierszami. Napisz metodę
         * Optional<User> findById(Connection, int id) (record User(int
         * id, String name, String email)) i wywołaj ją dla istniejącego
         * id, wypisując wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FindByIdMissingReturnsEmptyOptional {
        /*
         * 🧪 Zadanie 2:
         * Używając metody findById z Zadania 1, wywołaj ją dla id, które
         * NIE istnieje w tabeli. Wypisz found.isPresent() (powinno być
         * false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FindAllBasicList {
        /*
         * 🧪 Zadanie 3:
         * Napisz metodę List<User> findAll(Connection) zwracającą
         * WSZYSTKICH użytkowników z tabeli "users" (co najmniej 4
         * wiersze). Wywołaj ją i wypisz każdy element listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FindAllOnEmptyTable {
        /*
         * 🧪 Zadanie 4:
         * Utwórz PUSTĄ tabelę "users" (bez żadnych wierszy) i wywołaj na
         * niej metodę findAll z Zadania 3. Wypisz rozmiar zwróconej
         * listy (powinien być 0) oraz result.isEmpty().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MapRowReusedMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz JEDNĄ metodę mapRow(ResultSet) zwracającą User, a potem
         * użyj jej wewnątrz BOTH findById i findAll (bez powtarzania
         * kodu mapującego getInt/getString). Zademonstruj, że obie
         * metody dają poprawny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FindByEmailReturnsOptional {
        /*
         * 🧪 Zadanie 6:
         * Napisz metodę Optional<User> findByEmail(Connection, String
         * email) analogiczną do findById, ale wyszukującą po adresie
         * e-mail. Przetestuj dla istniejącego i nieistniejącego adresu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_OptionalOrElseUsage {
        /*
         * 🧪 Zadanie 7:
         * Wywołaj findById dla nieistniejącego id i użyj
         * found.orElse(null) oraz found.orElseGet(() -> tworzenie
         * "domyślnego" User o id=0) - wypisz oba warianty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_OptionalIsPresentAndGet {
        /*
         * 🧪 Zadanie 8:
         * Wywołaj findById dla istniejącego id. Sprawdź found.isPresent()
         * przed wywołaniem found.get() - wypisz oba wyniki, tłumacząc,
         * dlaczego zawsze warto sprawdzić isPresent() przed get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CountAllUsersMethod {
        /*
         * 🧪 Zadanie 9:
         * Napisz metodę int countAll(Connection) wykonującą "SELECT
         * COUNT(*) FROM users" i zwracającą wynik jako int. Porównaj
         * (println) z rozmiarem listy zwróconej przez findAll().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FindAllOrderedByName {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę List<User> findAllOrderedByName(Connection),
         * analogiczną do findAll, ale z "ORDER BY name" w zapytaniu SQL.
         * Wypisz wynik i sprawdź, że lista jest alfabetycznie
         * uporządkowana.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FindByNameLikePattern {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę List<User> findByNamePattern(Connection, String
         * pattern) wykonującą "WHERE name LIKE ?" z parametrem (np.
         * "%kow%"). Przetestuj na tabeli z co najmniej 5 różnymi
         * nazwiskami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FindFirstMatchingFromFilteredList {
        /*
         * 🧪 Zadanie 12:
         * Wywołaj findAll(), a potem w Javie (Stream API) znajdź
         * PIERWSZEGO użytkownika, którego email zawiera konkretną domenę
         * (np. "@example.com"), zwracając Optional<User>. Porównaj z
         * podejściem "filtrowania w SQL" (osobne zapytanie WHERE email
         * LIKE) - wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FindAllAsMapById {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę Map<Integer, User> findAllAsMap(Connection),
         * która wykonuje findAll() i konwertuje wynik na mapę id -> User
         * (Collectors.toMap albo pętla). Wypisz mapę i pokaż odczyt po
         * konkretnym kluczu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExistsByIdMethod {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę boolean existsById(Connection, int id) opartą na
         * findById(id).isPresent(). Przetestuj dla istniejącego i
         * nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_PaginateWithLimitOffset {
        /*
         * 🧪 Zadanie 15:
         * Na tabeli "users" z co najmniej 10 wierszami napisz metodę
         * List<User> findPage(Connection, int limit, int offset)
         * wykonującą "SELECT ... ORDER BY id LIMIT ? OFFSET ?".
         * Przetestuj dla dwóch różnych "stron" (np. limit=3 offset=0
         * oraz limit=3 offset=3) i wypisz obie strony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FindByIdOrThrowCustomException {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj własny niesprawdzany wyjątek
         * UserNotFoundException(int id). Napisz metodę User
         * findByIdOrThrow(Connection, int id), która woła findById i
         * rzuca UserNotFoundException, jeśli Optional jest pusty.
         * Przetestuj dla istniejącego (sukces) i nieistniejącego id
         * (złap wyjątek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FindManyByIdsInClause {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę List<User> findByIds(Connection, List<Integer>
         * ids), która DYNAMICZNIE buduje SQL z odpowiednią liczbą
         * placeholderów "?" dla klauzuli "WHERE id IN (...)" (na
         * podstawie rozmiaru listy ids) i wiąże wszystkie parametry.
         * Przetestuj dla listy 3 konkretnych id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SortInJavaVsSortInSql {
        /*
         * 🧪 Zadanie 18:
         * Wywołaj findAll() (bez ORDER BY w SQL) i posortuj wynik W
         * JAVIE (Comparator po nazwie). Porównaj z findAllOrderedByName
         * (sortowanie w SQL) z Zadania 10 - wypisz obie listy i
         * potwierdź, że dają ten sam porządek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IsEmptyResultCheck {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę boolean noUsersFound(Connection) sprawdzającą
         * findAll(connection).isEmpty(). Przetestuj na tabeli z danymi
         * (false) i na osobnej, pustej tabeli (true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareSqlFilterVsJavaStreamFilter {
        /*
         * 🧪 Zadanie 20:
         * Na tabeli z 10 wierszami porównaj DWA podejścia do znalezienia
         * użytkowników, których nazwisko zaczyna się na "A": (a) SQL
         * "WHERE name LIKE 'A%'", (b) findAll() + Stream.filter() w
         * Javie. Zmierz czas obu podejść i wypisz, które okazało się
         * szybsze (dla małej tabeli różnica może być minimalna -
         * skomentuj, że dla dużych tabel filtrowanie w SQL jest zwykle
         * korzystniejsze, bo nie przesyła nadmiarowych danych).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericRepositoryInterfacePattern {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj generyczny interfejs Repository<T> z metodami
         * Optional<T> findById(int id) i List<T> findAll(). Zaimplementuj
         * go jako UserRepository (przechowujący Connection w konstruktorze).
         * Zademonstruj użycie obu metod interfejsu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FindPagePaginationMethod {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz Zadanie 15: napisz metodę pomocniczą printAllPages(Connection,
         * int pageSize), która w pętli wywołuje findPage() ze wzrastającym
         * offsetem, aż zwrócona lista będzie pusta, wypisując zawartość
         * każdej "strony" po kolei (dla tabeli z co najmniej 11 wierszami
         * i pageSize=4 - powinno być 3 strony, ostatnia niepełna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MultiColumnSearchLikeQuery {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę List<User> search(Connection, String term)
         * wyszukującą term jednocześnie w KOLUMNACH name ORAZ email
         * (WHERE name LIKE ? OR email LIKE ?, z tym samym wzorcem "%" +
         * term + "%" dla obu). Przetestuj dla terminu, który trafia w
         * name jednego użytkownika i w email innego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DynamicOptionalFiltersSelect {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę List<User> findFiltered(Connection, String
         * nameContainsOrNull, String emailDomainOrNull), która buduje SQL
         * z ZMIENNĄ liczbą warunków WHERE (AND) w zależności od tego,
         * które filtry są różne od null, wiążąc odpowiednie parametry w
         * odpowiedniej kolejności. Przetestuj dla 3 różnych kombinacji
         * (tylko name, tylko email, oba, żaden).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureFindAllOnLargeDataset {
        /*
         * 🧪 Zadanie 25:
         * Wstaw 2000 wierszy do tabeli "users" (w pętli, dane generowane
         * programowo). Zmierz czas wykonania findAll() (odczyt WSZYSTKICH
         * 2000 wierszy do listy) i wypisz go w ms, komentując, że
         * odczyt "wszystkiego naraz" ma swój koszt pamięciowy przy
         * bardzo dużych tabelach (stąd sens paginacji z Zadania 15/22).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FindTopNOrdered {
        /*
         * 🧪 Zadanie 26:
         * Na tabeli "users" (id, name, email) dodaj kolumnę
         * registered_at TIMESTAMP z różnymi datami. Napisz metodę
         * List<User> findTopNMostRecent(Connection, int n) zwracającą n
         * najnowiej zarejestrowanych użytkowników (ORDER BY
         * registered_at DESC LIMIT ?). Przetestuj dla n=3 na tabeli z 8
         * wierszami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CombinedListAndAggregateReport {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę generującą raport tekstowy: pełną listę
         * findAll(), łączną liczbę użytkowników (countAll()), oraz
         * (obliczoną w Javie po liście) liczbę unikalnych domen e-mail
         * (część adresu po "@"). Wypisz sformatowany raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_UserRepositoryFacadeClass {
        /*
         * 🧪 Zadanie 28:
         * Napisz klasę UserRepository grupującą WSZYSTKIE metody z tej
         * lekcji (findById, findAll, findByEmail, countAll,
         * existsById, findByNamePattern) jako metody instancyjne
         * (Connection przechowywane w polu). Zademonstruj wywołanie co
         * najmniej 4 z nich na jednym obiekcie repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ExistsByEmailUsingCountQuery {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę boolean existsByEmail(Connection, String email)
         * wykorzystującą "SELECT COUNT(*) FROM users WHERE email = ?" (a
         * NIE findByEmail().isPresent(), dla porównania podejść -
         * COUNT(*) nie wymaga pobierania całego wiersza). Przetestuj dla
         * istniejącego i nieistniejącego adresu, komentując różnicę
         * wydajnościową względem podejścia z Zadania 6/14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneUserServiceDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klasę UserService korzystającą z UserRepository
         * (Zadanie 28): metody registerDemoUsers() (wstawia 12
         * przykładowych użytkowników), listAllFormatted() (wypisuje
         * wszystkich), findUserOrReport(id) (wypisuje użytkownika albo
         * komunikat "nie znaleziono"), searchByTerm(term) i
         * paginatedReport(pageSize). Wywołaj wszystkie metody po kolei,
         * demonstrując pełny przekrój możliwości SELECT-a poznanych w
         * tej lekcji.
         */
        public static void main(String[] args) { }
    }
}
