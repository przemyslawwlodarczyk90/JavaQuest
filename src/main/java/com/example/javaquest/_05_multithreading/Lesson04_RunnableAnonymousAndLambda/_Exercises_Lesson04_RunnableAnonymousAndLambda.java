package com.example.javaquest._05_multithreading.Lesson04_RunnableAnonymousAndLambda;

public class _Exercises_Lesson04_RunnableAnonymousAndLambda {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LambdaHelloWorld {
        /*
         * 🧪 Zadanie 1:
         * Utwórz Runnable jako lambdę wypisującą "Czesc ze swiata lambda!" wraz z
         * nazwą wątku (Thread.currentThread().getName()). Uruchom w wątku
         * (start()+join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AnonymousClassHelloWorld {
        /*
         * 🧪 Zadanie 2:
         * To samo zadanie co w Zadaniu 1, ale zapisane jako ANONIMOWA klasa
         * Runnable (new Runnable() { ... }). Porównaj (w komentarzu) ilość kodu
         * potrzebnego do zapisania tego samego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CaptureEffectivelyFinalVariable {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj lokalną zmienną String imie = "Zosia" (effectively final),
         * użyj jej wewnątrz lambdy Runnable wypisującej powitanie. Uruchom w
         * wątku (start()+join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ThreeWaysSameTask {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj TO SAMO zadanie (wypisanie sumy liczb 4 i 7) trzema
         * sposobami: osobna klasa implements Runnable, anonimowa klasa Runnable,
         * lambda. Uruchom wszystkie trzy (osobne wątki, start()+join()
         * sekwencyjnie) i porównaj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LambdaInlineDirectlyInThreadConstructor {
        /*
         * 🧪 Zadanie 5:
         * Utwórz Thread bezpośrednio z lambdą "w locie" (bez zmiennej pośredniej
         * typu Runnable), np. new Thread(() -> ...). Uruchom (start()) i poczekaj
         * (join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AnonymousClassWithOwnField {
        /*
         * 🧪 Zadanie 6:
         * Stwórz anonimową klasę Runnable z własnym polem int wywolania = 0,
         * inkrementowanym przy każdym run(). Uruchom TĘ SAMĄ instancję 3 razy
         * sekwencyjnie (start()+join()), wypisując licznik po każdym uruchomieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MultipleLambdasInArray {
        /*
         * 🧪 Zadanie 7:
         * Umieść 4 różne lambdy Runnable w tablicy Runnable[4] (każda wypisuje
         * inny komunikat). Iteruj po tablicy, uruchamiając każdą w osobnym
         * wątku (start()+join() sekwencyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CaptureMultipleVariables {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj 2 zmienne lokalne (String imie = "Kasia", int wiek = 28) i
         * użyj obu w treści lambdy Runnable formatującej zdanie o osobie. Uruchom
         * w wątku (start()+join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_LambdaVsAnonymousThisReference {
        /*
         * 🧪 Zadanie 9:
         * W anonimowej klasie Runnable z polem String etykieta = "ANON" wypisz
         * this.etykieta (odwołanie przez this do pola klasy anonimowej). Skomentuj
         * (println/komentarz), że w lambdzie this odnosiłoby się do klasy
         * zewnętrznej, bo lambda nie ma własnego this.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LambdaWithLoopVariableCaptureIssue {
        /*
         * 🧪 Zadanie 10:
         * W pętli for (int i = 0; i < 3; i++) utwórz przez utworzenie nowej,
         * lokalnej zmiennej (final int index = i) 3 lambdy Runnable wypisujące
         * swój numer. Uruchamiaj je sekwencyjnie (start()+join() w tej samej
         * pętli) i zademonstruj, że każda wypisuje inny, poprawny numer.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CounterAnonymousClassSharedAcrossThreads {
        /*
         * 🧪 Zadanie 11:
         * Stwórz anonimową klasę Runnable z polem int licznik. Uruchamiaj TĘ SAMĄ
         * instancję sekwencyjnie w 5 różnych wątkach (start()+join() po każdym).
         * Wypisz wartość licznika po każdym uruchomieniu (1..5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LambdaFactoryMethod {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę statyczną createCounter(String label) zwracającą lambdę
         * zamykającą się nad lokalną tablicą int[1] jako "mutowalnym licznikiem"
         * (bo lambda nie może zmieniać zwykłej zmiennej lokalnej, ale może
         * modyfikować zawartość tablicy). Uruchom zwrócone zadanie 3 razy
         * sekwencyjnie, obserwując rosnący licznik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AnonymousClassMultipleMethods {
        /*
         * 🧪 Zadanie 13:
         * Stwórz anonimową klasę implementującą Runnable z DODATKOWĄ metodą
         * pomocniczą (np. private String opisz()) wywoływaną wewnątrz run().
         * Skomentuj (println/komentarz), że czysta lambda nie mogłaby mieć takiej
         * dodatkowej metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareAllThreeForStatefulTask {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj zadanie zliczające wywołania (jak w Zadaniu 6) na trzy
         * sposoby: (a) osobna klasa z polem, (b) anonimowa klasa z polem,
         * (c) lambda z zewnętrznym, mutowalnym "schowkiem" (tablica int[1]).
         * Uruchom każde podejście 3 razy sekwencyjnie i porównaj wyniki liczników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LambdaCollectionOfTasksWithLabels {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj List<Runnable> z 5 lambdami, każda przechwytująca inny String
         * label z listy etykiet ({"Alfa","Beta","Gamma","Delta","Epsilon"}).
         * Uruchom wszystkie sekwencyjnie (start()+join()), wypisując etykietę
         * i nazwę wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AnonymousClassAsComparatorAndRunnable {
        /*
         * 🧪 Zadanie 16:
         * Nawiązując do Lesson03 (PriorytetowaneZadanie), stwórz anonimową klasę
         * implementującą JEDNOCZEŚNIE Runnable i Comparable<T> (zamiast osobnej
         * klasy). Utwórz 2 takie zadania z różnymi priorytetami i porównaj je
         * przez compareTo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LambdaThrowingCheckedExceptionWorkaround {
        /*
         * 🧪 Zadanie 17:
         * Runnable.run() nie może rzucać checked exception. Zaimplementuj lambdę,
         * która wewnątrz obsługuje (try-catch) wywołanie Thread.sleep(100),
         * przekształcając złapany InterruptedException na wywołanie
         * Thread.currentThread().interrupt() w catch.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NestedLambdaCreatingThread {
        /*
         * 🧪 Zadanie 18:
         * W treści jednej lambdy Runnable, po wypisaniu komunikatu, utwórz i
         * uruchom (start()+join()) DRUGI wątek z KOLEJNĄ lambdą (zagnieżdżenie).
         * Wypisz nazwy obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AnonymousClassWithConstructorLikeInit {
        /*
         * 🧪 Zadanie 19:
         * Ponieważ anonimowa klasa nie może mieć jawnego konstruktora, zainicjalizuj
         * jej pole (np. long czasUtworzenia) przez blok inicjalizacyjny instancji
         * ({ ... } wewnątrz ciała anonimowej klasy) ustawiający System.currentTimeMillis().
         * W run() wypisz różnicę czasu między utworzeniem a uruchomieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_LambdaVsAnonymousBytecodePerfNote {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas utworzenia 100 000 lambd Runnable oraz czas utworzenia
         * 100 000 anonimowych klas Runnable (bez uruchamiania wątków). Porównaj
         * oba czasy i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_StatefulAnonymousTaskPool {
        /*
         * 🧪 Zadanie 21:
         * Stwórz anonimową klasę Runnable z polem List<String> historia
         * (zbierającą komunikat z każdego wywołania). Uruchamiaj TĘ SAMĄ instancję
         * sekwencyjnie w 6 wątkach o różnych nazwach. Na końcu wypisz całą
         * zebraną historię.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LambdaBasedTaskRegistry {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj Map<String, Runnable> "rejestr zadań" (4 wpisy: klucz = nazwa
         * zadania, wartość = lambda). Napisz metodę runTask(Map<String,Runnable>
         * registry, String name) uruchamiającą wybrane zadanie w nowym wątku
         * (start()+join()). Przetestuj dla wszystkich kluczy oraz dla
         * nieistniejącego klucza (obsłuż brak wpisu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MixedRunnableSourcesPipeline {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj listę 6 zadań Runnable z MIESZANYCH źródeł (2 lambdy, 2 anonimowe
         * klasy, 2 instancje osobnych klas z Lesson03) operujących na wspólnym,
         * zsynchronizowanym StringBuilderze. Uruchom je sekwencyjnie (start()+
         * join()), budując końcowy raport tekstowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AnonymousClassSelfReferenceRecursion {
        /*
         * 🧪 Zadanie 24:
         * Stwórz tablicę Runnable[1] jako "self-holder", potrzebną by anonimowa
         * klasa Runnable mogła w run() odwołać się do samej siebie (przez tę
         * tablicę) i uruchomić samą siebie ponownie w nowym wątku maksymalnie
         * 3 razy (licznik rekurencji jako pole klasy anonimowej – twardy limit
         * gwarantujący zakończenie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LambdaClosureOverMutableWrapper {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj "bufor wyników" jako tablicę long[1] przechwytywaną przez
         * 5 lambd Runnable, z których każda DODAJE do niej swoją wartość (dostęp
         * zsynchronizowany blokiem synchronized na tablicy). Uruchom wszystkie
         * 5 równolegle (start wszystkich, potem join wszystkich), wypisz finalną
         * sumę i porównaj z oczekiwaną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AnonymousClassStateMachineTask {
        /*
         * 🧪 Zadanie 26:
         * Analogicznie do automatu stanów z Lesson03 (enum Stan), zaimplementuj
         * to jako ANONIMOWĄ klasę Runnable (zamiast osobnej klasy). Uruchom
         * (start()+join()) i wypisz przejścia między stanami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LambdaGeneratedTasksFromData {
        /*
         * 🧪 Zadanie 27:
         * Na podstawie tablicy int[] {3, 1, 4, 1, 5, 9, 2, 6} wygeneruj DYNAMICZNIE
         * (w pętli for-each) listę lambd Runnable, po jednej na element, każda
         * wypisująca czy liczba jest parzysta czy nieparzysta. Uruchamiaj każdą
         * sekwencyjnie (start()+join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AnonymousClassVsLambdaMemoryFootprintDemo {
        /*
         * 🧪 Zadanie 28:
         * Utwórz i zapisz do listy 1000 obiektów: raz jako lambdy Runnable (w
         * List<Runnable>), raz jako anonimowe klasy Runnable z własnym polem
         * (w osobnej liście). Zmierz czas tworzenia obu list i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ChainedLambdaTasksWithSharedContext {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj prosty "kontekst" (StringBuilder log) przekazywany do 4 lambd
         * Runnable tworzonych w pętli, gdzie każda kolejna lambda dopisuje do
         * wspólnego kontekstu swój krok. Uruchamiaj je sekwencyjnie (start()+
         * join() po każdej, bo kolejność ma znaczenie) i wypisz finalny log.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullThreeStylesTaskFramework {
        /*
         * 🧪 Zadanie 30:
         * Zdefiniuj interfejs NamedTask extends Runnable z dodatkową metodą
         * String getName(). Zaimplementuj go raz jako osobna klasa i raz jako
         * anonimowa klasa. Wyjaśnij w komentarzu, dlaczego czysta lambda NIE
         * MOŻE zaimplementować interfejsu z dwiema metodami abstrakcyjnymi
         * (nie jest funkcyjny), i zamiast tego dodaj trzeci wariant jako zwykła
         * lambda Runnable + osobna mapa nazw. Uruchom wszystkie warianty
         * sekwencyjnie i wypisz zbiorczy raport z nazwami i wynikami.
         */
        public static void main(String[] args) { }
    }
}
