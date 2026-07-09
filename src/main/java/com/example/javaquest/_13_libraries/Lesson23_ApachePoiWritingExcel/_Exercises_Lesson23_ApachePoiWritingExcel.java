package com.example.javaquest._13_libraries.Lesson23_ApachePoiWritingExcel;

public class _Exercises_Lesson23_ApachePoiWritingExcel {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateWorkbookSheetAndSingleStringCell {
        /*
         * 🧪 Zadanie 1:
         * Utwórz XSSFWorkbook wewnątrz try-with-resources, utwórz arkusz "Test",
         * utwórz wiersz 0 i komórkę A0 z wartością String "Witaj Excelu". Wypisz
         * wartość komórki metodą getStringCellValue().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HeaderRowWithMultipleStringCells {
        /*
         * 🧪 Zadanie 2:
         * Utwórz arkusz "Zamowienia" i w wierszu 0 zapisz nagłówek: "Id",
         * "Klient", "Data", "Kwota" (4 kolumny, w pętli po tablicy String[]).
         * Wypisz każdą utworzoną komórkę nagłówka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NumericCellsFromIntAndDouble {
        /*
         * 🧪 Zadanie 3:
         * Utwórz wiersz z 3 komórkami liczbowymi: int 42 (rzutowany na double),
         * long 1000L (rzutowany na double) i double 3.14 - wszystkie przez
         * setCellValue(double). Odczytaj każdą komórkę getNumericCellValue() i
         * wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BooleanCells {
        /*
         * 🧪 Zadanie 4:
         * Utwórz wiersz z 4 komórkami boolean reprezentującymi odpowiedzi
         * true/false/true/true na pytania ankiety. Odczytaj każdą metodą
         * getBooleanCellValue() i wypisz jako "TAK"/"NIE".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LocalDateCellWithDateStyle {
        /*
         * 🧪 Zadanie 5:
         * Utwórz komórkę z wartością LocalDate.now(), nadaj jej CellStyle z
         * formatem "dd-MM-yyyy" (przez CreationHelper.createDataFormat()).
         * Wypisz cell.getLocalDateTimeCellValue() oraz potwierdź w komentarzu, że
         * fizycznie komórka przechowuje liczbę (dni od 1900-01-01).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MixedTypesRowLikeLessonDemo {
        /*
         * 🧪 Zadanie 6:
         * Odtwórz wzorzec z lekcji: JEDEN wiersz z 4 komórkami różnych typów
         * (String, double, boolean, LocalDate ze stylem daty). W pętli po
         * komórkach wiersza wypisz indeks kolumny i cell.getCellType() dla
         * każdej z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MultipleRowsFromListOfProducts {
        /*
         * 🧪 Zadanie 7:
         * Utwórz listę 6 produktów (rekord: String name, double price). W
         * pętli utwórz wiersz dla każdego produktu (kolumna 0 = nazwa String,
         * kolumna 1 = cena double), zaczynając od wiersza 1 (wiersz 0 to
         * nagłówek "Nazwa"/"Cena").
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SaveWorkbookToTempFileAndPrintSize {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj dowolny prosty arkusz (kilka wierszy danych), zapisz go do
         * pliku w katalogu z Files.createTempDirectory("cwiczenie08") metodą
         * workbook.write(FileOutputStream) wewnątrz try-with-resources. Wypisz
         * pełną ścieżkę pliku oraz jego rozmiar w bajtach (Files.size(...)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WorkbookWithMultipleSheets {
        /*
         * 🧪 Zadanie 9:
         * Utwórz JEDEN Workbook z TRZEMA arkuszami: "Styczen", "Luty", "Marzec"
         * (workbook.createSheet(...) trzykrotnie), każdy z jedną prostą komórką
         * identyfikującą arkusz. Wypisz workbook.getNumberOfSheets() oraz nazwę
         * każdego arkusza przez workbook.getSheetName(i).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IterateCellsInRowPrintingTypeAndIndex {
        /*
         * 🧪 Zadanie 10:
         * Utwórz wiersz z 5 komórkami różnych typów (dowolny miks String/
         * double/boolean). Użyj pętli for-each (Row implementuje
         * Iterable<Cell>) do wypisania dla każdej komórki: getColumnIndex() oraz
         * getCellType().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EmployeeTableWithHeaderDataAndSumFormula {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz pełny wzorzec arkusza "Pracownicy" z lekcji, ale dla WŁASNEGO
         * zestawu 6 pracowników (imię/nazwisko, stanowisko, data zatrudnienia
         * jako LocalDate, pensja jako double). Dodaj wiersz "RAZEM" z formułą
         * SUM obejmującą wszystkie wiersze danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_EvaluateFormulaWithFormulaEvaluator {
        /*
         * 🧪 Zadanie 12:
         * Na arkuszu z Zadania 11 pobierz FormulaEvaluator przez
         * workbook.getCreationHelper().createFormulaEvaluator() i wywołaj
         * evaluator.evaluate(formulaCell).getNumberValue() na komórce z formułą
         * SUM. Porównaj wynik z ręcznie policzoną sumą pensji (Stream/pętla) i
         * wypisz, czy się zgadzają.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteRowsInLoopFromStudentGradesList {
        /*
         * 🧪 Zadanie 13:
         * Utwórz listę 10 studentów (rekord: String name, double[] grades - 3
         * oceny każdy). W pętli zapisz wiersz na studenta: kolumna 0 = imię,
         * kolumny 1-3 = poszczególne oceny (double), kolumna 4 = średnia
         * (policzona w Javie, NIE formułą Excela).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TwoSheetsWithIndependentData {
        /*
         * 🧪 Zadanie 14:
         * W jednym Workbooku utwórz DWA arkusze: "Produkty" (nazwa, cena) i
         * "Klienci" (imię, email) - każdy z własnym nagłówkiem i co najmniej 4
         * wierszami danych, całkowicie niezależnymi od siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CustomNumberFormatForCurrency {
        /*
         * 🧪 Zadanie 15:
         * Utwórz CellStyle z formatem "#,##0.00" (przez
         * CreationHelper.createDataFormat().getFormat(...)) i zastosuj go do
         * kolumny z cenami w arkuszu z co najmniej 5 wierszami produktów. W
         * komentarzu zapisz różnicę między wartością "surową" (double) a tym, co
         * zobaczy użytkownik Excela po otwarciu pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AverageFormula {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj arkusz z kolumną liczbową (co najmniej 6 wartości) i dodaj
         * komórkę z formułą "AVERAGE(...)" obejmującą cały zakres. Oblicz wynik
         * FormulaEvaluatorem i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PerRowMultiplicationFormula {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj arkusz "Koszyk" z kolumnami Ilosc (B) i CenaJednostkowa (C) dla
         * 5 pozycji - w kolumnie D każdego wiersza zapisz FORMUŁĘ mnożącą
         * odpowiednie komórki tego wiersza (np. "B2*C2"). Oblicz każdą formułę
         * evaluatorem i wypisz razem z wartościami wejściowymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareFileSizeForDifferentRowCounts {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę generującą i zapisującą arkusz z N wierszami prostych
         * danych (String + double). Wywołaj ją dla N = 10, 100, 1000 (do trzech
         * osobnych plików tymczasowych) i wypisz rozmiar KAŻDEGO pliku w
         * bajtach - zaobserwuj (w komentarzu) jak rozmiar rośnie wraz z N.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DynamicSheetNamesPerMonthInLoop {
        /*
         * 🧪 Zadanie 19:
         * W pętli po tablicy 12 polskich nazw miesięcy utwórz PO JEDNYM
         * arkuszu na miesiąc (workbook.createSheet(nazwaMiesiaca)) z jedną
         * komórką "Raport za " + nazwaMiesiaca. Po pętli wypisz
         * workbook.getNumberOfSheets() i przejrzyj wszystkie nazwy arkuszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CombinedSumAndCountFormulasReport {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj arkusz z listą transakcji (co najmniej 8 wierszy, kolumna
         * Kwota). Dodaj DWIE komórki podsumowania: jedną z formułą "SUM(...)",
         * drugą z formułą "COUNT(...)" - oblicz obie evaluatorem i wypisz jako
         * "Liczba transakcji: X, Suma: Y".
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericExportMethodForListOfRecords {
        /*
         * 🧪 Zadanie 21:
         * Napisz reużywalną metodę statyczną exportToExcel(String sheetName,
         * String[] headers, List<Object[]> rows, Path outputFile), która
         * buduje Workbook, zapisuje nagłówek i wiersze (obsługując String i
         * double przez instanceof) i zapisuje plik. Użyj jej do wyeksportowania
         * DWÓCH różnych zestawów danych (np. produkty i pracownicy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PerformanceTestLargeDataset {
        /*
         * 🧪 Zadanie 22:
         * Zmierz (System.nanoTime()) czas zapisania arkusza z 10 000 wierszy (3
         * kolumny: String, double, boolean, generowane w pętli) do pliku
         * tymczasowego. Wypisz czas w milisekundach oraz rozmiar wynikowego
         * pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_HandleNullValuesGracefullyWhenWriting {
        /*
         * 🧪 Zadanie 23:
         * Utwórz listę rekordów z CELOWO brakującymi danymi (np. null zamiast
         * String). Napisz metodę pomocniczą safeSetCellValue(Cell, String) -
         * jeśli wartość jest null, ustaw pustego stringa zamiast wywoływać
         * setCellValue(null) (co powoduje pustą/niepoprawną komórkę) - zapisz
         * arkusz i zweryfikuj brak wyjątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_IndexSheetListingOtherSheetNames {
        /*
         * 🧪 Zadanie 24:
         * Utwórz Workbook z arkuszami "Q1", "Q2", "Q3", "Q4" (dane dowolne) oraz
         * DODATKOWYM arkuszem "Spis tresci" utworzonym jako PIERWSZY, w którym
         * w kolejnych wierszach wypisujesz nazwy pozostałych czterech arkuszy
         * jako zwykły tekst (bez hiperłączy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_InvoiceWithSubtotalTaxAndTotalFormulas {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj arkusz "Faktura" z co najmniej 4 pozycjami (nazwa, ilość, cena
         * jednostkowa, wartość = formuła ilość*cena). Dodaj TRZY wiersze
         * podsumowania z formułami: "Suma czesciowa" (SUM wartości pozycji),
         * "VAT 23%" (formuła referencyjna do komórki sumy częściowej * 0.23),
         * "Do zaplaty" (suma dwóch poprzednich komórek formułą). Oblicz
         * wszystko evaluatorem i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_VerifyInMemoryValueImmediatelyAfterSetCellValue {
        /*
         * 🧪 Zadanie 26:
         * Zapisz komórkę setCellValue("Test") i OD RAZU (bez zamykania/zapisu
         * pliku) odczytaj ją getStringCellValue() w tym samym bloku
         * try-with-resources - wypisz, że wartość jest natychmiast dostępna w
         * pamięci. Powtórz dla double i boolean, potwierdzając ten sam wzorzec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MonthlyReportWorkbookWithPerSheetTotals {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj Workbook z arkuszami "Styczen", "Luty", "Marzec", każdy z
         * własną listą 5 sprzedaży (double) i formułą SUM na dole arkusza. Dodaj
         * dodatkowy arkusz "Podsumowanie", w którym w Javie (evaluatorem) obliczasz
         * sumę z każdego miesiąca i zapisujesz JAKO WARTOŚĆ (nie formułę
         * odwołującą się do innego arkusza) trzy komórki podsumowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CrossSheetFormulaReference {
        /*
         * 🧪 Zadanie 28:
         * Rozszerz Zadanie 27: zamiast przepisywać wartości w Javie, w arkuszu
         * "Podsumowanie" użyj PRAWDZIWEJ formuły odwołującej się do innego
         * arkusza, np. "Styczen!D7" (dokładna komórka z sumą stycznia). Oblicz
         * wartość evaluatorem i porównaj z wynikiem z Zadania 27.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GenericPipelineFromListOfMapsWithMixedTypes {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj List<Map<String, Object>> (każda mapa reprezentuje jeden
         * wiersz, wartości mogą być String/Double/Boolean/LocalDate). Napisz
         * metodę zapisującą taką strukturę do arkusza, dobierając odpowiedni
         * setCellValue(...) na podstawie instanceof dla KAŻDEJ wartości (w tym
         * osobny CellStyle dla LocalDate).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneSalesReportWorkbook {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "Raport sprzedazy.xlsx": arkusz "Sprzedaz" (co
         * najmniej 10 transakcji: data jako LocalDate ze stylem, produkt,
         * ilość, cena, wartość = formuła), arkusz "Produkty" (lista unikalnych
         * produktów z cenami) oraz wiersz/arkusz podsumowania z formułami SUM i
         * AVERAGE. Zapisz do pliku tymczasowego i wypisz pełną ścieżkę oraz
         * rozmiar - to ma być samodzielny, kompletny eksport gotowy do
         * otworzenia w Excelu.
         */
        public static void main(String[] args) { }
    }
}
