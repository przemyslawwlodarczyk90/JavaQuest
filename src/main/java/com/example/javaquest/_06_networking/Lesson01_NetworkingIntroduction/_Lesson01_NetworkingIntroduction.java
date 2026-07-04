package com.example.javaquest._06_networking.Lesson01_NetworkingIntroduction;

import java.net.InetSocketAddress;

public class _Lesson01_NetworkingIntroduction {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 PROGRAMOWANIE SIECIOWE – WPROWADZENIE
         * ============================================================
         * Ten rozdział (_06_networking) uczy, jak programy napisane w Javie
         * "rozmawiają" ze sobą przez sieć – od surowych gniazd (Socket)
         * przez protokół HTTP, aż po pracę z JSON/XML pobieranymi z sieci.
         *
         * Zanim napiszemy pierwszą linijkę kodu sieciowego, musimy poznać
         * kilka fundamentalnych pojęć: model klient-serwer, TCP vs UDP,
         * adresy IP, porty, DNS oraz HTTP/HTTPS. Ta lekcja jest CZYSTO
         * TEORETYCZNA – pierwsze prawdziwe gniazda (Socket) pojawią się
         * w Lesson03.
         */

        System.out.println("=== WPROWADZENIE DO PROGRAMOWANIA SIECIOWEGO ===");

        /*
         * ============================================================
         * 🔹 MODEL KLIENT-SERWER
         * ============================================================
         * Zdecydowana większość komunikacji sieciowej opiera się na
         * modelu KLIENT-SERWER:
         *
         * - SERWER (server) – program, który NASŁUCHUJE na określonym
         *   porcie i CZEKA na połączenia od klientów. Jest zwykle
         *   uruchomiony długo (24/7) i obsługuje wielu klientów.
         *   Przykład: serwer WWW (np. Apache, nginx), serwer bazy danych.
         *
         * - KLIENT (client) – program, który sam z siebie INICJUJE
         *   połączenie do serwera, wysyła zapytanie i czeka na odpowiedź.
         *   Przykład: przeglądarka internetowa, aplikacja mobilna.
         *
         * 🔹 ANALOGIA: serwer to restauracja (zawsze otwarta, czeka na
         * gości), klient to gość, który wchodzi, składa zamówienie
         * (request) i dostaje danie (response).
         *
         * Jeden serwer zwykle obsługuje WIELU klientów jednocześnie –
         * to właśnie zobaczymy w Lesson06 (ServerSocket) i Lesson07
         * (wielowątkowy serwer, jeden wątek na klienta).
         */

        System.out.println("\n=== MODEL KLIENT-SERWER ===");
        System.out.println("Serwer: nasluchuje, czeka na polaczenia, obsluguje wielu klientow.");
        System.out.println("Klient: inicjuje polaczenie, wysyla zapytanie, odbiera odpowiedz.");

        /*
         * ============================================================
         * 🔍 TCP vs UDP – DWA GŁÓWNE PROTOKOŁY TRANSPORTOWE
         * ============================================================
         * Zarówno TCP, jak i UDP działają "na wierzchu" protokołu IP
         * (który zajmuje się samym adresowaniem i routingiem pakietów).
         * Różnią się tym, JAK dostarczają dane.
         *
         * TCP (Transmission Control Protocol):
         * - POŁĄCZENIOWY – najpierw nawiązywane jest połączenie
         *   (tzw. "three-way handshake"), dopiero potem płyną dane.
         * - NIEZAWODNY – gwarantuje dostarczenie danych W KOLEJNOŚCI,
         *   bez duplikatów, z retransmisją zgubionych pakietów.
         * - Wolniejszy niż UDP (narzut na potwierdzenia, kolejkowanie).
         * - Używany m.in. przez: HTTP/HTTPS, e-mail (SMTP), FTP, SSH.
         * - W Javie: klasy Socket i ServerSocket (poznamy w tym rozdziale).
         *
         * UDP (User Datagram Protocol):
         * - BEZPOŁĄCZENIOWY – wysyłasz pakiet ("datagram") i NIE MASZ
         *   gwarancji, że dotrze, że dotrze w kolejności, ani że dotrze
         *   tylko raz.
         * - Za to jest SZYBKI i ma mały narzut (brak handshake'u,
         *   brak potwierdzeń).
         * - Używany tam, gdzie liczy się szybkość bardziej niż
         *   niezawodność: streaming wideo/audio na żywo, gry online,
         *   DNS (zapytania), VoIP.
         * - W Javie: klasy DatagramSocket i DatagramPacket (poza zakresem
         *   tego rozdziału – skupiamy się na TCP, bo to fundament HTTP).
         *
         * 📌 KIEDY KTÓRE?
         * - Ważna KAŻDA porcja danych, kolejność, brak strat -> TCP
         *   (np. przesyłanie pliku, strona WWW, transakcja bankowa).
         * - Ważniejsza SZYBKOŚĆ niż pojedyncza utrata pakietu -> UDP
         *   (np. rozmowa wideo – lepiej stracić 1 klatkę niż czekać
         *   na jej retransmisję i mieć opóźnienia).
         */

