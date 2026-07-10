package com.example.javaquest._17_architecture.Lesson06_BoundedContextsAndDddLite;

public class _Lesson06_BoundedContextsAndDddLite {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: BOUNDED CONTEXTS I DDD W PIGULCE ===");

        /*
         * ============================================================
         * 📦 TEN SAM TERMIN, INNE ZNACZENIE W ROZNYCH CZESCIACH SYSTEMU
         * ============================================================
         * - W duzym systemie e-commerce slowo "Produkt" oznacza COS
         *   INNEGO w zaleznosci od tego, KTO o nim mowi:
         *   - dla KATALOGU: nazwa, opis marketingowy, zdjecia, kategoria.
         *   - dla MAGAZYNU: SKU, waga, lokalizacja na polce, stan.
         *   - dla FAKTUROWANIA: cena netto, stawka VAT, kod ksiegowy.
         * - Proba zbudowania JEDNEJ, uniwersalnej klasy `Product` "dla
         *   wszystkich" prowadzi do klasy-potwora ze WSZYSTKIMI polami
         *   naraz - dokladnie "God Class"/niska spojnosc z `_16_clean_code/
         *   Lesson06`, ale na poziomie CALEGO MODELU DOMENOWEGO, a nie
         *   1 klasy technicznej.
         */
        System.out.println("Ten sam termin biznesowy (np. 'Produkt') moze OZNACZAC COS INNEGO w roznych czesciach systemu.");

        /*
         * ============================================================
         * 🔹 BOUNDED CONTEXT (ERIC EVANS, "DOMAIN-DRIVEN DESIGN", 2003)
         * ============================================================
         * - Bounded Context (ograniczony kontekst) to JAWNA GRANICA, w
         *   ramach ktorej DANY model (ze swoim precyzyjnym znaczeniem
         *   terminow) obowiazuje - POZA ta granica, TEN SAM termin MOZE
         *   (i CZESTO POWINIEN) znaczyc cos innego.
         * - To NIE jest blad ani niespojnosc - to SWIADOME rozpoznanie, ze
         *   "1 uniwersalny model dla calej firmy" jest praktycznie
         *   NIEMOZLIWY do utrzymania w realnym, wiekszym systemie.
         */
        demonstrateSameTermDifferentModelsInDifferentContexts();

        /*
         * ============================================================
         * 🔍 JEZYK WSZECHOBECNY (UBIQUITOUS LANGUAGE) WEWNATRZ KONTEKSTU
         * ============================================================
         * - W RAMACH 1 bounded context, deweloperzy i eksperci
         *   dziedzinowi (np. pracownicy magazynu) POWINNI uzywac
         *   DOKLADNIE TYCH SAMYCH slow, co widnieja w kodzie - klasa
         *   `StockItem` z metoda `reserveQuantity(...)` powinna nazywac
         *   sie i dzialac TAK, jak opisalby to pracownik magazynu, BEZ
         *   zadnego "tlumaczenia" miedzy jezykiem biznesu a jezykiem kodu.
         * - To sprawia, ze kod w 1 kontekscie jest CZYTELNY dla osob
         *   ROZUMIEJACYCH TEN kawalek biznesu - nawet jesli slowo
         *   "Produkt" znaczy tam cos innego niz w kontekscie katalogu.
         */
        System.out.println("\nJezyk wszechobecny: WEWNATRZ 1 kontekstu kod i biznes uzywaja DOKLADNIE tych samych slow.");

        /*
         * ============================================================
         * 🔹 GRANICE KONTEKSTU = PRZYSZLE GRANICE MODULOW
         * ============================================================
         * - Bounded context to NIE jest "diagram na scianie" - to
         *   NAJCZESCIEJ realna granica w KODZIE: osobny pakiet, osobny
         *   modul (Lesson17: ModularMonolith), czasem osobny
         *   mikroserwis (Lesson19).
         * - Rozpoznanie WLASCIWYCH granic kontekstow to jedna z
         *   NAJWAZNIEJSZYCH (i najtrudniejszych) decyzji architektonicznych -
         *   dokladnie tych "kosztownych do odwrocenia" z Lesson01 - i
         *   dobry kandydat na wlasny ADR (Lesson02).
         */
        System.out.println("\nGranice bounded context CZESTO staja sie granicami modulow/pakietow (Lesson17) - decyzja warta ADR (Lesson02).");

        /*
         * ============================================================
         * 🔍 MAPOWANIE KONTEKSTOW (CONTEXT MAPPING) - TLUMACZENIE NA GRANICY
         * ============================================================
         * - Gdy kontekst Katalogu POTRZEBUJE danych z kontekstu Magazynu
         *   (np. "czy produkt jest dostepny"), NIE dzieli sie z nim
         *   WSPOLNEJ klasy `Product` - zamiast tego, na GRANICY miedzy
         *   kontekstami, dane sa TLUMACZONE (mapowane) na wlasny,
         *   lokalny ksztalt.
         * - To DOKLADNIE ten sam mechanizm co DTO/Mapper poznany w
         *   `_09_jdbc/Lesson19-20` i pogleebiony architektonicznie w
         *   nastepnej-nastepnej lekcji (Lesson08: DtoEntityMapper) - tylko
         *   zastosowany MIEDZY kontekstami, nie tylko miedzy warstwami
         *   1 aplikacji.
         */
        demonstrateContextMappingBetweenCatalogAndWarehouse();

