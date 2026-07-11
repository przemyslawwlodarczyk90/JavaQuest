package com.example.javaquest._20_spring_core.Lesson11_FieldInjectionWhyAvoid;

public class _Exercises_Lesson11_FieldInjectionWhyAvoid {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListFourProblemsWithFieldInjection {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 4 problemy field injection pokazane w
         * tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhyFinalFieldCannotBeAutowired {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij DOKLADNA sekwencje dzialan Springa,
         * przez ktora pole `@Autowired` nie moze byc `final`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateFieldInjectedClassAndInspectConstructor {
        /*
         * 🧪 Zadanie 3:
         * Utworz WLASNA klase z field injection - wypisz jej
         * konstruktor(y) przez refleksje (jak w teorii).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TriggerNpeWithoutSpringContainer {
        /*
         * 🧪 Zadanie 4:
         * Utworz obiekt z Zadania 3 przez `new` i wywolaj metode
         * uzywajaca niezainicjalizowanego pola - zapisz DOKLADNY
         * wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConvertFieldInjectionToConstructorInjection {
        /*
         * 🧪 Zadanie 5:
         * Przepisz klase z Zadania 3 na constructor injection - zweryfikuj,
         * ze test z Zadania 4 (teraz z `new` + fake zaleznoscia) DZIALA
         * BEZ wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReproduceCircularFieldInjectionDemo {
        /*
         * 🧪 Zadanie 6:
         * Odtworz demo cyklicznej zaleznosci przez field injection z
         * teorii dla WLASNYCH 2 klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyCircularDependencyIsUsuallyBadDesign {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, dlaczego 2 klasy zalezne od siebie
         * NAWZAJEM to zwykle SYGNAL zlego podzialu odpowiedzialnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListSituationsWhereFieldInjectionStillAppears {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wymien miejsca, gdzie field injection WCIAZ
         * czesto sie pojawia (stare tutoriale, testy JUnit z `@MockBean`
         * itd.) - dlaczego tam jest bardziej tolerowany?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareIdeAutocompleteExperienceAcrossStyles {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: opisz, jak IDE (autouzupelnianie) "pomaga"
         * inaczej przy tworzeniu obiektu przez `new` dla klasy z field
         * injection vs constructor injection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IdentifyFieldInjectionInGivenCodeSnippet {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: majac 3 przykladowe fragmenty kodu klas,
         * wskaz KTORE uzywaja field injection - jaki jest 'sygnal
         * wizualny' (`@Autowired` nad polem, nie nad konstruktorem)?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementThreeClassCircularChainWithFieldInjection {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj demo cyklu do 3 klas (A->B->C->A) przez field
         * injection - zweryfikuj, ze WCIAZ "dziala".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MigrateThreeClassCycleToConstructorAndObserveFailure {
        /*
         * 🧪 Zadanie 12:
         * Przepisz WSZYSTKIE 3 klasy z Zadania 11 na constructor
         * injection - zaobserwuj i zapisz blad przy starcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RefactorCycleByExtractingSharedDependency {
        /*
         * 🧪 Zadanie 13:
         * Napraw PRAWDZIWY problem projektowy z Zadania 12 - wydziel
         * WSPOLNA zaleznosc do 3. klasy, zamiast utrzymywac cykl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementMixedFieldAndConstructorInjectionInSameClass {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj klase MIESZAJACA style (jedna zaleznosc field
         * injection, druga constructor) - bez terminala: wyjasnij,
         * dlaczego to ZLA praktyka (niespojnosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_InspectFieldAccessibilityViaReflection {
        /*
         * 🧪 Zadanie 15:
         * Sprawdz przez refleksje (`Field.isAccessible()` /
         * `canAccess(...)`), czy pole `@Autowired` jest domyslnie
         * `private` - jakim mechanizmem Spring je mimo to ustawia?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementManualFieldWiringForTestingFieldInjectedClass {
        /*
         * 🧪 Zadanie 16:
         * Napisz "test" dla klasy z field injection, ktory RECZNIE
         * (przez refleksje, jak w `Lesson04`) ustawia pole PRZED testem -
         * porownaj ILOSC kodu z Zadaniem 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainWhyMockBeanHistoricallyUsedFieldInjectionStyle {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij (koncepcyjnie), dlaczego adnotacja
         * testowa `@MockBean` (Spring Boot) dziala na POLACH, mimo ze
         * ogolna zasada mowi "unikaj field injection w kodzie
         * PRODUKCYJNYM" - czy to sprzecznosc?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureTimeToDiagnoseHiddenDependencyBug {
        /*
         * 🧪 Zadanie 18:
         * Zasymuluj (na czas, "na sucho") ile TRUDNIEJ jest zdiagnozowac
         * przyczyne `NullPointerException` w duzej klasie z field
         * injection niz w klasie z constructor injection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementFieldInjectionWithRequiredFalse {
        /*
         * 🧪 Zadanie 19:
         * Uzyj `@Autowired(required = false)` na polu - zweryfikuj
         * zachowanie, gdy zaleznosc NIE jest dostepna w kontekscie
         * (brak bledu, pole pozostaje `null`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildCodeSmellDetectorForFieldInjection {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj metode (przez refleksje) SKANUJACA liste klas i
         * WYKRYWAJACA pola oznaczone `@Autowired` (potencjalny "code
         * smell") - wygeneruj prosty raport.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementAutomatedMigrationToolFieldToConstructor {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj (koncepcyjnie, na przykladzie) narzedzie
         * ANALIZUJACE klase (refleksja) i GENERUJACE tekst nowego
         * konstruktora na podstawie znalezionych pol `@Autowired`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareCircularDependencyBehaviorAcrossSpringVersions {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: wyjasnij (powiazujac z `Lesson02`), jak
         * zachowanie z tej lekcji (cykl "dziala" przez field injection)
         * mogloby sie ROZNIC, gdyby demo uruchomic przez pelne
         * `SpringApplication.run()` (Spring Boot) zamiast gologo
         * `AnnotationConfigApplicationContext` (podpowiedz: domyslna
         * flaga `allow-circular-references` zmieniona w Boot 2.6).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementDeepCircularChainWithMixedInjectionStyles {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj lancuch 4 klas z MIESZANYMI stylami injection (niektore
         * field, niektore constructor) tworzacy cykl - przeanalizuj, KTORA
         * klasa "psuje" caly lancuch (musi byc constructor-injected, zeby
         * cykl w ogole zawiodl).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildStaticAnalysisRuleForFieldInjectionBan {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj (opisz, ewentualnie zaimplementuj uproszczona
         * wersje) regule dla narzedzia statycznej analizy (jak PMD z
         * `_16_clean_code/Lesson20`) WYKRYWAJACA field injection w
         * kodzie produkcyjnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementProxyBasedLazyFieldInjectionAlternative {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WLASNY mechanizm "leniwego" wstrzykiwania przez
         * proxy (jak `@Lazy` w Springu) - zamiast pola ustawianego od
         * razu, uzyj `Supplier<T>` inicjalizowanego przy pierwszym
         * uzyciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AnalyzeMemoryLayoutDifferenceFieldVsConstructorInjection {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: czy field injection i constructor injection
         * DAJA rozny uklad pamieciowy obiektu (rozmiar/liczba pol)? Czy
         * roznica jest WYLACZNIE na poziomie KODU ZRODLOWEGO?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCompleteCycleDetectionAlgorithmManually {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj WLASNY algorytm wykrywania cykli w grafie
         * zaleznosci (DFS z kolorowaniem wierzcholkow) - zastosuj do
         * grafu 5+ klas symulowanych Mapa "kto-zalezy-od-kogo".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareStartupTimeCircularVsNonCircularGraph {
        /*
         * 🧪 Zadanie 28:
         * Zmierz czas startu kontekstu DLA grafu beanow BEZ cykli vs Z
         * cyklem (field injection) - czy jest RÓZNICA wydajnosciowa
         * zwiazana z rozwiazywaniem cyklu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementLegacyCodeAuditReportForInjectionStyles {
        /*
         * 🧪 Zadanie 29:
         * Zasymuluj (liste 20+ "klas" z metadanymi o stylu injection)
         * audyt duzego, legacy projektu - wygeneruj raport procentowy
         * (ile % field/constructor/setter) i liste PRIORYTETOWYCH klas
         * do migracji (np. tych bedacych CZESCIA cyklu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMigrationCapstoneFromLegacyToModern {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny scenariusz: "legacy" aplikacja z field
         * injection (wlacznie z cyklem) -> ZDIAGNOZUJ problemy (Zadania
         * 20/27) -> PRZEMIGRUJ na constructor injection -> ROZWIAZ
         * ujawniony cykl (Zadanie 13) - z czytelnym logiem KAZDEGO etapu.
         */
        public static void main(String[] args) { }
    }
}
