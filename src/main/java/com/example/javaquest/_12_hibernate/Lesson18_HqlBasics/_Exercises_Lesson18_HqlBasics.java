package com.example.javaquest._12_hibernate.Lesson18_HqlBasics;

public class _Exercises_Lesson18_HqlBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicFromQuery {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Product (id, name, price, category) na H2
         * ("jdbc:h2:mem:l18ex01;DB_CLOSE_DELAY=-1"). Zapisz 5 produktow. Uzyj HQL
         * "from Product" zeby odczytac WSZYSTKIE naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WhereWithNamedParameter {
        /*
         * 🧪 Zadanie 2:
         * Napisz HQL "from Product p where p.category = :cat" z parametrem
         * nazwanym. Znajdz wszystkie produkty konkretnej kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WhereWithPositionalParameter {
        /*
         * 🧪 Zadanie 3:
         * Ta sama funkcjonalnosc co Zadanie 2, ale z parametrem pozycyjnym (?1).
         * Porownaj w komentarzu czytelnosc obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OrderByAscending {
        /*
         * 🧪 Zadanie 4:
         * Napisz HQL "from Product p order by p.price asc" - zweryfikuj, ze wyniki
         * sa posortowane rosnaco po cenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_OrderByDescending {
        /*
         * 🧪 Zadanie 5:
         * Ta sama lista, ale posortowana MALEJACO ("order by p.price desc").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LikeOperator {
        /*
         * 🧪 Zadanie 6:
         * Napisz HQL z LIKE ("where p.name like :pattern") zeby znalezc produkty,
         * ktorych nazwa ZACZYNA SIE od konkretnej litery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InOperator {
        /*
         * 🧪 Zadanie 7:
         * Napisz HQL z IN ("where p.category in :categories") zeby znalezc produkty
         * NALEZACE do jednej z 2 wybranych kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BetweenOperator {
        /*
         * 🧪 Zadanie 8:
         * Napisz HQL z BETWEEN ("where p.price between :min and :max") zeby znalezc
         * produkty w konkretnym przedziale cenowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TypedQueryReturnsCorrectType {
        /*
         * 🧪 Zadanie 9:
         * Uzyj TypedQuery<Product> (createQuery(hql, Product.class)) i zweryfikuj
         * (getClass()), ze getResultList() zwraca konkretnie List<Product>, bez
         * potrzeby rzutowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GetSingleResultForUniqueMatch {
        /*
         * 🧪 Zadanie 10:
         * Napisz HQL zwracajacy DOKLADNIE JEDEN wynik (np. po unikalnej nazwie) i
         * uzyj getSingleResult() zamiast getResultList().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CombineWhereAndOrderBy {
        /*
         * 🧪 Zadanie 11:
         * Napisz JEDNO zapytanie HQL laczace WHERE (filtr kategorii) z ORDER BY
         * (sortowanie po cenie) - zweryfikuj, ze wynik jest zarowno przefiltrowany,
         * jak i posortowany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleConditionsWithAndOr {
        /*
         * 🧪 Zadanie 12:
         * Napisz HQL z warunkiem zlozonym: "where (p.category = :cat1 or p.category =
         * :cat2) and p.price < :maxPrice" - zweryfikuj poprawnosc dzialania nawiasow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NotOperatorNegation {
        /*
         * 🧪 Zadanie 13:
         * Napisz HQL z "not like" i osobno z "not in" - znajdz produkty NIE
         * pasujace do danego wzorca/listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_IsNullAndIsNotNull {
        /*
         * 🧪 Zadanie 14:
         * Zapisz kilka produktow, z ktorych CZESC ma category=null. Napisz HQL "where
         * p.category is null" ORAZ "is not null" - zweryfikuj poprawny podzial.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_GetSingleResultNoResultException {
        /*
         * 🧪 Zadanie 15:
         * Napisz HQL zwracajacy ZERO wynikow i wywolaj getSingleResult() - zapisz w
         * komentarzu wyjatek NoResultException (zamiast zwrocenia null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GetSingleResultTooManyResults {
        /*
         * 🧪 Zadanie 16:
         * Napisz HQL zwracajacy WIECEJ NIZ jeden wynik i wywolaj getSingleResult() -
         * zapisz w komentarzu wyjatek NonUniqueResultException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SetMaxResultsPagination {
        /*
         * 🧪 Zadanie 17:
         * Zapisz 20 produktow. Uzyj setMaxResults(5) zeby ograniczyc wynik do
         * pierwszych 5 - zweryfikuj rozmiar zwroconej listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SetFirstResultOffset {
        /*
         * 🧪 Zadanie 18:
         * Uzyj setFirstResult(5) razem z setMaxResults(5) (jak paginacja strony 2)
         * - zweryfikuj, ktore konkretnie produkty zwrocilo zapytanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConcatenationInSelect {
        /*
         * 🧪 Zadanie 19:
         * Napisz HQL "select concat(p.name, ' - ', p.category) from Product p"
         * zwracajacy String zamiast pelnej encji - zweryfikuj poprawnosc konkatenacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CaseSensitivityOfHqlIdentifiers {
        /*
         * 🧪 Zadanie 20:
         * Sprawdz (eksperymentalnie), czy nazwa encji w HQL ("from Product" vs "from
         * product" - mala litera) jest case-sensitive - zapisz obserwacje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DynamicSearchWithOptionalParameters {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode "searchProducts(Session, String category, Double minPrice,
         * Double maxPrice)" budujaca HQL Z WARUNKOWO doklejanymi fragmentami WHERE
         * (String concatenation) w zaleznosci od tego, ktore parametry sa != null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ParameterizedQueryPreventInjection {
        /*
         * 🧪 Zadanie 22:
         * Napisz HQL przyjmujacy jako filtr WARTOSC podana przez "uzytkownika"
         * (symulowany String input, potencjalnie zawierajacy znaki specjalne) -
         * zademonstruj, ze uzycie PARAMETRU (:cat) jest bezpieczne, w odroznieniu od
         * sklejania Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ComplexReportQuery {
        /*
         * 🧪 Zadanie 23:
         * Zapisz 50 produktow w 5 kategoriach. Napisz zlozone zapytanie HQL
         * laczace WHERE (cena > srednia globalna, podglad przed Lesson19), ORDER BY,
         * i setMaxResults - "top 10 najdrozszych produktow powyzej sredniej".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ReusableQueryBuilderHelper {
        /*
         * 🧪 Zadanie 24:
         * Napisz klase "ProductQueryBuilder" z metodami fluent (.withCategory(...)
         * .withPriceRange(...) .build(Session)) budujaca finalne TypedQuery<Product> -
         * uzyj jej zamiast recznego sklejania HQL w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PerformanceComparisonHqlVsFindLoop {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas znalezienia 10 konkretnych produktow (po znanych id): raz
         * przez petle find() x10, raz przez JEDNO zapytanie HQL "where p.id in
         * (:ids)" - porownaj oba czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HqlWithSubqueryPreview {
        /*
         * 🧪 Zadanie 26:
         * Napisz HQL z PODZAPYTANIEM (podglad przed Lesson19) "where p.price > (select
         * avg(p2.price) from Product p2)" - znajdz produkty drozsze niz srednia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigrateRawSqlToHql {
        /*
         * 🧪 Zadanie 27:
         * Wez zapytanie SQL z _08_sql (np. z Lesson filtrujaca po WHERE+ORDER BY+LIMIT)
         * i PRZEPISZ je na HQL - porownaj w komentarzu skladnie obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ValidateAllHqlAtStartupSketch {
        /*
         * 🧪 Zadanie 28:
         * Zbierz 10 roznych zapytan HQL uzywanych w Twoich cwiczeniach do JEDNEJ
         * listy Stringow i napisz metode "validateAll(SessionFactory)" probujaca
         * KAZDE sparsowac (createQuery) BEZ wykonywania - zlap ewentualne bledy
         * skladni PRZED uruchomieniem aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnHqlVsSqlTradeoffs {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * porownujacy HQL vs czyste SQL (_08_sql) - kiedy przenosnosc HQL ma
         * znaczenie, a kiedy warto zejsc do native SQL (Lesson21).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullHqlQueryLibraryCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNA "biblioteke" zapytan HQL dla
         * katalogu produktow - wszystkie operatory z tej lekcji (WHERE, LIKE, IN,
         * BETWEEN, IS NULL, ORDER BY, paginacja) w JEDNEJ, spojnej klasie
         * "ProductQueries" z udokumentowanymi metodami.
         */
        public static void main(String[] args) { }
    }
}
