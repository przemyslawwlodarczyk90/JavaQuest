package com.example.javaquest._18_rest_api.Lesson03_ResourcesAndEndpoints;

public class _Exercises_Lesson03_ResourcesAndEndpoints {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainNounVsVerbInUri {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij w komentarzu zasade "URI = rzeczownik,
         * metoda HTTP = czasownik" - podaj 2 przyklady zlych URI (jak
         * czasownik) i ich poprawniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FixFiveBadUris {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: popraw 5 URI zlamanych konwencji: "/getAllUsers",
         * "/User/42", "/order_items", "/users/42.xml", "/users/" -
         * dla kazdego napisz poprawiona wersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_StartServerWithUsersCollectionEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Uruchom lokalny `HttpServer` z endpointem `/products` zwracajacym
         * liste min. 3 produktow jako JSON (jako Mape w pamieci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddSingleResourceEndpoint {
        /*
         * 🧪 Zadanie 4:
         * Rozszerz serwer z Zadania 3 o `/products/{id}` zwracajacy JEDEN
         * produkt po ID - zwroc 404, jesli ID nie istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyPluralNotSingular {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, dlaczego stosuje sie liczbe mnoga
         * NAWET dla pojedynczego zasobu (/users/42, nie /user/42) -
         * co to zapewnia w kontekscie spojnosci API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListThreeReasonsToAvoidFileExtensions {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: podaj min. 2 powody, dla ktorych URI NIE powinno
         * zawierac rozszerzenia pliku (np. /users/42.json), skoro format
         * odpowiedzi da sie okreslic inaczej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementNestedOrdersResource {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj endpoint `/customers/{id}/orders` zwracajacy
         * zamowienia NALEZACE DO danego klienta (min. 2 klientow, kazdy z
         * inna liczba zamowien w pamieci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareTrailingSlashVariants {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj serwer, ktory rejestruje TYLKO `/products` (bez
         * koncowego ukosnika) - sprawdz, jak zachowuje sie request do
         * `/products/` (z ukosnikiem) - opisz w komentarzu obserwacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MoveIdFromQueryToPath {
        /*
         * 🧪 Zadanie 9:
         * Masz endpoint `/users?id=42` (ZLY wzorzec) - przepisz go na
         * `/users/42` (dobry wzorzec) i zweryfikuj identyczny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFiveResourcesForBlogApi {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: dla aplikacji "blog" (posty, komentarze,
         * autorzy, tagi, kategorie) zaprojektuj 5 nazw kolekcji URI
         * zgodnych z konwencjami z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementThreeLevelResourceHierarchy {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `/schools/{schoolId}/classes/{classId}/students` -
         * 3 poziomy zagniezdzenia - zwroc uczniow NALEZACYCH do konkretnej
         * klasy konkretnej szkoly (dane w pamieci, min. 2 szkoly).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FlattenDeeplyNestedResource {
        /*
         * 🧪 Zadanie 12:
         * Wez hipotetyczny "zbyt gleboki" URI
         * `/schools/1/classes/2/students/3/grades/4/comments` i przeprojektuj
         * go na "plaski" zasob z parametrami filtrujacymi (np.
         * `/comments?gradeId=4`) - zaimplementuj i zweryfikuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DistinguishCollectionAndElementForSameId {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj `/tags` (kolekcja) i `/tags/{id}` (element) tak,
         * zeby oba dzialaly poprawnie na TYM SAMYM kontekscie (`/tags`)
         * roznicujac sciezke wewnatrz handlera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSecondaryKeyLookup {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj `/users/by-email/{email}` jako alternatywny
         * sposob wyszukania zasobu po INNYM niz ID kluczu - w komentarzu
         * wyjasnij, dlaczego to WCIAZ jest zgodne z konwencja "rzeczownik+ID".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidateAndRejectMalformedResourceId {
        /*
         * 🧪 Zadanie 15:
         * Rozszerz `/products/{id}` tak, zeby dla ID, ktore NIE jest liczba
         * (np. "/products/abc"), zwracal 400 zamiast rzucac wyjatek parsowania
         * (`NumberFormatException`) - zweryfikuj oba przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignUriSchemeForECommerceApi {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: zaprojektuj kompletny schemat URI (min. 6 endpointow)
         * dla sklepu internetowego (produkty, kategorie, koszyk, zamowienia,
         * uzytkownicy, recenzje) - zachowaj konwencje z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementApiFromExercise16Skeleton {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj SZKIELET (routing bez pelnej logiki biznesowej)
         * endpointow zaprojektowanych w Zadaniu 16 - kazdy zwraca prosty
         * placeholder JSON, ale sciezki i metody musza byc poprawne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareResourceOrientedVsRpcStyleForSameFeature {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj TA SAMA funkcjonalnosc (np. "aktywuj konto
         * uzytkownika") w 2 stylach: RPC (`POST /activateUser?id=42`) i
         * zasobowym (`POST /users/42/activation`) - porownaj w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HandleAmbiguousPathSegmentCount {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj handler dla `/users/*`, ktory poprawnie rozroznia
         * `/users` (kolekcja), `/users/42` (element), `/users/42/orders`
         * (zagniezdzona kolekcja) i `/users/42/orders/7` (zagniezdzony
         * element) na podstawie liczby segmentow sciezki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorFlatToNestedWhenRelationshipMatters {
        /*
         * 🧪 Zadanie 20:
         * Masz plaski endpoint `/orders?customerId=42` - przepisz go na
         * zagniezdzony `/customers/42/orders` TYLKO jesli relacja
         * "zamowienie NALEZY DO klienta" jest kluczowa dla API - w
         * komentarzu uzasadnij, KIEDY plaski wzorzec jest lepszy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildGenericRouterMatchingUriPatterns {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj prosty "router" - klase mapujaca wzorce URI (np.
         * "/users/{id}/orders/{orderId}") na handlery, z automatycznym
         * wyciaganiem wartosci placeholderow ({id}, {orderId}) do Mapy -
         * przetestuj dla min. 3 roznych wzorcow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementResourceVersioningInPathPrefix {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj `/v1/users` i `/v2/users` zwracajace INNY ksztalt
         * odpowiedzi dla tego samego zasobu logicznego (foreshadowing
         * Lesson14: Versioning) - zweryfikuj oba dzialajace niezaleznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignAndImplementSelfLinkingCollection {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj `/products`, gdzie KAZDY element listy zawiera pole
         * "self" z pelnym URI do tego konkretnego elementu (np.
         * "/products/7") - zweryfikuj poprawnosc dla min. 3 elementow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HandleCaseSensitivityInUriMatching {
        /*
         * 🧪 Zadanie 24:
         * Zbadaj (i opisz w komentarzu) zachowanie Twojego routera/serwera
         * dla `/Users/42` vs `/users/42` (rozna wielkosc liter) - zaimplementuj
         * SWIADOMA decyzje (odrzuc jako rozne zasoby LUB znormalizuj).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementBulkResourceLookupEndpoint {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj `/users/batch?ids=1,2,5` zwracajacy WIELE zasobow
         * naraz po liscie ID - w komentarzu uzasadnij, dlaczego to WYJATEK
         * od "1 URI = 1 zasob" i kiedy taki wzorzec ma sens (unikanie N
         * osobnych requestow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MigrateRpcStyleLegacyApiToResourceOriented {
        /*
         * 🧪 Zadanie 26:
         * Masz "legacy" API w stylu RPC (min. 4 endpointy typu
         * `/doSomethingWithUser`) - zaprojektuj I zaimplementuj ich pelny
         * odpowiednik zasobowy, zachowujac IDENTYCZNA funkcjonalnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementResourceAliasingWithRedirect {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj `/me` jako "alias" przekierowujacy (301/302,
         * naglowek Location) do `/users/{aktualnieZalogowanyId}` - zweryfikuj
         * poprawny kod statusu i naglowek Location.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ValidateUriDesignAgainstChecklistProgrammatically {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode `List<String> validateUri(String uri)` sprawdzajaca
         * automatycznie reguly z tej lekcji (liczba mnoga w segmentach
         * nie-numerycznych, brak koncowego ukosnika, brak rozszerzen,
         * male litery) - zwroc liste naruszen. Przetestuj dla min. 5 URI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignSubResourceForRelationshipManagement {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj i zaimplementuj `/users/{id}/roles` jako zasob
         * reprezentujacy relacje many-to-many (uzytkownik ma role) - z
         * GET (lista rol), POST (przypisz role), DELETE
         * `/users/{id}/roles/{roleId}` (usun konkretna role).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteResourceMapForMultiModuleApp {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj I zaimplementuj (jako szkielet routingu) kompletna
         * mape zasobow (min. 10 endpointow) dla systemu "platforma
         * kursow online" (kursy, lekcje, kursanci, zapisy, oceny,
         * certyfikaty) - w pelni zgodna z konwencjami tej lekcji, wlacznie
         * z odpowiednim poziomem zagniezdzenia.
         */
        public static void main(String[] args) { }
    }
}
