package com.example.javaquest._13_libraries.Lesson30_PicocliSubcommandsAndValidation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.MissingParameterException;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

public class _Lesson30_PicocliSubcommandsAndValidation {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 30: PICOCLI - PODKOMENDY I WALIDACJA ===");

        /*
         * ============================================================
         * 📦 SUBCOMMANDS: CLI W STYLU "git" (git add / git commit / ...)
         * ============================================================
         * - Narzędzia takie jak git, docker czy npm NIE mają jednej płaskiej
         *   listy opcji - mają PODKOMENDY: "git add", "git commit",
         *   "docker run", "npm install". Każda podkomenda ma WŁASNY zestaw
         *   opcji i argumentów.
         * - Picocli wspiera to przez atrybut subcommands na @Command
         *   komendy nadrzędnej: @Command(subcommands = {Sub1.class, Sub2.class}).
         *   Poniżej TaskCliCommand ("mycli") ma trzy podkomendy: add, remove,
         *   list (zdefiniowane jako klasy zagnieżdżone na końcu pliku).
         * - Wywołanie z terminala wyglądałoby jak "mycli add "Kup mleko"
         *   --priority 1" - nazwa podkomendy idzie ZARAZ po nazwie/opcjach
         *   komendy głównej, a jej WŁASNE opcje/argumenty idą po niej.
         * - execute(...) na CommandLine komendy GŁÓWNEJ rozpoznaje pierwszy
         *   argument jako nazwę podkomendy i deleguje do niej resztę
         *   parsowania - Ty budujesz TYLKO JEDEN obiekt CommandLine (dla
         *   komendy głównej), Picocli sam znajduje właściwą podkomendę.
         */
        TaskCliCommand cli = new TaskCliCommand();
        CommandLine commandLine = new CommandLine(cli);

        System.out.println("\n=== PODKOMENDA 'add': dodawanie zadan ===");
        String[] addArgs1 = new String[]{"add", "Kup mleko", "--priority", "1"};
        commandLine.execute(addArgs1);

        String[] addArgs2 = new String[]{"add", "Posprzataj biuro"}; // priority = defaultValue "3"
        commandLine.execute(addArgs2);

        System.out.println("\n=== PODKOMENDA 'list': wypisanie wszystkich zadan ===");
        String[] listArgs = new String[]{"list"};
        commandLine.execute(listArgs);

        /*
         * ============================================================
         * 🔹 @ParentCommand: DOSTĘP DO STANU/OPCJI KOMENDY NADRZĘDNEJ
         * ============================================================
         * - Podkomenda (AddTaskCommand, RemoveTaskCommand, ListTasksCommand)
         *   często potrzebuje danych z komendy NADRZĘDNEJ - np. współdzielonej
         *   listy zadań (tasks) albo globalnej flagi --verbose ustawionej na
         *   głównym poleceniu, NIE na podkomendzie.
         * - Pole oznaczone @ParentCommand w klasie podkomendy Picocli
         *   automatycznie wypełnia REFERENCJĄ do instancji komendy
         *   nadrzędnej (TU: TaskCliCommand parent w AddTaskCommand) - bez
         *   tego pole zostałoby null.
         * - Zwróć uwagę: użyliśmy TEGO SAMEGO obiektu "cli" (i "commandLine")
         *   we wszystkich wywołaniach execute() powyżej - dlatego pole
         *   tasks w TaskCliCommand PRZETRWAŁO między "add" i "list" (ta sama
         *   lista w pamięci, nie nowa przy każdym execute()).
         * - Poniżej: opcja globalna "--verbose" na KOMENDZIE GŁÓWNEJ, odczytana
         *   z wnętrza podkomendy "add" przez parent.verbose.
         */
        System.out.println("\n=== @ParentCommand: globalna flaga -v z komendy glownej, widoczna w podkomendzie ===");
        CommandLine verboseCli = new CommandLine(new TaskCliCommand());
        String[] verboseAddArgs = new String[]{"-v", "add", "Zadanie z logiem", "--priority", "2"};
        verboseCli.execute(verboseAddArgs);

