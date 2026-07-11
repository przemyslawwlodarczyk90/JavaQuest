package com.example.javaquest._20_spring_core.Lesson01_WhatIsSpring;

public class _Exercises_Lesson01_WhatIsSpring {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatProblemSpringSolves {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, jaki problem
         * rozwiazuje kontener Springa w duzej aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainSpringFrameworkVsSpringBoot {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij roznice miedzy Spring Framework a
         * Spring Boot - czy to konkurencyjne technologie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListSpringEcosystemProjects {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wymien co najmniej 4 projekty z ekosystemu
         * Springa (np. Spring Data, Spring Security) i krotko opisz,
         * czym sie kazdy zajmuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CreateManualWiringWithoutSpring {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj 2 klasy (np. `OrderService` zalezny od
         * `PriceCalculator`) i POLACZ je RECZNIE (bez Springa) przez
         * `new` - wzorem `demonstrateManualWiringWithoutSpring()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AnnotateSameClassesWithComponent {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `@Component` do obu klas z Zadania 4 - NIE zmieniaj
         * niczego innego w ich kodzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateApplicationContextAndFetchBean {
        /*
         * 🧪 Zadanie 6:
         * Utworz `AnnotationConfigApplicationContext` z klasa
         * `@Configuration`+`@ComponentScan` i pobierz bean przez
         * `context.getBean(...)` - porownaj z wynikiem Zadania 4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyJakartaNamespaceMatters {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, dlaczego namespace `jakarta.*` (a nie
         * `javax.*`) jest wazny przy szukaniu pomocy dla Spring Boot 3.x
         * w internecie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CheckProjectSpringBootVersionInPomXml {
        /*
         * 🧪 Zadanie 8:
         * Sprawdz w `pom.xml` tego projektu, jaka dokladnie wersje
         * `spring-boot-starter-parent` i Javy uzywa ten kurs.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CloseApplicationContextExplicitly {
        /*
         * 🧪 Zadanie 9:
         * Utworz kontekst BEZ try-with-resources, wypisz komunikat PRZED
         * i PO recznym wywolaniu `context.close()` - zaobserwuj, kiedy
         * kontener "gasnie".
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareLineCountManualVsSpring {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: policz linie kodu potrzebne do POLACZENIA 3
         * zaleznych od siebie klas RECZNIE vs PRZEZ Springa (kontener) -
         * co rosnie szybciej wraz z liczba klas?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_AddThirdDependencyLevelAndWireManually {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj Zadanie 4 o 3. klase (np. `OrderService` zalezny od
         * `PriceCalculator`, ktory zalezy od `TaxRateProvider`) - polacz
         * WSZYSTKIE 3 recznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LetSpringWireThreeLevelChain {
        /*
         * 🧪 Zadanie 12:
         * Dodaj `@Component` do wszystkich 3 klas z Zadania 11 i pozwol
         * kontenerowi je polaczyc - porownaj ilosc Twojego kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExploreWhatBeansContainerRegistered {
        /*
         * 🧪 Zadanie 13:
         * Uzyj `context.getBeanDefinitionNames()` zeby wypisac WSZYSTKIE
         * beany zarejestrowane w kontenerze - zidentyfikuj, ktore
         * pochodza z Twojego kodu, a ktore z samego Springa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareGetBeanByTypeVsByName {
        /*
         * 🧪 Zadanie 14:
         * Pobierz TEN SAM bean przez `getBean(Class)` i przez
         * `getBean(String, Class)` (po nazwie) - skad bierze sie
         * DOMYSLNA nazwa beana?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SimulateWithoutSpringUsingFactoryMethod {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj RECZNA "fabryke" (metoda statyczna tworzaca i
         * lacząca wszystkie obiekty na raz) jako POSREDNI krok miedzy
         * czystym `new` a kontenerem Springa - porownaj z Zadaniem 12.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainWhySpringDoesNotChangeYourClasses {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wyjasnij, dlaczego `@Component` na klasie NIE
         * zmienia jej dzialania (klasa dziala identycznie wywolana przez
         * `new` i przez kontener) - co WIEC faktycznie robi adnotacja?
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TryToGetBeanOfUnregisteredType {
        /*
         * 🧪 Zadanie 17:
         * Sprobuj pobrac `context.getBean(NiezarejestrowanaKlasa.class)`
         * (klasa BEZ `@Component`) - zaobserwuj i wyjasnij wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureContextStartupTime {
        /*
         * 🧪 Zadanie 18:
         * Zmierz (`System.nanoTime()`) czas tworzenia
         * `AnnotationConfigApplicationContext` - porownaj z czasem
         * "recznego" tworzenia tych samych obiektow przez `new`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainEcosystemMapToNewcomer {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: napisz krotkie (5-8 zdan) wyjasnienie mapy
         * ekosystemu Springa dla osoby, ktora slyszala tylko o "Spring
         * Boot" i nie wie, ze istnieje cokolwiek innego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_IdentifyWhichModulesAreUsedInThisProject {
        /*
         * 🧪 Zadanie 20:
         * Przejrzyj `pom.xml` tego projektu i zidentyfikuj, ktore
         * "startery" Springa sa juz obecne PRZED napisaniem tego
         * rozdzialu (podpowiedz: byly dodane transytywnie w
         * `_04_io`/`_07_servlets`/`_18_rest_api`).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildAppWithFiveInterdependentComponents {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj 5 wspolzaleznych komponentow (np. warstwy
         * kontroler->serwis->repozytorium + 2 pomocnicze) i pozwol
         * kontenerowi je wszystkie polaczyc - wypisz pelny lancuch
         * wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareContainerManagedVsFactoryPatternDesign {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: porownaj kontener Springa z klasycznym wzorcem
         * Factory/Abstract Factory (znanym z innych jezykow/kursow) -
         * czym kontener IoC jest "wiecej" niz zwykla fabryka obiektow?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementPluggableComponentSwapDemo {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj interfejs z 2 roznymi implementacjami
         * `@Component` (na razie zaakceptuj, ze da to
         * `NoUniqueBeanDefinitionException` - to material na Lesson12) -
         * zaobserwuj i zapisz DOKLADNY komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExplainContainerLifecycleAtHighLevel {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: opisz na WYSOKIM poziomie (bez szczegolow z
         * pozniejszych lekcji) 3 fazy zycia kontenera: start (skanowanie
         * i tworzenie beanow), praca (dostarczanie beanow na zadanie),
         * zamkniecie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildMiniEcosystemMapAsJavaRecord {
        /*
         * 🧪 Zadanie 25:
         * Zamodeluj mape ekosystemu Springa (Zadanie 3/19) jako liste
         * rekordow Java `SpringProject(String nazwa, String cel)` i
         * wypisz ja sformatowana w petli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareStartupCostOfContainerAtScale {
        /*
         * 🧪 Zadanie 26:
         * Rozbuduj Zadanie 18 do 20+ komponentow `@Component` - zmierz,
         * jak rosnie czas startu kontenera wraz z liczba beanow (liniowo?
         * gwaltowniej?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExplainWhySpringBootExistsGivenSpringFramework {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: majac juz dzialajacy kontener Springa (ten
         * rozdzial), wyjasnij, PO CO w ogole potrzebny byl Spring Boot -
         * co KONKRETNIE automatyzuje ponad to, co widziales dzisiaj?
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildBeforeAfterComparisonReport {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj raport (wypisywany na konsole) porownujacy "PRZED
         * Springiem" i "PO Springie" dla tej samej, 4-klasowej aplikacji -
         * linie kodu, liczba miejsc z `new`, latwosc podmiany
         * implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PredictWhatNextTwentyTwoLessonsWillCover {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: na podstawie dzisiejszego demo, przewidz (zanim
         * przeczytasz Lesson02+), jakie pytania o dzialanie kontenera
         * jeszcze pozostaly bez odpowiedzi (np. "co jesli 2 klasy
         * pasuja?", "co jesli sa cykliczne?").
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteBeforeAfterCapstoneDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo "przed i po Springu" dla WLASNEGO,
         * wymyslonego mini-scenariusza biznesowego (min. 4 wspolzalezne
         * klasy) - z czytelnym podsumowaniem, co dokladnie kontener
         * zrobil za Ciebie.
         */
        public static void main(String[] args) { }
    }
}
