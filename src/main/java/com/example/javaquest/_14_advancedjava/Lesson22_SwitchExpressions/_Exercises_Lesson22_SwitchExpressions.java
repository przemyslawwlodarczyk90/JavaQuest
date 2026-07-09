package com.example.javaquest._14_advancedjava.Lesson22_SwitchExpressions;

public class _Exercises_Lesson22_SwitchExpressions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicSwitchExpressionArrow {
        /*
         * 🧪 Zadanie 1:
         * Napisz metodę String grade(int score) zwracającą "A" (>=90),
         * "B" (>=75), "C" (>=60), "D" (poniżej 60) za pomocą arrow-switcha
         * na WYLICZONYM z score przedziale (np. score/10), lub innej
         * metody bez zakresów w case. Przetestuj dla 95, 80, 65, 30.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ClassicSwitchStatementWithBreak {
        /*
         * 🧪 Zadanie 2:
         * Napisz metodę void printSeasonForMonth(int month) używającą
         * KLASYCZNEGO switcha statement (dwukropek) z JAWNYMI `break` w
         * każdej gałęzi, mapującą numer miesiąca (1-12) na porę roku.
         * Przetestuj dla miesięcy 1, 6, 9, 12.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FallThroughBugDemo {
        /*
         * 🧪 Zadanie 3:
         * Napisz metodę CELOWO zawierającą błąd fall-through: klasyczny
         * switch statement z co najmniej 4 etykietami, w którym w JEDNYM
         * miejscu BRAKUJE break. Uruchom dla wartości powodującej
         * fall-through i w komentarzu opisz NIEOCZEKIWANY wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixFallThroughWithArrow {
        /*
         * 🧪 Zadanie 4:
         * Weź metodę z Zadania 3 i przepisz ją na arrow-switch (->).
         * Uruchom dla TEJ SAMEJ wartości wejściowej i potwierdź w
         * komentarzu, że błąd fall-through zniknął.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MultipleLabelsPerArm {
        /*
         * 🧪 Zadanie 5:
         * Napisz metodę String dayCategory(int dayNumber) (1-7) zwracającą
         * "Robocze" dla 1-5 i "Weekend" dla 6-7, używając WIELU etykiet na
         * jedną gałąź (`case 1, 2, 3, 4, 5 ->`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_YieldInBlockBody {
        /*
         * 🧪 Zadanie 6:
         * Napisz switch-wyrażenie na int quantity obliczające rabat
         * procentowy, gdzie DLA JEDNEJ z gałęzi (np. quantity >= 10)
         * potrzebujesz bloku z `yield`: najpierw oblicz wartość pośrednią
         * (np. bazowy rabat), potem zmodyfikuj ją warunkiem if, na końcu
         * yield. Przetestuj dla kilku wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SwitchExpressionAsMethodArgument {
        /*
         * 🧪 Zadanie 7:
         * Wywołaj System.out.println(...) przekazując WYNIK switch-
         * wyrażenia BEZPOŚREDNIO jako argument (bez pośredniej zmiennej),
         * np. mapując kod statusu HTTP (200/404/500) na opis.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SwitchExpressionAsReturnStatement {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę String romanDigit(int n) (dla n = 1..5) zwracającą
         * cyfrę rzymską przez `return switch (n) { ... };` BEZPOŚREDNIO,
         * bez pośredniej zmiennej lokalnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExhaustiveEnumSwitchNoDefault {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj enum TrafficSignal { RED, YELLOW, GREEN }. Napisz
         * switch-wyrażenie String instructionFor(TrafficSignal s) BEZ
         * default, obsługujące WSZYSTKIE trzy stałe. Wypisz instrukcję
         * dla każdej wartości enuma w pętli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NonExhaustiveEnumSwitchCompileErrorComment {
        /*
         * 🧪 Zadanie 10:
         * W KOMENTARZU zapisz wersję metody z Zadania 9, w której CELOWO
         * pominięto gałąź YELLOW i NIE dodano default. Zapisz treść błędu
         * kompilacji ("the switch expression does not cover all possible
         * input values"). Następnie napraw kod, dopisując brakującą
         * gałąź.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DefaultRequiredForNonEnumExpression {
        /*
         * 🧪 Zadanie 11:
         * W KOMENTARZU zapisz switch-wyrażenie nad `int` BEZ default (np.
         * tylko case 1, 2, 3) i zanotuj błąd kompilacji, jaki by wystąpił
         * (kompilator nie potrafi udowodnić wyczerpania dla int). Następnie
         * napisz DZIAŁAJĄCĄ wersję z default.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_StatementVsExpressionExhaustivenessComparison {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj enum Direction { NORTH, SOUTH, EAST, WEST }. Napisz
         * DWIE metody nad tym enumem: (a) switch STATEMENT pomijający 2
         * stałe (kompiluje się, mimo braków), (b) switch EXPRESSION
         * próbujący pominąć te same 2 stałe (W KOMENTARZU - to się NIE
         * skompiluje). Wyjaśnij różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NestedSwitchInsideYieldBlock {
        /*
         * 🧪 Zadanie 13:
         * Napisz switch-wyrażenie nad String category ("A"/"B"/inne), w
         * którym gałąź dla "A" ma blok z `yield`, wewnątrz którego
         * znajduje się DRUGI, ZAGNIEŻDŻONY switch-wyrażenie (np. nad
         * dodatkowym int priority) - wynik tego wewnętrznego switcha
         * przypisz do zmiennej i użyj w yield.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleLabelsWithEnumAndDefault {
        /*
         * 🧪 Zadanie 14:
         * Użyj enuma Season z lekcji. Napisz switch-wyrażenie grupujące
         * SPRING i SUMMER jako "Ciepła połowa roku", AUTUMN i WINTER jako
         * "Zimna połowa roku", z dodanym (choć obecnie zbędnym) `default`
         * jako zabezpieczeniem na przyszłość - skomentuj, dlaczego to
         * dobra praktyka defensywna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConvertLegacyIfElseChainToSwitch {
        /*
         * 🧪 Zadanie 15:
         * Napisz NAJPIERW łańcuch if/else-if klasyfikujący int
         * httpStatusCode na "2xx"/"3xx"/"4xx"/"5xx"/"inny". POTEM
         * zrefaktoryzuj na switch-wyrażenie, wykorzystując fakt, że switch
         * nie obsługuje zakresów - podziel httpStatusCode / 100 i switchuj
         * po WYNIKU tego dzielenia (2, 3, 4, 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_YieldVsReturnDifference {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę String processCode(int code), w której: jeśli
         * code == -1, metoda robi WCZESNY `return "BŁĄD KRYTYCZNY"` (poza
         * switchem, na samym początku metody); w przeciwnym razie wynik
         * przypisywany jest ze switch-wyrażenia używającego `yield`
         * wewnątrz bloków. W komentarzu wyjaśnij różnicę między `return` a
         * `yield`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SwitchExpressionProducingEnum {
        /*
         * 🧪 Zadanie 17:
         * Użyj enuma Season z lekcji. Napisz metodę Season parseSeason
         * (String name) zwracającą ODPOWIEDNIĄ stałą enuma na podstawie
         * Stringa ("wiosna"/"lato"/"jesień"/"zima", małymi literami) - z
         * default rzucającym IllegalArgumentException dla nieznanej
         * nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ChainedSwitchExpressionsComposition {
        /*
         * 🧪 Zadanie 18:
         * Napisz DWA switch-wyrażenia, gdzie WYNIK pierwszego (np. int ->
         * "kategoria wiekowa" jako String) jest od razu SELEKTOREM
         * drugiego switcha (String kategoria -> rekomendowany produkt).
         * Przetestuj dla kilku wieków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CombiningTernaryAndSwitchExpression {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę używającą TERNARA dla prostego wyboru dwuwartościowego
         * (np. czy liczba jest parzysta) ORAZ switch-wyrażenia dla wyboru
         * co najmniej trzywartościowego (np. kategoria wagowa: lekki/
         * średni/ciężki) w JEDNEJ metodzie. W komentarzu uzasadnij, kiedy
         * wybrać ternar, a kiedy switch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorFallThroughIntentionalGrouping {
        /*
         * 🧪 Zadanie 20:
         * Napisz klasyczny switch statement, który POPRAWNIE (celowo,
         * bez błędu) wykorzystuje fall-through do grupowania: case 1, 2,
         * 3 dzielą wspólny kod, potem `break`. Następnie napisz RÓWNOWAŻNY
         * arrow-switch z wieloma etykietami (`case 1, 2, 3 ->`). W
         * komentarzu oceń, która wersja jest czytelniejsza.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ExhaustivenessSafetyNetDemo {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj enum Status { NEW, IN_PROGRESS, DONE } i napisz
         * EXHAUSTIVE switch-wyrażenie String label(Status s) (bez
         * default) obsługujące wszystkie 3 stałe. W KOMENTARZU opisz, co
         * by się stało z WSZYSTKIMI takimi switchami w hipotetycznym dużym
         * projekcie, gdyby dodano 4. stałą CANCELLED - porównaj tę
         * korzyść z analogiczną cechą sealed typów (Lekcja 19/21).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SwitchExpressionWithComplexYieldLogic {
        /*
         * 🧪 Zadanie 22:
         * Napisz switch-wyrażenie nad int n, gdzie JEDNA z gałęzi (np. dla
         * n > 100) oblicza w bloku z `yield` sumę cyfr n za pomocą pętli
         * while wewnątrz tego bloku, a pozostałe gałęzie zwracają proste
         * wartości. Przetestuj dla n = 12345 i n = 7.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MinimalArrowSyntaxExpressionStatementForm {
        /*
         * 🧪 Zadanie 23:
         * Napisz TE SAME przypadki (np. mapowanie dnia tygodnia na opis)
         * DWA razy: (a) jako arrow-switch użyty jako STATEMENT (każda
         * gałąź tylko woła println, switch nic nie zwraca), (b) jako
         * arrow-switch użyty jako EXPRESSION (przypisany do zmiennej i
         * wypisany raz na końcu). W komentarzu opisz, kiedy wybrać którą
         * formę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SwitchOnCharacterType {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę String classifyChar(char c) zwracającą "samogłoska"
         * (a,e,i,o,u,y), "cyfra" (0-9 - użyj Character.isDigit w guardzie
         * lub osobnych etykiet), "spółgłoska" lub "inny znak" - za pomocą
         * switch-wyrażenia z wieloma etykietami. Przetestuj na napisie
         * "Kot123!" znak po znaku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SwitchOnStringWithMultipleSynonymLabels {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj enum Action { START, STOP, PAUSE }. Napisz metodę
         * Action parseCommand(String cmd) mapującą SYNONIMY komend
         * ("start","run","go" -> START; "stop","halt" -> STOP; "pause",
         * "wait" -> PAUSE) na wartość enuma, używając wielu etykiet
         * Stringów na jedną gałąź plus default rzucający wyjątkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CombiningMultipleLabelsWithYieldBlocks {
        /*
         * 🧪 Zadanie 26:
         * Napisz JEDNO switch-wyrażenie, w którym NIEKTÓRE gałęzie to
         * proste wyrażenia strzałkowe (`case X -> "prosto"`), a INNE to
         * bloki z `yield` (z dodatkową logiką) - w TYM SAMYM switchu.
         * Zweryfikuj, że obie formy współistnieją poprawnie dla kilku
         * wartości wejściowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RewriteHourOfDayWithComputedBucket {
        /*
         * 🧪 Zadanie 27:
         * Nawiązując do przykładu pory dnia z Lesson03_Conditionals,
         * napisz PRECYZYJNIEJSZĄ wersję: oblicz `int bucket = hour / 6`
         * (0=noc, 1=poranek, 2=popołudnie, 3=wieczór dla godzin 0-23) i
         * napisz switch-wyrażenie String timeOfDay nad `bucket` (nie nad
         * `hour` bezpośrednio). Przetestuj dla kilku godzin z różnych
         * przedziałów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DemonstrateSwitchExpressionTypeUnification {
        /*
         * 🧪 Zadanie 28:
         * Napisz switch-wyrażenie, w którym RÓŻNE gałęzie zwracają literały
         * różnych typów liczbowych (np. jedna gałąź `-> 5` (int), inna `->
         * 2.5` (double)) przypisane do zmiennej `double wynik = switch
         * (...) {...}`. W komentarzu wyjaśnij, jak kompilator ujednolica
         * (unifikuje) typ całego wyrażenia do double, i wypisz wynik dla
         * obu gałęzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_LegacyCodeMigrationExercise {
        /*
         * 🧪 Zadanie 29:
         * Napisz NAJPIERW metodę w stylu LEGACY: deklaruje zmienną `String
         * result;` PRZED switchem, klasyczny switch statement z co
         * najmniej 4 gałęziami MUTUJĄCYMI `result = ...;` i `break;` w
         * każdej, na końcu `return result;`. POTEM w PEŁNI zrefaktoryzuj
         * do switch-wyrażenia zwracanego bezpośrednio przez `return
         * switch (...) {...};`, USUWAJĄC zmienną mutowalną całkowicie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneComparePatternMatchingSwitchVsPlainSwitch {
        /*
         * 🧪 Zadanie 30:
         * W KOMENTARZU krótko porównaj cztery techniki poznane w Lekcjach
         * 19-22: (a) zwykły switch-wyrażenie na int/enum (ta lekcja), (b)
         * switch pattern matching + record patterns na sealed typach
         * (Lekcja 21), (c) pattern matching instanceof (Lekcja 20), (d)
         * sealed classes jako fundament wyczerpania (Lekcja 19). Następnie
         * NAPISZ JEDNĄ metodę łączącą CO NAJMNIEJ dwie z tych technik (np.
         * pattern matching instanceof + switch-wyrażenie na wyniku
         * klasyfikacji) w spójnym, działającym przykładzie.
         */
        public static void main(String[] args) { }
    }
}
