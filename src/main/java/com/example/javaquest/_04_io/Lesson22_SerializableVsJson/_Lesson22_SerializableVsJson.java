package com.example.javaquest._04_io.Lesson22_SerializableVsJson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson22_SerializableVsJson {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 SERIALIZABLE VS JSON – DWA SPOSOBY "ZAPISANIA" OBIEKTU
         * ============================================================
         * Oba mechanizmy odpowiadają na to samo pytanie: "jak zamienić obiekt
         * Javy na ciąg bajtów/znaków, żeby go zapisać albo wysłać, a potem
         * odtworzyć?" – ale robią to zupełnie inaczej.
         *
         * Serializable (java.io.Serializable):
         * - format BINARNY, natywny dla JVM
         * - potrafi zapisać CAŁY graf obiektów (referencje, cykle) "za darmo"
         * - działa TYLKO między aplikacjami Java (odczyta go tylko inna JVM)
         *
         * JSON (Gson/Jackson):
         * - format TEKSTOWY, uniwersalny (JavaScript Object Notation)
         * - czytelny dla człowieka, łatwy do debugowania
         * - odczytają go dowolne języki/systemy (Python, JS, curl, przeglądarka...)
         */

        Product product = new Product("Laptop", 4999.99, "SECRET-DISCOUNT-CODE");

        /*
         * ============================================================
         * 🔹 SERIALIZACJA BINARNA (Serializable)
         * ============================================================
         * ObjectOutputStream.writeObject() – zapisuje obiekt jako bajty.
         * Klasa MUSI implementować Serializable, inaczej NotSerializableException.
         * transient – pole pomijane przy serializacji (np. dane wrażliwe, cache).
         */

        Path binFile = Files.createTempFile("product", ".ser");

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(binFile.toFile()))) {
            oos.writeObject(product);
        }

        System.out.println("=== SERIALIZABLE (binarny) ===");
        System.out.println("Rozmiar pliku .ser: " + Files.size(binFile) + " bajtów");
        System.out.println("Zawartość (surowe bajty, nieczytelne dla człowieka):");
        System.out.println(bytesToHexPreview(Files.readAllBytes(binFile)));

        Product deserializedProduct;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(binFile.toFile()))) {
            deserializedProduct = (Product) ois.readObject();
        }
        System.out.println("Odtworzony obiekt: " + deserializedProduct);
        // discountCode będzie null – pole było transient, nie zostało zapisane!

        /*
         * ============================================================
         * 🔹 SERIALIZACJA TEKSTOWA (JSON – Jackson)
         * ============================================================
         */

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);

        Path jsonFile = Files.createTempFile("product", ".json");
        Files.writeString(jsonFile, json);

        System.out.println("\n=== JSON (tekstowy) ===");
        System.out.println("Rozmiar pliku .json: " + Files.size(jsonFile) + " bajtów");
        System.out.println("Zawartość (czytelna dla człowieka):");
        System.out.println(json);
        // discountCode WIDNIEJE w JSON-ie, bo Jackson nie respektuje "transient"
        // domyślnie tak samo jak Serializable – trzeba by użyć @JsonIgnore!

        Product fromJson = mapper.readValue(json, Product.class);
        System.out.println("Odtworzony obiekt: " + fromJson);

        /*
         * ============================================================
         * ⚠️ ZALETY I WADY
         * ============================================================
         * Serializable:
         * ✅ szybkie i proste – jedna adnotacja/interfejs, brak bibliotek
         * ✅ zapisuje pełny graf obiektów automatycznie
         * ❌ format binarny – nieczytelny, trudny do debugowania
         * ❌ TYLKO Java-Java – niekompatybilny z innymi systemami/wersją
         * ❌ KRUCHY na zmiany klasy (dodanie pola bez serialVersionUID
         *    może rzucić InvalidClassException przy odczycie starych danych)
         *
         * JSON:
         * ✅ czytelny dla człowieka, łatwy do debugowania i logowania
         * ✅ uniwersalny – rozumie go każdy język i każdy system (REST API!)
         * ✅ elastyczny na zmiany struktury (nowe/brakujące pole to zwykle nie problem)
         * ❌ nieco większy narzut (parsowanie tekstu, biblioteka zewnętrzna)
         * ❌ nie zapisuje "za darmo" złożonych grafów z cyklami (trzeba uważać)
         */

        /*
         * ============================================================
         * ✅ KIEDY CO STOSOWAĆ?
         * ============================================================
         * Serializable:
         * - wewnętrzny cache JVM-to-JVM (np. Ehcache, sesje HTTP w klastrze)
         * - szybki zapis/odczyt stanu obiektu do pliku w OBRĘBIE tej samej aplikacji
         *
         * JSON:
         * - komunikacja przez REST/HTTP API
         * - wymiana danych między różnymi systemami/językami
         * - pliki konfiguracyjne, logi, dane do przechowania długoterminowego
         *   (JSON przetrwa lata, format binarny Javy może się "zestarzeć")
         */

        // Sprzątanie plików tymczasowych
        Files.deleteIfExists(binFile);
        Files.deleteIfExists(jsonFile);
        System.out.println("\nPliki tymczasowe usunięte.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Serializable → binarny, tylko Java, szybki, kruchy na zmiany wersji.
         * JSON          → tekstowy, uniwersalny, czytelny, elastyczny.
         *
         * Reguła praktyczna:
         * "Jeśli dane wychodzą poza JVM (do innego systemu, do przeglądarki,
         * do pliku który ktoś ma czytać) → JSON. Jeśli zostają wewnątrz Javy
         * jako szybki wewnętrzny mechanizm → Serializable może wystarczyć."
         */
    }

    private static String bytesToHexPreview(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int limit = Math.min(bytes.length, 32);
        for (int i = 0; i < limit; i++) {
            sb.append(String.format("%02X ", bytes[i]));
        }
        if (bytes.length > limit) {
            sb.append("...");
        }
        return sb.toString();
    }

    static class Product implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;
        private double price;
        private transient String discountCode; // NIE zostanie zserializowany binarnie

        public Product(String name, double price, String discountCode) {
            this.name = name;
            this.price = price;
            this.discountCode = discountCode;
        }

        // Jackson potrzebuje konstruktora bezargumentowego do deserializacji JSON
        public Product() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getDiscountCode() {
            return discountCode;
        }

        public void setDiscountCode(String discountCode) {
            this.discountCode = discountCode;
        }

        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price
                    + ", discountCode=" + discountCode + "}";
        }
    }
}
