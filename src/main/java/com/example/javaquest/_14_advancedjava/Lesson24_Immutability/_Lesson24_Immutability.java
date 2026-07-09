package com.example.javaquest._14_advancedjava.Lesson24_Immutability;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class _Lesson24_Immutability {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 24: NIEZMIENNOŚĆ (IMMUTABILITY) ===");

        /*
         * ============================================================
         * 📦 KRÓTKI RECAP: final Z Lesson10_FinalKeyword (_02_oop)
         * ============================================================
         * W rozdziale _02_oop, Lesson10_FinalKeyword poznałeś już słowo
         * kluczowe `final` na trzech poziomach (zmienna/pole, metoda, klasa)
         * oraz prostą klasę `ImmutablePoint` z polami `private final int x,
         * y`. To był dobry START, ale ta lekcja idzie dużo głębiej - bo samo
         * `final` na polu to NIE to samo, co pełna niezmienność OBIEKTU.
         * Zobaczysz dokładnie, gdzie ta różnica robi się niebezpieczna.
         */

        /*
         * ============================================================
         * 🔹 CZYM JEST OBIEKT NIEZMIENNY (IMMUTABLE)?
         * ============================================================
         * Obiekt jest NIEZMIENNY, jeśli jego CAŁY widoczny na zewnątrz stan
         * jest dokładnie taki sam przez cały czas życia obiektu - od momentu
         * skończenia konstruktora aż do usunięcia przez Garbage Collector.
         * Żadna metoda nie może zmienić tego stanu. "Zmiana" zawsze oznacza
         * stworzenie NOWEGO obiektu (patrz String.toUpperCase() - nie zmienia
         * oryginalnego Stringa, zwraca nowy).
         *
         * 📋 PRZEPIS NA (NAPRAWDĘ) NIEMUTOWALNĄ KLASĘ:
         *   1. Wszystkie pola: private final.
         *   2. Klasa oznaczona jako final (żeby podklasa nie dodała
         *      mutowalnego stanu albo nie przesłoniła metody, łamiąc kontrakt).
         *   3. BRAK setterów i jakichkolwiek metod mutujących pola.
         *   4. Wartości pól przypisywane WYŁĄCZNIE w konstruktorze.
         *   5. Walidacja inwariantów (niezmienników) w konstruktorze.
         *   6. ⚠️ Jeśli pole jest typu MUTOWALNEGO (tablica, List, Date,
         *      własna klasa z setterami) - punkty 1-5 NIE WYSTARCZĄ. Trzeba
         *      dodatkowo wykonać DEFENSYWNĄ KOPIĘ - i to CAŁY temat
         *      Lesson25_DefensiveCopying, który jest bezpośrednią kontynuacją
         *      tej lekcji.
         */
        System.out.println("\nNiemutowalny obiekt = caly jego stan jest identyczny przez cale zycie obiektu.");

        // Przykład klasy, która SPEŁNIA punkty 1-5 i nie ma pól mutowalnego
        // typu - to genuinely, w pełni bezpieczna klasa niemutowalna:
        ImmutableMoney cena = new ImmutableMoney(new java.math.BigDecimal("19.99"), "PLN");
        ImmutableMoney cenaPoRabacie = cena.minus(new java.math.BigDecimal("5.00"));
        System.out.println("\nCena oryginalna: " + cena);
        System.out.println("Cena po rabacie (NOWY obiekt): " + cenaPoRabacie);
        System.out.println("Oryginalna cena bez zmian: " + cena);

        /*
         * ============================================================
         * 🔍 PUŁAPKA: final NA POLU MUTOWALNEGO TYPU CHRONI TYLKO REFERENCJĘ!
         * ============================================================
         * `final` gwarantuje tylko, że nie da się PONOWNIE PRZYPISAĆ nowej
         * wartości do pola (referencja jest "przybita" raz na zawsze). NIE
         * gwarantuje NIC o tym, co dzieje się z OBIEKTEM, na który ta
         * referencja wskazuje - jeśli to obiekt mutowalny (tablica, List,
         * Date...), jego ZAWARTOŚĆ nadal można zmieniać.
         *
         * Poniżej klasa `BrokenImmutablePerson` - WYGLĄDA niemutowalnie
         * (final pola, brak setterów), ale ma DWIE dziury:
         *   a) konstruktor przechowuje CUDZĄ referencję do listy bez kopii,
         *   b) getter zwraca TĘ SAMĄ, żywą referencję na zewnątrz.
         */
        System.out.println("\n=== PULAPKA: final List NIE chroni zawartosci ===");

        List<String> hobbyKasi = new ArrayList<>(List.of("czytanie", "szachy"));
        BrokenImmutablePerson kasia = new BrokenImmutablePerson("Kasia", 27, hobbyKasi);
        System.out.println("Hobby Kasi (na starcie): " + kasia.getHobbies());

        // (a) Mutujemy ORYGINALNĄ listę PO utworzeniu obiektu - konstruktor
        //     nie zrobił kopii, więc obiekt "widzi" tę zmianę:
        hobbyKasi.add("hakowanie systemow");
        System.out.println("Po mutacji ORYGINALNEJ listy z zewnatrz: " + kasia.getHobbies());
        System.out.println("-> 'hakowanie systemow' PRZECIEKLO do wnetrza obiektu, mimo ze pole jest final!");

        // (b) Mutujemy przez sam getter - też działa, bo getter zwraca
        //     żywą referencję, a nie kopię:
        kasia.getHobbies().add("wlamania do sieci");
        System.out.println("Po mutacji PRZEZ GETTER: " + kasia.getHobbies());
        System.out.println("-> stan wewnetrzny obiektu zmienil sie BEZ zadnego settera!");

        // To samo dotyczy tablic - final tablica NIE chroni jej elementów:
        System.out.println("\n=== Ta sama pulapka z tablica final int[] ===");
        int[] oryginalneOceny = {5, 4, 5};
        BrokenImmutableGrades grades = new BrokenImmutableGrades(oryginalneOceny);
        System.out.println("Oceny (na starcie): " + java.util.Arrays.toString(grades.getScores()));

        oryginalneOceny[0] = 2; // mutacja oryginalnej tablicy z zewnatrz
        System.out.println("Po zmianie oryginalnej tablicy z zewnatrz: " + java.util.Arrays.toString(grades.getScores()));

        grades.getScores()[1] = 1; // mutacja przez getter
        System.out.println("Po mutacji przez getter: " + java.util.Arrays.toString(grades.getScores()));
        System.out.println("-> `final int[] scores` NIE zablokowal ani jednej z tych dwoch mutacji.");

        /*
         * ============================================================
         * 🔍 RECORDY (Lesson14_Records, _02_oop) A NIEZMIENNOŚĆ - PŁYTKA!
         * ============================================================
         * Rekord daje niezmienność "za darmo" dla SWOICH WŁASNYCH pól -
         * nie masz jak przypisać nowej wartości do `record.pole` (nie ma
         * takiej składni, gettery bez "set"). Ale to jest NIEZMIENNOŚĆ
         * PŁYTKA (shallow) - dokładnie ten sam problem co wyżej, jeśli pole
         * rekordu samo jest typu mutowalnego.
         */
        System.out.println("\n=== Rekord tez ma tylko PLYTKA niezmiennosc ===");

        List<String> czlonkowieBackend = new ArrayList<>(List.of("Ala", "Piotr"));
        ZespolRecord backend = new ZespolRecord("Backend", czlonkowieBackend);
        System.out.println("Zespol (na starcie): " + backend);

        czlonkowieBackend.add("Intruz"); // mutacja oryginalnej listy z zewnatrz
        System.out.println("Po mutacji oryginalnej listy: " + backend);
        System.out.println("-> mimo ze `ZespolRecord` jest rekordem, 'Intruz' PRZECIEKL do jego stanu.");
        System.out.println("(Lesson25_DefensiveCopying pokaze, jak naprawic to w konstruktorze kompaktowym.)");

        /*
         * ============================================================
         * 🔍 final NA POLU JAWNIE MUTOWALNEJ KLASY (np. java.util.Date)
         * ============================================================
         * `java.util.Date` to klasyczny przykład "starej", mutowalnej klasy
         * z JDK (ma metodę setTime()). `final Date` w polu wciąż nie chroni
         * przed wywołaniem tej metody na obiekcie, na który pole wskazuje.
         */
        System.out.println("\n=== final Date - klasyczna pulapka z JDK ===");

        Date terminUtworzenia = new Date(0L); // 1 stycznia 1970
        EventBroken event = new EventBroken("Rejestracja konta", terminUtworzenia);
        System.out.println("Data zdarzenia (na starcie): " + event.getCreated());

        terminUtworzenia.setTime(1_000_000_000_000L); // mutujemy oryginalny obiekt Date
        System.out.println("Po setTime() na oryginalnym obiekcie z zewnatrz: " + event.getCreated());
        System.out.println("-> `final Date created` nie przeszkodzil w zmianie momentu w czasie.");

        /*
         * ============================================================
         * 📌 KORZYŚCI Z NIEZMIENNOŚCI
         * ============================================================
         * - THREAD-SAFETY "za darmo": obiekt, którego stanu nie da się
         *   zmienić, jest AUTOMATYCZNIE bezpieczny wątkowo - żadnej
         *   synchronizacji, żadnych wyścigów. Pełne demo tego zjawiska
         *   znajdziesz w _05_multithreading/Lesson10_ThreadSafety (ten sam
         *   wniosek: "final rekord = automatycznie thread-safe").
         * - Bezpiecznie CACHOWAĆ i WSPÓŁDZIELIĆ: skoro nikt nie może zmienić
         *   obiektu, można go dowolnie przekazywać między metodami/wątkami/
         *   warstwami aplikacji bez obawy, że ktoś "po cichu" zepsuje stan,
         *   który widzi ktoś inny.
         * - Bezpieczne jako klucze w HashMap/HashSet: hashCode() obiektu
         *   niemutowalnego nigdy się nie zmienia, więc obiekt zawsze da się
         *   ponownie odnaleźć w kolekcji hashującej.
         * - Prostsze rozumowanie o kodzie: nie trzeba śledzić, KTO i KIEDY
         *   mógł zmienić stan obiektu w trakcie jego życia - stan "z
         *   konstruktora" to JEDYNY możliwy stan.
         *
         * ⚠️ ALE: jak właśnie zobaczyłeś, wszystkie te korzyści są WARUNKOWE -
         * działają TYLKO, jeśli klasa jest niemutowalna NAPRAWDĘ, a nie tylko
         * "z wyglądu" (final pola typu mutowalnego to fałszywe poczucie
         * bezpieczeństwa). Lesson25_DefensiveCopying pokaże DOKŁADNIE, jak
         * naprawić każdy z powyższych, zepsutych przykładów.
         */
        System.out.println("\nKorzysci (thread-safety, cache, klucze map) dzialaja TYLKO gdy klasa jest naprawde niemutowalna.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Obiekt niemutowalny = cały jego stan jest identyczny przez cały
         *   czas życia; "zmiana" = zawsze NOWY obiekt.
         * - Przepis: private final pola + final klasa + brak setterów +
         *   przypisanie tylko w konstruktorze + walidacja inwariantów.
         * - PUŁAPKA: final chroni tylko REFERENCJĘ, nie zawartość obiektu,
         *   na który wskazuje. Pole typu tablica/List/Date/mutowalna klasa
         *   wciąż można zmienić - albo przez oryginalną referencję
         *   przekazaną do konstruktora, albo przez wartość zwróconą z gettera.
         * - Rekordy (Lesson14_Records) dają niezmienność PŁYTKĄ - te same
         *   pułapki dotyczą pól rekordu typu mutowalnego.
         * - Korzyści: automatyczne thread-safety (patrz Lesson10_ThreadSafety
         *   w _05_multithreading), bezpieczne cachowanie/współdzielenie,
         *   bezpieczne klucze map, prostsze rozumowanie o kodzie.
         * - Naprawa pułapek z tej lekcji = DEFENSYWNE KOPIOWANIE, temat
         *   następnej lekcji: Lesson25_DefensiveCopying.
         */
        System.out.println("\n=== KONIEC LEKCJI 24 (dalszy ciag: Lesson25_DefensiveCopying) ===");
    }
}

