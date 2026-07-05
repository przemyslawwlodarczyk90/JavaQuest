package com.example.javaquest._08_sql.Lesson05_NullValues;

public class _Exercises_Lesson05_NullValues {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_NullIsNotZeroOrEmptyString {
        /*
         * 🧪 Zadanie 1:
         * Bez łączenia się z bazą: wypisz println wyjaśniające różnicę między NULL,
         * liczbą 0, pustym Stringiem "" i wartością false - podaj po jednym przykładzie
         * sytuacji z życia, gdzie każda z tych wartości byłaby poprawną reprezentacją
         * danej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_InsertRowWithNullableColumn {
        /*
         * 🧪 Zadanie 2:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex02_null;DB_CLOSE_DELAY=-1). Utwórz
         * tabelę "users" (id INT PRIMARY KEY, name VARCHAR(100) NOT NULL, middle_name
         * VARCHAR(100)). Wstaw 2 użytkowników: jednego z drugim imieniem, jednego z
         * middle_name = NULL. Wypisz obu (SELECT *).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IsNullOperator {
        /*
         * 🧪 Zadanie 3:
         * Odtwórz tabelę "users" z Zadania 2 z 3 użytkownikami (2 bez drugiego
         * imienia). Wykonaj "SELECT name FROM users WHERE middle_name IS NULL" i
         * wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IsNotNullOperator {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz tabelę "users" z Zadania 3. Wykonaj "SELECT name FROM users WHERE
         * middle_name IS NOT NULL" i wypisz wynik - powinien być odwrotny do Zadania 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_EqualsNullNeverWorks {
        /*
         * 🧪 Zadanie 5:
         * Odtwórz tabelę "users" z NULL-ami z Zadania 2. Wykonaj (błędne, ale
         * poprawne składniowo) zapytanie "SELECT name FROM users WHERE middle_name =
         * NULL" i wypisz liczbę wyników (powinno być 0, NIGDY nie zwróci wiersza z
         * middle_name IS NULL) - skomentuj w println, czemu tak się dzieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NotNullConstraintRejectsNull {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz tabelę "users" (id INT PRIMARY KEY, name VARCHAR(100) NOT NULL) z
         * lekcji. Spróbuj wstawić wiersz z name = NULL - złap SQLException i wypisz
         * komunikat, potwierdzając, że ograniczenie NOT NULL zabroniło operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GetIntOnNullReturnsZeroPitfall {
        /*
         * 🧪 Zadanie 7:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, name VARCHAR(100), age INT).
         * Wstaw 2 użytkowników: jednego z age=30, jednego z age=NULL. Odczytaj
         * age przez rs.getInt("age") dla obu i wypisz - zaobserwuj, że dla NULL
         * getInt() "po cichu" zwraca 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WasNullDetectsRealNull {
        /*
         * 🧪 Zadanie 8:
         * Odtwórz tabelę "users" z age (jak w Zadaniu 7). Dla KAŻDEGO wiersza odczytaj
         * age przez rs.getInt("age"), zaraz po tym wywołaj rs.wasNull() i wypisz obie
         * wartości w formacie "age=<int>, wasNull=<bool>" - użyj tego do POPRAWNEGO
         * odróżnienia wieku 0 od "brak danych".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_GetObjectReturnsTrueNull {
        /*
         * 🧪 Zadanie 9:
         * Odtwórz tabelę "users" z age (jak w Zadaniu 7). Odczytaj age przez
         * (Integer) rs.getObject("age") - dla wiersza z NULL powinieneś dostać
         * PRAWDZIWY null Javowy (nie 0). Wypisz wynik dla obu wierszy, sprawdzając
         * ageOrNull == null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_OptionalWrappingNullableColumn {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz tabelę "users" z age (jak w Zadaniu 7). Dla każdego wiersza odczytaj
         * age przez getObject, zapakuj w Optional.ofNullable(...) i wypisz w formacie
         * "<name>: age=<wartosc albo 'brak danych'>" używając
         * optional.map(String::valueOf).orElse("brak danych").
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_NullInArithmeticExpressions {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tabelę "orders" (id INT PRIMARY KEY, amount INT, discount INT).
         * Wstaw 2 zamówienia: jedno z discount=10, jedno z discount=NULL. Wykonaj
         * "SELECT id, amount, discount, amount - discount AS final_amount FROM
         * orders" i wypisz - zaobserwuj, że dla wiersza z discount=NULL,
         * final_amount też jest NULL ("zarażenie" przez NULL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CoalesceLikeDefaultInJava {
        /*
         * 🧪 Zadanie 12:
         * Odtwórz tabelę "orders" z discount (jak w Zadaniu 11). W Javie (nie SQL-em)
         * napisz metodę resolveDiscount(Integer discountOrNull), która zwraca 0, gdy
         * argument jest null, albo samą wartość w przeciwnym razie - zastosuj ją po
         * odczycie przez getObject("discount") do wszystkich wierszy i wypisz
         * "przetworzone" wartości discount.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultipleNullableColumnsFiltering {
        /*
         * 🧪 Zadanie 13:
         * Utwórz tabelę "customers" (id INT PRIMARY KEY, name VARCHAR(100), phone
         * VARCHAR(20), email VARCHAR(100)) - phone i email opcjonalne. Wstaw 4
         * klientów z różnymi kombinacjami NULL (oba podane, tylko phone, tylko email,
         * oba NULL). Wykonaj zapytanie znajdujące klientów, którzy NIE MAJĄ żadnej
         * metody kontaktu (WHERE phone IS NULL AND email IS NULL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UpdateColumnToNull {
        /*
         * 🧪 Zadanie 14:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, name VARCHAR(100), middle_name
         * VARCHAR(100)). Wstaw użytkownika z drugim imieniem. Wykonaj "UPDATE users
         * SET middle_name = NULL WHERE id = ?" (celowe "wyczyszczenie" wartości) i
         * zweryfikuj przez SELECT + IS NULL, że operacja się powiodła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NotNullWithDefaultCombination {
        /*
         * 🧪 Zadanie 15:
         * Utwórz tabelę "accounts" (id INT PRIMARY KEY, status VARCHAR(20) NOT NULL
         * DEFAULT 'ACTIVE'). Wstaw wiersz bez podania status (użyje DEFAULT, więc
         * NIE będzie NULL) i spróbuj wstawić wiersz z jawnym status = NULL - złap
         * SQLException, potwierdzając że DEFAULT nie omija NOT NULL przy jawnym NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CountDistinctIgnoresNull {
        /*
         * 🧪 Zadanie 16:
         * Utwórz tabelę "leads" (id INT PRIMARY KEY, email VARCHAR(100)). Wstaw 5
         * wierszy, gdzie 2 mają email = NULL, a 3 mają różne adresy. Wykonaj
         * "SELECT COUNT(*) AS total, COUNT(email) AS with_email FROM leads" i wypisz
         * oba wyniki - zaobserwuj, że COUNT(kolumna) (w przeciwieństwie do COUNT(*))
         * NIE liczy wierszy z NULL w tej kolumnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_NullSafeComparisonPattern {
        /*
         * 🧪 Zadanie 17:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, category VARCHAR(50)). Wstaw
         * 4 produkty, jeden z category = NULL. Napisz zapytanie, które znajduje
         * produkty NIE będące w kategorii 'Elektronika' - użyj poprawnego wzorca
         * "(category != 'Elektronika' OR category IS NULL)" i wypisz wynik, pokazując,
         * że sam "category != 'Elektronika'" pominąłby wiersz z NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BatchNullSafeReadingWithOptional {
        /*
         * 🧪 Zadanie 18:
         * Utwórz tabelę "employees" (id INT PRIMARY KEY, name VARCHAR(100), bonus
         * DECIMAL(10,2)). Wstaw 4 pracowników, 2 z bonus = NULL. Napisz metodę
         * readBonusSafely(ResultSet rs), zwracającą Optional<BigDecimal> przez
         * Optional.ofNullable(rs.getBigDecimal("bonus")) - odczytaj wszystkie wiersze
         * przy jej pomocy i wypisz sumę TYLKO tych bonusów, które istnieją (mapToObj +
         * suma w Javie, bez SQL SUM).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_NullInForeignKeyOptionalRelation {
        /*
         * 🧪 Zadanie 19:
         * Utwórz "departments" (id INT PRIMARY KEY, name VARCHAR(50)) i "employees"
         * (id INT PRIMARY KEY, name VARCHAR(100), department_id INT REFERENCES
         * departments(id)) - department_id dopuszcza NULL (pracownik może nie mieć
         * jeszcze przypisanego działu). Wstaw 3 pracowników, 1 bez działu. Wypisz
         * wszystkich pracowników, zamieniając department_id=NULL na tekst "brak
         * działu" w Javie po odczycie przez getObject.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NullHandlingReportAcrossTable {
        /*
         * 🧪 Zadanie 20:
         * Utwórz tabelę "survey_responses" (id INT PRIMARY KEY, q1_answer INT,
         * q2_answer INT, q3_answer INT) - respondent mógł pominąć pytania (NULL).
         * Wstaw 5 wierszy z różnymi kombinacjami wypełnionych/pominiętych odpowiedzi.
         * Napisz raport w Javie (bez SQL agregacji): dla każdej z 3 kolumn policz, ile
         * wierszy ma tam NULL (getObject + sprawdzenie == null w pętli), i wypisz
         * "<kolumna>: <liczba pominiec>".
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ThreeValuedLogicTruthTable {
        /*
         * 🧪 Zadanie 21:
         * Utwórz tabelę "flags" (id INT PRIMARY KEY, a BOOLEAN, b BOOLEAN) i wstaw
         * WSZYSTKIE kombinacje a i b z wartości {TRUE, FALSE, NULL} (9 wierszy - logika
         * trójwartościowa SQL). Dla każdego wiersza wykonaj SELECT (a AND b), (a OR b)
         * i wypisz wynik jako "a=<..>, b=<..>, a AND b=<..>, a OR b=<..>" - zaobserwuj
         * przypadki, gdzie wynik jest NULL, mimo że jeden argument jest znany (np. TRUE
         * OR NULL = TRUE, ale FALSE OR NULL = NULL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NullPropagationThroughMultipleCalculations {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "invoices" (id INT PRIMARY KEY, net_amount DECIMAL(10,2),
         * tax_rate DECIMAL(5,2), discount DECIMAL(10,2)) - wszystkie 3 kolumny mogą
         * być NULL. Wstaw 4 wiersze z różnymi kombinacjami NULL. Napisz zapytanie
         * liczące "net_amount * (1 + tax_rate/100) - discount AS total" i wypisz -
         * zaobserwuj, że NULL w JEDNEJ kolumnie "zaraża" cały wynik, nawet jeśli inne
         * kolumny mają wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SafeAggregationDespiteNulls {
        /*
         * 🧪 Zadanie 23:
         * Utwórz tabelę "ratings" (id INT PRIMARY KEY, product_id INT, score INT) -
         * score może być NULL (klient nie ocenił, tylko kupił). Wstaw 6 wierszy dla 2
         * produktów, część z score=NULL. Napisz w Javie metodę averageIgnoringNulls,
         * która odczytuje WSZYSTKIE wiersze, ignoruje te z score=NULL (getObject +
         * null check) i liczy średnią RĘCZNIE (bez SQL AVG - poznamy w Lesson13) -
         * porównaj wynik z SQL-owym AVG(score), które też domyślnie ignoruje NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_NullAwareDataQualityReport {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tabelę "customer_records" (id INT PRIMARY KEY, name VARCHAR(100),
         * email VARCHAR(100), phone VARCHAR(20), address VARCHAR(200)) - wszystkie
         * poza name mogą być NULL. Wstaw 10 wierszy z realistycznie "brudnymi" danymi
         * (różne braki). Napisz raport "jakości danych": dla każdej opcjonalnej
         * kolumny wypisz procent wierszy z wypełnioną wartością (COUNT(kolumna) *
         * 100.0 / COUNT(*)) - użyj SQL-a do policzenia tego bez pobierania wszystkich
         * wierszy do Javy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MigratingLegacyEmptyStringToNull {
        /*
         * 🧪 Zadanie 25:
         * Utwórz "legacy_users" (id INT PRIMARY KEY, middle_name VARCHAR(100)) i
         * wstaw wiersze, gdzie brak drugiego imienia reprezentowany jest jako PUSTY
         * STRING "" (błędna praktyka, nie NULL) razem z wierszami mającymi realne
         * wartości. Napisz migrację w Javie: odczytaj wszystkie wiersze, dla tych z
         * middle_name = "" wykonaj UPDATE ustawiające middle_name na jawny NULL,
         * zweryfikuj przez IS NULL po migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_NullSafeSortingBehaviorObservation {
        /*
         * 🧪 Zadanie 26:
         * Utwórz tabelę "products" (id INT PRIMARY KEY, price DECIMAL(10,2)) i wstaw
         * 5 produktów, 2 z price = NULL. Wykonaj "SELECT id, price FROM products
         * ORDER BY price" (bez jawnego NULLS FIRST/LAST - to poznamy dokładnie w
         * Lesson12) i wypisz kolejność - zaobserwuj i skomentuj w println, gdzie H2
         * domyślnie umieszcza wiersze z NULL w wyniku sortowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_NullHandlingInForeignKeyCascade {
        /*
         * 🧪 Zadanie 27:
         * Odtwórz "departments"/"employees" z Zadania 19, ale z FOREIGN KEY (department_id)
         * REFERENCES departments(id) ON DELETE SET NULL. Wstaw dział i 2 pracowników w
         * nim. Usuń dział (DELETE FROM departments) i zweryfikuj przez getObject +
         * IS NULL, że department_id pracowników zmienił się na NULL (a nie zostali
         * usunięci razem z działem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_NullValueEqualityEdgeCaseInJoinPreview {
        /*
         * 🧪 Zadanie 28:
         * Utwórz "orders" (id INT PRIMARY KEY, coupon_code VARCHAR(20)) - coupon_code
         * może być NULL. Wstaw 3 zamówienia, 1 bez kuponu (NULL). Spróbuj (celowo źle)
         * znaleźć zamówienia z TAKIM SAMYM coupon_code przez porównanie "a.coupon_code
         * = b.coupon_code" między dwoma zapytaniami w Javie dla wierszy z NULL -
         * zademonstruj, że dwa NULL-e NIE są uznawane za "równe" (kod Javy porównujący
         * String==null musi to obsłużyć jawnie, inaczej "zgubi" dopasowanie NULL=NULL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildNullSafeDtoMapper {
        /*
         * 🧪 Zadanie 29:
         * Utwórz tabelę "users" (id INT PRIMARY KEY, name VARCHAR(100) NOT NULL, age
         * INT, email VARCHAR(100)) - age i email opcjonalne. Napisz prosty rekord/klasę
         * UserDto(int id, String name, Optional<Integer> age, Optional<String> email) i
         * metodę mapRow(ResultSet rs) zwracającą UserDto, która POPRAWNIE (przez
         * getObject) mapuje kolumny opcjonalne na Optional. Wstaw 3 użytkowników z
         * różnymi kombinacjami NULL, zmapuj wszystkich na UserDto i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneNullAuditAndCleanupPipeline {
        /*
         * 🧪 Zadanie 30:
         * Utwórz tabelę "imported_contacts" (id INT PRIMARY KEY, full_name VARCHAR(100)
         * NOT NULL, phone VARCHAR(20), email VARCHAR(100), notes VARCHAR(200)) i wstaw
         * 8 realistycznie "brudnych" wierszy (różne kombinacje NULL w phone/email/notes,
         * w tym co najmniej 1 kontakt bez ŻADNEJ metody kontaktu). Napisz kompletny
         * pipeline w Javie: (1) policz i wypisz statystyki wypełnienia każdej
         * opcjonalnej kolumny (jak w Zadaniu 24), (2) znajdź i wypisz kontakty BEZ
         * żadnej metody kontaktu (phone IS NULL AND email IS NULL), (3) zmapuj
         * WSZYSTKIE kontakty na obiekty z polami Optional<String> dla phone/email/notes
         * (jak w Zadaniu 29), (4) wypisz końcowy raport z nagłówkiem "=== RAPORT
         * JAKOSCI DANYCH ===" podsumowujący wszystkie 3 poprzednie kroki.
         */
        public static void main(String[] args) { }
    }
}