        System.out.println("\n=== TCP vs UDP ===");
        System.out.println("TCP: polaczeniowy, niezawodny, w kolejnosci -> HTTP, e-mail, SSH");
        System.out.println("UDP: bezpolaczeniowy, szybki, bez gwarancji -> streaming, gry, DNS");

        /*
         * ============================================================
         * 🔹 ADRESY IP (IPv4)
         * ============================================================
         * Adres IP jednoznacznie identyfikuje urządzenie w sieci
         * (na potrzeby routingu pakietów). W tym kursie skupiamy się
         * na IPv4 (wciąż dominujący w praktyce, choć IPv6 zyskuje).
         *
         * IPv4: 4 liczby (0-255) oddzielone kropkami, np. 192.168.0.1
         * - Łącznie ok. 4.3 miliarda możliwych adresów (32 bity).
         * - Adresy prywatne (używane w sieciach lokalnych, np. domowych):
         *     10.0.0.0    - 10.255.255.255
         *     172.16.0.0  - 172.31.255.255
         *     192.168.0.0 - 192.168.255.255
         * - Adres pętli zwrotnej (loopback, "to ja sam"): 127.0.0.1
         *   (alias: localhost).
         *
         * IPv6 (skrótowo): 128-bitowy, zapisywany szesnastkowo,
         * np. 2001:0db8:85a3:0000:0000:8a2e:0370:7334 – powstał, bo
         * adresów IPv4 zabrakło. W tym kursie się nim nie zajmujemy,
         * ale warto wiedzieć, że istnieje i coraz częściej jest używany.
         */

        System.out.println("\n=== ADRESY IP ===");
        System.out.println("IPv4 loopback (localhost): 127.0.0.1");
        System.out.println("Przyklad adresu prywatnego: 192.168.0.1");

        /*
         * ============================================================
         * 🔍 PORTY
         * ============================================================
         * Sam adres IP identyfikuje MASZYNĘ, ale na jednej maszynie może
         * działać wiele programów sieciowych naraz (serwer WWW, serwer
         * pocztowy, baza danych...). PORT pozwala rozróżnić, do KTÓREGO
         * programu na danej maszynie ma trafić połączenie.
         *
         * Port to liczba w zakresie 0-65535:
         * - 0-1023      -> porty "well-known" (zarezerwowane, wymagają
         *                  zwykle uprawnień administratora do nasłuchu)
         * - 1024-49151  -> porty zarejestrowane (registered)
         * - 49152-65535 -> porty dynamiczne/prywatne (efemeryczne –
         *                  system sam je przydziela klientom)
         *
         * Kilka najważniejszych portów "well-known" do zapamiętania:
         *   20/21  -> FTP (transfer plików)
         *   22     -> SSH (bezpieczny zdalny dostęp)
         *   25     -> SMTP (wysyłka e-maili)
         *   53     -> DNS
         *   80     -> HTTP
         *   443    -> HTTPS
         *
         * Adres IP + port razem tworzą unikalny punkt końcowy (endpoint),
         * np. 93.184.216.34:80. W Javie taki endpoint reprezentuje klasa
         * InetSocketAddress.
         */

        System.out.println("\n=== PORTY (WELL-KNOWN) ===");
        System.out.printf("%-8s %-6s %s%n", "Port", "Skrot", "Zastosowanie");
        System.out.printf("%-8s %-6s %s%n", "20/21", "FTP", "Transfer plikow");
        System.out.printf("%-8s %-6s %s%n", "22", "SSH", "Zdalny dostep");
        System.out.printf("%-8s %-6s %s%n", "25", "SMTP", "Wysylka e-maili");
        System.out.printf("%-8s %-6s %s%n", "53", "DNS", "Rozwiazywanie nazw domen");
        System.out.printf("%-8s %-6s %s%n", "80", "HTTP", "Strony WWW (bez szyfrowania)");
        System.out.printf("%-8s %-6s %s%n", "443", "HTTPS", "Strony WWW (szyfrowane TLS)");

        // InetSocketAddress reprezentuje pare (host/IP, port) - "gdzie" laczymy sie w sieci
        InetSocketAddress endpoint = new InetSocketAddress("example.com", 443);
        System.out.println("\nPrzykladowy endpoint: " + endpoint);
        System.out.println("Sam host: " + endpoint.getHostString());
        System.out.println("Sam port: " + endpoint.getPort());
        // Uwaga: powyzsze NIE laczy sie z siecia - to tylko obiekt "adres + port"
        // w pamieci. Prawdziwe polaczenie nawiazemy dopiero przez Socket (Lesson03+).

