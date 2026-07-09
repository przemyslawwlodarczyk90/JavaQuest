package com.example.javaquest._13_libraries.Lesson29_PicocliBasics;

public class _Exercises_Lesson29_PicocliBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_HelloCommandWithNameOption {
        /*
         * 🧪 Zadanie 1:
         * Utwórz klasę HelloCommand (@Command, implements Runnable) z opcją
         * @Option(names = {"-n", "--name"}, defaultValue = "Kursant"). W run()
         * wypisz "Czesc, " + name + "!". Uruchom przez
         * new CommandLine(new HelloCommand()).execute(new String[]{"--name", "Ola"}).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HelloCommandWithDefaultValue {
        /*
         * 🧪 Zadanie 2:
         * Uruchom HelloCommand z Zadania 1 z PUSTĄ tablicą argumentów
         * (new String[]{}) - zweryfikuj, że wypisze się "Czesc, Kursant!"
         * dzięki defaultValue.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ShortAndLongOptionAliases {
        /*
         * 🧪 Zadanie 3:
         * Uruchom HelloCommand DWA razy - raz z tablicą {"-n", "Ala"}, raz z
         * {"--name", "Ala"} - zweryfikuj, że OBA warianty (krótki i długi alias)
         * dają IDENTYCZNY wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IntOptionWithAutomaticConversion {
        /*
         * 🧪 Zadanie 4:
         * Utwórz klasę RepeatCommand z @Option(names = "--times") int times
         * (defaultValue = "1") i @Option(names = "--text") String text. W run()
         * wypisz text "times" razy. Uruchom z {"--text", "Hej", "--times", "3"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BooleanFlagOption {
        /*
         * 🧪 Zadanie 5:
         * Dodaj do RepeatCommand z Zadania 4 opcję
         * @Option(names = {"-u", "--uppercase"}) boolean uppercase - jeśli true,
         * wypisuj text.toUpperCase(). Uruchom z {"--text", "cisza", "-u"} oraz
         * bez flagi -u, porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MixinStandardHelpOptionsHelp {
        /*
         * 🧪 Zadanie 6:
         * Dodaj mixinStandardHelpOptions = true do @Command RepeatCommand oraz
         * atrybut description dla KAŻDEJ opcji. Uruchom komendę z
         * {"--help"} i zweryfikuj, że Picocli sam wygenerował czytelny tekst
         * pomocy na podstawie adnotacji description.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VersionOption {
        /*
         * 🧪 Zadanie 7:
         * Dodaj version = "repeat 2.0" do @Command RepeatCommand (wymaga
         * mixinStandardHelpOptions = true, patrz Zadanie 6). Uruchom z
         * {"--version"} i zweryfikuj wypisany numer wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PositionalParameterSingle {
        /*
         * 🧪 Zadanie 8:
         * Utwórz klasę WordCountCommand z @Parameters(index = "0") String text
         * (BEZ nazwy flagi - argument pozycyjny). W run() wypisz liczbę słów
         * (text.split(" ").length). Uruchom z {"To jest test picocli"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TwoPositionalParameters {
        /*
         * 🧪 Zadanie 9:
         * Utwórz klasę AddCommand z DWOMA argumentami pozycyjnymi typu int:
         * @Parameters(index = "0") int a, @Parameters(index = "1") int b. W
         * run() wypisz a + b. Uruchom z {"5", "7"} - zweryfikuj, że Picocli SAM
         * skonwertował Stringi na int (bez Integer.parseInt w Twoim kodzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CallableReturningExitCode {
        /*
         * 🧪 Zadanie 10:
         * Przerób AddCommand z Zadania 9 na implements Callable<Integer> -
         * call() zwraca 0, jeśli a + b >= 0, a 1 w przeciwnym razie. Uruchom z
         * {"-10", "3"} i wypisz kod wyjścia zwrócony przez execute(...).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RequiredOptionMissing {
        /*
         * 🧪 Zadanie 11:
         * Utwórz klasę DeployCommand z @Option(names = "--env", required = true)
         * String env. Uruchom BEZ podania --env (new String[]{}) - wypisz kod
         * wyjścia zwrócony przez execute(...) i zweryfikuj, że jest niezerowy
         * (Picocli sam odrzucił wywołanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RequiredOptionProvided {
        /*
         * 🧪 Zadanie 12:
         * Uruchom DeployCommand z Zadania 11 z {"--env", "produkcja"} -
         * zweryfikuj, że run() wykonał się poprawnie i execute() zwróciło 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FileOptionAutomaticConversion {
        /*
         * 🧪 Zadanie 13:
         * Utwórz klasę ReadConfigCommand z @Option(names = "--config") File
         * config. W run() wypisz config.getName() oraz config.exists(). Uruchom
         * z {"--config", "nieistniejacy-plik.yaml"} i zweryfikuj obie wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_EnumOptionValidValue {
        /*
         * 🧪 Zadanie 14:
         * Utwórz enum Environment {DEV, TEST, PROD} oraz klasę EnvCommand z
         * @Option(names = "--env", defaultValue = "DEV") Environment env.
         * Uruchom z {"--env", "PROD"} - zweryfikuj automatyczną konwersję
         * String -> enum.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_EnumOptionInvalidValue {
        /*
         * 🧪 Zadanie 15:
         * Uruchom EnvCommand z Zadania 14 z {"--env", "STAGING"} (wartość, KTÓREJ
         * NIE MA w enumie Environment) - wypisz kod wyjścia zwrócony przez
         * execute(...) i zapisz w komentarzu, że Picocli sam wykrył błędną
         * wartość enuma (błąd konwersji typu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ArityFixedTwoValues {
        /*
         * 🧪 Zadanie 16:
         * Utwórz klasę RangeCommand z @Option(names = "--range", arity = "2")
         * int[] range (dokładnie dwie wartości: od-do). W run() wypisz
         * "Zakres: " + range[0] + "-" + range[1]. Uruchom z
         * {"--range", "10", "20"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ArityVariableListOption {
        /*
         * 🧪 Zadanie 17:
         * Utwórz klasę EmailCommand z @Option(names = "--to", arity = "1..*")
         * List<String> recipients. W run() wypisz liczbę odbiorców i całą listę.
         * Uruchom z {"--to", "a@firma.pl", "b@firma.pl", "c@firma.pl"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OptionalArityZeroOrOne {
        /*
         * 🧪 Zadanie 18:
         * Utwórz klasę BackupCommand z @Option(names = "--compress",
         * arity = "0..1", defaultValue = "gzip") String compress. Uruchom TRZY
         * razy: bez --compress, z samym "--compress" (bez wartości), z
         * {"--compress", "zip"} - porównaj wyniki w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DescriptionInGeneratedHelp {
        /*
         * 🧪 Zadanie 19:
         * Weź dowolną komendę z tej lekcji (np. DeployCommand z Zadania 11),
         * dodaj description = "..." zarówno w @Command jak i w KAŻDEJ @Option.
         * Uruchom z {"--help"} i zweryfikuj, że wszystkie opisy pojawiają się w
         * wygenerowanym tekście pomocy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareManualVsPicocliLineCount {
        /*
         * 🧪 Zadanie 20:
         * Napisz RĘCZNY parser (bez Picocli, jak demonstrateManualParsingPain()
         * w lekcji) obsługujący TE SAME opcje co RepeatCommand z Zadania 4/5
         * (--text, --times, -u/--uppercase) WŁĄCZNIE z obsługą --help. Porównaj
         * w komentarzu liczbę linii kodu ręcznego parsera vs klasy z adnotacjami
         * Picocli.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultipleOptionsWithMixedTypes {
        /*
         * 🧪 Zadanie 21:
         * Utwórz klasę CreateUserCommand z opcjami: String username (required),
         * int age (defaultValue "18"), boolean active (defaultValue "true"),
         * File avatarFile (opcjonalny). Zademonstruj DWA wywołania: pełne (ze
         * wszystkimi opcjami) i minimalne (tylko username) - porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RunnableVsCallableComparison {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj DWIE wersje tej samej komendy walidującej wiek (>= 18):
         * AgeCheckRunnable (implements Runnable, wypisuje wynik przez
         * System.out) i AgeCheckCallable (implements Callable<Integer>, zwraca
         * 0/1 jako kod wyjścia). Uruchom obie z tym samym argumentem i porównaj
         * w komentarzu, kiedy lepiej użyć której formy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ArrayOfPositionalParametersUnlimited {
        /*
         * 🧪 Zadanie 23:
         * Utwórz klasę SumCommand z @Parameters(arity = "1..*") int[] numbers
         * (pozycyjne, DOWOLNA ilość liczb, BEZ flagi). W run() wypisz sumę.
         * Uruchom z {"1", "2", "3", "4", "5"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CombiningPositionalAndNamedOptions {
        /*
         * 🧪 Zadanie 24:
         * Utwórz klasę ArchiveCommand łączącą @Parameters(index = "0") File
         * sourceDir (pozycyjny) z @Option(names = "--format", defaultValue =
         * "zip") String format i @Option(names = "-v") boolean verbose
         * (nazwane). Uruchom z {"projekt/", "--format", "tar", "-v"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TestingCommandAsUnitTestStyle {
        /*
         * 🧪 Zadanie 25:
         * Napisz statyczną metodę pomocniczą
         * assertExitCode(int expected, String[] cliArgs, Object command) w klasie
         * ćwiczenia, która wywołuje new CommandLine(command).execute(cliArgs) i
         * wypisuje "OK"/"BLAD" w zależności od zgodności z oczekiwanym kodem
         * wyjścia. Użyj jej do przetestowania 3 różnych scenariuszy dowolnej
         * komendy z tej lekcji (sukces, brak wymaganej opcji, zły typ danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CustomExitCodeBasedOnBusinessLogic {
        /*
         * 🧪 Zadanie 26:
         * Utwórz klasę DivideCommand (Callable<Integer>) z dwoma opcjami int
         * dividend, int divisor. Jeśli divisor == 0, wypisz błąd i zwróć 2 (bez
         * rzucania wyjątku) zamiast dzielenia - w przeciwnym razie wypisz wynik
         * i zwróć 0. Zademonstruj OBA scenariusze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ReusableOptionsViaMixinField {
        /*
         * 🧪 Zadanie 27:
         * Utwórz klasę CommonOptions (zwykła klasa, BEZ @Command) z polem
         * @Option(names = "--verbose") boolean verbose. W komendzie
         * ReportCommand dodaj pole @CommandLine.Mixin CommonOptions common -
         * zweryfikuj, że opcja --verbose z CommonOptions działa TAK, jakby była
         * zadeklarowana bezpośrednio w ReportCommand.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_NegativeNumberAsOptionValue {
        /*
         * 🧪 Zadanie 28:
         * Utwórz klasę TemperatureCommand z @Option(names = "--celsius") int
         * celsius. Uruchom z {"--celsius", "-15"} (wartość UJEMNA) - zweryfikuj
         * w komentarzu, czy Picocli poprawnie odróżnia "-15" jako WARTOŚĆ opcji
         * od nowej flagi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_HelpTextForCommandWithManyOptions {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj komendę ServerCommand z co najmniej 5 różnymi opcjami (String
         * host, int port, boolean ssl, File certFile, enum LogLevel logLevel),
         * KAŻDA z opisowym description i sensownym defaultValue. Uruchom
         * {"--help"} i sprawdź, czy pomoc jest czytelna i kompletna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCliAppSimulationEndToEnd {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletną, pojedynczą komendę FileStatsCommand symulującą
         * mini-narzędzie CLI: @Parameters(index = "0") File file (plik do
         * "analizy"), @Option(names = "--unit", defaultValue = "BYTES") enum
         * SizeUnit {BYTES, KB, MB}, @Option(names = {"-v","--verbose"}) boolean
         * verbose, mixinStandardHelpOptions = true. Zademonstruj PEŁNY zestaw
         * wywołań: sukces, --help, błąd konwersji enuma, brakujący argument
         * pozycyjny - wypisz kod wyjścia dla każdego.
         */
        public static void main(String[] args) { }
    }
}
