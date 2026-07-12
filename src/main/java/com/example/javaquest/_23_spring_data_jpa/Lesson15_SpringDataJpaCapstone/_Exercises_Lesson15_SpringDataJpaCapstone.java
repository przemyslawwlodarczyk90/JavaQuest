package com.example.javaquest._23_spring_data_jpa.Lesson15_SpringDataJpaCapstone;

public class _Exercises_Lesson15_SpringDataJpaCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllMechanismsUsedInCapstone {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz WSZYSTKIE 14 mechanizmow Spring Data JPA
         * uzytych W kapsztonie - DLA KAZDEGO podaj lekcje, W KTOREJ byl
         * wprowadzony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddDeleteBookMethod {
        /*
         * 🧪 Zadanie 2:
         * Dodaj metode DO `PublishingService` usuwajaca ksiazke PO id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddFindByTitleQueryMethod {
        /*
         * 🧪 Zadanie 3:
         * Dodaj `BookRepository.findByTitleContainingIgnoreCase(String)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddThirdAuthorWithBooks {
        /*
         * 🧪 Zadanie 4:
         * Dodaj TRZECIEGO autora Z 3 ksiazkami PRZEZ `PublishingService`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_QueryBooksByPageCountRange {
        /*
         * 🧪 Zadanie 5:
         * Dodaj query method wyszukujacy ksiazki W ZADANYM zakresie
         * liczby stron (`findByPagesBetween`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ChangePageSizeInPaginationScenario {
        /*
         * 🧪 Zadanie 6:
         * Zmien rozmiar strony W scenariuszu 4 NA 1 I ZWERYFIKUJ, ze
         * `getTotalPages()` sie zmienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SortBooksByTitleAscending {
        /*
         * 🧪 Zadanie 7:
         * Zmien sortowanie W scenariuszu 4 NA `title` rosnaco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ObserveRollbackWithZeroPriceBook {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, ze cena `0.00` (NIE ujemna) TEZ WYWOLUJE rollback
         * (warunek `<= 0`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrintCreatedDateForAllBooks {
        /*
         * 🧪 Zadanie 9:
         * Wypisz `createdDate` DLA WSZYSTKICH ksiazek W bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_QueryFlywayHistoryForVersionCount {
        /*
         * 🧪 Zadanie 10:
         * Zweryfikuj (PRZEZ `flyway_schema_history`), ile migracji
         * zostalo ZASTOSOWANYCH W tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddThirdCountryAndFilterByIt {
        /*
         * 🧪 Zadanie 11:
         * Dodaj autora Z TRZECIEGO kraju I przefiltruj ksiazki PRZEZ
         * `Specification.hasAuthorCountry(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddPriceRangeSpecification {
        /*
         * 🧪 Zadanie 12:
         * Dodaj `Specification` `priceGreaterThanOrEqual(...)` I POLACZ
         * JA Z `priceLessThanOrEqual(...)` (przedzial cenowy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddNewProjectionInterface {
        /*
         * 🧪 Zadanie 13:
         * Dodaj NOWA projekcje `TitleOnlyProjection` (TYLKO `getTitle()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareQueryCountsBeforeAfterEntityGraph {
        /*
         * 🧪 Zadanie 14:
         * Zbierz WSZYSTKIE 3 liczniki zapytan (naiwny/JOIN FETCH/
         * @EntityGraph) DO tabeli porownawczej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddSecondEntityListenerField {
        /*
         * 🧪 Zadanie 15:
         * Dodaj auditing (4 pola) TEZ DO encji `Author`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddV2MigrationForNewColumn {
        /*
         * 🧪 Zadanie 16:
         * Dodaj `V2__add_isbn_column.sql` DO `db/migration-lesson15` I
         * dopasuj mapowanie encji `Book` (`ddl-auto=validate` MUSI dalej
         * przechodzic).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestServiceMethodInIsolationWithoutSpring {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: opisz, jak PRZETESTOWAC logike walidacji ceny W
         * `PublishingService` BEZ podnoszenia calego kontekstu Springa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddSecondAuditorForBooksImportedInBulk {
        /*
         * 🧪 Zadanie 18:
         * Zasymuluj "import hurtowy" (5 ksiazek OD JEDNEGO autora) PRZEZ
         * JEDNEGO auditora I zweryfikuj, ze WSZYSTKIE maja TEN SAM
         * `createdBy`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CombineSpecificationWithPageable {
        /*
         * 🧪 Zadanie 19:
         * Polacz `Specification` (scenariusz 7) Z `Pageable`
         * (`findAll(Specification, Pageable)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyDdlAutoValidateIsSafestChoiceHere {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij, DLACZEGO `ddl-auto=validate` (NIE
         * `create-drop`) jest JEDYNYM poprawnym wyborem W TEJ aplikacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AddReviewEntityWithManyToManyToBook {
        /*
         * 🧪 Zadanie 21:
         * Dodaj encje `Tag` W relacji `@ManyToMany` Z `Book` (powiazanie
         * Z `_12_hibernate/Lesson13`) - WLASNA migracja Flyway DLA
         * tabeli posredniczacej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RefactorToHexagonalPort {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_17_architecture/Lesson12_PortsAndAdapters` -
         * wydziel `LibraryPort` (interfejs) Z `PublishingService` jako
         * implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AddOptimisticLockingToBook {
        /*
         * 🧪 Zadanie 23:
         * Dodaj `@Version` DO `Book` (powiazanie Z
         * `_12_hibernate/Lesson25`) - zasymuluj KONFLIKT WSPOLBIEZNEJ
         * aktualizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureNPlusOnePerformanceWithLargeDataset {
        /*
         * 🧪 Zadanie 24:
         * Zaladuj 100 autorow Z 10 ksiazkami KAZDY - zmierz REALNY czas
         * naiwnego podejscia (Lesson9) NA TLE JOIN FETCH.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignPublishingServiceEventOnNewBook {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_20_spring_core/Lesson20_ApplicationEvents` -
         * opublikuj zdarzenie `BookPublishedEvent` PO KAZDYM UDANYM
         * `publishAuthorWithBooks(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareCapstoneWithHibernateChapterEquivalent {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: porownaj ILOSC kodu W TYM kapsztonie Z
         * rownowaznym scenariuszem napisanym CZYSTYM Hibernate/JPA
         * (`_12_hibernate`) - CO Spring Data JPA zaoszczedzil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AddCustomExceptionHierarchy {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj WLASNA hierarchie wyjatkow domenowych
         * (`InvalidBookPriceException` zamiast ogolnego
         * `IllegalArgumentException`) - powiazanie Z
         * `_16_clean_code/Lesson17_ExceptionDesign`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AddFlywayJavaMigrationForDataCleanup {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z Lesson14 - zaimplementuj migracje `BaseJavaMigration`
         * czyszczaca/normalizujaca istniejace dane W `books`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignFullCiCdMigrationStrategy {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zaprojektuj PELNY proces CI/CD DLA TEJ aplikacji
         * (Flyway + `ddl-auto=validate` + testy) - KIEDY migracje SA
         * uruchamiane WZGLEDEM deploya.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildExtendedCapstoneWithAllExercisesCombined {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj ROZSZERZONA wersje kapsztonu LACZACA WYNIKI Zadan
         * 2-29 (min. 5) W JEDNYM, spojnym demo Z WLASNYM raportem
         * podsumowujacym NA koniec.
         */
        public static void main(String[] args) { }
    }
}
