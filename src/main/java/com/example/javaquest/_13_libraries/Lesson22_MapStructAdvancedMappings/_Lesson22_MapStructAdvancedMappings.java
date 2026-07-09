package com.example.javaquest._13_libraries.Lesson22_MapStructAdvancedMappings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public class _Lesson22_MapStructAdvancedMappings {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 22: MAPSTRUCT - MAPOWANIA ZAAWANSOWANE ===");

        /*
         * ============================================================
         * 📦 KONTYNUACJA LEKCJI 21
         * ============================================================
         * W poprzedniej lekcji poznalismy podstawy MapStruct: @Mapper,
         * Mappers.getMapper(...), automatyczne mapowanie pol o tej samej
         * nazwie/typie, @Mapping(source=..., target=...) oraz
         * @Mapping(target=..., expression="java(...)"), a takze
         * automatyczne mapowanie List<T>. Ta lekcja NIE powtarza tego
         * materialu - skupia sie WYLACZNIE na bardziej zaawansowanych
         * mozliwosciach MapStruct, ktorych realne projekty potrzebuja
         * niemal codziennie: mapowanie obiektow zagniezdzonych, wlasne
         * metody konwersji, ignorowanie pol, laczenie wielu mapperow oraz
         * aktualizacje JUZ ISTNIEJACEGO obiektu (wzorzec PATCH).
         */

        demonstrateNestedMapping();
        demonstrateComposedMapperUses();
        demonstrateQualifiedByName();
        demonstrateDefaultValue();
        demonstrateIgnoreField();
        demonstrateCustomConversionMethods();
        demonstrateMappingTargetUpdate();
        demonstrateDifferentCollectionTypes();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - MAPOWANIE ZAGNIEZDZONE: gdy pole zrodlowe i docelowe sa
         *   obiektami zlozonymi (nie prymitywami/String), MapStruct SAM
         *   szuka w tym samym interfejsie @Mapper metody o pasujacej
         *   sygnaturze (parametr = typ zagniezdzonego pola zrodlowego,
         *   zwracany typ = typ zagniezdzonego pola docelowego) i uzywa
         *   jej - bez zadnej dodatkowej adnotacji.
         * - @Mapping(qualifiedByName = "...") wskazuje KONKRETNA metode
         *   oznaczona @Named, gdy w mapperze jest wiecej niz jedna metoda
         *   mogaca teoretycznie posluzyc do konwersji danego pola.
         * - @Mapping(defaultValue = "...") ustawia wartosc domyslna, gdy
         *   pole zrodlowe jest null (dziala TYLKO dla null, nie dla
         *   pustego stringa czy 0).
         * - @Mapping(target = "...", ignore = true) jawnie wylacza pole
         *   docelowe z automatycznego mapowania - zostaje null/domyslne.
         * - @Mapper(uses = {InnyMapper.class}) pozwala SKLADAC mappery -
         *   zamiast duplikowac logike mapowania zagniezdzonego typu w
         *   kazdym mapperze, ktory go potrzebuje, wskazuje sie ISTNIEJACY
         *   mapper jako zrodlo metod pomocniczych.
         * - Metody "default" wewnatrz interfejsu @Mapper to wlasne
         *   konwersje dla typow, ktorych MapStruct nie potrafi zmapowac
         *   automatycznie (np. LocalDate <-> String wg konkretnego wzorca)
         *   - MapStruct wykrywa je AUTOMATYCZNIE po typie parametru i
         *   zwracanym typie, bez potrzeby @Mapping, o ile nazwy pol sie
         *   zgadzaja.
         * - @MappingTarget pozwala zaktualizowac JUZ ISTNIEJACY obiekt
         *   (metoda typu void, drugi parametr oznaczony @MappingTarget)
         *   zamiast tworzyc nowy - klasyczny wzorzec PATCH/czesciowej
         *   aktualizacji.
         * - MapStruct mapuje miedzy ROZNYMI typami kolekcji (np.
         *   List<Encja> -> Set<Dto>) dokladnie tak samo latwo jak
         *   List -> List z Lekcji 21 - sam dobiera implementacje
         *   docelowej kolekcji.
         */

        System.out.println("\n=== KONIEC LEKCJI 22 ===");
    }

    /*
     * ============================================================
     * 🔹 MAPOWANIE ZAGNIEZDZONE - MapStruct SAM znajduje metode dla
     * zagniezdzonego typu w TYM SAMYM interfejsie @Mapper
     * ============================================================
     * OrderMapper deklaruje DWIE metody: toDto(Order) i toDto(Customer)
     * (przeciazenie po typie parametru - dozwolone w Javie). Pole
     * "customer" w Order jest typu Customer, a odpowiadajace mu pole w
     * OrderDto jest typu CustomerDto - MapStruct automatycznie
     * dopasowuje i UZYWA drugiej metody (toDto(Customer)) do zmapowania
     * tego zagniezdzonego pola, dokladnie tak jak w Lekcji 21 robil to
     * dla elementow listy.
     */
    private static void demonstrateNestedMapping() {
        System.out.println("\n=== MAPOWANIE ZAGNIEZDZONE (Order zawiera Customer) ===");

        OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

        Customer customer = new Customer(1L, "Anna Kowalska", "anna@example.com");
        Order order = new Order(100L, customer, 259.99);

        OrderDto orderDto = orderMapper.toDto(order);
        System.out.println("Order: " + order);
        System.out.println("OrderDto (customer zmapowany zagniezdzona metoda toDto(Customer)): " + orderDto);
    }

    /*
     * ============================================================
     * 🔍 @Mapper(uses = {...}) - SKLADANIE MAPPEROW
     * ============================================================
     * W przeciwienstwie do poprzedniego przykladu (gdzie obie metody
     * mapujace byly w JEDNYM interfejsie), tutaj AddressMapper to
     * CALKIEM OSOBNY, samodzielny interfejs @Mapper. CompanyMapper
     * NIE duplikuje logiki mapowania Address -> AddressDto - zamiast
     * tego deklaruje @Mapper(uses = {AddressMapper.class}), wiec
     * wygenerowany CompanyMapperImpl wewnetrznie korzysta z
     * AddressMapperImpl do zmapowania pola "headquarters".
     */
    private static void demonstrateComposedMapperUses() {
        System.out.println("\n=== @Mapper(uses = {...}) - SKLADANIE MAPPEROW (Company + Address) ===");

        AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
        Address address = new Address("Marszalkowska 1", "Warszawa", "00-001");
        System.out.println("AddressMapper uzyty samodzielnie: " + addressMapper.toDto(address));

        CompanyMapper companyMapper = Mappers.getMapper(CompanyMapper.class);
        Company company = new Company("JavaQuest sp. z o.o.", address);
        CompanyDto companyDto = companyMapper.toDto(company);
        System.out.println("CompanyMapper (uses = AddressMapper.class) zmapowal headquarters bez wlasnej logiki: "
                + companyDto);
    }

    /*
     * ============================================================
     * 🔹 @Mapping(qualifiedByName = "...") - GDY JEST WIECEJ NIZ JEDNA
     * PASUJACA METODA KONWERSJI
     * ============================================================
     * ProductMapper ma DWIE metody "default" o identycznej sygnaturze
     * String -> String (normalizeSku i shoutName), obie oznaczone
     * @Named. Poniewaz pole "sku" ma typ String zarowno w Product, jak
     * i w ProductDto, MapStruct BEZ dodatkowej wskazowki po prostu
     * skopiowalby wartosc "jak jest" (typy sie zgadzaja, wiec nie
     * szuka konwertera). Zeby WYMUSIC uzycie konkretnej metody
     * transformujacej mimo zgodnych typow (i jednoznacznie wskazac
     * KTORA z wielu kandydujacych metod uzyc), sluzy
     * @Mapping(target = "sku", qualifiedByName = "normalizeSku") -
     * odwoluje sie do nazwy podanej w @Named na metodzie normalizeSku.
     */
    private static void demonstrateQualifiedByName() {
        System.out.println("\n=== @Mapping(qualifiedByName = ...) - wybor konkretnej metody konwersji ===");

        ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
        Product product = new Product(1L, "Klawiatura mechaniczna", "  kb-2026-black  ");

        ProductDto dto = productMapper.toDto(product);
        System.out.println("Product.sku (surowe): '" + product.sku() + "'");
        System.out.println("ProductDto.sku (po normalizeSku - trim + uppercase): '" + dto.sku() + "'");
    }

    /*
     * ============================================================
     * 🔍 @Mapping(defaultValue = "...") - WARTOSC DOMYSLNA DLA NULL
     * ============================================================
     * defaultValue dziala TYLKO, gdy pole zrodlowe jest null - jesli
     * zrodlo ma jakakolwiek wartosc (nawet pusty String ""), zostanie
     * ona uzyta zamiast wartosci domyslnej. Ponizej: Ticket z priority
     * = null dostaje w DTO domyslny priorytet "NORMALNY", a Ticket z
     * jawnie ustawionym priority zachowuje swoja wartosc.
     */
    private static void demonstrateDefaultValue() {
        System.out.println("\n=== @Mapping(defaultValue = ...) - wartosc domyslna gdy zrodlo = null ===");

        TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

        Ticket ticketBezPriorytetu = new Ticket(1L, null);
        Ticket ticketZPriorytetem = new Ticket(2L, "PILNY");

        System.out.println("Ticket(1, priority=null)      -> " + ticketMapper.toDto(ticketBezPriorytetu));
        System.out.println("Ticket(2, priority='PILNY')   -> " + ticketMapper.toDto(ticketZPriorytetem));
    }

    /*
     * ============================================================
     * 🔹 @Mapping(target = "...", ignore = true) - WYKLUCZENIE POLA
     * ============================================================
     * internalNotes istnieje w UserAccount, ale swiadomie NIE powinno
     * trafiac do UserAccountDto (np. dane wewnetrzne, nieprzeznaczone
     * dla API). Bez ignore = true MapStruct probowalby je zmapowac (bo
     * DTO ma pole o tej samej nazwie/typie) - ignore = true jawnie
     * wylacza to pole, mimo ze technicznie DAloBY sie je zmapowac.
     */
    private static void demonstrateIgnoreField() {
        System.out.println("\n=== @Mapping(target = ..., ignore = true) - wykluczenie pola ===");

        UserAccountMapper userAccountMapper = Mappers.getMapper(UserAccountMapper.class);
        UserAccount account = new UserAccount(7L, "jkowalski", "klient VIP, wrazliwe uwagi wewnetrzne");

        UserAccountDto dto = userAccountMapper.toDto(account);
        System.out.println("UserAccount.internalNotes = '" + account.internalNotes() + "'");
        System.out.println("UserAccountDto (internalNotes zignorowane): " + dto);
    }

    /*
     * ============================================================
     * 🔍 WLASNE METODY KONWERSJI (default) - LocalDate <-> String
     * ============================================================
     * MapStruct nie wie SAM, jak zamienic LocalDate na String wedlug
     * konkretnego wzorca (ani odwrotnie) - to decyzja biznesowa, nie
     * uniwersalna regula. Rozwiazanie: dwie metody "default" WEWNATRZ
     * interfejsu @Mapper, obie nazwane tak samo (map) ale z rozna
     * sygnatura (LocalDate -> String oraz String -> LocalDate).
     * MapStruct AUTOMATYCZNIE dopasowuje odpowiednia z nich do pola
     * "eventDate" w KAZDYM kierunku (Event -> EventDto oraz
     * EventDto -> Event) - bez zadnej adnotacji @Mapping, wystarczy, ze
     * nazwa pola sie zgadza, a sygnatura metody pasuje do typow zrodlo/cel.
     */
    private static void demonstrateCustomConversionMethods() {
        System.out.println("\n=== Wlasne metody 'default' - konwersja LocalDate <-> String ===");

        EventMapper eventMapper = Mappers.getMapper(EventMapper.class);

        Event event = new Event(1L, "Konferencja JavaQuest", LocalDate.of(2026, 9, 15));
        EventDto eventDto = eventMapper.toDto(event);
        System.out.println("Event.eventDate (LocalDate) = " + event.eventDate());
        System.out.println("EventDto.eventDate (String, wzorzec dd-MM-yyyy) = " + eventDto.eventDate());

        Event backToEntity = eventMapper.toEntity(eventDto);
        System.out.println("Powrot EventDto -> Event, eventDate sparsowany z powrotem na LocalDate: "
                + backToEntity.eventDate());
    }

    /*
     * ============================================================
     * 🔹 @MappingTarget - AKTUALIZACJA ISTNIEJACEGO OBIEKTU (PATCH)
     * ============================================================
     * Do tej pory kazda metoda mappera TWORZYLA nowy obiekt docelowy.
     * @MappingTarget odwraca ten schemat: metoda ma typ zwracany void,
     * przyjmuje DTO ze zmianami ORAZ istniejacy obiekt oznaczony
     * @MappingTarget - MapStruct generuje kod, ktory dla kazdego pola
     * obecnego w DTO wywoluje odpowiedni SETTER na przekazanym obiekcie,
     * MUTUJAC go w miejscu. Pola, ktorych DTO nie ma (tutaj: id, email),
     * pozostaja NIETKNIETE. To dokladnie wzorzec czesciowej aktualizacji
     * (PATCH) znany z API REST.
     */
    private static void demonstrateMappingTargetUpdate() {
        System.out.println("\n=== @MappingTarget - aktualizacja istniejacego obiektu (wzorzec PATCH) ===");

        UserProfileMapper profileMapper = Mappers.getMapper(UserProfileMapper.class);

        UserProfile profile = new UserProfile("U1", "stary_nick", "Stara notka biograficzna", "user@example.com");
        System.out.println("PRZED aktualizacja: " + profile);

        UserProfileUpdateDto patch = new UserProfileUpdateDto("nowy_nick", "Nowa notka biograficzna");
        profileMapper.updateProfileFromDto(patch, profile);

        System.out.println("PO aktualizacji (id i email BEZ ZMIAN, displayName/bio zaktualizowane): " + profile);
    }

    /*
     * ============================================================
     * 🔍 MAPOWANIE MIEDZY ROZNYMI TYPAMI KOLEKCJI - List<T> -> Set<R>
     * ============================================================
     * Dokladnie tak, jak w Lekcji 21 MapStruct sam wygenerowal metode
     * mapujaca List<Employee> -> List<EmployeeDto>, potrafi tez
     * wygenerowac mapowanie MIEDZY roznymi ksztaltami kolekcji po obu
     * stronach - tutaj List<Tag> (zrodlo) -> Set<TagDto> (cel). Element
     * po elemencie nadal korzysta z metody toDto(Tag), a caloscia
     * (wybor implementacji Set, dodawanie elementow) zajmuje sie
     * wygenerowany kod.
     */
    private static void demonstrateDifferentCollectionTypes() {
        System.out.println("\n=== List<Encja> -> Set<Dto> - rozne typy kolekcji po obu stronach ===");

        TagMapper tagMapper = Mappers.getMapper(TagMapper.class);
        List<Tag> tags = List.of(
                new Tag(1L, "java"),
                new Tag(2L, "mapstruct"),
                new Tag(3L, "backend"));

        Set<TagDto> tagDtos = tagMapper.toDtoSet(tags);
        System.out.println("Wejscie: List<Tag> o rozmiarze " + tags.size());
        System.out.println("Wyjscie: Set<TagDto> o rozmiarze " + tagDtos.size() + " -> " + tagDtos);
    }

    // ============================================================
    // Modele domenowe + interfejsy mapperow (nested, jak w Lekcji 21)
    // ============================================================

    /** Encja zagniezdzona - klient powiazany z zamowieniem. */
    public static class Customer {
        private final Long id;
        private final String name;
        private final String email;

        public Customer(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return "Customer{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }

    public record CustomerDto(Long id, String name, String email) {
    }

    /** Encja glowna - zawiera zagniezdzonego Customer. */
    public static class Order {
        private final Long id;
        private final Customer customer;
        private final double totalAmount;

        public Order(Long id, Customer customer, double totalAmount) {
            this.id = id;
            this.customer = customer;
            this.totalAmount = totalAmount;
        }

        public Long getId() {
            return id;
        }

        public Customer getCustomer() {
            return customer;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        @Override
        public String toString() {
            return "Order{id=" + id + ", customer=" + customer + ", totalAmount=" + totalAmount + "}";
        }
    }

    public record OrderDto(Long id, CustomerDto customer, double totalAmount) {
    }

    /**
     * Mapper z DWIEMA metodami toDto - MapStruct sam uzyje toDto(Customer)
     * do zmapowania zagniezdzonego pola "customer" w toDto(Order).
     */
    @Mapper
    public interface OrderMapper {
        OrderDto toDto(Order order);

        CustomerDto toDto(Customer customer);
    }

    /** Value object - adres, uzywany zarowno samodzielnie, jak i w Company. */
    public static class Address {
        private final String street;
        private final String city;
        private final String zipCode;

        public Address(String street, String city, String zipCode) {
            this.street = street;
            this.city = city;
            this.zipCode = zipCode;
        }

        public String getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }

        public String getZipCode() {
            return zipCode;
        }
    }

    public record AddressDto(String street, String city, String zipCode) {
    }

    /** Samodzielny mapper Address - bedzie SKLADANY do CompanyMapper przez uses. */
    @Mapper
    public interface AddressMapper {
        AddressDto toDto(Address address);
    }

    public static class Company {
        private final String name;
        private final Address headquarters;

        public Company(String name, Address headquarters) {
            this.name = name;
            this.headquarters = headquarters;
        }

        public String getName() {
            return name;
        }

        public Address getHeadquarters() {
            return headquarters;
        }
    }

    public record CompanyDto(String name, AddressDto headquarters) {
    }

    /** CompanyMapper NIE ma wlasnej logiki dla Address -> AddressDto - korzysta z AddressMapper. */
    @Mapper(uses = {AddressMapper.class})
    public interface CompanyMapper {
        CompanyDto toDto(Company company);
    }

    public record Product(Long id, String name, String sku) {
    }

    public record ProductDto(Long id, String name, String sku) {
    }

    /**
     * Mapper z dwiema metodami "default" o identycznej sygnaturze String -> String -
     * @Named + qualifiedByName jednoznacznie wskazuje, ktorej uzyc dla pola "sku".
     */
    @Mapper
    public interface ProductMapper {

        @Mapping(target = "sku", qualifiedByName = "normalizeSku")
        ProductDto toDto(Product product);

        @Named("normalizeSku")
        default String normalizeSku(String sku) {
            return sku == null ? null : sku.trim().toUpperCase();
        }

        @Named("shoutName")
        default String shoutName(String value) {
            return value == null ? null : value.toUpperCase() + "!!!";
        }
    }

    public record Ticket(Long id, String priority) {
    }

    public record TicketDto(Long id, String priority) {
    }

    @Mapper
    public interface TicketMapper {

        @Mapping(target = "priority", defaultValue = "NORMALNY")
        TicketDto toDto(Ticket ticket);
    }

    public record UserAccount(Long id, String username, String internalNotes) {
    }

    public record UserAccountDto(Long id, String username, String internalNotes) {
    }

    @Mapper
    public interface UserAccountMapper {

        @Mapping(target = "internalNotes", ignore = true)
        UserAccountDto toDto(UserAccount account);
    }

    public record Event(Long id, String title, LocalDate eventDate) {
    }

    public record EventDto(Long id, String title, String eventDate) {
    }

    /**
     * Mapper z dwiema przeciazonymi metodami "default" (map) - MapStruct
     * sam dobiera odpowiednia dla pola eventDate w OBU kierunkach.
     */
    @Mapper
    public interface EventMapper {

        EventDto toDto(Event event);

        Event toEntity(EventDto dto);

        default String map(LocalDate date) {
            return date == null ? null : date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        default LocalDate map(String date) {
            return (date == null || date.isBlank())
                    ? null
                    : LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
    }

    /** Mutowalna klasa (gettery + settery) - wymagana dla celu @MappingTarget. */
    public static class UserProfile {
        private String id;
        private String displayName;
        private String bio;
        private String email;

        public UserProfile(String id, String displayName, String bio, String email) {
            this.id = id;
            this.displayName = displayName;
            this.bio = bio;
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "UserProfile{id='" + id + "', displayName='" + displayName + "', bio='" + bio
                    + "', email='" + email + "'}";
        }
    }

    /** DTO czesciowej aktualizacji - celowo BEZ pol id/email. */
    public record UserProfileUpdateDto(String displayName, String bio) {
    }

    @Mapper
    public interface UserProfileMapper {
        void updateProfileFromDto(UserProfileUpdateDto dto, @MappingTarget UserProfile profile);
    }

    public record Tag(Long id, String label) {
    }

    public record TagDto(Long id, String label) {
    }

    @Mapper
    public interface TagMapper {
        TagDto toDto(Tag tag);

        Set<TagDto> toDtoSet(List<Tag> tags);
    }
}
