package com.example.javaquest._13_libraries.Lesson30_PicocliSubcommandsAndValidation;

public class _Exercises_Lesson30_PicocliSubcommandsAndValidation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_TwoSubcommandsGitStyleCli {
        /*
         * 🧪 Zadanie 1:
         * Utwórz komendę główną NotesCli (@Command(name = "notes",
         * subcommands = {AddNoteCommand.class, ListNotesCommand.class})) oraz
         * dwie podkomendy: AddNoteCommand (@Parameters(index = "0") String text)
         * i ListNotesCommand (wypisuje statyczną/współdzieloną listę notatek).
         * Uruchom new CommandLine(new NotesCli()).execute(new String[]{"add", "Pamietaj o mleku"}).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListSubcommandShowsAddedItems {
        /*
         * 🧪 Zadanie 2:
         * Używając TEGO SAMEGO obiektu CommandLine z Zadania 1, wykonaj DWA
         * wywołania execute(...) pod rząd - najpierw {"add", "Notatka 1"}, potem
         * {"list"} - zweryfikuj, że lista współdzieli stan między wywołaniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ParentCommandAccessSharedList {
        /*
         * 🧪 Zadanie 3:
         * Dodaj do NotesCli z Zadania 1 pole @ParentCommand w AddNoteCommand
         * (typu NotesCli) i użyj go, żeby dopisywać notatki do listy
         * przechowywanej W KOMENDZIE GŁÓWNEJ (nie statycznej). Zweryfikuj, że
         * działa tak samo jak w Zadaniu 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GlobalVerboseFlagOnParent {
        /*
         * 🧪 Zadanie 4:
         * Dodaj do NotesCli opcję @Option(names = "--verbose") boolean verbose
         * NA KOMENDZIE GŁÓWNEJ. W AddNoteCommand (przez @ParentCommand) wypisz
         * dodatkowy komunikat "[VERBOSE] ..." TYLKO gdy parent.verbose == true.
         * Uruchom z {"--verbose", "add", "Test"} oraz bez --verbose, porównaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RequiredPositionalMissingDefaultHandling {
        /*
         * 🧪 Zadanie 5:
         * Uruchom AddNoteCommand z Zadania 1 z {"add"} (BEZ tekstu notatki) -
         * wypisz kod wyjścia zwrócony przez execute(...) i zweryfikuj, że jest
         * niezerowy (MissingParameterException obsłużony automatycznie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ManualParseArgsCatchMissingParameterException {
        /*
         * 🧪 Zadanie 6:
         * Powtórz Zadanie 5, ale zamiast execute(...) użyj RĘCZNIE
         * new CommandLine(new NotesCli()).parseArgs(new String[]{"add"}) w bloku
         * try/catch (MissingParameterException ex) - wypisz ex.getMessage()
         * oraz ex.getClass().getSimpleName().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CustomRangeValidationThrowsParameterException {
        /*
         * 🧪 Zadanie 7:
         * Utwórz komendę RateCommand z @Option(names = "--stars") int stars.
         * Wewnątrz run() (z polem @Spec CommandLine.Model.CommandSpec spec),
         * jeśli stars < 1 lub stars > 5, rzuć
         * new CommandLine.ParameterException(spec.commandLine(), "..."). Uruchom
         * z {"--stars", "7"} i wypisz kod wyjścia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FileExistsCustomValidation {
        /*
         * 🧪 Zadanie 8:
         * Utwórz komendę LoadCommand z @Option(names = "--file") File file.
         * W run() (z @Spec), jeśli !file.exists(), rzuć ParameterException z
         * czytelnym komunikatem. Uruchom z plikiem, który NA PEWNO nie istnieje
         * (np. "brak-takiego-pliku-xyz.dat") i wypisz kod wyjścia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ArgGroupExclusiveTwoOptions {
        /*
         * 🧪 Zadanie 9:
         * Utwórz komendę FindCommand z klasą pomocniczą Selector (@Option
         * --by-id Integer, @Option --by-email String) i polem
         * @ArgGroup(exclusive = true, multiplicity = "1") Selector selector.
         * Uruchom z {"--by-id", "5"} (poprawnie) oraz z OBIEMA opcjami naraz
         * (błąd) - porównaj kody wyjścia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExitCodeConstantsPrinted {
        /*
         * 🧪 Zadanie 10:
         * Wypisz wartości CommandLine.ExitCode.OK, CommandLine.ExitCode.SOFTWARE
         * i CommandLine.ExitCode.USAGE. W komentarzu opisz, w JAKIEJ sytuacji
         * execute() zwraca każdą z tych wartości.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeSubcommandsSharedInventory {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj InventoryCli (@Command(subcommands = {AddItemCommand.class,
         * RemoveItemCommand.class, ListItemsCommand.class})) zarządzające
         * współdzieloną listą Map<String, Integer> (nazwa -> ilość) w komendzie
         * głównej. AddItemCommand dodaje/zwiększa ilość, RemoveItemCommand
         * zmniejsza/usuwa, ListItemsCommand wypisuje cały stan magazynu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ArgGroupCoOccurringTwoOptions {
        /*
         * 🧪 Zadanie 12:
         * Dodaj do InventoryCli podkomendę ExportCommand z klasą pomocniczą
         * RemoteTarget (@Option --host String required, @Option --port int
         * required) i polem @ArgGroup(exclusive = false) RemoteTarget target
         * (opcjonalna CAŁA grupa). Uruchom z TYLKO --host (błąd), z OBOMA
         * (sukces) i BEZ żadnej (sukces - eksport lokalny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CustomTypeConverterForDateRange {
        /*
         * 🧪 Zadanie 13:
         * Utwórz record DateRange(LocalDate from, LocalDate to) i klasę
         * DateRangeConverter implements ITypeConverter<DateRange>, parsującą
         * String w formacie "2024-01-01..2024-01-31" (rozdzielone "..").
         * Podepnij ją przez @Option(names = "--period", converter =
         * DateRangeConverter.class) w komendzie ReportCommand. Przetestuj
         * poprawny i niepoprawny format.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CustomConverterThrowsOnInvalidFormat {
        /*
         * 🧪 Zadanie 14:
         * Uruchom ReportCommand z Zadania 13 z {"--period", "styczen"} (zły
         * format, brak "..") - zweryfikuj, że Picocli sam opakował wyjątek z
         * convert(...) w czytelny błąd walidacji i execute() zwróciło niezerowy
         * kod wyjścia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NestedSubcommandsThreeLevels {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj CLI z ZAGNIEŻDŻONYMI podkomendami: "admin" (komenda pośrednia,
         * implements Runnable, BEZ własnej logiki poza wypisaniem podpowiedzi)
         * z WŁASNĄ podkomendą "reset-password" (@Command(subcommands =
         * {ResetPasswordCommand.class}) na klasie AdminCommand, która sama jest
         * podkomendą komendy głównej). Uruchom {"admin", "reset-password",
         * "--user", "jkowalski"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MissingParameterExceptionMessageContainsOptionName {
        /*
         * 🧪 Zadanie 16:
         * Utwórz komendę z WYMAGANĄ opcją @Option(names = "--api-key",
         * required = true). Sprowokuj MissingParameterException (RĘCZNIE przez
         * parseArgs + catch) i zweryfikuj w komentarzu, że ex.getMessage()
         * zawiera nazwę brakującej opcji ("--api-key").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ArgGroupExclusiveWithThreeOptions {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz ArgGroup z Zadania 9 (FindCommand) o TRZECIĄ opcję
         * wykluczającą się: --by-username String. Sprawdź WSZYSTKIE 3
         * pojedyncze warianty (po jednej opcji na raz - każdy poprawny) oraz
         * jeden wariant z DWIEMA opcjami naraz (błąd).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UsageMethodPrintsHelpManually {
        /*
         * 🧪 Zadanie 18:
         * Złap ParameterException z dowolnej komendy tej lekcji (parseArgs +
         * catch) i zamiast domyślnej obsługi execute(), wywołaj RĘCZNIE
         * CommandLine.usage(ex.getCommandLine(), System.out) - zweryfikuj, że
         * wypisuje się ta sama pomoc, co przy błędzie z execute().
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SoftwareExitCodeFromCallable {
        /*
         * 🧪 Zadanie 19:
         * Utwórz komendę RiskyCommand implements Callable<Integer>, której
         * call() celowo rzuca RuntimeException (NIE ParameterException) dla
         * pewnych danych wejściowych. Uruchom taką komendę i wypisz zwrócony
         * kod wyjścia - zweryfikuj w komentarzu, że to CommandLine.ExitCode.SOFTWARE (1),
         * bo Picocli domyślnie mapuje NIEZŁAPANE wyjątki z run()/call() na 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CombineParentCommandWithArgGroup {
        /*
         * 🧪 Zadanie 20:
         * W podkomendzie InventoryCli (Zadanie 11) połącz @ParentCommand
         * (dostęp do współdzielonego magazynu) Z @ArgGroup exclusive (usuwanie
         * przedmiotu PO NAZWIE albo PO INDEKSIE na liście) w JEDNEJ podkomendzie
         * RemoveItemByCommand.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullTaskCliWithFourSubcommandsFromScratch {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj OD ZERA (bez kopiowania z lekcji) własne CLI ProjectCli z
         * podkomendami: create-task, complete-task, list-tasks, delete-task -
         * każda z sensownymi opcjami/argumentami i współdzielonym stanem przez
         * @ParentCommand. Zademonstruj PEŁNY cykl życia zadania (utworzenie ->
         * oznaczenie jako ukończone -> usunięcie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ValidationCombiningRangeAndFileExistence {
        /*
         * 🧪 Zadanie 22:
         * Utwórz komendę ImportCommand łączącą DWIE własne walidacje w JEDNEJ
         * metodzie run(): @Option --file (musi istnieć) ORAZ @Option --batchSize
         * int (musi być w zakresie 1-1000). Zademonstruj 4 scenariusze: oba OK,
         * zly plik, zly batchSize, oba złe (sprawdź, KTÓRY błąd zgłasza się
         * pierwszy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ArgGroupOfArgGroups {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj komendę ConnectCommand z DWOMA niezależnymi ArgGroup na tej
         * samej klasie: jeden exclusive (--by-name ALBO --by-ip, dokładnie
         * jedno) i drugi co-occurring (--user + --password razem, opcjonalnie).
         * Przetestuj kombinacje: poprawną pełną, brak drugiej grupy (dozwolone),
         * błąd w pierwszej grupie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CustomConverterForCommaSeparatedIntList {
        /*
         * 🧪 Zadanie 24:
         * Napisz IntListConverter implements ITypeConverter<List<Integer>>
         * parsujący String "1,2,3,4" na List<Integer> (bez używania arity -
         * JEDNA wartość String dzielona po przecinku wewnątrz convert()).
         * Podepnij do @Option(names = "--ids", converter = IntListConverter.class)
         * i przetestuj z poprawnymi oraz niepoprawnymi danymi (np. "1,abc,3").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ManualExitCodeMappingBasedOnExceptionType {
        /*
         * 🧪 Zadanie 25:
         * Napisz metodę statyczną runWithCustomExitCodes(Object command, String[]
         * cliArgs), która używa parseArgs (nie execute) i w catch rozróżnia
         * MissingParameterException (zwróć 2), CommandLine.MutuallyExclusiveArgsException
         * (zwróć 3) i ogólny ParameterException (zwróć 4) - każdy przypadek
         * dodatkowo wypisujący CommandLine.usage(...). Przetestuj wszystkie 3 gałęzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SubcommandListingAllRegisteredSubcommands {
        /*
         * 🧪 Zadanie 26:
         * Dla dowolnej komendy głównej z tej lekcji (np. TaskCliCommand) wypisz
         * WSZYSTKIE zarejestrowane podkomendy przez
         * commandLine.getSubcommands().keySet() - zweryfikuj, że zawiera
         * dokładnie te nazwy, które podałeś w atrybucie subcommands.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ProgrammaticallyAddingSubcommandAtRuntime {
        /*
         * 🧪 Zadanie 27:
         * Zamiast deklarować podkomendy przez atrybut subcommands w @Command,
         * zbuduj CommandLine dla komendy głównej BEZ tego atrybutu, a następnie
         * dodaj podkomendę PROGRAMISTYCZNIE przez
         * commandLine.addSubcommand("nazwa", new PodkomendaInstance()). Uruchom
         * i zweryfikuj, że działa tak samo jak deklaratywnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ArgGroupWithCustomValidationInsideRun {
        /*
         * 🧪 Zadanie 28:
         * Utwórz komendę BookingCommand z @ArgGroup(exclusive = false)
         * zawierającym --checkIn (LocalDate) i --checkOut (LocalDate), OBA
         * required = true wewnątrz grupy. DODATKOWO w run() sprawdź WŁASNĄ
         * regułą biznesową, że checkOut MUSI być PO checkIn - jeśli nie, rzuć
         * ParameterException. Przetestuj: brak dat, złą kolejność dat, poprawne dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullCliSimulationWithAllExitCodesCatalogued {
        /*
         * 🧪 Zadanie 29:
         * Wybierz DOWOLNĄ komendę z podkomendami z tej lekcji i zbuduj TABELĘ
         * (wypisaną przez System.out) zestawiającą: scenariusz wywołania (opis),
         * użyta tablica argumentów, zwrócony kod wyjścia - dla CO NAJMNIEJ 6
         * różnych scenariuszy (sukces, brak wymaganego pola, zły typ, złamana
         * reguła ArgGroup exclusive, złamana reguła ArgGroup co-occurring, własna
         * walidacja biznesowa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignAndImplementGitStyleCliForBugTracker {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj I zaimplementuj kompletne CLI "bugtracker" w stylu git dla
         * mini-systemu zgłoszeń: podkomendy report (utworzenie zgłoszenia:
         * tytuł, priorytet 1-5 z własną walidacją, opcjonalny --assignee
         * powiązany co-occurring z --team przez ArgGroup), close (zamknięcie po
         * ID ALBO po tytule przez ArgGroup exclusive), list (wypisanie
         * wszystkich) - wszystko na współdzielonym stanie przez @ParentCommand.
         * Zademonstruj PEŁNY przepływ z co najmniej 5 wywołaniami execute(...).
         */
        public static void main(String[] args) { }
    }
}
