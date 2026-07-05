package com.example.javaquest._12_hibernate.Lesson28_BeanValidationIntegration;

public class _Exercises_Lesson28_BeanValidationIntegration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_NotNullConstraintBasic {
        /*
         * 🧪 Zadanie 1:
         * Utworz encje Product (id, @NotNull name, price) na H2
         * ("jdbc:h2:mem:l28ex01;DB_CLOSE_DELAY=-1"). Sprobuj zapisac produkt z
         * name=null - zapisz w komentarzu ConstraintViolationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SizeConstraintBasic {
        /*
         * 🧪 Zadanie 2:
         * Dodaj @Size(min=3, max=50) na name. Sprobuj zapisac produkt z name="AB"
         * (za krotkie) i zapisz w komentarzu bledna wiadomosc walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MinMaxConstraintBasic {
        /*
         * 🧪 Zadanie 3:
         * Dodaj @Min(0) @Max(100000) na price. Sprobuj zapisac produkt z price=-10 i
         * z price=200000 - zapisz w komentarzu obie bledne wiadomosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_EmailConstraintBasic {
        /*
         * 🧪 Zadanie 4:
         * Utworz DRUGA encje Contact (id, @Email contactEmail). Sprobuj zapisac z
         * "nie-email" i zapisz w komentarzu blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ValidObjectSavesSuccessfully {
        /*
         * 🧪 Zadanie 5:
         * Zapisz Product ze WSZYSTKIMI polami POPRAWNYMI (spelniajacymi wszystkie
         * ograniczenia z Zadan 1-3) - zweryfikuj sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleViolationsAtOnce {
        /*
         * 🧪 Zadanie 6:
         * Sprobuj zapisac Product NARUSZAJACY WSZYSTKIE 3 ograniczenia naraz
         * (name=null, price=-10). Zapisz w komentarzu, ile violations zwrocil
         * ConstraintViolationException (spodziewaj sie WIECEJ niz 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ManualValidatorWithoutDatabase {
        /*
         * 🧪 Zadanie 7:
         * Uzyj Validation.buildDefaultValidatorFactory().getValidator().validate(obj)
         * BEZ zadnej bazy danych - zwaliduj obiekt Product recznie i wypisz naruszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ValidObjectManualValidationEmptySet {
        /*
         * 🧪 Zadanie 8:
         * Zwaliduj recznie (Zadanie 7) POPRAWNY obiekt Product - zweryfikuj, ze
         * validate() zwraca PUSTY zbior naruszen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PositiveAndNegativeAnnotations {
        /*
         * 🧪 Zadanie 9:
         * Dodaj pole z @Positive (musi byc > 0, nie >= 0 jak @Min(0)) - zapisz w
         * komentarzu roznice miedzy @Positive a @Min(0) (0 przechodzi @Min(0), ale
         * NIE przechodzi @Positive).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PatternConstraintRegex {
        /*
         * 🧪 Zadanie 10:
         * Dodaj @Pattern(regexp = "...") na polu (np. kod produktu w formacie
         * "PRD-00001"). Sprobuj zapisac produkt z NIEPRAWIDLOWYM formatem kodu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ValidationOnUpdateNotJustPersist {
        /*
         * 🧪 Zadanie 11:
         * Zapisz POPRAWNY Product, potem w NOWEJ Session ZMIEN jego price na
         * NIEPRAWIDLOWA wartosc (-50) i wywolaj commit - zweryfikuj, ze walidacja
         * zadziala TAKZE przy UPDATE, nie tylko przy CREATE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CustomValidationGroups {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj WLASNA "grupe" walidacji (interfejs np. OnCreate) i zastosuj
         * @NotNull(groups = OnCreate.class) na polu (np. id-zewnetrzne, wymagane
         * TYLKO przy tworzeniu). Uzyj validator.validate(obj, OnCreate.class) recznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CustomConstraintAnnotation {
        /*
         * 🧪 Zadanie 13:
         * Napisz WLASNA adnotacje walidacyjna (@ValidProductCode + wlasny
         * ConstraintValidator) sprawdzajaca zlozona regule (np. kod musi zaczynac sie
         * od "PRD-" i miec dokladnie 5 cyfr po myslniku). Zastosuj ja na polu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CrossFieldValidationWithClassLevel {
        /*
         * 🧪 Zadanie 14:
         * Napisz WLASNA adnotacje NA POZIOMIE KLASY (nie pola) walidujaca relacje
         * MIEDZY dwoma polami (np. "discountPrice musi byc MNIEJSZA niz price").
         * Zastosuj ja na Product z DWOMA polami cenowymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ValidateNestedEmbeddableObject {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do Product @Embedded Address (podglad Lesson09) z WLASNYMI
         * ograniczeniami walidacyjnymi na jego polach + @Valid na polu @Embedded w
         * Product (kaskadowa walidacja zagniezdzonego obiektu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ManualValidationBeforePersistPattern {
        /*
         * 🧪 Zadanie 16:
         * Napisz metode "validateAndSave(Session, Validator, Product)" ktora
         * NAJPIERW waliduje RECZNIE (przed persist), a dopiero PO POMYSLNEJ
         * walidacji wywoluje session.persist() - unikajac kosztownego "cichego"
         * bledu bazy danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ValidationMessageCustomization {
        /*
         * 🧪 Zadanie 17:
         * Ustaw WLASNA wiadomosc bledu na adnotacji (np. @NotNull(message =
         * "Nazwa produktu jest wymagana!")) - zweryfikuj, ze wlasna wiadomosc
         * pojawia sie w ConstraintViolation.getMessage() zamiast domyslnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConstraintViolationExceptionDetailedReport {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode "formatViolations(ConstraintViolationException)" zwracajaca
         * CZYTELNY, wieloliniowy raport (jedna linia na naruszenie: "pole: wiadomosc")
         * zamiast surowego toString() wyjatku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ValidationWithCollectionElements {
        /*
         * 🧪 Zadanie 19:
         * Dodaj do Product @ElementCollection List<String> tags z @NotEmpty na
         * KAZDYM elemencie (@Size(min=1) na kazdym Stringu w liscie, uzywajac
         * @Valid na kolekcji). Sprobuj zapisac produkt z PUSTYM tagiem w liscie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DisableAutoValidationConfiguration {
        /*
         * 🧪 Zadanie 20:
         * Wylacz automatyczna walidacje Hibernate (hibernate.validator.apply_to_ddl=false
         * lub javax.persistence.validation.mode=NONE) - zapisz NIEPOPRAWNY obiekt
         * BEZ ConstraintViolationException i zapisz w komentarzu, czy dane trafily
         * do bazy mimo naruszenia reguly.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullFormValidationBackendService {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNY "backend formularza" - klasa "ProductFormService" z
         * metoda "submitForm(RawFormData)" ktora: (1) recznie waliduje dane
         * WEJSCIOWE (Zadanie 16), (2) mapuje na encje, (3) zapisuje - z pelnym
         * raportowaniem bledow (Zadanie 18) na kazdym etapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CustomConstraintWithDatabaseLookup {
        /*
         * 🧪 Zadanie 22:
         * Napisz WLASNA adnotacje walidacyjna sprawdzajaca UNIKALNOSC pola W BAZIE
         * (np. @UniqueProductCode) - ConstraintValidator wykonuje ZAPYTANIE do bazy
         * (przez wstrzykniete SessionFactory) zeby sprawdzic, czy kod juz istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ValidationGroupSequenceOrdering {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj @GroupSequence z 2 grupami walidacji wykonywanymi W OKRESLONEJ
         * KOLEJNOSCI (podstawowe ograniczenia PRZED kosztownymi, np. wywolujacymi
         * baze danych z Zadania 22) - zademonstruj, ze druga grupa NIE jest
         * sprawdzana, jesli pierwsza juz zawiodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ValidationPerformanceOverheadMeasurement {
        /*
         * 🧪 Zadanie 24:
         * Zmierz czas zapisu 1000 obiektow Product Z automatyczna walidacja Hibernate
         * WLACZONA vs WYLACZONA (Zadanie 20) - zapisz oba czasy w komentarzu i
         * oszacuj narzut walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConditionalValidationBasedOnState {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WARUNKOWA walidacje (np. "discountPrice jest WYMAGANE TYLKO
         * gdy onSale=true") przez WLASNY class-level constraint (Zadanie 14) z
         * logika warunkowa wewnatrz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_IntegrationWithExternalApiValidationRules {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj (kod + komentarz) integracje walidacji Hibernate z
         * "zewnetrznymi" regulami biznesowymi (np. limit cenowy pobierany z
         * konfiguracji, nie z kodu) - WLASNY ConstraintValidator odczytujacy
         * wartosc progu z zewnetrznego zrodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ValidationErrorAggregationAcrossMultipleEntities {
        /*
         * 🧪 Zadanie 27:
         * Napisz metode walidujaca CALY graf obiektow (Product + powiazane Category)
         * naraz PRZED zapisaniem czegokolwiek - zbierajaca WSZYSTKIE naruszenia z
         * OBU encji w JEDNYM raporcie, zamiast zatrzymywac sie na pierwszym bledzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestingCustomConstraintValidatorInIsolation {
        /*
         * 🧪 Zadanie 28:
         * Napisz "test" (metoda z asercjami if+throw) sprawdzajacy WLASNY
         * ConstraintValidator (Zadanie 13) NA SUCHO (bez zadnej Session/bazy) -
         * bezposrednio wywolujac isValid() z roznymi wartosciami wejsciowymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnValidationLayerPlacement {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, na KTOREJ warstwie aplikacji (kontroler,
         * serwis, encja) powinna zyc KTORA walidacja - i dlaczego automatyczna
         * walidacja Hibernate NIE zastepuje walidacji na wejsciu (np. formularza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullBeanValidationCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system zarzadzania produktami
         * laczacy: podstawowe adnotacje (@NotNull/@Size/@Min/@Max), WLASNA adnotacje
         * (Zadanie 13), walidacje na poziomie klasy (Zadanie 14), grupy walidacji z
         * kolejnoscia (Zadanie 23), i pelny formularz backendowy (Zadanie 21).
         */
        public static void main(String[] args) { }
    }
}
