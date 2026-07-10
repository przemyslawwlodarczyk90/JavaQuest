package com.example.javaquest._16_clean_code.Lesson09_LiskovSubstitutionPrinciple;

import java.util.List;

public class _Lesson09_LiskovSubstitutionPrinciple {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: LISKOV SUBSTITUTION PRINCIPLE (LSP) ===");

        /*
         * ============================================================
         * 🔹 DEFINICJA LSP
         * ============================================================
         * - Sformulowanie Barbary Liskov (1987): "Jesli S jest podtypem T,
         *   to obiekty typu T powinny dac sie zastapic obiektami typu S BEZ
         *   zmiany poprawnosci dzialania programu".
         * - W praktyce: kazde miejsce w kodzie, ktore przyjmuje/uzywa typu
         *   bazowego (np. metoda `void test(Rectangle r)`), MUSI dzialac
         *   poprawnie, gdy podstawimy dowolny podtyp (np. Square) - BEZ
         *   sprawdzania "czy to przypadkiem nie jest Square" (`instanceof`).
         * - LSP to zasada o BEHAWIORZE, nie tylko o strukturze/typach.
         *   Dziedziczenie w Javie gwarantuje zgodnosc STRUKTURALNA (Square
         *   ma wszystkie metody Rectangle), ale NIE gwarantuje zgodnosci
         *   BEHAWIORALNEJ (czy Square "zachowuje sie" tak, jak oczekuje tego
         *   kod uzywajacy Rectangle).
         */
        System.out.println("LSP: podtyp musi byc podstawialny za typ bazowy BEZ zmiany poprawnosci dzialania programu.");

        /*
         * ============================================================
         * 🔹 KLASYCZNY PRZYKLAD NARUSZENIA: KWADRAT DZIEDZICZACY PO PROSTOKACIE
         * ============================================================
         * - Matematycznie kazdy kwadrat JEST prostokatem (ma 4 katy proste,
         *   boki rowne) - kuszace wydaje sie wiec `class Square extends
         *   Rectangle`.
         * - Rectangle ma niezalezne setWidth()/setHeight(). Square, aby
         *   zachowac WLASNY niezmiennik (bok = bok), musi PRZESLONIC obie
         *   metody tak, by zmiana szerokosci zmienialla TEZ wysokosc (i
         *   odwrotnie).
         * - To wlasnie LAMIE oczekiwania kodu napisanego dla Rectangle: kto
         *   woluje setWidth(5) spodziewa sie, ze WYSOKOSC POZOSTANIE BEZ
         *   ZMIAN (tak dziala kazdy "normalny" prostokat) - a dla
         *   podstawionego Square wysokosc TEZ sie zmieni.
         */
        demonstrateLspViolation();

        /*
         * ============================================================
         * 🔍 DOWOD NA ZYWO: TEST NIEZMIENNIKA PROSTOKATA
         * ============================================================
         * - Ponizej realny test: ustawiamy szerokosc=5, wysokosc=4, i
         *   sprawdzamy, czy area() == 20 (oczekiwanie kazdego kodu
         *   uzywajacego Rectangle).
         * - Dla BadRectangle test PRZECHODZI. Dla BadSquare (podstawionego
         *   w miejsce Rectangle - zgodnie z LSP powinno to byc bezpieczne)
         *   test NIE PRZECHODZI - poniewaz setWidth(5) w Square zmienia
         *   TEZ wysokosc na 5, wiec area() = 25, nie 20.
         */
        demonstrateInvariantTestFailure();

        /*
         * ============================================================
         * 🔍 DLACZEGO TO PROBLEM W PRAKTYCE
         * ============================================================
         * - Kazdy kod pisany "dla Rectangle" musialby wiedziec o istnieniu
         *   Square i traktowac go inaczej (np. przez `if (r instanceof Square)`)
         *   - to dokladnie ZAPRZECZA idei polimorfizmu (po to uzywamy typu
         *     bazowego, zeby NIE musiec sprawdzac konkretnego podtypu).
         * - Ukryte bledy: kod dzialajacy poprawnie przez miesiace nagle
         *   "psuje sie" w momencie, gdy ktos przekazal Square zamiast
         *   Rectangle - trudne do namierzenia, bo kompilator NIC nie
         *   zasygnalizuje (Square jest STRUKTURALNIE poprawnym Rectangle).
         * - Sygnal ostrzegawczy: podklasa PRZESLANIA metode tak, by zrobic
         *   "mniej" lub "inaczej" niz sugeruje kontrakt klasy bazowej, albo
         *   dorzuca dodatkowe warunki/wyjatki, ktorych klasa bazowa nie mial.
         */
        System.out.println("\nProblem: kod dla Rectangle musialby znac Square (instanceof) - zaprzecza to idei polimorfizmu.");

