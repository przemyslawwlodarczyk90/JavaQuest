package com.example.javaquest._12_hibernate.Lesson19_HqlAdvanced;

public class _Exercises_Lesson19_HqlAdvanced {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicJoinFetch {
        /*
         * 🧪 Zadanie 1:
         * Utworz Author (id, name) i Book (id, title, price, @ManyToOne author) na
         * H2 ("jdbc:h2:mem:l19ex01;DB_CLOSE_DELAY=-1"). Napisz HQL "select b from
         * Book b join fetch b.author" i zweryfikuj JEDNO zapytanie SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CountAggregateFunction {
        /*
         * 🧪 Zadanie 2:
         * Zapisz 5 ksiazek. Napisz HQL "select count(b) from Book b" zwracajacy
         * CALKOWITA liczbe ksiazek jako Long.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SumAggregateFunction {
        /*
         * 🧪 Zadanie 3:
         * Napisz HQL "select sum(b.price) from Book b" zwracajacy SUME cen
         * wszystkich ksiazek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AvgMinMaxFunctions {
        /*
         * 🧪 Zadanie 4:
         * Napisz TRZY osobne zapytania HQL zwracajace: srednia cene (avg), minimalna
         * cene (min), maksymalna cene (max) wszystkich ksiazek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GroupByBasic {
        /*
         * 🧪 Zadanie 5:
         * Zapisz ksiazki od 3 roznych autorow. Napisz HQL "select a.name, count(b)
         * from Book b join b.author a group by a.name" zliczajacy ksiazki KAZDEGO
         * autora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_HavingFilter {
        /*
         * 🧪 Zadanie 6:
         * Rozszerz zapytanie z Zadania 5 o "having count(b) > 1" - znajdz TYLKO
         * autorow z WIECEJ NIZ jedna ksiazka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SimpleSubqueryInWhere {
        /*
         * 🧪 Zadanie 7:
         * Napisz HQL z podzapytaniem "where b.price > (select avg(b2.price) from
         * Book b2)" - znajdz ksiazki drozsze niz srednia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExistsSubquery {
        /*
         * 🧪 Zadanie 8:
         * Napisz HQL z EXISTS "where exists (select 1 from Book b where b.author =
         * a)" - znajdz autorow MAJACYCH przynajmniej jedna ksiazke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DtoProjectionBasic {
        /*
         * 🧪 Zadanie 9:
         * Utworz record BookTitleOnly(String title). Napisz HQL "select new
         * pelna.nazwa.BookTitleOnly(b.title) from Book b" - zwerfyfikuj projekcje BEZ
         * ladowania calej encji Book.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BulkUpdateBasic {
        /*
         * 🧪 Zadanie 10:
         * Napisz "update Book set price = price * 1.1" (executeUpdate()) - zweryfikuj
         * ze WSZYSTKIE ceny wzrosly o 10% JEDNYM zapytaniem SQL.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_JoinFetchVsPlainJoinComparison {
        /*
         * 🧪 Zadanie 11:
         * Napisz DWIE wersje TEGO SAMEGO zapytania - "join" (zwykly) i "join fetch" -
         * dla obu sprawdz (po Session.close()) czy dostep do author.getName() rzuca
         * LazyInitializationException (spodziewaj sie: TAK dla "join", NIE dla "join fetch").
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleGroupByColumns {
        /*
         * 🧪 Zadanie 12:
         * Dodaj do Book pole "category". Napisz HQL grupujacy po DWOCH kolumnach
         * naraz (author.name, category) - zliczajac ksiazki w kazdej kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CorrelatedSubquery {
        /*
         * 🧪 Zadanie 13:
         * Napisz HQL z podzapytaniem SKORELOWANYM (odwolujacym sie do zmiennej z
         * zapytania zewnetrznego) - np. "gdzie cena wieksza niz srednia cena TEGO
         * SAMEGO autora".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DtoWithMultipleFieldsFromJoin {
        /*
         * 🧪 Zadanie 14:
         * Utworz record BookSummary(String title, String authorName, double price).
         * Napisz projekcje DTO laczaca dane z DWOCH encji (Book+Author) przez join.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BulkDeleteWithCondition {
        /*
         * 🧪 Zadanie 15:
         * Napisz "delete from Book where price < :threshold" (executeUpdate()) -
         * zweryfikuj, ze TYLKO tanie ksiazki zostaly usuniete, JEDNYM zapytaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BulkUpdateDoesNotRefreshLoadedEntities {
        /*
         * 🧪 Zadanie 16:
         * Zaladuj Book do pamieci (find()), potem wykonaj bulk update zmieniajacy
         * jego cene BEZPOSREDNIO w bazie. Sprawdz w komentarzu, ze JUZ zaladowany
         * obiekt w pamieci NADAL ma STARA cene (bulk update omija first-level cache).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RefreshAfterBulkUpdate {
        /*
         * 🧪 Zadanie 17:
         * Napraw problem z Zadania 16 przez session.refresh(book) PO bulk update -
         * zweryfikuj, ze obiekt w pamieci ma TERAZ AKTUALNA cene.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CountDistinctValues {
        /*
         * 🧪 Zadanie 18:
         * Napisz HQL "select count(distinct b.author) from Book b" zliczajacy
         * UNIKALNYCH autorow (nie liczbe ksiazek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CaseWhenExpressionInSelect {
        /*
         * 🧪 Zadanie 19:
         * Napisz HQL z wyrazeniem "case when p.price > 50 then 'drogie' else
         * 'tanie' end" klasyfikujac ksiazki na dwie kategorie cenowe w SAMYM zapytaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BulkUpdateVsLoopPerformance {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas podwyzszenia ceny 1000 ksiazek o 10%: raz PETLA (find+setPrice+
         * commit dla kazdej), raz JEDNYM bulk update - zapisz oba czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ComplexReportWithJoinGroupByHaving {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj RAPORT laczacy join fetch, group by, having I order by w JEDNYM
         * zapytaniu - "autorzy z wiecej niz 2 ksiazkami, posortowani po lacznej
         * wartosci ich ksiazek malejaco".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NestedSubqueriesTwoLevels {
        /*
         * 🧪 Zadanie 22:
         * Napisz HQL z DWOMA zagniezdzonymi poziomami podzapytan (np. "autorzy,
         * ktorych srednia cena ksiazek jest wyzsza niz globalna srednia SREDNICH
         * cen wszystkich autorow") - opisz logike w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DtoProjectionWithAggregateFunction {
        /*
         * 🧪 Zadanie 23:
         * Utworz record AuthorStats(String name, long bookCount, double totalValue).
         * Napisz projekcje DTO LACZACA join, group by, i funkcje agregujace w JEDNYM
         * "select new" wyrazeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BulkOperationWithinTransactionRollback {
        /*
         * 🧪 Zadanie 24:
         * Wykonaj bulk update W TRANSAKCJI, a potem wywolaj rollback() zamiast
         * commit - zweryfikuj, ze zmiana ZOSTALA wycofana (bulk operacje TEZ
         * respektuja transakcje, mimo ze omijaja dirty checking).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MigratingReportFromJavaLoopToHql {
        /*
         * 🧪 Zadanie 25:
         * Napisz raport "suma wartosci ksiazek kazdego autora" NAJPIERW przez
         * zaladowanie WSZYSTKICH danych i sumowanie w Javie (petle), POTEM przez
         * JEDNO zapytanie HQL z group by+sum - porownaj czas i zuzycie pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConditionalBulkUpdateWithCase {
        /*
         * 🧪 Zadanie 26:
         * Napisz bulk update z wyrazeniem CASE WHEN wewnatrz SET - np. "update Book
         * set price = case when price > 100 then price * 0.9 else price * 1.05 end"
         * (rozne przeceny w zaleznosci od progu ceny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_QueryPlanCacheAwareness {
        /*
         * 🧪 Zadanie 27:
         * Uruchom TO SAMO zapytanie HQL (z roznymi wartosciami parametrow) 100 razy
         * w petli i zmierz czas - porownaj z 100 wywolaniami zapytan o LEKKO INNEJ
         * strukturze (rozne stringi HQL za kazdym razem) - zapisz w komentarzu
         * wplyw na wydajnosc (Hibernate cache'uje SPARSOWANE plany zapytan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullReportingModuleWithMultipleDtos {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj klase "BookReportService" z 5 roznymi metodami raportujacymi
         * (kazda zwracajaca INNY typ DTO przez "select new") - top autorzy, tanie
         * ksiazki, statystyki wedlug kategorii, itd.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnBulkOperationsRisks {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami ryzyka bulk operations (pominiecie dirty
         * checking, callbacks @PreUpdate NIE wywolywane, potrzeba recznego refresh) -
         * kiedy mimo to warto ich uzyc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullAdvancedHqlCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY modul analityczny dla biblioteki
         * ksiazek laczacy: join fetch (unikanie N+1), raporty z group by/having,
         * projekcje DTO (Zadanie 23), i bulk operations z odpowiednim refresh
         * (Zadania 16-17) - zademonstruj wszystko w jednym, spojnym scenariuszu.
         */
        public static void main(String[] args) { }
    }
}
