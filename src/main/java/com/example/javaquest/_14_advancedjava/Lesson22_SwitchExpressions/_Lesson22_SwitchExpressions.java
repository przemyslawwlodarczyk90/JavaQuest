package com.example.javaquest._14_advancedjava.Lesson22_SwitchExpressions;

public class _Lesson22_SwitchExpressions {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔁 PRZYPOMNIENIE Z _01_fundamentals/Lesson03_Conditionals
         * ============================================================
         * W Lekcji 03 poznałeś już podstawy: switch jako WYRAŻENIE
         * (zwraca wartość), strzałkową składnię `->` bez `break`, `yield`
         * dla bloków oraz wiele etykiet na jedną gałąź (`case 1, 2, 3 ->`).
         * Tutaj pogłębiamy temat: PORÓWNUJEMY strzałkę z klasyczną
         * składnią dwukropkową (i jej pułapką - fall-through), patrzymy
         * głębiej na `yield`, i dokładnie omawiamy WYCZERPANIE
         * (exhaustiveness) dla enumów.
         */

        int day = 3;
        String name = switch (day) {
            case 1 -> "Poniedziałek";
            case 2 -> "Wtorek";
            case 3 -> "Środa";
            default -> "Inny dzień";
        };
        System.out.println("Przypomnienie: " + name);

        /*
         * ============================================================
         * 🆚 SKŁADNIA STRZAŁKOWA (->) vs KLASYCZNA DWUKROPKOWA (:) I FALL-THROUGH
         * ============================================================
         * Klasyczny switch (od JDK 1.0) używa dwukropka i wymaga JAWNEGO
         * `break`, żeby zakończyć gałąź. Jeśli o `break` zapomnimy,
         * wykonanie "SPADA" (fall-through) do KOLEJNEJ etykiety - to
         * jedno z najbardziej klasycznych źródeł błędów w Javie.
         */

        System.out.println("\n--- BUGGY: classic switch BEZ break (fall-through) ---");
        printDayTypeBuggy(3); // dzień roboczy, ale zobacz co się wypisze...

        System.out.println("\n--- FIXED: classic switch Z break ---");
        printDayTypeFixed(3);

        System.out.println("\n--- Arrow switch: fall-through NIE ISTNIEJE ---");
        printDayTypeArrow(3);

        /*
         * Arrow switch (->) w OGÓLE nie ma mechanizmu "spadania" do
         * kolejnej gałęzi - każda gałąź jest w pełni niezależna. To
         * CAŁKOWICIE eliminuje tę kategorię błędów, nie tylko "przypomina
         * o break".
         */

        /*
         * ============================================================
         * 🎯 SWITCH JAKO WYRAŻENIE – GŁĘBIEJ
         * ============================================================
         * Switch-wyrażenie MUSI zwrócić wartość tego samego (lub
         * kompatybilnego) typu w KAŻDEJ gałęzi - kompilator to
         * weryfikuje. Można go użyć BEZPOŚREDNIO jako argument metody
         * albo w instrukcji return, bez pośredniej zmiennej.
         */

        System.out.println(formatScore(85));
        System.out.println(formatScore(40));

        /*
         * ============================================================
         * 🔢 WIELE ETYKIET NA JEDNĄ GAŁĄŹ – Z ENUMEM
         * ============================================================
         */

        for (Season s : Season.values()) {
            System.out.println(s + " -> " + activityFor(s));
        }

        /*
         * ============================================================
         * 📤 yield DLA BLOKÓW – GŁĘBIEJ
         * ============================================================
         * `yield` działa jak `return`, ale wychodzi TYLKO z bloku switch-
         * wyrażenia, NIE z całej metody. Można go używać wewnątrz
         * dowolnie złożonej logiki (if, pętle) - liczy się, że KAŻDA
         * ścieżka w bloku kończy się jakimś yield.
         */

        int code = 2;
        String message = switch (code) {
            case 1, 2, 3 -> {
                String base = "Kod niski: " + code;
                if (code == 2) {
                    yield base + " (przypadek specjalny)";
                }
                yield base;
            }
            default -> "Kod inny";
        };
        System.out.println(message);

        /*
         * ============================================================
         * ✅ WYCZERPANIE (EXHAUSTIVENESS) DLA ENUM
         * ============================================================
         * Switch-WYRAŻENIE nad enumem MUSI obsłużyć WSZYSTKIE stałe
         * (albo mieć default) - inaczej kompilacja się nie powiedzie.
         * Poniższa metoda NIE MA default, bo obsługuje WSZYSTKIE 4 stałe
         * Season - to ta sama korzyść refaktoryzacyjna, co przy sealed
         * (Lekcja 19) i switch pattern matching (Lekcja 21): dodanie
         * nowej stałej do enuma bez zaktualizowania tego switcha
         * SPOWODUJE BŁĄD KOMPILACJI.
         */

        for (Season s : Season.values()) {
            System.out.println(describeSeasonExhaustive(s));
        }

