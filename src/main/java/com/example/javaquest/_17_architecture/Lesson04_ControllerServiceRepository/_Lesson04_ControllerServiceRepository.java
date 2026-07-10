package com.example.javaquest._17_architecture.Lesson04_ControllerServiceRepository;

import java.util.ArrayList;
import java.util.List;

public class _Lesson04_ControllerServiceRepository {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: ANATOMIA TROJKI CONTROLLER/SERVICE/REPOSITORY ===");

        /*
         * ============================================================
         * 📦 NAZWY JUZ ZNASZ - TERAZ: TYPOWE BLEDY W ICH UZYCIU
         * ============================================================
         * - Nazewnictwo Controller/Service/Repository (i roznice
         *   Repository vs DAO) poznales juz w `_10_dao/Lesson02` i
         *   `Lesson10_RepositoryVsDao` - NIE powtarzamy tego tutaj.
         * - Ta lekcja (swiadomie BEZ Springa - te same zasady dotycza
         *   dowolnego frameworka, a Spring Boot to ostatni rozdzial kursu)
         *   skupia sie na czyms INNYM: jak DOKLADNIE rozgraniczyc
         *   odpowiedzialnosc miedzy tymi 3 warstwami, i jakie 2 NAJCZESTSZE
         *   bledy popelniaja zespoly w praktyce - "gruby kontroler" (fat
         *   controller) i "przeciekajace repozytorium" (repository
         *   leaking).
         */
        System.out.println("Nazwy znasz z _10_dao - tu: dokladne granice odpowiedzialnosci i 2 najczestsze bledy w praktyce.");

        /*
         * ============================================================
         * 🔹 CONTROLLER: TLUMACZ FORMATU, NIE DECYDENT BIZNESOWY
         * ============================================================
         * - Jedyna odpowiedzialnosc kontrolera: PRZETLUMACZYC surowe dane
         *   wejsciowe (z formularza/JSON/argumentow CLI) na wywolanie
         *   Service, i PRZETLUMACZYC wynik Service z powrotem na format
         *   wyjsciowy (kod statusu HTTP, tekst konsoli).
         * - Kontroler NIE POWINIEN zawierac ZADNEJ reguly biznesowej -
         *   "czy ta operacja jest dozwolona" to ZAWSZE pytanie do Service.
         */
        demonstrateThinController();

        /*
         * ============================================================
         * 🔍 ANTY-WZORZEC 1: "GRUBY KONTROLER" (FAT CONTROLLER)
         * ============================================================
         * - Objaw: kontroler SAM sprawdza reguly biznesowe ("czy limit
         *   zadan zostal przekroczony?"), bo "to tylko 1 warunek, nie
         *   oplaca sie robic osobnej metody w Service".
         * - Problem: gdy pojawi sie DRUGI kontroler dla TEJ SAMEJ logiki
         *   (np. CLI obok REST API, albo wersja API v2) - reguła MUSI
         *   zostac POWIELONA, bo zyje w kontrolerze, a nie w Service.
         *   Dokladnie "Shotgun Surgery" (`_16_clean_code/Lesson14`) na
         *   poziomie architektury.
         */
        demonstrateFatControllerAntiPattern();

        /*
         * ============================================================
         * 🔹 NAPRAWA: PRZENIESIENIE REGULY DO SERVICE
         * ============================================================
         */
        demonstrateFixedThinControllerWithTwoEntryPoints();

        /*
         * ============================================================
         * 🔍 SERVICE: ORKIESTRACJA PRZYPADKU UZYCIA (USE CASE)
         * ============================================================
         * - Service odpowiada za CALY przypadek uzycia: moze wywolac
         *   WIELE repozytoriow/wspolpracownikow, podejmuje WSZYSTKIE
         *   decyzje biznesowe, i (jak zobaczysz w Lesson13) to WLASNIE TU
         *   powinna zyc granica transakcji.
         * - "Anemiczny" Service (ktory dla KAZDEJ metody TYLKO przekazuje
         *   wywolanie do 1 repozytorium, bez zadnej wlasnej logiki) nie
         *   jest sam w sobie bledem - ale jesli dzieje sie tak
         *   SYSTEMATYCZNIE dla calej aplikacji, to sygnal, ze logika
         *   biznesowa "uciekla" gdzie indziej (do encji - patrz Lesson05,
         *   albo do kontrolera - patrz wyzej).
         */
        System.out.println("\nService = orkiestracja calego przypadku uzycia. 'Zawsze pass-through' = sygnal, ze logika uciekla gdzie indziej.");

