package com.example.javaquest._18_rest_api.Lesson08_JsonApiDesign;

public class _Exercises_Lesson08_JsonApiDesign {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyNamingConsistencyMatters {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, dlaczego mieszanie camelCase i
         * snake_case w JEDNYM API jest problemem - podaj konkretny
         * przyklad niespojnego JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ConvertSnakeCaseToCamelCaseJson {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj JSON w snake_case ({"first_name":..., "last_name":...})
         * a nastepnie recznie napisz jego odpowiednik w camelCase - wypisz
         * oba obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FormatDateTimeAsIso8601 {
        /*
         * 🧪 Zadanie 3:
         * Uzywajac `Instant`/`DateTimeFormatter` sformatuj biezacy czas
         * jako ISO-8601 i wstaw do JSON jako pole "timestamp".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixStringifiedNumbersInJson {
        /*
         * 🧪 Zadanie 4:
         * Masz JSON {"price":"49.99","quantity":"3"} - popraw go tak, zeby
         * "price" i "quantity" byly PRAWDZIWYMI liczbami, nie stringami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConvertMapKeyedByIdToArray {
        /*
         * 🧪 Zadanie 5:
         * Masz JSON {"1":{"name":"A"},"2":{"name":"B"}} (obiekt kluczowany
         * po ID) - przeksztalc go na tablice obiektow z jawnym polem "id".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DemonstrateNullVsMissingField {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj (uzywajac Gson) 2 JSON-y dla tego samego "profilu" -
         * jeden z jawnym `null` dla pola "bio", drugi z CALKOWICIE
         * pominietym polem "bio" - porownaj wynikowe stringi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyOverNestedJsonAndFlattenIt {
        /*
         * 🧪 Zadanie 7:
         * Masz JSON z 3 zbednymi poziomami zagniezdzenia wokol 1 wartosci -
         * splaszcz go recznie do najprostszej mozliwej postaci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListFiveJsonDesignRules {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wymien 5 zasad dobrego projektowania JSON z tej
         * lekcji - po 1 zdaniu uzasadnienia kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BuildProductJsonFollowingAllRules {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj JSON dla zasobu "produkt" (id, nazwa, cena, data
         * dodania, dostepnosc) stosujac WSZYSTKIE zasady z tej lekcji -
         * wypisz i zweryfikuj kazda zasade w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareBooleanRepresentations {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj 3 sposoby reprezentacji wartosci
         * logicznej w JSON (prawdziwy `true`/`false`, string "true"/"false",
         * liczba 1/0) - ktory jest poprawny wg tej lekcji i dlaczego?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildConsistentNamingConverterUtility {
        /*
         * 🧪 Zadanie 11:
         * Napisz metode `String toCamelCase(String snakeCaseInput)` i
         * `String toSnakeCase(String camelCaseInput)` - przetestuj oba
         * kierunki konwersji dla min. 5 nazw pol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementJsonSchemaAwareValidator {
        /*
         * 🧪 Zadanie 12:
         * Napisz walidator sprawdzajacy, czy podany JSON (Mapa po
         * sparsowaniu Gson) uzywa SPOJNEJ konwencji nazewnictwa (wszystkie
         * klucze camelCase LUB wszystkie snake_case) - zwroc liste
         * naruszen dla mieszanego przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDateTimeRoundTripValidation {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj walidacje, ze podany string daty jest POPRAWNYM
         * ISO-8601 (uzyj `DateTimeFormatter.ISO_INSTANT` do parsowania) -
         * przetestuj dla poprawnych i niepoprawnych formatow (np.
         * "10.07.2026").
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildTypeSafeJsonComparisonTool {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode wykrywajaca "stringified" liczby/booleany w JSON
         * (Mapa po sparsowaniu) - dla kazdej wartosci typu String sprawdz,
         * czy DA SIE ja sparsowac jako liczbe/boolean i zglos to jako
         * potencjalny problem projektowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignNestedAddressWithinUserResource {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj JSON dla zasobu "uzytkownik" z ZAGNIEZDZONYM
         * obiektem "address" (ulica, miasto, kod pocztowy) - w komentarzu
         * uzasadnij, DLACZEGO to zagniezdzenie MA sens (w odroznieniu od
         * przykladu z teorii, gdzie NIE mialo sensu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementArrayOfObjectsWithConsistentShape {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj tablice 5 obiektow "zamowienie" - zweryfikuj (napisz
         * kod sprawdzajacy), ze KAZDY element ma DOKLADNIE ten sam zestaw
         * kluczy (spojnosc ksztaltu w kolekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareGsonSerializeNullsOnOff {
        /*
         * 🧪 Zadanie 17:
         * Porownaj wyjscie `Gson` z i bez `.serializeNulls()` dla obiektu
         * z polem `null` - wyjasnij w komentarzu, KIEDY chcesz jawnie
         * widziec null w JSON, a kiedy wolisz go pominac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildFlatDtoFromDeeplyNestedDomainModel {
        /*
         * 🧪 Zadanie 18:
         * Masz model domenowy z 3 poziomami zagniezdzenia (Order ->
         * Customer -> Address) - zbuduj SPLASZCZONE DTO zawierajace tylko
         * pola RZECZYWISCIE potrzebne klientowi API (np. orderId,
         * customerName, city).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementEnumSerializationAsString {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj serializacje enuma (np. `OrderStatus`) jako
         * CZYTELNY string (np. "SHIPPED"), NIE jako liczbe porzadkowa (0,
         * 1, 2...) - w komentarzu wyjasnij, dlaczego liczba porzadkowa
         * jest krucha przy zmianach enuma.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignConsistentIdFieldAcrossResources {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj JSON dla 3 roznych zasobow (uzytkownik, produkt,
         * zamowienie) - zweryfikuj, ze WSZYSTKIE uzywaja TEGO SAMEGO
         * nazewnictwa dla identyfikatora (np. zawsze "id", nigdy
         * "userId"/"productId" na najwyzszym poziomie wlasnego zasobu).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullJsonDesignLinter {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny "linter" JSON sprawdzajacy JEDNOCZESNIE: spojnosc
         * nazewnictwa, format dat (ISO-8601), typy nie-stringified, brak
         * nadmiernego zagniezdzenia (max glebokosc) - przetestuj na min.
         * 3 przykladach (dobry, zly, mieszany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementBackwardCompatibleFieldRename {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj strategie ZMIANY nazwy pola (np. "name" ->
         * "fullName") BEZ lamania istniejacych klientow - zwroc OBA pola
         * naraz przez pewien okres przejsciowy - w komentarzu opisz plan
         * wycofania starego pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignPolymorphicJsonWithTypeDiscriminator {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj JSON dla listy roznych typow "powiadomien" (email,
         * sms, push) z WSPOLNYM polem dyskryminujacym typ (np. "type":
         * "email") - zaimplementuj parsowanie rozgaleziajace sie po tym polu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementJsonSizeOptimizationComparison {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj TA SAMA kolekcje 100 elementow w 2 wariantach - z
         * pelnymi nazwami pol i ze SKROCONYMI (np. "id"/"nm"/"pr") -
         * porownaj rozmiar w bajtach - w komentarzu przedyskutuj, kiedy
         * (jesli w ogole) taki kompromis czytelnosc/rozmiar ma sens.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementConsistentErrorFieldNamingAcrossApi {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj i zaimplementuj SPOJNY ksztalt JSON dla bledow w
         * CALYM API (nawiazanie do Lesson05/10) - zweryfikuj identyczna
         * strukture dla min. 3 roznych typow bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildJsonDiffToolForApiVersionComparison {
        /*
         * 🧪 Zadanie 26:
         * Napisz narzedzie porownujace 2 wersje JSON (np. odpowiedz v1 i
         * v2 tego samego zasobu) i wypisujace: pola DODANE, USUNIETE,
         * ZMIENIONE typem - foreshadowing Lesson08 (Versioning).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCircularSafeSerializationForGraphLikeData {
        /*
         * 🧪 Zadanie 27:
         * Zamodeluj dane grafopodobne (np. kategorie z podkategoriami,
         * mozliwie zagniezdzone) i zaimplementuj BEZPIECZNA serializacje
         * do JSON z ograniczona GLEBOKOSCIA (np. max 3 poziomy), zamiast
         * ryzykowac nieskonczona rekurencje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAndImplementSparseFieldsetsSupport {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj wsparcie dla parametru "?fields=id,name" -
         * zwracaj TYLKO zadane pola zasobu, zamiast calego obiektu -
         * zweryfikuj dla roznych kombinacji pol.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildJsonNamingConventionMigrationScript {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj skrypt migrujacy ISTNIEJACA kolekcje danych z
         * snake_case do camelCase (lub odwrotnie) - zachowaj WARTOSCI,
         * zmien TYLKO klucze, rekurencyjnie dla zagniezdzonych obiektow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteWellDesignedApiResponseCatalog {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj katalog (min. 5 przykladow) poprawnie zaprojektowanych
         * odpowiedzi JSON dla roznych scenariuszy (pojedynczy zasob,
         * kolekcja, blad, zagniezdzona relacja, polimorficzna lista) -
         * kazdy zgodny ze WSZYSTKIMI zasadami z tej lekcji, z komentarzem
         * wyjasniajacym decyzje projektowe.
         */
        public static void main(String[] args) { }
    }
}
