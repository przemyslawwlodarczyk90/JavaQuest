package com.example.javaquest._18_rest_api.Lesson19_RestVsRpcVsGraphQL;

public class _Exercises_Lesson19_RestVsRpcVsGraphQL {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCoreDifferenceRestVsRpc {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij podstawowa roznice miedzy REST (zasoby)
         * a RPC (funkcje) - podaj po 1 przykladowym endpoincie kazdego stylu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteJsonRpcStyleRequestExample {
        /*
         * 🧪 Zadanie 2:
         * Napisz (jako string) przykladowy request w stylu JSON-RPC dla
         * operacji "pobierz produkt o id=7".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteEquivalentRestRequestExample {
        /*
         * 🧪 Zadanie 3:
         * Napisz odpowiednik z Zadania 2 w stylu REST (metoda+URI) - porownaj oba.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteGraphQlQueryForSpecificFields {
        /*
         * 🧪 Zadanie 4:
         * Napisz (jako text block) przykladowe zapytanie GraphQL pobierajace
         * TYLKO 2 wybrane pola produktu (np. name i price).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_IdentifyOverFetchingInGivenResponse {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: dla podanej (przykladowej) odpowiedzi JSON z 8
         * polami, gdzie klient potrzebuje TYLKO 2, zidentyfikuj to jako
         * over-fetching i wyjasnij dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyUnderFetchingScenario {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: opisz scenariusz (min. 3 kroki), w ktorym klient
         * REST musi wykonac WIELE requestow, zeby zebrac dane, ktore
         * GraphQL zwrociloby w 1 zapytaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SimulateOverFetchingWithJavaRecord {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj rekord z 6 polami - zasymuluj GET zwracajacy CALY
         * rekord, mimo ze "klient" (komentarz) potrzebuje tylko 1 pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListThreeCriteriaFromComparisonTable {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wymien 3 kryteria z tabeli porownawczej z teorii
         * (endpointy, cache HTTP, typowy uzytek) - dla kazdego opisz
         * ROZNICE miedzy REST i GraphQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyGrpcUsesProtobuf {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego gRPC czesto uzywa Protocol
         * Buffers (binarny format) zamiast JSON - jaka to daje przewage
         * dla komunikacji miedzyserwisowej?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ChooseStyleForThreeScenarios {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: dla 3 scenariuszy (publiczne API dla partnerow,
         * komunikacja miedzy 2 wewnetrznymi mikroserwisami, backend dla
         * skomplikowanej aplikacji mobilnej) wybierz NAJLEPSZY styl (REST/
         * RPC/GraphQL) z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementFieldSelectionSimulationInRest {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj endpoint REST wspierajacy parametr `?fields=name,price`
         * (por. `_18_rest_api/Lesson08` Zadanie 28) jako CZESCIOWE
         * rozwiazanie over-fetchingu BEZ pelnego GraphQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementBffPatternToSolveUnderFetching {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj wzorzec "Backend For Frontend" (BFF) - 1 nowy
         * endpoint AGREGUJACY dane z 2-3 innych endpointow REST po
         * stronie serwera, zwracajacy je klientowi za 1 requestem -
         * alternatywne rozwiazanie under-fetchingu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementSimplifiedGraphQlLikeResolver {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj UPROSZCZONY "resolver" - metoda przyjmujaca liste
         * nazw pol (np. ["name", "orders.total"]) i zwracajaca TYLKO te
         * pola z zagniezdzonego obiektu, imitujac zachowanie GraphQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareCacheabilityOfRestVsGraphQlEndpoint {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj 2 endpointy - REST GET (cacheable, Lesson11) i
         * symulowany GraphQL POST (NIE cacheable HTTP-owo) - w
         * komentarzu wyjasnij, dlaczego POST z zapytaniem w ciele nie
         * korzysta z cache przegladarki/proxy tak samo latwo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRpcStyleEndpointForComparison {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj (przy pomocy `HttpServer`) endpoint w stylu RPC
         * (1 URI, metoda w ciele JSON) obok jego odpowiednika REST -
         * porownaj kod obu implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasurePayloadSizeRestVsFieldSelection {
        /*
         * 🧪 Zadanie 16:
         * Zmierz rozmiar odpowiedzi (bajty) dla pelnego obiektu (8 pol)
         * vs odpowiedzi z tylko 2 wybranymi polami (Zadanie 11) -
         * wypisz procentowa oszczednosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementMultiResourceAggregationEndpoint {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj endpoint zwracajacy dane uzytkownika WRAZ z jego
         * ostatnimi 3 zamowieniami W 1 ODPOWIEDZI (zamiast 2 osobnych
         * requestow) - zmierz roznice w liczbie "podrozy siec-serwer"
         * wzgledem podejscia z 2 osobnych GET-ow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainNPlusOneProblemInGraphQlContext {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: wyjasnij problem "N+1" w kontekscie GraphQL
         * (resolver wywolujacy zapytanie do bazy dla KAZDEGO elementu
         * listy osobno) - odniesienie do `_12_hibernate/Lesson15`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementVersioningComparisonAcrossStyles {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: porownaj wersjonowanie w REST (Lesson14: URI/
         * naglowek) z podejsciem GraphQL (zwykle BEZ wersjonowania -
         * ewolucja przez dodawanie pol, deprecating starych) - jaka to
         * roznica filozofii?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildDecisionMatrixForApiStyleSelection {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako tekst/Mape) "macierz decyzyjna" - dla min. 5
         * kryteriow (publiczne/wewnetrzne, potrzeba cache, roznorodnosc
         * klientow, wydajnosc, prostota) oceniajaca kazdy styl (REST/RPC/
         * GraphQL) w skali 1-5.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFullFieldSelectionEngineWithNesting {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj PELNY "silnik" selekcji pol wspierajacy ZAGNIEZDZONE
         * sciezki (np. "orders.items.name") - zwracajacy CZESCIOWY,
         * zagniezdzony obiekt zawierajacy TYLKO zadane pola, na wzor
         * prawdziwego resolvera GraphQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementHybridRestGraphQlGateway {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj "gateway" udostepniajacy TE SAME dane przez OBA
         * style naraz - klasyczne endpointy REST ORAZ 1 endpoint
         * przyjmujacy uproszczone "zapytania" JSON (lista pol/zasobow) -
         * WSPOLNA logika biznesowa pod spodem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementGrpcLikeContractWithStrictTypes {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (jako interfejsy Java + rekordy) SCISLY,
         * silnie typowany kontrakt "na wzor" Protocol Buffers dla 3
         * operacji - w komentarzu wyjasnij, jak silne typowanie
         * ZAPOBIEGA bledom, ktore latwo popelnic w luznym JSON-ie REST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCachingLayerForGraphQlLikeQueries {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj WLASNY mechanizm cache'owania (Mapa
         * zapytanie->wynik) dla symulowanego GraphQL, ktory NIE moze
         * korzystac z natywnego cache HTTP (Lesson11) - zaimplementuj
         * kluczowanie po TRESCI zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureNPlusOneImpactWithBatchingSolution {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj problem N+1 (Zadanie 18) dla listy 20 elementow -
         * zmierz liczbe "zapytan do bazy" (symulowane wywolania) BEZ
         * optymalizacji, a nastepnie zaimplementuj "DataLoader"-podobny
         * mechanizm batchowania i zmierz redukcje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSchemaFirstVsCodeFirstComparison {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: porownaj podejscie "schema-first" (najpierw
         * definiujesz kontrakt - OpenAPI/GraphQL SDL/Protobuf, potem
         * generujesz kod) z "code-first" (kod generuje kontrakt,
         * `_18_rest_api/Lesson18`) - zalety/wady kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRateLimitingForFieldSelectionComplexity {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj limit "kosztu zapytania" dla symulowanego
         * GraphQL (np. kazde zagniezdzone pole kosztuje punkty, zapytanie
         * przekraczajace budzet jest odrzucane) - realny problem w GraphQL
         * (klient MOZE zadac dowolnie zlozonego zapytania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementMigrationPathFromRestToGraphQl {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (opisz kroki + szkielet kodu) stopniowa migracje
         * istniejacego API REST (z tego rozdzialu) do warstwy GraphQL
         * DZIALAJACEJ NAD tymi samymi endpointami REST (wzorzec "GraphQL
         * jako fasada"), bez przepisywania logiki biznesowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildBenchmarkComparingAllThreeStylesForSameOperation {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj TA SAMA operacje (pobierz uzytkownika+zamowienia)
         * w 3 wariantach (REST wielo-requestowy, REST z BFF/agregacja,
         * symulowany GraphQL) - zmierz liczbe requestow HTTP i laczny
         * rozmiar danych dla kazdego wariantu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteDecisionFrameworkWithWorkedExample {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, DZIALAJACY przyklad (min. 3 zasoby) w stylu
         * REST z tego rozdzialu, a nastepnie zaimplementuj WARSTWE
         * agregujaca symulujaca GraphQL nad TYMI SAMYMI danymi - napisz
         * podsumowanie (komentarz) uzasadniajace, KIEDY warto dodac taka
         * warstwe, a kiedy czysty REST wystarczy.
         */
        public static void main(String[] args) { }
    }
}
