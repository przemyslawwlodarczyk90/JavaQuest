package com.example.javaquest._17_architecture.Lesson14_CachingArchitecture;

import java.util.HashMap;
import java.util.Map;

public class _Lesson14_CachingArchitecture {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: ARCHITEKTURA CACHE'OWANIA ===");

        /*
         * ============================================================
         * 📦 MECHANIKE CACHE'A ZNASZ - TU: GDZIE POWINIEN ZYC ARCHITEKTONICZNIE
         * ============================================================
         * - Jak skonfigurowac i uzywac REALNEGO cache'a (Caffeine, TTL,
         *   eviction) poznales w `_13_libraries/Lesson27_CaffeineBasics` i
         *   `Lesson28_CaffeineLoadingAndAsyncCache`.
         * - Ta lekcja pyta o pytanie ARCHITEKTONICZNE, analogiczne do
         *   Lesson13 (transakcje): cache to KOLEJNY "przekroj poprzeczny"
         *   (cross-cutting concern) - GDZIE w architekturze powinien zyc,
         *   zeby NIE zanieczyszczac logiki biznesowej?
         */
        System.out.println("Mechanike cache'a (Caffeine) znasz z _13_libraries - tu: GDZIE architektonicznie powinien zyc.");

        /*
         * ============================================================
         * 🔹 WZORZEC CACHE-ASIDE JAKO KOLEJNY ADAPTER (DEKORATOR) PORTU
         * ============================================================
         * - Najlepsze miejsce na cache: DEKORATOR wokol portu DRIVEN
         *   (Lesson11-12) - klasa implementujaca TEN SAM port co
         *   "prawdziwy" adapter, ktora NAJPIERW sprawdza cache, a
         *   dopiero PRZY BRAKU danych ("cache miss") deleguje do
         *   prawdziwego adaptera.
         * - Kluczowa korzysc: aplikacja (Use Case/Service) W OGoLE NIE
         *   WIE, ze cache istnieje - zalezy TYLKO od portu, dokladnie jak
         *   przy kazdym innym adapterze (Lesson12). Cache mozna dodac
         *   albo usunac BEZ zmiany logiki biznesowej.
         */
        demonstrateCacheAsideDecoratorPattern();

        /*
         * ============================================================
         * 🔍 NAJTRUDNIEJSZA CZESC: INWALIDACJA PRZY ZAPISIE
         * ============================================================
         * - Phil Karlton (znany zart w branzy): "sa tylko 2 trudne rzeczy
         *   w informatyce: uniewaznianie cache'a i nazywanie rzeczy" (a
         *   `_16_clean_code/Lesson02` juz pokazal, ze DRUGA rzecz tez nie
         *   jest prosta).
         * - Jesli dekorator cache'ujacy odczyty NIE inwaliduje wpisu przy
         *   ZAPISIE (aktualizacji danych), klienci beda dostawac
         *   NIEAKTUALNE dane - kod MUSI jawnie usunac/odswiezyc wpis
         *   cache przy KAZDEJ operacji zapisu na tych samych danych.
         */
        demonstrateCacheInvalidationOnWrite();

        /*
         * ============================================================
         * 🔹 GDZIE (NA JAKIM POZIOMIE) CACHE'OWAC - KOMPROMIS
         * ============================================================
         * - CACHE NA POZIOMIE REPOSITORY (jak w tej lekcji): cache'uje
         *   SUROWE dane - inwalidacja jest PROSTA (1 klucz = 1 rekord),
         *   ale zysk wydajnosciowy jest MNIEJSZY (logika biznesowa i tak
         *   wykonuje sie za kazdym razem).
         * - CACHE NA POZIOMIE SERVICE (wynik CALEJ operacji biznesowej,
         *   np. "podsumowanie zamowien klienta"): WIEKSZY zysk
         *   wydajnosciowy, ale inwalidacja jest TRUDNIEJSZA (wynik zalezy
         *   od WIELU zrodel danych - zmiana KTOREGOKOLWIEK z nich
         *   wymaga inwalidacji).
         * - CACHE NA POZIOMIE CALEJ ODPOWIEDZI API (Controller): NAJWIEKSZY
         *   zysk, ale NAJTRUDNIEJSZA inwalidacja i realne ryzyko
         *   pokazania KOMPLETNIE nieaktualnych danych klientowi.
         * - Praktyczna zasada: zaczynaj NISKO (Repository) - podnos
         *   poziom TYLKO gdy profilowanie (`_15_jvm_internals`) wykaze
         *   REALNY problem wydajnosciowy tam, gdzie prostszy cache nie
         *   wystarcza.
         */
        System.out.println("\nCache nizej (Repository) = prostsza inwalidacja, mniejszy zysk. Cache wyzej = odwrotnie. Zaczynaj NISKO.");

