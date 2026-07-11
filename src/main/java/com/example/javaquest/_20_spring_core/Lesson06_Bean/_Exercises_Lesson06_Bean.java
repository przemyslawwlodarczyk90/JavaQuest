package com.example.javaquest._20_spring_core.Lesson06_Bean;

public class _Exercises_Lesson06_Bean {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatBeanMeansInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, czym jest "bean" -
         * czy to specjalny typ Javy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreatePlainObjectAndCompareWithBean {
        /*
         * 🧪 Zadanie 2:
         * Utworz WLASNA klase, stworz JEJ 2 instancje przez `new`, potem
         * jako `@Component` pobierz ja DWUKROTNIE z kontekstu - porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifySameInstanceReturnedTwice {
        /*
         * 🧪 Zadanie 3:
         * Uzyj operatora `==` zeby zweryfikowac, ze DWA pobrania tego
         * samego beana zwracaja IDENTYCZNA (nie tylko "rowna") instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InspectBeanDefinitionForOwnClass {
        /*
         * 🧪 Zadanie 4:
         * Pobierz `BeanDefinition` dla WLASNEGO beana - wypisz jego
         * klase i zakres.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyDefaultBeanNameConvention {
        /*
         * 🧪 Zadanie 5:
         * Utworz klase `InvoicePrinter` jako `@Component` - zweryfikuj,
         * ze domyslna nazwa beana to "invoicePrinter".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_OverrideBeanNameExplicitly {
        /*
         * 🧪 Zadanie 6:
         * Nadaj WLASNEJ klasie jawna nazwe beana przez
         * `@Component("mojaWlasnaNazwa")` - zweryfikuj przez
         * `context.containsBean(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListAllBeanDefinitionNames {
        /*
         * 🧪 Zadanie 7:
         * Wypisz WSZYSTKIE nazwy beanow zarejestrowanych w kontekscie
         * (`getBeanDefinitionNames()`) dla WLASNEJ konfiguracji z 3
         * beanami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyPojoIsCalledPojo {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij skrot POJO ("Plain Old Java Object") -
         * dlaczego bean Springa jest WCIAZ POJO?
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CreateBeanWithMultipleAliasNames {
        /*
         * 🧪 Zadanie 9:
         * Uzyj `@Bean(name = {"nazwa1", "nazwa2"})` w metodzie `@Bean`,
         * zeby nadac JEDNEMU beanowi WIELE aliasow - pobierz go pod
         * OBIEMA nazwami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareBeanNameWithClassSimpleName {
        /*
         * 🧪 Zadanie 10:
         * Napisz metode sprawdzajaca, czy nazwa beana ZAWSZE odpowiada
         * `Class.getSimpleName()` z mala pierwsza litera - przetestuj na
         * 3 roznych klasach.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_InspectBeanDefinitionsForAllRegisteredBeans {
        /*
         * 🧪 Zadanie 11:
         * Iteruj po WSZYSTKICH nazwach beanow i wypisz dla kazdego jego
         * `BeanDefinition` (klase, zakres) - odroznij beany WLASNE od
         * infrastrukturalnych (Springa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareBeanCreationTimingEagerVsUsage {
        /*
         * 🧪 Zadanie 12:
         * Dodaj `System.out.println` w konstruktorze WLASNEGO beana -
         * zweryfikuj, w KTORYM momencie (start kontekstu czy pierwsze
         * `getBean()`) faktycznie sie tworzy (zapowiedz `Lesson07_
         * ApplicationContext` - eager vs lazy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementBeanWithMutableStateAndObserveSharedState {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj bean z licznikiem odwiedzin (`incrementAndGet()`) -
         * pobierz go w 3 roznych miejscach kodu i zweryfikuj, ze stan
         * jest WSPOLDZIELONY (bo to TA SAMA instancja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainWhySharedMutableBeanStateIsRisky {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: powiaz Zadanie 13 z `_05_multithreading` -
         * dlaczego WSPOLDZIELONY, MUTOWALNY stan beana jest RYZYKOWNY w
         * aplikacji wielowatkowej (jak serwer web)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareBeanDefinitionAttributesAcrossConfigStyles {
        /*
         * 🧪 Zadanie 15:
         * Zarejestruj TEN SAM typ beana przez `@Component` i przez
         * `@Bean` (2 osobne konteksty) - porownaj ich `BeanDefinition`
         * (klasa zrodlowa, atrybuty).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCustomBeanNameGenerator {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj WLASNY `BeanNameGenerator` (interfejs Springa) i
         * uzyj go w `@ComponentScan(nameGenerator = ...)` - zmien logike
         * generowania domyslnych nazw.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_VerifyBeanIdentityAcrossMultipleGetBeanCalls {
        /*
         * 🧪 Zadanie 17:
         * Pobierz TEN SAM bean 100 razy w petli - zweryfikuj (przez
         * `System.identityHashCode`), ze WSZYSTKIE 100 to TA SAMA
         * instancja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementBeanFactoryAwareToInspectContainer {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj bean implementujacy `BeanFactoryAware` - wypisz
         * w metodzie `setBeanFactory(...)`, ILE beanow jest juz
         * zarejestrowanych w momencie jego tworzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareAbstractBeanDefinitionWithConcrete {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wyszukaj i wyjasnij, czym jest "abstrakcyjna"
         * definicja beana w XML (`abstract="true"`) - dlaczego Java
         * Config tego NIE POTRZEBUJE (podpowiedz: dziedziczenie klas).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildBeanInventoryReport {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj raport (wypisywany na konsole) WSZYSTKICH beanow WLASNEJ
         * aplikacji - nazwa, klasa, zakres, sformatowane w czytelnej
         * tabeli tekstowej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementProgrammaticBeanRegistrationAtRuntime {
        /*
         * 🧪 Zadanie 21:
         * Uzyj `GenericApplicationContext.registerBean(...)`, zeby
         * zarejestrowac NOWY bean PO tym, jak kontekst JUZ dziala
         * (dynamicznie, w trakcie wykonania) - zweryfikuj jego dostepnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementBeanDefinitionRegistryPostProcessor {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj `BeanDefinitionRegistryPostProcessor`, ktory
         * PROGRAMOWO dodaje NOWA definicje beana PRZED faktycznym
         * utworzeniem jakichkolwiek beanow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureMemoryFootprintOfManyBeans {
        /*
         * 🧪 Zadanie 23:
         * Zarejestruj 1000 roznych beanow (generowanych programowo) -
         * zmierz przyblizone zuzycie pamieci/czas startu kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomScopeAndRegisterIt {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj WLASNY, prosty `Scope` (interfejs Springa, np.
         * "watek" - jeden bean per watek uzywajac `ThreadLocal`) -
         * zarejestruj go przez `ConfigurableBeanFactory.registerScope(...)`
         * (zapowiedz `Lesson14_BeanScopes`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AnalyzeBeanDependencyGraphFromContainer {
        /*
         * 🧪 Zadanie 25:
         * Uzyj `ConfigurableListableBeanFactory.getDependenciesForBean(...)`,
         * zeby wypisac graf zaleznosci MIEDZY zarejestrowanymi beanami
         * WLASNEJ aplikacji (min. 4 wspolzalezne beany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementBeanPostProcessorLoggingCreationOrder {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj prosty `BeanPostProcessor` logujacy KOLEJNOSC
         * tworzenia beanow - zweryfikuj, czy jest ZGODNA z kolejnoscia
         * deklaracji, czy zalezy od czegos innego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareEagerSingletonWithLazyInitBean {
        /*
         * 🧪 Zadanie 27:
         * Oznacz jeden bean `@Lazy` - zweryfikuj (przez log w
         * konstruktorze), ze POWSTAJE dopiero przy pierwszym
         * `getBean()`, w odroznieniu od domyslnego, "chetnego" (eager)
         * tworzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildBeanDefinitionDiffTool {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj narzedzie porownujace `BeanDefinition` DWOCH roznych
         * kontekstow (np. "dev" i "prod" symulowane roznymi klasami
         * `@Configuration`) - wypisz roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFactoryBeanForComplexObjectCreation {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj interfejs `FactoryBean<T>` (specjalny rodzaj
         * beana TWORZACEGO inne beany) dla obiektu wymagajacego
         * skomplikowanej logiki budowy - zarejestruj i pobierz wynikowy
         * obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteBeanLifecycleInspectionCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo inspekcji kontenera - lista beanow,
         * ich definicje, graf zaleznosci, kolejnosc tworzenia
         * (BeanPostProcessor z Zadania 26) - jeden spojny raport
         * "co dokladnie dzieje sie wewnatrz kontenera".
         */
        public static void main(String[] args) { }
    }
}
