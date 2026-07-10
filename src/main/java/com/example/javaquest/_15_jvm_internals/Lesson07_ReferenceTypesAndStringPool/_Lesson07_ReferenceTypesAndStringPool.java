package com.example.javaquest._15_jvm_internals.Lesson07_ReferenceTypesAndStringPool;

import java.lang.ref.Cleaner;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public class _Lesson07_ReferenceTypesAndStringPool {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 7: TYPY REFERENCJI I STRING POOL ===");

        /*
         * ============================================================
         * 🔹 PRZYPOMNIENIE Z _01_fundamentals/Lesson14_GarbageCollector
         * ============================================================
         * - Tamta lekcja pokazala PODSTAWOWE API referencji (silna
         *   referencja domyslnie, System.gc() jako sugestia).
         * - Tutaj poznajemy 3 SPECJALNE typy referencji (WeakReference,
         *   SoftReference, PhantomReference) - narzedzia dla twórcow
         *   cache'y, mapy WeakHashMap i frameworkow zarzadzajacych
         *   zasobami natywnymi - oraz String pool, czyli mechanizm
         *   wspoldzielenia identycznych literalow tekstowych.
         */
        System.out.println("Silna referencja (strong) to domyslna - dopoki istnieje, GC NIGDY nie usunie obiektu.");

        /*
         * ============================================================
         * 🔍 WeakReference + ReferenceQueue - REFERENCJA "SLABA"
         * ============================================================
         * - WeakReference<T> NIE zapobiega usunieciu obiektu przez GC -
         *   jesli jedyna referencja do obiektu to referencja slaba, GC
         *   MOZE go usunac przy NAJBLIZSZYM cyklu (nie czeka na presje
         *   pamieci, w odroznieniu od SoftReference ponizej).
         * - Klasyczne zastosowanie: WeakHashMap (klucze trzymane slabo -
         *   wpis znika automatycznie, gdy nikt inny nie trzyma klucza).
         * - ReferenceQueue pozwala DOWIEDZIEC SIE, kiedy referencja
         *   zostala wyczyszczona - JVM sama wklada wyczyszczona referencje
         *   do kolejki.
         * - System.gc() to TYLKO SUGESTIA dla JVM (nie ma gwarancji) -
         *   ale w praktyce, dla prostego, izolowanego przykladu jak
         *   ponizej, zazwyczaj dziala niezawodnie.
         */
        demonstrateWeakReference();

        /*
         * ============================================================
         * 🔹 SoftReference - REFERENCJA "MIEKKA"
         * ============================================================
         * - SoftReference<T> jest czyszczona przez GC DOPIERO POD PRESJA
         *   PAMIECI (gdy JVM naprawde potrzebuje odzyskac miejsce na
         *   stercie, tuz przed OutOfMemoryError) - w praktyce oznacza to
         *   "trzymaj to, dopoki masz miejsce, ale nie pozwol na OOM z
         *   mojego powodu".
         * - Klasyczne zastosowanie: cache w pamieci (np. wyniki drogich
         *   obliczen) - dane znikaja automatycznie, gdy pamiec robi sie
         *   ciasna, zamiast rzucac OutOfMemoryError.
         * - WAZNE: czyszczenia SoftReference NIE da sie deterministycznie
         *   pokazac w krotkiej, bezpiecznej demonstracji - wymagaloby to
         *   celowego wyczerpania sterty, co jest ryzykowne. Ponizsza metoda
         *   mowi to WPROST, zamiast udawac zachowanie, ktorego nie
         *   zademonstruje w bezpieczny sposob.
         */
        demonstrateSoftReference();

        /*
         * ============================================================
         * 🔍 PhantomReference I Cleaner - NOWOCZESNA ALTERNATYWA DLA finalize()
         * ============================================================
         * - PhantomReference<T> jest NAJSLABSZYM typem referencji -
         *   get() ZAWSZE zwraca null (nie da sie przez nia dostac do
         *   obiektu) - jej JEDYNYM celem jest powiadomienie (przez
         *   ReferenceQueue), ze obiekt zostal juz uznany za martwy i
         *   sfinalizowany przez GC, wiec to bezpieczny moment na
         *   posprzatanie zasobow NATYWNYCH (pliki, sockety, pamiec poza
         *   sterta Javy).
         * - Metoda Object.finalize() (stara, PRZESTARZALA od Javy 9)
         *   mialy podobny cel, ale byla nieprzewidywalna i mogla
         *   "wskrzesic" obiekt - java.lang.ref.Cleaner (od Javy 9) to
         *   jej bezpieczny, nowoczesny zastepnik, zbudowany WLASNIE na
         *   PhantomReference pod spodem.
         * - Cleaner.create().register(obiekt, akcjaSprzatajaca) rejestruje
         *   Runnable, ktory JVM wywola, gdy `obiekt` stanie sie
         *   nieosiagalny - akcja NIE MOZE trzymac referencji do `obiekt`
         *   (inaczej nigdy by nie umarl!).
         */
        demonstratePhantomReferenceAndCleaner();

        /*
         * ============================================================
         * 📌 STRING POOL - WSPOLDZIELENIE LITERALOW TEKSTOWYCH
         * ============================================================
         * - String pool (pula stringow) to specjalny obszar pamieci
         *   (czesc sterty od Javy 7), gdzie JVM trzyma PO JEDNEJ kopii
         *   kazdego literalu tekstowego - "Ala" napisane w 5 miejscach
         *   kodu to w rzeczywistosci JEDEN obiekt String w pamieci.
         * - `new String("Ala")` OMIJA pule - tworzy NOWY obiekt na
         *   stercie, nawet jesli "Ala" juz jest w puli.
         * - `.intern()` sprawdza, czy rownowazny String jest juz w puli -
         *   jesli tak, zwraca REFERENCJE do wersji z puli (nie tworzy
         *   duplikatu).
         * - Operator == porownuje REFERENCJE (czy to ten sam obiekt w
         *   pamieci), .equals() porownuje WARTOSC (zawartosc znakow) -
         *   dla Stringow NALEZY prawie zawsze uzywac .equals().
         */
        demonstrateStringPool();

        /*
         * ============================================================
         * 🔍 CIEKAWOSTKA: COMPACT STRINGS (JAVA 9+)
         * ============================================================
         * - Przed Java 9 kazdy String trzymal znaki jako char[] (2 bajty
         *   na znak, UTF-16), NIEZALEZNIE od tego, czy tresc wymagala az
         *   tylu bitow.
         * - Od Javy 9 (JEP 254) String trzyma dane jako byte[] i
         *   dodatkowy znacznik kodowania: jesli WSZYSTKIE znaki miesza
         *   sie w Latin-1 (1 bajt/znak - wiekszosc tekstow po polsku i
         *   angielsku), JVM uzywa 1 bajta/znak; dopiero gdy pojawi sie
         *   znak spoza Latin-1 (np. emoji, chinski), przechodzi na
         *   pelne UTF-16 (2 bajty/znak).
         * - Efekt: typowe aplikacje zuzywaja realnie MNIEJ pamieci na
         *   Stringi, bez ZADNEJ zmiany w kodzie (dzieje sie w JVM, pod
         *   spodem, przezroczyscie dla programisty).
         */
        System.out.println("\nCompact Strings (Java 9+): Stringi z samych znakow Latin-1 zajmuja 1 bajt/znak zamiast 2.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - WeakReference - czyszczona przy NAJBLIZSZYM GC, gdy brak
         *   silnych referencji (np. WeakHashMap).
         * - SoftReference - czyszczona dopiero POD PRESJA PAMIECI (cache).
         * - PhantomReference + Cleaner - bezpieczne sprzatanie zasobow
         *   natywnych, nowoczesny zastepnik przestarzalego finalize().
         * - String pool + .intern() - wspoldzielenie identycznych
         *   literalow, == porownuje referencje, .equals() wartosc.
         * - W kolejnej lekcji (Lesson08) poznamy PODSTAWY samego
         *   algorytmu Garbage Collection - hipoteze generacyjna, GC roots
         *   i fazy mark-sweep-compact.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void demonstrateWeakReference() throws InterruptedException {
        System.out.println("\n=== WeakReference + ReferenceQueue ===");

        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object target = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(target, queue);

        System.out.println("Przed usunieciem silnej referencji: weakReference.get() = " + weakReference.get());

        target = null; // usuwamy JEDYNA silna referencje do obiektu
        System.gc(); // tylko sugestia dla JVM - w praktyce zwykle wystarcza dla tak prostego przypadku

        Reference<?> cleared = null;
        for (int attempt = 0; attempt < 20 && cleared == null; attempt++) {
            cleared = queue.poll();
            if (cleared == null) {
                Thread.sleep(50); // krotkie, ograniczone oczekiwanie - max ~1 sekunda lacznie
            }
        }

        if (cleared != null) {
            System.out.println("Referencja pojawila sie w ReferenceQueue - obiekt zostal usuniety przez GC.");
        } else {
            System.out.println("GC jeszcze nie zdazyl wyczyscic referencji w tym czasie (System.gc() to tylko sugestia).");
        }
        System.out.println("weakReference.get() teraz = " + weakReference.get());
    }

    private static void demonstrateSoftReference() {
        System.out.println("\n=== SoftReference ===");

        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024]);
        System.out.println("Zaraz po utworzeniu, softReference.get() != null: " + (softReference.get() != null));
        System.out.println("SoftReference jest czyszczona DOPIERO pod PRESJA PAMIECI (blisko OutOfMemoryError).");
        System.out.println("Tego NIE da sie bezpiecznie i deterministycznie pokazac bez celowego wyczerpania sterty -");
        System.out.println("dlatego ta demonstracja swiadomie tego NIE probuje - to opisujemy, a nie udajemy.");
    }

    private static void demonstratePhantomReferenceAndCleaner() throws InterruptedException {
        System.out.println("\n=== PhantomReference + java.lang.ref.Cleaner ===");

        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        AtomicBoolean cleanupRan = new AtomicBoolean(false);

        Cleaner cleaner = Cleaner.create();
        Object resource = new Object();
        // WAZNE: akcja sprzatajaca NIE MOZE trzymac referencji do `resource`!
        Cleaner.Cleanable cleanable = cleaner.register(resource, () -> {
            cleanupRan.set(true);
            System.out.println("   [Cleaner] Akcja sprzatajaca wykonana - zasob zostal 'zamkniety'.");
        });

        PhantomReference<Object> phantomReference = new PhantomReference<>(resource, queue);
        System.out.println("phantomReference.get() zawsze zwraca null: " + phantomReference.get());

        resource = null; // usuwamy jedyna silna referencje
        System.gc();

        for (int attempt = 0; attempt < 20 && !cleanupRan.get(); attempt++) {
            Thread.sleep(50);
        }

        System.out.println("Cleaner akcja wykonana: " + cleanupRan.get()
                + (cleanupRan.get() ? "" : " (zalezne od czasu GC - proba jest ograniczona czasowo)"));

        cleanable.clean(); // idempotentne - jesli juz wykonane, nie zrobi nic ponownie
    }

    private static void demonstrateStringPool() {
        System.out.println("\n=== STRING POOL: ==, .equals(), .intern() ===");

        String literalA = "javaquest";
        String literalB = "javaquest";
        String heapString = new String("javaquest");
        String internedHeapString = heapString.intern();

        System.out.println("literalA == literalB (oba literaly, ta sama pula): " + (literalA == literalB));
        System.out.println("literalA == heapString (new String(), POZA pula): " + (literalA == heapString));
        System.out.println("literalA.equals(heapString) (porownanie WARTOSCI): " + literalA.equals(heapString));
        System.out.println("literalA == internedHeapString (po .intern(), z powrotem w puli): " + (literalA == internedHeapString));
    }
}
