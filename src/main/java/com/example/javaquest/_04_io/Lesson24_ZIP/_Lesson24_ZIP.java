package com.example.javaquest._04_io.Lesson24_ZIP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class _Lesson24_ZIP {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 ZIP – ARCHIWA W JAVIE (java.util.zip)
         * ============================================================
         * JDK ma WBUDOWANE wsparcie dla formatu ZIP – nie potrzeba żadnej
         * dodatkowej biblioteki. Kluczowe klasy:
         *
         * - ZipOutputStream – strumień do TWORZENIA archiwum .zip
         * - ZipEntry        – reprezentuje JEDEN plik/wpis wewnątrz archiwum
         * - ZipInputStream  – strumień do ODCZYTU/ROZPAKOWANIA archiwum .zip
         *
         * Ogólny schemat zapisu:
         * 1. otwórz ZipOutputStream na strumieniu docelowym (np. FileOutputStream)
         * 2. dla każdego pliku: putNextEntry(new ZipEntry(nazwa)) → zapisz bajty → closeEntry()
         * 3. zamknij ZipOutputStream (close() – najlepiej przez try-with-resources)
         */

        Path tempDir = Files.createTempDirectory("zip_demo");
        Path zipFile = tempDir.resolve("archiwum.zip");

        // Kilka przykładowych plików tekstowych do spakowania
        Map<String, String> filesToZip = new LinkedHashMap<>();
        filesToZip.put("notatka1.txt", "Tresc pierwszej notatki.\nDruga linia.");
        filesToZip.put("notatka2.txt", "Zakupy: mleko, chleb, jajka.");
        filesToZip.put("podfolder/notatka3.txt", "Plik wewnatrz podfolderu w archiwum.");

        /*
         * ============================================================
         * 🔹 TWORZENIE ARCHIWUM – ZipOutputStream + ZipEntry
         * ============================================================
         */

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            for (Map.Entry<String, String> entry : filesToZip.entrySet()) {
                ZipEntry zipEntry = new ZipEntry(entry.getKey());
                zos.putNextEntry(zipEntry);
                zos.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
                zos.closeEntry();
            }
        }

        System.out.println("Utworzono archiwum: " + zipFile);
        System.out.println("Rozmiar archiwum: " + Files.size(zipFile) + " bajtów");

        /*
         * ============================================================
         * 🔍 ODCZYT / ROZPAKOWANIE ARCHIWUM – ZipInputStream
         * ============================================================
         * getNextEntry() zwraca kolejny ZipEntry (albo null, gdy koniec archiwum).
         * Zawartość wpisu czytamy jak zwykły InputStream – aż do -1.
         * ⚠️ ZipInputStream trzeba czytać do końca strumienia bajtów wpisu
         *    zanim przejdziemy do closeEntry()/kolejnego getNextEntry().
         */

        Map<String, String> extracted = new LinkedHashMap<>();

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String content = readAllBytesFromEntry(zis);
                extracted.put(entry.getName(), content);
                System.out.println("Rozpakowano wpis: " + entry.getName()
                        + " (" + content.length() + " znakow)");
                zis.closeEntry();
            }
        }

        /*
         * ============================================================
         * ✅ WERYFIKACJA – CZY ROZPAKOWANA ZAWARTOŚĆ SIĘ ZGADZA?
         * ============================================================
         */

        System.out.println("\n--- Weryfikacja zawartości ---");
        boolean allMatch = true;
        for (Map.Entry<String, String> original : filesToZip.entrySet()) {
            String extractedContent = extracted.get(original.getKey());
            boolean matches = original.getValue().equals(extractedContent);
            allMatch &= matches;
            System.out.println(original.getKey() + " -> zgodność: " + matches);
        }
        System.out.println(allMatch ? "✅ Cała zawartość zgodna z oryginałem!"
                : "❌ Wykryto niezgodność!");

        /*
         * ============================================================
         * 📌 UWAGI PRAKTYCZNE
         * ============================================================
         * - Nazwa ZipEntry może zawierać "/" – tworzy to strukturę podfolderów
         *   wewnątrz archiwum (jak wyżej: "podfolder/notatka3.txt").
         * - Do rozpakowywania NA DYSK (a nie tylko do pamięci jak wyżej) trzeba
         *   samodzielnie tworzyć brakujące katalogi (Files.createDirectories)
         *   przed zapisem pliku z danego wpisu.
         * - ⚠️ Uważaj na "Zip Slip" – jeśli nazwa wpisu zawiera "../", rozpakowanie
         *   może próbować zapisać plik POZA docelowym katalogiem. W kodzie
         *   produkcyjnym zawsze waliduj/normalizuj docelową ścieżkę pliku.
         */

        // Sprzątanie – usuwamy plik zip i katalog tymczasowy
        Files.deleteIfExists(zipFile);
        Files.deleteIfExists(tempDir);
        System.out.println("\nPliki tymczasowe usunięte.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Tworzenie archiwum:
         * ZipOutputStream + dla każdego pliku: putNextEntry(new ZipEntry(nazwa))
         * → write(bajty) → closeEntry()
         *
         * Odczyt/rozpakowanie:
         * ZipInputStream + pętla po getNextEntry() (aż null), czytanie bajtów
         * bieżącego wpisu, closeEntry() na koniec każdego wpisu.
         *
         * Wszystko wbudowane w JDK (java.util.zip) – żadnej dodatkowej zależności.
         */
    }

    private static String readAllBytesFromEntry(InputStream entryStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        entryStream.transferTo(buffer);
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