        /*
         * ============================================================
         * ⚠️ SWITCH-STATEMENT (klasyczny, nie-wyrażenie) NIE WYMAGA WYCZERPANIA
         * ============================================================
         * To ważna różnica: powyższa reguła dotyczy tylko switcha jako
         * WYRAŻENIA. Zwykły switch-INSTRUKCJA (statement - nic nie
         * zwraca) może pominąć dowolną liczbę stałych enuma i i tak się
         * skompiluje - po prostu nic się nie wykona dla pominiętych
         * przypadków.
         */

        printSeasonStatement(Season.WINTER);
        printSeasonStatement(Season.SPRING); // pominięty przypadek - po prostu cisza

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - klasyczny switch (:) wymaga break, inaczej fall-through
         *   (kod "spada" do kolejnej etykiety) - klasyczne źródło bugów
         * - arrow switch (->) NIE MA fall-through w ogóle - każda gałąź
         *   jest niezależna
         * - switch-wyrażenie można używać bezpośrednio jako argument,
         *   wartość zwracana, itp. - musi zwracać spójny typ w KAŻDEJ
         *   gałęzi
         * - `yield` kończy blok switcha (nie całą metodę) - używany,
         *   gdy gałąź potrzebuje więcej niż jednej linii kodu
         * - switch-WYRAŻENIE nad enumem musi być WYCZERPUJĄCE (wszystkie
         *   stałe albo default) - kompilator to wymusza
         * - switch-INSTRUKCJA (statement) NIE wymaga wyczerpania, nawet
         *   nad enumem
         *
         * ============================================================
         * 🏁 KONIEC KLASTRA "NOWOCZESNY SYSTEM TYPÓW" (Lekcje 19-22)
         * ============================================================
         * Podsumowanie czterech lekcji:
         * - Lekcja 19: sealed classes - domykamy zbiór TYPÓW, znany
         *   kompilatorowi w całości
         * - Lekcja 20: pattern matching instanceof - bezpieczne
         *   sprawdzanie typu + flow scoping zmiennej wzorca
         * - Lekcja 21: switch pattern matching + record patterns -
         *   łączymy sealed + instanceof + dekompozycję rekordów w
         *   zwięzłą, WYCZERPUJĄCĄ formę
         * - Lekcja 22: switch expressions - nowoczesna, bezpieczna
         *   alternatywa dla klasycznego switcha, też z wyczerpaniem
         *
         * Wspólny motyw: kompilator coraz częściej PILNUJE za nas
         * kompletności obsługi przypadków - to fundament bezpiecznej
         * refaktoryzacji w dużych projektach.
         *
         * 🔮 ZAPOWIEDŹ LEKCJI 23: var i wnioskowanie typów (type
         * inference) - zobaczymy, gdzie `var` skraca kod bezpiecznie,
         * a gdzie może zaszkodzić czytelności.
         */
    }

    // BUGGY: classic switch statement bez break -> fall-through
    static void printDayTypeBuggy(int day) {
        switch (day) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("Dzień roboczy");
                // BRAK break! -> wykonanie "spada" dalej do case 6
            case 6:
            case 7:
                System.out.println("Weekend"); // wypisze się TEŻ dla dnia roboczego!
        }
    }

    static void printDayTypeFixed(int day) {
        switch (day) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("Dzień roboczy");
                break;
            case 6:
            case 7:
                System.out.println("Weekend");
                break;
        }
    }

    static void printDayTypeArrow(int day) {
        switch (day) {
            case 1, 2, 3, 4, 5 -> System.out.println("Dzień roboczy");
            case 6, 7 -> System.out.println("Weekend");
        }
    }

    static String formatScore(int score) {
        // switch-wyrażenie użyte BEZPOŚREDNIO w konkatenacji, jako część returnowanego Stringa
        return "Wynik " + score + ": " + switch (Integer.signum(score - 60)) {
            case 1, 0 -> "Zaliczone";
            case -1 -> "Niezaliczone";
            default -> "?"; // wymagane - kompilator nie wie, że signum() zwraca TYLKO -1/0/1
        };
    }

    static String activityFor(Season season) {
        return switch (season) {
            case WINTER, AUTUMN -> "Czytanie książek";
            case SPRING, SUMMER -> "Spacer";
        };
    }

    static String describeSeasonExhaustive(Season season) {
        // BRAK default - kompilator wymusza obsłużenie WSZYSTKICH stałych enuma
        return switch (season) {
            case SPRING -> "Wiosna - kwitnienie";
            case SUMMER -> "Lato - upały";
            case AUTUMN -> "Jesień - liście";
            case WINTER -> "Zima - śnieg";
        };
    }

    static void printSeasonStatement(Season season) {
        // switch STATEMENT (nie expression) - default NIE jest wymagany,
        // nawet gdy pomijamy część stałych enuma
        switch (season) {
            case WINTER:
                System.out.println("Ubierz się ciepło");
                break;
            case SUMMER:
                System.out.println("Weź wodę");
                break;
            // SPRING i AUTUMN celowo pominięte - to i tak SIĘ SKOMPILUJE
        }
    }
}

enum Season {
    SPRING, SUMMER, AUTUMN, WINTER
}
