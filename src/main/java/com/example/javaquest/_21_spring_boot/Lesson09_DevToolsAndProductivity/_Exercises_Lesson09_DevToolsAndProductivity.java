package com.example.javaquest._21_spring_boot.Lesson09_DevToolsAndProductivity;

public class _Exercises_Lesson09_DevToolsAndProductivity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatDevToolsAutomates {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 3 rzeczy, ktore automatyzuje
         * spring-boot-devtools.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddDevToolsToOwnScratchProject {
        /*
         * 🧪 Zadanie 2:
         * W OSOBNYM, testowym projekcie (NIE tym kursie) dodaj
         * `spring-boot-devtools` i zaobserwuj auto-restart.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainTwoClassLoaderMechanism {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij wlasnymi slowami mechanizm 2
         * classloaderow (base/restart).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhyOptionalTrueMatters {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij, po co `<optional>true</optional>`
         * przy zaleznosci DevTools.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyClassAbsenceInThisProject {
        /*
         * 🧪 Zadanie 5:
         * Odtworz sprawdzenie z teorii (`Class.forName`) dla INNEJ
         * klasy DevTools (np. `LiveReloadServer`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainLiveReloadMechanism {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, jak dziala LiveReload (przegladarka +
         * wtyczka + serwer wbudowany w DevTools).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareRestartTimeWithFullJvmRestart {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: oszacuj (na podstawie opisu mechanizmu),
         * DLACZEGO restart DevTools jest SZYBSZY niz pelny restart JVM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainTriggerFileOption {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala (dokumentacja): sprawdz `spring.devtools.restart.trigger-file` -
         * po co ograniczac restart TYLKO do zapisu KONKRETNEGO pliku?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListFilesExcludedFromRestartByDefault {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala (dokumentacja): jakie katalogi/pliki SA
         * WYLACZONE z wywolywania restartu domyslnie (np. `static/`,
         * `templates/`)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyProductionJarNeverContainsDevTools {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij mechanizm, przez ktory DevTools NIGDY
         * nie trafia do produkcyjnego jara.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ConfigureRemoteDevToolsConceptually {
        /*
         * 🧪 Zadanie 11:
         * Bez terminala (dokumentacja): sprawdz "Remote DevTools" -
         * jak dziala zdalny restart/reload dla aplikacji uruchomionej
         * NA INNYM serwerze?
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExplainDevToolsPropertyDefaultsOverride {
        /*
         * 🧪 Zadanie 12:
         * Bez terminala (dokumentacja): jakie WLASCIWOSCI DevTools
         * NADPISUJE domyslnie (np. cache szablonow) - po co?
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementCustomFileWatcherSimulation {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj (bez DevTools, WLASNORECZNIE) prosty "watcher"
         * pliku (`WatchService`, `java.nio.file`) wykrywajacy zmiane -
         * zbliz sie koncepcyjnie do tego, co robi DevTools.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareDevToolsWithJRebel {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: porownaj DevTools z komercyjnymi narzedziami
         * typu JRebel - co ROBIA WIECEJ (hot-swap metod, NIE tylko
         * restart)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainLiveReloadPortConfiguration {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala (dokumentacja): sprawdz, jak zmienic domyslny
         * port serwera LiveReload (`spring.devtools.livereload.port`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCustomRestartExcludePattern {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala (dokumentacja): jak DODAC WLASNY wzorzec
         * wykluczenia z restartu (`spring.devtools.restart.exclude`)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareDevToolsWithContainerBasedHotReload {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj DevTools z hot-reloadem w kontenerach
         * (np. bind mount + framework typu Nodemon w innych ekosystemach) -
         * podobienstwa koncepcyjne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainDevToolsRestartClassLoaderMemoryImpact {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: czy WIELOKROTNE restarty (godzina pracy) MOGA
         * powodowac WYCIEK pamieci (stare classloadery)? Jak DevTools to
         * OGRANICZA?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementDisableRestartProgrammatically {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala (dokumentacja): jak WYLACZYC auto-restart
         * PROGRAMOWO (`System.setProperty("spring.devtools.restart.enabled",
         * "false")`) mimo obecnosci DevTools na classpath?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildDevToolsFeatureChecklist {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) checkliste "co WLACZYC/skonfigurowac" przy
         * dodawaniu DevTools do NOWEGO projektu zespolowego.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomFileWatcherTriggeringRestart {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY, uproszczony mechanizm "restartu" - watcher
         * plikow + PONOWNE tworzenie ApplicationContext (nowy
         * `AnnotationConfigApplicationContext`) po wykryciu zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AnalyzeDevToolsSourceForRestartStrategy {
        /*
         * 🧪 Zadanie 22:
         * Zbadaj (dokumentacja/zrodla) DOKLADNA strategie, jaka DevTools
         * uzywa do decydowania, KTORE klasy trafiaja do "restart"
         * classloadera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareDevToolsPerformanceOverheadInProduction {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: przedyskutuj, dlaczego SLUCHANIE na zmiany
         * plikow (nawet WYLACZONE) MIALOBY sens NIE WLACZAC na produkcji
         * (bezpieczenstwo I wydajnosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomDevOnlyBeanConditionalOnDevTools {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj bean `@ConditionalOnClass` (Lesson04) obecny
         * TYLKO gdy DevTools jest na classpath - przydatne dla WLASNYCH
         * narzedzi deweloperskich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignTeamDevProductivityGuidelines {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj (opisz) wytyczne zespolu dotyczace narzedzi
         * deweloperskich (DevTools + IDE hot-swap + Docker Compose dla
         * zaleznosci) - kompletny setup lokalny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareDevToolsWithNativeImageDevelopmentMode {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: powiaz z przyszla `Lesson14_
         * BuildingExecutableJarAndNativeImage` - czy DevTools DZIALA z
         * natywnym obrazem GraalVM? Dlaczego/dlaczego nie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomHotSwapAgentSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zbadaj (koncepcyjnie) JVM HotSwap (`-XX:+AllowRedefinition`,
         * `Instrumentation.redefineClasses`) - jak to sie MA do
         * mechanizmu restart classloadera DevTools?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MeasureDeveloperProductivityGainQuantitatively {
        /*
         * 🧪 Zadanie 28:
         * Oszacuj (na podstawie WLASNYCH danych/doswiadczen) ILE czasu
         * DZIENNIE oszczedza szybszy restart przy typowej liczbie zmian
         * kodu w ciagu dnia pracy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCiSafeguardPreventingDevToolsInProdBuild {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj (opisz) krok CI/CD WERYFIKUJACY, ze finalny,
         * produkcyjny jar NIE ZAWIERA klas DevTools (dodatkowa warstwa
         * bezpieczenstwa POZA automatycznym wykluczeniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteLocalDevelopmentEnvironmentGuideCapstone {
        /*
         * 🧪 Zadanie 30:
         * Napisz (jako tekst) KOMPLETNY przewodnik "srodowisko
         * deweloperskie od zera" dla NOWEGO czlonka zespolu - DevTools +
         * profile (Lesson06) + logowanie (Lesson10) + IDE.
         */
        public static void main(String[] args) { }
    }
}