        /*
         * ============================================================
         * 🔹 DNS (Domain Name System)
         * ============================================================
         * Ludzie zapamiętują nazwy (np. "example.com"), a komputery
         * komunikują się po adresach IP (np. 93.184.216.34). DNS to
         * rozproszony, hierarchiczny "system nazw" tłumaczący nazwy
         * domen na adresy IP – coś jak globalna książka telefoniczna
         * internetu.
         *
         * W dużym skrócie, gdy wpisujesz w przeglądarce "example.com":
         * 1) Komputer pyta serwer DNS: "jaki adres IP ma example.com?"
         * 2) Zapytanie może wędrować przez kilka serwerów DNS (root ->
         *    TLD ".com" -> serwer autorytatywny dla example.com), zwykle
         *    jednak wynik jest już zapamiętany (cache) po drodze.
         * 3) DNS zwraca adres IP, np. 93.184.216.34.
         * 4) Dopiero TERAZ komputer nawiązuje połączenie TCP do TEGO IP.
         *
         * DNS typowo działa po UDP (port 53) – szybkie, pojedyncze
         * zapytanie/odpowiedź, ewentualny brak odpowiedzi łatwo ponowić.
         *
         * W Javie sam proces DNS jest "schowany" – wystarczy podać nazwę
         * hosta, a klasa InetAddress (Lesson02) wykona rozwiązywanie
         * nazwy (DNS lookup) za nas.
         */

        System.out.println("\n=== DNS ===");
        System.out.println("DNS tlumaczy nazwy (example.com) na adresy IP (93.184.216.34)");
        System.out.println("Dzialanie w skrocie: nazwa -> zapytanie DNS -> adres IP -> polaczenie TCP");

        /*
         * ============================================================
         * 🔍 HTTP vs HTTPS
         * ============================================================
         * HTTP (HyperText Transfer Protocol) to protokół warstwy
         * aplikacji, na którym opiera się przeglądanie stron WWW.
         * Działa NA WIERZCHU TCP – klient wysyła zapytanie tekstowe
         * (request), serwer odsyła odpowiedź tekstową (response).
         * Zobaczymy to "z bliska" w Lesson05 (ręczne zapytanie HTTP
         * przez surowy Socket) oraz w dalszych lekcjach (URL,
         * HttpURLConnection).
         *
         * HTTP:
         * - Domyślny port: 80
         * - Dane przesyłane JAWNYM TEKSTEM (plain text) – każdy, kto
         *   podsłucha ruch sieciowy, może odczytać treść (np. hasła!).
         *
         * HTTPS (HTTP Secure):
         * - Domyślny port: 443
         * - To HTTP przesyłane przez dodatkową warstwę szyfrującą: TLS
         *   (Transport Layer Security – następca starszego SSL).
         * - TLS w skrócie zapewnia:
         *     - POUFNOŚĆ    – nikt po drodze nie odczyta treści (szyfrowanie)
         *     - INTEGRALNOŚĆ – dane nie zostały zmienione po drodze
         *     - UWIERZYTELNIENIE – klient ma pewność, że rozmawia
         *       z prawdziwym serwerem (certyfikat wystawiony przez
         *       zaufany urząd certyfikacji, CA)
         * - Dziś HTTPS to standard – przeglądarki oznaczają strony
         *   bez HTTPS jako "niezabezpieczone".
         *
         * 📌 W SKRÓCIE: HTTPS = HTTP + TLS (szyfrowanie + weryfikacja
         * tożsamości serwera). Sam format zapytań/odpowiedzi HTTP
         * pozostaje identyczny – różnica jest w warstwie transportowej
         * pod spodem.
         */

        System.out.println("\n=== HTTP vs HTTPS ===");
        System.out.println("HTTP:  port 80,  tekst jawny (bez szyfrowania)");
        System.out.println("HTTPS: port 443, HTTP + TLS (szyfrowanie, integralnosc, uwierzytelnienie serwera)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Model klient-serwer: serwer nasluchuje i obsluguje wielu
         *   klientow, klient inicjuje polaczenie.
         * - TCP: polaczeniowy, niezawodny, w kolejnosci -> HTTP, e-mail, SSH.
         * - UDP: bezpolaczeniowy, szybki, bez gwarancji -> streaming, gry, DNS.
         * - Adres IP identyfikuje maszyne (IPv4: 4 liczby 0-255,
         *   loopback = 127.0.0.1/localhost).
         * - Port (0-65535) identyfikuje KONKRETNY program na maszynie
         *   (well-known: 22 SSH, 25 SMTP, 53 DNS, 80 HTTP, 443 HTTPS).
         * - DNS tlumaczy nazwy domen na adresy IP (nazwa -> IP -> TCP).
         * - HTTPS = HTTP + TLS: szyfrowanie, integralnosc, uwierzytelnienie
         *   serwera. Domyslne porty: HTTP=80, HTTPS=443.
         *
         * W kolejnej lekcji (Lesson02) poznamy klase InetAddress -
         * pierwsze prawdziwe API sieciowe Javy, ktore wykonuje
         * rozwiazywanie nazw (DNS lookup) za nas.
         */
    }
}
