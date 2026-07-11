package com.example.javaquest._21_spring_boot.Lesson07_CommandLineRunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

public class _Lesson07_CommandLineRunner {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: CommandLineRunner I ApplicationRunner ===");

        /*
         * ============================================================
         * 📦 KOD URUCHAMIANY PO STARCIE KONTEKSTU
         * ============================================================
         * Czasem potrzebujesz kodu, ktory wykona sie DOKLADNIE RAZ, PO
         * tym, jak CALY kontekst jest JUZ w pelni gotowy (wszystkie
         * beany utworzone, `@PostConstruct` z `_20_spring_core/Lesson18`
         * juz sie wykonaly) - np. wczytanie danych startowych,
         * wypisanie podsumowania, walidacja konfiguracji. Boot daje 2
         * interfejsy do TEGO: `CommandLineRunner` (surowe `String[] args`)
         * i `ApplicationRunner` (sparsowane `ApplicationArguments`).
         */
        System.out.println("CommandLineRunner/ApplicationRunner = kod wykonywany PO pelnym starcie kontekstu, PRZED zwroceniem sterowania do main().");

        demonstrateCommandLineRunnerReceivesRawArgs();
        demonstrateApplicationRunnerReceivesParsedArgs();
        demonstrateMultipleRunnersExecuteInOrder();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `CommandLineRunner.run(String... args)` - SUROWE argumenty,
         *   DOKLADNIE jak w `main(String[] args)`.
         * - `ApplicationRunner.run(ApplicationArguments args)` -
         *   SPARSOWANE - rozroznia `--opcja=wartosc` (option args) od
         *   zwyklych argumentow pozycyjnych.
         * - `SpringApplication.run(...)` NIE ZWRACA sterowania do `main()`,
         *   dopoki WSZYSTKIE runnery sie NIE ZAKONCZA - to
         *   SYNCHRONICZNY, blokujacy krok startu.
         * - Wiele runnerow + `@Order` - kontrola KOLEJNOSCI (jak
         *   `@Order` na aspektach w `_20_spring_core/Lesson22`).
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    static class RawArgsRunner implements CommandLineRunner {
        @Override
        public void run(String... args) {
            System.out.println("  [CommandLineRunner] otrzymane SUROWE argumenty: " + Arrays.toString(args));
        }
    }

    @SpringBootApplication
    static class CommandLineRunnerApp {
        @Bean
        RawArgsRunner rawArgsRunner() {
            return new RawArgsRunner();
        }
    }

    private static void demonstrateCommandLineRunnerReceivesRawArgs() {
        System.out.println("\n=== CommandLineRunner: SUROWE String[] args ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CommandLineRunnerApp.class)
                .web(WebApplicationType.NONE)
                .run("--mode=import", "plik.csv", "--verbose");
        context.close();
    }

    static class ParsedArgsRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            System.out.println("  [ApplicationRunner] argumenty OPCJI (--klucz=wartosc): " + args.getOptionNames());
            for (String optionName : args.getOptionNames()) {
                System.out.println("    " + optionName + " = " + args.getOptionValues(optionName));
            }
            System.out.println("  [ApplicationRunner] argumenty POZYCYJNE (bez '--'): " + args.getNonOptionArgs());
        }
    }

    @SpringBootApplication
    static class ApplicationRunnerApp {
        @Bean
        ParsedArgsRunner parsedArgsRunner() {
            return new ParsedArgsRunner();
        }
    }

    private static void demonstrateApplicationRunnerReceivesParsedArgs() {
        System.out.println("\n=== ApplicationRunner: SPARSOWANE ApplicationArguments ===");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationRunnerApp.class)
                .web(WebApplicationType.NONE)
                .run("--mode=import", "plik.csv", "--verbose");
        context.close();
        System.out.println("-> TE SAME argumenty co w demo CommandLineRunner - ale TERAZ Boot SAM rozroznil '--mode=import'/'--verbose' (opcje) od 'plik.csv' (pozycyjny).");
    }

    static class FirstRunner implements CommandLineRunner {
        @Override
        public void run(String... args) {
            System.out.println("  [FirstRunner, @Order(1)] wykonuje sie PIERWSZY");
        }
    }

    @Order(1)
    static class FirstOrderedRunner extends FirstRunner {
    }

    static class SecondRunner implements CommandLineRunner {
        @Override
        public void run(String... args) {
            System.out.println("  [SecondRunner, @Order(2)] wykonuje sie DRUGI");
        }
    }

    @Order(2)
    static class SecondOrderedRunner extends SecondRunner {
    }

    @SpringBootApplication
    static class MultipleRunnersApp {
        @Bean
        SecondOrderedRunner secondOrderedRunner() {
            // CELOWO zarejestrowany PRZED 'first' - kolejnosc rejestracji NIE MA znaczenia, liczy sie @Order.
            return new SecondOrderedRunner();
        }

        @Bean
        FirstOrderedRunner firstOrderedRunner() {
            return new FirstOrderedRunner();
        }
    }

    private static void demonstrateMultipleRunnersExecuteInOrder() {
        System.out.println("\n=== WIELE RUNNEROW + @Order: WYMUSZONA KOLEJNOSC (NIEZALEZNIE OD KOLEJNOSCI REJESTRACJI @Bean) ===");

        System.out.println("PRZED run() - main() jeszcze CZEKA...");
        ConfigurableApplicationContext context = new SpringApplicationBuilder(MultipleRunnersApp.class)
                .web(WebApplicationType.NONE)
                .run();
        System.out.println("PO run() - main() dostal sterowanie z powrotem, oba runnery JUZ sie zakonczyly.");
        context.close();
    }
}
