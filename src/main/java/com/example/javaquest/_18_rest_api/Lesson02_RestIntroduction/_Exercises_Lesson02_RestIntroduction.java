package com.example.javaquest._18_rest_api.Lesson02_RestIntroduction;

public class _Exercises_Lesson02_RestIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListSixRestConstraints {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien w komentarzu wszystkie 6 ograniczen REST
         * (client-server, stateless, cacheable, uniform interface, layered
         * system, code-on-demand) - dla kazdego 1 zdanie wyjasnienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhoFieldingWas {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij w komentarzu, kim byl Roy Fielding i w
         * jakim dokumencie (jakiego roku) opisal REST - dlaczego to NIE
         * jest "nowy protokol", tylko styl architektoniczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListFourUniformInterfaceSubconstraints {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wymien 4 podograniczenia "uniform interface"
         * (identyfikacja zasobow, manipulacja przez reprezentacje,
         * samoopisujace sie wiadomosci, HATEOAS) - po 1 zdaniu na kazde.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ClassifyEndpointByRichardsonLevel {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: dla 3 przykladowych endpointow - (a) "POST /api
         * cialo={action:'deleteUser',id:5}", (b) "POST /users/5/delete",
         * (c) "DELETE /users/5" - okresl poziom Richardsona (0/1/2) kazdego
         * z nich, z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_StartServerReturningCacheControlHeader {
        /*
         * 🧪 Zadanie 5:
         * Uruchom lokalny `HttpServer` z endpointem `/api/config`, ktory
         * zwraca naglowek "Cache-Control: public, max-age=30" - zweryfikuj
         * po stronie klienta, ze naglowek dotarl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyStatelessHelpsScaling {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij w komentarzu, dlaczego bezstanowosc
         * ulatwia SKALOWANIE POZIOME (dodawanie kolejnych instancji
         * serwera za load balancerem) w porownaniu do API trzymajacego
         * stan sesji w pamieci pojedynczego serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyLayeredSystemBenefit {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: opisz w komentarzu, dlaczego klient NIE powinien
         * zakladac, ze laczy sie BEZPOSREDNIO z serwerem aplikacyjnym (a
         * nie np. przez load balancer/CDN/API gateway) - jaka to daje
         * elastycznosc operacyjna?
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteRpcStyleEndpointAsLevel0Example {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj endpoint `/api` w stylu Poziomu 0 (RPC) - 1 endpoint,
         * cala "operacja" zakodowana w JSON ciala (np. {"action":"add",
         * "a":2,"b":3}) - serwer parsuje `action` i wykonuje odpowiednia
         * operacje. Zweryfikuj dla 2 roznych akcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_RefactorLevel0ToLevel2 {
        /*
         * 🧪 Zadanie 9:
         * Przeksztalc endpoint z Zadania 8 na Poziom 2 - osobne URI per
         * operacja (np. GET /api/add?a=2&b=3) z odpowiednia metoda HTTP -
         * porownaj obie wersje w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainCodeOnDemandConstraint {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij w komentarzu, na czym polega OPCJONALNE
         * ograniczenie "code-on-demand" i podaj 1 przyklad z realnego
         * swiata (niekoniecznie z API REST).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementSelfDescriptiveMessageWithContentType {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj endpoint zwracajacy TA SAMA informacje w 2 formatach
         * (JSON i zwykly tekst), wybierany na podstawie naglowka "Accept" -
         * zweryfikuj oba warianty (foreshadowing Lesson07: Content Negotiation).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildHateoasStyleResponse {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj endpoint `/api/orders/{id}`, ktory w odpowiedzi
         * JSON dodaje pole "_links" z linkami "self" i "cancel" (jak w
         * przykladzie Poziomu 3 z teorii) - zweryfikuj poprawnosc struktury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareCacheableVsNonCacheableEndpoint {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj 2 endpointy - jeden z "Cache-Control: no-store"
         * (np. saldo konta, zawsze aktualne), drugi z "Cache-Control:
         * max-age=300" (np. lista kategorii) - w komentarzu uzasadnij
         * roznice dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateClientIndependenceFromServerInternals {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj endpoint zwracajacy dane z WEWNETRZNEJ struktury
         * (np. rekord `InternalUserRecord`), ale wystawiajacy je jako INNY
         * ksztalt JSON (np. zmienione nazwy pol) - zademonstruj, ze klient
         * NIE musi znac wewnetrznej reprezentacji serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SimulateTwoIndependentClients {
        /*
         * 🧪 Zadanie 15:
         * Napisz 2 rozne "klienty" (np. jeden wysylajacy Accept: application/json,
         * drugi Accept: text/plain) laczace sie z TYM SAMYM serwerem -
         * zademonstruj, ze serwer obsluzy oba niezaleznie i poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ClassifyRealWorldApiEndpointsByLevel {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: dla 4 znanych Ci publicznych API (lub wymyslonych
         * przykladow z dokumentacji) okresl, na ktorym poziomie Richardsona
         * sie znajduja, z krotkim uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementStatelessAuthViaHeaderNotSession {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj endpoint wymagajacy naglowka "X-Api-Key" w KAZDYM
         * requescie (BEZ trzymania sesji po stronie serwera) - odrzuc
         * (401) requesty bez poprawnego klucza. Zweryfikuj dla 2 kolejnych,
         * niezaleznych requestow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureBenefitOfCachingWithSimulatedLatency {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj endpoint z celowym opoznieniem (np. Thread.sleep(200))
         * symulujacym drogie obliczenie - po stronie klienta zaimplementuj
         * prosty cache w pamieci respektujacy Cache-Control: max-age -
         * zmierz roznice czasu miedzy 1. a 2. requestem do tego samego zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DiscussTradeoffOfFullHateoas {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: opisz w komentarzu min. 1 zalete i 1 wade pelnego
         * wdrozenia HATEOAS (Poziom 3) w porownaniu do zatrzymania sie na
         * Poziomie 2 - kiedy dodatkowa zlozonosc jest tego warta?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildResourceWithMultipleActionLinks {
        /*
         * 🧪 Zadanie 20:
         * Rozszerz endpoint z Zadania 12 - w zaleznosci od STANU zasobu
         * (np. zamowienie juz anulowane vs aktywne) zwracaj ROZNE dostepne
         * linki akcji (np. brak linku "cancel" dla juz anulowanego) -
         * zweryfikuj dla obu stanow.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullLevel3HypermediaApi {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj mini-API (min. 2 zasoby, np. /orders i /orders/{id}) w
         * PELNI na Poziomie 3 - kazda odpowiedz zawiera WSZYSTKIE mozliwe
         * kolejne kroki jako linki, klient NIE ma "na sztywno" zakodowanego
         * ZADNEGO URL poza punktem wejscia ("/").
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementHypermediaAwareClientFollowingLinks {
        /*
         * 🧪 Zadanie 22:
         * Napisz klienta, ktory zaczyna od "/" API z Zadania 21 i
         * "PODAZA" za linkami (`_links.orders.href`), zamiast miec URL
         * zakodowane na sztywno - zademonstruj dojscie do zasobu bez
         * znajomosci jego finalnego URL z gory.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementConditionalCachingWithEtag {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj endpoint zwracajacy naglowek "ETag" (np. hash
         * zawartosci) - po stronie klienta wyslij 2. request z naglowkiem
         * "If-None-Match" - zweryfikuj, ze serwer moze odpowiedziec 304
         * Not Modified (pelna mechanika w Lesson11 - tu tylko szkic).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureLatencyImpactOfLayeredSystemViaProxy {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prosty "proxy" (kolejny `HttpServer`), ktory
         * przekazuje request do "prawdziwego" serwera i odsyla odpowiedz -
         * zmierz i porownaj czas requestu BEZPOSREDNIO do serwera vs przez
         * proxy - zademonstruj koszt/korzysc warstwy posredniej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementLevel1ThenUpgradeToLevel2Migration {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj API na Poziomie 1 (osobne URI, ale zawsze POST) dla
         * operacji na "produktach" (get/create/delete jako osobne sciezki),
         * a nastepnie PRZEBUDUJ je do Poziomu 2 (wlasciwe metody HTTP) -
         * zachowaj DZIALAJACE testy/wywolania przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignApiForMultipleClientTypesStatelessly {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj (i zaimplementuj podstawowy szkielet) API obslugujace
         * jednoczesnie klienta webowego i mobilnego - bez ZADNEGO stanu
         * sesji po stronie serwera (tozsamosc zawsze w naglowku) -
         * zweryfikuj dzialanie dla 2 "symulowanych" typow klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EvaluateApiAgainstAllSixConstraints {
        /*
         * 🧪 Zadanie 27:
         * Wez API zbudowane w Zadaniu 21 (lub innym wczesniejszym cwiczeniu)
         * i w komentarzu OCEN je wzgledem WSZYSTKICH 6 ograniczen REST -
         * dla kazdego: spelnione/niespelnione + uzasadnienie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementNegativeCachingForErrorResponses {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj endpoint zwracajacy 404 z naglowkiem
         * "Cache-Control: max-age=10" dla nieistniejacego zasobu -
         * uzasadnij w komentarzu, kiedy cache'owanie NEGATYWNYCH
         * odpowiedzi ma sens (np. ograniczenie obciazenia dla nieistniejacych ID).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareRestApiWithSoapConceptually {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj w komentarzu REST z SOAP (starszy
         * standard oparty na XML i sztywnych kontraktach WSDL) - min. 3
         * roznice (format, elastycznosc, typowe zastosowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildComplianceCheckerForRestConstraints {
        /*
         * 🧪 Zadanie 30:
         * Napisz narzedzie (klase), ktore wysyla requesty do podanego
         * lokalnego API i AUTOMATYCZNIE sprawdza czesc ograniczen REST
         * (np. czy odpowiedzi maja Content-Type, czy sa naglowki Cache-Control,
         * czy status code jest sensowny dla danej metody) - wypisz raport.
         */
        public static void main(String[] args) { }
    }
}
