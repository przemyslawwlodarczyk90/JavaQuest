package com.example.javaquest._06_networking.Lesson04_SocketWhois;

public class _Exercises_Lesson04_SocketWhois {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WhoisQueryExampleOrg {
        /*
         * 🧪 Zadanie 1:
         * Połącz się z whois.iana.org:43, ustaw setSoTimeout(5000), wyślij
         * zapytanie o domenę "example.org" (zakończone "\r\n") i wypisz surową
         * odpowiedź. Obsłuż SocketTimeoutException i IOException gracefully.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WhoisQueryExampleNet {
        /*
         * 🧪 Zadanie 2:
         * Analogicznie do Zadania 1, wykonaj zapytanie WHOIS o domenę
         * "example.net" do whois.iana.org:43, z timeoutem 5000 ms i pełną
         * obsługą błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CountResponseLines {
        /*
         * 🧪 Zadanie 3:
         * Wykonaj zapytanie WHOIS o "example.com" do whois.iana.org:43 i
         * zamiast wypisywać każdą linię, policz ich liczbę i wypisz tylko
         * wynik liczby linii odpowiedzi (timeout 5000 ms, obsługa błędów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FindLineContainingKeyword {
        /*
         * 🧪 Zadanie 4:
         * W odpowiedzi WHOIS dla "example.com" znajdź pierwszą linię zawierającą
         * "whois:" (adres serwera whois odpowiedzialnego rejestru) i wypisz ją,
         * albo wypisz "nie znaleziono", jeśli takiej linii nie ma.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MeasureQueryDuration {
        /*
         * 🧪 Zadanie 5:
         * Zmierz (System.nanoTime()) całkowity czas połączenia, wysłania
         * zapytania WHOIS i odczytania pełnej odpowiedzi dla "example.com".
         * Wypisz czas w milisekundach (zmierz go także w ścieżce błędu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TimeoutTooShortDemo {
        /*
         * 🧪 Zadanie 6:
         * Połącz się z whois.iana.org:43 i ustaw celowo bardzo krótki
         * setSoTimeout(1) (1 ms), tak by odczyt niemal na pewno rzucił
         * SocketTimeoutException. Złap wyjątek i wypisz komunikat
         * demonstrujący działanie mechanizmu timeoutu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteQueryManually {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj ręcznie tablicę bajtów zapytania WHOIS dla domeny
         * "example.com" (String + "\r\n", zakodowane UTF-8) i wypisz jej
         * długość w bajtach PRZED wysłaniem jej do whois.iana.org:43.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReadFirstLineOnly {
        /*
         * 🧪 Zadanie 8:
         * Wykonaj zapytanie WHOIS dla "example.com", ale wypisz TYLKO pierwszą
         * linię odpowiedzi (jedno wywołanie readLine()), a następnie zamknij
         * połączenie bez czytania reszty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_QueryInvalidDomainSyntax {
        /*
         * 🧪 Zadanie 9:
         * Wyślij do whois.iana.org:43 zapytanie o celowo niepoprawną "domenę"
         * (String "nie domena!!"), z timeoutem 5000 ms, i wypisz dokładnie to,
         * co odpowie serwer (obsłuż błędy tak jak w lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CloseAndVerifyClosed {
        /*
         * 🧪 Zadanie 10:
         * Wykonaj zapytanie WHOIS w bloku try-with-resources, zachowując
         * referencję do Socket poza blokiem (deklaracja przed try). Po
         * zakończeniu bloku wypisz socket.isClosed(), by potwierdzić, że
         * gniazdo zostało poprawnie zamknięte.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WhoisForMultipleDomains {
        /*
         * 🧪 Zadanie 11:
         * Dla tablicy domen {"example.com", "example.org", "example.net"}
         * wykonaj zapytanie WHOIS osobno dla każdej (osobny try-catch per
         * domena, tak by błąd jednej nie przerwał pętli) i wypisz liczbę linii
         * odpowiedzi dla każdej domeny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExtractRegistrarField {
        /*
         * 🧪 Zadanie 12:
         * Dla odpowiedzi WHOIS "example.com" znajdź i wypisz wartość pola
         * zaczynającego się od "refer:" lub "whois:" (informacja o
         * odpowiedzialnym rejestrze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WhoisResponseToList {
        /*
         * 🧪 Zadanie 13:
         * Zbierz wszystkie linie odpowiedzi WHOIS dla "example.com" do
         * List<String>, a następnie wypisz tylko linie NIEPUSTE (pomiń
         * puste separatory).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RetryOnTimeout {
        /*
         * 🧪 Zadanie 14:
         * Napisz zapytanie WHOIS dla "example.com" z ograniczoną liczbą
         * ponownych prób (max 2) w razie SocketTimeoutException, ustawiając
         * timeout 3000 ms na każdą próbę. Wypisz, przy której próbie się
         * udało (lub że wszystkie zawiodły).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareTwoDomainsResponses {
        /*
         * 🧪 Zadanie 15:
         * Wykonaj zapytania WHOIS dla "example.com" i "example.org", policz
         * liczbę linii odpowiedzi każdej z nich i wypisz, która odpowiedź
         * była dłuższa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildQueryHelperMethod {
        /*
         * 🧪 Zadanie 16:
         * Wydziel reużywalną metodę queryWhois(String domain) zwracającą
         * String z pełną odpowiedzią WHOIS albo "BLAD: <opis>" w razie
         * niepowodzenia. Wywołaj ją dla "example.com" i "example.org".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WhoisWithCustomPort {
        /*
         * 🧪 Zadanie 17:
         * Zamiast konstruktora Socket(host, port), użyj bezargumentowego
         * Socket() i jawnego socket.connect(new InetSocketAddress(host, 43),
         * connectTimeoutMs) (np. 4000 ms), demonstrując oddzielny timeout
         * połączenia od timeoutu odczytu (setSoTimeout). Wykonaj pełne
         * zapytanie WHOIS dla "example.com".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SummarizeResponseStats {
        /*
         * 🧪 Zadanie 18:
         * Dla odpowiedzi WHOIS "example.com" oblicz: liczbę linii, łączną
         * liczbę znaków oraz najdłuższą linię. Wypisz podsumowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SaveResponseToStringBuilder {
        /*
         * 🧪 Zadanie 19:
         * Zbierz całą odpowiedź WHOIS "example.com" do jednego StringBuildera
         * (linia po linii, z separatorem "\n"), a dopiero na końcu wypisz
         * cały zebrany tekst jednym println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GracefulNoInternetFallback {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę Optional<String> queryWhoisSafe(String domain), łapiącą
         * osobno SocketTimeoutException i ogólny IOException. Wywołaj ją dla
         * "example.com" i wypisz wynik na podstawie tego, czy Optional jest
         * obecny.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WhoisUsingInetAddressResolution {
        /*
         * 🧪 Zadanie 21:
         * Najpierw rozwiąż InetAddress.getByName("whois.iana.org") (łapiąc
         * UnknownHostException), wypisz uzyskany adres IP, a następnie
         * połącz Socket z TYM rozwiązanym InetAddress i portem 43, by wysłać
         * zapytanie WHOIS o "example.com".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiDomainReportTable {
        /*
         * 🧪 Zadanie 22:
         * Dla domen {"example.com", "example.org", "example.net"} zbuduj
         * tabelę raportu: domena, sukces (true/false), liczba linii
         * odpowiedzi, czas trwania w ms. Wypisz sformatowaną tabelę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WhoisLineClassifier {
        /*
         * 🧪 Zadanie 23:
         * Dla odpowiedzi WHOIS "example.com" sklasyfikuj każdą linię jako
         * "KOMENTARZ" (zaczyna się od "%") lub "DANE", policz linie każdej
         * kategorii i wypisz podsumowanie liczbowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExtractAllKeyValuePairs {
        /*
         * 🧪 Zadanie 24:
         * Sparsuj odpowiedź WHOIS "example.com" jako pary klucz-wartość (linie
         * postaci "klucz: wartosc", podział po pierwszym ":", pomijając linie
         * komentarza zaczynające się od "%") do Map<String,String>. Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TimeoutEscalationStrategy {
        /*
         * 🧪 Zadanie 25:
         * Wykonaj zapytanie WHOIS "example.com" z timeoutem 1000 ms; jeśli
         * rzuci SocketTimeoutException, spróbuj ponownie z timeoutem 4000 ms.
         * Wypisz, która próba (krótka czy długa) się powiodła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_WhoisCapstoneReport {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj klasę/rekord WhoisReport (domena, sukces, liczbaLinii,
         * mapaKluczWartosc) łączącą techniki z Zadań 13, 18 i 24. Wygeneruj
         * raport dla "example.com" i "example.org" i wypisz oba w pełnym,
         * czytelnym formacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareLocalEchoVsRealWhois {
        /*
         * 🧪 Zadanie 27:
         * Uruchom lokalny serwer echo (jak w Lesson03, ServerSocket(0) w
         * wątku demon) i zmierz czas jego round-trip, a następnie zmierz czas
         * pełnego zapytania WHOIS do whois.iana.org dla "example.com". Wypisz
         * oba czasy obok siebie i porównaj (lokalny powinien być znacznie
         * szybszy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ManualHandshakeLoggingClient {
        /*
         * 🧪 Zadanie 28:
         * Wykonując zapytanie WHOIS dla "example.com", zapisuj do
         * List<String> znaczniki czasu (System.currentTimeMillis()) kluczowych
         * kroków: "connect", "write", "first-read", "eof". Na końcu wypisz
         * pełną oś czasu diagnostyczną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RobustWhoisClient {
        /*
         * 🧪 Zadanie 29:
         * Napisz klasę WhoisClient z metodą lookup(String domain, int
         * timeoutMs), która wewnętrznie ponawia zapytanie maksymalnie 2 razy,
         * loguje przyczynę każdej porażki i zwraca wynik albo przyjazny
         * komunikat błędu. Zademonstruj dla "example.com" (poprawny przypadek)
         * i dla celowo bardzo krótkiego timeoutu (np. 1 ms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WhoisAndInetAddressCombinedDiagnostics {
        /*
         * 🧪 Zadanie 30:
         * Dla domeny "example.com" wykonaj pełną diagnostykę: najpierw sprawdź
         * InetAddress.getByName("whois.iana.org").isReachable(2000) (Lesson02);
         * jeśli host nieosiągalny, pomiń zapytanie WHOIS z czytelnym
         * komunikatem; w przeciwnym razie wykonaj pełne zapytanie WHOIS i
         * wypisz wynik.
         */
        public static void main(String[] args) { }
    }
}
