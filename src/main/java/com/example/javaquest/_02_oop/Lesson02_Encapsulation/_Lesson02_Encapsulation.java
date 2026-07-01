package com.example.javaquest._02_oop.Lesson02_Encapsulation;

public class _Lesson02_Encapsulation {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔒 CZYM JEST ENKAPSULACJA?
         * ============================================================
         * Enkapsulacja (ang. encapsulation) = ukrywanie szczegółów implementacji
         * i udostępnianie tylko tego, co potrzebne na zewnątrz.
         *
         * Zasada: pola klasy powinny być prywatne (private),
         * dostęp do nich poprzez publiczne metody (gettery/settery).
         *
         * Korzyści:
         * - kontrola nad danymi (walidacja w setterze)
         * - możliwość zmiany implementacji bez wpływu na zewnątrz
         * - ochrona przed niepoprawnym stanem obiektu
         */

        BankAccount account = new BankAccount("Jan Kowalski", 1000.0);
        System.out.println("Właściciel: " + account.getOwner());
        System.out.println("Saldo: " + account.getBalance());

        account.deposit(500.0);
        System.out.println("Po wpłacie: " + account.getBalance());

        boolean success = account.withdraw(200.0);
        System.out.println("Wypłata 200: " + success + ", saldo: " + account.getBalance());

        boolean failed = account.withdraw(5000.0);
        System.out.println("Wypłata 5000: " + failed + ", saldo: " + account.getBalance());

        /*
         * ============================================================
         * 🔑 GETTERY I SETTERY
         * ============================================================
         * getter: metoda zwracająca wartość pola
         *   konwencja nazwy: getFieldName() lub isFieldName() dla boolean
         *
         * setter: metoda ustawiająca wartość pola, może zawierać walidację
         *   konwencja nazwy: setFieldName(typ wartość)
         *
         * Nie każde pole musi mieć oba – czasami pole może być:
         * - tylko do odczytu (tylko getter, brak settera)
         * - tylko do zapisu (rzadkie – tylko setter)
         * - w pełni ukryte (brak obu)
         */

        Person person = new Person("Anna", 25);
        System.out.println("Imię: " + person.getName());
        System.out.println("Wiek: " + person.getAge());
        System.out.println("Pełnoletni?: " + person.isAdult());

        person.setAge(17); // walidacja: wiek nie może być ujemny
        System.out.println("Wiek po próbie ustawienia 17: " + person.getAge());

        person.setAge(-5); // próba ustawienia ujemnego – ignorowana
        System.out.println("Wiek po próbie ustawienia -5: " + person.getAge());

        /*
         * ============================================================
         * 🔐 MODYFIKATORY DOSTĘPU (skrótowo – szczegóły w Lesson09)
         * ============================================================
         * private  → dostępne tylko wewnątrz klasy
         * public   → dostępne wszędzie
         * protected → dostępne w klasie, podklasach i pakiecie
         * (brak)   → dostępne tylko w tym samym pakiecie (package-private)
         *
         * Reguła minimalnego dostępu: ujawnij tylko tyle, ile konieczne.
         */

        /*
         * ============================================================
         * 🛡️ WALIDACJA W SETTERACH
         * ============================================================
         * Setter może odrzucić niepoprawną wartość lub rzucić wyjątek.
         * Dzięki temu obiekt zawsze jest w spójnym stanie.
         */

        Temperature temp = new Temperature(20.0);
        System.out.println("Temperatura: " + temp.getCelsius() + "°C");
        System.out.println("Fahrenheit: " + temp.getFahrenheit() + "°F");

        temp.setCelsius(-300); // poniżej zera absolutnego – odrzucone
        System.out.println("Temperatura po błędnym ustawieniu: " + temp.getCelsius());

        temp.setCelsius(100);
        System.out.println("Temperatura po poprawnym ustawieniu: " + temp.getCelsius());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - private fields + public getters/setters = enkapsulacja
         * - setter może walidować dane przed przypisaniem
         * - getter może przeliczać lub formatować dane przed zwróceniem
         * - obiekt zawsze powinien być w spójnym stanie (invariant)
         * - nie każde pole musi mieć getter i setter
         */
    }
}

class BankAccount {
    private String owner;
    private double balance;

    BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance >= 0 ? initialBalance : 0;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class Person {
    private String name;
    private int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age >= 0 ? age : 0;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isAdult() {
        return age >= 18;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }
}

class Temperature {
    private double celsius;

    Temperature(double celsius) {
        this.celsius = celsius;
    }

    public double getCelsius() {
        return celsius;
    }

    public double getFahrenheit() {
        return celsius * 9 / 5 + 32;
    }

    public void setCelsius(double celsius) {
        if (celsius >= -273.15) { // zero absolutne
            this.celsius = celsius;
        }
    }
}
