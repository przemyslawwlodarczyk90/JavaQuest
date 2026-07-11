package com.example.javaquest._21_spring_boot.Lesson02_ProjectSetup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class _Lesson02_ProjectSetup {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: JAK ZAKLADA SIE PROJEKT SPRING BOOT ===");

        /*
         * ============================================================
         * 📦 SPRING INITIALIZR (start.spring.io) - KONCEPCYJNIE
         * ============================================================
         * W praktyce projekt Spring Boot zakłada się przez Spring
         * Initializr (web.: start.spring.io, wbudowany też w IntelliJ
         * IDEA/VS Code) - wybierasz build tool (Maven/Gradle), wersję
         * Javy, "starter" zależności (Lesson03) - Initializr GENERUJE
         * gotowy szkielet projektu (pom.xml/build.gradle + klasa
         * `@SpringBootApplication` + pusty test). TEN kurs już MA taki
         * projekt (widzisz go od `_01_fundamentals`) - dzisiaj
         * przeanalizujemy REALNY `pom.xml` tego projektu, DOKŁADNIE
         * jakby właśnie wygenerował go Initializr.
         */
        System.out.println("Spring Initializr generuje szkielet projektu - Ty wybierasz build tool/Jave/startery, ono generuje pom.xml + klase startowa.");

        demonstrateMavenStandardDirectoryLayout();
        demonstrateRealPomXmlStartersOfThisProject();

        /*
         * ============================================================
         * 🔹 MAVEN vs GRADLE (PRZYPOMNIENIE Z `_11_buildtools`)
         * ============================================================
         * Initializr generuje ROWNIE dobrze projekt Gradle
         * (`build.gradle`/`build.gradle.kts`) jak Maven (`pom.xml`) -
         * TEN kurs (i caly `_11_buildtools`) uzywa Mavena, ale WSZYSTKO,
         * czego uczysz sie w tym rozdziale (starter, auto-konfiguracja,
         * itd.) jest IDENTYCZNE niezaleznie od wybranego build toola -
         * to mechanizmy Springa, nie Mavena/Gradle.
         */
        System.out.println("\nMaven vs Gradle (patrz _11_buildtools) - Initializr generuje OBA, mechanizmy Springa (startery/auto-konfiguracja) sa IDENTYCZNE w obu.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Spring Initializr generuje SZKIELET (build file + klasa
         *   startowa + pusty test) - nie musisz pisac tego RECZNIE.
         * - Maven Standard Directory Layout (`_11_buildtools`) - `src/
         *   main/java`, `src/main/resources`, `src/test/java` - Spring
         *   Boot NIE WYMYSLA nowej struktury, uzywa TEJ SAMEJ konwencji.
         * - "Startery" (widziane dzisiaj w realnym `pom.xml`) to temat
         *   NASTEPNEJ lekcji - dzisiaj tylko je ZAUWAZYLES.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateMavenStandardDirectoryLayout() {
        System.out.println("\n=== STANDARDOWA STRUKTURA KATALOGOW (Maven Standard Directory Layout) ===");

        Path projectRoot = locateProjectRoot();
        if (projectRoot == null) {
            System.out.println("Nie znaleziono katalogu glownego projektu - pomijam ten fragment demo.");
            return;
        }

        String[] expectedDirs = {
                "src/main/java", "src/main/resources", "src/test/java"
        };
        for (String dir : expectedDirs) {
            Path path = projectRoot.resolve(dir.replace("/", java.io.File.separator));
            System.out.println("  " + dir + " -> istnieje: " + Files.isDirectory(path));
        }
        System.out.println("-> Spring Initializr generuje projekt WEDLUG tej SAMEJ konwencji, ktora juz znasz z `_11_buildtools` (Maven Standard Directory Layout).");
    }

    private static Path locateProjectRoot() {
        Path candidate = Path.of("pom.xml").toAbsolutePath();
        Path dir = candidate.getParent();
        for (int i = 0; i < 5 && dir != null; i++) {
            Path pom = dir.resolve("pom.xml");
            if (Files.isRegularFile(pom)) {
                return dir;
            }
            dir = dir.getParent();
        }
        return null;
    }

    private record Dependency(String groupId, String artifactId) {
    }

    private static void demonstrateRealPomXmlStartersOfThisProject() {
        System.out.println("\n=== REALNE STARTERY W pom.xml TEGO PROJEKTU (JAKBY WYGENEROWAL JE INITIALIZR) ===");

        Path projectRoot = locateProjectRoot();
        if (projectRoot == null) {
            System.out.println("Nie znaleziono pom.xml - pomijam ten fragment demo (uruchom przez 'mvnw.cmd exec:java' z katalogu projektu).");
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(projectRoot.resolve("pom.xml").toFile());
            document.getDocumentElement().normalize();

            List<Dependency> starters = new ArrayList<>();
            NodeList topLevelNodes = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < topLevelNodes.getLength(); i++) {
                Node node = topLevelNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && "dependencies".equals(node.getNodeName())) {
                    NodeList dependencyNodes = node.getChildNodes();
                    for (int j = 0; j < dependencyNodes.getLength(); j++) {
                        Node depNode = dependencyNodes.item(j);
                        if (depNode.getNodeType() == Node.ELEMENT_NODE && "dependency".equals(depNode.getNodeName())) {
                            Element depElement = (Element) depNode;
                            String artifactId = childText(depElement, "artifactId");
                            if (artifactId != null && artifactId.startsWith("spring-boot-starter")) {
                                starters.add(new Dependency(childText(depElement, "groupId"), artifactId));
                            }
                        }
                    }
                }
            }

            System.out.println("Znaleziono " + starters.size() + " starterow Spring Boota w TYM projekcie:");
            for (Dependency starter : starters) {
                System.out.println("  - " + starter.groupId() + ":" + starter.artifactId());
            }
            System.out.println("-> DOKLADNIE tak wygladalby wybor 'Dependencies' w Spring Initializr dla TEGO projektu - kazdy starter to jedna 'zaznaczona kratka' na start.spring.io.");
        } catch (Exception e) {
            System.out.println("Nie udalo sie sparsowac pom.xml (" + e.getMessage() + ") - pomijam ten fragment demo.");
        }
    }

    private static String childText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return null;
        }
        return nodes.item(0).getTextContent();
    }
}
