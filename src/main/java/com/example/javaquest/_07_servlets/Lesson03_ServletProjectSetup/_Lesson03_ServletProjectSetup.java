package com.example.javaquest._07_servlets.Lesson03_ServletProjectSetup;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson03_ServletProjectSetup {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 SKĄD BIERZE SIĘ jakarta.servlet.* I org.apache.catalina.*?
         * ============================================================
         * Żeby pisać i uruchamiać serwlety, potrzebujemy dwóch rzeczy
         * na classpath:
         *
         * 1) API Servletów (jakarta.servlet.*) - interfejsy i klasy takie
         *    jak Servlet, HttpServlet, HttpServletRequest/Response. To
         *    tylko SPECYFIKACJA (kontrakty) - same w sobie nic nie robią.
         * 2) IMPLEMENTACJĘ kontenera (np. Apache Tomcat) - konkretny kod,
         *    który te kontrakty realizuje: otwiera gniazdo TCP, parsuje
         *    HTTP, woła metody serwletu.
         *
         * W TYM PROJEKCIE obu tych rzeczy dostarcza jedna zależność,
         * którą już mamy w pom.xml: spring-boot-starter-web. Spring Boot
         * "pod spodem" ciągnie transytywnie moduł tomcat-embed-core (i
         * kilka innych modułów Tomcata), a ten z kolei niesie ze sobą
         * kompletne API jakarta.servlet.*. Dlatego w całym tym rozdziale
         * NIE dodawaliśmy żadnej nowej zależności - wszystko, czego
         * potrzebujemy, już tu jest.
         *
         * 🔍 A W ZUPEŁNIE NOWYM, "CZYSTYM" PROJEKCIE (bez Spring Boota)?
         * Trzeba by dodać zależność RĘCZNIE, np. w Mavenie:
         *
         *   <dependency>
         *       <groupId>org.apache.tomcat.embed</groupId>
         *       <artifactId>tomcat-embed-core</artifactId>
         *       <version>10.1.x</version>
         *   </dependency>
         *
         * To dokładnie ten sam moduł, który Spring Boot dociąga za nas
         * transytywnie - "osadzony" (embeddable) silnik Tomcata jako
         * zwykła biblioteka Javy, gotowa do programowego uruchomienia.
         */

        /*
         * ============================================================
         * 🔹 jakarta.servlet.* vs javax.servlet.* - UWAGA NA WERSJE!
         * ============================================================
         * Do 2019 roku cała specyfikacja nazywała się Java EE i pakiety
         * miały prefiks javax.servlet.*. Gdy Oracle przekazał Java EE
         * fundacji Eclipse, powstała Jakarta EE - a Eclipse Foundation z
         * przyczyn licencyjnych (znak towarowy "javax" należy do Oracle)
         * musiał przemianować WSZYSTKIE pakiety z javax.* na jakarta.*.
         * Zmiana nazwy pakietu weszła w życie od specyfikacji Jakarta EE
         * 9 (2020) - to CZYSTO KOSMETYCZNA, ale WIELKA zmiana: kod pod
         * starym API (javax.servlet.HttpServlet) nie skompiluje się pod
         * nowym API (jakarta.servlet.HttpServlet) i odwrotnie, mimo że
         * funkcjonalnie klasy są niemal identyczne.
         *
         * Co to oznacza w praktyce:
         * - Tomcat 8 i 9 -> API javax.servlet.* (Jakarta EE 8 i starsze).
         * - Tomcat 10 i nowsze -> API jakarta.servlet.* (Jakarta EE 9+).
         * - Ten kurs używa Tomcata 10.1.x (patrz import
         *   org.apache.catalina.startup.Tomcat poniżej) - dlatego WSZĘDZIE
         *   importujemy jakarta.servlet.*, nigdy javax.servlet.*.
         *
         * 📌 To częsty błąd przy migracji starszych projektów Spring/Java
         * EE: mieszanie bibliotek napisanych pod starą (javax) i nową
         * (jakarta) specyfikację kończy się błędami kompilacji albo (gorzej)
         * cichym brakiem działania w runtime (ClassNotFoundException,
         * serwlet "niewidziany" przez kontener).
         */

        System.out.println("=== LESSON 3: Konfiguracja projektu servletowego ===\n");

        /*
         * ============================================================
         * 🔍 WZORZEC: OSADZONY TOMCAT KROK PO KROKU
         * ============================================================
         * Poniższy wzorzec (embedded Tomcat uruchamiany wprost z main())
         * będzie się powtarzał w KAŻDEJ kolejnej lekcji tego rozdziału -
         * tu omawiamy go raz, dokładnie, linia po linii.
         */

        // KROK 1: nowa instancja obiektu Tomcat - to sam "silnik", jeszcze
        // nieuruchomiony i bez żadnej skonfigurowanej aplikacji.
        Tomcat tomcat = new Tomcat();

        // KROK 2: katalog bazowy (baseDir) - Tomcat, nawet w trybie
        // osadzonym, potrzebuje jakiegoś katalogu roboczego na dysku
        // (np. na pliki tymczasowe, cache JSP, logi pracy). Katalog MUSI
        // istnieć - dlatego tworzymy świeży katalog tymczasowy zamiast
        // podawać np. nieistniejącą ścieżkę na sztywno. Files.createTempDirectory
        // sam tworzy katalog i zwraca do niego Path.
        Path baseDir = Files.createTempDirectory("lesson03");
        tomcat.setBaseDir(baseDir.toString());
        System.out.println("Katalog bazowy Tomcata: " + baseDir);

        // KROK 3: port 0 - to specjalna wartość mówiąca systemowi
        // operacyjnemu "wybierz dowolny WOLNY port efemeryczny". Dzięki
        // temu ta lekcja (i każda inna w tym rozdziale) może się uruchomić
        // RÓWNOLEGLE z innymi bez ryzyka konfliktu portów (np. gdybyśmy na
        // sztywno wpisali port 8080, a coś innego już go zajmowało).
        // WAŻNE: getConnector() trzeba wywołać PRZED tomcat.start() -
        // dopiero wtedy możemy ustawić na nim port; connector tworzony jest
        // "leniwie" przy pierwszym wywołaniu getConnector().
        tomcat.getConnector().setPort(0);

        // KROK 4: kontekst aplikacji - "" oznacza kontekst główny (root),
        // czyli aplikacja odpowiada pod http://host:port/ZAMIAST
        // http://host:port/nazwa-aplikacji/. Drugi argument (null) to
        // ścieżka do katalogu z zasobami statycznymi aplikacji (docRoot) -
        // dla naszych czysto-programowych demo serwletów jej nie
        // potrzebujemy.
        Context context = tomcat.addContext("", null);

        // KROK 5: rejestracja serwletu w kontekście - Tomcat.addServlet
        // (metoda statyczna) tworzy DEFINICJĘ serwletu o podanej nazwie
        // ("configServlet") i przypisuje ją do danego kontekstu. Nazwa
        // służy tylko do identyfikacji wewnątrz kontenera (np. w logach),
        // nie jest częścią URL-a.
        Tomcat.addServlet(context, "configServlet", new SetupInfoServlet());

        // KROK 6: mapowanie URL -> serwlet - dopiero addServletMappingDecoded
        // mówi kontenerowi, pod jaką ścieżką URL serwlet ma odpowiadać.
        // Bez tego kroku serwlet byłby zarejestrowany, ale NIEOSIĄGALNY
        // (żaden URL by go nie wywoływał). "Decoded" oznacza, że ścieżkę
        // podajemy już zdekodowaną (bez %-escapingu URL).
        context.addServletMappingDecoded("/setup", "configServlet");

        try {
            // KROK 7: dopiero teraz serwer faktycznie zaczyna nasłuchiwać
            // na gnieździe TCP.
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            System.out.println("Tomcat wystartowal na wylosowanym porcie: " + port);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/setup"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
        } finally {
            // KROK 8: try/finally jest tu KLUCZOWE - nawet jeśli coś w
            // bloku try rzuci wyjątek (np. serwlet się wysypie, klient
            // dostanie błąd sieciowy), MUSIMY zwolnić zasoby serwera:
            // - stop() zamyka gniazdo nasłuchujące i zatrzymuje wątki,
            // - destroy() zwalnia wewnętrzne struktury kontenera.
            // Bez tego JVM (a w testach/demo - kolejne lekcje) mógłby
            // zostawić "wiszący" wątek nasłuchujący na porcie, co
            // uniemożliwiłoby czyste zakończenie programu albo powodowałoby
            // wyciek zasobów przy wielokrotnym uruchamianiu.
            tomcat.stop();
            tomcat.destroy();
            System.out.println("Tomcat zatrzymany i zniszczony (stop + destroy w finally).");
        }

        System.out.println("\n=== KONIEC LEKCJI 3 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Do pisania i uruchamiania serwletów potrzeba dwóch elementów:
         *   API (jakarta.servlet.*) oraz implementacji kontenera
         *   (tu: org.apache.catalina.* z Tomcata).
         * - W TYM PROJEKCIE oba dostarcza spring-boot-starter-web
         *   transytywnie (tomcat-embed-core) - żadna nowa zależność nie
         *   jest potrzebna. W czystym projekcie bez Spring Boota dodaje
         *   się tomcat-embed-core ręcznie do pom.xml/build.gradle.
         * - jakarta.servlet.* (Jakarta EE 9+, Tomcat 10+) zastąpiło stare
         *   javax.servlet.* (Tomcat 8/9 i starsze specyfikacje Java EE) -
         *   to zmiana nazwy pakietu, nie kompatybilna binarnie ani
         *   źródłowo - trzeba pilnować spójności wersji przy mieszaniu
         *   bibliotek.
         * - Kanoniczny wzorzec embedded Tomcata: new Tomcat() -> setBaseDir
         *   (katalog MUSI istnieć) -> getConnector().setPort(0) (wolny
         *   port, PRZED start()) -> addContext("", null) -> addServlet +
         *   addServletMappingDecoded -> start() w try, stop()+destroy()
         *   w finally.
         * - Ten dokładny wzorzec będzie się powtarzał (z coraz mniejszym
         *   komentarzem "dlaczego") w każdej kolejnej lekcji tego
         *   rozdziału.
         */
    }

    /**
     * Prosty serwlet demonstracyjny - potwierdza, ze cala konfiguracja
     * (zaleznosci, pakiety, wzorzec uruchomieniowy) dziala poprawnie.
     */
    static class SetupInfoServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write("Projekt servletowy skonfigurowany poprawnie - jakarta.servlet.* + embedded Tomcat dzialaja.");
        }
    }
}
