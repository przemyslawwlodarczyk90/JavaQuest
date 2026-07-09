package com.example.javaquest._14_advancedjava.Lesson16_ReflectionUseCasesAndRisks;

public class _Exercises_Lesson16_ReflectionUseCasesAndRisks {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineInjectAnnotation {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj wlasna adnotacje "@MyInject" z retencja RUNTIME i celem
         * FIELD (jak w Lesson13/14). Oznacz nia prywatne pole "Logger logger"
         * w nowej klasie "ReportService" (interfejs "Logger" z metoda
         * "void log(String)" i implementacja "ConsoleLogger" tez do napisania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MiniInjectorForOneField {
        /*
         * 🧪 Zadanie 2:
         * Napisz metode "static void inject(Object target, Object dependency)",
         * ktora przez "getDeclaredFields()" znajduje pole oznaczone "@MyInject"
         * (Zadanie 1) o typie zgodnym z klasa "dependency" i ustawia je
         * (setAccessible + Field.set). Przetestuj na "ReportService".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_EntityWithNoArgConstructorLikeHibernate {
        /*
         * 🧪 Zadanie 3:
         * Napisz klase "CustomerEntity" z PRYWATNYMI polami "Long id",
         * "String email" oraz PRYWATNYM konstruktorem bezargumentowym (jak
         * wymaga Hibernate - patrz _12_hibernate/Lesson04). Utworz jej
         * instancje WYLACZNIE przez "getDeclaredConstructor().newInstance()"
         * z setAccessible(true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FillEntityFieldsFromMap {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode "static void fillFields(Object entity, Map<String,Object> data)",
         * ktora dla kazdego wpisu w mapie ustawia pole o tej nazwie na obiekcie
         * "entity" (reflekcyjnie). Uzyj jej, by wypelnic "CustomerEntity"
         * (Zadanie 3) danymi {"id" -> 1L, "email" -> "a@example.com"}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SimpleJsonLikeSerializer {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode "static String toSimpleJson(Object obj)", ktora
         * reflekcyjnie odczytuje WSZYSTKIE pola obiektu (getDeclaredFields +
         * setAccessible + Field.get) i zwraca napis
         * "{"pole1":wartosc1,"pole2":wartosc2}" (String w cudzyslowie, liczby
         * bez). Przetestuj na "CustomerEntity" (Zadanie 3/4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DefineTestAnnotationAndFindMethods {
        /*
         * 🧪 Zadanie 6:
         * Zdefiniuj wlasna adnotacje "@MyTest" (RUNTIME, METHOD). Napisz klase
         * "CalculatorTests" z TRZEMA metodami oznaczonymi "@MyTest" i JEDNA
         * BEZ tej adnotacji. Wypisz (samymi nazwami, bez wywolywania) tylko
         * te oznaczone, uzywajac "isAnnotationPresent(MyTest.class)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RunTestMethodsLikeJUnit {
        /*
         * 🧪 Zadanie 7:
         * Napisz metode "static void runTests(Class<?> testClass)", ktora
         * tworzy NOWA instancje klasy (bezargumentowy konstruktor) i wywoluje
         * KAZDA metode oznaczona "@MyTest" (Zadanie 6) przez "Method.invoke(instancja)".
         * Wypisz "PASS: nazwaMetody" po kazdym udanym wywolaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CatchExceptionFromFailingTest {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do "CalculatorTests" (Zadanie 6/7) metode "@MyTest" ktora rzuca
         * "RuntimeException("celowy blad")". Rozszerz "runTests" (Zadanie 7),
         * by lapala "InvocationTargetException", odczytywala "getCause()" i
         * wypisywala "FAIL: nazwaMetody - " + komunikat przyczyny, zamiast
         * przerywac dzialanie calej metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MeasureReflectiveInvokeOverhead {
        /*
         * 🧪 Zadanie 9:
         * Zmierz (System.nanoTime()) czas 100 000 wywolan prostej metody
         * bezargumentowej BEZPOSREDNIO oraz przez "Method.invoke(...)". Wypisz
         * oba czasy w milisekundach i w komentarzu (min. 2 zdania) skomentuj
         * roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWhichFrameworksUseReflectionWhere {
        /*
         * 🧪 Zadanie 10:
         * W komentarzu (bez kodu) wypisz w formie listy: Guice/Spring, Hibernate,
         * Jackson/Gson, JUnit - i dla kazdego jednym zdaniem opisz, W KTORYM
         * dokladnie momencie (jaka klasa/adnotacja/metoda reflekcyjna) siega
         * po refleksje.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SetAccessibleBypassValidationDemo {
        /*
         * 🧪 Zadanie 11:
         * Napisz klase "BankAccount" z prywatnym polem "double balance" i
         * publiczna metoda "withdraw(double amount)", ktora rzuca wyjatek dla
         * kwoty wiekszej niz saldo. Uzyj Field.set (po setAccessible) by
         * USTAWIC saldo na ujemna wartosc BEZ przechodzenia przez "withdraw" -
         * w komentarzu (min. 3 zdania) opisz, dlaczego to obejscie jest
         * niebezpieczne w prawdziwym systemie bankowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_NoSuchFieldExceptionAfterRename {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode "static Object readFieldSafely(Object obj, String fieldName)",
         * ktora probuje odczytac pole po nazwie i - jesli go nie ma - lapie
         * "NoSuchFieldException" i zwraca "null" z wypisaniem ostrzezenia
         * zamiast wywalic caly program. Zademonstruj na celowo BLEDNEJ nazwie
         * pola (symulacja tego, co dzieje sie po "rename" pola bez aktualizacji
         * kodu reflekcyjnego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PluginLoaderByClassName {
        /*
         * 🧪 Zadanie 13:
         * Napisz interfejs "Plugin" z metoda "void run()". Napisz DWIE
         * implementacje: "GreetingPlugin" i "FarewellPlugin". Napisz metode
         * "static Plugin loadPlugin(String fullyQualifiedClassName)", ktora
         * przez "Class.forName(name).getDeclaredConstructor().newInstance()"
         * tworzy i zwraca obiekt "Plugin" na podstawie SAMEJ nazwy klasy jako
         * String (symulacja ladowania wtyczek po nazwie z konfiguracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ValidateNoArgConstructorRequirement {
        /*
         * 🧪 Zadanie 14:
         * Napisz metode "static boolean hasNoArgConstructor(Class<?> type)",
         * ktora zwraca true/false na podstawie proby "getDeclaredConstructor()"
         * (zlapanego "NoSuchMethodException"). Sprawdz nia klase MAJACA taki
         * konstruktor i klase NIE majaca go (tylko konstruktor z argumentami) -
         * to symulacja walidacji, jaka Hibernate wykonuje dla kazdej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ReflectiveDtoToEntityMapper {
        /*
         * 🧪 Zadanie 15:
         * Napisz klasy "PersonDto" i "PersonEntity" (obie z polami "String name",
         * "int age", takimi samymi nazwami). Napisz metode
         * "static <T> T map(Object source, Class<T> targetType)", ktora tworzy
         * obiekt "targetType" (bezargumentowy konstruktor) i kopiuje wartosci
         * pol o TEJ SAMEJ nazwie ze "source" (jak MapStruct z _13_libraries,
         * ale reflekcyjnie zamiast generowanym kodem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DetectMissingNoArgConstructorFriendlyError {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode "static Object safeNewInstance(Class<?> type)", ktora
         * probuje "getDeclaredConstructor().newInstance()" i - jesli konstruktor
         * bezargumentowy nie istnieje - lapie "NoSuchMethodException" i rzuca
         * WLASNY, czytelny wyjatek z komunikatem w stylu: "Klasa X musi miec
         * konstruktor bezargumentowy, by moc byc zbudowana reflekcyjnie".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_AnnotationBasedFieldExclusion {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj adnotacje "@JsonIgnore" (RUNTIME, FIELD - jak prawdziwy
         * Jackson). Oznacz nia jedno pole (np. haslo/password) w nowej klasie
         * "UserAccount". Rozszerz serializator z Zadania 5 tak, by POMIJAL
         * pola oznaczone "@JsonIgnore" (isAnnotationPresent check przed
         * dodaniem do wyniku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareFieldVsSetterInjectionMechanics {
        /*
         * 🧪 Zadanie 18:
         * Napisz DWIE wersje tej samej klasy: "ServiceFieldInjection" (prywatne
         * pole z @MyInject, jak w Zadaniu 1-2) i "ServiceSetterInjection"
         * (publiczny setter, wywolywany reflekcyjnie przez "getMethod("setDependency", ...)
         * .invoke(...)"). Zbuduj OBIE reflekcyjnie i w komentarzu (min. 3 zdania)
         * porownaj wymagania wobec kazdej z nich (co framework musi wiedziec/
         * zalozyc w kazdym przypadku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_InaccessibleObjectExceptionExplanation {
        /*
         * 🧪 Zadanie 19:
         * W komentarzu (bez kodu, min. 4 zdania) wyjasnij: czym jest
         * "InaccessibleObjectException", w jakiej sytuacji (modul JPMS bez
         * "opens") jest rzucany, i dlaczego nasz projekt (bez module-info.java,
         * dzialajacy na classpath) NIGDY go nie napotka - mimo intensywnego
         * uzycia setAccessible(true) w tej lekcji. Pelne szczegoly poznasz
         * w Lesson27/28.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DecideReflectionVsPolymorphism {
        /*
         * 🧪 Zadanie 20:
         * Dany jest problem: masz 5 typow ksztaltow (Circle, Square, Triangle...)
         * i chcesz dla kazdego policzyc pole powierzchni. Napisz DWA rozwiazania:
         * (a) reflekcyjne - wywolanie "getMethod("area").invoke(ksztalt)" po
         * nazwie metody, (b) polimorficzne - wspolny interfejs "Shape" z metoda
         * "double area()". W komentarzu uzasadnij, ktore rozwiazanie jest
         * WLASCIWE w tym przypadku i dlaczego reflekcja byłaby tu naduzyciem.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MiniDiContainerWithConstructorInjection {
        /*
         * 🧪 Zadanie 21:
         * Napisz mini-kontener DI: "static <T> T create(Class<T> type, Map<Class<?>, Object> registry)",
         * ktory znajduje JEDYNY publiczny konstruktor typu "type", dla kazdego
         * jego PARAMETRU szuka pasujacego obiektu w "registry" (po typie) i
         * wola "constructor.newInstance(dopasowaneZaleznosci...)". Przetestuj
         * na klasie z konstruktorem przyjmujacym 2 zaleznosci (interfejsy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RecursiveDependencyGraphBuilder {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz kontener z Zadania 21 tak, by REKURENCYJNIE budowal
         * zaleznosci, ktorych NIE MA jeszcze w "registry", ale SA konkretnymi
         * klasami z wlasnym konstruktorem (Just-In-Time, jak w Guice -
         * porownaj z _13_libraries/Lesson19.Exercise09). Zbuduj graf 3-poziomowy
         * (A zalezy od B, B zalezy od C) JEDNYM wywolaniem "create(A.class, registry)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ReflectiveEntityHydratorFromResultSetLikeRows {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode "static <T> List<T> hydrate(Class<T> entityType, List<Map<String,Object>> rows)",
         * ktora dla KAZDEGO wiersza (mapy nazwaKolumny->wartosc) tworzy nowy
         * obiekt "entityType" (bezargumentowy konstruktor) i wypelnia jego pola
         * (jak buildEntityLikeHibernate() z teorii lekcji) - zwroc liste
         * gotowych obiektow. Przetestuj z 3 wierszami danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AnnotationDrivenValidatorWithMultipleRules {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj DWIE adnotacje: "@NotBlank" (dla String) i "@Positive"
         * (dla liczb). Oznacz nimi pola w nowej klasie "RegistrationForm"
         * (name @NotBlank, age @Positive). Napisz "static List<String> validate(Object obj)"
         * sprawdzajaca OBIE reguly reflekcyjnie i zwracajaca liste bledow.
         * Przetestuj na poprawnym i niepoprawnym formularzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BenchmarkReflectionFieldAccessVsGetter {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas 500 000 odczytow pola: (a) przez publiczny getter
         * bezposrednio, (b) przez "Field.get()" reflekcyjnie (BEZ ponownego
         * pobierania Field w petli - pobierz go RAZ przed pomiarem). Wypisz
         * oba czasy i wspolczynnik spowolnienia - w komentarzu odnies wynik
         * do Ryzyka 1 z teorii lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ReflectiveObserverRegistration {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj adnotacje "@OnEvent(String value())" (RUNTIME, METHOD).
         * Napisz klase "EventHandlers" z dwiema metodami oznaczonymi
         * "@OnEvent("ORDER_CREATED")" i "@OnEvent("ORDER_CANCELLED")". Napisz
         * metode "static void dispatch(Object handlersObj, String eventName)",
         * ktora reflekcyjnie znajduje i wywoluje WLASCIWA metode na podstawie
         * wartosci adnotacji rownej "eventName" (prosty, reczny "event bus").
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DeepFieldCopyRespectingJsonIgnore {
        /*
         * 🧪 Zadanie 27:
         * Rozszerz kopiowanie pol (podobne do Lesson15.Exercise16) o
         * respektowanie adnotacji "@JsonIgnore" z Zadania 17 - pola nia
         * oznaczone maja pozostac NIEZMIENIONE (z wartoscia domyslna) w kopii,
         * mimo ze zrodlo ma inna wartosc. Zademonstruj na "UserAccount"
         * (haslo NIE powinno zostac skopiowane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SimulateJUnitBeforeEachViaAnnotation {
        /*
         * 🧪 Zadanie 28:
         * Zdefiniuj adnotacje "@MySetup" (RUNTIME, METHOD - odpowiednik
         * @BeforeEach z JUnit). Rozszerz "runTests" z Zadania 7 tak, by PRZED
         * KAZDA metoda "@MyTest" wywolywala NAJPIERW metode oznaczona
         * "@MySetup" (jesli istnieje) na TEJ SAMEJ (nowej) instancji klasy
         * testowej. Zademonstruj na klasie z metoda setup() inicjalizujaca
         * pole uzywane przez testy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PerformanceCachingReflectiveLookups {
        /*
         * 🧪 Zadanie 29:
         * Napisz klase "ReflectionCache" z "Map<Class<?>, Method[]>" jako polem
         * statycznym oraz metoda "static Method[] methodsOf(Class<?> type)",
         * ktora zwraca "getDeclaredMethods()" z CACHE (obliczajac tylko RAZ na
         * klase, kolejne wywolania czytaja z mapy). Zmierz czas 100 000 wywolan
         * "methodsOf(SameClass)" Z cache i (w osobnym biegu) BEZ cache (wprost
         * "SameClass.class.getDeclaredMethods()" w petli) - porownaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMiniOrmSelectAllRows {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz mini-"ORM" laczacy watki tej lekcji.
         * Klasa encji "ProductEntity" (id, name, price) z prywatnym
         * konstruktorem bezargumentowym i adnotacjami "@Column("nazwa_kolumny")"
         * na kazdym polu (RUNTIME, FIELD - Twoja wlasna adnotacja). Metoda
         * "static <T> List<T> mapRows(Class<T> entityType, List<Map<String,Object>> rawRows)"
         * ma: dla kazdego wiersza stworzyc encje reflekcyjnie, dla kazdego pola
         * odczytac wartosc jej @Column.value() z wiersza (a NIE nazwy pola
         * Javy wprost) i ustawic pole. Przetestuj z wierszami majacymi klucze
         * w stylu "product_id"/"product_name"/"product_price".
         */
        public static void main(String[] args) { }
    }
}
