package com.example.javaquest._19_security_basics.Lesson14_InsecureDeserialization;

public class _Exercises_Lesson14_InsecureDeserialization {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainSerializationVsDeserializationInOwnWords {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: przypomnij (z `_04_io/Lesson16`) roznice miedzy
         * serializacja a deserializacja - ktora z nich jest tu ryzykowna
         * i dlaczego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SerializeAndDeserializeSimpleRecord {
        /*
         * 🧪 Zadanie 2:
         * Zserializuj i odtworz prosty rekord `Serializable` do/z
         * tablicy bajtow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhyReadObjectIsNotJustDataReading {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij, dlaczego `readObject()` NIE jest
         * "zwyklym odczytem danych" - co dokladnie sie dzieje (tworzenie
         * obiektow, wywolywanie metod).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainGadgetChainConceptually {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij wlasnymi slowami, czym jest "gadget
         * chain" - dlaczego atakujacy NIE MUSI znac zadnych sekretow
         * aplikacji, zeby go wykorzystac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CreateObjectInputFilterAllowingOneClass {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj `ObjectInputFilter` dopuszczajacy TYLKO 1 konkretna
         * klase - zweryfikuj, ze deserializacja TEJ klasy dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RejectDisallowedClassWithFilter {
        /*
         * 🧪 Zadanie 6:
         * Zweryfikuj, ze filtr z Zadania 5 ODRZUCA INNA klase -
         * przechwyc `InvalidClassException`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareBehaviorWithAndWithoutFilter {
        /*
         * 🧪 Zadanie 7:
         * Sprobuj zdeserializowac te sama klase Z filtrem i BEZ filtra -
         * porownaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyJsonIsSaferThanJavaSerialization {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, dlaczego deserializacja JSON (Jackson/
         * Gson, `_04_io/Lesson19-21`) jest z NATURY bezpieczniejsza niz
         * `ObjectInputStream.readObject()` (podpowiedz: JSON nie
         * wywoluje dowolnych konstruktorow/metod klas).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListClassesAllowedInFilterAsWhitelist {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj filtr dopuszczajacy WIELE (3-4) konkretnych klas -
         * zweryfikuj kazda z osobna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyBlacklistingIsWeakerThanWhitelisting {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego "czarna lista" (blokowanie
         * ZNANYCH zlych klas) jest slabsza niz "biala lista"
         * (dopuszczanie TYLKO znanych dobrych klas) w kontekscie
         * deserializacji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementFilterWithMaxDepthAndArrayLength {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj filtr ograniczajacy DODATKOWO maksymalna glebokosc
         * (`maxdepth`) i dlugosc tablic (`maxarray`) w deserializowanym
         * grafie obiektow - wyjasnij, przed jakim atakiem (np. "billion
         * laughs" dla serializacji) to chroni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementGlobalJvmWideFilter {
        /*
         * 🧪 Zadanie 12:
         * Zbadaj i (jesli mozliwe w tym srodowisku) zademonstruj
         * ustawienie GLOBALNEGO filtra JVM (`jdk.serialFilter` -
         * property systemowa/`ObjectInputFilter.Config.setSerialFilter`)
         * dzialajacego dla WSZYSTKICH `ObjectInputStream` w aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MigrateSerializableClassToJsonRecord {
        /*
         * 🧪 Zadanie 13:
         * Przepisz klase `Serializable` z tej lekcji na uzycie Jackson
         * (`_04_io/Lesson21`) zamiast natywnej serializacji Javy -
         * porownaj bezpieczenstwo i elastycznosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DetectSerializableClassesOnClasspath {
        /*
         * 🧪 Zadanie 14:
         * Uzyj refleksji do wypisania wszystkich klas WLASNEGO pakietu
         * implementujacych `Serializable` - wyjasnij, dlaczego KAZDA z
         * nich to POTENCJALNY punkt wejscia ataku, jesli kiedykolwiek
         * bedzie deserializowana z niezaufanego zrodla.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementSafeDeserializationWrapperMethod {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj metode `safeDeserialize(byte[], Class<T>)`,
         * ktora WEWNETRZNIE zawsze ustawia filtr dopuszczajacy TYLKO
         * podana klase - uzyj jej zamiast bezposredniego
         * `ObjectInputStream`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_HandleInvalidClassExceptionGracefully {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj obsluge `InvalidClassException` w endpointcie
         * HTTP (symulowanym) - zwroc 400 z BEZPIECZNYM komunikatem (bez
         * szczegolow technicznych ujawniajacych strukture systemu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareSerializationFormatsForApiContracts {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj (tabela w komentarzu) natywna
         * serializacje Javy, JSON, i Protocol Buffers pod katem
         * bezpieczenstwa deserializacji i przenosnosci miedzy jezykami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementVersionCheckBeforeDeserialization {
        /*
         * 🧪 Zadanie 18:
         * Polacz filtr z `serialVersionUID` (`_04_io/Lesson17`) - wyjasnij,
         * czym rozni sie ochrona przez `serialVersionUID` (kompatybilnosc
         * WERSJI) od ochrony przez `ObjectInputFilter` (BEZPIECZENSTWO
         * klas).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LogRejectedDeserializationAttemptsForMonitoring {
        /*
         * 🧪 Zadanie 19:
         * Dodaj logowanie (na konsole) kazdej odrzuconej przez filtr proby
         * deserializacji - zapowiedz Lesson19 (Secure Logging).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainRealWorldCveExampleConceptually {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: zbadaj (z pamieci/wiedzy ogolnej) i opisz JEDEN
         * historyczny przyklad CVE zwiazany z niebezpieczna deserializacja
         * Javy (np. Apache Commons Collections + WebLogic/WebSphere,
         * ok. 2015) - jaki byl skutek.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildLayeredDefenseFilterPlusJsonMigration {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj strategie MIGRACJI istniejacego systemu z natywnej
         * serializacji Javy na JSON - jak zapewnic filtr (Zadanie 5) jako
         * TYMCZASOWE zabezpieczenie W TRAKCIE migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomReadObjectWithValidation {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj wlasna metode `readObject(ObjectInputStream)` w
         * klasie `Serializable`, ktora WALIDUJE stan obiektu PO
         * odczytaniu (np. odrzuca nieprawidlowe wartosci pol) - wyjasnij,
         * ze to DODATKOWA, nie ZASTEPCZA, warstwa wobec filtra.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasurePerformanceOverheadOfObjectInputFilter {
        /*
         * 🧪 Zadanie 23:
         * Zmierz narzut czasowy deserializacji 100 000 obiektow Z i BEZ
         * filtra - skomentuj, czy jest ISTOTNY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementDenyListAsSecondaryDefenseLayer {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj DODATKOWY filtr jawnie BLOKUJACY znane,
         * historycznie niebezpieczne klasy (np. z Commons Collections) -
         * wyjasnij, ze to warstwa "na wszelki wypadek" OBOK biala listy,
         * NIE zamiast niej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AuditThirdPartyDependenciesForKnownGadgets {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: opisz, jak `mvn dependency:tree` (zapowiedz
         * Lesson20) moze pomoc zidentyfikowac obecnosc bibliotek znanych
         * z historycznych "gadget chains" na classpath aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSandboxedDeserializationProcess {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj (opisz w komentarzu) architekture, w ktorej
         * deserializacja NIEZAUFANYCH danych odbywa sie w IZOLOWANYM
         * procesie/kontenerze o OGRANICZONYCH uprawnieniach - jaki jest
         * cel takiej izolacji (ograniczenie "blast radius" nawet przy
         * udanym ataku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareJavaSerializationRiskAcrossVersions {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: zbadaj, jak zmienialo sie podejscie do tego
         * problemu w kolejnych wersjach Javy (JEP 290 w Javie 9, dalsze
         * ulepszenia) - dlaczego SPOLECZNOSC Javy dlugo dyskutowala o
         * CALKOWITYM WYCOFANIU natywnej serializacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildAutomatedTestDetectingUnsafeDeserialization {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj "test" (w `main()`) probujacy zdeserializowac liste
         * roznych klas (dozwolonych i niedozwolonych) wobec ustalonego
         * filtra - wypisz PASS/FAIL dla kazdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementDefenseInDepthWithFilterAndLogging {
        /*
         * 🧪 Zadanie 29:
         * Polacz filtr (Zadanie 5), walidacje po odczycie (Zadanie 22) i
         * logowanie odrzuconych prob (Zadanie 19) w 1 spojna, kompletna
         * warstwe ochrony deserializacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSecureDeserializationServiceDemo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "serwis" przyjmujacy dane binarne (symulowane
         * jako `byte[]`) i bezpiecznie je deserializujacy z PELNA
         * ochrona (Zadanie 29) - zweryfikuj co najmniej 4 scenariusze
         * (dozwolona klasa, niedozwolona klasa, uszkodzone dane,
         * nieprawidlowy stan po walidacji) z czytelnym logiem.
         */
        public static void main(String[] args) { }
    }
}
