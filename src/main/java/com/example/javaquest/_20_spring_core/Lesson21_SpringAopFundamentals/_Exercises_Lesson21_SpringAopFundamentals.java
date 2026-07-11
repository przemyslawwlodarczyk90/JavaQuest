package com.example.javaquest._20_spring_core.Lesson21_SpringAopFundamentals;

public class _Exercises_Lesson21_SpringAopFundamentals {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainCrossCuttingConcernsInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij czym sa "cross-cutting concerns" -
         * podaj 3 przyklady spoza tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnAnnotationAndAspect {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNA adnotacje znacznikowa i `@Aspect` z
         * `@Before` reagujacym na nia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementAfterReturningLoggingResult {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj `@AfterReturning` logujace WYNIK metody z
         * Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementAfterThrowingLoggingException {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj `@AfterThrowing` logujace wyjatek z metody, ktora
         * czasem rzuca blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementAroundMeasuringExecutionTime {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj `@Around` mierzace czas wykonania WLASNEJ metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ForgetToCallProceedAndObserveBrokenBehavior {
        /*
         * 🧪 Zadanie 6:
         * Celowo NIE wywolaj `joinPoint.proceed()` w `@Around` -
         * zaobserwuj, ze ORYGINALNA metoda W OGOLE sie nie wykonuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareJdkProxyClassNameWithOriginal {
        /*
         * 🧪 Zadanie 7:
         * Wypisz `getClass().getName()` beana z interfejsem (JDK proxy) -
         * porownaj z nazwa oryginalnej klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareCglibProxyClassNameWithOriginal {
        /*
         * 🧪 Zadanie 8:
         * To samo dla beana BEZ interfejsu (CGLIB) - porownaj nazwy klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyAroundIsMostPowerful {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego `@Around` jest
         * NAJPOTEZNIEJSZYM (i najbardziej ryzykownym) rodzajem "rady".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRealSpringAnnotationsBasedOnAop {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wymien co najmniej 3 wbudowane adnotacje
         * Springa dzialajace dzieki AOP (poza `@Transactional`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementMultipleAdviceTypesOnSameMethod {
        /*
         * 🧪 Zadanie 11:
         * Polacz WSZYSTKIE 4 rodzaje "rady" (before/afterReturning/
         * afterThrowing/after) na TEJ SAMEJ metodzie - zweryfikuj
         * kolejnosc wykonania dla sukcesu I dla wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementAspectModifyingMethodArguments {
        /*
         * 🧪 Zadanie 12:
         * W `@Around` ZMIEN argumenty PRZED wywolaniem `proceed(nowe
         * argumenty)` - zweryfikuj, ze metoda dostala ZMODYFIKOWANE dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementAspectModifyingReturnValue {
        /*
         * 🧪 Zadanie 13:
         * W `@Around` ZMIEN WYNIK metody PO `proceed()` (np. dodaj
         * prefiks do zwracanego Stringa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementAspectSuppressingException {
        /*
         * 🧪 Zadanie 14:
         * W `@Around` ZLAP wyjatek RZUCONY przez `proceed()` i zwroc
         * BEZPIECZNA wartosc domyslna ZAMIAST propagowac wyjatek dalej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCachingAspectManually {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj PROSTY aspekt cache'ujacy (Mapa argument->wynik) -
         * WLASNORECZNA wersja `@Cacheable`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRetryAspect {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj aspekt PONAWIAJACY wywolanie metody (N razy) w
         * `@Around`, jesli rzuci ona wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareBeanTargetClassWithProxyClass {
        /*
         * 🧪 Zadanie 17:
         * Uzyj `AopUtils.getTargetClass(bean)` zeby uzyskac ORYGINALNA
         * klase spod proxy - porownaj z `bean.getClass()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ForceCglibProxyExplicitly {
        /*
         * 🧪 Zadanie 18:
         * Uzyj `@EnableAspectJAutoProxy(proxyTargetClass = true)` dla
         * beana Z interfejsem - zweryfikuj, ze TERAZ dostaje CGLIB, nie
         * JDK proxy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementAspectAccessingMethodArgumentsByName {
        /*
         * 🧪 Zadanie 19:
         * Uzyj `args(nazwaParametru)` w wyrazeniu pointcut, zeby
         * DOPASOWAC TYLKO wywolania z KONKRETNA wartoscia/typem
         * argumentu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildAdviceTypeComparisonTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza 5 typow "rady" -
         * kolumny: nazwa, KIEDY sie wykonuje, czy MOZE zmienic wynik/
         * zablokowac wywolanie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementDistributedTracingStyleAspect {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj aspekt generujacy "trace ID" (UUID) dla KAZDEGO
         * wywolania i propagujacy go (przez `ThreadLocal`) do WSZYSTKICH
         * zagniezdzonych wywolan w tym samym watku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRateLimitingAspect {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj aspekt OGRANICZAJACY liczbe wywolan metody w
         * oknie czasowym (powiaz z `_18_rest_api/Lesson16_
         * RateLimitingAndThrottling`) - rzuc wyjatek po przekroczeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureAopProxyOverheadAtScale {
        /*
         * 🧪 Zadanie 23:
         * Zmierz narzut wywolania metody PRZEZ proxy AOP (JDK i CGLIB
         * osobno) vs wywolania BEZPOSREDNIEGO - 1 000 000 wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomPointcutExpressionCombiningConditions {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj zlozone wyrazenie pointcut LACZACE `@annotation(...)`
         * ORAZ `execution(...)` operatorem `&&` - zweryfikuj precyzyjne
         * dopasowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementSecurityCheckAspectSimulation {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj aspekt symulujacy autoryzacje (jak
         * `@PreAuthorize` z `_24_spring_security/Lesson10`) - sprawdza
         * WLASNA adnotacje `@RequireRole("ADMIN")` PRZED wywolaniem metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareAspectjWeavingWithSpringProxyBasedAop {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wyjasnij roznice miedzy PELNYM tkaniem AspectJ
         * (compile-time/load-time weaving) a proxy-based AOP Springa
         * (runtime, TYLKO na beanach kontenera) - jakie sa OGRANICZENIA
         * tego drugiego (np. wywolania PRYWATNYCH metod, `final` klas)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementAspectHandlingMultipleAnnotationTypes {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj JEDEN aspekt reagujacy na WIELE roznych adnotacji
         * (np. `@Logged`, `@Timed`, `@Audited`) - ROZNE zachowanie w
         * zaleznosci od tego, KTORA adnotacja jest obecna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementAopBasedValidationFramework {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj MINI-framework walidacji przez AOP - `@Around` czytajacy
         * WLASNE adnotacje na PARAMETRACH metody (przez refleksje) i
         * walidujacy je PRZED `proceed()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ExplainWhyFinalMethodsCannotBeAdvised {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj metode `final` z adnotacja aspektu - zaobserwuj,
         * ze aspekt SIE NIE URUCHAMIA (dla CGLIB) - wyjasnij DLACZEGO
         * (podpowiedz: CGLIB tworzy PODKLASE, nie moze NADPISAC `final`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteObservabilityAspectSuite {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny zestaw aspektow "observability" - logowanie +
         * pomiar czasu + licznik wywolan + wykrywanie bledow - dla
         * WIELU serwisow naraz, z jednym, spojnym raportem na koniec.
         */
        public static void main(String[] args) { }
    }
}
