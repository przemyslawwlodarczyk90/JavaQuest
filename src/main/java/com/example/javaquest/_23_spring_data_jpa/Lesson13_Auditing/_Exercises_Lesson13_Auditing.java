package com.example.javaquest._23_spring_data_jpa.Lesson13_Auditing;

public class _Exercises_Lesson13_Auditing {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatJpaAuditingTracks {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, JAKIE 4 pola metadanych potrafi
         * automatycznie wypelnic JPA Auditing.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddEntityListenersAnnotation {
        /*
         * 🧪 Zadanie 2:
         * Dodaj `@EntityListeners(AuditingEntityListener.class)` DO
         * WLASNEJ encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddCreatedDateField {
        /*
         * 🧪 Zadanie 3:
         * Dodaj pole `@CreatedDate` typu `Instant` DO encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddLastModifiedDateField {
        /*
         * 🧪 Zadanie 4:
         * Dodaj pole `@LastModifiedDate` typu `Instant` DO encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_EnableJpaAuditingOnConfiguration {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `@EnableJpaAuditing` NA klasie `@Configuration`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SaveEntityAndPrintCreatedDate {
        /*
         * 🧪 Zadanie 6:
         * Zapisz encje I wypisz AUTOMATYCZNIE wypelniona `createdDate`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UpdateEntityAndCompareModifiedDate {
        /*
         * 🧪 Zadanie 7:
         * Zaktualizuj encje I porownaj `lastModifiedDate` PRZED I PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_VerifyCreatedDateNeverChanges {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, ze `createdDate` NIE ZMIENIA SIE PO aktualizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyAuditorAwareIsNeeded {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, DLACZEGO `@CreatedBy`/`@LastModifiedBy`
         * WYMAGAJA dodatkowego beana (w odroznieniu OD dat).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ObserveNullCreatedByWithoutAuditorAware {
        /*
         * 🧪 Zadanie 10:
         * Zaobserwuj, ze `createdBy` zostaje `null` BEZ zarejestrowanego
         * `AuditorAware`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementAuditorAwareString {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNY `AuditorAware<String>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RegisterAuditorAwareRef {
        /*
         * 🧪 Zadanie 12:
         * Zarejestruj beana PRZEZ `@EnableJpaAuditing(auditorAwareRef = "...")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddCreatedByAndLastModifiedByFields {
        /*
         * 🧪 Zadanie 13:
         * Dodaj `@CreatedBy` I `@LastModifiedBy` DO encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SimulateTwoDifferentUsers {
        /*
         * 🧪 Zadanie 14:
         * Zasymuluj DWOCH ROZNYCH "biezacych uzytkownikow" (tworzacego
         * I aktualizujacego) I zweryfikuj OBA pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareLocalDateTimeVsInstant {
        /*
         * 🧪 Zadanie 15:
         * Porownaj `Instant` vs `LocalDateTime` jako typ pol audytowych -
         * kiedy ktory wybrac (strefy czasowe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddAuditingToSecondEntity {
        /*
         * 🧪 Zadanie 16:
         * Dodaj auditing DO DRUGIEJ, NIEZALEZNEJ encji W TYM SAMYM
         * kontekscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainPrePersistPreUpdateHooks {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij, jak `AuditingEntityListener` uzywa
         * `@PrePersist`/`@PreUpdate` (JPA lifecycle callbacks).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UseDateTimeProviderForFixedClock {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj WLASNY `DateTimeProvider` (np. staly `Clock`
         * DO testow deterministycznych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareAuditingVsManualTimestamps {
        /*
         * 🧪 Zadanie 19:
         * Porownaj JPA Auditing Z RECZNYM ustawianiem `createdAt`
         * W konstruktorze/`@PrePersist` WLASNYM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_VerifyAuditingWorksWithEmbeddable {
        /*
         * 🧪 Zadanie 20:
         * Zweryfikuj, czy pola audytowe MOZNA wydzielic DO
         * `@Embeddable` (`AuditMetadata`) I dolaczyc `@Embedded` DO
         * KILKU encji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CompareJpaAuditingVsHibernateEnvers {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_12_hibernate/Lesson29_HibernateEnvers` - porownaj
         * JPA Auditing (TYLKO ostatni stan) Z Envers (PELNA historia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ExplainAuditorAwareWithSpringSecurity {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: opisz, jak `AuditorAware` W PRAWDZIWEJ
         * aplikacji pobralby uzytkownika Z `SecurityContextHolder`
         * (zapowiedz `_24_spring_security`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CombineAuditingWithOptimisticLocking {
        /*
         * 🧪 Zadanie 23:
         * Polacz auditing Z `@Version` (optymistyczne blokowanie,
         * `_12_hibernate/Lesson25`) NA TEJ SAMEJ encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestAuditingWithMultipleSequentialUpdates {
        /*
         * 🧪 Zadanie 24:
         * Wykonaj 3 kolejne aktualizacje I zweryfikuj, ze
         * `lastModifiedDate` ROSNIE MONOTONICZNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAuditorAwareReturningEmptyOptional {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj `AuditorAware` zwracajacy `Optional.empty()`
         * (np. DLA operacji systemowych BEZ zalogowanego uzytkownika)
         * I zaobserwuj skutek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainWhySilentNullIsRiskyDesign {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: oceń RYZYKO cichego `null` W `createdBy` (BRAK
         * bledu, gdy zapomnisz zarejestrowac `AuditorAware`) - JAK
         * MOZNA by to wykryc wczesniej (np. testy integracyjne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AddAuditingToEntityWithInheritance {
        /*
         * 🧪 Zadanie 27:
         * Dodaj pola audytowe DO `@MappedSuperclass` (powiazanie Z
         * `_12_hibernate/Lesson27_InheritanceMapping`) I odziedzicz JE
         * W 2 roznych encjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasurePerformanceImpactOfAuditing {
        /*
         * 🧪 Zadanie 28:
         * Porownaj czas 1000 zapisow Z auditingiem I BEZ - oszacuj
         * NARZUT (powinien byc pomijalny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignAuditingStrategyForMultiTenantApp {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zaprojektuj, jak `AuditorAware` MOGLBY zwracac
         * TEZ identyfikator "tenanta" (np. `AuditorAware<TenantUser>`
         * Z rekordem zamiast `String`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullAuditingDemoWithTwoEntitiesAndReport {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj DEMO Z 2 powiazanymi encjami (obie Z auditingiem),
         * wykonaj serie operacji (create/update/create/update) I
         * wypisz RAPORT "kto/kiedy" DLA KAZDEJ encji.
         */
        public static void main(String[] args) { }
    }
}
