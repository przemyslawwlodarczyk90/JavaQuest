package com.example.javaquest._17_architecture.Lesson02_ArchitectureDecisionRecords;

public class _Exercises_Lesson02_ArchitectureDecisionRecords {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainAdrPurposeInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), po co istnieje ADR i jaki problem rozwiazuje (utrata
         * "dlaczego" w czasie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DefineAdrRecordWithFourSections {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj record `Adr(int number, String title, String context,
         * String decision, String consequences)` - stworz 1 instancje
         * reprezentujaca dowolna wymyslona decyzje i wypisz wszystkie pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteAdrForChoosingJsonLibrary {
        /*
         * 🧪 Zadanie 3:
         * Napisz PELNY ADR (uzywajac recordu z Zadania 2) dla decyzji "Gson
         * czy Jackson do serializacji JSON" (patrz `_04_io/Lesson19-21`) -
         * wypelnij WSZYSTKIE 4 sekcje realistyczna trescia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ClassifyThreeDecisionsAsAdrWorthyOrNot {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: dla 3 decyzji - (a) "uzyjemy PostgreSQL", (b) "ta
         * zmienna nazywa sie `total`", (c) "komunikacja miedzy modulami przez
         * zdarzenia" - oceń, KTORE zasluguja na ADR, z uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddStatusFieldToAdrRecord {
        /*
         * 🧪 Zadanie 5:
         * Rozszerz record z Zadania 2 o enum `AdrStatus` (PROPOSED/ACCEPTED/
         * REJECTED/SUPERSEDED) - stworz 2 ADR-y o roznym statusie i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BuildSimpleInMemoryAdrLog {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase `AdrLog` z metoda `record(Adr adr)` i `printAll()` -
         * dodaj 3 rozne ADR-y i wypisz je wszystkie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyAdrsShouldNotBeEdited {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * istniejacego ADR NIE powinno sie edytowac, gdy decyzja sie zmienia -
         * dlaczego zamiast tego pisze sie NOWY ADR.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteAdrThatSupersedesAnEarlierOne {
        /*
         * 🧪 Zadanie 8:
         * Napisz 2 ADR-y: pierwszy "Uzywamy XML do konfiguracji", drugi
         * "Migrujemy z XML na YAML" (jawnie odwolujacy sie do numeru
         * pierwszego jako zastepowanego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MarkOriginalAdrAsSuperseded {
        /*
         * 🧪 Zadanie 9:
         * Korzystajac z AdrLog z Zadania 6, dodaj metode
         * `markSuperseded(int oldNumber, int newNumber)` - uzyj jej dla ADR-ow
         * z Zadania 8 i wypisz PELNA historie (oba ADR-y nadal widoczne, stary
         * ze zmienionym statusem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAdrBestPracticesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * dobre praktyki pisania ADR-ow z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteAdrForChoosingBetweenTwoArchitecturalStyles {
        /*
         * 🧪 Zadanie 11:
         * Napisz PELNY ADR dla decyzji "architektura warstwowa czy
         * heksagonalna dla nowego modulu platnosci" - z realistycznym
         * kontekstem (np. "potrzebujemy latwej podmiany bramki platnosci w
         * testach") i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildAdrLogFilterableByStatus {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz `AdrLog` o metode `List<Adr> findByStatus(AdrStatus
         * status)` - dodaj min. 5 ADR-ow o roznych statusach i wypisz TYLKO
         * te ze statusem ACCEPTED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignAdrNumberingSchemeAndValidateUniqueness {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz `AdrLog` o metode `boolean isNumberUnique(int number)`
         * sprawdzajaca, czy dany numer ADR nie jest juz zajety - zademonstruj
         * probe dodania ADR z DUPLIKATEM numeru i obsluge tego przypadku
         * (np. rzucenie wyjatku, por. `_16_clean_code/Lesson17`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteChainOfThreeSupersedingAdrs {
        /*
         * 🧪 Zadanie 14:
         * Napisz LANCUCH 3 ADR-ow, gdzie KAZDY kolejny zastepuje poprzedni
         * (np. "uzyj REST" -> "migruj na GraphQL" -> "migruj na gRPC") -
         * wypisz pelna historie z poprawnymi statusami dla wszystkich trzech.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignAdrTemplateAsReusableBuilder {
        /*
         * 🧪 Zadanie 15:
         * Zaprojektuj `AdrBuilder` (fluent, por. `_16_clean_code/Lesson19`:
         * Builder) z metodami `withTitle`/`withContext`/`withDecision`/
         * `withConsequence` (wielokrotnie wolane) i `build()`. Zbuduj 1 ADR
         * przez builder.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareDecisionDocumentedVsUndocumentedOverTime {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: napisz w komentarzu krotka "historyjke" (min. 4
         * zdania) o zespole, ktory PODJAL decyzje BEZ ADR, a rok pozniej nowy
         * czlonek zespolu chce ja zmienic - opisz, co pojdzie zle bez zapisu
         * kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildAdrLogWithChronologicalOrdering {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz `AdrLog` o metode `printAllChronologically()` sortujaca
         * ADR-y wg daty (nie numeru) - dodaj min. 4 ADR-y z roznymi datami w
         * losowej kolejnosci dodawania i wypisz je posortowane chronologicznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteAdrDocumentingRejectedAlternative {
        /*
         * 🧪 Zadanie 18:
         * Napisz ADR ze statusem REJECTED dla alternatywy, ktora zespol
         * ROZWAZAL, ale odrzucil (np. "rozwazalismy mikroserwisy, odrzucone -
         * zbyt maly zespol") - w komentarzu wyjasnij, dlaczego dokumentowanie
         * ODRZUCONYCH opcji tez ma wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignAdrRelevantToRealCourseChapter {
        /*
         * 🧪 Zadanie 19:
         * Napisz ADR uzasadniajacy JEDNA z RZECZYWISTYCH decyzji z tego kursu
         * (np. "dlaczego `_11_buildtools` embeduje prawdziwego Anta zamiast
         * tylko opisywac XML" - patrz CLAUDE.md rozdzialu) - z realistycznym
         * kontekstem/decyzja/konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditGivenAdrForMissingSections {
        /*
         * 🧪 Zadanie 20:
         * Dany jest "niekompletny" ADR z PUSTA sekcja konsekwencji. W
         * komentarzu wyjasnij, jakiego ryzyka to ADR NIE opisuje, i dopisz
         * brakujaca sekcje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullAdrRepositorySimulationWithValidation {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny `AdrRepository` (rozszerzenie AdrLog): unikalne
         * numery (walidacja fail-fast, `_16_clean_code/Lesson17`), metoda
         * `supersede(int oldNumber, Adr newAdr)` ktora ATOMOWO dodaje nowy ADR
         * I oznacza stary jako zastapiony, oraz `printFullHistory()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateYearLongProjectWithMultipleAdrChains {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj "roczny projekt" z min. 6 ADR-ami, z ktorych 2 tworza
         * lancuchy zastepowania (jak w Zadaniu 14) - wypisz PELNA, chronologiczna
         * historie z wyraznym oznaczeniem, ktore ADR-y sa nadal AKTYWNE
         * (ACCEPTED), a ktore zastapione.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignAdrIndexGroupedByArchitecturalArea {
        /*
         * 🧪 Zadanie 23:
         * Dodaj do `Adr` pole `String area` (np. "baza danych", "komunikacja",
         * "bezpieczenstwo") - napisz metode grupujaca ADR-y wg tego pola
         * (`Map<String, List<Adr>>`) i wypisz podsumowanie per obszar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildAdrConsistencyCheckerForDanglingReferences {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode `List<String> findDanglingSupersedes(AdrLog log)`
         * sprawdzajaca, czy KAZDY ADR oznaczony jako "zastapiony przez ADR-000X"
         * ma odpowiadajacy, ISTNIEJACY ADR o numerze X w logu - zademonstruj
         * wykrycie 1 "wiszacego" odwolania (do nieistniejacego ADR).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareAdrDrivenDecisionWithUndocumentedAlternativeHistory {
        /*
         * 🧪 Zadanie 25:
         * Napisz 2 "rownolegle historie" tego samego projektu (jako 2 osobne
         * `List<String>` z opisem wydarzen) - jedna GDZIE kazda decyzja miala
         * ADR, druga BEZ ADR-ow - w komentarzu (na bazie obu historii) opisz
         * KONKRETNY moment, w ktorym brak ADR spowodowal realny problem
         * (powtorzona debata, przypadkowe cofniecie decyzji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignAdrReviewWorkflowWithProposedStatus {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj przeplyw: ADR tworzony ze statusem PROPOSED, nastepnie
         * (po "code review", `_16_clean_code/Lesson22`) zmieniany na ACCEPTED
         * lub REJECTED - zaimplementuj metody `propose(Adr)`, `accept(int
         * number)`, `reject(int number)` w `AdrLog` i zademonstruj pelny cykl.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildMarkdownStyleRendererForAdr {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode `String renderAsMarkdown(Adr adr)` generujaca String w
         * formacie zblizonym do prawdziwego pliku Markdown ADR (nagłówki `#`/
         * `##`, sekcje) - wypisz wynik dla 1 przykladowego ADR.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAdrForDecisionMadeElsewhereInThisCourse {
        /*
         * 🧪 Zadanie 28:
         * Wybierz 1 REALNA decyzje architektoniczna udokumentowana w
         * `CLAUDE.md` tego projektu (np. wybor embedowania Ant/PMD/SpotBugs
         * zamiast tylko opisu) i przepisz ja jako PELNY, sformalizowany ADR
         * (wszystkie sekcje + status).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveAdrAuthoringChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do pisania DOBRYCH ADR-ow - laczac WSZYSTKIE zasady z tej
         * lekcji (kiedy pisac, format, niezmiennosc, superseding,
         * przechowywanie razem z kodem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullAdrLifecycleForRealisticProject {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zasymuluj PELNY cykl zycia architektury
         * malego, wymyslonego projektu (min. 8 ADR-ow) laczac WSZYSTKIE
         * elementy z tej lekcji: statusy (proposed/accepted/rejected/
         * superseded), min. 2 lancuchy zastepowania, grupowanie wg obszaru
         * (Zadanie 23), walidacje unikalnosci numerow, oraz wyrenderowanie
         * PELNEGO indeksu wszystkich decyzji w formacie zblizonym do
         * Markdown.
         */
        public static void main(String[] args) { }
    }
}
