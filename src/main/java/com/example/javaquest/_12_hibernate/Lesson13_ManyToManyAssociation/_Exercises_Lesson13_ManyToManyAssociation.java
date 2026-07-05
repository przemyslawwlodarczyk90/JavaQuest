package com.example.javaquest._12_hibernate.Lesson13_ManyToManyAssociation;

public class _Exercises_Lesson13_ManyToManyAssociation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicManyToMany {
        /*
         * 🧪 Zadanie 1:
         * Utworz Actor (id, name, @ManyToMany @JoinTable) i Movie (id, title,
         * @ManyToMany(mappedBy="movies")) na H2
         * ("jdbc:h2:mem:l13ex01;DB_CLOSE_DELAY=-1"). Zapisz aktora grajacego w 2
         * filmach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifyJoinTableStructure {
        /*
         * 🧪 Zadanie 2:
         * Sprawdz recznym SQL (INFORMATION_SCHEMA) strukture wygenerowanej tabeli
         * posredniczacej - zapisz w komentarzu jej nazwe i kolumny (2 klucze obce).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ManyActorsInOneMovie {
        /*
         * 🧪 Zadanie 3:
         * Zapisz JEDEN film z 3 roznymi aktorami. Odczytaj film i wypisz wszystkich
         * przypisanych aktorow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OneActorInManyMovies {
        /*
         * 🧪 Zadanie 4:
         * Zapisz JEDNEGO aktora grajacego w 3 roznych filmach. Odczytaj aktora i
         * wypisz wszystkie jego filmy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_QueryFromInverseSide {
        /*
         * 🧪 Zadanie 5:
         * Zapisz relacje aktor-film. Odczytaj z INNEJ strony niz zapisywales (jesli
         * zapisywales przez Actor, teraz odczytaj przez Movie) i sprawdz, ze relacja
         * jest widoczna z OBU stron.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RemoveOneAssociationNotBothEntities {
        /*
         * 🧪 Zadanie 6:
         * Usun TYLKO powiazanie miedzy konkretnym aktorem i filmem (usun z kolekcji,
         * NIE usuwaj samego aktora ani filmu) - zweryfikuj, ze OBIE encje wciaz
         * istnieja, ale relacja zniknela.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_EmptyManyToManyCollection {
        /*
         * 🧪 Zadanie 7:
         * Zapisz aktora BEZ zadnego filmu. Odczytaj go i zweryfikuj, ze getMovies()
         * zwraca PUSTY zbior (nie null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CustomJoinTableName {
        /*
         * 🧪 Zadanie 8:
         * Ustaw @JoinTable(name = "actor_movie_cast") (wlasna nazwa tabeli
         * posredniczacej). Sprawdz recznym SQL, ze zostala uzyta dokladnie ta nazwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DuplicateAssociationPrevention {
        /*
         * 🧪 Zadanie 9:
         * Sprobuj dodac TEGO SAMEGO aktora do TEGO SAMEGO filmu DWUKROTNIE (Set,
         * wiec duplikat powinien zostac zignorowany w pamieci). Zweryfikuj recznym
         * SQL, ze w tabeli posredniczacej jest TYLKO JEDEN wiersz dla tej pary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SimpleQueryCountAssociations {
        /*
         * 🧪 Zadanie 10:
         * Zapisz 5 aktorow i 3 filmy z rozmaitymi powiazaniami. Napisz zapytanie HQL
         * (podglad przed Lesson18) zliczajace CALKOWITA liczbe wierszy w tabeli
         * posredniczacej.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorToExplicitJoinEntity {
        /*
         * 🧪 Zadanie 11:
         * Przebuduj Actor-Movie (@ManyToMany) na jawna encje posredniczaca "Role"
         * (@ManyToOne do obu + pole "characterName") - zademonstruj zapis TEJ SAMEJ
         * pary aktor-film, ale TERAZ z dodatkowym atrybutem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_QueryThroughJoinEntity {
        /*
         * 🧪 Zadanie 12:
         * Dla modelu z Zadania 11: napisz HQL znajdujacy WSZYSTKIE role (Role) danego
         * aktora WRAZ z nazwa granej postaci (characterName) i tytulem filmu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_OwningSideManagementInBidirectional {
        /*
         * 🧪 Zadanie 13:
         * Dodaj metody pomocnicze addMovie(Actor, Movie)/removeMovie(...) do klasy
         * Actor (strona wlascicielska), aktualizujace OBIE kolekcje (u Actor i u
         * Movie) - jak w Lesson12, ale dla @ManyToMany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ManyToManyWithThreeEntities {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj DWIE niezalezne relacje @ManyToMany na jednej encji Movie: Movie<->Actor
         * ORAZ Movie<->Genre (kategoria filmowa). Zapisz film z 2 aktorami i 3 gatunkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CascadePersistOnManyToMany {
        /*
         * 🧪 Zadanie 15:
         * Z cascade = CascadeType.PERSIST na @ManyToMany: zapisz NOWY film z 2 NOWYMI
         * aktorami JEDNYM session.persist(movie) - bez recznego persist() na kazdym
         * aktorze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ManyToManySelfReferencing {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj encje Person z SAMOODNOSZACYM SIE @ManyToMany "friends" (osoba moze
         * miec wiele znajomych, tez bedacych Person). Zapisz 3 osoby z wzajemnymi
         * relacjami znajomosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_QueryMoviesByActorNameHql {
        /*
         * 🧪 Zadanie 17:
         * Napisz HQL "select m from Movie m join m.actors a where a.name = :name"
         * zwracajacy wszystkie filmy KONKRETNEGO aktora po jego imieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RemoveActorCascadesFromJoinTableOnly {
        /*
         * 🧪 Zadanie 18:
         * Usun Actor bez cascade REMOVE na filmy. Zweryfikuj recznym SQL, ze wpisy w
         * tabeli posredniczacej DOTYCZACE tego aktora zostaly usuniete (Hibernate
         * sam czysci tabele posredniczaca), ale filmy POZOSTALY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ManyToManyWithOrderColumn {
        /*
         * 🧪 Zadanie 19:
         * Dodaj @OrderColumn (dodatkowa kolumna w tabeli posredniczacej trzymajaca
         * INDEKS) zamiast zwyklego Set, zeby zachowac KOLEJNOSC dodawania aktorow do
         * filmu. Zweryfikuj, ze kolejnosc jest zachowana po odczycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareSetVsListForManyToMany {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj DWIE wersje relacji Movie<->Actor - jedna z Set (bez gwarancji
         * kolejnosci, ochrona przed duplikatami), druga z List + @OrderColumn
         * (Zadanie 19) - porownaj w komentarzu, kiedy ktore podejscie jest lepsze.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCastingSystemWithRoles {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNY system castingowy: Movie, Actor, Role (encja
         * posredniczaca z characterName i salary) - zapisz film z 3 aktorami, kazdy
         * z INNA postacia i wynagrodzeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReportTotalSalaryPerMovie {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21: napisz raport HQL (podglad przed Lesson19)
         * sumujacy WYNAGRODZENIA wszystkich rol w KAZDYM filmie (group by).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MigrateManyToManyToJoinEntityWithData {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj MIGRACJE: zacznij od czystego @ManyToMany (Actor<->Movie, jak w
         * Zadaniu 1), zapisz kilka par, potem "przelacz" na jawna encje Role
         * (Zadanie 11) - napisz w komentarzu SQL potrzebny do przeniesienia
         * istniejacych danych z tabeli posredniczacej do nowej tabeli Role.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ConcurrentAdditionsToSameJoinTable {
        /*
         * 🧪 Zadanie 24:
         * Uruchom DWA watki (jak w _05_multithreading), kazdy w OSOBNEJ Session
         * dodajacy INNEGO aktora do TEGO SAMEGO filmu jednoczesnie - zweryfikuj, ze
         * OBAJ aktorzy koncowo sa przypisani (bez utraty zapisu w tabeli
         * posredniczacej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ManyToManyWithFilterCriteria {
        /*
         * 🧪 Zadanie 25:
         * Napisz zapytanie HQL znajdujace WSZYSTKICH aktorow, ktorzy GRALI w
         * filmach OBU podanych gatunkow naraz (Zadanie 14, Movie<->Genre) - wymaga
         * podzapytania lub podwojnego JOIN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PerformanceManyToManyVsJoinEntity {
        /*
         * 🧪 Zadanie 26:
         * Zmierz czas zapisu 100 par aktor-film RAZ przez czysty @ManyToMany, RAZ
         * przez jawna encje Role (Zadanie 11, z dodatkowymi polami) - zapisz oba
         * czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GraphOfMutualFriendsQuery {
        /*
         * 🧪 Zadanie 27:
         * Dla Person z samoodnoszacym @ManyToMany "friends" (Zadanie 16): napisz
         * zapytanie znajdujace WSPOLNYCH znajomych DWOCH konkretnych osob (osoby
         * bedace znajomymi OBU naraz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BusinessRuleMaxActorsPerMovie {
        /*
         * 🧪 Zadanie 28:
         * Dodaj do metody addActor() (Zadanie 13) WALIDACJE biznesowa: film moze miec
         * MAKSYMALNIE 15 aktorow w obsadzie - rzuc wyjatek przy probie przekroczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnManyToManyVsJoinEntityTradeoffs {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, kiedy czysty @ManyToMany wystarcza, a kiedy
         * KONIECZNIE trzeba przejsc na jawna encje posredniczaca - z konkretnym
         * przykladem z Twojego doswiadczenia lub wyobrazonego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullManyToManyCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system filmowy laczacy WSZYSTKIE
         * techniki tej lekcji: Movie<->Actor przez jawna Role (z characterName,
         * salary), Movie<->Genre przez czysty @ManyToMany, metody pomocnicze
         * utrzymujace spojnosc, oraz raport agregujacy (Zadanie 22) -
         * zademonstruj pelny scenariusz obsadzenia i analizy filmu.
         */
        public static void main(String[] args) { }
    }
}
