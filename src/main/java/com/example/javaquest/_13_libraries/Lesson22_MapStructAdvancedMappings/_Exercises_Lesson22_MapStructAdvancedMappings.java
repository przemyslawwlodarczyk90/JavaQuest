package com.example.javaquest._13_libraries.Lesson22_MapStructAdvancedMappings;

public class _Exercises_Lesson22_MapStructAdvancedMappings {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_NestedMappingTwoLevels {
        /*
         * 🧪 Zadanie 1:
         * Utwórz klasę Author (pola: String name, String country) i AuthorDto (te
         * same pola). Utwórz klasę Book (pola: String title, Author author) i
         * BookDto (pola: String title, AuthorDto author). W JEDNYM interfejsie
         * BookMapper zadeklaruj DWIE metody: toDto(Book) i toDto(Author) - bez
         * żadnej dodatkowej adnotacji sprawdź, że MapStruct sam użył drugiej metody
         * do zmapowania zagnieżdżonego pola "author".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NestedMappingWithListInsideObject {
        /*
         * 🧪 Zadanie 2:
         * Utwórz klasę Chapter (pole: String title) i ChapterDto (to samo pole).
         * Utwórz klasę Ebook (pola: String title, List<Chapter> chapters) i
         * EbookDto (pola: String title, List<ChapterDto> chapters). Zadeklaruj
         * EbookMapper z metodami toDto(Ebook) i toDto(Chapter) - zmapuj Ebook z 3
         * rozdziałami i wypisz wynikową listę ChapterDto.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ComposedMapperWithUses {
        /*
         * 🧪 Zadanie 3:
         * Utwórz klasę Money (pola: double amount, String currency) i MoneyDto (te
         * same pola). Zadeklaruj SAMODZIELNY interfejs MoneyMapper (@Mapper) z
         * metodą toDto(Money). Utwórz klasę Invoice (pola: String number, Money
         * total) i InvoiceDto (pola: String number, MoneyDto total). Zadeklaruj
         * InvoiceMapper jako @Mapper(uses = {MoneyMapper.class}) - zweryfikuj, że
         * InvoiceMapper NIE ma własnej metody dla Money, a mimo to pole total
         * mapuje się poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_QualifiedByNameSingleNamedMethod {
        /*
         * 🧪 Zadanie 4:
         * Utwórz klasę Coupon (pole: String code) i CouponDto (pole: String code).
         * W interfejsie CouponMapper dodaj metodę "default" oznaczoną
         * @Named("normalizeCode"), która robi code.trim().toUpperCase(), a na
         * metodzie toDto użyj @Mapping(target = "code", qualifiedByName =
         * "normalizeCode"). Zmapuj Coupon("  letnia-promocja  ") i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DefaultValueWhenSourceIsNull {
        /*
         * 🧪 Zadanie 5:
         * Utwórz klasę Subscription (pola: Long id, String plan) i
         * SubscriptionDto (te same pola). Użyj @Mapping(target = "plan",
         * defaultValue = "FREE") na metodzie toDto. Zmapuj DWA obiekty:
         * Subscription(1L, null) i Subscription(2L, "PREMIUM") - wypisz oba wyniki
         * i porównaj wartość pola plan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_DefaultValueOnlyForNullNotEmptyString {
        /*
         * 🧪 Zadanie 6:
         * Rozszerz Zadanie 5 o trzeci obiekt: Subscription(3L, "") (PUSTY string,
         * nie null). Zmapuj go tym samym mapperem i w komentarzu zapisz, czy
         * defaultValue = "FREE" zadziałało dla pustego stringa, czy tylko dla
         * null (zweryfikuj empirycznie, wypisując wynik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IgnoreSingleField {
        /*
         * 🧪 Zadanie 7:
         * Utwórz klasę Employee (pola: Long id, String name, double secretBonus) i
         * EmployeeDto (te same 3 pola). Użyj @Mapping(target = "secretBonus",
         * ignore = true) na metodzie toDto - zmapuj przykładowego pracownika z
         * niezerowym secretBonus i sprawdź, że w DTO pole ma wartość domyślną
         * (0.0), mimo że źródło miało wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IgnoreMultipleFields {
        /*
         * 🧪 Zadanie 8:
         * Utwórz klasę Patient (pola: String name, String pesel, String
         * diagnosis) i PatientPublicDto (te same 3 pola - do publicznej
         * statystyki). Użyj DWÓCH adnotacji @Mapping z ignore = true (dla pesel i
         * diagnosis) na jednej metodzie toDto - zweryfikuj, że tylko "name"
         * zostaje zmapowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CustomDefaultMethodSimpleStringConversion {
        /*
         * 🧪 Zadanie 9:
         * Utwórz klasę RawLog (pole: String message) i LogDto (pole: String
         * message). W interfejsie LogMapper dodaj metodę default String
         * clean(String), która przycina spacje i zamienia wielokrotne spacje na
         * pojedyncze (bez @Mapping - MapStruct sam dopasuje metodę po nazwie pola
         * "message" i sygnaturze String -> String). Zmapuj RawLog("  blad   w
         * systemie  ") i wypisz oczyszczony wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MapperCombiningIgnoreAndDefaultValue {
        /*
         * 🧪 Zadanie 10:
         * Utwórz klasę Task (pola: Long id, String status, String internalOwner)
         * i TaskDto (pola: Long id, String status - BEZ internalOwner, celowo).
         * Na metodzie toDto użyj @Mapping(target = "status", defaultValue =
         * "NOWE") - zmapuj Task(1L, null, "jkowalski") i Task(2L, "ZAKONCZONE",
         * "anowak"), wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CustomDefaultMethodsBidirectional {
        /*
         * 🧪 Zadanie 11:
         * Utwórz klasę Meeting (pola: Long id, LocalDateTime startsAt) i
         * MeetingDto (pola: Long id, String startsAt). W interfejsie
         * MeetingMapper dodaj DWIE metody default o nazwie "map" - jedną
         * LocalDateTime -> String (wzorzec "dd.MM.yyyy HH:mm"), drugą String ->
         * LocalDateTime. Dodaj metody toDto(Meeting) i toEntity(MeetingDto) -
         * zmapuj w OBIE strony i wypisz wyniki obu konwersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MappingTargetBasicUpdate {
        /*
         * 🧪 Zadanie 12:
         * Utwórz MUTOWALNĄ klasę (gettery + settery) Article (pola: Long id,
         * String title, String content) i record ArticleUpdateDto (String title,
         * String content). Zadeklaruj ArticleMapper z metodą void
         * updateFromDto(ArticleUpdateDto dto, @MappingTarget Article target).
         * Zaktualizuj istniejący Article nowymi danymi i wypisz obiekt PRZED i PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MappingTargetLeavesMissingFieldsUntouched {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz Article z Zadania 12 o pole Long id oraz LocalDateTime
         * createdAt (mutowalne, z getterami/setterami). ArticleUpdateDto NADAL ma
         * tylko title/content. Po wywołaniu updateFromDto zweryfikuj (wypisz), że
         * pola id i createdAt mają DOKŁADNIE te same wartości co przed
         * aktualizacją (referencyjnie/wartościowo niezmienione).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NestedMappingCombinedWithUses {
        /*
         * 🧪 Zadanie 14:
         * Utwórz klasę Category (pola: Long id, String name) i CategoryDto (te
         * same pola) z osobnym CategoryMapper (@Mapper). Utwórz klasę Product
         * (pola: String name, Category category, List<String> tags) i ProductDto
         * (pola: String name, CategoryDto category, List<String> tags). Zadeklaruj
         * ProductMapper jako @Mapper(uses = {CategoryMapper.class}) - zmapuj
         * produkt z kategorią i listą tagów, wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MultipleQualifiedByNameInOneMapper {
        /*
         * 🧪 Zadanie 15:
         * Utwórz klasę Contact (pola: String phone, String email) i ContactDto
         * (te same pola). W ContactMapper dodaj DWIE metody default oznaczone
         * @Named ("maskPhone" i "maskEmail"), każda maskująca odpowiednio część
         * numeru/adresu (np. "***-***-1234", "a***@example.com"). Użyj OBU
         * qualifiedByName na jednej metodzie toDto i zmapuj przykładowy kontakt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MapListToSet {
        /*
         * 🧪 Zadanie 16:
         * Utwórz klasę Skill (pola: Long id, String name) i SkillDto (te same
         * pola). Zadeklaruj SkillMapper z metodami toDto(Skill) i Set<SkillDto>
         * toDtoSet(List<Skill> skills). Zmapuj listę 5 umiejętności (z jednym
         * powtórzonym rekordem o tym samym id/name) i sprawdź w komentarzu
         * rozmiar wynikowego Set (Set wymaga equals/hashCode na rekordzie, które
         * rekordy Javy generują automatycznie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MapSetToList {
        /*
         * 🧪 Zadanie 17:
         * Odwróć kierunek z Zadania 16: metoda List<SkillDto>
         * toDtoList(Set<Skill> skills) - zmapuj Set.of(...) trzech obiektów Skill
         * na List<SkillDto> i wypisz rozmiar oraz zawartość wynikowej listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ThreeLevelNestedMapping {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj TRZY poziomy zagnieżdżenia: Country (name, code) -> CountryDto,
         * City (name, Country country) -> CityDto (name, CountryDto country),
         * Warehouse (code, City city) -> WarehouseDto (code, CityDto city). W
         * JEDNYM interfejsie WarehouseMapper zadeklaruj TRZY metody toDto (po
         * jednej dla każdego poziomu) - zmapuj przykładowy Warehouse i wypisz
         * pełną, zagnieżdżoną strukturę WarehouseDto.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MapperUsingTwoOtherMappers {
        /*
         * 🧪 Zadanie 19:
         * Utwórz DWA niezależne mappery: SizeMapper (dla klasy Size: pola width,
         * height -> SizeDto) i ColorMapper (dla klasy Color: pole hex ->
         * ColorDto). Utwórz klasę Banner (pola: Size size, Color color, String
         * text) i BannerDto (SizeDto size, ColorDto color, String text).
         * Zadeklaruj BannerMapper jako @Mapper(uses = {SizeMapper.class,
         * ColorMapper.class}) - zmapuj przykładowy baner i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MapperCombiningDefaultValueAndIgnore {
        /*
         * 🧪 Zadanie 20:
         * Utwórz klasę Ticket (pola: Long id, String priority, String
         * assignedAgent) i TicketDto (pola: Long id, String priority - BEZ
         * assignedAgent). Na jednej metodzie toDto użyj JEDNOCZEŚNIE
         * @Mapping(target = "priority", defaultValue = "NISKI") - zmapuj DWA
         * bilety (jeden z priority = null, drugi bez) i wypisz oba wyniki,
         * potwierdzając że assignedAgent nigdzie się nie pojawia (bo go nie ma w DTO).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullChainNestedUsesAndCustomConversion {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj scenariusz łączący WSZYSTKIE techniki naraz: klasa Employee
         * (pola: String fullName, LocalDate hireDate, Department department) z
         * osobnym DepartmentMapper (@Mapper(uses = ...) nie jest tu potrzebny, ale
         * EmployeeMapper MA używać @Mapper(uses = {DepartmentMapper.class})), a
         * hireDate mapuje się na String przez własną metodę default (wzorzec
         * "yyyy-MM-dd"). Zmapuj listę 3 pracowników na EmployeeDto i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MappingTargetUpdatingNestedObjectManually {
        /*
         * 🧪 Zadanie 22:
         * Utwórz mutowalną klasę Customer (pola: String name, Address address -
         * gdzie Address też jest mutowalne z polami street/city) oraz DTO
         * CustomerAddressUpdateDto (String street, String city). Zadeklaruj
         * metodę void updateAddress(CustomerAddressUpdateDto dto, @MappingTarget
         * Address target) w AddressMapper i wywołaj ją RĘCZNIE na
         * customer.getAddress() (bez zagnieżdżonego @MappingTarget na poziomie
         * Customer - MapStruct nie wspiera zagnieżdżonego @MappingTarget wprost).
         * Wypisz Customer PRZED i PO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_QualifiedByNameForNumericFormatting {
        /*
         * 🧪 Zadanie 23:
         * Utwórz klasę Payment (pole: double amountInCents, np. 12345 = 123.45
         * zł) i PaymentDto (pole: String amountFormatted). W PaymentMapper dodaj
         * default String toFormattedAmount(double amountInCents) oznaczoną
         * @Named("formatAmount"), zwracającą String w formacie "123,45 zł" (z
         * przecinkiem). Użyj @Mapping(target = "amountFormatted", source =
         * "amountInCents", qualifiedByName = "formatAmount") i zmapuj 3 różne
         * kwoty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ManualPatchVsMappingTargetLineCount {
        /*
         * 🧪 Zadanie 24:
         * Weź UserProfile/UserProfileUpdateDto z lekcji (albo analogiczną własną
         * parę klas). Napisz RĘCZNĄ metodę statyczną applyPatchManually(dto,
         * profile) robiącą DOKŁADNIE to samo co
         * UserProfileMapper.updateProfileFromDto(...) (wywołania setterów bez
         * MapStruct). Porównaj w komentarzu liczbę linii obu podejść i opisz, co
         * się zmienia, gdy DTO urośnie do 10 pól.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_NamedEnumConversionMethod {
        /*
         * 🧪 Zadanie 25:
         * Utwórz enum OrderStatus (NEW, PAID, SHIPPED, CANCELLED) w klasie Order
         * (pola: Long id, OrderStatus status) oraz OrderDto (pola: Long id, String
         * statusLabel - polska etykieta, np. "Nowe", "Oplacone"). W OrderMapper
         * dodaj default String toLabel(OrderStatus status) oznaczoną @Named z
         * mapowaniem enum -> etykieta (np. switch), użyj qualifiedByName na polu
         * statusLabel. Zmapuj listę zamówień o różnych statusach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BatchUpdateEntitiesFromDtoListUsingMappingTarget {
        /*
         * 🧪 Zadanie 26:
         * Utwórz listę 5 mutowalnych obiektów Product (id, name, double price) i
         * odpowiadającą jej listę 5 ProductPriceUpdateDto (double newPrice, w tej
         * samej kolejności co id produktów). W pętli, dla każdej pary (produkt,
         * dto) o tym samym indeksie, wywołaj mapper.updatePrice(dto, product) -
         * @MappingTarget. Wypisz WSZYSTKIE produkty PRZED i PO aktualizacji cen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FullPipelineNestedCollectionAndCustomConversion {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj klasę Cart (pola: String customerName, List<CartItem> items -
         * gdzie CartItem to record productName/unitPrice/quantity) i CartDto
         * (pola: String customerName, Set<CartItemDto> items, String
         * totalFormatted - wyliczone przez expression jako suma
         * unitPrice*quantity ze wszystkich pozycji, sformatowana jako "123,45
         * zł"). Połącz mapowanie zagnieżdżonej kolekcji (List -> Set) z
         * expression na poziomie całego obiektu Cart.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AmbiguousDefaultMethodsRequireQualifiedByName {
        /*
         * 🧪 Zadanie 28:
         * Celowo zadeklaruj w JEDNYM interfejsie mappera DWIE metody default o
         * IDENTYCZNEJ sygnaturze String -> String (np. toUpperCaseVersion i
         * toLowerCaseVersion), OBIE oznaczone różnymi @Named, ale NIE użyj
         * qualifiedByName na polu tego samego typu String -> String w metodzie
         * toDto. Sprawdź (wypisz komentarz), czy skopiowało wartość "jak jest"
         * (bez wywołania żadnej z dwóch metod), a NASTĘPNIE dodaj qualifiedByName
         * i pokaż, że dopiero wtedy właściwa metoda zostaje użyta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignMapperLayerForInvoiceDomain {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj kompletną warstwę mapperów dla faktury: Client (id, name,
         * Address), InvoiceLine (description, double unitPrice, int quantity),
         * Invoice (number, Client client, List<InvoiceLine> lines, LocalDate
         * issueDate). Odpowiadające DTO: ClientDto, InvoiceLineDto (z
         * lineTotal wyliczonym przez expression), InvoiceDto (issueDate jako
         * String przez własną metodę default, lines jako List<InvoiceLineDto>).
         * Połącz: zagnieżdżanie, @Mapper(uses=...), custom conversion i
         * expression w jednym spójnym zestawie interfejsów. Zademonstruj pełne
         * mapowanie przykładowej faktury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneCrmMergingAllAdvancedTechniques {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-CRM łączący WSZYSTKIE techniki z tej lekcji: Lead (id, name,
         * email, LocalDate contactDate, Address address, Set<String> tags,
         * internalScore) -> LeadDto (bez internalScore - ignore, contactDate jako
         * String przez custom conversion, address jako AddressDto przez uses,
         * tags bez zmian, email częściowo zamaskowany przez qualifiedByName).
         * Dodaj też LeadUpdateDto + metodę z @MappingTarget do częściowej
         * aktualizacji istniejącego Lead. Zademonstruj: utworzenie leadów ->
         * zmapowanie na DTO (z maskowaniem/ignorowaniem) -> aktualizację
         * wybranego leada przez @MappingTarget -> ponowne zmapowanie i wypisanie
         * "przed/po".
         */
        public static void main(String[] args) { }
    }
}
