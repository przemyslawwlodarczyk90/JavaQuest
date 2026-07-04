package com.example.javaquest._08_sql.Lesson15_SqlRelationships;

public class _Exercises_Lesson15_SqlRelationships {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_OneToOneUserProfile {
        /*
         * 🧪 Zadanie 1:
         * Użyj bazy H2 w pamięci (np. jdbc:h2:mem:lesson15_ex01;DB_CLOSE_DELAY=-1).
         * Utwórz tabele users(id INT PRIMARY KEY, name VARCHAR(100)) oraz
         * user_profiles(user_id INT PRIMARY KEY REFERENCES users(id), bio VARCHAR(255)) –
         * to relacja 1:1. Wstaw 3 użytkowników i po jednym profilu dla każdego,
         * a następnie JOIN-em wypisz imię i bio każdego użytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OneToManyAuthorsBooks {
        /*
         * 🧪 Zadanie 2:
         * Utwórz tabele authors(id, name) i books(id, title, author_id REFERENCES authors(id)) –
         * relacja 1:N. Wstaw 2 autorów i 4 książki (rozłożone nierówno między nich).
         * JOIN-em wypisz każdego autora wraz z tytułami jego książek, posortowane
         * po nazwisku autora, a potem po tytule.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ForeignKeyRejectInsert {
        /*
         * 🧪 Zadanie 3:
         * Utwórz customers(id PK) i orders(id PK, customer_id REFERENCES customers(id)).
         * Wstaw 2 klientów. Spróbuj wstawić zamówienie z customer_id = 999
         * (nieistniejący klient) – złap SQLException i wypisz jego komunikat,
         * potwierdzając, że baza wymusiła integralność referencyjną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ManyToManyJunctionTable {
        /*
         * 🧪 Zadanie 4:
         * Utwórz students(id, name), courses(id, name) oraz tabelę pośrednią
         * students_courses(student_id, course_id, PRIMARY KEY(student_id, course_id))
         * – relacja N:M. Wstaw 3 studentów, 2 kursy i kilka par przypisań
         * (tak, by co najmniej jeden student był zapisany na oba kursy).
         * Wypisz wszystkie pary student-kurs przez JOIN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LeftJoinOptionalRelation {
        /*
         * 🧪 Zadanie 5:
         * Utwórz departments(id, name) i employees(id, name, department_id REFERENCES
         * departments(id) – kolumna dopuszcza NULL). Wstaw 3 pracowników, w tym
         * jednego BEZ przypisanego działu (department_id = NULL). Użyj LEFT JOIN,
         * by wypisać wszystkich pracowników razem z nazwą działu (lub "brak działu").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CountRelatedRows {
        /*
         * 🧪 Zadanie 6:
         * Na bazie authors/books z Zadania 2 (odtwórz je w nowej, osobnej bazie
         * w pamięci), użyj JOIN + GROUP BY + COUNT, by wypisać liczbę książek
         * przypisanych do każdego autora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompositePrimaryKeyDuplicateRejected {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz tabelę pośrednią students_courses z kluczem złożonym
         * (student_id, course_id) z Zadania 4. Wstaw jedną parę (1, 1),
         * a potem spróbuj wstawić DOKŁADNIE TĘ SAMĄ parę drugi raz – złap
         * SQLException potwierdzający, że klucz złożony wymusił unikalność pary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SelfReferencingRelationship {
        /*
         * 🧪 Zadanie 8:
         * Utwórz tabelę employees(id PK, name, manager_id INT REFERENCES employees(id))
         * – relacja "sama do siebie". Wstaw jednego pracownika bez menedżera (manager_id
         * = NULL) i dwóch podwładnych wskazujących na niego. Przez self-join wypisz
         * każdego pracownika razem z imieniem jego menedżera (lub "brak menedżera").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CascadeSimulationManualDelete {
        /*
         * 🧪 Zadanie 9:
         * Utwórz customers(id PK) i orders(id PK, customer_id REFERENCES customers(id)).
         * Wstaw klienta i przypisane mu zamówienie. Spróbuj usunąć klienta – baza
         * powinna odrzucić DELETE (bo istnieje powiązane zamówienie), złap wyjątek.
         * Następnie usuń najpierw zamówienie, a potem klienta – tym razem operacja
         * powinna się powieść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_UniqueForeignKeyEnforcesOneToOne {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz users/user_profiles z Zadania 1. Wstaw użytkownika i jego profil.
         * Spróbuj wstawić DRUGI profil dla TEGO SAMEGO user_id – ponieważ user_id
         * jest jednocześnie kluczem głównym tabeli user_profiles, baza odrzuci
         * wstawienie. Złap wyjątek i skomentuj w println, dlaczego to gwarantuje 1:1.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_OneToManyWithAggregateReport {
        /*
         * 🧪 Zadanie 11:
         * Utwórz customers(id, name) i orders(id, customer_id REFERENCES customers(id),
         * amount INT). Wstaw 3 klientów i po kilka zamówień każdemu. JOIN-em +
         * GROUP BY + SUM policz łączną wartość zamówień na klienta i wypisz
         * ranking malejąco po sumie (ORDER BY ... DESC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ManyToManyWithExtraColumn {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz tabelę pośrednią students_courses o dodatkową kolumnę grade INT.
         * Wstaw studentów, kursy i oceny. Wypisz (JOIN) studentów, których ocena
         * z dowolnego kursu jest wyższa niż 4, razem z nazwą kursu i oceną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ThreeTableJoinChain {
        /*
         * 🧪 Zadanie 13:
         * Utwórz authors(id, name), publishers(id, name) i books(id, title,
         * author_id REFERENCES authors(id), publisher_id REFERENCES publishers(id)).
         * Wstaw dane i wykonaj JOIN łączący WSZYSTKIE TRZY tabele naraz, wypisując
         * tytuł, autora i wydawcę każdej książki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FindEntitiesWithoutRelation {
        /*
         * 🧪 Zadanie 14:
         * Na bazie authors/books (jak w Zadaniu 2), dodaj autora, który NIE MA
         * żadnej książki. Użyj LEFT JOIN + WHERE book.id IS NULL, by znaleźć
         * i wypisać wyłącznie autorów bez żadnej książki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleRelationsPerEntity {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj movies(id, title, director_id REFERENCES directors(id)) – relacja
         * 1:N z directors(id, name) – oraz movies_actors(movie_id, actor_id) jako
         * tabelę pośrednią do relacji N:M z actors(id, name). Wstaw dane dla 2 filmów,
         * po jednym reżyserze i kilku wspólnych aktorach. Wypisz dla każdego filmu:
         * tytuł, reżysera i listę aktorów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompositeKeyRangeQuery {
        /*
         * 🧪 Zadanie 16:
         * Utwórz orders(id, customer_name), products(id, name, price) oraz
         * order_items(order_id, product_id, quantity, PRIMARY KEY(order_id, product_id))
         * jako tabelę N:M z kluczem złożonym. Wstaw 2 zamówienia z kilkoma pozycjami
         * każde. JOIN-em policz i wypisz łączną wartość (quantity * price) każdego
         * zamówienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SelfReferencingTreeDepth {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz self-referencing employees z Zadania 8 do 3 poziomów hierarchii
         * (dyrektor -> kierownik -> pracownik). Wykonaj DWUKROTNY self-join (alias
         * dla menedżera i osobny alias dla "menedżera menedżera"), by wypisać
         * każdego pracownika razem z jego bezpośrednim przełożonym i przełożonym
         * przełożonego (lub "brak", jeśli nie istnieje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ReferentialIntegrityOnUpdate {
        /*
         * 🧪 Zadanie 18:
         * Odtwórz authors/books z Zadania 2. Spróbuj wykonać UPDATE books SET
         * author_id = 999 WHERE id = ... (nieistniejący autor) – złap SQLException
         * i wypisz jego komunikat, potwierdzając, że integralność referencyjna
         * działa też przy UPDATE, nie tylko przy INSERT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_JunctionTableRemoveRelation {
        /*
         * 🧪 Zadanie 19:
         * Odtwórz products_categories z lekcji (products, categories, tabela
         * pośrednia). Wstaw produkt należący do dwóch kategorii. Usuń (DELETE)
         * TYLKO JEDEN wiersz z tabeli pośredniej (jedną kategorię), pozostawiając
         * drugie powiązanie nietknięte. Wypisz kategorie produktu przed i po usunięciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareOneToManyVsManyToMany {
        /*
         * 🧪 Zadanie 20:
         * W jednej bazie zbuduj oba schematy: customers/orders (1:N) oraz
         * students/courses/students_courses (N:M). Napisz raport porównawczy:
         * dla 1:N policz liczbę zamówień na klienta, dla N:M policz liczbę
         * kursów na studenta ORAZ liczbę studentów na kurs – i skomentuj
         * w println różnicę między obiema relacjami.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullSchemaLibrary {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj schemat biblioteki: authors(id,name), books(id,title,author_id)
         * – relacja 1:N, members(id,name), oraz loans(member_id, book_id, loan_date)
         * jako tabelę pośrednią realizującą relację N:M między members a books
         * (członek może wypożyczyć wiele książek, książkę może wypożyczać wielu
         * członków w czasie). Wstaw dane i napisz zapytanie zwracające, jakie
         * książki ma aktualnie wypożyczone każdy członek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullEcommerceRevenueReport {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj customers(id,name), orders(id,customer_id) – 1:N, products(id,name,price)
         * oraz order_items(order_id, product_id, quantity) – tabelę N:M z kluczem
         * złożonym. Wstaw dane dla kilku klientów, zamówień i produktów. Połącz
         * JOIN, GROUP BY i SUM (poznane w Lesson13_Grouping), by policzyć łączny
         * przychód (quantity * price) wygenerowany przez KAŻDY produkt across
         * wszystkich klientów, posortowany malejąco.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TransactionalRelationInsert {
        /*
         * 🧪 Zadanie 23:
         * Utwórz authors(id PK, name NOT NULL) i books(id PK, title NOT NULL,
         * author_id REFERENCES authors(id)). Wyłącz autocommit (setAutoCommit(false))
         * i w jednej transakcji wstaw nowego autora oraz JEGO książki; jeśli
         * którakolwiek książka narusza ograniczenie NOT NULL na title, złap wyjątek
         * i wykonaj rollback() całej transakcji, po czym zweryfikuj (nowym
         * zapytaniem), że ANI autor, ANI żadna książka nie zostały zapisane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HierarchicalCategoriesSelfJoin {
        /*
         * 🧪 Zadanie 24:
         * Utwórz categories(id PK, name, parent_id INT REFERENCES categories(id) –
         * dopuszcza NULL). Zbuduj 2-poziomową hierarchię: kilka kategorii głównych
         * (parent_id = NULL) i kilka podkategorii wskazujących na kategorię główną.
         * Self-joinem wypisz pary "podkategoria -> kategoria nadrzędna".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ManyToManyWithFilteringAndSorting {
        /*
         * 🧪 Zadanie 25:
         * Na bazie students/courses/students_courses (z kolumną grade z Zadania 12),
         * użyj GROUP BY + AVG, by policzyć średnią ocenę każdego studenta z
         * wszystkich jego kursów, a następnie połącz to z WHERE (Lesson11_Filtering)
         * i ORDER BY (Lesson12_Sorting), by wypisać TYLKO studentów ze średnią >= 4.0,
         * posortowanych malejąco po średniej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConstraintDrivenRelationshipDesign {
        /*
         * 🧪 Zadanie 26:
         * Utwórz accounts(id PK, balance INT NOT NULL CHECK (balance >= 0)) oraz
         * account_transactions(id PK, account_id REFERENCES accounts(id) NOT NULL,
         * amount INT NOT NULL CHECK (amount != 0)) – łącząc ograniczenia z
         * Lesson06_DataConstraints z relacją 1:N. Wstaw poprawne dane, a następnie
         * przetestuj po kolei: próbę wstawienia transakcji z amount = 0 (odrzucona),
         * z account_id wskazującym donikąd (odrzucona) i poprawną transakcję (przyjęta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FullEcommerceMiniModel {
        /*
         * 🧪 Zadanie 27:
         * Rozszerz model z Zadania 22 o customers.name i orders.order_date. Napisz
         * zapytanie łączące customers, orders i order_items+products, które dla
         * KAŻDEGO zamówienia wypisuje: nazwę klienta, datę zamówienia i łączną
         * wartość zamówienia (SUM(quantity*price)), posortowane malejąco po wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RecursiveLikeManagerChainLoop {
        /*
         * 🧪 Zadanie 28:
         * Odtwórz self-referencing employees(id, name, manager_id) z co najmniej
         * 4 poziomami hierarchii. W Javie (NIE w SQL – bez CTE/recursive query),
         * napisz pętlę, która dla wybranego pracownika "wspina się" po kolejnych
         * manager_id, wykonując osobne zapytanie na każdym kroku, i wypisuje
         * całą ścieżkę od pracownika do najwyższego przełożonego (aż manager_id
         * będzie NULL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_IntegrityAcrossThreeRelations {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj authors(id,name), books(id,title,author_id) – 1:N, tags(id,name)
         * i book_tags(book_id, tag_id) – N:M. Wstaw dane tak, by kilka książek
         * miało wspólne tagi i wspólnych autorów. Napisz zapytanie z zagnieżdżonymi
         * JOIN-ami, które znajdzie książki napisane przez KONKRETNEGO autora
         * ORAZ oznaczone KONKRETNYM tagiem jednocześnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignAndVerifyNormalizedSchema {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj mini-schemat bloga: users(id,name), posts(id,title,user_id) –
         * 1:N, tags(id,name) + post_tags(post_id,tag_id) – N:M, oraz comments(id,
         * post_id,user_id,content) – 1:N zarówno z posts jak i z users. Wstaw
         * przykładowe dane obejmujące wszystkie relacje. Napisz JEDNO zapytanie
         * łączące wszystkie te tabele, które dla każdego posta wypisuje: tytuł,
         * autora, liczbę tagów i liczbę komentarzy.
         */
        public static void main(String[] args) { }
    }
}
