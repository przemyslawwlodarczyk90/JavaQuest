package com.example.javaquest._01_fundamentals.Lesson06_StringsAndBuilder;

import java.util.Arrays;

public class _Lesson06_StringsAndBuilder {

    public static void main(String[] args) {

        /*
         * =====================================================================
         * 🔤 CZYM JEST STRING W JAVIE?
         * =====================================================================
         * - String to klasa reprezentująca ciąg znaków (tekst).
         * - String jest niemutowalny (immutable), co oznacza, że jego zawartości nie da się zmienić po utworzeniu.
         * - Każda operacja modyfikująca String tworzy nowy obiekt w pamięci.
         */

        String text = "Java Programming";

        /*
         * =====================================================================
         * 🧠 STRING POOL — JAK TO DZIAŁA?
         * =====================================================================
         * - String Pool to specjalna część pamięci w JVM, w której przechowywane są literalne łańcuchy znaków.
         * - Kiedy tworzysz String jako literał (np. String a = "Hello"), Java najpierw sprawdza, czy taki String już istnieje w puli.
         *   → Jeśli tak, to zwraca istniejący obiekt.
         *   → Jeśli nie, to tworzy nowy i dodaje do puli.
         * - Dzięki temu oszczędza się pamięć i przyspiesza porównania (można porównywać przez ==).
         *
         * Przykład:
         */
        String a = "test";
        String b = "test";
        String c = new String("test");
        String d = c.intern();

        System.out.println("a == b: " + (a == b));           // true (ten sam obiekt z puli)
        System.out.println("a == c: " + (a == c));           // false (new String tworzy nowy obiekt)
        System.out.println("a == d (c.intern()): " + (a == d)); // true (intern() dodaje do puli lub zwraca z puli)
        System.out.println("a.equals(c): " + a.equals(c));   // true (porównanie zawartości)

        // intern() warto znać np. przy dużej liczbie powtarzających się Stringów



        // =============================
        // METODY ANALITYCZNE I DOSTĘPOWE
        // =============================
        System.out.println("Długość tekstu: " + text.length()); // Zwraca liczbę znaków
        System.out.println("Znak na indeksie 3: " + text.charAt(3)); // Zwraca znak na wskazanym indeksie

        // =============================
        // PORÓWNYWANIE STRINGÓW
        // =============================
        String aa = "test";
        String bb = "test";
        String cc = new String("test");

        System.out.println("aa == bb: " + (a == b)); // true, bo to ten sam obiekt z puli
        System.out.println("aa == cc: " + (a == c)); // false, bo c to nowy obiekt
        System.out.println("aa.equals(cc): " + a.equals(c)); // true, bo zawartość taka sama
        System.out.println("equalsIgnoreCase(): " + "JAVA".equalsIgnoreCase("java")); // ignoruje wielkość liter

        // =============================
        // SPRAWDZANIE ZAWARTOŚCI I STRUKTURY
        // =============================
        System.out.println("contains(): " + text.contains("Program"));
        System.out.println("startsWith(): " + text.startsWith("Java"));
        System.out.println("endsWith(): " + text.endsWith("ing"));
        System.out.println("isEmpty(): " + "".isEmpty()); // true
        System.out.println("isBlank(): " + "   ".isBlank()); // true, dostępne od Java 11

        // =============================
        // WYSZUKIWANIE I POZYCJONOWANIE
        // =============================
        System.out.println("indexOf('a'): " + text.indexOf('a')); // indeks pierwszego wystąpienia
        System.out.println("lastIndexOf('a'): " + text.lastIndexOf('a')); // indeks ostatniego wystąpienia
        System.out.println("indexOf(\"gram\"): " + text.indexOf("gram")); // indeks początku podciągu

        // =============================
        // WYDZIELANIE FRAGMENTÓW
        // =============================
        System.out.println("substring(5): " + text.substring(5)); // od indeksu 5 do końca
        System.out.println("substring(5, 11): " + text.substring(5, 11)); // od indeksu 5 do 10

        // =============================
        // ZMIANA WYGLĄDU TEKSTU
        // =============================
        String messy = "  JaVa RuLeS!  ";
        System.out.println("trim(): '" + messy.trim() + "'"); // usuwa spacje z przodu i końca
        System.out.println("toLowerCase(): " + messy.toLowerCase()); // wszystkie litery małe
        System.out.println("toUpperCase(): " + messy.toUpperCase()); // wszystkie litery duże
        System.out.println("replace(): " + messy.replace("RuLeS", "ROCKS")); // zamiana fragmentu tekstu
        System.out.println("replaceAll(): " + messy.replaceAll("[aA]", "@")); // regexowa zamiana
        System.out.println("replaceFirst(): " + messy.replaceFirst("a", "@")); // tylko pierwsze wystąpienie

        // =============================
        // DZIELENIE I ŁĄCZENIE STRINGÓW
        // =============================
        String csv = "Jan,Kowalski,30,Programista";
        String[] parts = csv.split(",");
        System.out.println("split(): " + Arrays.toString(parts)); // podział tekstu na części

        String joined = String.join(" | ", parts);
        System.out.println("join(): " + joined); // łączenie tablicy w jeden String

        System.out.println("repeat(3): " + "Ha! ".repeat(3)); // powtórzenie tekstu (Java 11+)

        // =============================
        // INNE METODY
        // =============================
        System.out.println("toCharArray(): " + Arrays.toString(text.toCharArray())); // konwersja na tablicę znaków
        System.out.println("format(): " + String.format("Wynik: %.2f", 12.345)); // formatowanie tekstu
        System.out.println("strip(): '" + "  test  ".strip() + "'"); // zaawansowany trim (Java 11+)

        // =============================
        // STRINGBUILDER
        // =============================
        StringBuilder sb = new StringBuilder("Java");

        sb.append(" is fast"); // dodawanie
        sb.insert(0, "["); // wstawienie na początek
        sb.insert(sb.length(), "]"); // wstawienie na koniec
        sb.replace(0, 1, "{"); // zamiana fragmentu
        sb.delete(0, 1); // usunięcie znaku
        sb.reverse(); // odwrócenie tekstu

        System.out.println("StringBuilder wynik: " + sb.toString());

        // Przywrócenie zawartości
        sb.setLength(0);
        sb.append("Nowy tekst");
        System.out.println("Po czyszczeniu: " + sb);

        // StringBuffer – jak StringBuilder, ale thread-safe (synchronizowany)
        // Używany tylko w środowiskach wielowątkowych
    }
}
