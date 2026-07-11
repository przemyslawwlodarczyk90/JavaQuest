package com.example.javaquest._19_security_basics.Lesson08_HttpsTlsBasics;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.concurrent.TimeUnit;

public class _Lesson08_HttpsTlsBasics {

    private static final String STORE_PASSWORD = "changeit";

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 8: PODSTAWY HTTPS I TLS ===");

        /*
         * ============================================================
         * 📦 PO CO HTTPS? PROBLEM ZWYKLEGO HTTP
         * ============================================================
         * Zwykly HTTP przesyla dane JAWNYM TEKSTEM przez siec - kazdy
         * "posrodku" (publiczne WiFi, dostawca internetu, atakujacy w tej
         * samej sieci) MOZE PODSLUCHAC (hasla, ciasteczka z Lesson04,
         * tokeny JWT z Lesson05!) lub nawet ZMODYFIKOWAC ruch (atak
         * "man-in-the-middle"). HTTPS = HTTP + TLS (Transport Layer
         * Security) - szyfruje CALY ruch miedzy klientem a serwerem.
         */
        System.out.println("HTTP = jawny tekst (podsluch/MITM mozliwy). HTTPS = HTTP + TLS - caly ruch SZYFROWANY.");

        Path workDir = Files.createTempDirectory("lesson08-tls");
        File serverKeystore = workDir.resolve("server.p12").toFile();
        File certFile = workDir.resolve("server.cer").toFile();
        File clientTruststore = workDir.resolve("client-truststore.p12").toFile();

        boolean keytoolAvailable = generateSelfSignedCertificate(serverKeystore, certFile, clientTruststore);

        if (!keytoolAvailable) {
            System.out.println("\nNarzedzie 'keytool' niedostepne w tym srodowisku - pomijam demo z prawdziwym handshake'em TLS.");
            System.out.println("=== KONIEC LEKCJI 8 (skrocony przebieg) ===");
            return;
        }

        printCertificateInfo(serverKeystore);

        HttpsServer server = startHttpsServer(serverKeystore);
        int port = server.getAddress().getPort();
        System.out.println("\nLokalny HTTPS serwer (samopodpisany certyfikat) wystartowal na porcie " + port);

