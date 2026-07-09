package com.example.javaquest._14_advancedjava.Lesson29_AdvancedLanguageBestPractices;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * LEKCJA 29: "KIEDY CZEGO UZYC" - SKROCONY PRZEGLAD CALEGO ROZDZIALU _14_advancedjava.
 *
 * To NIE jest nowy material - to "cheat sheet" laczacy decyzje projektowe z
 * poprzednich 28 lekcji w jedno miejsce, z krotkimi, dzialajacymi demami
 * kontrastujacymi "dobrze" i "zle" podejscie.
 */
public class _Lesson29_AdvancedLanguageBestPractices {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 29: DOBRE PRAKTYKI - PRZEGLAD CALEGO ROZDZIALU ===");

        /*
         * ============================================================
         * 📦 GENERICS vs RAW TYPES / OBJECT (Lekcje 1-7)
         * ============================================================
         * ZASADA: generics ZAWSZE, gdy to mozliwe. Surowy typ (raw type)
         * albo Object "dziala", ale przenosi sprawdzanie typow z
         * KOMPILACJI (bezpieczne, szybkie do naprawienia) na WYKONANIE
         * (ClassCastException w losowym miejscu produkcji).
         */
        demoGenericsVsRawTypes();

        /*
         * ============================================================
         * 🔹 LAMBDY / FUNKCYJNE INTERFEJSY vs KLASYCZNE OOP (Lekcje 8-11)
         * ============================================================
         * ZASADA: lambda dla KROTKIEGO, BEZSTANOWEGO zachowania (jedna
         * metoda, brak wlasnego stanu). Pelna klasa, gdy potrzebujesz:
         * - wiecej niz jednej metody publicznej,
         * - wlasnego, MUTOWALNEGO stanu,
         * - czytelnej nazwy typu w stack trace / API.
         */
        demoLambdaVsFullClass();

        /*
         * ============================================================
         * 🔍 REFLEKSJA vs METHOD HANDLES (Lekcje 15-18)
         * ============================================================
         * ZASADA: klasyczna refleksja (java.lang.reflect) - narzedzia,
         * frameworki, serializacja, DI, testy - miejsca gdzie liczy sie
         * ELASTYCZNOSC bardziej niz wydajnosc. Gdy kod jest na "goracej
         * sciezce" (wywolywany czesto), MethodHandle jest zazwyczaj
         * szybszy po rozgrzaniu JIT-a - kompilator JIT potrafi go lepiej
         * zinline'owac niz Method.invoke().
         */
        demoReflectionVsMethodHandles();

        /*
         * ============================================================
         * 🔹 SEALED + PATTERN MATCHING vs KLASYCZNY POLIMORFIZM/VISITOR (Lekcje 19-22)
         * ============================================================
         * ZASADA: sealed + switch swietnie sprawdza sie dla ZAMKNIETEGO,
         * znanego z gory zbioru typow, gdzie zalezy Ci na exhaustiveness
         * (kompilator pilnuje, ze obsluzyles WSZYSTKIE warianty). Klasyczny
         * polimorfizm (abstrakcyjna metoda w interfejsie) lepiej pasuje do
         * OTWARTYCH hierarchii - tam, gdzie SPODZIEWASZ SIE, ze ktos (Ty
         * albo autor wtyczki) dopisze nowy podtyp bez modyfikacji Twojego
         * kodu (patrz tez SPI - Lekcje 26-28, i Lekcja 30).
         */
        demoSealedVsOpenPolymorphism();

        /*
         * ============================================================
         * 📦 NIEZMIENNOSC DOMYSLNIE (Lekcje 24-25)
         * ============================================================
         * ZASADA: projektuj klasy jako NIEZMIENNE, dopoki nie masz
         * KONKRETNEGO powodu, zeby byly mutowalne (np. bardzo czesta
         * mutacja w petli o duzej skali). Niezmiennosc eliminuje CALA
         * klase bledow zwiazanych z aliasingiem i wspoldzielonym stanem
         * miedzy watkami "za darmo" - bez synchronized.
         */
        demoImmutabilityByDefault();

        /*
         * ============================================================
         * 🔹 var - KIEDY UZYWAC (Lekcja 23)
         * ============================================================
         * ZASADA: var tam, gdzie typ jest OCZYWISTY z prawej strony
         * przypisania (np. `new ArrayList<String>()`, literal, wynik
         * oczywistej metody fabrykujacej). NIE uzywaj var, gdy typ
         * wynikowy jest niejasny bez zagladania do sygnatury metody -
         * var ma sluzyc CZYTELNOSCI, nie skracaniu kodu za wszelka cene.
         */
        demoVarUsage();

