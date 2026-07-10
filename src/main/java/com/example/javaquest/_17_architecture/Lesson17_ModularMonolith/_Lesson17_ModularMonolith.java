package com.example.javaquest._17_architecture.Lesson17_ModularMonolith;

public class _Lesson17_ModularMonolith {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: MODULARNY MONOLIT ===");

        /*
         * ============================================================
         * 📦 CZYM JEST MODULARNY MONOLIT?
         * ============================================================
         * - "Monolit" (w sensie WDROZENIOWYM) = 1 wdrazalna jednostka, 1
         *   proces, zazwyczaj 1 baza danych - w OPOZYCJI do mikroserwisow
         *   (Lesson19), gdzie kazdy serwis to OSOBNY proces/wdrozenie.
         * - "Modularny" = WEWNATRZ tego 1 procesu, kod jest podzielony na
         *   WYRAZNE moduly z WYMUSZONYMI granicami - dokladne
         *   przeciwienstwo "Big Ball of Mud" (Lesson01).
         * - Modularny monolit lączy: prostote OPERACYJNA monolitu
         *   (1 deployment, 1 baza, latwiejsze debugowanie) z PORZADKIEM
         *   architektonicznym typowym dla mikroserwisow - stad okreslenie
         *   "najlepsze z obu swiatow".
         */
        System.out.println("Modularny monolit = 1 proces/wdrozenie (jak monolit) + wymuszone granice modulow (jak mikroserwisy).");

        /*
         * ============================================================
         * 🔹 FUNDAMENT: BOUNDED CONTEXT + PACKAGE-BY-FEATURE + PORTY
         * ============================================================
         * - Modularny monolit to NIE nowa idea - to POLACZENIE WSZYSTKICH
         *   poprzednich lekcji tego rozdzialu:
         *   - Bounded context (Lesson06) definiuje GDZIE przebiegaja
         *     granice modulow (wg jezyka biznesowego, nie technologii).
         *   - Package-by-feature (Lesson09) daje FIZYCZNA strukture
         *     kodu (1 modul = 1 pakiet najwyzszego poziomu) I techniczne
         *     narzedzie wymuszenia granic (`package-private`).
         *   - Porty (Lesson11-12) definiuja, JAK moduly komunikuja sie z
         *     WLASNA infrastruktura (kazdy modul ma WLASNE porty/adaptery).
         */
        System.out.println("Modularny monolit = Bounded Context (Lesson06) + Package-by-feature (Lesson09) + Porty (Lesson11-12) razem.");

        /*
         * ============================================================
         * 🔍 KAZDY MODUL: PUBLICZNE API + PRYWATNE WNETRZE
         * ============================================================
         * - Modul eksponuje MALY, JAWNY zestaw klas publicznych (jego
         *   "publiczne API") - reszta (encje wewnetrzne, repozytoria,
         *   szczegoly implementacji) jest `package-private` i FIZYCZNIE
         *   niedostepna dla innych modulow (kompilator to wymusza,
         *   Lesson09).
         * - Inne moduly MOGA wywolywac TYLKO publiczne API - proba
         *   siegniecia "w glab" modulu konczy sie BLEDEM KOMPILACJI, nie
         *   tylko "zlym zwyczajem".
         */
        demonstrateModuleWithPublicApiAndPrivateInternals();

        /*
         * ============================================================
         * 🔹 KAZDY MODUL WLASCICIELEM SWOICH DANYCH
         * ============================================================
         * - Rownie WAZNA (i czesto pomijana) zasada: modul B NIE MOZE
         *   BEZPOSREDNIO odpytywac tabel bazy danych modulu A - nawet
         *   jesli technicznie siedza w TEJ SAMEJ bazie fizycznej.
         * - Naruszenie tej zasady to NAJCZESTSZY sposob, w jaki
         *   "modularne" monolity CICHO degeneruja sie z powrotem w Big
         *   Ball of Mud - kod WYGLADA modularnie (osobne pakiety), ale
         *   BAZA DANYCH zdradza prawdziwe, ukryte sprzezenie.
         */
        demonstrateDataOwnershipViolationAntiPattern();

        /*
         * ============================================================
         * 🔍 "WSPOLNY" MODUL JAKO SMIETNIK - ANTY-WZORZEC
         * ============================================================
         * - Kuszace jest stworzenie modulu `common`/`shared` "na
         *   wszystko, co nie pasuje nigdzie indziej" - z czasem staje sie
         *   on WLASNYM, niekontrolowanym "god-modulem" (Lesson06: shared
         *   kernel powinien byc MALY i STABILNY - proste typy
         *   wartosciowe, NIE cala logika biznesowa "ktoregos z modulow").
         */
        System.out.println("\nModul 'common' powinien byc MALY (proste typy wartosciowe) - NIE smietnikiem na cala niepasujaca logike.");

