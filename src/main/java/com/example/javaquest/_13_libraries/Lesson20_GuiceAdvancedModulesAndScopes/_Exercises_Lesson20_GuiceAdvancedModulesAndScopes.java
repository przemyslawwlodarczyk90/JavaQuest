package com.example.javaquest._13_libraries.Lesson20_GuiceAdvancedModulesAndScopes;

public class _Exercises_Lesson20_GuiceAdvancedModulesAndScopes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ProveNoScopeCreatesNewInstances {
        /*
         * 🧪 Zadanie 1:
         * Napisz klase "SessionId" z polem "final String value =
         * "S-" + System.nanoTime();". BEZ zadnego scope'u pobierz ja przez
         * Injector DWA razy do dwoch zmiennych i wypisz PASS/FAIL sprawdzajac
         * operatorem "==", ze to DWIE ROZNE instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SingletonAnnotationOnClass {
        /*
         * 🧪 Zadanie 2:
         * Napisz klase "GlobalCounter" oznaczona adnotacja "@Singleton" z
         * polem "int value" i metoda "increment()". Pobierz ja przez Injector
         * DWA RAZY do dwoch zmiennych, wywolaj "increment()" na PIERWSZEJ, a
         * nastepnie wypisz "value" z DRUGIEJ - udowodnij (PASS/FAIL), ze to
         * TA SAMA instancja (wartosc tez wzrosla).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ScopesSingletonInModule {
        /*
         * 🧪 Zadanie 3:
         * Napisz interfejs "CacheService" (metoda "void put(String key, String
         * value)") z implementacja "InMemoryCacheService" BEZ adnotacji
         * "@Singleton" na klasie. W module zwiaz go jako singleton przez
         * "bind(CacheService.class).to(InMemoryCacheService.class)
         * .in(Scopes.SINGLETON)" i udowodnij (PASS/FAIL, "=="), ze dwa pobrania
         * daja TA SAMA instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FirstProvidesMethod {
        /*
         * 🧪 Zadanie 4:
         * Napisz modul z metoda oznaczona "@Provides" o nazwie "provideAppVersion()"
         * zwracajaca "String" zbudowany z wlasna logika (np. konkatenacja "1." +
         * jakas liczba). Pobierz ten "String" przez Injector i wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ProvidesWithSingleton {
        /*
         * 🧪 Zadanie 5:
         * Napisz metode "@Provides @Singleton" budujaca "java.util.Random" z
         * ZALICZONYM (seedowanym) ziarnem. Pobierz go DWA razy z Injectora i
         * wypisz PASS/FAIL potwierdzajac ("=="), ze to TA SAMA instancja
         * (metoda wykonala sie tylko raz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BasicProviderInjection {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase "TicketPrinter" ktora w konstruktorze z "@Inject"
         * przyjmuje "Provider<SessionId>" (uzyj "SessionId" z Zadania 1).
         * Napisz metode "printTickets(int count)" ktora w petli "count" razy
         * wywoluje "provider.get()" i wypisuje ROZNA wartosc za kazdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TwoNamedBindingsOfSameInterface {
        /*
         * 🧪 Zadanie 7:
         * Napisz interfejs "MessageFormatter" (metoda "String format(String
         * text)") z DWIEMA implementacjami: "UpperCaseFormatter" i
         * "LowerCaseFormatter". Zwiaz obie pod roznymi etykietami "@Named"
         * ("upper" i "lower") w jednym module i pobierz OBIE przez Injector z
         * "Key.get(...)" lub osobna klase korzystajaca (@Named na parametrach
         * konstruktora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ToInstanceBindingForConfigValue {
        /*
         * 🧪 Zadanie 8:
         * Napisz modul ktory wiaze "@Named("maxRetries")" typu "Integer" na
         * GOTOWA wartosc (np. 5) przez "toInstance(...)". Napisz klase
         * "RetryPolicy" zalezna od tej wartosci (konstruktor z "@Inject" i
         * "@Named("maxRetries")") i wypisz jej wartosc po pobraniu z Injectora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CombineTwoModulesInOneInjector {
        /*
         * 🧪 Zadanie 9:
         * Napisz DWA osobne, proste moduly (kazdy wiazacy inny interfejs, np.
         * "CacheService" z Zadania 3 i "MessageFormatter" z Zadania 7) i
         * przekaz OBA naraz do "Guice.createInjector(moduleA, moduleB)".
         * Pobierz przez ten sam Injector po jednej instancji kazdego typu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToUseSingletonVsNoScope {
        /*
         * 🧪 Zadanie 10:
         * W komentarzu (min. 4 zdania) opisz DWA przyklady z realnego swiata,
         * gdzie "@Singleton" ma sens (np. polaczenie z baza, cache) oraz DWA
         * przyklady, gdzie brak scope'u (nowa instancja za kazdym razem) jest
         * WLASCIWY (np. obiekt reprezentujacy pojedyncze zadanie/request).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SharedStateThroughSingletonAcrossTwoConsumers {
        /*
         * 🧪 Zadanie 11:
         * Uzywajac "GlobalCounter" (Zadanie 2, @Singleton) napisz DWIE rozne
         * klasy "ServiceA" i "ServiceB", KAZDA zalezna (przez @Inject
         * konstruktor) od "GlobalCounter". Wywolaj "increment()" w ServiceA,
         * a nastepnie odczytaj wartosc w ServiceB - potwierdz PASS/FAIL, ze
         * OBIE klasy widza TE SAMA, wspoldzielona instancje licznika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ProvidesMethodWithInjectedDependency {
        /*
         * 🧪 Zadanie 12:
         * Napisz metode "@Provides" ktora SAMA przyjmuje argument bedacy inna
         * zaleznoscia zarzadzana przez Guice (np. "@Provides TimestampedGreeting
         * provideGreeting(Clock clock)") - w komentarzu wyjasnij, ze metody
         * @Provides moga miec DOWOLNA liczbe parametrow, ktore Guice
         * automatycznie wstrzykuje, dokladnie jak w konstruktorze @Inject.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ProviderForLazyExpensiveObject {
        /*
         * 🧪 Zadanie 13:
         * Napisz klase "HeavyReportGenerator" (konstruktor wypisuje "Tworze
         * ciezki raport..." - symulacja kosztownej operacji). Napisz klase
         * "ReportController" zalezna od "Provider<HeavyReportGenerator>" -
         * wywolaj "provider.get()" TYLKO wewnatrz metody "generateIfNeeded(boolean
         * needed)", jesli "needed == true". Pokaz w main(), ze przy
         * "needed = false" konstruktor "HeavyReportGenerator" NIGDY sie nie
         * wykonuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NamedBindingsForDatabaseUrls {
        /*
         * 🧪 Zadanie 14:
         * Zwiaz DWA Stringi pod etykietami "@Named("primaryDbUrl")" i
         * "@Named("replicaDbUrl")" przez "toInstance(...)". Napisz klase
         * "DatabaseRouter" zalezna od OBU (osobne parametry konstruktora z
         * "@Named") i metode "route(boolean readOnly)" wybierajaca odpowiedni URL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RefactorLesson19DecoratorWithNamedInnerGateway {
        /*
         * 🧪 Zadanie 15:
         * Dokoncz zadanie z Lesson19.Exercise13 (LoggingNotifier opakowujacy
         * Notifier) - tym razem uzyj "@Named" do jawnego wskazania, KTORY
         * "Notifier" ma byc opakowany (np. "@Named("real")" na wewnetrznej
         * implementacji, a "LoggingNotifier" konstruktor przyjmuje
         * "@Named("real") Notifier delegate").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SingletonProvidesCountingCreations {
        /*
         * 🧪 Zadanie 16:
         * Napisz modul z polem statycznym "static int creations = 0" i metoda
         * "@Provides @Singleton" zwiekszajaca ten licznik przy KAZDYM
         * wykonaniu. Pobierz powiazany typ PIEC razy z tego samego Injectora i
         * wypisz PASS/FAIL sprawdzajac, ze "creations == 1".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultipleInjectorsHaveIndependentSingletons {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj DWA NIEZALEZNE Injectory z TYM SAMYM modulem zawierajacym
         * "@Singleton" (np. AppConfig z lekcji). Pobierz "AppConfig" z KAZDEGO
         * Injectora osobno i wypisz PASS/FAIL sprawdzajac, ze instancje z
         * ROZNYCH Injectorow sa RUZNE (singleton dziala TYLKO w ramach
         * jednego Injectora, nie globalnie w calej JVM).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ProviderInLoopProducesIndependentObjects {
        /*
         * 🧪 Zadanie 18:
         * Napisz klase "OrderBatchBuilder" zalezna od "Provider<SessionId>"
         * (Zadanie 1). W metodzie "buildOrders(int count)" zbierz "count"
         * obiektow "SessionId" do "List<SessionId>" i wypisz PASS/FAIL
         * sprawdzajac (przez "Set" i porownanie rozmiarow), ze WSZYSTKIE sa
         * od siebie ROZNE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BindToInstanceForSharedMutableConfig {
        /*
         * 🧪 Zadanie 19:
         * Napisz klase "FeatureFlags" (mutowalna, np. "Map<String, Boolean>"
         * jako pole) - utworz JEDEN obiekt w main() PRZED stworzeniem
         * Injectora, zwiaz go przez "toInstance(...)", pobierz go z Injectora
         * w DWOCH roznych klasach zaleznych i pokaz, ze zmiana flagi w jednej
         * klasie jest WIDOCZNA w drugiej (to ta sama instancja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DiscussPrivateModuleAndMultibindingsUseCases {
        /*
         * 🧪 Zadanie 20:
         * W komentarzu (min. 5 zdan, bez kodu) opisz JEDEN konkretny scenariusz,
         * gdzie przydalby sie "PrivateModule" (ukrycie wiazania wewnetrznego) i
         * JEDEN scenariusz, gdzie przydalby sie "Multibinder" (wstrzykniecie
         * "Set<T>" wielu implementacji naraz) - odwolaj sie do problemu z
         * Lesson19.Exercise23 (CompositeNotifier).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullPrimaryBackupGatewayWithFallbackLogic {
        /*
         * 🧪 Zadanie 21:
         * Rozszerz "DualGatewayOrderService" z lekcji (@Named "primary"/"backup")
         * o REALNA logike fallback: "PrimaryGateway" (StubPaymentGateway z
         * Lesson18.Exercise14, "shouldFail = true") rzuca wyjatek przy "charge(...)" -
         * przechwyc go i automatycznie sprobuj przez "backup". Zademonstruj
         * caly scenariusz zbudowany przez Injector.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SingletonSharedCacheAcrossMultipleServices {
        /*
         * 🧪 Zadanie 22:
         * Napisz "@Singleton InMemoryCacheService" (Zadanie 3) uzywany
         * jednoczesnie przez TRZY rozne serwisy (np. "ProductService",
         * "PricingService", "InventoryService" - kazdy zalezny od CacheService
         * przez @Inject). Pokaz, ze wpis dodany przez JEDEN serwis jest
         * odczytywalny przez POZOSTALE (wspoldzielony stan przez singleton).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ProvidesMethodBuildingFromExternalConfig {
        /*
         * 🧪 Zadanie 23:
         * Napisz "@Provides" metode budujaca "DataSourceConfig" (rekord/klasa z
         * polami url/username/maxPoolSize) na podstawie WARTOSCI ODCZYTANYCH z
         * kilku "@Named" Stringow/liczb wstrzyknietych jako PARAMETRY tej samej
         * metody @Provides (kazdy zwiazany osobno przez toInstance w tym samym
         * module).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CombineThreeModulesRealisticApp {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj "realistyczna" aplikacje z TRZEMA modulami: "PersistenceModule"
         * (CacheService jako @Singleton), "PaymentModule" (@Named primary/backup
         * PaymentGateway), "ConfigModule" (toInstance dla nazwy aplikacji i
         * maxRetries). Zloz WSZYSTKIE w jednym "Guice.createInjector(...)" i
         * pobierz klase "ApplicationBootstrap" zalezna od WSZYSTKICH trzech
         * obszarow naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ProviderVsDirectInjectionPerformanceDiscussion {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj DWIE wersje tej samej klasy: "DirectInjectionService"
         * (zalezny wprost od "HeavyReportGenerator" z Zadania 13 - budowany
         * przy KAZDYM getInstance) i "LazyInjectionService" (zalezny od
         * "Provider<HeavyReportGenerator>"). W komentarzu (min. 4 zdania)
         * wyjasnij roznice w momencie tworzenia ciezkiego obiektu miedzy tymi
         * dwoma podejsciami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_NamedQualifierForEnvironmentSpecificBinding {
        /*
         * 🧪 Zadanie 26:
         * Napisz DWA moduly: "DevEnvironmentModule" i "ProdEnvironmentModule",
         * kazdy wiazacy INNY "@Named("apiBaseUrl")" String (np. "http://localhost:8080"
         * vs "https://api.produkcja.pl"). Napisz metode
         * "buildClientForEnvironment(boolean isProd)" zwracajaca "ApiClient"
         * (zalezny od "@Named("apiBaseUrl")") zbudowany z WLASCIWEGO modulu w
         * zaleznosci od flagi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ErrorWhenMissingNamedBinding {
        /*
         * 🧪 Zadanie 27:
         * Napisz klase zalezna od "@Named("missingKey") String" BEZ zadnego
         * wiazania dla tej etykiety w zadnym module. Sprobuj pobrac ja przez
         * Injector, zlap "com.google.inject.ConfigurationException" i wypisz
         * PRZYJAZNY komunikat tlumaczacy, ze brakuje wiazania dla konkretnej
         * (typ, etykieta) pary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CapstoneOrderPipelineWithSingletonsAndNamed {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj PELNY pipeline zamowien: "@Singleton OrderIdGenerator"
         * (generuje kolejne numery, wspoldzielony), DWIE "@Named" implementacje
         * "PaymentGateway" (primary/backup, jak w lekcji), "@Provides"
         * metoda budujaca "OrderContext" (laczaca kilka wstrzykniec naraz), i
         * finalna klase "OrderPipeline" zalezna od WSZYSTKICH powyzszych.
         * Zbuduj CALY graf przez JEDEN Injector z minimum DWOMA modulami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareScopesDecisionTreeInComment {
        /*
         * 🧪 Zadanie 29:
         * W komentarzu (min. 6 zdan) napisz "drzewo decyzyjne" - dla danej
         * klasy, jak zdecydowac, czy powinna byc: bez scope'u, "@Singleton",
         * budowana przez "@Provides", czy wstrzykiwana jako "Provider<T>".
         * Podaj po jednym konkretnym przykladzie z tej lekcji dla kazdej z 4 opcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneRefactorLesson19ToUseAllLesson20Tools {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: wez "OrderController" z Lesson19 (Przyklad 6 -
         * zalezny od OrderService i Notifier) i rozbuduj CALY graf o narzedzia z
         * tej lekcji: 1) "PaymentGateway" niech ma DWIE "@Named" implementacje
         * (primary/backup) z logika fallback (jak Zadanie 21), 2) "Notifier"
         * niech bedzie "@Singleton" (wspoldzielony licznik wyslanych wiadomosci),
         * 3) dodaj "@Provides" metode budujaca obiekt "OrderMetadata" (np. z
         * timestampem z wstrzykowanego "Clock"), 4) uzyj "Provider<SessionId>"
         * (Zadanie 1) do generowania unikalnego ID KAZDEGO zamowienia. Zloz
         * WSZYSTKO z minimum TRZECH modulow w main() i wykonaj pelny scenariusz
         * demo z komentarzem podsumowujacym, ktorego narzedzia (spomiedzy
         * @Singleton/@Provides/Provider/@Named/toInstance) uzyles i dlaczego.
         */
        public static void main(String[] args) { }
    }
}
