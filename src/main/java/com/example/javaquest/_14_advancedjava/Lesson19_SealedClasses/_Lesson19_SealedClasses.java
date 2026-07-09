package com.example.javaquest._14_advancedjava.Lesson19_SealedClasses;

public class _Lesson19_SealedClasses {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔒 SEALED CLASSES (JDK 17) – PO CO NAM TO?
         * ============================================================
         * Zwykłe dziedziczenie w Javie jest z założenia OTWARTE:
         * jeśli klasa/interfejs nie jest `final`, to KAŻDY, w KAŻDYM
         * pakiecie (nawet w kodzie, którego jeszcze nie napisano),
         * może ją rozszerzyć.
         *
         * To świetne dla bibliotek (elastyczność), ale bywa problemem,
         * gdy chcemy zamodelować DOMKNIĘTY, znany z góry zbiór typów,
         * np.:
         * - kształt: Circle, Square, Triangle – i NIC WIĘCEJ
         * - wynik operacji: Success, Failure – i NIC WIĘCEJ
         * - węzeł drzewa wyrażeń: Number, Add, Multiply – i NIC WIĘCEJ
         *
         * Bez mechanizmu sealed kompilator nie ma jak sprawdzić,
         * czy obsłużyliśmy WSZYSTKIE możliwe podtypy – bo teoretycznie
         * zbiór podtypów jest nieskończony (ktoś może dopisać nowy).
         */

        // Przykład "otwartej" hierarchii – każdy może dopisać kolejny podtyp:
        OpenShape openCircle = new OpenCircle(5);
        System.out.println("Otwarta hierarchia, pole: " + openCircle.area());
        // Nic nie stoi na przeszkodzie, by w innym pliku ktoś napisał:
        // class OpenHexagon extends OpenShape { ... }
        // Kompilator o tym nie wie i nie potrafi wymusić obsługi takiego przypadku.

        /*
         * ============================================================
         * 📦 SEALED INTERFACE + permits – DOMYKAMY ZBIÓR TYPÓW
         * ============================================================
         * `sealed interface Shape permits Circle, Square, Triangle`
         * mówi kompilatorowi: "TYLKO te trzy klasy (i żadne inne)
         * mogą implementować Shape". Lista `permits` jest wiążąca
         * i sprawdzana w czasie kompilacji.
         *
         * Klasy z listy `permits` muszą znajdować się w tym samym
         * pakiecie (albo module) co typ sealed – kompilator musi mieć
         * możliwość zweryfikowania całej hierarchii naraz.
         */

        Shape[] shapes = {
                new Circle(5),
                new Square(4),
                new EquilateralTriangle(3)
        };

        for (Shape shape : shapes) {
            System.out.printf("%-20s: pole=%.2f%n",
                    shape.getClass().getSimpleName(), shape.area());
        }

        // PRZYKŁAD BŁĘDU KOMPILACJI (gdyby odkomentować, projekt się nie skompiluje):
        // class Hexagon implements Shape { public double area() { return 0; } }
        // BŁĄD: "class is not allowed to extend sealed interface Shape:
        //         (Hexagon is not listed in its permits clause)"

        /*
         * ============================================================
         * 🔹 TRZY OPCJE DLA BEZPOŚREDNIEGO PODTYPU SEALED
         * ============================================================
         * Każda klasa wymieniona w `permits` MUSI zadeklarować, jak
         * dalej traktuje własną otwartość na dziedziczenie – dokładnie
         * jedną z trzech opcji:
         *
         * 1. final       – koniec hierarchii, nikt więcej nie rozszerzy.
         *                  → `final class Circle implements Shape`
         *
         * 2. sealed       – nadal domknięta, ale z WŁASNĄ, jeszcze bardziej
         *                    zawężoną listą `permits`.
         *                  → `sealed class Triangle implements Shape
         *                        permits EquilateralTriangle`
         *
         * 3. non-sealed   – "otwiera" hierarchię z powrotem: od tego
         *                    miejsca w dół każdy może dziedziczyć normalnie,
         *                    jak z każdej zwykłej klasy.
         *                  → `non-sealed class Square implements Shape`
         *
         * Zobacz definicje Circle / Square / Triangle / EquilateralTriangle
         * poniżej w tym pliku – każda demonstruje inną opcję.
         */

        // Square jest non-sealed → można ją swobodnie rozszerzyć:
        Square regularSquare = new Square(4);
        BigSquare bigSquare = new BigSquare(10);
        System.out.println("Zwykły kwadrat, pole: " + regularSquare.area());
        System.out.println("BigSquare (rozszerza non-sealed Square), pole: " + bigSquare.area());

