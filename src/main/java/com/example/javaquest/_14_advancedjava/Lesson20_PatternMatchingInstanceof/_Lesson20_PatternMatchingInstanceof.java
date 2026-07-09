package com.example.javaquest._14_advancedjava.Lesson20_PatternMatchingInstanceof;

import java.util.List;

public class _Lesson20_PatternMatchingInstanceof {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔁 PRZYPOMNIENIE Z _02_oop/Lesson06_Polymorphism
         * ============================================================
         * W Lekcji 06 (OOP) poznałeś już podstawową składnię:
         *
         *   if (someShape instanceof Circle c) {
         *       System.out.println(c.radius);
         *   }
         *
         * zamiast starego, dwuetapowego:
         *
         *   if (someShape instanceof Circle) {
         *       Circle circle = (Circle) someShape;
         *       System.out.println(circle.radius);
         *   }
         *
         * To pattern matching for instanceof (JDK 16). W tej lekcji
         * pogłębiamy temat: jak DOKŁADNIE działa zasięg zmiennej `c`
         * (flow scoping), jak łączyć to z `&&`, dlaczego NIE da się
         * połączyć z `||`, i jak refaktoryzować stare kaskady instanceof.
         */

        Object obj = "Ala ma kota";

        if (obj instanceof String s) {
            System.out.println("Długość: " + s.length());
        }

        /*
         * ============================================================
         * 🔍 FLOW SCOPING – ZASIĘG ZALEŻNY OD PRZEPŁYWU STEROWANIA
         * ============================================================
         * Zmienna wzorca (pattern variable) `s` NIE jest zwykłą zmienną
         * lokalną o stałym zasięgu bloku {}. Jej zasięg wyznacza
         * kompilator na podstawie ANALIZY PRZEPŁYWU (flow analysis):
         * `s` jest widoczna WSZĘDZIE tam, gdzie kompilator może
         * UDOWODNIĆ, że instanceof było prawdziwe.
         *
         * Najprostszy przypadek – wewnątrz bloku `if`:
         *
         *   if (obj instanceof String s) {
         *       // tu s jest widoczne i na pewno jest Stringiem
         *   }
         *   // TU s już NIE istnieje (poza blokiem if)
         *
         * Poniższy kod (gdyby odkomentować) NIE skompiluje się:
         *   if (obj instanceof String s) { }
         *   System.out.println(s.length()); // BŁĄD: cannot find symbol s
         */

        /*
         * ============================================================
         * 🚫 IDIOM NEGACJI – `!(x instanceof T t)` + WCZEŚNIEJSZY return
         * ============================================================
         * Najbardziej przydatny przykład flow scopingu: NEGACJA warunku
         * połączona z wcześniejszym wyjściem z metody (return/continue/
         * throw). Jeśli w gałęzi "false" metoda na pewno się kończy,
         * kompilator WIE, że po całym if-ie warunek pozytywny musiał być
         * prawdziwy – więc zmienna wzorca jest dostępna DALEJ, PO if-ie,
         * bez zagnieżdżania kodu w kolejnym bloku.
         */

        printLengthIfString(obj);
        printLengthIfString(42);

        /*
         * ============================================================
         * 🔗 ŁĄCZENIE Z && – WARUNEK ZŁOŻONY
         * ============================================================
         * `&&` jest leniwy (short-circuit) i sprawdzany OD LEWEJ DO
         * PRAWEJ. Dzięki temu w wyrażeniu:
         *
         *   obj instanceof String s && s.length() > 3
         *
         * w momencie oceny `s.length() > 3` kompilator MA PEWNOŚĆ,
         * że `s` istnieje i jest typu String – bo `&&` oceniłoby prawą
         * stronę TYLKO, gdyby lewa była true. To jest bezpieczne
         * i to jest DOKŁADNIE ten sam mechanizm flow scopingu.
         */

        describe(obj);
        describe(" ");
        describe(123);

        /*
         * ============================================================
         * ❌ DLACZEGO NIE `||`?
         * ============================================================
         * Spróbujmy analogicznie z `||`:
         *
         *   if (obj instanceof String s || s.length() > 3) { ... }
         *
         * To NIE SKOMPILUJE SIĘ:
         *   error: cannot find symbol s.length()
         *
         * Dlaczego? `||` też jest leniwe, ale w PRZECIWNĄ stronę: prawa
         * strona jest oceniana TYLKO, gdy lewa jest FALSE. A skoro lewa
         * jest false, to znaczy, że `obj` NIE JEST Stringiem – więc `s`
         * NIE JEST zdefiniowane w momencie oceny prawej strony.
         * Kompilator odmawia skompilowania kodu, w którym zmienna
         * mogłaby być użyta bez gwarancji przypisania (definite
         * assignment). Poniższa linia (celowo w komentarzu, bo się nie
         * kompiluje):
         *
         *   if (obj instanceof String s || s.length() > 3) { }
         */

