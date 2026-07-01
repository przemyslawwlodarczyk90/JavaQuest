package com.example.javaquest._01_fundamentals.Lesson12_BinaryAndHex;

public class _Exercises_Lesson12_BinaryAndHex {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_ToBinaryString {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj int n = 42.
         * Wypisz jego reprezentację binarną używając Integer.toBinaryString(n).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_ToHexString {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj int n = 255.
         * Wypisz jego reprezentację szesnastkową (hex) używając Integer.toHexString(n).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_BinaryLiteral {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj zmienną używając literału binarnego: int flags = 0b1101.
         * Wypisz jej wartość dziesiętną i binarną.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_HexLiteral {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj zmienną używając literału hex: int color = 0xFF0000.
         * Wypisz jej wartość dziesiętną.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_ParseBinaryString {
        /*
         * 🧪 Zadanie 5:
         * Sparsuj String binarny "101010" na int używając Integer.parseInt("101010", 2).
         * Wypisz wynik dziesiętny (42).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_ParseHexString {
        /*
         * 🧪 Zadanie 6:
         * Sparsuj String hex "ff" na int używając Integer.parseInt("ff", 16).
         * Wypisz wynik dziesiętny (255).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_OctalLiteral {
        /*
         * 🧪 Zadanie 7:
         * Zadeklaruj int octal = 077 (oktalnie, cyfra 0 na początku oznacza octal).
         * Wypisz jego wartość dziesiętną i przekonwertuj do oktalnego stringa: Integer.toOctalString(63).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_DecToAllSystems {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj int n = 100.
         * Wypisz n w systemach: dziesiętnym, binarnym, ósemkowym i szesnastkowym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_HexToDecimal {
        /*
         * 🧪 Zadanie 9:
         * Sparsuj hex Stringi: "1F", "A5", "FF" na int.
         * Wypisz każdy wynik w systemie dziesiętnym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_BinaryToDec {
        /*
         * 🧪 Zadanie 10:
         * Sparsuj binarne Stringi: "1010", "11111111", "1000" na int.
         * Wypisz każdy wynik w systemie dziesiętnym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_PaddedBinaryString {
        /*
         * 🧪 Zadanie 11:
         * Zadeklaruj int n = 7.
         * Wypisz jego reprezentację binarną uzupełnioną zerami do 8 bitów: "00000111".
         * Użyj String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0').
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_HexToBinary {
        /*
         * 🧪 Zadanie 12:
         * Stwórz String hex = "1F".
         * Przekonwertuj hex → dziesiętny → binarny.
         * Wypisz każdy etap.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_BinaryToHex {
        /*
         * 🧪 Zadanie 13:
         * Stwórz String binary = "1100".
         * Przekonwertuj binary → dziesiętny → hex.
         * Wypisz każdy etap.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_OctalToDecAndBin {
        /*
         * 🧪 Zadanie 14:
         * Sparsuj oktalny String "075" używając Integer.parseInt("075", 8).
         * Wypisz wynik dziesiętny i binarny.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_BitFlagCheck {
        /*
         * 🧪 Zadanie 15:
         * Zadeklaruj int flags = 0b101101.
         * Sprawdź, czy 3-ci bit (bit nr 2, licząc od 0) jest ustawiony.
         * Użyj maski: int mask = 1 << 2; boolean isSet = (flags & mask) != 0;
         * Wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_SetBit {
        /*
         * 🧪 Zadanie 16:
         * Zadeklaruj int flags = 0b101000.
         * Ustaw bit nr 1 używając: flags |= (1 << 1).
         * Wypisz wartość flags przed i po ustawieniu bitu (w formacie binarnym).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_ClearBit {
        /*
         * 🧪 Zadanie 17:
         * Zadeklaruj int flags = 0b111111.
         * Wyczyść bit nr 3 używając: flags &= ~(1 << 3).
         * Wypisz wartość flags przed i po wyczyszczeniu bitu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_ToggleBit {
        /*
         * 🧪 Zadanie 18:
         * Zadeklaruj int flags = 0b101010.
         * Przełącz (toggle) bit nr 0 używając: flags ^= (1 << 0).
         * Wypisz wartość przed i po, a następnie przełącz ponownie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_PrintFlagsHex {
        /*
         * 🧪 Zadanie 19:
         * Zadeklaruj int flags = 0b101101.
         * Wypisz jego wartość:
         * - binarnie: Integer.toBinaryString(flags)
         * - hex: Integer.toHexString(flags)
         * - dziesiętnie: wprost
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ConvertManyNumbers {
        /*
         * 🧪 Zadanie 20:
         * Wypisz konwersje dla n = 0, 1, 10, 15, 16, 127, 255:
         * Format: "n=X | bin=... | oct=... | hex=..."
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_HexColorParser {
        /*
         * 🧪 Zadanie 21:
         * Masz kolor HTML "#FF5733".
         * Wyodrębnij składowe R, G, B parsując po 2 znaki od indeksu 1, 3, 5:
         * int r = Integer.parseInt("FF", 16), itd.
         * Wypisz: "R=255, G=87, B=51".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ManualBinaryConversion {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj konwersję z dziesiętnego na binarny BEZ użycia Integer.toBinaryString().
         * Użyj pętli while i operatorów / i %.
         * Sprawdź dla n = 42 – wynik powinien być "101010".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_BinaryStringToIntManually {
        /*
         * 🧪 Zadanie 23:
         * Zadeklaruj String bin = "101010".
         * Przelicz ręcznie na int BEZ Integer.parseInt():
         * Iteruj przez znaki od prawej, mnożąc pozycję przez potęgę 2.
         * Wynik powinien być 42.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_NumberBaseConverter {
        /*
         * 🧪 Zadanie 24:
         * Napisz program, który wypisuje "tablicę konwersji" dla liczb 0-15:
         * Format tabeli: "Dec | Bin    | Oct | Hex"
         *                  0  | 0      | 0   | 0
         *                  1  | 1      | 1   | 1
         *                 ...
         *                 15  | 1111   | 17  | f
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_PermissionBitmask {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj flagi uprawnień:
         * int READ = 1 << 0, WRITE = 1 << 1, EXECUTE = 1 << 2.
         * Stwórz wartość permissions = READ | WRITE.
         * Sprawdź: czy ma READ, WRITE, EXECUTE?
         * Usuń WRITE i sprawdź ponownie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_HexToRGBStruct {
        /*
         * 🧪 Zadanie 26:
         * Zadeklaruj int rgb = 0x1A2B3C.
         * Wyodrębnij R, G, B używając przesunięć bitowych i masek:
         * int r = (rgb >> 16) & 0xFF;
         * int g = (rgb >> 8) & 0xFF;
         * int b = rgb & 0xFF;
         * Wypisz każdą składową.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_PackRGBIntoInt {
        /*
         * 🧪 Zadanie 27:
         * Masz: int r = 255, g = 128, b = 0.
         * Upakuj je w jeden int używając: int packed = (r << 16) | (g << 8) | b.
         * Wypisz packed w hex. Następnie wyodrębnij z powrotem i sprawdź.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_CheckPowerOfTwo {
        /*
         * 🧪 Zadanie 28:
         * Sprawdź, czy liczba jest potęgą dwójki używając bitowej sztuczki:
         * n > 0 && (n & (n - 1)) == 0
         * Sprawdź dla n = 1, 2, 3, 4, 8, 10, 16, 100.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_CountSetBits {
        /*
         * 🧪 Zadanie 29:
         * Policz liczbę bitów ustawionych na 1 w int n = 42 (0b101010 → 3 bity).
         * Zaimplementuj bez Integer.bitCount(): użyj while i n & 1 oraz n >>= 1.
         * Sprawdź wynik z Integer.bitCount(42).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_BinaryCalculator {
        /*
         * 🧪 Zadanie 30:
         * Zadeklaruj dwa binarne Stringi: String a = "1010", String b = "1100".
         * Sparsuj oba na int, wykonaj działania: AND, OR, XOR.
         * Wypisz wyniki w formacie: "A AND B = X (binarnie: ...)", itd.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
