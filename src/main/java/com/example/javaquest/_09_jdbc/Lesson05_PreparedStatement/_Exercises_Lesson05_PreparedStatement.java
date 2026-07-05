package com.example.javaquest._09_jdbc.Lesson05_PreparedStatement;

public class _Exercises_Lesson05_PreparedStatement {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InsertEmployeeWithSetters {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l05ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "employees" (id INT, name VARCHAR(100), salary DECIMAL(10,2)).
         * Wstaw jeden wiersz przez PreparedStatement, używając setInt(),
         * setString() i setBigDecimal(). Wypisz liczbę zmienionych
         * wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReuseSameStatementForMultipleInserts {
        /*
         * 🧪 Zadanie 2:
         * Na tabeli "employees" (id, name, salary) użyj JEDNEGO obiektu
         * PreparedStatement do wstawienia 5 różnych wierszy w pętli,
         * zmieniając parametry przez settery przed każdym executeUpdate().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SelectByNameParameter {
        /*
         * 🧪 Zadanie 3:
         * Na tabeli "employees" z co najmniej 3 wierszami, wykonaj SELECT
         * z parametrem "WHERE name = ?" przez PreparedStatement i wypisz
         * znaleziony wiersz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SetDateFromLocalDate {
        /*
         * 🧪 Zadanie 4:
         * Na tabeli "employees" (id, name, birth_date DATE) wstaw wiersz,
         * konwertując java.time.LocalDate na java.sql.Date przez
         * Date.valueOf(...) i ustawiając go przez ps.setDate(). Odczytaj
         * wiersz i wypisz datę urodzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SetTimestampFromLocalDateTime {
        /*
         * 🧪 Zadanie 5:
         * Na tabeli "employees" (id, name, hired_at TIMESTAMP) wstaw
         * wiersz, konwertując java.time.LocalDateTime na
         * java.sql.Timestamp przez Timestamp.valueOf(...) i ustawiając
         * go przez ps.setTimestamp(). Odczytaj i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SetBigDecimalForSalary {
        /*
         * 🧪 Zadanie 6:
         * Na tabeli "employees" (id, name, salary DECIMAL(10,2)) wstaw 3
         * wiersze z różnymi wartościami salary przez ps.setBigDecimal().
         * Odczytaj je i wypisz wszystkie salda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ParameterIndexOrderMatters {
        /*
         * 🧪 Zadanie 7:
         * Na tabeli "employees" (id, name, salary) napisz INSERT z
         * parametrami w kolejności (id, name, salary). Wstaw wiersz
         * poprawnie ustawiając parametry 1, 2, 3 w tej właśnie
         * kolejności, a następnie wypisz go, żeby potwierdzić, że
         * indeksy PreparedStatement liczą się OD 1, nie od 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SetObjectWithSqlType {
        /*
         * 🧪 Zadanie 8:
         * Na tabeli "employees" (id, name, hired_at TIMESTAMP) wstaw
         * wiersz, ustawiając hired_at przez ps.setObject(indeks,
         * Timestamp.valueOf(...), Types.TIMESTAMP) - alternatywę dla
         * setTimestamp(). Odczytaj i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_InsertRowWithNullOptionalColumn {
        /*
         * 🧪 Zadanie 9:
         * Na tabeli "employees" (id, name, birth_date DATE - kolumna
         * opcjonalna, bez NOT NULL) wstaw wiersz, w którym birth_date jest
         * NULL - użyj ps.setNull(indeks, Types.DATE). Odczytaj wiersz i
         * wypisz wartość birth_date (powinno być null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SearchByExactStringParameterTreatedLiterally {
        /*
         * 🧪 Zadanie 10:
         * Na tabeli "employees" z co najmniej 2 wierszami wykonaj SELECT
         * z parametrem "WHERE name = ?" podając jako wartość parametru
         * "ktos' OR '1'='1" (tekst z apostrofami). Wypisz liczbę
         * znalezionych wierszy (powinno być 0) - potwierdzenie, że
         * PreparedStatement traktuje cały string jako dosłowny tekst.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InsertEmployeeMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę insertEmployee(Connection conn, int id, String
         * name, BigDecimal salary, LocalDate birthDate, LocalDateTime
         * hiredAt) wstawiającą kompletny wiersz przez PreparedStatement z
         * odpowiednimi konwersjami typów. Wywołaj ją 2 razy z różnymi
         * danymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FindByNameMethod {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę boolean existsByName(Connection conn, String
         * name) sprawdzającą (przez PreparedStatement + SELECT COUNT(*)
         * WHERE name = ?) czy pracownik o podanej nazwie istnieje.
         * Przetestuj dla istniejącego i nieistniejącego imienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UpdateSalaryWithTwoParameters {
        /*
         * 🧪 Zadanie 13:
         * Na tabeli "employees" wykonaj UPDATE ustawiający nową wartość
         * salary (parametr 1) WHERE id = ? (parametr 2) przez
         * PreparedStatement. Wypisz liczbę zaktualizowanych wierszy oraz
         * nowe salary po zmianie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DeleteByIdParameter {
        /*
         * 🧪 Zadanie 14:
         * Na tabeli "employees" wykonaj DELETE FROM employees WHERE id =
         * ? przez PreparedStatement, wypisz liczbę usuniętych wierszy, a
         * potem SELECT COUNT(*) potwierdzający zmniejszenie liczby
         * wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RangeQueryWithTwoParameters {
        /*
         * 🧪 Zadanie 15:
         * Na tabeli "employees" z 5 wierszami o różnych salary, wykonaj
         * SELECT z "WHERE salary BETWEEN ? AND ?" (dwa parametry) i
         * wypisz znalezione wiersze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CombinedAndConditionWithTwoParameters {
        /*
         * 🧪 Zadanie 16:
         * Na tabeli "employees" (id, name, salary, department VARCHAR(50))
         * wykonaj SELECT z "WHERE department = ? AND salary > ?" (dwa
         * różne parametry). Przetestuj z konkretnymi wartościami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LoopInsertFromArrayOfData {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj tablicę/listę obiektów (np. String[][] albo listę
         * rekordów) z danymi 6 pracowników. Użyj JEDNEGO
         * PreparedStatement w pętli, wstawiając każdy element tablicy
         * jako osobny wiersz. Zweryfikuj przez SELECT COUNT(*).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareStatementVsPreparedStatementSameQuery {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj TO SAMO zapytanie SELECT z parametrem name dwoma
         * sposobami: przez Statement + konkatenację Stringów oraz przez
         * PreparedStatement + setString(). Porównaj (println) wyniki -
         * powinny być identyczne dla normalnej, bezpiecznej wartości
         * wejściowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ClearParametersAndReuse {
        /*
         * 🧪 Zadanie 19:
         * Utwórz PreparedStatement do INSERT-a, ustaw parametry i wykonaj
         * executeUpdate(). Następnie wywołaj ps.clearParameters(), ustaw
         * NOWE wartości i wykonaj executeUpdate() jeszcze raz na tym
         * samym obiekcie. Zweryfikuj SELECT-em, że oba wiersze zostały
         * poprawnie wstawione.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NullableDateColumnHandling {
        /*
         * 🧪 Zadanie 20:
         * Na tabeli "employees" (id, name, hired_at TIMESTAMP - kolumna
         * opcjonalna) wstaw dwóch pracowników: jednego z prawdziwą datą
         * zatrudnienia (setTimestamp) i jednego jeszcze niezatrudnionego
         * (setNull(indeks, Types.TIMESTAMP)). Odczytaj obu i wypisz różnicę.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BulkInsertFromArrayWithVerification {
        /*
         * 🧪 Zadanie 21:
         * Wygeneruj tablicę 50 obiektów danych pracowników (id, name,
         * salary generowane programowo, np. "Pracownik " + i). Wstaw je
         * wszystkie przez JEDEN reużywany PreparedStatement w pętli.
         * Zweryfikuj SELECT COUNT(*) oraz SELECT SUM(salary), porównując
         * z sumą wyliczoną w Javie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GenericParameterBinderMethod {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę bindParameter(PreparedStatement ps, int index,
         * Object value), która na podstawie typu wartości (instanceof
         * Integer, String, BigDecimal, LocalDate, LocalDateTime) wywołuje
         * odpowiedni setter (setInt/setString/setBigDecimal/setDate z
         * konwersją/setTimestamp z konwersją). Użyj jej do wstawienia
         * pełnego wiersza pracownika, iterując po tablicy Object[]
         * wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PerformanceComparisonConcatenationVsPrepared {
        /*
         * 🧪 Zadanie 23:
         * Zmierz czas wstawienia 500 wierszy dwoma sposobami: (a)
         * Statement + budowanie SQL przez konkatenację przy każdym
         * INSERT-cie, (b) JEDEN PreparedStatement reużywany z różnymi
         * parametrami. Wypisz oba czasy i różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_InsertAndReturnGeneratedId {
        /*
         * 🧪 Zadanie 24:
         * Na tabeli "employees" (id INT GENERATED ALWAYS AS IDENTITY
         * PRIMARY KEY, name, salary) napisz metodę
         * insertAndReturnId(Connection, name, salary), która wstawia
         * wiersz przez PreparedStatement z Statement.RETURN_GENERATED_KEYS
         * i zwraca wygenerowane id. Wywołaj ją 3 razy i wypisz zwrócone id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DynamicOptionalFiltersQuery {
        /*
         * 🧪 Zadanie 25:
         * Napisz metodę findEmployees(Connection conn, String
         * nameFilterOrNull, BigDecimal minSalaryOrNull), która buduje SQL
         * z ZMIENNĄ liczbą placeholderów `?` w zależności od tego, które
         * filtry są różne od null (np. tylko WHERE name = ?, tylko WHERE
         * salary >= ?, obydwa, albo brak WHERE wcale), i wiąże parametry
         * w odpowiedniej kolejności. Przetestuj dla 3 różnych kombinacji
         * filtrów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PreparedStatementReuseVsRecreateTiming {
        /*
         * 🧪 Zadanie 26:
         * Zmierz czas 100-krotnego wykonania tego samego INSERT-a
         * dwoma sposobami: (a) TWORZĄC nowy PreparedStatement przy
         * każdym insercie (connection.prepareStatement(sql) w pętli),
         * (b) REUŻYWAJĄC jeden raz utworzony PreparedStatement (tylko
         * zmieniając parametry). Wypisz oba czasy i skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EmployeeRepositoryClass {
        /*
         * 🧪 Zadanie 27:
         * Napisz klasę EmployeeRepository z polem Connection i metodami
         * insert(name, salary), findById(id) (zwraca Optional/String
         * opis), updateSalary(id, newSalary), delete(id) - wszystkie
         * oparte na PreparedStatement. Zademonstruj pełny cykl: insert,
         * findById, updateSalary, findById ponownie, delete, findById
         * (brak wyniku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TypeMismatchThrowsException {
        /*
         * 🧪 Zadanie 28:
         * Na tabeli "employees" (id INT, name VARCHAR(100)) spróbuj
         * wykonać SELECT z parametrem "WHERE id = ?" ustawiając
         * ps.setString(1, "abc") (tekst niebędący liczbą) na kolumnie
         * typu INT. Złap SQLException (albo sprawdź, że H2 rzuca błąd
         * konwersji) i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ParameterizedAggregateReport {
        /*
         * 🧪 Zadanie 29:
         * Na tabeli "employees" z 6 wierszami o różnych salary napisz
         * metodę countAboveThreshold(Connection, BigDecimal threshold)
         * używającą PreparedStatement z "WHERE salary > ?" +
         * SELECT COUNT(*). Wywołaj ją dla 2 różnych progów i wypisz
         * wyniki jako mini raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneFullEmployeeDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj pełne demo: tabela "employees" (id, name, salary,
         * birth_date, hired_at), wstaw 5 pracowników używając WSZYSTKICH
         * poznanych typów setterów (setInt, setString, setBigDecimal,
         * setDate z konwersji LocalDate, setTimestamp z konwersji
         * LocalDateTime, setObject z Types, oraz setNull dla jednego
         * pracownika bez daty urodzenia). Wykonaj: wyszukanie po nazwie,
         * aktualizację pensji, usunięcie jednego pracownika, i wypisz
         * finalny raport wszystkich pozostałych.
         */
        public static void main(String[] args) { }
    }
}
