package com.example.javaquest._17_architecture.Lesson09_PackageByLayerVsPackageByFeature;

public class _Lesson09_PackageByLayerVsPackageByFeature {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: PACKAGE BY LAYER VS PACKAGE BY FEATURE ===");

        /*
         * ============================================================
         * 📦 DWA SPOSOBY UKLADANIA TYCH SAMYCH KLAS W PAKIETY
         * ============================================================
         * - Znasz juz warstwy (Lesson03) i trojke Controller/Service/
         *   Repository (Lesson04) - ale w JAKI SPOSOB ulozyc je w PAKIETY
         *   (foldery)? Sa 2 fundamentalnie rozne odpowiedzi.
         */
        System.out.println(PACKAGE_BY_LAYER_TREE);
        System.out.println(PACKAGE_BY_FEATURE_TREE);

        /*
         * ============================================================
         * 🔹 PACKAGE BY LAYER: WSZYSTKIE KONTROLERY RAZEM, WSZYSTKIE SERWISY RAZEM
         * ============================================================
         * - Zaleta: latwo znalezc "WSZYSTKIE kontrolery aplikacji" -
         *   wystarczy otworzyc 1 folder.
         * - Wada: zeby zrozumiec 1 funkcje biznesowa (np. "Zamowienia"),
         *   trzeba SKAKAC miedzy 3+ roznymi folderami (controller/,
         *   service/, repository/) - klasy DOTYCZACE TEJ SAMEJ funkcji
         *   sa ROZRZUCONE.
         * - To jest DOKLADNIE niska spojnosc (`_16_clean_code/Lesson06`),
         *   ale zastosowana do CALEGO PAKIETU zamiast do 1 klasy: pakiet
         *   `controller` zawiera klasy dotyczace Zamowien, Uzytkownikow,
         *   Platnosci - RAZEM, mimo ze nie maja ze soba NIC wspolnego
         *   poza tym, ze "sa kontrolerami".
         */
        demonstratePackageByLayerCohesionProblem();

        /*
         * ============================================================
         * 🔍 PACKAGE BY FEATURE: WSZYSTKO O 1 FUNKCJI RAZEM
         * ============================================================
         * - Zaleta: WSZYSTKIE klasy dotyczace 1 funkcji biznesowej
         *   (Controller+Service+Repository dla "Zamowien") stoja W 1
         *   FOLDERZE - wysoka spojnosc pakietu, latwo zrozumiec "o co
         *   chodzi w tej funkcji" bez skakania miedzy folderami.
         * - To naturalnie WSPOLGRA z bounded context (Lesson06) - 1
         *   pakiet "by feature" CZESTO odpowiada 1 bounded context, i
         *   staje sie naturalnym kandydatem na przyszly modul
         *   (Lesson17: ModularMonolith).
         */
        demonstratePackageByFeatureCohesion();

        /*
         * ============================================================
         * 🔹 UKRYTA KORZYSC TECHNICZNA: WIDOCZNOSC PACKAGE-PRIVATE
         * ============================================================
         * - W package-by-feature, `Repository` konkretnej funkcji MOZE
         *   byc `package-private` (bez modyfikatora `public`) - Java
         *   FIZYCZNIE (na poziomie kompilatora) NIE POZWOLI innej
         *   funkcji/pakietowi go uzyc BEZPOSREDNIO, wymuszajac przejscie
         *   przez `Service` (a wiec i przez wszystkie jego reguly -
         *   dokladnie unikanie "przeciekajacego repozytorium",
         *   Lesson04).
         * - W package-by-layer, `Repository` w pakiecie `repository`
         *   MUSI byc `public`, zeby uzyl go `Service` z INNEGO pakietu -
         *   NIC nie stoi na przeszkodzie, zeby dowolna inna klasa w
         *   aplikacji tez go uzyla, omijajac Service. Package-by-layer
         *   NIE MA jak wymusic tej granicy TECHNICZNIE - polega TYLKO na
         *   dyscyplinie zespolu.
         */
        System.out.println("\nPackage-by-feature MOZE uzyc 'package-private' do WYMUSZENIA granic - package-by-layer NIE MOZE (wszystko musi byc public).");

