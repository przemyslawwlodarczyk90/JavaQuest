package com.example.javaquest._27_spring_test.Lesson01_SpringTestModuleOverview;

public class _Exercises_Lesson01_SpringTestModuleOverview {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteTestUsingSpringExtensionAndContextConfiguration {
        /* 🧪 Zadanie 1: Napisz test `@ExtendWith(SpringExtension.class)` + `@ContextConfiguration`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AutowireApplicationContextAndAssertBeanCount {
        /* 🧪 Zadanie 2: Wstrzyknij `ApplicationContext` I zweryfikuj LICZBE beanow. */
        public static void main(String[] args) { }
    }

    static class Exercise03_AutowireCustomBeanFromMinimalApp {
        /* 🧪 Zadanie 3: Dodaj WLASNY `@Bean` DO `MinimalApp` I wstrzyknij go DO testu. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhatSpringBootStarterTestIncludes {
        /* 🧪 Zadanie 4: Bez terminala - wymien WSZYSTKIE biblioteki WCHODZACE W sklad `spring-boot-starter-test`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareRawSpringExtensionWithSpringBootTest {
        /* 🧪 Zadanie 5: Bez terminala - porownaj `@ExtendWith(SpringExtension.class)` Z `@SpringBootTest` (zapowiedz Lesson02). */
        public static void main(String[] args) { }
    }

    static class Exercise06_PrintAllBeanNamesFromContext {
        /* 🧪 Zadanie 6: Wypisz WSZYSTKIE nazwy beanow Z `context.getBeanDefinitionNames()`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyAutoConfigurationRanEvenWithoutSpringApplicationRun {
        /* 🧪 Zadanie 7: Zweryfikuj, ze auto-konfiguracja DZIALA NAWET BEZ `SpringApplication.run(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteFailingTestAndReadSpringErrorReport {
        /* 🧪 Zadanie 8: CELOWO zepsuj konfiguracje I odczytaj DIAGNOSTYCZNY raport Springa. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyTestsLiveInSrcMainJavaInThisCourse {
        /* 🧪 Zadanie 9: Bez terminala - przypomnij (Z `_25_unit_testing`), DLACZEGO testy tego kursu ZYJA W `src/main/java`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFourTestSliceAnnotationsByName {
        /* 🧪 Zadanie 10: Wymien 4 "wycinki" testowe (`@WebMvcTest`/`@DataJpaTest`/itd., zapowiedz Lesson04). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CompareContextLoadedBySpringExtensionWithAndWithoutAutoConfig {
        /* 🧪 Zadanie 11: Porownaj LICZBE beanow Z I BEZ `@EnableAutoConfiguration` NA klasie konfiguracyjnej. */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteTestUsingMultipleConfigurationClasses {
        /* 🧪 Zadanie 12: Uzyj `@ContextConfiguration(classes = {A.class, B.class})` Z 2 klasami. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExploreApplicationContextHierarchyMethods {
        /* 🧪 Zadanie 13: Zbadaj `context.getParent()`/`getId()`/`getStartupDate()`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteTestVerifyingProfileSpecificBeanNotLoadedByDefault {
        /* 🧪 Zadanie 14: Powiaz z `_20_spring_core/Lesson15` - zweryfikuj, ze bean Z `@Profile("dev")` NIE laduje sie BEZ aktywacji. */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureContextStartupTimeForMinimalApp {
        /* 🧪 Zadanie 15: Zmierz CZAS uruchomienia minimalnego kontekstu Spring. */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteTestUsingImportAnnotationInsteadOfContextConfiguration {
        /* 🧪 Zadanie 16: Zbadaj alternatywe `@Import` DO dodania konfiguracji DO klasy testowej. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareJUnit4SpringJUnit4ClassRunnerWithJUnit5SpringExtension {
        /* 🧪 Zadanie 17: Bez terminala - porownaj `SpringJUnit4ClassRunner` (JUnit4) Z `SpringExtension` (JUnit5). */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteTestAccessingEnvironmentBean {
        /* 🧪 Zadanie 18: Wstrzyknij `Environment` I odczytaj WLASCIWOSC systemowa. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExploreApplicationContextInitializerConcept {
        /* 🧪 Zadanie 19: Bez terminala - zbadaj rola `ApplicationContextInitializer` W testach. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignMinimalTestSetupForSimpleSpringLibraryModule {
        /* 🧪 Zadanie 20: Zaprojektuj MINIMALNY setup testowy DLA prostego modulu bibliotecznego Springa. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomTestExecutionListener {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `TestExecutionListener` (Spring TestContext Framework). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomContextCustomizerFactory {
        /* 🧪 Zadanie 22: Zbadaj `ContextCustomizerFactory` DO PROGRAMOWEJ modyfikacji kontekstu testowego. */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareContextCachingBehaviorAcrossDifferentConfigClasses {
        /* 🧪 Zadanie 23: Powiaz z Lesson17 - porownaj cache'owanie kontekstu DLA ROZNYCH klas konfiguracyjnych. */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildMetaAnnotationCombiningSpringExtensionAndContextConfiguration {
        /* 🧪 Zadanie 24: Zbuduj WLASNA meta-adnotacje LACZACA `@ExtendWith`+`@ContextConfiguration`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDynamicPropertySourceForRawSpringExtensionTest {
        /* 🧪 Zadanie 25: Powiaz z Lesson11 - zbadaj `@DynamicPropertySource` NA "surowym" tescie SpringExtension. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DebugContextLoadFailureWithVerboseLogging {
        /* 🧪 Zadanie 26: Wlacz VERBOSE logowanie ladowania kontekstu I przeanalizuj wyjscie. */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareSpringTestModuleVersionsAcrossBootVersions {
        /* 🧪 Zadanie 27: Bez terminala - powiaz z `_20_spring_core/Lesson02` - opisz ROZNICE `spring-test` W Boot 2 vs Boot 3. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementReusableBaseTestClassWithSpringExtension {
        /* 🧪 Zadanie 28: Zbuduj WSPOLNA klase bazowa `@ExtendWith(SpringExtension.class)` DLA WIELU klas testowych. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ExploreBootstrapWithAnnotationForCustomTestContextBootstrapper {
        /* 🧪 Zadanie 29: Zbadaj `@BootstrapWith` DO WLASNEGO `TestContextBootstrapper`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullOverviewDocumentOfSpringTestEcosystemForTeam {
        /* 🧪 Zadanie 30: Napisz PELNY dokument przegladowy ekosystemu Spring Test DLA zespolu. */
        public static void main(String[] args) { }
    }
}
