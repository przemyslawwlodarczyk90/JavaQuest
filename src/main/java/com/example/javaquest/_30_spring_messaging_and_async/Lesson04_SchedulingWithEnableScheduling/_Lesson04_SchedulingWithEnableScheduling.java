package com.example.javaquest._30_spring_messaging_and_async.Lesson04_SchedulingWithEnableScheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson04_SchedulingWithEnableScheduling {

    @Component
    static class ZadaniaCykliczne {
        private final AtomicInteger licznikFixedRate = new AtomicInteger();
        private final AtomicInteger licznikFixedDelay = new AtomicInteger();
        private final AtomicInteger licznikCron = new AtomicInteger();

        @Scheduled(fixedRate = 50)
        public void codzienne50ms() {
            licznikFixedRate.incrementAndGet();
        }

        @Scheduled(fixedDelay = 50)
        public void poZakonczeniuPoprzedniego() {
            licznikFixedDelay.incrementAndGet();
        }

        @Scheduled(cron = "*/1 * * * * *") // co 1 sekunde
        public void coSekunde() {
            licznikCron.incrementAndGet();
        }

        public int getLicznikFixedRate() {
            return licznikFixedRate.get();
        }

        public int getLicznikFixedDelay() {
            return licznikFixedDelay.get();
        }

        public int getLicznikCron() {
            return licznikCron.get();
        }
    }

    @SpringBootApplication
    @EnableScheduling
    static class SchedulingApp {
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 4: @Scheduled + @EnableScheduling - zadania cykliczne w Spring ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - @Scheduled
         * ============================================================
         * `@EnableScheduling` WLACZA obsluge `@Scheduled` - metody
         * OZNACZONE ta adnotacja SA WYWOLYWANE CYKLICZNIE PRZEZ Spring
         * (WEWNETRZNY `TaskScheduler`, DOMYSLNIE 1-watkowy - WAZNE:
         * WSZYSTKIE zadania `@Scheduled` DZIELA TEN SAM, pojedynczy
         * watek, chyba ze skonfigurujesz WLASNY `TaskScheduler` Z
         * WIEKSZA pula - inaczej DLUGO trwajace zadanie OPOZNIA
         * WSZYSTKIE inne).
         *
         * 3 style harmonogramowania:
         * - `fixedRate` - URUCHOM CO X ms OD POCZATKU poprzedniego
         *   wywolania (NIEZALEZNIE, czy poprzednie sie zakonczylo).
         * - `fixedDelay` - URUCHOM X ms PO ZAKONCZENIU poprzedniego
         *   wywolania.
         * - `cron` - wyrazenie cron (jak crontab Linuksa, ale Z
         *   DODATKOWYM polem sekund NA POCZATKU).
         */
        System.out.println("@Scheduled: fixedRate (co X ms od poczatku), fixedDelay (X ms po zakonczeniu), cron (wyrazenie crontab+sekundy).");

        try (ConfigurableApplicationContext context = SpringApplication.run(SchedulingApp.class, "--server.port=0", "--logging.level.root=WARN")) {
            ZadaniaCykliczne zadania = context.getBean(ZadaniaCykliczne.class);

            demonstrateFixedRateAndFixedDelay(zadania);
            demonstrateCronExpression(zadania);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Scheduled(fixedRate = X)` - CYKL co X ms OD STARTU
         *   poprzedniego wywolania.
         * - `@Scheduled(fixedDelay = X)` - X ms PO ZAKONCZENIU
         *   poprzedniego wywolania (BEZPIECZNIEJSZE DLA zadan O
         *   ZMIENNYM czasie trwania - BRAK nakladania sie).
         * - `@Scheduled(cron = "...")` - PELNA elastycznosc (np.
         *   "co poniedzialek O 3:00").
         * - DOMYSLNIE WSZYSTKIE `@Scheduled` metody DZIELA 1 watek -
         *   WLASNY `TaskScheduler` (podobnie DO `TaskExecutor` Z
         *   Lesson02) POTRZEBNY DLA rownoleglych zadan cyklicznych.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static void demonstrateFixedRateAndFixedDelay(ZadaniaCykliczne zadania) throws InterruptedException {
        System.out.println("\n--- fixedRate=50ms vs fixedDelay=50ms - obserwacja PO 500ms ---");
        Thread.sleep(500);

        System.out.println("fixedRate (50ms): wykonano " + zadania.getLicznikFixedRate() + " razy (oczekiwane ~10, bo 500/50=10).");
        System.out.println("fixedDelay (50ms): wykonano " + zadania.getLicznikFixedDelay() + " razy (podobnie ~10, bo metoda jest BARDZO szybka - roznica WIDOCZNA TYLKO, gdy metoda SAMA trwa dlugo).");

        assertThat(zadania.getLicznikFixedRate()).isGreaterThanOrEqualTo(5);
        assertThat(zadania.getLicznikFixedDelay()).isGreaterThanOrEqualTo(5);
    }

    private static void demonstrateCronExpression(ZadaniaCykliczne zadania) throws InterruptedException {
        System.out.println("\n--- cron = \"*/1 * * * * *\" (co 1 sekunde) - obserwacja PO DODATKOWYCH 2.5s ---");
        Thread.sleep(2500);

        System.out.println("cron (co 1s): wykonano " + zadania.getLicznikCron() + " razy PO LACZNYM czasie ~3s (oczekiwane 2-3).");
        assertThat(zadania.getLicznikCron()).isGreaterThanOrEqualTo(1);
    }
}
