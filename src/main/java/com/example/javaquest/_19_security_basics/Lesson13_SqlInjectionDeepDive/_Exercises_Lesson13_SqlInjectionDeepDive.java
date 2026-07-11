package com.example.javaquest._19_security_basics.Lesson13_SqlInjectionDeepDive;

public class _Exercises_Lesson13_SqlInjectionDeepDive {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainUnionBasedInjectionInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, na czym polega
         * UNION-based SQL injection - jaki warunek MUSI byc spelniony,
         * zeby zadzialalo (liczba/typy kolumn).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SetupUsersAndProductsTablesInH2 {
        /*
         * 🧪 Zadanie 2:
         * Uzyj H2 in-memory do stworzenia tabel `users` i `products` (jak
         * w teorii) - wypelnij przykladowymi danymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BuildVulnerableSearchQuery {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj PODATNE zapytanie wyszukiwania produktow (konkatenacja
         * inputu) - wykonaj je z normalnym (niezlosliwym) inputem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExploitWithUnionSelect {
        /*
         * 🧪 Zadanie 4:
         * Wykorzystaj zapytanie z Zadania 3 z payloadem `UNION SELECT`
         * wydobywajacym dane z tabeli `users` - wypisz wyciekniete dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixWithPreparedStatement {
        /*
         * 🧪 Zadanie 5:
         * Napraw zapytanie z Zadania 3 przez `PreparedStatement` -
         * zweryfikuj, ze TEN SAM payload z Zadania 4 NIE dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainBlindBooleanBasedInjectionInOwnWords {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij wlasnymi slowami blind boolean-based
         * injection - kiedy atakujacy MUSI po nia siegnac (zamiast
         * UNION-based).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BuildFoundNotFoundEndpoint {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj metode zwracajaca TYLKO true/false ("znaleziono"/
         * "nie znaleziono") dla PODATNEGO zapytania sprawdzajacego istnienie
         * uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExtractOneCharacterViaBooleanProbe {
        /*
         * 🧪 Zadanie 8:
         * Uzyj metody z Zadania 7 do odgadniecia 1. znaku hasha admina -
         * jak w teorii, iterujac po alfabecie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainSecondOrderInjectionInOwnWords {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij wlasnymi slowami second-order injection -
         * dlaczego "dane juz sa w naszej bazie" to BLEDNE zalozenie
         * bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IdentifyWhichQueriesInAppAreVulnerable {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: majac liste 5 przykladowych fragmentow kodu
         * (niektore z PreparedStatement, niektore z konkatenacja),
         * wskaz, ktore sa PODATNE.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ExtractFullPasswordHashCharacterByCharacter {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj Zadanie 8 do PELNEGO odtworzenia calego hasha admina
         * (petla po WSZYSTKICH pozycjach) - wypisz odtworzona wartosc i
         * porownaj z prawdziwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureQueriesNeededForBlindExtraction {
        /*
         * 🧪 Zadanie 12:
         * Policz, ile ZAPYTAN potrzebowal atak z Zadania 11 - skomentuj,
         * dlaczego blind injection jest WOLNIEJSZY niz UNION-based, ale
         * WCIAZ realistycznym zagrozeniem (mozna zautomatyzowac).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementTimeBasedBlindInjectionConceptually {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala (lub z prostym przykladem `SLEEP`/`PG_SLEEP` w
         * komentarzu): opisz "time-based blind injection" - gdy nawet
         * BRAK roznicy w tresci odpowiedzi nie przeszkadza atakujacemu,
         * bo mierzy on CZAS odpowiedzi (opoznienie = warunek prawdziwy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateSecondOrderWithStoredUsername {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj (jak w teorii) zapis username przez
         * PreparedStatement, a nastepnie PODATNE uzycie go w INNYM
         * zapytaniu - zweryfikuj skutek ataku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FixSecondOrderInjectionEverywhere {
        /*
         * 🧪 Zadanie 15:
         * Napraw PODATNE, DRUGIE zapytanie z Zadania 14 przez
         * `PreparedStatement` - zweryfikuj, ze zlosliwy username juz NIE
         * dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DetectInjectionAttemptsViaInputPatterns {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj PROSTA (i jawnie oznaczona jako NIEWYSTARCZAJACA
         * SAMA W SOBIE) heurystyke wykrywania podejrzanych wzorcow w
         * inputcie (np. `--`, `UNION`, `;DROP`) - wyjasnij, dlaczego to
         * TYLKO dodatkowa warstwa (WAF-owa), NIE zastepuje
         * PreparedStatement.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ApplyLeastPrivilegeToDbAccount {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj (opisz w komentarzu) konto bazodanowe aplikacji z
         * uprawnieniami TYLKO SELECT/INSERT/UPDATE na WLASCIWYCH
         * tabelach (bez `DROP`/`ALTER`/dostepu do innych schematow) -
         * powiaz z Lesson07 (zasada najmniejszych uprawnien).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestUnionInjectionWithMismatchedColumnCount {
        /*
         * 🧪 Zadanie 18:
         * Sprobuj UNION-based injection z NIEPRAWIDLOWA liczba kolumn -
         * zweryfikuj i wypisz blad SQL, ktory atakujacy widzialby PRZED
         * dopasowaniem poprawnej liczby (typowy proces "prob i bledow").
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementParameterizedDynamicOrderBy {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj bezpieczne dynamiczne `ORDER BY` (kolumna wybierana przez
         * uzytkownika) - wyjasnij, dlaczego NAZW KOLUMN NIE MOZNA
         * parametryzowac przez `PreparedStatement` (tylko WARTOSCI) i
         * jak temu zaradzic (biala lista dozwolonych nazw kolumn).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareErrorBasedInjectionTechnique {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zbadaj i opisz "error-based injection" - gdy
         * atakujacy CELOWO wywoluje bledy SQL, ktorych TRESC (np. numer
         * wersji bazy w komunikacie bledu) ujawnia informacje o systemie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullAutomatedBlindExtractionTool {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj (w celach edukacyjnych, na WLASNEJ, kontrolowanej bazie
         * H2) "narzedzie" automatyzujace pelne wydobycie hasha admina -
         * zmierz calkowity czas i liczbe zapytan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementBinarySearchOptimizationForBlindInjection {
        /*
         * 🧪 Zadanie 22:
         * Zoptymalizuj Zadanie 21 - zamiast iterowac PO KOLEI caly
         * alfabet per znak, uzyj wyszukiwania BINARNEGO po kodzie ASCII
         * znaku (`ASCII(SUBSTRING(...))>X`) - zmierz redukcje liczby
         * zapytan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DetectSecondOrderInjectionAcrossMultipleModules {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj wieksza aplikacje z 3 "modulami" (rejestracja,
         * powiadomienia, raporty) - dane zapisane BEZPIECZNIE w module
         * rejestracji, ale UZYTE niebezpiecznie w module raportow -
         * wykryj i napraw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementParameterizedStoredProcedureCall {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj wywolanie procedury skladowanej H2
         * (`CallableStatement`) z parametrami - zweryfikuj, ze RÓWNIEZ
         * ten mechanizm jest odporny na injection przy poprawnym uzyciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildWafStyleInputFilterAndBypassIt {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj prosty filtr blokujacy slowo "UNION" (wielkimi
         * literami) w inpucie - nastepnie ZADEMONSTRUJ jego OMINIECIE
         * (np. `uNiOn`/komentarze SQL rozdzielajace slowo) - wyjasnij,
         * dlaczego filtrowanie wzorcow NIGDY nie jest niezawodne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementQueryLoggingForForensicAnalysis {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj logowanie WSZYSTKICH wykonanych zapytan SQL (z
         * czasem i "zrodlem" - ktory endpoint) - wyjasnij, jak taki log
         * pomaga PO FAKCIE wykryc probe/skutki ataku (zapowiedz Lesson19).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareOrmProtectionWithRawJdbc {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: wyjasnij, dlaczego Hibernate/JPA (`_12_hibernate`)
         * z HQL/Criteria API/parametrami nazwanymi jest z NATURY bezpieczniejszy
         * niz reczny JDBC - i w JAKIM przypadku (native SQL query z
         * konkatenacja) NAWET Hibernate MOZE byc podatny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDefenseInDepthForSqlInjection {
        /*
         * 🧪 Zadanie 28:
         * Polacz WSZYSTKIE warstwy obrony: PreparedStatement (wszedzie),
         * najmniejsze uprawnienia konta DB (Zadanie 17), biala lista dla
         * dynamicznych nazw kolumn (Zadanie 19), logowanie zapytan
         * (Zadanie 26) - zaprojektuj (opisz) kompletna strategie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SimulateFullAttackChainFromUnionToDataExfiltration {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj pelny lancuch ataku: rozpoznanie liczby kolumn (Zadanie
         * 18) -> UNION-based wydobycie danych (Zadanie 4) -> proba
         * modyfikacji danych second-order (Zadanie 14) - wypisz czytelny
         * log calego lancucha jako "raport z testu penetracyjnego".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureQueryLayerWithTests {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna, bezpieczna warstwe dostepu do danych (DAO)
         * odporna na WSZYSTKIE 3 techniki z tej lekcji - napisz
         * "testy" (w `main()`) probujace kazdy atak i weryfikujace, ze
         * ZADEN sie nie powiodl.
         */
        public static void main(String[] args) { }
    }
}
