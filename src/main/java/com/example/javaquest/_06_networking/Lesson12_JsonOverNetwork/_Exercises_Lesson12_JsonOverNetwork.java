package com.example.javaquest._06_networking.Lesson12_JsonOverNetwork;

public class _Exercises_Lesson12_JsonOverNetwork {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FetchAndParseSimpleGreeting {
        /*
         * 🧪 Zadanie 1:
         * Uruchom lokalny com.sun.net.httpserver.HttpServer (port 0) z
         * kontekstem "/api/greeting" zwracającym JSON {"message":"Hello"}.
         * Pobierz go przez HttpClient, sparsuj Gsonem do rekordu
         * Greeting(String message) i wypisz pole message. Zatrzymaj serwer
         * na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FetchNumbersArraySum {
        /*
         * 🧪 Zadanie 2:
         * Kontekst "/api/numbers" zwraca JSON [1,2,3,4,5]. Pobierz go i
         * sparsuj Gsonem do List<Integer> (przez TypeToken), policz sumę
         * elementów i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FetchSingleUserRecord {
        /*
         * 🧪 Zadanie 3:
         * Kontekst "/api/user" zwraca JSON pojedynczego użytkownika
         * {"id":1,"name":"Anna Nowak","email":"anna@example.com"}. Sparsuj
         * go do rekordu User(int id, String name, String email) i wypisz
         * wszystkie pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrettyPrintJava2Json {
        /*
         * 🧪 Zadanie 4:
         * Utwórz w Javie obiekt User(4, "Piotr Zieliński", "piotr@example.com")
         * (bez łączenia się z siecią) i zserializuj go do JSON-a przy pomocy
         * new GsonBuilder().setPrettyPrinting().create().toJson(...). Wypisz
         * sformatowany wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyParsedTypeIsUserNotMap {
        /*
         * 🧪 Zadanie 5:
         * Pobierz z kontekstu "/api/user" pojedynczego użytkownika, sparsuj
         * go do User.class i wypisz users.getClass().getSimpleName() –
         * zweryfikuj, że to "User", a nie "LinkedTreeMap".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_HandleMalformedJson {
        /*
         * 🧪 Zadanie 6:
         * Kontekst "/api/zle" zwraca celowo uszkodzony JSON (np. brakujący
         * nawias klamrowy). Spróbuj sparsować odpowiedź Gsonem, złap
         * JsonSyntaxException i wypisz przyjazny komunikat błędu zamiast
         * pozwolić programowi się wysypać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CheckContentTypeBeforeParsing {
        /*
         * 🧪 Zadanie 7:
         * Przed sparsowaniem odpowiedzi z kontekstu "/api/user" sprawdź
         * nagłówek Content-Type (response.headers().firstValue("Content-Type"))
         * – jeśli nie zaczyna się od "application/json", wypisz ostrzeżenie
         * zamiast próbować parsować. W tej lekcji powinien się zgadzać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NestedObjectParsing {
        /*
         * 🧪 Zadanie 8:
         * Zdefiniuj rekordy Address(String city, String zip) oraz
         * UserWithAddress(int id, String name, Address address). Kontekst
         * "/api/user-adres" zwraca JSON z zagnieżdżonym obiektem adresu.
         * Sparsuj i wypisz miasto z zagnieżdżonego pola address.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EmptyArrayHandling {
        /*
         * 🧪 Zadanie 9:
         * Kontekst "/api/pusta-lista" zwraca JSON []. Sparsuj go do
         * List<User>, sprawdź isEmpty() i wypisz odpowiedni komunikat
         * zamiast próbować odczytać pierwszy element.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_JavaToJsonSizeComparison {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj w Javie listę 3 obiektów User (bez sieci), zserializuj ją
         * Gsonem do JSON-a (bez pretty printing) i wypisz liczbę znaków
         * wynikowego tekstu jako prostą miarę "zwartości" formatu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FilterUsersByNameSubstring {
        /*
         * 🧪 Zadanie 11:
         * Pobierz listę użytkowników z kontekstu "/api/users" (jak w
         * lekcji), sparsuj do List<User> i przefiltruj streamami tylko te,
         * których name zawiera literę "a" (dowolna wielkość liter). Wypisz
         * przefiltrowaną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SortUsersByName {
        /*
         * 🧪 Zadanie 12:
         * Pobierz listę użytkowników, posortuj ją Comparatorem po polu name
         * i wypisz posortowaną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PostUserAndVerifyEcho {
        /*
         * 🧪 Zadanie 13:
         * Zserializuj obiekt User do JSON-a i wyślij go POST-em na kontekst
         * "/api/echo-user", który odsyła DOKŁADNIE to samo ciało z powrotem.
         * Sparsuj odpowiedź z powrotem do User i porównaj (equals) z
         * oryginalnym obiektem (rekordy mają equals z automatu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExtraUnknownFieldsIgnored {
        /*
         * 🧪 Zadanie 14:
         * Kontekst "/api/user-extra" zwraca JSON z dodatkowym polem, którego
         * nie ma w rekordzie User (np. "createdAt":"2024-01-01"). Sparsuj
         * mimo to do User.class i wypisz, że parsowanie się powiodło, a
         * dodatkowe pole zostało po prostu zignorowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConditionalFormatByQueryParam {
        /*
         * 🧪 Zadanie 15:
         * Kontekst "/api/format" zwraca listę użytkowników dla
         * "?format=list", a pojedynczego użytkownika dla "?format=single".
         * Wyślij oba warianty żądania i sparsuj każdy odpowiednim TypeToken
         * (List<User> vs User), wypisując wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GenericGetJsonHelper {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę generyczną <T> T getJson(String url, Type type)
         * łączącą HttpClient i Gson. Użyj jej do pobrania osobno
         * User (z "/api/user") oraz List<User> (z "/api/users").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ParseJsonMapObject {
        /*
         * 🧪 Zadanie 17:
         * Kontekst "/api/mapa" zwraca JSON {"1":"Anna","2":"Jan","3":"Ewa"}.
         * Sparsuj go do Map<String,String> (przez TypeToken) i wypisz
         * wszystkie wpisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NumberAsStringMismatch {
        /*
         * 🧪 Zadanie 18:
         * Kontekst "/api/dziwny-user" zwraca id jako JSON string "1" zamiast
         * liczby. Zdefiniuj pomocniczy rekord z polem String id i sparsuj tę
         * odpowiedź, a następnie ręcznie skonwertuj String na int
         * (Integer.parseInt) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ValidateAllEmailsContainAt {
        /*
         * 🧪 Zadanie 19:
         * Pobierz listę użytkowników z "/api/users", sparsuj do List<User> i
         * zwaliduj, że każdy email zawiera znak "@" (streamami). Wypisz
         * raport walidacji z listą niepoprawnych wpisów (jeśli jakieś są).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ClientSideGetById {
        /*
         * 🧪 Zadanie 20:
         * Kontekst "/api/users" zwraca pełną listę. Pobierz ją raz, sparsuj
         * do List<User>, a następnie zaimplementuj metodę findById(List<User>, int)
         * symulującą "pobranie pojedynczego zasobu po id" wyłącznie po
         * stronie klienta (bez dodatkowego żądania sieciowego). Przetestuj
         * dla kilku różnych id.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InMemoryCrudJsonApi {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj w handlerze lokalnego serwera prostą listę
         * użytkowników w pamięci (List<User> jako pole modyfikowalne, np.
         * ArrayList) obsługującą: GET "/api/users" (cała lista jako JSON),
         * POST "/api/users" (dodaje nowego, zwraca go z wygenerowanym id),
         * DELETE "/api/users?id=X" (usuwa i zwraca pozostałą listę). Po
         * stronie klienta wykonaj: pobierz -> dodaj -> pobierz (sprawdź
         * wzrost liczby elementów) -> usuń -> pobierz (sprawdź spadek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GenericJsonListHelper {
        /*
         * 🧪 Zadanie 22:
         * Napisz generyczną metodę pomocniczą do pobierania list JSON,
         * przyjmującą URL oraz gotowy TypeToken (lub Type), i użyj jej
         * zarówno dla List<User> (z "/api/users"), jak i dla List<Integer>
         * (z "/api/numbers") w tym samym programie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PaginatedFetchAllPages {
        /*
         * 🧪 Zadanie 23:
         * Kontekst "/api/strona" zwraca dla "?page=1" JSON
         * {"page":1,"totalPages":2,"items":[...]}, a dla "?page=2" drugą
         * porcję elementów z totalPages=2. Napisz pętlę klienta, która
         * pobiera kolejne strony aż do totalPages i łączy wszystkie items
         * w jedną listę List<User>. Wypisz łączną liczbę elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RobustErrorVsSuccessHandling {
        /*
         * 🧪 Zadanie 24:
         * Kontekst "/api/czasem-blad" zwraca losowo (lub na podstawie
         * parametru query "fail=true") albo poprawną listę użytkowników z
         * kodem 200, albo JSON błędu {"error":"Coś poszło nie tak"} z kodem
         * 500. Klient sprawdza statusCode() PRZED parsowaniem i wybiera
         * odpowiedni typ docelowy (List<User> albo rekord ErrorResponse).
         * Przetestuj oba warianty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_JsonVsManualXmlSizeComparison {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj w Javie tę samą listę 3 użytkowników i zserializuj ją do
         * JSON-a Gsonem, a osobno zbuduj RĘCZNIE (przez konkatenację
         * Stringów, bez prawdziwego parsera) odpowiadający jej uproszczony
         * tekst przypominający XML. Porównaj długości obu tekstów w bajtach
         * (UTF-8) i wypisz obie wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ClientSideCacheWithHitCounter {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj po stronie serwera licznik wywołań kontekstu
         * "/api/users" (AtomicInteger). Po stronie klienta zbuduj prostą
         * pamięć podręczną: pierwsze pobranie listy trafia do serwera i
         * zapisuje wynik lokalnie, kolejne "pobrania" korzystają z lokalnej
         * kopii. Zweryfikuj licznik serwera (powinien wynosić 1 mimo kilku
         * odczytów po stronie klienta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RoundTripIntegrityFiveUsers {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj w Javie 5 rekordów User, zserializuj listę do JSON-a i
         * wyślij POST-em na kontekst echo (odsyłający dokładnie to samo
         * ciało). Sparsuj odpowiedź z powrotem do List<User> i zweryfikuj
         * (equals na całej liście, korzystając z equals rekordów), że dane
         * przetrwały cały "podróż w obie strony" bez zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ConcurrentIdenticalFetches {
        /*
         * 🧪 Zadanie 28:
         * Użyj ExecutorService, aby równolegle wysłać 4 żądania GET do
         * "/api/users", parsując każdą odpowiedź niezależnie do List<User>.
         * Po zakończeniu wszystkich wątków zweryfikuj, że wszystkie 4
         * sparsowane listy są sobie równe (equals).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SchemaEvolutionOptionalField {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj rekord UserV1(int id, String name) oraz pełny User(int id,
         * String name, String email). Kontekst "/api/v1/user" zwraca JSON
         * bez pola email, a "/api/v2/user" zwraca pełny obiekt. Sparsuj
         * każdy odpowiednim typem rekordu i wypisz oba wyniki, komentując
         * (println), jak Gson radzi sobie z brakującym polem (ustawia null
         * dla typów referencyjnych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_TinyJsonRestClient {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj klasę JsonRestClient z generyczną metodą
         * <T> T get(String path, Type type) oraz metodą
         * <T> T post(String path, Object body, Type responseType) (łączącą
         * HttpClient + Gson). Zbuduj lokalne REST API "użytkowników" z
         * operacjami list/create/delete (jak w Zadaniu 21) i przeprowadź
         * pełen scenariusz testowy przy pomocy tej klasy, kończąc wypisaniem
         * zbiorczego raportu z wynikami każdego kroku.
         */
        public static void main(String[] args) { }
    }
}
