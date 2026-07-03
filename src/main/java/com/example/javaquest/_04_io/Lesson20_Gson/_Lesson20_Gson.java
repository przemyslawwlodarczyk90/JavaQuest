package com.example.javaquest._04_io.Lesson20_Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class _Lesson20_Gson {

    /*
     * Prosty POJO (tu: record) do przykładów serializacji/deserializacji.
     * Gson od wersji 2.10 potrafi obsłużyć rekordy "z pudełka" – korzysta
     * z kanonicznego konstruktora rekordu do ich odtwarzania.
     */
    record Person(String name, int age) {
    }

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST GSON?
         * ============================================================
         * Gson to biblioteka Google do konwersji obiektów Java <-> JSON.
         * Zamiast ręcznie parsować tekst JSON (jak w Lesson19), Gson robi
         * to automatycznie na podstawie REFLEKSJI – analizuje pola klasy
         * i mapuje je na klucze JSON (i odwrotnie).
         *
         * Podstawowe operacje:
         *   Gson gson = new Gson();
         *   String json = gson.toJson(obiekt);         // Java  -> JSON
         *   T obiekt = gson.fromJson(json, Klasa.class); // JSON -> Java
         */

        Gson gson = new Gson();

        Person anna = new Person("Anna Nowak", 30);

        String json = gson.toJson(anna);
        System.out.println("=== toJson (obiekt -> JSON) ===");
        System.out.println(json); // {"name":"Anna Nowak","age":30}

        Person restored = gson.fromJson(json, Person.class);
        System.out.println("\n=== fromJson (JSON -> obiekt) ===");
        System.out.println(restored); // Person[name=Anna Nowak, age=30]
        System.out.println("Czy równe oryginałowi? " + anna.equals(restored)); // true (record ma equals z automatu)

        /*
         * ============================================================
         * 🔹 GsonBuilder – KONFIGUROWANIE GSON
         * ============================================================
         * Domyślny Gson generuje "skompresowany" JSON w jednej linii.
         * GsonBuilder pozwala dostosować zachowanie, np.:
         * - setPrettyPrinting()  → ładne formatowanie z wcięciami
         * - serializeNulls()      → wypisuj też pola null (domyślnie pomijane)
         * - setDateFormat(...)    → format zapisu dat
         */

        Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String prettyJson = prettyGson.toJson(anna);
        System.out.println("\n=== setPrettyPrinting() ===");
        System.out.println(prettyJson);
        // {
        //   "name": "Anna Nowak",
        //   "age": 30
        // }

        /*
         * ============================================================
         * 🔍 SERIALIZACJA KOLEKCJI – List<Person>
         * ============================================================
         * Gson bez problemu serializuje kolekcje – lista obiektów
         * zamienia się na tablicę JSON obiektów.
         */

        List<Person> people = List.of(
                new Person("Anna Nowak", 30),
                new Person("Jan Kowalski", 25),
                new Person("Ewa Wiśniewska", 41)
        );

        String peopleJson = prettyGson.toJson(people);
        System.out.println("\n=== Serializacja List<Person> ===");
        System.out.println(peopleJson);

        /*
         * ============================================================
         * ⚠️ PROBLEM: DESERIALIZACJA GENERYCZNYCH TYPÓW
         * ============================================================
         * Przy odczycie pojedynczego obiektu wystarczy podać Person.class.
         * Ale co z List<Person>? W Javie generyki są "kasowane" w runtime
         * (type erasure) – gson.fromJson(json, List.class) nie wie, że to
         * ma być lista Person, tylko lista "czegoś" (domyślnie LinkedTreeMap!).
         *
         * ROZWIĄZANIE: TypeToken<T> – "przechwytuje" pełny typ generyczny
         * (łącznie z parametrem <Person>) w czasie kompilacji, dzięki
         * mechanizmowi anonimowej podklasy.
         */

        Type personListType = new TypeToken<List<Person>>() {}.getType();
        List<Person> peopleRestored = gson.fromJson(peopleJson, personListType);

        System.out.println("\n=== fromJson z TypeToken<List<Person>> ===");
        peopleRestored.forEach(System.out::println);
        System.out.println("Typ pierwszego elementu: "
                + peopleRestored.get(0).getClass().getSimpleName()); // Person, nie LinkedTreeMap!

        /*
         * ============================================================
         * 📋 SERIALIZACJA MAP – Map<String, Person>
         * ============================================================
         * Mapy Java (klucz = String) tłumaczą się bezpośrednio na
         * obiekty JSON – każdy klucz mapy staje się kluczem JSON-a.
         */

        Map<String, Person> peopleById = Map.of(
                "P001", new Person("Anna Nowak", 30),
                "P002", new Person("Jan Kowalski", 25)
        );

        String mapJson = prettyGson.toJson(peopleById);
        System.out.println("\n=== Serializacja Map<String, Person> ===");
        System.out.println(mapJson);

        Type mapType = new TypeToken<Map<String, Person>>() {}.getType();
        Map<String, Person> mapRestored = gson.fromJson(mapJson, mapType);
        System.out.println("Odtworzona mapa: " + mapRestored);
        System.out.println("P001 -> " + mapRestored.get("P001"));

        /*
         * ============================================================
         * 🔧 ZAPIS I ODCZYT JSON-A Z PLIKU
         * ============================================================
         * Gson umie pisać/czytać bezpośrednio do/z Writer/Reader,
         * co jest wygodne przy pracy z plikami – nie trzeba samemu
         * budować Stringa z całą zawartością pliku.
         */

        Path tempFile = Files.createTempFile("javaquest-gson-demo", ".json");
        try {
            try (FileWriter writer = new FileWriter(tempFile.toFile())) {
                prettyGson.toJson(people, writer);
            }
            System.out.println("\n=== Zapisano JSON do pliku tymczasowego ===");
            System.out.println("Ścieżka: " + tempFile);

            List<Person> peopleFromFile;
            try (FileReader reader = new FileReader(tempFile.toFile())) {
                peopleFromFile = gson.fromJson(reader, personListType);
            }

            System.out.println("Odczytano z pliku: " + peopleFromFile);
        } finally {
            Files.deleteIfExists(tempFile); // sprzątanie po demo
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - new Gson().toJson(obiekt)          → Java -> JSON (String)
         * - gson.fromJson(json, Klasa.class)     → JSON -> Java (obiekt)
         * - new GsonBuilder().setPrettyPrinting().create() → ładnie sformatowany JSON
         * - TypeToken<List<X>>(){}.getType()     → niezbędny przy deserializacji
         *   typów generycznych (List<X>, Map<K,V>...) – inaczej Gson zwróci
         *   surowe LinkedTreeMap/ArrayList zamiast właściwych typów
         * - Gson naturalnie serializuje/deserializuje kolekcje (List, Map)
         *   obiektów, w tym rekordy (od Gson 2.10+)
         * - toJson/fromJson mają przeciążenia przyjmujące Writer/Reader –
         *   wygodne przy pracy bezpośrednio z plikami
         */
    }
}
