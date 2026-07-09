package com.example.javaquest._14_advancedjava.Lesson28_ModulesAdvanced;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class _Lesson28_ModulesAdvanced {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 28: JPMS - opens, exports...to, uses/provides ===\n");

        /*
         * ============================================================
         * 📦 NAWIAZANIE DO LEKCJI 27
         * ============================================================
         * W Lekcji 27 poznales podstawy JPMS: "module", "requires",
         * "exports", modul nienazwany i moduly automatyczne. Widziales
         * tez REALNY blad kompilacji, gdy modul probowal uzyc pakietu,
         * ktorego inny modul nie eksportuje.
         *
         * Dzisiaj konczymy klaster "Odkrywanie uslug i modularnosc"
         * trzema bardziej zaawansowanymi dyrektywami:
         *  - opens - kontrola DEEP REFLECTION (nawiazanie do Lekcji 16 -
         *    refleksja i jej ryzyka),
         *  - exports ... to ... - eksport TYLKO dla wybranych modulow,
         *  - uses / provides - NATYWNA dla modulow wersja ServiceLoadera
         *    z Lekcji 26, bez pliku META-INF/services.
         *
         * Na koniec - uczciwa rozmowa o tym, dlaczego SAM ten kurs
         * (javaQuest) swiadomie NIE uzywa JPMS.
         */

        /*
         * ============================================================
         * 🔹 opens - SILNA ENKAPSULACJA DOTYCZY TEZ REFLEKSJI
         * ============================================================
         * W Lekcji 16 poznales refleksje i jej ryzyka - m.in. mozliwosc
         * "wlamania" sie do PRYWATNYCH pol/metod przez
         * Field.setAccessible(true). Na classpath (modul nienazwany) nic
         * nie stoi na przeszkodzie - setAccessible(true) zawsze dziala.
         *
         * W PRAWDZIWYM module sytuacja jest INNA. "exports" kontroluje
         * dostep w COMPILE TIME (widocznosc typow publicznych), ale
         * DEEP REFLECTION (odczyt/wywolanie PRYWATNYCH skladowych przez
         * refleksje, tak jak robia to np. Hibernate, Jackson czy Spring,
         * zebt wstrzykiwac wartosci do prywatnych pol encji/DTO) to
         * ZUPELNIE OSOBNE uprawnienie, kontrolowane przez dyrektywe
         * "opens":
         *
         *     module my.entities {
         *         exports com.example.entities;       // widocznosc typow
         *         opens com.example.entities;          // + deep reflection
         *     }
         *
         * Modul MOZE "exports" pakiet bez "opens" go (kod zewnetrzny
         * widzi i uzywa typow publicznych normalnie, ale refleksyjny
         * dostep do PRYWATNYCH skladowych jest zablokowany) - i odwrotnie,
         * moze "opens" pakiet, ktorego NIE eksportuje (typy niewidoczne
         * w compile time, ale dostepne dla frameworkow dzialajacych
         * WYLACZNIE przez refleksje, np. silnika DI skanujacego klasy).
         *
         * "open module" (na poziomie calego modulu) otwiera WSZYSTKIE
         * jego pakiety do refleksji naraz - czesty wybor dla modulow
         * "aplikacyjnych" hostujacych encje/DTO skanowane przez frameworki.
         */

        System.out.println("--- DEMO: opens kontroluje DEEP REFLECTION, nie tylko widocznosc typow ---\n");

        String javaHome = System.getProperty("java.home");
        String javacExe = javaHome + File.separator + "bin" + File.separator + "javac";
        String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

        Path opensWorkDir = Files.createTempDirectory("lesson28-opens");
        try {
            Path srcDir = opensWorkDir.resolve("src");
            Path targetModuleDir = srcDir.resolve("reflect.target");
            Path consumerModuleDir = srcDir.resolve("reflect.consumer");
            Files.createDirectories(targetModuleDir.resolve("target"));
            Files.createDirectories(consumerModuleDir.resolve("consumer"));

            // Modul "reflect.target" EKSPORTUJE pakiet "target" (widoczny
            // w compile time), ale (na razie) go NIE "opens" - deep
            // reflection do prywatnego pola bedzie zablokowana.
            Files.writeString(targetModuleDir.resolve("module-info.java"), """
                    module reflect.target {
                        exports target;
                    }
                    """);
            Files.writeString(targetModuleDir.resolve("target").resolve("Widget.java"), """
                    package target;

                    public class Widget {
                        private String secretLabel = "ukryta-etykieta";
                    }
                    """);

            Files.writeString(consumerModuleDir.resolve("module-info.java"), """
                    module reflect.consumer {
                        requires reflect.target;
                    }
                    """);
            Files.writeString(consumerModuleDir.resolve("consumer").resolve("Main.java"), """
                    package consumer;

                    import target.Widget;
                    import java.lang.reflect.Field;

                    public class Main {
                        public static void main(String[] args) throws Exception {
                            Widget widget = new Widget();
                            Field field = Widget.class.getDeclaredField("secretLabel");
                            try {
                                field.setAccessible(true);
                                System.out.println("Odczytano przez refleksje: " + field.get(widget));
                            } catch (Exception e) {
                                System.out.println("BLAD: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                            }
                        }
                    }
                    """);

            Path outWithoutOpens = opensWorkDir.resolve("out-without-opens");
            Files.createDirectories(outWithoutOpens);
            runProcess("javac (kompilacja BEZ opens)", javacExe,
                    "--module-source-path", srcDir.toString(),
                    "-d", outWithoutOpens.toString(),
                    "--module", "reflect.target,reflect.consumer");

            System.out.println("--- Uruchomienie BEZ 'opens target' (oczekiwany blad refleksji) ---");
            runProcess("java (bez opens)", javaExe,
                    "--module-path", outWithoutOpens.toString(),
                    "--module", "reflect.consumer/consumer.Main");

            // Teraz dopisujemy "opens target;" i powtarzamy caly proces -
            // ten sam kod refleksyjny, jedyna zmiana to deskryptor modulu.
            Files.writeString(targetModuleDir.resolve("module-info.java"), """
                    module reflect.target {
                        exports target;
                        opens target;
                    }
                    """);

            Path outWithOpens = opensWorkDir.resolve("out-with-opens");
            Files.createDirectories(outWithOpens);
            runProcess("javac (kompilacja Z opens)", javacExe,
                    "--module-source-path", srcDir.toString(),
                    "-d", outWithOpens.toString(),
                    "--module", "reflect.target,reflect.consumer");

            System.out.println("\n--- Uruchomienie Z 'opens target' (oczekiwany sukces) ---");
            runProcess("java (z opens)", javaExe,
                    "--module-path", outWithOpens.toString(),
                    "--module", "reflect.consumer/consumer.Main");

            System.out.println("""

                    Wniosek: DOKLADNIE ten sam kod refleksyjny (Field.setAccessible) -
                    raz zawiodl (InaccessibleObjectException), raz zadzialal - JEDYNA
                    roznica to jedna linia "opens target;" w module-info.java modulu
                    reflect.target. To jest wlasnie powod, dla ktorego frameworki jak
                    Hibernate/Jackson/Spring wymagaja "opens" dla pakietow z Twoimi
                    encjami/DTO, jesli Twoja aplikacja jest zbudowana jako prawdziwy
                    modul JPMS.
                    """);

        } finally {
            deleteRecursively(opensWorkDir);
        }

        /*
         * ============================================================
         * 🔹 exports ... to ... - EKSPORT KWALIFIKOWANY
         * ============================================================
         * Zwykle "exports pkg;" udostepnia pakiet KAZDEMU modulowi, ktory
         * "requires" nasz modul. Czasem chcemy udostepnic pakiet tylko
         * WYBRANYM, ZAUFANYM modulom (np. wewnetrzne API miedzy dwoma
         * modulami tej samej biblioteki, ktorego uzytkownicy koncowi nie
         * powinni widziec) - do tego sluzy EKSPORT KWALIFIKOWANY:
         *
         *     module my.api {
         *         exports com.example.internal.bridge to my.plugin.trusted;
         *     }
         *
         * Pakiet "com.example.internal.bridge" jest widoczny WYLACZNIE
         * dla modulu "my.plugin.trusted" - kazdy inny modul (nawet jesli
         * "requires my.api") dostanie DOKLADNIE taki sam blad kompilacji
         * jak przy braku exports w ogole ("package ... is not visible").
         * Sam JDK intensywnie uzywa tego mechanizmu do kontrolowanego
         * udostepniania wewnetrznych pakietow miedzy MODULAMI SAMEGO JDK.
         */

        System.out.println("\n--- DEMO: exports ... to ... (eksport kwalifikowany) ---\n");

        Path qualifiedWorkDir = Files.createTempDirectory("lesson28-qualified-exports");
        try {
            Path srcDir = qualifiedWorkDir.resolve("src");
            Path apiDir = srcDir.resolve("qual.api");
            Path friendDir = srcDir.resolve("qual.friend");
            Path strangerDir = srcDir.resolve("qual.stranger");
            Files.createDirectories(apiDir.resolve("qual"));
            Files.createDirectories(friendDir.resolve("friend"));
            Files.createDirectories(strangerDir.resolve("stranger"));

            // "exports qual to qual.friend;" - TYLKO modul qual.friend
            // widzi pakiet "qual", nikt inny (nawet requires qual.api nie pomoze).
            Files.writeString(apiDir.resolve("module-info.java"), """
                    module qual.api {
                        exports qual to qual.friend;
                    }
                    """);
            Files.writeString(apiDir.resolve("qual").resolve("Secret.java"), """
                    package qual;

                    public class Secret {
                        public static String reveal() { return "tylko dla przyjaciol"; }
                    }
                    """);

            Files.writeString(friendDir.resolve("module-info.java"), """
                    module qual.friend {
                        requires qual.api;
                    }
                    """);
            Files.writeString(friendDir.resolve("friend").resolve("Main.java"), """
                    package friend;

                    import qual.Secret;

                    public class Main {
                        public static void main(String[] args) {
                            System.out.println("Przyjaciel widzi: " + Secret.reveal());
                        }
                    }
                    """);

            Files.writeString(strangerDir.resolve("module-info.java"), """
                    module qual.stranger {
                        requires qual.api;
                    }
                    """);
            Files.writeString(strangerDir.resolve("stranger").resolve("Main.java"), """
                    package stranger;

                    import qual.Secret;

                    public class Main {
                        public static void main(String[] args) {
                            System.out.println("Obcy widzi: " + Secret.reveal());
                        }
                    }
                    """);

            Path friendOut = qualifiedWorkDir.resolve("out-friend");
            Files.createDirectories(friendOut);
            System.out.println("--- Kompilacja qual.api + qual.friend (modul UPRAWNIONY - oczekiwany sukces) ---");
            runProcess("javac (qual.friend)", javacExe,
                    "--module-source-path", srcDir.toString(),
                    "-d", friendOut.toString(),
                    "--module", "qual.api,qual.friend");

            Path strangerOut = qualifiedWorkDir.resolve("out-stranger");
            Files.createDirectories(strangerOut);
            System.out.println("\n--- Kompilacja qual.api + qual.stranger (modul NIEUPRAWNIONY - oczekiwany blad) ---");
            runProcess("javac (qual.stranger)", javacExe,
                    "--module-source-path", srcDir.toString(),
                    "-d", strangerOut.toString(),
                    "--module", "qual.api,qual.stranger");
            System.out.println("Mimo ze qual.stranger tez ma 'requires qual.api', NIE jest na liscie 'to' - kompilacja pada.");

        } finally {
            deleteRecursively(qualifiedWorkDir);
        }

        /*
         * ============================================================
         * 🔹 uses / provides - ServiceLoader NATYWNY DLA MODULOW
         * ============================================================
         * Lekcja 26 pokazala ServiceLoader oparty na pliku
         * META-INF/services/pelna.nazwa.Interfejsu. W prawdziwym module
         * JPMS ta sama mechanika jest wyrazona WPROST w module-info.java,
         * BEZ zadnego pliku tekstowego:
         *
         *  - modul KONSUMENTA deklaruje "uses pelna.nazwa.Interfejsu;"
         *    - "ja BEDE szukal dostawcow tej uslugi przez ServiceLoader".
         *  - modul DOSTAWCY deklaruje
         *    "provides pelna.nazwa.Interfejsu with pelna.nazwa.Implementacji;"
         *    - "JA dostarczam implementacje tej uslugi".
         *
         * java.util.ServiceLoader.load(Interfejs.class) DZIALA DOKLADNIE
         * TAK SAMO jak w Lekcji 26 - JEDYNA roznica to ZRODLO rejestracji
         * (deskryptor modulu zamiast pliku tekstowego). To wieksze
         * bezpieczenstwo (bledy w "provides"/"uses" sa wykrywane juz
         * PRZY KOMPILACJI, np. gdy podana klasa nie implementuje
         * interfejsu) i lepsza czytelnosc (zaleznosci widoczne wprost w
         * module-info.java, a nie ukryte w pliku tekstowym w zasobach).
         */

        System.out.println("\n--- DEMO: uses/provides (JPMS-natywny ServiceLoader, nawiazanie do Lekcji 26) ---\n");

        Path spiWorkDir = Files.createTempDirectory("lesson28-uses-provides");
        try {
            Path srcDir = spiWorkDir.resolve("src");
            Path apiDir = srcDir.resolve("spi.api");
            Path providerDir = srcDir.resolve("spi.provider");
            Path consumerDir = srcDir.resolve("spi.consumer");
            Files.createDirectories(apiDir.resolve("spi"));
            Files.createDirectories(providerDir.resolve("spi").resolve("provider"));
            Files.createDirectories(consumerDir.resolve("consumer"));

            Files.writeString(apiDir.resolve("module-info.java"), """
                    module spi.api {
                        exports spi;
                    }
                    """);
            Files.writeString(apiDir.resolve("spi").resolve("GreetingService.java"), """
                    package spi;

                    public interface GreetingService {
                        String greet(String name);
                    }
                    """);

            // "provides spi.GreetingService with spi.provider.PolishGreetingServiceImpl;"
            // ZASTEPUJE plik META-INF/services/spi.GreetingService z Lekcji 26 -
            // ta jedna linia w module-info.java to CALA rejestracja.
            Files.writeString(providerDir.resolve("module-info.java"), """
                    module spi.provider {
                        requires spi.api;
                        provides spi.GreetingService with spi.provider.PolishGreetingServiceImpl;
                    }
                    """);
            Files.writeString(providerDir.resolve("spi").resolve("provider").resolve("PolishGreetingServiceImpl.java"), """
                    package spi.provider;

                    import spi.GreetingService;

                    public class PolishGreetingServiceImpl implements GreetingService {
                        @Override
                        public String greet(String name) {
                            return "Czesc, " + name + "! (modul spi.provider, znaleziony przez uses/provides)";
                        }
                    }
                    """);

            // "uses spi.GreetingService;" deklaruje: "ten modul konsumuje
            // te usluge przez ServiceLoader" - konsument NIE musi znac ani
            // "requires" modulu dostawcy (spi.provider) - to jest wlasnie
            // sedno luznego powiazania SPI, kontynuacja idei z Lekcji 26.
            Files.writeString(consumerDir.resolve("module-info.java"), """
                    module spi.consumer {
                        requires spi.api;
                        uses spi.GreetingService;
                    }
                    """);
            Files.writeString(consumerDir.resolve("consumer").resolve("Main.java"), """
                    package consumer;

                    import spi.GreetingService;
                    import java.util.ServiceLoader;

                    public class Main {
                        public static void main(String[] args) {
                            ServiceLoader<GreetingService> loader = ServiceLoader.load(GreetingService.class);
                            int count = 0;
                            for (GreetingService service : loader) {
                                System.out.println(service.getClass().getName() + " -> " + service.greet("Kursancie"));
                                count++;
                            }
                            System.out.println("Liczba znalezionych dostawcow: " + count);
                        }
                    }
                    """);

            Path spiOut = spiWorkDir.resolve("out");
            Files.createDirectories(spiOut);
            System.out.println("--- Kompilacja spi.api + spi.provider + spi.consumer ---");
            runProcess("javac (uses/provides)", javacExe,
                    "--module-source-path", srcDir.toString(),
                    "-d", spiOut.toString(),
                    "--module", "spi.api,spi.provider,spi.consumer");

            /*
             * WAZNY SZCZEGOL: spi.consumer NIE ma "requires spi.provider"
             * (celowo - konsument nie powinien znac konkretnych
             * dostawcow!). Zeby modul spi.provider w ogole trafil do
             * GRAFU MODULOW uruchamianej aplikacji, trzeba go dorzucic
             * jawnie przez --add-modules (albo, w typowym wdrozeniu z
             * jlink/jpackage, dostawcy sa zwykle od razu wlaczeni do
             * obrazu runtime). Bez --add-modules modul spi.provider
             * zostalby zignorowany i ServiceLoader znalazlby 0 dostawcow.
             */
            System.out.println("\n--- Uruchomienie spi.consumer (z --add-modules spi.provider) ---");
            runProcess("java (uses/provides)", javaExe,
                    "--module-path", spiOut.toString(),
                    "--add-modules", "spi.provider",
                    "--module", "spi.consumer/consumer.Main");

        } finally {
            deleteRecursively(spiWorkDir);
        }

        /*
         * ============================================================
         * 🔹 MODULY AUTOMATYCZNE I MIGRACJA CLASSPATH -> MODULY (krotko)
         * ============================================================
         * Migracja duzego, istniejacego projektu classpathowego do JPMS
         * zwykle przebiega w KROKACH, a nie "wszystko na raz":
         *
         *  1. STATUS QUO - caly kod i wszystkie zaleznosci na classpath,
         *     w module nienazwanym (tak jak caly ten kurs dzisiaj).
         *  2. MIGRACJA CZESCIOWA - wlasny kod dostaje module-info.java i
         *     trafia na module path, ALE zaleznosci (biblioteki
         *     zewnetrzne bez wlasnego module-info.java) tez przenosimy na
         *     module path - staja sie MODULAMI AUTOMATYCZNYMI (Lekcja 27) -
         *     powstaje graf MIESZANY (nazwane + automatyczne moduly).
         *  3. PELNA MODULARNOSC - kazda zaleznosc ma juz WLASNY, prawdziwy
         *     module-info.java (coraz wiecej popularnych bibliotek to
         *     ma) - pelna silna enkapsulacja w calym systemie.
         *
         * Narzedzia JDK wspierajace ten proces: "jdeps" (analizuje kod
         * BAJTOWY i wypisuje, jakich pakietow/modulow faktycznie uzywa -
         * pomaga ustalic tresc przyszlego module-info.java PRZED
         * migracja), oraz "jlink" (buduje WLASNY, minimalny obraz
         * uruchomieniowy JVM zlozony TYLKO z uzywanych modulow - mniejszy
         * rozmiar, szybszy start, mniejsza powierzchnia ataku).
         */

        System.out.println("\n=== KONIEC LEKCJI 28 ===");

        /*
         * ============================================================
         * 📌 UCZCIWA NOTATKA: DLACZEGO TEN KURS ZOSTAJE NA CLASSPATH
         * ============================================================
         * Caly projekt javaQuest (WSZYSTKIE 14 rozdzialow, w tym ten)
         * celowo NIE MA ani jednego pliku module-info.java w
         * src/main/java i NIE POWINIEN go dostac. Powody:
         *
         *  - Kurs jest zaprojektowany jako JEDNA, plaska przestrzen
         *    pakietow (com.example.javaquest.<rozdzial>.<lekcja>), w
         *    ktorej kursant swobodnie "przeskakuje" miedzy lekcjami i
         *    rozdzialami (np. odwolania miedzy lekcjami w komentarzach,
         *    wspolne narzedzia). Silna enkapsulacja JPMS wymuszalaby
         *    JAWNE "exports"/"requires" miedzy KAZDA para pakietow lekcji -
         *    to zaprzeczaloby idei jednego, spojnego projektu-kursu.
         *  - Wszystkie demo w tej lekcji (i w Lekcji 26-27) swiadomie
         *    budowaly OSOBNE, jednorazowe mini-projekty modulowe w
         *    katalogach TYMCZASOWYCH (Files.createTempDirectory) -
         *    dokladnie po to, zeby pokazac PRAWDZIWY JPMS (prawdziwy
         *    javac/java jako podproces), nie naruszajac ani odrobiny
         *    struktury glownego projektu.
         *  - To w pelni realistyczne odzwierciedlenie sytuacji w branzy:
         *    WIEKSZOSC projektow (zwlaszcza oparte o Spring Boot, jak
         *    docelowo bedzie ten kurs) do dzis swiadomie zostaje na
         *    classpath - JPMS jest bardziej powszechny w bibliotekach
         *    (ktore dostarczaja module-info.java, zeby ich uzytkownicy
         *    MOGLI go uzyc) niz w aplikacjach koncowych.
         */

        /*
         * ============================================================
         * 📌 ZAMKNIECIE KLASTRA: ODKRYWANIE USLUG I MODULARNOSC (26-28)
         * ============================================================
         * - Lekcja 26: SPI + java.util.ServiceLoader - dekady sprawdzony
         *   wzorzec "znajdz implementacje w runtime", dzialajacy zarowno
         *   na classpath (META-INF/services), jak i w modulach.
         * - Lekcja 27: JPMS - podstawy - modul, requires, exports, modul
         *   nienazwany, moduly automatyczne.
         * - Lekcja 28 (ta): JPMS - zaawansowane - opens (deep reflection),
         *   exports...to (eksport kwalifikowany), uses/provides
         *   (ServiceLoader natywny dla modulow).
         *
         * Ten klaster przygotowuje grunt pod:
         *  - Lekcje 29 - dobre praktyki calego rozdzialu _14_advancedjava
         *    (w tym: kiedy WARTO siegnac po JPMS, a kiedy zostac na
         *    classpath),
         *  - Lekcje 30 - projekt kapstonowy, laczacy mechanizmy poznane
         *    w calym rozdziale (generyki, programowanie funkcyjne,
         *    adnotacje/refleksja, nowoczesna Java, niezmiennosc, a
         *    takze - tam gdzie to sensowne - SPI/moduly z tego klastra).
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - "opens pkg;" (albo "open module") kontroluje DEEP REFLECTION
         *   (setAccessible do prywatnych skladowych) - OSOBNO od "exports",
         *   ktore kontroluje widocznosc typow w compile time. Zweryfikowane
         *   realnie: BEZ opens -> InaccessibleObjectException, Z opens ->
         *   sukces, identyczny kod refleksyjny.
         * - "exports pkg to modA, modB;" (eksport kwalifikowany) udostepnia
         *   pakiet TYLKO wymienionym modulom - kazdy inny modul dostaje
         *   ten sam blad "not visible", co przy braku exports w ogole.
         * - "uses Interfejs;" (w konsumencie) + "provides Interfejs with
         *   Impl;" (w dostawcy) to JPMS-natywna wersja ServiceLoadera z
         *   Lekcji 26 - ten sam java.util.ServiceLoader.load(...), ale
         *   rejestracja w module-info.java zamiast pliku META-INF/services.
         *   Modul-dostawca trzeba jawnie dolaczyc do grafu (np. przez
         *   --add-modules), jesli konsument go nie "requires".
         * - Migracja classpath -> moduly to zwykle proces stopniowy przez
         *   moduly automatyczne; "jdeps" pomaga ustalic zaleznosci przed
         *   migracja, "jlink" buduje wlasny, minimalny obraz runtime.
         * - Ten kurs (javaQuest) SWIADOMIE zostaje na classpath (modul
         *   nienazwany) w calosci - wszystkie demo JPMS w Lekcjach 26-28
         *   dzialaly w calkowicie osobnych, tymczasowych mini-projektach.
         */
    }

    /**
     * Uruchamia PRAWDZIWY proces systemowy (javac albo java), wypisuje
     * dokladna komende, przechwytuje i wypisuje jego CALY output
     * (stdout+stderr polaczone), i zwraca kod wyjscia.
     */
    private static int runProcess(String description, String... command) throws IOException, InterruptedException {
        System.out.println(">>> [" + description + "] " + String.join(" ", command));

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (InputStream in = process.getInputStream()) {
            byte[] output = in.readAllBytes();
            if (output.length > 0) {
                System.out.print(new String(output));
            }
        }

        int exitCode = process.waitFor();
        System.out.println("(kod wyjscia: " + exitCode + ")");
        return exitCode;
    }

    /**
     * Rekurencyjnie usuwa caly katalog tymczasowy (od najglebszych plikow
     * do korzenia) - sprzatanie po demo, wywolywane w bloku finally.
     */
    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    // Ignorujemy pojedyncze bledy sprzatania katalogu tymczasowego.
                }
            });
        }
    }
}
