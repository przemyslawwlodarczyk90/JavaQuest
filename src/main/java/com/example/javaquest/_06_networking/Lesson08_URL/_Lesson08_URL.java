package com.example.javaquest._06_networking.Lesson08_URL;

import java.net.URI;
import java.net.URL;

public class _Lesson08_URL {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST URL?
         * ============================================================
         * URL (Uniform Resource Locator) to "adres" zasobu w sieci –
         * mówi CO chcemy pobrać i JAK się z nim skomunikować (protokół).
         *
         * Anatomia przykładowego URL-a:
         *
         *   https://example.com:8443/search?q=java&lang=pl#results
         *   \___/   \_________/ \__/ \_____/ \____________/ \_____/
         *  protokół    host     port  path       query      fragment
         *  (scheme)
         *
         * - PROTOKÓŁ / SCHEME – "https" – sposób komunikacji (http, https,
         *   ftp, file, mailto...)
         * - HOST – "example.com" – adres serwera (nazwa domenowa lub IP)
         * - PORT – "8443" – port TCP, na którym nasłuchuje serwer
         *   (jeśli pominięty, używany jest domyślny dla danego protokołu:
         *   80 dla http, 443 dla https)
         * - PATH (ścieżka) – "/search" – lokalizacja zasobu na serwerze
         * - QUERY (zapytanie) – "q=java&lang=pl" – dodatkowe parametry
         *   w formacie klucz=wartość, oddzielone znakiem &
         * - FRAGMENT / REF – "results" – wskazuje na konkretne miejsce
         *   WEWNĄTRZ zasobu (np. sekcję strony) – fragment NIGDY nie jest
         *   wysyłany do serwera, obsługuje go wyłącznie przeglądarka!
         */

        /*
         * ============================================================
         * 🔹 java.net.URI vs java.net.URL
         * ============================================================
         * URI (Uniform Resource Identifier) – to POJĘCIE OGÓLNIEJSZE niż
         * URL. URI to każdy identyfikator zasobu (może to być lokalizacja
         * – URL, albo sama nazwa – URN, bez informacji jak się z nim
         * skomunikować). URI zajmuje się WYŁĄCZNIE parsowaniem składni
         * tekstu (zgodnie z RFC 3986) – NIE łączy się z siecią.
         *
         * URL – to KONKRETNY podtyp URI, który dodatkowo mówi, JAK
         * dotrzeć do zasobu (protokół) i (przez openConnection/openStream)
         * potrafi nawiązać prawdziwe połączenie sieciowe.
         *
         * ⚠️ WAŻNE (nowoczesna praktyka): konstruktor `new URL(String)`
         * jest w praktyce ODRADZANY dla dowolnych, niezaufanych stringów
         * – nie waliduje w pełni składni zgodnie z RFC, a od Javy 20 część
         * jego przeciążeń jest formalnie oznaczona jako @Deprecated.
         * Zalecany wzorzec: najpierw zbuduj i zwaliduj `URI`, dopiero
         * potem (jeśli faktycznie potrzebujesz się połączyć) zamień je
         * na `URL` metodą `.toURL()`:
         *
         *   URI uri = new URI("https://example.com/path");
         *   URL url = uri.toURL();
         *
         * URI w konstruktorze RZUCA wyjątek (URISyntaxException) przy
         * błędnej składni – szybciej wykrywamy problem niż przy "cichszym"
         * zachowaniu starego URL(String).
         */

        System.out.println("=== Budowa URI i konwersja do URL ===");
        URI uri = new URI("https://example.com:8443/search?q=java&lang=pl#results");
        URL url = uri.toURL();
        System.out.println("URI: " + uri);
        System.out.println("URL: " + url);

        /*
         * ============================================================
         * 🔍 ROZBIÓR URL-a NA CZĘŚCI SKŁADOWE
         * ============================================================
         * Zarówno URL, jak i URI udostępniają gettery do poszczególnych
         * elementów adresu. Poniżej porównanie obu API na tym samym
         * przykładzie.
         */

