package com.example.javaquest._13_libraries.Lesson15_WhySlf4jNotSystemOut;

public class _Exercises_Lesson15_WhySlf4jNotSystemOut {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FirstLogger {
        /*
         * 🧪 Zadanie 1:
         * Utworz pole "private static final Logger logger =
         * LoggerFactory.getLogger(Exercise01_FirstLogger.class);" i wypisz nim
         * jeden komunikat na poziomie INFO: "Pierwszy log przez SLF4J!".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AllFiveLevels {
        /*
         * 🧪 Zadanie 2:
         * Utworz logger dla tej klasy i wywolaj po JEDNYM komunikacie na
         * KAZDYM z 5 poziomow (TRACE/DEBUG/INFO/WARN/ERROR), tresc dowolna,
         * ale zwiazana ze scenariuszem "konto bankowe" (np. "Sprawdzam saldo",
         * "Brak srodkow na koncie").
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RefactorPrintlnToLogger {
        /*
         * 🧪 Zadanie 3:
         * Ponizszy kod uzywa System.out.println - przepisz go na logger.info
         * z komunikatem SPARAMETRYZOWANYM (uzyj {}):
         *   String user = "anna";
         *   int wiek = 30;
         *   System.out.println("Uzytkownik " + user + " ma " + wiek + " lat");
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParameterizedRegistration {
        /*
         * 🧪 Zadanie 4:
         * Napisz log INFO o rejestracji nowego uzytkownika z DWOMA
         * parametrami: login ("nowak99") oraz email ("nowak99@example.com") -
         * uzyj skladni logger.info("...{}...{}...", arg1, arg2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LoggerNamePerClass {
        /*
         * 🧪 Zadanie 5:
         * Utworz DWA loggery w tej samej metodzie main - jeden dla
         * Exercise05_LoggerNamePerClass.class, drugi dla String.class (celowo
         * bledna klasa) - wypisz logger.getName() obu i zaobserwuj roznice w
         * nazwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AppStartupScenario {
        /*
         * 🧪 Zadanie 6:
         * Zasymuluj start aplikacji: INFO "Aplikacja startuje...", WARN
         * "Brak pliku konfiguracyjnego, uzywam domyslnych ustawien", ERROR
         * "Nie udalo sie polaczyc z baza danych" (bez rzucania wyjatku,
         * tylko log).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RefactorConcatenationBlock {
        /*
         * 🧪 Zadanie 7:
         * Ponizszy blok uzywa konkatenacji w logu - przepisz KAZDA linijke na
         * wersje sparametryzowana:
         *   int id = 5; String status = "ANULOWANE";
         *   logger.info("Zamowienie " + id + " ma status " + status);
         *   logger.warn("Zamowienie " + id + " przekroczylo limit czasu");
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_NestedClassLoggerName {
        /*
         * 🧪 Zadanie 8:
         * Utworz logger dla TEJ zagniezdzonej klasy (Exercise08_...class) i
         * wypisz jego nazwe (getName()) - zaobserwuj, ze zawiera znak "$"
         * (podobnie jak nazwy encji JPA w _12_hibernate, Lesson04) - zapisz
         * ten wniosek w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CalculatorWithDebugLogs {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode "int dodaj(int a, int b)" ktora loguje DEBUG z
         * argumentami i wynikiem ("Dodaje {} + {} = {}"), a nastepnie wywolaj
         * ja dla par (2,3), (10,-4), (0,0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WhySlf4jSummary {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu wypisz (min. 4 punkty) glowne roznice
         * miedzy System.out.println a logowaniem przez SLF4J, ktore
         * zapamietales z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_OrderProcessingPipeline {
        /*
         * 🧪 Zadanie 11:
         * Zasymuluj pipeline przetwarzania zamowienia z 4 metodami
         * (odebrano/zwalidowano/zapisano/wyslano), kazda loguje INFO na
         * odpowiednim etapie z numerem zamowienia jako parametrem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LogExceptionWithLogger {
        /*
         * 🧪 Zadanie 12:
         * W bloku try-catch wywolaj Integer.parseInt("abc") (rzuci
         * NumberFormatException) i zaloguj go przez
         * logger.error("Nie udalo sie sparsowac liczby", exception) - drugi
         * argument to sam wyjatek, nie jego getMessage().
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_BenchmarkConcatVsParameterized {
        /*
         * 🧪 Zadanie 13:
         * Wzorujac sie na demonstracji z teorii, zmierz czas 500 000
         * wywolan logger.trace z konkatenacja Stringow vs 500 000 wywolan z
         * placeholderami {} (poziom TRACE wylaczony) - wypisz oba czasy w ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LibraryHidingImplementation {
        /*
         * 🧪 Zadanie 14:
         * Napisz klase "MiniCalculatorLibrary" z metoda "divide(int a, int
         * b)" ktora WEWNETRZNIE uzywa loggera SLF4J (DEBUG dla dzialania,
         * ERROR przy dzieleniu przez zero) - kod WYWOLUJACY biblioteke nie
         * powinien wiedziec, jaka implementacja SLF4J jest pod spodem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidateAgeWithLevels {
        /*
         * 🧪 Zadanie 15:
         * Napisz metode "validateAge(int age)": DEBUG "Waliduje wiek: {}" na
         * poczatku, WARN "Wiek ujemny: {}" jesli age < 0, INFO "Wiek OK: {}"
         * jesli poprawny. Wywolaj dla -5, 0, 30, 150.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ThreeServiceLoggers {
        /*
         * 🧪 Zadanie 16:
         * Utworz 3 osobne loggery (dla klas symbolizujacych OrderService,
         * PaymentService, InventoryService) i zaloguj po jednym zdarzeniu
         * biznesowym INFO z kazdego - zaobserwuj, ze kazdy log ma inna
         * nazwe zrodlowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WrongClassPitfall {
        /*
         * 🧪 Zadanie 17:
         * Celowo utworz logger dla NIEWLASCIWEJ klasy (np. w klasie
         * "ServiceA" napisz LoggerFactory.getLogger(ServiceB.class)) i
         * zaloguj komunikat - zaobserwuj mylaca nazwe w logu, zapisz w
         * komentarzu, dlaczego to bledna praktyka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RetryWithBackoffLogging {
        /*
         * 🧪 Zadanie 18:
         * Zasymuluj 3 proby polaczenia z "serwerem" (kazda "nieudana" -
         * boolean/losowo), loguj WARN "Proba {} nieudana" po kazdej porazce,
         * a po 3. nieudanej probie zaloguj ERROR "Wszystkie proby wyczerpane".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FormValidationLevelsDesign {
        /*
         * 🧪 Zadanie 19:
         * Dla scenariusza "walidacja formularza rejestracji" zaprojektuj i
         * ZAIMPLEMENTUJ logi na WSZYSTKICH 5 poziomach (TRACE - kazde pole
         * sprawdzane, DEBUG - wynik walidacji pojedynczego pola, INFO -
         * formularz zaakceptowany, WARN - pole niepoprawne ale naprawialne,
         * ERROR - formularz odrzucony).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TimedOperationHelper {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode "timed(String opName, Runnable op)" ktora loguje
         * DEBUG "Start: {}" przed, wykonuje op.run(), potem DEBUG "Koniec:
         * {} ({} ms)" z nazwa operacji i zmierzonym czasem. Uzyj jej dla
         * Thread.sleep(50) opakowanego w Runnable.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InMemoryCacheWithDebugLogs {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj prosta klase "SimpleCache" (Map<String,String> pod
         * spodem) z metodami get/put logujacymi DEBUG "Cache HIT: {}" / "Cache
         * MISS: {}" (sparametryzowane) - zweryfikuj dzialanie na 5 operacjach
         * get/put.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_Slf4jVsAlternativesResearch {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: napisz w komentarzu (min. 5 punktow) porownanie
         * SLF4J+Logback vs SLF4J+Log4j2 vs java.util.logging (JUL) - kiedy
         * wybrac ktora implementacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LibrarySystemFullyLogged {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj mini-system biblioteczny (wypozyczBook/zwrocBook) z
         * WLASCIWYMI poziomami logowania: INFO dla zdarzen biznesowych
         * (wypozyczenie/zwrot), DEBUG dla operacji wewnetrznych (sprawdzanie
         * dostepnosci), WARN gdy ksiazka niedostepna, ERROR przy proba
         * zwrotu niewypozyczonej ksiazki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HighThroughputBenchmark {
        /*
         * 🧪 Zadanie 24:
         * Zmierz czas 1 000 000 wywolan logger.trace z konkatenacja vs 1 000
         * 000 z placeholderami (TRACE wylaczony) - wypisz oba czasy oraz
         * PROCENTOWA roznice, opisz wynik w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ExceptionChainLogging {
        /*
         * 🧪 Zadanie 25:
         * Nawiazujac do _01_fundamentals/Lesson16_Exceptions (chaining/cause):
         * rzuc wlasny wyjatek z "cause" (np. RuntimeException opakowujacy
         * NumberFormatException) i zaloguj go logger.error(msg, exception) -
         * zweryfikuj, ze w stack trace widac OBA wyjatki ("Caused by:").
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PerThreadNamedLoggers {
        /*
         * 🧪 Zadanie 26:
         * Uruchom 3 watki (jak w _05_multithreading), kazdy z WLASNYM
         * loggerem (mozna uzyc tej samej klasy, ale zaloguj nazwe watku jako
         * parametr {}) - zweryfikuj, ze logi z roznych watkow sa czytelnie
         * odrozniane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RefactorGodClassToProperLogging {
        /*
         * 🧪 Zadanie 27:
         * Dany opis "God class" ktora uzywala System.out.println do
         * WSZYSTKIEGO (start, walidacja, zapis, blad) - przepisz jako klase z
         * loggerem SLF4J, przypisujac KAZDEMU komunikatowi wlasciwy poziom
         * (uzasadnij wybor w komentarzu przy kazdej linijce).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_LoggingPolicyAcrossLayers {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (w komentarzu) polityke logowania dla warstw
         * controller/service/repository fikcyjnego projektu (jaki poziom
         * gdzie, co NIE powinno byc logowane) - a nastepnie ZAIMPLEMENTUJ 3
         * przykladowe klasy z loggerami zgodnymi z ta polityka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ProveGuardAvoidsStringBuilding {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj dowod (kodem), ze przy WYLACZONYM poziomie DEBUG argument
         * bedacy KOSZTOWNA operacja (np. metoda zwracajaca String i
         * inkrementujaca licznik wywolan) NIE jest wywolywany, gdy przekazany
         * jest jako zwykly obiekt do logger.debug("{}", obiekt) - podpowiedz:
         * porownaj z uzyciem "+" w konkatenacji, gdzie metoda BY sie wywolala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_OrderSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zbuduj system OrderService + PaymentService
         * + ShippingService, kazdy z WLASNYM loggerem (LoggerFactory.getLogger
         * z .class), w pelni wykorzystujacy komunikaty sparametryzowane i
         * wlasciwe poziomy (INFO dla przeplywu biznesowego, DEBUG dla
         * szczegolow technicznych, WARN/ERROR dla problemow) - zasymuluj
         * pelne zamowienie od zlozenia do wyslania.
         */
        public static void main(String[] args) { }
    }
}
