package com.example.javaquest._21_spring_boot.Lesson10_LoggingInSpringBoot;

public class _Exercises_Lesson10_LoggingInSpringBoot {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDefaultLogbackAutoConfiguration {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, co dokladnie auto-konfiguruje
         * `spring-boot-starter` w kwestii logowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_LogAtAllFiveLevels {
        /*
         * 🧪 Zadanie 2:
         * Zaloguj komunikat na WSZYSTKICH 5 poziomach SLF4J (TRACE/
         * DEBUG/INFO/WARN/ERROR) - zaobserwuj, KTORE sa domyslnie WIDOCZNE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ChangeLogLevelForOwnPackage {
        /*
         * 🧪 Zadanie 3:
         * Odtworz demo zmiany poziomu (`logging.level.*`) dla WLASNEGO
         * pakietu/klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SetRootLogLevelToWarn {
        /*
         * 🧪 Zadanie 4:
         * Ustaw `logging.level.root=WARN` - zweryfikuj, ze KOMUNIKATY
         * INFO Springa (np. baner startu) TEZ znikaja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainLoggerHierarchyInheritance {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, jak DZIEDZICZENIE poziomow logowania
         * dziala miedzy pakietami (np. `com.example` a
         * `com.example.javaquest`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareColoredVsPlainConsoleOutput {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala (dokumentacja): sprawdz `spring.output.ansi.enabled` -
         * jak WYLACZYC kolorowy output (np. dla logow zapisywanych do
         * pliku)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_LogExceptionWithStackTrace {
        /*
         * 🧪 Zadanie 7:
         * Zaloguj WYJATEK (nie tylko komunikat) - zweryfikuj, ze PELNY
         * stack trace pojawia sie w konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareLoggingLevelPropertyNamingConventions {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: porownaj `logging.level.com.example=DEBUG` z
         * `logging.level.root=DEBUG` - co ROZNI ich zasieg?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_LogParametrizedMessageWithPlaceholders {
        /*
         * 🧪 Zadanie 9:
         * Zaloguj komunikat z PLACEHOLDERAMI SLF4J (`log.info("Wartosc:
         * {}", x)`) - wyjasnij, DLACZEGO to LEPSZE niz konkatenacja
         * Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListDifferenceBetweenLogbackLog4j2AndJul {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (dokumentacja): Boot wspiera TEZ Log4j2 i JUL -
         * jak PRZELACZYC sie z Logbacka (domyslnego) na Log4j2?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_WriteLogsToFileInsteadOfConsole {
        /*
         * 🧪 Zadanie 11:
         * Ustaw `logging.file.name=...` - zweryfikuj, ze logi TRAFIAJA
         * do PLIKU (nie tylko konsoli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConfigureLogRotationViaProperties {
        /*
         * 🧪 Zadanie 12:
         * Skonfiguruj rotacje plikow logu (`logging.logback.rollingpolicy.*`)
         * PRZEZ WLASCIWOSCI, BEZ pisania `logback-spring.xml`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementMdcIntegrationWithSpringBoot {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_13_libraries/Lesson17` - uzyj MDC W KONTEKSCIE
         * Spring Boota (np. w `CommandLineRunner`, zapowiedz `Lesson07`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CreateCustomLogbackSpringXmlFile {
        /*
         * 🧪 Zadanie 14:
         * Utworz WLASNY `logback-spring.xml` z placeholderem `${...}`
         * odczytujacym wlasciwosc Springa - zweryfikuj poprawne
         * dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementProfileSpecificLoggingConfiguration {
        /*
         * 🧪 Zadanie 15:
         * Skonfiguruj RÓZNY poziom logowania per profil (powiaz z
         * `Lesson06`) - "dev" = DEBUG, "prod" = WARN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementJsonFormattedLogOutput {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala (dokumentacja): sprawdz, jak skonfigurowac
         * logowanie w formacie JSON (np. dla agregatorow ELK/Splunk).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureLoggingOverheadAtHighVolume {
        /*
         * 🧪 Zadanie 17:
         * Zmierz czas 100 000 wywolan `log.info(...)` - jaki jest
         * narzut logowania na wydajnosc?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConditionalDebugLoggingWithIsDebugEnabled {
        /*
         * 🧪 Zadanie 18:
         * Uzyj `log.isDebugEnabled()` PRZED KOSZTOWNYM budowaniem
         * komunikatu DEBUG - wyjasnij, KIEDY to WAZNE (dla wydajnosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareApplicationLogsWithAuditLogsFromSecurityBasics {
        /*
         * 🧪 Zadanie 19:
         * Powiaz z `_19_security_basics/Lesson19_
         * SecureLoggingAndAuditing` - jak POLACZYC zwykle logi Boota z
         * dziennikiem audytu (osobny kanal)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildLoggingConfigurationReferenceTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape) "sciage" najwazniejszych wlasciwosci
         * `logging.*` z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomLogbackAppenderProgrammatically {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY, programowy `Appender` Logbacka (jak w
         * `_13_libraries/Lesson16`) dolaczony do konfiguracji Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementStructuredLoggingWithKeyValuePairs {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj "structured logging" (Boot 3.4+ ma wbudowane
         * wsparcie) - klucz-wartosc zamiast wolnego tekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementLogSamplingForHighVolumeEndpoints {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj "sampling" logow (loguj tylko co N-te zadanie) -
         * dla ENDPOINTU o BARDZO WYSOKIEJ czestotliwosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementDynamicLogLevelChangeAtRuntime {
        /*
         * 🧪 Zadanie 24:
         * Uzyj Actuatora (`/actuator/loggers`, zapowiedz `Lesson12`) -
         * ZMIEN poziom logowania W TRAKCIE dzialania aplikacji, BEZ
         * restartu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCorrelationIdPropagationAcrossServices {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj propagacje "correlation ID" (MDC) MIEDZY
         * symulowanymi "serwisami" (wywolania metod) - powiaz z
         * distributed tracing (zapowiedz `Lesson13`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareLoggingPerformanceAcrossFrameworks {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala (dokumentacja): porownaj wydajnosc Logback vs
         * Log4j2 (asynchroniczne appendery) - kiedy ROZNICA ma
         * znaczenie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSensitiveDataMaskingInLogPattern {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_19_security_basics/Lesson19` - zaimplementuj WLASNY
         * konwerter Logbacka MASKUJACY wzorce wygladajace jak
         * hasla/karty platnicze w KAZDYM logowanym komunikacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementLogAggregationSimulation {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj (WLASNY, prosty "kolektor") agregacje logow z
         * WIELU "instancji" (watkow) do JEDNEGO, uporzadkowanego
         * strumienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementLogBasedAlertingRule {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj prosta regule ALERTUJACA (wypisujaca
         * ostrzezenie) na podstawie CZESTOTLIWOSCI logow ERROR w oknie
         * czasowym - powiaz z `_19_security_basics/Lesson19`, Zadanie 25.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteObservableLoggingSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system logowania - profile-owe poziomy
         * (Lesson06) + MDC + maskowanie danych wrazliwych + rotacja
         * plikow - jeden, spojny, produkcyjnie gotowy setup.
         */
        public static void main(String[] args) { }
    }
}
