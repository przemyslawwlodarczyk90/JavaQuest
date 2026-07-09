package com.example.javaquest._14_advancedjava.Lesson25_DefensiveCopying;

public class _Exercises_Lesson25_DefensiveCopying {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FixBrokenPersonConstructorCopy {
        /*
         * 🧪 Zadanie 1:
         * Napraw klasę podobną do BrokenImmutablePerson z Lesson24 (pola
         * String name, java.util.List<String> hobbies) dodając defensywne
         * kopiowanie w konstruktorze przy pomocy `List.copyOf(hobbies)`.
         * Zademonstruj, że mutacja oryginalnej listy PO utworzeniu obiektu
         * już nie wpływa na jego stan wewnętrzny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FixedGetterThrowsOnMutationAttempt {
        /*
         * 🧪 Zadanie 2:
         * Używając naprawionej klasy z Zadania 1, zademonstruj, że próba
         * wywołania `getHobbies().add("cos")` rzuca
         * UnsupportedOperationException (bo List.copyOf zwraca niemodyfikowalną
         * listę) - złap wyjątek i wypisz jego typ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SafeInventoryArrayConstructorClone {
        /*
         * 🧪 Zadanie 3:
         * Napisz klasę SafeInventory z polem `private final int[] stock`,
         * kopiującą przekazaną tablicę w konstruktorze przez `stock.clone()`
         * (lub Arrays.copyOf). Zademonstruj, że mutacja oryginalnej tablicy
         * po konstrukcji NIE wpływa na stan wewnętrzny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SafeInventoryGetterClone {
        /*
         * 🧪 Zadanie 4:
         * Do klasy SafeInventory z Zadania 3 dodaj poprawny getter
         * `getStock()` zwracający KOPIĘ tablicy (nie żywą referencję).
         * Zademonstruj, że mutacja wyniku gettera nie zmienia stanu
         * wewnętrznego obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SetCopyOfAndMapCopyOf {
        /*
         * 🧪 Zadanie 5:
         * Użyj `Set.copyOf` oraz `Map.copyOf` do stworzenia niemodyfikowalnych
         * kopii istniejącego `HashSet<String>` i `HashMap<String, Integer>`.
         * Zademonstruj, że próba modyfikacji kopii rzuca wyjątek, a
         * modyfikacja oryginału po skopiowaniu nie wpływa na kopię.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListCopyOfNullChecks {
        /*
         * 🧪 Zadanie 6:
         * Zademonstruj, że `List.copyOf(null)` oraz `List.copyOf(java.util.Arrays.asList("a",
         * null, "b"))` rzucają NullPointerException - złap oba wyjątki
         * osobno i wypisz ich komunikaty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareUnmodifiableListAndCopyOf {
        /*
         * 🧪 Zadanie 7:
         * Stwórz jedną `ArrayList<String>`, opakuj ją OBIEMA metodami
         * (`Collections.unmodifiableList` i `List.copyOf`) do dwóch osobnych
         * zmiennych, następnie zmutuj ORYGINALNĄ listę (dodaj element) i
         * wypisz obie "niemodyfikowalne" wersje - zaobserwuj różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainViewVsCopyDifference {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: na podstawie obserwacji z Zadania 7, wyjaśnij w
         * komentarzu (min. 3 zdania) różnicę między "widokiem tylko do
         * odczytu" (view) a "prawdziwą niemodyfikowalną kopią" (immutable
         * copy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FixTeamRecordWithCompactConstructor {
        /*
         * 🧪 Zadanie 9:
         * Napraw rekord `Zespol(String nazwa, java.util.List<String>
         * czlonkowie)` z Lesson24 (Zadanie 12) dodając kompaktowy konstruktor
         * z linijką `czlonkowie = List.copyOf(czlonkowie);`. Zademonstruj, że
         * przeciek mutacji, który wcześniej występował, już nie ma miejsca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SafeCopyHelperMethodForNullableList {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę statyczną `static java.util.List<String>
         * bezpiecznaKopia(java.util.List<String> input)` zwracającą
         * `input == null ? java.util.List.of() : List.copyOf(input)`.
         * Przetestuj ją dla null, pustej listy i listy z 3 elementami.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CompareCopyOnceVsCopyEveryGetterCall {
        /*
         * 🧪 Zadanie 11:
         * Napisz klasę ImmutableEventLog z polem final `List<String>
         * zdarzenia` - konstruktor robi defensywną kopię ORAZ metoda
         * `wszystkie()` zwraca `List.copyOf(zdarzenia)` (kolejna kopia przy
         * KAŻDYM wywołaniu). W komentarzu porównaj to z podejściem "skopiuj
         * raz w konstruktorze, potem zwracaj tę samą niemodyfikowalną
         * referencję z gettera" pod kątem wydajności vs bezpieczeństwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FixImmutableConfigMapCopyOf {
        /*
         * 🧪 Zadanie 12:
         * Napraw klasę ImmutableConfig z Lesson24 (Zadanie 17, final
         * `Map<String, String> ustawienia`) przy pomocy `Map.copyOf` w
         * konstruktorze. Zademonstruj, że przeciek mutacji przez oryginalną
         * mapę już nie występuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImmutableEmployeeWithImmutableAddressRecord {
        /*
         * 🧪 Zadanie 13:
         * Nawiązując do ImmutableEmployee z Lesson24 (Zadanie 14) - napisz
         * wersję, w której Address jest rekordem (niemutowalny), a nie
         * mutowalną klasą. W komentarzu wyjaśnij, dlaczego "obronienie się"
         * przed mutowalnością elementu czasem oznacza zamianę TYPU pola na
         * niemutowalny, a nie tylko kopiowanie kolekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ShallowCopyDoesNotProtectMutableElements {
        /*
         * 🧪 Zadanie 14:
         * Napisz klasę Zespol2 z polem final `List<Gracz> gracze`, gdzie
         * Gracz to MUTOWALNA klasa (z setImie(String)). Zademonstruj, że
         * `List.copyOf(gracze)` (płytka kopia) NIE chroni przed mutacją
         * pojedynczego obiektu Gracz przez zewnętrzną referencję. W
         * komentarzu zaproponuj (opisowo) jak wykonać głęboką kopię.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementDeepCopyWithMapToNewInstance {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj głęboką kopię z Zadania 14: dodaj do klasy Gracz
         * metodę `Gracz kopia()` i użyj jej w konstruktorze Zespol2 przez
         * `gracze.stream().map(Gracz::kopia).toList()`. Zademonstruj, że
         * mutacja zewnętrznego obiektu Gracz NIE wpływa już na stan
         * wewnętrzny zespołu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FixBankAccountSnapshotFromLesson24 {
        /*
         * 🧪 Zadanie 16:
         * Napraw klasę BankAccountSnapshot z Lesson24 (Zadanie 30) przy
         * użyciu defensywnego kopiowania (`List.copyOf` w konstruktorze).
         * Napisz krótki test potwierdzający, że "zdjęcie" konta jest teraz w
         * pełni bezpieczne (mutacja oryginalnej listy transakcji po
         * utworzeniu snapshotu nie ma wpływu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ConfigurableViewOrCopyHelper {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę generyczną `static <T> java.util.List<T>
         * widokLubKopia(java.util.List<T> input, boolean uzyjKopii)`
         * zwracającą albo `Collections.unmodifiableList(input)`, albo
         * `List.copyOf(input)` w zależności od flagi. Napisz test
         * demonstrujący różnicę zachowania przy mutacji `input` PO wywołaniu
         * metody dla obu wariantów flagi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasurePerformanceUnmodifiableVsCopyOf {
        /*
         * 🧪 Zadanie 18:
         * Zmierz `System.nanoTime()` różnicę kosztu między
         * `Collections.unmodifiableList` (brak kopiowania) a `List.copyOf`
         * (kopiuje całą zawartość) dla dużej listy (np. 100 000 elementów).
         * Wypisz zmierzone czasy i krótki wniosek w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImmutableGraphDoubleLevelCopy {
        /*
         * 🧪 Zadanie 19:
         * Napisz klasę ImmutableGraph z polem final `Map<String,
         * Set<String>> sasiedzi`. Zademonstruj PODWÓJNE defensywne
         * kopiowanie: samej mapy ORAZ każdego zbioru sąsiadów wewnątrz niej
         * (bo `Map.copyOf` robi tylko płytką kopię - wartości, czyli
         * Set<String>, wciąż mogą być tymi samymi mutowalnymi obiektami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_WriteFullImmutabilityChecklist {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz w komentarzu (min. 5 zdań) kompletny
         * "checklist" bezpiecznej, niemutowalnej klasy łączący wiedzę z
         * Lesson24 i Lesson25 - final pola, final klasa, brak setterów,
         * walidacja w konstruktorze, defensywna kopia wejścia, defensywna
         * kopia/niemodyfikowalny widok wyjścia, świadomość płytkiej vs
         * głębokiej kopii.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImmutableScheduleShallowCopySufficient {
        /*
         * 🧪 Zadanie 21:
         * Napisz klasę ImmutableSchedule z polem final
         * `List<java.time.LocalDateTime> terminy` (LocalDateTime jest
         * niemutowalny). W komentarzu wyjaśnij, dlaczego w tym konkretnym
         * przypadku wystarczy PŁYTKA kopia (`List.copyOf`) i nie trzeba
         * głębokiej kopii elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImmutableDocumentByteArrayCloneBothSides {
        /*
         * 🧪 Zadanie 22:
         * Napisz klasę ImmutableDocument z polem final `byte[] zawartosc`.
         * Zaimplementuj poprawnie konstruktor (`clone()`) i getter
         * (`clone()`). Napisz test wysyłający "ten sam" dokument do dwóch
         * "odbiorców" (zmiennych) i pokaż, że mutacja kopii jednego odbiorcy
         * nie wpływa na drugiego ani na oryginał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_GenericImmutableWrapperWithCopyStrategy {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj generyczną klasę `ImmutableWrapper<T>` przyjmującą
         * `java.util.function.UnaryOperator<T> kopiujacy` (strategię
         * kopiowania) zamiast zakładać konkretny typ. Zademonstruj jej
         * użycie zarówno z `List<String>` (kopiujący przez `List::copyOf`)
         * jak i z niestandardowym typem mutowalnym (kopiujący przez metodę
         * `kopia()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DocumentIntentionalUnsafeFactoryMethod {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę fabryczną `static <T> List<T>
         * unsafeWrap(List<T> list)`, która celowo NIE kopiuje (ze względów
         * wydajnościowych) - opatrz ją komentarzem javadoc ostrzegającym
         * wywołującego, że przejmuje WŁASNOŚĆ (ownership) listy i wywołujący
         * NIE MOŻE jej już modyfikować. W komentarzu oceń, kiedy taki
         * kompromis (brak kopii + kontrakt w dokumentacji) bywa akceptowalny
         * w realnych bibliotekach (np. `List.of(...)` w samym JDK).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ListCopyOfOptimizationAlreadyImmutable {
        /*
         * 🧪 Zadanie 25:
         * Zademonstruj, że `List.copyOf(existingList)` wywołane na LIŚCIE,
         * która jest JUŻ wynikiem wcześniejszego `List.copyOf(...)`, zwraca
         * TĘ SAMĄ referencję (optymalizacja, bo kopiowanie już
         * niemodyfikowalnej listy jest zbędne) - zweryfikuj to operatorem
         * `==` na dwóch takich wywołaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConcurrentReadersAgainstImmutableConfig {
        /*
         * 🧪 Zadanie 26:
         * Nawiązując do _05_multithreading: uruchom kilka wątków
         * odczytujących jednocześnie współdzieloną, poprawnie zdefensywnie
         * skopiowaną strukturę (np. naprawiony ImmutableConfig z Zadania
         * 12) razem z jednym wątkiem próbującym (bezskutecznie, bo ma tylko
         * dostęp do gettera zwracającego niemodyfikowalną mapę) ją
         * zmodyfikować. Złap i policz wyjątki UnsupportedOperationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DeepCopy2dArrayRowByRow {
        /*
         * 🧪 Zadanie 27:
         * Napraw klasę ImmutableMatrix z Lesson24 (Zadanie 26) - zaimplementuj
         * GŁĘBOKĄ kopię tablicy dwuwymiarowej `int[][]` w konstruktorze
         * (skopiuj KAŻDY wiersz osobno, nie tylko tablicę wierszy) oraz w
         * getterze. Zademonstruj, że teraz mutacja `dane[0][0] = x` na
         * zewnętrznej tablicy nie wpływa na macierz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareThreeGetterStrategies {
        /*
         * 🧪 Zadanie 28:
         * Porównaj (kodem + krótkim komentarzem dla każdej) 3 strategie
         * zwracania kolekcji z gettera: (a) żywa referencja (niebezpieczne),
         * (b) `Collections.unmodifiableList` (widok), (c) `List.copyOf`
         * (kopia). Dla każdej napisz krótki test pokazujący jej realne
         * zachowanie przy próbie mutacji z zewnątrz oraz przy mutacji
         * oryginału.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImmutableCacheNoSynchronizationNeeded {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj prostą, w pełni niemutowalną klasę `ImmutableCache<K,
         * V>` (niemodyfikowalna mapa budowana raz w konstruktorze przez
         * `Map.copyOf`). W komentarzu wyjaśnij, dlaczego niemutowalność
         * sprawia, że NIE POTRZEBUJE żadnej synchronizacji
         * (`synchronized`/`ConcurrentHashMap`), mimo że będzie czytana przez
         * wiele wątków jednocześnie (odwołanie do Lesson10_ThreadSafety).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FinalClusterCapstoneOrderSnapshot {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie klastra (Lekcje 23-25): zaprojektuj kompletną, w
         * pełni bezpieczną niemutowalną klasę `OrderSnapshot` (numer
         * zamówienia - String, klient - String, final `List<String>
         * pozycje`, final `BigDecimal suma`) łączącą WSZYSTKIE poznane
         * techniki - final klasa, final pola, walidacja w konstruktorze,
         * defensywne kopiowanie wejścia (`List.copyOf`), brak żywych
         * referencji w getterach. Napisz krótki test (możesz świadomie użyć
         * `var` przy lokalnych zmiennych testu, nawiązując do Lesson23)
         * tworzący snapshot, próbujący go zepsuć na 2 sposoby (przez
         * oryginalną listę i przez getter) i potwierdzający, że żadna próba
         * się nie powiodła.
         */
        public static void main(String[] args) { }
    }
}
