package com.example.javaquest._15_jvm_internals.Lesson13_JitCompilerBasics;

public class _Exercises_Lesson13_JitCompilerBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainInterpreterVsJit {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij (min. 3 zdania) roznice miedzy
         * interpretowaniem bajtkodu a kompilacja JIT, i dlaczego JVM
         * uzywa OBU podejsc zamiast tylko jednego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CompareC1AndC2InOwnWords {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: opisz roznice miedzy kompilatorem C1 a C2 (szybkosc
         * kompilacji vs jakosc wygenerowanego kodu) - min. 3 zdania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListTieredCompilationLevels {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wypisz w komentarzu wszystkie 5 poziomow (0-4)
         * tiered compilation z krotkim opisem kazdego (na podstawie
         * teorii lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteSimpleHotMethodAndCallItManyTimes {
        /*
         * 🧪 Zadanie 4:
         * Napisz prosta metode `static int square(int x)` zwracajaca x*x i
         * wywolaj ja 100 000 razy w petli, sumujac wyniki - wypisz sume
         * koncowa (bez pomiaru czasu na razie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MeasureSingleColdCall {
        /*
         * 🧪 Zadanie 5:
         * Zmierz (System.nanoTime()) czas JEDNEGO, pierwszego wywolania
         * metody square() z Zadania 4 - wypisz wynik w nanosekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainCompileThresholdConcept {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij (min. 2 zdania), co koncepcyjnie oznacza
         * "prog kompilacji" (compile threshold) i dlaczego JIT nie
         * kompiluje metody od razu, przy pierwszym wywolaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainDeoptimizationInOwnWords {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: opisz wlasnymi slowami (min. 3 zdania), czym jest
         * deoptymalizacja i podaj przyklad sytuacji (z teorii lub wlasny),
         * ktora moze ja wywolac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RunJvmWithPrintCompilationFlag {
        /*
         * 🧪 Zadanie 8:
         * Uruchom (ProcessBuilder, z limitem czasu) "java
         * -XX:+PrintCompilation -version" - wypisz kod wyjscia i pierwsze
         * 3 niepuste linie wyjscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CountCompilationLogLines {
        /*
         * 🧪 Zadanie 9:
         * Uruchom prosty program z goraca petla (np. 200 000 wywolan
         * metody arytmetycznej) z flaga -XX:+PrintCompilation - policz i
         * wypisz calkowita liczbe niepustych linii logu kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyBenchmarksNeedWarmup {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego rzetelny
         * mikrobenchmark w Javie MUSI zawierac faze "rozgrzewki" (warmup)
         * przed wlasciwym pomiarem czasu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MeasureColdVsWarmBatchOfCalls {
        /*
         * 🧪 Zadanie 11:
         * Uzywajac wzorca z teorii (timeBatch), zmierz czas 100 "zimnych"
         * wywolan metody i 100 wywolan PO 20 000 wywolaniach
         * rozgrzewajacych - wypisz oba czasy i policz przyspieszenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VaryWarmupCountAndObserveTrend {
        /*
         * 🧪 Zadanie 12:
         * Zmierz czas tego samego batcha wywolan (np. 100) po 0, 1 000,
         * 10 000 i 100 000 wywolaniach rozgrzewajacych - wypisz wszystkie 4
         * czasy jako "krzywa rozgrzewania" i skomentuj trend.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FilterPrintCompilationOutputByMethodName {
        /*
         * 🧪 Zadanie 13:
         * Uruchom program z -XX:+PrintCompilation zawierajacy metode o
         * unikalnej nazwie (np. "myUniqueHotMethod") - odfiltruj z
         * wyjscia TYLKO linie zawierajace te nazwe i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TryPrintCompilationWithAndWithoutUnlock {
        /*
         * 🧪 Zadanie 14:
         * Odtworz logike z teorii: sprobuj uruchomic "-XX:+PrintCompilation"
         * BEZ odblokowania diagnostyki, a jesli JVM zglosi blad zwiazany z
         * diagnostyka, ponow probe z "-XX:+UnlockDiagnosticVMOptions" -
         * wypisz, ktora proba sie powiodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainMonomorphicVsPolymorphicCallSite {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij roznice miedzy wywolaniem monomorficznym
         * (jedna implementacja widziana w danym miejscu wywolania) a
         * polimorficznym (wiele roznych implementacji) - i jak to wplywa
         * na decyzje JIT o inline'owaniu/deoptymalizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildInterfaceAndTriggerPolymorphicCallSite {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj interfejs Shape z metoda `double area()` oraz 2 klasy
         * implementujace (Circle, Square). Napisz petle wywolujaca
         * area() na obiekcie typu Shape, gdzie przez pierwsze 40 000
         * wywolan zawsze przekazujesz Circle, a nastepnie zaczynasz
         * przekazywac Square - w komentarzu zanotuj, czy widac roznice w
         * czasie wykonania w miejscu zmiany typu (nie zawsze bedzie to
         * widoczne w prostym demo - opisz obserwacje uczciwie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareTimingOfTrivialVsHeavyMethod {
        /*
         * 🧪 Zadanie 17:
         * Napisz dwie metody: trywialna (np. `return x + 1`) i ciezsza
         * (petla z 100 iteracjami obliczen) - zmierz czas 'zimnych' i
         * 'rozgrzanych' wywolan OBU metod i porownaj, gdzie widac wieksze
         * przyspieszenie po rozgrzaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ParseCompilationLevelColumnFromLog {
        /*
         * 🧪 Zadanie 18:
         * Z realnego logu -XX:+PrintCompilation wyciagnij (wyrazeniem
         * regularnym lub prostym parsowaniem) kolumne poziomu kompilacji
         * (liczba 1-4 wystepujaca po numerze ID) dla linii zawierajacych
         * Twoja metode - wypisz zbior UNIKALNYCH napotkanych poziomow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureImpactOfDisablingTieredCompilation {
        /*
         * 🧪 Zadanie 19:
         * Uruchom (ProcessBuilder) ten sam program raz normalnie, raz z
         * -XX:-TieredCompilation (wylaczajac tryb warstwowy, JIT idzie
         * od razu do C2) - porownaj czas calkowitego wykonania obu
         * wariantow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeJitDecisionFactors {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wypisz (min. 4 punkty) czynniki, ktore MOGA
         * wplywac na to, czy i jak szybko JIT skompiluje dana metode
         * (np. czestotliwosc wywolan, rozmiar metody, stabilnosc typow
         * argumentow).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildWarmupCurveAcrossManyPoints {
        /*
         * 🧪 Zadanie 21:
         * Zmierz czas batcha 50 wywolan po KAZDYM z progow rozgrzewania:
         * 0, 500, 2 000, 5 000, 10 000, 20 000, 50 000, 100 000 - wypisz
         * pelna "krzywa rozgrzewania" (8 punktow) w formie tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompileAndRunExternalHotLoopWithFlags {
        /*
         * 🧪 Zadanie 22:
         * Skompiluj (javax.tools.JavaCompiler, tak jak w teorii) osobny
         * plik zrodlowy z goraca petla i uruchom go jako proces potomny z
         * -XX:+PrintCompilation ORAZ z -XX:CICompilerCount=1 (ogranicza
         * liczbe watkow kompilujacych) - porownaj liczbe linii logu z
         * domyslna konfiguracja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DetectDeoptimizationLogEntries {
        /*
         * 🧪 Zadanie 23:
         * Uruchom program z polimorficznym wywolaniem (2+ implementacje
         * interfejsu przelaczane w trakcie dzialania) z flagami
         * -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation
         * -XX:+TraceDeoptimization - poszukaj w wyjsciu linii zawierajacych
         * "deopt" - jesli ich brak, w komentarzu wyjasnij mozliwe powody
         * (np. JIT nie zdazyl skompilowac przed zmiana typu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MeasureVarianceAcrossMultipleRuns {
        /*
         * 🧪 Zadanie 24:
         * Uruchom TEN SAM eksperyment "zimne vs rozgrzane" 5 razy pod
         * rzad (w tym samym procesie) - wypisz wszystkie 5 par wynikow i
         * skomentuj (min. 3 zdania) obserwowana zmiennosc (variance)
         * pomiarow czasu w JVM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareC1OnlyVsFullTieredThroughput {
        /*
         * 🧪 Zadanie 25:
         * Uruchom (ProcessBuilder) program obliczeniowy z
         * -XX:TieredStopAtLevel=1 (zatrzymuje sie na C1, bez C2) oraz bez
         * tej flagi (pelny tiered do poziomu 4) - zmierz i porownaj czas
         * wykonania obu wariantow dla wystarczajaco dlugiej petli
         * obliczeniowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildReusableJitExperimentHarness {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode `static long runVariant(String... extraJvmFlags)`,
         * ktora kompiluje i uruchamia ten sam program obliczeniowy z
         * podanymi dodatkowymi flagami JVM i zwraca zmierzony czas
         * wykonania (ms) - przetestuj z co najmniej 3 roznymi zestawami
         * flag (domyslne, -XX:TieredStopAtLevel=1, -XX:-TieredCompilation).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AnalyzeRealCompilationLogStatistics {
        /*
         * 🧪 Zadanie 27:
         * Z realnego logu -XX:+PrintCompilation zbierz statystyki: ile
         * razy KAZDA unikalna metoda zostala skompilowana (rekompilacje
         * to normalne przy przechodzeniu przez poziomy) - wypisz metody
         * skompilowane WIECEJ NIZ RAZ jako dowod przejscia przez wiele
         * poziomow tiered compilation.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareCompilationSpeedVsCodeQualityTradeoff {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: na podstawie wynikow z Zadania 25 (C1-only vs
         * pelny tiered) napisz krotki raport (min. 5 zdan) omawiajacy
         * kompromis miedzy czasem kompilacji a jakoscia/szybkoscia
         * wygenerowanego kodu, z odwolaniem do konkretnych zmierzonych liczb.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignGuidanceForChoosingCompilerMode {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: opisz (min. 4 punkty) sytuacje, w ktorych
         * warto rozwazyc zmiane domyslnego trybu kompilacji (np. bardzo
         * krotko dzialajace narzedzia CLI vs dlugo dzialajace serwisy) -
         * odwolaj sie do flag poznanych w tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildMiniJitBehaviorReport {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zbuduj narzedzie JitBehaviorReport,
         * ktore dla wskazanej metody obliczeniowej: (1) mierzy czas
         * zimnego i rozgrzanego wywolania w tym samym procesie, (2)
         * uruchamia zewnetrzny proces z -XX:+PrintCompilation i wyciaga
         * najwyzszy zaobserwowany poziom kompilacji dla tej metody, (3)
         * drukuje pelny raport laczacy oba zrodla danych. Kazdy
         * uruchomiony proces MUSI miec limit czasu (waitFor + timeout).
         */
        public static void main(String[] args) { }
    }
}
