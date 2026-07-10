package com.example.javaquest._15_jvm_internals.Lesson06_HeapStackMetaspace;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

public class _Lesson06_HeapStackMetaspace {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: HEAP, STACK I METASPACE - PRAWDZIWA INTROSPEKCJA JVM ===");

        /*
         * ============================================================
         * 🔹 PRZYPOMNIENIE Z _01_fundamentals/Lesson10_HeapAndStack
         * ============================================================
         * - Tamta lekcja byla KONCEPCYJNA: obiekty zyja na stercie (heap),
         *   zmienne lokalne i ramki wywolan metod zyja na stosie (stack) -
         *   kazdy watek ma WLASNY stos.
         * - Tutaj idziemy krok dalej: zamiast tylko OPISYWAC te obszary,
         *   zapytamy SAMA JVM (przez java.lang.management), ile pamieci
         *   faktycznie ma zaalokowanej, ile zuzywa i jak nazywaja sie
         *   realne "pule pamieci" dzialajace pod spodem w TEJ WLASNIE JVM,
         *   na TYM WLASNYM sprzecie. Wyniki moga sie roznic miedzy
         *   maszynami/wersjami JDK - i to jest cala idea tej lekcji.
         */
        System.out.println("W tej lekcji pytamy JVM wprost - przez java.lang.management - o realny stan pamieci.");

        /*
         * ============================================================
         * 🔍 MemoryMXBean - GLOWNY PUNKT WEJSCIA DO PAMIECI JVM
         * ============================================================
         * - ManagementFactory.getMemoryMXBean() zwraca obiekt opisujacy
         *   pamiec HEAP (gdzie zyja obiekty) i NON-HEAP (m.in. Metaspace,
         *   pamiec na skompilowany kod JIT, itp.) jako calosc.
         * - getHeapMemoryUsage() / getNonHeapMemoryUsage() zwracaja
         *   MemoryUsage z 4 liczbami (w bajtach):
         *     * init      - ile JVM poprosila o zaalokowanie na starcie
         *     * used      - ile jest FAKTYCZNIE uzywane w tej chwili
         *     * committed - ile system operacyjny GWARANTUJE, ze JVM moze
         *                   uzyc bez dodatkowej alokacji (>= used)
         *     * max       - twardy limit (-1 jesli nieokreslony)
         */
        demonstrateHeapMemoryUsage();

        /*
         * ============================================================
         * 🔍 NON-HEAP MEMORY - METASPACE I INNE OBSZARY POZA STERTA
         * ============================================================
         * - Non-heap to m.in. Metaspace (metadane klas - zastapil
         *   PermGen od Javy 8), Compressed Class Space, oraz obszary na
         *   skompilowany kod natywny wygenerowany przez JIT (Code Cache).
         * - Metaspace NIE jest czescia sterty - rosnie w pamieci NATYWNEJ
         *   procesu (poza kontrola zwyklego GC sterty), az do limitu
         *   ustawionego flaga -XX:MaxMetaspaceSize (domyslnie brak limitu -
         *   ograniczeniem jest dostepna pamiec systemu operacyjnego).
         */
        demonstrateNonHeapMemoryUsage();

        /*
         * ============================================================
         * 📌 MemoryPoolMXBean - WSZYSTKIE REALNE PULE PAMIECI TEJ JVM
         * ============================================================
         * - JVM dzieli pamiec na wiele mniejszych "pul" (memory pools) -
         *   ich DOKLADNE nazwy i liczba ZALEZA od uzywanego kolektora GC
         *   (domyslnie G1 od Javy 9) oraz wersji JDK.
         * - Typowe przyklady dla G1: "G1 Eden Space", "G1 Survivor Space",
         *   "G1 Old Gen", "Metaspace", "Compressed Class Space",
         *   "CodeHeap 'non-nmethods'", "CodeHeap 'profiled nmethods'",
         *   "CodeHeap 'non-profiled nmethods'".
         * - Ponizej wypisujemy WSZYSTKIE pule, jakie faktycznie zwraca
         *   Twoja JVM - to jest "prawda", a nie podrecznikowy przyklad.
         */
        demonstrateMemoryPools();

        /*
         * ============================================================
         * 🔹 STOS WATKU (THREAD STACK) I StackOverflowError
         * ============================================================
         * - Kazdy watek dostaje wlasny, OGRANICZONY stos - jego rozmiar
         *   kontroluje flaga JVM -Xss (np. -Xss256k, domyslnie zalezny od
         *   platformy, czesto ok. 512KB-1MB).
         * - Kazde wywolanie metody dodaje na stos "ramke" (stack frame) -
         *   zmienne lokalne, adres powrotu, referencje. Rekurencja BEZ
         *   warunku stopu w koncu wyczerpie stos - JVM rzuca wtedy
         *   StackOverflowError (BLAD, nie wyjatek - dziedziczy z Error,
         *   nie Exception, bo program zwykle nie moze sie z niego sensownie
         *   wyleczyc - ale MOZNA go zlapac, jak ponizej, zeby np. zalogowac
         *   i bezpiecznie zakonczyc dana operacje).
         */
        demonstrateStackOverflow();

