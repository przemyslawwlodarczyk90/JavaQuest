package com.example.javaquest._19_security_basics.Lesson05_JwtIntroduction;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HexFormat;

public class _Lesson05_JwtIntroduction {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 5: WPROWADZENIE DO JWT ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 4
         * ============================================================
         * Sesje po stronie serwera dzialaja swietnie, ale WYMAGAJA
         * wspolnego magazynu stanu (Mapy/Redis/bazy) dostepnego dla
         * kazdej instancji serwera. JWT (JSON Web Token, RFC 7519) to
         * ALTERNATYWA - token BEZSTANOWY, ktory sam w sobie NIESIE dane
         * (np. kim jest uzytkownik) i jest podpisany kryptograficznie,
         * wiec serwer NIE MUSI nigdzie przechowywac stanu sesji.
         */
        System.out.println("JWT = bezstanowy, podpisany token niosacy dane - serwer NIE musi pamietac sesji.");

        SecretKey key = Jwts.SIG.HS256.key().build();

        demonstrateTokenStructure(key);
        demonstrateTokenVerification(key);
        demonstrateTamperedTokenIsRejected(key);
        demonstrateWrongKeyIsRejected();
        demonstrateExpiration();

        /*
         * ============================================================
         * 🔹 3 CZESCI JWT: HEADER.PAYLOAD.SIGNATURE
         * ============================================================
         * Token to 3 segmenty zakodowane Base64URL, rozdzielone kropkami:
         * - HEADER    - algorytm podpisu (np. "HS256") + typ ("JWT")
         * - PAYLOAD   - "claims" (dane) - np. subject, role, expiration
         * - SIGNATURE - podpis HMAC(header+"."+payload, tajny klucz) -
         *   gwarantuje, ze NIKT bez klucza nie mogl zmienic 1 i 2 czesci
         * WAZNE: HEADER i PAYLOAD sa TYLKO zakodowane (Base64), NIE
         * zaszyfrowane - KAZDY moze je odczytac (np. na jwt.io) - JWT
         * chroni przed MODYFIKACJA, NIE przed PODEJRZENIEM zawartosci.
         * NIGDY nie wkladaj do payloadu hasel/sekretow.
         */
        System.out.println("\nJWT = header.payload.signature - payload jest TYLKO zakodowany (Base64), NIE zaszyfrowany - kazdy go odczyta!");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - JWT = bezstanowy token z 3 czesci: header.payload.signature.
         * - Payload jest JAWNIE CZYTELNY (tylko zakodowany, nie
         *   zaszyfrowany) - NIGDY nie wkladaj tam sekretow.
         * - Podpis (HMAC lub RSA/EC) gwarantuje INTEGRALNOSC - kazda
         *   zmiana payloadu uniewaznia podpis.
         * - Token z nieprawidlowym podpisem/zlym kluczem MUSI byc
         *   odrzucony (wyjatek `SignatureException`).
         * - `exp` (expiration) - JWT powinien miec KROTKI czas zycia -
         *   w odroznieniu od sesji serwerowej, NIE DA SIE "uniewaznic"
         *   pojedynczego JWT przed jego wygasnieciem bez dodatkowego
         *   mechanizmu (np. blacklisty) - to WAZNA WADA w porownaniu z
         *   sesjami z Lesson04.
         * - Nastepna lekcja (Lesson06: OAuth2/OIDC) pokaze, gdzie JWT
         *   naturalnie pasuje jako FORMAT tokenu w wiekszym protokole
         *   autoryzacji.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void demonstrateTokenStructure(SecretKey key) {
        System.out.println("\n=== BUDOWA TOKENU JWT ===");

        String token = Jwts.builder()
                .subject("jan.kowalski")
                .claim("role", "admin")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(key)
                .compact();

        System.out.println("Wygenerowany JWT: " + token);

        String[] parts = token.split("\\.");
        System.out.println("Liczba czesci (header.payload.signature): " + parts.length);

        String decodedHeader = new String(Base64.getUrlDecoder().decode(parts[0]));
        String decodedPayload = new String(Base64.getUrlDecoder().decode(parts[1]));
        System.out.println("Zdekodowany header (JAWNIE czytelny, bez klucza!): " + decodedHeader);
        System.out.println("Zdekodowany payload (JAWNIE czytelny, bez klucza!): " + decodedPayload);
    }