        // Triangle jest sealed → jedyny dozwolony podtyp to EquilateralTriangle,
        // wymieniony w jego WŁASNYM permits. Próba napisania np.
        // `class ScaleneTriangle extends Triangle {}` gdziekolwiek indziej
        // NIE skompiluje się.

        /*
         * ============================================================
         * 🆚 SEALED CLASSES vs ENUM
         * ============================================================
         * Oba mechanizmy "domykają" zbiór – ale domykają CO INNEGO:
         *
         * - enum      → domknięty zbiór KONKRETNYCH INSTANCJI tego samego typu.
         *               RED, YELLOW, GREEN to trzy obiekty jednej klasy TrafficLight,
         *               o identycznym zestawie pól i metod.
         *
         * - sealed    → domknięty zbiór RÓŻNYCH TYPÓW (klas/interfejsów),
         *               z których każdy może mieć WŁASNE, różne pola i logikę.
         *               Circle ma radius, Square ma side, Triangle ma base+height –
         *               to nie warianty jednej klasy, tylko osobne klasy.
         *
         * Reguła kciuka: gdy warianty różnią się TYLKO nazwą/etykietą → enum.
         * Gdy warianty niosą różne DANE i/lub różne ZACHOWANIE → sealed.
         */

        for (TrafficLight light : TrafficLight.values()) {
            System.out.println("Sygnalizator: " + light);
        }

        /*
         * ============================================================
         * 🆚 SEALED vs ZWYKŁE (NIEOGRANICZONE) DZIEDZICZENIE
         * ============================================================
         * | Cecha                          | zwykła klasa   | sealed          |
         * |---------------------------------|----------------|-----------------|
         * | Kto może dziedziczyć            | ktokolwiek     | tylko z permits |
         * | Zbiór podtypów znany kompilatorowi | NIE         | TAK             |
         * | Można "zamknąć" pojedynczą gałąź | nie dotyczy    | final           |
         * | Można "otworzyć" pojedynczą gałąź| zawsze otwarta | non-sealed      |
         *
         * sealed to świadomy wybór projektowy: "ta hierarchia jest
         * SKOŃCZONA i ZNANA – i chcę, żeby kompilator to dla mnie pilnował".
         */

        /*
         * ============================================================
         * 🔮 ZAPOWIEDŹ: SEALED + SWITCH PATTERN MATCHING (Lekcja 21)
         * ============================================================
         * Dzięki temu, że Shape jest sealed, kompilator DOKŁADNIE wie,
         * że istnieją tylko trzy podtypy: Circle, Square, Triangle.
         * W Lekcji 21 zobaczysz, że switch z pattern matching potrafi
         * to wykorzystać: jeśli obsłużysz wszystkie trzy przypadki,
         * `default` NIE JEST WYMAGANY – kompilator sam udowadnia
         * wyczerpanie (exhaustiveness). A jeśli ktoś doda w przyszłości
         * czwarty podtyp do `permits`, każdy taki switch PRZESTANIE
         * SIĘ KOMPILOWAĆ, dopóki nie dopiszesz nowego przypadku –
         * to potężna siatka bezpieczeństwa przy refaktoryzacji.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - sealed + permits domyka zbiór dozwolonych podtypów, znany
         *   w całości kompilatorowi już na etapie kompilacji
         * - każdy bezpośredni podtyp musi być: final, sealed (z własnym
         *   węższym permits) albo non-sealed (reotwiera hierarchię)
         * - enum domyka zbiór INSTANCJI, sealed domyka zbiór TYPÓW
         * - permits wymienione klasy muszą leżeć w tym samym
         *   pakiecie/module co typ sealed
         * - sealed przygotowuje grunt pod exhaustywny switch pattern
         *   matching (Lekcja 21) – kompilator pilnuje kompletności
         */
    }
}

// ==================== "OTWARTA" HIERARCHIA (dla porównania) ====================

abstract class OpenShape {
    abstract double area();
}

class OpenCircle extends OpenShape {
    double radius;

    OpenCircle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

// ==================== SEALED HIERARCHIA ====================

sealed interface Shape permits Circle, Square, Triangle {
    double area();
}

final class Circle implements Shape {
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

non-sealed class Square implements Shape {
    final double side;

    Square(double side) {
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }
}

// Square jest non-sealed, więc BigSquare może ją swobodnie rozszerzać,
// tak jak każdą zwykłą klasę:
class BigSquare extends Square {
    BigSquare(double side) {
        super(side);
    }
}

sealed class Triangle implements Shape permits EquilateralTriangle {
    final double base;
    final double height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}

final class EquilateralTriangle extends Triangle {
    EquilateralTriangle(double side) {
        super(side, side * Math.sqrt(3) / 2);
    }
}

// ==================== ENUM DLA PORÓWNANIA ====================

enum TrafficLight {
    RED, YELLOW, GREEN
}
