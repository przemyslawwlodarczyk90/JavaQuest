package com.example.javaquest._23_spring_data_jpa.Lesson01_WhatIsSpringDataJpa;

public class _Exercises_Lesson01_WhatIsSpringDataJpa {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSpringDataJpaVsHibernate {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_12_hibernate`): wyjasnij, CZYM
         * Spring Data JPA rozni sie OD "golego" Hibernate - CO
         * DOKLADNIE automatyzuje?
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnEntityAndRepository {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA encje + interfejs
         * `extends JpaRepository<T, Long>` (ZERO kodu implementacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SaveAndFindEntity {
        /*
         * 🧪 Zadanie 3:
         * Zapisz encje I odczytaj ja PO ID PRZEZ WYGENEROWANE
         * repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InspectProxyClassName {
        /*
         * 🧪 Zadanie 4:
         * Wypisz `getClass().getName()` WLASNEGO repozytorium -
         * zweryfikuj, ze to PROXY, NIE Twoja klasa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareEntityNamePitfall {
        /*
         * 🧪 Zadanie 5:
         * Powiaz z `_12_hibernate/Lesson04` - USUN `@Entity(name = "...")`
         * Z ZAGNIEZDZONEJ encji - zweryfikuj BLAD.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementCountMethod {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `repository.count()` PO zapisaniu 3 encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementDeleteById {
        /*
         * 🧪 Zadanie 7:
         * Usun encje PRZEZ `deleteById(...)` - zweryfikuj JEJ zniknieciem
         * Z `findAll()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementExistsById {
        /*
         * 🧪 Zadanie 8:
         * Uzyj `existsById(...)` PRZED I PO usunieciu encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareDdlAutoValues {
        /*
         * 🧪 Zadanie 9:
         * Wyprobuj `spring.jpa.hibernate.ddl-auto` Z wartosciami
         * `create`/`update`/`validate`/`none` - jaka ROZNICA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyRepositoryIsJustAnInterface {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, DLACZEGO repozytorium W Spring Data
         * JPA jest TYLKO interfejsem, BEZ implementacji W TWOIM kodzie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementRepositoryWithTwoEntities {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj 2 encje + 2 repozytoria W JEDNYM kontekscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementSaveAllBatch {
        /*
         * 🧪 Zadanie 12:
         * Uzyj `saveAll(List<T>)` DO zapisania WIELU encji NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementFindAllById {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `findAllById(List<Long>)` - pobierz WYBRANY podzbior
         * encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareGeneratedSqlWithShowSql {
        /*
         * 🧪 Zadanie 14:
         * Wlacz `spring.jpa.show-sql=true` - zapisz WYGENEROWANE
         * zapytania SQL DLA `save`/`findById`/`deleteById`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementDeleteAllInBatch {
        /*
         * 🧪 Zadanie 15:
         * Uzyj `deleteAllInBatch()` - porownaj Z `deleteAll()`
         * (WYDAJNOSC - JEDNO zapytanie vs WIELE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRepositoryInjectionIntoService {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_20_spring_core/Lesson10` - wstrzyknij repozytorium
         * DO WLASNEGO serwisu PRZEZ constructor injection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementEntityWithEmbeddedIdAlternative {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_12_hibernate/Lesson09` - zaimplementuj encje Z
         * `@Embeddable` polem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareInMemoryH2WithFileBasedH2 {
        /*
         * 🧪 Zadanie 18:
         * Porownaj `jdbc:h2:mem:...` Z `jdbc:h2:file:...` - KIEDY
         * dane PRZETRWAJA restart aplikacji?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureStartupTimeWithJpaVsWithout {
        /*
         * 🧪 Zadanie 19:
         * Zmierz DODATKOWY czas STARTU aplikacji Z `spring-boot-starter-
         * data-jpa` wzgledem BEZ niego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildSpringDataJpaVsHibernateCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" odpowiednikow: `Session.save()` ->
         * `repository.save()`, `session.get()` -> `repository.findById()`
         * itd.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomBaseRepositoryInterface {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY interfejs bazowy (rozszerzajacy
         * `JpaRepository`) Z DODATKOWA, WSPOLNA metoda DLA WSZYSTKICH
         * repozytoriow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRepositoryFactoryBeanCustomization {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala (dokumentacja): sprawdz, JAK dostosowac
         * `JpaRepositoryFactoryBean` - KIEDY jest to POTRZEBNE?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementMultipleDataSourcesConceptually {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala (dokumentacja): sprawdz, JAK skonfigurowac 2
         * RÓZNE zrodla danych (`@EnableJpaRepositories(basePackages=...)`)
         * W 1 aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRepositoryPerformanceComparisonWithRawHibernate {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_12_hibernate` - zmierz CZAS zapisu 1000 encji
         * PRZEZ `saveAll` (Spring Data) wzgledem RECZNEGO `Session`
         * (Hibernate).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRepositoryWithCustomIdGenerator {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_12_hibernate/Lesson05` - zaimplementuj encje Z
         * `@GeneratedValue(strategy = GenerationType.SEQUENCE)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementRepositoryAuditLogWrapper {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_19_security_basics/Lesson19` - opakuj WYWOLANIA
         * repozytorium WLASNYM serwisem logujacym KAZDA operacje
         * zapisu/usuwania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementGracefulDegradationWithoutDatabase {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj BRAK dostepnej bazy danych PRZY starcie - jaki
         * BLAD rzuca Spring Boot?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRepositoryTestWithoutFullContext {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala (zapowiedz przyszlego rozdzialu o testowaniu):
         * sprawdz, CZYM jest `@DataJpaTest` - JAK RÓZNI SIE OD
         * `@SpringBootTest`?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareSpringDataJpaWithMyBatisOrJooq {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (powiaz z `_12_hibernate/Lesson01`): porownaj
         * Spring Data JPA Z ALTERNATYWAMI (MyBatis, jOOQ) - jakie SA
         * kompromisy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMinimalCrudServiceCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj MINIMALNY, dzialajacy serwis CRUD (encja + repozytorium
         * + WLASNY serwis) - PODSUMOWANIE lekcji.
         */
        public static void main(String[] args) { }
    }
}
