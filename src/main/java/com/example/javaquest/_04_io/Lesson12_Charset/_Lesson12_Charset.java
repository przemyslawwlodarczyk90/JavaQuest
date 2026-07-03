package com.example.javaquest._04_io.Lesson12_Charset;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson12_Charset {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 CHARSET – KODOWANIE ZNAKÓW W JAVA
         * ============================================================
         * Komputer zapisuje wszystko jako bajty. Charset (zestaw znaków)
         * to REGUŁA tłumaczenia między znakami (np. polskie "ą", "ę", "ł")
         * a bajtami zapisanymi na dysku / wysyłanymi przez sieć.
         *
         * Ten sam tekst zapisany w RÓŻNYCH kodowaniach da RÓŻNE bajty!
         * A jeśli odczytamy te bajty przy założeniu NIEWŁAŚCIWEGO
         * kodowania – dostaniemy "krzaki" (mojibake).
         *
         * java.nio.charset.StandardCharsets to gotowe stałe dla
         * najpopularniejszych kodowań:
         * - StandardCharsets.UTF_8    → uniwersalny standard (ZALECANY)
         * - StandardCharsets.ISO_8859_1 (Latin-1)
         * - StandardCharsets.US_ASCII
         * - StandardCharsets.UTF_16
         */

        System.out.println("UTF_8: " + StandardCharsets.UTF_8);
        System.out.println("ISO_8859_1: " + StandardCharsets.ISO_8859_1);
        System.out.println("US_ASCII: " + StandardCharsets.US_ASCII);

        /*
         * ============================================================
         * ⚠️ CHARSET.DEFAULTCHARSET() – DOMYŚLNE KODOWANIE PLATFORMY
         * ============================================================
         * Wiele starych metod Javy (np. konstruktor `new FileReader(file)`
         * bez podania Charset) używa DOMYŚLNEGO kodowania systemu operacyjnego.
         *
         * ❌ PROBLEM: to kodowanie RÓŻNI SIĘ między systemami!
         * - Windows (polski) → często Cp1250 lub Cp852
         * - Linux/macOS      → prawie zawsze UTF-8
         *
         * Ten sam kod, uruchomiony na dwóch różnych komputerach, może dać
         * RÓŻNE wyniki dla tego samego pliku – klasyczne źródło błędów
         * "u mnie działa"!
         */

        Charset platformDefault = Charset.defaultCharset();
        System.out.println("\nDomyślne kodowanie tej platformy: " + platformDefault);
        System.out.println("(Może to być co innego na innym komputerze/systemie!)");

        /*
         * ============================================================
         * ❌ PRZYKŁAD POPSUTEGO KODOWANIA (MOJIBAKE)
         * ============================================================
         * Zapiszemy tekst z polskimi znakami w kodowaniu UTF-8, a następnie
         * odczytamy te same bajty jakby były w ISO-8859-1 (Latin-1).
         * Efekt: znaki spoza ASCII zostaną błędnie zinterpretowane.
         */

        Path tempFile = Files.createTempFile("javaquest_lesson12_", ".txt");
        String polishText = "Zażółć gęślą jaźń";

        // Zapis w UTF-8
        Files.writeString(tempFile, polishText, StandardCharsets.UTF_8);
        System.out.println("\nZapisany tekst (UTF-8): " + polishText);

        // Odczyt PRAWIDŁOWY – tym samym kodowaniem co zapis
        String correctRead = Files.readString(tempFile, StandardCharsets.UTF_8);
        System.out.println("Odczyt jako UTF-8 (poprawnie): " + correctRead);

        // Odczyt BŁĘDNY – zakładamy ISO-8859-1, choć zapisane było jako UTF-8
        byte[] rawBytes = Files.readAllBytes(tempFile);
        String brokenRead = new String(rawBytes, StandardCharsets.ISO_8859_1);
        System.out.println("Odczyt jako ISO-8859-1 (❌ krzaki): " + brokenRead);
        // Przykładowy wynik: "ZaÅ¼Ã³Å‚Ä‡ gÄ™Å›lÄ… jaÅºÅ„"
        // Polskie znaki (wielobajtowe w UTF-8) zostały rozbite na osobne,
        // błędnie zinterpretowane bajty w Latin-1.

        System.out.println("Czy oba stringi są takie same? " + polishText.equals(brokenRead)); // false

        /*
         * ============================================================
         * 🔍 DLACZEGO TAK SIĘ DZIEJE?
         * ============================================================
         * W UTF-8 polskie znaki (np. "ż") są kodowane na 2 bajty.
         * ISO-8859-1 to kodowanie JEDNOBAJTOWE – każdy bajt traktuje jako
         * osobny, pojedynczy znak. Dlatego 2 bajty znaku "ż" w UTF-8
         * zostają odczytane jako 2 ODDZIELNE, dziwne znaki w Latin-1.
         */

        byte[] zBytes = "ż".getBytes(StandardCharsets.UTF_8);
        System.out.println("\n\"ż\" w UTF-8 to bajty: " + java.util.Arrays.toString(zBytes) + " (2 bajty!)");
        byte[] zBytesLatin1 = "ż".getBytes(StandardCharsets.ISO_8859_1);
        System.out.println("\"ż\" w ISO-8859-1 to bajty: " + java.util.Arrays.toString(zBytesLatin1) + " (1 bajt, ale inny znak!)");

        /*
         * ============================================================
         * ✅ JAK JAWNIE WYMUSZAĆ UTF-8 WSZĘDZIE
         * ============================================================
         * Zasada: ZAWSZE podawaj Charset jawnie, nigdy nie polegaj na
         * domyślnym kodowaniu platformy. Dotyczy to zarówno odczytu, jak
         * i zapisu – dla plików, strumieni, Readerów i Writerów.
         */

        // 1) Files.readString / writeString – jawny Charset jako argument
        Files.writeString(tempFile, "Nowa treść: śćż", StandardCharsets.UTF_8);
        String explicit = Files.readString(tempFile, StandardCharsets.UTF_8);
        System.out.println("\n✅ Files.readString z jawnym UTF-8: " + explicit);

        // 2) InputStreamReader / OutputStreamWriter – jawny Charset w konstruktorze
        try (var writer = new OutputStreamWriter(Files.newOutputStream(tempFile), StandardCharsets.UTF_8)) {
            writer.write("Zapis przez OutputStreamWriter z jawnym UTF-8: ĄĘŁ");
        }
        try (var reader = new InputStreamReader(Files.newInputStream(tempFile), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            System.out.println("✅ Odczyt przez InputStreamReader z jawnym UTF-8: " + sb);
        }

        // ❌ Unikaj wersji BEZ Charset – używa Charset.defaultCharset()!
        // new FileReader(file);                    // ❌ niejawne kodowanie platformy
        // new InputStreamReader(inputStream);      // ❌ to samo
        // Files.readString(path);                  // ⚠️ w Java 11+ to akurat domyślnie UTF-8,
        //                                           //    ale lepiej i tak podawać jawnie dla czytelności

        // Sprzątanie po demo
        Files.deleteIfExists(tempFile);
        System.out.println("\nPlik tymczasowy usunięty, istnieje? " + Files.exists(tempFile));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Charset (StandardCharsets.UTF_8 itd.) → reguła tłumaczenia znaki <-> bajty
         * Charset.defaultCharset() → zależy od SYSTEMU – źródło błędów "u mnie działa"
         * Zapis w jednym kodowaniu + odczyt w innym → "krzaki" (mojibake)
         * ✅ ZASADA: zawsze podawaj Charset JAWNIE:
         *   - Files.readString(path, charset) / Files.writeString(path, text, charset)
         *   - new InputStreamReader(in, charset) / new OutputStreamWriter(out, charset)
         * ✅ Domyślny wybór dla nowego kodu: StandardCharsets.UTF_8
         */
    }
}
