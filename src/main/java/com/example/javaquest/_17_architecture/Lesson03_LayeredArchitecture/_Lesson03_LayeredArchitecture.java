package com.example.javaquest._17_architecture.Lesson03_LayeredArchitecture;

import java.util.HashMap;
import java.util.Map;

public class _Lesson03_LayeredArchitecture {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: ARCHITEKTURA WARSTWOWA (LAYERED ARCHITECTURE) ===");

        /*
         * ============================================================
         * 📦 RECAP: `_10_dao/Lesson02_LayeredArchitecture`
         * ============================================================
         * - Tamta lekcja pokazala KONKRETNY, dzialajacy przyklad warstw
         *   Controller -> Service -> DAO -> JDBC -> Database na realnej
         *   bazie H2, dla 1 operacji (rejestracja uzytkownika).
         * - Ta lekcja generalizuje ten sam pomysl NA CALA APLIKACJE,
         *   niezaleznie od technologii: architektura warstwowa dotyczy
         *   RowNIEZ aplikacji CLI, wsadowych (batch), desktopowych - nie
         *   TYLKO aplikacji webowych z baza danych.
         */
        System.out.println("Architektura warstwowa dziala TAK SAMO w apce webowej, CLI, wsadowej czy desktopowej.");

        /*
         * ============================================================
         * 🔹 WARSTWY (LAYERS) A WARSTWY FIZYCZNE (TIERS) - TO NIE TO SAMO
         * ============================================================
         * - WARSTWA (layer) to LOGICZNY podzial kodu wg odpowiedzialnosci -
         *   moze istniec CALA w 1 procesie, na 1 maszynie (tak jak w
         *   demach tego kursu).
         * - TIER (warstwa FIZYCZNA/wdrozeniowa) to podzial wg tego, GDZIE
         *   dany kod FIZYCZNIE dziala (np. "3-tier": przegladarka na
         *   komputerze klienta, serwer aplikacji na innej maszynie, baza
         *   danych na jeszcze innej).
         * - Mozna miec WIELE warstw logicznych w 1 tierze (tak jak w tym
         *   kursie - Controller/Service/DAO dzialaja w 1 JVM) - i
         *   odwrotnie, System z 1 warstwa logiczna moze byc rozbity na
         *   wiele tierow fizycznych. To 2 NIEZALEZNE osie podzialu.
         */
        System.out.println("\nWarstwy (layers) = logiczny podzial kodu. Tiers = fizyczny podzial wdrozenia. To 2 ROZNE osie.");

        /*
         * ============================================================
         * 🔍 GLOWNA ZASADA: WARSTWA N ROZMAWIA TYLKO Z WARSTWA N-1
         * ============================================================
         * - "Strict layering" (scisle warstwowanie): warstwa WYZSZA
         *   (np. Presentation) MOZE wolac TYLKO warstwe bezposrednio pod
         *   soba (Business) - NIGDY nie omija jej, zeby dotrzec do
         *   warstwy jeszcze nizej (Data).
         * - Ta sama zasada, co "Prawo Demeter" (`_16_clean_code/Lesson12`),
         *   ale zastosowana do CALYCH WARSTW zamiast pojedynczych
         *   obiektow: "rozmawiaj TYLKO z bezposrednim sasiadem".
         */
        demonstrateStrictLayeringForLoanApproval();

        /*
         * ============================================================
         * 🔹 SCISLE (STRICT) VS LUZNE (RELAXED/OPEN) WARSTWOWANIE
         * ============================================================
         * - Scisle warstwowanie jest BEZPIECZNIEJSZE (latwiej przewidziec
         *   skutki zmiany), ale czasem WYMUSZA "pass-through" kod - warstwa
         *   posrednia, ktora TYLKO przekazuje wywolanie dalej, nie dodajac
         *   wartosci.
         * - Luzne (open) warstwowanie POZWALA warstwie wyzszej ominac
         *   warstwe posrednia w WYBRANYCH przypadkach (np. odczyt
         *   "slownikow" - danych referencyjnych bez logiki biznesowej) -
         *   kompromis miedzy czystoscia a pragmatyzmem.
         * - W PRAKTYCE: wiekszosc realnych systemow stosuje scisle
         *   warstwowanie dla operacji ZMIENIAJACYCH stan (write) i czasem
         *   luzne dla PROSTYCH odczytow (read) - swiadoma decyzja
         *   architektoniczna, warta udokumentowania ADR-em (Lesson02).
         */
        System.out.println("\nScisle warstwowanie = bezpieczniejsze, ale czasem 'pass-through'. Luzne = pragmatyczne, ale rusza granice.");

