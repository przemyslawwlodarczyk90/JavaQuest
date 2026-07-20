package com.example.javaquest._28_java_evolution.Lesson24_JavaEvolutionCapstone;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson24_JavaEvolutionCapstone {

    /*
     * ============================================================
     * 🏁 KAPSZTON ROZDZIALU "_28_java_evolution" - "JavaQuest Task Processor"
     * ============================================================
     * Ta lekcja LACZY funkcje Z CALEJ retrospektywy (Lesson02-22, Java
     * 8 -> 25) W JEDNYM, spojnym, DZIALAJACYM demo - MALY procesor
     * zadan (email/raport/sprzatanie), ktory:
     *
     * - definiuje typy zadan JAKO sealed rekordy (Java 16/17, Lesson14/15),
     * - PRZETWARZA je uzywajac pattern matching switch + record patterns
     *   (Java 21, Lesson18),
     * - wykonuje zadania ROWNOLEGLE NA watkach wirtualnych (Java 21,
     *   Lesson19),
     * - SLEDZI historie WYKONANYCH zadan uzywajac SequencedMap (Java 21,
     *   Lesson20) Z automatyczna EWIKCJA najstarszego wpisu,
     * - generuje KONCOWY raport JAKO text block (Java 15, Lesson13),
     * - uzywa `var` (Java 10, Lesson08) I nowych metod String
     *   (Java 11, Lesson09) PRZY okazji.
     *
     * NIE uzywa funkcji Z Lesson21-22 (Java 22-25) - TE WYMAGAJA
     * `--release 22+`/podprocesu (baseline projektu: 21) - kapszton
     * ROZMYSLNIE zostaje W GRANICACH tego, co dziala BEZPOSREDNIO W
     * `src/main/java` tego projektu (dokladnie JAK reszta kursu).
     */

    sealed interface Zadanie permits ZadanieEmail, ZadanieRaport, ZadanieSprzatania {
        String id();
    }

    record ZadanieEmail(String id, String adresat, String tresc) implements Zadanie {
    }

    record ZadanieRaport(String id, String nazwaRaportu, int liczbaWierszy) implements Zadanie {
    }

    record ZadanieSprzatania(String id, String katalog) implements Zadanie {
    }

    record WynikZadania(String idZadania, String opis, boolean sukces) {
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 24 (KAPSZTON): JavaQuest Task Processor - laczy funkcje Z Java 8 -> 21 ===");

        List<Zadanie> zadania = przygotujZadania();
        System.out.println("Przygotowano " + zadania.size() + " zadan do przetworzenia.");

        Instant start = Instant.now();
        List<WynikZadania> wyniki = przetworzZadaniaNaWatkachWirtualnych(zadania);
        Duration czasCalkowity = Duration.between(start, Instant.now());

        Map<String, WynikZadania> historia = zbudujHistorieZOgraniczeniemRozmiaru(wyniki, 3);

        String raportKoncowy = wygenerujRaportTekstowy(zadania.size(), wyniki, czasCalkowity, historia);
        System.out.println("\n" + raportKoncowy);

        assertThat(wyniki).hasSize(zadania.size());
        assertThat(wyniki).allMatch(WynikZadania::sukces);
        assertThat(historia).hasSize(3);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU "_28_java_evolution"
         * ============================================================
         * Ten kapszton pokazal, ze wspolczesny kod Javy (21) TO
         * NAGROMADZENIE 13 LAT ewolucji: rekordy (Java 16) + sealed
         * (17) + pattern matching (21) DAJA zwiezle, TYPOWO BEZPIECZNE
         * modelowanie danych. Watki wirtualne (21) DAJA PROSTA
         * wspolbieznosc BEZ zlozonosci puli watkow. Sequenced
         * Collections (21) DAJA SPOJNE API kolekcji. Text blocks (15)
         * DAJA CZYTELNE generowanie tekstu. `var` (10) I nowe metody
         * String (11) SKRACAJA boilerplate.
         *
         * Rozdzial `_28_java_evolution` (24 lekcje) DOMYKA caly kurs
         * javaQuest - kursant PRZESZEDL droge OD `_01_fundamentals`
         * (Java 8 jako punkt startowy) DO retrospektywy CALEJ historii
         * jezyka AZ PO najnowsze funkcje (Java 25 + zapowiedz 22-25 W
         * Lesson21-22).
         */
        System.out.println("\n=== KONIEC LEKCJI 24 - KONIEC ROZDZIALU _28_java_evolution ===");
    }

    private static List<Zadanie> przygotujZadania() {
        return List.of(
                new ZadanieEmail("Z1", "  klient@example.com  ", "Witaj w JavaQuest!"),
                new ZadanieRaport("Z2", "raport-sprzedazy", 150),
                new ZadanieSprzatania("Z3", "/tmp/cache"),
                new ZadanieEmail("Z4", "support@example.com", "Zgloszenie przyjete."),
                new ZadanieRaport("Z5", "raport-uzytkownikow", 42)
        );
    }

    private static List<WynikZadania> przetworzZadaniaNaWatkachWirtualnych(List<Zadanie> zadania) throws InterruptedException {
        List<Callable<WynikZadania>> callable = new ArrayList<>();
        for (Zadanie zadanie : zadania) {
            callable.add(() -> przetworzPojedynczeZadanie(zadanie));
        }

        List<WynikZadania> wyniki = new ArrayList<>();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var future : executor.invokeAll(callable)) {
                try {
                    wyniki.add(future.get());
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return wyniki;
    }

    private static WynikZadania przetworzPojedynczeZadanie(Zadanie zadanie) throws InterruptedException {
        Thread.sleep(20); // symulacja krotkiego I/O (wyslanie maila / zapis raportu / operacja na plikach)

        // Pattern matching switch + record patterns (Java 21) - rozbicie NA komponenty W JEDNYM kroku.
        return switch (zadanie) {
            case ZadanieEmail(var id, var adresat, var tresc) -> {
                String czystyAdresat = adresat.strip(); // String.strip() - Java 11, Lesson09
                boolean poprawny = !czystyAdresat.isBlank() && !tresc.isBlank();
                yield new WynikZadania(id, "Email do '" + czystyAdresat + "' (" + tresc.length() + " znakow)", poprawny);
            }
            case ZadanieRaport(var id, var nazwa, var liczbaWierszy) ->
                    new WynikZadania(id, "Raport '" + nazwa + "' (" + liczbaWierszy + " wierszy)", liczbaWierszy > 0);
            case ZadanieSprzatania(var id, var katalog) ->
                    new WynikZadania(id, "Sprzatanie katalogu '" + katalog + "'", true);
        };
    }

    private static Map<String, WynikZadania> zbudujHistorieZOgraniczeniemRozmiaru(List<WynikZadania> wyniki, int maxRozmiar) {
        // SequencedMap (Java 21, Lesson20) - pollFirstEntry() usuwa NAJSTARSZY wpis przy przekroczeniu limitu.
        LinkedHashMap<String, WynikZadania> historia = new LinkedHashMap<>();
        for (WynikZadania wynik : wyniki) {
            historia.put(wynik.idZadania(), wynik);
            if (historia.size() > maxRozmiar) {
                historia.pollFirstEntry();
            }
        }
        return historia;
    }

    private static String wygenerujRaportTekstowy(int liczbaZadan, List<WynikZadania> wyniki, Duration czas, Map<String, WynikZadania> historia) {
        long sukcesy = wyniki.stream().filter(WynikZadania::sukces).count();

        StringBuilder szczegoly = new StringBuilder();
        for (WynikZadania wynik : wyniki) {
            szczegoly.append("  [%s] %s -> %s%n".formatted(
                    wynik.idZadania(), wynik.opis(), wynik.sukces() ? "OK" : "BLAD"));
        }

        // Text block (Java 15, Lesson13) - czytelny szablon raportu.
        return """
                ================================================
                RAPORT KONCOWY - JavaQuest Task Processor
                ================================================
                Przetworzono zadan: %d
                Sukcesy: %d / %d
                Czas calkowity (watki wirtualne): %d ms
                Ostatnie %d zadania w historii (SequencedMap): %s
                ------------------------------------------------
                Szczegoly:
                %s================================================
                """.formatted(
                liczbaZadan, sukcesy, liczbaZadan, czas.toMillis(),
                historia.size(), historia.keySet(),
                szczegoly.toString());
    }
}
