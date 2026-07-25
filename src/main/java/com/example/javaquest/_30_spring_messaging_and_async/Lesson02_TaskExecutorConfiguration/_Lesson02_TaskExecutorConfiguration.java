package com.example.javaquest._30_spring_messaging_and_async.Lesson02_TaskExecutorConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson02_TaskExecutorConfiguration {

    @Service
    static class ZadaniaService {
        // PRYWATNE pole (NIE publiczne) - Spring OPAKOWUJE ten bean W proxy CGLIB (potrzebne DLA
        // @Async), a proxy jest tworzony PRZEZ Objenesis Z POMINIECIEM konstruktora - inicjalizatory
        // pol NA SAMEJ POWLOCE proxy (NIE na prawdziwym celu/delegacie) NIGDY sie NIE wykonuja.
        // BEZPOSREDNI dostep DO pola publicznego (`serwis.pole`) czytalby WIEC pole PROXY (null),
        // NIE prawdziwego celu - odkryte empirycznie (NullPointerException). Metody SA prawidlowo
        // DELEGOWANE do celu przez proxy, WIEC getter DZIALA poprawnie - ZAWSZE udostepniaj stan
        // beana @Async przez METODY, nigdy przez publiczne pola.
        private final List<String> nazwyWatkow = new CopyOnWriteArrayList<>();

        @Async("mojaPulaWatkow")
        public void wykonajZadanie(int numer) {
            nazwyWatkow.add(Thread.currentThread().getName());
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }

        public List<String> getNazwyWatkow() {
            return nazwyWatkow;
        }

        public void wyczyscNazwyWatkow() {
            nazwyWatkow.clear();
        }
    }

    @SpringBootApplication
    @EnableAsync
    static class TaskExecutorApp {
        // WLASNY, NAZWANY TaskExecutor - ZASTEPUJE domyslny SimpleAsyncTaskExecutor
        // (Lesson01), ktory TWORZY NOWY watek PRZY KAZDYM wywolaniu (BRAK puli, BRAK limitu!).
        @Bean(name = "mojaPulaWatkow")
        ThreadPoolTaskExecutor mojaPulaWatkow() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(2);
            executor.setMaxPoolSize(4);
            executor.setQueueCapacity(10);
            executor.setThreadNamePrefix("moja-pula-");
            executor.initialize();
            return executor;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 2: Konfiguracja TaskExecutor - wlasna pula watkow dla @Async ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - konfiguracja executora
         * ============================================================
         * Lesson01 uzywal DOMYSLNEGO executora (`SimpleAsyncTaskExecutor`)
         * - TWORZY ON NOWY watek PRZY KAZDYM wywolaniu `@Async`, BEZ
         *   zadnego LIMITU - PRZY DUZEJ liczbie wywolan MOZE
         *   wyczerpac zasoby systemu (analogicznie DO problemu Z
         *   `_29_spring_reactive/Lesson01`: zbyt WIELE watkow
         *   platformowych naraz).
         *
         * `ThreadPoolTaskExecutor` (Spring-owy wrapper NAD
         * `java.util.concurrent.ThreadPoolExecutor`, `_05_multithreading`)
         * DAJE PRAWDZIWA, OGRANICZONA pule - `corePoolSize`,
         * `maxPoolSize`, `queueCapacity` DZIALAJA DOKLADNIE TAK, JAK
         * odpowiadajace parametry `ThreadPoolExecutor`.
         *
         * `@Async("mojaPulaWatkow")` - NAZWA W nawiasie WSKAZUJE,
         * KTORY bean `TaskExecutor` uzyc (BEZ nazwy: Spring szuka
         * beana TYPU `TaskExecutor`/`Executor` - jesli JEST WIECEJ
         * NIZ 1, WYMAGANA jest jawna nazwa).
         */
        System.out.println("ThreadPoolTaskExecutor - PRAWDZIWA, OGRANICZONA pula watkow DLA @Async (ZAMIAST domyslnego SimpleAsyncTaskExecutor Z Lesson01).");

        try (ConfigurableApplicationContext context = SpringApplication.run(TaskExecutorApp.class, "--server.port=0", "--logging.level.root=WARN")) {
            ZadaniaService serwis = context.getBean(ZadaniaService.class);

            demonstrateNamedExecutorIsUsed(serwis);
            demonstratePoolSizeLimitsConcurrency(serwis);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `ThreadPoolTaskExecutor` - Spring-owy wrapper NAD
         *   `ThreadPoolExecutor` (`_05_multithreading/Lesson21`).
         * - `corePoolSize`/`maxPoolSize`/`queueCapacity` - TE SAME
         *   koncepcje CO surowy `ThreadPoolExecutor`.
         * - `@Async("nazwaBeana")` - JAWNE wskazanie, KTOREGO
         *   executora uzyc - WAZNE, GDY W kontekscie JEST WIECEJ NIZ
         *   1 bean `TaskExecutor`.
         * - `threadNamePrefix` - UZYTECZNE DO debugowania (widac W
         *   logach/thread dumpach, KTORA pula wykonala dane zadanie).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateNamedExecutorIsUsed(ZadaniaService serwis) throws InterruptedException {
        System.out.println("\n--- @Async(\"mojaPulaWatkow\") - uzywa NASZEGO, nazwanego executora ---");
        serwis.wykonajZadanie(1);
        Thread.sleep(100);

        System.out.println("Nazwa watku, ktory wykonal zadanie: " + serwis.getNazwyWatkow().get(0));
        assertThat(serwis.getNazwyWatkow().get(0)).startsWith("moja-pula-");
    }

    private static void demonstratePoolSizeLimitsConcurrency(ZadaniaService serwis) throws InterruptedException {
        System.out.println("\n--- corePoolSize=2, maxPoolSize=4 - LIMIT rownoleglosci ---");
        serwis.getNazwyWatkow().clear();

        for (int i = 0; i < 8; i++) {
            serwis.wykonajZadanie(i);
        }
        Thread.sleep(500); // poczekaj, az wszystkie 8 zadan (przetworzonych partiami) sie zakoncza

        long liczbaUnikalnychWatkow = serwis.getNazwyWatkow().stream().distinct().count();
        System.out.println("8 zadan wykonanych, liczba UNIKALNYCH watkow uzytych: " + liczbaUnikalnychWatkow + " (maksymalnie maxPoolSize=4, NIE 8 - w odroznieniu OD domyslnego executora Z Lesson01, ktory stworzylby 8 NOWYCH watkow).");

        assertThat(liczbaUnikalnychWatkow).isLessThanOrEqualTo(4);
        assertThat(serwis.getNazwyWatkow()).hasSize(8);
    }
}
