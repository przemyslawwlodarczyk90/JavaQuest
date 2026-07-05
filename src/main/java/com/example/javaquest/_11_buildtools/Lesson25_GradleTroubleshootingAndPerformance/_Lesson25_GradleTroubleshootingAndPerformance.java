package com.example.javaquest._11_buildtools.Lesson25_GradleTroubleshootingAndPerformance;

public class _Lesson25_GradleTroubleshootingAndPerformance {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 25: GRADLE - TROUBLESHOOTING I WYDAJNOSC ===");
        System.out.println("Ta lekcja zamyka blok Gradle (Lekcje 16-22) - skupia sie na PRAKTYCE:");
        System.out.println("jak diagnozowac i naprawiac typowe problemy z Gradle 'na zywo'.");
        System.out.println("(Wszystkie polecenia './gradlew ...' sa ILUSTRACYJNE - ten wrapper nie");
        System.out.println(" istnieje w tym repo, patrz CLAUDE.md, sekcja _11_buildtools.)");

        /*
         * ============================================================
         * 📦 GRADLE DAEMON - CO TO JEST I CZEMU MA ZNACZENIE
         * ============================================================
         * Gradle Daemon to DLUGOZYJACY proces JVM w tle, ktory Gradle
         * uruchamia PRZY PIERWSZYM wywolaniu "./gradlew ..." i pozostawia
         * dzialajacy MIEDZY kolejnymi wywolaniami. Bez daemona, KAZDE
         * wywolanie Gradle musialoby od zera: odpalic nowa JVM, wczytac
         * WSZYSTKIE klasy Gradle, sparsowac build.gradle, zbudowac model
         * projektu - to zajmuje kilka sekund SAMEGO rozgrzewania, ZANIM
         * jakikolwiek task zacznie sie wykonywac.
         *
         * Z daemonem: druga i kolejne wywolania "./gradlew" korzystaja z
         * JUZ dzialajacej, "rozgrzanej" JVM (JIT juz skompilowal
         * najczesciej uzywany kod Gradle, klasy sa juz wczytane) - stąd
         * ODCZUWALNIE szybsze starty (czesto z 5-10s na <1s).
         *
         * Przydatne komendy:
         * - "./gradlew --stop"              -> zatrzymuje WSZYSTKIE
         *   dzialajace demony Gradle (przydatne po zmianie wersji JDK,
         *   albo gdy demon "zaklinowal sie" w dziwnym stanie)
         * - "-Dorg.gradle.daemon=false"      -> wylacza demona dla
         *   JEDNEGO konkretnego wywolania (np. na CI, gdzie i tak
         *   kazdy build to nowy, jednorazowy kontener - demon tylko
         *   zajmuje pamiec bez korzysci, bo NIE BEDZIE drugiego wywolania)
         */

        System.out.println("\n=== GRADLE DAEMON ===");
        System.out.println("Daemon = dlugozyjacy proces JVM w tle - pierwsze wywolanie 'rozgrzewa' go,");
        System.out.println("kolejne sa DUZO szybsze (JIT + klasy juz wczytane).");
        System.out.println("./gradlew --stop                 -> zatrzymuje wszystkie demony (np. po zmianie JDK).");
        System.out.println("-Dorg.gradle.daemon=false         -> wylacza demona dla JEDNEGO wywolania (typowe na CI).");

        /*
         * ============================================================
         * 🔹 --profile I BUILD SCANS - DIAGNOZA WYDAJNOSCI
         * ============================================================
         * Gdy build jest "podejrzanie wolny", Gradle daje dwa poziomy
         * diagnostyki:
         *
         * - "./gradlew build --profile" -> generuje LOKALNY raport HTML
         *   (build/reports/profile/profile-<timestamp>.html) z czasem
         *   trwania KAZDEGO taska - pierwszy krok do znalezienia "ktory
         *   task zjada najwiecej czasu".
         *
         * - Build Scans (build.scans plugin / "--scan" flag) -> WYSYLA
         *   (za Twoja zgoda, do gradle.com albo wlasnego serwera Develocity)
         *   BARDZO szczegolowy raport online - nie tylko czasy taskow, ale
         *   PELNY graf zaleznosci, konfiguracje srodowiska, cache hit/miss
         *   per task, porownanie z poprzednimi buildami. Bardziej
         *   zaawansowane niz --profile, ale wymaga (opcjonalnej) wysylki
         *   danych poza Twoja maszyne - KONCEPCYJNIE wazne, warto wiedziec,
         *   ze taka opcja istnieje, nawet bez uzywania jej w tym kursie.
         */

