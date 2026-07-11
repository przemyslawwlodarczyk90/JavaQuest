package com.example.javaquest._20_spring_core.Lesson08_ComponentServiceRepository;

public class _Exercises_Lesson08_ComponentServiceRepository {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainRelationshipBetweenStereotypesAndComponent {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij relacje miedzy `@Component` a
         * `@Service`/`@Repository`/`@Controller`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifyMetaAnnotationWithReflection {
        /*
         * 🧪 Zadanie 2:
         * Uzyj reflekcji (`isAnnotationPresent`), zeby zweryfikowac, ze
         * `@RestController` TEZ jest (posrednio) `@Component`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateOneClassPerStereotype {
        /*
         * 🧪 Zadanie 3:
         * Utworz WLASNE 4 klasy - po jednej z kazdym stereotypem - z
         * metoda `identify()` jak w teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RetrieveAllFourFromSameContext {
        /*
         * 🧪 Zadanie 4:
         * Pobierz WSZYSTKIE 4 klasy z Zadania 3 z JEDNEGO kontekstu -
         * zweryfikuj, ze WSZYSTKIE zostaly utworzone.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhenToUseServiceVsRepository {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, kiedy uzyc `@Service`, a kiedy
         * `@Repository` - podaj przyklad KAZDEGO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReproduceExceptionTranslationDemo {
        /*
         * 🧪 Zadanie 6:
         * Odtworz demo tlumaczenia wyjatkow z teorii dla WLASNEGO,
         * wymyslonego "natywnego" wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TryExceptionTranslationWithoutRepositoryAnnotation {
        /*
         * 🧪 Zadanie 7:
         * Usun `@Repository` z klasy w Zadaniu 6 (zostaw
         * `PersistenceExceptionTranslationPostProcessor`) - zaobserwuj,
         * ze tlumaczenie PRZESTAJE dzialac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyControllerNeedsSpringMvc {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, dlaczego samo `@Controller` (BEZ
         * uruchomionego Spring MVC) nie sprawi, ze klasa zacznie
         * obslugiwac zadania HTTP - co jeszcze jest potrzebne?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListWhatHappensIfWrongStereotypeUsed {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: opisz KONSEKWENCJE oznaczenia klasy dostepu do
         * danych jako `@Service` zamiast `@Repository` - co DOKLADNIE
         * sie NIE wydarzy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareStereotypeNamesAcrossLayeredArchitecture {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: powiaz stereotypy z warstwami architektury z
         * `_10_dao/Lesson02_LayeredArchitecture` - ktora warstwa
         * odpowiada ktoremu stereotypowi?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementCustomStereotypeAnnotation {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNA, nowa adnotacje meta-oznaczona `@Component`
         * (np. `@UseCase`) - zweryfikuj, ze component-scanning ja
         * znajduje TAK SAMO jak wbudowane stereotypy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementServiceOrchestratingMultipleRepositories {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj `@Service`, ktory orkiestruje (wywoluje) DWA
         * rozne `@Repository` - zademonstruj typowy przeplyw logiki
         * biznesowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementMultipleTranslatorsForDifferentExceptionTypes {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj DRUGI `PersistenceExceptionTranslator` dla INNEGO
         * typu "natywnego" wyjatku - zweryfikuj, ze Spring uzywa
         * WLASCIWEGO translatora dla kazdego typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRepositoryMethodThatDoesNotThrow {
        /*
         * 🧪 Zadanie 14:
         * Zweryfikuj, ze proxy tlumaczace wyjatki NIE WPLYWA na
         * normalne, POPRAWNE wywolania metody (bez wyjatku) -
         * zmierz ewentualny narzut czasowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhyTranslatorReturnsNullForUnknownExceptions {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: wyjasnij, dlaczego
         * `translateExceptionIfPossible` zwraca `null` dla NIEZNANYCH
         * wyjatkow zamiast rzucac blad - co sie wtedy dzieje z
         * oryginalnym wyjatkiem?
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareDataAccessExceptionHierarchy {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala (lub przegladajac Javadoc): wymien co najmniej 3
         * konkretne podklasy `DataAccessException` i ich typowe
         * zastosowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementRestControllerStereotypeCheck {
        /*
         * 🧪 Zadanie 17:
         * Sprawdz reflekcja, czy `@RestController` jest meta-oznaczone
         * `@Controller` (a NIE bezposrednio `@Component`) - narysuj
         * (tekstowo) LANCUCH meta-adnotacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementLayeredArchitectureWithAllStereotypes {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj mini-aplikacje z 4 warstwami (Controller->Service->
         * Repository->Component-narzedzie) - kazda WLASCIWYM stereotypem,
         * z pelnym przeplywem wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainProxyMechanismBehindExceptionTranslation {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyjasnij (na podstawie tego, co juz wiesz o Java
         * dynamic proxy z `_14_advancedjava`), JAK `PersistenceException
         * TranslationPostProcessor` "przechwytuje" wyjatek - zapowiedz
         * Lesson21_SpringAopFundamentals.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildStereotypeUsageAuditForSimulatedProject {
        /*
         * 🧪 Zadanie 20:
         * Zasymuluj (liste nazw klas + ich stereotypow) "audyt" duzego
         * projektu - wykryj klasy z NAZWAMI sugerujacymi jedno
         * (np. "...Repository") a stereotypem INNYM (np. `@Service`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomStereotypeWithAdditionalBehavior {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY stereotyp (jak Zadanie 11) ORAZ
         * `BeanPostProcessor`, ktory DODAJE specjalne zachowanie TYLKO
         * dla klas oznaczonych TA adnotacja (wzorem `@Repository`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureProxyOverheadForRepositoryBeans {
        /*
         * 🧪 Zadanie 22:
         * Zmierz roznice wydajnosci miedzy WYWOLANIEM metody na beanie
         * `@Repository` (opakowanym w proxy) a zwyklym obiektem (przez
         * `new`) - 100 000 wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementChainedExceptionTranslators {
        /*
         * 🧪 Zadanie 23:
         * Zarejestruj WIELE translatorow wyjatkow naraz - zweryfikuj
         * KOLEJNOSC, w jakiej Spring je sprawdza (pierwszy pasujacy
         * wygrywa? wszystkie sa sprawdzane?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildCustomAnnotationProcessorValidatingStereotypeUsage {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj metode "walidujaca" (przez reflekcje) liste klas -
         * OSTRZEGAJ, jesli klasa z nazwa konczaca sie na "Repository" NIE
         * ma adnotacji `@Repository`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementServiceLayerTransactionBoundaryPreview {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj `@Service`, ktory wywoluje 2 `@Repository` W
         * RAMACH JEDNEJ logicznej operacji - w komentarzu wyjasnij,
         * DLACZEGO granica transakcji (`@Transactional`, zapowiedz
         * `_23_spring_data_jpa/Lesson08`) powinna byc na TEJ warstwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareStereotypesWithHexagonalArchitectureTerms {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: powiaz stereotypy Springa z terminologia
         * architektury heksagonalnej z `_17_architecture/Lesson11-12` -
         * ktory stereotyp odpowiada "portowi", a ktory "adapterowi"?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRepositoryWithMultipleTranslationLayers {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj repozytorium symulujace WIELE poziomow bledow
         * (blad polaczenia, blad zapytania, blad integralnosci danych) -
         * kazdy tlumaczony na INNA podklase `DataAccessException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCustomStereotypeHierarchyMirroringSpring {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj WLASNA "mini-hierarchie" stereotypow (jak
         * `@Component`->`@Service`) dla WLASNEJ domeny (np.
         * `@Component`->`@UseCase`->`@ReadUseCase`/`@WriteUseCase`) -
         * zweryfikuj dzialanie component-scanningu na WSZYSTKICH
         * poziomach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AnalyzeRealSpringSourceForStereotypeMetaAnnotations {
        /*
         * 🧪 Zadanie 29:
         * Przeanalizuj (przez reflekcje w runtime) PELNY zestaw adnotacji
         * obecnych NA `@Service`/`@Repository`/`@Controller` (nie tylko
         * `@Component`) - wypisz WSZYSTKIE, ktore znajdziesz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteLayeredAppWithExceptionTranslationCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna, 3-warstwowa mini-aplikacje (Controller-
         * Service-Repository) z DZIALAJACYM tlumaczeniem wyjatkow na
         * warstwie Repository - zweryfikuj co najmniej 3 scenariusze
         * (sukces, blad przetlumaczony, blad nieprzetlumaczony).
         */
        public static void main(String[] args) { }
    }
}
