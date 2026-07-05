package com.example.javaquest._09_jdbc.Lesson14_SqlInjection;

public class _Exercises_Lesson14_SqlInjection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_NormalUsageOfUnsafeMethod {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l14ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "users" (id, email, name) z 3 wierszami. Napisz metodę
         * findByEmailUnsafe(Connection, String email) (konkatenacja
         * Stringów, jak w lekcji) i wywołaj ją z NORMALNYM, bezpiecznym
         * adresem email - potwierdź, że działa poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ClassicOrAttackAgainstUnsafeMethod {
        /*
         * 🧪 Zadanie 2:
         * Używając findByEmailUnsafe z Zadania 1, wywołaj ją z
         * wstrzykniętym parametrem "cokolwiek' OR '1'='1". Wypisz liczbę
         * zwróconych wierszy - powinny wyciekać WSZYSCY użytkownicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SameAttackAgainstSafeMethod {
        /*
         * 🧪 Zadanie 3:
         * Napisz metodę findByEmailSafe(Connection, String email) (przez
         * PreparedStatement). Wywołaj ją z TYM SAMYM wstrzykniętym
         * parametrem z Zadania 2 - wypisz liczbę zwróconych wierszy
         * (powinna być 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UnsafeSearchByName {
        /*
         * 🧪 Zadanie 4:
         * Napisz metodę findByNameUnsafe(Connection, String name)
         * (konkatenacja) i zademonstruj TEN SAM atak "' OR '1'='1" na
         * kolumnie name - wypisz liczbę wyciekłych wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SafeSearchByName {
        /*
         * 🧪 Zadanie 5:
         * Napisz bezpieczną wersję findByNameSafe(Connection, String
         * name) przez PreparedStatement. Zademonstruj, że TEN SAM atak
         * z Zadania 4 zwraca 0 wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CommentBasedInjectionVariant {
        /*
         * 🧪 Zadanie 6:
         * Wywołaj findByEmailUnsafe z payloadem "cokolwiek' OR '1'='1' --"
         * (wariant z komentarzem SQL na końcu, "wyciszającym" resztę
         * zapytania). Wypisz zbudowany SQL oraz liczbę wyciekłych wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UnionBasedAttackLeaksPasswords {
        /*
         * 🧪 Zadanie 7:
         * Na tabeli "users" (id, email, name, password) zademonstruj atak
         * typu UNION: wywołaj findByEmailUnsafe z payloadem "nieistnieje'
         * UNION SELECT id, password, password, password FROM users --"
         * (dopasuj liczbę kolumn do swojej tabeli). Wypisz zbudowany SQL
         * i wyciekłe hasła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SafeMethodBlocksUnionAttack {
        /*
         * 🧪 Zadanie 8:
         * Wywołaj findByEmailSafe z TYM SAMYM payloadem UNION z Zadania
         * 7. Wypisz liczbę zwróconych wierszy (powinna być 0) -
         * PreparedStatement traktuje cały string jako literalny tekst
         * do wyszukania, nie jako SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UnsafeLoginBypassAttack {
        /*
         * 🧪 Zadanie 9:
         * Napisz metodę loginUnsafe(Connection, String username, String
         * password) budującą SQL przez konkatenację obu parametrów
         * ("WHERE username = '...' AND password = '...'"). Zademonstruj
         * ominięcie logowania podając username = "admin' --" i JAKIKOLWIEK
         * password - wypisz, że logowanie się "powiodło" bez znajomości
         * prawdziwego hasła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SafeLoginBlocksBypassAttempt {
        /*
         * 🧪 Zadanie 10:
         * Napisz bezpieczną wersję loginSafe(Connection, String username,
         * String password) przez PreparedStatement. Zademonstruj TĘ SAMĄ
         * próbę ominięcia z Zadania 9 - wypisz, że logowanie się nie
         * powiodło (0 wyników).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BatchOfPayloadsComparisonReport {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj List<String> zawierającą co najmniej 5 różnych
         * payloadów atakujących (z Zadań 2, 6, 7 i innych). Przetestuj
         * KAŻDY z nich przeciw findByEmailUnsafe i findByEmailSafe,
         * wypisując sformatowaną tabelę: payload | liczba wierszy
         * (unsafe) | liczba wierszy (safe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NaiveSanitizationIsUnreliable {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę sanitizeNaively(String input), która próbuje
         * "oczyścić" input, usuwając WSZYSTKIE apostrofy (replace("'", "")).
         * Użyj jej PRZED wywołaniem findByEmailUnsafe z payloadem "' OR
         * '1'='1" - sprawdź, czy atak nadal działa w innej formie (np.
         * bez apostrofów, przez UNION z liczbami) LUB skomentuj
         * (println), dlaczego samodzielne "łatanie" konkatenacji jest
         * kruche i podatne na nowe warianty ataku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_InjectionRiskInDeleteOperation {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę deleteByEmailUnsafe(Connection, String email)
         * (konkatenacja). Zademonstruj, że payload "x' OR '1'='1" usuwa
         * WSZYSTKIE wiersze z tabeli. Napisz bezpieczną wersję
         * deleteByEmailSafe (PreparedStatement) i pokaż, że TEN SAM
         * payload usuwa 0 wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_StoredValueSafelyReusedLater {
        /*
         * 🧪 Zadanie 14:
         * Wstaw BEZPIECZNIE (przez PreparedStatement) użytkownika, którego
         * pole "name" zawiera tekst wyglądający jak SQL (np. "Robert';
         * DROP TABLE users; --"). Odczytaj go później i użyj tej wartości
         * jako parametru w INNYM bezpiecznym zapytaniu (WHERE name = ?) -
         * potwierdź, że wszystko działa normalnie, bez żadnego ryzyka,
         * dopóki konsekwentnie używamy PreparedStatement.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_OrderByCannotBeParameterizedNeedsWhitelist {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę findAllOrderedBy(Connection, String columnName)
         * budującą "SELECT * FROM users ORDER BY " + columnName (nazwa
         * kolumny NIE MOŻE być parametrem `?` w standardowym SQL).
         * Zademonstruj, że payload jako columnName mógłby być
         * niebezpieczny, a POPRAWNYM zabezpieczeniem jest sprawdzenie
         * columnName względem WHITELISTY dozwolonych nazw (np.
         * Set.of("id","email","name")) PRZED zbudowaniem SQL - zaimplementuj
         * tę walidację i przetestuj dla dozwolonej i niedozwolonej nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LikePatternWithWildcardsIsSafe {
        /*
         * 🧪 Zadanie 16:
         * Wywołaj bezpieczną metodę wyszukującą "WHERE email LIKE ?" z
         * parametrem zawierającym znaki wieloznaczne bezpośrednio od
         * "uzytkownika" (np. "%' OR '1'='1' --%"). Wypisz wynik i
         * potwierdź, że PreparedStatement bezpiecznie traktuje CAŁY ten
         * string (łącznie z % i apostrofami) jako dosłowny wzorzec LIKE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultiStatementInjectionAttemptObserved {
        /*
         * 🧪 Zadanie 17:
         * Wywołaj findByEmailUnsafe z payloadem "x'; DROP TABLE users; --"
         * (próba wielu poleceń SQL naraz). Złap ewentualny SQLException
         * (typowo błąd składni, bo executeQuery/statement w JDBC/H2 nie
         * wykonuje wielu instrukcji w jednym wywołaniu) i wypisz wynik -
         * skomentuj (println), że mechanika ochrony różni się między
         * driverami/bazami, więc i tak NIGDY nie warto polegać na
         * konkatenacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ComparisonTableForMultiplePayloads {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę testPayloads(Connection, List<String> payloads),
         * która dla KAŻDEGO payloadu wywołuje findByEmailUnsafe i
         * findByEmailSafe, wypisując sformatowany wiersz raportu (payload
         * ucięty do 40 znaków | liczba wierszy unsafe | liczba wierszy
         * safe | "BEZPIECZNY"/"PODATNY"). Przetestuj na co najmniej 4
         * payloadach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PreparedStatementHandlesWeirdCharacters {
        /*
         * 🧪 Zadanie 19:
         * Wstaw bezpiecznie użytkownika, którego email zawiera nietypowe
         * znaki: apostrof, backslash, średnik i cudzysłów (np.
         * "o'br\\ien\";--@example.com"). Odczytaj go z powrotem przez
         * findByEmailSafe z TYM SAMYM stringiem jako parametr - potwierdź,
         * że zostaje znaleziony poprawnie, bez żadnych problemów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NumericInjectionWithoutQuotes {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę findByIdUnsafe(Connection, String idAsText)
         * budującą "WHERE id = " + idAsText (BEZ cudzysłowów, bo id to
         * liczba). Zademonstruj atak z idAsText = "1 OR 1=1" - wypisz
         * liczbę wyciekłych wierszy. Napisz bezpieczną wersję
         * findByIdSafe(Connection, int id) (setInt) i skomentuj, że
         * przyjęcie parametru jako int (a nie String) w Javie ELIMINUJE
         * ten konkretny atak jeszcze PRZED dotknięciem SQL.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullAttackSimulationHarness {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj listę 7-8 klasycznych payloadów (bypass logowania,
         * UNION, komentarz, zawsze-prawda, numeryczny bez cudzysłowów).
         * Uruchom je WSZYSTKIE przeciw BEZPIECZNEJ implementacji
         * wyszukiwania (PreparedStatement) i wypisz raport bezpieczeństwa
         * potwierdzający, że WSZYSTKIE zwracają 0 nieoczekiwanych wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DefenseInDepthWithInputValidation {
        /*
         * 🧪 Zadanie 22:
         * Do findByEmailSafe (PreparedStatement) dodaj DODATKOWĄ walidację
         * formatu email (prosty regex z "@") PRZED wykonaniem zapytania -
         * jeśli format jest niepoprawny, rzuć IllegalArgumentException
         * BEZ dotykania bazy. Skomentuj (println), że PreparedStatement
         * sam wystarcza do bezpieczeństwa przed SQL Injection, ale
         * walidacja danych ma sens z innych powodów (jakość danych,
         * czytelne komunikaty błędów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BlacklistFilterBypassDemo {
        /*
         * 🧪 Zadanie 23:
         * Napisz naiwny filtr attemptBlacklistFilter(String input),
         * odrzucający stringi zawierające słowa "OR", "UNION", "--"
         * (dokładne dopasowanie wielkości liter). Zademonstruj OBEJŚCIE
         * tego filtra payloadem używającym innej wielkości liter (np.
         * "cokolwiek' oR '1'='1") lub innej techniki - wypisz, że filtr
         * przepuszcza szkodliwy input, i skomentuj, dlaczego czarne
         * listy słów kluczowych NIE są wystarczającą ochroną (w
         * przeciwieństwie do PreparedStatement).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SafeDynamicInClauseVsUnsafeConcatenation {
        /*
         * 🧪 Zadanie 24:
         * Napisz DWIE metody findByIdsUnsafe(Connection, String
         * rawCommaSeparatedIds) (konkatenacja bezpośrednio do "WHERE id
         * IN (...)") oraz findByIdsSafe(Connection, List<Integer> ids)
         * (dynamicznie budowane placeholdery `?`, jak w Lesson08).
         * Zademonstruj, że rawCommaSeparatedIds = "1) OR (1=1" psuje
         * bezpieczeństwo wersji unsafe, podczas gdy wersja safe przyjmuje
         * TYLKO listę int-ów (typ Javy eliminuje ten atak na starcie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_HeuristicSqlConcatenationLinter {
        /*
         * 🧪 Zadanie 25:
         * Napisz METODĘ EDUKACYJNĄ auditSourceSnippet(String codeSnippet),
         * która (jako uproszczony, ILUSTRACYJNY "linter") sprawdza, czy
         * podany fragment kodu (jako String reprezentujący linię kodu)
         * zawiera wzorzec typowy dla łączenia SQL przez konkatenację
         * (np. zawiera "SELECT" oraz znak "+" w tej samej linii) - jeśli
         * tak, wypisuje ostrzeżenie "Potencjalne ryzyko SQL Injection".
         * Przetestuj na 2 przykładowych liniach (jednej "podatnej", jednej
         * z PreparedStatement).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FullLoginBypassNarrativeDemo {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj pełną narrację ataku: atakujący NIE ZNA żadnego
         * prawdziwego hasła administratora. Użyj loginUnsafe z Zadania 9
         * z username="admin' --" i password="cokolwiek" - wypisz krok po
         * kroku (zbudowany SQL, wynik zapytania, komunikat "ZALOGOWANO
         * JAKO ADMIN BEZ ZNANIA HASLA"). Następnie powtórz identyczny
         * scenariusz na loginSafe - wypisz "LOGOWANIE ODRZUCONE".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SqlInjectionSafeButAuthorizationUnsafe {
        /*
         * 🧪 Zadanie 27:
         * Napisz BEZPIECZNĄ (PreparedStatement) metodę findOrderById(Connection,
         * int orderId) zwracającą zamówienie PO ID, ale BEZ sprawdzenia,
         * czy zamówienie należy do aktualnie "zalogowanego" użytkownika
         * (brak warunku WHERE user_id = ?). Zademonstruj, że użytkownik A
         * może odczytać zamówienie użytkownika B, mimo że kod jest
         * CAŁKOWICIE odporny na SQL Injection - to inny, NIEZALEŻNY
         * problem bezpieczeństwa (autoryzacja, nie SQL Injection).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_InjectionRegressionTestSuite {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę runInjectionRegressionTests(Connection),
         * wykonującą 5 payloadów z Zadania 11/21 przeciw findByEmailSafe
         * i sprawdzającą (assert-podobny println: "PASS"/"FAIL"), że
         * KAŻDY zwraca 0 nieoczekiwanych wierszy. Wypisz finalne
         * podsumowanie "X/5 testow zaliczonych".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ManualEscapingIsIncompleteDefense {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę improvedButStillNotIdeal(Connection, String email)
         * ręcznie "escapującą" apostrofy przez podwojenie (' -> ''),
         * zamiast używać PreparedStatement. Zademonstruj, że payload z
         * pojedynczym apostrofem ("' OR '1'='1") jest teraz bezpieczny,
         * ale znajdź/skonstruuj INNY sposób na obejście tego RĘCZNEGO
         * escapowania (albo skomentuj konkretny scenariusz, w którym
         * ręczne escapowanie zawodzi, np. inne kodowanie znaków) -
         * wniosek: PreparedStatement to KOMPLETNE rozwiązanie, ręczne
         * escapowanie zawsze niesie ryzyko przeoczenia przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneSecurityAuditClass {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klasę SecurityAudit, która uruchamia PEŁNY zestaw
         * payloadów atakujących (z Zadania 21) przeciw TRZEM różnym,
         * BEZPIECZNYM metodom repozytorium (wyszukiwanie po email,
         * wyszukiwanie po nazwie, logowanie), a na końcu DOLICZA do
         * raportu JEDNĄ celowo pozostawioną NIEBEZPIECZNĄ metodę
         * (findByEmailUnsafe), oznaczoną w raporcie jako "PODATNA -
         * WYLACZNIE DO CELOW EDUKACYJNYCH". Wypisz finalny raport
         * bezpieczeństwa porównujący wszystkie 4 metody.
         */
        public static void main(String[] args) { }
    }
}
