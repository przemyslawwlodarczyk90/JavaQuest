package com.example.javaquest._02_oop.Lesson15_DesignPatterns;

import java.util.ArrayList;
import java.util.List;

public class _Lesson15_DesignPatterns {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🏛️ WZORCE PROJEKTOWE OOP (Design Patterns)
         * ============================================================
         * Wzorce projektowe to sprawdzone rozwiązania typowych problemów
         * w projektowaniu oprogramowania.
         *
         * Trzy kategorie (Gang of Four):
         * - Kreacyjne (Creational): jak tworzyć obiekty
         * - Strukturalne (Structural): jak organizować klasy
         * - Behawioralne (Behavioral): jak klasy współpracują
         *
         * W tej lekcji: Singleton, Builder, Strategy, Observer
         */

        /*
         * ============================================================
         * 🔒 SINGLETON – jeden obiekt dla całej aplikacji
         * ============================================================
         * Problem: chcemy mieć dokładnie jedną instancję klasy (np. baza danych,
         * konfiguracja, logger).
         *
         * Rozwiązanie:
         * - prywatny konstruktor
         * - statyczne pole z instancją
         * - statyczna metoda getInstance()
         *
         * Kategoria: Kreacyjny
         */

        AppDatabase db1 = AppDatabase.getInstance();
        AppDatabase db2 = AppDatabase.getInstance();

        db1.query("SELECT * FROM users");
        System.out.println("Ten sam obiekt DB? " + (db1 == db2)); // true

        /*
         * ============================================================
         * 🏗️ BUILDER – budowanie złożonych obiektów krok po kroku
         * ============================================================
         * Problem: klasa z wieloma parametrami konstruktora jest trudna w użyciu.
         * Konstruktor z 10 parametrami to antypattern (telescoping constructor).
         *
         * Rozwiązanie: oddzielna klasa Builder pozwala ustawiać każde pole
         * osobno i na końcu zbudować gotowy obiekt.
         *
         * Kategoria: Kreacyjny
         */

        HttpRequest request = new HttpRequest.Builder("https://api.example.com/users")
                .method("POST")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer token123")
                .body("{\"name\": \"Jan\"}")
                .timeout(5000)
                .build();

        System.out.println(request);

        /*
         * ============================================================
         * ⚡ STRATEGY – wymienne algorytmy/zachowania
         * ============================================================
         * Problem: mamy klasę, która powinna zachowywać się różnie w zależności
         * od kontekstu (np. różne algorytmy sortowania, różne sposoby płatności).
         *
         * Rozwiązanie: wydziel algorytm do osobnego interfejsu (Strategy),
         * wstrzyknij konkretną implementację w czasie działania.
         *
         * Kategoria: Behawioralny
         */

        ShoppingCart cart = new ShoppingCart(150.0);

        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout();

        cart.setPaymentStrategy(new BlikPayment("123456"));
        cart.checkout();

        cart.setPaymentStrategy(new CashPayment());
        cart.checkout();

        /*
         * ============================================================
         * 📡 OBSERVER – powiadamianie o zdarzeniach
         * ============================================================
         * Problem: obiekt (Subject) zmienia stan i chcemy powiadomić
         * wiele innych obiektów (Observers) bez ścisłego powiązania.
         *
         * Rozwiązanie: Subject przechowuje listę Observerów i notyfikuje je
         * przy każdej zmianie stanu.
         *
         * Kategoria: Behawioralny
         * Zastosowanie: GUI events, event bus, reactive programming
         */

        WeatherStation station = new WeatherStation();

        PhoneDisplay phone = new PhoneDisplay("Telefon");
        TVDisplay tv = new TVDisplay("Telewizor");
        WebDisplay web = new WebDisplay("Strona WWW");

        station.addObserver(phone);
        station.addObserver(tv);
        station.addObserver(web);

        station.setTemperature(22.5);
        System.out.println("--- zmiana temperatury ---");
        station.setTemperature(18.0);

