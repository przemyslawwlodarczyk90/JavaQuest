package com.example.javaquest._18_rest_api.Lesson18_OpenApiSwaggerIntro;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class _Lesson18_OpenApiSwaggerIntro {

    record Article(int id, String title, boolean published, double rating) {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: OPENAPI I SWAGGER ===");

        /*
         * ============================================================
         * 📦 OPENAPI vs SWAGGER - CZYM SIE ROZNIA?
         * ============================================================
         * - OpenAPI Specification (OAS) - STANDARD (format YAML/JSON) do
         *   OPISU REST API - metody, sciezki, parametry, ksztalty
         *   odpowiedzi/bledow - CALKOWICIE niezalezny od jezyka programowania.
         * - Swagger - NAZWA HISTORYCZNA i EKOSYSTEM NARZEDZI wokol tego
         *   standardu (Swagger UI - interaktywna dokumentacja w
         *   przegladarce, Swagger Editor, generatory kodu klienta) -
         *   "Swagger" byl PIERWOTNA nazwa specyfikacji, zanim przekazano
         *   ja fundacji Linux jako "OpenAPI" (2016) - dzis czesto uzywane
         *   zamiennie w mowie potocznej, ale technicznie OpenAPI = format,
         *   Swagger = marka narzedzi.
         */
        System.out.println("OpenAPI = STANDARD opisu API (YAML/JSON). Swagger = NARZEDZIA wokol tego standardu (np. Swagger UI).");

        demonstrateOpenApiStructure();
        demonstrateGeneratingSpecFromExistingEndpoints();
        demonstrateSchemaGenerationFromRecord();
        demonstrateSpringBootShortcut();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - OpenAPI opisuje API DEKLARATYWNIE: info (nazwa/wersja), servers
         *   (bazowe URL), paths (endpointy+metody+parametry+odpowiedzi),
         *   components/schemas (wielokrotnego uzytku ksztalty danych).
         * - Korzysci: automatycznie generowana INTERAKTYWNA dokumentacja
         *   (Swagger UI), automatyczne generowanie klientow SDK w wielu
         *   jezykach, mozliwosc IMPORTU do Postmana (Lesson17) jako
         *   gotowa kolekcja.
         * - W tym kursie generujemy specyfikacje RECZNIE (jako text block),
         *   bo nie uzywamy Springa - w Spring Boot (OSTATNI rozdzial
         *   calego kursu) ten sam efekt osiagniesz PRAKTYCZNIE za darmo.
         * - Kolejna lekcja (Lesson19): REST vs RPC vs GraphQL - jak REST
         *   (i cala jego dokumentacja OpenAPI) wyglada na tle alternatyw.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    private static void demonstrateOpenApiStructure() {
        System.out.println("\n=== PODSTAWOWA STRUKTURA DOKUMENTU OPENAPI 3.0 ===");
        String minimalSpec = """
                openapi: 3.0.3
                info:
                  title: JavaQuest Articles API
                  version: 1.0.0
                servers:
                  - url: http://localhost:8080
                paths:
                  /articles:
                    get:
                      summary: Pobierz liste artykulow
                      responses:
                        '200':
                          description: Lista artykulow
                """;
        System.out.println(minimalSpec.indent(2));
        System.out.println("4 glowne sekcje: info (metadane), servers (bazowe URL), paths (endpointy), components (ksztalty danych).");
    }

    private static void demonstrateGeneratingSpecFromExistingEndpoints() {
        System.out.println("\n=== GENEROWANIE PELNEJ SPECYFIKACJI DLA ENDPOINTOW Z LESSON04 ===");

        String fullSpec = """
                openapi: 3.0.3
                info:
                  title: JavaQuest Books API
                  version: 1.0.0
                  description: API zarzadzania ksiazkami (por. Lesson04_HttpMethods)
                paths:
                  /books:
                    get:
                      summary: Pobierz wszystkie ksiazki
                      responses:
                        '200':
                          description: Lista ksiazek
                          content:
                            application/json:
                              schema:
                                type: array
                                items:
                                  $ref: '#/components/schemas/Book'
                    post:
                      summary: Utworz nowa ksiazke
                      requestBody:
                        required: true
                        content:
                          application/json:
                            schema:
                              $ref: '#/components/schemas/Book'
                      responses:
                        '201':
                          description: Ksiazka utworzona
                  /books/{id}:
                    get:
                      summary: Pobierz ksiazke po ID
                      parameters:
                        - name: id
                          in: path
                          required: true
                          schema:
                            type: integer
                      responses:
                        '200':
                          description: Znaleziona ksiazka
                        '404':
                          description: Ksiazka nie istnieje
                components:
                  schemas:
                    Book:
                      type: object
                      properties:
                        id:
                          type: integer
                        title:
                          type: string
                """;
        System.out.println(fullSpec.indent(2));
        System.out.println("-> ta specyfikacja jest WYSTARCZAJACA, zeby Swagger UI wygenerowal DZIALAJACA, interaktywna dokumentacje.");
    }

    /**
     * Prosty, REFLEKSYJNY generator schematu OpenAPI z rekordu Java -
     * zamiast pisac schema recznie dla kazdego DTO, wyprowadzamy go
     * automatycznie z definicji klasy (podobny duch do
     * `_14_advancedjava/Lesson15_ReflectionBasics`).
     */
    private static void demonstrateSchemaGenerationFromRecord() {
        System.out.println("\n=== GENEROWANIE SCHEMATU PRZEZ REFLEKSJE (Z REKORDU JAVA) ===");

        Map<String, String> schema = generateOpenApiSchema(Article.class);
        System.out.println("Rekord Java: record Article(int id, String title, boolean published, double rating)");
        System.out.println("Wygenerowany schemat OpenAPI (properties):");
        schema.forEach((field, type) -> System.out.println("  " + field + ": { type: " + type + " }"));
    }

    private static Map<String, String> generateOpenApiSchema(Class<?> recordClass) {
        Map<String, String> schema = new LinkedHashMap<>();
        for (Field field : recordClass.getDeclaredFields()) {
            schema.put(field.getName(), mapJavaTypeToOpenApiType(field.getType()));
        }
        return schema;
    }

    private static String mapJavaTypeToOpenApiType(Class<?> javaType) {
        if (javaType == int.class || javaType == Integer.class || javaType == long.class || javaType == Long.class) {
            return "integer";
        }
        if (javaType == double.class || javaType == Double.class || javaType == float.class || javaType == Float.class) {
            return "number";
        }
        if (javaType == boolean.class || javaType == Boolean.class) {
            return "boolean";
        }
        return "string";
    }

    private static void demonstrateSpringBootShortcut() {
        System.out.println("\n=== ZAPOWIEDZ: W SPRING BOOT TO PRAWIE 'ZA DARMO' ===");
        /*
         * ============================================================
         * 📌 WAZNA UWAGA NA PRZYSZLOSC
         * ============================================================
         * W projekcie Spring Boot (OSTATNI rozdzial calego kursu) NIE
         * trzeba recznie pisac YAML jak wyzej - wystarczy DODAC 1
         * zaleznosc do `pom.xml`:
         *
         *   <dependency>
         *     <groupId>org.springdoc</groupId>
         *     <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
         *   </dependency>
         *
         * a biblioteka AUTOMATYCZNIE skanuje adnotacje `@RestController`/
         * `@GetMapping`/`@PostMapping` w Twoim kodzie i SAMA generuje
         * pelna specyfikacje OpenAPI (dostepna pod `/v3/api-docs`) ORAZ
         * interaktywne Swagger UI (pod `/swagger-ui.html`) - BEZ pisania
         * YAML recznie. Ten mechanizm wymaga zywego kontekstu Spring MVC
         * (skanowania adnotacji przez framework), wiec nie da sie go
         * zademonstrowac w TYM rozdziale (celowo jeszcze bez Springa) -
         * ale WARTO wiedziec, ze reczne pisanie specyfikacji, ktore
         * cwiczylismy powyzej, to dokladnie to, co Spring Boot zrobi
         * za Ciebie automatycznie.
         */
        System.out.println("Spring Boot + 1 zaleznosc (springdoc-openapi-starter-webmvc-ui) = automatyczna specyfikacja + Swagger UI, bez recznego YAML.");
        System.out.println("(Ten mechanizm wymaga zywego Spring MVC - zademonstrujemy go realnie w OSTATNIM rozdziale calego kursu.)");
    }
}
