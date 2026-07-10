package com.example.javaquest._15_jvm_internals.Lesson15_MemoryLeaksInJava;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class _Lesson15_MemoryLeaksInJava {

    /** Statyczna kolekcja rosnaca bez ograniczen - klasyczny wzorzec wycieku #1. */
    private static final List<byte[]> LEAKY_STATIC_CACHE = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: WYCIEKI PAMIECI W JAVIE (MEMORY LEAKS) ===");

        /*
         * ============================================================
         * 🔹 DLACZEGO WYCIEKI SA W OGOLE MOZLIWE, SKORO MAMY GC?
         * ============================================================
         * - Garbage Collector NIE sprzata obiektow "niepotrzebnych" -
         *   sprzata obiekty NIEOSIAGALNE (unreachable), czyli takie, do
         *   ktorych nie prowadzi ZADNA sciezka referencji od "GC roots"
         *   (zmienne lokalne na stosie, pola statyczne, aktywne watki itp.).
         * - "Wyciek pamieci w Javie" oznacza wiec: obiekt jest LOGICZNIE
         *   niepotrzebny (program juz go nigdy nie uzyje sensownie), ale
         *   WCIAZ OSIAGALNY - jakas referencja (czesto zapomniana) wciaz
         *   do niego prowadzi. GC widzi "osiagalny" i grzecznie zostawia
         *   go w spokoju - bo z jego punktu widzenia to wciaz "zywy" obiekt.
         * - Klasyczne miejsca, gdzie takie zapomniane referencje sie
         *   ukrywaja: statyczne kolekcje, niewyrejestrowani obserwatorzy,
         *   ThreadLocal w puli watkow, niejawne referencje z klas
         *   wewnetrznych - ponizej PRAWDZIWIE demonstrujemy wszystkie 4.
         */
        System.out.println("GC sprzata obiekty NIEOSIAGALNE, nie 'niepotrzebne' - wyciek to obiekt logicznie zbedny, ale wciaz osiagalny.");

        /*
         * ============================================================
         * 🔍 WZORZEC 1: STATYCZNA KOLEKCJA ROSNACA BEZ OGRANICZEN
         * ============================================================
         * - Pole statyczne zyje TAK DLUGO, jak dlugo zaladowana jest jego
         *   klasa (praktycznie: caly czas dzialania aplikacji). Jesli cos
         *   DODAJE do takiej kolekcji bez odpowiadajacego USUWANIA, ona
         *   rosnie W NIESKONCZONOSC - klasyczny "unbounded cache".
         * - Ponizej: 50 000 iteracji dodajacych male tablice bajtow do
         *   statycznej listy LEAKY_STATIC_CACHE - BEZ zadnego czyszczenia.
         *   Mierzymy realne zuzycie sterty (MemoryMXBean) przed i po.
         */
        demonstrateGrowingStaticCollection();

        /*
         * ============================================================
         * 🔍 WZORZEC 2: OBSERWATOR NIGDY NIE WYREJESTROWANY
         * ============================================================
         * - Wzorzec obserwator (Subject/Observer): Subject trzyma LISTE
         *   swoich obserwatorow, kazdy Observer moze trzymac referencje do
         *   duzych danych (np. "kontekst" swojego zycia).
         * - Jesli KTOS zapomni wywolac subject.removeListener(observer),
         *   Subject wiecznie trzyma referencje do Observera (a Observer -
         *   do swoich danych) - NAWET jesli "logicznie" obserwator juz
         *   dawno powinien byc martwy.
         * - Demonstrujemy to przez WeakReference: tworzymy Observera,
         *   rejestrujemy go w Subject, "gubimy" nasza wlasna, silna
         *   referencje - i pokazujemy, ze WeakReference WCIAZ zwraca
         *   zywy obiekt (bo Subject go trzyma!) dopoki nie wywolamy
         *   removeListener().
         */
        demonstrateUnregisteredObserverLeak();

        /*
         * ============================================================
         * 🔍 WZORZEC 3: THREADLOCAL W PULI WATKOW BEZ .remove()
         * ============================================================
         * - ThreadLocal<T> trzyma OSOBNA wartosc DLA KAZDEGO WATKU. W puli
         *   watkow (np. ExecutorService o stalej liczbie watkow) te SAME
         *   watki sa REUZYWANE do wielu roznych zadan.
         * - Jesli zadanie ustawia wartosc ThreadLocal (np. "kontekst
         *   zapytania") i NIE wywoluje .remove() na koniec, ta wartosc
         *   ZOSTAJE przypieta do watku - i "przecieka" do NASTEPNEGO
         *   zadania, ktore trafi na TEN SAM watek z puli!
         * - Ponizej: pula 2 watkow, kilka zadan - najpierw BEZ .remove()
         *   (pokazujemy przeciek wartosci miedzy zadaniami), potem Z
         *   .remove() (pokazujemy poprawke - kazde zadanie widzi czysty stan).
         */
        demonstrateThreadLocalLeakInThreadPool();

        /*
         * ============================================================
         * 🔍 WZORZEC 4: NIE-STATYCZNA KLASA WEWNETRZNA I NIEJAWNA REFERENCJA
         * ============================================================
         * - Kazda instancja NIE-STATYCZNEJ klasy wewnetrznej (inner class)
         *   trzyma NIEJAWNA referencje do instancji klasy zewnetrznej
         *   (Outer.this) - kompilator dodaje ja automatycznie, zeby inner
         *   class mogla siegac po pola/metody outer.
         * - Jesli DLUGO ZYJACA kolekcja (np. cache) trzyma instancje takiej
         *   klasy wewnetrznej, NIEJAWNIE trzyma tez CALY obiekt zewnetrzny -
         *   nawet jesli logika programu "skonczyla" z tym zewnetrznym
         *   obiektem.
         * - Demonstrujemy to przez WeakReference na "duzym" obiekcie
         *   zewnetrznym: dopoki lista dlugozyjaca trzyma jego wewnetrzna
         *   klase, WeakReference pokazuje, ze zewnetrzny obiekt WCIAZ zyje.
         */
        demonstrateNonStaticInnerClassLeak();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wycieki w Javie to NIE "duchy poza kontrola GC" - to zwykle,
         *   REALNE referencje, o ktorych ktos zapomnial: statyczna
         *   kolekcja bez limitu, niewyrejestrowany obserwator, ThreadLocal
         *   bez .remove() w puli watkow, niejawna referencja z klasy
         *   wewnetrznej do obiektu zewnetrznego.
         * - Wspolny mianownik wszystkich 4 wzorcow: cos ZYJE DLUZEJ niz
         *   powinno (statyczne pole, dlugozyjacy Subject, watek z puli,
         *   dlugozyjaca kolekcja) i TRZYMA referencje do czegos, co
         *   logicznie powinno juz zniknac.
         * - Praktyczne przeciwdzialanie: ograniczaj rozmiar
         *   cache'y/kolekcji statycznych (np. przez polityki eksmisji),
         *   ZAWSZE parujuj register()/removeListener(),
         *   ZAWSZE wolaj threadLocal.remove() na koncu zadania w puli
         *   watkow, i uzywaj STATYCZNYCH klas wewnetrznych, gdy nie
         *   potrzebujesz dostepu do stanu obiektu zewnetrznego.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void demonstrateGrowingStaticCollection() {
        System.out.println("\n--- Wzorzec 1: rosnaca statyczna kolekcja ---");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        System.gc();
        long heapBefore = memoryBean.getHeapMemoryUsage().getUsed();

        int iterations = 50_000;
        for (int i = 0; i < iterations; i++) {
            LEAKY_STATIC_CACHE.add(new byte[256]); // NIC nigdy nie usuwa elementow z tej listy!
        }

        long heapAfter = memoryBean.getHeapMemoryUsage().getUsed();

        System.out.println("Dodano " + iterations + " elementow do LEAKY_STATIC_CACHE (nigdy nieczyszczonej).");
        System.out.println("Rozmiar kolekcji po petli: " + LEAKY_STATIC_CACHE.size() + " elementow.");
        System.out.println("Przyrost zuzycia sterty (heurystyczny, MemoryMXBean): "
                + ((heapAfter - heapBefore) / 1024) + " KB.");
        System.out.println("Ta kolekcja bedzie ROSNAC w nieskonczonosc, dopoki aplikacja dziala - klasyczny 'unbounded cache'.");
    }

    /** Duzy obiekt "kontekstu", ktory Observer trzyma - symuluje realne dane niosace koszt pamieciowy. */
    private static final class HeavyContext {
        private final byte[] payload = new byte[1024 * 64]; // 64 KB - wystarczajaco duzo, by bylo "cos" do wyciekania
    }

    private static final class Observer {
        private final String name;
        private final HeavyContext context; // referencja do "duzych" danych

        Observer(String name, HeavyContext context) {
            this.name = name;
            this.context = context;
        }

        void onEvent(String event) {
            // celowo puste/minimalne - w tej lekcji interesuje nas TYLKO cykl zycia referencji
        }
    }

    private static final class Subject {
        private final List<Observer> observers = new ArrayList<>();

        void addListener(Observer observer) {
            observers.add(observer);
        }

        void removeListener(Observer observer) {
            observers.remove(observer);
        }

        void fireEvent(String event) {
            for (Observer observer : observers) {
                observer.onEvent(event);
            }
        }

        int listenerCount() {
            return observers.size();
        }
    }

    private static void demonstrateUnregisteredObserverLeak() {
        System.out.println("\n--- Wzorzec 2: obserwator nigdy niewyrejestrowany ---");

        Subject subject = new Subject();
        Observer observer = new Observer("raportowy-obserwator", new HeavyContext());
        WeakReference<Observer> weakObserverRef = new WeakReference<>(observer);

        subject.addListener(observer);
        subject.fireEvent("start");

        observer = null; // gubimy NASZA wlasna, silna referencje - ale Subject WCIAZ ja trzyma!
        System.gc();

        System.out.println("Po utracie naszej referencji i System.gc(): observer wciaz zywy? "
                + (weakObserverRef.get() != null) + " (Subject go trzyma, obserwator NIGDY nie zostal wyrejestrowany).");
        System.out.println("Liczba obserwatorow w Subject: " + subject.listenerCount());

        System.out.println("Naprawa: subject.removeListener(...) PRZED utrata referencji.");
        Observer secondObserver = new Observer("drugi-obserwator", new HeavyContext());
        WeakReference<Observer> weakSecondRef = new WeakReference<>(secondObserver);
        subject.addListener(secondObserver);

        subject.removeListener(secondObserver); // POPRAWNE wyrejestrowanie PRZED utrata referencji
        secondObserver = null;
        System.gc();

        System.out.println("Po removeListener() + utracie referencji + System.gc(): observer wciaz zywy? "
                + (weakSecondRef.get() != null) + " (tym razem powinno byc false - obiekt mozliwy do zebrania).");
        System.out.println("Liczba obserwatorow w Subject po naprawie: " + subject.listenerCount());
    }

    private static void demonstrateThreadLocalLeakInThreadPool() throws InterruptedException {
        System.out.println("\n--- Wzorzec 3: ThreadLocal w puli watkow bez .remove() ---");

        ThreadLocal<String> requestContext = new ThreadLocal<>();

        // KAZDA faza dostaje WLASNA, SWIEZA pule watkow - inaczej resztki
        // (niesprzatniete wartosci) z Fazy A "przecieklyby" do Fazy B i
        // falszywie zasugerowalyby, ze naprawa (.remove()) nie zadziala.
        ExecutorService poolA = Executors.newFixedThreadPool(2);
        try {
            System.out.println("Faza A: BEZ wywolania .remove() na koniec zadania (przeciek miedzy zadaniami).");
            List<String> observedWithoutRemove = runTasksAndCollectObservedValues(poolA, requestContext, false, "A", 4);
            System.out.println("Zaobserwowane wartosci ThreadLocal na starcie kolejnych zadan (bez remove): " + observedWithoutRemove);
            boolean leakDetected = observedWithoutRemove.stream().anyMatch(v -> !v.equals("brak-wartosci"));
            System.out.println("Wykryto 'przeciekniete' wartosci z poprzednich zadan: " + leakDetected);
        } finally {
            poolA.shutdown();
            if (!poolA.awaitTermination(5, TimeUnit.SECONDS)) {
                poolA.shutdownNow();
            }
        }

        ExecutorService poolB = Executors.newFixedThreadPool(2);
        try {
            System.out.println("\nFaza B: NOWA pula watkow, Z wywolaniem .remove() na koniec kazdego zadania (poprawka).");
            List<String> observedWithRemove = runTasksAndCollectObservedValues(poolB, requestContext, true, "B", 4);
            System.out.println("Zaobserwowane wartosci ThreadLocal na starcie kolejnych zadan (z remove): " + observedWithRemove);
            boolean allClean = observedWithRemove.stream().allMatch(v -> v.equals("brak-wartosci"));
            System.out.println("Wszystkie zadania widzialy czysty stan (brak przecieku): " + allClean);
        } finally {
            poolB.shutdown();
            if (!poolB.awaitTermination(5, TimeUnit.SECONDS)) {
                poolB.shutdownNow();
            }
        }
    }

    /**
     * Wysyla kolejno {@code taskCount} zadan do puli 2 watkow. Kazde zadanie
     * NAJPIERW odczytuje biezaca wartosc ThreadLocal (zeby wykryc ewentualny
     * "przeciek" z poprzedniego zadania na tym samym watku), a POTEM ustawia
     * WLASNA wartosc - opcjonalnie sprzatajac ja na koniec (.remove()).
     * Zadania wykonywane SA SEKWENCYJNIE (kazde czeka na zakonczenie
     * poprzedniego), zeby deterministycznie trafiac na te same watki z puli.
     */
    private static List<String> runTasksAndCollectObservedValues(
            ExecutorService pool, ThreadLocal<String> requestContext, boolean callRemove, String phaseLabel, int taskCount)
            throws InterruptedException {

        List<String> observedValues = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            String taskValue = "zadanie-" + phaseLabel + i;
            CountDownLatch latch = new CountDownLatch(1);
            String[] observedHolder = new String[1];

            pool.submit(() -> {
                String existing = requestContext.get();
                observedHolder[0] = existing == null ? "brak-wartosci" : existing;
                requestContext.set(taskValue);
                if (callRemove) {
                    requestContext.remove();
                }
                latch.countDown();
            });

            latch.await(5, TimeUnit.SECONDS); // czekamy na TO zadanie, zanim wyslemy kolejne (deterministyczna kolejnosc)
            observedValues.add(observedHolder[0]);
        }
        return observedValues;
    }

    /** "Duzy" obiekt zewnetrzny - symuluje np. kontroler/serwis niosacy realny koszt pamieciowy. */
    private static final class LargeOuterComponent {
        private final byte[] largeState = new byte[1024 * 128]; // 128 KB

        // KLASA WEWNETRZNA NIE-STATYCZNA - kazda jej instancja niejawnie
        // trzyma referencje do LargeOuterComponent.this!
        final class Listener {
            void onTick() {
                // celowo puste - interesuje nas tylko niejawna referencja do zewnetrznego obiektu
            }
        }

        Listener newListener() {
            return new Listener();
        }
    }

    private static void demonstrateNonStaticInnerClassLeak() {
        System.out.println("\n--- Wzorzec 4: nie-statyczna klasa wewnetrzna i niejawna referencja ---");

        List<LargeOuterComponent.Listener> longLivedListeners = new ArrayList<>();

        LargeOuterComponent outerComponent = new LargeOuterComponent();
        WeakReference<LargeOuterComponent> weakOuterRef = new WeakReference<>(outerComponent);

        LargeOuterComponent.Listener listener = outerComponent.newListener();
        longLivedListeners.add(listener); // DLUGOZYJACA kolekcja trzyma inner class...

        outerComponent = null; // ...a inner class NIEJAWNIE trzyma caly LargeOuterComponent!
        listener = null;
        System.gc();

        System.out.println("Po utracie bezposredniej referencji do LargeOuterComponent i System.gc():");
        System.out.println("czy zewnetrzny obiekt (128 KB) wciaz zyje? " + (weakOuterRef.get() != null)
                + " (trzymany NIEJAWNIE przez Listener w longLivedListeners).");

        System.out.println("Naprawa: usuwamy Listener z dlugozyjacej kolekcji.");
        longLivedListeners.clear();
        System.gc();

        System.out.println("Po longLivedListeners.clear() i System.gc(): zewnetrzny obiekt wciaz zyje? "
                + (weakOuterRef.get() != null) + " (powinno byc false - brak juz zadnej sciezki referencji).");
    }
}
