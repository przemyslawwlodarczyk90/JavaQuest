package com.example.javaquest._16_clean_code.Lesson17_ExceptionDesign;

public class _Lesson17_ExceptionDesign {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: CZYSTE PROJEKTOWANIE WYJATKOW ===");

        /*
         * ============================================================
         * 📦 TA LEKCJA ZAKLADA ZNAJOMOSC PODSTAW WYJATKOW
         * ============================================================
         * - Mechanike try-catch-finally, checked vs unchecked, throw/throws,
         *   wlasne wyjatki, lancuchowanie (cause) i stack trace poznales w
         *   `_01_fundamentals/Lesson16_Exceptions` - TUTAJ NIE powtarzamy tej
         *   skladni, tylko pytamy: jak projektowac wyjatki tak, by kod byl
         *   CZYTELNY i LATWY W UTRZYMANIU (perspektywa Clean Code).
         */
        System.out.println("Zaklada znajomosc _01_fundamentals/Lesson16_Exceptions - tu nacisk na JAKOSC projektu, nie skladnie.");

        /*
         * ============================================================
         * 🔹 ZASADA 1: WYJATKI ZAMIAST KODOW BLEDOW
         * ============================================================
         * - Stary styl (jeszcze z jezykow bez wyjatkow, jak C): funkcja
         *   zwraca specjalny "kod bledu" (np. -1, null), a WOLUJACY MUSI
         *   pamietac, zeby go sprawdzic PO KAZDYM wywolaniu - latwo
         *   zapomniec, a bledy "milkna" w nieskonczonosc.
         * - Wyjatki ROZDZIELAJA logike "co sie robi w przypadku sukcesu"
         *   od logiki "co sie robi w przypadku bledu" - kod glownej
         *   sciezki (happy path) NIE jest zaszumiony sprawdzeniami kodu
         *   bledu po kazdej linii.
         */
        demonstrateErrorCodesVsExceptions();

        /*
         * ============================================================
         * 🔍 ZASADA 2: UNCHECKED ZAMIAST CHECKED (KONTROWERSJA JAVY)
         * ============================================================
         * - Robert C. Martin (Clean Code): checked exceptions LAMIA
         *   Open/Closed Principle (Lesson08) - dodanie `throws NowyWyjatek`
         *   do metody niskiego poziomu wymusza dodanie go do SYGNATUR
         *   WSZYSTKICH metod posrednich az do gory stosu wywolan, nawet
         *   jesli same go nie obsluguja.
         * - W praktyce nowoczesnej Javy (i wiekszosc bibliotek/frameworkow,
         *   np. Spring) preferuje sie UNCHECKED (`RuntimeException`) dla
         *   bledow, ktorych wolujacy zwykle NIE MOZE sensownie
         *   "naprawic" w miejscu wywolania (np. blad walidacji danych,
         *   naruszenie reguly biznesowej).
         * - Checked exceptions maja nadal sens dla bledow, ktore wolujacy
         *   MOZE i POWINIEN obsluzyc lokalnie (np. `IOException` - "sprobuj
         *   ponownie" czy "pokaz komunikat uzytkownikowi" to realne,
         *   rozne akcje w ROZNYCH miejscach wywolania).
         */
        System.out.println("\nJava wymusza checked exceptions, ale Clean Code (Martin) zaleca UNCHECKED tam, gdzie wolujacy i tak nie naprawi bledu lokalnie.");

        /*
         * ============================================================
         * 🔹 ZASADA 3: KOMUNIKAT WYJATKU Z PELNYM KONTEKSTEM
         * ============================================================
         * - Goly komunikat "Blad walidacji" NIC nie mowi o TYM, CO
         *   konkretnie zawiodlo - dobry komunikat zawiera KONKRETNE
         *   wartosci/pola, ktore spowodowaly problem.
         * - Zasada: komunikat wyjatku powinien wystarczyc, zeby
         *   zdiagnozowac przyczyne BEZ debuggera - kto/co/dlaczego.
         */
        demonstrateVagueVsContextualMessage();

        /*
         * ============================================================
         * 🔍 ZASADA 4: WLASNE WYJATKI DEFINIOWANE Z PERSPEKTYWY WOLUJACEGO
         * ============================================================
         * - Nazwa i hierarchia wyjatku powinny odzwierciedlac, JAK
         *   WOLUJACY chce go OBSLUZYC, a nie SZCZEGOL implementacyjny
         *   miejsca, gdzie powstal.
         * - Przyklad: `InsufficientFundsException` (odzwierciedla regule
         *   BIZNESOWA - "za malo srodkow") jest lepszy niz
         *   `SqlConstraintViolationException` (odzwierciedla szczegol
         *   TECHNICZNY bazy danych) - wolujacy z logiki biznesowej NIE
         *   powinien wiedziec, ze pod spodem jest SQL.
         */
        demonstrateCallerPerspectiveExceptionDesign();

