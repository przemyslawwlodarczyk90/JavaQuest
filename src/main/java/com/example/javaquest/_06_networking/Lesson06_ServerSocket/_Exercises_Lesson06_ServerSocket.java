package com.example.javaquest._06_networking.Lesson06_ServerSocket;

public class _Exercises_Lesson06_ServerSocket {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicServerOneClient {
        /*
         * 🧪 Zadanie 1:
         * Odtwórz wzorzec z lekcji: ServerSocket(0) w wątku demon,
         * zsynchronizowany CountDownLatch(1), akceptujący JEDNEGO klienta,
         * odczytujący jedną linię i odsyłający ją z prefiksem "SERWER: ".
         * Klient (main) wysyła "Czesc serwerze" i wypisuje odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ServerRespondsWithFixedGreeting {
        /*
         * 🧪 Zadanie 2:
         * Zmodyfikuj serwer tak, aby IGNOROWAŁ treść wiadomości klienta i
         * zawsze odsyłał tę samą, stałą odpowiedź "WITAJ W NASZYM SERWISIE".
         * Klient wysyła dowolną wiadomość i wypisuje stałą odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ServerEchoesTwice {
        /*
         * 🧪 Zadanie 3:
         * Serwer po odczytaniu JEDNEJ linii od klienta odsyła DWIE linie
         * odpowiedzi (np. echo powtórzone dwukrotnie). Klient odczytuje obie
         * linie (dwa readLine()) i wypisuje je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ClientSendsNumberServerSquares {
        /*
         * 🧪 Zadanie 4:
         * Klient wysyła liczbę jako tekst (np. "7"). Serwer parsuje ją
         * (Integer.parseInt) i odsyła jej kwadrat jako tekst. Klient wypisuje
         * otrzymany wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GetRemoteSocketAddressOnServer {
        /*
         * 🧪 Zadanie 5:
         * Po stronie serwera, zaraz po accept(), wypisz
         * klient.getRemoteSocketAddress() i klient.getLocalSocketAddress().
         * Skomentuj (w println) różnicę między nimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LatchTimeoutTooShort {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj wariant demo, w którym serverGotowy.await(1,
         * TimeUnit.MILLISECONDS) (celowo bardzo krótki czas). Obsłuż OBA
         * przypadki: jeśli await() zwróci false, wypisz komunikat i
         * zakończ metodę (return) bez próby połączenia - dokładnie jak w
         * oryginalnym wzorcu z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ServerClosesWithoutResponding {
        /*
         * 🧪 Zadanie 7:
         * Serwer akceptuje połączenie, ale NIC nie odczytuje ani nie
         * odsyła - od razu zamyka gniazdo klienta. Klient ustawia
         * setSoTimeout(2000) i próbuje odczytać odpowiedź - obsłuż zarówno
         * przypadek odczytania null (koniec strumienia), jak i wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountConnectionAttempts {
        /*
         * 🧪 Zadanie 8:
         * Użyj AtomicInteger inkrementowanego w wątku serwera za każdym razem,
         * gdy accept() zwróci połączenie. Po jednym połączeniu klienta
         * zweryfikuj (i wypisz), że licznik ma wartość 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ServerRejectsEmptyMessage {
        /*
         * 🧪 Zadanie 9:
         * Serwer sprawdza, czy odczytana linia jest null lub pusta - jeśli
         * tak, odsyła "PUSTA WIADOMOSC" zamiast zwykłego echo. Klient wysyła
         * pusty String "" i wypisuje otrzymaną odpowiedź o błędzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PortZeroDemonstration {
        /*
         * 🧪 Zadanie 10:
         * Utwórz new ServerSocket(0), wypisz przydzielony port, zamknij je,
         * a następnie utwórz DRUGI new ServerSocket(0) i również wypisz jego
         * port. Skomentuj (println), że system operacyjny sam przydziela
         * wolny port za każdym razem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoSequentialClientsOneAtATime {
        /*
         * 🧪 Zadanie 11:
         * Serwer wywołuje accept() DWA razy w pętli for (bez wątków,
         * sekwencyjnie) - obsługuje klienta A do końca, dopiero potem
         * przyjmuje klienta B. Main łączy się jako klient A, czeka na
         * zakończenie, potem jako klient B.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_StructuredRequestParsing {
        /*
         * 🧪 Zadanie 12:
         * Klient wysyła "CMD:PING". Serwer rozpoznaje komendę "PING" i
         * odpowiada "PONG"; dla dowolnej innej treści odpowiada "UNKNOWN".
         * Przetestuj klienta wysyłającego "CMD:PING" oraz osobno inny test z
         * "CMD:COS_INNEGO".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LatchCountingMultipleSignals {
        /*
         * 🧪 Zadanie 13:
         * Użyj CountDownLatch(2) z dwoma odrębnymi sygnałami: "gniazdo
         * utworzone" i "serwer gotowy do accept()", odliczanymi osobno w
         * dwóch momentach kodu serwera. Klient czeka (await) na oba sygnały
         * przed połączeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ServerWithReadTimeoutOnAcceptedSocket {
        /*
         * 🧪 Zadanie 14:
         * Serwer ustawia setSoTimeout(1000) na zaakceptowanym gnieździe
         * klienta. Klient celowo opóźnia wysłanie wiadomości (Thread.sleep(2000)
         * przed napisaniem czegokolwiek). Zweryfikuj, że serwer łapie
         * SocketTimeoutException podczas odczytu i obsługuje go gracefully.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EchoServerCountingBytes {
        /*
         * 🧪 Zadanie 15:
         * Serwer liczy długość otrzymanej linii w bajtach (UTF-8) i odsyła
         * "BAJTY: N" zamiast treści echo. Klient wysyła "Zażółć gęślą jaźń" i
         * wypisuje otrzymaną liczbę bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GracefulLatchFailureHandling {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj scenariusz, w którym wątek serwera celowo odlicza latch
         * dopiero po Thread.sleep(2000), a klient czeka na await() z
         * timeoutem 500 ms (krótszym niż opóźnienie serwera). Obsłuż
         * przypadek await()==false wypisując czytelny komunikat i kończąc
         * main() bez próby połączenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ClientTimeoutWhenServerNeverResponds {
        /*
         * 🧪 Zadanie 17:
         * Serwer akceptuje połączenie, odczytuje wiadomość klienta, ale
         * NIGDY nie odsyła odpowiedzi (zamiast tego np. usypia się na krótko
         * i zamyka gniazdo). Klient ustawia setSoTimeout(1500) i łapie
         * SocketTimeoutException przy próbie odczytu odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MultiFieldProtocolMessage {
        /*
         * 🧪 Zadanie 18:
         * Klient wysyła "OP:ADD;A:5;B:7". Serwer parsuje 3 pola (rozdzielone
         * ";", każde jako klucz:wartość), oblicza A+B i odsyła wynik jako
         * tekst. Wypisz obliczony wynik po stronie klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ServerLogsElapsedHandlingTime {
        /*
         * 🧪 Zadanie 19:
         * Serwer mierzy (System.nanoTime()) czas od accept() do wysłania
         * odpowiedzi i wypisuje go w milisekundach po stronie serwera po
         * zakończeniu obsługi klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ClientAndServerBothLogTimestamps {
        /*
         * 🧪 Zadanie 20:
         * Zarówno klient, jak i serwer wypisują System.currentTimeMillis()
         * przy kluczowych krokach (połączenie, wysłanie, odebranie,
         * zamknięcie), budując prostą oś czasu w konsoli.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ServerAcceptsFixedNumberSequential {
        /*
         * 🧪 Zadanie 21:
         * Serwer w pętli for wykonuje DOKŁADNIE 3 razy accept() (bez wątków -
         * po kolei), za każdym razem obsługując jednego klienta osobną
         * wiadomością. Main łączy się sekwencyjnie jako 3 różni klienci,
         * dopiero po zakończeniu poprzedniego łącząc kolejnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ServerUsingInetAddressBinding {
        /*
         * 🧪 Zadanie 22:
         * Zamiast new ServerSocket(0), utwórz ServerSocket bez argumentów i
         * jawnie wywołaj bind(new InetSocketAddress(InetAddress.getLoopbackAddress(),
         * 0)) (koncepcje z Lesson01/Lesson02). Wypisz przydzielony port i
         * wykonaj standardową wymianę wiadomości z klientem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WhoisLikeLineProtocolServer {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj lokalny serwer imitujący protokół WHOIS (Lesson04): trzyma
         * Map<String,String> "fałszywy rejestr domen" (np. "example.com" ->
         * "Wlasciciel: Firma XYZ"). Klient wysyła nazwę domeny, serwer zwraca
         * dane z mapy lub "NOT FOUND".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HttpLikeLineProtocolServer {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj lokalny serwer parsujący odebraną linię jako linię startową
         * HTTP (np. "GET /strona HTTP/1.1"), wyodrębniający ścieżkę.
         * Jeśli ścieżka znajduje się w znanej mapie (np. "/" -> "Strona
         * glowna"), serwer odsyła "200 OK" + treść; w przeciwnym razie "404
         * NOT FOUND".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CalculatorServerCapstone {
        /*
         * 🧪 Zadanie 25:
         * Rozszerz wzorzec z lekcji o serwer implementujący protokół
         * kalkulatora ("ADD a b", "SUB a b", "MUL a b"), zsynchronizowany
         * przez CountDownLatch, testowany 3 kolejnymi żądaniami wysłanymi
         * przez 3 sekwencyjnie łączących się klientów (bounded pętla
         * accept()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ServerWithGracefulErrorResponses {
        /*
         * 🧪 Zadanie 26:
         * Serwer próbuje sparsować liczbę z wiadomości klienta - jeśli
         * NumberFormatException zostanie rzucony, serwer łapie go i odsyła
         * "ERROR: nieprawidlowa liczba" zamiast pozwolić watkowi obsługi się
         * wywalić. Klient testuje zarówno poprawną, jak i błędną wartość
         * (w dwóch osobnych, niezależnych uruchomieniach demo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FullClientServerDiagnosticReport {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj obiekt raportu agregujący: użyty port lokalny, adres
         * lokalny/zdalny klienta, czas przetwarzania po stronie serwera
         * (nanoTime) oraz treść wymienionej wiadomości. Po jednej interakcji
         * klient-serwer wypisz pełny raport diagnostyczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BoundedRetryClientAgainstOwnServer {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj klienta, który próbuje połączyć się z serwerem maksymalnie 3
         * razy (z krótkim odstępem, np. 200 ms), zanim serwer zdąży wystartować
         * nasłuchiwanie (celowo pomiń poprawne czekanie na CountDownLatch w tej
         * wersji). Porównaj (komentarzem/println) zachowanie z poprawnym
         * podejściem używającym await() z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SecureShutdownSequenceCapstone {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj pełną, uporządkowaną sekwencję zamknięcia: serwer po
         * zakończeniu obsługi jawnie zamyka gniazdo klienta i loguje "serwer
         * zamknal polaczenie"; klient po swoim zamknięciu sprawdza
         * socket.isClosed(). Wypisz połączony raport końcowy z obu stron.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ServerSocketFullCapstone {
        /*
         * 🧪 Zadanie 30:
         * Połącz w jednym demo: synchronizację CountDownLatch, protokół
         * kalkulatora z komendami tekstowymi (Zadanie 18/25), pomiar czasu
         * obsługi (Zadanie 19), bounded pętlę 2 sekwencyjnych klientów
         * (Zadanie 21) i obsługę błędnych danych (Zadanie 26). Na końcu
         * wypisz jeden zbiorczy raport podsumowujący obie interakcje
         * klient-serwer.
         */
        public static void main(String[] args) { }
    }
}
