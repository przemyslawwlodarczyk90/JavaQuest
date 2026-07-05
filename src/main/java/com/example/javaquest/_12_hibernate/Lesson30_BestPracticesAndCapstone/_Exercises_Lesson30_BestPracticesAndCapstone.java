package com.example.javaquest._12_hibernate.Lesson30_BestPracticesAndCapstone;

public class _Exercises_Lesson30_BestPracticesAndCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExtendCapstoneWithNewAuthor {
        /*
         * 🧪 Zadanie 1:
         * Na bazie modelu Author/Book/Loan z lekcji (H2
         * "jdbc:h2:mem:l30ex01;DB_CLOSE_DELAY=-1"): dodaj DRUGIEGO autora z 3
         * ksiazkami (cascade PERSIST, Lesson14) i zweryfikuj poprawny zapis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_IdentifyPitfallNPlusOneInCapstone {
        /*
         * 🧪 Zadanie 2:
         * W systemie z Zadania 1: odczytaj WSZYSTKICH autorow i w petli wywolaj
         * getBooks().size() dla kazdego (LAZY, Lesson15) - policz w show_sql liczbe
         * zapytan i zapisz w komentarzu, ze to demonstracja problemu N+1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FixNPlusOneWithJoinFetch {
        /*
         * 🧪 Zadanie 3:
         * Napraw problem z Zadania 2 uzywajac "join fetch" (Lesson15/19) - policz
         * zapytania ponownie i porownaj w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DemonstrateDetachedEntityPitfall {
        /*
         * 🧪 Zadanie 4:
         * Odczytaj Book, zamknij Session, zmien pole title, sprawdz w NOWEJ Session
         * ze zmiana NIE zostala zapisana (Lesson06/16) - napraw to przez merge().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddVersionFieldToLoan {
        /*
         * 🧪 Zadanie 5:
         * Dodaj @Version (Lesson25) do Loan (jesli jeszcze nie ma - patrz teoria
         * lekcji). Zasymuluj konflikt dwoch Session probujacych zwrocic TA SAMA
         * ksiazke jednoczesnie - zweryfikuj OptimisticLockException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReadOnlyReportWithBatchSize {
        /*
         * 🧪 Zadanie 6:
         * Zapisz 20 autorow po 3 ksiazki. Skonfiguruj hibernate.jdbc.batch_size=20
         * (lekcja) i zmierz czas zapisu - porownaj z zapisem BEZ tej konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareJdbcDaoHibernateForSameOperation {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj "znajdz wszystkie ksiazki konkretnego autora z jego danymi"
         * RAZ czystym JDBC (_09_jdbc), RAZ przez DAO (_10_dao), RAZ przez Hibernate
         * (join fetch) - porownaj w komentarzu liczbe linii kodu KAZDEGO podejscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ValidateBookBeforeLoan {
        /*
         * 🧪 Zadanie 8:
         * Dodaj Bean Validation (@NotNull/@Size, Lesson28) na polu borrowerName w
         * Loan. Sprobuj wypozyczyc ksiazke z pustym imieniem wypozyczajacego i
         * zapisz w komentarzu ConstraintViolationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AuditLoanHistoryWithEnvers {
        /*
         * 🧪 Zadanie 9:
         * Dodaj @Audited (Lesson29) do Loan. Wykonaj wypozyczenie i zwrot (dwie
         * zmiany) - odczytaj PELNA historie rewizji tego wypozyczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_L2CacheForRarelyChangedAuthorData {
        /*
         * 🧪 Zadanie 10:
         * Dodaj @Cacheable + @Cache(READ_ONLY) (Lesson24) do Author (dane rzadko
         * sie zmieniaja). Zweryfikuj (statystyki) trafienia L2 przy odczycie w
         * roznych Session.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullBookLoanWorkflowWithAllSafeguards {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj KOMPLETNY workflow wypozyczenia: walidacja (Zadanie 8),
         * blokada pesymistyczna NA CZAS sprawdzenia dostepnosci (Lesson26), zapis
         * Loan, audytowanie (Zadanie 9) - wszystko w JEDNEJ transakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReportMostPopularBooksWithHql {
        /*
         * 🧪 Zadanie 12:
         * Napisz raport HQL (Lesson19) "najczesciej wypozyczane ksiazki" (group by
         * book, count(loan), order by count desc) - wypisz TOP 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DynamicSearchWithCriteriaApi {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj metode "searchBooks(Session, String title, String authorName)"
         * przez Criteria API (Lesson20) z OBOMA filtrami opcjonalnymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NativeSqlForComplexAvailabilityReport {
        /*
         * 🧪 Zadanie 14:
         * Napisz native SQL (Lesson21) laczacy dane o ksiazkach i AKTYWNYCH
         * wypozyczeniach (returnDate IS NULL) w RAPORT dostepnosci - z funkcja
         * specyficzna dla H2, jesli to mozliwe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NamedQueriesForCommonOperations {
        /*
         * 🧪 Zadanie 15:
         * Przenies 3 NAJCZESCIEJ uzywane zapytania (np. findByAuthor, findAvailable,
         * findOverdue) na @NamedQuery (Lesson22) przy odpowiednich encjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_EnumStatusForLoanState {
        /*
         * 🧪 Zadanie 16:
         * Zamiast pola returnDate (nullable), dodaj enum LoanStatus (ACTIVE,
         * RETURNED, OVERDUE) z @Enumerated(STRING) (Lesson10). Zaimplementuj
         * metode obliczajaca status na podstawie dat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InheritanceForDifferentMediaTypes {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz Book na hierarchie dziedziczenia (Lesson27, SINGLE_TABLE) z
         * podklasami PhysicalBook (shelfLocation) i EBook (fileSizeMb) - zapisz po
         * jednym obiekcie kazdego typu i odczytaj polimorficznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_EmbeddableForBookMetadata {
        /*
         * 🧪 Zadanie 18:
         * Dodaj do Book @Embedded komponent BookMetadata (Lesson09: isbn,
         * publishYear, pageCount) zamiast rozrzuconych osobnych pol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ManyToManyForBookCategories {
        /*
         * 🧪 Zadanie 19:
         * Dodaj @ManyToMany (Lesson13) miedzy Book i Category. Zapisz ksiazke w 2
         * kategoriach i napisz raport "ksiazki w danej kategorii".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullPerformanceAuditOfCapstoneSystem {
        /*
         * 🧪 Zadanie 20:
         * Wlacz hibernate.generate_statistics=true na CALYM systemie z Zadan 1-19.
         * Wykonaj typowy scenariusz (10 wypozyczen, 5 raportow) i wypisz KOMPLETNE
         * statystyki (query count, cache hits, entity loads) na koniec.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLibraryManagementSystemAllFeatures {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNY system biblioteczny LACZACY WSZYSTKIE elementy Zadan
         * 1-19 w JEDNYM, spojnym programie: dziedziczenie (Zadanie 17), @Embedded
         * metadane (Zadanie 18), @ManyToMany kategorie (Zadanie 19), audytowanie
         * (Zadanie 9), L2 cache (Zadanie 10), i pesymistyczne blokowanie (Zadanie 11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ServiceLayerArchitectureForCapstone {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj WARSTWOWA architekture dla systemu z Zadania 21: repozytoria
         * (AuthorRepository, BookRepository, LoanRepository) + serwisy
         * (LibraryService orkiestrujacy operacje miedzy repozytoriami) - z jawnym
         * zarzadzaniem transakcjami na poziomie serwisu (Lesson08).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConcurrencyStressTestFullSystem {
        /*
         * 🧪 Zadanie 23:
         * Uruchom 10 watkow (jak w _05_multithreading) jednoczesnie probujacych
         * wypozyczyc/zwracac ROZNE ksiazki w systemie z Zadania 21 - zweryfikuj
         * poprawnosc koncowego stanu (zadna ksiazka nie jest "podwojnie wypozyczona").
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MigrationPlanFromJdbcDaoSystemToHibernate {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: napisz SZCZEGOLOWY plan migracji (min. 6 krokow, w
         * komentarzu) hipotetycznego, istniejacego systemu bibliotecznego opartego
         * na czystym JDBC/DAO (_09_jdbc/_10_dao) NA Hibernate - z ryzykami i
         * kolejnoscia dzialan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PerformanceBenchmarkFullReport {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj KOMPLETNY benchmark systemu z Zadania 21: 1000 ksiazek, 5000
         * wypozyczen, 10 typowych raportow - zmierz czas KAZDEJ operacji I calkowity
         * czas, wypisz PELNY raport wydajnosciowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ProductionReadinessChecklist {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: napisz "checklist" (min. 8 punktow, w komentarzu) rzeczy do
         * sprawdzenia PRZED wdrozeniem systemu z Zadania 21 na produkcje (hbm2ddl.auto
         * powinno byc validate/none + Flyway, L2 cache rozproszony jesli multi-instance,
         * connection pooling przez HikariCP, itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExtendCapstoneWithNewFeatureFromScratch {
        /*
         * 🧪 Zadanie 27:
         * Dodaj DO systemu z Zadania 21 CALKOWICIE NOWA funkcje (np. rezerwacje
         * ksiazek - Reservation, oddzielna od Loan) - zaprojektuj I zaimplementuj
         * JEJ encje, relacje, i logike biznesowa OD ZERA, stosujac wzorce poznane w
         * calym rozdziale.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CodeReviewOfOwnCapstoneImplementation {
        /*
         * 🧪 Zadanie 28:
         * Przejrzyj WLASNA implementacje z Zadania 21 pod katem WSZYSTKICH pulapek z
         * lekcji (tabela z teorii) - napisz w komentarzu, KTORE z nich faktycznie
         * napotkales i jak je naprawiles.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnHibernateJourneySummary {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz OSOBISTE podsumowanie (min. 8 zdan, w komentarzu)
         * calego rozdzialu _12_hibernate - co bylo NAJBARDZIEJ zaskakujace, ktora
         * lekcja byla NAJTRUDNIEJSZA, i jak Twoje rozumienie Hibernate zmienilo sie
         * od Lesson01 do teraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCapstoneFinalSubmission {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie calego rozdzialu: dopracuj system z Zadania 21+27
         * (biblioteka + rezerwacje) do stanu "gotowego do prezentacji" - z pelnym
         * scenariuszem demo w main() pokazujacym KAZDA poznana technike w akcji,
         * czytelnymi komunikatami println na kazdym kroku, i podsumowaniem
         * statystyk Hibernate na koniec (jak Zadanie 20).
         */
        public static void main(String[] args) { }
    }
}
