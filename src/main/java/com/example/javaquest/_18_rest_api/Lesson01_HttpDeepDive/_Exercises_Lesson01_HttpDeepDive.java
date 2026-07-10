package com.example.javaquest._18_rest_api.Lesson01_HttpDeepDive;

public class _Exercises_Lesson01_HttpDeepDive {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreePartsOfHttpRequest {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wymien i krotko opisz 3 czesci
         * skladowe kazdego requestu HTTP (linia startowa, naglowki, cialo)
         * - podaj przyklad kazdej z nich dla requestu POST /users.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StartLocalHttpServerOnPortZero {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `com.sun.net.httpserver.HttpServer` na `localhost`, port 0,
         * z 1 kontekstem `/ping`, ktory zawsze odpowiada statusem 200 i
         * cialem "pong". Wypisz port, na ktorym faktycznie wystartowal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SendGetRequestAndPrintStatusCode {
        /*
         * 🧪 Zadanie 3:
         * Do serwera z Zadania 2 wyslij `HttpClient` GET /ping i wypisz
         * `statusCode()` oraz `body()` odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddCustomRequestHeaderAndReadItOnServer {
        /*
         * 🧪 Zadanie 4:
         * Do requestu z Zadania 3 dodaj naglowek "X-Client-Name: JavaQuest".
         * Po stronie serwera odczytaj go przez `exchange.getRequestHeaders()`
         * i wypisz jego wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ParseUriIntoItsComponents {
        /*
         * 🧪 Zadanie 5:
         * Dla URI "http://shop.example.com/products/17?currency=PLN" wypisz
         * osobno: scheme, host, path, query (uzyj `java.net.URI`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareGetRequestWithAndWithoutBody {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij w komentarzu, dlaczego GET zwyczajowo
         * NIE powinien miec ciala requestu, mimo ze technicznie HTTP na to
         * pozwala - podaj 1 praktyczny problem, jaki to moze powodowac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReadResponseHeadersFromServer {
        /*
         * 🧪 Zadanie 7:
         * Rozszerz serwer z Zadania 2, zeby dodawal naglowek odpowiedzi
         * "X-Powered-By: JavaQuest". Po stronie klienta odczytaj go z
         * `response.headers()` i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SendPostRequestWithStringBody {
        /*
         * 🧪 Zadanie 8:
         * Wyslij POST /ping z cialem tekstowym "hello" (
         * `HttpRequest.BodyPublishers.ofString`) - po stronie serwera
         * odczytaj `exchange.getRequestBody()` i wypisz jego zawartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MeasureContentLengthOfRequestBody {
        /*
         * 🧪 Zadanie 9:
         * Dla requestu z Zadania 8 odczytaj po stronie serwera naglowek
         * "Content-Length" i porownaj z rzeczywista dlugoscia odczytanego
         * ciala - wypisz, czy sa rowne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListStandardRequestHeaders {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wymien w komentarzu 5 standardowych naglowkow
         * requestu (np. Host, User-Agent, Accept, Content-Type,
         * Authorization) i jednym zdaniem opisz przeznaczenie kazdego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DemonstrateStatelessnessAcrossTwoRequests {
        /*
         * 🧪 Zadanie 11:
         * Wyslij 2 kolejne requesty do tego samego endpointu - w pierwszym
         * dodaj naglowek "X-Session: abc", w drugim NIE dodawaj go wcale.
         * Po stronie serwera zademonstruj, ze drugi request NIE "widzi"
         * naglowka z pierwszego (statyczna Mapa serwera pozostaje pusta
         * miedzy requestami, chyba ze SAM to zaimplementujesz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementFixedLengthResponse {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj endpoint, ktory zna z gory dlugosc odpowiedzi i
         * uzywa `sendResponseHeaders(200, dlugoscBajtow)` - zweryfikuj po
         * stronie klienta naglowek "Content-Length" w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementChunkedResponse {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj endpoint, ktory wysyla odpowiedz w 3 oddzielnych
         * kawalkach (`sendResponseHeaders(200, 0)` + kilka `write()` +
         * `flush()`) - zweryfikuj po stronie klienta naglowek
         * "Transfer-Encoding: chunked" oraz ze cale cialo dotarlo poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PrintNegotiatedHttpVersion {
        /*
         * 🧪 Zadanie 14:
         * Wyslij request do lokalnego serwera i wypisz `response.version()`
         * - w komentarzu wyjasnij, dlaczego wynik to HTTP/1.1, mimo ze
         * `HttpClient` domyslnie preferuje HTTP/2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareHttp11Http2Http3Conceptually {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: w tabeli tekstowej (komentarz) porownaj HTTP/1.1,
         * HTTP/2 i HTTP/3 pod katem: format (tekstowy/binarny), wielosc
         * requestow na 1 polaczeniu, protokol transportowy (TCP/UDP).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ReuseSameHttpClientForMultipleRequests {
        /*
         * 🧪 Zadanie 16:
         * Wyslij 5 kolejnych requestow do tego samego serwera UZYWAJAC
         * TEGO SAMEGO obiektu `HttpClient` - zmierz i wypisz czas trwania
         * wszystkich 5 requestow (System.nanoTime()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildAndParseQueryStringManually {
        /*
         * 🧪 Zadanie 17:
         * Napisz metode `String buildQueryString(Map<String,String> params)`
         * budujaca poprawny query string (np. "a=1&b=2") - i odwrotna
         * metode parsujaca go z powrotem do Map. Zweryfikuj dla min. 3 par.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_HandleMissingHeaderGracefully {
        /*
         * 🧪 Zadanie 18:
         * Po stronie klienta odczytaj naglowek odpowiedzi, ktorego serwer
         * NIE ustawia (np. "X-Nonexistent") uzywajac
         * `response.headers().firstValue(...)` - obsluz brak wartosci BEZ
         * wyjatku (Optional), wypisz przyjazny komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareTwoUrisForSameResourceDifferentQuery {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj 2 obiekty `URI` roznace sie TYLKO kolejnoscia parametrow
         * query (np. "?a=1&b=2" vs "?b=2&a=1") - sprawdz, czy `URI.equals()`
         * uznaje je za rowne, i wyjasnij w komentarzu dlaczego (lub dlaczego
         * nie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementServerThatEchoesAllHeaders {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj endpoint, ktory odsyla klientowi WSZYSTKIE
         * naglowki, jakie od niego odebral, jako cialo odpowiedzi w
         * formacie "naglowek: wartosc" (po 1 w linii). Zweryfikuj dla
         * requestu z min. 3 wlasnymi naglowkami.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MeasureConnectionReuseWithKeepAlive {
        /*
         * 🧪 Zadanie 21:
         * Wyslij 20 requestow do lokalnego serwera przy uzyciu 1
         * `HttpClient` i zmierz laczny czas - porownaj (opisowo w
         * komentarzu) z hipotetycznym scenariuszem, w ktorym KAZDY request
         * wymagalby nowego polaczenia TCP (Connection: close) - dlaczego
         * ponowne uzycie polaczenia jest szybsze?
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementServerRejectingBodyOverLimit {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj endpoint, ktory odczytuje `Content-Length` PRZED
         * wczytaniem ciala i jesli przekracza 100 bajtow, odpowiada
         * statusem 413 (Payload Too Large) BEZ wczytywania calego ciala -
         * zweryfikuj dla requestu ponizej i powyzej limitu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimulateSlowChunkedStreamingResponse {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj endpoint chunked, ktory wysyla 5 kawalkow z krotkim
         * opoznieniem (np. 50ms) miedzy kazdym - po stronie klienta uzyj
         * `HttpResponse.BodyHandlers.ofLines()`, zeby przetwarzac odpowiedz
         * STRUMIENIOWO, i wypisz kazda linie w momencie jej otrzymania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildMinimalHttpParserFromRawSocket {
        /*
         * 🧪 Zadanie 24:
         * Uzywajac surowego `java.net.Socket` (BEZ HttpClient), wyslij
         * recznie sformatowany request HTTP/1.1 (linia startowa + naglowki
         * + pusta linia) do lokalnego serwera i wypisz SUROWA odpowiedz
         * bajt po bajcie jako tekst - zademonstruj, ze HttpClient to
         * "tylko" wygodna otoczka nad tym samym protokolem tekstowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DetectAndHandleMalformedContentLength {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj endpoint, ktory celowo deklaruje bledny
         * Content-Length (inny niz faktyczna dlugosc wysylanych bajtow) -
         * zaobserwuj i opisz w komentarzu, jak zachowuje sie klient
         * (bledne dane, blokada, wyjatek?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementConditionalLoggingMiddlewarePattern {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj "middleware" jako `HttpHandler` opakowujacy inny
         * `HttpHandler` - loguj metode, sciezke i czas przetwarzania
         * KAZDEGO requestu PRZED i PO wywolaniu wlasciwego handlera, bez
         * modyfikowania logiki biznesowej samego handlera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareUriEncodingOfSpecialCharacters {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj `URI` ze sciezka zawierajaca spacje i znaki specjalne
         * (np. "/search/java quest?q=a+b") uzywajac konstruktora
         * `URI(scheme, host, path, query, fragment)` (automatyczne
         * kodowanie) - wypisz `toString()` i `toASCIIString()`, porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementHeadRequestSupport {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj endpoint obslugujacy metode HEAD - powinien
         * zwrocic TE SAME naglowki co dla GET (wlacznie z Content-Length),
         * ale BEZ ciala odpowiedzi. Zweryfikuj oba warianty (GET i HEAD)
         * na tym samym endpoincie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SimulateHttp2MultiplexingConceptually {
        /*
         * 🧪 Zadanie 29:
         * Uzywajac `ExecutorService` i 5 rownoleglych watkow, wyslij 5
         * requestow "jednoczesnie" do lokalnego serwera (kazdy z innym
         * opoznieniem po stronie serwera) - zmierz, czy laczny czas jest
         * blizszy sumie opoznien, czy najdluzszemu pojedynczemu opoznieniu.
         * W komentarzu odniesc wynik do idei multipleksowania z HTTP/2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullRequestResponseInspector {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "inspektor" - klase, ktora dla podanego
         * `HttpRequest`+`HttpResponse` wypisuje CALA anatomie obu (linia
         * startowa/statusu, wszystkie naglowki, dlugosc ciala, wersja
         * protokolu) w czytelnym, sformatowanym widoku - przetestuj na
         * min. 2 roznych requestach z tej lekcji.
         */
        public static void main(String[] args) { }
    }
}
