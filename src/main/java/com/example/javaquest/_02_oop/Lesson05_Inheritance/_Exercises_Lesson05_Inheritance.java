package com.example.javaquest._02_oop.Lesson05_Inheritance;

public class _Exercises_Lesson05_Inheritance {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicExtends {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Animal z polem name (String) i metodą eat().
         * Stwórz klasę Dog extends Animal z metodą bark().
         * Utwórz psa, wywołaj eat() (odziedziczone) i bark() (własne).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_SuperConstructor {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Vehicle z polami brand (String) i speed (int) i konstruktorem.
         * Stwórz klasę Car extends Vehicle z dodatkowym polem doors (int).
         * Konstruktor Car używa super(brand, speed).
         * Wypisz wszystkie dane samochodu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_Override {
        /*
         * 🧪 Zadanie 3:
         * Stwórz klasę Shape z metodą describe() wypisującą "To jest kształt".
         * Stwórz Circle extends Shape, który nadpisuje describe() → "To jest koło".
         * Stwórz Rectangle extends Shape, który nadpisuje describe() → "To jest prostokąt".
         * Wypisz opis każdego kształtu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_SuperMethod {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę Person z metodą introduce() wypisującą "Jestem [name]".
         * Stwórz Employee extends Person z polem company.
         * Employee.introduce() najpierw wywołuje super.introduce(), potem wypisuje "Pracuję w [company]".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_IsA {
        /*
         * 🧪 Zadanie 5:
         * Stwórz hierarchię: Animal → Mammal → Dog.
         * Każda klasa dodaje jedno pole i jedną metodę.
         * Utwórz obiekt Dog i sprawdź instanceof na każdym poziomie hierarchii.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_ProtectedField {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę BankAccount z protected polem balance (double).
         * Stwórz SavingsAccount extends BankAccount.
         * SavingsAccount ma pole interestRate i metodę addInterest()
         * która bezpośrednio modyfikuje balance (z protected dostępu).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_MultiLevel {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj łańcuch dziedziczenia: A → B → C.
         * A: pole x=1, metoda print() wypisuje x.
         * B: pole y=2, nadpisuje print() → super.print() + y.
         * C: pole z=3, nadpisuje print() → super.print() + z.
         * Utwórz C i wywołaj print() – powinno wypisać x, y, z.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_Upcast {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasy Animal, Dog extends Animal, Cat extends Animal.
         * Stwórz tablicę Animal[] i umieść w niej psa i kota (upcasting).
         * Wywołaj metodę makeSound() na każdym elemencie tablicy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_InheritedMethods {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę Calculator z metodami add(int,int), subtract(int,int), multiply(int,int).
         * Stwórz ScientificCalculator extends Calculator, dodając metody:
         * power(double base, double exp) i sqrt(double n).
         * Przetestuj metody z obu klas na obiekcie ScientificCalculator.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_FinalMethod {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę Template z metodą final void run() wywołującą step1(), step2(), step3().
         * step1(), step2(), step3() są nadpisywalne.
         * Stwórz podklasę, która nadpisuje step2() ale nie może nadpisać run().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConstructorChain {
        /*
         * 🧪 Zadanie 11:
         * Stwórz hierarchię:
         * - Vehicle(String type)
         * - Car extends Vehicle (dodaje brand, model)
         * - ElectricCar extends Car (dodaje rangeKm)
         * Każdy konstruktor używa super(...).
         * Utwórz ElectricCar i wypisz wszystkie dane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_OverrideAll {
        /*
         * 🧪 Zadanie 12:
         * Stwórz klasę Sorter z metodami:
         * - void sort(int[] arr) – bubble sort
         * - String getAlgorithmName() → "BubbleSort"
         * Stwórz SelectionSorter extends Sorter nadpisujący obie metody.
         * Porównaj sortowanie 5-elementowej tablicy przez oba algorytmy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_DownCast {
        /*
         * 🧪 Zadanie 13:
         * Stwórz hierarchię: Media → Movie (tytuł, czas), Podcast (odcinek, autor).
         * Utwórz tablicę Media[] z mieszanką.
         * Przeiteruj: sprawdź instanceof, rzutuj w dół i wywołaj specyficzną metodę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_Abstract {
        /*
         * 🧪 Zadanie 14:
         * Stwórz abstrakcyjną klasę Discount z metodą abstract double apply(double price).
         * Podklasy:
         * - PercentageDiscount (np. 20%)
         * - FixedDiscount (np. 10 PLN)
         * - BuyTwoGetOne (trzecia sztuka gratis – apply na cenę 1 sztuki)
         * Przetestuj każdy rabat na cenie 100 PLN.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_MixedHierarchy {
        /*
         * 🧪 Zadanie 15:
         * Stwórz hierarchię pracowników:
         * Employee(name, salary) → Manager extends Employee (bonus) → Director extends Manager (carAllowance)
         * Każda klasa ma metodę totalComp() zwracającą całkowite wynagrodzenie.
         * Manager.totalComp() = super.totalComp() + bonus.
         * Director.totalComp() = super.totalComp() + carAllowance.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_PolymorphicArray {
        /*
         * 🧪 Zadanie 16:
         * Stwórz 5 klas figur geometrycznych (all extend Shape):
         * Circle, Rectangle, Triangle, Pentagon, Hexagon.
         * Każda implementuje double area() i String name().
         * Stwórz tablicę Shape[], oblicz i wypisz pole każdej figury, posortuj malejąco.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_AbstractHooks {
        /*
         * 🧪 Zadanie 17:
         * Stwórz abstrakcyjną klasę Report z metodami:
         * - abstract String generateContent()
         * - void export(String format) – wypisuje nagłówek, content i stopkę
         * Podklasy: SalesReport, InventoryReport.
         * Wywołaj export("PDF") dla obu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_InheritanceVsComposition {
        /*
         * 🧪 Zadanie 18:
         * Porównaj dziedziczenie vs kompozycję:
         * Wariant A: Logger extends FileWriter (dziedziczenie)
         * Wariant B: Logger ma pole FileWriter writer (kompozycja)
         * Zaimplementuj oba warianty z metodą log(String).
         * W komentarzu napisz który jest lepszy i dlaczego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_Covariant {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasę Animal z metodą Animal create() zwracającą new Animal().
         * Stwórz Dog extends Animal z @Override Dog create() zwracającą new Dog().
         * (covariant return type – podklasa zwraca bardziej specyficzny typ)
         * Przetestuj wywołanie create() na referencji Animal i Dog.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Polymorphic {
        /*
         * 🧪 Zadanie 20:
         * Stwórz interfejs Drawable z metodą void draw().
         * Stwórz klasę UIComponent z metodami: void render(), String getType().
         * Stwórz Button extends UIComponent implements Drawable.
         * Button nadpisuje render() i draw().
         * Przetestuj zmienną Drawable d = new Button().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GameHierarchy {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj hierarchię gry RPG:
         * Character(name, hp, mp) → Warrior(attackPower) → Paladin(holyPower)
         *                         → Mage(spellPower) → Archmage(manaMultiplier)
         * Każda klasa ma metodę attack() i getStats().
         * Symuluj walkę: Paladin vs Archmage (3 rundy).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_DeepHierarchy {
        /*
         * 🧪 Zadanie 22:
         * Stwórz hierarchię pojazdów: Vehicle → LandVehicle → Car → SportsCar → RaceCar.
         * Każda klasa dodaje jedno pole specyficzne.
         * Wszystkie mają metodę describe() wywołującą super.describe() + własne dane.
         * Wywołaj describe() na obiekcie RaceCar.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_TemplateMethod {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj wzorzec Template Method:
         * Abstrakcyjna klasa DataProcessor z metodą final void process(int[] data)
         * wywołującą: filter(data) → transform(data) → output(data).
         * Podklasy:
         * - EvenFilter: filter usuwa nieparzyste, transform podwaja, output wypisuje
         * - PositiveFilter: filter usuwa ujemne, transform podnosi do kwadratu, output wypisuje
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_VisitorPrep {
        /*
         * 🧪 Zadanie 24:
         * Stwórz hierarchię: Expression → NumberExpr(value), AddExpr(left, right), MulExpr(left, right).
         * Każda klasa ma metodę evaluate() zwracającą wynik obliczeń.
         * NumberExpr.evaluate() → value.
         * AddExpr.evaluate() → left.evaluate() + right.evaluate().
         * Oblicz: (3 + 4) * (2 + 5).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_Overriding {
        /*
         * 🧪 Zadanie 25:
         * Stwórz klasę Animal z metodami: String sound(), String diet(), void describe().
         * describe() wypisuje: "[name] is [diet] and says [sound()]".
         * Podklasy: Lion, Rabbit, Eagle – każda nadpisuje sound() i diet() ale NIE describe().
         * Pokaż jak polimorfizm działa w describe().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_AbstractFactory {
        /*
         * 🧪 Zadanie 26:
         * Stwórz abstrakcyjną klasę UIFactory z metodami:
         * abstract Button createButton(), abstract TextField createTextField().
         * Podklasy: WindowsFactory, MacFactory, LinuxFactory.
         * Każda tworzy inne implementacje Button i TextField.
         * Wypisz jak renderują się kontrolki każdego systemu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_Mixins {
        /*
         * 🧪 Zadanie 27:
         * Symuluj "mixiny" przez dziedziczenie:
         * Flyable – abstract (fly())
         * Swimmable – abstract (swim())
         * Duck extends Flyable + implementuje Swimmable (oba zachowania).
         * Penguin extends Swimmable – nie lata.
         * Sprawdź instanceof dla obu zwierząt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Composite {
        /*
         * 🧪 Zadanie 28:
         * Stwórz hierarchię Component → Leaf(name) i Composite(name, Component[]).
         * Obie klasy mają metodę void show(int indent).
         * Leaf.show wypisuje "  [name]" (z wcięciem).
         * Composite.show wypisuje siebie + wywołuje show na każdym dziecku.
         * Zbuduj i wypisz drzewo folderów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_Strategy {
        /*
         * 🧪 Zadanie 29:
         * Stwórz abstrakcyjną klasę SortAlgorithm z metodami:
         * abstract void sort(int[] arr), abstract String name().
         * Implementacje: BubbleSort, QuickSort (uproszczony), InsertionSort.
         * Klasa Sorter przyjmuje SortAlgorithm i deleguje sortowanie.
         * Porównaj wyniki dla tablicy {5,3,8,1,9,2,7,4,6}.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_ComplexHierarchy {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj system płatności:
         * PaymentMethod(abstract) → OnlinePayment → CreditCard, PayPal
         *                         → OfflinePayment → Cash, BankTransfer
         * Każda klasa implementuje:
         * - boolean process(double amount)
         * - String getTransactionId()
         * - double getFee(double amount)
         * Polimorficznie przetwórz listę płatności i wypisz podsumowanie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