        /*
         * ============================================================
         * 🛠️ REFAKTORYZACJA: STARA KASKADA instanceof+CAST → PATTERN MATCHING
         * ============================================================
         * PRZED (styl sprzed Javy 16 – każdy branch osobno rzutuje):
         *
         *   static String classifyOld(Object obj) {
         *       if (obj instanceof String) {
         *           String s = (String) obj;
         *           return "String o długości " + s.length();
         *       } else if (obj instanceof Integer) {
         *           Integer i = (Integer) obj;
         *           return "Integer " + i;
         *       } else if (obj instanceof List) {
         *           List<?> list = (List<?>) obj;
         *           return "Lista o rozmiarze " + list.size();
         *       } else {
         *           return "Nieznany typ";
         *       }
         *   }
         *
         * PO (pattern matching – bez osobnego rzutowania, krócej i
         * bezpieczniej – zobacz metodę classify() poniżej):
         */

        Object[] items = {"hello", 42, 3.14, List.of(1, 2, 3), 99L};
        for (Object item : items) {
            System.out.println(classify(item));
        }

        /*
         * ============================================================
         * ⚠️ PATTERN VARIABLE NIE JEST NIEJAWNIE final
         * ============================================================
         * W odróżnieniu od zmiennych przechwytywanych przez lambdę
         * (które MUSZĄ być effectively final), zmienna wzorca z
         * instanceof zachowuje się jak ZWYKŁA zmienna lokalna – MOŻNA
         * ją ponownie przypisać. To dozwolone, choć w praktyce rzadko
         * potrzebne (i bywa mylące – najlepiej traktować ją jak stałą).
         */

        reassignPatternVariableDemo(obj);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `if (obj instanceof T t)` – zmienna `t` żyje dzięki FLOW
         *   SCOPING, nie zwykłemu zasięgowi bloku {}
         * - idiom negacji + wcześniejsze wyjście (`return`/`continue`/
         *   `throw`) "przenosi" zmienną wzorca PO if-ie
         * - `&&` można bezpiecznie łączyć z instanceof (leniwa ewaluacja
         *   od lewej gwarantuje, że wzorzec już "zadziałał")
         * - `||` NIE DA SIĘ tak połączyć – w prawej gałęzi zmienna
         *   wzorca nie jest jeszcze zdefiniowana (błąd kompilacji)
         * - pattern matching pozwala ZASTĄPIĆ kaskady instanceof+cast
         *   krótszym i bezpieczniejszym kodem
         * - zmienna wzorca NIE jest niejawnie final (w odróżnieniu od
         *   zmiennych przechwytywanych przez lambdy)
         * - w Lekcji 21 zobaczysz, jak switch pattern matching idzie
         *   o krok dalej i wymusza KOMPLETNOŚĆ obsługi typów
         */
    }

    static void printLengthIfString(Object obj) {
        if (!(obj instanceof String s)) {
            System.out.println("To nie jest String, kończę.");
            return;
        }
        // dzięki flow scopingowi: skoro nie zwróciliśmy się wyżej,
        // kompilator WIE, że obj JEST Stringiem – s jest tu dostępne
        System.out.println("Długość Stringa: " + s.length());
    }

    static void describe(Object obj) {
        if (obj instanceof String s && !s.isBlank()) {
            System.out.println("Niepusty String: \"" + s + "\"");
        } else {
            System.out.println("Puste/nie-String: " + obj);
        }
    }

    static String classify(Object obj) {
        if (obj instanceof String s) {
            return "String o długości " + s.length();
        } else if (obj instanceof Integer i && i > 0) {
            return "Dodatni Integer " + i;
        } else if (obj instanceof Integer i) {
            return "Integer (niedodatni) " + i;
        } else if (obj instanceof Double d) {
            return "Double " + d;
        } else if (obj instanceof List<?> list) {
            return "Lista o rozmiarze " + list.size();
        } else {
            return "Nieznany typ: " + obj.getClass().getSimpleName();
        }
    }

    static void reassignPatternVariableDemo(Object obj) {
        if (obj instanceof String s) {
            System.out.println("Przed: " + s);
            s = s.toUpperCase(); // dozwolone - s nie jest niejawnie final
            System.out.println("Po ponownym przypisaniu: " + s);
        }
    }
}
