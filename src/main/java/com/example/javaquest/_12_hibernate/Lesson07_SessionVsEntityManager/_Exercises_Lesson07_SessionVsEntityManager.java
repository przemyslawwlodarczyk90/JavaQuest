package com.example.javaquest._12_hibernate.Lesson07_SessionVsEntityManager;

public class _Exercises_Lesson07_SessionVsEntityManager {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_UseSessionAsEntityManager {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Employee (id, name) na H2
         * ("jdbc:h2:mem:l07ex01;DB_CLOSE_DELAY=-1"). Przypisz Session do zmiennej typu
         * jakarta.persistence.EntityManager i wywolaj przez nia persist()/find().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseSessionNatively {
        /*
         * 🧪 Zadanie 2:
         * Ta sama encja - uzyj Session BEZPOSREDNIO (typ Session, nie EntityManager)
         * i wywolaj natywna metode save() (zamiast persist()) - zapisz zwrocony klucz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_LoadReturnsProxy {
        /*
         * 🧪 Zadanie 3:
         * Uzyj session.load(Employee.class, id) (natywne, leniwy proxy) zamiast
         * find(). Wypisz klase zwroconego obiektu (getClass()) - zapisz w komentarzu,
         * ze to NIE jest dokladnie Employee.class (proxy Hibernate).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_LoadThrowsOnMissingId {
        /*
         * 🧪 Zadanie 4:
         * Wywolaj session.load(Employee.class, 9999L) (nieistniejace id), a
         * NASTEPNIE odczytaj jego pole (np. getName()) - zapisz w komentarzu wyjatek,
         * ktory zostanie rzucony DOPIERO przy tym dostepie (nie przy samym load()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UnwrapSessionFromEntityManager {
        /*
         * 🧪 Zadanie 5:
         * Przypisz Session do zmiennej EntityManager, a potem uzyj
         * entityManager.unwrap(Session.class) zeby odzyskac natywna Session. Wywolaj
         * na niej metode natywna (np. save()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareApiMethodNamesTable {
        /*
         * 🧪 Zadanie 6:
         * Bez bazy danych: wypisz WLASNA (nie kopiuj z lekcji) tabele porownawcza
         * min. 4 par metod: JPA vs Hibernate natywne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BothApiOnSameManagedObject {
        /*
         * 🧪 Zadanie 7:
         * W JEDNEJ Session: zapisz obiekt przez persist() (JPA), potem znajdz go
         * przez get() (Hibernate natywne), potem usun przez remove() (JPA) - trzy
         * rozne API na TYM SAMYM obiekcie w jednej Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SessionExtendsEntityManagerVerification {
        /*
         * 🧪 Zadanie 8:
         * Sprawdz w kodzie (instanceof) ze otwarta Session JEST rowniez instancja
         * jakarta.persistence.EntityManager - wypisz wynik tego sprawdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FindVsGetIdenticalBehaviorForExisting {
        /*
         * 🧪 Zadanie 9:
         * Dla ISTNIEJACEGO id: wywolaj zarowno find() jak i get() i porownaj
         * zwrocone obiekty (rowne pola) - zapisz w komentarzu, ze dla istniejacego
         * wiersza zachowuja sie identycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ChooseApiBasedOnNeedComment {
        /*
         * 🧪 Zadanie 10:
         * Bez bazy danych: dla KAZDEGO z 3 scenariuszy (biblioteka wspoldzielona,
         * mala aplikacja standalone, potrzeba leniwego proxy) zapisz w komentarzu,
         * czy wybrales by JPA czy Hibernate natywne API - z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UnwrapToSessionFactoryImplementor {
        /*
         * 🧪 Zadanie 11:
         * Uzyj sessionFactory.unwrap(org.hibernate.SessionFactory.class) (mimo ze
         * SessionFactory JUZ implementuje EntityManagerFactory) - zapisz w
         * komentarzu, kiedy taki unwrap moglby byc potrzebny mimo posiadania juz
         * odpowiedniego typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NativeQueryOnlyOnSession {
        /*
         * 🧪 Zadanie 12:
         * Uzyj metody dostepnej TYLKO na Session (np. setDefaultReadOnly() lub
         * getSessionFactory()) - metody, ktorej NIE MA na standardowym
         * EntityManager - zademonstruj jej dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PortableCodeWithPureJpa {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode przyjmujaca WYLACZNIE typ EntityManager (nie Session) jako
         * parametr i wykonujaca CRUD - zademonstruj, ze dziala poprawnie mimo braku
         * jawnego odwolania do Hibernate w SYGNATURZE metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_HibernateSpecificFlushModeOnSession {
        /*
         * 🧪 Zadanie 14:
         * Uzyj session.setHibernateFlushMode(...) (metoda natywna, Lesson17 rozwinie
         * FlushMode) - zademonstruj rozne zachowanie flush z MANUAL vs AUTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EntityManagerFactoryVsSessionFactory {
        /*
         * 🧪 Zadanie 15:
         * Przypisz SessionFactory do zmiennej typu
         * jakarta.persistence.EntityManagerFactory i wywolaj przez nia
         * createEntityManager() - porownaj zwrocony obiekt z sessionFactory.openSession().
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LoadPerformanceVsFind {
        /*
         * 🧪 Zadanie 16:
         * Zmierz czas 1000 wywolan load() (bez dostepu do pol, tylko tworzenie
         * proxy) vs 1000 wywolan find() (pelne zaladowanie) dla ISTNIEJACYCH id -
         * zapisz oba czasy w komentarzu i wyjasnij roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MixedApiInServiceLayer {
        /*
         * 🧪 Zadanie 17:
         * Napisz klase serwisu z polem prywatnym typu EntityManager (JPA), ale z JEDNA
         * metoda WEWNETRZNIE wykonujaca unwrap na Session dla konkretnej funkcji -
         * zapisz w komentarzu, dlaczego to WCIAZ dobra praktyka (izolacja Hibernate-
         * specific kodu w jednym miejscu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareExceptionTypesBothApis {
        /*
         * 🧪 Zadanie 18:
         * Sprowokuj TEN SAM blad (np. zapis duplikatu unique) RAZ przez API JPA
         * (persist), RAZ przez natywne (save) - zapisz w komentarzu, czy typ
         * wyjatku sie rozni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SessionGetSessionFactoryBackReference {
        /*
         * 🧪 Zadanie 19:
         * Z otwartej Session wywolaj session.getSessionFactory() i zweryfikuj
         * (==), ze to TA SAMA SessionFactory, z ktorej otworzono te Session.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentApiChoicePerMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz klase serwisu z 5 metodami CRUD, gdzie KAZDA metoda ma w
         * komentarzu Javadoc jawnie udokumentowane, czy uzywa JPA czy Hibernate
         * natywnego API i DLACZEGO.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AbstractRepositoryOverEntityManager {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj generyczna klase "GenericRepository<T, ID>" operujaca
         * WYLACZNIE na EntityManager (przenosnie) - z metodami save/findById/deleteById -
         * zademonstruj jej uzycie dla DWOCH roznych encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_HibernateSpecificFeatureNotInJpa {
        /*
         * 🧪 Zadanie 22:
         * Znajdz i zademonstruj JEDNA funkcje Hibernate, ktorej NIE MA w standardzie
         * JPA (np. @NaturalId, filtry @Filter) - napisz krotki, dzialajacy przyklad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PortabilityTestWithMockEntityManager {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode przyjmujaca EntityManager i wykonujaca logike biznesowa -
         * zademonstruj (komentarzem), ze JEDYNA zmiana potrzebna do dzialania z
         * INNYM dostawca JPA (np. EclipseLink) bylaby zmiana konfiguracji, nie kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WrapperClassHidingImplementationChoice {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj interfejs "EntityStore" z metodami CRUD i DWIE implementacje:
         * jedna oparta na czystym EntityManager, druga na natywnej Session - kod
         * wolajacy nie powinien wiedziec, ktorej uzywa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MigrationPathFromNativeToJpaReport {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: napisz raport tekstowy (min. 4 punkty, w komentarzu) opisujacy
         * kroki MIGRACJI istniejacego kodu uzywajacego Hibernate natywnego API (save/
         * get/update/delete) na czysty JPA (persist/find/merge/remove).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PerformanceOfUnwrapCall {
        /*
         * 🧪 Zadanie 26:
         * Zmierz czas 10000 wywolan entityManager.unwrap(Session.class) w petli -
         * zapisz w komentarzu, czy to operacja kosztowna (spodziewaj sie, ze NIE -
         * to zwykle rzutowanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_HybridApiDecisionMatrix {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj tabele decyzyjna (jako println) z min. 5 kryteriami (przenosnosc,
         * potrzeba konkretnej funkcji, zespol znajacy tylko JPA, wydajnosc,
         * czytelnosc) i rekomendacja API dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestingWithBothApiVariants {
        /*
         * 🧪 Zadanie 28:
         * Napisz DWIE wersje TEJ SAMEJ metody testowej weryfikujacej zapis encji -
         * jedna uzywajaca WYLACZNIE EntityManager, druga WYLACZNIE Session - obie
         * powinny dawac IDENTYCZNY wynik koncowy w bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DocumentingApiBoundaryInLargeApp {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj (jako komentarz) konwencje dla WIEKSZEGO zespolu: KTORE
         * warstwy aplikacji (repozytoria, serwisy, kontrolery) moga uzywac Session
         * natywnie, a ktore WYLACZNIE EntityManager - z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullApiComparisonCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY, dzialajacy program wykonujacy TEN
         * SAM scenariusz biznesowy (utworzenie pracownika, aktualizacja, leniwy
         * odczyt, usuniecie) DWIEMA sciezkami API (czysta EntityManager i natywna
         * Session) i wypisz w komentarzu koncowe podsumowanie roznic praktycznych.
         */
        public static void main(String[] args) { }
    }
}
