package com.example.javaquest._01_fundamentals.Lesson13_BitwiseOperators;

public class _Exercises_Lesson13_BitwiseOperators {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_BitwiseAND {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj a = 5 (0101), b = 3 (0011).
         * Oblicz i wypisz: a & b w dziesiętnym i binarnym.
         * Wynik: 1 (0001).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_BitwiseOR {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj a = 5 (0101), b = 3 (0011).
         * Oblicz i wypisz: a | b w dziesiętnym i binarnym.
         * Wynik: 7 (0111).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_BitwiseXOR {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj a = 5 (0101), b = 3 (0011).
         * Oblicz i wypisz: a ^ b w dziesiętnym i binarnym.
         * Wynik: 6 (0110).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_BitwiseNOT {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj a = 5 (0101).
         * Oblicz i wypisz: ~a w dziesiętnym i binarnym.
         * Wynik: -6 (uzupełnienie do dwóch).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_LeftShift {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj a = 5 (0101).
         * Oblicz i wypisz: a << 1 i a << 2.
         * Wyjaśnij w komentarzu: przesunięcie w lewo o n = mnożenie przez 2^n.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_RightShift {
        /*
         * 🧪 Zadanie 6:
         * Zadeklaruj a = 20.
         * Oblicz i wypisz: a >> 1 i a >> 2.
         * Wyjaśnij: przesunięcie w prawo o n = dzielenie przez 2^n (dla liczb dodatnich).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_UnsignedRightShift {
        /*
         * 🧪 Zadanie 7:
         * Zadeklaruj int neg = -8.
         * Oblicz i wypisz: neg >> 1 (signed) i neg >>> 1 (unsigned).
         * Wyjaśnij różnicę w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_PrintAllBitwiseOps {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj a = 12 (1100), b = 10 (1010).
         * Wypisz wyniki wszystkich operacji: &, |, ^, ~a, a<<1, a>>1.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_MultiplyByTwo {
        /*
         * 🧪 Zadanie 9:
         * Zadeklaruj int n = 7.
         * Oblicz n * 2 oraz n << 1 – wyniki powinny być takie same.
         * Wypisz oba i potwierdź równość.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_DivideByTwo {
        /*
         * 🧪 Zadanie 10:
         * Zadeklaruj int n = 64.
         * Oblicz n / 2 oraz n >> 1 – wyniki powinny być takie same.
         * Wypisz oba.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_CheckEvenOdd {
        /*
         * 🧪 Zadanie 11:
         * Sprawdź parzystość za pomocą operatora &:
         * Liczba parzysta ma bit 0 == 0: (n & 1) == 0.
         * Sprawdź dla: 4, 7, 10, 13, 16.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_SetBitFlag {
        /*
         * 🧪 Zadanie 12:
         * Zadeklaruj int permissions = 0.
         * Ustaw flagi READ (bit 0) i WRITE (bit 1) używając |=.
         * Wypisz permissions w binarnym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_CheckBitFlag {
        /*
         * 🧪 Zadanie 13:
         * Zadeklaruj int permissions = 0b0110 (WRITE i EXECUTE ustawione).
         * Sprawdź, czy bit READ (bit 0) jest ustawiony: (permissions & 1) != 0.
         * Sprawdź WRITE (bit 1) i EXECUTE (bit 2). Wypisz każdy wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_ClearBitFlag {
        /*
         * 🧪 Zadanie 14:
         * Zadeklaruj int permissions = 0b0111 (READ, WRITE, EXECUTE).
         * Usuń flagę WRITE (bit 1) używając: permissions &= ~(1 << 1).
         * Wypisz permissions przed i po.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_ToggleBitFlag {
        /*
         * 🧪 Zadanie 15:
         * Zadeklaruj int state = 0b1010.
         * Przełącz bit 3 używając XOR: state ^= (1 << 3).
         * Wypisz przed i po. Przełącz ponownie i wypisz – powinno wrócić do oryginału.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_SwapWithXOR {
        /*
         * 🧪 Zadanie 16:
         * Zamień wartości a = 10 i b = 20 używając TYLKO operatora XOR (bez zmiennej tymczasowej):
         * a = a ^ b; b = a ^ b; a = a ^ b;
         * Wypisz przed i po zamianie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_UserPermissionSystem {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj flagi: FLAG_ACTIVE = 1<<0, FLAG_ADMIN = 1<<1, FLAG_OWNER = 1<<2, FLAG_BANNED = 1<<3.
         * Stwórz użytkownika z uprawnieniami ACTIVE i ADMIN.
         * Sprawdź czy jest adminem. Usuń flagę ACTIVE. Wypisz uprawnienia po każdej zmianie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ExtractLowNibble {
        /*
         * 🧪 Zadanie 18:
         * Zadeklaruj int byte_val = 0xAB (= 171).
         * Wyodrębnij dolne 4 bity (low nibble): low = byte_val & 0x0F (= 0xB = 11).
         * Wyodrębnij górne 4 bity (high nibble): high = (byte_val >> 4) & 0x0F (= 0xA = 10).
         * Wypisz oba.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_BitwisePowerOfTwo {
        /*
         * 🧪 Zadanie 19:
         * Używając przesunięcia bitowego << wypisz kolejne potęgi 2 od 2^0 do 2^15.
         * Użyj: int power = 1 << i.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_AbsoluteValueBitwise {
        /*
         * 🧪 Zadanie 20:
         * Oblicz wartość bezwzględną liczby ujemnej bez if/Math.abs():
         * int n = -42;
         * int mask = n >> 31;
         * int abs = (n ^ mask) - mask;
         * Wypisz wynik i porównaj z Math.abs(-42).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_CountOneBits {
        /*
         * 🧪 Zadanie 21:
         * Policz bity ustawione na 1 w n = 42 używając pętli:
         * while (n != 0) { count += n & 1; n >>= 1; }
         * Porównaj z Integer.bitCount(42).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ReverseBits {
        /*
         * 🧪 Zadanie 22:
         * Odwróć bity 8-bitowej liczby n = 0b10110100 (180).
         * Iteruj po 8 bitach, budując odwrócony wynik.
         * Wypisz oryginał i wynik w binarnym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_BitwiseIsPowerOfTwo {
        /*
         * 🧪 Zadanie 23:
         * Sprawdź, czy liczba jest potęgą dwójki używając: n > 0 && (n & (n-1)) == 0.
         * Sprawdź dla: 1, 2, 3, 4, 5, 8, 16, 100, 128.
         * Wypisz wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_GetBitAtPosition {
        /*
         * 🧪 Zadanie 24:
         * Napisz "metodę" (logikę inline) get_bit(n, pos):
         * Zwraca bit na pozycji pos z liczby n: (n >> pos) & 1.
         * Sprawdź n = 42 (0b101010) dla pos = 0, 1, 2, 3, 4, 5.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_XORCipher {
        /*
         * 🧪 Zadanie 25:
         * Zaszyfruj wiadomość "Hello" przy użyciu XOR z kluczem 42:
         * Dla każdego znaku: encrypted[i] = message[i] ^ key.
         * Wypisz zaszyfrowany tekst i odszyfruj go ponownie przez XOR z tym samym kluczem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_PackUnpackIntoInt {
        /*
         * 🧪 Zadanie 26:
         * Upakuj cztery 8-bitowe wartości (np. 10, 20, 30, 40) w jeden int:
         * packed = (a << 24) | (b << 16) | (c << 8) | d.
         * Wyodrębnij je z powrotem i wypisz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_MaskPattern {
        /*
         * 🧪 Zadanie 27:
         * Zadeklaruj int data = 0b11001010.
         * Użyj AND z maską 0b11110000, aby wyzerować dolne 4 bity.
         * Użyj OR z maską 0b00001111, aby ustawić dolne 4 bity na 1.
         * Wypisz każdy wynik binarnie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_FindHighestSetBit {
        /*
         * 🧪 Zadanie 28:
         * Znajdź pozycję najwyższego ustawionego bitu w n = 42.
         * Użyj pętli przesuwając n w prawo (>>) dopóki n != 0.
         * Alternatywnie użyj Integer.highestOneBit() lub 31 - Integer.numberOfLeadingZeros(n).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_BitwiseAddition {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj dodawanie TYLKO przy użyciu operatorów bitowych (bez +):
         * while (b != 0) { int carry = a & b; a = a ^ b; b = carry << 1; }
         * Sprawdź: 5 + 3 = 8, 10 + 15 = 25.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_BitwiseChallenge {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj mini-system flag dla ustawień aplikacji:
         * DARK_MODE=1<<0, NOTIFICATIONS=1<<1, AUTO_SAVE=1<<2, BETA_FEATURES=1<<3.
         * Stwórz profil z DARK_MODE i AUTO_SAVE.
         * Dodaj NOTIFICATIONS, usuń AUTO_SAVE, przełącz DARK_MODE.
         * Po każdej operacji wypisz aktywne ustawienia czytelnie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
