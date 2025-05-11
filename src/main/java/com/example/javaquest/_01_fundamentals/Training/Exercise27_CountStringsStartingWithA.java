package com.example.javaquest._01_fundamentals.Training;

import java.util.Arrays;
import java.util.List;

public class Exercise27_CountStringsStartingWithA {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Ala", "Ola", "Antek", "Bartek", "Asia", "Zosia");

                int startsWithA = Math.toIntExact(names.stream()
                        .filter(name -> name.startsWith("a"))
                        .count());




        /*
         * 🧪 Ćwiczenie 27: Stream + count
         *
         * Dla listy imion policz, ile z nich zaczyna się na literę "A" (duża litera).
         *
         * List<String> names = Arrays.asList("Ala", "Ola", "Antek", "Bartek", "Asia", "Zosia");
         *
         * Wskazówki:
         * - użyj stream() + filter() + count()
         * - metoda String.startsWith("A")
         */
    }
}