/**
 * Naprawdę niemutowalna klasa - żadne z jej pól nie jest typu mutowalnego
 * (BigDecimal i String są same w sobie niemutowalne), więc "przepis" final +
 * final klasa + brak setterów w pełni wystarcza, bez potrzeby żadnej
 * dodatkowej kopii.
 */
final class ImmutableMoney {
    private final java.math.BigDecimal amount;
    private final String currency;

    ImmutableMoney(java.math.BigDecimal amount, String currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("amount i currency nie moga byc null");
        }
        this.amount = amount;
        this.currency = currency;
    }

    java.math.BigDecimal getAmount() { return amount; }
    String getCurrency() { return currency; }

    // "Modyfikacja" = zawsze NOWY obiekt, oryginal bez zmian.
    ImmutableMoney minus(java.math.BigDecimal ile) {
        return new ImmutableMoney(this.amount.subtract(ile), this.currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}

/**
 * CELOWO zepsuta klasa - wygląda niemutowalnie (final pola, brak setterów),
 * ale pole `hobbies` jest typu List (mutowalnego) i NIE jest kopiowane ani w
 * konstruktorze, ani w getterze. Naprawa: Lesson25_DefensiveCopying.
 */
class BrokenImmutablePerson {
    private final String name;
    private final int age;
    private final List<String> hobbies; // final, ale List<String> jest MUTOWALNA!

    BrokenImmutablePerson(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies; // ❌ BLAD: przechowujemy CUDZA referencje bez kopii
    }

    String getName() { return name; }
    int getAge() { return age; }

    List<String> getHobbies() {
        return hobbies; // ❌ BLAD: zwracamy ZYWA referencje na zewnatrz
    }
}

/**
 * Ten sam błąd co wyżej, tym razem na tablicy - final tablica dalej pozwala
 * zmieniać jej elementy.
 */
class BrokenImmutableGrades {
    private final int[] scores;

    BrokenImmutableGrades(int[] scores) {
        this.scores = scores; // ❌ brak kopii
    }

    int[] getScores() {
        return scores; // ❌ zwraca zywa referencje
    }
}

/**
 * Zespół jako rekord - niezmienność PŁYTKA: sam rekord nie da się zmienić
 * (nie ma jak przypisać `zespol.members = ...`), ale pole `members` typu
 * List wciąż jest mutowalną listą, jeśli nie zostanie skopiowana.
 */
record ZespolRecord(String nazwa, List<String> czlonkowie) {}

/**
 * Wydarzenie z polem final java.util.Date - klasyczna pułapka ze starym,
 * mutowalnym API czasu z JDK (java.time.* jest już w pełni niemutowalne).
 */
class EventBroken {
    private final String name;
    private final Date created;

    EventBroken(String name, Date created) {
        this.name = name;
        this.created = created; // ❌ brak kopii (np. new Date(created.getTime()))
    }

    String getName() { return name; }

    Date getCreated() {
        return created; // ❌ zwraca zywa, mutowalna referencje
    }
}
