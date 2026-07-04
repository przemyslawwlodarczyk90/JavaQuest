package com.example.javaquest._06_networking.Lesson13_XmlParsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class _Lesson13_XmlParsing {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 XML JAKO FORMAT WYMIANY DANYCH
         * ============================================================
         * XML (eXtensible Markup Language) to starszy od JSON-a, ale
         * wciąż powszechny format tekstowy do reprezentowania danych
         * strukturalnych – nadal spotykany np. w starszych API SOAP,
         * plikach konfiguracyjnych (pom.xml!), eksportach z systemów
         * korporacyjnych, feedach RSS itd.
         *
         * Podstawowa struktura: ZAGNIEŻDŻONE ZNACZNIKI (elementy), które
         * mogą mieć ATRYBUTY oraz zawierać TEKST lub kolejne elementy:
         *
         *   <element atrybut="wartość">tekst</element>
         *
         * W tej lekcji parsujemy XML TRZYMANY W PAMIĘCI (zwykły String)
         * – żadnego pliku ani sieci, żeby demo było szybkie i w 100%
         * deterministyczne. Dokładnie to samo API zadziała z XML-em
         * wczytanym z pliku (patrz rozdział _04_io) albo pobranym przez
         * sieć (patrz Lesson09-Lesson12).
         */

        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <users>
                    <user id="1">
                        <name>Anna Nowak</name>
                        <email>anna@example.com</email>
                    </user>
                    <user id="2">
                        <name>Jan Kowalski</name>
                        <email>jan@example.com</email>
                    </user>
                </users>
                """;

        System.out.println("=== SUROWY XML ===");
        System.out.println(xml);

        /*
         * ============================================================
         * 🔹 DOM (Document Object Model) – PODEJŚCIE "CAŁE DRZEWO NA RAZ"
         * ============================================================
         * DocumentBuilderFactory + DocumentBuilder wczytują CAŁY dokument
         * XML do pamięci jako DRZEWO obiektów (Document -> Element -> Node...),
         * po którym można się swobodnie poruszać w dowolnym kierunku
         * (rodzic, dzieci, atrybuty) – wygodne w użyciu, ale wymaga
         * pamięci proporcjonalnej do rozmiaru CAŁEGO dokumentu.
         *
         * InputSource + StringReader pozwalają podać parserowi XML
         * jako zwykły String (zamiast pliku/strumienia sieciowego).
         */

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        document.getDocumentElement().normalize();
        // normalize() – "scala" sąsiadujące węzły tekstowe w jeden;
        // dobra praktyka po sparsowaniu, przed przetwarzaniem drzewa

        System.out.println("=== KORZEŃ DOKUMENTU ===");
        System.out.println("Nazwa elementu głównego: " + document.getDocumentElement().getNodeName());
        // users

        /*
         * ============================================================
         * 🔍 PRZECHODZENIE PO DRZEWIE DOM
         * ============================================================
         * getElementsByTagName(nazwa) – zwraca WSZYSTKIE elementy
         * o danej nazwie w całym dokumencie (niezależnie od poziomu
         * zagnieżdżenia) jako NodeList.
         */

        NodeList userNodes = document.getElementsByTagName("user");
        System.out.println("\n=== LICZBA ELEMENTÓW <user> ===");
        System.out.println("Znaleziono: " + userNodes.getLength());

        System.out.println("\n=== ODCZYT ATRYBUTÓW I DZIECI KAŻDEGO <user> ===");
        for (int i = 0; i < userNodes.getLength(); i++) {
            Node userNode = userNodes.item(i);

            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;

                /*
                 * getAttributes() / getAttribute(nazwa) – odczyt atrybutów
                 * elementu, np. id="1" w <user id="1">.
                 */
                String id = userElement.getAttribute("id");

                /*
                 * getElementsByTagName na POJEDYNCZYM elemencie (nie na
                 * całym dokumencie) – szuka dzieci TYLKO wewnątrz tego
                 * elementu. item(0).getTextContent() odczytuje tekst
                 * zawarty wewnątrz znacznika, np. <name>Anna Nowak</name>.
                 */
                String name = userElement.getElementsByTagName("name").item(0).getTextContent();
                String email = userElement.getElementsByTagName("email").item(0).getTextContent();

                System.out.println("  user[id=" + id + "] -> name=" + name + ", email=" + email);
            }
        }

        /*
         * ============================================================
         * 📋 NISKOPOZIOMOWE PRZECHODZENIE PRZEZ getChildNodes()
         * ============================================================
         * getChildNodes() zwraca WSZYSTKIE bezpośrednie węzły-dzieci,
         * w tym węzły TEKSTOWE (białe znaki między znacznikami też są
         * osobnymi węzłami typu TEXT_NODE!). To dlatego w praktyce
         * częściej korzysta się z getElementsByTagName – jest wygodniejsze,
         * bo od razu filtruje po nazwie elementu.
         */

        System.out.println("\n=== SUROWE DZIECI PIERWSZEGO <user> (getChildNodes) ===");
        Node firstUser = userNodes.item(0);
        NodeList children = firstUser.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            String type = child.getNodeType() == Node.ELEMENT_NODE ? "ELEMENT"
                    : child.getNodeType() == Node.TEXT_NODE ? "TEXT" : "INNY";
            String preview = child.getNodeType() == Node.TEXT_NODE
                    ? child.getNodeValue().trim().replace("\n", "\\n")
                    : child.getNodeName();
            System.out.println("  [" + type + "] " + preview);
        }
        // Widać tu węzły TEXT (białe znaki wcięć) przeplatane z ELEMENT
        // (name, email) – stąd wygoda getElementsByTagName() powyżej.

        /*
         * ============================================================
         * ⚠️ DOM vs SAX – KIEDY UŻYĆ CZEGO?
         * ============================================================
         * DOM (użyte powyżej):
         * - Wczytuje CAŁY dokument do pamięci jako drzewo obiektów
         * - Wygodne API (można "chodzić" po drzewie w dowolną stronę)
         * - Zużycie pamięci rośnie wraz z rozmiarem dokumentu
         * - Dobre dla dokumentów małych/średnich rozmiarów
         *
         * SAX (Simple API for XML) – ALTERNATYWA, tu NIE demonstrowana:
         * - Parser STRUMIENIOWY, zdarzeniowy (event-driven) – "leci"
         *   przez dokument RAZ, od góry do dołu, i przy każdym
         *   napotkanym elemencie WYWOŁUJE CALLBACK (np. startElement,
         *   endElement, characters) zamiast budować całe drzewo
         * - NIE trzyma całego dokumentu w pamięci -> dużo mniejsze
         *   zużycie RAM-u, nadaje się do OGROMNYCH plików XML
         *   (gigabajty), których nie dałoby się wczytać przez DOM
         * - Mniej wygodny w użyciu – nie da się "cofnąć" ani swobodnie
         *   przeskoczyć do innego miejsca w dokumencie, trzeba samemu
         *   budować stan w trakcie przechodzenia zdarzeń
         *
         * 📌 W SKRÓCIE: DOM – wygoda i swoboda, kosztem pamięci.
         * SAX – oszczędność pamięci i szybkość dla wielkich plików,
         * kosztem wygody API.
         */

        System.out.println("\n=== DOM vs SAX (skrót) ===");
        System.out.println("DOM: całe drzewo w pamięci, wygodne API, dobre dla małych/średnich XML-i");
        System.out.println("SAX: strumień zdarzeń, minimalne zużycie pamięci, dobre dla ogromnych plików");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DocumentBuilderFactory.newInstance().newDocumentBuilder()
         *   -> DocumentBuilder do parsowania XML
         * - builder.parse(new InputSource(new StringReader(xml)))
         *   -> parsowanie XML-a trzymanego w Stringu (bez pliku/sieci)
         * - document.getElementsByTagName(nazwa) -> wszystkie elementy
         *   o danej nazwie w całym dokumencie (NodeList)
         * - element.getAttribute(nazwa) -> odczyt atrybutu elementu
         * - node.getTextContent() -> tekst zawarty w elemencie
         * - node.getChildNodes() -> WSZYSTKIE dzieci, w tym węzły tekstowe
         *   (białe znaki) - stąd wygodniejsze bywa getElementsByTagName
         * - SAX to alternatywa dla DOM - streamingowa, zdarzeniowa,
         *   oszczędna pamięciowo, ale mniej wygodna w użyciu
         */
    }
}
