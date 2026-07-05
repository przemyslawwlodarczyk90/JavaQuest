package com.example.javaquest._11_buildtools.Lesson01_WhyBuildTools;

public class _Exercises_Lesson01_WhyBuildTools {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ManualTerminalBuild {
        /*
         * 🧪 Zadanie 1:
         * (Zadanie terminalowe, poza JVM demo.) Stworz na dysku prosty, jednoklasowy
         * projekt Java (plik Hello.java z metoda main wypisujaca "Hello, build!") BEZ
         * zadnego IDE. W terminalu wykonaj recznie: "javac Hello.java", a potem
         * "java Hello". Zapisz w komentarzu, jakie dwie komendy wpisales i jaki byl wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BuildStepsList {
        /*
         * 🧪 Zadanie 2:
         * Wypisz na konsoli (System.out.println) 6 etapow typowego builda wymienionych
         * w lekcji (kompilacja, kopiowanie zasobow, testy, pakowanie, raporty,
         * deployment) - kazdy w jednej linii z jednozdaniowym opisem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WhyMultiClassIsHard {
        /*
         * 🧪 Zadanie 3:
         * Wypisz na konsoli 3 powody (z lekcji), dlaczego reczne budowanie projektu
         * z wieloma klasami jest trudne (wiele folderow, biblioteki zewnetrzne, testy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_EnvironmentsComparisonPrint {
        /*
         * 🧪 Zadanie 4:
         * Wypisz tabelke porownawcza (3 linie println) srodowisk dev/test/prod - dla
         * kazdego wypisz jedna przykladowa roznice w konfiguracji (np. adres bazy danych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BuildToolExamplesList {
        /*
         * 🧪 Zadanie 5:
         * Wypisz na konsoli nazwy 3 build toolow poznawanych w tym rozdziale (Ant,
         * Maven, Gradle) razem z jednozdaniowym opisem kazdego (np. "Ant - oparty na XML,
         * imperatywny").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CountManualCommandsForNClasses {
        /*
         * 🧪 Zadanie 6:
         * Napisz metode obliczajaca (bez faktycznej kompilacji), ile MINIMUM recznych
         * komend javac potrzeba, aby skompilowac projekt o N niezaleznych klasach (kazda
         * osobno) plus 1 komende java do uruchomienia. Wypisz wynik dla N = 3, 10, 100.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ManualStepsAtomicList {
        /*
         * 🧪 Zadanie 7:
         * Stworz liste (List<String>) z 4 krokami recznego builda z lekcji (kompiluj
         * MathHelper, kompiluj TextHelper, kompiluj Main, uruchom Main) i wypisz ja w
         * formie numerowanej listy (1. ..., 2. ..., itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_JdkVersionPrint {
        /*
         * 🧪 Zadanie 8:
         * Wypisz na konsoli aktualna wersje JDK, na ktorej dziala ten program, korzystajac
         * z System.getProperty("java.version") oraz System.getProperty("java.home").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_HumanErrorScenarios {
        /*
         * 🧪 Zadanie 9:
         * Wypisz 3 przykladowe bledy ludzkie, ktore moga sie zdarzyc przy recznym
         * budowaniu (zapomniany plik, zla kolejnosc kompilacji, zla wersja biblioteki na
         * classpath) - kazdy w osobnej linii z krotkim wyjasnieniem konsekwencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_OneCommandVsManyPrint {
        /*
         * 🧪 Zadanie 10:
         * Wypisz porownanie "1 komenda build toola" vs "N recznych komend javac/java" w
         * formie dwoch linii, gdzie N to wartosc podana jako zmienna int (ustaw ja np. na 12).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_GenerateAndCompileTwoFiles {
        /*
         * 🧪 Zadanie 11:
         * Wygeneruj (Files.writeString) 2 pliki .java w katalogu tymczasowym: klase
         * pomocnicza (np. "Greeter" z metoda statyczna greet(String name)) i klase "App"
         * korzystajaca z niej. Skompiluj obie recznie przez ProcessBuilder("javac", ...) w
         * poprawnej kolejnosci (Greeter przed App) i wypisz kod wyjscia kazdej komendy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WrongOrderCompilationFailure {
        /*
         * 🧪 Zadanie 12:
         * Powtorz sytuacje z Zadania 11, ale tym razem skompiluj klase "App" PRZED
         * skompilowaniem klasy "Greeter" (odwrotna kolejnosc). Przechwyc i wypisz output
         * bledu kompilacji ("cannot find symbol") jako dowod, ze kolejnosc ma znaczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RunGeneratedProgramAndCaptureOutput {
        /*
         * 🧪 Zadanie 13:
         * Wygeneruj i skompiluj prosta klase Main z metoda main wypisujaca 3 linie tekstu.
         * Uruchom ja przez ProcessBuilder("java", "-cp", ..., "Main"), przechwyc caly output
         * procesu (InputStream) i wypisz go z prefiksem "[Main] " przed kazda linia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasureManualBuildTime {
        /*
         * 🧪 Zadanie 14:
         * Powtorz reczny build 3 plikow z lekcji (MathHelper, TextHelper, Main), ale
         * zmierz System.nanoTime() przed i po WSZYSTKICH 4 komendach i wypisz calkowity
         * czas trwania recznego builda w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MissingFileScenario {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj "zapomniany plik": wygeneruj 3 klasy powiazane ze soba (jak w lekcji),
         * ale skompiluj i sprobuj uruchomic program POMIJAJAC kompilacje jednej z klas
         * pomocniczych. Przechwyc i wypisz blad ("cannot find symbol" przy kompilacji albo
         * NoClassDefFoundError przy uruchomieniu klasy, ktora jej uzywa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CountStepsForGrowingProject {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode estimateManualSteps(int classCount, int testCount) zwracajaca
         * szacowana liczbe recznych krokow (kompilacja kazdej klasy + kazdego testu +
         * 1 krok pakowania + 1 krok uruchomienia). Wypisz wynik dla kilku przykladowych
         * rozmiarow projektu (male: 5 klas/2 testy, srednie: 50/20, duze: 500/200).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ResourceCopySimulation {
        /*
         * 🧪 Zadanie 17:
         * Zasymuluj krok "kopiowanie zasobow": stworz plik config.properties w jednym
         * katalogu i recznie (Files.copy) skopiuj go do katalogu "wynikowego" razem z
         * uprzednio skompilowanymi klasami, tak jakby to mial zrobic build tool. Wypisz,
         * ze plik faktycznie istnieje w katalogu docelowym po skopiowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DifferentEnvironmentConfigs {
        /*
         * 🧪 Zadanie 18:
         * Wygeneruj 2 pliki: application-dev.properties (z wartoscia "db.url=localhost")
         * i application-prod.properties (z wartoscia "db.url=prod-server"). Napisz metode
         * loadConfigFor(String env), ktora wczytuje odpowiedni plik i wypisuje jego
         * zawartosc - symulujac problem "roznych srodowisk" z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompileWithExternalLibraryMissing {
        /*
         * 🧪 Zadanie 19:
         * Wygeneruj klase, ktora w kodzie odwoluje sie do nieistniejacej klasy z "biblioteki
         * zewnetrznej" (np. "com.acme.SomeLibraryClass", ktorej nigdy nie skompilowalismy).
         * Sprobuj ja skompilowac recznie i przechwyc/wypisz blad kompilacji jako dowod, ze
         * bez odpowiedniej biblioteki na classpath kompilacja nie przejdzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullManualPipelineReport {
        /*
         * 🧪 Zadanie 20:
         * Wykonaj reczny build i uruchomienie 4-klasowego mini-projektu (3 klasy pomocnicze
         * + 1 Main), licz KAZDY reczny krok (kompilacja kazdej klasy + uruchomienie) i na
         * koniec wypisz raport: "=== RAPORT RECZNEGO BUILDA ===" z liczba krokow, calkowitym
         * czasem i lista wszystkich wykonanych komend.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericManualBuildRunner {
        /*
         * 🧪 Zadanie 21:
         * Napisz generyczna metode runManualBuild(List<Path> sourceFilesInOrder, Path
         * outputDir), ktora kompiluje podane pliki .java w podanej kolejnosci (osobne
         * wywolania javac, klasyczny wzorzec z lekcji) i zwraca liczbe wykonanych krokow.
         * Przetestuj ja na projekcie o 5 wygenerowanych plikach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DependencyOrderValidator {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode, ktora przed kompilacja sprawdza (na podstawie prostej mapy
         * "klasa -> lista zaleznosci") czy podana kolejnosc kompilacji plikow jest
         * poprawna (zaleznosci kompilowane przed klasami, ktore ich uzywaja). Przetestuj
         * ja na poprawnej i niepoprawnej kolejnosci dla 4 klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ManualVsAutomatedTimeComparison {
        /*
         * 🧪 Zadanie 23:
         * Zmierz i wypisz czas recznego builda (osobne komendy javac dla N=5 plikow +
         * 1 java) w porownaniu do czasu skompilowania TYCH SAMYCH plikow JEDNA komenda
         * javac przyjmujaca wszystkie pliki na raz (np. "javac *.java" odpowiednik przez
         * podanie wielu argumentow). Wypisz oba czasy i roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulatedCiPipeline {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj mini-pipeline CI/CD jako sekwencje etapow (metody: compileStep(),
         * testStep(), packageStep(), deployStep() - kazda tylko wypisuje swoja nazwe i
         * "OK"), wywolywanych w petli z listy nazw etapow. Jesli ktorykolwiek etap "zawiedzie"
         * (zasymuluj to flagiem boolean), pipeline powinien sie zatrzymac i wypisac ktory
         * etap zawiodl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ClasspathGrowthSimulation {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj liste symulujaca rosnacy classpath projektu (dodawaj po jednej "bibliotece"
         * - Stringi jak "lib1.jar", "lib2.jar" - w petli do 10 elementow) i po kazdym
         * dodaniu wypisz aktualny, poprawnie sformatowany classpath (elementy rozdzielone
         * separatorem File.pathSeparator).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildFailureRecoveryDemo {
        /*
         * 🧪 Zadanie 26:
         * Wygeneruj projekt 3-klasowy, w ktorym JEDNA klasa ma celowy blad skladniowy.
         * Sprobuj ja skompilowac recznie, przechwyc blad, "napraw" plik (nadpisz go
         * poprawna wersja przez Files.writeString) i skompiluj ponownie - wypisz, ze druga
         * kompilacja zakonczyla sie sukcesem (kod wyjscia 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiModuleManualBuildPain {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj projekt z 2 "modulami" (2 osobne podkatalogi ze zroznymi plikami .java,
         * gdzie modul B zalezy od modulu A). Skompiluj recznie modul A, potem modul B z
         * classpath wskazujacym na skompilowany modul A, a na koniec uruchom klase z modulu
         * B - licz i wypisz calkowita liczbe recznych krokow potrzebnych do zbudowania
         * calosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ManualBuildScriptGenerator {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode generateBuildScriptText(List<String> javacCommands, String
         * javaCommand), ktora sklada wszystkie podane komendy w jeden tekst przypominajacy
         * skrypt .bat/.sh (kazda komenda w nowej linii). Wypisz wygenerowany "skrypt" i
         * skomentuj, ze to wlasnie taki "skrypt" zastapi w kolejnych lekcjach build.xml Anta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ErrorTaxonomyFromManualBuild {
        /*
         * 🧪 Zadanie 29:
         * Wykonaj 3 rozne scenariusze recznego builda, ktore koncza sie bledem (zly plik
         * wejsciowy, zla kolejnosc kompilacji, brak biblioteki na classpath) i zbierz
         * wszystkie komunikaty bledow do jednej listy. Wypisz podsumowanie: "typ bledu ->
         * przykladowy komunikat" dla kazdego z 3 scenariuszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WhyBuildToolsCapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Wykonaj PELNY reczny build i uruchomienie 5-klasowego projektu (4 klasy pomocnicze
         * o zaleznosciach lancuchowych + 1 Main), licz kazdy krok i czas, a na koniec wypisz
         * rozbudowany raport koncowy: liczba plikow, liczba recznych komend, calkowity czas,
         * lista wszystkich komend oraz wniosek koncowy - jedno zdanie uzasadniajace, czemu
         * przy takiej skali potrzebny jest build tool (odniesienie do tresci tej lekcji).
         */
        public static void main(String[] args) { }
    }
}
