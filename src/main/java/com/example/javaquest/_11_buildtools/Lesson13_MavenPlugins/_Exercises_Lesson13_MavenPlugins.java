package com.example.javaquest._11_buildtools.Lesson13_MavenPlugins;

public class _Exercises_Lesson13_MavenPlugins {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ConfigureCompilerPluginRelease {
        /*
         * 🧪 Zadanie 1:
         * W realnym projekcie Maven dodaj do pom.xml jawną konfigurację maven-compiler-plugin
         * z <release>21</release>. Uruchom "mvn compile" w terminalu i zapisz w komentarzu,
         * że kompilacja przechodzi identycznie jak z source/target.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OldStyleSourceTarget {
        /*
         * 🧪 Zadanie 2:
         * Zmień konfigurację z zadania 1 na starszy styl: <source>21</source> i
         * <target>21</target> (bez <release>). Uruchom "mvn compile" ponownie i zapisz
         * w komentarzu, że wynik jest ten sam - to dwa różne zapisy tej samej intencji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SurefireReportsInspection {
        /*
         * 🧪 Zadanie 3:
         * W projekcie z co najmniej jednym testem JUnit uruchom "mvn test". Otwórz katalog
         * target/surefire-reports/ i zapisz w komentarzu nazwy plików, które tam znalazłeś
         * (np. TEST-*.xml, *.txt).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MainClassInManifest {
        /*
         * 🧪 Zadanie 4:
         * Skonfiguruj maven-jar-plugin z <archive><manifest><mainClass> wskazującym Twoją
         * klasę z main(). Uruchom "mvn package" i odpal "java -jar target/<nazwa>.jar" -
         * zapisz w komentarzu, że teraz działa BEZ podawania "-cp" ani nazwy klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JarWithoutMainClassFails {
        /*
         * 🧪 Zadanie 5:
         * Usuń konfigurację Main-Class z zadania 4, uruchom "mvn package" ponownie i
         * spróbuj "java -jar target/<nazwa>.jar" bez podawania klasy. Zapisz w komentarzu
         * dokładny błąd, jaki wypisze Java ("no main manifest attribute").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PluginPurposeMatching {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wypisz na konsoli (System.out.println) 6 linii łączących nazwę
         * pluginu z jego głównym zadaniem (compiler -> kompilacja, surefire -> testy,
         * jar -> pakowanie JAR, war -> pakowanie WAR, shade -> fat jar, exec -> odpalanie
         * aplikacji przez Maven).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InspectPlainJarContents {
        /*
         * 🧪 Zadanie 7:
         * W projekcie z zależnością (np. gson) w scope compile uruchom "mvn package" (bez
         * shade-plugin). Otwórz zbudowany .jar (np. "jar tf target/*.jar" w terminalu) i
         * zapisz w komentarzu, że wewnątrz jest TYLKO Twój kod, BEZ klas gson.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RunViaExecPlugin {
        /*
         * 🧪 Zadanie 8:
         * Dodaj do pom.xml exec-maven-plugin z <mainClass> wskazującym Twoją klasę z main().
         * Uruchom w terminalu "mvn exec:java" i zapisz w komentarzu output aplikacji
         * (powinien być identyczny jak przy "java -cp target/classes <klasa>").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PluginVersionPinning {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wypisz wyjaśnienie, dlaczego dobrą praktyką jest przypinanie
         * (<version>) konkretnej wersji pluginu w pom.xml, a nie zdawanie się na wersję
         * "domyślną" Maven-a - jaki problem to rozwiązuje (powtarzalność budowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WarPackagingSetup {
        /*
         * 🧪 Zadanie 10:
         * Utwórz nowy projekt Maven z packaging="war" i minimalną strukturą
         * src/main/webapp/WEB-INF/web.xml (może być prosty, pusty deskryptor). Dodaj
         * maven-war-plugin. Uruchom "mvn package" i sprawdź w terminalu, że powstał plik
         * .war w target/.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ShadePluginFatJarBuild {
        /*
         * 🧪 Zadanie 11:
         * W projekcie z zależnością gson dodaj maven-shade-plugin skonfigurowany jak w
         * lekcji (z ManifestResourceTransformer i mainClass). Uruchom "mvn package" w
         * terminalu i zapisz w komentarzu, że powstały DWA jary w target/ (oryginalny i
         * "-shaded" albo nadpisany, zależnie od konfiguracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareJarSizes {
        /*
         * 🧪 Zadanie 12:
         * Porównaj (w terminalu, np. "dir target\\*.jar" na Windows) rozmiary pliku .jar
         * zbudowanego przez plain maven-jar-plugin i pliku zbudowanego przez
         * maven-shade-plugin w tym samym projekcie. Zapisz w komentarzu różnicę w KB/MB
         * i wyjaśnienie, skąd ta różnica.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RunFatJarStandalone {
        /*
         * 🧪 Zadanie 13:
         * Uruchom "java -jar" na fat jarze z zadania 11 w terminalu, w katalogu bez dostępu
         * do ~/.m2 (np. tymczasowo zmienionym M2_HOME, albo po prostu w innym katalogu
         * roboczym) - zapisz w komentarzu, że aplikacja działa samodzielnie, bez błędu
         * NoClassDefFoundError, bo gson jest już w środku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RunPlainJarWithoutClasspathFails {
        /*
         * 🧪 Zadanie 14:
         * Uruchom "java -jar" na PLAIN jarze (bez shade, z gson w kodzie) - zapisz w
         * komentarzu dokładny błąd NoClassDefFoundError/ClassNotFoundException dla klasy
         * z pakietu com.google.gson, bo gson nie jest wewnątrz tego jara.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FixPlainJarWithClasspath {
        /*
         * 🧪 Zadanie 15:
         * Naprawiaj problem z zadania 14 RĘCZNIE (bez shade): znajdź plik gson-*.jar w
         * ~/.m2/repository i uruchom aplikację poleceniem "java -cp
         * target/classes;<sciezka-do-gson.jar>" (Windows, separator ";") - zapisz w
         * komentarzu, że to dokładnie ta praca, którą shade-plugin robi za Ciebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SurefireSkipConfiguration {
        /*
         * 🧪 Zadanie 16:
         * Skonfiguruj maven-surefire-plugin z <configuration><skipTests>true</skipTests>
         * w pom.xml (a nie flagą -D). Uruchom "mvn package" w terminalu i zapisz w
         * komentarzu, że testy są pomijane bez podawania żadnej flagi w linii komend.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExecPluginWithArguments {
        /*
         * 🧪 Zadanie 17:
         * Zmodyfikuj klasę main() w projekcie tak, żeby czytała args[0]. Skonfiguruj
         * exec-maven-plugin z <arguments><argument>jakas-wartosc</argument></arguments>.
         * Uruchom "mvn exec:java" w terminalu i zapisz w komentarzu, że argument dotarł
         * do aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WarPluginFailOnMissingWebXml {
        /*
         * 🧪 Zadanie 18:
         * W projekcie packaging=war USUŃ plik web.xml i ustaw w maven-war-plugin
         * <failOnMissingWebXml>false</failOnMissingWebXml>. Uruchom "mvn package" w
         * terminalu i zapisz w komentarzu, że budowanie się udało mimo braku web.xml
         * (nowsze specyfikacje Servlet API pozwalają na deskryptor przez adnotacje, nie
         * plik XML).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PluginGoalListingCommand {
        /*
         * 🧪 Zadanie 19:
         * W terminalu uruchom "mvn org.apache.maven.plugins:maven-help-plugin:describe
         * -Dplugin=org.apache.maven.plugins:maven-jar-plugin -Dfull" (albo krócej "mvn
         * help:describe -Dplugin=jar -Dfull" jeśli masz help-plugin skonfigurowany).
         * Zapisz w komentarzu, jakie goale wypisało dla maven-jar-plugin.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SixPluginsComparisonTablePrint {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wypisz tabelę (println) porównującą 6 pluginów z lekcji wg 2
         * kolumn: "Faza domyślna" i "Główny goal" (np. "compiler | compile |
         * compiler:compile").
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ShadeWithExclusions {
        /*
         * 🧪 Zadanie 21:
         * Skonfiguruj maven-shade-plugin z sekcją <filters> wykluczającą z fat jara pliki
         * podpisów (META-INF/*.SF, *.DSA, *.RSA) - typowy problem "invalid signature file
         * digest" przy fat jarach. Uruchom "mvn package" w terminalu i zapisz w
         * komentarzu, czy build przeszedł bez błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiExecutionSurefireAndFailsafe {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: napisz w komentarzu porównanie maven-surefire-plugin (testy
         * jednostkowe, faza test) z maven-failsafe-plugin (testy integracyjne, fazy
         * integration-test/verify) - czemu są to DWA różne pluginy, mimo podobnego API
         * testowego (JUnit).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CustomPhaseBinding {
        /*
         * 🧪 Zadanie 23:
         * Skonfiguruj exec-maven-plugin tak, żeby jego goal "exec:java" był PODWIĄZANY
         * do konkretnej fazy (np. "verify") przez <executions><execution><phase>, a nie
         * odpalany ręcznie jako "mvn exec:java". Uruchom "mvn verify" w terminalu i
         * zapisz w komentarzu, że aplikacja odpaliła się automatycznie w ramach tej fazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ShadeRelocation {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: wypisz wyjaśnienie mechanizmu <relocations> w maven-shade-plugin -
         * przenoszenia pakietów bibliotek do innej nazwy pakietu wewnątrz fat jara, żeby
         * uniknąć konfliktów wersji tej samej biblioteki między Twoim kodem a inną
         * zależnością ("shading" w sensie dosłownym - stąd nazwa pluginu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WarWithMultipleWebResources {
        /*
         * 🧪 Zadanie 25:
         * W projekcie packaging=war dodaj do src/main/webapp plik index.html i
         * WEB-INF/web.xml. Uruchom "mvn package" w terminalu, rozpakuj (np. "jar tf") war
         * z target/ i zapisz w komentarzu, że oba pliki znalazły się we właściwych
         * miejscach w archiwum.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PluginManagementPreview {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wypisz krótkie wyjaśnienie, czym RÓŻNI się <pluginManagement> od
         * zwykłego <plugins> (podobnie jak dependencyManagement vs dependencies) - że
         * pluginManagement tylko DEKLARUJE wersję/domyślną konfigurację, ale plugin
         * faktycznie się WYKONA tylko, jeśli zostanie też wymieniony w <plugins>. Pełny
         * przykład zobaczysz w Lesson14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BenchmarkPlainVsFatJarStartup {
        /*
         * 🧪 Zadanie 27:
         * W terminalu zmierz (np. "Measure-Command" w PowerShell albo "time" na Linux/Mac)
         * czas odpalenia plain jara (z classpath) vs fat jara z tego samego projektu.
         * Zapisz w komentarzu wyniki i skomentuj, czy różnica jest odczuwalna dla małej
         * aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CustomManifestEntries {
        /*
         * 🧪 Zadanie 28:
         * Skonfiguruj maven-jar-plugin z dodatkowymi, WŁASNYMI wpisami manifestu (np.
         * <manifestEntries><Build-Time>...</Build-Time></manifestEntries>). Uruchom
         * "mvn package" w terminalu, rozpakuj META-INF/MANIFEST.MF z jara i zapisz w
         * komentarzu, że Twój własny wpis się tam znalazł.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullSixPluginPomFromScratch {
        /*
         * 🧪 Zadanie 29:
         * Napisz od zera (bez kopiowania z lekcji) kompletny pom.xml konfigurujący
         * WSZYSTKIE 6 pluginów z tej lekcji naraz w jednym, realnym projekcie testowym.
         * Uruchom w terminalu "mvn clean package" i zapisz w komentarzu, czy build
         * przeszedł, i jakie artefakty powstały w target/.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_PluginsCapstoneReport {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie: w projekcie z zadania 29 zbuduj RAPORT tekstowy (w
         * komentarzu w kodzie) opisujący dla każdego z 6 pluginów: jego goal, fazę
         * domyślną, i jedno zdanie "kiedy używać w praktyce". Dodatkowo zapisz wnioski
         * z porównania rozmiaru plain jar vs fat jar z tego samego projektu.
         */
        public static void main(String[] args) { }
    }
}
