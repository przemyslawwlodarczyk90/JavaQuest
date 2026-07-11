package com.example.javaquest._19_security_basics.Lesson16_PathTraversalAndFileUploadSecurity;

public class _Exercises_Lesson16_PathTraversalAndFileUploadSecurity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainPathTraversalInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, jak sekwencja `../`
         * w nazwie pliku pozwala "wyjsc" poza zamierzony katalog.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateSafeUploadDirectory {
        /*
         * 🧪 Zadanie 2:
         * Utworz tymczasowy katalog "uploadow" - wypisz jego sciezke
         * bezwzgledna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SaveFileWithVulnerableConcatenation {
        /*
         * 🧪 Zadanie 3:
         * Zapisz plik uzywajac `baseDir.resolve(nazwaOdUzytkownika)` BEZ
         * zadnej walidacji - przetestuj z normalna nazwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExploitWithDotDotSlashSequence {
        /*
         * 🧪 Zadanie 4:
         * Uzyj nazwy pliku `../../../poza-katalogiem.txt` w metodzie z
         * Zadania 3 - zweryfikuj, GDZIE naprawde zostal zapisany plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NormalizeAndCheckStartsWith {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj walidacje: `resolve().normalize()` + sprawdzenie
         * `startsWith(znormalizowanyKatalogBazowy)` - odrzuc zly
         * przypadek z Zadania 4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VerifyLegitimateFilenamesStillWork {
        /*
         * 🧪 Zadanie 6:
         * Zweryfikuj, ze walidacja z Zadania 5 NADAL akceptuje normalne
         * nazwy plikow (np. "raport.pdf", "zdjecie_2024.jpg").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_GenerateRandomFilenameInsteadOfUserProvided {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj generowanie WLASNEJ nazwy pliku (UUID) zamiast
         * uzywania nazwy od uzytkownika - zachowaj oryginalna nazwe
         * OSOBNO (np. w Mapie) tylko do wyswietlenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainZipSlipInOwnWords {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, czym jest "zip slip" i czym rozni sie
         * od zwyklego path traversal (co konkretnie zawiera `../` w tym
         * przypadku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BuildZipWithMaliciousEntryName {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj (przez `ZipOutputStream`) archiwum ZIP z wpisem o nazwie
         * `../evil.txt`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExtractZipVulnerablyAndObserveEscape {
        /*
         * 🧪 Zadanie 10:
         * Rozpakuj ZIP z Zadania 9 BEZ walidacji - zweryfikuj, ze plik
         * powstal POZA katalogiem docelowym.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_FixZipExtractionWithPathValidation {
        /*
         * 🧪 Zadanie 11:
         * Napraw rozpakowywanie z Zadania 10 - zwaliduj KAZDY wpis
         * (`normalize()` + `startsWith()`) przed zapisem, odrzuc
         * niebezpieczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ValidateFileExtensionAllowlist {
        /*
         * 🧪 Zadanie 12:
         * Dodaj DODATKOWA walidacje - biala lista dozwolonych rozszerzen
         * plikow (np. tylko ".pdf", ".jpg", ".png") - odrzuc inne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ValidateFileSizeLimit {
        /*
         * 🧪 Zadanie 13:
         * Dodaj limit rozmiaru pliku (np. max 5 MB) - odrzuc wieksze
         * przed zapisem na dysk.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DetectNullByteInjectionInFilename {
        /*
         * 🧪 Zadanie 14:
         * Zweryfikuj (i odrzuc) nazwe pliku zawierajaca bajt zerowy
         * (`\0`) - historyczna technika omijania walidacji rozszerzenia w
         * niektorych jezykach/systemach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementZipBombProtectionWithSizeLimit {
        /*
         * 🧪 Zadanie 15:
         * Dodaj limit LACZNEGO rozmiaru rozpakowanych danych (np. max
         * 100 MB) podczas rozpakowywania ZIP - przerwij z bledem, jesli
         * przekroczony (ochrona przed "zip bomb").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementZipEntryCountLimit {
        /*
         * 🧪 Zadanie 16:
         * Dodaj limit LICZBY wpisow w archiwum (np. max 1000) - odrzuc
         * archiwa z wieksza liczba (kolejna ochrona przed DoS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestAbsolutePathAsFilename {
        /*
         * 🧪 Zadanie 17:
         * Przetestuj nazwe pliku BEDACA sciezka BEZWZGLEDNA (np.
         * "C:\\Windows\\evil.txt" lub "/etc/evil.txt") - zweryfikuj, ze
         * walidacja z Zadania 5/11 RowNIEZ ja odrzuca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementSecureFileUploadEndpoint {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj `HttpServer` z endpointem uploadu laczacym
         * WSZYSTKIE walidacje z tej lekcji (sciezka, rozszerzenie,
         * rozmiar, losowa nazwa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LogRejectedUploadAttemptsForMonitoring {
        /*
         * 🧪 Zadanie 19:
         * Dodaj logowanie (na konsole) kazdej odrzuconej proby uploadu z
         * powodem odrzucenia - zapowiedz Lesson19 (Secure Logging).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareValidationApproachesAcrossOperatingSystems {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij, dlaczego walidacja sciezek MUSI
         * uwzgledniac ROZNICE miedzy systemami (Windows uzywa `\`, ma
         * dyski `C:\`, zna nazwy zarezerwowane jak `CON`/`NUL`; Linux
         * uzywa `/`) - dlaczego API `java.nio.file.Path` pomaga to
         * ABSTRAHOWAC.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildReusableSecureFileStorageService {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj klase `SecureFileStorage` z metodami `save`/`load`/
         * `delete`, KAZDA z pelna walidacja sciezki, rozszerzenia i
         * rozmiaru - uzyj jej we wszystkich miejscach z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementSecureZipExtractionUtility {
        /*
         * 🧪 Zadanie 22:
         * Zaprojektuj klase `SecureZipExtractor` z pelna ochrona (path
         * walidacja + limit rozmiaru + limit liczby wpisow) - przetestuj
         * na kilku roznych zlosliwych archiwach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestSymlinkBasedPathTraversalConceptually {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: opisz DODATKOWY wariant ataku - dowiazanie
         * symboliczne (symlink) WEWNATRZ katalogu uploadu wskazujace poza
         * niego - dlaczego sama walidacja `normalize()`+`startsWith()`
         * MOZE nie wystarczyc (podpowiedz: `toRealPath()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRealPathValidationAgainstSymlinks {
        /*
         * 🧪 Zadanie 24:
         * Wzmocnij walidacje uzywajac `Path.toRealPath()` (rozwiazuje
         * TEZ symlinki) zamiast samego `normalize()` - wyjasnij roznice w
         * komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BenchmarkValidationOverheadForLargeUploads {
        /*
         * 🧪 Zadanie 25:
         * Zmierz narzut czasowy pelnej walidacji (sciezka + rozszerzenie
         * + rozmiar) dla 10 000 uploadow - skomentuj, czy jest istotny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ScanUploadedFileContentForMagicBytes {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj weryfikacje RZECZYWISTEGO typu pliku po "magic
         * bytes" (pierwsze bajty pliku, np. PNG zaczyna sie od
         * `\x89PNG`) zamiast ufania samemu rozszerzeniu - wykryj plik
         * `.jpg` bedacy w rzeczywistosci czyms innym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementQuarantineForUnverifiedUploads {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj przeplyw "kwarantanny" - przeslany plik trafia
         * najpierw do OSOBNEGO katalogu, dopiero po weryfikacji (rozmiar,
         * typ, magic bytes) jest PRZENOSZONY do wlasciwego katalogu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AuditExistingFileHandlingCodeForTraversalRisk {
        /*
         * 🧪 Zadanie 28:
         * Napisz metode "audytujaca" liste fragmentow kodu (symulowanych
         * jako Stringi wzorcow) pod katem uzycia niezwalidowanej nazwy
         * pliku bezposrednio w `resolve()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementDefenseInDepthForFileHandling {
        /*
         * 🧪 Zadanie 29:
         * Polacz WSZYSTKIE techniki: walidacja sciezki (toRealPath),
         * biala lista rozszerzen, limit rozmiaru, magic bytes, losowa
         * nazwa, kwarantanna - zaprojektuj (opisz) kompletna architekture
         * bezpiecznego uploadu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureUploadAndExtractionDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletne demo laczace bezpieczny upload pojedynczych
         * plikow (Zadanie 21) i bezpieczne rozpakowywanie ZIP (Zadanie
         * 22) - zweryfikuj co najmniej 5 scenariuszy (prawidlowy plik,
         * path traversal, zip slip, zbyt duzy plik, zla nazwa
         * rozszerzenia) z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