        try {
            demonstrateSuccessfulHandshakeWithTrustedClient(port, clientTruststore);
            demonstrateHandshakeFailureWithDefaultClient(port);
        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 🔹 CO TAK NAPRAWDE DZIEJE SIE W TLS HANDSHAKE (W SKROCIE)
         * ============================================================
         * 1. Klient laczy sie i wysyla obslugiwane algorytmy szyfrowania.
         * 2. Serwer odsyla swoj CERTYFIKAT (zawiera klucz publiczny +
         *    podpis wystawcy - tu: SAMEGO SIEBIE, bo to certyfikat
         *    "samopodpisany").
         * 3. Klient WERYFIKUJE certyfikat - czy ufa WYSTAWCY (Certificate
         *    Authority) LUB (jak w tej lekcji) czy ma go JAWNIE w swoim
         *    truststore.
         * 4. Po weryfikacji, klient i serwer uzgadniaja WSPOLNY klucz
         *    sesji (szyfrowanie symetryczne - szybsze niz asymetryczne,
         *    uzywane do faktycznej wymiany danych).
         * SAMOPODPISANY certyfikat (jak w tej lekcji) NIE MA zaufanego
         * wystawcy - dlatego przegladarki/klienci z DOMYSLNYM truststore
         * go ODRZUCAJA (stad "ostrzezenie o niebezpiecznej stronie" w
         * przegladarce) - w PRAWDZIWEJ produkcji certyfikat wystawia
         * zaufany CA (np. Let's Encrypt, DigiCert).
         */
        System.out.println("\nCertyfikat samopodpisany = brak zaufanego wystawcy (CA) - domyslne truststore go odrzuca (\"niebezpieczna strona\").");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HTTPS = HTTP + TLS - szyfruje caly ruch, chroni przed
         *   podsluchem i MITM.
         * - Certyfikat serwera zawiera klucz publiczny + podpis wystawcy
         *   (CA) - klient WERYFIKUJE ten podpis przed zaufaniem serwerowi.
         * - Certyfikat SAMOPODPISANY (jak w tym demo) jest ODRZUCANY przez
         *   klienta z DOMYSLNYM truststore - trzeba go JAWNIE dodac (co
         *   zrobilismy recznie, budujac wlasny truststore).
         * - W produkcji: certyfikat od zaufanego CA (np. Let's Encrypt -
         *   darmowy, automatyczny odnawiania) - ZERO recznego dodawania
         *   do truststore po stronie klientow.
         * - `HttpOnly`+`Secure` na ciasteczkach (Lesson04/Lesson12) MA
         *   SENS TYLKO gdy strona dziala pod HTTPS - inaczej `Secure`
         *   po prostu zablokuje wyslanie ciastka W OGOLE.
         * - Wazny naglowek `Strict-Transport-Security` (HSTS, Lesson12)
         *   wymusza HTTPS nawet jesli uzytkownik wpisze `http://`.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    /** Generuje samopodpisany certyfikat przez WYWOLANIE prawdziwego narzedzia keytool z JDK. */
    private static boolean generateSelfSignedCertificate(File serverKeystore, File certFile, File clientTruststore) {
        System.out.println("\n=== GENEROWANIE SAMOPODPISANEGO CERTYFIKATU (prawdziwy 'keytool') ===");

        String javaHome = System.getProperty("java.home");
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        File keytool = new File(javaHome, "bin" + File.separator + (isWindows ? "keytool.exe" : "keytool"));

        if (!keytool.exists()) {
            System.out.println("Nie znaleziono keytool pod: " + keytool);
            return false;
        }

        boolean genOk = runProcess(
                keytool.getAbsolutePath(), "-genkeypair",
                "-alias", "serverkey", "-keyalg", "RSA", "-keysize", "2048",
                "-validity", "1", "-storetype", "PKCS12",
                "-keystore", serverKeystore.getAbsolutePath(), "-storepass", STORE_PASSWORD, "-keypass", STORE_PASSWORD,
                "-dname", "CN=localhost, OU=JavaQuest, O=JavaQuest, L=Warsaw, C=PL",
                "-ext", "SAN=dns:localhost,ip:127.0.0.1"
        );
        if (!genOk) {
            return false;
        }
        System.out.println("Wygenerowano klucz + samopodpisany certyfikat: " + serverKeystore.getName());

        boolean exportOk = runProcess(
                keytool.getAbsolutePath(), "-exportcert",
                "-alias", "serverkey", "-storetype", "PKCS12",
                "-keystore", serverKeystore.getAbsolutePath(), "-storepass", STORE_PASSWORD,
                "-file", certFile.getAbsolutePath(), "-rfc"
        );
        if (!exportOk) {
            return false;
        }
        System.out.println("Wyeksportowano certyfikat publiczny: " + certFile.getName());

        boolean importOk = runProcess(
                keytool.getAbsolutePath(), "-importcert",
                "-alias", "serverkey", "-storetype", "PKCS12",
                "-keystore", clientTruststore.getAbsolutePath(), "-storepass", STORE_PASSWORD,
                "-file", certFile.getAbsolutePath(), "-noprompt"
        );
        if (!importOk) {
            return false;
        }
        System.out.println("Zbudowano truststore klienta (JAWNIE ufajacy TEMU JEDNEMU certyfikatowi): " + clientTruststore.getName());
        return true;
    }

    private static boolean runProcess(String... command) {
        try {
            Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
            String output = new String(process.getInputStream().readAllBytes());
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                System.out.println("Proces " + command[0] + " przekroczyl limit czasu.");
                return false;
            }
            if (process.exitValue() != 0) {
                System.out.println("Proces zakonczyl sie bledem (" + process.exitValue() + "): " + output);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Nie udalo sie uruchomic procesu " + command[0] + ": " + e.getMessage());
            return false;
        }
    }

    private static void printCertificateInfo(File serverKeystore) throws Exception {
        System.out.println("\n=== INFORMACJE O WYGENEROWANYM CERTYFIKACIE ===");

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream in = new FileInputStream(serverKeystore)) {
            keyStore.load(in, STORE_PASSWORD.toCharArray());
        }
        Certificate certificate = keyStore.getCertificate("serverkey");
        if (certificate instanceof java.security.cert.X509Certificate x509) {
            System.out.println("Subject (dla kogo wystawiony): " + x509.getSubjectX500Principal());
            System.out.println("Issuer (kto wystawil): " + x509.getIssuerX500Principal()
                    + " -> IDENTYCZNY z subject = SAMOPODPISANY.");
            System.out.println("Wazny od: " + x509.getNotBefore() + " do: " + x509.getNotAfter());
        }
    }

    private static HttpsServer startHttpsServer(File serverKeystore) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream in = new FileInputStream(serverKeystore)) {
            keyStore.load(in, STORE_PASSWORD.toCharArray());
        }
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, STORE_PASSWORD.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);

        HttpsServer server = HttpsServer.create(new InetSocketAddress(0), 0);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext));
        server.createContext("/hello", exchange -> {
            byte[] response = "Polaczenie przez HTTPS nawiazane pomyslnie!".getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });
        server.setExecutor(null);
        server.start();
        return server;
    }

    private static void demonstrateSuccessfulHandshakeWithTrustedClient(int port, File clientTruststore) throws Exception {
        System.out.println("\n=== KLIENT UFAJACY CERTYFIKATOWI (WLASNY TRUSTSTORE) - HANDSHAKE OK ===");

        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream in = new FileInputStream(clientTruststore)) {
            trustStore.load(in, STORE_PASSWORD.toCharArray());
        }
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext clientSslContext = SSLContext.getInstance("TLS");
        clientSslContext.init(null, tmf.getTrustManagers(), null);

        HttpClient client = HttpClient.newBuilder().sslContext(clientSslContext).build();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("https://localhost:" + port + "/hello")).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("GET https://localhost:" + port + "/hello -> status " + response.statusCode() + ", body: " + response.body());
    }

    private static void demonstrateHandshakeFailureWithDefaultClient(int port) {
        System.out.println("\n=== KLIENT Z DOMYSLNYM TRUSTSTORE (BEZ naszego certyfikatu) - HANDSHAKE ODRZUCONY ===");

        HttpClient defaultClient = HttpClient.newHttpClient(); // domyslny SSLContext - NIE zna naszego samopodpisanego certyfikatu
        try {
            defaultClient.send(
                    HttpRequest.newBuilder(URI.create("https://localhost:" + port + "/hello")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("BLAD: polaczenie powinno zostac odrzucone, a przeszlo!");
        } catch (SSLHandshakeException e) {
            System.out.println("Polaczenie ODRZUCONE (jak oczekiwano) - certyfikat nieznany domyslnemu truststore: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Polaczenie odrzucone innym wyjatkiem zwiazanym z TLS: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
