package com.example.javaquest._20_spring_core.Lesson02_SpringVersionsAndCompatibilityOverview;

public class _Exercises_Lesson02_SpringVersionsAndCompatibilityOverview {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ReadActualSpringBootVersionFromCode {
        /*
         * 🧪 Zadanie 1:
         * Uzyj `SpringBootVersion.getVersion()` i `SpringVersion.
         * getVersion()`, zeby wypisac dokladna wersje Springa uzywana w
         * tym projekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CheckPomXmlForSpringBootParentVersion {
        /*
         * 🧪 Zadanie 2:
         * Sprawdz w `pom.xml`, jaka wersje `spring-boot-starter-parent`
         * deklaruje ten projekt - porownaj z wynikiem Zadania 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainJavaxVsJakartaNamespaceChange {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij, SKAD wzielo sie przejscie z `javax.*`
         * na `jakarta.*` (podpowiedz: zmiana wlasciciela Jakarta EE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ListThreeSignalsOfOldSpringSecurityCode {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wymien 3 sygnaly w kodzie, ktore zdradzaja, ze
         * fragment Spring Security jest napisany pod STARSZA wersje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ClassifyFiveMoreCodeSnippets {
        /*
         * 🧪 Zadanie 5:
         * Rozbuduj `classifyTutorialSnippet` o 5 WLASNYCH przykladowych
         * fragmentow kodu (mieszanka starych/nowych) i sklasyfikuj je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhySpringBoot1IsEndOfLife {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij, co to znaczy, ze Spring Boot 1.x jest
         * "EOL" (end-of-life) - jakie to ma konsekwencje bezpieczenstwa?
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareExplicitVsImplicitConstructorInjection {
        /*
         * 🧪 Zadanie 7:
         * Napisz 2 klasy `@Component` z JEDNYM konstruktorem - jedna z
         * jawnym `@Autowired`, druga bez - zweryfikuj, ze OBIE dzialaja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TryImplicitInjectionWithTwoConstructors {
        /*
         * 🧪 Zadanie 8:
         * Dodaj DRUGI konstruktor do klasy z Zadania 7 (bez jawnego
         * `@Autowired` na zadnym) - zaobserwuj i zapisz blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListVersionMappingTableFromMemory {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: odtworz z pamieci mapowanie Spring Boot 1.x/
         * 2.x/3.x -> wersja Spring Framework -> wersja Javy -> namespace.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IdentifyWhichVersionThisCourseTargets {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: napisz JEDNYM zdaniem, jaka wersje Spring
         * Boot/Framework/Javy uczy ten kurs i skad to wiadomo.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_BuildOwnMechanismChangeTable {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj WLASNA liste rekordow `MechanismChange` z co najmniej 3
         * DODATKOWYMI mechanizmami (spoza tabeli w teorii), ktore
         * zmienily sie miedzy wersjami Springa - poszukaj w dokumentacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteMigrationGuideForOneSpecificMechanism {
        /*
         * 🧪 Zadanie 12:
         * Wybierz JEDEN mechanizm z tabeli (np. WebSecurityConfigurer
         * Adapter->SecurityFilterChain) i napisz krotki (5-10 linii)
         * przewodnik migracji "przed/po" z przykladowym kodem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DetectMixedOldAndNewApiInSameSnippet {
        /*
         * 🧪 Zadanie 13:
         * Rozbuduj `classifyTutorialSnippet`, zeby wykrywac MIESZANIE
         * starego i nowego API w JEDNYM fragmencie (np. `authorizeRequests`
         * razem z `SecurityFilterChain`) - oznacz to jako BLAD do naprawy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildVersionAwareCodeSnippetValidator {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj metode zwracajaca `List<String>` ostrzezen dla
         * fragmentu kodu (nie tylko JEDNA klasyfikacje) - fragment moze
         * miec WIELE sygnalow naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhySpringBoot26ChangedCircularRefDefault {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij, DLACZEGO Spring Boot 2.6 zmienil
         * domyslne zachowanie dla zaleznosci cyklicznych (podpowiedz:
         * cykle to zwykle blad projektowy, nie funkcja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareSpringFactoriesWithAutoConfigurationImports {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: porownaj mechanizmy `spring.factories` i
         * `AutoConfiguration.imports` - ktory jest bardziej "jawny/
         * odkrywalny" i dlaczego to zaleta?
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureContainerBehaviorDifferenceAcrossStyles {
        /*
         * 🧪 Zadanie 17:
         * Zweryfikuj (przez `context.getBeanDefinitionNames()`), ze
         * `LegacyStyleService` i `ModernStyleService` z teorii sa
         * zarejestrowane w kontenerze IDENTYCZNIE, mimo roznego stylu
         * adnotacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildTimelineOfSpringMajorReleases {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj (jako liste rekordow) chronologiczna os czasu glownych
         * wydan Spring Framework (1.x-6.x) i Spring Boot (1.x-3.x) z
         * przyblizonymi latami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainRiskOfCopyingOutdatedTutorialCode {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: opisz KONKRETNY scenariusz (np. kopiowanie
         * konfiguracji Security ze starego tutoriala), w ktorym brak
         * znajomosci tej mapy wersji kosztowalby godziny debugowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CrossReferenceEachTableRowWithItsFutureLesson {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: dla kazdego wiersza tabeli mechanizmow, zapisz,
         * W KTOREJ lekcji bloku rozdzialow poswieconych Springowi
         * spodziewasz sie pelnego wyjasnienia - sprawdzisz to pozniej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildAutomatedLegacyCodeScanner {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj skaner (wzorem `_16_clean_code/Lesson20`/
         * `_19_security_basics/Lesson18`) analizujacy WIELE "plikow"
         * (Stringow) na raz i generujacy raport, ktore z nich uzywaja
         * PRZESTARZALEGO API Springa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateGradualMigrationOfLargeCodebase {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj (na liscie "plikow" z mieszanym starym/nowym API)
         * STOPNIOWA migracje - ile "plikow" zostalo, ile juz
         * zmigrowanych - z raportem postepu w procentach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ResearchAndDocumentThreeMoreVersionDifferences {
        /*
         * 🧪 Zadanie 23:
         * Wyszukaj (w oficjalnej dokumentacji Spring) 3 KOLEJNE mechanizmy
         * (spoza tego kursu), ktore zmienily sie miedzy wersjami - udokumentuj
         * je w tym samym formacie co `MechanismChange`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildDecisionTreeForChoosingSpringBootVersion {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: zbuduj "drzewo decyzyjne" (jako tekst/ASCII) -
         * kiedy warto uzyc Boot 2.x (legacy Java 8, stary kod) a kiedy
         * ZAWSZE Boot 3.x (nowy projekt).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementVersionCompatibilityCheckAtStartup {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj metode, ktora PRZY STARCIE aplikacji sprawdza
         * (przez `SpringBootVersion.getVersion()`) czy wersja Springa
         * jest "wystarczajaco nowa" (np. >= 3.0) - rzuca czytelny wyjatek,
         * jesli nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareMigrationCostAcrossDifferentMechanisms {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: z tabeli mechanizmow oceń (1-5), KTORY z nich
         * jest NAJTRUDNIEJSZY do migracji w duzym, istniejacym projekcie -
         * uzasadnij.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildFullTutorialSnippetHealthReport {
        /*
         * 🧪 Zadanie 27:
         * Polacz Zadanie 14 (wiele ostrzezen) z Zadaniem 21 (skaner wielu
         * plikow) - zbuduj JEDEN raport "zdrowia wersji" dla calego
         * symulowanego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExplainSemanticVersioningInSpringEcosystem {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: wyjasnij, jak semantyczne wersjonowanie (major.
         * minor.patch) stosuje sie do Spring Boot (np. 3.4.4) - co
         * oznacza kazda czesc numeru?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PredictFutureDeprecationsBasedOnPattern {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: na podstawie WZORCA z tabeli (stary mechanizm
         * deprecated -> nowy wprowadzony rownolegle -> stary usuniety kilka
         * lat pozniej), przewidz, jak Spring MOZE podejsc do
         * przyszlych, duzych zmian API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteVersionAwarenessCapstoneTool {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne narzedzie laczace: odczyt realnej wersji
         * (Zadanie 1), tabele mechanizmow (Zadanie 11), skaner kodu
         * (Zadanie 21/27) i drzewo decyzyjne (Zadanie 24) - jeden spojny
         * raport "czy moj kod/projekt jest gotowy na Spring Boot 3.x?".
         */
        public static void main(String[] args) { }
    }
}
