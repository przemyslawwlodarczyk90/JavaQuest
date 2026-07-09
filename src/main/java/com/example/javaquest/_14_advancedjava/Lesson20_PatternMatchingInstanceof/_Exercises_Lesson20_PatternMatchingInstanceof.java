package com.example.javaquest._14_advancedjava.Lesson20_PatternMatchingInstanceof;

public class _Exercises_Lesson20_PatternMatchingInstanceof {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicPatternMatchInstanceof {
        /*
         * 🧪 Zadanie 1:
         * Napisz metodę printIfInteger(Object o), która za pomocą pattern
         * matching instanceof (if (o instanceof Integer i)) sprawdza, czy
         * argument jest Integerem, i jeśli tak - wypisuje jego wartość razy
         * 2. Przetestuj dla 21 oraz dla "tekst".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_PatternVariableScopeInIfBlock {
        /*
         * 🧪 Zadanie 2:
         * Napisz metodę demonstrującą, że zmienna wzorca z instanceof jest
         * dostępna TYLKO wewnątrz bloku if. W KOMENTARZU pod kodem zapisz,
         * jaki błąd kompilacji wystąpiłby, gdyby spróbować użyć tej
         * zmiennej PO bloku if.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NegationIdiomEarlyReturn {
        /*
         * 🧪 Zadanie 3:
         * Napisz metodę printUpperCaseIfString(Object o) używającą idiomu
         * negacji: `if (!(o instanceof String s)) { return; }`, a
         * następnie (poza blokiem if) użyj zmiennej s do wypisania
         * s.toUpperCase(). Przetestuj dla "ala" i dla 100.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CombineWithAnd {
        /*
         * 🧪 Zadanie 4:
         * Napisz metodę boolean isLongString(Object o) zwracającą true,
         * gdy `o instanceof String s && s.length() > 5`. Przetestuj dla
         * "krótki", "bardzo długi tekst" i dla liczby 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WhyNotOr {
        /*
         * 🧪 Zadanie 5:
         * W KOMENTARZU zapisz próbę napisania warunku `if (o instanceof
         * String s || s.length() > 3)`. Wyjaśnij dokładnie (2-3 zdania),
         * dlaczego to się NIE kompiluje - co dzieje się w gałęzi, gdy
         * lewa strona || jest false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleTypesChain {
        /*
         * 🧪 Zadanie 6:
         * Napisz metodę String classify(Object o) obsługującą kolejno (bez
         * switcha - tylko if/else if z pattern matching) typy: String,
         * Integer, Double, Boolean, zwracając opisowy tekst dla każdego, a
         * dla reszty "nieznany typ". Przetestuj na tablicy z co najmniej
         * jednym przykładem każdego typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PatternVariableNaming {
        /*
         * 🧪 Zadanie 7:
         * Napisz metodę obsługującą trzy typy (String, Integer, Double) w
         * jednym łańcuchu if/else if, nadając KAŻDEJ zmiennej wzorca inną,
         * sensowną nazwę (nie s1/s2/s3, tylko np. text, count, price).
         * Wypisz wynik dla przykładowych wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CombiningWithMethodCall {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę printFirstIfNonEmptyList(Object o), która sprawdza
         * `o instanceof java.util.List<?> list && !list.isEmpty()` i jeśli
         * prawda - wypisuje list.get(0). Przetestuj na niepustej liście,
         * pustej liście i na Stringu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NestedIfWithoutPatternMatchingThenRefactor {
        /*
         * 🧪 Zadanie 9:
         * NAJPIERW napisz metodę z klasycznym, ZAGNIEŻDŻONYM
         * (dwupoziomowym) instanceof + rzutowaniem (if (o instanceof
         * String) { String s = (String) o; if (s.length() > 3) {...} }).
         * POTEM zrefaktoryzuj ją do JEDNEGO if-a z pattern matching i &&.
         * W komentarzu porównaj liczbę linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_UsingPatternVarInReturnExpression {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę String describe(Object o), która w JEDNYM if-ie z
         * pattern matching zwraca sformatowany String bezpośrednio w linii
         * return (np. `if (o instanceof Integer i) return "int:" + i;`).
         * Dodaj domyślny return dla pozostałych przypadków.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorCascadeFromLesson06 {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj lokalnie proste klasy Circle2(double radius), Rectangle2
         * (double w, double h), Triangle2(double base, double height) (bez
         * wspólnego nadtypu jest OK - obiekt wejściowy to Object). Napisz
         * metodę String describeShape(Object shape) klasyfikującą wszystkie
         * trzy typy za pomocą łańcucha instanceof pattern matching.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GuardWithComplexBooleanExpression {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę boolean isValidUsername(Object o), sprawdzającą
         * jednym warunkiem: `o instanceof String s && s.length() >= 3 &&
         * s.length() <= 12 && !s.contains(" ")`. Przetestuj na kilku
         * różnych Stringach i jednym Integerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PatternVariableShadowingPitfall {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę z lokalną zmienną String s = "zewnętrzne"; a
         * następnie SPRÓBUJ (w komentarzu) napisać
         * `if (o instanceof String s)` z tą samą nazwą s wewnątrz tej
         * metody - opisz błąd kompilacji (duplikat zmiennej) i napraw,
         * zmieniając nazwę zmiennej wzorca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReassigningPatternVariable {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę, która w bloku `if (o instanceof String s)`
         * NAJPIERW wypisuje s, potem PRZYPISUJE do s nową wartość
         * (s = s.trim().toLowerCase()) i wypisuje ponownie. W komentarzu
         * potwierdź, że to się kompiluje, bo zmienna wzorca nie jest
         * niejawnie final.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_InstanceofWithGenericsUncheckedWarning {
        /*
         * 🧪 Zadanie 15:
         * W KOMENTARZU wyjaśnij, dlaczego `obj instanceof List<String> list`
         * jest NIELEGALNE (typ generyczny jest wymazywany w runtime - type
         * erasure, temat z wcześniejszego rozdziału o generykach). Napisz
         * DZIAŁAJĄCY odpowiednik z `List<?> list` i zademonstruj go na
         * przykładzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_NegationIdiomInLoop {
        /*
         * 🧪 Zadanie 16:
         * Mając tablicę Object[] items z mieszanymi typami, napisz pętlę
         * for-each, która dla KAŻDEGO elementu używa idiomu negacji z
         * `continue` (zamiast return) - jeśli element nie jest Stringiem,
         * pomiń go (continue), w przeciwnym razie wypisz jego długość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultiConditionEarlyExitChain {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę processOrder(Object customerId, Object amount) z
         * DWOMA kolejnymi "strażnikami" (guard clauses) opartymi na
         * idiomie negacji: (1) jeśli customerId nie jest Stringiem -
         * return z komunikatem błędu; (2) jeśli amount nie jest Double lub
         * jest <= 0 - return z komunikatem błędu. Jeśli oba przejdą,
         * wypisz "Zamówienie przyjęte".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PatternMatchingInBooleanReturningMethod {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę predykatu boolean isPositiveInteger(Object o) w
         * JEDNEJ linii zwracającej `o instanceof Integer i && i > 0`.
         * Przetestuj dla -5, 0, 8 i "tekst".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CombiningInstanceofWithTernary {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę String safeLabel(Object o) używającą wyrażenia
         * ternarnego z pattern matching w warunku: `(o instanceof String s
         * && !s.isEmpty()) ? s : "brak danych"`. Przetestuj na "", "abc" i
         * null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorLegacyValidationMethod {
        /*
         * 🧪 Zadanie 20:
         * NAJPIERW napisz (oznaczoną komentarzem "// LEGACY") metodę
         * walidującą Object payload przez stary styl: 3 osobne bloki
         * `if (x instanceof TYP) { TYP zmienna = (TYP) x; ... }` sprawdzające
         * String/Integer/Double. POTEM napisz jej w pełni zrefaktoryzowaną
         * wersję z łańcuchem pattern matching i porównaj obie w komentarzu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FlowScopingAcrossElseIfChain {
        /*
         * 🧪 Zadanie 21:
         * Napisz łańcuch if/else if z DWOMA różnymi instanceof (String s w
         * pierwszym if, Integer i w drugim else if). W KOMENTARZU wyjaśnij,
         * dlaczego zmienna s z pierwszej gałęzi NIE jest widoczna w
         * gałęzi else if/else (każda gałąź ma własny zasięg wynikający z
         * przepływu sterowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FlowScopingWithNegationAndAndAcrossTwoChecks {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę pomocniczą łączącą idiom negacji z DWOMA typami
         * pod rząd: `if (!(a instanceof String s)) return "błąd A";
         * if (!(b instanceof Integer i)) return "błąd B"; return s + i;`.
         * Przetestuj dla poprawnych i niepoprawnych argumentów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PatternMatchingWithArraysInstanceof {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę String summarize(Object o) sprawdzającą
         * `o instanceof int[] arr` (sumuje elementy) oraz
         * `o instanceof String[] arr` (łączy przez ", "). Przetestuj na
         * int[]{1,2,3} i String[]{"a","b","c"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ShapeLikeHierarchyInstanceofNoExhaustivenessCheck {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj LOKALNIE (w tym pliku) prostą hierarchię inspirowaną
         * Lesson19_SealedClasses: interfejs ShapeX z klasami CircleX,
         * SquareX, TriangleX. Napisz metodę String describe(ShapeX shape)
         * łańcuchem instanceof pattern matching obsługującą WSZYSTKIE trzy
         * typy. W komentarzu zaznacz, że nawet gdyby ShapeX był sealed,
         * ten if/else-if i tak NIE byłby sprawdzany pod kątem kompletności
         * przez kompilator (to dopiero switch z Lekcji 21).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CombiningPatternMatchWithStreamsFilter {
        /*
         * 🧪 Zadanie 25:
         * Mając List<Object> mixed = List.of("a", 1, "bb", 2.0, "ccc"),
         * użyj Stream API: .filter(o -> o instanceof String s &&
         * s.length() > 1) połączonego z .map(o -> (String) o) - zbierz
         * wynik do List<String> i wypisz. W komentarzu wyjaśnij, dlaczego
         * w .filter() zmienna s NIE jest dostępna w kolejnym .map() (to
         * osobna lambda, inny zasięg).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RecordPatternPreviewManualEquivalent {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj lokalny record PointX(int x, int y). Napisz metodę
         * printPoint(Object o) używającą DZISIEJSZEGO pattern matching:
         * `if (o instanceof PointX p) { int x = p.x(); int y = p.y(); ... }`
         * W KOMENTARZU zapowiedz (Lekcja 21), że record pattern pozwoli to
         * zapisać krócej: `if (o instanceof PointX(int x, int y))`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PerformanceNoteRedundantInstanceofCalls {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę z ANTY-WZORCEM: DWUKROTNYM sprawdzeniem `if (o
         * instanceof String)` dla tego samego obiektu (raz bez wzorca, raz
         * z osobnym rzutowaniem) zamiast JEDNEGO pattern matching. Popraw
         * kod do JEDNEGO sprawdzenia z pattern matching i skomentuj,
         * dlaczego powtórne sprawdzanie typu to code smell.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CustomExceptionHierarchyPatternMatching {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj lokalnie dwa wyjątki: class ValidationException extends
         * RuntimeException i class NetworkException extends RuntimeException.
         * W bloku catch (Exception e) użyj pattern matching (if (e
         * instanceof ValidationException ve) ... else if (e instanceof
         * NetworkException ne) ...) do obsługi każdego typu inaczej.
         * Przetestuj rzucając oba typy wyjątków po kolei.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GenericSafeCastHelperContrast {
        /*
         * 🧪 Zadanie 29:
         * Napisz generyczną metodę statyczną <T> java.util.Optional<T>
         * safeCast(Object obj, Class<T> type) używającą type.isInstance(obj)
         * (NIE pattern matching instanceof, bo typ jest dynamiczny -
         * Class<T> znany dopiero w runtime). W komentarzu wyjaśnij,
         * dlaczego pattern matching instanceof działa TYLKO z typem
         * znanym w czasie kompilacji, a ta metoda pokazuje alternatywne
         * podejście oparte na refleksji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniProjectShapeDescriptionEngine {
        /*
         * 🧪 Zadanie 30:
         * Rozbuduj hierarchię ShapeX z Zadania 24 o czwarty wariant
         * BigSquareX extends SquareX (analogicznie do BigSquare/Square z
         * Lesson19_SealedClasses). Napisz PEŁNY "silnik opisu" - metodę
         * String fullDescription(ShapeX shape) obsługującą wszystkie
         * cztery warianty łańcuchem pattern matching (z odrębną gałęzią
         * dla BigSquareX PRZED ogólną gałęzią SquareX) plus domyślny
         * fallback. Przetestuj na mieszanej tablicy 5 kształtów.
         */
        public static void main(String[] args) { }
    }
}
