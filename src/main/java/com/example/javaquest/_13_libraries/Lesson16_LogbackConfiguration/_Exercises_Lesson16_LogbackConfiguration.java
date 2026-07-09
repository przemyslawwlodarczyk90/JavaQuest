package com.example.javaquest._13_libraries.Lesson16_LogbackConfiguration;

public class _Exercises_Lesson16_LogbackConfiguration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ApplyLogbackConfigHelper {
        /*
         * 🧪 Zadanie 1:
         * Napisz metode applyLogbackConfig(String xml) analogiczna do tej z
         * teorii (LoggerContext.reset() + JoranConfigurator.doConfigure(...))
         * i uzyj jej, by zaladowac NAJPROSTSZY mozliwy config: jeden
         * ConsoleAppender z wzorcem "%level - %msg%n". Zaloguj jeden
         * komunikat INFO i zweryfikuj format wyjscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FullConsolePattern {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj config XML z ConsoleAppender i wzorcem zawierajacym KOLEJNO:
         * %d{HH:mm:ss}, %thread, %level, %logger{20}, %msg, %n - zaaplikuj
         * go i zaloguj 3 komunikaty INFO o dowolnej tresci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RootLevelWarn {
        /*
         * 🧪 Zadanie 3:
         * Ustaw poziom root loggera na WARN w configu (ConsoleAppender),
         * zaloguj po jednym komunikacie na KAZDYM z 5 poziomow
         * (TRACE/DEBUG/INFO/WARN/ERROR) i zweryfikuj wzrokowo, ze w konsoli
         * pojawiaja sie TYLKO WARN i ERROR.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FileAppenderBasics {
        /*
         * 🧪 Zadanie 4:
         * Utworz FileAppender zapisujacy do pliku w katalogu z
         * Files.createTempDirectory("cwiczenie04"), zaloguj 5 komunikatow
         * INFO, nastepnie odczytaj plik (Files.readAllLines) i wypisz jego
         * zawartosc linia po linii w konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ComparePatterns {
        /*
         * 🧪 Zadanie 5:
         * Zaaplikuj DWIE rozne konfiguracje ConsoleAppender po kolei: jedna
         * z minimalnym wzorcem "%msg%n", druga z pelnym wzorcem
         * (%d/%thread/%level/%logger/%msg/%n) - dla KAZDEJ zaloguj TEN SAM
         * komunikat "Test wzorca" i porownaj roznice w formacie wyjscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PatternTokensExplained {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami znaczenie 6
         * tokenow wzorca: %d, %thread, %level, %logger, %msg, %n - co
         * dokladnie kazdy z nich wstawia do wpisu logu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TwoAppendersAtOnce {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj config z DWOMA appenderami (ConsoleAppender ORAZ
         * FileAppender) podpietymi do TEGO SAMEGO root loggera (dwa
         * appender-ref). Zaloguj jeden komunikat i zweryfikuj, ze pojawia
         * sie I w konsoli, I w pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PerLoggerDebugOverride {
        /*
         * 🧪 Zadanie 8:
         * Ustaw w configu poziom loggera dla nazwy "com.example.demo.PaymentService"
         * na DEBUG, podczas gdy root ma level="INFO". Utworz logger o
         * DOKLADNIE tej nazwie (LoggerFactory.getLogger("com.example.demo.PaymentService")),
         * zaloguj nim komunikat DEBUG i zweryfikuj, ze SIE POJAWIA mimo
         * ciszszego root.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_LevelPaddingDemo {
        /*
         * 🧪 Zadanie 9:
         * Zaaplikuj config z tokenem %-5level w encoderze i zaloguj po
         * jednym komunikacie na poziomach INFO oraz ERROR - zaobserwuj w
         * konsoli, ze slowa "INFO " i "ERROR" zajmuja te sama szerokosc
         * kolumny (wyrownanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AppenderTypesSummary {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu wypisz (min. 4 punkty) roznice miedzy
         * ConsoleAppender, FileAppender i RollingFileAppender - kiedy
         * uzylbys/uzylabys ktorego w prawdziwym projekcie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TimeBasedRollingPolicy {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj RollingFileAppender z TimeBasedRollingPolicy (fileNamePattern
         * zawierajacy %d{yyyy-MM-dd}), maxHistory=7. Zaaplikuj config, zaloguj
         * kilka komunikatow i wypisz w konsoli sciezke AKTYWNEGO pliku logu
         * (elementu <file>).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ForceRealRotation {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj RollingFileAppender z SizeAndTimeBasedRollingPolicy z bardzo
         * malym maxFileSize (np. "500B" lub "1KB") i w petli zaloguj
         * wystarczajaco duzo komunikatow (np. 100-200), by wymusic REALNA
         * rotacje - wypisz liste plikow powstalych w katalogu (Files.list).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareTwoLoggersLevels {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode logAtAllLevels(Logger logger) wywolujaca wszystkie 5
         * poziomow. Skonfiguruj DWA loggery o roznych nazwach - jeden z
         * poziomem DEBUG w configu, drugi z poziomem ERROR - wywolaj
         * logAtAllLevels dla obu i porownaj, ile komunikatow sie pojawilo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_HierarchyInheritance {
        /*
         * 🧪 Zadanie 14:
         * Zademonstruj dziedziczenie w hierarchii loggerow: w configu ustaw
         * <logger name="com.shop" level="INFO"/> (BEZ definiowania
         * "com.shop.cart"). Utworz logger "com.shop.cart" i sprawdz przez
         * ((ch.qos.logback.classic.Logger) logger).getEffectiveLevel(), ze
         * DZIEDZICZY poziom INFO po rodzicu "com.shop".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LoggerLevelOff {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj config z <logger name="com.shop.noisy" level="OFF"/> -
         * zaloguj tym loggerem po jednym komunikacie na WSZYSTKICH 5
         * poziomach (wlacznie z ERROR) i zweryfikuj, ze ANI JEDEN sie nie
         * pojawia w konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ContextResetBetweenConfigs {
        /*
         * 🧪 Zadanie 16:
         * Zaaplikuj DWIE rozne konfiguracje po kolei w tym samym main()
         * (najpierw ConsoleAppender, potem - po kolejnym reset() -
         * FileAppender BEZ konsoli). Zaloguj komunikat PO drugiej
         * konfiguracji i zweryfikuj, ze NIE trafia juz do konsoli, tylko do
         * pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TotalLogFileSize {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj RollingFileAppender, zaloguj wystarczajaco duzo komunikatow
         * by powstalo min. 3 pliki, a nastepnie zsumuj i wypisz LACZNY
         * rozmiar wszystkich plikow .log w katalogu (Files.list + Files.size).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BenchmarkConsoleVsFile {
        /*
         * 🧪 Zadanie 18:
         * Zmierz (System.nanoTime()) czas potrzebny na zalogowanie 10 000
         * komunikatow do ConsoleAppender vs 10 000 do FileAppender (bez
         * wypisywania tresci pliku w konsoli po fakcie) - wypisz oba czasy w
         * ms i skomentuj roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ThreeModulesThreeLevels {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj scenariusz: aplikacja z 3 "modulami" (loggery
         * "app.auth", "app.orders", "app.payments"), kazdy skonfigurowany w
         * XML na INNY poziom (auth=WARN, orders=INFO, payments=DEBUG).
         * Zaloguj po komunikacie z kazdego na WSZYSTKICH poziomach i
         * zweryfikuj roznice w liczbie widocznych wpisow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ConsoleAndFileDifferentPatterns {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj config z DWOMA appenderami podpietymi do root - Console z
         * KROTKIM wzorcem ("%level - %msg%n"), File z PELNYM wzorcem
         * (data/watek/poziom/logger/msg). Zaloguj jeden komunikat i
         * porownaj (odczytujac plik) roznice w formacie miedzy konsola a
         * plikiem.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ConfigBuilderMethod {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode buildConsoleOnlyConfig(String pattern, String rootLevel)
         * zwracajaca String z gotowym XML configu (sparametryzowany wzorzec
         * i poziom root) - uzyj jej do wygenerowania i zaaplikowania 3
         * roznych konfiguracji w petli (rozne pattern/rootLevel w kazdej
         * iteracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TotalSizeCapDiscussion {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj RollingFileAppender z totalSizeCap ustawionym na mala
         * wartosc (np. "5KB") oraz maxHistory - wygeneruj DUZO logow (by
         * przekroczyc cap). W komentarzu (dokumentacyjnie, bo sprzatanie
         * archiwum jest asynchroniczne w Logbacku) opisz, jak totalSizeCap
         * ograniczalby miejsce na dysku w REALNEJ, dlugo dzialajacej
         * aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RuntimeLevelSwitch {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj "przelacznik debugowania w locie": metoda
         * setLoggerLevel(String loggerName, String level) budujaca i
         * aplikujaca config na nowo z wybranym poziomem dla danego loggera
         * (bez restartu JVM) - zademonstruj wlaczenie DEBUG dla jednego
         * loggera w trakcie dzialania programu (najpierw log DEBUG
         * niewidoczny, potem po zmianie widoczny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HandleJoranException {
        /*
         * 🧪 Zadanie 24:
         * Celowo wprowadz blad skladniowy w XML configu (np. brakujacy tag
         * zamykajacy </appender>) i przechwyc wyjatek JoranException przy
         * probie zaladowania - wypisz przyjazny komunikat bledu zamiast
         * pozwolic aplikacji sie wywalic z surowym stack trace'em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TwoFilesDifferentThresholds {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj config z DWOMA appenderami PLIKOWYMI (dwa rozne pliki), z
         * ktorych KAZDY ma inny <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
         * (jeden zbiera tylko ERROR, drugi wszystko od DEBUG) - zaloguj
         * komunikaty na roznych poziomach i zweryfikuj zawartosc OBU plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DateAndIndexRollover {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj klase "OrderProcessor" z wlasnym loggerem, skonfiguruj
         * RollingFileAppender z fileNamePattern zawierajacym I date, I numer
         * (%d{yyyy-MM-dd}.%i), wygeneruj wystarczajaco duzo logow, by powstaly
         * min. 3 zarchiwizowane pliki - wypisz ich nazwy w kolejnosci
         * powstania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PatternSizeComparison {
        /*
         * 🧪 Zadanie 27:
         * Porownaj (empirycznie, kodem) rozmiar pliku logu przy wzorcu
         * "%msg%n" (minimalny) vs pelnym wzorcem (%d/%thread/%level/%logger/%msg/%n)
         * dla TYCH SAMYCH 1000 komunikatow - wypisz roznice w bajtach i
         * wyjasnij w komentarzu, dlaczego bogatszy wzorzec kosztuje wiecej
         * miejsca na dysku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_LayeredMicroserviceConfig {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj i zaimplementuj config odpowiedni dla mikroserwisu z 3
         * warstwami (controller/service/repository) - root=INFO, ale
         * warstwa repository (np. "com.shop.repository") ma level="DEBUG"
         * (do diagnozowania zapytan) - zweryfikuj dzialaniem (loggery dla
         * kazdej warstwy, po komunikacie DEBUG i INFO z kazdej).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ReconfigurationOverhead {
        /*
         * 🧪 Zadanie 29:
         * Zmierz narzut czasowy SAMEGO context.reset() + JoranConfigurator.doConfigure(...)
         * (bez zadnego logowania) wywolany 100 razy pod rzad - wypisz sredni
         * czas jednej rekonfiguracji w ms lub mikrosekundach. W komentarzu
         * skomentuj, czy to operacja "tania" czy "droga" i dlaczego NIE robi
         * sie jej przy KAZDYM pojedynczym logu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ProductionLikeConfigCapstone {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zbuduj kompletna konfiguracje
         * "produkcyjna" dla fikcyjnej aplikacji e-commerce: ConsoleAppender
         * (poziom INFO) do szybkiego podgladu, RollingFileAppender z
         * SizeAndTimeBasedRollingPolicy (rotacja dzienna + limit rozmiaru,
         * maxHistory=30, totalSizeCap="200MB") dla pelnej historii, root=INFO,
         * logger "com.shop.payments"=DEBUG (bo to newralgiczny modul) -
         * zademonstruj dzialanie na symulowanym przeplywie zamowienia
         * (min. 5 logow z roznych "warstw" aplikacji).
         */
        public static void main(String[] args) { }
    }
}
