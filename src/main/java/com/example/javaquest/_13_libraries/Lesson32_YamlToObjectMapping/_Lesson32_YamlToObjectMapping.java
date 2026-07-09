package com.example.javaquest._13_libraries.Lesson32_YamlToObjectMapping;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.FieldProperty;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

public class _Lesson32_YamlToObjectMapping {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 32: SNAKEYAML - MAPOWANIE YAML NA OBIEKTY JAVY ===");

        /*
         * ============================================================
         * 📦 KONTYNUACJA LEKCJI 31
         * ============================================================
         * W poprzedniej lekcji yaml.load(...) zwracal ZAWSZE ogolne
         * struktury: Map<String, Object> / List<Object>. Dziala to
         * uniwersalnie, ale w praktyce (dokladnie jak przy Gson/Jackson w
         * _04_io/Lesson20_Gson i Lesson21_Jackson) wygodniej jest
         * pracowac z WLASNA klasa Javy - z podpowiedziami typow w IDE,
         * bezpieczenstwem typow w czasie kompilacji i bez recznego
         * rzutowania kazdego pola. SnakeYAML potrafi mapowac YAML
         * BEZPOSREDNIO na taka klase (POJO) - i dziala to na TEJ SAMEJ
         * zasadzie, co POJO-binding w Gson/Jackson: klasa docelowa MUSI
         * miec publiczny konstruktor BEZPARAMETROWY oraz publiczne
         * settery (lub publiczne pola) dla kazdej mapowanej wlasciwosci.
         */

        demonstrateLoadAs();
        demonstrateExplicitConstructor();
        demonstrateNestedListAutoResolved();
        demonstrateTypeErasureProblem();
        demonstrateTypeDescriptionFix();
        demonstrateFieldOrderDump();
        demonstrateFlowStyleOptions();
        printSnakeYamlVsJacksonYaml();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - yaml.loadAs(String, MyClass.class) mapuje YAML BEZPOSREDNIO
         *   na wlasna klase - wymaga konstruktora bezparametrowego oraz
         *   publicznych setterow (lub pol) - dokladnie ta sama zasada,
         *   co POJO-binding w Gson/Jackson z _04_io.
         * - Do WIEKSZEJ kontroli (m.in. do rejestrowania TypeDescription)
         *   buduje sie Yaml z jawnym Constructor + LoaderOptions:
         *   new Yaml(new Constructor(MyClass.class, new LoaderOptions())).
         * - Pole List<Address> zadeklarowane BEZPOSREDNIO w klasie (z
         *   konkretnym parametrem typu w kodzie) SnakeYAML odczytuje
         *   POPRAWNIE bez zadnej dodatkowej konfiguracji - biblioteka
         *   sama odczytuje generyczna sygnature pola przez refleksje.
         * - PRAWDZIWY problem type erasure pojawia sie, gdy konkretny
         *   parametr typu NIE jest zapisany wprost na polu - np. gdy pole
         *   pochodzi z klasy GENERYCZNEJ (List<T> w klasie bazowej), a
         *   konkretny typ (T = Address) jest okreslony dopiero w klasie
         *   POCHODNEJ (przez dziedziczenie). W takim przypadku refleksja
         *   widzi tylko zmienna typu T, a SnakeYAML tworzy dla kazdego
         *   elementu zwykla LinkedHashMap zamiast wlasciwej klasy.
         *   TypeDescription + addPropertyParameters("pole", Klasa.class)
         *   jawnie mowi wtedy SnakeYAML, jakiego typu sa elementy -
         *   to NARZEDZIE OGOLNEGO PRZEZNACZENIA, ktore dziala zawsze
         *   (rowniez tam, gdzie automatyczne wykrycie by zadzialalo), ale
         *   jest NIEZBEDNE dokladnie w takich przypadkach prawdziwej
         *   utraty informacji o typie.
         * - Domyslny Representer (uzywany przy yaml.dump(obiekt)) zawsze
         *   porzadkuje wlasciwosci ALFABETYCZNIE wg nazwy (wewnetrznie
         *   uzywa posortowanego TreeSet) - NIEZALEZNIE od tego, czy
         *   odczytuje je z getterow/setterow, czy z pol (BeanAccess.FIELD
         *   samo w sobie NIE zmienia kolejnosci, tylko zrodlo odczytu).
         *   Zeby naprawde zachowac kolejnosc DEKLARACJI pol, trzeba
         *   podstawic WLASNY PropertyUtils zwracajacy LinkedHashSet
         *   (zamiast TreeSet) zbudowany z Class.getDeclaredFields() -
         *   patrz DeclaredOrderPropertyUtils ponizej w tym pliku.
         * - DumperOptions.setDefaultFlowStyle(...) kontroluje styl
         *   wyjscia: BLOCK (czytelny, wieloliniowy - domyslny wybor dla
         *   plikow konfiguracyjnych) vs FLOW (zwarty, jednoliniowy,
         *   przypomina skladnie JSON-a).
         * - SnakeYAML to podejscie NISKOPOZIOMOWE i RECZNE (Constructor,
         *   TypeDescription, Representer trzeba skonfigurowac samemu).
         *   Biblioteka jackson-dataformat-yaml (NIEUZywana w tym kursie)
         *   dodaje modul YAML do Jacksona znanego z _04_io/Lesson21_Jackson -
         *   API wygladaloby WTEDY identycznie jak
         *   ObjectMapper.readValue/writeValueAsString, tylko z innym
         *   ObjectMapperem (YAMLMapper) pod spodem. Wybor miedzy nimi to
         *   typowy kompromis: SnakeYAML - lekko, bez dodatkowych
         *   zaleznosci Jacksona, wiecej kontroli recznej; Jackson YAML -
         *   spojne API z reszta projektu, jesli Jackson juz jest obecny
         *   (jak w tym projekcie - patrz _04_io/Lesson21_Jackson).
         *
         * To byla OSTATNIA lekcja rozdzialu _13_libraries ("Biblioteki
         * zewnetrzne w Javie"). Rozdzial przeszedl przez redukcje
         * boilerplate'u (Lombok), narzedzia ogolnego przeznaczenia
         * (Apache Commons, Guava), klienta HTTP (OkHttp), logowanie
         * (SLF4J), Dependency Injection (Guice), mapowanie obiektow
         * (MapStruct), pliki Excel (Apache POI), parsowanie HTML (Jsoup),
         * cache (Caffeine) i na koniec format YAML (SnakeYAML) - kazda z
         * nich jako przyklad tego, jak swiadomie dobierac i wykorzystywac
         * gotowe biblioteki zamiast pisac wszystko od zera (wracajac do
         * pytania postawionego na samym poczatku rozdzialu, w Lekcji 1).
         */
        System.out.println("\n=== KONIEC LEKCJI 32 ===");
        System.out.println("=== KONIEC ROZDZIALU _13_libraries ===");
    }

