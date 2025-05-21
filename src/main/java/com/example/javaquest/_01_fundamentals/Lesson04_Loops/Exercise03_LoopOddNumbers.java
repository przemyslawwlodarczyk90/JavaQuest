package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise03_LoopOddNumbers {
    public static void main(String[] args) {
        // 🔁 Wypisz liczby nieparzyste od 1 do 19 za pomocą pętli do-while


        int i = 1;

        do{
            if(i%2!=0){
                System.out.println(i);
            }
            i++;
        }while (i<=19);

    }
}