    private static void demonstrateTokenVerification(SecretKey key) {
        System.out.println("\n=== WERYFIKACJA PODPISU I ODCZYT CLAIMS ===");

        String token = Jwts.builder()
                .subject("jan.kowalski")
                .claim("role", "admin")
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(key)
                .compact();

        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        System.out.println("Podpis poprawny - subject: " + claims.getSubject() + ", role: " + claims.get("role", String.class));
        System.out.println("Data wygasniecia: " + claims.getExpiration());
    }

    private static void demonstrateTamperedTokenIsRejected(SecretKey key) {
        System.out.println("\n=== ZMODYFIKOWANY TOKEN JEST ODRZUCANY ===");

        String token = Jwts.builder()
                .subject("jan.kowalski")
                .claim("role", "user")
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(key)
                .compact();

        String[] parts = token.split("\\.");
        String decodedPayload = new String(Base64.getUrlDecoder().decode(parts[1]));
        String tamperedPayload = decodedPayload.replace("\"user\"", "\"admin\""); // atakujacy probuje podniesc sobie uprawnienia
        String tamperedPayloadEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(tamperedPayload.getBytes());
        String tamperedToken = parts[0] + "." + tamperedPayloadEncoded + "." + parts[2]; // stary podpis, nowy payload

        System.out.println("Oryginalny payload: " + decodedPayload);
        System.out.println("Zmodyfikowany (przez atakujacego) payload: " + tamperedPayload);

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(tamperedToken);
            System.out.println("BLAD: token zostal zaakceptowany mimo modyfikacji - to by byla powazna luka!");
        } catch (SignatureException e) {
            System.out.println("Token ODRZUCONY - podpis nie zgadza sie z (zmodyfikowanym) payloadem: " + e.getMessage());
        }
    }

    private static void demonstrateWrongKeyIsRejected() {
        System.out.println("\n=== TOKEN PODPISANY INNYM KLUCZEM JEST ODRZUCANY ===");

        SecretKey realKey = Jwts.SIG.HS256.key().build();
        SecretKey attackerKey = Jwts.SIG.HS256.key().build(); // atakujacy NIE zna prawdziwego klucza serwera

        String tokenSignedByAttacker = Jwts.builder()
                .subject("haker")
                .claim("role", "admin")
                .signWith(attackerKey)
                .compact();

        try {
            Jwts.parser().verifyWith(realKey).build().parseSignedClaims(tokenSignedByAttacker);
            System.out.println("BLAD: token podpisany obcym kluczem zostal zaakceptowany!");
        } catch (SignatureException e) {
            System.out.println("Token ODRZUCONY - podpisany NIEZNANYM serwerowi kluczem: " + e.getMessage());
        }

        System.out.println("Klucz serwera (hex, TYLKO do celow demonstracyjnych - w praktyce NIGDY nie wypisuj klucza): "
                + HexFormat.of().formatHex(realKey.getEncoded()).substring(0, 16) + "...");
    }

    private static void demonstrateExpiration() throws InterruptedException {
        System.out.println("\n=== WYGASNIECIE TOKENU (exp) ===");

        SecretKey key = Jwts.SIG.HS256.key().build();
        String shortLivedToken = Jwts.builder()
                .subject("jan.kowalski")
                .expiration(new Date(System.currentTimeMillis() + 500)) // 500 ms - specjalnie krotko na potrzeby demo
                .signWith(key)
                .compact();

        System.out.println("Token wazny 500 ms wygenerowany, czekam az wygasnie...");
        Thread.sleep(700);

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(shortLivedToken);
            System.out.println("BLAD: wygasly token zostal zaakceptowany!");
        } catch (ExpiredJwtException e) {
            System.out.println("Token ODRZUCONY - wygasl: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("Token odrzucony z innego powodu: " + e.getMessage());
        }
    }
}