        /*
         * ============================================================
         * 🔍 WALIDACJA 1: required = true -> MissingParameterException
         * ============================================================
         * - @Parameters(index = "0") name w AddTaskCommand NIE ma jawnego
         *   "arity", więc domyślnie jest WYMAGANY (arity domyślne dla
         *   pozycyjnych to "1" - dokładnie jedna wartość, obowiązkowa).
         * - Gdy brakuje wymaganego argumentu/opcji, Picocli rzuca
         *   MissingParameterException (podklasa ParameterException).
         *   Domyślnie execute(...) SAM łapie ten wyjątek: wypisuje błąd na
         *   System.err razem z podpowiedzią "Usage:" i zwraca niezerowy kod
         *   wyjścia (bez rzucania wyjątku "na zewnątrz" execute()).
         * - Poniżej pokazujemy OBA podejścia: najpierw domyślną obsługę
         *   (execute), a potem RĘCZNE przechwycenie wyjątku przez
         *   parseArgs(...), żeby zobaczyć DOKŁADNĄ klasę wyjątku.
         */
        System.out.println("\n=== required (pozycyjny arg): brak nazwy zadania w 'add' ===");
        String[] missingNameArgs = new String[]{"add"};
        int missingNameExitCode = commandLine.execute(missingNameArgs);
        System.out.println("Kod wyjscia (execute() samo obsluzylo blad): " + missingNameExitCode);

        System.out.println("\n=== To samo, ale RECZNIE przechwycone przez parseArgs(...) ===");
        try {
            new CommandLine(new TaskCliCommand()).parseArgs(missingNameArgs);
        } catch (MissingParameterException ex) {
            System.out.println("Zlapano wyjatek: " + ex.getClass().getSimpleName());
            System.out.println("Komunikat: " + ex.getMessage());
        }

        /*
         * ============================================================
         * 🔍 WALIDACJA 2: WŁASNA LOGIKA -> rzucenie ParameterException
         * ============================================================
         * - Picocli waliduje TYPY (String -> int, enum...) i OBECNOŚĆ
         *   wymaganych opcji SAM, ale nie zna Twoich reguł biznesowych
         *   (np. "priorytet musi być 1-5"). Taką walidację piszesz w
         *   run()/call() i w razie błędu rzucasz WŁASNY
         *   CommandLine.ParameterException - Picocli obsłuży go DOKŁADNIE
         *   tak samo jak wbudowane błędy walidacji (czytelny komunikat +
         *   Usage + niezerowy kod wyjścia).
         * - Żeby zbudować ParameterException, potrzebujesz referencji do
         *   CommandLine BIEŻĄCEGO polecenia - dostarcza ją pole oznaczone
         *   @Spec (typu CommandLine.Model.CommandSpec), wstrzykiwane
         *   automatycznie przez Picocli, tak samo jak @ParentCommand.
         */
        System.out.println("\n=== Wlasna walidacja: priorytet spoza zakresu 1-5 ===");
        String[] badPriorityArgs = new String[]{"add", "Zadanie z zlym priorytetem", "--priority", "9"};
        int badPriorityExitCode = commandLine.execute(badPriorityArgs);
        System.out.println("Kod wyjscia (wlasny ParameterException): " + badPriorityExitCode);

        System.out.println("\n=== Wlasna walidacja: plik do synchronizacji nie istnieje ===");
        String[] badFileArgs = new String[]{"sync", "--file", "brakujacy-backup.dat"};
        int badFileExitCode = commandLine.execute(badFileArgs);
        System.out.println("Kod wyjscia (plik nie istnieje): " + badFileExitCode);

