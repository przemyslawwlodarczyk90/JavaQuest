package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise09_ArrayReplaceElement {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 9 (łatwe)
         *
         * W tablicy String[] colors = {"czerwony", "zielony", "niebieski"}
         * zamień "zielony" na "żółty" i wypisz wszystkie elementy.
         */

        String[] colors = {"czerwony", "zielony", "niebieski"};

        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals("zielony")) {
                colors[i] = "żółty";
            }
        }

        for (String color : colors) {
            System.out.println(color);
        }
    }
}