        /*
         * ============================================================
         * 📌 REFAKTORYZACJA: WSPOLNY INTERFEJS ZAMIAST NIEZGODNEGO DZIEDZICZENIA
         * ============================================================
         * - Zamiast wymuszac relacje "Square JEST Rectangle" (ktora jest
         *   prawdziwa geometrycznie, ale FALSZYWA behawioralnie w API z
         *   mutowalnymi setterami), wprowadzamy wspolny interfejs Shape
         *   z metoda area().
         * - Rectangle i Square implementuja Shape NIEZALEZNIE OD SIEBIE -
         *   zaden z nich nie musi udawac drugiego ani przeslaniac cudzych
         *   setterow.
         * - Kazda klasa ma WLASNY, spojny niezmiennik: Rectangle ma
         *   NIEZALEZNE szerokosc/wysokosc, Square ma JEDEN bok - i obie
         *   poprawnie licza area() zgodnie z WLASNA definicja.
         */
        demonstrateGoodExample();

        /*
         * ============================================================
         * 🔍 PROSTY "TEST LSP" DO STOSOWANIA W PRAKTYCE
         * ============================================================
         * - Pytanie kontrolne: "Czy moge podstawic dowolny podtyp w miejsce
         *   typu bazowego, NIE dodajac przy tym zadnego `instanceof` ani
         *   warunku specjalnego dla tego podtypu?"
         * - Jesli odpowiedz brzmi "nie" - najprawdopodobniej dziedziczenie
         *   zostalo uzyte tam, gdzie lepszy bylby wspolny interfejs (jak w
         *   przykladzie Shape powyzej) albo kompozycja zamiast dziedziczenia.
         * - Inne klasyczne sygnaly naruszenia LSP: podklasa rzuca WYJATEK w
         *   metodzie, ktora w klasie bazowej nigdy nie rzucala (zobaczysz to
         *   ponownie w Lesson10 - Interface Segregation Principle, na
         *   przykladzie RobotWorker.eat()).
         */
        System.out.println("\nTest LSP: 'czy podstawiam podtyp BEZ instanceof/warunkow specjalnych?' - jesli nie, to smog dziedziczenia.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - LSP: podtyp musi zachowywac sie zgodnie z OCZEKIWANIAMI wobec
         *   typu bazowego, nie tylko miec te sama STRUKTURE (metody).
         * - Klasyczny przyklad naruszenia: Square extends Rectangle (psuje
         *   niezmiennik "zmiana szerokosci nie zmienia wysokosci").
         * - Lekarstwo: gdy dziedziczenie wymusza "przeslanianie" zachowania
         *   w sposob niezgodny z oczekiwaniami klasy bazowej - zastap je
         *   wspolnym interfejsem lub kompozycja.
         * - W kolejnej lekcji (Lesson10): Interface Segregation Principle -
         *   dlaczego male, wyspecjalizowane interfejsy sa lepsze niz jeden
         *   "tlusty" interfejs.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateLspViolation() {
        System.out.println("\n=== NARUSZENIE LSP: Square extends Rectangle ===");

        BadRectangle rectangle = new BadRectangle(2, 3);
        System.out.println("BadRectangle: szerokosc=2, wysokosc=3, area()=" + rectangle.area());

        BadRectangle square = new BadSquare(4); // Square PODSTAWIONY jako Rectangle
        System.out.println("BadSquare (jako BadRectangle): bok=4, area()=" + square.area());

        square.setWidth(10);
        System.out.println("Po square.setWidth(10) (Square 'udaje' Rectangle):");
        System.out.println(" szerokosc=" + square.getWidth() + ", wysokosc=" + square.getHeight()
                + " <- wysokosc TEZ sie zmienila! Prawdziwy prostokat by tak nie zrobil.");
    }

    private static void demonstrateInvariantTestFailure() {
        System.out.println("\n=== TEST NIEZMIENNIKA: setWidth(5); setHeight(4); oczekiwane area()==20 ===");

        BadRectangle realRectangle = new BadRectangle(1, 1);
        testRectangleInvariant("BadRectangle", realRectangle);

        BadRectangle squareAsRectangle = new BadSquare(1);
        testRectangleInvariant("BadSquare (podstawiony jako BadRectangle)", squareAsRectangle);
    }

    /**
     * Test symulujacy kod NAPISANY DLA Rectangle - nie wie nic o Square,
     * po prostu ustawia szerokosc i wysokosc, i oczekuje area() = 5 * 4 = 20.
     * Zgodnie z LSP, kazdy podtyp Rectangle powinien ten test przejsc.
     */
    private static void testRectangleInvariant(String label, BadRectangle rectangle) {
        rectangle.setWidth(5);
        rectangle.setHeight(4);
        int expectedArea = 20;
        int actualArea = rectangle.area();

        if (actualArea == expectedArea) {
            System.out.println(" [PASS] " + label + ": area()=" + actualArea + " (zgodne z oczekiwaniem " + expectedArea + ")");
        } else {
            System.out.println(" [FAIL] " + label + ": area()=" + actualArea + ", oczekiwano " + expectedArea
                    + " -> NARUSZENIE LSP wykryte!");
        }
    }

    private static void demonstrateGoodExample() {
        System.out.println("\n=== DOBRY PRZYKLAD: wspolny interfejs Shape, bez niezgodnego dziedziczenia ===");

        List<Shape> shapes = List.of(
                new Rectangle(5, 4),
                new Square(5)
        );

        for (Shape shape : shapes) {
            System.out.println(" " + shape.getClass().getSimpleName() + ".area() = " + shape.area()
                    + " (wlasny, spojny niezmiennik - bez udawania czegos innego)");
        }
        System.out.println("Rectangle i Square NIE dziedzicza jeden po drugim - kazdy ma WLASNA, poprawna logike.");
    }

    /**
     * ZLY PRZYKLAD - klasyczny "prostokat" z niezaleznymi setterami
     * szerokosci i wysokosci. Wyglada niewinnie w izolacji - problem
     * pojawia sie dopiero przy podklasie BadSquare.
     */
    static class BadRectangle {
        private int width;
        private int height;

        BadRectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        void setWidth(int width) {
            this.width = width;
        }

        void setHeight(int height) {
            this.height = height;
        }

        int getWidth() {
            return width;
        }

        int getHeight() {
            return height;
        }

        int area() {
            return width * height;
        }
    }

    /**
     * ZLY PRZYKLAD - Square "dziedziczy" po Rectangle, ale musi PRZESLONIC
     * oba settery, zeby zachowac wlasny niezmiennik (bok==bok). To wlasnie
     * lamie LSP - kod napisany dla Rectangle nie oczekuje, ze setWidth()
     * zmieni TEZ wysokosc.
     */
    static class BadSquare extends BadRectangle {
        BadSquare(int side) {
            super(side, side);
        }

        @Override
        void setWidth(int width) {
            super.setWidth(width);
            super.setHeight(width); // efekt uboczny, ktorego Rectangle nie ma
        }

        @Override
        void setHeight(int height) {
            super.setWidth(height); // efekt uboczny, ktorego Rectangle nie ma
            super.setHeight(height);
        }
    }

    /**
     * DOBRY PRZYKLAD - wspolny interfejs. Kazda figura implementuje go
     * NIEZALEZNIE, wedlug WLASNEJ definicji pola.
     */
    interface Shape {
        int area();
    }

    static class Rectangle implements Shape {
        private final int width;
        private final int height;

        Rectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public int area() {
            return width * height;
        }
    }

    static class Square implements Shape {
        private final int side;

        Square(int side) {
            this.side = side;
        }

        @Override
        public int area() {
            return side * side;
        }
    }
}
