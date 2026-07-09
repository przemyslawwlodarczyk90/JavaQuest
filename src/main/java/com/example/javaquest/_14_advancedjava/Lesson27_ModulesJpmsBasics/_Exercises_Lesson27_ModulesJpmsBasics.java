package com.example.javaquest._14_advancedjava.Lesson27_ModulesJpmsBasics;

public class _Exercises_Lesson27_ModulesJpmsBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreeProblemsJpmsSolves {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz w komentarzu 3 problemy "plaskiego" classpath sprzed
         * Javy 9 wymienione w lekcji (slaba enkapsulacja, niepewna konfiguracja,
         * monolityczny JDK), kazdy z jednozdaniowym wyjasnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteMinimalModuleInfoText {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: w komentarzu napisz tresc minimalnego pliku module-info.java
         * dla modulu o nazwie "com.example.orders", ktory eksportuje pakiet
         * "com.example.orders.api" i wymaga modulu "com.example.customers".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainRequiresVsExports {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: w komentarzu wyjasnij roznice miedzy "requires" a "exports"
         * (min. 2 zdania kazde) - kto od kogo zalezy, a co jest udostepniane komu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrintJavaHomeAndVersion {
        /*
         * 🧪 Zadanie 4:
         * Wypisz na konsoli System.getProperty("java.home") oraz
         * System.getProperty("java.version") - to sciezki/wersja, ktorych uzyjesz do
         * budowania sciezek do javac/java w kolejnych zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainUnnamedModule {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), czym jest "modul
         * nienazwany" i dlaczego caly ten kurs (javaQuest) w nim dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainAutomaticModules {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu wyjasnij, czym jest "modul automatyczny", skad
         * bierze sie jego nazwa (2 mozliwe zrodla) i jaka ma widocznosc pakietow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GenerateOneModuleSourceOnDisk {
        /*
         * 🧪 Zadanie 7:
         * W katalogu tymczasowym (Files.createTempDirectory) wygeneruj (Files.writeString)
         * JEDEN modul "hello.world" z module-info.java (bez zadnych requires/exports) i
         * jedna klasa Main w pakiecie "hello" z metoda main wypisujaca powitanie.
         * Wypisz sciezke utworzonego katalogu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BuildJavacCommandArray {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj (jako String[] albo List<String>) pelna komende javac z opcjami
         * --module-source-path, -d i --module dla modulu z Zadania 7 (NIE musisz jej
         * jeszcze uruchamiac) - wypisz ja jako pojedynczy String (String.join(" ", ...)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompileSingleModule {
        /*
         * 🧪 Zadanie 9:
         * Skompiluj modul z Zadania 7 PRAWDZIWA komenda javac (ProcessBuilder), wypisz
         * kod wyjscia (oczekiwane 0) oraz wypisz Files.exists(...) dla utworzonego
         * pliku module-info.class w katalogu wyjsciowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RunSingleModule {
        /*
         * 🧪 Zadanie 10:
         * Uruchom skompilowany modul z Zadania 9 PRAWDZIWA komenda java --module-path
         * --module (ProcessBuilder), przechwyc i wypisz jego output oraz kod wyjscia.
         * Posprzataj katalog tymczasowy na koniec.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoModulesWithDependency {
        /*
         * 🧪 Zadanie 11:
         * Wygeneruj DWA moduly: "shapes.api" (eksportuje pakiet "shapes" z interfejsem
         * Shape o metodzie double area()) i "shapes.app" (requires shapes.api, klasa
         * Circle implementujaca Shape + Main uzywajacy jej). Skompiluj i uruchom przez
         * ProcessBuilder (javac --module-source-path, java --module-path), wypisz
         * rzeczywisty output.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TriggerNonExportedPackageError {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz modul "shapes.api" z Zadania 11 o pakiet "shapes.internal" (NIE
         * eksportowany) z jakas klasa pomocnicza. Zmien Main w "shapes.app" tak, aby
         * importowal klase z tego pakietu, skompiluj i przechwyc/wypisz PRAWDZIWY blad
         * kompilacji ("package ... is not visible").
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MissingRequiresError {
        /*
         * 🧪 Zadanie 13:
         * Usun linie "requires shapes.api;" z module-info.java modulu "shapes.app" (ale
         * zostaw import Shape/Circle w Main.java). Skompiluj i przechwyc/wypisz
         * PRAWDZIWY blad kompilacji zwiazany z brakujacym "requires".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MissingModuleAtRuntimeError {
        /*
         * 🧪 Zadanie 14:
         * Skompiluj poprawnie 2 moduly (jak w Zadaniu 11), ale przy URUCHOMIENIU podaj
         * --module-path wskazujacy TYLKO na katalog z modulem "shapes.app" (bez
         * "shapes.api"). Przechwyc i wypisz PRAWDZIWY blad runtime (brakujacy modul w
         * grafie modulow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ThreeModuleChain {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj LANCUCH 3 modulow: A (bez zaleznosci, eksportuje pakiet), B (requires
         * A, eksportuje wlasny pakiet uzywajacy typow z A), C (requires B, uzywa typow z
         * B). Skompiluj wszystkie trzy JEDNA komenda javac --module-source-path
         * --module A,B,C i uruchom modul C.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareCompileTimeVsCommandLine {
        /*
         * 🧪 Zadanie 16:
         * Zmierz czas (System.nanoTime()) kompilacji tych samych 2 modulow (Zadanie 11)
         * DWA razy pod rzad (drugi raz do innego katalogu wyjsciowego) i wypisz oba
         * czasy - to pokazuje, ile realnie trwa "prawdziwa" kompilacja modulowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ListModuleDescriptorViaJavaCommand {
        /*
         * 🧪 Zadanie 17:
         * Po skompilowaniu modulu (np. z Zadania 7 albo 11), uruchom PRAWDZIWA komende
         * "java --describe-module" (badz "java --list-modules" jesli --describe-module
         * wymaga dodatkowych opcji) na skompilowanym module i wypisz przechwycony
         * output opisujacy jego deskryptor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GenerateModuleInfoProgrammatically {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode generateModuleInfo(String moduleName, List<String> requiresModules,
         * List<String> exportsPackages), ktora sklada i zwraca poprawny tekst
         * module-info.java jako String (uzywajac StringBuilder/text blocks). Przetestuj
         * ja dla 2 roznych zestawow parametrow i wypisz wygenerowany tekst.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CleanupAfterEachScenario {
        /*
         * 🧪 Zadanie 19:
         * Powtorz demo z Zadania 11 (2 moduly, kompilacja + uruchomienie), ale caly
         * kod opakuj w try/finally z rekurencyjnym usunieciem katalogu tymczasowego
         * (Files.walk + sorted(Comparator.reverseOrder())). Potwierdz Files.exists ==
         * false po sprzataniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeCommandDifferencesFromClasspath {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz w komentarzu tabelke porownawcza (min. 3 wiersze)
         * "classpath" vs "module path" - jakiej opcji uzywa sie do kompilacji/uruchomienia
         * w kazdym trybie (np. -cp vs --module-path) i jaka jest kluczowa roznica w
         * widocznosci klas.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DiamondDependencyGraph {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj graf modulow w ksztalcie rombu: D requires B i C, B requires A, C
         * requires A (A na dole, D na gorze). Skompiluj wszystkie 4 JEDNA komenda i
         * uruchom modul D, ktory korzysta z typu wystawionego przez A (przez B i C).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RequiresTransitiveDemo {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj 3 moduly: A (eksportuje typ Foo), B ("requires transitive A" +
         * eksportuje wlasny typ uzywajacy Foo w sygnaturze publicznej metody), C
         * (requires TYLKO B, bez A). Sprawdz i wypisz, czy C moze uzyc typu Foo z A
         * (dzieki "transitive" w B) bez wlasnego "requires A" - zweryfikuj kompilacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CircularDependencyCompileError {
        /*
         * 🧪 Zadanie 23:
         * Sprobuj skompilowac 2 moduly, gdzie A "requires B" ORAZ B "requires A"
         * (cykl). Przechwyc i wypisz PRAWDZIWY blad kompilacji zwiazany z cyklem w
         * grafie modulow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DuplicatePackageAcrossModulesError {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj 2 NIEZALEZNE moduly, ktore oba definiuja klase w TYM SAMYM pakiecie
         * (np. "shared"). Sprobuj uruchomic program z --module-path wskazujacym na OBA
         * naraz i przechwyc/wypisz PRAWDZIWY blad ("pakiet w wielu modulach" -
         * split package).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ModulePathVsClasspathMixedRun {
        /*
         * 🧪 Zadanie 25:
         * Skompiluj modul "shapes.api"/"shapes.app" (jak w Zadaniu 11) NORMALNIE, ale
         * uruchom go, podajac katalog wyjsciowy PRZEZ -cp (classpath) zamiast
         * --module-path. Wypisz i skomentuj (w komentarzu w kodzie) roznice w
         * zachowaniu/bledzie w porownaniu do uruchomienia przez --module-path.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ProgrammaticModuleGraphInspection {
        /*
         * 🧪 Zadanie 26:
         * Po skompilowaniu i URUCHOMIENIU modulu (dowolny scenariusz z tej lekcji),
         * uzyj w URUCHAMIANEJ klasie API java.lang.Module (getClass().getModule(),
         * isNamed(), getName()) i wypisz te informacje jako CZESC outputu procesu
         * potomnego - potwierdzajac, ze klasa faktycznie dziala w NAZWANYM module.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigrateManualClasspathProjectToModules {
        /*
         * 🧪 Zadanie 27:
         * Wez dwuklasowy projekt classpathowy podobny do tego z rozdzialu
         * _11_buildtools (Helper + Main, kompilacja przez -cp) i "zmigruj" go: dodaj
         * module-info.java, przenies klasy do wlasciwej struktury katalogow modulu,
         * skompiluj i uruchom NOWA wersja modulowa - porownaj obie (classpath vs modul)
         * na tym samym przykladzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultiModuleBuildTimingReport {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj graf 5 modulow o roznej glebokosci zaleznosci, skompiluj je JEDNA
         * komenda, zmierz czas kompilacji i URUCHOMIENIA modulu koncowego, a na koniec
         * wypisz raport: liczba modulow, calkowity czas kompilacji, calkowity czas
         * uruchomienia, lista nazw modulow w kolejnosci zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareModuleInfoVariantsErrorMatrix {
        /*
         * 🧪 Zadanie 29:
         * Przygotuj TRZY warianty tego samego 2-modulowego projektu: (a) poprawny, (b)
         * brak wymaganego exports, (c) brak wymaganego requires. Skompiluj kazdy
         * wariant OSOBNO, zbierz wyniki (sukces/blad + komunikat) do jednej struktury i
         * wypisz macierz porownawcza "wariant -> wynik".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullJpmsBasicsCapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Wykonaj PELNY scenariusz: zbuduj 3-modulowy projekt (lancuch zaleznosci),
         * skompiluj go, uruchom przypadek POPRAWNY (pelny sukces) oraz DWA przypadki
         * BLEDNE (nie-eksportowany pakiet, brakujacy requires) - zbierz wszystkie
         * wyniki i wypisz koncowy raport "=== RAPORT PODSTAW JPMS ===" z liczba
         * scenariuszy, iloscia sukcesow/bledow oraz krotkim wnioskiem koncowym
         * (odniesienie do tresci lekcji: po co jest silna enkapsulacja i niezawodna
         * konfiguracja). Posprzataj wszystkie katalogi tymczasowe w finally.
         */
        public static void main(String[] args) { }
    }
}
