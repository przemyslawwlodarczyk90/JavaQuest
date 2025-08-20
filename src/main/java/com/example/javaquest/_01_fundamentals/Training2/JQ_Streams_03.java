package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_03 — "Unikalne duże słowa"
 *
 * ZADANIE:
 * Masz listę słów (String).
 * 1) Zamień każde słowo na wielkie litery.
 * 2) Usuń duplikaty.
 * 3) Posortuj alfabetycznie.
 * 4) Zwróć listę wynikową.
 *
 * Przykład:
 * Wejście: ["kot", "pies", "Kot", "pies", "ALA"]
 * Wynik: ["ALA", "KOT", "PIES"]
 */
public class JQ_Streams_03 {

    public static List<String> processWords(List<String> words) {
        return words.stream()
                .map(a->a.toUpperCase())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList("kot", "pies", "Kot", "pies", "ALA");
        System.out.println(processWords(input)); // ["ALA", "KOT", "PIES"]
    }
}
