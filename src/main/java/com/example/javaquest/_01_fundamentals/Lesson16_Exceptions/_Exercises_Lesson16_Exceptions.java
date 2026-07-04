package com.example.javaquest._01_fundamentals.Lesson16_Exceptions;

public class _Exercises_Lesson16_Exceptions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CatchArithmeticException {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj int a = 10, int b = 0. Spróbuj wykonać a / b w bloku try
         * i złap ArithmeticException, wypisując komunikat wyjątku (getMessage()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CatchArrayIndexOutOfBounds {
        /*
         * 🧪 Zadanie 2:
         * Utwórz tablicę int[] liczby = {1, 2, 3}. W bloku try spróbuj odczytać
         * liczby[5] i złap ArrayIndexOutOfBoundsException, wypisując jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CatchNullPointerException {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj String tekst = null. W bloku try wywołaj tekst.length()
         * i złap NullPointerException, wypisując nazwę klasy wyjątku
         * (e.getClass().getSimpleName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CatchNumberFormatException {
        /*
         * 🧪 Zadanie 4:
         * Spróbuj sparsować String "abc123" przez Integer.parseInt().
         * Złap NumberFormatException i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FinallyAlwaysRuns {
        /*
         * 🧪 Zadanie 5:
         * Napisz metodę test(int dzielnik), która w try dzieli 100 / dzielnik
         * i wypisuje wynik, w catch (ArithmeticException) wypisuje "blad",
         * a w finally zawsze wypisuje "koniec proby". Wywołaj metodę
         * dla dzielnik = 5 oraz dzielnik = 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleCatchBlocks {
        /*
         * 🧪 Zadanie 6:
         * Napisz metodę przetworz(String wejscie), która próbuje sparsować
         * wejście na int i podzielić 100 przez tę liczbę. Obsłuż osobno
         * NumberFormatException (komunikat "zle dane") i ArithmeticException
         * (komunikat "dzielenie przez zero"). Wywołaj ją dla "10", "0", "xyz".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ThrowIllegalArgument {
        /*
         * 🧪 Zadanie 7:
         * Napisz metodę ustawWiek(int wiek), która rzuca
         * IllegalArgumentException z komunikatem "Wiek musi byc >= 0 i <= 150",
         * jeśli wiek jest spoza tego zakresu. Wywołaj ją z wartościami -5, 200
         * i 30, łapiąc wyjątek tam, gdzie zostanie rzucony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ThrowsDeclaration {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę sprawdzHaslo(String haslo) throws Exception, która rzuca
         * Exception z komunikatem "Haslo za krotkie", jeśli haslo.length() < 8.
         * Wywołaj ją w main w bloku try-catch dla hasła "abc" i "bezpieczneHaslo123".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ClassCastException {
        /*
         * 🧪 Zadanie 9:
         * Utwórz Object obiekt = "tekst". Spróbuj rzutować go na Integer
         * ((Integer) obiekt) w bloku try i złap ClassCastException,
         * wypisując komunikat wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExceptionHierarchyCheck {
        /*
         * 🧪 Zadanie 10:
         * Utwórz tablicę Exception[] wyjatki zawierającą: new NullPointerException(),
         * new ArithmeticException(), new IOException() (java.io.IOException),
         * new RuntimeException(). Dla każdego elementu wypisz, czy jest instancją
         * RuntimeException (instanceof) – w ten sposób sprawdź, które są checked,
         * a które unchecked.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CustomCheckedException {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj własny checked wyjątek NieprawidlowyPeselException
         * (dziedziczy po Exception) z konstruktorem przyjmującym String message.
         * Napisz metodę waliduj(String pesel) throws NieprawidlowyPeselException,
         * rzucającą go, gdy pesel.length() != 11. Wywołaj dla "12345" i "12345678901".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CustomUncheckedException {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj własny unchecked wyjątek NieprawidlowyStanKontaException
         * (dziedziczy po RuntimeException) z konstruktorem przyjmującym String.
         * Napisz metodę wyplac(double stan, double kwota), rzucającą go,
         * gdy kwota > stan. Wywołaj dla stan=100, kwota=50 oraz stan=100, kwota=300.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExceptionChaining {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę niskopoziomowa(), rzucającą new NumberFormatException("zle dane").
         * Napisz metodę wysokopoziomowa(), która łapie ten wyjątek i rzuca nowy
         * RuntimeException("Blad przetwarzania", cause) z oryginalnym wyjątkiem
         * jako przyczyną. W main złap RuntimeException i wypisz zarówno getMessage(),
         * jak i getCause().
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultiCatchWithPipe {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę parsujDwieLiczby(String a, String b), sumującą
         * Integer.parseInt(a) + Integer.parseInt(b) i dzielącą wynik przez
         * (a.length() - b.length()). Obsłuż w JEDNYM catch (multi-catch z |)
         * zarówno NumberFormatException, jak i ArithmeticException, wypisując
         * "Blad danych wejsciowych: " + nazwa klasy wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RethrowAfterLogging {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę przetworz(int wartosc), która w try dzieli 1000/wartosc,
         * a w catch (ArithmeticException e) najpierw wypisuje "Log: " + e.getMessage(),
         * a następnie RZUCA TEN SAM wyjątek dalej (rethrow). W main wywołaj ją
         * dla wartosc=0 w kolejnym try-catch, łapiąc ponownie rzucony wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TryCatchInLoop {
        /*
         * 🧪 Zadanie 16:
         * Dla tablicy String[] dane = {"10", "20", "abc", "0", "5"}, w pętli
         * spróbuj podzielić 100 przez każdą sparsowaną wartość. Złap zarówno
         * NumberFormatException, jak i ArithmeticException OSOBNYMI catch,
         * tak by pętla kontynuowała działanie dla kolejnych elementów mimo
         * błędów, i na końcu wypisz liczbę udanych i nieudanych operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ValidationWithMultipleExceptions {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj dwa własne unchecked wyjątki: PustyStringException
         * i ZaDlugiStringException. Napisz metodę walidujOpis(String opis),
         * rzucającą PustyStringException gdy opis jest null lub puste,
         * a ZaDlugiStringException gdy opis.length() > 50. Przetestuj
         * dla null, "", i bardzo długiego stringa (np. "a".repeat(60)),
         * łapiąc każdy wyjątek osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FinallyWithReturn {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę int test(), która w try robi return 1, w catch
         * (nigdy nie zostanie wywołany, bo try się nie wywala) robi return 2,
         * a w finally wykonuje return 3. Wypisz w main wynik wywołania test()
         * i wyjaśnij w komentarzu (println), dlaczego finally "wygrywa" nad
         * return z bloku try.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_StackTraceInspection {
        /*
         * 🧪 Zadanie 19:
         * Napisz 3 metody wywołujące się kaskadowo: poziom1() -> poziom2() ->
         * poziom3(), gdzie poziom3() rzuca RuntimeException("blad glebinowy").
         * W main złap ten wyjątek, wypisz liczbę elementów w getStackTrace()
         * oraz nazwę metody z pierwszego elementu stack trace
         * (getStackTrace()[0].getMethodName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ResourceCleanupWithFinally {
        /*
         * 🧪 Zadanie 20:
         * Zasymuluj "zasób" jako boolean[] otwarty = {true}. Napisz metodę
         * uzyjZasobu(boolean rzucBlad), która w try (ewentualnie) rzuca
         * RuntimeException("awaria"), a w finally ustawia otwarty[0] = false
         * i wypisuje "Zasob zamkniety". Wywołaj metodę raz z rzucBlad=true
         * (w try-catch) i raz z rzucBlad=false, za każdym razem sprawdzając
         * na końcu wartość otwarty[0].
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CustomExceptionHierarchy {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj małą hierarchię własnych wyjątków: bazowy checked
         * SklepException, oraz dwie podklasy BrakTowaruException
         * i NieprawidlowaPlatnoscException. Napisz metodę zlozZamowienie(
         * String produkt, int iloscDostepna, int iloscZamowiona,
         * boolean platnoscOk) throws SklepException, rzucającą odpowiedni
         * podtyp w zależności od warunku. W main złap wszystko jako
         * SklepException, ale wypisz REALNĄ klasę złapanego wyjątku
         * (getClass().getSimpleName()) dla kilku różnych wywołań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RetryWithLimitedAttempts {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę symulującą niestabilne połączenie: pobierzDane(int proba),
         * rzucającą RuntimeException("timeout") gdy proba < 3, a zwracającą
         * String "dane OK" gdy proba >= 3. W main zaimplementuj pętlę retry
         * z MAKSYMALNIE 5 próbami (licznik!), łapiącą wyjątek i próbującą
         * ponownie, aż się uda albo skończą się próby – wypisz wynik końcowy
         * (sukces albo "poddano sie po N probach").
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AggregatingErrorsFromBatch {
        /*
         * 🧪 Zadanie 23:
         * Dla tablicy String[] wpisy = {"10", "-5", "abc", "20", "", "7"},
         * napisz metodę waliduj(String wpis), rzucającą IllegalArgumentException
         * z opisowym komunikatem dla: pustego stringa, nieparsowalnej liczby,
         * liczby ujemnej. W main przejdź przez wszystkie wpisy, ZBIERAJĄC
         * komunikaty błędów do List<String> (łapiąc wyjątek dla każdego wpisu
         * osobno, bez przerywania pętli), i na końcu wypisz pełną listę błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExceptionChainWithMultipleLevels {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj łańcuch 3 poziomów: warstwaDanych() rzuca
         * NoSuchElementException("brak rekordu"), warstwaBiznesowa() łapie go
         * i rzuca własny checked wyjątek BladBiznesowyException z cause,
         * warstwaApi() łapie BladBiznesowyException i rzuca RuntimeException
         * z cause. W main złap RuntimeException i przejdź w pętli po
         * getCause() (aż do null), wypisując klasę i komunikat KAŻDEGO
         * wyjątku w łańcuchu (czyli pełną "historię" błędu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SuppressedExceptionsSimulation {
        /*
         * 🧪 Zadanie 25:
         * Napisz metodę zamknijZasob(String nazwa, boolean rzucPrzyZamknieciu),
         * rzucającą RuntimeException("blad zamkniecia " + nazwa) jeśli
         * rzucPrzyZamknieciu=true. Napisz metodę wykonajOperacje(), która
         * w try rzuca RuntimeException("blad operacji"), a w finally wywołuje
         * zamknijZasob(..., true) – ręcznie zaimplementuj dodanie drugiego
         * wyjątku jako "suppressed" do pierwszego (addSuppressed()) zamiast
         * pozwolić, by przykrył on oryginalny błąd. W main wypisz komunikat
         * głównego wyjątku oraz wszystkie getSuppressed().
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CustomExceptionWithExtraData {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj własny checked wyjątek WalidacjaFormularzaException
         * przechowujący dodatkowo (poza message) List<String> bledyPol
         * (lista nazw pól z błędami), z odpowiednim konstruktorem i getterem.
         * Napisz metodę walidujFormularz(String imie, String email, int wiek)
         * throws WalidacjaFormularzaException, sprawdzającą wszystkie 3 pola
         * i – jeśli którekolwiek jest niepoprawne – rzucającą wyjątek z listą
         * WSZYSTKICH niepoprawnych pól naraz (nie tylko pierwszego napotkanego).
         * Przetestuj dla kilku kombinacji poprawnych/niepoprawnych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TransactionRollbackSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj "transakcję" jako Map<String, Integer> konta z saldami
         * dwóch kont (np. "A"=100, "B"=50). Napisz metodę przelej(Map konta,
         * String zKogo, String doKogo, int kwota), która: odejmuje kwotę
         * z konta źródłowego, RZUCA IllegalStateException jeśli wynik byłby
         * ujemny (cofając już wykonane odjęcie, zanim rzuci wyjątek – ręczny
         * "rollback"), w przeciwnym razie dodaje kwotę do konta docelowego.
         * Przetestuj przelew, który się uda, i taki, który przekracza saldo –
         * zweryfikuj, że po nieudanym przelewie salda kont są NIEZMIENIONE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExceptionBasedStateMachine {
        /*
         * 🧪 Zadanie 28:
         * Zamodeluj prostą maszynę stanów zamówienia (NOWE -> OPLACONE ->
         * WYSLANE -> DOSTARCZONE) jako enum. Napisz metodę
         * zmienStan(StanZamowienia obecny, StanZamowienia docelowy), rzucającą
         * własny unchecked wyjątek NiedozwolonePrzejscieException, jeśli
         * docelowy stan nie jest bezpośrednim następnikiem obecnego (np. nie
         * można przejść z NOWE od razu do WYSLANE). Przetestuj poprawne
         * i niepoprawne przejścia, wypisując wynik każdej próby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BatchProcessorWithPartialFailure {
        /*
         * 🧪 Zadanie 29:
         * Zamodeluj przetwarzanie "wsadowe" listy 10 zamówień (np. int[]
         * kwoty), gdzie przetworzZamowienie(int kwota) rzuca
         * IllegalArgumentException dla kwot ujemnych lub równych zero.
         * Napisz metodę przetworzWszystkie(int[] kwoty), która przetwarza
         * WSZYSTKIE zamówienia (nie przerywa się na pierwszym błędzie),
         * zwracając obiekt/rekord z trzema polami: liczba sukcesów, liczba
         * porażek, lista komunikatów błędów. Wypisz pełne podsumowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullExceptionHandlingPipeline {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji w mały "pipeline" walidacji rejestracji
         * użytkownika: dane wejściowe to String imie, String email, String
         * wiekStr (do sparsowania). Zaimplementuj: (1) własny checked wyjątek
         * RejestracjaException z listą błędów, (2) parsowanie wieku z obsługą
         * NumberFormatException konwertowanego (chaining) na błąd walidacji,
         * (3) sprawdzenie, że imie i email nie są puste, (4) zebranie
         * WSZYSTKICH błędów na raz (nie przerywanie na pierwszym) i rzucenie
         * jednego RejestracjaException na końcu, jeśli lista błędów nie jest
         * pusta. Przetestuj dla poprawnych danych oraz dla danych z kilkoma
         * jednoczesnymi błędami, wypisując pełną listę problemów.
         */
        public static void main(String[] args) { }
    }
}
