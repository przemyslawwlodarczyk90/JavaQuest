package com.example.javaquest._07_servlets.Lesson16_ServletAnnotations;

public class _Exercises_Lesson16_ServletAnnotations {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ReadWebServletValue {
        /*
         * 🧪 Zadanie 1:
         * Oznacz serwlet adnotacją @WebServlet("/greet"). Przez refleksję odczytaj
         * zadeklarowaną ścieżkę (annotation.value()[0]) i użyj JEJ (a nie zapisanego na
         * sztywno literału) w context.addServletMappingDecoded(...). Uruchom Tomcat,
         * wyślij GET pod tę ścieżkę i zweryfikuj status 200.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MultiplePathsWebServlet {
        /*
         * 🧪 Zadanie 2:
         * Oznacz serwlet adnotacją @WebServlet({"/a", "/b"}) (dwie ścieżki na jednej
         * klasie). Przez refleksję pobierz tablicę value() i w pętli zarejestruj OBIE
         * ścieżki dla tego samego serwletu. Wyślij zadania GET pod "/a" i "/b" i
         * potwierdź, że oba obsługuje ten sam serwlet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReadServletNameAttribute {
        /*
         * 🧪 Zadanie 3:
         * Użyj @WebServlet(name = "myServlet", urlPatterns = "/named"). Odczytaj przez
         * refleksję zarówno name(), jak i urlPatterns()[0], i zarejestruj serwlet
         * Tomcat.addServlet(context, annotation.name(), instancja) pod odczytaną ścieżką.
         * Wyślij zadanie i zweryfikuj odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WebFilterAnnotationDeclaredOnly {
        /*
         * 🧪 Zadanie 4:
         * Oznacz klasę filtra adnotacją @WebFilter("/filtered/*") (wyłącznie jako
         * metadana - bez skanowania). Odczytaj wzorzec przez refleksję i zarejestruj
         * FilterDef+FilterMap RĘCZNIE, używając odczytanej wartości. Wyślij zadanie pod
         * pasujący adres i potwierdź, że filtr faktycznie zadziałał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WebListenerAnnotationDeclaredOnly {
        /*
         * 🧪 Zadanie 5:
         * Oznacz klasę listenera adnotacją @WebListener. Przed rejestracją sprawdź przez
         * refleksję (isAnnotationPresent(WebListener.class)) i wypisz potwierdzający log,
         * a następnie zarejestruj go jak zwykle przez
         * context.addApplicationListener(NazwaKlasy.class.getName()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AssertAnnotationMatchesRegistration {
        /*
         * 🧪 Zadanie 6:
         * Serwlet oznaczony @WebServlet("/check"). Zarejestruj go pod stałą tekstową
         * ("/check"), która celowo MUSI zgadzać się z adnotacją. Napisz porównanie
         * annotation.value()[0].equals(stała) i wypisz "OK: zgodne" lub "BŁĄD: niezgodne".
         * W tym zadaniu wartości powinny się zgadzać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MismatchDetectionDemo {
        /*
         * 🧪 Zadanie 7:
         * Serwlet oznaczony @WebServlet("/foo"), ale zarejestruj go RĘCZNIE pod INNĄ
         * ścieżką "/bar" (celowa niezgodność). Napisz kod porównujący wartość adnotacji z
         * faktycznie użytą ścieżką rejestracji i wypisujący czytelny komunikat o
         * wykrytej niezgodności. Wyślij zadanie pod "/bar" (jedyny działający adres) i
         * potwierdź odpowiedź mimo niezgodności metadanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleServletsAnnotatedIndependently {
        /*
         * 🧪 Zadanie 8:
         * Utwórz dwie osobne klasy serwletów, każda z własną adnotacją @WebServlet o innej
         * ścieżce. Zarejestruj obie, w każdym przypadku odczytując ścieżkę z adnotacji
         * przez refleksję. Wyślij zadania do obu adresów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WebInitParamOnAnnotation {
        /*
         * 🧪 Zadanie 9:
         * Użyj @WebServlet(urlPatterns = "/config", initParams =
         * @WebInitParam(name = "greeting", value = "Hej")). Odczytaj initParams() przez
         * refleksję i zarejestruj te same wartości ręcznie na Wrapperze
         * (wrapper.addInitParameter(...)). Serwlet w doGet odczytuje
         * getInitParameter("greeting") i zwraca go - zweryfikuj, że dotarła wartość z
         * adnotacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LoadOnStartupAnnotationAttribute {
        /*
         * 🧪 Zadanie 10:
         * Użyj @WebServlet(value = "/lazy", loadOnStartup = 1). Odczytaj loadOnStartup()
         * przez refleksję i zastosuj ją ręcznie: wrapper.setLoadOnStartup(wartość) PRZED
         * tomcat.start(). Wyślij zadanie pod "/lazy" i zweryfikuj normalną odpowiedź 200.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ServletAndFilterAnnotatedTogether {
        /*
         * 🧪 Zadanie 11:
         * Serwlet oznaczony @WebServlet("/data") i filtr oznaczony @WebFilter("/data")
         * (obie adnotacje wyłącznie jako metadane), zarejestruj oba ręcznie odczytując
         * ich ścieżki przez refleksję. Wyślij zadanie i potwierdź w logach, że filtr
         * uruchamia się PRZED serwletem dla tej samej ścieżki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ServletAndListenerAnnotatedTogether {
        /*
         * 🧪 Zadanie 12:
         * Połącz @WebServlet oraz @WebListener (kontekstowy) w jednym uruchomieniu, oba
         * zarejestrowane ręcznie. Zweryfikuj kolejność logów: contextInitialized
         * listenera PRZED obsłużeniem zadania przez serwlet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultipleWebFilterAnnotatedClasses {
        /*
         * 🧪 Zadanie 13:
         * Dwie klasy filtrów, każda oznaczona @WebFilter z INNYM wzorcem ścieżki.
         * Zarejestruj obie ręcznie, odczytując wzorce przez refleksję. Wyślij zadania do
         * dwóch różnych adresów i potwierdź, że każdy filtr reaguje wyłącznie na swój
         * zadeklarowany wzorzec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReflectionDrivenServletRegistrationLoop {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj tablicę/listę kilku klas serwletów oznaczonych @WebServlet. Napisz pętlę,
         * która dla każdej klasy: tworzy instancję przez
         * getDeclaredConstructor().newInstance(), odczytuje ścieżkę z adnotacji i
         * rejestruje serwlet - własny, mały "skaner" napisany ręcznie (nie prawdziwe
         * skanowanie kontenera). Wyślij zadania do wszystkich zarejestrowanych ścieżek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AnnotationDrivenFilterAndInitParamCombo {
        /*
         * 🧪 Zadanie 15:
         * Filtr oznaczony @WebFilter(filterName = "myFilter", value = "/x",
         * initParams = @WebInitParam(name = "level", value = "3")). Odczytaj
         * filterName(), value() i initParams() przez refleksję i na ich podstawie
         * zbuduj kompletnie FilterDef + FilterMap + parametr inicjalizacyjny. Zweryfikuj
         * finalne zachowanie filtra dla zadania pod "/x".
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareAnnotatedVsProgrammaticServlet {
        /*
         * 🧪 Zadanie 16:
         * Zarejestruj jeden serwlet WYŁĄCZNIE na podstawie odczytu jego adnotacji
         * @WebServlet (przez refleksję), a drugi zarejestruj w pełni "na sztywno", tak
         * jak we wcześniejszych lekcjach (bez żadnej adnotacji). Wyślij zadania do obu i
         * potwierdź identyczny kształt odpowiedzi mimo różnego pochodzenia konfiguracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ServletNameCollisionCheck {
        /*
         * 🧪 Zadanie 17:
         * Dwie klasy serwletów oznaczone @WebServlet z tą samą wartością name(). Napisz
         * walidację, która PRZED rejestracją porównuje nazwy wszystkich klas do
         * zarejestrowania i wykrywa kolizję (wypisuje czytelny komunikat błędu / rzuca
         * wyjątek), zamiast dopuścić do cichego nadpisania jednego serwletu przez drugi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WebListenerPlusWebFilterCombinedAudit {
        /*
         * 🧪 Zadanie 18:
         * Oznaczony @WebListener kontekstowy listener ustawia licznik odwiedzin jako
         * atrybut kontekstu; oznaczony @WebFilter filtr inkrementuje ten licznik przy
         * każdym zadaniu. Oba zarejestruj ręcznie, odczytując metadane z adnotacji.
         * Serwlet "/stats" zwraca aktualną wartość licznika po kilku zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MultipleUrlPatternsFilterAnnotation {
        /*
         * 🧪 Zadanie 19:
         * Filtr oznaczony @WebFilter(urlPatterns = {"/p1/*", "/p2/*"}). Odczytaj tablicę
         * urlPatterns() i w pętli dodaj OBA wzorce do TEGO SAMEGO FilterMap
         * (filterMap.addURLPattern(...) wywołane dwa razy). Wyślij zadania pod "/p1/x" i
         * "/p2/y" i potwierdź, że filtr reaguje na oba prefiksy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DispatcherTypesAnnotationAttributeExploration {
        /*
         * 🧪 Zadanie 20:
         * Filtr oznaczony @WebFilter(value = "/y", dispatcherTypes = DispatcherType.REQUEST).
         * Odczytaj dispatcherTypes() przez refleksję i zastosuj ją ręcznie:
         * filterMap.setDispatcher(DispatcherType.REQUEST.name()) przed rejestracją.
         * Zweryfikuj, że filtr wciąż działa poprawnie dla zwykłego zadania GET.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MiniManualAnnotationScanner {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę scanAndRegister(Context context, Tomcat tomcat, Class<?>...
         * classes), która dla każdej podanej klasy sprawdza obecność @WebServlet,
         * @WebFilter (przy implementacji Filter) lub @WebListener i odpowiednio ją
         * rejestruje - Twój własny, miniaturowy "skaner adnotacji" działający na kilku
         * przygotowanych klasach testowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ValidateAllRegistrationsMatchAnnotationsAtStartup {
        /*
         * 🧪 Zadanie 22:
         * Po ręcznej rejestracji kilku oznaczonych adnotacjami serwletów/filtrów, napisz
         * krok walidacyjny wykonywany PRZED tomcat.start(), który dla każdej klasy
         * ponownie odczytuje adnotację i porównuje ją z faktycznie użytymi wartościami
         * rejestracji, rzucając IllegalStateException przy jakiejkolwiek niezgodności.
         * Przetestuj wersję zgodną (bez wyjątku) i celowo niezgodną (z wyjątkiem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FilterOrderFromCustomPriorityConvention {
        /*
         * 🧪 Zadanie 23:
         * Ponieważ @WebFilter nie definiuje kolejności, zaprojektuj własną konwencję
         * (np. dodatkowy interfejs z metodą int priority()) i na jej podstawie RĘCZNIE
         * ustal kolejność rejestracji kilku filtrów. Zweryfikuj, że faktyczna kolejność
         * wykonania łańcucha filtrów odpowiada Twojemu priorytetowi, a nie kolejności
         * deklaracji klas w kodzie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CombineFilterListenerAndServletAnnotations {
        /*
         * 🧪 Zadanie 24:
         * Połącz oznaczony @WebFilter filtr mierzący czas (wzorzec z Lekcji 14), oznaczony
         * @WebListener listener startu/zamknięcia (Lekcja 15) i oznaczony @WebServlet
         * serwlet z tej lekcji - wszystkie zarejestrowane ręcznie na podstawie odczytu
         * adnotacji. Zweryfikuj pełną, poprawną sekwencję: init -> filtr-przed -> serwlet
         * -> filtr-po -> (później) destroy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_AnnotationBasedConfigAuditReport {
        /*
         * 🧪 Zadanie 25:
         * Dla wszystkich klas zarejestrowanych w tym pliku ćwiczenia, zbuduj tekstowy
         * "raport audytu": rodzaj (serwlet/filtr/listener), wartość zadeklarowana w
         * adnotacji oraz wartość faktycznie użyta przy rejestracji, z kolumną
         * ZGODNE/NIEZGODNE. Wypisz sformatowaną tabelę na końcu programu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DynamicPathRegistrationFromExternalConfig {
        /*
         * 🧪 Zadanie 26:
         * Symuluj "zewnętrzną konfigurację" jako Map<String,String> mogącą nadpisać
         * domyślną ścieżkę zadeklarowaną w @WebServlet. Napisz logikę: jeśli mapa
         * zawiera override dla danej klasy - użyj go do rejestracji, w przeciwnym razie
         * użyj wartości z adnotacji. Przetestuj oba przypadki (z override i bez) w
         * dwóch osobnych uruchomieniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_WebInitParamValidationAcrossMultipleServlets {
        /*
         * 🧪 Zadanie 27:
         * Kilka serwletów oznaczonych @WebServlet(initParams = ...). Zarejestruj wszystkie
         * parametry programowo, a następnie napisz krok walidacyjny PRZED tomcat.start(),
         * który dla każdego serwletu potwierdza, że KAŻDY parametr zadeklarowany w
         * adnotacji faktycznie trafił na Wrapper (wrapper.findInitParameter(nazwa) równa
         * się wartości z adnotacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TwoServletsOneMismatchedConsistencyReport {
        /*
         * 🧪 Zadanie 28:
         * Powtórz ideę z Zadania 7, ale dla DWÓCH serwletów jednocześnie: jeden z
         * poprawnie zgodną rejestracją, drugi z celowo niezgodną. Zbuduj i wypisz
         * końcowy raport jasno wskazujący, który serwlet przeszedł, a który nie przeszedł
         * kontroli zgodności adnotacja-rejestracja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SingleDescriptorClassRegisteringAll {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj zwykłą klasę-opisową (bez adnotacji) z polami: ścieżka serwletu,
         * wzorzec filtra, nazwa klasy listenera. Napisz metodę, która na podstawie JEDNEJ
         * instancji tej klasy wykonuje WSZYSTKIE trzy rejestracje (serwlet + filtr +
         * listener) spójnie, z jednego źródła prawdy - i skontrastuj to podejście z
         * podejściem adnotacyjnym z wcześniejszych zadań (komentarzem w kodzie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneAnnotatedMiniApp {
        /*
         * 🧪 Zadanie 30:
         * Kapstone: co najmniej 2 oznaczone adnotacjami serwlety, 1 filtr i 1 listener,
         * wszystkie zarejestrowane przez własny skaner z Zadania 21. Przetestuj pełny
         * przepływ zadań do wszystkich endpointów i wypisz na końcu raport zgodności (jak
         * w Zadaniu 25), potwierdzający, że adnotacje to tylko metadane - to Twój kod
         * musiał je faktycznie odczytać i zastosować.
         */
        public static void main(String[] args) { }
    }
}
