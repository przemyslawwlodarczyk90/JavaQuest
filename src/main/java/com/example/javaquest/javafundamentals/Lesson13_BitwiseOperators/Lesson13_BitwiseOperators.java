package com.example.javaquest.javafundamentals.Lesson13_BitwiseOperators;

public class Lesson13_BitwiseOperators {
    public static void main(String[] args) {

        /*
         * =====================================================================
         * ⚙️ BITWISE OPERATORS IN JAVA (Operatory bitowe)
         * =====================================================================
         * Operatory bitowe działają na reprezentacji binarnej liczb całkowitych.
         * Pozwalają manipulować pojedynczymi bitami, co jest szybkie i wydajne.
         * Często wykorzystywane są w:
         *   - programowaniu niskopoziomowym,
         *   - systemach oszczędzających pamięć,
         *   - pracy z flagami i maskami bitowymi.
         */

        int a = 5;   // 0101 w binarnym
        int b = 3;   // 0011 w binarnym

        System.out.println("a = 5 → " + Integer.toBinaryString(a));
        System.out.println("b = 3 → " + Integer.toBinaryString(b));

        // =============================================================
        // & (AND) – bitowy AND – wynik to 1 tylko, gdy oba bity są 1
        int and = a & b; // 0101 & 0011 = 0001
        System.out.println("a & b = " + and + " → " + Integer.toBinaryString(and));

        // | (OR) – bitowy OR – wynik to 1, jeśli przynajmniej jeden bit to 1
        int or = a | b; // 0101 | 0011 = 0111
        System.out.println("a | b = " + or + " → " + Integer.toBinaryString(or));

        // ^ (XOR) – bitowy XOR – wynik to 1, jeśli dokładnie jeden bit to 1
        int xor = a ^ b; // 0101 ^ 0011 = 0110
        System.out.println("a ^ b = " + xor + " → " + Integer.toBinaryString(xor));

        // ~ (NOT) – negacja bitowa – zamienia 0 na 1 i 1 na 0
        int notA = ~a; // ~0101 = ...11111010 (z uwzględnieniem bitów znaku)
        System.out.println("~a = " + notA + " → " + Integer.toBinaryString(notA));

        // =============================================================
        // << (left shift) – przesunięcie bitów w lewo (mnożenie przez 2)
        int leftShift = a << 1; // 0101 << 1 = 1010
        System.out.println("a << 1 = " + leftShift + " → " + Integer.toBinaryString(leftShift));

        // >> (right shift) – przesunięcie w prawo (dzielenie przez 2)
        int rightShift = a >> 1; // 0101 >> 1 = 0010
        System.out.println("a >> 1 = " + rightShift + " → " + Integer.toBinaryString(rightShift));

        // >>> (unsigned right shift) – przesunięcie w prawo bez znaku
        int neg = -8;
        System.out.println("neg = -8 → " + Integer.toBinaryString(neg));
        int unsignedRight = neg >>> 1;
        System.out.println("-8 >>> 1 = " + unsignedRight + " → " + Integer.toBinaryString(unsignedRight));

        // =============================================================
        // 🛡️ Praktyczne zastosowanie: Maska bitowa i flagi
        // =============================================================
        // Załóżmy, że mamy 4 flagi (bit 0 - czy aktywny, bit 1 - czy admin, itd.)
        final int FLAG_ACTIVE = 1 << 0; // 0001
        final int FLAG_ADMIN  = 1 << 1; // 0010
        final int FLAG_OWNER  = 1 << 2; // 0100
        final int FLAG_BANNED = 1 << 3; // 1000

        int userPermissions = 0;
        userPermissions |= FLAG_ACTIVE; // dodajemy flagę "active"
        userPermissions |= FLAG_ADMIN;  // dodajemy flagę "admin"

        System.out.println("Uprawnienia użytkownika (bin): " + Integer.toBinaryString(userPermissions));

        // Sprawdzenie czy flaga ADMIN jest ustawiona:
        boolean isAdmin = (userPermissions & FLAG_ADMIN) != 0;
        System.out.println("Czy użytkownik to admin? " + isAdmin);

        // Usunięcie flagi ACTIVE:
        userPermissions &= ~FLAG_ACTIVE;
        System.out.println("Po usunięciu ACTIVE: " + Integer.toBinaryString(userPermissions));

        // Przełączanie flagi OWNER (toggle):
        userPermissions ^= FLAG_OWNER;
        System.out.println("Po przełączeniu OWNER: " + Integer.toBinaryString(userPermissions));
    }
}
