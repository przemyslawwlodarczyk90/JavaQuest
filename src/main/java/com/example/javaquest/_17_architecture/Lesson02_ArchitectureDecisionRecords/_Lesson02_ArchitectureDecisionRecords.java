package com.example.javaquest._17_architecture.Lesson02_ArchitectureDecisionRecords;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class _Lesson02_ArchitectureDecisionRecords {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: ARCHITECTURE DECISION RECORDS (ADR) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: "DLACZEGO" GINIE, "CO" ZOSTAJE
         * ============================================================
         * - Kod pokazuje, CO system robi - ale rzadko pokazuje, DLACZEGO
         *   zrobiono to WLASNIE TAK, a nie inaczej. Za pol roku nikt (w tym
         *   Ty sam) nie pamieta, czy "uzylismy HashMap zamiast bazy danych"
         *   bylo swiadoma decyzja, czy prowizorka "na szybko".
         * - Michael Nygard w artykule "Documenting Architecture Decisions"
         *   (2011) zaproponowal PROSTY, lekki format zapisywania WAZNYCH
         *   decyzji architektonicznych (Lesson01: decyzji TRUDNYCH do
         *   odwrocenia) - Architecture Decision Record (ADR).
         * - ADR to NIE gruby dokument projektowy pisany raz na poczatku
         *   projektu (Big Design Up Front, Lesson01) - to KROTKI zapis
         *   TWORZONY W MOMENCIE podjecia KAZDEJ istotnej decyzji, trzymany
         *   RAZEM z kodem (np. w repozytorium, folder `docs/adr/`).
         */
        System.out.println("ADR (Nygard, 2011) = lekki, krotki zapis WAZNEJ decyzji architektonicznej, tworzony NA BIEZACO.");

        /*
         * ============================================================
         * 🔹 FORMAT ADR: 4 SEKCJE
         * ============================================================
         * - TYTUL - krotki, konkretny (np. "Uzyj PostgreSQL zamiast
         *   MongoDB do przechowywania zamowien").
         * - STATUS - "proponowana" / "zaakceptowana" / "odrzucona" /
         *   "zastapiona przez ADR-00X" (patrz nizej: superseding).
         * - KONTEKST - jaka SYTUACJA/PROBLEM wymusily te decyzje (jakie
         *   ograniczenia, jakie alternatywy byly brane pod uwage).
         * - DECYZJA - co KONKRETNIE zostalo postanowione.
         * - KONSEKWENCJE - co ta decyzja OZNACZA na przyszlosc (zarowno
         *   pozytywy, jak i koszty/kompromisy, ktore SWIADOMIE
         *   akceptujemy).
         */
        demonstrateAdrStructure();

        /*
         * ============================================================
         * 🔍 KIEDY PISAC ADR (A KIEDY NIE)
         * ============================================================
         * - ADR pisze sie dla decyzji o ZNACZENIU ARCHITEKTONICZNYM
         *   (Lesson01: trudnych/kosztownych do odwrocenia) - wybor bazy
         *   danych, wybor stylu komunikacji miedzy modulami, wybor
         *   sposobu uwierzytelniania.
         * - NIE pisze sie ADR dla kazdej drobnej decyzji kodowej (nazwa
         *   zmiennej, wybor 1 algorytmu w 1 metodzie) - to zalatwiaja
         *   zwykle komentarze/code review (`_16_clean_code/Lesson22`).
         * - Praktyczny test: "czy za rok ktos zapyta NAS, dlaczego to tak
         *   zrobilismy, i czy odpowiedz bedzie WARTA zapisania?" - jesli
         *   tak, to kandydat na ADR.
         */
        System.out.println("\nADR TYLKO dla decyzji o realnym znaczeniu architektonicznym - nie dla kazdego drobiazgu.");

        /*
         * ============================================================
         * 🔹 ADR SA NIEZMIENNE - ZMIANA DECYZJI = NOWY ADR ("SUPERSEDING")
         * ============================================================
         * - Kiedy okazuje sie, ze wczesniejsza decyzja byla bledna albo
         *   uwarunkowania sie zmienily, NIE EDYTUJEMY starego ADR - to
         *   zniszczyloby HISTORYCZNY zapis "co i dlaczego mysleismy W TYM
         *   MOMENCIE".
         * - Zamiast tego piszemy NOWY ADR, ktory jawnie mowi "zastepuje
         *   ADR-00X" - a STARY ADR dostaje status "zastapiona przez
         *   ADR-00Y". Cala historia decyzji (wlacznie z tymi juz
         *   nieaktualnymi) zostaje zachowana i CZYTELNA.
         */
        demonstrateSupersedingAnAdr();

        /*
         * ============================================================
         * 🔍 ADR JAKO CZESC REPOZYTORIUM, NIE OSOBNE NARZEDZIE
         * ============================================================
         * - ADR trzyma sie RAZEM z kodem (np. jako pliki Markdown w
         *   `docs/adr/0001-xxx.md`, `0002-yyy.md`) - dzieki temu
         *   przechodzi przez TEN SAM code review i TA SAMA historie Git,
         *   co reszta projektu, zamiast ginac w osobnej wiki/dokumencie,
         *   ktorego nikt nie aktualizuje.
         * - W tej lekcji modelujemy ADR jako prosty, niezmienny `record`
         *   Javy + rejestr (`AdrLog`) - to WLASNIE ODDAJE ducha metody:
         *   prosty, ustrukturyzowany zapis, latwy do przegladania.
         */
        System.out.println("\nADR-y ZYJA razem z kodem (Markdown w repo) - przechodza ten sam code review co kod (Lesson22, _16_clean_code).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ADR (Nygard) = lekki zapis WAZNEJ decyzji architektonicznej:
         *   tytul, status, kontekst, decyzja, konsekwencje.
         * - Pisz ADR TYLKO dla decyzji o realnym znaczeniu (Lesson01:
         *   trudnych do odwrocenia) - nie dla kazdego drobiazgu.
         * - ADR-y sa NIEZMIENNE - zmiana decyzji to NOWY ADR zastepujacy
         *   stary (superseding), nie edycja historii.
         * - ADR-y zyja razem z kodem w repozytorium, nie w osobnym
         *   dokumencie.
         * - W kolejnej lekcji (Lesson03): warstwowa architektura -
         *   pierwszy KONKRETNY wzorzec organizacji calej aplikacji, ktory
         *   mozna udokumentowac wlasnym ADR-em.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateAdrStructure() {
        System.out.println("\n=== STRUKTURA POJEDYNCZEGO ADR ===");

        Adr adr = new Adr(
                1,
                "Uzyj PostgreSQL zamiast MongoDB do przechowywania zamowien",
                AdrStatus.ACCEPTED,
                "Zespol rozwaza 2 bazy danych do nowego modulu zamowien: PostgreSQL (relacyjna, "
                        + "zespol ma juz doswiadczenie z `_08_sql`/`_09_jdbc`/`_10_dao`) i MongoDB "
                        + "(dokumentowa, zerowe doswiadczenie w zespole). Zamowienia maja SILNE "
                        + "relacje (zamowienie-pozycje-platnosc) wymagajace transakcji (`_08_sql/Lesson19`).",
                "Uzywamy PostgreSQL. Relacyjny model dobrze pasuje do silnie powiazanych danych "
                        + "zamowien, a zespol juz zna JDBC/transakcje z wczesniejszych modulow.",
                List.of(
                        "Zyskujemy: dojrzale wsparcie transakcji, znane zespolowi narzedzia (JDBC/DAO).",
                        "Kosztujemy: schemat wymaga migracji przy kazdej zmianie struktury danych (por. `_10_dao/Lesson25`)."
                ),
                LocalDate.of(2024, 3, 1)
        );

        printAdr(adr);
    }

    private static void printAdr(Adr adr) {
        System.out.println("ADR-" + String.format("%04d", adr.number()) + ": " + adr.title());
        System.out.println("  Status: " + adr.status());
        System.out.println("  Data: " + adr.date());
        System.out.println("  Kontekst: " + adr.context());
        System.out.println("  Decyzja: " + adr.decision());
        System.out.println("  Konsekwencje:");
        adr.consequences().forEach(c -> System.out.println("    - " + c));
    }

    enum AdrStatus {
        PROPOSED, ACCEPTED, REJECTED, SUPERSEDED
    }

    /** Niezmienny zapis 1 decyzji architektonicznej - zgodny z formatem Nygarda. */
    record Adr(int number, String title, AdrStatus status, String context, String decision,
               List<String> consequences, LocalDate date) {
    }

    private static void demonstrateSupersedingAnAdr() {
        System.out.println("\n=== ZASTĘPOWANIE (SUPERSEDING) STAREGO ADR NOWYM ===");

        AdrLog log = new AdrLog();

        Adr original = new Adr(
                1, "Uzyj PostgreSQL do przechowywania zamowien", AdrStatus.ACCEPTED,
                "Potrzebujemy relacyjnej bazy z transakcjami.",
                "Wybieramy PostgreSQL.",
                List.of("Dojrzale transakcje, znane narzedzia."),
                LocalDate.of(2024, 3, 1)
        );
        log.record(original);

        // Rok pozniej: skala ruchu wymusza zmiane - piszemy NOWY ADR,
        // NIE edytujemy oryginalu.
        Adr replacement = new Adr(
                2, "Migracja przechowywania zamowien z PostgreSQL na rozproszona baze kolumnowa",
                AdrStatus.ACCEPTED,
                "Wolumen zamowien przekroczyl skale, dla ktorej pojedynczy PostgreSQL byl projektowany "
                        + "(ADR-0001) - potrzebujemy poziomego skalowania zapisu.",
                "Migrujemy na rozproszona baze kolumnowa, zachowujac PostgreSQL dla mniejszych modulow.",
                List.of("Zyskujemy: skalowalnosc zapisu.", "Kosztujemy: utrata czesci gwarancji transakcyjnych ADR-0001."),
                LocalDate.of(2025, 6, 1)
        );
        log.record(replacement);
        log.markSuperseded(original.number(), replacement.number());

        System.out.println("Historia ADR PO zastapieniu (WSZYSTKIE wpisy nadal widoczne, ZADEN nie zniknal):");
        log.printAll();
    }

    /** Prosty, przypisujacy status rejestr ADR - modeluje regule 'ADR sa niezmienne, tylko status sie zmienia'. */
    static class AdrLog {
        private final List<Adr> entries = new ArrayList<>();

        void record(Adr adr) {
            entries.add(adr);
        }

        void markSuperseded(int oldNumber, int newNumber) {
            Optional<Adr> oldAdr = entries.stream().filter(a -> a.number() == oldNumber).findFirst();
            oldAdr.ifPresent(old -> {
                entries.remove(old);
                entries.add(new Adr(old.number(), old.title(), AdrStatus.SUPERSEDED,
                        old.context(), old.decision() + " [ZASTAPIONA przez ADR-" + String.format("%04d", newNumber) + "]",
                        old.consequences(), old.date()));
            });
        }

        void printAll() {
            entries.stream()
                    .sorted((a, b) -> Integer.compare(a.number(), b.number()))
                    .forEach(a -> System.out.println("  ADR-" + String.format("%04d", a.number())
                            + " [" + a.status() + "] " + a.title()));
        }
    }
}
