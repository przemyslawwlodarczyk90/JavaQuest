package com.example.javaquest._24_spring_security.Lesson12_JwtAuthentication;

public class _Exercises_Lesson12_JwtAuthentication {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyJwtNeedsCustomFilter {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, DLACZEGO Security NIE MA
         * WBUDOWANEGO `.jwt()` (w odroznieniu OD `.formLogin()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddSecondUserToLoginEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Dodaj DRUGIEGO uzytkownika DO map `passwordHashes`/`roles`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestLoginWithWrongPassword {
        /*
         * 🧪 Zadanie 3:
         * Zweryfikuj status 401 PRZY BLEDNYM hasle W `/api/login`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestLoginWithUnknownUser {
        /*
         * 🧪 Zadanie 4:
         * Zweryfikuj status 401 PRZY NIEZNANYM uzytkowniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainAddFilterBeforePlacement {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, PO CO `.addFilterBefore(...,
         * UsernamePasswordAuthenticationFilter.class)` (a NIE
         * `.addFilterAfter(...)`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddSecondProtectedEndpoint {
        /*
         * 🧪 Zadanie 6:
         * Dodaj DRUGI endpoint chroniony (`/api/profile`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestTokenWithExpiredTime {
        /*
         * 🧪 Zadanie 7:
         * Skroc czas wygasniecia tokenu DO 500ms, poczekaj I zweryfikuj
         * 401.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestMalformedAuthorizationHeader {
        /*
         * 🧪 Zadanie 8:
         * Wyslij naglowek `Authorization: BLEDNY-FORMAT` (BEZ "Bearer ")
         * - zweryfikuj status.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExtractRoleFromTokenAndUseInAuthorization {
        /*
         * 🧪 Zadanie 9:
         * Dodaj endpoint `/api/admin-only` chroniony `hasRole("ADMIN")`
         * I DODAJ uzytkownika Z TA rola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareWithSessionBasedApproachFromLesson04 {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj TA lekcje Z Lesson04/08 (sesje) - CO
         * SIE ZMIENILO (BRAK ciasteczka sesji, `Authorization` naglowek
         * ZAMIAST).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ReturnJwtAsJsonInsteadOfPlainText {
        /*
         * 🧪 Zadanie 11:
         * Zmien odpowiedz `/api/login` NA JSON (`{"token": "..."}`)
         * ZAMIAST surowego stringa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementRefreshTokenEndpoint {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj endpoint `/api/refresh` wydajacy NOWY token NA
         * PODSTAWIE WAZNEGO (jeszcze NIE wygaslego) STAREGO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddMultipleClaimsToToken {
        /*
         * 🧪 Zadanie 13:
         * Dodaj DODATKOWY "claim" (np. "email") DO tokenu I ODCZYTAJ
         * go W filtrze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MeasureJwtVerificationOverheadPerRequest {
        /*
         * 🧪 Zadanie 14:
         * Zmierz CZAS weryfikacji tokenu (`parseSignedClaims`) NA 1000
         * zadan - oszacuj narzut NA zadanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementGlobalExceptionHandlerForJwtErrors {
        /*
         * 🧪 Zadanie 15:
         * Zwroc CZYTELNY komunikat JSON (ZAMIAST domyslnego 401) DLA
         * roznych typow bledow JWT (`ExpiredJwtException` vs
         * `SignatureException`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestConcurrentTokenGeneration {
        /*
         * 🧪 Zadanie 16:
         * Wygeneruj 10 tokenow ROWNOLEGLE (osobne logowania) -
         * zweryfikuj, ze WSZYSTKIE SA UNIKALNE I POPRAWNE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExternalizeSecretKeyToConfiguration {
        /*
         * 🧪 Zadanie 17:
         * Powiaz z `_19_security_basics/Lesson18_SecretsManagement` -
         * przenies klucz JWT Z kodu DO WLASCIWOSCI konfiguracyjnej
         * (`@Value`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementTokenBlacklistForLogout {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: opisz PROBLEM "wylogowania" PRZY JWT (BRAK
         * mozliwosci uniewaznienia) I zaprojektuj CZARNA liste tokenow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareShortVsLongTokenExpiration {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: porownaj kompromisy KROTKIEGO (5 min) I
         * DLUGIEGO (7 dni) czasu zycia tokenu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementRoleBasedFilteringAtFilterLevel {
        /*
         * 🧪 Zadanie 20:
         * Zmodyfikuj filtr, ZEBY odrzucal (401) tokeny BEZ pola "role"
         * W claims.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementRsaSignedTokensInsteadOfHmac {
        /*
         * 🧪 Zadanie 21:
         * Zamien HMAC (`Jwts.SIG.HS256`) NA RSA (`Jwts.SIG.RS256`) -
         * podpis PRYWATNYM kluczem, weryfikacja PUBLICZNYM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignMicroserviceTokenPropagation {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: powiaz z `_17_architecture/Lesson19` -
         * zaprojektuj PROPAGACJE tokenu JWT miedzy mikroserwisami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementTokenRevocationWithRedisLikeStore {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj magazyn UNIEWAZNIONYCH tokenow (In-memory Set Z
         * TTL) SPRAWDZANY W filtrze PRZED zaakceptowaniem tokenu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkHmacVsRsaVerificationSpeed {
        /*
         * 🧪 Zadanie 24:
         * Porownaj CZAS weryfikacji 1000 tokenow HMAC vs RSA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementAudienceAndIssuerValidation {
        /*
         * 🧪 Zadanie 25:
         * Dodaj claims "iss" (issuer) I "aud" (audience) DO tokenu I
         * ZWERYFIKUJ JE W filtrze (odrzuc token Z OBCEGO issuera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignKeyRotationStrategy {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: zaprojektuj ROTACJE klucza podpisujacego BEZ
         * uniewaznienia WSZYSTKICH aktywnych tokenow NARAZ (kid W
         * headerze).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementRateLimitedLoginEndpoint {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_18_rest_api/Lesson16` - dodaj limit prob
         * logowania NA `/api/login` (ochrona PRZED brute-force).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareCustomFilterWithNimbusJwtDecoder {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: zapowiedz Lesson15 - porownaj TEN RECZNY
         * filtr Z natywnym `NimbusJwtDecoder`/OAuth2 Resource Server.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementAuditLogForTokenIssuanceAndValidation {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_19_security_basics/Lesson19` - loguj KAZDE
         * wydanie I KAZDA nieudana weryfikacje tokenu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullJwtAuthSystemWithRefreshAndRevocation {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY system: login -> access token (5 min) + refresh
         * token (7 dni) -> odswiezanie -> wylogowanie Z REALNA
         * unieważnieniem.
         */
        public static void main(String[] args) { }
    }
}
