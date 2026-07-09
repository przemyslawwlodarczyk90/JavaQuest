package com.example.javaquest._13_libraries.Lesson16_LogbackConfiguration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class _Lesson16_LogbackConfiguration {

    // Logger utworzony RAZ, jako pole statyczne - dokladnie jak w Lekcji 15.
    // WAZNE: mimo ze w tej lekcji BEDZIEMY wielokrotnie przeladowywac
    // konfiguracje Logbacka (patrz applyLogbackConfig() nizej), TA SAMA
    // instancja loggera pozostaje uzywalna przez caly czas - reset()
    // czysci appendery/poziomy, ale NIE niszczy juz utworzonych obiektow
    // Logger (SLF4J zawsze zwroci tego samego loggera dla tej samej nazwy).
    private static final Logger logger = LoggerFactory.getLogger(_Lesson16_LogbackConfiguration.class);

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: KONFIGURACJA LOGBACKA (logback.xml) ===");

        /*
         * ============================================================
         * 📦 PO CO KONFIGUROWAC LOGBACKA?
         * ============================================================
         * - W Lekcji 15 uzywalismy loggera "z automatu" - bez ZADNEGO
         *   pliku konfiguracyjnego. Logback, gdy NIE znajdzie na classpath
         *   pliku "logback.xml" (ani "logback-test.xml"), sam wlacza
         *   awaryjna konfiguracje (tzw. "BasicConfigurator"): jeden
         *   ConsoleAppender, poziom DEBUG, prosty wzorzec z data/godzina.
         *   Wlasnie TAK wygladaly logi w Lekcji 15, mimo ze o tym nie
         *   mowilismy wprost.
         * - W prawdziwym projekcie chcesz nad tym MIEC KONTROLE: dokad
         *   trafiaja logi (konsola? plik? oba naraz?), jak dokladnie
         *   wygladaja (jaki format daty, czy pokazywac nazwe watku?),
         *   jaki jest domyslny poziom, i czy niektore pakiety maja
         *   INNY poziom niz reszta aplikacji (np. wlaczony DEBUG tylko
         *   dla jednego, diagnozowanego modulu).
         * - W REALNYM projekcie ta konfiguracja to plik
         *   "src/main/resources/logback.xml" - Logback znajduje go
         *   SAM, automatycznie, przy starcie aplikacji. TEN projekt
         *   (javaQuest) CELOWO nie ma takiego pliku - zeby lekcje z
         *   INNYCH rozdzialow (ktore tez logguja przez SLF4J) nie
         *   zmienily zachowania przez przypadek.
         * - Zamiast tego, w TEJ lekcji zbudujemy konfiguracje Logbacka
         *   JAKO TEKST (String, Java text block) i zaladujemy ja
         *   PROGRAMOWO, w locie, w obrebie jednego uruchomienia main().
         *   To DOKLADNIE ten sam trik, ktory poznales w _11_buildtools
         *   przy embedowanym Ancie (Lekcja 3): tam generowalismy
         *   prawdziwy build.xml jako text block i wgrywalismy go do
         *   silnika Anta przez ProjectHelper.configureProject(...) -
         *   tutaj generujemy prawdziwy logback.xml i wgrywamy go do
         *   silnika Logbacka przez JoranConfigurator. W obu przypadkach
         *   uzywamy PRAWDZIWEGO narzedzia z PRAWDZIWA skladnia - tylko
         *   zrodlem configu jest String w kodzie, nie plik na dysku.
         */
        System.out.println("\n=== DOMYSLNA KONFIGURACJA LOGBACKA (BEZ logback.xml) ===");
        logger.debug("To DEBUG - w domyslnej (awaryjnej) konfiguracji Logbacka jest WIDOCZNY");
        logger.info("To INFO - domyslny wzorzec: data, watek, poziom, nazwa loggera, komunikat");
        logger.warn("Zaraz zastapimy ta konfiguracje WLASNA, wczytana z tekstu XML");

        /*
         * ============================================================
         * 🔹 STRUKTURA logback.xml: <configuration>, <appender>, <root>
         * ============================================================
         * Najwazniejsze elementy pliku logback.xml:
         *
         *  - <configuration> - korzen calego pliku.
         *  - <appender name="..." class="...">
         *      Definiuje DOKAD trafiaja logi. "class" to pelna nazwa
         *      klasy Javy implementujacej appender. Najwazniejsze:
         *      * ch.qos.logback.core.ConsoleAppender - na konsole (System.out).
         *      * ch.qos.logback.core.FileAppender - do JEDNEGO, stalego pliku.
         *      * ch.qos.logback.core.rolling.RollingFileAppender - do pliku,
         *        ktory jest okresowo "rotowany" (przenoszony/archiwizowany
         *        i zaczynany od nowa) wedlug <rollingPolicy> - patrz nizej.
         *  - <encoder> (wewnatrz appendera)
         *      Odpowiada za PRZEKSZTALCENIE wpisu logu na finalny tekst -
         *      wewnatrz <pattern> podajemy wzorzec (zobacz sekcja ponizej).
         *  - <root level="...">
         *      Konfiguruje logger GLOWNY (korzen calej hierarchii - patrz
         *      dalsza czesc lekcji) - "level" to domyslny poziom dla
         *      WSZYSTKICH loggerow, ktore nie maja wlasnego, jawnego
         *      poziomu. <appender-ref ref="..."/> wewnatrz <root> podpina
         *      appender(y) do root loggera.
         *  - <logger name="..." level="...">
         *      Nadpisuje poziom dla KONKRETNEGO loggera (typowo: nazwy
         *      pakietu/klasy) - wiecej w sekcji o hierarchii nizej.
         */

        /*
         * ============================================================
         * 🔹 TOKENY WZORCA (%pattern) W encoderze
         * ============================================================
         * Najczesciej uzywane tokeny w <pattern>:
         *  - %d{...}   - data/godzina, np. %d{HH:mm:ss.SSS} lub %d{yyyy-MM-dd}.
         *  - %thread   - nazwa watku, ktory wygenerowal log.
         *  - %level lub %p  - poziom (TRACE/DEBUG/INFO/WARN/ERROR).
         *                     %-5level wyrownuje do 5 znakow (dla ladnych kolumn).
         *  - %logger{N} - nazwa loggera (pelna klasa), skrocona do N znakow
         *                 (np. %logger{36} - Logback inteligentnie skraca
         *                 nazwy pakietow, zachowujac nazwe klasy w calosci).
         *  - %msg lub %m - sama tresc komunikatu (juz po podstawieniu {}).
         *  - %n        - znak nowej linii, WLASCIWY dla systemu operacyjnego
         *                (odpowiednik System.lineSeparator()) - zawsze konczy
         *                pattern, inaczej kolejne logi wyladuja w JEDNEJ linii.
         *  - %X{klucz} - wartosc z MDC (Mapped Diagnostic Context) - poznasz
         *                to szczegolowo w Lekcji 17.
         */
        printPatternTokensTable();

        /*
         * ============================================================
         * 🔍 WZORZEC: PROGRAMOWE LADOWANIE KONFIGURACJI (JoranConfigurator)
         * ============================================================
         * Metoda applyLogbackConfig(String xml) ponizej to nasz odpowiednik
         * "wczytaj logback.xml" - tyle ze zamiast pliku na dysku, zrodlem
         * jest zwykly String:
         *  1) LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
         *     - "ILoggerFactory" to interfejs SLF4J - w tym projekcie POD SPODEM
         *     zawsze jest to instancja ch.qos.logback.classic.LoggerContext
         *     (dokladnie tak, jak logger.getClass() w Lekcji 15 pokazal
         *     konkretna klase Logbacka).
         *  2) context.reset() - CZYSCI biezaca konfiguracje (appendery,
         *     poziomy, filtry) - zaczynamy "na czysto", zeby kolejne demo
         *     w tej lekcji nie mieszalo sie z poprzednim.
         *  3) new JoranConfigurator() - to WLASNIE "silnik", ktory Logback
         *     normalnie uzywa do parsowania logback.xml przy starcie
         *     aplikacji - tutaj uzywamy go RECZNIE.
         *  4) configurator.doConfigure(inputStream) - parsuje XML i
         *     KONFIGURUJE nasz LoggerContext (tworzy appendery, przypisuje
         *     poziomy) - DOKLADNIE to samo, co dzieje sie automatycznie
         *     przy starcie normalnej aplikacji z plikiem logback.xml.
         */
        System.out.println("\n=== WLASNA KONFIGURACJA NR 1: ConsoleAppender z krotkim wzorcem ===");
        applyLogbackConfig("""
                <configuration>
                    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
                        <encoder>
                            <pattern>[%level] %logger{20} - %msg%n</pattern>
                        </encoder>
                    </appender>
                    <root level="INFO">
                        <appender-ref ref="CONSOLE"/>
                    </root>
                </configuration>
                """);
        logger.debug("To DEBUG - NIE powinno sie pojawic (root level=INFO w nowej konfiguracji)");
        logger.info("To INFO - format jest teraz KROTSZY niz domyslny (brak daty/watku)");
        logger.warn("To WARN - zauwaz brak daty i nazwy watku w porownaniu do domyslnego formatu");

        System.out.println("\n=== WLASNA KONFIGURACJA NR 2: ConsoleAppender z PELNYM wzorcem ===");
        applyLogbackConfig("""
                <configuration>
                    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
                        <encoder>
                            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
                        </encoder>
                    </appender>
                    <root level="DEBUG">
                        <appender-ref ref="CONSOLE"/>
                    </root>
                </configuration>
                """);
        logger.debug("To DEBUG - tym razem WIDOCZNE (root level=DEBUG) i z pelnym wzorcem");
        logger.info("Zauwaz %-5level: 'INFO ' i 'DEBUG' zajmuja tyle samo miejsca - kolumny sie zgadzaja");

        /*
         * ============================================================
         * 📦 FileAppender - LOGI DO PLIKU
         * ============================================================
         * FileAppender pisze WSZYSTKIE logi do JEDNEGO, stalego pliku
         * (<file>sciezka</file>) - plik rosnie w nieskonczonosc, dopoki
         * ktos go recznie nie wyczysci/zarchiwizuje. W praktyce do
         * dlugo dzialajacych aplikacji uzywa sie RollingFileAppender
         * (patrz nizej) - FileAppender jest dobry np. do prostych
         * skryptow/narzedzi jednorazowych.
         */
        System.out.println("\n=== FileAppender: LOGI ZAPISANE DO PRAWDZIWEGO PLIKU NA DYSKU ===");
        demonstrateFileAppender();

        /*
         * ============================================================
         * 📦 RollingFileAppender + rollingPolicy - ROTACJA PLIKOW LOGU
         * ============================================================
         * - RollingFileAppender rozwiazuje problem "plik rosnie w
         *   nieskonczonosc": po przekroczeniu limitu (rozmiaru i/lub
         *   czasu) BIEZACY plik jest "domykany", przenoszony pod NOWA
         *   nazwe (wedlug <fileNamePattern>), a zapis kontynuowany jest
         *   do nowego, pustego pliku o pierwotnej nazwie.
         * - <rollingPolicy class="...TimeBasedRollingPolicy">
         *     Rotacja wg CZASU - typowo raz dziennie. <fileNamePattern>
         *     zawiera %d{yyyy-MM-dd} - kazdy dzien to nowy plik.
         *     <maxHistory>7</maxHistory> - zachowaj max 7 ostatnich dni,
         *     starsze pliki sa automatycznie usuwane.
         * - <rollingPolicy class="...SizeAndTimeBasedRollingPolicy">
         *     Rotacja wg CZASU **I** ROZMIARU jednoczesnie - jesli w
         *     ciagu jednego dnia plik przekroczy <maxFileSize>, ZANIM
         *     minie dzien, i tak zostanie zrotowany. <fileNamePattern>
         *     MUSI wtedy zawierac OBA tokeny: %d{...} ORAZ %i (numer
         *     kolejnego pliku w ramach tego samego dnia, np.
         *     "app.2026-07-09.0.log", "app.2026-07-09.1.log", ...).
         *     <totalSizeCap> ogranicza LACZNY rozmiar WSZYSTKICH
         *     zarchiwizowanych plikow (niezaleznie od <maxHistory>) -
         *     gdy limit zostanie przekroczony, najstarsze pliki znikaja.
         * - Ponizej wymuszamy PRAWDZIWA rotacje w kilka milisekund,
         *   ustawiajac <maxFileSize> na absurdalnie mala wartosc (1KB) -
         *   normalnie w produkcji byloby to np. "100MB".
         */
        System.out.println("\n=== RollingFileAppender: WYMUSZONA, PRAWDZIWA ROTACJA PLIKOW ===");
        demonstrateRollingFileAppender();

        /*
         * ============================================================
         * 🔍 POZIOM PER-LOGGER I HIERARCHIA LOGGEROW
         * ============================================================
         * - Nazwa loggera (LoggerFactory.getLogger(TwojaKlasa.class) ->
         *   pelna, kwalifikowana nazwa klasy, np. "com.shop.service.OrderService")
         *   tworzy NIEJAWNA hierarchie, dokladnie tak, jak PAKIETY w
         *   Javie - segmenty oddzielone kropkami sa "przodkami":
         *     "com.shop"                <- przodek
         *     "com.shop.service"        <- potomek "com.shop"
         *     "com.shop.service.OrderService" <- potomek "com.shop.service"
         * - Kazdy logger ma EFEKTYWNY poziom (getEffectiveLevel()):
         *   jesli logger ma WLASNY, jawnie ustawiony poziom w configu -
         *   uzywa go. Jesli NIE MA wlasnego poziomu - DZIEDZICZY poziom
         *   od NAJBLIZSZEGO PRZODKA, ktory taki poziom ma (az do <root>,
         *   ktory zawsze ma jakis poziom - to "dach" calej hierarchii).
         * - Dzieki temu mozna ustawic np. root=WARN (cicho dla calej
         *   aplikacji), a jednej, konkretnej klasie/pakietowi (np. temu,
         *   ktory akurat diagnozujesz) daj level="DEBUG" - i TYLKO ten
         *   fragment aplikacji zacznie "gadac" wiecej, reszta zostaje cicha.
         */
        System.out.println("\n=== HIERARCHIA LOGGEROW: root=WARN, ale 'com.shop.service'=DEBUG ===");
        demonstrateLoggerHierarchy();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Bez logback.xml, Logback sam wlacza awaryjna konfiguracje
         *   (ConsoleAppender, poziom DEBUG, prosty wzorzec) - to wlasnie
         *   widziales "z automatu" w Lekcji 15.
         * - logback.xml: <configuration> -> <appender> (dokad: Console/
         *   File/RollingFile) -> <encoder><pattern> (jak wyglada wpis) ->
         *   <root level="..."> (domyslny poziom + podpiete appendery).
         * - Tokeny wzorca: %d (data), %thread, %level/%-5level, %logger{N},
         *   %msg, %n (koniecznie na koncu), %X{klucz} (MDC - Lekcja 17).
         * - RollingFileAppender + TimeBasedRollingPolicy (rotacja wg dnia)
         *   lub SizeAndTimeBasedRollingPolicy (rotacja wg dnia I rozmiaru,
         *   wymaga %d ORAZ %i w fileNamePattern) - maxHistory/totalSizeCap
         *   pilnuja, zeby stare logi nie zapelnily dysku.
         * - Poziom mozna ustawic per-logger (<logger name="..." level="...">) -
         *   logger BEZ wlasnego poziomu DZIEDZICZY go po najblizszym
         *   przodku w hierarchii nazw (rozdzielanej kropkami, jak pakiety),
         *   az do <root> - w praktyce: cicha aplikacja + "gadatliwy" jeden,
         *   diagnozowany modul.
         * - W tej lekcji ladowalismy caly config PROGRAMOWO (JoranConfigurator
         *   + LoggerContext.reset()) tylko dla celow demonstracyjnych -
         *   w REALNYM projekcie ta sama tresc XML lezy po prostu w pliku
         *   src/main/resources/logback.xml i Logback wczytuje ja SAM.
         * - W Lekcji 17: MDC (%X{...}) i dobre praktyki logowania.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    /**
     * Laduje podana konfiguracje Logbacka (tresc logback.xml jako String) do
     * biezacego LoggerContext - resetuje poprzednia konfiguracje i wgrywa
     * nowa przez PRAWDZIWY silnik Logbacka (JoranConfigurator), dokladnie
     * tak, jak zrobilby to Logback SAM przy starcie aplikacji z plikiem na
     * dysku. Analogia: ProjectHelper.configureProject(...) w _11_buildtools.
     */
    private static void applyLogbackConfig(String xmlConfig) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset(); // czysci appendery/poziomy z POPRZEDNIEJ konfiguracji
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        try (InputStream inputStream = new ByteArrayInputStream(xmlConfig.getBytes(StandardCharsets.UTF_8))) {
            configurator.doConfigure(inputStream);
        } catch (JoranException | IOException e) {
            throw new RuntimeException("Nie udalo sie zaladowac konfiguracji Logbacka", e);
        }
    }

    private static void printPatternTokensTable() {
        System.out.println("\n=== NAJWAZNIEJSZE TOKENY WZORCA (%pattern) ===");
        String format = "%-16s | %s%n";
        System.out.printf(format, "Token", "Znaczenie");
        System.out.println("-".repeat(70));
        System.out.printf(format, "%d{...}", "data/godzina, np. %d{HH:mm:ss.SSS}");
        System.out.printf(format, "%thread", "nazwa watku, ktory wygenerowal log");
        System.out.printf(format, "%-5level", "poziom logu, wyrownany do 5 znakow");
        System.out.printf(format, "%logger{36}", "nazwa loggera (klasy), skrocona do 36 znakow");
        System.out.printf(format, "%msg / %m", "sama tresc komunikatu");
        System.out.printf(format, "%n", "znak nowej linii - ZAWSZE na koncu wzorca");
        System.out.printf(format, "%X{klucz}", "wartosc z MDC dla podanego klucza (Lekcja 17)");
    }

    private static void demonstrateFileAppender() throws IOException {
        Path logDir = Files.createTempDirectory("lesson16-file-appender");
        Path logFile = logDir.resolve("app.log");
        String logFilePath = logFile.toString().replace("\\", "/");

        applyLogbackConfig("""
                <configuration>
                    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
                        <file>%s</file>
                        <encoder>
                            <pattern>%%d{HH:mm:ss.SSS} %%-5level %%logger{20} - %%msg%%n</pattern>
                        </encoder>
                    </appender>
                    <root level="INFO">
                        <appender-ref ref="FILE"/>
                    </root>
                </configuration>
                """.formatted(logFilePath));

        logger.info("Ten log NIE trafia juz do konsoli - tylko do pliku: {}", logFilePath);
        logger.warn("Drugi wpis do tego samego pliku");
        logger.error("Trzeci wpis - poziom ERROR");

        // Resetujemy kontekst PRZED odczytem, zeby FileAppender zdazyl
        // zamknac/sflushowac strumien do pliku (reset() zatrzymuje appendery).
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();

        System.out.println("Zawartosc pliku " + logFilePath + " (odczytana z powrotem w Javie):");
        List<String> lines = Files.readAllLines(logFile);
        lines.forEach(line -> System.out.println("  | " + line));

        Files.deleteIfExists(logFile);
        Files.deleteIfExists(logDir);
    }

    private static void demonstrateRollingFileAppender() throws IOException {
        Path logDir = Files.createTempDirectory("lesson16-rolling-appender");
        String logDirPath = logDir.toString().replace("\\", "/");

        applyLogbackConfig("""
                <configuration>
                    <appender name="ROLLING" class="ch.qos.logback.core.rolling.RollingFileAppender">
                        <file>%s/app.log</file>
                        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
                            <fileNamePattern>%s/app.%%d{yyyy-MM-dd}.%%i.log</fileNamePattern>
                            <maxFileSize>1KB</maxFileSize>
                            <maxHistory>7</maxHistory>
                            <totalSizeCap>10MB</totalSizeCap>
                        </rollingPolicy>
                        <encoder>
                            <pattern>%%d{HH:mm:ss.SSS} %%-5level - %%msg%%n</pattern>
                        </encoder>
                    </appender>
                    <root level="INFO">
                        <appender-ref ref="ROLLING"/>
                    </root>
                </configuration>
                """.formatted(logDirPath, logDirPath));

        System.out.println("maxFileSize=1KB (celowo malutkie, normalnie np. 100MB) - wymuszamy rotacje w praktyce:");
        for (int i = 0; i < 120; i++) {
            logger.info("Linia loga numer {} - dodatkowa tresc, zeby szybciej przekroczyc limit 1KB", i);
        }

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset(); // zamyka/flushuje appender PRZED odczytem katalogu

        System.out.println("Pliki powstale w katalogu " + logDirPath + " po rotacji (PRAWDZIWA rotacja Logbacka):");
        try (Stream<Path> files = Files.list(logDir)) {
            List<Path> sorted = files.sorted(Comparator.comparing(Path::getFileName)).toList();
            for (Path file : sorted) {
                System.out.printf("  | %-30s (%d B)%n", file.getFileName(), Files.size(file));
            }
            System.out.println("Razem plikow: " + sorted.size() + " (jeden aktywny 'app.log' + reszta zarchiwizowana z numerem %i)");
        }

        try (Stream<Path> files = Files.list(logDir)) {
            for (Path file : files.toList()) {
                Files.deleteIfExists(file);
            }
        }
        Files.deleteIfExists(logDir);
    }

    private static void demonstrateLoggerHierarchy() {
        applyLogbackConfig("""
                <configuration>
                    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
                        <encoder>
                            <pattern>%-5level %logger - %msg%n</pattern>
                        </encoder>
                    </appender>
                    <logger name="com.shop.service" level="DEBUG"/>
                    <root level="WARN">
                        <appender-ref ref="CONSOLE"/>
                    </root>
                </configuration>
                """);

        // "com.shop.service" ma WLASNY, jawny poziom DEBUG w configu powyzej.
        Logger serviceLogger = LoggerFactory.getLogger("com.shop.service");
        // "com.shop.service.OrderService" NIE MA wlasnego poziomu - dziedziczy
        // po najblizszym przodku, czyli "com.shop.service" (DEBUG), NIE po root (WARN).
        Logger orderServiceLogger = LoggerFactory.getLogger("com.shop.service.OrderService");
        // "com.other.unrelated" nie ma zadnego przodka z jawnym poziomem -
        // dziedziczy wprost po <root level="WARN">.
        Logger unrelatedLogger = LoggerFactory.getLogger("com.other.unrelated");

        System.out.println("Efektywny poziom 'com.shop.service': "
                + ((ch.qos.logback.classic.Logger) serviceLogger).getEffectiveLevel());
        System.out.println("Efektywny poziom 'com.shop.service.OrderService' (dziedziczony): "
                + ((ch.qos.logback.classic.Logger) orderServiceLogger).getEffectiveLevel());
        System.out.println("Efektywny poziom 'com.other.unrelated' (dziedziczony po root): "
                + ((ch.qos.logback.classic.Logger) unrelatedLogger).getEffectiveLevel());

        System.out.println("\n-- logi z trzech loggerow o roznym EFEKTYWNYM poziomie --");
        serviceLogger.debug("[com.shop.service] DEBUG - widoczny (wlasny poziom DEBUG)");
        orderServiceLogger.debug("[com.shop.service.OrderService] DEBUG - widoczny (dziedziczy DEBUG po rodzicu)");
        unrelatedLogger.debug("[com.other.unrelated] DEBUG - NIEwidoczny (dziedziczy WARN po root)");
        unrelatedLogger.warn("[com.other.unrelated] WARN - widoczny (spelnia poziom WARN z root)");

        // Level jest tez uzyteczny do porownan programowych, np. przy
        // warunkowym budowaniu drogich argumentow logu (poza zakresem tej
        // lekcji) - tu tylko pokazujemy typ Level.
        Level effective = ((ch.qos.logback.classic.Logger) orderServiceLogger).getEffectiveLevel();
        System.out.println("Czy 'com.shop.service.OrderService' ma wlaczony poziom TRACE? "
                + effective.isGreaterOrEqual(Level.TRACE));
    }
}
