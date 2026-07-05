package com.example.javaquest._10_dao.Lesson28_JdbcBestPractices;

public class _Exercises_Lesson28_JdbcBestPractices {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PreparedStatementDefeatsInjectionAttempt {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson28ex01;DB_CLOSE_DELAY=-1" utworz tabele users
         * (id, name) z kilkoma wierszami. Odtworz Zasade 1 z lekcji: wywolaj
         * zapytanie SELECT * FROM users WHERE name = ? z parametrem "x' OR
         * '1'='1" przez PreparedStatement i sprawdz, ze wynik ma 0 wierszy (atak nie
         * zadzialal).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TryWithResourcesForAllThreeResources {
        /*
         * 🧪 Zadanie 2:
         * Odtworz Zasade 2: wykonaj SELECT COUNT(*) FROM users z Connection,
         * Statement i ResultSet WSZYSTKIMI trzema w JEDNYM try-with-resources.
         * Wypisz wynik i potwierdz w komentarzu, ze wszystkie 3 zasoby zamkna sie
         * automatycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ShortLivedConnectionPattern {
        /*
         * 🧪 Zadanie 3:
         * Odtworz Zasade 3: napisz metode countUsers(url) otwierajaca polaczenie
         * TUZ PRZED uzyciem i zamykajaca je NATYCHMIAST po (try-with-resources).
         * Wywolaj ja 3 razy z rzedu i sprawdz, ze KAZDE wywolanie samo otwiera i
         * zamyka WLASNE polaczenie (bez trzymania go "na zapas" w polu klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WhitelistValidatedDynamicOrderBy {
        /*
         * 🧪 Zadanie 4:
         * Odtworz Zasade 4: zdefiniuj Set<String> allowedColumns = Set.of("id",
         * "name"), zwaliduj requestedColumn="name" wzgledem tej listy PRZED
         * zbudowaniem "ORDER BY " + requestedColumn, i wykonaj zapytanie. Sprobuj
         * TAKZE z requestedColumn="haslo" (niedozwolona) - sprawdz, ze
         * IllegalArgumentException zostaje rzucony PRZED wykonaniem SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TransactionForMultiStepOperation {
        /*
         * 🧪 Zadanie 5:
         * Odtworz Zasade 5: wykonaj DWA UPDATE (np. zmiana imienia dwoch
         * uzytkownikow) w JEDNEJ transakcji (setAutoCommit(false), commit na
         * koncu, rollback+throw w catch). Sprawdz SELECT po operacji - obie zmiany
         * powinny byc widoczne razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LayeredArchitectureSkeletonComment {
        /*
         * 🧪 Zadanie 6:
         * Odtworz Zasade 6 jako DZIALAJACY, choc minimalny, przyklad: napisz
         * UserDao (SQL), UserService (wywoluje UserDao, ZERO SQL) i metode
         * "Controller" (wywoluje TYLKO UserService, ZERO SQL i ZERO Connection).
         * Wywolaj cala sciezke Controller->Service->Dao dla jednego zapytania o
         * uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_OptionalInsteadOfNullForSingleResult {
        /*
         * 🧪 Zadanie 7:
         * Odtworz Zasade 7 (czesc 1): napisz findUserNameById(connection, id) (jak w
         * lekcji, Optional<String>). Wywolaj dla istniejacego i nieistniejacego id -
         * sprawdz, ze DRUGI przypadek zwraca Optional.empty(), NIGDY null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_EmptyListInsteadOfNullForCollectionResult {
        /*
         * 🧪 Zadanie 8:
         * Odtworz Zasade 7 (czesc 2): napisz findUserNamesByPrefix(connection,
         * prefix) (jak w lekcji, List<String>). Wywolaj dla prefiksu, ktory NIC nie
         * dopasowuje ("Nieistniejacy") i sprawdz, ze wynik to PUSTA lista
         * (isEmpty()=true), NIGDY null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConfigurationOutsideCodeViaEnvironmentVariable {
        /*
         * 🧪 Zadanie 9:
         * Odtworz Zasade 8: odczytaj adres bazy z System.getenv().getOrDefault(
         * "DB_URL", "<wartosc domyslna dla tego zadania>") - NIGDY nie wpisuj
         * prawdziwego adresu produkcyjnego na sztywno w kodzie. Wypisz odczytana
         * wartosc i skomentuj, dlaczego takie podejscie jest bezpieczniejsze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConceptualConnectionPoolExplanation {
        /*
         * 🧪 Zadanie 10:
         * Odtworz Zasade 9 (koncepcyjnie): napisz metode
         * explainConnectionPoolBenefit() wypisujaca (jako log) porownanie: koszt
         * nawiazania NOWEGO polaczenia TCP kazdorazowo vs POZYCZANIE gotowego z puli.
         * Zmierz (System.nanoTime()) faktyczny czas otwarcia NOWEGO polaczenia H2 10
         * razy z rzedu i wypisz sredni czas jako ilustracje kosztu, ktory pool
         * eliminuje.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CombineRule1And2InOneDaoMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode DAO laczaca Zasady 1 i 2: PreparedStatement (bezpieczne
         * parametry) WEWNATRZ try-with-resources obejmujacego Connection, Statement
         * i ResultSet. Zademonstruj na zapytaniu z parametrem uzytkownika (nie
         * literalem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ViolateRule3ThenFixIt {
        /*
         * 🧪 Zadanie 12:
         * Napisz najpierw "zla" wersje NARUSZAJACA Zasade 3: klasa z polem
         * "private final Connection sharedConnection" otwieranym RAZ w konstruktorze
         * i trzymanym przez CALY czas zycia obiektu. Napraw ja, przenoszac otwieranie
         * polaczenia do KAZDEJ metody (try-with-resources), zamiast do pola.
         * Porownaj OBIE wersje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExtendWhitelistWithNewSortableColumn {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj przyklad z Zasady 4 o NOWA, dodatkowa dozwolona kolumne (np.
         * "created_at" - dodaj ta kolumne do tabeli users). Zademonstruj sortowanie
         * po niej po ROZSZERZENIU bialej listy, oraz sprawdz, ze PRZED
         * rozszerzeniem bylaby ona odrzucona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultiStepTransactionWithRollbackDemonstration {
        /*
         * 🧪 Zadanie 14:
         * Rozbuduj przyklad z Zasady 5 o TRZECI UPDATE naruszajacy ograniczenie
         * bazy (np. CHECK). Sprawdz, ze rollback cofa WSZYSTKIE trzy kroki (nawet
         * te 2, ktore "same" bylyby poprawne) - dowod atomowosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ServiceContainsBusinessLogicDaoDoesNot {
        /*
         * 🧪 Zadanie 15:
         * Rozbuduj przyklad z Zasady 6: dodaj do UserService regule biznesowa (np.
         * "imie musi miec min. 2 znaki") sprawdzana PRZED wywolaniem DAO. Sprawdz,
         * ze UserDao (SQL) NIE zawiera ani jednej linii tej reguly - jest CALKOWICIE
         * "niema" logiki biznesowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_NullSafeCallerCodeUsingOptional {
        /*
         * 🧪 Zadanie 16:
         * Napisz kod WYWOLUJACY findUserNameById (Zasada 7) demonstrujacy WLASCIWE
         * uzycie Optional: .map(), .orElse(), .ifPresentOrElse() - bez NIGDY
         * wywolywania .get() bez wczesniejszego sprawdzenia. Zademonstruj na
         * istniejacym i nieistniejacym id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ConfigurationViaPropertiesFileSimulation {
        /*
         * 🧪 Zadanie 17:
         * Rozbuduj Zasade 8: skonstruuj obiekt java.util.Properties RECZNIE w kodzie
         * (symulujac wczytanie z pliku application.properties) z kluczami "db.url",
         * "db.user" i uzyj ich do polaczenia z baza - bez ZADNEGO adresu/hasla
         * wpisanego na sztywno w logice polaczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SimulateConnectionPoolWithSimpleQueue {
        /*
         * 🧪 Zadanie 18:
         * Napisz UPROSZCZONY, wlasny "pool" polaczen: klasa SimpleConnectionPool z
         * WCZESNIEJ otwartymi 3 polaczeniami w kolejce (java.util.Queue), metodami
         * borrow() (zdejmuje z kolejki) i giveBack(connection) (dodaje z powrotem).
         * Zademonstruj "pozyczenie" i "oddanie" polaczenia 5 razy (wiecej razy niz
         * jest polaczen w puli, ale sekwencyjnie - nie rownolegle) - to koncepcyjna
         * ilustracja Zasady 9.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ChecklistValidatorForCodeReview {
        /*
         * 🧪 Zadanie 19:
         * Napisz klase CodeReviewChecklist z metoda statyczna
         * List<String> review(String sqlCode) - PROSTA analiza tekstowa (bez
         * pelnego parsera SQL) wykrywajaca "podejrzane" wzorce w Stringu z kodem
         * SQL, np. konkatenacja "+" w tekscie SQL (potencjalne zlamanie Zasady 1/4).
         * Przetestuj na 3 przykladowych Stringach kodu (2 "zle", 1 "dobry").
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummaryTestSuiteOneTestPerRule {
        /*
         * 🧪 Zadanie 20:
         * Napisz mini test-runner (jak w Lesson26_TestingDao) z DZIEWIECIOMA testami -
         * PO JEDNYM na kazda z 9 zasad z tej lekcji (np. "testRule1_PreparedStatement
         * BlocksInjection", "testRule7_ReturnsOptionalNotNull" itd.). Wypisz
         * PASSED/FAILED dla wszystkich 9.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorLegacyCodeApplyingAllNineRules {
        /*
         * 🧪 Zadanie 21:
         * Napisz najpierw "kod legacy" NARUSZAJACY jednoczesnie KILKA zasad naraz
         * (konkatenacja SQL, brak try-with-resources, polaczenie w polu klasy, SQL
         * w "kontrolerze", zwracanie null). Zrefaktoryzuj go KROK PO KROKU,
         * naprawiajac PO JEDNEJ zasadzie na raz, i po kazdym kroku uruchamiajac
         * (dzialajacy) kod, zeby pokazac, ze zachowanie sie NIE zmienia, tylko
         * jakosc kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullLayeredAppWithTransactionValidationAndPool {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj mala, WARSTWOWA aplikacje laczaca Zasady 5+6+9: Service
         * orkiestrujacy DWA DAO w JEDNEJ transakcji (Zasada 5), wywolywany z
         * "kontrolera" bez zadnego SQL (Zasada 6), korzystajacy z SimpleConnectionPool
         * z Zadania 18 (Zasada 9) zamiast DriverManager.getConnection przy kazdym
         * wywolaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SecurityAuditScriptCheckingForConcatenatedSql {
        /*
         * 🧪 Zadanie 23:
         * Rozbuduj CodeReviewChecklist z Zadania 19 o skanowanie WIELU "plikow" (tu:
         * symulowanych jako lista Stringow reprezentujacych zawartosc plikow) i
         * wypisanie RAPORTU bezpieczenstwa: dla kazdego "pliku" lista wykrytych
         * naruszen zasad z tej lekcji (przynajmniej Zasady 1 i 4). Przetestuj na 5
         * "plikach" o roznej jakosci kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PerformanceComparisonAllAntiPatternsVsBestPractices {
        /*
         * 🧪 Zadanie 24:
         * Zmierz (System.nanoTime()) czas wykonania 50 operacji biznesowych (kazda:
         * 2 zapytania) DWOMA sposobami: (a) "zle" - nowe polaczenie przy kazdym
         * zapytaniu (100 polaczen), bez transakcji, (b) "dobrze" - 1 polaczenie na
         * operacje biznesowa (z Zadania 18 SimpleConnectionPool), w transakcji.
         * Wypisz oba czasy i podsumuj, ktore konkretne zasady z tej lekcji
         * najbardziej wplynely na roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignDocumentJustifyingEachRuleWithOwnExample {
        /*
         * 🧪 Zadanie 25:
         * Dla KAZDEJ z 9 zasad napisz WLASNY (inny niz w lekcji), samodzielnie
         * zaprojektowany, dzialajacy przyklad ZLE/DOBRZE (analogicznie do
         * zasadaN_Xxx z lekcji, ale z INNYMI danymi/tabelami niz w orginale).
         * Wywolaj wszystkie 9 przykladow po kolei w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConcurrentAccessRespectingAllRulesSimultaneously {
        /*
         * 🧪 Zadanie 26:
         * Uruchom 4 WATKI, kazdy wykonujacy 10 operacji biznesowych PRZESTRZEGAJAC
         * WSZYSTKICH 9 zasad naraz (PreparedStatement, try-with-resources,
         * krotkotrwale polaczenia z puli, bezpieczne dynamiczne sortowanie,
         * transakcje, warstwowa architektura, Optional/pusta lista, konfiguracja z
         * env, connection pool). Poczekaj na zakonczenie (join z limitem) i sprawdz
         * koncowy stan danych na spojnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigrationPathFromViolatingCodeToCompliantCode {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj i zaimplementuj TRZY WERSJE tej samej funkcjonalnosci
         * (rejestracja uzytkownika) - wersja 1 (najgorsza, narusza wiele zasad),
         * wersja 2 (czesciowa poprawa - np. tylko PreparedStatement i
         * try-with-resources), wersja 3 (pelna zgodnosc z wszystkimi 9 zasadami).
         * Wywolaj wszystkie trzy dla tych samych danych i porownaj w komentarzu
         * KOLEJNE etapy poprawy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AutomatedComplianceReportAcrossWholeMiniApp {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj mala, kompletna "mini-aplikacje" (DAO+Service+Controller dla 2
         * encji) i napisz AUTOMATYCZNY test (mini-runner) weryfikujacy zgodnosc z
         * KAZDA z 9 zasad (np. przez refleksje sprawdzajaca sygnatury metod DAO -
         * brak "throws SQLException" na zewnatrz, zwracane typy Optional/List, etc.)
         * Wypisz raport zgodnosci - PASSED/FAILED per zasada.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TeachingExampleWithDeliberateAntiPatternsAnnotated {
        /*
         * 🧪 Zadanie 29:
         * Napisz "materiał szkoleniowy" - klasa z metoda showAllAntiPatterns()
         * demonstrujaca (w DZIALAJACYM, choc kontrolowanym kodzie) WSZYSTKIE 9
         * antywzorcow naraz w jednej metodzie (jak w komentarzach "❌ ZLE" z lekcji,
         * ale FAKTYCZNIE WYKONANYCH, nie tylko jako komentarz), a nastepnie
         * showAllBestPractices() z poprawionymi wersjami. Porownaj wyniki i policz
         * liczbe otwartych polaczen w kazdej wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMiniAppSummarizingWholeDaoChapter {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj "capstone" mini-aplikacje podsumowujaca CALY rozdzial _10_dao:
         * warstwa DAO+Service+Controller (Zasada 6), transakcje (Zasada 5), Unit of
         * Work (nawiazujac do Lesson19), obsluga bledow z wlasnymi wyjatkami (Zasada
         * 7, nawiazujac do Lesson20), walidacja przed zapisem (nawiazujac do
         * Lesson21), paginacja+sortowanie+filtrowanie (nawiazujac do Lesson22-24),
         * connection pool (Zasada 9) i logowanie SQL (nawiazujac do Lesson27).
         * Zasymuluj realistyczny scenariusz (np. 10 operacji na "sklepie
         * internetowym") wykorzystujacy WIEKSZOSC tych elementow i wypisz KOMPLETNY
         * raport podsumowujacy caly przebieg.
         */
        public static void main(String[] args) { }
    }
}
