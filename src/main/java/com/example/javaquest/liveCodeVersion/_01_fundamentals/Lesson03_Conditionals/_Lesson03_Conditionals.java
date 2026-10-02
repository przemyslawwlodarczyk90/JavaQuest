package com.example.javaquest.liveCodeVersion._01_fundamentals.Lesson03_Conditionals;

public class _Lesson03_Conditionals {

    public static void main(String[] args) {

        /*
         * ============================================================
         * IF / ELSE IF / ELSE – KLASYKA WARUNKÓW
         * ============================================================
         *
         * Używamy, gdy chcemy sprawdzić logiczne warunki:
         * - warunek może być prosty (number > 10)
         * - może być też złożony (a > 5 && b < 10)
         */

        int number = 15;

        if (number > 20) {
            System.out.println("Liczba jest większa niż 20");
        } else if (number == 20) {
            System.out.println("Liczba jest równa 20");
        } else {
            System.out.println("Liczba jest mniejsza niż 20");
        }

        /*
         * Uwaga:
         * - `else if` pozwala sprawdzać kolejne przypadki
         * - `else` zawsze pasuje do każdego innego przypadku, jeśli nic wyżej nie pasowało
         * - Możemy też używać pojedynczego `if` bez `else` – wtedy kod może wykonać się lub nie
         */

        // =====================================================
        // 🎭  TERNARNY
        // =====================================================

        int points = 85;
        String grade = (points >= 90) ? "Celujący" :
                (points >= 75) ? "Bardzo dobry" :
                        (points >= 60) ? "Dobry" : "Dostateczny";

        System.out.println("Ocena końcowa: " + grade);



        /*
         * ============================================================
         * SWITCH – WIELE OPCJI DLA TEJ SAMEJ ZMIENNEJ (OD JDK 1.0)
         * ============================================================
         */



        String day = "Środa";

        switch (day) {
            case "Poniedziałek":
                System.out.println("To jest pierwszy dzień tygodnia");
                break;
            case "Środa":
                System.out.println("To jest środek tygodnia");
                break;
            case "Piątek":
                System.out.println("To jest ostatni dzień roboczy");
                break;
            default:
                System.out.println("To nie jest konkretny dzień roboczy");
        }

        /*
         * Uwaga:
         * - każda `case` kończy się `break`, aby nie przechodzić dalej (tzw. fall-through)
         * - `default` działa jak `else` – wyłapuje przypadki niespełnione
         */

        /*
         * ============================================================
         * NOWA SYNTAXA SWITCH (JDK 14+ → JDK 17: stabilna)
         * ============================================================
         */

        String result = switch (day) {
            case "Poniedziałek" -> "Pierwszy dzień tygodnia";
            case "Środa" -> "Środek tygodnia";
            case "Piątek" -> "Ostatni dzień roboczy";
            default -> "Nieznany dzień";
        };

        System.out.println("Nowy switch: " + result);

        /*
         * Zalety nowego switcha:
         * - bardziej czytelny
         * - bez `break`
         * - zwraca wartość
         */

        /*
         * ============================================================
         * SWITCH Z `yield` – JEŚLI TRZEBA WYKONAĆ WIĘCEJ KODU
         * ============================================================
         */

        int hour = 14;

        String timeOfDay = switch (hour) {
            case 6, 7, 8, 9, 10, 11 -> "Poranek";
            case 12, 13, 14, 15, 16 -> {
                System.out.println("Witaj w ciągu dnia!");
                yield "Popołudnie";
            }
            case 17, 18, 19 -> "Wieczór";
            default -> "Noc";
        };

        System.out.println("Pora dnia: " + timeOfDay);

        /*
         * yield → działa jak return w bloku switch
         * pozwala wykonać kod przed zwróceniem wyniku
         */

        /*
         * ============================================================
         * PORÓWNANIE: IF vs SWITCH
         * ============================================================
         * - if/else if – idealne dla złożonych, dynamicznych warunków (np. a > b && c < d)
         * - switch – idealny dla sprawdzania jednej zmiennej z wieloma możliwymi wartościami
         *           (np. dzień tygodnia, status zamówienia, typ użytkownika)
         */

        /*
         * ============================================================
         * SWITCH A ENUMY – KOMBINACJA IDEALNA
         * ============================================================
         * Switch działa świetnie z enumami – np. DzienTygodnia.PONIEDZIALEK
         * Ale to temat na osobną lekcję w OOP.
         */

        /*
         * ============================================================
         * ZAGNIEŻDŻONE IF-y – LOGIKA W LOGICE
         * ============================================================
         */

        int temperature = 25;
        boolean isRaining = false;
        boolean isWindy = false;

        if (temperature > 20) {
            if (!isRaining) {
                if (!isWindy) {
                    System.out.println("Idealna pogoda – ciepło, sucho i spokojnie.");
                } else {
                    System.out.println("Ciepło i sucho, ale wietrznie.");
                }
            } else {
                System.out.println("Ciepło, ale pada deszcz.");
            }
        } else {
            System.out.println("Jest chłodno.");
        }

        /*
         * Uwaga:
         * - zagnieżdżanie ifów jest możliwe, ale lepiej nie przesadzać
         * - warto wyciągać logikę do metod pomocniczych przy skomplikowanych warunkach
         */

        /*
         * ============================================================
         * NULL-SAFE SWITCH (od Javy 17)
         * ============================================================
         */

        String language = null;

        // W Javie 17+ switch z null rzuca NullPointerException, chyba że dodamy case null:
        String languageResult = switch (language) {
            case "Java" -> "To język JVM!";
            case "Python" -> "Popularny w data science.";
//            case null -> "Brak danych o języku.";                 to dostepne dopier w jdk powyzej 18
            default -> "Inny język.";
        };

        System.out.println("Język: " + languageResult);
    }
}
