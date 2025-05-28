package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise23_LoopDrawChessboardFor {
    public static void main(String[] args) {
        // ♟️ Wypisz szachownicę 8x8 z naprzemiennymi znakami X i O używając zagnieżdżonych pętli for

        for (int i = 0; i<8; i++){
            for (int j = 0; j<4; j++){
                System.out.print("XO");
            }
            System.out.println();
        }
    }
}
