package com.example.javaquest._06_networking.Lesson11_HttpProtocol;

public class _Exercises_Lesson11_HttpProtocol {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ParseRequestLine {
        /*
         * 🧪 Zadanie 1:
         * Mając surowy tekst "GET /users/42 HTTP/1.1\nHost: example.com\n",
         * pobierz jego pierwszą linię i rozbij ją (split(" ")) na metodę,
         * ścieżkę i wersję protokołu. Wypisz wszystkie trzy wartości osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ParseStatusLine {
        /*
         * 🧪 Zadanie 2:
         * Mając surowy tekst "HTTP/1.1 404 Not Found", rozbij go na wersję
         * protokołu, kod liczbowy (Integer.parseInt) oraz frazę opisową
         * (może zawierać spacje). Wypisz wszystkie trzy elementy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ClassifyStatusCodes {
        /*
         * 🧪 Zadanie 3:
         * Mając tablicę kodów {100, 200, 301, 404, 500, 201, 403}, napisz
         * metodę classify(int code) zwracającą nazwę rodziny ("1xx -
         * informacyjne", "2xx - sukces", itd.) i wypisz wynik dla każdego
         * kodu z tablicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParseHeadersBlock {
        /*
         * 🧪 Zadanie 4:
         * Mając blok tekstu "Content-Type: text/html\nHost: example.com\n
         * User-Agent: JavaQuest/1.0\n", rozbij go linia po linii i dla
         * każdej linii rozdziel po pierwszym ":" na nazwę i wartość nagłówka,
         * budując Map<String,String>. Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CheckIdempotency {
        /*
         * 🧪 Zadanie 5:
         * Mając tablicę metod {"GET","POST","PUT","PATCH","DELETE"}, napisz
         * metodę isIdempotent(String method) zwracającą true/false zgodnie
         * z regułami z lekcji, i wypisz wynik dla każdej metody z tablicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_Explain401Vs403 {
        /*
         * 🧪 Zadanie 6:
         * Mając listę kodów {401, 403, 401, 403}, dla każdego wypisz krótkie
         * wyjaśnienie zgodne z lekcją: 401 = "nie wiem kim jesteś", 403 =
         * "wiem kim jesteś, ale brak dostępu".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BuildRawRequestText {
        /*
         * 🧪 Zadanie 7:
         * Napisz metodę buildRequestLine(String method, String path, String host),
         * która zwraca sformatowany tekst w stylu:
         * "GET /users/42 HTTP/1.1\nHost: example.com\n". Wywołaj ją dla
         * kilku różnych zestawów parametrów i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BuildRawResponseText {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę buildResponseText(int code, String phrase, String body),
         * która oblicza Content-Length na podstawie długości body (w bajtach
         * UTF-8) i zwraca pełny tekst odpowiedzi w formacie z lekcji
         * (linia statusu + nagłówki + pusta linia + ciało). Przetestuj dla
         * kodu 200 i ciała '{"id":1}'.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExtractCookieNameValue {
        /*
         * 🧪 Zadanie 9:
         * Mając surowy nagłówek "Set-Cookie: SESSIONID=abc123; Path=/;
         * HttpOnly", wyodrębnij samą parę nazwa=wartość ciasteczka
         * ("SESSIONID" i "abc123"), ignorując atrybuty Path/HttpOnly. Wypisz
         * obie wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CountStatusFamilies {
        /*
         * 🧪 Zadanie 10:
         * Mając listę kodów {200, 201, 301, 404, 500, 200, 403, 302, 500},
         * policz, ile kodów należy do każdej rodziny (2xx, 3xx, 4xx, 5xx)
         * i wypisz podsumowanie w formie Map<String,Integer>.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LiveStatusClassification {
        /*
         * 🧪 Zadanie 11:
         * Uruchom lokalny HttpServer (port 0) z kontekstem "/status" (kod
         * 200). Wyślij żądanie GET przez java.net.http.HttpClient, odczytaj
         * statusCode() i sklasyfikuj go metodą z Zadania 3. Zatrzymaj
         * serwer na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_QueryDrivenStatusCode {
        /*
         * 🧪 Zadanie 12:
         * Kontekst "/kod" niech odczytuje parametr query "?code=XXX" (z
         * exchange.getRequestURI().getQuery()) i zwraca dokładnie ten kod
         * statusu. Wyślij żądania z code=200, code=404, code=500 i wypisz
         * uzyskane kody odpowiedzi dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_HeaderRoundTripViaHttpClient {
        /*
         * 🧪 Zadanie 13:
         * Wyślij żądanie HttpRequest z niestandardowymi nagłówkami
         * "User-Agent" i "X-Test". Kontekst serwera odczytuje je i odsyła
         * ich wartości w ciele odpowiedzi (jak w lekcji). Zweryfikuj po
         * stronie klienta, że odebrane wartości zgadzają się z wysłanymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ManualCookieSessionFlow {
        /*
         * 🧪 Zadanie 14:
         * Kontekst "/login" ustawia nagłówek odpowiedzi Set-Cookie z jakąś
         * wartością SESSIONID. Klient odczytuje ją przez
         * response.headers().firstValue("Set-Cookie") i ręcznie dołącza
         * jako nagłówek "Cookie" do KOLEJNEGO żądania pod "/profil", który
         * zwraca "Zalogowano" tylko gdy nagłówek Cookie jest obecny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DescribeStatusHelper {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę describeStatus(int code) zwracającą tekst w stylu
         * "2xx Sukces". Uruchom 3 różne konteksty lokalnego serwera zwracające
         * różne kody, pobierz je HttpClientem i wypisz opis każdego przez
         * describeStatus.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PublicVsSecureEndpoint {
        /*
         * 🧪 Zadanie 16:
         * Zarejestruj konteksty "/publiczny" (zawsze 200) i "/bezpieczny"
         * (zawsze 401). Wyślij żądania do obu i na podstawie RZECZYWIŚCIE
         * otrzymanego kodu wypisz odpowiednie wyjaśnienie z Zadania 6
         * (dynamicznie, na podstawie response.statusCode()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InspectEffectiveHeaders {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj HttpRequest do lokalnego serwera bez jawnego ustawiania
         * nagłówka Host. Po stronie serwera odczytaj i wypisz WSZYSTKIE
         * nagłówki żądania, jakie faktycznie dotarły (w tym automatycznie
         * dodane przez HttpClient, np. Host).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_IdempotencyTableFromRealRequests {
        /*
         * 🧪 Zadanie 18:
         * Wyślij do lokalnego serwera żądania GET (rzeczywiście) oraz
         * zasymuluj (bez faktycznego wysyłania) sprawdzenie metod PUT/PATCH/
         * DELETE metodą isIdempotent z Zadania 5. Wypisz tabelę: metoda,
         * czy wysłano naprawdę, czy jest idempotentna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_StatelessCounterDemo {
        /*
         * 🧪 Zadanie 19:
         * Kontekst "/licznik" trzyma w pamięci serwera licznik żądań
         * (AtomicInteger) i zwraca jego aktualną wartość w ciele odpowiedzi.
         * Wyślij 3 osobne żądania i wypisz kolejne odczytane wartości –
         * skomentuj (println), że każde POJEDYNCZE żądanie HTTP jest
         * bezstanowe, a "pamięć" żyje wyłącznie po stronie aplikacji serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RequestResponseLogger {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę logRequestResponse(HttpRequest request, HttpResponse<String> response),
         * która wypisuje sformatowany, "surowy" tekst przypominający
         * request/response z lekcji (linia żądania/statusu + nagłówki +
         * ciało). Wywołaj ją dla żądania GET do lokalnego serwera.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullSessionSimulation {
        /*
         * 🧪 Zadanie 21:
         * Kontekst "/login" bez nagłówka Cookie generuje nowy SESSIONID,
         * zapisuje go w mapie serwera (sessionId -> null) i ustawia
         * Set-Cookie. Kontekst "/ustaw-imie?imie=Jan" (z Cookie w żądaniu)
         * zapisuje imię pod danym sessionId. Kontekst "/kim-jestem" (z tym
         * samym Cookie) zwraca zapisane imię. Zasymuluj cały przepływ
         * trzema kolejnymi żądaniami klienta, ręcznie przenosząc ciasteczko
         * między nimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_StatusDecisionEngine {
        /*
         * 🧪 Zadanie 22:
         * Zarejestruj 5 kontekstów zwracających kody 200, 201, 400, 404, 500.
         * Wyślij żądania do wszystkich, zbierz kody, pogrupuj po rodzinie
         * (2xx/4xx/5xx) i wypisz raport z licznikami ORAZ listą ścieżek,
         * które powinny być ponowione przez klienta (tylko 5xx).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ManualRedirectFollowingWithHttpClient {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj HttpClient z .followRedirects(HttpClient.Redirect.NEVER).
         * Kontekst "/a" zwraca 302 z nagłówkiem Location "/b", a "/b" zwraca
         * 200 "OK". Wyślij żądanie do "/a", odczytaj ręcznie kod i Location,
         * następnie wyślij DRUGIE żądanie pod odczytany adres i wypisz
         * finalny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ContentNegotiationFlow {
        /*
         * 🧪 Zadanie 24:
         * Kontekst "/dane" zwraca różne ciało/Content-Type w zależności od
         * nagłówka żądania Accept ("application/json" vs "text/plain").
         * Wyślij 2 żądania z różnymi wartościami Accept i zweryfikuj, że
         * zarówno Content-Type, jak i treść odpowiedzi są zgodne z oczekiwaniem
         * dla każdego przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AuthorizationHeaderFlow {
        /*
         * 🧪 Zadanie 25:
         * Kontekst "/chronione" sprawdza nagłówek "Authorization": bez niego
         * zwraca 401, z wartością "Bearer secrettoken" zwraca 200 "OK",
         * z inną wartością zwraca 403. Wyślij po kolei 3 żądania (brak
         * nagłówka, poprawny token, zły token) i wypisz uzyskane kody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TimingReportAcrossEndpoints {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj 4 konteksty zwracające różne kody (200, 201, 400, 500).
         * Wyślij żądania do wszystkich, mierząc czas każdego (System.nanoTime)
         * i wypisz raport posortowany od najszybszego do najwolniejszego,
         * z kodem statusu i czasem w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DiscoveryEndpointAllowHeader {
        /*
         * 🧪 Zadanie 27:
         * Kontekst "/zasob" niech dla metody OPTIONS zwraca kod 204 z
         * nagłówkiem odpowiedzi "Allow: GET, POST, DELETE" (bez ciała), a dla
         * GET zwykłą treść. Wyślij żądanie OPTIONS i wypisz odczytaną wartość
         * nagłówka Allow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ParseThenSendRawRequest {
        /*
         * 🧪 Zadanie 28:
         * Mając surowy tekst żądania (np. "GET /status HTTP/1.1\nHost:
         * localhost\n"), sparsuj go metodą z Zadania 1 na metodę i ścieżkę,
         * a następnie NA PODSTAWIE tych sparsowanych wartości zbuduj i
         * wyślij prawdziwe żądanie HttpClientem pod lokalny serwer,
         * weryfikując odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CookieManagerAutomaticJar {
        /*
         * 🧪 Zadanie 29:
         * Skonfiguruj HttpClient z dołączonym java.net.CookieManager
         * (CookiePolicy.ACCEPT_ALL). Kontekst "/licznik-sesji" ustawia
         * ciasteczko przy pierwszym żądaniu i zwiększa powiązany z nim
         * licznik przy każdym kolejnym. Wyślij 3 żądania TYM SAMYM klientem
         * i zweryfikuj, że licznik rośnie automatycznie – bez ręcznego
         * przepisywania nagłówka Cookie (w przeciwieństwie do Zadania 14/21).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniBrowserLoginFlow {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj klasę MiniBrowser opakowującą HttpClient +
         * CookieManager z metodami login(String user), fetchProtected() oraz
         * logout(). Lokalny serwer symuluje prosty mechanizm logowania przez
         * ciasteczka (jak w Zadaniu 21/29). Wykonaj pełen scenariusz: login
         * -> pobranie chronionego zasobu (sukces) -> logout -> ponowna próba
         * pobrania chronionego zasobu (oczekiwane 401) i wypisz raport
         * PASS/FAIL dla każdego kroku.
         */
        public static void main(String[] args) { }
    }
}