        /*
         * ============================================================
         * 🔍 ArgGroup: OPCJE WYKLUCZAJĄCE SIĘ (exclusive = true)
         * ============================================================
         * - RemoveTaskCommand usuwa zadanie PO ID albo PO NAZWIE - te dwie
         *   opcje NIE powinny być podane jednocześnie, ale DOKŁADNIE JEDNA
         *   z nich musi się pojawić.
         * - @ArgGroup(exclusive = true, multiplicity = "1") na polu typu
         *   klasy pomocniczej (Selector, zawierającej --id i --name jako
         *   zwykłe @Option) wymusza OBIE reguły naraz: exclusive = true
         *   ("co najwyżej jedna z opcji w grupie"), multiplicity = "1"
         *   ("grupa musi wystąpić dokładnie raz" - czyli co najmniej jedna
         *   opcja z grupy JEST wymagana).
         * - Naruszenie exclusive (podanie OBU) rzuca
         *   CommandLine.MutuallyExclusiveArgsException (podklasa
         *   ParameterException) - obsługiwane tak samo jak inne błędy
         *   walidacji.
         */
        System.out.println("\n=== ArgGroup exclusive: usuwanie PO ID (poprawnie) ===");
        String[] removeByIdArgs = new String[]{"remove", "--id", "1"};
        commandLine.execute(removeByIdArgs);

        System.out.println("\n=== ArgGroup exclusive: PODANO OBIE opcje na raz (blad wykluczania) ===");
        String[] removeBothArgs = new String[]{"remove", "--id", "2", "--name", "Posprzataj biuro"};
        int removeBothExitCode = commandLine.execute(removeBothArgs);
        System.out.println("Kod wyjscia (MutuallyExclusiveArgsException): " + removeBothExitCode);

        System.out.println("\n=== ArgGroup exclusive: NIE PODANO zadnej opcji (multiplicity = \"1\") ===");
        String[] removeNoneArgs = new String[]{"remove"};
        int removeNoneExitCode = commandLine.execute(removeNoneArgs);
        System.out.println("Kod wyjscia (brak wymaganej grupy): " + removeNoneExitCode);

        /*
         * ============================================================
         * 🔍 ArgGroup: OPCJE WSPÓŁWYSTĘPUJĄCE (exclusive = false)
         * ============================================================
         * - Odwrotny scenariusz: "sync" przyjmuje OPCJONALNE dane logowania
         *   (--user/--password), ale JEŚLI podasz jedną z nich, MUSISZ podać
         *   OBIE (logowanie "częściowe" nie ma sensu).
         * - @ArgGroup(exclusive = false) (bez multiplicity - domyślnie
         *   grupa jako CAŁOŚĆ jest opcjonalna) w połączeniu z
         *   required = true na KAŻDEJ opcji WEWNĄTRZ grupy (Credentials)
         *   daje dokładnie to zachowanie: "jeśli grupa użyta, wszystkie jej
         *   required-opcje muszą wystąpić".
         */
        System.out.println("\n=== ArgGroup co-occurring: pelne dane logowania (poprawnie) ===");
        String[] syncFullAuthArgs = new String[]{"sync", "--user", "admin", "--password", "tajne123"};
        commandLine.execute(syncFullAuthArgs);

        System.out.println("\n=== ArgGroup co-occurring: TYLKO --user, brak --password (blad) ===");
        String[] syncPartialAuthArgs = new String[]{"sync", "--user", "admin"};
        int syncPartialExitCode = commandLine.execute(syncPartialAuthArgs);
        System.out.println("Kod wyjscia (niekompletna grupa co-occurring): " + syncPartialExitCode);

        System.out.println("\n=== ArgGroup co-occurring: BEZ zadnych danych logowania (dozwolone, cala grupa opcjonalna) ===");
        String[] syncNoAuthArgs = new String[]{"sync"};
        commandLine.execute(syncNoAuthArgs);

