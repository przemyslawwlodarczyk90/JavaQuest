package com.example.javaquest._25_unit_testing.Lesson05_AssertJIntroduction;

public class _Exercises_Lesson05_AssertJIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAssertThatEntryPoint {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, DLACZEGO `assertThat(x)` jest
         * JEDNYM, uniwersalnym punktem wejscia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteStringAssertionChain {
        /*
         * 🧪 Zadanie 2:
         * Napisz test Z LANCUCHEM co najmniej 4 metod NA Stringu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteNumericAssertionChain {
        /*
         * 🧪 Zadanie 3:
         * Napisz test Z LANCUCHEM NA liczbie (`isPositive`,
         * `isLessThan`, `isBetween`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareWithBuiltInAssertEquals {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: przepisz `assertEquals(5, wynik)` (Lesson03)
         * NA `assertThat(wynik).isEqualTo(5)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseAsDescriptionForCustomMessage {
        /*
         * 🧪 Zadanie 5:
         * Uzyj `.as("opis")` W teście - zweryfikuj, ze WLASNY opis
         * POJAWIA SIE W komunikacie PRZY porazce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteAssertThatThrownByTest {
        /*
         * 🧪 Zadanie 6:
         * Napisz test `assertThatThrownBy` sprawdzajacy typ I
         * komunikat wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteFailingAssertJTestAndReadMessage {
        /*
         * 🧪 Zadanie 7:
         * Napisz CELOWO nieudany test AssertJ - porownaj CZYTELNOSC
         * komunikatu Z wbudowanym `assertEquals` (Lesson03).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseIsNullAndIsNotNull {
        /*
         * 🧪 Zadanie 8:
         * Napisz test uzywajacy `.isNull()`/`.isNotNull()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseIsEqualToIgnoringCase {
        /*
         * 🧪 Zadanie 9:
         * Napisz test porownujacy Stringi BEZ wzgledu NA wielkosc
         * liter.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFiveStringSpecificMethods {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz 5 metod DOSTEPNYCH TYLKO DLA
         * `assertThat(String)` (NIE DLA liczb).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteChainedNumericRangeAssertion {
        /*
         * 🧪 Zadanie 11:
         * Napisz test Z `.isBetween(min, max)` + `.isNotZero()` NA
         * WYNIKU obliczen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseIsInstanceOfWithGenerics {
        /*
         * 🧪 Zadanie 12:
         * Napisz test sprawdzajacy `.isInstanceOf(...)` NA obiekcie
         * polimorficznym (powiazanie Z `_02_oop/Lesson06`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseHasFieldOrPropertyWithValue {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `.hasFieldOrPropertyWithValue(...)` DO sprawdzenia
         * pola WEWNATRZ obiektu (BEZ pisania getterow W tescie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseUsingRecursiveComparison {
        /*
         * 🧪 Zadanie 14:
         * Uzyj `.usingRecursiveComparison().isEqualTo(...)` DO
         * porownania DWOCH obiektow POLE-PO-POLU (BEZ nadpisywania
         * `equals()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ChainExceptionAssertionWithCause {
        /*
         * 🧪 Zadanie 15:
         * Napisz test sprawdzajacy `.hasCauseInstanceOf(...)` DLA
         * ZAGNIEZDZONEGO ("chained") wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareAssertJReadabilityWithBuiltIn {
        /*
         * 🧪 Zadanie 16:
         * Napisz TA SAMA weryfikacje (3 warunki NA obiekcie) OBIEMA
         * metodami (wbudowana I AssertJ) - porownaj LICZBE linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseSatisfiesForComplexAssertion {
        /*
         * 🧪 Zadanie 17:
         * Uzyj `.satisfies(lambda)` DO WLASNEJ, ZLOZONEJ logiki
         * sprawdzania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ChainMultipleObjectPropertyChecks {
        /*
         * 🧪 Zadanie 18:
         * Napisz test sprawdzajacy 5 WLASCIWOSCI obiektu domenowego
         * (np. `Person`) W JEDNYM lancuchu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_UseIsCloseToForFloatingPoint {
        /*
         * 🧪 Zadanie 19:
         * Uzyj `.isCloseTo(oczekiwana, within(delta))` DO porownania
         * `double` (powiazanie Z Lesson03 Zadanie 24).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignAssertionForCustomDomainObject {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj WLASNA klase domenowa I NAPISZ test AssertJ
         * sprawdzajacy JEJ stan PO operacji biznesowej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomAssertClass {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNA klase `PersonAssert extends
         * AbstractAssert<PersonAssert, Person>` Z WLASNYMI metodami
         * (zapowiedz Lesson07).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RegisterCustomEntryPoint {
        /*
         * 🧪 Zadanie 22:
         * Dodaj WLASNA statyczna metode `assertThat(Person)` (PRZECIAZENIE)
         * zwracajaca `PersonAssert` Z Exercise21.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareAssertJWithHamcrestSyntax {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: napisz (W komentarzu) TA SAMA asercje
         * SKLADNIA Hamcrest (`assertThat(x, is(equalTo(y)))`) I
         * porownaj Z AssertJ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSoftAssertionsFromAssertJ {
        /*
         * 🧪 Zadanie 24:
         * Uzyj WBUDOWANEGO `SoftAssertions` AssertJ (ZAMIAST WLASNEJ
         * implementacji Z Lesson03 Zadanie 27) - zweryfikuj, ze
         * WSZYSTKIE bledy SA zebrane NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BenchmarkAssertJVsBuiltInPerformance {
        /*
         * 🧪 Zadanie 25:
         * Zmierz narzut AssertJ (tworzenie obiektu `assertThat(...)`)
         * WZGLEDEM `Assertions.assertEquals` NA 100000 wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCustomComparatorForAssertJ {
        /*
         * 🧪 Zadanie 26:
         * Uzyj `.usingComparator(...)` DO porownania WEDLUG WLASNEJ
         * logiki (np. porownanie DAT BEZ czasu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementExtractingForNestedProperties {
        /*
         * 🧪 Zadanie 27:
         * Uzyj `.extracting("pole.zagniezdzone")` DO sprawdzenia
         * GLEBOKO zagniezdzonej wlasciwosci BEZ recznego wywolywania
         * getterow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAssertJExtensionForDomainLanguage {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj MINI "jezyk domenowy" (DSL) NA bazie WLASNYCH
         * asercji (np. `assertThatOrder(order).isPaid().hasItems(3)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareVerboseOutputConfiguration {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zbadaj (dokumentacja) opcje konfiguracji
         * SZCZEGOLOWOSCI komunikatow AssertJ (np. `Assertions.
         * setMaxLengthForSingleLineDescription`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullAssertJShowcaseAcrossAllTypes {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj JEDNA klase testowa POKRYWAJACA WSZYSTKIE typy AssertJ
         * poznane W tej lekcji (String/liczba/boolean/wyjatek/obiekt
         * domenowy) NA JEDNYM, SPOJNYM scenariuszu.
         */
        public static void main(String[] args) { }
    }
}
