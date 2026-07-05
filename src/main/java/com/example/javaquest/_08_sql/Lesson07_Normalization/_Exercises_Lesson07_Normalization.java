package com.example.javaquest._08_sql.Lesson07_Normalization;

public class _Exercises_Lesson07_Normalization {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WhyNormalizeExplanationPrint {
        /*
         * 🧪 Zadanie 1:
         * Bez łączenia się z bazą: wypisz println wyjaśnienie 3 problemów braku
         * normalizacji z lekcji (marnowanie miejsca, anomalia aktualizacji, anomalia
         * wstawiania, anomalia usuwania - wybierz i opisz 3 z nich) po jednym przykładzie
         * z życia dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DenormalizedTableDuplicationDemo {
        /*
         * 🧪 Zadanie 2:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex02_norm;DB_CLOSE_DELAY=-1). Utwórz
         * "zdenormalizowaną" tabelę "orders_flat" (id INT PRIMARY KEY, customer_name
         * VARCHAR(100), customer_email VARCHAR(100), product_name VARCHAR(100)).
         * Wstaw 3 zamówienia TEGO SAMEGO klienta (dane klienta powtórzone 3 razy).
         * Wykonaj "SELECT COUNT(*), COUNT(DISTINCT customer_email) FROM orders_flat" i
         * wypisz - pokaż, że email jest zduplikowany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FirstNormalFormAtomicValues {
        /*
         * 🧪 Zadanie 3:
         * Utwórz "źle" zaprojektowaną tabelę "users_bad" (id INT PRIMARY KEY, name
         * VARCHAR(100), phones VARCHAR(200)) z kolumną phones zawierającą kilka
         * numerów oddzielonych przecinkiem (naruszenie 1NF). Wstaw 1 wiersz z 3
         * numerami. Napisz kod w Javie, który odczytuje tę kolumnę i rozbija ją
         * (String.split(",")) na osobne numery - skomentuj w println, czemu to
         * NIE jest 1NF.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixFirstNormalFormWithSeparateTable {
        /*
         * 🧪 Zadanie 4:
         * Naprawiając Zadanie 3: utwórz "users" (id, name) i "phones" (id, user_id FK,
         * phone) - poprawna postać 1NF (jedna wartość atomowa na wiersz). Wstaw 1
         * użytkownika i 3 jego numery jako 3 osobne wiersze w "phones". Wypisz je
         * przez SELECT phone FROM phones WHERE user_id = ?.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UpdateAnomalyDemo {
        /*
         * 🧪 Zadanie 5:
         * Odtwórz "orders_flat" z Zadania 2 z 3 zamówieniami tego samego klienta.
         * Zasymuluj "anomalię aktualizacji": zaktualizuj email TYLKO w jednym wierszu
         * (UPDATE ... WHERE id = 1), po czym wykonaj "SELECT DISTINCT customer_email
         * FROM orders_flat" i wypisz - zaobserwuj, że teraz są DWA różne emaile dla
         * tego samego klienta (niespójność danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_InsertAnomalyDemo {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz "orders_flat" (id, customer_name, customer_email, product_name).
         * Spróbuj (w kodzie skomentuj problem) dodać NOWY produkt do "katalogu" bez
         * żadnego zamówienia - zaobserwuj, że w tym zdenormalizowanym schemacie
         * MUSISZ podać też dane klienta, mimo że nie mają one związku z samym
         * produktem (anomalia wstawiania). Wypisz println z wyjaśnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DeleteAnomalyDemo {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz "orders_flat" z JEDNYM zamówieniem zawierającym JEDYNE wystąpienie
         * konkretnego produktu ("Monitor"). Usuń to zamówienie (DELETE) i wykonaj
         * "SELECT * FROM orders_flat WHERE product_name = 'Monitor'" - wynik będzie
         * pusty, mimo że nie chcieliśmy "zapomnieć" o istnieniu tego produktu
         * (anomalia usuwania). Wypisz komentarz w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NormalizedSchemaBasic {
        /*
         * 🧪 Zadanie 8:
         * Utwórz znormalizowany schemat: "users" (id, name, email) i "products" (id,
         * name). Wstaw 1 użytkownika i 2 produkty - każde dane istnieją TYLKO RAZ.
         * Wypisz obie tabele i policz liczbę fizycznych wierszy z danymi użytkownika
         * (powinno być 1, niezależnie od liczby jego przyszłych zamówień).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NormalFormsOverviewPrint {
        /*
         * 🧪 Zadanie 9:
         * Bez łączenia się z bazą: wypisz na konsoli krótkie opisy 1NF, 2NF i 3NF z
         * lekcji (po jednej linii println na każdą postać normalną) w formacie
         * "<postac>: <opis>".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareRowCountBeforeAfterNormalization {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz "orders_flat" (zdenormalizowana, 3 zamówienia tego samego klienta)
         * i równoległy znormalizowany schemat "users"+"orders" (id, user_id FK) z
         * TYMI SAMYMI danymi logicznymi. Porównaj: COUNT(*) z orders_flat vs
         * COUNT(DISTINCT customer_email) z orders_flat vs COUNT(*) z users - wypisz
         * wszystkie 3 liczby i skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SecondNormalFormPartialDependency {
        /*
         * 🧪 Zadanie 11:
         * Utwórz "źle" zaprojektowaną tabelę "order_items_bad" (order_id INT,
         * product_id INT, product_name VARCHAR(100), quantity INT, PRIMARY
         * KEY(order_id, product_id)) - product_name zależy TYLKO od product_id, nie od
         * CAŁEGO klucza złożonego (naruszenie 2NF). Wstaw 3 wiersze, gdzie ten sam
         * product_id ma product_name powtórzone w każdym wierszu. Wypisz je i
         * skomentuj naruszenie 2NF w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FixSecondNormalForm {
        /*
         * 🧪 Zadanie 12:
         * Naprawiając Zadanie 11: utwórz "products" (id, name) i "order_items" (order_id,
         * product_id FK, quantity, PRIMARY KEY(order_id, product_id)) - teraz
         * product_name jest TYLKO w products, zależy od CAŁEGO (jedynego) klucza tej
         * tabeli. Wstaw dane i JOIN-em (podglądowo, dokładnie poznamy w Lesson14)
         * pokaż wynik równoważny Zadaniu 11, ale bez duplikacji nazwy produktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ThirdNormalFormTransitiveDependency {
        /*
         * 🧪 Zadanie 13:
         * Utwórz "źle" zaprojektowaną tabelę "orders_bad3nf" (id INT PRIMARY KEY,
         * user_id INT, user_email VARCHAR(100)) - user_email zależy od user_id (a nie
         * bezpośrednio od id zamówienia) - to zależność przechodnia, naruszenie 3NF.
         * Wstaw 3 zamówienia tego samego użytkownika z powtórzonym emailem. Wypisz je
         * i skomentuj naruszenie 3NF w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FixThirdNormalForm {
        /*
         * 🧪 Zadanie 14:
         * Naprawiając Zadanie 13: utwórz "users" (id, email) i "orders" (id, user_id
         * FK) - email istnieje TYLKO w users. Wstaw te same dane logiczne co w
         * Zadaniu 13 i zweryfikuj (COUNT(*) z users), że email istnieje fizycznie
         * TYLKO RAZ, niezależnie od liczby zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MigrationScriptFlatToNormalized {
        /*
         * 🧪 Zadanie 15:
         * Utwórz "orders_flat" (id, user_name, user_email, product_name,
         * product_price) i wstaw 5 wierszy z pewną liczbą powtórzeń klientów i
         * produktów. Napisz migrację w Javie: (1) utwórz znormalizowany schemat
         * "users"/"products"/"orders", (2) odczytaj wszystkie wiersze z orders_flat w
         * pętli, (3) dla każdego wiersza wstaw użytkownika/produkt TYLKO jeśli jeszcze
         * nie istnieje (sprawdzenie przez SELECT COUNT(*)), (4) wstaw zamówienie
         * odwołujące się do właściwych id. Zweryfikuj liczność każdej tabeli na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_QuantifyDuplicationSavings {
        /*
         * 🧪 Zadanie 16:
         * Utwórz "orders_flat" (id, customer_name VARCHAR(100), customer_email
         * VARCHAR(100)) i wstaw 20 zamówień rozłożonych między TYLKO 3 klientów (dużo
         * powtórzeń). Policz łączną "wagę" zduplikowanych danych: (liczba wierszy -
         * liczba unikalnych klientów) * przybliżony rozmiar danych klienta w bajtach
         * (np. długość name+email) - wypisz, ile bajtów "zaoszczędziłaby" normalizacja
         * (przeniesienie danych klienta do osobnej tabeli users).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DetectViolationsProgrammatically {
        /*
         * 🧪 Zadanie 17:
         * Utwórz "orders_flat" (id, customer_email VARCHAR(100), customer_name
         * VARCHAR(100)) z 6 wierszami, gdzie 2 klientów mają NIEZGODNE dane (ten sam
         * email, ale RÓŻNE customer_name w różnych wierszach - typowy skutek anomalii
         * aktualizacji). Napisz metodę w Javie, która grupuje wiersze po
         * customer_email (bez SQL GROUP BY - poznamy w Lesson13, użyj Map<String,
         * Set<String>> w Javie) i wykrywa/wypisuje, gdzie ten sam email ma więcej niż
         * jedną unikalną wartość customer_name (sygnał niespójności danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NormalizeThreeEntitySchema {
        /*
         * 🧪 Zadanie 18:
         * Utwórz "zdenormalizowaną" tabelę "sales_flat" (id, salesperson_name,
         * product_name, product_category, amount) z 6 wierszami (kilku sprzedawców
         * sprzedających różne produkty, powtórzenia nazw kategorii). Zaprojektuj i
         * zbuduj poprawny schemat 3NF: "salespeople" (id, name), "categories" (id,
         * name), "products" (id, category_id FK, name), "sales" (id, salesperson_id
         * FK, product_id FK, amount). Przepisz dane w Javie, unikając duplikatów
         * słowników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DenormalizationForReadPerformance {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj znormalizowany schemat "orders" (id, user_id FK) + "users" (id, name)
         * i CELOWO zdenormalizowaną "kopię raportową" "orders_report" (order_id,
         * user_name) wypełnianą przez JOIN (podglądowo). Wstaw dane, wypełnij tabelę
         * raportową, a następnie porównaj: zapytanie o nazwę klienta zamówienia z
         * orders_report (1 SELECT, bez łączenia tabel) vs z orders+users (2 osobne
         * SELECT-y, bez JOIN - poznamy w Lesson14) - skomentuj w println, kiedy taka
         * świadoma denormalizacja bywa uzasadniona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullNormalizationBeforeAfterReport {
        /*
         * 🧪 Zadanie 20:
         * Utwórz "orders_flat" (id, customer_name, customer_email, product_name,
         * product_price) z 8 wierszami (3 klientów, 4 produkty, z powtórzeniami).
         * Zbuduj równoległy znormalizowany schemat "customers"/"products"/"orders" i
         * przenieś dane. Wypisz raport porównawczy "PRZED / PO": liczba fizycznych
         * wierszy z danymi klienta w obu wariantach, liczba fizycznych wierszy z
         * danymi produktu w obu wariantach.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullNormalizationPipelineWithValidation {
        /*
         * 🧪 Zadanie 21:
         * Utwórz "orders_flat" (id, customer_name, customer_email, product_name,
         * product_price, quantity) z 10 wierszami realistycznych danych (4 klientów,
         * 5 produktów, sporo powtórzeń). Napisz KOMPLETNY pipeline migracji do 3NF:
         * (1) utwórz "customers"/"products"/"orders"/"order_items", (2) migruj dane z
         * deduplikacją, (3) po migracji zweryfikuj integralność - policz SUM(quantity
         * * product_price) w NOWYM schemacie i porównaj z tą samą sumą policzoną w
         * Javie ze starego orders_flat - muszą się zgadzać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_IdentifyNormalFormViolationsAutomatically {
        /*
         * 🧪 Zadanie 22:
         * Utwórz tabelę "orders_bad" (id, product_id, product_name, product_price,
         * PRIMARY KEY(id)) - kolumna id jest kluczem prostym, ale product_name i
         * product_price ZALEŻĄ w rzeczywistości od product_id, nie od id (zależność
         * przechodnia - naruszenie 3NF). Wstaw dane z powtórzeniami tej samej
         * kombinacji product_id+product_name+product_price. Napisz metodę w Javie,
         * która wykrywa tę zależność funkcyjną: dla każdej unikalnej wartości
         * product_id sprawdza, czy WSZYSTKIE wiersze z tym product_id mają IDENTYCZNE
         * product_name i product_price - jeśli tak, to sygnał naruszenia 3NF (dana
         * nie zależy od głównego klucza, tylko od innej kolumny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PerformanceTradeoffMeasurement {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj DWA warianty schematu z 500 zamówieniami: (A) znormalizowany
         * "users"+"orders" (wymaga 2 zapytań, żeby dostać nazwę klienta danego
         * zamówienia - bez JOIN, poznamy w Lesson14), (B) zdenormalizowany
         * "orders_flat" z nazwą klienta wprost w tabeli (1 zapytanie). Zmierz czas
         * (System.nanoTime()) wykonania 100 odczytów "nazwa klienta dla zamówienia X"
         * w obu wariantach i wypisz porównanie - skomentuj kiedy taki kompromis (2
         * zapytania vs redundancja) ma sens.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_NormalizeManyToManyWithAttributes {
        /*
         * 🧪 Zadanie 24:
         * Utwórz "źle" zaprojektowaną tabelę "enrollments_flat" (student_id,
         * student_name, course_id, course_name, grade) z 6 wierszami, gdzie
         * student_name i course_name są zduplikowane wielokrotnie. Zaprojektuj
         * poprawny schemat 3NF: "students" (id, name), "courses" (id, name),
         * "enrollments" (student_id FK, course_id FK, grade, PRIMARY
         * KEY(student_id, course_id)). Przepisz dane z deduplikacją słowników
         * students/courses.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConsistencyBugCausedByDenormalization {
        /*
         * 🧪 Zadanie 25:
         * Odtwórz "orders_flat" (id, customer_email, customer_name) z 4 zamówieniami
         * tego samego klienta. Zasymuluj "bug" powstały z braku normalizacji:
         * zaktualizuj customer_name w 2 z 4 wierszy (zapominając o pozostałych 2 -
         * typowy błąd programisty w zdenormalizowanym schemacie). Napisz metodę
         * detectInconsistency(Statement stmt), która wykrywa klientów mających WIĘCEJ
         * NIŻ JEDNĄ różną wartość customer_name dla tego samego customer_email i
         * wypisuje ostrzeżenie - to właśnie problem, którego normalizacja by uniknęła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignDecisionJustifiedDenormalization {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj system analityczny: znormalizowany "sales" (id, product_id FK,
         * amount, sale_date) + "products" (id, name, category) ORAZ CELOWO
         * zdenormalizowaną tabelę "sales_summary_daily" (sale_date, category,
         * total_amount) wypełnianą RAZ dziennie (symulacja: napisz metodę
         * refreshSummary(Statement stmt), która przelicza sales_summary_daily z
         * danych sales przez agregację w Javie i nadpisuje ją). Wstaw dane, odśwież
         * podsumowanie i wypisz je - skomentuj, czemu w systemie analitycznym (dużo
         * odczytu raportowego) taka denormalizacja jest uzasadniona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiStepNormalizationFrom1NFTo3NF {
        /*
         * 🧪 Zadanie 27:
         * Zacznij od tabeli "orders_raw" (id, customer_name, customer_email,
         * items_csv VARCHAR(300)) - items_csv zawiera kilka par "nazwa:cena"
         * oddzielonych średnikiem (naruszenie 1NF). Wykonaj pełną migrację w 3 krokach
         * udokumentowanych w println: (KROK 1 -> 1NF) rozbij items_csv na osobne
         * wiersze w tabeli order_items_temp (order_id, product_name, price), (KROK 2
         * -> 2NF/3NF) wydziel products (id, name, price) z order_items_temp, unikając
         * duplikatów, (KROK 3) wydziel customers (id, name, email) z orders_raw. Na
         * końcu wypisz finalne, w pełni znormalizowane tabele.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ValidateNoRedundancyAfterMigration {
        /*
         * 🧪 Zadanie 28:
         * Po migracji analogicznej do Zadania 21 (dowolny mini-schemat sklepu do
         * 3NF), napisz metodę validateNoRedundancy(Connection conn), która dla KAŻDEJ
         * tabeli słownikowej (np. customers, products) sprawdza (SELECT COUNT(*) vs
         * COUNT(DISTINCT klucz_naturalny, np. email dla customers) ), że nie ma
         * fizycznych duplikatów tej samej encji pod różnymi id - wypisz raport
         * "brak redundancji" albo "wykryto redundancję" dla każdej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_HybridSchemaWithControlledDuplication {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj HYBRYDOWY schemat: w pełni znormalizowane "users"/"orders", ale
         * z JEDNĄ świadomie zduplikowaną kolumną "orders.customer_name_snapshot"
         * (kopia imienia klienta w MOMENCIE złożenia zamówienia - typowy, uzasadniony
         * wzorzec w e-commerce, żeby historia zamówień nie zmieniała się, gdy klient
         * później zmieni imię). Wstaw użytkownika, złóż zamówienie (zapisując
         * snapshot), potem ZMIEŃ imię użytkownika w users - zweryfikuj, że stare
         * zamówienie WCIĄŻ pokazuje stare imię (snapshot), a users.name jest już inne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneFullSchemaNormalizationAudit {
        /*
         * 🧪 Zadanie 30:
         * Utwórz kompletnie zdenormalizowaną tabelę "shop_flat" (id, customer_name,
         * customer_email, product_name, product_category, product_price, quantity,
         * order_date) z 12 wierszami realistycznych danych (4 klientów, 5 produktów w
         * 3 kategoriach, z dużą liczbą powtórzeń). Napisz kompletny pipeline: (1)
         * zidentyfikuj i wypisz naruszenia 1NF/2NF/3NF na podstawie analizy danych w
         * Javie (jak w poprzednich zadaniach), (2) zaprojektuj i zbuduj w pełni
         * znormalizowany schemat 3NF (customers, categories, products, orders,
         * order_items), (3) zmigruj wszystkie dane z deduplikacją, (4) zweryfikuj
         * zgodność sumy wartości zamówień PRZED i PO migracji, (5) wypisz końcowy
         * raport "=== RAPORT NORMALIZACJI ===" z podsumowaniem redukcji liczby
         * fizycznych wierszy ze zduplikowanymi danymi.
         */
        public static void main(String[] args) { }
    }
}
