package com.example.javaquest._13_libraries.Lesson15_WhySlf4jNotSystemOut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _Lesson15_WhySlf4jNotSystemOut {

    // Logger utworzony RAZ, jako pole statyczne klasy (standardowy idiom -
    // patrz sekcja "LoggerFactory.getLogger(Class)" nizej).
    private static final Logger logger = LoggerFactory.getLogger(_Lesson15_WhySlf4jNotSystemOut.class);

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: DLACZEGO SLF4J, A NIE System.out.println? ===");

        /*
         * ============================================================
         * 📦 PROBLEMY Z System.out.println W PRAWDZIWEJ APLIKACJI
         * ============================================================
         * - Brak POZIOMOW WAZNOSCI (severity) - kazdy println() jest
         *   "taki sam" - nie da sie odroznic informacji diagnostycznej
         *   od powaznego bledu bez recznego parsowania tekstu.
         * - Brak TIMESTAMPU i KONTEKSTU - nie wiadomo, KIEDY log powstal
         *   ani z jakiej klasy/watku - trzeba by to recznie dopisywac do
         *   kazdego println(), co szybko robi sie niespojne miedzy
         *   czlonkami zespolu.
         * - Nie da sie WYLACZYC bez usuwania/komentowania kodu - jesli
         *   println() jest wpisany na sztywno, jedyny sposob, by go
         *   "wyciszyc" w produkcji, to fizyczna zmiana kodu (i ponowny
         *   deploy) - a przeciez czesto chcemy tylko CZASOWO wlaczyc
         *   szczegolowe logi do debugowania problemu na produkcji.
         * - Brak PRZEKIEROWANIA - System.out zawsze leci na standardowe
         *   wyjscie konsoli. Prawdziwe aplikacje chca logow rownoczesnie
         *   w pliku (z rotacja), w systemie monitoringu (ELK, Grafana
         *   Loki, Splunk) i ewentualnie w konsoli - System.out tego nie
         *   umie bez recznego przekierowywania strumieni.
         * - WYDAJNOSC przy duzej liczbie wywolan - System.out.println()
         *   jest SYNCHRONIZOWANY (kazde wywolanie blokuje watek na
         *   wspoldzielonym obiekcie PrintStream) i wymusza konkatenacje
         *   Stringow ZAWSZE, nawet jesli finalnie nikt tego loga nie
         *   przeczyta - w systemie logowania mozna to ominac (patrz
         *   sekcja o komunikatach sparametryzowanych nizej).
         */
        printSystemOutProblems();

        /*
         * ============================================================
         * 🔹 CZYM JEST SLF4J: FASADA, NIE IMPLEMENTACJA
         * ============================================================
         * - SLF4J (Simple Logging Facade for Java) to API - zbior
         *   interfejsow (m.in. org.slf4j.Logger), BEZ wlasnej logiki
         *   zapisu logow. Sam SLF4J nie potrafi nic wypisac "na wlasna
         *   reke" - deleguje kazde wywolanie do KONKRETNEJ IMPLEMENTACJI
         *   podpietej na classpath w runtime.
         * - To DOKLADNIE ta sama idea, co sterownik JDBC z rozdzialu
         *   _09_jdbc: java.sql.Driver/Connection to API (interfejsy) -
         *   Twoj kod pisze przeciwko INTERFEJSOM, a to, czy pod spodem
         *   dziala H2, PostgreSQL czy MySQL, zalezy TYLKO od tego, jaki
         *   sterownik (JAR) dolaczysz do projektu. Kod aplikacji ani
         *   linijki sie nie zmienia.
         * - Analogicznie: Twoj kod pisze przeciwko org.slf4j.Logger -
         *   a to, CO faktycznie dzieje sie z logiem (czy trafia do
         *   konsoli, pliku, systemu monitoringu), zalezy od IMPLEMENTACJI
         *   podpietej na classpath. W tym projekcie (transitywnie przez
         *   spring-boot-starter) jest to Logback - poznasz go szczegolowo
         *   w Lekcji 16.
         * - Inne popularne implementacje SLF4J: Log4j2 (przez adapter
         *   log4j-slf4j2-impl), java.util.logging (JUL, wbudowane w JDK,
         *   przez adapter slf4j-jdk14). Gdyby ten projekt chcial
         *   przelaczyc sie z Logback na Log4j2, wystarczyloby podmienic
         *   ZALEZNOSC w pom.xml - ANI JEDNA linijka kodu wywolujaca
         *   logger.info(...) w calej aplikacji NIE musialaby sie zmienic.
         */
        explainFacadeVsImplementation();

        /*
         * ============================================================
         * 🔹 POZIOMY LOGOWANIA: TRACE / DEBUG / INFO / WARN / ERROR
         * ============================================================
         * Od najmniej do najbardziej istotnego:
         * - TRACE - najdrobniejsze detale, "co linijka po linijce robi
         *   kod" - praktycznie tylko przy bardzo szczegolowym
         *   debugowaniu, prawie nigdy wlaczone nawet na srodowisku dev.
         *   Przyklad: "Wchodze do petli, i=" + i, "Parsuje token: " + token.
         * - DEBUG - informacje przydatne PROGRAMISCIE przy diagnozowaniu
         *   problemow, ale zbyt szczegolowe na co dzien - typowo wlaczone
         *   na srodowisku deweloperskim/testowym, WYLACZONE na produkcji.
         *   Przyklad: "Zapytanie SQL: " + sql, "Cache miss dla klucza: " + key.
         * - INFO - istotne zdarzenia biznesowe/aplikacyjne, "co sie
         *   dzieje" na wysokim poziomie - typowy domyslny poziom na
         *   produkcji. Przyklad: "Uzytkownik 123 zalogowal sie",
         *   "Aplikacja wystartowala na porcie 8080".
         * - WARN - cos poszlo NIE TAK JAK ZWYKLE, ale aplikacja sobie
         *   RADZI (np. uzyla wartosci domyslnej, ponowila operacje) -
         *   wymaga uwagi, ale nie jest to jeszcze awaria.
         *   Przyklad: "Brak konfiguracji X, uzywam wartosci domyslnej",
         *   "Ponawiam polaczenie z baza (proba 2/3)".
         * - ERROR - cos NAPRAWDE poszlo zle - wyjatek, operacja sie nie
         *   powiodla, uzytkownik/system ucierpial. Zazwyczaj logowane
         *   razem z wyjatkiem (drugi argument metody), zeby zachowac
         *   pelny stack trace.
         *   Przyklad: "Nie udalo sie zapisac zamowienia", exception.
         */
        demonstrateLogLevels();

        /*
         * ============================================================
         * 🔍 LoggerFactory.getLogger(TwojaKlasa.class) - PO CO .class?
         * ============================================================
         * - Standardowy idiom: Logger logger =
         *   LoggerFactory.getLogger(TwojaKlasa.class); - jako pole
         *   PRYWATNE, STATYCZNE, FINALNE klasy (patrz pole "logger" na
         *   gorze tego pliku).
         * - Przekazujac .class, logger "wie", z JAKIEJ klasy pochodzi
         *   kazdy log - ta informacja trafia do KAZDEGO wpisu (widoczna
         *   w Lekcji 16 jako %logger w patternie) - dzieki temu, patrzac
         *   na logi produkcyjne, od razu wiesz, KTORA klasa/warstwa
         *   aplikacji wygenerowala dany wpis, bez czytania calego
         *   komunikatu.
         * - Nazwa loggera (pelna nazwa klasy, np.
         *   "com.example.javaquest...MojaKlasa") umozliwia TAKZE
         *   hierarchiczne wlaczanie/wylaczanie logow per PAKIET -
         *   rozwiniemy to w Lekcji 16 (hierarchia loggerow).
         * - Czesty blad poczatkujacych: skopiowanie tej linijki miedzy
         *   klasami i zapomnienie podmiany .class na wlasciwa klase -
         *   logi beda wtedy blednie "podpisane" nazwa INNEJ klasy, co
         *   myli przy diagnozowaniu problemow.
         */
        explainLoggerFactoryClassArgument();

        /*
         * ============================================================
         * 🔍 KOMUNIKATY SPARAMETRYZOWANE ZAMIAST KONKATENACJI STRINGOW
         * ============================================================
         * - Zamiast: logger.info("User " + userId + " logged in at " + timestamp);
         *   pisz:     logger.info("User {} logged in at {}", userId, timestamp);
         * - Znaki {} to PLACEHOLDERY - SLF4J podmienia je na wartosci
         *   argumentow, w kolejnosci wystapienia.
         * - KLUCZOWA roznica wydajnosciowa: przy konkatenacji ("+") Java
         *   MUSI zbudowac finalny String (wywolac wszystkie toString(),
         *   StringBuilder.append(...)) ZANIM w ogole wywola logger.info(...)
         *   - nawet jesli poziom INFO jest WYLACZONY i log finalnie
         *   NIGDZIE sie nie pojawi, koszt budowy Stringa juz zostal
         *   poniesiony.
         * - Przy wersji sparametryzowanej, implementacja SLF4J NAJPIERW
         *   sprawdza, czy dany poziom jest WLACZONY dla tego loggera -
         *   i TYLKO wtedy laczy komunikat z argumentami. Jesli poziom
         *   jest wylaczony (np. logger.debug(...) przy poziomie
         *   ustawionym na INFO), argumenty NIGDY nie sa konwertowane na
         *   String - zero kosztu.
         * - Przy tysiacach/milionach wywolan logger.debug(...) w petlach
         *   (np. przetwarzanie duzych kolekcji), ta roznica ma REALNY
         *   wplyw na wydajnosc aplikacji.
         */
        demonstrateParameterizedMessages();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - System.out.println() nie ma poziomow, kontekstu, mozliwosci
         *   wylaczenia ani przekierowania - w prawdziwej aplikacji
         *   zastepuje go logowanie przez SLF4J.
         * - SLF4J to FASADA (API) - Twoj kod zalezy TYLKO od interfejsu
         *   org.slf4j.Logger, a konkretna implementacje (Logback,
         *   Log4j2, JUL) podmienia sie przez zaleznosc w pom.xml, bez
         *   zmiany kodu (analogia: sterownik JDBC z _09_jdbc).
         * - 5 poziomow: TRACE < DEBUG < INFO < WARN < ERROR - rosnaca
         *   waznosc, malejaca "gadatliwosc" na produkcji.
         * - Zawsze: LoggerFactory.getLogger(TwojaKlasa.class) jako pole
         *   static final.
         * - Zawsze: komunikaty sparametryzowane logger.info("{} {}", a, b)
         *   zamiast konkatenacji Stringow - czytelniej i wydajniej.
         * - W Lekcji 16: konfiguracja Logbacka - dokad trafiaja logi
         *   i jak wygladaja.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void printSystemOutProblems() {
        System.out.println("\n=== PROBLEMY Z System.out.println ===");
        System.out.println("--- Tak wygladaja 'logi' przez System.out (brak poziomu, timestampu, kontekstu) ---");
        System.out.println("Uzytkownik zalogowany");
        System.out.println("Blad zapisu do bazy!");
        System.out.println("--- Nie da sie ich odroznic bez czytania tresci, wylaczyc, ani przekierowac ---");
    }

    private static void explainFacadeVsImplementation() {
        System.out.println("\n=== SLF4J = FASADA (API), LOGBACK = IMPLEMENTACJA ===");
        System.out.println("org.slf4j.Logger / LoggerFactory  <-- Twoj kod zalezy TYLKO od tego (API)");
        System.out.println("ch.qos.logback.classic.Logger     <-- konkretna implementacja pod spodem (JAR na classpath)");
        System.out.println("Analogia z _09_jdbc: java.sql.Connection (API) vs org.h2.Driver (implementacja).");

        // Rzeczywisty dowod, ze "logger" (typu org.slf4j.Logger) w runtime
        // jest instancja Logbacka - mimo ze kod odwoluje sie TYLKO do API SLF4J.
        System.out.println("Klasa implementujaca interfejs Logger w tym projekcie: " + logger.getClass().getName());
    }

    private static void demonstrateLogLevels() {
        System.out.println("\n=== POZIOMY LOGOWANIA (TRACE < DEBUG < INFO < WARN < ERROR) ===");
        logger.trace("TRACE: petla przetwarza element o indeksie {}", 42);
        logger.debug("DEBUG: wykonano zapytanie SQL: {}", "SELECT * FROM users WHERE id = ?");
        logger.info("INFO: uzytkownik {} zalogowal sie o {}", "kowalski", "12:34:56");
        logger.warn("WARN: brak konfiguracji 'timeout', uzywam wartosci domyslnej: {} ms", 5000);
        logger.error("ERROR: nie udalo sie zapisac zamowienia {}", "ORD-2026-001");
        System.out.println("(TRACE zwykle NIE pojawia sie w konsoli - domyslny poziom Logbacka to DEBUG - patrz Lekcja 16)");
    }

    private static void explainLoggerFactoryClassArgument() {
        System.out.println("\n=== LoggerFactory.getLogger(TwojaKlasa.class) ===");
        System.out.println("Nazwa TEGO loggera (widoczna w kazdym logu): " + logger.getName());
        System.out.println("To pelna, kwalifikowana nazwa klasy - pozwala odrozniac zrodlo logow i grupowac je per pakiet (Lekcja 16).");
    }

    private static void demonstrateParameterizedMessages() {
        System.out.println("\n=== KOMUNIKATY SPARAMETRYZOWANE VS KONKATENACJA ===");

        String userId = "u-777";
        String timestamp = "2026-07-09T10:15:00";

        // ZLA praktyka: konkatenacja - String budowany ZAWSZE, nawet gdy
        // poziom DEBUG jest wylaczony i log nigdy nie trafi do konsoli.
        logger.debug("User " + userId + " logged in at " + timestamp + " (KONKATENACJA - zawsze buduje String)");

        // DOBRA praktyka: placeholdery {} - String budowany TYLKO, jesli
        // poziom DEBUG jest faktycznie wlaczony dla tego loggera.
        logger.debug("User {} logged in at {} (SPARAMETRYZOWANE - String budowany tylko, gdy poziom wlaczony)", userId, timestamp);

        // Prosty pomiar - ile kosztuje samo budowanie duzego Stringa
        // konkatenacja w petli w porownaniu do przekazania argumentow,
        // ktore SLF4J polaczy tylko w razie potrzeby.
        int iterations = 200_000;

        long startConcat = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            String ignored = "Element numer " + i + " ma wartosc " + (i * 2);
        }
        long concatNanos = System.nanoTime() - startConcat;

        long startParam = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            // logger.trace(...) z placeholderami - TRACE jest wylaczony
            // (patrz demonstrateLogLevels powyzej), wiec SLF4J odrzuca
            // wywolanie NATYCHMIAST, bez tworzenia Stringa czy StringBuildera.
            logger.trace("Element numer {} ma wartosc {}", i, i * 2);
        }
        long paramNanos = System.nanoTime() - startParam;

        System.out.printf("Konkatenacja %d Stringow (zawsze budowane): %d ms%n", iterations, concatNanos / 1_000_000);
        System.out.printf("logger.trace z {} (poziom wylaczony, nic nie budowane): %d ms%n", paramNanos / 1_000_000);
        System.out.println("Roznica pokazuje, dlaczego 'guard' logowania sparametryzowanego jest praktycznie darmowy, gdy poziom jest wylaczony.");
    }
}
