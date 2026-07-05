package com.example.javaquest._12_hibernate.Lesson29_HibernateEnvers;

public class _Exercises_Lesson29_HibernateEnvers {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicAuditedEntity {
        /*
         * 🧪 Zadanie 1:
         * Utworz @Audited encje Product (id, name, price) na H2
         * ("jdbc:h2:mem:l29ex01;DB_CLOSE_DELAY=-1"). Zapisz produkt i sprawdz
         * recznym SQL, ze powstala DODATKOWA tabela "product_AUD".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FirstRevisionAfterInsert {
        /*
         * 🧪 Zadanie 2:
         * Zapisz Product. Uzyj AuditReader.getRevisions(Product.class, id) - zapisz
         * w komentarzu liczbe rewizji (spodziewaj sie 1 - sam INSERT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SecondRevisionAfterUpdate {
        /*
         * 🧪 Zadanie 3:
         * Zmien pole price w NOWEJ Session i zapisz. Uzyj getRevisions() ponownie -
         * zweryfikuj, ze TERAZ sa 2 rewizje (INSERT + UPDATE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReadHistoricalStateAtRevision {
        /*
         * 🧪 Zadanie 4:
         * Uzyj auditReader.find(Product.class, id, pierwszaRewizja) - zweryfikuj,
         * ze zwrocony obiekt ma STARA cene (sprzed aktualizacji z Zadania 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RevisionAfterDelete {
        /*
         * 🧪 Zadanie 5:
         * Usun Product. Uzyj getRevisions() - zapisz w komentarzu, ze TAKZE
         * usuniecie jest rejestrowane jako nowa rewizja (REVTYPE=DEL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NonAuditedEntityNoAuditTable {
        /*
         * 🧪 Zadanie 6:
         * Utworz DRUGA encje Category BEZ @Audited. Zapisz i zmien kilka razy -
         * sprawdz recznym SQL, ze NIE powstala zadna tabela "category_AUD".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AuditedFieldExclusion {
        /*
         * 🧪 Zadanie 7:
         * Uzyj @NotAudited na JEDNYM polu (np. "internalNotes") w encji @Audited.
         * Zmien to pole - zweryfikuj, ze NIE trafilo do historii rewizji (kolumna
         * NIE istnieje/jest ignorowana w tabeli _AUD).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RevisionTypeColumnValues {
        /*
         * 🧪 Zadanie 8:
         * Sprawdz recznym SQL (SELECT * FROM product_AUD) wartosci kolumny REVTYPE
         * po INSERT, UPDATE i DELETE - zapisz w komentarzu, ze to 0/1/2 odpowiednio.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MultipleUpdatesMultipleRevisions {
        /*
         * 🧪 Zadanie 9:
         * Zmien Product 5 razy (rozne pola, rozne Session). Uzyj getRevisions() i
         * zweryfikuj, ze jest DOKLADNIE 6 rewizji (1 insert + 5 update).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AuditReaderFactoryFromDifferentSession {
        /*
         * 🧪 Zadanie 10:
         * Uzyj AuditReaderFactory.get(session) w ROZNYCH Session (nie tej samej, w
         * ktorej zapisywales) - zweryfikuj, ze historia jest dostepna niezaleznie
         * od tego, ktora Session czyta.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CustomRevisionEntityWithUsername {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj @RevisionEntity z WLASNYM RevisionListener ustawiajacym pole
         * "changedBy" (jak w lekcji), ale dla WLASNEJ encji Product. Zapisz zmiany
         * "jako" 2 rozni "uzytkownicy" (ThreadLocal, jak w lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FindRevisionMetadataForSpecificChange {
        /*
         * 🧪 Zadanie 12:
         * Dla KAZDEJ rewizji Product (Zadanie 9): uzyj
         * auditReader.findRevision(CustomRevisionEntity.class, numer) - wypisz
         * KTO i KIEDY (czas rewizji) dokonal kazdej zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AuditedAssociationOneToMany {
        /*
         * 🧪 Zadanie 13:
         * Dodaj do Product @Audited @OneToMany List<PriceChange> (podglad przed
         * pelnym Lesson12) - zweryfikuj, ze zmiany W KOLEKCJI TAKZE trafiaja do
         * historii audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RevisionsOfDeletedEntityQuery {
        /*
         * 🧪 Zadanie 14:
         * Usun Product. Uzyj AuditQuery (createQuery().forRevisionsOfEntity(...))
         * zeby odczytac PELNA historie WLACZNIE z rewizja usuniecia - wypisz KAZDA
         * rewizje z jej REVTYPE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AuditQueryWithCriteria {
        /*
         * 🧪 Zadanie 15:
         * Uzyj AuditQuery z warunkiem (np. AuditEntity.property("price").gt(100)) -
         * znajdz WSZYSTKIE rewizje, w ktorych cena byla powyzej progu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareCurrentStateVsHistoricalRevision {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode "compareWithRevision(Session, Long id, Number revision)"
         * zwracajaca RAPORT (min. 2 pola) roznic miedzy AKTUALNYM stanem a stanem z
         * WSKAZANEJ rewizji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LatestRevisionNumberQuery {
        /*
         * 🧪 Zadanie 17:
         * Uzyj getRevisions() i pobierz OSTATNI element listy (najnowsza rewizje) -
         * zweryfikuj, ze odczyt find() z TA rewizja daje TAKI SAM wynik, jak zwykly
         * session.find() (aktualny stan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RevisionTimestampFiltering {
        /*
         * 🧪 Zadanie 18:
         * Uzyj AuditQuery z warunkiem na czasie rewizji (AuditEntity.revisionProperty
         * ("timestamp")) - znajdz stan encji "sprzed" konkretnej godziny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AuditingMultipleEntitiesInOneTransaction {
        /*
         * 🧪 Zadanie 19:
         * W JEDNEJ transakcji zmien DWIE rozne encje @Audited naraz. Zweryfikuj, ze
         * OBIE dostaly NOWA rewizje o TYM SAMYM numerze (jedna rewizja per transakcja,
         * nie per encja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RevisionEntityCustomFieldsMultiple {
        /*
         * 🧪 Zadanie 20:
         * Rozszerz CustomRevisionEntity o DRUGIE dodatkowe pole (np. "sourceIp" -
         * symulowany, staly String) obok "changedBy". Zapisz zmiane i zweryfikuj OBA
         * pola metadanych.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullAuditTrailReportingSystem {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNY system audytu dla Product z: WLASNA metadane rewizji
         * (kto/kiedy, Zadanie 11), raportem porownawczym (Zadanie 16), i metoda
         * "printFullHistory(Session, Long id)" wypisujaca CHRONOLOGICZNIE wszystkie
         * zmiany z ich autorami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RestoreEntityToHistoricalState {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode "restoreToRevision(Session, Long id, Number revision)" ktora
         * ODCZYTUJE stan z podanej rewizji i ZAPISUJE go jako NOWY, AKTUALNY stan
         * (efektywnie "cofniecie" zmian, ale jako NOWY wpis w historii, nie
         * modyfikacja przeszlosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ComplianceReportForAuditRequirements {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj RAPORT "compliance" - dla KAZDEJ zmiany ceny Product WIEKSZEJ niz
         * 20% (porownanie kolejnych rewizji), wypisz OSTRZEZENIE z data, autorem i
         * wartosciami przed/po - symulacja wymogu audytu finansowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AuditedEntityWithSoftDeleteInteraction {
        /*
         * 🧪 Zadanie 24:
         * Polacz Envers Z "soft delete" (podglad z Lesson06/23 - pole "deleted"
         * zamiast fizycznego usuwania) - zweryfikuj, ze ZMIANA pola deleted=true
         * TAKZE trafia do historii rewizji jako zwykly UPDATE (nie REVTYPE=DEL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PerformanceOverheadOfEnvers {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas zapisu 1000 obiektow Z @Audited vs 1000 obiektow encji BEZ
         * @Audited (identycznej struktury) - zapisz oba czasy w komentarzu i oszacuj
         * narzut Envers.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AuditQueryWithJoinAcrossRevisions {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj ZLOZONY AuditQuery laczacy filtr na WARTOSCI pola Z filtrem na
         * METADANYCH rewizji (kto + jaka wartosc) - znajdz wszystkie zmiany
         * WYKONANE przez konkretnego "uzytkownika", ktore ustawily cene powyzej progu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ArchivingOldRevisionsStrategy {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: zaprojektuj (kod + komentarz) strategie ARCHIWIZACJI
         * starych rewizji (np. starszych niz rok) do OSOBNEJ tabeli/bazy, zeby
         * tabela "_AUD" nie rosla w nieskonczonosc - opisz kroki takiej archiwizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestingAuditTrailCompleteness {
        /*
         * 🧪 Zadanie 28:
         * Napisz "test" (metoda z asercjami if+throw) weryfikujacy, ze KAZDA
         * operacja CRUD na Product (create/update/delete) generuje ODPOWIEDNIA
         * liczbe i typ rewizji - uruchom wszystkie scenariusze z podsumowaniem
         * "PASSED"/"FAILED".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnEnversUseCasesAndLimitations {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, kiedy Envers jest WARTY uzycia (wymogi
         * compliance, debugowanie) i jakie sa jego OGRANICZENIA (narzut wydajnosciowy,
         * rosnaca tabela _AUD, brak wbudowanej archiwizacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullEnversCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system audytu produktow laczacy:
         * WLASNA metadane rewizji z 2 polami (Zadanie 20), raport compliance
         * (Zadanie 23), metode restore (Zadanie 22), i systematyczny test
         * kompletnosci historii (Zadanie 28).
         */
        public static void main(String[] args) { }
    }
}
