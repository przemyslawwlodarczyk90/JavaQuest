package com.example.javaquest._11_buildtools.Lesson18_AntMavenGradleComparison;

public class _Exercises_Lesson18_AntMavenGradleComparison {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ThreePhilosophiesOneSentenceEach {
        /*
         * 🧪 Zadanie 1:
         * Wypisz na konsoli po jednym zdaniu opisujacym filozofie kazdego narzedzia:
         * Ant (imperatywny), Maven (deklaratywny/konwencja), Gradle (hybryda/DSL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ConfigFormatComparisonPrint {
        /*
         * 🧪 Zadanie 2:
         * Wypisz 3 linie: jaki format konfiguracji uzywa kazde narzedzie (build.xml -
         * XML, pom.xml - XML, build.gradle - DSL Groovy/Kotlin).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DependencyManagementComparisonPrint {
        /*
         * 🧪 Zadanie 3:
         * Wypisz 3 linie porownujace zarzadzanie zaleznosciami: Ant (brak natywnego,
         * Ivy jako dodatek), Maven (wbudowane, transytywne), Gradle (wbudowane, szybsze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WhenToUseAntList {
        /*
         * 🧪 Zadanie 4:
         * Wypisz 3 konkretne scenariusze, kiedy sensowne jest uzycie Anta (z lekcji:
         * legacy, niestandardowe procesy, stare enterprise).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WhenToUseMavenList {
        /*
         * 🧪 Zadanie 5:
         * Wypisz 3 konkretne scenariusze, kiedy sensowne jest uzycie Mavena (standardowe
         * backendy, przewidywalnosc, duzy ekosystem pluginow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WhenToUseGradleList {
        /*
         * 🧪 Zadanie 6:
         * Wypisz 3 konkretne scenariusze, kiedy sensowne jest uzycie Gradle (Android,
         * Spring Boot, custom buildy/monorepo z cache).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SameDependencyThreeWaysPrint {
        /*
         * 🧪 Zadanie 7:
         * Wypisz deklaracje zaleznosci "commons-lang3:3.14.0" w trzech wariantach:
         * Maven (XML <dependency>), Gradle (implementation '...'), i Ant (komentarz o
         * potrzebie recznego JAR-a w lib/ albo Ivy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_LearningCurveRanking {
        /*
         * 🧪 Zadanie 8:
         * Wypisz subiektywny ranking (1-3) "latwosci startu" dla poczatkujacego wsrod
         * Ant/Maven/Gradle, z jednozdaniowym uzasadnieniem kazdego miejsca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BuildSpeedFactorsList {
        /*
         * 🧪 Zadanie 9:
         * Wypisz 2 czynniki wplywajace na szybkosc builda w Gradle (build cache,
         * incremental build), ktorych Ant/Maven w tej postaci nie mialy w lekcjach 03-15.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ImperativeVsDeclarativeExplained {
        /*
         * 🧪 Zadanie 10:
         * Wypisz jedno zdanie wyjasniajace roznice miedzy podejsciem imperatywnym (Ant -
         * "jak to zrobic, krok po kroku") i deklaratywnym (Maven - "co ma powstac").
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullComparisonTableRebuild {
        /*
         * 🧪 Zadanie 11:
         * Odtworz (samodzielnie, wlasnym kodem printf/println) tabele porownawcza z
         * lekcji dla WSZYSTKICH 7 kryteriow (paradygmat, format konfiguracji, konwencja,
         * zarzadzanie zaleznosciami, szybkosc, krzywa uczenia, typowe zastosowanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SameProjectThreeConfigFiles {
        /*
         * 🧪 Zadanie 12:
         * Dla hipotetycznego malego projektu (jedna zaleznosc JUnit, packaging jar)
         * zbuduj Stringi: minimalny build.xml (Ant, target compile+jar), minimalny
         * pom.xml (Maven), minimalny build.gradle (Gradle) - wypisz wszystkie 3 i policz
         * liczbe linii kazdego (String.lines().count()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CeremonyLevelScoring {
        /*
         * 🧪 Zadanie 13:
         * Na podstawie liczby linii z Zadania 12, wypisz "wskaznik ceremonii" (liczba
         * linii / liczba faktycznych "decyzji" projektowych - policz to sam, np. 3 dla
         * kazdego: zaleznosc, packaging, wersja Javy) dla kazdego narzedzia i skomentuj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MixedCompanyScenario {
        /*
         * 🧪 Zadanie 14:
         * Napisz (jako Stringi + println) opis fikcyjnej firmy z 3 projektami: stary
         * system na Ancie (10 lat), nowy backend na Mavenie, aplikacja mobilna/Android na
         * Gradle - i uzasadnij (1 zdanie na projekt), czemu KAZDE narzedzie jest tam
         * uzasadnionym wyborem, a nie "bledem".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DependencyManagementDeepDive {
        /*
         * 🧪 Zadanie 15:
         * Wypisz szczegolowe porownanie (min. 4 punkty) zarzadzania zaleznosciami:
         * Ant+Ivy (pliki ivy.xml, ivysettings.xml) vs Maven (pom.xml, repozytoria
         * centralne, ~/.m2) vs Gradle (build.gradle, cache ~/.gradle/caches) -
         * uwzgledniajac miejsce przechowywania pobranych JAR-ow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildToolDecisionTree {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj metode recommendBuildTool(boolean isLegacy, boolean isAndroid,
         * boolean needsCustomLogic, boolean isStandardBackend), ktora na podstawie flag
         * zwraca rekomendacje (String) - Ant/Maven/Gradle - wg prostej logiki if/else
         * odzwierciedlajacej wskazowki z lekcji. Przetestuj na 4 roznych kombinacjach flag.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LifecycleVsTasksVsTargetsComparison {
        /*
         * 🧪 Zadanie 17:
         * Wypisz porownanie 3 mechanizmow organizacji pracy: Ant target (dowolna nazwa,
         * depends="..."), Maven phase (sztywna lista faz w lifecycle), Gradle task
         * (dowolna nazwa, automatycznie wykrywane zaleznosci wejscie/wyjscie) - min. 2
         * roznice dla kazdej pary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_EcosystemSizeComparisonPrint {
        /*
         * 🧪 Zadanie 18:
         * Wypisz subiektywne porownanie rozmiaru ekosystemu pluginow/tasков dla kazdego
         * narzedzia (Maven Central pluginy, Gradle Plugin Portal, Ant - mniejszy
         * ekosystem, ale latwo pisac wlasne taski w Javie) - 3 linie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CiFriendlinessComparison {
        /*
         * 🧪 Zadanie 19:
         * Wypisz porownanie "przyjaznosci" dla CI (Jenkins/GitHub Actions) kazdego z 3
         * narzedzi - co potrzeba zainstalowac/przygotowac na serwerze CI dla kazdego
         * (Ant - sam Ant + JDK, Maven - mvnw eliminuje potrzebe instalacji, Gradle -
         * gradlew analogicznie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MigrationDifficultyEstimation {
        /*
         * 🧪 Zadanie 20:
         * Wypisz subiektywna ocene (1-5, gdzie 5 = najtrudniej) trudnosci migracji w
         * kazdym kierunku: Ant->Maven, Ant->Gradle, Maven->Gradle, Gradle->Maven - z
         * jednozdaniowym uzasadnieniem kazdej oceny (zapowiedz tematu Lekcji 19).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WeightedScoringSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj system punktowy: kazde narzedzie (Ant/Maven/Gradle) ma oceny
         * (1-10) w 5 kategoriach (elastycznosc, latwosc startu, szybkosc, ekosystem,
         * przewidywalnosc) w Map<String, Map<String,Integer>>. Napisz metode
         * computeWeightedScore(String tool, Map<String,Double> weights), ktora liczy
         * sredni wazona i porownaj wyniki dla 2 roznych profili wag (np. "startup" vs
         * "duze enterprise").
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildFileComplexityAnalyzer {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode analyzeComplexity(String buildFileContent), ktora liczy: liczbe
         * linii, liczbe "slow kluczowych" (np. "target"/"dependency"/"task" w zaleznosci
         * od typu pliku), i szacowany "poziom zagniezdzenia" (liczy najglebszy wciecie).
         * Zastosuj do 3 wygenerowanych wczesniej plikow (build.xml/pom.xml/build.gradle)
         * i wypisz porownanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_HybridProjectSimulator {
        /*
         * 🧪 Zadanie 23:
         * Zasymuluj hybrydowy projekt: modul "legacy-core" budowany Antem (generuje JAR
         * do wspolnego katalogu dist/), modul "new-api" budowany Mavenem, ktory
         * DEKLARUJE zaleznosc typu <systemPath> do JAR-a z dist/legacy-core.jar (jako
         * String pom.xml). Wypisz oba configi i skomentuj, jak takie mieszanie technologii
         * dziala w praktyce (i jakie ma wady).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ToolSelectionSurveyProcessor {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj liste "glosow" (rekordow: nazwa projektu, wybrane narzedzie, powod) dla
         * co najmniej 8 fikcyjnych projektow. Napisz metode grupujaca je po narzedziu
         * (Map<String, List<String>>) i wypisujaca statystyke: ile projektow wybralo
         * kazde narzedzie i jakie byly najczestsze powody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ThreeToolCeremonyBenchmark {
        /*
         * 🧪 Zadanie 25:
         * Dla 5 roznych hipotetycznych zaleznosci (rozne biblioteki) wygeneruj ich
         * deklaracje we WSZYSTKICH 3 narzedziach (Maven XML, Gradle DSL, Ant komentarz),
         * zlicz sumaryczna liczbe znakow potrzebnych do zadeklarowania wszystkich 5 w
         * kazdym narzedziu, i wypisz ranking "najbardziej zwiezle -> najbardziej rozwlekle".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ToolCapabilityMatrix {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj boolowska "matryce mozliwosci" (Map<String, Map<String,Boolean>>) dla
         * cech: "wbudowane zarzadzanie zaleznosciami", "custom logika bez pluginu",
         * "wrapper/mvnw-jak", "build cache", "multi-module natywnie" x 3 narzedzia.
         * Wypisz ja jako czytelna tabele i policz, ktore narzedzie ma najwiecej "true".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RealWorldCaseStudySimulation {
        /*
         * 🧪 Zadanie 27:
         * Napisz symulacje "case study": firma z 15-letnim systemem na Ancie rozwaza
         * 3 opcje (zostac na Ancie, migrowac do Mavena, migrowac do Gradle). Zbuduj dla
         * kazdej opcji liste 3 plusow i 3 minusow (jako Stringi) i wypisz je w formacie
         * decyzyjnym (rekomendacja na koniec, z uzasadnieniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ConfigurationAsCodeSpectrum {
        /*
         * 🧪 Zadanie 28:
         * Wypisz i uzasadnij umieszczenie kazdego z 3 narzedzi na "spektrum" od
         * "czystej konfiguracji" (deklaratywne dane, zero logiki) do "pelnego kodu"
         * (programowalna logika) - z konkretnymi przykladami z lekcji (np. Ant <script>,
         * Gradle custom tasks) ilustrujacymi, ze granice nie sa ostre.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiCriteriaDecisionMatrix {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj pelna macierz decyzyjna: 3 narzedzia x 6 kryteriow z ocenami
         * liczbowymi (mozesz uzyc danych z Zadania 21), oblicz SUMARYCZNY wynik kazdego
         * narzedzia bez wag i Z wagami z 2 profili (startup/enterprise z Zadania 21), i
         * wypisz KTORE narzedzie "wygrywa" w kazdym z 3 scenariuszy (bez wag, startup,
         * enterprise) - pokazujac, ze odpowiedz "ktory jest najlepszy" zalezy od kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ComparisonReportGenerator {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: napisz metode generateComparisonReport(), ktora zbiera
         * WSZYSTKIE dane z tej lekcji (tabela porownawcza, przyklad Gson w 3 wariantach,
         * "kiedy ktore narzedzie") w jeden, sformatowany, wieloczesciowy raport tekstowy
         * (z naglowkami sekcji jak w lekcji teoretycznej) i wypisuje go w calosci na
         * konsoli - jako "sciagawke" do wykorzystania przy realnej decyzji projektowej.
         */
        public static void main(String[] args) { }
    }
}