        /*
         * ============================================================
         * 🔹 NATURALNY TRAMPOLINE DO MIKROSERWISOW (JESLI KIEDYS POTRZEBNE)
         * ============================================================
         * - Dobrze zbudowany modularny monolit ma JUZ wyrazne granice
         *   modulow, WLASNE dane, WLASNE publiczne API - dokladnie tam,
         *   gdzie MUSIALABY przebiegac granica SIECIOWA, gdyby modul
         *   trzeba bylo kiedys wydzielic do osobnego mikroserwisu.
         * - To sprawia, ze migracja "modul -> mikroserwis" (jesli w ogole
         *   potrzebna, Lesson19) jest STOSUNKOWO bezbolesna - w
         *   odroznieniu od proby wydzielenia serwisu z prawdziwego Big
         *   Ball of Mud, gdzie granice trzeba dopiero ODKRYC (czesto z
         *   wielkim bolem).
         */
        System.out.println("\nModularny monolit z wyraznymi granicami = naturalny trampoline do mikroserwisow, GDYBY kiedys potrzebne (Lesson19).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Modularny monolit: 1 proces/wdrozenie (jak monolit) + wymuszone
         *   granice modulow (jak mikroserwisy) - "najlepsze z obu swiatow".
         * - Fundament: Bounded Context (Lesson06) + Package-by-feature
         *   (Lesson09) + Porty/Adaptery (Lesson11-12).
         * - Kazdy modul: publiczne API + prywatne wnetrze (wymuszone
         *   przez `package-private`).
         * - Kazdy modul WLASCICIELEM swoich danych - ZERO bezposrednich
         *   zapytan do tabel innego modulu.
         * - Unikaj modulu "common" jako smietnika.
         * - Dobre granice modulow = naturalny trampoline do mikroserwisow,
         *   gdyby kiedys byly potrzebne.
         * - W kolejnej lekcji (Lesson18): jak moduly komunikuja sie ze
         *   soba LUZNO, przez zdarzenia domenowe, zamiast bezposrednich
         *   wywolan metod.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    /*
     * SYMULACJA: w prawdziwym projekcie ponizsze klasy zylyby w OSOBNYCH
     * pakietach (np. com.example.inventory, com.example.orders) - tu, dla
     * demonstracji w 1 pliku, uzywamy zagniezdzonych klas z KOMENTARZEM
     * "// (w realnym projekcie: package-private)" tam, gdzie prawdziwa
     * granica pakietu wymusilaby to FIZYCZNIE (Lesson09).
     */

    /** MODUL "Inventory" - PUBLICZNE API (jedyne, czego inne moduly moga uzywac). */
    static class InventoryModuleApi {
        private final InventoryInternalRepository internalRepository = new InventoryInternalRepository();

        public boolean isAvailable(String sku, int quantity) {
            return internalRepository.getStock(sku) >= quantity; // publiczna metoda DELEGUJE do wewnetrznych szczegolow
        }

        public void reserve(String sku, int quantity) {
            internalRepository.decreaseStock(sku, quantity);
        }
    }

    /** WEWNETRZNY szczegol modulu Inventory - w realnym projekcie: package-private, NIEDOSTEPNY spoza pakietu `inventory`. */
    static class InventoryInternalRepository /* (w realnym projekcie: package-private) */ {
        private final java.util.Map<String, Integer> stockLevels = new java.util.HashMap<>(java.util.Map.of("SKU-1", 10));

        int getStock(String sku) {
            return stockLevels.getOrDefault(sku, 0);
        }

        void decreaseStock(String sku, int quantity) {
            stockLevels.merge(sku, -quantity, Integer::sum);
        }
    }

    /** MODUL "Orders" - korzysta z Inventory WYLACZNIE przez jego publiczne API. */
    static class OrdersModuleApi {
        private final InventoryModuleApi inventory;

        OrdersModuleApi(InventoryModuleApi inventory) {
            this.inventory = inventory;
        }

        public String placeOrder(String sku, int quantity) {
            // Orders zna TYLKO InventoryModuleApi.isAvailable()/reserve() -
            // NIGDY InventoryInternalRepository (w realnym projekcie:
            // proba dostepu do niego z pakietu 'orders' byłaby BLEDEM KOMPILACJI).
            if (!inventory.isAvailable(sku, quantity)) {
                return "BLAD: brak wystarczajacego stanu dla " + sku;
            }
            inventory.reserve(sku, quantity);
            return "Zamowienie zlozone: " + sku + " x" + quantity;
        }
    }

    private static void demonstrateModuleWithPublicApiAndPrivateInternals() {
        System.out.println("\n=== MODUL Z PUBLICZNYM API I PRYWATNYM WNETRZEM ===");

        InventoryModuleApi inventory = new InventoryModuleApi();
        OrdersModuleApi orders = new OrdersModuleApi(inventory);

        System.out.println(orders.placeOrder("SKU-1", 3));
        System.out.println(orders.placeOrder("SKU-1", 100));
        System.out.println("-> OrdersModuleApi zna TYLKO InventoryModuleApi (publiczne API) -");
        System.out.println("   nigdy InventoryInternalRepository (w realnym projekcie: fizycznie niedostepne, package-private).");
    }

    private static void demonstrateDataOwnershipViolationAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: MODUL 'B' BEZPOSREDNIO CZYTA TABELE MODULU 'A' ===");

        System.out.println("[ZLA] Modul 'Raportowanie' wykonuje: 'SELECT * FROM inventory_stock_levels'");
        System.out.println("      BEZPOSREDNIO na tabeli WEWNETRZNEJ modulu Inventory (pomijajac jego API).");
        System.out.println("-> Kod WYGLADA modularnie (osobne pakiety), ale BAZA DANYCH zdradza ukryte sprzezenie -");
        System.out.println("   zmiana schematu tabeli w Inventory ZEPSUJE modul Raportowania, ktory 'nie powinien' o niej wiedziec.");
        System.out.println("[DOBRA] Modul 'Raportowanie' woluje InventoryModuleApi (publiczne API), NIGDY tabeli bezposrednio.");
    }
}