    /*
     * ============================================================
     * 🔹 yaml.loadAs(String, Class) - MAPOWANIE NA PROSTE POJO
     * ============================================================
     * ServerConfig (zdefiniowana nizej) to zwykla klasa JavaBean:
     * konstruktor bezparametrowy + gettery/settery dla kazdego pola.
     * yaml.loadAs(tekst, ServerConfig.class) dziala jak Gson.fromJson(
     * json, MyClass.class) z _04_io/Lesson20_Gson - SnakeYAML dla
     * kazdego klucza YAML szuka SETTERA o pasujacej nazwie
     * (setHost, setPort, setDebug) i go wywoluje.
     */
    private static void demonstrateLoadAs() {
        System.out.println("\n=== yaml.loadAs(String, Class) - proste POJO (ServerConfig) ===");

        String yamlText = """
                host: localhost
                port: 8080
                debug: true
                """;

        Yaml yaml = new Yaml();
        ServerConfig config = yaml.loadAs(yamlText, ServerConfig.class);
        System.out.println("Wejscie YAML:\n" + yamlText);
        System.out.println("Wynik loadAs -> " + config);
    }

    /*
     * ============================================================
     * 🔍 JAWNY Constructor + LoaderOptions - WIEKSZA KONTROLA
     * ============================================================
     * loadAs to skrot - pod spodem SnakeYAML i tak buduje obiekt klasy
     * org.yaml.snakeyaml.constructor.Constructor. Gdy potrzebujemy
     * WIECEJ kontroli (m.in. zeby zarejestrowac TypeDescription w
     * kolejnym kroku), budujemy Constructor SAMODZIELNIE - wymaga on
     * LoaderOptions (od pewnej wersji SnakeYAML jawny LoaderOptions jest
     * OBOWIAZKOWY - m.in. ze wzgledu na limity bezpieczenstwa przy
     * parsowaniu, np. maksymalna glebokosc zagniezdzenia aliasow YAML).
     * Majac Constructor z okreslonym "rootem" (Person.class), yaml.load(...)
     * zwraca JUZ obiekt typu Person - bez potrzeby wywolywania loadAs.
     */
    private static void demonstrateExplicitConstructor() {
        System.out.println("\n=== Jawny Constructor + LoaderOptions - yaml.load() zwraca od razu Person ===");

        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor personConstructor = new Constructor(Person.class, loaderOptions);
        Yaml yaml = new Yaml(personConstructor);

        String yamlText = """
                name: Anna Kowalska
                age: 30
                """;

        Person person = yaml.load(yamlText);
        System.out.println("Wynik yaml.load() z jawnym Constructor(Person.class, ...) -> " + person);
    }

