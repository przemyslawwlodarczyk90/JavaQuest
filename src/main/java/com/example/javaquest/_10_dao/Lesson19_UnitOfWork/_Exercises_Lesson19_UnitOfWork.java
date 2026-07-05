package com.example.javaquest._10_dao.Lesson19_UnitOfWork;

public class _Exercises_Lesson19_UnitOfWork {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SqlWorkFunctionalInterfaceBasics {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj funkcyjny interfejs SqlWork<T> (jak w lekcji: T execute(Connection
         * connection) throws SQLException). Na bazie
         * "jdbc:h2:mem:lesson19ex01;DB_CLOSE_DELAY=-1" z tabela products, napisz
         * najproscistza implementacje jako lambda liczaca COUNT(*) i wywolaj ja
         * recznie (bez klasy UnitOfWork) przekazujac Connection - wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UnitOfWorkExecuteSuccess {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj klase UnitOfWork z lekcji (execute(SqlWork<T>): otwiera
         * polaczenie, wylacza autocommit, wykonuje blok, commituje, zwraca wynik,
         * zamyka polaczenie). Uzyj jej na bazie
         * "jdbc:h2:mem:lesson19ex02;DB_CLOSE_DELAY=-1" do wstawienia jednego produktu
         * i zwrocenia jego wygenerowanego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UnitOfWorkExecuteFailureRollsBack {
        /*
         * 🧪 Zadanie 3:
         * Uzywajac UnitOfWork z Zadania 2, przekaz blok kodu, ktory wstawia produkt,
         * a POTEM rzuca recznie RuntimeException("blad testowy"). Zlap
         * RuntimeException zwrocony przez execute() (opakowany, jak w lekcji) i
         * sprawdz SELECT COUNT(*) - wiersz NIE zostal zapisany (rollback zadzialal).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OrderServiceUsesUnitOfWork {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj OrderService z lekcji (konstruktor przyjmuje UnitOfWork,
         * metoda placeOrder(productId, quantity) wywoluje unitOfWork.execute z lambda
         * laczaca ProductDao.decreaseStock i OrderDao.insert). Wywolaj dla poprawnej
         * ilosci i wypisz zwrocone id zamowienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_OrderServiceFailureCase {
        /*
         * 🧪 Zadanie 5:
         * Uzywajac OrderService z Zadania 4, wywolaj placeOrder z quantity WIEKSZA
         * niz stan magazynowy - zlap RuntimeException i sprawdz stan po probie: 0
         * zamowien w tabeli orders, stan magazynowy NIEZMIENIONY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_GenericTypeParameterDifferentReturnTypes {
        /*
         * 🧪 Zadanie 6:
         * Uzyj JEDNEGO obiektu UnitOfWork do wykonania DWOCH ROZNYCH operacji z
         * ROZNYMI typami wyniku T: raz execute() zwracajace Long (id nowego
         * produktu), raz execute() zwracajace String (nazwa najdrozszego produktu z
         * SELECT). Pokaz, ze ta sama metoda generyczna obsluguje oba przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PrintStateHelperReused {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode printState(url, label) (jak w lekcji: liczba zamowien, stan
         * magazynowy produktu #1) i uzyj jej PRZED i PO wywolaniu udanego placeOrder -
         * pokaz zmiane stanu magazynowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleUnitOfWorkCallsSequential {
        /*
         * 🧪 Zadanie 8:
         * Na bazie "jdbc:h2:mem:lesson19ex08;DB_CLOSE_DELAY=-1" z produktem o stanie
         * 10, wywolaj unitOfWork.execute() TRZY RAZY z rzedu (kazde: insert zamowienia
         * + decreaseStock o 2). Sprawdz stan magazynowy po kazdym wywolaniu (kolejno
         * 8, 6, 4) - kazde wywolanie execute() to WLASNA, niezalezna transakcja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UnitOfWorkWrapsConnectionOpeningItself {
        /*
         * 🧪 Zadanie 9:
         * Zweryfikuj (np. przez logowanie w SqlWork), ze OrderService.placeOrder
         * NIGDZIE sam nie wywoluje DriverManager.getConnection - CALE otwieranie
         * polaczenia jest ukryte wewnatrz UnitOfWork.execute(). Pokaz to poprzez
         * przegladniecie kodu Service w komentarzu i praktyczne dzialanie (Service
         * dostaje TYLKO obiekt UnitOfWork w konstruktorze, nigdy url/Connection).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareManualVsUnitOfWorkLineCount {
        /*
         * 🧪 Zadanie 10:
         * Napisz najpierw RECZNA wersje placeOrderManual (bez UnitOfWork - caly
         * boilerplate setAutoCommit/try/commit/catch/rollback/finally wpisany wprost
         * w metodzie Service). Porownaj ja z placeOrder korzystajacym z UnitOfWork -
         * wykonaj obie dla tych samych danych i w komentarzu porownaj liczbe linii
         * "logiki biznesowej" w kazdej z wersji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UnitOfWorkWithThreeDaoCalls {
        /*
         * 🧪 Zadanie 11:
         * Dodaj tabele users i UserDao.findNameById (przyjmujace Connection).
         * Rozbuduj placeOrder, by lambda przekazana do unitOfWork.execute wywolywala
         * TRZY DAO: UserDao, ProductDao, OrderDao - wszystkie na tym samym Connection
         * otrzymanym z execute(). Zasymuluj blad w kroku UserDao (nieistniejacy
         * user) i sprawdz, ze rollback cofa rowniez ewentualne wczesniejsze kroki
         * (jesli je przestawisz przed user check).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NestedLambdaCapturingOuterVariables {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode placeOrderWithLogging(unitOfWork, productId, quantity)
         * przekazujaca do execute() lambde, ktora WEWNATRZ SIEBIE (na tym samym
         * Connection) najpierw wypisuje log "rozpoczynam", potem wykonuje
         * decreaseStock+insert, potem wypisuje "zakonczono" - wszystko w JEDNEJ
         * lambda-metodzie SqlWork.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UnitOfWorkReturningCustomRecord {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj record OrderResult(long orderId, int remainingStock). Zmodyfikuj
         * placeOrder, by execute() zwracalo OBIEKT OrderResult (T=OrderResult) zamiast
         * samego long - lambda po insercie zamowienia wykonuje dodatkowy SELECT
         * stanu magazynowego i pakuje oba wyniki w rekord. Wypisz zwrocony rekord.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TwoDifferentUnitOfWorkInstancesDifferentDatabases {
        /*
         * 🧪 Zadanie 14:
         * Utworz DWA obiekty UnitOfWork wskazujace na DWIE ROZNE bazy H2
         * ("jdbc:h2:mem:lesson19ex14a" i "...ex14b"), kazda z WLASNYM schematem i
         * danymi. Uzyj OrderService z KAZDYM z nich osobno i pokaz, ze operacje na
         * jednej bazie NIE wplywaja na druga.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExceptionMessageWrappedWithOriginalCause {
        /*
         * 🧪 Zadanie 15:
         * Wywolaj unitOfWork.execute z lambda rzucajaca celowo SQLException (np.
         * zapytanie do nieistniejacej tabeli). Zlap RuntimeException zwrocony przez
         * execute(), wypisz e.getMessage() ORAZ e.getCause() - sprawdz, ze
         * oryginalny SQLException jest zachowany jako cause (jak w implementacji z
         * lekcji: "Transakcja wycofana (rollback): " + e.getMessage()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UnitOfWorkUsedForReadOnlyQuery {
        /*
         * 🧪 Zadanie 16:
         * Uzyj UnitOfWork.execute() do wykonania CZYSTO ODCZYTOWEGO zapytania (SELECT
         * listy produktow, T=List<String>) - bez zadnego INSERT/UPDATE. Sprawdz, ze
         * mechanizm commit/rollback dziala identycznie (commit "na sucho", bez zmian
         * danych) i nie powoduje bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ComparingTwoOrderServicesOneWithOneWithoutUnitOfWork {
        /*
         * 🧪 Zadanie 17:
         * Napisz DRUGI OrderService (OrderServiceLegacy) uzywajacy WLASNEGO, recznego
         * kodu transakcyjnego (bez UnitOfWork). Wywolaj TE SAMA operacje (placeOrder)
         * na obu wersjach dla identycznych danych i porownaj wyniki koncowe (stan
         * magazynowy, liczba zamowien) - powinny byc identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UnitOfWorkWithValidationInsideLambda {
        /*
         * 🧪 Zadanie 18:
         * Rozbuduj lambda przekazywana do execute() o walidacje quantity > 0 NA
         * SAMYM POCZATKU bloku (przed jakimkolwiek SQL) - jesli niepoprawna, rzuc
         * IllegalArgumentException wewnatrz lambdy. Sprawdz, ze UnitOfWork.execute()
         * poprawnie wykonuje rollback (mimo ze polaczenie nie mialo jeszcze zadnych
         * zmian) i opakowuje wyjatek tak samo jak przy bledzie SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ChainingTwoUnitOfWorkCallsManually {
        /*
         * 🧪 Zadanie 19:
         * Wywolaj unitOfWork.execute() DWUKROTNIE z rzedu - pierwsze wywolanie
         * wstawia zamowienie #1, DRUGIE (uzywajac id zwroconego z pierwszego jako
         * danych wejsciowych) wstawia powiazany wpis w order_log. Zwroc uwage w
         * komentarzu, ze to SA DWIE OSOBNE transakcje (nie jedna wspolna) - to
         * swiadomy kompromis tego podejscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureOverheadOfUnitOfWorkAbstraction {
        /*
         * 🧪 Zadanie 20:
         * Zmierz (System.nanoTime()) czas wykonania 100 operacji placeOrder DWOMA
         * sposobami: przez UnitOfWork i przez rowoznaczny, reczny kod transakcyjny
         * (bez abstrakcji). Wypisz oba czasy i skomentuj, czy narzut samej
         * abstrakcji UnitOfWork jest zauwazalny (oczekiwana odpowiedz: znikomy w
         * porownaniu do kosztu samego SQL).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericUnitOfWorkReusedAcrossThreeDifferentServices {
        /*
         * 🧪 Zadanie 21:
         * Uzyj JEDNEGO obiektu UnitOfWork w TRZECH ROZNYCH klasach Service:
         * OrderService (placeOrder), InventoryService (metoda restock(productId, qty)
         * zwiekszajaca stan) i ReportService (metoda countTotalOrders() - tylko
         * SELECT). Wywolaj po jednej operacji z kazdego i pokaz, ze wszystkie
         * korzystaja z tego samego mechanizmu transakcyjnego bez duplikacji kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_UnitOfWorkWithIndependentAuditLog {
        /*
         * 🧪 Zadanie 22:
         * Rozbuduj UnitOfWork o druga metode executeAndLog(SqlWork<T> work, String
         * successMessage, String failureMessagePrefix), ktora PO nieudanej probie
         * (rollback glownej pracy) zapisuje wpis do order_log w OSOBNEJ,
         * NIEZALEZNEJ transakcji (nowe polaczenie, wlasny commit) - tak, by log
         * przetrwal rollback. Zademonstruj na nieudanym zamowieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RetryWrapperAroundUnitOfWork {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode <T> T executeWithRetry(UnitOfWork unitOfWork, SqlWork<T> work,
         * int maxAttempts) probujaca wywolac unitOfWork.execute(work) do maxAttempts
         * razy, lapiac RuntimeException miedzy probami (z krotkim Thread.sleep).
         * Zademonstruj scenariusz, w ktorym pierwsza proba zawodzi (stan=0), a
         * druga (po recznym "doladowaniu" stanu miedzy probami) sie udaje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ConcurrentUnitOfWorkCallsTwoThreads {
        /*
         * 🧪 Zadanie 24:
         * Na bazie "jdbc:h2:mem:lesson19ex24;DB_CLOSE_DELAY=-1" z produktem o stanie
         * 100, uruchom DWA WATKI, kazdy wywolujacy unitOfWork.execute() (placeOrder,
         * quantity=1) 30 razy w petli (60 zamowien lacznie). Poczekaj na zakonczenie
         * obu (join z limitem) i sprawdz, ze stan magazynowy koncowy wynosi
         * DOKLADNIE 40 (100-60) - kazda transakcja UnitOfWork jest atomowa nawet przy
         * wspolbieznym uzyciu z roznych watkow (rozne Connection z tego samego URL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_UnitOfWorkWithTimingMetricsPerCall {
        /*
         * 🧪 Zadanie 25:
         * Rozbuduj UnitOfWork.execute() o pomiar czasu (System.nanoTime() na starcie
         * i koncu metody, NIEZALEZNIE od sukcesu/porazki - policz w finally) i
         * wypisywanie "Transakcja trwala Xms" po KAZDYM wywolaniu execute(). Wywolaj
         * 5 operacji o roznej zlozonosci i porownaj zmierzone czasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildUnitOfWorkFactoryPerDatabase {
        /*
         * 🧪 Zadanie 26:
         * Napisz klase UnitOfWorkFactory z metoda statyczna UnitOfWork forDatabase(String
         * dbName) tworzaca UnitOfWork na url "jdbc:h2:mem:" + dbName +
         * ";DB_CLOSE_DELAY=-1" oraz od razu wywolujaca setUpSchema na nowo otwartym
         * polaczeniu. Uzyj tej fabryki do stworzenia 3 niezaleznych "instancji"
         * aplikacji (rozne bazy) w jednym main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiStepBusinessProcessInsideSingleUnitOfWork {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj metode processReturnAndRestock(unitOfWork, orderId,
         * productId, quantity) - w JEDNYM wywolaniu unitOfWork.execute(): usuwa
         * zamowienie z orders, PRZYWRACA stan magazynowy (increaseStock), wstawia
         * wpis do order_log o zwrocie. Zademonstruj na zamowieniu zlozonym wczesniej
         * przez placeOrder - stan magazynowy powinien wrocic do wartosci
         * poczatkowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareLegacyVsUnitOfWorkUnderFailureInjection {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj scenariusz "wstrzykiwania bledu" (np. metoda pomocnicza rzucajaca
         * wyjatek po N-tym wywolaniu SQL, poprzez licznik statyczny) i uzyj go w
         * OBIE wersjach Service (z UnitOfWork i reczna, z Zadania 17) dla identycznej
         * sekwencji operacji. Porownaj, czy OBIE wersje poprawnie wycofuja zmiany po
         * wstrzykniętym bledzie - pokaz, ze abstrakcja UnitOfWork nie zmienia
         * SEMANTYKI transakcji, tylko ja ukrywa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_UnitOfWorkWithNestedNonTransactionalSideEffect {
        /*
         * 🧪 Zadanie 29:
         * Zademonstruj "pulapke": lambda przekazana do execute() wysyla (symulowane,
         * System.out.println) powiadomienie e-mail PRZED commitem, a transakcja
         * PONIZEJ w tej samej lambda kończy się rollbackiem. Pokaz, ze powiadomienie
         * "wyslalo się" mimo ze transakcja zostala wycofana (efekt uboczny NIE jest
         * transakcyjny) - i zaproponuj w komentarzu poprawke (wyslac powiadomienie
         * PO udanym commit, poza lambda execute()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullUnitOfWorkFrameworkWithRetryLoggingAndMetrics {
        /*
         * 🧪 Zadanie 30:
         * Polacz wszystkie techniki z lekcji w jeden mini "framework": UnitOfWork +
         * executeAndLog (audit przetrwaly rollback, Zadanie 22) + executeWithRetry
         * (Zadanie 23) + pomiar czasu (Zadanie 25). Przetestuj na 3 scenariuszach:
         * udane zamowienie, nieudane (bez retry, blad biznesowy), zamowienie ktore
         * udaje sie przy 2. probie (zasymulowane "doladowaniem" stanu miedzy
         * probami). Wypisz pelny log wykonania dla wszystkich trzech przypadkow.
         */
        public static void main(String[] args) { }
    }
}
