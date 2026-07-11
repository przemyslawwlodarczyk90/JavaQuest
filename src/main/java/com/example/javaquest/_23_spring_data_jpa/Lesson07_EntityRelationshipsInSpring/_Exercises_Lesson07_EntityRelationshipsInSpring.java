package com.example.javaquest._23_spring_data_jpa.Lesson07_EntityRelationshipsInSpring;

public class _Exercises_Lesson07_EntityRelationshipsInSpring {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRelationsUnchangedFromHibernate {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_12_hibernate/Lesson11-13`): wyjasnij,
         * CO Spring Data JPA ZMIENIA, a CO POZOSTAJE IDENTYCZNE W
         * mapowaniu relacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnOneToManyRelationship {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA relacje `@OneToMany`/`@ManyToOne` (2
         * encje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementCascadeSave {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj `cascade = CascadeType.ALL` - zapisz RODZICA Z
         * DZIECMI JEDNYM wywolaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementNestedPropertyQueryMethodOwn {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj WLASNA metode `findByPowiazanaEncja_Pole(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementManyToManyRelationship {
        /*
         * 🧪 Zadanie 5:
         * Powiaz z `_12_hibernate/Lesson13` - zaimplementuj
         * `@ManyToMany` (2 encje + tabela posrednia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementOrphanRemoval {
        /*
         * 🧪 Zadanie 6:
         * Dodaj `orphanRemoval = true` - usun DZIECKO Z KOLEKCJI
         * rodzica I zapisz - zweryfikuj USUNIECIE Z bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareEagerVsLazyFetchDefault {
        /*
         * 🧪 Zadanie 7:
         * Powiaz z `_12_hibernate/Lesson15` - zweryfikuj DOMYSLNY
         * `FetchType` DLA `@ManyToOne` (EAGER) I `@OneToMany` (LAZY).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementBidirectionalRelationshipHelperMethod {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj metode POMOCNICZA (`addChild(...)`) UTRZYMUJACA
         * SPOJNOSC relacji DWUKIERUNKOWEJ (OBIE strony NARAZ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementCountByRelatedEntity {
        /*
         * 🧪 Zadanie 9:
         * Powiaz z Lesson04 - zaimplementuj `countByPowiazanaEncja_Pole(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyMappedByMatters {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, CO ROBI `mappedBy` I DLACZEGO
         * DOKLADNIE JEDNA strona relacji MUSI go MIEC.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementOneToOneRelationship {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z `_12_hibernate/Lesson11` - zaimplementuj `@OneToOne`
         * (wspoldzielony PK LUB osobny FK).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementQueryWithJoinFetch {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z Lesson05/Lesson09 - zaimplementuj `@Query` Z `JOIN
         * FETCH`, ROZWIAZUJACY problem N+1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDeepNestedPropertyNavigation {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj query method Z 2-POZIOMOWA nawigacja
         * (`findByA_B_Pole(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSaveChildIndependently {
        /*
         * 🧪 Zadanie 14:
         * Zapisz DZIECKO NIEZALEZNIE (WLASNE repozytorium, BEZ
         * kaskady) - powiaz Z RODZICEM PO fakcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCircularReferenceJsonHandling {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_22_spring_web/Lesson07` (DTO) - zapobiegnij
         * NIESKONCZONEJ petli JSON PRZY serializacji encji Z relacja
         * DWUKIERUNKOWA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRelationshipWithCompositeKey {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_12_hibernate/Lesson09` - zaimplementuj relacje
         * WYKORZYSTUJACA `@EmbeddedId`/klucz zlozony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementRemoveRelationshipWithoutDeletingEntity {
        /*
         * 🧪 Zadanie 17:
         * Rozlacz relacje (`order.customer = null`) BEZ usuwania
         * zamowienia - zweryfikuj WYNIK.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareCascadePersistVsCascadeAll {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_12_hibernate/Lesson14` - porownaj `CascadeType.PERSIST`
         * Z `CascadeType.ALL` - RÓZNICA W zachowaniu PRZY `delete`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureCascadeSavePerformanceWithManyChildren {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas zapisu rodzica Z 100 dziecmi PRZEZ kaskade.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildEntityRelationshipsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" relacji (OneToMany/ManyToOne/
         * ManyToMany/OneToOne) Z DOMYSLNYM fetch/cascade.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementSelfReferencingRelationship {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj encje Z relacja SAMA DO SIEBIE (np. `Employee`
         * Z `manager`/`subordinates`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementPolymorphicRelationshipConceptually {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_12_hibernate/Lesson27` (dziedziczenie) -
         * zaimplementuj relacje DO encji BAZOWEJ Z hierarchii
         * dziedziczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementBatchFetchSizeOptimization {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_12_hibernate/Lesson15` - skonfiguruj
         * `@BatchSize`/`hibernate.default_batch_fetch_size` - powiaz Z
         * Lesson09 (N+1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRelationshipAuditTrail {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z Lesson13 (Auditing) - sledz ZMIANY W relacji (KIEDY
         * dziecko zostalo DODANE/USUNIETE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRelationshipConsistencyValidation {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WALIDACJE SPOJNOSCI relacji PRZED zapisem
         * (`@PrePersist`, powiaz Z `_12_hibernate/Lesson16`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementManyToManyWithExtraColumns {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_12_hibernate/Lesson13` - przeksztalc
         * `@ManyToMany` NA jawna encje LACZACA (Z DODATKOWYMI
         * kolumnami, np. `dataPolaczenia`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementLazyLoadingOutsideSessionErrorDemo {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_12_hibernate/Lesson15` - zademonstruj
         * `LazyInitializationException` PRZY probie dostepu DO
         * kolekcji LAZY PO zamknieciu sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDeepGraphSavePerformanceProfile {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_15_jvm_internals` - zmierz I PROFILUJ zapis
         * GLEBOKIEGO grafu obiektow (3+ poziomy relacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareRelationshipModelingApproaches {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj MODELOWANIE relacji PRZEZ JPA
         * (obiektowe) Z RECZNYM JOIN W `_09_jdbc` - jakie SA
         * kompromisy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteOrderManagementDomainCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY model domenowy (Customer/Order/Product,
         * WIELE relacji NARAZ) - jeden spojny, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
