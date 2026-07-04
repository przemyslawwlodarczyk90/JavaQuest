package com.example.javaquest._06_networking.Lesson14_HtmlUnit;

public class _Exercises_Lesson14_HtmlUnit {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FetchTitleOfExampleCom {
        /*
         * 🧪 Zadanie 1:
         * Utwórz WebClient (try-with-resources) z wyłączonym CSS i
         * JavaScript oraz timeoutem 5000ms. Pobierz stronę
         * "https://example.com" i wypisz jej tytuł (getTitleText()). Całość
         * opakuj w try/catch, aby brak internetu nie przerwał programu –
         * w takim przypadku wypisz czytelny komunikat błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PrintEffectiveUrl {
        /*
         * 🧪 Zadanie 2:
         * Pobierz "https://example.com" i wypisz page.getUrl() – zweryfikuj,
         * że odpowiada oryginalnie żądanemu adresowi. Obsłuż błąd połączenia
         * gracefully (bez przerywania programu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CountAnchorsOnPage {
        /*
         * 🧪 Zadanie 3:
         * Pobierz "https://example.com", policz liczbę elementów <a>
         * (page.getAnchors().size()) i wypisz wynik. Timeout 5000ms + obsługa
         * wyjątku jak w poprzednich zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrintFirstAnchorText {
        /*
         * 🧪 Zadanie 4:
         * Pobierz "https://example.com" i wypisz tekst pierwszego znalezionego
         * linku (getAnchors().get(0).getTextContent().trim()), a jeśli lista
         * jest pusta – wypisz "brak linków".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TryWithResourcesLogging {
        /*
         * 🧪 Zadanie 5:
         * Zademonstruj poprawne zamykanie WebClient: wypisz komunikat "Otwieram
         * WebClient" przed blokiem try-with-resources, wykonaj prostą operację
         * (np. ustawienie opcji), a po wyjściu z bloku (czyli po automatycznym
         * close()) wypisz "WebClient zamknięty".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ConfigureAndPrintOptions {
        /*
         * 🧪 Zadanie 6:
         * Skonfiguruj WebClient: setCssEnabled(false), setJavaScriptEnabled(false),
         * setTimeout(3000). PRZED wykonaniem jakiegokolwiek połączenia wypisz
         * wszystkie trzy ustawione wartości odczytane z getOptions().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CheckStatusCode200 {
        /*
         * 🧪 Zadanie 7:
         * Pobierz "https://example.com" i sprawdź
         * page.getWebResponse().getStatusCode() == 200, wypisując wynik
         * porównania. Obsłuż gracefully ewentualny brak połączenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GracefulFailureOnInvalidDomain {
        /*
         * 🧪 Zadanie 8:
         * Spróbuj pobrać oczywiście nieistniejącą domenę (np.
         * "https://ta-domena-na-pewno-nie-istnieje-abcxyz123.com") z
         * timeoutem 5000ms. Złap wyjątek i wypisz jego typ oraz komunikat –
         * program NIGDY nie powinien zakończyć się nieobsłużonym wyjątkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PageContentLengthMetric {
        /*
         * 🧪 Zadanie 9:
         * Pobierz "https://example.com" (z timeoutem i obsługą błędu) i
         * wypisz długość tekstu strony (page.asNormalizedText().length())
         * jako prostą metrykę rozmiaru treści.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareTwoFetchesSameTitle {
        /*
         * 🧪 Zadanie 10:
         * Pobierz "https://example.com" dwa razy pod rząd (tym samym
         * WebClient, dwa osobne getPage()) i porównaj (equals) tytuły obu
         * odpowiedzi – powinny być identyczne. Wypisz wynik porównania.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FetchTwoStableDomainsIndependently {
        /*
         * 🧪 Zadanie 11:
         * Pobierz kolejno "https://example.com" oraz "https://example.org"
         * (druga domena IANA, też zarezerwowana dokumentacyjnie), każdą w
         * OSOBNYM bloku try/catch, tak by ewentualna awaria jednej nie
         * przeszkodziła w próbie drugiej. Wypisz tytuły obu (lub informację
         * o błędzie dla tej, która zawiedzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FetchTitleOptionalHelper {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę fetchTitle(String url) zwracającą Optional<String>
         * (pusty przy błędzie, dzięki wewnętrznemu try/catch). Przetestuj ją
         * dla "https://example.com" (oczekiwany tytuł) oraz dla oczywiście
         * błędnego adresu (oczekiwany Optional.empty()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExtractHttpHrefsOnly {
        /*
         * 🧪 Zadanie 13:
         * Pobierz "https://example.com", zbierz wszystkie wartości
         * getHrefAttribute() z linków i przefiltruj (streamami) tylko te
         * zaczynające się od "http". Wypisz przefiltrowaną listę (może być
         * pusta, jeśli strona nie ma takich linków – obsłuż to gracefully).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasureFetchDuration {
        /*
         * 🧪 Zadanie 14:
         * Zmierz (System.nanoTime przed/po) czas pobrania
         * "https://example.com" i wypisz go w milisekundach – skomentuj
         * (println), dlaczego ustawiony wcześniej timeout jest ważny na
         * wypadek wolnego/martwego połączenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_PrintAnchorsTable {
        /*
         * 🧪 Zadanie 15:
         * Pobierz "https://example.com" i wypisz sformatowaną "tabelę"
         * (String.format) z kolumnami: indeks, tekst linku, href – dla
         * każdego znalezionego elementu <a>. Jeśli strona nie ma linków,
         * wypisz stosowny komunikat zamiast pustej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareDifferentTimeoutSettings {
        /*
         * 🧪 Zadanie 16:
         * Skonfiguruj dwa osobne WebClienty: jeden z setTimeout(500), drugi
         * z setTimeout(5000). Pobierz nimi (osobno, każdy w try/catch)
         * "https://example.com" i porównaj, czy oba zakończyły się
         * sukcesem – dla stabilnej, szybkiej domeny obie próby powinny
         * się udać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WebClientProfileRecord {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj rekord WebClientProfile(boolean css, boolean js, int timeoutMs).
         * Zbuduj profil "szybkie scrapowanie" (css=false, js=false,
         * timeoutMs=3000), zastosuj go do skonfigurowania WebClient (metoda
         * applyProfile(WebClient, WebClientProfile)) i pobierz
         * "https://example.com" korzystając z tak skonfigurowanego klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PageSummaryRecord {
        /*
         * 🧪 Zadanie 18:
         * Zdefiniuj rekord PageSummary(String title, String firstLink).
         * Pobierz "https://example.com", zbuduj i wypisz taki rekord
         * (firstLink = href pierwszego linku, albo "brak", jeśli lista jest
         * pusta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RetryWithBackoff {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj pętlę próbującą pobrać "https://example.com" do 3
         * razy, z krótką przerwą (np. Thread.sleep(100)) między nieudanymi
         * próbami, przerywając od razu po pierwszym sukcesie. Wypisz numer
         * próby, przy której się udało (dla stabilnej domeny oczekiwana
         * próba 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareStatusCodesOkAndMissing {
        /*
         * 🧪 Zadanie 20:
         * Pobierz "https://example.com" (oczekiwany status 200) oraz
         * "https://example.com/strona-ktorej-na-pewno-nie-ma-xyz" (oczekiwany
         * status 404). Dla drugiego wywołania obsłuż zarówno scenariusz
         * wyjątku (FailingHttpStatusCodeException – odczytaj z niego kod
         * przez getStatusCode()), jak i scenariusz zwrócenia strony
         * bezpośrednio, i wypisz oba uzyskane statusy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DisableThrowOnFailingStatus {
        /*
         * 🧪 Zadanie 21:
         * Ustaw webClient.getOptions().setThrowExceptionOnFailingStatusCode(false),
         * a następnie pobierz "https://example.com/nieistniejaca-strona-xyz"
         * (oczekiwany status 404) BEZ wyjątku – wypisz uzyskany status kod
         * bezpośrednio z page.getWebResponse().getStatusCode().
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_OneHopLinkCrawler {
        /*
         * 🧪 Zadanie 22:
         * Pobierz "https://example.com", znajdź PIERWSZY bezwzględny link
         * zaczynający się od "http" wśród jej anchorów. Jeśli taki istnieje,
         * spróbuj pobrać (osobny try/catch, gracefully) i tę stronę,
         * wypisując oba tytuły (strony startowej i strony "o jeden krok
         * dalej"). Jeśli nie ma takiego linku lub drugie pobranie zawiedzie,
         * wypisz stosowny komunikat zamiast przerywać program.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ReusableClientConfigurator {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę configureClient(WebClient client), która ustawia
         * spójnie: CSS/JS wyłączone, timeout 5000ms oraz
         * setThrowExceptionOnFailingStatusCode(false). Użyj jej do
         * skonfigurowania dwóch osobnych WebClientów użytych do pobrania
         * dwóch różnych adresów ("https://example.com" i
         * "https://example.com/brak-strony").
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SiteAvailabilityChecker {
        /*
         * 🧪 Zadanie 24:
         * Mając listę adresów: {"https://example.com", "https://example.org",
         * "https://ta-domena-na-pewno-nie-istnieje-abcxyz123.com"}, sprawdź
         * dla każdego (osobny try/catch na iterację pętli, program nigdy nie
         * przerywa się w środku) czy jest osiągalny, budując
         * Map<String,Boolean> adres -> dostępny. Wypisz pełny raport na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareHtmlUnitVsHttpClientContentLength {
        /*
         * 🧪 Zadanie 25:
         * Pobierz "https://example.com" zarówno przez HtmlUnit
         * (page.asNormalizedText().length()), jak i przez zwykły
         * java.net.http.HttpClient (response.body().length() surowego
         * HTML-a, patrz Lesson11). Wypisz obie długości obok siebie –
         * skomentuj (println) różnicę między "przetworzonym tekstem strony"
         * a "surowym HTML-em".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DistinctLinkDomains {
        /*
         * 🧪 Zadanie 26:
         * Pobierz "https://example.com", zbierz wszystkie bezwzględne
         * (zaczynające się od "http") wartości href, zbuduj dla każdej
         * obiekt java.net.URI (patrz Lesson08) i wyciągnij getHost().
         * Wypisz zbiór unikalnych domen (Set<String>) – jeśli lista linków
         * jest pusta, wypisz stosowny komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_IsLinkWorkingHelper {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę isLinkWorking(String url) zwracającą true, jeśli
         * pobranie strony kończy się kodem statusu < 400 (użyj
         * setThrowExceptionOnFailingStatusCode(false) i sprawdzenia kodu), a
         * false w każdym innym przypadku (w tym przy wyjątku – catch
         * zwracający false). Przetestuj ją na małej, wybranej ręcznie liście
         * 2-3 adresów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PageStabilityDiff {
        /*
         * 🧪 Zadanie 28:
         * Pobierz "https://example.com" DWA razy, za każdym razem świeżym
         * WebClientem (nowy try-with-resources). Porównaj tytuł oraz liczbę
         * linków między obiema odpowiedziami i wypisz raport, czy strona
         * wygląda na "stabilną" (bez zmian) między dwoma pobraniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TinyTimeoutThenNormalRetry {
        /*
         * 🧪 Zadanie 29:
         * Skonfiguruj WebClient z bardzo małym timeoutem (np. 1ms) i
         * spróbuj pobrać "https://example.com" – oczekiwany wyjątek
         * (timeout). Złap go i wypisz komunikat, a następnie ponów próbę
         * NOWYM WebClientem z normalnym timeoutem (5000ms), tym razem
         * oczekując sukcesu. Wypisz oba wyniki (nieudany i udany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ComprehensiveScrapingReport {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny raport scrapowania dla "https://example.com":
         * tytuł, status HTTP, liczba linków, zbiór unikalnych domen
         * linków (jak w Zadaniu 26) oraz całkowity czas pobrania w ms.
         * Całość opakuj tak, by w razie NIEPOWODZENIA (brak internetu,
         * timeout) program wypisał czytelny raport zastępczy informujący o
         * niepowodzeniu, zamiast zakończyć się awaryjnie.
         */
        public static void main(String[] args) { }
    }
}
