package com.example.javaquest._10_dao.Lesson16_ServiceLayer;

public class _Exercises_Lesson16_ServiceLayer {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimpleUserDaoForService {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:lesson16ex01;DB_CLOSE_DELAY=-1" utworz tabele users
         * (id BIGINT PRIMARY KEY, name VARCHAR(100)) i klase UserDao z metoda
         * Optional<String> findNameById(long id) (SELECT po id, SQL identyczny jak
         * w UserDao z lekcji). Wstaw jednego uzytkownika i wypisz wynik findNameById
         * dla jego id oraz dla id nieistniejacego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SimpleProductDaoWithStock {
        /*
         * 🧪 Zadanie 2:
         * Na bazie "jdbc:h2:mem:lesson16ex02;DB_CLOSE_DELAY=-1" utworz tabele products
         * (id, name, quantity_in_stock) i klase ProductDao z metoda
         * Optional<Product> findById(long id) (record Product jak w lekcji: id, name,
         * quantityInStock). Wstaw 2 produkty i wypisz wynik findById dla obu id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ServiceWithSingleDaoNoOrchestration {
        /*
         * 🧪 Zadanie 3:
         * Uzywajac ProductDao z Zadania 2, napisz klase ProductService z JEDNA metoda
         * biznesowa boolean isAvailable(long productId, int requestedQuantity) - NIE
         * dotyka SQL wprost, wywoluje TYLKO productDao.findById. Przetestuj dla
         * produktu z wystarczajacym stanem i z niewystarczajacym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ServiceOrchestratingTwoDaos {
        /*
         * 🧪 Zadanie 4:
         * Na bazie "jdbc:h2:mem:lesson16ex04;DB_CLOSE_DELAY=-1" z tabelami users i
         * products (jak w lekcji), napisz UserProductService z metoda
         * String describePurchase(long userId, long productId) laczaca WYNIKI z DWOCH
         * osobnych DAO (UserDao.findNameById, ProductDao.findById) w jeden opisowy
         * String, np. "Ania chce kupic Klawiature". UserDao i ProductDao NIE moga
         * znac siebie nawzajem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ValidationBeforeDaoCall {
        /*
         * 🧪 Zadanie 5:
         * Rozbuduj OrderService z lekcji o regule: przed wywolaniem JAKIEGOKOLWIEK
         * DAO sprawdz, czy quantity <= 20 (limit jednorazowego zamowienia). Jesli
         * przekracza, wypisz komunikat odrzucenia i NIE wywoluj zadnego DAO.
         * Przetestuj dla quantity=5 (przechodzi dalej) i quantity=50 (odrzucone
         * natychmiast, bez zadnego zapytania SQL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_OrderDaoAndOrderItemDaoIndependence {
        /*
         * 🧪 Zadanie 6:
         * Na bazie "jdbc:h2:mem:lesson16ex06;DB_CLOSE_DELAY=-1" utworz tabele orders i
         * order_items jak w lekcji. Napisz OrderDao (insert(userId) zwracajacy
         * wygenerowane id) i OrderItemDao (insert(orderId, productId, quantity)) jako
         * DWIE zupelnie NIEZALEZNE klasy - zaden z nich nie importuje ani nie odwoluje
         * sie do drugiego. Wywolaj obie recznie z main (bez Service), zeby pokazac,
         * ze DZIALAJA osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ServiceCallsBothDaosInOrder {
        /*
         * 🧪 Zadanie 7:
         * Korzystajac z DAO z Zadania 6, napisz SimpleOrderService z metoda
         * long placeSimpleOrder(long userId, long productId, int quantity)
         * wywolujaca NAJPIERW orderDao.insert, a POTEM orderItemDao.insert z uzyciem
         * zwroconego id zamowienia. Wypisz zwrocone id i sprawdz zawartosc obu tabel
         * przez SELECT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RejectionMessagesPerRule {
        /*
         * 🧪 Zadanie 8:
         * Rozbuduj OrderService z lekcji (albo z Zadania 7) o TRZY oddzielne
         * komunikaty odrzucenia zamowienia: "ilosc niedodatnia", "uzytkownik nie
         * istnieje", "produkt nie istnieje" - kazdy sprawdzany OSOBNO, w KOLEJNOSCI
         * (najpierw walidacja bez SQL, potem sprawdzenia wymagajace DAO). Przetestuj
         * na 4 przypadkach, kazdy naruszajacy inna regule.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ServiceReturnsBooleanInsteadOfPrinting {
        /*
         * 🧪 Zadanie 9:
         * Zmodyfikuj metode placeOrder tak, by zamiast wypisywac komunikaty przez
         * System.out, zwracala boolean sukcesu, a kod WYWOLUJACY (main) sam decydowal,
         * co wypisac na podstawie zwroconej wartosci. Przetestuj na zamowieniu
         * poprawnym i niepoprawnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CountOrdersAfterMultiplePlaceOrderCalls {
        /*
         * 🧪 Zadanie 10:
         * Na bazie "jdbc:h2:mem:lesson16ex10;DB_CLOSE_DELAY=-1" wywolaj placeOrder
         * (Service z Zadania 7 lub podobny) 5 razy z rzedu dla tego samego,
         * istniejacego produktu (za kazdym razem quantity=1), a nastepnie wypisz
         * COUNT(*) z tabeli orders - powinno wynosic 5.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeDaoOrchestration {
        /*
         * 🧪 Zadanie 11:
         * Na bazie "jdbc:h2:mem:lesson16ex11;DB_CLOSE_DELAY=-1" dodaj tabele
         * discounts (product_id, percent) obok users/products/orders/order_items.
         * Napisz DiscountDao z metoda int findPercentByProductId(long productId)
         * (domyslnie 0, jesli nie ma wpisu). Rozbuduj OrderService, by orkiestrowal
         * JUZ CZTERY DAO: user, product, discount, order/order_item - metoda
         * placeOrderWithDiscount wypisuje efektywna cene po rabacie (bez faktycznego
         * pola price w products - przyjmij stala cene bazowa np. 100 w kodzie Service).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_StockCheckBeforeInsert {
        /*
         * 🧪 Zadanie 12:
         * Rozbuduj ProductDao o metode boolean hasEnoughStock(long productId, int
         * quantity) (SELECT quantity_in_stock, porownanie w Javie, NIE w SQL). Uzyj
         * jej w OrderService PRZED wywolaniem orderDao.insert - jesli stanu nie
         * wystarcza, zamowienie NIE jest tworzone (zaden insert do orders). Przetestuj
         * dla ilosci mniejszej i wiekszej niz stan magazynowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ServiceWithLoggingPerStep {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj placeOrder tak, by przed KAZDYM krokiem (walidacja ilosci,
         * sprawdzenie uzytkownika, sprawdzenie produktu, sprawdzenie stanu, insert
         * zamowienia, insert pozycji) wypisywal linie logu w formacie
         * "[KROK n] <opis>". Przetestuj na jednym poprawnym zamowieniu i sprawdz, ze
         * WSZYSTKIE kroki zostaly zalogowane w poprawnej kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleServicesShareSameDao {
        /*
         * 🧪 Zadanie 14:
         * Na bazie "jdbc:h2:mem:lesson16ex14;DB_CLOSE_DELAY=-1" napisz JEDEN
         * ProductDao i DWA rozne Service korzystajace z NIEGO SAMEGO: StoreService
         * (metoda sellProduct zmniejszajaca "wirtualnie" dostepnosc - bez UPDATE, tylko
         * odczyt) i ReportService (metoda printLowStockProducts wypisujaca produkty z
         * quantity_in_stock < 5). Pokaz, ze oba Service dzialaja na tym samym DAO bez
         * duplikowania kodu SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorControllerLogicIntoService {
        /*
         * 🧪 Zadanie 15:
         * Napisz najpierw "zla" metode handleOrderRequestBadWay(connection, userId,
         * productId, quantity) mieszajaca WALIDACJE, SQL sprawdzajacy istnienie
         * uzytkownika/produktu i SQL wstawiajacy zamowienie w JEDNEJ metodzie (bez
         * DAO/Service). Nastepnie zrefaktoryzuj: wydziel UserDao, ProductDao, OrderDao,
         * OrderItemDao oraz OrderService orkiestrujacy je wszystkie - i pokaz, ze
         * wynik dla tych samych danych wejsciowych jest identyczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ServiceValidatesBeforeAnyDaoCallCounter {
        /*
         * 🧪 Zadanie 16:
         * Dodaj do jednego z DAO (np. ProductDao) prywatny licznik statyczny liczacy
         * kazde wywolanie findById (inkrementowany na starcie metody). W main wywolaj
         * placeOrder z quantity=0 (walidacja biznesowa powinna odrzucic PRZED
         * jakimkolwiek wywolaniem DAO) i sprawdz, ze licznik wywolan findById wynosi
         * 0 - dowod, ze walidacja "bez SQL" naprawde wykonuje sie jako pierwsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ServiceHandlesEmptyOptionalGracefully {
        /*
         * 🧪 Zadanie 17:
         * Rozbuduj UserDao o metode findNameById zwracajaca Optional<String>. W
         * OrderService, dla nieistniejacego userId, NIE uzywaj Optional.get() bez
         * sprawdzenia - uzyj Optional.isEmpty()/orElseThrow. Wywolaj placeOrder dla 3
         * nieistniejacych userId i sprawdz, ze zaden z nich nie powoduje
         * NoSuchElementException, tylko czytelny komunikat biznesowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TwoIndependentBusinessOperationsSameService {
        /*
         * 🧪 Zadanie 18:
         * Rozbuduj OrderService o druga metode biznesowa cancelOrder(long orderId)
         * (usuwa wiersz z order_items, potem z orders - proste DELETE przez DAO, BEZ
         * transakcji na tym etapie). Przetestuj: zloz zamowienie przez placeOrder,
         * potem anuluj je przez cancelOrder, a na koniec sprawdz SELECT COUNT(*) z
         * obu tabel - powinny wrocic do 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ServiceConstructorInjectionWithFourDaos {
        /*
         * 🧪 Zadanie 19:
         * Napisz OrderService, ktorego konstruktor przyjmuje WSZYSTKIE cztery DAO
         * (UserDao, ProductDao, OrderDao, OrderItemDao) jako parametry (jak w lekcji).
         * Zbuduj DWIE INSTANCJE OrderService na DWOCH ROZNYCH bazach H2
         * ("jdbc:h2:mem:lesson16ex19a" i "..ex19b") i wykonaj to samo zamowienie na
         * obu - pokaz, ze sa calkowicie IZOLOWANE (zmiana w jednej bazie nie wplywa na
         * druga).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PrintOrdersJoinAfterMultipleAttempts {
        /*
         * 🧪 Zadanie 20:
         * Na bazie "jdbc:h2:mem:lesson16ex20;DB_CLOSE_DELAY=-1" wykonaj SERIE 6
         * wywolan placeOrder z roznymi kombinacjami poprawnych/niepoprawnych danych
         * (jak w przykladach z lekcji: poprawne, ilosc=0, nieistniejacy produkt,
         * nieistniejacy user, i 2 kolejne poprawne). Na koncu uzyj metody printOrders
         * (JOIN orders + order_items, jak w lekcji) i sprawdz, ze widac TYLKO
         * zamowienia z poprawnych wywolan.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullECommerceFlowFourDaos {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj pelny, samodzielny scenariusz e-commerce oparty na strukturze z
         * lekcji: 3 uzytkownikow, 4 produkty (z roznym stanem magazynowym), oraz
         * OrderService orkiestrujacy UserDao/ProductDao/OrderDao/OrderItemDao.
         * Wykonaj 10 zamowien (mieszanka poprawnych i naruszajacych rozne reguly) i
         * wypisz PODSUMOWANIE: liczba sukcesow, liczba odrzuceń z podzialem na typ
         * przyczyny (ilosc, brak usera, brak produktu, brak stanu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ServiceComposesTwoOtherServices {
        /*
         * 🧪 Zadanie 22:
         * Napisz NotificationService (metoda notifyOrderPlaced(String userName,
         * String productName) - tylko wypisuje sformatowany "e-mail" na konsole, ZERO
         * SQL) i rozbuduj OrderService tak, by po udanym zlozeniu zamowienia
         * WOLAL notificationService.notifyOrderPlaced. Pokaz, ze OrderService orkiestruje
         * teraz i DAO, i INNY SERVICE - ale NotificationService nie wie nic o SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DaoIgnoranceOfOtherDaoVerified {
        /*
         * 🧪 Zadanie 23:
         * Napisz test (bez frameworka, prosty main z if/throw jak w lekcji o
         * testowaniu) sprawdzajacy STATYCZNIE (przegladajac kod recznie w komentarzu)
         * i DYNAMICZNIE (poprzez wywolanie), ze UserDao NIE ma zadnej metody ani pola
         * odwolujacego sie do ProductDao/OrderDao. Zaimplementuj to jako prosty
         * "test strukturalny": stworz UserDao caly czas majac dostep TYLKO do
         * Connection, i wypisz komunikat potwierdzajacy, ze klasa kompiluje sie bez
         * zadnego importu innych DAO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BusinessRuleChangedWithoutTouchingDao {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj OrderService z regula "maksymalna ilosc na zamowienie = 10".
         * Wykonaj 3 zamowienia (2 w limicie, 1 poza limitem). Nastepnie ZMIEN regule w
         * Service na "maksymalna ilosc = 5" (bez dotykania ANI JEDNEGO DAO) i
         * wykonaj TE SAME 3 zamowienia ponownie na NOWEJ, czystej bazie - pokaz w
         * komentarzu, ze wynik dla drugiego z trzech zamowien sie zmienil, mimo ze
         * DAO pozostaly identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ServiceLevelRetryWithoutTransaction {
        /*
         * 🧪 Zadanie 25:
         * Na bazie "jdbc:h2:mem:lesson16ex25;DB_CLOSE_DELAY=-1" napisz metode
         * placeOrderWithRetry(userId, productId, quantity, maxAttempts) w OrderService,
         * ktora w petli probuje wywolac placeOrder, a jesli produkt akurat ma stan 0
         * (symulacja "chwilowego braku"), CZEKA (Thread.sleep(50)) i probuje ponownie,
         * maksymalnie maxAttempts razy. Zademonstruj na scenariuszu, gdzie stan
         * magazynowy jest "doladowywany" (recznym UPDATE) miedzy pierwsza i druga
         * proba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiStepValidationReport {
        /*
         * 🧪 Zadanie 26:
         * Rozbuduj OrderService o metode List<String> validate(userId, productId,
         * quantity) zwracajaca LISTE WSZYSTKICH naruszonych regul (a nie tylko
         * pierwszej znalezionej) - np. moze zwrocic jednoczesnie "ilosc niedodatnia"
         * i "produkt nie istnieje" dla tego samego wywolania. Metoda placeOrder
         * wywoluje validate() i odrzuca zamowienie, jesli lista nie jest pusta,
         * wypisujac WSZYSTKIE bledy naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ServiceWithPluggableDiscountStrategy {
        /*
         * 🧪 Zadanie 27:
         * Zdefiniuj funkcyjny interfejs DiscountStrategy z metoda
         * BigDecimal apply(BigDecimal basePrice, int quantity). Napisz DWIE
         * implementacje (np. "rabat 10% dla ilosci >= 5" i "brak rabatu") i
         * rozbuduj OrderService, by PRZYJMOWAL DiscountStrategy w konstruktorze i
         * uzywal jej do wyliczenia ceny koncowej zamowienia - bez zmiany ani jednego
         * DAO. Przetestuj to samo zamowienie z obiema strategiami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AuditTrailAcrossMultipleServiceCalls {
        /*
         * 🧪 Zadanie 28:
         * Na bazie "jdbc:h2:mem:lesson16ex28;DB_CLOSE_DELAY=-1" dodaj tabele
         * order_log (id, message) i OrderLogDao z metoda insert(String message).
         * Rozbuduj OrderService, by KAZDA proba zlozenia zamowienia (sukces LUB
         * odrzucenie, z powodem) dopisywala jeden wiersz do order_log. Wykonaj 5
         * zamowien o roznych wynikach i wypisz cala zawartosc order_log na koniec,
         * jako "historie" operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ServiceLayerUnawareOfConnectionLifecycle {
        /*
         * 🧪 Zadanie 29:
         * Napisz OrderService, ktorego konstruktor przyjmuje TYLKO cztery DAO (bez
         * Connection, bez URL bazy) - Service nie wie NIC o tym, jak/kiedy polaczenie
         * zostalo otwarte. W main otworz Connection w try-with-resources, zbuduj
         * wszystkie DAO na NIM, przekaz je do Service, wykonaj 2 zamowienia, a
         * dopiero PO wyjsciu z try-with-resources (zamknieciu polaczenia) sprobuj
         * (w NOWYM polaczeniu) odczytac dane - pokaz, ze dane PRZETRWALY zamkniecie
         * polaczenia (H2 in-memory z DB_CLOSE_DELAY=-1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullServiceLayerMiniApp {
        /*
         * 🧪 Zadanie 30:
         * Zloz "mini-aplikacje" sklepu: tabele users, products, orders, order_items
         * (jak w lekcji) + discounts (z Zadania 11) + order_log (z Zadania 28).
         * Napisz OrderService orkiestrujacy WSZYSTKIE potrzebne DAO, z regulami
         * biznesowymi (ilosc dodatnia, limit ilosci, istnienie usera/produktu, stan
         * magazynowy, rabat), logujacy KAZDA probe do order_log. Zasymuluj 8
         * zamowien o roznych wynikach, wypisz raport koncowy (sukcesy/odrzucenia z
         * podzialem na przyczyny) i pelna zawartosc order_log.
         */
        public static void main(String[] args) { }
    }
}
