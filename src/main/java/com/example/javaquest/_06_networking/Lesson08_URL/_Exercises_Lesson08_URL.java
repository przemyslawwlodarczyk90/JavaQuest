package com.example.javaquest._06_networking.Lesson08_URL;

public class _Exercises_Lesson08_URL {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ParseBasicUri {
        /*
         * 🧪 Zadanie 1:
         * Utwórz URI z tekstu "https://example.com:8443/search?q=java&lang=pl#results".
         * Wypisz osobno: scheme, host, port, path, query, fragment
         * (odpowiednie gettery z klasy URI).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UriToUrlConversion {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj URI z tekstu "http://codenga.pl/kursy/java", zamień je na URL
         * metodą toURL(). Wypisz oba obiekty (toString()) oraz nazwę klasy
         * każdego z nich (getClass().getSimpleName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RawQueryString {
        /*
         * 🧪 Zadanie 3:
         * Dla URI "https://sklep.pl/produkty?kategoria=ksiazki&sortuj=cena"
         * wypisz surową wartość zwróconą przez getQuery() – bez żadnego
         * rozbijania na pary klucz=wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FragmentVsNoFragment {
        /*
         * 🧪 Zadanie 4:
         * Porównaj dwa URI: "https://example.com/page#section2" oraz
         * "https://example.com/page" (bez fragmentu). Wypisz getFragment()
         * dla obu – dla drugiego powinno być null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DefaultPortVsExplicitPort {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj URL dla "https://example.com/" (bez portu) oraz dla
         * "https://example.com:9443/" (z jawnym portem). Dla obu wypisz
         * getPort() oraz getDefaultPort() i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AuthorityPart {
        /*
         * 🧪 Zadanie 6:
         * Dla URL "https://user:pass@example.com:8443/path" wypisz wynik
         * getAuthority() oraz osobno getHost() i getPort() – zaobserwuj,
         * co dokładnie znajduje się w "authority".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GetFilePathPlusQuery {
        /*
         * 🧪 Zadanie 7:
         * Dla URL "https://example.com/search?q=java" wypisz osobno getPath(),
         * getQuery() oraz getFile() – zaobserwuj, że getFile() to połączenie
         * path + "?" + query.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_InvalidUriHandling {
        /*
         * 🧪 Zadanie 8:
         * Spróbuj utworzyć URI z niepoprawnego tekstu "http:// spacja w
         * hoscie /path" (zawiera spację). Złap URISyntaxException i wypisz
         * komunikat błędu zamiast pozwolić programowi się wysypać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareTwoUrisEquality {
        /*
         * 🧪 Zadanie 9:
         * Utwórz dwa obiekty URI z identycznego tekstu
         * "https://example.com/path?a=1" oraz sprawdź metodą equals(),
         * czy są sobie równe. Wypisz wynik porównania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExtractSchemeFromList {
        /*
         * 🧪 Zadanie 10:
         * Mając tablicę tekstów adresów: {"https://example.com", "ftp://files.pl",
         * "mailto:kontakt@example.com", "file:///C:/dane.txt"}, zbuduj dla
         * każdego URI i wypisz samą wartość getScheme().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ManualQueryToMap {
        /*
         * 🧪 Zadanie 11:
         * Dla URI "https://sklep.pl/produkty?kategoria=ksiazki&sortuj=cena&strona=2"
         * ręcznie (bez zewnętrznych bibliotek) rozbij getQuery() na
         * Map<String,String> (split po "&", potem po "="). Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildUriFromComponents {
        /*
         * 🧪 Zadanie 12:
         * Skorzystaj z konstruktora URI(scheme, host, path, query, fragment),
         * podając: scheme="https", host="example.com", path="/szukaj",
         * query="q=java", fragment="wyniki". Wypisz zbudowany URI (toString()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PortFallbackToDefault {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę effectivePort(URL url), która zwraca getPort(),
         * a jeśli ten wynosi -1 (nie podano jawnie), zwraca getDefaultPort().
         * Przetestuj ją dla "http://example.com/" oraz "http://example.com:8081/".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ClassifyByScheme {
        /*
         * 🧪 Zadanie 14:
         * Mając tablicę adresów: {"https://a.pl", "http://b.pl", "ftp://c.pl",
         * "file:///d.txt", "https://e.pl"}, zbuduj Map<String,Integer> licząc,
         * ile adresów ma dany scheme (http/https/ftp/file). Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SplitPathIntoSegments {
        /*
         * 🧪 Zadanie 15:
         * Dla URI "https://example.com/kursy/java/kolekcje/streamy" pobierz
         * getPath() i rozbij go ręcznie (split("/")) na segmenty, pomijając
         * ewentualne puste elementy. Wypisz listę segmentów z numeracją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UriVsUrlApiComparison {
        /*
         * 🧪 Zadanie 16:
         * Dla tego samego adresu "https://example.com:8443/path?x=1#frag"
         * zbuduj zarówno URI, jak i URL (przez toURL()). Wypisz obok siebie
         * wyniki odpowiadających sobie metod z obu klas (np. getHost() z URI
         * i getHost() z URL, getQuery() z obu itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DetectNonDefaultPort {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę usesNonDefaultPort(URL url) zwracającą true, jeśli
         * URL ma jawnie podany port RÓŻNY od getDefaultPort(). Przetestuj
         * dla "https://example.com/" (false) i "https://example.com:8443/" (true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UriComponentsRecord {
        /*
         * 🧪 Zadanie 18:
         * Zdefiniuj rekord UriParts(String scheme, String host, int port,
         * String path, String query, String fragment). Napisz metodę
         * parse(URI uri) zwracającą wypełniony rekord i przetestuj ją na
         * 2 różnych adresach, wypisując wynikowe rekordy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DuplicateQueryParams {
        /*
         * 🧪 Zadanie 19:
         * Dla URI "https://sklep.pl/produkty?tag=java&tag=web&tag=backend"
         * rozbij query na Map<String, List<String>>, gdzie ten sam klucz
         * "tag" zbiera WSZYSTKIE swoje wartości do jednej listy. Wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ValidateBatchOfUris {
        /*
         * 🧪 Zadanie 20:
         * Mając tablicę tekstów: {"https://ok.pl", "http:// zle bo spacja",
         * "ftp://tez.ok", "nie<>uri"}, spróbuj utworzyć URI dla każdego,
         * łapiąc URISyntaxException per element. Zbierz osobno listę
         * poprawnych i niepoprawnych wpisów, wypisz obie listy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildQueryStringEncoded {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę buildQueryString(Map<String,String> params), która
         * dla mapy {"q"->"java kurs", "lang"->"pl"} zakoduje wartości metodą
         * URLEncoder.encode(value, StandardCharsets.UTF_8) i połączy pary
         * w tekst "q=java+kurs&lang=pl". Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DecodeQueryValues {
        /*
         * 🧪 Zadanie 22:
         * Dla surowego query "imie=Jan+Kowalski&miasto=Nowy%20S%C4%85cz"
         * rozbij je na pary klucz=wartość, a każdą wartość zdekoduj metodą
         * URLDecoder.decode(value, StandardCharsets.UTF_8). Wypisz zdekodowaną mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SimplePathVariableRouter {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę matchPath(String pattern, String actualPath), gdzie
         * pattern to np. "/users/{id}/orders/{orderId}", a actualPath to
         * "/users/42/orders/7". Metoda ma zwrócić Map<String,String> ze
         * zmiennymi {"id"->"42", "orderId"->"7"} (dopasowanie segment po
         * segmencie, bez regexów). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ResolveRelativeUri {
        /*
         * 🧪 Zadanie 24:
         * Mając bazowe URI "https://example.com/kursy/java/" oraz względne
         * "../kolekcje/streamy", użyj metody URI.resolve(String), aby
         * uzyskać pełny adres wynikowy. Wypisz wynik dla 2-3 różnych
         * względnych ścieżek (np. "index.html", "/absolutna/sciezka").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_NormalizePathSegments {
        /*
         * 🧪 Zadanie 25:
         * Dla URI "https://example.com/a/b/../c/./d" wywołaj metodę
         * normalize() i wypisz oryginalny oraz znormalizowany URI –
         * porównaj, jak zniknęły segmenty ".." i ".".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FluentUriBuilder {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj prostą klasę UriBuilder z metodami fluent (zwracającymi
         * this): scheme(String), host(String), path(String), addQueryParam(String,String),
         * fragment(String) oraz metodą build() zwracającą gotowe URI (z ręcznie
         * poskładanym query z par klucz=wartość). Zbuduj przykładowy adres
         * i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GroupUrisByHost {
        /*
         * 🧪 Zadanie 27:
         * Mając listę adresów: {"https://a.pl/x", "https://b.pl/y",
         * "https://a.pl/z", "https://c.pl/w", "https://a.pl/q"}, zbuduj
         * dla każdego URI i pogrupuj je w Map<String, List<URI>> po hoście.
         * Wypisz mapę posortowaną po nazwie hosta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BatchParserReport {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj rekord UrlParts (jak w Zadaniu 18, ewentualnie ten sam).
         * Dla tablicy co najmniej 5 różnych adresów URL sparsuj każdy do
         * rekordu i wypisz sformatowaną "tabelę" (scheme, host, port, path,
         * query, fragment) w konsoli, wyrównaną kolumnowo (String.format).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DuplicateKeyCaseSensitivity {
        /*
         * 🧪 Zadanie 29:
         * Dla query "Sort=asc&sort=desc&SORT=none" zademonstruj, że przy
         * ręcznym parsowaniu do zwykłej Map<String,String> klucze różniące
         * się wielkością liter ("Sort", "sort", "SORT") są TRZEMA różnymi
         * wpisami. Wypisz zawartość mapy i skomentuj (println), czy to
         * pożądane zachowanie w typowym REST API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ComprehensiveUrlAnalyzer {
        /*
         * 🧪 Zadanie 30:
         * Dla tablicy co najmniej 8 różnorodnych adresów URL (mix http/https,
         * z portem i bez, z query i bez, z fragmentem i bez) napisz analizator,
         * który wypisze raport podsumowujący: liczbę adresów bezpiecznych
         * (https) vs niebezpiecznych (http), liczbę adresów z jawnym portem
         * różnym od domyślnego, liczbę adresów posiadających query oraz
         * liczbę adresów posiadających fragment.
         */
        public static void main(String[] args) { }
    }
}