        /*
         * ============================================================
         * 📌 ANTYWZORCE Z CALEGO ROZDZIALU - SZYBKI PRZEGLAD
         * ============================================================
         */
        printAntiPatternsTable();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE: MACIERZ DECYZYJNA "KIEDY CZEGO UZYC"
         * ============================================================
         * - Generics zawsze zamiast raw types/Object - bezpieczenstwo
         *   typow w czasie kompilacji, nie w runtime.
         * - Lambda dla krotkiego, bezstanowego zachowania; pelna klasa,
         *   gdy potrzeba wielu metod albo mutowalnego stanu.
         * - Refleksja dla frameworkow/narzedzi; MethodHandle, gdy liczy
         *   sie wydajnosc na czesto wywolywanej sciezce.
         * - Sealed+switch dla ZAMKNIETYCH hierarchii z exhaustiveness;
         *   klasyczny polimorfizm/visitor dla OTWARTYCH, rozszerzalnych.
         * - Niezmiennosc jako DOMYSLNY wybor - mutowalnosc tylko z
         *   konkretnego, swiadomego powodu.
         * - var tam, gdzie typ jest oczywisty - czytelnosc ponad
         *   skrotowosc.
         */
        printDecisionMatrix();

        System.out.println("\n=== KONIEC LEKCJI 29 ===");
    }

    // ============================================================
    // 📦 GENERICS vs RAW TYPES
    // ============================================================

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void demoGenericsVsRawTypes() {
        System.out.println("\n=== GENERICS vs RAW TYPES/OBJECT ===");

        // RAW TYPE - kompilator NIE sprawdza typow elementow.
        List rawList = new ArrayList();
        rawList.add("tekst");
        rawList.add(42); // KOMPILUJE SIE - ale to bomba zegarowa
        try {
            for (Object element : rawList) {
                String text = (String) element; // wybuchnie na elemencie 42
                System.out.println("RAW: element = " + text);
            }
        } catch (ClassCastException e) {
            System.out.println("RAW TYPE: ClassCastException W CZASIE WYKONANIA - " + e.getMessage());
        }

        // GENERICS - kompilator lapie bledny typ OD RAZU, przy pisaniu kodu.
        List<String> genericList = new ArrayList<>();
        genericList.add("tekst");
        // genericList.add(42); // BLAD KOMPILACJI - nie da sie nawet uruchomic z tym bledem
        System.out.println("GENERICS: " + genericList + " - bledny typ zlapany JUZ przy kompilacji");
    }

    // ============================================================
    // 🔹 LAMBDA vs KLASYCZNE OOP
    // ============================================================

    private static void demoLambdaVsFullClass() {
        System.out.println("\n=== LAMBDY vs KLASYCZNE OOP ===");

        List<String> names = new ArrayList<>(List.of("Zenon", "Ala", "Kasia", "Bob"));

        // Lambda - krotkie, bezstanowe zachowanie -> idealny przypadek dla lambdy.
        names.sort((a, b) -> a.length() - b.length());
        System.out.println("Lambda (sortowanie po dlugosci): " + names);

        // Klasa - gdy potrzebujesz WLASNEGO STANU (tu: licznik wywolan compare()).
        CallCountingComparator<String> countingComparator = new CallCountingComparator<>(Comparator.<String>naturalOrder());
        names.sort(countingComparator);
        System.out.println("Klasa ze stanem (posortowane alfabetycznie): " + names);
        System.out.println("Liczba wywolan compare() - stan, ktorego lambda by nie 'utrzymala': "
                + countingComparator.getCallCount());
    }

    // ============================================================
    // 🔍 REFLEKSJA vs METHOD HANDLES
    // ============================================================

    private static void demoReflectionVsMethodHandles() {
        System.out.println("\n=== REFLEKSJA vs METHOD HANDLES ===");

        int iterations = 200_000;
        try {
            Constructor<Point> constructor = Point.class.getDeclaredConstructor(int.class, int.class);
            long startReflection = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                constructor.newInstance(i, i);
            }
            long reflectionNanos = System.nanoTime() - startReflection;

            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle handle = lookup.findConstructor(Point.class, MethodType.methodType(void.class, int.class, int.class));
            long startHandle = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                Point point = (Point) handle.invoke(i, i);
            }
            long handleNanos = System.nanoTime() - startHandle;

            System.out.printf("Klasyczna refleksja (Constructor.newInstance): %,d ns / %,d wywolan%n",
                    reflectionNanos, iterations);
            System.out.printf("MethodHandle (invoke):                        %,d ns / %,d wywolan%n",
                    handleNanos, iterations);
            System.out.println("(Mikro-benchmark ILUSTRACYJNY - brak warm-up/JMH - ale kierunek jest typowo");
            System.out.println(" zgodny z regula: MethodHandle >= klasyczna refleksja, zwlaszcza po JIT.)");
        } catch (Throwable t) {
            System.out.println("Demo refleksji/MethodHandle nie powiodlo sie technicznie: " + t);
        }
    }

    // ============================================================
    // 🔹 SEALED + PATTERN MATCHING vs OTWARTY POLIMORFIZM
    // ============================================================

    private static void demoSealedVsOpenPolymorphism() {
        System.out.println("\n=== SEALED+SWITCH (zamkniete) vs POLIMORFIZM (otwarte) ===");

        // ZAMKNIETY zbior - dokladnie 3 znane sposoby platnosci, nic wiecej.
        // Sealed + switch bez 'default' - kompilator PILNUJE kompletnosci.
        PaymentMethod[] payments = {
                new CardPayment("4111-1111-1111-1111"),
                new CashPayment(50.0),
                new BankTransferPayment("PL61109010140000071219812874")
        };
        for (PaymentMethod payment : payments) {
            System.out.printf("Platnosc %-22s -> oplata: %.2f zl%n",
                    payment.getClass().getSimpleName(), calculateFee(payment));
        }
        System.out.println("Gdyby dopisac 4. sposob platnosci do 'permits', powyzszy switch PRZESTALBY sie");
        System.out.println("kompilowac, dopoki nie dopiszesz nowego 'case' - to jest zaleta sealed.");

        // OTWARTY zbior - kazdy (np. autor wtyczki) moze dopisac WLASNY Notifier,
        // BEZ modyfikacji istniejacego kodu. Tu sealed byloby ZLYM wyborem.
        List<Notifier> notifiers = List.of(new EmailNotifier(), new SmsNotifier());
        for (Notifier notifier : notifiers) {
            notifier.send("Zamowienie zostalo zlozone");
        }
        System.out.println("Notifier jest CELOWO otwarty - nowy kanal powiadomien (np. Push) dopisujesz");
        System.out.println("jako NOWA klase implements Notifier, bez dotykania istniejacego kodu.");
    }

    private static double calculateFee(PaymentMethod method) {
        return switch (method) {
            case CardPayment card -> 2.50;
            case CashPayment cash -> 0.0;
            case BankTransferPayment transfer -> 1.00;
        };
    }

    // ============================================================
    // 📦 NIEZMIENNOSC DOMYSLNIE
    // ============================================================

    private static void demoImmutabilityByDefault() {
        System.out.println("\n=== NIEZMIENNOSC DOMYSLNIE ===");

        // MUTOWALNA klasa - alias (dwie zmienne wskazujace na TEN SAM obiekt)
        // to typowe zrodlo bledow, ktorych trudno sie domyslic z samego kodu.
        MutableBudget mutableBudget = new MutableBudget(1000.0);
        MutableBudget aliasReference = mutableBudget;
        aliasReference.setAmount(0.0);
        System.out.println("Mutowalny budzet po zmianie PRZEZ ALIAS: " + mutableBudget.getAmount()
                + " (zmiana 'przeciekla' do drugiej zmiennej!)");

        // NIEZMIENNA klasa (Lekcja 24) - alias przestaje byc problemem,
        // bo nic w obiekcie nie da sie zmienic po utworzeniu.
        ImmutableBudget immutableBudget = new ImmutableBudget(1000.0);
        ImmutableBudget sameImmutableReference = immutableBudget;
        ImmutableBudget afterSpending = sameImmutableReference.withAmount(0.0); // NOWY obiekt
        System.out.println("Niezmienny budzet - oryginal: " + immutableBudget.amount()
                + ", nowy obiekt po 'zmianie': " + afterSpending.amount());
    }

    // ============================================================
    // 🔹 var - KIEDY UZYWAC
    // ============================================================

    private static void demoVarUsage() {
        System.out.println("\n=== var - KIEDY UZYWAC ===");

        // DOBRZE - typ jest oczywisty z prawej strony przypisania.
        var orders = new ArrayList<String>();
        var totalAmount = 0.0;
        var today = LocalDate.now();
        orders.add("Zamowienie #1");
        System.out.println("var, gdy typ jest oczywisty: " + orders + ", suma=" + totalAmount + ", data=" + today);

        /*
         * ZLE (celowo TYLKO w komentarzu - w praktyce psuje czytelnosc):
         *
         * var result = orderService.process(customerId, items, discount);
         * // Jaki typ zwraca process()? Order? Result<Order>? Optional<Order>?
         * // Trzeba zgadywac albo zagladac do sygnatury metody - var TU zabral
         * // wiecej czytelnosci, niz dal skrotowosci.
         */
    }

    // ============================================================
    // 📌 TABELE PODSUMOWUJACE
    // ============================================================

    private static void printAntiPatternsTable() {
        System.out.println("\n=== ANTYWZORCE Z CALEGO ROZDZIALU - SZYBKI PRZEGLAD ===");
        String format = "%-40s | %-46s%n";
        System.out.printf(format, "Antywzorzec", "Dlaczego to problem / co zamiast");
        System.out.println("-".repeat(89));
        System.out.printf(format, "Surowe typy (raw types)", "Brak sprawdzania typow - ClassCastException w runtime");
        System.out.printf(format, "Naduzywanie refleksji", "Wolne, lamie enkapsulacje, brak sprawdzania w compile-time");
        System.out.printf(format, "Mutowalny stan WSPOLDZIELONY", "Aliasing/wyscigi - preferuj niezmiennosc (Lekcja 24)");
        System.out.printf(format, "Nadmiar wildcardow (?)", "API trudne w uzyciu - PECS z rozwaga (Lekcja 5)");
        System.out.printf(format, "switch(sealed) z 'lykajacym' default", "Traci exhaustiveness - nowy podtyp przejdzie CICHO");
    }

    private static void printDecisionMatrix() {
        System.out.println("\n=== MACIERZ DECYZYJNA: KIEDY CZEGO UZYC ===");
        String format = "%-28s | %-58s%n";
        System.out.printf(format, "Temat (lekcje)", "Wybieraj gdy...");
        System.out.println("-".repeat(90));
        System.out.printf(format, "Generics (1-7)", "ZAWSZE zamiast raw types/Object - bez wyjatkow");
        System.out.printf(format, "Lambda (8-11)", "Krotkie, bezstanowe zachowanie, jedna metoda");
        System.out.printf(format, "Pelna klasa (8-11)", "Wiele metod / wlasny mutowalny stan");
        System.out.printf(format, "Refleksja (15-18)", "Framework/narzedzie, elastycznosc wazniejsza niz predkosc");
        System.out.printf(format, "MethodHandle (18)", "Czesto wywolywana sciezka, liczy sie wydajnosc");
        System.out.printf(format, "Sealed+switch (19-22)", "Zamkniety, znany z gory zbior typow - chcesz exhaustiveness");
        System.out.printf(format, "Polimorfizm/visitor", "Otwarta hierarchia - ktos dopisze nowy podtyp/wtyczke");
        System.out.printf(format, "Niezmiennosc (24-25)", "Domyslnie ZAWSZE, chyba ze masz konkretny powod inaczej");
        System.out.printf(format, "var (23)", "Typ oczywisty z prawej strony przypisania");
    }
}