        /*
         * ============================================================
         * 🔹 ZASADA 5: TLUMACZENIE WYJATKOW (EXCEPTION TRANSLATION)
         * ============================================================
         * - Gdy niskopoziomowy szczegol (np. baza danych, plik, siec)
         *   rzuca WLASNY, techniczny wyjatek, warstwa WYZSZEGO poziomu
         *   powinna go ZLAPAC i "przetlumaczyc" na wyjatek ZNACZACY
         *   COS w JEJ WLASNYM jezyku (domenie biznesowej) - ZAWSZE z
         *   zachowaniem oryginalnej przyczyny (`cause`, poznanej w
         *   Lesson16 `_01_fundamentals`) do celow debugowania.
         * - To jest DOKLADNIE ta sama idea co DIP (Lesson11) - warstwa
         *   wysokopoziomowa nie powinna "przeciekac" szczegolami
         *   niskopoziomowymi, WLACZNIE z typami wyjatkow.
         */
        demonstrateExceptionTranslation();

        /*
         * ============================================================
         * 🔍 ZASADA 6: NIE UZYWAJ WYJATKOW DO STEROWANIA PRZEPLYWEM
         * ============================================================
         * - Wyjatek to mechanizm dla sytuacji WYJATKOWYCH (stad nazwa) -
         *   NIE nalezy uzywac go jako "specjalnego rodzaju return" w
         *   normalnym, oczekiwanym przeplywie logiki (np. rzucanie
         *   wyjatku, zeby "wyjsc" z glebokiej petli zamiast po prostu
         *   uzyc `break`/`return`).
         * - Tworzenie wyjatku (zbieranie stack trace'u) ma REALNY koszt
         *   wydajnosciowy - uzywanie go jako "zwyklego" mechanizmu
         *   sterowania jest zarowno nieczytelne, jak i wolniejsze niz
         *   zwykle instrukcje sterujace.
         */
        demonstrateExceptionsForControlFlowAntiPattern();

        /*
         * ============================================================
         * 🔹 ZASADA 7: NIE LAP ZBYT OGOLNEGO TYPU
         * ============================================================
         * - `catch (Exception e)` (albo gorzej: `catch (Throwable t)`)
         *   lapie WSZYSTKO - wlacznie z bledami PROGRAMISTY (np.
         *   `NullPointerException`, `ArrayIndexOutOfBoundsException`),
         *   ktore powinny byc NAPRAWIONE, a nie po cichu polkniete.
         * - Zasada: lap NAJWEZSZY typ wyjatku, ktory realnie mozesz
         *   OBSLUZYC w danym miejscu - reszta powinna "przeciekac" wyzej,
         *   az do miejsca, gdzie MOZNA ja sensownie obsluzyc (lub do
         *   globalnego handlera na samej granicy aplikacji).
         */
        demonstrateCatchingTooBroadAntiPattern();

        /*
         * ============================================================
         * 🔍 ZASADA 8: JEDEN POZIOM ABSTRAKCJI - MALE BLOKI TRY-CATCH
         * ============================================================
         * - Blok `try` powinien zawierac TYLKO 1 operacje, ktora
         *   REALNIE moze rzucic wyjatek - reszta logiki (przetwarzanie
         *   wyniku) powinna byc WYDZIELONA do osobnej metody (patrz
         *   Extract Method, Lesson16).
         * - Duzy blok `try` z wieloma operacjami utrudnia zrozumienie,
         *   KTORA DOKLADNIE linia rzucila zlapany wyjatek.
         */
        demonstrateSingleResponsibilityTryBlock();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wyjatki zamiast kodow bledow - oddzielaja happy path od
         *   obslugi bledow.
         * - Unchecked preferowane tam, gdzie wolujacy i tak nie naprawi
         *   bledu lokalnie (kontrowersja checked exceptions, Martin).
         * - Komunikaty z pelnym kontekstem - diagnoza bez debuggera.
         * - Wlasne wyjatki nazwane z perspektywy WOLUJACEGO, nie
         *   implementacji.
         * - Tlumaczenie wyjatkow (exception translation) miedzy warstwami,
         *   z zachowaniem `cause`.
         * - Wyjatki NIE do sterowania przeplywem - to realny koszt i
         *   nieczytelnosc.
         * - Lap najwezszy mozliwy typ, nie `Exception`/`Throwable`.
         * - Male, jednowierszowe bloki `try` - 1 poziom abstrakcji.
         * - W kolejnej lekcji (Lesson18): obsluga `null` - blisko
         *   powiazany temat (fail-fast throw zamiast zwracania null).
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static void demonstrateErrorCodesVsExceptions() {
        System.out.println("\n=== KODY BLEDOW VS WYJATKI ===");

        int errorCode = divideWithErrorCode(10, 0);
        if (errorCode == Integer.MIN_VALUE) {
            System.out.println("[KODY BLEDOW] Wykryto blad przez SPECJALNA wartosc (Integer.MIN_VALUE) - latwo przeoczyc sprawdzenie.");
        }

        try {
            int result = divideWithException(10, 0);
            System.out.println("Wynik: " + result);
        } catch (ArithmeticException e) {
            System.out.println("[WYJATKI] Blad ROZDZIELONY od logiki glownej sciezki: " + e.getMessage());
        }
    }

