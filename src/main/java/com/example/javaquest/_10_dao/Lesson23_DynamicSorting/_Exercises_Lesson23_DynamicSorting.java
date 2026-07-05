package com.example.javaquest._10_dao.Lesson23_DynamicSorting;

public class _Exercises_Lesson23_DynamicSorting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WhyPlaceholderDoesNotWorkForColumnName {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson23ex01;DB_CLOSE_DELAY=-1" utworz tabele users
         * (id, name, email) z 3 wierszami (jak w lekcji). Sprobuj wykonac
         * PreparedStatement z SQL "SELECT * FROM users ORDER BY ?" i ustawieniem
         * stmt.setString(1, "name") - zlap SQLException (albo zaobserwuj brak
         * sortowania) i wypisz w komentarzu, co faktycznie sie stalo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WhitelistSetDefinition {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj Set<String> ALLOWED_COLUMNS = Set.of("id", "name", "email") oraz
         * Set<String> ALLOWED_DIRECTIONS = Set.of("ASC", "DESC") (jak w lekcji).
         * Napisz metode validateColumn(String) rzucajaca IllegalArgumentException dla
         * wartosci spoza listy. Przetestuj dla "name" (OK) i "password_hash"
         * (odrzucone).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ValidateDirectionNormalization {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode validateDirection(String) normalizujaca wejscie (trim +
         * toUpperCase) PRZED sprawdzeniem wzgledem ALLOWED_DIRECTIONS. Przetestuj
         * dla " asc " (male litery, spacje - powinno przejsc jako "ASC") oraz dla
         * "boczny" (odrzucone).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FindAllSortedBasicUsage {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj UserDao.findAllSorted(column, direction) DOKLADNIE jak w
         * lekcji (walidacja obu parametrow, potem konkatenacja BEZPIECZNYCH,
         * zwalidowanych wartosci do SQL). Wywolaj dla ("name", "ASC") i ("email",
         * "DESC") - wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RejectedColumnOutsideWhitelist {
        /*
         * 🧪 Zadanie 5:
         * Wywolaj findAllSorted("password_hash", "ASC") (kolumna ISTNIEJACA w
         * tabeli, ale NIE na bialej liscie). Zlap IllegalArgumentException i wypisz
         * komunikat - pokazujac, ze sama OBECNOSC kolumny w tabeli NIE wystarcza,
         * musi byc na bialej liscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SqlInjectionAttemptThroughColumnParameter {
        /*
         * 🧪 Zadanie 6:
         * Wywolaj findAllSorted("id; DROP TABLE users; --", "ASC") (jak w lekcji).
         * Zlap IllegalArgumentException i sprawdz PO probie (SELECT COUNT(*) FROM
         * users), ze tabela users WCIAZ ISTNIEJE i ma te same 3 wiersze - biala
         * lista zablokowala atak PRZED zbudowaniem SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SqlInjectionAttemptThroughDirectionParameter {
        /*
         * 🧪 Zadanie 7:
         * Wywolaj findAllSorted("name", "ASC; DROP TABLE users; --"). Zlap
         * IllegalArgumentException i sprawdz, ze walidacja kierunku TAKZE blokuje
         * ten atak (nie tylko kolumna jest zabezpieczona - kierunek rowniez musi
         * przejsc przez biala liste).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SortByIdBothDirections {
        /*
         * 🧪 Zadanie 8:
         * Wywolaj findAllSorted("id", "ASC") i findAllSorted("id", "DESC") - wypisz
         * OBA wyniki i sprawdz recznie, ze kolejnosc jest dokladnie odwrotna miedzy
         * nimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DefaultSortWhenNoParameterGiven {
        /*
         * 🧪 Zadanie 9:
         * Rozbuduj UserDao o metode findAllDefaultSort() (bez parametrow) wywolujaca
         * findAllSorted("id", "ASC") jako sensowny domyslny wybor. Wywolaj ja i
         * porownaj wynik z bezposrednim findAllSorted("id", "ASC").
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NullColumnOrDirectionRejected {
        /*
         * 🧪 Zadanie 10:
         * Wywolaj findAllSorted(null, "ASC") oraz findAllSorted("name", null). Sprawdz,
         * ze OBA przypadki sa poprawnie odrzucone przez walidacje (null NIE powinien
         * powodowac NullPointerException, tylko czytelny IllegalArgumentException).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ExtendWhitelistWithNewColumn {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do users kolumne created_at DATE i rozbuduj ALLOWED_COLUMNS o
         * "created_at". Wstaw 3 wiersze z roznymi datami i wywolaj
         * findAllSorted("created_at", "DESC") - wypisz wynik sprawdzajac, ze
         * sortowanie po nowej kolumnie dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultiColumnSortWithFixedSecondaryColumn {
        /*
         * 🧪 Zadanie 12:
         * Rozbuduj findAllSorted, by budowal ORDER BY <safeColumn> <safeDirection>,
         * id (drugi, STALY, zawsze bezpieczny klucz sortowania jako "tie-breaker").
         * Wstaw 2 uzytkownikow o TEJ SAMEJ nazwie i sprawdz, ze sortowanie po name
         * daje DETERMINISTYCZNY wynik dzieki dodatkowemu sortowaniu po id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SortParameterFromSimulatedHttpRequest {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode parseSortParameter(String rawSortParam) parsujaca String w
         * formacie "kolumna,kierunek" (np. "email,desc" - symulacja parametru z URL
         * "?sort=email,desc") na dwa Stringi PRZEKAZYWANE do findAllSorted (ktore
         * WCIAZ wykonuje pelna walidacje wzgledem bialej listy). Przetestuj dla
         * poprawnego i zlosliwego parametru wejsciowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CaseInsensitiveColumnNameHandling {
        /*
         * 🧪 Zadanie 14:
         * Rozbuduj validateColumn, by AKCEPTOWAL kolumne niezaleznie od wielkosci
         * liter na WEJSCIU (np. "NAME", "Name", "name" - wszystkie normalizowane do
         * malych liter PRZED sprawdzeniem wzgledem bialej listy), ale ZAWSZE
         * zwracal znormalizowana, bezpieczna wersje. Przetestuj na 3 wariantach
         * wielkosci liter tej samej kolumny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MapFriendlyNamesToActualColumns {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj Map<String, String> friendlyToColumn (np. "imie" -> "name",
         * "adres-email" -> "email") - uzytkownik UI podaje "przyjazna" nazwe, kod
         * mapuje ja na PRAWDZIWA nazwe kolumny, a NASTEPNIE i tak przechodzi przez
         * walidacje wzgledem ALLOWED_COLUMNS (podwojna ochrona). Przetestuj dla
         * "imie" (mapuje sie na "name") i dla nieznanej "przyjaznej" nazwy
         * (odrzucone).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestingWhitelistWithMiniRunner {
        /*
         * 🧪 Zadanie 16:
         * Napisz mini test-runner (jak w Lesson26_TestingDao) z co najmniej 5
         * testami: sortowanie po dozwolonej kolumnie dziala, kolumna spoza listy
         * rzuca wyjatek, atak SQL injection przez kolumne jest zablokowany, atak
         * przez kierunek jest zablokowany, null jest odrzucany. Wypisz
         * PASSED/FAILED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PerformanceOfValidationNegligible {
        /*
         * 🧪 Zadanie 17:
         * Zmierz (System.nanoTime()) czas wykonania 10000 wywolan validateColumn +
         * validateDirection (bez zadnego SQL). Wypisz zmierzony czas i skomentuj, ze
         * koszt samej walidacji wzgledem bialej listy jest calkowicie znikomy w
         * porownaniu do kosztu zapytania SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DynamicSortCombinedWithFixedWhereClause {
        /*
         * 🧪 Zadanie 18:
         * Dodaj do users kolumne active BOOLEAN. Rozbuduj findAllSorted, by
         * zapytanie mialo STALY warunek WHERE active = true, a ORDER BY pozostaje
         * dynamiczny (zwalidowany). Wstaw uzytkownikow aktywnych i nieaktywnych i
         * sprawdz, ze wynik zawiera TYLKO aktywnych, poprawnie posortowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExceptionMessageListsAllowedValues {
        /*
         * 🧪 Zadanie 19:
         * Sprawdz (jak w lekcji), ze komunikat wyjatku dla niedozwolonej kolumny
         * ZAWIERA pelna liste dozwolonych wartosci (np. "Dozwolone: [id, name,
         * email]") - to uzyteczne dla debugowania/API. Wywolaj z 3 roznymi
         * niedozwolonymi kolumnami i sprawdz, ze KAZDY komunikat wymienia te sama,
         * kompletna liste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorToGenericWhitelistValidator {
        /*
         * 🧪 Zadanie 20:
         * Wydziel walidacje "wartosc na bialej liscie" do OGOLNEJ metody generycznej
         * <T> T validateAgainstWhitelist(T value, Set<T> allowed, String
         * fieldNameForError) - uzyj jej DLA OBU walidacji (kolumna i kierunek) w
         * UserDao, eliminujac duplikacje kodu miedzy validateColumn i
         * validateDirection.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DynamicSortAcrossTwoJoinedTablesWithPrefixedWhitelist {
        /*
         * 🧪 Zadanie 21:
         * Dodaj tabele orders (id, user_id, amount) polaczona z users przez JOIN.
         * Rozbuduj biala liste kolumn sortowania na PREFIKSOWANE nazwy (np.
         * "u.name", "u.email", "o.amount") - kazda z nich MUSI byc jawnie
         * wymieniona na bialej liscie (nie generuj prefiksow dynamicznie).
         * Zademonstruj sortowanie zapytania JOIN po "o.amount" DESC i po "u.name"
         * ASC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiColumnDynamicSortWithOwnWhitelistPerPosition {
        /*
         * 🧪 Zadanie 22:
         * Rozbuduj findAllSorted, by przyjmowal LISTE par (kolumna, kierunek) -
         * List<String[]> sortSpec - i budowal ORDER BY z WIELOMA kolumnami naraz
         * (np. "ORDER BY name ASC, id DESC"), KAZDA kolumna i kierunek zwalidowane
         * NIEZALEZNIE wzgledem tej samej bialej listy. Przetestuj z 3-elementowa
         * specyfikacja sortowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WhitelistLoadedFromConfigurationNotHardcoded {
        /*
         * 🧪 Zadanie 23:
         * Zamiast Set.of(...) na sztywno w kodzie, wczytaj dozwolone kolumny z
         * java.util.Properties (recznie skonstruowanych w kodzie, symulujacych plik
         * konfiguracyjny: klucz "sortable.columns" z wartoscia "id,name,email"
         * rozdzielona przecinkami). Zbuduj Set<String> z tej wartosci PRZY STARCIE
         * i uzyj go jako bialej listy - zademonstruj, ze dziala identycznie jak
         * wersja "na sztywno".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FuzzTestingWhitelistAgainstManyMaliciousInputs {
        /*
         * 🧪 Zadanie 24:
         * Przygotuj liste 15 "zlosliwych" Stringow (rozne warianty SQL injection:
         * komentarze --, UNION SELECT, podzapytania, srednik+kolejna instrukcja,
         * biale znaki, Unicode itp.) i wywolaj dla KAZDEGO findAllSorted(malicious,
         * "ASC"). Sprawdz, ze WSZYSTKIE 15 zostaly odrzucone przez
         * IllegalArgumentException (zero przypadkow "przeslizgniecia sie" przez
         * walidacje) i wypisz podsumowanie testu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LoggingRejectedSortAttemptsForSecurityAudit {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj validateColumn/validateDirection, by KAZDA odrzucona wartosc
         * (spoza bialej listy) byla dopisywana do listy statycznej
         * "suspiciousSortAttempts" (z timestampem i wartoscia). Wykonaj 5 prob (3
         * zlosliwe, 2 poprawne) i wypisz na koniec zawartosc tej listy jako
         * "log bezpieczenstwa".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ComparingWhitelistVsBlacklistApproach {
        /*
         * 🧪 Zadanie 26:
         * Napisz (jako przeciwny przyklad, TYLKO do porownania - NIE uzywaj go
         * produkcyjnie) validateColumnBlacklist(String column) odrzucajacy TYLKO
         * znane "niebezpieczne" znaki (np. ';', '--', spacja). Przetestuj go na TEJ
         * SAMEJ liscie 15 zlosliwych wejsc z Zadania 24 i sprawdz w komentarzu,
         * KTORE z nich przeslizguja sie przez czarna liste, mimo ze biala lista je
         * blokuje - to dowod, dlaczego biala lista jest jedynym bezpiecznym
         * podejsciem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DynamicSortWithNullsFirstLastOption {
        /*
         * 🧪 Zadanie 27:
         * Dodaj do users kolumne middle_name VARCHAR (moze byc NULL). Rozbuduj
         * findAllSorted o dodatkowy, zwalidowany parametr nullsPosition ("FIRST" albo
         * "LAST", TAKZE na bialej liscie) dodawany jako "NULLS FIRST"/"NULLS LAST"
         * do ORDER BY. Zademonstruj sortowanie po middle_name z NULLS FIRST i z
         * NULLS LAST na danych z mieszanka null/niepustych wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ThreadSafeWhitelistValidationUnderConcurrentAccess {
        /*
         * 🧪 Zadanie 28:
         * Uruchom 5 WATKOW wywolujacych rownolegle findAllSorted z ROZNYMI (i
         * miedzy nimi zlosliwymi) parametrami wejsciowymi, kazdy po 20 wywolan.
         * Poczekaj na zakonczenie wszystkich (join z limitem czasu) i sprawdz, ze
         * ZADNE wywolanie ze zlosliwym parametrem nie "przeciekło" (Set.of(...) jest
         * immutable i thread-safe do odczytu) - policz liczbe odrzuconych wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullRegressionTestSuiteForSortingSecurity {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj kompletny pakiet testow regresyjnych (mini-runner) obejmujacy: 15
         * przypadkow z fuzz-testu (Zadanie 24), multi-column sort (Zadanie 22),
         * sortowanie z JOIN (Zadanie 21), oraz normalizacje wielkosci liter (Zadanie
         * 14). Wypisz PASSED/FAILED dla wszystkich i podsumowanie liczbowe na
         * koncu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullDynamicSortingApiLayer {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletna "warstwe API" sortowania: parsowanie parametru "sort" z
         * symulowanego zapytania (Zadanie 13), mapowanie przyjaznych nazw (Zadanie
         * 15), walidacja wzgledem bialej listy zaladowanej z konfiguracji (Zadanie
         * 23), logowanie podejrzanych prob (Zadanie 25), i sortowanie
         * wielokolumnowe (Zadanie 22). Zasymuluj 6 "zadan uzytkownika" (mieszanka
         * poprawnych i zlosliwych parametrow sort) i wypisz kompletny log
         * przetworzenia kazdego z nich.
         */
        public static void main(String[] args) { }
    }
}
