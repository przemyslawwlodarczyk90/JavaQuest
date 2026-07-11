package com.example.javaquest._20_spring_core.Lesson03_InversionOfControl;

import java.util.HashMap;
import java.util.Map;

public class _Lesson03_InversionOfControl {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: INVERSION OF CONTROL (IoC) ===");

        /*
         * ============================================================
         * 📦 IoC TO ZASADA, NIE KONKRETNE NARZEDZIE
         * ============================================================
         * "Inversion of Control" NIE oznacza "uzywaj Springa" - to
         * OGOLNA zasada projektowa: kontrola nad TYM, KIEDY i JAK
         * wywolywany jest Twoj kod, zostaje ODWROCONA — przenosi sie z
         * TWOJEGO kodu do JAKIEJS zewnetrznej "ramy" (frameworka,
         * kontenera, biblioteki). Znana tez jako "Zasada Hollywood":
         * "Don't call us, we'll call you" — nie Ty wywolujesz framework,
         * TO framework wywoluje Twoj kod, kiedy uzna to za stosowne.
         * Dependency Injection (Lesson04) to JEDNA konkretna TECHNIKA
         * realizujaca IoC - dzisiejsza lekcja pokazuje SZERSZY obraz.
         */
        System.out.println("IoC = zasada Hollywood: 'nie dzwon do nas, my zadzwonimy do Ciebie' - kontrola nad wywolaniem Twojego kodu jest ODWROCONA.");

        demonstrateTraditionalControlFlow();
        demonstrateInversionViaTemplateMethod();
        demonstrateServiceLocatorAsPartialInversion();

