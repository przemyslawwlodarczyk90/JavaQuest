package com.example.javaquest._06_networking.Lesson04_SocketWhois;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class _Lesson04_SocketWhois {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 PROTOKÓŁ WHOIS - PROSTY TEKSTOWY PROTOKÓŁ NA TCP
         * ============================================================
         * WHOIS to bardzo stary (lata 80.), bardzo prosty protokół
         * służący do odpytywania rejestrów domen o dane właściciela/
         * rejestracji domeny (kto zarejestrował domenę, kiedy wygasa,
         * jaki serwer DNS jej używa itd.).
         *
         * Charakterystyka WHOIS (dobry przykład "gołego" protokołu
         * tekstowego na TCP - bez żadnej otoczki jak HTTP):
         * - działa na porcie 43 (standardowy, zarezerwowany port WHOIS),
         * - protokół TEKSTOWY, liniowy (line-based) - klient wysyła
         *   JEDNĄ linię (nazwę domeny) zakończoną "\r\n",
         * - serwer odsyła odpowiedź jako czysty tekst, linia po linii,
         *   i sam zamyka połączenie po wysłaniu całości,
         * - BRAK sztywnego schematu odpowiedzi - każdy rejestr (TLD)
         *   formatuje dane trochę inaczej (to nie jest ustrukturyzowany
         *   format jak JSON/XML) - dlatego WHOIS bywa dziś zastępowany
         *   przez nowocześniejszy protokół RDAP (JSON przez HTTPS).
         *
         * My połączymy się z whois.iana.org - to punkt startowy w
         * hierarchii WHOIS (IANA zarządza globalną pulą domen najwyższego
         * poziomu i "kieruje" do właściwego rejestru dla danej domeny).
         *
         * ⚠️ WAŻNE (bezpieczeństwo demo): to POŁĄCZENIE Z PRAWDZIWYM
         * serwerem w internecie - w przeciwieństwie do Lesson03 (lokalny
         * serwer echo). Jeśli nie ma połączenia z internetem albo serwer
         * jest niedostępny, program NIE MOŻE "zawiesić się" w
         * nieskończoność - dlatego ustawiamy setSoTimeout(5000) (max 5s
         * oczekiwania na dane) i całość owijamy w try-catch z czytelnym
         * komunikatem zastępczym.
         */

        System.out.println("=== java.net.Socket - zapytanie WHOIS (whois.iana.org:43) ===");

        String host = "whois.iana.org";
        int port = 43;
        String domena = "example.com";

        /*
         * ============================================================
         * 🔹 KROK 1: połączenie i wysłanie zapytania
         * ============================================================
         * Zapytanie WHOIS to po prostu nazwa domeny + zakończenie linii
         * "\r\n" (CRLF - klasyczne zakończenie linii w protokołach
         * sieciowych, np. też w HTTP - zobacz Lesson05).
         */

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(5000); // max 5s oczekiwania na dane - kluczowe zabezpieczenie

            System.out.println("Polaczono z " + host + ":" + port);
            System.out.println("Wysylam zapytanie o domene: " + domena);

            OutputStream out = socket.getOutputStream();
            out.write((domena + "\r\n").getBytes(StandardCharsets.UTF_8));
            out.flush();

            /*
             * ============================================================
             * 🔍 KROK 2: odczyt surowej odpowiedzi tekstowej, linia po linii
             * ============================================================
             * Serwer WHOIS sam zamknie połączenie po wysłaniu całej
             * odpowiedzi - readLine() zwróci null, gdy strumień się
             * skończy, więc pętla while zakończy się naturalnie (NIE jest
             * to pętla nieskończona - jej koniec gwarantuje zachowanie
             * serwera + timeout na wypadek problemów).
             */

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            System.out.println("\n=== ODPOWIEDZ SERWERA WHOIS ===");
            String linia;
            while ((linia = reader.readLine()) != null) {
                System.out.println(linia);
            }
            System.out.println("=== KONIEC ODPOWIEDZI (serwer zamknal polaczenie) ===");

        } catch (SocketTimeoutException e) {
            System.out.println("\n[BLAD] Przekroczono czas oczekiwania (5s) - " +
                    "brak polaczenia z internetem lub serwer niedostepny.");
        } catch (IOException e) {
            System.out.println("\n[BLAD] Nie udalo sie polaczyc z serwerem WHOIS - " +
                    "brak polaczenia z internetem lub serwer niedostepny. Szczegoly: " + e.getMessage());
        }

        System.out.println("\n=== KONIEC DEMONSTRACJI ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - WHOIS to prosty, TEKSTOWY, LINIOWY protokół na porcie 43 -
         *   klient wysyła nazwę domeny zakończoną "\r\n", serwer odsyła
         *   odpowiedź jako zwykły tekst i sam zamyka połączenie.
         * - Odpowiedź NIE MA sztywnego schematu - każdy rejestr formatuje
         *   ją trochę inaczej (to odróżnia WHOIS od nowocześniejszych,
         *   ustrukturyzowanych API jak RDAP/JSON).
         * - setSoTimeout(...) jest KONIECZNY przy łączeniu się z
         *   prawdziwymi serwerami w internecie - bez niego program mógłby
         *   czekać w nieskończoność, jeśli serwer nie odpowie.
         * - Zawsze łapiemy IOException przy komunikacji sieciowej -
         *   sieć jest zawodna (brak internetu, serwer padł, firewall itp.)
         *   i kod musi się z tym godnie obchodzić, a nie "wieszać" program.
         *
         * W Lesson05 zobaczymy podobny wzorzec (Socket + surowy tekst),
         * ale dla protokołu HTTP - "ręcznie" złożymy zapytanie GET.
         */
    }
}
