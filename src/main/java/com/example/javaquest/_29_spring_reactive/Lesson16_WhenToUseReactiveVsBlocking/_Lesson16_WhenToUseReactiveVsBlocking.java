package com.example.javaquest._29_spring_reactive.Lesson16_WhenToUseReactiveVsBlocking;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson16_WhenToUseReactiveVsBlocking {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 16: Kiedy reaktywnosc, a kiedy blokujacy styl - decyzja architektoniczna ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - PODSUMOWANIE architektoniczne rozdzialu
         * ============================================================
         * PO 15 lekcjach mechaniki reaktywnej (Reactor, WebFlux,
         * R2DBC, Security, testowanie) NALEZY zadac PRAKTYCZNE
         * pytanie: "CZY MOJ projekt POWINIEN byc reaktywny?".
         * Powiazanie Z `_17_architecture` - TO jest DECYZJA
         * architektoniczna, NIE "zawsze lepszy wybor".
         */
        System.out.println("Po 15 lekcjach mechaniki: KIEDY faktycznie warto uzyc WebFlux/Reactor, A KIEDY zostac przy Spring MVC?");

        explainWhenReactiveMakesSense();
        explainWhenBlockingIsFine();
        demonstrateDecisionMatrix();
        explainTheAllOrNothingProblem();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU
         * ============================================================
         * - Reaktywnosc POMAGA, GDY: (1) WYSOKA liczba jednoczesnych
         *   polaczen I/O-bound (streaming, WebSocket, proxy/gateway),
         *   (2) CALY stos (kontroler+serwis+baza+klienci zewnetrzni)
         *   MOZE byc reaktywny END-TO-END.
         * - Blokujacy styl (Spring MVC, `_22_spring_web`) WYSTARCZY,
         *   GDY: (1) UMIARKOWANY ruch, (2) zespol NIE ZNA dobrze
         *   reaktywnosci (KRZYWA uczenia SIE, debug trudniejszy -
         *   stack trace NIE POKAZUJE "skad przyszlo" wywolanie),
         *   (3) WATKI wirtualne (`_28_java_evolution/Lesson19`) DAJA
         *   PODOBNA skalowalnosc PRZY BLOKUJACYM stylu pisania kodu.
         * - "All-or-nothing": MIESZANIE blokujacego kodu Z reaktywnym
         *   BEZ Schedulera (Lesson08) NISZCZY korzysci - JEDNO
         *   blokujace wywolanie NA event-loop watku BLOKUJE WSZYSTKIE
         *   inne zadania.
         * - `_20`-`_24` (Spring Core/Boot/Web/JPA/Security) + `_28/
         *   Lesson19` (watki wirtualne) TO CZESTO WYSTARCZAJACY,
         *   PROSTSZY wybor DLA WIEKSZOSCI projektow W 2026.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 I ROZDZIALU (przed kapsztonem, Lesson17) ===");
    }

    private static void explainWhenReactiveMakesSense() {
        System.out.println("\n--- Kiedy reaktywnosc SIE OPLACA ---");
        System.out.println("1. WYSOKA liczba jednoczesnych, DLUGOTRWALYCH polaczen I/O (streaming wideo, WebSocket czat, Server-Sent Events).");
        System.out.println("2. API Gateway/proxy AGREGUJACY WIELE wywolan downstream ROWNOLEGLE (powiazanie Z `_31_spring_cloud_microservices`).");
        System.out.println("3. CALY stos MOZE byc reaktywny END-TO-END (R2DBC zamiast JDBC, WebClient zamiast blokujacych klientow) - MIESZANIE traci zalety.");
        System.out.println("4. Zasoby SA OGRANICZONE (kontener Z MALA iloscia pamieci/CPU) - MNIEJ watkow platformowych = MNIEJSZE zuzycie pamieci.");
    }

    private static void explainWhenBlockingIsFine() {
        System.out.println("\n--- Kiedy blokujacy styl (Spring MVC) WYSTARCZY ---");
        System.out.println("1. UMIARKOWANY ruch - Tomcat Z 200 watkami OBSLUZY tysiace zadan/s DLA typowego CRUD API.");
        System.out.println("2. Zespol NIE MA doswiadczenia W reaktywnosci - koszt SZKOLENIA + trudniejszy DEBUG (stack trace 'gubi' kontekst wywolania MIEDZY operatorami) MOZE przewyzszyc zysk.");
        System.out.println("3. Java 21+ MA watki wirtualne (`_28_java_evolution/Lesson19`) - DAJA WIELE korzysci skalowania BEZ zmiany stylu pisania kodu (nadal SYNCHRONICZNY, LATWIEJSZY DO debugowania).");
        System.out.println("4. Wiekszosc bibliotek/frameworkow W ekosystemie JEST blokujaca (JDBC-based ORM, wiele klientow SDK) - reaktywnosc WYMAGA REAKTYWNYCH odpowiednikow WSZEDZIE.");
    }

    private static void demonstrateDecisionMatrix() {
        System.out.println("\n--- Macierz decyzyjna ---");
        Map<String, String> macierz = new LinkedHashMap<>();
        macierz.put("Prosty CRUD REST API, umiarkowany ruch", "Spring MVC (+ watki wirtualne, jesli potrzeba wiecej skali) - PROSTSZY kod, LATWIEJSZY debug.");
        macierz.put("API Gateway agregujacy WIELE mikroserwisow", "Spring WebFlux - NATURALNY use case DLA reaktywnego agregowania rownoleglych wywolan.");
        macierz.put("Streaming danych (wideo/czat/live dashboard)", "Spring WebFlux - Server-Sent Events/WebSocket TO WLASCIWY use case DLA Flux.");
        macierz.put("Aplikacja Z bardzo WYSOKA liczba polaczen (setki tysiecy)", "Spring WebFlux END-TO-END (R2DBC+WebClient) - JEDYNY sposob NA taka skale bez ogromnych zasobow.");
        macierz.put("Zespol BEZ doswiadczenia reaktywnego, termin BLISKI", "Spring MVC - RYZYKO bledow (blokowanie event-loop) przewyzsza korzysci.");

        for (Map.Entry<String, String> wpis : macierz.entrySet()) {
            System.out.println("Scenariusz: " + wpis.getKey());
            System.out.println("  -> Rekomendacja: " + wpis.getValue());
        }

        assertThat(macierz).hasSize(5);
    }

    private static void explainTheAllOrNothingProblem() {
        System.out.println("\n--- Problem 'wszystko albo nic' ---");
        System.out.println("Reaktywnosc DAJE PELNE korzysci TYLKO, gdy CALY lancuch wywolan (kontroler -> serwis -> baza -> klienci zewnetrzni) jest NIEBLOKUJACY.");
        System.out.println("1 BLOKUJACE wywolanie (np. stary sterownik JDBC) BEZ `Schedulers.boundedElastic()` (Lesson08) ZABLOKUJE watek event-loop DLA WSZYSTKICH innych zadan NA TYM watku - GORZEJ NIZ Spring MVC (TAM kazde zadanie MA WLASNY watek, blokada 1 zadania NIE WPLYWA na INNE).");
        System.out.println("STAD zasada: NIE 'dodawaj troche reaktywnosci' DO istniejacego blokujacego projektu - ALBO PELNA migracja END-TO-END, ALBO zostan przy sprawdzonym, blokujacym stylu.");
    }
}