        station.removeObserver(tv);
        System.out.println("--- telewizor odłączony ---");
        station.setTemperature(25.0);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Singleton: jeden obiekt dla całej aplikacji (prywatny konstruktor + getInstance)
         * Builder:   krok po kroku budowanie złożonego obiektu
         * Strategy:  wymienne algorytmy wstrzykiwane w czasie działania
         * Observer:  powiadamianie wielu obserwatorów o zmianie stanu
         *
         * Te wzorce to fundament – istnieje ich ponad 20 (Gang of Four).
         * Dalej: Decorator, Factory, Command, Template Method...
         */
    }
}

// ============================================================
// SINGLETON
// ============================================================
class AppDatabase {
    private static AppDatabase instance;
    private int queryCount = 0;

    private AppDatabase() {
        System.out.println("[DB] Połączenie nawiązane.");
    }

    static AppDatabase getInstance() {
        if (instance == null) {
            instance = new AppDatabase();
        }
        return instance;
    }

    void query(String sql) {
        queryCount++;
        System.out.println("[DB #" + queryCount + "] " + sql);
    }
}

// ============================================================
// BUILDER
// ============================================================
class HttpRequest {
    private final String url;
    private final String method;
    private final List<String> headers;
    private final String body;
    private final int timeout;

    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
        this.timeout = builder.timeout;
    }

    @Override
    public String toString() {
        return "HttpRequest{" + method + " " + url
                + ", headers=" + headers.size()
                + ", body=" + (body != null ? body.length() + " chars" : "null")
                + ", timeout=" + timeout + "ms}";
    }

    static class Builder {
        private final String url;
        private String method = "GET";
        private List<String> headers = new ArrayList<>();
        private String body;
        private int timeout = 3000;

        Builder(String url) {
            this.url = url;
        }

        Builder method(String method)   { this.method = method; return this; }
        Builder header(String key, String value) { headers.add(key + ": " + value); return this; }
        Builder body(String body)       { this.body = body; return this; }
        Builder timeout(int ms)         { this.timeout = ms; return this; }

        HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}

// ============================================================
// STRATEGY
// ============================================================
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Płatność %.2f PLN kartą %s%n", amount,
                cardNumber.substring(cardNumber.length() - 4));
    }
}

class BlikPayment implements PaymentStrategy {
    private String code;

    BlikPayment(String code) {
        this.code = code;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Płatność %.2f PLN BLIK kodem %s%n", amount, code);
    }
}

class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.printf("Płatność gotówką: %.2f PLN%n", amount);
    }
}

class ShoppingCart {
    private double total;
    private PaymentStrategy paymentStrategy;

    ShoppingCart(double total) {
        this.total = total;
    }

    void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    void checkout() {
        paymentStrategy.pay(total);
    }
}

// ============================================================
// OBSERVER
// ============================================================
interface WeatherObserver {
    void update(double temperature);
}

class WeatherStation {
    private List<WeatherObserver> observers = new ArrayList<>();
    private double temperature;

    void addObserver(WeatherObserver o)    { observers.add(o); }
    void removeObserver(WeatherObserver o) { observers.remove(o); }

    void setTemperature(double temperature) {
        this.temperature = temperature;
        notifyObservers();
    }

    private void notifyObservers() {
        for (WeatherObserver o : observers) {
            o.update(temperature);
        }
    }
}

class PhoneDisplay implements WeatherObserver {
    private String name;

    PhoneDisplay(String name) { this.name = name; }

    @Override
    public void update(double temperature) {
        System.out.printf("[%s] Temperatura: %.1f°C%n", name, temperature);
    }
}

class TVDisplay implements WeatherObserver {
    private String name;

    TVDisplay(String name) { this.name = name; }

    @Override
    public void update(double temperature) {
        System.out.printf("[%s] Aktualna temperatura: %.1f stopni%n", name, temperature);
    }
}

class WebDisplay implements WeatherObserver {
    private String name;

    WebDisplay(String name) { this.name = name; }

    @Override
    public void update(double temperature) {
        System.out.printf("[%s] weather.update(%.1f)%n", name, temperature);
    }
}
