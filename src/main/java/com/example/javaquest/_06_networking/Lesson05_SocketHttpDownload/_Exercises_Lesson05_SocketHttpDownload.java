package com.example.javaquest._06_networking.Lesson05_SocketHttpDownload;

public class _Exercises_Lesson05_SocketHttpDownload {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicGetRequestExampleOrg {
        /*
         * 🧪 Zadanie 1:
         * Ręcznie zbuduj zapytanie "GET / HTTP/1.1" z nagłówkami Host i
         * Connection: close dla hosta "example.org" na porcie 80, wyślij je
         * przez Socket (setSoTimeout(5000)) i wypisz surową odpowiedź.
         * Obsłuż SocketTimeoutException i IOException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetSpecificPath {
        /*
         * 🧪 Zadanie 2:
         * Wykonaj ręczne zapytanie GET do "example.com":80, ale dla ścieżki
         * "/index.html" zamiast "/". Wypisz surową odpowiedź (timeout 5000 ms,
         * obsługa błędów jak w lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PrintOnlyStatusLine {
        /*
         * 🧪 Zadanie 3:
         * Wykonaj zapytanie GET do "example.com":80, ale wypisz TYLKO
         * pierwszą linię odpowiedzi (linię statusu, np. "HTTP/1.1 200 OK"),
         * ignorując resztę odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CountResponseHeaderLines {
        /*
         * 🧪 Zadanie 4:
         * Dla odpowiedzi z "example.com":80 policz, ile linii nagłówków
         * pojawia się PRZED pierwszą pustą linią, i wypisz tę liczbę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExtractContentType {
        /*
         * 🧪 Zadanie 5:
         * W nagłówkach odpowiedzi z "example.com":80 znajdź linię zaczynającą
         * się od "Content-Type:" i wypisz jej wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExtractContentLength {
        /*
         * 🧪 Zadanie 6:
         * W nagłówkach odpowiedzi z "example.com":80 znajdź linię
         * "Content-Length:", sparsuj wartość jako int i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RequestWithoutConnectionClose {
        /*
         * 🧪 Zadanie 7:
         * Wyślij zapytanie GET do "example.com":80 BEZ nagłówka
         * "Connection: close" (tylko linia startowa + Host). Ustaw
         * setSoTimeout(3000) i wypisz to, co udało się odczytać zanim odczyt
         * przerwie się wyjątkiem SocketTimeoutException (serwer może nie
         * zamknąć połączenia samodzielnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ManualRequestBuilderMethod {
        /*
         * 🧪 Zadanie 8:
         * Wydziel metodę buildGetRequest(String host, String path) zwracającą
         * gotowy String zapytania HTTP GET (z Host i Connection: close).
         * Przetestuj ją dla ścieżek "/" i "/index.html".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SeparateHeadersFromBody {
        /*
         * 🧪 Zadanie 9:
         * Pobierz pełną surową odpowiedź z "example.com":80, a następnie
         * podziel ją na dwie części względem pierwszej pustej linii: nagłówki
         * i ciało (body). Wypisz obie części osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_HeadRequestInsteadOfGet {
        /*
         * 🧪 Zadanie 10:
         * Wyślij zapytanie "HEAD / HTTP/1.1" (zamiast GET) do "example.com":80.
         * Wypisz odpowiedź i skomentuj (println), że ciało powinno być puste.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ParseStatusCode {
        /*
         * 🧪 Zadanie 11:
         * Ze wypisanej linii statusu (np. "HTTP/1.1 200 OK") wyodrębnij
         * numeryczny kod statusu jako int (split po spacji) i wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_HeaderMapParser {
        /*
         * 🧪 Zadanie 12:
         * Sparsuj WSZYSTKIE linie nagłówków odpowiedzi z "example.com":80
         * (podział po pierwszym ":") do Map<String,String> i wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_Non200Handling {
        /*
         * 🧪 Zadanie 13:
         * Wykonaj zapytanie GET do "example.com":80 dla ścieżki
         * "/nieistniejaca-strona-xyz". Sparsuj kod statusu i wypisz różny
         * komunikat w zależności od tego, czy to 200, 404 czy inny kod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareGetVsHead {
        /*
         * 🧪 Zadanie 14:
         * Wykonaj osobno zapytanie GET i HEAD do "example.com":80 (dwa
         * niezależne połączenia). Porównaj wartość nagłówka Content-Length
         * zwróconą przez oba oraz rzeczywistą długość ciała odebranego
         * z GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleSequentialRequests {
        /*
         * 🧪 Zadanie 15:
         * Dla ścieżek {"/", "/index.html"} wykonaj GET do "example.com":80,
         * otwierając NOWE połączenie dla każdej ścieżki (bo używamy Connection:
         * close). Wypisz kod statusu każdego zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TimingDownload {
        /*
         * 🧪 Zadanie 16:
         * Zmierz (System.nanoTime()) łączny czas połączenia, wysłania
         * zapytania GET i odczytania pełnej odpowiedzi z "example.com":80.
         * Wypisz czas w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BodySizeReport {
        /*
         * 🧪 Zadanie 17:
         * Pobierz odpowiedź z "example.com":80, wydziel samo ciało (body) i
         * policz liczbę znaków w nim. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UserAgentHeader {
        /*
         * 🧪 Zadanie 18:
         * Dodaj do zapytania GET własny nagłówek
         * "User-Agent: JavaQuestClient/1.0" (po Host, przed pustą linią) przy
         * połączeniu z "example.com":80 i zweryfikuj, że serwer nadal
         * odpowiada poprawnie (kod 200).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RequestBuilderWithHeadersMap {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj metodę z Zadania 8 do buildGetRequest(String host, String
         * path, Map<String,String> extraHeaders), dopisującą dodatkowe
         * nagłówki po Host/Connection. Przetestuj z mapą zawierającą
         * "User-Agent" i "Accept".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GracefulPortClosedHandling {
        /*
         * 🧪 Zadanie 20:
         * Spróbuj połączyć się z "example.com" na porcie 81 (prawdopodobnie
         * zamkniętym/filtrowanym), używając Socket() + connect(new
         * InetSocketAddress(host, 81), 3000) (osobny timeout połączenia).
         * Złap wyjątek i wypisz czytelny komunikat o niepowodzeniu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullHttpClientHelperMethod {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę pomocniczą httpGet(String host, String path) zwracającą
         * rekord HttpResponse(int status, Map<String,String> headers, String
         * body). Użyj jej dla dwóch zapytań: "example.com"/"/" i
         * "example.org"/"/".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RedirectDetection {
        /*
         * 🧪 Zadanie 22:
         * Po sparsowaniu kodu statusu odpowiedzi z "example.com":80, sprawdź,
         * czy jest to kod przekierowania (301, 302, 303, 307 lub 308) i jeśli
         * tak, wypisz wartość nagłówka "Location" (o ile występuje);
         * w przeciwnym razie wypisz, że przekierowania nie było.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CombineWhoisAndHttp {
        /*
         * 🧪 Zadanie 23:
         * Dla domeny "example.com" wykonaj najpierw zapytanie WHOIS (jak w
         * Lesson04, do whois.iana.org:43), a następnie ręczne zapytanie HTTP
         * GET na porcie 80. Wypisz oba wyniki w jednym połączonym raporcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_InetAddressPreResolution {
        /*
         * 🧪 Zadanie 24:
         * Rozwiąż "example.com" przez InetAddress.getByName (Lesson02),
         * wypisz uzyskany adres IP, a następnie połącz Socket bezpośrednio z
         * tym InetAddress i portem 80, wykonując ręczne zapytanie GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DownloadAndSaveToFile {
        /*
         * 🧪 Zadanie 25:
         * Pobierz odpowiedź GET z "example.com":80, wydziel samo ciało (body)
         * i zapisz je do lokalnego pliku (np. FileWriter/BufferedWriter z
         * rozdziału _04_io) zamiast wypisywać na konsolę. Odczytaj plik z
         * powrotem i wypisz jego rozmiar, by zweryfikować zapis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HeaderCaseInsensitiveLookup {
        /*
         * 🧪 Zadanie 26:
         * Sparsuj nagłówki odpowiedzi z "example.com":80 do mapy, a następnie
         * napisz metodę getHeaderIgnoreCase(Map<String,String> headers, String
         * name), która znajduje wartość niezależnie od wielkości liter nazwy
         * nagłówka. Przetestuj dla "content-type", "Content-Type" i
         * "CONTENT-TYPE".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RetryWithBackoff {
        /*
         * 🧪 Zadanie 27:
         * Wykonaj GET do "example.com":80 z maksymalnie 2 ponowieniami przy
         * SocketTimeoutException/IOException, używając rosnących timeoutów
         * (np. 2000 ms, potem 4000 ms). Wypisz, przy której próbie zapytanie
         * się powiodło.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultiHostComparisonReport {
        /*
         * 🧪 Zadanie 28:
         * Wykonaj ręczne zapytanie GET do "example.com" i "example.org" (port
         * 80, ścieżka "/"). Zbuduj tabelę porównawczą: host, kod statusu,
         * Content-Length, czas trwania w ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ChunkedOrContentLengthDetector {
        /*
         * 🧪 Zadanie 29:
         * Po pobraniu nagłówków odpowiedzi z "example.com":80 sprawdź, czy
         * występuje nagłówek "Transfer-Encoding: chunked" czy raczej
         * "Content-Length", i wypisz, którą strategię przesyłania ciała
         * zastosował serwer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_HttpDownloadCapstone {
        /*
         * 🧪 Zadanie 30:
         * Napisz metodę downloadPage(String host, String path, String
         * localFilePath) łączącą: budowanie zapytania (Zadanie 19), retry z
         * rosnącym timeoutem (Zadanie 27), wcześniejsze rozwiązanie adresu
         * przez InetAddress (Zadanie 24) i zapis ciała do pliku (Zadanie 25).
         * Metoda zwraca sformatowany raport podsumowujący. Wywołaj ją dla
         * "example.com"/"/" i wypisz raport końcowy.
         */
        public static void main(String[] args) { }
    }
}