    /*
     * ============================================================
     * 🔍 List<Address> ZADEKLAROWANE WPROST - DZIALA BEZ TypeDescription
     * ============================================================
     * Pole Person.addresses ma typ List<Address> zapisany BEZPOSREDNIO
     * w kodzie klasy Person. SnakeYAML odczytuje generyczna sygnature
     * TEGO KONKRETNEGO pola przez refleksje (Field.getGenericType()) i
     * SAM rozpoznaje, ze elementy listy powinny byc typu Address - bez
     * jakiejkolwiek dodatkowej konfiguracji. To wygodna, ale czesto
     * mylnie uogolniana wlasciwosc nowszych wersji SnakeYAML - patrz
     * kolejna sekcja, gdzie POKAZANY jest przypadek, w ktorym to
     * automatyczne wykrywanie faktycznie zawodzi.
     */
    private static void demonstrateNestedListAutoResolved() {
        System.out.println("\n=== List<Address> zadeklarowane wprost na polu - dziala automatycznie ===");

        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(Person.class, loaderOptions);
        Yaml yaml = new Yaml(constructor);

        String yamlText = """
                name: Jan Nowak
                age: 42
                addresses:
                  - street: Marszalkowska 1
                    city: Warszawa
                  - street: Dluga 5
                    city: Gdansk
                """;

        Person person = yaml.load(yamlText);
        Object pierwszyElement = person.getAddresses().get(0);
        System.out.println("Typ elementu listy addresses (BEZ TypeDescription): "
                + pierwszyElement.getClass().getSimpleName() + " - poprawnie rozpoznany jako Address!");
        System.out.println("Person -> " + person);
    }

    /*
     * ============================================================
     * 🔍 PRAWDZIWY PROBLEM TYPE ERASURE - GENERYCZNA KLASA BAZOWA
     * ============================================================
     * Container<T> (ponizej) deklaruje pole List<T> items - w kodzie
     * TEJ klasy typ elementu to zmienna typu T, nie konkretna klasa.
     * AddressBox extends Container<Address> okresla T = Address, ale
     * TA informacja istnieje TYLKO na poziomie dziedziczenia (w
     * deklaracji "extends Container<Address>"), NIE na samym polu
     * "items" (ktore w klasie Container nadal jest zadeklarowane jako
     * List<T>). To jest WLASNIE type erasure w czystej postaci -
     * refleksja odczytujaca pole "items" (nawet na obiekcie klasy
     * AddressBox) widzi tylko "List<T>", bez ladu, jaki typ podstawic
     * za T. SnakeYAML nie ma zatem jak sam wywnioskowac Address - dla
     * kazdego elementu listy tworzy zwykla java.util.LinkedHashMap.
     */
    private static void demonstrateTypeErasureProblem() {
        System.out.println("\n=== PRAWDZIWY PROBLEM: generyczne pole odziedziczone (Container<T> -> AddressBox) ===");

        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(AddressBox.class, loaderOptions);
        Yaml yaml = new Yaml(constructor);

        String yamlText = """
                items:
                  - street: Dluga 5
                    city: Gdansk
                  - street: Krotka 2
                    city: Poznan
                """;

        AddressBox box = yaml.load(yamlText);
        Object pierwszyElement = box.getItems().get(0);
        System.out.println("Deklaracja pola 'items' pochodzi z Container<T> (typ elementu: zmienna T)");
        System.out.println("Rzeczywisty typ elementu listy (BEZ TypeDescription): "
                + pierwszyElement.getClass().getSimpleName() + "  <-- to NIE jest Address!");
    }

