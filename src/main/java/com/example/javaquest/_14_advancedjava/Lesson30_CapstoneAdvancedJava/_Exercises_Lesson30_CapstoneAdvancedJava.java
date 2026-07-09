package com.example.javaquest._14_advancedjava.Lesson30_CapstoneAdvancedJava;

public class _Exercises_Lesson30_CapstoneAdvancedJava {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddSubtractExpressionType {
        /*
         * 🧪 Zadanie 1:
         * Dodaj do sealed hierarchii Expr z lekcji NOWY rekord `Subtract(Expr left,
         * Expr right)` (odejmowanie). Dopisz go do 'permits' i do switcha w
         * evaluate() - zaobserwuj, ze BEZ tej drugiej zmiany kod sie nie skompiluje
         * (exhaustiveness w akcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_EvaluateSimpleExpressionsFromLesson {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj i wyewaluuj 5 roznych wyrazen na wzor tych z lekcji, np.
         * "3 * (x + 1)", "-(y * y)", "sqrt(x) + sqrt(y)" - dla zmiennych x=4.0, y=9.0.
         * Wypisz kazdy wynik przez switch na Result<Double>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_EnvironmentDefensiveCopyProof {
        /*
         * 🧪 Zadanie 3:
         * Powtorz demo z lekcji (zmiana oryginalnej mapy PO utworzeniu Environment),
         * ale tym razem takze sprobuj wywolac `((Map) env.getVariablesForTest()).put(...)`
         * (gdybys dodal taki "wyciekajacy" getter) - wyjasnij w komentarzu, dlaczego
         * `Map.copyOf` chroni przed OBOMA kierunkami wycieku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RegisterNewStableFunction {
        /*
         * 🧪 Zadanie 4:
         * Zarejestruj w FunctionRegistry NOWA, "stabilna" funkcje (np. "abs" ->
         * Math::abs, "square" -> lambda x -> x*x) - zweryfikuj, ze refleksja NIE
         * zglasza dla niej ostrzezenia @Experimental (bo to lambda/referencja do
         * metody, nie nazwana klasa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HandleUnknownVariableGracefully {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj wyrazenie uzywajace 3 zmiennych, z ktorych TYLKO 2 sa w Environment.
         * Wyewaluuj je i zweryfikuj, ze dostajesz Result.Failure z CZYTELNYM
         * komunikatem wskazujacym brakujaca zmienna - bez wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ResultMapChaining {
        /*
         * 🧪 Zadanie 6:
         * Wyewaluuj dowolne wyrazenie, a nastepnie na WYNIKOWYM Result<Double> uzyj
         * `.map(...)` DWUKROTNIE z rzedu (np. pomnoz przez 2, potem zaokraglij) -
         * wypisz posrednie i koncowy wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ResultFlatMapForDivision {
        /*
         * 🧪 Zadanie 7:
         * Dodaj do Expr rekord `Divide(Expr left, Expr right)`. W evaluate() dla
         * Divide, jesli prawa strona wyewaluuje sie do 0.0, zwroc Result.Failure
         * "Dzielenie przez zero" zamiast dopuscic do Infinity/NaN (uzyj flatMap).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_PrettyPrintExpressionTree {
        /*
         * 🧪 Zadanie 8:
         * Napisz metode `String opisz(Expr expr)` uzywajaca switcha z rekordowymi
         * wzorcami (jak evaluate()), ktora zamiast liczyc wynik, buduje CZYTELNY
         * zapis wyrazenia jako String (np. "((2 * x) + sqrt(y))").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CountNodesInExpressionTree {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode `int policzWezly(Expr expr)` (rekurencyjnie, switch z
         * rekordowymi wzorcami) zliczajaca WSZYSTKIE wezly w drzewie wyrazenia -
         * zweryfikuj na wyrazeniu z lekcji "(2 * x) + sqrt(y)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MarkAndDetectSecondExperimentalFunction {
        /*
         * 🧪 Zadanie 10:
         * Utworz DRUGA klase implementujaca DoubleUnaryOperator, oznaczona
         * @Experimental z WLASNYM opisem powodu, zarejestruj ja w FunctionRegistry i
         * zweryfikuj, ze refleksja wypisuje TWOJ opis (nie ten z lekcji).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ScanRegistryForAllExperimentalFunctions {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz FunctionRegistry o metode `List<String> znajdzEksperymentalne()`,
         * ktora skanuje refleksja WSZYSTKIE zarejestrowane funkcje i zwraca nazwy
         * tych oznaczonych @Experimental - bez wypisywania niczego przy rejestracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VariableSubstitutionOptimization {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode `Expr uprosc(Expr expr)` (switch z rekordowymi wzorcami),
         * ktora upraszcza `Multiply(Literal(0), cokolwiek)` do `Literal(0)`, oraz
         * `Add(Literal(0), x)` do `x` - zweryfikuj na 3 przykladach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GenericResultForNonDoubleType {
        /*
         * 🧪 Zadanie 13:
         * Wykorzystaj Result<T> z lekcji DLA INNEGO typu niz Double - np. napisz
         * `Result<String> sparsuj(String tekst)` parsujace nazwe zmiennej z prostego
         * formatu "VAR:x" (Result.Success("x")) albo blad dla innego formatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExhaustivenessSafetyNetDemo {
        /*
         * 🧪 Zadanie 14:
         * Dodaj do Expr NOWY rekord (np. Power - potegowanie) TYLKO do 'permits' i
         * definicji rekordu, ale CELOWO NIE dopisuj go do switcha w evaluate() -
         * zaobserwuj i zapisz w komentarzu DOKLADNY blad kompilacji. Napraw go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FunctionRegistryWithTwoArgFunctions {
        /*
         * 🧪 Zadanie 15:
         * Rozszerz system o funkcje DWUARGUMENTOWE (np. "pow", "max") - dodaj rekord
         * `CallBinary(String functionName, Expr left, Expr right)` do Expr oraz
         * osobny rejestr oparty na `DoubleBinaryOperator` (Lekcja 11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImmutableExpressionCacheByStructuralEquality {
        /*
         * 🧪 Zadanie 16:
         * Wykorzystaj fakt, ze rekordy maja darmowe equals()/hashCode() (strukturalna
         * rownosc) - zaimplementuj prosty `Map<Expr, Result<Double>> cache` w
         * evaluate(), zeby NIE liczyc ponownie identycznego wyrazenia. Zmierz przyspieszenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ThreadSafeSharedEnvironment {
        /*
         * 🧪 Zadanie 17:
         * Uruchom 8 watkow (jak w _05_multithreading) rownolegle ewaluujacych RÓZNE
         * wyrazenia na TYM SAMYM, WSPOLDZIELONYM obiekcie Environment - zweryfikuj
         * brak race condition (Environment jest niezmienny, wiec to powinno byc
         * bezpieczne bez synchronized) i wyjasnij dlaczego w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CustomExceptionInsteadOfResultComparison {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj RÓWNOLEGLA wersje `double evaluateOrThrow(Expr, Environment,
         * FunctionRegistry)` rzucajaca wlasny wyjatek (zamiast Result<T>) przy bledzie
         * - porownaj w komentarzu (min. 4 zdania) oba podejscia: kiedy Result<T> jest
         * lepszym wyborem niz wyjatek, i odwrotnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MethodHandleBasedEvaluatorForPerformance {
        /*
         * 🧪 Zadanie 19:
         * Na wzor Lekcji 29 (refleksja vs MethodHandle): zaimplementuj wariant
         * FunctionRegistry oparty na MethodHandle zamiast DoubleUnaryOperator dla
         * funkcji zarejestrowanych PRZEZ NAZWE metody statycznej (np. "Math.sqrt") -
         * porownaj wydajnosc z wersja lambda-based na 100 000 wywolaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ValidateExpressionAnnotatedFields {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj wlasna adnotacje `@WymaganaZmienna(String nazwa)` na METODZIE
         * budujacej konkretne wyrazenie testowe i - refleksja - zweryfikuj PRZED
         * ewaluacja, ze wszystkie zmienne wymagane adnotacja SA obecne w Environment
         * (inaczej Result.Failure zamiast wywolania evaluate()).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SketchSpiBasedFunctionProvider {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj (kod + komentarz, bez uruchamiania ServiceLoader) interfejs
         * `NamedFunctionProvider` zapowiedziany w komentarzu lekcji (Lekcje 26-28) -
         * napisz 2 przykladowe implementacje (np. LogFunctionProvider,
         * CbrtFunctionProvider) I tresc pliku `META-INF/services/...` jaki by dla
         * nich powstal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FullExpressionParserFromString {
        /*
         * 🧪 Zadanie 22:
         * Napisz PROSTY parser `Expr sparsuj(String tekst)` dla notacji infiksowej z
         * nawiasami (np. "(2 * x) + sqrt(y)") budujacy drzewo Expr - wystarczy
         * obsluzyc +, *, wywolania jednoargumentowe i nawiasy (bez pelnej gramatyki
         * priorytetow, jesli uzyjesz nawiasow wszedzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ExtendResultWithRecoverMethod {
        /*
         * 🧪 Zadanie 23:
         * Dodaj do sealed interface Result<T> domyslna metode `T recover(T
         * wartoscDomyslna)` (zwraca value dla Success, wartoscDomyslna dla Failure) -
         * uzyj jej do "bezpiecznej" ewaluacji wyrazen z potencjalnymi bledami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FullBenchmarkOfWholeEvaluationPipeline {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj 1000 losowych, ROZNYCH wyrazen (glebokosc 3-5) i wyewaluuj je
         * WSZYSTKIE - zmierz i wypisz PELNY raport wydajnosciowy (calkowity czas,
         * sredni czas na wyrazenie, liczba trafien do rejestru funkcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PluginStyleExtensionWithoutModifyingEvaluate {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj sposob dodania NOWEJ operacji (np. "modulo") do systemu BEZ
         * modyfikacji istniejacego switcha w evaluate() - np. przez osobny rekord
         * `CustomOp(String name, Expr left, Expr right)` obslugiwany PRZEZ REJESTR
         * (podobnie jak Call), a nie przez nowy 'case'. Zaimplementuj i uzasadnij w
         * komentarzu, kiedy takie podejscie jest lepsze od dodawania nowych 'case'.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareApproachSealedVsOpenForThisDomain {
        /*
         * 🧪 Zadanie 26:
         * Napisz (w komentarzu, min. 6 zdan) analize: system wyrazen z lekcji
         * zamodelowano jako SEALED (zamkniete). Rozwaz, co by sie zmienilo, gdyby
         * ktos chcial DODAWAC NOWE typy wezlow jako zewnetrzne wtyczki (np. jak w
         * Zadaniu 25) - czy sealed dalej ma sens, czy lepszy bylby otwarty
         * interfejs+polimorfizm (Lekcja 29)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FullSystemWithAllExercisesCombined {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj KOMPLETNY system LACZACY: Subtract/Divide (Zadania 1,7), uproszczenia
         * (Zadanie 12), cache (Zadanie 16), parser (Zadanie 22), recover() (Zadanie 23)
         * - w JEDNYM, spojnym programie demonstrujacym KAZDA z tych technik na
         * kolejnych krokach z czytelnymi komunikatami println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ProductionReadinessChecklistForThisSystem {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: napisz "checklist" (min. 8 punktow, w komentarzu) rzeczy do
         * sprawdzenia PRZED uzyciem tego systemu wyrazen w prawdziwym projekcie
         * (walidacja wejscia parsera, limity glebokosci drzewa przed StackOverflowError,
         * bezpieczenstwo watkowe, itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PersonalRoundupOfWholeChapter14 {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz OSOBISTE podsumowanie (min. 8 zdan, w komentarzu)
         * calego rozdzialu _14_advancedjava - co bylo NAJBARDZIEJ zaskakujace, ktora
         * lekcja byla NAJTRUDNIEJSZA, i jak zmienilo sie Twoje rozumienie "nowoczesnej"
         * Javy od Lesson01 (generics) do Lesson30 (ten kapston).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCapstoneFinalSubmission {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie calego rozdzialu: dopracuj system z Zadania 27 do stanu
         * "gotowego do prezentacji" - z pelnym scenariuszem demo w main() pokazujacym
         * KAZDA poznana technike calego rozdzialu _14_advancedjava w akcji (generics,
         * lambdy, refleksja+adnotacje, sealed+pattern matching, niezmiennosc, i
         * komentarz o mozliwym rozszerzeniu przez SPI), z czytelnymi komunikatami
         * println na kazdym kroku.
         */
        public static void main(String[] args) { }
    }
}
