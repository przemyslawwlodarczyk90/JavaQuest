package com.example.javaquest._09_jdbc.Lesson19_Dto;

public class _Exercises_Lesson19_Dto {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineInputDto {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj DTO WEJŚCIOWE record CreateProductRequest(String name,
         * BigDecimal price, String internalNote). Utwórz jedną instancję
         * i wypisz jej pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DefineOutputDtoWithFewerFields {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj DTO WYJŚCIOWE record ProductResponse(long id, String
         * name, BigDecimal price) - celowo BEZ pola internalNote z
         * Zadania 1 (dane wewnętrzne, których klient nie powinien widzieć).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_EntityWithInternalFieldMappedToResponse {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj encję domenową record Product(long id, String name,
         * BigDecimal price, BigDecimal costPrice) - costPrice to dana
         * WEWNĘTRZNA (koszt zakupu, nie do pokazania klientowi). Napisz
         * metodę toResponse(Product) zwracającą ProductResponse (z
         * Zadania 2) BEZ costPrice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DemonstrateLeakIfUsingEntityDirectly {
        /*
         * 🧪 Zadanie 4:
         * Wypisz WSZYSTKIE pola encji Product z Zadania 3 (łącznie z
         * costPrice) - to, co by "wyciekło", gdybyśmy zwrócili encję
         * bezpośrednio z API. Porównaj z wypisaniem ProductResponse
         * (przez toResponse) - który nie ma tego problemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_InputDtoWithTemporarySensitiveField {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj DTO WEJŚCIOWE record CreateAccountRequest(String
         * login, String rawPin) - rawPin to dana TYMCZASOWA (jawny PIN
         * od użytkownika). Napisz metodę tworzącą encję Account(String
         * login, String pinHash) z "zahaszowanym" (symulowanym) PIN-em,
         * NIGDY nie przechowującą rawPin.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ValidateInputDtoBeforeMapping {
        /*
         * 🧪 Zadanie 6:
         * Na DTO CreateProductRequest (Zadanie 1) napisz metodę validate(),
         * która rzuca IllegalArgumentException, jeśli price <= 0 - walidacja
         * DZIEJE SIĘ POZA samym DTO (w osobnej metodzie/klasie), nie
         * jako logika WEWNĄTRZ rekordu. Przetestuj dla poprawnych i
         * niepoprawnych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareInputEntityOutputSideBySide {
        /*
         * 🧪 Zadanie 7:
         * Dla JEDNEGO przepływu (request -> entity -> response) wypisz
         * WSZYSTKIE trzy obiekty jeden pod drugim, z jasnym opisem który
         * to etap - zilustruj, jak pola się zmieniają między etapami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SummaryDtoWithEvenFewerFields {
        /*
         * 🧪 Zadanie 8:
         * Zdefiniuj record ProductListItemResponse(long id, String name) -
         * jeszcze BARDZIEJ okrojone DTO niż ProductResponse (Zadanie 2),
         * przeznaczone do WIDOKU LISTY (bez ceny). Napisz metodę
         * toListItem(Product) mapującą encję na ten skromniejszy DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DifferentResponseDtoPerAudience {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj DWA różne DTO wyjściowe dla TEJ SAMEJ encji Product:
         * CustomerProductResponse(long id, String name, BigDecimal price)
         * i AdminProductResponse(long id, String name, BigDecimal price,
         * BigDecimal costPrice, BigDecimal margin) - druga wersja
         * (dla administratorów) zawiera WIĘCEJ danych, w tym wyliczoną
         * marżę. Zademonstruj zmapowanie TEJ SAMEJ encji na OBA DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DtoImmutabilityAsRecord {
        /*
         * 🧪 Zadanie 10:
         * Zademonstruj, że DTO zdefiniowane jako record (np.
         * ProductResponse) NIE MA setterów - wszystkie pola są
         * niemodyfikowalne po utworzeniu. Wypisz komentarz (println)
         * wyjaśniający, dlaczego niemutowalność jest dobrą praktyką dla
         * DTO (bezpieczne przekazywanie między warstwami/wątkami).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullRoundTripCreateProductFlow {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj pełny przepływ dla tworzenia produktu: CreateProductRequest
         * -> metoda createProductFromRequest(request) -> Product (encja,
         * z wygenerowanym id) -> metoda toResponse(product) ->
         * ProductResponse. Wypisz wynik każdego etapu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AntiPatternReusingEntityAsDto {
        /*
         * 🧪 Zadanie 12:
         * Zademonstruj ANTY-WZORZEC: użyj JEDNEJ klasy Product (z
         * costPrice) JEDNOCZESNIE jako danych wejściowych (tworzenie) I
         * jako "odpowiedzi" zwracanej z metody. Wypisz, że
         * costPrice WYCIEKŁOBY do klienta, gdyby to było prawdziwe API -
         * skomentuj (println), czym różni się to od podejścia z
         * dedykowanymi DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UpdateRequestDtoWithOptionalFields {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj record UpdateProductRequest(String nameOrNull,
         * BigDecimal priceOrNull) - DTO do AKTUALIZACJI, gdzie pola MOGĄ
         * być null (oznacza "nie zmieniaj tego pola"). Napisz metodę
         * applyUpdate(Product existing, UpdateProductRequest request)
         * zwracającą NOWY Product z zaktualizowanymi TYLKO niepustymi
         * polami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FlatDtoCombiningTwoEntities {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj encje Order(long id, long customerId, BigDecimal
         * total) i Customer(long id, String name). Zdefiniuj DTO
         * OrderSummaryResponse(long orderId, String customerName,
         * BigDecimal total) łączące pola z OBU encji w jeden, płaski
         * DTO. Napisz metodę mapującą (Order, Customer) -> OrderSummaryResponse.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DedicatedMapperClassSeparateFromDto {
        /*
         * 🧪 Zadanie 15:
         * Napisz klasę ProductMapper z metodami statycznymi
         * toEntity(CreateProductRequest) i toResponse(Product) - CAŁA
         * logika konwersji żyje w tej klasie, a nie w samych DTO/encji
         * (żadnych metod konwertujących WEWNĄTRZ CreateProductRequest
         * czy Product).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DedicatedValidatorClassWithMultipleRules {
        /*
         * 🧪 Zadanie 16:
         * Napisz klasę CreateProductRequestValidator z metodą
         * validate(CreateProductRequest), zwracającą List<String> z
         * WSZYSTKIMI naruszonymi regułami (np. "nazwa nie moze byc pusta",
         * "cena musi byc dodatnia") - NIE przerywając na pierwszym
         * naruszeniu. Przetestuj na żądaniu z 2 naruszeniami naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultipleInputDtosMapToSameEntity {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj DWA różne DTO wejściowe: RegisterViaEmailRequest(String
         * email, String plainPassword) i
         * RegisterViaSocialLoginRequest(String provider, String
         * externalId, String displayName). Napisz DWIE metody mapujące
         * (każda dla swojego DTO) na TĘ SAMĄ encję User, z RÓŻNĄ logiką
         * wypełniania pól (np. brak hasła dla logowania społecznościowego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DtoWithRenamedFieldVsEntity {
        /*
         * 🧪 Zadanie 18:
         * Zdefiniuj encję User(long id, String firstName) i DTO
         * UserResponse(long id, String name) - pole "name" w DTO
         * odpowiada polu "firstName" w encji (INNA nazwa). Napisz
         * mapper wyraźnie pokazujący to mapowanie i skomentuj, że DTO
         * NIE MUSI nazywać pól identycznie jak encja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_NestedDtoFromNestedEntityGraph {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj encje Order (z List<OrderItem>) i OrderItem (product
         * name, quantity, price). Zdefiniuj DTO OrderResponse(long id,
         * List<OrderItemResponse> items) i OrderItemResponse(String
         * productName, int quantity). Napisz mapper budujący ZAGNIEŻDŻONY
         * DTO z zagnieżdżonej encji (mapowanie listy przez Stream).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RequestCarriesTransientDataNotStoredDirectly {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj DTO UploadAvatarRequest(long userId, byte[] rawImageData) -
         * dane WEJŚCIOWE, których encja NIGDY nie przechowuje w takiej
         * postaci. Napisz metodę processUpload(request), która symuluje
         * "przetworzenie" (np. policzenie rozmiaru w bajtach) i tworzy
         * encję User z polem avatarUrl (String) - WYPROWADZONYM, nie
         * surowym.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLayeredFlowWithoutDatabase {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny, warstwowy przepływ (bez bazy danych - encje
         * "zapisywane" do List<Order> w pamięci): metoda
         * handleCreateOrder(CreateOrderRequest), która WALIDUJE żądanie,
         * MAPUJE je na Order (z wygenerowanym id), "zapisuje" do listy,
         * a na końcu MAPUJE zapisaną encję na OrderResponse i ją zwraca.
         * Zademonstruj wywołanie dla 2 różnych żądań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_VersionedResponseDtos {
        /*
         * 🧪 Zadanie 22:
         * Zdefiniuj TĘ SAMĄ encję User (id, name, avatarUrl) oraz DWA
         * DTO wyjściowe: UserResponseV1(long id, String name) i
         * UserResponseV2(long id, String name, String avatarUrl).
         * Zademonstruj zmapowanie JEDNEJ encji na OBA DTO -
         * ilustrując, jak API może "ewoluować" bez zmiany modelu domenowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MaskedFieldInPublicDto {
        /*
         * 🧪 Zadanie 23:
         * Zdefiniuj DTO PublicUserResponse(long id, String maskedEmail).
         * Napisz metodę maskEmail(String email) (np. "jan@example.com"
         * -> "j***@example.com") i użyj jej w mapperze
         * toPublicResponse(User) - logika maskowania żyje w MAPPERZE, nie
         * w DTO ani w encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FixOverexposureBugInMapper {
        /*
         * 🧪 Zadanie 24:
         * Napisz "wadliwy" mapper buggyToResponse(User), który przez
         * błąd (np. wywołanie user.toString() zawierającego
         * passwordHash) przypadkowo umieszcza dane hasła w zwróconym
         * String/DTO. Zidentyfikuj błąd (println wyjaśnienie), a potem
         * napisz POPRAWNY mapper fixedToResponse(User) zwracający
         * WYŁĄCZNIE bezpieczne pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BulkOperationRequestResponseDtos {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj record BulkCreateUsersRequest(List<CreateUserRequest>
         * requests) i record BulkCreateUsersResponse(List<UserResponse>
         * created, List<String> errors). Napisz metodę processBulk(request),
         * która przetwarza KAŻDE żądanie osobno (łapiąc wyjątki walidacji
         * dla pojedynczych elementów, ZAMIAST przerywać całość) i zwraca
         * BulkCreateUsersResponse z częściowym sukcesem (część utworzona,
         * część z błędami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImmutableCollectionInsideResponseDto {
        /*
         * 🧪 Zadanie 26:
         * W DTO OrderResponse (z Zadania 19) upewnij się, że pole items
         * jest zbudowane przez List.copyOf(...) (niemodyfikowalna kopia).
         * Zademonstruj, że próba items.add(...) na zwróconym DTO rzuca
         * UnsupportedOperationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ValidationErrorResponseDto {
        /*
         * 🧪 Zadanie 27:
         * Zdefiniuj record ValidationErrorResponse(List<String> errors).
         * Napisz metodę handleCreateProduct(CreateProductRequest), która
         * dla NIEPOPRAWNEGO żądania (np. pusta nazwa I ujemna cena)
         * zwraca ValidationErrorResponse z WSZYSTKIMI błędami (zamiast
         * rzucać wyjątek), a dla poprawnego żądania zwraca zwykły
         * ProductResponse. Zademonstruj oba przypadki (użyj Object jako
         * typu zwracanego albo prostego wrappera z flagą sukcesu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_EntityEvolvesWithoutBreakingOldDto {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj EWOLUCJĘ encji User: dodaj NOWE pole loyaltyPoints
         * (int) do encji, BEZ dotykania istniejącego DTO UserResponse
         * (bez nowego pola). Zademonstruj, że stary mapper toResponse(User)
         * kompiluje się i działa BEZ ŻADNYCH zmian, mimo rozszerzenia encji -
         * kluczowa zaleta granicy DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TwoWayMappingTestHarnessWithAssertions {
        /*
         * 🧪 Zadanie 29:
         * Dla 3 przykładowych żądań CreateUserRequest wykonaj pełny
         * przepływ request -> entity -> response, wypisując wszystkie 3
         * etapy dla każdego z nich. Po każdym przepływie wykonaj proste
         * "asercje" (println "PASS"/"FAIL"), sprawdzające, że
         * WYNIKOWY response NIE zawiera żadnego pola związanego z hasłem
         * (np. przez sprawdzenie, że klasa UserResponse w ogóle nie
         * deklaruje takiego pola/gettera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneRegistrationAndProfileFeature {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletną mini-funkcjonalność "rejestracja + widok
         * profilu" z DTO: CreateUserRequest (wejście), User (encja - z
         * passwordHash i createdAt), UserResponse (wyjście publiczne -
         * minimalne dane) oraz AdminUserResponse (wyjście dla administratora
         * - więcej danych, ale WCIĄŻ bez surowego hasła/hasha). Zaimplementuj
         * mapper dla WSZYSTKICH kombinacji i zademonstruj pełny przepływ:
         * rejestracja, a potem odczyt TEGO SAMEGO użytkownika przez DWIE
         * różne "role" (zwykly uzytkownik i administrator), każda widząca
         * inny zestaw danych.
         */
        public static void main(String[] args) { }
    }
}
