package com.example.javaquest._01_fundamentals.Lesson16_Exceptions;

import java.io.IOException;
import java.util.NoSuchElementException;

public class _Lesson16_Exceptions {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 CZYM JEST WYJĄTEK?
         * ============================================================
         * Wyjątek (exception) to obiekt reprezentujący sytuację, która
         * przerywa normalny tok wykonania programu – błąd, którego nie
         * dało się (albo nie chciało się) obsłużyć "na miejscu".
         *
         * Gdy w kodzie dzieje się coś nieoczekiwanego (dzielenie przez
         * zero, brak elementu w tablicy, null tam gdzie się go nie
         * spodziewamy), JVM tworzy obiekt wyjątku i "rzuca" go w górę
         * stosu wywołań, aż ktoś go złapie (catch) albo program się
         * zakończy z błędem (stack trace w konsoli).
         *
         * 🔹 ANALOGIA
         * Metoda A wywołuje B, B wywołuje C. Jeśli C napotka problem,
         * którego nie potrafi obsłużyć, "podaje go wyżej" do B. Jeśli B
         * też nie wie co z nim zrobić – podaje wyżej do A. Jeśli nikt
         * po drodze go nie złapie, program się wywraca (kończy z błędem).
         */

        System.out.println("=== PRZYKŁAD: NIEZŁAPANY WYJĄTEK (zakomentowany) ===");
        // int wynik = 10 / 0; // rzuciłoby ArithmeticException i zakończyło main()
        System.out.println("(gdyby powyższa linia była odkomentowana, program zakończyłby się błędem)");

        /*
         * ============================================================
         * 🔹 HIERARCHIA WYJĄTKÓW – Throwable
         * ============================================================
         *                    Throwable
         *                   /         \
         *              Error         Exception
         *             (JVM/system)      |
         *                        /              \
         *              RuntimeException      (pozostałe – CHECKED)
         *              (UNCHECKED)           np. IOException,
         *              np. NullPointerException, InterruptedException
         *              ArithmeticException,
         *              ArrayIndexOutOfBoundsException...
         *
         * - Throwable – wspólny przodek WSZYSTKIEGO, co można rzucić/złapać.
         * - Error – poważne problemy JVM/systemu (OutOfMemoryError,
         *   StackOverflowError). Zwykle NIE łapiemy ich – program i tak
         *   nie ma jak sensownie kontynuować.
         * - Exception – błędy na poziomie aplikacji, z którymi zwykle
         *   MOŻNA coś zrobić (obsłużyć, zalogować, spróbować ponownie).
         * - RuntimeException i jej podklasy – wyjątki UNCHECKED (patrz niżej).
         * - Pozostałe podklasy Exception (nie RuntimeException) – CHECKED.
         */

        /*
         * ============================================================
         * 🔍 CHECKED vs UNCHECKED – KLUCZOWA RÓŻNICA
         * ============================================================
         * CHECKED (sprawdzane przez kompilator):
         * - Dziedziczą po Exception, ale NIE po RuntimeException.
         * - Kompilator WYMAGA, żeby je złapać (try-catch) albo zadeklarować
         *   w sygnaturze metody (throws).
         * - Przykłady: IOException, InterruptedException, SQLException.
         * - Sens: reprezentują sytuacje, których wystąpienia realnie
         *   trzeba się spodziewać (plik może nie istnieć, sieć może
         *   nie odpowiedzieć) – kompilator wymusza świadomą reakcję.
         *
         * UNCHECKED (niesprawdzane przez kompilator):
         * - Dziedziczą po RuntimeException (albo bezpośrednio po Error).
         * - Kompilator NIE wymaga ich łapania ani deklarowania.
         * - Przykłady: NullPointerException, ArithmeticException,
         *   ArrayIndexOutOfBoundsException, IllegalArgumentException,
         *   ClassCastException, NumberFormatException.
         * - Sens: zwykle reprezentują BŁĘDY PROGRAMISTY (zły warunek,
         *   brak walidacji) – "nie powinny" się zdarzać przy poprawnym
         *   kodzie, więc nie zaśmiecamy każdej linijki try-catchem.
         */

