package com.example.javaquest._17_architecture.Lesson13_TransactionBoundaries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class _Lesson13_TransactionBoundaries {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: GRANICE TRANSAKCJI ===");

        /*
         * ============================================================
         * 📦 MECHANIKE ZNASZ - TU: GDZIE TA GRANICA POWINNA ZYC ARCHITEKTONICZNIE
         * ============================================================
         * - Prawdziwa mechanika transakcji JDBC (`setAutoCommit`, `commit`,
         *   `rollback`) i wzorzec Unit of Work poznales dokladnie w
         *   `_10_dao/Lesson17_TransactionsInServiceLayer` i
         *   `Lesson19_UnitOfWork`.
         * - Ta lekcja NIE powtarza tej mechaniki (i celowo NIE uzywa
         *   prawdziwej bazy danych - symulujemy "transakcje" prosta
         *   lista operacji) - pyta o pytanie ARCHITEKTONICZNE: GDZIE (w
         *   ktorej warstwie/porcie) granica transakcji powinna zyc, i
         *   DLACZEGO to jest decyzja architektoniczna (Lesson01), nie
         *   tylko techniczny detal JDBC.
         */
        System.out.println("Mechanike transakcji znasz z _10_dao/Lesson17+19 - tu: GDZIE architektonicznie zyje ta granica.");

        /*
         * ============================================================
         * 🔹 ZASADA: 1 PRZYPADEK UZYCIA = 1 TRANSAKCJA
         * ============================================================
         * - Granica transakcji powinna DOKLADNIE pokrywac sie z granica
         *   1 przypadku uzycia (Service/UseCase, Lesson04/Lesson11) - NIE
         *   WIECEJ (kilka niepowiazanych operacji naraz) i NIE MNIEJ
         *   (rozbita na kilka osobnych mini-transakcji, jak w anty-wzorcu
         *   ponizej).
         */
        demonstrateOneUseCaseOneTransaction();

        /*
         * ============================================================
         * 🔍 ANTY-WZORZEC 1: GRANICA W REPOSITORY (ZA NISKO)
         * ============================================================
         * - Jesli KAZDA metoda Repository "commituje" SAMODZIELNIE (tak
         *   jak surowy JDBC z domyslnym autocommit), to Service NIE MA
         *   SPOSOBU zagwarantowac, ze WIELE wywolan Repository (np.
         *   "zdejmij ze stanu" + "zapisz zamowienie") powiedzie sie
         *   RAZEM albo wcale.
         * - To DOKLADNIE problem z `_10_dao/Lesson17` (niespojne dane po
         *   czesciowej awarii) - tu nazwany architektonicznie: granica
         *   transakcji byla ZBYT NISKO (w kregu zewnetrznym/adapterze
         *   zamiast w kregu przypadkow uzycia, Lesson10).
         */
        demonstrateTransactionBoundaryTooLowAntiPattern();

        /*
         * ============================================================
         * 🔹 ANTY-WZORZEC 2: GRANICA W KONTROLERZE (ZA WYSOKO)
         * ============================================================
         * - Odwrotny blad: kontroler (adapter driving, Lesson11) SAM
         *   otwiera transakcje i woła NIEPOWIAZANE serwisy w jej ramach -
         *   transakcja staje sie ZBYT SZEROKA (obejmuje wiecej niz 1
         *   spojny przypadek uzycia) I kontroler zaczyna "wiedziec" o
         *   szczegole technicznym (transakcji bazodanowej) - naruszenie
         *   Dependency Rule (Lesson10): adapter zewnetrzny NIE powinien
         *   znac tego typu detalu infrastruktury.
         */
        demonstrateTransactionBoundaryTooHighAntiPattern();

        /*
         * ============================================================
         * 🔍 PULAPKA: WOLNE/ZEWNETRZNE WYWOLANIA WEWNATRZ TRANSAKCJI
         * ============================================================
         * - Jesli granica transakcji PRZYPADKIEM obejmuje WOLNE wywolanie
         *   zewnetrzne (np. wywolanie zewnetrznego API platnosci), baza
         *   danych TRZYMA BLOKADY przez caly czas trwania tego wolnego
         *   wywolania - realny problem WYDAJNOSCIOWY i architektoniczny
         *   (system-krytyczna baza danych "czeka" na system zewnetrzny,
         *   nad ktorym nie masz kontroli).
         * - Rozwiazanie: wywolania ZEWNETRZNE/wolne powinny zyc POZA
         *   granica transakcji bazodanowej - spojnosc miedzy systemami
         *   (baza + zewnetrzne API) wymaga innych technik (np. wzorca
         *   "outbox" albo zdarzen domenowych - zobaczysz zapowiedz w
         *   Lesson18: EventDrivenCommunicationBetweenModules).
         */
        demonstrateSlowExternalCallInsideTransactionAntiPattern();

        /*
         * ============================================================
         * 🔹 GRANICA TRANSAKCJI W JEZYKU PORTOW/ADAPTEROW (LESSON11-12)
         * ============================================================
         * - W terminologii heksagonalnej: granica transakcji zyje NA
         *   WEJSCIU do portu driving (metoda Use Case) - port DRIVEN
         *   (repozytorium) UCZESTNICZY w transakcji, ale jej NIE
         *   ROZPOCZYNA ani NIE KONCZY samodzielnie.
         * - Adapter driving (kontroler) WOLA metode Use Case i NIE WIE
         *   NIC o tym, ze pod spodem dzieje sie cokolwiek "transakcyjnego" -
         *   to szczegol WEWNATRZ aplikacji, nie na jej granicy z UI/API.
         */
        System.out.println("\nW jezyku portow (Lesson11-12): granica na WEJSCIU do Use Case - Repository UCZESTNICZY, nie ROZPOCZYNA/KONCZY.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Mechanike transakcji znasz z `_10_dao` - tu: architektoniczna
         *   zasada "1 przypadek uzycia = 1 transakcja".
         * - Granica ZA NISKO (w Repository): brak gwarancji spojnosci
         *   miedzy wieloma operacjami.
         * - Granica ZA WYSOKO (w kontrolerze): zbyt szeroka transakcja +
         *   adapter "wie" o szczegole infrastruktury.
         * - Pulapka: wolne/zewnetrzne wywolania WEWNATRZ transakcji
         *   trzymaja blokady bazy zbyt dlugo.
         * - W jezyku portow: granica na wejsciu do Use Case, Repository
         *   uczestniczy, nie zarzadza.
         * - W kolejnej lekcji (Lesson14): architektura cache'owania - GDZIE
         *   (analogicznie do transakcji) powinien zyc cache jako kolejny
         *   "przekroj poprzeczny" architektury.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    /** Symulacja "transakcji" - lista operacji odlozonych do wspolnego 'commit' albo 'rollback', bez prawdziwej bazy danych. */
    static class SimulatedTransaction {
        private final List<Consumer<List<String>>> pendingOperations = new ArrayList<>();
        private boolean committed = false;

        void addOperation(Consumer<List<String>> operation) {
            pendingOperations.add(operation);
        }

        void commit(List<String> targetStorage) {
            pendingOperations.forEach(op -> op.accept(targetStorage));
            committed = true;
        }

        void rollback() {
            pendingOperations.clear();
            committed = false;
        }

        boolean isCommitted() {
            return committed;
        }
    }

    private static void demonstrateOneUseCaseOneTransaction() {
        System.out.println("\n=== 1 PRZYPADEK UZYCIA = 1 TRANSAKCJA ===");

        List<String> warehouseLog = new ArrayList<>();
        List<String> orderLog = new ArrayList<>();

        boolean success = placeOrderUseCase("ORD-1", 5, warehouseLog, orderLog);

        System.out.println("Wynik use case: " + (success ? "SUKCES" : "PORAZKA"));
        System.out.println("Log magazynu: " + warehouseLog);
        System.out.println("Log zamowien: " + orderLog);
        System.out.println("-> OBIE operacje (magazyn + zamowienie) zapadaja RAZEM, w 1 granicy transakcji Use Case.");
    }

    /** GRANICA TRANSAKCJI = CALA metoda Use Case, ani wiecej, ani mniej. */
    private static boolean placeOrderUseCase(String orderId, int quantity, List<String> warehouseLog, List<String> orderLog) {
        SimulatedTransaction transaction = new SimulatedTransaction();

        transaction.addOperation(log -> log.add("Zarezerwowano " + quantity + " szt. dla " + orderId));
        transaction.addOperation(log -> orderLog.add("Zapisano zamowienie " + orderId));

        transaction.commit(warehouseLog); // WSZYSTKO w 1 miejscu - poczatek i koniec granicy transakcji
        return transaction.isCommitted();
    }

    private static void demonstrateTransactionBoundaryTooLowAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: GRANICA ZA NISKO (KAZDA OPERACJA 'COMMITUJE' OSOBNO) ===");

        List<String> warehouseLog = new ArrayList<>();
        List<String> orderLog = new ArrayList<>();

        // KAZDA operacja "commituje" NATYCHMIAST, osobno - jesli 2. sie nie
        // uda, 1. JUZ zdarzyla sie nieodwracalnie:
        warehouseLog.add("Zarezerwowano 5 szt. dla ORD-2 (juz ZACOMMITOWANE, osobno)");
        boolean secondStepFails = true;
        if (secondStepFails) {
            System.out.println("Krok 2 (zapis zamowienia) ZAWIODL - ale krok 1 JUZ jest w 'bazie':");
            System.out.println("  Log magazynu: " + warehouseLog + " (NIESPOJNE - rezerwacja bez odpowiadajacego zamowienia!)");
            System.out.println("  Log zamowien: " + orderLog + " (pusty)");
        }
        System.out.println("-> Granica transakcji byla ZA NISKO (w Repository, nie w Use Case) - brak gwarancji 'wszystko albo nic'.");
    }

    private static void demonstrateTransactionBoundaryTooHighAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: GRANICA ZA WYSOKO (W KONTROLERZE, ZBYT SZEROKA) ===");

        System.out.println("[ZLA] Kontroler SAM otwiera 'transakcje' i woła 2 NIEPOWIAZANE serwisy w jej ramach:");
        System.out.println("  handleHttpRequest() { transaction.begin(); orderService.placeOrder(...); ");
        System.out.println("  unrelatedAnalyticsService.recordPageView(...); transaction.commit(); }");
        System.out.println("-> Kontroler TERAZ 'wie' o transakcjach (szczegol infrastruktury, Lesson10) I");
        System.out.println("   laczy 2 NIEPOWIAZANE przypadki uzycia w 1 zbyt szeroka transakcje.");
    }

    private static void demonstrateSlowExternalCallInsideTransactionAntiPattern() {
        System.out.println("\n=== PULAPKA: WOLNE ZEWNETRZNE WYWOLANIE WEWNATRZ GRANICY TRANSAKCJI ===");

        System.out.println("[ZLA] transaction.begin(); repository.reserveStock(...); ");
        System.out.println("      externalPaymentApi.charge(...); /* WOLNE! blokady bazy trzymane przez caly ten czas */");
        System.out.println("      repository.save(order); transaction.commit();");
        System.out.println("-> Baza 'czeka' na zewnetrzne API - ryzyko blokad/timeoutow niezalezne od Twojej bazy.");

        System.out.println("[DOBRA] Wywolanie zewnetrzne PRZED lub PO granicy transakcji bazodanowej,");
        System.out.println("        spojnosc miedzy systemami przez osobny mechanizm (np. zdarzenia, Lesson18).");
    }
}