    /*
     * ============================================================
     * 🔹 TypeDescription - NAPRAWA PROBLEMU TYPE ERASURE
     * ============================================================
     * TypeDescription pozwala jawnie POWIEDZIEC SnakeYAML, jakiego typu
     * sa elementy generycznej kolekcji we WSKAZANEJ wlasciwosci klasy:
     * addPropertyParameters("items", Address.class) mowi "wlasciwosc
     * items (w AddressBox) to kolekcja elementow typu Address" -
     * niezaleznie od tego, ze pole "items" fizycznie znajduje sie w
     * klasie bazowej Container. Zarejestrowana na Constructor
     * (constructor.addTypeDescription(...)) informacja jest uzywana
     * PRZY KAZDYM load() wykonanym przez ten Yaml.
     */
    private static void demonstrateTypeDescriptionFix() {
        System.out.println("\n=== NAPRAWA: TypeDescription.addPropertyParameters(...) ===");

        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(AddressBox.class, loaderOptions);

        TypeDescription addressBoxDescription = new TypeDescription(AddressBox.class);
        addressBoxDescription.addPropertyParameters("items", Address.class);
        constructor.addTypeDescription(addressBoxDescription);

        Yaml yaml = new Yaml(constructor);

        String yamlText = """
                items:
                  - street: Dluga 5
                    city: Gdansk
                  - street: Krotka 2
                    city: Poznan
                """;

        AddressBox box = yaml.load(yamlText);
        Object pierwszyElement = box.getItems().get(0);
        System.out.println("Rzeczywisty typ elementu listy (Z TypeDescription): "
                + pierwszyElement.getClass().getSimpleName());
        for (Address address : box.getItems()) {
            System.out.println("  - " + address);
        }
    }

    /*
     * ============================================================
     * 🔍 KOLEJNOSC POL PRZY dump() - DOMYSLNIE ZAWSZE ALFABETYCZNA
     * ============================================================
     * Domyslny Representer (uzywany przez yaml.dump(obiekt)) pobiera
     * liste wlasciwosci z PropertyUtils.getProperties(...), a ta metoda
     * WEWNETRZNIE zwraca java.util.TreeSet - czyli KOLEJNOSC JEST ZAWSZE
     * ALFABETYCZNA wg nazwy wlasciwosci, bez wzgledu na to, czy
     * wlasciwosci sa odczytywane z getterow/setterow, czy z pol wprost
     * (BeanAccess.FIELD zmienia tylko ZRODLO odczytu wartosci, NIE
     * kolejnosc sortowania). Zeby naprawde zachowac kolejnosc DEKLARACJI
     * pol w klasie (name, age, addresses - tak jak w kodzie Person),
     * trzeba podstawic WLASNY PropertyUtils (patrz zagniezdzona klasa
     * DeclaredOrderPropertyUtils nizej), ktory zamiast TreeSet zwraca
     * LinkedHashSet zbudowany w kolejnosci Class.getDeclaredFields().
     */
    private static void demonstrateFieldOrderDump() {
        System.out.println("\n=== Kolejnosc pol przy dump() - domyslna (alfabetyczna) vs wlasny PropertyUtils ===");

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Person person = new Person();
        person.setName("Ewa Zielinska");
        person.setAge(27);
        person.setAddresses(List.of(new Address("Kwiatowa 2", "Krakow")));

        Yaml domyslnyYaml = new Yaml(dumperOptions);
        System.out.println("Dump DOMYSLNY (kolejnosc ZAWSZE alfabetyczna - addresses, age, name):");
        System.out.println(domyslnyYaml.dump(person));

        Representer representer = new Representer(dumperOptions);
        representer.setPropertyUtils(new DeclaredOrderPropertyUtils());
        Yaml fieldOrderYaml = new Yaml(representer, dumperOptions);
        System.out.println("Dump z wlasnym PropertyUtils (kolejnosc DEKLARACJI pol - name, age, addresses):");
        System.out.println(fieldOrderYaml.dump(person));
    }

    /*
     * ============================================================
     * 🔹 DumperOptions.setDefaultFlowStyle(...) - BLOCK vs FLOW
     * ============================================================
     * FlowStyle.BLOCK to czytelny, wieloliniowy styl znany z Lekcji 31
     * (kazdy klucz/element w osobnej linii) - naturalny wybor dla
     * plikow konfiguracyjnych czytanych przez ludzi. FlowStyle.FLOW
     * generuje zwarty, "inline" zapis przypominajacy JSON (nawiasy {}/[],
     * przecinki) - przydatny np. przy logowaniu krotkich struktur w
     * jednej linii.
     */
    private static void demonstrateFlowStyleOptions() {
        System.out.println("\n=== DumperOptions.setDefaultFlowStyle(BLOCK vs FLOW) ===");

        Map<String, Object> dane = new LinkedHashMap<>();
        dane.put("nazwa", "JavaQuest");
        dane.put("wersja", 32);
        dane.put("tagi", List.of("kurs", "java", "biblioteki"));

        DumperOptions blockOptions = new DumperOptions();
        blockOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        System.out.println("BLOCK:\n" + new Yaml(blockOptions).dump(dane));

        DumperOptions flowOptions = new DumperOptions();
        flowOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
        System.out.println("FLOW:\n" + new Yaml(flowOptions).dump(dane));
    }

