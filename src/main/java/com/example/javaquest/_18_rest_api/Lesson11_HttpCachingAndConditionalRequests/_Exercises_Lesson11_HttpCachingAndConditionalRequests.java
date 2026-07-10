package com.example.javaquest._18_rest_api.Lesson11_HttpCachingAndConditionalRequests;

public class _Exercises_Lesson11_HttpCachingAndConditionalRequests {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListFourCacheControlDirectives {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 4 dyrektywy Cache-Control (max-age,
         * no-cache, no-store, private/public) - po 1 zdaniu wyjasnienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementEndpointWithMaxAge {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/config` zwracajacym naglowek
         * "Cache-Control: max-age=120" - zweryfikuj po stronie klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ComputeEtagFromContent {
        /*
         * 🧪 Zadanie 3:
         * Napisz metode obliczajaca ETag (hash SHA-256, skrocony) dla
         * podanego stringa - zweryfikuj, ze IDENTYCZNA tresc daje TEN SAM
         * ETag, a RÓZNA tresc daje INNY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementGetReturningEtagHeader {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj GET zwracajacy naglowek "ETag" obliczony z
         * aktualnej tresci zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementConditionalGetReturning304 {
        /*
         * 🧪 Zadanie 5:
         * Rozszerz Zadanie 4 o obsluge "If-None-Match" - jesli klient
         * przesle AKTUALNY ETag, zwroc 304 Not Modified BEZ ciala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VerifyEtagChangesAfterModification {
        /*
         * 🧪 Zadanie 6:
         * Zmodyfikuj zasob (np. PUT) i zweryfikuj, ze ETag pobrany PRZED
         * zmiana ROZNI SIE od ETag pobranego PO zmianie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainDifferenceNoStoreVsNoCache {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij roznice miedzy "no-store" a "no-cache" -
         * podaj przyklad danych, dla ktorych uzylbys kazdej z tych dyrektyw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementPreconditionFailedFor412 {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj PUT wymagajacy naglowka "If-Match" - zwroc 412,
         * jesli przeslany ETag NIE zgadza sie z aktualnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareEtagWithLastModified {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: porownaj ETag i Last-Modified jako mechanizmy
         * weryfikacji "czy zasob sie zmienil" - kiedy ETag jest
         * dokladniejszy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainPrivateVsPublicCacheControl {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij roznice miedzy "private" a "public" w
         * Cache-Control - podaj przyklad zasobu wymagajacego kazdej z nich.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementLastModifiedAndIfModifiedSince {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj alternatywny mechanizm - naglowek "Last-Modified"
         * (czas ostatniej zmiany) i obsluge "If-Modified-Since" -> 304,
         * jesli zasob nie zmienil sie od podanego czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementOptimisticConcurrencyWithIfMatch {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj pelny scenariusz optymistycznej wspolbieznosci:
         * 2 "uzytkownicy" pobieraja ten sam zasob, 1. modyfikuje go z
         * poprawnym If-Match (sukces), 2. probuje z NIEAKTUALNYM
         * If-Match (412) - zweryfikuj obie sciezki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementVaryHeaderForCacheCorrectness {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj endpoint z content negotiation (Lesson07) I
         * cache'owaniem naraz - dodaj naglowek "Vary: Accept", zeby
         * posrednie cache NIE pomylily odpowiedzi w roznych formatach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasureBandwidthSavedWithConditionalGet {
        /*
         * 🧪 Zadanie 14:
         * Zmierz i porownaj laczna liczbe przeslanych bajtow dla 10x GET
         * BEZ warunkowego zapytania vs 10x GET Z If-None-Match (gdzie
         * zasob sie NIE zmienia) - wypisz oszczednosc procentowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementClientSideCacheRespectingMaxAge {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj prosty cache PO STRONIE KLIENTA (Mapa w pamieci) -
         * przed wyslaniem GET sprawdz, czy poprzednia odpowiedz jest
         * jeszcze "swieza" wedlug max-age - jesli tak, NIE wysylaj requestu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementWeakVsStrongEtag {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj ETag "slaby" (prefiks "W/", oznacza "semantycznie
         * rownowazne", np. ignorujac formatowanie) i "silny" (dokladna
         * bajt-po-bajcie rownosc) - w komentarzu wyjasnij roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementDeleteWithIfMatchProtection {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj DELETE wymagajacy If-Match - zapobiegnij
         * usunieciu zasobu, ktory zostal zmieniony przez kogos innego od
         * momentu, gdy klient go ostatnio pobral.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCacheableCollectionWithSharedEtag {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj ETag dla CALEJ KOLEKCJI (np. hash wszystkich ID +
         * ich wersji) - zwroc 304 dla GET /items, jesli ZADEN element nie
         * zmienil sie od ostatniego zapytania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HandleMultipleETagsInIfNoneMatch {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj obsluge WIELU wartosci w "If-None-Match" (np.
         * "\"etag1\", \"etag2\""), zgodnie ze specyfikacja HTTP - 304,
         * jesli KTORYKOLWIEK z podanych ETagow pasuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementCacheInvalidationOnRelatedResourceChange {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj scenariusz, gdzie zmiana zasobu A (np. autor)
         * wymusza zmiane ETag POWIAZANEGO zasobu B (np. lista jego ksiazek) -
         * zweryfikuj kaskadowa inwalidacje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFullRfc7232ConditionalRequestLogic {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PELNA logike zapytan warunkowych zgodna z RFC 7232:
         * If-Match, If-None-Match, If-Modified-Since, If-Unmodified-Since -
         * z poprawnym priorytetem miedzy nimi, gdy klient wysle wiecej niz
         * 1 naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildHttpCacheSimulatorWithEviction {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj symulator cache HTTP (Mapa URL->odpowiedz+czas
         * wygasniecia) respektujacy max-age I automatycznie usuwajacy
         * (evict) wpisy po wygasnieciu - zweryfikuj dla min. 3 zasobow z
         * roznymi czasami wygasniecia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementConcurrentUpdateStressTest {
        /*
         * 🧪 Zadanie 23:
         * Uzywajac wielu watkow, wyslij 10 "jednoczesnych" PUT z TYM SAMYM,
         * poczatkowym ETag do 1 zasobu - zweryfikuj, ze DOKLADNIE 1
         * zakonczyl sie sukcesem (200), a reszta 412 (optymistyczna
         * kontrola wspolbieznosci pod obciazeniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCacheControlPerFieldSensitivity {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj 2 endpointy dla POWIAZANYCH danych o roznej
         * wrazliwosci (np. profil publiczny - cacheable, saldo konta -
         * no-store) - w komentarzu uzasadnij kazda decyzje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDistributedCacheInvalidationSimulation {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj 2 "instancje" serwera dzielace TEN SAM magazyn danych,
         * ale KAZDA z WLASNYM lokalnym cache - zaimplementuj mechanizm
         * (np. wspolna "wersja" w magazynie) wymuszajacy inwalidacje
         * lokalnego cache przy zmianie danych przez INNA instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementStaleWhileRevalidatePattern {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj wzorzec "stale-while-revalidate" - klient dostaje
         * NIEAKTUALNA (ale szybka) odpowiedz z cache, podczas gdy w tle
         * (asynchronicznie) trwa odswiezanie danych na kolejne zapytanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildEtagBasedDeduplicationForBulkSync {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj endpoint synchronizacji "bulk" - klient przesyla
         * liste (ID, ETag) posiadanych lokalnie zasobow, serwer zwraca
         * TYLKO te, ktore faktycznie SIE ZMIENILY (roznica ETag) -
         * oszczednosc transferu dla duzych zbiorow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasureEtagComputationOverheadAtScale {
        /*
         * 🧪 Zadanie 28:
         * Zmierz koszt obliczania ETag (SHA-256) dla 10 000 zapytan o
         * duzy zasob (np. 100KB tekstu) - porownaj z kosztem po prostu
         * ZAWSZE zwracania pelnej odpowiedzi - skomentuj kompromis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementConditionalRequestsAcrossRelatedEndpoints {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj mini-API z 3 powiazanymi zasobami, gdzie KAZDY
         * wspiera pelna negocjacje warunkowa (ETag+If-Match/If-None-Match) -
         * napisz "test suite" weryfikujacy poprawnosc dla min. 6 scenariuszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteCachingAwareRestClient {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletnego klienta REST respektujacego Cache-Control I
         * ETag AUTOMATYCZNIE (bez recznego zarzadzania przez uzytkownika
         * klienta) - transparentnie zwraca dane z lokalnego cache, gdy to
         * mozliwe, w przeciwnym razie wysyla zapytanie warunkowe.
         */
        public static void main(String[] args) { }
    }
}
