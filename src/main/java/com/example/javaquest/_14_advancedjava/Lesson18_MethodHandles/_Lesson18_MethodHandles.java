package com.example.javaquest._14_advancedjava.Lesson18_MethodHandles;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class _Lesson18_MethodHandles {

    public static void main(String[] args) throws Throwable {

        System.out.println("=== LEKCJA 18: METHODHANDLES (java.lang.invoke) ===");

        /*
         * ============================================================
         * 📦 PO CO KOLEJNY MECHANIZM, SKORO MAMY JUZ REFLEKSJE (LESSON15)?
         * ============================================================
         * "java.lang.invoke.MethodHandles" to API wprowadzone w Javie 7,
         * zaprojektowane jako NOWOCZESNIEJSZA, WYDAJNIEJSZA i - o dziwo -
         * BARDZIEJ TYPOWO BEZPIECZNA alternatywa dla klasycznej refleksji
         * z "java.lang.reflect" (Lesson15). Rozwiazuje dokladnie Ryzyko 1 z
         * Lesson16 (koszt wydajnosciowy refleksji): JVM potrafi "MethodHandle"
         * zoptymalizowac (zinline'owac, zoptymalizowac JIT-em) niemal tak
         * dobrze, jak zwykle, bezposrednie wywolanie metody - w odroznieniu
         * od "Method.invoke(...)", ktore zawsze ponosi narzut pakowania
         * argumentow do "Object[]" i sprawdzania dostepu.
         *
         * Metafora: jesli refleksja (Class/Field/Method/Constructor) to
         * "ogolny opis" czlonka klasy razem z metadanymi (nazwa, modyfikatory,
         * adnotacje...), to "MethodHandle" to raczej "bezposredni wskaznik
         * wykonywalny" - chudy, szybki obiekt, ktorego JEDYNYM zadaniem jest
         * DAC SIE WYWOLAC z okreslonym typem argumentow i wyniku.
         */

        /*
         * ============================================================
         * 🔹 MethodHandles.lookup() - PUNKT WEJSCIA (ODPOWIEDNIK Class<T>)
         * ============================================================
         * "MethodHandles.lookup()" zwraca obiekt "Lookup" zwiazany z KLASA,
         * w ktorej zostal wywolany - to on niesie ze soba UPRAWNIENIA
         * dostepu (dokladnie tak, jakby kod w tym miejscu mial normalny,
         * kompilacyjny dostep do prywatnych/pakietowych skladowych TEJ klasy).
         * To fundamentalna roznica wobec Lesson15: tam KAZDY mogl zdobyc
         * "Class<T>" i probowac "setAccessible(true)"; tutaj "Lookup" jest
         * "podpisany" prawami DOKLADNIE tego miejsca w kodzie, w ktorym go
         * utworzono - bezpieczniejszy model dostepu.
         */
        System.out.println("\n=== MethodHandles.lookup() ===");
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        System.out.println("Lookup zwiazany z klasa: " + lookup.lookupClass().getSimpleName());

        /*
         * ============================================================
         * 🔹 MethodType - OPIS SYGNATURY (TYP ZWRACANY + TYPY PARAMETROW)
         * ============================================================
         * "MethodType.methodType(typZwracany, typyParametrow...)" opisuje
         * DOKLADNA sygnature metody - to odpowiednik tego, co w Lesson15
         * przekazywalismy jako "Class<?>... parameterTypes" do "getMethod(...)",
         * ale opakowane w wygodny, "pierwszoklasowy" obiekt.
         */
        System.out.println("\n=== MethodType.methodType(...) ===");
        MethodType raiseSalaryType = MethodType.methodType(void.class, double.class);
        System.out.println("MethodType dla 'void raiseSalary(double)': " + raiseSalaryType);

        /*
         * ============================================================
         * 🔹 findVirtual - UCHWYT DO METODY INSTANCYJNEJ
         * ============================================================
         * "lookup.findVirtual(klasa, nazwaMetody, methodType)" zwraca
         * "MethodHandle" do metody INSTANCYJNEJ. Wywolanie takiego uchwytu
         * wymaga podania obiektu, na ktorym ma byc wykonana, jako
         * PIERWSZEGO argumentu "invoke(...)".
         */
        System.out.println("\n=== findVirtual - METODA INSTANCYJNA ===");
        MethodHandle raiseSalaryHandle = lookup.findVirtual(Employee.class, "raiseSalary", raiseSalaryType);
        Employee employee = new Employee("Anna Kowalska", 5000.0);

        /*
         * ============================================================
         * 🔍 invoke(...) vs invokeExact(...) - KLUCZOWA ROZNICA
         * ============================================================
         * - "invokeExact(...)" wymaga, by typy argumentow (i typ zwracany,
         *   wymuszony przez rzutowanie w miejscu wywolania) ZGADZALY SIE
         *   DOKLADNIE z "MethodType" uchwytu - ani jednej automatycznej
         *   konwersji (nawet "int" -> "long" czy "Integer" -> "int" NIE
         *   przejdzie bez jawnego rzutowania w kodzie). To pozwala JVM
         *   wygenerowac NAJSZYBSZY mozliwy kod wywolania.
         * - "invoke(...)" jest WYGODNIEJSZY - dopuszcza automatyczne
         *   konwersje typow (podobne do zwyklego wywolania metody w Javie:
         *   boxing/unboxing, rozszerzanie typow prymitywnych, rzutowanie
         *   w gore hierarchii klas), kosztem odrobiny wydajnosci
         *   (dodatkowa warstwa dopasowania typow).
         * W praktyce: piszac REKZNIE kod korzystajacy z MethodHandle,
         * niemal zawsze uzywamy "invoke(...)" - "invokeExact(...)" jest
         * uzywany tam, gdzie kod SAM generuje precyzyjnie dobrane wywolania
         * (np. wewnatrz samego JDK, w zaimplementowanych mechanizmach jezyka).
         */
        System.out.println("\n=== invoke(...) - Z AUTOMATYCZNYMI KONWERSJAMI ===");
        raiseSalaryHandle.invoke(employee, 500.0);
        System.out.println("Po invoke(employee, 500.0): pensja = " + employee.getSalary());

        System.out.println("\n=== invokeExact(...) - WYMAGA DOKLADNEGO DOPASOWANIA TYPOW ===");
        raiseSalaryHandle.invokeExact(employee, 250.0); // typy MUSZA zgadzac sie 1:1 z MethodType
        System.out.println("Po invokeExact(employee, 250.0): pensja = " + employee.getSalary());
        System.out.println("(gdybysmy tu podali np. 'int' zamiast 'double', invokeExact NIE skompilowalby sie/");
        System.out.println(" rzucilby WrongMethodTypeException w runtime - invoke(...) po prostu by go rozszerzyl)");

        /*
         * ============================================================
         * 🔹 findStatic - UCHWYT DO METODY STATYCZNEJ
         * ============================================================
         */
        System.out.println("\n=== findStatic - METODA STATYCZNA ===");
        MethodType parseBonusType = MethodType.methodType(double.class, String.class);
        MethodHandle parseBonusHandle = lookup.findStatic(Employee.class, "parseBonus", parseBonusType);
        double parsedBonus = (double) parseBonusHandle.invoke("1250.50");
        System.out.println("Employee.parseBonus(\"1250.50\") przez MethodHandle -> " + parsedBonus);

        /*
         * ============================================================
         * 🔹 findConstructor - UCHWYT DO KONSTRUKTORA
         * ============================================================
         * Sygnatura "MethodType" dla konstruktora ma typ zwracany "void.class" -
         * co moze byc mylace, ale wynika z tego, ze konstruktor "nie zwraca
         * wartosci" w sensie bajtkodu; sam "MethodHandle" mimo to, po
         * wywolaniu, ZWROCI nowo utworzony obiekt danego typu.
         */
        System.out.println("\n=== findConstructor - KONSTRUKTOR ===");
        MethodType constructorType = MethodType.methodType(void.class, String.class, double.class);
        MethodHandle constructorHandle = lookup.findConstructor(Employee.class, constructorType);
        Employee newEmployee = (Employee) constructorHandle.invoke("Jan Nowak", 4200.0);
        System.out.println("Nowy obiekt przez MethodHandle konstruktora: " + newEmployee);

        /*
         * ============================================================
         * 🔹 findGetter / findSetter - UCHWYTY DO POL
         * ============================================================
         * Odpowiednik "Field.get(...)"/"Field.set(...)" z Lesson15, ale
         * jako oddzielne, wyspecjalizowane uchwyty - jeden do odczytu, drugi
         * do zapisu.
         */
        System.out.println("\n=== findGetter / findSetter - POLA ===");
        MethodHandle nameGetter = lookup.findGetter(Employee.class, "name", String.class);
        MethodHandle salarySetter = lookup.findSetter(Employee.class, "salary", double.class);

        String employeeName = (String) nameGetter.invoke(newEmployee);
        System.out.println("Odczyt pola 'name' przez findGetter: " + employeeName);

        salarySetter.invoke(newEmployee, 9999.0);
        System.out.println("Zapis pola 'salary' przez findSetter: " + newEmployee.getSalary());

        /*
         * ⚠️ UWAGA: "findGetter"/"findSetter" (podobnie jak "findVirtual"/
         * "findStatic"/"findConstructor") respektuja modyfikatory dostepu
         * WZGLEDEM "Lookup" - tutaj dziala, bo pole "salary" i konstruktor sa
         * dostepne z tej samej klasy zewnetrznej (Lesson18 jest w tym samym
         * pliku, co "Employee"). Dla PRYWATNYCH skladowych spoza tego zasiegu
         * potrzebny bylby "lookup.unreflectSetter(field)" na uprzednio
         * "setAccessible(true)"-owanym Field - MethodHandles NIE eliminuja
         * potrzeby lamania hermetyzacji, gdy jest ona rzeczywiscie potrzebna,
         * tylko oferuja SZYBSZY sposob wywolywania juz odnalezionych skladowych.
         */

        /*
         * ============================================================
         * 🔍 PORONNANIE Z REFLEKSJA (LESSON15): BEZPIECZENSTWO TYPOW
         * ============================================================
         * W Lesson15 "Method.invoke(obiekt, argumenty...)" przyjmuje
         * "Object... args" - BLEDNY typ argumentu (np. String zamiast double)
         * wykryjemy DOPIERO w runtime, jako "IllegalArgumentException".
         * "MethodHandle.invokeExact(...)" jest sprawdzany scislej: typ
         * KAZDEGO argumentu w MIEJSCU WYWOLANIA (call site) musi zgadzac sie
         * z zadeklarowanym "MethodType" - kompilator/JVM (przez mechanizm
         * "polymorphic signature") wymusza wiecej niz przy zwyklym
         * "Object[]" z refleksji. To wlasnie ta scislejsza kontrola (plus
         * mozliwosc optymalizacji przez JIT) czyni MethodHandles
         * "type-safe i szybszym" odpowiednikiem refleksji.
         */
        System.out.println("\n=== PORONNANIE: BLEDNY TYP ARGUMENTU ===");
        try {
            // "findVirtual" ustalone dla (Employee, double) -> void; podajemy String zamiast double.
            raiseSalaryHandle.invoke(employee, "nie-liczba");
        } catch (RuntimeException e) {
            System.out.println("Blad przy niezgodnym typie argumentu, przechwycony w runtime: "
                    + e.getClass().getSimpleName());
        }

        /*
         * ============================================================
         * 🔹 SKAD ZNAMY TEN MECHANIZM? LAMBDY I invokedynamic
         * ============================================================
         * W _14_advancedjava/Lesson09_LambdaExpressions poznales/as wyrazenia
         * lambda ("x -> x * 2"). To, co dzieje sie "pod maska" przy KAZDEJ
         * lambdzie, jest scisle powiazane z "java.lang.invoke": kompilator
         * Javy NIE generuje dla lambdy osobnej, nazwanej klasy anonimowej
         * (jak robil to kiedys dla klas anonimowych) - zamiast tego emituje
         * bajtkodowa instrukcje "invokedynamic", ktora przy PIERWSZYM
         * wywolaniu danej lambdy odwoluje sie do "LambdaMetafactory"
         * (rowniez czesc "java.lang.invoke"). "LambdaMetafactory" DYNAMICZNIE
         * generuje (i cachuje) klase implementujaca odpowiedni interfejs
         * funkcyjny, uzywajac WLASNIE "MethodHandle" wskazujacego cialo
         * lambdy. Dzieki temu lambdy sa jednoczesnie: (a) bardzo szybkie po
         * "rozgrzaniu" (JIT dobrze optymalizuje MethodHandle), (b) leniwie
         * tworzone TYLKO gdy naprawde sa uzyte, w odroznieniu od "gorliwego"
         * generowania klas anonimowych przy kompilacji.
         */
        System.out.println("\n=== POWIAZANIE Z LAMBDAMI (Lesson09) ===");
        System.out.println("Kazda lambda w Javie kompiluje sie do instrukcji 'invokedynamic', ktora");
        System.out.println("laczy sie z LambdaMetafactory - a ten, WEWNATRZ, uzywa MethodHandle,");
        System.out.println("dokladnie tych samych klas, ktorych sami dopiero co uzywalismy w tej lekcji.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE: REFLEKSJA (LESSON15) vs METHODHANDLES (LESSON18)
         * ============================================================
         * | Cecha                     | Reflection (Lesson15)      | MethodHandles (Lesson18)      |
         * |----------------------------|----------------------------|---------------------------------|
         * | Punkt wejscia              | Class<T>                   | MethodHandles.lookup()          |
         * | Opis sygnatury             | Class<?>... parameterTypes | MethodType                      |
         * | Wywolanie                  | Method.invoke(Object...)   | invoke(...) / invokeExact(...)  |
         * | Sprawdzanie typow          | W duzej mierze w runtime   | Scislejsze (polymorphic sig.)   |
         * | Wydajnosc                  | Wolniejsza (Object[] + AccessCheck) | Blizsza wywolaniu bezposredniemu (JIT-friendly) |
         * | Metadane (adnotacje itd.)  | Bogate (Field/Method/...)  | Minimalne - tylko wywolywalnosc |
         *
         * - "MethodHandles.lookup()" to punkt wejscia niosacy uprawnienia
         *   dostepu MIEJSCA, w ktorym zostal utworzony.
         * - "MethodType.methodType(zwracany, parametry...)" opisuje sygnature.
         * - "findVirtual"/"findStatic"/"findConstructor"/"findGetter"/"findSetter"
         *   zwracaja gotowe, "chude" uchwyty wykonywalne ("MethodHandle").
         * - "invoke(...)" dopuszcza konwersje typow (wygodniejszy),
         *   "invokeExact(...)" wymaga scislej zgodnosci typow (szybszy,
         *   bardziej rygorystyczny).
         * - MethodHandles sa SZYBSZE i bardziej "type-safe" niz klasyczna
         *   refleksja, ale MAJA MNIEJ metadanych (nie sluza do przegladania
         *   adnotacji czy modyfikatorow - do tego nadal uzywa sie Class/Field/
         *   Method z Lesson15) - w praktyce oba API czesto wspolpracuja
         *   (np. "lookup.unreflect(method)" zamienia "Method" znaleziony
         *   refleksyjnie w "MethodHandle").
         * - To wlasnie MethodHandles (przez invokedynamic/LambdaMetafactory)
         *   stoi za wydajna implementacja wyrazen lambda z Lesson09.
         *
         * ============================================================
         * 📌 KONIEC KLASTRA "REFLECTION & DYNAMIC MECHANISMS" (15-18)
         * ============================================================
         * W tych czterech lekcjach przeszlismy od PODSTAW refleksji (Class,
         * Field, Method, Constructor - Lesson15), przez jej REALNE
         * zastosowania w bibliotekach, ktore juz znasz, i uczciwe omowienie
         * ryzyk (Lesson16), przez DYNAMICZNE GENEROWANIE calych implementacji
         * interfejsow w runtime (Proxy/InvocationHandler - Lesson17), az po
         * nowoczesniejsza, szybsza alternatywe dla samej refleksji
         * (MethodHandles - Lesson18). Wszystkie te mechanizmy laczy jedna
         * idea: kod, ktory operuje na INNYM kodzie jako na danych, podejmujac
         * decyzje W CZASIE DZIALANIA programu, a nie tylko w czasie kompilacji.
         *
         * W kolejnej lekcji (Lesson19_SealedClasses) zmieniamy calkowicie
         * kierunek - zamiast "otwierac" system typow w runtime, poznasz
         * "sealed classes": mechanizm pozwalajacy CELOWO OGRANICZYC (uszczelnic)
         * hierarchie klas juz W CZASIE KOMPILACJI, precyzyjnie kontrolujac,
         * KTO wolno dziedziczyc po danym typie.
         */
        System.out.println("\n=== KONIEC LEKCJI 18 (I KLASTRA REFLECTION & DYNAMIC MECHANISMS, LEKCJE 15-18) ===");
        System.out.println("Nastepna lekcja: Lesson19_SealedClasses.");
    }

    /**
     * Prosta klasa demonstracyjna - metoda instancyjna, statyczna, pole i
     * konstruktor uzywane przez wszystkie rodzaje MethodHandle w tej lekcji.
     */
    static class Employee {
        private final String name;
        private double salary;

        Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        void raiseSalary(double amount) {
            this.salary += amount;
        }

        double getSalary() {
            return salary;
        }

        static double parseBonus(String raw) {
            return Double.parseDouble(raw);
        }

        @Override
        public String toString() {
            return "Employee{name='" + name + "', salary=" + salary + "}";
        }
    }
}
