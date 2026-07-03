package com.example.javaquest._04_io.Lesson21_Jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson21_Jackson {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 JACKSON – JSON W JAVIE (ALTERNATYWA DLA GSON)
         * ============================================================
         * Jackson to najpopularniejsza biblioteka do pracy z JSON w ekosystemie
         * Java – to właśnie jej używa Spring Boot "pod maską" (np. w kontrolerach REST).
         *
         * Jeśli znasz już Gson – Jackson robi to samo (POJO <-> JSON), ale:
         * ✅ jest bardziej rozbudowany (adnotacje, moduły, integracja ze Spring/JAX-RS)
         * ✅ ma bogatszy model drzewiasty (JsonNode) do pracy bez klas POJO
         * ✅ jest szybszy przy dużych ilościach danych (strumieniowe API pod spodem)
         * ❌ ma nieco więcej "ceremonii" konfiguracyjnej niż prosty Gson
         *
         * Centralną klasą Jacksona jest ObjectMapper – odpowiednik Gson z biblioteki Gson.
         *
         * Zależność (już w pom.xml – transitywnie przez spring-boot-starter-web):
         * com.fasterxml.jackson.databind.ObjectMapper i pokrewne pakiety.
         */

        ObjectMapper mapper = new ObjectMapper();

        /*
         * ============================================================
         * 🔹 PROSTY POJO
         * ============================================================
         */

        Person person = new Person("Jan Kowalski", 30, "tajneHaslo123", null);

        /*
         * ============================================================
         * 🔹 SERIALIZACJA – OBIEKT -> JSON
         * ============================================================
         * writeValueAsString() → zwraca JSON jako String
         * writeValue(File, obj) → zapisuje JSON od razu do pliku
         */

        String json = mapper.writeValueAsString(person);
        System.out.println("JSON (writeValueAsString): " + json);
        // {"full_name":"Jan Kowalski","age":30}
        // Uwaga: pole "password" zniknęło (@JsonIgnore), a "email" (null) też
        // zniknęło (@JsonInclude(NON_NULL)) – zobacz adnotacje w klasie Person.

        Path tempFile = Files.createTempFile("jackson_demo", ".json");
        File file = tempFile.toFile();
        mapper.writeValue(file, person);
        System.out.println("Zapisano do pliku: " + file.getAbsolutePath());
        System.out.println("Zawartość pliku: " + Files.readString(tempFile));

        /*
         * ============================================================
         * 🔹 DESERIALIZACJA – JSON -> OBIEKT
         * ============================================================
         * readValue() – z String, z File, z InputStream...
         */

        Person fromString = mapper.readValue(json, Person.class);
        System.out.println("\nOdczytany z Stringa: " + fromString);

        Person fromFile = mapper.readValue(file, Person.class);
        System.out.println("Odczytany z pliku: " + fromFile);

        /*
         * ============================================================
         * 🔍 JSONNODE – MODEL DRZEWIASTY (BEZ MAPOWANIA NA KLASĘ)
         * ============================================================
         * Czasem nie chcemy (albo nie możemy) tworzyć klasy POJO – np. gdy
         * struktura JSON jest nieznana z góry albo interesuje nas tylko
         * fragment danych. Wtedy używamy JsonNode – ogólnego drzewa JSON,
         * po którym nawigujemy jak po mapie/liście.
         */

        String rawJson = """
                {
                  "full_name": "Anna Nowak",
                  "age": 25,
                  "address": {
                    "city": "Warszawa",
                    "zip": "00-001"
                  },
                  "hobbies": ["czytanie", "bieganie", "szachy"]
                }
                """;

        JsonNode root = mapper.readTree(rawJson);

        System.out.println("\n--- JsonNode nawigacja ---");
        System.out.println("full_name: " + root.get("full_name").asText());
        System.out.println("age: " + root.get("age").asInt());
        System.out.println("miasto: " + root.get("address").get("city").asText());

        JsonNode hobbies = root.get("hobbies");
        System.out.print("hobby: ");
        for (JsonNode hobby : hobbies) {
            System.out.print(hobby.asText() + " ");
        }
        System.out.println();

        // Bezpieczne sprawdzanie brakujących pól – path() zamiast get()
        System.out.println("brakujące pole (path): " + root.path("nickname").asText("brak"));
        // "brak" – path() nigdy nie zwraca null, w przeciwieństwie do get()

        /*
         * ============================================================
         * 📌 ADNOTACJE JACKSONA (zobacz klasę Person poniżej)
         * ============================================================
         * @JsonProperty("full_name") – zmienia nazwę pola w JSON-ie
         * @JsonIgnore              – pole całkowicie pomijane przy (de)serializacji
         * @JsonInclude(Include.NON_NULL) – pomija pola o wartości null przy serializacji
         *   (można ustawić na całej klasie albo na pojedynczym polu)
         */

        // Sprzątanie – usuwamy plik tymczasowy
        Files.deleteIfExists(tempFile);
        System.out.println("\nPlik tymczasowy usunięty: " + !file.exists());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * ObjectMapper – centralna klasa Jacksona:
         * - writeValueAsString(obj) / writeValue(File/Stream, obj) → serializacja
         * - readValue(String/File/Stream, Klasa.class)             → deserializacja
         * - readTree(String)                                        → JsonNode (model drzewiasty)
         *
         * JsonNode – nawigacja po JSON bez klasy POJO:
         * - get("pole") – zwraca null, gdy brak pola
         * - path("pole") – nigdy nie zwraca null (bezpieczniejsze łańcuchowanie)
         * - asText()/asInt()/asBoolean() – konwersja na typ prosty
         *
         * Adnotacje: @JsonProperty, @JsonIgnore, @JsonInclude(NON_NULL)
         *
         * Jackson vs Gson: podobne API, Jackson bogatszy i szybszy,
         * standard w świecie Spring/REST.
         */
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class Person {

        @JsonProperty("full_name")
        private String name;

        private int age;

        @JsonIgnore
        private String password;

        private String email; // gdy null – zniknie z JSON-a dzięki @JsonInclude(NON_NULL)

        // Jackson wymaga konstruktora bezargumentowego (lub adnotacji @JsonCreator)
        public Person() {
        }

        public Person(String name, int age, String password, String email) {
            this.name = name;
            this.age = age;
            this.password = password;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + ", email=" + email + "}";
        }
    }
}
