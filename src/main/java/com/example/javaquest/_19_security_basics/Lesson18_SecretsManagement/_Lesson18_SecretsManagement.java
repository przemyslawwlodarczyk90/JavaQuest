package com.example.javaquest._19_security_basics.Lesson18_SecretsManagement;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Lesson18_SecretsManagement {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 18: ZARZADZANIE SEKRETAMI ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE: `_10_dao/Lesson13_EnvironmentVariables`
         * ============================================================
         * Tamta lekcja pokazala PIERWSZY krok: haslo NIE w kodzie, tylko
         * w zmiennej srodowiskowej ustawianej poza repozytorium. To
         * NADAL nie jest pelne "zarzadzanie sekretami" - zmienne
         * srodowiskowe MAJA wlasne slabosci, a w wiekszych systemach
         * (wiele serwisow, rotacja hasel, audyt dostepu) potrzeba
         * czegos wiecej. Ta lekcja idzie o krok dalej.
         */
        System.out.println("Zmienne srodowiskowe (Lesson13 z _10_dao) to KROK 1 - ta lekcja pokazuje, co dalej.");

        demonstrateWhyEnvVarsAreNotEnough();
        demonstrateSecretMaskingInToString();
        demonstrateVaultStyleSecretStoreWithCaching();
        demonstrateHardcodedSecretScanner();

        /*
         * ============================================================
         * 🔹 DEDYKOWANE MENEDZERY SEKRETOW (KONCEPCYJNIE)
         * ============================================================
         * HashiCorp Vault, AWS Secrets Manager, Azure Key Vault, GCP
         * Secret Manager - to WYSPECJALIZOWANE systemy, ktore:
         * - przechowuja sekrety SZYFROWANE, w JEDNYM, kontrolowanym
         *   miejscu (nie rozrzucone po zmiennych srodowiskowych 50
         *   roznych serwerow),
         * - daja DOKLADNA kontrole dostepu (KTO/KTORY serwis moze
         *   odczytac KTORY sekret - zasada najmniejszych uprawnien,
         *   patrz `_19_security_basics/Lesson07_AuthorizationPatternsAndRbac`),
         * - AUDYTUJA kazdy odczyt sekretu (kto, kiedy, jaki sekret -
         *   pomost do `Lesson19_SecureLoggingAndAuditing`),
         * - wspieraja ROTACJE (automatyczna zmiana hasla/klucza w tle,
         *   bez przestoju aplikacji) i sekrety KROTKOZYWOTNIE
         *   (dynamiczne, wygasajace po minutach - jesli wyciekna, sa
         *   bezuzyteczne po chwili).
         * Aplikacja NIE trzyma sekretu "na stale" - pyta menedzera przy
         * starcie (lub okresowo), tak jak demo nizej (`CachingSecretStore`).
         */
        System.out.println("\nDedykowany menedzer sekretow (Vault/AWS Secrets Manager/...) = centralne miejsce + kontrola dostepu + audyt + rotacja.");

        /*
         * ============================================================
         * 🔍 GDZIE JESZCZE SEKRETY POTRAFIA WYCIEC
         * ============================================================
         * - Argumenty linii polecen (`java -jar app.jar --db-password=tajne`)
         *   - widoczne dla KAZDEGO uzytkownika systemu przez liste
         *     procesow (`ps aux` na Linuksie, Menedzer Zadan na
         *     Windows) - UNIKAJ, preferuj zmienne srodowiskowe/plik
         *     konfiguracyjny o ograniczonych uprawnieniach.
         * - Pliki `.env` PRZYPADKOWO dodane do repozytorium (brak w
         *   `.gitignore`) - jeden `git add .` i haslo produkcyjne trafia
         *   do historii Gita NA ZAWSZE (nawet po usunieciu pliku pozniej!).
         * - Komunikaty wyjatkow (`throw new SQLException("Blad polaczenia:
         *   user=admin;password=" + realPassword)`) - trafiaja do logow/
         *   stack trace'ow (pomost do Lesson19).
         * - Zrzuty pamieci (heap dump, patrz `_15_jvm_internals/Lesson16`)
         *   - sekret zyjacy jako zwykly `String`/pole obiektu jest
         *     widoczny w kazdym zrzucie pamieci procesu.
         */
        System.out.println("Inne wycieki: argumenty CLI (widoczne w liscie procesow), zapomniany '.env' w Gicie, sekret w komunikacie wyjatku, zrzut pamieci.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zmienne srodowiskowe (Lesson13 z _10_dao) to KROK 1, nie
         *   koniec drogi - w wiekszych systemach potrzeba dedykowanego
         *   menedzera sekretow (centralizacja + kontrola dostepu + audyt
         *   + rotacja).
         * - Sekret NIGDY nie powinien byc widoczny w: kodzie zrodlowym,
         *   argumentach CLI, plikach commitowanych do Gita, komunikatach
         *   wyjatkow, domyslnym `toString()` obiektu.
         * - Maskowanie (`***`) w `toString()`/logach to obrona w
         *   glebi (defense in depth) - nawet jesli obiekt przypadkiem
         *   trafi do logu, sekret sie NIE ujawni.
         * - Rotacja sekretow ogranicza SZKODE po ewentualnym wycieku -
         *   wykradziony sekret jest bezuzyteczny po najblizszej rotacji.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 ===");
    }

    private static void demonstrateWhyEnvVarsAreNotEnough() {
        System.out.println("\n=== DLACZEGO SAME ZMIENNE SRODOWISKOWE TO NIE WYSTARCZY ===");

        System.out.println("Zmienna srodowiskowa NIE jest szyfrowana - kazdy proces o odpowiednich uprawnieniach systemowych moze ja odczytac.");
        System.out.println("Nie ma WBUDOWANEGO mechanizmu ROTACJI (trzeba zrestartowac proces, zeby podjac nowa wartosc).");
        System.out.println("Nie ma WBUDOWANEGO AUDYTU (kto/kiedy odczytal haslo z env) - w przeciwienstwie do dedykowanego menedzera sekretow.");
        System.out.println("-> w prostych/malych projektach zmienne srodowiskowe sa OK, w wiekszych systemach potrzeba czegos wiecej.");
    }

    private static final class RawPasswordHolder {
        private final String username;
        private final String password;

        RawPasswordHolder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            return "RawPasswordHolder{username='" + username + "', password='" + password + "'}";
        }
    }

    private static final class MaskedSecret {
        private final String value;

        MaskedSecret(String value) {
            this.value = value;
        }

        String reveal() {
            return value;
        }

        @Override
        public String toString() {
            if (value == null || value.isEmpty()) {
                return "***";
            }
            return "*".repeat(Math.min(value.length(), 8));
        }
    }

    private static final class SafeCredentials {
        private final String username;
        private final MaskedSecret password;

        SafeCredentials(String username, String password) {
            this.username = username;
            this.password = new MaskedSecret(password);
        }

        @Override
        public String toString() {
            return "SafeCredentials{username='" + username + "', password=" + password + "}";
        }
    }

    private static void demonstrateSecretMaskingInToString() {
        System.out.println("\n=== MASKOWANIE SEKRETU W toString() (OBRONA W GLEBI) ===");

        RawPasswordHolder bad = new RawPasswordHolder("admin", "SuperTajneHaslo123!");
        System.out.println("ZLE   - haslo widoczne wprost, jesli ten obiekt trafi do logu: " + bad);

        SafeCredentials good = new SafeCredentials("admin", "SuperTajneHaslo123!");
        System.out.println("DOBRZE - toString() maskuje haslo (dostep do wartosci TYLKO przez jawne reveal()): " + good);
        System.out.println("Prawdziwa wartosc nadal dostepna tam, gdzie NAPRAWDE potrzebna: " + good.password.reveal().length() + " znakow (dlugosc, nie tresc).");
    }

    private interface SecretProvider {
        String fetchSecret(String key);
    }

    private static final class FakeVaultSecretProvider implements SecretProvider {
        private final Map<String, String> backingStore;
        private int fetchCount = 0;

        FakeVaultSecretProvider(Map<String, String> backingStore) {
            this.backingStore = backingStore;
        }

        @Override
        public String fetchSecret(String key) {
            fetchCount++;
            String value = backingStore.get(key);
            if (value == null) {
                throw new IllegalArgumentException("Sekret '" + key + "' nie istnieje w magazynie");
            }
            return value;
        }
    }

    private record CachedEntry(String value, Instant fetchedAt) {
        boolean isExpired(Duration ttl) {
            return Duration.between(fetchedAt, Instant.now()).compareTo(ttl) > 0;
        }
    }

    private static final class CachingSecretStore {
        private final SecretProvider provider;
        private final Duration ttl;
        private final Map<String, CachedEntry> cache = new ConcurrentHashMap<>();

        CachingSecretStore(SecretProvider provider, Duration ttl) {
            this.provider = provider;
            this.ttl = ttl;
        }

        String get(String key) {
            CachedEntry cached = cache.get(key);
            if (cached != null && !cached.isExpired(ttl)) {
                return cached.value();
            }
            String fresh = provider.fetchSecret(key);
            cache.put(key, new CachedEntry(fresh, Instant.now()));
            return fresh;
        }
    }

    private static void demonstrateVaultStyleSecretStoreWithCaching() {
        System.out.println("\n=== WZORZEC: KLIENT MENEDZERA SEKRETOW Z CACHE'EM (JAK W VAULT/AWS SECRETS MANAGER) ===");

        FakeVaultSecretProvider fakeVault = new FakeVaultSecretProvider(Map.of(
                "db.password", "P4ssw0rd-Produkcyjne",
                "payments.api-key", "sk_live_abcdef123456"
        ));

        CachingSecretStore store = new CachingSecretStore(fakeVault, Duration.ofMillis(200));

        System.out.println("Pierwszy odczyt 'db.password' -> wymusza zapytanie do 'vaulta' (fetchCount przed: " + fakeVault.fetchCount + ")");
        store.get("db.password");
        System.out.println("fetchCount po 1. odczycie: " + fakeVault.fetchCount);

        System.out.println("Drugi odczyt (od razu potem, w oknie TTL) -> uzywa CACHE, BEZ zapytania do 'vaulta'");
        store.get("db.password");
        System.out.println("fetchCount po 2. odczycie (bez zmiany, bo z cache'a): " + fakeVault.fetchCount);

        System.out.println("-> aplikacja NIE trzyma sekretu na stale ani nie pyta menedzera przy KAZDYM uzyciu - cache z TTL to kompromis miedzy obciazeniem menedzera a szybkoscia rotacji.");
    }

    private static void demonstrateHardcodedSecretScanner() {
        System.out.println("\n=== PROSTY SKANER ZAHARDKODOWANYCH SEKRETOW (POMYSL NA GIT-SECRETS/TRUFFLEHOG) ===");

        Pattern awsKeyPattern = Pattern.compile("AKIA[0-9A-Z]{16}");
        Pattern hardcodedPasswordPattern = Pattern.compile("(?i)password\\s*=\\s*\"[^\"]{3,}\"");
        Pattern privateKeyPattern = Pattern.compile("-----BEGIN (RSA )?PRIVATE KEY-----");

        String[] sampleSourceLines = {
                "String dbUrl = \"jdbc:h2:mem:test\";",
                "String awsAccessKey = \"AKIAIOSFODNN7EXAMPLE\";",
                "String password = \"SuperTajneHaslo123\";",
                "// -----BEGIN RSA PRIVATE KEY----- wklejony przypadkiem do kodu",
                "String greeting = \"Witaj swiecie\";"
        };

        for (String line : sampleSourceLines) {
            boolean flagged = false;
            Matcher awsMatcher = awsKeyPattern.matcher(line);
            if (awsMatcher.find()) {
                System.out.println("PODEJRZANE (AWS access key): \"" + line + "\"");
                flagged = true;
            }
            Matcher passwordMatcher = hardcodedPasswordPattern.matcher(line);
            if (passwordMatcher.find()) {
                System.out.println("PODEJRZANE (zahardkodowane haslo): \"" + line + "\"");
                flagged = true;
            }
            Matcher keyMatcher = privateKeyPattern.matcher(line);
            if (keyMatcher.find()) {
                System.out.println("PODEJRZANE (klucz prywatny): \"" + line + "\"");
                flagged = true;
            }
            if (!flagged) {
                System.out.println("OK: \"" + line + "\"");
            }
        }
        System.out.println("-> takie skanery (git-secrets, TruffleHog, GitHub secret scanning) uruchamia sie automatycznie w CI/CD (pomost do `_20_dependency_and_supply_chain...` w Lesson20).");
    }
}
