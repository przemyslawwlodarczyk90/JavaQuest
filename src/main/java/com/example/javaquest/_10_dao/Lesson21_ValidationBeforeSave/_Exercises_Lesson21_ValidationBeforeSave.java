package com.example.javaquest._10_dao.Lesson21_ValidationBeforeSave;

public class _Exercises_Lesson21_ValidationBeforeSave {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ServiceRejectsEmptyEmailWithoutSql {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson21ex01;DB_CLOSE_DELAY=-1" utworz tabele users
         * (jak w lekcji: email UNIQUE, name, reputation z CHECK >= 0). Napisz
         * UserService.registerUser(email, name) zwracajace String, sprawdzajace
         * TYLKO regule "email nie moze byc pusty" (bez kontaktu z DAO). Wywolaj dla
         * pustego e-maila i wypisz komunikat odrzucenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ServiceRejectsInvalidEmailFormat {
        /*
         * 🧪 Zadanie 2:
         * Rozbuduj UserService o regule "email musi zawierac @" (jak w lekcji).
         * Przetestuj na 3 adresach: poprawnym, bez @ oraz pustym - wypisz komunikat
         * dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SuccessfulRegistrationReachesDatabase {
        /*
         * 🧪 Zadanie 3:
         * Uzywajac UserService i UserDao z lekcji (insert(email, name, reputation)),
         * zarejestruj jednego uzytkownika z poprawnymi danymi. Sprawdz SELECT
         * COUNT(*) z tabeli users - powinien wynosic 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UniqueConstraintAsLastLineOfDefense {
        /*
         * 🧪 Zadanie 4:
         * Zarejestruj tego samego e-maila DWA RAZY przez UserService.registerUser.
         * Sprawdz, ze walidacja w Service NIE wykryla duplikatu (bo nie ma osobnego
         * sprawdzenia SELECT), ale ograniczenie UNIQUE w bazie odrzucilo drugi
         * insert - Service powinien zlapac SQLException i zwrocic czytelny
         * komunikat (nie surowy wyjatek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IsUniqueViolationHelperMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode pomocnicza boolean isUniqueViolation(SQLException e) (jak w
         * lekcji: "23505".equals(e.getSQLState())). Wywolaj insert dwukrotnie z tym
         * samym e-mailem, zlap SQLException i sprawdz DZIALANIE tej metody na
         * zlapanym wyjatku - wypisz wynik (true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NegativeReputationRejectedInService {
        /*
         * 🧪 Zadanie 6:
         * Uzywajac UserService.registerUser(email, name, reputation) z lekcji,
         * zarejestruj uzytkownika z reputation=-5. Sprawdz, ze walidacja w Service
         * odrzucila go PRZED jakimkolwiek kontaktem z baza (np. przez licznik
         * wywolan DAO.insert rowny 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CheckConstraintAsLastLineOfDefense {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode registerUserBypassingValidation(connection, email, name,
         * reputation) WYWOLUJACA DAO DIRECTLY, bez przechodzenia przez walidacje
         * Service (symulujac "inny kod, ktory zapisuje bezposrednio do bazy").
         * Wywolaj ja z reputation=-10 i sprawdz, ze CHECK w bazie (reputation >= 0)
         * odrzuca insert mimo braku walidacji w Service - zlap SQLException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UserFriendlyMessageNeverShowsRawSqlException {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, ze registerUser NIGDY nie zwraca surowego
         * e.getMessage()/e.toString() z SQLException uzytkownikowi - dla duplikatu
         * e-maila powinien zwrocic WLASNY, czytelny komunikat (jak w lekcji:
         * "Rejestracja odrzucona: e-mail '...' jest juz uzywany"). Sprawdz to
         * porownujac zwrocony String z surowym komunikatem SQLException (nie moga
         * byc rowne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MultipleValidRegistrationsInARow {
        /*
         * 🧪 Zadanie 9:
         * Zarejestruj 5 uzytkownikow z ROZNYMI, poprawnymi e-mailami przez
         * registerUser. Sprawdz SELECT COUNT(*) - powinno byc 5, i wypisz wszystkie
         * zwrocone komunikaty sukcesu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CombinedValidationAndConstraintReport {
        /*
         * 🧪 Zadanie 10:
         * Wykonaj 5 prob rejestracji: 2 poprawne (rozne e-maile), 1 z pustym
         * e-mailem, 1 z ujemna reputacja, 1 duplikat jednego z poprawnych. Zbierz
         * WSZYSTKIE 5 komunikatow do listy i wypisz je jako raport, oznaczajac
         * KTORA "linia obrony" (Service czy baza) odrzucila kazdy z nieudanych
         * przypadkow.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ServiceValidationCatchesMostCasesEarly {
        /*
         * 🧪 Zadanie 11:
         * Dodaj do UserDao licznik statyczny wywolan insert(). Wykonaj 10 prob
         * rejestracji, z czego 6 narusza walidacje w Service (pusty email, brak @,
         * ujemna reputacja w roznych kombinacjach) i 4 sa poprawne. Sprawdz, ze
         * licznik wywolan insert() wynosi TYLKO 4 - walidacja w Service
         * "przechwycila" wiekszosc bledow, ZANIM dotarly do bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NameLengthValidationRule {
        /*
         * 🧪 Zadanie 12:
         * Dodaj do UserService nowa regule: imie (name) musi miec co najmniej 2
         * znaki. Rozbuduj tabele/kolumne jesli potrzebne, i przetestuj rejestracje z
         * imieniem 1-znakowym (odrzucone w Service) oraz poprawnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DatabaseCheckCatchesRaceConditionSimulation {
        /*
         * 🧪 Zadanie 13:
         * Zasymuluj "wyscig" dwoch rownoleglych rejestracji tego samego e-maila:
         * wykonaj DWA wywolania registerUser z TYM SAMYM e-mailem "blisko siebie"
         * (bez sprawdzania duplikatu miedzy nimi - jak w naturalnym przebiegu kodu).
         * Sprawdz, ze DOKLADNIE JEDNO z nich sie powiodlo, a drugie zostalo
         * odrzucone przez UNIQUE w bazie - Service sam z siebie nie moglby tego
         * wykryc bez dodatkowego zapytania SELECT (ktore i tak nie gwarantuje
         * bezpieczenstwa przy prawdziwej wspolbiezno sci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TranslateCheckViolationToFriendlyMessage {
        /*
         * 🧪 Zadanie 14:
         * Rozbuduj registerUser, by ROZROZNIAL DWA rodzaje naruszen bazy: UNIQUE
         * (SQLState 23505 -> "e-mail juz uzywany") i CHECK (SQLState 23514 ->
         * "reputacja nie moze byc ujemna"). Wywolaj bezposrednio DAO.insert (bez
         * walidacji Service) z ujemna reputacja i sprawdz, ze Service poprawnie
         * rozpoznaje TEN KONKRETNY typ naruszenia z SQLState.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidationOrderMattersForUserExperience {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj registerUser tak, by SPRAWDZAL reguly w OKRESLONEJ kolejnosci
         * (najpierw pustosc, potem format, potem reputacja) i ZWRACAL komunikat dla
         * PIERWSZEJ naruszonej reguly (nie kontynuuje sprawdzania dalej). Wywolaj dla
         * danych naruszajacych WIELE regul naraz i sprawdz, ktory komunikat sie
         * pojawia - musi byc konsekwentny z ustalona kolejnoscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DomainValidatorExtractedFromService {
        /*
         * 🧪 Zadanie 16:
         * Wydziel walidacje e-maila i reputacji z UserService do osobnej klasy
         * UserValidator z metodami statycznymi (List<String> validate(email, name,
         * reputation) zwracajacymi liste WSZYSTKICH naruszonych regul). Zmodyfikuj
         * registerUser, by korzystal WYLACZNIE z UserValidator - DAO nie zna ani
         * jednej linii walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ReportBothDefenseLinesInOneRun {
        /*
         * 🧪 Zadanie 17:
         * Wykonaj sekwencje 6 rejestracji tak zaprojektowana, by 3 zostaly
         * odrzucone przez WALIDACJE W SERVICE, a 3 (mimo przejscia walidacji)
         * zostaly odrzucone przez OGRANICZENIA W BAZIE (duplikat e-maila po
         * poprawnym formacie). Zbierz i wypisz raport z podzialem: "odrzucone przez
         * Service" vs "odrzucone przez baze" vs "sukces".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddNotNullConstraintAndVerifyServiceCatchesFirst {
        /*
         * 🧪 Zadanie 18:
         * Sprawdz zachowanie dla name=null: Service powinien odrzucic PRZED
         * kontaktem z baza (walidacja "imie nie moze byc null/puste"). Napisz DRUGA
         * wersje testu, w ktorej OBEJDZIESZ walidacje Service (wywolanie DAO.insert
         * bezposrednio z name=null) i sprawdz, ze ograniczenie NOT NULL w bazie
         * TAKZE to wykrywa (zlapane SQLException).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PerformanceComparisonValidationVsNoValidation {
        /*
         * 🧪 Zadanie 19:
         * Zmierz (System.nanoTime()) czas wykonania 100 wywolan registerUser z
         * NIEPOPRAWNYMI danymi (pusty e-mail) DWOMA sposobami: (a) z walidacja w
         * Service (odrzucone natychmiast, bez SQL), (b) hipotetycznie bez walidacji
         * (symuluj to poprzez wywolanie DAO.insert wprost, ktore i tak zawiedzie na
         * NOT NULL w bazie). Porownaj czasy i skomentuj, dlaczego walidacja w
         * Service jest szybsza dla oczywistych bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullRegistrationReportWithBothDefenseLayers {
        /*
         * 🧪 Zadanie 20:
         * Wykonaj 12 rejestracji z pelnym zestawem mozliwych bledow (pusty email,
         * zly format, ujemna reputacja, zbyt krotkie imie, duplikat e-maila,
         * poprawne dane) i wypisz KOMPLETNY raport: liczba sukcesow, liczba
         * odrzucen przez Service (z rodzajem regoly), liczba odrzucen przez baze (z
         * SQLState).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ValidatorReturnsAllViolationsNotJustFirst {
        /*
         * 🧪 Zadanie 21:
         * Rozbuduj UserValidator (z Zadania 16), by validate() zwracalo WSZYSTKIE
         * naruszone reguly naraz (nie zatrzymuje sie na pierwszej). Wywolaj
         * registerUser dla danych naruszajacych JEDNOCZESNIE 3 reguly (pusty email,
         * ujemna reputacja, zbyt krotkie imie) i wypisz WSZYSTKIE 3 komunikaty w
         * jednej odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CustomCheckConstraintForEmailDomainWhitelist {
        /*
         * 🧪 Zadanie 22:
         * Dodaj do tabeli users CHECK ograniczenie wymagajace, by domena e-maila (po
         * "@") byla jedna z: "example.com", "firma.pl" (uzyj CHECK z LIKE, np. email
         * LIKE '%@example.com' OR email LIKE '%@firma.pl'). Przetestuj rejestracje
         * z domena dozwolona i niedozwolona - Service powinien przetlumaczyc
         * naruszenie CHECK na czytelny komunikat "domena e-maila nie jest
         * dozwolona".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConcurrentRegistrationsStressTestUniqueConstraint {
        /*
         * 🧪 Zadanie 23:
         * Na bazie "jdbc:h2:mem:lesson21ex23;DB_CLOSE_DELAY=-1" uruchom 5 WATKOW,
         * KAZDY probujacy zarejestrowac TEN SAM e-mail "wyscig@example.com" (z
         * WLASNYM polaczeniem kazdy). Poczekaj na zakonczenie wszystkich (join z
         * limitem czasu) i sprawdz SELECT COUNT(*) WHERE email = 'wyscig@...' -
         * MUSI wynosic dokladnie 1, niezaleznie od tego, ktory watek "wygral" wyscig
         * - dowod, ze TYLKO ograniczenie bazy gwarantuje to w 100%.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ValidationRulesConfigurableViaStrategyPattern {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj funkcyjny interfejs ValidationRule z metoda
         * Optional<String> check(String email, String name, int reputation).
         * Zbuduj LISTE reguł (email niepusty, format email, reputacja >=0, imie
         * min. 2 znaki) jako implementacje tego interfejsu i rozbuduj
         * UserValidator, by iterowal po LISCIE reguł zamiast miec je "na sztywno"
         * wpisane. Dodaj NOWA regule (np. max. dlugosc imienia) BEZ modyfikowania
         * istniejacego kodu walidacji - tylko dopisujac ja do listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AuditFailedValidationAttemptsSeparateTable {
        /*
         * 🧪 Zadanie 25:
         * Dodaj tabele validation_failures (id, email, reason) i zapisuj do niej
         * wpis KAZDYM razem, gdy walidacja w Service ODRZUCI rejestracje (osobny
         * insert, poza glowna logika rejestracji - moze byc na tym samym
         * polaczeniu, bez transakcji). Wykonaj 8 prob (mieszanka) i wypisz pelna
         * zawartosc validation_failures na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RegistrationWithRetryAfterConstraintViolation {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode registerWithSuffixRetry(baseEmail, name) - jesli
         * registerUser(baseEmail, name) zawiedzie z powodu duplikatu e-maila,
         * AUTOMATYCZNIE probuje ponownie z "baseEmail" zmodyfikowanym (np.
         * dopisujac "+1", "+2" przed "@") do 3 razy, az sie powiedzie albo skoncza
         * sie proby. Zademonstruj na e-mailu, ktory juz istnieje w bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CrossFieldValidationRule {
        /*
         * 🧪 Zadanie 27:
         * Dodaj regule "cross-field" (laczaca DWA pola): jesli reputation > 100, to
         * name MUSI zaczynac sie od "VIP_" (arbitralna, ale konkretna regula
         * biznesowa laczaca dwa pola). Zaimplementuj ja w UserValidator i przetestuj
         * na 3 kombinacjach: wysoka reputacja + poprawny prefiks, wysoka reputacja +
         * bez prefiksu (odrzucone), niska reputacja + bez prefiksu (ok, regula nie
         * dotyczy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransactionalValidationWithRollbackOnPartialBusinessCheck {
        /*
         * 🧪 Zadanie 28:
         * Rozbuduj rejestracje o DRUGI insert w TEJ SAMEJ transakcji: po insert do
         * users, wstaw wpis do tabeli welcome_bonus (user_id, amount) - jesli
         * naruszenie UNIQUE nastapi PRZY insertach users, caly blok (obejmujacy
         * OBA inserty) powinien byc wycofany (setAutoCommit(false)+rollback).
         * Zweryfikuj, ze przy duplikacie e-maila NIE powstaje "osierocony" wpis w
         * welcome_bonus.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ComprehensiveValidatorUnitTestsWithMiniRunner {
        /*
         * 🧪 Zadanie 29:
         * Napisz mini test-runner (bez bazy danych - testy TYLKO na UserValidator, bez
         * SQL) z co najmniej 6 testami sprawdzajacymi KAZDA regule z osobna (pusty
         * email, zly format, ujemna reputacja, krotkie imie, cross-field z Zadania
         * 27, oraz poprawny przypadek bez naruszen). Wypisz PASSED/FAILED dla
         * kazdego - podkresl w komentarzu, ze te testy NIE WYMAGAJA polaczenia z
         * baza danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullTwoLayerDefenseMiniApp {
        /*
         * 🧪 Zadanie 30:
         * Zloz kompletna "mini aplikacje" rejestracji: UserValidator (lista reguł,
         * Zadanie 24) jako pierwsza linia obrony, tabela users z UNIQUE+CHECK+NOT
         * NULL jako druga linia, audyt nieudanych prob (validation_failures,
         * Zadanie 25) i tlumaczenie SQLState na czytelne komunikaty. Zasymuluj 15
         * rejestracji o bardzo roznych, celowo skomponowanych danych i wypisz
         * KOMPLETNY raport koncowy (sukcesy / odrzucenia Service / odrzucenia bazy,
         * z rozbiciem na konkretna przyczyne kazdego odrzucenia).
         */
        public static void main(String[] args) { }
    }
}
