package com.example.javaquest._25_unit_testing.Lesson18_TestNamingAndOrganizationBestPractices;

public class _Exercises_Lesson18_TestNamingAndOrganizationBestPractices {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RenameBadTestNameToDescriptiveOne {
        /*
         * 🧪 Zadanie 1:
         * Wez test nazwany `test1()` I ZMIEN nazwe NA opisowa (schemat
         * methodName_condition_expectedResult).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteTestWithMethodConditionResultNaming {
        /*
         * 🧪 Zadanie 2:
         * Napisz NOWY test uzywajacy schematu
         * `methodName_condition_expectedResult`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddDisplayNameToExistingTest {
        /*
         * 🧪 Zadanie 3:
         * Dodaj `@DisplayName` DO testu Z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_StructureTestWithArrangeActAssertComments {
        /*
         * 🧪 Zadanie 4:
         * Podziel test NA WYRAZNE sekcje ARRANGE/ACT/ASSERT
         * (komentarze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SplitTestCheckingTwoUnrelatedThingsIntoTwo {
        /*
         * 🧪 Zadanie 5:
         * Rozbij JEDEN test SPRAWDZAJACY 2 NIEZWIAZANE fakty NA DWA
         * OSOBNE testy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RemoveIfStatementFromTestBody {
        /*
         * 🧪 Zadanie 6:
         * Znajdz (I NAPRAW) test Z LOGIKA (`if`) W srodku - ZASTAP go
         * DWOMA prostymi testami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GroupTestsWithNestedByScenario {
        /*
         * 🧪 Zadanie 7:
         * Pogrupuj 4+ testow PRZEZ `@Nested` WEDLUG scenariusza
         * (powiazanie Z Lesson09).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareShouldStyleWithMethodConditionResultStyle {
        /*
         * 🧪 Zadanie 8:
         * Napisz TEN SAM test 2 stylami nazewnictwa ("should..." vs
         * "methodName_condition_result") - porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IdentifyPoorlyNamedTestInOwnPastWork {
        /*
         * 🧪 Zadanie 9:
         * Znajdz (W DOWOLNEJ WCZESNIEJSZEJ lekcji Z `_25_unit_testing`)
         * test Z SLABA nazwa I ZAPROPONUJ lepsza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteTestClassLevelDisplayNameForWholeSuite {
        /*
         * 🧪 Zadanie 10:
         * Dodaj `@DisplayName` NA POZIOMIE KLASY testowej opisujacej
         * CALY pakiet.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RefactorLargeTestClassIntoNestedGroups {
        /*
         * 🧪 Zadanie 11:
         * Wez DUZA (10+ testow) klase testowa I PODZIEL ja NA 3-4
         * grupy `@Nested`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteGivenWhenThenStyleTestSuite {
        /*
         * 🧪 Zadanie 12:
         * Napisz PAKIET testow W stylu "Given-When-Then" (BDD),
         * KAZDY Z 3 sekcjami W komentarzach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_EliminateDuplicationWithBeforeEachHelperMethod {
        /*
         * 🧪 Zadanie 13:
         * Znajdz POWTARZAJACY sie kod ARRANGE W kilku testach I
         * WYDZIEL go DO `@BeforeEach`/metody pomocniczej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteTestNamesThatSurviveImplementationChange {
        /*
         * 🧪 Zadanie 14:
         * Napisz nazwy testow OPISUJACE ZACHOWANIE (nie
         * implementacje) - zademonstruj, ze NAZWA POZOSTAJE aktualna
         * PO zmianie SPOSOBU implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_OrganizeTestsByFeatureNotByMethod {
        /*
         * 🧪 Zadanie 15:
         * Zorganizuj testy WEDLUG FUNKCJONALNOSCI (nie WEDLUG
         * metody-pod-testem) - powiazanie Z
         * `_17_architecture/Lesson09_PackageByFeature`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteSelfDocumentingAssertionMessages {
        /*
         * 🧪 Zadanie 16:
         * Dodaj WLASNE komunikaty (`.as("...")`) DO asercji AssertJ
         * (powiazanie Z Lesson05).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareTestNamingConventionsAcrossLanguages {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj konwencje nazewnicze testow W Javie
         * (JUnit) Z INNYMI jezykami (np. RSpec/Jest, KONCEPCYJNIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefactorTestWithMagicNumbersToNamedConstants {
        /*
         * 🧪 Zadanie 18:
         * Znajdz test Z "MAGICZNYMI liczbami" I ZASTAP je NAZWANYMI
         * stalymi (powiazanie Z `_16_clean_code/Lesson02_Naming`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteTestPlanDocumentBeforeImplementingTests {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj (jako LISTA nazw metod, BEZ implementacji) PELNY
         * plan testow DLA WLASNEJ klasy PRZED napisaniem kodu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReviewAndImproveTeammatesTestNaming {
        /*
         * 🧪 Zadanie 20:
         * Wez test NAPISANY W Lesson13 (Mockito) I OCEN JEGO
         * nazewnictwo - ZAPROPONUJ poprawki.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignTestNamingConventionDocumentForTeam {
        /*
         * 🧪 Zadanie 21:
         * Napisz (jako komentarz) PELNY DOKUMENT konwencji nazewniczej
         * DLA zespolu (przyklady, WYJATKI, ZASADY).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomDisplayNameGenerator {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj WLASNY `DisplayNameGenerator` (JUnit 5)
         * AUTOMATYCZNIE formatujacy nazwy metod NA czytelne zdania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RefactorEntireLegacyTestSuiteToBestPractices {
        /*
         * 🧪 Zadanie 23:
         * Wez CALY pakiet testow (np. Z Lesson13-15) I PRZEPISZ WEDLUG
         * WSZYSTKICH zasad Z tej lekcji NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementArchUnitLikeCheckForTestNamingConvention {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj PROSTA (WLASNA) WALIDACJE PRZEZ REFLEKSJE
         * SPRAWDZAJACA, ze WSZYSTKIE metody testowe W klasie PASUJA DO
         * wzorca nazewniczego (zapowiedz
         * `_27_spring_test/Lesson18_ArchUnit`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignTestOrganizationForMultiModuleProject {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj STRUKTURE PAKIETOW testow DLA hipotetycznego
         * projektu WIELOMODULOWEGO (powiazanie Z
         * `_17_architecture/Lesson17_ModularMonolith`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_WriteTestSuiteActingAsLivingDocumentation {
        /*
         * 🧪 Zadanie 26:
         * Napisz PAKIET testow, KTORY - CZYTANY OD GORY DO DOLU - MOZE
         * SLUZYC JAKO "zywa dokumentacja" zachowania klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasureReadabilityImprovementWithBeforeAfterComparison {
        /*
         * 🧪 Zadanie 27:
         * Pokaz KONKRETNY test PRZED/PO refaktoryzacji nazewnictwa I
         * organizacji - OPISZ (W komentarzu) KONKRETNA POPRAWE
         * czytelnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementTestSuiteHealthReportCountingViolations {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj "raport zdrowia" pakietu testow (PRZEZ
         * refleksje) - LICZACY naruszenia konwencji nazewniczej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignOnboardingGuideForNewTeamMemberAboutTestConventions {
        /*
         * 🧪 Zadanie 29:
         * Napisz (jako komentarz) KROTKI przewodnik "onboardingowy"
         * DLA nowego czlonka zespolu O konwencjach testow W tym
         * kursie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullExemplaryTestSuiteFollowingAllRulesFromLesson {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY, WZORCOWY pakiet testow (min. 8 testow, min. 2
         * grupy `@Nested`) STOSUJACY WSZYSTKIE zasady Z tej lekcji
         * JEDNOCZESNIE.
         */
        public static void main(String[] args) { }
    }
}
