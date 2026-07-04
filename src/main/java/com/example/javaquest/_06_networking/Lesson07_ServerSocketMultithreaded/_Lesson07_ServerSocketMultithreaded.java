package com.example.javaquest._06_networking.Lesson07_ServerSocketMultithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class _Lesson07_ServerSocketMultithreaded {

    /** Liczba symulowanych klientow - STAŁA, znana z góry (żadnej pętli bez końca). */
    private static final int LICZBA_KLIENTOW = 4;

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 SERWER WIELOWĄTKOWY - "WĄTEK NA KLIENTA" (thread-per-connection)
         * ============================================================
         * W Lesson06 serwer wywoływał accept() TYLKO RAZ - obsłużył
         * jednego klienta i skończył. To nie wystarcza w praktyce: prawdziwy
         * serwer (WWW, gra sieciowa, czat) musi obsłużyć WIELU klientów,
         * którzy łączą się w różnym czasie i chcą być obsłużeni RÓWNOCZEŚNIE
         * (jeden klient nie może czekać, aż serwer skończy z poprzednim).
         *
         * Klasyczne (najprostsze koncepcyjnie) rozwiązanie to wzorzec
         * "wątek na klienta" (thread-per-connection), znany już z
         * rozdziału _05_multithreading:
         *
         *   while (serwer dziala) {
         *       Socket klient = serverSocket.accept();  // blokuje do polaczenia
         *       new Thread(() -> obsluz(klient)).start(); // odpal obsluge w tle
         *       // <- accept() NATYCHMIAST wraca po kolejnego klienta,
         *       //    bo obsluga poprzedniego dzieje sie w INNYM watku
         *   }
         *
         * Dzięki temu główny wątek serwera prawie caly czas "wisi" na
         * accept() i natychmiast wraca do niego po odpaleniu obsługi
         * kolejnego klienta w osobnym wątku - klienci są obsługiwani
         * WSPÓŁBIEŻNIE, a nie jeden po drugim.
         *
         * ⚠️ WAŻNE (bezpieczeństwo demo): prawdziwy serwer miałby pętlę
         * `while (true) { accept(); ... }` działającą bez końca (dopóki
         * ktoś go nie zatrzyma). W tej lekcji, żeby demo ZAWSZE kończyło
         * się samo, serwer accept()uje DOKŁADNIE tyle razy, ile mamy
         * symulowanych klientów (licznik ograniczony z góry) - NIGDY
         * pętla nieskończona.
         */

        System.out.println("=== ServerSocket wielowatkowy - " + LICZBA_KLIENTOW + " klientow rownoczesnie ===");

        ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();
        System.out.println("[serwer] nasluchuje na porcie " + port);

        /*
         * ============================================================
         * 🔹 KROK 1: watek glowny serwera - ograniczona liczba accept()
         * ============================================================
         * Kazde polaczenie przyjete przez accept() natychmiast dostaje
         * WLASNY watek obslugi (thread-per-connection) - serwer nie czeka,
         * az skonczy obsluge jednego klienta, zeby przyjac kolejnego.
         */

        CountDownLatch wszyscyObsluzeni = new CountDownLatch(LICZBA_KLIENTOW);

        Thread serverThread = new Thread(() -> {
            try (ServerSocket ss = serverSocket) {
                for (int i = 1; i <= LICZBA_KLIENTOW; i++) {
                    Socket klient = ss.accept(); // blokuje do kolejnego polaczenia
                    System.out.println("[serwer] przyjeto polaczenie #" + i +
                            " od " + klient.getRemoteSocketAddress());

                    Thread obslugaKlienta = new Thread(() -> obsluzKlienta(klient, wszyscyObsluzeni),
                            "obsluga-klienta-" + i);
                    obslugaKlienta.setDaemon(true);
                    obslugaKlienta.start();
                }
                System.out.println("[serwer] przyjeto wszystkich " + LICZBA_KLIENTOW +
                        " klientow, koncze nasluchiwanie");
            } catch (IOException e) {
                System.out.println("[serwer] blad: " + e.getMessage());
            }
        }, "serwer-glowny");

        serverThread.setDaemon(true);
        serverThread.start();

        /*
         * ============================================================
         * 🔍 KROK 2: symulowani klienci - kazdy w osobnym watku
         * ============================================================
         * Uruchamiamy LICZBA_KLIENTOW niezaleznych "klientow" - kazdy
         * laczy sie z serwerem, wysyla krotka wiadomosc, czeka na
         * odpowiedz i sie rozlacza. Startujemy ich prawie rownoczesnie,
         * zeby pokazac, ze serwer NAPRAWDE obsluguje ich rownolegle
         * (a nie jeden po drugim).
         */

        Thread[] klienci = new Thread[LICZBA_KLIENTOW];
        for (int i = 1; i <= LICZBA_KLIENTOW; i++) {
            int numerKlienta = i;
            klienci[i - 1] = new Thread(() -> symulujKlienta(port, numerKlienta), "klient-" + numerKlienta);
        }
        for (Thread klient : klienci) {
            klient.start();
        }

        /*
         * ============================================================
         * 🔹 KROK 3: uporzadkowane zakonczenie - wszystko z limitem czasu
         * ============================================================
         * CountDownLatch.await(...) pozwala poczekac az WSZYSCY klienci
         * zostana obsluzeni, bez zgadywania czasu - ale zawsze z limitem,
         * na wypadek nieoczekiwanego bledu.
         */

        boolean wszyscySkonczyli = wszyscyObsluzeni.await(5, TimeUnit.SECONDS);
        System.out.println("\n[main] wszyscy klienci obsluzeni: " + wszyscySkonczyli);

        for (Thread klient : klienci) {
            klient.join(2000); // limit czasu - main nigdy nie czeka w nieskonczonosc
        }
        serverThread.join(2000);

        System.out.println("\n=== KONIEC DEMONSTRACJI ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wzorzec "watek na klienta" (thread-per-connection): po
         *   kazdym accept() od razu startujemy NOWY watek do obslugi
         *   tego klienta, a glowny watek serwera natychmiast wraca do
         *   accept() po kolejne polaczenie - klienci obslugiwani SA
         *   WSPOLBIEZNIE, a nie po kolei.
         * - To bezposrednie zastosowanie wielowatkowosci z rozdzialu
         *   _05_multithreading do zadania I/O-bound (kazdy klient
         *   "czeka" na siec - idealny przypadek, gdzie wiele watkow
         *   pomaga nawet na 1 rdzeniu procesora).
         * - W prawdziwym serwerze petla accept() jest NIESKONCZONA
         *   (dziala, dopoki serwer nie zostanie jawnie zatrzymany) - w
         *   tej lekcji, dla bezpieczenstwa demo, ograniczylismy liczbe
         *   accept() do stalej, znanej z gory liczby symulowanych klientow.
         * - Kazdy watek obslugi klienta jest niezalezny - blad jednego
         *   klienta (np. IOException) nie wplywa na obsluge pozostalych.
         * - CountDownLatch pozwala poczekac, az WSZYSCY klienci zostana
         *   obsluzeni, bez zgadywania czasu przez Thread.sleep(...).
         * - Uwaga praktyczna: nieograniczone tworzenie watku-na-klienta
         *   (bez zadnego limitu) moze wyczerpac zasoby przy bardzo duzej
         *   liczbie polaczen - w praktyce czesto uzywa sie zamiast tego
         *   puli watkow (ExecutorService, patrz _05_multithreading
         *   Lesson21_ExecutorService) zamiast recznego new Thread(...)
         *   za kazdym razem.
         */
    }

    /**
     * Obsluguje jednego klienta na serwerze: odczytuje jedna linie, odsyla
     * odpowiedz, zamyka polaczenie. Uruchamiane w OSOBNYM watku dla kazdego
     * zaakceptowanego polaczenia (thread-per-connection).
     */
    private static void obsluzKlienta(Socket klient, CountDownLatch wszyscyObsluzeni) {
        try (Socket s = klient) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            OutputStream out = s.getOutputStream();

            String wiadomosc = in.readLine();
            System.out.println("  [" + Thread.currentThread().getName() + "] odebralem: " + wiadomosc);

            String odpowiedz = "SERWER (" + Thread.currentThread().getName() + "): echo -> " + wiadomosc + "\n";
            out.write(odpowiedz.getBytes(StandardCharsets.UTF_8));
            out.flush();

        } catch (IOException e) {
            System.out.println("  [" + Thread.currentThread().getName() + "] blad: " + e.getMessage());
        } finally {
            wszyscyObsluzeni.countDown();
        }
    }

    /**
     * Symuluje jednego klienta: laczy sie z serwerem, wysyla krotka
     * wiadomosc, odczytuje odpowiedz i sie rozlacza.
     */
    private static void symulujKlienta(int port, int numerKlienta) {
        try (Socket socket = new Socket("localhost", port)) {
            socket.setSoTimeout(2000);

            OutputStream out = socket.getOutputStream();
            String wiadomosc = "wiadomosc od klienta " + numerKlienta;
            out.write((wiadomosc + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String odpowiedz = in.readLine();
            System.out.println("[" + Thread.currentThread().getName() + "] otrzymano: " + odpowiedz);

        } catch (IOException e) {
            System.out.println("[" + Thread.currentThread().getName() + "] blad polaczenia: " + e.getMessage());
        }
    }
}
