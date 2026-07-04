package com.example.javaquest._04_io.Lesson21_Jackson;

public class _Exercises_Lesson21_Jackson {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SerializeToString {
        /*
         * 🧪 Zadanie 1:
         * Stwórz prosty POJO Person(name, age) i użyj ObjectMapper.writeValueAsString()
         * żeby zamienić obiekt {"Jan Kowalski", 30} na JSON-a. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DeserializeFromString {
        /*
         * 🧪 Zadanie 2:
         * Mając String json = "{\"name\":\"Anna\",\"age\":25}", użyj
         * ObjectMapper.readValue(json, Person.class) żeby odtworzyć obiekt. Wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteReadFile {
        /*
         * 🧪 Zadanie 3:
         * Zapisz obiekt Person do pliku tymczasowego (Files.createTempFile) metodą
         * mapper.writeValue(File, obj), a następnie odczytaj go z powrotem mapper.readValue(File, Person.class).
         * Usuń plik na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_JsonPropertyRename {
        /*
         * 🧪 Zadanie 4:
         * Klasa Product z polem private String productName oznaczonym @JsonProperty("product_name").
         * Zserializuj obiekt i sprawdź, że w JSON-ie klucz to "product_name", nie "productName".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JsonIgnore {
        /*
         * 🧪 Zadanie 5:
         * Klasa User(login, password) z @JsonIgnore na polu password.
         * Zserializuj obiekt User("admin","tajne123") i sprawdź, że "password" nie pojawia się w JSON-ie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_JsonIncludeNonNull {
        /*
         * 🧪 Zadanie 6:
         * Klasa Account(username, nickname) oznaczona @JsonInclude(Include.NON_NULL).
         * Zserializuj obiekt Account("kowal", null) i pokaż, że pole "nickname" znika z JSON-a.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_JsonNodeGet {
        /*
         * 🧪 Zadanie 7:
         * Mając surowy JSON: {"city":"Poznan","population":540000}
         * wczytaj go przez mapper.readTree(...) i odczytaj pola przez get("city").asText()
         * oraz get("population").asInt().
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PathVsGet {
        /*
         * 🧪 Zadanie 8:
         * Dla tego samego JSON-a co w zadaniu 7 spróbuj odczytać nieistniejące pole "country"
         * przez get("country") (zobacz co zwraca) i przez path("country").asText("brak")
         * (zobacz różnicę w bezpieczeństwie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_JsonNodeArray {
        /*
         * 🧪 Zadanie 9:
         * JSON: {"name":"Kasia","tags":["java","backend","junior"]}
         * Wczytaj przez readTree i zaiteruj po tablicy "tags" (JsonNode implementuje Iterable),
         * wypisując każdy tag.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NestedPojo {
        /*
         * 🧪 Zadanie 10:
         * Klasa Employee(name, Address address), gdzie Address(city, zip) to osobna klasa statyczna.
         * Zserializuj Employee("Marek", new Address("Lodz","90-001")) do JSON-a i odtwórz z powrotem
         * przez readValue – sprawdź, że address.city się zgadza.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ListOfPojo {
        /*
         * 🧪 Zadanie 11:
         * Lista List<Person> z 3 osobami. Zserializuj całą listę do JSON-a (tablica obiektów),
         * a potem odtwórz ją przez mapper.readValue(json, Person[].class) i zamień na listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MapToJson {
        /*
         * 🧪 Zadanie 12:
         * Map<String, Object> z kluczami "name","age","active" i różnymi typami wartości.
         * Zserializuj mapę do JSON-a, a potem odczytaj JSON z powrotem jako
         * mapper.readValue(json, Map.class).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_JacksonVsGson {
        /*
         * 🧪 Zadanie 13:
         * Ten sam obiekt Person zserializuj raz Jacksonem (ObjectMapper.writeValueAsString)
         * i raz Gsonem (new Gson().toJson). Porównaj wygenerowane stringi JSON – czy są identyczne?
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DeepNavigation {
        /*
         * 🧪 Zadanie 14:
         * JSON: {"company":{"name":"Acme","address":{"city":"Warszawa","street":"Dluga 5"}}}
         * Użyj readTree i łańcucha get(...).get(...).get(...) żeby wyciągnąć samą nazwę ulicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ModifyWithObjectNode {
        /*
         * 🧪 Zadanie 15:
         * Wczytaj JSON {"name":"Ola","age":22} jako ObjectNode (mapper.readTree zwraca JsonNode,
         * rzutuj na ObjectNode), dodaj nowe pole put("city","Gdansk"), usuń pole "age"
         * (remove("age")), wypisz zmodyfikowany JSON jako String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PrettyPrint {
        /*
         * 🧪 Zadanie 16:
         * Zserializuj obiekt zagnieżdżony (np. Employee z zadania 10) przy pomocy
         * mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj).
         * Porównaj wynik z domyślnym writeValueAsString (bez wcięć).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DefaultValuesForMissing {
        /*
         * 🧪 Zadanie 17:
         * JSON konfiguracyjny: {"timeout":30} (brak pola "retries" i "debug").
         * Użyj path(...).asInt(domyslna) i path(...).asBoolean(domyslna) żeby odczytać
         * timeout, retries (domyślnie 3) i debug (domyślnie false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConvertValue {
        /*
         * 🧪 Zadanie 18:
         * Wczytaj JsonNode z JSON-a {"name":"Piotr","age":40} i użyj
         * mapper.convertValue(node, Map.class) żeby zamienić drzewo na zwykłą Map<String,Object>.
         * Wypisz zawartość mapy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AnnotationsCombined {
        /*
         * 🧪 Zadanie 19:
         * Klasa Customer z polami: @JsonProperty("full_name") name, @JsonIgnore pesel,
         * oraz @JsonInclude(Include.NON_NULL) na polu phoneNumber. Zserializuj dwa obiekty:
         * jeden z phoneNumber ustawionym, drugi z null – porównaj wygenerowane JSON-y.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SumFromJsonArray {
        /*
         * 🧪 Zadanie 20:
         * JSON: {"orderId":1,"items":[{"name":"Chleb","price":4.5},{"name":"Mleko","price":3.2}]}
         * Wczytaj readTree, zaiteruj po tablicy "items" i zsumuj wartości pola "price"
         * używając JsonNode (bez tworzenia klas POJO dla Item).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ConfigLoader {
        /*
         * 🧪 Zadanie 21:
         * Zapisz do pliku tymczasowego JSON konfiguracyjny z zagnieżdżonymi sekcjami
         * (np. "database":{...}, "server":{...}). Wczytaj plik przez readTree, zmapuj
         * wybrane sekcje na osobne klasy POJO (DatabaseConfig, ServerConfig) i wypisz raport
         * z odczytanych wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MergeJsonObjects {
        /*
         * 🧪 Zadanie 22:
         * Dwa JSON-y: bazowy {"name":"Jan","age":30,"city":"Warszawa"} i nadpisujący
         * {"age":31,"country":"Polska"}. Zaimplementuj scalanie na poziomie kluczy top-level
         * (drugi nadpisuje/dodaje pola pierwszego) używając ObjectNode, wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CsvToJsonBridge {
        /*
         * 🧪 Zadanie 23:
         * Mając listę wierszy w postaci String[] (imitacja danych z CSV: name,age,city),
         * zmapuj je na listę POJO Person, a następnie zapisz jako tablicę JSON do pliku
         * przy pomocy ObjectMapper (mostek CSV -> JSON).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TreeInspector {
        /*
         * 🧪 Zadanie 24:
         * Napisz rekurencyjną metodę printTree(JsonNode node, String prefix), która przechodzi
         * dowolne, nieznane z góry drzewo JSON (obiekty, tablice, wartości proste) i wypisuje
         * każdą parę klucz-wartość z odpowiednim wcięciem. Przetestuj na złożonym, zagnieżdżonym JSON-ie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FilterUsersFromJson {
        /*
         * 🧪 Zadanie 25:
         * JSON: tablica "users": [{"name":"Ala","age":17},{"name":"Bartek","age":25},
         * {"name":"Celina","age":30}]. Bez tworzenia klasy POJO, używając samego JsonNode,
         * wypisz imiona użytkowników z age > 18.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DiffTwoObjects {
        /*
         * 🧪 Zadanie 26:
         * Mając dwa obiekty Person tej samej klasy ale różniące się kilkoma polami,
         * napisz metodę diff(Person a, Person b) -> Map<String, Object[]>, która zwraca
         * mapę: nazwa pola -> [wartość w a, wartość w b] dla pól, które się różnią
         * (porównaj przez zserializowane do JSON drzewa obu obiektów, pole po polu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RoundTripMutation {
        /*
         * 🧪 Zadanie 27:
         * Zserializuj obiekt Person("Tomek", 20) do Stringa JSON, ręcznie zamień w tym
         * Stringu wartość age (np. replace("20","21")), zdeserializuj zmodyfikowany JSON
         * z powrotem do obiektu Person i sprawdź, że age faktycznie wynosi 21.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CountMatchingInArray {
        /*
         * 🧪 Zadanie 28:
         * Zapisz do pliku tymczasowego JSON z dużą tablicą obiektów (np. 100 "produktów"
         * z polami name/price wygenerowanych w pętli). Wczytaj plik przez readTree
         * i policz, ile produktów ma price > zadany próg.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PublicVsInternalView {
        /*
         * 🧪 Zadanie 29:
         * Klasa InternalUser z polami login, password, internalNotes oraz osobna klasa
         * PublicUserView z tylko polem login (bez wrażliwych danych). Napisz metodę
         * toPublicView(InternalUser u) -> PublicUserView i zserializuj obie klasy do JSON-a,
         * porównując co widać w publicznej wersji a co w wewnętrznej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_OrdersSummary {
        /*
         * 🧪 Zadanie 30:
         * Zapisz do pliku JSON strukturę zamówień: lista "orders", każde zamówienie ma
         * "orderId" i listę "items" (name, price, qty). Wczytaj plik, dla każdego zamówienia
         * oblicz sumę (price*qty) wszystkich pozycji, a wynik (orderId -> suma) zapisz
         * jako nowy plik JSON-podsumowanie.
         */
        public static void main(String[] args) { }
    }
}
