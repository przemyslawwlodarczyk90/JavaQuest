package com.example.javaquest._11_buildtools.Lesson10_AntDebugging;

public class _Exercises_Lesson10_AntDebugging {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InfoVsWarnLevels {
        /*
         * 🧪 Zadanie 1:
         * Odpal ten sam prosty target embedded Antem DWA razy - raz z DefaultLogger na
         * poziomie Project.MSG_INFO, raz na Project.MSG_WARN. Porownaj (opisz w komentarzu),
         * ile linii wypisal kazdy przebieg.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ThreeEchoSystemFacts {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj target z trzema <echo> wypisujacymi ${ant.version}, ${os.name} i
         * ${java.home}. Wykonaj go i sprawdz, czy wszystkie trzy wartosci wypisaly sie
         * poprawnie (Ant automatycznie eksponuje wlasciwosci systemowe jako property).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UndefinedPropertyInsideEcho {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj target z <echo message="Wartosc: ${totally.unset.prop}"/> (property nigdy
         * nieustawiona). Wykonaj go i wypisz, ze <echo> NIE rzuca bledu - po prostu wypisuje
         * literalny tekst "${totally.unset.prop}" - w kontrascie do przypadku javac z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MkdirWithLiteralPropertyName {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj target z <mkdir dir="${also.unset}"/> (property nigdy nieustawiona).
         * Wykonaj go i sprawdz na dysku, czy powstal katalog o LITERALNEJ nazwie
         * "${also.unset}" - wypisz Files.exists(...) dla takiej sciezki jako dowod pulapki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UnknownTaskElement {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj build.xml z literowka w nazwie taska (np. "&lt;javacc .../&gt;" zamiast
         * "&lt;javac/&gt;" - nieistniejacy element). Sprobuj skonfigurowac/wykonac go w
         * try/catch i wypisz prawdziwy komunikat bledu o nieznanym elemencie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FailTaskCustomMessage {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj target zawierajacy &lt;fail message="Cos poszlo zle - kod 42"/&gt;. Zlap
         * BuildException i wypisz dokladnie ten komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DestdirIsAFile {
        /*
         * 🧪 Zadanie 7:
         * Stworz na dysku ZWYKLY plik (nie katalog) o nazwie "output". Zbuduj target z
         * &lt;javac destdir="output" .../&gt; wskazujacym na ten plik jako destdir. Zlap
         * i wypisz prawdziwy komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TwoDifferentBuildFilesSameDir {
        /*
         * 🧪 Zadanie 8:
         * W JEDNYM katalogu bazowym zapisz DWA rozne pliki: "build.xml" (target "a") i
         * "custom-build.xml" (target "b" - odpowiednik "ant -f custom-build.xml"). Wczytaj
         * i wykonaj OBA (dwa niezalezne ProjectHelper.configureProject), wypisujac, ktory
         * plik/target zostal odpalony w kazdym przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SelfDependingTarget {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj target "self" depends="self" (zalezy od samego siebie). Sprobuj go
         * wykonac w try/catch i wypisz prawdziwy komunikat BuildException o cyklu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_EchoLevelFiltering {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj target z &lt;echo level="warning" message="Uwaga!"/&gt; oraz zwyklym
         * &lt;echo message="Info."/&gt;. Odpal go raz na MSG_WARN i raz na MSG_INFO,
         * porownujac ktore linie sie pojawily - wypisz wnioski o filtrowaniu wg poziomu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FourTargetChainVerbosity {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj lancuch 4 targetow A->B->C->D (kazdy zalezy od poprzedniego, kazdy
         * wypisuje wlasna nazwe przez echo). Wykonaj go na MSG_VERBOSE i MSG_INFO,
         * porownujac (opisowo) liczbe dodatkowych linii "wewnetrznych" logera na wyzszym
         * poziomie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CopyMissingSourceFile {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj target z &lt;copy file="nieistniejacy.txt" tofile="kopia.txt"/&gt;
         * (zrodlo nie istnieje). Zlap BuildException, wypisz jego komunikat i dopisz WLASNY
         * jednoparagrafowy komentarz z krokami diagnozy (sprawdz sciezke, sprawdz basedir,
         * sprawdz katalog roboczy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_JavacCompileErrorSurfaced {
        /*
         * 🧪 Zadanie 13:
         * Wygeneruj plik .java z CELOWYM bledem skladni (np. brakujacy srednik). Zbuduj
         * target &lt;javac&gt; kompilujacy ten plik, zlap BuildException i wypisz jego
         * komunikat wraz z (jesli dostepny) tekstem bledu kompilatora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AlwaysPrintElapsedTime {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode runTimed(Project project, String target), ktora mierzy czas
         * (System.nanoTime) wykonania targetu i ZAWSZE (sukces lub blad - try/finally)
         * wypisuje czas trwania w milisekundach. Przetestuj ja na jednym udanym i jednym
         * nieudanym targecie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BytecodeVersionInspection {
        /*
         * 🧪 Zadanie 15:
         * Skompiluj mala klase z &lt;javac source="8" target="8" .../&gt; (starszy poziom
         * bajtkodu). Odczytaj pierwsze bajty powstalego pliku .class (Files.readAllBytes) i
         * wypisz bajty 6-7 (major version) - pokazujac, jak realnie wykrywa sie wersje
         * bajtkodu odpowiadajaca za UnsupportedClassVersionError.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AbsolutePathPortabilityWarning {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj DWA targety: jeden z twardo zakodowana sciezka charakterystyczna dla
         * jednego systemu (np. "C:\\dane\\plik.txt"), drugi z odpowiednikiem
         * "${basedir}/dane/plik.txt". Wypisz komentarzem, dlaczego drugi wariant jest
         * bezpieczny na Windows/Linux/CI, a pierwszy nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_EncodingMismatchDemo {
        /*
         * 🧪 Zadanie 17:
         * Zapisz plik .java zawierajacy polskie znaki w Stringu, uzywajac Charsetu
         * ISO-8859-1, a nastepnie skompiluj go &lt;javac encoding="UTF-8"&gt; (rozny
         * encoding zapisu i kompilacji). Zaobserwuj i wypisz symptom niezgodnosci (np.
         * zniekawiony tekst po odpaleniu skompilowanej klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AvailableGuardBeforeRiskyTask {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj target uzywajacy &lt;available file="wymagany.txt" property="toolPresent"/&gt;,
         * a nastepnie kolejny target if="toolPresent" wykonujacy "ryzykowny" krok. Uruchom
         * scenariusz bez i z plikiem wymagany.txt, porownujac zachowanie z podejsciem
         * "pozwol sie wywalic i czytaj komunikat" z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FailFastWithinTarget {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj target z DWOMA taskami, gdzie PIERWSZY zawodzi (np. &lt;fail/&gt;), a
         * DRUGI powinien wypisac echo widoczny "gdyby build kontynuowal". Wykonaj go w
         * try/catch i wypisz dowod, ze drugi task NIGDY sie nie wykonal (fail-fast w
         * ramach jednego targetu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_EnvironmentPropertyPrefix {
        /*
         * 🧪 Zadanie 20:
         * Uzyj &lt;property environment="env"/&gt; i wypisz ${env.PATH} (albo inna
         * zmienna srodowiskowa dostepna na Twoim systemie). Sprobuj TAKZE uzyc ${PATH} BEZ
         * prefiksu "env." i wypisz, ze to sie NIE rozwiazuje (literalny tekst) - klasyczna
         * pulapka zapomnianego prefiksu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InMemoryLogCapturer {
        /*
         * 🧪 Zadanie 21:
         * Napisz wariant DefaultLoggera (albo prosty BuildListener) zapisujacy WSZYSTKIE
         * komunikaty do java.util.List&lt;String&gt; w pamieci, zamiast tylko je wypisywac.
         * Przepusc przez niego jeden z celowo nieudanych targetow z lekcji i przeszukaj
         * zebrany log w poszukiwaniu slowa kluczowego wskazujacego blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DependencyChainPartialFailure {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj target depends="krok1,krok2" gdzie krok2 zawodzi (krok1 sie udaje).
         * Wykonaj go w try/catch i wypisz dowod (np. przez wlasne flagi/echo w krok1 i
         * krok2), ze krok1 wykonal sie w pelni, krok2 zawiodl, a WLASNE taski glownego
         * targetu NIGDY sie nie wykonaly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_UnwritableDestdirMessage {
        /*
         * 🧪 Zadanie 23:
         * Sprobuj &lt;javac destdir="..."&gt; wskazac katalog, ktorego Ant NIE bedzie w
         * stanie utworzyc/zapisac (np. sciezka pod nieistniejacym dyskiem/urzadzeniem albo
         * pod plikiem uzytym jako katalog nadrzedny). Zlap i wypisz komunikat, porownujac
         * go z komunikatem "srcdir does not exist" z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DebugLevelExtraContextOnFailure {
        /*
         * 🧪 Zadanie 24:
         * Wykonaj TEN SAM, celowo zepsuty target (np. fail-bad-srcdir z lekcji) raz na
         * MSG_INFO i raz na MSG_DEBUG. Porownaj (wypisz obserwacje), czy i o ile wiecej
         * kontekstu przed samym bledem dostarcza poziom debug.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_EnvironmentSnapshotWithoutAnt {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode printEnvironmentSnapshot() (BEZ uzycia Anta wogole), ktora zbiera i
         * wypisuje: java.version, java.home, os.name, os.arch, user.dir, domyslny Charset
         * (Charset.defaultCharset()) i liczbe dostepnych procesorow
         * (Runtime.getRuntime().availableProcessors()) - reczny mini-odpowiednik czesci
         * JVM-owej z "ant -diagnostics".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MacrodefWrongAttributeName {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj macrodef oczekujacy atrybutu "who", ale wywolaj go, podajac przez
         * pomylke atrybut "whom". Zlap i wypisz prawdziwy komunikat BuildException o
         * nierozpoznanym atrybucie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SilentNoOpConditionTypo {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj &lt;condition property="isWindows"&gt;...&lt;/condition&gt;, ale w
         * targecie sprawdzajacym uzyj if="is.windows" (literowka - inna nazwa property).
         * Wykonaj go i wypisz dowod, ze target SIE WYKONAL bez zadnego bledu, ale jego
         * wlasne taski zostaly po cichu pominiete - wyjasnij, czym to jest bardziej
         * niebezpieczne niz glosny BuildException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DiffStyleTwoBuildFiles {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj DWA prawie identyczne build.xml, rozniace sie JEDNA wartoscia domyslna
         * property. Wykonaj TEN SAM target na obu i wypisz porownanie ich wynikow w stylu
         * "diff" (linia po linii, oznaczajac roznice), symulujac debugowanie roznicy
         * miedzy dwoma srodowiskami/branchami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SafeRunnerWithPassFailSummary {
        /*
         * 🧪 Zadanie 29:
         * Napisz metode boolean runTargetSafely(Project project, String target), ktora
         * NIGDY nie przepuszcza BuildException dalej (zawsze go loguje i zwraca false przy
         * bledzie, true przy sukcesie). Uzyj jej dla WSZYSTKICH 3 celowo zepsutych targetow
         * z teorii lekcji plus 2 WLASNYCH nowych, wypisujac koncowa tabelke PASS/FAIL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FiveMistakesReportCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj JEDEN build.xml z 5 targetami, kazdy reprezentujacy INNY klasyczny blad z
         * lekcji (brakujaca property, zla sciezka srcdir, zerwany depends, zalezność od
         * samego siebie, nieznany task). Napisz "test runner" w Javie, ktory wykonuje
         * WSZYSTKIE piec w try/catch, zbiera kazdy komunikat BuildException i wypisuje
         * koncowy raport tytulowany "RAPORT BLEDOW BUDOWANIA" z jedna linia na blad.
         */
        public static void main(String[] args) { }
    }
}