        System.out.println("\n=== Rozbiór przez URL ===");
        System.out.println("getProtocol(): " + url.getProtocol());   // https
        System.out.println("getHost():     " + url.getHost());       // example.com
        System.out.println("getPort():     " + url.getPort());       // 8443 (jawnie podany)
        System.out.println("getDefaultPort(): " + url.getDefaultPort()); // 443 (domyślny dla https)
        System.out.println("getPath():     " + url.getPath());       // /search
        System.out.println("getQuery():    " + url.getQuery());      // q=java&lang=pl
        System.out.println("getRef():      " + url.getRef());        // results
        System.out.println("getFile():     " + url.getFile());       // /search?q=java&lang=pl (path + query)
        System.out.println("getAuthority():" + url.getAuthority());  // example.com:8443

        System.out.println("\n=== Rozbiór przez URI ===");
        System.out.println("getScheme():   " + uri.getScheme());     // https
        System.out.println("getHost():     " + uri.getHost());       // example.com
        System.out.println("getPort():     " + uri.getPort());       // 8443
        System.out.println("getPath():     " + uri.getPath());       // /search
        System.out.println("getQuery():    " + uri.getQuery());      // q=java&lang=pl
        System.out.println("getFragment(): " + uri.getFragment());   // results

        /*
         * ============================================================
         * 🔹 PRZYKŁAD BEZ JAWNEGO PORTU I QUERY
         * ============================================================
         * Gdy port nie jest podany w adresie, getPort() zwraca -1
         * (oznacza "nie podano jawnie") – rzeczywisty domyślny port
         * dla danego protokołu poznamy przez getDefaultPort().
         */

        System.out.println("\n=== Przykład 2: bez portu, bez query, bez fragmentu ===");
        URI uri2 = new URI("http://codenga.pl/kursy/java");
        URL url2 = uri2.toURL();
        System.out.println("URL:        " + url2);
        System.out.println("getPort():  " + url2.getPort());        // -1 (nie podano jawnie)
        System.out.println("getDefaultPort(): " + url2.getDefaultPort()); // 80 (domyślny dla http)
        System.out.println("getQuery(): " + url2.getQuery());       // null (brak query)
        System.out.println("getRef():   " + url2.getRef());         // null (brak fragmentu)

        /*
         * ============================================================
         * 🔍 KILKA PARAMETRÓW QUERY NARAZ
         * ============================================================
         * URL/URI NIE parsują automatycznie query na osobne pary
         * klucz=wartość – getQuery() zwraca surowy tekst. Rozbicie na
         * pary trzeba zrobić samodzielnie (np. split po "&", potem "=").
         */

        System.out.println("\n=== Przykład 3: reczne rozbicie query na parametry ===");
        URI uri3 = new URI("https://sklep.pl/produkty?kategoria=ksiazki&sortuj=cena&strona=2");
        String query = uri3.getQuery();
        System.out.println("Surowe query: " + query);
        for (String param : query.split("&")) {
            String[] pair = param.split("=", 2);
            System.out.println("  " + pair[0] + " -> " + pair[1]);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - URL/URI opisuje adres zasobu: protokol://host:port/path?query#fragment
         * - protokół (scheme) – sposób komunikacji (http, https, ftp...)
         * - host – adres serwera; port – port TCP (domyślny zależy od protokołu)
         * - path – ścieżka zasobu na serwerze
         * - query – parametry (klucz=wartość, oddzielone &) – NIE jest
         *   automatycznie rozbijany na pary, trzeba to zrobić samemu
         * - fragment (ref) – wskazuje miejsce WEWNĄTRZ zasobu, NIGDY nie
         *   jest wysyłany do serwera
         * - URI = ogólny identyfikator zasobu, tylko parsowanie składni
         *   (bez sieci); URL = konkretny URI + umiejętność połączenia się
         * - ✅ DOBRA PRAKTYKA: buduj `new URI(String)`, waliduj, potem
         *   `.toURL()` – unikaj `new URL(String)` bezpośrednio na
         *   niezaufanym/dowolnym tekście
         * - Ta lekcja to CZYSTE PARSOWANIE – brak jakiejkolwiek komunikacji
         *   sieciowej (o tym w kolejnych lekcjach: URLConnection,
         *   HttpURLConnection)
         */
    }
}
