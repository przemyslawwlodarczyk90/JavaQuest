package com.example.javaquest._17_architecture.Lesson17_ModularMonolith;

public class _Exercises_Lesson17_ModularMonolith {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainModularMonolithInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), czym jest modularny monolit i dlaczego to "najlepsze z
         * obu swiatow".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DesignModuleWithPublicApiAndInternals {
        /*
         * 🧪 Zadanie 2:
         * Zaprojektuj modul "Users" z publiczna klasa `UsersModuleApi` (1-2
         * metody) i wewnetrznym repozytorium (oznaczonym komentarzem "w
         * realnym projekcie: package-private").
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DesignSecondModuleUsingFirstModuleApi {
        /*
         * 🧪 Zadanie 3:
         * Zaprojektuj modul "Notifications" korzystajacy z `UsersModuleApi`
         * z Zadania 2 (np. do pobrania e-maila uzytkownika) - WYLACZNIE
         * przez publiczne API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IdentifyDataOwnershipViolation {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: opisz scenariusz (jak w teorii), w ktorym modul
         * "Raportowanie" czyta BEZPOSREDNIO tabele modulu "Zamowienia" -
         * wyjasnij, dlaczego to narusza modularnosc mimo osobnych pakietow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixDataOwnershipViolationFromExercise04 {
        /*
         * 🧪 Zadanie 5:
         * Zaproponuj (opisowo + prostym kodem) poprawke z Zadania 4 -
         * Raportowanie korzysta z publicznego API modulu Zamowienia zamiast
         * bezposredniego dostepu do tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyCommonModuleBecomingDumpingGround {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: opisz (min. 3 zdania) sytuacje, w ktorej modul
         * `common` stopniowo staje sie "smietnikiem" - jakie klasy tam
         * trafiaja, ktore NIE powinny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DesignProperlyScopedCommonModule {
        /*
         * 🧪 Zadanie 7:
         * Zaprojektuj WLASCIWY modul `common` zawierajacy TYLKO 1-2 proste
         * typy wartosciowe (np. `Money`, `_16_clean_code/Lesson19`) - w
         * komentarzu uzasadnij, dlaczego TYLKO to nalezy do `common`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ConnectModularMonolithToBoundedContextFromLesson06 {
        /*
         * 🧪 Zadanie 8:
         * Wez bounded context zaprojektowane w `_17_architecture/Lesson06` -
         * zaproponuj, jak KAZDY z nich stalby sie osobnym modulem w
         * modularnym monolicie (jako drzewo tekstowe pakietow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainMigrationPathToMicroservicesFromModularMonolith {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wyjasnij (min. 3 zdania), dlaczego modularny
         * monolit z wyraznymi granicami latwiej wydzielic na mikroserwisy
         * niz Big Ball of Mud (Lesson01).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListModularMonolithPrinciplesFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 4
         * zasady modularnego monolitu z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignFullThreeModuleECommerceSystem {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj modularny monolit z 3 modulami (Katalog, Zamowienia,
         * Platnosci) - KAZDY z publicznym API i wewnetrznymi szczegolami.
         * Zademonstruj wspoldzialanie WSZYSTKICH 3 dla 1 przypadku uzycia
         * (checkout).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddNewModuleWithoutModifyingExistingOnes {
        /*
         * 🧪 Zadanie 12:
         * Dla systemu z Zadania 11, dodaj CZWARTY modul (np. "Lojalnosc") -
         * zademonstruj, ze integruje sie z istniejacymi modulami TYLKO
         * przez ich publiczne API, bez modyfikacji ich wewnetrznych klas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignAdrForAdoptingModularMonolithOverMicroservices {
        /*
         * 🧪 Zadanie 13:
         * Napisz PELNY ADR (`Lesson02`) uzasadniajacy wybor modularnego
         * monolitu zamiast mikroserwisow dla nowego projektu - z kontekstem
         * (np. rozmiar zespolu, brak doswiadczenia w systemach rozproszonych)
         * i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SimulateDataOwnershipViolationAndDetection {
        /*
         * 🧪 Zadanie 14:
         * Zasymuluj (kodem + komentarzem) sytuacje, w ktorej modul
         * "Platnosci" PRZYPADKOWO uzyskuje bezposredni dostep do "tabeli"
         * (symulowanej Map) modulu "Zamowienia" - zaproponuj, jak wykryc
         * takie naruszenie (np. przez przeglad kodu, `_16_clean_code/
         * Lesson22`, lub automatyczne narzedzia typu ArchUnit).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignModulePublicApiUsingPortsAndAdaptersTerminology {
        /*
         * 🧪 Zadanie 15:
         * Polacz modularny monolit z terminologia portow (Lesson11-12) -
         * zaprojektuj modul, ktorego publiczne API to WLASNIE jego port
         * driving, uzywany przez INNE moduly jako "klienci".
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorTightlyCoupledModulesIntoProperBoundaries {
        /*
         * 🧪 Zadanie 16:
         * Napisz 2 "moduly" z BEZPOSREDNIMI odwolaniami do swoich
         * wzajemnych, wewnetrznych klas (brak publicznego API) -
         * zrefaktoryzuj, wprowadzajac WLASCIWE publiczne API dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignModuleOwnedDatabaseSchemaSimulation {
        /*
         * 🧪 Zadanie 17:
         * Zasymuluj (osobnymi `Map`-ami jako "schematami") 3 moduly, KAZDY
         * z WLASNYMI "tabelami" - zademonstruj, ze KAZDY modul odwoluje sie
         * TYLKO do wlasnej mapy, nigdy do cudzej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareModularMonolithWithPackageByLayerFromLesson09 {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego
         * package-by-layer (`_17_architecture/Lesson09`) NIE NADAJE SIE jako
         * fundament pod modularny monolit, a package-by-feature TAK.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignModuleCommunicationThroughPublicApiOnly {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj 3 moduly w LANCUCHU zaleznosci (A wola B, B wola C) -
         * zademonstruj, ze KAZDY wywoluje TYLKO publiczne API bezposredniego
         * sasiada, nigdy nie "przeskakuje" (por. Prawo Demeter,
         * `_16_clean_code/Lesson12`, zastosowane do modulow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCourseChapterForModularBoundaries {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) strukture calego kursu (rozdzialy
         * `_01` do `_16`) - czy KAZDY rozdzial to w praktyce "modul" z
         * wlasnym, samodzielnym zakresem? Czy sa miejsca, gdzie 1 rozdzial
         * "siega" do wewnetrznych szczegolow innego?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullFiveModuleModularMonolithForSaasPlatform {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny modularny monolit z 5 modulami dla platformy
         * SaaS (np. Konta, Subskrypcje, Produkt Glowny, Analityka,
         * Powiadomienia) - KAZDY z publicznym API, prywatnymi
         * wewnetrznymi szczegolami, i WLASNYMI "danymi".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCrossModuleUseCaseRespectingAllBoundaries {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21, zaimplementuj przypadek uzycia
         * angazujacy WSZYSTKIE 5 modulow (np. "nowa subskrypcja") -
         * zademonstruj, ze KAZDA interakcja miedzy modulami przechodzi
         * WYLACZNIE przez publiczne API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignArchitectureTestPreventingInternalAccessViolation {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (koncepcyjnie, jako opis + prosty kod symulujacy
         * sprawdzenie) "test architektoniczny" weryfikujacy, ze ZADNA klasa
         * spoza modulu X nie odwoluje sie do klas oznaczonych jako
         * "wewnetrzne" tego modulu - podobne do narzedzi typu ArchUnit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulateExtractingOneModuleIntoStandaloneServicePreview {
        /*
         * 🧪 Zadanie 24:
         * Dla systemu z Zadania 21, wybierz 1 modul (np. Powiadomienia) i w
         * komentarzu opisz KROK PO KROKU, jak wygladalby proces wydzielenia
         * go do osobnego mikroserwisu (Lesson19) - dzieki temu, ze MA JUZ
         * wyrazne publiczne API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RefactorRealisticEntangledSystemIntoModularMonolith {
        /*
         * 🧪 Zadanie 25:
         * Napisz REALISTYCZNY system z 3 "modulami" majacymi WZAJEMNY,
         * bezposredni dostep do swoich wewnetrznych klas I danych (Big Ball
         * of Mud w miniaturze) - w PELNI zrefaktoryzuj na modularny monolit,
         * krok po kroku (`_16_clean_code/Lesson15`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignModulePublicApiVersioningStrategy {
        /*
         * 🧪 Zadanie 26:
         * Polacz modularny monolit z wersjonowaniem API (Lesson08) -
         * zaprojektuj sytuacje, w ktorej publiczne API 1 modulu MUSI sie
         * zmienic w sposob lamiacy - zaproponuj strategie dla WEWNETRZNYCH
         * klientow (innych modulow), analogiczna do strategii dla
         * zewnetrznych klientow API.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAdrForEnforcingModuleBoundariesWithTooling {
        /*
         * 🧪 Zadanie 27:
         * Napisz PELNY ADR uzasadniajacy WPROWADZENIE automatycznego
         * narzedzia (np. koncepcyjnie ArchUnit) do wymuszania granic
         * modulow w CI - z kontekstem (np. "znalezlismy 3 naruszenia granic
         * podczas przegladu kodu w ostatnim miesiacu") i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareModularMonolithGovernanceAcrossGrowingTeamSizes {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: opisz (min. 4 zdania), jak zarzadzanie granicami
         * modulow w modularnym monolicie zmienia sie, gdy zespol rosnie z 3
         * do 30 osob - jakie DODATKOWE mechanizmy (automatyczne testy
         * architektoniczne, code owners per modul) staja sie potrzebne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveModularMonolithDesignChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 7
         * punktow) do projektowania modularnego monolitu - laczac WSZYSTKIE
         * zasady z tej lekcji i powiazanych (Lesson06/09/11-12).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullModularMonolithForHealthcarePlatform {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny
         * modularny monolit dla platformy medycznej z min. 4 modulami
         * (np. Pacjenci, Wizyty, Fakturowanie, Powiadomienia) - KAZDY z
         * jawnym publicznym API, prywatnymi wewnetrznymi szczegolami,
         * WLASNYMI "danymi" (symulowanymi osobnymi mapami), oraz min. 1
         * modulem `common` zawierajacym WYLACZNIE proste typy wartosciowe.
         * Zademonstruj min. 1 przypadek uzycia angazujacy WSZYSTKIE moduly,
         * z ZERO naruszen granic.
         */
        public static void main(String[] args) { }
    }
}
