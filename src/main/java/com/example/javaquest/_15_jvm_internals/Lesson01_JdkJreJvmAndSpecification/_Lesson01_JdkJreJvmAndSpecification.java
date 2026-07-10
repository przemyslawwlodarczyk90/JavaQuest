package com.example.javaquest._15_jvm_internals.Lesson01_JdkJreJvmAndSpecification;

public class _Lesson01_JdkJreJvmAndSpecification {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: JDK, JRE, JVM I SPECYFIKACJA ===\n");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAL: _15_jvm_internals - JVM, PAMIEC I WYDAJNOSC
         * ============================================================
         * Do tej pory poznales Jave "od strony API": skladnie, OOP,
         * kolekcje, wielowatkowosc, siec, SQL/JDBC/Hibernate, biblioteki,
         * generyki, refleksje, moduly... Ten rozdzial schodzi PONIZEJ API -
         * do samej maszyny wirtualnej: jak dziala classloading, gdzie
         * fizycznie "zyja" obiekty (heap/stack/metaspace), jak dziala
         * Garbage Collector "pod maska", jak JIT kompiluje bajtkod do
         * kodu maszynowego, oraz jak diagnozowac prawdziwe problemy
         * produkcyjne (heap dump, thread dump, Java Flight Recorder).
         *
         * UWAGA: `_01_fundamentals` mial juz Lesson00 (JDK/JRE/JVM - bardzo
         * krotki wstep), Lesson10 (heap/stack koncepcyjnie, "gdzie zyja
         * zmienne") i Lesson14 (GC koncepcyjnie, Weak/Soft/PhantomReference).
         * Ten rozdzial NIE powtarza tamtych lekcji - idzie duzo glebiej:
         * prawdziwe mechanizmy dzialania JVM, prawdziwe API do introspekcji
         * (ManagementFactory, Java Flight Recorder), prawdziwe generowanie
         * plikow diagnostycznych.
         *
         * Plan calego rozdzialu (20 lekcji, w duzych klastrach tematycznych):
         *  1-5   Classloading i bajtkod - ta lekcja: JDK/JRE/JVM i specyfikacja;
         *        potem: kompilacja i bajtkod (javap), mechanika ladowania klas,
         *        wlasne class loadery, classpath vs module path.
         *  6-7   Obszary pamieci JVM - heap vs stack vs metaspace "od srodka",
         *        jak obiekty naprawde ukladaja sie w pamieci.
         *  8-12  Garbage Collector - algorytmy (mark-sweep-compact, generacyjny
         *        GC), konkretne kolektory (Serial/Parallel/G1/ZGC/Shenandoah),
         *        strojenie i pomiar pauz.
         *  13-14 JIT (Just-In-Time) - interpreter vs C1/C2, inlining, escape
         *        analysis, deoptymalizacja.
         *  15    Wycieki pamieci - jak je rozpoznac i znalezc.
         *  16-20 Diagnostyka i tuning produkcyjny - JFR, JMX, heap/thread dump,
         *        flagi JVM, projekt podsumowujacy (capstone).
         *
         * Ta lekcja (1) to fundament pod wszystko powyzsze: co to znaczy, ze
         * "Java to specyfikacja", i czym HotSpot rozni sie od innych JVM-ow.
         */
        System.out.println("Rozdzial _15_jvm_internals: classloading -> pamiec -> GC -> JIT -> diagnostyka.\n");

        /*
         * ============================================================
         * 🔹 SPECYFIKACJA vs IMPLEMENTACJA - KLUCZOWE ROZROZNIENIE
         * ============================================================
         * "Java" to w istocie DWIE oddzielne specyfikacje, publikowane przez
         * Oracle/JCP jako dokumenty:
         *
         *  - JLS (Java Language Specification) - opisuje SAM JEZYK: skladnie,
         *    semantyke wyrazen, reguly typowania, model pamieci Javy (Java
         *    Memory Model - JMM, kluczowy tez dla wielowatkowosci).
         *  - JVMS (Java Virtual Machine Specification) - opisuje MASZYNE
         *    WIRTUALNA: format pliku .class, zestaw instrukcji bajtkodu,
         *    zachowanie maszyny stosowej, wymagania wobec weryfikatora klas.
         *
         * KLUCZOWA IDEA: obie sa dokumentami TEKSTOWYMI opisujacymi
         * WYMAGANE zachowanie - nie zawieraja ani linijki kodu. Kazdy, kto
         * chce, moze napisac WLASNA implementacje zgodna z ta specyfikacja -
         * i to jest dokladnie to, co robia rozne firmy/projekty.
         *
         * Dzieki temu ten sam plik .class (skompilowany raz, gdziekolwiek)
         * moze dzialac identycznie na roznych implementacjach JVM - to jest
         * fundament hasla "write once, run anywhere".
         */
        explainSpecificationVsImplementation();

        /*
         * ============================================================
         * 🔹 IMPLEMENTACJE JVM - HOTSPOT, GRAALVM, OPENJ9...
         * ============================================================
         * Skoro JVM to "tylko" specyfikacja, istnieje kilka konkurencyjnych
         * (lub uzupelniajacych sie) implementacji:
         *
         *  - HotSpot - domyslna JVM dostarczana z OpenJDK/Oracle JDK. To ta,
         *    ktorej uzywasz uruchamiajac ten kurs. Nazwa "HotSpot" pochodzi
         *    od jej kluczowej cechy: identyfikuje "goracy" (czesto wykonywany)
         *    kod i kompiluje go JIT-em do natywnego kodu maszynowego (patrz
         *    lekcje 13-14).
         *  - GraalVM - alternatywna implementacja (tez oparta o rdzen HotSpot,
         *    ale z wlasnym, nowoczesnym kompilatorem JIT napisanym w Javie -
         *    "Graal"). Dodatkowo umozliwia kompilacje AOT (Ahead-Of-Time) do
         *    natywnego binarium (GraalVM Native Image) - blyskawiczny start,
         *    minimalne zuzycie pamieci, kosztem dluzszej kompilacji "z gory".
         *  - Eclipse OpenJ9 (dawniej J9 od IBM) - implementacja zoptymalizowana
         *    pod szybki start i niskie zuzycie pamieci, popularna w
         *    kontenerach/chmurze.
         *
         * WAZNE: wszystkie trzy implementuja TA SAMA specyfikacje JVMS - ten
         * sam plik .class uruchomi sie na kazdej z nich (choc wydajnosciowo
         * moga zachowywac sie inaczej). W tym kursie skupiamy sie na HotSpot,
         * bo to najbardziej rozpowszechniona implementacja produkcyjna.
         *
         * Krotkie odeslanie: `_01_fundamentals/Lesson00_JavaPlatformBasics`
         * tlumaczyl juz roznice JDK/JRE/JVM na poziomie "co jest do czego
         * potrzebne" - tutaj nie powtarzamy tamtej lekcji, tylko dokladamy
         * warstwe "specyfikacja vs konkretna implementacja".
         */
        explainJvmImplementations();

        /*
         * ============================================================
         * 🔹 ARCHITEKTURA JVM NA WYSOKIM POZIOMIE (WSTEP)
         * ============================================================
         * Kazda implementacja JVM (zgodna z JVMS) musi dostarczyc te same
         * trzy glowne podsystemy - to jest mapa calego rozdzialu:
         *
         *  1) CLASS LOADER SUBSYSTEM - laduje pliki .class do pamieci,
         *     weryfikuje bajtkod, przygotowuje statyczne pola, wykonuje
         *     inicjalizacje. Szczegoly: lekcje 3-4 (mechanika ladowania,
         *     wlasne class loadery).
         *  2) RUNTIME DATA AREAS (obszary danych w czasie dzialania) -
         *     pamiec podzielona na: Heap (obiekty), Stack (ramki wywolan
         *     metod per-watek), Metaspace (metadane klas), PC Register,
         *     Native Method Stacks. Szczegoly: lekcje 6-7.
         *  3) EXECUTION ENGINE (silnik wykonawczy) - sklada sie z:
         *     interpretera bajtkodu, kompilatora JIT (przyspiesza "goracy"
         *     kod), oraz Garbage Collectora (odzyskuje pamiec nieuzywanych
         *     obiektow). Szczegoly: lekcje 8-14.
         *
         * Dodatkowo JVM ma NATIVE METHOD INTERFACE (JNI) - most pozwalajacy
         * wywolywac kod natywny (np. C/C++) z Javy i odwrotnie - w tym
         * kursie tylko wspominamy, to temat na osobny, bardziej
         * zaawansowany kurs.
         *
         * To WSTEP - kazdy z tych elementow rozwiniemy szczegolowo w
         * kolejnych lekcjach tego rozdzialu.
         */
        explainJvmArchitectureOverview();

        /*
         * ============================================================
         * 🔍 DEMO: PRAWDZIWE WLASCIWOSCI SYSTEMOWE JVM
         * ============================================================
         * Ponizej odczytujemy z URUCHOMIONEJ WLASNIE JVM realne informacje
         * o tym, JAKA to konkretnie implementacja i JAKA wersja
         * specyfikacji jest przez nia realizowana.
         */
        printJvmIdentity();

        System.out.println("\n=== KONIEC LEKCJI 1 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - JLS opisuje JEZYK Java, JVMS opisuje SAMA MASZYNE WIRTUALNA -
         *   oba to dokumenty specyfikujace WYMAGANE zachowanie, nie kod.
         * - HotSpot, GraalVM, Eclipse OpenJ9 to RZNE implementacje TEJ SAMEJ
         *   specyfikacji JVMS - ten sam .class dziala na kazdej z nich.
         * - Architektura JVM (kazda implementacja): Class Loader Subsystem
         *   -> Runtime Data Areas (Heap/Stack/Metaspace/...) -> Execution
         *   Engine (interpreter + JIT + GC), plus JNI jako most do kodu
         *   natywnego.
         * - System.getProperty("java.vm.name"/"java.vm.version"/
         *   "java.specification.version"/"java.vendor") oraz
         *   Runtime.version() pozwalaja odczytac te informacje na zywo,
         *   z wnetrza dzialajacego programu.
         * - Kolejna lekcja (2): co dokladnie robi javac z Twoim kodem
         *   zrodlowym, i jak wyglada wynikowy bajtkod "od srodka" (javap).
         */
    }

    private static void explainSpecificationVsImplementation() {
        System.out.println("[Specyfikacja vs implementacja]");
        System.out.println("JLS (Java Language Specification) - reguly JEZYKA (skladnia, typy, JMM).");
        System.out.println("JVMS (JVM Specification) - reguly MASZYNY WIRTUALNEJ (format .class, bajtkod).");
        System.out.println("Obie sa dokumentami - kazdy moze napisac WLASNA, zgodna implementacje.\n");
    }

    private static void explainJvmImplementations() {
        System.out.println("[Implementacje JVM]");
        System.out.println("- HotSpot   -> domyslna w OpenJDK/Oracle JDK, JIT identyfikuje 'gorace' metody.");
        System.out.println("- GraalVM   -> alternatywny JIT (Graal) + mozliwosc AOT (Native Image).");
        System.out.println("- OpenJ9    -> nacisk na szybki start i niskie zuzycie pamieci (kontenery).");
        System.out.println("Wszystkie implementuja TA SAMA specyfikacje JVMS.\n");
    }

    private static void explainJvmArchitectureOverview() {
        System.out.println("[Architektura JVM - wysoki poziom]");
        System.out.println("1) Class Loader Subsystem   -> ladowanie/weryfikacja/inicjalizacja klas (lekcje 3-4).");
        System.out.println("2) Runtime Data Areas        -> Heap/Stack/Metaspace/PC Register (lekcje 6-7).");
        System.out.println("3) Execution Engine          -> interpreter + JIT + Garbage Collector (lekcje 8-14).");
        System.out.println("+ Native Method Interface (JNI) - most do kodu natywnego (tylko wzmianka).\n");
    }

    private static void printJvmIdentity() {
        System.out.println("[Tozsamosc TEJ konkretnej JVM - dane na zywo]");
        System.out.println("java.vm.name                = " + System.getProperty("java.vm.name"));
        System.out.println("java.vm.version              = " + System.getProperty("java.vm.version"));
        System.out.println("java.vm.vendor                = " + System.getProperty("java.vm.vendor"));
        System.out.println("java.specification.name       = " + System.getProperty("java.specification.name"));
        System.out.println("java.specification.version    = " + System.getProperty("java.specification.version"));
        System.out.println("java.vendor                   = " + System.getProperty("java.vendor"));
        System.out.println("java.version                  = " + System.getProperty("java.version"));

        Runtime.Version version = Runtime.version();
        System.out.println("Runtime.version()              = " + version);
        System.out.println("  - feature (glowny numer wersji): " + version.feature());
        System.out.println("  - interim/update/patch:          " + version.interim() + "/" + version.update() + "/" + version.patch());
    }
}
