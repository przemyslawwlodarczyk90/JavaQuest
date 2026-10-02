package com.example.javaquest.liveCodeVersion._01_fundamentals.Lesson12_BinaryAndHex;

public class _Lesson12_BinaryAndHex {
    public static void main(String[] args) {

        /*
         * =====================================================================
         * 💡 SYSTEMY LICZBOWE W JAVIE
         * =====================================================================
         * Komputery operują na systemie binarnym (dwójkowym), ale programiści
         * często korzystają również z systemów dziesiętnego, ósemkowego i szesnastkowego.
         * W tej lekcji dowiesz się:
         *   - Jak działa system binarny i szesnastkowy
         *   - Jak odczytywać i zapisywać liczby w różnych systemach
         *   - Jak konwertować liczby między systemami
         *   - Jak korzystać z metod pomocniczych z klasy Integer
         *
         * SYSTEM BINARNY (podstawa 2):
         * - opiera się na cyfrach: 0 i 1
         * - każda pozycja reprezentuje kolejną potęgę liczby 2
         *   np. 1010 = 1*2^3 + 0*2^2 + 1*2^1 + 0*2^0 = 8 + 0 + 2 + 0 = 10
         *
         * SYSTEM SZESNASTKOWY (HEXADECIMAL, podstawa 16):
         * - cyfry: 0–9, A–F (gdzie A=10, B=11, ..., F=15)
         * - każda pozycja to potęga liczby 16
         *   np. 2A = 2*16^1 + 10*16^0 = 32 + 10 = 42
         *
         * SYSTEM ÓSEMKOWY (OCTAL, podstawa 8):
         * - cyfry: 0–7
         * - każda pozycja to potęga liczby 8
         *   np. 075 = 7*8^1 + 5*8^0 = 56 + 5 = 61
         */

        // =============================================================
        // 📘 System dziesiętny → binarny i hex
        // =============================================================
        int number = 42;
        String binary = Integer.toBinaryString(number);
        String hex = Integer.toHexString(number);

        System.out.println("Liczba 42 w binarnym: " + binary); // 101010
        System.out.println("Liczba 42 w szesnastkowym: " + hex); // 2a

        int number2 = 255;
        System.out.println("Liczba 255 w binarnym: " + Integer.toBinaryString(number2)); // 11111111
        System.out.println("Liczba 255 w hex: " + Integer.toHexString(number2)); // ff

        // =============================================================
        // 📘 System binarny/hex → dziesiętny
        // =============================================================
        int fromBinary = Integer.parseInt("101010", 2);
        int fromHex = Integer.parseInt("2a", 16);

        System.out.println("Binarny '101010' → dziesiętny: " + fromBinary); // 42
        System.out.println("Hex '2a' → dziesiętny: " + fromHex); // 42

        int fromBinary2 = Integer.parseInt("11111111", 2);
        System.out.println("Binarny '11111111' → dziesiętny: " + fromBinary2); // 255

        // =============================================================
        // 🛠️ Literals w innych systemach (od Javy 7+)
        // =============================================================
        int binLiteral = 0b1101;   // 13
        int hexLiteral = 0xFF;     // 255
        int decLiteral = 100;

        System.out.println("Literal binarny 0b1101 = " + binLiteral);
        System.out.println("Literal hex 0xFF = " + hexLiteral);
        System.out.println("Literal dziesiętny 100 = " + decLiteral);

        // =============================================================
        // 🔍 Formatowanie binarnego stringa z zerami wiodącymi
        // =============================================================
        int value = 7;
        String paddedBinary = String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0');
        System.out.println("Binarna wartość z zerami: " + paddedBinary); // 00000111

        // =============================================================
        // 🧮 Konwersje na inne systemy: oktalny (ósemkowy)
        // =============================================================
        int octalLiteral = 077; // 63 w dziesiętnym (0 na początku oznacza oktal)
        System.out.println("Liczba oktalna 077 (ósemkowy): " + octalLiteral);
        System.out.println("Dziesiętna 63 → oktalna: " + Integer.toOctalString(63));

        // =============================================================
        // 🔄 Konwersja hex ↔ bin ↔ dec (przykład rozszerzony)
        // =============================================================
        String hexStr = "1F";           // hex
        int decimal = Integer.parseInt(hexStr, 16); // hex → dec
        String binStr = Integer.toBinaryString(decimal); // dec → bin

        System.out.println("Hex '1F' → dziesiętny: " + decimal); // 31
        System.out.println("Hex '1F' → binarny: " + binStr);     // 11111

        String binaryStr = "1100"; // 12
        int fromBin = Integer.parseInt(binaryStr, 2);
        String hexFromBin = Integer.toHexString(fromBin);
        System.out.println("Binary '1100' → dec: " + fromBin);
        System.out.println("Binary '1100' → hex: " + hexFromBin);

        // Można również konwertować hex na oktal i odwrotnie przez konwersję pośrednią:
        int hexToOct = Integer.parseInt("A", 16); // A = 10
        String octFromHex = Integer.toOctalString(hexToOct);
        System.out.println("Hex 'A' → oktal: " + octFromHex);

        // =============================================================
        // 🔒 Debugowanie bitów i flag z wykorzystaniem systemów liczbowych
        // =============================================================
        int flags = 0b101101;
        System.out.println("Flagi binarne: " + Integer.toBinaryString(flags));
        System.out.println("Flagi hex: " + Integer.toHexString(flags));

        // Możemy łatwo sprawdzić stan konkretnego bitu np. 3-go:
        int mask = 1 << 2; // 000100 (bit nr 2)
        boolean isSet = (flags & mask) != 0;
        System.out.println("Czy 3-ci bit jest ustawiony? " + isSet);

        // Można też ustawić, wyczyścić lub przełączyć konkretne bity
        int setBit = flags | (1 << 1); // ustawiamy bit 1
        int clearBit = flags & ~(1 << 0); // zerujemy bit 0
        int toggleBit = flags ^ (1 << 3); // przełączamy bit 3

        System.out.println("Ustawiony bit 1: " + Integer.toBinaryString(setBit));
        System.out.println("Wyzerowany bit 0: " + Integer.toBinaryString(clearBit));
        System.out.println("Przełączony bit 3: " + Integer.toBinaryString(toggleBit));

        // =============================================================
        // ℹ️ Podsumowanie:
        // =============================================================
        System.out.println("\n--- PODSUMOWANIE ---");
        System.out.println("0b (binarnie): 0b1010 = " + 0b1010);
        System.out.println("0x (hex): 0xA5 = " + 0xA5);
        System.out.println("Oktalnie: 077 = " + 077);

        System.out.println("parseInt (binary): '1101' → " + Integer.parseInt("1101", 2));
        System.out.println("toBinaryString: 13 → '" + Integer.toBinaryString(13) + "'");
        System.out.println("toHexString: 255 → '" + Integer.toHexString(255) + "'");
        System.out.println("toOctalString: 64 → '" + Integer.toOctalString(64) + "'");
    }
}
