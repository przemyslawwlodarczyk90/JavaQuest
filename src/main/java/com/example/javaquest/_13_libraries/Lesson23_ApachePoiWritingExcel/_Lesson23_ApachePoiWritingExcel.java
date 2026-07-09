package com.example.javaquest._13_libraries.Lesson23_ApachePoiWritingExcel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class _Lesson23_ApachePoiWritingExcel {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 23: APACHE POI - ZAPIS DO EXCELA (.xlsx) ===");

        /*
         * ============================================================
         * 📦 CZYM JEST APACHE POI
         * ============================================================
         * Apache POI ("Poor Obfuscation Implementation") to biblioteka Javy
         * do CZYTANIA i ZAPISYWANIA formatów biurowych Microsoftu:
         * - Excel   -> moduł "poi"/"poi-ooxml" (arkusze kalkulacyjne) - TEN rozdział
         * - Word    -> moduł "poi-scratchpad"/"poi-ooxml" (dokumenty .docx)
         * - PowerPoint -> moduł "poi-ooxml" (prezentacje .pptx)
         *
         * W tej i następnej lekcji skupiamy się WYŁĄCZNIE na Excelu - to
         * najczęstszy przypadek użycia POI w praktyce (eksport raportów,
         * import danych z arkuszy od klienta, generowanie faktur itp.).
         *
         * Zależności (już w pom.xml tego projektu):
         *   org.apache.poi:poi          - rdzeń biblioteki + stary format .xls
         *   org.apache.poi:poi-ooxml    - obsługa nowoczesnego formatu .xlsx
         */

        /*
         * ============================================================
         * 🔹 FORMAT .xlsx = ZIP + XML
         * ============================================================
         * Format .xlsx (Office Open XML) to tak naprawdę PACZKA ZIP
         * zawierająca zestaw plików XML (arkusze, style, metadane) -
         * dokładnie ten sam mechanizm ZIP, który poznałeś w
         * `_04_io.Lesson24_ZIP` (java.util.zip).
         *
         * 🎓 Ciekawostka: możesz wziąć DOWOLNY plik .xlsx, zmienić jego
         * rozszerzenie na .zip i otworzyć zwykłym programem do archiwów -
         * zobaczysz w środku m.in. "xl/worksheets/sheet1.xml". Apache POI
         * robi dokładnie to samo pod spodem (pakuje/rozpakowuje ZIP i
         * parsuje XML), tylko chowa tę złożoność za wygodnym API obiektowym
         * (Workbook/Sheet/Row/Cell), żeby nie trzeba było ręcznie składać XML.
         *
         * Starszy format .xls (Excel 97-2003) to zupełnie INNY, binarny
         * format - obsługuje go klasa HSSFWorkbook (zamiast XSSFWorkbook).
         * W praktyce niemal zawsze pracujesz z .xlsx, więc ta i następna
         * lekcja skupiają się na XSSFWorkbook - HSSFWorkbook wspominamy
         * tylko po to, żebyś wiedział, że istnieje (np. gdy trafisz na
         * bardzo stary plik .xls od klienta).
         */
        System.out.println("\n=== .xlsx = paczka ZIP z plikami XML w srodku (tak jak _04_io/Lesson24_ZIP) ===");
        System.out.println("XSSFWorkbook -> .xlsx (nowoczesny, uzywamy go).  HSSFWorkbook -> .xls (stary, tylko wspomniany).");

        /*
         * ============================================================
         * 🔹 WORKBOOK, SHEET, ROW, CELL - PODSTAWOWA HIERARCHIA
         * ============================================================
         * - Workbook (interfejs) / XSSFWorkbook (implementacja .xlsx)
         *   - reprezentuje CAŁY plik Excela, implementuje Closeable
         * - Sheet   - pojedynczy arkusz w skoroszycie (workbook.createSheet("Nazwa"))
         * - Row     - pojedynczy wiersz arkusza (sheet.createRow(indeksWiersza))
         * - Cell    - pojedyncza komórka wiersza (row.createCell(indeksKolumny))
         *
         * WAŻNE: Workbook implementuje Closeable -> ZAWSZE try-with-resources,
         * inaczej wygenerowany plik może zostać "obcięty" (nie w pełni
         * zapisany) albo zasoby (pliki tymczasowe POI) nie zostaną zwolnione.
         */
        try (Workbook demoWorkbook = new XSSFWorkbook()) {
            Sheet demoSheet = demoWorkbook.createSheet("Demo");
            Row demoRow = demoSheet.createRow(0);
            Cell demoCell = demoRow.createCell(0);
            demoCell.setCellValue("Pierwsza komorka");
            System.out.println("\n=== Utworzono Workbook -> Sheet 'Demo' -> Row 0 -> Cell A1 = '"
                    + demoCell.getStringCellValue() + "' ===");
        }

        /*
         * ============================================================
         * 🔍 RÓŻNE TYPY DANYCH W KOMÓRCE
         * ============================================================
         * Cell.setCellValue(...) ma wiele przeciążeń - POI samo dobiera
         * wewnętrzny CellType na podstawie typu argumentu:
         *
         * - setCellValue(String)     -> tekst (CellType.STRING)
         * - setCellValue(double)     -> liczba (CellType.NUMERIC) - UWAGA:
         *   Excel NIE rozróżnia int/long/double na poziomie komórki, wszystko
         *   to "liczba" - int/long trzeba jawnie rzutować na double
         * - setCellValue(boolean)    -> wartość logiczna (CellType.BOOLEAN)
         * - setCellValue(LocalDate)  -> data - fizycznie to TEŻ liczba
         *   (liczba dni od 1900-01-01), ale wyświetlana jako data DZIĘKI
         *   odpowiedniemu CellStyle z formatem daty (patrz niżej)
         * - setCellFormula(String)   -> formuła Excela, np. "SUM(D2:D6)"
         *   (BEZ znaku "=" na początku - POI dodaje go automatycznie)
         */
        try (Workbook typesWorkbook = new XSSFWorkbook()) {
            Sheet typesSheet = typesWorkbook.createSheet("Typy");
            Row row = typesSheet.createRow(0);

            row.createCell(0).setCellValue("Tekst jako String");
            row.createCell(1).setCellValue(1234.56); // liczba (double)
            row.createCell(2).setCellValue(true);    // boolean

            // Data - żeby wyświetlała się jako data, a nie "surowa" liczba,
            // trzeba nadać komórce CellStyle z formatem daty (CreationHelper).
            CreationHelper creationHelper = typesWorkbook.getCreationHelper();
            CellStyle dateStyle = typesWorkbook.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));
            Cell dateCell = row.createCell(3);
            dateCell.setCellValue(LocalDate.of(2026, 7, 9));
            dateCell.setCellStyle(dateStyle);

            System.out.println("\n=== Wiersz z roznymi typami: STRING, NUMERIC, BOOLEAN, DATE (sformatowana) ===");
            for (Cell cell : row) {
                System.out.println(" - kolumna " + cell.getColumnIndex() + ": typ=" + cell.getCellType());
            }
        }

        /*
         * ============================================================
         * 🔹 PRZYKŁAD: ARKUSZ "Pracownicy" (nagłówek + dane + formuła)
         * ============================================================
         * Budujemy pełny, realistyczny arkusz: nagłówek w wierszu 0,
         * dane pracowników w kolejnych wierszach, oraz formułę SUM
         * liczącą sumę wszystkich pensji.
         */
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Pracownicy");

            String[] headers = {"Imie i nazwisko", "Stanowisko", "Data zatrudnienia", "Pensja"};
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                headerRow.createCell(col).setCellValue(headers[col]);
            }

            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));

            Object[][] employees = {
                    {"Jan Kowalski", "Programista", LocalDate.of(2021, 3, 15), 9500.0},
                    {"Anna Nowak", "Analityk", LocalDate.of(2022, 6, 1), 8200.0},
                    {"Piotr Wisniewski", "Kierownik zespolu", LocalDate.of(2019, 11, 20), 13000.0},
                    {"Ewa Zielinska", "Tester", LocalDate.of(2023, 1, 10), 7300.0},
                    {"Marek Lewandowski", "Programista", LocalDate.of(2020, 9, 5), 9800.0}
            };

            int rowIndex = 1;
            for (Object[] employee : employees) {
                Row row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue((String) employee[0]);
                row.createCell(1).setCellValue((String) employee[1]);

                Cell dateCell = row.createCell(2);
                dateCell.setCellValue((LocalDate) employee[2]);
                dateCell.setCellStyle(dateStyle);

                row.createCell(3).setCellValue((double) employee[3]);
                rowIndex++;
            }

            // Wiersz podsumowania z formułą Excela SUM(D2:D6).
            int lastDataRow = rowIndex; // 1-based numer ostatniego wiersza z danymi w Excelu (rowIndex to indeks 0-based NASTĘPNEGO wiersza)
            Row totalRow = sheet.createRow(rowIndex);
            totalRow.createCell(0).setCellValue("RAZEM");
            Cell formulaCell = totalRow.createCell(3);
            formulaCell.setCellFormula("SUM(D2:D" + lastDataRow + ")");

            // Formuła jest zapisana w pliku, ale POI jej nie "liczy" samo z siebie -
            // FormulaEvaluator pozwala pobrać wyliczoną wartość już teraz (do println),
            // Excel i tak przeliczy ją ponownie przy otwarciu pliku.
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            double totalSalary = evaluator.evaluate(formulaCell).getNumberValue();
            System.out.println("\n=== Arkusz 'Pracownicy': " + employees.length
                    + " wierszy danych, suma pensji (formula SUM) = " + totalSalary + " ===");

            /*
             * ============================================================
             * 🔍 ZAPIS DO PLIKU .xlsx
             * ============================================================
             * workbook.write(OutputStream) zapisuje CAŁY skoroszyt do
             * strumienia - zwykle FileOutputStream. Zgodnie z zasadami tego
             * kursu (patrz _11_buildtools) plik lądowuje w katalogu
             * TYMCZASOWYM, żeby nie zaśmiecać repozytorium.
             */
            Path tempDir = Files.createTempDirectory("poi_demo");
            Path outputFile = tempDir.resolve("pracownicy.xlsx");
            try (FileOutputStream fileOut = new FileOutputStream(outputFile.toFile())) {
                workbook.write(fileOut);
            }
            System.out.println("=== Zapisano plik: " + outputFile + " (rozmiar: " + Files.size(outputFile) + " B) ===");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Apache POI = biblioteka do czytania/zapisu formatów Office;
         *   moduły "poi" + "poi-ooxml" obsługują Excel (.xlsx).
         * - .xlsx to paczka ZIP z plikami XML - XSSFWorkbook chowa tę
         *   złożoność za wygodnym API. HSSFWorkbook to odpowiednik dla
         *   starego formatu .xls.
         * - Hierarchia: Workbook -> Sheet -> Row -> Cell.
         * - Cell.setCellValue(...) ma przeciążenia dla String/double/
         *   boolean/LocalDate - a setCellFormula(...) zapisuje formułę
         *   Excela (bez znaku "=").
         * - Daty wymagają CellStyle z DataFormat, żeby wyświetlały się
         *   jako data, a nie "surowa" liczba.
         * - Workbook implementuje Closeable -> zawsze try-with-resources,
         *   a zapis na dysk to workbook.write(OutputStream) wewnątrz
         *   try-with-resources na FileOutputStream.
         * - W następnej lekcji (Lesson24): odczyt istniejącego pliku
         *   .xlsx oraz stylowanie komórek (czcionka, tło, obramowanie,
         *   autosize kolumn).
         */

        System.out.println("\n=== KONIEC LEKCJI 23 ===");
    }
}
