package com.example.javaquest._14_advancedjava.Lesson18_MethodHandles;

public class _Exercises_Lesson18_MethodHandles {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LookupAndLookupClass {
        /*
         * 🧪 Zadanie 1:
         * Wywolaj "MethodHandles.lookup()" w main() i wypisz
         * "lookup.lookupClass().getSimpleName()" - potwierdz, ze wskazuje na
         * klase, w ktorej zostal wywolany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BuildMethodTypeForNoArgMethod {
        /*
         * 🧪 Zadanie 2:
         * Utworz "MethodType.methodType(String.class)" (opisujacy metode
         * bezargumentowa zwracajaca String) i wypisz go (toString()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FindVirtualForInstanceMethod {
        /*
         * 🧪 Zadanie 3:
         * Utworz klase "Counter" z polem "int value" i metoda "int increment()"
         * (zwieksza i zwraca "value"). Uzyj "lookup.findVirtual(Counter.class,
         * "increment", MethodType.methodType(int.class))" by uzyskac
         * MethodHandle, i wywolaj go przez "invoke(counterInstance)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_InvokeExactWithMatchingTypes {
        /*
         * 🧪 Zadanie 4:
         * Dla uchwytu z Zadania 3 wywolaj "invokeExact(counterInstance)" z
         * jawnym rzutowaniem wyniku na "int" ("(int) handle.invokeExact(...)").
         * Sprawdz, ze dziala IDENTYCZNIE jak "invoke(...)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FindStaticForUtilityMethod {
        /*
         * 🧪 Zadanie 5:
         * Napisz statyczna metode "static int square(int n)" w dowolnej klasie
         * pomocniczej. Uzyj "lookup.findStatic(...)" by uzyskac do niej
         * MethodHandle i wywolaj go dla kilku wartosci przez "invoke(...)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FindConstructorForSimpleClass {
        /*
         * 🧪 Zadanie 6:
         * Napisz klase "Point" z konstruktorem "(int x, int y)". Uzyj
         * "lookup.findConstructor(Point.class, MethodType.methodType(void.class, int.class, int.class))"
         * i utworz przez niego nowy obiekt "Point", rzutujac wynik "invoke(...)"
         * na "Point".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FindGetterForField {
        /*
         * 🧪 Zadanie 7:
         * Dla klasy "Point" (Zadanie 6) uzyj "lookup.findGetter(Point.class, "x", int.class)"
         * by odczytac wartosc pola "x" z instancji przez MethodHandle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FindSetterForField {
        /*
         * 🧪 Zadanie 8:
         * Dla klasy "Point" (Zadanie 6, pole "x" NIE moze byc "final") uzyj
         * "lookup.findSetter(...)" by USTAWIC nowa wartosc pola "x" przez
         * MethodHandle, a nastepnie odczytaj ja zwyklym dostepem (lub
         * getterem z Zadania 7) i potwierdz zmiane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareDirectCallVsMethodHandleResult {
        /*
         * 🧪 Zadanie 9:
         * Wywolaj metode "square(5)" (Zadanie 5) BEZPOSREDNIO oraz przez
         * MethodHandle - porownaj (equals/==) oba wyniki i wypisz PASS/FAIL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WrongMethodTypeExceptionDemo {
        /*
         * 🧪 Zadanie 10:
         * Sprobuj wywolac "invokeExact(...)" na MethodHandle z Zadania 5 z
         * NIEPOPRAWNYM typem argumentu (np. "long" zamiast "int") - zlap
         * "WrongMethodTypeException" i wypisz jej komunikat. W komentarzu
         * (min. 2 zdania) wyjasnij, dlaczego "invoke(...)" w tej samej sytuacji
         * (z automatyczna konwersja) mogloby zadzialac bez wyjatku.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PrivateFieldViaUnreflectSetter {
        /*
         * 🧪 Zadanie 11:
         * Napisz klase z PRYWATNYM polem "String secret". Pobierz jego
         * "Field" przez klasyczna refleksje (Lesson15: getDeclaredField +
         * setAccessible(true)), a nastepnie zamien go w "MethodHandle" przez
         * "lookup.unreflectSetter(field)" / "unreflectGetter(field)". Uzyj
         * powstalych MethodHandle do odczytu/zapisu tego prywatnego pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UnreflectExistingMethodFromLesson15Style {
        /*
         * 🧪 Zadanie 12:
         * Pobierz "Method" reflekcyjnie (jak w Lesson15: getDeclaredMethod)
         * dla dowolnej metody, a nastepnie zamien go w "MethodHandle" przez
         * "lookup.unreflect(method)" - wywolaj go i porownaj wynik z
         * bezposrednim wywolaniem "method.invoke(...)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_BenchmarkReflectionVsMethodHandle {
        /*
         * 🧪 Zadanie 13:
         * Zmierz czas 1 000 000 wywolan tej samej bezargumentowej metody: (a)
         * przez "Method.invoke(...)" (Lesson15), (b) przez "MethodHandle.invoke(...)".
         * Wypisz oba czasy w milisekundach i wspolczynnik roznicy - skomentuj
         * (min. 2 zdania) zgodnosc wyniku z teoria lekcji (MethodHandles jako
         * szybsza alternatywa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MethodHandleForOverloadedMethod {
        /*
         * 🧪 Zadanie 14:
         * Napisz klase z DWOMA przeciazonymi metodami "format(int)" i
         * "format(String)". Uzyj "findVirtual" DWUKROTNIE, z ROZNYMI
         * "MethodType", by uzyskac uchwyty do KAZDEGO z przeciazen osobno -
         * wywolaj oba i wypisz wyniki, pokazujac, ze MethodType jednoznacznie
         * rozroznia przeciazenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConstructorHandleForClassWithTwoConstructors {
        /*
         * 🧪 Zadanie 15:
         * Napisz klase z DWOMA konstruktorami o roznej liczbie parametrow.
         * Uzyj "findConstructor" DWUKROTNIE (raz na kazdy konstruktor, z
         * odpowiednim MethodType) i utworz przez oba uchwyty po jednym
         * obiekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MethodHandleStoredInArrayDispatchedByIndex {
        /*
         * 🧪 Zadanie 16:
         * Napisz klase "Calculator" z metodami "int add(int,int)",
         * "int subtract(int,int)", "int multiply(int,int)". Zbuduj tablice
         * "MethodHandle[]" (po jednym uchwycie na kazda metode, przez
         * findVirtual) i napisz metode "static int calculate(int op, int a, int b)",
         * ktora wywoluje ODPOWIEDNI uchwyt z tablicy na podstawie indeksu "op"
         * (0=add, 1=subtract, 2=multiply).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_HandleGetterAndSetterForFinalFieldReadOnly {
        /*
         * 🧪 Zadanie 17:
         * Napisz klase z polem "final String id" ustawianym w konstruktorze.
         * Uzyj "findGetter" (dziala normalnie), a nastepnie sprobuj
         * "findSetter" na TYM SAMYM polu "final" - zlap wyjatek (np.
         * "IllegalAccessException") i wypisz jego komunikat, wyjasniajac (w
         * komentarzu, min. 2 zdania) dlaczego pola "final" nie maja settera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NoSuchMethodExceptionHandling {
        /*
         * 🧪 Zadanie 18:
         * Sprobuj wywolac "findVirtual" dla NIEISTNIEJACEJ metody (bledna nazwa
         * lub sygnatura) - zlap "NoSuchMethodException" i wypisz przyjazny
         * komunikat bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ChainedMethodHandleCalls {
        /*
         * 🧪 Zadanie 19:
         * Napisz klase "TextBox" z metodami "TextBox append(String s)" (zwraca
         * "this", fluent API) i "String build()". Uzyj MethodHandle do
         * wywolania "append" DWA RAZY z rzedu (wynik pierwszego wywolania
         * jako "this" dla drugiego), a nastepnie "build()" - porownaj z
         * bezposrednim wywolaniem lancuchowym "new TextBox().append("a").append("b").build()".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainInvokeVsInvokeExactWithNumericWidening {
        /*
         * 🧪 Zadanie 20:
         * Utworz MethodHandle dla metody przyjmujacej "double" (findVirtual z
         * MethodType(void.class, double.class)). Wywolaj go przez "invoke(obj, 5)"
         * (podajac "int" literal zamiast "double") - powinno zadzialac dzieki
         * automatycznemu rozszerzeniu typu. Nastepnie sprobuj TEGO SAMEGO przez
         * "invokeExact(obj, 5)" - powinno rzucic "WrongMethodTypeException".
         * W komentarzu wyjasnij roznice.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericMethodHandleInvokerUtility {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode "static Object callNoArg(Object target, String methodName, Class<?> returnType)",
         * ktora WEWNATRZ uzywa "MethodHandles.lookup().findVirtual(target.getClass(), methodName,
         * MethodType.methodType(returnType))" i wywoluje "invoke(target)". Przetestuj
         * ja na 2 roznych klasach z bezargumentowymi metodami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MethodHandleBasedStrategyDispatchTable {
        /*
         * 🧪 Zadanie 22:
         * Napisz "Map<String, MethodHandle>" mapujacy NAZWY operacji
         * (""add"", "subtract", "multiply", "divide") na odpowiednie
         * MethodHandle metod statycznych z sygnatura "(int,int)int". Napisz
         * "static int execute(String opName, int a, int b)" korzystajaca z tej
         * mapy - to strategia dispatch bez switch/if-else, oparta na
         * MethodHandles zamiast na wzorcu Strategy z interfejsami (porownaj z
         * podejsciem obiektowym, ktore mogles/as poznac wczesniej w kursie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BindToPartiallyAppliedMethodHandle {
        /*
         * 🧪 Zadanie 23:
         * Napisz klase "Multiplier" z metoda instancyjna "int multiplyBy(int n)".
         * Utworz MethodHandle przez "findVirtual", a nastepnie uzyj
         * "handle.bindTo(instancjaMultiplier)", by uzyskac NOWY uchwyt, ktory
         * NIE wymaga juz podawania instancji przy kazdym wywolaniu (tylko
         * "n"). Porownaj wywolanie PRZED i PO "bindTo" (rozna liczba argumentow).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AsTypeConversion {
        /*
         * 🧪 Zadanie 24:
         * Dla MethodHandle metody statycznej "int square(int)" (Zadanie 5)
         * uzyj "handle.asType(MethodType.methodType(long.class, long.class))",
         * by otrzymac NOWY uchwyt akceptujacy/zwracajacy "long" zamiast "int".
         * Wywolaj go przez "invokeExact(5L)" (teraz to zadziala, bo typy
         * zgadzaja sie z NOWYM MethodType) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ReflectiveVsMethodHandleFieldAccessBenchmark {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas 500 000 operacji odczytu tego samego pola: (a) przez
         * "Field.get()" (Lesson15, pole pobrane RAZ przed petla), (b) przez
         * "MethodHandle" z "findGetter" (rowniez pobrany RAZ). Wypisz oba
         * czasy i porownaj w komentarzu z wynikiem Zadania 13.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExceptionHandlingWithCatchException {
        /*
         * 🧪 Zadanie 26:
         * Napisz metode "static int parseOrDefault(String raw)" rzucajaca
         * "NumberFormatException" dla niepoprawnego wejscia. Uzyj
         * "MethodHandles.catchException(...)" by zbudowac NOWY uchwyt, ktory
         * w razie "NumberFormatException" zwraca wartosc domyslna "-1" zamiast
         * propagowac wyjatek. Przetestuj z poprawnym i niepoprawnym Stringiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MiniOrmFieldMapperUsingMethodHandles {
        /*
         * 🧪 Zadanie 27:
         * Przepisz "buildEntityLikeHibernate" z Lesson16 (teoria) tak, by
         * zamiast "Field.set(...)" uzywac "MethodHandle" z "findSetter" (po
         * jednym uchwycie na kazde pole, zbudowanym RAZ i przechowywanym w
         * mapie "Map<String, MethodHandle>"). Zademonstruj na klasie "encji" z
         * min. 3 polami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareBytecodeGeneratedByLambdaVsAnonymousClassConceptually {
        /*
         * 🧪 Zadanie 28:
         * W komentarzu (bez kodu, min. 5 zdan) opisz - odwolujac sie do teorii
         * tej lekcji - jak invokedynamic + LambdaMetafactory + MethodHandle
         * wspolpracuja, by "utworzyc" implementacje interfejsu funkcyjnego dla
         * wyrazenia lambda z Lesson09, i dlaczego jest to bardziej wydajne niz
         * gdyby kompilator za kazdym razem generowal osobna, nazwana klase
         * anonimowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullComparisonTableReflectionProxyMethodHandle {
        /*
         * 🧪 Zadanie 29:
         * W komentarzu (tabela tekstowa, min. 4 wiersze) porownaj TRZY
         * mechanizmy poznane w tym klastrze lekcji (15-18): klasyczna
         * refleksja (Lesson15), dynamiczne proxy (Lesson17), MethodHandles
         * (Lesson18) - kolumny: "typowe zastosowanie", "wydajnosc",
         * "bezpieczenstwo typow", "poziom trudnosci API".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMethodHandleBasedEventDispatcher {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji (i calego klastra 15-18): napisz klase
         * "EventDispatcher" z metoda "void register(Object handlerInstance, String methodName)",
         * ktora WEWNATRZ (przez findVirtual, z sygnatura "(String)void") buduje
         * MethodHandle do metody obslugujacej zdarzenie na PRZEKAZANEJ
         * instancji i zapamietuje go w liscie. Metoda "void dispatch(String eventPayload)"
         * ma wywolac WSZYSTKIE zarejestrowane uchwyty (kazdy zwiazany z inna
         * instancja, przez "bindTo" - patrz Zadanie 23) z przekazanym payloadem.
         * Zarejestruj min. 3 rozne obiekty-handlery i wyslij 2 zdarzenia -
         * pokaz, ze WSZYSCY subskrybenci otrzymuja kazde zdarzenie, bez ANI
         * JEDNEGO interfejsu/callbacka zdefiniowanego z gory (caly dispatch
         * oparty WYLACZNIE na MethodHandles dobranych po nazwie metody).
         */
        public static void main(String[] args) { }
    }
}
