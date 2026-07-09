package com.example.javaquest._13_libraries.Lesson21_MapStructBasics;

public class _Exercises_Lesson21_MapStructBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimpleProductToProductDtoMapper {
        /*
         * 🧪 Zadanie 1:
         * Utwórz klasę Product (pola: Long id, String name, double price) oraz
         * record ProductDto (Long id, String name, double price - te SAME nazwy i
         * typy). Napisz zagnieżdżony interfejs ProductMapper z @Mapper i metodą
         * ProductDto toDto(Product product) - bez żadnych @Mapping (pola powinny
         * zmapować się automatycznie). Pobierz mapper przez Mappers.getMapper(...)
         * i zmapuj Product(1L, "Laptop", 4500.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MapperInterfaceWithMappersGetMapper {
        /*
         * 🧪 Zadanie 2:
         * Utwórz klasę Customer (id, email, phoneNumber) i CustomerDto (te same
         * pola). Zadeklaruj CustomerMapper (@Mapper) z metodą toDto - wypisz
         * PRZED i PO mapowaniu, żeby zobaczyć, że instancja zwrócona przez
         * Mappers.getMapper(CustomerMapper.class) NIE jest null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MappingWithDifferentFieldNames {
        /*
         * 🧪 Zadanie 3:
         * Utwórz klasę Book (pola: String title, String isbn) i BookDto (pola:
         * String bookTitle, String isbnCode - INNE nazwy). Użyj
         * @Mapping(source = "title", target = "bookTitle") oraz
         * @Mapping(source = "isbn", target = "isbnCode") w interfejsie mappera.
         * Zmapuj Book("Pan Tadeusz", "978-83-01-1234") i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MapSingleFieldWithDifferentName {
        /*
         * 🧪 Zadanie 4:
         * Utwórz klasę Car (pola: String brand, String model, int productionYear)
         * i CarDto (pola: String brand, String model, int year - TYLKO productionYear
         * ma inną nazwę w DTO). Zmapuj @Mapping TYLKO dla productionYear->year, reszta
         * pól powinna zmapować się automatycznie (te same nazwy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MapListOfCustomers {
        /*
         * 🧪 Zadanie 5:
         * Rozszerz CustomerMapper z Zadania 2 o metodę
         * List<CustomerDto> toDtoList(List<Customer> customers). Zmapuj listę 4
         * obiektów Customer i wypisz KAŻDY element wynikowej listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExpressionCombiningTwoFields {
        /*
         * 🧪 Zadanie 6:
         * Utwórz klasę Address (pola: String street, String houseNumber, String
         * city) i AddressDto (pole: String fullAddress). Użyj
         * @Mapping(target = "fullAddress", expression = "java(...)") łączącego
         * street + " " + houseNumber + ", " + city w jedno pole. Zmapuj
         * przykładowy adres i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MapBooleanFlagField {
        /*
         * 🧪 Zadanie 7:
         * Utwórz klasę Account (pola: String username, boolean active) i AccountDto
         * (te SAME pola - te sama nazwa/typ). Zmapuj Account("jkowalski", true) i
         * wypisz pole active w DTO, żeby potwierdzić, że boolean też mapuje się
         * automatycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MapNumericFieldsDifferentButCompatibleTypes {
        /*
         * 🧪 Zadanie 8:
         * Utwórz klasę Invoice (pole: int quantity) i InvoiceDto (pole: long
         * quantity - int -> long, KOMPATYBILNE typy liczbowe). Zmapuj Invoice(5) i
         * sprawdź w komentarzu, czy MapStruct sam wygenerował rzutowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareManualMappingVsMapStructLineCount {
        /*
         * 🧪 Zadanie 9:
         * Weź Employee/EmployeeDto z lekcji. Napisz RĘCZNĄ metodę statyczną
         * manualToDto(Employee) robiącą TO SAMO co EmployeeMapper.toDto(...) (bez
         * MapStruct, zwykły kod). Porównaj w komentarzu liczbę linii obu podejść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MapperForStudentGradeAverage {
        /*
         * 🧪 Zadanie 10:
         * Utwórz klasę Student (pola: String firstName, String lastName, double[]
         * grades) i StudentDto (pola: String fullName, double averageGrade). Użyj
         * expression dla OBU pól (fullName = firstName+lastName, averageGrade =
         * średnia z grades). Zmapuj przykładowego studenta z ocenami {4.5, 5.0, 3.5}.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MapperWithTwoTargetMethodsSameSource {
        /*
         * 🧪 Zadanie 11:
         * Dla klasy Employee (z lekcji) zdefiniuj DRUGI DTO - EmployeeSummaryDto
         * (pola: Long id, String fullName - BEZ salary, np. do publicznej listy
         * pracowników). Dodaj do EmployeeMapper drugą metodę toSummaryDto(Employee)
         * obok istniejącej toDto(Employee) - zweryfikuj, że OBIE metody działają
         * niezależnie na tym samym obiekcie źródłowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ValidateGeneratedImplClassName {
        /*
         * 🧪 Zadanie 12:
         * Pobierz mapper przez Mappers.getMapper(EmployeeMapper.class) i wypisz
         * mapper.getClass().getName() - w komentarzu wyjaśnij, dlaczego wygenerowana
         * klasa (EmployeeMapperImpl) NIE jest zagnieżdżona mimo że interfejs
         * EmployeeMapper jest zagnieżdżony w pliku lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MapperReusingAnotherMapperMethod {
        /*
         * 🧪 Zadanie 13:
         * Utwórz klasę Department (pola: String name, String code) i DepartmentDto
         * (te same pola). Utwórz klasę EmployeeWithDepartment (dziedziczy pola
         * Employee + Department department) i EmployeeWithDepartmentDto (fullName,
         * salary, DepartmentDto department). W JEDNYM interfejsie mappera zadeklaruj
         * metodę dla Department->DepartmentDto ORAZ metodę dla
         * EmployeeWithDepartment->EmployeeWithDepartmentDto - sprawdź w komentarzu,
         * czy MapStruct sam użył pierwszej metody do zmapowania pola department.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DetectMissingFieldWarningAtCompileTime {
        /*
         * 🧪 Zadanie 14:
         * Utwórz klasę Order (pola: Long id, double totalAmount, String status) i
         * OrderDto (pola: Long id, double totalAmount - BEZ status, celowo). Zmapuj
         * i zapisz w komentarzu: czy kompilacja się powiodła mimo brakującego pola w
         * DTO (MapStruct domyślnie tylko OSTRZEGA o niezmapowanych polach źródłowych,
         * nie blokuje kompilacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MapperForApiResponseWrapper {
        /*
         * 🧪 Zadanie 15:
         * Utwórz klasę Product (id, name, price) i generyczny wzorzec ApiResponse
         * (bez MapStruct - zwykła klasa z polem List<ProductDto> data oraz int
         * totalCount). Zmapuj listę 5 produktów przez ProductMapper.toDtoList(...) i
         * "opakuj" wynik w ApiResponse z totalCount = data.size().
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExpressionWithStaticHelperMethod {
        /*
         * 🧪 Zadanie 16:
         * Utwórz klasę Person (pola: String firstName, String lastName) i
         * PersonDto (pole: String initials, np. "AK" dla Anna Kowalska). Napisz w
         * klasie lekcji STATYCZNĄ metodę pomocniczą buildInitials(String, String) i
         * wywołaj ją WEWNĄTRZ @Mapping(expression = "java(...)").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MapperHandlingNullSourceObject {
        /*
         * 🧪 Zadanie 17:
         * Wywołaj EmployeeMapper.toDto(null) - sprawdź (i zapisz w komentarzu) co
         * zwraca MapStruct DOMYŚLNIE dla null wejścia (spodziewaj się null, bez
         * NullPointerException - to domyślne zachowanie wygenerowanego kodu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MapEmptyListReturnsEmptyList {
        /*
         * 🧪 Zadanie 18:
         * Wywołaj EmployeeMapper.toDtoList(List.of()) (pusta lista) - zweryfikuj, że
         * wynik to pusta lista (NIE null) i wypisz jej rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MultipleMappingAnnotationsOnOneMethod {
        /*
         * 🧪 Zadanie 19:
         * Utwórz klasę Article (pola: String headline, String bodyText, String
         * authorName) i ArticleDto (pola: String title, String content, String
         * author - WSZYSTKIE 3 nazwy inne). Użyj TRZECH osobnych adnotacji @Mapping
         * na jednej metodzie (source/target dla każdego pola).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareMapStructVsHandwrittenPerformance {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas (System.nanoTime()) mapowania 1_000_000 obiektów Employee na
         * EmployeeDto DWOMA sposobami: przez EmployeeMapper (MapStruct) i przez
         * ręczną metodę statyczną (jak w Zadaniu 9). Wypisz oba czasy i zapisz w
         * komentarzu wniosek o zbliżonej wydajności.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MapperForShoppingCartWithTotalCalculation {
        /*
         * 🧪 Zadanie 21:
         * Utwórz klasę CartItem (String productName, double unitPrice, int
         * quantity) i CartItemDto (productName, double lineTotal - unitPrice *
         * quantity przez expression). Zmapuj listę 4 pozycji koszyka i wypisz sumę
         * WSZYSTKICH lineTotal (poza mapperem, w zwykłym kodzie Javy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReplaceManualDtoFromLesson19WithMapStruct {
        /*
         * 🧪 Zadanie 22:
         * Wróć do _09_jdbc/Lesson19_Dto (User -> UserResponse, RĘCZNE mapowanie
         * pomijające passwordHash). Odtwórz TĘ SAMĄ logikę przez MapStruct: encja
         * User (id, email, firstName, passwordHash) -> UserResponseDto (id, email,
         * firstName - BEZ passwordHash, MapStruct pominie je automatycznie bo DTO
         * go nie ma). Zweryfikuj, że UserResponseDto NIE zawiera hasła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MapperWithConditionalExpressionUsingTernary {
        /*
         * 🧪 Zadanie 23:
         * Utwórz klasę Product (pola: String name, int stockQuantity) i ProductDto
         * (pola: String name, String availability - "DOSTEPNY"/"BRAK" przez
         * wyrażenie warunkowe w expression, na podstawie stockQuantity > 0). Zmapuj
         * 3 produkty z różnymi stanami magazynowymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ChainedMappingThroughIntermediateDto {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj TRZY klasy: RawSensorReading (String rawValue, long timestamp),
         * SensorReadingDto (double celsius, String readableTime) - zmapuj
         * RawSensorReading -> SensorReadingDto przez DWIE osobne metody expression
         * (parsowanie rawValue na double, formatowanie timestamp na czytelny
         * String), obie w JEDNYM interfejsie mappera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BulkMapAndFilterWithStreams {
        /*
         * 🧪 Zadanie 25:
         * Zmapuj listę 10 obiektów Employee (Employee/EmployeeDto z lekcji) przez
         * toDtoList(...), a NASTĘPNIE (poza mapperem, przez Stream) odfiltruj z
         * wyniku tylko te DTO, gdzie salary > 8000 - wypisz przefiltrowaną listę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MapperInterfaceExtendingAnotherInterface {
        /*
         * 🧪 Zadanie 26:
         * Utwórz bazowy interfejs (NIE @Mapper) BaseConverter<S, T> z metodą
         * T convert(S source). Zadeklaruj ProductMapper jako
         * "interface ProductMapper extends BaseConverter<Product, ProductDto>"
         * dodatkowo oznaczony @Mapper - sprawdź w komentarzu, czy MapStruct
         * poprawnie wygenerował implementację odziedziczonej metody convert().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FullPipelineFromRawDataToDtoReport {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj pełen scenariusz: lista 20 obiektów Order (id, totalAmount,
         * status) -> zmapuj na OrderDto przez mapper -> pogrupuj (Stream, poza
         * mapperem) wg status -> wypisz raport "liczba zamówień i suma totalAmount
         * na każdy status".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DetectAndFixAmbiguousMappingMethods {
        /*
         * 🧪 Zadanie 28:
         * Celowo zadeklaruj w JEDNYM interfejsie mappera DWIE metody o tej samej
         * sygnaturze wejścia, ale różnym typie zwracanym (np. EmployeeDto
         * toDto(Employee) ORAZ EmployeeSummaryDto toDto(Employee) - ta sama nazwa,
         * ten sam parametr, RÓŻNY zwracany typ, przeciążenie). Sprawdź, czy
         * kompiluje się bez problemu (przeciążenie po typie zwracanym w Javie NIE
         * jest dozwolone samo w sobie, ale przez różne NAZWY metod jest OK) - opisz
         * wniosek w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MigrateEntireManualMapperClassToMapStruct {
        /*
         * 🧪 Zadanie 29:
         * Wybierz dowolną klasę z WCZEŚNIEJSZEGO rozdziału zawierającą RĘCZNE
         * mapowanie encja<->DTO (np. _10_dao) - odtwórz JEJ funkcjonalność
         * WYŁĄCZNIE przez interfejs MapStruct (bez ani jednej linii ręcznego
         * mapowania pól). Porównaj w komentarzu liczbę linii kodu PRZED i PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignMapperLayerForMiniOnlineStore {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj I zaimplementuj kompletną warstwę mapperów dla mini-sklepu:
         * encje Product, Customer, Order (Order zawiera Customer i listę
         * CartItem) oraz odpowiadające im DTO. Zbuduj OSOBNE interfejsy
         * ProductMapper/CustomerMapper/OrderMapper (Order może korzystać z
         * pozostałych dwóch, jeśli je zaimportujesz) - zademonstruj PEŁNY przepływ:
         * utworzenie danych -> zmapowanie na DTO -> wypisanie "odpowiedzi API".
         */
        public static void main(String[] args) { }
    }
}
