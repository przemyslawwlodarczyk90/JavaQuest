package com.example.javaquest._18_rest_api.Lesson07_ContentNegotiation;

public class _Exercises_Lesson07_ContentNegotiation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainContentNegotiationInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, czym jest content
         * negotiation - jaka role gra naglowek Accept w tym procesie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementEndpointRespondingWithJsonOnly {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/status` zwracajacym ZAWSZE
         * "application/json" (bez negocjacji) - zweryfikuj naglowek
         * Content-Type odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExtendEndpointToSupportTwoFormats {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz endpoint z Zadania 2, zeby wspieral 2 formaty (JSON i
         * zwykly tekst) - wybierz na podstawie naglowka Accept (prosta
         * logika: jesli zawiera "text/plain", zwroc tekst).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParseSimpleAcceptHeaderWithoutQuality {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode `List<String> parseAccept(String header)`
         * rozbijajaca naglowek Accept (bez wag "q") na liste typow
         * media - przetestuj dla "application/json, text/plain".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementNotAcceptableFor406 {
        /*
         * 🧪 Zadanie 5:
         * Rozszerz endpoint z Zadania 3, zeby zwracal 406 Not Acceptable
         * dla Accept, ktorego serwer w ogole nie wspiera (np.
         * "application/xml").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyNotUseFileExtensionInUri {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, dlaczego content negotiation przez
         * naglowek Accept jest lepsza praktyka niz rozszerzenie w URI
         * (/users/42.json) - odniesienie do Lesson03.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementVaryHeaderInResponse {
        /*
         * 🧪 Zadanie 7:
         * Dodaj naglowek "Vary: Accept" do odpowiedzi endpointu z
         * Zadania 3 - w komentarzu wyjasnij, po co ten naglowek jest
         * potrzebny dla posrednich cache'ow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SendRequestWithWildcardAccept {
        /*
         * 🧪 Zadanie 8:
         * Wyslij request z "Accept: *\/*" do endpointu z Zadania 3 -
         * zweryfikuj, ze serwer wybral swoj DOMYSLNY format.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareResponseBodyForTwoDifferentAcceptValues {
        /*
         * 🧪 Zadanie 9:
         * Wyslij 2 requesty do tego samego endpointu z roznymi wartosciami
         * Accept - porownaj i wypisz oba ciala odpowiedzi obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListCommonMediaTypes {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wymien min. 5 popularnych typow media (JSON,
         * XML, CSV, plain text, HTML, PDF) z ich pelnymi nazwami MIME.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementQualityValueParsing {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode parsujaca naglowek Accept Z WAGAMI "q" (np.
         * "text/html;q=0.8, application/json;q=0.9") i sortujaca wyniki
         * malejaco wg wagi - przetestuj dla min. 3 roznych naglowkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementFullNegotiationAlgorithm {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj kompletny algorytm negocjacji - dla listy
         * wspieranych formatow serwera i sparsowanego (z wagami) Accept
         * klienta, zwroc NAJLEPSZY wspolny format - przetestuj dla min.
         * 4 scenariuszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SupportThreeFormatsJsonCsvXml {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj endpoint zwracajacy TE SAME dane w 3 formatach
         * (JSON, CSV, prosty recznie zbudowany XML) w zaleznosci od
         * Accept - zweryfikuj poprawnosc kazdego formatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_HandleAcceptHeaderWithMultipleWildcards {
        /*
         * 🧪 Zadanie 14:
         * Obsluz naglowek typu "text/*;q=0.5, application/json" -
         * zaimplementuj dopasowanie CZESCIOWYCH wildcardow (typ/*)
         * oprocz pelnego "*\/*".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementContentTypeValidationForRequestBody {
        /*
         * 🧪 Zadanie 15:
         * Rozroznij "Accept" (co klient chce OTRZYMAC) od "Content-Type"
         * (co klient WYSYLA) - zaimplementuj endpoint POST, ktory
         * NIEZALEZNIE neguje format odpowiedzi (Accept) i waliduje
         * format przychodzacego ciala (Content-Type).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasurePerformanceImpactOfMultipleFormats {
        /*
         * 🧪 Zadanie 16:
         * Zmierz czas generowania odpowiedzi w kazdym z 3 formatow
         * (JSON/CSV/XML) dla tych samych, wiekszych danych (np. 1000
         * elementow) - porownaj i skomentuj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDefaultFormatWhenAcceptMissing {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj jawna obsluge BRAKU naglowka Accept (nie tylko
         * "*\/*") - zdefiniuj i udokumentuj w komentarzu, jaki jest
         * domyslny format serwera w takim przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildAcceptHeaderTestMatrix {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj "macierz testowa" - dla min. 6 roznych wartosci Accept
         * (w tym pustych, wildcardowych, z wagami, niewspieranych) wypisz
         * w tabeli oczekiwany i faktyczny wybrany format.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementAcceptLanguageAsSeparateNegotiation {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj OSOBNY mechanizm negocjacji dla naglowka
         * "Accept-Language" (np. "pl" vs "en") - zwroc te SAME dane z
         * roznymi etykietami/komunikatami w zaleznosci od jezyka -
         * w komentarzu wyjasnij, ze to INNY wymiar negocjacji niz format.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementVendorSpecificJsonMediaType {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj endpoint akceptujacy "Accept:
         * application/vnd.javaquest.v1+json" jako alternatywna nazwe dla
         * zwyklego "application/json" - zwroc naglowek Content-Type z
         * TA SAMA "vendor" nazwa w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementRfc7231CompliantAcceptParser {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PELNY parser naglowka Accept zgodny z regulami
         * RFC 7231 (specyficznosc typu > waga q > kolejnosc) - obsluz
         * przypadki, gdzie 2 wpisy maja TA SAMA wage, ale rozna
         * specyficznosc ("application/json" vs "application/*").
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementContentNegotiationMiddleware {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj generyczny "middleware" negocjacji, ktory MOZNA
         * podpiac pod DOWOLNY handler (przekazujac Mape format->funkcja
         * generujaca cialo) - przetestuj na min. 2 roznych endpointach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementFallbackChainForUnsupportedFormats {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj lancuch fallbackow: jesli zaden format z Accept
         * nie jest wspierany, sprobuj domyslnego formatu zamiast od razu
         * 406 - ale TYLKO jesli klient NIE wyslal jawnego Accept (odroznij
         * "brak naglowka" od "naglowek z niewspieranymi wartosciami").
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementContentEncodingNegotiation {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj DODATKOWA negocjacje po naglowku "Accept-Encoding"
         * (np. gzip vs identity) - jesli klient wspiera gzip, skompresuj
         * odpowiedz i ustaw "Content-Encoding: gzip".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildNegotiationAuditLog {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj logowanie KAZDEJ decyzji negocjacji (otrzymany
         * Accept, wybrany format, czy byl to fallback) do listy w pamieci -
         * po serii testow wypisz statystyke najczesciej wybieranych formatow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementPerClientDefaultFormatPreference {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj zapamietywanie preferencji formatu PER klient
         * (na podstawie np. naglowka X-Client-Id) - jesli klient wczesniej
         * uzyl konkretnego formatu i NIE wysyla Accept w kolejnym
         * requescie, uzyj zapamietanej preferencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareNegotiationStrategiesUriVsHeaderVsMediaType {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj TEN SAM endpoint w 3 wariantach negocjacji formatu:
         * (a) przez rozszerzenie w URI (ZLA praktyka, dla porownania),
         * (b) przez Accept, (c) przez custom naglowek "X-Format" - w
         * komentarzu podsumuj zalety/wady kazdego podejscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementStrictVsLenientNegotiationModes {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj 2 tryby negocjacji: SCISLY (406 przy braku
         * dopasowania) i LUZNY (zawsze zwraca domyslny format jako
         * fallback) - udostepnij je jako parametr konfiguracyjny serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_HandleMalformedAcceptHeaderGracefully {
        /*
         * 🧪 Zadanie 29:
         * Wyslij celowo ZLE SFORMATOWANY naglowek Accept (np. "q=abc"
         * zamiast liczby) - zaimplementuj odporne parsowanie, ktore NIE
         * rzuca wyjatku, tylko traktuje bledny wpis jako najnizszy priorytet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMultiFormatApiWithFullNegotiation {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne mini-API (min. 2 zasoby) wspierajace PELNA
         * negocjacje (Accept z wagami, Vary, 406, fallback, wildcardy) -
         * napisz "test suite" w `main()` weryfikujacy min. 8 roznych
         * kombinacji Accept i oczekiwanych wynikow.
         */
        public static void main(String[] args) { }
    }
}
