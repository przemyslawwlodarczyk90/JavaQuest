package com.example.javaquest._10_dao.Lesson24_DynamicFiltering;

public class _Exercises_Lesson24_DynamicFiltering {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SetupOrdersTableWithSixRows {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson24ex01;DB_CLOSE_DELAY=-1" utworz tabele orders
         * (id, status, order_date, amount) i wstaw 6 wierszy jak w lekcji (rozne
         * statusy, daty, kwoty). Wykonaj proste "SELECT * FROM orders WHERE 1 = 1"
         * (bez zadnego dodatkowego warunku) i wypisz wszystkie wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SearchWithOnlyStatusFilter {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj OrderDao.search(status, fromDate, minAmount, maxAmount)
         * DOKLADNIE jak w lekcji (StringBuilder + List<Object> params). Wywolaj
         * search("PAID", null, null, null) - wypisz wynik i sprawdz recznie, ze
         * zwrocone sa TYLKO zamowienia ze statusem PAID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SearchWithNoFiltersReturnsAll {
        /*
         * 🧪 Zadanie 3:
         * Wywolaj search(null, null, null, null) (wszystkie parametry null) - sprawdz,
         * ze zwrocone sa WSZYSTKIE 6 zamowien, a wygenerowany SQL zawiera TYLKO
         * "WHERE 1 = 1 ORDER BY id" (bez zadnego dodatkowego AND).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SearchWithOnlyDateFilter {
        /*
         * 🧪 Zadanie 4:
         * Wywolaj search(null, LocalDate.of(2024, 2, 1), null, null) - wypisz wynik i
         * sprawdz, ze zwrocone sa TYLKO zamowienia z order_date >= 2024-02-01.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SearchWithAmountRangeOnly {
        /*
         * 🧪 Zadanie 5:
         * Wywolaj search(null, null, new BigDecimal("100.00"), new
         * BigDecimal("300.00")) - wypisz wynik i sprawdz, ze zwrocone sa TYLKO
         * zamowienia z amount w zakresie [100, 300].
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SearchWithAllFiltersCombined {
        /*
         * 🧪 Zadanie 6:
         * Wywolaj search("PAID", LocalDate.of(2024, 1, 1), new BigDecimal("50.00"),
         * new BigDecimal("500.00")) (WSZYSTKIE kryteria naraz, jak w lekcji) - wypisz
         * wynik i policz recznie w komentarzu, ktore z 6 zamowien powinny spelniac
         * WSZYSTKIE cztery warunki naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SqlInjectionAttemptThroughStatusValue {
        /*
         * 🧪 Zadanie 7:
         * Wywolaj search("PAID' OR '1'='1", null, null, null) (jak w lekcji). Sprawdz,
         * ze wynik ma 0 elementow - PreparedStatement traktuje cala wartosc jako
         * DOSLOWNY tekst parametru, nie jako fragment SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountParamsMatchesPlaceholders {
        /*
         * 🧪 Zadanie 8:
         * Rozbuduj search o logowanie: po zbudowaniu SQL i listy params, wypisz
         * liczbe znakow '?' w sql.toString() oraz params.size() - sprawdz dla 4
         * roznych kombinacji filtrow (0, 1, 2, 4 aktywne filtry), ze liczby SIE
         * ZGADZAJA (kazdy placeholder ma odpowiadajaca mu wartosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrintHelperForOrderResults {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode print(List<Order> orders) (jak w lekcji - wypisuje "(brak
         * wynikow)" dla pustej listy). Wywolaj ja dla wyniku search z filtrem, ktory
         * NIE zwraca zadnego wiersza (np. status="NIEISTNIEJACY") i sprawdz format
         * wypisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SingleOptionalStatusFilterMethod {
        /*
         * 🧪 Zadanie 10:
         * Napisz UPROSZCZONA metode findByOptionalStatus(String status) - jeden,
         * pojedynczy opcjonalny filtr (bez daty/kwoty), zbudowana wg tego samego
         * wzorca (StringBuilder + jeden warunkowy AND). Wywolaj dla null i dla
         * konkretnego statusu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddFifthOptionalFilterCustomerName {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do orders kolumne customer_name VARCHAR. Rozbuduj search o PIATY
         * opcjonalny parametr customerName (dopisujacy "AND customer_name = ?" gdy
         * nie null). Zademonstruj wywolanie z WSZYSTKIMI PIECIOMA filtrami aktywnymi
         * naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LikeFilterForPartialCustomerNameMatch {
        /*
         * 🧪 Zadanie 12:
         * Zamiast dokladnego dopasowania customer_name, uzyj "AND customer_name LIKE
         * ?" z wartoscia parametru "%" + fragment + "%" (WARTOSC wciaz przez `?`,
         * TYLKO konkatenacja znakow % w Javie, NIE w SQL). Zademonstruj wyszukiwanie
         * fragmentu imienia klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CombiningRangeFilterWithSortingWhitelist {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj search o dodatkowy, ZWALIDOWANY (biala lista jak w
         * Lesson23_DynamicSorting) parametr sortColumn zastepujacy "ORDER BY id" na
         * koncu zapytania. Zademonstruj wywolanie z filtrem amount + sortColumn=
         * "amount" oraz proba wstrzykniecia niedozwolonej kolumny (powinna zostac
         * odrzucona PRZED zbudowaniem SQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CountMatchingOrdersWithoutFetchingRows {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode long countMatching(status, fromDate, minAmount, maxAmount)
         * budujaca TEN SAM dynamiczny WHERE co search (wydziel budowanie WHERE do
         * wspolnej metody pomocniczej!), ale wykonujaca "SELECT COUNT(*) FROM orders
         * WHERE ..." zamiast pobierania wierszy. Zweryfikuj, ze dla tych samych
         * filtrow countMatching zwraca TAKA SAMA liczbe, jak rozmiar listy z
         * search().
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExtractWhereBuilderToSeparateMethod {
        /*
         * 🧪 Zadanie 15:
         * Wydziel budowanie klauzuli WHERE (StringBuilder + List<Object>) z metody
         * search do osobnej, prywatnej metody buildWhereClause(status, fromDate,
         * minAmount, maxAmount) zwracajacej maly rekord/pare (WhereClause(String sql,
         * List<Object> params)). Uzyj jej TAKZE w countMatching z Zadania 14 -
         * eliminujac duplikacje kodu miedzy dwoma metodami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FilterByStatusInListOfValues {
        /*
         * 🧪 Zadanie 16:
         * Rozbuduj search o parametr List<String> statuses (moze byc null/pusta -
         * "brak filtra") budujacy "AND status IN (?, ?, ...)" z TYLE placeholderow,
         * ile elementow na liscie (kazdy jako OSOBNY parametr `?`, NIGDY jako
         * konkatenacja Stringow w jednym placeholderze). Zademonstruj dla listy
         * ["PAID", "NEW"].
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_NullVsEmptyStringTreatedDifferently {
        /*
         * 🧪 Zadanie 17:
         * Sprawdz zachowanie search dla status="" (PUSTY STRING, nie null) - w
         * obecnej implementacji pusty string NIE jest traktowany jako "brak
         * filtra" (bo warunek to `status != null`), wiec zapytanie faktycznie
         * filtruje po status=''. Zademonstruj to zachowanie i zaproponuj (i
         * zaimplementuj) poprawke uzywajaca `status != null && !status.isBlank()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MinAmountGreaterThanMaxAmountEdgeCase {
        /*
         * 🧪 Zadanie 18:
         * Wywolaj search z minAmount=500 i maxAmount=100 (odwrocony, niesensowny
         * zakres). Sprawdz, ze zapytanie zwraca 0 wynikow (SQL po prostu nie
         * znajdzie wiersza spelniajacego "amount >= 500 AND amount <= 100") - i
         * zaproponuj w komentarzu walidacje w Service, ktora odrzucilaby takie
         * dane WCZESNIEJ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DynamicFilterCombinedWithPagination {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj search o parametry page i size (LIMIT/OFFSET dodane NA KONCU
         * dynamicznie zbudowanego zapytania, PO ORDER BY, jak w Lesson22). Zwroc
         * Page<Order> (uzyj rekordu z Lesson22, recznie skopiowanego lub
         * odtworzonego lokalnie) laczac dynamiczne filtrowanie z paginacja.
         * Zademonstruj na filtrze status="PAID" + page=1, size=2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestingDynamicFilterCombinationsWithMiniRunner {
        /*
         * 🧪 Zadanie 20:
         * Napisz mini test-runner (jak w Lesson26_TestingDao) z co najmniej 6
         * testami: brak filtrow zwraca wszystko, kazdy filtr osobno zwraca
         * poprawna liczbe wynikow, kombinacja wszystkich filtrow zwraca poprawna
         * liczbe, atak SQL injection przez wartosc filtra zwraca 0 wynikow. Wypisz
         * PASSED/FAILED dla kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FilterCriteriaAsBuilderPattern {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj klase OrderSearchCriteria z metodami "buildera"
         * (withStatus(String), withDateFrom(LocalDate), withAmountRange(min, max)) -
         * kazda zwraca "this", pozwalajac na lancuchowe wywolania. Rozbuduj
         * OrderDao.search, by przyjmowal JEDEN obiekt OrderSearchCriteria zamiast 4
         * osobnych parametrow. Zademonstruj budowanie kryteriow lancuchowo i
         * przekazanie ich do search.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FilterByJoinedProductCategoryOptional {
        /*
         * 🧪 Zadanie 22:
         * Dodaj tabele products (id, category) i kolumne product_id w orders.
         * Rozbuduj search o OPCJONALNY filtr po category PRODUKTU powiazanego z
         * zamowieniem (wymaga JOIN z products, dolaczanego do zapytania TYLKO gdy
         * ten filtr jest aktywny - warunkowo dodany JOIN, nie tylko WHERE).
         * Zademonstruj z i bez tego filtra.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DynamicFilterPerformanceWithAndWithoutIndex {
        /*
         * 🧪 Zadanie 23:
         * Wstaw 2000 wierszy do orders z rozlosowanymi statusami/datami/kwotami.
         * Zmierz (System.nanoTime()) czas search z filtrem po status BEZ indeksu, a
         * potem utworz "CREATE INDEX idx_status ON orders(status)" i zmierz ten sam
         * filtr PONOWNIE. Wypisz oba czasy i skomentuj rozniice (moze byc niewielka
         * na H2 in-memory z mala tabela, ale mechanizm i podejscie sa poprawne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ValidateFilterValuesBeforeQueryExecution {
        /*
         * 🧪 Zadanie 24:
         * Rozbuduj search o walidacje WARTOSCI PRZED zbudowaniem SQL: status (jesli
         * podany) musi byc jedna z {"NEW","PAID","CANCELLED"} (biala lista
         * WARTOSCI, nie kolumn - to inny przypadek niz Lesson23), fromDate nie moze
         * byc w przyszlosci, minAmount/maxAmount nie moga byc ujemne. Zademonstruj
         * na 4 przypadkach naruszajacych kazda regule.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConcurrentFilteringDoesNotInterfereBetweenCalls {
        /*
         * 🧪 Zadanie 25:
         * Uruchom 4 WATKI (kazdy z WLASNYM Connection), kazdy wywolujacy search z
         * INNYM zestawem filtrow, po 20 razy w petli. Poczekaj na zakonczenie
         * wszystkich (join z limitem czasu) i sprawdz, ze KAZDY watek konsekwentnie
         * dostawal TE SAME wyniki dla swoich filtrow przez cale 20 iteracji (StringBuilder
         * lokalny w metodzie - brak wspoldzielonego, mutowalnego stanu miedzy
         * wywolaniami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExportFilteredResultsToCsvString {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode String exportToCsv(List<Order> orders) konwertujaca wynik
         * search() na prosty String CSV (naglowek + wiersze, przecinek jako
         * separator). Zademonstruj eksport wyniku search z 2 aktywnymi filtrami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DynamicFilterWithOrLogicBetweenTwoOptionalGroups {
        /*
         * 🧪 Zadanie 27:
         * Rozbuduj search o obsluge logiki OR miedzy DWOMA grupami warunkow (np.
         * "status=PAID" LUB "amount > 400", oba opcjonalne) - zbuduj fragment
         * "AND (cond1 OR cond2)" TYLKO gdy OBA warunki grupy sa aktywne, w
         * przeciwnym razie dodaj tylko ten jeden, ktory jest aktywny (bez OR).
         * Zademonstruj na 3 kombinacjach obecnosci/braku obu warunkow grupy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullTextSearchAcrossMultipleOptionalColumns {
        /*
         * 🧪 Zadanie 28:
         * Rozbuduj search o parametr searchText (moze byc null) budujacy "AND
         * (status LIKE ? OR customer_name LIKE ?)" z TA SAMA wartoscia "%" +
         * searchText + "%" powtorzona jako DWA OSOBNE parametry `?` (w poprawnej
         * kolejnosci wzgledem listy params!). Zademonstruj wyszukiwanie fragmentu
         * tekstu, ktory moze wystapic w OBU kolumnach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TestSuiteCoveringParameterOrderMismatchBug {
        /*
         * 🧪 Zadanie 29:
         * Zademonstruj (i napraw) TYPOWY blad: zmien kolejnosc dodawania warunkow w
         * search TAK, by KOLEJNOSC w params.add(...) NIE ZGADZALA SIE z kolejnoscia
         * placeholderow `?` w SQL (wprowadz blad recznie). Uruchom search i pokaz,
         * ze wyniki sa BLEDNE/nieoczekiwane (np. porownanie liczby vs daty).
         * Napraw blad i udowodnij testem, ze wynik jest teraz poprawny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullDynamicFilteringApiLayer {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletna "warstwe API" wyszukiwania zamowien: OrderSearchCriteria
         * (builder, Zadanie 21), walidacja wartosci (Zadanie 24), opcjonalny JOIN po
         * kategorii produktu (Zadanie 22), paginacja (Zadanie 19), sortowanie z
         * bialej listy (Zadanie 13) i eksport CSV (Zadanie 26). Zasymuluj 5
         * "zadan wyszukiwania" o roznych kombinacjach kryteriow (w tym jedno z
         * proba SQL injection) i wypisz kompletny raport wykonania dla kazdego.
         */
        public static void main(String[] args) { }
    }
}
