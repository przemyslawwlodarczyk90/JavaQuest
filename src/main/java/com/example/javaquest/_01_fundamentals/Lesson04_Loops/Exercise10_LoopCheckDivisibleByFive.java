package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise10_LoopCheckDivisibleByFive {
    public static void main(String[] args) {
        // 🔎 Wypisz liczby od 1 do 50 podzielne przez 5

        int i = 1;

        do{
            if(i%5==0){
                System.out.println(i);
            }
            i++;
        }while (i<=50);

    }
}