        /*
         * ============================================================
         * 🔍 PODEJSCIE HYBRYDOWE
         * ============================================================
         * - W praktyce wiele projektow laczy oba podejscia: NAJPIERW
         *   dzieli wg funkcji/bounded context (poziom najwyzszy), a
         *   DOPIERO WEWNATRZ kazdej funkcji (jesli jest wystarczajaco
         *   duza) stosuje podzial na warstwy.
         * - To NIE jest sprzecznosc - to po prostu 2 ROZNE poziomy
         *   organizacji, kazdy odpowiadajacy na inne pytanie ("co to za
         *   funkcja?" na gorze, "jaka to warstwa?" w srodku danej funkcji).
         */
        System.out.println(HYBRID_TREE);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Package by layer: latwo znalezc "wszystkie kontrolery", ale
         *   niska spojnosc - 1 funkcja rozrzucona po wielu pakietach.
         * - Package by feature: wysoka spojnosc, wspolgra z bounded
         *   context (Lesson06), fundament pod przyszle moduly (Lesson17).
         * - Package by feature MOZE technicznie wymusic granice przez
         *   package-private - package by layer nie moze.
         * - Podejscie hybrydowe: feature na gorze, warstwy wewnatrz
         *   kazdej (wiekszej) funkcji.
         * - W kolejnej lekcji (Lesson10): kierunek zaleznosci (Dependency
         *   Rule) - formalizacja tego, KTORA warstwa/modul MOZE zalezec
         *   od ktorej, niezaleznie od wybranego sposobu pakowania.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static final String PACKAGE_BY_LAYER_TREE = """

            === PACKAGE BY LAYER ===
            com.example
              controller/
                OrderController.java
                UserController.java
                PaymentController.java
              service/
                OrderService.java
                UserService.java
                PaymentService.java
              repository/
                OrderRepository.java
                UserRepository.java
                PaymentRepository.java
            """;

    private static final String PACKAGE_BY_FEATURE_TREE = """
            === PACKAGE BY FEATURE ===
            com.example
              orders/
                OrderController.java
                OrderService.java
                OrderRepository.java
              users/
                UserController.java
                UserService.java
                UserRepository.java
              payments/
                PaymentController.java
                PaymentService.java
                PaymentRepository.java
            """;

    private static final String HYBRID_TREE = """

            === HYBRYDA: FEATURE NA GORZE, WARSTWY WEWNATRZ ===
            com.example
              orders/
                controller/OrderController.java
                service/OrderService.java
                repository/OrderRepository.java
              users/
                controller/UserController.java
                service/UserService.java
                repository/UserRepository.java
            """;

    private static void demonstratePackageByLayerCohesionProblem() {
        System.out.println("\n=== PROBLEM SPOJNOSCI: 'controller' LACZY 3 NIEPOWIAZANE FUNKCJE ===");

        // Symulacja pakietu 'controller' z package-by-layer - WSZYSTKIE
        // kontrolery, niezaleznie od funkcji, w 1 grupie:
        LayerGroupedControllers controllers = new LayerGroupedControllers();
        System.out.println(controllers.orderController() + ", " + controllers.userController() + ", " + controllers.paymentController());
        System.out.println("-> Te 3 klasy NIE MAJA ze soba nic wspolnego poza tym, ze 'sa kontrolerami' - niska spojnosc pakietu.");
        System.out.println("   Zeby zrozumiec CALA funkcje 'Zamowienia', trzeba jeszcze zajrzec do OSOBNYCH grup 'service' i 'repository'.");
    }

    /** Symulacja pakietu package-by-layer 'controller' - grupuje wg WARSTWY, nie wg funkcji. */
    record LayerGroupedControllers(String orderController, String userController, String paymentController) {
        LayerGroupedControllers() {
            this("OrderController", "UserController", "PaymentController");
        }
    }

    private static void demonstratePackageByFeatureCohesion() {
        System.out.println("\n=== WYSOKA SPOJNOSC: 'orders' ZAWIERA WSZYSTKO O ZAMOWIENIACH ===");

        // Symulacja pakietu 'orders' z package-by-feature - WSZYSTKIE 3
        // warstwy TEJ SAMEJ funkcji, razem w 1 grupie:
        OrdersFeature orders = new OrdersFeature();
        System.out.println(orders.controller() + ", " + orders.service() + ", " + orders.repository());
        System.out.println("-> WSZYSTKO potrzebne do zrozumienia 'Zamowien' jest w 1 miejscu - wysoka spojnosc pakietu.");
    }

    /** Symulacja pakietu package-by-feature 'orders' - grupuje wg FUNKCJI, wszystkie warstwy razem. */
    record OrdersFeature(String controller, String service, String repository) {
        OrdersFeature() {
            this("OrderController", "OrderService", "OrderRepository");
        }
    }
}
