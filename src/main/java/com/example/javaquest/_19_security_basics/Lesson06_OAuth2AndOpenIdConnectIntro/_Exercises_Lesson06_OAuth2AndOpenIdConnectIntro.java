package com.example.javaquest._19_security_basics.Lesson06_OAuth2AndOpenIdConnectIntro;

public class _Exercises_Lesson06_OAuth2AndOpenIdConnectIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainFourOAuth2RolesWithOwnExample {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: podaj WLASNY przyklad (inny niz FotoDrukarnia z
         * teorii) i przypisz mu 4 role OAuth2: Resource Owner, Client,
         * Authorization Server, Resource Server.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementAuthorizeEndpointIssuingCode {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z `/authorize` zwracajacym losowy
         * authorization code i zapisujacym go w Mapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementTokenEndpointExchangingCode {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj `/token` przyjmujacy `code` i zwracajacy
         * `access_token`, jesli kod istnieje w Mapie z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RejectTokenRequestWithUnknownCode {
        /*
         * 🧪 Zadanie 4:
         * Zweryfikuj, ze `/token` z nieistniejacym/zmyslonym kodem
         * zwraca blad (400).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ValidateClientCredentials {
        /*
         * 🧪 Zadanie 5:
         * Dodaj Mape zarejestrowanych klientow (client_id->client_secret)
         * - `/token` odrzuca zle `client_secret` (401).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementProtectedResourceEndpoint {
        /*
         * 🧪 Zadanie 6:
         * Uruchom OSOBNY `HttpServer` (Resource Server) z `/api/data`
         * wymagajacym naglowka `Authorization: Bearer <token>`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CallResourceServerWithValidToken {
        /*
         * 🧪 Zadanie 7:
         * Wykonaj pelny przeplyw (authorize -> token -> /api/data) i
         * wypisz zwrocone dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MakeAuthorizationCodeSingleUse {
        /*
         * 🧪 Zadanie 8:
         * Zweryfikuj, ze uzycie TEGO SAMEGO authorization code DRUGI raz
         * zwraca blad - usun kod z Mapy natychmiast po pierwszym uzyciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyClientSecretNeverInBrowser {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego `client_secret` NIGDY nie
         * powinien trafic do kodu JavaScript dzialajacego w przegladarce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareOAuth2WithDirectPasswordSharing {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj (2-3 zdania) OAuth2 z "naiwnym"
         * podejsciem, gdzie FotoDrukarnia po prostu prosi o Twoje haslo do
         * ChmuraZdjec - jakie ryzyka eliminuje OAuth2?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_AddScopeParameterToAuthorization {
        /*
         * 🧪 Zadanie 11:
         * Dodaj parametr `scope` (np. "read:photos") do `/authorize` i
         * `/token` - zapisz go razem z tokenem, `/api/photos` sprawdza
         * czy token ma wymagany scope.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RejectInsufficientScope {
        /*
         * 🧪 Zadanie 12:
         * Zweryfikuj, ze token ze scope "read:photos" NIE MA dostepu do
         * hipotetycznego `/api/photos/delete` wymagajacego
         * "write:photos".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementTokenExpiration {
        /*
         * 🧪 Zadanie 13:
         * Dodaj czas wygasniecia do access tokenu (np. 2 sekundy w
         * demo) - zweryfikuj, ze Resource Server odrzuca wygasly token.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRefreshTokenForOAuth2 {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz `/token` o zwracanie dodatkowego `refresh_token` -
         * zaimplementuj wymiane refresh_token na nowy access_token bez
         * ponownego logowania uzytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BuildOidcStyleIdToken {
        /*
         * 🧪 Zadanie 15:
         * Uzyj biblioteki JWT z Lesson05 do zbudowania ID tokenu (OIDC) z
         * claims `sub`, `email`, `name` - zwroc go OBOK access_tokenu z
         * `/token`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ValidateIdTokenSignatureOnClientSide {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj (po stronie "Clienta") weryfikacje podpisu ID
         * tokenu z Zadania 15 - wyjasnij w komentarzu, dlaczego Client
         * musi UFAC kluczowi Authorization Servera (a nie odwrotnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainDifferenceBetweenAccessTokenAndIdToken {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij roznice miedzy access_token (dla
         * Resource Servera - "co mozesz zrobic") a id_token (dla Clienta -
         * "kim jest uzytkownik").
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementTokenIntrospectionEndpoint {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj `/introspect` na Authorization Server, ktory
         * Resource Server MOZE wywolac zamiast trzymac wlasna Mape
         * tokenow - zwraca `active: true/false` + metadane tokenu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RevokeTokenViaRevocationEndpoint {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj `/revoke` uniewazniajacy podany token - zweryfikuj,
         * ze token po uniewaznieniu jest odrzucany przez Resource Server.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_HandleMultipleClientsWithDifferentScopes {
        /*
         * 🧪 Zadanie 20:
         * Zarejestruj 2 roznych klientow z roznymi dozwolonymi scope'ami -
         * zweryfikuj, ze Client A NIE MOZE zazadac scope zarezerwowanego
         * dla Clienta B.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementPkceForPublicClients {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PKCE (Proof Key for Code Exchange, RFC 7636) -
         * Client generuje `code_verifier`+`code_challenge` (SHA-256),
         * `/token` wymaga poprawnego `code_verifier` pasujacego do
         * `code_challenge` z `/authorize` - wyjasnij, dla jakich klientow
         * (mobilne/SPA bez client_secret) to jest niezbedne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RejectTokenExchangeWithWrongCodeVerifier {
        /*
         * 🧪 Zadanie 22:
         * Zweryfikuj, ze `/token` z Zadania 21 odrzuca wymiane kodu, gdy
         * podany `code_verifier` NIE pasuje do zapisanego
         * `code_challenge`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementStateParameterForCsrfProtection {
        /*
         * 🧪 Zadanie 23:
         * Dodaj parametr `state` (losowy, generowany przez Clienta PRZED
         * przekierowaniem) - zweryfikuj go po powrocie z `/authorize` -
         * wyjasnij w komentarzu, przed jakim atakiem chroni (CSRF na
         * przeplyw OAuth2, zapowiedz Lesson10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulateOpenRedirectVulnerability {
        /*
         * 🧪 Zadanie 24:
         * Zademonstruj (i napraw) podatnosc "open redirect" - jesli
         * `/authorize` przekierowuje do DOWOLNEGO `redirect_uri` bez
         * walidacji wobec listy ZAREJESTROWANYCH adresow dla danego
         * client_id, atakujacy moze wykrasc kod - dodaj walidacje
         * whitelisty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementClientCredentialsGrantForM2M {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj inny "grant type" - Client Credentials (dla
         * komunikacji maszyna-maszyna, BEZ uzytkownika) - Client
         * uwierzytelnia sie WLASNYMI danymi bezposrednio na `/token`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareAuthorizationCodeWithImplicitFlow {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: zbadaj i wyjasnij, czym byl (juz ODRADZANY) Implicit
         * Flow, i dlaczego wspolczesne rekomendacje (OAuth 2.1) zalecaja
         * Authorization Code Flow + PKCE zamiast niego nawet dla
         * aplikacji SPA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementConsentScreenSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj ekran zgody - `/authorize` zwraca liste zadanych
         * scope'ow, a "uzytkownik" (recznie w kodzie) moze zaakceptowac
         * TYLKO CZESC z nich - wydany token ma TYLKO zaakceptowane scope'y.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DetectAndPreventTokenLeakageViaReferrer {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: wyjasnij, dlaczego umieszczanie tokenu w
         * PARAMETRZE URL (zamiast w naglowku `Authorization`) jest
         * ryzykowne (wycieki przez logi serwera, naglowek `Referer`,
         * historie przegladarki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementDeviceAuthorizationGrantConceptually {
        /*
         * 🧪 Zadanie 29:
         * Zasymuluj Device Authorization Grant (uzywany np. przez Smart
         * TV bez klawiatury) - urzadzenie dostaje `device_code`+
         * `user_code`, uzytkownik wpisuje `user_code` na INNYM
         * urzadzeniu (np. telefonie), urzadzenie "poluje" (polling) na
         * `/token` az uzytkownik potwierdzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteOAuth2OidcDemoWithPkceAndScopes {
        /*
         * 🧪 Zadanie 30:
         * Polacz PKCE (Zadanie 21), state (Zadanie 23), scope'y (Zadanie
         * 11-12) i ID token OIDC (Zadanie 15) w 1 spojne, kompletne demo
         * przeplywu logowania - wypisz czytelny log kazdego kroku.
         */
        public static void main(String[] args) { }
    }
}