        System.out.println("\n=== CHECKED vs UNCHECKED ===");
        try {
            throw new IOException("przykladowy checked wyjatek");
        } catch (IOException e) {
            System.out.println("Zlapano checked: " + e.getMessage());
        }

        try {
            int[] tablica = new int[3];
            System.out.println(tablica[10]); // ArrayIndexOutOfBoundsException - unchecked
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Zlapano unchecked: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔹 TRY-CATCH-FINALLY
         * ============================================================
         * try    – blok kodu, który MOŻE rzucić wyjątek.
         * catch  – blok obsługujący konkretny typ wyjątku (może być ich
         *          wiele, sprawdzane od góry do dołu, od najbardziej
         *          szczegółowego do najbardziej ogólnego).
         * finally – blok wykonywany ZAWSZE (czy był wyjątek, czy nie,
         *           nawet jeśli w try/catch było return!). Typowo używany
         *           do sprzątania zasobów (choć try-with-resources –
         *           poznane w rozdziale I/O – jest lepszym rozwiązaniem
         *           dla zasobów typu Closeable).
         */

        System.out.println("\n=== TRY-CATCH-FINALLY ===");
        System.out.println("Wynik metody: " + przykladFinally(0));
        System.out.println("Wynik metody: " + przykladFinally(10));

        /*
         * ============================================================
         * 🔍 WIELOKROTNY CATCH I MULTI-CATCH
         * ============================================================
         * Można mieć wiele bloków catch pod jednym try – JVM sprawdza je
         * PO KOLEI i wykonuje PIERWSZY pasujący. Dlatego bardziej
         * szczegółowe wyjątki muszą być WYŻEJ niż ich nadklasy
         * (inaczej kompilator zgłosi błąd "unreachable catch block").
         *
         * Multi-catch (od Java 7) – jeśli kilka typów wyjątków ma być
         * obsłużonych IDENTYCZNIE, można je połączyć znakiem |.
         */

        System.out.println("\n=== WIELOKROTNY CATCH / MULTI-CATCH ===");
        String[] wartosci = {"12", "abc", null};
        for (String wartosc : wartosci) {
            try {
                int liczba = Integer.parseInt(wartosc);
                System.out.println("Sparsowano: " + liczba);
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Blad parsowania dla '" + wartosc + "': " + e.getClass().getSimpleName());
            }
        }

        /*
         * ============================================================
         * 🔹 THROW vs THROWS
         * ============================================================
         * throw   – SŁOWO KLUCZOWE UŻYWANE W CIELE metody, by faktycznie
         *           rzucić konkretny obiekt wyjątku: throw new IOException(...);
         * throws  – DEKLARACJA W SYGNATURZE metody informująca, że metoda
         *           MOŻE rzucić dany checked wyjątek (kompilator wymusi
         *           to na wywołujących – muszą go złapać albo też
         *           zadeklarować throws).
         */

        System.out.println("\n=== THROW vs THROWS ===");
        try {
            sprawdzWiek(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Zlapano throw: " + e.getMessage());
        }

        try {
            wczytajDaneRyzykowne();
        } catch (IOException e) {
            System.out.println("Zlapano wyjatek zadeklarowany przez throws: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔍 WŁASNE WYJĄTKI (CUSTOM EXCEPTIONS)
         * ============================================================
         * Własną klasę wyjątku tworzymy dziedzicząc po Exception (checked)
         * lub RuntimeException (unchecked). Dobra praktyka: dodać
         * konstruktor z komunikatem (String message) oraz opcjonalnie
         * z przyczyną (Throwable cause) – patrz niżej "chaining".
         */

        System.out.println("\n=== WŁASNY WYJĄTEK ===");
        try {
            wyplacPieniadze(100, 250);
        } catch (NiewystarczajaceSrodkiException e) {
            System.out.println("Zlapano wlasny wyjatek: " + e.getMessage());
        }

        /*
         * ============================================================
         * 🔹 ŁAŃCUCHOWANIE WYJĄTKÓW (EXCEPTION CHAINING)
         * ============================================================
         * Czasem łapiemy wyjątek niskiego poziomu (np. techniczny,
         * z bazy danych) i chcemy rzucić inny, bardziej "sensowny"
         * z punktu widzenia wywołującego – ale BEZ TRACENIA oryginalnej
         * przyczyny. Do tego służy konstruktor przyjmujący cause
         * (Throwable) – oryginalny wyjątek trafia wtedy do stack trace
         * jako "Caused by: ...".
         */

        System.out.println("\n=== ŁAŃCUCHOWANIE WYJĄTKÓW ===");
        try {
            operacjaWysokopoziomowa();
        } catch (RuntimeException e) {
            System.out.println("Zlapano: " + e.getMessage());
            System.out.println("Przyczyna (cause): " + e.getCause());
        }

        /*
         * ============================================================
         * 🔍 STACK TRACE
         * ============================================================
         * Każdy wyjątek niesie ze sobą "ślad stosu" – listę metod,
         * przez które przeszedł zanim został rzucony. printStackTrace()
         * wypisuje go na System.err. getMessage() zwraca sam komunikat,
         * getStackTrace() zwraca go jako tablicę StackTraceElement
         * (przydatne do logowania w bardziej ustrukturyzowany sposób).
         */

        System.out.println("\n=== STACK TRACE ===");
        try {
            poziomA();
        } catch (RuntimeException e) {
            System.out.println("Komunikat: " + e.getMessage());
            StackTraceElement[] slad = e.getStackTrace();
            System.out.println("Pierwsza klatka stosu: " + slad[0]);
            System.out.println("Liczba klatek w sladzie: " + slad.length);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Throwable = Error (JVM/system, nie łapiemy) + Exception
         * - Exception dzieli się na CHECKED (kompilator wymusza obsługę,
         *   np. IOException) i UNCHECKED = RuntimeException (błędy
         *   programisty, np. NullPointerException) – nie trzeba deklarować
         * - try-catch-finally: finally wykonuje się ZAWSZE
         * - throw rzuca konkretny obiekt wyjątku; throws deklaruje
         *   w sygnaturze metody, że checked wyjątek może wylecieć wyżej
         * - własne wyjątki: dziedzicz po Exception lub RuntimeException,
         *   dodaj konstruktor z message (i opcjonalnie cause)
         * - łańcuchowanie (cause) zachowuje oryginalną przyczynę błędu
         * - stack trace pokazuje ścieżkę wywołań, które doprowadziły
         *   do rzucenia wyjątku
         */
    }

    private static int przykladFinally(int dzielnik) {
        try {
            int wynik = 100 / dzielnik;
            System.out.println("Dzielenie sie udalo: " + wynik);
            return wynik;
        } catch (ArithmeticException e) {
            System.out.println("Zlapano dzielenie przez zero");
            return -1;
        } finally {
            System.out.println("Blok finally – wykonuje sie zawsze");
        }
    }

    private static void sprawdzWiek(int wiek) {
        if (wiek < 0) {
            throw new IllegalArgumentException("Wiek nie moze byc ujemny: " + wiek);
        }
        System.out.println("Wiek poprawny: " + wiek);
    }

    private static void wczytajDaneRyzykowne() throws IOException {
        // metoda deklaruje "throws IOException" - wywolujacy MUSI ja obsluzyc
        throw new IOException("symulowany blad odczytu danych");
    }

    private static void wyplacPieniadze(double stanKonta, double kwota) throws NiewystarczajaceSrodkiException {
        if (kwota > stanKonta) {
            throw new NiewystarczajaceSrodkiException(
                    "Brak srodkow: stan konta " + stanKonta + ", proba wyplaty " + kwota);
        }
        System.out.println("Wyplacono: " + kwota);
    }

    private static void operacjaWysokopoziomowa() {
        try {
            operacjaNiskopoziomowa();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Nie udalo sie wykonac operacji wysokopoziomowej", e);
        }
    }

    private static void operacjaNiskopoziomowa() {
        throw new NoSuchElementException("brak elementu w zrodle danych");
    }

    private static void poziomA() {
        poziomB();
    }

    private static void poziomB() {
        poziomC();
    }

    private static void poziomC() {
        throw new RuntimeException("blad na najglebszym poziomie");
    }

    /**
     * Przykład własnego, checked wyjątku (dziedziczy po Exception).
     */
    static class NiewystarczajaceSrodkiException extends Exception {
        public NiewystarczajaceSrodkiException(String message) {
            super(message);
        }
    }
}
