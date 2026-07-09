package com.example.javaquest._13_libraries.Lesson21_MapStructBasics;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public class _Lesson21_MapStructBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 21: MAPSTRUCT - PODSTAWY ===");

        /*
         * ============================================================
         * 📦 PROBLEM: RĘCZNE MAPOWANIE ENCJA <-> DTO
         * ============================================================
         * W lekcji _09_jdbc/Lesson19_Dto poznaliśmy DTO (Data Transfer
         * Object) - obiekt do przenoszenia danych między warstwami, np.
         * encja domenowa User -> UserResponse (bez wrażliwych pól).
         * Tamto mapowanie napisaliśmy RĘCZNIE:
         *
         *   private static UserResponse toResponse(User user) {
         *       return new UserResponse(user.id(), user.email(), user.firstName());
         *   }
         *
         * Dla 3 pól to żaden problem. Ale w realnych projektach encje
         * mają dziesiątki pól, a DTO są dziesiątki (jeden na każdy
         * endpoint API). Ręczne mapowanie oznacza:
         * - MASĘ powtarzalnego, nudnego kodu (getter -> setter, pole
         *   po polu) w każdym mapperze,
         * - ŁATWO o błąd - dodajesz nowe pole do encji, zapominasz
         *   dopisać je w metodzie mapującej -> cichy błąd (pole zawsze
         *   puste w DTO), kompilator NIC nie powie,
         * - przy zmianie nazwy pola w jednym miejscu trzeba pamiętać
         *   o wszystkich mapperach, które go używają.
         *
         * 🔍 CZYM JEST MAPSTRUCT?
         * MapStruct to biblioteka, która GENERUJE implementację mappera
         * w CZASIE KOMPILACJI (annotation processing) - Ty piszesz tylko
         * INTERFEJS opisujący "z czego na co mapować", a MapStruct
         * dopisuje prawdziwą klasę z kodem `target.setPole(source.getPole())`
         * dla każdego pola. To NIE jest refleksja w runtime (jak np.
         * popularny ModelMapper) - wygenerowany kod wygląda tak, jakby
         * napisał go człowiek, więc wydajność jest praktycznie identyczna
         * jak przy ręcznym mapowaniu (brak narzutu refleksji przy KAŻDYM
         * wywołaniu), a błędy (np. literówka w nazwie pola) wychodzą na
         * etapie KOMPILACJI, a nie dopiero w runtime.
         */

        /*
         * ============================================================
         * 🔹 PIERWSZY MAPPER: @Mapper + Mappers.getMapper
         * ============================================================
         * Interfejs oznaczony @Mapper (poniżej: EmployeeMapper, zdefiniowany
         * jako zagnieżdżony "static interface" na końcu tego pliku) deklaruje
         * METODĘ ABSTRAKCYJNĄ: TargetType toTarget(SourceType source).
         * MapStruct w czasie kompilacji generuje klasę EmployeeMapperImpl
         * implementującą ten interfejs - dla pól o TEJ SAMEJ nazwie i
         * (kompatybilnym) typie w obu klasach mapowanie dzieje się
         * AUTOMATYCZNIE, bez żadnej dodatkowej adnotacji.
         *
         * Instancję wygenerowanego mappera pobieramy przez
         * Mappers.getMapper(EmployeeMapper.class) - standardowy wzorzec
         * MapStruct (fabryka szuka wygenerowanej klasy XxxMapperImpl w tym
         * samym pakiecie). Działa też dla interfejsów ZAGNIEŻDŻONYCH (jak
         * tutaj) - wygenerowana klasa implementacji i tak ląduje w pakiecie
         * TOP-LEVEL tej lekcji, nie zagnieżdżona.
         */
        EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

        Employee employee = new Employee(1L, "Anna", "Kowalska", 8500.0);
        System.out.println("\n=== KROK 1: ENCJA Employee ===");
        System.out.println(employee);

        EmployeeDto dto = employeeMapper.toDto(employee);
        System.out.println("\n=== KROK 2: DTO wygenerowane przez MapStruct (id, salary - te sama nazwa/typ) ===");
        System.out.println(dto);

        /*
         * ============================================================
         * 🔹 @Mapping(source = ..., target = ...) - gdy nazwy się różnią
         * ============================================================
         * Pola id oraz salary mają w Employee i EmployeeDto identyczne
         * nazwy i typy - MapStruct zmapował je BEZ żadnej adnotacji.
         * Ale EmployeeDto ma jedno pole fullName ZAMIAST dwóch osobnych
         * pól firstName/lastName z Employee - trzeba jawnie powiedzieć
         * MapStructowi, JAK je połączyć. Służy do tego @Mapping z
         * atrybutem "expression" - dowolny fragment kodu Javy wstawiany
         * WPROST do wygenerowanej metody (patrz deklaracja EmployeeMapper
         * niżej w pliku):
         *
         *   @Mapping(target = "fullName",
         *             expression = "java(employee.getFirstName() + \" \" + employee.getLastName())")
         *   EmployeeDto toDto(Employee employee);
         *
         * Zwróć uwagę na "target" - to nazwa pola w DTO (fullName), NIE w
         * encji źródłowej. Parametr metody (tu: "employee") jest dostępny
         * wewnątrz wyrażenia "java(...)" pod nazwą, jaką nadaliśmy mu w
         * sygnaturze metody.
         */
        System.out.println("\n=== KROK 3: fullName = firstName + lastName (@Mapping z expression) ===");
        System.out.println("fullName w DTO: " + dto.fullName());

        /*
         * ============================================================
         * 🔍 MAPOWANIE LIST - MapStruct SAM generuje pętlę
         * ============================================================
         * Gdybyśmy mapowali List<Employee> ręcznie, musielibyśmy pisać
         * pętlę/stream wywołujący toDto() dla każdego elementu. MapStruct
         * potrafi wygenerować TAKĄ metodę SAM - wystarczy zadeklarować w
         * interfejsie kolejną metodę abstrakcyjną:
         *
         *   List<EmployeeDto> toDtoList(List<Employee> employees);
         *
         * MapStruct rozpozna, że wewnątrz listy trzeba zmapować
         * Employee -> EmployeeDto, i UŻYJE do tego metody toDto() z tego
         * samego interfejsu (ponowne wykorzystanie mapowania pojedynczego
         * elementu) - wygenerowana implementacja to po prostu pętla
         * "for" wywołująca toDto() na każdym elemencie.
         */
        List<Employee> employees = List.of(
                new Employee(1L, "Anna", "Kowalska", 8500.0),
                new Employee(2L, "Jan", "Nowak", 7200.0),
                new Employee(3L, "Maria", "Wiśniewska", 9100.0));

        List<EmployeeDto> dtos = employeeMapper.toDtoList(employees);
        System.out.println("\n=== KROK 4: MAPOWANIE LISTY (MapStruct sam wygenerował petle) ===");
        dtos.forEach(System.out::println);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - MapStruct generuje implementację mappera W CZASIE KOMPILACJI
         *   (annotation processing) - to NIE jest refleksja w runtime,
         *   stąd wydajność zbliżona do ręcznego kodu.
         * - Interfejs z @Mapper + metoda abstrakcyjna TargetType toTarget(Source)
         *   - MapStruct sam mapuje pola o TEJ SAMEJ nazwie i typie.
         * - Instancję pobieramy przez Mappers.getMapper(XxxMapper.class)
         *   - działa też dla interfejsów zagnieżdżonych.
         * - @Mapping(source = "...", target = "...") - gdy nazwy pól się
         *   różnią; @Mapping(target = "...", expression = "java(...)") -
         *   gdy trzeba POŁĄCZYĆ/PRZEKSZTAŁCIĆ kilka pól w jedno.
         * - Mapowanie kolekcji (List<Target> toTargetList(List<Source>))
         *   MapStruct generuje SAM, ponownie wykorzystując mapowanie
         *   pojedynczego elementu.
         * - Błędy w mapowaniu (np. literówka w nazwie pola przy source/target)
         *   wychodzą na etapie KOMPILACJI, nie dopiero w runtime - ogromna
         *   przewaga nad ręcznym mapowaniem i nad mapperami opartymi na
         *   refleksji.
         */

        System.out.println("\n=== KONIEC LEKCJI 21 ===");
    }

    /** Encja domenowa - reprezentuje pracownika w systemie. */
    public static class Employee {

        private final Long id;
        private final String firstName;
        private final String lastName;
        private final double salary;

        public Employee(Long id, String firstName, String lastName, double salary) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.salary = salary;
        }

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "Employee{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName
                    + "', salary=" + salary + "}";
        }
    }

    /** DTO wyjściowe - id/salary jak w encji, ale fullName ZAMIAST firstName+lastName. */
    public record EmployeeDto(Long id, String fullName, double salary) {
    }

    /**
     * Mapper MapStruct - interfejs zagnieżdżony (niejawnie static). MapStruct
     * wygeneruje w czasie kompilacji klasę EmployeeMapperImpl implementującą
     * ten interfejs, w pakiecie TOP-LEVEL tej lekcji (nie zagnieżdżoną).
     */
    @Mapper
    public interface EmployeeMapper {

        @Mapping(target = "fullName",
                expression = "java(employee.getFirstName() + \" \" + employee.getLastName())")
        EmployeeDto toDto(Employee employee);

        List<EmployeeDto> toDtoList(List<Employee> employees);
    }
}
