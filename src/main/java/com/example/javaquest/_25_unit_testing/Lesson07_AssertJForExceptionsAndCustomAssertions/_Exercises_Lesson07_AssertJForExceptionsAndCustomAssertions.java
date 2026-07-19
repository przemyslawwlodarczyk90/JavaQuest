package com.example.javaquest._25_unit_testing.Lesson07_AssertJForExceptionsAndCustomAssertions;

public class _Exercises_Lesson07_AssertJForExceptionsAndCustomAssertions {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteAssertThatThrownByTest {
        /*
         * 🧪 Zadanie 1:
         * Napisz test `assertThatThrownBy` DLA WLASNEGO wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteAssertThatExceptionOfTypeTest {
        /*
         * 🧪 Zadanie 2:
         * Napisz TEN SAM test stylem `assertThatExceptionOfType`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteCatchThrowableTest {
        /*
         * 🧪 Zadanie 3:
         * Napisz test uzywajacy `catchThrowable` I 2 OSOBNYCH asercji
         * NA przechwyconym wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainThreeExceptionStyles {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij, KIEDY wybrac KTORY Z 3 stylow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteAssertThatNoExceptionTest {
        /*
         * 🧪 Zadanie 5:
         * Napisz test `assertThatNoException()` DLA operacji, KTORA
         * NIE powinna rzucac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteHasMessageContainingTest {
        /*
         * 🧪 Zadanie 6:
         * Napisz test `.hasMessageContaining(...)` NA WLASNYM wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteHasNoCauseTest {
        /*
         * 🧪 Zadanie 7:
         * Napisz test `.hasNoCause()` DLA wyjatku BEZ przyczyny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteBasicCustomAssertClass {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj WLASNA klase `extends AbstractAssert` DLA
         * WLASNEGO rekordu (1 metoda sprawdzajaca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestCustomAssertionSuccess {
        /*
         * 🧪 Zadanie 9:
         * Napisz test UDANY, wywolujacy WLASNA asercje Z Zadania 8.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TestCustomAssertionFailure {
        /*
         * 🧪 Zadanie 10:
         * Napisz test CELOWO NIEUDANY - odczytaj WLASNY komunikat Z
         * `failWithMessage`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddSecondMethodToCustomAssertClass {
        /*
         * 🧪 Zadanie 11:
         * Dodaj DRUGA metode sprawdzajaca DO WLASNEJ klasy asercji Z
         * Zadania 8 - zweryfikuj LANCUCHOWANIE (`.metoda1().metoda2()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteExceptionAssertionWithCause {
        /*
         * 🧪 Zadanie 12:
         * Napisz test sprawdzajacy `.hasCauseInstanceOf(...)` DLA
         * wyjatku Z PRZYCZYNA (owiniety wyjatek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteExceptionAssertionForCheckedException {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_01_fundamentals/Lesson16` - napisz test DLA
         * CHECKED wyjatku (`Exception`, NIE `RuntimeException`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomAssertWithMultipleFields {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz WLASNA klase asercji O sprawdzenie 3 ROZNYCH pol
         * obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareCustomAssertWithHasFieldOrProperty {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: porownaj WLASNA asercje (Zadanie 14) Z
         * generycznym `.hasFieldOrPropertyWithValue(...)` (Lesson05).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementIsInStateAssertion {
        /*
         * 🧪 Zadanie 16:
         * Dodaj metode `isInState(Stan)` DO WLASNEJ klasy asercji DLA
         * obiektu Z maszyna stanow (powiazanie Z `_02_oop/Lesson13`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteExceptionAssertionMatchingRegex {
        /*
         * 🧪 Zadanie 17:
         * Napisz test `.hasMessageMatching(regex)` NA komunikacie
         * wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCustomAssertForCollection {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj WLASNA klase asercji DLA `List<Order>` (np.
         * `.hasTotalValueOf(kwota)` SUMUJACA WSZYSTKIE zamowienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestExceptionThrownFromNestedMethodCall {
        /*
         * 🧪 Zadanie 19:
         * Napisz test sprawdzajacy wyjatek RZUCONY Z GLEBOKO
         * zagniezdzonego wywolania metody (A->B->C rzuca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignAssertionLibraryForOwnDomain {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj (I NAPISZ) 3 WLASNE klasy asercji DLA WLASNEJ
         * domeny (np. `OrderAssert`/`CustomerAssert`/`ProductAssert`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementAssertionsEntryPointClass {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj WLASNA klase `MyAssertions` (statyczne fabryki DLA
         * WSZYSTKICH WLASNYCH asercji) - wzorem `org.assertj.core.api.
         * Assertions`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementSoftAssertionsForCustomAssertClass {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz WLASNA klase asercji O wsparcie DLA
         * `SoftAssertions` (zbieranie WIELU niepowodzen NARAZ).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementRecursiveCustomAssertForGraph {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj WLASNA asercje SPRAWDZAJACA CALY graf obiektow
         * (Order->OrderLine->Product) W JEDNYM lancuchu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareCustomAssertionsWithBddMockitoStyle {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: powiaz z Lesson13 (Mockito) - porownaj STYL
         * WLASNYCH asercji Z stylem BDD (`given/when/then`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAssertionErrorWithDiffOutput {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WLASNA asercje pokazujaca CZYTELNY "diff"
         * (oczekiwane vs rzeczywiste) W komunikacie bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignAssertionsForStateMachine {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_02_oop/Lesson13_Enums` - zaprojektuj WLASNA
         * asercje SPRAWDZAJACA DOZWOLONE przejscia MIEDZY stanami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementExceptionAssertionForRetryLogic {
        /*
         * 🧪 Zadanie 27:
         * Napisz test sprawdzajacy, ze OPERACJA Z retry RZUCA wyjatek
         * DOPIERO PO WSZYSTKICH probach (powiazanie Z
         * `_26_integration_testing/Lesson12`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BenchmarkCustomAssertionOverhead {
        /*
         * 🧪 Zadanie 28:
         * Zmierz narzut WLASNEJ klasy asercji WZGLEDEM bezposredniego
         * sprawdzenia pol (`if` + wyjatek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFluentAssertionsForBuilderPattern {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_02_oop/Lesson15_DesignPatterns` - zaprojektuj
         * WLASNA asercje SPRAWDZAJACA obiekt zbudowany PRZEZ Builder.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullCustomAssertionFrameworkForDomain {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY, SPOJNY "framework" WLASNYCH asercji DLA
         * REALISTYCZNEJ domeny (min. 3 klasy asercji, min. 3 metody
         * KAZDA) I NAPISZ demo-test UZYWAJACY WSZYSTKICH.
         */
        public static void main(String[] args) { }
    }
}
