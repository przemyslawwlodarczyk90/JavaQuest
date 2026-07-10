package com.example.javaquest._17_architecture.Lesson19_WhenMicroservicesMakeSense;

public class _Lesson19_WhenMicroservicesMakeSense {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: KIEDY MIKROSERWISY MAJA SENS ===");

        /*
         * ============================================================
         * 📦 WSZYSTKO DOTAD DZIALO SIE W 1 PROCESIE - CO SIE ZMIENIA?
         * ============================================================
         * - Lesson01-18 uczyly organizacji kodu W RAMACH 1 wdrazalnej
         *   jednostki (modularny monolit, Lesson17) - moduly komunikowaly
         *   sie bezposrednio (Lesson17) albo przez zdarzenia w pamieci
         *   (Lesson18), ale ZAWSZE w tym samym procesie.
         * - Mikroserwis to KAZDY z tych modulow wydzielony do OSOBNEGO
         *   procesu/wdrozenia, komunikujacy sie przez SIEC (HTTP, kolejka
         *   wiadomosci) zamiast wywolania metody w pamieci.
         * - To NIE jest "nastepny, wyzszy poziom" architektury - to
         *   ZUPELNIE INNY zestaw kompromisow, ktory NALEZY swiadomie
         *   rozwazyc (Lesson01: architektura = decyzje kosztowne do
         *   odwrocenia).
         */
        System.out.println("Mikroserwis = modul (Lesson17) wydzielony do OSOBNEGO procesu, komunikujacy sie przez SIEC.");

        /*
         * ============================================================
         * 🔹 REALNY KOSZT: AWARIE CZESCIOWE, KTORYCH MONOLIT NIE ZNA
         * ============================================================
         * - Wywolanie metody W PAMIECI (Lesson17) MOZE sie nie udac
         *   TYLKO przez wyjatek w logice - albo sie wykona, albo nie.
         * - Wywolanie PRZEZ SIEC ma DODATKOWA kategorie awarii: TIMEOUT
         *   (serwis nie odpowiedzial NA CZAS - ale MOZE i tak przetworzyl
         *   zadanie!), utrata polaczenia W POLOWIE, czesciowa odpowiedz.
         *   Te scenariusze NIE ISTNIEJA przy zwyklym wywolaniu metody.
         */
        demonstrateNetworkFailureModesVsInProcessCall();

        /*
         * ============================================================
         * 🔍 REALNY KOSZT: BRAK TRANSAKCJI MIEDZY SERWISAMI
         * ============================================================
         * - W modularnym monolicie (Lesson13) transakcja bazodanowa
         *   MOGLA obejmowac operacje na kilku modulach naraz (JEDNA baza).
         * - Miedzy OSOBNYMI serwisami (osobne bazy danych!) TAKIEJ
         *   transakcji juz NIE MA - spojnosc wymaga wzorcow typu Saga
         *   (kompensacja, zapowiedz z Lesson13/18) - REALNA, DODATKOWA
         *   zlozonosc, ktorej modularny monolit unika.
         */
        System.out.println("\nBrak wspolnej transakcji miedzy serwisami (osobne bazy!) - wymaga Sagi/kompensacji (Lesson13/18) zamiast 1 commitu.");

        /*
         * ============================================================
         * 🔹 PRAWO CONWAYA (MELVIN CONWAY, 1967)
         * ============================================================
         * - "Organizacje projektuja systemy, ktore odzwierciedlaja ich
         *   WLASNA strukture komunikacji" - Melvin Conway, 1967.
         * - Mikroserwisy NATURALNIE pasuja do organizacji z WIELOMA,
         *   NIEZALEZNYMI zespolami, ktore potrzebuja wdrazac swoja czesc
         *   systemu NIEZALEZNIE od innych zespolow.
         * - 1 MALY zespol (np. 5 osob) dzielacy caly system MA MALO do
         *   zyskania na sztucznym rozbiciu na serwisy, ktore i tak MUSI
         *   rozwijac i koordynowac TEN SAM, maly zespol.
         */
        demonstrateConwaysLawImplication();

