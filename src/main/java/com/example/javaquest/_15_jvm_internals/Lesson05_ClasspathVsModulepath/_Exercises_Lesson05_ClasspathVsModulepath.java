package com.example.javaquest._15_jvm_internals.Lesson05_ClasspathVsModulepath;

public class _Exercises_Lesson05_ClasspathVsModulepath {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_ExplainUnnamedModuleInComment {
        /*
         * 🧪 Zadanie 1:
         * W komentarzu wyjaśnij, czym jest "modul nienazwany" (unnamed
         * module) i dlaczego cały ten kurs javaQuest w nim żyje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_PrintClassModuleInfo {
        /*
         * 🧪 Zadanie 2:
         * Wypisz getClass().getModule() dla tej klasy oraz dla
         * String.class - porównaj oba obiekty Module (np. isNamed()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_ExplainSplitPackagesOnClasspath {
        /*
         * 🧪 Zadanie 3:
         * W komentarzu wyjaśnij, czym są "split packages" i dlaczego są
         * możliwe (choć ryzykowne) na classpath.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_CompileSingleClassWithJavacNoModule {
        /*
         * 🧪 Zadanie 4:
         * Skompiluj (przez ProcessBuilder + timeout) prostą klasę bez
         * module-info.java, standardowo do katalogu tymczasowego, i
         * uruchom ją komendą "java -cp".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_ExplainExportsVsOpensDifference {
        /*
         * 🧪 Zadanie 5:
         * W komentarzu wyjaśnij różnicę między "exports" a "opens" w
         * module-info.java z perspektywy REFLEKSJI (który z nich pozwala
         * na setAccessible(true) na prywatnych polach/metodach?).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_ListModulesOfBootLayer {
        /*
         * 🧪 Zadanie 6:
         * Użyj ModuleLayer.boot().modules() aby wypisać nazwy kilku
         * (np. pierwszych 10) modułów załadowanych w warstwie startowej
         * JVM (będą to głównie moduły java.* jeśli projekt działa na
         * classpath).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_CheckIfCurrentModuleIsNamed {
        /*
         * 🧪 Zadanie 7:
         * Sprawdź this.getClass().getModule().isNamed() dla tej klasy
         * (uruchomionej normalnie, na classpath tego projektu) i wypisz
         * czytelny komunikat tłumaczący wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ExplainWhyModulePathNeedsModuleInfo {
        /*
         * 🧪 Zadanie 8:
         * W komentarzu wyjaśnij, dlaczego --module-path wymaga obecności
         * pliku module-info.java (lub .class) w korzeniu każdego modułu,
         * podczas gdy -cp/-classpath tego nie wymaga.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ListDifferencesClasspathVsModulepathTable {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj (na sztywno w kodzie) tabelkę porównawczą classpath vs
         * module path z kolumnami: silna enkapsulacja, split packages,
         * wykrywanie brakującej zależności, wymóg module-info. Wypisz ją
         * sformatowaną w konsoli.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_RunProcessHelperSmokeTest {
        /*
         * 🧪 Zadanie 10:
         * Napisz własną, uproszczoną wersję metody runProcess(...) (jak w
         * teorii tej lekcji) i przetestuj ją, uruchamiając "java -version"
         * jako podproces z timeoutem 10 sekund.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_BuildTwoPackagesOnClasspathAndAccessViaReflection {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj (w katalogu tymczasowym) dwie klasy w różnych pakietach,
         * bez module-info.java. Skompiluj obie na classpath i użyj
         * refleksji (Class.forName + getMethod + invoke) z jednej, aby
         * wywołać publiczną metodę drugiej. Wypisz wynik - powinien się
         * udać bez żadnych ograniczeń.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_BuildTwoModulesWhereSecondRequiresFirst {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj dwa moduły JPMS (module-info.java w każdym): moduł A z
         * eksportowanym pakietem i klasą, moduł B z "requires A". Skompiluj
         * przez javac --module-source-path i uruchom przez
         * java --module-path - potwierdź działanie (kod wyjścia 0).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_DemonstrateNonExportedPackageCompileError {
        /*
         * 🧪 Zadanie 13:
         * Zmodyfikuj przykład z zadania 12 tak, aby moduł B próbował
         * bezpośrednio zaimportować klasę z NIEeksportowanego pakietu
         * modułu A (statyczny import, nie refleksja). Skompiluj i wypisz
         * realny błąd kompilacji ("package X is not visible").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_DemonstrateReflectiveAccessFailureAcrossModules {
        /*
         * 🧪 Zadanie 14:
         * Odtwórz demo z teorii tej lekcji (dwa moduły, jeden nie
         * eksportuje swojego pakietu) i wywołaj refleksyjnie publiczną
         * metodę klasy z modułu, który jej nie eksportuje - wypisz REALNY
         * wyjątek uzyskany z outputu podprocesu (IllegalAccessException /
         * podobny komunikat).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_FixReflectiveAccessFailureWithOpens {
        /*
         * 🧪 Zadanie 15:
         * Zmodyfikuj demo z zadania 14 tak, aby moduł używał "opens X;"
         * zamiast braku eksportu (albo "exports X;" jeśli wystarczy do
         * zwykłej refleksji bez setAccessible). Skompiluj i uruchom
         * ponownie - potwierdź, że refleksja TERAZ się udaje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_CompareExitCodesOfBothVariants {
        /*
         * 🧪 Zadanie 16:
         * Uruchom oba warianty (classpath i module path, z pakietem nie
         * eksportowanym) jeden po drugim i zbierz oba kody wyjścia do
         * tablicy int[]. Wypisz porównanie w formacie tabelki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_BuildOpensToSpecificModuleOnly {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj trzy moduły: A (z pakietem "opens pakiet to B;" - otwarty
         * TYLKO dla modułu B, nie ogólnie), B (requires A, może
         * refleksyjnie sięgnąć do pakietu A) i C (requires A, próbuje tego
         * samego). Zweryfikuj, że B się udaje, a C dostaje błąd dostępu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_DemonstrateSplitPackageErrorOnModulePath {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj dwa moduły, które OBA próbują zdefiniować pakiet o tej
         * samej nazwie (split package) i połącz je razem na jednym
         * module path. Uruchom "java --module-path ... --module ..." i
         * wypisz REALNY błąd JVM zgłoszony przy starcie (nie w trakcie
         * działania).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_MeasureCompileTimeErrorDetectionSpeedDifference {
        /*
         * 🧪 Zadanie 19:
         * Porównaj (w komentarzu, na podstawie tego, co zaobserwowałeś w
         * tej lekcji) moment wykrycia błędu braku widoczności pakietu:
         * classpath (błąd dopiero w runtime przy refleksji) vs module path
         * z bezpośrednim importem (błąd już przy kompilacji). Wypisz
         * wniosek jako komentarz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_BuildAutomaticModuleFromPlainJar {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj zwykły JAR (bez module-info.java, jak w Lekcji 2 -
         * użyj JarOutputStream) z jedną klasą, umieść go na
         * --module-path (nie -cp!) razem z modułem wymagającym go po
         * nazwie automatycznej (wyprowadzonej z nazwy pliku JAR). Wypisz,
         * czy kompilacja/uruchomienie się powiodło.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_BuildDiagnosticToolComparingBothModelsAutomatically {
        /*
         * 🧪 Zadanie 21:
         * Napisz narzędzie, które automatycznie: (1) generuje kod dwóch
         * klas (jak w teorii tej lekcji), (2) kompiluje i uruchamia je NA
         * CLASSPATH, (3) kompiluje i uruchamia je jako DWA MODUŁY na
         * MODULE PATH, (4) wypisuje raport porównawczy z kodami wyjścia i
         * skróconymi komunikatami błędów obu wariantów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ParametrizeDemoWithConfigurableVisibility {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz narzędzie z zadania 21 o parametr (enum lub String):
         * NONE / EXPORTS / OPENS, kontrolujący treść dyrektywy w
         * module-info.java modułu udostępniającego pakiet. Przetestuj
         * wszystkie 3 warianty i wypisz, dla których refleksja się udaje.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_CompareDeepReflectionSetAccessibleAcrossModules {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj wariant demo, w którym metoda docelowa jest PRYWATNA
         * (wymaga setAccessible(true)). Sprawdź zachowanie na module path
         * dla modułu z "exports" (bez "opens") vs z "opens" - wypisz, w
         * którym przypadku setAccessible(true) rzuca wyjątek.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_BuildThreeModuleChainWithTransitiveRequires {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj 3 moduły A -> B -> C, gdzie B ma "requires transitive A"
         * a C ma "requires B" (bez bezpośredniego "requires A"). Sprawdź
         * (przez próbę kompilacji kodu w C używającego typu z A), czy C
         * "widzi" A dzięki transitive - wypisz wynik kompilacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_SimulateMigrationFromClasspathToModulepath {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj migrację: zacznij od 2 klas na classpath (działają),
         * dodaj module-info.java bez żadnych "exports" (zamknięty modul) i
         * pokaż, że przestaje działać dokładnie ten sam kod refleksyjny,
         * mimo że NIE zmieniłeś ani linijki logiki biznesowej - tylko
         * dodałeś plik module-info.java.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_BuildReportOfAccessErrorMessagesAcrossScenarios {
        /*
         * 🧪 Zadanie 26:
         * Zbierz (uruchamiając kilka wariantów demo) rzeczywiste komunikaty
         * błędów runtime dla: braku exports, obecności tylko exports (bez
         * opens) przy próbie setAccessible, oraz split package przy
         * starcie. Wypisz je razem jako "katalog błędów JPMS" z krótkim
         * wyjaśnieniem każdego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_ExplainWhyClasspathStillDominatesInLegacyProjects {
        /*
         * 🧪 Zadanie 27:
         * W komentarzu (min. 4 zdania) wyjaśnij, dlaczego wiele dużych,
         * istniejących projektów (w tym ten kurs, patrz notatka w
         * `_14_advancedjava/Lesson28`) pozostaje na classpath zamiast
         * migrować w pełni na module path, mimo korzyści silnej
         * enkapsulacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_BuildHybridSetupWithAutomaticModuleDependency {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj moduł "app" (z module-info.java, requires na automatyczny
         * moduł), który zależy od zwykłego JAR-a bez module-info (jak w
         * zadaniu 20). Skompiluj i uruchom całość na module path, wypisz
         * nazwę automatycznego modułu wyprowadzoną z nazwy pliku JAR.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_CompareStartupTimeClasspathVsModulepath {
        /*
         * 🧪 Zadanie 29:
         * Zmierz (System.nanoTime() wokół runProcess) czas uruchomienia
         * prostego programu "Hello World" na classpath oraz jako
         * pojedynczy moduł na module path - powtórz pomiar kilka razy i
         * wypisz uśrednione czasy oraz swój wniosek (różnice mogą być
         * niewielkie/statystycznie nieistotne na tak prostym przykładzie).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullClasspathVsModulepathAuditTool {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne narzędzie audytujące: dla podanego (na sztywno
         * w kodzie) prostego projektu z dwiema klasami w różnych pakietach
         * generuje i uruchamia WSZYSTKIE warianty: (1) classpath, (2)
         * moduł zamknięty (brak exports), (3) moduł z exports, (4) moduł z
         * opens. Wypisz końcowy raport - tabelkę: wariant / kod wyjścia /
         * czy refleksja się udała / skrócony komunikat błędu (jeśli był).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