        /*
         * ============================================================
         * 📌 FLAGI JVM: -Xss I -XX:MaxMetaspaceSize (KONCEPCYJNIE)
         * ============================================================
         * - -Xss<rozmiar> - ustawia rozmiar stosu KAZDEGO nowego watku,
         *   np. `-Xss512k`. Zbyt maly stos = szybszy StackOverflowError
         *   przy glebokiej rekurencji; zbyt duzy = wiecej pamieci
         *   natywnej zuzytej NA WATEK (istotne przy tysiacach watkow -
         *   patrz rozdzial _05_multithreading).
         * - -XX:MaxMetaspaceSize=<rozmiar> - twardy limit na Metaspace.
         *   Bez tego limitu, patologiczny przypadek (np. generowanie
         *   tysiecy klas w runtime przez frameworki/proxy/classloadery -
         *   patrz Lesson04_CustomClassLoaders) moze zuzyc CALA dostepna
         *   pamiec natywna systemu, zamiast rzucic kontrolowany
         *   OutOfMemoryError: Metaspace.
         * - Obu flag NIE mozna odczytac w 100% wiarygodnie z poziomu
         *   samego kodu Javy (to ustawienia startowe JVM) - w praktyce
         *   sprawdza sie je przez `jcmd <pid> VM.flags` albo po prostu
         *   znajac wartosc przekazana przy starcie procesu.
         */
        explainStackAndMetaspaceFlags();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - MemoryMXBean daje ogolny obraz heap/non-heap (init/used/
         *   committed/max).
         * - MemoryPoolMXBeans pokazuja SZCZEGOLOWY podzial na realne
         *   pule pamieci - ich nazwy zaleza od kolektora GC i wersji JDK.
         * - StackOverflowError to realny, wykrywalny Blad (Error) -
         *   mozna go zlapac, ale to sygnal, ze albo rekurencja jest zla,
         *   albo dane sa zbyt glebokie dla tego algorytmu.
         * - -Xss kontroluje rozmiar stosu watku, -XX:MaxMetaspaceSize
         *   twardy limit Metaspace - w kolejnych lekcjach (8-10) zajmiemy
         *   sie tym, CO DOKLADNIE dzieje sie na stercie podczas GC.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void demonstrateHeapMemoryUsage() {
        System.out.println("\n=== MemoryMXBean: HEAP MEMORY USAGE ===");

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();

        printMemoryUsage("HEAP", heap);
    }

    private static void demonstrateNonHeapMemoryUsage() {
        System.out.println("\n=== MemoryMXBean: NON-HEAP MEMORY USAGE (m.in. Metaspace) ===");

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();

        printMemoryUsage("NON-HEAP", nonHeap);
    }

    private static void printMemoryUsage(String label, MemoryUsage usage) {
        System.out.println(" [" + label + "]");
        System.out.println("   init      = " + formatBytes(usage.getInit()));
        System.out.println("   used      = " + formatBytes(usage.getUsed()));
        System.out.println("   committed = " + formatBytes(usage.getCommitted()));
        System.out.println("   max       = " + (usage.getMax() < 0 ? "brak limitu (-1)" : formatBytes(usage.getMax())));
    }

    private static void demonstrateMemoryPools() {
        System.out.println("\n=== MemoryPoolMXBeans: WSZYSTKIE REALNE PULE PAMIECI TEJ JVM ===");

        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        System.out.println("Ta JVM zglasza " + pools.size() + " pul pamieci:");

        for (MemoryPoolMXBean pool : pools) {
            MemoryUsage usage = pool.getUsage();
            System.out.println(" - " + pool.getName()
                    + " [" + pool.getType() + "]"
                    + " uzyte=" + formatBytes(usage.getUsed())
                    + ", max=" + (usage.getMax() < 0 ? "brak limitu" : formatBytes(usage.getMax())));
        }

        System.out.println("Uwaga: dokladne nazwy pul (np. \"G1 Eden Space\") zaleza od kolektora GC");
        System.out.println("(domyslnie G1 od Javy 9) i moga byc inne przy innym JDK/kolektorze (patrz Lesson09).");
    }

    /**
     * Licznik glebokosci rekurencji - pole statyczne, zeby metoda recurse()
     * mogla byc BEZARGUMENTOWA (prostsza demonstracja czystego przepelnienia stosu).
     */
    private static long recursionDepth = 0;

    private static void recurse() {
        recursionDepth++;
        recurse();
    }

    private static void demonstrateStackOverflow() {
        System.out.println("\n=== StackOverflowError PRZEZ NIEOGRANICZONA REKURENCJE (ZLAPANY) ===");

        recursionDepth = 0;
        try {
            recurse();
        } catch (StackOverflowError e) {
            System.out.println("Zlapano StackOverflowError - program NIE zostal zabity.");
            System.out.println("Osiagnieta przyblizona glebokosc rekurencji: " + recursionDepth + " wywolan.");
            System.out.println("(dokladna liczba zalezy od rozmiaru stosu -Xss i rozmiaru ramki tej metody)");
        }
    }

    private static void explainStackAndMetaspaceFlags() {
        System.out.println("\n=== FLAGI JVM: -Xss I -XX:MaxMetaspaceSize (KONCEPCYJNIE) ===");
        System.out.println(" -Xss512k              -> rozmiar stosu KAZDEGO nowego watku");
        System.out.println(" -XX:MaxMetaspaceSize=256m -> twardy limit na Metaspace (metadane klas)");
        System.out.println("Te flagi ustawia sie przy starcie JVM (np. `java -Xss512k -jar app.jar`),");
        System.out.println("nie da sie ich w 100% niezawodnie odczytac z poziomu dzialajacego kodu Javy -");
        System.out.println("w praktyce uzywa sie do tego `jcmd <pid> VM.flags` z zewnatrz procesu.");
    }

    private static String formatBytes(long bytes) {
        if (bytes < 0) {
            return "n/a";
        }
        double mb = bytes / (1024.0 * 1024.0);
        return String.format("%.2f MB (%d B)", mb, bytes);
    }
}
