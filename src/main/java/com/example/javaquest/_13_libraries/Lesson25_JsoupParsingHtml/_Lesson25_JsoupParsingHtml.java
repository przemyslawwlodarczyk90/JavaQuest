package com.example.javaquest._13_libraries.Lesson25_JsoupParsingHtml;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Duration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class _Lesson25_JsoupParsingHtml {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 25: JSOUP - PARSOWANIE HTML ===");

        /*
         * ============================================================
         * 📦 CZYM JEST JSOUP?
         * ============================================================
         * Jsoup to popularna biblioteka Java do pracy z HTML-em: parsuje
         * dokument HTML do wygodnego modelu DOM (podobnie jak parser XML
         * z _06_networking/Lesson13_XmlParsing), a potem pozwala go
         * przeszukiwać i modyfikować wygodnym API w stylu jQuery -
         * SELEKTORAMI CSS (np. "div.article > p", "#id", "a[href]").
         *
         * Największa różnica względem parserów XML (Lesson13) i
         * HtmlUnit (_06_networking/Lesson14):
         * - Parser XML wymaga POPRAWNEGO, dobrze sformowanego dokumentu -
         *   jeden brakujący tag zamykający i dostajesz wyjątek.
         * - Jsoup jest TOLERANCYJNY na niepoprawny HTML - dokładnie tak,
         *   jak przeglądarka. Brakujące </p>, źle zagnieżdżone tagi,
         *   brak <html>/<body> - Jsoup "naprawia" strukturę automatycznie
         *   i buduje z tego sensowne drzewo DOM. To kluczowe, bo bardzo
         *   dużo prawdziwych stron w internecie ma niepoprawny HTML.
         * - HtmlUnit (Lesson14) to CAŁA przeglądarka bez GUI - potrafi
         *   wykonać JavaScript strony, wypełnić formularz, kliknąć link.
         *   Jsoup NIE WYKONUJE JAVASCRIPTU - to czysty parser HTML/DOM,
         *   dużo szybszy i lżejszy, ale widzi tylko to, co przyszło w
         *   surowej odpowiedzi HTTP (bez treści dogenerowanej przez JS
         *   w przeglądarce).
         *
         * Kiedy więc wybrać co:
         * - statyczny HTML, chcesz szybko wyciągnąć dane -> Jsoup
         * - strona renderowana/dogenerowana przez JavaScript, interakcje
         *   (logowanie, kliknięcia) -> HtmlUnit (Lesson14) albo
         *   prawdziwa przeglądarka (Selenium)
         * Więcej porównania w Lesson26 na końcu.
         */

        /*
         * ============================================================
         * 🔹 PARSOWANIE ZE STRINGA - Jsoup.parse(String)
         * ============================================================
         * Najprostszy sposób na start: mamy gotowy tekst HTML (np. jako
         * Java text block) i parsujemy go bezpośrednio w pamięci, bez
         * żadnego pliku ani sieci.
         */

        String articlesHtml = """
                <html>
                <head><title>Blog o Javie</title></head>
                <body>
                    <h1 id="main-title">Najnowsze artykuły</h1>
                    <div class="article">
                        <h2>Wprowadzenie do Jsoup</h2>
                        <p class="summary">Jsoup to biblioteka do parsowania HTML w Javie.</p>
                        <a href="https://example.com/jsoup-wprowadzenie" class="read-more">Czytaj dalej</a>
                    </div>
                    <div class="article">
                        <h2>Streams w Javie</h2>
                        <p class="summary">Jak używać Stream API do przetwarzania kolekcji.</p>
                        <a href="https://example.com/streams" class="read-more">Czytaj dalej</a>
                    </div>
                    <div class="article featured">
                        <h2>Rekordy w Javie 17+</h2>
                        <p class="summary">Krótkie, niemutowalne nośniki danych.</p>
                        <a href="https://example.com/rekordy" class="read-more">Czytaj dalej</a>
                    </div>
                    <footer>
                        <p>Kontakt: <a href="mailto:redakcja@blog.example.com">redakcja@blog.example.com</a></p>
                    </footer>
                </body>
                </html>
                """;

        Document doc = Jsoup.parse(articlesHtml);

        System.out.println("\n=== Sparsowany dokument ===");
        System.out.println("Tytuł strony (<title>): " + doc.title());

        /*
         * ============================================================
         * 🔍 STRUKTURA: Document / Element / Elements
         * ============================================================
         * - Document      - cały sparsowany dokument (dziedziczy po
         *                    Element - to też jest element, korzeń drzewa)
         * - Element       - pojedynczy węzeł DOM (jeden konkretny tag)
         * - Elements      - lista Element (wynik wyszukiwania przez
         *                    select() - implementuje List<Element>,
         *                    więc można po niej normalnie iterować)
         */

        System.out.println("\n=== Document / Element / Elements ===");
        System.out.println("doc jest typu: " + doc.getClass().getSimpleName());
        Element h1 = doc.selectFirst("#main-title");
        System.out.println("Pierwszy element h1 jest typu: " + (h1 != null ? h1.getClass().getSimpleName() : "brak"));

        /*
         * ============================================================
         * 🔹 SELEKTORY CSS - doc.select(...)
         * ============================================================
         * select(selektor) zwraca Elements - WSZYSTKIE pasujące elementy.
         * selectFirst(selektor) zwraca tylko pierwszy (albo null, gdy
         * nic nie pasuje) - wygodne, gdy wiemy, że szukamy jednego węzła.
         *
         * Kilka typowych selektorów (składnia identyczna z CSS):
         */

        printSelectorCheatSheet();

        System.out.println("\n=== Przykłady selektorów w akcji ===");

        // #id -> element o konkretnym id
        Element title = doc.selectFirst("#main-title");
        System.out.println("doc.selectFirst(\"#main-title\") -> " + (title != null ? title.text() : "brak"));

        // .klasa -> wszystkie elementy z daną klasą CSS
        Elements articles = doc.select(".article");
        System.out.println("doc.select(\".article\") -> znaleziono " + articles.size() + " artykułów");

        // tag.klasa > tag -> selektor złożony (dziecko BEZPOŚREDNIE)
        Elements articleTitles = doc.select("div.article > h2");
        System.out.println("doc.select(\"div.article > h2\") -> " + articleTitles.size() + " tytułów");

        // tag[atrybut] -> elementy posiadające dany atrybut
        Elements linksWithHref = doc.select("a[href]");
        System.out.println("doc.select(\"a[href]\") -> " + linksWithHref.size() + " linków z atrybutem href");

        // tag.klasa1.klasa2 -> element musi mieć OBIE klasy
        Elements featured = doc.select("div.article.featured");
        System.out.println("doc.select(\"div.article.featured\") -> " + featured.size() + " wyróżniony artykuł");

        /*
         * ============================================================
         * 🔍 ODCZYT TEKSTU, ATRYBUTÓW I HTML Z ELEMENTU
         * ============================================================
         * - element.text()       - "widoczny" tekst (bez tagów HTML)
         * - element.attr(nazwa)  - wartość konkretnego atrybutu
         * - element.html()       - wewnętrzny HTML elementu (dzieci)
         * - element.outerHtml()  - HTML CAŁEGO elementu (razem z jego
         *                          własnym tagiem otwierającym/zamykającym)
         */

        System.out.println("\n=== Wyciąganie tytułów i linków artykułów ===");
        for (Element article : articles) {
            Element h2 = article.selectFirst("h2");
            Element summary = article.selectFirst("p.summary");
            Element link = article.selectFirst("a.read-more");

            String articleTitle = h2 != null ? h2.text() : "(brak tytułu)";
            String articleSummary = summary != null ? summary.text() : "(brak opisu)";
            String articleHref = link != null ? link.attr("href") : "(brak linku)";

            System.out.println(" - \"" + articleTitle + "\"");
            System.out.println("   opis: " + articleSummary);
            System.out.println("   link: " + articleHref);
        }

        System.out.println("\n=== element.html() vs element.outerHtml() (pierwszy artykuł) ===");
        Element firstArticle = articles.first();
        if (firstArticle != null) {
            System.out.println("html() (zawartość wewnątrz <div>):");
            System.out.println(firstArticle.html());
            System.out.println("\nouterHtml() (razem z tagiem <div ...> ... </div>) - pierwsze 60 znaków:");
            String outer = firstArticle.outerHtml();
            System.out.println(outer.substring(0, Math.min(60, outer.length())) + "...");
        }

        /*
         * ============================================================
         * 🔹 TOLERANCJA NA NIEPOPRAWNY HTML
         * ============================================================
         * Poniższy fragment HTML jest CELOWO niepoprawny: brakuje
         * zamknięcia <p>, tag <b> nie jest zamknięty, brak <html>/<body>.
         * Prawdziwy parser XML (Lesson13) rzuciłby wyjątkiem - Jsoup
         * "naprawia" strukturę tak, jak zrobiłaby to przeglądarka.
         */

        String brokenHtml = "<div><p>Pierwszy akapit<p>Drugi akapit <b>pogrubiony tekst</div>";
        Document brokenDoc = Jsoup.parse(brokenHtml);

        System.out.println("\n=== Tolerancja na niepoprawny HTML ===");
        System.out.println("Wejście (niepoprawne): " + brokenHtml);
        System.out.println("Jsoup naprawił strukturę do:");
        System.out.println(brokenDoc.body().html());
        System.out.println("Liczba <p> po naprawie: " + brokenDoc.select("p").size());

        /*
         * ============================================================
         * 🔍 PARSOWANIE Z PLIKU I Z URL
         * ============================================================
         * - Jsoup.parse(File, charsetName)  - parsuje plik HTML z dysku
         * - Jsoup.connect(url).get()        - pobiera stronę PRZEZ HTTP
         *   i od razu zwraca sparsowany Document. connect() zwraca
         *   Connection - obiekt konfigurowalny (timeout, nagłówki,
         *   user-agent...) - więcej o tym w Lesson26.
         *
         * Parsowanie z pliku wymagałoby istniejącego pliku na dysku -
         * tutaj pomijamy to jako osobny przykład (identyczna zasada
         * co Jsoup.parse(String), tylko źródłem jest File zamiast String)
         * i skupiamy się na Jsoup.connect(url) - z timeoutem i
         * przyjaznym komunikatem, gdyby brakło internetu (patrz
         * _06_networking/Lesson14_HtmlUnit - ta sama zasada demo).
         */

        System.out.println("\n=== Jsoup.connect(url).get() - prawdziwa strona (opcjonalnie) ===");
        try {
            Document remoteDoc = Jsoup.connect("https://example.com")
                    .timeout((int) Duration.ofSeconds(5).toMillis())
                    .get();
            System.out.println("Tytuł zdalnej strony: " + remoteDoc.title());
            Element heading = remoteDoc.selectFirst("h1");
            System.out.println("Nagłówek h1: " + (heading != null ? heading.text() : "(brak)"));
        } catch (SocketTimeoutException e) {
            System.out.println("Przekroczono limit czasu połączenia (brak internetu?) - kontynuuję bez zdalnych danych.");
        } catch (IOException e) {
            System.out.println("Nie udało się połączyć z https://example.com: " + e.getClass().getSimpleName()
                    + " - " + e.getMessage() + " (brak internetu? kontynuuję bez zdalnych danych)");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Jsoup = czysty, TOLERANCYJNY parser HTML w Javie - naprawia
         *   niepoprawną strukturę HTML jak przeglądarka, ale NIE wykonuje
         *   JavaScriptu (w przeciwieństwie do HtmlUnit z Lesson14).
         * - Jsoup.parse(String) - parsowanie ze Stringa (np. text block)
         * - Jsoup.parse(File, charset) - parsowanie z pliku na dysku
         * - Jsoup.connect(url).get()/.post() - pobranie i sparsowanie
         *   strony przez HTTP w jednym kroku
         * - Document/Element/Elements - model DOM; Elements to lista
         *   wyników select()
         * - doc.select("selektor CSS") - wyszukiwanie WIELU elementów,
         *   doc.selectFirst("selektor") - wyszukiwanie JEDNEGO (albo null)
         * - element.text() - widoczny tekst, element.attr("nazwa") -
         *   wartość atrybutu, element.html()/outerHtml() - surowy HTML
         * - Lesson26: web scraping "prawdziwej" strony (lokalny serwer),
         *   nawigacja po DOM (parent/siblingElements/children) i
         *   porównanie Jsoup vs HtmlUnit.
         */

        System.out.println("\n=== KONIEC LEKCJI 25 ===");
    }

    private static void printSelectorCheatSheet() {
        System.out.println("\n=== Ściąga: najczęstsze selektory CSS w Jsoup ===");
        String format = "%-28s | %-40s%n";
        System.out.printf(format, "Selektor", "Znaczenie");
        System.out.println("-".repeat(72));
        System.out.printf(format, "tagname", "wszystkie elementy danego tagu, np. \"p\"");
        System.out.printf(format, "#id", "element o konkretnym id, np. \"#main-title\"");
        System.out.printf(format, ".klasa", "elementy z daną klasą CSS, np. \".article\"");
        System.out.printf(format, "tag.klasa", "tag Z konkretną klasą, np. \"div.article\"");
        System.out.printf(format, "rodzic > dziecko", "BEZPOŚREDNIE dziecko, np. \"div.article > h2\"");
        System.out.printf(format, "przodek potomek", "dowolny potomek, np. \"div.article p\"");
        System.out.printf(format, "tag[atrybut]", "element MA dany atrybut, np. \"a[href]\"");
        System.out.printf(format, "tag[atrybut=wartosc]", "atrybut o KONKRETNEJ wartości, np. \"a[target=_blank]\"");
        System.out.printf(format, "tag1, tag2", "suma zbiorów (OR), np. \"h1, h2\"");
    }
}
