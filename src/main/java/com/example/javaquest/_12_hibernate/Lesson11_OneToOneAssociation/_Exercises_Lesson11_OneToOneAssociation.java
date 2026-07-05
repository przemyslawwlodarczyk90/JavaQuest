package com.example.javaquest._12_hibernate.Lesson11_OneToOneAssociation;

public class _Exercises_Lesson11_OneToOneAssociation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicUnidirectionalOneToOne {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Passport (id, number) i Person (id, name, @OneToOne
         * @JoinColumn passport) na H2 ("jdbc:h2:mem:l11ex01;DB_CLOSE_DELAY=-1") -
         * TYLKO Person zna Passport (unidirectional). Zapisz oba i odczytaj Person
         * z jego paszportem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifyForeignKeyUnique {
        /*
         * 🧪 Zadanie 2:
         * Sprawdz recznym SQL (INFORMATION_SCHEMA), ze kolumna FK w tabeli person
         * (wskazujaca passport) ma ograniczenie UNIQUE - wyjasnij w komentarzu, po
         * co ono tam jest (wymusza "jeden-do-jednego" a nie "jeden-do-wielu").
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BidirectionalOneToOne {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz Passport o pole @OneToOne(mappedBy = "passport") person - relacja
         * staje sie dwukierunkowa. Zapisz Person+Passport i odczytaj OBIE strony
         * (person.getPassport() ORAZ passport.getPerson()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DeleteOwningSideOnly {
        /*
         * 🧪 Zadanie 4:
         * Usun TYLKO Person (bez cascade). Sprawdz, czy Passport POZOSTAJE w bazie
         * (bez wlasciciela relacji) - zapisz obserwacje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NullOneToOneField {
        /*
         * 🧪 Zadanie 5:
         * Zapisz Person BEZ przypisanego Passport (pole = null). Odczytaj go i
         * zweryfikuj, ze getPassport() zwraca null bez wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UniqueViolationOnSecondAssignment {
        /*
         * 🧪 Zadanie 6:
         * Sprobuj przypisac TEN SAM Passport do DWOCH roznych obiektow Person.
         * Zapisz w komentarzu blad naruszenia UNIQUE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_JoinColumnCustomName {
        /*
         * 🧪 Zadanie 7:
         * Ustaw @JoinColumn(name = "passport_fk") (inna nazwa niz domyslna). Sprawdz
         * w show_sql, ze wygenerowana kolumna nazywa sie dokladnie tak.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TwoDifferentOneToOneRelations {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do Person DRUGA relacje @OneToOne (np. do encji DriverLicense, bez
         * powiazania z Passport). Zapisz Person z obydwoma dokumentami i odczytaj oba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EagerLoadingVerification {
        /*
         * 🧪 Zadanie 9:
         * Zweryfikuj (przez show_sql) ze domyslny @OneToOne (strona posiadajaca) jest
         * EAGER - odczytaj Person i sprawdz, czy Passport zaladowal sie OD RAZU
         * (JOIN w tym samym zapytaniu lub natychmiastowe drugie zapytanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_UpdateOneToOneAssociation {
        /*
         * 🧪 Zadanie 10:
         * Zmien Person tak, zeby wskazywal na INNY, nowy Passport (zamiast
         * pierwotnego). Zapisz zmiane i zweryfikuj w NOWEJ Session.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MapsIdSharedPrimaryKey {
        /*
         * 🧪 Zadanie 11:
         * Przebuduj relacje Person-Passport na WSPOLDZIELONY klucz glowny przez
         * @MapsId - Passport.id STAJE SIE jednoczesnie kluczem obcym do Person.
         * Zapisz i odczytaj oba obiekty, potwierdzajac ze maja TEN SAM id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareJoinColumnVsMapsId {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj DWIE wersje tej samej relacji - jedna z @JoinColumn (osobny PK +
         * unique FK), druga z @MapsId (wspoldzielony PK). Porownaj w komentarzu
         * wygenerowany schemat obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CascadePersistOnOneToOne {
        /*
         * 🧪 Zadanie 13:
         * Dodaj cascade = CascadeType.PERSIST do @OneToOne. Zapisz Person z NOWYM,
         * jeszcze niezapisanym Passport JEDNYM wywolaniem session.persist(person) -
         * bez recznego persist(passport).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_OrphanRemovalOnOneToOne {
        /*
         * 🧪 Zadanie 14:
         * Dodaj orphanRemoval = true do bidirectional @OneToOne (strona mappedBy).
         * Ustaw person.setPassport(null) i zapisz zmiane - zweryfikuj, ze stary
         * Passport zostal AUTOMATYCZNIE usuniety z bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LazyFetchAttemptAndLimitation {
        /*
         * 🧪 Zadanie 15:
         * Ustaw fetch = FetchType.LAZY na stronie mappedBy (bez @JoinColumn). Zapisz
         * w komentarzu zaobserwowane zachowanie (czy faktycznie laduje sie leniwie,
         * czy mimo deklaracji EAGERLY - patrz pulapka z lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_OptionalFalseOnOneToOne {
        /*
         * 🧪 Zadanie 16:
         * Ustaw @OneToOne(optional = false) i sprobuj zapisac Person z passport =
         * null. Zapisz w komentarzu, czy i jaki blad Hibernate/baza zwraca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_QueryByAssociatedEntityFieldHqlPreview {
        /*
         * 🧪 Zadanie 17:
         * Zapisz 3 osoby z paszportami o roznych numerach. Uzyj HQL (podglad przed
         * Lesson18) "from Person p where p.passport.number = :number" zeby znalezc
         * konkretna osobe po numerze paszportu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ThreeEntityChainOneToOne {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj LANCUCH trzech encji polaczonych @OneToOne: Person -> Passport ->
         * VisaStamp (kazda kolejna relacja "jeden-do-jednego"). Zapisz cala kolejke
         * naraz i odczytaj przez wszystkie 3 poziomy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ReplaceAssociationHandlesOldOrphan {
        /*
         * 🧪 Zadanie 19:
         * Z orphanRemoval=true: przypisz Person NOWY Passport (zamiast starego) BEZ
         * jawnego ustawienia null najpierw. Zweryfikuj, ze STARY Passport zostal
         * automatycznie usuniety.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ManualLazyLoadingWorkaround {
        /*
         * 🧪 Zadanie 20:
         * Zamiast fetch=LAZY na @OneToOne (pulapka z lekcji): zaimplementuj RECZNE
         * "leniwe ladowanie" - Person BEZ zadnej relacji, osobne zapytanie find() na
         * Passport TYLKO gdy faktycznie potrzebne.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullBidirectionalWithHelperMethods {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj PELNA, dwukierunkowa relacje Person-Passport z metodami pomocniczymi
         * (assignPassport(Passport)/removePassport()) utrzymujacymi spojnosc OBU
         * stron w pamieci - jak wzorzec addBook/removeBook z Lesson12, ale dla 1:1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MapsIdWithCompositeScenario {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz przyklad @MapsId (Zadanie 11) o TRZECIA encje powiazana z
         * Passport (nie z Person) - zademonstruj, ze @MapsId dziala poprawnie nawet
         * gdy "wspoldzielacy" PK ma WLASNE, dodatkowe relacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MigrationFromJoinColumnToMapsId {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj migracje istniejacych danych z modelu @JoinColumn (osobny PK) na
         * @MapsId (wspoldzielony PK) - napisz w komentarzu (lub kodzie) kroki takiej
         * migracji schematu bez utraty danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PerformanceEagerVsManualLazy {
        /*
         * 🧪 Zadanie 24:
         * Porownaj czas odczytu 100 obiektow Person: raz z EAGER @OneToOne (domyslne,
         * zawsze laduje Passport), raz z podejsciem "reczny lazy" z Zadania 20 (bez
         * ladowania Passport, chyba ze potrzebne) - zapisz oba czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_OneToOneWithVersionOptimisticLockPreview {
        /*
         * 🧪 Zadanie 25:
         * Dodaj @Version (podglad przed Lesson25) do Person. Zasymuluj dwie
         * "rownolegle" proby zmiany przypisanego Passport - zapisz w komentarzu,
         * ktora sekcja kodu skorzysta z optymistycznego blokowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SelfReferencingOneToOne {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj encje Employee z @OneToOne DO SAMEJ SIEBIE (np. "mentor" - jeden
         * pracownik moze miec dokladnie jednego mentora, tez pracownika). Zapisz 2
         * pracownikow, gdzie jeden jest mentorem drugiego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConditionalCascadeBasedOnBusinessRule {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode "assignPassport" ktora kaskadowo usuwa STARY paszport TYLKO
         * jesli byl "tymczasowy" (dodatkowe pole isTemporary=true), w przeciwnym
         * razie zostawia go osierocony w bazie (reczna logika biznesowa NAD
         * mechanizmem orphanRemoval).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AuditingOneToOneChanges {
        /*
         * 🧪 Zadanie 28:
         * Uzyj @PreUpdate (podglad przed Lesson16) na Person zeby logowac (println)
         * KAZDA zmiane przypisanego Passport (stary vs nowy numer) przed zapisaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOneToOneDesignDecisions {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * porownujacy @JoinColumn vs @MapsId dla RELACJI Z TWOJEGO WLASNEGO
         * wyobrazonego projektu - z uzasadnieniem wyboru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullOneToOneCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system User-UserProfile-UserSettings
         * (LANCUCH 2 relacji @OneToOne) z: metodami pomocniczymi (Zadanie 21),
         * orphanRemoval, i @MapsId dla JEDNEJ z relacji (dla roznorodnosci) -
         * zademonstruj pelny cykl zycia tego grafu obiektow.
         */
        public static void main(String[] args) { }
    }
}
