package com.example.javaquest._12_hibernate.Lesson10_EnumsAndAttributeConverters;

public class _Exercises_Lesson10_EnumsAndAttributeConverters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_EnumOrdinalBasic {
        /*
         * 🧪 Zadanie 1:
         * Utworz enum Priority (LOW, MEDIUM, HIGH) i encje Task (id, title,
         * @Enumerated(ORDINAL) priority) na H2 ("jdbc:h2:mem:l10ex01;DB_CLOSE_DELAY=-1").
         * Zapisz zadanie z priority=HIGH i sprawdz recznym SQL, jaka liczba zostala
         * zapisana w kolumnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_EnumStringBasic {
        /*
         * 🧪 Zadanie 2:
         * Ta sama encja, ale z @Enumerated(STRING). Zapisz zadanie z priority=HIGH i
         * sprawdz recznym SQL, jaki TEKST zostal zapisany w kolumnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OrdinalFragilityDemo {
        /*
         * 🧪 Zadanie 3:
         * Z encja ORDINAL: zapisz zadanie z priority=HIGH (index 2). ZMIEN kolejnosc
         * stalych w enum (np. HIGH na poczatek) i odczytaj to samo zadanie ponownie -
         * zapisz w komentarzu, ze priority "zmienilo sie" mimo braku modyfikacji danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReadEnumFromDatabase {
        /*
         * 🧪 Zadanie 4:
         * Zapisz 3 zadania z roznymi priorytetami (STRING). Odczytaj wszystkie i
         * wypisz ich priorytety jako wartosci enum (nie liczby/Stringi).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_EnumInWhereClauseHqlPreview {
        /*
         * 🧪 Zadanie 5:
         * Zapisz zadania z roznymi priorytetami. Uzyj HQL (podglad przed Lesson18)
         * "from Task t where t.priority = :priority" zeby znalezc TYLKO zadania HIGH.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SimpleAttributeConverterUpperCase {
        /*
         * 🧪 Zadanie 6:
         * Napisz AttributeConverter<String, String> zamieniajacy tekst na WIELKIE
         * LITERY przy zapisie (i zwracajacy bez zmian przy odczycie). Zastosuj go do
         * pola "title" w Task i zweryfikuj zapisana wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ConverterAutoApplyFalse {
        /*
         * 🧪 Zadanie 7:
         * Uzyj @Converter(autoApply = false) i zastosuj konwerter TYLKO do JEDNEGO
         * pola przez jawny @Convert(converter = ...) - zweryfikuj, ze INNE pole tego
         * samego typu NIE zostalo przekonwertowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ConverterAutoApplyTrue {
        /*
         * 🧪 Zadanie 8:
         * Uzyj @Converter(autoApply = true) i zweryfikuj, ze konwerter zastosowal
         * sie AUTOMATYCZNIE do WSZYSTKICH pol tego typu w encji, BEZ jawnego @Convert.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TagListAsCsvConverter {
        /*
         * 🧪 Zadanie 9:
         * Odtworz konwerter List<String> <-> CSV z lekcji dla WLASNEJ encji (np.
         * BlogPost z polem tags). Zapisz post z 4 tagami i odczytaj je z powrotem
         * jako liste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_EmptyListConverterEdgeCase {
        /*
         * 🧪 Zadanie 10:
         * Zapisz encje z PUSTA lista tagow (List.of()). Zweryfikuj, ze po odczycie
         * lista jest PUSTA (nie null, nie lista z jednym pustym Stringiem).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DateAttributeConverter {
        /*
         * 🧪 Zadanie 11:
         * Napisz AttributeConverter<LocalDate, String> zapisujacy date w formacie
         * "DD/MM/YYYY" (zamiast domyslnego ISO). Zapisz i odczytaj encje z konkretna
         * data, weryfikujac poprawna konwersje w obie strony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BooleanAsYesNoConverter {
        /*
         * 🧪 Zadanie 12:
         * Napisz AttributeConverter<Boolean, String> zapisujacy "Y"/"N" zamiast
         * TRUE/FALSE (typowe w starszych bazach danych). Zweryfikuj recznym SQL
         * zapisana wartosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MapAsJsonLikeStringConverter {
        /*
         * 🧪 Zadanie 13:
         * Napisz AttributeConverter<Map<String,String>, String> serializujacy prosta
         * mape do formatu "klucz1=wartosc1;klucz2=wartosc2". Zapisz encje z mapa 3
         * par i odczytaj ja poprawnie z powrotem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_EnumWithCustomConverterInsteadOfEnumerated {
        /*
         * 🧪 Zadanie 14:
         * ZAMIAST @Enumerated, napisz WLASNY AttributeConverter<Priority, Integer>
         * mapujacy enum na WLASNE, jawne kody liczbowe (np. LOW=10, MEDIUM=20,
         * HIGH=30 - nie kolejne indeksy) - odporne na zmiane kolejnosci stalych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareOrdinalStringAndConverterStorage {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj TRZY wersje TEJ SAMEJ encji z priorytetem: ORDINAL, STRING, i
         * WLASNY konwerter (Zadanie 14). Zapisz identyczne dane w kazdej wersji i
         * porownaj recznym SQL zawartosc kolumny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConverterWithValidationInside {
        /*
         * 🧪 Zadanie 16:
         * Dodaj do convertToEntityAttribute() konwertera walidacje wejscia (np. jesli
         * String z bazy nie pasuje do oczekiwanego formatu, rzuc
         * IllegalArgumentException zamiast zwrocic bledna wartosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TemporalAnnotationOnLegacyDate {
        /*
         * 🧪 Zadanie 17:
         * Dodaj do encji pole typu java.util.Date z @Temporal(TemporalType.DATE).
         * Zapisz obiekt z konkretna data i odczytaj ja poprawnie - porownaj w
         * komentarzu z uzyciem LocalDate (bez potrzeby @Temporal).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_EnumSetMappingViaConverter {
        /*
         * 🧪 Zadanie 18:
         * Napisz AttributeConverter<Set<Priority>, String> zapisujacy ZBIOR enumow
         * jako liste rozdzielona przecinkami (np. "LOW,HIGH"). Zapisz encje z 2
         * priorytetami naraz i odczytaj cala liste poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConverterPerformanceOverhead {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas zapisu 500 obiektow z polem konwertowanym (List<String> jako
         * CSV) vs 500 obiektow z prostym polem String (bez konwersji) - zapisz oba
         * czasy w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MigrateOrdinalToStringSafely {
        /*
         * 🧪 Zadanie 20:
         * Zasymuluj bezpieczna migracje z ORDINAL na STRING: zapisz dane z ORDINAL,
         * napisz reczny skrypt SQL (UPDATE) zamieniajacy liczby na odpowiadajace
         * nazwy tekstowe, potem zmien encje na @Enumerated(STRING) i zweryfikuj
         * poprawnosc odczytu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultipleConvertersOnOneEntity {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj encje z 3 roznymi, WLASNYMI konwerterami naraz (np. CSV lista, Y/N
         * boolean, wlasny format daty) - zapisz kompletny obiekt i zweryfikuj
         * poprawnosc kazdego pola po odczycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_JsonSerializationConverterWithLibrary {
        /*
         * 🧪 Zadanie 22:
         * Napisz AttributeConverter uzywajacy Gson (juz w pom.xml, z _04_io) do
         * serializacji CALEGO obiektu (np. WLASNA klasa Address) do JSON w jednej
         * kolumnie tekstowej - zapisz i odczytaj poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConverterVersioningStrategy {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj (kod + komentarz) strategie "wersjonowania" formatu
         * konwertera - np. prefiks "v2:" przed danymi w nowym formacie, tak aby
         * convertToEntityAttribute() umial odczytac ZARWNO stare, jak i nowe dane w
         * tej samej kolumnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_EnumBasedStateMachine {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj enum OrderStatus (NEW, PAID, SHIPPED, DELIVERED, CANCELLED) z
         * WLASNA metoda canTransitionTo(OrderStatus next) definiujaca dozwolone
         * przejscia (prosta maszyna stanow) - uzyj jej PRZED zapisaniem zmiany statusu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareConverterVsEmbeddableForComplexType {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj TEN SAM zlozony typ (np. zakres cenowy min-max) RAZ jako
         * @Embeddable (2 kolumny), RAZ jako WLASNY konwerter (1 kolumna tekstowa
         * "10-50") - porownaj w komentarzu zalety/wady obu podejsc (mozliwosc
         * zapytan SQL po pojedynczej wartosci min/max).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LazyConverterWithCaching {
        /*
         * 🧪 Zadanie 26:
         * Zoptymalizuj kosztowny konwerter (np. symulowane parsowanie z opoznieniem
         * Thread.sleep(1)) przez wewnetrzny cache (Map) unikajacy powtornego
         * przetwarzania TEJ SAMEJ wartosci - zmierz i porownaj czas z wersja bez cache'a.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConverterForEncryptedField {
        /*
         * 🧪 Zadanie 27:
         * Napisz PROSTY (edukacyjny, NIE produkcyjny) AttributeConverter "szyfrujacy"
         * pole tekstowe prostym odwroceniem Stringa/XOR przy zapisie i odwrotna
         * operacja przy odczycie - zademonstruj, ze w bazie (recznym SQL) wartosc
         * wyglada inaczej niz w obiekcie Javy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BackwardCompatibilityWithOldOrdinalData {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj sytuacje: STARE dane zapisane z ORDINAL (liczby) wciaz w bazie,
         * NOWY kod uzywa @Enumerated(STRING). Napisz WLASNY konwerter potrafiacy
         * odczytac OBA formaty (String LUB liczba jako String) z tej samej kolumny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnEnumMappingStrategies {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, kiedy wybrac ORDINAL, kiedy STRING, a
         * kiedy WLASNY konwerter dla enuma - z odniesieniem do konkretnych ryzyk
         * (zmiana kolejnosci, rozmiar danych, czytelnosc w bazie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullConvertersCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj encje BlogPost laczaca WSZYSTKIE techniki z
         * tej lekcji: status jako @Enumerated(STRING), tagi jako WLASNY konwerter CSV
         * (Zadanie 9), oraz WLASNA metadane jako konwerter Map (Zadanie 13) -
         * zademonstruj pelny zapis i odczyt takiego posta.
         */
        public static void main(String[] args) { }
    }
}
