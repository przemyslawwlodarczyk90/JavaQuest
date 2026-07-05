package com.example.javaquest._10_dao.Lesson10_RepositoryVsDao;

public class _Exercises_Lesson10_RepositoryVsDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DaoStyleUserMethods {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l10ex01;DB_CLOSE_DELAY=-1" z tabelą users (id,
         * first_name, email, active), napisz UserDao w stylu TECHNICZNYM:
         * findById(Long), findAll() - dokładnie jak w lekcji. Zapisz 3
         * użytkowników i wywołaj obie metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RepositoryStyleSameData {
        /*
         * 🧪 Zadanie 2:
         * Na TEJ SAMEJ tabeli, napisz UserRepository w stylu DOMENOWYM:
         * findActiveUsers() (WHERE active = TRUE) i findByEmailDomain(String).
         * Zapisz mix aktywnych/nieaktywnych użytkowników i wywołaj obie metody
         * Repository.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReadCallsAloudComparison {
        /*
         * 🧪 Zadanie 3:
         * Wywołaj userDao.findById(1L) i userRepository.findActiveUsers() jedno
         * po drugim i w komentarzu w kodzie napisz "przeczytanie na głos" obu
         * wywołań - co trzeba wiedzieć o strukturze bazy, żeby zrozumieć każde z
         * nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ProductRepositoryDomainNaming {
        /*
         * 🧪 Zadanie 4:
         * Tabela products (id, name, price, in_stock BOOLEAN). Napisz
         * ProductRepository z metodami domenowymi: findAvailableProducts()
         * (WHERE in_stock = TRUE) i findExpensiveProducts(BigDecimal threshold)
         * (WHERE price > ?). Przetestuj obie na 5 produktach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SameSqlDifferentMethodName {
        /*
         * 🧪 Zadanie 5:
         * Napisz DWIE metody wykonujące IDENTYCZNY SQL (SELECT * FROM users WHERE
         * active = TRUE): jedną nazwaną technicznie findByActiveTrue() (styl DAO)
         * i jedną nazwaną domenowo findActiveUsers() (styl Repository). Wypisz w
         * komentarzu, która nazwa jest bardziej zrozumiała dla osoby NIEZNAJĄCEJ
         * struktury tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UserRepositoryReactivateMethod {
        /*
         * 🧪 Zadanie 6:
         * Dodaj do UserRepository metodę reactivateUser(Long id) (UPDATE ... SET
         * active = TRUE WHERE id = ?) - nazwana JĘZYKIEM BIZNESU, nie
         * "setActiveTrue". Zapisz nieaktywnego użytkownika, reaktywuj go i
         * zweryfikuj przez findActiveUsers().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DaoMethodNamesMatchSqlDirectly {
        /*
         * 🧪 Zadanie 7:
         * Napisz UserDao.findByActiveAndEmailLike(boolean active, String pattern)
         * - nazwa metody bezpośrednio odzwierciedla warunki WHERE (styl DAO
         * skrajnie techniczny). Porównaj w komentarzu z tym, jak wyglądałaby
         * nazwa "domenowa" tej samej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BothStylesSharingSameConnection {
        /*
         * 🧪 Zadanie 8:
         * Utwórz JEDNO Connection i przekaż je DO UserDao i UserRepository
         * (obie klasy operują na tej samej tabeli users). Wywołaj insert przez
         * jedną z klas (dodaj metodę insert do UserDao) i odczyt przez drugą
         * (findActiveUsers z Repository) - pokaż, że obie "widzą" te same dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CountUsersBothStyles {
        /*
         * 🧪 Zadanie 9:
         * Napisz UserDao.count() (styl techniczny) i UserRepository.totalRegisteredUsers()
         * (styl domenowy) - obie wykonują IDENTYCZNY SQL (SELECT COUNT(*)).
         * Zweryfikuj, że dają ten sam wynik liczbowy dla tej samej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ChooseNamingConsistently {
        /*
         * 🧪 Zadanie 10:
         * Zaprojektuj OrderDao (styl DAO: findById, findByStatus, insert) DLA
         * tabeli orders (id, status, customer_email). W main uzasadnij w
         * komentarzu wybór stylu DAO dla tego konkretnego przypadku (np. bo
         * aplikacja jest mała i nie ma potrzeby "udawania kolekcji domenowej").
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RepositoryHidesSqlCompletelyFromCaller {
        /*
         * 🧪 Zadanie 11:
         * Napisz OrderRepository z metodami domenowymi: findPendingOrders(),
         * findOrdersReadyToShip(), markAsShipped(Long orderId) - żadna z tych
         * nazw nie zawiera słowa "status" ani nazwy kolumny wprost. Przetestuj
         * wszystkie trzy na 5 zamówieniach o różnych statusach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TranslateDaoStyleToRepositoryStyle {
        /*
         * 🧪 Zadanie 12:
         * Dany jest UserDao ze stylu technicznego (findByActiveAndEmailLike z
         * Zadania 7). Napisz jego "tłumaczenie" na styl Repository:
         * findActiveCompanyUsers(String domain) - ta sama logika SQL, inna nazwa
         * metody. Zademonstruj obie wersje na tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RepositoryComposesMultipleQueriesIntoOneBusinessMethod {
        /*
         * 🧪 Zadanie 13:
         * Napisz CustomerRepository.findVipCustomers() - reguła biznesowa "VIP" to
         * klient z co najmniej 3 zamówieniami o statusie "SHIPPED" (JOIN + GROUP
         * BY + HAVING COUNT(*) >= 3). Nazwa metody NIE zdradza szczegółów SQL.
         * Przetestuj na 4 klientach z różną liczbą zamówień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DaoAndRepositoryForSameEntityDifferentConsumers {
        /*
         * 🧪 Zadanie 14:
         * Napisz OBA - ProductDao (dla warstwy administracyjnej, potrzebującej
         * technicznego CRUD: findById, update, deleteById) i ProductRepository
         * (dla warstwy sprzedażowej, potrzebującej findBestSellers(),
         * findOnSale()) - operujące na TEJ SAMEJ tabeli products, każdy dla
         * innego "konsumenta" tych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RenameExistingDaoMethodsToDomainLanguage {
        /*
         * 🧪 Zadanie 15:
         * Weź UserDao z metodami technicznymi (insert, findById, findAll,
         * deleteById) i napisz UserRepository OWIJAJĄCY go (delegacja) z
         * odpowiednikami domenowymi: registerUser (zamiast insert),
         * findUserProfile (zamiast findById), listAllMembers (zamiast findAll),
         * removeMembership (zamiast deleteById). Zademonstruj, że wewnątrz
         * Repository woła te same metody DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BusinessAnalystReadableMethodNames {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj SubscriptionRepository (tabela subscriptions: id, user_email,
         * plan, expires_on) z metodami CZYTELNYMI dla analityka biznesowego:
         * findExpiringSoon(int daysAhead), findPremiumSubscribers(),
         * hasActiveSubscription(String email). Zaimplementuj i przetestuj
         * wszystkie trzy na przykładowych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DaoStyleForInternalAdminTool {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj AdminUserDao (styl czysto techniczny, bo to "wewnętrzne
         * narzędzie administracyjne") z metodami: updateColumn(String table,
         * String column, Object value, Long id) - GENERYCZNA, zbudowana
         * dynamicznie (nazwa kolumny w konkatenacji, wartość przez
         * PreparedStatement). Przetestuj na zmianie first_name i email.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConsistentNamingAcrossTeamConvention {
        /*
         * 🧪 Zadanie 18:
         * Napisz DWIE klasy dla tej samej tabeli orders, obie w stylu Repository
         * (bez mieszania stylów): OrderRepository (findPendingOrders,
         * markAsShipped) i CustomerOrderRepository (findOrdersForCustomer,
         * hasOpenOrders). W komentarzu wypisz zasadę: SPÓJNOŚĆ nazewnictwa w
         * całym projekcie jest ważniejsza niż "poprawność" jednego stylu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RepositoryReturningDomainSummaryObject {
        /*
         * 🧪 Zadanie 19:
         * Napisz record CustomerSummary(String name, long orderCount, BigDecimal
         * totalSpent) i CustomerRepository.getCustomerSummary(Long customerId)
         * zwracającą go - metoda "udaje", że to gotowy raport biznesowy, a nie
         * surowy wynik JOIN+GROUP BY. Przetestuj na kliencie z 3 zamówieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorRawSqlCallerIntoRepositoryMethod {
        /*
         * 🧪 Zadanie 20:
         * Dany jest kod w main wykonujący SQL bezpośrednio przez Statement (SELECT
         * first_name FROM users WHERE active = TRUE). Zrefaktoryzuj go, wydzielając
         * to zapytanie do metody UserRepository.findActiveUserNames() - pokaż kod
         * PRZED i PO refaktoryzacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullDomainRepositoryForOrderManagement {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny OrderRepository (tabele orders, order_items,
         * customers) z metodami czysto domenowymi: placeOrder(customerEmail,
         * items), cancelOrder(orderId), findOrderHistory(customerEmail),
         * calculateLifetimeValue(customerEmail). Zaimplementuj wszystkie cztery i
         * przetestuj na scenariuszu jednego klienta z 3 zamówieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SpringDataStyleNamingPreview {
        /*
         * 🧪 Zadanie 22:
         * Napisz UserRepository z metodami NAZWANYMI w stylu Spring Data (choć
         * ręcznie zaimplementowanymi przez JDBC, nie przez Spring):
         * findByActiveTrue(), findByEmailContaining(String), countByActiveTrue().
         * W komentarzu wyjaśnij, że w Spring Data te metody powstałyby SAME z
         * samej nazwy, bez implementacji - tu implementujemy je ręcznie, żeby
         * zrozumieć, co dzieje się "pod spodem".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RepositoryWrappingTransactionalBusinessOperation {
        /*
         * 🧪 Zadanie 23:
         * Napisz AccountRepository.transferFunds(fromAccountId, toAccountId, amount)
         * - nazwa i sygnatura metody są czysto domenowe, ale WEWNĄTRZ metoda
         * wykonuje transakcję JDBC (setAutoCommit(false), 2 UPDATE, commit/rollback).
         * Osoba korzystająca z Repository NIE WIE, że pod spodem jest transakcja SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TwoTeamsDifferentStylesSameDatabase {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj sytuację "dwóch zespołów": zespół A pisze UserDao (styl
         * techniczny, do wewnętrznego panelu admina), zespół B pisze
         * UserRepository (styl domenowy, do publicznego API) - OBIE klasy
         * operują na tej samej tabeli users przez OSOBNE Connection. Wykonaj
         * operacje z obu klas naprzemiennie i sprawdź spójność danych na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RepositoryHidingComplexJoinLogic {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj ProductRepository.findTrendingProducts() - reguła
         * biznesowa: produkt jest "trendy", gdy miał więcej niż 5 zamówień w
         * ciągu ostatnich 7 dni (symuluj daty przez kolumnę order_date DATE i
         * porównanie z CURRENT_DATE - 7). Cała złożoność JOIN+GROUP BY+HAVING+
         * warunek dat jest UKRYTA za jedną, czytelną nazwą metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MigratingFromDaoStyleToRepositoryStyleGradually {
        /*
         * 🧪 Zadanie 26:
         * Napisz UserRepository DZIEDZICZĄCE (przez delegację, nie extends) ze
         * starego UserDao - metody domenowe WEWNĘTRZNIE wywołują metody
         * techniczne DAO, ale stopniowo "przykrywają" je nowszym API. Zademonstruj
         * migrację: kod caller-a najpierw woła dao.findById, potem (po
         * refaktoryzacji) repository.findUserProfile - z identycznym efektem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PerformanceIdenticalRegardlessOfNaming {
        /*
         * 🧪 Zadanie 27:
         * Zmierz (System.nanoTime()) czas wykonania 1000 wywołań userDao.findById
         * oraz 1000 wywołań userRepository.findUserProfile (identyczny SQL, inna
         * nazwa metody). Wypisz porównanie i potwierdź w komentarzu, że nazewnictwo
         * NIE MA wpływu na wydajność - to czysto kwestia czytelności kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DomainDrivenAggregateRepositoryPattern {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj OrderRepository, który zwraca CAŁY "agregat" Order (z
         * listą OrderItem, jak w Lesson08_OneToManyDao) przez JEDNĄ metodę
         * findOrderAggregate(Long orderId) - Repository "udaje", że to jeden,
         * kompletny obiekt biznesowy, mimo że pod spodem wykonuje 2 zapytania SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_NamingConventionAuditAcrossWholeSchema {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj 3 klasy DAO/Repository (dla users, orders, products) -
         * KAŻDĄ konsekwentnie w JEDNYM stylu (wszystkie DAO albo wszystkie
         * Repository). Napisz metodę auditNamingConsistency() sprawdzającą (przez
         * reflection: getClass().getMethods()), czy nazwy metod we wszystkich
         * trzech klasach są w tym samym stylu (np. wszystkie zaczynają się od
         * "find"/"save" vs mieszanka).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMiniApplicationChoosingStylePerLayer {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-aplikację biblioteczną: BookDao (styl techniczny, warstwa
         * "silnika" - CRUD) i LibraryRepository (styl domenowy, warstwa
         * "biznesowa" - borrowBook(title, borrowerEmail), returnBook(title),
         * findOverdueBooks()) - Repository WEWNĘTRZNIE korzysta z BookDao.
         * Zasymuluj scenariusz 5 książek, 3 wypożyczeń, 1 zwrot, wypisz raport
         * końcowy przez metody Repository, nigdy odwołując się bezpośrednio do
         * BookDao z main().
         */
        public static void main(String[] args) { }
    }
}
