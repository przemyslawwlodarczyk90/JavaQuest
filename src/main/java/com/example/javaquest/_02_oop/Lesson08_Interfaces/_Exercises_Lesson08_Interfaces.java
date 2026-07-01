package com.example.javaquest._02_oop.Lesson08_Interfaces;

public class _Exercises_Lesson08_Interfaces {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicInterface {
        /*
         * 🧪 Zadanie 1:
         * Stwórz interfejs Printable z metodą void print().
         * Klasy: Document(content), Photo(filename) implementują Printable.
         * Utwórz tablicę Printable[] i wywołaj print() na każdym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_MultipleInterfaces {
        /*
         * 🧪 Zadanie 2:
         * Interfejsy: Flyable (fly()), Swimmable (swim()), Runnable (run()).
         * Klasy: Duck implements Flyable, Swimmable. Fish implements Swimmable. Cheetah implements Runnable.
         * Przetestuj każde zwierzę wywołując dostępne metody.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_InterfaceType {
        /*
         * 🧪 Zadanie 3:
         * Stwórz interfejs Greetable z metodą String greet(String name).
         * Klasy: FormalGreeting, CasualGreeting, EnglishGreeting.
         * Użyj zmiennej typu interfejsu: Greetable g = new FormalGreeting();
         * Przetestuj każdą implementację przez zmienną interfejsową.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_InterfaceConstant {
        /*
         * 🧪 Zadanie 4:
         * Stwórz interfejs MathConstants z stałymi:
         * double PI = 3.14159265, double E = 2.71828, int MAX_INT = 2147483647.
         * Klasa Calculator implementuje MathConstants i używa tych stałych w metodach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_Comparable {
        /*
         * 🧪 Zadanie 5:
         * Stwórz interfejs Comparable z metodą int compareTo(Object other).
         * Klasa Temperature implementuje Comparable.
         * Stwórz 5 temperatur i znajdź najwyższą używając compareTo().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_DefaultMethod {
        /*
         * 🧪 Zadanie 6:
         * Stwórz interfejs Logger z:
         * - void log(String msg)  (abstract)
         * - default void logError(String msg) { log("[ERROR] " + msg); }
         * - default void logInfo(String msg) { log("[INFO] " + msg); }
         * Klasy: ConsoleLogger, SilentLogger (puste log()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_StaticInterfaceMethod {
        /*
         * 🧪 Zadanie 7:
         * Stwórz interfejs Validator z:
         * - boolean validate(String input)  (abstract)
         * - static Validator notEmpty() { return s -> !s.isEmpty(); }
         * - static Validator minLength(int n) { return s -> s.length() >= n; }
         * Przetestuj obie fabryczne metody statyczne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_FunctionalInterface {
        /*
         * 🧪 Zadanie 8:
         * Stwórz @FunctionalInterface Operation z metodą int apply(int a, int b).
         * Utwórz przez lambdy: add, subtract, multiply, divide.
         * Wywołaj każdą operację na parze (10, 3).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ImplementInterface {
        /*
         * 🧪 Zadanie 9:
         * Stwórz interfejs Shape z metodami: double area(), double perimeter(), String color().
         * Klasy: GreenCircle(radius), BlueRectangle(w,h), RedTriangle(base,height).
         * Wypisz kolor, pole i obwód każdego kształtu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_Callback {
        /*
         * 🧪 Zadanie 10:
         * Stwórz @FunctionalInterface Callback z metodą void onComplete(String result).
         * Metoda processData(String data, Callback callback) przetwarza dane i wywołuje callback.
         * Przetestuj z lambdą: result -> System.out.println("Wynik: " + result).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InterfaceChain {
        /*
         * 🧪 Zadanie 11:
         * Interfejsy: A (methodA()), B extends A (methodB()), C extends B (methodC()).
         * Klasa Impl implements C.
         * Sprawdź instanceof dla zmiennej Impl z każdym typem interfejsu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_DefaultConflict {
        /*
         * 🧪 Zadanie 12:
         * Interfejsy A i B, oba z default String greet() → "Cześć od A" / "Cześć od B".
         * Klasa AB implements A, B musi rozwiązać konflikt nadpisując greet().
         * Wywołaj A.super.greet() i B.super.greet() wewnątrz nadpisanej metody.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_Strategy {
        /*
         * 🧪 Zadanie 13:
         * @FunctionalInterface SortStrategy z metodą void sort(int[] arr).
         * Zaimplementuj przez lambdy:
         * - bubbleSort
         * - selectionSort
         * - reverseSort (odwraca tablicę)
         * Klasa Sorter przyjmuje SortStrategy i używa jej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_Predicate {
        /*
         * 🧪 Zadanie 14:
         * @FunctionalInterface Predicate<T> z metodą boolean test(T value).
         * Stwórz: isPositive, isEven, isLongerThan(n), startsWithUpper.
         * Metoda static <T> T[] filter(T[] arr, Predicate<T> pred) filtruje tablicę.
         * Przefiltruj tablicę intów i tablicę Stringów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_PaymentSystem {
        /*
         * 🧪 Zadanie 15:
         * Interfejs PaymentMethod z: boolean charge(double amount), String getProvider().
         * Klasy: CreditCard, BankTransfer, Cryptocurrency, PayPal.
         * Stwórz interfejs Refundable z: boolean refund(double amount).
         * CreditCard i PayPal implementują oba interfejsy.
         * Przetestuj płatności i zwroty.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_Mixins {
        /*
         * 🧪 Zadanie 16:
         * Interfejsy jako "mixiny":
         * - Timestamps z default: String createdAt() → "2024-01-01"
         * - SoftDeletable z: boolean isDeleted(), void delete()
         * - Auditable z: String lastModifiedBy()
         * Klasa User implements Timestamps, SoftDeletable, Auditable.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_EventSystem {
        /*
         * 🧪 Zadanie 17:
         * @FunctionalInterface EventListener<T> z metodą void onEvent(T event).
         * Klasy zdarzeń: LoginEvent(userId), LogoutEvent(userId), PurchaseEvent(amount).
         * EventBus z metodami: <T> void subscribe(Class<T> type, EventListener<T> listener),
         * <T> void emit(T event).
         * Zarejestruj listenery i emituj zdarzenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Pipeline {
        /*
         * 🧪 Zadanie 18:
         * @FunctionalInterface Transformer<T, R> z metodą R transform(T input).
         * Połącz transformacje:
         * String → int (length) → boolean (isEven) → String ("parzyste"/"nieparzyste").
         * Klasa Pipeline<T> z metodą <R> Pipeline<R> then(Transformer<T, R> step).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_InterfaceEvolution {
        /*
         * 🧪 Zadanie 19:
         * Pokaż ewolucję API przez default methods:
         * Interfejs v1: Service z metodą String process(String input).
         * Interfejs v2: dodaj default String validate(String input) → "valid".
         * Interfejs v3: dodaj default String cache(String input) → process(input).
         * Stara klasa OldService implements v1 działa bez zmian.
         * Wyjaśnij w komentarzu dlaczego default methods to ważna cecha.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Composable {
        /*
         * 🧪 Zadanie 20:
         * @FunctionalInterface Condition z metodą boolean evaluate(int n).
         * Metody default:
         * - Condition and(Condition other) → n -> this.evaluate(n) && other.evaluate(n)
         * - Condition or(Condition other) → n -> this.evaluate(n) || other.evaluate(n)
         * - Condition negate() → n -> !this.evaluate(n)
         * Zbuduj: isPositiveEvenOrDivisibleBy3 = positive.and(even).or(divisibleBy3).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_Command {
        /*
         * 🧪 Zadanie 21:
         * Interfejsy: Command (execute(), undo()), UndoableCommand extends Command.
         * @FunctionalInterface SimpleCommand extends Command (bez undo).
         * Klasa CommandProcessor z historią poleceń.
         * Zaimplementuj 5 poleceń i przetestuj run/undo.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_Decorator {
        /*
         * 🧪 Zadanie 22:
         * Interfejs Coffee z metodami: String getDescription(), double getCost().
         * Klasy: Espresso, Latte.
         * Dekoratory: MilkDecorator, SugarDecorator, VanillaDecorator.
         * Każdy dekorator owija Coffee i dodaje do opisu i kosztu.
         * Zbuduj: Espresso + Milk + Sugar + Vanilla.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Observer {
        /*
         * 🧪 Zadanie 23:
         * @FunctionalInterface Observer<T> z metodą void update(T value).
         * Klasa Observable<T> z metodami: subscribe(Observer<T>), setValue(T), unsubscribe(Observer<T>).
         * Stwórz Observable<Integer> dla licznika.
         * Subskrybuj 3 obserwatorów, zmień wartość 3 razy, odsubskrybuj 1.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_Builder {
        /*
         * 🧪 Zadanie 24:
         * Interfejs Buildable<T> z metodą T build().
         * Interfejs FluentBuilder<T, SELF extends FluentBuilder<T, SELF>> z:
         * - SELF with(String key, String value)
         * - T build()
         * Implementacja: JsonBuilder i XmlBuilder.
         * Zbuduj JSON i XML tym samym API.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_Repository {
        /*
         * 🧪 Zadanie 25:
         * Interfejs Repository<T, ID> z metodami:
         * - void save(T entity)
         * - T findById(ID id)
         * - void delete(ID id)
         * - T[] findAll()
         * - default void saveAll(T[] entities) { for each: save }
         * Implementacja: InMemoryUserRepository (tablica).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_Specification {
        /*
         * 🧪 Zadanie 26:
         * Interfejs Specification<T> z metodami:
         * - boolean isSatisfiedBy(T item)
         * - default Specification<T> and(Specification<T> other)
         * - default Specification<T> or(Specification<T> other)
         * - default Specification<T> not()
         * Stwórz specyfikacje dla produktów (cena, kategoria, dostępność).
         * Filtruj produkty złożonymi specyfikacjami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_PluginSystem {
        /*
         * 🧪 Zadanie 27:
         * Interfejs Plugin z metodami: String getName(), void init(), void execute(String input), void shutdown().
         * Interfejs PriorityPlugin extends Plugin z: int getPriority().
         * Klasa PluginManager z tablicą Plugin[]:
         * - register(Plugin), unregister(String name)
         * - void runAll(String input) – sortuje by priority (jeśli PriorityPlugin), uruchamia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Monad {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj prosty Maybe<T> monad:
         * Interfejs Maybe<T> z metodami:
         * - boolean isPresent()
         * - T get()
         * - <R> Maybe<R> map(Transformer<T,R> fn)
         * - Maybe<T> filter(Predicate<T> pred)
         * Klasy: Just<T>(value) i Nothing<T>.
         * Przetestuj łańcuch: Maybe.of("hello").filter(s->s.length()>3).map(String::toUpperCase).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_InterfaceSegregation {
        /*
         * 🧪 Zadanie 29:
         * Zasada Interface Segregation Principle (ISP):
         * Zamiast jednego dużego interfejsu Worker(work, eat, sleep, charge),
         * stwórz: Workable (work()), Feedable (eat(), sleep()), Rechargeable (charge()).
         * HumanWorker implements Workable, Feedable.
         * RobotWorker implements Workable, Rechargeable.
         * Wyjaśnij w komentarzu dlaczego ISP jest ważne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_DependencyInversion {
        /*
         * 🧪 Zadanie 30:
         * Zasada Dependency Inversion Principle (DIP) przez interfejsy:
         * Interfejsy: MessageSender (send(String to, String msg)), NotificationChannel (notify(String msg)).
         * Klasy niskiego poziomu: EmailSender, SmsSender, PushSender.
         * Klasa NotificationService (wysoki poziom) ma MessageSender[] i wysyła przez wszystkie.
         * Pokaż jak łatwo dodać nowy kanał bez zmiany NotificationService.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
