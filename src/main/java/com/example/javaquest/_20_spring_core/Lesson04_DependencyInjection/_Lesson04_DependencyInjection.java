package com.example.javaquest._20_spring_core.Lesson04_DependencyInjection;

public class _Lesson04_DependencyInjection {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: DEPENDENCY INJECTION (DI) ===");

        /*
         * ============================================================
         * 📦 DI = NAJPEŁNIEJSZA REALIZACJA IoC Z LESSON03
         * ============================================================
         * Dependency Injection to KONKRETNA technika: obiekt W OGOLE
         * nie wie, SKAD biora sie jego zaleznosci - dostaje je GOTOWE,
         * z ZEWNATRZ. W odroznieniu od Service Locatora (Lesson03),
         * obiekt NIE ma nawet UKRYTEJ zaleznosci od "kogos, kogo pyta" -
         * zaleznosc jest WIDOCZNA wprost (np. w sygnaturze konstruktora).
         * Dzisiaj: 3 SPOSOBY dostarczenia zaleznosci - konstruktor,
         * setter, pole - na TYM SAMYM przykladzie, zeby porownac.
         */
        System.out.println("DI = zaleznosc dostarczona GOTOWA, z zewnatrz - obiekt NIE wie i NIE pyta, skad ona pochodzi.");

        demonstrateConstructorInjection();
        demonstrateSetterInjection();
        demonstrateFieldInjection();

        /*
         * ============================================================
         * 🔹 3 RODZAJE WSTRZYKIWANIA - SZYBKI PODGLAD (SZCZEGOLY DALEJ)
         * ============================================================
         * - KONSTRUKTOR (Lesson10) — zaleznosc jako parametr konstruktora,
         *   MOZE byc `final`, obiekt NIGDY nie istnieje w niekompletnym
         *   stanie. REKOMENDOWANY domyslny wybor.
         * - SETTER — zaleznosc ustawiana metoda `setXxx(...)` PO
         *   utworzeniu obiektu - przydatne dla zaleznosci OPCJONALNYCH,
         *   ale obiekt MOZE istniec chwilowo w niekompletnym stanie.
         * - POLE (Lesson11) — zaleznosc wstrzykiwana WPROST do pola
         *   (przez refleksje) - NAJKROTSZY zapis, ale NAJWIECEJ wad
         *   (zobaczysz je szczegolowo w Lesson11).
         */
        System.out.println("\n3 rodzaje DI: konstruktor (rekomendowany), setter (zaleznosci opcjonalne), pole (najkrotszy zapis, najwiecej wad).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DI = zaleznosc dostarczona Z ZEWNATRZ, obiekt jej NIE szuka
         *   (kontrast z Service Locatorem z Lesson03).
         * - 3 mechanizmy dostarczenia: konstruktor/setter/pole - KAZDY
         *   dziala, ale NIE sa rownowazne pod wzgledem jakosci kodu -
         *   Lesson10/11 pojda w te roznice GLEBOKO.
         * - To WLASNIE ta technika (glownie w wariancie konstruktorowym)
         *   stoi za kontenerem Springa z Lesson01/02.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private interface Notifier {
        void notify(String message);
    }

    private static class ConsoleNotifier implements Notifier {
        @Override
        public void notify(String message) {
            System.out.println("  [Powiadomienie] " + message);
        }
    }

    // KONSTRUKTOR - zaleznosc WYMAGANA, WIDOCZNA w sygnaturze, mozliwe 'final'.
    private static class OrderServiceConstructorInjection {
        private final Notifier notifier;

        OrderServiceConstructorInjection(Notifier notifier) {
            this.notifier = notifier;
        }

        void placeOrder(String product) {
            notifier.notify("Zamowienie zlozone: " + product);
        }
    }

    private static void demonstrateConstructorInjection() {
        System.out.println("\n=== WSTRZYKIWANIE PRZEZ KONSTRUKTOR ===");

        OrderServiceConstructorInjection service = new OrderServiceConstructorInjection(new ConsoleNotifier());
        service.placeOrder("Laptop");
        System.out.println("-> zaleznosc WIDOCZNA w konstruktorze, pole MOZE byc 'final' - obiekt NIGDY nie istnieje bez swojej zaleznosci.");
    }

    // SETTER - zaleznosc USTAWIANA PO utworzeniu, dobra dla OPCJONALNYCH zaleznosci.
    private static class OrderServiceSetterInjection {
        private Notifier notifier;

        void setNotifier(Notifier notifier) {
            this.notifier = notifier;
        }

        void placeOrder(String product) {
            if (notifier != null) {
                notifier.notify("Zamowienie zlozone: " + product);
            } else {
                System.out.println("  (brak powiadomienia - notifier nieustawiony, ale zamowienie i tak przeszlo)");
            }
        }
    }

    private static void demonstrateSetterInjection() {
        System.out.println("\n=== WSTRZYKIWANIE PRZEZ SETTER ===");

        OrderServiceSetterInjection withoutNotifier = new OrderServiceSetterInjection();
        System.out.println("Obiekt UTWORZONY, ale BEZ zaleznosci - w tym stanie moze byc uzywany (opcjonalna zaleznosc):");
        withoutNotifier.placeOrder("Mysz");

        OrderServiceSetterInjection withNotifier = new OrderServiceSetterInjection();
        withNotifier.setNotifier(new ConsoleNotifier());
        System.out.println("Ten sam typ obiektu, ale PO wywolaniu settera:");
        withNotifier.placeOrder("Klawiatura");
        System.out.println("-> elastyczne dla zaleznosci OPCJONALNYCH, ale obiekt MOZE chwilowo istniec w niekompletnym/niespojnym stanie.");
    }

    // POLE - zaleznosc wstrzykiwana WPROST (tu: recznie przez refleksje, imitujac to, co Spring robi za kulisami).
    private static class OrderServiceFieldInjection {
        private Notifier notifier; // w Springu: @Autowired private Notifier notifier;

        void placeOrder(String product) {
            notifier.notify("Zamowienie zlozone: " + product);
        }
    }

    private static void demonstrateFieldInjection() {
        System.out.println("\n=== WSTRZYKIWANIE PRZEZ POLE (RECZNA SYMULACJA TEGO, CO ROBI @Autowired) ===");

        OrderServiceFieldInjection service = new OrderServiceFieldInjection();
        try {
            java.lang.reflect.Field field = OrderServiceFieldInjection.class.getDeclaredField("notifier");
            field.setAccessible(true); // dokladnie to robi kontener Springa dla @Autowired na polu
            field.set(service, new ConsoleNotifier());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        service.placeOrder("Monitor");
        System.out.println("-> W SPRINGU wygladaloby to jak '@Autowired private Notifier notifier;' - kontener robi DOKLADNIE to, co widzisz wyzej: ustawia pole przez refleksje.");
        System.out.println("   NAJKROTSZY zapis w kodzie, ale (Lesson11 wyjasni) najwiecej ukrytych wad - m.in. TEN sam problem 'niekompletnego obiektu' co przy setterach.");
    }
}
