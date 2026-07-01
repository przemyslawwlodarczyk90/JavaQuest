package com.example.javaquest._02_oop.Lesson15_DesignPatterns;

public class _Exercises_Lesson15_DesignPatterns {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_Singleton {
        /*
         * 🧪 Zadanie 1:
         * Zaimplementuj Singleton Logger z prywatnym konstruktorem.
         * Metoda getInstance() i log(String message).
         * Sprawdź że dwa wywołania getInstance() dają ten sam obiekt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_SimpleBuilder {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę House z Builder zawierającym pola:
         * rooms (int), floors (int), color (String), hasGarage (boolean).
         * Zbuduj dom: 5 pokoi, 2 piętra, biały, z garażem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_SimpleStrategy {
        /*
         * 🧪 Zadanie 3:
         * Interfejs SortStrategy z metodą void sort(int[] arr).
         * Strategie: AscendingSort i DescendingSort.
         * Klasa NumberSorter z setStrategy() i sort().
         * Posortuj {5,3,8,1} rosnąco i malejąco.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_SimpleObserver {
        /*
         * 🧪 Zadanie 4:
         * Interfejs Observer z metodą void update(String message).
         * Klasa Subject z addObserver, removeObserver, notifyAll.
         * 2 Obserwatorów wypisują wiadomości. Emituj 3 zdarzenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_SimpleFactory {
        /*
         * 🧪 Zadanie 5:
         * Interfejs Animal z metodą String sound().
         * Klasy: Dog, Cat, Bird.
         * Klasa AnimalFactory z metodą static Animal create(String type).
         * Przetestuj tworzenie każdego zwierzęcia przez fabrykę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_SimpleDecorator {
        /*
         * 🧪 Zadanie 6:
         * Interfejs TextFormatter z metodą String format(String text).
         * PlainText zwraca text bez zmian.
         * BoldDecorator owija → "**text**".
         * ItalicDecorator owija → "_text_".
         * Przetestuj: plain, bold, italic, bold+italic.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_SimpleCommand {
        /*
         * 🧪 Zadanie 7:
         * Interfejs Command z execute() i undo().
         * LightOnCommand i LightOffCommand dla klasy Light (pole isOn).
         * Klasa RemoteControl z lastCommand i undo().
         * Włącz, sprawdź stan, undo, sprawdź stan.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_SimpleTemplate {
        /*
         * 🧪 Zadanie 8:
         * Abstrakcyjna klasa Beverage z final makeDrink():
         * boilWater() → brew() → pour() → addCondiments().
         * Podklasy Tea i Coffee – brew i addCondiments są różne.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_SimpleProxy {
        /*
         * 🧪 Zadanie 9:
         * Interfejs Image z metodą void display().
         * RealImage ładuje plik przy display() (wypisz "Ładowanie [filename]").
         * ProxyImage ładuje lazily – tylko przy pierwszym display().
         * Przetestuj: stwórz ProxyImage, wywołaj display() dwa razy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_SimpleAdapter {
        /*
         * 🧪 Zadanie 10:
         * Interfejs EuropeanSocket z metodą int getVolts() → 230.
         * Interfejs USASocket z metodą int getVolts() → 110.
         * Klasa EuroToUSAAdapter implements USASocket, ma EuropeanSocket.
         * Wypisz napięcie przez adapter.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuilderFluent {
        /*
         * 🧪 Zadanie 11:
         * Klasa QueryBuilder z fluent API:
         * .select(String... columns).from(String table).where(String cond)
         * .orderBy(String col).limit(int n).build() → SQL String.
         * Przetestuj kilka zapytań.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_StrategyPayment {
        /*
         * 🧪 Zadanie 12:
         * Interfejs PaymentStrategy z: boolean pay(double amount), String name().
         * Strategie: CreditCard, PayPal, Bitcoin, BankTransfer.
         * Klasa Checkout przyjmuje PaymentStrategy.
         * Przetestuj każdą strategię dla kwoty 199.99.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_ObserverStock {
        /*
         * 🧪 Zadanie 13:
         * Klasa StockMarket jako Subject z tickerSymbol i currentPrice.
         * Obserwatorzy: MobileAlert, EmailAlert, DatabaseLogger.
         * Zmień cenę akcji 5 razy. Po każdej zmianie obserwatorzy reagują.
         * Jeden obserwator odsubskrybuje po 3 zmianie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_DecoratorCoffee {
        /*
         * 🧪 Zadanie 14:
         * Interfejs Coffee z getDescription() i getCost().
         * Espresso (base), Latte (base).
         * Dekoratory: Milk (+0.50), Sugar (+0.20), Vanilla (+1.00), ExtraShot (+1.50).
         * Zbuduj: Espresso + ExtraShot + Milk + Sugar i wypisz opis i koszt.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_FactoryMethod {
        /*
         * 🧪 Zadanie 15:
         * Abstrakcyjna klasa Notification z abstract send(String to, String message).
         * Fabryczna metoda: abstract Notification createNotification(String channel).
         * Podklasy: EmailNotificationFactory, SMSNotificationFactory, PushNotificationFactory.
         * Każda zwraca konkretny typ Notification.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_CommandHistory {
        /*
         * 🧪 Zadanie 16:
         * System edytora tekstu z Command Pattern:
         * TextEditor z polem StringBuilder content.
         * Komendy: TypeCommand(text), DeleteCommand(count), ReplaceCommand(from, to).
         * CommandHistory z execute(), undo(), redo().
         * Symuluj edycję: type "Hello", type " World", delete 5, undo, undo.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_AbstractFactory {
        /*
         * 🧪 Zadanie 17:
         * Abstract Factory dla UI:
         * Interfejs UIFactory z: createButton(), createCheckbox(), createTextField().
         * Klasy: WindowsFactory, MacFactory.
         * Każda tworzy elementy odpowiednie dla swojego systemu.
         * Metoda renderUI(UIFactory factory) tworzy i renderuje kompletny UI.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_Facade {
        /*
         * 🧪 Zadanie 18:
         * Wzorzec Facade dla systemu kina:
         * Klasy: Projector (on/off), SoundSystem (volume), Lights (dim/bright), Screen (down/up).
         * CinemaFacade z metodami: watchMovie(String title) i endMovie().
         * watchMovie: lights dim, screen down, projector on, sound up.
         * endMovie: odwrócone kolejności.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_State {
        /*
         * 🧪 Zadanie 19:
         * Wzorzec State dla vending machine:
         * Interfejs VendingState z: insertCoin(), pressButton(), dispense().
         * Stany: NoMoneyState, HasMoneyState, DispensingState.
         * Klasa VendingMachine przechowuje current state.
         * Symuluj zakup produktu i próbę bez monet.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_Iterator {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj Iterator Pattern:
         * Interfejs MyIterator<T> z: boolean hasNext(), T next().
         * Klasa NumberCollection z ArrayIterator jako inner class.
         * Przetestuj iterację po kolekcji 1-10.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_Composite {
        /*
         * 🧪 Zadanie 21:
         * Wzorzec Composite dla systemu plików:
         * Interfejs FileSystemItem z: String getName(), long getSize(), void display(int indent).
         * Klasa File (leaf) i Directory (composite) z FileSystemItem[].
         * Zbuduj strukturę folderów i oblicz łączny rozmiar.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ChainOfResponsibility {
        /*
         * 🧪 Zadanie 22:
         * Wzorzec Chain of Responsibility dla obsługi HTTP requestów:
         * Abstrakcyjny Handler z next i handle(Request).
         * Handlery: AuthHandler (sprawdza token), RateLimitHandler (max req/s), LoggingHandler, RouteHandler.
         * Przetestuj z requestem autoryzowanym i nieautoryzowanym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_Visitor {
        /*
         * 🧪 Zadanie 23:
         * Wzorzec Visitor dla figur geometrycznych:
         * Interfejs ShapeVisitor z: visitCircle, visitRectangle, visitTriangle.
         * Figury mają: double accept(ShapeVisitor v).
         * Visitory: AreaVisitor, PerimeterVisitor, DrawVisitor.
         * Odwiedź tablicę figur każdym visitorem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_Mediator {
        /*
         * 🧪 Zadanie 24:
         * Wzorzec Mediator dla czatu:
         * Interfejs ChatMediator z: sendMessage(String from, String to, String msg), addUser(User).
         * Klasa ChatRoom implements ChatMediator.
         * Klasa User (name, mediator) z: sendTo(String to, String msg), receive(String msg).
         * Przetestuj rozmowę 4 użytkowników.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_Interpreter {
        /*
         * 🧪 Zadanie 25:
         * Wzorzec Interpreter dla wyrażeń matematycznych:
         * Interfejs Expression z: int interpret().
         * Klasy: NumberExpression(int), AddExpression(left, right), MultiplyExpression(left, right),
         *         SubtractExpression(left, right).
         * Oblicz: (3 + 4) * (10 - 6) bez zmiany kodu klienta.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_Flyweight {
        /*
         * 🧪 Zadanie 26:
         * Wzorzec Flyweight dla obiektów czcionek:
         * Klasa FontStyle z (font, size, bold, italic) – pola wspólne (intrinsic).
         * Klasa FontStyleFactory cachuje już stworzone FontStyle.
         * Przetestuj tworzenie 1000 obiektów czcionek – ile unikalnych?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_Memento {
        /*
         * 🧪 Zadanie 27:
         * Wzorzec Memento dla edytora tekstu:
         * Klasa TextEditor z content i historią Memento[].
         * Klasa TextMemento (snapshot stanu).
         * Metody: save(), restore(TextMemento), undo().
         * Symuluj: pisanie, save, pisanie, pisanie, undo do save.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Bridge {
        /*
         * 🧪 Zadanie 28:
         * Wzorzec Bridge rozdziela abstrakcję od implementacji:
         * Interfejs Renderer z: renderCircle(double r), renderRect(double w, double h).
         * Implementacje: VectorRenderer, RasterRenderer.
         * Abstrakcyjna klasa Shape z Renderer (wstrzykiwany).
         * Circle i Rectangle extends Shape.
         * Przetestuj każdą kombinację Shape + Renderer.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_GOF {
        /*
         * 🧪 Zadanie 29:
         * Połącz kilka wzorców w jednym mini-systemie:
         * System zamówień z:
         * - Builder (tworzenie zamówienia)
         * - Observer (powiadomienia o zmianach)
         * - Strategy (różne metody dostawy)
         * - Command (operacje na zamówieniu z undo)
         * Symuluj pełny cykl zamówienia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_Framework {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj mini-framework webowy używając wzorców:
         * - Singleton: ApplicationContext (kontener)
         * - Factory Method: ControllerFactory (tworzy kontrolery)
         * - Chain of Responsibility: Middleware (auth, logging, cors)
         * - Command: HttpRequest (przetwarzanie żądania)
         * - Template Method: BaseController (cykl obsługi requestu)
         * Obsłuż request: GET /users → zwróć listę użytkowników.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
