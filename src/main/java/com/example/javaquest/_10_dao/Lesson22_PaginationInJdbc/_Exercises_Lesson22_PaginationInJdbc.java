package com.example.javaquest._10_dao.Lesson22_PaginationInJdbc;

public class _Exercises_Lesson22_PaginationInJdbc {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateProductsTableWithTwentyRows {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson22ex01;DB_CLOSE_DELAY=-1" utworz tabele products
         * (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100)) i wstaw 20
         * wierszy ("Produkt 1".."Produkt 20"). Wykonaj proste SELECT id, name FROM
         * products ORDER BY id LIMIT 5 OFFSET 0 i wypisz wynik (5 pierwszych
         * produktow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_LimitOffsetSecondPage {
        /*
         * 🧪 Zadanie 2:
         * Na tej samej tabeli, wykonaj SELECT z LIMIT 5 OFFSET 5 (druga "strona") -
         * wypisz produkty od 6 do 10. Sprawdz recznie, ze wyniki NIE powtarzaja sie z
         * pierwsza strona z Zadania 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PageRecordManualConstruction {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj record Page<T>(List<T> content, int page, int size, long
         * totalElements, int totalPages) (jak w lekcji). Recznie (bez metody DAO)
         * wykonaj zapytanie LIMIT/OFFSET dla strony 1 (size=5), osobne zapytanie
         * COUNT(*), i skonstruuj obiekt Page<String> (lista nazw produktow) z
         * poprawnie wyliczonym totalPages.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ProductDaoFindPageMethod {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj ProductDao.findPage(int page, int size) DOKLADNIE jak w
         * lekcji (LIMIT/OFFSET + osobne COUNT(*)). Wywolaj dla page=1, size=5 i
         * wypisz zawartosc strony oraz metadane (page, size, totalElements,
         * totalPages).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LastPartialPage {
        /*
         * 🧪 Zadanie 5:
         * Uzywajac ProductDao z Zadania 4 na tabeli z 23 produktami (jak w lekcji),
         * wywolaj findPage(5, 5) - ostatnia, NIEPELNA strona (powinna miec tylko 3
         * elementy: produkty 21, 22, 23). Wypisz zawartosc i sprawdz totalPages
         * (powinno wynosic 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PageBeyondLastReturnsEmpty {
        /*
         * 🧪 Zadanie 6:
         * Uzywajac tej samej tabeli, wywolaj findPage(6, 5) (strona ZA ostatnia
         * strona danych). Sprawdz, ze content jest PUSTA lista (nie null, nie
         * wyjatek), a totalElements i totalPages sa mimo to poprawnie wyliczone.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DifferentPageSizesSameData {
        /*
         * 🧪 Zadanie 7:
         * Na tej samej tabeli (23 produkty), wywolaj findPage(1, 10) oraz
         * findPage(3, 10) - wypisz oba wyniki i sprawdz, ze totalPages dla size=10
         * wynosi 3 (23/10 zaokraglone w gore).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_InvalidPageOrSizeThrowsException {
        /*
         * 🧪 Zadanie 8:
         * Sprawdz walidacje z lekcji: wywolaj findPage(0, 5) oraz findPage(1, 0) -
         * OBA powinny rzucic IllegalArgumentException (page i size musza byc >= 1).
         * Zlap wyjatki i wypisz komunikaty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_OrderByRequiredForStablePagination {
        /*
         * 🧪 Zadanie 9:
         * Wykonaj DWA identyczne zapytania LIMIT 5 OFFSET 5, jedno Z klauzula ORDER
         * BY id, jedno (do porownania - tylko jako demonstracja, nie jako "produkcyjny"
         * kod) BEZ ORDER BY. Wypisz oba wyniki i skomentuj, dlaczego wersja bez
         * ORDER BY jest RYZYKOWNA (kolejnosc wierszy nie jest gwarantowana przez
         * SQL bez sortowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PrintPageHelperMethod {
        /*
         * 🧪 Zadanie 10:
         * Napisz metode printPage(Page<Product> page) (jak w lekcji: "Strona X z Y
         * (rozmiar=..., wszystkich=...)"). Wywolaj dla 3 roznych stron (1, 3, 6 przy
         * size=5 na 23 produktach) i sprawdz, ze format wypisu jest konsekwentny.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PaginationOverUsersTableWithEmailColumn {
        /*
         * 🧪 Zadanie 11:
         * Na bazie "jdbc:h2:mem:lesson22ex11;DB_CLOSE_DELAY=-1" utworz tabele users
         * (id, name, email) z 37 wierszami. Napisz UserDao.findPage(page, size)
         * analogiczne do ProductDao, ale zwracajace Page<User> (record User(id,
         * name, email)). Zademonstruj strony 1, 4, 8 przy size=5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TotalPagesCalculationEdgeCases {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode statyczna int calculateTotalPages(long totalElements, int
         * size) (Math.ceil jak w lekcji) i przetestuj ja dla 5 przypadkow: 0
         * elementow, elementow=size (dokladnie 1 strona), elementow=size+1 (2
         * strony), elementow duzo wiecej niz size, oraz size=1. Wypisz wynik dla
         * kazdego przypadku i sprawdz recznie w komentarzu, czy jest poprawny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IteratingThroughAllPagesSequentially {
        /*
         * 🧪 Zadanie 13:
         * Uzywajac ProductDao.findPage, napisz metode printAllPages(dao, size)
         * iterujaca AUTOMATYCZNIE przez WSZYSTKIE strony (od 1 do totalPages,
         * odczytanego z pierwszej strony) i wypisujaca zawartosc kazdej. Zweryfikuj,
         * ze suma dlugosci content wszystkich stron rowna sie totalElements.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PaginationCombinedWithSimpleWhereFilter {
        /*
         * 🧪 Zadanie 14:
         * Dodaj do products kolumne category VARCHAR. Rozbuduj findPage o STALY
         * filtr WHERE category = 'ELEKTRONIKA' (dodany do zapytania LIMIT/OFFSET
         * ORAZ do zapytania COUNT(*) - obie musza uzywac TEGO SAMEGO warunku, inaczej
         * totalPages bedzie zle). Zweryfikuj to na danych z mieszanymi
         * kategoriami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ChangingPageSizeMidSessionSimulation {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj "uzytkownika UI", ktory najpierw przeglada strony przy size=5
         * (strony 1, 2), a potem ZMIENIA rozmiar strony na 20 (strona 1) - wypisz
         * wyniki obu "sesji" i sprawdz, ze totalPages sie zmienia odpowiednio do
         * nowego size.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CountQueryPerformanceNote {
        /*
         * 🧪 Zadanie 16:
         * Zmierz (System.nanoTime()) czas wykonania SAMEGO zapytania COUNT(*) na
         * tabeli z 1000 wierszami (wstaw je programowo w petli) w porownaniu do
         * czasu zapytania LIMIT/OFFSET dla jednej strony. Wypisz oba czasy i
         * skomentuj, ze COUNT(*) na duzych tabelach moze byc kosztowny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ZeroBasedVsOneBasedPageNumbering {
        /*
         * 🧪 Zadanie 17:
         * Napisz DRUGA wersje metody findPageZeroBased(page, size) uzywajaca
         * numeracji stron OD 0 (offset = page * size, bez odejmowania 1). Wywolaj OBIE
         * wersje (jednobazowa z lekcji i zerobazowa) dla "pierwszej strony" i
         * porownaj wyniki - powinny byc identyczne, mimo innej numeracji
         * wejsciowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_LastPageShortcut {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode findLastPage(dao, size) obliczajaca NAJPIERW totalPages
         * (np. przez findPage(1, size).totalPages()), a potem wywolujaca
         * findPage(totalPages, size) - zwracajaca OSTATNIA strone bez konieczności
         * znajomości jej numeru z wyprzedzeniem. Zademonstruj na tabeli z 23
         * produktami, size=7 (ostatnia strona ma 2 elementy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PaginationWithSortedByNameInsteadOfId {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj findPage o parametr boolean sortByName - jesli true, ORDER BY
         * name ASC, w przeciwnym razie ORDER BY id (jak dotychczas). WAZNE: to
         * dwie ROZNE, ale OBIE STALE, zakodowane wersje zapytania (bez konkatenacji
         * dynamicznej nazwy kolumny - patrz Lesson23 dla bezpiecznej wersji
         * dynamicznej). Zademonstruj obie wersje na tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ApiResponseShapeWithPageMetadata {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode String toApiResponseJson(Page<Product> page) recznie
         * budujaca prosty String w stylu JSON (bez biblioteki), np.
         * {"content":[...],"page":1,"size":5,"totalElements":23,"totalPages":5} -
         * zademonstruj na wyniku findPage(2, 5).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_KeysetPaginationAlternativeComparison {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj ALTERNATYWNY sposob paginacji - "keyset pagination": metoda
         * findPageAfterId(long lastSeenId, int size) uzywajaca WHERE id > ? ORDER BY
         * id LIMIT ? (bez OFFSET). Porownaj w komentarzu z LIMIT/OFFSET: dlaczego
         * keyset pagination NIE "przesuwa sie" nawet gdy w miedzyczasie ktos dodal
         * nowe wiersze na poczatku tabeli, a LIMIT/OFFSET moze pokazac duplikaty
         * lub pominac wiersze w takiej sytuacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PaginationConsistencyUnderConcurrentInserts {
        /*
         * 🧪 Zadanie 22:
         * Na bazie "jdbc:h2:mem:lesson22ex22;DB_CLOSE_DELAY=-1" z 20 produktami,
         * odczytaj strone 2 (size=5, id 6-10), potem WSTAW nowy produkt z id
         * mniejszym niz istniejace (np. recznie INSERT z konkretnym id=3, jesli
         * PK pozwala), a nastepnie odczytaj strone 2 PONOWNIE - sprawdz i
         * skomentuj, czy zawartosc sie zmienila (efekt "przesuwania sie" danych
         * miedzy stronami przy modyfikacjach danych w trakcie przegladania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CombinedPaginationWithMultipleOptionalFilters {
        /*
         * 🧪 Zadanie 23:
         * Rozbuduj products o kolumny category i price. Napisz findPage(page, size,
         * String category, BigDecimal maxPrice) gdzie category i maxPrice moga byc
         * null (analogicznie do wzorca z Lesson24_DynamicFiltering - budowanie WHERE
         * dynamicznie, ale WARTOSCI zawsze przez PreparedStatement). WAZNE: warunek
         * WHERE musi byc IDENTYCZNY w zapytaniu LIMIT/OFFSET i w zapytaniu COUNT(*).
         * Zademonstruj na 3 kombinacjach filtrow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CachingTotalCountForRepeatedPageRequests {
        /*
         * 🧪 Zadanie 24:
         * Napisz klase CachedPageProvider owijajaca ProductDao - metoda
         * findPageCached(page, size) wykonuje zapytanie COUNT(*) TYLKO PRZY
         * PIERWSZYM wywolaniu (potem uzywa zapamietanej wartosci przez kolejne
         * wywolania findPageCached dla tego samego rozmiaru danych), a zapytanie
         * LIMIT/OFFSET wykonuje ZAWSZE na nowo. Zademonstruj na 3 kolejnych
         * wywolaniach z rosnacym numerem strony i pomiarem czasu (COUNT powinien
         * wykonac sie tylko raz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PaginationPerformanceLargeOffsetProblem {
        /*
         * 🧪 Zadanie 25:
         * Wstaw 5000 wierszy do products. Zmierz czas wykonania findPage(1, 10)
         * (mala strona na poczatku) w porownaniu do findPage(490, 10) (strona daleko
         * w tabeli, wielki OFFSET). Wypisz oba czasy i skomentuj (w oparciu o
         * wynik), ze duzy OFFSET bywa kosztowny (baza musi "przewinac" i odrzucic
         * wszystkie wczesniejsze wiersze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PageDtoWithNavigationLinks {
        /*
         * 🧪 Zadanie 26:
         * Rozbuduj record Page<T> o dodatkowe pola boolean hasNext, boolean
         * hasPrevious (wyliczane na podstawie page i totalPages). Zaimplementuj
         * konstrukcje takiego rozszerzonego Page w findPage i zademonstruj na
         * pierwszej, srodkowej i ostatniej stronie - wypisz wartosci hasNext/
         * hasPrevious dla kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PaginationTestSuiteWithMiniRunner {
        /*
         * 🧪 Zadanie 27:
         * Napisz mini test-runner (jak w Lesson26_TestingDao) z co najmniej 6 testami
         * dla ProductDao.findPage: pierwsza strona ma poprawna liczbe elementow,
         * ostatnia niepelna strona ma poprawna liczbe, strona poza zakresem jest
         * pusta, totalPages jest poprawnie wyliczone dla kilku rozmiarow, page=0
         * rzuca wyjatek, size=0 rzuca wyjatek. Wypisz PASSED/FAILED dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_InfiniteScrollSimulationWithKeysetPagination {
        /*
         * 🧪 Zadanie 28:
         * Uzywajac keyset pagination z Zadania 21, zasymuluj "infinite scroll" -
         * napisz petle, ktora automatycznie pobiera KOLEJNE "strony" po 5 elementow
         * (uzywajac id ostatniego elementu z poprzedniej partii), az do momentu, gdy
         * zwrocona lista jest pusta. Wypisz liczbe wykonanych "doladowan" i sume
         * wszystkich pobranych elementow (powinna byc rowna liczbie wierszy w
         * tabeli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiTablePaginationWithJoinAndSharedCount {
        /*
         * 🧪 Zadanie 29:
         * Dodaj tabele orders (id, product_id, quantity). Napisz metode
         * findOrderPageWithProductName(page, size) laczaca orders i products
         * przez JOIN (zapytanie LIMIT/OFFSET z JOIN) oraz OSOBNE zapytanie COUNT(*)
         * z TYM SAMYM JOIN (a nie samym COUNT(*) FROM orders - jesli JOIN moze
         * "zwezic" wynik, np. INNER JOIN z brakujacymi referencjami). Zademonstruj
         * na danych z 15 zamowieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullPaginationApiLayerMiniApp {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletna "mini-usluge" listowania produktow: DAO z findPage
         * (Zadanie 4), opcjonalnym filtrowaniem (Zadanie 23), keyset pagination jako
         * alternatywa (Zadanie 21), rozszerzonym Page z linkami nawigacji (Zadanie
         * 26) i konwersja do formatu odpowiedzi (Zadanie 20). Zasymuluj sesje
         * "uzytkownika UI" przegladajacego 4 kolejne strony z roznym filtrem na
         * srodku sesji i wypisz kompletny log jego dzialan (strona, filtr, liczba
         * wynikow, hasNext/hasPrevious).
         */
        public static void main(String[] args) { }
    }
}