    /*
     * ============================================================
     * 📌 SNAKEYAML VS JACKSON YAML (jackson-dataformat-yaml)
     * ============================================================
     * Ta biblioteka NIE jest dodana do projektu (samo API opisane
     * ponizej dla porownania) - Jackson byl juz uzyty do JSON-a w
     * _04_io/Lesson21_Jackson przez ObjectMapper.readValue/writeValueAsString.
     */
    private static void printSnakeYamlVsJacksonYaml() {
        System.out.println("\n=== POROWNANIE: SnakeYAML vs jackson-dataformat-yaml (NIEUZywana w tym kursie) ===");
        String format = "%-24s | %-45s%n";
        System.out.printf(format, "Cecha", "SnakeYAML vs Jackson YAML");
        System.out.println("-".repeat(75));
        System.out.printf(format, "Poziom API", "SnakeYAML - niskopoziomowy, reczna konfiguracja");
        System.out.printf(format, "", "Jackson YAML - ObjectMapper.readValue/writeValueAsString (jak JSON)");
        System.out.printf(format, "Generyczne kolekcje", "SnakeYAML - reczny TypeDescription");
        System.out.printf(format, "", "Jackson YAML - adnotacje @JsonProperty/TypeReference jak w _04_io");
        System.out.printf(format, "Sposob uzycia", "Identyczny styl API co Jackson JSON, inny 'silnik' (YAMLMapper)");
        System.out.printf(format, "Kiedy wybrac", "SnakeYAML - lekko, bez Jacksona; Jackson YAML - spojnosc z JSON-em");
    }

    /** Prosta konfiguracja serwera - klasyczny JavaBean do yaml.loadAs(...). */
    public static class ServerConfig {

        private String host;
        private int port;
        private boolean debug;

        public ServerConfig() {
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        @Override
        public String toString() {
            return "ServerConfig{host='" + host + "', port=" + port + ", debug=" + debug + "}";
        }
    }

    /** Value object - adres, uzywany jako element listy w Person oraz w AddressBox. */
    public static class Address {

        private String street;
        private String city;

        public Address() {
        }

        public Address(String street, String city) {
            this.street = street;
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Address{street='" + street + "', city='" + city + "'}";
        }
    }

    /**
     * Pole "addresses" ma typ List<Address> zadeklarowany WPROST na tym polu
     * (nie odziedziczony z klasy generycznej) - dlatego SnakeYAML poprawnie
     * odczytuje typ elementu automatycznie, bez TypeDescription.
     * Kolejnosc pol: name -> age -> addresses (uzywane w demo kolejnosci dump).
     */
    public static class Person {

        private String name;
        private int age;
        private List<Address> addresses;

        public Person() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<Address> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<Address> addresses) {
            this.addresses = addresses;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + ", addresses=" + addresses + "}";
        }
    }

    /**
     * Klasa GENERYCZNA - pole "items" ma tu typ List<T> (T to zmienna typu,
     * nie konkretna klasa). Uzywana wylacznie do demonstracji PRAWDZIWEGO
     * problemu type erasure (patrz AddressBox nizej).
     */
    public static class Container<T> {

        private List<T> items;

        public Container() {
        }

        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> items) {
            this.items = items;
        }
    }

    /**
     * Konkretyzuje Container<T> jako Container<Address> - ale pole "items"
     * fizycznie POZOSTAJE zadeklarowane w klasie bazowej jako List<T>, wiec
     * refleksja NIE ma jak odczytac "Address" z samego pola - to jest
     * prawdziwy przypadek type erasure, w ktorym TypeDescription jest
     * NIEZBEDNE (nie tylko wygodne).
     */
    public static class AddressBox extends Container<Address> {
    }

    /**
     * Wlasny PropertyUtils zwracajacy wlasciwosci w kolejnosci DEKLARACJI
     * pol (Class.getDeclaredFields()) zamiast domyslnego, zawsze
     * alfabetycznego posortowania (TreeSet) uzywanego przez standardowy
     * PropertyUtils. Uzywany w demonstrateFieldOrderDump().
     */
    public static class DeclaredOrderPropertyUtils extends PropertyUtils {

        @Override
        protected Set<Property> createPropertySet(Class<?> type, org.yaml.snakeyaml.introspector.BeanAccess bAccess) {
            Set<Property> properties = new LinkedHashSet<>();
            for (Field field : type.getDeclaredFields()) {
                if (!field.isSynthetic()) {
                    properties.add(new FieldProperty(field));
                }
            }
            return properties;
        }
    }
}
