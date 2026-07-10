package com.example.javaquest._15_jvm_internals.Lesson03_ClassLoadingMechanics;

public class _Exercises_Lesson03_ClassLoadingMechanics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_PrintOwnClassLoader {
        /*
         * 🧪 Zadanie 1:
         * Wypisz class loader tej klasy przy pomocy getClass().getClassLoader().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_PrintStringClassLoader {
        /*
         * 🧪 Zadanie 2:
         * Wypisz class loader klasy java.lang.String. Powinien być null -
         * wyjaśnij w komentarzu dlaczego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PrintParentOfApplicationClassLoader {
        /*
         * 🧪 Zadanie 3:
         * Pobierz class loader tej klasy i wypisz jego rodzica
         * (getParent()) - jaki to loader?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_UseClassForNameBasic {
        /*
         * 🧪 Zadanie 4:
         * Użyj Class.forName("java.util.HashMap") i wypisz zwrócony
         * obiekt Class (getName()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_CatchClassNotFoundException {
        /*
         * 🧪 Zadanie 5:
         * Spróbuj wczytać nieistniejącą klasę przez Class.forName
         * ("com.example.NieIstnieje") i złap ClassNotFoundException,
         * wypisując czytelny komunikat.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_ExplainBootstrapLoaderInComment {
        /*
         * 🧪 Zadanie 6:
         * W komentarzu (min. 3 zdania) wyjaśnij, czym jest Bootstrap Class
         * Loader i dlaczego z poziomu kodu Javy widać go jako null.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_CreateClassWithStaticBlock {
        /*
         * 🧪 Zadanie 7:
         * Stwórz zagnieżdżoną klasę statyczną z blokiem statycznym, który
         * wypisuje "Zainicjalizowano!". Utwórz jej instancję (new) i
         * zaobserwuj, kiedy pojawia się ten komunikat.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_ListThreeClassLoaderTypes {
        /*
         * 🧪 Zadanie 8:
         * Wypisz (na sztywno, jako tekst) trzy wbudowane class loadery
         * JVM w kolejności hierarchii, razem z jednym zdaniem opisu
         * każdego z nich.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_CompareTwoClassLoaderInstancesEquality {
        /*
         * 🧪 Zadanie 9:
         * Pobierz class loader tej klasy dwukrotnie do dwóch zmiennych i
         * porównaj je operatorem "==" - wypisz wynik (powinny być tym
         * samym obiektem).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_PrintClassLoaderOfArrayList {
        /*
         * 🧪 Zadanie 10:
         * Wypisz class loader klasy java.util.ArrayList oraz porównaj go
         * (przez ==) z class loaderem klasy String - czy to ten sam
         * obiekt?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_DemonstrateLazyInitializationWithStaticField {
        /*
         * 🧪 Zadanie 11:
         * Stwórz klasę zagnieżdżoną z blokiem statycznym drukującym
         * komunikat oraz statyczną metodą. Użyj Class.forName(nazwa, false,
         * loader) aby TYLKO ją załadować - upewnij się (przez brak
         * komunikatu), że blok statyczny się nie wykonał. Następnie wywołaj
         * jej metodę i zaobserwuj moment inicjalizacji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_CompareForNameWithAndWithoutInitialize {
        /*
         * 🧪 Zadanie 12:
         * Stwórz klasę ze statycznym blokiem. Załaduj ją RAZ przez
         * Class.forName(nazwa) (jednoargumentowa - inicjalizuje) i
         * porównaj zachowanie z Class.forName(nazwa, false, loader) na
         * INNEJ, podobnej klasie - wypisz różnicę w momencie pojawienia
         * się komunikatu z bloku statycznego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_PrintFullClassLoaderChainAsList {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę, która dla podanej klasy wypisuje CAŁY łańcuch
         * class loaderów (od loadera klasy, przez kolejnych rodziców, aż
         * do null) w formacie listy ponumerowanej.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_TriggerInitializationViaStaticFieldAccess {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę z NIE-finalnym statycznym polem (np. static int
         * counter = 10;) oraz blokiem statycznym. Wypisz komunikat, że
         * zamierzasz odczytać pole, odczytaj je (np. przez
         * KlasaX.counter) i zaobserwuj, że inicjalizacja następuje w
         * TYM momencie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_ShowCompileTimeConstantDoesNotTriggerInit {
        /*
         * 🧪 Zadanie 15:
         * Stwórz klasę z polem "static final int VALUE = 42;" (compile-time
         * constant) oraz blokiem statycznym. Odczytaj VALUE z zewnątrz i
         * sprawdź (obserwując brak komunikatu), że kompilator "wkleił"
         * (inlined) wartość 42 bezpośrednio - klasa może wcale nie zostać
         * zainicjalizowana. Skomentuj wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_ExplainVerificationPhaseWithExample {
        /*
         * 🧪 Zadanie 16:
         * W komentarzu wyjaśnij fazę Verification (część Linking) i podaj
         * przykład tego, co weryfikator bajtkodu mógłby wykryć jako błąd
         * (np. niezgodność typów na stosie operandów).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_ExplainPreparationVsInitializationDifference {
        /*
         * 🧪 Zadanie 17:
         * W komentarzu wyjaśnij różnicę między fazą Preparation (pola
         * statyczne dostają wartości DOMYŚLNE: 0/null/false) a fazą
         * Initialization (pola statyczne dostają wartości Z KODU) - podaj
         * konkretny przykład jednego pola w obu fazach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_BuildClassLoaderInspectorForMultipleClasses {
        /*
         * 🧪 Zadanie 18:
         * Napisz narzędzie, które przyjmuje tablicę obiektów Class<?> (np.
         * String.class, ArrayList.class, Twoja własna klasa, klasa
         * biblioteczna typu java.time.LocalDate) i dla każdej wypisuje
         * łańcuch class loaderów oraz informację, czy dwie z nich mają
         * WSPÓLNEGO bezpośredniego loadera.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_DemonstrateOrderOfMultipleStaticBlocks {
        /*
         * 🧪 Zadanie 19:
         * Stwórz klasę z DWOMA blokami statycznymi (każdy wypisujący inny
         * komunikat) oraz statycznym polem inicjalizowanym wywołaniem
         * metody. Zainicjalizuj klasę i wypisz kolejność, w jakiej
         * wykonują się te elementy (powinna być zgodna z kolejnością w
         * kodzie źródłowym).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_CompareLoadClassVsForNameBehavior {
        /*
         * 🧪 Zadanie 20:
         * Użyj this.getClass().getClassLoader().loadClass(nazwa) na klasie
         * z blokiem statycznym i porównaj zachowanie (czy inicjalizuje) z
         * Class.forName(nazwa) na tej samej klasie (w osobnym uruchomieniu
         * / innej podobnej klasie) - wypisz wnioski.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_SimulateParentFirstDelegationManually {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę symulującą (na sztywno, bez prawdziwego
         * ClassLoadera) algorytm delegacji parent-first jako rekurencyjną
         * funkcję operującą na liście nazw loaderów (np.
         * ["Application", "Platform", "Bootstrap"]) i liście "znanych"
         * klas każdego z nich - zwróć, KTÓRY loader faktycznie załadowałby
         * podaną klasę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_MeasureTimeDifferenceLoadVsInitialize {
        /*
         * 🧪 Zadanie 22:
         * Stwórz klasę z "kosztownym" blokiem statycznym (np. pętla
         * licząca coś przez chwilę, ale bez sleep - czysto obliczeniowa).
         * Zmierz (System.nanoTime()) czas samego ładowania (forName z
         * initialize=false) osobno od czasu pierwszego użycia (co wyzwala
         * inicjalizację) - wypisz oba czasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_BuildClassLoadingLifecycleLogger {
        /*
         * 🧪 Zadanie 23:
         * Stwórz klasę z konstruktorem, blokiem statycznym i metodą
         * instancyjną, każdy wypisujący komunikat ze znacznikiem czasu
         * (System.nanoTime()). Utwórz dwie instancje tej klasy i wypisz
         * pełny "dziennik" pokazujący, że blok statyczny wykonuje się
         * TYLKO RAZ, a konstruktor za każdym razem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_ExplainWhyStaticNestedClassAffectsInitializationTiming {
        /*
         * 🧪 Zadanie 24:
         * Stwórz klasę zewnętrzną z zagnieżdżoną klasą statyczną, każda z
         * własnym blokiem statycznym. Zainicjalizuj TYLKO klasę
         * zagnieżdżoną (bez dotykania zewnętrznej) i wypisz obserwację:
         * czy inicjalizacja klasy zagnieżdżonej wymusza też inicjalizację
         * klasy zewnętrznej?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_CompareInitializationOfInterfaceVsClass {
        /*
         * 🧪 Zadanie 25:
         * Stwórz interfejs z polem "static final int X = compute();" (gdzie
         * compute() to metoda statyczna w tym interfejsie drukująca
         * komunikat) oraz implementującą go klasę. Sprawdź, czy samo
         * ZAIMPLEMENTOWANIE interfejsu (bez odwołania do X) wymusza jego
         * inicjalizację - wypisz obserwację.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_BuildCustomClassInitializationTracker {
        /*
         * 🧪 Zadanie 26:
         * Napisz mechanizm śledzący (np. statyczna lista Stringów w
         * osobnej klasie "narzędziowej"), do którego kilka różnych klas z
         * blokami statycznymi dopisuje wpis w momencie swojej
         * inicjalizacji. Zainicjalizuj je w określonej kolejności i wypisz
         * finalny dziennik potwierdzający tę kolejność.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_ExplainRelationshipBetweenLinkingAndVerifyOption {
        /*
         * 🧪 Zadanie 27:
         * W komentarzu wyjaśnij, po co istnieje opcja JVM "-noverify"/
         * "-Xverify:none" (dostępna w starszych JVM, dziś usunięta/
         * przestarzała) i dlaczego jej użycie byłoby ryzykowne w
         * kontekście fazy Verification omówionej w tej lekcji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_SimulateClassNotFoundVsNoClassDefFoundScenario {
        /*
         * 🧪 Zadanie 28:
         * Wywołaj Class.forName na nieistniejącej klasie, by złapać
         * ClassNotFoundException. Następnie w komentarzu wyjaśnij różnicę
         * względem NoClassDefFoundError (poznanego w innym rozdziale kursu)
         * z perspektywy faz LOADING vs INITIALIZATION.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_BuildFullClassLifecycleReportForCustomClass {
        /*
         * 🧪 Zadanie 29:
         * Stwórz własną klasę z: blokiem statycznym, polem statycznym
         * niebędącym stałą kompilacyjną, konstruktorem oraz metodą
         * instancyjną. Zbuduj pełny raport pokazujący kolejno: (1) moment
         * załadowania bez inicjalizacji, (2) moment inicjalizacji (pierwsze
         * realne użycie), (3) moment utworzenia instancji, (4) moment
         * wywołania metody instancyjnej - każdy krok z jasnym komunikatem.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_CompareClassLoadingBehaviorAcrossThreeJdkClasses {
        /*
         * 🧪 Zadanie 30:
         * Wybierz 3 klasy z różnych "warstw" JDK (np. java.lang.Object,
         * java.util.regex.Pattern, oraz klasę z biblioteki dodanej w
         * pom.xml tego projektu, jeśli jest dostępna na classpath - w
         * ostateczności użyj własnej klasy z innego pakietu kursu). Dla
         * każdej wypisz pełny łańcuch class loaderów i podsumuj w
         * komentarzu, co ta różnica (lub jej brak) mówi o pochodzeniu
         * danej klasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