        /*
         * ============================================================
         * 🔍 KORZYSC: PODMIANA WARSTWY DANYCH BEZ DOTYKANIA LOGIKI BIZNESOWEJ
         * ============================================================
         * - Dokladnie ta sama korzysc co w Lesson01 (izolacja zmiany), ale
         *   pokazana na poziomie CALEJ warstwy, nie 1 klasy: podmieniamy
         *   CALA implementacje przechowywania danych (in-memory -> "legacy
         *   plikowa") - warstwa Business (LoanApprovalService) NIE WIE, ze
         *   cokolwiek sie zmienilo.
         */
        demonstrateSwappingDataLayerWithoutTouchingBusinessLayer();

        /*
         * ============================================================
         * 🔹 ANTY-WZORZEC: "PRZECIEKAJACE" WARSTWY (LAYER LEAKING)
         * ============================================================
         * - Naruszenie architektury warstwowej objawia sie na 2 sposoby:
         *   (1) logika BIZNESOWA "przecieka" DO GORY, do warstwy
         *   prezentacji (np. kontroler sam liczy zdolnosc kredytowa) -
         *   (2) SZCZEGOLY warstwy DANYCH "przeciekaja" W GORE, do logiki
         *   biznesowej (np. Service otrzymuje/zwraca typy specyficzne dla
         *   bazy danych, jak `java.sql.ResultSet`).
         * - Oba kierunki przeciekania niszcza GLOWNA korzysc architektury
         *   warstwowej - izolacje zmiany.
         */
        demonstrateLayerLeakingAntiPattern();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Architektura warstwowa generalizuje sie POZA aplikacje web+DB
         *   (`_10_dao/Lesson02`) - dotyczy KAZDEGO typu aplikacji.
         * - Warstwy (logiczny podzial) != tiers (fizyczny podzial
         *   wdrozenia) - 2 niezalezne osie.
         * - Glowna zasada: warstwa N rozmawia TYLKO z warstwa N-1 (strict
         *   layering) - luzne warstwowanie to swiadomy kompromis.
         * - Korzysc: mozna podmienic CALA warstwe (np. dane) bez dotykania
         *   warstw powyzej.
         * - Anty-wzorzec: przeciekajace warstwy (logika w gore, szczegoly
         *   techniczne w dol) niszcza te korzysc.
         * - W kolejnej lekcji (Lesson04): dokladna anatomia trojki
         *   Controller/Service/Repository - odpowiedzialnosc KAZDEJ z 3
         *   klasycznych warstw i typowe bledy przy ich implementacji.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void demonstrateStrictLayeringForLoanApproval() {
        System.out.println("\n=== SCISLE WARSTWOWANIE: SYSTEM OCENY WNIOSKOW KREDYTOWYCH ===");

        LoanRepository repository = new InMemoryLoanRepository();
        LoanApprovalService service = new LoanApprovalService(repository);

        String response = handleLoanApplicationRequest(service, "Ala Kowalska", 50000, 6000);
        System.out.println("Odpowiedz do 'klienta': " + response);
    }

