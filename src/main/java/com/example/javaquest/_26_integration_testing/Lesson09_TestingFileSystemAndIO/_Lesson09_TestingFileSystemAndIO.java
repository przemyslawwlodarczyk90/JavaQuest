package com.example.javaquest._26_integration_testing.Lesson09_TestingFileSystemAndIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson09_TestingFileSystemAndIO {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 9: Testowanie operacji na systemie plikow ===");

        /*
         * ============================================================
         * 📦 3 REGULY testowania kodu operujacego NA plikach
         * ============================================================
         * 1. NIGDY nie pisz DO stalej sciezki NA dysku uzytkownika -
         *    ZAWSZE `Files.createTempDirectory(...)`/`createTempFile(...)`
         *    (ten sam wzorzec co `_04_io` w calym kursie) - test
         *    URUCHOMIONY ROWNOLEGLE (LUB DWA RAZY POD RZAD) NIE MOZE
         *    kolidowac O TA SAMA sciezke.
         * 2. ZAWSZE SPRZATAJ PO SOBIE (`try/finally` LUB
         *    `Files.deleteIfExists`/rekurencyjne usuniecie katalogu) -
         *    inaczej katalog TYMCZASOWY ROSNIE Z KAZDYM uruchomieniem
         *    testow (powiazanie Z Lesson02: zlozonosc setup/teardown).
         * 3. TESTUJ TEZ SCIEZKI BLEDOW specyficzne DLA I/O: BRAK
         *    uprawnien, NIEISTNIEJACY plik, PELNY dysk (trudne DO
         *    zasymulowania, ALE WARTO chociaz przetestowac BRAK
         *    pliku/katalogu).
         */
        System.out.println("3 reguly: tymczasowe sciezki (NIGDY stale), ZAWSZE sprzataj (try/finally), testuj TEZ sciezki bledow I/O.");

        demonstrateTempDirectoryIsolation();
        demonstrateRealFileErrorPaths();
        demonstrateFileBasedExportImport();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Files.createTempDirectory("prefix")` - IZOLOWANY katalog
         *   NA test, AUTOMATYCZNIE UNIKALNY (system operacyjny
         *   dokleja losowy sufiks).
         * - Rekurencyjne usuwanie: `Files.walk(dir).sorted(Comparator.
         *   reverseOrder()).forEach(...)` (pliki PRZED katalogami).
         * - Test I/O SPRAWDZA REALNE zachowanie systemu operacyjnego
         *   (kodowanie znakow, uprawnienia, limity sciezki) - CZEGO
         *   test jednostkowy Z ZAMOCKOWANYM `FileSystem`
         *   (`_25_unit_testing/Lesson16`) W OGOLE NIE sprawdza.
         * - Powiazanie Z CALYM `_04_io` - TA lekcja UCZY, JAK
         *   TESTOWAC kod Z TAMTEGO rozdzialu, NIE powtarza JEGO API.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateTempDirectoryIsolation() throws IOException {
        System.out.println("\n--- Izolowany katalog tymczasowy NA test ---");
        Path testDir = Files.createTempDirectory("lesson09-isolated");
        try {
            Path file = testDir.resolve("dane.csv");
            Files.writeString(file, "id,nazwa\n1,Klawiatura\n2,Mysz");

            List<String> lines = Files.readAllLines(file);
            assertThat(lines).hasSize(3);
            assertThat(lines.get(0)).isEqualTo("id,nazwa");
            System.out.println("Zapisano/odczytano PRAWDZIWY plik w izolowanym katalogu: " + testDir.getFileName());
        } finally {
            deleteRecursively(testDir);
            assertThat(Files.exists(testDir)).isFalse();
            System.out.println("Katalog tymczasowy POPRAWNIE usuniety po tescie.");
        }
    }

    private static void demonstrateRealFileErrorPaths() {
        System.out.println("\n--- Sciezki bledow I/O - plik/katalog, ktorego NIE MA ---");
        Path nonExistent = Path.of(System.getProperty("java.io.tmpdir"), "lesson09-na-pewno-nie-istnieje-" + System.nanoTime());

        assertThatThrownBy(() -> Files.readAllLines(nonExistent))
                .isInstanceOf(java.nio.file.NoSuchFileException.class);
        System.out.println("Odczyt NIEISTNIEJACEGO pliku POPRAWNIE rzucil NoSuchFileException.");

        assertThatThrownBy(() -> Files.createDirectory(Path.of(nonExistent.toString(), "podkatalog")))
                .isInstanceOf(IOException.class);
        System.out.println("Utworzenie podkatalogu W NIEISTNIEJACYM katalogu nadrzednym POPRAWNIE rzucilo IOException.");
    }

    private static void demonstrateFileBasedExportImport() throws IOException {
        System.out.println("\n--- Integracja: eksport wyniku 'zapytania' do pliku i ponowny odczyt ---");
        record Product(int id, String name, double price) {
        }
        List<Product> products = List.of(
                new Product(1, "Klawiatura", 199.99),
                new Product(2, "Mysz", 49.99));

        Path exportFile = Files.createTempFile("lesson09-export", ".csv");
        try {
            List<String> csvLines = products.stream()
                    .map(p -> p.id() + "," + p.name() + "," + p.price())
                    .toList();
            Files.write(exportFile, csvLines);

            List<String> reimported = Files.readAllLines(exportFile);
            assertThat(reimported).hasSize(2);
            assertThat(reimported.get(0)).isEqualTo("1,Klawiatura,199.99");
            System.out.println("Wyeksportowano " + products.size() + " produkty do pliku CSV i POPRAWNIE ponownie odczytano.");
        } finally {
            Files.deleteIfExists(exportFile);
        }
    }

    private static void deleteRecursively(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return;
        }
        try (var paths = Files.walk(directory)) {
            paths.sorted(java.util.Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}
