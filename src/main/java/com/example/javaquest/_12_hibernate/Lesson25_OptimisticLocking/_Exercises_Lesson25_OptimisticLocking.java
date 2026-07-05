package com.example.javaquest._12_hibernate.Lesson25_OptimisticLocking;

public class _Exercises_Lesson25_OptimisticLocking {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicVersionField {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Document (id, title, @Version int version) na H2
         * ("jdbc:h2:mem:l25ex01;DB_CLOSE_DELAY=-1"). Zapisz dokument i wypisz jego
         * pole version PO persist (spodziewaj sie 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VersionIncrementsAfterUpdate {
        /*
         * 🧪 Zadanie 2:
         * Zapisz Document, potem zmien jego tytul w NOWEJ Session i zatwierdz -
         * zweryfikuj, ze version WZROSLO z 0 na 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TwoSessionsSameVersionConflict {
        /*
         * 🧪 Zadanie 3:
         * Otworz DWIE Session, obie znajdz TEN SAM Document (obie widza version=0).
         * Zapisz zmiane w PIERWSZEJ (version->1). Sprobuj zapisac zmiane w DRUGIEJ
         * (wciaz version=0) - zapisz w komentarzu OptimisticLockException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UpdateSequenceWorksWithoutConflict {
        /*
         * 🧪 Zadanie 4:
         * Wykonaj DWIE SEKWENCYJNE (nie rownolegle) aktualizacje TEGO SAMEGO
         * dokumentu, kazda w NOWEJ Session z NOWYM, swiezym find() - zweryfikuj, ze
         * OBIE sie udaja (brak konfliktu, bo kazda widzi aktualna wersje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LongVersionType {
        /*
         * 🧪 Zadanie 5:
         * Zmien typ pola version z int na Long. Zapisz w komentarzu, czy zachowanie
         * jest identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TimestampVersionType {
        /*
         * 🧪 Zadanie 6:
         * Zmien @Version na typ java.sql.Timestamp (zamiast liczby). Zweryfikuj, ze
         * mechanizm optymistycznego blokowania DALEJ dziala (Hibernate porownuje
         * znaczniki czasu zamiast liczb).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ManualVersionModificationIgnored {
        /*
         * 🧪 Zadanie 7:
         * Sprobuj RECZNIE ustawic pole version na dowolna wartosc PRZED zapisem
         * zmiany - zapisz w komentarzu, czy Hibernate uwzglednil Twoja reczna
         * wartosc, czy i tak wygenerowal wlasna (Hibernate w pelni zarzadza tym polem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RetryAfterCaughtException {
        /*
         * 🧪 Zadanie 8:
         * Zlap OptimisticLockException (jak w Zadaniu 3) i zaimplementuj PROSTA
         * strategie retry - odswiez dane (nowy find) i sprobuj zapisac zmiane
         * PONOWNIE, tym razem z sukcesem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_VersionOnEntityWithAssociation {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do Document @ManyToOne Author (bez @Version na Author). Zmien
         * TYLKO pole title Document - zweryfikuj, ze version WZROSLO, mimo ze
         * zmiana nie dotyczyla powiazanej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NoVersionFieldNoOptimisticLocking {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj DRUGA encje BEZ @Version. Powtorz scenariusz z Zadania 3 (dwie
         * Session, obie modyfikuja) - zapisz w komentarzu, ze OBIE zmiany "przechodza"
         * (druga NADPISUJE pierwsza bez ostrzezenia - lost update, brak ochrony).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_VersionWithCascadeToChildren {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do Document @OneToMany List<Comment> (bez @Version na Comment).
         * Zmien liste komentarzy (dodaj nowy) - zweryfikuj, ze version RODZICA
         * (Document) TEZ wzroslo (zmiana kolekcji dziecka wplywa na wersje rodzica,
         * jesli skonfigurowano @OptimisticLock).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_OptimisticLockExcludeAnnotation {
        /*
         * 🧪 Zadanie 12:
         * Uzyj @OptimisticLock(excluded = true) (Hibernate) na polu NIE wplywajacym
         * na logike biznesowa (np. "viewCount"). Zmien TYLKO to pole - zweryfikuj, ze
         * version NIE wzroslo (pole wykluczone z optymistycznego sledzenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConcurrentThreadsRealRaceCondition {
        /*
         * 🧪 Zadanie 13:
         * Uruchom DWA prawdziwe watki (jak w _05_multithreading), OBA startujace w
         * przyblizeniu jednoczesnie, KAZDY z WLASNA Session probujacy zmienic TEN
         * SAM Document - zweryfikuj, ze DOKLADNIE JEDEN sie udal, drugi dostal
         * OptimisticLockException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RetryLoopWithMaxAttempts {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode "updateWithRetry(SessionFactory, Long id, Consumer<Document>
         * modyfikacja, int maxAttempts)" - probujaca zapisac zmiane, a przy
         * OptimisticLockException odswiezajaca dane i probujaca PONOWNIE, do
         * maxAttempts razy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LockModeOptimisticExplicit {
        /*
         * 🧪 Zadanie 15:
         * Uzyj session.find(Document.class, id, LockModeType.OPTIMISTIC) (JAWNE
         * zadanie sprawdzenia wersji przy commit, nawet BEZ modyfikacji pol) -
         * zademonstruj przypadek, gdzie chcesz wykryc, ze KTOS INNY zmienil dane,
         * mimo ze Ty sam nic nie modyfikujesz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LockModeOptimisticForceIncrement {
        /*
         * 🧪 Zadanie 16:
         * Uzyj LockModeType.OPTIMISTIC_FORCE_INCREMENT - zweryfikuj, ze version
         * ZOSTAL zinkrementowany MIMO ze nie zmienile zadnego pola encji (przydatne,
         * gdy zmiana powiazanej encji powinna "unifikowac" tez rodzica).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_OptimisticLockingWithDetachedMerge {
        /*
         * 🧪 Zadanie 17:
         * Zapisz Document, zamknij Session (detached, version=0). W MIEDZYCZASIE
         * zmien go w INNEJ Session (version->1). Sprobuj merge() PIERWSZEGO,
         * detached obiektu (wciaz version=0) - zapisz w komentarzu OptimisticLockException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConflictResolutionStrategyLastWriteWins {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj strategie "last write wins" - PO zlapaniu
         * OptimisticLockException, zamiast merge'owac WSZYSTKIE pola, scal TYLKO
         * konkretne pole (np. title) z nowa wersja, zachowujac RESZTE zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_UserFriendlyErrorMessageOnConflict {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode LAPIACA OptimisticLockException i zwracajaca CZYTELNY
         * komunikat dla uzytkownika (np. "Dokument zostal zmieniony przez inna
         * osobe. Odswiez strone.") zamiast surowego wyjatku technicznego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_VersionFieldInDtoProjection {
        /*
         * 🧪 Zadanie 20:
         * Uzyj HQL "select new DocumentDto(d.id, d.title, d.version) from Document
         * d" (podglad z Lesson19) - zwroc DTO zawierajace TAKZE numer wersji,
         * przydatny gdy klient (np. formularz webowy) musi PRZESLAC z powrotem
         * wersje, ktora edytowal.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullOptimisticLockingBankingScenario {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj system bankowy (Account z @Version) z DWOMA rownoleglymi
         * "wyplatami" z tego samego konta (2 watki) - zweryfikuj, ze OptimisticLockException
         * ZAPOBIEGL podwojnej wyplacie (tylko JEDNA transakcja przeszla, druga
         * musiala odswiezyc dane i ponowic).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_OptimisticLockingWithFrontendVersionPassing {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj typowy scenariusz webowy: "formularz" zwraca (poza kolejnym
         * wywolaniem main()) numer version, ktory byl widoczny PRZY EDYCJI. Napisz
         * metode "saveEdit(Session, Long id, String newTitle, int expectedVersion)"
         * ktora RECZNIE weryfikuje version PRZED zapisem i rzuca WLASNY wyjatek,
         * jesli sie nie zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_HighConcurrencyStressTest {
        /*
         * 🧪 Zadanie 23:
         * Uruchom 10 watkow (jak w _05_multithreading) PROBUJACYCH jednoczesnie
         * zmodyfikowac TEN SAM Document, kazdy z retry (Zadanie 14, maxAttempts=5) -
         * zweryfikuj, ze WSZYSTKIE 10 zmian OSTATECZNIE sie powiodlo (dzieki retry),
         * mimo poczatkowych konfliktow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_OptimisticLockingPerformanceUnderContention {
        /*
         * 🧪 Zadanie 24:
         * Zmierz "przepustowosc" (liczba udanych zapisow na sekunde) systemu z
         * retry (Zadanie 14) PRZY ROSNACEJ liczbie rownoleglych watkow (2, 5, 10) -
         * zapisz w komentarzu, jak degraduje sie wydajnosc przy wysokiej kontencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CombineOptimisticLockingWithL2CachePreview {
        /*
         * 🧪 Zadanie 25:
         * Dodaj do Document Z @Version TAKZE @Cacheable (podglad z Lesson24).
         * Zweryfikuj, ze L2 cache POPRAWNIE respektuje numer wersji (nie zwraca
         * przestarzalej wersji z cache po udanej aktualizacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_OptimisticLockingAcrossAggregateRoot {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj "agregat" Order+OrderLine z @Version TYLKO na Order (korzen
         * agregatu). Zmien liste OrderLine - zweryfikuj (@OptimisticLock, Zadanie
         * 11), ze zmiana W DZIECKU podnosi wersje RODZICA, chroniac cala spojnosc
         * agregatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CustomOptimisticLockExceptionHandlerFramework {
        /*
         * 🧪 Zadanie 27:
         * Napisz generyczny "framework" - metoda "executeWithOptimisticRetry(Session-
         * Factory, Supplier<T> operacja, int maxAttempts)" dzialajaca dla DOWOLNEJ
         * operacji (nie tylko konkretnej encji) - uzyj jej dla 2 roznych scenariuszy
         * biznesowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestingOptimisticLockingBehaviorSystematically {
        /*
         * 🧪 Zadanie 28:
         * Napisz "test" (metoda z asercjami if+throw) systematycznie weryfikujaca
         * WSZYSTKIE scenariusze z tej lekcji (konflikt wykryty, retry dziala, brak
         * @Version = brak ochrony) - uruchom wszystkie w main() z podsumowaniem
         * "PASSED"/"FAILED".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnOptimisticLockingTradeoffs {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, kiedy blokowanie optymistyczne WYSTARCZA, a
         * kiedy trzeba siegnac po pesymistyczne (Lesson26) - z konkretnym przykladem
         * z Twojego doswiadczenia lub wyobrazonego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullOptimisticLockingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system bankowy (Account z
         * @Version) laczacy: retry framework (Zadanie 27), stress test wielu watkow
         * (Zadanie 23), przyjazne komunikaty bledow (Zadanie 19), i systematyczny
         * test wszystkich scenariuszy (Zadanie 28).
         */
        public static void main(String[] args) { }
    }
}
