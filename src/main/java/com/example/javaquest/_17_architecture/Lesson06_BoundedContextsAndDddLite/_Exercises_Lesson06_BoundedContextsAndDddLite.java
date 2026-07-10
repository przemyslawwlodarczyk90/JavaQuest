package com.example.javaquest._17_architecture.Lesson06_BoundedContextsAndDddLite;

public class _Exercises_Lesson06_BoundedContextsAndDddLite {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainBoundedContextInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), czym jest bounded context i dlaczego ten sam termin moze
         * znaczyc co innego w roznych kontekstach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DesignTwoDifferentUserModelsForTwoContexts {
        /*
         * 🧪 Zadanie 2:
         * Zaprojektuj 2 rozne klasy `User` - jedna dla kontekstu
         * "Uwierzytelnianie" (login, hashHasla, ostatnie logowanie), druga dla
         * kontekstu "Marketing" (imie, preferencje newslettera, segment).
         * Stworz po 1 instancji kazdej i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IdentifyGodModelAttemptCombiningBothContexts {
        /*
         * 🧪 Zadanie 3:
         * Napisz 1 "uniwersalna" klase `UserEverything` laczaca WSZYSTKIE
         * pola z Zadania 2 w 1 klasie. W komentarzu wyjasnij, dlaczego to
         * jest "God Class" na poziomie modelu domenowego (`_16_clean_code/
         * Lesson06`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DesignTranslationBetweenTwoContextModels {
        /*
         * 🧪 Zadanie 4:
         * Napisz metode `MarketingView translateToMarketingView(AuthUser
         * user)` tlumaczaca z modelu kontekstu Uwierzytelniania na WLASNY
         * ksztalt kontekstu Marketingu (bez dzielenia klasy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainUbiquitousLanguageWithOwnExample {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: opisz WLASNY przyklad (inny niz z teorii) sytuacji, w
         * ktorej nazwa metody/klasy powinna uzywac DOKLADNIE slowa, ktorym
         * poslugiwalby sie ekspert dziedzinowy (jezyk wszechobecny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DesignTwoBookModelsForLibraryAndBookstoreContexts {
        /*
         * 🧪 Zadanie 6:
         * Zaprojektuj 2 rozne klasy `Book` - jedna dla kontekstu Biblioteki
         * (numer inwentarzowy, status wypozyczenia), druga dla kontekstu
         * Ksiegarni (cena, stan magazynowy do sprzedazy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyContextBoundaryInGivenSystemDescription {
        /*
         * 🧪 Zadanie 7:
         * Dany jest opis systemu szpitalnego z modulami: Rejestracja
         * Pacjentow, Fakturowanie, Historia Medyczna. W komentarzu zaproponuj,
         * GDZIE przebiegalyby naturalne granice bounded context.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DesignLocalShapeForCrossContextData {
        /*
         * 🧪 Zadanie 8:
         * Zaprojektuj `record PatientBillingView(String patientId, double
         * outstandingBalance)` jako LOKALNY ksztalt danych o pacjencie
         * uzywany WYLACZNIE przez kontekst Fakturowania (bez dzielenia klasy z
         * kontekstem Rejestracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareSharedModelVsTranslatedModelForMaintenance {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu porownaj (min. 3 zdania), co sie
         * stanie, gdy kontekst Rejestracji zmieni swoj model `Patient`, w
         * przypadku (a) WSPOLDZIELENIA klasy z Fakturowaniem, (b) tlumaczenia
         * na lokalny ksztalt (Zadanie 8).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListBoundedContextSignalsFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * sygnaly sugerujace, ze potrzebujesz osobnego bounded context.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignThreeContextsForRideSharingApp {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj 3 rozne modele "Kierowca" dla aplikacji przewozu osob:
         * kontekst Dopasowywania (lokalizacja, dostepnosc), kontekst Platnosci
         * (dane rozliczeniowe), kontekst Ocen (srednia ocena, liczba przejazdow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DesignContextMapTranslatingDriverAcrossAllThreeContexts {
        /*
         * 🧪 Zadanie 12:
         * Napisz metody tlumaczace miedzy WSZYSTKIMI 3 modelami z Zadania 11
         * (np. z modelu Dopasowywania na widok potrzebny kontekstowi Platnosci)
         * - zademonstruj pelny lancuch tlumaczen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_IdentifyAntiCorruptionLayerNeedForLegacySystem {
        /*
         * 🧪 Zadanie 13:
         * Dany jest opis: nowy kontekst "Zamowienia" musi integrowac sie ze
         * "starym" systemem magazynowym o innym, niezgrabnym modelu danych.
         * Zaprojektuj klase "warstwy antykorupcyjnej" (anti-corruption layer)
         * tlumaczaca stary model NA czysty model nowego kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DemonstrateAntiCorruptionLayerIsolatingLegacyQuirks {
        /*
         * 🧪 Zadanie 14:
         * Uzywajac rozwiazania z Zadania 13, zademonstruj, ze "dziwactwo"
         * starego systemu (np. status jako liczba: 0/1/2) jest WIDOCZNE TYLKO
         * wewnatrz warstwy antykorupcyjnej - reszta nowego kontekstu widzi
         * czytelny enum/String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignBoundedContextsAlignedWithTeamOwnership {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: opisz (min. 4 zdania) hipotetyczny podzial 3
         * zespolow (np. Zespol Platnosci, Zespol Katalogu, Zespol Logistyki) i
         * ZAPROPONUJ, jak granice ich bounded context powinny pokrywac sie z
         * granicami zespolow (wprowadzenie do prawa Conwaya, Lesson19).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorGodModelIntoMultipleContextSpecificModels {
        /*
         * 🧪 Zadanie 16:
         * Napisz "God Model" `EmployeeEverything` laczacy dane HR, dane
         * platnicze i dane dostepu do systemow IT (min. 8 pol razem).
         * Zrefaktoryzuj na 3 osobne modele kontekstowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignSharedKernelForCommonValueObject {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj WSPOLNY typ wartosciowy `Money` (record, `_16_clean_code/
         * Lesson19`) uzywany PRZEZ 2 rozne konteksty (Platnosci i
         * Fakturowanie) - w komentarzu wyjasnij, dlaczego TYLKO PROSTE,
         * stabilne typy wartosciowe (nie encje z zachowaniem specyficznym dla
         * kontekstu) nadaja sie na "wspolny rdzen" (shared kernel).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignAdrForChoosingContextBoundaries {
        /*
         * 🧪 Zadanie 18:
         * Napisz PELNY ADR (`Lesson02`) dla decyzji "gdzie przebiegaja granice
         * bounded context w nowym systemie rezerwacji podrozy" (np. Loty vs
         * Hotele vs Platnosci jako osobne konteksty) - z realistycznym
         * kontekstem i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareUbiquitousLanguageConsistencyWithinContext {
        /*
         * 🧪 Zadanie 19:
         * Napisz 2 wersje TEJ SAMEJ metody w kontekscie Magazynu: (a) nazwana
         * jezykiem TECHNICZNYM ("updateRow"), (b) nazwana jezykiem BIZNESU
         * magazynu ("replenishStock"). W komentarzu wyjasnij, ktora lepiej
         * sluzy jezykowi wszechobecnemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealCourseChapterForImplicitBoundedContexts {
        /*
         * 🧪 Zadanie 20:
         * W komentarzu przeanalizuj rozdzialy `_08_sql`/`_09_jdbc`/`_10_dao` -
         * czy "Uzytkownik"/"Zamowienie" z roznych lekcji tych rozdzialow maja
         * SPOJNY model, czy kazda lekcja definiuje WLASNY, lokalny ksztalt
         * (co byloby analogiczne do oddzielnych bounded context)?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignRealisticFourContextECommercePlatform {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletny system e-commerce z 4 bounded context
         * (Katalog, Magazyn, Zamowienia, Platnosci) - dla KAZDEGO zdefiniuj
         * WLASNY model "Produktu"/"Zamowienia" (tam gdzie relevant) z polami
         * WLASCIWYMI tylko temu kontekstowi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildFullContextMapWithTranslationsForCheckoutFlow {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21, zaimplementuj PELNY przeplyw "checkout"
         * przechodzacy przez WSZYSTKIE 4 konteksty, z jawnym TLUMACZENIEM
         * danych na KAZDEJ granicy miedzy kontekstami (min. 3 tlumaczenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignAntiCorruptionLayerForThirdPartyPaymentProvider {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj warstwe antykorupcyjna tlumaczaca niezgrabny model
         * "zewnetrznego dostawcy platnosci" (symulowany, z polami o dziwnych
         * nazwach/typach) na czysty model WEWNETRZNEGO kontekstu Platnosci z
         * Zadania 21.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_IdentifyAndFixLeakingModelAcrossContextBoundary {
        /*
         * 🧪 Zadanie 24:
         * Napisz kod, w ktorym kontekst Zamowien BEZPOSREDNIO uzywa klasy
         * `Product` z kontekstu Katalogu (bez tlumaczenia) - zademonstruj
         * problem (np. zmiana pola w Katalogu psuje kompilacje w Zamowieniach).
         * Napraw przez wprowadzenie tlumaczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignSharedKernelVsSeparateModelsTradeoffAnalysis {
        /*
         * 🧪 Zadanie 25:
         * Dla systemu z Zadania 21, zaimplementuj typ `Money` jako (a)
         * wspolny rdzen (shared kernel) uzywany przez wszystkie 4 konteksty,
         * (b) alternatywnie - osobne typy dla kazdego kontekstu. W
         * komentarzu uzasadnij, ktora opcja jest lepsza DLA TEGO KONKRETNEGO
         * typu (odpowiedz: shared kernel dla prostych, stabilnych VO jest OK).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildContextConsistentEventNamingAcrossModules {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj zdarzenia domenowe (proste rekordy) nazwane jezykiem
         * WLASCIWYM kazdemu kontekstowi z Zadania 21 (np. `ProductListed`
         * w Katalogu, `StockReserved` w Magazynie, `PaymentCaptured` w
         * Platnosciach) - to wprowadzenie do Lesson18.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignContextBoundariesForMultiTenantSaasSystem {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj bounded context dla systemu SaaS z modulami:
         * Zarzadzanie Kontem (subskrypcje, platnosci), Produkt Glowny
         * (funkcjonalnosc dla uzytkownika koncowego), Analityka (raporty
         * uzycia) - dla kazdego zaproponuj WLASNY model "Uzytkownika".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAdrDocumentingContextMergeDecision {
        /*
         * 🧪 Zadanie 28:
         * Napisz ADR uzasadniajacy ODWROTNA decyzje - POLACZENIE 2
         * wczesniej osobnych bounded context w 1 (bo okazalo sie, ze granica
         * byla sztuczna i generowala zbyt duzo tlumaczen bez realnej
         * korzysci) - z realistycznym kontekstem i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveBoundedContextIdentificationChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do IDENTYFIKACJI wlasciwych granic bounded context w
         * nowym projekcie - laczac WSZYSTKIE zasady z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneMultiContextSystemWithFullTranslationLayer {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletny,
         * realistyczny system (np. system zarzadzania wydarzeniami: kontekst
         * Sprzedazy Biletow, kontekst Logistyki Sali, kontekst Marketingu)
         * z MIN. 3 bounded context, KAZDY z WLASNYM modelem "Wydarzenia"/
         * "Uczestnika", PELNYM context mapem (tlumaczenia na KAZDEJ granicy),
         * oraz przynajmniej 1 warstwa antykorupcyjna dla symulowanego
         * zewnetrznego systemu. Zademonstruj pelny przeplyw miedzy WSZYSTKIMI
         * kontekstami.
         */
        public static void main(String[] args) { }
    }
}
