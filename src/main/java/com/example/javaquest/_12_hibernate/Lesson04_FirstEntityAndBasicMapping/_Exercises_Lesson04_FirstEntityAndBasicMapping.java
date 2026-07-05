package com.example.javaquest._12_hibernate.Lesson04_FirstEntityAndBasicMapping;

public class _Exercises_Lesson04_FirstEntityAndBasicMapping {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FirstStandaloneEntity {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Movie (@Entity, @Table, @Id, @GeneratedValue IDENTITY, tytul,
         * rok produkcji) na H2 ("jdbc:h2:mem:l04ex01;DB_CLOSE_DELAY=-1"). Zapisz
         * jeden film i odczytaj go przez session.find().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MissingNoArgConstructorError {
        /*
         * 🧪 Zadanie 2:
         * Usun bezargumentowy konstruktor z encji Movie (zostaw tylko konstruktor z
         * parametrami). Sprobuj zapisac obiekt i zapisz w komentarzu blad Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TableNameWithSpecialCharacters {
        /*
         * 🧪 Zadanie 3:
         * Ustaw @Table(name = "movie_catalog") (nazwa inna niz nazwa klasy). Zapisz
         * obiekt i sprawdz w show_sql, ze wygenerowany SQL uzywa TEJ nazwy tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ColumnNullableFalseViolation {
        /*
         * 🧪 Zadanie 4:
         * Ustaw @Column(nullable = false) na polu title. Sprobuj zapisac obiekt z
         * title = null i zapisz w komentarzu blad zwrocony przez baze/Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ColumnLengthViolation {
        /*
         * 🧪 Zadanie 5:
         * Ustaw @Column(length = 10) na polu title. Sprobuj zapisac tytul dluzszy niz
         * 10 znakow i zapisz w komentarzu, czy i jaki blad wystapil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ColumnNameDifferentFromField {
        /*
         * 🧪 Zadanie 6:
         * Ustaw @Column(name = "release_year") na polu "year". Sprawdz w show_sql,
         * ze wygenerowana kolumna nazywa sie "release_year", a nie "year".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FindReturnsNullForMissingId {
        /*
         * 🧪 Zadanie 7:
         * Wywolaj session.find(Movie.class, 9999L) (nieistniejace id) i zapisz w
         * komentarzu, ze metoda zwraca null (a NIE rzuca wyjatku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleFieldsEntity {
        /*
         * 🧪 Zadanie 8:
         * Rozszerz Movie o pola: director (String), durationMinutes (int),
         * rating (double). Zapisz kompletny obiekt i odczytaj wszystkie pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrimitiveVsWrapperFieldTypes {
        /*
         * 🧪 Zadanie 9:
         * Dodaj DWA pola tej samej "koncepcji": jedno jako "int" (prymitywne), drugie
         * jako "Integer" (wrapper, nullable). Zapisz obiekt z Integer=null i
         * zaobserwuj w bazie wartosc NULL vs domyslne 0 dla int.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ReadAllRowsWithFindLoop {
        /*
         * 🧪 Zadanie 10:
         * Zapisz 3 rozne filmy. Odczytaj je PO KOLEI przez session.find() (znajac ich
         * id) i wypisz wszystkie tytuly.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TransientFieldNotPersisted {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do encji pole oznaczone @jakarta.persistence.Transient (np.
         * "popularityScore" liczone w runtime). Zapisz obiekt i sprawdz w show_sql,
         * ze ta kolumna NIE zostala wygenerowana w tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ColumnUniqueConstraintViolation {
        /*
         * 🧪 Zadanie 12:
         * Ustaw @Column(unique = true) na polu title. Zapisz dwa filmy z TYM SAMYM
         * tytulem i zapisz w komentarzu blad naruszenia unikalnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LargeTextColumnDefinition {
        /*
         * 🧪 Zadanie 13:
         * Dodaj pole "description" z @Column(columnDefinition = "CLOB") (dla bardzo
         * dlugiego tekstu). Zapisz opis o dlugosci > 1000 znakow i odczytaj go
         * poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleEntitiesSharedNamingConvention {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj DWIE encje (Movie i Actor, bez relacji miedzy nimi) z ta sama
         * konwencja nazewnictwa kolumn (snake_case przez @Column). Zapisz po jednym
         * obiekcie kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DefaultValueViaConstructor {
        /*
         * 🧪 Zadanie 15:
         * Ustaw w konstruktorze bezargumentowym domyslna wartosc pola (np. rating =
         * 0.0). Zapisz obiekt bez jawnego ustawienia rating i zweryfikuj domyslna
         * wartosc w bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UpdateSingleFieldAfterFind {
        /*
         * 🧪 Zadanie 16:
         * Zapisz film, potem w NOWEJ Session znajdz go, zmien pole "rating" i
         * zatwierdz transakcje (bez jawnego update()/merge() - dirty checking).
         * Zweryfikuj zmiane w KOLEJNEJ, NOWEJ Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ToStringOverrideForEntity {
        /*
         * 🧪 Zadanie 17:
         * Dodaj do encji Movie wlasna metode toString() (BEZ uzywania jej w
         * equals/hashCode). Zapisz i wypisz obiekt bezposrednio przez
         * System.out.println(movie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BooleanFieldMapping {
        /*
         * 🧪 Zadanie 18:
         * Dodaj pole "isAvailable" (boolean). Zapisz dwa filmy (jeden dostepny,
         * jeden nie) i sprawdz w bazie (recznym SQL) jak Hibernate zmapowal boolean
         * na typ SQL w H2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_EntityWithDateField {
        /*
         * 🧪 Zadanie 19:
         * Dodaj pole "releaseDate" typu java.time.LocalDate. Zapisz film z konkretna
         * data i odczytaj ja z powrotem, weryfikujac ze typ sie zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareGeneratedDdlForTwoEntities {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj DWIE wersje tej samej encji Movie - jedna z @Column wszedzie
         * jawnie, druga BEZ zadnych @Column (same domyslne nazwy). Porownaj w
         * komentarzu wygenerowany DDL (show_sql) obu wersji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullEntityWithAllColumnOptions {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj encje Movie wykorzystujaca WSZYSTKIE poznane opcje @Column naraz:
         * name, nullable, length, unique - na roznych polach. Zapisz poprawny obiekt
         * i sprobuj zapisac obiekt naruszajacy KAZDA z tych regul po kolei.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CustomIdGenerationBeforePersist {
        /*
         * 🧪 Zadanie 22:
         * BEZ @GeneratedValue (klucz reczny): napisz metode generujaca "naturalny"
         * identyfikator (np. slug z tytulu, "pan-tadeusz") i ustawiajaca go PRZED
         * session.persist().
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BulkInsertOneHundredMovies {
        /*
         * 🧪 Zadanie 23:
         * Zapisz 100 filmow w petli w JEDNEJ transakcji. Zmierz czas operacji i
         * zapisz w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ReadAllViaHqlPreview {
        /*
         * 🧪 Zadanie 24:
         * Uzyj session.createQuery("from Movie", Movie.class).getResultList() (HQL,
         * pelniej poznany w Lesson18) do odczytania WSZYSTKICH filmow naraz -
         * porownaj z podejsciem find() w petli z Zadania 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ColumnDefinitionRawSql {
        /*
         * 🧪 Zadanie 25:
         * Uzyj @Column(columnDefinition = "DECIMAL(10,2)") dla pola price - sprawdz w
         * show_sql, ze Hibernate uzyl DOKLADNIE tej definicji SQL zamiast domyslnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImmutableEntityWithFinalFields {
        /*
         * 🧪 Zadanie 26:
         * Sprobuj zrobic pola encji "final" (ustawiane tylko w konstruktorze, bez
         * setterow). Zapisz w komentarzu, czy Hibernate potrafi taka encje zapisac i
         * odczytac (wymaga dostepu przez pola, nie settery).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ValidateColumnTypesAgainstH2 {
        /*
         * 🧪 Zadanie 27:
         * Sprawdz recznym zapytaniem SQL (INFORMATION_SCHEMA.COLUMNS w H2) DOKLADNE
         * typy kolumn, jakie Hibernate wygenerowal dla kazdego typu pola Javy (String,
         * int, double, boolean, LocalDate) - wypisz tabelaryczne zestawienie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RefactorJdbcDaoToEntityFully {
        /*
         * 🧪 Zadanie 28:
         * Wez encje odpowiadajaca jednej z tabel z _08_sql/Lesson02+ (np. users) i
         * PRZEPISZ ja jako pelnoprawna @Entity Hibernate z WSZYSTKIMI polami i
         * ograniczeniami z oryginalnego CREATE TABLE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PerformanceComparisonManyColumns {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj encje z 20 polami (szeroka tabela) i zmierz czas zapisu 50 takich
         * obiektow - porownaj w komentarzu z czasem zapisu 50 obiektow encji o 3
         * polach (waskiej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMappingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj kompletna, "produkcyjnej jakosci" encje Movie
         * laczaca WSZYSTKIE poznane w tej lekcji techniki (jawne @Table/@Column z
         * name/nullable/length/unique, roznorodne typy pol, @Transient pole
         * pomocnicze) i napisz krotki raport tekstowy (w komentarzu) opisujacy
         * KAZDA decyzje mapowania i jej uzasadnienie.
         */
        public static void main(String[] args) { }
    }
}
