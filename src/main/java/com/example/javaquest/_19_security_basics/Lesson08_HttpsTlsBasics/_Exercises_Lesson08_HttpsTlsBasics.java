package com.example.javaquest._19_security_basics.Lesson08_HttpsTlsBasics;

public class _Exercises_Lesson08_HttpsTlsBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainHttpVsHttpsInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami roznice miedzy HTTP a
         * HTTPS - co konkretnie zyskujemy?
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GenerateSelfSignedCertificateWithKeytool {
        /*
         * 🧪 Zadanie 2:
         * Uzyj `ProcessBuilder` do wywolania `keytool -genkeypair` i
         * wygenerowania wlasnego keystore PKCS12 - zweryfikuj, ze plik
         * powstal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReadCertificateSubjectAndIssuer {
        /*
         * 🧪 Zadanie 3:
         * Zaladuj wygenerowany keystore i wypisz subject/issuer
         * certyfikatu - zweryfikuj, ze sa IDENTYCZNE (samopodpisany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_StartHttpsServerWithCertificate {
        /*
         * 🧪 Zadanie 4:
         * Uruchom `HttpsServer` uzywajac wygenerowanego certyfikatu -
         * zweryfikuj, ze wystartowal na porcie 0 (losowy wolny port).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConnectWithClientTrustingCertificate {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj `HttpClient` z wlasnym truststore (importowanym
         * certyfikatem) - polacz sie z serwerem z Zadania 4 i wypisz
         * odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ShowDefaultClientRejectsSelfSignedCert {
        /*
         * 🧪 Zadanie 6:
         * Sprobuj polaczyc sie z serwerem z Zadania 4 uzywajac
         * `HttpClient.newHttpClient()` (BEZ zaufania) - przechwyc i
         * wypisz wyjatek TLS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhatTlsHandshakeAchieves {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij (3-4 kroki) co dzieje sie podczas
         * handshake'u TLS, zanim jakiekolwiek dane aplikacji zostana
         * przeslane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CheckCertificateValidityPeriod {
        /*
         * 🧪 Zadanie 8:
         * Odczytaj i wypisz date "wazny od"/"wazny do" wygenerowanego
         * certyfikatu - porownaj z aktualna data.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhyPlainHttpLeaksCookiesAndTokens {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij, dlaczego ciastko sesyjne (Lesson04)
         * lub token JWT (Lesson05) przeslany przez ZWYKLY HTTP moze
         * zostac PODSLUCHANY i wykorzystany przez atakujacego w tej
         * samej sieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListWhatCertificateContainsBesidesPublicKey {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz, jakie informacje (poza kluczem
         * publicznym) zawiera typowy certyfikat X.509 (subject, issuer,
         * waznosc, numer seryjny, algorytm podpisu).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_AddSubjectAlternativeNameForMultipleHosts {
        /*
         * 🧪 Zadanie 11:
         * Wygeneruj certyfikat z WIELOMA SAN (Subject Alternative Name) -
         * np. "localhost", "127.0.0.1", "test.local" - zweryfikuj, ze
         * wszystkie sa obecne w wygenerowanym certyfikacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementMutualTlsClientCertificate {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj mTLS (mutual TLS) - wygeneruj DRUGI certyfikat
         * dla KLIENTA, skonfiguruj serwer tak, by WYMAGAL certyfikatu
         * klienta (`setNeedClientAuth`/analog) - zweryfikuj odrzucenie
         * klienta BEZ certyfikatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareTls12WithTls13 {
        /*
         * 🧪 Zadanie 13:
         * Wypisz obslugiwane protokoly TLS na tej maszynie
         * (`SSLContext.getDefault().getSupportedSSLParameters()
         * .getProtocols()`) - skomentuj roznice TLS 1.2 vs TLS 1.3
         * (szybszy handshake, usuniete stare, slabe szyfry).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ForceSpecificTlsVersionAndObserveFailure {
        /*
         * 🧪 Zadanie 14:
         * Skonfiguruj serwer TYLKO na "TLSv1.2", a klienta wymuszajac
         * (probujac) "TLSv1.3" (lub odwrotnie) - zaobserwuj i wypisz
         * blad niedopasowania protokolow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCertificatePinningSimulation {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj uproszczone "certificate pinning" - klient
         * PORÓWNUJE odcisk (hash SHA-256) certyfikatu serwera z
         * ZAPISANYM WCZESNIEJ odciskiem, zamiast (lub oprocz) polegania
         * na truststore - odrzuc polaczenie przy niezgodnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DetectExpiredCertificateHandling {
        /*
         * 🧪 Zadanie 16:
         * Wygeneruj certyfikat z `-validity 0` (lub bardzo krotka
         * waznoscia) - poczekaj az wygasnie (symulowane/realne krotkie
         * oczekiwanie) i zweryfikuj, ze klient odrzuca wygasly certyfikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainCertificateChainAndRootCa {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: wyjasnij, czym jest "lancuch zaufania"
         * (certificate chain) - jak certyfikat serwera, posredni CA i
         * glowny (root) CA sie do siebie odnosza w PRAWDZIWYM (nie
         * samopodpisanym) scenariuszu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementHstsHeaderOnHttpsServer {
        /*
         * 🧪 Zadanie 18:
         * Dodaj naglowek `Strict-Transport-Security: max-age=31536000` do
         * odpowiedzi serwera HTTPS z tej lekcji - wyjasnij w komentarzu
         * jego dzialanie (zapowiedz Lesson12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureTlsHandshakeOverhead {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas 100 polaczen HTTPS (z pelnym handshake) vs 100
         * polaczen HTTP (bez TLS) do analogicznego serwera - skomentuj
         * narzut wydajnosciowy TLS.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementRedirectFromHttpToHttps {
        /*
         * 🧪 Zadanie 20:
         * Uruchom ROWNOLEGLE zwykly `HttpServer` (port HTTP) i
         * `HttpsServer` (port HTTPS) - zaimplementuj na porcie HTTP
         * przekierowanie 301 do odpowiadajacego adresu HTTPS.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementFullMutualTlsWithClientVerification {
        /*
         * 🧪 Zadanie 21:
         * Rozbuduj Zadanie 12 w pelne demo mTLS - serwer WERYFIKUJE
         * certyfikat klienta wobec WLASNEGO truststore (osobnego od
         * truststore klienta) - zweryfikuj sukces z poprawnym
         * certyfikatem klienta i porazke bez niego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateManInTheMiddleWithoutCertVerification {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj (WYLACZNIE w celach edukacyjnych, jawnie
         * skomentowane jako ZLA PRAKTYKA) klienta z TrustManagerem
         * ufajacym WSZYSTKIEMU (`X509TrustManager` bez weryfikacji) -
         * wyjasnij, dlaczego to CALKOWICIE eliminuje ochrone TLS przed
         * MITM.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementOcspStyleRevocationCheckConceptually {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: zbadaj i wyjasnij, czym jest OCSP (Online
         * Certificate Status Protocol) i CRL (Certificate Revocation
         * List) - po co sprawdzac, czy certyfikat zostal UNIEWAZNIONY
         * PRZED uplywem jego waznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkCipherSuitesPerformance {
        /*
         * 🧪 Zadanie 24:
         * Wypisz dostepne "cipher suites" na serwerze
         * (`SSLServerSocketFactory` lub `SSLContext`) - skomentuj, ktore
         * sa uznawane za PRZESTARZALE/SLABE i powinny byc jawnie
         * WYLACZONE w produkcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCertificateRotationWithoutDowntime {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj wymiane certyfikatu serwera "w locie" (nowy
         * SSLContext, nowy `HttpsConfigurator`) bez zatrzymywania
         * dzialajacego serwera - zweryfikuj, ze polaczenia PO wymianie
         * uzywaja NOWEGO certyfikatu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSniBasedMultiCertificateServer {
        /*
         * 🧪 Zadanie 26:
         * Zbadaj i (w miare mozliwosci embedowanego `HttpsServer`)
         * zaimplementuj obsluge SNI (Server Name Indication) - serwer
         * zwraca RÓŻNY certyfikat w zaleznosci od zadanej nazwy hosta w
         * handshake'u.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_AuditTlsConfigurationForKnownWeaknesses {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode "audytujaca" konfiguracje TLS serwera - sprawdza
         * obslugiwane protokoly i cipher suites pod katem znanych,
         * przestarzalych wartosci (np. SSLv3, TLS 1.0/1.1, RC4) i
         * wypisuje ostrzezenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCertificateExpiryMonitoring {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj metode sprawdzajaca, ile dni zostalo do
         * wygasniecia certyfikatu - wypisz OSTRZEZENIE, jesli zostalo
         * mniej niz 30 dni (typowy monitoring produkcyjny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareSelfSignedWithLetsEncryptWorkflow {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: zbadaj i opisz (w komentarzu) jak dziala
         * automatyczne wystawianie/odnawianie certyfikatow przez Let's
         * Encrypt (protokol ACME) - dlaczego to ZREWOLUCJONIZOWALO
         * powszechnosc HTTPS w internecie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureHttpsDemoWithHstsAndMtls {
        /*
         * 🧪 Zadanie 30:
         * Polacz mTLS (Zadanie 21), HSTS (Zadanie 18) i monitoring
         * waznosci certyfikatu (Zadanie 28) w 1 spojne demo - zweryfikuj
         * co najmniej 3 scenariusze (sukces, brak certyfikatu klienta,
         * certyfikat bliski wygasniecia) z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
