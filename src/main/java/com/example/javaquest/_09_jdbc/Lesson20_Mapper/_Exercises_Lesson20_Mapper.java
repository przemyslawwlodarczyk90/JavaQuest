package com.example.javaquest._09_jdbc.Lesson20_Mapper;

public class _Exercises_Lesson20_Mapper {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MapperDirectionOneResultSetToEntity {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l20ex01;DB_CLOSE_DELAY=-1" utwórz tabelę
         * "products" (id BIGINT AUTO_INCREMENT, name VARCHAR(100), price
         * DECIMAL(10,2)) z 2 wierszami. Zdefiniuj encję Product(long id,
         * String name, BigDecimal price) i napisz w klasie ProductMapper
         * metodę toEntity(ResultSet) (kierunek 1). Przetestuj na jednym
         * wierszu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MapperDirectionTwoBindParameters {
        /*
         * 🧪 Zadanie 2:
         * W klasie ProductMapper napisz metodę bindParameters(PreparedStatement,
         * Product) (kierunek 2 - obiekt -> parametry SQL). Użyj jej do
         * wstawienia produktu przez INSERT (name, price), a potem
         * zweryfikuj SELECT-em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MapperDirectionThreeDtoToEntity {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj record CreateProductRequest(String name, BigDecimal
         * price). W ProductMapper napisz metodę
         * fromCreateRequest(CreateProductRequest) -> Product (kierunek 3,
         * id=0 tymczasowe). Przetestuj konwersję.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MapperDirectionFourEntityToDto {
        /*
         * 🧪 Zadanie 4:
         * Zdefiniuj record ProductResponse(long id, String name,
         * BigDecimal price). W ProductMapper napisz metodę toDto(Product)
         * (kierunek 4). Przetestuj konwersję zmapowanego wcześniej Product.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FullRoundTripAllFourDirections {
        /*
         * 🧪 Zadanie 5:
         * Połącz WSZYSTKIE 4 kierunki z Zadań 1-4 w jednym przepływie:
         * CreateProductRequest -> Product (kierunek 3) -> INSERT z
         * bindParameters (kierunek 2) -> SELECT + toEntity (kierunek 1)
         * -> toDto (kierunek 4). Wypisz wynik każdego etapu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MapperForBigDecimalField {
        /*
         * 🧪 Zadanie 6:
         * Na tabeli "products" (price DECIMAL) przetestuj OSOBNO
         * poprawność mapowania kierunku 1 (toEntity) i kierunku 2
         * (bindParameters) DLA POLA price - wstaw wartość przez mapper,
         * odczytaj przez mapper i porównaj (println), że wartości są
         * identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MapperForDateField {
        /*
         * 🧪 Zadanie 7:
         * Na tabeli "products" dodaj kolumnę added_on DATE. Rozszerz
         * Product o pole LocalDate addedOn. Zaktualizuj toEntity() i
         * bindParameters() ProductMapper, żeby obsługiwały konwersję
         * java.sql.Date <-> java.time.LocalDate. Przetestuj pełny round
         * trip dla tego pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MapperForBooleanField {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do Product pole boolean inStock. Zaktualizuj toEntity()
         * i bindParameters() o obsługę tej kolumny (BOOLEAN). Przetestuj
         * wstawienie i odczyt produktu z inStock=false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MapperInverseConsistencyCheck {
        /*
         * 🧪 Zadanie 9:
         * Zmapuj CreateProductRequest na Product (kierunek 3), wstaw
         * przez bindParameters (kierunek 2), odczytaj z bazy przez
         * toEntity (kierunek 1) i porównaj (println pola po polu) z
         * ORYGINALNYM Product z kierunku 3 - potwierdź zgodność (poza
         * id, które nadaje baza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BasicUserMapperInsertAndList {
        /*
         * 🧪 Zadanie 10:
         * Analogicznie do UserMapper z lekcji, wstaw 2 użytkowników przez
         * fromCreateRequest+bindParameters, odczytaj WSZYSTKICH przez
         * toEntity w pętli, a potem zmapuj każdego na UserResponse przez
         * toDto. Wypisz listę odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ProductMapperExcludingInternalCostPrice {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz Product o pole WEWNĘTRZNE costPrice (BigDecimal, koszt
         * zakupu, NIE do pokazania klientowi). ProductMapper.toDto()
         * MUSI zwracać ProductResponse BEZ tego pola. Zademonstruj pełny
         * przepływ i potwierdź, że ProductResponse nie ma costPrice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BindParametersForUpdateVariant {
        /*
         * 🧪 Zadanie 12:
         * Dodaj do ProductMapper metodę bindParametersForUpdate(PreparedStatement,
         * Product) z INNĄ kolejnością/zestawem kolumn niż insertowa
         * wersja (np. "UPDATE products SET name=?, price=? WHERE id=?" -
         * id na KOŃCU, w przeciwieństwie do insertu). Przetestuj
         * aktualizację istniejącego produktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MapperToDtoListReusingToDto {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę ProductMapper.toDtoList(List<Product>) wywołującą
         * toDto() dla KAŻDEGO elementu (Stream.map). Przetestuj na
         * liście 5 produktów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ToEntityReusedInFindByIdAndFindAll {
        /*
         * 🧪 Zadanie 14:
         * Napisz findById(Connection, long) i findAll(Connection) - OBIE
         * metody używają TEJ SAMEJ metody ProductMapper.toEntity(ResultSet),
         * bez duplikacji logiki mapującej. Przetestuj obie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MapperHandlesNullableColumnCorrectly {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do "products" opcjonalną kolumnę discount DECIMAL (może
         * być NULL). Zaktualizuj Product/ProductResponse i mapper, żeby
         * poprawnie obsługiwały NULL w OBU kierunkach (1 i 4).
         * Zademonstruj na produkcie z discount=NULL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MergeStyleFromUpdateRequest {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj record UpdateProductRequest(String nameOrNull,
         * BigDecimal priceOrNull). W ProductMapper napisz metodę
         * applyUpdate(Product existing, UpdateProductRequest request) ->
         * Product (nowy obiekt z NADPISANYMI tylko niepustymi polami,
         * podobnie jak Lesson19). Przetestuj częściową aktualizację
         * (tylko name).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TwoDifferentMappersConsistentStructure {
        /*
         * 🧪 Zadanie 17:
         * Napisz OrderMapper (dla encji Order) analogiczny strukturalnie
         * do ProductMapper (te same 4 kierunki: toEntity, bindParameters,
         * fromCreateRequest, toDto). Zademonstruj użycie OBU mapperów
         * (Product i Order) w jednym demo, pokazując, że mają wspólny
         * "szablon" metod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_JoinResultDirectlyToFlatDto {
        /*
         * 🧪 Zadanie 18:
         * Wykonaj JOIN "orders" + "customers" i napisz metodę
         * OrderMapper.toSummaryDto(ResultSet) mapującą WYNIK JOIN-a
         * BEZPOŚREDNIO na OrderSummaryResponse (bez budowania pośredniej
         * encji Order+Customer) - skomentuj (println), kiedy taki
         * "skrót" jest akceptowalny (proste, płaskie DTO tylko do odczytu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ValidationIntegratedIntoMapper {
        /*
         * 🧪 Zadanie 19:
         * Zaktualizuj ProductMapper.fromCreateRequest(), żeby NAJPIERW
         * walidowało żądanie (price > 0, name niepuste) i rzucało
         * IllegalArgumentException PRZED zbudowaniem encji Product.
         * Przetestuj dla poprawnego i niepoprawnego żądania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullCrudRepositoryUsingOnlyMapper {
        /*
         * 🧪 Zadanie 20:
         * Napisz klasę ProductRepository z metodami create(CreateProductRequest),
         * findById(long) -> ProductResponse, findAll() ->
         * List<ProductResponse>, update(long, UpdateProductRequest) - w
         * KTÓREJ ŻADEN kod poza ProductMapper nie odczytuje/zapisuje pól
         * bezpośrednio z ResultSet/PreparedStatement. Zademonstruj pełny
         * CRUD.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericEntityMapperAbstraction {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj generyczny interfejs EntityMapper<E, C, R> z metodami
         * E toEntity(ResultSet), void bindParameters(PreparedStatement, E),
         * E fromCreateRequest(C), R toDto(E). Zaimplementuj go jako
         * ProductMapper (Product, CreateProductRequest, ProductResponse)
         * i UserMapper (User, CreateUserRequest, UserResponse). Napisz
         * GENERYCZNĄ metodę repository <E,C,R> R create(Connection, String
         * sql, EntityMapper<E,C,R> mapper, C request) działającą dla
         * OBU typów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BindThenGeneratedKeysWithoutSecondSelect {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę insertAndReturnEntity(Connection, CreateProductRequest),
         * która: mapuje DTO na Product (kierunek 3, id=0), wstawia go
         * przez bindParameters + RETURN_GENERATED_KEYS, odczytuje
         * wygenerowane id, i BUDUJE finalny Product ŁĄCZĄC wygenerowane
         * id z już posiadanymi w pamięci danymi (name, price) - BEZ
         * DODATKOWEGO zapytania SELECT. Zweryfikuj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MapperUnitTestSuiteForFourDirections {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę testProductMapper(), która dla znanych, stałych
         * danych testuje NIEZALEŻNIE każdy z 4 kierunków ProductMapper i
         * wypisuje "PASS"/"FAIL" dla każdego (np. czy toDto() poprawnie
         * pomija costPrice, czy fromCreateRequest() poprawnie ustawia
         * pola, itd.).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ChangeInternalRepresentationDtoUnaffected {
        /*
         * 🧪 Zadanie 24:
         * Zmień WEWNĘTRZNĄ reprezentację ceny w Product z BigDecimal na
         * long (grosze) - zaktualizuj TYLKO ProductMapper (bindParameters
         * konwertuje grosze<->DECIMAL, toDto konwertuje grosze->BigDecimal
         * z powrotem). Zademonstruj, że ProductResponse i
         * CreateProductRequest (kontrakty DTO) pozostają BEZ ŻADNYCH
         * zmian, mimo zmiany wewnętrznej encji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BatchInsertUsingMapperBindParameters {
        /*
         * 🧪 Zadanie 25:
         * Zmapuj listę 50 CreateProductRequest na 50 obiektów Product
         * (kierunek 3), a potem wstaw je WSZYSTKIE przez JEDEN
         * PreparedStatement, używając bindParameters() + addBatch() w
         * pętli, i executeBatch() na końcu (integracja z Lesson16).
         * Zweryfikuj SELECT COUNT(*) = 50.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_NestedEntityMapperTwoRoundTrips {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj Order (id, customerId) i OrderItem (orderId, productName,
         * quantity). Napisz OrderMapper.toEntityWithItems(Connection,
         * long orderId), która WYKONUJE DWA zapytania (jedno dla orders,
         * jedno dla order_items) i SKŁADA wynik w jeden pełny obiekt
         * Order z listą OrderItem. Zademonstruj na zamówieniu z 3 pozycjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExceptionTranslationInsideMapperLayer {
        /*
         * 🧪 Zadanie 27:
         * Napisz metodę ProductMapper.insertOrThrow(Connection, CreateProductRequest),
         * która łapie SQLException z bindParameters+executeUpdate i
         * rzuca własny, sprawdzany wyjątek ProductCreationException(String,
         * Throwable) z oryginalnym SQLException jako cause (integracja z
         * Lesson13). Przetestuj na poprawnym i naruszającym ograniczenie
         * żądaniu (np. duplikat unikalnej nazwy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullServiceLayerDtoOnlyContract {
        /*
         * 🧪 Zadanie 28:
         * Napisz klasę ProductService (owijającą ProductRepository z
         * Zadania 20) z metodami PRZYJMUJĄCYMI I ZWRACAJĄCYMI WYŁĄCZNIE
         * DTO (nigdy Product, nigdy ResultSet/PreparedStatement w
         * sygnaturach publicznych): createProduct(CreateProductRequest)
         * -> ProductResponse, listProducts() -> List<ProductResponse>,
         * changePrice(long id, BigDecimal newPrice) -> ProductResponse.
         * Zademonstruj użycie serwisu bez ŻADNEGO odniesienia do JDBC w
         * kodzie wołającym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ScatteredVsCentralizedMappingRegressionDemo {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj TĘ SAMĄ funkcjonalność (odczyt + konwersja na DTO)
         * DWOMA sposobami: (a) "rozproszone" mapowanie - kod w KAŻDEJ
         * metodzie repozytorium osobno odczytuje rs.getXxx(...) i buduje
         * DTO; (b) scentralizowany ProductMapper. Zasymuluj ZMIANĘ nazwy
         * kolumny w bazie (np. "price" -> "unit_price") i wypisz, w ILU
         * miejscach kodu trzeba by to poprawić w wariancie (a) kontra (b).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneFullUserMapperCrudFeature {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletną funkcjonalność CRUD dla użytkowników oparta
         * WYŁĄCZNIE na UserMapper (wszystkie 4 kierunki konsekwentnie
         * używane): create (DTO wejściowe -> encja -> INSERT), findById
         * (SELECT -> encja -> DTO), findAll (lista encji -> lista DTO),
         * update (częściowe DTO -> merge z istniejącą encją -> UPDATE),
         * delete. Zademonstruj wszystkie operacje w jednym scenariuszu
         * end-to-end i wypisz raport podsumowujący, że KAŻDA konwersja
         * między ResultSet/SQL/encja/DTO przechodzi przez JEDNO, centralne
         * miejsce (UserMapper).
         */
        public static void main(String[] args) { }
    }
}
