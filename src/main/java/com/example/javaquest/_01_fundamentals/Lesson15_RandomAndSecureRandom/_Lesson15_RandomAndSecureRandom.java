package com.example.javaquest._01_fundamentals.Lesson15_RandomAndSecureRandom;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Lesson15_RandomAndSecureRandom {
    public static void main(String[] args) {

        /*
         * =====================================================================
         * 🎲 RANDOM VS SECURERANDOM W JAVIE
         * =====================================================================
         * W Javie możemy generować liczby losowe na kilka sposobów:
         *
         * 1. Math.random() – najprostszy, ale statyczny i mniej elastyczny
         * 2. Random – klasyczny generator liczb pseudolosowych (seed, metody nextInt, nextDouble itd.)
         * 3. ThreadLocalRandom – szybszy niż Random w środowiskach wielowątkowych (od Java 7)
         * 4. SecureRandom – kryptograficznie bezpieczny generator (np. do haseł, tokenów)
         *
         * UWAGA:
         * - Random i Math.random() są deterministyczne (mogą dawać przewidywalne wyniki!)
         * - SecureRandom zapewnia lepszą entropię, ale działa wolniej
         * - SecureRandom używa źródeł systemowych np. /dev/urandom w Linuksie
         */

        // =====================================================================
        // 1. Math.random() – zwraca double od 0.0 (włącznie) do 1.0 (wyłącznie)
        // =====================================================================
        double mathRandom = Math.random();
        System.out.println("Math.random(): " + mathRandom);

        // Losowanie liczby całkowitej 0–9:
        int mathRandomInt = (int) (Math.random() * 10);
        System.out.println("Math.random() * 10 → int: " + mathRandomInt);

        // =====================================================================
        // 2. Random – więcej kontroli: nextInt, nextDouble, nextBoolean itd.
        // =====================================================================
        Random random = new Random();

        System.out.println("Random.nextInt(100): " + random.nextInt(100));
        System.out.println("Random.nextDouble(): " + random.nextDouble());
        System.out.println("Random.nextBoolean(): " + random.nextBoolean());

        // Losowanie z określonego zakresu, np. od 50 do 100:
        int low = 50;
        int high = 100;
        int randomRange = low + random.nextInt(high - low + 1);
        System.out.println("Random 50–100: " + randomRange);

        // Losowy element z listy:
        List<String> names = List.of("Anna", "Bartek", "Celina", "Daniel");
        String randomName = names.get(random.nextInt(names.size()));
        System.out.println("Losowe imię z listy: " + randomName);

        // Losowanie tablicy w losowej kolejności:
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Collections.shuffle(numbers);
        System.out.println("Przemieszana tablica: " + numbers);

        // Losowy kolor RGB:
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        System.out.println("Losowy kolor RGB: rgb(" + r + ", " + g + ", " + b + ")");

        // =====================================================================
        // 3. ThreadLocalRandom – lepszy w aplikacjach wielowątkowych
        // =====================================================================
        int threadLocalInt = ThreadLocalRandom.current().nextInt(1, 11);
        double threadLocalDouble = ThreadLocalRandom.current().nextDouble();

        System.out.println("ThreadLocalRandom.nextInt(1, 11): " + threadLocalInt);
        System.out.println("ThreadLocalRandom.nextDouble(): " + threadLocalDouble);

        // =====================================================================
        // 4. SecureRandom – bezpieczne losowanie (np. do tokenów, haseł)
        // =====================================================================
        SecureRandom secureRandom = new SecureRandom();

        byte[] token = new byte[16];
        secureRandom.nextBytes(token);

        System.out.print("SecureRandom token (hex): ");
        for (byte byteValue : token) {
            System.out.printf("%02x", b);
        }
        System.out.println();

        // Losowe liczby całkowite i float
        System.out.println("SecureRandom.nextInt(100): " + secureRandom.nextInt(100));
        System.out.println("SecureRandom.nextDouble(): " + secureRandom.nextDouble());

        // Losowanie silnego hasła ze znakami specjalnymi:
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int index = secureRandom.nextInt(allChars.length());
            password.append(allChars.charAt(index));
        }
        System.out.println("Losowe silne hasło: " + password);

        // Losowanie UUID ręcznie (bez biblioteki):
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            uuid.append(Integer.toHexString(secureRandom.nextInt(16)));
        }
        System.out.println("Losowy UUID (32 hex): " + uuid);

        // =====================================================================
        // 📌 PODSUMOWANIE + REKOMENDACJE
        // =====================================================================
        System.out.println("\n--- PODSUMOWANIE ---");
        System.out.println("Math.random() – szybki, ale tylko double i statyczny");
        System.out.println("Random – elastyczny, nadaje się do większości zastosowań");
        System.out.println("ThreadLocalRandom – optymalny w środowiskach wielowątkowych");
        System.out.println("SecureRandom – wolniejszy, ale bezpieczny do haseł i kryptografii");

        System.out.println("\n--- KIEDY UŻYWAĆ ---");
        System.out.println("✔ Math.random() – szybkie testy, niekrytyczne wartości");
        System.out.println("✔ Random – gry, losowania w logice biznesowej");
        System.out.println("✔ ThreadLocalRandom – wysokowydajne aplikacje serwerowe");
        System.out.println("✔ SecureRandom – hasła, tokeny, numery sesji, dane użytkownika");
    }
}