        /*
         * ============================================================
         * 🔍 KIEDY MIKROSERWISY RZECZYWISCIE MAJA SENS
         * ============================================================
         * - NIEZALEZNE SKALOWANIE: 1 czesc systemu ma DRASTYCZNIE inna
         *   charakterystyke obciazenia niz reszta (np. przetwarzanie
         *   obrazow vs prosty CRUD) - warto skalowac je NIEZALEZNIE.
         * - NIEZALEZNE WDROZENIA: rozne zespoly potrzebuja wdrazac swoja
         *   czesc W INNYM RYTMIE (1 zespol wdraza 10x dziennie, inny
         *   ostroznie raz na kwartal) bez blokowania sie nawzajem.
         * - RoZNORODNOSC TECHNOLOGICZNA: realna, uzasadniona potrzeba
         *   uzycia INNEGO stosu technologicznego dla 1 fragmentu (rzadkie,
         *   ale realne - np. biblioteki ML dostepne tylko w innym jezyku).
         * - SKALA ORGANIZACYJNA: dziesiatki zespolow, ktore inaczej
         *   blokowalyby sie wzajemnie w 1 repozytorium/wdrozeniu.
         */
        System.out.println(WHEN_MICROSERVICES_MAKE_SENSE);

        /*
         * ============================================================
         * 🔹 PRZEDWCZESNE ROZPROSZENIE = PRZEDWCZESNA OPTYMALIZACJA
         * ============================================================
         * - "Mikroserwisy od pierwszego dnia" dla malego zespolu/startupu
         *   to forma PRZEDWCZESNEJ OPTYMALIZACJI (YAGNI, `_16_clean_code/
         *   Lesson13`) na poziomie ARCHITEKTURY - placisz WSZYSTKIE koszty
         *   systemu rozproszonego (Sagi, monitoring wielu serwisow,
         *   opoznienia sieciowe), ZANIM masz SKALE albo problem
         *   ORGANIZACYJNY, ktory by to uzasadnial.
         */
        System.out.println("\n'Mikroserwisy od dnia 1' dla malego zespolu = przedwczesna optymalizacja (YAGNI) na poziomie architektury.");

