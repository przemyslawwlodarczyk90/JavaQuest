package com.example.javaquest._08_sql.Lesson03_TableDesign;

public class _Exercises_Lesson03_TableDesign {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ColumnVsTableDecisionPrint {
        /*
         * 🧪 Zadanie 1:
         * Bez łączenia się z bazą: wypisz println decyzję "kolumna czy tabela" dla 4
         * przykładów z krótkim uzasadnieniem: (1) imię użytkownika, (2) adresy dostawy
         * użytkownika (może być wiele), (3) cena produktu, (4) kategoria produktu
         * (współdzielona między wieloma produktami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OneToOneUserProfile {
        /*
         * 🧪 Zadanie 2:
         * Połącz się z bazą H2 (jdbc:h2:mem:ex02_design;DB_CLOSE_DELAY=-1). Utwórz
         * "users" (id INT PRIMARY KEY, name VARCHAR(100)) oraz "user_profiles" (user_id
         * INT PRIMARY KEY REFERENCES users(id), bio VARCHAR(200)) - relacja 1:1 (klucz
         * obcy jest jednocześnie kluczem głównym tabeli profili). Wstaw 2 użytkowników
         * i po jednym profilu, wypisz oba profile.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OneToManyUsersOrders {
        /*
         * 🧪 Zadanie 3:
         * Utwórz "users" (id, name) i "orders" (id, user_id FK -> users) - relacja 1:N.
         * Wstaw 1 użytkownika i 3 zamówienia dla niego. Wypisz liczbę zamówień
         * użytkownika (SELECT COUNT(*) FROM orders WHERE user_id = ?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ManyToManyJunctionTableBasic {
        /*
         * 🧪 Zadanie 4:
         * Utwórz "orders" (id, customer_name), "products" (id, name) oraz tabelę
         * pośrednią "orders_products" (order_id FK, product_id FK, quantity INT,
         * PRIMARY KEY(order_id, product_id)) - relacja M:N. Wstaw 1 zamówienie, 2
         * produkty i 2 wiersze w tabeli pośredniej. Wypisz zawartość orders_products.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NamingConventionTableNamesPlural {
        /*
         * 🧪 Zadanie 5:
         * W bazie jdbc:h2:mem:ex05_design utwórz DWIE tabele zgodnie z konwencją z
         * lekcji: "products" (liczba mnoga, snake_case) i "order_items" (snake_case,
         * liczba mnoga). Wstaw po 1 wierszu do każdej i wypisz je - w komentarzu println
         * wyjaśnij, czemu NIE nazwano ich "product" i "orderItem".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ForeignKeyNamingConvention {
        /*
         * 🧪 Zadanie 6:
         * Utwórz "categories" (id, name) i "products" (id, category_id INT, name,
         * FOREIGN KEY (category_id) REFERENCES categories(id)) - zgodnie z konwencją
         * nazywania kluczy obcych z lekcji (nazwa_tabeli_docelowej w liczbie
         * pojedynczej + "_id"). Wstaw dane i wykonaj zapytanie po category_id, wypisując
         * nazwy produktów danej kategorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UniqueForeignKeyEnforcesOneToOne {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz users/user_profiles z Zadania 2. Wstaw użytkownika i jego profil.
         * Spróbuj wstawić DRUGI profil dla TEGO SAMEGO user_id - ponieważ user_id jest
         * kluczem głównym tabeli user_profiles, baza odrzuci wstawienie. Złap
         * SQLException i wypisz komunikat, komentując, że to gwarantuje relację 1:1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddressesOneToManyDemo {
        /*
         * 🧪 Zadanie 8:
         * Odtwórz "users" (id, name) i "addresses" (id, user_id FK, city, street) z
         * lekcji - relacja 1:N (jeden użytkownik może mieć wiele adresów). Wstaw 1
         * użytkownika i 3 adresy (dom, praca, rodzice). Wypisz wszystkie adresy tego
         * użytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_JunctionTableRequiredForManyToMany {
        /*
         * 🧪 Zadanie 9:
         * Utwórz "students" (id, name) i "courses" (id, name). Spróbuj (świadomie źle)
         * zamodelować relację M:N JEDNĄ kolumną klucza obcego w "students" (np.
         * course_id INT) - wypisz println wyjaśniający, czemu to NIE DZIAŁA, jeśli
         * student ma być zapisany na WIĘCEJ NIŻ JEDEN kurs (kolumna może wskazywać
         * tylko na jeden wiersz). Następnie utwórz poprawną tabelę pośrednią
         * "students_courses" i zademonstruj zapis jednego studenta na 2 kursy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ColumnNamingSingularSnakeCase {
        /*
         * 🧪 Zadanie 10:
         * W bazie jdbc:h2:mem:ex10_design utwórz tabelę "products" z kolumnami
         * zgodnymi z konwencją (liczba pojedyncza, snake_case): id, name, unit_price,
         * created_at. Wstaw 2 wiersze i wypisz je - w println skomentuj, czemu kolumna
         * nazywa się "unit_price", a nie np. "UnitPrice" czy "prices".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_JunctionTableWithExtraColumn {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz orders/products/orders_products (M:N) z Zadania 4, gdzie
         * orders_products ma dodatkową kolumnę "quantity". Wstaw 1 zamówienie z 3
         * różnymi produktami w różnych ilościach. Policz (bez JOIN - poznamy w
         * Lesson14, użyj osobnych zapytań w pętli) łączną liczbę sztuk w zamówieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CategoriesProductsOneToManyWithCount {
        /*
         * 🧪 Zadanie 12:
         * Odtwórz "categories" (id, name) i "products" (id, category_id FK, name,
         * price) z lekcji. Wstaw 3 kategorie i po różnej liczbie produktów w każdej
         * (np. 4, 1, 0). Dla każdej kategorii wypisz jej nazwę i liczbę produktów
         * (SELECT COUNT(*) WHERE category_id = ?), włącznie z kategorią bez produktów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ChooseTableOrColumnForPhoneNumbers {
        /*
         * 🧪 Zadanie 13:
         * Zamodeluj DWA warianty przechowywania numerów telefonu użytkownika: (A)
         * tabela "users_single_phone" (id, name, phone VARCHAR(20)) - zakładająca
         * DOKŁADNIE jeden numer, (B) tabela "users_multi" (id, name) + "phones" (id,
         * user_id FK, phone VARCHAR(20)) - zakładająca WIELE numerów. Zademonstruj
         * wstawienie 3 numerów dla jednego użytkownika w wariancie B (co jest
         * niemożliwe w wariancie A bez zmiany schematu) i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ThreeTierOneToManyChain {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj 3-poziomowy łańcuch relacji 1:N: "departments" (id, name) ->
         * "teams" (id, department_id FK, name) -> "employees" (id, team_id FK, name).
         * Wstaw 1 dział, 2 zespoły, po 2 pracowników w każdym. Dla każdego zespołu
         * wypisz jego nazwę i listę pracowników (osobne zapytania po team_id).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ManyToManyWithBidirectionalQueries {
        /*
         * 🧪 Zadanie 15:
         * Odtwórz orders/products/orders_products (M:N) z Zadania 4. Wstaw 2
         * zamówienia i 2 produkty, gdzie jeden produkt występuje w OBU zamówieniach.
         * Napisz zapytanie "od produktu do zamówień" (SELECT order_id FROM
         * orders_products WHERE product_id = ?) i zapytanie "od zamówienia do
         * produktów" (SELECT product_id FROM orders_products WHERE order_id = ?) -
         * zademonstruj oba kierunki relacji M:N.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorDenormalizedToNormalized {
        /*
         * 🧪 Zadanie 16:
         * Utwórz "źle" zaprojektowaną tabelę "orders_flat" (id, customer_name,
         * customer_email, product_name, product_price) i wstaw 3 wiersze, gdzie
         * customer_name/email się powtarzają. Zaprojektuj i utwórz poprawny schemat
         * "customers" (id, name, email) + "orders" (id, customer_id FK, product_name,
         * product_price), a następnie w Javie przepisz dane z orders_flat do nowego
         * schematu (SELECT z orders_flat w pętli, INSERT do customers/orders, unikając
         * duplikatów klienta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompositeKeyJunctionWithPrimaryKeyCheck {
        /*
         * 🧪 Zadanie 17:
         * Odtwórz students/courses/students_courses (M:N, klucz złożony
         * PRIMARY KEY(student_id, course_id)) z lekcji. Wstaw parę (1, 100), a potem
         * spróbuj wstawić DOKŁADNIE TĘ SAMĄ parę drugi raz - złap SQLException
         * potwierdzający, że klucz złożony wymusił unikalność KAŻDEJ PARY, a nie
         * pojedynczej kolumny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SchemaDesignDecisionTable {
        /*
         * 🧪 Zadanie 18:
         * Bez łączenia się z bazą: wypisz w formie tabelarycznych linii println
         * porównanie relacji 1:1, 1:N, M:N wg 3 kryteriów: sposób modelowania (klucz
         * obcy + UNIQUE / zwykły klucz obcy / tabela pośrednia), typowy przykład,
         * kierunek zależności (kto "wie" o kim).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_NamingConventionViolationVsCorrect {
        /*
         * 🧪 Zadanie 19:
         * Utwórz DWIE równoległe tabele o identycznej strukturze, ale różnym
         * nazewnictwie: "Product" (id, ProductName, Order_Item_Id) - naruszająca
         * konwencję z lekcji, oraz "products" (id, name, order_item_id) - zgodna z
         * konwencją. Wstaw po 1 wierszu do każdej i wypisz obie - w println wypisz 3
         * konkretne naruszenia konwencji w pierwszej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullShopSchemaWithAllThreeRelationTypes {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj schemat sklepu obejmujący WSZYSTKIE 3 typy relacji naraz: "users"
         * <-> "user_profiles" (1:1), "users" -> "orders" (1:N), "orders" <-> "products"
         * przez "orders_products" (M:N). Wstaw kompletne dane (1 user, jego profil, 2
         * zamówienia, 3 produkty rozłożone między zamówieniami) i wypisz raport: dla
         * użytkownika jego bio, liczbę zamówień, i dla każdego zamówienia listę
         * produktów.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignBlogSchemaFromScratch {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj OD ZERA schemat bloga (bez podanej struktury): użytkownicy
         * piszą posty (1:N), posty mają wiele tagów, a tagi są współdzielone między
         * postami (M:N przez tabelę pośrednią "post_tags"), oraz każdy post może mieć
         * dokładnie jedną "wyróżnioną" metadanę SEO (1:1, np. "post_seo" z meta_title,
         * meta_description). Utwórz wszystkie tabele z odpowiednimi kluczami, wstaw
         * przykładowe dane obejmujące wszystkie 3 relacje, i zweryfikuj SELECT-ami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MigrateFlatTableToThreeNormalizedTables {
        /*
         * 🧪 Zadanie 22:
         * Utwórz "zdenormalizowaną" tabelę "sales_flat" (id, salesperson_name,
         * region_name, product_name, amount) z 6 wierszami (kilku sprzedawców w kilku
         * regionach, sprzedających różne produkty, z powtórzeniami nazw). Zaprojektuj
         * i zbuduj poprawny schemat: "salespeople" (id, name), "regions" (id, name),
         * "products" (id, name), "sales" (id, salesperson_id FK, region_id FK,
         * product_id FK, amount). Przepisz dane z sales_flat do nowego schematu w
         * Javie, unikając duplikatów słowników (salespeople/regions/products).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_JunctionTableWithCompositeAndSurrogateComparison {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj DWIE wersje tabeli pośredniej dla relacji M:N students/courses: (A)
         * z kluczem ZŁOŻONYM PRIMARY KEY(student_id, course_id), (B) z odrębnym
         * kluczem SUROGATOWYM "id INT PRIMARY KEY" + osobne UNIQUE(student_id,
         * course_id). Wstaw te same dane do obu wariantów i zademonstruj, że OBA
         * odrzucają duplikat tej samej pary (student, course), ale wariant B dodatkowo
         * ma własny, prosty identyfikator wiersza - skomentuj różnicę w println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HierarchicalSelfReferencingDesign {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj tabelę "categories" (id PK, name, parent_id INT REFERENCES
         * categories(id), dopuszczająca NULL) - kategoria może mieć kategorię
         * nadrzędną (self-referencing 1:N). Zbuduj 3-poziomową hierarchię (np.
         * Elektronika -> Komputery -> Laptopy). Napisz metodę w Javie, która dla
         * podanej kategorii "wspina się" po parent_id (osobne zapytania w pętli, bez
         * JOIN) i wypisuje pełną ścieżkę od kategorii do korzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ValidateSchemaAgainstNamingConventionProgrammatically {
        /*
         * 🧪 Zadanie 25:
         * Utwórz 4 tabele o różnym stopniu zgodności z konwencją nazewnictwa z lekcji
         * (niektóre liczba mnoga/snake_case, inne nie). Napisz metodę
         * checkNamingConvention(Connection conn), która przez
         * conn.getMetaData().getTables(...) pobiera nazwy wszystkich tabel i dla
         * każdej sprawdza (prostą heurystyką w Javie, np. czy zawiera wielkie litery
         * lub nie kończy się na "s") i wypisuje, czy nazwa jest zgodna z konwencją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FullEcommerceWithProfileAndReviews {
        /*
         * 🧪 Zadanie 26:
         * Rozszerz mini-schemat sklepu o dodatkowe relacje: "users" <-> "user_profiles"
         * (1:1), "users" -> "orders" (1:N), "orders" <-> "products" przez tabelę
         * pośrednią z quantity (M:N), oraz "products" -> "reviews" (1:N, recenzja ma
         * user_id FK i product_id FK - czyli reviews jest jednocześnie w relacji 1:N
         * z DWOMA tabelami naraz). Wstaw kompletne dane i napisz raport wypisujący dla
         * każdego produktu jego recenzje z imieniem autora (bez JOIN, osobne zapytania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_JunctionTableCascadeCleanup {
        /*
         * 🧪 Zadanie 27:
         * Odtwórz students/courses/students_courses (M:N) z lekcji, gdzie
         * students_courses ma FOREIGN KEY (student_id) REFERENCES students(id) ON
         * DELETE CASCADE. Wstaw studenta zapisanego na 2 kursy. Usuń studenta i
         * zweryfikuj (COUNT(*)), że oba wiersze w students_courses zostały automatycznie
         * usunięte, a same kursy w tabeli "courses" NIE zostały dotknięte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareThreeDesignsForSameProblem {
        /*
         * 🧪 Zadanie 28:
         * Dla problemu "produkt może mieć wiele zdjęć" zaimplementuj i porównaj 2
         * podejścia: (A) kolumna "image_urls VARCHAR(500)" z URL-ami rozdzielonymi
         * przecinkiem (naruszenie 1NF - lista w jednej kolumnie), (B) osobna tabela
         * "product_images" (id, product_id FK, url) - poprawne 1:N. Wstaw te same 3
         * zdjęcia w obu wariantach, a potem zademonstruj w Javie, że w wariancie A
         * trzeba RĘCZNIE parsować String.split(","), a w wariancie B to zwykłe SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ManyToManyWithAttributesAndAggregation {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj "employees" (id, name), "projects" (id, name) i tabelę pośrednią
         * "employee_projects" (employee_id FK, project_id FK, hours_worked INT,
         * PRIMARY KEY(employee_id, project_id)) - relacja M:N z dodatkowym atrybutem.
         * Wstaw 3 pracowników i 2 projekty z różnym przypisaniem godzin. W Javie
         * (osobne zapytania, bez GROUP BY - poznamy w Lesson13) policz łączną liczbę
         * godzin każdego pracownika na WSZYSTKICH projektach i wypisz ranking.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMultiTableSchemaDesignAndValidation {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj i zbuduj kompletny schemat systemu rezerwacji: "customers" (id,
         * name), "rooms" (id, number, capacity), "reservations" (id, customer_id FK,
         * room_id FK, start_date, end_date) - 1:N od obu stron (klient może mieć wiele
         * rezerwacji, pokój może być rezerwowany wiele razy), oraz "amenities" (id,
         * name) + "room_amenities" (room_id FK, amenity_id FK, PRIMARY KEY(room_id,
         * amenity_id)) - M:N między pokojami i udogodnieniami. Wstaw pełne dane
         * testowe (min. 2 klientów, 2 pokoje, 3 rezerwacje, 2 udogodnienia z
         * przypisaniami) i napisz raport końcowy wypisujący dla każdej rezerwacji:
         * imię klienta, numer pokoju, zakres dat i listę udogodnień pokoju (wszystko
         * osobnymi zapytaniami, bez JOIN - to poznamy w Lesson14).
         */
        public static void main(String[] args) { }
    }
}