        /*
         * ============================================================
         * 🔍 WŁASNY ITypeConverter: PARSOWANIE NIESTANDARDOWEGO TYPU
         * ============================================================
         * - Picocli zna wbudowane konwersje (String/int/boolean/File/enum...
         *   z Lekcji 29), ale dla WŁASNYCH typów (tu: Coordinates, para
         *   x:y) trzeba dostarczyć konwerter samodzielnie.
         * - Implementujesz interfejs ITypeConverter<T> z JEDNĄ metodą
         *   convert(String value): T i podpinasz go do opcji przez
         *   @Option(converter = TwojKonwerter.class).
         * - Jeśli convert(...) rzuci wyjątek (dowolny, np.
         *   IllegalArgumentException dla złego formatu), Picocli
         *   automatycznie OPAKUJE go w czytelny błąd walidacji - DOKŁADNIE
         *   tak samo, jakby to była wbudowana konwersja typu.
         */
        System.out.println("\n=== Wlasny ITypeConverter: --location w formacie X:Y ===");
        String[] locationArgs = new String[]{"add", "Zadanie z lokalizacja", "--location", "10:20"};
        commandLine.execute(locationArgs);

        System.out.println("\n=== Wlasny ITypeConverter: BLEDNY format lokalizacji ===");
        String[] badLocationArgs = new String[]{"add", "Zadanie z bledna lokalizacja", "--location", "abc"};
        int badLocationExitCode = commandLine.execute(badLocationArgs);
        System.out.println("Kod wyjscia (blad konwersji wlasnego typu): " + badLocationExitCode);

        /*
         * ============================================================
         * 📌 KODY WYJŚCIA (exit codes): execute() zwraca int
         * ============================================================
         * - execute(...) ZAWSZE zwraca int - konwencja (i wartości ze stałych
         *   CommandLine.ExitCode) to:
         *     OK = 0        - sukces,
         *     SOFTWARE = 1  - błąd logiki wewnątrz run()/call() (np. rzucony
         *                     wyjątek inny niż ParameterException),
         *     USAGE = 2     - błąd WALIDACJI wejścia (brak wymaganej opcji,
         *                     zły typ, złamana reguła ArgGroup...).
         * - Domyślnie execute(...) SAM obsługuje ParameterException (patrz
         *   wszystkie przykłady powyżej) - ale czasem chcesz PRZEJĄĆ tę
         *   obsługę ręcznie (np. żeby zalogować błąd do pliku, wysłać
         *   metrykę, albo sformatować komunikat inaczej niż domyślnie).
         *   Wzorzec: parseArgs(...) w try, złap ParameterException, wypisz
         *   WŁASNY komunikat i pomoc przez CommandLine.usage(...), a na
         *   końcu zwróć/ustaw kod wyjścia sam.
         */
        System.out.println("\n=== CommandLine.ExitCode: znaczenie stalych ===");
        System.out.println("OK = " + CommandLine.ExitCode.OK
                + ", SOFTWARE = " + CommandLine.ExitCode.SOFTWARE
                + ", USAGE = " + CommandLine.ExitCode.USAGE);

