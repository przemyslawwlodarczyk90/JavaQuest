package com.example.javaquest.conditionals03;

public class Lesson03_Conditionals {

    public static void main(String[] args) {

        // ========================
        // INSTRUKCJE WARUNKOWE IF / ELSE IF / ELSE
        // ========================

        int number = 15;

        // Instrukcja if sprawdza, czy dany warunek jest prawdziwy.
        // Jeśli tak, wykonywany jest blok kodu wewnątrz if-a.
        if (number > 20) {
            System.out.println("Liczba jest większa niż 20");
        }
        // Instrukcja else if umożliwia sprawdzenie innego warunku,
        // jeśli pierwszy nie został spełniony.
        else if (number == 20) {
            System.out.println("Liczba jest równa 20");
        }
        // Instrukcja else wykona się, jeśli żaden wcześniejszy warunek nie był prawdziwy.
        else {
            System.out.println("Liczba jest mniejsza niż 20");
        }

        // ========================
        // OPERATOR TERNARNY (SKRÓT IF/ELSE)
        // ========================

        int age = 17;

        // Operator ternarny pozwala na przypisanie wartości do zmiennej
        // w zależności od spełnienia warunku. Składnia:
        // warunek ? wartość_jeśli_prawda : wartość_jeśli_fałsz
        String access = (age >= 18) ? "Dostęp przyznany" : "Brak dostępu";
        System.out.println("Wynik ternarny: " + access);

        // ========================
        // INSTRUKCJA SWITCH (STARA SYNTAXA - dostępna od Javy 1.0)
        // ========================

        String day = "Środa";

        // Klasyczna wersja switch:
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
                break;
        }

        // ========================
        // INSTRUKCJA SWITCH (NOWA SYNTAXA - dostępna od Javy 14 w trybie preview, od Javy 17 oficjalnie)
        // ========================

        String result = switch (day) {
            case "Poniedziałek" -> "Pierwszy dzień tygodnia";
            case "Środa" -> "Środek tygodnia";
            case "Piątek" -> "Ostatni dzień roboczy";
            default -> "Nieznany dzień";
        };

        System.out.println("Nowy switch: " + result);

        // ========================
        // ZAGNIEŻDŻONE IF-y
        // ========================

        int temperature = 25;
        boolean isRaining = false;

        // Zagnieżdżony if to sytuacja, gdy w jednym if-ie znajduje się inny if.
        // To pozwala na dokładniejsze sprawdzanie zależnych warunków.
        if (temperature > 20) {
            if (!isRaining) {
                System.out.println("Jest ciepło i nie pada - idealna pogoda!");
            } else {
                System.out.println("Jest ciepło, ale pada deszcz.");
            }
        } else {
            System.out.println("Jest chłodno.");
        }
    }
}