// ==================== POMOCNICZE TYPY DLA DEMO ====================

// --- Lambda vs klasyczne OOP: klasa ze STANEM, ktorego lambda nie ma jak utrzymac ---

class CallCountingComparator<T> implements Comparator<T> {

    private final Comparator<T> delegate;
    private int callCount = 0;

    CallCountingComparator(Comparator<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public int compare(T a, T b) {
        callCount++;
        return delegate.compare(a, b);
    }

    int getCallCount() {
        return callCount;
    }
}

// --- Refleksja vs MethodHandle: prosty typ konstruowany wielokrotnie ---

class Point {
    final int x;
    final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

// --- Sealed+switch: ZAMKNIETY zbior sposobow platnosci (Lekcje 19-22) ---

sealed interface PaymentMethod permits CardPayment, CashPayment, BankTransferPayment {
}

record CardPayment(String cardNumber) implements PaymentMethod {
}

record CashPayment(double amountGiven) implements PaymentMethod {
}

record BankTransferPayment(String iban) implements PaymentMethod {
}

// --- Klasyczny polimorfizm: OTWARTA hierarchia kanalow powiadomien ---

interface Notifier {
    void send(String message);
}

class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[EMAIL] " + message);
    }
}

class SmsNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[SMS] " + message);
    }
}

// --- Niezmiennosc: mutowalna vs niezmienna wersja tego samego pojecia ---

class MutableBudget {
    private double amount;

    MutableBudget(double amount) {
        this.amount = amount;
    }

    void setAmount(double amount) {
        this.amount = amount;
    }

    double getAmount() {
        return amount;
    }
}

record ImmutableBudget(double amount) {
    ImmutableBudget withAmount(double newAmount) {
        return new ImmutableBudget(newAmount);
    }
}
