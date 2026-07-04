package com.example.javaquest._06_networking.Lesson06_ServerSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class _Lesson06_ServerSocket {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 java.net.ServerSocket - GNIAZDO NASŁUCHUJĄCE (SERWER TCP)
         * ============================================================
         * O ile Socket (Lesson03-05) to KLIENT - obiekt reprezentujący
         * jedno POŁĄCZENIE WYCHODZĄCE do jakiegoś serwera, o tyle
         * ServerSocket to SERWER - obiekt, który NASŁUCHUJE na danym
         * porcie i CZEKA na przychodzące połączenia od klientów.
         *
         * Typowy cykl życia serwera TCP:
         * 1) new ServerSocket(port)  -> "zajmij" port, zacznij nasłuchiwać
         * 2) serverSocket.accept()  -> ZABLOKUJ się i CZEKAJ, aż jakiś
         *    klient się połączy. Gdy to nastąpi, zwróć NOWY obiekt
         *    Socket - reprezentujący POŁĄCZENIE Z TYM KONKRETNYM
         *    klientem (to inny obiekt niż serverSocket!).
         * 3) czytaj/pisz na zwróconym Socket, tak jak w Lesson03-05
         * 4) zamknij Socket klienta (połączenie), gdy skończona obsługa
         * 5) (opcjonalnie) wróć do accept() po kolejnego klienta...
         *
         * 🔍 KLUCZOWE: accept() jest BLOKUJĄCE - wątek, który je wywoła,
         * "zamraża się" (nic więcej nie robi), dopóki nie połączy się
         * jakiś klient. To dlatego prosty serwer obsługujący klientów
         * "po kolei" (jak w tej lekcji) może obsłużyć tylko JEDNEGO
         * klienta na raz - dopóki nie skończy z pierwszym, nie wróci do
         * accept() po drugiego. Rozwiążemy ten problem w Lesson07
         * (wątek na klienta).
         */

        System.out.println("=== java.net.ServerSocket - serwer TCP (1 klient) ===");

        /*
         * ============================================================
         * 🔹 KROK 1: uruchomienie serwera w tle + synchronizacja startu
         * ============================================================
         * ServerSocket(0) -> port 0 = "system operacyjny, wybierz wolny
         * port" (jak w Lesson03) - zero ryzyka konfliktu portów.
         *
         * Serwer uruchamiamy w osobnym wątku (main będzie potem grał rolę
         * "klienta"). Żeby DETERMINISTYCZNIE wiedzieć, kiedy serwer jest
         * już gotowy na accept() (a nie zgadywać przez Thread.sleep!),
         * używamy CountDownLatch - licznika odliczającego w dół, który
         * "otwiera bramkę" dla czekających wątków, gdy osiągnie zero.
         * To narzędzie synchronizacji poznane w _05_multithreading
         * (Lesson20_Synchronizers).
         */

        CountDownLatch serverGotowy = new CountDownLatch(1);
        ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();

        Thread serverThread = new Thread(() -> {
            System.out.println("[serwer] nasluchuje na porcie " + port);
            serverGotowy.countDown(); // sygnal: "juz mozna sie laczyc"

            try (ServerSocket ss = serverSocket;
                 Socket klient = ss.accept()) { // <-- BLOKUJE do momentu polaczenia klienta

                System.out.println("[serwer] klient polaczony: " + klient.getRemoteSocketAddress());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(klient.getInputStream(), StandardCharsets.UTF_8));
                OutputStream out = klient.getOutputStream();

                String zadanie = in.readLine();
                System.out.println("[serwer] odebralem: " + zadanie);

                String odpowiedz = "SERWER: witaj, otrzymalem '" + zadanie + "'\n";
                out.write(odpowiedz.getBytes(StandardCharsets.UTF_8));
                out.flush();

                System.out.println("[serwer] odpowiedz wyslana, zamykam polaczenie z klientem");

            } catch (IOException e) {
                System.out.println("[serwer] blad: " + e.getMessage());
            }
        }, "serwer-tcp");

        serverThread.setDaemon(true); // watek demon - nie zablokuje zakonczenia JVM
        serverThread.start();

        /*
         * ============================================================
         * 🔍 KROK 2: klient laczy sie DOPIERO gdy serwer jest gotowy
         * ============================================================
         * await() blokuje az CountDownLatch osiagnie 0 (albo minie limit
         * czasu) - to precyzyjna, deterministyczna synchronizacja, w
         * przeciwstawieniu do "zgadywania" odpowiedniego czasu przez
         * Thread.sleep(...).
         */

        boolean gotowy = serverGotowy.await(5, java.util.concurrent.TimeUnit.SECONDS);
        if (!gotowy) {
            System.out.println("[klient] serwer nie wystartowal w oczekiwanym czasie - przerywam.");
            return;
        }

        try (Socket socket = new Socket("localhost", port)) {
            socket.setSoTimeout(2000);

            System.out.println("\n[klient] polaczono z serwerem na porcie " + port);

            OutputStream out = socket.getOutputStream();
            String wiadomosc = "Witaj, serwerze!";
            out.write((wiadomosc + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            System.out.println("[klient] wyslano: " + wiadomosc);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String odpowiedz = in.readLine();
            System.out.println("[klient] otrzymano: " + odpowiedz);

        } catch (IOException e) {
            System.out.println("[klient] blad polaczenia: " + e.getMessage());
        }

        // Czekamy na zakonczenie watku serwera z limitem czasu - main nigdy nie
        // czeka w nieskonczonosc.
        serverThread.join(2000);

        System.out.println("\n=== KONIEC DEMONSTRACJI ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ServerSocket = gniazdo NASŁUCHUJĄCE na danym porcie (serwer).
         * - accept() BLOKUJE wątek do momentu połączenia klienta, a
         *   następnie zwraca NOWY Socket - reprezentujący to konkretne
         *   połączenie (odrębny od samego ServerSocket).
         * - Każdy zaakceptowany klient dostaje WŁASNY obiekt Socket -
         *   dzięki temu można obsłużyć wielu klientów, każdego przez
         *   jego "prywatny" strumień danych.
         * - Prosty serwer wywołujący accept() TYLKO RAZ (jak tutaj)
         *   obsłuży dokładnie jednego klienta, a potem kończy pracę -
         *   to nie skaluje się do wielu klientów naraz (patrz Lesson07).
         * - CountDownLatch to deterministyczny sposób synchronizacji
         *   "poczekaj, aż coś się wydarzy" - lepszy niż zgadywanie czasu
         *   przez Thread.sleep(...).
         * - ServerSocket(0) -> port 0 = system sam wybiera wolny port
         *   (przydatne w demo/testach).
         *
         * W Lesson07 rozszerzymy ten wzorzec o OBSŁUGĘ WIELU klientów
         * jednocześnie - jeden wątek na każde połączenie.
         */
    }
}
