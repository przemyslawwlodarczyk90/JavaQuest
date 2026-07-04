package com.example.javaquest._06_networking.Lesson14_HtmlUnit;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlPage;

public class _Lesson14_HtmlUnit {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 CZYM JEST HtmlUnit?
         * ============================================================
         * HtmlUnit to biblioteka Java symulująca PRZEGLĄDARKĘ BEZ GUI
         * ("headless browser") – potrafi wejść na stronę, wykonać jej
         * JavaScript, wypełnić i wysłać formularz, kliknąć link, a nawet
         * obsługiwać ciasteczka/sesje – wszystko z poziomu zwykłego kodu
         * Java, bez otwierania żadnego okna.
         *
         * Typowe zastosowania:
         * - web scraping (automatyczne wyciąganie danych ze stron)
         * - testowanie aplikacji webowych "od strony przeglądarki"
         * - automatyzacja interakcji ze stronami (logowanie, wypełnianie
         *   formularzy) bez ręcznego klikania
         *
         * Różnica względem HttpClient (Lesson10)/URLConnection (Lesson09):
         * te API dają Ci SUROWĄ odpowiedź HTTP (np. tekst HTML) – Ty sam
         * musisz go sparsować. HtmlUnit idzie o krok dalej: parsuje HTML,
         * buduje model DOM strony (podobny do tego z Lesson13_XmlParsing,
         * tylko dla HTML) i pozwala nawigować po nim jak po żywej stronie
         * (elementy, linki, formularze...).
         */

        /*
         * ============================================================
         * 🔹 KONFIGURACJA WebClient
         * ============================================================
         * WebClient implementuje AutoCloseable – używamy go w
         * try-with-resources, żeby zawsze zwolnić zasoby (wątki, cache)
         * po zakończeniu pracy.
         *
         * Domyślnie HtmlUnit próbuje wykonywać CSS i JavaScript strony,
         * co bywa powolne i zawodne (wiele stron ma JS niekompatybilny
         * z silnikiem HtmlUnit, co generuje mnóstwo ostrzeżeń w logach).
         * Do prostego scrapowania STATYCZNEJ treści HTML wyłączamy
         * to jawnie – szybciej i stabilniej.
         */

        try (WebClient webClient = new WebClient()) {

            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setTimeout(5000); // 5 sekund - nie czekamy w nieskończoność

            System.out.println("=== Konfiguracja WebClient ===");
            System.out.println("CSS włączony:        " + webClient.getOptions().isCssEnabled());
            System.out.println("JavaScript włączony: " + webClient.getOptions().isJavaScriptEnabled());
            System.out.println("Timeout (ms):        " + webClient.getOptions().getTimeout());

            /*
             * ============================================================
             * 🔍 NAWIGACJA NA STRONĘ – getPage(url)
             * ============================================================
             * example.com to domena zarezerwowana przez IANA specjalnie
             * do celów dokumentacyjnych/testowych (RFC 2606) – jest
             * stabilna, statyczna i zawsze powinna odpowiadać, w
             * przeciwieństwie do przypadkowej strony, która może zniknąć
             * albo się zmienić.
             *
             * getPage(url) zwraca obiekt strony odpowiedniego typu –
             * dla odpowiedzi HTML będzie to HtmlPage.
             */

            System.out.println("\n=== Łączenie z https://example.com ===");
            HtmlPage page = webClient.getPage("https://example.com");

            System.out.println("Tytuł strony: " + page.getTitleText());
            System.out.println("URL strony:   " + page.getUrl());

            /*
             * ============================================================
             * 📋 ODCZYT ELEMENTÓW STRONY – LINKI (getAnchors)
             * ============================================================
             * getAnchors() zwraca wszystkie znaczniki <a> (linki) na
             * stronie jako listę HtmlAnchor – można odczytać ich tekst
             * (getTextContent) oraz docelowy adres (getHrefAttribute).
             */

            System.out.println("\n=== Linki znalezione na stronie ===");
            var anchors = page.getAnchors();
            if (anchors.isEmpty()) {
                System.out.println("(strona nie zawiera żadnych linków <a>)");
            } else {
                for (HtmlAnchor anchor : anchors) {
                    System.out.println("  \"" + anchor.getTextContent().trim() + "\" -> " + anchor.getHrefAttribute());
                }
            }

            /*
             * ============================================================
             * 🔧 A GDYBY STRONA MIAŁA FORMULARZ? (tylko komentarz -
             * example.com go nie ma, więc tego nie uruchamiamy)
             * ============================================================
             * HtmlUnit potrafi też wypełniać i wysyłać formularze HTML,
             * np.:
             *
             *   HtmlForm form = page.getFormByName("login");
             *   HtmlTextInput login = form.getInputByName("username");
             *   login.setValueAttribute("jan.kowalski");
             *   HtmlPasswordInput haslo = form.getInputByName("password");
             *   haslo.setValueAttribute("tajneHaslo123");
             *   HtmlSubmitInput submit = form.getInputByValue("Zaloguj");
             *   HtmlPage strona_po_zalogowaniu = submit.click();
             *
             * WebClient automatycznie zarządza też ciasteczkami/sesją
             * między kolejnymi żądaniami (webClient.getCookieManager()) -
             * dokładnie tak, jak prawdziwa przeglądarka (patrz Lesson11 -
             * Cookies i Sesje).
             */

            System.out.println("\n(HtmlUnit potrafi też wypełniać/wysyłać formularze HTML");
            System.out.println(" oraz automatycznie zarządzać ciasteczkami/sesją - patrz komentarz w kodzie)");

        } catch (Exception e) {
            /*
             * ============================================================
             * ⚠️ GRACEFUL FAILURE - BRAK INTERNETU / NIEDOSTĘPNY SERWER
             * ============================================================
             * Ta lekcja, w przeciwieństwie do poprzednich, łączy się
             * z PRAWDZIWYM, zdalnym serwerem w internecie - jeśli maszyna
             * nie ma dostępu do sieci (albo example.com akurat nie
             * odpowiada), zamiast zawieszać się w nieskończoność (stąd
             * ustawiony wcześniej timeout 5000ms) kończymy działanie
             * czytelnym komunikatem, zamiast rzucać nieobsłużony wyjątek.
             */
            System.out.println("\n=== Nie udało się połączyć z https://example.com ===");
            System.out.println("Powód: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            System.out.println("(Sprawdź połączenie z internetem - ta lekcja wymaga prawdziwej sieci)");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HtmlUnit = przeglądarka bez GUI (headless) sterowana z Javy
         * - WebClient jest AutoCloseable -> używaj try-with-resources
         * - getOptions().setCssEnabled(false)/setJavaScriptEnabled(false)
         *   - wyłączają CSS/JS dla szybszego, stabilniejszego scrapowania
         *   statycznej treści
         * - getOptions().setTimeout(ms) - zabezpieczenie przed
         *   nieskończonym oczekiwaniem na wolny/martwy serwer
         * - webClient.getPage(url) -> HtmlPage (dla odpowiedzi HTML)
         * - page.getTitleText(), page.getAnchors() -> odczyt tytułu i
         *   linków ze strony
         * - HtmlUnit potrafi też wypełniać/wysyłać formularze
         *   (HtmlForm, getInputByName, setValueAttribute, click()) oraz
         *   zarządzać ciasteczkami/sesją automatycznie
         * - przy łączeniu z prawdziwym, zewnętrznym serwerem ZAWSZE
         *   obsłuż błąd połączenia (brak internetu, timeout) zamiast
         *   pozwolić programowi się zawiesić lub wybuchnąć wyjątkiem
         */
    }
}
