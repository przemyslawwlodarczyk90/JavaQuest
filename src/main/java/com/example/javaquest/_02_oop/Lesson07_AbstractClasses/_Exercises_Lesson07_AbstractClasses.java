package com.example.javaquest._02_oop.Lesson07_AbstractClasses;

public class _Exercises_Lesson07_AbstractClasses {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicAbstract {
        /*
         * 🧪 Zadanie 1:
         * Stwórz abstrakcyjną klasę Animal z polem name i metodą abstract sound().
         * Podklasy Dog (→ "Hau!") i Cat (→ "Miau!").
         * Utwórz tablicę Animal[] i wywołaj sound() polimorficznie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_AbstractMethod {
        /*
         * 🧪 Zadanie 2:
         * Stwórz abstrakcyjną klasę Shape z:
         * - abstract double area()
         * - concrete void describe() wypisująca "To jest [getClass().getSimpleName()] o polu [area()]"
         * Podklasy Circle i Square.
         * Wywołaj describe() na obu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_NoInstantiation {
        /*
         * 🧪 Zadanie 3:
         * Udowodnij że nie można stworzyć instancji klasy abstrakcyjnej.
         * Stwórz abstract class Base {} i spróbuj new Base() (w komentarzu).
         * Wyjaśnij w komentarzu co się dzieje i jak ominąć przez podklasę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_MixedMethods {
        /*
         * 🧪 Zadanie 4:
         * Abstrakcyjna klasa Vehicle z:
         * - concrete method start() → "Silnik włączony"
         * - concrete method stop()  → "Silnik wyłączony"
         * - abstract String fuelType()
         * Podklasy: GasCar ("benzyna"), ElectricCar ("elektryczność"), HydrogenCar ("wodór").
         * Wypisz typ paliwa każdego pojazdu, uruchom i zatrzymaj.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_Constructor {
        /*
         * 🧪 Zadanie 5:
         * Abstrakcyjna klasa Person z polem name i konstruktorem Person(String name).
         * Podklasa Student extends Person z polem major.
         * Student(String name, String major) → super(name).
         * Wypisz dane studenta.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_AbstractArea {
        /*
         * 🧪 Zadanie 6:
         * Abstrakcyjna klasa Figure z abstract double area(), abstract double perimeter().
         * Podklasy: Triangle(base, height, side), Rhombus(diagonal1, diagonal2, side).
         * Przetestuj pole i obwód każdej figury.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_AbstractChain {
        /*
         * 🧪 Zadanie 7:
         * Stwórz: abstract A → abstract B extends A → concrete C extends B.
         * A: abstract void methodA()
         * B: abstract void methodB(), concrete void callAll() → methodA() + methodB()
         * C: implementuje obie.
         * Wywołaj callAll() na obiekcie C.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_Implement {
        /*
         * 🧪 Zadanie 8:
         * Abstrakcyjna klasa Converter z abstract double convert(double value).
         * Podklasy:
         * - CelsiusToFahrenheit
         * - KgToPounds
         * - KmToMiles
         * Przetestuj każdy konwerter.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_AbstractState {
        /*
         * 🧪 Zadanie 9:
         * Abstrakcyjna klasa MediaPlayer z polem boolean playing i polami title.
         * Concrete: play() → playing=true, stop() → playing=false.
         * Abstract: String getFormat().
         * Podklasy: Mp3Player, StreamingPlayer, VinylPlayer.
         * Odtwórz i zatrzymaj każdy odtwarzacz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_ConcreteBase {
        /*
         * 🧪 Zadanie 10:
         * Abstrakcyjna klasa Logger z:
         * - concrete void info(String msg) → formatuje i wywołuje abstract write(String)
         * - concrete void error(String msg) → podobnie
         * - abstract void write(String formattedMsg)
         * Podklasy: ConsoleLogger, FileLogger (tylko drukuje gdzie by zapisał).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TemplateMethod {
        /*
         * 🧪 Zadanie 11:
         * Abstrakcyjna klasa Report z metodą final void generate():
         * fetchData() → processData() → format() → output()
         * Podklasy:
         * - SalesReport (własna implementacja kroków)
         * - AuditReport (własna implementacja kroków)
         * Wywołaj generate() dla obu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_HookMethods {
        /*
         * 🧪 Zadanie 12:
         * Abstrakcyjna klasa Game z:
         * - final void run() → setup() → loop() → teardown()
         * - abstract void setup(), abstract void loop()
         * - concrete void teardown() → "Gra zakończona"
         * Podklasy: SnakeGame, TetrisGame.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_AbstractFactory {
        /*
         * 🧪 Zadanie 13:
         * Abstrakcyjna klasa DocumentFactory z:
         * - abstract Document createDocument(String name)
         * - concrete void openDocument(String name) → createDocument → doc.open()
         * Podklasy: PdfFactory, WordFactory.
         * Klasa Document ma pole name i metodę open().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_Sorting {
        /*
         * 🧪 Zadanie 14:
         * Abstrakcyjna klasa SortStrategy z:
         * - abstract void sort(int[] arr)
         * - concrete void sortAndPrint(int[] arr) → sort(arr) + wypisz wynik
         * - abstract String name()
         * Podklasy: BubbleSort, SelectionSort, InsertionSort.
         * Porównaj wszystkie trzy dla tablicy {5, 2, 8, 1, 9}.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_AbstractValidator {
        /*
         * 🧪 Zadanie 15:
         * Abstrakcyjna klasa Validator<T> z:
         * - abstract boolean isValid(T value)
         * - concrete void validate(T value) → jeśli !isValid() rzuć IllegalArgumentException
         * Podklasy: PositiveNumberValidator, NonEmptyStringValidator, EmailValidator.
         * Przetestuj poprawne i niepoprawne dane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_AbstractIterator {
        /*
         * 🧪 Zadanie 16:
         * Abstrakcyjna klasa NumberRange z polami start, end, step.
         * Abstract method abstract int[] generate().
         * Concrete method void print() → generate() + wypisz.
         * Podklasy: EvenNumbers, OddNumbers, Multiples(of).
         * Wygeneruj i wypisz zakres 0-20 dla każdego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_MixedAbstract {
        /*
         * 🧪 Zadanie 17:
         * Abstrakcyjna klasa DatabaseConnection z:
         * - abstract void connect(), abstract void disconnect(), abstract boolean ping()
         * - concrete void executeQuery(String sql) → connect → wypisz sql → disconnect
         * Podklasy: MySQLConnection, PostgreSQLConnection, SQLiteConnection.
         * Uruchom executeQuery na każdej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_AbstractWidget {
        /*
         * 🧪 Zadanie 18:
         * Abstrakcyjna klasa UIWidget z polami: id, visible (boolean).
         * Concrete: show(), hide(), boolean isVisible().
         * Abstract: void render(), String getType().
         * Podklasy: Button, TextField, CheckBox, Dropdown.
         * Wyrenderuj każdy widget (jeśli visible).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_ExtendAbstract {
        /*
         * 🧪 Zadanie 19:
         * Stwórz: abstract Animal → abstract Pet extends Animal (dodaje ownerName) → Dog, Cat.
         * Animal: abstract String sound(), concrete void breathe().
         * Pet: abstract void greetOwner(), concrete void introduce() → "Jestem [name] należący do [owner]".
         * Dog i Cat implementują obie metody abstrakcyjne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_PipelineAbstract {
        /*
         * 🧪 Zadanie 20:
         * Abstrakcyjna klasa PipelineStep z:
         * - abstract String process(String input)
         * - concrete PipelineStep then(PipelineStep next) – ustawia kolejny krok
         * - concrete String execute(String input) → process → if(next) next.execute
         * Kroki: TrimStep, UpperCaseStep, ReplaceSpacesStep("_"), AddPrefixStep(prefix).
         * Zbuduj pipeline i przetestuj na " hello world ".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AbstractCommand {
        /*
         * 🧪 Zadanie 21:
         * Abstrakcyjna klasa Command z:
         * - abstract void execute(), abstract void undo()
         * - concrete boolean wasExecuted()
         * Klasa CommandHistory przechowuje Command[] i metody: run(Command), undoLast().
         * Zaimplementuj: AddTextCommand, DeleteTextCommand, FormatTextCommand.
         * Uruchom 3 komendy i cofnij 2.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_AbstractRepository {
        /*
         * 🧪 Zadanie 22:
         * Abstrakcyjna klasa Repository<T> z:
         * - abstract void save(T item)
         * - abstract T findById(int id)
         * - abstract void delete(int id)
         * - concrete void saveAndLog(T item) → save(item) + wypisz "Saved: " + item
         * Podklasy: UserRepository, ProductRepository (przechowują w tablicy).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_AbstractObserver {
        /*
         * 🧪 Zadanie 23:
         * Abstrakcyjna klasa EventListener z:
         * - abstract void onEvent(String eventType, Object data)
         * - concrete boolean listensTo(String type) → porównuje z getListenedTypes()
         * - abstract String[] getListenedTypes()
         * Podklasy: ClickListener (nasłuchuje "click"), KeyListener ("keydown","keyup").
         * EventBus: subscribe(EventListener), emit(String type, Object data).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_AbstractBuilder {
        /*
         * 🧪 Zadanie 24:
         * Abstrakcyjna klasa QueryBuilder<T extends QueryBuilder<T>> z:
         * - abstract T self() (zwraca this jako T)
         * - T where(String condition) → this.conditions = ...; return self()
         * - T limit(int n) → return self()
         * - abstract String build()
         * Podklasy: SelectBuilder (dodaje from, select), DeleteBuilder (dodaje from).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_AbstractStrategy {
        /*
         * 🧪 Zadanie 25:
         * Abstrakcyjna klasa CompressionAlgorithm z:
         * - abstract byte[] compress(String data)
         * - abstract String decompress(byte[] data)
         * - concrete double compressionRatio(String data) → data.length vs compress(data).length
         * Podklasy: NoCompression, RunLengthEncoding (uproszczone), ReverseObfuscation.
         * Porównaj ratio dla tekstu "aaaaabbbbbccccc".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_AbstractComponent {
        /*
         * 🧪 Zadanie 26:
         * Wzorzec Composite z klasą abstrakcyjną:
         * abstract class Component { abstract int getSize(); abstract void display(int depth); }
         * Klasa File extends Component (jeden element).
         * Klasa Directory extends Component (zawiera Component[]).
         * Zbuduj strukturę folderów i policz łączny rozmiar.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_AbstractCache {
        /*
         * 🧪 Zadanie 27:
         * Abstrakcyjna klasa Cache<K, V> z:
         * - abstract void put(K key, V value)
         * - abstract V get(K key)
         * - abstract void evict(K key)
         * - concrete V getOrCompute(K key, V defaultValue)
         * - abstract int size()
         * Podklasy: LRUCache (usuwa najstarszy gdy pełny) i TTLCache (usuwa po 3 get'ach).
         * Użyj uproszczonych tablic zamiast Map.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_AbstractProtocol {
        /*
         * 🧪 Zadanie 28:
         * Abstrakcyjna klasa NetworkProtocol z:
         * - final void communicate(String message) → handshake → send(message) → receive() → close()
         * - abstract void handshake(), abstract void send(String msg), abstract String receive(), abstract void close()
         * Podklasy: HttpProtocol, WebSocketProtocol, GrpcProtocol.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_AbstractRecursive {
        /*
         * 🧪 Zadanie 29:
         * Abstrakcyjna klasa TreeNode z polem int value.
         * Abstract: int count(), int sum(), int depth(), void print(int indent).
         * Klasa Leaf extends TreeNode (brak dzieci).
         * Klasa Branch extends TreeNode (ma left i right TreeNode).
         * Zbuduj drzewo i policz count, sum, depth.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_AbstractMachine {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj abstrakcyjną maszynę Turinga (uproszczoną):
         * abstract class TuringMachine z:
         * - abstract char[] getAlphabet()
         * - abstract void transition(char symbol)
         * - concrete void run(char[] tape)
         * Podklasy: BinaryIncrementer (dodaje 1 do liczby binarnej), PalindromeChecker.
         * Przetestuj każdą maszynę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
