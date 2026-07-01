package com.example.javaquest._02_oop.Lesson01_ClassesAndObjects;

public class _Exercises_Lesson01_ClassesAndObjects {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateClass {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Book z polami: title (String), author (String), pages (int).
         * Utwórz dwa obiekty Book i wypisz ich dane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_Constructor {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Rectangle z polami width i height (double).
         * Dodaj konstruktor przyjmujący obie wartości.
         * Utwórz obiekt i wypisz: "Rectangle: 5.0 x 3.0".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_Methods {
        /*
         * 🧪 Zadanie 3:
         * Dodaj do klasy Rectangle z zadania 2 metody:
         * - double area() – zwraca pole (width * height)
         * - double perimeter() – zwraca obwód (2 * (width + height))
         * Wypisz pole i obwód dla prostokąta 4.0 x 7.0.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_ThisKeyword {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę Student z polami name (String) i grade (int).
         * Konstruktor ma parametry o tych samych nazwach co pola.
         * Użyj `this.name = name` w konstruktorze.
         * Dodaj metodę introduce() wypisującą "Jestem [name], ocena: [grade]".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_StaticField {
        /*
         * 🧪 Zadanie 5:
         * Stwórz klasę Counter z polem statycznym count.
         * Każde wywołanie konstruktora Counter() zwiększa count o 1.
         * Utwórz 5 obiektów Counter i wypisz Counter.count.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_MultipleObjects {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę Circle z polem radius (double).
         * Utwórz tablicę 3 okręgów o promieniach: 1.0, 2.5, 5.0.
         * Wypisz pole każdego okręgu (Math.PI * r * r).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_DefaultValues {
        /*
         * 🧪 Zadanie 7:
         * Stwórz klasę Sensor z polami: name (String), value (double), active (boolean).
         * Utwórz obiekt Sensor bez konstruktora (użyj domyślnego).
         * Wypisz wartości domyślne każdego pola.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_VoidMethod {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę Lamp z polem boolean isOn.
         * Dodaj metody: turnOn(), turnOff(), printStatus().
         * Wywołaj sekwencję: turnOn → status → turnOff → status.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ReturnMethod {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę BankAccount z polem balance (double).
         * Dodaj metody deposit(double), withdraw(double), getBalance().
         * Wpłać 500, wypłać 200 i wypisz saldo.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_StaticMethod {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę MathHelper z metodami statycznymi:
         * - static int square(int n) – n*n
         * - static boolean isPrime(int n) – sprawdza czy n jest pierwsze
         * Przetestuj: square(7) i isPrime(13).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_OverloadedConstructors {
        /*
         * 🧪 Zadanie 11:
         * Stwórz klasę Email z polami: to, subject, body (String).
         * Dodaj 3 konstruktory:
         * - Email(String to)
         * - Email(String to, String subject)
         * - Email(String to, String subject, String body)
         * Wypisz dane każdego emaila.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_ObjectArray {
        /*
         * 🧪 Zadanie 12:
         * Stwórz klasę Product z polami name (String) i price (double).
         * Utwórz tablicę 5 produktów. Znajdź i wypisz najdroższy produkt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_MethodChaining {
        /*
         * 🧪 Zadanie 13:
         * Stwórz klasę StringBuilder-like: TextBuilder z polem text (String).
         * Metody append(String) i prepend(String) mają zwracać `this` (TextBuilder).
         * Użyj łańcuchowania: builder.prepend(">>").append("<<").append("!").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_StaticCounter {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę Connection z polem statycznym activeConnections.
         * connect() zwiększa licznik, disconnect() zmniejsza (min 0).
         * Symuluj 4 połączenia i 2 rozłączenia. Wypisz stan po każdej operacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_ClassWithLogic {
        /*
         * 🧪 Zadanie 15:
         * Stwórz klasę Temperature z polem celsius (double).
         * Dodaj metody:
         * - toFahrenheit() → celsius * 9/5 + 32
         * - toKelvin() → celsius + 273.15
         * - String getCategory() → "Mróz" < 0, "Chłód" < 15, "Ciepło" < 25, "Upał" inaczej
         * Przetestuj dla: -5, 10, 20, 35 stopni.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_ObjectComparison {
        /*
         * 🧪 Zadanie 16:
         * Stwórz klasę Version z polami major, minor, patch (int).
         * Dodaj metodę isNewerThan(Version other) zwracającą boolean.
         * Porównaj wersje: 1.2.3 vs 1.2.4, 2.0.0 vs 1.9.9.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_Swap {
        /*
         * 🧪 Zadanie 17:
         * Stwórz klasę Pair z polami first i second (int).
         * Dodaj metodę swap() zamieniającą wartości miejscami.
         * Wypisz parę przed i po swap().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Fibonacci {
        /*
         * 🧪 Zadanie 18:
         * Stwórz klasę FibSequence z polami a i b (long).
         * Konstruktor: FibSequence() inicjuje a=0, b=1.
         * Metoda next() przesuwa sekwencję i zwraca nową wartość.
         * Wypisz pierwsze 10 liczb Fibonacciego używając obiektu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_StudentGrades {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasę Student z polami name (String) i grades (double[]).
         * Dodaj metody:
         * - double average() – średnia ocen
         * - double max() – najwyższa ocena
         * - double min() – najniższa ocena
         * Przetestuj dla studenta z ocenami {4.0, 3.5, 5.0, 4.5, 3.0}.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ShoppingCart {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę ShoppingCart z polem items (String[]) i itemCount.
         * Metody: addItem(String), removeLastItem(), double totalItems().
         * Dodaj 5 produktów, usuń 2, wypisz stan koszyka.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_Stack {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj klasę Stack (LIFO) z polami: int[] data, int top.
         * Metody: push(int), int pop(), int peek(), boolean isEmpty(), int size().
         * Przetestuj: push 5 elementów, pop 2, peek, sprawdź rozmiar.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_Queue {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj klasę Queue (FIFO) z polami: int[] data, int head, int tail.
         * Metody: enqueue(int), int dequeue(), int peek(), boolean isEmpty().
         * Przetestuj kolejkę dla sekwencji: enqueue 1,2,3, dequeue, enqueue 4, dequeue.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Matrix {
        /*
         * 🧪 Zadanie 23:
         * Stwórz klasę Matrix2x2 z polem double[][] data.
         * Metody:
         * - double determinant() → a*d - b*c
         * - Matrix2x2 multiply(Matrix2x2 other) → mnożenie macierzy
         * - void print()
         * Wypisz macierz i jej wyznacznik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_LinkedList {
        /*
         * 🧪 Zadanie 24:
         * Stwórz klasy Node (z polem int value, Node next)
         * i SimpleLinkedList z polem Node head.
         * Metody: add(int), void printAll(), int size().
         * Dodaj liczby 1-5 i wypisz całą listę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_FluentBuilder {
        /*
         * 🧪 Zadanie 25:
         * Stwórz klasę Pizza z polami: size (String), crust (String),
         * toppings (String[]), int toppingCount.
         * Dodaj Fluent API: setSize(...), setCrust(...), addTopping(...)
         * – każda metoda zwraca this.
         * Zbuduj pizzę: size=Large, crust=thin, toppings: cheese, mushrooms, pepperoni.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ImmutableClass {
        /*
         * 🧪 Zadanie 26:
         * Stwórz niezmienną klasę Money z polami final long cents i final String currency.
         * Nie dodawaj setterów.
         * Dodaj metody:
         * - Money add(Money other) – zwraca NOWY obiekt z sumą
         * - Money multiply(int factor) – zwraca NOWY obiekt
         * Przetestuj: 5.00 PLN + 3.50 PLN = 8.50 PLN.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_StaticFactory {
        /*
         * 🧪 Zadanie 27:
         * Stwórz klasę Color z prywatnymi polami int r, g, b.
         * Prywatny konstruktor.
         * Statyczne fabryczne metody:
         * - Color ofRGB(int r, int g, int b) – walidacja 0-255
         * - Color ofHex(String hex) – parsuje "#RRGGBB"
         * - Color red() / green() / blue() – predefiniowane kolory
         * Wypisz każdy kolor w formacie "Color(r=X, g=Y, b=Z)".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_ObjectPool {
        /*
         * 🧪 Zadanie 28:
         * Stwórz klasę ConnectionPool zarządzającą pulą 3 połączeń (DBConnection[]).
         * Metody: DBConnection acquire() – zwraca wolne połączenie lub null,
         * release(DBConnection) – zwalnia połączenie.
         * Symuluj akwizycję 3 połączeń, próbę 4-tego (null), zwolnienie 1 i ponowną akwizycję.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_EventSystem {
        /*
         * 🧪 Zadanie 29:
         * Stwórz klasę EventLog przechowującą zdarzenia (String[]).
         * Metody: log(String event), void printAll(), void clear(), int count().
         * Klasa powinna być Singleton (jedna instancja dla całej aplikacji).
         * Użyj getInstance() z dwóch różnych miejsc i sprawdź, czy to ten sam obiekt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_Calculator {
        /*
         * 🧪 Zadanie 30:
         * Stwórz klasę ScientificCalculator z:
         * - polem history (String[]) przechowującym ostatnie 5 operacji
         * - metodą oblicz(double a, String op, double b) wykonującą +,-,*,/,^,%
         * - metodą printHistory() wypisującą historię
         * - statyczną metodą factorial(int n)
         * Wykonaj 7 obliczeń (historia "rotuje" – tylko ostatnie 5), wypisz historię.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
