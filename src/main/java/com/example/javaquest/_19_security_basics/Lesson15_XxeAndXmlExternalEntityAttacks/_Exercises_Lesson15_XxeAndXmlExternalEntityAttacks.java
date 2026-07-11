package com.example.javaquest._19_security_basics.Lesson15_XxeAndXmlExternalEntityAttacks;

public class _Exercises_Lesson15_XxeAndXmlExternalEntityAttacks {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainXxeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, czym jest "encja
         * zewnetrzna" (external entity) w XML i dlaczego jej domyslne
         * ladowanie jest niebezpieczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateSecretFileForDemo {
        /*
         * 🧪 Zadanie 2:
         * Utworz tymczasowy plik z "sekretna" zawartoscia (symulujacy
         * plik konfiguracyjny serwera) - wypisz jego sciezke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ParseXmlWithDefaultFactory {
        /*
         * 🧪 Zadanie 3:
         * Sparsuj prosty, NIEZLOSLIWY XML domyslnym
         * `DocumentBuilderFactory` - wypisz odczytana wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BuildXxePayloadReferencingLocalFile {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj XML z DOCTYPE definiujacym encje `SYSTEM "file:///..."`
         * wskazujaca na plik z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ParseXxePayloadWithVulnerableParser {
        /*
         * 🧪 Zadanie 5:
         * Sparsuj payload z Zadania 4 domyslnym parserem - zweryfikuj, ze
         * zawartosc pliku zostala WSTAWIONA do dokumentu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DisableDoctypeDeclaration {
        /*
         * 🧪 Zadanie 6:
         * Skonfiguruj `DocumentBuilderFactory` z
         * `disallow-doctype-decl=true` - sparsuj TEN SAM payload i
         * przechwyc wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyLegitimateXmlStillWorksAfterFix {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj, ze zabezpieczony parser z Zadania 6 NADAL poprawnie
         * parsuje NORMALNY XML bez DOCTYPE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyMostAppsDontNeedDoctype {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, dlaczego wiekszosc aplikacji (API
         * wymieniajace dane) NIGDY nie potrzebuje DOCTYPE w przesylanym
         * XML - kiedy MOGLBY byc uzasadniony wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareVulnerableAndSecureFactoryConfiguration {
        /*
         * 🧪 Zadanie 9:
         * Wypisz obok siebie (2 bloki kodu w komentarzu) konfiguracje
         * PODATNA i BEZPIECZNA `DocumentBuilderFactory` - podkresl
         * roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainSsrfConnectionToXxe {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, jak XXE moze prowadzic do SSRF
         * (Server-Side Request Forgery) - gdy encja zewnetrzna wskazuje
         * na `http://` zamiast `file://`.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_DisableExternalGeneralEntitiesOnly {
        /*
         * 🧪 Zadanie 11:
         * Zamiast pelnego `disallow-doctype-decl`, skonfiguruj TYLKO
         * `external-general-entities=false` i
         * `external-parameter-entities=false` - zweryfikuj, ze DOCTYPE
         * bez zewnetrznych encji WCIAZ dziala, ale XXE jest zablokowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SecureSaxParserFactoryAgainstXxe {
        /*
         * 🧪 Zadanie 12:
         * Zabezpiecz `SAXParserFactory` (inny parser XML w JDK) TYMI
         * SAMYMI ustawieniami - zweryfikuj identyczne zachowanie jak dla
         * `DocumentBuilderFactory`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SecureXmlInputFactoryForStax {
        /*
         * 🧪 Zadanie 13:
         * Zabezpiecz `XMLInputFactory` (StAX) przez wlasciwosci
         * `IS_SUPPORTING_EXTERNAL_ENTITIES=false` i
         * `SUPPORT_DTD=false` - zweryfikuj odrzucenie XXE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateBillionLaughsDosAttackConceptually {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala (lub z BARDZO malym, kontrolowanym przykladem):
         * opisz atak "billion laughs" - masowo zagniezdzone encje
         * wewnetrzne powodujace eksplodujace zuzycie pamieci/CPU przy
         * parsowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementXxeProtectionMiddlewareForHttpServer {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj `HttpServer` z endpointem przyjmujacym XML w ciele
         * zadania - uzyj ZABEZPIECZONEGO parsera do przetworzenia go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestMultipleXxePayloadVariants {
        /*
         * 🧪 Zadanie 16:
         * Przetestuj zabezpieczony parser wobec kilku roznych wariantow
         * payloadu XXE (parameter entity, encja w atrybucie, zagniezdzona
         * encja) - zweryfikuj, ze WSZYSTKIE sa odrzucone.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareXxeRiskAcrossXmlLibraries {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: zbadaj, czy inne biblioteki XML w Javie (np.
         * Dom4j, JAXB `Unmarshaller`) maja PODOBNY problem domyslnej
         * konfiguracji - co trzeba sprawdzic przy kazdej nowej bibliotece
         * XML.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementXxeAuditForExistingParsersInCodebase {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode "audytujaca" liste utworzonych fabryk parserow
         * XML (symulowanych obiektow konfiguracji) - oznacz, ktore NIE
         * MAJA zastosowanego zabezpieczenia XXE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HandleParsingExceptionWithoutLeakingDetails {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj obsluge bledu parsowania (zablokowany XXE) w
         * symulowanym endpointcie - zwroc uzytkownikowi OGOLNY komunikat
         * (bez ujawniania sciezek plikow z komunikatu wyjatku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestXxeAgainstInternalNetworkAddressConceptually {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: opisz, jak atakujacy MOGLBY uzyc XXE do
         * odpytania WEWNETRZNYCH adresow sieciowych (np.
         * `http://169.254.169.254/` - typowy adres metadanych w chmurze)
         * niedostepnych z zewnatrz - dlaczego to POWAZNE ryzyko w
         * srodowiskach chmurowych.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildReusableSecureXmlParserFactory {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj klase narzedziowa `SecureXmlParsers` z metoda
         * statyczna zwracajaca ZAWSZE bezpiecznie skonfigurowany
         * `DocumentBuilderFactory` - uzyj jej we WSZYSTKICH miejscach
         * parsujacych XML w tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementXxeProtectionForXsltTransformer {
        /*
         * 🧪 Zadanie 22:
         * Zbadaj i zabezpiecz `TransformerFactory` (uzywany przy
         * transformacjach XSLT) przed analogicznym atakiem - zweryfikuj
         * na prostym przykladzie transformacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BenchmarkParsingOverheadWithSecureSettings {
        /*
         * 🧪 Zadanie 23:
         * Zmierz czas parsowania 10 000 normalnych (bez DOCTYPE)
         * dokumentow XML Z i BEZ zabezpieczen - potwierdz brak istotnego
         * narzutu wydajnosciowego dla ZWYKLEGO uzycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementXxeDetectionAndAlertingBeforeParsing {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj WSTEPNA (przed pelnym parsowaniem) heurystyke
         * wykrywajaca DOCTYPE w surowym tekscie XML - wyjasnij, dlaczego
         * to TYLKO dodatkowa warstwa (WAF-owa), NIE zastepuje
         * prawidlowej konfiguracji parsera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SimulateSsrfViaXxeWithLocalHttpServer {
        /*
         * 🧪 Zadanie 25:
         * Uruchom lokalny `HttpServer` (symulujacy "wewnetrzna usluge") i
         * zademonstruj, jak PODATNY parser XML MOGLBY (encja `SYSTEM
         * "http://localhost:<port>/..."`) wykonac zadanie do niego -
         * potwierdz naprawe zabezpieczonym parserem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AuditAllXmlEntryPointsInHypotheticalApp {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj liste "hipotetycznych" endpointow aplikacji przyjmujacych
         * XML (upload pliku, webhook, import danych, SOAP) - dla kazdego
         * zaprojektuj odpowiednie zabezpieczenie parsera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementXxeRegressionTestSuite {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj zestaw testowy (payload+oczekiwany rezultat) uruchamiany
         * w `main()` sprawdzajacy WSZYSTKIE warianty XXE z Zadania 16
         * wobec finalnej konfiguracji parsera - wypisz PASS/FAIL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareXxeWithOtherInjectionClassesLearned {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj (tabela w komentarzu) XXE, SQL
         * Injection (Lesson13) i Insecure Deserialization (Lesson14) pod
         * katem WSPOLNEGO mianownika - "niezaufane dane interpretowane
         * jako COS WIECEJ niz zwykle dane" (kod/zapytanie/instrukcje
         * parsera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFileUploadValidationForXmlContentType {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj walidacje endpointu przyjmujacego upload plikow -
         * pliki z `Content-Type: application/xml`/`text/xml` MUSZA
         * przechodzic przez zabezpieczony parser (Zadanie 21) PRZED
         * jakimkolwiek dalszym przetwarzaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureXmlProcessingPipeline {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "pipeline" przetwarzania XML: walidacja
         * Content-Type (Zadanie 29) + bezpieczny parser (Zadanie 21) +
         * obsluga bledow bez wycieku szczegolow (Zadanie 19) + testy
         * regresyjne (Zadanie 27) - zweryfikuj co najmniej 4 scenariusze
         * z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