        String profileIllustrative = """
                > Task :compileJava
                > Task :test
                > Task :jar
                BUILD SUCCESSFUL in 4s

                See the profiling report at: file:///path/to/build/reports/profile/profile-2026-07-05-12-00-00.html
                A fine-grained performance profile is available: use the --scan option.
                """;

        System.out.println("\n=== ILUSTRACYJNY OUTPUT './gradlew build --profile' ===");
        System.out.println("(To NIE jest realny output - ilustracyjny format, zeby pokazac, GDZIE Gradle");
        System.out.println(" zapisuje raport profilowania.)");
        System.out.println(profileIllustrative);
        System.out.println("--profile   -> lokalny raport HTML z czasami taskow (pierwszy krok diagnozy).");
        System.out.println("--scan      -> (opcjonalnie) szczegolowy raport ONLINE (Build Scan) - wymaga zgody na wyslanie danych.");

        /*
         * ============================================================
         * 🔍 --offline I --refresh-dependencies
         * ============================================================
         * Dwie flagi zwiazane z rozwiazywaniem zaleznosci, czesto uzywane
         * w PRZECIWNYCH sytuacjach:
         *
         * - "--offline"              -> Gradle NIE probuje kontaktowac
         *   sie z ZADNYM zdalnym repozytorium - uzywa WYLACZNIE tego,
         *   co juz jest w lokalnym cache'u (~/.gradle/caches). Przydatne
         *   bez internetu (np. w podrozy) - ALE zawiedzie, jesli
         *   potrzebna zaleznosc NIE JEST jeszcze w cache'u.
         * - "--refresh-dependencies" -> ODWROTNIE: WYMUSZA ponowne
         *   sprawdzenie WSZYSTKICH zaleznosci w zdalnych repozytoriach
         *   (ignorujac lokalny cache), nawet jesli lokalnie "wyglada"
         *   na aktualne. Przydatne, gdy podejrzewasz, ze cache jest
         *   NIEAKTUALNY albo USZKODZONY (np. po przerwanym pobieraniu),
         *   albo gdy zaleznosc typu SNAPSHOT zmienila sie na serwerze,
         *   a Gradle wciaz uzywa starej, zcache'owanej wersji.
         */

        System.out.println("\n=== --offline vs --refresh-dependencies ===");
        System.out.println("--offline               -> UZYJ TYLKO lokalnego cache'u (~/.gradle/caches), bez sieci.");
        System.out.println("--refresh-dependencies   -> WYMUS ponowne sprawdzenie WSZYSTKICH zaleznosci zdalnie,");
        System.out.println("                             ignorujac (podejrzany/nieaktualny) lokalny cache.");

        /*
         * ============================================================
         * 🔹 BŁĄD #1: WRAPPER CHECKSUM MISMATCH
         * ============================================================
         * Gradle Wrapper (Lekcja 16) MOZE (opcjonalnie, ale zalecane w
         * bezpiecznych srodowiskach) weryfikowac SUME KONTROLNA (checksum)
         * pobranej distrybucji Gradle - zeby wykryc uszkodzone/podmienione
         * pobieranie (atak typu "man in the middle" na proces budowania -
         * realne ryzyko bezpieczenstwa lancucha dostaw oprogramowania).
         */

        String wrapperChecksumError = """
                Exception in thread "main" java.security.SecurityException:
                Verification of Gradle distribution failed!

                Your Gradle distribution may have been tampered with.
                Confirm that the 'distributionSha256Sum' property in your
                gradle-wrapper.properties file is correct.
                """;

        System.out.println("\n=== BLAD ILUSTRACYJNY: wrapper checksum mismatch ===");
        System.out.println(wrapperChecksumError);
        System.out.println("NAPRAWA: zweryfikuj/zaktualizuj 'distributionSha256Sum' w gradle-wrapper.properties");
        System.out.println("(wartosc mozna wygenerowac przez './gradlew wrapper --gradle-version X --gradle-distribution-sha256-sum <suma>'),");
        System.out.println("albo (jesli PEWIEN, ze zrodlo jest zaufane) usun te wlasciwosc, jesli nie jest wymagana.");

        /*
         * ============================================================
         * 🔹 BŁĄD #2: "Could not find" ZALEŻNOŚĆ
         * ============================================================
         */

        String couldNotFindError = """
                Execution failed for task ':compileJava'.
                > Could not resolve all files for configuration ':compileClasspath'.
                   > Could not find com.google.code.gsonn:gson:2.11.0.
                     Searched in the following locations:
                       - https://repo.maven.apache.org/maven2/com/google/code/gsonn/gson/2.11.0/gson-2.11.0.pom
                     Required by:
                         project :
                """;

