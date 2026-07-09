package com.example.javaquest._13_libraries.Lesson29_PicocliBasics;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class _Lesson29_PicocliBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 29: PICOCLI - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 PROBLEM: RĘCZNE PARSOWANIE String[] args
         * ============================================================
         * - Metoda main(String[] args) dostaje argumenty wiersza poleceń
         *   jako zwykłą tablicę Stringów - Java NIE parsuje ich za Ciebie.
         *   Chcesz opcję "--name Kasia" albo "--count 3"? Musisz sam(a)
         *   napisać pętlę, która przegląda tablicę, porównuje Stringi i
         *   ręcznie konwertuje typy.
         * - Poniżej naiwna, RĘCZNA implementacja takiego parsowania -
         *   zobacz, ile tu ukrytych problemów.
         */
        demonstrateManualParsingPain();

        /*
         * ============================================================
         * 🔍 CZYM JEST PICOCLI?
         * ============================================================
         * - Picocli (info.picocli:picocli) to biblioteka do budowania
         *   aplikacji CLI (Command Line Interface) w Javie - zamiast
         *   ręcznie przeglądać String[] args, OPISUJESZ oczekiwane
         *   opcje/argumenty adnotacjami na zwykłej klasie, a Picocli sam
         *   je parsuje, WALIDUJE i KONWERTUJE na właściwe typy.
         * - Rozwiązuje DOKŁADNIE problemy z demonstrateManualParsingPain():
         *   automatyczna konwersja typów (String -> int/boolean/File/enum...),
         *   automatyczne --help i --version, czytelne komunikaty błędów,
         *   wsparcie dla aliasów opcji (-n / --name), wartości domyślne,
         *   walidacja wymaganych opcji - i to wszystko z JEDNEJ, deklaratywnej
         *   definicji klasy, bez pętli if/else.
         * - Jest to jedna z najpopularniejszych bibliotek CLI w Javie -
         *   używana m.in. przez narzędzia takie jak Micronaut CLI, Quarkus CLI
         *   czy Spring Boot CLI.
         */
        System.out.println("\nPicocli = opisujesz opcje ADNOTACJAMI na klasie, biblioteka sama parsuje/waliduje/konwertuje.");

        /*
         * ============================================================
         * 📦 @Command + @Option + @Parameters: PIERWSZA KOMENDA
         * ============================================================
         * - @Command na klasie oznacza ją jako komendę Picocli - atrybut
         *   "name" to nazwa wyświetlana w pomocy, "description" trafia do
         *   automatycznie generowanego tekstu --help.
         * - @Option(names = {"-n", "--name"}) na polu deklaruje NAZWANĄ
         *   opcję - można ją podać jako krótki alias (-n) LUB długą formę
         *   (--name), Picocli obsługuje OBIE automatycznie z JEDNEJ adnotacji.
         * - mixinStandardHelpOptions = true DODAJE za darmo dwie standardowe
         *   opcje: -h/--help (wypisuje wygenerowaną pomoc i kończy z kodem 0)
         *   oraz -V/--version (wypisuje wartość atrybutu "version" z @Command).
         * - Klasa komendy implementuje Runnable (metoda run(), bez zwracanej
         *   wartości) LUB Callable<Integer> (metoda call(), zwraca kod
         *   wyjścia) - Picocli wywoła jedną z nich PO udanym sparsowaniu
         *   argumentów.
         * - Uruchamiamy komendę przez:
         *     new CommandLine(new GreetCommand()).execute(argsArray)
         *   execute(...) parsuje argumenty, wywołuje run()/call() i ZWRACA
         *   int - kod wyjścia (exit code): 0 = sukces, inna wartość = błąd
         *   (w tym błędy walidacji, które Picocli obsłuży SAM, patrz niżej).
         * - Ponieważ ta lekcja to main() uruchamiany w IDE (nie prawdziwy
         *   terminal), SYMULUJEMY wywołania z wiersza poleceń, przekazując
         *   RĘCZNIE spreparowane tablice String[] do execute(...) - to
         *   dokładnie ten sam sposób, w jaki testowałbyś komendę Picocli w
         *   teście jednostkowym (bez odpalania prawdziwego terminala).
         */
        System.out.println("\n=== GreetCommand: --name i --count (@Option) ===");
        String[] greetArgs = new String[]{"--name", "Kasia", "--count", "3"};
        int greetExitCode = new CommandLine(new GreetCommand()).execute(greetArgs);
        System.out.println("Kod wyjscia execute(): " + greetExitCode);

        /*
         * ============================================================
         * 🔹 defaultValue: WARTOŚCI DOMYŚLNE OPCJI
         * ============================================================
         * - Atrybut defaultValue na @Option ustawia wartość pola, gdy
         *   opcja NIE zostanie podana w wierszu poleceń - w GreetCommand
         *   "--name" domyślnie to "Swiecie", a "--count" domyślnie "1".
         * - Poniżej wywołujemy komendę BEZ żadnych argumentów - obie
         *   wartości domyślne zostaną użyte automatycznie.
         */
        System.out.println("\n=== GreetCommand BEZ argumentow (uzyte defaultValue) ===");
        String[] emptyArgs = new String[]{};
        new CommandLine(new GreetCommand()).execute(emptyArgs);

        /*
         * ============================================================
         * 🔍 mixinStandardHelpOptions: --help i --version ZA DARMO
         * ============================================================
         * - Dzięki mixinStandardHelpOptions = true w @Command, "--help"
         *   automatycznie wypisuje wygenerowaną (na podstawie adnotacji!)
         *   pomoc - nazwy opcji, ich opisy (description) i wartości
         *   domyślne - BEZ pisania ani jednej linijki kodu formatującej
         *   ten tekst.
         * - "--version" wypisuje wartość atrybutu version z @Command
         *   (tutaj: "greet 1.0").
         * - W OBU przypadkach Picocli NIE wywołuje run()/call() - po
         *   prostu wypisuje pomoc/wersję i execute() zwraca kod 0.
         */
        System.out.println("\n=== GreetCommand --help (automatycznie wygenerowana pomoc) ===");
        String[] helpArgs = new String[]{"--help"};
        new CommandLine(new GreetCommand()).execute(helpArgs);

        System.out.println("\n=== GreetCommand --version ===");
        String[] versionArgs = new String[]{"--version"};
        new CommandLine(new GreetCommand()).execute(versionArgs);

        /*
         * ============================================================
         * 📦 @Parameters: ARGUMENTY POZYCYJNE + Callable<Integer>
         * ============================================================
         * - @Parameters (bez "names") to argument POZYCYJNY - identyfikowany
         *   MIEJSCEM w wierszu poleceń (index = "0", "1", ...), NIE nazwą
         *   flagi. Typowy przykład: "cp zrodlo.txt cel.txt" - "zrodlo.txt"
         *   i "cel.txt" nie mają żadnej flagi przed sobą.
         * - CopyCommand implementuje Callable<Integer> zamiast Runnable -
         *   metoda call() zwraca int, który STAJE SIĘ kodem wyjścia
         *   zwróconym przez execute(...). To wygodne, gdy logika komendy
         *   sama chce zdecydować o sukcesie/porażce (np. 0 = OK, 1 = błąd).
         * - Pole typu File - Picocli SAM konwertuje String na java.io.File
         *   (podobnie jak zamieni "3" na int w GreetCommand) - to jest
         *   WŁAŚNIE automatyczna konwersja typów, jedna z głównych korzyści
         *   Picocli względem ręcznego parsowania.
         */
        System.out.println("\n=== CopyCommand: @Parameters pozycyjne + Callable<Integer> ===");
        String[] copyArgs = new String[]{"zrodlo-nieistniejace.txt", "backup/zrodlo.txt", "--verbose"};
        int copyExitCode = new CommandLine(new CopyCommand()).execute(copyArgs);
        System.out.println("Kod wyjscia call(): " + copyExitCode + " (1 = plik zrodlowy nie istnieje)");

        /*
         * ============================================================
         * 🔍 required = true: OPCJA OBOWIĄZKOWA
         * ============================================================
         * - required = true na @Option oznacza, że Picocli ODRZUCI
         *   wywołanie, jeśli ta opcja nie zostanie podana - zamiast
         *   cichego "null" (jak w ręcznym parsowaniu) dostajemy JAWNY
         *   błąd walidacji, ZANIM run()/call() w ogóle się wykona.
         * - Domyślnie execute(...) SAM obsługuje ten błąd: wypisuje
         *   czytelny komunikat na System.err, dorzuca podpowiedź "Usage:"
         *   i zwraca NIEZEROWY kod wyjścia - nie musisz pisać własnego
         *   try/catch, żeby to zadziałało (choć w Lekcji 30 zobaczysz,
         *   jak przejąć tę obsługę ręcznie, gdy potrzeba więcej kontroli).
         */
        System.out.println("\n=== SetLogLevelCommand BEZ wymaganej opcji --input (required = true) ===");
        String[] missingRequiredArgs = new String[]{"--level", "DEBUG"};
        int missingRequiredExitCode = new CommandLine(new SetLogLevelCommand()).execute(missingRequiredArgs);
        System.out.println("Kod wyjscia (required=true, brak --input): " + missingRequiredExitCode + " (niezerowy = blad walidacji)");

        /*
         * ============================================================
         * 🔹 AUTOMATYCZNA KONWERSJA TYPÓW: String -> enum, File, int, boolean
         * ============================================================
         * - Picocli rozpoznaje typ pola i SAM dobiera konwersję:
         *     * String   -> bez zmian,
         *     * int/long/double... -> Integer.parseInt/Long.parseLong/...,
         *     * boolean  -> flaga BEZ wartości (samo podanie "-v" ustawia true),
         *     * File/Path -> opakowanie Stringa w java.io.File / java.nio.file.Path,
         *     * enum     -> Enum.valueOf(...) (String MUSI pasować do jednej
         *       ze stałych enuma, inaczej Picocli zwróci błąd konwersji).
         * - Poniżej SetLogLevelCommand z POPRAWNYMI argumentami - "WARN"
         *   zostaje skonwertowane na stałą LogLevel.WARN, a "app.log" na
         *   obiekt File.
         */
        System.out.println("\n=== SetLogLevelCommand: konwersja String -> enum (LogLevel) i String -> File ===");
        String[] logLevelArgs = new String[]{"--level", "WARN", "--input", "app.log"};
        new CommandLine(new SetLogLevelCommand()).execute(logLevelArgs);

        /*
         * ============================================================
         * 🔍 arity: ILE WARTOŚCI PRZYJMUJE JEDNA OPCJA
         * ============================================================
         * - arity ("arguments' cardinality") opisuje, ILE wartości opcja
         *   przyjmuje. Domyślnie opcja typu List/tablica ma arity "1"
         *   (jedna flaga = jedna wartość, więc trzeba by powtarzać flagę:
         *   "-t java -t spring -t docker").
         * - arity = "1..*" oznacza "co najmniej jedna, dowolnie wiele" -
         *   dzięki temu JEDNA flaga zbiera WSZYSTKIE wartości aż do
         *   kolejnej flagi lub końca argumentów: "-t java spring docker".
         * - Inne przydatne wartości arity: "0" (flaga bez wartości, jak
         *   boolean), "0..1" (opcjonalna pojedyncza wartość), "2" (zawsze
         *   dokładnie dwie wartości).
         */
        System.out.println("\n=== TagsCommand: arity = \"1..*\" (lista wartosci pod JEDNA flaga) ===");
        String[] tagsArgs = new String[]{"--tag", "java", "spring", "docker"};
        new CommandLine(new TagsCommand()).execute(tagsArgs);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ręczne parsowanie String[] args jest kruche: brak walidacji
         *   typów, brak --help, łatwo o literówkę w kluczu, kod trzeba
         *   pisać od nowa dla każdego programu.
         * - @Command na klasie komendy (mixinStandardHelpOptions = true
         *   dodaje darmowe -h/--help i -V/--version).
         * - @Option(names = {"-x", "--nazwa"}) - opcja NAZWANA (flaga),
         *   @Parameters(index = "N") - argument POZYCYJNY (bez flagi).
         * - Runnable (run(), bez zwracanej wartości) albo
         *   Callable<Integer> (call(), int staje się kodem wyjścia) -
         *   uruchamiane przez new CommandLine(komenda).execute(args).
         * - Automatyczna konwersja typów: String/int/boolean/File/enum -
         *   Picocli sam dobiera konwersję na podstawie typu pola.
         * - defaultValue - wartość, gdy opcja nie podana; required = true -
         *   Picocli SAM odrzuci wywołanie bez tej opcji (kod wyjścia != 0).
         * - arity = "1..*" - jedna flaga zbiera WIELE wartości do listy.
         * - W Lekcji 30: komendy z PODKOMENDAMI (styl git), @ParentCommand,
         *   własna logika walidacji (ParameterException), ArgGroup
         *   (opcje wykluczające się/współwystępujące), własny
         *   ITypeConverter i szczegółowa obsługa kodów wyjścia.
         */
        System.out.println("\n=== KONIEC LEKCJI 29 ===");
    }

    /**
     * Naiwna, RĘCZNA implementacja parsowania argumentów - bez żadnej
     * biblioteki. Celowo pokazuje typowe problemy: brak walidacji typów
     * (Integer.parseInt rzuci wyjątek dla złych danych), konieczność
     * ręcznej obsługi każdego aliasu opcji osobno, brak --help, brak
     * czytelnych komunikatów błędów - to WSZYSTKO Picocli robi za Ciebie.
     */
    private static void demonstrateManualParsingPain() {
        System.out.println("\n=== RECZNE PARSOWANIE args (BEZ biblioteki) ===");
        String[] manualArgs = new String[]{"--name", "Kasia", "--count", "3"};

        String name = null;
        int count = 1; // "domyslna" wartosc - trzeba pamietac ustawic RECZNIE
        for (int i = 0; i < manualArgs.length; i++) {
            if ((manualArgs[i].equals("--name") || manualArgs[i].equals("-n")) && i + 1 < manualArgs.length) {
                name = manualArgs[++i];
            } else if (manualArgs[i].equals("--count") && i + 1 < manualArgs.length) {
                count = Integer.parseInt(manualArgs[++i]); // rzuci NumberFormatException dla np. "--count abc"
            }
            // a co z opcja "--help"? Trzeba dopisac RECZNIE.
            // a co jesli "--name" w ogole nie zostanie podane (required)? Tez trzeba sprawdzic RECZNIE.
            // a co z bledna flaga, np. literowka "--nam"? Zostanie po cichu ZIGNOROWANA.
        }
        System.out.println("Recznie sparsowano: name=" + name + ", count=" + count);
        System.out.println("Problemy: brak walidacji typow, brak automatycznego --help, brak obslugi");
        System.out.println("aliasow bez powielania kodu, ciche ignorowanie literowek w kluczach,");
        System.out.println("i ten kod trzeba pisac OD NOWA dla kazdej nowej opcji w kazdym programie CLI.");
    }

    // ==================== KOMENDY PICOCLI (klasy zagnieżdżone) ====================

    /** Poziom logowania - przykład typu enum konwertowanego automatycznie przez Picocli. */
    enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }

    /** Pierwsza komenda: @Option z aliasami, defaultValue i mixinStandardHelpOptions. */
    @Command(name = "greet", mixinStandardHelpOptions = true, version = "greet 1.0",
            description = "Wypisuje powitanie zadana ilosc razy.")
    static class GreetCommand implements Runnable {

        @Option(names = {"-n", "--name"}, defaultValue = "Swiecie",
                description = "Imie do powitania (domyslnie: ${DEFAULT-VALUE}).")
        String name;

        @Option(names = {"-c", "--count"}, defaultValue = "1",
                description = "Ile razy powtorzyc powitanie (domyslnie: ${DEFAULT-VALUE}).")
        int count;

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                System.out.println("Witaj, " + name + "!");
            }
        }
    }

    /** @Parameters pozycyjne + Callable<Integer> - kod wyjscia zalezny od logiki komendy. */
    @Command(name = "copy", mixinStandardHelpOptions = true,
            description = "Kopiuje plik zrodlowy do docelowego (symulacja, bez realnego zapisu na dysk).")
    static class CopyCommand implements Callable<Integer> {

        @Parameters(index = "0", description = "Plik zrodlowy.")
        File source;

        @Parameters(index = "1", description = "Plik docelowy.")
        File target;

        @Option(names = {"-v", "--verbose"}, description = "Wypisz szczegoly operacji.")
        boolean verbose;

        @Override
        public Integer call() {
            if (verbose) {
                System.out.println("[VERBOSE] Zrodlo: " + source.getPath() + ", cel: " + target.getPath());
            }
            if (!source.exists()) {
                System.out.println("BLAD: plik zrodlowy nie istnieje: " + source.getPath());
                return 1;
            }
            System.out.println("Skopiowano (symulacja): " + source.getPath() + " -> " + target.getPath());
            return 0;
        }
    }

    /** required = true oraz konwersja typow String -> enum i String -> File. */
    @Command(name = "level", mixinStandardHelpOptions = true,
            description = "Ustawia poziom logowania i wskazuje plik wejsciowy.")
    static class SetLogLevelCommand implements Runnable {

        @Option(names = "--level", defaultValue = "INFO",
                description = "Poziom logowania (domyslnie: ${DEFAULT-VALUE}).")
        LogLevel level;

        @Option(names = "--input", required = true, description = "Wymagany plik wejsciowy.")
        File input;

        @Override
        public void run() {
            System.out.println("Ustawiono poziom logowania na: " + level + " (typ enum LogLevel)");
            System.out.println("Plik wejsciowy: " + input.getPath());
        }
    }

    /** arity = "1..*" - jedna flaga zbiera dowolnie wiele wartosci do listy. */
    @Command(name = "tags", mixinStandardHelpOptions = true,
            description = "Przyjmuje liste tagow pod jedna flaga (arity 1..*).")
    static class TagsCommand implements Runnable {

        @Option(names = {"-t", "--tag"}, arity = "1..*", description = "Jeden lub wiecej tagow.")
        List<String> tags;

        @Override
        public void run() {
            System.out.println("Otrzymano " + tags.size() + " tag(ow): " + tags);
        }
    }
}
