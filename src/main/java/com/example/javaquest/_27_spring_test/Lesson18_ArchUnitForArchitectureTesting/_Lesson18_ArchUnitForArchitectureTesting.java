package com.example.javaquest._27_spring_test.Lesson18_ArchUnitForArchitectureTesting;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson18_ArchUnitForArchitectureTesting {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: ArchUnit - testowanie reguł architektonicznych ===");

        /*
         * ============================================================
         * 📦 ArchUnit = TESTY sprawdzajace ZALEZNOSCI MIEDZY klasami/pakietami, NIE zachowanie
         * ============================================================
         * WSZYSTKIE poprzednie lekcje testowaly ZACHOWANIE kodu
         * (WYNIK wywolania metody). ArchUnit testuje STRUKTURE
         * kodu - "CZY warstwa kontrolera PRZYPADKIEM NIE omija
         * warstwy serwisowej I NIE siega BEZPOSREDNIO DO
         * repozytorium?" (powiazanie Z `_17_architecture/Lesson04_
         * ControllerServiceRepository`) - REGULY, KTORYCH ZLAMANIE
         * NIE DA sie wykryc ZWYKLYM testem jednostkowym/integracyjnym
         * (kod MOZE DZIALAC POPRAWNIE, ALE LAMAC ARCHITEKTURE).
         *
         * 🔍 `ClassFileImporter().importPackages(...)` WCZYTUJE
         * SKOMPILOWANY bajtkod (NIE zrodla!) I BUDUJE MODEL
         * zaleznosci - `ArchRule` DEFINIUJE REGULE ("ZADNA klasa
         * Controller NIE POWINNA zalezec OD klasy Repository"),
         * `.check(classes)` WERYFIKUJE i RZUCA `AssertionError`
         * PRZY naruszeniu.
         */
        System.out.println("ArchUnit = testy STRUKTURY kodu (zaleznosci miedzy klasami), NIE zachowania. Wykrywa naruszenia architektury NIEWIDOCZNE dla zwyklych testow.");

        demonstrateLayeringRule();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `noClasses().that().haveSimpleNameEndingWith("Controller")
         *   .should().dependOnClassesThat().haveSimpleNameEndingWith(
         *   "Repository")` - ZAKAZUJE kontrolerom BEZPOSREDNIEGO
         *   dostepu DO repozytoriow (powiazanie Z
         *   `_17_architecture/Lesson04`).
         * - `ArchRule.check(classes)` - RZUCA `AssertionError`
         *   Z DOKLADNYM opisem naruszenia (KTORA klasa, KTORA linia).
         * - PRAWDZIWE zastosowania W praktyce: wymuszenie
         *   `_17_architecture/Lesson10_DependencyDirection` (zaleznosci
         *   TYLKO DO SRODKA), zakaz cyklicznych zaleznosci MIEDZY
         *   pakietami, wymuszenie konwencji nazewniczych.
         * - ArchUnit URUCHAMIANY ZWYKLE JAKO CZESC pakietu testow CI
         *   (Lesson14 `_26`) - LAPIE "architektoniczna erozje" ZANIM
         *   TRAFI DO produkcji.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    // ============================================================
    // Mini-architektura DEMO (Controller -> Service -> Repository, powiazanie z _17_architecture)
    // ============================================================
    interface DemoRepository {
        String findData();
    }

    static class DemoRepositoryImpl implements DemoRepository {
        @Override
        public String findData() {
            return "dane-z-repozytorium";
        }
    }

    interface DemoService {
        String getData();
    }

    static class DemoServiceImpl implements DemoService {
        private final DemoRepository repository;

        DemoServiceImpl(DemoRepository repository) {
            this.repository = repository;
        }

        @Override
        public String getData() {
            return repository.findData();
        }
    }

    /** POPRAWNA warstwa - GoodController korzysta Z serwisu, NIGDY BEZPOSREDNIO Z repozytorium. */
    static class GoodController {
        private final DemoService service;

        GoodController(DemoService service) {
            this.service = service;
        }

        String handle() {
            return service.getData();
        }
    }

    /** NARUSZENIE architektury - BadController POMIJA warstwe serwisowa I siega WPROST DO repozytorium. */
    static class BadController {
        private final DemoRepository repository;

        BadController(DemoRepository repository) {
            this.repository = repository;
        }

        String handle() {
            return repository.findData();
        }
    }

    private static void demonstrateLayeringRule() {
        JavaClasses importedClasses = new ClassFileImporter()
                .importPackages("com.example.javaquest._27_spring_test.Lesson18_ArchUnitForArchitectureTesting");

        System.out.println("\nZaimportowano " + importedClasses.size() + " klas z pakietu tej lekcji do analizy ArchUnit.");

        // REGULA 1 (dla GoodController) - SPRAWDZAMY TYLKO GoodController - REGULA PRZECHODZI,
        // bo GoodController NIGDY nie odwoluje sie DO klasy konczacej sie na "Repository".
        ArchRule goodControllerRule = noClasses()
                .that().haveSimpleName("GoodController")
                .should().dependOnClassesThat().haveSimpleNameEndingWith("Repository")
                .because("kontrolery powinny korzystac z repozytoriow WYLACZNIE przez warstwe serwisowa");

        goodControllerRule.check(importedClasses);
        System.out.println("REGULA (GoodController): PRZESZLA - GoodController POPRAWNIE NIE zalezy bezposrednio od Repository.");

        // REGULA 2 (dla BadController) - TA SAMA idea REGULY, TERAZ NA BadController -
        // REGULA POWINNA zostac ZLAMANA, bo BadController WPROST wstrzykuje DemoRepository.
        ArchRule badControllerRule = noClasses()
                .that().haveSimpleName("BadController")
                .should().dependOnClassesThat().haveSimpleNameEndingWith("Repository")
                .because("kontrolery powinny korzystac z repozytoriow WYLACZNIE przez warstwe serwisowa");

        assertThatThrownBy(() -> badControllerRule.check(importedClasses))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("BadController");

        System.out.println("REGULA (BadController): POPRAWNIE WYKRYLA naruszenie architektury - BadController LAMIE warstwowanie.");

        assertThat(importedClasses.size()).isGreaterThan(0);
    }
}
