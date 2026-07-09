package com.example.javaquest._14_advancedjava.Lesson21_PatternMatchingSwitchAndRecordPatterns;

public class _Exercises_Lesson21_PatternMatchingSwitchAndRecordPatterns {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SwitchPatternMatchingDescribeShape {
        /*
         * 🧪 Zadanie 1:
         * Użyj sealed interface Shape (Circle/Rectangle/Triangle) z lekcji.
         * Napisz metodę String area(Shape shape) zwracającą pole
         * powierzchni jako sformatowany String, za pomocą switcha z
         * pattern matching - BEZ default (Shape jest sealed, obsłuż
         * wszystkie 3 podtypy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddNewCaseCompileErrorComment {
        /*
         * 🧪 Zadanie 2:
         * W KOMENTARZU opisz, co by się stało z metodą area() z Zadania 1,
         * gdyby do permits Shape dopisano czwarty rekord (np. Pentagon), a
         * switcha NIE zaktualizowano. Zapisz przybliżoną treść komunikatu
         * błędu kompilacji dotyczącego niewyczerpanego switcha.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RecordPatternBasicDeconstruction {
        /*
         * 🧪 Zadanie 3:
         * Użyj record Point(int x, int y) z lekcji. Napisz metodę
         * String format(Point p) używającą record pattern w switchu
         * (`case Point(int x, int y) -> "(" + x + ", " + y + ")"`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RecordPatternWithInstanceof {
        /*
         * 🧪 Zadanie 4:
         * Napisz metodę printSumIfPoint(Object obj) używającą
         * `if (obj instanceof Point(int x, int y))` i wypisującą x + y.
         * Przetestuj dla Point(3, 7) i dla Stringa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NestedRecordPatternLineMidpoint {
        /*
         * 🧪 Zadanie 5:
         * Użyj record Line(Point start, Point end) z lekcji. Napisz metodę
         * String midpoint(Line line) obliczającą i formatującą środek
         * odcinka, dekomponując OD RAZU oba punkty w jednym zagnieżdżonym
         * record patternie: `Line(Point(int x1, int y1), Point(int x2, int
         * y2))`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_GuardedPatternOrderingPitfall {
        /*
         * 🧪 Zadanie 6:
         * W KOMENTARZU zapisz BŁĘDNĄ kolejność gałęzi w switchu: najpierw
         * `case Circle c -> ...` (bez when), a POTEM `case Circle c when
         * c.radius() > 5 -> ...`. Zapisz komunikat błędu kompilacji
         * ("unreachable"). Następnie napisz POPRAWNĄ, działającą wersję
         * (guard PRZED gołą gałęzią).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GuardedPatternSizeCategoryTriangle {
        /*
         * 🧪 Zadanie 7:
         * Napisz metodę String triangleSizeCategory(Triangle t) zwracającą
         * "Duży trójkąt" gdy pole (0.5 * base * height) > 20, w
         * przeciwnym razie "Mały trójkąt" - za pomocą guarded pattern
         * (`when`) plus gołej gałęzi fallback.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExhaustivePatternSwitchExprStructure {
        /*
         * 🧪 Zadanie 8:
         * Użyj sealed interface Expr (Num/Add/Multiply) z lekcji. Napisz
         * metodę String structure(Expr expr) zwracającą OPIS struktury
         * (nie wynik liczbowy!), np. "liczba", "suma", "iloczyn" - za
         * pomocą switcha z record patterns, BEZ default.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EvalExpressionTree {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj drzewo Expr reprezentujące (5 + 3) * 2 (użyj Num/Add/
         * Multiply z lekcji) i wywołaj na nim metodę eval() z lekcji.
         * Wypisz wynik (spodziewane 16.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NullCaseInPatternSwitch {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę String safeName(Shape shape) obsługującą
         * `case null -> "brak"` ORAZ wszystkie trzy podtypy Shape.
         * Przetestuj wywołując dla null oraz dla instancji Circle.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RecordPatternWithVar {
        /*
         * 🧪 Zadanie 11:
         * Przepisz metodę format(Point p) z Zadania 3, używając `var`
         * zamiast jawnych typów w record patternie: `case Point(var x, var
         * y) ->`. W komentarzu porównaj czytelność obu wersji i podaj,
         * kiedy `var` jest bardziej odpowiednie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DeepNestedRecordPatternThreeLevels {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj lokalnie record Path(Line first, Line second). Napisz
         * metodę wypisującą WSZYSTKIE cztery współrzędne (x1,y1,x2,y2 z
         * first ORAZ x3,y3,x4,y4 z second) w JEDNYM trzypoziomowym,
         * zagnieżdżonym record patternie (Path -> Line -> Point).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GuardedPatternWithMultipleConditions {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę String rectangleKind(Rectangle r) rozróżniającą
         * "Kwadrat" (width == height), "Wąski prostokąt" (width < height /
         * 2) i "Zwykły prostokąt" (pozostałe przypadki) - za pomocą DWÓCH
         * guarded patterns (`when` z warunkami złożonymi) plus fallbacku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExhaustivenessBenefitDemo {
        /*
         * 🧪 Zadanie 14:
         * W KOMENTARZU napisz DWIE wersje metody liczącej "liczbę boków"
         * kształtu: (a) stary styl if/else if z instanceof, w którym
         * CELOWO "zapomniano" obsłużyć Triangle (kod cicho zwraca złą
         * wartość z default/else), (b) switch pattern matching, w którym
         * brak gałęzi Triangle jest BŁĘDEM KOMPILACJI. Wyjaśnij różnicę w
         * bezpieczeństwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RecordPatternWithGuardOnSquareCheck {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę String classify(Rectangle r) używającą record
         * patternu z guardem operującym na zdekomponowanych polach:
         * `case Rectangle(double w, double h) when w == h -> "Kwadrat!"`,
         * z fallbackiem `case Rectangle(double w, double h) -> "Prostokąt
         * " + w + "x" + h`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SwitchExpressionReturningRecord {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj lokalnie record BoundingBox(double width, double
         * height). Napisz metodę BoundingBox boundingBoxOf(Shape shape)
         * zwracającą DLA KAŻDEGO podtypu inaczej wyliczony BoundingBox
         * (np. dla Circle: (2*radius, 2*radius)) za pomocą switcha
         * zwracającego nowy rekord w każdej gałęzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CombineGuardsWithoutMissingFallback {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę String triangleCategoryDetailed(Triangle t) z
         * DWOMA guarded gałęziami dla Triangle (mała/duża powierzchnia).
         * W KOMENTARZU wyjaśnij, dlaczego kompilator WYMAGA jeszcze
         * trzeciej, gołej gałęzi `case Triangle t -> ...` jako fallbacku
         * (guardy same w sobie NIE liczą się do wyczerpania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExprTreePrettyPrinter {
        /*
         * 🧪 Zadanie 18:
         * Napisz REKURENCYJNĄ metodę String toInfixString(Expr expr)
         * zwracającą zapis infiksowy wyrażenia, np. dla (2 + 3) * 4 zwróć
         * "((2.0 + 3.0) * 4.0)" - użyj switcha z record patterns i
         * wywołań rekurencyjnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExprTreeDepthCounter {
        /*
         * 🧪 Zadanie 19:
         * Napisz REKURENCYJNĄ metodę int depth(Expr expr) liczącą
         * głębokość drzewa (Num ma głębokość 1, Add/Multiply mają
         * głębokość 1 + max(depth(left), depth(right))) - za pomocą
         * switcha z record patterns. Przetestuj na drzewie z co najmniej
         * 3 poziomami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MixingConstantAndTypePatternsInSwitch {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę String interpret(Object o) obsługującą w JEDNYM
         * switchu ZARÓWNO stałe etykiety Stringów (`case "STOP" ->
         * "Zatrzymaj się"`), JAK I wzorce typu (`case Integer i ->
         * "Liczba: " + i`), plus `default`. Przetestuj dla "STOP", 42 i
         * 3.14.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorLesson20CascadeIntoSwitch {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę classify(Object o) obsługującą String/Integer/
         * Double/java.util.List<?> NAJPIERW jako łańcuch if/else if z
         * instanceof (styl z Lekcji 20), a POTEM jako switch pattern
         * matching. W komentarzu porównaj czytelność i policz linie kodu
         * obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimplifyExpressionTreeRule {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę Expr simplify(Expr expr) wykrywającą za pomocą
         * zagnieżdżonych record patterns dwa przypadki: mnożenie przez
         * Num(0) (`case Multiply(Num(double v), Expr r) when v == 0 ->
         * new Num(0)` i analogicznie dla prawej strony) - zwracającą
         * Num(0) w tych przypadkach, a w pozostałych oryginalne wyrażenie
         * bez zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ShapeAreaCalculatorWithBoundingCheck {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę double safeArea(Shape shape), która za pomocą
         * guarded pattern wykrywa "nieprawidłowy" Circle (radius <= 0) i
         * rzuca IllegalArgumentException z czytelnym komunikatem, a dla
         * pozostałych poprawnych kształtów zwraca pole powierzchni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_IsConstantExpressionCheck {
        /*
         * 🧪 Zadanie 24:
         * Napisz REKURENCYJNĄ metodę boolean isConstant(Expr expr) zwracającą
         * true, gdy wyrażenie składa się WYŁĄCZNIE z Num (bez zmiennych) -
         * dla Num zawsze true, dla Add/Multiply true TYLKO gdy OBIE strony
         * są constant. Użyj switcha z record patterns i rekurencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RecordPatternInEnhancedForLoop {
        /*
         * 🧪 Zadanie 25:
         * Mając java.util.List<Point> points z co najmniej 3 punktami,
         * użyj record patternu BEZPOŚREDNIO w nagłówku pętli for-each:
         * `for (Point(int x, int y) : points)` i wypisz sumę x+y dla
         * każdego punktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CombineSealedInterfacesTwoHierarchiesInOneMethod {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę String describeAny(Object o) obsługującą w JEDNYM
         * switchu WSZYSTKIE podtypy Shape (Circle/Rectangle/Triangle) ORAZ
         * WSZYSTKIE podtypy Expr (Num/Add/Multiply), plus `default` na
         * koniec (bo Object nie gwarantuje wyczerpania). Przetestuj na
         * przykładach z obu hierarchii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GuardedNullSafeChain {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę String describeCarefully(Shape shape) obsługującą
         * w JEDNYM switchu: `case null ->`, guarded `case Circle c when
         * c.radius() == 0 -> "zdegenerowane koło"`, gołą `case Circle c
         * ->`, oraz Rectangle i Triangle. Przetestuj dla null, Circle(0),
         * Circle(5) i Rectangle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PerformanceNoteRefactorFourBranchIfCascade {
        /*
         * 🧪 Zadanie 28:
         * Napisz NAJPIERW 4-gałęziowy łańcuch if/else if z instanceof dla
         * TEJ SAMEJ zmiennej Object o (String/Integer/Double/Boolean).
         * Zrefaktoryzuj do JEDNEGO switcha pattern matching. W komentarzu
         * wyjaśnij, dlaczego switch jest tu lepszy niż seria osobnych
         * sprawdzeń if (jasność intencji, jedno miejsce dopasowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MiniProjectAddDivideWithGuard {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj NOWY record Divide(Expr left, Expr right) i DODAJ go
         * do permits sealed interface Expr (będziesz musiał/a
         * skopiować/rozszerzyć hierarchię lokalnie w tym pliku, bo Expr z
         * lekcji ma zamknięty permits). Napisz eval(Expr) z guarded case
         * dla Divide wykrywającym dzielenie przez 0 (right ewaluuje się do
         * 0) i rzucającym ArithmeticException, w przeciwnym razie
         * zwracającym iloraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMiniBooleanInterpreter {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj sealed interface BoolExpr permits BoolLit, And, Or,
         * Not (recordy: BoolLit(boolean value), And(BoolExpr left,
         * BoolExpr right), Or(BoolExpr left, BoolExpr right), Not(BoolExpr
         * expr)). Napisz REKURENCYJNĄ metodę boolean evalBool(BoolExpr
         * expr) za pomocą switcha z record patterns, z DODATKOWĄ gałęzią
         * optymalizującą krótkie spięcie: `case And(BoolLit(boolean v),
         * BoolExpr other) when !v -> false` (And z false po lewej zawsze
         * daje false, bez liczenia prawej strony). Przetestuj na drzewie
         * reprezentującym (true AND false) OR (NOT false).
         */
        public static void main(String[] args) { }
    }
}
