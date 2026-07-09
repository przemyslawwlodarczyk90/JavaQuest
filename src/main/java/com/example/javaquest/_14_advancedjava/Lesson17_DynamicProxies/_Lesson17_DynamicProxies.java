package com.example.javaquest._14_advancedjava.Lesson17_DynamicProxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class _Lesson17_DynamicProxies {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: DYNAMICZNE PROXY (java.lang.reflect.Proxy) ===");

        /*
         * ============================================================
         * 📦 CZYM JEST DYNAMICZNE PROXY?
         * ============================================================
         * W Lesson15/16 refleksja pozwalala nam ODCZYTYWAC i WYWOLYWAC
         * ISTNIEJACE klasy/metody/pola. "java.lang.reflect.Proxy" idzie o
         * krok dalej: pozwala WYGENEROWAC W CZASIE DZIALANIA (runtime) CALA
         * NOWA KLASE implementujaca dowolny INTERFEJS (lub kilka interfejsow
         * naraz) - bez pisania ani jednej linijki tej klasy w kodzie zrodlowym,
         * i bez kompilacji. Wygenerowany obiekt "podszywa sie" pod dany
         * interfejs, ale KAZDE wywolanie jego metody jest w rzeczywistosci
         * przekierowywane do JEDNEGO, wspolnego obiektu - "InvocationHandler" -
         * ktory decyduje, co z tym wywolaniem zrobic (np. zaloguj je, zmierz
         * czas, deleguj do prawdziwej implementacji, albo w ogole jej NIE wywoluj).
         *
         * WAZNE OGRANICZENIE: "Proxy.newProxyInstance" potrafi generowac
         * implementacje TYLKO dla INTERFEJSOW, nigdy dla klas konkretnych
         * (do proxowania klas sluza inne narzedzia, np. CGLIB uzywany przez
         * Spring AOP dla klas bez interfejsu - o tym pod koniec lekcji).
         */

        /*
         * ============================================================
         * 🔹 KROK 1: INTERFEJS I PRAWDZIWA IMPLEMENTACJA
         * ============================================================
         * Zaczynamy od zwyklego interfejsu i jego zwyklej implementacji -
         * BEZ zadnej refleksji. To ona bedzie "opakowana" przez proxy.
         */
        System.out.println("\n=== KROK 1: ZWYKLY INTERFEJS + IMPLEMENTACJA (BEZ PROXY) ===");
        PaymentService realService = new RealPaymentService();
        realService.charge("PLN", 199.99);

        /*
         * ============================================================
         * 🔹 KROK 2: InvocationHandler - "MOZG" PROXY
         * ============================================================
         * "InvocationHandler" to interfejs z JEDNA metoda:
         *   Object invoke(Object proxy, Method method, Object[] args) throws Throwable
         * Kazde wywolanie metody NA obiekcie proxy (np. "proxyService.charge(...)")
         * konczy sie wywolaniem WLASNIE TEJ metody "invoke" - "method" to
         * refleksyjny opis wywolanej metody interfejsu, "args" to argumenty,
         * z ktorymi ja wywolano, a "proxy" to sam obiekt proxy (rzadko potrzebny).
         * To dokladnie te same typy "Method"/"Object[]" co przy "Method.invoke(...)"
         * z Lesson15 - proxy jest w istocie zbudowane NA refleksji.
         */
        System.out.println("\n=== KROK 2: LOGGING/TIMING InvocationHandler ===");
        InvocationHandler loggingHandler = new LoggingTimingInvocationHandler(realService);

        /*
         * ============================================================
         * 🔹 KROK 3: Proxy.newProxyInstance(...) - GENEROWANIE PROXY
         * ============================================================
         * Trzy argumenty:
         *   1. ClassLoader   - z ktorego "swiata klas" ma pochodzic wygenerowana
         *                      klasa (najczesciej: classloader interfejsu).
         *   2. Class<?>[]    - tablica interfejsow, ktore proxy MA implementowac
         *                      (moze byc wiecej niz jeden!).
         *   3. InvocationHandler - obiekt, do ktorego trafi KAZDE wywolanie.
         * Zwrocony obiekt mozna bezpiecznie RZUTOWAC na typ interfejsu -
         * dla kodu wywolujacego wyglada IDENTYCZNIE jak prawdziwa implementacja.
         */
        System.out.println("\n=== KROK 3: Proxy.newProxyInstance(...) ===");
        PaymentService proxiedService = (PaymentService) Proxy.newProxyInstance(
                PaymentService.class.getClassLoader(),
                new Class<?>[] { PaymentService.class },
                loggingHandler);

        System.out.println("Klasa wygenerowanego obiektu proxy: " + proxiedService.getClass().getName());
        System.out.println("(zauwaz: to NIE jest 'RealPaymentService' - to CALKIEM NOWA klasa, "
                + "wygenerowana w runtime przez JVM, o nazwie zaczynajacej sie od '$Proxy')");
        System.out.println("Proxy.isProxyClass(...) potwierdza to formalnie: "
                + Proxy.isProxyClass(proxiedService.getClass()));

        System.out.println("\n--- Wywolanie metody NA PROXY (kod wywolujacy NIC nie wie o proxy) ---");
        proxiedService.charge("EUR", 49.5);

        /*
         * ============================================================
         * 🔍 CO SIE WLASNIE STALO POD SPODEM?
         * ============================================================
         * "proxiedService.charge("EUR", 49.5)" NIE wywolalo bezposrednio
         * kodu z "RealPaymentService.charge(...)". Zamiast tego:
         *   1. JVM wywolalo "loggingHandler.invoke(proxiedService, chargeMethod, ["EUR", 49.5])".
         *   2. Nasz "LoggingTimingInvocationHandler" wypisal log "PRZED",
         *      zmierzyl czas, i SAM zdecydowal, by wywolac
         *      "method.invoke(realService, args)" - DOPIERO TERAZ uruchamiajac
         *      prawdziwa logike biznesowa.
         *   3. Handler wypisal log "PO" i zwrocil wynik z powrotem do kodu
         *      wywolujacego, jakby nic sie nie stalo.
         * Kod wywolujacy (ten w main()) w OGOLE nie wie, ze ma do czynienia
         * z proxy - dla niego to zwykly "PaymentService".
         */

        /*
         * ============================================================
         * 🔹 DRUGI PRZYKLAD: LICZENIE WYWOLAN + WYLACZANIE METODY
         * ============================================================
         * InvocationHandler moze robic DUZO wiecej niz tylko logowac - moze
         * na przyklad ZLICZAC wywolania, CACHOWAC wyniki, a nawet CALKOWICIE
         * ZABLOKOWAC dostep do wybranej metody, bez zmiany implementacji.
         */
        System.out.println("\n=== PRZYKLAD 2: PROXY LICZACY WYWOLANIA I BLOKUJACY REFUND ===");
        CountingInvocationHandler countingHandler = new CountingInvocationHandler(realService);
        PaymentService guardedService = (PaymentService) Proxy.newProxyInstance(
                PaymentService.class.getClassLoader(),
                new Class<?>[] { PaymentService.class },
                countingHandler);

        guardedService.charge("USD", 10.0);
        guardedService.charge("USD", 20.0);
        try {
            guardedService.refund("USD", 5.0);
        } catch (UnsupportedOperationException e) {
            System.out.println("Zablokowano refund() przez proxy: " + e.getMessage());
        }
        System.out.println("Liczba przechwyconych wywolan lacznie: " + countingHandler.getInvocationCount());

        /*
         * ============================================================
         * 🔍 SKAD ZNAMY JUZ TEN MECHANIZM? LAZY-LOADING PROXY W HIBERNATE
         * ============================================================
         * W _12_hibernate/Lesson15_FetchTypesAndNPlusOne czytaliscie, ze
         * relacja "FetchType.LAZY" NIE laduje powiazanego obiektu od razu -
         * zamiast prawdziwej encji, Hibernate podstawia PROXY. Idea jest
         * DOKLADNIE ta sama, co tutaj (Hibernate faktycznie uzywa biblioteki
         * bajtkodowej, np. Bytebuddy/ByteBuddy, a nie zawsze wprost
         * java.lang.reflect.Proxy, bo czesto potrzebuje proxowac KLASY, nie
         * interfejsy - ale koncepcja "InvocationHandler" jest identyczna):
         *   - Kod aplikacji dostaje obiekt, ktory WYGLADA jak "Author" -
         *     mozna wywolac "author.getName()".
         *   - Pierwsze wywolanie DOWOLNEJ metody na tym proxy URUCHAMIA
         *     zapytanie SQL do bazy ("PRZED" - jak w naszym LoggingTimingHandler)
         *     i DOPIERO WTEDY deleguje wywolanie do prawdziwego, zaladowanego
         *     obiektu.
         *   - Kolejne odwolania do JUZ zaladowanego proxy po prostu delegujа
         *     dalej, bez ponownego zapytania.
         * To wlasnie dlatego problem "N+1" z Lesson15 objawial sie osobnym
         * zapytaniem SQL PRZY KAZDYM "author.getBooks()" w petli - kazde
         * wywolanie na LAZY proxy jest przechwycone i moze wywolac dodatkowa
         * prace (tutaj: zapytanie do bazy), zupelnie jak nasz "loggingHandler"
         * przechwytuje kazde wywolanie "charge(...)".
         */
        System.out.println("\n=== POWIAZANIE Z HIBERNATE: LAZY PROXY (Lesson15 w _12_hibernate) ===");
        System.out.println("Hibernate's LAZY proxy = ten sam wzorzec: przechwyc kazde wywolanie metody,");
        System.out.println("PRZED delegacja wykonaj dodatkowa prace (zapytanie SQL), potem oddaj wynik.");

        /*
         * ============================================================
         * 🔍 SKAD ZNAMY JUZ TEN MECHANIZM? SPRING AOP
         * ============================================================
         * Spring AOP (Aspect-Oriented Programming) - adnotacje takie jak
         * "@Transactional" czy "@Cacheable" (o ktorych uslyszysz przy okazji
         * Spring Boota) - dzialaja na TEJ SAMEJ zasadzie: Spring TWORZY proxy
         * wokol Twojego beana (jesli bean implementuje interfejs - dokladnie
         * "java.lang.reflect.Proxy" jak tutaj; jesli nie - CGLIB, generujacy
         * podklase). Adnotacja "@Transactional" nad metoda to sygnal, by
         * "InvocationHandler" (u Springa: "MethodInterceptor") otworzyl
         * transakcje PRZED wywolaniem prawdziwej metody i zatwierdzil/wycofal
         * ja PO - dokladnie ten sam schemat "przed/po", ktory sami napisalismy
         * w "LoggingTimingInvocationHandler" powyzej.
         */
        System.out.println("\n=== POWIAZANIE ZE SPRING AOP ===");
        System.out.println("@Transactional/@Cacheable w Springu = proxy + InvocationHandler-podobny");
        System.out.println("'MethodInterceptor' otwierajacy/zamykajacy logike PRZED i PO Twoja metoda.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - "java.lang.reflect.Proxy" generuje W RUNTIME nowa klase
         *   implementujaca WSKAZANE INTERFEJSY (nie klasy konkretne).
         * - "InvocationHandler.invoke(proxy, method, args)" to JEDYNE miejsce,
         *   przez ktore przechodzi KAZDE wywolanie metody na obiekcie proxy -
         *   tam decydujemy, czy/jak/kiedy delegowac do prawdziwej implementacji
         *   (uzywajac "method.invoke(target, args)" - dokladnie jak w Lesson15).
         * - "Proxy.newProxyInstance(classLoader, interfaces, handler)" tworzy
         *   gotowy obiekt, ktory mozna rzutowac na typ interfejsu - kod
         *   wywolujacy NIE WIE, ze ma do czynienia z proxy.
         * - Typowe zastosowania: logowanie, mierzenie czasu, cachowanie,
         *   kontrola dostepu/blokowanie metod, lazy-loading (Hibernate),
         *   AOP - transakcje/cache/security (Spring AOP).
         * - Ograniczenie: TYLKO interfejsy. Proxowanie KLAS konkretnych
         *   (bez interfejsu) wymaga bibliotek generujacych bajtkod w locie
         *   (np. CGLIB/ByteBuddy) - poza zakresem "czystej" Javy.
         * - W kolejnej lekcji (Lesson18) poznasz "MethodHandles" - nowoczesniejsza,
         *   bardziej wydajna alternatywe dla klasycznej refleksji (Lesson15),
         *   ktora rowniez stoi u podstaw dzialania niektorych mechanizmow JVM.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    /**
     * Interfejs, ktory bedziemy proxowac - "java.lang.reflect.Proxy" wymaga
     * interfejsu, nigdy klasy konkretnej.
     */
    interface PaymentService {
        void charge(String currency, double amount);

        void refund(String currency, double amount);
    }

    /**
     * Prawdziwa, "biznesowa" implementacja - kompletnie nieswiadoma tego,
     * ze kiedykolwiek zostanie opakowana proxy.
     */
    static class RealPaymentService implements PaymentService {
        @Override
        public void charge(String currency, double amount) {
            System.out.println("  [RealPaymentService] Obciazono " + amount + " " + currency);
        }

        @Override
        public void refund(String currency, double amount) {
            System.out.println("  [RealPaymentService] Zwrocono " + amount + " " + currency);
        }
    }

    /**
     * InvocationHandler logujacy KAZDE wywolanie PRZED i PO delegacji do
     * prawdziwego obiektu, oraz mierzacy czas wykonania.
     */
    static class LoggingTimingInvocationHandler implements InvocationHandler {
        private final Object target;

        LoggingTimingInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("  [PROXY] PRZED wywolaniem: " + method.getName()
                    + "(" + (args == null ? "" : java.util.Arrays.toString(args)) + ")");
            long start = System.nanoTime();
            try {
                return method.invoke(target, args); // ta sama Method.invoke() co w Lesson15
            } finally {
                long elapsedMicros = (System.nanoTime() - start) / 1000;
                System.out.println("  [PROXY] PO wywolaniu: " + method.getName()
                        + " (czas: " + elapsedMicros + " mikrosekund)");
            }
        }
    }

    /**
     * InvocationHandler zliczajacy wywolania oraz BLOKUJACY metode "refund" -
     * pokazuje, ze proxy moze rowniez ZMIENIC zachowanie, a nie tylko je
     * obserwowac.
     */
    static class CountingInvocationHandler implements InvocationHandler {
        private final Object target;
        private int invocationCount = 0;

        CountingInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            invocationCount++;
            if (method.getName().equals("refund")) {
                throw new UnsupportedOperationException("refund() jest wylaczony dla tego proxy");
            }
            return method.invoke(target, args);
        }

        int getInvocationCount() {
            return invocationCount;
        }
    }
}