        /*
         * ============================================================
         * 🔍 ANTY-WZORZEC: CACHE ROZLANY PO LOGICE BIZNESOWEJ
         * ============================================================
         * - Jesli Service SAM w SRODKU swojej metody biznesowej sprawdza
         *   "czy mam to w mapie", to logika CACHE'OWANIA miesza sie z
         *   logika BIZNESOWA w 1 klasie - naruszenie SRP
         *   (`_16_clean_code/Lesson07`), dokladnie tak samo jak przy
         *   mieszaniu logiki z logowaniem czy walidacja.
         */
        demonstrateCacheLogicLeakingIntoBusinessLogicAntiPattern();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Cache to przekroj poprzeczny, jak transakcje (Lesson13) -
         *   NIE powinien zyc w logice biznesowej.
         * - Najlepsze miejsce: dekorator wokol portu driven (ten sam
         *   port co "prawdziwy" adapter, Lesson11-12).
         * - Inwalidacja przy zapisie to NAJTRUDNIEJSZA czesc - latwo
         *   zapomniec i serwowac nieaktualne dane.
         * - Im WYZEJ w architekturze cache'ujesz, tym WIEKSZY zysk, ale
         *   TRUDNIEJSZA inwalidacja - zaczynaj nisko (Repository).
         * - Anty-wzorzec: logika cache'owania wymieszana z logika
         *   biznesowa w 1 klasie.
         * - W kolejnej lekcji (Lesson15): architektura walidacji - kolejny
         *   przekroj poprzeczny, tym razem "gdzie sprawdzac poprawnosc
         *   danych" w warstwach aplikacji.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    interface ProductRepositoryPort {
        String findNameById(long id);
    }

    /** "Prawdziwy" adapter - symulacja WOLNEGO dostepu do danych (np. odleglej bazy danych). */
    static class SlowProductRepositoryAdapter implements ProductRepositoryPort {
        @Override
        public String findNameById(long id) {
            System.out.println("  [WOLNY adapter] Symulacja kosztownego zapytania dla id=" + id + "...");
            return "Produkt-" + id;
        }
    }

    /** DEKORATOR cache'ujacy - implementuje TEN SAM port, deleguje do prawdziwego adaptera TYLKO przy 'cache miss'. */
    static class CachedProductRepositoryAdapter implements ProductRepositoryPort {
        private final ProductRepositoryPort delegate;
        private final Map<Long, String> cache = new HashMap<>();

        CachedProductRepositoryAdapter(ProductRepositoryPort delegate) {
            this.delegate = delegate;
        }

        @Override
        public String findNameById(long id) {
            if (cache.containsKey(id)) {
                System.out.println("  [Cache HIT] id=" + id);
                return cache.get(id);
            }
            System.out.println("  [Cache MISS] id=" + id + " - delegowanie do prawdziwego adaptera");
            String result = delegate.findNameById(id);
            cache.put(id, result);
            return result;
        }

        void invalidate(long id) {
            cache.remove(id);
            System.out.println("  [Cache INVALIDATE] id=" + id);
        }
    }

    private static void demonstrateCacheAsideDecoratorPattern() {
        System.out.println("\n=== DEKORATOR CACHE'UJACY - TEN SAM PORT CO PRAWDZIWY ADAPTER ===");

        ProductRepositoryPort realAdapter = new SlowProductRepositoryAdapter();
        CachedProductRepositoryAdapter cachedAdapter = new CachedProductRepositoryAdapter(realAdapter);

        // Aplikacja (symulowana tutaj bezposrednim wywolaniem) zalezy TYLKO
        // od interfejsu ProductRepositoryPort - nie wie, ze pod spodem jest cache.
        System.out.println("1. wywolanie: " + cachedAdapter.findNameById(1L));
        System.out.println("2. wywolanie (TO SAMO id): " + cachedAdapter.findNameById(1L));
        System.out.println("-> 2. wywolanie NIE uruchomilo 'wolnego adaptera' - obsluzyl je cache.");
    }

    private static void demonstrateCacheInvalidationOnWrite() {
        System.out.println("\n=== INWALIDACJA CACHE'A PRZY ZAPISIE ===");

        ProductRepositoryPort realAdapter = new SlowProductRepositoryAdapter();
        CachedProductRepositoryAdapter cachedAdapter = new CachedProductRepositoryAdapter(realAdapter);

        System.out.println("Odczyt PRZED zmiana: " + cachedAdapter.findNameById(2L));
        System.out.println("Odczyt PONOWNY (z cache'a, wciaz stare dane): " + cachedAdapter.findNameById(2L));

        System.out.println("Symulacja ZAPISU zmieniajacego produkt id=2 -> WYMAGANA inwalidacja:");
        cachedAdapter.invalidate(2L); // BEZ tego wywolania, kolejny odczyt zwrocilby NIEAKTUALNE dane z cache'a

        System.out.println("Odczyt PO inwalidacji (znow 'cache miss', swieze dane): " + cachedAdapter.findNameById(2L));
    }

    private static void demonstrateCacheLogicLeakingIntoBusinessLogicAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: LOGIKA CACHE'A WYMIESZANA Z LOGIKA BIZNESOWA ===");

        System.out.println("[ZLA] Service SAM zawiera logike cache'a WEWNATRZ metody biznesowej:");
        System.out.println("  calculateDiscount(customerId) {");
        System.out.println("    if (discountCache.containsKey(customerId)) return discountCache.get(customerId);");
        System.out.println("    double discount = /* logika biznesowa */ ...;");
        System.out.println("    discountCache.put(customerId, discount);");
        System.out.println("    return discount;");
        System.out.println("  }");
        System.out.println("-> Logika CACHE'A i logika BIZNESOWA sa NIEROZLACZNIE wymieszane w 1 metodzie.");
        System.out.println("[DOBRA] Cache jako OSOBNY dekorator portu (jak wyzej) - Service zawiera TYLKO logike biznesowa.");
    }
}
