package com.example.javaquest._19_security_basics.Lesson15_XxeAndXmlExternalEntityAttacks;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson15_XxeAndXmlExternalEntityAttacks {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: XXE (XML EXTERNAL ENTITY) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z _06_networking/Lesson13_XmlParsing
         * ============================================================
         * Poznales juz PODSTAWOWE parsowanie XML przez
         * `DocumentBuilderFactory`. Ta lekcja pokazuje, ze DOMYSLNA
         * konfiguracja tego parsera (bez zadnych dodatkowych ustawien!)
         * jest PODATNA na atak - standard XML pozwala zdefiniowac
         * "ENCJE ZEWNETRZNE" (external entities), ktore parser
         * AUTOMATYCZNIE ZALADUJE - w tym z PLIKOW LOKALNYCH serwera.
         */
        System.out.println("Domyslny DocumentBuilderFactory laduje ZEWNETRZNE encje z XML - w tym pliki LOKALNE serwera!");

        Path secretFile = Files.createTempFile("lesson15-secret", ".txt");
        Files.writeString(secretFile, "HASLO_DO_BAZY_DANYCH=SuperTajneHaslo123");
        System.out.println("Utworzono 'sekretny' plik na serwerze: " + secretFile + " (symuluje np. plik konfiguracyjny).");

        try {
            demonstrateVulnerableParserReadsLocalFile(secretFile);
            demonstrateSecureParserRejectsExternalEntity(secretFile);
        } finally {
            Files.deleteIfExists(secretFile);
        }

        /*
         * ============================================================
         * 🔹 JAK DZIALA ATAK XXE
         * ============================================================
         * Atakujacy przesyla (np. jako cialo zadania HTTP z
         * `Content-Type: application/xml`) dokument z DEFINICJA WLASNEJ
         * ENCJI odwolujacej sie do zasobu zewnetrznego:
         * <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///etc/passwd"> ]>
         * <foo>&xxe;</foo>
         * Jesli parser XML ma DOMYSLNA konfiguracje, PODMIENI `&xxe;`
         * NA ZAWARTOSC pliku `/etc/passwd` (lub INNEGO pliku lokalnego,
         * a nawet - w niektorych konfiguracjach - wykona zadanie
         * sieciowe do WEWNETRZNEGO serwisu, co nazywa sie SSRF -
         * Server-Side Request Forgery). Atakujacy odczytuje WYNIK w
         * odpowiedzi aplikacji (jesli ta zwraca sparsowana tresc XML).
         *
         * ============================================================
         * 🔹 OBRONA: WYLACZENIE PRZETWARZANIA DTD/ENCJI ZEWNETRZNYCH
         * ============================================================
         * Najprostsza i NAJSKUTECZNIEJSZA obrona - CALKOWITE wylaczenie
         * przetwarzania DOCTYPE (skoro wiekszosc aplikacji NIGDY go nie
         * potrzebuje): `factory.setFeature(
         * "http://apache.org/xml/features/disallow-doctype-decl", true)`.
         * Jesli DOCTYPE jest naprawde potrzebny, alternatywa to
         * wylaczenie SAMYCH encji zewnetrznych (`external-general-entities`,
         * `external-parameter-entities` -> false) oraz
         * `factory.setXIncludeAware(false)` i
         * `factory.setExpandEntityReferences(false)`.
         */
        System.out.println("\nObrona: wylaczenie DOCTYPE (disallow-doctype-decl=true) - najprostsza i najskuteczniejsza.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - XXE wykorzystuje standardowa funkcje XML (encje zewnetrzne),
         *   ktora WIEKSZOSC aplikacji NIGDY nie potrzebuje.
         * - Skutki: odczyt PLIKOW LOKALNYCH serwera, SSRF (zadania do
         *   wewnetrznych uslug), a w skrajnych przypadkach odmowa
         *   uslugi (DoS przez "billion laughs" - masowe zagniezdzone
         *   encje).
         * - Domyslna konfiguracja `DocumentBuilderFactory` w Javie JEST
         *   podatna - trzeba JAWNIE ja zabezpieczyc.
         * - Obrona: `disallow-doctype-decl=true` (najprostsza) LUB
         *   wylaczenie `external-general-entities`/
         *   `external-parameter-entities` jesli DOCTYPE jest niezbedny.
         * - Ta sama zasada dotyczy INNYCH parserow XML w Javie
         *   (`SAXParserFactory`, `XMLInputFactory` dla StAX,
         *   transformerow XSLT) - KAZDY z nich wymaga analogicznego
         *   zabezpieczenia.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void demonstrateVulnerableParserReadsLocalFile(Path secretFile) throws Exception {
        System.out.println("\n=== PODATNY PARSER - DOMYSLNA KONFIGURACJA ===");

        String maliciousXml = """
                <?xml version="1.0"?>
                <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///%s"> ]>
                <foo>&xxe;</foo>
                """.formatted(secretFile.toString().replace("\\", "/"));

        System.out.println("Zlosliwy XML przeslany przez 'atakujacego':\n" + maliciousXml);

        DocumentBuilderFactory vulnerableFactory = DocumentBuilderFactory.newInstance();
        // BRAK JAKIEGOKOLWIEK zabezpieczenia - domyslna konfiguracja.
        DocumentBuilder builder = vulnerableFactory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(maliciousXml.getBytes(StandardCharsets.UTF_8)));

        String leakedContent = document.getDocumentElement().getTextContent();
        System.out.println("Zawartosc elementu <foo> PO sparsowaniu: " + leakedContent);
        System.out.println("-> ATAK SIE POWIODL - parser ODCZYTAL i WSTAWIL zawartosc PLIKU LOKALNEGO serwera do wyniku!");
    }

    private static void demonstrateSecureParserRejectsExternalEntity(Path secretFile) {
        System.out.println("\n=== BEZPIECZNY PARSER - DOCTYPE WYLACZONE ===");

        String maliciousXml = """
                <?xml version="1.0"?>
                <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///%s"> ]>
                <foo>&xxe;</foo>
                """.formatted(secretFile.toString().replace("\\", "/"));

        try {
            DocumentBuilderFactory secureFactory = DocumentBuilderFactory.newInstance();
            secureFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            secureFactory.setXIncludeAware(false);
            secureFactory.setExpandEntityReferences(false);
            secureFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder builder = secureFactory.newDocumentBuilder();
            builder.parse(new ByteArrayInputStream(maliciousXml.getBytes(StandardCharsets.UTF_8)));

            System.out.println("BLAD: zlosliwy XML zostal zaakceptowany - zabezpieczenie nie zadzialalo!");
        } catch (SAXException e) {
            System.out.println("XML ODRZUCONY, zanim jakikolwiek plik zostal odczytany: " + e.getMessage());
            System.out.println("-> ATAK ZABLOKOWANY - parser odmawia przetwarzania DOCTYPE w ogole.");
        } catch (Exception e) {
            System.out.println("XML odrzucony innym wyjatkiem zwiazanym z konfiguracja parsera: " + e.getMessage());
        }
    }
}
