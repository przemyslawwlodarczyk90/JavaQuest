package com.example.javaquest._06_networking.Lesson03_Socket;

public class _Exercises_Lesson03_Socket {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LocalEchoServerBasic {
        /*
         * 🧪 Zadanie 1:
         * Uruchom (jak w lekcji) lokalny ServerSocket(0) w wątku demon,
         * akceptujący jednego klienta, odczytujący jedną linię i odsyłający ją
         * z prefiksem "ECHO: ". Klient (main) wysyła "Test polaczenia" i wypisuje
         * otrzymaną odpowiedź. Pamiętaj o setSoTimeout i zamknięciu zasobów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SoTimeoutDemo {
        /*
         * 🧪 Zadanie 2:
         * Uruchom lokalny serwer (ServerSocket(0)), który po accept() celowo NIC
         * nie wysyła klientowi (śpi np. 3 sekundy, po czym zamyka gniazdo).
         * Klient ustawia setSoTimeout(1000) i próbuje odczytać odpowiedź -
         * złap SocketTimeoutException i wypisz czytelny komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MultipleMessagesSingleConnection {
        /*
         * 🧪 Zadanie 3:
         * Zmodyfikuj lokalny serwer echo tak, aby w pętli odczytał DOKŁADNIE 3
         * linie od klienta i odesłał każdą z osobna z prefiksem "ECHO: ". Klient
         * wysyła kolejno 3 różne wiadomości przez tę samą, jedną otwartą
         * połączenie i wypisuje 3 otrzymane odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UppercaseEchoServer {
        /*
         * 🧪 Zadanie 4:
         * Lokalny serwer echo, który zamienia otrzymaną linię na wielkie litery
         * (String.toUpperCase()) przed odesłaniem. Klient wysyła "Witaj swiecie"
         * i wypisuje odpowiedź "WITAJ SWIECIE".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ByteCountingServer {
        /*
         * 🧪 Zadanie 5:
         * Lokalny serwer, który zamiast odsyłać echo, liczy długość otrzymanej
         * linii w bajtach (UTF-8, getBytes().length) i odsyła String "BAJTY: N".
         * Klient wysyła "Zażółć gęślą jaźń" i wypisuje otrzymaną liczbę bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ConnectionRefusedHandling {
        /*
         * 🧪 Zadanie 6:
         * Spróbuj połączyć się (new Socket("localhost", port)) z portem, o
         * którym wiadomo, że nic na nim nie nasłuchuje (np. weź wolny port z
         * new ServerSocket(0), od razu go zamknij, a potem połącz się na ten sam
         * numer portu). Złap IOException ("connection refused") i wypisz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GetRemoteAndLocalSocketAddress {
        /*
         * 🧪 Zadanie 7:
         * W lokalnym demo echo (jak w lekcji) po nawiązaniu połączenia po
         * stronie klienta wypisz getLocalSocketAddress() oraz
         * getRemoteSocketAddress() i skomentuj różnicę między nimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ClosingSocketExplicitly {
        /*
         * 🧪 Zadanie 8:
         * Otwórz Socket do lokalnego serwera echo BEZ try-with-resources,
         * wykonaj wymianę wiadomości, a następnie ręcznie wywołaj close() w
         * bloku finally. Wypisz isClosed() przed i po wywołaniu close().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ReverseEchoServer {
        /*
         * 🧪 Zadanie 9:
         * Lokalny serwer, który odwraca otrzymany String (new
         * StringBuilder(linia).reverse()) przed odesłaniem. Klient wysyła
         * "Ala ma kota" i wypisuje odwróconą odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TryWithResourcesForBoth {
        /*
         * 🧪 Zadanie 10:
         * Napisz wersję demo z lekcji, w której zarówno ServerSocket (w wątku
         * serwera), jak i klient Socket (w main) są zarządzane przez
         * try-with-resources, bez ani jednego ręcznego wywołania close().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoRoundTripEcho {
        /*
         * 🧪 Zadanie 11:
         * Zmodyfikuj serwer echo, aby obsłużył 2 kolejne żądania na TYM SAMYM
         * połączeniu (2x readLine + 2x odpowiedź). Klient wysyła "pierwsza
         * wiadomosc", odbiera echo, potem wysyła "druga wiadomosc" i też
         * odbiera echo - wszystko na jednym Sockecie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConfigurableEchoServer {
        /*
         * 🧪 Zadanie 12:
         * Serwer parsuje otrzymaną linię: jeśli zaczyna się od "UPPER:",
         * odsyła resztę tekstu wielkimi literami; jeśli od "REV:" - odwróconą;
         * w przeciwnym razie zwykłe echo. Klient testuje po kolei 3 warianty
         * wejścia i wypisuje odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ClientRetryOnRefused {
        /*
         * 🧪 Zadanie 13:
         * Napisz klienta, który próbuje połączyć się z portem bez nasłuchującego
         * serwera maksymalnie 3 razy (ograniczona, skończona pętla), za każdym
         * razem łapiąc IOException, i po wyczerpaniu prób wypisuje komunikat
         * o rezygnacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WordCountServer {
        /*
         * 🧪 Zadanie 14:
         * Lokalny serwer liczy liczbę słów w otrzymanej linii (split po białych
         * znakach) i odsyła "SLOWA: N". Klient wysyła "To jest przykladowe
         * zdanie testowe" i wypisuje otrzymaną liczbę słów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultiLineRequestResponse {
        /*
         * 🧪 Zadanie 15:
         * Klient wysyła kilka linii tekstu zakończonych specjalnym markerem
         * "END". Serwer czyta linie w pętli, aż napotka "END", liczy odebrane
         * linie (bez markera) i odsyła "LICZBA_LINII: N".
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SlowServerTimeoutHandling {
        /*
         * 🧪 Zadanie 16:
         * Serwer po odebraniu wiadomości celowo czeka (Thread.sleep(3000)) przed
         * odesłaniem odpowiedzi. Klient ustawia setSoTimeout(1000), więc odczyt
         * powinien rzucić SocketTimeoutException - złap go i wypisz czytelny
         * komunikat (główny wątek nie czeka na spóźnioną odpowiedź serwera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_EchoServerWithGreeting {
        /*
         * 🧪 Zadanie 17:
         * Serwer od razu po zaakceptowaniu połączenia wysyła powitalną linię
         * "WITAJ NA SERWERZE" (zanim jeszcze cokolwiek odczyta). Klient najpierw
         * odczytuje i wypisuje powitanie, dopiero potem wysyła swoją wiadomość
         * i odczytuje zwykłe echo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CountingConnectionsServer {
        /*
         * 🧪 Zadanie 18:
         * Serwer w pętli bounded do 2 iteracji: dla każdej iteracji tworzy NOWY
         * ServerSocket(0) (na innym porcie), akceptuje jednego klienta, odsyła
         * echo i zamyka gniazdo. Main sekwencyjnie łączy się jako "klient 1", a
         * potem jako "klient 2", drukując port użyty za każdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ClientSendsNumberServerDoublesIt {
        /*
         * 🧪 Zadanie 19:
         * Prosty protokół: klient wysyła liczbę jako tekst (np. "21"), serwer
         * parsuje ją (Integer.parseInt), mnoży razy 2 i odsyła wynik jako tekst.
         * Klient wypisuje otrzymaną wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_StructuredProtocolMessage {
        /*
         * 🧪 Zadanie 20:
         * Klient wysyła sformatowaną wiadomość "NAME:Jan;AGE:30". Serwer parsuje
         * pary klucz-wartość (rozdzielone ";", każda para po ":") i odsyła
         * potwierdzenie "OK, imie=Jan wiek=30".
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CalculatorProtocolServer {
        /*
         * 🧪 Zadanie 21:
         * Serwer implementuje prosty protokół tekstowy: klient wysyła np.
         * "ADD 3 4", "SUB 10 4" lub "MUL 6 7"; serwer parsuje operację i dwie
         * liczby, oblicza wynik i odsyła go jako tekst. Klient testuje wszystkie
         * trzy operacje sekwencyjnie na jednym połączeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ClientUsesInetAddressForConnection {
        /*
         * 🧪 Zadanie 22:
         * Zamiast konstruktora Socket(String host, int port), użyj
         * InetAddress.getLoopbackAddress() (z Lesson02) i konstruktora
         * Socket(InetAddress, int) do połączenia z lokalnym serwerem echo.
         * Zweryfikuj, że wymiana wiadomości nadal działa poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TimingRoundTrip {
        /*
         * 🧪 Zadanie 23:
         * Na JEDNYM, trwałym połączeniu z lokalnym serwerem echo wykonaj 5
         * kolejnych żądań/odpowiedzi, mierząc (System.nanoTime()) czas każdego
         * "round trip". Wypisz minimalny, maksymalny i średni czas w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_GracefulServerShutdownSignal {
        /*
         * 🧪 Zadanie 24:
         * Serwer rozpoznaje specjalną wiadomość "STOP" - gdy ją odbierze,
         * odsyła potwierdzenie "ZAMYKAM POLACZENIE" zamiast zwykłego echo i
         * kończy obsługę. Klient wysyła "STOP" i wypisuje otrzymane potwierdzenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_EchoServerWithPerLineTimestamp {
        /*
         * 🧪 Zadanie 25:
         * Serwer dołącza do każdej odsyłanej linii znacznik czasu
         * (System.currentTimeMillis()) na początku, np. "[123456789] ECHO: ...".
         * Klient wysyła wiadomość i wypisuje odpowiedź razem z timestampem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiClientSequentialBounded {
        /*
         * 🧪 Zadanie 26:
         * Serwer w pętli for wykonuje DOKŁADNIE 3 razy: accept() jednego
         * klienta, obsłuż go (echo), zamknij połączenie, wróć do accept().
         * Main sekwencyjnie (jeden po drugim, BEZ wątków) łączy się jako 3
         * kolejni klienci, wysyłając różne wiadomości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ProtocolWithLengthPrefix {
        /*
         * 🧪 Zadanie 27:
         * Klient wysyła 4-bajtowy prefiks długości (ręcznie, przez przesunięcia
         * bitowe out.write(len >> 24) itd.), a potem sam payload (bajty tekstu).
         * Serwer odczytuje najpierw 4 bajty długości, potem dokładnie tyle
         * bajtów payloadu ile wskazuje długość, i odsyła je z powrotem
         * (w tym samym formacie: prefiks + payload).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RobustClientWithTimeoutAndRetry {
        /*
         * 🧪 Zadanie 28:
         * Serwer startuje nasłuchiwanie dopiero po krótkim, kontrolowanym
         * opóźnieniu (Thread.sleep(500) przed new ServerSocket). Klient próbuje
         * połączyć się maksymalnie 3 razy z odstępem 300 ms między próbami,
         * łapiąc IOException, aż połączenie się powiedzie albo limit prób
         * zostanie wyczerpany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullDuplexPingPong {
        /*
         * 🧪 Zadanie 29:
         * Na jednym połączeniu klient i serwer wymieniają się na przemian
         * wiadomościami "PING" (klient) i "PONG" (serwer) DOKŁADNIE 3 razy
         * (pętla bounded do 3 iteracji po obu stronach). Wypisz przebieg całej
         * wymiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniChatCapstone {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji: lokalny serwer akceptuje jednego
         * klienta i wymieniają DOKŁADNIE 3 predefiniowane wiadomości w każdą
         * stronę (jak mini-czat), każda odsyłana wiadomość serwera ma
         * timestamp i jest zamieniana na wielkie litery. Po wymianie
         * połączenie jest jawnie i poprawnie zamykane po obu stronach -
         * wypisz pełny przebieg "rozmowy".
         */
        public static void main(String[] args) { }
    }
}
