package com.example.javaquest._14_advancedjava.Lesson24_Immutability;

public class _Exercises_Lesson24_Immutability {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteImmutableColorClass {
        /*
         * 🧪 Zadanie 1:
         * Napisz niemutowalną klasę ImmutableColor z polami `private final
         * int r, g, b`, konstruktorem i getterami (getRed/getGreen/getBlue),
         * BEZ żadnych setterów. Stwórz instancję ImmutableColor(255, 0, 0) i
         * wypisz jej składowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhySetterBreaksImmutability {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: w komentarzu dopisz do klasy z Zadania 1 metodę
         * `setRed(int r)` (samym komentarzem, nie realnym kodem) i wyjaśnij
         * min. 2 zdaniami, dlaczego jej dodanie natychmiast łamie
         * niemutowalność klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteImmutableMoneyWithAddMethod {
        /*
         * 🧪 Zadanie 3:
         * Napisz niemutowalną klasę ImmutableMoney2 z polami
         * java.math.BigDecimal amount i String currency (oba typy same są
         * niemutowalne). Dodaj metodę `add(BigDecimal ile)` zwracającą NOWY
         * obiekt ImmutableMoney2 - zademonstruj, że oryginalny obiekt się
         * nie zmienia po wywołaniu add().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainFinalFieldVsImmutableObject {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: w komentarzu (min. 3 zdania) wyjaśnij różnicę
         * między "polem final" a "obiektem niemutowalnym" - dlaczego samo
         * final na polu NIE gwarantuje, że cały obiekt jest niezmienny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RecordAutomaticallyImmutable {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj rekord `Punkt3D(double x, double y, double z)`, stwórz
         * instancję, wypisz ją. W zakomentowanej linii kodu napisz próbę
         * `punkt.x = 10;` i w komentarzu wyjaśnij, dlaczego taki kod w ogóle
         * nie kompiluje się dla rekordów (nie ma takiej składni).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BrokenBoxArrayLeakViaGetter {
        /*
         * 🧪 Zadanie 6:
         * Napisz klasę BrokenBox z polem `private final int[] dane`,
         * konstruktorem przypisującym tablicę BEZ kopiowania i getterem
         * zwracającym ją bezpośrednio. Zademonstruj (analogicznie do teorii),
         * że wywołanie `box.getDane()[0] = 999;` zmienia stan wewnętrzny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BrokenBoxArrayLeakViaOriginalReference {
        /*
         * 🧪 Zadanie 7:
         * Używając klasy BrokenBox z Zadania 6, zmutuj oryginalną tablicę PO
         * przekazaniu jej do konstruktora (przed wywołaniem gettera) i pokaż,
         * że stan wewnętrzny obiektu też się zmienił, mimo braku jakiegokolwiek
         * settera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListImmutableAndMutableJdkTypes {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: w komentarzu wypisz 4 typy z JDK, które są
         * niemutowalne (np. String, Integer, LocalDate, BigDecimal) oraz 3
         * typy, które są mutowalne mimo pozornej "prostoty" (np.
         * StringBuilder, ArrayList, java.util.Date).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FinalClassImmutableUser {
        /*
         * 🧪 Zadanie 9:
         * Napisz `final class ImmutableUser` z final polami (np. String
         * login, String email) i konstruktorem. W komentarzu wyjaśnij,
         * dlaczego oznaczenie KLASY jako final wzmacnia gwarancję
         * niemutowalności (czego dokładnie zabrania podklasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainThreadSafetyOfImmutableObjects {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: nawiązując do _05_multithreading/Lesson10_ThreadSafety,
         * wyjaśnij w komentarzu (min. 3 zdania), dlaczego niemutowalny obiekt
         * jest automatycznie bezpieczny wątkowo bez żadnej synchronizacji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImmutableIntervalWithValidation {
        /*
         * 🧪 Zadanie 11:
         * Napisz klasę ImmutableInterval z polami `private final int start,
         * end` i walidacją w konstruktorze (jeśli start > end, rzuć
         * IllegalArgumentException). Przetestuj przypadek poprawny (np. 1,
         * 10) i niepoprawny (np. 10, 1), łapiąc wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RecordListLeakDemo {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj rekord `Zespol(String nazwa, java.util.List<String>
         * czlonkowie)` BEZ defensywnego kopiowania. Stwórz mutowalną listę,
         * przekaż ją do rekordu, a następnie zmutuj oryginalną listę po
         * utworzeniu rekordu - zademonstruj, że zmiana "przecieka" do
         * rekordu mimo że jest to niby niemutowalny typ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImmutabilityChecklistInComments {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: w komentarzu napisz checklistę min. 5 punktów,
         * jakimi "na oko" sprawdziłbyś, czy dana klasa jest naprawdę
         * niemutowalna (final pola? final klasa? settery? typ pól
         * mutowalny? kopie w konstruktorze/getterze?).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImmutableEmployeeWithMutableAddress {
        /*
         * 🧪 Zadanie 14:
         * Napisz klasę ImmutableEmployee z polem `private final Address
         * adres`, gdzie Address to OSOBNA, MUTOWALNA klasa (z setterem np.
         * setMiasto(String)). Zademonstruj, że mimo iż ImmutableEmployee nie
         * ma żadnych setterów, jego stan można zmienić pośrednio przez
         * `pracownik.getAdres().setMiasto(...)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareWithOopFinalKeywordLesson {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: porównaj w komentarzu klasę ImmutablePoint z
         * _02_oop/Lesson10_FinalKeyword z klasą z Zadania 1 tej lekcji - co
         * jest takie samo, a czego tamten przykład NIE pokazywał (pułapka z
         * polami mutowalnego typu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FinalMutableDateField {
        /*
         * 🧪 Zadanie 16:
         * Napisz klasę Rejestracja z polem `private final java.util.Date
         * utworzono`, konstruktorem przypisującym otrzymany Date BEZ kopii.
         * Zademonstruj wywołaniem `utworzono.setTime(...)` na zewnętrznej
         * referencji, że final NIE chroni przed zmianą stanu obiektu Date.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImmutableConfigMapLeak {
        /*
         * 🧪 Zadanie 17:
         * Napisz klasę ImmutableConfig z polem `private final
         * java.util.Map<String, String> ustawienia`, przypisanym BEZPOŚREDNIO
         * (bez kopiowania) w konstruktorze. Zademonstruj przeciek mutacji
         * analogicznie do Zadania 12, ale dla Map zamiast List.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainCachingRiskWithMutableObjects {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: wyjaśnij w komentarzu (min. 4 zdania, z konkretnym
         * scenariuszem) dlaczego cache'owanie obiektów MUTOWALNYCH we
         * współdzielonej mapie/rejestrze jest ryzykowne, a obiektów
         * NIEMUTOWALNYCH - bezpieczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImmutablePairGenericClass {
        /*
         * 🧪 Zadanie 19:
         * Napisz generyczną klasę `ImmutablePair<K, V>` z final polami key i
         * value, bez setterów. Przetestuj z parami String/Integer oraz
         * Integer/java.util.List<String>. W komentarzu zaznacz, że sama para
         * jest niemutowalna, ale JEŚLI V jest typem mutowalnym (jak w drugim
         * przypadku), zawartość V nadal można zmienić z zewnątrz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImmutableCounterWithIncrementedMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz klasę ImmutableCounter z final polem int value - zamiast
         * mutującej metody increment(), dodaj metodę `incremented()`
         * zwracającą NOWY ImmutableCounter z wartością +1. Zademonstruj
         * łańcuch 5 wywołań (`c.incremented().incremented()...`), wypisz
         * końcową wartość i potwierdź, że oryginalny obiekt `c` pozostał bez
         * zmian.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImmutableOrderTwoWaysToBreakIt {
        /*
         * 🧪 Zadanie 21:
         * Napisz klasę ImmutableOrder z polami String id, java.util.List<String>
         * items, java.math.BigDecimal total - świadomie NIE kopiuj listy
         * defensywnie (celowa pułapka, jak w teorii). Zademonstruj DWA różne
         * sposoby zepsucia jej stanu z zewnątrz: przez oryginalną referencję
         * i przez getter. W komentarzu zapowiedz, że Lesson25 pokaże naprawę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ExplainWhyStringStaysImmutable {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: przeanalizuj w komentarzu, dlaczego mimo posiadania
         * metod takich jak `replace()`/`toUpperCase()`/`substring()`, które
         * "wyglądają jak mutujące", klasa String pozostaje w pełni
         * niemutowalna - co faktycznie zwracają te metody?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImmutableShapeHierarchyThreadSafeSharing {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj interfejs Ksztalt (metoda pole()) oraz 2 rekordy
         * implementujące go (nawiązanie do Lesson14_Records: np. Kolo,
         * Prostokat). Uruchom kilka wątków (podobnie jak w
         * Lesson10_ThreadSafety) czytających jednocześnie te same instancje
         * i wypisujące ich pole() - zademonstruj, że brak synchronizacji nie
         * powoduje żadnych problemów, bo obiekty są niemutowalne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImmutableAuditLogWithEntry {
        /*
         * 🧪 Zadanie 24:
         * Napisz klasę ImmutableAudytLog z final polem java.util.List<String>
         * wpisy. Zaimplementuj metodę `withEntry(String wpis)` zwracającą
         * NOWY ImmutableAudytLog z dodanym wpisem (skopiuj listę wewnątrz
         * metody przez `new ArrayList<>(wpisy)`, bez mutowania oryginalnej).
         * Przetestuj łańcuch dodawania 3 wpisów i wypisz każdy pośredni stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImmutableObjectsAsSafeHashMapKeys {
        /*
         * 🧪 Zadanie 25:
         * Zademonstruj kodem, że dwa niemutowalne obiekty (np. rekordy) o
         * tych samych wartościach mogą być bezpiecznie używane jako klucze w
         * HashMap (odnajdywanie działa poprawnie). W komentarzu wyjaśnij,
         * dlaczego użycie MUTOWALNEGO obiektu jako klucza HashMap jest
         * niebezpieczne (zmiana pól po dodaniu do mapy psuje hashCode i
         * uniemożliwia późniejsze odnalezienie wpisu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImmutableMatrixNestedArrayLeak {
        /*
         * 🧪 Zadanie 26:
         * Napisz klasę ImmutableMatrix z polem `private final int[][] dane`
         * (prosta macierz 2D). Zademonstruj, że nawet final dwuwymiarowa
         * tablica NIE chroni elementów WEWNĘTRZNYCH tablic - mutacja
         * `matrix.getDane()[0][0] = 999;` jest możliwa mimo że `dane` jest
         * final.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImmutableRangeWithIntersect {
        /*
         * 🧪 Zadanie 27:
         * Napisz klasę ImmutableRange (final int min, max) z metodą
         * `contains(int value)` oraz `intersect(ImmutableRange other)`
         * zwracającą NOWY ImmutableRange reprezentujący część wspólną (lub
         * null, gdy zakresy się nie przecinają). Żadna metoda nie może
         * mutować istniejących obiektów - przetestuj oba przypadki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImmutableSnapshotGenericLimitation {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj generyczną klasę `ImmutableSnapshot<T>` przyjmującą w
         * konstruktorze wartość T i udostępniającą ją tylko przez getter (bez
         * settera). Przetestuj z T = String (bezpieczne) oraz z T = własna
         * mutowalna klasa (niebezpieczne) - w komentarzu opisz ograniczenie
         * tego podejścia, gdy T samo jest typem mutowalnym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DiscussAllocationCostOfImmutableStyle {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala (koncepcyjnie): porównaj w komentarzu tworzenie
         * nowego obiektu przy KAŻDEJ "zmianie" (styl niemutowalny, jak
         * ImmutableCounter z Zadania 20) z mutowaniem istniejącego obiektu w
         * pętli 1 000 000 iteracji - kiedy koszt alokacji obiektów może być
         * realnym problemem wydajnościowym, a kiedy jest pomijalny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BankAccountSnapshotWithIntentionalTrap {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj niemutowalną klasę
         * BankAccountSnapshot (numer konta - String, saldo - BigDecimal,
         * java.util.List<String> ostatnieTransakcje) reprezentującą read-only
         * "zdjęcie" stanu konta w danym momencie (np. do wysłania przez sieć
         * lub zapisania w logu). Świadomie zostaw w niej TĘ SAMĄ pułapkę z
         * listą (bez defensywnego kopiowania) co w Zadaniu 21 - zademonstruj
         * jej istnienie testem. W komentarzu zapowiedz, że Lesson25 naprawi
         * ją defensywnym kopiowaniem.
         */
        public static void main(String[] args) { }
    }
}
