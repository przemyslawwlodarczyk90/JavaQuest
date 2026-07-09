package com.example.javaquest._14_advancedjava.Lesson26_ServiceLoaderAndSpi;

public class _Exercises_Lesson26_ServiceLoaderAndSpi {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSpiInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3 zdania),
         * czym jest wzorzec SPI (Service Provider Interface) i czym rozni sie od
         * zwyklego "new KonkretnaImplementacja()".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DefinePaymentServiceInterface {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj (jako osobna klase/interfejs w tym pliku lub w nowym pliku w tym
         * pakiecie) interfejs PaymentService z metoda String pay(double amount).
         * Nic wiecej - to tylko "API" uslugi, bez implementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TwoManualImplementations {
        /*
         * 🧪 Zadanie 3:
         * Napisz DWIE klasy implementujace PaymentService z Zadania 2: CardPaymentService
         * (zwraca "Zaplacono karta: " + amount) i CashPaymentService (zwraca "Zaplacono
         * gotowka: " + amount). Wypisz wyniki obu wywolan dla amount = 99.99.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteMetaInfServicesFileContent {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: w komentarzu napisz DOKLADNA sciezke i zawartosc pliku
         * META-INF/services, ktory zarejestrowalby obie implementacje z Zadania 3
         * jako dostawcow interfejsu PaymentService (podaj pelne nazwy pakietowe).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ServiceLoaderLoadReturnsEmptyIterator {
        /*
         * 🧪 Zadanie 5:
         * Uzyj java.util.ServiceLoader.load(PaymentService.class) (z interfejsem z
         * Zadania 2, BEZ tworzenia pliku META-INF/services) i wypisz, ile dostawcow
         * zostalo znalezionych (oczekiwane: 0) oraz ze NIE zostal rzucony zaden wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListKnownJdkSpiExamples {
        /*
         * 🧪 Zadanie 6:
         * Wypisz na konsoli 4 przyklady mechanizmow standardowej Javy oparte na SPI,
         * wymienione w lekcji (java.sql.Driver, FileSystemProvider, DocumentBuilderFactory,
         * Chronology) razem z jednozdaniowym opisem kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ManualProviderRegistryMap {
        /*
         * 🧪 Zadanie 7:
         * Bez ServiceLoadera: stworz Map<String, PaymentService> (z Zadania 2/3) "na
         * reczne", recznie rejestrujac obie implementacje pod kluczami "card" i "cash".
         * Wypisz wynik wywolania pay(50.0) dla obu kluczy - to pokazuje, co ServiceLoader
         * robi za nas automatycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareSpiVsNewKeyword {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wypisz w komentarzu 3 zalety SPI (luzne powiazanie,
         * rozszerzalnosc, wymiennosc) z lekcji, kazda z jednym konkretnym przykladem
         * z kodu (moze byc z Zadan 2-3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ThirdImplementationAddedWithoutTouchingApi {
        /*
         * 🧪 Zadanie 9:
         * Dopisz TRZECIA implementacje PaymentService (np. BlikPaymentService, zwraca
         * "Zaplacono BLIK: " + amount) BEZ modyfikowania interfejsu PaymentService ani
         * dwoch poprzednich klas - wypisz jej wynik dla amount = 15.5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ProviderMustHaveNoArgConstructor {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: w komentarzu wyjasnij, jaki wymog ma klasa-dostawca, aby
         * ServiceLoader mogl utworzyc jej instancje (publiczny konstruktor
         * bezargumentowy albo, od Javy 9, publiczna statyczna metoda provider()).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CompileInterfaceAndImplToTempDir {
        /*
         * 🧪 Zadanie 11:
         * Uzywajac ToolProvider.getSystemJavaCompiler() (jak w lekcji), wygeneruj i
         * skompiluj do tymczasowego katalogu interfejs NotificationService (metoda
         * String notify(String message)) i jedna implementacje EmailNotificationService.
         * Wypisz kod wyjscia kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteMetaInfServicesFileForRealAndLoad {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz Zadanie 11: utworz prawdziwy plik
         * META-INF/services/notify.NotificationService w skompilowanym katalogu klas,
         * wczytaj go przez URLClassLoader + ServiceLoader.load(...) (analogicznie do
         * lekcji) i wypisz wynik wywolania notify("Test") na znalezionym dostawcy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TwoProvidersInOneFile {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz Zadanie 12 o druga implementacje SmsNotificationService i dopisz ja
         * jako druga linie w pliku META-INF/services. Wypisz wyniki notify("Test") dla
         * OBU znalezionych dostawcow, z nazwa klasy kazdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ServiceLoaderStreamWithoutInstantiating {
        /*
         * 🧪 Zadanie 14:
         * Na bazie Zadania 13, uzyj ServiceLoader.stream() (zamiast zwyklej iteracji) i
         * wypisz TYLKO nazwy klas dostawcow (Provider.type().getName()) BEZ tworzenia
         * ich instancji (bez wywolywania Provider.get()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MissingRegistrationFileVsEmptyFile {
        /*
         * 🧪 Zadanie 15:
         * Porownaj 2 scenariusze: (a) plik META-INF/services w ogole nie istnieje, (b)
         * plik istnieje, ale jest pusty. W obu przypadkach wypisz liczbe znalezionych
         * dostawcow przez ServiceLoader (oczekiwane: 0 w obu) - potwierdz, ze zachowanie
         * jest identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_InvalidClassNameInRegistrationFile {
        /*
         * 🧪 Zadanie 16:
         * Wpisz do pliku META-INF/services nazwe klasy, ktora NIE ISTNIEJE w skompilowanym
         * katalogu (literowka). Sprobuj zaladowac dostawcow przez ServiceLoader,
         * zlap wyjatek (ServiceConfigurationError) podczas iteracji i wypisz jego typ
         * oraz komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ProviderWithoutNoArgConstructorFails {
        /*
         * 🧪 Zadanie 17:
         * Skompiluj implementacje uslugi, ktora ma TYLKO konstruktor z argumentem (bez
         * bezargumentowego). Zarejestruj ja w META-INF/services, sprobuj zaladowac przez
         * ServiceLoader, zlap ServiceConfigurationError podczas iteracji i wypisz jego
         * komunikat jako dowod, ze wymog konstruktora bezargumentowego jest egzekwowany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ReloadAfterAddingNewProvider {
        /*
         * 🧪 Zadanie 18:
         * Zaladuj ServiceLoader dla interfejsu z JEDNYM zarejestrowanym dostawca,
         * wypisz liczbe znalezionych. Dopisz DRUGA implementacje do pliku
         * META-INF/services NA DYSKU, wywolaj serviceLoader.reload() i wypisz liczbe
         * znalezionych ponownie (oczekiwane: teraz 2) - pokazujac, ze bez reload()
         * ServiceLoader korzysta z wewnetrznego cache.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FactoryPatternViaServiceLoader {
        /*
         * 🧪 Zadanie 19:
         * Napisz metode statyczna <T> T loadSingleProvider(Class<T> serviceInterface,
         * ClassLoader loader), ktora zwraca PIERWSZEGO znalezionego dostawce (albo
         * rzuca wyjatek, jesli brak) - wzorzec "fabryki" opartej o ServiceLoader.
         * Przetestuj ja na interfejsie z 1 zarejestrowanym dostawca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CleanupTempDirectoryInFinally {
        /*
         * 🧪 Zadanie 20:
         * Powtorz pelne demo z Zadania 12 (kompilacja + META-INF/services + ServiceLoader),
         * ale opakuj caly katalog tymczasowy w blok try/finally, ktory na koniec
         * REKURENCYJNIE usuwa katalog (Files.walk + sorted(Comparator.reverseOrder())).
         * Wypisz potwierdzenie, ze katalog juz nie istnieje (Files.exists == false).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_PluginDiscoveryFromMultipleJars {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj DWA osobne, skompilowane katalogi klas ("jar1" i "jar2"), kazdy z
         * WLASNYM plikiem META-INF/services rejestrujacym INNEGO dostawce tego samego
         * interfejsu. Zaladuj URLClassLoader z OBU katalogow naraz (dwa URL-e w tablicy)
         * i wypisz, ze ServiceLoader znajduje dostawcow z OBU "jarow" jednoczesnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PriorityOrderedProviders {
        /*
         * 🧪 Zadanie 22:
         * Dodaj do interfejsu uslugi metode int priority(). Zaladuj wszystkich
         * dostawcow przez ServiceLoader do listy, posortuj ja wg priority() (malejaco)
         * i wypisz posortowana kolejnosc - symulujac wybor "najlepszego" dostawcy
         * sposrod wielu zarejestrowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ServiceLoaderWithCustomClassLoaderHierarchy {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj TRZY-poziomowa hierarchie URLClassLoaderow (dziecko -> rodzic -> platform)
         * gdzie interfejs uslugi jest w katalogu "rodzica", a implementacja + plik
         * META-INF/services w katalogu "dziecka". Wypisz, czy ServiceLoader.load(...,
         * dzieckoLoader) znajduje dostawce (i wyjasnij w komentarzu dlaczego dziala albo
         * dlaczego nie - delegacja class loaderow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_StaticProviderMethodInsteadOfConstructor {
        /*
         * 🧪 Zadanie 24:
         * Skompiluj implementacje uslugi, ktora ma PRYWATNY konstruktor, ale publiczna
         * statyczna metode "provider()" zwracajaca instancje (wzorzec singleton/factory
         * method z Javy 9+ dla ServiceLoadera). Zarejestruj ja i wypisz, ze ServiceLoader
         * poprawnie ja znajduje i uzywa (mimo braku publicznego konstruktora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureServiceLoaderLazyBehaviour {
        /*
         * 🧪 Zadanie 25:
         * Dodaj do konstruktora implementacji println("Tworzenie instancji: " + ...).
         * Wywolaj ServiceLoader.load(...) (BEZ iteracji) i wypisz, ze konstruktor JESZCZE
         * sie nie wykonal. Dopiero po pierwszym iterator().next() wypisz, ze konstruktor
         * sie wykonal - dowod na leniwosc (laziness) ServiceLoadera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FallbackToDefaultImplementation {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode getServiceOrDefault(Class<T> serviceInterface, ClassLoader loader,
         * T defaultImpl), ktora zwraca pierwszego dostawce znalezionego przez
         * ServiceLoader, a jesli brak - zwraca podana wartosc domyslna. Przetestuj ja
         * w OBU scenariuszach (z dostawca i bez).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ValidateAllProvidersImplementInterfaceCorrectly {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode walidujaca, ktora dla kazdego wpisu w pliku META-INF/services
         * sprawdza (przez refleksje, PRZED wywolaniem ServiceLoadera) czy dana klasa
         * faktycznie implementuje docelowy interfejs oraz ma publiczny konstruktor
         * bezargumentowy - wypisz raport OK/BLAD dla kilku przykladowych wpisow
         * (poprawnych i celowo blednych).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SimulateJdbcDriverManagerPattern {
        /*
         * 🧪 Zadanie 28:
         * Zamodeluj uproszczony odpowiednik java.sql.DriverManager: interfejs
         * MiniDriver z metoda boolean accepts(String url) i String connect(String url),
         * dwie implementacje obslugujace rozne "protokoly" (np. "mini://" i "test://").
         * Napisz metode connect(String url), ktora przez ServiceLoader znajduje
         * PIERWSZEGO dostawce, dla ktorego accepts(url) zwraca true, i go uzywa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BenchmarkServiceLoaderVsReflectionScan {
        /*
         * 🧪 Zadanie 29:
         * Zmierz System.nanoTime() dla (a) znalezienia dostawcow przez ServiceLoader
         * (odczyt pliku META-INF/services) oraz (b) recznego skanowania wszystkich
         * plikow .class w katalogu i sprawdzania przez refleksje, czy implementuja dany
         * interfejs. Wypisz oba czasy i wniosek, ktore podejscie jest szybsze i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_EndToEndPluginSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny, wieloetapowy "system wtyczek": interfejs TextTransformer
         * (metoda String transform(String input)), TRZY implementacje (np. odwracanie
         * tekstu, zamiana na wielkie litery, usuwanie spacji) skompilowane i
         * zarejestrowane przez META-INF/services, zaladowane przez ServiceLoader, a
         * nastepnie zaaplikowane KOLEJNO (jak pipeline/lancuch) do jednego tekstu
         * wejsciowego - wypisz wynik po kazdym etapie transformacji oraz wynik koncowy.
         * Posprzataj katalog tymczasowy w finally.
         */
        public static void main(String[] args) { }
    }
}
