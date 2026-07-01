package com.example.javaquest._02_oop.Lesson06_Polymorphism;

public class _Exercises_Lesson06_Polymorphism {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DynamicDispatch {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Animal z metodą speak() → "...".
         * Dog extends Animal → "Hau!", Cat extends Animal → "Miau!".
         * Przechowaj psa i kota w tablicy Animal[] i wywołaj speak() polimorficznie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_Overloading {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Printer z metodami print():
         * - print(String text)
         * - print(int number)
         * - print(double number)
         * - print(boolean flag)
         * Wywołaj każdą wersję i sprawdź, która jest uruchamiana.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PolymorphicRef {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę Shape z metodą area() zwracającą 0.0.
         * Circle i Rectangle nadpisują area().
         * Przypisz Circle do zmiennej Shape i wywołaj area(). Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_Instanceof {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę Vehicle i podklasy Car, Truck, Motorcycle.
         * Utwórz tablicę Vehicle[] z mieszanką.
         * Przeiteruj i wypisz typ każdego pojazdu używając instanceof.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_PatternMatching {
        /*
         * 🧪 Zadanie 5:
         * Użyj pattern matching z instanceof (Java 16+):
         * Object[] objects = {42, "Hello", 3.14, true, new int[]{1,2,3}};
         * Przeiteruj, sprawdź typ i wywołaj odpowiednią operację:
         * int → *2, String → toUpperCase(), double → Math.round(), boolean → !b, int[] → length.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_OverloadingVsOverriding {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę Base z metodami:
         * - void process(int n)
         * - void process(String s)
         * - void process(int n, String s)
         * Stwórz Derived extends Base, nadpisując void process(int n).
         * Wywołaj wszystkie warianty na obiekcie Derived.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_ArrayOfInterface {
        /*
         * 🧪 Zadanie 7:
         * Stwórz interfejs Drawable z metodą void draw().
         * Klasy: Circle, Square, Triangle implementują Drawable.
         * Stwórz tablicę Drawable[] i wywołaj draw() na każdym elemencie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_MixedTypes {
        /*
         * 🧪 Zadanie 8:
         * Stwórz interfejs Serializable z metodą String serialize().
         * Klasy User, Product, Order implementują Serializable.
         * Utwórz tablicę Serializable[], serialize wszystkie, wypisz wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_CompileVsRuntime {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasy Parent i Child extends Parent.
         * Obie mają static void staticMethod() i void instanceMethod().
         * Przypisz new Child() do zmiennej Parent.
         * Wywołaj obie metody – wyjaśnij w komentarzu który wariant jest wywołany i dlaczego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_Dispatch {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę Logger z metodami:
         * - log(String msg)
         * - log(String msg, int level)
         * - log(Exception e)
         * Wywołaj każdą wersję. Który mechanizm wybiera wersję metody?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ShapeCalculator {
        /*
         * 🧪 Zadanie 11:
         * Stwórz 5 klas figur geometrycznych – wszystkie extend Shape z metodami area() i perimeter().
         * Stwórz metodę statyczną printStats(Shape[]) wypisującą pole i obwód każdej figury,
         * a na końcu: total area, largest shape, smallest shape.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_Sorting {
        /*
         * 🧪 Zadanie 12:
         * Stwórz interfejs Comparable z metodą int compareTo(Object other).
         * Klasa Student implementuje Comparable (porównuje po GPA).
         * Napisz metodę bubbleSort(Comparable[]) używającą compareTo.
         * Posortuj 5 studentów według ocen.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_CommandPolymorphism {
        /*
         * 🧪 Zadanie 13:
         * Stwórz interfejs Command z metodami execute() i undo().
         * Klasy: AddCommand, DeleteCommand, UpdateCommand.
         * Przechowaj historię Command[] i wykonaj execute() na wszystkich,
         * następnie undo() w odwrotnej kolejności.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_StrategyPolymorphism {
        /*
         * 🧪 Zadanie 14:
         * Stwórz interfejs TaxStrategy z metodą double calculate(double income).
         * Strategie: FlatTax (19%), ProgressiveTax (progi: 12% do 120000, 32% powyżej), NoTax.
         * Klasa Taxpayer ma pole TaxStrategy i metodę netIncome(double gross).
         * Przetestuj dla dochodów: 50000, 150000, 30000.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_Visitor {
        /*
         * 🧪 Zadanie 15:
         * Stwórz interfejs Visitor z metodami:
         * - visit(Circle c), visit(Rectangle r), visit(Triangle t)
         * Klasy geometryczne mają metodę accept(Visitor v).
         * Zaimplementuj AreaVisitor i PerimeterVisitor.
         * Odwiedź tablicę Shape[] każdym visitoreme.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_Heterogeneous {
        /*
         * 🧪 Zadanie 16:
         * Stwórz interfejs Task z metodami: void run(), int priority(), String name().
         * Klasy: DatabaseTask, EmailTask, LogTask, BackupTask.
         * Posortuj tablicę Task[] malejąco po priority() i uruchom.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_Narrowing {
        /*
         * 🧪 Zadanie 17:
         * Stwórz hierarchię: Content → TextContent(text), ImageContent(url), VideoContent(url, duration).
         * Utwórz tablicę Content[] z mieszanką.
         * Bezpiecznie rzutuj w dół używając instanceof + pattern matching.
         * Wywołaj metodę specyficzną dla każdego typu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Null {
        /*
         * 🧪 Zadanie 18:
         * Stwórz interfejs Validator z metodą validate(String input).
         * Klasy: NotNullValidator, LengthValidator(min,max), RegexValidator(pattern).
         * Stwórz tablicę Validator[] i uruchom walidację łańcuchowo.
         * Wypisz wynik każdej walidacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_EventHandler {
        /*
         * 🧪 Zadanie 19:
         * Stwórz interfejs EventHandler z metodą void handle(String event).
         * Klasy: ClickHandler, KeyHandler, MouseMoveHandler.
         * EventDispatcher przechowuje tablicę EventHandler[] i metodę dispatch(String).
         * Zarejestruj handlery i zdyspatchuj zdarzenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Duck {
        /*
         * 🧪 Zadanie 20:
         * Zasada "duck typing" w Javie przez interfejs:
         * Interfejs Quackable z metodą quack().
         * Klasy: RealDuck, RubberDuck, DuckCall, PersonDuck.
         * Każda implementuje quack() inaczej.
         * Symuluj "kacze zawody" – wypisz dźwięki wszystkich "kaczek".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_VirtualDispatch {
        /*
         * 🧪 Zadanie 21:
         * Stwórz hierarchię: A → B → C, każda nadpisuje methodA().
         * B.methodA() wywołuje super.methodA().
         * C.methodA() wywołuje super.methodA().
         * Przechowaj new C() w zmiennych A, B, C.
         * Wywołaj methodA() z każdej zmiennej – sprawdź wynik i wyjaśnij.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_PolyReturn {
        /*
         * 🧪 Zadanie 22:
         * Stwórz interfejs Builder<T> z metodami T build(), Builder<T> set(String key, String value).
         * Klasy UserBuilder i ProductBuilder implementują Builder<T>.
         * Przetestuj polimorficzne użycie przez zmienną Builder<?>.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Decorator {
        /*
         * 🧪 Zadanie 23:
         * Stwórz interfejs TextProcessor z metodą String process(String text).
         * Klasy:
         * - PlainText: zwraca text bez zmian
         * - UpperCaseDecorator: owija inny TextProcessor, wynik uppercase
         * - TrimDecorator: owija inny TextProcessor, wynik trim
         * - StarWrapDecorator: owija w gwiazdki "*** text ***"
         * Zbuduj łańcuch dekoratorów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_Interpreter {
        /*
         * 🧪 Zadanie 24:
         * Stwórz prostą maszynę stanów:
         * Interfejs State z metodami: void enter(), void exit(), State next().
         * Klasy: IdleState, RunningState, PausedState, StoppedState.
         * StateMachine przechowuje currentState i wywołuje przejścia.
         * Symuluj: start → pause → resume → stop.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_Chain {
        /*
         * 🧪 Zadanie 25:
         * Stwórz abstrakcyjną klasę Handler z polem Handler next.
         * Metoda handle(int request) – jeśli może obsłużyć, obsługuje, wpp deleguje do next.
         * Klasy: LowHandler (0-10), MidHandler (11-50), HighHandler (51+).
         * Przetestuj łańcuch dla requestów: 5, 25, 75.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_Proxy {
        /*
         * 🧪 Zadanie 26:
         * Stwórz interfejs ImageLoader z metodą void load(String url).
         * Klasa RealImageLoader ładuje "naprawdę" (wypisuje "Loading: url").
         * Klasa CachingProxy implementuje ImageLoader:
         * - jeśli url już w cache[] → wypisuje "[CACHE HIT] url"
         * - jeśli nie → wywołuje realLoader.load() i zapamiętuje
         * Przetestuj z powtarzającymi się URL-ami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_OverloadResolution {
        /*
         * 🧪 Zadanie 27:
         * Stwórz klasę Dispatch z metodami:
         * - void m(Object o)
         * - void m(String s)
         * - void m(Number n)
         * - void m(Integer i)
         * Przetestuj z: Object, String, Integer, Number, null.
         * Wyjaśnij w komentarzu reguły wyboru metody przy overloading.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Covariance {
        /*
         * 🧪 Zadanie 28:
         * Stwórz klasy Fruit → Apple, Banana, Mango.
         * Metoda static Fruit pickBest(Fruit[] fruits) – zwraca największy (losowy przykład).
         * Utwórz Apple[], przekaż do pickBest (covariant array).
         * Wyjaśnij w komentarzu na czym polega ArrayStoreException i jak go uniknąć.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_AbstractTemplate {
        /*
         * 🧪 Zadanie 29:
         * Stwórz abstrakcyjną klasę Game z metodami:
         * - abstract void initialize()
         * - abstract void startPlay()
         * - abstract void endPlay()
         * - final void play() wywołuje je po kolei
         * Podklasy: Chess, Football, Cricket.
         * Wywołaj play() dla każdej gry polimorficznie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_Benchmark {
        /*
         * 🧪 Zadanie 30:
         * Porównaj wydajność polimorficznego wywołania vs bezpośredniego:
         * Interfejs Calc z metodą int compute(int n).
         * Implementacja DirectCalc: oblicza n*n.
         * W pętli 1_000_000 iteracji zmierz czas wywołania przez:
         * a) DirectCalc directCalc = new DirectCalc()
         * b) Calc polymorphicCalc = new DirectCalc()
         * Wypisz czasy. Porównaj i skomentuj różnicę (JIT powinien je wyrównać).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