        System.out.println("\n=== BLAD ILUSTRACYJNY: 'Could not find' zaleznosc ===");
        System.out.println(couldNotFindError);
        System.out.println("NAPRAWA: sprawdz literowke w koordynatach (tu: 'gsonn' zamiast 'gson' - klasyczna");
        System.out.println("literowka!), sprawdz, czy repozytorium z ta biblioteka jest w repositories{},");
        System.out.println("sprawdz wersje (moze zostala wycofana/nigdy nie istniala).");

        /*
         * ============================================================
         * 🔹 BŁĄD #3: NIEZGODNA WERSJA JAVA TOOLCHAIN
         * ============================================================
         * "Toolchain" to mechanizm Gradle pozwalajacy zadeklarowac, JAKIEJ
         * wersji JDK build POTRZEBUJE do kompilacji/testow - NIEZALEZNIE
         * od tego, jaka wersja JDK jest zainstalowana jako domyslna w
         * systemie (Gradle moze nawet SAM POBRAC odpowiedni JDK, jesli
         * go nie znajdzie lokalnie!).
         */

        String toolchainSnippet = """
                java {
                    toolchain {
                        languageVersion = JavaLanguageVersion.of(21)
                    }
                }
                """;

        String toolchainError = """
                > Task :compileJava FAILED
                Cannot find a Java installation on your machine matching:
                {languageVersion=17, vendor=any vendor, implementation=vendor-specific}
                """;

        System.out.println("\n=== JAVA TOOLCHAIN (build.gradle) ===");
        System.out.println(toolchainSnippet);
        System.out.println("=== BLAD ILUSTRACYJNY: niezgodna wersja Java toolchain ===");
        System.out.println(toolchainError);
        System.out.println("NAPRAWA: zainstaluj wymagana wersje JDK lokalnie, albo pozwol Gradle ja pobrac");
        System.out.println("automatycznie (domyslnie wlaczone auto-provisioning toolchainow w nowszych Gradle),");
        System.out.println("albo (jesli to swiadoma decyzja) zmien languageVersion w build.gradle na dostepna wersje.");

        /*
         * ============================================================
         * 🔹 BŁĄD #4: CACHE PROBLEMS (rozwiniecie z Lekcji 17)
         * ============================================================
         */

        String cacheCorruptionError = """
                Execution failed for task ':compileJava'.
                > Could not read workspace metadata from
                  /home/user/.gradle/caches/8.10/executionHistory/executionHistory.bin
                > Premature end of file
                """;

        System.out.println("\n=== BLAD ILUSTRACYJNY: uszkodzony lokalny cache ===");
        System.out.println(cacheCorruptionError);
        System.out.println("NAPRAWA (od najlagodniejszej do najbardziej drastycznej):");
        System.out.println("1. './gradlew build --refresh-dependencies' (patrz wyzej).");
        System.out.println("2. './gradlew --stop' (zatrzymaj demony, ktore moga trzymac zablokowane pliki cache).");
        System.out.println("3. W ostatecznosci: usunac katalog ~/.gradle/caches (Gradle odbuduje go od zera -");
        System.out.println("   kosztowne czasowo przy pierwszym nastepnym buildzie, ale gwarantowanie 'czysty stan').");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Gradle Daemon - dlugozyjacy proces JVM w tle dla szybszych
         *   startow; --stop zatrzymuje wszystkie, -Dorg.gradle.daemon=false
         *   wylacza dla jednego wywolania (typowe na CI).
         * - --profile (lokalny raport HTML czasow taskow) i Build Scans
         *   (szczegolowy raport online, opcjonalny) - diagnoza wydajnosci.
         * - --offline (tylko lokalny cache) vs --refresh-dependencies
         *   (wymus ponowne sprawdzenie zdalne) - przeciwne scenariusze.
         * - Typowe realne bledy: wrapper checksum mismatch (bezpieczenstwo
         *   distrybucji), "Could not find" (literowka/brak repo/zla wersja),
         *   niezgodny Java toolchain (brak wymaganej wersji JDK), cache
         *   problems (uszkodzony ~/.gradle/caches).
         * - To zamyka blok Gradle (Lekcje 16-22) - dalej: Lekcja 23
         *   (porownanie Ant/Maven/Gradle), 24 (migracje), 25 (praktyka),
         *   26 (troubleshooting ogolny/JVM), 27 (capstone).
         */

        System.out.println("\n=== KONIEC LEKCJI 25 ===");
    }
}
