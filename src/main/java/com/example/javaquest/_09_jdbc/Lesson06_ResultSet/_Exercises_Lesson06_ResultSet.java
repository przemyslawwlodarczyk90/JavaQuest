package com.example.javaquest._09_jdbc.Lesson06_ResultSet;

public class _Exercises_Lesson06_ResultSet {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IterateRowsWithNext {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l06ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "students" (id, name) z 4 wierszami. Wykonaj SELECT i wypisz
         * wszystkie wiersze w pętli while(rs.next()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CursorStartsBeforeFirstRow {
        /*
         * 🧪 Zadanie 2:
         * Wykonaj executeQuery() na tabeli z co najmniej 1 wierszem, ale
         * NIE wołaj rs.next() - spróbuj bezpośrednio wywołać rs.getString(1).
         * Złap SQLException i wypisz komunikat, potwierdzający że kursor
         * na starcie stoi PRZED pierwszym wierszem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_GetByNameVsGetByIndex {
        /*
         * 🧪 Zadanie 3:
         * Na tabeli "students" (id, name, grade_avg) z 1 wierszem odczytaj
         * te same wartości DWOMA sposobami: przez getInt("id")/getString("name")
         * i przez getInt(1)/getString(2). Porównaj (println) czy wyniki
         * są identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetIntReturnsZeroForNull {
        /*
         * 🧪 Zadanie 4:
         * Na tabeli "students" (id, name, scholarship_amount INT -
         * kolumna opcjonalna) wstaw wiersz z NULL w scholarship_amount.
         * Odczytaj tę kolumnę przez rs.getInt("scholarship_amount") i
         * wypisz wynik - zaobserwuj, że zwraca 0, mimo że w bazie jest NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GetBigDecimalCorrectlyReturnsNull {
        /*
         * 🧪 Zadanie 5:
         * Na tej samej tabeli co w Zadaniu 4 (kolumna scholarship_amount
         * DECIMAL, wartość NULL), odczytaj ją przez rs.getBigDecimal(...)
         * i wypisz wynik - powinien być null (w przeciwieństwie do
         * getInt() z poprzedniego zadania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WasNullCheck {
        /*
         * 🧪 Zadanie 6:
         * Po odczytaniu kolumny z wartością NULL (np. getBigDecimal na
         * kolumnie scholarship_amount), wywołaj rs.wasNull() i wypisz
         * wynik (powinno być true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReadFullRowAllTypes {
        /*
         * 🧪 Zadanie 7:
         * Na tabeli "students" (id, name, grade_avg DECIMAL, enrolled_at
         * TIMESTAMP) z 1 wierszem, odczytaj i wypisz wszystkie 4 kolumny
         * używając odpowiednich getterów (getInt, getString,
         * getBigDecimal, getTimestamp).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ManualRowCounter {
        /*
         * 🧪 Zadanie 8:
         * Na tabeli "students" z 6 wierszami policz liczbę wierszy
         * RĘCZNIE w Javie (zmienna licznik zwiększana w pętli
         * while(rs.next())), bez używania SQL COUNT(*). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ReadSingleAggregateValue {
        /*
         * 🧪 Zadanie 9:
         * Wykonaj "SELECT COUNT(*) FROM students" przez executeQuery(),
         * odczytaj pojedynczą wartość (rs.next(); rs.getInt(1)) i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_EmptyResultSetNextReturnsFalse {
        /*
         * 🧪 Zadanie 10:
         * Wykonaj SELECT z warunkiem WHERE, który nie dopasuje żadnego
         * wiersza (np. WHERE id = 9999). Wywołaj rs.next() i wypisz jego
         * wynik (powinien być false) - zapytanie zwróciło 0 wierszy.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MapRowsIntoListOfRecords {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj record Student(int id, String name, BigDecimal
         * gradeAvg). Odczytaj wszystkie wiersze tabeli "students" i
         * zmapuj je na List<Student>, wypisując każdy element.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GetObjectWithClassParameter {
        /*
         * 🧪 Zadanie 12:
         * Na kolumnie scholarship_amount (INT, opcjonalna, z wartością
         * NULL dla jednego wiersza) porównaj wynik
         * rs.getObject("scholarship_amount", Integer.class) (powinien
         * poprawnie zwrócić null) z rs.getInt("scholarship_amount")
         * (zwraca 0) - wypisz oba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CheckWasNullForMultipleColumns {
        /*
         * 🧪 Zadanie 13:
         * Na wierszu z co najmniej dwoma kolumnami opcjonalnymi (jedną
         * NULL, jedną wypełnioną) odczytaj obie kolumny getBigDecimal(...)
         * i po KAŻDYM odczycie wywołaj wasNull(), wypisując rezultat dla
         * obu przypadków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareIndexVsNameForAllColumns {
        /*
         * 🧪 Zadanie 14:
         * Na tabeli z 3 kolumnami odczytaj CAŁY wiersz dwoma sposobami
         * (po indeksach 1..3 i po nazwach kolumn) i porównaj (println)
         * czy dają identyczny wynik dla każdej kolumny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FormatNullAsPlaceholder {
        /*
         * 🧪 Zadanie 15:
         * Odczytując kolumnę scholarship_amount (DECIMAL, może być NULL),
         * napisz metodę formatOrDash(BigDecimal value), która zwraca
         * value.toString() albo "-" gdy value == null. Użyj jej przy
         * wypisywaniu wszystkich wierszy tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MapRowMethodForFindOneAndFindAll {
        /*
         * 🧪 Zadanie 16:
         * Napisz jedną metodę mapRow(ResultSet rs) zwracającą Student
         * (jak w Zadaniu 11), a potem użyj jej w DWÓCH miejscach: metodzie
         * findById(Connection, int) (if (rs.next())) i findAll(Connection)
         * (while (rs.next())) - unikając duplikacji kodu mapującego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CursorCannotGoBackwards {
        /*
         * 🧪 Zadanie 17:
         * Po przejściu pętlą while(rs.next()) przez WSZYSTKIE wiersze
         * (kursor na końcu), wywołaj rs.next() jeszcze raz i wypisz wynik
         * (powinien być false) - potwierdzenie, że domyślny (forward-only)
         * ResultSet nie wraca automatycznie na początek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ManualSumVsSqlSum {
        /*
         * 🧪 Zadanie 18:
         * Na tabeli "students" (grade_avg DECIMAL) zsumuj wszystkie
         * grade_avg RĘCZNIE w Javie (iterując ResultSet), a następnie
         * wykonaj SQL "SELECT SUM(grade_avg) FROM students" - wypisz obie
         * wartości i potwierdź, że są (praktycznie) równe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildListSkippingNullEntries {
        /*
         * 🧪 Zadanie 19:
         * Na tabeli "students" z kolumną scholarship_amount (część
         * wierszy NULL) zbuduj DWIE listy: jedną ze WSZYSTKIMI nazwiskami,
         * drugą TYLKO z nazwiskami studentów, którzy MAJĄ stypendium
         * (scholarship_amount != null, sprawdzone przez wasNull() albo
         * porównanie z null z getBigDecimal). Wypisz obie listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReadColumnCountFromMetaData {
        /*
         * 🧪 Zadanie 20:
         * Wykonaj SELECT * z tabeli "students" i przez
         * rs.getMetaData().getColumnCount() wypisz liczbę kolumn w
         * wyniku oraz (w pętli) nazwę każdej kolumny (getColumnName(i)).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericPrintAnyResultSet {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę printAnyResultSet(ResultSet rs), która używa
         * ResultSetMetaData do dynamicznego odczytania liczby i nazw
         * kolumn (bez znajomości struktury tabeli z góry) i wypisuje
         * WSZYSTKIE wiersze niezależnie od tego, jaka tabela została
         * przekazana. Przetestuj ją na DWÓCH różnych zapytaniach
         * (różne tabele/kolumny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ResultSetToListOfMaps {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę List<Map<String,Object>> resultSetToMaps(ResultSet rs),
         * która generycznie (przez metadata) zamienia KAŻDY wiersz na
         * Map<String,Object> (nazwa kolumny -> wartość, przez getObject).
         * Przetestuj ją na tabeli "students" i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_StrictVsLenientMapping {
        /*
         * 🧪 Zadanie 23:
         * Napisz dwie metody mapowania wiersza na Student: mapRowStrict
         * (rzuca IllegalStateException, jeśli grade_avg jest NULL) i
         * mapRowLenient (używa domyślnej wartości BigDecimal.ZERO dla
         * NULL). Przetestuj obie na wierszu z NULL w grade_avg - pokaż
         * różne zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ScrollableResultSetNavigation {
        /*
         * 🧪 Zadanie 24:
         * Utwórz Statement z parametrami
         * (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
         * i wykonaj SELECT na tabeli z co najmniej 5 wierszami. Użyj
         * rs.last() (przejście na ostatni wiersz), wypisz go, potem
         * rs.first() (powrót na pierwszy) i wypisz go, oraz rs.absolute(3)
         * (przejście na konkretny numer wiersza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ForwardOnlyThrowsOnPrevious {
        /*
         * 🧪 Zadanie 25:
         * Wykonaj zwykłe (domyślne, forward-only) executeQuery(), przejdź
         * przez kilka wierszy, a potem spróbuj wywołać rs.previous().
         * Złap SQLException i wypisz komunikat - potwierdzenie, że
         * domyślny ResultSet nie wspiera nawigacji "do tyłu".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CursorPositionAwareness {
        /*
         * 🧪 Zadanie 26:
         * Na SCROLLABLE ResultSet (jak w Zadaniu 24) wypisz
         * rs.isBeforeFirst() (przed next()), a po przejściu na konkretny
         * wiersz wypisz rs.getRow() (numer aktualnego wiersza) oraz po
         * dojściu do końca - rs.isAfterLast().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ResilientMapperSkipsBadRows {
        /*
         * 🧪 Zadanie 27:
         * Wstaw do tabeli "students" jeden wiersz z NULL w kolumnie name
         * (jeśli kolumna to pozwala - użyj tabeli bez NOT NULL na name
         * do tego zadania) razem z poprawnymi wierszami. Napisz metodę
         * mapowania, która łapie wyjątek/NPE dla "wadliwego" wiersza,
         * loguje ostrzeżenie i KONTYNUUJE mapowanie pozostałych wierszy,
         * zwracając listę TYLKO poprawnie zmapowanych obiektów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FormattedReportWithNullHandling {
        /*
         * 🧪 Zadanie 28:
         * Na tabeli "students" (id, name, scholarship_amount - część
         * NULL) wypisz sformatowany raport tabelaryczny (println z
         * wyrównaniem) wszystkich studentów, gdzie brak stypendium jest
         * pokazany jako "brak stypendium", a nie jako "null" czy "0".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CountNullVsNonNullColumns {
        /*
         * 🧪 Zadanie 29:
         * Na tabeli "students" ze mieszanką wartości NULL/nie-NULL w
         * scholarship_amount, iterując ResultSet policz RĘCZNIE (bez SQL)
         * ile wierszy ma stypendium a ile nie, wypisując oba liczniki na
         * końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneStudentsReport {
        /*
         * 🧪 Zadanie 30:
         * Połącz wszystko: na tabeli "students" (id, name, grade_avg,
         * enrolled_at, scholarship_amount - część NULL) napisz generyczną
         * metodę wypisującą raport (przez metadata, bez hardkodowania
         * kolumn), dodaj osobne podsumowanie: liczba studentów ze
         * stypendium, średnia ocen (SQL AVG vs ręcznie w Javie - porównaj),
         * oraz listę studentów zmapowanych na obiekty Student (z
         * poprawną obsługą NULL przez getBigDecimal/wasNull).
         */
        public static void main(String[] args) { }
    }
}