        /*
         * ============================================================
         * 🔍 SCIEZKA MIGRACJI: MODULARNY MONOLIT JAKO DOMYSLNY PUNKT STARTU
         * ============================================================
         * - Zalecana strategia (i powod, dla ktorego Lesson17 poswiecono
         *   cala lekcje): ZACZNIJ od modularnego monolitu z WYRAZNYMI
         *   granicami modulow - WYDZIEL modul do osobnego mikroserwisu
         *   POZNIEJ, TYLKO gdy pojawi sie KONKRETNA, ZMIERZONA potrzeba
         *   (Lesson17: dobre granice modulow = latwy "trampoline").
         * - To jest zastosowanie zasady z Lesson01: zaczynaj od TANSZEJ
         *   do odwrocenia decyzji (monolit modularny mozna PODZIELIC
         *   pozniej) - odwrotny kierunek (POLACZENIE juz rozproszonych
         *   serwisow z powrotem) jest DUZO trudniejszy.
         */
        System.out.println("\nZaczynaj od modularnego monolitu (Lesson17) - wydzielaj serwisy PoZNIEJ, gdy pojawi sie KONKRETNA potrzeba.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Mikroserwis = modul wydzielony do osobnego procesu,
         *   komunikujacy sie przez siec - to INNY zestaw kompromisow, nie
         *   "wyzszy poziom" architektury.
         * - Realne koszty: awarie czesciowe (timeout != porazka), brak
         *   wspolnej transakcji (Saga zamiast commitu), narzut operacyjny.
         * - Prawo Conwaya: mikroserwisy pasuja do organizacji z wieloma
         *   niezaleznymi zespolami.
         * - Warto TYLKO przy: niezaleznym skalowaniu, niezaleznych
         *   wdrozeniach roznych zespolow, realnej roznorodnosci
         *   technologicznej, skali organizacyjnej.
         * - Przedwczesne rozproszenie = przedwczesna optymalizacja (YAGNI)
         *   na poziomie architektury.
         * - Domyslny punkt startu: modularny monolit (Lesson17) - migruj
         *   pozniej, gdy pojawi sie KONKRETNA potrzeba.
         * - W kolejnej, OSTATNIEJ lekcji rozdzialu (Lesson20): kapsztonowy
         *   projekt laczacy WSZYSTKIE 19 lekcji w 1 spojnej aplikacji.
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    private static final String WHEN_MICROSERVICES_MAKE_SENSE = """

            Mikroserwisy MAJA SENS, gdy (realnie, nie hipotetycznie):
              1) Niezalezne skalowanie - drastycznie rozna charakterystyka obciazenia.
              2) Niezalezne wdrozenia - rozne zespoly, rozny rytm wydan.
              3) Roznorodnosc technologiczna - realna, uzasadniona potrzeba innego stosu.
              4) Skala organizacyjna - dziesiatki zespolow blokujacych sie nawzajem.
            """;

    /** Symulacja WYWOLANIA W PAMIECI (jak modularny monolit, Lesson17) - proste, deterministyczne: albo sukces, albo wyjatek. */
    private static String callInProcessModule(boolean shouldSucceed) {
        if (!shouldSucceed) {
            throw new RuntimeException("Blad logiki - JEDYNY mozliwy tryb awarii wywolania w pamieci");
        }
        return "Wynik z modulu (natychmiast, bez sieci)";
    }

    /**
     * Symulacja wywolania PRZEZ SIEC (mikroserwis) - DODATKOWA kategoria awarii
     * (timeout), ktorej wywolanie w pamieci W OGoLE nie zna. Serwis mogl
     * przetworzyc zadanie, ale odpowiedz "zgubila sie" w sieci - NIE WIEMY.
     */
    private static String callRemoteMicroservice(boolean networkTimeout, boolean shouldSucceedIfReached) {
        if (networkTimeout) {
            // Serwis MoGL przetworzyc zadanie poprawnie - MY tego nie wiemy!
            // To jest kategoria bledu, ktorej wywolanie metody w pamieci W OGOLE nie zna.
            throw new RuntimeException("TIMEOUT SIECIOWY - stan nieznany: serwis MOGL wykonac operacje, MOGL tez nie");
        }
        if (!shouldSucceedIfReached) {
            throw new RuntimeException("Serwis ODPOWIEDZIAL i zglosil blad logiki");
        }
        return "Wynik z serwisu zdalnego (przez siec)";
    }

    private static void demonstrateNetworkFailureModesVsInProcessCall() {
        System.out.println("\n=== DODATKOWA KATEGORIA AWARII: TIMEOUT SIECIOWY ===");

        try {
            System.out.println(callInProcessModule(true));
        } catch (RuntimeException e) {
            System.out.println("Wywolanie w pamieci - blad: " + e.getMessage());
        }

        try {
            callInProcessModule(false);
        } catch (RuntimeException e) {
            System.out.println("Wywolanie w pamieci - JEDYNY mozliwy blad: " + e.getMessage());
        }

        try {
            callRemoteMicroservice(true, true);
        } catch (RuntimeException e) {
            System.out.println("Wywolanie przez siec - NOWA kategoria bledu: " + e.getMessage());
            System.out.println("-> Musisz teraz zaprojektowac strategie RETRY/idempotencji (Lesson18: Zadanie 23),");
            System.out.println("   ktorej wywolanie w pamieci NIGDY by nie potrzebowalo.");
        }
    }

    private static void demonstrateConwaysLawImplication() {
        System.out.println("\n=== PRAWO CONWAYA: STRUKTURA SYSTEMU ODZWIERCIEDLA STRUKTURE ZESPOLU ===");

        System.out.println("[Maly zespol, 1 modularny monolit] 5-osobowy zespol rozwija Katalog+Zamowienia+Platnosci");
        System.out.println("  RAZEM, w 1 repozytorium - koordynacja jest NATYCHMIASTOWA (ten sam zespol, ten sam kod).");

        System.out.println("[Duza organizacja, mikroserwisy] 3 NIEZALEZNE zespoly (po 8 osob), kazdy WLASCICIELEM");
        System.out.println("  1 serwisu - KAZDY wdraza NIEZALEZNIE, bez czekania na pozostale 2 zespoly.");

        System.out.println("-> Ten sam KOD moglby dzialac w OBU strukturach - to WYBOR ORGANIZACYJNY, nie tylko techniczny.");
    }
}
