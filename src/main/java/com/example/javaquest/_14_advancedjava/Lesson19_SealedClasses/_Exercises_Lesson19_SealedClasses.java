package com.example.javaquest._14_advancedjava.Lesson19_SealedClasses;

public class _Exercises_Lesson19_SealedClasses {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicSealedInterface {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj sealed interface Vehicle permits Car, Truck, Motorcycle
         * z metodą int wheelCount(). Każda z trzech klas niech będzie final
         * i zwraca odpowiednio 4, 6 i 2. Utwórz po jednej instancji każdej
         * i wypisz liczbę kół.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FinalSubtypeCannotBeExtended {
        /*
         * 🧪 Zadanie 2:
         * Weź klasę Circle z lekcji (final class Circle implements Shape).
         * W KOMENTARZU zapisz próbę napisania `class SuperCircle extends
         * Circle {}` i dokładny komunikat błędu kompilacji, jaki by się
         * pojawił, ponieważ Circle jest final.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NonSealedSubtypeFreelyExtendable {
        /*
         * 🧪 Zadanie 3:
         * Klasa Square z lekcji jest non-sealed. Napisz DRUGĄ (obok
         * BigSquare) klasę TinySquare rozszerzającą Square, z dodatkowym
         * polem/metodą wg uznania. Utwórz instancję i wypisz jej pole
         * odziedziczone po Square.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SealedSubtypeWithOwnPermits {
        /*
         * 🧪 Zadanie 4:
         * Wzorując się na Triangle/EquilateralTriangle z lekcji, zdefiniuj
         * WŁASNĄ sealed klasę Polygon implements Shape permits
         * RegularPolygon, z jedną dozwoloną podklasą final RegularPolygon
         * (pola: liczba boków, długość boku; area() – dowolne uproszczone
         * wyliczenie). Utwórz instancję i wypisz pole.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_PermitsCompileErrorComment {
        /*
         * 🧪 Zadanie 5:
         * W KOMENTARZU napisz kod klasy `class Hexagon implements Shape {}`
         * (bez umieszczania jej w permits) i zapisz dokładny komunikat
         * błędu kompilacji, jaki zwróci kompilator (nazwa klasy, nazwa
         * sealed typu, wzmianka o permits).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SamePackageRequirementExplained {
        /*
         * 🧪 Zadanie 6:
         * W KOMENTARZU wyjaśnij (2-3 zdania), dlaczego klasy wymienione w
         * `permits` sealed typu muszą znajdować się w tym samym pakiecie
         * (lub module) co ten typ, i co by się stało, gdyby Java na to
         * pozwalała bez ograniczeń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SealedVsEnumChoiceOrderStatus {
        /*
         * 🧪 Zadanie 7:
         * Dla domeny "status zamówienia" (PENDING, PAID, CANCELLED – bez
         * dodatkowych danych per status) zdecyduj: enum czy sealed?
         * Uzasadnij w komentarzu, a następnie ZAIMPLEMENTUJ wybrane
         * rozwiązanie (jeśli enum – z metodą opisową; jeśli sealed – z
         * klasami) i przetestuj dla wszystkich wariantów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TotalAreaUsingExistingShapeHierarchy {
        /*
         * 🧪 Zadanie 8:
         * Użyj Shape/Circle/Square/EquilateralTriangle z lekcji. Napisz
         * metodę statyczną double totalArea(Shape[] shapes) sumującą pola
         * wszystkich kształtów. Przetestuj na tablicy złożonej z jednego
         * Circle, jednego Square i jednego EquilateralTriangle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TrafficLightNextState {
        /*
         * 🧪 Zadanie 9:
         * Użyj enuma TrafficLight z lekcji. Napisz metodę statyczną
         * TrafficLight next(TrafficLight current) zwracającą kolejny stan
         * w cyklu RED -> GREEN -> YELLOW -> RED. Wypisz pełny cykl 4 kroków
         * startując od RED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExtendOpenHierarchyFreely {
        /*
         * 🧪 Zadanie 10:
         * Użyj "otwartej" hierarchii OpenShape/OpenCircle z lekcji. Napisz
         * NOWĄ klasę OpenHexagon extends OpenShape (dowolne pole boku,
         * area() – uproszczony wzór). W komentarzu skontrastuj to z Shape:
         * dlaczego dopisanie OpenHexagon jest legalne, a analogiczne
         * dopisanie klasy do Shape byłoby błędem kompilacji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PaymentMethodSealedHierarchy {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj sealed interface PaymentMethod permits CreditCard,
         * PayPal, BankTransfer (final klasy z RÓŻNYMI polami: CreditCard –
         * numer karty i data ważności, PayPal – email, BankTransfer –
         * numer konta). Dodaj metodę String describe() w każdej klasie i
         * wypisz opis dla instancji wszystkich trzech.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ResultSuccessFailureHierarchy {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj generyczny sealed interface Result<T> permits Success,
         * Failure z final klasami Success<T> (pole T value) i Failure
         * (pole String errorMessage). Napisz metodę statyczną, która dla
         * dzielenia dwóch liczb zwraca Success lub Failure (dzielenie przez
         * zero), i obsłuż oba przypadki instanceof-em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AbstractSealedClassEmployee {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj abstract sealed class Employee permits Manager,
         * Developer z polem String name i abstrakcyjną metodą double
         * salary(). Zaimplementuj final Manager (bonus procentowy) i final
         * Developer (stawka godzinowa * liczba godzin). Wypisz pensje dla
         * przykładowej instancji każdej klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SealedInterfaceWithRecordImplementations {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj sealed interface Shape2 permits CirclePoint, SquarePoint
         * i zaimplementuj OBA warianty jako recordy (record CirclePoint
         * (double radius) implements Shape2 itd. – recordy są AUTOMATYCZNIE
         * final, więc nie trzeba tego pisać jawnie). W komentarzu potwierdź
         * ten fakt. Wypisz pole obu kształtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NonSealedReopensThenTwoBranches {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj trzypoziomową hierarchię: sealed interface LevelA permits
         * LevelB; non-sealed class LevelB implements LevelA. Ponieważ
         * LevelB jest non-sealed, napisz DWIE RÓŻNE, niepowiązane ze sobą
         * klasy LevelC1 extends LevelB oraz LevelC2 extends LevelB. Utwórz
         * po jednej instancji i wypisz ich klasę (getClass().getSimpleName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultiLevelSealedHierarchyLeafCounter {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj sealed interface Node permits Leaf, Branch, gdzie Branch
         * jest SAM sealed i ma WŁASNY permits: permits BranchTypeA,
         * BranchTypeB (obie final). Napisz metodę countLeafTypes() zwracającą
         * (na sztywno, na podstawie znajomości hierarchii) liczbę
         * "liściastych" typów w całej strukturze i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SealedInterfaceDefaultMethod {
        /*
         * 🧪 Zadanie 17:
         * Do sealed interface (np. PaymentMethod z Zadania 11 albo nowego)
         * dodaj metodę default String summary() wywołującą describe() i
         * doklejającą prefiks "[Płatność] ". Sprawdź, że WSZYSTKIE
         * implementacje dziedziczą summary() bez zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExtendBigSquareFurtherExplained {
        /*
         * 🧪 Zadanie 18:
         * BigSquare z lekcji rozszerza non-sealed Square, ale SAMA klasa
         * BigSquare nie ma modyfikatora final/sealed/non-sealed. W
         * komentarzu wyjaśnij, czy to znaczy, że BigSquare jest nadal
         * otwarta na dziedziczenie. Następnie napisz klasę EvenBiggerSquare
         * extends BigSquare, aby to POTWIERDZIĆ empirycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MixAllThreeOptionsInOnePermitsList {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj sealed interface Media permits Book, Ebook, Audiobook,
         * gdzie: Book jest final, Ebook jest non-sealed (i ma jedną klasę
         * pochodną InteractiveEbook), Audiobook jest sealed z własnym
         * permits ograniczonym do AudiobookMp3. Zbuduj po jednej instancji
         * każdego "liścia" hierarchii i wypisz ich nazwy klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorOpenHierarchyToSealed {
        /*
         * 🧪 Zadanie 20:
         * Napisz NAJPIERW zwykłą (otwartą) hierarchię: abstract class
         * Notification z podklasami EmailNotification i SmsNotification.
         * Następnie ZREFAKTORUJ ją na sealed: sealed abstract class
         * Notification permits EmailNotification, SmsNotification (obie
         * final). W komentarzu opisz, jaką korzyść daje ta zmiana.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SealedInterfaceWithGenerics {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj generyczny sealed interface Box<T> permits SingleBox,
         * MultiBox. SingleBox<T> (final) przechowuje jeden element T,
         * MultiBox<T> (final) przechowuje java.util.List<T>. Utwórz po
         * jednej instancji dla typu String i wypisz zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ExhaustivenessLimitationWithoutSwitchPreview {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę String describe(Shape shape) używającą ŁAŃCUCHA
         * if/else if z instanceof (Circle/Square/Triangle) BEZ switcha
         * (poznamy go w Lekcji 21). W komentarzu zaznacz, że mimo iż Shape
         * jest sealed, ten if/else-if ŁAŃCUCH i tak NIE jest sprawdzany
         * przez kompilator pod kątem kompletności – tę korzyść da dopiero
         * switch pattern matching.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PrivateConstructorControlledSealedHierarchy {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj sealed abstract class Currency permits Usd, Eur z
         * PRYWATNYM konstruktorem w Currency (żeby nawet w tym samym
         * pakiecie nikt nie mógł stworzyć "gołej" Currency, tylko przez
         * final podklasy Usd/Eur wywołujące super()). Utwórz po jednej
         * instancji i wypisz ich opis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_JsonAstSealedHierarchy {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj sealed interface JsonValue permits JsonString,
         * JsonNumber, JsonBoolean, JsonNull (wszystkie final recordy, np.
         * record JsonString(String value) implements JsonValue). Zbuduj
         * tablicę JsonValue[] z przykładami wszystkich czterech wariantów
         * i wypisz ich zawartość w pętli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ArithmeticExpressionTreeManualInstanceof {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj sealed interface Expr permits Num, Add, Multiply
         * (recordy: Num(double value), Add(Expr left, Expr right),
         * Multiply(Expr left, Expr right)). Zbuduj drzewo reprezentujące
         * (2 + 3) * 4 i napisz REKURENCYJNĄ metodę double eval(Expr expr)
         * używającą łańcucha instanceof + rzutowania (BEZ switcha – to
         * dopiero Lekcja 21). Wypisz wynik (spodziewane 20.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompilationErrorPermitsCrossPackage {
        /*
         * 🧪 Zadanie 26:
         * W KOMENTARZU opisz próbę umieszczenia w permits sealed interfejsu
         * klasy zdefiniowanej w INNYM pakiecie niż ten interfejs (np.
         * `sealed interface Foo permits com.example.other.Bar`). Zapisz
         * dokładny komunikat błędu kompilacji dotyczący naruszenia
         * wymogu tego samego modułu/pakietu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SealedInterfaceExtendingSealedInterface {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj łańcuch: sealed interface Base permits Mid; sealed
         * interface Mid extends Base permits Leaf1, Leaf2 (Leaf1 i Leaf2 –
         * final klasy implementujące Mid). Utwórz instancje Leaf1 i Leaf2,
         * przypisz je do zmiennych typu Base i wypisz ich klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_EnumVsSealedTradeoffCaseStudy {
        /*
         * 🧪 Zadanie 28:
         * Zamodeluj domenę "Vehicle" DWA razy: (a) jako enum VehicleType
         * { CAR, TRUCK } – bez dodatkowych danych per wariant, (b) jako
         * sealed hierarchię Car(int seats) i Truck(double payloadTons) (np.
         * recordy implementujące sealed interface Vehicle2). W obszernym
         * komentarzu uzasadnij, które podejście lepiej pasuje, gdy każdy
         * wariant potrzebuje WŁASNYCH, różnych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RecordPatternPreviewComment {
        /*
         * 🧪 Zadanie 29:
         * Bazując na drzewie Expr z Zadania 25, w KOMENTARZU (bez pisania
         * realnego, kompilującego się kodu – pattern matching poznamy w
         * Lekcji 21) naszkicuj, jak wyglądałaby metoda eval(Expr) napisana
         * za pomocą switcha z record patterns, np. `case Add(Expr l, Expr
         * r) -> eval(l) + eval(r)`. Porównaj czytelność z wersją
         * instanceof z Zadania 25.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneShapeCalculatorLibrary {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj kompletną mini-bibliotekę: sealed interface Shape3
         * permits Circle3, Rectangle3, Triangle3 (final recordy z polami
         * odpowiednimi dla każdego kształtu) oraz klasę pomocniczą
         * ShapeUtils z metodami statycznymi: double totalArea(List<Shape3>
         * shapes) i Shape3 largestShape(List<Shape3> shapes) (obie oparte
         * na łańcuchu instanceof, tak jak w Lekcji 19 – bez switcha).
         * Przetestuj na liście złożonej z 5 różnych kształtów, wypisując
         * sumaryczne pole i opis największego kształtu.
         */
        public static void main(String[] args) { }
    }
}
