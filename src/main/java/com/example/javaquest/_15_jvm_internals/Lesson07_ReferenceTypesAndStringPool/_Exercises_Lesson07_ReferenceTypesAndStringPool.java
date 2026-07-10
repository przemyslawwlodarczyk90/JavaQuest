package com.example.javaquest._15_jvm_internals.Lesson07_ReferenceTypesAndStringPool;

public class _Exercises_Lesson07_ReferenceTypesAndStringPool {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainThreeReferenceTypes {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania) roznice miedzy WeakReference, SoftReference i
         * PhantomReference - kiedy kazda z nich jest czyszczona przez GC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateWeakReferenceAndCheckGet {
        /*
         * 🧪 Zadanie 2:
         * Stworz obiekt Object, opakuj go w WeakReference, wypisz wynik
         * get() PRZED i PO ustawieniu silnej referencji na null oraz
         * wywolaniu System.gc() (z krotkim Thread.sleep(100)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareEqualsAndDoubleEquals {
        /*
         * 🧪 Zadanie 3:
         * Stworz String literal "kot" oraz new String("kot") - wypisz wynik
         * porownania == oraz .equals() dla obu par i skomentuj roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InternHeapStringAndCompare {
        /*
         * 🧪 Zadanie 4:
         * Stworz new String("pies"), wywolaj na nim .intern(), porownaj
         * wynik operatorem == z literalem "pies" - wypisz rezultat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateSoftReferenceAndExplain {
        /*
         * 🧪 Zadanie 5:
         * Stworz SoftReference<byte[]> na tablicy 1024 bajtow, wypisz czy
         * get() zwraca niepusta wartosc - w komentarzu wyjasnij, KIEDY
         * (pod jakim warunkiem) GC moglby ja wyczyscic.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RegisterCleanerAction {
        /*
         * 🧪 Zadanie 6:
         * Stworz Cleaner (Cleaner.create()) i zarejestruj prosty obiekt z
         * akcja sprzatajaca, ktora wypisuje "Sprzatanie wykonane!" - NIE
         * wywoluj clean() recznie, tylko obserwuj (opcjonalnie) po
         * System.gc().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyPhantomGetAlwaysNull {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, dlaczego PhantomReference.get() ZAWSZE
         * zwraca null i jaki jest wobec tego JEDYNY sposob "dowiedzenia
         * sie" czegokolwiek przez PhantomReference (podpowiedz: ReferenceQueue).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainStringPoolConcept {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: opisz (min. 3 zdania), czym jest String pool i
         * dlaczego dwa identyczne literaly tekstowe w roznych miejscach
         * kodu to w rzeczywistosci JEDEN obiekt w pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareFinalizeVsCleaner {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego Object.finalize() jest
         * przestarzale (deprecated) od Javy 9 i czym Cleaner jest od niego
         * lepszy (min. 2 konkretne powody).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConcatenateStringsAndCheckPoolMembership {
        /*
         * 🧪 Zadanie 10:
         * Stworz String przez konkatenacje DWOCH literalow w czasie
         * kompilacji (np. "java" + "quest" jako stale) i porownaj == z
         * literalem "javaquest" - w komentarzu wyjasnij wynik (podpowiedz:
         * stale laczone w compile-time trafiaja do puli tak samo jak literal).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildWeakReferenceCleanupDetector {
        /*
         * 🧪 Zadanie 11:
         * Stworz WeakReference z ReferenceQueue, usun silna referencje,
         * wywolaj System.gc(), a nastepnie w ograniczonej petli (max 20
         * iteracji, Thread.sleep(50) miedzy nimi) sprawdzaj queue.poll() -
         * wypisz, czy referencja pojawila sie w kolejce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConcatenateStringsAtRuntimeAndCompare {
        /*
         * 🧪 Zadanie 12:
         * Stworz String przez konkatenacje w RUNTIME (np. przy uzyciu
         * zmiennej `String part = "java"; String result = part + "quest";`) -
         * porownaj == z literalem "javaquest" i wyjasnij, dlaczego wynik
         * jest INNY niz w Zadaniu 10 (konkatenacja compile-time vs runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_BuildSimpleWeakValueCache {
        /*
         * 🧪 Zadanie 13:
         * Napisz prosta mape java.util.Map<String, WeakReference<byte[]>>
         * jako "cache" - dodaj 3 wpisy, wyzeruj silne referencje do
         * wartosci, wywolaj System.gc() i po krotkim oczekiwaniu sprawdz,
         * ktore wpisy maja juz get() == null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareInternPerformanceConceptually {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: opisz scenariusz, w ktorym NADUZYWANIE .intern()
         * (np. wywolanego na milionach roznych, dynamicznie generowanych
         * Stringow) moze byc SZKODLIWE dla pamieci - wyjasnij dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementResourceWithCleanerPattern {
        /*
         * 🧪 Zadanie 15:
         * Napisz klase ManagedResource, ktora w konstruktorze rejestruje
         * siebie w Cleaner z akcja wypisujaca "Zasob X zamkniety" (uzyj
         * STATYCZNEJ klasy wewnetrznej implementujacej Runnable, ktora NIE
         * trzyma referencji do ManagedResource - tylko do potrzebnych danych,
         *  np. nazwy) - przetestuj tworzac i porzucajac instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureTimeToGcNotification {
        /*
         * 🧪 Zadanie 16:
         * Zmierz (System.nanoTime()) czas miedzy wywolaniem System.gc() a
         * pojawieniem sie wyczyszczonej WeakReference w ReferenceQueue -
         * wypisz zmierzony czas w milisekundach (z limitem oczekiwania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DemonstrateInternReducesDuplicates {
        /*
         * 🧪 Zadanie 17:
         * Stworz 5 obiektow `new String("duplikat")`, zintern'uj kazdy z
         * nich i sprawdz operatorem ==, ze WSZYSTKIE zinternowane wersje
         * wskazuja na TEN SAM obiekt w pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainWeakHashMapUseCase {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz (min. 4 zdania) realny scenariusz z
         * praktyki, w ktorym java.util.WeakHashMap (zbudowana na
         * WeakReference) jest lepszym wyborem niz zwykla HashMap.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildPhantomReferenceQueueProcessor {
        /*
         * 🧪 Zadanie 19:
         * Stworz WLASNA ReferenceQueue<Object> (bez Cleaner) i 3
         * PhantomReference wskazujace na 3 rozne obiekty - usun silne
         * referencje, wywolaj System.gc(), i w ograniczonej petli
         * przetwarzaj kolejke, liczac ile referencji zostalo wyczyszczonych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareCompactStringsMemoryConceptually {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego String zlozony
         * WYLACZNIE z liter polskiego alfabetu bez znakow diakrytycznych
         * (np. "ala ma kota") zajmuje mniej pamieci niz String zawierajacy
         * chocby JEDEN znak spoza Latin-1 (np. emoji) - odwolaj sie do
         * Compact Strings (Java 9+).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildSimpleWeakReferenceBasedCache {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj klase SimpleWeakCache<K, V>, przechowujaca wewnetrznie
         * Map<K, WeakReference<V>> - z metodami put(K, V), get(K) (zwraca
         * null, jesli wartosc zostala juz wyczyszczona przez GC) oraz
         * cleanupCleared() usuwajaca wpisy z wyczyszczonymi referencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureStringPoolSizeEffectOnEquality {
        /*
         * 🧪 Zadanie 22:
         * Wygeneruj petla 1000 Stringow przez konkatenacje runtime (rozne
         * wartosci), zintern'uj polowe z nich - policz i wypisz, ile par
         * (i,j) z wygenerowanej listy spelnia warunek == po internowaniu, a
         * ile bez.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementResourceTrackerWithPhantomReferences {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj klase ResourceTracker, ktora dla kazdego
         * zarejestrowanego "zasobu" (dowolny Object) tworzy PhantomReference
         * z ReferenceQueue, a metoda `int drainClearedCount()` zwraca liczbe
         * zasobow wyczyszczonych od ostatniego wywolania - przetestuj
         * rejestrujac 5 zasobow i usuwajac silne referencje do 3 z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareReferenceTypesInSingleTable {
        /*
         * 🧪 Zadanie 24:
         * Napisz kod, ktory tworzy PO JEDNYM obiekcie kazdego typu
         * referencji (Strong - zwykla zmienna, Weak, Soft, Phantom) i
         * wypisuje w formie tabeli (System.out.printf z wyrownaniem kolumn)
         * ich nazwe oraz to, czy get() zwraca wartosc (dla Phantom - zawsze
         * false/null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildNativeResourceSimulationWithCleaner {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj "natywny uchwyt" (np. sztuczny int jako "file descriptor")
         * zamkniety w klasie NativeHandle, ktora w konstruktorze rejestruje
         * Cleaner z akcja "zwalniajaca" (wypisujaca komunikat z numerem
         * uchwytu) - przetestuj tworzac 3 uchwyty, jawnie wywolujac clean()
         * na jednym i pozostawiajac 2 dla GC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainMemoryLeakDespiteGcInJava {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: opisz (min. 5 zdan) jak w Javie MIMO Garbage
         * Collectora moze dojsc do "wycieku pamieci" (memory leak) - podaj
         * konkretny przyklad (np. statyczna kolekcja rosnaca bez limitu,
         * niezamkniete listenery) i wyjasnij, jak WeakReference moze
         * pomoc w takim przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildInterningBenchmarkComparison {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj 100_000 unikalnych Stringow przez konkatenacje runtime,
         * zmierz czas (System.nanoTime()) budowania listy BEZ .intern() a
         * potem CZAS wywolania .intern() na kazdym z nich - wypisz oba
         * pomiary i skomentuj kompromis (pamiec vs czas CPU).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignCacheChoosingBetweenWeakAndSoft {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zaprojektuj (opisz w komentarzu) cache dla wynikow
         * drogich obliczen w aplikacji - uzasadnij, czy lepszym wyborem
         * bylby SoftReference czy WeakReference dla przechowywanych
         * wartosci, biorac pod uwage kompromis miedzy trzymaniem danych
         * "jak najdluzej" a ryzykiem OutOfMemoryError.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SimulateReferenceProcessingPipeline {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj "potok" przetwarzania: 10 obiektow rejestrowanych z
         * PhantomReference w jednej wspolnej ReferenceQueue, watek
         * (lub petla w main) drenujacy kolejke i logujacy KTORY z 10
         * zasobow zostal wyczyszczony (uzyj mapy Reference -> identyfikator),
         * z ograniczonym czasowo oczekiwaniem na wszystkie 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullReferenceTypesAndStringPoolReport {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: napisz kompletny raport demonstracyjny
         * laczacy WSZYSTKIE elementy lekcji - WeakReference z detekcja
         * czyszczenia, PhantomReference+Cleaner z komunikatem sprzatania,
         * oraz sekcje String pool (==, .equals(), .intern()) z co najmniej
         * 4 roznymi przypadkami porownan - wypisz jako jeden spojny raport
         * tekstowy z naglowkami sekcji.
         */
        public static void main(String[] args) { }
    }
}
