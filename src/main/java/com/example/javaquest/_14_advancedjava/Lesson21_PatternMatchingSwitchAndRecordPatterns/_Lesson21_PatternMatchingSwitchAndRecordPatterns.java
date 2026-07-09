package com.example.javaquest._14_advancedjava.Lesson21_PatternMatchingSwitchAndRecordPatterns;

public class _Lesson21_PatternMatchingSwitchAndRecordPatterns {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔁 PUNKT WYJŚCIA: SEALED HIERARCHIA Z LEKCJI 19
         * ============================================================
         * W Lekcji 19 (sealed classes) poznałeś domknięty zbiór typów:
         * sealed interface Shape permits Circle, Square, Triangle. Tam
         * obsługa różnych podtypów odbywała się przez łańcuch instanceof
         * (Lekcja 20). Teraz, dzięki switch pattern matching (JEP 441) i
         * record patterns (JEP 440, oba stabilne od JDK 21), możemy to
         * zapisać krócej i - co ważniejsze - z GWARANCJĄ KOMPLETNOŚCI
         * sprawdzaną przez kompilator.
         *
         * Na potrzeby tej lekcji definiujemy WŁASNĄ, lokalną wersję
         * hierarchii Shape jako recordy (patrz na dół pliku) - recordy są
         * idealnym partnerem dla sealed + pattern matching, bo od razu
         * wystawiają swoje pola do dekompozycji.
         */

        Shape[] shapes = {new Circle(3), new Rectangle(4, 5), new Triangle(6, 2)};

        /*
         * ============================================================
         * 🔀 SWITCH PATTERN MATCHING NA HIERARCHII SEALED
         * ============================================================
         * Każda gałąź `case Typ zmienna ->` sprawdza typ I OD RAZU
         * przypisuje dopasowaną wartość do zmiennej wzorca - dokładnie
         * jak instanceof, ale w formie switcha.
         *
         * Ponieważ Shape jest sealed z DOKŁADNIE trzema podtypami, a my
         * obsłużyliśmy wszystkie trzy, kompilator UZNAJE switch za
         * WYCZERPUJĄCY (exhaustive) - `default` NIE JEST WYMAGANY.
         */

        for (Shape shape : shapes) {
            System.out.println(describe(shape));
        }

        // Gdyby do permits Shape dopisać w przyszłości np. Pentagon,
        // metoda describe() PRZESTAŁABY SIĘ KOMPILOWAĆ, dopóki nie
        // dopiszemy nowej gałęzi `case Pentagon p -> ...`. To jest
        // dokładnie ta "siatka bezpieczeństwa przy refaktoryzacji",
        // zapowiedziana w Lekcji 19.

        /*
         * ============================================================
         * 🧩 RECORD PATTERNS – DEKOMPOZYCJA REKORDU (JEP 440)
         * ============================================================
         * Zamiast wywoływać akcesory (p.x(), p.y()) po dopasowaniu typu,
         * możemy OD RAZU "rozpakować" pola rekordu w samym wzorcu.
         * Działa to zarówno w instanceof, jak i w switch.
         */

        Point p = new Point(3, 7);
        printPoint(p);

        /*
         * ============================================================
         * 🪆 ZAGNIEŻDŻONE RECORD PATTERNS
         * ============================================================
         * Record patterns można ZAGNIEŻDŻAĆ dowolnie głęboko - jeśli
         * rekord zawiera pole będące innym rekordem, można je od razu
         * zdekomponować w JEDNYM wzorcu.
         */

        Line line = new Line(new Point(0, 0), new Point(3, 4));
        printLineLength(line);

        /*
         * ============================================================
         * 🛡️ GUARDED PATTERNS – SŁOWO KLUCZOWE `when`
         * ============================================================
         * Sam pattern matching sprawdza tylko TYP. Jeśli potrzebujemy
         * DODATKOWEGO warunku (np. tylko "duże" koła), dopisujemy klauzulę
         * `when` po wzorcu. WAŻNE: gałąź z `when` MUSI wystąpić PRZED
         * "gołą" gałęzią tego samego typu, inaczej ta druga jest
         * nieosiągalna (unreachable) i kompilacja się nie powiedzie.
         *
         * Guard (`when`) NIE liczy się do wyczerpania (exhaustiveness) -
         * nadal potrzebujemy "gołej" gałęzi Circle jako fallbacku.
         */

        for (Shape shape : shapes) {
            System.out.println(sizeCategory(shape));
        }

        /*
         * ============================================================
         * ❓ OBSŁUGA null W SWITCHU (JDK 21)
         * ============================================================
         * Od JDK 21 switch pattern matching może jawnie obsłużyć null
         * przez `case null ->`, bez rzucania NullPointerException (co
         * byłoby domyślnym zachowaniem zwykłego switcha - patrz Lekcja 03
         * i Lekcja 22). Można też połączyć: `case null, default -> ...`.
         */