        System.out.println("\n=== Reczna obsluga: parseArgs(...) + catch ParameterException + CommandLine.usage(...) ===");
        String[] manualCatchArgs = new String[]{"add"}; // brak wymaganej nazwy zadania
        CommandLine manualCommandLine = new CommandLine(new TaskCliCommand());
        try {
            manualCommandLine.parseArgs(manualCatchArgs);
            System.out.println("Parsowanie powiodlo sie - logika biznesowa mogla by sie wykonac tutaj.");
        } catch (ParameterException ex) {
            System.err.println("Blad parsowania argumentow: " + ex.getMessage());
            CommandLine.usage(ex.getCommandLine(), System.out);
            int manualExitCode = ex.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
            System.out.println("Recznie odczytany kod wyjscia (exitCodeOnInvalidInput): " + manualExitCode);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Podkomendy: @Command(subcommands = {A.class, B.class}) - CLI w
         *   stylu git ("mycli add ...", "mycli remove ...").
         * - @ParentCommand w podkomendzie daje dostęp do stanu/opcji komendy
         *   nadrzędnej (współdzielone dane, globalne flagi jak -v).
         * - required = true (opcja) / domyślne arity "1" (pozycyjny arg) ->
         *   MissingParameterException, obsłużony automatycznie przez
         *   execute() ALBO ręcznie przez parseArgs() + catch.
         * - Własna logika walidacji (np. zakres liczbowy, istnienie pliku) -
         *   rzucasz CommandLine.ParameterException, korzystając z pola
         *   @Spec (CommandSpec) - Picocli obsłuży go tak samo jak wbudowane
         *   błędy.
         * - ArgGroup: exclusive = true + multiplicity = "1" -> DOKŁADNIE
         *   jedna z opcji (wykluczające się); exclusive = false +
         *   required = true na opcjach wewnątrz -> WSZYSTKIE razem albo
         *   żadna (współwystępujące).
         * - Własny ITypeConverter<T> - parsowanie dowolnego, niestandardowego
         *   typu ze Stringa, podpięte przez @Option(converter = ...).
         * - execute() zwraca int (CommandLine.ExitCode.OK/SOFTWARE/USAGE) -
         *   domyślnie obsługuje błędy walidacji samo, ale parseArgs(...) +
         *   catch (ParameterException ex) + CommandLine.usage(ex.getCommandLine(), ...)
         *   daje PEŁNĄ kontrolę nad obsługą błędów, gdy jest potrzebna.
         */
        System.out.println("\n=== KONIEC LEKCJI 30 ===");
    }

    // ==================== DOMENA ====================

    /** Wspolrzedne "x:y" - przykladowy WLASNY typ parsowany przez ITypeConverter. */
    record Coordinates(int x, int y) {
        @Override
        public String toString() {
            return x + ":" + y;
        }
    }

    /** Pojedyncze zadanie na liscie zarzadzanej przez TaskCliCommand. */
    record Task(int id, String name, int priority, Coordinates location) {
        @Override
        public String toString() {
            return "Task{id=" + id + ", name='" + name + "', priority=" + priority
                    + (location != null ? ", location=" + location : "") + "}";
        }
    }

    /** Wlasny konwerter Stringa "x:y" na Coordinates - podpiety przez @Option(converter = ...). */
    static class CoordinatesConverter implements ITypeConverter<Coordinates> {
        @Override
        public Coordinates convert(String value) {
            String[] parts = value.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Oczekiwano formatu X:Y, otrzymano: '" + value + "'");
            }
            return new Coordinates(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }
    }

    // ==================== KOMENDA GLOWNA + PODKOMENDY ====================

    /** Komenda glowna ("mycli") - deklaruje podkomendy i wspoldzielony stan (lista zadan). */
    @Command(name = "mycli", mixinStandardHelpOptions = true, version = "mycli 1.0",
            description = "Przykladowe CLI do zarzadzania zadaniami (styl git: mycli <podkomenda> ...).",
            subcommands = {AddTaskCommand.class, RemoveTaskCommand.class, ListTasksCommand.class, SyncCommand.class})
    static class TaskCliCommand implements Runnable {

        @Option(names = {"-v", "--verbose"}, description = "Wypisuj dodatkowe informacje diagnostyczne.")
        boolean verbose;

        final List<Task> tasks = new ArrayList<>();

        @Override
        public void run() {
            System.out.println("Uzyj jednej z podkomend: add, remove, list, sync (--help pokaze szczegoly).");
        }
    }

    /** Podkomenda "add" - dodaje zadanie, korzysta z @ParentCommand, wlasnej walidacji i ITypeConverter. */
    @Command(name = "add", description = "Dodaje nowe zadanie do listy.")
    static class AddTaskCommand implements Runnable {

        @ParentCommand
        TaskCliCommand parent;

        @Parameters(index = "0", description = "Nazwa zadania.")
        String name;

