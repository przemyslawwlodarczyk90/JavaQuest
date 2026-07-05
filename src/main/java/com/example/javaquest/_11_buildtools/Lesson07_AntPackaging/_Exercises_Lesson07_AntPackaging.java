package com.example.javaquest._11_buildtools.Lesson07_AntPackaging;

public class _Exercises_Lesson07_AntPackaging {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimplestJarNoManifest {
        /*
         * 🧪 Zadanie 1:
         * Embedded Antem zbuduj najprostszy mozliwy JAR (bez zadnego manifestu) z JEDNEJ
         * skompilowanej klasy (dowolna klasa z metoda main wypisujaca cokolwiek). Otworz
         * powstaly plik przez java.util.zip.ZipFile i wypisz nazwy wszystkich wpisow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExecutableJarDifferentMain {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj wykonywalny JAR (z manifestem Main-Class) dla WLASNEJ klasy Main, ktora
         * wypisuje "Zadanie 2 dziala!". Uzyj <jar><manifest><attribute .../></manifest></jar>,
         * a nastepnie odpal powstaly plik komenda "java -jar" przez ProcessBuilder i wypisz
         * przechwycony output.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExtraManifestAttribute {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj JAR z DWOMA atrybutami manifestu: Main-Class oraz Implementation-Version
         * (np. "1.0.0-cwiczenie"). Otworz plik przez java.util.jar.JarFile, odczytaj
         * java.util.jar.Manifest i wypisz wartosci obu atrybutow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ResourceInsideJar {
        /*
         * 🧪 Zadanie 4:
         * Do katalogu z klasami dolacz plik zasobu "app.properties" (np. "version=1.0\n").
         * Zbuduj JAR obejmujacy zarowno klasy jak i ten zasob (jeden <fileset dir="...">
         * obejmujacy oba). Sprawdz ZipFile-em, ze wpis "app.properties" jest w archiwum.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JarWithIncludePattern {
        /*
         * 🧪 Zadanie 5:
         * Skompiluj klasy tak, aby w tym samym katalogu wynikowym znalazly sie i pliki
         * .class, i (recznie skopiowane) pliki .java. Zbuduj JAR uzywajac <fileset> z
         * <include name="**&#47;*.class"/>, tak aby pliki .java NIE trafily do archiwum.
         * Zweryfikuj to ZipFile-em.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TwoClassCalculatorJar {
        /*
         * 🧪 Zadanie 6:
         * Napisz dwie klasy: Calculator (metoda statyczna int add(int,int)) i Main (wywoluje
         * Calculator.add(20, 22) i wypisuje wynik). Skompiluj obie, zbuduj wykonywalny JAR
         * i odpal go, sprawdzajac w wyjsciu, ze wypisano "42".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_JarWithoutMainClassFails {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj JAR BEZ atrybutu Main-Class (brak nested <manifest>). Sprobuj go odpalic
         * "java -jar" przez ProcessBuilder i przechwyc output - powinien zawierac komunikat
         * o braku glownego atrybutu manifestu ("no main manifest attribute"). Wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ClassPathManifestAttribute {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj biblioteke jako osobny JAR "helper.jar" (jedna klasa z metoda statyczna).
         * Zbuduj drugi JAR "app.jar" z Main-Class ORAZ atrybutem manifestu
         * "Class-Path" wskazujacym "helper.jar" (oba pliki w tym samym katalogu). Odpal
         * app.jar i sprawdz, czy dziala - wypisz komentarzem, czym to podejscie rozni sie
         * od fat jara z lekcji (Class-Path w manifescie wymaga fizycznej obecnosci helper.jar
         * w tym samym miejscu, wiec jest bardziej kruche).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MinimalWarWebXmlOnly {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj minimalny WAR zawierajacy TYLKO WEB-INF/web.xml (bez zadnych klas -
         * pomin nested <classes>). Uzyj wlasnego, prostego web.xml. Otworz plik ZipFile-em
         * i wypisz wszystkie wpisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WarWithTwoPojoClasses {
        /*
         * 🧪 Zadanie 10:
         * Skompiluj DWIE proste klasy POJO (np. UserInfo i OrderInfo, bez zaleznosci od
         * servlet API). Zbuduj WAR z <classes dir="..."> obejmujacym obie klasy, bez
         * podawania wlasnego web.xml (Ant sam wygeneruje domyslny). Wypisz liczbe wpisow
         * w powstalym archiwum.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThinVsFatJarSecondScenario {
        /*
         * 🧪 Zadanie 11:
         * Analogicznie do demo z lekcji, ale z INNA para klas: biblioteka "StringUtils"
         * (metoda reverse(String)) i aplikacja "Main" jej uzywajaca. Zbuduj najpierw "cienki"
         * JAR (bez biblioteki) i odpal go, przechwytujac NoClassDefFoundError. Potem zbuduj
         * fat jar (zipfileset) i odpal ponownie, dowodzac, ze teraz dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FatJarTwoDependencies {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj DWIE osobne "biblioteki" jako dwa JAR-y (np. MathLib i TextLib, kazda z
         * jedna klasa i jedna metoda statyczna). Zbuduj fat jar aplikacji korzystajacej z
         * OBU bibliotek, uzywajac DWOCH nested <zipfileset src="..."> w jednym tasku <jar>.
         * Odpal wynikowy JAR i wypisz output potwierdzajacy, ze obie biblioteki dzialaja.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WarWithLibJar {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj mala "biblioteke" jako JAR (jedna klasa). Zbuduj WAR z nested <lib>
         * (element <war> kopiujacy podane pliki do WEB-INF/lib/) wskazujacym na ten JAR.
         * ZipFile-em sprawdz, ze wpis "WEB-INF/lib/<nazwa>.jar" istnieje w archiwum.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ZipfilesetOrderConflict {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj DWA malutkie JAR-y zawierajace klasy o TEJ SAMEJ, w pelni kwalifikowanej
         * nazwie (np. demo.util.Version), ale z INNA trescia metody (kazda wypisuje inny
         * tekst). Zbuduj fat jar laczacy oba <zipfileset> w konkretnej kolejnosci, odpal go
         * i sprawdz (na podstawie wypisanego tekstu), ktora wersja klasy "wygrala" w
         * finalnym archiwum. Powtorz z odwrotna kolejnoscia zipfilesetow i porownaj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AllInOnePipelineTarget {
        /*
         * 🧪 Zadanie 15:
         * Napisz build.xml z targetem "all" depends="compile,jar,war", ktory jednym
         * wywolaniem project.executeTarget("all") buduje i JAR, i WAR z tych samych
         * skompilowanych klas. Po wykonaniu wypisz liczbe wpisow w obu powstalych plikach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MainClassReadsBundledResource {
        /*
         * 🧪 Zadanie 16:
         * Napisz klase Main, ktora w metodzie main() woła
         * getClass().getResourceAsStream("/app.properties") i wypisuje jej zawartosc.
         * Zbuduj JAR obejmujacy klase Main ORAZ plik app.properties (patrz Zadanie 4),
         * ustaw Main-Class i odpal go - potwierdz, ze klasa poprawnie odczytala zasob
         * z WLASNEGO archiwum, w ktorym sie znajduje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WarWithEmptyClassesDir {
        /*
         * 🧪 Zadanie 17:
         * Sprobuj zbudowac WAR z nested <classes dir="..."> wskazujacym na PUSTY katalog
         * (nie kompiluj nic wczesniej). Sprawdz, jaki plik powstaje i co dokladnie znajduje
         * sie (albo nie znajduje) w WEB-INF/classes - wypisz wnioski.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GenericZipInspector {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode pomocnicza java.util.List&lt;String&gt; listZipEntries(Path zipFile),
         * dzialajaca dla KAZDEGO pliku ZIP/JAR/WAR. Uzyj jej do porownania listy wpisow
         * zwyklego JAR-a i WAR-a zbudowanych z tych samych klas (ten sam <fileset dir="...">
         * uzyty w obu targetach) - wypisz, jakie sciezki sa inne (prefiks WEB-INF/classes/).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_DiscoverMainClassFromManifest {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj fat jar (jak w demo z lekcji). Bez zagladania do XML-a - odczytaj wartosc
         * atrybutu Main-Class z powstalego pliku przy pomocy java.util.jar.JarFile i
         * Manifest. Uzyj ODCZYTANEJ wartosci (nie zaszytej na sztywno w kodzie) do
         * zbudowania komendy ProcessBuilder("java", "-jar", ...) - ta komenda i tak odpala
         * caly JAR, ale pokazujesz w kodzie, ze umiesz PROGRAMOWO odkryc klase startowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ProvidedScopeMismatchExplanation {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj WAR z WEB-INF/classes zawierajacym skompilowana klase, ktora importuje
         * pakiet, ktorego JAR-u NIE dolaczasz nigdzie do archiwum (np. celowo pomin
         * biblioteke, od ktorej klasa "zalezy" konceptualnie). Otworz WAR ZipFile-em - sam
         * proces pakowania sie uda. Wypisz komentarzem, dlaczego w PRAWDZIWYM kontenerze
         * (np. Tomcat z rozdzialu _07_servlets) taka klasa i tak zawiodlaby w runtime
         * (NoClassDefFoundError) - to samo zjawisko co "provided scope" w Mavenie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FatJarThreeDependencies {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj fat jar dla aplikacji korzystajacej z TRZECH osobnych "bibliotek" (trzy
         * JAR-y, kazdy z inna klasa/metoda), laczac wszystkie trzy <zipfileset> w jednym
         * tasku <jar>. Odpal wynikowy JAR i wypisz output potwierdzajacy dzialanie
         * wszystkich trzech zaleznosci naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReusableFatJarBuilderMethod {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode buildFatJar(Path baseDir, Path buildFile, String target),
         * ktora konfiguruje Project/ProjectHelper/DefaultLogger i wywoluje podany target.
         * Uzyj jej DWA razy dla DWOCH roznych, niezaleznych projektow (rozne baseDir i
         * build.xml, ale ten sam target "fat-jar" w kazdym), dowodzac reuzywalnosci kodu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WarAndFatJarFromSameClasses {
        /*
         * 🧪 Zadanie 23:
         * Skompiluj JEDEN zestaw klas. W jednym build.xml zdefiniuj DWA niezalezne targety:
         * jeden pakujacy je do fat jara, drugi do WAR-a. Wykonaj oba i porownaj (wypisz)
         * listy wpisow obu artefaktow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DuplicateEntryDetector {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj DWA osobne JAR-y "biblioteczne" majace WSPOLNY (identyczny co do nazwy)
         * wpis, np. oba zawieraja plik o tej samej sciezce "shared/Config.class" (dwie
         * rozne klasy o tej samej nazwie w tym samym pakiecie w dwoch niezaleznych src).
         * Napisz w Javie (nie w Ancie) kod, ktory otwiera oba JAR-y ZipFile-em, liczy
         * PRZECIECIE nazw wpisow i wypisuje kazdy konflikt - prosty "detektor konfliktow"
         * przed zlaczeniem ich w fat jar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RebuildAfterSourceChange {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj wykonywalny JAR z klasy Main wypisujacej "wersja 1". Odpal go i zapisz
         * output. Nastepnie NADPISZ plik source Main.java, zmieniajac tekst na "wersja 2",
         * ponownie wykonaj target "jar" (Ant sam skompiluje zmienione zrodlo) i odpal
         * PRZEBUDOWANY JAR. Porownaj oba przechwycone wyjscia, dowodzac, ze przebudowa
         * naprawde zadzialala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DynamicManifestAttributesFromJava {
        /*
         * 🧪 Zadanie 26:
         * Przed wywolaniem executeTarget ustaw w Javie project.setProperty("build.by", ...)
         * i project.setProperty("build.date", ... aktualny czas ...). W build.xml uzyj tych
         * property jako wartosci atrybutow manifestu "Built-By" i "Build-Date" (obok
         * Main-Class). Po zbudowaniu JAR-a odczytaj manifest przez JarFile i wypisz obie
         * wartosci, dowodzac, ze pochodza z kodu Javy, nie z XML-a na sztywno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_WarStructureValidator {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj WAR z WEB-INF/classes (min. 2 klasy), WEB-INF/lib (min. 1 JAR) oraz wlasnym
         * web.xml. Napisz metode validateWar(Path warFile) sprawdzajaca po nazwach wpisow:
         * czy istnieje WEB-INF/web.xml, czy istnieje co najmniej 1 wpis pod WEB-INF/classes/
         * i co najmniej 1 plik .jar pod WEB-INF/lib/. Wypisz "WALIDACJA OK" albo liste
         * brakujacych elementow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DevVsProdFatJarVariants {
        /*
         * 🧪 Zadanie 28:
         * W JEDNYM build.xml zdefiniuj property "env" (dev/prod) i uzyj <condition>, aby
         * warunkowo dolaczyc DODATKOWY <zipfileset> (np. "debug-logging.jar") tylko gdy
         * env=dev. Zbuduj wariant dev (project.setProperty("env","dev") przed
         * executeTarget) i wariant prod w DWOCH niezaleznych uruchomieniach (dwa Project),
         * porownujac liczbe wpisow i zachowanie po odpaleniu obu JAR-ow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareJarSizes {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj (jak w demo z lekcji albo Zadaniu 11) parę cienki/fat jar. Napisz metode
         * compareJarSizes(Path thinJar, Path fatJar) wypisujaca rozmiar obu plikow w
         * bajtach oraz roznice - konkretne liczby udowadniajace, ze fat jar jest wiekszy,
         * bo zawiera dodatkowe klasy z biblioteki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_PackagingPipelineCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj JEDEN build.xml z pelnym lancuchem targetow: clean -> compile ->
         * test-stub (<echo> udajacy testy) -> jar -> fat-jar -> war, polaczonych przez
         * depends w jeden finalny target "release". Wywolaj project.executeTarget("release")
         * JEDNYM wywolaniem z Javy, a na koniec wypisz podsumowanie: sciezki do WSZYSTKICH
         * powstalych artefaktow (JAR, fat JAR, WAR) razem z potwierdzeniem Files.exists(...)
         * dla kazdego z nich.
         */
        public static void main(String[] args) { }
    }
}