        /*
         * ============================================================
         * 🔹 DDD "W PIGULCE" - CO POMIJAMY (SWIADOMIE)
         * ============================================================
         * - Pelne Domain-Driven Design (Evans) to OBSZERNA ksiazka z
         *   dziesiatkami wzorcow (Aggregates, Entities vs Value Objects,
         *   Domain Events, Repositories jako wzorzec DDD, Context Mapping
         *   ze szczegolowa typologia relacji). Ta lekcja to CELOWO "DDD w
         *   pigulce" - TYLKO idea bounded context, bo TA WLASNIE jest
         *   fundamentem pod kolejne lekcje (Lesson17: ModularMonolith,
         *   Lesson18: komunikacja miedzy modulami).
         * - Agregaty i obiekty wartosci JUZ poznales praktycznie (bogaty
         *   model z Lesson05, obiekty wartosci z `_16_clean_code/
         *   Lesson19`) - tutaj tylko LACZYMY je z pojeciem granicy.
         */
        System.out.println("\nTo 'DDD w pigulce' - pelne DDD to cala ksiazka (Evans); tu: TYLKO fundament pod Lesson17-18.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Bounded Context (Evans): jawna granica, w ktorej dany model i
         *   znaczenie terminow obowiazuja - poza nia, ten sam termin moze
         *   znaczyc co innego.
         * - Jezyk wszechobecny: w RAMACH kontekstu, kod i biznes uzywaja
         *   TYCH SAMYCH slow.
         * - Granice kontekstow czesto staja sie granicami modulow/
         *   pakietow (Lesson17).
         * - Miedzy kontekstami: tlumaczenie (context mapping), nigdy
         *   wspoldzielenie tej samej klasy modelu.
         * - W kolejnej lekcji (Lesson07): DTO/Encja/Mapper - dokladny
         *   mechanizm tego tlumaczenia, tym razem na granicy API, nie
         *   miedzy kontekstami domenowymi.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void demonstrateSameTermDifferentModelsInDifferentContexts() {
        System.out.println("\n=== TEN SAM TERMIN 'PRODUKT' - 3 ROZNE MODELE W 3 KONTEKSTACH ===");

        CatalogContext.Product catalogProduct = new CatalogContext.Product(
                "SKU-100", "Laptop Gamingowy X1", "Wydajny laptop do gier", "Elektronika");
        WarehouseContext.StockItem warehouseItem = new WarehouseContext.StockItem(
                "SKU-100", 2.4, "Regal A-12", 15);
        BillingContext.InvoiceLineItem billingItem = new BillingContext.InvoiceLineItem(
                "SKU-100", 4500.0, "23%", "PKWiU-2620");

        System.out.println("[Kontekst KATALOGU] " + catalogProduct);
        System.out.println("[Kontekst MAGAZYNU]  " + warehouseItem);
        System.out.println("[Kontekst FAKTUROWANIA] " + billingItem);
        System.out.println("-> WSZYSTKIE 3 dotycza 'tego samego' produktu (SKU-100), ale KAZDY kontekst");
        System.out.println("   widzi TYLKO wlasciwe SOBIE pola - zadna klasa nie ma WSZYSTKICH naraz.");
    }

    /** Kontekst KATALOGU - "Produkt" = tresc marketingowa, NIC o magazynie/fakturach. */
    static class CatalogContext {
        record Product(String sku, String displayName, String marketingDescription, String category) {
            @Override
            public String toString() {
                return sku + " '" + displayName + "' (" + category + ")";
            }
        }
    }

    /** Kontekst MAGAZYNU - "Produkt" = fizyczne parametry i stan, NIC o marketingu/cenie. */
    static class WarehouseContext {
        record StockItem(String sku, double weightKg, String shelfLocation, int quantityOnHand) {
            @Override
            public String toString() {
                return sku + ", waga=" + weightKg + "kg, polka=" + shelfLocation + ", stan=" + quantityOnHand;
            }
        }
    }

    /** Kontekst FAKTUROWANIA - "Produkt" = cena i dane podatkowe, NIC o wadze/nazwie marketingowej. */
    static class BillingContext {
        record InvoiceLineItem(String sku, double netPrice, String vatRate, String taxCode) {
            @Override
            public String toString() {
                return sku + ", cena netto=" + netPrice + ", VAT=" + vatRate + ", kod=" + taxCode;
            }
        }
    }

    private static void demonstrateContextMappingBetweenCatalogAndWarehouse() {
        System.out.println("\n=== MAPOWANIE KONTEKSTOW: KATALOG PYTA MAGAZYN O DOSTEPNOSC ===");

        WarehouseContext.StockItem warehouseItem = new WarehouseContext.StockItem("SKU-100", 2.4, "Regal A-12", 15);

        // Katalog NIE UZYWA StockItem wprost - TLUMACZY go na WLASNY, lokalny ksztalt
        // (DokLADNIE mechanizm DTO/Mapper, poglebiony architektonicznie w Lesson08).
        CatalogAvailabilityView availabilityView = translateToAvailabilityView(warehouseItem);
        System.out.println("Kontekst KATALOGU widzi TYLKO: " + availabilityView);
        System.out.println("-> Katalog NIE zna pol 'weightKg'/'shelfLocation' - to szczegoly WYLACZNIE Magazynu.");
    }

    /** Lokalny ksztalt danych o dostepnosci - WLASNY dla kontekstu Katalogu, nie dzieli klasy z Magazynem. */
    record CatalogAvailabilityView(String sku, boolean inStock) {
        @Override
        public String toString() {
            return sku + " -> " + (inStock ? "DOSTEPNY" : "BRAK NA STANIE");
        }
    }

    private static CatalogAvailabilityView translateToAvailabilityView(WarehouseContext.StockItem item) {
        return new CatalogAvailabilityView(item.sku(), item.quantityOnHand() > 0);
    }
}
