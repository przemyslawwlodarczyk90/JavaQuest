package com.example.javaquest._07_servlets.Lesson02_ServletContainers;

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

public class _Lesson02_ServletContainers {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CO DOKŁADNIE ROBI KONTENER SERWLETÓW?
         * ============================================================
         * W Lesson01 poznaliśmy cykl życia serwletu. Ta lekcja skupia się
         * na samym KONTENERZE – programie, który ten cykl życia obsługuje.
         * Kontener serwletów odpowiada za:
         *
         * 1) PARSOWANIE HTTP – odbiera surowe bajty z gniazda TCP,
         *    interpretuje linię żądania, nagłówki, ciało, buduje z tego
         *    wygodne obiekty HttpServletRequest/HttpServletResponse.
         * 2) PULA WĄTKÓW – każde przychodzące żądanie jest obsługiwane
         *    (zwykle) przez osobny wątek z puli, żeby serwer mógł
         *    obsługiwać wielu klientów naraz (patrz rozdział
         *    _05_multithreading – to klasyczny przypadek I/O-bound).
         * 3) ZARZĄDZANIE SESJĄ – utrzymuje HttpSession dla użytkownika
         *    pomiędzy kolejnymi żądaniami (przez ciasteczko JSESSIONID) –
         *    dokładnie w Lesson11.
         * 4) BEZPIECZEŃSTWO – uwierzytelnianie, autoryzacja, HTTPS/TLS,
         *    ograniczenia dostępu do zasobów.
         * 5) MAPOWANIE URL -> SERWLET – na podstawie ścieżki żądania
         *    (np. "/hello") wybiera właściwą instancję serwletu.
         *
         * Innymi słowy: kontener to "silnik webowy", a serwlety to
         * "logika biznesowa" osadzona w tym silniku.
         */

        /*
         * ============================================================
         * 🔹 NAJPOPULARNIEJSZE KONTENERY SERWLETÓW
         * ============================================================
         * APACHE TOMCAT
         * - Najpopularniejszy, "referencyjny" kontener serwletów w Javie.
         * - Rozwijany przez Apache Software Foundation, darmowy, open-source.
         * - Używany m.in. jako domyślny serwer w Spring Boot
         *   (spring-boot-starter-web) - dokładnie ten używamy w tym kursie!
         * - Dojrzały, dobrze udokumentowany, ogromna społeczność.
         *
         * JETTY
         * - Lekki, modularny kontener, często używany jako serwer OSADZONY
         *   (embedded) w innych narzędziach (np. w starszych wersjach
         *   Elasticsearch, w narzędziach do budowania jak Maven).
         * - Bardzo szybki start, mały narzut pamięciowy.
         *
         * UNDERTOW
         * - Kontener od Red Hat/JBoss, używany w WildFly.
         * - Wysoka wydajność, non-blocking I/O "z natury", niski narzut.
         * - Alternatywny serwer wbudowany w Spring Boot (zamiast Tomcata).
         *
         * 📌 Wszystkie trzy implementują tę samą specyfikację Servlet API
         * (jakarta.servlet.*) - kod Twojego serwletu w dużej mierze NIE
         * MUSI się zmieniać przy zmianie kontenera (to cała siła
         * standaryzacji API).
         */

        /*
         * ============================================================
         * 🔍 EMBEDDED vs EXTERNAL (CLASSIC) - DWA MODELE WDROŻENIA
         * ============================================================
         * MODEL KLASYCZNY (external / standalone):
         * - Instalujesz i uruchamiasz Tomcata jako osobny, samodzielny
         *   proces/serwis na maszynie (np. rozpakowujesz archiwum Tomcata,
         *   uruchamiasz skrypt startup.sh/startup.bat).
         * - Swoją aplikację pakujesz jako plik WAR (Web Application
         *   Archive) i wrzucasz go do folderu webapps/ tego serwera.
         * - Tomcat sam wykrywa nowy WAR, rozpakowuje go i uruchamia.
         * - To klasyczny model sprzed czasów Spring Boota - wciąż używany
         *   w wielu firmowych środowiskach (WAR na współdzielonym serwerze
         *   aplikacyjnym).
         *
         * MODEL OSADZONY (embedded):
         * - Kontener (np. Tomcat) jest zwykłą BIBLIOTEKĄ (zależnością
         *   Mavena/Gradle) dodaną do Twojej aplikacji.
         * - To Twój kod (metoda main()) programowo tworzy instancję
         *   serwera, konfiguruje ją i uruchamia - serwer żyje "wewnątrz"
         *   Twojego procesu JVM.
         * - Aplikację pakujesz jako zwykły, "gruby" plik JAR
         *   (fat/uber JAR) zawierający i Twój kod, i serwer - uruchamiasz
         *   go poleceniem "java -jar aplikacja.jar", bez osobnej
         *   instalacji serwera.
         * - To DOKŁADNIE model używany przez Spring Boota (i przez CAŁY
         *   ten rozdział kursu!) - dzięki niemu każda lekcja jest
         *   samodzielnym, uruchamialnym programem, bez potrzeby
         *   instalowania czegokolwiek na Twoim komputerze.
         *
         * 📌 WAR vs JAR w skrócie:
         * - WAR - archiwum aplikacji webowej, wymaga ZEWNĘTRZNEGO
         *   kontenera, w którym zostanie wdrożone (deployed).
         * - JAR (z osadzonym serwerem) - samowystarczalny plik, sam w
         *   sobie jest kompletną, uruchamialną aplikacją webową.
         */

        System.out.println("=== LESSON 2: Kontenery serwletów ===");
        System.out.println("Ten kurs uzywa modelu EMBEDDED - ponizej krotka demonstracja,");
        System.out.println("ze to naprawde ten sam Tomcat co w klasycznym WAR-owym wdrozeniu,");
        System.out.println("tylko uruchomiony programowo z poziomu main().\n");

        // Minimalna demonstracja - szczegolowe omowienie kazdej linii wzorca
        // (setBaseDir, port 0, addContext, try/finally) jest w Lesson03.
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("lesson02").toString());
        tomcat.getConnector().setPort(0);

        Context context = tomcat.addContext("", null);
        Tomcat.addServlet(context, "infoServlet", new ContainerInfoServlet());
        context.addServletMappingDecoded("/info", "infoServlet");

        try {
            tomcat.start();
            int port = tomcat.getConnector().getLocalPort();
            System.out.println("Embedded Tomcat wystartowal na porcie: " + port);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/info"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status: " + response.statusCode());
            System.out.println("Body: " + response.body());
        } finally {
            tomcat.stop();
            tomcat.destroy();
        }

        System.out.println("\n=== KONIEC LEKCJI 2 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Kontener serwletow = program zarzadzajacy cyklem zycia
         *   serwletow, parsowaniem HTTP, pula watkow, sesjami,
         *   bezpieczenstwem i routingiem URL -> serwlet.
         * - Trzy popularne implementacje: Apache Tomcat (najpopularniejszy,
         *   uzywany w Spring Boot), Jetty (lekki, czesto embedded),
         *   Undertow (wysoka wydajnosc, uzywany w WildFly/Quarkus).
         * - Model KLASYCZNY: pakujesz aplikacje jako WAR, wdrazasz w
         *   zewnetrznym, juz uruchomionym serwerze (folder webapps/).
         * - Model EMBEDDED: serwer to biblioteka, Twoj main() go
         *   programowo tworzy i uruchamia; aplikacja to samodzielny JAR.
         * - Ten kurs (i caly rozdzial _07_servlets) uzywa modelu embedded -
         *   dzieki temu kazda lekcja jest w pelni samodzielna i
         *   uruchamialna bez zadnej zewnetrznej instalacji serwera.
         */
    }

    /**
     * Prosty serwlet zwracajacy informacje tekstowe - ilustruje, ze mimo
     * "programowego" uruchomienia to nadal ten sam, standardowy Servlet API.
     */
    static class ContainerInfoServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain");
            resp.getWriter().println("Ten serwlet dziala wewnatrz osadzonego (embedded) Apache Tomcata.");
        }
    }
}
