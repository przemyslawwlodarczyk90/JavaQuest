package com.example.javaquest._19_security_basics.Lesson16_PathTraversalAndFileUploadSecurity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class _Lesson16_PathTraversalAndFileUploadSecurity {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: PATH TRAVERSAL I BEZPIECZENSTWO UPLOADU PLIKOW ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z _07_servlets/Lesson18_FileUpload I _04_io/Lesson24_ZIP
         * ============================================================
         * Poprzednie lekcje pokazaly MECHANIKE uploadu plikow i
         * rozpakowywania ZIP-ow. Ta lekcja pokazuje, co sie stanie, gdy
         * NAZWA PLIKU (od uploadu) lub NAZWA WPISU W ZIP-ie pochodzi OD
         * NIEZAUFANEGO UZYTKOWNIKA i jest uzyta BEZPOSREDNIO do budowy
         * sciezki zapisu na dysku - klasyczny atak "path traversal"
         * (przejscie po katalogach) uzywajacy sekwencji `../`.
         */
        System.out.println("Path traversal = '../' w nazwie pliku pozwala zapisac/odczytac PLIK POZA zamierzonym katalogiem.");

        Path uploadsDir = Files.createTempDirectory("lesson16-uploads");
        System.out.println("\nBezpieczny katalog uploadu: " + uploadsDir);

        try {
            demonstrateVulnerablePathTraversal(uploadsDir);
            demonstrateSecurePathValidation(uploadsDir);
            demonstrateZipSlipVulnerability();
            demonstrateSecureZipExtraction();
        } finally {
            deleteRecursively(uploadsDir);
        }

        /*
         * ============================================================
         * 🔹 OBRONA: NORMALIZACJA + WERYFIKACJA WZGLEDEM KATALOGU BAZOWEGO
         * ============================================================
         * 1. Zbuduj docelowa sciezke: `baseDir.resolve(nazwaPliku)`.
         * 2. ZNORMALIZUJ ja: `.normalize()` (rozwiazuje `..`/`.`
         *    logicznie, BEZ dostepu do systemu plikow).
         * 3. SPRAWDZ, czy znormalizowana sciezka WCIAZ zaczyna sie od
         *    znormalizowanego katalogu bazowego: `.startsWith(baseDir)`.
         * 4. Jesli NIE - ODRZUC ZANIM cokolwiek zapiszesz/odczytasz.
         * DODATKOWO: NIGDY nie uzywaj nazwy pliku OD UZYTKOWNIKA
         * bezposrednio jako nazwy na dysku - najlepiej wygeneruj WLASNY,
         * losowy identyfikator (UUID), a oryginalna nazwe zachowaj
         * OSOBNO (np. w bazie danych) tylko do wyswietlenia.
         *
         * ============================================================
         * 🔹 "ZIP SLIP" - SPECYFICZNY PRZYPADEK PRZY ROZPAKOWYWANIU
         * ============================================================
         * Ten sam problem, ale WEWNATRZ archiwum ZIP - nazwa WPISU (nie
         * pliku uploadu) moze zawierac `../../../` lub byc sciezka
         * BEZWZGLEDNA. Rozpakowywanie "na slepo" (bez walidacji KAZDEGO
         * wpisu) pozwala atakujacemu spreparowac ZIP, ktory przy
         * rozpakowaniu NADPISZE dowolny plik POZA katalogiem docelowym
         * (np. plik konfiguracyjny, klucz SSH, skrypt startowy).
         */
        System.out.println("\nObrona: normalize() + startsWith(katalogBazowy) PRZED KAZDYM zapisem - dotyczy TEZ kazdego wpisu w ZIP (\"zip slip\").");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Path traversal wykorzystuje `../` w nazwie pliku/wpisu do
         *   "wyjscia" poza zamierzony katalog.
         * - "Zip slip" to TEN SAM problem zastosowany do wpisow archiwum
         *   ZIP podczas rozpakowywania.
         * - Obrona: `normalize()` + `startsWith(katalogBazowy)` PRZED
         *   KAZDYM zapisem/odczytem - zarowno dla pojedynczych plikow,
         *   jak i KAZDEGO wpisu w petli rozpakowywania ZIP.
         * - Dodatkowa dobra praktyka: generuj WLASNE nazwy plikow na
         *   dysku (UUID), NIGDY nie ufaj nazwie od uzytkownika do
         *   budowy sciezki.
         * - Ograniczaj TEZ rozmiar rozpakowywanych plikow ("zip bomb" -
         *   male archiwum rozpakowujace sie do GB danych) - osobny,
         *   pokrewny problem DoS.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    private static void demonstrateVulnerablePathTraversal(Path uploadsDir) throws IOException {
        System.out.println("\n=== PODATNY ZAPIS - NAZWA PLIKU OD UZYTKOWNIKA UZYTA WPROST ===");

        String maliciousFilename = "../../../evil-overwritten.txt";
        Path targetPath = uploadsDir.resolve(maliciousFilename); // BRAK walidacji - PODATNE

        Files.createDirectories(targetPath.getParent());
        Files.writeString(targetPath, "Tresc zapisana przez atak path traversal!");

        System.out.println("Nazwa pliku od 'uzytkownika': " + maliciousFilename);
        System.out.println("Rzeczywista sciezka zapisu: " + targetPath.normalize());
        System.out.println("-> plik zostal zapisany POZA katalogiem uploadu (" + uploadsDir + ") - atak sie powiodl!");

        Files.deleteIfExists(targetPath.normalize());
    }

    private static void demonstrateSecurePathValidation(Path uploadsDir) throws IOException {
        System.out.println("\n=== BEZPIECZNY ZAPIS - WALIDACJA WZGLEDEM KATALOGU BAZOWEGO ===");

        String maliciousFilename = "../../../evil-overwritten.txt";
        String legitimateFilename = "raport.pdf";

        System.out.println("Proba zapisu ZLOSLIWEJ nazwy: " + maliciousFilename);
        boolean maliciousAccepted = trySaveSecurely(uploadsDir, maliciousFilename, "zlosliwa tresc");
        System.out.println("Wynik: " + (maliciousAccepted ? "BLAD - zaakceptowano!" : "ODRZUCONO - sciezka wychodzi poza katalog bazowy."));

        System.out.println("\nProba zapisu POPRAWNEJ nazwy: " + legitimateFilename);
        boolean legitimateAccepted = trySaveSecurely(uploadsDir, legitimateFilename, "prawdziwa tresc raportu");
        System.out.println("Wynik: " + (legitimateAccepted ? "ZAAKCEPTOWANO - plik zapisany poprawnie." : "BLAD - odrzucono prawidlowy plik!"));
    }

    private static boolean trySaveSecurely(Path baseDir, String userProvidedFilename, String content) throws IOException {
        Path normalizedBase = baseDir.normalize();
        Path targetPath = normalizedBase.resolve(userProvidedFilename).normalize();

        if (!targetPath.startsWith(normalizedBase)) {
            return false; // ODRZUCONE - sciezka "uciekla" poza katalog bazowy
        }
        Files.writeString(targetPath, content);
        return true;
    }

    private static void demonstrateZipSlipVulnerability() throws IOException {
        System.out.println("\n=== ZIP SLIP - PODATNE ROZPAKOWYWANIE ===");

        Path maliciousZip = Files.createTempFile("lesson16-malicious", ".zip");
        Path extractDir = Files.createTempDirectory("lesson16-extract");
        Path outsideMarker = extractDir.getParent().resolve("zip-slip-victim.txt");

        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(maliciousZip))) {
            ZipEntry entry = new ZipEntry("../zip-slip-victim.txt"); // wpis "ucieka" poza katalog docelowy!
            zipOut.putNextEntry(entry);
            zipOut.write("Nadpisano przez atak zip slip!".getBytes());
            zipOut.closeEntry();
        }
        System.out.println("Zbudowano zlosliwe archiwum z wpisem: ../zip-slip-victim.txt");

        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(maliciousZip))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                Path targetPath = extractDir.resolve(entry.getName()); // BRAK walidacji - PODATNE
                Files.createDirectories(targetPath.getParent());
                Files.write(targetPath, zipIn.readAllBytes());
            }
        }

        boolean escaped = Files.exists(outsideMarker);
        System.out.println("Docelowy katalog rozpakowania: " + extractDir);
        System.out.println("Plik POZA katalogiem docelowym powstal: " + escaped + " (" + outsideMarker + ")");
        System.out.println("-> " + (escaped ? "ATAK SIE POWIODL - zip slip nadpisal plik poza katalogiem docelowym!" : "nieoczekiwanie nie powiodl sie na tym systemie."));

        Files.deleteIfExists(outsideMarker);
        deleteRecursively(extractDir);
        Files.deleteIfExists(maliciousZip);
    }

    private static void demonstrateSecureZipExtraction() throws IOException {
        System.out.println("\n=== ZIP SLIP - BEZPIECZNE ROZPAKOWYWANIE (WALIDACJA KAZDEGO WPISU) ===");

        Path maliciousZip = Files.createTempFile("lesson16-malicious2", ".zip");
        Path extractDir = Files.createTempDirectory("lesson16-extract2");

        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(maliciousZip))) {
            ZipEntry maliciousEntry = new ZipEntry("../zip-slip-victim2.txt");
            zipOut.putNextEntry(maliciousEntry);
            zipOut.write("Probe nadpisania!".getBytes());
            zipOut.closeEntry();

            ZipEntry legitimateEntry = new ZipEntry("dokument.txt");
            zipOut.putNextEntry(legitimateEntry);
            zipOut.write("Prawdziwa tresc dokumentu.".getBytes());
            zipOut.closeEntry();
        }

        Path normalizedExtractDir = extractDir.normalize();
        int extracted = 0;
        int rejected = 0;

        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(maliciousZip))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                Path targetPath = normalizedExtractDir.resolve(entry.getName()).normalize();
                if (!targetPath.startsWith(normalizedExtractDir)) {
                    System.out.println("ODRZUCONO wpis '" + entry.getName() + "' - probuje wyjsc poza katalog docelowy!");
                    rejected++;
                    continue;
                }
                Files.createDirectories(targetPath.getParent());
                Files.write(targetPath, zipIn.readAllBytes());
                System.out.println("ZAAKCEPTOWANO i rozpakowano wpis: " + entry.getName());
                extracted++;
            }
        }

        System.out.println("Podsumowanie: " + extracted + " wpis(y) rozpakowane bezpiecznie, " + rejected + " odrzucone jako proba zip slip.");

        deleteRecursively(extractDir);
        Files.deleteIfExists(maliciousZip);
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }
        try (var stream = Files.walk(path)) {
            stream.sorted(java.util.Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException ignored) {
                    // sprzatanie plikow tymczasowych demo - blad tutaj nie wplywa na wynik lekcji
                }
            });
        }
    }
}
