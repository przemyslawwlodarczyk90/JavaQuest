package com.example.javaquest._19_security_basics.Lesson19_SecureLoggingAndAuditing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class _Lesson19_SecureLoggingAndAuditing {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 19: BEZPIECZNE LOGOWANIE I AUDYT ===");

        /*
         * ============================================================
         * 📦 KONTEKST: ZWYKLE LOGI vs AUDYT BEZPIECZENSTWA
         * ============================================================
         * `_13_libraries/Lesson17_MdcAndLoggingBestPractices` uczyl JAK
         * technicznie logowac (SLF4J/Logback, MDC, korelacja logow
         * miedzy watkami). Ta lekcja jest o INNYM pytaniu: CO wolno
         * zalogowac, a CZEGO NIGDY, oraz jak zbudowac ODDZIELNY,
         * wiarygodny dziennik zdarzen BEZPIECZENSTWA (audit trail) -
         * kto zrobil CO, kiedy, z jakim skutkiem - niezalezny od
         * zwyklych logow aplikacyjnych (debug/info o przebiegu kodu).
         */
        System.out.println("Logi aplikacyjne (DEBUG/INFO o przebiegu kodu) != dziennik audytu bezpieczenstwa (KTO/CO/KIEDY/SKUTEK).");

        demonstrateNeverLogSecretsOrPii();
        demonstrateLogInjectionAndSanitization();
        demonstrateRealAuditLogFile();
        demonstrateTamperEvidentHashChain();

        /*
         * ============================================================
         * 🔹 A09 OWASP: "SECURITY LOGGING AND MONITORING FAILURES"
         * ============================================================
         * Brak (lub niewystarczajacy) audyt bezpieczenstwa to OSOBNA
         * kategoria w OWASP Top 10 (pomost do `Lesson21_OwaspTop10Overview
         * AndCapstone`) - bez logu prob logowania/zmian uprawnien/dostepu
         * do wrazliwych danych, WLAMANIE moze pozostac NIEWYKRYTE
         * miesiacami. Dobry audyt trail odpowiada na pytania: "kto
         * probowal zalogowac sie jako admin?", "kto zmienil uprawnienia
         * uzytkownika X?", "kto pobral dane karty platniczej Y?".
         */
        System.out.println("\nBrak/slaby audyt = OWASP A09 'Security Logging and Monitoring Failures' - wlamanie moze pozostac niewykryte tygodniami.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - NIGDY nie loguj: hasel, tokenow/kluczy API (Lesson18), pelnych
         *   numerow kart platniczych, danych wrazliwych (PESEL, adres) -
         *   maskuj albo pomijaj calkowicie.
         * - Sanityzuj input PRZED zalogowaniem (usun/zescapuj znaki nowej
         *   linii) - inaczej atakujacy moze WSTRZYKNAC falszywe wpisy do
         *   logu (log injection/log forging).
         * - Dziennik AUDYTU (kto/co/kiedy/skutek) to OSOBNY kanal od
         *   zwyklych logow aplikacyjnych - zdarzenia bezpieczenstwa
         *   (logowanie, zmiana uprawnien, dostep do wrazliwych danych)
         *   MUSZA byc widoczne bez przekopywania sie przez tysiace linii
         *   DEBUG.
         * - Lancuch skrotow (hash chain) czyni log TAMPER-EVIDENT - kazda
         *   proba modyfikacji/usuniecia wpisu psuje weryfikacje calego
         *   lancucha od tego miejsca w dol.
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    private static void demonstrateNeverLogSecretsOrPii() {
        System.out.println("\n=== CZEGO NIGDY NIE LOGOWAC ===");

        String username = "jan_kowalski";
        String password = "SuperTajneHaslo123!";
        String creditCard = "4111111111111111";

        System.out.println("ZLE   : \"Uzytkownik " + username + " zalogowal sie haslem " + password + "\" - haslo w LOGU NA STALE, czytelne dla kazdego z dostepem do logow.");

        String maskedCard = "****-****-****-" + creditCard.substring(creditCard.length() - 4);
        System.out.println("DOBRZE: \"Uzytkownik " + username + " zaplacil karta " + maskedCard + "\" - tylko ostatnie 4 cyfry, reszta zamaskowana.");
        System.out.println("-> zasada: loguj FAKT zdarzenia (kto/co/kiedy), NIGDY surowa wartosc sekretu/danych wrazliwych (patrz `Lesson18_SecretsManagement`).");
    }

    private static String sanitizeForLog(String untrustedInput) {
        if (untrustedInput == null) {
            return "null";
        }
        return untrustedInput.replace("\n", "\\n").replace("\r", "\\r");
    }

    private static void demonstrateLogInjectionAndSanitization() {
        System.out.println("\n=== LOG INJECTION (FALSZOWANIE WPISOW W LOGU) ===");

        String maliciousUsername = "admin\n2026-07-11 12:00:00 INFO Uzytkownik admin zalogowal sie pomyslnie";

        System.out.println("ZLE   (bez sanityzacji) - zalogowana proba logowania:");
        System.out.println("  Proba logowania jako: " + maliciousUsername);
        System.out.println("  -> atakujacy WSTRZYKNAL znak nowej linii i SFABRYKOWAL drugi, falszywy wpis w logu, wygladajacy jak udane logowanie admina!");

        System.out.println("DOBRZE (po sanityzacji `\\n`/`\\r`):");
        System.out.println("  Proba logowania jako: " + sanitizeForLog(maliciousUsername));
        System.out.println("  -> caly zlosliwy input zmiescil sie w JEDNEJ linii logu, bez mozliwosci sfalszowania kolejnego wpisu.");
    }

    private static void demonstrateRealAuditLogFile() throws IOException {
        System.out.println("\n=== PRAWDZIWY, ODDZIELNY DZIENNIK AUDYTU (java.util.logging + FileHandler) ===");

        Path tempDir = Files.createTempDirectory("lesson19-audit");
        Path logFile = tempDir.resolve("security-audit.log");

        Logger auditLogger = Logger.getLogger("com.example.javaquest.securityAudit.lesson19");
        auditLogger.setUseParentHandlers(false);
        auditLogger.setLevel(Level.INFO);

        FileHandler fileHandler = new FileHandler(logFile.toString(), true);
        fileHandler.setFormatter(new SimpleFormatter());
        auditLogger.addHandler(fileHandler);

        auditLogger.info(sanitizeForLog("AUTH_SUCCESS user=jan_kowalski ip=10.0.0.5"));
        auditLogger.warning(sanitizeForLog("AUTH_FAILURE user=admin ip=203.0.113.7 reason=zle_haslo"));
        auditLogger.info(sanitizeForLog("PERMISSION_DENIED user=jan_kowalski resource=/admin/uzytkownicy action=DELETE"));

        fileHandler.close();
        auditLogger.removeHandler(fileHandler);

        long size = Files.size(logFile);
        System.out.println("Realny plik dziennika audytu zapisany na dysku: " + logFile);
        System.out.println("Rozmiar pliku: " + size + " bajtow (celowo NIE usuwany - mozna go otworzyc i przeczytac).");
        System.out.println("-> ten dziennik jest ODDZIELNY od zwyklych logow aplikacyjnych - zawiera TYLKO zdarzenia istotne dla bezpieczenstwa.");
    }

    private record AuditEvent(String actor, String action, String resource, String outcome, Instant timestamp, String hash) {
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 powinien byc zawsze dostepny w JDK", e);
        }
    }

    private static AuditEvent appendEvent(List<AuditEvent> chain, String actor, String action, String resource, String outcome) {
        String previousHash = chain.isEmpty() ? "GENESIS" : chain.get(chain.size() - 1).hash();
        Instant timestamp = Instant.now();
        String payload = previousHash + "|" + actor + "|" + action + "|" + resource + "|" + outcome + "|" + timestamp;
        String hash = sha256Hex(payload);
        AuditEvent event = new AuditEvent(actor, action, resource, outcome, timestamp, hash);
        chain.add(event);
        return event;
    }

    private static boolean verifyChain(List<AuditEvent> chain) {
        String expectedPreviousHash = "GENESIS";
        for (AuditEvent event : chain) {
            String payload = expectedPreviousHash + "|" + event.actor() + "|" + event.action() + "|" + event.resource() + "|" + event.outcome() + "|" + event.timestamp();
            String recomputed = sha256Hex(payload);
            if (!recomputed.equals(event.hash())) {
                return false;
            }
            expectedPreviousHash = event.hash();
        }
        return true;
    }

    private static void demonstrateTamperEvidentHashChain() {
        System.out.println("\n=== LANCUCH SKROTOW: DZIENNIK ODPORNY NA NIEZAUWAZONA MODYFIKACJE ===");

        List<AuditEvent> chain = new ArrayList<>();
        appendEvent(chain, "jan_kowalski", "LOGIN", "/login", "SUCCESS");
        appendEvent(chain, "jan_kowalski", "READ", "/api/zamowienia/42", "SUCCESS");
        appendEvent(chain, "admin", "DELETE", "/api/uzytkownicy/17", "SUCCESS");
        appendEvent(chain, "atakujacy", "LOGIN", "/login", "FAILURE");

        System.out.println("Zbudowano lancuch " + chain.size() + " zdarzen - kazde zawiera hash SHA-256 zaleznie od SWOJEJ tresci ORAZ hasha POPRZEDNIKA.");
        System.out.println("Weryfikacja nietknietego lancucha: " + verifyChain(chain) + " (oczekiwane: true)");

        AuditEvent tampered = chain.get(2);
        AuditEvent forged = new AuditEvent(tampered.actor(), tampered.action(), tampered.resource(), "FAILURE", tampered.timestamp(), tampered.hash());
        List<AuditEvent> tamperedChain = new ArrayList<>(chain);
        tamperedChain.set(2, forged);

        System.out.println("Ktos probuje ZMIENIC wynik zdarzenia #2 z SUCCESS na FAILURE, zachowujac STARY hash (proba ukrycia sladow)...");
        System.out.println("Weryfikacja PO probie manipulacji: " + verifyChain(tamperedChain) + " (oczekiwane: false - manipulacja WYKRYTA)");
        System.out.println("-> bez lancucha skrotow taka zmiana bylaby NIEWIDOCZNA (dziennik to 'zwykly' plik/wiersz w bazie, ktory mozna po prostu nadpisac).");
    }
}