    /** Symulacja warstwy PREZENTACJI - zna TYLKO Service, nie wie NIC o LoanRepository. */
    private static String handleLoanApplicationRequest(LoanApprovalService service, String applicant, double amount, double monthlyIncome) {
        System.out.println("[Prezentacja] Odebrano wniosek: " + applicant);
        LoanDecision decision = service.evaluate(applicant, amount, monthlyIncome);
        return decision.approved() ? "WNIOSEK ZAAKCEPTOWANY: " + decision.reason() : "WNIOSEK ODRZUCONY: " + decision.reason();
    }

    record LoanDecision(boolean approved, String reason) {
    }

    /** Warstwa BIZNESOWA - zna reguly oceny wniosku, NIE zna szczegolow przechowywania danych. */
    static class LoanApprovalService {
        private final LoanRepository repository;

        LoanApprovalService(LoanRepository repository) {
            this.repository = repository;
        }

        LoanDecision evaluate(String applicant, double amount, double monthlyIncome) {
            System.out.println("[Biznes] Ocena zdolnosci kredytowej dla: " + applicant);
            double maxAllowedAmount = monthlyIncome * 10; // uproszczona regula biznesowa
            if (amount > maxAllowedAmount) {
                return new LoanDecision(false, "kwota przekracza 10x miesieczny dochod");
            }
            repository.save(applicant, amount);
            return new LoanDecision(true, "zdolnosc kredytowa potwierdzona");
        }
    }

    /** Abstrakcja warstwy DANYCH - Service zna TYLKO ten interfejs. */
    interface LoanRepository {
        void save(String applicant, double amount);
    }

    static class InMemoryLoanRepository implements LoanRepository {
        private final Map<String, Double> approvedLoans = new HashMap<>();

        @Override
        public void save(String applicant, double amount) {
            System.out.println("[Dane] Zapis wniosku (in-memory): " + applicant + " -> " + amount);
            approvedLoans.put(applicant, amount);
        }
    }

    private static void demonstrateSwappingDataLayerWithoutTouchingBusinessLayer() {
        System.out.println("\n=== PODMIANA CALEJ WARSTWY DANYCH BEZ ZMIANY LOGIKI BIZNESOWEJ ===");

        LoanRepository legacyRepository = new LegacyFileBasedLoanRepository();
        LoanApprovalService serviceWithLegacyStorage = new LoanApprovalService(legacyRepository);

        System.out.println(handleLoanApplicationRequest(serviceWithLegacyStorage, "Jan Nowak", 30000, 5000));
        System.out.println("-> LoanApprovalService to DOKLADNIE ta sama klasa co wyzej - nie wie, ze");
        System.out.println("   'pod spodem' zamienilismy in-memory na (symulowane) pliki.");
    }

    /** Alternatywna implementacja warstwy DANYCH - symuluje 'starszy' system przechowywania. */
    static class LegacyFileBasedLoanRepository implements LoanRepository {
        @Override
        public void save(String applicant, double amount) {
            System.out.println("[Dane - LEGACY] Symulowany zapis do pliku .dat: " + applicant + " -> " + amount);
        }
    }

    private static void demonstrateLayerLeakingAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: PRZECIEKAJACE WARSTWY ===");

        System.out.println("[ZLA] Prezentacja SAMA liczy zdolnosc kredytowa (logika biznesowa 'ucieka w gore'):");
        double amount = 50000;
        double monthlyIncome = 4000;
        boolean approvedInPresentation = amount <= monthlyIncome * 10; // logika biznesowa NIE POWINNA tu byc!
        System.out.println("  Kontroler SAM zdecydowal: zaakceptowano=" + approvedInPresentation);
        System.out.println("  -> Zmiana reguly oceny wymagalaby teraz szukania jej W KAZDYM miejscu prezentacji, ktore ja powielilo.");

        System.out.println("[ZLA] Warstwa biznesowa zwraca typ specyficzny dla bazy danych (szczegol 'przecieka w dol->gore'):");
        System.out.println("  np. metoda Service zwracajaca java.sql.ResultSet zamiast wlasnego DTO/rekordu -");
        System.out.println("  wymusza to na WARSTWIE PREZENTACJI znajomosc JDBC, ktorej NIE POWINNA znac.");
    }
}