        Shape maybeNull = null;
        System.out.println(describeNullable(maybeNull));
        System.out.println(describeNullable(new Circle(1)));

        /*
         * ============================================================
         * 🧮 PRZYKŁAD: MINI-EWALUATOR WYRAŻEŃ ARYTMETYCZNYCH
         * ============================================================
         * Łączymy sealed + record + switch pattern matching + record
         * patterns w jednym realistycznym przykładzie: drzewo wyrażeń
         * arytmetycznych (Expr) i jego rekurencyjna ewaluacja.
         *
         * Drzewo dla (2 + 3) * 4:
         *        Multiply
         *        /      \
         *      Add      Num(4)
         *     /   \
         *  Num(2) Num(3)
         */

        Expr expr = new Multiply(new Add(new Num(2), new Num(3)), new Num(4));
        System.out.println("Wynik (2 + 3) * 4 = " + eval(expr));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - switch pattern matching: `case Typ zmienna ->` dopasowuje typ
         *   i wiąże zmienną, tak jak instanceof, ale w formie switcha
         * - dla sealed typów pokrycie WSZYSTKICH podtypów sprawia, że
         *   `default` nie jest wymagany - kompilator dowodzi wyczerpania
         * - dodanie nowego podtypu do permits BEZ aktualizacji switcha
         *   = błąd kompilacji (bezpieczna refaktoryzacja)
         * - record patterns (`case Point(int x, int y) ->`) dekomponują
         *   pola rekordu bezpośrednio we wzorcu, bez wołania akcesorów
         * - record patterns można ZAGNIEŻDŻAĆ (rekord w rekordzie)
         * - `when` dodaje dodatkowy warunek (guard) - gałęzie z `when`
         *   muszą poprzedzać "gołą" gałąź tego samego typu
         * - `case null ->` pozwala jawnie obsłużyć null bez NPE
         * - sealed + record + switch pattern matching to potężne
         *   narzędzie do modelowania drzew wyrażeń / AST
         */
    }

    static String describe(Shape shape) {
        return switch (shape) {
            case Circle c -> "Koło o promieniu " + c.radius();
            case Rectangle r -> "Prostokąt " + r.width() + "x" + r.height();
            case Triangle t -> "Trójkąt o podstawie " + t.base();
            // BRAK default - Shape jest sealed z DOKŁADNIE trzema podtypami
        };
    }

    static void printPoint(Object obj) {
        // record pattern działa też z instanceof, nie tylko ze switchem
        if (obj instanceof Point(int x, int y)) {
            System.out.println("Punkt: x=" + x + ", y=" + y);
        }
    }

    static void printLineLength(Line line) {
        // zagnieżdżony record pattern - od razu wyciągamy x1,y1,x2,y2
        if (line instanceof Line(Point(int x1, int y1), Point(int x2, int y2))) {
            double length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            System.out.printf("Długość odcinka: %.2f%n", length);
        }
    }

    static String sizeCategory(Shape shape) {
        return switch (shape) {
            case Circle c when c.radius() > 10 -> "Duże koło";
            case Circle c -> "Małe koło";
            case Rectangle r when r.width() * r.height() > 50 -> "Duży prostokąt";
            case Rectangle r -> "Mały prostokąt";
            case Triangle t -> "Trójkąt";
        };
    }

    static String describeNullable(Shape shape) {
        return switch (shape) {
            case null -> "Brak kształtu (null)";
            case Circle c -> "Koło";
            case Rectangle r -> "Prostokąt";
            case Triangle t -> "Trójkąt";
        };
    }

    static double eval(Expr expr) {
        return switch (expr) {
            case Num(double value) -> value;
            case Add(Expr left, Expr right) -> eval(left) + eval(right);
            case Multiply(Expr left, Expr right) -> eval(left) * eval(right);
        };
    }
}

// ==================== SEALED HIERARCHIA KSZTAŁTÓW (recordy) ====================

sealed interface Shape permits Circle, Rectangle, Triangle {
}

record Circle(double radius) implements Shape {
}

record Rectangle(double width, double height) implements Shape {
}

record Triangle(double base, double height) implements Shape {
}

// ==================== PUNKTY I ODCINKI (record patterns) ====================

record Point(int x, int y) {
}

record Line(Point start, Point end) {
}

// ==================== DRZEWO WYRAŻEŃ ARYTMETYCZNYCH ====================

sealed interface Expr permits Num, Add, Multiply {
}

record Num(double value) implements Expr {
}

record Add(Expr left, Expr right) implements Expr {
}

record Multiply(Expr left, Expr right) implements Expr {
}
