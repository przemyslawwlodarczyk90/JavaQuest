package com.example.javaquest._14_advancedjava.Lesson28_ModulesAdvanced;

public class _Exercises_Lesson28_ModulesAdvanced {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainOpensVsExports {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania) roznice miedzy "exports"
         * a "opens" - co kontroluje kazda z tych dyrektyw i dlaczego modul moze miec
         * jedna bez drugiej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteOpenModuleSyntax {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: w komentarzu napisz tresc module-info.java dla modulu
         * "com.example.entities" jako "open module" (otwierajacego WSZYSTKIE swoje
         * pakiety do refleksji naraz), eksportujacego pakiet "com.example.entities.model".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainQualifiedExports {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij w komentarzu (min. 2 zdania), czym rozni sie
         * "exports pkg;" od "exports pkg to modA, modB;" i podaj jeden przyklad sytuacji,
         * kiedy eksport kwalifikowany ma sens.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteUsesProvidesSyntax {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: napisz w komentarzu 2 linie module-info.java: "uses" dla
         * interfejsu "com.example.spi.Notifier" (modul konsumenta) oraz "provides ...
         * with ..." dla implementacji "com.example.impl.EmailNotifier" (modul dostawcy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyFrameworksNeedOpens {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij w komentarzu (min. 3 zdania), dlaczego frameworki takie
         * jak Hibernate/Jackson/Spring potrzebuja "opens" na pakietach z Twoimi
         * encjami/DTO, jesli aplikacja jest zbudowana jako modul JPMS (nawiazanie do
         * Lekcji 16 o refleksji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TriggerInaccessibleObjectExceptionOnClasspath {
        /*
         * 🧪 Zadanie 6:
         * BEZ modulow (na zwyklym classpath, tak jak reszta tego kursu) sprobuj wywolac
         * Field.setAccessible(true) na prywatnym polu KLASY Z JDK, ktora tego zabrania
         * (np. proba dostepu do wewnetrznych pol w java.lang - w module nienazwanym
         * moze to jednak zadzialac inaczej niz w prawdziwym module). Wypisz, co faktycznie
         * sie stalo i skomentuj roznice wobec demo z lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListJdksInternalPackagesExample {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: podaj w komentarzu jeden przyklad pakietu wewnetrznego samego
         * JDK (np. "jdk.internal.misc"), ktory historycznie byl nadużywany przez
         * biblioteki poprzez refleksje, i wyjasnij, jak JPMS/opens temu przeciwdziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GenerateTwoModuleOpensProjectFiles {
        /*
         * 🧪 Zadanie 8:
         * Wygeneruj (Files.writeString) na dysku strukture 2 modulow z lekcji
         * (reflect.target BEZ opens + reflect.consumer z kodem refleksyjnym) - NIE
         * kompiluj jeszcze, tylko wypisz sciezki wszystkich utworzonych plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompileAndRunWithoutOpens {
        /*
         * 🧪 Zadanie 9:
         * Skompiluj i uruchom projekt z Zadania 8 PRAWDZIWA komenda javac/java
         * (ProcessBuilder) i przechwyc/wypisz PRAWDZIWY InaccessibleObjectException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AddOpensAndRerun {
        /*
         * 🧪 Zadanie 10:
         * Dopisz "opens target;" do module-info.java z Zadania 8/9, przekompiluj do
         * NOWEGO katalogu wyjsciowego i uruchom ponownie - wypisz, ze tym razem odczyt
         * przez refleksje sie powiodl. Posprzataj katalog tymczasowy na koniec.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_QualifiedExportsToTwoModules {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj modul "qual.api" z "exports internal to qual.friendA, qual.friendB;"
         * oraz DWA moduly-przyjaciele. Skompiluj i uruchom OBA (osobno) - wypisz, ze OBA
         * maja dostep do pakietu "internal".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ThirdModuleNotInListFails {
        /*
         * 🧪 Zadanie 12:
         * Dodaj TRZECI modul "qual.strangerC" (NIE wymieniony w liscie "to" z Zadania
         * 11), sprobuj go skompilowac z uzyciem pakietu "internal" i przechwyc/wypisz
         * PRAWDZIWY blad kompilacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_OpensToQualifiedReflection {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj modul z KWALIFIKOWANYM "opens pkg to trusted.module;" (zamiast
         * bezwarunkowego "opens pkg;"). Sprawdz i wypisz wynik proby refleksji z modulu
         * "trusted.module" (oczekiwany sukces) ORAZ z INNEGO modulu (oczekiwany
         * InaccessibleObjectException).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_EndToEndUsesProvidesThreeModules {
        /*
         * 🧪 Zadanie 14:
         * Odtworz pelne demo uses/provides z lekcji (spi.api/spi.provider/spi.consumer)
         * dla WLASNEGO interfejsu uslugi (np. "TaxCalculator" z metoda double
         * calculate(double amount)) - skompiluj, uruchom z --add-modules i wypisz
         * rzeczywisty wynik dzialania znalezionego dostawcy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleProvidersForSameService {
        /*
         * 🧪 Zadanie 15:
         * Rozszerz Zadanie 14 o DRUGI modul-dostawce z INNA implementacja tego samego
         * interfejsu ("provides" w OSOBNYM module). Uruchom konsumenta z --add-modules
         * wskazujacym OBA moduly dostawcow i wypisz wyniki OBU znalezionych dostawcow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ForgetAddModulesShowsZeroProviders {
        /*
         * 🧪 Zadanie 16:
         * Uruchom konsumenta z Zadania 14 BEZ opcji --add-modules (modul dostawcy
         * skompilowany, ale nie dolaczony do grafu modulow uruchomienia). Wypisz
         * rzeczywisty wynik - liczba znalezionych dostawcow powinna wynosic 0 - i
         * skomentuj w kodzie dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareServiceLoaderClasspathVsModulePath {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj DWIE rownowazne wersje tej samej uslugi: (a) na classpath z plikiem
         * META-INF/services (jak w Lekcji 26), (b) jako moduly z uses/provides (jak w
         * tej lekcji). Uruchom OBIE i wypisz, ze ServiceLoader.load(...) daje TAKI SAM
         * wynik w obu przypadkach, mimo calkowicie innego mechanizmu rejestracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_JdepsSimulationReport {
        /*
         * 🧪 Zadanie 18:
         * (Bez uruchamiania prawdziwego "jdeps".) Napisz metode, ktora na podstawie
         * prostej listy importow (String[] importedPackages) i mapy "pakiet -> modul"
         * generuje sugerowana liste "requires" dla module-info.java - wypisz wynik dla
         * przykladowego zestawu importow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MigrationStepsSimulation {
        /*
         * 🧪 Zadanie 19:
         * Zasymuluj 3 kroki migracji z lekcji: (1) projekt classpathowy (wypisz "brak
         * enkapsulacji"), (2) dodanie module-info.java tylko do WLASNEGO kodu + biblioteka
         * jako modul automatyczny (wypisz nazwe automatycznego modulu wyprowadzona z
         * nazwy pliku JAR, np. "commons-lang3-3.14.0.jar" -> "commons.lang3"), (3) pelna
         * modularnosc. Wypisz krotki opis kazdego kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CleanupMultipleTempDirsInFinally {
        /*
         * 🧪 Zadanie 20:
         * Powtorz DWA rozne demo z tej lekcji (np. opens i qualified exports) w JEDNYM
         * uruchomieniu, kazde we WLASNYM katalogu tymczasowym, oba posprzataj w osobnych
         * blokach finally. Wypisz potwierdzenie (Files.exists == false) dla obu po
         * zakonczeniu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullOpensExportsProvidesInOneModule {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj JEDEN modul deklarujacy jednoczesnie: "exports" (zwykly pakiet
         * publiczny), "exports ... to ..." (pakiet dla zaufanego modulu), "opens"
         * (pakiet z encjami do refleksji) oraz "provides" (implementacja uslugi). Wypisz
         * pelna tresc wygenerowanego module-info.java i zweryfikuj kompilacja calosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReflectionAccessMatrixAcrossScenarios {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj macierz 2x2 scenariuszy dla dostepu refleksyjnego: (exports=tak/nie) x
         * (opens=tak/nie) - dla kazdej z 4 kombinacji skompiluj i uruchom PRAWDZIWA
         * proba refleksji, zbierz wyniki (sukces/InaccessibleObjectException/blad
         * kompilacji) i wypisz je jako tabelke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ServiceLoaderWithMultipleQualifiedConsumers {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj scenariusz z JEDNYM modulem-dostawca uslugi i DWOMA modulami-konsumentami,
         * gdzie KAZDY z konsumentow deklaruje wlasne "uses" i uruchamiany jest OSOBNO
         * (osobne wywolania java --module-path --add-modules --module). Wypisz, ze OBAJ
         * konsumenci niezaleznie znajduja tego samego dostawce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ProviderNotImplementingInterfaceCompileError {
        /*
         * 🧪 Zadanie 24:
         * W deskryptorze modulu dostawcy napisz "provides Interfejs with Klasa;", gdzie
         * "Klasa" NIE implementuje "Interfejsu" (celowy blad). Skompiluj i przechwyc/wypisz
         * PRAWDZIWY blad kompilacji ("... does not implement ...") - dowod, ze JPMS
         * weryfikuje to JUZ przy kompilacji, w odroznieniu od pliku META-INF/services z
         * Lekcji 26 (ktory taki blad ujawnilby dopiero w runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DynamicModuleLayerViaModuleLayerApi {
        /*
         * 🧪 Zadanie 25:
         * (Zaawansowane API java.lang.ModuleLayer/Configuration - poza glownym zakresem
         * lekcji, eksploracyjne zadanie.) Zbadaj (dokumentacja/eksperyment) i napisz
         * PROSTY przyklad programowego zbudowania ModuleLayer dla skompilowanych modulow
         * z tej lekcji, jako ALTERNATYWE dla uruchamiania ich jako osobny proces
         * ProcessBuilder. Wypisz nazwy modulow widoczne w utworzonej warstwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_OpensToFrameworkSimulation {
        /*
         * 🧪 Zadanie 26:
         * Zasymuluj sytuacje "biblioteka DI skanujaca encje": modul "app.entities" z
         * "opens com.example.entities to fake.framework;", modul "fake.framework"
         * odczytujacy WSZYSTKIE pola instancji przekazanej klasy przez refleksje
         * (Class.getDeclaredFields() + setAccessible + get) i wypisujacy je jako
         * "wstrzykniete" pary klucz-wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MigrationDryRunReport {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj mini-projekt classpathowy (3-4 klasy w roznych "logicznych pakietach")
         * i napisz metode analyzeForModularization(...), ktora na podstawie prostej
         * analizy importow sugeruje PODZIAL na moduly (nazwa modulu + lista klas +
         * sugerowane exports) - wypisz wygenerowany "raport migracyjny" (bez faktycznego
         * tworzenia module-info.java).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PerformanceComparisonReflectionOpenVsClosed {
        /*
         * 🧪 Zadanie 28:
         * Zmierz System.nanoTime() dla WIELOKROTNEGO (np. 10000x) odczytu tego samego
         * prywatnego pola przez refleksje w module Z "opens" - wypisz sredni czas
         * pojedynczego odczytu. (Nie da sie zmierzyc scenariusza BEZ opens - konczy sie
         * wyjatkiem za pierwszym razem - skomentuj to w kodzie jako obserwacje.)
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CapstoneClusterIntegration {
        /*
         * 🧪 Zadanie 29:
         * Polacz Lekcje 26-28 w jednym demo: interfejs uslugi zdefiniowany w module
         * "cluster.api" (exports), implementacja w module "cluster.provider" (provides,
         * z polem prywatnym odczytywanym refleksyjnie dzieki "opens" - np. wewnetrzny
         * licznik wywolan), konsument w module "cluster.consumer" (uses + requires
         * cluster.api). Skompiluj, uruchom i wypisz zarowno wynik wywolania uslugi, jak
         * i odczytany refleksyjnie prywatny licznik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ModulesAdvancedCapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Wykonaj PELNY zestaw demo z tej lekcji (opens sukces/blad, exports...to
         * sukces/blad, uses/provides sukces) w JEDNYM uruchomieniu, zliczaj kazdy
         * scenariusz (nazwa + wynik: SUKCES/BLAD-OCZEKIWANY) i na koniec wypisz raport
         * "=== RAPORT ZAAWANSOWANYCH MODULOW ===" z pelna lista scenariuszy, ich
         * wynikami oraz jednozdaniowym wnioskiem koncowym o tym, dlaczego ten kurs mimo
         * to zostaje na classpath. Posprzataj WSZYSTKIE katalogi tymczasowe w finally.
         */
        public static void main(String[] args) { }
    }
}
