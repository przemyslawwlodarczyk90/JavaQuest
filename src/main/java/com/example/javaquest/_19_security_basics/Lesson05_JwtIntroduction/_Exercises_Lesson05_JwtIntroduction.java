package com.example.javaquest._19_security_basics.Lesson05_JwtIntroduction;

public class _Exercises_Lesson05_JwtIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_GenerateHs256SecretKey {
        /*
         * 🧪 Zadanie 1:
         * Uzyj `Jwts.SIG.HS256.key().build()` do wygenerowania losowego
         * klucza HMAC - wypisz jego dlugosc w bajtach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BuildSimpleTokenWithSubject {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj token JWT z `subject("test.user")` i podpisz go kluczem
         * z Zadania 1 - wypisz wynikowy string.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DecodeHeaderAndPayloadManually {
        /*
         * 🧪 Zadanie 3:
         * Rozbij token z Zadania 2 po kropkach i zdekoduj Base64URL
         * pierwsze 2 czesci (header, payload) - wypisz zawartosc JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParseTokenAndReadSubject {
        /*
         * 🧪 Zadanie 4:
         * Sparsuj token przez `Jwts.parser().verifyWith(key).build()
         * .parseSignedClaims()` i odczytaj `getSubject()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddCustomClaimsForRoleAndEmail {
        /*
         * 🧪 Zadanie 5:
         * Dodaj wlasne "claims" (`role`, `email`) do tokenu - odczytaj je
         * po sparsowaniu przez `claims.get("role", String.class)`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SetExpirationClaim {
        /*
         * 🧪 Zadanie 6:
         * Ustaw `expiration()` na 1 minute od teraz - wypisz date
         * wygasniecia odczytana po sparsowaniu tokenu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RejectTokenSignedWithDifferentKey {
        /*
         * 🧪 Zadanie 7:
         * Wygeneruj token 1 kluczem, sprobuj sparsowac go INNYM kluczem -
         * przechwyc i wypisz `SignatureException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DetectExpiredToken {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj token z `expiration()` w PRZESZLOSCI - sparsuj go i
         * przechwyc `ExpiredJwtException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyPayloadIsNotEncrypted {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego payload JWT NIE jest
         * szyfrowany, tylko kodowany - co to oznacza dla danych, ktore
         * mozna tam wlozyc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ImplementLoginEndpointReturningJwt {
        /*
         * 🧪 Zadanie 10:
         * Uruchom `HttpServer` z POST `/login`, ktory po poprawnych
         * danych zwraca token JWT (zamiast identyfikatora sesji jak w
         * Lesson04).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementProtectedEndpointRequiringBearerJwt {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj `/profile` wymagajacy naglowka
         * `Authorization: Bearer <jwt>` - sparsuj i zweryfikuj token,
         * zwroc 401 dla brakujacego/niepoprawnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementRoleBasedAuthorizationFromJwtClaim {
        /*
         * 🧪 Zadanie 12:
         * Odczytaj claim `role` z tokenu i zwroc 403 dla
         * `/admin/settings`, jesli rola nie jest "admin" - analogicznie
         * do Lesson01, ale z danymi z JWT zamiast Mapy sesji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_HandleMalformedAuthorizationHeader {
        /*
         * 🧪 Zadanie 13:
         * Obsluz przypadki: brak naglowka `Authorization`, naglowek bez
         * prefiksu "Bearer ", oraz kompletnie losowy string zamiast JWT -
         * zwroc czytelny 401 dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareJwtSizeWithSessionIdSize {
        /*
         * 🧪 Zadanie 14:
         * Porownaj rozmiar (w bajtach) tokenu JWT z kilkoma claims wobec
         * identyfikatora sesji UUID z Lesson04 - skomentuj wplyw na
         * rozmiar kazdego zadania HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRefreshTokenPattern {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj 2 tokeny: krotkozyjacy "access token" (np. 60s) i
         * dlugozyjacy "refresh token" (np. 1h) - zaimplementuj endpoint
         * `/refresh` wymieniajacy wazny refresh token na nowy access
         * token.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainWhyJwtCannotBeRevokedEasily {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: wyjasnij, dlaczego pojedynczego wydanego JWT
         * NIE da sie "uniewaznic" przed jego wygasnieciem BEZ dodatkowego
         * mechanizmu - podaj scenariusz, gdzie to jest problemem (np.
         * zwolnienie pracownika).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementTokenBlacklistForRevocation {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj Mape "czarnej listy" uniewaznionych tokenow
         * (po unikalnym claim "jti") - endpoint `/logout` dodaje token do
         * listy, `/profile` odrzuca tokeny z listy mimo poprawnego
         * podpisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddIssuerAndAudienceClaims {
        /*
         * 🧪 Zadanie 18:
         * Dodaj claims `issuer` i `audience` do tokenu - zaimplementuj
         * weryfikacje, ktora odrzuca token z NIEOCZEKIWANYM issuerem
         * (np. wydany przez "inny system").
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureJwtParsingPerformance {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas generowania i parsowania 10 000 tokenow JWT -
         * porownaj z czasem odczytu z Mapy sesji (Lesson04) dla tej samej
         * liczby operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementNoneAlgorithmAttackDemo {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj RECZNIE (bez podpisu) token z headerem `{"alg":"none"}` i
         * sprobuj go sparsowac parserem skonfigurowanym do WYMAGANIA
         * podpisu - zweryfikuj, ze zostaje odrzucony (klasyczny,
         * historyczny atak "alg: none" na zle skonfigurowane biblioteki).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildCompleteLoginProfileAdminDemoWithJwt {
        /*
         * 🧪 Zadanie 21:
         * Polacz Zadania 10, 11, 12 w 1 spojne demo: logowanie zwracajace
         * JWT, `/profile` wymagajacy tokenu, `/admin/settings` wymagajacy
         * roli admin.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementAccessAndRefreshTokenFullFlow {
        /*
         * 🧪 Zadanie 22:
         * Rozbuduj Zadanie 15 o pelny przeplyw: logowanie -> uzycie
         * access tokenu az do wygasniecia -> `/refresh` -> dalsze uzycie
         * NOWEGO access tokenu - wypisz czytelny log kazdego kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RotateRefreshTokenOnEachUse {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj "rotacje" refresh tokenu - kazde uzycie
         * `/refresh` uniewaznia STARY refresh token i wydaje NOWY -
         * zweryfikuj, ze ponowne uzycie starego refresh tokenu jest
         * odrzucane (ochrona przed powtornym uzyciem skradzionego tokenu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareHmacVsRsaSignedTokens {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj i zweryfikuj token podpisany RS256 (klucz asymetryczny,
         * `Jwts.SIG.RS256.keyPair().build()`) zamiast HS256 - wyjasnij w
         * komentarzu, kiedy asymetryczny podpis jest lepszym wyborem
         * (np. wiele serwisow WERYFIKUJACYCH token, tylko 1 GO WYDAJACY).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementMultiServiceVerificationWithPublicKey {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj 2 "serwisy" (2 metody) weryfikujace TEN SAM token
         * RS256 uzywajac TYLKO klucza publicznego (bez dostepu do klucza
         * prywatnego, ktorym token zostal podpisany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DetectAlgorithmConfusionAttack {
        /*
         * 🧪 Zadanie 26:
         * Zbadaj (i skomentuj) klasyczny atak "algorithm confusion" -
         * gdy serwer akceptujacy zarowno HS256 jak i RS256 moze zostac
         * oszukany tokenem podpisanym kluczem PUBLICZNYM RSA jako
         * "sekret" HMAC - wyjasnij, dlaczego biblioteki (w tym jjwt)
         * wymagaja jawnego podania OCZEKIWANEGO algorytmu przy parsowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementJwtWithMinimalClaimsForPrivacy {
        /*
         * 🧪 Zadanie 27:
         * Przeprojektuj payload tokenu tak, by zawieral TYLKO niezbedne
         * dane (np. identyfikator uzytkownika, NIE email/imie/nazwisko) -
         * wyjasnij w komentarzu zasade minimalizacji danych w JWT (dane
         * sa PUBLICZNIE czytelne przez kazdego posiadacza tokenu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BenchmarkStatelessJwtVsStatefulSessionAtScale {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj "3 instancje serwera" (3 osobne metody/mapy) -
         * pokaz, ze JWT dziala natychmiast na kazdej instancji BEZ
         * wspoldzielenia stanu, podczas gdy sesja z Lesson04 wymagalaby
         * wspolnej Mapy (replikacji stanu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementShortLivedTokenWithClockSkewTolerance {
        /*
         * 🧪 Zadanie 29:
         * Skonfiguruj parser z tolerancja "clock skew" (np.
         * `.clockSkewSeconds(5)`) - zademonstruj token, ktory technicznie
         * "wlasnie wygasl" (1-2 sekundy temu), ale jest wciaz akceptowany
         * dzieki tolerancji - wyjasnij, po co ta tolerancja w systemach
         * rozproszonych (rozne zegary serwerow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureJwtAuthSystem {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny system: logowanie (BCrypt z Lesson03) +
         * access/refresh JWT (Zadanie 22-23) + role (Zadanie 12) +
         * blacklista przy wylogowaniu (Zadanie 17) - zweryfikuj co
         * najmniej 5 scenariuszy (sukces, zly token, wygasly token, zla
         * rola, wylogowanie) z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
