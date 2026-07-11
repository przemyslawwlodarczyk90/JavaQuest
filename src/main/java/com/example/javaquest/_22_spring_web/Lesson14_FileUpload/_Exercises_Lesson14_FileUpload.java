package com.example.javaquest._22_spring_web.Lesson14_FileUpload;

public class _Exercises_Lesson14_FileUpload {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainMultipartFilePurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_07_servlets/Lesson18`): wyjasnij,
         * jak `MultipartFile` UPRASZCZA upload wzgledem SUROWEGO
         * Servlet API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementOwnSingleFileUploadEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY endpoint uploadu 1 pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementOwnMultipleFileUploadEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj WLASNY endpoint uploadu WIELU plikow
         * (`List<MultipartFile>`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ImplementFileMetadataInspection {
        /*
         * 🧪 Zadanie 4:
         * Zwroc METADANE wgranego pliku (nazwa/rozmiar/typ) BEZ
         * odczytu tresci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementEmptyFileDetection {
        /*
         * 🧪 Zadanie 5:
         * Wyslij PUSTY plik - zweryfikuj `file.isEmpty()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementFileExtensionValidation {
        /*
         * 🧪 Zadanie 6:
         * Zwaliduj ROZSZERZENIE pliku (np. TYLKO `.txt`/`.csv`) -
         * odrzuc INNE Z czytelnym komunikatem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementFileSizeLimitConfiguration {
        /*
         * 🧪 Zadanie 7:
         * Ustaw WLASNY limit `spring.servlet.multipart.max-file-size` -
         * zweryfikuj `MaxUploadSizeExceededException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementSaveUploadedFileToTempDirectory {
        /*
         * 🧪 Zadanie 8:
         * Zapisz WGRANY plik DO `Files.createTempDirectory(...)` -
         * zwroc SCIEZKE zapisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementMissingFileParamErrorHandling {
        /*
         * 🧪 Zadanie 9:
         * Wyslij zadanie BEZ pliku - zapisz DOKLADNY status i komunikat
         * bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyMultipartNeedsPostNotGet {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego upload plikow WYMAGA
         * POST/PUT (`multipart/form-data`), NIE GET.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementFileUploadWithAdditionalFormFields {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj endpoint PRZYJMUJACY plik + DODATKOWE pola
         * formularza (`@RequestParam String description`) NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementFileContentTypeValidation {
        /*
         * 🧪 Zadanie 12:
         * Zwaliduj `getContentType()` (np. TYLKO `image/*`) -
         * odrzuc NIEDOZWOLONE typy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementPathTraversalProtectionOnFilename {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_19_security_basics/Lesson16` - zabezpiecz zapis
         * PRZED `../../../etc/passwd` W nazwie pliku (znormalizuj
         * sciezke, sprawdz `startsWith`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementUniqueFilenameGeneration {
        /*
         * 🧪 Zadanie 14:
         * Wygeneruj UNIKALNA nazwe zapisu (UUID + oryginalne
         * rozszerzenie) - unikaj NADPISYWANIA istniejacych plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementFileDownloadEndpointForUploadedFile {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z Lesson06 - zaimplementuj endpoint POBIERANIA
         * WCZESNIEJ wgranego pliku (`ResponseEntity<byte[]>` +
         * `Content-Disposition`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCsvFileParsingAfterUpload {
        /*
         * 🧪 Zadanie 16:
         * Powiaz z `_04_io/Lesson23_CSV` - sparsuj WGRANY plik CSV I
         * zwroc LICZBE wierszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementZipFileUploadAndExtraction {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_04_io/Lesson24_ZIP` I
         * `_19_security_basics/Lesson16` (zip slip) - wgraj ZIP I
         * BEZPIECZNIE go rozpakuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementProgressTrackingForLargeUploads {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala (dokumentacja): sprawdz, JAK sledzic POSTEP
         * DUZEGO uploadu W Spring MVC (streaming/`InputStream`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureUploadThroughputForVariousFileSizes {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas uploadu plikow O ROSNACYM rozmiarze (1KB/100KB/
         * 1MB) - jak SKALUJE SIE czas?
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildFileUploadPatternsCheatSheet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste) "sciage" wzorcow uploadu (pojedynczy/
         * wielokrotny/walidacja/limit/bezpieczny zapis).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementVirusScanSimulationBeforeSave {
        /*
         * 🧪 Zadanie 21:
         * Zasymuluj SKANOWANIE antywirusowe (np. sprawdzenie "sygnatury"
         * testowej EICAR) PRZED zapisem pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementImageResizingAfterUpload {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj PRZESKALOWANIE wgranego obrazu (`java.awt.image`
         * lub `ImageIO`) DO STALEGO rozmiaru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCloudStorageAbstractionForUploads {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_17_architecture/Lesson12_PortsAndAdapters` -
         * zaimplementuj port `FileStoragePort` Z 2 adapterami (lokalny
         * dysk vs symulowana chmura).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementConcurrentUploadStressTest {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_05_multithreading` - wyslij 10+ jednoczesnych
         * uploadow (WIELE watkow) - zweryfikuj brak UTRATY/USZKODZENIA
         * danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementUploadAuditTrail {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj KAZDY
         * upload (uzytkownik/nazwa/rozmiar/czas) DO dziennika audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementRateLimitingForUploadEndpoint {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_18_rest_api/Lesson16` - ogranicz CZESTOTLIWOSC
         * uploadow NA klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementChunkedResumableUploadSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj upload PODZIELONY NA CZESCI (chunked/resumable) -
         * SCALENIE fragmentow PO otrzymaniu WSZYSTKICH.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementFileTypeDetectionByMagicBytes {
        /*
         * 🧪 Zadanie 28:
         * Zweryfikuj RZECZYWISTY typ pliku PO "magic bytes" (poczatkowe
         * bajty), NIE TYLKO po deklarowanym `Content-Type` (KTORY klient
         * MOZE SFALSZOWAC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareMultipartWithBase64JsonUploadTradeoffs {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: porownaj `multipart/form-data` Z uploadem
         * PRZEZ `@RequestBody` (plik zakodowany Base64 W JSON) - jakie
         * SA kompromisy ROZMIARU/PROSTOTY?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureFileUploadServiceCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY, bezpieczny serwis uploadu - walidacja typu
         * (Zadanie 12) + magic bytes (Zadanie 28) + unikalne nazwy
         * (Zadanie 14) + ochrona path traversal (Zadanie 13) + audyt
         * (Zadanie 25) - jeden spojny system.
         */
        public static void main(String[] args) { }
    }
}
