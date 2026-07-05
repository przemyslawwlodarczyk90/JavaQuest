package com.example.javaquest._12_hibernate.Lesson05_PrimaryKeyGeneration;

public class _Exercises_Lesson05_PrimaryKeyGeneration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IdentityStrategyBasic {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Ticket z @GeneratedValue(strategy = GenerationType.IDENTITY) na
         * H2 ("jdbc:h2:mem:l05ex01;DB_CLOSE_DELAY=-1"). Zapisz 3 obiekty i wypisz ich
         * kolejne, automatycznie wygenerowane id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SequenceStrategyBasic {
        /*
         * 🧪 Zadanie 2:
         * Ta sama encja, ale z @GeneratedValue(strategy = SEQUENCE) + @SequenceGenerator.
         * Zapisz 3 obiekty i sprawdz w show_sql, ze Hibernate uzyl "select next value
         * for..." przed kazdym INSERT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TableStrategyBasic {
        /*
         * 🧪 Zadanie 3:
         * Ta sama encja z @GeneratedValue(strategy = TABLE). Zapisz obiekt i sprawdz
         * recznym SQL, ze powstala dodatkowa tabela pomocnicza (domyslnie
         * "hibernate_sequences" lub podobna) trzymajaca licznik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UuidStrategyBasic {
        /*
         * 🧪 Zadanie 4:
         * Encja z polem @Id typu UUID + @UuidGenerator. Zapisz 2 obiekty i wypisz ich
         * wygenerowane UUID - potwierdz w komentarzu, ze sa rozne i nie sa liczbami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ManualKeyAssignment {
        /*
         * 🧪 Zadanie 5:
         * Encja Country z reczie przypisanym kluczem @Id typu String (kod ISO, np.
         * "PL", "DE") - BEZ @GeneratedValue. Zapisz 3 kraje z reczie ustawionymi kodami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentityGapsAfterRollback {
        /*
         * 🧪 Zadanie 6:
         * Z encja IDENTITY: zapisz obiekt, sprawdz jego id, potem zapisz DRUGI
         * obiekt i wywolaj rollback ZAMIAST commit. Zapisz TRZECI obiekt i sprawdz w
         * komentarzu, czy w numeracji id powstala "dziura" (typowe dla IDENTITY/AUTO_INCREMENT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareGeneratedIdValues {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj DWIE osobne encje - jedna z IDENTITY, jedna z SEQUENCE - zapisz po 5
         * obiektow kazdej i porownaj w komentarzu wzorzec numeracji id (czy sa
         * identyczne, czy roznia sie np. przy usunieciu wiersza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IdNotNullAfterPersist {
        /*
         * 🧪 Zadanie 8:
         * Zapisz obiekt z @GeneratedValue i sprawdz (assert/if) ze POLE id jest
         * rozne od null NATYCHMIAST po wywolaniu session.persist() (przed commit) -
         * zapisz w komentarzu, dla ktorej strategii to dziala od razu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SequenceAllocationSizeExperiment {
        /*
         * 🧪 Zadanie 9:
         * Ustaw @SequenceGenerator(allocationSize = 10). Zapisz 3 obiekty i sprawdz w
         * show_sql, ile razy Hibernate FAKTYCZNIE wykonal "select next value for..."
         * (mniej niz 3 razy, bo pobiera pule id naraz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NaturalKeyValidationBeforePersist {
        /*
         * 🧪 Zadanie 10:
         * Dla encji z kluczem naturalnym (Zadanie 5): dodaj RECZNA walidacje (np.
         * dlugosc kodu ISO = dokladnie 2 znaki) PRZED session.persist(), rzucajaca
         * IllegalArgumentException dla nieprawidlowego kodu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BatchInsertsWithSequence {
        /*
         * 🧪 Zadanie 11:
         * Skonfiguruj hibernate.jdbc.batch_size=20 razem z encja SEQUENCE
         * (allocationSize=20). Zapisz 100 obiektow w petli i sprawdz w show_sql, ze
         * INSERT-y zostaly zgrupowane w batch (mniej "round-tripow" do bazy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BatchInsertsWithIdentityLimitation {
        /*
         * 🧪 Zadanie 12:
         * Ten sam test co Zadanie 11, ale z encja IDENTITY. Zapisz w komentarzu
         * obserwacje - IDENTITY NIE pozwala na prawdziwe batchowanie (kazdy INSERT
         * musi wykonac sie od razu, zeby poznac wygenerowane id).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UuidAsForeignKeyReference {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj DWIE powiazane encje (bez pelnego @ManyToOne, wystarczy pole
         * "parentId" typu UUID) - jedna z kluczem UUID, druga trzymajaca reczie ten
         * UUID jako "wskaznik". Zapisz oba obiekty i zweryfikuj powiazanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SequenceSharedBetweenTwoEntities {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj DWIE rozne encje uzywajace TEJ SAMEJ nazwy sekwencji
         * (sequenceName) w @SequenceGenerator. Zapisz po 2 obiekty kazdej i sprawdz w
         * komentarzu, czy ich id "dziela" wspolna numeracje (bez kolizji, ale bez
         * ciaglosci per-encja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CustomSequenceStartValue {
        /*
         * 🧪 Zadanie 15:
         * Ustaw @SequenceGenerator(initialValue = 1000). Zapisz pierwszy obiekt i
         * zweryfikuj, ze jego id zaczyna sie od 1000 (a nie od 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TableGeneratorCustomTableName {
        /*
         * 🧪 Zadanie 16:
         * Skonfiguruj @TableGenerator z wlasna nazwa tabeli (np. "id_generator") i
         * @GeneratedValue(strategy = TABLE, generator = "..."). Sprawdz recznym SQL
         * zawartosc tej tabeli po kilku zapisach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MixedStrategiesInOneSessionFactory {
        /*
         * 🧪 Zadanie 17:
         * Zarejestruj w JEDNEJ SessionFactory 4 rozne encje, kazda z INNA strategia
         * generowania klucza (IDENTITY, SEQUENCE, TABLE, UUID). Zapisz po jednym
         * obiekcie kazdej i wypisz wszystkie wygenerowane id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UuidCollisionProbabilityDiscussion {
        /*
         * 🧪 Zadanie 18:
         * Wygeneruj (bez bazy danych) 1 000 000 losowych UUID (java.util.UUID.randomUUID())
         * w petli i sprawdz (Set<UUID>), czy wystapila jakakolwiek kolizja - zapisz
         * wynik w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_IdentityVsSequencePerformanceBenchmark {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas zapisu 200 obiektow: raz z IDENTITY, raz z SEQUENCE
         * (allocationSize=50) + batch_size=50. Zapisz oba czasy w komentarzu i
         * wyjasnij zaobserwowana roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NaturalKeyUniquenessConstraint {
        /*
         * 🧪 Zadanie 20:
         * Dla encji z kluczem naturalnym (kod ISO kraju): sprobuj zapisac DWA obiekty
         * z TYM SAMYM kodem. Zapisz w komentarzu blad naruszenia klucza glownego
         * (PRIMARY KEY violation).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CustomIdGeneratorImplementation {
        /*
         * 🧪 Zadanie 21:
         * Napisz WLASNA klase implementujaca org.hibernate.id.IdentifierGenerator
         * (np. generujaca id w formacie "TCK-00001") i uzyj jej przez
         * @GenericGenerator/@GeneratedValue(generator=...).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompositeNaturalKeyWithEmbeddedId {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj encje z ZLOZONYM kluczem naturalnym (np. OrderLine z kluczem
         * (orderId, lineNumber)) przez @EmbeddedId + osobna klase @Embeddable
         * reprezentujaca ten klucz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SequencePerEntityVsShared {
        /*
         * 🧪 Zadanie 23:
         * Porownaj w praktyce DWIE konfiguracje: (a) kazda encja ma WLASNA sekwencje,
         * (b) wszystkie encje dziela JEDNA sekwencje. Zapisz po 5 obiektow kazdego
         * podejscia i porownaj w komentarzu zuzycie numeracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MigrationFromIdentityToSequence {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj migracje istniejacej tabeli z IDENTITY na SEQUENCE: zapisz kilka
         * wierszy z IDENTITY, potem "przelacz" encje na SEQUENCE z initialValue
         * ustawionym POWYZEJ najwyzszego istniejacego id - zapisz kolejny wiersz i
         * zweryfikuj brak kolizji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DistributedIdGenerationSimulation {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj 2 "niezalezne instancje aplikacji" (2 osobne watki, KAZDY
         * generujacy UUID bez koordynacji z drugim) zapisujace obiekty do TEJ SAMEJ
         * bazy - zweryfikuj brak konfliktow kluczy (dowod na przydatnosc UUID w
         * systemach rozproszonych, patrz _05_multithreading dla wzorca watkow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_IndexSizeComparisonDiscussion {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wypisz porownanie (min. 3 punkty) rozmiaru indeksu klucza
         * glownego dla BIGINT (8 bajtow) vs UUID (16 bajtow) przy tabeli z milionem
         * wierszy - jak wplywa to na wydajnosc JOIN-ow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SequenceGapOnCrash {
        /*
         * 🧪 Zadanie 27:
         * Z encja SEQUENCE (allocationSize=20): zapisz JEDEN obiekt (Hibernate
         * pobiera od razu pule 20 id), "zrestartuj" SessionFactory (zamknij i
         * otworz nowa) i zapisz KOLEJNY obiekt - zapisz w komentarzu, czy powstala
         * "dziura" w numeracji (typowe zachowanie sekwencji z pulami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_HybridKeyStrategySelection {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (kod + komentarz) system z DWOMA typami encji: "wewnetrzne"
         * (SEQUENCE, bo wydajnosc wazniejsza) i "publiczne API" (UUID, bo klucze nie
         * moga zdradzac liczby rekordow konkurencji) - zaimplementuj po jednym
         * przykladzie kazdego typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullBenchmarkAllStrategies {
        /*
         * 🧪 Zadanie 29:
         * Zmierz czas zapisu 300 obiektow dla WSZYSTKICH 4 strategii (IDENTITY,
         * SEQUENCE+batch, TABLE, UUID) i wypisz tabele porownawcza czasow w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullKeyGenerationCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj system z 3 encjami reprezentujacymi 3 rozne,
         * uzasadnione wybory strategii klucza (np. Order=SEQUENCE dla wydajnosci,
         * ApiToken=UUID dla bezpieczenstwa, CountryCode=reczny klucz naturalny) -
         * napisz w komentarzu uzasadnienie KAZDEGO wyboru.
         */
        public static void main(String[] args) { }
    }
}
