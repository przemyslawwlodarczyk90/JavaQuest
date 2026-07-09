package com.example.javaquest._14_advancedjava.Lesson16_ReflectionUseCasesAndRisks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class _Lesson16_ReflectionUseCasesAndRisks {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: REFLEKSJA W PRAKTYCE - ZASTOSOWANIA I RYZYKA ===");

        /*
         * ============================================================
         * 📦 PO CO TO WSZYSTKO? PRZYPOMNIENIE MECHANIKI Z LESSON15
         * ============================================================
         * W Lesson15 poznalismy "SUROWE" narzedzia: Class, Field, Method,
         * Constructor, setAccessible(true). Same w sobie sa dosc niskopoziomowe
         * i mało kto uzywa ich WPROST w kodzie aplikacyjnym na co dzien.
         * Ich prawdziwa moc widac dopiero w BIBLIOTEKACH i FRAMEWORKACH,
         * ktore juz poznales/as w tym kursie - one buduja na tych samych
         * czterech klasach cala swoja "magie". W tej lekcji lączymy kropki:
         * pokazujemy DOKLADNIE, w ktorym miejscu kazdy z tych frameworkow
         * uzywa Class/Field/Method/Constructor - a potem mowimy uczciwie
         * o CENIE, jaka za to placimy.
         */

        /*
         * ============================================================
         * 🔹 ZASTOSOWANIE 1: DEPENDENCY INJECTION (Guice, Spring)
         * ============================================================
         * W _13_libraries/Lesson19_GuiceBasics widzieliscie adnotacje
         * "@Inject" na konstruktorze i na PRYWATNYM polu ("NotificationService.notifier").
         * Guice (i identycznie Spring z "@Autowired") robi to w 3 krokach,
         * ktore juz znasz z Lesson15:
         *   1. "targetClass.getDeclaredConstructors()" / "getDeclaredFields()" -
         *      szuka skladowych oznaczonych "@Inject".
         *   2. Dla kazdej zaleznosci sprawdza jej TYP (Field.getType() /
         *      Parameter.getType()) i odpytuje swoj rejestr wiazan
         *      ("bind(Interfejs.class).to(Impl.class)") o gotowa instancje.
         *   3. "constructor.newInstance(zaleznosci...)" ALBO
         *      "field.setAccessible(true); field.set(obiekt, zaleznosc)" -
         *      dokladnie te same wywolania, ktore sami robilismy w Lesson15!
         * Ponizej odtwarzamy to w miniaturze - WLASNY, prosty "kontener DI"
         * uzywajacy tylko surowej refleksji (bez Guice).
         */
        System.out.println("\n=== PRZYKLAD 1: MINI-KONTENER DI (jak Guice/Spring, ale recznie) ===");
        NotificationService service = new NotificationService();
        injectDependencies(service);
        service.notifyUser("Zamowienie przyjete.");

        /*
         * ============================================================
         * 🔹 ZASTOSOWANIE 2: HIBERNATE/JPA - BUDOWANIE ENCJI
         * ============================================================
         * W _12_hibernate poznaliscie regule: kazda encja JPA MUSI miec
         * bezargumentowy konstruktor (moze byc protected/private - patrz
         * Lesson04_FirstEntityAndBasicMapping). To WYMAGANIE istnieje
         * DOKLADNIE dlatego, ze Hibernate tworzy obiekty encji tak samo
         * jak w naszym Zadaniu 14 z Lesson15:
         *   1. "entityClass.getDeclaredConstructor()" - znajduje konstruktor
         *      bezargumentowy, NIEZALEZNIE od jego widocznosci.
         *   2. "constructor.setAccessible(true); constructor.newInstance()" -
         *      tworzy "pusty" obiekt encji, ZANIM zna jakiekolwiek dane z bazy.
         *   3. Dla kazdej kolumny z wyniku zapytania SQL: "field.setAccessible(true);
         *      field.set(entity, wartoscZBazy)" - wypelnia PRYWATNE pola encji
         *      (np. "id", "title") OMIJAJAC settery, bo (celowo) czesto ich
         *      w ogole nie ma albo zawieraja logike biznesowa, ktorej Hibernate
         *      nie chce uruchamiac przy ladowaniu z bazy.
         * Ten sam mechanizm stoi za "leniwymi proxy" z
         * _12_hibernate/Lesson15_FetchTypesAndNPlusOne - o proxy dokladniej
         * w NASZEJ kolejnej lekcji, Lesson17_DynamicProxies.
         */
        System.out.println("\n=== PRZYKLAD 2: SYMULACJA \"HIBERNATE\" BUDUJACEGO ENCJE REFLEKSYJNIE ===");
        BookEntity book = buildEntityLikeHibernate(BookEntity.class,
                Map.of("id", 42L, "title", "Effective Java"));
        System.out.println("Encja zbudowana bez ANI JEDNEGO wywolania 'new BookEntity(...)' z naszej strony: "
                + book);

        /*
         * ============================================================
         * 🔹 ZASTOSOWANIE 3: JACKSON/GSON - MAPOWANIE JSON <-> OBIEKT
         * ============================================================
         * W _04_io/Lesson20_Gson i Lesson21_Jackson wywolywaliscie
         * "gson.fromJson(json, Person.class)" / "mapper.readValue(json, Person.class)"
         * jako "czarna skrzynke". W srodku dzieje sie to samo, co widzieliscie
         * juz dwa razy powyzej: biblioteka bierze "Person.class", odczytuje
         * jego "getDeclaredFields()", dla kazdego pola szuka klucza o takiej
         * samej (lub zmapowanej przez "@JsonProperty"/"@SerializedName") nazwie
         * w drzewie JSON, i ustawia je przez "Field.set(...)" (Jackson potrafi
         * tez uzyc setterow albo konstruktora - w zaleznosci od konfiguracji).
         * W DRUGA strone (serializacja obiektu DO JSON-a) dzieje sie
         * odwrotnosc: "getDeclaredFields()" + "Field.get(obiekt)" dla kazdego
         * pola, by odczytac jego wartosc i zapisac jako klucz JSON-a.
         */
        System.out.println("\n=== PRZYKLAD 3: SYMULACJA \"JACKSONA\" SERIALIZUJACEGO OBIEKT DO JSON ===");
        BookEntity anotherBook = new BookEntity();
        setPrivateField(anotherBook, "id", 7L);
        setPrivateField(anotherBook, "title", "Clean Code");
        System.out.println(toJsonLikeJackson(anotherBook));

        /*
         * ============================================================
         * 🔹 ZASTOSOWANIE 4: JUNIT - ZNAJDOWANIE METOD TESTOWYCH @Test
         * ============================================================
         * Kazdy uruchomiony test JUnit w tym projekcie zaczyna sie od tego
         * samego kroku: JUnit bierze "Class<?>" klasy testowej, wola
         * "getDeclaredMethods()", i dla kazdej metody sprawdza
         * "method.isAnnotationPresent(Test.class)" (dokladnie tak, jak
         * czytaliscie WLASNE adnotacje w Lesson13/Lesson14). Dla kazdej
         * znalezionej metody testowej JUnit tworzy NOWA instancje klasy
         * testowej (bezargumentowy konstruktor - jak w Przykladzie 2) i
         * woła "method.invoke(instancja)" - i to WLASNIE dlatego metody
         * testowe moga byc "package-private" albo nawet prywatne w
         * niektorych wersjach JUnit, a caly test uruchamia sie bez
         * jednego jawnego wywolania z naszej strony.
         */
        System.out.println("\n=== PRZYKLAD 4: SYMULACJA \"JUNITA\" SZUKAJACEGO METOD @Test ===");
        runAnnotatedTests(SampleTestClass.class);

        /*
         * ============================================================
         * ⚠️ RYZYKA I KOSZTY REFLEKSJI
         * ============================================================
         */

        /*
         * 🔍 1. WYDAJNOSC
         * Wywolanie reflekcyjne (Method.invoke, Field.get/set,
         * Constructor.newInstance) jest WOLNIEJSZE niz wywolanie bezposrednie -
         * JVM nie moze go tak agresywnie zoptymalizowac/zinline'owac jak
         * zwyklego wywolania, dochodzi tez narzut sprawdzania dostepu (o ile
         * nie ustawiono setAccessible) i pakowania/rozpakowywania argumentow
         * do "Object[]" (w tym autoboxing typow prymitywnych). W kodzie
         * wykonywanym RAZ (np. przy starcie aplikacji - budowa grafu DI) ten
         * koszt jest nieistotny. W kodzie wykonywanym MILIONY razy w petli
         * (hot path) moze byc realnym problemem - stad w Lesson18 poznasz
         * "MethodHandles" jako szybsza alternatywe.
         */
        System.out.println("\n=== RYZYKO 1: WYDAJNOSC ===");
        System.out.println("Method.invoke(...) ma zauwazalny narzut vs wywolanie bezposrednie - "
                + "nieistotny raz na start aplikacji, wazny w petli wykonywanej miliony razy.");

        /*
         * 🔍 2. LAMIE HERMETYZACJE I BEZPIECZENSTWO TYPOW
         * "setAccessible(true)" (Lesson15) pozwala odczytac/zmienic KAZDE
         * prywatne pole, ominac walidacje w konstruktorach/setterach, a nawet
         * (w starszych wersjach Javy) zmienic wartosc pola "final". To
         * oznacza, ze "prywatne" i "final" przestaja byc GWARANCJA - sa
         * jedynie SUGESTIA dla kogos, kto NIE siega po refleksje.
         */
        System.out.println("\n=== RYZYKO 2: LAMIE HERMETYZACJE ===");
        System.out.println("setAccessible(true) omija 'private' i walidacje w konstruktorach/setterach - "
                + "kod staje sie podatny na niespojne/niepoprawne stany obiektu.");

        /*
         * 🔍 3. KRUCHOSC WOBEC REFAKTORYZACJI
         * Kod odwolujacy sie do pol/metod PO NAZWIE (String) NIE jest
         * sprawdzany przez kompilator. Zmiana nazwy pola "pesel" na
         * "nationalId" (proste "rename" w IDE) NIE zaktualizuje automatycznie
         * napisu "getDeclaredField("pesel")" gdzies w kodzie reflekcyjnym -
         * blad ujawni sie DOPIERO w runtime, jako "NoSuchFieldException".
         */
        System.out.println("\n=== RYZYKO 3: KRUCHOSC WOBEC REFAKTORYZACJI ===");
        try {
            Employee.class.getDeclaredField("nazwaKtoraNieIstnieje");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException wykryty dopiero W RUNTIME, nie przez kompilator: "
                    + e.getMessage());
        }

        /*
         * 🔍 4. OGRANICZENIA MODULOW (JPMS) - ZAPOWIEDZ LESSON28
         * Od Javy 9 (system modulow, JPMS) domyslnie modul NIE udostepnia
         * swoich pakietow do refleksji "z zewnatrz" - nawet "setAccessible(true)"
         * moze zakonczyc sie wyjatkiem "InaccessibleObjectException", jesli
         * modul docelowy nie zadeklarowal "opens" dla danego pakietu (albo
         * nie jest to modul "open module"). To swiadome ograniczenie ze
         * strony tworcow Javy - ma chronic wewnetrzne API silnika (np. JDK)
         * przed tym samym ryzykiem, o ktorym mowimy w tej lekcji. Nasz
         * projekt dziala jako "classpath" (bez pliku module-info.java), wiec
         * tego ograniczenia NIE odczuwamy - pelne omowienie "module-info.java",
         * "opens"/"exports" i "InaccessibleObjectException" znajdziesz
         * w Lesson27/Lesson28.
         */
        System.out.println("\n=== RYZYKO 4: JPMS (ZAPOWIEDZ LESSON27/28) ===");
        System.out.println("W aplikacji modulowej (module-info.java) refleksja \"z zewnatrz\" wymaga "
                + "jawnego 'opens pakiet;' - inaczej nawet setAccessible(true) rzuci InaccessibleObjectException.");

        /*
         * ============================================================
         * 📌 KIEDY REFLEKSJA JEST UZASADNIONA?
         * ============================================================
         * ✅ Piszesz BIBLIOTEKE/FRAMEWORK ogolnego przeznaczenia (DI, ORM,
         *    serializacja, testy) - i to jest DOKLADNIE Twoj przypadek uzycia.
         * ✅ Musisz obslugiwac typy NIEZNANE w czasie kompilacji (np. wtyczki
         *    ladowane po nazwie z konfiguracji - patrz tez Lesson26_ServiceLoaderAndSpi).
         * ✅ Piszesz narzedzia deweloperskie/diagnostyczne (debugger, IDE,
         *    narzedzia do inspekcji obiektow).
         * ❌ W ZWYKLYM kodzie aplikacyjnym (logika biznesowa) - tu niemal
         *    zawsze istnieje lepsza alternatywa: interfejsy, polimorfizm,
         *    wzorce projektowe (Strategy, Factory), zwykle wywolania metod.
         * ❌ Gdy chcesz tylko "obejsc" prywatnosc pola w CUDZEJ klasie, bo
         *    "tak jest wygodniej" - to sygnal, ze prawdopodobnie brakuje
         *    publicznego API, a nie ze potrzebujesz refleksji.
         * Zasada praktyczna: jesli piszesz kod, ktory MUSI dzialac z typami
         * poznanymi dopiero w runtime (albo pisany PRZED ich istnieniem) -
         * refleksja. Jesli po prostu znasz swoje typy w czasie kompilacji -
         * NIE siegaj po refleksje tylko dlatego, ze jest "sprytna".
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Guice/Spring (DI): czytaja "@Inject" przez getDeclaredFields()/
         *   getDeclaredConstructors(), a nastepnie wywoluja setAccessible +
         *   Field.set / Constructor.newInstance.
         * - Hibernate/JPA: tworza encje przez bezargumentowy konstruktor
         *   (nawet prywatny) i wypelniaja pola bezposrednio, z pominieciem
         *   setterow - dlatego encje MUSZA miec taki konstruktor.
         * - Jackson/Gson: mapuja JSON <-> obiekt odczytujac/ustawiajac pola
         *   (lub uzywajac setterow/konstruktora) na podstawie nazw kluczy.
         * - JUnit: znajduje metody @Test przez getDeclaredMethods() +
         *   isAnnotationPresent(), wywoluje je przez Method.invoke.
         * - Ryzyka: wydajnosc (narzut invoke/get/set), zlamana hermetyzacja
         *   i bezpieczenstwo typow (setAccessible), kruchosc wobec refaktoryzacji
         *   (nazwy jako String, brak sprawdzania przez kompilator), ograniczenia
         *   JPMS (opens/exports - szczegoly w Lesson27/28).
         * - Refleksja jest narzedziem dla TWORCOW bibliotek/frameworkow i
         *   przypadkow "nieznany typ w czasie kompilacji" - w zwyklym kodzie
         *   aplikacyjnym niemal zawsze istnieje lepsza alternatywa.
         * - W kolejnej lekcji (Lesson17) poznasz "java.lang.reflect.Proxy" -
         *   mechanizm budujacy CALE implementacje interfejsow w runtime,
         *   oparty na refleksji, ktory stoi za lazy-loading proxy w Hibernate
         *   i za Spring AOP.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    // ============================================================
    // Pomocnicze metody symulujace mechanizmy frameworkow (Przyklady 1-4)
    // ============================================================

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Inject {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Test {
    }

    interface Notifier {
        void send(String message);
    }

    static class EmailNotifier implements Notifier {
        @Override
        public void send(String message) {
            System.out.println("[EmailNotifier] Wysylam e-mail: " + message);
        }
    }

    static class NotificationService {
        @Inject
        private Notifier notifier; // celowo BEZ konstruktora/settera - wstrzykniemy reflekcyjnie

        void notifyUser(String message) {
            notifier.send(message);
        }
    }

    /**
     * Mini-kontener DI - ta sama idea co Guice z Lesson19 w _13_libraries,
     * napisana surowa refleksja: znajduje pola oznaczone @Inject i wstrzykuje
     * "z gory" znana implementacje na podstawie typu pola.
     */
    private static void injectDependencies(Object target) throws IllegalAccessException {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                if (field.getType() == Notifier.class) {
                    field.setAccessible(true);
                    field.set(target, new EmailNotifier());
                    System.out.println("Wstrzyknieto " + EmailNotifier.class.getSimpleName()
                            + " do pola '" + field.getName() + "' na podstawie @Inject i typu pola.");
                }
            }
        }
    }

    static class BookEntity {
        private Long id;
        private String title;

        BookEntity() {
            // bezargumentowy konstruktor - wymagany "jak w Hibernate"
        }

        @Override
        public String toString() {
            return "BookEntity{id=" + id + ", title='" + title + "'}";
        }
    }

    /**
     * Symuluje to, co robi Hibernate: bezargumentowy konstruktor (reflekcyjnie)
     * + wypelnienie prywatnych pol na podstawie mapy "nazwaKolumny -> wartosc".
     */
    private static <T> T buildEntityLikeHibernate(Class<T> entityClass, Map<String, Object> row)
            throws ReflectiveOperationException {
        T entity = entityClass.getDeclaredConstructor().newInstance();
        for (var entry : row.entrySet()) {
            setPrivateField(entity, entry.getKey(), entry.getValue());
        }
        return entity;
    }

    private static void setPrivateField(Object target, String fieldName, Object value)
            throws ReflectiveOperationException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    /**
     * Symuluje to, co robi Jackson/Gson przy serializacji: odczytuje kazde
     * pole reflekcyjnie i buduje z niego prosty napis w stylu JSON.
     */
    private static String toJsonLikeJackson(Object obj) throws IllegalAccessException {
        StringBuilder json = new StringBuilder("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Object value = field.get(obj);
            json.append("\"").append(field.getName()).append("\":")
                    .append(value instanceof String ? "\"" + value + "\"" : value);
            if (i < fields.length - 1) {
                json.append(",");
            }
        }
        return json.append("}").toString();
    }

    static class SampleTestClass {
        @Test
        void shouldAddNumbers() {
            System.out.println("  [SampleTestClass] shouldAddNumbers() wykonana - 2 + 2 == " + (2 + 2));
        }

        @Test
        void shouldConcatenateStrings() {
            System.out.println("  [SampleTestClass] shouldConcatenateStrings() wykonana - "
                    + ("ab" + "cd"));
        }

        void notATest() {
            System.out.println("  [SampleTestClass] notATest() - to NIE powinno sie wywolac.");
        }
    }

    /**
     * Symuluje to, co robi JUnit przy starcie klasy testowej: znajduje
     * wszystkie metody oznaczone @Test i wywoluje je reflekcyjnie na
     * NOWEJ instancji klasy testowej.
     */
    private static void runAnnotatedTests(Class<?> testClass) throws ReflectiveOperationException {
        Object instance = testClass.getDeclaredConstructor().newInstance();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                System.out.println("Znaleziono @Test: " + method.getName() + "() - wywoluje...");
                method.invoke(instance);
            }
        }
    }

    static class Employee {
        private String pesel;
    }
}
