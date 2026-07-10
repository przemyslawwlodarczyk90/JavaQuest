package com.example.javaquest._15_jvm_internals.Lesson04_CustomClassLoaders;

public class _Exercises_Lesson04_CustomClassLoaders {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_ExtendClassLoaderMinimal {
        /*
         * 🧪 Zadanie 1:
         * Stwórz minimalną klasę rozszerzającą ClassLoader (bez
         * przesłaniania żadnej metody) i wypisz nazwę jej klasy oraz
         * rodzica (getParent()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_ExplainFindClassContract {
        /*
         * 🧪 Zadanie 2:
         * W komentarzu wyjaśnij, do czego służy metoda findClass(String)
         * i kiedy dokładnie jest wywoływana w kontekście modelu
         * parent-first (odwołaj się do Lekcji 3).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_CompileSimpleClassToByteArray {
        /*
         * 🧪 Zadanie 3:
         * Skompiluj (przez ToolProvider.getSystemJavaCompiler() do
         * katalogu tymczasowego) prostą klasę "Foo" z pustym konstruktorem
         * i wczytaj jej bajty do tablicy byte[] (Files.readAllBytes).
         * Wypisz długość tablicy bajtów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_ImplementFindClassWithDefineClass {
        /*
         * 🧪 Zadanie 4:
         * Napisz własną klasę rozszerzającą ClassLoader z przesłoniętą
         * metodą findClass(String), która woła defineClass(...) na
         * przekazanej w konstruktorze tablicy bajtów. Załaduj przez nią
         * skompilowaną wcześniej klasę i wypisz jej nazwę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_CreateInstanceViaReflectionAfterCustomLoad {
        /*
         * 🧪 Zadanie 5:
         * Używając własnego class loadera z poprzedniego zadania, utwórz
         * instancję załadowanej klasy przez getDeclaredConstructor().newInstance()
         * i wypisz wynik toString() tej instancji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_InvokeMethodViaReflectionOnCustomLoadedClass {
        /*
         * 🧪 Zadanie 6:
         * Skompiluj klasę z metodą instancyjną zwracającą String (np.
         * "opis()"). Załaduj ją własnym ClassLoaderem i wywołaj tę metodę
         * przez refleksję (Method.invoke), wypisując wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_CatchClassNotFoundFromCustomLoader {
        /*
         * 🧪 Zadanie 7:
         * Użyj własnego ClassLoadera z Lekcji (findClass rzucający
         * ClassNotFoundException dla nieznanej nazwy) i spróbuj załadować
         * klasę o niepasującej nazwie - złap wyjątek i wypisz komunikat.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ListUseCasesOfCustomClassLoaders {
        /*
         * 🧪 Zadanie 8:
         * Wypisz (na sztywno) co najmniej 4 praktyczne zastosowania
         * własnych class loaderów poznane w tej lekcji, każde z jednym
         * zdaniem uzasadnienia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_PrintClassLoaderOfCustomLoadedInstance {
        /*
         * 🧪 Zadanie 9:
         * Załaduj klasę własnym ClassLoaderem, utwórz instancję i wypisz
         * getClass().getClassLoader() tej instancji - potwierdź, że to
         * TWÓJ custom loader, nie Application Class Loader.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_CompareCustomLoaderParentToApplicationLoader {
        /*
         * 🧪 Zadanie 10:
         * Stwórz własny ClassLoader podając jako rodzica
         * Thread.currentThread().getContextClassLoader(). Wypisz
         * getParent() tego loadera i porównaj go z Application Class
         * Loaderem tej klasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_LoadSameClassTwiceWithTwoLoaderInstances {
        /*
         * 🧪 Zadanie 11:
         * Utwórz DWIE instancje własnego ClassLoadera (z tymi samymi
         * bajtami klasy) i załaduj przez każdą tę samą klasę. Porównaj
         * zwrócone obiekty Class operatorem "==" i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_DemonstrateClassCastExceptionAcrossLoaders {
        /*
         * 🧪 Zadanie 12:
         * Na bazie poprzedniego zadania: utwórz instancje z obu wersji
         * klasy i spróbuj (przez isInstance() lub próbę rzutowania przez
         * wspólny interfejs) pokazać, że instancja z loadera #1 NIE jest
         * zgodna typowo z klasą z loadera #2. Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_LoadClassFromRealFileOnDisk {
        /*
         * 🧪 Zadanie 13:
         * Zmodyfikuj findClass(...) tak, aby czytała bajty klasy z
         * PLIKU .class na dysku (Files.readAllBytes(Path)) zamiast z
         * tablicy przekazanej w konstruktorze. Skompiluj klasę do
         * katalogu tymczasowego i załaduj ją tą wersją loadera.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_ImplementLoaderForMultipleClasses {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz własny ClassLoader tak, aby przyjmował Map<String,
         * byte[]> (nazwa klasy -> bajty) i potrafił załadować DOWOLNĄ z
         * kilku różnych klas po nazwie. Skompiluj 2-3 klasy i załaduj je
         * wszystkie przez jedną instancję loadera.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_MeasureMemoryReleaseAfterLoaderBecomesUnreachable {
        /*
         * 🧪 Zadanie 15:
         * Załaduj klasę przez własny loader w osobnym bloku/metodzie tak,
         * aby ani loader, ani klasa, ani instancja nie miały już żywych
         * referencji poza WeakReference. Wywołaj System.gc() (tylko
         * sugestia dla JVM) i sprawdź (WeakReference.get()), czy obiekt
         * zniknął - wypisz wynik (może się różnić między uruchomieniami).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_BuildPluginInterfaceAndTwoImplementations {
        /*
         * 🧪 Zadanie 16:
         * Skompiluj wspólny interfejs "Plugin" z metodą String run() oraz
         * DWIE implementujące go klasy (różne komunikaty). Załaduj obie
         * przez WSPÓLNY własny ClassLoader (znający oba zestawy bajtów) i
         * wywołaj run() na obu przez refleksję.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_SimulateHotReloadWithTwoVersionsOfSameClass {
        /*
         * 🧪 Zadanie 17:
         * Skompiluj DWIE wersje tej samej klasy o tej samej nazwie, ale
         * różnej treści metody (np. zwracany String "wersja 1" vs
         * "wersja 2"), każdą do OSOBNEGO katalogu wyjściowego. Załaduj
         * wersję 1 jednym loaderem, potem wersję 2 DRUGIM loaderem -
         * zasymuluj w ten sposób "hot-reload" i wypisz wyniki obu wywołań.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ImplementLoaderThatDelegatesFirstThenOverrides {
        /*
         * 🧪 Zadanie 18:
         * Napisz loader, który przesłania loadClass(String, boolean)
         * (zamiast tylko findClass) tak, by dla klas zaczynających się od
         * "com.example." NAJPIERW próbował delegacji do rodzica, a dla
         * pozostałych zawsze próbował findClass. Przetestuj na 2 różnych
         * nazwach klas i wypisz, która ścieżka została użyta.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_BuildChildParentCustomLoaderChain {
        /*
         * 🧪 Zadanie 19:
         * Stwórz DWA własne loadery, gdzie loader B ma jako rodzica
         * loader A (obydwa Twoje, custom). Załaduj przez B klasę znaną
         * tylko A - upewnij się, że delegacja parent-first zadziała mimo
         * że oba loadery są "custom", nie wbudowane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ExplainClassIdentityFormulaWithExample {
        /*
         * 🧪 Zadanie 20:
         * W komentarzu (min. 3 zdania) wyjaśnij formułę "tożsamość klasy w
         * JVM = pełna nazwa + class loader" i podaj WŁASNY (inny niż w
         * teorii) przykład sytuacji, w której dwie identyczne z pozoru
         * klasy są traktowane jako różne typy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_BuildIsolatedPluginRegistryUsingCustomLoaders {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj mini-"rejestr pluginów": mapę String (nazwa pluginu) ->
         * własny ClassLoader + załadowana klasa implementująca wspólny
         * interfejs. Skompiluj i zarejestruj co najmniej 3 pluginy, każdy
         * we WŁASNYM, IZOLOWANYM loaderze, a następnie wywołaj metodę
         * wspólnego interfejsu na każdym z nich po kolei.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ImplementByteArrayXorObfuscationBeforeDefineClass {
        /*
         * 🧪 Zadanie 22:
         * Skompiluj klasę, "zaszyfruj" jej bajty prostym XOR-em z ustalonym
         * kluczem (np. pojedynczy bajt) i zapisz zaszyfrowaną wersję.
         * Napisz loader, który w findClass(...) NAJPIERW odszyfrowuje
         * bajty (odwrotny XOR), a dopiero potem woła defineClass(...).
         * Zweryfikuj, że załadowana klasa działa poprawnie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_CompareTimingOfFirstVsSubsequentCustomLoad {
        /*
         * 🧪 Zadanie 23:
         * Zmierz (System.nanoTime()) czas pierwszego załadowania klasy
         * przez własny loader (findClass + defineClass) oraz czas
         * KOLEJNEGO zapytania o tę samą klasę przez ten sam loader
         * (loadClass ponownie) - ClassLoader cache'uje już załadowane
         * klasy. Wypisz oba czasy i porównaj.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_ThrowLinkageErrorByDefiningSameClassTwiceInOneLoader {
        /*
         * 🧪 Zadanie 24:
         * Spróbuj wywołać defineClass(...) DWA RAZY dla tej samej nazwy
         * klasy na TYM SAMYM loaderze (bez cache'owania przez loadClass) -
         * powinieneś dostać LinkageError ("duplicate class definition").
         * Złap ten błąd i wypisz komunikat wyjaśniający dlaczego wystąpił.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_BuildLoaderThatLogsEveryFindClassCall {
        /*
         * 🧪 Zadanie 25:
         * Rozszerz własny loader tak, by KAŻDE wywołanie findClass(...)
         * logowało (System.out) nazwę żądanej klasy oraz to, czy
         * zakończyło się sukcesem czy ClassNotFoundException. Przetestuj
         * na kilku nazwach (istniejących i nieistniejących).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ImplementVersionedPluginLoaderSelection {
        /*
         * 🧪 Zadanie 26:
         * Skompiluj 2 wersje tej samej klasy pluginu (różne zachowanie),
         * każdą do osobnego katalogu. Napisz metodę wybierającą, KTÓRY
         * katalog (a więc która wersja) ma zostać załadowana na podstawie
         * parametru (np. "v1"/"v2"), tworzącą dedykowany loader dla
         * wybranej wersji. Przetestuj obie ścieżki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_CompareMemoryFootprintOfManyIsolatedLoaders {
        /*
         * 🧪 Zadanie 27:
         * Załaduj tę samą (lub bardzo podobną) klasę przez np. 20 RÓŻNYCH
         * instancji własnego loadera (symulując 20 "wtyczek"), trzymając
         * referencje do wszystkich klas w liście. Wypisz Runtime.getRuntime()
         * .totalMemory()/freeMemory() przed i po, komentując przybliżony
         * wpływ (świadomość, że każdy loader + klasa zajmuje pamięć
         * metaspace, temat pogłębiony w dalszych lekcjach rozdziału).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_ImplementServiceStyleDispatchAcrossIsolatedPlugins {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj wspólny interfejs "Command" z metodą String execute(String arg).
         * Skompiluj 3 różne implementacje (np. "upper", "reverse", "repeat")
         * i załaduj każdą we własnym, izolowanym loaderze. Napisz
         * dyspozytor (dispatcher), który na podstawie nazwy komendy
         * wybiera odpowiednią zaimplementowaną klasę i wywołuje execute(...)
         * z przykładowym argumentem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_ExplainWhyClassUnloadingRequiresLoaderUnreachability {
        /*
         * 🧪 Zadanie 29:
         * W komentarzu wyjaśnij (min. 4 zdania), dlaczego JEDYNYM sposobem
         * na "wyładowanie" (unload) klasy z JVM jest sprawienie, by SAM
         * class loader (i wszystkie załadowane przez niego klasy oraz ich
         * instancje) stał się nieosiągalny dla Garbage Collectora - odnieś
         * się do tego, co GC odzyskuje "z metaspace" (temat pogłębiony w
         * dalszych lekcjach rozdziału o pamięci/GC).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_BuildFullPluginFrameworkWithIsolationAndReload {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny mini-framework pluginów: (1) wspólny interfejs
         * Plugin z metodą String describe(), (2) rejestr pluginów jako
         * mapa nazwa -> (loader, instancja), (3) możliwość ZAREJESTROWANIA
         * nowej wersji pluginu o tej samej nazwie w NOWYM loaderze (stara
         * instancja zostaje usunięta z rejestru, więc traci referencje),
         * (4) metodę wypisującą describe() wszystkich aktualnie
         * zarejestrowanych pluginów. Zademonstruj rejestrację, wywołanie,
         * "przeładowanie" jednego pluginu do nowej wersji i ponowne
         * wywołanie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