        @Option(names = {"-p", "--priority"}, defaultValue = "3",
                description = "Priorytet 1 (najwyzszy) - 5 (najnizszy). Domyslnie: ${DEFAULT-VALUE}.")
        int priority;

        @Option(names = "--location", description = "Wspolrzedne w formacie X:Y (np. 10:20).",
                converter = CoordinatesConverter.class)
        Coordinates location;

        @Spec
        CommandLine.Model.CommandSpec spec;

        @Override
        public void run() {
            if (priority < 1 || priority > 5) {
                throw new ParameterException(spec.commandLine(),
                        "Priorytet musi byc w zakresie 1-5, otrzymano: " + priority);
            }
            int id = parent.tasks.size() + 1;
            Task task = new Task(id, name, priority, location);
            parent.tasks.add(task);
            if (parent.verbose) {
                System.out.println("[VERBOSE] Lista ma teraz " + parent.tasks.size() + " zadan.");
            }
            System.out.println("Dodano zadanie: " + task);
        }
    }

    /** Podkomenda "remove" - ArgGroup exclusive (usun po ID ALBO po nazwie, dokladnie jedno). */
    @Command(name = "remove", description = "Usuwa zadanie po ID lub po nazwie (dokladnie jedno z dwoch).")
    static class RemoveTaskCommand implements Runnable {

        @ParentCommand
        TaskCliCommand parent;

        static class Selector {
            @Option(names = "--id", description = "ID zadania do usuniecia.")
            Integer id;

            @Option(names = "--name", description = "Nazwa zadania do usuniecia.")
            String name;
        }

        @ArgGroup(exclusive = true, multiplicity = "1")
        Selector selector;

        @Override
        public void run() {
            boolean removed;
            if (selector.id != null) {
                removed = parent.tasks.removeIf(t -> t.id() == selector.id);
                System.out.println(removed
                        ? "Usunieto zadanie o ID=" + selector.id
                        : "Nie znaleziono zadania o ID=" + selector.id);
            } else {
                removed = parent.tasks.removeIf(t -> t.name().equals(selector.name));
                System.out.println(removed
                        ? "Usunieto zadanie o nazwie=" + selector.name
                        : "Nie znaleziono zadania o nazwie=" + selector.name);
            }
        }
    }

    /** Podkomenda "list" - wypisuje wspoldzielona liste zadan z komendy nadrzednej. */
    @Command(name = "list", description = "Wypisuje wszystkie zadania.")
    static class ListTasksCommand implements Runnable {

        @ParentCommand
        TaskCliCommand parent;

        @Override
        public void run() {
            if (parent.tasks.isEmpty()) {
                System.out.println("Brak zadan.");
                return;
            }
            parent.tasks.forEach(System.out::println);
        }
    }

    /** Podkomenda "sync" - ArgGroup co-occurring (--user + --password razem) i walidacja istnienia pliku. */
    @Command(name = "sync", description = "Synchronizuje zadania z plikiem/serwerem zdalnym (symulacja).")
    static class SyncCommand implements Callable<Integer> {

        static class Credentials {
            @Option(names = "--user", required = true, description = "Nazwa uzytkownika.")
            String user;

            @Option(names = "--password", required = true, description = "Haslo.")
            String password;
        }

        @ArgGroup(exclusive = false)
        Credentials credentials;

        @Option(names = "--file", description = "Plik z kopia zapasowa do zaimportowania.")
        File file;

        @Spec
        CommandLine.Model.CommandSpec spec;

        @Override
        public Integer call() {
            if (file != null && !file.exists()) {
                throw new ParameterException(spec.commandLine(),
                        "Podany plik nie istnieje: " + file.getPath());
            }
            if (credentials != null) {
                System.out.println("Synchronizacja jako uzytkownik: " + credentials.user);
            } else {
                System.out.println("Synchronizacja anonimowa (bez logowania).");
            }
            if (file != null) {
                System.out.println("Zaimportowano dane z pliku: " + file.getName());
            }
            return CommandLine.ExitCode.OK;
        }
    }
}
