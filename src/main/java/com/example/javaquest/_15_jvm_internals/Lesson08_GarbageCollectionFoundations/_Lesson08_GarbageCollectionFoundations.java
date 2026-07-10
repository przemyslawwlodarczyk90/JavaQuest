package com.example.javaquest._15_jvm_internals.Lesson08_GarbageCollectionFoundations;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class _Lesson08_GarbageCollectionFoundations {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 8: PODSTAWY GARBAGE COLLECTION ===");

        /*
         * ============================================================
         * 🔹 HIPOTEZA GENERACYJNA (GENERATIONAL HYPOTHESIS)
         * ============================================================
         * - Obserwacja empiryczna, na ktorej opiera sie WIEKSZOSC
         *   nowoczesnych kolektorow GC: "WIEKSZOSC obiektow umiera
         *   MLODO" (weak generational hypothesis).
         * - Przyklad: zmienna lokalna w metodzie, obiekt tymczasowy
         *   (StringBuilder w petli, DTO stworzone na chwile) - zyja
         *   milisekundy, a potem staja sie smieciem.
         * - Wniosek praktyczny: skoro wiekszosc obiektow umiera szybko,
         *   OPLACA SIE czesto (i TANIO) sprzatac MALY obszar pamieci
         *   ("mlodej generacji"), zamiast za kazdym razem przeszukiwac
         *   CALA sterte - stad podzial na generacje (young/old) w
         *   wiekszosci kolektorow (Serial, Parallel, G1 - patrz Lesson09).
         */
        System.out.println("Hipoteza generacyjna: wiekszosc obiektow umiera mlodo -> GC czesciej sprzata 'mlody' obszar.");

        /*
         * ============================================================
         * 🔍 GC ROOTS - PUNKTY STARTOWE ANALIZY OSIAGALNOSCI
         * ============================================================
         * - GC nie "szuka smieci" wprost - szuka OBIEKTOW OSIAGALNYCH
         *   (reachable), startujac od zbioru tzw. GC roots, i wszystko
         *   inne (nieosiagalne z korzeni) uznaje za smiec.
         * - Typowe GC roots:
         *     * zmienne LOKALNE i parametry na stosie kazdego AKTYWNEGO watku,
         *     * statyczne pola klas (zyja tak dlugo, jak klasa jest zaladowana),
         *     * aktywne watki same w sobie (Thread jako obiekt),
         *     * referencje JNI (kod natywny trzymajacy obiekt Javy).
         * - Obiekt jest "osiagalny", jesli istnieje LANCUCH referencji
         *   od jakiegokolwiek GC root do tego obiektu.
         */
        demonstrateReachability();

        /*
         * ============================================================
         * 🔹 ALGORYTM MARK-SWEEP-COMPACT - 3 FAZY
         * ============================================================
         * - MARK (oznaczanie): GC przechodzi graf referencji od GC roots
         *   i oznacza kazdy NAPOTKANY (osiagalny) obiekt jako "zywy".
         * - SWEEP (zamiatanie): GC przechodzi CALA stercie i odzyskuje
         *   pamiec zajeta przez obiekty NIEoznaczone (martwe) - ich
         *   miejsce staje sie wolne do ponownej alokacji.
         * - COMPACT (kompaktowanie): opcjonalna faza - GC PRZESUWA
         *   pozostale zywe obiekty, zeby byly obok siebie (bez "dziur"),
         *   co eliminuje fragmentacje i przyspiesza kolejne alokacje
         *   (nowy obiekt = po prostu przesuniecie wskaznika, bez
         *   szukania wolnej "dziury" odpowiedniego rozmiaru).
         */
        System.out.println("\nFazy GC: MARK (oznacz zywe) -> SWEEP (odzyskaj martwe) -> COMPACT (usun fragmentacje).");

        /*
         * ============================================================
         * 📌 TERMINOLOGIA: MINOR / MAJOR / FULL GC
         * ============================================================
         * - Minor GC (mlode GC) - sprzata TYLKO mloda generacje (Eden +
         *   Survivor) - szybkie, czeste, krotkie pauzy.
         * - Major GC - sprzata stara generacje (old generation) - obiekty,
         *   ktore przetrwaly wystarczajaco duzo cykli minor GC, zeby
         *   zostac "awansowane" (promoted) do starej generacji.
         * - Full GC - sprzata CALA sterte (mloda + stara + czesto Metaspace) -
         *   NAJWOLNIEJSZA operacja GC, zazwyczaj z najdluzsza pauza
         *   (stop-the-world) - jej czestotliwosc to jeden z glownych
         *   wskaznikow "zdrowia" strojenia GC aplikacji.
         */
        System.out.println("Minor GC (mloda generacja) < Major GC (stara generacja) < Full GC (cala sterta).");

        /*
         * ============================================================
         * 🔍 REALNA INTROSPEKCJA: GarbageCollectorMXBean
         * ============================================================
         * - ManagementFactory.getGarbageCollectorMXBeans() zwraca liste
         *   kolektorow DZIALAJACYCH w TEJ WLASNIE JVM (domyslnie G1 od
         *   Javy 9 - zobaczysz zazwyczaj 2 "kolektory logiczne" G1,
         *   odpowiadajace za mloda i mieszana/stara faze).
         * - getCollectionCount() - ile razy dany kolektor zadzialal od
         *   startu JVM.
         * - getCollectionTime() - laczny czas (ms) spedzony w tym
         *   kolektorze od startu JVM.
         */
        demonstrateGarbageCollectorBeans();

        /*
         * ============================================================
         * 📌 DOWOD EMPIRYCZNY: ALOKACJA SMIECI ZWIEKSZA LICZNIK GC
         * ============================================================
         * - Ponizej alokujemy 100 000 malych, krotko zyjacych obiektow
         *   (bounded - nie ma ryzyka OutOfMemoryError ani zawieszenia) i
         *   sprawdzamy, czy getCollectionCount() ktoregos z kolektorow
         *   WZROSL po tej operacji - to najprostszy mozliwy "dowod" na
         *   dzialanie GC w praktyce, bez zgadywania.
         */
        demonstrateAllocationTriggersGc();

        /*
         * ============================================================
         * 🔍 OPCJONALNIE: NASLUCHIWANIE NA REALNE ZDARZENIA GC (JMX)
         * ============================================================
         * - Kazdy GarbageCollectorMXBean jest jednoczesnie
         *   NotificationEmitter - mozna zarejestrowac NotificationListener
         *   i dostawac powiadomienie o KAZDYM zakonczonym cyklu GC (nazwa,
         *   przyczyna, czas trwania) - to dokladnie ten mechanizm, na
         *   ktorym opieraja sie narzedzia monitorujace typu VisualVM/JFR.
         * - UWAGA: to, czy powiadomienie faktycznie nadejdzie w krotkim,
         *   ograniczonym oknie czasowym, zalezy od JVM/kolektora - kod
         *   ponizej UCZCIWIE informuje o obu mozliwych wynikach.
         */
        demonstrateGcNotifications();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Hipoteza generacyjna: wiekszosc obiektow umiera mlodo ->
         *   podzial na generacje mlodo/stara.
         * - GC roots (stos, statyczne pola, watki, JNI) to punkty
         *   startowe analizy osiagalnosci - reszta grafu to potencjalne smieci.
         * - Mark-sweep-compact: oznacz zywe -> odzyskaj martwe -> usun fragmentacje.
         * - Minor < Major < Full GC - rosnacy zakres i koszt.
         * - GarbageCollectorMXBean daje REALNE liczniki z dzialajacej JVM.
         * - W kolejnej lekcji (Lesson09) porownamy KONKRETNE ALGORYTMY/
         *   implementacje kolektorow (Serial/Parallel/G1/ZGC/Shenandoah).
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    /**
     * Pole statyczne (GC root) - obiekt osiagalny, dopoki referencja tu
     * istnieje.
     */
    private static Object staticallyReachableObject = new Object();

    private static void demonstrateReachability() {
        System.out.println("\n=== REACHABILITY: OBIEKT OSIAGALNY VS NIEOSIAGALNY ===");

        Object localReference = new Object();
        System.out.println("localReference (zmienna lokalna = GC root) jest OSIAGALNY: " + (localReference != null));
        System.out.println("staticallyReachableObject (pole statyczne = GC root) jest OSIAGALNY: "
                + (staticallyReachableObject != null));

        Object temporary = new Object();
        System.out.println("Stworzono 'temporary' - w tej chwili OSIAGALNY (lokalna referencja go trzyma).");
        temporary = null; // usunieta jedyna referencja
        System.out.println("Po 'temporary = null' - obiekt jest NIEOSIAGALNY -> KANDYDAT do GC");
        System.out.println("(sam fakt bycia nieosiagalnym NIE oznacza natychmiastowego usuniecia - to zalezy od GC).");
    }

    private static void demonstrateGarbageCollectorBeans() {
        System.out.println("\n=== GarbageCollectorMXBeans DZIALAJACE W TEJ JVM ===");

        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println(" - " + gcBean.getName()
                    + " | liczba cykli=" + gcBean.getCollectionCount()
                    + " | laczny czas=" + gcBean.getCollectionTime() + " ms");
        }
    }

    private static void demonstrateAllocationTriggersGc() {
        System.out.println("\n=== ALOKACJA SMIECI A LICZNIK GC (DOWOD EMPIRYCZNY) ===");

        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        long totalCountBefore = gcBeans.stream().mapToLong(GarbageCollectorMXBean::getCollectionCount).sum();
        System.out.println("Laczna liczba cykli GC PRZED alokacja: " + totalCountBefore);

        long checksum = allocateGarbage(100_000);
        System.out.println("Zaalokowano 100 000 krotko zyjacych obiektow (checksum kontrolny=" + checksum + ")");

        long totalCountAfter = gcBeans.stream().mapToLong(GarbageCollectorMXBean::getCollectionCount).sum();
        System.out.println("Laczna liczba cykli GC PO alokacji: " + totalCountAfter);

        if (totalCountAfter > totalCountBefore) {
            System.out.println("Licznik GC WZROSL - kolektor faktycznie zadzialal.");
        } else {
            System.out.println("Licznik GC sie NIE zmienil - JVM uznala, ze mloda generacja ma jeszcze miejsce (to tez normalne).");
        }
    }

    /**
     * Alokuje `count` malych, krotko zyjacych tablic bajtowych - zaden wynik
     * nie jest trzymany na stercie dluzej niz jedna iteracje petli, wiec
     * niemal wszystko od razu staje sie smieciem (typowy przypadek dla
     * hipotezy generacyjnej).
     */
    private static long allocateGarbage(int count) {
        long checksum = 0;
        for (int i = 0; i < count; i++) {
            byte[] junk = new byte[32];
            checksum += junk.length + (i % 7);
        }
        return checksum;
    }

    private static void demonstrateGcNotifications() throws InterruptedException {
        System.out.println("\n=== NASLUCHIWANIE NA REALNE ZDARZENIA GC (JMX NotificationListener) ===");

        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        CountDownLatch notificationReceived = new CountDownLatch(1);

        NotificationListener listener = (Notification notification, Object handback) -> {
            if (GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION.equals(notification.getType())) {
                GarbageCollectionNotificationInfo info =
                        GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                System.out.println("   [Notification] kolektor=" + info.getGcName()
                        + ", przyczyna=" + info.getGcCause()
                        + ", czas=" + info.getGcInfo().getDuration() + " ms");
                notificationReceived.countDown();
            }
        };

        for (GarbageCollectorMXBean gcBean : gcBeans) {
            if (gcBean instanceof NotificationEmitter emitter) {
                emitter.addNotificationListener(listener, null, null);
            }
        }

        allocateGarbage(150_000);
        System.gc();

        boolean received = notificationReceived.await(3, TimeUnit.SECONDS);
        System.out.println(received
                ? "Powiadomienie o cyklu GC odebrane przez listener."
                : "Brak powiadomienia w limicie 3 sekund (zalezne od JVM/harmonogramu GC - to normalne).");

        for (GarbageCollectorMXBean gcBean : gcBeans) {
            if (gcBean instanceof NotificationEmitter emitter) {
                try {
                    emitter.removeNotificationListener(listener);
                } catch (ListenerNotFoundException ignored) {
                    // listener mogl juz nie byc zarejestrowany na tym konkretnym beanie - bezpiecznie ignorujemy
                }
            }
        }
    }
}
