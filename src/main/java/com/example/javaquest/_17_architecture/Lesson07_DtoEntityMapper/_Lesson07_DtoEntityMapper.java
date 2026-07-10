package com.example.javaquest._17_architecture.Lesson07_DtoEntityMapper;

import java.time.LocalDate;

public class _Lesson07_DtoEntityMapper {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: DTO, ENCJA I MAPPER - PERSPEKTYWA ARCHITEKTONICZNA ===");

        /*
         * ============================================================
         * 📦 MECHANIKE JUZ ZNASZ - TU: DLACZEGO TA GRANICA MA ZNACZENIE
         * ============================================================
         * - Czym jest DTO, czym rozni sie od encji domenowej, i JAK
         *   napisac mapper miedzy nimi - poznales dokladnie w
         *   `_09_jdbc/Lesson19_Dto` i `Lesson20_Mapper`.
         * - Ta lekcja NIE powtarza tamtej mechaniki - pyta o cos innego:
         *   DLACZEGO ta granica (DTO na zewnatrz, encja wewnatrz) jest
         *   DECYZJA ARCHITEKTONICZNA (Lesson01: trudna/kosztowna do
         *   odwrocenia, jesli jej zabraknie), a nie tylko "dodatkowa
         *   klasa do napisania".
         */
        System.out.println("Mechanike DTO/Mapper znasz z _09_jdbc - tu: dlaczego ta granica ma znaczenie ARCHITEKTONICZNE.");

        /*
         * ============================================================
         * 🔹 PROBLEM 1: SPRZEZENIE KONTRAKTU API ZE SCHEMATEM BAZY DANYCH
         * ============================================================
         * - Jesli zwracasz ENCJE wprost jako odpowiedz API, to KAZDA
         *   zmiana w schemacie bazy (przemianowanie kolumny, dodanie
         *   pola technicznego) NATYCHMIAST zmienia PUBLICZNY kontrakt,
         *   ktorego uzywaja zewnetrzni klienci - mimo ze z ich
         *   perspektywy NIC sie NIE powinno zmienic.
         * - To jest DOKLADNIE ten sam problem co "przeciekajace warstwy"
         *   z Lesson03, ale na SAMEJ GRANICY systemu (miedzy Twoja
         *   aplikacja a swiatem zewnetrznym) - i BEZPOSREDNIE zastosowanie
         *   Dependency Rule z Lesson10 (zobaczysz ja formalnie za 3
         *   lekcje): szczegol (schemat bazy) NIE MOZE dyktowac ksztaltu
         *   stabilnego kontraktu.
         */
        demonstrateEntityLeakingAsApiResponse();

        /*
         * ============================================================
         * 🔍 PROBLEM 2: NADMIAROWE/WRAZLIWE DANE (OVER-FETCHING)
         * ============================================================
         * - Encja domenowa ZWYKLE zawiera WIECEJ pol, niz jakikolwiek
         *   1 klient API kiedykolwiek potrzebuje - hash hasla, wewnetrzne
         *   notatki administracyjne, ID recznie tworzacego rekord
         *   pracownika.
         * - `_09_jdbc/Lesson19` juz pokazal to na przykladzie hasla - tu
         *   ROZSZERZAMY: problem dotyczy KAZDEGO pola "wewnetrznego",
         *   nie tylko oczywistych sekretow (haslo). Latwo przeoczyc pole
         *   typu "notatka wewnetrzna od supportu", ktore NIE POWINNO
         *   nigdy trafic do odpowiedzi API.
         */
        demonstrateOverFetchingBeyondObviousSecrets();

        /*
         * ============================================================
         * 🔹 PROBLEM 3: KSZTALT ENCJI RZADKO PASUJE 1:1 DO POTRZEB API
         * ============================================================
         * - Konkretny endpoint API czesto potrzebuje danych Z KILKU
         *   encji naraz (np. "podsumowanie zamowienia" laczy dane
         *   Zamowienia i Klienta) - albo ODWROTNIE, TYLKO WYCINKA 1
         *   encji.
         * - DTO pozwala zaprojektowac ksztalt danych DOKLADNIE pod
         *   KONKRETNY przypadek uzycia API (patrz tez Lesson06: kazdy
         *   "widok" jest lokalnym tlumaczeniem pod konkretne potrzeby
         *   odbiorcy) - encja NIE MUSI (i nie powinna) probowac
         *   "obslugiwac wszystkich mozliwych klientow naraz".
         */
        demonstrateDtoCombiningMultipleEntities();

        /*
         * ============================================================
         * 🔍 PROBLEM 4: ADNOTACJE SERIALIZACJI ZANIECZYSZCZAJACE MODEL DOMENOWY
         * ============================================================
         * - Gdyby encja domenowa musiala byc BEZPOSREDNIO serializowana
         *   do JSON (np. przez adnotacje typu `@JsonProperty`), jej klasa
         *   zaczelaby miec 2 ROZNE powody do zmiany naraz: zmiana reguly
         *   biznesowej ORAZ zmiana formatu API - naruszenie SRP
         *   (`_16_clean_code/Lesson07`) na poziomie CALEJ klasy.
         * - DTO przejmuje CALA odpowiedzialnosc za "jak to wyglada na
         *   zewnatrz" - encja zostaje WYLACZNIE odpowiedzialna za logike
         *   biznesowa (i moze pozostac "bogata", Lesson05, bez obawy o
         *   przypadkowa serializacje metod biznesowych).
         */
        System.out.println("\nBez DTO: encja ma 2 powody do zmiany (logika biznesowa + format API) - narusza SRP (_16_clean_code/Lesson07).");

