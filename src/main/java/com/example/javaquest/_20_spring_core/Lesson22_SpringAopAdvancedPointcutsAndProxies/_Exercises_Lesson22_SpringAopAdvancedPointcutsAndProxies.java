package com.example.javaquest._20_spring_core.Lesson22_SpringAopAdvancedPointcutsAndProxies;

public class _Exercises_Lesson22_SpringAopAdvancedPointcutsAndProxies {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainExecutionPointcutSyntax {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: rozlóż na czesci skladnie
         * `execution(* com.example.Foo.*(..))` - co oznacza KAZDY
         * fragment?
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteExecutionPointcutForOwnClass {
        /*
         * 🧪 Zadanie 2:
         * Napisz WLASNE wyrazenie `execution(...)` dopasowujace
         * WSZYSTKIE metody WLASNEJ klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteExecutionPointcutForSpecificMethodName {
        /*
         * 🧪 Zadanie 3:
         * Napisz wyrazenie dopasowujace TYLKO metody zaczynajace sie od
         * "get" (`get*`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReproduceSelfInvocationTrap {
        /*
         * 🧪 Zadanie 4:
         * Odtworz pulapke self-invocation z teorii dla WLASNYCH 2 metod
         * TEJ SAMEJ klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixSelfInvocationByInjectingSelfProxy {
        /*
         * 🧪 Zadanie 5:
         * Napraw pulapke z Zadania 4 wstrzykujac `ApplicationContext` i
         * pobierajac SAMEGO SIEBIE jako proxy (`context.getBean(WlasnaKlasa.class)`)
         * ZAMIAST uzywac `this`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FixSelfInvocationBySplittingIntoTwoBeans {
        /*
         * 🧪 Zadanie 6:
         * Napraw pulapke z Zadania 4 INACZEJ - wydziel druga metode do
         * OSOBNEGO beana, wstrzyknietego przez konstruktor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementTwoOrderedAspects {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj 2 aspekty z `@Order` dla WLASNEJ metody -
         * zweryfikuj kolejnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReverseOrderAndObserveChange {
        /*
         * 🧪 Zadanie 8:
         * Zamien wartosci `@Order` z Zadania 7 - zweryfikuj, ze kolejnosc
         * wykonania TEZ sie zmienila.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyExecutionIsMoreFragileThanAnnotation {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego `execution(...)` (oparte na
         * nazwach pakietow/klas) jest BARDZIEJ podatne na "zepsucie sie"
         * przy refaktoryzacji niz `@annotation(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRealWorldSelfInvocationBugReports {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: opisz REALNY scenariusz (np. `@Transactional`
         * wywolane przez `this` w duzym serwisie), w ktorym pulapka
         * self-invocation spowodowalaby BLAD w produkcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementWithinPointcutForWholePackage {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `within(com.example..*)` (dopasowanie po PAKIECIE, nie
         * pojedynczej klasie) - zweryfikuj, ze obejmuje WIELE klas naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CombinePointcutsWithLogicalOperators {
        /*
         * 🧪 Zadanie 12:
         * Polacz 2 wyrazenia pointcut operatorem `||` (LUB) - zweryfikuj
         * dopasowanie do OBU niezaleznych przypadkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExcludeSpecificMethodWithNegation {
        /*
         * 🧪 Zadanie 13:
         * Uzyj operatora `!` (NIE) w wyrazeniu pointcut, zeby WYKLUCZYC
         * JEDNA konkretna metode z szerszego dopasowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementNamedPointcutReusedAcrossAdvices {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj `@Pointcut` jako OSOBNA, NAZWANA metode - uzyj JEJ w
         * WIELU roznych "radach" (`@Before`/`@After`) bez powtarzania
         * wyrazenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementThreeAspectsWithMixedOrdering {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj 3 aspekty z RÓZNYMI `@Order` (w tym JEDEN BEZ
         * `@Order` w ogole) - zweryfikuj, GDZIE trafia ten bez `@Order`
         * (najnizszy czy najwyzszy priorytet?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FixSelfInvocationWithAopContextCurrentProxy {
        /*
         * 🧪 Zadanie 16:
         * Napraw self-invocation przez `AopContext.currentProxy()`
         * (wymaga `exposeProxy=true`) - PORTOWNAJ z rozwiazaniami z
         * Zadania 5/6.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementAspectMatchingByReturnType {
        /*
         * 🧪 Zadanie 17:
         * Napisz wyrazenie `execution(...)` dopasowujace TYLKO metody
         * zwracajace KONKRETNY typ (np. `String`, nie `*`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementAspectMatchingByParameterType {
        /*
         * 🧪 Zadanie 18:
         * Napisz wyrazenie dopasowujace TYLKO metody przyjmujace
         * KONKRETNY typ parametru (nie `(..)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareProxyIdentityWithTargetIdentity {
        /*
         * 🧪 Zadanie 19:
         * Porownaj `System.identityHashCode(proxy)` z
         * `System.identityHashCode(AopProxyUtils.getSingletonTarget(proxy))` -
         * zweryfikuj, ze to DWA rozne obiekty w pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildPointcutSyntaxCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako Mape `String -> String`) "sciage" najczesciej
         * uzywanych wzorcow wyrazen pointcut z tej lekcji, z krotkim
         * opisem KAZDEGO.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCompleteFixForRealisticSelfInvocationScenario {
        /*
         * 🧪 Zadanie 21:
         * Zasymuluj REALISTYCZNY serwis (5+ metod, niektore wolajace inne
         * przez `this`) - zidentyfikuj WSZYSTKIE przypadki self-invocation
         * i napraw KAZDY WLASCIWA technika (Zadanie 5/6/16).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomPointcutDesignator {
        /*
         * 🧪 Zadanie 22:
         * Zbadaj (dokumentacja) czy Spring AOP wspiera WLASNE "pointcut
         * designators" - jesli TAK, zaimplementuj przyklad; jesli NIE,
         * wyjasnij dlaczego (ograniczenie proxy-based AOP).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureOrderingOverheadWithManyAspects {
        /*
         * 🧪 Zadanie 23:
         * Zarejestruj 10 aspektow (z `@Order`) na TEJ SAMEJ metodzie -
         * zmierz narzut wywolania wzgledem 1 aspektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementAspectDetectingSelfInvocationAutomatically {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj mechanizm (np. porownanie `this` z proxy przez
         * `AopContext`) WYKRYWAJACY W RUNTIME, ze metoda zostala wywolana
         * PRZEZ self-invocation - zaloguj OSTRZEZENIE zamiast po cichu
         * pomijac aspekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDeclareParentsInterIntroduction {
        /*
         * 🧪 Zadanie 25:
         * Zbadaj (dokumentacja) `@DeclareParents` (wprowadzanie NOWEGO
         * interfejsu do ISTNIEJACEGO beana przez AOP) - zaimplementuj
         * prosty przyklad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareSpringAopWithDecoratorPatternRefactoring {
        /*
         * 🧪 Zadanie 26:
         * Przepisz JEDEN z aspektow z tej lekcji jako RECZNY wzorzec
         * Decorator (BEZ Springa/AOP) - porownaj ILOSC kodu i ELASTYCZNOSC
         * (latwosc dodania KOLEJNEGO "przekroju").
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementConditionalAspectBasedOnProfile {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj aspekt DZIALAJACY TYLKO w okreslonym profilu
         * (Lesson15) - np. szczegolowe logowanie TYLKO w "dev".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAspectWithThreadSafeSharedState {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj aspekt zbierajacy STATYSTYKI (liczba wywolan,
         * sredni czas) WSPOLDZIELONE miedzy WATKAMI - zapewnij
         * bezpieczenstwo watkowe (`_05_multithreading`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DebugWhyAspectDoesNotFireChecklist {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj "checkliste diagnostyczna" (tekst) - najczestsze
         * przyczyny, DLACZEGO aspekt "nie dziala" (self-invocation,
         * brak @EnableAspectJAutoProxy, zla widocznosc metody, `final`
         * klasa/metoda, bean NIE zarzadzany przez Springa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteAopDiagnosticToolCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne narzedzie diagnostyczne AOP - wykrywanie typu
         * proxy (JDK/CGLIB), listowanie dopasowanych aspektow dla danego
         * beana, wykrywanie potencjalnych self-invocation (statyczna
         * analiza wywolan `this.`) - jeden spojny raport.
         */
        public static void main(String[] args) { }
    }
}