        /*
         * ============================================================
         * 🔹 REPOSITORY: TYLKO PRZECHOWYWANIE, ZERO DECYZJI BIZNESOWYCH
         * ============================================================
         * - Repository (albo DAO - patrz `_10_dao/Lesson10` dla niuansu
         *   nazewnictwa) NIE decyduje, CZY operacja jest dozwolona - tylko
         *   WYKONUJE zapis/odczyt, o ktory poprosi Service.
         */
        System.out.println("\nRepository: tylko wykonanie zapisu/odczytu - ZERO decyzji 'czy wolno'.");

        /*
         * ============================================================
         * 🔍 ANTY-WZORZEC 2: "PRZECIEKAJACE REPOZYTORIUM" (REPOSITORY LEAKING)
         * ============================================================
         * - Objaw: kontroler dostaje BEZPOSREDNI dostep do Repository
         *   (pomijajac Service) "bo to tylko prosty odczyt, po co
         *   przechodzic przez Service".
         * - Problem: teraz CZESC operacji przechodzi przez Service (i ma
         *   spojna walidacje/logowanie/transakcje), a CZESC omija go
         *   calkowicie - architektura systemu staje sie NIESPOJNA i trudna
         *   do przewidzenia ("dlaczego TU jest walidacja, a TAM nie?").
         */
        demonstrateRepositoryLeakingIntoControllerAntiPattern();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Controller: tlumacz formatu, ZERO logiki biznesowej.
         * - Service: orkiestrator przypadku uzycia, wszystkie decyzje
         *   biznesowe, granica transakcji (Lesson13).
         * - Repository: tylko przechowywanie, zero decyzji biznesowych.
         * - Anty-wzorzec 1: gruby kontroler - logika biznesowa w
         *   kontrolerze, powielana przy 2. punkcie wejscia.
         * - Anty-wzorzec 2: przeciekajace repozytorium - kontroler omija
         *   Service, architektura staje sie niespojna.
         * - W kolejnej lekcji (Lesson05): anemiczny model domenowy - co
         *   sie dzieje, gdy encje sa TYLKO workami na dane, a CALA logika
         *   (nawet ta "wlasciwa" dla danych) trafia do Service.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    // Wspolny "magazyn" zadan dla wszystkich demonstracji w tej lekcji.
    private static final List<Task> TASKS = new ArrayList<>();
    private static int nextTaskLimitPerUser = 3;

    record Task(String owner, String title) {
    }

    private static void demonstrateThinController() {
        System.out.println("\n=== 'CHUDY' KONTROLER - TYLKO TLUMACZY FORMAT ===");

        TaskRepository repository = new InMemoryTaskRepository();
        TaskService service = new TaskService(repository);

        String response = handleAddTaskRequestThin(service, "ala", "Kupic mleko");
        System.out.println("Odpowiedz: " + response);
    }

    interface TaskRepository {
        void save(Task task);

        long countByOwner(String owner);
    }

    static class InMemoryTaskRepository implements TaskRepository {
        private final List<Task> tasks = new ArrayList<>();

        @Override
        public void save(Task task) {
            tasks.add(task);
        }

        @Override
        public long countByOwner(String owner) {
            return tasks.stream().filter(t -> t.owner().equals(owner)).count();
        }
    }

    static class TaskService {
        private static final int MAX_TASKS_PER_USER = 3;

        private final TaskRepository repository;

        TaskService(TaskRepository repository) {
            this.repository = repository;
        }

        TaskResult addTask(String owner, String title) {
            if (repository.countByOwner(owner) >= MAX_TASKS_PER_USER) {
                return TaskResult.failure("limit " + MAX_TASKS_PER_USER + " zadan na uzytkownika osiagniety");
            }
            repository.save(new Task(owner, title));
            return TaskResult.ok();
        }
    }

