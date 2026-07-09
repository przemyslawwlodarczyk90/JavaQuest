package com.example.javaquest._14_advancedjava.Lesson17_DynamicProxies;

public class _Exercises_Lesson17_DynamicProxies {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InterfaceAndRealImplementation {
        /*
         * 🧪 Zadanie 1:
         * Utworz interfejs "Greeter" z metoda "String greet(String name)" oraz
         * implementacje "EnglishGreeter" zwracajaca "\"Hello, \" + name".
         * Wywolaj ja BEZ zadnego proxy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MinimalInvocationHandler {
        /*
         * 🧪 Zadanie 2:
         * Napisz klase implementujaca "InvocationHandler", ktora w metodzie
         * "invoke(...)" po prostu DELEGUJE kazde wywolanie do przekazanego w
         * konstruktorze obiektu docelowego (przez "method.invoke(target, args)"),
         * bez zadnej dodatkowej logiki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateFirstProxy {
        /*
         * 🧪 Zadanie 3:
         * Uzyj "Proxy.newProxyInstance(...)" z handlerem z Zadania 2 i
         * interfejsem "Greeter" (Zadanie 1), by stworzyc obiekt proxy. Wywolaj
         * na nim "greet("Ala")" i wypisz wynik - powinien byc IDENTYCZNY jak
         * bez proxy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrintProxyClassName {
        /*
         * 🧪 Zadanie 4:
         * Dla obiektu proxy z Zadania 3 wypisz "getClass().getName()" oraz
         * wynik "Proxy.isProxyClass(obj.getClass())" - potwierdz, ze to NIE
         * jest klasa "EnglishGreeter".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LoggingBeforeOnly {
        /*
         * 🧪 Zadanie 5:
         * Napisz InvocationHandler, ktory PRZED delegacja wypisuje
         * "Wywolanie: " + method.getName() + " z argumentami: " + Arrays.toString(args),
         * a POTEM deleguje wywolanie. Przetestuj na "Greeter" (Zadanie 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_LoggingBeforeAndAfterWithResult {
        /*
         * 🧪 Zadanie 6:
         * Rozszerz handler z Zadania 5 tak, by PO delegacji wypisywal rowniez
         * ZWROCONY wynik ("Wynik: " + result), zanim go zwroci do wywolujacego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TimingHandler {
        /*
         * 🧪 Zadanie 7:
         * Napisz InvocationHandler mierzacy czas wykonania KAZDEGO wywolania
         * (System.nanoTime() przed/po) i wypisujacy go w milisekundach. Dodaj
         * do "EnglishGreeter" (Zadanie 1) sztuczne opoznienie (Thread.sleep)
         * wewnatrz "greet", by zmierzony czas byl widoczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleMethodsInInterface {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do "Greeter" (Zadanie 1) druga metode "String farewell(String name)".
         * Zaimplementuj ja w "EnglishGreeter". Wywolaj OBIE metody przez TO
         * SAMO proxy (handler z Zadania 6) i sprawdz, ze "method.getName()"
         * poprawnie rozroznia, ktora metoda zostala wywolana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BlockOneMethodEntirely {
        /*
         * 🧪 Zadanie 9:
         * Napisz InvocationHandler, ktory dla metody "farewell" (Zadanie 8)
         * rzuca "UnsupportedOperationException" (BEZ wywolywania prawdziwej
         * implementacji), a dla "greet" dziala normalnie (deleguje). Zlap
         * wyjatek w main() i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareDirectVsProxyCallOutputs {
        /*
         * 🧪 Zadanie 10:
         * Wywolaj "greet("Kasia")" NA WPROST na "EnglishGreeter" oraz PRZEZ
         * proxy (handler z Zadania 2) i porownaj (equals) zwrocone Stringi -
         * wypisz PASS/FAIL potwierdzajac, ze proxy jest "przezroczyste" dla
         * wyniku.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CountInvocationsPerMethod {
        /*
         * 🧪 Zadanie 11:
         * Napisz InvocationHandler z polem "Map<String, Integer> callCounts",
         * ktory dla KAZDEGO wywolania inkrementuje licznik pod kluczem
         * "method.getName()". Po kilku wywolaniach roznych metod wypisz cala
         * mape liczników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CachingProxyForExpensiveMethod {
        /*
         * 🧪 Zadanie 12:
         * Napisz interfejs "Calculator" z metoda "long slowSquare(int n)"
         * (implementacja z "Thread.sleep(500)" symulujaca kosztowne obliczenie).
         * Napisz InvocationHandler CACHUJACY wyniki w "Map<Object, Object>"
         * (klucz = argument) - druga i kolejne wywolania z TYM SAMYM
         * argumentem maja pochodzic z cache (bez ponownego opoznienia). Zmierz
         * i porownaj czasy pierwszego i drugiego wywolania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RetryHandlerOnException {
        /*
         * 🧪 Zadanie 13:
         * Napisz interfejs "FlakyService" z metoda "String call()" oraz
         * implementacje, ktora rzuca wyjatek przy PIERWSZYCH 2 wywolaniach, a
         * dopiero za 3 razem zwraca wynik (licznik w polu implementacji).
         * Napisz InvocationHandler, ktory w razie wyjatku PONAWIA wywolanie
         * (max 3 proby) zanim odda blad dalej. Zademonstruj, ze proxy w koncu
         * zwraca poprawny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultiInterfaceProxy {
        /*
         * 🧪 Zadanie 14:
         * Utworz DWA niezalezne interfejsy "Readable" (String read()) i
         * "Writable" (void write(String)). Napisz JEDNA klase implementujaca
         * OBA. Utworz proxy implementujace OBA interfejsy naraz
         * ("new Class<?>[]{Readable.class, Writable.class}") i wywolaj metody
         * z obu, rzutujac proxy raz na "Readable", raz na "Writable".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NullResultOnUnknownMethodGuard {
        /*
         * 🧪 Zadanie 15:
         * Napisz InvocationHandler, ktory dla metod, ktorych nazwa zaczyna sie
         * od "get", zwraca WYNIK delegacji normalnie, a dla WSZYSTKICH innych
         * metod (np. zaczynajacych sie od "set") loguje ostrzezenie "Operacja
         * zapisu zablokowana w trybie tylko-do-odczytu" i zwraca "null" BEZ
         * delegacji. Przetestuj na interfejsie z metodami "getX()"/"setX(...)"
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ProxyImplementingToStringAndEquals {
        /*
         * 🧪 Zadanie 16:
         * Wywolaj na obiekcie proxy (Zadanie 3) metody "toString()",
         * "hashCode()", "equals(inny)" - w komentarzu wyjasnij (min. 3 zdania),
         * dlaczego rowniez TE metody (odziedziczone po Object) przechodza
         * przez "InvocationHandler.invoke(...)", mimo ze nie zadeklarowano ich
         * w interfejsie "Greeter".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LazyLoadingProxySimulation {
        /*
         * 🧪 Zadanie 17:
         * Napisz interfejs "Author" z metoda "List<String> getBooks()". Napisz
         * "RealAuthor" symulujaca "zapytanie do bazy" (wypisuje "Ladowanie z
         * bazy..." i zwraca liste). Napisz InvocationHandler LazyLoadingHandler,
         * ktory tworzy "RealAuthor" DOPIERO przy PIERWSZYM wywolaniu jakiejkolwiek
         * metody (leniwie), a przy kolejnych uzywa JUZ utworzonego obiektu -
         * jak lazy proxy w Hibernate (Lesson15 w _12_hibernate, omowione w
         * teorii tej lekcji). Udowodnij (licznik "ile razy stworzono RealAuthor"),
         * ze RealAuthor powstal TYLKO RAZ mimo wielu wywolan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TransactionLikeWrapper {
        /*
         * 🧪 Zadanie 18:
         * Napisz interfejs "AccountOperations" z metoda "void transfer(double amount)"
         * (implementacja moze rzucic "RuntimeException" dla kwoty > 1000, symulujac
         * blad biznesowy). Napisz InvocationHandler symulujacy "@Transactional"
         * ze Spring AOP (wspomniane w teorii): wypisuje "BEGIN TRANSACTION",
         * deleguje wywolanie w try/catch, przy wyjatku wypisuje "ROLLBACK" i
         * rzuca dalej, przy sukcesie wypisuje "COMMIT".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ProxyChaining {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj DWA proxy jeden na drugim: najpierw opakuj "EnglishGreeter"
         * (Zadanie 1) handlerem logujacym (Zadanie 6), a NASTEPNIE opakuj
         * WYNIKOWE proxy DRUGIM proxy z handlerem mierzacym czas (Zadanie 7).
         * Wywolaj metode na zewnetrznym proxy i zaobserwuj, ze OBA handlery
         * sie aktywuja (proxy proxy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_HandlerThrowsCustomExceptionOnBadArgument {
        /*
         * 🧪 Zadanie 20:
         * Napisz InvocationHandler, ktory PRZED delegacja WALIDUJE argumenty
         * (np. dla metody z parametrem "double amount" rzuca
         * "IllegalArgumentException", jesli amount < 0) - i TYLKO gdy walidacja
         * przejdzie, deleguje wywolanie. Zademonstruj na "PaymentService"-podobnym
         * interfejsie z Twojej wlasnej definicji, wywolujac raz z poprawna, raz
         * z niepoprawna kwota.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_GenericLoggingProxyFactory {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode generyczna
         * "static <T> T withLogging(T target, Class<T> interfaceType)", ktora
         * zwraca proxy opakowujace DOWOLNY obiekt "target" (implementujacy
         * "interfaceType") handlerem logujacym PRZED/PO kazde wywolanie. Uzyj
         * jej dla DWOCH roznych interfejsow z poprzednich zadan (np. Greeter
         * i Calculator) bez pisania nowego handlera za kazdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MetricsCollectingProxy {
        /*
         * 🧪 Zadanie 22:
         * Napisz InvocationHandler zbierajacy PER-METODA statystyki: liczbe
         * wywolan, laczny czas (ns), sredni czas. Po serii losowych wywolan
         * (min. 20, na min. 2 roznych metodach) wypisz raport w formacie
         * tabelarycznym: nazwa metody | liczba wywolan | sredni czas (mikrosekundy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CircuitBreakerViaProxy {
        /*
         * 🧪 Zadanie 23:
         * Przepisz "CircuitBreakerPaymentGateway" (znany z
         * _13_libraries/Lesson18/Lesson19) tak, by logika "otwartego obwodu"
         * (po 3 kolejnych niepowodzeniach blokuj kolejne wywolania przez
         * pewien czas) byla zaimplementowana jako InvocationHandler opakowujacy
         * DOWOLNY "PaymentGateway"-podobny interfejs, zamiast osobnej klasy
         * dekorujacej. Zademonstruj otwarcie i (po czasie) zamkniecie obwodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AuditLogProxyWithTimestampsAndUser {
        /*
         * 🧪 Zadanie 24:
         * Napisz InvocationHandler AuditingHandler, ktory dla kazdego wywolania
         * zapisuje do wewnetrznej listy wpis "AuditEntry" (metoda, argumenty,
         * timestamp, "aktualny uzytkownik" - String przekazany w konstruktorze
         * handlera). Po serii wywolan wypisz PELNA historie audytu w kolejnosci
         * chronologicznej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ReadOnlyViewProxyOverMutableInterface {
        /*
         * 🧪 Zadanie 25:
         * Napisz interfejs "MutableRepository<T>" z metodami "add(T item)",
         * "remove(T item)", "List<T> findAll()". Napisz InvocationHandler
         * tworzacy proxy "tylko do odczytu": metody "add"/"remove" rzucaja
         * "UnsupportedOperationException", "findAll" deleguje normalnie.
         * Zademonstruj na implementacji przechowujacej liste Stringow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ProxyBackedByRemoteCallSimulation {
        /*
         * 🧪 Zadanie 26:
         * Napisz interfejs "WeatherService" z metoda "String getForecast(String city)".
         * Napisz InvocationHandler symulujacy "zdalne wywolanie" - BEZ prawdziwej
         * implementacji lokalnej (target moze byc "null"!), handler sam buduje
         * i zwraca wynik na podstawie argumentow (np. "Prognoza dla " + city + ": slonecznie").
         * To demonstracja, ze proxy NIE MUSI opakowywac istniejacego obiektu -
         * moze BYC cala implementacja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompositeInvocationHandlerChainOfResponsibility {
        /*
         * 🧪 Zadanie 27:
         * Napisz "InvocationHandler" przyjmujacy LISTE mniejszych "przed-hookow"
         * (np. "List<Consumer<Method>> beforeHooks") wywolywanych PO KOLEI przed
         * delegacja (logowanie, walidacja, licznik - kazdy jako osobny,
         * niezalezny Consumer). Zademonstruj dodanie 3 roznych hookow do jednego
         * proxy i pokaz, ze wszystkie sie wykonuja w kolejnosci dodania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExplainWhyProxyCannotWrapConcreteClassWithoutInterface {
        /*
         * 🧪 Zadanie 28:
         * Sprobuj (w komentarzu, NIE w dzialajacym kodzie - to sie nie skompiluje/
         * zadziala z czystym java.lang.reflect.Proxy) opisac, co by sie stalo,
         * gdybys probowal/a stworzyc proxy dla klasy "RealPaymentService" (z
         * teorii lekcji) BEZ interfejsu "PaymentService". Wyjasnij (min. 4 zdania),
         * dlaczego "Proxy.newProxyInstance" tego nie wspiera i jakiego narzedzia
         * (CGLIB/ByteBuddy - jak w Spring AOP dla beanow bez interfejsu) uzylby
         * w tej sytuacji prawdziwy framework.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_PerformanceCompareDirectVsProxyCall {
        /*
         * 🧪 Zadanie 29:
         * Zmierz czas 200 000 wywolan metody interfejsu (a) bezposrednio na
         * prawdziwej implementacji, (b) przez proxy z pustym, delegujacym
         * handlerem (jak Zadanie 2). Wypisz oba czasy i narzut proxy w
         * procentach - w komentarzu odnies to do Ryzyka 1 z Lesson16 (koszt
         * refleksji, na ktorej proxy jest zbudowane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneAopStyleFrameworkMiniature {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie lekcji: zbuduj miniaturowy "framework AOP". Zdefiniuj
         * wlasna adnotacje "@Logged" (RUNTIME, METHOD). Napisz interfejs
         * biznesowy (min. 3 metody, TYLKO CZESC z nich oznaczona "@Logged" w
         * implementacji) i jego implementacje. Napisz metode
         * "static <T> T createAopProxy(T target, Class<T> interfaceType)",
         * ktora zwraca proxy logujace PRZED/PO wywolanie TYLKO dla metod
         * oznaczonych "@Logged" (sprawdz adnotacje na METODZIE IMPLEMENTACJI,
         * uzywajac "target.getClass().getMethod(...)" wewnatrz handlera) - dla
         * pozostalych metod deleguj CICHO, bez logowania. Zademonstruj wywolanie
         * wszystkich metod i pokaz, ze log pojawia sie TYLKO dla oznaczonych.
         */
        public static void main(String[] args) { }
    }
}