        /*
         * ============================================================
         * 🔹 3 TECHNIKI REALIZUJACE IoC (OD NAJSTARSZEJ)
         * ============================================================
         * 1. Service Locator — obiekt SAM aktywnie PYTA centralny
         *    rejestr o swoja zaleznosc (`ServiceLocator.get(Foo.class)`).
         *    CZESCIOWA inwersja — kontrola nad TYM, CO dostaniesz, wciaz
         *    nalezy do zewnetrznego rejestru, ale obiekt WCIAZ sam
         *    INICJUJE pytanie (ukryta zaleznosc od samego lokatora).
         * 2. Template Method (wzorzec projektowy, patrz demo nizej) —
         *    klasa bazowa/framework NARZUCA KOLEJNOSC wywolan, Twoj kod
         *    dostarcza tylko "wypelnienie" w konkretnych punktach.
         * 3. Dependency Injection (Lesson04) — NAJPELNIEJSZA inwersja:
         *    obiekt w OGOLE nie wie, SKAD biora sie jego zaleznosci -
         *    ktos z ZEWNATRZ (kontener) dostarcza je GOTOWE. To wlasnie
         *    ta technika stoi za demo z Lesson01/02.
         */
        System.out.println("\n3 techniki IoC (od najslabszej do najpelniejszej inwersji): Service Locator -> Template Method -> Dependency Injection.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - IoC = kontrola nad wywolaniem TWOJEGO kodu przenosi sie NA
         *   ZEWNATRZ (do frameworka/kontenera) - "zasada Hollywood".
         * - DI (Lesson04) to JEDNA technika realizujaca IoC - NIE
         *   jedyna i NIE synonim.
         * - Kontener Springa z Lesson01/02 to zastosowanie IoC na skala
         *   CALEJ aplikacji: framework decyduje, KIEDY stworzyc Twoje
         *   obiekty, w JAKIEJ kolejnosci, i CZYM je wypelnic.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static class ReportGenerator {
        void generateReport() {
            System.out.println("  1. Sam otwieram zrodlo danych");
            System.out.println("  2. Sam czytam dane");
            System.out.println("  3. Sam formatuje raport");
            System.out.println("  4. Sam zamykam zrodlo danych");
        }
    }

    private static void demonstrateTraditionalControlFlow() {
        System.out.println("\n=== TRADYCYJNY PRZEPLYW STEROWANIA (BEZ IoC) ===");

        ReportGenerator generator = new ReportGenerator();
        System.out.println("TWOJ kod decyduje o WSZYSTKIM - wywoluje metode, KTORA SAMA decyduje o calej kolejnosci dzialan:");
        generator.generateReport();
        System.out.println("-> generateReport() SAMO kontroluje caly przebieg - typowy, 'zwykly' kod, BEZ odwrocenia kontroli.");
    }

    // Klasa bazowa (udaje "framework") narzuca KOLEJNOSC - to ONA jest w kontroli.
    private static abstract class ReportTemplate {
        // final = podklasy NIE MOGA zmienic kolejnosci krokow - tylko ich TRESC.
        final void generate() {
            openSource();
            readData();
            formatReport();
            closeSource();
        }

        private void openSource() {
            System.out.println("  [FRAMEWORK] otwieram zrodlo danych (wspolne dla WSZYSTKICH raportow)");
        }

        private void closeSource() {
            System.out.println("  [FRAMEWORK] zamykam zrodlo danych (wspolne dla WSZYSTKICH raportow)");
        }

        // Punkty rozszerzenia - TU "framework" ODDAJE glos TWOJEMU kodowi.
        protected abstract void readData();

        protected abstract void formatReport();
    }

    private static class CsvReportTemplate extends ReportTemplate {
        @Override
        protected void readData() {
            System.out.println("  [TWOJ KOD] czytam dane jako CSV");
        }

        @Override
        protected void formatReport() {
            System.out.println("  [TWOJ KOD] formatuje jako wiersze rozdzielone przecinkami");
        }
    }

    private static void demonstrateInversionViaTemplateMethod() {
        System.out.println("\n=== ODWROCENIE KONTROLI PRZEZ TEMPLATE METHOD (WZORZEC PROJEKTOWY) ===");

        ReportTemplate report = new CsvReportTemplate();
        System.out.println("TERAZ to KLASA BAZOWA (ReportTemplate.generate()) dyktuje KOLEJNOSC - Twoj kod (CsvReportTemplate) TYLKO wypelnia 2 punkty:");
        report.generate();
        System.out.println("-> 'Zasada Hollywood' w akcji: NIE Ty wywolujesz readData()/formatReport() - TO framework (generate()) wywoluje JE, kiedy uzna za stosowne.");
    }

    private interface PaymentGateway {
        void pay(double amount);
    }

    private static class FakePaymentGateway implements PaymentGateway {
        @Override
        public void pay(double amount) {
            System.out.println("  [PaymentGateway] przetworzono platnosc: " + amount + " PLN");
        }
    }

    // Centralny rejestr, z ktorego obiekty SAME "wyciagaja" swoje zaleznosci.
    private static class ServiceLocator {
        private static final Map<Class<?>, Object> REGISTRY = new HashMap<>();

        static void register(Class<?> type, Object instance) {
            REGISTRY.put(type, instance);
        }

        @SuppressWarnings("unchecked")
        static <T> T get(Class<T> type) {
            Object instance = REGISTRY.get(type);
            if (instance == null) {
                throw new IllegalStateException("Brak zarejestrowanej uslugi dla " + type.getSimpleName());
            }
            return (T) instance;
        }
    }

    private static class CheckoutService {
        void checkout(double amount) {
            // UWAGA: ta klasa SAMA, AKTYWNIE pyta o swoja zaleznosc - to CZESCIOWA inwersja.
            PaymentGateway gateway = ServiceLocator.get(PaymentGateway.class);
            gateway.pay(amount);
        }
    }

    private static void demonstrateServiceLocatorAsPartialInversion() {
        System.out.println("\n=== SERVICE LOCATOR: CZESCIOWA INWERSJA (KONTRAST Z DI Z LESSON04) ===");

        ServiceLocator.register(PaymentGateway.class, new FakePaymentGateway());

        CheckoutService checkout = new CheckoutService();
        System.out.println("CheckoutService SAM aktywnie PYTA ServiceLocator o swoja zaleznosc (PaymentGateway), zamiast dostac ja z zewnatrz:");
        checkout.checkout(199.99);
        System.out.println("-> to WCIAZ IoC (CheckoutService nie tworzy PaymentGateway przez 'new') - ale UKRYTA zaleznosc od SAMEGO ServiceLocator to wada,");
        System.out.println("   ktora Dependency Injection (Lesson04) calkowicie eliminuje - zobaczysz to w nastepnej lekcji.");
    }
}
