package com.example.javaquest._19_security_basics.Lesson20_DependencyAndSupplyChainSecurity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class _Lesson20_DependencyAndSupplyChainSecurity {

    private record Dependency(String groupId, String artifactId, String version, String scope) {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20: BEZPIECZENSTWO ZALEZNOSCI I LANCUCHA DOSTAW ===");

        /*
         * ============================================================
         * 📦 PROBLEM: KOD, KTOREGO NIE NAPISALES, ALE MU UFASZ
         * ============================================================
         * Kazdy projekt Maven/Gradle (patrz `_11_buildtools`) ciagnie
         * dziesiatki, czasem SETKI zaleznosci TRANZYTYWNYCH (zaleznosci
         * TWOICH zaleznosci) - nikt ich pojedynczo nie przegladal linia
         * po linii. "Lancuch dostaw oprogramowania" (software supply
         * chain) to WSZYSTKO, co trafia do Twojej aplikacji z zewnatrz:
         * biblioteki, wtyczki buildu, obrazy bazowe Dockera. Atak na
         * JEDNO ogniwo (popularna biblioteke) uderza we WSZYSTKICH jej
         * uzytkownikow naraz - to dlatego to osobna, wazna kategoria
         * bezpieczenstwa (OWASP A06 i A08, patrz `Lesson21`).
         */
        System.out.println("Zaleznosci tranzytywne = kod, ktorego nie napisales, ale mu ufasz - atak na 1 popularna biblioteke uderza we WSZYSTKICH jej uzytkownikow.");

        List<Dependency> dependencies = readProjectDependencies();
        demonstrateMiniSbomFromRealPomXml(dependencies);
        demonstrateTyposquattingRisk();
        demonstrateDependencyConfusionConcept();
        demonstrateVersionPinningVsRanges();

        /*
         * ============================================================
         * 🔹 NARZEDZIA AUTOMATYCZNE (KONCEPCYJNIE)
         * ============================================================
         * - `mvn dependency:tree` (patrz `_11_buildtools/Lesson18`) -
         *   pokazuje PELNE drzewo zaleznosci, w tym tranzytywnych, oraz
         *   rozwiazane konflikty wersji.
         * - OWASP Dependency-Check / Snyk / GitHub Dependabot - skanuja
         *   zaleznosci wobec baz ZNANYCH podatnosci (CVE) i automatycznie
         *   proponuja aktualizacje (Dependabot potrafi nawet sam otworzyc
         *   Pull Request).
         * - Weryfikacja SUM KONTROLNYCH/PODPISOW (checksums/signatures) -
         *   Maven Central wymaga podpisu GPG kazdego artefaktu (patrz
         *   `_11_buildtools/Lesson17_MavenPublishing`) - utrudnia
         *   podmiane paczki na zlosliwa wersje PO fakcie.
         */
        System.out.println("\nNarzedzia: `mvn dependency:tree` (drzewo zaleznosci), OWASP Dependency-Check/Snyk/Dependabot (skan CVE), podpisy GPG (weryfikacja autentycznosci).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Kazda zaleznosc (zwlaszcza TRANZYTYWNA) to rozszerzenie
         *   "powierzchni ataku" Twojej aplikacji - ufasz kodowi, ktorego
         *   nie napisales i pewnie nigdy nie przeczytales.
         * - SBOM (Software Bill of Materials) to "lista skladnikow"
         *   aplikacji - niezbedna, zeby SZYBKO odpowiedziec na pytanie
         *   "czy jestesmy podatni na CVE-2026-XXXXX?" bez przeszukiwania
         *   kodu recznie.
         * - Typosquatting i dependency confusion to ataki wykorzystujace
         *   ZAUFANIE do nazw pakietow/repozytoriow - obrona to uwaznosc
         *   przy dodawaniu zaleznosci + jawna konfiguracja repozytoriow.
         * - Pinuj (zamrazaj) DOKLADNE wersje zaleznosci produkcyjnych -
         *   zakresy wersji (`[1.0,2.0)`) moga ZASKAKUJACO podciagnac
         *   nowa, jeszcze niesprawdzona (lub zlosliwa) wersje.
         */
        System.out.println("\n=== KONIEC LEKCJI 20 ===");
    }

    private static Path locateProjectPomXml() {
        Path candidate = Path.of("pom.xml").toAbsolutePath();
        Path dir = candidate.getParent();
        for (int i = 0; i < 5 && dir != null; i++) {
            Path pom = dir.resolve("pom.xml");
            if (Files.isRegularFile(pom)) {
                return pom;
            }
            dir = dir.getParent();
        }
        return null;
    }

    private static List<Dependency> readProjectDependencies() {
        Path pomPath = locateProjectPomXml();
        if (pomPath == null) {
            System.out.println("\n=== ODCZYT REALNEGO 'pom.xml' TEGO PROJEKTU ===");
            System.out.println("Nie znaleziono 'pom.xml' w drzewie katalogow uruchomienia - pomijam ten fragment demo (uruchom przez 'mvnw.cmd exec:java' z katalogu projektu).");
            return List.of();
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(pomPath.toFile());
            document.getDocumentElement().normalize();

            Element projectElement = document.getDocumentElement();
            List<Dependency> result = new ArrayList<>();

            NodeList topLevelNodes = projectElement.getChildNodes();
            for (int i = 0; i < topLevelNodes.getLength(); i++) {
                Node node = topLevelNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && "dependencies".equals(node.getNodeName())) {
                    NodeList dependencyNodes = node.getChildNodes();
                    for (int j = 0; j < dependencyNodes.getLength(); j++) {
                        Node depNode = dependencyNodes.item(j);
                        if (depNode.getNodeType() == Node.ELEMENT_NODE && "dependency".equals(depNode.getNodeName())) {
                            result.add(parseDependencyElement((Element) depNode));
                        }
                    }
                }
            }

            System.out.println("\n=== ODCZYT REALNEGO 'pom.xml' TEGO PROJEKTU ===");
            System.out.println("Wczytano " + result.size() + " BEZPOSREDNICH zaleznosci z " + pomPath + " (bez tranzytywnych - te ujawnia dopiero 'mvn dependency:tree').");
            return result;
        } catch (Exception e) {
            System.out.println("\n=== ODCZYT REALNEGO 'pom.xml' TEGO PROJEKTU ===");
            System.out.println("Nie udalo sie sparsowac pom.xml (" + e.getMessage() + ") - pomijam ten fragment demo.");
            return List.of();
        }
    }

    private static Dependency parseDependencyElement(Element dependencyElement) {
        String groupId = childText(dependencyElement, "groupId", "?");
        String artifactId = childText(dependencyElement, "artifactId", "?");
        String version = childText(dependencyElement, "version", "(zarzadzana przez BOM)");
        String scope = childText(dependencyElement, "scope", "compile");
        return new Dependency(groupId, artifactId, version, scope);
    }

    private static String childText(Element parent, String tagName, String defaultValue) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return defaultValue;
        }
        String text = nodes.item(0).getTextContent();
        return (text == null || text.isBlank()) ? defaultValue : text.trim();
    }

    private static void demonstrateMiniSbomFromRealPomXml(List<Dependency> dependencies) {
        if (dependencies.isEmpty()) {
            return;
        }

        System.out.println("\n=== MINI SBOM (SOFTWARE BILL OF MATERIALS) WYGENEROWANY Z REALNYCH ZALEZNOSCI ===");
        System.out.println("SBOM = 'lista skladnikow' aplikacji (formaty w praktyce: CycloneDX, SPDX) - tu uproszczona, wlasna forma tekstowa:");

        for (Dependency dependency : dependencies) {
            System.out.println(String.format("  - %s:%s@%s (scope=%s)",
                    dependency.groupId(), dependency.artifactId(), dependency.version(), dependency.scope()));
        }

        System.out.println("-> majac taka liste, na pytanie 'czy uzywamy podatnej biblioteki X w wersji < 2.5?' odpowiadasz w SEKUNDY, nie godziny przeszukiwania kodu.");
    }

    private static void demonstrateTyposquattingRisk() {
        System.out.println("\n=== TYPOSQUATTING: ZLOSLIWY PAKIET O NAZWIE PODOBNEJ DO ZNANEJ ===");

        String legitArtifact = "commons-lang3";
        String[] suspiciousLookalikes = {"commons-lang", "common-lang3", "commons-lang3-utils"};

        System.out.println("Legalny, znany artefakt: '" + legitArtifact + "' (Apache Commons Lang3, patrz `_13_libraries/Lesson6`).");
        for (String lookalike : suspiciousLookalikes) {
            System.out.println("Podejrzanie podobna nazwa: '" + lookalike + "' - atakujacy LICZY na literowke/pomylke przy `mvn dependency:add`/kopiowaniu z niezaufanego zrodla (bloga/StackOverflow).");
        }
        System.out.println("-> obrona: kopiuj wspolrzedne Maven TYLKO z Maven Central/oficjalnej dokumentacji projektu (ta sama zasada co przy weryfikacji wersji w tym kursie - patrz notatki w CLAUDE.md o BCrypt/JJWT/PMD).");
    }

    private static void demonstrateDependencyConfusionConcept() {
        System.out.println("\n=== DEPENDENCY CONFUSION: PUBLICZNE VS PRYWATNE REPOZYTORIUM ===");

        System.out.println("Firma ma WEWNETRZNY pakiet 'com.firma:core-utils:1.0' w PRYWATNYM repozytorium (np. Nexus/Artifactory) - nigdy niepublikowany publicznie.");
        System.out.println("Atakujacy publikuje NA MAVEN CENTRAL pakiet o TEJ SAMEJ nazwie 'com.firma:core-utils', ale z WYZSZYM numerem wersji (np. 99.0).");
        System.out.println("Jesli build narzedzie jest zle skonfigurowane (przeszukuje NAJPIERW/ROWNOLEGLE publiczne repo), moze pobrac ZLOSLIWA, publiczna wersje zamiast wewnetrznej!");
        System.out.println("-> obrona: jawna konfiguracja repozytoriow (priorytet/wylacznosc dla prywatnego repo dla wlasnych grup pakietow), rezerwacja nazwy grupy tez publicznie, jesli to mozliwe.");
    }

    private static void demonstrateVersionPinningVsRanges() {
        System.out.println("\n=== PINOWANIE WERSJI vs ZAKRESY WERSJI ===");

        System.out.println("Zakres wersji w Maven, np. '[1.0,2.0)' - build MOZE automatycznie podciagnac KAZDA nowa wersje w tym przedziale, WLACZNIE z ta opublikowana WCZORAJ.");
        System.out.println("Dokladnie PINOWANA wersja, np. '1.4.2' - build ZAWSZE uzywa DOKLADNIE tej, przetestowanej wersji - nowa wersja wymaga SWIADOMEJ decyzji (zmiana w pom.xml + code review).");
        System.out.println("-> w projektach produkcyjnych PINUJ dokladne wersje (tak jak ten projekt robi to dla jbcrypt/jjwt w pom.xml) - aktualizuj SWIADOMIE, nie 'automagicznie'.");
    }
}
