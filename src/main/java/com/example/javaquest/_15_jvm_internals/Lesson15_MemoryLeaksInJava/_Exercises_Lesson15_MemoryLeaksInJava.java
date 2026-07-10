package com.example.javaquest._15_jvm_internals.Lesson15_MemoryLeaksInJava;

public class _Exercises_Lesson15_MemoryLeaksInJava {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyGcDoesNotPreventLeaks {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego wycieki
         * pamieci sa mozliwe w Javie mimo istnienia Garbage Collectora -
         * odwolaj sie do pojecia "osiagalnosci" (reachability) obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListFourLeakPatternsFromLesson {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wypisz w komentarzu 4 wzorce wyciekow pamieci
         * omowione w lekcji, kazdy z jednozdaniowym opisem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateGrowingStaticListAndMeasureSize {
        /*
         * 🧪 Zadanie 3:
         * Stworz statyczne pole `static List<byte[]> cache`, dodaj do
         * niego 10 000 tablic po 128 bajtow w petli (bez usuwania) - wypisz
         * rozmiar listy po zakonczeniu petli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MeasureHeapBeforeAndAfterGrowingCollection {
        /*
         * 🧪 Zadanie 4:
         * Uzywajac MemoryMXBean, wypisz zuzycie sterty PRZED i PO petli z
         * Zadania 3 - wypisz roznice w KB.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainObserverPatternLeakRisk {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego wzorzec
         * obserwator (Subject/Observer) jest szczegolnie podatny na
         * wycieki, jesli obserwatorzy nigdy nie sa wyrejestrowywani.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BuildSimpleSubjectObserverPair {
        /*
         * 🧪 Zadanie 6:
         * Napisz minimalna klase Subject (lista obserwatorow,
         * addListener/removeListener/fireEvent) oraz interfejs Observer z
         * metoda onEvent(String) - zarejestruj 2 obserwatorow i wywolaj
         * fireEvent na obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainThreadLocalPurpose {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij (min. 3 zdania), do czego sluzy
         * ThreadLocal<T> i dlaczego kazdy watek widzi WLASNA, oddzielna
         * wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CreateThreadLocalAndSetGetInSingleThread {
        /*
         * 🧪 Zadanie 8:
         * Stworz ThreadLocal<String>, ustaw wartosc "test", odczytaj ja i
         * wypisz - wszystko w watku glownym (bez puli watkow na razie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainImplicitOuterReferenceInInnerClass {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego instancja
         * nie-statycznej klasy wewnetrznej niejawnie trzyma referencje do
         * instancji klasy zewnetrznej, i jak to moze prowadzic do wycieku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareStaticAndNonStaticInnerClassDeclaration {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: napisz obok siebie deklaracje nie-statycznej
         * klasy wewnetrznej i statycznej klasy zagniezdzonej (static
         * nested class) o tej samej nazwie pol - opisz jednym zdaniem
         * kluczowa roznice majaca znaczenie dla wyciekow pamieci.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseWeakReferenceToDetectReachability {
        /*
         * 🧪 Zadanie 11:
         * Stworz obiekt, opakuj go w WeakReference, usun jedyna silna
         * referencje, wywolaj System.gc() i sprawdz weakReference.get() -
         * wypisz, czy obiekt zostal zebrany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DemonstrateObserverLeakWithWeakReference {
        /*
         * 🧪 Zadanie 12:
         * Uzywajac klas Subject/Observer z Zadania 6, zarejestruj
         * obserwatora w Subject, opakuj go w WeakReference, usun wlasna
         * referencje i wywolaj System.gc() - pokaz, ze WeakReference wciaz
         * zwraca obiekt (bo Subject go trzyma).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FixObserverLeakWithRemoveListener {
        /*
         * 🧪 Zadanie 13:
         * Powtorz Zadanie 12, ale tym razem PRZED usunieciem wlasnej
         * referencji wywolaj subject.removeListener(observer) - pokaz, ze
         * po System.gc() WeakReference zwraca null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CreateFixedThreadPoolAndSubmitTwoTasks {
        /*
         * 🧪 Zadanie 14:
         * Stworz Executors.newFixedThreadPool(1) (JEDEN watek, zeby
         * zagwarantowac reuzycie), wyslij DWA zadania SEKWENCYJNIE
         * (drugie po zakonczeniu pierwszego - uzyj np. Future.get()),
         * kazde ustawiajace inna wartosc ThreadLocal BEZ .remove() - w
         * drugim zadaniu odczytaj wartosc ThreadLocal NA POCZATKU i
         * wypisz, czy widoczna jest wartosc z pierwszego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FixThreadLocalLeakWithRemove {
        /*
         * 🧪 Zadanie 15:
         * Powtorz Zadanie 14, ale kazde zadanie konczy sie wywolaniem
         * threadLocal.remove() - pokaz, ze drugie zadanie widzi teraz
         * "brak wartosci" zamiast wartosci z poprzedniego zadania. Pamietaj
         * o shutdown()+awaitTermination na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildLargeOuterComponentWithInnerListener {
        /*
         * 🧪 Zadanie 16:
         * Napisz klase LargeComponent z duzym polem (np. byte[65536]) oraz
         * NIE-STATYCZNA klase wewnetrzna Listener - stworz instancje
         * LargeComponent, pobierz z niej Listener, dodaj Listener do
         * dlugozyjacej listy statycznej, opakuj LargeComponent w
         * WeakReference.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DemonstrateInnerClassLeakWithWeakReference {
        /*
         * 🧪 Zadanie 17:
         * Kontynuujac Zadanie 16: usun bezposrednia referencje do
         * LargeComponent, wywolaj System.gc() i sprawdz weakReference.get() -
         * pokaz, ze obiekt WCIAZ zyje (trzymany niejawnie przez Listener w
         * dlugozyjacej liscie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FixInnerClassLeakByClearingCollection {
        /*
         * 🧪 Zadanie 18:
         * Kontynuujac Zadanie 17: wyczysc dlugozyjaca liste (usun
         * Listener), wywolaj System.gc() ponownie i pokaz, ze teraz
         * weakReference.get() zwraca null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RefactorInnerClassToStaticNestedClass {
        /*
         * 🧪 Zadanie 19:
         * Przepisz Listener z Zadania 16 jako STATYCZNA klase zagniezdzona
         * (z jawnym polem-referencja do LargeComponent TYLKO jesli
         * faktycznie potrzebne) - pokaz, ze teraz mozna trzymac Listener w
         * dlugozyjacej liscie BEZ niejawnego trzymania calego
         * LargeComponent (chyba ze jawnie tego chcemy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SummarizeAllFourFixesInOwnWords {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: dla kazdego z 4 wzorcow wyciekow napisz JEDNA
         * konkretna "poprawke" (fix) w formie krotkiego zdania - lacznie
         * min. 4 zdania.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildBoundedCacheWithEvictionPolicy {
        /*
         * 🧪 Zadanie 21:
         * Napisz klase BoundedCache<K, V> oparta na LinkedHashMap z
         * przeslonieta metoda removeEldestEntry(...), ktora automatycznie
         * usuwa najstarszy wpis po przekroczeniu limitu (np. 100 wpisow) -
         * przetestuj dodajac 500 wpisow i sprawdzajac, ze rozmiar nigdy nie
         * przekracza limitu (w odroznieniu od Wzorca 1 z teorii).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureMemoryDifferenceBoundedVsUnboundedCache {
        /*
         * 🧪 Zadanie 22:
         * Porownaj (MemoryMXBean) zuzycie pamieci po dodaniu 100 000
         * wpisow do NIEOGRANICZONEJ mapy vs do BoundedCache z Zadania 21 -
         * wypisz oba wyniki i skomentuj roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildAutoCleaningObserverRegistry {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj wersje Subject uzywajaca WEWNETRZNIE listy
         * WeakReference<Observer> zamiast silnych referencji - pokaz, ze
         * teraz obserwator MOZE zostac zebrany przez GC nawet bez
         * jawnego removeListener() (uwaga: to realny wzorzec z JDK -
         * np. baza dla mechanizmow podobnych do WeakHashMap/listenery
         * Swing).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulateRealisticRequestContextWithThreadLocal {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj klase RequestContext (np. z polem userId, sessionData
         * jako duzy byte[]) trzymana w ThreadLocal<RequestContext> w puli
         * 3 watkow obslugujacej 10 "zadan" - bez .remove() pokaz na
         * konkretnym przykladzie (np. bledny userId widoczny w zlym
         * zadaniu), jak niebezpieczny w praktyce jest ten przeciek (nie
         * tylko pamieciowo, ale i LOGICZNIE - zle dane trafiajace do
         * zlego "requesta").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildThreadLocalLeakDetectorUtility {
        /*
         * 🧪 Zadanie 25:
         * Napisz metode pomocnicza `static <T> void runInPoolAndVerifyClean
         * (ExecutorService pool, ThreadLocal<T> local, Supplier<T> valueFactory,
         * int taskCount)`, ktora wykonuje zadania i AUTOMATYCZNIE sprawdza
         * (rzuca wyjatek/wypisuje ostrzezenie), jesli jakies zadanie
         * zobaczylo NIE-null wartosc ThreadLocal na starcie (czyli wykryla
         * przeciek) - przetestuj z i bez .remove().
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AnalyzeMemoryProfilerStyleSnapshot {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj prosty "snapshot" stanu pamieci: metoda zwracajaca
         * rekord (uzytaPamiecMB, liczbaWatkow, liczbaZaladowanychKlas) na
         * podstawie ManagementFactory (MemoryMXBean, ThreadMXBean,
         * ClassLoadingMXBean) - wypisz snapshot PRZED i PO kazdym z 4
         * wzorcow wyciekow z teorii tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildInnerClassLeakWithCollectionOfListeners {
        /*
         * 🧪 Zadanie 27:
         * Rozszerz demo z Wzorca 4: stworz 100 instancji LargeComponent (po
         * 64 KB kazdy), z kazdego pobierz Listener i dodaj do JEDNEJ,
         * dlugozyjacej listy - zmierz calkowity "wyciekly" rozmiar
         * (MemoryMXBean przed/po) i porownaj z oczekiwanym teoretycznie
         * (100 x 64 KB).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareAllFourLeaksInSingleReport {
        /*
         * 🧪 Zadanie 28:
         * Uruchom WSZYSTKIE 4 wzorce wyciekow z tej lekcji (lub ich kopie)
         * jeden po drugim w tym samym procesie, zbierajac dla kazdego:
         * przyrost sterty PRZED naprawa i PO naprawie - wypisz zbiorcza
         * tabele porownawcza wszystkich 4 wzorcow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_WriteProductionChecklistForAvoidingLeaks {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz checkliste code-review (min. 6 punktow)
         * do wychwytywania tych 4 wzorcow wyciekow PRZED wdrozeniem kodu
         * na produkcje (np. "czy kazde addListener ma odpowiadajace
         * removeListener?").
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneLeakDetectionHarness {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zbuduj narzedzie LeakDetectionHarness,
         * ktore przyjmuje liste "scenariuszy" (kazdy jako para Runnable
         * "buduj-wyciek" + Runnable "napraw"), dla kazdego scenariusza:
         * mierzy stan pamieci PRZED, PO "wycieku" i PO "naprawie"
         * (System.gc() przed kazdym pomiarem), a na koniec drukuje pelny
         * raport tabelaryczny pokazujacy, ze kazda "naprawa" faktycznie
         * zmniejszyla zuzycie pamieci z powrotem w okolice stanu
         * poczatkowego. Uzyj w nim WSZYSTKICH 4 wzorcow z teorii tej lekcji.
         */
        public static void main(String[] args) { }
    }
}
