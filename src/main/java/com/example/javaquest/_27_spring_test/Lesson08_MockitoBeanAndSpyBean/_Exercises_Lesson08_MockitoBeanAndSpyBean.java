package com.example.javaquest._27_spring_test.Lesson08_MockitoBeanAndSpyBean;

public class _Exercises_Lesson08_MockitoBeanAndSpyBean {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddMockitoBeanFieldToSpringBootTest {
        /* 🧪 Zadanie 1: Dodaj `@MockitoBean` DO WLASNEGO `@SpringBootTest`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_StubMockitoBeanAndVerifyCall {
        /* 🧪 Zadanie 2: Zaprogramuj `when(...)` NA `@MockitoBean` I zweryfikuj `verify(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseMockitoSpyBeanOnConcreteService {
        /* 🧪 Zadanie 3: Uzyj `@MockitoSpyBean` NA konkretnej klasie serwisowej. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhyMockBeanIsDeprecated {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij, DLACZEGO `@MockBean` jest DEPRECATED. */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyMockitoBeanReplacesRealBeanInContext {
        /* 🧪 Zadanie 5: Zweryfikuj (PRZEZ `ApplicationContext`), ze bean W kontekscie jest FAKTYCZNIE mockiem. */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseMockitoBeanInDataJpaTest {
        /* 🧪 Zadanie 6: Powiaz z Lesson06 - uzyj `@MockitoBean` W `@DataJpaTest`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareMockitoBeanWithManualMockFromChapter25 {
        /* 🧪 Zadanie 7: Bez terminala - porownaj `@MockitoBean` Z RECZNYM `mock(...)` Z `_25_unit_testing/Lesson13`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestExceptionThrownByMockitoBean {
        /* 🧪 Zadanie 8: Zaprogramuj `@MockitoBean` DO rzucania wyjatku I zweryfikuj PROPAGACJE. */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddSecondMockitoBeanForDifferentDependency {
        /* 🧪 Zadanie 9: Dodaj DRUGI `@MockitoBean` DLA INNEJ zaleznosci. */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentWhenToUseMockVsSpyBean {
        /* 🧪 Zadanie 10: Bez terminala - opisz, KIEDY `@MockitoBean`, a KIEDY `@MockitoSpyBean`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseMockitoBeanInWebMvcTestExplicitly {
        /* 🧪 Zadanie 11: Powiaz z Lesson05 - uzyj `@MockitoBean` W `@WebMvcTest` (JUZ DOMYSLNIE wymagane). */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestServiceWithMixOfRealAndMockedDependencies {
        /* 🧪 Zadanie 12: Zbuduj serwis Z 2 zaleznosciami - JEDNA PRAWDZIWA, DRUGA `@MockitoBean`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseArgumentCaptorWithMockitoBean {
        /* 🧪 Zadanie 13: Powiaz z `_25_unit_testing/Lesson14` - uzyj `ArgumentCaptor` NA `@MockitoBean`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestSpyBeanPartialStubbing {
        /* 🧪 Zadanie 14: Powiaz z `_25_unit_testing/Lesson16` - CZESCIOWO zastubuj `@MockitoSpyBean` (`doReturn`). */
        public static void main(String[] args) { }
    }

    static class Exercise15_VerifyNoInteractionsWithMockitoBeanOnErrorPath {
        /* 🧪 Zadanie 15: Zweryfikuj `verifyNoInteractions(...)` NA `@MockitoBean` W SCIEZCE bledu. */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestMockitoBeanResetBetweenTestMethods {
        /* 🧪 Zadanie 16: Zbadaj, CZY `@MockitoBean` jest AUTOMATYCZNIE resetowany MIEDZY metodami. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseMockitoBeanForExternalHttpClientDependency {
        /* 🧪 Zadanie 17: Powiaz z `_22_spring_web/Lesson17` - zamockuj `RestClient`/`WebClient` `@MockitoBean`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareMockitoBeanNameQualificationWithMultipleBeansOfSameType {
        /* 🧪 Zadanie 18: Zbadaj `@MockitoBean(name = "...")` PRZY WIELU beanach TEGO SAMEGO typu. */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestControllerAdviceInteractionWithMockedService {
        /* 🧪 Zadanie 19: Powiaz z `_22_spring_web/Lesson09` - przetestuj `@ControllerAdvice` PRZY wyjatku Z `@MockitoBean`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullServiceLayerTestSuiteUsingMockitoBean {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet testow warstwy serwisowej uzywajac `@MockitoBean`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomBeanOverrideStrategy {
        /* 🧪 Zadanie 21: Zbadaj mechanizm `BeanOverride` (Spring Framework 6.2+) POD `@MockitoBean`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestContextCachingImpactOfMockitoBeanUsage {
        /* 🧪 Zadanie 22: Powiaz z Lesson17 - zbadaj, JAK `@MockitoBean` WPLYWA NA cache'owanie kontekstu. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementMigrationFromDeprecatedMockBeanToMockitoBean {
        /* 🧪 Zadanie 23: Napisz (PRZED/PO) migracje `@MockBean` -> `@MockitoBean` DLA przykladowego testu. */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestSpyBeanWithSelfInvocationPitfall {
        /* 🧪 Zadanie 24: Powiaz z `_20_spring_core/Lesson22` - zademonstruj pulapke self-invocation NA `@MockitoSpyBean`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildHybridTestUsingMockitoBeanAndWireMock {
        /* 🧪 Zadanie 25: Powiaz z `_26_integration_testing/Lesson07` - polacz `@MockitoBean` (wewnetrzne) Z WireMock (zewnetrzne). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCustomQualifierBasedMockitoBeanSelection {
        /* 🧪 Zadanie 26: Zbadaj wybor `@MockitoBean` PRZY WIELU implementacjach interfejsu (`@Qualifier`). */
        public static void main(String[] args) { }
    }

    static class Exercise27_TestMockitoBeanInteractionAcrossMultipleTestClassesSharingContext {
        /* 🧪 Zadanie 27: Powiaz z Lesson17 - zbadaj zachowanie `@MockitoBean` PRZY WSPOLDZIELONYM kontekscie. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignFullMockingStrategyDecisionMatrixForLargeApp {
        /* 🧪 Zadanie 28: Zaprojektuj MACIERZ decyzyjna (`_25` mock vs `@MockitoBean` vs `@MockitoSpyBean` vs WireMock). */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementBeanOverrideForNonMockitoScenario {
        /* 🧪 Zadanie 29: Zbadaj INNE mechanizmy `BeanOverride` (`@TestBean`) POZA Mockito. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullTestingGuidelinesForMockitoBeanUsageInTeam {
        /* 🧪 Zadanie 30: Zaprojektuj PELNE wytyczne uzycia `@MockitoBean`/`@MockitoSpyBean` DLA zespolu. */
        public static void main(String[] args) { }
    }
}
