package com.example.javaquest._11_buildtools.Lesson12_MavenDependencies;

public class _Exercises_Lesson12_MavenDependencies {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddSingleDependency {
        /*
         * 🧪 Zadanie 1:
         * W nowym projekcie Maven (struktura jak w Lesson11) dodaj do pom.xml zależność
         * com.google.code.gson:gson:2.11.0 (scope domyślny compile). Uruchom "mvn compile"
         * w terminalu i zapisz w komentarzu, czy się udało bez błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseGsonInCode {
        /*
         * 🧪 Zadanie 2:
         * W klasie App.java użyj klasy Gson (new Gson().toJson(...)) na prostym obiekcie.
         * Uruchom "mvn compile" a potem "mvn package" - zapisz w komentarzu, czy kompilacja
         * przechodzi dzięki dodanej zależności z zadania 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestScopeDependency {
        /*
         * 🧪 Zadanie 3:
         * Dodaj zależność junit-jupiter ze scope="test". Napisz klasę testową w
         * src/test/java, która używa JUnit-a. Spróbuj (celowo) użyć klasy z junit-jupiter
         * w src/main/java - zapisz w komentarzu błąd kompilacji, jaki to spowoduje
         * (dowód, że scope=test nie jest dostępny w main).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ProvidedScopeExplanationPrint {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wypisz na konsoli (System.out.println) wyjaśnienie, dlaczego
         * jakarta.servlet-api w projekcie servletowym ma scope="provided" - kto i kiedy
         * faktycznie dostarcza tę bibliotekę w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ScopeTablePrint {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wypisz tabelę (jak w lekcji) porównującą 4 scope'y (compile,
         * provided, runtime, test) wg 3 kryteriów: dostępność przy kompilacji, dostępność
         * w testach, obecność w finalnym artefakcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_InspectLocalRepoFolder {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: użyj System.getProperty("user.home") do zbudowania ścieżki do
         * ~/.m2/repository i wypisz ją. Sprawdź (Files.isDirectory), czy istnieje na Twojej
         * maszynie i wypisz odpowiedni komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RunDependencyTree {
        /*
         * 🧪 Zadanie 7:
         * W projekcie z zadania 1-2 uruchom w terminalu "mvn dependency:tree". Zapisz w
         * komentarzu pełny output - ile bibliotek transytywnych zostało ściągniętych przez
         * samo dodanie gson (sprawdź, czy gson ma jakieś zależności przechodnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TwoDependenciesDifferentScopes {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do pom.xml DWIE zależności: gson (scope compile) i junit-jupiter
         * (scope test). Uruchom "mvn dependency:tree" w terminalu i zapisz, jak Maven
         * oznacza scope każdej z nich w wyniku (np. ":compile", ":test").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TransitiveDependencyExplanationPrint {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wypisz na konsoli wyjaśnienie zależności przechodnich z konkretnym
         * przykładem z tego kursu - że spring-boot-starter-web (deklarowana w pom.xml
         * JavaQuest) transytywnie dociąga tomcat-embed-core używany w rozdziale _07_servlets.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ThisRepoDependencyTree {
        /*
         * 🧪 Zadanie 10:
         * W KORZENIU repozytorium tego kursu (javaQuest) uruchom w terminalu
         * "mvnw.cmd dependency:tree" (Windows) lub "./mvnw dependency:tree" (Linux/Mac).
         * Znajdź w wyniku gson i h2 - zapisz w komentarzu, na jakiej głębokości drzewa się
         * znajdują (bezpośrednie zależności = głębokość 1).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SimulateVersionConflict {
        /*
         * 🧪 Zadanie 11:
         * Bez terminala: napisz w komentarzu przykładowy (fikcyjny) scenariusz konfliktu
         * wersji dla 2 bibliotek A i C, które obie zależą od tej samej biblioteki B, ale w
         * różnych wersjach (1.0 i 2.0). Narysuj ASCII-drzewo (jak w lekcji) i zapisz, którą
         * wersję B wybierze Maven wg reguły "najbliższa w drzewie wygrywa".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExclusionsBlock {
        /*
         * 🧪 Zadanie 12:
         * Napisz (jako String w kodzie, bez pisania realnego projektu) fragment pom.xml
         * z <exclusions> wykluczającym konkretną zależność przechodnią (dowolna fikcyjna
         * biblioteka) z zależności "com.example:some-library:1.0.0", a następnie
         * deklarującym tę wykluczoną bibliotekę osobno, w konkretnej wersji. Wypisz ten
         * fragment na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ForceVersionByDirectDeclaration {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala: wypisz wyjaśnienie, dlaczego zadeklarowanie biblioteki B
         * BEZPOŚREDNIO w pom.xml Twojego projektu (głębokość 1) "wygrywa" z jej wersją
         * przychodzącą przechodnio głębiej w drzewie (głębokość 2+) - podaj przykład z
         * konkretnymi numerami wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RealM2Listing {
        /*
         * 🧪 Zadanie 14:
         * Bez terminala: jeśli katalog ~/.m2/repository istnieje na Twojej maszynie,
         * wypisz (Files.list, z sortowaniem) 10 pierwszych podkatalogów groupId, jakie
         * znajdziesz na pierwszym poziomie. Jeśli katalog nie istnieje, wypisz przyjazny
         * komunikat zamiast wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FindGsonInM2 {
        /*
         * 🧪 Zadanie 15:
         * Jeśli wykonałeś wcześniej "mvn compile" z zależnością gson, sprawdź (bez terminala,
         * w Javie), czy plik ~/.m2/repository/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar
         * istnieje na dysku (Files.exists) i wypisz odpowiedni komunikat z pełną ścieżką.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RuntimeScopeExample {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: napisz przykład pom.xml (jako String) z zależnością sterownika
         * JDBC (np. org.postgresql:postgresql) w scope="runtime" - wyjaśnij w komentarzu,
         * czemu kod źródłowy NIE odwołuje się bezpośrednio do klas sterownika (używa tylko
         * java.sql.* przez JDBC), a jednak sterownik musi być na classpath w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_DependencyTreeAfterExclusion {
        /*
         * 🧪 Zadanie 17:
         * W prawdziwym małym projekcie Maven dodaj zależność spring-boot-starter-web
         * (bez wersji, bez parenta - dodaj wersję jawnie, np. 3.4.4, jeśli nie masz parenta
         * spring-boot-starter-parent). Uruchom "mvn dependency:tree" w terminalu i policz,
         * ile transytywnych zależności zostało ściągniętych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareTreeBeforeAfterExclusion {
        /*
         * 🧪 Zadanie 18:
         * W projekcie z zadania 17 dodaj <exclusions> wykluczające jedną z widocznych w
         * drzewie zależności transytywnych (np. konkretny logger). Uruchom "mvn
         * dependency:tree" ponownie i porównaj oba wyniki - zapisz w komentarzu różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SystemScopeWarningPrint {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: wypisz na konsoli ostrzeżenie/wyjaśnienie, czemu scope="system"
         * (zależność wskazywana <systemPath> na dysku) jest rzadko używana i uważana za
         * "code smell" w większości projektów (m.in. brak przenośności między maszynami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ThisCourseDependenciesAudit {
        /*
         * 🧪 Zadanie 20:
         * Otwórz pom.xml w korzeniu repozytorium tego kursu. Wypisz (bez terminala, po
         * prostu jako println w kodzie na podstawie tego, co przeczytałeś) listę WSZYSTKICH
         * bezpośrednich zależności wraz z ich scope (domyślny compile, jeśli nie podano) -
         * łącznie z ant, ant-junit, ivy dodanymi na potrzeby tego rozdziału.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ThreeLibraryConflictSimulation {
        /*
         * 🧪 Zadanie 21:
         * Bez terminala: rozszerz scenariusz z zadania 11 na 3 poziomy głębokości (Twój
         * projekt -> A -> D -> B:1.0 oraz Twój projekt -> C -> B:2.0). Narysuj pełne
         * ASCII-drzewo z głębokościami i zapisz, którą wersję B wybierze dependency
         * mediation i dlaczego (porównanie głębokości: 3 vs 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ExclusionPlusPin {
        /*
         * 🧪 Zadanie 22:
         * W realnym projekcie Maven z zależnością mającą duże drzewo transytywne (np.
         * spring-boot-starter-web) znajdź w "mvn dependency:tree" bibliotekę transytywną,
         * którą chciałbyś "przypiąć" do innej wersji. Dodaj <exclusions> + osobną deklarację
         * z wybraną wersją, uruchom "mvn dependency:tree" i zweryfikuj w terminalu, że nowa
         * wersja się pojawiła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DependencyTreeToFile {
        /*
         * 🧪 Zadanie 23:
         * W terminalu uruchom "mvn dependency:tree -DoutputFile=tree.txt" w dowolnym
         * projekcie Maven. Sprawdź, że plik tree.txt powstał w katalogu projektu, otwórz go
         * i zapisz w komentarzu jego pierwsze 5 linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DependencyAnalyzeGoal {
        /*
         * 🧪 Zadanie 24:
         * W terminalu uruchom "mvn dependency:analyze" w projekcie z kilkoma zależnościami.
         * Zapisz w komentarzu, jakie sekcje wypisał ten goal (np. "Used undeclared
         * dependencies", "Unused declared dependencies") i co dokładnie oznaczają.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BomImportExplanationPrint {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: wypisz wyjaśnienie scope="import" - że używany jest WYŁĄCZNIE
         * wewnątrz <dependencyManagement><dependencies> do zaimportowania całego BOM-u
         * (Bill of Materials) innego projektu (np. spring-boot-dependencies), co pozwala
         * pominąć <version> przy wielu zależnościach naraz. Ta lekcja tylko wspomina temat -
         * pełny przykład zobaczysz w Lesson14 (dependencyManagement).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RealM2SizeReport {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: jeśli ~/.m2/repository istnieje, policz (rekurencyjnie, Files.walk)
         * łączną liczbę plików .jar znajdujących się w lokalnym repozytorium na tej maszynie
         * i wypisz tę liczbę. Jeśli katalog nie istnieje - wypisz przyjazny komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConflictResolutionDecisionTree {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: napisz metodę explainConflictResolution(), która na podstawie
         * dwóch podanych "głębokości" (int) dwóch konkurujących wersji tej samej biblioteki
         * wypisuje, którą wersję Maven wybierze i dlaczego (najmniejsza głębokość wygrywa,
         * remis -> pierwsza zadeklarowana). Przetestuj ją na 3 różnych parach głębokości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultiScopeRealProjectBuild {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj realny projekt Maven z 4 zależnościami o RÓŻNYCH scope'ach (compile,
         * provided, runtime, test - każdy raz). Uruchom "mvn package" w terminalu i
         * rozpakuj (np. "jar tf target/*.jar") powstały .jar - zapisz w komentarzu, KTÓRE
         * z 4 bibliotek faktycznie znalazły się w środku, a które nie (i czy to zgadza się
         * z tabelą scope'ów z lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DependencyTreeDiffScript {
        /*
         * 🧪 Zadanie 29:
         * W terminalu wygeneruj "mvn dependency:tree" DWA razy dla tego samego projektu -
         * raz PRZED, raz PO dodaniu nowej zależności. Zapisz oba pliki wyników i porównaj
         * je (np. "fc" na Windows albo "diff" na Linux/Mac) - zapisz w komentarzu, jakie
         * nowe wpisy się pojawiły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullDependencyAuditCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie na realnym projekcie Maven z min. 5 zależnościami (różne
         * scope'y, w tym jedna z celowo wykluczoną zależnością przechodnią przez
         * <exclusions>): uruchom w terminalu po kolei "mvn dependency:tree",
         * "mvn dependency:analyze" i "mvn package". Napisz krótki raport tekstowy (w
         * komentarzu w kodzie) podsumowujący: liczbę zależności bezpośrednich, liczbę
         * transytywnych, czy były konflikty wersji i jak je rozwiązano.
         */
        public static void main(String[] args) { }
    }
}
