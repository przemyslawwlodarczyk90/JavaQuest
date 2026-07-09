package com.example.javaquest._14_advancedjava.Lesson15_ReflectionBasics;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class _Lesson15_ReflectionBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: PODSTAWY REFLEKSJI (java.lang.reflect) ===");

        /*
         * ============================================================
         * 📦 CZYM JEST REFLEKSJA?
         * ============================================================
         * Do tej pory caly kod, ktory pisalismy, znal swoje typy W CZASIE
         * KOMPILACJI - jesli chcielismy wywolac metode "foo()" na obiekcie
         * klasy "Bar", pisalismy po prostu "bar.foo()", a kompilator
         * sprawdzal, czy taka metoda w ogole istnieje.
         *
         * REFLEKSJA (ang. reflection) to mechanizm, ktory pozwala programowi
         * "przegladac samego siebie" W CZASIE DZIALANIA (runtime) - odpytywac
         * o klasy, pola, metody i konstruktory, ktorych NAZWY moga byc znane
         * dopiero w trakcie dzialania programu (np. wczytane z pliku
         * konfiguracyjnego, adnotacji albo napisu podanego przez uzytkownika),
         * a nawet WYWOLYWAC je i MODYFIKOWAC, mimo ze w kodzie zrodlowym nie ma
         * ani jednego jawnego odwolania typu "obiekt.metoda()".
         *
         * To wlasnie refleksja stoi za mechanizmami, ktore poznales/as w
         * poprzednich rozdzialach, nie zdajac sobie z tego w pelni sprawy:
         * Guice budujacy obiekty na podstawie adnotacji "@Inject"
         * (_13_libraries/Lesson19_GuiceBasics), Hibernate tworzacy encje z
         * pustego konstruktora i wypelniajacy ich prywatne pola
         * (_12_hibernate), czy Jackson/Gson zamieniajace JSON na obiekty
         * (_04_io). W tej lekcji poznajemy FUNDAMENTY, na ktorych to wszystko
         * jest zbudowane. W Lesson16 polaczymy to z konkretnymi przypadkami
         * uzycia i z ryzykami, jakie refleksja ze soba niesie.
         */

        /*
         * ============================================================
         * 🔹 java.lang.Class<T> - PUNKT WEJSCIA DO REFLEKSJI
         * ============================================================
         * Kazdy typ zaladowany przez JVM (klasa, interfejs, tablica, typ
         * prymitywny) ma w czasie dzialania SWOJ WLASNY obiekt "Class<T>"
         * reprezentujacy jego metadane - to on jest "drzwiami" do calej
         * reszty API refleksji (Field, Method, Constructor to obiekty,
         * ktore UZYSKUJEMY z obiektu Class).
         *
         * Trzy sposoby zdobycia obiektu Class dla tego samego typu:
         */
        System.out.println("\n=== SPOSOB 1: LITERAL KLASY (SomeType.class) ===");
        Class<Employee> classByLiteral = Employee.class;
        System.out.println("Employee.class -> " + classByLiteral.getName());
        System.out.println("Znany juz W CZASIE KOMPILACJI - najbezpieczniejszy, gdy znamy typ z gory.");

        System.out.println("\n=== SPOSOB 2: instance.getClass() ===");
        Employee employee = new Employee("Anna Kowalska", 5500.0, "PESEL-DO-UKRYCIA");
        Class<?> classByInstance = employee.getClass();
        System.out.println("employee.getClass() -> " + classByInstance.getName());
        System.out.println("Dziala na DOWOLNYM obiekcie, nawet gdy znamy go tylko jako referencje 'Object' -");
        System.out.println("zwraca RZECZYWISTY, wykonawczy typ obiektu (polimorficznie).");

        System.out.println("\n=== SPOSOB 3: Class.forName(\"pelna.nazwa.Klasy\") ===");
        // UWAGA: dla klasy ZAGNIEZDZONEJ (jak "Employee" ponizej) "pelna nazwa" to
        // BINARNA nazwa Javy - klasa zewnetrzna oddzielona od zagniezdzonej znakiem
        // "$", NIE kropka (tu bierzemy ja z "classByLiteral.getName()", zeby uniknac
        // recznego, podatnego na literowki wpisywania jej na sztywno).
        String employeeBinaryName = classByLiteral.getName();
        System.out.println("Uzywana nazwa binarna (z '$' dla klasy zagniezdzonej): " + employeeBinaryName);
        Class<?> classByName = Class.forName(employeeBinaryName);
        System.out.println("Class.forName(String) -> " + classByName.getName());
        System.out.println("Nazwa klasy jako TEKST, znany dopiero w runtime (np. z pliku konfiguracyjnego) -");
        System.out.println("to wlasnie ten mechanizm laduje sterowniki JDBC albo pluginy po nazwie z configa.");

        System.out.println("\nCzy wszystkie trzy Class sa TYM SAMYM obiektem? "
                + (classByLiteral == classByInstance && classByInstance == classByName));
        System.out.println("(TAK - JVM trzyma DOKLADNIE JEDEN obiekt Class na kazdy zaladowany typ.)");

        /*
         * ============================================================
         * 🔍 getFields() vs getDeclaredFields() - KLUCZOWA ROZNICA
         * ============================================================
         * - getFields()          -> tylko pola PUBLICZNE, ZA TO wliczajac
         *                           te ODZIEDZICZONE po klasach nadrzednych.
         * - getDeclaredFields()  -> WSZYSTKIE pola zadeklarowane BEZPOSREDNIO
         *                           w tej klasie (prywatne, chronione,
         *                           pakietowe, publiczne), ale BEZ pol
         *                           odziedziczonych po rodzicu.
         *
         * Dokladnie ta sama zasada dotyczy getMethods()/getDeclaredMethods()
         * oraz getConstructors()/getDeclaredConstructors().
         */
        System.out.println("\n=== getFields() vs getDeclaredFields() NA KLASIE Employee ===");
        System.out.println("Employee ma pola: name (private), salary (private), pesel (private), "
                + "oraz PUBLIC_COMPANY_NAME (public static).");

        Field[] publicFields = Employee.class.getFields();
        System.out.println("\ngetFields() (tylko PUBLICZNE, wliczajac odziedziczone) -> "
                + publicFields.length + " pole(a): "
                + Arrays.toString(Arrays.stream(publicFields).map(Field::getName).toArray()));

        Field[] declaredFields = Employee.class.getDeclaredFields();
        System.out.println("getDeclaredFields() (WSZYSTKIE zadeklarowane w tej klasie, bez wzgledu na "
                + "widocznosc) -> " + declaredFields.length + " pola: "
                + Arrays.toString(Arrays.stream(declaredFields).map(Field::getName).toArray()));

        /*
         * ============================================================
         * 🔹 TO SAMO DLA METOD I KONSTRUKTOROW
         * ============================================================
         */
        System.out.println("\n=== getMethods() vs getDeclaredMethods() ===");
        System.out.println("getMethods() zwraca m.in. metody odziedziczone po Object (np. toString, equals) -"
                + " liczba: " + Employee.class.getMethods().length);
        System.out.println("getDeclaredMethods() zwraca TYLKO metody zadeklarowane wprost w Employee "
                + "(wliczajac prywatna raiseSalary...) - liczba: "
                + Employee.class.getDeclaredMethods().length);
        for (Method m : Employee.class.getDeclaredMethods()) {
            System.out.println("  - " + Modifier.toString(m.getModifiers()) + " " + m.getName() + "(...)");
        }

        System.out.println("\n=== getConstructors() vs getDeclaredConstructors() ===");
        System.out.println("Employee ma DWA konstruktory: publiczny (name, salary, pesel) "
                + "i PRYWATNY bezargumentowy (symulujacy to, czego np. Hibernate/Jackson wymagaja od encji/DTO).");
        System.out.println("getConstructors() (tylko publiczne) -> "
                + Employee.class.getConstructors().length);
        System.out.println("getDeclaredConstructors() (wszystkie) -> "
                + Employee.class.getDeclaredConstructors().length);

        /*
         * ============================================================
         * 🔹 WYWOLYWANIE METODY REFLEKSYJNIE: Method.invoke(...)
         * ============================================================
         * "getMethod(nazwa, typyParametrow...)" znajduje PUBLICZNA metode
         * (wliczajac odziedziczone) o podanej nazwie i sygnaturze.
         * "invoke(obiektNaKtorymWywolujemy, argumenty...)" faktycznie ja
         * URUCHAMIA - dokladnie tak, jakby ktos napisal
         * "employee.raiseSalaryPublic(500.0)" w kodzie zrodlowym, tylko ze
         * nazwa metody jest tu ZWYKLYM NAPISEM znanym dopiero w runtime.
         */
        System.out.println("\n=== Method.invoke() - WYWOLANIE METODY PRZEZ REFLEKSJE ===");
        Method getSalaryMethod = Employee.class.getMethod("getSalary");
        Object currentSalary = getSalaryMethod.invoke(employee);
        System.out.println("employee.getClass().getMethod(\"getSalary\").invoke(employee) -> " + currentSalary);

        Method raiseSalaryMethod = Employee.class.getMethod("raiseSalaryPublic", double.class);
        raiseSalaryMethod.invoke(employee, 300.0);
        System.out.println("Po wywolaniu raiseSalaryPublic(300.0) reflekcyjnie, nowa pensja: "
                + employee.getSalary());

        /*
         * ============================================================
         * 🔹 TWORZENIE OBIEKTU REFLEKSYJNIE: Constructor.newInstance(...)
         * ============================================================
         * To DOKLADNIE ten mechanizm, ktorego uzywaja frameworki takie jak
         * Hibernate czy Jackson, by stworzyc obiekt encji/DTO, ZANIM w ogole
         * znaja jego dane - najpierw wolaja konstruktor bezargumentowy
         * (czesto prywatny!), a POTEM wypelniaja pola refleksyjnie (patrz
         * przyklad z Field.set(...) ponizej).
         */
        System.out.println("\n=== Constructor.newInstance() - TWORZENIE OBIEKTU PRZEZ REFLEKSJE ===");
        Constructor<Employee> publicConstructor = Employee.class.getConstructor(String.class, double.class, String.class);
        Employee reflectivelyCreated = publicConstructor.newInstance("Jan Nowak", 4200.0, "PESEL-JN");
        System.out.println("Nowy obiekt utworzony przez Constructor.newInstance(...): " + reflectivelyCreated);

        /*
         * ============================================================
         * 🔍 setAccessible(true) - DOSTEP DO PRYWATNYCH SKLADOWYCH
         * ============================================================
         * Domyslnie refleksja RESPEKTUJE modyfikatory dostepu - proba
         * wywolania "invoke"/"newInstance"/"get"/"set" na czyms prywatnym
         * konczy sie wyjatkiem "IllegalAccessException". Metoda
         * "setAccessible(true)" (odziedziczona po AccessibleObject, wspolna
         * dla Field/Method/Constructor) WYLACZA to sprawdzanie.
         *
         * ⚠️ OSTRZEZENIE: to jest swiadome ZLAMANIE hermetyzacji (encapsulation) -
         * "prywatne" przestaje cokolwiek znaczyc dla kogos, kto ma dostep do
         * obiektu Field/Method/Constructor. Uzywane bez rozwagi prowadzi do
         * kruchego kodu, ktory zalezy od WEWNETRZNEJ (i mogacej sie zmienic
         * bez ostrzezenia) implementacji cudzej klasy. Frameworki robia to
         * SWIADOMIE i w kontrolowany sposob (np. Hibernate wypelnia pola
         * encji, bo taka jest jego JEDYNA praca) - w kodzie aplikacyjnym
         * pisanym przez Ciebie powinno to byc RZADKOSCIA. Pelne omowienie
         * ryzyk (i modularnych ograniczen JPMS na "setAccessible") znajdziesz
         * w Lesson16 oraz Lesson28.
         */
        System.out.println("\n=== setAccessible(true) I DOSTEP DO PRYWATNEGO POLA (Field.get/set) ===");
        Field peselField = Employee.class.getDeclaredField("pesel");
        try {
            System.out.println("Probuje odczytac PRYWATNE pole 'pesel' BEZ setAccessible(true)...");
            peselField.get(employee);
        } catch (IllegalAccessException e) {
            System.out.println("Zgodnie z oczekiwaniami: " + e.getClass().getSimpleName()
                    + " - refleksja domyslnie respektuje 'private'.");
        }

        peselField.setAccessible(true); // ⚠️ swiadome zlamanie hermetyzacji - patrz ostrzezenie wyzej
        Object peselValue = peselField.get(employee);
        System.out.println("Po setAccessible(true): odczytana wartosc 'pesel' = " + peselValue);

        peselField.set(employee, "PESEL-NADPISANY-PRZEZ-REFLEKSJE");
        System.out.println("Field.set(employee, nowaWartosc) nadpisal prywatne pole OMIJAJAC kazdy "
                + "ewentualny setter/walidacje: " + employee);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Refleksja (java.lang.reflect) pozwala odpytywac i manipulowac
         *   klasami, polami, metodami i konstruktorami W CZASIE DZIALANIA,
         *   czesto na podstawie samych NAZW (tekstow) znanych dopiero
         *   w runtime.
         * - java.lang.Class<T> to punkt wejscia - zdobywamy go przez
         *   "Typ.class" (kompilacyjnie), "obiekt.getClass()" (polimorficznie,
         *   z dowolnej referencji) albo "Class.forName(\"pelna.nazwa\")"
         *   (z tekstu, np. z pliku konfiguracyjnego).
         * - "getX()" (getFields/getMethods/getConstructors) zwraca tylko
         *   skladowe PUBLICZNE, ale WLICZAJAC odziedziczone; "getDeclaredX()"
         *   zwraca WSZYSTKIE skladowe zadeklarowane wprost w danej klasie,
         *   niezaleznie od widocznosci, ale BEZ odziedziczonych.
         * - "Method.invoke(obiekt, argumenty...)" wywoluje metode,
         *   "Constructor.newInstance(argumenty...)" tworzy obiekt,
         *   "Field.get(obiekt)"/"Field.set(obiekt, wartosc)" czyta/zapisuje
         *   pole - wszystko reflekcyjnie, bez jawnego odwolania w kodzie
         *   zrodlowym.
         * - "setAccessible(true)" omija sprawdzanie widocznosci (private/
         *   protected) - to swiadome zlamanie hermetyzacji, ktore powinno
         *   byc wyjatkiem, a nie regula, w kodzie aplikacyjnym. Wiecej
         *   o ryzykach i o tym, KIEDY refleksja jest uzasadniona - w Lesson16.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    /**
     * Prosta klasa demonstracyjna: mix pol publicznych/prywatnych oraz
     * konstruktorow publicznego/prywatnego - zeby pokazac roznice
     * getFields()/getDeclaredFields() i getConstructors()/getDeclaredConstructors().
     */
    static class Employee {

        public static final String PUBLIC_COMPANY_NAME = "JavaQuest Sp. z o.o.";

        private final String name;
        private double salary;
        private String pesel; // dane wrazliwe - celowo prywatne, bez publicznego gettera/settera

        public Employee(String name, double salary, String pesel) {
            this.name = name;
            this.salary = salary;
            this.pesel = pesel;
        }

        // Prywatny konstruktor bezargumentowy - symuluje to, czego frameworki
        // (Hibernate, Jackson) oczekuja od encji/DTO, by moc je zbudowac
        // reflekcyjnie, ZANIM znaja jakiekolwiek dane.
        private Employee() {
            this.name = "(nieznany)";
        }

        public double getSalary() {
            return salary;
        }

        public void raiseSalaryPublic(double amount) {
            this.salary += amount;
        }

        private void raiseSalaryPrivate(double amount) {
            this.salary += amount;
        }

        @Override
        public String toString() {
            return "Employee{name='" + name + "', salary=" + salary + ", pesel='" + pesel + "'}";
        }
    }
}
