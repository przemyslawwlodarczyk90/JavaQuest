package com.example.javaquest._06_networking.Lesson03_Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class _Lesson03_Socket {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 java.net.Socket - KLIENT TCP
         * ============================================================
         * Socket to obiekt reprezentujacy JEDNO polaczenie TCP miedzy
         * dwoma punktami koncowymi (patrz Lesson01 - endpoint = IP + port).
         * Po stronie klienta tworzymy Socket wskazujac host i port
         * serwera - konstruktor od razu probuje nawiazac polaczenie
         * (three-way handshake TCP "pod maska").
         *
         * Po nawiazaniu polaczenia Socket udostepnia:
         * - getOutputStream() -> strumien do WYSYLANIA danych do serwera
         * - getInputStream()  -> strumien do ODBIERANIA danych od serwera
         *
         * To ten sam swiat co strumienie z rozdzialu _04_io (Lesson01-06) -
         * gniazdo sieciowe "pod spodem" to po prostu kolejne zrodlo/cel
         * strumienia bajtow.
         *
         * ⚠️ WAZNE (bezpieczenstwo demo): ta lekcja NIE laczy sie z
         * zadnym prawdziwym serwerem w internecie. Zeby dzialala
         * natychmiastowo i bez zaleznosci od sieci, sami uruchamiamy
         * malutki serwer echo NA TEJ SAMEJ maszynie, w tle (watek
         * demon), na porcie 0 (system sam wybierze wolny port - zero
         * ryzyka konfliktu portow). Prawdziwe polaczenia z internetem
         * (WHOIS, HTTP) poznamy w Lesson04 i Lesson05.
         */

        System.out.println("=== java.net.Socket - klient TCP (z lokalnym serwerem echo) ===");

        /*
         * ============================================================
         * 🔹 KROK 1: lokalny serwer echo w tle (watek demon)
         * ============================================================
         * ServerSocket(0) -> port 0 oznacza "system operacyjny, wybierz
         * dowolny wolny port" - dzieki temu lekcja NIGDY nie koliduje
         * z innym procesem zajmujacym staly port.
         *
         * Serwer w petli: accept() JEDNEGO klienta, odczytuje JEDNA linie
         * tekstu, odsyla ja z powrotem (echo), zamyka polaczenie i konczy
         * watek. To celowo bardzo proste - pelny ServerSocket (z
         * accept() w petli, obsluga wielu klientow) poznamy w Lesson06
         * i Lesson07.
         */

        ServerSocket serverSocket = new ServerSocket(0); // 0 = wolny port przydzielony przez OS
        int port = serverSocket.getLocalPort();
        System.out.println("Lokalny serwer echo nasluchuje na porcie: " + port);

        Thread serverThread = new Thread(() -> {
            try (ServerSocket ss = serverSocket;
                 Socket clientOnServerSide = ss.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientOnServerSide.getInputStream(), StandardCharsets.UTF_8));
                 OutputStream out = clientOnServerSide.getOutputStream()) {

                String linia = in.readLine();
                System.out.println("[serwer] odebralem: " + linia);

                String odpowiedz = "ECHO: " + linia + "\n";
                out.write(odpowiedz.getBytes(StandardCharsets.UTF_8));
                out.flush();
                System.out.println("[serwer] odeslalem echo i konczy prace");

            } catch (IOException e) {
                System.out.println("[serwer] blad: " + e.getMessage());
            }
        }, "lokalny-serwer-echo");

        serverThread.setDaemon(true); // watek demon - nie zablokuje zakonczenia JVM
        serverThread.start();

        /*
         * ============================================================
         * 🔍 KROK 2: klient - polaczenie Socket do localhost:port
         * ============================================================
         * try-with-resources automatycznie zamknie Socket (a wiec i jego
         * strumienie) na koncu bloku - zawsze zwalniamy zasoby sieciowe,
         * dokladnie jak pliki w rozdziale _04_io.
         *
         * setSoTimeout(...) ustawia maksymalny czas oczekiwania na dane
         * przy odczycie (read()) - jesli serwer nie odpowie w tym czasie,
         * odczyt rzuci SocketTimeoutException zamiast czekac w
         * nieskonczonosc. To kluczowe zabezpieczenie kazdego kodu
         * sieciowego.
         */

        try (Socket clientSocket = new Socket("localhost", port)) {
            clientSocket.setSoTimeout(2000); // max 2s oczekiwania na odpowiedz

            System.out.println("\n[klient] polaczono z serwerem na porcie " + port);

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));

            String wiadomosc = "Witaj, serwerze!";
            writer.println(wiadomosc); // println dodaje "\n" - potrzebne, bo serwer czyta readLine()
            System.out.println("[klient] wyslano: " + wiadomosc);

            String odpowiedz = reader.readLine();
            System.out.println("[klient] otrzymano: " + odpowiedz);

        } catch (IOException e) {
            System.out.println("[klient] blad polaczenia: " + e.getMessage());
        }

        // Poczekaj chwile az watek serwera skonczy sprzatanie (z limitem czasu -
        // nigdy nie czekamy w nieskonczonosc).
        serverThread.join(2000);

        System.out.println("\n=== KONIEC DEMONSTRACJI ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Socket to JEDNO polaczenie TCP klient <-> serwer.
         * - new Socket(host, port) od razu probuje nawiazac polaczenie
         *   (moze rzucic IOException, jesli sie nie uda).
         * - getOutputStream()/getInputStream() -> wysylanie/odbieranie
         *   danych - to zwykle strumienie bajtow, jak w rozdziale _04_io.
         * - setSoTimeout(ms) -> limit czasu na odczyt, zeby program nigdy
         *   nie "zawisl" czekajac na dane, ktore nigdy nie przyjda.
         * - Socket (i ServerSocket) NALEZY zamykac - najlepiej przez
         *   try-with-resources.
         * - Serwer nasluchujacy na porcie 0 -> system sam wybiera wolny
         *   port (przydatne w testach/demo, zeby uniknac konfliktow).
         * - Watki pomocnicze (np. lokalny serwer w demo) oznaczamy jako
         *   demon i/lub laczymy (join) z limitem czasu, zeby main() zawsze
         *   konczyl sie samodzielnie.
         *
         * W kolejnych lekcjach (Lesson04, Lesson05) uzyjemy Socket do
         * POLACZENIA Z PRAWDZIWYMI serwerami w internecie (WHOIS, HTTP).
         */
    }
}
