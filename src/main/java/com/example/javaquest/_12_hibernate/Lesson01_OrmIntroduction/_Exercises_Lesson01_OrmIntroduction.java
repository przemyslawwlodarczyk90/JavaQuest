package com.example.javaquest._12_hibernate.Lesson01_OrmIntroduction;

public class _Exercises_Lesson01_OrmIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ManualMappingVsHibernate {
        /*
         * 🧪 Zadanie 1:
         * Bez Hibernate: napisz klasę Product (id, name, price) i ręcznie (czystym
         * JDBC, jak w _09_jdbc) zapisz jeden obiekt do tabeli "product" na H2
         * ("jdbc:h2:mem:l01ex01;DB_CLOSE_DELAY=-1"). Policz linie kodu potrzebne do
         * samego INSERT-a i zapisz w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SameEntityWithHibernate {
        /*
         * 🧪 Zadanie 2:
         * Ten sam Product z Zadania 1, ale jako @Entity (SessionFactory na H2,
         * "jdbc:h2:mem:l01ex02;DB_CLOSE_DELAY=-1", hbm2ddl.auto=create-drop). Zapisz
         * przez session.persist(). Porownaj w komentarzu liczbe linii kodu z
         * Zadaniem 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MinimalEntityRequirements {
        /*
         * 🧪 Zadanie 3:
         * Utworz klase encji Category (id, name) celowo BEZ bezargumentowego
         * konstruktora (dodaj tylko konstruktor z parametrami). Sprobuj zbudowac
         * SessionFactory i zapisac obiekt - zapisz w komentarzu tresc bledu, jaki
         * zwraca Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FinalClassEntityError {
        /*
         * 🧪 Zadanie 4:
         * Oznacz klase encji jako "final" (np. "public static final class Category").
         * Sprobuj zbudowac SessionFactory z ta klasa i zapisz w komentarzu, czy i
         * jaki blad Hibernate zwraca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MultipleIdFieldsError {
        /*
         * 🧪 Zadanie 5:
         * Dodaj DWA pola oznaczone @Id w jednej klasie encji (bez @IdClass/@EmbeddedId).
         * Sprobuj zbudowac SessionFactory i zapisz w komentarzu tresc bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_JpaVsHibernateNamingPrint {
        /*
         * 🧪 Zadanie 6:
         * Bez bazy danych: wypisz na konsoli (System.out.println) 3 pary nazw
         * "JPA standard -> Hibernate natywny odpowiednik" dla podstawowych operacji
         * (np. persist -> save, find -> get, EntityManager -> Session).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MyBatisStyleManualSql {
        /*
         * 🧪 Zadanie 7:
         * Bez Hibernate: napisz metode wykonujaca RECZNIE napisane zapytanie SQL
         * (jak w podejsciu MyBatis - Ty piszesz SQL) mapujace ResultSet na obiekt
         * Product. Porownaj w komentarzu z podejsciem ORM z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountLinesComparisonTable {
        /*
         * 🧪 Zadanie 8:
         * Bez bazy danych: wypisz tabele (min. 3 wiersze) porownujaca PRZYBLIZONA
         * liczbe linii kodu potrzebnych do pelnego CRUD na jednej encji: czysty JDBC
         * vs DAO reczny (_10_dao) vs Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WhenOrmHurts {
        /*
         * 🧪 Zadanie 9:
         * Bez bazy danych: wypisz 2 konkretne scenariusze (inne niz w lekcji), w
         * ktorych czyste SQL/JDBC byloby lepszym wyborem niz Hibernate - z
         * uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FirstEntityWithTwoFields {
        /*
         * 🧪 Zadanie 10:
         * Stworz encje Note (id, content) - jak w lekcji, ale z dodatkowym polem
         * "important" (boolean). Zapisz 2 notatki (jedna important=true) na H2 i
         * odczytaj obie przez session.find().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_JooqLikeTypedQuerySketch {
        /*
         * 🧪 Zadanie 11:
         * Bez bazy danych: wypisz (jako String, tylko do wyswietlenia) SZKIC
         * przykladowego zapytania w stylu jOOQ (typowane API budowane w Javie) dla
         * "SELECT * FROM product WHERE price > 100" - z komentarzem wyjasniajacym
         * roznice wzgledem HQL Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SpringDataJpaForwardLooking {
        /*
         * 🧪 Zadanie 12:
         * Bez bazy danych: wypisz przykladowy interfejs
         * "ProductRepository extends JpaRepository<Product, Long>" z jedna metoda
         * wyprowadzona z nazwy (np. findByPriceGreaterThan) - z komentarzem, ze to
         * warstwa NAD Hibernate, temat przyszlego rozdzialu Spring Boot.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_EntityWithoutTableAnnotation {
        /*
         * 🧪 Zadanie 13:
         * Utworz encje BEZ @Table (tylko @Entity). Zapisz obiekt na H2 i sprawdz w
         * konsoli (show_sql) jaka nazwe tabeli Hibernate wygenerowal domyslnie -
         * zapisz obserwacje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleEntitiesOneSessionFactory {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj JEDNA SessionFactory z DWIEMA roznymi encjami (np. Product i
         * Category, bez relacji miedzy nimi). Zapisz po jednym obiekcie kazdej i
         * odczytaj oba w tej samej Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareInsertTimings {
        /*
         * 🧪 Zadanie 15:
         * Zmierz (System.nanoTime()) czas zapisu 100 obiektow Product czystym JDBC
         * (PreparedStatement w petli) vs 100 obiektow przez Hibernate
         * (session.persist() w petli, bez batchowania). Zapisz oba czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AlternativeOrmResearchPrint {
        /*
         * 🧪 Zadanie 16:
         * Bez bazy danych: wypisz krotkie (2-3 zdania) porownanie MyBatis vs jOOQ vs
         * Hibernate pod katem "ile kontroli nad SQL" daje kazde z nich (od
         * najmniejszej do najwiekszej kontroli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_EntityEqualsAndHashCode {
        /*
         * 🧪 Zadanie 17:
         * Dodaj do encji Product WLASNA implementacje equals()/hashCode() oparta
         * TYLKO na polu id. Zapisz obiekt, odczytaj go w NOWEJ Session i porownaj
         * przez equals() z oryginalnym (transient) obiektem sprzed zapisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ManualDaoRefactorToEntity {
        /*
         * 🧪 Zadanie 18:
         * Wez dowolne DAO z rozdzialu _10_dao (np. UserDao) i PRZEPISZ jego
         * podstawowe operacje (save/findById) na Hibernate - zachowujac te same
         * sygnatury metod publicznych, ale zamieniajac implementacje wewnetrzna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImpedanceMismatchDemo {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj klase Java z polem typu List<String> (kolekcja) i sprobuj zapisac
         * ja jako zwykla encje @Entity BEZ zadnych dodatkowych adnotacji (poza @Id).
         * Zapisz w komentarzu blad/zachowanie Hibernate - to demonstracja
         * niedopasowania obiektowo-relacyjnego z Lesson1 (kolekcje wymagaja
         * dodatkowego mapowania, poznanego w Lesson12/13).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_HibernateVersionPrint {
        /*
         * 🧪 Zadanie 20:
         * Wypisz na konsoli wersje Hibernate uzywana w tym projekcie (widoczna w
         * logu startowym SessionFactory: "HHH000412: Hibernate ORM core version
         * ...") - uruchom dowolna lekcje i zapisz w komentarzu odczytana wersje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCrudBothWays {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PELNY CRUD (create/read/update/delete) dla encji Product -
         * RAZ czystym JDBC (jak w _09_jdbc), RAZ przez Hibernate. Uruchom oba i
         * zapisz w komentarzu subiektywne porownanie czytelnosci kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CustomExceptionOnValidationFailure {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj encje z polem, ktore MUSI byc dodatnie (price > 0), ale bez Bean
         * Validation (poznamy w Lesson28) - napisz WLASNA walidacje w konstruktorze/
         * setterze rzucajaca IllegalArgumentException PRZED wywolaniem
         * session.persist().
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MigratingDaoToHibernateReport {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: napisz raport tekstowy (w komentarzu) opisujacy, JAKIE
         * kroki trzeba by wykonac, zeby migrowac caly rozdzial _10_dao (28 lekcji)
         * na Hibernate - z min. 4 punktami (mapowanie encji, zamiana DAO na
         * repozytoria, obsluga transakcji, testy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkBatchVsIndividual {
        /*
         * 🧪 Zadanie 24:
         * Zmierz czas zapisu 500 obiektow Product przez Hibernate DWOMA sposobami:
         * pojedynczo (session.persist() w petli) vs z flush+clear co 50 (wzorzec z
         * Lesson23). Zapisz oba czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ReadOnlyReportingQuery {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj scenariusz z 1000 wierszami Product i napisz DWIE wersje raportu
         * "suma cen wszystkich produktow": jedna ladujaca WSZYSTKIE encje i sumujaca
         * w Javie, druga przez agregacje SQL (native query lub HQL sum()). Porownaj
         * w komentarzu czas i zuzycie pamieci (subiektywnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_OrmVsJooqSyntaxComparison {
        /*
         * 🧪 Zadanie 26:
         * Bez bazy danych: wypisz TEN SAM warunek WHERE (np. "price BETWEEN 10 AND
         * 50 AND name LIKE 'A%'") zapisany jako: (a) czysty SQL, (b) HQL, (c) szkic
         * Criteria API (Lesson20) - trzy rozne reprezentacje tego samego zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EntityWithBusinessLogicMethod {
        /*
         * 🧪 Zadanie 27:
         * Dodaj do encji Product metode biznesowa applyDiscount(double percent)
         * modyfikujaca pole price - i wywolaj ja na zarzadzanym obiekcie w aktywnej
         * Session (dirty checking, Lesson17, automatycznie zapisze zmiane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareWithAndWithoutOrm {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj mini-scenariusz z 3 powiazanymi tabelami (np. Customer/Order/
         * OrderItem) i zaimplementuj odczyt PELNEGO zamowienia z pozycjami - RAZ
         * recznym JOIN-em w JDBC (_09_jdbc), RAZ przez encje Hibernate z
         * @OneToMany. Porownaj w komentarzu liczbe linii kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnOrmTradeoffs {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, kiedy w REALNYM projekcie wybralbys
         * Hibernate, a kiedy czysty JDBC/DAO - z odniesieniem do konkretnego
         * przykladu z Twojego doswiadczenia lub wyobrazonego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstonePreviewSketch {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: naszkicuj (jako komentarz, bez pisania pelnego
         * kodu) model encji dla mini-biblioteki (Author/Book/Loan) - taki, jaki
         * poznamy w pelni w Lesson30 (capstone) na koncu tego rozdzialu. Wypisz
         * pola i relacje, jakie Twoim zdaniem powinny tam wystapic.
         */
        public static void main(String[] args) { }
    }
}