    private static int divideWithErrorCode(int a, int b) {
        if (b == 0) {
            return Integer.MIN_VALUE; // "kod bledu" - wolujacy MUSI pamietac o sprawdzeniu
        }
        return a / b;
    }

    private static int divideWithException(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Dzielenie przez zero: " + a + " / " + b);
        }
        return a / b;
    }

    private static void demonstrateVagueVsContextualMessage() {
        System.out.println("\n=== KOMUNIKAT OGOLNIKOWY VS KOMUNIKAT Z KONTEKSTEM ===");

        try {
            validateAgeVague(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("[OGOLNIKOWY] " + e.getMessage() + " -> nie wiadomo, jaka wartosc zawiodla");
        }

        try {
            validateAgeWithContext(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("[Z KONTEKSTEM] " + e.getMessage());
        }
    }

    private static void validateAgeVague(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Nieprawidlowy wiek");
        }
    }

    private static void validateAgeWithContext(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException(
                    "Nieprawidlowy wiek: " + age + " (dozwolony zakres: 0-150)");
        }
    }

    private static void demonstrateCallerPerspectiveExceptionDesign() {
        System.out.println("\n=== WYJATEK NAZWANY Z PERSPEKTYWY WOLUJACEGO, NIE IMPLEMENTACJI ===");

        try {
            withdrawFromAccount(100.0, 500.0);
        } catch (InsufficientFundsException e) {
            System.out.println("[DOBRA NAZWA - domena biznesowa] " + e.getMessage());
            System.out.println("-> Kod wywolujacy NIE wie i NIE musi wiedziec, ze pod spodem 'jest baza danych'.");
        }
    }

    /** Wyjatek nazwany JEZYKIEM DOMENY BIZNESOWEJ - to jest, co wolujacy naprawde chce wiedziec. */
    static class InsufficientFundsException extends RuntimeException {
        InsufficientFundsException(String message) {
            super(message);
        }
    }

    private static void withdrawFromAccount(double balance, double requestedAmount) {
        if (requestedAmount > balance) {
            throw new InsufficientFundsException(
                    "Za malo srodkow: saldo=" + balance + ", zadana kwota=" + requestedAmount);
        }
    }

    private static void demonstrateExceptionTranslation() {
        System.out.println("\n=== TLUMACZENIE WYJATKOW (EXCEPTION TRANSLATION) ===");

        try {
            loadUserProfile("user-404");
        } catch (UserProfileLoadException e) {
            System.out.println("[WARSTWA WYSOKA] " + e.getMessage());
            System.out.println("Przyczyna techniczna (cause) nadal dostepna dla debugowania: "
                    + e.getCause().getClass().getSimpleName() + " - " + e.getCause().getMessage());
        }
    }

    /** Symulacja niskopoziomowego, technicznego wyjatku (np. z warstwy dostepu do danych). */
    static class LowLevelStorageException extends RuntimeException {
        LowLevelStorageException(String message) {
            super(message);
        }
    }

    /** Wyjatek WYSOKIEGO poziomu - to, co widzi wolujacy z logiki biznesowej. */
    static class UserProfileLoadException extends RuntimeException {
        UserProfileLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static void loadUserProfile(String userId) {
        try {
            readFromStorage(userId);
        } catch (LowLevelStorageException technicalError) {
            // Tlumaczenie: technicznego wyjatku NIE puszczamy dalej "jak jest" -
            // opakowujemy go w znaczacy dla wolujacego wyjatek, zachowujac cause.
            throw new UserProfileLoadException(
                    "Nie udalo sie wczytac profilu uzytkownika " + userId, technicalError);
        }
    }

    private static void readFromStorage(String userId) {
        throw new LowLevelStorageException("Rekord o kluczu '" + userId + "' nie istnieje w tabeli users");
    }

    private static void demonstrateExceptionsForControlFlowAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: WYJATKI JAKO STEROWANIE PRZEPLYWEM ===");

        int[] numbers = {1, 5, 8, 12, 3};

        // ZLA: uzycie wyjatku do "wyjscia" z petli po znalezieniu wartosci - normalny przypadek, nie wyjatkowy.
        try {
            findFirstEvenBad(numbers);
        } catch (FoundValueSignal signal) {
            System.out.println("[ZLA - wyjatek jako sterowanie] znaleziono: " + signal.value);
        }

        // DOBRA: zwykly return - wyjatek nie jest tu w ogole potrzebny.
        Integer found = findFirstEvenGood(numbers);
        System.out.println("[DOBRA - zwykly return] znaleziono: " + found);
    }

    /** ZLA: wyjatek uzyty jako "specjalny return" - myli czytelnika i ma zbedny koszt (stack trace). */
    static class FoundValueSignal extends RuntimeException {
        final int value;

        FoundValueSignal(int value) {
            this.value = value;
        }
    }

    private static void findFirstEvenBad(int[] numbers) {
        for (int n : numbers) {
            if (n % 2 == 0) {
                throw new FoundValueSignal(n);
            }
        }
    }

    private static Integer findFirstEvenGood(int[] numbers) {
        for (int n : numbers) {
            if (n % 2 == 0) {
                return n;
            }
        }
        return null;
    }

    private static void demonstrateCatchingTooBroadAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: LAPANIE ZBYT OGOLNEGO TYPU ===");

        System.out.println("[ZLA] catch (Exception e) polyka WSZYSTKO, wlacznie z bledami programisty:");
        try {
            triggerNullPointerBug();
        } catch (Exception e) { // zbyt szeroki catch - polyka i NPE, i realny blad logiki
            System.out.println("  Zlapano: " + e.getClass().getSimpleName() + " - ukryty PRAWDZIWY blad w kodzie!");
        }

        System.out.println("[DOBRA] catch (SpecificException e) lapie TYLKO to, co realnie wiemy jak obsluzyc:");
        try {
            validateAgeWithContext(-1);
        } catch (IllegalArgumentException e) { // konkretny, oczekiwany typ
            System.out.println("  Zlapano oczekiwany blad walidacji: " + e.getMessage());
        }
    }

    private static void triggerNullPointerBug() {
        String value = null;
        value.length(); // realny blad programisty - NIE powinien byc "po cichu" polykany przez catch(Exception)
    }

    private static void demonstrateSingleResponsibilityTryBlock() {
        System.out.println("\n=== JEDEN POZIOM ABSTRAKCJI W BLOKU TRY ===");

        System.out.println("[ZLA] duzy blok try z wieloma operacjami:");
        processOrderBadTryBlock("12345", -1);

        System.out.println("[DOBRA] maly blok try (1 operacja) + wydzielona logika:");
        processOrderGoodTryBlock("12345", -1);
    }

    /** ZLA: caly przeplyw (parsowanie + walidacja + obliczenia) w JEDNYM duzym bloku try. */
    private static void processOrderBadTryBlock(String orderId, int quantity) {
        try {
            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity musi byc > 0, otrzymano: " + quantity);
            }
            double total = quantity * 10.0;
            System.out.println("  Zamowienie " + orderId + " OK, suma=" + total);
        } catch (IllegalArgumentException e) {
            System.out.println("  Blad (z duzego bloku try, trudno wskazac KTORA linia): " + e.getMessage());
        }
    }

    /** DOBRA: try zawiera TYLKO operacje, ktora realnie rzuca - reszta wydzielona (Extract Method, Lesson16). */
    private static void processOrderGoodTryBlock(String orderId, int quantity) {
        try {
            validateQuantity(quantity);
        } catch (IllegalArgumentException e) {
            System.out.println("  Blad walidacji (jasno zlokalizowany): " + e.getMessage());
            return;
        }
        double total = calculateOrderTotal(orderId, quantity);
        System.out.println("  Zamowienie " + orderId + " OK, suma=" + total);
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity musi byc > 0, otrzymano: " + quantity);
        }
    }

    private static double calculateOrderTotal(String orderId, int quantity) {
        return quantity * 10.0;
    }
}
