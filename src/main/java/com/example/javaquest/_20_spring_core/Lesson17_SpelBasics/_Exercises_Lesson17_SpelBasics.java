package com.example.javaquest._20_spring_core.Lesson17_SpelBasics;

public class _Exercises_Lesson17_SpelBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainDifferenceBetweenPlaceholderAndSpel {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij roznice miedzy `${...}` (placeholder,
         * Lesson16) a `#{...}` (SpEL).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_EvaluateSimpleArithmeticExpression {
        /*
         * 🧪 Zadanie 2:
         * Uzyj `SpelExpressionParser`, zeby obliczyc WLASNE wyrazenie
         * arytmetyczne (np. `(5 + 3) * 2`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CallStringMethodViaSpel {
        /*
         * 🧪 Zadanie 3:
         * Wywolaj (przez SpEL) INNA metode `String` niz w teorii (np.
         * `.substring(0, 3)`, `.replace(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseValueAnnotationWithSpelArithmetic {
        /*
         * 🧪 Zadanie 4:
         * Uzyj `@Value("#{wyrazenie}")` z WLASNYM obliczeniem
         * arytmetycznym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AccessSystemPropertyViaSpel {
        /*
         * 🧪 Zadanie 5:
         * Odczytaj INNA wlasciwosc systemowa (np. `java.version`) przez
         * `#{systemProperties['...']}`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementTernaryExpressionForBusinessRule {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj operator trojkowy SpEL dla WLASNEJ reguly (np.
         * "czy wiek >= 18 -> 'DOROSLY' : 'NIELETNI'").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseSafeNavigationOnNestedObject {
        /*
         * 🧪 Zadanie 7:
         * Uzyj `?.` na ZAGNIEZDZONYM lancuchu (`#obj?.pole?.metoda()`) -
         * zweryfikuj BRAK NPE, gdy KTORYKOLWIEK element jest `null`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareSafeNavigationWithRegularDotOperator {
        /*
         * 🧪 Zadanie 8:
         * Uzyj ZWYKLEJ kropki (bez `?.`) na `null` obiekcie w SpEL -
         * zaobserwuj wyjatek, porownaj z Zadaniem 7.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_EvaluateExpressionReturningDifferentTypes {
        /*
         * 🧪 Zadanie 9:
         * Sprawdz `getValue(Class)` (z jawnym typem docelowym) na
         * wyrazeniu zwracajacym `String` skonwertowanym na `Integer`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWhereSpelAppearsElsewhereInSpring {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wymien co najmniej 2 MIEJSCA w Springu (poza
         * `@Value`), gdzie SpEL sie pojawia (zapowiedz
         * `_24_spring_security/Lesson10`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCustomFunctionRegisteredInEvaluationContext {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj WLASNA metode statyczna jako "funkcje" SpEL
         * (`registerFunction(...)`) - wywolaj ja w wyrazeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_EvaluateCollectionProjectionExpression {
        /*
         * 🧪 Zadanie 12:
         * Uzyj "projekcji" SpEL (`#{list.![metoda()]}`) na liscie
         * obiektow - zwroc liste WYNIKOW wywolania metody NA KAZDYM
         * elemencie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_EvaluateCollectionSelectionExpression {
        /*
         * 🧪 Zadanie 13:
         * Uzyj "selekcji" SpEL (`#{list.?[warunek]}`) - przefiltruj
         * liste WEDLUG warunku bezposrednio w wyrazeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSpelExpressionCachingForPerformance {
        /*
         * 🧪 Zadanie 14:
         * Sparsuj wyrazenie RAZ (`parser.parseExpression(...)`), potem
         * wywoluj `getValue()` WIELOKROTNIE na tym SAMYM sparsowanym
         * obiekcie - zmierz roznice wzgledem parsowania OD NOWA za
         * kazdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementBeanReferenceStyleWithMapOfBeans {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj `@nazwaBeana` z prawdziwego Springa - Mapa "nazwa ->
         * obiekt" jako `setVariable` dla KAZDEGO wpisu, wywolaj metode
         * NA jednym z nich przez SpEL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementSpelExpressionWithMapAccess {
        /*
         * 🧪 Zadanie 16:
         * Uzyj SpEL do odczytu wartosci z `Map<String, Object>`
         * przekazanej jako zmienna (`#mapa['klucz']`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementSpelExpressionConstructingNewObject {
        /*
         * 🧪 Zadanie 17:
         * Uzyj SpEL do UTWORZENIA nowego obiektu (`new
         * java.util.ArrayList()`) i wywolania na nim metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_HandleSpelParseExceptionForInvalidSyntax {
        /*
         * 🧪 Zadanie 18:
         * Sparsuj CELOWO bledne wyrazenie SpEL - zlap i wypisz
         * `ParseException` z czytelnym komunikatem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareSpelPerformanceWithPlainJavaCode {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas 10 000 ewaluacji TEGO SAMEGO (sparsowanego)
         * wyrazenia SpEL vs 10 000 wywolan ROWNOWAZNEGO kodu Javy - jaki
         * jest narzut?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildSpelExpressionLibraryForCommonPatterns {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape `String -> String`) biblioteke GOTOWYCH,
         * czesto uzywanych wyrazen SpEL z tej lekcji - z krotkim opisem
         * KAZDEGO.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomPropertyAccessorForSpel {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `PropertyAccessor` (interfejs SpEL) -
         * pozwol wyrazeniom odczytywac "wirtualne" wlasciwosci NIE
         * bedace prawdziwymi polami/getterami klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCompiledSpelExpressionForHotPath {
        /*
         * 🧪 Zadanie 22:
         * Uzyj `SpelCompilerMode.IMMEDIATE` (kompilacja wyrazenia do
         * bajtkodu) - zmierz roznice wydajnosci wobec standardowej
         * interpretacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildRuleEngineUsingSpelExpressions {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj prosty "silnik regul" - lista Stringow-wyrazen SpEL
         * (reguly biznesowe) ewaluowanych PO KOLEI na obiekcie danych,
         * zbierajaca WYNIKI (np. "zamowienie kwalifikuje sie do rabatu?").
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSecuritySensitiveSpelSandbox {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: przedyskutuj RYZYKO bezpieczenstwa, gdyby
         * wyrazenie SpEL POCHODZILO od uzytkownika (niezaufany input) -
         * co MOZE pojsc zle (podpowiedz: SpEL moze wywolywac DOWOLNE
         * metody Javy)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementRestrictedEvaluationContextForUntrustedExpressions {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj OGRANICZONY `EvaluationContext` (np. WLASNY
         * `MethodResolver` blokujacy niebezpieczne metody) - zademonstruj
         * na przykladzie zablokowanego wywolania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareSpelWithOtherExpressionLanguages {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: porownaj SpEL z INNYMI jezykami wyrazen (np. OGNL,
         * MVEL, JSP EL) - co je LACZY konceptualnie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementTemplateEngineUsingSpelForPlaceholders {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj PROSTY "silnik szablonow" - tekst z placeholderami
         * `#{wyrazenie}` podmienianymi na wyniki ewaluacji SpEL (jak
         * `parser.parseExpression(text, ParserContext.TEMPLATE_EXPRESSION)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDynamicSortingWithSpelExpressions {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj sortowanie listy obiektow WEDLUG pola WYBRANEGO
         * dynamicznie w runtime (nazwa pola jako String -> wyrazenie SpEL
         * uzyte jako `Comparator`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ProfileSpelOverheadInHighFrequencyScenario {
        /*
         * 🧪 Zadanie 29:
         * Zasymuluj scenariusz WYSOKIEJ czestotliwosci (np. autoryzacja
         * KAZDEGO zadania HTTP przez wyrazenie SpEL, zapowiedz
         * `_24_spring_security/Lesson10`) - zmierz calkowity narzut przy
         * 100 000 "zadaniach".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSpelPoweredConfigurationEngineCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "silnik" konfiguracji oparty na SpEL -
         * odczyt reguł biznesowych jako wyrazenia z pliku, bezpieczna
         * (ograniczona) ewaluacja, cache sparsowanych wyrazen - jeden
         * spojny, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