    record TaskResult(boolean success, String errorMessage) {
        static TaskResult ok() {
            return new TaskResult(true, null);
        }

        static TaskResult failure(String errorMessage) {
            return new TaskResult(false, errorMessage);
        }
    }

    /** DOBRA: kontroler TYLKO tlumaczy - zadanie biznesowe ("czy limit przekroczony") jest w Service. */
    private static String handleAddTaskRequestThin(TaskService service, String owner, String title) {
        TaskResult result = service.addTask(owner, title);
        return result.success() ? "201 Created" : "400 Bad Request - " + result.errorMessage();
    }

    private static void demonstrateFatControllerAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: 'GRUBY KONTROLER' ===");

        String response = handleAddTaskRequestFat("bartek", "Napisac raport");
        System.out.println("Odpowiedz (z 'grubego' kontrolera): " + response);
        System.out.println("-> Regula 'limit 3 zadan' zyje TU, w kontrolerze - nie w Service.");
    }

    /** ZLA: kontroler SAM sprawdza regule biznesowa ('limit zadan') zamiast delegowac do Service. */
    private static String handleAddTaskRequestFat(String owner, String title) {
        long currentCount = TASKS.stream().filter(t -> t.owner().equals(owner)).count();
        if (currentCount >= nextTaskLimitPerUser) { // logika biznesowa W KONTROLERZE!
            return "400 Bad Request - limit zadan osiagniety";
        }
        TASKS.add(new Task(owner, title));
        return "201 Created";
    }

    private static void demonstrateFixedThinControllerWithTwoEntryPoints() {
        System.out.println("\n=== NAPRAWA: 2 PUNKTY WEJSCIA (REST-podobny I CLI-podobny) DZIELA 1 SERVICE ===");

        TaskService service = new TaskService(new InMemoryTaskRepository());

        String restLikeResponse = handleAddTaskRequestThin(service, "celina", "Zaplacic rachunki");
        String cliLikeResponse = handleAddTaskCliCommand(service, "celina", "dodaj-zadanie", "Umyc auto");

        System.out.println("REST-podobny kontroler: " + restLikeResponse);
        System.out.println("CLI-podobny kontroler: " + cliLikeResponse);
        System.out.println("-> OBA punkty wejscia deleguja do TEJ SAMEJ regule w TaskService - zero powielenia logiki.");
    }

    /** Drugi, ROZNY kontroler (symulacja CLI) - deleguje do TEGO SAMEGO Service, zero powielonej logiki. */
    private static String handleAddTaskCliCommand(TaskService service, String owner, String command, String title) {
        if (!"dodaj-zadanie".equals(command)) {
            return "nieznana komenda";
        }
        TaskResult result = service.addTask(owner, title);
        return result.success() ? "OK: zadanie dodane" : "BLAD: " + result.errorMessage();
    }

    private static void demonstrateRepositoryLeakingIntoControllerAntiPattern() {
        System.out.println("\n=== ANTY-WZORZEC: KONTROLER OMIJA SERVICE I UZYWA REPOSITORY WPROST ===");

        TaskRepository repository = new InMemoryTaskRepository();
        TaskService service = new TaskService(repository);

        // 'Dobra' sciezka - przechodzi przez Service, ma walidacje limitu:
        System.out.println(handleAddTaskRequestThin(service, "damian", "Zadanie 1"));

        // ZLA sciezka - "tylko prosty zapis", kontroler POMIJA Service i
        // uzywa Repository BEZPOSREDNIO - walidacja limitu ZNIKA:
        repository.save(new Task("damian", "Zadanie omijajace limit"));
        repository.save(new Task("damian", "Kolejne omijajace limit"));
        repository.save(new Task("damian", "I kolejne - limit juz dawno przekroczony!"));

        System.out.println("Liczba zadan 'damian' PO ominieciu Service: " + repository.countByOwner("damian")
                + " (limit mial byc 3, ale walidacja ZOSTALA OMINIETA dla czesci zapisow)");
        System.out.println("-> Architektura NIESPOJNA: czesc zapisow ma walidacje, czesc - nie.");
    }
}
