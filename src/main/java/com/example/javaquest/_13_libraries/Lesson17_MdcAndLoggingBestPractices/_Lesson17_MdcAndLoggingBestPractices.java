package com.example.javaquest._13_libraries.Lesson17_MdcAndLoggingBestPractices;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson17_MdcAndLoggingBestPractices {

    private static final Logger logger = LoggerFactory.getLogger(_Lesson17_MdcAndLoggingBestPractices.class);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 17: MDC I DOBRE PRAKTYKI LOGOWANIA ===");

        /*
         * ============================================================
         * 📦 PROBLEM: JAK POWIAZAC LOGI NALEZACE DO TEJ SAMEJ OPERACJI?
         * ============================================================
         * - Wyobraz sobie serwer obslugujacy WIELE zadan (requestow)
         *   rownolegle, kazde na innym watku (patrz _07_servlets,
         *   _05_multithreading). Kazde zadanie generuje logi z RÓZNYCH
         *   metod/klas w trakcie swojego przetwarzania.
         * - Gdyby wszystkie te logi trafily do JEDNEGO pliku (co jest
         *   normalne w produkcji), jak odrozniamy, ktore wpisy naleza do
         *   OBSLUGI ZADANIA nr 42, a ktore do zadania nr 43, skoro leca
         *   wymieszane w czasie?
         * - "Naiwne" rozwiazanie: recznie dopisywac ID zadania do KAZDEGO
         *   komunikatu ("Zadanie 42: sprawdzam uzytkownika", "Zadanie 42:
         *   zapisuje do bazy", ...) - dziala, ale jest MECZACE i LATWO O
         *   POMYLKE (ktos zapomni dopisac ID w jednym miejscu).
         * - MDC (Mapped Diagnostic Context) z pakietu org.slf4j rozwiazuje
         *   to ELEGANCKO: ustawiasz wartosc RAZ na poczatku obslugi
         *   zadania, a Logback DOLACZA ja AUTOMATYCZNIE do KAZDEGO logu
         *   wygenerowanego w tym samym watku, bez zadnej zmiany w
         *   tresci komunikatow.
         */
        System.out.println("\n=== BEZ MDC: recznie dopisywany identyfikator w KAZDYM komunikacie ===");
        logger.info("Zadanie REQ-042: sprawdzam uzytkownika");
        logger.info("Zadanie REQ-042: zapisuje do bazy");
        logger.warn("Zadanie REQ-042: brak srodkow na koncie, uzywam limitu awaryjnego");
        System.out.println("(dziala, ale kazdy komunikat MUSI recznie zawierac 'Zadanie REQ-042:' - latwo zapomniec)");

        /*
         * ============================================================
         * 🔹 MDC.put/get/remove/clear - API
         * ============================================================
         * - MDC.put(klucz, wartosc) - zapisuje pare klucz-wartosc w
         *   kontekscie BIEZACEGO WATKU (patrz sekcja o thread-local nizej).
         * - MDC.get(klucz) - odczytuje wartosc (albo null, jesli brak).
         * - MDC.remove(klucz) - usuwa JEDEN, konkretny klucz.
         * - MDC.clear() - usuwa WSZYSTKIE klucze naraz - wazne przy
         *   "sprzataniu" po zakonczeniu operacji (patrz sekcja o
         *   try/finally nizej), zeby nie zostawic "smieci" z poprzedniego
         *   zadania w kontekscie.
         */
        System.out.println("\n=== MDC.put/get/remove/clear - PODSTAWOWE API ===");
        MDC.put("requestId", "REQ-777");
        System.out.println("MDC.get(\"requestId\") = " + MDC.get("requestId"));
        MDC.put("userId", "u-42");
        System.out.println("MDC.get(\"userId\") = " + MDC.get("userId"));
        MDC.remove("userId");
        System.out.println("Po MDC.remove(\"userId\"): MDC.get(\"userId\") = " + MDC.get("userId"));
        MDC.clear();
        System.out.println("Po MDC.clear(): MDC.get(\"requestId\") = " + MDC.get("requestId"));

        /*
         * ============================================================
         * 🔍 %X{klucz} W WZORCU - MDC AUTOMATYCZNIE W LOGACH
         * ============================================================
         * - Uzywamy tej samej techniki programowego ladowania configu, co
         *   w Lekcji 16 (JoranConfigurator + LoggerContext.reset()).
         * - Token %X{klucz} w <pattern> wstawia wartosc MDC dla podanego
         *   klucza - AUTOMATYCZNIE, przy KAZDYM logu, bez zadnego
         *   dodatkowego argumentu w wywolaniu logger.info(...).
         * - Jesli klucz NIE ISTNIEJE w MDC w momencie logowania, %X{klucz}
         *   po prostu wstawia PUSTY STRING - zaden blad, zadne "null".
         */
        System.out.println("\n=== %X{requestId} I %X{userId} W WZORCU - DEMO ===");
        applyLogbackConfig("""
                <configuration>
                    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
                        <encoder>
                            <pattern>[requestId=%X{requestId} userId=%X{userId}] %-5level %msg%n</pattern>
                        </encoder>
                    </appender>
                    <root level="DEBUG">
                        <appender-ref ref="CONSOLE"/>
                    </root>
                </configuration>
                """);

        logger.info("Log PRZED ustawieniem MDC - oba pola puste w nawiasie");

        MDC.put("requestId", "REQ-777");
        MDC.put("userId", "u-42");
        logger.info("Sprawdzam uzytkownika");
        logger.info("Zapisuje zamowienie do bazy");
        logger.warn("Brak srodkow na koncie, uzywam limitu awaryjnego");
        System.out.println("^ Zauwaz: KAZDY log automatycznie ma requestId/userId - komunikaty ich NIE wspominaja!");

        MDC.clear();
        logger.info("Log PO MDC.clear() - znowu oba pola puste w nawiasie");

        /*
         * ============================================================
         * 🔹 REALNY WZORZEC: try/finally WOKOL OBSLUGI ZADANIA
         * ============================================================
         * - W prawdziwej aplikacji watki (np. z puli watkow ExecutorService,
         *   patrz _05_multithreading Lekcja 21) sa PONOWNIE UZYWANE do
         *   obslugi KOLEJNYCH zadan. Jesli zapomnisz wyczyscic MDC po
         *   zakonczeniu zadania, WARTOSCI Z POPRZEDNIEGO zadania "WYCIEKNA"
         *   do logow NASTEPNEGO zadania obsluzonego przez TEN SAM watek -
         *   to POWAZNY blad (mylace, bledne logi w produkcji).
         * - Dlatego standardowy wzorzec to try/finally: MDC.put(...) na
         *   poczatku, logika biznesowa w try, MDC.clear() w finally -
         *   GWARANTUJE sprzatanie, nawet jesli po drodze poleci wyjatek.
         */
        System.out.println("\n=== WZORZEC try/finally: MDC.clear() ZAWSZE na koncu ===");
        handleSimulatedRequest("REQ-901", "u-100");
        handleSimulatedRequest("REQ-902", "u-200");
        System.out.println("MDC.get(\"requestId\") PO obsludze obu zadan (poza try/finally): " + MDC.get("requestId") + " (musi byc null!)");

        /*
         * ============================================================
         * 🔍 MDC JEST THREAD-LOCAL - NIE PROPAGUJE SIE DO WATKOW POTOMNYCH
         * ============================================================
         * - Pod spodem MDC trzyma dane w ThreadLocal (patrz
         *   _05_multithreading Lekcja 30, ThreadLocal) - kazdy WATEK ma
         *   WLASNA, oddzielna "kopie" mapy MDC.
         * - PULAPKA: jesli w trakcie obslugi zadania odpalisz NOWY watek
         *   (np. `new Thread(...)`, albo zadanie w puli watkow), ten NOWY
         *   watek NIE WIDZI wartosci MDC ustawionych w watku macierzystym -
         *   dostaje WLASNY, PUSTY kontekst MDC.
         * - Ponizej dowod: ustawiamy MDC w watku glownym, odpalamy watek
         *   potomny (DAEMON, joinowany z timeoutem - zgodnie z zasada
         *   bezpieczenstwa demo z _05_multithreading) i sprawdzamy,
         *   ze MDC.get(...) w tym watku zwraca null.
         */
        System.out.println("\n=== PULAPKA: MDC NIE PROPAGUJE SIE DO WATKU POTOMNEGO ===");
        demonstrateMdcDoesNotPropagateToChildThread();

        /*
         * ============================================================
         * 🔍 NAPRAWA: RECZNE SKOPIOWANIE KONTEKSTU DO WATKU POTOMNEGO
         * ============================================================
         * - MDC.getCopyOfContextMap() zwraca KOPIE calej mapy MDC z
         *   biezacego watku (Map<String, String> albo null, gdy pusta).
         * - W watku POTOMNYM wywolujemy MDC.setContextMap(kopia) NA
         *   POCZATKU jego pracy - dopiero WTEDY ten watek "widzi" te same
         *   wartosci - i (WAZNE) czyscimy je w finally, zeby nie
         *   "wyciekly" dalej, gdyby ten watek byl z puli i obslugiwal
         *   pozniej cos innego.
         */
        System.out.println("\n=== NAPRAWA: RECZNE PRZEKAZANIE KONTEKSTU MDC DO WATKU POTOMNEGO ===");
        demonstrateMdcManualPropagation();

        MDC.clear(); // sprzatanie po demach powyzej, na wszelki wypadek

        /*
         * ============================================================
         * 📌 DOBRE PRAKTYKI LOGOWANIA (1/5): KOMUNIKATY SPARAMETRYZOWANE
         * ============================================================
         * - Przypomnienie z Lekcji 15: ZAWSZE logger.info("{} {}", a, b),
         *   NIGDY konkatenacja "+" - guard wydajnosciowy dziala TYLKO przy
         *   placeholderach {}, nie przy recznej konkatenacji Stringow.
         */
        System.out.println("\n=== DOBRA PRAKTYKA 1: SPARAMETRYZOWANE {} (przypomnienie z Lekcji 15) ===");
        String orderId = "ORD-555";
        logger.info("Zlozono zamowienie " + orderId + " (KONKATENACJA - zawsze buduje String, ZLA praktyka)");
        logger.info("Zlozono zamowienie {} (SPARAMETRYZOWANE - buduje String tylko gdy poziom wlaczony)", orderId);

        /*
         * ============================================================
         * 📌 DOBRE PRAKTYKI LOGOWANIA (2/5): WLASCIWY POZIOM
         * ============================================================
         * Praktyczna "reguła kciuka" przy wyborze poziomu:
         *  - ERROR - cos wymaga NATYCHMIASTOWEJ reakcji czlowieka (operacja
         *    sie nie powiodla, dane moga byc niespojne) - jesli masz alerty
         *    produkcyjne, ERROR typowo je WYZWALA.
         *  - WARN - dzieje sie cos NIETYPOWEGO, ale system SOBIE RADZI
         *    (wartosc domyslna, ponowiona proba) - warto przejrzec, ale
         *    nikt nie musi wstawac w nocy.
         *  - INFO - "kamienie milowe" biznesowe/aplikacyjne, na tyle rzadkie,
         *    by dalo sie je czytac na produkcji przez caly dzien bez zalewu.
         *  - DEBUG - szczegoly TECHNICZNE, przydatne PROGRAMISCIE przy
         *    diagnozowaniu - wlaczone na dev/test, wylaczone domyslnie na
         *    produkcji (wlaczane CZASOWO, gdy trzeba zdiagnozowac problem).
         *  - TRACE - "co linijka po linijce robi kod" - prawie nigdy
         *    wlaczone, nawet na dev - tylko przy bardzo szczegolowym
         *    debugowaniu konkretnego, izolowanego problemu.
         */
        System.out.println("\n=== DOBRA PRAKTYKA 2: WLASCIWY POZIOM (ERROR/WARN/INFO/DEBUG/TRACE) ===");
        printLevelGuidanceTable();

        /*
         * ============================================================
         * 📌 DOBRE PRAKTYKI LOGOWANIA (3/5): NIGDY NIE LOGUJ SEKRETOW/PII
         * ============================================================
         * - Logi CZESTO trafiaja do systemow z SZERSZYM dostepem niz baza
         *   danych produkcyjna (agregatory logow, konsole deweloperskie,
         *   czasem nawet pliki wysylane do supportu) - a Ty NIE MASZ
         *   kontroli, kto je pozniej przeczyta ani jak dlugo tam zostana.
         * - NIGDY nie loguj: hasel, tokenow/kluczy API, numerow kart
         *   platniczych, danych wrazliwych (PESEL, dane medyczne) - nawet
         *   "tymczasowo, do debugowania" - takie logi latwo zapomniec
         *   usunac, a wyciek trafia od razu do CALEJ historii logow.
         * - Jesli identyfikacja jest potrzebna - loguj BEZPIECZNY
         *   identyfikator (login, ID uzytkownika), NIGDY samych danych
         *   wrazliwych.
         */
        System.out.println("\n=== DOBRA PRAKTYKA 3: NIGDY NIE LOGUJ SEKRETOW/PII ===");
        String login = "jkowalski";
        String password = "SuperTajneHaslo123!";
        logger.info("ZLA PRAKTYKA (NIGDY tak nie rob w prawdziwym kodzie): uzytkownik={} haslo={}", login, password);
        logger.info("DOBRA PRAKTYKA: uzytkownik zalogowany (login={})", login);
        System.out.println("^ Haslo NIGDY nie powinno trafic do zadnego loga - powyzej pokazane TYLKO jako ostrzezenie.");

        /*
         * ============================================================
         * 📌 DOBRE PRAKTYKI LOGOWANIA (4/5): WYJATEK JAKO OSTATNI ARGUMENT
         * ============================================================
         * - logger.error("komunikat", exception) - gdy OSTATNI argument
         *   jest typu Throwable, SLF4J/Logback TRAKTUJE go SPECJALNIE:
         *   dolacza PELNY stack trace (wraz z ewentualnym "Caused by:") POD
         *   komunikatem - NIE jako kolejny placeholder {}.
         * - PRZECIWIENSTWO: logger.error("komunikat: " + exception.getMessage())
         *   zachowuje TYLKO tekst komunikatu wyjatku - TRACISZ cala
         *   informacje o tym, GDZIE (ktora linijka, jaki lancuch wywolan)
         *   wyjatek powstal - bezcenna informacja przy diagnozowaniu bledow.
         */
        System.out.println("\n=== DOBRA PRAKTYKA 4: THROWABLE JAKO OSTATNI ARGUMENT (PELNY STACK TRACE) ===");
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("--- ZLA PRAKTYKA: tylko getMessage(), BEZ stack trace ---");
            logger.error("Nie udalo sie sparsowac liczby: " + e.getMessage());
            System.out.println("--- DOBRA PRAKTYKA: throwable jako OSTATNI argument, PELNY stack trace ---");
            logger.error("Nie udalo sie sparsowac liczby", e);
        }

        /*
         * ============================================================
         * 📌 DOBRE PRAKTYKI LOGOWANIA (5/5): KONTEKST (MDC) ZAMIAST WSZYSTKIEGO W TRESCI
         * ============================================================
         * - Zamiast upychac WSZYSTKIE dane w tresci komunikatu
         *   ("Zamowienie ORD-1 dla uzytkownika u-1 w sesji SES-1 o kwocie
         *   99.99 PLN o statusie NOWE zostalo utworzone o 12:00:00...") -
         *   przekaz dane, ktore dotycza CALEJ operacji (nie tylko jednego
         *   zdania), przez MDC - a tresc komunikatu zostaw CZYTELNA i
         *   skupiona na tym, CO SIE STALO.
         * - Zaleta: systemy do analizy logow (ELK, Grafana Loki, Splunk)
         *   traktuja pola MDC jako OSOBNE, PRZESZUKIWALNE atrybuty (np.
         *   "znajdz wszystkie logi requestId=REQ-777"), a nie jako fragment
         *   nieustrukturyzowanego tekstu do parsowania regexem.
         */
        System.out.println("\n=== DOBRA PRAKTYKA 5: KONTEKST (MDC) ZAMIAST UPYCHANIA WSZYSTKIEGO W TRESCI ===");
        MDC.put("requestId", "REQ-1000");
        MDC.put("userId", "u-1");
        logger.info("ZLA PRAKTYKA: Zamowienie ORD-1 dla uzytkownika u-1 w ramach requestId=REQ-1000 utworzone");
        logger.info("Zamowienie {} utworzone (DOBRA PRAKTYKA - requestId/userId juz sa w MDC, nie trzeba ich powtarzac)", "ORD-1");
        MDC.clear();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - MDC (org.slf4j.MDC) - "kontekst diagnostyczny" per watek:
         *   put/get/remove/clear. Wartosci sa AUTOMATYCZNIE dolaczane do
         *   kazdego logu przez token %X{klucz} we wzorcu Logbacka.
         * - MDC jest THREAD-LOCAL - NIE propaguje sie do watkow potomnych
         *   samoczynnie. Reczna naprawa: MDC.getCopyOfContextMap() w watku
         *   macierzystym, MDC.setContextMap(kopia) w watku potomnym (i
         *   MDC.clear() w finally watku potomnego).
         * - ZAWSZE czysc MDC w finally (MDC.clear()/MDC.remove(...)) -
         *   inaczej w aplikacjach z pula watkow wartosci "wyciekna" do
         *   kolejnego, niezwiazanego zadania obslugiwanego przez ten sam watek.
         * - 5 dobrych praktyk logowania: (1) komunikaty sparametryzowane {},
         *   (2) wlasciwy poziom (ERROR/WARN/INFO/DEBUG/TRACE wedlug
         *   "reguly kciuka" powyzej), (3) NIGDY sekrety/PII w logach,
         *   (4) throwable jako OSTATNI argument logger.error(msg, ex) -
         *   pelny stack trace, (5) kontekst (MDC) zamiast upychania
         *   wszystkiego w tresci komunikatu.
         * - To KONIEC rozdzialu o SLF4J/Logback w tym kursie (Lekcje 15-17) -
         *   od tej pory kazda kolejna lekcja moze (i powinna) logowac
         *   przez logger.info/debug/warn/error zamiast System.out.println,
         *   tam gdzie ma to sens dydaktyczny.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    /**
     * Identyczny helper jak w Lekcji 16 - laduje podana konfiguracje
     * Logbacka (tresc logback.xml jako String) do biezacego LoggerContext.
     */
    private static void applyLogbackConfig(String xmlConfig) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        try (InputStream inputStream = new ByteArrayInputStream(xmlConfig.getBytes(StandardCharsets.UTF_8))) {
            configurator.doConfigure(inputStream);
        } catch (JoranException | IOException e) {
            throw new RuntimeException("Nie udalo sie zaladowac konfiguracji Logbacka", e);
        }
    }

    /**
     * Symuluje obsluge jednego "zadania" (np. requestu HTTP) - MDC.put(...)
     * na poczatku, logika w try, MDC.clear() w finally - GWARANTUJE
     * sprzatanie kontekstu, nawet gdyby logika biznesowa rzucila wyjatek.
     */
    private static void handleSimulatedRequest(String requestId, String userId) {
        MDC.put("requestId", requestId);
        MDC.put("userId", userId);
        try {
            logger.info("Rozpoczynam obsluge zadania");
            logger.info("Zadanie zakonczone sukcesem");
        } finally {
            MDC.clear();
        }
    }

    private static void printLevelGuidanceTable() {
        String format = "%-7s | %s%n";
        System.out.printf(format, "Poziom", "Kiedy uzyc");
        System.out.println("-".repeat(90));
        System.out.printf(format, "ERROR", "Operacja sie nie powiodla, wymaga NATYCHMIASTOWEJ reakcji czlowieka");
        System.out.printf(format, "WARN", "Cos nietypowego, ale system sobie RADZI (wartosc domyslna, retry)");
        System.out.printf(format, "INFO", "Kamienie milowe biznesowe - domyslny poziom na produkcji");
        System.out.printf(format, "DEBUG", "Szczegoly techniczne dla programisty - wlaczone na dev, wylaczone na prod");
        System.out.printf(format, "TRACE", "Skrajnie szczegolowe - prawie nigdy wlaczone, nawet na dev");
    }

    private static void demonstrateMdcDoesNotPropagateToChildThread() throws InterruptedException {
        MDC.put("requestId", "REQ-CHILD-TEST");
        logger.info("Log z WATKU GLOWNEGO - MDC.get(\"requestId\") = {}", MDC.get("requestId"));

        AtomicInteger childThreadMdcValueIsNull = new AtomicInteger(-1);
        Thread childThread = new Thread(() -> {
            String requestIdInChild = MDC.get("requestId");
            System.out.println("W watku potomnym MDC.get(\"requestId\") = " + requestIdInChild + " (BRAK propagacji!)");
            childThreadMdcValueIsNull.set(requestIdInChild == null ? 1 : 0);
        }, "watek-potomny-bez-mdc");
        childThread.setDaemon(true); // zasada bezpieczenstwa demo z _05_multithreading
        childThread.start();
        childThread.join(TimeUnit.SECONDS.toMillis(2)); // join z timeoutem - main() nigdy sie nie zawiesi

        System.out.println("Czy watek potomny NIE widzial MDC (oczekiwane: 1=tak): " + childThreadMdcValueIsNull.get());
        MDC.clear();
    }

    private static void demonstrateMdcManualPropagation() throws InterruptedException {
        MDC.put("requestId", "REQ-PROPAGATED");
        logger.info("Log z WATKU GLOWNEGO przed odpaleniem watku potomnego");

        // KROK 1: kopiujemy CALY kontekst MDC biezacego (glownego) watku.
        Map<String, String> copiedContext = MDC.getCopyOfContextMap();

        Thread childThread = new Thread(() -> {
            try {
                // KROK 2: wstrzykujemy skopiowany kontekst DO watku potomnego -
                // dopiero TERAZ MDC.get(...) w tym watku "widzi" te wartosci.
                if (copiedContext != null) {
                    MDC.setContextMap(copiedContext);
                }
                logger.info("Log z WATKU POTOMNEGO - requestId={} (dziala dzieki recznemu skopiowaniu)", MDC.get("requestId"));
            } finally {
                // KROK 3: sprzatamy kontekst TEGO watku - wazne zwlaszcza przy
                // watkach z puli, ktore pozniej obsluza CALKIEM INNE zadanie.
                MDC.clear();
            }
        }, "watek-potomny-z-mdc");
        childThread.setDaemon(true);
        childThread.start();
        childThread.join(TimeUnit.SECONDS.toMillis(2));

        MDC.clear();
    }
}
