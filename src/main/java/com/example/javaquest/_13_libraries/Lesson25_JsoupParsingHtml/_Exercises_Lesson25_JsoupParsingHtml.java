package com.example.javaquest._13_libraries.Lesson25_JsoupParsingHtml;

public class _Exercises_Lesson25_JsoupParsingHtml {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ParseSimpleHtmlString {
        /*
         * 🧪 Zadanie 1:
         * Sparsuj String "<html><body><h1>Witaj</h1></body></html>" przez
         * Jsoup.parse(...) i wypisz doc.title() oraz tekst elementu <h1>
         * (doc.selectFirst("h1").text()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadDocumentTitle {
        /*
         * 🧪 Zadanie 2:
         * Sparsuj HTML "<html><head><title>Moja strona</title></head>
         * <body></body></html>" i wypisz doc.title(). Zmień tytuł metodą
         * doc.title("Nowy tytul") i wypisz ponownie, żeby zobaczyć zmianę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SelectAllParagraphs {
        /*
         * 🧪 Zadanie 3:
         * Sparsuj HTML zawierający 4 akapity <p> z różnym tekstem. Użyj
         * doc.select("p") i wypisz tekst KAŻDEGO znalezionego akapitu w
         * pętli, z numerem porządkowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SelectById {
        /*
         * 🧪 Zadanie 4:
         * Sparsuj HTML z elementem <div id="footer">Stopka strony</div>
         * (wśród innych elementów). Użyj selektora "#footer" i wypisz
         * text() znalezionego elementu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SelectByClass {
        /*
         * 🧪 Zadanie 5:
         * Sparsuj HTML z 3 elementami <span class="price"> o wartościach
         * "19.99", "29.99", "39.99". Użyj selektora ".price" i wypisz
         * wszystkie znalezione wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReadHrefAttribute {
        /*
         * 🧪 Zadanie 6:
         * Sparsuj HTML z linkiem <a href="https://codenga.pl">Codenga</a>.
         * Znajdź go selektorem "a" i wypisz zarówno text() jak i
         * attr("href").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SelectFirstVsSelect {
        /*
         * 🧪 Zadanie 7:
         * Sparsuj HTML z 5 elementami <li>. Porównaj doc.selectFirst("li")
         * (jeden Element) z doc.select("li") (Elements, lista) - wypisz
         * typ zwracany przez obie metody (getClass().getSimpleName()) i
         * ich zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountElementsBySelector {
        /*
         * 🧪 Zadanie 8:
         * Sparsuj HTML "strony sklepu" z 6 elementami <div class="product">.
         * Policz je selektorem ".product" (Elements.size()) i wypisz wynik
         * w zdaniu "Znaleziono X produktow".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ReadInnerHtmlVsOuterHtml {
        /*
         * 🧪 Zadanie 9:
         * Sparsuj HTML z <div id="box"><b>Pogrubiony</b> tekst</div>.
         * Wypisz element.html() (zawartość wewnątrz div) ORAZ
         * element.outerHtml() (cały div razem z tagiem) - porównaj różnicę
         * w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ParseTolerantlyBrokenHtml {
        /*
         * 🧪 Zadanie 10:
         * Sparsuj CELOWO niepoprawny HTML
         * "<div><p>Ala ma kota<p>Kot ma Ale</div>" (brak zamknięcia <p>).
         * Wypisz doc.body().html() po "naprawie" przez Jsoup i policz ile
         * tagów <p> Jsoup faktycznie utworzył.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DirectChildSelector {
        /*
         * 🧪 Zadanie 11:
         * Sparsuj HTML z <div class="menu"><ul><li>A</li></ul>
         * <li>B (poza ul)</li></div>. Porównaj wynik selektora
         * "div.menu > li" (tylko BEZPOŚREDNIE dzieci div) z
         * "div.menu li" (WSZYSCY potomkowie) - wypisz liczność obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AttributeExistsSelector {
        /*
         * 🧪 Zadanie 12:
         * Sparsuj HTML z 4 linkami <a>, z których TYLKO 2 mają atrybut
         * "target" (np. target="_blank"). Użyj selektora "a[target]" i
         * wypisz tylko te 2 linki (href + target).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AttributeEqualsSelector {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz Zadanie 12: użyj selektora "a[target=_blank]" (dokładna
         * wartość atrybutu) i porównaj liczność wyniku z selektorem
         * "a[target]" z poprzedniego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleClassesSelector {
        /*
         * 🧪 Zadanie 14:
         * Sparsuj HTML z elementami <div class="article">,
         * <div class="article featured">, <div class="article draft">.
         * Użyj selektora "div.article.featured" i wypisz, ile elementów
         * pasuje (powinien być dokładnie 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CommaOrSelector {
        /*
         * 🧪 Zadanie 15:
         * Sparsuj HTML z nagłówkami <h1>, <h2> i <h3> (po 2 każdego).
         * Użyj selektora "h1, h2" (suma zbiorów) i wypisz WSZYSTKIE
         * pasujące elementy razem z nazwą ich tagu (element.tagName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExtractArticleTitlesAndLinks {
        /*
         * 🧪 Zadanie 16:
         * Na bazie HTML z lekcji (lista <div class="article"> z <h2> i
         * <a class="read-more">) napisz metodę zwracającą Map<String,
         * String> (tytuł -> link) dla WSZYSTKICH artykułów - użyj
         * doc.select(".article") i zagnieżdżonego selectFirst().
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ParseHtmlFromFile {
        /*
         * 🧪 Zadanie 17:
         * Zapisz przykładowy HTML do tymczasowego pliku (Files.writeString
         * + Files.createTempFile) i sparsuj go przez
         * Jsoup.parse(File, "UTF-8") zamiast Jsoup.parse(String) - wypisz
         * doc.title() odczytany z pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_HandleMissingElementGracefully {
        /*
         * 🧪 Zadanie 18:
         * Sparsuj HTML BEZ elementu o id "nonexistent". Użyj
         * doc.selectFirst("#nonexistent") i sprawdź, że wynik to null -
         * obsłuż to bez NullPointerException (wypisz czytelny komunikat
         * zamiast rzucać wyjątek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExtractTableData {
        /*
         * 🧪 Zadanie 19:
         * Sparsuj HTML z tabelą <table> zawierającą 3 wiersze <tr>, każdy
         * z 2 komórkami <td> (imię, wiek). Użyj select("table tr") i dla
         * każdego wiersza select("td") żeby wyciągnąć dane do listy par
         * (imię, wiek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareParseVsBrokenAndValidHtml {
        /*
         * 🧪 Zadanie 20:
         * Sparsuj DWA warianty tego samego HTML - poprawny i celowo
         * uszkodzony (brakujące zamknięcia tagów) - i zweryfikuj, że
         * doc.select(...) daje TAKI SAM wynik dla obu (Jsoup naprawia
         * strukturę tak samo). Wypisz porównanie w komentarzu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildArticleRecordListFromHtml {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj record Article(String title, String summary, String
         * url). Sparsuj HTML z lekcji i zbuduj List<Article> przez
         * doc.select(".article") + strumień (Stream, _03_collections) -
         * wypisz listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ConnectToUrlWithTimeoutAndFallback {
        /*
         * 🧪 Zadanie 22:
         * Użyj Jsoup.connect("https://example.com").timeout(5000).get() w
         * try-catch (SocketTimeoutException/IOException) - wypisz tytuł
         * strony JEŚLI się uda, albo czytelny komunikat "brak internetu"
         * jeśli się nie uda (main() musi zakończyć się samoistnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ValidateAllLinksHaveText {
        /*
         * 🧪 Zadanie 23:
         * Sparsuj HTML z 6 linkami <a>, z których 2 są CELOWO puste
         * (<a href="...">  </a>). Napisz walidację, która znajduje i
         * wypisuje WSZYSTKIE linki bez widocznego tekstu (element.text()
         * puste po trim()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExtractNestedListStructure {
        /*
         * 🧪 Zadanie 24:
         * Sparsuj HTML z zagnieżdżoną listą <ul><li>Kategoria A
         * <ul><li>Podkategoria A1</li><li>Podkategoria A2</li></ul>
         * </li></ul>. Wypisz strukturę z WCIĘCIEM odzwierciedlającym
         * poziom zagnieżdżenia (rekurencyjnie po children()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareJsoupParseSpeedVsElementCount {
        /*
         * 🧪 Zadanie 25:
         * Wygeneruj (kodem, w pętli) String HTML z 1000 elementami <div
         * class="item">Item N</div>. Zmierz czas Jsoup.parse(...) oraz
         * czas doc.select(".item") (System.nanoTime()) i wypisz oba w ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExtractMetaTagsAsMap {
        /*
         * 🧪 Zadanie 26:
         * Sparsuj HTML z kilkoma <meta name="..." content="..."> w <head>
         * (np. description, author, viewport). Użyj select("meta[name]")
         * i zbuduj Map<String, String> (name -> content).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DetectDuplicateArticleTitles {
        /*
         * 🧪 Zadanie 27:
         * Sparsuj HTML z 5 artykułami, z których 2 mają IDENTYCZNY tytuł
         * <h2>. Wykryj i wypisz zduplikowane tytuły (Stream groupingBy +
         * filter count > 1, _03_collections).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SanitizeHtmlOutputAsPlainText {
        /*
         * 🧪 Zadanie 28:
         * Sparsuj HTML zawierający tagi formatujące (<b>, <i>, <span>) w
         * treści artykułu. Wypisz WYŁĄCZNIE czysty tekst (text()) bez
         * ŻADNYCH tagów HTML - zweryfikuj w komentarzu, że wynik nie
         * zawiera znaku '<'.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildMiniHtmlReportGenerator {
        /*
         * 🧪 Zadanie 29:
         * Mając List<Article> (jak w Zadaniu 21), wygeneruj NOWY String
         * HTML (text block + pętla/StringBuilder) z listą artykułów jako
         * <ul><li>...</li></ul> i sparsuj go PONOWNIE przez Jsoup, żeby
         * zweryfikować poprawność wygenerowanej struktury (select("li")
         * ma tyle samo elementów co lista wejściowa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullArticleExtractionPipeline {
        /*
         * 🧪 Zadanie 30:
         * Połącz WSZYSTKIE poznane techniki: sparsuj HTML "bloga" (min. 5
         * artykułów, część z brakującymi polami - opis/link), dla
         * KAŻDEGO artykułu bezpiecznie (bez NPE) wyciągnij tytuł, opis,
         * link, policz artykuły z klasą "featured", wykryj duplikaty
         * tytułów i wypisz PEŁNY raport podsumowujący.
         */
        public static void main(String[] args) { }
    }
}
