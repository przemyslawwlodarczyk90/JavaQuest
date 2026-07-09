package com.example.javaquest._14_advancedjava.Lesson23_VarAndTypeInference;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _Lesson23_VarAndTypeInference {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 23: var I WNIOSKOWANIE TYPU (TYPE INFERENCE) ===");

        /*
         * ============================================================
         * 📦 CZYM JEST var
         * ============================================================
         * - `var` (Java 10+, JEP 286 "Local-Variable Type Inference") to
         *   NIE nowy typ i NIE słowo kluczowe w pełnym sensie - to tzw.
         *   "reserved type name". Nadal możesz mieć zmienną/metodę/pole o
         *   nazwie `var` (choć nikt tak nie robi) - to odróżnia go od
         *   prawdziwych słów kluczowych jak `class` czy `if`.
         * - `var` mówi kompilatorowi: "sam wywnioskuj typ tej lokalnej
         *   zmiennej na podstawie wyrażenia po prawej stronie" - i robi to
         *   WYŁĄCZNIE w miejscu deklaracji, raz, w czasie KOMPILACJI.
         * - Kluczowe: to jest cukier składniowy, a NIE dynamiczne typowanie.
         *   Bajtkod wygenerowany dla `var lista = new ArrayList<String>();`
         *   jest IDENTYCZNY jak dla `ArrayList<String> lista = new ArrayList<>();`.
         *   Więcej o tym w sekcji "var jest wciąż statycznie typowany" niżej.
         */
        System.out.println("\nvar = wnioskowanie typu w miejscu deklaracji zmiennej lokalnej (Java 10+).");

        /*
         * ============================================================
         * 🔹 GDZIE MOŻNA UŻYĆ var
         * ============================================================
         * 1. Zmienna lokalna z inicjalizatorem (wewnątrz metody/bloku).
         * 2. Zmienna pętli w rozszerzonym for (for-each).
         * 3. Zasób w try-with-resources.
         * 4. Zwykła pętla for (zmienna licznika też jest lokalna).
         */

        // 1. Zmienna lokalna z inicjalizatorem
        var imie = "Kasia";               // wywnioskowane: String
        var wiek = 27;                    // wywnioskowane: int
        var cena = 19.99;                 // wywnioskowane: double
        var lista = new ArrayList<String>(); // wywnioskowane: ArrayList<String>
        System.out.println("\nvar imie = \"Kasia\"  -> typ: String");
        System.out.println("var wiek = 27       -> typ: int");
        System.out.println("var cena = 19.99     -> typ: double");
        System.out.println(imie + ", " + wiek + " lat, cena: " + cena);

        // 2. Zmienna w for-each
        System.out.println("\nfor-each z var:");
        lista.add("Java");
        lista.add("Kotlin");
        for (var jezyk : lista) { // jezyk wywnioskowany jako String
            System.out.println("  - " + jezyk);
        }

        // 3. try-with-resources
        System.out.println("\ntry-with-resources z var:");
        try (var reader = new BufferedReader(new StringReader("linia1\nlinia2"))) {
            String linia;
            while ((linia = reader.readLine()) != null) {
                System.out.println("  wczytano: " + linia);
            }
        }

        // 4. Zwykła pętla for
        System.out.println("\nzwykla petla for z var:");
        for (var i = 0; i < 3; i++) { // i wywnioskowane jako int
            System.out.println("  i = " + i);
        }

        /*
         * ============================================================
         * 🔹 GDZIE NIE MOŻNA UŻYĆ var
         * ============================================================
         * `var` działa TYLKO dla zmiennych LOKALNYCH z inicjalizatorem.
         * Poniższe przypadki NIE kompilują się - celowe ograniczenie
         * projektowe JEP 286, żeby publiczne "kontrakty" klasy (pola,
         * sygnatury metod) zawsze miały jawny, czytelny typ.
         */

        System.out.println("\nMiejsca, gdzie var jest ZABRONIONY (patrz komentarze w kodzie):");

        // ❌ 1. Pole klasy - var NIE MOŻE być typem pola (patrz klasa BledneUzyciaVar niżej,
        //    zakomentowana treść pola pokazuje błąd kompilacji).
        // class Zla {
        //     var pole = 10; // ❌ błąd kompilacji: "var is not allowed here"
        // }

        // ❌ 2. Parametr metody
        // void metoda(var x) { } // ❌ błąd kompilacji

        // ❌ 3. Typ zwracany metody
        // var metoda() { return 5; } // ❌ błąd kompilacji

        // ❌ 4. Deklaracja bez inicjalizatora - kompilator nie ma z czego wywnioskować typu
        // var x; // ❌ błąd kompilacji: "cannot infer type for local variable x"

        // ❌ 5. Inicjalizacja samym null - null nie niesie żadnej informacji o typie
        // var y = null; // ❌ błąd kompilacji: "cannot infer type for local variable y"
        //                //    (var y = (String) null; JUŻ zadziała - rzutowanie daje typ)

        // ❌ 6. Wiele zmiennych w jednej deklaracji
        // var a = 1, b = 2; // ❌ błąd kompilacji - var pozwala tylko na JEDNĄ zmienną naraz

        System.out.println("  1. pole klasy           -> ❌ var pole = 10;");
        System.out.println("  2. parametr metody      -> ❌ void m(var x) {}");
        System.out.println("  3. typ zwracany metody  -> ❌ var m() { return 5; }");
        System.out.println("  4. bez inicjalizatora   -> ❌ var x;");
        System.out.println("  5. inicjalizacja null   -> ❌ var y = null;");
        System.out.println("  6. wiele zmiennych naraz-> ❌ var a = 1, b = 2;");

        /*
         * ============================================================
         * 🔹 var Z GENERYKAMI I OPERATOREM DIAMOND
         * ============================================================
         * `var` wnioskuje typ z PRAWEJ strony wyrażenia - to oznacza, że
         * generyki i tak MUSISZ podać po prawej stronie, `var` niczego
         * tu nie "domyśla się magicznie". Innymi słowy: `var` NIE zamienia
         * całej deklaracji w coś nietypowanego - typ jest ustalany raz,
         * na podstawie tego, co faktycznie stoi po `=`.
         */

        var slownik = new HashMap<String, Integer>(); // <String, Integer> wciąż wymagane po prawej
        slownik.put("jeden", 1);
        slownik.put("dwa", 2);
        System.out.println("\nvar slownik = new HashMap<String, Integer>(); -> " + slownik);

        // var pustyDiament = new ArrayList<>(); // ❌ ryzykowne: bez jawnego typu po prawej
        //   kompilator wywnioskuje ArrayList<Object> - diamond <> sam z siebie nie ma typu,
        //   musi go skądś wziąć, a var nie dostarcza kontekstu (jak zrobiłby to np. jawny typ
        //   po lewej: List<String> l = new ArrayList<>();). Zawsze podawaj typ w diamentcie
        //   przy użyciu var: new ArrayList<String>(), new HashMap<String, Integer>() itd.

        /*
         * ============================================================
         * 🔍 var JEST WCIĄŻ STATYCZNIE TYPOWANY
         * ============================================================
         * To NIE jest dynamiczne typowanie znane z JavaScriptu/Pythona
         * (gdzie ta sama zmienna może w trakcie działania programu
         * przechowywać raz String, raz liczbę). Typ zmiennej `var` jest
         * USTALANY RAZ, W CZASIE KOMPILACJI, na podstawie inicjalizatora -
         * i od tej pory zmienna zachowuje się DOKŁADNIE tak jak zmienna
         * zadeklarowana z jawnym typem.
         */

        var tekst = "Hello"; // typ wywnioskowany jako String, RAZ NA ZAWSZE
        // tekst = 42; // ❌ błąd kompilacji: "incompatible types: int cannot be converted to String"
        //   dokładnie tak samo jak: String tekst = "Hello"; tekst = 42; // ❌
        System.out.println("\nvar tekst = \"Hello\"; -> typ String ustalony na stałe, tekst = 42; to błąd kompilacji.");

        /*
         * ============================================================
         * 📌 CZYTELNOŚĆ - KIEDY UŻYWAĆ, A KIEDY UNIKAĆ var
         * ============================================================
         * `var` to narzędzie, nie nawyk - ma redukować szum (powtórzony
         * typ po obu stronach `=`), a nie ukrywać istotną informację.
         *
         * ✅ UŻYWAJ var, gdy typ jest oczywisty z prawej strony:
         *    var lista = new ArrayList<String>();      // typ w samej nazwie klasy
         *    var uzytkownik = new User("Ala", 30);      // konstruktor mówi wprost
         *
         * ✅ UŻYWAJ var, gdy nazwa zmiennej jest bardzo opisowa:
         *    var licznikBledow = 0;
         *    var czyAktywny = sprawdzStatus();
         *
         * ❌ UNIKAJ var, gdy ukrywa istotną informację o typie:
         *    var wynik = obliczSuma();   // int? double? BigDecimal? nie wiadomo bez zajrzenia do metody
         *    var dane = pobierzDane();   // List? Optional? Map? całkowicie nieczytelne
         *
         * ❌ UNIKAJ var przy typach interfejsowych, gdy chcesz podkreślić
         *    abstrakcję (np. `List<String> l = new ArrayList<>();` jasno mówi
         *    "programuj do interfejsu" - `var l = new ArrayList<String>();`
         *    technicznie wywnioskuje konkretny typ ArrayList, nie List).
         *
         * Zasada praktyczna: jeśli czytając samą linijkę z var (bez zaglądania
         * do dokumentacji metody) NIE potrafisz powiedzieć, jaki to typ - nie
         * używaj var w tym miejscu.
         */
        System.out.println("\nZasada: var tam, gdzie typ widac na pierwszy rzut oka; jawny typ, gdy var by go ukryl.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - var = wnioskowanie typu zmiennej LOKALNEJ na podstawie prawej
         *   strony przypisania, ustalane RAZ w czasie kompilacji.
         * - Działa dla: zmiennych lokalnych z inicjalizatorem, zmiennych
         *   for-each, zasobów try-with-resources, liczników zwykłego for.
         * - NIE działa dla: pól klasy, parametrów metod, typu zwracanego,
         *   deklaracji bez inicjalizatora, inicjalizacji samym null,
         *   wielu zmiennych w jednej deklaracji.
         * - Generyki/diamond wciąż trzeba podać PO PRAWEJ stronie - var nie
         *   zwalnia z podania <String> czy <Integer>.
         * - var to statyczne typowanie w przebraniu wygody składniowej -
         *   NIE dynamiczne typowanie jak w JS/Pythonie.
         * - Używaj var tam, gdzie poprawia czytelność (typ oczywisty z
         *   prawej strony lub z nazwy zmiennej); unikaj tam, gdzie ukrywa
         *   ważną informację o typie.
         */
        System.out.println("\n=== KONIEC LEKCJI 23 ===");
    }
}

/**
 * Klasa pomocnicza, na którą powołuje się dokumentacja powyżej - pokazuje
 * (w komentarzach, bo błędy kompilacji nie mogą trafić do realnego kodu)
 * miejsca, w których `var` jest zabroniony przez specyfikację języka.
 */
class BledneUzyciaVar {
    // var pole = 10; // ❌ pole klasy - var niedozwolony

    // var metodaZwracajacaVar() { // ❌ typ zwracany - var niedozwolony
    //     return 5;
    // }

    // void metoda(var parametr) { } // ❌ parametr metody - var niedozwolony
}
