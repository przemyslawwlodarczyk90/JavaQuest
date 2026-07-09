package com.example.javaquest._13_libraries.Lesson24_ApachePoiReadingAndStyling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class _Lesson24_ApachePoiReadingAndStyling {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 24: APACHE POI - ODCZYT I ZAAWANSOWANE STYLOWANIE ===");

        /*
         * ============================================================
         * 📦 KONTYNUACJA LEKCJI 23
         * ============================================================
         * W poprzedniej lekcji nauczyliśmy się TWORZYĆ i ZAPISYWAĆ pliki
         * .xlsx (Workbook -> Sheet -> Row -> Cell, różne typy danych,
         * formuły, prosty CellStyle dla dat). Ta lekcja domyka temat
         * Apache POI od DRUGIEJ strony:
         * - jak OTWORZYĆ i ODCZYTAĆ istniejący plik .xlsx,
         * - jak poprawnie rozróżniać CellType podczas odczytu,
         * - jak przeliczyć formuły po odczycie (FormulaEvaluator),
         * - jak stylować komórki bardziej zaawansowanie: obramowanie,
         *   kolor tła, własny format liczb, scalanie komórek.
         *
         * Najlepszy sposób na naukę odczytu to zrobić PEŁNY cykl: zapisz
         * plik (technikami z Lekcji 23 + nowe stylowanie), zamknij go,
         * otwórz PONOWNIE i odczytaj z powrotem - dokładnie to robi ta
         * lekcja poniżej, budując arkusz "Faktura".
         */

        Path outputFile = demonstrateWritingStyledWorkbook();

        demonstrateWorkbookFactoryGenericOpen(outputFile);
        demonstrateReadingCellTypes(outputFile);
        demonstrateFormulaEvaluationOnRead(outputFile);
        demonstrateReadingMergedRegionsAndStyles(outputFile);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - WorkbookFactory.create(File/InputStream) to UNIWERSALNY sposób
         *   otwierania plików Excela - sam rozpoznaje .xls vs .xlsx i
         *   zwraca odpowiednią implementację (HSSFWorkbook/XSSFWorkbook).
         *   Gdy z góry WIESZ, że plik to .xlsx, można też wprost użyć
         *   "new XSSFWorkbook(inputStream)".
         * - Cell.getCellType() zwraca CellType (STRING/NUMERIC/BOOLEAN/
         *   FORMULA/BLANK/ERROR) - odczyt WARTOŚCI musi użyć metody
         *   pasującej do typu (getStringCellValue()/getNumericCellValue()/
         *   getBooleanCellValue()/getCellFormula()), inaczej POI rzuci
         *   wyjątek (np. wywołanie getStringCellValue() na komórce
         *   NUMERIC to błąd).
         * - Komórka z formułą PRZECHOWUJE tekst formuły (getCellFormula()),
         *   ale NIE jej wynik - żeby dostać policzoną wartość, trzeba użyć
         *   FormulaEvaluator (workbook.getCreationHelper()
         *   .createFormulaEvaluator()) i evaluator.evaluate(cell), co
         *   zwraca CellValue z wynikiem odpowiedniego typu.
         * - Zaawansowane stylowanie to zawsze ten sam wzorzec: utwórz
         *   CellStyle (workbook.createCellStyle()), skonfiguruj go
         *   (obramowanie przez setBorderXxx(BorderStyle...), tło przez
         *   setFillForegroundColor(...) + setFillPattern(SOLID_FOREGROUND),
         *   czcionkę przez osobny obiekt Font), a na końcu przypisz go do
         *   KONKRETNEJ komórki przez cell.setCellStyle(styl). WAŻNE: style
         *   należy tworzyć RAZ i re-używać dla wielu komórek (a nie
         *   tworzyć nowy CellStyle w każdej iteracji pętli) - Excel ma
         *   limit unikalnych stylów w pliku.
         * - DataFormat pozwala nadać komórce dowolny format wyświetlania
         *   liczby (np. "#,##0.00 zl") - dane fizycznie NADAL są liczbą
         *   (double), zmienia się tylko sposób ich prezentacji w Excelu.
         * - Scalanie komórek: sheet.addMergedRegion(new CellRangeAddress
         *   (wierszOd, wierszDo, kolumnaOd, kolumnaDo)) - wartość wpisuje
         *   się TYLKO do komórki lewej-górnej scalonego obszaru, pozostałe
         *   komórki w regionie odczytują się jako CellType.BLANK.
         *   sheet.getMergedRegions()/getNumMergedRegions() pozwalają
         *   odczytać scalone obszary z powrotem po otwarciu pliku.
         */

        System.out.println("\n=== KONIEC LEKCJI 24 ===");
    }

    /*
     * ============================================================
     * 🔹 ZAPIS: ARKUSZ "FAKTURA" ZE STYLAMI + FORMUŁĄ + SCALONĄ KOMÓRKĄ
     * ============================================================
     * Ten arkusz posłuży jako "materiał wejściowy" do wszystkich
     * kolejnych demonstracji odczytu w tej lekcji - dlatego celowo
     * zawiera WSZYSTKO naraz: tytuł w scalonej komórce, kolorowy
     * nagłówek tabeli (czcionka + tło + obramowanie), komórki z
     * niestandardowym formatem liczb i formuły (mnożenie w wierszu,
     * suma na dole).
     */
    private static Path demonstrateWritingStyledWorkbook() throws IOException {
        System.out.println("\n=== Zapis: arkusz 'Faktura' ze stylami (obramowanie, tlo, format liczb, scalenie) i formula ===");

        Path tempDir = Files.createTempDirectory("poi_read_demo");
        Path outputFile = tempDir.resolve("faktura.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Faktura");
            CreationHelper creationHelper = workbook.getCreationHelper();

            // Czcionka naglowka - pogrubiona, biala.
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            // Styl naglowka - czcionka + kolorowe tlo + cienkie obramowanie ze wszystkich stron.
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Styl komorek z kwotami - wlasny format liczb ("#,##0.00 zl") + dolne obramowanie.
            CellStyle currencyStyle = workbook.createCellStyle();
            DataFormat dataFormat = creationHelper.createDataFormat();
            currencyStyle.setDataFormat(dataFormat.getFormat("#,##0.00 \"zl\""));
            currencyStyle.setBorderBottom(BorderStyle.THIN);

            // Tytul faktury w scalonej komorce A1:D1.
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Faktura nr FV/2026/07/01");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

            // Naglowek tabeli (wiersz 1).
            String[] headers = {"Produkt", "Ilosc", "Cena jednostkowa", "Wartosc"};
            Row headerRow = sheet.createRow(1);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(headerStyle);
            }

            // Pozycje faktury (wiersze 2-4) - kolumna D to FORMULA (Ilosc * Cena).
            Object[][] items = {
                    {"Monitor 27\"", 2.0, 899.0},
                    {"Klawiatura mechaniczna", 3.0, 350.0},
                    {"Mysz bezprzewodowa", 5.0, 120.0}
            };

            int rowIndex = 2;
            for (Object[] item : items) {
                Row row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue((String) item[0]);
                row.createCell(1).setCellValue((double) item[1]);

                Cell priceCell = row.createCell(2);
                priceCell.setCellValue((double) item[2]);
                priceCell.setCellStyle(currencyStyle);

                int excelRow = rowIndex + 1; // POI jest 0-based, formuly Excela 1-based
                Cell valueCell = row.createCell(3);
                valueCell.setCellFormula("B" + excelRow + "*C" + excelRow);
                valueCell.setCellStyle(currencyStyle);
                rowIndex++;
            }

            // Wiersz "RAZEM" - formula SUM ponad wszystkimi wartosciami pozycji.
            int firstDataExcelRow = 3; // wiersz 2 (0-based) = wiersz 3 w Excelu
            int lastDataExcelRow = rowIndex; // rowIndex (0-based) = ostatni wiersz danych + 1 w Excelu
            Row totalRow = sheet.createRow(rowIndex);
            totalRow.createCell(0).setCellValue("RAZEM");
            Cell totalCell = totalRow.createCell(3);
            totalCell.setCellFormula("SUM(D" + firstDataExcelRow + ":D" + lastDataExcelRow + ")");
            totalCell.setCellStyle(currencyStyle);

            try (FileOutputStream fileOut = new FileOutputStream(outputFile.toFile())) {
                workbook.write(fileOut);
            }
        }

        System.out.println("Zapisano plik: " + outputFile + " (rozmiar: " + Files.size(outputFile) + " B)");
        return outputFile;
    }

    /*
     * ============================================================
     * 🔍 WorkbookFactory.create(...) - UNIWERSALNE OTWIERANIE PLIKU
     * ============================================================
     * W odróżnieniu od "new XSSFWorkbook(...)" (który ZAKŁADA, że plik
     * jest .xlsx), WorkbookFactory.create(File/InputStream) SAM rozpozna
     * format pliku (stary binarny .xls vs nowoczesny .xlsx) i zwróci
     * odpowiednią implementację Workbook. To bezpieczniejszy wybór, gdy
     * format pliku nie jest z góry pewny (np. plik wgrany przez
     * użytkownika w aplikacji webowej).
     */
    private static void demonstrateWorkbookFactoryGenericOpen(Path file) throws IOException {
        System.out.println("\n=== WorkbookFactory.create(...) - uniwersalne otwieranie .xls/.xlsx ===");

        File rawFile = file.toFile();
        try (Workbook workbook = WorkbookFactory.create(rawFile)) {
            System.out.println("Otwarto plik: " + file.getFileName()
                    + ", liczba arkuszy: " + workbook.getNumberOfSheets()
                    + ", implementacja Workbook: " + workbook.getClass().getSimpleName());
        }
    }

    /*
     * ============================================================
     * 🔹 ODCZYT KOMOREK - ROZROZNIANIE CellType
     * ============================================================
     * Każda komórka "wie", jaki typ danych przechowuje - Cell.getCellType()
     * zwraca enum CellType. Odczyt WARTOŚCI musi użyć metody dopasowanej
     * do tego typu, dlatego switch po CellType jest standardowym wzorcem
     * bezpiecznego odczytu arkusza o nieznanej z góry zawartości.
     */
    private static void demonstrateReadingCellTypes(Path file) throws IOException {
        System.out.println("\n=== Odczyt komorek - rozroznianie CellType (STRING/NUMERIC/FORMULA/BLANK...) ===");

        try (InputStream in = new FileInputStream(file.toFile());
                Workbook workbook = new XSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(1);
            System.out.println("Naglowek tabeli (wiersz 1):");
            for (Cell cell : headerRow) {
                System.out.println("  - kolumna " + cell.getColumnIndex() + ": '" + cell.getStringCellValue()
                        + "' (typ=" + cell.getCellType() + ")");
            }

            Row firstItemRow = sheet.getRow(2);
            System.out.println("Pierwszy wiersz danych (wiersz 2) - odczyt wg CellType:");
            for (Cell cell : firstItemRow) {
                String description = switch (cell.getCellType()) {
                    case STRING -> "STRING: " + cell.getStringCellValue();
                    case NUMERIC -> "NUMERIC: " + cell.getNumericCellValue();
                    case BOOLEAN -> "BOOLEAN: " + cell.getBooleanCellValue();
                    case FORMULA -> "FORMULA (surowy tekst): " + cell.getCellFormula();
                    case BLANK -> "BLANK (pusta komorka)";
                    default -> "INNY TYP: " + cell.getCellType();
                };
                System.out.println("  - kolumna " + cell.getColumnIndex() + " -> " + description);
            }
        }
    }

    /*
     * ============================================================
     * 🔍 FormulaEvaluator - PONOWNE OBLICZENIE FORMUL PO ODCZYCIE
     * ============================================================
     * Komórka typu FORMULA fizycznie przechowuje sam TEKST formuły
     * (np. "SUM(D3:D5)"), a nie jej wynik - Excel przelicza formuły przy
     * otwarciu pliku, ale POI tego automatycznie nie robi. Żeby dostać
     * policzoną wartość w Javie, trzeba jawnie użyć FormulaEvaluator.
     */
    private static void demonstrateFormulaEvaluationOnRead(Path file) throws IOException {
        System.out.println("\n=== FormulaEvaluator - obliczenie formuly odczytanej z pliku ===");

        try (InputStream in = new FileInputStream(file.toFile());
                Workbook workbook = new XSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            int lastRowIndex = sheet.getLastRowNum();
            Row totalRow = sheet.getRow(lastRowIndex);
            Cell totalCell = totalRow.getCell(3);

            System.out.println("Formula w komorce D" + (lastRowIndex + 1) + ": " + totalCell.getCellFormula());

            CellValue evaluated = evaluator.evaluate(totalCell);
            System.out.println("Wynik po przeliczeniu: " + evaluated.getNumberValue()
                    + " (typ wyniku: " + evaluated.getCellType() + ")");
        }
    }

    /*
     * ============================================================
     * 🔹 ODCZYT SCALONYCH KOMOREK I STYLU
     * ============================================================
     * sheet.getMergedRegions() zwraca listę CellRangeAddress opisujących
     * wszystkie scalone obszary arkusza. Styl komórki (obramowanie, tło,
     * format) też jest odczytywalny po ponownym otwarciu pliku - dokładnie
     * te same wartości, które zapisaliśmy przy tworzeniu pliku.
     */
    private static void demonstrateReadingMergedRegionsAndStyles(Path file) throws IOException {
        System.out.println("\n=== Odczyt scalonych obszarow (merged regions) i stylu komorek ===");

        try (InputStream in = new FileInputStream(file.toFile());
                Workbook workbook = new XSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheetAt(0);

            System.out.println("Liczba scalonych obszarow: " + sheet.getNumMergedRegions());
            for (CellRangeAddress region : sheet.getMergedRegions()) {
                System.out.println("  - scalony obszar: " + region.formatAsString());
            }

            Row headerRow = sheet.getRow(1);
            Cell firstHeaderCell = headerRow.getCell(0);
            CellStyle style = firstHeaderCell.getCellStyle();
            System.out.println("Styl pierwszej komorki naglowka ('" + firstHeaderCell.getStringCellValue() + "'):");
            System.out.println("  - kolor tla (indeks): " + style.getFillForegroundColor());
            System.out.println("  - wzor wypelnienia: " + style.getFillPattern());
            System.out.println("  - obramowanie dolne: " + style.getBorderBottom());

            // Komorka WEWNATRZ scalonego regionu (inna niz lewa-gorna) odczytuje sie jako BLANK.
            Cell mergedSecondCell = sheet.getRow(0).getCell(1);
            System.out.println("Druga komorka scalonego tytulu (B1) - typ: "
                    + (mergedSecondCell == null ? "null (komorka nigdy nie zostala utworzona)"
                            : mergedSecondCell.getCellType()));
        }
    }
}
