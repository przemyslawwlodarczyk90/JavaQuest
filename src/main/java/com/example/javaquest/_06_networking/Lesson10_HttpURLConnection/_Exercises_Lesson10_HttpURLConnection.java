package com.example.javaquest._06_networking.Lesson10_HttpURLConnection;

public class _Exercises_Lesson10_HttpURLConnection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_GetResponseCodeAndMessage {
        /*
         * 🧪 Zadanie 1:
         * Uruchom lokalny com.sun.net.httpserver.HttpServer (port 0) z
         * kontekstem "/status" zwracającym kod 204 (bez treści). Połącz się
         * przez HttpURLConnection, wywołaj setRequestMethod("GET") i wypisz
         * getResponseCode() oraz getResponseMessage(). Zatrzymaj serwer na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SimpleGetWithBody {
        /*
         * 🧪 Zadanie 2:
         * Kontekst "/greet" zwraca kod 200 i tekst "Hello, JavaQuest!".
         * Wykonaj żądanie GET, wypisz kod odpowiedzi oraz treść odczytaną
         * przez getInputStream().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PostWithBodyAndCode201 {
        /*
         * 🧪 Zadanie 3:
         * Kontekst "/echo" (jak w lekcji) odsyła z powrotem otrzymane ciało
         * z kodem 201. Wyślij POST z tekstem "Testowa wiadomość" jako ciałem
         * żądania, wypisz getResponseCode() (oczekiwane 201) oraz odebrane
         * ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DeleteMethod {
        /*
         * 🧪 Zadanie 4:
         * Kontekst "/resource" niech obsługuje metodę DELETE, zwracając kod
         * 200 i tekst "Usunieto". Wyślij żądanie z setRequestMethod("DELETE")
         * i wypisz kod odpowiedzi oraz treść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DefaultMethodIsGet {
        /*
         * 🧪 Zadanie 5:
         * Otwórz HttpURLConnection BEZ wywoływania setRequestMethod(...) i
         * wypisz connection.getRequestMethod() – zaobserwuj, że domyślną
         * metodą jest "GET".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PutWithPayload {
        /*
         * 🧪 Zadanie 6:
         * Kontekst "/dane" niech obsługuje PUT, odczytując ciało żądania i
         * odsyłając je z dopiskiem "Zaktualizowano: " oraz kodem 200. Wyślij
         * PUT z setDoOutput(true) i zapisz przykładowe dane, wypisz odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplicitDisconnect {
        /*
         * 🧪 Zadanie 7:
         * Wykonaj proste żądanie GET do dowolnego kontekstu, odczytaj
         * odpowiedź, a następnie jawnie wywołaj connection.disconnect().
         * Wypisz komunikat potwierdzający, że nie wystąpił żaden wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReadErrorStreamFor404 {
        /*
         * 🧪 Zadanie 8:
         * Wyślij żądanie GET pod ścieżkę, dla której serwer NIE zarejestrował
         * kontekstu (oczekiwany kod 404 z domyślnego handlera HttpServer).
         * Odczytaj treść błędu przez connection.getErrorStream() (NIE przez
         * getInputStream() – to rzuciłoby wyjątek) i wypisz kod oraz treść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_HeaderFieldsOnHttpUrlConnection {
        /*
         * 🧪 Zadanie 9:
         * Odczytaj connection.getHeaderFields() na obiekcie HttpURLConnection
         * (dokładnie jak w Lesson09 dla URLConnection) i wypisz wszystkie
         * nagłówki – potwierdź, że API jest identyczne, bo HttpURLConnection
         * to podtyp URLConnection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TimeoutsOnHealthyServer {
        /*
         * 🧪 Zadanie 10:
         * Ustaw setConnectTimeout(3000) i setReadTimeout(3000) na połączeniu
         * do szybkiego lokalnego serwera. Wypisz oba ustawienia oraz
         * potwierdź, że odpowiedź (getResponseCode()) i tak przychodzi
         * natychmiast, bo serwer jest lokalny i szybki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ReusableReadBodyHelper {
        /*
         * 🧪 Zadanie 11:
         * Napisz metodę readBody(HttpURLConnection connection) (podobną do
         * tej z teorii lekcji), która wybiera getInputStream() lub
         * getErrorStream() w zależności od kodu odpowiedzi. Użyj jej dla
         * żądań GET, POST i żądania do nieistniejącej ścieżki (404).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_PostJsonEcho {
        /*
         * 🧪 Zadanie 12:
         * Wyślij POST na kontekst "/echo" z ciałem '{"name":"Ola"}' i
         * nagłówkiem "Content-Type: application/json; charset=UTF-8".
         * Wypisz kod odpowiedzi i sprawdź (contains), czy odebrane ciało
         * zawiera oryginalny tekst JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UnsupportedMethodReturns405 {
        /*
         * 🧪 Zadanie 13:
         * Kontekst obsługuje tylko GET/POST (jak w lekcji, kod 405 dla
         * innych metod). Wyślij żądanie z setRequestMethod("PUT") pod ten
         * sam kontekst i wypisz kod odpowiedzi (oczekiwane 405) oraz
         * getResponseMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CollectResponseCodesPerMethod {
        /*
         * 🧪 Zadanie 14:
         * Dla tego samego kontekstu (obsługującego tylko GET/POST) wyślij po
         * kolei żądania GET, POST oraz PUT, zbierając wyniki do
         * Map<String,Integer> (metoda -> kod odpowiedzi). Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_InputStreamThrowsOnErrorCode {
        /*
         * 🧪 Zadanie 15:
         * Wyślij żądanie kończące się kodem 405 (jak w Zadaniu 13). Spróbuj
         * odczytać ciało przez getInputStream() i złap wynikający z tego
         * IOException, a następnie odczytaj poprawnie treść przez
         * getErrorStream() i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SequentialRequestsTiming {
        /*
         * 🧪 Zadanie 16:
         * Wyślij 5 kolejnych żądań GET do tego samego kontekstu (za każdym
         * razem NOWY obiekt HttpURLConnection, bo połączenie nie jest
         * ponownie użyte). Zmierz łączny czas (System.nanoTime) i wypisz go
         * wraz z listą uzyskanych kodów odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RedirectFollowingToggle {
        /*
         * 🧪 Zadanie 17:
         * Kontekst "/stary" zwraca kod 302 z nagłówkiem Location wskazującym
         * na "/nowy", a "/nowy" zwraca 200 "OK". Wyślij żądanie do "/stary"
         * z domyślnym setInstanceFollowRedirects(true) – oczekiwany finalny
         * kod 200. Następnie powtórz z setInstanceFollowRedirects(false) –
         * oczekiwany kod 302 i odczytany nagłówek Location.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_EmptyBodyPost {
        /*
         * 🧪 Zadanie 18:
         * Wyślij POST na kontekst "/echo" z setDoOutput(true), ale bez
         * zapisania żadnych bajtów do getOutputStream() (pusty strumień
         * wyjściowy). Wypisz kod odpowiedzi i odebrane (puste) ciało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_EchoReceivedLengthHeader {
        /*
         * 🧪 Zadanie 19:
         * Kontekst serwera niech odczyta nagłówek żądania "Content-Length"
         * (exchange.getRequestHeaders()) i odeśle jego wartość jako
         * niestandardowy nagłówek odpowiedzi "X-Received-Length". Klient
         * wysyła POST z konkretnym ciałem i weryfikuje (equals), że wartość
         * z X-Received-Length zgadza się z długością wysłanych bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SendJsonHelper {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę sendJson(String urlString, String jsonBody) zwracającą
         * kod odpowiedzi po wysłaniu POST z Content-Type application/json.
         * Przetestuj ją na kontekście "/echo" z dwoma różnymi przykładowymi
         * ciałami JSON.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RetryOn5xx {
        /*
         * 🧪 Zadanie 21:
         * Kontekst "/niestabilny" niech zwraca kod 500 dla pierwszych 2
         * wywołań (licznik w handlerze), a 200 dla trzeciego. Zaimplementuj
         * pętlę klienta ponawiającą żądanie (nowe HttpURLConnection za
         * każdym razem) maksymalnie 3 razy, kończącą się sukcesem i
         * wypisującą liczbę wykonanych prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TinyRestClientCrudFlow {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj w handlerze lokalnego serwera prostą "bazę danych"
         * w pamięci (np. Map<Integer,String> notatek) obsługującą: GET
         * (lista wszystkich), POST (dodanie nowej, zwraca wygenerowane id),
         * DELETE po ścieżce z id w query (np. "?id=1"). Napisz klasę-wrapper
         * z metodami request(method, path, body) zwracającą rekord
         * Response(int status, String body) i wykonaj pełen przepływ:
         * dodaj notatkę, pobierz listę, usuń notatkę, pobierz listę ponownie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ManualRedirectFollowing {
        /*
         * 🧪 Zadanie 23:
         * Wyłącz automatyczne podążanie za przekierowaniami
         * (setInstanceFollowRedirects(false)) i samodzielnie w pętli (maks.
         * 3 przeskoki) odczytuj kod 3xx oraz nagłówek Location, wysyłając
         * kolejne żądanie pod nowy adres, aż otrzymasz kod spoza zakresu 3xx.
         * Wypisz każdy krok przekierowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ConcurrentGetRequests {
        /*
         * 🧪 Zadanie 24:
         * Użyj ExecutorService, aby wysłać 5 równoległych żądań GET do tego
         * samego kontekstu lokalnego serwera. Zbierz kody odpowiedzi i treści
         * do wspólnej, bezpiecznej wątkowo struktury i wypisz wszystkie
         * wyniki po zakończeniu wszystkich wątków (nie zapomnij zamknąć executor'a).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ReadTimeoutOnSlowEndpoint {
        /*
         * 🧪 Zadanie 25:
         * Kontekst "/wolny" niech śpi (Thread.sleep) 1000ms przed odpowiedzią.
         * Ustaw setReadTimeout(200) i wyślij żądanie – złap SocketTimeoutException
         * i wypisz czytelny komunikat zamiast pozwolić programowi się wysypać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AcceptHeaderNegotiation {
        /*
         * 🧪 Zadanie 26:
         * Kontekst "/dane" sprawdza nagłówek żądania "Accept": dla
         * "application/json" zwraca kod 200 z ciałem '{"format":"json"}' i
         * odpowiednim Content-Type, dla innych wartości zwraca zwykły tekst.
         * Wyślij dwa żądania z różnymi wartościami Accept i wypisz status
         * oraz Content-Type dla każdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ManualCookieRoundTrip {
        /*
         * 🧪 Zadanie 27:
         * Kontekst "/login" ustawia w odpowiedzi nagłówek
         * "Set-Cookie: SESSIONID=abc123". Klient odczytuje tę wartość przez
         * getHeaderField("Set-Cookie") i ręcznie (HttpURLConnection nie
         * zarządza ciasteczkami automatycznie) dołącza nagłówek "Cookie:
         * SESSIONID=abc123" do KOLEJNEGO żądania pod kontekst "/profil",
         * który sprawdza obecność tego nagłówka i zwraca "Zalogowano" lub
         * "Brak sesji".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_IdempotencyComparisonPutVsPost {
        /*
         * 🧪 Zadanie 28:
         * Kontekst "/zasob" (PUT, nadpisuje jedną wartość w pamięci) oraz
         * "/nowy" (POST, za każdym razem tworzy NOWY wpis z rosnącym id w
         * kolekcji). Wykonaj PUT dwukrotnie z tymi samymi danymi i sprawdź,
         * że stan serwera jest identyczny po 1 i po 2 wywołaniu (idempotentność).
         * Wykonaj POST dwukrotnie i sprawdź, że powstały DWA osobne wpisy
         * (brak idempotentności). Wypisz raport porównawczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FormUrlEncodedRoundTrip {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj ręcznie (przez URLEncoder) ciało typu
         * application/x-www-form-urlencoded z mapy {"imie"->"Jan Kowalski",
         * "miasto"->"Nowy Sącz"}. Wyślij je POST-em na kontekst "/formularz",
         * który parsuje ciało z powrotem do mapy i odsyła ją jako tekst.
         * Zweryfikuj (equals na wartościach), że dane przeszły bez zniekształceń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ComprehensiveNotesApiIntegrationTest {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj lokalne, w pełni działające mini REST API "notatek"
         * (GET lista, POST nowa notatka, DELETE po id) i napisz zestaw metod
         * klienckich wykonujących pełen scenariusz: utwórz 2 notatki -> pobierz
         * listę (oczekiwane 2 elementy) -> usuń jedną -> pobierz listę ponownie
         * (oczekiwany 1 element). Po każdym kroku sprawdź oczekiwany kod
         * odpowiedzi i zawartość, a na końcu wypisz zbiorczy raport PASS/FAIL.
         */
        public static void main(String[] args) { }
    }
}