        /*
         * ============================================================
         * 📌 KOSZT: MAPOWANIE TO BOILERPLATE - CZY ZAWSZE SIE OPLACA?
         * ============================================================
         * - Pisanie mappera dla KAZDEGO DTO to realny, powtarzalny koszt
         *   (stad narzedzia jak MapStruct z `_13_libraries/Lesson21-22`,
         *   automatyzujace ta mechanike).
         * - Na GRANICY PUBLICZNEGO API (kontrakt z zewnetrznymi klientami)
         *   ten koszt PRAWIE ZAWSZE sie oplaca - stabilnosc kontraktu jest
         *   zbyt wazna. WEWNATRZ aplikacji, miedzy blisko wspolpracujacymi
         *   warstwami (patrz "luzne warstwowanie", Lesson03), czasem
         *   swiadomie akceptuje sie mniejsza sciezke (np. Service zwraca
         *   encje bezposrednio do WEWNETRZNEGO kontrolera tej samej
         *   aplikacji) - to swiadomy kompromis, warty ADR (Lesson02).
         */
        System.out.println("\nKoszt mapowania PRAWIE ZAWSZE oplaca sie na granicy PUBLICZNEGO API - wewnatrz aplikacji to swiadomy kompromis.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Mechanike DTO/Mapper znasz z `_09_jdbc` - ta lekcja dodaje
         *   PERSPEKTYWE ARCHITEKTONICZNA: dlaczego ta granica ma
         *   znaczenie.
         * - Encja wprost jako odpowiedz API sprzega kontrakt API ze
         *   schematem bazy - zmiana wewnetrzna lamie zewnetrznych klientow.
         * - DTO chroni przed nadmiarowymi/wrazliwymi danymi (nie tylko
         *   oczywistymi sekretami).
         * - DTO pozwala dopasowac ksztalt danych do KONKRETNEGO przypadku
         *   uzycia API, niezaleznie od struktury encji.
         * - Bez DTO encja ma 2 powody do zmiany naraz - narusza SRP.
         * - W kolejnej lekcji (Lesson08): wersjonowanie API - co sie
         *   dzieje z tym samym DTO, gdy kontrakt MUSI sie zmienic w
         *   sposob, ktory zlamie istniejacych klientow.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void demonstrateEntityLeakingAsApiResponse() {
        System.out.println("\n=== PROBLEM: ENCJA ZWRACANA WPROST JAKO ODPOWIEDZ API ===");

        UserEntity entity = new UserEntity(1L, "ala@example.com", "hash$2a$10$abcdef", "VIP-flagged-by-support", LocalDate.of(2024, 1, 15));

        System.out.println("[ZLA] 'Odpowiedz API' = encja wprost: " + entity);
        System.out.println("-> Klient API widzi passwordHash i wewnetrzna notatke supportu - WYCIEK danych!");

        UserResponseDto dto = toResponseDto(entity);
        System.out.println("[DOBRA] Odpowiedz API przez DTO: " + dto);
        System.out.println("-> Klient widzi TYLKO to, co powinien - passwordHash/internalNote NIE ISTNIEJA w DTO.");
    }

    /** Encja domenowa - zawiera pola WEWNETRZNE, ktorych API nie powinno nigdy ujawnic. */
    record UserEntity(long id, String email, String passwordHash, String internalSupportNote, LocalDate registeredAt) {
    }

    /** DTO wyjsciowe - TYLKO to, co klient API powinien zobaczyc. */
    record UserResponseDto(long id, String email, String memberSince) {
    }

    private static UserResponseDto toResponseDto(UserEntity entity) {
        return new UserResponseDto(entity.id(), entity.email(), entity.registeredAt().toString());
    }

    private static void demonstrateOverFetchingBeyondObviousSecrets() {
        System.out.println("\n=== NIE TYLKO HASLO - KAZDE POLE WEWNETRZNE JEST RYZYKIEM ===");

        UserEntity entity = new UserEntity(2L, "jan@example.com", "hash$2a$10$xyz123", "Zglaszal reklamacje 3x - obserwowac", LocalDate.of(2023, 6, 1));

        System.out.println("Pole 'internalSupportNote' to NIE haslo, ale RowNIEZ nie powinno opuszczac systemu:");
        System.out.println("  " + entity.internalSupportNote());
        System.out.println("DTO (" + toResponseDto(entity) + ") pomija je AUTOMATYCZNIE - bo DTO w ogole NIE MA takiego pola.");
    }

    private static void demonstrateDtoCombiningMultipleEntities() {
        System.out.println("\n=== DTO LACZACY DANE Z KILKU ENCJI - KSZTALT POD KONKRETNY PRZYPADEK UZYCIA ===");

        UserEntity user = new UserEntity(3L, "celina@example.com", "hash$2a$10$qqq", "brak uwag", LocalDate.of(2022, 3, 10));
        OrderEntity order = new OrderEntity(501L, 3L, 249.99, "SHIPPED");

        // Endpoint "podsumowanie zamowienia klienta" potrzebuje danych z OBU encji naraz -
        // ZADNA z encji osobno nie ma takiego ksztaltu, i nie powinna go miec.
        OrderSummaryDto summary = new OrderSummaryDto(order.orderId(), user.email(), order.totalAmount(), order.status());
        System.out.println("OrderSummaryDto (laczy UserEntity + OrderEntity): " + summary);
        System.out.println("-> Ani UserEntity, ani OrderEntity osobno nie maja TAKIEGO ksztaltu - DTO go tworzy 'na miare'.");
    }

    record OrderEntity(long orderId, long userId, double totalAmount, String status) {
    }

    record OrderSummaryDto(long orderId, String customerEmail, double totalAmount, String status) {
    }
}
