package com.example.javaquest._15_jvm_internals.Lesson04_CustomClassLoaders;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class _Lesson04_CustomClassLoaders {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 4: WLASNE CLASS LOADERY ===\n");

        /*
         * ============================================================
         * 🔹 PO CO PISAC WLASNY ClassLoader?
         * ============================================================
         * Domyslna hierarchia (Bootstrap -> Platform -> Application,
         * Lekcja 3) wystarcza w zdecydowanej wiekszosci programow. Ale
         * czasem potrzeba WIECEJ kontroli nad tym, SKAD i JAK klasy sa
         * ladowane:
         *
         *  - SYSTEMY PLUGINOW - aplikacja chce ladowac klasy z JAR-ow
         *    dostarczonych PO fakcie (np. wtyczki), bez umieszczania ich
         *    na glownym classpath.
         *  - HOT-RELOAD - narzedzia deweloperskie (np. serwery aplikacji)
         *    potrafia PRZELADOWAC zmieniona klase w trakcie dzialania
         *    programu, tworzac NOWY class loader dla nowej wersji kodu.
         *  - IZOLACJA BIBLIOTEK - kontenery serwletow (jak Tomcat, patrz
         *    `_07_servlets`) daja KAZDEJ aplikacji webowej WLASNY class
         *    loader, zeby dwie aplikacje moglyby uzywac RÓZNYCH wersji tej
         *    samej biblioteki bez konfliktu.
         *  - SZYFROWANIE/OBFUSKACJA - wlasny loader moze odszyfrowac bajty
         *    .class "w locie" tuz przed przekazaniem ich do JVM.
         *
         * Wspolny mianownik: wlasny ClassLoader pozwala przejac kontrole
         * nad KROKIEM "LOADING" z cyklu zycia klasy (Lekcja 3) - czyli
         * SKAD dokladnie pochodza bajty .class.
         */
        explainWhyCustomClassLoaders();

        /*
         * ============================================================
         * 🔹 JAK NAPISAC WLASNY ClassLoader
         * ============================================================
         * Standardowy wzorzec: rozszerz java.lang.ClassLoader i przeslon
         * metode protected Class<?> findClass(String name). To WLASNIE
         * ta metoda jest wywolywana przez domyslna implementacje
         * loadClass(...) DOPIERO PO TYM, jak model parent-first (Lekcja 3)
         * zawiedzie - czyli gdy ANI Bootstrap, ANI Platform, ANI rodzic
         * Twojego loadera nie potrafi znalezc danej klasy.
         *
         * Wewnatrz findClass(...) Twoim zadaniem jest:
         *  1) Zdobyc surowe bajty .class (z pliku, tablicy bajtow, sieci,
         *     zaszyfrowanego zrodla...).
         *  2) Przekazac je do defineClass(name, bytes, offset, length) -
         *     to WBUDOWANA metoda ClassLoader, ktora faktycznie tworzy
         *     obiekt java.lang.Class z surowych bajtow (i uruchamia
         *     Linking - weryfikacje bajtkodu).
         *
         * Ponizej: konkretna implementacja ladujaca klase z tablicy
         * bajtow trzymanej w pamieci (np. wygenerowanej wczesniej przez
         * javac).
         */
        System.out.println("[Wlasny ClassLoader - InMemoryClassLoader]");
        System.out.println("Rozszerza ClassLoader, przeslania findClass(...), wola defineClass(...).\n");

        Path workDir = Files.createTempDirectory("lesson04-custom-loader");
        try {
            Path srcDir = workDir.resolve("src");
            Path classesDir = workDir.resolve("classes");
            Files.createDirectories(srcDir);
            Files.createDirectories(classesDir);

            // Generujemy prosta klase PluginGreeter - to bedzie nasz
            // "plugin" ladowany przez wlasny class loader.
            String className = "PluginGreeter";
            Path pluginSrc = srcDir.resolve(className + ".java");
            Files.writeString(pluginSrc, """
                    public class PluginGreeter {
                        private final String name = "Plugin v1";

                        public String greet() {
                            return "Witaj z " + name + ", zaladowanego przez WLASNY ClassLoader!";
                        }
                    }
                    """);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("Brak systemowego kompilatora - upewnij sie, ze uzywasz JDK.");
            }
            int compileResult = compiler.run(null, System.out, System.err,
                    "-d", classesDir.toString(), pluginSrc.toString());
            System.out.println("Kompilacja PluginGreeter.java - kod wyjscia: " + compileResult + "\n");

            byte[] classBytes = Files.readAllBytes(classesDir.resolve(className + ".class"));

            /*
             * ============================================================
             * 🔍 DEMO 1: LADOWANIE KLASY PRZEZ WLASNY LOADER + REFLEKSJA
             * ============================================================
             */
            System.out.println("--- DEMO 1: uzycie wlasnego loadera do zaladowania i uruchomienia klasy ---");
            InMemoryClassLoader loader1 = new InMemoryClassLoader(className, classBytes,
                    _Lesson04_CustomClassLoaders.class.getClassLoader());
            Class<?> pluginClass1 = loader1.loadClass(className);
            System.out.println("Klasa zaladowana przez: " + pluginClass1.getClassLoader());

            Object pluginInstance1 = pluginClass1.getDeclaredConstructor().newInstance();
            Method greetMethod = pluginClass1.getMethod("greet");
            String greeting = (String) greetMethod.invoke(pluginInstance1);
            System.out.println("Wynik wywolania metody przez refleksje: " + greeting + "\n");

            /*
             * ============================================================
             * 🔍 DEMO 2: KLASYCZNA "ZAGADKA" CLASSLOADEROW
             * ============================================================
             * Tworzymy DWIE NIEZALEZNE instancje TEGO SAMEGO custom
             * loadera, obie ladujace klase o TEJ SAMEJ nazwie i z TYCH
             * SAMYCH bajtow. Mimo identycznych bajtow, JVM traktuje
             * "tozsamosc" klasy jako pare (pelna nazwa, class loader) -
             * skoro loadery sa RÓZNYMI obiektami, powstaja DWA RÓZNE
             * obiekty java.lang.Class, mimo identycznej nazwy i kodu!
             *
             * Praktyczna konsekwencja: instancji utworzonej przez klase z
             * loadera #1 NIE MOZNA rzutowac na typ zaladowany przez
             * loader #2 - JVM zglosi ClassCastException, mimo ze "z nazwy"
             * to ta sama klasa.
             */
            System.out.println("--- DEMO 2: dwa loadery, ta sama klasa -> DWA rozne obiekty Class ---");
            InMemoryClassLoader loader2 = new InMemoryClassLoader(className, classBytes,
                    _Lesson04_CustomClassLoaders.class.getClassLoader());
            Class<?> pluginClass2 = loader2.loadClass(className);

            System.out.println("pluginClass1 == pluginClass2 ? " + (pluginClass1 == pluginClass2)
                    + "  (oczekiwane: false)");
            System.out.println("pluginClass1.equals(pluginClass2) ? " + pluginClass1.equals(pluginClass2)
                    + "  (oczekiwane: false)");

            Object pluginInstance2 = pluginClass2.getDeclaredConstructor().newInstance();
            System.out.println("Klasa instancji 1: " + pluginInstance1.getClass() + " (loader: " + pluginInstance1.getClass().getClassLoader() + ")");
            System.out.println("Klasa instancji 2: " + pluginInstance2.getClass() + " (loader: " + pluginInstance2.getClass().getClassLoader() + ")");

            try {
                // Rzutowanie obiektu z loader2 na typ zaladowany przez
                // loader1 (przez castowanie na Object dzialalby OK, ale
                // proba wywolania metody typu-specyficznego przez zwykle
                // rzutowanie na "Class<?> pluginClass1" jako typ nie jest
                // mozliwa w Javie statycznie - wiec symulujemy ten sam
                // efekt inaczej: sprawdzamy isInstance().
                boolean isInstanceOfOtherLoaderClass = pluginClass1.isInstance(pluginInstance2);
                System.out.println("pluginClass1.isInstance(pluginInstance2) ? " + isInstanceOfOtherLoaderClass
                        + "  (oczekiwane: false - to 'inna' klasa z punktu widzenia JVM)");
            } catch (Exception e) {
                System.out.println("Wyjatek przy probie porownania typow: " + e);
            }

            System.out.println("\nWNIOSEK: tozsamosc klasy w JVM to (pelna nazwa + class loader), NIE sama nazwa!");

        } finally {
            deleteRecursively(workDir);
        }

        /*
         * ============================================================
         * 🔹 ZASTOSOWANIA PRAKTYCZNE (PODSUMOWANIE)
         * ============================================================
         * - Systemy pluginow (np. IDE, serwery aplikacji) - kazdy plugin
         *   we WLASNYM loaderze, mozna go tez ZWOLNIC (przy braku innych
         *   referencji, GC odzyska caly graf klas zaladowanych przez ten
         *   loader - to jedyny sposob na "wyladowanie" klasy z JVM).
         * - Hot-reload (np. Spring DevTools, serwery aplikacji) - nowa
         *   wersja klasy = NOWY loader, stary jest porzucany.
         * - Izolacja wersji bibliotek miedzy modulami/aplikacjami w tym
         *   samym procesie JVM (np. kontenery servletow, patrz
         *   `_07_servlets`, gdzie kazda aplikacja webowa ma wlasny
         *   WebappClassLoader).
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wlasny ClassLoader: rozszerz ClassLoader, przeslon findClass(...),
         *   wewnatrz zdobadz bajty .class i przekaz je do defineClass(...).
         * - findClass(...) jest wywolywane DOPIERO PO nieudanej delegacji
         *   parent-first (Lekcja 3) - Twoj loader dostaje szanse jako
         *   OSTATNI w kolejce.
         * - Tozsamosc klasy w JVM = (pelna nazwa klasy, class loader) - DWIE
         *   instancje tego samego loadera dajace klase o tej samej nazwie
         *   tworza DWA RÓZNE obiekty Class - obiekty jednego "typu" nie sa
         *   compatible z drugim (ClassCastException/isInstance() == false).
         * - Praktyczne zastosowania: pluginy, hot-reload, izolacja wersji
         *   bibliotek (dokladnie to robi kazdy kontener servletow).
         * - Nastepna lekcja (5): jak ten sam mechanizm classloadingu
         *   zachowuje sie inaczej na "plaskim" classpath vs na module
         *   path JPMS.
         */
    }

    /**
     * Prosty wlasny ClassLoader ladujacy JEDNA, z gory znana klase z
     * tablicy bajtow przekazanej w konstruktorze. W realnym systemie
     * pluginow findClass(...) czytalby bajty np. z pliku JAR na dysku -
     * tutaj upraszczamy do tablicy w pamieci, zeby demo bylo
     * samowystarczalne (bez zaleznosci od plikow zewnetrznych).
     */
    static class InMemoryClassLoader extends ClassLoader {

        private final String expectedClassName;
        private final byte[] classBytes;

        InMemoryClassLoader(String expectedClassName, byte[] classBytes, ClassLoader parent) {
            super(parent);
            this.expectedClassName = expectedClassName;
            this.classBytes = classBytes;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (!name.equals(expectedClassName)) {
                // Zgodnie z kontraktem ClassLoader - jesli nie potrafimy
                // znalezc klasy, zglaszamy ClassNotFoundException.
                throw new ClassNotFoundException(name);
            }
            // defineClass(...) to serce calego mechanizmu - zamienia
            // surowe bajty .class na prawdziwy obiekt java.lang.Class,
            // przechodzac przez Linking (w tym Verification z Lekcji 3).
            return defineClass(name, classBytes, 0, classBytes.length);
        }
    }

    private static void explainWhyCustomClassLoaders() {
        System.out.println("[Po co wlasny ClassLoader?]");
        System.out.println("Pluginy, hot-reload, izolacja wersji bibliotek, deszyfrowanie bajtkodu w locie.\n");
    }

    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    // Ignorujemy pojedyncze bledy sprzatania katalogu tymczasowego.
                }
            });
        }
    }
}
