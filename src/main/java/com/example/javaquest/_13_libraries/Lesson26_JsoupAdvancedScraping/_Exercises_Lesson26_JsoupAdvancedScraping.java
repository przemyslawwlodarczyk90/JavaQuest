package com.example.javaquest._13_libraries.Lesson26_JsoupAdvancedScraping;

public class _Exercises_Lesson26_JsoupAdvancedScraping {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DescendantVsChildSelector {
        /*
         * 🧪 Zadanie 1:
         * Sparsuj HTML "<div class=\"box\"><p>Dziecko 1</p><section><p>Wnuk</p>
         * </section><p>Dziecko 2</p></div>". Porównaj liczność wyniku selektora
         * "div.box p" (wszyscy potomkowie) z "div.box > p" (tylko bezposrednie
         * dzieci) - wypisz obie liczby i wyjasnij roznice w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AttributeExistsSelector {
        /*
         * 🧪 Zadanie 2:
         * Sparsuj HTML z 4 obrazkami <img>, z ktorych tylko 3 maja atrybut
         * "alt". Uzyj selektora "img[alt]" i wypisz liczbe dopasowanych
         * elementow oraz ich wartosci alt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AttributeStartsWithSelector {
        /*
         * 🧪 Zadanie 3:
         * Sparsuj HTML z linkami <a href="https://a.pl">, <a href="http://b.pl">,
         * <a href="/lokalny">. Uzyj selektora "a[href^=https]" i wypisz tylko
         * linki zaczynajace sie od "https".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AttributeEndsWithSelector {
        /*
         * 🧪 Zadanie 4:
         * Sparsuj HTML z 5 linkami <a href="..."> wskazujacymi na rozne pliki
         * (".pdf", ".docx", ".pdf", ".jpg", ".pdf"). Uzyj selektora
         * "a[href$=.pdf]" i wypisz liczbe oraz tresc kazdego znalezionego linku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AttributeContainsSelector {
        /*
         * 🧪 Zadanie 5:
         * Sparsuj HTML z elementami <div id="product-101">, <div id="product-102">,
         * <div id="category-1">. Uzyj selektora "div[id*=product]" i wypisz
         * dopasowane id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ContainsTextSelector {
        /*
         * 🧪 Zadanie 6:
         * Sparsuj liste zadan <ul><li> z 5 pozycjami, z ktorych 2 zawieraja
         * slowo "pilne" w tekscie. Uzyj selektora "li:contains(pilne)" i wypisz
         * dopasowane pozycje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NthChildSelector {
        /*
         * 🧪 Zadanie 7:
         * Sparsuj HTML z 6 elementami <li> w jednym <ul id="lista">. Uzyj
         * selektora "#lista li:nth-child(3)" zeby pobrac trzeci element oraz
         * "#lista li:nth-child(2n)" zeby pobrac wszystkie na parzystych
         * pozycjach - wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CombinedTagClassAttributeSelector {
        /*
         * 🧪 Zadanie 8:
         * Sparsuj HTML z linkami <a class="external" href="..." target="_blank">
         * i <a class="external" href="..."> (bez target). Uzyj selektora
         * "a.external[target=_blank]" i wypisz tylko dopasowane linki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ParentAndChildrenNavigation {
        /*
         * 🧪 Zadanie 9:
         * Sparsuj HTML karty produktu z <h3 id="title">, <p class="price">,
         * <button>. Znajdz element o id "title", wypisz nazwe tagu jego
         * parent() oraz liczbe elementow zwroconych przez parent().children().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NextAndPreviousSiblingNavigation {
        /*
         * 🧪 Zadanie 10:
         * Uzywajac tej samej karty produktu co w Zadaniu 9, znajdz element
         * "p.price" i wypisz text() jego nextElementSibling() oraz
         * previousElementSibling() (moze byc null - obsluz to bez NPE).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SiblingElementsList {
        /*
         * 🧪 Zadanie 11:
         * Sparsuj HTML sekcji z naglowkiem <h2 id="section-title"> i trzema
         * elementami rodzenstwa (<p>, <ul>, <p>). Uzyj siblingElements() na
         * naglowku i wypisz nazwe tagu oraz tekst kazdego elementu rodzenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RelativeVsAbsoluteUrl {
        /*
         * 🧪 Zadanie 12:
         * Sparsuj HTML z linkiem <a id="link" href="/produkty/9"> przez
         * Jsoup.parse(html, "https://sklep.example/katalog"). Wypisz
         * attr("href") (surowa, wzgledna wartosc) oraz absUrl("href")
         * (pelny, wyliczony adres) - porownaj obie wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AlreadyAbsoluteUrlUnchanged {
        /*
         * 🧪 Zadanie 13:
         * Sparsuj HTML z linkiem juz bezwzglednym
         * (href="https://inna-domena.example/x") z ustawionym baseUri. Wypisz
         * attr("href") i absUrl("href") i zweryfikuj w komentarzu, ze sa
         * identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExtractAllAbsoluteLinksFromDocument {
        /*
         * 🧪 Zadanie 14:
         * Sparsuj dokument z baseUri "https://blog.example/artykuly/" i 4
         * linkami o roznych wzglednych/bezwzglednych href. Uzyj select("a[href]")
         * i zbuduj List<String> z absUrl("href") dla kazdego linku - wypisz liste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TextVsHtmlVsOuterHtmlComparison {
        /*
         * 🧪 Zadanie 15:
         * Sparsuj "<p id=\"note\">Cena: <b>199 zl</b> <i>(promocja)</i></p>".
         * Wypisz obok siebie text(), html() i outerHtml() elementu #note i w
         * komentarzu opisz roznice miedzy kazda z trzech wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExtractTableRowsViaSelectors {
        /*
         * 🧪 Zadanie 16:
         * Sparsuj HTML tabeli z <table id="cennik"> zawierajacej 4 wiersze
         * <tr>, kazdy z 2 komorkami <td> (produkt, cena). Uzyj
         * select("#cennik tr") i dla kazdego wiersza select("td") - zbuduj
         * Map<String, String> (produkt -> cena) i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExtractFormFieldsAndAttributes {
        /*
         * 🧪 Zadanie 17:
         * Sparsuj HTML formularza <form> z polami <input name="email"
         * type="email">, <input name="haslo" type="password"> oraz jednym
         * <input type="hidden" name="token" value="abc123">. Uzyj
         * select("input[name]") i wypisz dla kazdego pola name, type i
         * (jesli jest) value.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UserAgentAndTimeoutConfiguration {
        /*
         * 🧪 Zadanie 18:
         * Skonfiguruj (bez laczenia sie z siecia - tylko zbuduj obiekt)
         * Jsoup.connect("https://example.com").timeout(3000).userAgent(
         * "JavaQuestBot/1.0") i wypisz w komentarzu, po co ustawia sie
         * niestandardowego User-Agenta przy scrapowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LiveFetchWithTimeoutAndFallback {
        /*
         * 🧪 Zadanie 19:
         * Pobierz prawdziwa strone Jsoup.connect("https://example.com")
         * .timeout(5000).get() w try-catch (SocketTimeoutException/
         * IOException). Jesli sie uda - wypisz doc.title() i liczbe <p>.
         * Jesli sie NIE uda (brak internetu) - wypisz przyjazny komunikat
         * "brak internetu" tak, zeby main() zakonczyl sie samoistnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BasicHtmlSanitization {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj String z "niezaufanym" komentarzem uzytkownika zawierajacym
         * <script>alert('xss')</script> oraz zwykly tekst z <b>. Uzyj
         * Jsoup.clean(html, Safelist.basic()) i wypisz wynik - zweryfikuj w
         * komentarzu, ze <script> zostal usuniety.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SafelistNoneVsBasicVsRelaxed {
        /*
         * 🧪 Zadanie 21:
         * Uzywajac tego samego "niezaufanego" HTML co w Zadaniu 20 (z
         * <script>, <img onerror=...>, <a href="javascript:...">), wyczysc
         * go trzema roznymi Safelist (none(), basic(), relaxed()) i wypisz
         * wszystkie trzy wyniki obok siebie, zeby porownac restrykcyjnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CustomSafelistWithAllowedTags {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj wlasna Safelist metoda new Safelist().addTags("p", "b",
         * "a").addAttributes("a", "href") i wyczysc nia HTML zawierajacy
         * <p>, <b>, <a href="...">, <img> oraz <script> - wypisz wynik i
         * zweryfikuj, ze <img> zostal usuniety mimo braku <script>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildHtmlReportProgrammatically {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj od zera (Jsoup.parse("") + appendElement) dokument HTML
         * z naglowkiem <h1>Raport sprzedazy</h1> i lista <ul> z 5 pozycjami
         * "Produkt N: X szt." (dane wygenerowane w petli). Wypisz
         * body.outerHtml() gotowego dokumentu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RecursiveDomTreePrinter {
        /*
         * 🧪 Zadanie 24:
         * Sparsuj zagniezdzony HTML (min. 3 poziomy zagniezdzenia elementow).
         * Napisz metode rekurencyjna, ktora przechodzi po children() i
         * wypisuje nazwe kazdego tagu z WCIECIEM proporcjonalnym do glebokosci
         * zagniezdzenia (bez uzywania gotowych selektorow CSS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ExtractAllPdfLinksAsAbsoluteUrls {
        /*
         * 🧪 Zadanie 25:
         * Sparsuj dokument (z baseUri) zawierajacy 8 linkow, z ktorych 3
         * wskazuja na pliki ".pdf" (wzglednie i bezwzglednie). Uzyj selektora
         * "a[href$=.pdf]" razem z absUrl("href") zeby zwrocic List<String>
         * z PELNYMI adresami tylko do plikow PDF.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DetectExternalVsInternalLinks {
        /*
         * 🧪 Zadanie 26:
         * Sparsuj dokument z baseUri "https://moja-strona.example" i 6
         * linkami - czescia wzglednymi (wewnetrzne), czescia do innych domen
         * (zewnetrzne). Uzywajac absUrl("href") i sprawdzenia prefiksu domeny,
         * podziel linki na dwie listy: wewnetrzne i zewnetrzne - wypisz obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ScrapeAndSanitizeUserGeneratedComments {
        /*
         * 🧪 Zadanie 27:
         * Sparsuj HTML "strony z komentarzami" (3 elementy <div class=
         * "comment">), z ktorych jeden zawiera zlosliwy <script>. Wyciagnij
         * text komentarzy PRZEZ selektor, a nastepnie kazdy oczysc
         * Jsoup.clean(..., Safelist.basic()) zanim wypiszesz - opisz w
         * komentarzu, dlaczego oczyszczanie powinno byc zawsze, nie tylko
         * "dla podejrzanych" wpisow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_LiveFetchAndExtractAllAbsoluteLinks {
        /*
         * 🧪 Zadanie 28:
         * Pobierz "https://example.com" z timeoutem 5000ms w try-catch
         * (IOException) z przyjaznym fallbackiem "brak internetu". Jesli
         * fetch sie powiedzie - wyciagnij WSZYSTKIE linki select("a[href]")
         * jako pelne adresy (absUrl) i wypisz je posortowane alfabetycznie
         * (Collections.sort / Stream.sorted, _03_collections).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildLinkGraphFromNavigation {
        /*
         * 🧪 Zadanie 29:
         * Sparsuj HTML menu nawigacyjnego <nav> z zagniezdzonymi <ul><li><a>
         * (menu glowne + podmenu). Uzywajac WYLACZNIE nawigacji po drzewie
         * (children/parent/siblingElements, BEZ dodatkowych selektorow CSS
         * poza znalezieniem <nav>), zbuduj Map<String, List<String>>
         * (pozycja menu glownego -> lista pozycji podmenu) i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullScrapingPipelineWithFallback {
        /*
         * 🧪 Zadanie 30:
         * Polacz wszystkie poznane techniki w jeden pipeline: sprobuj
         * pobrac "https://example.com" (timeout + fallback "brak internetu").
         * Niezaleznie od wyniku sieci, sparsuj TAKZE lokalny, zdefiniowany w
         * kodzie HTML "sklepu" z produktami (nazwa, cena, link wzgledny) i
         * dla kazdego produktu wypisz nazwe, cene, absUrl linku ORAZ
         * oczyszczony (Jsoup.clean) opis produktu zawierajacy "niebezpieczne"
         * tagi - wypisz pelny raport na koniec.
         */
        public static void main(String[] args) { }
    }
}
