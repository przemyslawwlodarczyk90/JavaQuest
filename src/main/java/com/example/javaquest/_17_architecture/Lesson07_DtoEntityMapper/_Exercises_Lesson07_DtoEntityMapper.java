package com.example.javaquest._17_architecture.Lesson07_DtoEntityMapper;

public class _Exercises_Lesson07_DtoEntityMapper {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyEntityLeakingIsArchitecturalProblem {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: w komentarzu wyjasnij wlasnymi slowami (min. 3
         * zdania), dlaczego zwracanie encji wprost jako odpowiedzi API jest
         * problemem ARCHITEKTONICZNYM, nie tylko kosmetycznym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteEntityWithInternalOnlyField {
        /*
         * 🧪 Zadanie 2:
         * Napisz `record ProductEntity(long id, String name, double price,
         * double internalCostPrice)` - `internalCostPrice` to koszt
         * zakupu, NIGDY nie powinien trafic do klienta. Wypisz przykladowa
         * instancje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DesignDtoHidingInternalField {
        /*
         * 🧪 Zadanie 3:
         * Zaprojektuj `record ProductResponseDto(long id, String name, double
         * price)` (bez `internalCostPrice`) i metode mapujaca ProductEntity ->
         * ProductResponseDto. Zademonstruj, ze DTO nie ujawnia kosztu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SimulateSchemaChangeBreakingDirectEntityExposure {
        /*
         * 🧪 Zadanie 4:
         * Zmien nazwe pola w ProductEntity z Zadania 2 (np. `name` ->
         * `displayName`) - w komentarzu wyjasnij, co by sie stalo z
         * KLIENTAMI API, gdyby zwracali ta encje WPROST (bez DTO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ShowDtoShieldsClientsFromSchemaChange {
        /*
         * 🧪 Zadanie 5:
         * Zaktualizuj mapper z Zadania 3, by dzialal z ZMIENIONA encja z
         * Zadania 4 - w komentarzu potwierdz, ze `ProductResponseDto`
         * (kontrakt API) NIE MUSIAL sie zmienic.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DesignInputDtoSeparateFromEntity {
        /*
         * 🧪 Zadanie 6:
         * Zaprojektuj `record CreateProductRequestDto(String name, double
         * price)` (wejsciowe DTO, bez `id` - nadawanego przez system) i metode
         * budujaca ProductEntity z niego (z wygenerowanym ID).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifySrpViolationOfDirectlySerializedEntity {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: w komentarzu wyjasnij (min. 3 zdania), dlaczego encja
         * z adnotacjami serializacji JSON BEZPOSREDNIO na sobie miałaby "2
         * powody do zmiany" (naruszenie SRP, `_16_clean_code/Lesson07`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DesignDtoCombiningTwoEntities {
        /*
         * 🧪 Zadanie 8:
         * Zaprojektuj 2 encje (`CustomerEntity`, `OrderEntity`) oraz 1 DTO
         * laczacy dane z OBU (np. `OrderReceiptDto`) dla konkretnego widoku
         * "potwierdzenie zamowienia".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DesignDtoExposingOnlySubsetOfEntityFields {
        /*
         * 🧪 Zadanie 9:
         * Napisz encje z 6 polami i DTO ujawniajace TYLKO 2 z nich (dla
         * widoku "lista skrocona") - zademonstruj mapowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListArchitecturalReasonsForDtoBoundaryFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania teorii) min. 3
         * ARCHITEKTONICZNE (nie mechaniczne) powody, dla ktorych warto
         * oddzielic DTO od encji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignMultipleDtoViewsForSameEntity {
        /*
         * 🧪 Zadanie 11:
         * Zaprojektuj 1 encje `EmployeeEntity` (8 pol) i 3 ROZNE DTO: widok
         * "lista pracownikow" (2 pola), widok "profil publiczny" (4 pola),
         * widok "panel HR" (wszystkie 8 pol) - zademonstruj mapowanie do
         * kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureImpactOfEntityChangeAcrossThreeDtoViews {
        /*
         * 🧪 Zadanie 12:
         * Zmien 1 pole w `EmployeeEntity` z Zadania 11 - w komentarzu opisz,
         * KTORE z 3 DTO wymagaja aktualizacji mappera, a ktore NIE (bo nie
         * eksponuja tego pola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignAggregatingDtoFromThreeEntitiesForDashboard {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj DTO `CustomerDashboardDto` laczacy dane z 3 encji
         * (Customer, Orders-count, LoyaltyPoints) w 1 plaski obiekt dla
         * "pulpitu klienta" - zademonstruj budowanie go z 3 zrodel.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareRelaxedInternalMappingVsStrictPublicApiMapping {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj TA SAMA funkcjonalnosc na 2 sposoby: (a) WEWNETRZNY
         * kontroler (tej samej aplikacji) dostaje encje wprost (swiadomy
         * kompromis, Lesson03: luzne warstwowanie), (b) PUBLICZNE API
         * korzysta z DTO. W komentarzu uzasadnij ROZNE podejscie w kazdym
         * przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignAdrJustifyingDtoBoundaryForPublicApi {
        /*
         * 🧪 Zadanie 15:
         * Napisz PELNY ADR (`Lesson02`) uzasadniajacy wprowadzenie DTO na
         * granicy PUBLICZNEGO API nowego systemu - z realistycznym kontekstem
         * (np. "planujemy zewnetrznych partnerow integrujacych sie z naszym
         * API") i konsekwencjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignRichEntityWithDtoNotExposingBehavior {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj BOGATA encje (`_17_architecture/Lesson05`) `Order` z
         * metoda biznesowa `ship()` - zaprojektuj DTO `OrderDto` (tylko dane,
         * bez zadnej metody biznesowej) i mapper miedzy nimi. W komentarzu
         * wyjasnij, dlaczego DTO NIE powinno miec metod typu `ship()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignInputDtoWithValidationSeparateFromEntityInvariant {
        /*
         * 🧪 Zadanie 17:
         * Zaprojektuj `CreateAccountRequestDto` z podstawowa walidacja
         * FORMATU (np. email zawiera "@") oraz encje `Account` z WLASNYM
         * niezmiennikiem biznesowym (np. "wiek >= 18") - w komentarzu
         * wyjasnij roznice miedzy tymi 2 poziomami walidacji (wprowadzenie
         * do Lesson15: ValidationArchitecture).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RefactorLeakingEntityControllerToUseDto {
        /*
         * 🧪 Zadanie 18:
         * Napisz "kontroler" zwracajacy encje WPROST (z co najmniej 1 polem
         * wewnetrznym) - zrefaktoryzuj go, wprowadzajac DTO i mapper.
         * Zweryfikuj, ze wynik "API" juz nie zawiera pola wewnetrznego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignBidirectionalMapperForUpdateOperation {
        /*
         * 🧪 Zadanie 19:
         * Zaprojektuj `UpdateProductRequestDto` (wejsciowe) i metode
         * `applyUpdate(ProductEntity existing, UpdateProductRequestDto
         * changes)` zwracajaca NOWA, zaktualizowana encje (niezmienna,
         * `_16_clean_code/Lesson19`) - zademonstruj czesciowa aktualizacje
         * (np. TYLKO ceny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditRealDtoFromJdbcChapterForArchitecturalCorrectness {
        /*
         * 🧪 Zadanie 20:
         * Przeanalizuj (w komentarzu) `_09_jdbc/Lesson19_Dto` - czy DTO tam
         * zaprojektowane sa zgodne z zasadami architektonicznymi z tej lekcji
         * (brak wyciekow, dopasowanie do przypadku uzycia)?
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullDtoLayerForRealisticECommerceApi {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj kompletna warstwe DTO dla API sklepu internetowego:
         * min. 3 encje (Product, Customer, Order), min. 5 roznych DTO (wejsciowe
         * i wyjsciowe, dla roznych widokow), oraz mappery miedzy wszystkimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimulateApiClientUnaffectedByInternalRefactoring {
        /*
         * 🧪 Zadanie 22:
         * Dla systemu z Zadania 21, w PELNI zrefaktoryzuj WEWNETRZNA strukture
         * 1 encji (np. rozbij `CustomerEntity` na `CustomerEntity` +
         * `CustomerPreferencesEntity`, Extract Class, `_16_clean_code/
         * Lesson16`) - zademonstruj, ze WSZYSTKIE DTO/kontrakty API
         * pozostaja NIEZMIENIONE (tylko mapper sie zmienia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignVersionedDtoForEvolvingApiContract {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj 2 wersje DTO dla TEGO SAMEGO zasobu (`ProductDtoV1` z 3
         * polami, `ProductDtoV2` z 5 polami) mapowane z 1, WSPOLNEJ encji -
         * to wprowadzenie do Lesson08 (ApiVersioningAndCompatibility).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildMapperLayerRespectingDependencyDirectionPreview {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj strukture pakietow (w komentarzu, jako opis) TAK, by
         * `Mapper` ZALEZAL od obu (DTO i Entity), ale ANI DTO, ANI Entity NIE
         * zalezaly od Mappera - to wprowadzenie do Lesson10
         * (DependencyDirection).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignDtoAvoidingOverFetchingForMobileVsWebClients {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj 2 ROZNE DTO dla TEGO SAMEGO zasobu (np. `Product`)
         * zoptymalizowane pod 2 roznych klientow: `ProductMobileDto` (minimalne
         * pola, oszczednosc transferu) i `ProductWebDto` (pelniejsze dane) -
         * zademonstruj mapowanie do obu z 1 encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureBoilerplateCostVsMapStructAutomation {
        /*
         * 🧪 Zadanie 26:
         * Napisz RECZNY mapper dla encji z 8 polami (wszystkie pola mapowane
         * 1:1) - w komentarzu policz linie kodu i porownaj (opisowo, bez
         * uzywania biblioteki) z tym, jak wygladalby odpowiednik w MapStruct
         * (`_13_libraries/Lesson21-22`) - 1 adnotowany interfejs.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignAntiCorruptionMapperForLegacyDatabaseSchema {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj "legacy" encje z niezgrabnymi nazwami pol (np.
         * `usr_eml`, `usr_pwd_hsh`) symulujaca stara baze danych - napisz
         * mapper tlumaczacy ja na czysta, wspolczesna encje domenowa (por.
         * warstwa antykorupcyjna, Lesson06).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignAdrForChoosingManualMappingVsMapStructLibrary {
        /*
         * 🧪 Zadanie 28:
         * Napisz PELNY ADR uzasadniajacy wybor miedzy recznym mapowaniem a
         * biblioteka MapStruct dla nowego, duzego modulu z 20+ DTO - rozwaz
         * OBIE strony (kontrola vs automatyzacja boilerplate'u).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveDtoDesignChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu szczegolowa checkliste (min. 6
         * punktow) do projektowania DTO na granicy API - laczac WSZYSTKIE
         * zasady architektoniczne z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCapstoneFullDtoEntityMapperArchitectureForBookingSystem {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zaprojektuj i zaimplementuj kompletna
         * architekture DTO/Encja/Mapper dla systemu rezerwacji wydarzen: min.
         * 2 bogate encje (Lesson05) z WLASNA logika biznesowa, min. 4 rozne
         * DTO (wejsciowe/wyjsciowe, dla roznych widokow), mappery
         * NIEUJAWNIAJACE zadnych pol wewnetrznych ani metod biznesowych.
         * Zademonstruj PELNY przeplyw (tworzenie -> mapowanie do odpowiedzi ->
         * symulowana zmiana wewnetrznej struktury encji BEZ zmiany kontraktu
         * DTO).
         */
        public static void main(String[] args) { }
    }
}
