package com.example.javaquest._14_advancedjava.Lesson26_ServiceLoaderAndSpi;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public class _Lesson26_ServiceLoaderAndSpi {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 26: ServiceLoader i SPI (Service Provider Interface) ===\n");

        /*
         * ============================================================
         * 📦 NOWY KLASTER: ODKRYWANIE USLUG I MODULARNOSC (Lekcje 26-28)
         * ============================================================
         * Lekcje 26-28 to najbardziej techniczny klaster tego rozdzialu:
         *  - Lekcja 26 (ta): SPI + java.util.ServiceLoader - jak napisac
         *    kod, ktory zna tylko INTERFEJS, a implementacje "znajduje"
         *    sam w runtime, bez ANI JEDNEJ linijki "new KonkretnaImpl()".
         *  - Lekcja 27: JPMS (Java Platform Module System) - podstawy:
         *    module-info.java, requires/exports, moduly nienazwane.
         *  - Lekcja 28: JPMS zaawansowane - opens, exports...to, oraz
         *    NATYWNA dla modulow wersja dzisiejszego ServiceLoadera
         *    (uses/provides) - zamkniecie tego klastra.
         *
         * Caly ten projekt kursu (javaQuest) kompiluje sie na CLASSPATH
         * (brak module-info.java) - wiec demo w tej lekcji dziala w
         * ramach jednego, zwyklego JVM, zas Lekcje 27-28 dodatkowo
         * KOMPILUJA i URUCHAMIAJA prawdziwe, osobne mini-projekty
         * modulowe jako podprocesy (javac/java), zeby nie naruszyc tego
         * zalozenia calego projektu.
         */

        /*
         * ============================================================
         * 📦 SPI - SERVICE PROVIDER INTERFACE - PO CO TO?
         * ============================================================
         * SPI to wzorzec projektowy (nie mechanizm jezyka!) polegajacy na
         * ROZDZIELENIU:
         *  - API (interfejsu uslugi) - definiuje CO uslugat robi,
         *  - IMPLEMENTACJI (dostawcy uslugi, "provider") - definiuje JAK
         *    to robi, w OSOBNYM module/JAR-ze, czesto napisanym przez
         *    zupelnie inny zespol/firme.
         *
         * Kod korzystajacy z uslugi (klient) zna WYLACZNIE interfejs -
         * nigdy nie pisze "new KonkretnaImplementacja()". Konkretna
         * implementacja jest ZNAJDOWANA w czasie dzialania programu
         * (runtime), nie zaszyta na sztywno w kodzie (compile time).
         *
         * Klasyczny przyklad z samej Javy: java.sql.Driver (JDBC).
         * Twoj kod robi DriverManager.getConnection(url) i NIGDY nie
         * pisze "new org.h2.Driver()" ani "new com.mysql.cj.jdbc.Driver()"
         * - odpowiedni sterownik jest znajdowany automatycznie na
         * podstawie tego, co jest na classpath. To wlasnie SPI w akcji -
         * poznales je juz praktycznie w rozdziale _09_jdbc, teraz poznamy
         * MECHANIZM, ktory stoi za tym "magicznym" znajdowaniem.
         *
         * Zalety SPI:
         *  - LUZNE POWIAZANIE (loose coupling) - API nie zalezy od zadnej
         *    konkretnej implementacji, implementacje nie zaleza od siebie
         *    nawzajem.
         *  - ROZSZERZALNOSC - nowy dostawca uslugi to nowy JAR na
         *    classpath, ZERO zmian w kodzie klienta ani API.
         *  - WYMIENNOSC - mozna podmienic implementacje (np. w testach na
         *    "fake") bez zadnej zmiany w kodzie korzystajacym z uslugi.
         */

        /*
         * ============================================================
         * 🔹 java.util.ServiceLoader<S> - WBUDOWANY MECHANIZM SPI
         * ============================================================
         * Java od dawna (java.util.ServiceLoader istnieje od Javy 6) ma
         * WBUDOWANY mechanizm SPI - nie trzeba pisac wlasnego systemu
         * "wtyczek" recznie. Zasada dzialania (na classpath):
         *
         *  1. Definiujesz interfejs (albo klase abstrakcyjna) - "usluge".
         *  2. Kazda implementacja tej uslugi trafia do OSOBNEGO pliku
         *     tekstowego:
         *         META-INF/services/pelna.nazwa.InterfejsuUslugi
         *     Kazda LINIA tego pliku to PELNA (z pakietem) nazwa jednej
         *     klasy implementujacej ten interfejs.
         *  3. W kodzie wywolujesz:
         *         ServiceLoader<InterfejsUslugi> loader =
         *             ServiceLoader.load(InterfejsUslugi.class);
         *     ...i iterujesz po loaderze - ServiceLoader sam znajduje
         *     WSZYSTKIE pliki META-INF/services/InterfejsUslugi na
         *     classpath (moze ich byc wiele - po jednym z kazdego JAR-a!),
         *     wczytuje wymienione tam klasy i tworzy ich instancje
         *     (wymagany PUBLICZNY konstruktor bezargumentowy, albo od
         *     Javy 9 - publiczna metoda staticzna "provider()").
         *
         * WAZNE: ServiceLoader NIE rzuca wyjatku, jesli nie znajdzie
         * zadnego dostawcy - po prostu iterator jest pusty. To celowe -
         * "brak wtyczki" to zwykle poprawny, obslugiwany scenariusz.
         */

        System.out.println("--- DEMO: budujemy mini-SPI 'na zywca' ---\n");

        // Cala nasza mini-aplikacja SPI (interfejs GreetingService +
        // 2 implementacje) zyje w OSOBNYM, tymczasowym katalogu -
        // kompilujemy ja programowo (jak w Lekcji 2 rozdzialu 11) i
        // dopiero POTEM wczytujemy przez wlasny URLClassLoader. Dzieki
        // temu demo nie dotyka w ogole klasy .java tej lekcji ani
        // reszty projektu - jest w pelni samowystarczalne.
        Path workDir = Files.createTempDirectory("lesson26-spi");
        try {
            Path srcDir = workDir.resolve("src");
            Path classesDir = workDir.resolve("classes");
            Files.createDirectories(srcDir);
            Files.createDirectories(classesDir);

            // KROK 1: interfejs uslugi - to jest "API", ktore zna klient.
            Path serviceInterfaceSrc = srcDir.resolve("GreetingService.java");
            Files.writeString(serviceInterfaceSrc, """
                    package spi;

                    public interface GreetingService {
                        String greet(String name);
                    }
                    """);

            // KROK 2: DWIE niezalezne implementacje - w prawdziwym
            // projekcie kazda mogla by siedziec w OSOBNYM JAR-ze,
            // dostarczonym przez inny zespol.
            Path polishImplSrc = srcDir.resolve("PolishGreetingServiceImpl.java");
            Files.writeString(polishImplSrc, """
                    package spi.impl;

                    import spi.GreetingService;

                    public class PolishGreetingServiceImpl implements GreetingService {
                        @Override
                        public String greet(String name) {
                            return "Czesc, " + name + "! (dostawca: polski)";
                        }
                    }
                    """);

            Path englishImplSrc = srcDir.resolve("EnglishGreetingServiceImpl.java");
            Files.writeString(englishImplSrc, """
                    package spi.impl;

                    import spi.GreetingService;

                    public class EnglishGreetingServiceImpl implements GreetingService {
                        @Override
                        public String greet(String name) {
                            return "Hello, " + name + "! (provider: english)";
                        }
                    }
                    """);

            // KROK 3: kompilujemy WSZYSTKO programowo (dokladnie ten sam
            // JavaCompiler co w rozdziale 11) do wspolnego katalogu klas.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("Brak systemowego kompilatora Javy - uzyj JDK, nie samego JRE.");
            }
            int compileResult = compiler.run(null, System.out, System.err,
                    "-d", classesDir.toString(),
                    serviceInterfaceSrc.toString(),
                    polishImplSrc.toString(),
                    englishImplSrc.toString());
            System.out.println("Wynik kompilacji interfejsu + 2 implementacji (0 = sukces): " + compileResult);

            // KROK 4: TO JEST SERCE MECHANIZMU SPI - plik
            // META-INF/services/spi.GreetingService, gdzie kazda linia
            // to pelna nazwa jednej klasy implementujacej GreetingService.
            // ServiceLoader szuka DOKLADNIE takich plikow.
            Path metaInfServices = classesDir.resolve("META-INF").resolve("services");
            Files.createDirectories(metaInfServices);
            Path registrationFile = metaInfServices.resolve("spi.GreetingService");
            Files.writeString(registrationFile, """
                    spi.impl.PolishGreetingServiceImpl
                    spi.impl.EnglishGreetingServiceImpl
                    """);
            System.out.println("Utworzono plik rejestracji dostawcow: " + registrationFile);
            System.out.println("Zawartosc:\n" + Files.readString(registrationFile));

            /*
             * ============================================================
             * 🔍 URUCHAMIAMY ServiceLoader NA WLASNYM URLClassLoader
             * ============================================================
             * Interfejs GreetingService NIE istnieje w classpath TEJ
             * lekcji (zyje wylacznie w tymczasowym katalogu) - dlatego
             * wczytujemy go dynamicznie przez URLClassLoader i
             * Class.forName, a metode greet(...) wywolujemy refleksyjnie
             * (dokladnie tak, jak Class.forName+invoke w Lekcji 2
             * rozdzialu 11). W REALNYM projekcie, gdzie interfejs jest
             * zwykla, znana w compile-time klasa, wywolanie wyglada
             * DUZO prosciej:
             *
             *     ServiceLoader<GreetingService> loader =
             *         ServiceLoader.load(GreetingService.class);
             *     for (GreetingService service : loader) {
             *         System.out.println(service.greet("Kursancie"));
             *     }
             *
             * ...bez zadnej refleksji - to normalny, typowany kod.
             */

            URL[] classpathUrls = { classesDir.toUri().toURL() };
            // Parent = platformowy class loader (bez klas TEGO projektu)
            // - chcemy calkowicie izolowanego swiata dla tego mini-SPI.
            try (URLClassLoader spiClassLoader = new URLClassLoader(classpathUrls, ClassLoader.getSystemClassLoader().getParent())) {

                Class<?> serviceInterfaceClass = Class.forName("spi.GreetingService", true, spiClassLoader);

                @SuppressWarnings({ "unchecked", "rawtypes" })
                ServiceLoader<?> serviceLoader = ServiceLoader.load((Class) serviceInterfaceClass, spiClassLoader);

                Method greetMethod = serviceInterfaceClass.getMethod("greet", String.class);

                System.out.println("\n--- Znalezieni dostawcy GreetingService (przez ServiceLoader) ---");
                int providerCount = 0;
                for (Object provider : serviceLoader) {
                    String greeting = (String) greetMethod.invoke(provider, "Kursancie");
                    System.out.println(provider.getClass().getName() + " -> " + greeting);
                    providerCount++;
                }
                System.out.println("Liczba znalezionych dostawcow: " + providerCount);

                /*
                 * ============================================================
                 * 🔍 LENIWE LADOWANIE I stream()
                 * ============================================================
                 * ServiceLoader jest LENIWY (lazy) - iterator NIE tworzy
                 * wszystkich instancji od razu przy load(), tylko dopiero
                 * przy kazdym kolejnym next(). Od Javy 9 dostepna jest
                 * tez metoda stream(), zwracajaca Stream<Provider<S>> -
                 * pozwala np. sprawdzic KLASE dostawcy (Provider.type())
                 * BEZ tworzenia jego instancji, co bywa przydatne przy
                 * filtrowaniu wielu dostawcow po jakims kryterium
                 * (np. adnotacji) przed faktycznym uzyciem.
                 */
                System.out.println("\n--- ServiceLoader.reload() + stream() (leniwe metadane) ---");
                serviceLoader.reload(); // czyscimy wewnetrzny cache instancji
                serviceLoader.stream().forEach(provider ->
                        System.out.println("Dostepny typ dostawcy (bez tworzenia instancji): " + provider.type().getName()));
            }

            /*
             * ============================================================
             * 🔍 BRAK DOSTAWCOW - TO NIE JEST BLAD
             * ============================================================
             * Jesli plik META-INF/services/... nie istnieje (albo jest
             * pusty), ServiceLoader.load(...) NIE rzuca wyjatku -
             * iterator jest po prostu pusty. To bardzo swiadoma decyzja
             * projektowa API: "brak wtyczki" to normalny stan (np.
             * opcjonalny modul logowania, ktorego ktos nie dodal na
             * classpath), a nie blad krytyczny.
             */
            Path emptyClassesDir = workDir.resolve("empty-classes");
            Files.createDirectories(emptyClassesDir);
            // Kopiujemy sam skompilowany interfejs (bez META-INF/services i bez implementacji).
            Files.copy(classesDir.resolve("spi").resolve("GreetingService.class"),
                    Files.createDirectories(emptyClassesDir.resolve("spi")).resolve("GreetingService.class"));

            try (URLClassLoader emptyLoader = new URLClassLoader(
                    new URL[] { emptyClassesDir.toUri().toURL() }, ClassLoader.getSystemClassLoader().getParent())) {
                Class<?> emptyServiceInterface = Class.forName("spi.GreetingService", true, emptyLoader);
                @SuppressWarnings({ "unchecked", "rawtypes" })
                ServiceLoader<?> emptyServiceLoader = ServiceLoader.load((Class) emptyServiceInterface, emptyLoader);
                long found = emptyServiceLoader.stream().count();
                System.out.println("\n--- DEMO: brak pliku META-INF/services ---");
                System.out.println("Liczba znalezionych dostawcow (oczekiwane 0): " + found);
                System.out.println("Zaden wyjatek nie zostal rzucony - pusty ServiceLoader to poprawny stan.");
            }

        } finally {
            // KROK 5: SPRZATANIE - usuwamy caly katalog tymczasowy, od
            // najglebszych plikow do korzenia (tak jak w _04_io/Lesson10).
            try (Stream<Path> walk = Files.walk(workDir)) {
                walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (Exception e) {
                        // Ignorujemy pojedyncze bledy sprzatania - to tylko katalog tymczasowy.
                    }
                });
            }
        }

        /*
         * ============================================================
         * 🔹 SPI W SAMEJ JAVIE STANDARDOWEJ - GDZIE JUZ TEGO UZYWASZ
         * ============================================================
         * ServiceLoader/SPI nie jest cieakawostka - to fundament wielu
         * mechanizmow, ktorych juz uzywales w tym kursie:
         *  - java.sql.Driver - sterowniki JDBC (rozdzial _09_jdbc) -
         *    kazdy sterownik (np. H2) ma plik
         *    META-INF/services/java.sql.Driver.
         *  - java.nio.file.spi.FileSystemProvider - dostawcy systemow
         *    plikow (np. obsluga plikow ZIP jako "systemu plikow" -
         *    rozdzial _04_io, lekcja o ZIP).
         *  - javax.xml.parsers.DocumentBuilderFactory /
         *    javax.xml.transform.TransformerFactory - fabryki parserow
         *    XML - domyslna implementacja JDK moze zostac PODMIENIONA
         *    przez wlasna, jesli tylko wystawimy odpowiedni plik SPI.
         *  - java.time.chrono.Chronology - alternatywne kalendarze
         *    (np. japonski, hidzry) doczepiane jako "wtyczki".
         *  - Wiele bibliotek spoza JDK (np. sterowniki logowania w
         *    SLF4J) rowniez korzysta z tego samego mechanizmu.
         *
         * Za KAZDYM razem dziala DOKLADNIE ten sam wzorzec: interfejs +
         * plik META-INF/services/... + ServiceLoader.load(...).
         */

        /*
         * ============================================================
         * 📌 SPI vs "TWARDE" ZALEZNOSCI - KIEDY UZYWAC?
         * ============================================================
         * SPI ma sens, gdy:
         *  - implementacja MOZE (ale nie musi) byc dostarczona przez
         *    kogos innego (plugin, sterownik, rozszerzenie),
         *  - chcesz, zeby dodanie nowej implementacji nie wymagalo
         *    zmiany w kodzie API ani klienta - tylko dolozenia JAR-a,
         *  - liczba implementacji jest nieznana z gory / moze rosnac.
         *
         * SPI jest ZBYTECZNE (i utrudnia zycie), gdy implementacja jest
         * dokladnie JEDNA i nigdy sie nie zmieni - wtedy zwykle
         * "new KonkretnaKlasa()" (albo Dependency Injection, np. Spring,
         * ktory rozwiazuje ten sam problem w bardziej rozbudowany
         * sposob) jest prostsze i czytelniejsze.
         */

        System.out.println("\n=== KONIEC LEKCJI 26 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - SPI (Service Provider Interface) to wzorzec rozdzielajacy
         *   API (interfejs uslugi) od implementacji (dostawcy), znajdywanej
         *   dynamicznie w runtime, a nie zaszytej na sztywno w kodzie.
         * - java.util.ServiceLoader<S> to wbudowany w Jave mechanizm SPI:
         *   plik META-INF/services/pelna.nazwa.Interfejsu z lista pelnych
         *   nazw klas implementujacych ten interfejs, jedna na linie.
         * - ServiceLoader.load(Interfejs.class) zwraca ServiceLoader,
         *   po ktorym mozna iterowac (leniwie tworzy instancje) albo
         *   uzyc stream() (Java 9+) do pracy z metadanymi bez tworzenia
         *   instancji.
         * - Brak zadnego dostawcy NIE jest bledem - iterator/stream jest
         *   po prostu pusty.
         * - Implementacje wymagaja publicznego konstruktora bezargumentowego
         *   (albo od Javy 9 - publicznej statycznej metody provider()).
         * - JDBC, NIO FileSystemProvider, fabryki XML i wiele innych
         *   mechanizmow standardowej Javy uzywaja DOKLADNIE tego wzorca.
         * - Lekcja 28 pokaze NATYWNA dla modulow (JPMS) wersje tego
         *   samego mechanizmu - dyrektywy uses/provides w module-info.java,
         *   ktore CALKOWICIE zastepuja plik META-INF/services.
         */
    }
}
