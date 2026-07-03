package com.example.javaquest._04_io.Lesson17_SerialVersionUID;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class _Lesson17_SerialVersionUID {

    /*
     * Klasa z JAWNIE zadeklarowanym serialVersionUID – ZALECANE podejście.
     * Wartość "1L" jest umowna – to po prostu "numer wersji" struktury klasy.
     */
    static class Person implements Serializable {
        static final long serialVersionUID = 1L;

        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    /*
     * Ta sama klasa, ale BEZ jawnie zadeklarowanego serialVersionUID.
     * JVM sam wyliczy tę wartość na podstawie struktury klasy (nazwa,
     * pola, implementowane interfejsy, sygnatury metod...).
     */
    static class PersonNoUid implements Serializable {
        String name;
        int age;

        PersonNoUid(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST serialVersionUID?
         * ============================================================
         * serialVersionUID to unikalny "numer wersji" klasy implementującej
         * Serializable. Służy do sprawdzania ZGODNOŚCI między:
         * - klasą użytą do ZAPISANIA obiektu (nadawca / stara wersja programu)
         * - klasą użytą do ODCZYTANIA obiektu (odbiorca / nowa wersja programu)
         *
         * Podczas deserializacji ObjectInputStream porównuje:
         *   serialVersionUID zapisany w strumieniu bajtów
         *   vs
         *   serialVersionUID aktualnie załadowanej klasy w JVM
         *
         * Jeśli się RÓŻNIĄ → java.io.InvalidClassException.
         * Jeśli są TAKIE SAME → JVM próbuje wczytać obiekt (nawet jeśli
         * struktura klasy nieco się zmieniła – np. dodano nowe pole).
         *
         * Deklaruje się go tak:
         *   private static final long serialVersionUID = 1L;
         *
         * (modyfikator dostępu może być dowolny, ale MUSI być static final long)
         */

        /*
         * ============================================================
         * 🔹 SERIALIZACJA I DESERIALIZACJA – PRZYPOMNIENIE
         * ============================================================
         */

        Person original = new Person("Anna", 30);
        byte[] bytes = serialize(original);
        Person restored = (Person) deserialize(bytes);

        System.out.println("Oryginał:   " + original);
        System.out.println("Odtworzony: " + restored);

        /*
         * ============================================================
         * 🔍 CO SIĘ DZIEJE, GDY BRAKUJE serialVersionUID?
         * ============================================================
         * Gdy klasa implementuje Serializable, ale NIE deklaruje jawnie
         * serialVersionUID, kompilator (przy włączonym -Xlint:serial)
         * wypisuje ostrzeżenie, np.:
         *
         *   warning: [serial] serializable class PersonNoUid has no definition
         *   of serialVersionUID
         *
         * JVM w takiej sytuacji SAM WYLICZA tę wartość – na podstawie
         * skomplikowanego hasza ze struktury klasy (nazwa, modyfikatory,
         * lista pól, sygnatury metod, implementowane interfejsy...).
         * Możemy podejrzeć wyliczoną wartość programowo:
         */

        long uidPerson = ObjectStreamClass.lookup(Person.class).getSerialVersionUID();
        long uidPersonNoUid = ObjectStreamClass.lookup(PersonNoUid.class).getSerialVersionUID();

        System.out.println("\nPerson.serialVersionUID (jawny):        " + uidPerson);        // 1
        System.out.println("PersonNoUid.serialVersionUID (auto-gen): " + uidPersonNoUid);   // duża, "losowo" wyglądająca liczba

        /*
         * ⚠️ PROBLEM: auto-wyliczony numer ZALEŻY OD STRUKTURY KLASY.
         * Jeśli w kolejnej wersji programu dodasz/usuniesz pole, zmienisz
         * typ pola, dodasz metodę albo nawet PRZEKOMPILUJESZ innym
         * kompilatorem – wyliczona wartość może się ZMIENIĆ.
         *
         * Skutek: stare dane zserializowane starą wersją klasy PRZESTANĄ
         * być odczytywalne nową wersją klasy, mimo że logicznie dane
         * są kompatybilne (np. dodałeś tylko jedno nowe pole)!
         *
         * ✅ DLATEGO zawsze warto jawnie deklarować serialVersionUID – dzięki
         * temu KONTROLUJESZ, kiedy wersje są (nie)zgodne, zamiast polegać
         * na "magicznym" haszu wyliczanym przez JVM.
         */

        /*
         * ============================================================
         * ❌ SYMULACJA InvalidClassException – NIEZGODNOŚĆ WERSJI
         * ============================================================
         * Nie da się w jednym pliku .java mieć równocześnie dwóch RÓŻNYCH
         * wersji tej samej klasy (Java pozwala na jedną definicję danej
         * nazwy klasy). Możemy jednak zasymulować DOKŁADNIE ten sam efekt,
         * jaki wystąpiłby w praktyce:
         *
         *   1. Zapisujesz obiekt Person na dysku wersją v1 aplikacji
         *      (serialVersionUID = 1).
         *   2. Wdrażasz nową wersję v2 aplikacji, w której zmieniła się
         *      struktura klasy Person i (przez brak jawnego UID albo przez
         *      świadomą zmianę) serialVersionUID jest już INNY, np. 2.
         *   3. Próbujesz odczytać STARY plik nową wersją v2 klasy.
         *
         * Poniżej ręcznie "preparujemy" surowe bajty strumienia serializacji
         * tak, jakby zostały zapisane przez inną wersję klasy Person
         * (z innym serialVersionUID) – dokładnie to widziałby JVM podczas
         * próby odczytu niekompatybilnych, starych danych.
         */

        byte[] tamperedBytes = simulateDifferentClassVersion(bytes, Person.class.getName());

        try {
            deserialize(tamperedBytes);
            System.out.println("Nie powinno się to zdarzyć – dane powinny być niezgodne!");
        } catch (InvalidClassException e) {
            System.out.println("\nZłapano InvalidClassException: " + e.getMessage());
            // np.: Person; local class incompatible: stream classdesc
            //      serialVersionUID = 2, local class serialVersionUID = 1
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - serialVersionUID = "numer wersji" klasy implementującej Serializable
         * - deserializacja porówuje UID z pliku/strumienia z UID aktualnej klasy
         * - RÓŻNE UID → java.io.InvalidClassException ("local class incompatible")
         * - brak jawnego UID → JVM auto-generuje go na podstawie struktury klasy
         *   → NIESTABILNE między wersjami (nawet drobna zmiana klasy może
         *     zmienić auto-wyliczoną wartość i zepsuć wsteczną kompatybilność)
         * - ✅ ZAWSZE deklaruj jawnie:
         *       private static final long serialVersionUID = 1L;
         *   i zwiększaj go świadomie tylko wtedy, gdy CELOWO chcesz zerwać
         *   kompatybilność ze starymi danymi
         */
    }

    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        return baos.toByteArray();
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return ois.readObject();
        }
    }

    /*
     * Format strumienia java.io.Serializable zapisuje opis klasy (TC_CLASSDESC)
     * jako: [2-bajtowa długość nazwy][bajty nazwy klasy][8-bajtowy long UID]...
     * Modyfikujemy ostatni bajt 8-bajtowego UID, żeby zasymulować, że strumień
     * pochodzi z INNEJ wersji tej samej klasy (z innym serialVersionUID),
     * a lokalnie załadowana klasa Person nadal deklaruje serialVersionUID = 1L.
     */
    private static byte[] simulateDifferentClassVersion(byte[] original, String className) {
        byte[] copy = Arrays.copyOf(original, original.length);
        byte[] nameBytes = className.getBytes(StandardCharsets.UTF_8);

        int nameIndex = indexOf(copy, nameBytes);
        if (nameIndex < 0) {
            throw new IllegalStateException("Nie znaleziono nazwy klasy w strumieniu serializacji");
        }

        int uidOffset = nameIndex + nameBytes.length; // UID leży zaraz po nazwie klasy
        copy[uidOffset + 7] ^= 0xFF; // zmieniamy najmłodszy bajt UID -> inna wartość liczbowa
        return copy;
    }

    private static int indexOf(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i <= haystack.length - needle.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }
}
