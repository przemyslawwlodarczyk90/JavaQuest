package com.example.javaquest._04_io.Lesson13_TryWithResources;

public class _Exercises_Lesson13_TryWithResources {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CustomResource {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę MyLamp implementującą AutoCloseable, w konstruktorze
         * wypisz "Wlaczam lampe", w close() "Wylaczam lampe", dodaj metodę
         * swieci() wypisującą "Lampa swieci". Użyj jej w try-with-resources.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TwoResourcesOrder {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj w jednym try-with-resources dwa własne zasoby: "Pierwszy"
         * i "Drugi" (klasa jak w teorii). Sprawdź i wypisz w jakiej kolejności
         * zostaną zamknięte (powinno być odwrotnie niż deklaracja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExceptionInBody {
        /*
         * 🧪 Zadanie 3:
         * W try-with-resources z jednym własnym zasobem rzuć w bloku try
         * RuntimeException("Blad"). Złap go catch-em i sprawdź, że mimo
         * wyjątku close() zasobu i tak się wykonał (wypisz komunikat z close()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TryVsFinally {
        /*
         * 🧪 Zadanie 4:
         * Napisz dwie wersje tego samego kodu otwierającego własny zasób:
         * jedną z try-with-resources, drugą z ręcznym finally (zmienna=null,
         * try/finally z if(zmienna!=null)). Porównaj liczbę linijek kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CloseThrowsChecked {
        /*
         * 🧪 Zadanie 5:
         * Stwórz zasób, którego close() rzuca IOException (checked).
         * Użyj go w try-with-resources w metodzie z "throws Exception"
         * i sprawdź, że kod się kompiluje i działa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BufferedWriterReader {
        /*
         * 🧪 Zadanie 6:
         * Utwórz plik tymczasowy Files.createTempFile("cw6", ".txt").
         * Zapisz do niego 3 linie tekstu przez BufferedWriter w try-with-resources,
         * następnie odczytaj je przez BufferedReader w osobnym try-with-resources
         * i wypisz. Usuń plik na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NestedFileResources {
        /*
         * 🧪 Zadanie 7:
         * Utwórz dwa pliki tymczasowe: source.txt (z zawartością "Ala ma kota")
         * i target.txt (pusty). W jednym try-with-resources otwórz
         * BufferedReader dla source i BufferedWriter dla target, przepisz
         * linie z jednego do drugiego. Usuń oba pliki na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_EffectivelyFinalResource {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj zmienną finalną (lub effectively final) MyResource poza
         * try, a następnie użyj jej w try (zmienna) { ... } (Java 9+, bez
         * ponownej deklaracji typu). Sprawdź czy close() nadal się wywołuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CloseableVsAutoCloseable {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj klasę implementującą java.io.Closeable (close() throws
         * IOException) zamiast AutoCloseable. Użyj jej w try-with-resources
         * i porównaj deklarację typu wyjątku z Zadania 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MultiCatchWithResource {
        /*
         * 🧪 Zadanie 10:
         * W try-with-resources z własnym zasobem, w bloku try losowo (na sztywno
         * ustaw warunek boolean) rzuć albo IllegalStateException, albo
         * IllegalArgumentException. Złap oba w jednym catch (A | B e).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SuppressedBasics {
        /*
         * 🧪 Zadanie 11:
         * Stwórz zasób, którego close() rzuca RuntimeException("Blad zamkniecia").
         * W bloku try rzuć RuntimeException("Blad glowny"). Złap wyjątek główny
         * i wypisz go oraz wszystkie jego getSuppressed().
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleSuppressed {
        /*
         * 🧪 Zadanie 12:
         * Zadeklaruj trzy zasoby, z których DWA (drugi i trzeci) rzucają
         * wyjątek w close(). W bloku try rzuć własny wyjątek główny.
         * Wypisz główny wyjątek i sprawdź, że getSuppressed() zawiera
         * dokładnie dwa stłumione wyjątki, w kolejności zamykania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ResourceCounter {
        /*
         * 🧪 Zadanie 13:
         * Klasa CountingResource ze statycznym licznikiem openCount, który
         * rośnie w konstruktorze i maleje w close(). Otwórz 5 takich zasobów
         * w try-with-resources (nawet gdy w środku rzucisz wyjątek), sprawdź
         * po wyjściu z try że licznik wrócił do 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LoggingResource {
        /*
         * 🧪 Zadanie 14:
         * Stwórz zasób LogResource, który w close() dopisuje linię
         * "Zamknieto zasob X" do pliku log.txt (przez BufferedWriter).
         * Użyj trzech takich zasobów i sprawdź zawartość log.txt po zakończeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_OwnPlusFileResource {
        /*
         * 🧪 Zadanie 15:
         * W jednym try-with-resources połącz własny zasób (np. MyLamp)
         * z BufferedWriter do pliku tymczasowego. Zapisz jedną linię tekstu
         * i sprawdź kolejność komunikatów zamykania obu zasobów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompositeResource {
        /*
         * 🧪 Zadanie 16:
         * Stwórz klasę OuterResource implementującą AutoCloseable, która
         * w konstruktorze tworzy wewnątrz siebie InnerResource (też
         * AutoCloseable), a w swoim close() wywołuje inner.close().
         * Sprawdź kolejność komunikatów: otwarcie inner, otwarcie outer,
         * zamkniecie outer, zamkniecie inner.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MethodReturningFromTry {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę int countLines(Path file) używającą
         * try-with-resources z BufferedReader, zwracającą liczbę linii pliku.
         * Przetestuj na pliku tymczasowym z 5 liniami tekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TransactionResource {
        /*
         * 🧪 Zadanie 18:
         * Stwórz klasę TransactionResource z polem boolean committed=false
         * i metodą commit() ustawiającą je na true. W close() wypisz
         * "COMMIT" jeśli committed, w przeciwnym razie "ROLLBACK".
         * Przetestuj scenariusz z commit() i bez commit() (np. z wyjątkiem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ReturnValueWithSuppressed {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę int process(), która w try-with-resources z zasobem
         * rzucającym wyjątek w close() rzuca też wyjątek w bloku try (przed
         * return). Wywołaj ją w main, złap wyjątek główny, wypisz jego
         * getSuppressed() i pokaż że return nigdy się nie wykonał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorManualFinally {
        /*
         * 🧪 Zadanie 20:
         * Masz kod z trzema zasobami otwieranymi i zamykanymi ręcznie przez
         * zagnieżdżone try/finally (napisz go najpierw). Zrefaktoryzuj go
         * na jeden try-with-resources z trzema zasobami i porównaj kolejność
         * zamykania w obu wersjach.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ManualResourceManager {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj klasę ResourceManager z listą List<AutoCloseable>
         * i metodą register(AutoCloseable). Metoda closeAll() zamyka
         * wszystkie zarejestrowane zasoby w ODWROTNEJ kolejności rejestracji
         * (symulując zachowanie try-with-resources ręcznie). Przetestuj z 4
         * zasobami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FailingConstructor {
        /*
         * 🧪 Zadanie 22:
         * Zadeklaruj w try-with-resources cztery zasoby, gdzie konstruktor
         * TRZECIEGO rzuca wyjątek. Sprawdź (przez komunikaty w konsoli), że
         * dwa wcześniej otwarte zasoby zostały poprawnie zamknięte, mimo że
         * trzeci i czwarty nigdy się nie otworzyły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ExceptionChainPrinter {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę printFullExceptionChain(Throwable t), która rekurencyjnie
         * wypisuje wyjątek główny, jego przyczynę (getCause) oraz WSZYSTKIE
         * stłumione (getSuppressed(), też rekurencyjnie dla ich cause).
         * Przetestuj na scenariuszu z zasobem rzucającym w close() i przyczyną
         * opakowaną w nowy wyjątek w bloku try.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PipelineWithRollback {
        /*
         * 🧪 Zadanie 24:
         * Otwórz jednocześnie: BufferedReader (plik źródłowy z 3 liniami),
         * BufferedWriter (plik docelowy) i własny LoggerResource. Podczas
         * przepisywania linii symuluj błąd na 2 linii (rzuć wyjątek).
         * Po zakończeniu sprawdź zawartość pliku docelowego – ile linii
         * zdążyło się zapisać przed błędem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_GenericPooledResource {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj generyczną klasę PooledResource<T> implementującą
         * AutoCloseable, która w konstruktorze "pożycza" obiekt z prostej puli
         * (np. List<T> jako magazyn), a w close() zwraca go z powrotem do puli.
         * Przetestuj wypożyczenie i zwrot 3 obiektów w pętli, weryfikując
         * rozmiar puli przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ManyResourcesNoLeak {
        /*
         * 🧪 Zadanie 26:
         * Użyj klasy CountingResource z Zadania 13. W pętli 1000 razy otwórz
         * i zamknij taki zasób w try-with-resources (czasem z wymuszonym
         * wyjątkiem w środku, np. co 10-ty raz). Po pętli sprawdź, że licznik
         * otwartych zasobów wynosi dokładnie 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SuppressedReportToFile {
        /*
         * 🧪 Zadanie 27:
         * Zdefiniuj własny wyjątek ResourceOperationException. W scenariuszu
         * z zasobem rzucającym w close() i błędem w bloku try, po złapaniu
         * głównego wyjątku zapisz do pliku raport.txt: treść głównego
         * wyjątku oraz treść każdego stłumionego (użyj BufferedWriter).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransactionalFileWriter {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj TransactionalFileWriter implementujący AutoCloseable:
         * pisze dane do pliku tymczasowego .tmp, a w close() – jeśli metoda
         * markFailed() nie została wywołana – zmienia nazwę pliku na docelową
         * (commit), w przeciwnym razie usuwa plik .tmp (rollback). Przetestuj
         * oba scenariusze (sukces i wywołanie markFailed()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ManualTryWithResourcesSimulation {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę statyczną runWithResources(List<AutoCloseable> resources,
         * Runnable body), która ręcznie odtwarza semantykę try-with-resources:
         * wykonuje body, a w finally zamyka zasoby w odwrotnej kolejności,
         * zbierając wyjątki z close() jako suppressed do wyjątku z body
         * (lub rzucając pierwszy napotkany, jeśli body się powiodło).
         * Przetestuj na 3 zasobach, jeden z nich rzucający w close().
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FileCopyUtilityWithReport {
        /*
         * 🧪 Zadanie 30:
         * Mini-projekt: napisz narzędzie kopiujące linie z pliku źródłowego
         * (5 linii) do docelowego, używając własnych klas opakowujących
         * BufferedReader/BufferedWriter jako AutoCloseable z logowaniem.
         * Wymuś błąd w close() jednego z opakowań i wygeneruj na końcu
         * szczegółowy raport (do konsoli): czy kopiowanie się powiodło,
         * ile linii skopiowano, jakie wyjątki główne i stłumione wystąpiły.
         */
        public static void main(String[] args) { }
    }
}
