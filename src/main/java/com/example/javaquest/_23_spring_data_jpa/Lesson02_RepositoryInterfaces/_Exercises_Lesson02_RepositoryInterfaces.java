package com.example.javaquest._23_spring_data_jpa.Lesson02_RepositoryInterfaces;

public class _Exercises_Lesson02_RepositoryInterfaces {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRepositoryHierarchy {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij HIERARCHIE Repository -> CrudRepository
         * -> PagingAndSortingRepository -> JpaRepository.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementCrudRepositoryOnly {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNE repozytorium dziedziczace TYLKO PO
         * `CrudRepository`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementJpaRepositoryOnly {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNE repozytorium dziedziczace PO
         * `JpaRepository`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareFindAllReturnTypes {
        /*
         * 🧪 Zadanie 4:
         * Porownaj TYP zwracany `findAll()` W `CrudRepository`
         * (`Iterable<T>`) Z `JpaRepository` (`List<T>`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementSaveAndFlushDemo {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj WLASNE uzycie `saveAndFlush(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementDeleteInBatch {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `deleteAllInBatch()` - powiaz Z Lesson01, Zadanie 15.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementGetReferenceById {
        /*
         * 🧪 Zadanie 7:
         * Uzyj `getReferenceById(...)` (LENIWA referencja, BEZ zapytania
         * DO bazy) - porownaj Z `findById(...)` (ZACHLANNE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementMarkerInterfaceDemo {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj interfejs dziedziczacy TYLKO PO `Repository<T, ID>`
         * (marker) - dodaj WLASNA metode `findByTitle(...)` - zweryfikuj
         * DZIALANIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareSpringData3MigrationPattern {
        /*
         * 🧪 Zadanie 9:
         * Powiaz z teoria (RÓZNICA wersji) - wyjasnij, DLACZEGO
         * `PagingAndSortingRepository` sam W SOBIE juz NIE WYSTARCZA W
         * Spring Data 3+.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyJpaRepositoryIsDefaultChoice {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego `JpaRepository` jest
         * ZWYKLE NAJLEPSZYM domyslnym wyborem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementListCrudRepositoryVariant {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj repozytorium dziedziczace PO `ListCrudRepository`
         * (Spring Data 3+) - porownaj Z `CrudRepository`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementListPagingAndSortingRepositoryVariant {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj repozytorium dziedziczace PO
         * `ListPagingAndSortingRepository`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCustomRepositoryBaseInterface {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj WLASNY interfejs bazowy laczacy `JpaRepository`
         * + WLASNA, WSPOLNA metode (np. `findActive()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRepositoryWithoutJpaRepositoryAtAll {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj repozytorium dziedziczace TYLKO PO
         * `CrudRepository` + `PagingAndSortingRepository` (BEZ
         * `JpaRepository`) - jakich metod BRAKUJE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareGenericVsJpaSpecificMethods {
        /*
         * 🧪 Zadanie 15:
         * Zestaw liste metod GENERYCZNYCH (dzialajacych DLA WSZYSTKICH
         * baz Spring Data) Z metodami SPECYFICZNYMI DLA JPA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementFlushBehaviorDemo {
        /*
         * 🧪 Zadanie 16:
         * Zademonstruj RÓZNICE `flush()` (Wymuszenie zapisu BEZ
         * commitu) - powiaz z `_12_hibernate/Lesson17_DirtyChecking`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementRepositoryMethodOverriding {
        /*
         * 🧪 Zadanie 17:
         * PRZESLON WBUDOWANA metode (np. `findAll()`) WLASNA
         * implementacja (np. filtrujaca USUNIETE rekordy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementMultipleRepositoriesSharedTransaction {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z Lesson08 - uzyj 2 RÓZNYCH repozytoriow W JEDNEJ
         * metodzie `@Transactional`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureProxyCreationOverhead {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas TWORZENIA proxy DLA 20 RÓZNYCH interfejsow
         * repozytoriow PRZY starcie aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildRepositoryInterfaceHierarchyCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" HIERARCHII interfejsow Z metodami
         * DODANYMI NA KAZDYM poziomie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomRepositoryImplementationFragment {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY "fragment" (interfejs `XxxCustom` +
         * implementacja `XxxCustomImpl`) DOLACZONY DO glownego
         * repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRepositoryWithEntityManagerInjected {
        /*
         * 🧪 Zadanie 22:
         * W implementacji fragmentu Z Zadania 21, wstrzyknij
         * `EntityManager` - powiaz Z `_12_hibernate/Lesson07`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementRepositoryEventListeners {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj `@RepositoryEventHandler`/listenery zdarzen
         * repozytorium (`BeforeSaveEvent` itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementQueryByExampleDemo {
        /*
         * 🧪 Zadanie 24:
         * Uzyj `QueryByExampleExecutor` (CZESC `JpaRepository`) -
         * wyszukaj PO "przykladowym" obiekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRepositoryWithCustomBaseClass {
        /*
         * 🧪 Zadanie 25:
         * Skonfiguruj `@EnableJpaRepositories(repositoryBaseClass = ...)`
         * - WLASNA klasa bazowa DLA WSZYSTKICH repozytoriow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSoftDeleteViaRepositoryOverride {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj "miekkie usuwanie" (flaga `deleted`, NIE realne
         * `DELETE`) PRZEZ NADPISANIE `deleteById(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRepositoryMetricsWrapper {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_21_spring_boot/Lesson13` - zbierz metryki (czas
         * KAZDEGO wywolania repozytorium) PRZEZ AOP (`_20_spring_core/
         * Lesson21`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementReactiveRepositoryComparisonConceptually {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala (dokumentacja): sprawdz `ReactiveCrudRepository`
         * (Spring Data R2DBC) - CZYM RÓZNI SIE OD `JpaRepository`?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareRepositoryPatternWithGenericDaoFromChapter10 {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_10_dao` - porownaj generyczne DAO (RECZNIE
         * napisane) Z Spring Data repository - jakie SA PODOBIENSTWA
         * WZORCA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteRepositoryLayerArchitectureCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNA warstwe repozytoriow DLA 3 encji - WSPOLNY
         * interfejs bazowy (Zadanie 13) + fragment WLASNY (Zadanie 21) -
         * jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
