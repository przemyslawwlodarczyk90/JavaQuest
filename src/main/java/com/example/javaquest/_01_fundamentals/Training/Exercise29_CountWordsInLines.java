//package com.example.javaquest._01_fundamentals.Training;
//
//import java.io.IOException;
//import java.nio.file.Files;
//
//import java.nio.file.Path;
//import java.util.List;
//
//public class Exercise29_CountWordsInLines {
//
//    public static void wordInLineOfFileCounter(String pathOfFile){
//        try{
//            List<String> lines = Files.readAllLines(pathOfFile("input.txt"));
//            for (String line : lines){
//                int counter = 0;
//                System.out.println(line);
//                String [] arr = line.split("");
//                for (int i = 0; i< arr.length; i++){
//                    counter++;
//                }
//                System.out.println(counter);
//            }
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//
//
//    public static void main(String[] args) {
//        /*
//         * 🧪 Ćwiczenie 29: Zlicz słowa w każdej linii pliku
//         *
//         * Otwórz plik "input.txt" i dla każdej linii wypisz:
//         * - zawartość linii
//         * - liczbę słów w tej linii
//
//         * Przykład:
//         * Linia: "Ala ma kota"
//         * Słów: 3
//         *
//         * Wskazówki:
//         * - Files.readAllLines(Path)
//         * - line.split(" ") → tablica słów
//         * - wypisz każdą linię i words.length
//         */
//    }
//}
