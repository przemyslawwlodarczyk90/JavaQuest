package com.example.javaquest._18_rest_api.Lesson09_PathVariablesAndQueryParams;

public class _Exercises_Lesson09_PathVariablesAndQueryParams {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPathVariableVsQueryParamRule {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij regule "usuniecie parametru zmienia
         * KTORY zasob = path variable, zmienia TYLKO KTORE elementy =
         * query param" - podaj po 2 przyklady kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementSinglePathVariableEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/books/{id}` odczytujacym ID ze sciezki
         * i zwracajacym odpowiedni obiekt z listy w pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementSingleQueryParamFilter {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz serwer z Zadania 2 o `/books?author=...` filtrujacy
         * liste po autorze - zweryfikuj dla istniejacego i nieistniejacego autora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParseQueryStringManually {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode `Map<String,String> parseSimpleQuery(String raw)`
         * parsujaca prosty query string (bez wielokrotnych kluczy) na Mape.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HandleMissingQueryParamWithDefault {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj `/books?limit=...` z domyslna wartoscia limit=10,
         * jesli parametr nie zostal podany - zweryfikuj dla obu przypadkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_Return404ForMissingPathVariableResource {
        /*
         * 🧪 Zadanie 6:
         * Zweryfikuj, ze `/books/{id}` z Zadania 2 zwraca 404 dla ID,
         * ktorego NIE MA na liscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UrlEncodeAndDecodeValueRoundTrip {
        /*
         * 🧪 Zadanie 7:
         * Zakoduj (`URLEncoder`) string zawierajacy spacje i polskie znaki,
         * a nastepnie zdekoduj (`URLDecoder`) - zweryfikuj, ze wynik jest
         * identyczny z oryginalem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementNestedResourcePathVariables {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj `/authors/{authorId}/books/{bookId}` - odczytaj
         * OBA path variables i zweryfikuj poprawne dopasowanie danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ClassifyFiveParametersAsPathOrQuery {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: dla 5 parametrow (ID uzytkownika, numer strony,
         * kod kraju filtra, ID zamowienia zagniezdzonego, limit wynikow)
         * okresl, czy powinny byc path variable czy query param.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ImplementBooleanQueryParamParsing {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj parsowanie query param "?active=true/false" na
         * `boolean` - obsluz rowniez BRAK parametru (domyslnie false).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementMultiValueQueryParamRepeatedKey {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `/books?tag=fiction&tag=bestseller` - zbierz
         * WSZYSTKIE wartosci "tag" do listy i filtruj ksiazki majace
         * KTORYKOLWIEK z podanych tagow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementMultiValueQueryParamCommaSeparated {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj TA SAMA funkcjonalnosc co Zadanie 11, ale z
         * konwencja "?tags=fiction,bestseller" (1 klucz, wartosci
         * rozdzielone przecinkiem) - porownaj kod obu podejsc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementNumericPathVariableValidation {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz `/books/{id}` tak, zeby dla ID, ktore NIE jest liczba
         * (np. "/books/abc"), zwracal 400 zamiast rzucac
         * `NumberFormatException` az do klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCombinedFilterAndSearch {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj `/books?author=...&minYear=...&maxYear=...` -
         * lacz WSZYSTKIE podane filtry (AND) - zweryfikuj dla roznych
         * kombinacji obecnych/nieobecnych parametrow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementUuidAsPathVariable {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj zasob identyfikowany przez UUID zamiast liczby
         * calkowitej (`/sessions/{uuid}`) - wygeneruj i zweryfikuj dla
         * min. 2 roznych sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementSlugBasedPathVariable {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj `/articles/{slug}` gdzie slug to czytelny dla
         * czlowieka identyfikator tekstowy (np. "wprowadzenie-do-rest")
         * zamiast liczby - w komentarzu wyjasnij zalety/wady tego podejscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_HandleUrlEncodedSpecialCharactersInPath {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj obsluge path variable zawierajacej znaki specjalne
         * wymagajace kodowania (np. slug z ukosnikiem "%2F") - zweryfikuj
         * poprawne dekodowanie po stronie serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ValidateQueryParamValueRange {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj walidacje query param "?rating=1-5" - zwroc 400,
         * jesli podana wartosc jest poza dozwolonym zakresem (np. rating=10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementCaseInsensitiveQueryParamValues {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj filtrowanie po query param "?category=Tech" tak,
         * zeby dzialalo NIEZALEZNIE od wielkosci liter (Tech/tech/TECH
         * dają ten sam wynik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildQueryParamsToStringBuilder {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode `String buildQueryString(Map<String,List<String>>
         * params)` budujaca poprawnie zakodowany query string z Mapy
         * (wlacznie z wielokrotnymi wartosciami tego samego klucza).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildGenericPathVariableExtractor {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj generyczna metode wyciagajaca WSZYSTKIE path
         * variables ze sciezki na podstawie wzorca (np. wzorzec
         * "/authors/{authorId}/books/{bookId}" + faktyczna sciezka ->
         * Mapa {authorId=5, bookId=12}) - przetestuj dla min. 3 wzorcow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullQueryParamParserWithArraysAndTypes {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj kompletny parser query stringa obslugujacy:
         * proste wartosci, wielokrotne klucze (listy), oraz automatyczna
         * konwersje typow (String -> int/boolean/double) na podstawie
         * oczekiwanego typu docelowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementQueryParamWhitelisting {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj endpoint odrzucajacy (400) requesty z
         * NIEZNANYMI query params (np. "?katgeoria=tech" - literowka) -
         * zwroc czytelny komunikat sugerujacy poprawna nazwe parametru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementPathVariableTypeCoercionFramework {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj mini-framework konwertujacy path variables na WLASCIWE
         * typy Java (int, UUID, enum) na podstawie deklaracji - zwroc 400
         * przy niepowodzeniu konwersji, z informacja KTORY segment zawiodl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDeepLinkingWithQueryStateSerialization {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj serializacje/deserializacje ZLOZONEGO stanu
         * filtrowania (kilka pol, zakresy, sortowanie) DO i Z query
         * stringa, tak zeby URL mozna bylo "zapisac w zakladkach" i
         * odtworzyc dokladnie ten sam widok.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasurePerformanceOfQueryParsingApproaches {
        /*
         * 🧪 Zadanie 26:
         * Porownaj wydajnosc recznego parsowania query stringa (split+decode)
         * vs uzycia `java.net.URI`/wbudowanych metod dla 10 000 wywolan -
         * zmierz i wypisz roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRegexBasedPathMatching {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj dopasowywanie sciezek przez wyrazenia regularne
         * (np. `/books/(\d+)` vs `/books/([a-z-]+)` dla ID numerycznego
         * vs sluga) - obsluz OBA formaty tego samego zasobu jednoczesnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementQueryParamInjectionPrevention {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj bezpieczne uzycie wartosci query param w
         * zapytaniu "SQL-podobnym" (symulowanym) - zademonstruj, co by
         * sie stalo przy naiwnej konkatenacji stringow z query param
         * (foreshadowing `_19_security_basics/Lesson13`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullRoutingTableWithMixedPathAndQuery {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj kompletna tabele routingu (min. 6 wzorcow) mieszajaca
         * path variables i query params dla realistycznego mini-API
         * (np. biblioteka: ksiazki, autorzy, wypozyczenia) - zweryfikuj
         * poprawne dopasowanie dla min. 10 przykladowych requestow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ImplementQueryParamBasedApiVersionNegotiation {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj wersjonowanie API przez query param
         * ("?version=2") jako ALTERNATYWE dla wersjonowania w URI/naglowku
         * (Lesson07/14) - porownaj w komentarzu wszystkie 3 podejscia
         * poznane w tym rozdziale.
         */
        public static void main(String[] args) { }
    }
}
